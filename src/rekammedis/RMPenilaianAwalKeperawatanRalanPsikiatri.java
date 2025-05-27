/*
 * Kontribusi dari M. Syukur RS Jiwa Kendari
 */


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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanRalanPsikiatri extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah,tabModeRencana,tabModeDetailRencana;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan="",finger=""; 
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRalanPsikiatri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","Keluhan Utama","Sakit Sejak","Riwayat Penyakit Dahulu","Berobat",
            "Hasil Pengobatan","Putus Obat","Keterangan Putus Obat","Masalah Ekonomi","Keterangan Masalah Ekonomi","Masalah Fisik","Keterangan Masalah Fisik",
            "Masalah Psikososial","Keterangan Masalah Psikososial","Resiko Herediter","Keterangan Resiko Herediter","Res Bunuh Diri","Ide Bunuh Diri","Keterangan Ide Bunuh DIri",
            "Rencana Bunuh Diri","Keterangan Rencana Bunuh Diri","Alat Bunuh Diri","Keterangan Alat Bunuh Diri","Percobaan Bunuh Diri","Keterangan Percobaan Bunuh Diri",
            "Keinginan Bunuh Diri","Keterangan Keinginan Bunuh Diri","Penggunaan Obat Psikiatri","Keterangan Penggunaan Obat Psikiatri","Efek Samping Obat","Keterangan Efek samping Obat",
            "Napza","Keterangan Penggunaan Napza","Lama Napza","Cara Pemakaian Napza","Latar Belakang Pemakaian Napza","Obat Lainnya","Keterangan Penggunaan Obat Lainnya",
            "Alasan Penggunaan Obat Lainnya","Alergi Obat","Keterangan Alergi Obat","Merokok","Keterangan Merokok","Minum Kopi","Keterangan Minum Kopi","TD","Nadi","GCS","RR","Suhu",
            "Keluhan Fisik","Keterangan Keluhan Fisik","Skala Nyeri","Durasi","Nyeri","Provokes","Keterangan Provokes","Kualitas","Keterangan Kualitas","Lokasi","Menyebar","Lapor Dokter",
            "Jam Lapor Nyeri","Nyeri Hilang","Keterangan Hilang Nyeri","BB","TB","BMI","Lapor Status Nutrisi","Jam Lapor Status Nutrisi","Skrining Gizi 1","Nilai 1","Skrining Gizi 2",
            "Nilai 2","Total Skor","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C","Hasil Pengkajian Resiko Jatuh","Lapor Dokter","Jam Dilapor",
            "ADL Mandi","ADL Berpakaian","ADL Makan","ADL BAK","ADL BAB","ADL Hobi","Keterangan ADL Hobi","ADL Sosialisasi","Keterangan ADL Sosialisasi","ADL Kegiatan","Keterangan ADL. Kegiatan",
            "Penampilan","Alam Perasaan","Pembicaraan","Afek","Aktifitas Motorik","Gangguan Ringan","Proses Pikir","Orientasi","Kesadaran","Memori","Interaksi","Konsentrasi",
            "Persepsi","Keterangan Persepsi","Isi Pikir","Waham","Keterangan Waham","Daya Tilik Diri","Keterangan Daya Tilik Diri","Pembelajaran","Keterangan Pembelajaran",
            "Keterangan Pembelajaran Lainnya","Penerjamah","Penerjamah Lainnya","Bahasa Isyarat","Kebutuhan Edukasi",
            "Keterangan Kebutuhan Edukasi","Rencana Keperawatan Lainnya","NIP","Nama Perawat"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 131; i++) {
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
                column.setPreferredWidth(120);   
            }else if(i==7){
                column.setPreferredWidth(65); 
            }else if(i==8){
                column.setPreferredWidth(115); 
            }else if(i==9){
                column.setPreferredWidth(80); 
            }else if(i==10){
                column.setPreferredWidth(200); 
            }else if(i==11){
                column.setPreferredWidth(80); 
            }else if(i==12){
                column.setPreferredWidth(170); 
            }else if(i==13){
                column.setPreferredWidth(78); 
            }else if(i==14){
                column.setPreferredWidth(95); 
            }else if(i==15){
                column.setPreferredWidth(65); 
            }else if(i==16){
                column.setPreferredWidth(123); 
            }else if(i==17){
                column.setPreferredWidth(91); 
            }else if(i==18){
                column.setPreferredWidth(150); 
            }else if(i==19){
                column.setPreferredWidth(75); 
            }else if(i==20){
                column.setPreferredWidth(134); 
            }else if(i==21){
                column.setPreferredWidth(106); 
            }else if(i==22){
                column.setPreferredWidth(165); 
            }else if(i==23){
                column.setPreferredWidth(90); 
            }else if(i==24){
                column.setPreferredWidth(150); 
            }else if(i==25){
                column.setPreferredWidth(80); 
            }else if(i==26){
                column.setPreferredWidth(78); 
            }else if(i==27){
                column.setPreferredWidth(138); 
            }else if(i==28){
                column.setPreferredWidth(104); 
            }else if(i==29){
                column.setPreferredWidth(162); 
            }else if(i==30){
                column.setPreferredWidth(82); 
            }else if(i==31){
                column.setPreferredWidth(140); 
            }else if(i==32){
                column.setPreferredWidth(113); 
            }else if(i==33){
                column.setPreferredWidth(170); 
            }else if(i==34){
                column.setPreferredWidth(110); 
            }else if(i==35){
                column.setPreferredWidth(168); 
            }else if(i==36){
                column.setPreferredWidth(136); 
            }else if(i==37){
                column.setPreferredWidth(195); 
            }else if(i==38){
                column.setPreferredWidth(101); 
            }else if(i==39){
                column.setPreferredWidth(160); 
            }else if(i==40){
                column.setPreferredWidth(40); 
            }else if(i==41){
                column.setPreferredWidth(160); 
            }else if(i==42){
                column.setPreferredWidth(68); 
            }else if(i==43){
                column.setPreferredWidth(120); 
            }else if(i==44){
                column.setPreferredWidth(170); 
            }else if(i==45){
                column.setPreferredWidth(73); 
            }else if(i==46){
                column.setPreferredWidth(195); 
            }else if(i==47){
                column.setPreferredWidth(172); 
            }else if(i==48){
                column.setPreferredWidth(64); 
            }else if(i==49){
                column.setPreferredWidth(124); 
            }else if(i==50){
                column.setPreferredWidth(49); 
            }else if(i==51){
                column.setPreferredWidth(108); 
            }else if(i==52){
                column.setPreferredWidth(64); 
            }else if(i==53){
                column.setPreferredWidth(123); 
            }else if(i==54){
                column.setPreferredWidth(50); 
            }else if(i==55){
                column.setPreferredWidth(36); 
            }else if(i==56){
                column.setPreferredWidth(36); 
            }else if(i==57){
                column.setPreferredWidth(36); 
            }else if(i==58){
                column.setPreferredWidth(36); 
            }else if(i==59){
                column.setPreferredWidth(73); 
            }else if(i==60){
                column.setPreferredWidth(131); 
            }else if(i==61){
                column.setPreferredWidth(61); 
            }else if(i==62){
                column.setPreferredWidth(39); 
            }else if(i==63){
                column.setPreferredWidth(82); 
            }else if(i==64){
                column.setPreferredWidth(82); 
            }else if(i==65){
                column.setPreferredWidth(111); 
            }else if(i==66){
                column.setPreferredWidth(87); 
            }else if(i==67){
                column.setPreferredWidth(107); 
            }else if(i==68){
                column.setPreferredWidth(110); 
            }else if(i==69){
                column.setPreferredWidth(56); 
            }else if(i==70){
                column.setPreferredWidth(70); 
            }else if(i==71){
                column.setPreferredWidth(86); 
            }else if(i==72){
                column.setPreferredWidth(86); 
            }else if(i==73){
                column.setPreferredWidth(125); 
            }else if(i==74){
                column.setPreferredWidth(35); 
            }else if(i==75){
                column.setPreferredWidth(35); 
            }else if(i==76){
                column.setPreferredWidth(45); 
            }else if(i==77){
                column.setPreferredWidth(105); 
            }else if(i==78){
                column.setPreferredWidth(127); 
            }else if(i==79){
                column.setPreferredWidth(76); 
            }else if(i==80){
                column.setPreferredWidth(40); 
            }else if(i==81){
                column.setPreferredWidth(76); 
            }else if(i==82){
                column.setPreferredWidth(40); 
            }else if(i==83){
                column.setPreferredWidth(57); 
            }else if(i==84){
                column.setPreferredWidth(85); 
            }else if(i==85){
                column.setPreferredWidth(85); 
            }else if(i==86){
                column.setPreferredWidth(85); 
            }else if(i==87){
                column.setPreferredWidth(205); 
            }else if(i==88){
                column.setPreferredWidth(71); 
            }else if(i==89){
                column.setPreferredWidth(67); 
            }else if(i==90){
                column.setPreferredWidth(88); 
            }else if(i==91){
                column.setPreferredWidth(88); 
            }else if(i==92){
                column.setPreferredWidth(88); 
            }else if(i==93){
                column.setPreferredWidth(88); 
            }else if(i==94){
                column.setPreferredWidth(88); 
            }else if(i==95){
                column.setPreferredWidth(53); 
            }else if(i==96){
                column.setPreferredWidth(112); 
            }else if(i==97){
                column.setPreferredWidth(81); 
            }else if(i==98){
                column.setPreferredWidth(141); 
            }else if(i==99){
                column.setPreferredWidth(75); 
            }else if(i==100){
                column.setPreferredWidth(135); 
            }else if(i==101){
                column.setPreferredWidth(115); 
            }else if(i==102){
                column.setPreferredWidth(108); 
            }else if(i==103){
                column.setPreferredWidth(181); 
            }else if(i==104){
                column.setPreferredWidth(67); 
            }else if(i==105){
                column.setPreferredWidth(90); 
            }else if(i==106){
                column.setPreferredWidth(106); 
            }else if(i==107){
                column.setPreferredWidth(135); 
            }else if(i==108){
                column.setPreferredWidth(54); 
            }else if(i==109){
                column.setPreferredWidth(60); 
            }else if(i==110){
                column.setPreferredWidth(181); 
            }else if(i==111){
                column.setPreferredWidth(102); 
            }else if(i==112){
                column.setPreferredWidth(175); 
            }else if(i==113){
                column.setPreferredWidth(70); 
            }else if(i==114){
                column.setPreferredWidth(108); 
            }else if(i==115){
                column.setPreferredWidth(86); 
            }else if(i==116){
                column.setPreferredWidth(58); 
            }else if(i==117){
                column.setPreferredWidth(108); 
            }else if(i==118){
                column.setPreferredWidth(178); 
            }else if(i==119){
                column.setPreferredWidth(178); 
            }else if(i==120){
                column.setPreferredWidth(76); 
            }else if(i==121){
                column.setPreferredWidth(135); 
            }else if(i==122){
                column.setPreferredWidth(175); 
            }else if(i==123){
                column.setPreferredWidth(67); 
            }else if(i==124){
                column.setPreferredWidth(107); 
            }else if(i==125){
                column.setPreferredWidth(81); 
            }else if(i==126){
                column.setPreferredWidth(177); 
            }else if(i==127){
                column.setPreferredWidth(159); 
            }else if(i==128){
                column.setPreferredWidth(157); 
            }else if(i==129){
                column.setPreferredWidth(90); 
            }else if(i==130){
                column.setPreferredWidth(150); 
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

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KeluhanUtama.setDocument(new batasInput((int)500).getKata(KeluhanUtama));
        BMI.setDocument(new batasInput((byte)8).getKata(BMI));
        RKDKeluhan.setDocument(new batasInput((int)500).getKata(RKDKeluhan));
        KetPutusObat.setDocument(new batasInput((int)50).getKata(KetPutusObat));
        KetMasalahEkonomi.setDocument(new batasInput((int)50).getKata(KetMasalahEkonomi));
        KetMasalahFisik.setDocument(new batasInput((int)50).getKata(KetMasalahFisik));
        KetMasalahPsikososial.setDocument(new batasInput((int)50).getKata(KetMasalahPsikososial));
        KetRHKeluarga.setDocument(new batasInput((int)50).getKata(KetRHKeluarga));
        KetRBDIde.setDocument(new batasInput((int)50).getKata(KetRBDIde));
        KetRBDRencana.setDocument(new batasInput((int)50).getKata(KetRBDRencana));
        KetRBDAlat.setDocument(new batasInput((int)50).getKata(KetRBDAlat));
        KetRBDPercobaan.setDocument(new batasInput((int)15).getKata(KetRBDPercobaan));
        KetRBDKeinginan.setDocument(new batasInput((int)100).getKata(KetRBDKeinginan));
        KetRPOPenggunaan.setDocument(new batasInput((int)20).getKata(KetRPOPenggunaan));
        KetRPOEfekSamping.setDocument(new batasInput((int)20).getKata(KetRPOEfekSamping));
        KetRPONapza.setDocument(new batasInput((int)25).getKata(KetRPONapza));
        KetLamaPemakaian.setDocument(new batasInput((byte)8).getKata(KetLamaPemakaian));
        KetCaraPemakaian.setDocument(new batasInput((int)15).getKata(KetCaraPemakaian));
        KetLatarBelakangPemakaian.setDocument(new batasInput((int)60).getKata(KetLatarBelakangPemakaian));
        KetPenggunaanObatLainnya.setDocument(new batasInput((int)20).getKata(KetPenggunaanObatLainnya));
        KetAlergiObat.setDocument(new batasInput((int)25).getKata(KetAlergiObat));
        KetMerokok.setDocument(new batasInput((int)25).getKata(KetMerokok));
        KetMinumKopi.setDocument(new batasInput((int)25).getKata(KetMinumKopi));       
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        GCS.setDocument(new batasInput((byte)5).getKata(GCS));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        KetKeluhanFisik.setDocument(new batasInput((int)100).getKata(KetKeluhanFisik));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        KetLaporResikoJatuh.setDocument(new batasInput((int)15).getKata(KetLaporResikoJatuh));
        KetAlasanPenggunaan.setDocument(new batasInput((int)65).getKata(KetAlasanPenggunaan));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        KetDokter.setDocument(new batasInput((int)15).getKata(KetDokter));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        RKDSakitSejak.setDocument(new batasInput((byte)5).getKata(RKDSakitSejak));
        KetLaporStatusNutrisi.setDocument(new batasInput((int)15).getKata(KetLaporStatusNutrisi));
        KetADLHobi.setDocument(new batasInput((int)50).getKata(KetADLHobi));
        KetADLSosialisasi.setDocument(new batasInput((int)50).getKata(KetADLSosialisasi));
        KetADLKegiatan.setDocument(new batasInput((int)50).getKata(KetADLKegiatan));
        KetSKPersepsi.setDocument(new batasInput((int)70).getKata(KetSKPersepsi));
        KetSKWaham.setDocument(new batasInput((int)100).getKata(KetSKWaham));
        KetSKDayaTilikDiri.setDocument(new batasInput((int)100).getKata(KetSKDayaTilikDiri));
        KetKKPembelajaranLainnya.setDocument(new batasInput((int)50).getKata(KetKKPembelajaranLainnya));
        KetKKPenerjamahLainnya.setDocument(new batasInput((int)50).getKata(KetKKPenerjamahLainnya));
        KetKKKebutuhanEdukasi.setDocument(new batasInput((int)50).getKata(KetKKKebutuhanEdukasi));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
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
        TanggalRegistrasi = new widget.TextBox();
        Agama = new widget.TextBox();
        Bahasa = new widget.TextBox();
        CacatFisik = new widget.TextBox();
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
        KetKeluhanFisik = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RKDKeluhan = new widget.TextArea();
        KetKKPembelajaranLainnya = new widget.TextBox();
        jLabel54 = new widget.Label();
        FPMasalahPsikososial = new widget.ComboBox();
        FPPutusObat = new widget.ComboBox();
        KetMasalahPsikososial = new widget.TextBox();
        jLabel60 = new widget.Label();
        FPEkonomi = new widget.ComboBox();
        SG2 = new widget.ComboBox();
        SG1 = new widget.ComboBox();
        Nilai1 = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Nilai2 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jLabel92 = new widget.Label();
        TotalHasil = new widget.TextBox();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel93 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel96 = new widget.Label();
        jLabel63 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel74 = new widget.Label();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator10 = new javax.swing.JSeparator();
        LaporStatusNutrisi = new widget.ComboBox();
        jLabel98 = new widget.Label();
        KetLaporStatusNutrisi = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel79 = new widget.Label();
        RKDBerobat = new widget.ComboBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        PFKeluhanFisik = new widget.ComboBox();
        jLabel101 = new widget.Label();
        RHKeluarga = new widget.ComboBox();
        KetRHKeluarga = new widget.TextBox();
        jLabel91 = new widget.Label();
        RKDSakitSejak = new widget.TextBox();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        KetPutusObat = new widget.TextBox();
        KetMasalahEkonomi = new widget.TextBox();
        jLabel105 = new widget.Label();
        RKDHasilPengobatan = new widget.ComboBox();
        jLabel106 = new widget.Label();
        ResikoBunuhDiri = new widget.ComboBox();
        RBDIde = new widget.ComboBox();
        KetRBDIde = new widget.TextBox();
        jLabel109 = new widget.Label();
        RBDAlat = new widget.ComboBox();
        KetRBDRencana = new widget.TextBox();
        jLabel111 = new widget.Label();
        KetRBDAlat = new widget.TextBox();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        RBDPercobaan = new widget.ComboBox();
        KetRBDPercobaan = new widget.TextBox();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        RBDKeinginan = new widget.ComboBox();
        KetRBDKeinginan = new widget.TextBox();
        jLabel117 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel61 = new widget.Label();
        FPMasalahFisik = new widget.ComboBox();
        KetMasalahFisik = new widget.TextBox();
        jLabel107 = new widget.Label();
        RPOPenggunaan = new widget.ComboBox();
        KetRPOPenggunaan = new widget.TextBox();
        jLabel110 = new widget.Label();
        RPOEfekSamping = new widget.ComboBox();
        KetRPOEfekSamping = new widget.TextBox();
        jLabel118 = new widget.Label();
        RPONapza = new widget.ComboBox();
        KetRPONapza = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        KetLamaPemakaian = new widget.TextBox();
        jLabel122 = new widget.Label();
        RPOPenggunaanObatLainnya = new widget.ComboBox();
        KetPenggunaanObatLainnya = new widget.TextBox();
        jLabel124 = new widget.Label();
        KetAlasanPenggunaan = new widget.TextBox();
        jLabel126 = new widget.Label();
        RPOAlergiObat = new widget.ComboBox();
        KetAlergiObat = new widget.TextBox();
        jLabel130 = new widget.Label();
        RPOMerokok = new widget.ComboBox();
        KetMerokok = new widget.TextBox();
        jLabel132 = new widget.Label();
        RPOMinumKopi = new widget.ComboBox();
        KetMinumKopi = new widget.TextBox();
        jLabel134 = new widget.Label();
        KetCaraPemakaian = new widget.TextBox();
        jLabel135 = new widget.Label();
        KetLatarBelakangPemakaian = new widget.TextBox();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        ADLMandi = new widget.ComboBox();
        jLabel59 = new widget.Label();
        ADLBerpakaian = new widget.ComboBox();
        jLabel62 = new widget.Label();
        ADLMakan = new widget.ComboBox();
        jLabel69 = new widget.Label();
        ADLBab = new widget.ComboBox();
        jLabel76 = new widget.Label();
        ADLBak = new widget.ComboBox();
        jLabel77 = new widget.Label();
        ADLSosialisasi = new widget.ComboBox();
        ADLHobi = new widget.ComboBox();
        jLabel125 = new widget.Label();
        KKBahasaIsyarat = new widget.ComboBox();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        KetADLHobi = new widget.TextBox();
        KetADLKegiatan = new widget.TextBox();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel138 = new widget.Label();
        SKPenampilan = new widget.ComboBox();
        jLabel139 = new widget.Label();
        SKPembicaraan = new widget.ComboBox();
        jLabel140 = new widget.Label();
        SKAktifitasMotorik = new widget.ComboBox();
        jLabel141 = new widget.Label();
        SKAlamPerasaan = new widget.ComboBox();
        jLabel142 = new widget.Label();
        SKInteraksi = new widget.ComboBox();
        jLabel143 = new widget.Label();
        SKAfek = new widget.ComboBox();
        jLabel144 = new widget.Label();
        SKPersepsi = new widget.ComboBox();
        jLabel145 = new widget.Label();
        SKProsesPikir = new widget.ComboBox();
        jLabel146 = new widget.Label();
        SKIsiPikir = new widget.ComboBox();
        jLabel147 = new widget.Label();
        KKKebutuhanEdukasi = new widget.ComboBox();
        jLabel148 = new widget.Label();
        SKMemori = new widget.ComboBox();
        jLabel149 = new widget.Label();
        SKKonsentrasi = new widget.ComboBox();
        jLabel150 = new widget.Label();
        SKGangguanRingan = new widget.ComboBox();
        jLabel151 = new widget.Label();
        SKDayaTilikDiri = new widget.ComboBox();
        jLabel152 = new widget.Label();
        KetADLSosialisasi = new widget.TextBox();
        SKWaham = new widget.ComboBox();
        jLabel153 = new widget.Label();
        KetSKPersepsi = new widget.TextBox();
        KetSKWaham = new widget.TextBox();
        jLabel57 = new widget.Label();
        jLabel154 = new widget.Label();
        ADLKegiatan = new widget.ComboBox();
        jLabel155 = new widget.Label();
        SKTingkatKesadaranOrientasi = new widget.ComboBox();
        KetSKDayaTilikDiri = new widget.TextBox();
        jLabel156 = new widget.Label();
        jLabel157 = new widget.Label();
        KKPembelajaran = new widget.ComboBox();
        jLabel158 = new widget.Label();
        KKPenerjamah = new widget.ComboBox();
        jLabel159 = new widget.Label();
        KetKKPembelajaran = new widget.ComboBox();
        KetKKKebutuhanEdukasi = new widget.TextBox();
        jLabel160 = new widget.Label();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        KetKKPenerjamahLainnya = new widget.TextBox();
        jLabel161 = new widget.Label();
        jLabel162 = new widget.Label();
        jLabel163 = new widget.Label();
        SKOrientasi = new widget.ComboBox();
        RBDRencana = new widget.ComboBox();
        jLabel164 = new widget.Label();
        jLabel133 = new widget.Label();
        jLabel165 = new widget.Label();
        jSeparator9 = new javax.swing.JSeparator();
        Nyeri = new widget.ComboBox();
        jLabel80 = new widget.Label();
        Provokes = new widget.ComboBox();
        KetProvokes = new widget.TextBox();
        jLabel81 = new widget.Label();
        Quality = new widget.ComboBox();
        KetQuality = new widget.TextBox();
        jLabel90 = new widget.Label();
        jLabel83 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel67 = new widget.Label();
        Menyebar = new widget.ComboBox();
        jLabel88 = new widget.Label();
        jLabel85 = new widget.Label();
        SkalaNyeri = new widget.ComboBox();
        jLabel87 = new widget.Label();
        Durasi = new widget.TextBox();
        jLabel84 = new widget.Label();
        jLabel89 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel86 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        jLabel64 = new widget.Label();
        KetDokter = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel65 = new widget.Label();
        ResikoJatuh = new widget.ComboBox();
        jLabel66 = new widget.Label();
        BJM = new widget.ComboBox();
        jLabel70 = new widget.Label();
        MSA = new widget.ComboBox();
        jLabel68 = new widget.Label();
        HasilResikoJatuh = new widget.ComboBox();
        jLabel97 = new widget.Label();
        LaporResikoJatuh = new widget.ComboBox();
        jLabel71 = new widget.Label();
        KetLaporResikoJatuh = new widget.TextBox();
        Scroll6 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        TabRencanaKeperawatan = new javax.swing.JTabbedPane();
        panelBiasa1 = new widget.PanelBiasa();
        Scroll8 = new widget.ScrollPane();
        tbRencanaKeperawatan = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        BtnTambahRencana = new widget.Button();
        BtnAllRencana = new widget.Button();
        BtnCariRencana = new widget.Button();
        TCariRencana = new widget.TextBox();
        label13 = new widget.Label();
        BtnTambahMasalah = new widget.Button();
        BtnAllMasalah = new widget.Button();
        BtnCariMasalah = new widget.Button();
        TCariMasalah = new widget.TextBox();
        label12 = new widget.Label();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        Agama.setHighlighter(null);
        Agama.setName("Agama"); // NOI18N

        Bahasa.setHighlighter(null);
        Bahasa.setName("Bahasa"); // NOI18N

        CacatFisik.setHighlighter(null);
        CacatFisik.setName("CacatFisik"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Awal Keperawatan Rawat Jalan Psikiatri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1793));
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

        jLabel9.setText("Riwayat Penyakit Dahulu :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 140, 166, 23);

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
        jLabel12.setBounds(0, 910, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 910, 55, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(132, 910, 20, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(198, 910, 55, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(164, 910, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(313, 660, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(250, 660, 60, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(206, 660, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(566, 660, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(610, 660, 60, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 660, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(74, 660, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(673, 660, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(137, 660, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(256, 910, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(485, 660, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(422, 660, 60, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 660, 40, 23);

        jLabel14.setText("BMI :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(286, 910, 40, 23);

        BMI.setFocusTraversalPolicyProvider(true);
        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BMIKeyPressed(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(330, 910, 65, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kg/m²");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(398, 910, 40, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        KetKeluhanFisik.setFocusTraversalPolicyProvider(true);
        KetKeluhanFisik.setName("KetKeluhanFisik"); // NOI18N
        KetKeluhanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeluhanFisikKeyPressed(evt);
            }
        });
        FormInput.add(KetKeluhanFisik);
        KetKeluhanFisik.setBounds(295, 630, 559, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("2. Apakah nafsu makan berkurang, karena tidak nafsu makan ?");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(40, 990, 310, 23);

        jLabel50.setText("Jika Ya, Sebutkan :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(316, 320, 110, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("VI. PENGKAJIAN TINGKAT NYERI");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(10, 690, 160, 23);

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
        jLabel53.setText("V. PEMERIKSAAN FISIK");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 610, 180, 23);

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
        scrollPane1.setBounds(124, 90, 730, 43);

        jLabel30.setText("Keluhan Utama :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 90, 120, 20);

        jLabel31.setText("Sakit Sejak :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(510, 140, 70, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RKDKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RKDKeluhan.setColumns(20);
        RKDKeluhan.setRows(5);
        RKDKeluhan.setName("RKDKeluhan"); // NOI18N
        RKDKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RKDKeluhanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RKDKeluhan);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(170, 140, 330, 53);

        KetKKPembelajaranLainnya.setFocusTraversalPolicyProvider(true);
        KetKKPembelajaranLainnya.setName("KetKKPembelajaranLainnya"); // NOI18N
        KetKKPembelajaranLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKKPembelajaranLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KetKKPembelajaranLainnya);
        KetKKPembelajaranLainnya.setBounds(571, 1510, 283, 23);

        jLabel54.setText("Masalah Psikososial :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(426, 250, 120, 23);

        FPMasalahPsikososial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FPMasalahPsikososial.setName("FPMasalahPsikososial"); // NOI18N
        FPMasalahPsikososial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FPMasalahPsikososialKeyPressed(evt);
            }
        });
        FormInput.add(FPMasalahPsikososial);
        FPMasalahPsikososial.setBounds(550, 250, 80, 23);

        FPPutusObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FPPutusObat.setName("FPPutusObat"); // NOI18N
        FPPutusObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FPPutusObatKeyPressed(evt);
            }
        });
        FormInput.add(FPPutusObat);
        FPPutusObat.setBounds(115, 220, 80, 23);

        KetMasalahPsikososial.setFocusTraversalPolicyProvider(true);
        KetMasalahPsikososial.setName("KetMasalahPsikososial"); // NOI18N
        KetMasalahPsikososial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMasalahPsikososialKeyPressed(evt);
            }
        });
        FormInput.add(KetMasalahPsikososial);
        KetMasalahPsikososial.setBounds(634, 250, 220, 23);

        jLabel60.setText("Masalah Ekonomi :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(426, 220, 120, 23);

        FPEkonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FPEkonomi.setName("FPEkonomi"); // NOI18N
        FPEkonomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FPEkonomiActionPerformed(evt);
            }
        });
        FPEkonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FPEkonomiKeyPressed(evt);
            }
        });
        FormInput.add(FPEkonomi);
        FPEkonomi.setBounds(550, 220, 80, 23);

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
        SG2.setBounds(520, 990, 160, 23);

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
        SG1.setBounds(520, 960, 160, 23);

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
        Nilai1.setBounds(774, 960, 80, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(695, 1020, 75, 23);

        Nilai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1" }));
        Nilai2.setName("Nilai2"); // NOI18N
        Nilai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nilai2KeyPressed(evt);
            }
        });
        FormInput.add(Nilai2);
        Nilai2.setBounds(774, 990, 80, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1. Apakah ada penurunan berat badan yang tidak diinginkan selama 6 bulan terakhir ?");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 960, 440, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(695, 990, 75, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(774, 1020, 80, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2024 02:08:59" }));
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
        jLabel28.setBounds(700, 660, 90, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(794, 660, 60, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("VII. STATUS NUTRISI");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(10, 890, 180, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("XII. KEBUTUHAN KOMUNIKASI DAN EDUKASI");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 1490, 230, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("II. FAKTOR PRESIPITASI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 200, 120, 23);
        jLabel96.getAccessibleContext().setAccessibleDescription("");

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("IX. PENGKAJIAN RESIKO JATUH");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(10, 1050, 240, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("VIII. SKRINING GIZI");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(10, 940, 120, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 710, 320, 130);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 940, 880, 1);

        LaporStatusNutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        LaporStatusNutrisi.setName("LaporStatusNutrisi"); // NOI18N
        LaporStatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporStatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(LaporStatusNutrisi);
        LaporStatusNutrisi.setBounds(584, 910, 80, 23);

        jLabel98.setText("Dilaporkan Kepada DPJP ?");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(450, 910, 130, 23);

        KetLaporStatusNutrisi.setFocusTraversalPolicyProvider(true);
        KetLaporStatusNutrisi.setName("KetLaporStatusNutrisi"); // NOI18N
        KetLaporStatusNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporStatusNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(KetLaporStatusNutrisi);
        KetLaporStatusNutrisi.setBounds(774, 910, 80, 23);

        jLabel78.setText("Jam Dilaporkan :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(670, 910, 100, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("I. RIWAYAT KESEHATAN");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 70, 180, 23);

        jLabel79.setText("Berobat :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(667, 140, 60, 23);

        RKDBerobat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya, Alternatif", "Ya, RS", "Ya, Puskesmas" }));
        RKDBerobat.setName("RKDBerobat"); // NOI18N
        RKDBerobat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RKDBerobatKeyPressed(evt);
            }
        });
        FormInput.add(RKDBerobat);
        RKDBerobat.setBounds(731, 140, 123, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 440, 880, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 200, 880, 1);
        jSeparator12.getAccessibleContext().setAccessibleName("");

        PFKeluhanFisik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PFKeluhanFisik.setName("PFKeluhanFisik"); // NOI18N
        PFKeluhanFisik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PFKeluhanFisikActionPerformed(evt);
            }
        });
        PFKeluhanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PFKeluhanFisikKeyPressed(evt);
            }
        });
        FormInput.add(PFKeluhanFisik);
        PFKeluhanFisik.setBounds(211, 630, 80, 23);

        jLabel101.setText("Apakah Terdapat Keluhan Fisik :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 630, 207, 23);

        RHKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RHKeluarga.setName("RHKeluarga"); // NOI18N
        RHKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RHKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(RHKeluarga);
        RHKeluarga.setBounds(232, 320, 80, 23);

        KetRHKeluarga.setFocusTraversalPolicyProvider(true);
        KetRHKeluarga.setName("KetRHKeluarga"); // NOI18N
        KetRHKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRHKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(KetRHKeluarga);
        KetRHKeluarga.setBounds(430, 320, 424, 23);

        jLabel91.setText("Keluarga Dengan Penyakit Jiwa :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(0, 320, 228, 23);

        RKDSakitSejak.setFocusTraversalPolicyProvider(true);
        RKDSakitSejak.setName("RKDSakitSejak"); // NOI18N
        RKDSakitSejak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RKDSakitSejakKeyPressed(evt);
            }
        });
        FormInput.add(RKDSakitSejak);
        RKDSakitSejak.setBounds(584, 140, 80, 23);

        jLabel103.setText("Resiko Bunuh Diri :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 350, 131, 23);

        jLabel104.setText("Putus Obat :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(0, 220, 111, 23);

        KetPutusObat.setFocusTraversalPolicyProvider(true);
        KetPutusObat.setName("KetPutusObat"); // NOI18N
        KetPutusObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPutusObatKeyPressed(evt);
            }
        });
        FormInput.add(KetPutusObat);
        KetPutusObat.setBounds(199, 220, 220, 23);

        KetMasalahEkonomi.setFocusTraversalPolicyProvider(true);
        KetMasalahEkonomi.setName("KetMasalahEkonomi"); // NOI18N
        KetMasalahEkonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMasalahEkonomiKeyPressed(evt);
            }
        });
        FormInput.add(KetMasalahEkonomi);
        KetMasalahEkonomi.setBounds(634, 220, 220, 23);

        jLabel105.setText("Hasil Pengobatan :");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(537, 170, 190, 23);

        RKDHasilPengobatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Berhasil", "Tidak Berhasil" }));
        RKDHasilPengobatan.setName("RKDHasilPengobatan"); // NOI18N
        RKDHasilPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RKDHasilPengobatanKeyPressed(evt);
            }
        });
        FormInput.add(RKDHasilPengobatan);
        RKDHasilPengobatan.setBounds(731, 170, 123, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("III. FAKTOR RISIKO");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 280, 160, 23);

        ResikoBunuhDiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ResikoBunuhDiri.setName("ResikoBunuhDiri"); // NOI18N
        ResikoBunuhDiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikoBunuhDiriKeyPressed(evt);
            }
        });
        FormInput.add(ResikoBunuhDiri);
        ResikoBunuhDiri.setBounds(135, 350, 80, 23);

        RBDIde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RBDIde.setName("RBDIde"); // NOI18N
        RBDIde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RBDIdeKeyPressed(evt);
            }
        });
        FormInput.add(RBDIde);
        RBDIde.setBounds(279, 350, 80, 23);

        KetRBDIde.setFocusTraversalPolicyProvider(true);
        KetRBDIde.setName("KetRBDIde"); // NOI18N
        KetRBDIde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRBDIdeKeyPressed(evt);
            }
        });
        FormInput.add(KetRBDIde);
        KetRBDIde.setBounds(363, 350, 160, 23);

        jLabel109.setText("Ada Ide :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(215, 350, 60, 23);

        RBDAlat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RBDAlat.setName("RBDAlat"); // NOI18N
        RBDAlat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RBDAlatKeyPressed(evt);
            }
        });
        FormInput.add(RBDAlat);
        RBDAlat.setBounds(179, 380, 80, 23);

        KetRBDRencana.setFocusTraversalPolicyProvider(true);
        KetRBDRencana.setName("KetRBDRencana"); // NOI18N
        KetRBDRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRBDRencanaKeyPressed(evt);
            }
        });
        FormInput.add(KetRBDRencana);
        KetRBDRencana.setBounds(694, 350, 160, 23);

        jLabel111.setText("Ada Rencana :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(526, 350, 80, 23);

        KetRBDAlat.setFocusTraversalPolicyProvider(true);
        KetRBDAlat.setName("KetRBDAlat"); // NOI18N
        KetRBDAlat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRBDAlatKeyPressed(evt);
            }
        });
        FormInput.add(KetRBDAlat);
        KetRBDAlat.setBounds(263, 380, 160, 23);

        jLabel113.setText("Mempersiapkan Alat :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(0, 380, 175, 23);

        jLabel114.setText("Kapan :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(700, 380, 50, 23);

        RBDPercobaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RBDPercobaan.setName("RBDPercobaan"); // NOI18N
        RBDPercobaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RBDPercobaanKeyPressed(evt);
            }
        });
        FormInput.add(RBDPercobaan);
        RBDPercobaan.setBounds(622, 380, 80, 23);

        KetRBDPercobaan.setFocusTraversalPolicyProvider(true);
        KetRBDPercobaan.setName("KetRBDPercobaan"); // NOI18N
        KetRBDPercobaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRBDPercobaanKeyPressed(evt);
            }
        });
        FormInput.add(KetRBDPercobaan);
        KetRBDPercobaan.setBounds(754, 380, 100, 23);

        jLabel115.setText("Pernah Mencoba Perilaku Bunuh Diri :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(428, 380, 190, 23);

        jLabel116.setText("Jika Ya, Dengan Cara :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(390, 410, 120, 23);

        RBDKeinginan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RBDKeinginan.setName("RBDKeinginan"); // NOI18N
        RBDKeinginan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RBDKeinginanKeyPressed(evt);
            }
        });
        FormInput.add(RBDKeinginan);
        RBDKeinginan.setBounds(306, 410, 80, 23);

        KetRBDKeinginan.setFocusTraversalPolicyProvider(true);
        KetRBDKeinginan.setName("KetRBDKeinginan"); // NOI18N
        KetRBDKeinginan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRBDKeinginanKeyPressed(evt);
            }
        });
        FormInput.add(KetRBDKeinginan);
        KetRBDKeinginan.setBounds(514, 410, 340, 23);

        jLabel117.setText("Saat Ini Masih Ada Keinginan Untuk Bunuh Diri :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(0, 410, 302, 23);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 280, 880, 1);

        jLabel61.setText("Masalah Fisik :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 250, 111, 23);

        FPMasalahFisik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FPMasalahFisik.setName("FPMasalahFisik"); // NOI18N
        FPMasalahFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FPMasalahFisikKeyPressed(evt);
            }
        });
        FormInput.add(FPMasalahFisik);
        FPMasalahFisik.setBounds(115, 250, 80, 23);

        KetMasalahFisik.setFocusTraversalPolicyProvider(true);
        KetMasalahFisik.setName("KetMasalahFisik"); // NOI18N
        KetMasalahFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMasalahFisikKeyPressed(evt);
            }
        });
        FormInput.add(KetMasalahFisik);
        KetMasalahFisik.setBounds(199, 250, 220, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("IV. RIWAYAT PENGOBATAN");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(10, 440, 180, 23);

        RPOPenggunaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOPenggunaan.setName("RPOPenggunaan"); // NOI18N
        RPOPenggunaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOPenggunaanKeyPressed(evt);
            }
        });
        FormInput.add(RPOPenggunaan);
        RPOPenggunaan.setBounds(185, 460, 80, 23);

        KetRPOPenggunaan.setFocusTraversalPolicyProvider(true);
        KetRPOPenggunaan.setName("KetRPOPenggunaan"); // NOI18N
        KetRPOPenggunaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRPOPenggunaanKeyPressed(evt);
            }
        });
        FormInput.add(KetRPOPenggunaan);
        KetRPOPenggunaan.setBounds(269, 460, 160, 23);

        jLabel110.setText("Penggunaan Obat Psikiatri :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(0, 460, 181, 23);

        RPOEfekSamping.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOEfekSamping.setName("RPOEfekSamping"); // NOI18N
        RPOEfekSamping.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOEfekSampingKeyPressed(evt);
            }
        });
        FormInput.add(RPOEfekSamping);
        RPOEfekSamping.setBounds(185, 490, 80, 23);

        KetRPOEfekSamping.setFocusTraversalPolicyProvider(true);
        KetRPOEfekSamping.setName("KetRPOEfekSamping"); // NOI18N
        KetRPOEfekSamping.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRPOEfekSampingKeyPressed(evt);
            }
        });
        FormInput.add(KetRPOEfekSamping);
        KetRPOEfekSamping.setBounds(269, 490, 160, 23);

        jLabel118.setText("Efek Samping Obat Psikatri :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(0, 490, 181, 23);

        RPONapza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPONapza.setName("RPONapza"); // NOI18N
        RPONapza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPONapzaKeyPressed(evt);
            }
        });
        FormInput.add(RPONapza);
        RPONapza.setBounds(185, 520, 80, 23);

        KetRPONapza.setFocusTraversalPolicyProvider(true);
        KetRPONapza.setName("KetRPONapza"); // NOI18N
        KetRPONapza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRPONapzaKeyPressed(evt);
            }
        });
        FormInput.add(KetRPONapza);
        KetRPONapza.setBounds(269, 520, 160, 23);

        jLabel120.setText("Riwayat Penggunaan Napza :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(0, 520, 181, 23);

        jLabel121.setText("Cara Pakai :");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(221, 550, 70, 23);

        KetLamaPemakaian.setFocusTraversalPolicyProvider(true);
        KetLamaPemakaian.setName("KetLamaPemakaian"); // NOI18N
        KetLamaPemakaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLamaPemakaianKeyPressed(evt);
            }
        });
        FormInput.add(KetLamaPemakaian);
        KetLamaPemakaian.setBounds(164, 550, 60, 23);

        jLabel122.setText("Lama :");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(0, 550, 160, 23);

        RPOPenggunaanObatLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOPenggunaanObatLainnya.setName("RPOPenggunaanObatLainnya"); // NOI18N
        RPOPenggunaanObatLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOPenggunaanObatLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(RPOPenggunaanObatLainnya);
        RPOPenggunaanObatLainnya.setBounds(600, 460, 80, 23);

        KetPenggunaanObatLainnya.setFocusTraversalPolicyProvider(true);
        KetPenggunaanObatLainnya.setName("KetPenggunaanObatLainnya"); // NOI18N
        KetPenggunaanObatLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPenggunaanObatLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KetPenggunaanObatLainnya);
        KetPenggunaanObatLainnya.setBounds(684, 460, 170, 23);

        jLabel124.setText("Penggunaan Obat Lainnya :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(456, 460, 140, 23);

        KetAlasanPenggunaan.setFocusTraversalPolicyProvider(true);
        KetAlasanPenggunaan.setName("KetAlasanPenggunaan"); // NOI18N
        KetAlasanPenggunaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAlasanPenggunaanKeyPressed(evt);
            }
        });
        FormInput.add(KetAlasanPenggunaan);
        KetAlasanPenggunaan.setBounds(634, 490, 220, 23);

        jLabel126.setText("Alasan Penggunaan :");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(500, 490, 130, 23);

        RPOAlergiObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOAlergiObat.setName("RPOAlergiObat"); // NOI18N
        RPOAlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOAlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(RPOAlergiObat);
        RPOAlergiObat.setBounds(600, 520, 80, 23);

        KetAlergiObat.setFocusTraversalPolicyProvider(true);
        KetAlergiObat.setName("KetAlergiObat"); // NOI18N
        KetAlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAlergiObatKeyPressed(evt);
            }
        });
        FormInput.add(KetAlergiObat);
        KetAlergiObat.setBounds(684, 520, 170, 23);

        jLabel130.setText("Riwayat Alergi Obat :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(456, 520, 140, 23);

        RPOMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOMerokok.setName("RPOMerokok"); // NOI18N
        RPOMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOMerokokKeyPressed(evt);
            }
        });
        FormInput.add(RPOMerokok);
        RPOMerokok.setBounds(600, 550, 80, 23);

        KetMerokok.setFocusTraversalPolicyProvider(true);
        KetMerokok.setName("KetMerokok"); // NOI18N
        KetMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KetMerokok);
        KetMerokok.setBounds(684, 550, 90, 23);

        jLabel132.setText("Merokok :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(456, 550, 140, 23);

        RPOMinumKopi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RPOMinumKopi.setName("RPOMinumKopi"); // NOI18N
        RPOMinumKopi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOMinumKopiKeyPressed(evt);
            }
        });
        FormInput.add(RPOMinumKopi);
        RPOMinumKopi.setBounds(600, 580, 80, 23);

        KetMinumKopi.setFocusTraversalPolicyProvider(true);
        KetMinumKopi.setName("KetMinumKopi"); // NOI18N
        KetMinumKopi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMinumKopiKeyPressed(evt);
            }
        });
        FormInput.add(KetMinumKopi);
        KetMinumKopi.setBounds(684, 580, 90, 23);

        jLabel134.setText(" Minum Kopi :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(456, 580, 140, 23);

        KetCaraPemakaian.setFocusTraversalPolicyProvider(true);
        KetCaraPemakaian.setName("KetCaraPemakaian"); // NOI18N
        KetCaraPemakaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetCaraPemakaianKeyPressed(evt);
            }
        });
        FormInput.add(KetCaraPemakaian);
        KetCaraPemakaian.setBounds(295, 550, 134, 23);

        jLabel135.setText("Latar Belakang :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(0, 580, 160, 23);

        KetLatarBelakangPemakaian.setFocusTraversalPolicyProvider(true);
        KetLatarBelakangPemakaian.setName("KetLatarBelakangPemakaian"); // NOI18N
        KetLatarBelakangPemakaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLatarBelakangPemakaianKeyPressed(evt);
            }
        });
        FormInput.add(KetLatarBelakangPemakaian);
        KetLatarBelakangPemakaian.setBounds(164, 580, 265, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 610, 880, 1);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 690, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 890, 880, 1);

        jLabel95.setText("Nilai :");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(695, 960, 75, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1050, 880, 1);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 1180, 880, 1);

        ADLMandi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Total" }));
        ADLMandi.setName("ADLMandi"); // NOI18N
        ADLMandi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLMandiKeyPressed(evt);
            }
        });
        FormInput.add(ADLMandi);
        ADLMandi.setBounds(79, 1200, 132, 23);

        jLabel59.setText("Mandi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 1200, 75, 23);

        ADLBerpakaian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Total" }));
        ADLBerpakaian.setName("ADLBerpakaian"); // NOI18N
        ADLBerpakaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLBerpakaianKeyPressed(evt);
            }
        });
        FormInput.add(ADLBerpakaian);
        ADLBerpakaian.setBounds(722, 1200, 132, 23);

        jLabel62.setText("Berpakaian :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(618, 1200, 100, 23);

        ADLMakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Total" }));
        ADLMakan.setName("ADLMakan"); // NOI18N
        ADLMakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLMakanKeyPressed(evt);
            }
        });
        FormInput.add(ADLMakan);
        ADLMakan.setBounds(722, 1230, 132, 23);

        jLabel69.setText("Makan/Minum :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(618, 1230, 100, 23);

        ADLBab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Total" }));
        ADLBab.setName("ADLBab"); // NOI18N
        ADLBab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLBabKeyPressed(evt);
            }
        });
        FormInput.add(ADLBab);
        ADLBab.setBounds(79, 1260, 132, 23);

        jLabel76.setText("BAB :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 1260, 75, 23);

        ADLBak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Bantuan Minimal", "Bantuan Total" }));
        ADLBak.setName("ADLBak"); // NOI18N
        ADLBak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLBakKeyPressed(evt);
            }
        });
        FormInput.add(ADLBak);
        ADLBak.setBounds(79, 1230, 132, 23);

        jLabel77.setText("BAK :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 1230, 75, 23);

        ADLSosialisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ADLSosialisasi.setName("ADLSosialisasi"); // NOI18N
        ADLSosialisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLSosialisasiKeyPressed(evt);
            }
        });
        FormInput.add(ADLSosialisasi);
        ADLSosialisasi.setBounds(323, 1200, 80, 23);

        ADLHobi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ADLHobi.setName("ADLHobi"); // NOI18N
        ADLHobi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLHobiKeyPressed(evt);
            }
        });
        FormInput.add(ADLHobi);
        ADLHobi.setBounds(323, 1230, 80, 23);

        jLabel125.setText("Melakukan Hobi :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(219, 1230, 100, 23);

        KKBahasaIsyarat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KKBahasaIsyarat.setName("KKBahasaIsyarat"); // NOI18N
        KKBahasaIsyarat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KKBahasaIsyaratKeyPressed(evt);
            }
        });
        FormInput.add(KKBahasaIsyarat);
        KKBahasaIsyarat.setBounds(774, 1540, 80, 23);

        jLabel127.setText("Kegiatan RT :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(219, 1260, 100, 23);

        jLabel128.setText("Sosialisasi :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(219, 1200, 100, 23);

        KetADLHobi.setFocusTraversalPolicyProvider(true);
        KetADLHobi.setName("KetADLHobi"); // NOI18N
        KetADLHobi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetADLHobiKeyPressed(evt);
            }
        });
        FormInput.add(KetADLHobi);
        KetADLHobi.setBounds(407, 1230, 210, 23);

        KetADLKegiatan.setFocusTraversalPolicyProvider(true);
        KetADLKegiatan.setName("KetADLKegiatan"); // NOI18N
        KetADLKegiatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetADLKegiatanKeyPressed(evt);
            }
        });
        FormInput.add(KetADLKegiatan);
        KetADLKegiatan.setBounds(407, 1260, 210, 23);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(0, 1290, 880, 1);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("X. STATUS FUNGSIONAL");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(10, 1180, 230, 23);

        SKPenampilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersih", "Rapi", "Tidak Rapi", "Kotor", "Tidak Seperti Biasanya", "Pakaian Tidak Sesuai" }));
        SKPenampilan.setName("SKPenampilan"); // NOI18N
        SKPenampilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKPenampilanKeyPressed(evt);
            }
        });
        FormInput.add(SKPenampilan);
        SKPenampilan.setBounds(107, 1310, 160, 23);

        jLabel139.setText("Penampilan :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(0, 1310, 103, 23);

        SKPembicaraan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai", "Cepat", "Lambat", "Membisu", "Mendominasi", "Mengancam", "Inkoheren", "Apatis", "Keras", "Gagap", "Tidak Mampu Memulai Pembicaraan" }));
        SKPembicaraan.setName("SKPembicaraan"); // NOI18N
        SKPembicaraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKPembicaraanKeyPressed(evt);
            }
        });
        FormInput.add(SKPembicaraan);
        SKPembicaraan.setBounds(365, 1310, 225, 23);

        jLabel140.setText("Pembicaraan :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(271, 1310, 90, 23);

        SKAktifitasMotorik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Tegang", "Gelisah", "Lesuh", "Grimasem", "TIK", "Tremor", "Agitasi", "Konfulsif", "Melamun", "Sulit Diarahkan" }));
        SKAktifitasMotorik.setName("SKAktifitasMotorik"); // NOI18N
        SKAktifitasMotorik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKAktifitasMotorikKeyPressed(evt);
            }
        });
        FormInput.add(SKAktifitasMotorik);
        SKAktifitasMotorik.setBounds(380, 1340, 128, 23);

        jLabel141.setText("Aktifitas Motorik/Prilaku :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(236, 1340, 140, 23);

        SKAlamPerasaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai", "Marah", "Putus Asa", "Tertekan", "Sedih", "Labil", "Malu", "Khawatir", "Gembira Berlebihan", "Merasa Tidak Mampu", "Ketakutan", "Tidak Berguna" }));
        SKAlamPerasaan.setName("SKAlamPerasaan"); // NOI18N
        SKAlamPerasaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKAlamPerasaanKeyPressed(evt);
            }
        });
        FormInput.add(SKAlamPerasaan);
        SKAlamPerasaan.setBounds(699, 1310, 155, 23);

        jLabel142.setText("Alam Perasaan :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(605, 1310, 90, 23);

        SKInteraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kooperatif", "Tidak Kooperatif", "Bermusuhan", "Mudah Tersinggung", "Curiga", "Defensif", "Kontak Mata Kurang" }));
        SKInteraksi.setName("SKInteraksi"); // NOI18N
        SKInteraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKInteraksiKeyPressed(evt);
            }
        });
        FormInput.add(SKInteraksi);
        SKInteraksi.setBounds(699, 1340, 155, 23);

        jLabel143.setText("Interaksi Selama Wawancara :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(525, 1340, 170, 23);

        SKAfek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai", "Datar", "Tumpul", "Labil", "Tidak Sesuai" }));
        SKAfek.setName("SKAfek"); // NOI18N
        SKAfek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKAfekKeyPressed(evt);
            }
        });
        FormInput.add(SKAfek);
        SKAfek.setBounds(107, 1340, 115, 23);

        jLabel144.setText("Afek :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(0, 1340, 103, 23);

        SKPersepsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Halusinasi", "Pendengaran", "Penghidung", "Penglihatan", "Pengecapan", "Perabaan" }));
        SKPersepsi.setName("SKPersepsi"); // NOI18N
        SKPersepsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKPersepsiKeyPressed(evt);
            }
        });
        FormInput.add(SKPersepsi);
        SKPersepsi.setBounds(107, 1430, 115, 23);

        jLabel145.setText("Persepsi :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(0, 1430, 103, 23);

        SKProsesPikir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai", "Sirkumsial", "Kehilangan Asosiasi", "Flight Of Ideas", "Bloking", "Pengulangan Pembicaraan", "Tangensial" }));
        SKProsesPikir.setName("SKProsesPikir"); // NOI18N
        SKProsesPikir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKProsesPikirKeyPressed(evt);
            }
        });
        FormInput.add(SKProsesPikir);
        SKProsesPikir.setBounds(107, 1370, 180, 23);

        jLabel146.setText("Proses Pikir :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(0, 1370, 103, 23);

        SKIsiPikir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai", "Obsesi", "Fobia", "Hipokondria", "Depersonalisasi", "Pikiran Magis", "Ide Yang Terkait", "Waham" }));
        SKIsiPikir.setName("SKIsiPikir"); // NOI18N
        SKIsiPikir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKIsiPikirKeyPressed(evt);
            }
        });
        FormInput.add(SKIsiPikir);
        SKIsiPikir.setBounds(107, 1460, 130, 23);

        jLabel147.setText("Isi Pikir :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(0, 1460, 103, 23);

        KKKebutuhanEdukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diagnosa Dan Manajemen Penyakit", "Obat-obatan/Terapi", "Diet Dan Nutrisi", "Tindakan Keperawatan", "Rehabilitasi", "Manajemen Nyeri", "Lain-lain" }));
        KKKebutuhanEdukasi.setName("KKKebutuhanEdukasi"); // NOI18N
        KKKebutuhanEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KKKebutuhanEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KKKebutuhanEdukasi);
        KKKebutuhanEdukasi.setBounds(375, 1570, 220, 23);

        jLabel148.setText("Ya :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(730, 1430, 30, 23);

        SKMemori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ganguan Daya Ingat Jangka Pendek", "Ganguan Daya Ingat Jangka Panjang", "Ganguan Daya Ingat Saat Ini", "Konfabulasi" }));
        SKMemori.setName("SKMemori"); // NOI18N
        SKMemori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKMemoriKeyPressed(evt);
            }
        });
        FormInput.add(SKMemori);
        SKMemori.setBounds(107, 1400, 240, 23);

        jLabel149.setText("Memori :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(0, 1400, 103, 23);

        SKKonsentrasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Konsentrasi Baik", "Mudah Beralih", "Tidak Mampu Berkonsentrasi", "Tidak Mampu Berhitung Sederhana" }));
        SKKonsentrasi.setName("SKKonsentrasi"); // NOI18N
        SKKonsentrasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKKonsentrasiKeyPressed(evt);
            }
        });
        FormInput.add(SKKonsentrasi);
        SKKonsentrasi.setBounds(614, 1400, 240, 23);

        jLabel150.setText("Tingkat Konsentrasi & Berhitung :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(420, 1400, 190, 23);

        SKGangguanRingan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gangguan Ringan", "Gangguan Bermakna" }));
        SKGangguanRingan.setName("SKGangguanRingan"); // NOI18N
        SKGangguanRingan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKGangguanRinganKeyPressed(evt);
            }
        });
        FormInput.add(SKGangguanRingan);
        SKGangguanRingan.setBounds(704, 1460, 150, 23);

        jLabel151.setText("Bahasa Isyarat :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(680, 1540, 90, 23);

        SKDayaTilikDiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mengingkari Penyakit Yang Diderita", "Menyalahkan Hal-hal Diluar Dirinya" }));
        SKDayaTilikDiri.setName("SKDayaTilikDiri"); // NOI18N
        SKDayaTilikDiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKDayaTilikDiriKeyPressed(evt);
            }
        });
        FormInput.add(SKDayaTilikDiri);
        SKDayaTilikDiri.setBounds(393, 1370, 225, 23);

        jLabel152.setText("Daya Tilik Diri :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(299, 1370, 90, 23);

        KetADLSosialisasi.setFocusTraversalPolicyProvider(true);
        KetADLSosialisasi.setName("KetADLSosialisasi"); // NOI18N
        KetADLSosialisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetADLSosialisasiKeyPressed(evt);
            }
        });
        FormInput.add(KetADLSosialisasi);
        KetADLSosialisasi.setBounds(407, 1200, 210, 23);

        SKWaham.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kebesaran", "Curiga", "Agama", "Nihilistik" }));
        SKWaham.setName("SKWaham"); // NOI18N
        SKWaham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKWahamKeyPressed(evt);
            }
        });
        FormInput.add(SKWaham);
        SKWaham.setBounds(297, 1460, 105, 23);

        jLabel153.setText("Jika, Ya :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(331, 1540, 55, 23);

        KetSKPersepsi.setFocusTraversalPolicyProvider(true);
        KetSKPersepsi.setName("KetSKPersepsi"); // NOI18N
        KetSKPersepsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSKPersepsiKeyPressed(evt);
            }
        });
        FormInput.add(KetSKPersepsi);
        KetSKPersepsi.setBounds(226, 1430, 245, 23);

        KetSKWaham.setFocusTraversalPolicyProvider(true);
        KetSKWaham.setName("KetSKWaham"); // NOI18N
        KetSKWaham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSKWahamKeyPressed(evt);
            }
        });
        FormInput.add(KetSKWaham);
        KetSKWaham.setBounds(406, 1460, 155, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("XI. STATUS KESEHATAN SAAT INI");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 1290, 230, 23);

        jLabel154.setText("Kemampuan Pengkajian :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(570, 1460, 130, 23);

        ADLKegiatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ADLKegiatan.setName("ADLKegiatan"); // NOI18N
        ADLKegiatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKegiatanKeyPressed(evt);
            }
        });
        FormInput.add(ADLKegiatan);
        ADLKegiatan.setBounds(323, 1260, 80, 23);

        jLabel155.setText("Waham :");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(243, 1460, 50, 23);

        SKTingkatKesadaranOrientasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bingung", "Sedasi", "Waktu", "Stupor", "Tempat", "Orang" }));
        SKTingkatKesadaranOrientasi.setName("SKTingkatKesadaranOrientasi"); // NOI18N
        SKTingkatKesadaranOrientasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKTingkatKesadaranOrientasiKeyPressed(evt);
            }
        });
        FormInput.add(SKTingkatKesadaranOrientasi);
        SKTingkatKesadaranOrientasi.setBounds(764, 1430, 90, 23);

        KetSKDayaTilikDiri.setFocusTraversalPolicyProvider(true);
        KetSKDayaTilikDiri.setName("KetSKDayaTilikDiri"); // NOI18N
        KetSKDayaTilikDiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSKDayaTilikDiriKeyPressed(evt);
            }
        });
        FormInput.add(KetSKDayaTilikDiri);
        KetSKDayaTilikDiri.setBounds(622, 1370, 232, 23);

        jLabel156.setText("Lainnya :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(510, 1510, 57, 23);

        jLabel157.setText("Kebutuhan Edukasi (Pilih Topik Edukasi Pada Kolom Yang Tersedia) :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(0, 1570, 371, 23);

        KKPembelajaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KKPembelajaran.setName("KKPembelajaran"); // NOI18N
        KKPembelajaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KKPembelajaranKeyPressed(evt);
            }
        });
        FormInput.add(KKPembelajaran);
        KKPembelajaran.setBounds(251, 1510, 80, 23);

        jLabel158.setText("Dibutuhkan Penerjamah :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(0, 1540, 247, 23);

        KKPenerjamah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KKPenerjamah.setName("KKPenerjamah"); // NOI18N
        KKPenerjamah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KKPenerjamahKeyPressed(evt);
            }
        });
        FormInput.add(KKPenerjamah);
        KKPenerjamah.setBounds(251, 1540, 80, 23);

        jLabel159.setText("Terdapat Hambatan Dalam Pembelajaran :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(0, 1510, 247, 23);

        KetKKPembelajaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Pendengaran", "Penglihatan", "Kognitif", "Fisik", "Budaya", "Emosi", "Bahasa ", "Lainnya" }));
        KetKKPembelajaran.setName("KetKKPembelajaran"); // NOI18N
        KetKKPembelajaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKKPembelajaranKeyPressed(evt);
            }
        });
        FormInput.add(KetKKPembelajaran);
        KetKKPembelajaran.setBounds(390, 1510, 120, 23);

        KetKKKebutuhanEdukasi.setFocusTraversalPolicyProvider(true);
        KetKKKebutuhanEdukasi.setName("KetKKKebutuhanEdukasi"); // NOI18N
        KetKKKebutuhanEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKKKebutuhanEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetKKKebutuhanEdukasi);
        KetKKKebutuhanEdukasi.setBounds(654, 1570, 200, 23);

        jLabel160.setText("Lainnya :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(595, 1570, 55, 23);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 1490, 880, 1);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(0, 1600, 880, 1);

        KetKKPenerjamahLainnya.setFocusTraversalPolicyProvider(true);
        KetKKPenerjamahLainnya.setName("KetKKPenerjamahLainnya"); // NOI18N
        KetKKPenerjamahLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKKPenerjamahLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KetKKPenerjamahLainnya);
        KetKKPenerjamahLainnya.setBounds(390, 1540, 290, 23);

        jLabel161.setText("Jika, Ya :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(331, 1510, 55, 23);

        jLabel162.setText("Tingkat Kesadaran :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(480, 1430, 110, 23);

        jLabel163.setText("Orientasi :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(586, 1430, 60, 23);

        SKOrientasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SKOrientasi.setName("SKOrientasi"); // NOI18N
        SKOrientasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SKOrientasiKeyPressed(evt);
            }
        });
        FormInput.add(SKOrientasi);
        SKOrientasi.setBounds(650, 1430, 80, 23);

        RBDRencana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RBDRencana.setName("RBDRencana"); // NOI18N
        RBDRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RBDRencanaKeyPressed(evt);
            }
        });
        FormInput.add(RBDRencana);
        RBDRencana.setBounds(610, 350, 80, 23);

        jLabel164.setText("Resiko Herediter :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(0, 300, 131, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("batang/hari");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(777, 550, 60, 23);

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setText("gelas/hari");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(777, 580, 60, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(365, 705, 1, 140);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 710, 130, 23);

        jLabel80.setText("Penyebab :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(510, 710, 60, 23);

        Provokes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penyakit", "Benturan", "Lain-lain" }));
        Provokes.setName("Provokes"); // NOI18N
        Provokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvokesKeyPressed(evt);
            }
        });
        FormInput.add(Provokes);
        Provokes.setBounds(574, 710, 130, 23);

        KetProvokes.setFocusTraversalPolicyProvider(true);
        KetProvokes.setName("KetProvokes"); // NOI18N
        KetProvokes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProvokesKeyPressed(evt);
            }
        });
        FormInput.add(KetProvokes);
        KetProvokes.setBounds(708, 710, 146, 23);

        jLabel81.setText("Kualitas :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(370, 740, 55, 23);

        Quality.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seperti Tertusuk", "Berdenyut", "Teriris", "Tertindih", "Tertiban", "Lain-lain" }));
        Quality.setName("Quality"); // NOI18N
        Quality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QualityKeyPressed(evt);
            }
        });
        FormInput.add(Quality);
        Quality.setBounds(429, 740, 140, 23);

        KetQuality.setFocusTraversalPolicyProvider(true);
        KetQuality.setName("KetQuality"); // NOI18N
        KetQuality.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetQualityKeyPressed(evt);
            }
        });
        FormInput.add(KetQuality);
        KetQuality.setBounds(573, 740, 281, 23);

        jLabel90.setText("Wilayah :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(370, 770, 55, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(394, 790, 60, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(458, 790, 220, 23);

        jLabel67.setText("Menyebar :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(691, 790, 79, 23);

        Menyebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Menyebar.setName("Menyebar"); // NOI18N
        Menyebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyebarKeyPressed(evt);
            }
        });
        FormInput.add(Menyebar);
        Menyebar.setBounds(774, 790, 80, 23);

        jLabel88.setText("Severity :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(370, 820, 55, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("Skala Nyeri");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(428, 820, 60, 23);

        SkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        SkalaNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaNyeriKeyPressed(evt);
            }
        });
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(491, 820, 70, 23);

        jLabel87.setText("Waktu / Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(627, 820, 90, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(721, 820, 90, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Menit");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(815, 820, 35, 23);

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(0, 860, 130, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Istirahat", "Medengar Musik", "Minum Obat" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(134, 860, 130, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(268, 860, 150, 23);

        jLabel86.setText("Diberitahukan Pada Dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(480, 860, 150, 23);

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(PadaDokter);
        PadaDokter.setBounds(634, 860, 80, 23);

        jLabel64.setText("Jam  :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(720, 860, 50, 23);

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });
        FormInput.add(KetDokter);
        KetDokter.setBounds(774, 860, 80, 23);

        jLabel72.setText("a. Cara Berjalan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 1070, 126, 23);

        jLabel65.setText("1. Tidak seimbang / sempoyongan / limbung :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(35, 1090, 250, 23);

        ResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ResikoJatuh.setName("ResikoJatuh"); // NOI18N
        ResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(ResikoJatuh);
        ResikoJatuh.setBounds(289, 1090, 80, 23);

        jLabel66.setText("2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(370, 1090, 400, 23);

        BJM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BJM.setName("BJM"); // NOI18N
        BJM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BJMKeyPressed(evt);
            }
        });
        FormInput.add(BJM);
        BJM.setBounds(774, 1090, 80, 23);

        jLabel70.setText("b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 1120, 571, 23);

        MSA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MSA.setName("MSA"); // NOI18N
        MSA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MSAKeyPressed(evt);
            }
        });
        FormInput.add(MSA);
        MSA.setBounds(575, 1120, 80, 23);

        jLabel68.setText("Hasil :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 1150, 72, 23);

        HasilResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)", "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        HasilResikoJatuh.setName("HasilResikoJatuh"); // NOI18N
        HasilResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(HasilResikoJatuh);
        HasilResikoJatuh.setBounds(76, 1150, 293, 23);

        jLabel97.setText("Dilaporkan kepada dokter ?");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(381, 1150, 190, 23);

        LaporResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        LaporResikoJatuh.setName("LaporResikoJatuh"); // NOI18N
        LaporResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(LaporResikoJatuh);
        LaporResikoJatuh.setBounds(575, 1150, 80, 23);

        jLabel71.setText("Jam dilaporkan :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(680, 1150, 90, 23);

        KetLaporResikoJatuh.setFocusTraversalPolicyProvider(true);
        KetLaporResikoJatuh.setName("KetLaporResikoJatuh"); // NOI18N
        KetLaporResikoJatuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporResikoJatuhKeyPressed(evt);
            }
        });
        FormInput.add(KetLaporResikoJatuh);
        KetLaporResikoJatuh.setBounds(774, 1150, 80, 23);

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
        Scroll6.setBounds(10, 1610, 400, 143);

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
        TabRencanaKeperawatan.setBounds(433, 1610, 420, 143);

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
        BtnTambahRencana.setBounds(806, 1760, 28, 23);

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
        BtnAllRencana.setBounds(774, 1760, 28, 23);

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
        BtnCariRencana.setBounds(742, 1760, 28, 23);

        TCariRencana.setToolTipText("Alt+C");
        TCariRencana.setName("TCariRencana"); // NOI18N
        TCariRencana.setPreferredSize(new java.awt.Dimension(215, 23));
        TCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRencanaKeyPressed(evt);
            }
        });
        FormInput.add(TCariRencana);
        TCariRencana.setBounds(503, 1760, 235, 23);

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label13);
        label13.setBounds(439, 1760, 60, 23);

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
        BtnTambahMasalah.setBounds(363, 1760, 28, 23);

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
        BtnAllMasalah.setBounds(331, 1760, 28, 23);

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
        BtnCariMasalah.setBounds(299, 1760, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 1760, 215, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(16, 1760, 60, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(700, 200));

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-04-2024" }));
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
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RKDKeluhan.getText().trim().equals("")){
            Valid.textKosong(RKDKeluhan,"Keluhan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
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
            Valid.pindah(evt,KetKKKebutuhanEdukasi,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),129).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RKDKeluhan.getText().trim().equals("")){
            Valid.textKosong(RKDKeluhan,"Keluhan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),129).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan_psikiatri.tanggal,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.informasi,penilaian_awal_keperawatan_ralan_psikiatri.keluhan_utama,penilaian_awal_keperawatan_ralan_psikiatri.rkd_sakit_sejak,penilaian_awal_keperawatan_ralan_psikiatri.rkd_keluhan,penilaian_awal_keperawatan_ralan_psikiatri.rkd_berobat,penilaian_awal_keperawatan_ralan_psikiatri.rkd_hasil_pengobatan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.fp_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.fp_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_fisik,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.ket_rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.resiko_bunuh_diri,penilaian_awal_keperawatan_ralan_psikiatri.rbd_ide,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_ide,penilaian_awal_keperawatan_ralan_psikiatri.rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.rbd_percobaan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_percobaan,penilaian_awal_keperawatan_ralan_psikiatri.rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_efek_samping,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_efek_samping,penilaian_awal_keperawatan_ralan_psikiatri.rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_lama_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_cara_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_latar_belakang_pemakaian,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_alasan_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_alergi_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_alergi_obat,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rpo_merokok,penilaian_awal_keperawatan_ralan_psikiatri.ket_merokok,penilaian_awal_keperawatan_ralan_psikiatri.rpo_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.ket_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.td,penilaian_awal_keperawatan_ralan_psikiatri.nadi,penilaian_awal_keperawatan_ralan_psikiatri.gcs,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rr,penilaian_awal_keperawatan_ralan_psikiatri.suhu,penilaian_awal_keperawatan_ralan_psikiatri.pf_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.skala_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.durasi,penilaian_awal_keperawatan_ralan_psikiatri.nyeri,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.provokes,penilaian_awal_keperawatan_ralan_psikiatri.ket_provokes,penilaian_awal_keperawatan_ralan_psikiatri.quality,penilaian_awal_keperawatan_ralan_psikiatri.ket_quality,penilaian_awal_keperawatan_ralan_psikiatri.lokasi,penilaian_awal_keperawatan_ralan_psikiatri.menyebar,penilaian_awal_keperawatan_ralan_psikiatri.pada_dokter,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_dokter,penilaian_awal_keperawatan_ralan_psikiatri.nyeri_hilang,penilaian_awal_keperawatan_ralan_psikiatri.ket_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.bb,penilaian_awal_keperawatan_ralan_psikiatri.tb,penilaian_awal_keperawatan_ralan_psikiatri.bmi,penilaian_awal_keperawatan_ralan_psikiatri.lapor_status_nutrisi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor_status_nutrisi,penilaian_awal_keperawatan_ralan_psikiatri.sg1,penilaian_awal_keperawatan_ralan_psikiatri.nilai1,penilaian_awal_keperawatan_ralan_psikiatri.sg2,penilaian_awal_keperawatan_ralan_psikiatri.nilai2,penilaian_awal_keperawatan_ralan_psikiatri.total_hasil,penilaian_awal_keperawatan_ralan_psikiatri.resikojatuh,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.bjm,penilaian_awal_keperawatan_ralan_psikiatri.msa,penilaian_awal_keperawatan_ralan_psikiatri.hasil,penilaian_awal_keperawatan_ralan_psikiatri.lapor,penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor,penilaian_awal_keperawatan_ralan_psikiatri.adl_mandi,penilaian_awal_keperawatan_ralan_psikiatri.adl_berpakaian,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.adl_makan,penilaian_awal_keperawatan_ralan_psikiatri.adl_bak,penilaian_awal_keperawatan_ralan_psikiatri.adl_bab,penilaian_awal_keperawatan_ralan_psikiatri.adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.adl_sosialisasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_sosialisasi,penilaian_awal_keperawatan_ralan_psikiatri.adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.sk_penampilan,penilaian_awal_keperawatan_ralan_psikiatri.sk_alam_perasaan,penilaian_awal_keperawatan_ralan_psikiatri.sk_pembicaraan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_afek,penilaian_awal_keperawatan_ralan_psikiatri.sk_aktifitas_motorik,penilaian_awal_keperawatan_ralan_psikiatri.sk_gangguan_ringan,penilaian_awal_keperawatan_ralan_psikiatri.sk_proses_pikir,penilaian_awal_keperawatan_ralan_psikiatri.sk_orientasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_tingkat_kesadaran_orientasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_memori,penilaian_awal_keperawatan_ralan_psikiatri.sk_interaksi,penilaian_awal_keperawatan_ralan_psikiatri.sk_konsentrasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.sk_isi_pikir,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.kk_pembelajaran,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran,"+       
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_penerjamah,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_penerjamah_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_bahasa_isyarat,penilaian_awal_keperawatan_ralan_psikiatri.kk_kebutuhan_edukasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_kebutuhan_edukasi,penilaian_awal_keperawatan_ralan_psikiatri.rencana,penilaian_awal_keperawatan_ralan_psikiatri.nip,petugas.nama "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan_psikiatri on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan_psikiatri.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan_psikiatri.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan_psikiatri.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan_psikiatri.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan_psikiatri.tanggal,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.informasi,penilaian_awal_keperawatan_ralan_psikiatri.keluhan_utama,penilaian_awal_keperawatan_ralan_psikiatri.rkd_sakit_sejak,penilaian_awal_keperawatan_ralan_psikiatri.rkd_keluhan,penilaian_awal_keperawatan_ralan_psikiatri.rkd_berobat,penilaian_awal_keperawatan_ralan_psikiatri.rkd_hasil_pengobatan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.fp_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.fp_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_fisik,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.ket_rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.resiko_bunuh_diri,penilaian_awal_keperawatan_ralan_psikiatri.rbd_ide,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_ide,penilaian_awal_keperawatan_ralan_psikiatri.rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.rbd_percobaan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_percobaan,penilaian_awal_keperawatan_ralan_psikiatri.rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_efek_samping,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_efek_samping,penilaian_awal_keperawatan_ralan_psikiatri.rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_lama_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_cara_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_latar_belakang_pemakaian,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_alasan_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_alergi_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_alergi_obat,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rpo_merokok,penilaian_awal_keperawatan_ralan_psikiatri.ket_merokok,penilaian_awal_keperawatan_ralan_psikiatri.rpo_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.ket_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.td,penilaian_awal_keperawatan_ralan_psikiatri.nadi,penilaian_awal_keperawatan_ralan_psikiatri.gcs,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.rr,penilaian_awal_keperawatan_ralan_psikiatri.suhu,penilaian_awal_keperawatan_ralan_psikiatri.pf_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.skala_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.durasi,penilaian_awal_keperawatan_ralan_psikiatri.nyeri,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.provokes,penilaian_awal_keperawatan_ralan_psikiatri.ket_provokes,penilaian_awal_keperawatan_ralan_psikiatri.quality,penilaian_awal_keperawatan_ralan_psikiatri.ket_quality,penilaian_awal_keperawatan_ralan_psikiatri.lokasi,penilaian_awal_keperawatan_ralan_psikiatri.menyebar,penilaian_awal_keperawatan_ralan_psikiatri.pada_dokter,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_dokter,penilaian_awal_keperawatan_ralan_psikiatri.nyeri_hilang,penilaian_awal_keperawatan_ralan_psikiatri.ket_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.bb,penilaian_awal_keperawatan_ralan_psikiatri.tb,penilaian_awal_keperawatan_ralan_psikiatri.bmi,penilaian_awal_keperawatan_ralan_psikiatri.lapor_status_nutrisi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor_status_nutrisi,penilaian_awal_keperawatan_ralan_psikiatri.sg1,penilaian_awal_keperawatan_ralan_psikiatri.nilai1,penilaian_awal_keperawatan_ralan_psikiatri.sg2,penilaian_awal_keperawatan_ralan_psikiatri.nilai2,penilaian_awal_keperawatan_ralan_psikiatri.total_hasil,penilaian_awal_keperawatan_ralan_psikiatri.resikojatuh,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.bjm,penilaian_awal_keperawatan_ralan_psikiatri.msa,penilaian_awal_keperawatan_ralan_psikiatri.hasil,penilaian_awal_keperawatan_ralan_psikiatri.lapor,penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor,penilaian_awal_keperawatan_ralan_psikiatri.adl_mandi,penilaian_awal_keperawatan_ralan_psikiatri.adl_berpakaian,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.adl_makan,penilaian_awal_keperawatan_ralan_psikiatri.adl_bak,penilaian_awal_keperawatan_ralan_psikiatri.adl_bab,penilaian_awal_keperawatan_ralan_psikiatri.adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.adl_sosialisasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_sosialisasi,penilaian_awal_keperawatan_ralan_psikiatri.adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.sk_penampilan,penilaian_awal_keperawatan_ralan_psikiatri.sk_alam_perasaan,penilaian_awal_keperawatan_ralan_psikiatri.sk_pembicaraan,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_afek,penilaian_awal_keperawatan_ralan_psikiatri.sk_aktifitas_motorik,penilaian_awal_keperawatan_ralan_psikiatri.sk_gangguan_ringan,penilaian_awal_keperawatan_ralan_psikiatri.sk_proses_pikir,penilaian_awal_keperawatan_ralan_psikiatri.sk_orientasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_tingkat_kesadaran_orientasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_memori,penilaian_awal_keperawatan_ralan_psikiatri.sk_interaksi,penilaian_awal_keperawatan_ralan_psikiatri.sk_konsentrasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.sk_isi_pikir,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.kk_pembelajaran,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran,"+       
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_penerjamah,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_penerjamah_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_bahasa_isyarat,penilaian_awal_keperawatan_ralan_psikiatri.kk_kebutuhan_edukasi,"+
                            "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_kebutuhan_edukasi,penilaian_awal_keperawatan_ralan_psikiatri.rencana,penilaian_awal_keperawatan_ralan_psikiatri.nip,petugas.nama "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan_psikiatri on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan_psikiatri.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan_psikiatri.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan_psikiatri.tanggal between ? and ? and "+
                            "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "penilaian_awal_keperawatan_ralan_psikiatri.nip like ? or petugas.nama like ?) "+
                            "order by penilaian_awal_keperawatan_ralan_psikiatri.tanggal");
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
                    StringBuilder htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Agama</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cacat Fisik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Asuhan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Informasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keluhan Utama</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sakit Sejak</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Penyakit Dahulu</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berobat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pengobatan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Putus Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Putus Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Masalah Ekonomi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Masalah Ekonomi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Masalah Fisik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Masalah Fisik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Masalah Psikososial</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Masalah Psikososial</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Resiko Herediter</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Resiko Herediter</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Res Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ide Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Ide Bunuh DIri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rencana Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Rencana Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alat Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Alat Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Percobaan Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Percobaan Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keinginan Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keinginan Bunuh Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penggunaan Obat Psikiatri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penggunaan Obat Psikiatri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Efek Samping Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Efek samping Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Napza</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penggunaan Napza</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lama Napza</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Pemakaian Napza</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Latar Belakang Pemakaian Napza</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penggunaan Obat Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alasan Penggunaan Obat Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alergi Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Alergi Obat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merokok</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Merokok</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Minum Kopi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Minum Kopi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TD</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>GCS</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>RR</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keluhan Fisik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keluhan Fisik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skala Nyeri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Durasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Provokes</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Provokes</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kualitas</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kualitas</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lokasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menyebar</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lapor Dokter</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam Lapor Nyeri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Hilang</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Hilang Nyeri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>TB</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BMI</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lapor Status Nutrisi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam Lapor Status Nutrisi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skrining Gizi 1</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai 1</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Skrining Gizi 2</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nilai 2</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Berjalan A</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Berjalan B</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Berjalan C</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pengkajian Resiko Jatuh</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lapor Dokter</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam Dilapor</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Mandi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Berpakaian</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Makan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL BAK</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL BAB</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Hobi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan ADL Hobi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Sosialisasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan ADL Sosialisasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ADL Kegiatan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan ADL. Kegiatan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penampilan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alam Perasaan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pembicaraan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Afek</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aktifitas Motorik</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gangguan Ringan</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Proses Pikir</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Orientasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesadaran</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Memori</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Interaksi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konsentrasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Persepsi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Persepsi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Isi Pikir</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waham</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Waham</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daya Tilik Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Daya Tilik Diri</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pembelajaran</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Pembelajaran</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Pembelajaran Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penerjamah</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penerjamah Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa Isyarat</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kebutuhan Edukasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kebutuhan Edukasi</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rencana Keperawatan Lainnya</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").append(
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Perawat</b></td>").append(
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>").append(
                               "<td valign='top'>").append(rs.getString("no_rawat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("no_rkm_medis")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nm_pasien")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("jk")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("agama")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nama_bahasa")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nama_cacat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("tgl_lahir")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("tanggal")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("informasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("keluhan_utama")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rkd_sakit_sejak")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rkd_keluhan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rkd_berobat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rkd_hasil_pengobatan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("fp_putus_obat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_putus_obat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("fp_ekonomi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_masalah_ekonomi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("fp_masalah_fisik")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_masalah_fisik")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("fp_masalah_psikososial")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_masalah_psikososial")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rh_keluarga")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rh_keluarga")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("resiko_bunuh_diri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rbd_ide")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rbd_ide")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rbd_rencana")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rbd_rencana")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rbd_alat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rbd_alat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rbd_percobaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rbd_percobaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rbd_keinginan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rbd_keinginan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_penggunaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rpo_penggunaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_efek_samping")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rpo_efek_samping")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_napza")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_rpo_napza")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_lama_pemakaian")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_cara_pemakaian")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_latar_belakang_pemakaian")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_penggunaan_obat_lainnya")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_penggunaan_obat_lainnya")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_alasan_penggunaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_alergi_obat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_alergi_obat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_merokok")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_merokok")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rpo_minum_kopi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_minum_kopi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("td")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nadi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("gcs")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rr")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("suhu")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("pf_keluhan_fisik")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_keluhan_fisik")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("skala_nyeri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("durasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nyeri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("provokes")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_provokes")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("quality")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_quality")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("lokasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("menyebar")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("pada_dokter")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_dokter")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nyeri_hilang")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_nyeri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("bb")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("tb")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("bmi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("lapor_status_nutrisi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_lapor_status_nutrisi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sg1")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nilai1")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sg2")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nilai2")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("total_hasil")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("resikojatuh")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("bjm")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("msa")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("hasil")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("lapor")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_lapor")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_mandi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_berpakaian")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_makan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_bak")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_bab")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_hobi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_adl_hobi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_sosialisasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_adl_sosialisasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("adl_kegiatan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_adl_kegiatan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_penampilan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_alam_perasaan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_pembicaraan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_afek")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_aktifitas_motorik")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_gangguan_ringan")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_proses_pikir")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_orientasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_tingkat_kesadaran_orientasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_memori")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_interaksi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_konsentrasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_persepsi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_sk_persepsi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_isi_pikir")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_waham")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_sk_waham")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("sk_daya_tilik_diri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_sk_daya_tilik_diri")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("kk_pembelajaran")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_kk_pembelajaran")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_kk_pembelajaran_lainnya")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("kk_Penerjamah")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_kk_penerjamah_Lainnya")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("kk_bahasa_isyarat")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("kk_kebutuhan_edukasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("ket_kk_kebutuhan_edukasi")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("rencana")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nip")).append("</td>").append(
                               "<td valign='top'>").append(rs.getString("nama")).append("</td>").append(
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='14000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );
                    htmlContent=null;

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
                                "<table width='14000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,KetDokter,TB);
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
        Valid.pindah(evt,KetKeluhanFisik,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void KetKeluhanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeluhanFisikKeyPressed
        Valid.pindah(evt,PFKeluhanFisik,TD);
    }//GEN-LAST:event_KetKeluhanFisikKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,KeluhanUtama);
    }//GEN-LAST:event_InformasiKeyPressed

    private void RKDKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RKDKeluhanKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RKDSakitSejak);
    }//GEN-LAST:event_RKDKeluhanKeyPressed

    private void KetKKPembelajaranLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKKPembelajaranLainnyaKeyPressed
        Valid.pindah(evt,KetKKPembelajaran,KKPenerjamah);
    }//GEN-LAST:event_KetKKPembelajaranLainnyaKeyPressed

    private void FPMasalahPsikososialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FPMasalahPsikososialKeyPressed
        Valid.pindah(evt,KetMasalahFisik,KetMasalahPsikososial);
    }//GEN-LAST:event_FPMasalahPsikososialKeyPressed

    private void FPPutusObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FPPutusObatKeyPressed
        Valid.pindah(evt,RKDHasilPengobatan,KetPutusObat);
    }//GEN-LAST:event_FPPutusObatKeyPressed

    private void KetMasalahPsikososialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMasalahPsikososialKeyPressed
        Valid.pindah(evt,FPMasalahPsikososial,RHKeluarga);
    }//GEN-LAST:event_KetMasalahPsikososialKeyPressed

    private void FPEkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FPEkonomiKeyPressed
        Valid.pindah(evt,KetPutusObat,KetMasalahEkonomi);
    }//GEN-LAST:event_FPEkonomiKeyPressed

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,Nilai1,Nilai2);
    }//GEN-LAST:event_SG2KeyPressed

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,KetLaporResikoJatuh,Nilai1);
    }//GEN-LAST:event_SG1KeyPressed

    private void Nilai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai1KeyPressed
        Valid.pindah(evt,SG1,SG2);
    }//GEN-LAST:event_Nilai1KeyPressed

    private void Nilai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nilai2KeyPressed
        Valid.pindah(evt,SG2,ResikoJatuh);
    }//GEN-LAST:event_Nilai2KeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Suhu,Nyeri);
    }//GEN-LAST:event_GCSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/masalahkeperawatan.iyem")<30){
                tampilMasalah2();
            }else{
                tampilMasalah();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        Nilai1.setSelectedIndex(SG1.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void Nilai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Nilai1ItemStateChanged
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }//GEN-LAST:event_Nilai1ItemStateChanged

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        Nilai2.setSelectedIndex(SG2.getSelectedIndex());
        TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
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
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),64).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),63).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())); 
            try {
                masalahkeperawatan="";
                ps2=koneksi.prepareStatement(
                    "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                    "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                    "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by penilaian_awal_keperawatan_ralan_masalah.kode_masalah");
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
            try {
                masalahkeperawatan="";
                ps2=koneksi.prepareStatement(
                    "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                    "inner join penilaian_awal_keperawatan_ralan_rencana on penilaian_awal_keperawatan_ralan_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
                    "where penilaian_awal_keperawatan_ralan_rencana.no_rawat=? order by penilaian_awal_keperawatan_ralan_rencana.kode_rencana");
                try {
                    ps2.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        masalahkeperawatan=rs2.getString("rencana_keperawatan")+", "+masalahkeperawatan;
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
            param.put("rencana",masalahkeperawatan); 
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRalanPsikiatri.jasper","report","::[ Laporan Pengkajian Awal Keperawatan Ralan Psikiatri ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan_psikiatri.tanggal,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.informasi,penilaian_awal_keperawatan_ralan_psikiatri.keluhan_utama,penilaian_awal_keperawatan_ralan_psikiatri.rkd_sakit_sejak,penilaian_awal_keperawatan_ralan_psikiatri.rkd_keluhan,penilaian_awal_keperawatan_ralan_psikiatri.rkd_berobat,penilaian_awal_keperawatan_ralan_psikiatri.rkd_hasil_pengobatan,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.fp_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.fp_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_fisik,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.ket_rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.resiko_bunuh_diri,penilaian_awal_keperawatan_ralan_psikiatri.rbd_ide,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_ide,penilaian_awal_keperawatan_ralan_psikiatri.rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.rbd_percobaan,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_percobaan,penilaian_awal_keperawatan_ralan_psikiatri.rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_efek_samping,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_efek_samping,penilaian_awal_keperawatan_ralan_psikiatri.rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_lama_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_cara_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_latar_belakang_pemakaian,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_alasan_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_alergi_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_alergi_obat,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.rpo_merokok,penilaian_awal_keperawatan_ralan_psikiatri.ket_merokok,penilaian_awal_keperawatan_ralan_psikiatri.rpo_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.ket_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.td,penilaian_awal_keperawatan_ralan_psikiatri.nadi,penilaian_awal_keperawatan_ralan_psikiatri.gcs,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.rr,penilaian_awal_keperawatan_ralan_psikiatri.suhu,penilaian_awal_keperawatan_ralan_psikiatri.pf_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.skala_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.durasi,penilaian_awal_keperawatan_ralan_psikiatri.nyeri,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.provokes,penilaian_awal_keperawatan_ralan_psikiatri.ket_provokes,penilaian_awal_keperawatan_ralan_psikiatri.quality,penilaian_awal_keperawatan_ralan_psikiatri.ket_quality,penilaian_awal_keperawatan_ralan_psikiatri.lokasi,penilaian_awal_keperawatan_ralan_psikiatri.menyebar,penilaian_awal_keperawatan_ralan_psikiatri.pada_dokter,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_dokter,penilaian_awal_keperawatan_ralan_psikiatri.nyeri_hilang,penilaian_awal_keperawatan_ralan_psikiatri.ket_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.bb,penilaian_awal_keperawatan_ralan_psikiatri.tb,penilaian_awal_keperawatan_ralan_psikiatri.bmi,penilaian_awal_keperawatan_ralan_psikiatri.lapor_status_nutrisi,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor_status_nutrisi,penilaian_awal_keperawatan_ralan_psikiatri.sg1,penilaian_awal_keperawatan_ralan_psikiatri.nilai1,penilaian_awal_keperawatan_ralan_psikiatri.sg2,penilaian_awal_keperawatan_ralan_psikiatri.nilai2,penilaian_awal_keperawatan_ralan_psikiatri.total_hasil,penilaian_awal_keperawatan_ralan_psikiatri.resikojatuh,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.bjm,penilaian_awal_keperawatan_ralan_psikiatri.msa,penilaian_awal_keperawatan_ralan_psikiatri.hasil,penilaian_awal_keperawatan_ralan_psikiatri.lapor,penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor,penilaian_awal_keperawatan_ralan_psikiatri.adl_mandi,penilaian_awal_keperawatan_ralan_psikiatri.adl_berpakaian,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.adl_makan,penilaian_awal_keperawatan_ralan_psikiatri.adl_bak,penilaian_awal_keperawatan_ralan_psikiatri.adl_bab,penilaian_awal_keperawatan_ralan_psikiatri.adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.adl_sosialisasi,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_sosialisasi,penilaian_awal_keperawatan_ralan_psikiatri.adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.sk_penampilan,penilaian_awal_keperawatan_ralan_psikiatri.sk_alam_perasaan,penilaian_awal_keperawatan_ralan_psikiatri.sk_pembicaraan,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.sk_afek,penilaian_awal_keperawatan_ralan_psikiatri.sk_aktifitas_motorik,penilaian_awal_keperawatan_ralan_psikiatri.sk_gangguan_ringan,penilaian_awal_keperawatan_ralan_psikiatri.sk_proses_pikir,penilaian_awal_keperawatan_ralan_psikiatri.sk_orientasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_tingkat_kesadaran_orientasi,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.sk_memori,penilaian_awal_keperawatan_ralan_psikiatri.sk_interaksi,penilaian_awal_keperawatan_ralan_psikiatri.sk_konsentrasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.sk_isi_pikir,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.kk_pembelajaran,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran,"+       
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_penerjamah,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_penerjamah_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_bahasa_isyarat,penilaian_awal_keperawatan_ralan_psikiatri.kk_kebutuhan_edukasi,"+
                "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_kebutuhan_edukasi,penilaian_awal_keperawatan_ralan_psikiatri.rencana,penilaian_awal_keperawatan_ralan_psikiatri.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ralan_psikiatri on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan_psikiatri.no_rawat "+
                "inner join petugas on penilaian_awal_keperawatan_ralan_psikiatri.nip=petugas.nip "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void LaporStatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporStatusNutrisiKeyPressed
        Valid.pindah(evt,BMI,KetLaporStatusNutrisi);
    }//GEN-LAST:event_LaporStatusNutrisiKeyPressed

    private void KetLaporStatusNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporStatusNutrisiKeyPressed
        Valid.pindah(evt,LaporStatusNutrisi,SG1);
    }//GEN-LAST:event_KetLaporStatusNutrisiKeyPressed

    private void RKDBerobatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RKDBerobatKeyPressed
        Valid.pindah(evt,RKDSakitSejak,RKDHasilPengobatan);
    }//GEN-LAST:event_RKDBerobatKeyPressed

    private void PFKeluhanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PFKeluhanFisikKeyPressed
        Valid.pindah(evt,KetMinumKopi,KetKeluhanFisik);
    }//GEN-LAST:event_PFKeluhanFisikKeyPressed

    private void PFKeluhanFisikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PFKeluhanFisikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PFKeluhanFisikActionPerformed

    private void RHKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RHKeluargaKeyPressed
        Valid.pindah(evt,KetMasalahPsikososial,KetRHKeluarga);
    }//GEN-LAST:event_RHKeluargaKeyPressed

    private void KetRHKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRHKeluargaKeyPressed
        Valid.pindah(evt,RHKeluarga,ResikoBunuhDiri);
    }//GEN-LAST:event_KetRHKeluargaKeyPressed

    private void KetPutusObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPutusObatKeyPressed
        Valid.pindah(evt,FPPutusObat,FPEkonomi);
    }//GEN-LAST:event_KetPutusObatKeyPressed

    private void KetMasalahEkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMasalahEkonomiKeyPressed
        Valid.pindah(evt,FPEkonomi,FPMasalahFisik);
    }//GEN-LAST:event_KetMasalahEkonomiKeyPressed

    private void FPEkonomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FPEkonomiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FPEkonomiActionPerformed

    private void RKDHasilPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RKDHasilPengobatanKeyPressed
        Valid.pindah(evt,RKDBerobat,FPPutusObat);
    }//GEN-LAST:event_RKDHasilPengobatanKeyPressed

    private void ResikoBunuhDiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikoBunuhDiriKeyPressed
        Valid.pindah(evt,KetRHKeluarga,RBDIde);
    }//GEN-LAST:event_ResikoBunuhDiriKeyPressed

    private void RBDIdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RBDIdeKeyPressed
        Valid.pindah(evt,ResikoBunuhDiri,KetRBDIde);
    }//GEN-LAST:event_RBDIdeKeyPressed

    private void KetRBDIdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRBDIdeKeyPressed
        Valid.pindah(evt,RBDIde,RBDRencana);
    }//GEN-LAST:event_KetRBDIdeKeyPressed

    private void RBDAlatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RBDAlatKeyPressed
        Valid.pindah(evt,KetRBDRencana,KetRBDAlat);
    }//GEN-LAST:event_RBDAlatKeyPressed

    private void KetRBDRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRBDRencanaKeyPressed
        Valid.pindah(evt,RBDRencana,RBDAlat);
    }//GEN-LAST:event_KetRBDRencanaKeyPressed

    private void KetRBDAlatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRBDAlatKeyPressed
        Valid.pindah(evt,RBDAlat,RBDPercobaan);
    }//GEN-LAST:event_KetRBDAlatKeyPressed

    private void RBDPercobaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RBDPercobaanKeyPressed
        Valid.pindah(evt,KetRBDAlat,KetRBDPercobaan);
    }//GEN-LAST:event_RBDPercobaanKeyPressed

    private void KetRBDPercobaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRBDPercobaanKeyPressed
        Valid.pindah(evt,RBDPercobaan,RBDKeinginan);
    }//GEN-LAST:event_KetRBDPercobaanKeyPressed

    private void RBDKeinginanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RBDKeinginanKeyPressed
        Valid.pindah(evt,KetRBDPercobaan,KetRBDKeinginan);        
    }//GEN-LAST:event_RBDKeinginanKeyPressed

    private void KetRBDKeinginanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRBDKeinginanKeyPressed
        Valid.pindah(evt,RBDKeinginan,RPOPenggunaan);  
    }//GEN-LAST:event_KetRBDKeinginanKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Informasi,RKDKeluhan);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void FPMasalahFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FPMasalahFisikKeyPressed
        Valid.pindah(evt,KetMasalahEkonomi,KetMasalahFisik);
    }//GEN-LAST:event_FPMasalahFisikKeyPressed

    private void KetMasalahFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMasalahFisikKeyPressed
        Valid.pindah(evt,FPMasalahFisik,FPMasalahPsikososial);
    }//GEN-LAST:event_KetMasalahFisikKeyPressed

    private void RPOPenggunaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOPenggunaanKeyPressed
        Valid.pindah(evt,KetRBDKeinginan,KetRPOPenggunaan);
    }//GEN-LAST:event_RPOPenggunaanKeyPressed

    private void KetRPOPenggunaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRPOPenggunaanKeyPressed
        Valid.pindah(evt,RPOPenggunaan,RPOEfekSamping);
    }//GEN-LAST:event_KetRPOPenggunaanKeyPressed

    private void RPOEfekSampingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOEfekSampingKeyPressed
        Valid.pindah(evt,KetRPOPenggunaan,KetRPOEfekSamping);
    }//GEN-LAST:event_RPOEfekSampingKeyPressed

    private void KetRPOEfekSampingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRPOEfekSampingKeyPressed
        Valid.pindah(evt,RPOEfekSamping,RPONapza);
    }//GEN-LAST:event_KetRPOEfekSampingKeyPressed

    private void RPONapzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPONapzaKeyPressed
        Valid.pindah(evt,KetRPOEfekSamping,KetRPONapza);
    }//GEN-LAST:event_RPONapzaKeyPressed

    private void KetRPONapzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRPONapzaKeyPressed
        Valid.pindah(evt,RPONapza,KetLamaPemakaian);
    }//GEN-LAST:event_KetRPONapzaKeyPressed

    private void KetLamaPemakaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLamaPemakaianKeyPressed
        Valid.pindah(evt,KetRPONapza,KetCaraPemakaian);
    }//GEN-LAST:event_KetLamaPemakaianKeyPressed

    private void RPOPenggunaanObatLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOPenggunaanObatLainnyaKeyPressed
        Valid.pindah(evt,KetLatarBelakangPemakaian,KetPenggunaanObatLainnya);
    }//GEN-LAST:event_RPOPenggunaanObatLainnyaKeyPressed

    private void KetPenggunaanObatLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPenggunaanObatLainnyaKeyPressed
        Valid.pindah(evt,RPOPenggunaanObatLainnya,KetAlasanPenggunaan);
    }//GEN-LAST:event_KetPenggunaanObatLainnyaKeyPressed

    private void KetAlasanPenggunaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAlasanPenggunaanKeyPressed
        Valid.pindah(evt,KetPenggunaanObatLainnya,RPOAlergiObat);
    }//GEN-LAST:event_KetAlasanPenggunaanKeyPressed

    private void RPOAlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOAlergiObatKeyPressed
        Valid.pindah(evt,KetAlasanPenggunaan,KetAlergiObat);
    }//GEN-LAST:event_RPOAlergiObatKeyPressed

    private void KetAlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAlergiObatKeyPressed
        Valid.pindah(evt,RPOAlergiObat,RPOMerokok);
    }//GEN-LAST:event_KetAlergiObatKeyPressed

    private void RPOMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOMerokokKeyPressed
        Valid.pindah(evt,KetAlergiObat,KetMerokok);
    }//GEN-LAST:event_RPOMerokokKeyPressed

    private void KetMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMerokokKeyPressed
        Valid.pindah(evt,RPOMerokok,RPOMinumKopi);
    }//GEN-LAST:event_KetMerokokKeyPressed

    private void RPOMinumKopiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOMinumKopiKeyPressed
        Valid.pindah(evt,KetMerokok,KetMinumKopi);
    }//GEN-LAST:event_RPOMinumKopiKeyPressed

    private void KetMinumKopiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMinumKopiKeyPressed
        Valid.pindah(evt,RPOMinumKopi,PFKeluhanFisik);
    }//GEN-LAST:event_KetMinumKopiKeyPressed

    private void KetCaraPemakaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetCaraPemakaianKeyPressed
        Valid.pindah(evt,KetLamaPemakaian,KetLatarBelakangPemakaian);
    }//GEN-LAST:event_KetCaraPemakaianKeyPressed

    private void KetLatarBelakangPemakaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLatarBelakangPemakaianKeyPressed
        Valid.pindah(evt,KetCaraPemakaian,RPOPenggunaanObatLainnya);
    }//GEN-LAST:event_KetLatarBelakangPemakaianKeyPressed

    private void ADLMandiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLMandiKeyPressed
        Valid.pindah(evt,KetLaporResikoJatuh,ADLBak);
    }//GEN-LAST:event_ADLMandiKeyPressed

    private void ADLBerpakaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLBerpakaianKeyPressed
        Valid.pindah(evt,KetADLKegiatan,ADLMakan);
    }//GEN-LAST:event_ADLBerpakaianKeyPressed

    private void ADLMakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLMakanKeyPressed
        Valid.pindah(evt,ADLBerpakaian,SKPenampilan);
    }//GEN-LAST:event_ADLMakanKeyPressed

    private void ADLBabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLBabKeyPressed
        Valid.pindah(evt,ADLBak,ADLSosialisasi);
    }//GEN-LAST:event_ADLBabKeyPressed

    private void ADLBakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLBakKeyPressed
        Valid.pindah(evt,ADLMandi,ADLBab);
    }//GEN-LAST:event_ADLBakKeyPressed

    private void ADLSosialisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLSosialisasiKeyPressed
        Valid.pindah(evt,ADLBab,KetADLSosialisasi);
    }//GEN-LAST:event_ADLSosialisasiKeyPressed

    private void ADLHobiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLHobiKeyPressed
        Valid.pindah(evt,KetADLSosialisasi,KetADLHobi);
    }//GEN-LAST:event_ADLHobiKeyPressed

    private void KKBahasaIsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KKBahasaIsyaratKeyPressed
        Valid.pindah(evt,KetKKPenerjamahLainnya,KKKebutuhanEdukasi);
    }//GEN-LAST:event_KKBahasaIsyaratKeyPressed

    private void KetADLHobiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetADLHobiKeyPressed
        Valid.pindah(evt,ADLHobi,ADLKegiatan);
    }//GEN-LAST:event_KetADLHobiKeyPressed

    private void KetADLKegiatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetADLKegiatanKeyPressed
        Valid.pindah(evt,ADLKegiatan,ADLBerpakaian);
    }//GEN-LAST:event_KetADLKegiatanKeyPressed

    private void SKPenampilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKPenampilanKeyPressed
        Valid.pindah(evt,ADLMakan,SKPembicaraan);
    }//GEN-LAST:event_SKPenampilanKeyPressed

    private void SKPembicaraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKPembicaraanKeyPressed
        Valid.pindah(evt,SKPenampilan,SKAlamPerasaan);
    }//GEN-LAST:event_SKPembicaraanKeyPressed

    private void SKAktifitasMotorikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKAktifitasMotorikKeyPressed
        Valid.pindah(evt,SKAfek,SKInteraksi);
    }//GEN-LAST:event_SKAktifitasMotorikKeyPressed

    private void SKAlamPerasaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKAlamPerasaanKeyPressed
        Valid.pindah(evt,SKPembicaraan,SKAfek);
    }//GEN-LAST:event_SKAlamPerasaanKeyPressed

    private void SKInteraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKInteraksiKeyPressed
        Valid.pindah(evt,SKAktifitasMotorik,SKProsesPikir);
    }//GEN-LAST:event_SKInteraksiKeyPressed

    private void SKAfekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKAfekKeyPressed
        Valid.pindah(evt,SKAlamPerasaan,SKAktifitasMotorik);
    }//GEN-LAST:event_SKAfekKeyPressed

    private void SKPersepsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKPersepsiKeyPressed
        Valid.pindah(evt,SKKonsentrasi,KetSKPersepsi);
    }//GEN-LAST:event_SKPersepsiKeyPressed

    private void SKProsesPikirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKProsesPikirKeyPressed
        Valid.pindah(evt,SKInteraksi,SKDayaTilikDiri);
    }//GEN-LAST:event_SKProsesPikirKeyPressed

    private void SKIsiPikirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKIsiPikirKeyPressed
        Valid.pindah(evt,SKTingkatKesadaranOrientasi,SKWaham);
    }//GEN-LAST:event_SKIsiPikirKeyPressed

    private void KKKebutuhanEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KKKebutuhanEdukasiKeyPressed
        Valid.pindah(evt,KKBahasaIsyarat,KetKKKebutuhanEdukasi);
    }//GEN-LAST:event_KKKebutuhanEdukasiKeyPressed

    private void SKMemoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKMemoriKeyPressed
        Valid.pindah(evt,KetSKDayaTilikDiri,SKKonsentrasi);
    }//GEN-LAST:event_SKMemoriKeyPressed

    private void SKKonsentrasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKKonsentrasiKeyPressed
        Valid.pindah(evt,SKMemori,SKPersepsi);
    }//GEN-LAST:event_SKKonsentrasiKeyPressed

    private void SKGangguanRinganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKGangguanRinganKeyPressed
        Valid.pindah(evt,KetSKWaham,KKPembelajaran);
    }//GEN-LAST:event_SKGangguanRinganKeyPressed

    private void SKDayaTilikDiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKDayaTilikDiriKeyPressed
        Valid.pindah(evt,SKProsesPikir,KetSKDayaTilikDiri);
    }//GEN-LAST:event_SKDayaTilikDiriKeyPressed

    private void KetADLSosialisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetADLSosialisasiKeyPressed
        Valid.pindah(evt,ADLSosialisasi,ADLHobi);
    }//GEN-LAST:event_KetADLSosialisasiKeyPressed

    private void SKWahamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKWahamKeyPressed
        Valid.pindah(evt,SKIsiPikir,KetSKWaham);
    }//GEN-LAST:event_SKWahamKeyPressed

    private void KetSKPersepsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSKPersepsiKeyPressed
        Valid.pindah(evt,SKPersepsi,SKOrientasi);
    }//GEN-LAST:event_KetSKPersepsiKeyPressed

    private void KetSKWahamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSKWahamKeyPressed
        Valid.pindah(evt,SKWaham,SKGangguanRingan);
    }//GEN-LAST:event_KetSKWahamKeyPressed

    private void ADLKegiatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKegiatanKeyPressed
        Valid.pindah(evt,KetADLHobi,KetADLKegiatan);
    }//GEN-LAST:event_ADLKegiatanKeyPressed

    private void SKTingkatKesadaranOrientasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKTingkatKesadaranOrientasiKeyPressed
        Valid.pindah(evt,SKOrientasi,SKIsiPikir);
    }//GEN-LAST:event_SKTingkatKesadaranOrientasiKeyPressed

    private void KetSKDayaTilikDiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSKDayaTilikDiriKeyPressed
        Valid.pindah(evt,SKDayaTilikDiri,SKMemori);
    }//GEN-LAST:event_KetSKDayaTilikDiriKeyPressed

    private void KKPembelajaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KKPembelajaranKeyPressed
        Valid.pindah(evt,SKGangguanRingan,KetKKPembelajaran);
    }//GEN-LAST:event_KKPembelajaranKeyPressed

    private void KKPenerjamahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KKPenerjamahKeyPressed
        Valid.pindah(evt,KetKKPembelajaranLainnya,KetKKPenerjamahLainnya);
    }//GEN-LAST:event_KKPenerjamahKeyPressed

    private void KetKKPembelajaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKKPembelajaranKeyPressed
        Valid.pindah(evt,KKPembelajaran,KetKKPembelajaranLainnya);
    }//GEN-LAST:event_KetKKPembelajaranKeyPressed

    private void KetKKKebutuhanEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKKKebutuhanEdukasiKeyPressed
        Valid.pindah(evt,KKKebutuhanEdukasi,BtnSimpan);
    }//GEN-LAST:event_KetKKKebutuhanEdukasiKeyPressed

    private void KetKKPenerjamahLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKKPenerjamahLainnyaKeyPressed
        Valid.pindah(evt,KKPenerjamah,KKBahasaIsyarat);
    }//GEN-LAST:event_KetKKPenerjamahLainnyaKeyPressed

    private void SKOrientasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SKOrientasiKeyPressed
        Valid.pindah(evt,KetSKPersepsi,SKTingkatKesadaranOrientasi);
    }//GEN-LAST:event_SKOrientasiKeyPressed

    private void RBDRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RBDRencanaKeyPressed
        Valid.pindah(evt,KetRBDIde,KetRBDRencana);
    }//GEN-LAST:event_RBDRencanaKeyPressed

    private void RKDSakitSejakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RKDSakitSejakKeyPressed
        Valid.pindah(evt,RKDKeluhan,RKDBerobat);
    }//GEN-LAST:event_RKDSakitSejakKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,GCS,Provokes);
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
        Valid.pindah(evt,PadaDokter,BB);
    }//GEN-LAST:event_KetDokterKeyPressed

    private void ResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResikoJatuhKeyPressed
        Valid.pindah(evt,Nilai2,BJM);
    }//GEN-LAST:event_ResikoJatuhKeyPressed

    private void BJMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BJMKeyPressed
        Valid.pindah(evt,ResikoJatuh,MSA);
    }//GEN-LAST:event_BJMKeyPressed

    private void MSAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MSAKeyPressed
        Valid.pindah(evt,BJM,HasilResikoJatuh);
    }//GEN-LAST:event_MSAKeyPressed

    private void HasilResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilResikoJatuhKeyPressed
        Valid.pindah(evt,MSA,LaporResikoJatuh);
    }//GEN-LAST:event_HasilResikoJatuhKeyPressed

    private void LaporResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporResikoJatuhKeyPressed
        Valid.pindah(evt,HasilResikoJatuh,KetLaporResikoJatuh);
    }//GEN-LAST:event_LaporResikoJatuhKeyPressed

    private void KetLaporResikoJatuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporResikoJatuhKeyPressed
        Valid.pindah(evt,LaporResikoJatuh,ADLMandi);
    }//GEN-LAST:event_KetLaporResikoJatuhKeyPressed

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

    private void BtnTambahRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRencanaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterRencanaKeperawatanPsikiatri form=new MasterRencanaKeperawatanPsikiatri(null,false);
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

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatanPsikiatri form=new MasterMasalahKeperawatanPsikiatri(null,false);
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
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KetDokter.requestFocus();
        }
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KetDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BMIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyPressed
        Valid.pindah(evt,TB,LaporStatusNutrisi);
    }//GEN-LAST:event_BMIKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRalanPsikiatri dialog = new RMPenilaianAwalKeperawatanRalanPsikiatri(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADLBab;
    private widget.ComboBox ADLBak;
    private widget.ComboBox ADLBerpakaian;
    private widget.ComboBox ADLHobi;
    private widget.ComboBox ADLKegiatan;
    private widget.ComboBox ADLMakan;
    private widget.ComboBox ADLMandi;
    private widget.ComboBox ADLSosialisasi;
    private widget.TextBox Agama;
    private widget.TextBox BB;
    private widget.ComboBox BJM;
    private widget.TextBox BMI;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnAllMasalah;
    private widget.Button BtnAllRencana;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariMasalah;
    private widget.Button BtnCariRencana;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahMasalah;
    private widget.Button BtnTambahRencana;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.TextBox Durasi;
    private widget.ComboBox FPEkonomi;
    private widget.ComboBox FPMasalahFisik;
    private widget.ComboBox FPMasalahPsikososial;
    private widget.ComboBox FPPutusObat;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GCS;
    private widget.ComboBox HasilResikoJatuh;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.ComboBox KKBahasaIsyarat;
    private widget.ComboBox KKKebutuhanEdukasi;
    private widget.ComboBox KKPembelajaran;
    private widget.ComboBox KKPenerjamah;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetADLHobi;
    private widget.TextBox KetADLKegiatan;
    private widget.TextBox KetADLSosialisasi;
    private widget.TextBox KetAlasanPenggunaan;
    private widget.TextBox KetAlergiObat;
    private widget.TextBox KetCaraPemakaian;
    private widget.TextBox KetDokter;
    private widget.TextBox KetKKKebutuhanEdukasi;
    private widget.ComboBox KetKKPembelajaran;
    private widget.TextBox KetKKPembelajaranLainnya;
    private widget.TextBox KetKKPenerjamahLainnya;
    private widget.TextBox KetKeluhanFisik;
    private widget.TextBox KetLamaPemakaian;
    private widget.TextBox KetLaporResikoJatuh;
    private widget.TextBox KetLaporStatusNutrisi;
    private widget.TextBox KetLatarBelakangPemakaian;
    private widget.TextBox KetMasalahEkonomi;
    private widget.TextBox KetMasalahFisik;
    private widget.TextBox KetMasalahPsikososial;
    private widget.TextBox KetMerokok;
    private widget.TextBox KetMinumKopi;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetPenggunaanObatLainnya;
    private widget.TextBox KetProvokes;
    private widget.TextBox KetPutusObat;
    private widget.TextBox KetQuality;
    private widget.TextBox KetRBDAlat;
    private widget.TextBox KetRBDIde;
    private widget.TextBox KetRBDKeinginan;
    private widget.TextBox KetRBDPercobaan;
    private widget.TextBox KetRBDRencana;
    private widget.TextBox KetRHKeluarga;
    private widget.TextBox KetRPOEfekSamping;
    private widget.TextBox KetRPONapza;
    private widget.TextBox KetRPOPenggunaan;
    private widget.TextBox KetSKDayaTilikDiri;
    private widget.TextBox KetSKPersepsi;
    private widget.TextBox KetSKWaham;
    private widget.Label LCount;
    private widget.ComboBox LaporResikoJatuh;
    private widget.ComboBox LaporStatusNutrisi;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.ComboBox MSA;
    private widget.ComboBox Menyebar;
    private widget.TextBox Nadi;
    private widget.ComboBox Nilai1;
    private widget.ComboBox Nilai2;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.ComboBox PFKeluhanFisik;
    private widget.ComboBox PadaDokter;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Provokes;
    private widget.ComboBox Quality;
    private widget.ComboBox RBDAlat;
    private widget.ComboBox RBDIde;
    private widget.ComboBox RBDKeinginan;
    private widget.ComboBox RBDPercobaan;
    private widget.ComboBox RBDRencana;
    private widget.ComboBox RHKeluarga;
    private widget.ComboBox RKDBerobat;
    private widget.ComboBox RKDHasilPengobatan;
    private widget.TextArea RKDKeluhan;
    private widget.TextBox RKDSakitSejak;
    private widget.ComboBox RPOAlergiObat;
    private widget.ComboBox RPOEfekSamping;
    private widget.ComboBox RPOMerokok;
    private widget.ComboBox RPOMinumKopi;
    private widget.ComboBox RPONapza;
    private widget.ComboBox RPOPenggunaan;
    private widget.ComboBox RPOPenggunaanObatLainnya;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ComboBox ResikoBunuhDiri;
    private widget.ComboBox ResikoJatuh;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SKAfek;
    private widget.ComboBox SKAktifitasMotorik;
    private widget.ComboBox SKAlamPerasaan;
    private widget.ComboBox SKDayaTilikDiri;
    private widget.ComboBox SKGangguanRingan;
    private widget.ComboBox SKInteraksi;
    private widget.ComboBox SKIsiPikir;
    private widget.ComboBox SKKonsentrasi;
    private widget.ComboBox SKMemori;
    private widget.ComboBox SKOrientasi;
    private widget.ComboBox SKPembicaraan;
    private widget.ComboBox SKPenampilan;
    private widget.ComboBox SKPersepsi;
    private widget.ComboBox SKProsesPikir;
    private widget.ComboBox SKTingkatKesadaranOrientasi;
    private widget.ComboBox SKWaham;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ComboBox SkalaNyeri;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TCariRencana;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRencanaKeperawatan;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalHasil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
    private widget.Label jLabel107;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
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
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
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
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel43;
    private widget.Label jLabel50;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
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
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane4;
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
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan_psikiatri.tanggal,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.informasi,penilaian_awal_keperawatan_ralan_psikiatri.keluhan_utama,penilaian_awal_keperawatan_ralan_psikiatri.rkd_sakit_sejak,penilaian_awal_keperawatan_ralan_psikiatri.rkd_keluhan,penilaian_awal_keperawatan_ralan_psikiatri.rkd_berobat,penilaian_awal_keperawatan_ralan_psikiatri.rkd_hasil_pengobatan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.fp_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.fp_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_fisik,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.ket_rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.resiko_bunuh_diri,penilaian_awal_keperawatan_ralan_psikiatri.rbd_ide,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_ide,penilaian_awal_keperawatan_ralan_psikiatri.rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.rbd_percobaan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_percobaan,penilaian_awal_keperawatan_ralan_psikiatri.rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_efek_samping,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_efek_samping,penilaian_awal_keperawatan_ralan_psikiatri.rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_lama_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_cara_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_latar_belakang_pemakaian,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_alasan_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_alergi_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_alergi_obat,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rpo_merokok,penilaian_awal_keperawatan_ralan_psikiatri.ket_merokok,penilaian_awal_keperawatan_ralan_psikiatri.rpo_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.ket_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.td,penilaian_awal_keperawatan_ralan_psikiatri.nadi,penilaian_awal_keperawatan_ralan_psikiatri.gcs,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rr,penilaian_awal_keperawatan_ralan_psikiatri.suhu,penilaian_awal_keperawatan_ralan_psikiatri.pf_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.skala_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.durasi,penilaian_awal_keperawatan_ralan_psikiatri.nyeri,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.provokes,penilaian_awal_keperawatan_ralan_psikiatri.ket_provokes,penilaian_awal_keperawatan_ralan_psikiatri.quality,penilaian_awal_keperawatan_ralan_psikiatri.ket_quality,penilaian_awal_keperawatan_ralan_psikiatri.lokasi,penilaian_awal_keperawatan_ralan_psikiatri.menyebar,penilaian_awal_keperawatan_ralan_psikiatri.pada_dokter,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_dokter,penilaian_awal_keperawatan_ralan_psikiatri.nyeri_hilang,penilaian_awal_keperawatan_ralan_psikiatri.ket_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.bb,penilaian_awal_keperawatan_ralan_psikiatri.tb,penilaian_awal_keperawatan_ralan_psikiatri.bmi,penilaian_awal_keperawatan_ralan_psikiatri.lapor_status_nutrisi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor_status_nutrisi,penilaian_awal_keperawatan_ralan_psikiatri.sg1,penilaian_awal_keperawatan_ralan_psikiatri.nilai1,penilaian_awal_keperawatan_ralan_psikiatri.sg2,penilaian_awal_keperawatan_ralan_psikiatri.nilai2,penilaian_awal_keperawatan_ralan_psikiatri.total_hasil,penilaian_awal_keperawatan_ralan_psikiatri.resikojatuh,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.bjm,penilaian_awal_keperawatan_ralan_psikiatri.msa,penilaian_awal_keperawatan_ralan_psikiatri.hasil,penilaian_awal_keperawatan_ralan_psikiatri.lapor,penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor,penilaian_awal_keperawatan_ralan_psikiatri.adl_mandi,penilaian_awal_keperawatan_ralan_psikiatri.adl_berpakaian,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.adl_makan,penilaian_awal_keperawatan_ralan_psikiatri.adl_bak,penilaian_awal_keperawatan_ralan_psikiatri.adl_bab,penilaian_awal_keperawatan_ralan_psikiatri.adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.adl_sosialisasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_sosialisasi,penilaian_awal_keperawatan_ralan_psikiatri.adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.sk_penampilan,penilaian_awal_keperawatan_ralan_psikiatri.sk_alam_perasaan,penilaian_awal_keperawatan_ralan_psikiatri.sk_pembicaraan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_afek,penilaian_awal_keperawatan_ralan_psikiatri.sk_aktifitas_motorik,penilaian_awal_keperawatan_ralan_psikiatri.sk_gangguan_ringan,penilaian_awal_keperawatan_ralan_psikiatri.sk_proses_pikir,penilaian_awal_keperawatan_ralan_psikiatri.sk_orientasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_tingkat_kesadaran_orientasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_memori,penilaian_awal_keperawatan_ralan_psikiatri.sk_interaksi,penilaian_awal_keperawatan_ralan_psikiatri.sk_konsentrasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.sk_isi_pikir,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.kk_pembelajaran,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran,"+       
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_penerjamah,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_penerjamah_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_bahasa_isyarat,penilaian_awal_keperawatan_ralan_psikiatri.kk_kebutuhan_edukasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_kebutuhan_edukasi,penilaian_awal_keperawatan_ralan_psikiatri.rencana,penilaian_awal_keperawatan_ralan_psikiatri.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan_psikiatri on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan_psikiatri.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan_psikiatri.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan_psikiatri.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan_psikiatri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan_psikiatri.tanggal,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.informasi,penilaian_awal_keperawatan_ralan_psikiatri.keluhan_utama,penilaian_awal_keperawatan_ralan_psikiatri.rkd_sakit_sejak,penilaian_awal_keperawatan_ralan_psikiatri.rkd_keluhan,penilaian_awal_keperawatan_ralan_psikiatri.rkd_berobat,penilaian_awal_keperawatan_ralan_psikiatri.rkd_hasil_pengobatan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.fp_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_putus_obat,penilaian_awal_keperawatan_ralan_psikiatri.fp_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_ekonomi,penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_fisik,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.fp_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.ket_masalah_psikososial,penilaian_awal_keperawatan_ralan_psikiatri.rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.ket_rh_keluarga,penilaian_awal_keperawatan_ralan_psikiatri.resiko_bunuh_diri,penilaian_awal_keperawatan_ralan_psikiatri.rbd_ide,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_ide,penilaian_awal_keperawatan_ralan_psikiatri.rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_rencana,penilaian_awal_keperawatan_ralan_psikiatri.rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_alat,penilaian_awal_keperawatan_ralan_psikiatri.rbd_percobaan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_percobaan,penilaian_awal_keperawatan_ralan_psikiatri.rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rbd_keinginan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_efek_samping,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_efek_samping,penilaian_awal_keperawatan_ralan_psikiatri.rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_rpo_napza,penilaian_awal_keperawatan_ralan_psikiatri.ket_lama_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_cara_pemakaian,penilaian_awal_keperawatan_ralan_psikiatri.ket_latar_belakang_pemakaian,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rpo_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_penggunaan_obat_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.ket_alasan_penggunaan,penilaian_awal_keperawatan_ralan_psikiatri.rpo_alergi_obat,penilaian_awal_keperawatan_ralan_psikiatri.ket_alergi_obat,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rpo_merokok,penilaian_awal_keperawatan_ralan_psikiatri.ket_merokok,penilaian_awal_keperawatan_ralan_psikiatri.rpo_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.ket_minum_kopi,penilaian_awal_keperawatan_ralan_psikiatri.td,penilaian_awal_keperawatan_ralan_psikiatri.nadi,penilaian_awal_keperawatan_ralan_psikiatri.gcs,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.rr,penilaian_awal_keperawatan_ralan_psikiatri.suhu,penilaian_awal_keperawatan_ralan_psikiatri.pf_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.ket_keluhan_fisik,penilaian_awal_keperawatan_ralan_psikiatri.skala_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.durasi,penilaian_awal_keperawatan_ralan_psikiatri.nyeri,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.provokes,penilaian_awal_keperawatan_ralan_psikiatri.ket_provokes,penilaian_awal_keperawatan_ralan_psikiatri.quality,penilaian_awal_keperawatan_ralan_psikiatri.ket_quality,penilaian_awal_keperawatan_ralan_psikiatri.lokasi,penilaian_awal_keperawatan_ralan_psikiatri.menyebar,penilaian_awal_keperawatan_ralan_psikiatri.pada_dokter,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_dokter,penilaian_awal_keperawatan_ralan_psikiatri.nyeri_hilang,penilaian_awal_keperawatan_ralan_psikiatri.ket_nyeri,penilaian_awal_keperawatan_ralan_psikiatri.bb,penilaian_awal_keperawatan_ralan_psikiatri.tb,penilaian_awal_keperawatan_ralan_psikiatri.bmi,penilaian_awal_keperawatan_ralan_psikiatri.lapor_status_nutrisi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor_status_nutrisi,penilaian_awal_keperawatan_ralan_psikiatri.sg1,penilaian_awal_keperawatan_ralan_psikiatri.nilai1,penilaian_awal_keperawatan_ralan_psikiatri.sg2,penilaian_awal_keperawatan_ralan_psikiatri.nilai2,penilaian_awal_keperawatan_ralan_psikiatri.total_hasil,penilaian_awal_keperawatan_ralan_psikiatri.resikojatuh,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.bjm,penilaian_awal_keperawatan_ralan_psikiatri.msa,penilaian_awal_keperawatan_ralan_psikiatri.hasil,penilaian_awal_keperawatan_ralan_psikiatri.lapor,penilaian_awal_keperawatan_ralan_psikiatri.ket_lapor,penilaian_awal_keperawatan_ralan_psikiatri.adl_mandi,penilaian_awal_keperawatan_ralan_psikiatri.adl_berpakaian,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.adl_makan,penilaian_awal_keperawatan_ralan_psikiatri.adl_bak,penilaian_awal_keperawatan_ralan_psikiatri.adl_bab,penilaian_awal_keperawatan_ralan_psikiatri.adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_hobi,penilaian_awal_keperawatan_ralan_psikiatri.adl_sosialisasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_sosialisasi,penilaian_awal_keperawatan_ralan_psikiatri.adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.ket_adl_kegiatan,penilaian_awal_keperawatan_ralan_psikiatri.sk_penampilan,penilaian_awal_keperawatan_ralan_psikiatri.sk_alam_perasaan,penilaian_awal_keperawatan_ralan_psikiatri.sk_pembicaraan,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_afek,penilaian_awal_keperawatan_ralan_psikiatri.sk_aktifitas_motorik,penilaian_awal_keperawatan_ralan_psikiatri.sk_gangguan_ringan,penilaian_awal_keperawatan_ralan_psikiatri.sk_proses_pikir,penilaian_awal_keperawatan_ralan_psikiatri.sk_orientasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_tingkat_kesadaran_orientasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_memori,penilaian_awal_keperawatan_ralan_psikiatri.sk_interaksi,penilaian_awal_keperawatan_ralan_psikiatri.sk_konsentrasi,penilaian_awal_keperawatan_ralan_psikiatri.sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_persepsi,penilaian_awal_keperawatan_ralan_psikiatri.sk_isi_pikir,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_waham,penilaian_awal_keperawatan_ralan_psikiatri.sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.ket_sk_daya_tilik_diri,penilaian_awal_keperawatan_ralan_psikiatri.kk_pembelajaran,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran,"+       
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_pembelajaran_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_penerjamah,penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_penerjamah_lainnya,penilaian_awal_keperawatan_ralan_psikiatri.kk_bahasa_isyarat,penilaian_awal_keperawatan_ralan_psikiatri.kk_kebutuhan_edukasi,"+
                        "penilaian_awal_keperawatan_ralan_psikiatri.ket_kk_kebutuhan_edukasi,penilaian_awal_keperawatan_ralan_psikiatri.rencana,penilaian_awal_keperawatan_ralan_psikiatri.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan_psikiatri on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan_psikiatri.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan_psikiatri.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan_psikiatri.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_awal_keperawatan_ralan_psikiatri.nip like ? or petugas.nama like ?) "+
                        "order by penilaian_awal_keperawatan_ralan_psikiatri.tanggal");
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
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("keluhan_utama"),rs.getString("rkd_sakit_sejak"),rs.getString("rkd_keluhan"),
                        rs.getString("rkd_berobat"),rs.getString("rkd_hasil_pengobatan"),rs.getString("fp_putus_obat"),rs.getString("ket_putus_obat"),
                        rs.getString("fp_ekonomi"),rs.getString("ket_masalah_ekonomi"),rs.getString("fp_masalah_fisik"),rs.getString("ket_masalah_fisik"),rs.getString("fp_masalah_psikososial"),
                        rs.getString("ket_masalah_psikososial"),rs.getString("rh_keluarga"),rs.getString("ket_rh_keluarga"),rs.getString("resiko_bunuh_diri"),rs.getString("rbd_ide"),
                        rs.getString("ket_rbd_ide"),rs.getString("rbd_rencana"),rs.getString("ket_rbd_rencana"),rs.getString("rbd_alat"),rs.getString("ket_rbd_alat"),rs.getString("rbd_percobaan"),
                        rs.getString("ket_rbd_percobaan"),rs.getString("rbd_keinginan"),rs.getString("ket_rbd_keinginan"),rs.getString("rpo_penggunaan"),rs.getString("ket_rpo_penggunaan"),
                        rs.getString("rpo_efek_samping"),rs.getString("ket_rpo_efek_samping"),rs.getString("rpo_napza"),rs.getString("ket_rpo_napza"),rs.getString("ket_lama_pemakaian"),
                        rs.getString("ket_cara_pemakaian"),rs.getString("ket_latar_belakang_pemakaian"),rs.getString("rpo_penggunaan_obat_lainnya"),rs.getString("ket_penggunaan_obat_lainnya"),
                        rs.getString("ket_alasan_penggunaan"),rs.getString("rpo_alergi_obat"),rs.getString("ket_alergi_obat"),rs.getString("rpo_merokok"),rs.getString("ket_merokok"),
                        rs.getString("rpo_minum_kopi"),rs.getString("ket_minum_kopi"),rs.getString("td"),rs.getString("nadi"),rs.getString("gcs"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("pf_keluhan_fisik"),rs.getString("ket_keluhan_fisik"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri"),rs.getString("provokes"),
                        rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("pada_dokter"),
                        rs.getString("ket_dokter"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("lapor_status_nutrisi"),
                        rs.getString("ket_lapor_status_nutrisi"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),rs.getString("total_hasil"),
                        rs.getString("resikojatuh"),rs.getString("bjm"),rs.getString("msa"),rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("adl_mandi"),
                        rs.getString("adl_berpakaian"),rs.getString("adl_makan"),rs.getString("adl_bak"),rs.getString("adl_bab"),rs.getString("adl_hobi"),rs.getString("ket_adl_hobi"),
                        rs.getString("adl_sosialisasi"),rs.getString("ket_adl_sosialisasi"),rs.getString("adl_kegiatan"),rs.getString("ket_adl_kegiatan"),rs.getString("sk_penampilan"),
                        rs.getString("sk_alam_perasaan"),rs.getString("sk_pembicaraan"),rs.getString("sk_afek"),rs.getString("sk_aktifitas_motorik"),rs.getString("sk_gangguan_ringan"),
                        rs.getString("sk_proses_pikir"),rs.getString("sk_orientasi"),rs.getString("sk_tingkat_kesadaran_orientasi"),rs.getString("sk_memori"),rs.getString("sk_interaksi"),
                        rs.getString("sk_konsentrasi"),rs.getString("sk_persepsi"),rs.getString("ket_sk_persepsi"),rs.getString("sk_isi_pikir"),rs.getString("sk_waham"),rs.getString("ket_sk_waham"),
                        rs.getString("sk_daya_tilik_diri"),rs.getString("ket_sk_daya_tilik_diri"),rs.getString("kk_pembelajaran"),rs.getString("ket_kk_pembelajaran"),rs.getString("ket_kk_pembelajaran_lainnya"),
                        rs.getString("kk_Penerjamah"),rs.getString("ket_kk_penerjamah_Lainnya"),rs.getString("kk_bahasa_isyarat"),rs.getString("kk_kebutuhan_edukasi"),rs.getString("ket_kk_kebutuhan_edukasi"),
                        rs.getString("rencana"),rs.getString("nip"),rs.getString("nama")
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
        KeluhanUtama.setText("");
        BMI.setText("");
        RKDKeluhan.setText("");
        RKDBerobat.setSelectedIndex(0);
        RKDHasilPengobatan.setSelectedIndex(0);
        FPPutusObat.setSelectedIndex(0);
        KetPutusObat.setText("");
        FPEkonomi.setSelectedIndex(0);
        KetMasalahEkonomi.setText("");
        FPMasalahFisik.setSelectedIndex(0);
        KetMasalahFisik.setText("");
        FPMasalahPsikososial.setSelectedIndex(0);
        KetMasalahPsikososial.setText("");
        RHKeluarga.setSelectedIndex(0);
        KetRHKeluarga.setText(""); 
        ResikoBunuhDiri.setSelectedIndex(0);
        RBDIde.setSelectedIndex(0);
        KetRBDIde.setText("");
        RBDRencana.setSelectedIndex(0);
        KetRBDRencana.setText("");
        RBDAlat.setSelectedIndex(0);
        KetRBDAlat.setText("");
        RBDPercobaan.setSelectedIndex(0);
        KetRBDPercobaan.setText("");
        RBDKeinginan.setSelectedIndex(0);
        KetRBDKeinginan.setText("");
        RPOPenggunaan.setSelectedIndex(0);
        KetRPOPenggunaan.setText("");
        RPOEfekSamping.setSelectedIndex(0);
        KetRPOEfekSamping.setText("");
        RPONapza.setSelectedIndex(0);
        KetRPONapza.setText("");
        KetLamaPemakaian.setText("");
        KetCaraPemakaian.setText("");
        KetLatarBelakangPemakaian.setText("");
        RPOPenggunaanObatLainnya.setSelectedIndex(0);
        KetPenggunaanObatLainnya.setText("");
        KetAlasanPenggunaan.setText("");
        RPOAlergiObat.setSelectedIndex(0);
        KetAlergiObat.setText("");
        RPOMerokok.setSelectedIndex(0);
        KetMerokok.setText("");
        RPOMinumKopi.setSelectedIndex(0);
        KetMinumKopi.setText("");         
        TD.setText("");
        Nadi.setText("");
        GCS.setText("");        
        RR.setText("");
        Suhu.setText("");
        PFKeluhanFisik.setSelectedIndex(0);
        KetKeluhanFisik.setText("");
        SkalaNyeri.setSelectedIndex(0);
        Durasi.setText("");
        Nyeri.setSelectedIndex(0);
        Provokes.setSelectedIndex(0);
        KetProvokes.setText("");
        Quality.setSelectedIndex(0);
        KetQuality.setText("");
        Lokasi.setText("");
        Menyebar.setSelectedIndex(0);
        PadaDokter.setSelectedIndex(0);
        KetDokter.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        BB.setText("");
        TB.setText("");
        RKDSakitSejak.setText("");
        LaporStatusNutrisi.setSelectedIndex(0);
        KetLaporStatusNutrisi.setText("");
        SG1.setSelectedIndex(0);
        Nilai1.setSelectedIndex(0);
        SG2.setSelectedIndex(0);
        Nilai2.setSelectedIndex(0);
        TotalHasil.setText("0");
        ResikoJatuh.setSelectedIndex(0);
        BJM.setSelectedIndex(0);
        MSA.setSelectedIndex(0);
        HasilResikoJatuh.setSelectedIndex(0);
        LaporResikoJatuh.setSelectedIndex(0);
        KetLaporResikoJatuh.setText("");
        ADLMandi.setSelectedIndex(0);
        ADLBerpakaian.setSelectedIndex(0);
        ADLMakan.setSelectedIndex(0);
        ADLBak.setSelectedIndex(0);
        ADLBab.setSelectedIndex(0);
        ADLHobi.setSelectedIndex(0);
        KetADLHobi.setText("");
        ADLSosialisasi.setSelectedIndex(0);
        KetADLSosialisasi.setText("");
        ADLKegiatan.setSelectedIndex(0);
        KetADLKegiatan.setText("");
        SKPenampilan.setSelectedIndex(0);
        SKAlamPerasaan.setSelectedIndex(0);
        SKPembicaraan.setSelectedIndex(0);
        SKAfek.setSelectedIndex(0);
        SKAktifitasMotorik.setSelectedIndex(0);
        SKGangguanRingan.setSelectedIndex(0);
        SKProsesPikir.setSelectedIndex(0);
        SKOrientasi.setSelectedIndex(0);
        SKTingkatKesadaranOrientasi.setSelectedIndex(0);
        SKMemori.setSelectedIndex(0);
        SKInteraksi.setSelectedIndex(0);
        SKKonsentrasi.setSelectedIndex(0);
        SKPersepsi.setSelectedIndex(0);
        KetSKPersepsi.setText("");
        SKIsiPikir.setSelectedIndex(0);
        SKWaham.setSelectedIndex(0);
        KetSKWaham.setText("");
        SKDayaTilikDiri.setSelectedIndex(0);
        KetSKDayaTilikDiri.setText("");
        KKPembelajaran.setSelectedIndex(0);
        KetKKPembelajaran.setSelectedIndex(0);
        KetKKPembelajaranLainnya.setText("");
        KKPenerjamah.setSelectedIndex(0);
        KetKKPenerjamahLainnya.setText("");
        KKBahasaIsyarat.setSelectedIndex(0);
        KKKebutuhanEdukasi.setSelectedIndex(0);
        KetKKKebutuhanEdukasi.setText("");
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabModeRencana);
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
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            RKDSakitSejak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RKDKeluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            RKDBerobat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RKDHasilPengobatan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            FPPutusObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KetPutusObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            FPEkonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KetMasalahEkonomi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            FPMasalahFisik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KetMasalahFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            FPMasalahPsikososial.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KetMasalahPsikososial.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            RHKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KetRHKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            ResikoBunuhDiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            RBDIde.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KetRBDIde.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            RBDRencana.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KetRBDRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            RBDAlat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KetRBDAlat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            RBDPercobaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            KetRBDPercobaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            RBDKeinginan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KetRBDKeinginan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            RPOPenggunaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KetRPOPenggunaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            RPOEfekSamping.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            KetRPOEfekSamping.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            RPONapza.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            KetRPONapza.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            KetLamaPemakaian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            KetCaraPemakaian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            KetLatarBelakangPemakaian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            RPOPenggunaanObatLainnya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            KetPenggunaanObatLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            KetAlasanPenggunaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            RPOAlergiObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            KetAlergiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            RPOMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            KetMerokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            RPOMinumKopi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            KetMinumKopi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            PFKeluhanFisik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            KetKeluhanFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            PadaDokter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            KetDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            LaporStatusNutrisi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            KetLaporStatusNutrisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Nilai1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Nilai2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            ResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            BJM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            MSA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            HasilResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            LaporResikoJatuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            KetLaporResikoJatuh.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            ADLMandi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            ADLBerpakaian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            ADLMakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            ADLBak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            ADLBab.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            ADLHobi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            KetADLHobi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            ADLSosialisasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            KetADLSosialisasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            ADLKegiatan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            KetADLKegiatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            SKPenampilan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            SKAlamPerasaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            SKPembicaraan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            SKAfek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            SKAktifitasMotorik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            SKGangguanRingan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            SKProsesPikir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
            SKOrientasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),108).toString());
            SKTingkatKesadaranOrientasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),109).toString());
            SKMemori.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),110).toString());
            SKInteraksi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),111).toString());
            SKKonsentrasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),112).toString());
            SKPersepsi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),113).toString());
            KetSKPersepsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),114).toString());
            SKIsiPikir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),115).toString());
            SKWaham.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),116).toString());
            KetSKWaham.setText(tbObat.getValueAt(tbObat.getSelectedRow(),117).toString());
            SKDayaTilikDiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),118).toString());
            KetSKDayaTilikDiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),119).toString());
            KKPembelajaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),120).toString());
            KetKKPembelajaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),121).toString());
            KetKKPembelajaranLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),122).toString());
            KKPenerjamah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),123).toString());
            KetKKPenerjamahLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),124).toString());
            KKBahasaIsyarat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),125).toString());
            KKKebutuhanEdukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),126).toString());
            KetKKKebutuhanEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),127).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),128).toString());
            Valid.tabelKosong(tabModeMasalah);
            Valid.tabelKosong(tabModeRencana);
            for (i = 0; i < tbMasalahDetail.getRowCount(); i++) {
                tabModeMasalah.addRow(new Object[]{
                    true,tbMasalahDetail.getValueAt(i,0).toString(),tbMasalahDetail.getValueAt(i,1).toString()
                });
            }
            for (i = 0; i < tbRencanaDetail.getRowCount(); i++) {
                tabModeRencana.addRow(new Object[]{
                    true,tbRencanaDetail.getValueAt(i,0).toString(),tbRencanaDetail.getValueAt(i,1).toString()
                });
            }
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,"+
                    "pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,reg_periksa.tgl_registrasi, "+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_psikiatri());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_psikiatri());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_psikiatri());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_psikiatri());
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan());  
        BtnTambahRencana.setEnabled(akses.getmaster_rencana_keperawatan());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
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
    
    private void tampilMasalah() {
        try{
            Valid.tabelKosong(tabModeMasalah);
            file=new File("./cache/masalahkeperawatanpsikiatri.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_psikiatri order by master_masalah_keperawatan_psikiatri.kode_masalah");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
                    iyembuilder.append("{\"KodeMasalah\":\"").append(rs.getString(1)).append("\",\"NamaMasalah\":\"").append(rs.getString(2)).append("\"},");
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
                fileWriter.write("{\"masalahkeperawatanpsikiatri\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
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

            pilih=new boolean[jml]; 
            kode=new String[jml];
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
            
            pilih=null;
            kode=null;
            masalah=null;
            
            myObj = new FileReader("./cache/masalahkeperawatanpsikiatri.iyem");
            root = mapper.readTree(myObj);
            response = root.path("masalahkeperawatanpsikiatri");
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
            file=new File("./cache/rencanakeperawatanpsikiatri.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select * from master_rencana_keperawatan_psikiatri order by master_rencana_keperawatan_psikiatri.kode_rencana");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodeMasalah\":\"").append(rs.getString(1)).append("\",\"KodeRencana\":\"").append(rs.getString(2)).append("\",\"NamaRencana\":\"").append(rs.getString(3)).append("\"},");
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
                fileWriter.write("{\"rencanakeperawatanpsikiatri\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
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

            pilih=new boolean[jml]; 
            kode=new String[jml];
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
            
            pilih=null;
            kode=null;
            masalah=null;

            myObj = new FileReader("./cache/rencanakeperawatanpsikiatri.iyem");
            root = mapper.readTree(myObj);
            response = root.path("rencanakeperawatanpsikiatri");
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
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),128).toString());
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_psikiatri.kode_masalah,master_masalah_keperawatan_psikiatri.nama_masalah from master_masalah_keperawatan_psikiatri "+
                        "inner join penilaian_awal_keperawatan_ralan_masalah_psikiatri on penilaian_awal_keperawatan_ralan_masalah_psikiatri.kode_masalah=master_masalah_keperawatan_psikiatri.kode_masalah "+
                        "where penilaian_awal_keperawatan_ralan_masalah_psikiatri.no_rawat=? order by penilaian_awal_keperawatan_ralan_masalah_psikiatri.kode_masalah");
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
                        "select master_rencana_keperawatan_psikiatri.kode_rencana,master_rencana_keperawatan_psikiatri.rencana_keperawatan from master_rencana_keperawatan_psikiatri "+
                        "inner join penilaian_awal_keperawatan_ralan_rencana_psikiatri on penilaian_awal_keperawatan_ralan_rencana_psikiatri.kode_rencana=master_rencana_keperawatan_psikiatri.kode_rencana "+
                        "where penilaian_awal_keperawatan_ralan_rencana_psikiatri.no_rawat=? order by penilaian_awal_keperawatan_ralan_rencana_psikiatri.kode_rencana");
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
    
    private void isBMI(){
        if((!TB.getText().equals(""))&&(!BB.getText().equals(""))){
            try {
                BMI.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText())/((Valid.SetAngka(TB.getText())/100)*(Valid.SetAngka(TB.getText())/100)),1)+"");
            } catch (Exception e) {
                BMI.setText("");
            }
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ralan_psikiatri where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            TNoRM1.setText("");
            TPasien1.setText("");
            Valid.tabelKosong(tabModeDetailMasalah);
            Valid.tabelKosong(tabModeDetailRencana);
            ChkAccor.setSelected(false);
            isMenu();
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_awal_keperawatan_ralan_psikiatri","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,keluhan_utama=?,rkd_sakit_sejak=?,rkd_keluhan=?,rkd_berobat=?,rkd_hasil_pengobatan=?,fp_putus_obat=?,ket_putus_obat=?,fp_ekonomi=?,ket_masalah_ekonomi=?,fp_masalah_fisik=?,ket_masalah_fisik=?,fp_masalah_psikososial=?,ket_masalah_psikososial=?,"+
                "rh_keluarga=?,ket_rh_keluarga=?,resiko_bunuh_diri=?,rbd_ide=?,ket_rbd_ide=?,rbd_rencana=?,ket_rbd_rencana=?,rbd_alat=?,ket_rbd_alat=?,rbd_percobaan=?,ket_rbd_percobaan=?,rbd_keinginan=?,ket_rbd_keinginan=?,rpo_penggunaan=?,ket_rpo_penggunaan=?,rpo_efek_samping=?,ket_rpo_efek_samping=?,rpo_napza=?,ket_rpo_napza=?,ket_lama_pemakaian=?,ket_cara_pemakaian=?,"+
                "ket_latar_belakang_pemakaian=?,rpo_penggunaan_obat_lainnya=?,ket_penggunaan_obat_lainnya=?,ket_alasan_penggunaan=?,rpo_alergi_obat=?,ket_alergi_obat=?,rpo_merokok=?,ket_merokok=?,rpo_minum_kopi=?,ket_minum_kopi=?,td=?,nadi=?,gcs=?,rr=?,suhu=?,pf_keluhan_fisik=?,ket_keluhan_fisik=?,skala_nyeri=?,durasi=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,"+
                "ket_quality=?,lokasi=?,menyebar=?,pada_dokter=?,ket_dokter=?,nyeri_hilang=?,ket_nyeri=?,bb=?,tb=?,bmi=?,lapor_status_nutrisi=?,ket_lapor_status_nutrisi=?,sg1=?,nilai1=?,sg2=?,nilai2=?,total_hasil=?,resikojatuh=?,bjm=?,msa=?,hasil=?,lapor=?,ket_lapor=?,adl_mandi=?,adl_berpakaian=?,adl_makan=?,adl_bak=?,adl_bab=?,adl_hobi=?,ket_adl_hobi=?,adl_sosialisasi=?,"+
                "ket_adl_sosialisasi=?,adl_kegiatan=?,ket_adl_kegiatan=?,sk_penampilan=?,sk_alam_perasaan=?,sk_pembicaraan=?,sk_afek=?,sk_aktifitas_motorik=?,sk_gangguan_ringan=?,sk_proses_pikir=?,sk_orientasi=?,sk_tingkat_kesadaran_orientasi=?,sk_memori=?,sk_interaksi=?,sk_konsentrasi=?,sk_persepsi=?,ket_sk_persepsi=?,sk_isi_pikir=?,sk_waham=?,ket_sk_waham=?,"+
                "sk_daya_tilik_diri=?,ket_sk_daya_tilik_diri=?,kk_pembelajaran=?,ket_kk_pembelajaran=?,ket_kk_pembelajaran_lainnya=?,kk_Penerjamah=?,ket_kk_penerjamah_Lainnya=?,kk_bahasa_isyarat=?,kk_kebutuhan_edukasi=?,ket_kk_kebutuhan_edukasi=?,rencana=?,nip=?",124,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),RKDSakitSejak.getText(),RKDKeluhan.getText(),RKDBerobat.getSelectedItem().toString(),RKDHasilPengobatan.getSelectedItem().toString(),
                FPPutusObat.getSelectedItem().toString(),KetPutusObat.getText(),FPEkonomi.getSelectedItem().toString(),KetMasalahEkonomi.getText(),FPMasalahFisik.getSelectedItem().toString(),KetMasalahFisik.getText(),FPMasalahPsikososial.getSelectedItem().toString(),KetMasalahPsikososial.getText(),RHKeluarga.getSelectedItem().toString(),KetRHKeluarga.getText(),
                ResikoBunuhDiri.getSelectedItem().toString(),RBDIde.getSelectedItem().toString(),KetRBDIde.getText(),RBDRencana.getSelectedItem().toString(),KetRBDRencana.getText(),RBDAlat.getSelectedItem().toString(),KetRBDAlat.getText(),RBDPercobaan.getSelectedItem().toString(),KetRBDPercobaan.getText(),RBDKeinginan.getSelectedItem().toString(),KetRBDKeinginan.getText(),RPOPenggunaan.getSelectedItem().toString(),
                KetRPOPenggunaan.getText(),RPOEfekSamping.getSelectedItem().toString(),KetRPOEfekSamping.getText(),RPONapza.getSelectedItem().toString(),KetRPONapza.getText(),KetLamaPemakaian.getText(),KetCaraPemakaian.getText(),KetLatarBelakangPemakaian.getText(),RPOPenggunaanObatLainnya.getSelectedItem().toString(),KetPenggunaanObatLainnya.getText(),KetAlasanPenggunaan.getText(),
                RPOAlergiObat.getSelectedItem().toString(),KetAlergiObat.getText(),RPOMerokok.getSelectedItem().toString(),KetMerokok.getText(),RPOMinumKopi.getSelectedItem().toString(),KetMinumKopi.getText(),
                TD.getText(),Nadi.getText(),GCS.getText(),RR.getText(),Suhu.getText(),PFKeluhanFisik.getSelectedItem().toString(),KetKeluhanFisik.getText(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),
                Lokasi.getText(),Menyebar.getSelectedItem().toString(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),BB.getText(),TB.getText(),BMI.getText(),LaporStatusNutrisi.getSelectedItem().toString(),KetLaporStatusNutrisi.getText(),
                SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),ResikoJatuh.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),HasilResikoJatuh.getSelectedItem().toString(),LaporResikoJatuh.getSelectedItem().toString(),KetLaporResikoJatuh.getText(),
                ADLMandi.getSelectedItem().toString(),ADLBerpakaian.getSelectedItem().toString(),ADLMakan.getSelectedItem().toString(),ADLBak.getSelectedItem().toString(),ADLBab.getSelectedItem().toString(),ADLHobi.getSelectedItem().toString(),KetADLHobi.getText(),ADLSosialisasi.getSelectedItem().toString(),KetADLSosialisasi.getText(),ADLKegiatan.getSelectedItem().toString(),KetADLKegiatan.getText(),
                SKPenampilan.getSelectedItem().toString(),SKAlamPerasaan.getSelectedItem().toString(),SKPembicaraan.getSelectedItem().toString(),SKAfek.getSelectedItem().toString(),SKAktifitasMotorik.getSelectedItem().toString(),SKGangguanRingan.getSelectedItem().toString(),SKProsesPikir.getSelectedItem().toString(),SKOrientasi.getSelectedItem().toString(),SKTingkatKesadaranOrientasi.getSelectedItem().toString(),
                SKMemori.getSelectedItem().toString(),SKInteraksi.getSelectedItem().toString(),SKKonsentrasi.getSelectedItem().toString(),SKPersepsi.getSelectedItem().toString(),KetSKPersepsi.getText(),SKIsiPikir.getSelectedItem().toString(),SKWaham.getSelectedItem().toString(),KetSKWaham.getText(),SKDayaTilikDiri.getSelectedItem().toString(),KetSKDayaTilikDiri.getText(),
                KKPembelajaran.getSelectedItem().toString(),KetKKPembelajaran.getSelectedItem().toString(),KetKKPembelajaranLainnya.getText(),KKPenerjamah.getSelectedItem().toString(),KetKKPenerjamahLainnya.getText(),KKBahasaIsyarat.getSelectedItem().toString(),KKKebutuhanEdukasi.getSelectedItem().toString(),KetKKKebutuhanEdukasi.getText(),
                Rencana.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Agama.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Bahasa.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(CacatFisik.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(RKDSakitSejak.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(RKDKeluhan.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(RKDBerobat.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(RKDHasilPengobatan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(FPPutusObat.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(KetPutusObat.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(KetMasalahEkonomi.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(FPMasalahFisik.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(FPMasalahFisik.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(KetMasalahFisik.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(FPMasalahPsikososial.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(KetMasalahPsikososial.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(RHKeluarga.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(KetRHKeluarga.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(ResikoBunuhDiri.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(RBDIde.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(KetRBDIde.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(RBDRencana.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(KetRBDRencana.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(RBDAlat.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(KetRBDAlat.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(RBDPercobaan.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(KetRBDPercobaan.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(RBDKeinginan.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(KetRBDKeinginan.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(RPOPenggunaan.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(KetRPOPenggunaan.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(RPOEfekSamping.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(KetRPOEfekSamping.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(RPONapza.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(KetRPONapza.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(KetLamaPemakaian.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(KetCaraPemakaian.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(KetLatarBelakangPemakaian.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(RPOPenggunaanObatLainnya.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(KetPenggunaanObatLainnya.getText(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(KetAlasanPenggunaan.getText(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(RPOAlergiObat.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(KetAlergiObat.getText(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(RPOMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(KetMerokok.getText(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(RPOMinumKopi.getSelectedItem().toString(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(KetMinumKopi.getText(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(GCS.getText(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),57);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),58);
                tbObat.setValueAt(PFKeluhanFisik.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
                tbObat.setValueAt(KetKeluhanFisik.getText(),tbObat.getSelectedRow(),60);
                tbObat.setValueAt(SkalaNyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
                tbObat.setValueAt(Durasi.getText(),tbObat.getSelectedRow(),62);
                tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),63);
                tbObat.setValueAt(Provokes.getSelectedItem().toString(),tbObat.getSelectedRow(),64);
                tbObat.setValueAt(KetProvokes.getText(),tbObat.getSelectedRow(),65);
                tbObat.setValueAt(Quality.getSelectedItem().toString(),tbObat.getSelectedRow(),66);
                tbObat.setValueAt(KetQuality.getText(),tbObat.getSelectedRow(),67);
                tbObat.setValueAt(Lokasi.getText(),tbObat.getSelectedRow(),68);
                tbObat.setValueAt(Menyebar.getSelectedItem().toString(),tbObat.getSelectedRow(),69);
                tbObat.setValueAt(PadaDokter.getSelectedItem().toString(),tbObat.getSelectedRow(),70);
                tbObat.setValueAt(KetDokter.getText(),tbObat.getSelectedRow(),71);
                tbObat.setValueAt(NyeriHilang.getSelectedItem().toString(),tbObat.getSelectedRow(),72);
                tbObat.setValueAt(KetNyeri.getText(),tbObat.getSelectedRow(),73);
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),74);
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),75);
                tbObat.setValueAt(BMI.getText(),tbObat.getSelectedRow(),76);
                tbObat.setValueAt(LaporStatusNutrisi.getSelectedItem().toString(),tbObat.getSelectedRow(),77);
                tbObat.setValueAt(KetLaporStatusNutrisi.getText(),tbObat.getSelectedRow(),78);
                tbObat.setValueAt(SG1.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
                tbObat.setValueAt(Nilai1.getSelectedItem().toString(),tbObat.getSelectedRow(),80);
                tbObat.setValueAt(SG2.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
                tbObat.setValueAt(Nilai2.getSelectedItem().toString(),tbObat.getSelectedRow(),82);
                tbObat.setValueAt(TotalHasil.getText(),tbObat.getSelectedRow(),83);
                tbObat.setValueAt(ResikoJatuh.getSelectedItem().toString(),tbObat.getSelectedRow(),84);
                tbObat.setValueAt(BJM.getSelectedItem().toString(),tbObat.getSelectedRow(),85);
                tbObat.setValueAt(MSA.getSelectedItem().toString(),tbObat.getSelectedRow(),86);
                tbObat.setValueAt(HasilResikoJatuh.getSelectedItem().toString(),tbObat.getSelectedRow(),87);
                tbObat.setValueAt(LaporResikoJatuh.getSelectedItem().toString(),tbObat.getSelectedRow(),88);
                tbObat.setValueAt(KetLaporResikoJatuh.getText(),tbObat.getSelectedRow(),89);
                tbObat.setValueAt(ADLMandi.getSelectedItem().toString(),tbObat.getSelectedRow(),90);
                tbObat.setValueAt(ADLBerpakaian.getSelectedItem().toString(),tbObat.getSelectedRow(),91);
                tbObat.setValueAt(ADLMakan.getSelectedItem().toString(),tbObat.getSelectedRow(),92);
                tbObat.setValueAt(ADLBak.getSelectedItem().toString(),tbObat.getSelectedRow(),93);
                tbObat.setValueAt(ADLBab.getSelectedItem().toString(),tbObat.getSelectedRow(),94);
                tbObat.setValueAt(ADLHobi.getSelectedItem().toString(),tbObat.getSelectedRow(),95);
                tbObat.setValueAt(KetADLHobi.getText(),tbObat.getSelectedRow(),96);
                tbObat.setValueAt(ADLSosialisasi.getSelectedItem().toString(),tbObat.getSelectedRow(),97);
                tbObat.setValueAt(KetADLSosialisasi.getText(),tbObat.getSelectedRow(),98);
                tbObat.setValueAt(ADLKegiatan.getSelectedItem().toString(),tbObat.getSelectedRow(),99);
                tbObat.setValueAt(KetADLKegiatan.getText(),tbObat.getSelectedRow(),100);
                tbObat.setValueAt(SKPenampilan.getSelectedItem().toString(),tbObat.getSelectedRow(),101);
                tbObat.setValueAt(SKAlamPerasaan.getSelectedItem().toString(),tbObat.getSelectedRow(),102);
                tbObat.setValueAt(SKPembicaraan.getSelectedItem().toString(),tbObat.getSelectedRow(),103);
                tbObat.setValueAt(SKAfek.getSelectedItem().toString(),tbObat.getSelectedRow(),104);
                tbObat.setValueAt(SKAktifitasMotorik.getSelectedItem().toString(),tbObat.getSelectedRow(),105);
                tbObat.setValueAt(SKGangguanRingan.getSelectedItem().toString(),tbObat.getSelectedRow(),106);
                tbObat.setValueAt(SKProsesPikir.getSelectedItem().toString(),tbObat.getSelectedRow(),107);
                tbObat.setValueAt(SKOrientasi.getSelectedItem().toString(),tbObat.getSelectedRow(),108);
                tbObat.setValueAt(SKTingkatKesadaranOrientasi.getSelectedItem().toString(),tbObat.getSelectedRow(),109);
                tbObat.setValueAt(SKMemori.getSelectedItem().toString(),tbObat.getSelectedRow(),110);
                tbObat.setValueAt(SKInteraksi.getSelectedItem().toString(),tbObat.getSelectedRow(),111);
                tbObat.setValueAt(SKKonsentrasi.getSelectedItem().toString(),tbObat.getSelectedRow(),112);
                tbObat.setValueAt(SKPersepsi.getSelectedItem().toString(),tbObat.getSelectedRow(),113);
                tbObat.setValueAt(KetSKPersepsi.getText(),tbObat.getSelectedRow(),114);
                tbObat.setValueAt(SKIsiPikir.getSelectedItem().toString(),tbObat.getSelectedRow(),115);
                tbObat.setValueAt(SKWaham.getSelectedItem().toString(),tbObat.getSelectedRow(),116);
                tbObat.setValueAt(KetSKWaham.getText(),tbObat.getSelectedRow(),117);
                tbObat.setValueAt(SKDayaTilikDiri.getSelectedItem().toString(),tbObat.getSelectedRow(),118);
                tbObat.setValueAt(KetSKDayaTilikDiri.getText(),tbObat.getSelectedRow(),119);
                tbObat.setValueAt(KKPembelajaran.getSelectedItem().toString(),tbObat.getSelectedRow(),120);
                tbObat.setValueAt(KetKKPembelajaran.getSelectedItem().toString(),tbObat.getSelectedRow(),121);
                tbObat.setValueAt(KetKKPembelajaranLainnya.getText(),tbObat.getSelectedRow(),122);
                tbObat.setValueAt(KKPenerjamah.getSelectedItem().toString(),tbObat.getSelectedRow(),123);
                tbObat.setValueAt(KetKKPenerjamahLainnya.getText(),tbObat.getSelectedRow(),124);
                tbObat.setValueAt(KKBahasaIsyarat.getSelectedItem().toString(),tbObat.getSelectedRow(),125);
                tbObat.setValueAt(KKKebutuhanEdukasi.getSelectedItem().toString(),tbObat.getSelectedRow(),126);
                tbObat.setValueAt(KetKKKebutuhanEdukasi.getText(),tbObat.getSelectedRow(),127);
                tbObat.setValueAt(Rencana.getText(),tbObat.getSelectedRow(),128);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),129);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),130);
                Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah_psikiatri","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Sequel.meghapus("penilaian_awal_keperawatan_ralan_rencana_psikiatri","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ralan_masalah_psikiatri","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailMasalah.addRow(new Object[]{
                                tbMasalahKeperawatan.getValueAt(i,1).toString(),tbMasalahKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }

                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ralan_rencana_psikiatri","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailRencana.addRow(new Object[]{
                                tbRencanaKeperawatan.getValueAt(i,1).toString(),tbRencanaKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                DetailRencana.setText(Rencana.getText());
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
                emptTeks();
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_awal_keperawatan_ralan_psikiatri","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",123,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),RKDSakitSejak.getText(),RKDKeluhan.getText(),RKDBerobat.getSelectedItem().toString(),RKDHasilPengobatan.getSelectedItem().toString(),
                FPPutusObat.getSelectedItem().toString(),KetPutusObat.getText(),FPEkonomi.getSelectedItem().toString(),KetMasalahEkonomi.getText(),FPMasalahFisik.getSelectedItem().toString(),KetMasalahFisik.getText(),FPMasalahPsikososial.getSelectedItem().toString(),KetMasalahPsikososial.getText(),RHKeluarga.getSelectedItem().toString(),KetRHKeluarga.getText(),
                ResikoBunuhDiri.getSelectedItem().toString(),RBDIde.getSelectedItem().toString(),KetRBDIde.getText(),RBDRencana.getSelectedItem().toString(),KetRBDRencana.getText(),RBDAlat.getSelectedItem().toString(),KetRBDAlat.getText(),RBDPercobaan.getSelectedItem().toString(),KetRBDPercobaan.getText(),RBDKeinginan.getSelectedItem().toString(),KetRBDKeinginan.getText(),RPOPenggunaan.getSelectedItem().toString(),
                KetRPOPenggunaan.getText(),RPOEfekSamping.getSelectedItem().toString(),KetRPOEfekSamping.getText(),RPONapza.getSelectedItem().toString(),KetRPONapza.getText(),KetLamaPemakaian.getText(),KetCaraPemakaian.getText(),KetLatarBelakangPemakaian.getText(),RPOPenggunaanObatLainnya.getSelectedItem().toString(),KetPenggunaanObatLainnya.getText(),KetAlasanPenggunaan.getText(),
                RPOAlergiObat.getSelectedItem().toString(),KetAlergiObat.getText(),RPOMerokok.getSelectedItem().toString(),KetMerokok.getText(),RPOMinumKopi.getSelectedItem().toString(),KetMinumKopi.getText(),
                TD.getText(),Nadi.getText(),GCS.getText(),RR.getText(),Suhu.getText(),PFKeluhanFisik.getSelectedItem().toString(),KetKeluhanFisik.getText(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),Nyeri.getSelectedItem().toString(),Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),
                Lokasi.getText(),Menyebar.getSelectedItem().toString(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),BB.getText(),TB.getText(),BMI.getText(),LaporStatusNutrisi.getSelectedItem().toString(),KetLaporStatusNutrisi.getText(),
                SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),ResikoJatuh.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),HasilResikoJatuh.getSelectedItem().toString(),LaporResikoJatuh.getSelectedItem().toString(),KetLaporResikoJatuh.getText(),
                ADLMandi.getSelectedItem().toString(),ADLBerpakaian.getSelectedItem().toString(),ADLMakan.getSelectedItem().toString(),ADLBak.getSelectedItem().toString(),ADLBab.getSelectedItem().toString(),ADLHobi.getSelectedItem().toString(),KetADLHobi.getText(),ADLSosialisasi.getSelectedItem().toString(),KetADLSosialisasi.getText(),ADLKegiatan.getSelectedItem().toString(),KetADLKegiatan.getText(),
                SKPenampilan.getSelectedItem().toString(),SKAlamPerasaan.getSelectedItem().toString(),SKPembicaraan.getSelectedItem().toString(),SKAfek.getSelectedItem().toString(),SKAktifitasMotorik.getSelectedItem().toString(),SKGangguanRingan.getSelectedItem().toString(),SKProsesPikir.getSelectedItem().toString(),SKOrientasi.getSelectedItem().toString(),SKTingkatKesadaranOrientasi.getSelectedItem().toString(),
                SKMemori.getSelectedItem().toString(),SKInteraksi.getSelectedItem().toString(),SKKonsentrasi.getSelectedItem().toString(),SKPersepsi.getSelectedItem().toString(),KetSKPersepsi.getText(),SKIsiPikir.getSelectedItem().toString(),SKWaham.getSelectedItem().toString(),KetSKWaham.getText(),SKDayaTilikDiri.getSelectedItem().toString(),KetSKDayaTilikDiri.getText(),
                KKPembelajaran.getSelectedItem().toString(),KetKKPembelajaran.getSelectedItem().toString(),KetKKPembelajaranLainnya.getText(),KKPenerjamah.getSelectedItem().toString(),KetKKPenerjamahLainnya.getText(),KKBahasaIsyarat.getSelectedItem().toString(),KKKebutuhanEdukasi.getSelectedItem().toString(),KetKKKebutuhanEdukasi.getText(),
                Rencana.getText(),KdPetugas.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Jk.getText(),Agama.getText(),Bahasa.getText(),CacatFisik.getText(),TglLahir.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),RKDSakitSejak.getText(),RKDKeluhan.getText(),RKDBerobat.getSelectedItem().toString(),
                    RKDHasilPengobatan.getSelectedItem().toString(),FPPutusObat.getSelectedItem().toString(),KetPutusObat.getText(),KetMasalahEkonomi.getText(),FPMasalahFisik.getSelectedItem().toString(),FPMasalahFisik.getSelectedItem().toString(),KetMasalahFisik.getText(),FPMasalahPsikososial.getSelectedItem().toString(),KetMasalahPsikososial.getText(),RHKeluarga.getSelectedItem().toString(),KetRHKeluarga.getText(),
                    ResikoBunuhDiri.getSelectedItem().toString(),RBDIde.getSelectedItem().toString(),KetRBDIde.getText(),RBDRencana.getSelectedItem().toString(),KetRBDRencana.getText(),RBDAlat.getSelectedItem().toString(),KetRBDAlat.getText(),RBDPercobaan.getSelectedItem().toString(),KetRBDPercobaan.getText(),RBDKeinginan.getSelectedItem().toString(),KetRBDKeinginan.getText(),RPOPenggunaan.getSelectedItem().toString(),
                    KetRPOPenggunaan.getText(),RPOEfekSamping.getSelectedItem().toString(),KetRPOEfekSamping.getText(),RPONapza.getSelectedItem().toString(),KetRPONapza.getText(),KetLamaPemakaian.getText(),KetCaraPemakaian.getText(),KetLatarBelakangPemakaian.getText(),RPOPenggunaanObatLainnya.getSelectedItem().toString(),KetPenggunaanObatLainnya.getText(),KetAlasanPenggunaan.getText(),RPOAlergiObat.getSelectedItem().toString(),
                    KetAlergiObat.getText(),RPOMerokok.getSelectedItem().toString(),KetMerokok.getText(),RPOMinumKopi.getSelectedItem().toString(),KetMinumKopi.getText(),TD.getText(),Nadi.getText(),GCS.getText(),RR.getText(),Suhu.getText(),PFKeluhanFisik.getSelectedItem().toString(),KetKeluhanFisik.getText(),SkalaNyeri.getSelectedItem().toString(),Durasi.getText(),Nyeri.getSelectedItem().toString(),
                    Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),BB.getText(),TB.getText(),BMI.getText(),LaporStatusNutrisi.getSelectedItem().toString(),KetLaporStatusNutrisi.getText(),
                    SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),ResikoJatuh.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),HasilResikoJatuh.getSelectedItem().toString(),LaporResikoJatuh.getSelectedItem().toString(),KetLaporResikoJatuh.getText(),
                    ADLMandi.getSelectedItem().toString(),ADLBerpakaian.getSelectedItem().toString(),ADLMakan.getSelectedItem().toString(),ADLBak.getSelectedItem().toString(),ADLBab.getSelectedItem().toString(),ADLHobi.getSelectedItem().toString(),KetADLHobi.getText(),ADLSosialisasi.getSelectedItem().toString(),KetADLSosialisasi.getText(),ADLKegiatan.getSelectedItem().toString(),KetADLKegiatan.getText(),
                    SKPenampilan.getSelectedItem().toString(),SKAlamPerasaan.getSelectedItem().toString(),SKPembicaraan.getSelectedItem().toString(),SKAfek.getSelectedItem().toString(),SKAktifitasMotorik.getSelectedItem().toString(),SKGangguanRingan.getSelectedItem().toString(),SKProsesPikir.getSelectedItem().toString(),SKOrientasi.getSelectedItem().toString(),SKTingkatKesadaranOrientasi.getSelectedItem().toString(),
                    SKMemori.getSelectedItem().toString(),SKInteraksi.getSelectedItem().toString(),SKKonsentrasi.getSelectedItem().toString(),SKPersepsi.getSelectedItem().toString(),KetSKPersepsi.getText(),SKIsiPikir.getSelectedItem().toString(),SKWaham.getSelectedItem().toString(),KetSKWaham.getText(),SKDayaTilikDiri.getSelectedItem().toString(),KetSKDayaTilikDiri.getText(),KKPembelajaran.getSelectedItem().toString(),
                    KetKKPembelajaran.getSelectedItem().toString(),KetKKPembelajaranLainnya.getText(),KKPenerjamah.getSelectedItem().toString(),KetKKPenerjamahLainnya.getText(),KKBahasaIsyarat.getSelectedItem().toString(),KKKebutuhanEdukasi.getSelectedItem().toString(),KetKKKebutuhanEdukasi.getText(),Rencana.getText(),KdPetugas.getText(),NmPetugas.getText()
                });
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ralan_masalah_psikiatri","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailMasalah.addRow(new Object[]{
                                tbMasalahKeperawatan.getValueAt(i,1).toString(),tbMasalahKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }

                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ralan_rencana_psikiatri","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailRencana.addRow(new Object[]{
                                tbRencanaKeperawatan.getValueAt(i,1).toString(),tbRencanaKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                DetailRencana.setText(Rencana.getText());
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
                emptTeks();
        }
    }
}

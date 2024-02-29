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
            "Tgl.Asuhan","Macam Kasus","Anamnesis","Tiba Di Ruang Rawat","Cara Masuk","Riwayat Penyakit Saat Ini","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga",
            "Riwayat Penggunaan Obat","Riwayat Pembedahan","Riwayat Dirawat Di RS","Alat Bantu Yang Dipakai","Dalam Keadaan Hamil/Sedang Menyusui","Riwayat Transfusi Darah",
            "Riwayat Alergi","Merokok","Batang/Hari","Alkohol","Gelas/Hari","Obat Tidur","Olah Raga","Kesadaran Mental","Keadaan Umum","GCS(E,V,M)","TD(mmHg)",
            "Nadi(x/menit)","RR(x/menit)","Suhu(°C)","SpO2(%)","BB(Kg)","TB(cm)","Kepala","Wajah","Leher","Kejang","Sensorik","Pulsasi","Sirkulasi","Denyut Nadi",
            "Retraksi","Pola Nafas","Suara Nafas","Batuk & Sekresi","Volume","Jenis Pernafasaan","Irama","Mulut","Lidah","Gigi","Tenggorokan","Abdomen","Peistatik Usus",
            "Anus","Sensorik","Penglihatan","Alat Bantu Penglihatan","Motorik","Pendengaran","Bicara","Otot","Kulit","Warna Kulit","Turgor","Resiko Decubitas",
            "Oedema","Pergerakan Sendi","Kekuatan Otot","Fraktur","Nyeri Sendi","Frekuensi BAB","x/","Konsistensi BAB","Warna BAB","Frekuensi BAK","x/","Warna BAK",
            "Lain-lain BAK","Mandi","Makan/Minum","Berpakaian","Eliminasi","Berpindah","Porsi Makan","Frekuensi Makan","Jenis Makanan","Lama Tidur","Gangguan Tidur",
            "a. Aktifitas Sehari-hari","b. Berjalan","c. Aktifitas","d. Alat Ambulasi","e. Ekstremitas Atas","f. Ekstremitas Bawah","g. Kemampuan Menggenggam",
            "h. Kemampuan Koordinasi","i. Kesimpulan Gangguan Fungsi","a. Kondisi Psikologis","b. Adakah Perilaku","c. Gangguan Jiwa di Masa Lalu","d. Hubungan Pasien",
            "e. Agama","f. Tinggal Dengan","g. Pekerjaan","h. Pembayaran","i. Nilai-nilai Kepercayaan","j. Bahasa Sehari-hari","k. Pendidikan Pasien","l. Pendidikan P.J.",
            "m. Edukasi Diberikan Kepada","Nyeri","Penyebab Nyeri","Kualitas Nyeri","Lokasi Nyeri","Nyeri Menyebar","Skala Nyeri","Waktu / Durasi","Nyeri Hilang Bila",
            "Diberitahukan Pada Dokter","Skala Morse 1","N.M. 1","Skala Morse 2","N.M. 2","Skala Morse 3","N.M. 3","Skala Morse 4","N.M. 4","Skala Morse 5","N.M. 5",
            "Skala Morse 6","N.M. 6","T.M.","Skala Sydney 1","N.S. 1","Skala Sydney 2","N.S. 2","Skala Sydney 3","N.S. 3","Skala Sydney 4","N.S. 4",
            "Skala Sydney 5","N.S. 5","Skala Sydney 6","N.S. 6","Skala Sydney 7","N.S. 7","Skala Sydney 8","N.S. 8","Skala Sydney 9","N.S. 9","Skala Sydney 10","N.S. 10",
            "Skala Sydney 11","N.S. 11","T.S.","1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?","Skor 1","2. Apakah asupan makan berkurang karena tidak nafsu makan ?",
            "Skor 2","Total Skor","Pasien dengan diagnosis khusus","Keterangan Diagnosa Khusus","Sudah dibaca dan diketahui oleh Dietisen","Jam Dibaca Dietisen",
            "Rencana Keperawatan Lainnya"
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
            }else if(i==12){
                column.setPreferredWidth(78);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(220);
            }else if(i==17){
                column.setPreferredWidth(170);
            }else if(i==18){
                column.setPreferredWidth(170);
            }else if(i==19){
                column.setPreferredWidth(170);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(125);
            }else if(i==23){
                column.setPreferredWidth(210);
            }else if(i==24){
                column.setPreferredWidth(130);
            }else if(i==25){
                column.setPreferredWidth(130);
            }else if(i==26){
                column.setPreferredWidth(48);
            }else if(i==27){
                column.setPreferredWidth(65);
            }else if(i==28){
                column.setPreferredWidth(44);
            }else if(i==29){
                column.setPreferredWidth(59);
            }else if(i==30){
                column.setPreferredWidth(61);
            }else if(i==31){
                column.setPreferredWidth(59);
            }else if(i==32){
                column.setPreferredWidth(120);
            }else if(i==33){
                column.setPreferredWidth(85);
            }else if(i==34){
                column.setPreferredWidth(64);
            }else if(i==35){
                column.setPreferredWidth(60);
            }else if(i==36){
                column.setPreferredWidth(74);
            }else if(i==37){
                column.setPreferredWidth(67);
            }else if(i==38){
                column.setPreferredWidth(52);
            }else if(i==39){
                column.setPreferredWidth(52);
            }else if(i==40){
                column.setPreferredWidth(44);
            }else if(i==41){
                column.setPreferredWidth(44);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(106);
            }else if(i==45){
                column.setPreferredWidth(130);
            }else if(i==46){
                column.setPreferredWidth(65);
            }else if(i==47){
                column.setPreferredWidth(50);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(72);
            }else if(i==50){
                column.setPreferredWidth(54);
            }else if(i==51){
                column.setPreferredWidth(63);
            }else if(i==52){
                column.setPreferredWidth(69);
            }else if(i==53){
                column.setPreferredWidth(97);
            }else if(i==54){
                column.setPreferredWidth(75);
            }else if(i==55){
                column.setPreferredWidth(170);
            }else if(i==56){
                column.setPreferredWidth(70);
            }else if(i==57){
                column.setPreferredWidth(140);
            }else if(i==58){
                column.setPreferredWidth(140);
            }else if(i==59){
                column.setPreferredWidth(140);
            }else if(i==60){
                column.setPreferredWidth(140);
            }else if(i==61){
                column.setPreferredWidth(140);
            }else if(i==62){
                column.setPreferredWidth(111);
            }else if(i==63){
                column.setPreferredWidth(60);
            }else if(i==64){
                column.setPreferredWidth(60);
            }else if(i==65){
                column.setPreferredWidth(140);
            }else if(i==66){
                column.setPreferredWidth(119);
            }else if(i==67){
                column.setPreferredWidth(65);
            }else if(i==68){
                column.setPreferredWidth(74);
            }else if(i==69){
                column.setPreferredWidth(140);
            }else if(i==70){
                column.setPreferredWidth(41);
            }else if(i==71){
                column.setPreferredWidth(91);
            }else if(i==72){
                column.setPreferredWidth(66);
            }else if(i==73){
                column.setPreferredWidth(44);
            }else if(i==74){
                column.setPreferredWidth(159);
            }else if(i==75){
                column.setPreferredWidth(140);
            }else if(i==76){
                column.setPreferredWidth(94);
            }else if(i==77){
                column.setPreferredWidth(79);
            }else if(i==78){
                column.setPreferredWidth(140);
            }else if(i==79){
                column.setPreferredWidth(140);
            }else if(i==80){
                column.setPreferredWidth(79);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(85);
            }else if(i==83){
                column.setPreferredWidth(80);
            }else if(i==84){
                column.setPreferredWidth(79);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(80);
            }else if(i==87){
                column.setPreferredWidth(80);
            }else if(i==88){
                column.setPreferredWidth(103);
            }else if(i==89){
                column.setPreferredWidth(103);
            }else if(i==90){
                column.setPreferredWidth(103);
            }else if(i==91){
                column.setPreferredWidth(103);
            }else if(i==92){
                column.setPreferredWidth(103);
            }else if(i==93){
                column.setPreferredWidth(68);
            }else if(i==94){
                column.setPreferredWidth(90);
            }else if(i==95){
                column.setPreferredWidth(140);
            }else if(i==96){
                column.setPreferredWidth(65);
            }else if(i==97){
                column.setPreferredWidth(108);
            }else if(i==98){
                column.setPreferredWidth(120);
            }else if(i==99){
                column.setPreferredWidth(180);
            }else if(i==100){
                column.setPreferredWidth(67);
            }else if(i==101){
                column.setPreferredWidth(104);
            }else if(i==102){
                column.setPreferredWidth(140);
            }else if(i==103){
                column.setPreferredWidth(140);
            }else if(i==104){
                column.setPreferredWidth(170);
            }else if(i==105){
                column.setPreferredWidth(170);
            }else if(i==106){
                column.setPreferredWidth(161);
            }else if(i==107){
                column.setPreferredWidth(106);
            }else if(i==108){
                column.setPreferredWidth(250);
            }else if(i==109){
                column.setPreferredWidth(157);
            }else if(i==110){
                column.setPreferredWidth(105);
            }else if(i==111){
                column.setPreferredWidth(55);
            }else if(i==112){
                column.setPreferredWidth(140);
            }else if(i==113){
                column.setPreferredWidth(90);
            }else if(i==114){
                column.setPreferredWidth(90);
            }else if(i==115){
                column.setPreferredWidth(150);
            }else if(i==116){
                column.setPreferredWidth(110);
            }else if(i==117){
                column.setPreferredWidth(110);
            }else if(i==118){
                column.setPreferredWidth(95);
            }else if(i==119){
                column.setPreferredWidth(150);
            }else if(i==120){
                column.setPreferredWidth(80);
            }else if(i==121){
                column.setPreferredWidth(140);
            }else if(i==122){
                column.setPreferredWidth(140);
            }else if(i==123){
                column.setPreferredWidth(100);
            }else if(i==124){
                column.setPreferredWidth(85);
            }else if(i==125){
                column.setPreferredWidth(65);
            }else if(i==126){
                column.setPreferredWidth(80);
            }else if(i==127){
                column.setPreferredWidth(140);
            }else if(i==128){
                column.setPreferredWidth(140);
            }else if(i==129){
                column.setPreferredWidth(77);
            }else if(i==130){
                column.setPreferredWidth(40);
            }else if(i==131){
                column.setPreferredWidth(77);
            }else if(i==132){
                column.setPreferredWidth(40);
            }else if(i==133){
                column.setPreferredWidth(177);
            }else if(i==134){
                column.setPreferredWidth(40);
            }else if(i==135){
                column.setPreferredWidth(77);
            }else if(i==136){
                column.setPreferredWidth(40);
            }else if(i==137){
                column.setPreferredWidth(162);
            }else if(i==138){
                column.setPreferredWidth(40);
            }else if(i==139){
                column.setPreferredWidth(162);
            }else if(i==140){
                column.setPreferredWidth(40);
            }else if(i==141){
                column.setPreferredWidth(40);
            }else if(i==142){
                column.setPreferredWidth(82);
            }else if(i==143){
                column.setPreferredWidth(40);
            }else if(i==144){
                column.setPreferredWidth(82);
            }else if(i==145){
                column.setPreferredWidth(40);
            }else if(i==146){
                column.setPreferredWidth(82);
            }else if(i==147){
                column.setPreferredWidth(40);
            }else if(i==148){
                column.setPreferredWidth(82);
            }else if(i==149){
                column.setPreferredWidth(40);
            }else if(i==150){
                column.setPreferredWidth(82);
            }else if(i==151){
                column.setPreferredWidth(40);
            }else if(i==152){
                column.setPreferredWidth(82);
            }else if(i==153){
                column.setPreferredWidth(40);
            }else if(i==154){
                column.setPreferredWidth(82);
            }else if(i==155){
                column.setPreferredWidth(40);
            }else if(i==156){
                column.setPreferredWidth(82);
            }else if(i==157){
                column.setPreferredWidth(40);
            }else if(i==158){
                column.setPreferredWidth(82);
            }else if(i==159){
                column.setPreferredWidth(40);
            }else if(i==160){
                column.setPreferredWidth(86);
            }else if(i==161){
                column.setPreferredWidth(44);
            }else if(i==162){
                column.setPreferredWidth(86);
            }else if(i==163){
                column.setPreferredWidth(44);
            }else if(i==164){
                column.setPreferredWidth(40);
            }else if(i==165){
                column.setPreferredWidth(380);
            }else if(i==166){
                column.setPreferredWidth(40);
            }else if(i==167){
                column.setPreferredWidth(317);
            }else if(i==168){
                column.setPreferredWidth(40);
            }else if(i==169){
                column.setPreferredWidth(58);
            }else if(i==170){
                column.setPreferredWidth(165);
            }else if(i==171){
                column.setPreferredWidth(149);
            }else if(i==172){
                column.setPreferredWidth(209);
            }else if(i==173){
                column.setPreferredWidth(107);
            }else if(i==174){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KetAnamnesis.setDocument(new batasInput((byte)30).getKata(KetAnamnesis));
        KeteranganDiagnosaKhususGizi.setDocument(new batasInput((byte)50).getKata(KeteranganDiagnosaKhususGizi));
        KeteranganDiketahuiDietisen.setDocument(new batasInput((byte)10).getKata(KeteranganDiketahuiDietisen));
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
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        Anamnesis = new widget.ComboBox();
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
        MacamKasus = new widget.ComboBox();
        jLabel41 = new widget.Label();
        KetAnamnesis = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel271 = new widget.Label();
        jLabel272 = new widget.Label();
        SkalaGizi1 = new widget.ComboBox();
        jLabel273 = new widget.Label();
        NilaiGizi1 = new widget.TextBox();
        jLabel274 = new widget.Label();
        SkalaGizi2 = new widget.ComboBox();
        jLabel275 = new widget.Label();
        NilaiGizi2 = new widget.TextBox();
        jLabel276 = new widget.Label();
        NilaiGiziTotal = new widget.TextBox();
        jLabel277 = new widget.Label();
        DiagnosaKhususGizi = new widget.ComboBox();
        KeteranganDiagnosaKhususGizi = new widget.TextBox();
        jLabel278 = new widget.Label();
        DiketahuiDietisen = new widget.ComboBox();
        jLabel279 = new widget.Label();
        KeteranganDiketahuiDietisen = new widget.TextBox();
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
        KetAnamnesis1 = new widget.TextBox();
        label18 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel200 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel101 = new widget.Label();
        G = new widget.TextBox();
        jLabel102 = new widget.Label();
        P = new widget.TextBox();
        jLabel103 = new widget.Label();
        A = new widget.TextBox();
        jLabel104 = new widget.Label();
        Hidup = new widget.TextBox();
        jLabel42 = new widget.Label();
        MacamKasus1 = new widget.ComboBox();
        KetAnamnesis2 = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel37 = new widget.Label();
        KetAnamnesis3 = new widget.TextBox();
        jLabel38 = new widget.Label();
        KetAnamnesis4 = new widget.TextBox();
        MacamKasus2 = new widget.ComboBox();
        jLabel43 = new widget.Label();
        KetAnamnesis5 = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel44 = new widget.Label();
        MacamKasus3 = new widget.ComboBox();
        jLabel33 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel105 = new widget.Label();
        G1 = new widget.TextBox();
        jLabel106 = new widget.Label();
        P1 = new widget.TextBox();
        jLabel107 = new widget.Label();
        A1 = new widget.TextBox();
        jLabel45 = new widget.Label();
        label19 = new widget.Label();
        TglAsuhan1 = new widget.Tanggal();
        label20 = new widget.Label();
        KetAnamnesis6 = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        G2 = new widget.TextBox();
        MacamKasus4 = new widget.ComboBox();
        jLabel108 = new widget.Label();
        Hidup1 = new widget.TextBox();
        label21 = new widget.Label();
        KetAnamnesis7 = new widget.TextBox();
        MacamKasus5 = new widget.ComboBox();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        MacamKasus6 = new widget.ComboBox();
        jLabel51 = new widget.Label();
        jLabel109 = new widget.Label();
        G3 = new widget.TextBox();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        G4 = new widget.TextBox();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        G5 = new widget.TextBox();
        jLabel115 = new widget.Label();
        G6 = new widget.TextBox();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        G7 = new widget.TextBox();
        jLabel118 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel53 = new widget.Label();
        MacamKasus7 = new widget.ComboBox();
        G8 = new widget.TextBox();
        MacamKasus8 = new widget.ComboBox();
        G9 = new widget.TextBox();
        label22 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel57 = new widget.Label();
        MacamKasus9 = new widget.ComboBox();
        jLabel56 = new widget.Label();
        G10 = new widget.TextBox();
        G11 = new widget.TextBox();
        G12 = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel122 = new widget.Label();
        label23 = new widget.Label();
        MacamKasus10 = new widget.ComboBox();
        G13 = new widget.TextBox();
        jLabel58 = new widget.Label();
        label24 = new widget.Label();
        MacamKasus11 = new widget.ComboBox();
        G14 = new widget.TextBox();
        jLabel121 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        MacamKasus12 = new widget.ComboBox();
        G15 = new widget.TextBox();
        label25 = new widget.Label();
        KetAnamnesis8 = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        MacamKasus13 = new widget.ComboBox();
        G16 = new widget.TextBox();
        label26 = new widget.Label();
        KetAnamnesis9 = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        MacamKasus14 = new widget.ComboBox();
        G17 = new widget.TextBox();
        label27 = new widget.Label();
        KetAnamnesis10 = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        MacamKasus15 = new widget.ComboBox();
        G18 = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        MacamKasus16 = new widget.ComboBox();
        G19 = new widget.TextBox();
        MacamKasus17 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        G20 = new widget.TextBox();
        label28 = new widget.Label();
        MacamKasus18 = new widget.ComboBox();
        G21 = new widget.TextBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        MacamKasus19 = new widget.ComboBox();
        G22 = new widget.TextBox();
        jLabel75 = new widget.Label();
        MacamKasus20 = new widget.ComboBox();
        jLabel124 = new widget.Label();
        G23 = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        MacamKasus21 = new widget.ComboBox();
        G24 = new widget.TextBox();
        jLabel78 = new widget.Label();
        MacamKasus22 = new widget.ComboBox();
        jLabel127 = new widget.Label();
        G25 = new widget.TextBox();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel130 = new widget.Label();
        KeadaanMentalUmum = new widget.ComboBox();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel201 = new widget.Label();
        MacamKasus23 = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel29 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        GCS1 = new widget.TextBox();
        jLabel83 = new widget.Label();
        jLabel131 = new widget.Label();
        G26 = new widget.TextBox();
        jLabel132 = new widget.Label();
        G27 = new widget.TextBox();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        G28 = new widget.TextBox();
        jLabel136 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel133 = new widget.Label();
        KeadaanMentalUmum1 = new widget.ComboBox();
        jLabel137 = new widget.Label();
        KeadaanMentalUmum2 = new widget.ComboBox();
        jLabel138 = new widget.Label();
        KeadaanMentalUmum3 = new widget.ComboBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        MacamKasus24 = new widget.ComboBox();
        jLabel139 = new widget.Label();
        KeadaanMentalUmum4 = new widget.ComboBox();
        GCS2 = new widget.TextBox();
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
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2723));
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
        TPasien.setBounds(314, 10, 255, 23);

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

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baby Box", "Inkubator", "Kursi Roda" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(79, 100, 105, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2024 18:38:27" }));
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

        MacamKasus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IGD", "Poliklinik", "Kamar Bersalin", "Kamar Operasi" }));
        MacamKasus.setName("MacamKasus"); // NOI18N
        MacamKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasusKeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus);
        MacamKasus.setBounds(727, 70, 127, 23);

        jLabel41.setText("Asal Pasien :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(643, 70, 80, 23);

        KetAnamnesis.setFocusTraversalPolicyProvider(true);
        KetAnamnesis.setName("KetAnamnesis"); // NOI18N
        KetAnamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis);
        KetAnamnesis.setBounds(287, 100, 205, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 2390, 880, 1);

        jLabel271.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel271.setText("VIII. SKRINING GIZI");
        jLabel271.setName("jLabel271"); // NOI18N
        FormInput.add(jLabel271);
        jLabel271.setBounds(10, 2390, 380, 23);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel272.setText("1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?");
        jLabel272.setName("jLabel272"); // NOI18N
        FormInput.add(jLabel272);
        jLabel272.setBounds(44, 2410, 380, 23);

        SkalaGizi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada penurunan berat badan", "Tidak yakin/ tidak tahu/ terasa baju lebih longgar", "Ya 1-5 kg", "Ya 6-10 kg", "Ya 11-15 kg", "Ya > 15 kg" }));
        SkalaGizi1.setName("SkalaGizi1"); // NOI18N
        SkalaGizi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaGizi1ItemStateChanged(evt);
            }
        });
        SkalaGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaGizi1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaGizi1);
        SkalaGizi1.setBounds(424, 2410, 320, 23);

        jLabel273.setText("Skor :");
        jLabel273.setName("jLabel273"); // NOI18N
        FormInput.add(jLabel273);
        jLabel273.setBounds(750, 2410, 40, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(794, 2410, 60, 23);

        jLabel274.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel274.setText("2. Apakah asupan makan berkurang karena tidak nafsu makan ?");
        jLabel274.setName("jLabel274"); // NOI18N
        FormInput.add(jLabel274);
        jLabel274.setBounds(44, 2440, 380, 23);

        SkalaGizi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SkalaGizi2.setName("SkalaGizi2"); // NOI18N
        SkalaGizi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaGizi2ItemStateChanged(evt);
            }
        });
        SkalaGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaGizi2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaGizi2);
        SkalaGizi2.setBounds(424, 2440, 320, 23);

        jLabel275.setText("Skor :");
        jLabel275.setName("jLabel275"); // NOI18N
        FormInput.add(jLabel275);
        jLabel275.setBounds(750, 2440, 40, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(794, 2440, 60, 23);

        jLabel276.setText("Total Skor :");
        jLabel276.setName("jLabel276"); // NOI18N
        FormInput.add(jLabel276);
        jLabel276.setBounds(680, 2470, 110, 23);

        NilaiGiziTotal.setEditable(false);
        NilaiGiziTotal.setFocusTraversalPolicyProvider(true);
        NilaiGiziTotal.setName("NilaiGiziTotal"); // NOI18N
        FormInput.add(NilaiGiziTotal);
        NilaiGiziTotal.setBounds(794, 2470, 60, 23);

        jLabel277.setText("Pasien dengan diagnosis khusus : ");
        jLabel277.setName("jLabel277"); // NOI18N
        FormInput.add(jLabel277);
        jLabel277.setBounds(4, 2500, 206, 23);

        DiagnosaKhususGizi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DiagnosaKhususGizi.setName("DiagnosaKhususGizi"); // NOI18N
        DiagnosaKhususGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKhususGiziKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaKhususGizi);
        DiagnosaKhususGizi.setBounds(214, 2500, 80, 23);

        KeteranganDiagnosaKhususGizi.setFocusTraversalPolicyProvider(true);
        KeteranganDiagnosaKhususGizi.setName("KeteranganDiagnosaKhususGizi"); // NOI18N
        KeteranganDiagnosaKhususGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDiagnosaKhususGiziKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDiagnosaKhususGizi);
        KeteranganDiagnosaKhususGizi.setBounds(297, 2500, 150, 23);

        jLabel278.setText("Sudah dibaca dan diketahui oleh Dietisen :");
        jLabel278.setName("jLabel278"); // NOI18N
        FormInput.add(jLabel278);
        jLabel278.setBounds(448, 2500, 220, 23);

        DiketahuiDietisen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        DiketahuiDietisen.setName("DiketahuiDietisen"); // NOI18N
        DiketahuiDietisen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiketahuiDietisenKeyPressed(evt);
            }
        });
        FormInput.add(DiketahuiDietisen);
        DiketahuiDietisen.setBounds(672, 2500, 80, 23);

        jLabel279.setText("Jam  :");
        jLabel279.setName("jLabel279"); // NOI18N
        FormInput.add(jLabel279);
        jLabel279.setBounds(750, 2500, 40, 23);

        KeteranganDiketahuiDietisen.setFocusTraversalPolicyProvider(true);
        KeteranganDiketahuiDietisen.setName("KeteranganDiketahuiDietisen"); // NOI18N
        KeteranganDiketahuiDietisen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDiketahuiDietisenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDiketahuiDietisen);
        KeteranganDiketahuiDietisen.setBounds(794, 2500, 60, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 2530, 880, 1);

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
        Scroll6.setBounds(10, 2540, 400, 143);

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
        TabRencanaKeperawatan.setBounds(433, 2540, 420, 143);

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
        BtnTambahMasalah.setBounds(363, 2690, 28, 23);

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
        BtnAllMasalah.setBounds(331, 2690, 28, 23);

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
        BtnCariMasalah.setBounds(299, 2690, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 2690, 215, 23);

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
        BtnTambahRencana.setBounds(806, 2690, 28, 23);

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
        BtnAllRencana.setBounds(774, 2690, 28, 23);

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
        BtnCariRencana.setBounds(742, 2690, 28, 23);

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label13);
        label13.setBounds(439, 2690, 60, 23);

        TCariRencana.setToolTipText("Alt+C");
        TCariRencana.setName("TCariRencana"); // NOI18N
        TCariRencana.setPreferredSize(new java.awt.Dimension(215, 23));
        TCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRencanaKeyPressed(evt);
            }
        });
        FormInput.add(TCariRencana);
        TCariRencana.setBounds(503, 2690, 235, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(16, 2690, 60, 23);

        label17.setText("Deperoleh Dari :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(193, 100, 90, 23);

        KetAnamnesis1.setFocusTraversalPolicyProvider(true);
        KetAnamnesis1.setName("KetAnamnesis1"); // NOI18N
        KetAnamnesis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis1KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis1);
        KetAnamnesis1.setBounds(644, 100, 210, 23);

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

        G.setFocusTraversalPolicyProvider(true);
        G.setName("G"); // NOI18N
        G.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GKeyPressed(evt);
            }
        });
        FormInput.add(G);
        G.setBounds(178, 220, 50, 23);

        jLabel102.setText("P");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(210, 220, 30, 23);

        P.setFocusTraversalPolicyProvider(true);
        P.setName("P"); // NOI18N
        P.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PKeyPressed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(244, 220, 50, 23);

        jLabel103.setText("A");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(277, 220, 30, 23);

        A.setFocusTraversalPolicyProvider(true);
        A.setName("A"); // NOI18N
        A.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AKeyPressed(evt);
            }
        });
        FormInput.add(A);
        A.setBounds(311, 220, 50, 23);

        jLabel104.setText("UK");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(355, 220, 25, 23);

        Hidup.setFocusTraversalPolicyProvider(true);
        Hidup.setName("Hidup"); // NOI18N
        Hidup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidupKeyPressed(evt);
            }
        });
        FormInput.add(Hidup);
        Hidup.setBounds(384, 220, 80, 23);

        jLabel42.setText("Riwayat Penyakit Ibu :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(463, 220, 130, 23);

        MacamKasus1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "DM", "Hipertensi", "Jantung", "TBC", "Hep B", "Asma", "PMS", "Lainnya" }));
        MacamKasus1.setName("MacamKasus1"); // NOI18N
        MacamKasus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus1KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus1);
        MacamKasus1.setBounds(597, 220, 100, 23);

        KetAnamnesis2.setFocusTraversalPolicyProvider(true);
        KetAnamnesis2.setName("KetAnamnesis2"); // NOI18N
        KetAnamnesis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis2KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis2);
        KetAnamnesis2.setBounds(700, 220, 154, 23);

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

        KetAnamnesis4.setFocusTraversalPolicyProvider(true);
        KetAnamnesis4.setName("KetAnamnesis4"); // NOI18N
        KetAnamnesis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis4KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis4);
        KetAnamnesis4.setBounds(274, 250, 580, 23);

        MacamKasus2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MacamKasus2.setName("MacamKasus2"); // NOI18N
        MacamKasus2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus2KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus2);
        MacamKasus2.setBounds(159, 280, 80, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Pernah Dirawat");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(74, 280, 110, 23);

        KetAnamnesis5.setFocusTraversalPolicyProvider(true);
        KetAnamnesis5.setName("KetAnamnesis5"); // NOI18N
        KetAnamnesis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis5KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis5);
        KetAnamnesis5.setBounds(242, 280, 430, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 280, 155, 23);

        jLabel44.setText("Status Gizi Ibu :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(670, 280, 100, 23);

        MacamKasus3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Buruk" }));
        MacamKasus3.setName("MacamKasus3"); // NOI18N
        MacamKasus3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus3KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus3);
        MacamKasus3.setBounds(774, 280, 80, 23);

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

        G1.setFocusTraversalPolicyProvider(true);
        G1.setName("G1"); // NOI18N
        G1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G1KeyPressed(evt);
            }
        });
        FormInput.add(G1);
        G1.setBounds(178, 330, 50, 23);

        jLabel106.setText("P");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(210, 330, 30, 23);

        P1.setFocusTraversalPolicyProvider(true);
        P1.setName("P1"); // NOI18N
        P1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                P1KeyPressed(evt);
            }
        });
        FormInput.add(P1);
        P1.setBounds(244, 330, 50, 23);

        jLabel107.setText("A");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(277, 330, 30, 23);

        A1.setFocusTraversalPolicyProvider(true);
        A1.setName("A1"); // NOI18N
        A1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A1KeyPressed(evt);
            }
        });
        FormInput.add(A1);
        A1.setBounds(311, 330, 50, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 330, 163, 23);

        label19.setText("Tgl.Lahir :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(365, 330, 60, 23);

        TglAsuhan1.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2024 18:38:29" }));
        TglAsuhan1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan1.setName("TglAsuhan1"); // NOI18N
        TglAsuhan1.setOpaque(false);
        TglAsuhan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhan1KeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan1);
        TglAsuhan1.setBounds(429, 330, 135, 23);

        label20.setText("Kondisi Saat Lahir :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(570, 330, 105, 23);

        KetAnamnesis6.setFocusTraversalPolicyProvider(true);
        KetAnamnesis6.setName("KetAnamnesis6"); // NOI18N
        KetAnamnesis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis6KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis6);
        KetAnamnesis6.setBounds(679, 330, 175, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Cara Persalinan");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(74, 360, 90, 23);

        jLabel47.setText(":");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 360, 158, 23);

        G2.setFocusTraversalPolicyProvider(true);
        G2.setName("G2"); // NOI18N
        G2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G2KeyPressed(evt);
            }
        });
        FormInput.add(G2);
        G2.setBounds(295, 360, 120, 23);

        MacamKasus4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Vacum Ekstraksi", "Forcep Ekstraksi", "Sectio Caesarea", "Lainnya" }));
        MacamKasus4.setName("MacamKasus4"); // NOI18N
        MacamKasus4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus4KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus4);
        MacamKasus4.setBounds(162, 360, 130, 23);

        jLabel108.setText("APGAR :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(417, 360, 50, 23);

        Hidup1.setFocusTraversalPolicyProvider(true);
        Hidup1.setName("Hidup1"); // NOI18N
        Hidup1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hidup1KeyPressed(evt);
            }
        });
        FormInput.add(Hidup1);
        Hidup1.setBounds(471, 360, 60, 23);

        label21.setText("Minor :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(452, 440, 50, 23);

        KetAnamnesis7.setFocusTraversalPolicyProvider(true);
        KetAnamnesis7.setName("KetAnamnesis7"); // NOI18N
        KetAnamnesis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis7KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis7);
        KetAnamnesis7.setBounds(579, 360, 123, 23);

        MacamKasus5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jernih", "Darah", "Putih Keruh", "Hijau", "Meconium" }));
        MacamKasus5.setName("MacamKasus5"); // NOI18N
        MacamKasus5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus5KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus5);
        MacamKasus5.setBounds(125, 390, 106, 23);

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

        MacamKasus6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segar", "Layu", "Simpul" }));
        MacamKasus6.setName("MacamKasus6"); // NOI18N
        MacamKasus6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus6KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus6);
        MacamKasus6.setBounds(771, 360, 83, 23);

        jLabel51.setText("Antopometri BBL :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(228, 390, 110, 23);

        jLabel109.setText("BB");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(323, 390, 30, 23);

        G3.setFocusTraversalPolicyProvider(true);
        G3.setName("G3"); // NOI18N
        G3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G3KeyPressed(evt);
            }
        });
        FormInput.add(G3);
        G3.setBounds(356, 390, 50, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("gr,");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(408, 390, 20, 23);

        jLabel111.setText("PB");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(418, 390, 20, 23);

        G4.setFocusTraversalPolicyProvider(true);
        G4.setName("G4"); // NOI18N
        G4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G4KeyPressed(evt);
            }
        });
        FormInput.add(G4);
        G4.setBounds(441, 390, 50, 23);

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

        G5.setFocusTraversalPolicyProvider(true);
        G5.setName("G5"); // NOI18N
        G5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G5KeyPressed(evt);
            }
        });
        FormInput.add(G5);
        G5.setBounds(529, 390, 50, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("cm,");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(670, 390, 22, 23);

        G6.setFocusTraversalPolicyProvider(true);
        G6.setName("G6"); // NOI18N
        G6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G6KeyPressed(evt);
            }
        });
        FormInput.add(G6);
        G6.setBounds(618, 390, 50, 23);

        jLabel116.setText("LD");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(595, 390, 20, 23);

        jLabel117.setText("LP");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(683, 390, 20, 23);

        G7.setFocusTraversalPolicyProvider(true);
        G7.setName("G7"); // NOI18N
        G7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G7KeyPressed(evt);
            }
        });
        FormInput.add(G7);
        G7.setBounds(706, 390, 50, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("cm");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(758, 390, 22, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Faktor Resiko Infeksi :");
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

        MacamKasus7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ibu Demam >= 38° C", "KPD > 24 Jam", "Ketuban Hijau", "Korioamniotis", "Fetal Distress" }));
        MacamKasus7.setName("MacamKasus7"); // NOI18N
        MacamKasus7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus7KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus7);
        MacamKasus7.setBounds(115, 440, 157, 23);

        G8.setFocusTraversalPolicyProvider(true);
        G8.setName("G8"); // NOI18N
        G8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G8KeyPressed(evt);
            }
        });
        FormInput.add(G8);
        G8.setBounds(275, 440, 175, 23);

        MacamKasus8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "KPD > 12 Jam", "Asfiksia", "BBLR", "ISK", "UK < 37 Minggu", "Gemeli", "Keputihan", "Ibu Temperatur > 37° C" }));
        MacamKasus8.setName("MacamKasus8"); // NOI18N
        MacamKasus8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus8KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus8);
        MacamKasus8.setBounds(506, 440, 170, 23);

        G9.setFocusTraversalPolicyProvider(true);
        G9.setName("G9"); // NOI18N
        G9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G9KeyPressed(evt);
            }
        });
        FormInput.add(G9);
        G9.setBounds(679, 440, 175, 23);

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

        MacamKasus9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Air Susu Ibu", "Lainnya" }));
        MacamKasus9.setName("MacamKasus9"); // NOI18N
        MacamKasus9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus9KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus9);
        MacamKasus9.setBounds(117, 490, 110, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Nutrisi");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(74, 490, 60, 23);

        G10.setFocusTraversalPolicyProvider(true);
        G10.setName("G10"); // NOI18N
        G10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G10KeyPressed(evt);
            }
        });
        FormInput.add(G10);
        G10.setBounds(230, 490, 426, 23);

        G11.setFocusTraversalPolicyProvider(true);
        G11.setName("G11"); // NOI18N
        G11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G11KeyPressed(evt);
            }
        });
        FormInput.add(G11);
        G11.setBounds(723, 490, 55, 23);

        G12.setFocusTraversalPolicyProvider(true);
        G12.setName("G12"); // NOI18N
        G12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G12KeyPressed(evt);
            }
        });
        FormInput.add(G12);
        G12.setBounds(800, 490, 45, 23);

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

        MacamKasus10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada Keluhan", "Tidak Ada Keluhan" }));
        MacamKasus10.setName("MacamKasus10"); // NOI18N
        MacamKasus10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus10KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus10);
        MacamKasus10.setBounds(142, 540, 140, 23);

        G13.setFocusTraversalPolicyProvider(true);
        G13.setName("G13"); // NOI18N
        G13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G13KeyPressed(evt);
            }
        });
        FormInput.add(G13);
        G13.setBounds(285, 540, 190, 23);

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

        MacamKasus11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada Keluhan", "Tidak Ada Keluhan" }));
        MacamKasus11.setName("MacamKasus11"); // NOI18N
        MacamKasus11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus11KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus11);
        MacamKasus11.setBounds(521, 540, 140, 23);

        G14.setFocusTraversalPolicyProvider(true);
        G14.setName("G14"); // NOI18N
        G14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G14KeyPressed(evt);
            }
        });
        FormInput.add(G14);
        G14.setBounds(664, 540, 190, 23);

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

        MacamKasus12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        MacamKasus12.setName("MacamKasus12"); // NOI18N
        MacamKasus12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus12KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus12);
        MacamKasus12.setBounds(144, 590, 130, 23);

        G15.setFocusTraversalPolicyProvider(true);
        G15.setName("G15"); // NOI18N
        G15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G15KeyPressed(evt);
            }
        });
        FormInput.add(G15);
        G15.setBounds(277, 590, 240, 23);

        label25.setText("Jika Ada, Reaksi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(510, 590, 100, 23);

        KetAnamnesis8.setFocusTraversalPolicyProvider(true);
        KetAnamnesis8.setName("KetAnamnesis8"); // NOI18N
        KetAnamnesis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis8KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis8);
        KetAnamnesis8.setBounds(614, 590, 240, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Alergi Makanan");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(74, 620, 90, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 620, 156, 23);

        MacamKasus13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        MacamKasus13.setName("MacamKasus13"); // NOI18N
        MacamKasus13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus13KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus13);
        MacamKasus13.setBounds(160, 620, 130, 23);

        G16.setFocusTraversalPolicyProvider(true);
        G16.setName("G16"); // NOI18N
        G16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G16KeyPressed(evt);
            }
        });
        FormInput.add(G16);
        G16.setBounds(293, 620, 232, 23);

        label26.setText("Jika Ada, Reaksi :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(518, 620, 100, 23);

        KetAnamnesis9.setFocusTraversalPolicyProvider(true);
        KetAnamnesis9.setName("KetAnamnesis9"); // NOI18N
        KetAnamnesis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis9KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis9);
        KetAnamnesis9.setBounds(622, 620, 232, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Alergi Lainnya");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(74, 650, 90, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 650, 150, 23);

        MacamKasus14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Tidak Diketahui", "Ada" }));
        MacamKasus14.setName("MacamKasus14"); // NOI18N
        MacamKasus14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus14KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus14);
        MacamKasus14.setBounds(154, 650, 130, 23);

        G17.setFocusTraversalPolicyProvider(true);
        G17.setName("G17"); // NOI18N
        G17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G17KeyPressed(evt);
            }
        });
        FormInput.add(G17);
        G17.setBounds(287, 650, 235, 23);

        label27.setText("Jika Ada, Reaksi :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(515, 650, 100, 23);

        KetAnamnesis10.setFocusTraversalPolicyProvider(true);
        KetAnamnesis10.setName("KetAnamnesis10"); // NOI18N
        KetAnamnesis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesis10KeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis10);
        KetAnamnesis10.setBounds(619, 650, 235, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Riwayat Penyakit Keluarga");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(44, 680, 140, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 680, 180, 23);

        MacamKasus15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Diabetes", "Kanker", "Asma", "Hipertensi", "Jantung", "Lainnya" }));
        MacamKasus15.setName("MacamKasus15"); // NOI18N
        MacamKasus15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus15KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus15);
        MacamKasus15.setBounds(184, 680, 100, 23);

        G18.setFocusTraversalPolicyProvider(true);
        G18.setName("G18"); // NOI18N
        G18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G18KeyPressed(evt);
            }
        });
        FormInput.add(G18);
        G18.setBounds(287, 680, 567, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Riwayat Imunisasi");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(44, 710, 110, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 710, 140, 23);

        MacamKasus16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MacamKasus16.setName("MacamKasus16"); // NOI18N
        MacamKasus16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus16KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus16);
        MacamKasus16.setBounds(144, 710, 100, 23);

        G19.setFocusTraversalPolicyProvider(true);
        G19.setName("G19"); // NOI18N
        G19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G19KeyPressed(evt);
            }
        });
        FormInput.add(G19);
        G19.setBounds(247, 710, 607, 23);

        MacamKasus17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MacamKasus17.setName("MacamKasus17"); // NOI18N
        MacamKasus17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus17KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus17);
        MacamKasus17.setBounds(169, 740, 100, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Riwayat Tranfusi Darah");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(44, 740, 140, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 740, 165, 23);

        G20.setFocusTraversalPolicyProvider(true);
        G20.setName("G20"); // NOI18N
        G20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G20KeyPressed(evt);
            }
        });
        FormInput.add(G20);
        G20.setBounds(272, 740, 150, 23);

        label28.setText("Jika Ada, Reaksi Tranfusi :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(417, 740, 140, 23);

        MacamKasus18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MacamKasus18.setName("MacamKasus18"); // NOI18N
        MacamKasus18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus18KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus18);
        MacamKasus18.setBounds(561, 740, 100, 23);

        G21.setFocusTraversalPolicyProvider(true);
        G21.setName("G21"); // NOI18N
        G21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G21KeyPressed(evt);
            }
        });
        FormInput.add(G21);
        G21.setBounds(664, 740, 190, 23);

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

        MacamKasus19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Vitamin", "Jamu-jamuan", "Lainnya" }));
        MacamKasus19.setName("MacamKasus19"); // NOI18N
        MacamKasus19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus19KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus19);
        MacamKasus19.setBounds(193, 790, 120, 23);

        G22.setFocusTraversalPolicyProvider(true);
        G22.setName("G22"); // NOI18N
        G22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G22KeyPressed(evt);
            }
        });
        FormInput.add(G22);
        G22.setBounds(316, 790, 228, 23);

        jLabel75.setText("Merokok :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(551, 790, 60, 23);

        MacamKasus20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MacamKasus20.setName("MacamKasus20"); // NOI18N
        MacamKasus20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus20KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus20);
        MacamKasus20.setBounds(615, 790, 80, 23);

        jLabel124.setText("Jika Ya :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(696, 790, 50, 23);

        G23.setFocusTraversalPolicyProvider(true);
        G23.setName("G23"); // NOI18N
        G23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G23KeyPressed(evt);
            }
        });
        FormInput.add(G23);
        G23.setBounds(749, 790, 45, 23);

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

        MacamKasus21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MacamKasus21.setName("MacamKasus21"); // NOI18N
        MacamKasus21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus21KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus21);
        MacamKasus21.setBounds(181, 820, 100, 23);

        G24.setFocusTraversalPolicyProvider(true);
        G24.setName("G24"); // NOI18N
        G24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G24KeyPressed(evt);
            }
        });
        FormInput.add(G24);
        G24.setBounds(284, 820, 272, 23);

        jLabel78.setText("Alkohol :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(559, 820, 60, 23);

        MacamKasus22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MacamKasus22.setName("MacamKasus22"); // NOI18N
        MacamKasus22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus22KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus22);
        MacamKasus22.setBounds(623, 820, 80, 23);

        jLabel127.setText("Jika Ya :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(704, 820, 50, 23);

        G25.setFocusTraversalPolicyProvider(true);
        G25.setName("G25"); // NOI18N
        G25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G25KeyPressed(evt);
            }
        });
        FormInput.add(G25);
        G25.setBounds(757, 820, 45, 23);

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

        KeadaanMentalUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tampak Tidak Sakit", "Sakit Ringan", "Sakit Sedang", "Sakit Berat" }));
        KeadaanMentalUmum.setName("KeadaanMentalUmum"); // NOI18N
        KeadaanMentalUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum);
        KeadaanMentalUmum.setBounds(331, 870, 145, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(481, 870, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(555, 870, 55, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(611, 870, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(645, 870, 60, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(707, 870, 40, 23);

        jLabel201.setText(":");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(0, 870, 102, 23);

        MacamKasus23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Soporcoma", "Coma" }));
        MacamKasus23.setName("MacamKasus23"); // NOI18N
        MacamKasus23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus23KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus23);
        MacamKasus23.setBounds(106, 870, 125, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Kesadaran");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 870, 70, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(744, 870, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(788, 870, 50, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(840, 870, 30, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 900, 65, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(69, 900, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(121, 900, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(157, 900, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(201, 900, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(253, 900, 50, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(300, 900, 40, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(344, 900, 50, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("%");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(396, 900, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(543, 900, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(577, 900, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(629, 900, 30, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(647, 900, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(681, 900, 50, 23);

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

        GCS1.setFocusTraversalPolicyProvider(true);
        GCS1.setName("GCS1"); // NOI18N
        GCS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCS1KeyPressed(evt);
            }
        });
        FormInput.add(GCS1);
        GCS1.setBounds(490, 900, 50, 23);

        jLabel83.setText("Down Score :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(406, 900, 80, 23);

        jLabel131.setText("LK :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(752, 900, 30, 23);

        G26.setFocusTraversalPolicyProvider(true);
        G26.setName("G26"); // NOI18N
        G26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G26KeyPressed(evt);
            }
        });
        FormInput.add(G26);
        G26.setBounds(786, 900, 50, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("cm");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(838, 900, 22, 23);

        G27.setFocusTraversalPolicyProvider(true);
        G27.setName("G27"); // NOI18N
        G27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G27KeyPressed(evt);
            }
        });
        FormInput.add(G27);
        G27.setBounds(69, 930, 50, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("cm");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(121, 930, 22, 23);

        jLabel135.setText("LP :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(137, 930, 36, 23);

        G28.setFocusTraversalPolicyProvider(true);
        G28.setName("G28"); // NOI18N
        G28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                G28KeyPressed(evt);
            }
        });
        FormInput.add(G28);
        G28.setBounds(177, 930, 50, 23);

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

        KeadaanMentalUmum1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        KeadaanMentalUmum1.setName("KeadaanMentalUmum1"); // NOI18N
        KeadaanMentalUmum1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmum1KeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum1);
        KeadaanMentalUmum1.setBounds(373, 930, 75, 23);

        jLabel137.setText("Golongan Darah Ibu :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(458, 930, 110, 23);

        KeadaanMentalUmum2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        KeadaanMentalUmum2.setName("KeadaanMentalUmum2"); // NOI18N
        KeadaanMentalUmum2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmum2KeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum2);
        KeadaanMentalUmum2.setBounds(572, 930, 75, 23);

        jLabel138.setText("Golongan Darah Ayah :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(645, 930, 130, 23);

        KeadaanMentalUmum3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -" }));
        KeadaanMentalUmum3.setName("KeadaanMentalUmum3"); // NOI18N
        KeadaanMentalUmum3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmum3KeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum3);
        KeadaanMentalUmum3.setBounds(779, 930, 75, 23);

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

        MacamKasus24.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aktif", "Tidak Aktif" }));
        MacamKasus24.setName("MacamKasus24"); // NOI18N
        MacamKasus24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasus24KeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus24);
        MacamKasus24.setBounds(136, 980, 105, 23);

        jLabel139.setText("Kepala :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(244, 980, 50, 23);

        KeadaanMentalUmum4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hydrocephalus", "Hematoma", "Lainnya " }));
        KeadaanMentalUmum4.setName("KeadaanMentalUmum4"); // NOI18N
        KeadaanMentalUmum4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmum4KeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum4);
        KeadaanMentalUmum4.setBounds(300, 980, 110, 23);

        GCS2.setFocusTraversalPolicyProvider(true);
        GCS2.setName("GCS2"); // NOI18N
        GCS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCS2KeyPressed(evt);
            }
        });
        FormInput.add(GCS2);
        GCS2.setBounds(400, 980, 140, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2024" }));
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
            if(Valid.daysOld("./cache/masalahkeperawatan.iyem")<30){
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
                    "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                    "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
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
                    "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                    "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
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
        Valid.pindah(evt,BtnDPJP,MacamKasus);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,MacamKasus,KetAnamnesis);
    }//GEN-LAST:event_AnamnesisKeyPressed

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
        Valid.pindah(evt,BtnPetugas2,MacamKasus);
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void MacamKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasusKeyPressed
        Valid.pindah(evt,BtnDPJP,Anamnesis);
    }//GEN-LAST:event_MacamKasusKeyPressed

    private void KetAnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesisKeyPressed
        //Valid.pindah(evt,Anamnesis,TibadiRuang);
    }//GEN-LAST:event_KetAnamnesisKeyPressed

    private void SkalaGizi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaGizi1ItemStateChanged
        if(SkalaGizi1.getSelectedIndex()==0){
            NilaiGizi1.setText("0");
        }else if(SkalaGizi1.getSelectedIndex()==1){
            NilaiGizi1.setText("2");
        }else if(SkalaGizi1.getSelectedIndex()==2){
            NilaiGizi1.setText("1");
        }else if(SkalaGizi1.getSelectedIndex()==3){
            NilaiGizi1.setText("2");
        }else if(SkalaGizi1.getSelectedIndex()==4){
            NilaiGizi1.setText("3");
        }else if(SkalaGizi1.getSelectedIndex()==5){
            NilaiGizi1.setText("4");
        }
        //isTotalGizi();
    }//GEN-LAST:event_SkalaGizi1ItemStateChanged

    private void SkalaGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaGizi1KeyPressed
        //Valid.pindah(evt,SkalaSydney11,SkalaGizi2);
    }//GEN-LAST:event_SkalaGizi1KeyPressed

    private void SkalaGizi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaGizi2ItemStateChanged
        if(SkalaGizi2.getSelectedIndex()==0){
            NilaiGizi2.setText("0");
        }else if(SkalaGizi2.getSelectedIndex()==1){
            NilaiGizi2.setText("1");
        }
        //isTotalGizi();
    }//GEN-LAST:event_SkalaGizi2ItemStateChanged

    private void SkalaGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaGizi2KeyPressed
        Valid.pindah(evt,SkalaGizi1,DiagnosaKhususGizi);
    }//GEN-LAST:event_SkalaGizi2KeyPressed

    private void DiagnosaKhususGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKhususGiziKeyPressed
        Valid.pindah(evt,SkalaGizi2,KeteranganDiagnosaKhususGizi);
    }//GEN-LAST:event_DiagnosaKhususGiziKeyPressed

    private void KeteranganDiagnosaKhususGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDiagnosaKhususGiziKeyPressed
        Valid.pindah(evt,DiagnosaKhususGizi,DiketahuiDietisen);
    }//GEN-LAST:event_KeteranganDiagnosaKhususGiziKeyPressed

    private void DiketahuiDietisenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiketahuiDietisenKeyPressed
        Valid.pindah(evt,KeteranganDiagnosaKhususGizi,KeteranganDiketahuiDietisen);
    }//GEN-LAST:event_DiketahuiDietisenKeyPressed

    private void KeteranganDiketahuiDietisenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDiketahuiDietisenKeyPressed
        Valid.pindah(evt,DiketahuiDietisen,TCariMasalah);
    }//GEN-LAST:event_KeteranganDiketahuiDietisenKeyPressed

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
        MasterMasalahKeperawatan form=new MasterMasalahKeperawatan(null,false);
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
            KeteranganDiketahuiDietisen.requestFocus();
        }
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganDiketahuiDietisen.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BtnTambahRencanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahRencanaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterRencanaKeperawatan form=new MasterRencanaKeperawatan(null,false);
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

    private void KetAnamnesis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis1KeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah2(evt,CaraMasuk,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void GKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GKeyPressed
        //Valid.pindah(evt,StatusKawin3,P);
    }//GEN-LAST:event_GKeyPressed

    private void PKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PKeyPressed
        //Valid.pindah(evt,G,A);
    }//GEN-LAST:event_PKeyPressed

    private void AKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AKeyPressed
        //Valid.pindah(evt,P,Hidup);
    }//GEN-LAST:event_AKeyPressed

    private void HidupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidupKeyPressed
        //Valid.pindah(evt,A,HPHT);
    }//GEN-LAST:event_HidupKeyPressed

    private void MacamKasus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus1KeyPressed

    private void KetAnamnesis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis2KeyPressed

    private void KetAnamnesis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis3KeyPressed

    private void KetAnamnesis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis4KeyPressed

    private void MacamKasus2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus2KeyPressed

    private void KetAnamnesis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis5KeyPressed

    private void MacamKasus3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus3KeyPressed

    private void G1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G1KeyPressed

    private void P1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_P1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_P1KeyPressed

    private void A1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_A1KeyPressed

    private void TglAsuhan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhan1KeyPressed

    private void KetAnamnesis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis6KeyPressed

    private void G2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G2KeyPressed

    private void MacamKasus4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus4KeyPressed

    private void Hidup1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hidup1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hidup1KeyPressed

    private void KetAnamnesis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis7KeyPressed

    private void MacamKasus5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus5KeyPressed

    private void MacamKasus6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus6KeyPressed

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

    private void MacamKasus7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus7KeyPressed

    private void G8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G8KeyPressed

    private void MacamKasus8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus8KeyPressed

    private void G9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G9KeyPressed

    private void MacamKasus9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus9KeyPressed

    private void G10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G10KeyPressed

    private void G11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G11KeyPressed

    private void G12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G12KeyPressed

    private void MacamKasus10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus10KeyPressed

    private void G13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G13KeyPressed

    private void MacamKasus11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus11KeyPressed

    private void G14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G14KeyPressed

    private void MacamKasus12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus12KeyPressed

    private void G15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G15KeyPressed

    private void KetAnamnesis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis8KeyPressed

    private void MacamKasus13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus13KeyPressed

    private void G16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G16KeyPressed

    private void KetAnamnesis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis9KeyPressed

    private void MacamKasus14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus14KeyPressed

    private void G17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G17KeyPressed

    private void KetAnamnesis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesis10KeyPressed

    private void MacamKasus15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus15KeyPressed

    private void G18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G18KeyPressed

    private void MacamKasus16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus16KeyPressed

    private void G19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G19KeyPressed

    private void MacamKasus17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus17KeyPressed

    private void G20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G20KeyPressed

    private void MacamKasus18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus18KeyPressed

    private void G21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G21KeyPressed

    private void MacamKasus19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus19KeyPressed

    private void G22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G22KeyPressed

    private void MacamKasus20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus20KeyPressed

    private void G23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G23KeyPressed

    private void MacamKasus21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus21KeyPressed

    private void G24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G24KeyPressed

    private void MacamKasus22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus22KeyPressed

    private void G25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G25KeyPressed

    private void KeadaanMentalUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmumKeyPressed
        //Valid.pindah(evt,KesadaranMental,GCS);
    }//GEN-LAST:event_KeadaanMentalUmumKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        //Valid.pindah(evt,KeadaanMentalUmum,TD);
    }//GEN-LAST:event_GCSKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        //Valid.pindah(evt,GCS,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void MacamKasus23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus23KeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        //Valid.pindah(evt,RR,SpO2);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        //Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        //Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        //Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_SpO2KeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        //Valid.pindah(evt,SpO2,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        //Valid.pindah(evt,BB,SistemSarafKepala);
    }//GEN-LAST:event_TBKeyPressed

    private void GCS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCS1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCS1KeyPressed

    private void G26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G26KeyPressed

    private void G27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G27KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G27KeyPressed

    private void G28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_G28KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_G28KeyPressed

    private void KeadaanMentalUmum1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmum1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanMentalUmum1KeyPressed

    private void KeadaanMentalUmum2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmum2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanMentalUmum2KeyPressed

    private void KeadaanMentalUmum3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmum3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanMentalUmum3KeyPressed

    private void MacamKasus24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasus24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasus24KeyPressed

    private void KeadaanMentalUmum4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmum4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanMentalUmum4KeyPressed

    private void GCS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCS2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GCS2KeyPressed

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
    private widget.TextBox A;
    private widget.TextBox A1;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BB;
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
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private widget.ComboBox DiagnosaKhususGizi;
    private widget.ComboBox DiketahuiDietisen;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox G;
    private widget.TextBox G1;
    private widget.TextBox G10;
    private widget.TextBox G11;
    private widget.TextBox G12;
    private widget.TextBox G13;
    private widget.TextBox G14;
    private widget.TextBox G15;
    private widget.TextBox G16;
    private widget.TextBox G17;
    private widget.TextBox G18;
    private widget.TextBox G19;
    private widget.TextBox G2;
    private widget.TextBox G20;
    private widget.TextBox G21;
    private widget.TextBox G22;
    private widget.TextBox G23;
    private widget.TextBox G24;
    private widget.TextBox G25;
    private widget.TextBox G26;
    private widget.TextBox G27;
    private widget.TextBox G28;
    private widget.TextBox G3;
    private widget.TextBox G4;
    private widget.TextBox G5;
    private widget.TextBox G6;
    private widget.TextBox G7;
    private widget.TextBox G8;
    private widget.TextBox G9;
    private widget.TextBox GCS;
    private widget.TextBox GCS1;
    private widget.TextBox GCS2;
    private widget.TextBox Hidup;
    private widget.TextBox Hidup1;
    private widget.TextBox Jk;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.ComboBox KeadaanMentalUmum;
    private widget.ComboBox KeadaanMentalUmum1;
    private widget.ComboBox KeadaanMentalUmum2;
    private widget.ComboBox KeadaanMentalUmum3;
    private widget.ComboBox KeadaanMentalUmum4;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetAnamnesis;
    private widget.TextBox KetAnamnesis1;
    private widget.TextBox KetAnamnesis10;
    private widget.TextBox KetAnamnesis2;
    private widget.TextBox KetAnamnesis3;
    private widget.TextBox KetAnamnesis4;
    private widget.TextBox KetAnamnesis5;
    private widget.TextBox KetAnamnesis6;
    private widget.TextBox KetAnamnesis7;
    private widget.TextBox KetAnamnesis8;
    private widget.TextBox KetAnamnesis9;
    private widget.TextBox KeteranganDiagnosaKhususGizi;
    private widget.TextBox KeteranganDiketahuiDietisen;
    private widget.Label LCount;
    private widget.ComboBox MacamKasus;
    private widget.ComboBox MacamKasus1;
    private widget.ComboBox MacamKasus10;
    private widget.ComboBox MacamKasus11;
    private widget.ComboBox MacamKasus12;
    private widget.ComboBox MacamKasus13;
    private widget.ComboBox MacamKasus14;
    private widget.ComboBox MacamKasus15;
    private widget.ComboBox MacamKasus16;
    private widget.ComboBox MacamKasus17;
    private widget.ComboBox MacamKasus18;
    private widget.ComboBox MacamKasus19;
    private widget.ComboBox MacamKasus2;
    private widget.ComboBox MacamKasus20;
    private widget.ComboBox MacamKasus21;
    private widget.ComboBox MacamKasus22;
    private widget.ComboBox MacamKasus23;
    private widget.ComboBox MacamKasus24;
    private widget.ComboBox MacamKasus3;
    private widget.ComboBox MacamKasus4;
    private widget.ComboBox MacamKasus5;
    private widget.ComboBox MacamKasus6;
    private widget.ComboBox MacamKasus7;
    private widget.ComboBox MacamKasus8;
    private widget.ComboBox MacamKasus9;
    private widget.TextBox Nadi;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGiziTotal;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private widget.TextBox P;
    private widget.TextBox P1;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ComboBox SkalaGizi1;
    private widget.ComboBox SkalaGizi2;
    private widget.TextBox SpO2;
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
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglAsuhan1;
    private widget.TextBox TglLahir;
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
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel200;
    private widget.Label jLabel201;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel271;
    private widget.Label jLabel272;
    private widget.Label jLabel273;
    private widget.Label jLabel274;
    private widget.Label jLabel275;
    private widget.Label jLabel276;
    private widget.Label jLabel277;
    private widget.Label jLabel278;
    private widget.Label jLabel279;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
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
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
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
        Anamnesis.setSelectedIndex(0);
        KetAnamnesis.setText("");
        MacamKasus.setSelectedIndex(0);
        SkalaGizi1.setSelectedIndex(0);
        NilaiGizi1.setText("0");
        SkalaGizi2.setSelectedIndex(0);
        NilaiGizi2.setText("0");
        NilaiGiziTotal.setText("0");
        DiagnosaKhususGizi.setSelectedIndex(0);
        KeteranganDiagnosaKhususGizi.setText("");
        DiketahuiDietisen.setSelectedIndex(0);
        KeteranganDiketahuiDietisen.setText("");
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabModeRencana);
        TabRawat.setSelectedIndex(0);
        MacamKasus.requestFocus();
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
            MacamKasus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().contains("Autoanamnesis")){
                Anamnesis.setSelectedItem("Autoanamnesis");
            }else{
                Anamnesis.setSelectedItem("Alloanamnesis");
            }
            KetAnamnesis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().replaceAll(Anamnesis.getSelectedItem().toString()+", ",""));
            //isTotalResikoSydney();
            SkalaGizi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),165).toString());
            NilaiGizi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),166).toString());
            SkalaGizi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),167).toString());
            NilaiGizi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),168).toString());
            NilaiGiziTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),169).toString());
            DiagnosaKhususGizi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),170).toString());
            KeteranganDiagnosaKhususGizi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),171).toString());
            DiketahuiDietisen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),172).toString());
            KeteranganDiketahuiDietisen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),173).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
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
                        "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap()); 
        BtnTambahMasalah.setEnabled(akses.getmaster_masalah_keperawatan()); 
        BtnTambahRencana.setEnabled(akses.getmaster_rencana_keperawatan()); 
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
                        "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
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
                        "select master_rencana_keperawatan.kode_rencana,master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                        "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
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
            file=new File("./cache/masalahkeperawatan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan order by master_masalah_keperawatan.kode_masalah");
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
            
            myObj = new FileReader("./cache/masalahkeperawatan.iyem");
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
            file=new File("./cache/rencanakeperawatan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            iyem="";
            ps=koneksi.prepareStatement("select * from master_rencana_keperawatan order by master_rencana_keperawatan.kode_rencana");
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

            myObj = new FileReader("./cache/rencanakeperawatan.iyem");
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

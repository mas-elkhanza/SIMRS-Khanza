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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalKeperawatanRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String pilihan="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatPersalinan.setSize(650,192);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP Pengkaji 1","Nama Pengkaji 1","NIP Pengkaji 2","Nama Pengkaji 2","Kode DPJP","Nama DPJP",
            "Tgl.Asuhan","Anamnesis","Tiba di Ruang Rawat","Cara Masuk","Keluhan Utama","Penyakit Selama Kehamilan","Riwayat Penyakit Keluarga","Riwayat Pembedahan",
            "Riwayat Alergi","Komplikasi Kehamilan Sebelumnya","Keterangan Komplikasi Sebelumnya","Umur Menarche","Lamanya Mens","Banyaknya Pembalut","Siklus Haid","Ket.Siklus Haid","Dirasakan Saat Menstruasi",
            "Status Menikah","Jml.Nikah","Usia Perkawinan 1","Status Perkawinan 1","Usia Perkawinan 2","Status Perkawinan 2","Usia Perkawinan 3","Status Perkawinan 3",
            "G","P","A","Hidup","HPHT","Usia Hamil","Tg.Perkiraan","Riwayat Imunisasi","ANC","ANC Ke","Ket. ANC","Keluhan Hamil Muda","Keluhan Hamil Tua","Riwayat Keluarga Berencana",
            "Lamanya KB","Komplikasi KB","Ket Komplikasi KB","Berhenti KB","Alasan Berhenti KB","Riwayat Genekologi","Obat/Vitamin","Keterangan Obat/Vitamin","Merokok","Rokok/Hari",
            "Alkohol","Alkohol/Hari","Obat Tidur/Narkoba","Kesadaran Mental","Keadaan Umum","GCS(E,V,M)","TD","Nadi","RR","Suhu","SpO2","BB","TB","LILA","TFU","TBJ","GD","Letak",
            "Presentasi","Penurunan","Kontraksi/HIS","Kekuatan","Lamanya","DJJ","Keterangan DJJ","Portio","Serviks","Ketuban","Hodge","Panggul","Inspekulo","Keterangan Inspekulo",
            "Lakmus","Keterangan Lakmus","CTG","Keterangan CTG","Kepala","Muka","Mata","Hidung","Telinga","Mulut","Leher","Dada","Perut","Genitalia","Ekstremitas",
            "a. Aktivitas Sehari-hari","b. Berjalan","Ket. Berjalan","c. Aktifitas","d. Alat Ambulasi","e. Ekstrimitas Atas","Ket. Ekstrimitas Atas","f. Ekstrimitas Bawah",
            "Ket. Ekstrimitas Bawah","g. Kemampuan Menggenggam","Ket. Kemampuan Menggenggam","h. Kemampuan Koordinasi","Ket. Kemampuan Koordinasi","i. Kesimpulan Gangguan Fungsi",
            "a. Kondisi Psikologis","b. Adakah Perilaku","Keterangan Perilaku","c. Gangguan Jiwa di Masa Lalu","d. Hubungan dengan Anggota Keluarga","e. Agama","f. Tinggal Dengan",
            "Keterangan Tinggal","g. Pekerjaan","h. Pembayaran","i. Nilai-nilai Kepercayaan","Ket. Nilai-nilai Kepercayaan","j. Bahasa Sehari-hari","k. Pendidikan Pasien",
            "l. Pendidikan P.J.","m. Edukasi Diberikan Kepada","Keterangan Edukasi","Penilaian Nyeri","Penyebab","Keterangan Penyebab","Kualitas","Keterangan Kualitas",
            "Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri hilang bila","Keterangan Nyeri Hilang","Diberitahukan Dokter","Pada Jam","1. Riwayat Jatuh","Nilai 1",
            "2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)","Nilai 2","3. Alat Bantu","Nilai 3","4. Terpasang Infuse","Nilai 4","5. Gaya Berjalan","Nilai 5","6. Status Mental",
            "Nilai 6","Total Nilai","1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?","Skor 1","2. Apakah asupan makan berkurang karena tidak nafsu makan ?",
            "Skor 2","Total Skor","Pasien dengan diagnosis khusus","Keterangan Diagnosa Khusus","Sudah dibaca dan diketahui oleh Dietisen","Jam Dibaca Dietisen","Asesmen/Penilaian Kebidanan",
            "Rencana Kebidanan"
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
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(110);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(250);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(175);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(84);
            }else if(i==23){
                column.setPreferredWidth(78);
            }else if(i==24){
                column.setPreferredWidth(108);
            }else if(i==25){
                column.setPreferredWidth(60);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(136);
            }else if(i==28){
                column.setPreferredWidth(81);
            }else if(i==29){
                column.setPreferredWidth(54);
            }else if(i==30){
                column.setPreferredWidth(96);
            }else if(i==31){
                column.setPreferredWidth(106);
            }else if(i==32){
                column.setPreferredWidth(96);
            }else if(i==33){
                column.setPreferredWidth(106);
            }else if(i==34){
                column.setPreferredWidth(96);
            }else if(i==35){
                column.setPreferredWidth(106);
            }else if(i==36){
                column.setPreferredWidth(22);
            }else if(i==37){
                column.setPreferredWidth(22);
            }else if(i==38){
                column.setPreferredWidth(22);
            }else if(i==39){
                column.setPreferredWidth(37);
            }else if(i==40){
                column.setPreferredWidth(65);
            }else if(i==41){
                column.setPreferredWidth(59);
            }else if(i==42){
                column.setPreferredWidth(70);
            }else if(i==43){
                column.setPreferredWidth(97);
            }else if(i==44){
                column.setPreferredWidth(35);
            }else if(i==45){
                column.setPreferredWidth(45);
            }else if(i==46){
                column.setPreferredWidth(70);
            }else if(i==47){
                column.setPreferredWidth(105);
            }else if(i==48){
                column.setPreferredWidth(98);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==50){
                column.setPreferredWidth(70);
            }else if(i==51){
                column.setPreferredWidth(90);
            }else if(i==52){
                column.setPreferredWidth(120);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(120);
            }else if(i==55){
                column.setPreferredWidth(104);
            }else if(i==56){
                column.setPreferredWidth(100);
            }else if(i==57){
                column.setPreferredWidth(150);
            }else if(i==58){
                column.setPreferredWidth(50);
            }else if(i==59){
                column.setPreferredWidth(60);
            }else if(i==60){
                column.setPreferredWidth(45);
            }else if(i==61){
                column.setPreferredWidth(68);
            }else if(i==62){
                column.setPreferredWidth(103);
            }else if(i==63){
                column.setPreferredWidth(105);
            }else if(i==64){
                column.setPreferredWidth(84);
            }else if(i==65){
                column.setPreferredWidth(63);
            }else if(i==66){
                column.setPreferredWidth(50);
            }else if(i==67){
                column.setPreferredWidth(35);
            }else if(i==68){
                column.setPreferredWidth(35);
            }else if(i==69){
                column.setPreferredWidth(35);
            }else if(i==70){
                column.setPreferredWidth(35);
            }else if(i==71){
                column.setPreferredWidth(35);
            }else if(i==72){
                column.setPreferredWidth(35);
            }else if(i==73){
                column.setPreferredWidth(35);
            }else if(i==74){
                column.setPreferredWidth(55);
            }else if(i==75){
                column.setPreferredWidth(55);
            }else if(i==76){
                column.setPreferredWidth(27);
            }else if(i==77){
                column.setPreferredWidth(55);
            }else if(i==78){
                column.setPreferredWidth(65);
            }else if(i==79){
                column.setPreferredWidth(65);
            }else if(i==80){
                column.setPreferredWidth(75);
            }else if(i==81){
                column.setPreferredWidth(65);
            }else if(i==82){
                column.setPreferredWidth(50);
            }else if(i==83){
                column.setPreferredWidth(40);
            }else if(i==84){
                column.setPreferredWidth(85);
            }else if(i==85){
                column.setPreferredWidth(50);
            }else if(i==86){
                column.setPreferredWidth(45);
            }else if(i==87){
                column.setPreferredWidth(53);
            }else if(i==88){
                column.setPreferredWidth(53);
            }else if(i==89){
                column.setPreferredWidth(145);
            }else if(i==90){
                column.setPreferredWidth(60);
            }else if(i==91){
                column.setPreferredWidth(115);
            }else if(i==92){
                column.setPreferredWidth(60);
            }else if(i==93){
                column.setPreferredWidth(115);
            }else if(i==94){
                column.setPreferredWidth(60);
            }else if(i==95){
                column.setPreferredWidth(115);
            }else if(i==96){
                column.setPreferredWidth(80);
            }else if(i==97){
                column.setPreferredWidth(50);
            }else if(i==98){
                column.setPreferredWidth(128);
            }else if(i==99){
                column.setPreferredWidth(50);
            }else if(i==100){
                column.setPreferredWidth(50);
            }else if(i==101){
                column.setPreferredWidth(50);
            }else if(i==102){
                column.setPreferredWidth(140);
            }else if(i==103){
                column.setPreferredWidth(87);
            }else if(i==104){
                column.setPreferredWidth(100);
            }else if(i==105){
                column.setPreferredWidth(60);
            }else if(i==106){
                column.setPreferredWidth(100);
            }else if(i==107){
                column.setPreferredWidth(120);
            }else if(i==108){
                column.setPreferredWidth(140);
            }else if(i==109){
                column.setPreferredWidth(140);
            }else if(i==110){
                column.setPreferredWidth(68);
            }else if(i==111){
                column.setPreferredWidth(101);
            }else if(i==112){
                column.setPreferredWidth(100);
            }else if(i==113){
                column.setPreferredWidth(110);
            }else if(i==114){
                column.setPreferredWidth(107);
            }else if(i==115){
                column.setPreferredWidth(118);
            }else if(i==116){
                column.setPreferredWidth(154);
            }else if(i==117){
                column.setPreferredWidth(164);
            }else if(i==118){
                column.setPreferredWidth(133);
            }else if(i==119){
                column.setPreferredWidth(143);
            }else if(i==120){
                column.setPreferredWidth(160);
            }else if(i==121){
                column.setPreferredWidth(106);
            }else if(i==122){
                column.setPreferredWidth(98);
            }else if(i==123){
                column.setPreferredWidth(107);
            }else if(i==124){
                column.setPreferredWidth(156);
            }else if(i==125){
                column.setPreferredWidth(198);
            }else if(i==126){
                column.setPreferredWidth(70);
            }else if(i==127){
                column.setPreferredWidth(95);
            }else if(i==128){
                column.setPreferredWidth(103);
            }else if(i==129){
                column.setPreferredWidth(110);
            }else if(i==130){
                column.setPreferredWidth(130);
            }else if(i==131){
                column.setPreferredWidth(130);
            }else if(i==132){
                column.setPreferredWidth(142);
            }else if(i==133){
                column.setPreferredWidth(110);
            }else if(i==134){
                column.setPreferredWidth(108);
            }else if(i==135){
                column.setPreferredWidth(100);
            }else if(i==136){
                column.setPreferredWidth(148);
            }else if(i==137){
                column.setPreferredWidth(148);
            }else if(i==138){
                column.setPreferredWidth(83);
            }else if(i==139){
                column.setPreferredWidth(83);
            }else if(i==140){
                column.setPreferredWidth(115);
            }else if(i==141){
                column.setPreferredWidth(87);
            }else if(i==142){
                column.setPreferredWidth(107);
            }else if(i==143){
                column.setPreferredWidth(100);
            }else if(i==144){
                column.setPreferredWidth(55);
            }else if(i==145){
                column.setPreferredWidth(63);
            }else if(i==146){
                column.setPreferredWidth(60);
            }else if(i==147){
                column.setPreferredWidth(87);
            }else if(i==148){
                column.setPreferredWidth(126);
            }else if(i==149){
                column.setPreferredWidth(110);
            }else if(i==150){
                column.setPreferredWidth(60);
            }else if(i==151){
                column.setPreferredWidth(90);
            }else if(i==152){
                column.setPreferredWidth(40);
            }else if(i==153){
                column.setPreferredWidth(225);
            }else if(i==154){
                column.setPreferredWidth(40);
            }else if(i==155){
                column.setPreferredWidth(218);
            }else if(i==156){
                column.setPreferredWidth(40);
            }else if(i==157){
                column.setPreferredWidth(105);
            }else if(i==158){
                column.setPreferredWidth(40);
            }else if(i==159){
                column.setPreferredWidth(160);
            }else if(i==160){
                column.setPreferredWidth(40);
            }else if(i==161){
                column.setPreferredWidth(225);
            }else if(i==162){
                column.setPreferredWidth(40);
            }else if(i==163){
                column.setPreferredWidth(58);
            }else if(i==164){
                column.setPreferredWidth(380);
            }else if(i==165){
                column.setPreferredWidth(40);
            }else if(i==166){
                column.setPreferredWidth(317);
            }else if(i==167){
                column.setPreferredWidth(40);
            }else if(i==168){
                column.setPreferredWidth(58);
            }else if(i==169){
                column.setPreferredWidth(165);
            }else if(i==170){
                column.setPreferredWidth(149);
            }else if(i==171){
                column.setPreferredWidth(209);
            }else if(i==172){
                column.setPreferredWidth(107);
            }else if(i==173){
                column.setPreferredWidth(200);
            }else if(i==174){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KetAnamnesis.setDocument(new batasInput((byte)30).getKata(KetAnamnesis));
        
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
        TibadiRuang = new widget.ComboBox();
        jLabel37 = new widget.Label();
        CaraMasuk = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel39 = new widget.Label();
        Alergi = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jSeparator2 = new javax.swing.JSeparator();
        MacamKasus = new widget.ComboBox();
        jLabel41 = new widget.Label();
        KetAnamnesis = new widget.TextBox();
        jLabel40 = new widget.Label();
        RDirawatRS = new widget.TextBox();
        RPembedahan = new widget.TextBox();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        Anamnesis1 = new widget.ComboBox();
        Anamnesis2 = new widget.ComboBox();
        jLabel44 = new widget.Label();
        Alergi1 = new widget.TextBox();
        jLabel45 = new widget.Label();
        RPembedahan1 = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel124 = new widget.Label();
        KebiasaanMerokok = new widget.ComboBox();
        KebiasaanJumlahRokok = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        KebiasaanAlkohol = new widget.ComboBox();
        KebiasaanJumlahAlkohol = new widget.TextBox();
        jLabel127 = new widget.Label();
        KebiasaanNarkoba = new widget.ComboBox();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        OlahRaga = new widget.ComboBox();
        jLabel95 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel47 = new widget.Label();
        KesadaranMental = new widget.TextBox();
        jLabel130 = new widget.Label();
        KeadaanMentalUmum = new widget.ComboBox();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel24 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel131 = new widget.Label();
        KebiasaanMerokok1 = new widget.ComboBox();
        KebiasaanJumlahRokok1 = new widget.TextBox();
        jLabel132 = new widget.Label();
        KebiasaanMerokok2 = new widget.ComboBox();
        KebiasaanJumlahRokok2 = new widget.TextBox();
        jLabel133 = new widget.Label();
        KebiasaanMerokok3 = new widget.ComboBox();
        jLabel134 = new widget.Label();
        KebiasaanMerokok4 = new widget.ComboBox();
        jLabel135 = new widget.Label();
        KebiasaanMerokok5 = new widget.ComboBox();
        KebiasaanJumlahRokok3 = new widget.TextBox();
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
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2022" }));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Inap Umum ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2203));
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
        BtnPetugas.setBounds(388, 40, 28, 23);

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
        label11.setBounds(655, 70, 60, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Anamnesis :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 100, 70, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(74, 100, 130, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2022 19:40:29" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(719, 70, 135, 23);

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
        label16.setBounds(0, 70, 70, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setName("KdDPJP"); // NOI18N
        KdDPJP.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDPJPKeyPressed(evt);
            }
        });
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(74, 70, 110, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP"); // NOI18N
        NmDPJP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(186, 70, 230, 23);

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
        BtnDPJP.setBounds(418, 70, 28, 23);

        TibadiRuang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan Tanpa Bantuan", "Kursi Roda", "Brankar" }));
        TibadiRuang.setName("TibadiRuang"); // NOI18N
        TibadiRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TibadiRuangKeyPressed(evt);
            }
        });
        FormInput.add(TibadiRuang);
        TibadiRuang.setBounds(516, 100, 155, 23);

        jLabel37.setText("Tiba di Ruang Rawat :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(392, 100, 120, 23);

        CaraMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poli", "IGD", "Lain-lain" }));
        CaraMasuk.setSelectedIndex(2);
        CaraMasuk.setName("CaraMasuk"); // NOI18N
        CaraMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraMasukKeyPressed(evt);
            }
        });
        FormInput.add(CaraMasuk);
        CaraMasuk.setBounds(758, 100, 95, 23);

        jLabel38.setText("Cara Masuk :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(684, 100, 70, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("I. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 130, 180, 23);

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(450, 200, 150, 23);

        jLabel39.setText("Riwayat Alergi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(480, 310, 90, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(574, 310, 280, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(RPS);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(179, 150, 280, 43);

        jLabel30.setText("Riwayat Penyakit Saat Ini :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 150, 175, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPK);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(184, 200, 265, 43);

        jLabel31.setText("Riwayat Penyakit Keluarga :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 200, 180, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPD);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(604, 150, 250, 43);

        jLabel32.setText("Riwayat Penyakit Dahulu :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(450, 150, 150, 23);

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
        scrollPane4.setBounds(604, 200, 250, 43);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 130, 880, 1);

        MacamKasus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Trauma", "Non Trauma" }));
        MacamKasus.setSelectedIndex(1);
        MacamKasus.setName("MacamKasus"); // NOI18N
        MacamKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MacamKasusKeyPressed(evt);
            }
        });
        FormInput.add(MacamKasus);
        MacamKasus.setBounds(538, 70, 112, 23);

        jLabel41.setText("Macam Kasus :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(454, 70, 80, 23);

        KetAnamnesis.setFocusTraversalPolicyProvider(true);
        KetAnamnesis.setName("KetAnamnesis"); // NOI18N
        KetAnamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis);
        KetAnamnesis.setBounds(208, 100, 175, 23);

        jLabel40.setText("Riwayat Dirawat Di RS :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(440, 250, 130, 23);

        RDirawatRS.setFocusTraversalPolicyProvider(true);
        RDirawatRS.setName("RDirawatRS"); // NOI18N
        RDirawatRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RDirawatRSKeyPressed(evt);
            }
        });
        FormInput.add(RDirawatRS);
        RDirawatRS.setBounds(574, 250, 280, 23);

        RPembedahan.setFocusTraversalPolicyProvider(true);
        RPembedahan.setName("RPembedahan"); // NOI18N
        RPembedahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPembedahanKeyPressed(evt);
            }
        });
        FormInput.add(RPembedahan);
        RPembedahan.setBounds(160, 250, 280, 23);

        jLabel42.setText("Riwayat Pembedahan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 250, 156, 23);

        jLabel43.setText("Alat Bantu Yang Dipakai :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 280, 168, 23);

        Anamnesis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kacamata", "Prothesa", "Alat Bantu Dengar", "Lain-lain" }));
        Anamnesis1.setSelectedIndex(3);
        Anamnesis1.setName("Anamnesis1"); // NOI18N
        Anamnesis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis1KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis1);
        Anamnesis1.setBounds(172, 280, 140, 23);

        Anamnesis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis2.setName("Anamnesis2"); // NOI18N
        Anamnesis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis2KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis2);
        Anamnesis2.setBounds(580, 280, 80, 23);

        jLabel44.setText("Apakah Dalam Keadaan Hamil / Sedang Menyusui :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(316, 280, 260, 23);

        Alergi1.setFocusTraversalPolicyProvider(true);
        Alergi1.setName("Alergi1"); // NOI18N
        Alergi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi1KeyPressed(evt);
            }
        });
        FormInput.add(Alergi1);
        Alergi1.setBounds(664, 280, 190, 23);

        jLabel45.setText("Riwayat Transfusi Darah :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 310, 170, 23);

        RPembedahan1.setFocusTraversalPolicyProvider(true);
        RPembedahan1.setName("RPembedahan1"); // NOI18N
        RPembedahan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPembedahan1KeyPressed(evt);
            }
        });
        FormInput.add(RPembedahan1);
        RPembedahan1.setBounds(174, 310, 300, 23);

        jLabel46.setText("Kebiasaan :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 340, 101, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("batang/hari");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(244, 360, 70, 23);

        KebiasaanMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanMerokok.setName("KebiasaanMerokok"); // NOI18N
        KebiasaanMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokokKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok);
        KebiasaanMerokok.setBounds(120, 360, 80, 23);

        KebiasaanJumlahRokok.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahRokok.setName("KebiasaanJumlahRokok"); // NOI18N
        KebiasaanJumlahRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahRokokKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahRokok);
        KebiasaanJumlahRokok.setBounds(202, 360, 40, 23);

        jLabel125.setText("Merokok :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(0, 360, 116, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("gelas/hari");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(490, 360, 60, 23);

        KebiasaanAlkohol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanAlkohol.setName("KebiasaanAlkohol"); // NOI18N
        KebiasaanAlkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanAlkoholKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanAlkohol);
        KebiasaanAlkohol.setBounds(366, 360, 80, 23);

        KebiasaanJumlahAlkohol.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahAlkohol.setName("KebiasaanJumlahAlkohol"); // NOI18N
        KebiasaanJumlahAlkohol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahAlkoholKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahAlkohol);
        KebiasaanJumlahAlkohol.setBounds(448, 360, 40, 23);

        jLabel127.setText("Alkohol :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(312, 360, 50, 23);

        KebiasaanNarkoba.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KebiasaanNarkoba.setName("KebiasaanNarkoba"); // NOI18N
        KebiasaanNarkoba.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanNarkobaKeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanNarkoba);
        KebiasaanNarkoba.setBounds(620, 360, 80, 23);

        jLabel128.setText("Obat Tidur :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(546, 360, 70, 23);

        jLabel129.setText("Olah Raga :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(700, 360, 70, 23);

        OlahRaga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        OlahRaga.setName("OlahRaga"); // NOI18N
        OlahRaga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OlahRagaKeyPressed(evt);
            }
        });
        FormInput.add(OlahRaga);
        OlahRaga.setBounds(774, 360, 80, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PEMERIKSAAN FISIK");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 390, 180, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 390, 880, 1);

        jLabel47.setText("Kesadaran Mental :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 410, 138, 23);

        KesadaranMental.setFocusTraversalPolicyProvider(true);
        KesadaranMental.setName("KesadaranMental"); // NOI18N
        KesadaranMental.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranMentalKeyPressed(evt);
            }
        });
        FormInput.add(KesadaranMental);
        KesadaranMental.setBounds(142, 410, 175, 23);

        jLabel130.setText("Keadaan Umum :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(345, 410, 90, 23);

        KeadaanMentalUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        KeadaanMentalUmum.setName("KeadaanMentalUmum"); // NOI18N
        KeadaanMentalUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanMentalUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanMentalUmum);
        KeadaanMentalUmum.setBounds(439, 410, 90, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(552, 410, 70, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(626, 410, 75, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(721, 410, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(755, 410, 65, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(823, 410, 40, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 440, 73, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(77, 440, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(130, 440, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(182, 440, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(236, 440, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(289, 440, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(361, 440, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(405, 440, 50, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(458, 440, 30, 23);

        jLabel24.setText("SpO2 :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(500, 440, 40, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(544, 440, 50, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("%");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(597, 440, 30, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(633, 440, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(667, 440, 50, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(720, 440, 30, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(753, 440, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(787, 440, 50, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("cm");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(840, 440, 30, 23);

        jLabel27.setText("Sistem Susunan Saraf Pusat :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 470, 187, 23);

        jLabel131.setText("Kepala :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(0, 490, 109, 23);

        KebiasaanMerokok1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hydrocephalus", "Hematoma", "Lain-lain" }));
        KebiasaanMerokok1.setName("KebiasaanMerokok1"); // NOI18N
        KebiasaanMerokok1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokok1KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok1);
        KebiasaanMerokok1.setBounds(113, 490, 127, 23);

        KebiasaanJumlahRokok1.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahRokok1.setName("KebiasaanJumlahRokok1"); // NOI18N
        KebiasaanJumlahRokok1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahRokok1KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahRokok1);
        KebiasaanJumlahRokok1.setBounds(244, 490, 184, 23);

        jLabel132.setText("Wajah :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(432, 490, 80, 23);

        KebiasaanMerokok2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Kelainan Kongenital" }));
        KebiasaanMerokok2.setName("KebiasaanMerokok2"); // NOI18N
        KebiasaanMerokok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokok2KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok2);
        KebiasaanMerokok2.setBounds(516, 490, 150, 23);

        KebiasaanJumlahRokok2.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahRokok2.setName("KebiasaanJumlahRokok2"); // NOI18N
        KebiasaanJumlahRokok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahRokok2KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahRokok2);
        KebiasaanJumlahRokok2.setBounds(670, 490, 184, 23);

        jLabel133.setText("Leher :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(0, 520, 109, 23);

        KebiasaanMerokok3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kaku Kuduk", "Pembesaran Thyroid", "Pembesaran KGB" }));
        KebiasaanMerokok3.setName("KebiasaanMerokok3"); // NOI18N
        KebiasaanMerokok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokok3KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok3);
        KebiasaanMerokok3.setBounds(113, 520, 150, 23);

        jLabel134.setText("Sensorik :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(660, 520, 80, 23);

        KebiasaanMerokok4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Sakit Nyeri", "Rasa Kebas" }));
        KebiasaanMerokok4.setName("KebiasaanMerokok4"); // NOI18N
        KebiasaanMerokok4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokok4KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok4);
        KebiasaanMerokok4.setBounds(744, 520, 110, 23);

        jLabel135.setText("Kejang :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(292, 520, 60, 23);

        KebiasaanMerokok5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Kuat", "Ada" }));
        KebiasaanMerokok5.setName("KebiasaanMerokok5"); // NOI18N
        KebiasaanMerokok5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanMerokok5KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanMerokok5);
        KebiasaanMerokok5.setBounds(356, 520, 100, 23);

        KebiasaanJumlahRokok3.setFocusTraversalPolicyProvider(true);
        KebiasaanJumlahRokok3.setName("KebiasaanJumlahRokok3"); // NOI18N
        KebiasaanJumlahRokok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebiasaanJumlahRokok3KeyPressed(evt);
            }
        });
        FormInput.add(KebiasaanJumlahRokok3);
        KebiasaanJumlahRokok3.setBounds(460, 520, 184, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2022" }));
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Keluarga");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Penyakit Selama Kehamilan");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pembedahan");
        }
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Pengkaji 1");
        }else if(KdPetugas2.getText().trim().equals("")||NmPetugas2.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas2,"Pegkaji 2");
        }else if(KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")){
            Valid.textKosong(BtnDPJP,"DPJP");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Keluarga");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Penyakit Selama Kehamilan");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pembedahan");
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
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 
                
                ps=koneksi.prepareStatement(
                    "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,"+
                    "penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.keluhan,"+
                    "penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.psk,penilaian_awal_keperawatan_ranap.rp,penilaian_awal_keperawatan_ranap.alergi,"+
                    "penilaian_awal_keperawatan_ranap.komplikasi_sebelumnya,penilaian_awal_keperawatan_ranap.keterangan_komplikasi_sebelumnya,penilaian_awal_keperawatan_ranap.riwayat_mens_umur,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_mens_lamanya,penilaian_awal_keperawatan_ranap.riwayat_mens_banyaknya,penilaian_awal_keperawatan_ranap.riwayat_mens_siklus,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_mens_ket_siklus,penilaian_awal_keperawatan_ranap.riwayat_mens_dirasakan,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_status,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_status,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia1,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia1,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia2,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia2,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia3,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia3,penilaian_awal_keperawatan_ranap.riwayat_persalinan_g,penilaian_awal_keperawatan_ranap.riwayat_persalinan_p,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_persalinan_a,penilaian_awal_keperawatan_ranap.riwayat_persalinan_hidup,penilaian_awal_keperawatan_ranap.riwayat_hamil_hpht,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_hamil_usiahamil,penilaian_awal_keperawatan_ranap.riwayat_hamil_tp,penilaian_awal_keperawatan_ranap.riwayat_hamil_imunisasi,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_hamil_anc,penilaian_awal_keperawatan_ranap.riwayat_hamil_ancke,penilaian_awal_keperawatan_ranap.riwayat_hamil_ket_ancke,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_hamil_keluhan_hamil_muda,penilaian_awal_keperawatan_ranap.riwayat_hamil_keluhan_hamil_tua,penilaian_awal_keperawatan_ranap.riwayat_kb,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kb_lamanya,penilaian_awal_keperawatan_ranap.riwayat_kb_komplikasi,penilaian_awal_keperawatan_ranap.riwayat_kb_ket_komplikasi,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kb_kapaberhenti,penilaian_awal_keperawatan_ranap.riwayat_kb_alasanberhenti,penilaian_awal_keperawatan_ranap.riwayat_genekologi,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_obat,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_obat,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_merokok,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_merokok,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_alkohol,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_alkohol,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_narkoba,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_keadaan_umum,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_gcs,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_nadi,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_suhu,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_spo2,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lila,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tfu,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tbj,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_letak,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_presentasi,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_penurunan,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_his,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_kekuatan,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lamanya,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_djj,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_djj,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_portio,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_pembukaan,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ketuban,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_hodge,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_panggul,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_inspekulo,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_inspekulo,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lakmus,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_lakmus,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ctg,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_ctg,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_kepala,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_muka,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_mata,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_hidung,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_telinga,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_mulut,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_dada,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_perut,"+
                    "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_genitalia,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_ekstrimitas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_aktifitas,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktivitas,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_ekstrimitas_atas,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_ekstrimitas_bawah,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_kemampuan_menggenggam,"+
                    "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_gangguan_fungsi,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisipsiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_adakah_prilaku,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_adakah_prilaku,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_pasien,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_dengan,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_tinggal_dengan,penilaian_awal_keperawatan_ranap.riwayat_psiko_budaya,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_budaya,"+
                    "penilaian_awal_keperawatan_ranap.riwayat_psiko_pend_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_pada,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_edukasi_pada,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai1,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala3,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai4,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala6,"+
                    "penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuh_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,"+
                    "penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,penilaian_awal_keperawatan_ranap.nilai_gizi2,bahasa_pasien.nama_bahasa,"+
                    "penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                    "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.masalah,"+
                    "penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter, "+
                    "pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                    "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                    "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                    "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                    "penilaian_awal_keperawatan_ranap.tanggal between ? and ? "+
                     (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap.nip1 like ? or pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap.kd_dokter like ? or dokter.nm_dokter like ?)")+
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
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Tiba di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='250px'>Keluhan Utama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Penyakit Selama Kehamilan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='175px'>Komplikasi Kehamilan Sebelumnya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='180px'>Keterangan Komplikasi Sebelumnya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='84px'>Umur Menarche</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='78px'>Lamanya Mens</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='108px'>Banyaknya Pembalut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Siklus Haid</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'>Ket.Siklus Haid</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='136px'>Dirasakan Saat Menstruasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='81px'>Status Menikah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='54px'>Jml.Nikah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>G</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>P</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>A</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='37px'>Hidup</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>HPHT</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Usia Hamil</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Tg.Perkiraan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='97px'>Riwayat Imunisasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>ANC</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>ANC Ke</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Ket. ANC</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>Keluhan Hamil Muda</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='98px'>Keluhan Hamil Tua</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Keluarga Berencana</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Lamanya KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Komplikasi KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>Ket Komplikasi KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Berhenti KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>Alasan Berhenti KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='104px'>Riwayat Genekologi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Obat/Vitamin</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Keterangan Obat/Vitamin</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Rokok/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='68px'>Alkohol/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'>Obat Tidur/Narkoba</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='84px'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>TD</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>RR</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>Suhu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>SpO2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>BB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>TB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>LILA</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>TFU</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>TBJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='27px'>GD</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>Letak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Presentasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Penurunan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'>Kontraksi/HIS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Kekuatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Lamanya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>DJJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>Keterangan DJJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Portio</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>Serviks</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'>Ketuban</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'>Hodge</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='145px'>Panggul</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Inspekulo</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Inspekulo</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Lakmus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Lakmus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>CTG</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan CTG</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Muka</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='128px'>Mata</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Hidung</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Telinga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Dada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Perut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Genitalia</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Ekstremitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>a. Aktivitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>Ket. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='68px'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='101px'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>e. Ekstrimitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Ket. Ekstrimitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>f. Ekstrimitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='118px'>Ket. Ekstrimitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='154px'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='164px'>Ket. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='133px'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='143px'>Ket. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='98px'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Keterangan Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='156px'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='198px'>d. Hubungan dengan Anggota Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='95px'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'>Keterangan Tinggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='142px'>Ket. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='108px'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='148px'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='148px'>Keterangan Edukasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='83px'>Penilaian Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='83px'>Penyebab</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Penyebab</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Kualitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Keterangan Kualitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Lokasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Nyeri hilang bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='126px'>Keterangan Nyeri Hilang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Diberitahukan Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Pada Jam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>1. Riwayat Jatuh</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='225px'>2. Diagnosis Sekunder (&GreaterEqual; 2 Diagnosis Medis)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='218px'>3. Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>4. Terpasang Infuse</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='225px'>5. Gaya Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='215px'>6. Status Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='58px'>Total Nilai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='380px'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='317px'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='58px'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='165px'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='149px'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='209px'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'>Asesmen/Penilaian Kebidanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'>Rencana Kebidanan</td>"+
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
                                            "<td valign='top'>"+rs.getString("informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keluhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("psk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("komplikasi_sebelumnya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_komplikasi_sebelumnya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_umur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_banyaknya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_siklus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_ket_siklus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_dirasakan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_status")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_status")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_g")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_p")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_a")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_hidup")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_hpht")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_usiahamil")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_tp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_imunisasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_anc")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_ancke")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_ket_ancke")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_keluhan_hamil_muda")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_keluhan_hamil_tua")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_komplikasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_ket_komplikasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_kapaberhenti")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_alasanberhenti")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_genekologi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_obat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_obat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lila")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tfu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tbj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_letak")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_presentasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_penurunan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_penurunan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_his")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_kekuatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_djj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_djj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_portio")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_pembukaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ketuban")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_hodge")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_panggul")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_inspekulo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_inspekulo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lakmus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_lakmus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ctg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_ctg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_kepala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_muka")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_mata")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_hidung")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_telinga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_mulut")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_dada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_perut")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_genitalia")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_ekstrimitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_berjalan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktivitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_ekstrimitas_atas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_ekstrimitas_bawah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_menggenggam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_kemampuan_menggenggam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_koordinasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_gangguan_fungsi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisipsiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_adakah_prilaku")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_adakah_prilaku")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal_dengan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_tinggal_dengan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_budaya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_budaya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pend_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_pada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_edukasi_pada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("masalah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanKebidananRanap.html");            
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
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25px'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>NIP Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama Pengkaji 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>NIP Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama Pengkaji 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>Kode DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Nama DPJP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='117px'>Tgl.Asuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Anamnesis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Tiba di Ruang Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Cara Masuk</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='250px'>Keluhan Utama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Penyakit Selama Kehamilan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Penyakit Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Pembedahan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Riwayat Alergi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='175px'>Komplikasi Kehamilan Sebelumnya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='180px'>Keterangan Komplikasi Sebelumnya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='84px'>Umur Menarche</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='78px'>Lamanya Mens</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='108px'>Banyaknya Pembalut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Siklus Haid</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'>Ket.Siklus Haid</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='136px'>Dirasakan Saat Menstruasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='81px'>Status Menikah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='54px'>Jml.Nikah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='96px'>Usia Perkawinan 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>Status Perkawinan 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>G</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>P</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22px'>A</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='37px'>Hidup</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>HPHT</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='59px'>Usia Hamil</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Tg.Perkiraan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='97px'>Riwayat Imunisasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>ANC</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>ANC Ke</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Ket. ANC</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>Keluhan Hamil Muda</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='98px'>Keluhan Hamil Tua</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Riwayat Keluarga Berencana</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>Lamanya KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Komplikasi KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>Ket Komplikasi KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>Berhenti KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>Alasan Berhenti KB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='104px'>Riwayat Genekologi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Obat/Vitamin</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'>Keterangan Obat/Vitamin</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Merokok</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Rokok/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>Alkohol</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='68px'>Alkohol/Hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'>Obat Tidur/Narkoba</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'>Kesadaran Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='84px'>Keadaan Umum</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>GCS(E,V,M)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>TD</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>Nadi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>RR</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>Suhu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>SpO2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>BB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'>TB</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>LILA</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>TFU</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>TBJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='27px'>GD</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>Letak</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Presentasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Penurunan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'>Kontraksi/HIS</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'>Kekuatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Lamanya</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>DJJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'>Keterangan DJJ</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Portio</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45px'>Serviks</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'>Ketuban</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'>Hodge</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='145px'>Panggul</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Inspekulo</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Inspekulo</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Lakmus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Lakmus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>CTG</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan CTG</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'>Kepala</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Muka</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='128px'>Mata</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Hidung</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Telinga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'>Mulut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>Leher</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Dada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Perut</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Genitalia</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Ekstremitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'>a. Aktivitas Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>b. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='140px'>Ket. Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='68px'>c. Aktifitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='101px'>d. Alat Ambulasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>e. Ekstrimitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Ket. Ekstrimitas Atas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>f. Ekstrimitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='118px'>Ket. Ekstrimitas Bawah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='154px'>g. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='164px'>Ket. Kemampuan Menggenggam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='133px'>h. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='143px'>Ket. Kemampuan Koordinasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>i. Kesimpulan Gangguan Fungsi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='106px'>a. Kondisi Psikologis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='98px'>b. Adakah Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Keterangan Perilaku</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='156px'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='198px'>d. Hubungan dengan Anggota Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'>e. Agama</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='95px'>f. Tinggal Dengan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'>Keterangan Tinggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>g. Pekerjaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'>h. Pembayaran</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'>i. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='142px'>Ket. Nilai-nilai Kepercayaan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>j. Bahasa Sehari-hari</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='108px'>k. Pendidikan Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>l. Pendidikan P.J.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='148px'>m. Edukasi Diberikan Kepada</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='148px'>Keterangan Edukasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='83px'>Penilaian Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='83px'>Penyebab</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'>Keterangan Penyebab</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Kualitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Keterangan Kualitas</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'>Lokasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'>Menyebar</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='63px'>Skala Nyeri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Durasi</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='87px'>Nyeri hilang bila</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='126px'>Keterangan Nyeri Hilang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='110px'>Diberitahukan Dokter</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'>Pada Jam</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'>1. Riwayat Jatuh</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='225px'>2. Diagnosis Sekunder (&GreaterEqual; 2 Diagnosis Medis)</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='218px'>3. Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 3</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'>4. Terpasang Infuse</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 4</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='225px'>5. Gaya Berjalan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 5</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='215px'>6. Status Mental</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Nilai 6</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='58px'>Total Nilai</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='380px'>1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Skor 1</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='317px'>2. Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'>Skor 2</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='58px'>Total Skor</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='165px'>Pasien dengan diagnosis khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='149px'>Keterangan Diagnosa Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='209px'>Sudah dibaca dan diketahui oleh Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='107px'>Jam Dibaca Dietisen</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'>Asesmen/Penilaian Kebidanan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'>Rencana Kebidanan</td>"+
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
                                            "<td valign='top'>"+rs.getString("informasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("tiba_diruang_rawat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("cara_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keluhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("psk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("komplikasi_sebelumnya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_komplikasi_sebelumnya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_umur")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_banyaknya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_siklus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_ket_siklus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_mens_dirasakan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_status")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_status")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_usia3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_perkawinan_ket_usia3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_g")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_p")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_a")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_persalinan_hidup")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_hpht")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_usiahamil")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_tp")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_imunisasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_anc")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_ancke")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_ket_ancke")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_keluhan_hamil_muda")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_hamil_keluhan_hamil_tua")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_komplikasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_ket_komplikasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_kapaberhenti")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kb_alasanberhenti")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_genekologi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_obat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_obat")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_merokok")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_alkohol")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_kebiasaan_narkoba")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_mental")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_keadaan_umum")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_gcs")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_td")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_nadi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_rr")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_suhu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_spo2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_bb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tb")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lila")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tfu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_tbj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_letak")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_presentasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_penurunan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_penurunan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_his")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_kekuatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lamanya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_djj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_djj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_portio")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_pembukaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ketuban")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_hodge")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_panggul")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_inspekulo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_inspekulo")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_lakmus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_lakmus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ctg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_kebidanan_ket_ctg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_kepala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_muka")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_mata")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_hidung")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_telinga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_mulut")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_leher")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_dada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_perut")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_genitalia")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pemeriksaan_umum_ekstrimitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_aktifitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_berjalan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_berjalan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_aktivitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_ekstrimitas_atas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_ekstrimitas_bawah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_kemampuan_menggenggam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_kemampuan_menggenggam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_koordinasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_ket_koordinasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengkajian_fungsi_gangguan_fungsi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_kondisipsiko")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_adakah_prilaku")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_adakah_prilaku")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_hubungan_pasien")+"</td>"+
                                            "<td valign='top'>"+rs.getString("agama")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_tinggal_dengan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_tinggal_dengan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pekerjaan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("png_jawab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_budaya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_budaya")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pnd")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_pend_pj")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_edukasi_pada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("riwayat_psiko_ket_edukasi_pada")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_penyebab")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_kualitas")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_lokasi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_menyebar")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_skala")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_waktu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_ket_hilang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai3")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai4")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai5")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_skala6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_nilai6")+"</td>"+
                                            "<td valign='top'>"+rs.getString("penilaian_jatuh_totalnilai")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi1")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_gizi2")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nilai_total_gizi")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"</td>"+
                                            "<td valign='top'>"+rs.getString("masalah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanKebidananRanap.wps");            
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
                                    "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"NIP Pengkaji 1\";\"Nama Pengkaji 1\";\"NIP Pengkaji 2\";\"Nama Pengkaji 2\";\"Kode DPJP\";\"Nama DPJP\";\"Tgl.Asuhan\";\"Anamnesis\";\"Tiba di Ruang Rawat\";\"Cara Masuk\";\"Keluhan Utama\";\"Penyakit Selama Kehamilan\";\"Riwayat Penyakit Keluarga\";\"Riwayat Pembedahan\";\"Riwayat Alergi\";\"Komplikasi Kehamilan Sebelumnya\";\"Keterangan Komplikasi Sebelumnya\";\"Umur Menarche\";\"Lamanya Mens\";\"Banyaknya Pembalut\";\"Siklus Haid\";\"Ket.Siklus Haid\";\"Dirasakan Saat Menstruasi\";\"Status Menikah\";\"Jml.Nikah\";\"Usia Perkawinan 1\";\"Status Perkawinan 1\";\"Usia Perkawinan 2\";\"Status Perkawinan 2\";\"Usia Perkawinan 3\";\"Status Perkawinan 3\";\"G\";\"P\";\"A\";\"Hidup\";\"HPHT\";\"Usia Hamil\";\"Tg.Perkiraan\";\"Riwayat Imunisasi\";\"ANC\";\"ANC Ke\";\"Ket. ANC\";\"Keluhan Hamil Muda\";\"Keluhan Hamil Tua\";\"Riwayat Keluarga Berencana\";\"Lamanya KB\";\"Komplikasi KB\";\"Ket Komplikasi KB\";\"Berhenti KB\";\"Alasan Berhenti KB\";\"Riwayat Genekologi\";\"Obat/Vitamin\";\"Keterangan Obat/Vitamin\";\"Merokok\";\"Rokok/Hari\";\"Alkohol\";\"Alkohol/Hari\";\"Obat Tidur/Narkoba\";\"Kesadaran Mental\";\"Keadaan Umum\";\"GCS(E,V,M)\";\"TD\";\"Nadi\";\"RR\";\"Suhu\";\"SpO2\";\"BB\";\"TB\";\"LILA\";\"TFU\";\"TBJ\";\"GD\";\"Letak\";\"Presentasi\";\"Penurunan\";\"Kontraksi/HIS\";\"Kekuatan\";\"Lamanya\";\"DJJ\";\"Keterangan DJJ\";\"Portio\";\"Serviks\";\"Ketuban\";\"Hodge\";\"Panggul\";\"Inspekulo\";\"Keterangan Inspekulo\";\"Lakmus\";\"Keterangan Lakmus\";\"CTG\";\"Keterangan CTG\";\"Kepala\";\"Muka\";\"Mata\";\"Hidung\";\"Telinga\";\"Mulut\";\"Leher\";\"Dada\";\"Perut\";\"Genitalia\";\"Ekstremitas\";\"a. Aktivitas Sehari-hari\";\"b. Berjalan\";\"Ket. Berjalan\";\"c. Aktifitas\";\"d. Alat Ambulasi\";\"e. Ekstrimitas Atas\";\"Ket. Ekstrimitas Atas\";\"f. Ekstrimitas Bawah\";\"Ket. Ekstrimitas Bawah\";\"g. Kemampuan Menggenggam\";\"Ket. Kemampuan Menggenggam\";\"h. Kemampuan Koordinasi\";\"Ket. Kemampuan Koordinasi\";\"i. Kesimpulan Gangguan Fungsi\";\"a. Kondisi Psikologis\";\"b. Adakah Perilaku\";\"Keterangan Perilaku\";\"c. Gangguan Jiwa di Masa Lalu\";\"d. Hubungan dengan Anggota Keluarga\";\"e. Agama\";\"f. Tinggal Dengan\";\"Keterangan Tinggal\";\"g. Pekerjaan\";\"h. Pembayaran\";\"i. Nilai-nilai Kepercayaan\";\"Ket. Nilai-nilai Kepercayaan\";\"j. Bahasa Sehari-hari\";\"k. Pendidikan Pasien\";\"l. Pendidikan P.J.\";\"m. Edukasi Diberikan Kepada\";\"Keterangan Edukasi\";\"Penilaian Nyeri\";\"Penyebab\";\"Keterangan Penyebab\";\"Kualitas\";\"Keterangan Kualitas\";\"Lokasi\";\"Menyebar\";\"Skala Nyeri\";\"Durasi\";\"Nyeri hilang bila\";\"Keterangan Nyeri Hilang\";\"Diberitahukan Dokter\";\"Pada Jam\";\"1. Riwayat Jatuh\";\"Nilai 1\";\"2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)\";\"Nilai 2\";\"3. Alat Bantu\";\"Nilai 3\";\"4. Terpasang Infuse\";\"Nilai 4\";\"5. Gaya Berjalan\";\"Nilai 5\";\"6. Status Mental\";\"Nilai 6\";\"Total Nilai\";\"1. Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?\";\"Skor 1\";\"2. Apakah asupan makan berkurang karena tidak nafsu makan ?\";\"Skor 2\";\"Total Skor\";\"Pasien dengan diagnosis khusus\";\"Keterangan Diagnosa Khusus\";\"Sudah dibaca dan diketahui oleh Dietisen\";\"Jam Dibaca Dietisen\";\"Asesmen/Penilaian Kebidanan\";\"Rencana Kebidanan\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_rawat")+"\";\""+" "+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("nip1")+"\";\""+rs.getString("pengkaji1")+"\";\""+rs.getString("nip2")+"\";\""+rs.getString("pengkaji2")+"\";\""+rs.getString("kd_dokter")+"\";\""+rs.getString("nm_dokter")+"\";\""+rs.getString("tanggal")+"\";\""+rs.getString("informasi")+"\";\""+rs.getString("tiba_diruang_rawat")+"\";\""+rs.getString("cara_masuk")+"\";\""+rs.getString("keluhan")+"\";\""+rs.getString("psk")+"\";\""+rs.getString("rpk")+"\";\""+rs.getString("rp")+"\";\""+rs.getString("alergi")+"\";\""+rs.getString("komplikasi_sebelumnya")+"\";\""+rs.getString("keterangan_komplikasi_sebelumnya")+"\";\""+rs.getString("riwayat_mens_umur")+"\";\""+rs.getString("riwayat_mens_lamanya")+"\";\""+rs.getString("riwayat_mens_banyaknya")+"\";\""+rs.getString("riwayat_mens_siklus")+"\";\""+rs.getString("riwayat_mens_ket_siklus")+"\";\""+rs.getString("riwayat_mens_dirasakan")+"\";\""+rs.getString("riwayat_perkawinan_status")+"\";\""+rs.getString("riwayat_perkawinan_ket_status")+"\";\""+rs.getString("riwayat_perkawinan_usia1")+"\";\""+rs.getString("riwayat_perkawinan_ket_usia1")+"\";\""+rs.getString("riwayat_perkawinan_usia2")+"\";\""+rs.getString("riwayat_perkawinan_ket_usia2")+"\";\""+rs.getString("riwayat_perkawinan_usia3")+"\";\""+rs.getString("riwayat_perkawinan_ket_usia3")+"\";\""+rs.getString("riwayat_persalinan_g")+"\";\""+rs.getString("riwayat_persalinan_p")+"\";\""+rs.getString("riwayat_persalinan_a")+"\";\""+rs.getString("riwayat_persalinan_hidup")+"\";\""+rs.getString("riwayat_hamil_hpht")+"\";\""+rs.getString("riwayat_hamil_usiahamil")+"\";\""+rs.getString("riwayat_hamil_tp")+"\";\""+rs.getString("riwayat_hamil_imunisasi")+"\";\""+rs.getString("riwayat_hamil_anc")+"\";\""+rs.getString("riwayat_hamil_ancke")+"\";\""+rs.getString("riwayat_hamil_ket_ancke")+"\";\""+rs.getString("riwayat_hamil_keluhan_hamil_muda")+"\";\""+rs.getString("riwayat_hamil_keluhan_hamil_tua")+"\";\""+rs.getString("riwayat_kb")+"\";\""+rs.getString("riwayat_kb_lamanya")+"\";\""+rs.getString("riwayat_kb_komplikasi")+"\";\""+rs.getString("riwayat_kb_ket_komplikasi")+"\";\""+rs.getString("riwayat_kb_kapaberhenti")+"\";\""+rs.getString("riwayat_kb_alasanberhenti")+"\";\""+rs.getString("riwayat_genekologi")+"\";\""+rs.getString("riwayat_kebiasaan_obat")+"\";\""+rs.getString("riwayat_kebiasaan_ket_obat")+"\";\""+rs.getString("riwayat_kebiasaan_merokok")+"\";\""+rs.getString("riwayat_kebiasaan_ket_merokok")+"\";\""+rs.getString("riwayat_kebiasaan_alkohol")+"\";\""+rs.getString("riwayat_kebiasaan_ket_alkohol")+"\";\""+rs.getString("riwayat_kebiasaan_narkoba")+"\";\""+rs.getString("pemeriksaan_kebidanan_mental")+"\";\""+rs.getString("pemeriksaan_kebidanan_keadaan_umum")+"\";\""+rs.getString("pemeriksaan_kebidanan_gcs")+"\";\""+rs.getString("pemeriksaan_kebidanan_td")+"\";\""+rs.getString("pemeriksaan_kebidanan_nadi")+"\";\""+rs.getString("pemeriksaan_kebidanan_rr")+"\";\""+rs.getString("pemeriksaan_kebidanan_suhu")+"\";\""+rs.getString("pemeriksaan_kebidanan_spo2")+"\";\""+rs.getString("pemeriksaan_kebidanan_bb")+"\";\""+rs.getString("pemeriksaan_kebidanan_tb")+"\";\""+rs.getString("pemeriksaan_kebidanan_lila")+"\";\""+rs.getString("pemeriksaan_kebidanan_tfu")+"\";\""+rs.getString("pemeriksaan_kebidanan_tbj")+"\";\""+rs.getString("pemeriksaan_kebidanan_letak")+"\";\""+rs.getString("pemeriksaan_kebidanan_presentasi")+"\";\""+rs.getString("pemeriksaan_kebidanan_penurunan")+"\";\""+rs.getString("pemeriksaan_kebidanan_penurunan")+"\";\""+rs.getString("pemeriksaan_kebidanan_his")+"\";\""+rs.getString("pemeriksaan_kebidanan_kekuatan")+"\";\""+rs.getString("pemeriksaan_kebidanan_lamanya")+"\";\""+rs.getString("pemeriksaan_kebidanan_djj")+"\";\""+rs.getString("pemeriksaan_kebidanan_ket_djj")+"\";\""+rs.getString("pemeriksaan_kebidanan_portio")+"\";\""+rs.getString("pemeriksaan_kebidanan_pembukaan")+"\";\""+rs.getString("pemeriksaan_kebidanan_ketuban")+"\";\""+rs.getString("pemeriksaan_kebidanan_hodge")+"\";\""+rs.getString("pemeriksaan_kebidanan_panggul")+"\";\""+rs.getString("pemeriksaan_kebidanan_inspekulo")+"\";\""+rs.getString("pemeriksaan_kebidanan_ket_inspekulo")+"\";\""+rs.getString("pemeriksaan_kebidanan_lakmus")+"\";\""+rs.getString("pemeriksaan_kebidanan_ket_lakmus")+"\";\""+rs.getString("pemeriksaan_kebidanan_ctg")+"\";\""+rs.getString("pemeriksaan_kebidanan_ket_ctg")+"\";\""+rs.getString("pemeriksaan_umum_kepala")+"\";\""+rs.getString("pemeriksaan_umum_muka")+"\";\""+rs.getString("pemeriksaan_umum_mata")+"\";\""+rs.getString("pemeriksaan_umum_hidung")+"\";\""+rs.getString("pemeriksaan_umum_telinga")+"\";\""+rs.getString("pemeriksaan_umum_mulut")+"\";\""+rs.getString("pemeriksaan_umum_leher")+"\";\""+rs.getString("pemeriksaan_umum_dada")+"\";\""+rs.getString("pemeriksaan_umum_perut")+"\";\""+rs.getString("pemeriksaan_umum_genitalia")+"\";\""+rs.getString("pemeriksaan_umum_ekstrimitas")+"\";\""+rs.getString("pengkajian_fungsi_kemampuan_aktifitas")+"\";\""+rs.getString("pengkajian_fungsi_berjalan")+"\";\""+rs.getString("pengkajian_fungsi_ket_berjalan")+"\";\""+rs.getString("pengkajian_fungsi_aktivitas")+"\";\""+rs.getString("pengkajian_fungsi_ambulasi")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_atas")+"\";\""+rs.getString("pengkajian_fungsi_ket_ekstrimitas_atas")+"\";\""+rs.getString("pengkajian_fungsi_ekstrimitas_bawah")+"\";\""+rs.getString("pengkajian_fungsi_ket_ekstrimitas_bawah")+"\";\""+rs.getString("pengkajian_fungsi_kemampuan_menggenggam")+"\";\""+rs.getString("pengkajian_fungsi_ket_kemampuan_menggenggam")+"\";\""+rs.getString("pengkajian_fungsi_koordinasi")+"\";\""+rs.getString("pengkajian_fungsi_ket_koordinasi")+"\";\""+rs.getString("pengkajian_fungsi_gangguan_fungsi")+"\";\""+rs.getString("riwayat_psiko_kondisipsiko")+"\";\""+rs.getString("riwayat_psiko_adakah_prilaku")+"\";\""+rs.getString("riwayat_psiko_ket_adakah_prilaku")+"\";\""+rs.getString("riwayat_psiko_gangguan_jiwa")+"\";\""+rs.getString("riwayat_psiko_hubungan_pasien")+"\";\""+rs.getString("agama")+"\";\""+rs.getString("riwayat_psiko_tinggal_dengan")+"\";\""+rs.getString("riwayat_psiko_ket_tinggal_dengan")+"\";\""+rs.getString("pekerjaan")+"\";\""+rs.getString("png_jawab")+"\";\""+rs.getString("riwayat_psiko_budaya")+"\";\""+rs.getString("riwayat_psiko_ket_budaya")+"\";\""+rs.getString("nama_bahasa")+"\";\""+rs.getString("pnd")+"\";\""+rs.getString("riwayat_psiko_pend_pj")+"\";\""+rs.getString("riwayat_psiko_edukasi_pada")+"\";\""+rs.getString("riwayat_psiko_ket_edukasi_pada")+"\";\""+rs.getString("penilaian_nyeri")+"\";\""+rs.getString("penilaian_nyeri_penyebab")+"\";\""+rs.getString("penilaian_nyeri_ket_penyebab")+"\";\""+rs.getString("penilaian_nyeri_kualitas")+"\";\""+rs.getString("penilaian_nyeri_ket_kualitas")+"\";\""+rs.getString("penilaian_nyeri_lokasi")+"\";\""+rs.getString("penilaian_nyeri_menyebar")+"\";\""+rs.getString("penilaian_nyeri_skala")+"\";\""+rs.getString("penilaian_nyeri_waktu")+"\";\""+rs.getString("penilaian_nyeri_hilang")+"\";\""+rs.getString("penilaian_nyeri_ket_hilang")+"\";\""+rs.getString("penilaian_nyeri_diberitahukan_dokter")+"\";\""+rs.getString("penilaian_nyeri_jam_diberitahukan_dokter")+"\";\""+rs.getString("penilaian_jatuh_skala1")+"\";\""+rs.getString("penilaian_jatuh_nilai1")+"\";\""+rs.getString("penilaian_jatuh_skala2")+"\";\""+rs.getString("penilaian_jatuh_nilai2")+"\";\""+rs.getString("penilaian_jatuh_skala3")+"\";\""+rs.getString("penilaian_jatuh_nilai3")+"\";\""+rs.getString("penilaian_jatuh_skala4")+"\";\""+rs.getString("penilaian_jatuh_nilai4")+"\";\""+rs.getString("penilaian_jatuh_skala5")+"\";\""+rs.getString("penilaian_jatuh_nilai5")+"\";\""+rs.getString("penilaian_jatuh_skala6")+"\";\""+rs.getString("penilaian_jatuh_nilai6")+"\";\""+rs.getString("penilaian_jatuh_totalnilai")+"\";\""+rs.getString("skrining_gizi1")+"\";\""+rs.getString("nilai_gizi1")+"\";\""+rs.getString("skrining_gizi2")+"\";\""+rs.getString("nilai_gizi2")+"\";\""+rs.getString("nilai_total_gizi")+"\";\""+rs.getString("skrining_gizi_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_ket_diagnosa_khusus")+"\";\""+rs.getString("skrining_gizi_diketahui_dietisen")+"\";\""+rs.getString("skrining_gizi_jam_diketahui_dietisen")+"\";\""+rs.getString("masalah")+"\";\""+rs.getString("rencana")+"\"\n"
                                    );
                                }
                                f = new File("RMPenilaianAwalKeperawatanKebidananRanap.csv");            
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
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
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
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRanap.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,penilaian_awal_keperawatan_kebidanan.tanggal,"+
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
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalKebidananRalan2.jasper","report","::[ Laporan Penilaian Awal Ralan Kebidanan & Kandungan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,penilaian_awal_keperawatan_kebidanan.tanggal,"+
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
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TanggalPersalinan,TibadiRuang);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
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

    private void BtnPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugas2ActionPerformed
        i=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugas2ActionPerformed

    private void BtnPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugas2KeyPressed
        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void TibadiRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TibadiRuangKeyPressed
        Valid.pindah(evt,Anamnesis,CaraMasuk);
    }//GEN-LAST:event_TibadiRuangKeyPressed

    private void CaraMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraMasukKeyPressed
        Valid.pindah(evt,TibadiRuang,RPS);
    }//GEN-LAST:event_CaraMasukKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        //Valid.pindah(evt,RPO,KomplikasiKehamilan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,CaraMasuk,RPD);
    }//GEN-LAST:event_RPSKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah2(evt,RPD,RPO);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah2(evt,RPS,RPK);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah2(evt,RPK,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void MacamKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MacamKasusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MacamKasusKeyPressed

    private void KetAnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAnamnesisKeyPressed

    private void RDirawatRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RDirawatRSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RDirawatRSKeyPressed

    private void RPembedahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPembedahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPembedahanKeyPressed

    private void Anamnesis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis1KeyPressed

    private void Anamnesis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis2KeyPressed

    private void Alergi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi1KeyPressed

    private void RPembedahan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPembedahan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPembedahan1KeyPressed

    private void KebiasaanMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokokKeyPressed
        //Valid.pindah(evt,KebiasaanObatDiminum,KebiasaanJumlahRokok);
    }//GEN-LAST:event_KebiasaanMerokokKeyPressed

    private void KebiasaanJumlahRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahRokokKeyPressed
        Valid.pindah(evt,KebiasaanMerokok,KebiasaanAlkohol);
    }//GEN-LAST:event_KebiasaanJumlahRokokKeyPressed

    private void KebiasaanAlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanAlkoholKeyPressed
        Valid.pindah(evt,KebiasaanJumlahRokok,KebiasaanJumlahAlkohol);
    }//GEN-LAST:event_KebiasaanAlkoholKeyPressed

    private void KebiasaanJumlahAlkoholKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahAlkoholKeyPressed
        Valid.pindah(evt,KebiasaanAlkohol,KebiasaanNarkoba);
    }//GEN-LAST:event_KebiasaanJumlahAlkoholKeyPressed

    private void KebiasaanNarkobaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanNarkobaKeyPressed
        //Valid.pindah(evt,KebiasaanJumlahAlkohol,KesadaranMental);
    }//GEN-LAST:event_KebiasaanNarkobaKeyPressed

    private void OlahRagaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OlahRagaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OlahRagaKeyPressed

    private void KesadaranMentalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranMentalKeyPressed
        Valid.pindah(evt,KebiasaanNarkoba,KeadaanMentalUmum);
    }//GEN-LAST:event_KesadaranMentalKeyPressed

    private void KeadaanMentalUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanMentalUmumKeyPressed
        Valid.pindah(evt,KesadaranMental,GCS);
    }//GEN-LAST:event_KeadaanMentalUmumKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,KeadaanMentalUmum,TD);
    }//GEN-LAST:event_GCSKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,GCS,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        //Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SpO2);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_SpO2KeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,SpO2,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        //Valid.pindah(evt,BB,LILA);
    }//GEN-LAST:event_TBKeyPressed

    private void KebiasaanMerokok1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokok1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanMerokok1KeyPressed

    private void KebiasaanJumlahRokok1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahRokok1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanJumlahRokok1KeyPressed

    private void KebiasaanMerokok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokok2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanMerokok2KeyPressed

    private void KebiasaanJumlahRokok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahRokok2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanJumlahRokok2KeyPressed

    private void KebiasaanMerokok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokok3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanMerokok3KeyPressed

    private void KebiasaanMerokok4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokok4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanMerokok4KeyPressed

    private void KebiasaanMerokok5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanMerokok5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanMerokok5KeyPressed

    private void KebiasaanJumlahRokok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebiasaanJumlahRokok3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebiasaanJumlahRokok3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRanap dialog = new RMPenilaianAwalKeperawatanRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alergi1;
    private widget.ComboBox Anamnesis;
    private widget.ComboBox Anamnesis1;
    private widget.ComboBox Anamnesis2;
    private widget.TextBox BB;
    private widget.TextBox BBPB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDPJP;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.ComboBox CaraMasuk;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GCS;
    private widget.ComboBox JK;
    private widget.TextBox JenisPersalinan;
    private widget.TextBox Jk;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.TextBox Keadaan;
    private widget.ComboBox KeadaanMentalUmum;
    private widget.ComboBox KebiasaanAlkohol;
    private widget.TextBox KebiasaanJumlahAlkohol;
    private widget.TextBox KebiasaanJumlahRokok;
    private widget.TextBox KebiasaanJumlahRokok1;
    private widget.TextBox KebiasaanJumlahRokok2;
    private widget.TextBox KebiasaanJumlahRokok3;
    private widget.ComboBox KebiasaanMerokok;
    private widget.ComboBox KebiasaanMerokok1;
    private widget.ComboBox KebiasaanMerokok2;
    private widget.ComboBox KebiasaanMerokok3;
    private widget.ComboBox KebiasaanMerokok4;
    private widget.ComboBox KebiasaanMerokok5;
    private widget.ComboBox KebiasaanNarkoba;
    private widget.TextBox KesadaranMental;
    private widget.TextBox KetAnamnesis;
    private widget.Label LCount;
    private widget.ComboBox MacamKasus;
    private widget.TextBox Nadi;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private widget.ComboBox OlahRaga;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Penolong;
    private widget.TextBox Penyulit;
    private widget.TextBox RDirawatRS;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RPembedahan;
    private widget.TextBox RPembedahan1;
    private widget.TextBox RR;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll9;
    private widget.TextBox SpO2;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
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
    private widget.ComboBox TibadiRuang;
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
    private widget.Label jLabel12;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbMasalahDetail;
    private widget.Table tbObat;
    private widget.Table tbRencanaDetail;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select penilaian_awal_keperawatan_ranap.no_rawat,penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,"+
                "penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.keluhan,"+
                "penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.psk,penilaian_awal_keperawatan_ranap.rp,penilaian_awal_keperawatan_ranap.alergi,"+
                "penilaian_awal_keperawatan_ranap.komplikasi_sebelumnya,penilaian_awal_keperawatan_ranap.keterangan_komplikasi_sebelumnya,penilaian_awal_keperawatan_ranap.riwayat_mens_umur,"+
                "penilaian_awal_keperawatan_ranap.riwayat_mens_lamanya,penilaian_awal_keperawatan_ranap.riwayat_mens_banyaknya,penilaian_awal_keperawatan_ranap.riwayat_mens_siklus,"+
                "penilaian_awal_keperawatan_ranap.riwayat_mens_ket_siklus,penilaian_awal_keperawatan_ranap.riwayat_mens_dirasakan,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_status,"+
                "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_status,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia1,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia1,"+
                "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia2,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia2,penilaian_awal_keperawatan_ranap.riwayat_perkawinan_usia3,"+
                "penilaian_awal_keperawatan_ranap.riwayat_perkawinan_ket_usia3,penilaian_awal_keperawatan_ranap.riwayat_persalinan_g,penilaian_awal_keperawatan_ranap.riwayat_persalinan_p,"+
                "penilaian_awal_keperawatan_ranap.riwayat_persalinan_a,penilaian_awal_keperawatan_ranap.riwayat_persalinan_hidup,penilaian_awal_keperawatan_ranap.riwayat_hamil_hpht,"+
                "penilaian_awal_keperawatan_ranap.riwayat_hamil_usiahamil,penilaian_awal_keperawatan_ranap.riwayat_hamil_tp,penilaian_awal_keperawatan_ranap.riwayat_hamil_imunisasi,"+
                "penilaian_awal_keperawatan_ranap.riwayat_hamil_anc,penilaian_awal_keperawatan_ranap.riwayat_hamil_ancke,penilaian_awal_keperawatan_ranap.riwayat_hamil_ket_ancke,"+
                "penilaian_awal_keperawatan_ranap.riwayat_hamil_keluhan_hamil_muda,penilaian_awal_keperawatan_ranap.riwayat_hamil_keluhan_hamil_tua,penilaian_awal_keperawatan_ranap.riwayat_kb,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kb_lamanya,penilaian_awal_keperawatan_ranap.riwayat_kb_komplikasi,penilaian_awal_keperawatan_ranap.riwayat_kb_ket_komplikasi,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kb_kapaberhenti,penilaian_awal_keperawatan_ranap.riwayat_kb_alasanberhenti,penilaian_awal_keperawatan_ranap.riwayat_genekologi,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_obat,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_obat,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_merokok,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_merokok,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_alkohol,penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_ket_alkohol,"+
                "penilaian_awal_keperawatan_ranap.riwayat_kebiasaan_narkoba,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_keadaan_umum,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_gcs,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_nadi,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_suhu,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_spo2,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lila,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tfu,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_tbj,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_letak,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_presentasi,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_penurunan,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_his,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_kekuatan,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lamanya,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_djj,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_djj,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_portio,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_pembukaan,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ketuban,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_hodge,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_panggul,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_inspekulo,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_inspekulo,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_lakmus,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_lakmus,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ctg,penilaian_awal_keperawatan_ranap.pemeriksaan_kebidanan_ket_ctg,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_kepala,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_muka,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_mata,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_hidung,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_telinga,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_mulut,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_dada,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_perut,"+
                "penilaian_awal_keperawatan_ranap.pemeriksaan_umum_genitalia,penilaian_awal_keperawatan_ranap.pemeriksaan_umum_ekstrimitas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_aktifitas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktivitas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_ekstrimitas_atas,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_kemampuan_menggenggam,"+
                "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ket_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_gangguan_fungsi,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisipsiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_adakah_prilaku,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_adakah_prilaku,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_pasien,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_dengan,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_tinggal_dengan,penilaian_awal_keperawatan_ranap.riwayat_psiko_budaya,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_budaya,"+
                "penilaian_awal_keperawatan_ranap.riwayat_psiko_pend_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_pada,penilaian_awal_keperawatan_ranap.riwayat_psiko_ket_edukasi_pada,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai1,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala3,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai4,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuh_skala6,"+
                "penilaian_awal_keperawatan_ranap.penilaian_jatuh_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuh_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,"+
                "penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,penilaian_awal_keperawatan_ranap.nilai_gizi2,bahasa_pasien.nama_bahasa,"+
                "penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.masalah,"+
                "penilaian_awal_keperawatan_ranap.rencana,penilaian_awal_keperawatan_ranap.nip1,penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter, "+
                "pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                "penilaian_awal_keperawatan_ranap.tanggal between ? and ? "+
                 (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap.nip1 like ? or pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap.kd_dokter like ? or dokter.nm_dokter like ?)")+
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
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("tiba_diruang_rawat"),rs.getString("cara_masuk"),rs.getString("keluhan"),rs.getString("psk"),rs.getString("rpk"),
                        rs.getString("rp"),rs.getString("alergi"),rs.getString("komplikasi_sebelumnya"),rs.getString("keterangan_komplikasi_sebelumnya"),rs.getString("riwayat_mens_umur"),rs.getString("riwayat_mens_lamanya"),rs.getString("riwayat_mens_banyaknya"),
                        rs.getString("riwayat_mens_siklus"),rs.getString("riwayat_mens_ket_siklus"),rs.getString("riwayat_mens_dirasakan"),rs.getString("riwayat_perkawinan_status"),rs.getString("riwayat_perkawinan_ket_status"),rs.getString("riwayat_perkawinan_usia1"),
                        rs.getString("riwayat_perkawinan_ket_usia1"),rs.getString("riwayat_perkawinan_usia2"),rs.getString("riwayat_perkawinan_ket_usia2"),rs.getString("riwayat_perkawinan_usia3"),rs.getString("riwayat_perkawinan_ket_usia3"),
                        rs.getString("riwayat_persalinan_g"),rs.getString("riwayat_persalinan_p"),rs.getString("riwayat_persalinan_a"),rs.getString("riwayat_persalinan_hidup"),rs.getString("riwayat_hamil_hpht"),rs.getString("riwayat_hamil_usiahamil"),
                        rs.getString("riwayat_hamil_tp"),rs.getString("riwayat_hamil_imunisasi"),rs.getString("riwayat_hamil_anc"),rs.getString("riwayat_hamil_ancke"),rs.getString("riwayat_hamil_ket_ancke"),rs.getString("riwayat_hamil_keluhan_hamil_muda"),
                        rs.getString("riwayat_hamil_keluhan_hamil_tua"),rs.getString("riwayat_kb"),rs.getString("riwayat_kb_lamanya"),rs.getString("riwayat_kb_komplikasi"),rs.getString("riwayat_kb_ket_komplikasi"),rs.getString("riwayat_kb_kapaberhenti"),
                        rs.getString("riwayat_kb_alasanberhenti"),rs.getString("riwayat_genekologi"),rs.getString("riwayat_kebiasaan_obat"),rs.getString("riwayat_kebiasaan_ket_obat"),rs.getString("riwayat_kebiasaan_merokok"),rs.getString("riwayat_kebiasaan_ket_merokok"),
                        rs.getString("riwayat_kebiasaan_alkohol"),rs.getString("riwayat_kebiasaan_ket_alkohol"),rs.getString("riwayat_kebiasaan_narkoba"),rs.getString("pemeriksaan_kebidanan_mental"),rs.getString("pemeriksaan_kebidanan_keadaan_umum"),
                        rs.getString("pemeriksaan_kebidanan_gcs"),rs.getString("pemeriksaan_kebidanan_td"),rs.getString("pemeriksaan_kebidanan_nadi"),rs.getString("pemeriksaan_kebidanan_rr"),rs.getString("pemeriksaan_kebidanan_suhu"),
                        rs.getString("pemeriksaan_kebidanan_spo2"),rs.getString("pemeriksaan_kebidanan_bb"),rs.getString("pemeriksaan_kebidanan_tb"),rs.getString("pemeriksaan_kebidanan_lila"),rs.getString("pemeriksaan_kebidanan_tfu"),
                        rs.getString("pemeriksaan_kebidanan_tbj"),rs.getString("pemeriksaan_kebidanan_letak"),rs.getString("pemeriksaan_kebidanan_presentasi"),rs.getString("pemeriksaan_kebidanan_penurunan"),rs.getString("pemeriksaan_kebidanan_penurunan"),
                        rs.getString("pemeriksaan_kebidanan_his"),rs.getString("pemeriksaan_kebidanan_kekuatan"),rs.getString("pemeriksaan_kebidanan_lamanya"),rs.getString("pemeriksaan_kebidanan_djj"),rs.getString("pemeriksaan_kebidanan_ket_djj"),
                        rs.getString("pemeriksaan_kebidanan_portio"),rs.getString("pemeriksaan_kebidanan_pembukaan"),rs.getString("pemeriksaan_kebidanan_ketuban"),rs.getString("pemeriksaan_kebidanan_hodge"),rs.getString("pemeriksaan_kebidanan_panggul"),
                        rs.getString("pemeriksaan_kebidanan_inspekulo"),rs.getString("pemeriksaan_kebidanan_ket_inspekulo"),rs.getString("pemeriksaan_kebidanan_lakmus"),rs.getString("pemeriksaan_kebidanan_ket_lakmus"),rs.getString("pemeriksaan_kebidanan_ctg"),
                        rs.getString("pemeriksaan_kebidanan_ket_ctg"),rs.getString("pemeriksaan_umum_kepala"),rs.getString("pemeriksaan_umum_muka"),rs.getString("pemeriksaan_umum_mata"),rs.getString("pemeriksaan_umum_hidung"),rs.getString("pemeriksaan_umum_telinga"),
                        rs.getString("pemeriksaan_umum_mulut"),rs.getString("pemeriksaan_umum_leher"),rs.getString("pemeriksaan_umum_dada"),rs.getString("pemeriksaan_umum_perut"),rs.getString("pemeriksaan_umum_genitalia"),rs.getString("pemeriksaan_umum_ekstrimitas"),
                        rs.getString("pengkajian_fungsi_kemampuan_aktifitas"),rs.getString("pengkajian_fungsi_berjalan"),rs.getString("pengkajian_fungsi_ket_berjalan"),rs.getString("pengkajian_fungsi_aktivitas"),rs.getString("pengkajian_fungsi_ambulasi"),
                        rs.getString("pengkajian_fungsi_ekstrimitas_atas"),rs.getString("pengkajian_fungsi_ket_ekstrimitas_atas"),rs.getString("pengkajian_fungsi_ekstrimitas_bawah"),rs.getString("pengkajian_fungsi_ket_ekstrimitas_bawah"),
                        rs.getString("pengkajian_fungsi_kemampuan_menggenggam"),rs.getString("pengkajian_fungsi_ket_kemampuan_menggenggam"),rs.getString("pengkajian_fungsi_koordinasi"),rs.getString("pengkajian_fungsi_ket_koordinasi"),
                        rs.getString("pengkajian_fungsi_gangguan_fungsi"),rs.getString("riwayat_psiko_kondisipsiko"),rs.getString("riwayat_psiko_adakah_prilaku"),rs.getString("riwayat_psiko_ket_adakah_prilaku"),rs.getString("riwayat_psiko_gangguan_jiwa"),
                        rs.getString("riwayat_psiko_hubungan_pasien"),rs.getString("agama"),rs.getString("riwayat_psiko_tinggal_dengan"),rs.getString("riwayat_psiko_ket_tinggal_dengan"),rs.getString("pekerjaan"),rs.getString("png_jawab"),
                        rs.getString("riwayat_psiko_budaya"),rs.getString("riwayat_psiko_ket_budaya"),rs.getString("nama_bahasa"),rs.getString("pnd"),rs.getString("riwayat_psiko_pend_pj"),rs.getString("riwayat_psiko_edukasi_pada"),
                        rs.getString("riwayat_psiko_ket_edukasi_pada"),rs.getString("penilaian_nyeri"),rs.getString("penilaian_nyeri_penyebab"),rs.getString("penilaian_nyeri_ket_penyebab"),rs.getString("penilaian_nyeri_kualitas"),
                        rs.getString("penilaian_nyeri_ket_kualitas"),rs.getString("penilaian_nyeri_lokasi"),rs.getString("penilaian_nyeri_menyebar"),rs.getString("penilaian_nyeri_skala"),rs.getString("penilaian_nyeri_waktu"),rs.getString("penilaian_nyeri_hilang"),
                        rs.getString("penilaian_nyeri_ket_hilang"),rs.getString("penilaian_nyeri_diberitahukan_dokter"),rs.getString("penilaian_nyeri_jam_diberitahukan_dokter"),rs.getString("penilaian_jatuh_skala1"),rs.getString("penilaian_jatuh_nilai1"),
                        rs.getString("penilaian_jatuh_skala2"),rs.getString("penilaian_jatuh_nilai2"),rs.getString("penilaian_jatuh_skala3"),rs.getString("penilaian_jatuh_nilai3"),rs.getString("penilaian_jatuh_skala4"),rs.getString("penilaian_jatuh_nilai4"),
                        rs.getString("penilaian_jatuh_skala5"),rs.getString("penilaian_jatuh_nilai5"),rs.getString("penilaian_jatuh_skala6"),rs.getString("penilaian_jatuh_nilai6"),rs.getString("penilaian_jatuh_totalnilai"),rs.getString("skrining_gizi1"),
                        rs.getString("nilai_gizi1"),rs.getString("skrining_gizi2"),rs.getString("nilai_gizi2"),rs.getString("nilai_total_gizi"),rs.getString("skrining_gizi_diagnosa_khusus"),rs.getString("skrining_gizi_ket_diagnosa_khusus"),
                        rs.getString("skrining_gizi_diketahui_dietisen"),rs.getString("skrining_gizi_jam_diketahui_dietisen"),rs.getString("masalah"),rs.getString("rencana")
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
        TibadiRuang.setSelectedIndex(0);
        CaraMasuk.setSelectedIndex(0);
        RPS.setText("");
        RPD.setText("");
        RPK.setText("");
        RPO.setText("");
        Alergi.setText("");
        
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
            KdPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            NmPetugas2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            TibadiRuang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            CaraMasuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            RPS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString()); 
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString()); 
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString()); 
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()); 
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,pasien.pnd,pasien.pekerjaan,pasien.gol_darah "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    /*Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    PendidikanPasien.setText(rs.getString("pnd"));
                    PekerjaanPasien.setText(rs.getString("pekerjaan"));
                    GD.setText(rs.getString("gol_darah"));*/
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
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        //CaraBayar.setText(carabayar);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NmPetugas,KdPetugas.getText());
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
            tampil();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        
    }
}

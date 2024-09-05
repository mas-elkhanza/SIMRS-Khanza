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
import java.awt.event.KeyListener;
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
public final class RMPenilaianAwalKeperawatanRanapBayiAnak extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeImunisasi,tabModeDetailMasalah,tabModeRencana,tabModeDetailRencana;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
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
    private String TANGGALMUNDUR="yes";
    private MasterCariImunisasi imunisasi=new MasterCariImunisasi(null,false);
    private boolean ke1=false,ke2=false,ke3=false,ke4=false,ke5=false,ke6=false;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanRanapBayiAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatImunisasi.setSize(465,112);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP Pengkaji 1","Nama Pengkaji 1","NIP Pengkaji 2","Nama Pengkaji 2","Kode DPJP","Nama DPJP",
            "Tgl.Asuhan","Anamnesa","Keterangan Anamnesa","Kondisi Tiba Di Ruang Rawat","Riwayat Penyakit Saat Ini","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga",
            "Riwayat Penggunaan Obat","Riwayat Alergi","Tengkurap","Duduk","Berdiri","Gigi Pertama","Berjalan","Bicara","Membaca","Menulis","Gangguan Emosi",
            "Anak Ke","Bersaudara","Cara Kelahiran","Keterangan Cara Kelahiran","Umur Kelahiran","Kelainan Bawaan","Keterangan Kelainan Bawaan","BB Lahir","PP Lahir",
            "Riwayat Persalinan Lainnya","Kesadaran","GCS","TD(mmHg)","RR(x/menit)","Suhu(°C)","Nadi(x/menit)","BB(Kg)","TB(cm)","LP(cm)","LK(cm)","LD(cm)",
            "Saraf Pusat Kepala","Keterangan Saraf Pusat Kepala","Saraf Pusat Wajah","Keterangan Saraf Pusat Wajah","Saraf Pusat Leher","Saraf Pusat Kejang",
            "Keterangan Saraf Pusat Kejang","Saraf Pusat Sensorik","Kardi Pulsasi","Kardio Sirkulasi","Keterangan Kardio Sirkulasi","Kardio Denyut Nadi","Respi Retraksi",
            "Respi Pola Nafas","Respi Suara Nafas","Respi Batuk","Respi Volume","Respi Jenis Pernapasan","Keterangan Respi Jenis Pernapasan","Respirasi Irama",
            "Gastro Mulut","Keterangan Gastro Mulut","Gastro Tenggorakan","Keterangan Gastro Tenggorakan","Gastro Lidah","Keterangan Gastro Lidah","Gastro Abdomen",
            "Keterangan Gastro Abdomen","Gastro Gigi","Keterangan Gastro Gigi","Gastro Usus","Gastro Anus","Neuro Sensorik","Neuro Pengelihatan","Keterangan Neuro Pengelihatan",
            "Neuro Alat Bantu","Neuro Motorik","Neuro Pendengaran","Neuro Bicara","Keterangan Neuro Bicara","Neurologi Otot","Inte Kulit","Inte Warna Kulit","Inte Tugor",
            "Inte Decubi","Musku Odema","Keterangan Musku Odema","Musku Pegerakan Sendi","Musku Otot","Musku Fraktur","Keterangan Musku Fraktur","Musku Nyeri Sendi",
            "Keterangan Musku Nyeri Sendi","BAB Frekuensi","BAB Per","BAB Konsistesi","BAB Warna","BAK Frekuensi","BAK Per","BAK Warna","BAK Lain-lain",
            "Kondisi Psikologi","Adakah Perilaku","Keterangan Perilaku","Gangguan Jiwa Masa Lalu","Hubungan Dengan Keluarga","Agama Pasien","Tinggal Dengan",
            "Keterangan Tinggal Dengan","Pekerjaan P.J.","Pembayaran Pasien","Nilai Kepercayaan","Keterangan Nilai Kepercayaan","Bahasa Sehari-hari Pasien",
            "Pend.Pasien","Pend.P.J.","Edukasi Diberikan","Keterangan Edukasi Diberikan","Bahasa Sehari-hari Pendamping","Kemampuan Baca & Tulis Pendamping",
            "Pendamping Butuh Penerjamah","Keterangan Pendamping Butuh Penerjamah","Terdapat Hambatan","Hambatan Belajar","Keterangan Hambatan Belajar",
            "Hambatan Bicara","Hambatan Bahasa Isyarat","Cara Belajar Disukai","Menerima Informasi","Keterangan Kesediaan Menerima Informasi","Pemahaman Tentang Nutrisi/Diet",
            "Pemahaman Tentang Penyakit","Pemahaman Tentang Pengobatan","Pemahaman Tentang Perawatan","Skrining Gizi 1","N.G.1","Skrining Gizi 2","N.G.2",
            "Skrining Gizi 3","N.G.3","Skrining Gizi 4","N.G.4","T.N.Gizi","Keterangan Skrining Gizi","Skala Humpty Dumpty 1","N.H. 1","Skala Humpty Dumpty 2","N.H. 2",
            "Skala Humpty Dumpty 3","N.H. 3","Skala Humpty Dumpty 4","N.H. 4","Skala Humpty Dumpty 5","N.H. 5","Skala Humpty Dumpty 6","N.H. 6","Skala Humpty Dumpty 7","N.H. 7",
            "Total H.D.","Hasil Skrining Penilaian Humpty Dumpty","Nyeri Wajah","N.Nyeri Wajah","Nyeri Kaki","N.Nyeri Kaki","Nyeri Aktifitas","N.Nyeri Aktifitas",
            "Nyeri Menangis","N.Nyeri Menangis","Nyeri Bersuara","N.Nyeri Bersuara","Total Nilai Nyeri","Kondisi Nyeri","Lokasi Nyeri","Durasi Nyeri","Frekuensi Nyeri",
            "Nyeri Hilang Bila","Keterangan Nyeri Hilang","Nyeri Diberitahukan Dokter","Jam Nyeri Diberitahukan","Informasi Perencanaan Pulang","Lama Rawat Rata-rata",
            "Perencanaan Pulang","Kondisi Klinis Saat Pulang","Perawatan Lanjutan Yang Diberikan Di Rumah","Cara Transportasi Pulang","Transportasi Yang Digunakan",
            "Rencana Keperawatan Lainnya"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 100; i++) {
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
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeImunisasi=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Imunisasi","Ke 1","Ke 2","Ke 3","Ke 4","Ke 5","Ke 6"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbImunisasi.setModel(tabModeImunisasi);
        tbImunisasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbImunisasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 8; i++) {
            TableColumn column = tbImunisasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else{
                column.setPreferredWidth(50);
            }
        }
        tbImunisasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KetAnamnesis.setDocument(new batasInput((byte)30).getKata(KetAnamnesis));
        RPS.setDocument(new batasInput((int)300).getKata(RPS));
        RPD.setDocument(new batasInput((int)150).getKata(RPD));
        RPK.setDocument(new batasInput((int)150).getKata(RPK));
        RPO.setDocument(new batasInput((int)150).getKata(RPO));
        Alergi.setDocument(new batasInput((int)40).getKata(Alergi));
        UsiaTengkurap.setDocument(new batasInput((int)15).getKata(UsiaTengkurap));
        UsiaDuduk.setDocument(new batasInput((int)15).getKata(UsiaDuduk));
        UsiaBerdiri.setDocument(new batasInput((int)15).getKata(UsiaBerdiri));
        UsiaGigi.setDocument(new batasInput((int)15).getKata(UsiaGigi));
        UsiaBerjalan.setDocument(new batasInput((int)15).getKata(UsiaBerjalan));
        UsiaBicara.setDocument(new batasInput((int)15).getKata(UsiaBicara));
        UsiaMembaca.setDocument(new batasInput((int)15).getKata(UsiaMembaca));
        UsiaMenulis.setDocument(new batasInput((int)15).getKata(UsiaMenulis));
        GangguanEmosi.setDocument(new batasInput((int)30).getKata(GangguanEmosi));
        Anakke.setDocument(new batasInput((int)2).getKata(Anakke));
        DariSaudara.setDocument(new batasInput((int)2).getKata(DariSaudara));
        KetCaraKelahiran.setDocument(new batasInput((int)30).getKata(KetCaraKelahiran));
        KetKelainanBawaan.setDocument(new batasInput((int)30).getKata(KetKelainanBawaan));
        BBLahir.setDocument(new batasInput((int)5).getKata(BBLahir));
        PBLahir.setDocument(new batasInput((int)5).getKata(PBLahir));
        RiwayatPersalinanLainnya.setDocument(new batasInput((int)100).getKata(RiwayatPersalinanLainnya));
        GCS.setDocument(new batasInput((int)6).getKata(GCS));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        BB.setDocument(new batasInput((int)5).getKata(BB));
        TB.setDocument(new batasInput((int)5).getKata(TB));
        LP.setDocument(new batasInput((int)5).getKata(LP));
        LK.setDocument(new batasInput((int)5).getKata(LK));
        LD.setDocument(new batasInput((int)5).getKata(LD));
        KetSistemSarafKepala.setDocument(new batasInput((int)30).getKata(KetSistemSarafKepala));
        KetSistemSarafWajah.setDocument(new batasInput((int)30).getKata(KetSistemSarafWajah));
        KetSistemSarafKejang.setDocument(new batasInput((int)30).getKata(KetSistemSarafKejang));
        KetKardiovaskularSirkulasi.setDocument(new batasInput((int)30).getKata(KetKardiovaskularSirkulasi));
        KetRespirasiJenisPernafasan.setDocument(new batasInput((int)30).getKata(KetRespirasiJenisPernafasan));
        KetGastrointestinalMulut.setDocument(new batasInput((int)30).getKata(KetGastrointestinalMulut));
        KetGastrointestinalTenggorakan.setDocument(new batasInput((int)30).getKata(KetGastrointestinalTenggorakan));
        KetGastrointestinalLidah.setDocument(new batasInput((int)30).getKata(KetGastrointestinalLidah));
        KetGastrointestinalAbdomen.setDocument(new batasInput((int)30).getKata(KetGastrointestinalAbdomen));
        KetGastrointestinalGigi.setDocument(new batasInput((int)30).getKata(KetGastrointestinalGigi));
        KetNeurologiPenglihatan.setDocument(new batasInput((int)30).getKata(KetNeurologiPenglihatan));
        KetNeurologiBicara.setDocument(new batasInput((int)30).getKata(KetNeurologiBicara));
        KetMuskuloskletalOedema.setDocument(new batasInput((int)30).getKata(KetMuskuloskletalOedema));
        KetMuskuloskletalFraktur.setDocument(new batasInput((int)30).getKata(KetMuskuloskletalFraktur));
        KetMuskuloskletalNyeriSendi.setDocument(new batasInput((int)30).getKata(KetMuskuloskletalNyeriSendi));
        BAB.setDocument(new batasInput((int)3).getKata(BAB));
        XBAB.setDocument(new batasInput((int)10).getKata(XBAB));
        KBAB.setDocument(new batasInput((int)20).getKata(KBAB));
        WBAB.setDocument(new batasInput((int)20).getKata(WBAB));
        BAK.setDocument(new batasInput((int)3).getKata(BAK));
        XBAK.setDocument(new batasInput((int)10).getKata(XBAK));
        WBAK.setDocument(new batasInput((int)20).getKata(WBAK));
        LBAK.setDocument(new batasInput((int)20).getKata(LBAK));
        KeteranganAdakahPerilaku.setDocument(new batasInput((int)30).getKata(KeteranganAdakahPerilaku));
        KeteranganTinggalDengan.setDocument(new batasInput((int)30).getKata(KeteranganTinggalDengan));
        PekerjaanPJ.setDocument(new batasInput((int)30).getKata(PekerjaanPJ));
        KeteranganNilaiKepercayaan.setDocument(new batasInput((int)30).getKata(KeteranganNilaiKepercayaan));
        KeteranganEdukasiPsikologis.setDocument(new batasInput((int)30).getKata(KeteranganEdukasiPsikologis));
        KeteranganButuhPenerjemah.setDocument(new batasInput((int)30).getKata(KeteranganButuhPenerjemah));
        KeteranganHambatanBelajar.setDocument(new batasInput((int)30).getKata(KeteranganHambatanBelajar));
        KeteranganKesediaanMenerimaInformasi.setDocument(new batasInput((int)30).getKata(KeteranganKesediaanMenerimaInformasi));
        KeteranganSkriningGizi.setDocument(new batasInput((int)50).getKata(KeteranganSkriningGizi));
        KeteranganTingkatRisiko.setDocument(new batasInput((int)50).getKata(KeteranganTingkatRisiko));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        Frekuensi.setDocument(new batasInput((int)25).getKata(Frekuensi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        NyeriKetDokter.setDocument(new batasInput((int)15).getKata(NyeriKetDokter));
        LamaRatarata.setDocument(new batasInput((int)3).getKata(LamaRatarata));
        KondisiPulang.setDocument(new batasInput((int)100).getKata(KondisiPulang));
        PerawatanLanjutan.setDocument(new batasInput((int)300).getKata(PerawatanLanjutan));
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
        
        imunisasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(imunisasi.getTable().getSelectedRow()!= -1){ 
                    KdImunisasi.setText(imunisasi.getTable().getValueAt(imunisasi.getTable().getSelectedRow(),0).toString());
                    NmImunisasi.setText(imunisasi.getTable().getValueAt(imunisasi.getTable().getSelectedRow(),1).toString());   
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
        
        imunisasi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    imunisasi.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
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

        TanggalRegistrasi = new widget.TextBox();
        DlgRiwayatImunisasi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarImunisasi = new widget.Button();
        BtnSimpanImunisasi = new widget.Button();
        BtnImunisasi = new widget.Button();
        NmImunisasi = new widget.TextBox();
        KdImunisasi = new widget.TextBox();
        jLabel85 = new widget.Label();
        ImunisasiKe = new widget.ComboBox();
        BtnHapusImunisasi = new widget.Button();
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
        jLabel94 = new widget.Label();
        jLabel9 = new widget.Label();
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
        KetAnamnesis = new widget.TextBox();
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
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel38 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel33 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel96 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbImunisasi = new widget.Table();
        BtnTambahImunisasi = new widget.Button();
        BtnPanggilHapusImunisasi = new widget.Button();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel97 = new widget.Label();
        UsiaTengkurap = new widget.TextBox();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        UsiaDuduk = new widget.TextBox();
        jLabel61 = new widget.Label();
        UsiaBerdiri = new widget.TextBox();
        jLabel62 = new widget.Label();
        UsiaGigi = new widget.TextBox();
        jLabel63 = new widget.Label();
        jLabel66 = new widget.Label();
        UsiaBerjalan = new widget.TextBox();
        jLabel67 = new widget.Label();
        UsiaBicara = new widget.TextBox();
        jLabel68 = new widget.Label();
        UsiaMembaca = new widget.TextBox();
        jLabel69 = new widget.Label();
        UsiaMenulis = new widget.TextBox();
        jLabel70 = new widget.Label();
        GangguanEmosi = new widget.TextBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel44 = new widget.Label();
        Anakke = new widget.TextBox();
        DariSaudara = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel55 = new widget.Label();
        CaraKelahiran = new widget.ComboBox();
        KetCaraKelahiran = new widget.TextBox();
        jLabel57 = new widget.Label();
        KelainanBawaan = new widget.ComboBox();
        jLabel56 = new widget.Label();
        KetKelainanBawaan = new widget.TextBox();
        UmurKelahiran = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel35 = new widget.Label();
        BBLahir = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel27 = new widget.Label();
        PBLahir = new widget.TextBox();
        jLabel24 = new widget.Label();
        RiwayatPersalinanLainnya = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel98 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
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
        jLabel29 = new widget.Label();
        GCS = new widget.TextBox();
        jLabel40 = new widget.Label();
        TB = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        LK = new widget.TextBox();
        jLabel43 = new widget.Label();
        BB = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        LP = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        LD = new widget.TextBox();
        jLabel52 = new widget.Label();
        jLabel202 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel80 = new widget.Label();
        jLabel203 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel131 = new widget.Label();
        SistemSarafKepala = new widget.ComboBox();
        KetSistemSarafKepala = new widget.TextBox();
        jLabel132 = new widget.Label();
        SistemSarafWajah = new widget.ComboBox();
        KetSistemSarafWajah = new widget.TextBox();
        jLabel133 = new widget.Label();
        SistemSarafLeher = new widget.ComboBox();
        jLabel135 = new widget.Label();
        SistemSarafKejang = new widget.ComboBox();
        KetSistemSarafKejang = new widget.TextBox();
        jLabel134 = new widget.Label();
        SistemSarafSensorik = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel136 = new widget.Label();
        KardiovaskularPulsasi = new widget.ComboBox();
        jLabel137 = new widget.Label();
        KardiovaskularSirkulasi = new widget.ComboBox();
        KetKardiovaskularSirkulasi = new widget.TextBox();
        jLabel138 = new widget.Label();
        KardiovaskularDenyutNadi = new widget.ComboBox();
        jLabel76 = new widget.Label();
        jLabel139 = new widget.Label();
        RespirasiRetraksi = new widget.ComboBox();
        jLabel140 = new widget.Label();
        RespirasiPolaNafas = new widget.ComboBox();
        jLabel141 = new widget.Label();
        RespirasiSuaraNafas = new widget.ComboBox();
        jLabel145 = new widget.Label();
        RespirasiBatuk = new widget.ComboBox();
        jLabel143 = new widget.Label();
        RespirasiVolume = new widget.ComboBox();
        jLabel144 = new widget.Label();
        RespirasiJenisPernafasan = new widget.ComboBox();
        KetRespirasiJenisPernafasan = new widget.TextBox();
        jLabel142 = new widget.Label();
        RespirasiIrama = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel146 = new widget.Label();
        GastrointestinalMulut = new widget.ComboBox();
        KetGastrointestinalMulut = new widget.TextBox();
        jLabel150 = new widget.Label();
        GastrointestinalTenggorakan = new widget.ComboBox();
        KetGastrointestinalTenggorakan = new widget.TextBox();
        jLabel147 = new widget.Label();
        GastrointestinalLidah = new widget.ComboBox();
        KetGastrointestinalLidah = new widget.TextBox();
        jLabel151 = new widget.Label();
        GastrointestinalAbdomen = new widget.ComboBox();
        KetGastrointestinalAbdomen = new widget.TextBox();
        jLabel148 = new widget.Label();
        GastrointestinalGigi = new widget.ComboBox();
        KetGastrointestinalGigi = new widget.TextBox();
        jLabel152 = new widget.Label();
        GastrointestinalUsus = new widget.ComboBox();
        jLabel149 = new widget.Label();
        GastrointestinalAnus = new widget.ComboBox();
        jLabel78 = new widget.Label();
        jLabel153 = new widget.Label();
        NeurologiSensorik = new widget.ComboBox();
        jLabel156 = new widget.Label();
        NeurologiPenglihatan = new widget.ComboBox();
        KetNeurologiPenglihatan = new widget.TextBox();
        jLabel157 = new widget.Label();
        NeurologiAlatBantuPenglihatan = new widget.ComboBox();
        jLabel154 = new widget.Label();
        NeurologiMotorik = new widget.ComboBox();
        jLabel158 = new widget.Label();
        NeurologiPendengaran = new widget.ComboBox();
        jLabel159 = new widget.Label();
        NeurologiBicara = new widget.ComboBox();
        KetNeurologiBicara = new widget.TextBox();
        jLabel155 = new widget.Label();
        NeurologiOtot = new widget.ComboBox();
        jLabel79 = new widget.Label();
        jLabel160 = new widget.Label();
        IntegumentKulit = new widget.ComboBox();
        jLabel161 = new widget.Label();
        IntegumentWarnaKulit = new widget.ComboBox();
        jLabel162 = new widget.Label();
        IntegumentTurgor = new widget.ComboBox();
        jLabel163 = new widget.Label();
        IntegumentDecubitus = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel164 = new widget.Label();
        MuskuloskletalOedema = new widget.ComboBox();
        KetMuskuloskletalOedema = new widget.TextBox();
        jLabel167 = new widget.Label();
        MuskuloskletalPegerakanSendi = new widget.ComboBox();
        jLabel168 = new widget.Label();
        MuskuloskletalKekuatanOtot = new widget.ComboBox();
        jLabel165 = new widget.Label();
        MuskuloskletalFraktur = new widget.ComboBox();
        KetMuskuloskletalFraktur = new widget.TextBox();
        jLabel166 = new widget.Label();
        MuskuloskletalNyeriSendi = new widget.ComboBox();
        KetMuskuloskletalNyeriSendi = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel115 = new widget.Label();
        BAB = new widget.TextBox();
        jLabel113 = new widget.Label();
        XBAB = new widget.TextBox();
        jLabel114 = new widget.Label();
        KBAB = new widget.TextBox();
        jLabel116 = new widget.Label();
        WBAB = new widget.TextBox();
        jLabel117 = new widget.Label();
        BAK = new widget.TextBox();
        jLabel118 = new widget.Label();
        XBAK = new widget.TextBox();
        jLabel119 = new widget.Label();
        WBAK = new widget.TextBox();
        jLabel120 = new widget.Label();
        LBAK = new widget.TextBox();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel189 = new widget.Label();
        jLabel190 = new widget.Label();
        KondisiPsikologis = new widget.ComboBox();
        jLabel191 = new widget.Label();
        AdakahPerilaku = new widget.ComboBox();
        KeteranganAdakahPerilaku = new widget.TextBox();
        jLabel284 = new widget.Label();
        jLabel192 = new widget.Label();
        GangguanJiwa = new widget.ComboBox();
        jLabel193 = new widget.Label();
        HubunganAnggotaKeluarga = new widget.ComboBox();
        jLabel194 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel285 = new widget.Label();
        jLabel195 = new widget.Label();
        TinggalDengan = new widget.ComboBox();
        KeteranganTinggalDengan = new widget.TextBox();
        jLabel196 = new widget.Label();
        PekerjaanPJ = new widget.TextBox();
        jLabel197 = new widget.Label();
        CaraBayar = new widget.TextBox();
        jLabel286 = new widget.Label();
        jLabel198 = new widget.Label();
        NilaiKepercayaan = new widget.ComboBox();
        KeteranganNilaiKepercayaan = new widget.TextBox();
        jLabel199 = new widget.Label();
        Bahasa = new widget.TextBox();
        jLabel287 = new widget.Label();
        jLabel200 = new widget.Label();
        PendidikanPasien = new widget.TextBox();
        jLabel201 = new widget.Label();
        PendidikanPJ = new widget.ComboBox();
        jLabel204 = new widget.Label();
        EdukasiPsikolgis = new widget.ComboBox();
        KeteranganEdukasiPsikologis = new widget.TextBox();
        jLabel288 = new widget.Label();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel214 = new widget.Label();
        jLabel215 = new widget.Label();
        jLabel216 = new widget.Label();
        BahasaSehari = new widget.TextBox();
        jLabel217 = new widget.Label();
        KemampuanBacaTulis = new widget.ComboBox();
        jLabel218 = new widget.Label();
        ButuhPenerjemah = new widget.ComboBox();
        jLabel219 = new widget.Label();
        KeteranganButuhPenerjemah = new widget.TextBox();
        jLabel225 = new widget.Label();
        jLabel220 = new widget.Label();
        jLabel221 = new widget.Label();
        TerdapatHambatanBelajar = new widget.ComboBox();
        jLabel222 = new widget.Label();
        HambatanBelajar = new widget.ComboBox();
        KeteranganHambatanBelajar = new widget.TextBox();
        jLabel224 = new widget.Label();
        jLabel223 = new widget.Label();
        HambatanCaraBicara = new widget.ComboBox();
        HambatanBahasaIsyarat = new widget.ComboBox();
        jLabel226 = new widget.Label();
        jLabel227 = new widget.Label();
        CaraBelajarDisukai = new widget.ComboBox();
        jLabel228 = new widget.Label();
        jLabel229 = new widget.Label();
        KesediaanMenerimaInformasi = new widget.ComboBox();
        KeteranganKesediaanMenerimaInformasi = new widget.TextBox();
        jLabel230 = new widget.Label();
        jLabel231 = new widget.Label();
        PemahamanNutrisi = new widget.ComboBox();
        PemahamanPenyakit = new widget.ComboBox();
        jLabel232 = new widget.Label();
        jLabel233 = new widget.Label();
        jLabel234 = new widget.Label();
        PemahamanPerawatan = new widget.ComboBox();
        PemahamanPengobatan = new widget.ComboBox();
        jLabel235 = new widget.Label();
        jLabel169 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        SG1 = new widget.ComboBox();
        jLabel170 = new widget.Label();
        NilaiGizi1 = new widget.TextBox();
        SG2 = new widget.ComboBox();
        jLabel171 = new widget.Label();
        jLabel172 = new widget.Label();
        jLabel173 = new widget.Label();
        NilaiGizi2 = new widget.TextBox();
        jLabel174 = new widget.Label();
        jLabel175 = new widget.Label();
        jLabel176 = new widget.Label();
        SG3 = new widget.ComboBox();
        NilaiGizi3 = new widget.TextBox();
        jLabel177 = new widget.Label();
        jLabel178 = new widget.Label();
        SG4 = new widget.ComboBox();
        NilaiGizi4 = new widget.TextBox();
        jLabel179 = new widget.Label();
        jLabel180 = new widget.Label();
        TotalNilaiGizi = new widget.TextBox();
        jLabel240 = new widget.Label();
        KeteranganSkriningGizi = new widget.TextBox();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel242 = new widget.Label();
        jLabel243 = new widget.Label();
        SkalaResiko1 = new widget.ComboBox();
        NilaiResiko1 = new widget.TextBox();
        jLabel244 = new widget.Label();
        SkalaResiko2 = new widget.ComboBox();
        NilaiResiko2 = new widget.TextBox();
        jLabel245 = new widget.Label();
        SkalaResiko3 = new widget.ComboBox();
        NilaiResiko3 = new widget.TextBox();
        jLabel246 = new widget.Label();
        SkalaResiko4 = new widget.ComboBox();
        NilaiResiko4 = new widget.TextBox();
        jLabel247 = new widget.Label();
        SkalaResiko5 = new widget.ComboBox();
        NilaiResiko5 = new widget.TextBox();
        jLabel248 = new widget.Label();
        SkalaResiko6 = new widget.ComboBox();
        NilaiResiko6 = new widget.TextBox();
        jLabel249 = new widget.Label();
        SkalaResiko7 = new widget.ComboBox();
        NilaiResiko7 = new widget.TextBox();
        TingkatResiko1 = new widget.Label();
        KeteranganTingkatRisiko = new widget.TextBox();
        jLabel256 = new widget.Label();
        NilaiResikoTotal = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel181 = new widget.Label();
        jLabel182 = new widget.Label();
        jLabel183 = new widget.Label();
        SkalaWajah = new widget.ComboBox();
        NilaiWajah = new widget.TextBox();
        jLabel184 = new widget.Label();
        SkalaMenangis = new widget.ComboBox();
        NilaiMenangis = new widget.TextBox();
        jLabel185 = new widget.Label();
        SkalaKaki = new widget.ComboBox();
        NilaiKaki = new widget.TextBox();
        jLabel186 = new widget.Label();
        SkalaBersuara = new widget.ComboBox();
        NilaiBersuara = new widget.TextBox();
        jLabel187 = new widget.Label();
        SkalaAktifitas = new widget.ComboBox();
        NilaiAktifitas = new widget.TextBox();
        jLabel188 = new widget.Label();
        SkalaNyeri = new widget.TextBox();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator13 = new javax.swing.JSeparator();
        Nyeri = new widget.ComboBox();
        jLabel83 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel88 = new widget.Label();
        Frekuensi = new widget.TextBox();
        Durasi = new widget.TextBox();
        jLabel89 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel86 = new widget.Label();
        NyeriPadaDokter = new widget.ComboBox();
        jLabel84 = new widget.Label();
        jLabel87 = new widget.Label();
        NyeriKetDokter = new widget.TextBox();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel272 = new widget.Label();
        InformasiPerencanaanPulang = new widget.ComboBox();
        jLabel250 = new widget.Label();
        jLabel257 = new widget.Label();
        label29 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        jLabel261 = new widget.Label();
        LamaRatarata = new widget.TextBox();
        jLabel260 = new widget.Label();
        KondisiPulang = new widget.TextBox();
        jLabel259 = new widget.Label();
        jLabel262 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        PerawatanLanjutan = new widget.TextArea();
        jLabel263 = new widget.Label();
        CaraTransportasiPulang = new widget.ComboBox();
        jLabel264 = new widget.Label();
        jLabel265 = new widget.Label();
        jLabel266 = new widget.Label();
        TransportasiYangDigunakan = new widget.ComboBox();
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

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        DlgRiwayatImunisasi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRiwayatImunisasi.setName("DlgRiwayatImunisasi"); // NOI18N
        DlgRiwayatImunisasi.setUndecorated(true);
        DlgRiwayatImunisasi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Riwayat Imunisasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Imunisasi :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 13, 67, 23);

        BtnKeluarImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarImunisasi.setMnemonic('U');
        BtnKeluarImunisasi.setText("Tutup");
        BtnKeluarImunisasi.setToolTipText("Alt+U");
        BtnKeluarImunisasi.setName("BtnKeluarImunisasi"); // NOI18N
        BtnKeluarImunisasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarImunisasiActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarImunisasi);
        BtnKeluarImunisasi.setBounds(340, 50, 100, 30);

        BtnSimpanImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanImunisasi.setMnemonic('S');
        BtnSimpanImunisasi.setText("Simpan");
        BtnSimpanImunisasi.setToolTipText("Alt+S");
        BtnSimpanImunisasi.setName("BtnSimpanImunisasi"); // NOI18N
        BtnSimpanImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanImunisasiActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanImunisasi);
        BtnSimpanImunisasi.setBounds(10, 50, 100, 30);

        BtnImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnImunisasi.setMnemonic('2');
        BtnImunisasi.setToolTipText("Alt+2");
        BtnImunisasi.setName("BtnImunisasi"); // NOI18N
        BtnImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImunisasiActionPerformed(evt);
            }
        });
        BtnImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnImunisasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnImunisasi);
        BtnImunisasi.setBounds(307, 13, 28, 23);

        NmImunisasi.setEditable(false);
        NmImunisasi.setName("NmImunisasi"); // NOI18N
        NmImunisasi.setPreferredSize(new java.awt.Dimension(207, 23));
        panelBiasa2.add(NmImunisasi);
        NmImunisasi.setBounds(124, 13, 180, 23);

        KdImunisasi.setEditable(false);
        KdImunisasi.setName("KdImunisasi"); // NOI18N
        KdImunisasi.setPreferredSize(new java.awt.Dimension(80, 23));
        KdImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdImunisasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(KdImunisasi);
        KdImunisasi.setBounds(71, 13, 50, 23);

        jLabel85.setText("Ke :");
        jLabel85.setName("jLabel85"); // NOI18N
        panelBiasa2.add(jLabel85);
        jLabel85.setBounds(343, 13, 30, 23);

        ImunisasiKe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        ImunisasiKe.setName("ImunisasiKe"); // NOI18N
        ImunisasiKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImunisasiKeKeyPressed(evt);
            }
        });
        panelBiasa2.add(ImunisasiKe);
        ImunisasiKe.setBounds(377, 13, 60, 23);

        BtnHapusImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusImunisasi.setMnemonic('H');
        BtnHapusImunisasi.setText("Hapus");
        BtnHapusImunisasi.setToolTipText("Alt+H");
        BtnHapusImunisasi.setName("BtnHapusImunisasi"); // NOI18N
        BtnHapusImunisasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusImunisasiActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnHapusImunisasi);
        BtnHapusImunisasi.setBounds(230, 50, 100, 30);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgRiwayatImunisasi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Inap Bayi & Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 2688));
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
        label11.setBounds(514, 100, 70, 23);

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
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2024 09:55:16" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(588, 100, 135, 23);

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
        KdDPJP.setBounds(74, 70, 130, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setName("NmDPJP"); // NOI18N
        NmDPJP.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(206, 70, 260, 23);

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
        BtnDPJP.setBounds(468, 70, 28, 23);

        TibadiRuang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan Tanpa Bantuan", "Kursi Roda", "Brankar" }));
        TibadiRuang.setName("TibadiRuang"); // NOI18N
        TibadiRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TibadiRuangKeyPressed(evt);
            }
        });
        FormInput.add(TibadiRuang);
        TibadiRuang.setBounds(689, 70, 165, 23);

        jLabel37.setText("Kondisi Tiba Di Ruang Rawat :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(525, 70, 160, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("I. RIWAYAT KESEHATAN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 130, 180, 23);

        jLabel9.setText("Riwayat Penggunaan Obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(450, 200, 150, 23);

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
        scrollPane1.setBounds(179, 150, 270, 43);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Riwayat Penyakit Saat Ini");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(44, 150, 175, 23);

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

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Riwayat Penyakit Keluarga");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(44, 200, 180, 23);

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
        jLabel32.setBounds(460, 150, 140, 23);

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

        KetAnamnesis.setFocusTraversalPolicyProvider(true);
        KetAnamnesis.setName("KetAnamnesis"); // NOI18N
        KetAnamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(KetAnamnesis);
        KetAnamnesis.setBounds(208, 100, 258, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 2495, 880, 1);

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
        Scroll6.setBounds(10, 2505, 400, 143);

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
        TabRencanaKeperawatan.setBounds(433, 2505, 420, 143);

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
        BtnTambahMasalah.setBounds(363, 2655, 28, 23);

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
        BtnAllMasalah.setBounds(331, 2655, 28, 23);

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
        BtnCariMasalah.setBounds(299, 2655, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 2655, 215, 23);

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
        BtnTambahRencana.setBounds(806, 2655, 28, 23);

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
        BtnAllRencana.setBounds(774, 2655, 28, 23);

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
        BtnCariRencana.setBounds(742, 2655, 28, 23);

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label13);
        label13.setBounds(439, 2655, 60, 23);

        TCariRencana.setToolTipText("Alt+C");
        TCariRencana.setName("TCariRencana"); // NOI18N
        TCariRencana.setPreferredSize(new java.awt.Dimension(215, 23));
        TCariRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariRencanaKeyPressed(evt);
            }
        });
        FormInput.add(TCariRencana);
        TCariRencana.setBounds(503, 2655, 235, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(16, 2655, 60, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 200, 180, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 150, 175, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 250, 122, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(126, 250, 323, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Riwayat Alergi");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 250, 110, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 280, 880, 1);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("II. RIWAYAT IMUNISASI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 280, 350, 23);

        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbImunisasi.setName("tbImunisasi"); // NOI18N
        Scroll10.setViewportView(tbImunisasi);

        FormInput.add(Scroll10);
        Scroll10.setBounds(76, 300, 778, 93);

        BtnTambahImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahImunisasi.setMnemonic('3');
        BtnTambahImunisasi.setToolTipText("Alt+3");
        BtnTambahImunisasi.setName("BtnTambahImunisasi"); // NOI18N
        BtnTambahImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahImunisasiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahImunisasi);
        BtnTambahImunisasi.setBounds(44, 300, 28, 23);

        BtnPanggilHapusImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnPanggilHapusImunisasi.setMnemonic('3');
        BtnPanggilHapusImunisasi.setToolTipText("Alt+3");
        BtnPanggilHapusImunisasi.setName("BtnPanggilHapusImunisasi"); // NOI18N
        BtnPanggilHapusImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPanggilHapusImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanggilHapusImunisasiActionPerformed(evt);
            }
        });
        FormInput.add(BtnPanggilHapusImunisasi);
        BtnPanggilHapusImunisasi.setBounds(44, 330, 28, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 400, 880, 1);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("III. RIWAYAT TUMBUH KEMBANG ANAK");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(10, 400, 350, 23);

        UsiaTengkurap.setFocusTraversalPolicyProvider(true);
        UsiaTengkurap.setName("UsiaTengkurap"); // NOI18N
        UsiaTengkurap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaTengkurapKeyPressed(evt);
            }
        });
        FormInput.add(UsiaTengkurap);
        UsiaTengkurap.setBounds(145, 420, 90, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("a. Tengkurap, Usia");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(44, 420, 133, 23);

        jLabel60.setText("b. Duduk, Usia :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(248, 420, 90, 23);

        UsiaDuduk.setFocusTraversalPolicyProvider(true);
        UsiaDuduk.setName("UsiaDuduk"); // NOI18N
        UsiaDuduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaDudukKeyPressed(evt);
            }
        });
        FormInput.add(UsiaDuduk);
        UsiaDuduk.setBounds(342, 420, 90, 23);

        jLabel61.setText("c. Berdiri, Usia :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(445, 420, 90, 23);

        UsiaBerdiri.setFocusTraversalPolicyProvider(true);
        UsiaBerdiri.setName("UsiaBerdiri"); // NOI18N
        UsiaBerdiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaBerdiriKeyPressed(evt);
            }
        });
        FormInput.add(UsiaBerdiri);
        UsiaBerdiri.setBounds(539, 420, 90, 23);

        jLabel62.setText("d. Gigi Pertama, Usia :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(630, 420, 130, 23);

        UsiaGigi.setFocusTraversalPolicyProvider(true);
        UsiaGigi.setName("UsiaGigi"); // NOI18N
        UsiaGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaGigiKeyPressed(evt);
            }
        });
        FormInput.add(UsiaGigi);
        UsiaGigi.setBounds(764, 420, 90, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 420, 141, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("e. Mulai Berjalan, Usia");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(44, 450, 122, 23);

        UsiaBerjalan.setFocusTraversalPolicyProvider(true);
        UsiaBerjalan.setName("UsiaBerjalan"); // NOI18N
        UsiaBerjalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaBerjalanKeyPressed(evt);
            }
        });
        FormInput.add(UsiaBerjalan);
        UsiaBerjalan.setBounds(163, 450, 90, 23);

        jLabel67.setText("f. Mulai Bisa Bicara, Usia :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(321, 450, 130, 23);

        UsiaBicara.setFocusTraversalPolicyProvider(true);
        UsiaBicara.setName("UsiaBicara"); // NOI18N
        UsiaBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaBicaraKeyPressed(evt);
            }
        });
        FormInput.add(UsiaBicara);
        UsiaBicara.setBounds(455, 450, 90, 23);

        jLabel68.setText("g. Mulai Bisa Membaca, Usia :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(580, 450, 180, 23);

        UsiaMembaca.setFocusTraversalPolicyProvider(true);
        UsiaMembaca.setName("UsiaMembaca"); // NOI18N
        UsiaMembaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaMembacaKeyPressed(evt);
            }
        });
        FormInput.add(UsiaMembaca);
        UsiaMembaca.setBounds(764, 450, 90, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 450, 159, 23);

        UsiaMenulis.setFocusTraversalPolicyProvider(true);
        UsiaMenulis.setName("UsiaMenulis"); // NOI18N
        UsiaMenulis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaMenulisKeyPressed(evt);
            }
        });
        FormInput.add(UsiaMenulis);
        UsiaMenulis.setBounds(183, 480, 90, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("h. Mulai Bisa Menulis, Usia");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(44, 480, 172, 23);

        GangguanEmosi.setFocusTraversalPolicyProvider(true);
        GangguanEmosi.setName("GangguanEmosi"); // NOI18N
        GangguanEmosi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanEmosiKeyPressed(evt);
            }
        });
        FormInput.add(GangguanEmosi);
        GangguanEmosi.setBounds(644, 480, 210, 23);

        jLabel71.setText("Gangguan Perkembangan Mental/Emosi, Bila Ada, Jelaskan :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(320, 480, 320, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 480, 179, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 510, 880, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("IV. RIWAYAT PERSALINAN");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 510, 350, 23);

        jLabel44.setText("Anak Ke :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(135, 530, 55, 23);

        Anakke.setFocusTraversalPolicyProvider(true);
        Anakke.setName("Anakke"); // NOI18N
        Anakke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnakkeKeyPressed(evt);
            }
        });
        FormInput.add(Anakke);
        Anakke.setBounds(194, 530, 40, 23);

        DariSaudara.setFocusTraversalPolicyProvider(true);
        DariSaudara.setName("DariSaudara"); // NOI18N
        DariSaudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DariSaudaraKeyPressed(evt);
            }
        });
        FormInput.add(DariSaudara);
        DariSaudara.setBounds(266, 530, 40, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Dari");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(235, 530, 30, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Bersaudara");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(312, 530, 80, 23);

        jLabel55.setText("Cara Kelahiran :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(380, 530, 110, 23);

        CaraKelahiran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Sectio Caesaria", "Lain-Lain" }));
        CaraKelahiran.setName("CaraKelahiran"); // NOI18N
        CaraKelahiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKelahiranKeyPressed(evt);
            }
        });
        FormInput.add(CaraKelahiran);
        CaraKelahiran.setBounds(494, 530, 130, 23);

        KetCaraKelahiran.setFocusTraversalPolicyProvider(true);
        KetCaraKelahiran.setName("KetCaraKelahiran"); // NOI18N
        KetCaraKelahiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetCaraKelahiranKeyPressed(evt);
            }
        });
        FormInput.add(KetCaraKelahiran);
        KetCaraKelahiran.setBounds(628, 530, 226, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Riwayat Kelahiran");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(44, 530, 146, 23);

        KelainanBawaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        KelainanBawaan.setName("KelainanBawaan"); // NOI18N
        KelainanBawaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelainanBawaanKeyPressed(evt);
            }
        });
        FormInput.add(KelainanBawaan);
        KelainanBawaan.setBounds(424, 560, 100, 23);

        jLabel56.setText("Kelainan Bawaan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(310, 560, 110, 23);

        KetKelainanBawaan.setFocusTraversalPolicyProvider(true);
        KetKelainanBawaan.setName("KetKelainanBawaan"); // NOI18N
        KetKelainanBawaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKelainanBawaanKeyPressed(evt);
            }
        });
        FormInput.add(KetKelainanBawaan);
        KetKelainanBawaan.setBounds(528, 560, 326, 23);

        UmurKelahiran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cukup Bulan", "Kurang Bulan" }));
        UmurKelahiran.setName("UmurKelahiran"); // NOI18N
        UmurKelahiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurKelahiranKeyPressed(evt);
            }
        });
        FormInput.add(UmurKelahiran);
        UmurKelahiran.setBounds(130, 560, 150, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Umur Kelahiran");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(44, 560, 146, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Berat Badan Lahir");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(44, 590, 110, 23);

        BBLahir.setFocusTraversalPolicyProvider(true);
        BBLahir.setName("BBLahir"); // NOI18N
        BBLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBLahirKeyPressed(evt);
            }
        });
        FormInput.add(BBLahir);
        BBLahir.setBounds(142, 590, 50, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Kg");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(195, 590, 50, 23);

        jLabel27.setText("Panjang Badan Lahir :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(223, 590, 120, 23);

        PBLahir.setFocusTraversalPolicyProvider(true);
        PBLahir.setName("PBLahir"); // NOI18N
        PBLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PBLahirKeyPressed(evt);
            }
        });
        FormInput.add(PBLahir);
        PBLahir.setBounds(347, 590, 50, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(400, 590, 50, 23);

        RiwayatPersalinanLainnya.setFocusTraversalPolicyProvider(true);
        RiwayatPersalinanLainnya.setName("RiwayatPersalinanLainnya"); // NOI18N
        RiwayatPersalinanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPersalinanLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPersalinanLainnya);
        RiwayatPersalinanLainnya.setBounds(494, 590, 360, 23);

        jLabel28.setText("Lainnya :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(390, 590, 100, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 530, 139, 23);

        jLabel74.setText(":");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 560, 126, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 590, 138, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("V. PEMERIKSAAN FISIK");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(10, 620, 180, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 620, 880, 1);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(124, 670, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(76, 670, 45, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Nadi");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(44, 670, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(750, 640, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(794, 640, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(423, 640, 40, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(467, 640, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(842, 640, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(530, 640, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(678, 640, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(630, 640, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(586, 640, 40, 23);

        jLabel29.setText("GCS(E,V,M) :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(250, 640, 90, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(344, 640, 60, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("cm");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(413, 670, 50, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(365, 670, 45, 23);

        jLabel41.setText("TB :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(321, 670, 40, 23);

        jLabel42.setText("LK :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(608, 670, 40, 23);

        LK.setFocusTraversalPolicyProvider(true);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        FormInput.add(LK);
        LK.setBounds(652, 670, 45, 23);

        jLabel43.setText("BB :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(162, 670, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(236, 670, 45, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("cm");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(842, 670, 30, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Kg");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(284, 670, 30, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("cm");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(555, 670, 50, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(507, 670, 45, 23);

        jLabel50.setText("LP :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(463, 670, 40, 23);

        jLabel51.setText("LD :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(750, 670, 40, 23);

        LD.setFocusTraversalPolicyProvider(true);
        LD.setName("LD"); // NOI18N
        LD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LDKeyPressed(evt);
            }
        });
        FormInput.add(LD);
        LD.setBounds(794, 670, 45, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("cm");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(700, 670, 30, 23);

        jLabel202.setText(":");
        jLabel202.setName("jLabel202"); // NOI18N
        FormInput.add(jLabel202);
        jLabel202.setBounds(0, 640, 102, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Soporcoma", "Coma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(106, 640, 125, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Kesadaran");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 640, 70, 23);

        jLabel203.setText(":");
        jLabel203.setName("jLabel203"); // NOI18N
        FormInput.add(jLabel203);
        jLabel203.setBounds(0, 670, 72, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Sistem Susunan Saraf Pusat :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(44, 700, 187, 23);

        jLabel131.setText("Kepala :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(0, 720, 109, 23);

        SistemSarafKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hydrocephalus", "Hematoma", "Lain-lain" }));
        SistemSarafKepala.setName("SistemSarafKepala"); // NOI18N
        SistemSarafKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafKepalaKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafKepala);
        SistemSarafKepala.setBounds(113, 720, 103, 23);

        KetSistemSarafKepala.setFocusTraversalPolicyProvider(true);
        KetSistemSarafKepala.setName("KetSistemSarafKepala"); // NOI18N
        KetSistemSarafKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafKepala);
        KetSistemSarafKepala.setBounds(220, 720, 184, 23);

        jLabel132.setText("Wajah :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(432, 720, 80, 23);

        SistemSarafWajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Asimetris", "Kelainan Kongenital" }));
        SistemSarafWajah.setName("SistemSarafWajah"); // NOI18N
        SistemSarafWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafWajahKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafWajah);
        SistemSarafWajah.setBounds(516, 720, 150, 23);

        KetSistemSarafWajah.setFocusTraversalPolicyProvider(true);
        KetSistemSarafWajah.setName("KetSistemSarafWajah"); // NOI18N
        KetSistemSarafWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafWajahKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafWajah);
        KetSistemSarafWajah.setBounds(670, 720, 184, 23);

        jLabel133.setText("Leher :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(0, 750, 109, 23);

        SistemSarafLeher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kaku Kuduk", "Pembesaran Thyroid", "Pembesaran KGB" }));
        SistemSarafLeher.setName("SistemSarafLeher"); // NOI18N
        SistemSarafLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafLeherKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafLeher);
        SistemSarafLeher.setBounds(113, 750, 155, 23);

        jLabel135.setText("Kejang :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(302, 750, 60, 23);

        SistemSarafKejang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kuat", "Ada" }));
        SistemSarafKejang.setName("SistemSarafKejang"); // NOI18N
        SistemSarafKejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafKejangKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafKejang);
        SistemSarafKejang.setBounds(366, 750, 80, 23);

        KetSistemSarafKejang.setFocusTraversalPolicyProvider(true);
        KetSistemSarafKejang.setName("KetSistemSarafKejang"); // NOI18N
        KetSistemSarafKejang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSistemSarafKejangKeyPressed(evt);
            }
        });
        FormInput.add(KetSistemSarafKejang);
        KetSistemSarafKejang.setBounds(450, 750, 184, 23);

        jLabel134.setText("Sensorik :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(655, 750, 80, 23);

        SistemSarafSensorik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Sakit Nyeri", "Rasa Kebas" }));
        SistemSarafSensorik.setName("SistemSarafSensorik"); // NOI18N
        SistemSarafSensorik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistemSarafSensorikKeyPressed(evt);
            }
        });
        FormInput.add(SistemSarafSensorik);
        SistemSarafSensorik.setBounds(739, 750, 115, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Kardiovaskuler :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(44, 780, 122, 23);

        jLabel136.setText("Pulsasi :");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(0, 800, 109, 23);

        KardiovaskularPulsasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Lemah", "Lain-lain" }));
        KardiovaskularPulsasi.setName("KardiovaskularPulsasi"); // NOI18N
        KardiovaskularPulsasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularPulsasiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularPulsasi);
        KardiovaskularPulsasi.setBounds(113, 800, 96, 23);

        jLabel137.setText("Sirkulasi :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(245, 800, 60, 23);

        KardiovaskularSirkulasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Akral Hangat", "Akral Dingin", "Edema" }));
        KardiovaskularSirkulasi.setName("KardiovaskularSirkulasi"); // NOI18N
        KardiovaskularSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularSirkulasi);
        KardiovaskularSirkulasi.setBounds(309, 800, 120, 23);

        KetKardiovaskularSirkulasi.setFocusTraversalPolicyProvider(true);
        KetKardiovaskularSirkulasi.setName("KetKardiovaskularSirkulasi"); // NOI18N
        KetKardiovaskularSirkulasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKardiovaskularSirkulasiKeyPressed(evt);
            }
        });
        FormInput.add(KetKardiovaskularSirkulasi);
        KetKardiovaskularSirkulasi.setBounds(433, 800, 184, 23);

        jLabel138.setText("Denyut Nadi :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(650, 800, 80, 23);

        KardiovaskularDenyutNadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        KardiovaskularDenyutNadi.setName("KardiovaskularDenyutNadi"); // NOI18N
        KardiovaskularDenyutNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiovaskularDenyutNadiKeyPressed(evt);
            }
        });
        FormInput.add(KardiovaskularDenyutNadi);
        KardiovaskularDenyutNadi.setBounds(734, 800, 120, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Respirasi :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 830, 96, 23);

        jLabel139.setText("Retraksi :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(0, 850, 109, 23);

        RespirasiRetraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ringan", "Berat" }));
        RespirasiRetraksi.setName("RespirasiRetraksi"); // NOI18N
        RespirasiRetraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiRetraksiKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiRetraksi);
        RespirasiRetraksi.setBounds(113, 850, 100, 23);

        jLabel140.setText("Pola Nafas :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(220, 850, 80, 23);

        RespirasiPolaNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Bradipnea", "Tachipnea" }));
        RespirasiPolaNafas.setName("RespirasiPolaNafas"); // NOI18N
        RespirasiPolaNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiPolaNafasKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiPolaNafas);
        RespirasiPolaNafas.setBounds(304, 850, 102, 23);

        jLabel141.setText("Suara Nafas :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(418, 850, 80, 23);

        RespirasiSuaraNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vesikuler", "Wheezing", "Rhonki" }));
        RespirasiSuaraNafas.setName("RespirasiSuaraNafas"); // NOI18N
        RespirasiSuaraNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiSuaraNafasKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiSuaraNafas);
        RespirasiSuaraNafas.setBounds(502, 850, 100, 23);

        jLabel145.setText("Batuk & Sekresi :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(610, 850, 100, 23);

        RespirasiBatuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya : Produktif", "Ya : Non Produktif" }));
        RespirasiBatuk.setName("RespirasiBatuk"); // NOI18N
        RespirasiBatuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiBatukKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiBatuk);
        RespirasiBatuk.setBounds(714, 850, 140, 23);

        jLabel143.setText("Volume :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(0, 880, 109, 23);

        RespirasiVolume.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hiperventilasi", "Hipoventilasi" }));
        RespirasiVolume.setName("RespirasiVolume"); // NOI18N
        RespirasiVolume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiVolumeKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiVolume);
        RespirasiVolume.setBounds(113, 880, 120, 23);

        jLabel144.setText("Jenis Pernafasaan :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(235, 880, 120, 23);

        RespirasiJenisPernafasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pernafasan Dada", "Alat Bantu Pernafasaan" }));
        RespirasiJenisPernafasan.setName("RespirasiJenisPernafasan"); // NOI18N
        RespirasiJenisPernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiJenisPernafasanKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiJenisPernafasan);
        RespirasiJenisPernafasan.setBounds(359, 880, 166, 23);

        KetRespirasiJenisPernafasan.setFocusTraversalPolicyProvider(true);
        KetRespirasiJenisPernafasan.setName("KetRespirasiJenisPernafasan"); // NOI18N
        KetRespirasiJenisPernafasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetRespirasiJenisPernafasanKeyPressed(evt);
            }
        });
        FormInput.add(KetRespirasiJenisPernafasan);
        KetRespirasiJenisPernafasan.setBounds(529, 880, 135, 23);

        jLabel142.setText("Irama :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(670, 880, 60, 23);

        RespirasiIrama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teratur", "Tidak Teratur" }));
        RespirasiIrama.setName("RespirasiIrama"); // NOI18N
        RespirasiIrama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiIramaKeyPressed(evt);
            }
        });
        FormInput.add(RespirasiIrama);
        RespirasiIrama.setBounds(734, 880, 120, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Gastrointestinal :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 910, 129, 23);

        jLabel146.setText("Mulut :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(0, 930, 109, 23);

        GastrointestinalMulut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Stomatitis", "Mukosa Kering", "Bibir Pucat", "Lain-lain" }));
        GastrointestinalMulut.setName("GastrointestinalMulut"); // NOI18N
        GastrointestinalMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalMulutKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalMulut);
        GastrointestinalMulut.setBounds(113, 930, 120, 23);

        KetGastrointestinalMulut.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalMulut.setName("KetGastrointestinalMulut"); // NOI18N
        KetGastrointestinalMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalMulutKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalMulut);
        KetGastrointestinalMulut.setBounds(237, 930, 190, 23);

        jLabel150.setText("Tenggorokan :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(452, 930, 80, 23);

        GastrointestinalTenggorakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Gangguan Menelan", "Sakit Menelan", "Lain-lain" }));
        GastrointestinalTenggorakan.setName("GastrointestinalTenggorakan"); // NOI18N
        GastrointestinalTenggorakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalTenggorakanKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalTenggorakan);
        GastrointestinalTenggorakan.setBounds(536, 930, 150, 23);

        KetGastrointestinalTenggorakan.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalTenggorakan.setName("KetGastrointestinalTenggorakan"); // NOI18N
        KetGastrointestinalTenggorakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalTenggorakanKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalTenggorakan);
        KetGastrointestinalTenggorakan.setBounds(690, 930, 164, 23);

        jLabel147.setText("Lidah :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(0, 960, 109, 23);

        GastrointestinalLidah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Kotor", "Gerak Asimetris", "Lain-lain" }));
        GastrointestinalLidah.setName("GastrointestinalLidah"); // NOI18N
        GastrointestinalLidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalLidahKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalLidah);
        GastrointestinalLidah.setBounds(113, 960, 130, 23);

        KetGastrointestinalLidah.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalLidah.setName("KetGastrointestinalLidah"); // NOI18N
        KetGastrointestinalLidah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalLidahKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalLidah);
        KetGastrointestinalLidah.setBounds(247, 960, 190, 23);

        jLabel151.setText("Abdomen :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(452, 960, 80, 23);

        GastrointestinalAbdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Supel", "Asictes", "Tegang", "Nyeri Tekan/Lepas", "Lain-lain" }));
        GastrointestinalAbdomen.setName("GastrointestinalAbdomen"); // NOI18N
        GastrointestinalAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalAbdomen);
        GastrointestinalAbdomen.setBounds(536, 960, 150, 23);

        KetGastrointestinalAbdomen.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalAbdomen.setName("KetGastrointestinalAbdomen"); // NOI18N
        KetGastrointestinalAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalAbdomen);
        KetGastrointestinalAbdomen.setBounds(690, 960, 164, 23);

        jLabel148.setText("Gigi :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(0, 990, 109, 23);

        GastrointestinalGigi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Karies", "Goyang", "Lain-lain" }));
        GastrointestinalGigi.setName("GastrointestinalGigi"); // NOI18N
        GastrointestinalGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalGigiKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalGigi);
        GastrointestinalGigi.setBounds(113, 990, 95, 23);

        KetGastrointestinalGigi.setFocusTraversalPolicyProvider(true);
        KetGastrointestinalGigi.setName("KetGastrointestinalGigi"); // NOI18N
        KetGastrointestinalGigi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetGastrointestinalGigiKeyPressed(evt);
            }
        });
        FormInput.add(KetGastrointestinalGigi);
        KetGastrointestinalGigi.setBounds(210, 990, 170, 23);

        jLabel152.setText("Peistatik Usus :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(396, 990, 100, 23);

        GastrointestinalUsus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Tidak Ada Bising Usus", "Hiperistaltik" }));
        GastrointestinalUsus.setName("GastrointestinalUsus"); // NOI18N
        GastrointestinalUsus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalUsusKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalUsus);
        GastrointestinalUsus.setBounds(500, 990, 164, 23);

        jLabel149.setText("Anus :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(692, 990, 50, 23);

        GastrointestinalAnus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Atresia Ani" }));
        GastrointestinalAnus.setName("GastrointestinalAnus"); // NOI18N
        GastrointestinalAnus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GastrointestinalAnusKeyPressed(evt);
            }
        });
        FormInput.add(GastrointestinalAnus);
        GastrointestinalAnus.setBounds(746, 990, 108, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Neurologi :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(44, 1020, 98, 23);

        jLabel153.setText("Sensorik :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(0, 1040, 109, 23);

        NeurologiSensorik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Sakit Nyeri", "Rasa Kebas", "Lain-lain" }));
        NeurologiSensorik.setName("NeurologiSensorik"); // NOI18N
        NeurologiSensorik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiSensorikKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiSensorik);
        NeurologiSensorik.setBounds(113, 1040, 108, 23);

        jLabel156.setText("Penglihatan :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(230, 1040, 80, 23);

        NeurologiPenglihatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Ada Kelainan" }));
        NeurologiPenglihatan.setName("NeurologiPenglihatan"); // NOI18N
        NeurologiPenglihatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiPenglihatanKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiPenglihatan);
        NeurologiPenglihatan.setBounds(314, 1040, 115, 23);

        KetNeurologiPenglihatan.setFocusTraversalPolicyProvider(true);
        KetNeurologiPenglihatan.setName("KetNeurologiPenglihatan"); // NOI18N
        KetNeurologiPenglihatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiPenglihatanKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiPenglihatan);
        KetNeurologiPenglihatan.setBounds(433, 1040, 150, 23);

        jLabel157.setText("Alat Bantu Penglihatan :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(590, 1040, 140, 23);

        NeurologiAlatBantuPenglihatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Kacamata", "Lensa Kontak" }));
        NeurologiAlatBantuPenglihatan.setName("NeurologiAlatBantuPenglihatan"); // NOI18N
        NeurologiAlatBantuPenglihatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiAlatBantuPenglihatanKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiAlatBantuPenglihatan);
        NeurologiAlatBantuPenglihatan.setBounds(734, 1040, 120, 23);

        jLabel154.setText("Motorik :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(0, 1070, 109, 23);

        NeurologiMotorik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Hemiparese", "Tetraparese", "Tremor", "Lain-lain" }));
        NeurologiMotorik.setName("NeurologiMotorik"); // NOI18N
        NeurologiMotorik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiMotorikKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiMotorik);
        NeurologiMotorik.setBounds(113, 1070, 108, 23);

        jLabel158.setText("Pendengaran :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(229, 1070, 80, 23);

        NeurologiPendengaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Berdengung", "Nyeri", "Tuli", "Keluar Cairan", "Lain-lain" }));
        NeurologiPendengaran.setName("NeurologiPendengaran"); // NOI18N
        NeurologiPendengaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiPendengaranKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiPendengaran);
        NeurologiPendengaran.setBounds(313, 1070, 117, 23);

        jLabel159.setText("Bicara :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(433, 1070, 50, 23);

        NeurologiBicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jelas", "Tidak Jelas" }));
        NeurologiBicara.setName("NeurologiBicara"); // NOI18N
        NeurologiBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiBicaraKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiBicara);
        NeurologiBicara.setBounds(487, 1070, 105, 23);

        KetNeurologiBicara.setFocusTraversalPolicyProvider(true);
        KetNeurologiBicara.setName("KetNeurologiBicara"); // NOI18N
        KetNeurologiBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNeurologiBicaraKeyPressed(evt);
            }
        });
        FormInput.add(KetNeurologiBicara);
        KetNeurologiBicara.setBounds(596, 1070, 127, 23);

        jLabel155.setText("Otot :");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(728, 1070, 40, 23);

        NeurologiOtot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kuat", "Lemah" }));
        NeurologiOtot.setName("NeurologiOtot"); // NOI18N
        NeurologiOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NeurologiOtotKeyPressed(evt);
            }
        });
        FormInput.add(NeurologiOtot);
        NeurologiOtot.setBounds(772, 1070, 82, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Integument :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 1100, 108, 23);

        jLabel160.setText("Kulit :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(0, 1120, 109, 23);

        IntegumentKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Rash/Kemerahan", "Luka", "Memar", "Ptekie", "Bula" }));
        IntegumentKulit.setName("IntegumentKulit"); // NOI18N
        IntegumentKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentKulitKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentKulit);
        IntegumentKulit.setBounds(113, 1120, 134, 23);

        jLabel161.setText("Warna Kulit :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(251, 1120, 70, 23);

        IntegumentWarnaKulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Pucat", "Sianosis", "Lain-lain" }));
        IntegumentWarnaKulit.setName("IntegumentWarnaKulit"); // NOI18N
        IntegumentWarnaKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentWarnaKulitKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentWarnaKulit);
        IntegumentWarnaKulit.setBounds(325, 1120, 92, 23);

        jLabel162.setText("Turgor :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(420, 1120, 48, 23);

        IntegumentTurgor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        IntegumentTurgor.setName("IntegumentTurgor"); // NOI18N
        IntegumentTurgor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentTurgorKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentTurgor);
        IntegumentTurgor.setBounds(472, 1120, 86, 23);

        jLabel163.setText("Resiko Decubi :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(558, 1120, 90, 23);

        IntegumentDecubitus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Usia > 65 tahun", "Obesitas", "Imobilisasi", "Paraplegi/Vegetative State", "Dirawat Di HCU", "Penyakit Kronis (DM", "CHF", "CKD)", "Inkontinentia Uri/Alvi" }));
        IntegumentDecubitus.setName("IntegumentDecubitus"); // NOI18N
        IntegumentDecubitus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntegumentDecubitusKeyPressed(evt);
            }
        });
        FormInput.add(IntegumentDecubitus);
        IntegumentDecubitus.setBounds(652, 1120, 202, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Muskuloskletal :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(44, 1150, 122, 23);

        jLabel164.setText("Oedema :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(0, 1170, 109, 23);

        MuskuloskletalOedema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MuskuloskletalOedema.setName("MuskuloskletalOedema"); // NOI18N
        MuskuloskletalOedema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalOedemaKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalOedema);
        MuskuloskletalOedema.setBounds(113, 1170, 100, 23);

        KetMuskuloskletalOedema.setFocusTraversalPolicyProvider(true);
        KetMuskuloskletalOedema.setName("KetMuskuloskletalOedema"); // NOI18N
        KetMuskuloskletalOedema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMuskuloskletalOedemaKeyPressed(evt);
            }
        });
        FormInput.add(KetMuskuloskletalOedema);
        KetMuskuloskletalOedema.setBounds(217, 1170, 220, 23);

        jLabel167.setText("Pergerakan Sendi :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(452, 1170, 109, 23);

        MuskuloskletalPegerakanSendi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bebas", "Terbatas" }));
        MuskuloskletalPegerakanSendi.setName("MuskuloskletalPegerakanSendi"); // NOI18N
        MuskuloskletalPegerakanSendi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalPegerakanSendiKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalPegerakanSendi);
        MuskuloskletalPegerakanSendi.setBounds(565, 1170, 93, 23);

        jLabel168.setText("Kekuatan Otot :");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(675, 1170, 90, 23);

        MuskuloskletalKekuatanOtot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Lemah", "Tremor" }));
        MuskuloskletalKekuatanOtot.setName("MuskuloskletalKekuatanOtot"); // NOI18N
        MuskuloskletalKekuatanOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalKekuatanOtotKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalKekuatanOtot);
        MuskuloskletalKekuatanOtot.setBounds(769, 1170, 85, 23);

        jLabel165.setText("Fraktur :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(0, 1200, 109, 23);

        MuskuloskletalFraktur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MuskuloskletalFraktur.setName("MuskuloskletalFraktur"); // NOI18N
        MuskuloskletalFraktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalFrakturKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalFraktur);
        MuskuloskletalFraktur.setBounds(113, 1200, 100, 23);

        KetMuskuloskletalFraktur.setFocusTraversalPolicyProvider(true);
        KetMuskuloskletalFraktur.setName("KetMuskuloskletalFraktur"); // NOI18N
        KetMuskuloskletalFraktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMuskuloskletalFrakturKeyPressed(evt);
            }
        });
        FormInput.add(KetMuskuloskletalFraktur);
        KetMuskuloskletalFraktur.setBounds(217, 1200, 220, 23);

        jLabel166.setText("Nyeri Sendi :");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(446, 1200, 80, 23);

        MuskuloskletalNyeriSendi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        MuskuloskletalNyeriSendi.setName("MuskuloskletalNyeriSendi"); // NOI18N
        MuskuloskletalNyeriSendi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskuloskletalNyeriSendiKeyPressed(evt);
            }
        });
        FormInput.add(MuskuloskletalNyeriSendi);
        MuskuloskletalNyeriSendi.setBounds(530, 1200, 100, 23);

        KetMuskuloskletalNyeriSendi.setFocusTraversalPolicyProvider(true);
        KetMuskuloskletalNyeriSendi.setName("KetMuskuloskletalNyeriSendi"); // NOI18N
        KetMuskuloskletalNyeriSendi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetMuskuloskletalNyeriSendiKeyPressed(evt);
            }
        });
        FormInput.add(KetMuskuloskletalNyeriSendi);
        KetMuskuloskletalNyeriSendi.setBounds(634, 1200, 220, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Eliminasi :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(44, 1230, 95, 23);

        jLabel115.setText("BAB : Frekuensi :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(0, 1250, 166, 23);

        BAB.setHighlighter(null);
        BAB.setName("BAB"); // NOI18N
        BAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BABKeyPressed(evt);
            }
        });
        FormInput.add(BAB);
        BAB.setBounds(170, 1250, 50, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("X/");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(222, 1250, 13, 23);

        XBAB.setHighlighter(null);
        XBAB.setName("XBAB"); // NOI18N
        XBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XBABKeyPressed(evt);
            }
        });
        FormInput.add(XBAB);
        XBAB.setBounds(237, 1250, 75, 23);

        jLabel114.setText("Konsistensi :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(340, 1250, 70, 23);

        KBAB.setHighlighter(null);
        KBAB.setName("KBAB"); // NOI18N
        KBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KBABKeyPressed(evt);
            }
        });
        FormInput.add(KBAB);
        KBAB.setBounds(414, 1250, 175, 23);

        jLabel116.setText("Warna :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(620, 1250, 55, 23);

        WBAB.setHighlighter(null);
        WBAB.setName("WBAB"); // NOI18N
        WBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WBABKeyPressed(evt);
            }
        });
        FormInput.add(WBAB);
        WBAB.setBounds(679, 1250, 175, 23);

        jLabel117.setText("BAK : Frekuensi :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(0, 1280, 166, 23);

        BAK.setHighlighter(null);
        BAK.setName("BAK"); // NOI18N
        BAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BAKKeyPressed(evt);
            }
        });
        FormInput.add(BAK);
        BAK.setBounds(170, 1280, 50, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setText("X/");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(222, 1280, 13, 23);

        XBAK.setHighlighter(null);
        XBAK.setName("XBAK"); // NOI18N
        XBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                XBAKKeyPressed(evt);
            }
        });
        FormInput.add(XBAK);
        XBAK.setBounds(237, 1280, 75, 23);

        jLabel119.setText("Warna :");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(340, 1280, 70, 23);

        WBAK.setHighlighter(null);
        WBAK.setName("WBAK"); // NOI18N
        WBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WBAKKeyPressed(evt);
            }
        });
        FormInput.add(WBAK);
        WBAK.setBounds(414, 1280, 175, 23);

        jLabel120.setText("Lain-lain :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(620, 1280, 55, 23);

        LBAK.setHighlighter(null);
        LBAK.setName("LBAK"); // NOI18N
        LBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LBAKKeyPressed(evt);
            }
        });
        FormInput.add(LBAK);
        LBAK.setBounds(679, 1280, 175, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 1310, 880, 1);

        jLabel189.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel189.setText("VI. RIWAYAT PSIKOLOGIS – SOSIAL – EKONOMI – BUDAYA – SPIRITUAL");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(10, 1310, 490, 23);

        jLabel190.setText(":");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(0, 1330, 138, 23);

        KondisiPsikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Marah", "Takut", "Depresi", "Cepat Lelah", "Cemas", "Gelisah", "Sulit Tidur", "Lain-lain" }));
        KondisiPsikologis.setName("KondisiPsikologis"); // NOI18N
        KondisiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPsikologisKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPsikologis);
        KondisiPsikologis.setBounds(142, 1330, 142, 23);

        jLabel191.setText("Adakah Perilaku :");
        jLabel191.setName("jLabel191"); // NOI18N
        FormInput.add(jLabel191);
        jLabel191.setBounds(290, 1330, 110, 23);

        AdakahPerilaku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Perilaku Kekerasan", "Gangguan Efek", "Gangguan Memori", "Halusinasi", "Kecenderungan Percobaan Bunuh Diri", "Lain-lain" }));
        AdakahPerilaku.setName("AdakahPerilaku"); // NOI18N
        AdakahPerilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdakahPerilakuKeyPressed(evt);
            }
        });
        FormInput.add(AdakahPerilaku);
        AdakahPerilaku.setBounds(404, 1330, 235, 23);

        KeteranganAdakahPerilaku.setFocusTraversalPolicyProvider(true);
        KeteranganAdakahPerilaku.setName("KeteranganAdakahPerilaku"); // NOI18N
        KeteranganAdakahPerilaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAdakahPerilakuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAdakahPerilaku);
        KeteranganAdakahPerilaku.setBounds(642, 1330, 212, 23);

        jLabel284.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel284.setText("Kondisi Psikologis");
        jLabel284.setName("jLabel284"); // NOI18N
        FormInput.add(jLabel284);
        jLabel284.setBounds(44, 1330, 110, 23);

        jLabel192.setText(":");
        jLabel192.setName("jLabel192"); // NOI18N
        FormInput.add(jLabel192);
        jLabel192.setBounds(0, 1360, 188, 23);

        GangguanJiwa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        GangguanJiwa.setName("GangguanJiwa"); // NOI18N
        GangguanJiwa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GangguanJiwaKeyPressed(evt);
            }
        });
        FormInput.add(GangguanJiwa);
        GangguanJiwa.setBounds(192, 1360, 77, 23);

        jLabel193.setText("Hubungan Pasien dengan Anggota Keluarga :");
        jLabel193.setName("jLabel193"); // NOI18N
        FormInput.add(jLabel193);
        jLabel193.setBounds(283, 1360, 240, 23);

        HubunganAnggotaKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Harmonis", "Kurang Harmonis", "Tidak Harmonis", "Konflik Besar" }));
        HubunganAnggotaKeluarga.setName("HubunganAnggotaKeluarga"); // NOI18N
        HubunganAnggotaKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganAnggotaKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganAnggotaKeluarga);
        HubunganAnggotaKeluarga.setBounds(527, 1360, 133, 23);

        jLabel194.setText("Agama :");
        jLabel194.setName("jLabel194"); // NOI18N
        FormInput.add(jLabel194);
        jLabel194.setBounds(670, 1360, 60, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        FormInput.add(Agama);
        Agama.setBounds(734, 1360, 120, 23);

        jLabel285.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel285.setText("Gangguan Jiwa di Masa Lalu");
        jLabel285.setName("jLabel285"); // NOI18N
        FormInput.add(jLabel285);
        jLabel285.setBounds(44, 1360, 150, 23);

        jLabel195.setText(":");
        jLabel195.setName("jLabel195"); // NOI18N
        FormInput.add(jLabel195);
        jLabel195.setBounds(0, 1390, 127, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Orang Tua", "Panti Asuhan", "Keluarga", "Lain-lain" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(131, 1390, 105, 23);

        KeteranganTinggalDengan.setFocusTraversalPolicyProvider(true);
        KeteranganTinggalDengan.setName("KeteranganTinggalDengan"); // NOI18N
        KeteranganTinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTinggalDengan);
        KeteranganTinggalDengan.setBounds(239, 1390, 137, 23);

        jLabel196.setText("Pekerjaan P.J. :");
        jLabel196.setName("jLabel196"); // NOI18N
        FormInput.add(jLabel196);
        jLabel196.setBounds(397, 1390, 83, 23);

        PekerjaanPJ.setFocusTraversalPolicyProvider(true);
        PekerjaanPJ.setName("PekerjaanPJ"); // NOI18N
        PekerjaanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanPJKeyPressed(evt);
            }
        });
        FormInput.add(PekerjaanPJ);
        PekerjaanPJ.setBounds(484, 1390, 130, 23);

        jLabel197.setText("Pembayaran :");
        jLabel197.setName("jLabel197"); // NOI18N
        FormInput.add(jLabel197);
        jLabel197.setBounds(620, 1390, 90, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setFocusTraversalPolicyProvider(true);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput.add(CaraBayar);
        CaraBayar.setBounds(714, 1390, 140, 23);

        jLabel286.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel286.setText("Tinggal Dengan");
        jLabel286.setName("jLabel286"); // NOI18N
        FormInput.add(jLabel286);
        jLabel286.setBounds(44, 1390, 90, 23);

        jLabel198.setText(":");
        jLabel198.setName("jLabel198"); // NOI18N
        FormInput.add(jLabel198);
        jLabel198.setBounds(0, 1420, 322, 23);

        NilaiKepercayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        NilaiKepercayaan.setName("NilaiKepercayaan"); // NOI18N
        NilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(NilaiKepercayaan);
        NilaiKepercayaan.setBounds(326, 1420, 105, 23);

        KeteranganNilaiKepercayaan.setFocusTraversalPolicyProvider(true);
        KeteranganNilaiKepercayaan.setName("KeteranganNilaiKepercayaan"); // NOI18N
        KeteranganNilaiKepercayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganNilaiKepercayaanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganNilaiKepercayaan);
        KeteranganNilaiKepercayaan.setBounds(435, 1420, 170, 23);

        jLabel199.setText("Bahasa Sehari-hari :");
        jLabel199.setName("jLabel199"); // NOI18N
        FormInput.add(jLabel199);
        jLabel199.setBounds(610, 1420, 120, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        FormInput.add(Bahasa);
        Bahasa.setBounds(734, 1420, 120, 23);

        jLabel287.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel287.setText("Nilai-nilai Kepercayaan/Budaya Yang Perlu Diperhatikan");
        jLabel287.setName("jLabel287"); // NOI18N
        FormInput.add(jLabel287);
        jLabel287.setBounds(44, 1420, 290, 23);

        jLabel200.setText(":");
        jLabel200.setName("jLabel200"); // NOI18N
        FormInput.add(jLabel200);
        jLabel200.setBounds(0, 1450, 139, 23);

        PendidikanPasien.setEditable(false);
        PendidikanPasien.setFocusTraversalPolicyProvider(true);
        PendidikanPasien.setName("PendidikanPasien"); // NOI18N
        FormInput.add(PendidikanPasien);
        PendidikanPasien.setBounds(143, 1450, 100, 23);

        jLabel201.setText("Pendidikan P.J. :");
        jLabel201.setName("jLabel201"); // NOI18N
        FormInput.add(jLabel201);
        jLabel201.setBounds(247, 1450, 100, 23);

        PendidikanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "SLTA/SEDERAJAT", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        PendidikanPJ.setName("PendidikanPJ"); // NOI18N
        PendidikanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanPJKeyPressed(evt);
            }
        });
        FormInput.add(PendidikanPJ);
        PendidikanPJ.setBounds(351, 1450, 135, 23);

        jLabel204.setText("Edukasi Diberikan Kepada :");
        jLabel204.setName("jLabel204"); // NOI18N
        FormInput.add(jLabel204);
        jLabel204.setBounds(487, 1450, 160, 23);

        EdukasiPsikolgis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasien", "Keluarga", "Lainnya" }));
        EdukasiPsikolgis.setName("EdukasiPsikolgis"); // NOI18N
        EdukasiPsikolgis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiPsikolgisKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiPsikolgis);
        EdukasiPsikolgis.setBounds(651, 1450, 95, 23);

        KeteranganEdukasiPsikologis.setFocusTraversalPolicyProvider(true);
        KeteranganEdukasiPsikologis.setName("KeteranganEdukasiPsikologis"); // NOI18N
        KeteranganEdukasiPsikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEdukasiPsikologisKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEdukasiPsikologis);
        KeteranganEdukasiPsikologis.setBounds(749, 1450, 105, 23);

        jLabel288.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel288.setText("Pendidikan Pasien");
        jLabel288.setName("jLabel288"); // NOI18N
        FormInput.add(jLabel288);
        jLabel288.setBounds(44, 1450, 100, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 1480, 880, 1);

        jLabel214.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel214.setText("VII. KEBUTUHAN KOMUNIKASI DAN BELAJAR/EDUKASI (ORANGTUA/WALI/PENDAMPING)");
        jLabel214.setName("jLabel214"); // NOI18N
        FormInput.add(jLabel214);
        jLabel214.setBounds(10, 1480, 560, 23);

        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel215.setText("Bahasa Sehari-hari ");
        jLabel215.setName("jLabel215"); // NOI18N
        FormInput.add(jLabel215);
        jLabel215.setBounds(44, 1500, 120, 23);

        jLabel216.setText(":");
        jLabel216.setName("jLabel216"); // NOI18N
        FormInput.add(jLabel216);
        jLabel216.setBounds(0, 1500, 143, 23);

        BahasaSehari.setFocusTraversalPolicyProvider(true);
        BahasaSehari.setName("BahasaSehari"); // NOI18N
        BahasaSehari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaSehariKeyPressed(evt);
            }
        });
        FormInput.add(BahasaSehari);
        BahasaSehari.setBounds(147, 1500, 110, 23);

        jLabel217.setText("Kemampuan Baca & Tulis :");
        jLabel217.setName("jLabel217"); // NOI18N
        FormInput.add(jLabel217);
        jLabel217.setBounds(259, 1500, 140, 23);

        KemampuanBacaTulis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Kurang", "Tidak Bisa" }));
        KemampuanBacaTulis.setName("KemampuanBacaTulis"); // NOI18N
        KemampuanBacaTulis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemampuanBacaTulisKeyPressed(evt);
            }
        });
        FormInput.add(KemampuanBacaTulis);
        KemampuanBacaTulis.setBounds(403, 1500, 100, 23);

        jLabel218.setText("Butuh Penerjamah :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(502, 1500, 110, 23);

        ButuhPenerjemah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ButuhPenerjemah.setName("ButuhPenerjemah"); // NOI18N
        ButuhPenerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButuhPenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(ButuhPenerjemah);
        ButuhPenerjemah.setBounds(616, 1500, 80, 23);

        jLabel219.setText("Jika Ya :");
        jLabel219.setName("jLabel219"); // NOI18N
        FormInput.add(jLabel219);
        jLabel219.setBounds(696, 1500, 50, 23);

        KeteranganButuhPenerjemah.setFocusTraversalPolicyProvider(true);
        KeteranganButuhPenerjemah.setName("KeteranganButuhPenerjemah"); // NOI18N
        KeteranganButuhPenerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganButuhPenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganButuhPenerjemah);
        KeteranganButuhPenerjemah.setBounds(750, 1500, 104, 23);

        jLabel225.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel225.setText(",");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(698, 1500, 30, 23);

        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel220.setText(",");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(348, 1530, 20, 23);

        jLabel221.setText(":");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(0, 1530, 252, 23);

        TerdapatHambatanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TerdapatHambatanBelajar.setName("TerdapatHambatanBelajar"); // NOI18N
        TerdapatHambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerdapatHambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(TerdapatHambatanBelajar);
        TerdapatHambatanBelajar.setBounds(256, 1530, 90, 23);

        jLabel222.setText("Jika Ya :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(336, 1530, 60, 23);

        HambatanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Gangguan Pendengaran", "Gangguan Penglihatan", "Gangguan Kognitif", "Gangguan Fisik", "Gangguan Emosi", "Keterbatasan Bahasa", "Keterbatasan Budaya", "Keterbatasan Spiritual", "Agama", "Lainnya" }));
        HambatanBelajar.setName("HambatanBelajar"); // NOI18N
        HambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(HambatanBelajar);
        HambatanBelajar.setBounds(400, 1530, 177, 23);

        KeteranganHambatanBelajar.setFocusTraversalPolicyProvider(true);
        KeteranganHambatanBelajar.setName("KeteranganHambatanBelajar"); // NOI18N
        KeteranganHambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHambatanBelajar);
        KeteranganHambatanBelajar.setBounds(580, 1530, 274, 23);

        jLabel224.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel224.setText("Terdapat Hambatan Dalam Pembelajaran");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(44, 1530, 240, 23);

        jLabel223.setText(":");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(0, 1560, 160, 23);

        HambatanCaraBicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Bicara" }));
        HambatanCaraBicara.setName("HambatanCaraBicara"); // NOI18N
        HambatanCaraBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanCaraBicaraKeyPressed(evt);
            }
        });
        FormInput.add(HambatanCaraBicara);
        HambatanCaraBicara.setBounds(164, 1560, 150, 23);

        HambatanBahasaIsyarat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        HambatanBahasaIsyarat.setName("HambatanBahasaIsyarat"); // NOI18N
        HambatanBahasaIsyarat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanBahasaIsyaratKeyPressed(evt);
            }
        });
        FormInput.add(HambatanBahasaIsyarat);
        HambatanBahasaIsyarat.setBounds(474, 1560, 90, 23);

        jLabel226.setText("Hambatan Bahasa Isyarat :");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(320, 1560, 150, 23);

        jLabel227.setText("Cara Belajar Yang Disukai :");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(570, 1560, 150, 23);

        CaraBelajarDisukai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Audio", "Lisan", "Visual", "Demonstrasi", "Tulisan" }));
        CaraBelajarDisukai.setName("CaraBelajarDisukai"); // NOI18N
        CaraBelajarDisukai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraBelajarDisukaiKeyPressed(evt);
            }
        });
        FormInput.add(CaraBelajarDisukai);
        CaraBelajarDisukai.setBounds(724, 1560, 130, 23);

        jLabel228.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel228.setText("Hambatan Cara Bicara");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(44, 1560, 140, 23);

        jLabel229.setText(":");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(0, 1590, 202, 23);

        KesediaanMenerimaInformasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KesediaanMenerimaInformasi.setName("KesediaanMenerimaInformasi"); // NOI18N
        KesediaanMenerimaInformasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesediaanMenerimaInformasiKeyPressed(evt);
            }
        });
        FormInput.add(KesediaanMenerimaInformasi);
        KesediaanMenerimaInformasi.setBounds(206, 1590, 90, 23);

        KeteranganKesediaanMenerimaInformasi.setFocusTraversalPolicyProvider(true);
        KeteranganKesediaanMenerimaInformasi.setName("KeteranganKesediaanMenerimaInformasi"); // NOI18N
        KeteranganKesediaanMenerimaInformasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKesediaanMenerimaInformasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKesediaanMenerimaInformasi);
        KeteranganKesediaanMenerimaInformasi.setBounds(299, 1590, 273, 23);

        jLabel230.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel230.setText("Kesediaan Menerima Informasi");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(44, 1590, 170, 23);

        jLabel231.setText("Pemahaman Tentang Nutrisi/Diet :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(580, 1590, 180, 23);

        PemahamanNutrisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanNutrisi.setName("PemahamanNutrisi"); // NOI18N
        PemahamanNutrisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanNutrisiKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanNutrisi);
        PemahamanNutrisi.setBounds(764, 1590, 90, 23);

        PemahamanPenyakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPenyakit.setName("PemahamanPenyakit"); // NOI18N
        PemahamanPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPenyakit);
        PemahamanPenyakit.setBounds(202, 1620, 90, 23);

        jLabel232.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel232.setText("Pemahaman Tentang Penyakit");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(44, 1620, 180, 23);

        jLabel233.setText(":");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(0, 1620, 198, 23);

        jLabel234.setText("Pemahaman Tentang Perawatan :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(580, 1620, 180, 23);

        PemahamanPerawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPerawatan.setName("PemahamanPerawatan"); // NOI18N
        PemahamanPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPerawatanKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPerawatan);
        PemahamanPerawatan.setBounds(764, 1620, 90, 23);

        PemahamanPengobatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemahamanPengobatan.setName("PemahamanPengobatan"); // NOI18N
        PemahamanPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemahamanPengobatanKeyPressed(evt);
            }
        });
        FormInput.add(PemahamanPengobatan);
        PemahamanPengobatan.setBounds(486, 1620, 90, 23);

        jLabel235.setText("Pemahaman Tentang Pengobatan :");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(302, 1620, 180, 23);

        jLabel169.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel169.setText("VIII. SKRINING GIZI (STRONG KID)");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(10, 1650, 380, 23);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 1650, 880, 1);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
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
        SG1.setBounds(700, 1670, 80, 23);

        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText("Apakah pasien tampak kurus");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(58, 1670, 590, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi1.setText("0");
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(794, 1670, 60, 23);

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
        SG2.setBounds(700, 1700, 80, 23);

        jLabel171.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel171.setText("bila ada atau untuk bayi < 1 tahun ; berat badan tidak naik selama 3 bulan terakhir)");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(58, 1708, 600, 23);

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setText("2.");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(44, 1700, 20, 23);

        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setText("Apakah terdapat penurunan berat badan selama satu bulan terakhir? (berdasarkan penilaian objektif data berat badan");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(58, 1696, 600, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi2.setText("0");
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(794, 1700, 60, 23);

        jLabel174.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel174.setText("1.");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(44, 1670, 20, 23);

        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel175.setText("Apakah terdapat salah satu dari kondisi tersebut? Diare > 5 kali/hari dan/muntah > 3 kali/hari dalam seminggu terakhir;");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(58, 1726, 610, 23);

        jLabel176.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel176.setText("Asupan makanan berkurang selama 1 minggu terakhir");
        jLabel176.setName("jLabel176"); // NOI18N
        FormInput.add(jLabel176);
        jLabel176.setBounds(58, 1738, 610, 23);

        SG3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG3.setName("SG3"); // NOI18N
        SG3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        SG3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(SG3);
        SG3.setBounds(700, 1730, 80, 23);

        NilaiGizi3.setEditable(false);
        NilaiGizi3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi3.setText("0");
        NilaiGizi3.setFocusTraversalPolicyProvider(true);
        NilaiGizi3.setName("NilaiGizi3"); // NOI18N
        FormInput.add(NilaiGizi3);
        NilaiGizi3.setBounds(794, 1730, 60, 23);

        jLabel177.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel177.setText("3.");
        jLabel177.setName("jLabel177"); // NOI18N
        FormInput.add(jLabel177);
        jLabel177.setBounds(44, 1730, 20, 23);

        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setText("Apakah terdapat penyakit atau keadaan yang menyebabkan pasien beresiko mengalami malnutrisi?");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(58, 1760, 610, 23);

        SG4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG4.setName("SG4"); // NOI18N
        SG4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG4ItemStateChanged(evt);
            }
        });
        SG4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG4KeyPressed(evt);
            }
        });
        FormInput.add(SG4);
        SG4.setBounds(700, 1760, 80, 23);

        NilaiGizi4.setEditable(false);
        NilaiGizi4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi4.setText("0");
        NilaiGizi4.setFocusTraversalPolicyProvider(true);
        NilaiGizi4.setName("NilaiGizi4"); // NOI18N
        FormInput.add(NilaiGizi4);
        NilaiGizi4.setBounds(794, 1760, 60, 23);

        jLabel179.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel179.setText("4.");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(44, 1760, 20, 23);

        jLabel180.setText("Total Skor :");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(680, 1790, 90, 23);

        TotalNilaiGizi.setEditable(false);
        TotalNilaiGizi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalNilaiGizi.setText("0");
        TotalNilaiGizi.setFocusTraversalPolicyProvider(true);
        TotalNilaiGizi.setName("TotalNilaiGizi"); // NOI18N
        FormInput.add(TotalNilaiGizi);
        TotalNilaiGizi.setBounds(774, 1790, 80, 23);

        jLabel240.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel240.setText("Keterangan");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(44, 1790, 100, 23);

        KeteranganSkriningGizi.setFocusTraversalPolicyProvider(true);
        KeteranganSkriningGizi.setName("KeteranganSkriningGizi"); // NOI18N
        KeteranganSkriningGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganSkriningGiziKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganSkriningGizi);
        KeteranganSkriningGizi.setBounds(111, 1790, 383, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1820, 880, 1);

        jLabel242.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel242.setText("IX. PENILAIAN RISIKO JATUH (SKALA HUMPTY DUMPTY)");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(10, 1820, 380, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("1. Umur");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(44, 1840, 200, 23);

        SkalaResiko1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 - 3 Tahun", "3 - 7 Tahun", "7 - 13 Tahun", "> 13 Tahun" }));
        SkalaResiko1.setName("SkalaResiko1"); // NOI18N
        SkalaResiko1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko1ItemStateChanged(evt);
            }
        });
        SkalaResiko1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko1KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko1);
        SkalaResiko1.setBounds(254, 1840, 550, 23);

        NilaiResiko1.setEditable(false);
        NilaiResiko1.setFocusTraversalPolicyProvider(true);
        NilaiResiko1.setName("NilaiResiko1"); // NOI18N
        FormInput.add(NilaiResiko1);
        NilaiResiko1.setBounds(814, 1840, 40, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("2. Jenis Kelamin");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(44, 1870, 200, 23);

        SkalaResiko2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        SkalaResiko2.setName("SkalaResiko2"); // NOI18N
        SkalaResiko2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko2ItemStateChanged(evt);
            }
        });
        SkalaResiko2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko2KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko2);
        SkalaResiko2.setBounds(254, 1870, 550, 23);

        NilaiResiko2.setEditable(false);
        NilaiResiko2.setFocusTraversalPolicyProvider(true);
        NilaiResiko2.setName("NilaiResiko2"); // NOI18N
        FormInput.add(NilaiResiko2);
        NilaiResiko2.setBounds(814, 1870, 40, 23);

        jLabel245.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel245.setText("3. Diagnosa");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(44, 1900, 200, 23);

        SkalaResiko3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelainan Neurologi", "Perubahan Dalam Oksigen(Masalah Saluran Nafas, Dehidrasi, Anemia, Anoreksia / Sakit Kepala, Dll)", "Kelainan Psikis / Perilaku", "Diagnosa Lain" }));
        SkalaResiko3.setName("SkalaResiko3"); // NOI18N
        SkalaResiko3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko3ItemStateChanged(evt);
            }
        });
        SkalaResiko3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko3KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko3);
        SkalaResiko3.setBounds(254, 1900, 550, 23);

        NilaiResiko3.setEditable(false);
        NilaiResiko3.setFocusTraversalPolicyProvider(true);
        NilaiResiko3.setName("NilaiResiko3"); // NOI18N
        FormInput.add(NilaiResiko3);
        NilaiResiko3.setBounds(814, 1900, 40, 23);

        jLabel246.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel246.setText("4. Gangguan Kognitif");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(44, 1930, 200, 23);

        SkalaResiko4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Sadar Terhadap Keterbatasan", "Lupa Keterbatasan", "Mengetahui Kemampuan Diri" }));
        SkalaResiko4.setName("SkalaResiko4"); // NOI18N
        SkalaResiko4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko4ItemStateChanged(evt);
            }
        });
        SkalaResiko4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko4KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko4);
        SkalaResiko4.setBounds(254, 1930, 550, 23);

        NilaiResiko4.setEditable(false);
        NilaiResiko4.setFocusTraversalPolicyProvider(true);
        NilaiResiko4.setName("NilaiResiko4"); // NOI18N
        FormInput.add(NilaiResiko4);
        NilaiResiko4.setBounds(814, 1930, 40, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("5. Faktor Lingkungan");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(44, 1960, 200, 23);

        SkalaResiko5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Riwayat Jatuh Dari Tempat Tidur Saat Bayi/Anak", "Pasien Menggunakan Alat Bantu/Box/Mebel", "Pasien Berada Di Tempat Tidur", "Di Luar Ruang Rawat" }));
        SkalaResiko5.setName("SkalaResiko5"); // NOI18N
        SkalaResiko5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko5ItemStateChanged(evt);
            }
        });
        SkalaResiko5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko5KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko5);
        SkalaResiko5.setBounds(254, 1960, 550, 23);

        NilaiResiko5.setEditable(false);
        NilaiResiko5.setFocusTraversalPolicyProvider(true);
        NilaiResiko5.setName("NilaiResiko5"); // NOI18N
        FormInput.add(NilaiResiko5);
        NilaiResiko5.setBounds(814, 1960, 40, 23);

        jLabel248.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel248.setText("6. Efek Obat Penenang/Operasi/Anastesi");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(44, 1990, 210, 23);

        SkalaResiko6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam 24 Jam", "Dalam 48 Jam", "> 48 Jam" }));
        SkalaResiko6.setName("SkalaResiko6"); // NOI18N
        SkalaResiko6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko6ItemStateChanged(evt);
            }
        });
        SkalaResiko6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko6KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko6);
        SkalaResiko6.setBounds(254, 1990, 550, 23);

        NilaiResiko6.setEditable(false);
        NilaiResiko6.setFocusTraversalPolicyProvider(true);
        NilaiResiko6.setName("NilaiResiko6"); // NOI18N
        FormInput.add(NilaiResiko6);
        NilaiResiko6.setBounds(814, 1990, 40, 23);

        jLabel249.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel249.setText("7. Penggunaan Obat");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(44, 2020, 210, 23);

        SkalaResiko7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bermacam-macam Obat Yang Digunakan : Obat Sedative (Kecuali Pasien ICU Yang Menggunakan sedasi dan paralisis), Hipnotik, Barbiturat, Fenoti-Azin, Antidepresan, Laksans/Diuretika,Narkotik", "Salah Satu Dari Pengobatan Di Atas", "Pengobatan Lain" }));
        SkalaResiko7.setName("SkalaResiko7"); // NOI18N
        SkalaResiko7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaResiko7ItemStateChanged(evt);
            }
        });
        SkalaResiko7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaResiko7KeyPressed(evt);
            }
        });
        FormInput.add(SkalaResiko7);
        SkalaResiko7.setBounds(254, 2020, 550, 23);

        NilaiResiko7.setEditable(false);
        NilaiResiko7.setFocusTraversalPolicyProvider(true);
        NilaiResiko7.setName("NilaiResiko7"); // NOI18N
        FormInput.add(NilaiResiko7);
        NilaiResiko7.setBounds(814, 2020, 40, 23);

        TingkatResiko1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko1.setText("Tingkat Resiko");
        TingkatResiko1.setToolTipText("");
        TingkatResiko1.setName("TingkatResiko1"); // NOI18N
        FormInput.add(TingkatResiko1);
        TingkatResiko1.setBounds(44, 2050, 90, 23);

        KeteranganTingkatRisiko.setEditable(false);
        KeteranganTingkatRisiko.setFocusTraversalPolicyProvider(true);
        KeteranganTingkatRisiko.setName("KeteranganTingkatRisiko"); // NOI18N
        FormInput.add(KeteranganTingkatRisiko);
        KeteranganTingkatRisiko.setBounds(126, 2050, 368, 23);

        jLabel256.setText("Total :");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(734, 2050, 70, 23);

        NilaiResikoTotal.setEditable(false);
        NilaiResikoTotal.setFocusTraversalPolicyProvider(true);
        NilaiResikoTotal.setName("NilaiResikoTotal"); // NOI18N
        FormInput.add(NilaiResikoTotal);
        NilaiResikoTotal.setBounds(814, 2050, 40, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 2080, 880, 1);

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setText("X. PENILAIAN TINGKAT NYERI");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(10, 2080, 380, 23);

        jLabel182.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel182.setText("Skala FLACCS :");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(44, 2100, 210, 23);

        jLabel183.setText("Wajah :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(48, 2120, 60, 23);

        SkalaWajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tersenyum/tidak ada ekspresi khusus", "Terkadang meringis/menarik diri", "Sering menggetarkan dagu dan mengatupkan rahang" }));
        SkalaWajah.setName("SkalaWajah"); // NOI18N
        SkalaWajah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaWajahItemStateChanged(evt);
            }
        });
        SkalaWajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaWajahKeyPressed(evt);
            }
        });
        FormInput.add(SkalaWajah);
        SkalaWajah.setBounds(112, 2120, 310, 23);

        NilaiWajah.setEditable(false);
        NilaiWajah.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiWajah.setText("0");
        NilaiWajah.setFocusTraversalPolicyProvider(true);
        NilaiWajah.setName("NilaiWajah"); // NOI18N
        FormInput.add(NilaiWajah);
        NilaiWajah.setBounds(426, 2120, 40, 23);

        jLabel184.setText("Menangis :");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(480, 2120, 60, 23);

        SkalaMenangis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak menangis (mudah bergerak)", "Mengerang/merengek", "Menangis terus menerus, terisak, menjerit" }));
        SkalaMenangis.setName("SkalaMenangis"); // NOI18N
        SkalaMenangis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaMenangisItemStateChanged(evt);
            }
        });
        SkalaMenangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaMenangisKeyPressed(evt);
            }
        });
        FormInput.add(SkalaMenangis);
        SkalaMenangis.setBounds(544, 2120, 266, 23);

        NilaiMenangis.setEditable(false);
        NilaiMenangis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiMenangis.setText("0");
        NilaiMenangis.setFocusTraversalPolicyProvider(true);
        NilaiMenangis.setName("NilaiMenangis"); // NOI18N
        FormInput.add(NilaiMenangis);
        NilaiMenangis.setBounds(814, 2120, 40, 23);

        jLabel185.setText("Kaki :");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(48, 2150, 60, 23);

        SkalaKaki.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gerakan normal/relaksasi", "Tidak tenang/tegang", "Kaki dibuat menendang/menarik" }));
        SkalaKaki.setName("SkalaKaki"); // NOI18N
        SkalaKaki.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaKakiItemStateChanged(evt);
            }
        });
        SkalaKaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaKakiKeyPressed(evt);
            }
        });
        FormInput.add(SkalaKaki);
        SkalaKaki.setBounds(112, 2150, 310, 23);

        NilaiKaki.setEditable(false);
        NilaiKaki.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiKaki.setText("0");
        NilaiKaki.setFocusTraversalPolicyProvider(true);
        NilaiKaki.setName("NilaiKaki"); // NOI18N
        FormInput.add(NilaiKaki);
        NilaiKaki.setBounds(426, 2150, 40, 23);

        jLabel186.setText("Bersuara :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(480, 2150, 60, 23);

        SkalaBersuara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersuara normal/tenang", "Tenang bila dipeluk, digendong/diajak bicara", "Sulit untuk menenangkan" }));
        SkalaBersuara.setName("SkalaBersuara"); // NOI18N
        SkalaBersuara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaBersuaraItemStateChanged(evt);
            }
        });
        SkalaBersuara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaBersuaraKeyPressed(evt);
            }
        });
        FormInput.add(SkalaBersuara);
        SkalaBersuara.setBounds(544, 2150, 266, 23);

        NilaiBersuara.setEditable(false);
        NilaiBersuara.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiBersuara.setText("0");
        NilaiBersuara.setFocusTraversalPolicyProvider(true);
        NilaiBersuara.setName("NilaiBersuara"); // NOI18N
        FormInput.add(NilaiBersuara);
        NilaiBersuara.setBounds(814, 2150, 40, 23);

        jLabel187.setText("Aktifitas :");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(48, 2180, 60, 23);

        SkalaAktifitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidur posisi normal, mudah bergerak", "Gerakan menggeliat/berguling, kaku", "Melengkungkan punggung/kaku menghentak" }));
        SkalaAktifitas.setName("SkalaAktifitas"); // NOI18N
        SkalaAktifitas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaAktifitasItemStateChanged(evt);
            }
        });
        SkalaAktifitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaAktifitasKeyPressed(evt);
            }
        });
        FormInput.add(SkalaAktifitas);
        SkalaAktifitas.setBounds(112, 2180, 310, 23);

        NilaiAktifitas.setEditable(false);
        NilaiAktifitas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiAktifitas.setText("0");
        NilaiAktifitas.setFocusTraversalPolicyProvider(true);
        NilaiAktifitas.setName("NilaiAktifitas"); // NOI18N
        FormInput.add(NilaiAktifitas);
        NilaiAktifitas.setBounds(426, 2180, 40, 23);

        jLabel188.setText("Skala nyeri :");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(680, 2180, 90, 23);

        SkalaNyeri.setEditable(false);
        SkalaNyeri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        SkalaNyeri.setText("0");
        SkalaNyeri.setFocusTraversalPolicyProvider(true);
        SkalaNyeri.setName("SkalaNyeri"); // NOI18N
        FormInput.add(SkalaNyeri);
        SkalaNyeri.setBounds(774, 2180, 80, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(44, 2215, 320, 115);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(365, 2210, 1, 125);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 2215, 140, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(604, 2215, 46, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(654, 2215, 200, 23);

        jLabel88.setText("Frekuensi :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(600, 2245, 100, 23);

        Frekuensi.setFocusTraversalPolicyProvider(true);
        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(704, 2245, 150, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(417, 2245, 150, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Nyeri hilang bila");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(375, 2275, 93, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minum Obat", "Istirahat", "Mendengar Music", "Berubah Posisi/Tidur", "Lain-lain" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(465, 2275, 165, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(636, 2275, 218, 23);

        jLabel86.setText("Diberitahukan pada dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(381, 2305, 190, 23);

        NyeriPadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        NyeriPadaDokter.setName("NyeriPadaDokter"); // NOI18N
        NyeriPadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriPadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(NyeriPadaDokter);
        NyeriPadaDokter.setBounds(575, 2305, 80, 23);

        jLabel84.setText("Jam diberitahukan :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(660, 2305, 110, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Durasi");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(375, 2245, 45, 23);

        NyeriKetDokter.setFocusTraversalPolicyProvider(true);
        NyeriKetDokter.setName("NyeriKetDokter"); // NOI18N
        NyeriKetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKetDokterKeyPressed(evt);
            }
        });
        FormInput.add(NyeriKetDokter);
        NyeriKetDokter.setBounds(774, 2305, 80, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 2335, 880, 1);

        jLabel90.setText(":");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(375, 2245, 38, 23);

        jLabel91.setText(":");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(375, 2275, 86, 23);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel272.setText("XI. PERENCANAAN PULANG (DISCHARGE PLANNING)");
        jLabel272.setName("jLabel272"); // NOI18N
        FormInput.add(jLabel272);
        jLabel272.setBounds(10, 2335, 380, 23);

        InformasiPerencanaanPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        InformasiPerencanaanPulang.setName("InformasiPerencanaanPulang"); // NOI18N
        InformasiPerencanaanPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiPerencanaanPulangKeyPressed(evt);
            }
        });
        FormInput.add(InformasiPerencanaanPulang);
        InformasiPerencanaanPulang.setBounds(344, 2355, 80, 23);

        jLabel250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel250.setText("Pendamping/Wali Diberikan Informasi Perencanaan Pulang");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(44, 2355, 330, 23);

        jLabel257.setText("?");
        jLabel257.setName("jLabel257"); // NOI18N
        FormInput.add(jLabel257);
        jLabel257.setBounds(0, 2355, 340, 23);

        label29.setText("Perencanaan Pulang :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(630, 2355, 130, 23);

        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2024" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPulangKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPulang);
        TanggalPulang.setBounds(764, 2355, 90, 23);

        jLabel261.setText("Lama Rawat Rata-rata :");
        jLabel261.setName("jLabel261"); // NOI18N
        FormInput.add(jLabel261);
        jLabel261.setBounds(440, 2355, 130, 23);

        LamaRatarata.setFocusTraversalPolicyProvider(true);
        LamaRatarata.setName("LamaRatarata"); // NOI18N
        LamaRatarata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaRatarataKeyPressed(evt);
            }
        });
        FormInput.add(LamaRatarata);
        LamaRatarata.setBounds(574, 2355, 55, 23);

        jLabel260.setText(":");
        jLabel260.setName("jLabel260"); // NOI18N
        FormInput.add(jLabel260);
        jLabel260.setBounds(0, 2385, 176, 23);

        KondisiPulang.setFocusTraversalPolicyProvider(true);
        KondisiPulang.setName("KondisiPulang"); // NOI18N
        KondisiPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiPulangKeyPressed(evt);
            }
        });
        FormInput.add(KondisiPulang);
        KondisiPulang.setBounds(180, 2385, 674, 23);

        jLabel259.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel259.setText("Kondisi Klinis Saat Pulang");
        jLabel259.setName("jLabel259"); // NOI18N
        FormInput.add(jLabel259);
        jLabel259.setBounds(44, 2385, 180, 23);

        jLabel262.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel262.setText("Perawatan Lanjutan Yang Diberikan Di Rumah");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(44, 2415, 240, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        PerawatanLanjutan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PerawatanLanjutan.setColumns(20);
        PerawatanLanjutan.setRows(5);
        PerawatanLanjutan.setName("PerawatanLanjutan"); // NOI18N
        PerawatanLanjutan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanLanjutanKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(PerawatanLanjutan);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(278, 2415, 576, 43);

        jLabel263.setText(":");
        jLabel263.setName("jLabel263"); // NOI18N
        FormInput.add(jLabel263);
        jLabel263.setBounds(0, 2415, 274, 23);

        CaraTransportasiPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu Sebagian", "Dibantu Keseluruhan", "Menggunakan Rostul", "Menggunakan Brankar", "Berjalan" }));
        CaraTransportasiPulang.setName("CaraTransportasiPulang"); // NOI18N
        CaraTransportasiPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraTransportasiPulangKeyPressed(evt);
            }
        });
        FormInput.add(CaraTransportasiPulang);
        CaraTransportasiPulang.setBounds(178, 2465, 160, 23);

        jLabel264.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel264.setText("Cara Transportasi Pulang");
        jLabel264.setName("jLabel264"); // NOI18N
        FormInput.add(jLabel264);
        jLabel264.setBounds(44, 2465, 140, 23);

        jLabel265.setText(":");
        jLabel265.setName("jLabel265"); // NOI18N
        FormInput.add(jLabel265);
        jLabel265.setBounds(0, 2465, 174, 23);

        jLabel266.setText("Transportasi Yang Digunakan :");
        jLabel266.setName("jLabel266"); // NOI18N
        FormInput.add(jLabel266);
        jLabel266.setBounds(340, 2465, 167, 23);

        TransportasiYangDigunakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kendaraan Pribadi", "Mobil Ambulance", "Kendaraan Umum" }));
        TransportasiYangDigunakan.setName("TransportasiYangDigunakan"); // NOI18N
        TransportasiYangDigunakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiYangDigunakanKeyPressed(evt);
            }
        });
        FormInput.add(TransportasiYangDigunakan);
        TransportasiYangDigunakan.setBounds(511, 2465, 140, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2024" }));
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
        if(TNoRM.getText().trim().equals("")){
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
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString(),Sequel.ambiltanggalsekarang())==true){
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
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString(),Sequel.ambiltanggalsekarang())==true){
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
                    "select penilaian_awal_keperawatan_ranap_bayi.no_rawat,penilaian_awal_keperawatan_ranap_bayi.tanggal,penilaian_awal_keperawatan_ranap_bayi.informasi,penilaian_awal_keperawatan_ranap_bayi.ket_informasi,penilaian_awal_keperawatan_ranap_bayi.tiba_diruang_rawat,"+
                    "penilaian_awal_keperawatan_ranap_bayi.kasus_trauma,penilaian_awal_keperawatan_ranap_bayi.cara_masuk,penilaian_awal_keperawatan_ranap_bayi.rps,penilaian_awal_keperawatan_ranap_bayi.rpd,penilaian_awal_keperawatan_ranap_bayi.rpk,penilaian_awal_keperawatan_ranap_bayi.rpo,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_pembedahan,penilaian_awal_keperawatan_ranap_bayi.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap_bayi.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap_bayi.riwayat_tranfusi,penilaian_awal_keperawatan_ranap_bayi.riwayat_alergi,penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_narkoba,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_olahraga,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_mental,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gcs,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_td,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_rr,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_suhu,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_bb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_tb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_pola_nafas,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_volume_pernafasan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_irama_nafas,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_peistatik_usus,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_motorik,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_turgor,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_lainlain,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpakaian,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_porsi_makan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap_bayi.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_aktifitas,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ambulasi,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kesimpulan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan_keterangan,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_kualitas,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_skala,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_diberitahukan_dokter,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai1,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala3,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai4,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala6,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala1,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai2,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala4,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai5,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala7,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai8,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala10,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai11,"+
                    "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi1,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi1,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi2,"+
                    "penilaian_awal_keperawatan_ranap_bayi.nilai_gizi2,penilaian_awal_keperawatan_ranap_bayi.nilai_total_gizi,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_ket_diagnosa_khusus,"+
                    "penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.rencana,penilaian_awal_keperawatan_ranap_bayi.nip1,"+
                    "penilaian_awal_keperawatan_ranap_bayi.nip2,penilaian_awal_keperawatan_ranap_bayi.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                    "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join penilaian_awal_keperawatan_ranap_bayi on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_bayi.no_rawat "+
                    "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_bayi.nip1=pengkaji1.nip "+
                    "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_bayi.nip2=pengkaji2.nip "+
                    "inner join dokter on penilaian_awal_keperawatan_ranap_bayi.kd_dokter=dokter.kd_dokter "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                    "penilaian_awal_keperawatan_ranap_bayi.tanggal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap_bayi.nip1 like ? or "+
                    "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap_bayi.kd_dokter like ? or dokter.nm_dokter like ?)")+
                    " order by penilaian_awal_keperawatan_ranap_bayi.tanggal");

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
                    "inner join penilaian_awal_keperawatan_ranap_bayi_masalah on penilaian_awal_keperawatan_ranap_bayi_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                    "where penilaian_awal_keperawatan_ranap_bayi_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_bayi_masalah.kode_masalah");
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
                    "inner join penilaian_awal_keperawatan_ranap_bayi_rencana on penilaian_awal_keperawatan_ranap_bayi_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
                    "where penilaian_awal_keperawatan_ranap_bayi_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_bayi_rencana.kode_rencana");
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
                "select penilaian_awal_keperawatan_ranap_bayi.no_rawat,penilaian_awal_keperawatan_ranap_bayi.tanggal,penilaian_awal_keperawatan_ranap_bayi.informasi,penilaian_awal_keperawatan_ranap_bayi.ket_informasi,penilaian_awal_keperawatan_ranap_bayi.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap_bayi.kasus_trauma,penilaian_awal_keperawatan_ranap_bayi.cara_masuk,penilaian_awal_keperawatan_ranap_bayi.rps,penilaian_awal_keperawatan_ranap_bayi.rpd,penilaian_awal_keperawatan_ranap_bayi.rpk,penilaian_awal_keperawatan_ranap_bayi.rpo,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_pembedahan,penilaian_awal_keperawatan_ranap_bayi.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap_bayi.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap_bayi.riwayat_tranfusi,penilaian_awal_keperawatan_ranap_bayi.riwayat_alergi,penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_olahraga,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_mental,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_td,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_rr,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_suhu,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_bb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_tb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_pola_nafas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_volume_pernafasan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_irama_nafas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_peistatik_usus,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_motorik,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_turgor,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_lainlain,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpakaian,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_porsi_makan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap_bayi.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_aktifitas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ambulasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kesimpulan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_kualitas,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_skala,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai1,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala3,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai4,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala6,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala1,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai2,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala4,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai5,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala7,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai8,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala10,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai11,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi1,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi1,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi2,"+
                "penilaian_awal_keperawatan_ranap_bayi.nilai_gizi2,penilaian_awal_keperawatan_ranap_bayi.nilai_total_gizi,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.rencana,penilaian_awal_keperawatan_ranap_bayi.nip1,"+
                "penilaian_awal_keperawatan_ranap_bayi.nip2,penilaian_awal_keperawatan_ranap_bayi.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap_bayi on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_bayi.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_bayi.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_bayi.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap_bayi.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRanap2.jasper","report","::[ Laporan Penilaian Awal Keperawatan Rawat Inap ]::",
                "select penilaian_awal_keperawatan_ranap_bayi.no_rawat,penilaian_awal_keperawatan_ranap_bayi.tanggal,penilaian_awal_keperawatan_ranap_bayi.informasi,penilaian_awal_keperawatan_ranap_bayi.ket_informasi,penilaian_awal_keperawatan_ranap_bayi.tiba_diruang_rawat,"+
                "penilaian_awal_keperawatan_ranap_bayi.kasus_trauma,penilaian_awal_keperawatan_ranap_bayi.cara_masuk,penilaian_awal_keperawatan_ranap_bayi.rps,penilaian_awal_keperawatan_ranap_bayi.rpd,penilaian_awal_keperawatan_ranap_bayi.rpk,penilaian_awal_keperawatan_ranap_bayi.rpo,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_pembedahan,penilaian_awal_keperawatan_ranap_bayi.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap_bayi.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap_bayi.riwayat_tranfusi,penilaian_awal_keperawatan_ranap_bayi.riwayat_alergi,penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol,penilaian_awal_keperawatan_ranap_bayi.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap_bayi.riwayat_narkoba,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_olahraga,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_mental,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gcs,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_td,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_rr,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_suhu,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_bb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_tb,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_wajah_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_pola_nafas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_volume_pernafasan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_irama_nafas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_peistatik_usus,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_motorik,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_turgor,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap_bayi.pemeriksaan_eliminasi_bak_lainlain,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpakaian,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap_bayi.pola_nutrisi_porsi_makan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap_bayi.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_aktifitas,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ambulasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_menggenggam_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.pengkajian_fungsi_kesimpulan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap_bayi.riwayat_psiko_edukasi_diberikan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_kualitas,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_skala,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_diberitahukan_dokter,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai1,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala3,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai4,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_skala6,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala1,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai2,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala4,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai5,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala7,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai8,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala10,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_nilai11,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi1,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi1,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi2,"+
                "penilaian_awal_keperawatan_ranap_bayi.nilai_gizi2,penilaian_awal_keperawatan_ranap_bayi.nilai_total_gizi,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_ket_diagnosa_khusus,"+
                "penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap_bayi.rencana,penilaian_awal_keperawatan_ranap_bayi.nip1,"+
                "penilaian_awal_keperawatan_ranap_bayi.nip2,penilaian_awal_keperawatan_ranap_bayi.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pekerjaan,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap_bayi on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_bayi.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_bayi.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_bayi.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap_bayi.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,KetAnamnesis,RPS);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TibadiRuang,KetAnamnesis);
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
        //Valid.pindah(evt,BtnPetugas2,MacamKasus);
    }//GEN-LAST:event_BtnDPJPKeyPressed

    private void TibadiRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TibadiRuangKeyPressed
        Valid.pindah(evt,BtnDPJP,Anamnesis);
    }//GEN-LAST:event_TibadiRuangKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,TglAsuhan,RPD);
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

    private void KetAnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAnamnesisKeyPressed
        Valid.pindah(evt,Anamnesis,TglAsuhan);
    }//GEN-LAST:event_KetAnamnesisKeyPressed

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
            TransportasiYangDigunakan.requestFocus();
        }
    }//GEN-LAST:event_BtnCariMasalahKeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah2();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TransportasiYangDigunakan.requestFocus();
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

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,RPO,UsiaTengkurap);
    }//GEN-LAST:event_AlergiKeyPressed

    private void BtnTambahImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahImunisasiActionPerformed
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dimasukkan data riwayat imunisasinya...");
            Anamnesis.requestFocus();
        }else{
            KdImunisasi.setText("");
            NmImunisasi.setText("");
            ImunisasiKe.setSelectedIndex(0);
            BtnImunisasi.setEnabled(true);
            BtnSimpanImunisasi.setVisible(true);
            BtnHapusImunisasi.setVisible(false);
            DlgRiwayatImunisasi.setLocationRelativeTo(internalFrame1);
            DlgRiwayatImunisasi.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahImunisasiActionPerformed

    private void BtnPanggilHapusImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanggilHapusImunisasiActionPerformed
        if(tbImunisasi.getSelectedRow()>-1){
            if(TNoRM.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dihapus data riwayat imunisasinya...");
                tbImunisasi.requestFocus();
            }else{
                BtnImunisasi.setEnabled(false);
                KdImunisasi.setText(tbImunisasi.getValueAt(tbImunisasi.getSelectedRow(),0).toString());
                NmImunisasi.setText(tbImunisasi.getValueAt(tbImunisasi.getSelectedRow(),1).toString());
                BtnSimpanImunisasi.setVisible(false);
                BtnHapusImunisasi.setVisible(true);
                DlgRiwayatImunisasi.setLocationRelativeTo(internalFrame1);
                DlgRiwayatImunisasi.setVisible(true);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnPanggilHapusImunisasiActionPerformed

    private void UsiaTengkurapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaTengkurapKeyPressed
        Valid.pindah(evt,Alergi,UsiaDuduk);
    }//GEN-LAST:event_UsiaTengkurapKeyPressed

    private void UsiaDudukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaDudukKeyPressed
        Valid.pindah(evt,UsiaTengkurap,UsiaBerdiri);
    }//GEN-LAST:event_UsiaDudukKeyPressed

    private void UsiaBerdiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaBerdiriKeyPressed
        Valid.pindah(evt,UsiaDuduk,UsiaGigi);
    }//GEN-LAST:event_UsiaBerdiriKeyPressed

    private void UsiaGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaGigiKeyPressed
        Valid.pindah(evt,UsiaBerdiri,UsiaBerjalan);
    }//GEN-LAST:event_UsiaGigiKeyPressed

    private void UsiaBerjalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaBerjalanKeyPressed
        Valid.pindah(evt,UsiaGigi,UsiaBicara);
    }//GEN-LAST:event_UsiaBerjalanKeyPressed

    private void UsiaBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaBicaraKeyPressed
        Valid.pindah(evt,UsiaBerjalan,UsiaMembaca);
    }//GEN-LAST:event_UsiaBicaraKeyPressed

    private void UsiaMembacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaMembacaKeyPressed
        Valid.pindah(evt,UsiaBicara,UsiaMenulis);
    }//GEN-LAST:event_UsiaMembacaKeyPressed

    private void UsiaMenulisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaMenulisKeyPressed
        Valid.pindah(evt,UsiaMembaca,GangguanEmosi);
    }//GEN-LAST:event_UsiaMenulisKeyPressed

    private void GangguanEmosiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanEmosiKeyPressed
        Valid.pindah(evt,UsiaMenulis,Anakke);
    }//GEN-LAST:event_GangguanEmosiKeyPressed

    private void AnakkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnakkeKeyPressed
        Valid.pindah(evt,GangguanEmosi,DariSaudara);
    }//GEN-LAST:event_AnakkeKeyPressed

    private void DariSaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DariSaudaraKeyPressed
        Valid.pindah(evt,Anakke,UmurKelahiran);
    }//GEN-LAST:event_DariSaudaraKeyPressed

    private void CaraKelahiranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKelahiranKeyPressed
        Valid.pindah(evt,UmurKelahiran,KetCaraKelahiran);
    }//GEN-LAST:event_CaraKelahiranKeyPressed

    private void KetCaraKelahiranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetCaraKelahiranKeyPressed
        Valid.pindah(evt,CaraKelahiran,KelainanBawaan);
    }//GEN-LAST:event_KetCaraKelahiranKeyPressed

    private void KelainanBawaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelainanBawaanKeyPressed
        Valid.pindah(evt,KetCaraKelahiran,KetKelainanBawaan);
    }//GEN-LAST:event_KelainanBawaanKeyPressed

    private void KetKelainanBawaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKelainanBawaanKeyPressed
        Valid.pindah(evt,KelainanBawaan,BBLahir);
    }//GEN-LAST:event_KetKelainanBawaanKeyPressed

    private void UmurKelahiranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurKelahiranKeyPressed
        Valid.pindah(evt,DariSaudara,CaraKelahiran);
    }//GEN-LAST:event_UmurKelahiranKeyPressed

    private void BBLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBLahirKeyPressed
        Valid.pindah(evt,KetKelainanBawaan,PBLahir);
    }//GEN-LAST:event_BBLahirKeyPressed

    private void PBLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PBLahirKeyPressed
        Valid.pindah(evt,BBLahir,RiwayatPersalinanLainnya);
    }//GEN-LAST:event_PBLahirKeyPressed

    private void RiwayatPersalinanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPersalinanLainnyaKeyPressed
        Valid.pindah(evt,PBLahir,Kesadaran);
    }//GEN-LAST:event_RiwayatPersalinanLainnyaKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,GCS,RR);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        Valid.pindah(evt,Kesadaran,TD);
    }//GEN-LAST:event_GCSKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,LP);
    }//GEN-LAST:event_TBKeyPressed

    private void LKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LKKeyPressed
        Valid.pindah(evt,LP,LD);
    }//GEN-LAST:event_LKKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,Nadi,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        Valid.pindah(evt,TB,LK);
    }//GEN-LAST:event_LPKeyPressed

    private void LDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LDKeyPressed
        Valid.pindah(evt,LK,SistemSarafKepala);
    }//GEN-LAST:event_LDKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,RiwayatPersalinanLainnya,GCS);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void SistemSarafKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafKepalaKeyPressed
        Valid.pindah(evt,LD,KetSistemSarafKepala);
    }//GEN-LAST:event_SistemSarafKepalaKeyPressed

    private void KetSistemSarafKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafKepalaKeyPressed
        Valid.pindah(evt,SistemSarafKepala,SistemSarafWajah);
    }//GEN-LAST:event_KetSistemSarafKepalaKeyPressed

    private void SistemSarafWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafWajahKeyPressed
        Valid.pindah(evt,KetSistemSarafKepala,KetSistemSarafWajah);
    }//GEN-LAST:event_SistemSarafWajahKeyPressed

    private void KetSistemSarafWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafWajahKeyPressed
        Valid.pindah(evt,SistemSarafWajah,SistemSarafLeher);
    }//GEN-LAST:event_KetSistemSarafWajahKeyPressed

    private void SistemSarafLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafLeherKeyPressed
        Valid.pindah(evt,KetSistemSarafWajah,SistemSarafKejang);
    }//GEN-LAST:event_SistemSarafLeherKeyPressed

    private void SistemSarafKejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafKejangKeyPressed
        Valid.pindah(evt,SistemSarafLeher,KetSistemSarafKejang);
    }//GEN-LAST:event_SistemSarafKejangKeyPressed

    private void KetSistemSarafKejangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSistemSarafKejangKeyPressed
        Valid.pindah(evt,SistemSarafKejang,SistemSarafSensorik);
    }//GEN-LAST:event_KetSistemSarafKejangKeyPressed

    private void SistemSarafSensorikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistemSarafSensorikKeyPressed
        Valid.pindah(evt,KetSistemSarafKejang,KardiovaskularPulsasi);
    }//GEN-LAST:event_SistemSarafSensorikKeyPressed

    private void KardiovaskularPulsasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularPulsasiKeyPressed
        Valid.pindah(evt,SistemSarafSensorik,KardiovaskularSirkulasi);
    }//GEN-LAST:event_KardiovaskularPulsasiKeyPressed

    private void KardiovaskularSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularSirkulasiKeyPressed
        Valid.pindah(evt,KardiovaskularPulsasi,KetKardiovaskularSirkulasi);
    }//GEN-LAST:event_KardiovaskularSirkulasiKeyPressed

    private void KetKardiovaskularSirkulasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKardiovaskularSirkulasiKeyPressed
        Valid.pindah(evt,KardiovaskularSirkulasi,KardiovaskularDenyutNadi);
    }//GEN-LAST:event_KetKardiovaskularSirkulasiKeyPressed

    private void KardiovaskularDenyutNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiovaskularDenyutNadiKeyPressed
        Valid.pindah(evt,KetKardiovaskularSirkulasi,RespirasiRetraksi);
    }//GEN-LAST:event_KardiovaskularDenyutNadiKeyPressed

    private void RespirasiRetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiRetraksiKeyPressed
        Valid.pindah(evt,KardiovaskularDenyutNadi,RespirasiPolaNafas);
    }//GEN-LAST:event_RespirasiRetraksiKeyPressed

    private void RespirasiPolaNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiPolaNafasKeyPressed
        Valid.pindah(evt,RespirasiRetraksi,RespirasiSuaraNafas);
    }//GEN-LAST:event_RespirasiPolaNafasKeyPressed

    private void RespirasiSuaraNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiSuaraNafasKeyPressed
        Valid.pindah(evt,RespirasiPolaNafas,RespirasiBatuk);
    }//GEN-LAST:event_RespirasiSuaraNafasKeyPressed

    private void RespirasiBatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiBatukKeyPressed
        Valid.pindah(evt,RespirasiSuaraNafas,RespirasiVolume);
    }//GEN-LAST:event_RespirasiBatukKeyPressed

    private void RespirasiVolumeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiVolumeKeyPressed
        Valid.pindah(evt,RespirasiBatuk,RespirasiJenisPernafasan);
    }//GEN-LAST:event_RespirasiVolumeKeyPressed

    private void RespirasiJenisPernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiJenisPernafasanKeyPressed
        Valid.pindah(evt,RespirasiVolume,KetRespirasiJenisPernafasan);
    }//GEN-LAST:event_RespirasiJenisPernafasanKeyPressed

    private void KetRespirasiJenisPernafasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetRespirasiJenisPernafasanKeyPressed
        Valid.pindah(evt,RespirasiJenisPernafasan,RespirasiIrama);
    }//GEN-LAST:event_KetRespirasiJenisPernafasanKeyPressed

    private void RespirasiIramaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiIramaKeyPressed
        Valid.pindah(evt,KetRespirasiJenisPernafasan,GastrointestinalMulut);
    }//GEN-LAST:event_RespirasiIramaKeyPressed

    private void GastrointestinalMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalMulutKeyPressed
        Valid.pindah(evt,RespirasiIrama,KetGastrointestinalMulut);
    }//GEN-LAST:event_GastrointestinalMulutKeyPressed

    private void KetGastrointestinalMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalMulutKeyPressed
        Valid.pindah(evt,GastrointestinalMulut,GastrointestinalLidah);
    }//GEN-LAST:event_KetGastrointestinalMulutKeyPressed

    private void GastrointestinalTenggorakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalTenggorakanKeyPressed
        Valid.pindah(evt,KetGastrointestinalGigi,KetGastrointestinalTenggorakan);
    }//GEN-LAST:event_GastrointestinalTenggorakanKeyPressed

    private void KetGastrointestinalTenggorakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalTenggorakanKeyPressed
        Valid.pindah(evt,GastrointestinalTenggorakan,GastrointestinalAbdomen);
    }//GEN-LAST:event_KetGastrointestinalTenggorakanKeyPressed

    private void GastrointestinalLidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalLidahKeyPressed
        Valid.pindah(evt,KetGastrointestinalMulut,KetGastrointestinalLidah);
    }//GEN-LAST:event_GastrointestinalLidahKeyPressed

    private void KetGastrointestinalLidahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalLidahKeyPressed
        Valid.pindah(evt,GastrointestinalLidah,GastrointestinalGigi);
    }//GEN-LAST:event_KetGastrointestinalLidahKeyPressed

    private void GastrointestinalAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalAbdomenKeyPressed
        Valid.pindah(evt,KetGastrointestinalTenggorakan,KetGastrointestinalAbdomen);
    }//GEN-LAST:event_GastrointestinalAbdomenKeyPressed

    private void KetGastrointestinalAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalAbdomenKeyPressed
        Valid.pindah(evt,GastrointestinalAbdomen,GastrointestinalUsus);
    }//GEN-LAST:event_KetGastrointestinalAbdomenKeyPressed

    private void GastrointestinalGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalGigiKeyPressed
        Valid.pindah(evt,KetGastrointestinalLidah,KetGastrointestinalGigi);
    }//GEN-LAST:event_GastrointestinalGigiKeyPressed

    private void KetGastrointestinalGigiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetGastrointestinalGigiKeyPressed
        Valid.pindah(evt,GastrointestinalGigi,GastrointestinalTenggorakan);
    }//GEN-LAST:event_KetGastrointestinalGigiKeyPressed

    private void GastrointestinalUsusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalUsusKeyPressed
        Valid.pindah(evt,KetGastrointestinalGigi,GastrointestinalAnus);
    }//GEN-LAST:event_GastrointestinalUsusKeyPressed

    private void GastrointestinalAnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GastrointestinalAnusKeyPressed
        Valid.pindah(evt,GastrointestinalUsus,NeurologiSensorik);
    }//GEN-LAST:event_GastrointestinalAnusKeyPressed

    private void NeurologiSensorikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiSensorikKeyPressed
        Valid.pindah(evt,GastrointestinalAnus,NeurologiPenglihatan);
    }//GEN-LAST:event_NeurologiSensorikKeyPressed

    private void NeurologiPenglihatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiPenglihatanKeyPressed
        Valid.pindah(evt,NeurologiSensorik,KetNeurologiPenglihatan);
    }//GEN-LAST:event_NeurologiPenglihatanKeyPressed

    private void KetNeurologiPenglihatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiPenglihatanKeyPressed
        Valid.pindah(evt,NeurologiPenglihatan,NeurologiAlatBantuPenglihatan);
    }//GEN-LAST:event_KetNeurologiPenglihatanKeyPressed

    private void NeurologiAlatBantuPenglihatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiAlatBantuPenglihatanKeyPressed
        Valid.pindah(evt,KetNeurologiPenglihatan,NeurologiMotorik);
    }//GEN-LAST:event_NeurologiAlatBantuPenglihatanKeyPressed

    private void NeurologiMotorikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiMotorikKeyPressed
        Valid.pindah(evt,NeurologiAlatBantuPenglihatan,NeurologiPendengaran);
    }//GEN-LAST:event_NeurologiMotorikKeyPressed

    private void NeurologiPendengaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiPendengaranKeyPressed
        Valid.pindah(evt,NeurologiMotorik,NeurologiBicara);
    }//GEN-LAST:event_NeurologiPendengaranKeyPressed

    private void NeurologiBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiBicaraKeyPressed
        Valid.pindah(evt,NeurologiPendengaran,KetNeurologiBicara);
    }//GEN-LAST:event_NeurologiBicaraKeyPressed

    private void KetNeurologiBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNeurologiBicaraKeyPressed
        Valid.pindah(evt,NeurologiBicara,NeurologiOtot);
    }//GEN-LAST:event_KetNeurologiBicaraKeyPressed

    private void NeurologiOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NeurologiOtotKeyPressed
        Valid.pindah(evt,KetNeurologiBicara,IntegumentKulit);
    }//GEN-LAST:event_NeurologiOtotKeyPressed

    private void IntegumentKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentKulitKeyPressed
        Valid.pindah(evt,NeurologiOtot,IntegumentWarnaKulit);
    }//GEN-LAST:event_IntegumentKulitKeyPressed

    private void IntegumentWarnaKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentWarnaKulitKeyPressed
        Valid.pindah(evt,IntegumentKulit,IntegumentTurgor);
    }//GEN-LAST:event_IntegumentWarnaKulitKeyPressed

    private void IntegumentTurgorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentTurgorKeyPressed
        Valid.pindah(evt,IntegumentWarnaKulit,IntegumentDecubitus);
    }//GEN-LAST:event_IntegumentTurgorKeyPressed

    private void IntegumentDecubitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntegumentDecubitusKeyPressed
        Valid.pindah(evt,IntegumentTurgor,MuskuloskletalOedema);
    }//GEN-LAST:event_IntegumentDecubitusKeyPressed

    private void MuskuloskletalOedemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalOedemaKeyPressed
        Valid.pindah(evt,IntegumentDecubitus,KetMuskuloskletalOedema);
    }//GEN-LAST:event_MuskuloskletalOedemaKeyPressed

    private void KetMuskuloskletalOedemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMuskuloskletalOedemaKeyPressed
        Valid.pindah(evt,MuskuloskletalOedema,MuskuloskletalPegerakanSendi);
    }//GEN-LAST:event_KetMuskuloskletalOedemaKeyPressed

    private void MuskuloskletalPegerakanSendiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalPegerakanSendiKeyPressed
        Valid.pindah(evt,KetMuskuloskletalOedema,MuskuloskletalKekuatanOtot);
    }//GEN-LAST:event_MuskuloskletalPegerakanSendiKeyPressed

    private void MuskuloskletalKekuatanOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalKekuatanOtotKeyPressed
        Valid.pindah(evt,MuskuloskletalPegerakanSendi,MuskuloskletalFraktur);
    }//GEN-LAST:event_MuskuloskletalKekuatanOtotKeyPressed

    private void MuskuloskletalFrakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalFrakturKeyPressed
        Valid.pindah(evt,MuskuloskletalKekuatanOtot,KetMuskuloskletalFraktur);
    }//GEN-LAST:event_MuskuloskletalFrakturKeyPressed

    private void KetMuskuloskletalFrakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMuskuloskletalFrakturKeyPressed
        Valid.pindah(evt,MuskuloskletalFraktur,MuskuloskletalNyeriSendi);
    }//GEN-LAST:event_KetMuskuloskletalFrakturKeyPressed

    private void MuskuloskletalNyeriSendiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskuloskletalNyeriSendiKeyPressed
        Valid.pindah(evt,KetMuskuloskletalFraktur,KetMuskuloskletalNyeriSendi);
    }//GEN-LAST:event_MuskuloskletalNyeriSendiKeyPressed

    private void KetMuskuloskletalNyeriSendiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetMuskuloskletalNyeriSendiKeyPressed
        Valid.pindah(evt,KetMuskuloskletalNyeriSendi,BAB);
    }//GEN-LAST:event_KetMuskuloskletalNyeriSendiKeyPressed

    private void BABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BABKeyPressed
        Valid.pindah(evt,KetMuskuloskletalNyeriSendi,XBAB);
    }//GEN-LAST:event_BABKeyPressed

    private void XBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XBABKeyPressed
        Valid.pindah(evt,BAB,KBAB);
    }//GEN-LAST:event_XBABKeyPressed

    private void KBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KBABKeyPressed
        Valid.pindah(evt,XBAB,WBAB);
    }//GEN-LAST:event_KBABKeyPressed

    private void WBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WBABKeyPressed
        Valid.pindah(evt,KBAB,BAK);
    }//GEN-LAST:event_WBABKeyPressed

    private void BAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BAKKeyPressed
        Valid.pindah(evt,WBAB,XBAK);
    }//GEN-LAST:event_BAKKeyPressed

    private void XBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_XBAKKeyPressed
        Valid.pindah(evt,BAK,WBAK);
    }//GEN-LAST:event_XBAKKeyPressed

    private void WBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WBAKKeyPressed
        Valid.pindah(evt,XBAK,LBAK);
    }//GEN-LAST:event_WBAKKeyPressed

    private void LBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LBAKKeyPressed
        Valid.pindah(evt,WBAK,KondisiPsikologis);
    }//GEN-LAST:event_LBAKKeyPressed

    private void KondisiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPsikologisKeyPressed
        Valid.pindah(evt,LBAK,AdakahPerilaku);
    }//GEN-LAST:event_KondisiPsikologisKeyPressed

    private void AdakahPerilakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdakahPerilakuKeyPressed
        Valid.pindah(evt,KondisiPsikologis,KeteranganAdakahPerilaku);
    }//GEN-LAST:event_AdakahPerilakuKeyPressed

    private void KeteranganAdakahPerilakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAdakahPerilakuKeyPressed
        Valid.pindah(evt,AdakahPerilaku,GangguanJiwa);
    }//GEN-LAST:event_KeteranganAdakahPerilakuKeyPressed

    private void GangguanJiwaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GangguanJiwaKeyPressed
        Valid.pindah(evt,KeteranganAdakahPerilaku,HubunganAnggotaKeluarga);
    }//GEN-LAST:event_GangguanJiwaKeyPressed

    private void HubunganAnggotaKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganAnggotaKeluargaKeyPressed
        Valid.pindah(evt,GangguanJiwa,TinggalDengan);
    }//GEN-LAST:event_HubunganAnggotaKeluargaKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganAnggotaKeluarga,KeteranganTinggalDengan);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KeteranganTinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTinggalDenganKeyPressed
        Valid.pindah(evt,TinggalDengan,PekerjaanPJ);
    }//GEN-LAST:event_KeteranganTinggalDenganKeyPressed

    private void NilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiKepercayaanKeyPressed
        Valid.pindah(evt,PekerjaanPJ,KeteranganNilaiKepercayaan);
    }//GEN-LAST:event_NilaiKepercayaanKeyPressed

    private void KeteranganNilaiKepercayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganNilaiKepercayaanKeyPressed
        Valid.pindah(evt,NilaiKepercayaan,PendidikanPJ);
    }//GEN-LAST:event_KeteranganNilaiKepercayaanKeyPressed

    private void PendidikanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanPJKeyPressed
        Valid.pindah(evt,KeteranganNilaiKepercayaan,EdukasiPsikolgis);
    }//GEN-LAST:event_PendidikanPJKeyPressed

    private void EdukasiPsikolgisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiPsikolgisKeyPressed
        Valid.pindah(evt,PendidikanPJ,KeteranganEdukasiPsikologis);
    }//GEN-LAST:event_EdukasiPsikolgisKeyPressed

    private void KeteranganEdukasiPsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEdukasiPsikologisKeyPressed
        Valid.pindah(evt,EdukasiPsikolgis,BahasaSehari);
    }//GEN-LAST:event_KeteranganEdukasiPsikologisKeyPressed

    private void BahasaSehariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaSehariKeyPressed
        Valid.pindah(evt,KeteranganEdukasiPsikologis,KemampuanBacaTulis);
    }//GEN-LAST:event_BahasaSehariKeyPressed

    private void KemampuanBacaTulisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemampuanBacaTulisKeyPressed
        Valid.pindah(evt,BahasaSehari,ButuhPenerjemah);
    }//GEN-LAST:event_KemampuanBacaTulisKeyPressed

    private void ButuhPenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButuhPenerjemahKeyPressed
        Valid.pindah(evt,KemampuanBacaTulis,KeteranganButuhPenerjemah);
    }//GEN-LAST:event_ButuhPenerjemahKeyPressed

    private void KeteranganButuhPenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganButuhPenerjemahKeyPressed
        Valid.pindah(evt,ButuhPenerjemah,TerdapatHambatanBelajar);
    }//GEN-LAST:event_KeteranganButuhPenerjemahKeyPressed

    private void TerdapatHambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerdapatHambatanBelajarKeyPressed
        Valid.pindah(evt,KeteranganButuhPenerjemah,HambatanBelajar);
    }//GEN-LAST:event_TerdapatHambatanBelajarKeyPressed

    private void HambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanBelajarKeyPressed
        Valid.pindah(evt,TerdapatHambatanBelajar,KeteranganHambatanBelajar);
    }//GEN-LAST:event_HambatanBelajarKeyPressed

    private void KeteranganHambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHambatanBelajarKeyPressed
        Valid.pindah(evt,HambatanBelajar,HambatanCaraBicara);
    }//GEN-LAST:event_KeteranganHambatanBelajarKeyPressed

    private void HambatanCaraBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanCaraBicaraKeyPressed
        Valid.pindah(evt,KeteranganHambatanBelajar,HambatanBahasaIsyarat);
    }//GEN-LAST:event_HambatanCaraBicaraKeyPressed

    private void HambatanBahasaIsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanBahasaIsyaratKeyPressed
        Valid.pindah(evt,HambatanCaraBicara,CaraBelajarDisukai);
    }//GEN-LAST:event_HambatanBahasaIsyaratKeyPressed

    private void CaraBelajarDisukaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraBelajarDisukaiKeyPressed
        Valid.pindah(evt,HambatanBahasaIsyarat,KesediaanMenerimaInformasi);
    }//GEN-LAST:event_CaraBelajarDisukaiKeyPressed

    private void KesediaanMenerimaInformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesediaanMenerimaInformasiKeyPressed
        Valid.pindah(evt,CaraBelajarDisukai,KeteranganKesediaanMenerimaInformasi);
    }//GEN-LAST:event_KesediaanMenerimaInformasiKeyPressed

    private void KeteranganKesediaanMenerimaInformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKesediaanMenerimaInformasiKeyPressed
        Valid.pindah(evt,KesediaanMenerimaInformasi,PemahamanNutrisi);
    }//GEN-LAST:event_KeteranganKesediaanMenerimaInformasiKeyPressed

    private void PemahamanNutrisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanNutrisiKeyPressed
        Valid.pindah(evt,KeteranganKesediaanMenerimaInformasi,PemahamanPenyakit);
    }//GEN-LAST:event_PemahamanNutrisiKeyPressed

    private void PemahamanPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPenyakitKeyPressed
        Valid.pindah(evt,PemahamanNutrisi,PemahamanPengobatan);
    }//GEN-LAST:event_PemahamanPenyakitKeyPressed

    private void PemahamanPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPerawatanKeyPressed
        Valid.pindah(evt,PemahamanPengobatan,SG1);
    }//GEN-LAST:event_PemahamanPerawatanKeyPressed

    private void PemahamanPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemahamanPengobatanKeyPressed
        Valid.pindah(evt,PemahamanPenyakit,PemahamanPerawatan);
    }//GEN-LAST:event_PemahamanPengobatanKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        NilaiGizi1.setText(SG1.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        Valid.pindah(evt,PemahamanPerawatan,SG2);
    }//GEN-LAST:event_SG1KeyPressed

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        NilaiGizi2.setText(SG2.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG2ItemStateChanged

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        Valid.pindah(evt,SG1,SG3);
    }//GEN-LAST:event_SG2KeyPressed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        NilaiGizi3.setText(SG3.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        Valid.pindah(evt,SG2,SG4);
    }//GEN-LAST:event_SG3KeyPressed

    private void SG4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG4ItemStateChanged
        NilaiGizi4.setText(SG4.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG4ItemStateChanged

    private void SG4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG4KeyPressed
        Valid.pindah(evt,SG3,KeteranganSkriningGizi);
    }//GEN-LAST:event_SG4KeyPressed

    private void KeteranganSkriningGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganSkriningGiziKeyPressed
        Valid.pindah(evt,SG4,SkalaResiko1);
    }//GEN-LAST:event_KeteranganSkriningGiziKeyPressed

    private void SkalaResiko1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko1ItemStateChanged
        if(SkalaResiko1.getSelectedIndex()==0){
            NilaiResiko1.setText("4");
        }else if(SkalaResiko1.getSelectedIndex()==1){
            NilaiResiko1.setText("3");
        }else if(SkalaResiko1.getSelectedIndex()==2){
            NilaiResiko1.setText("2");
        }else{
            NilaiResiko1.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko1ItemStateChanged

    private void SkalaResiko1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko1KeyPressed
        Valid.pindah(evt,KeteranganSkriningGizi,SkalaResiko2);
    }//GEN-LAST:event_SkalaResiko1KeyPressed

    private void SkalaResiko2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko2ItemStateChanged
        if(SkalaResiko2.getSelectedIndex()==0){
            NilaiResiko2.setText("2");
        }else{
            NilaiResiko2.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko2ItemStateChanged

    private void SkalaResiko2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko2KeyPressed
        Valid.pindah(evt,SkalaResiko1,SkalaResiko3);
    }//GEN-LAST:event_SkalaResiko2KeyPressed

    private void SkalaResiko3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko3ItemStateChanged
        if(SkalaResiko3.getSelectedIndex()==0){
            NilaiResiko3.setText("4");
        }else if(SkalaResiko3.getSelectedIndex()==1){
            NilaiResiko3.setText("3");
        }else if(SkalaResiko3.getSelectedIndex()==2){
            NilaiResiko3.setText("2");
        }else{
            NilaiResiko3.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko3ItemStateChanged

    private void SkalaResiko3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko3KeyPressed
        Valid.pindah(evt,SkalaResiko2,SkalaResiko4);
    }//GEN-LAST:event_SkalaResiko3KeyPressed

    private void SkalaResiko4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko4ItemStateChanged
        if(SkalaResiko4.getSelectedIndex()==0){
            NilaiResiko4.setText("3");
        }else if(SkalaResiko4.getSelectedIndex()==1){
            NilaiResiko4.setText("2");
        }else{
            NilaiResiko4.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko4ItemStateChanged

    private void SkalaResiko4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko4KeyPressed
        Valid.pindah(evt,SkalaResiko3,SkalaResiko5);
    }//GEN-LAST:event_SkalaResiko4KeyPressed

    private void SkalaResiko5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko5ItemStateChanged
        if(SkalaResiko5.getSelectedIndex()==0){
            NilaiResiko5.setText("4");
        }else if(SkalaResiko5.getSelectedIndex()==1){
            NilaiResiko5.setText("3");
        }else if(SkalaResiko5.getSelectedIndex()==2){
            NilaiResiko5.setText("2");
        }else{
            NilaiResiko5.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko5ItemStateChanged

    private void SkalaResiko5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko5KeyPressed
        Valid.pindah(evt,SkalaResiko4,SkalaResiko6);
    }//GEN-LAST:event_SkalaResiko5KeyPressed

    private void SkalaResiko6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko6ItemStateChanged
        if(SkalaResiko6.getSelectedIndex()==0){
            NilaiResiko6.setText("3");
        }else if(SkalaResiko6.getSelectedIndex()==1){
            NilaiResiko6.setText("2");
        }else{
            NilaiResiko6.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko6ItemStateChanged

    private void SkalaResiko6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko6KeyPressed
        Valid.pindah(evt,SkalaResiko5,SkalaResiko7);
    }//GEN-LAST:event_SkalaResiko6KeyPressed

    private void SkalaResiko7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaResiko7ItemStateChanged
        if(SkalaResiko7.getSelectedIndex()==0){
            NilaiResiko7.setText("3");
        }else if(SkalaResiko7.getSelectedIndex()==1){
            NilaiResiko7.setText("2");
        }else{
            NilaiResiko7.setText("1");
        }
        isTotalResikoJatuh();
    }//GEN-LAST:event_SkalaResiko7ItemStateChanged

    private void SkalaResiko7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaResiko7KeyPressed
        Valid.pindah(evt,SkalaResiko6,SkalaWajah);
    }//GEN-LAST:event_SkalaResiko7KeyPressed

    private void SkalaWajahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaWajahItemStateChanged
        NilaiWajah.setText(SkalaWajah.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaWajahItemStateChanged

    private void SkalaWajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaWajahKeyPressed
        Valid.pindah(evt,SG4,SkalaKaki);
    }//GEN-LAST:event_SkalaWajahKeyPressed

    private void SkalaMenangisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaMenangisItemStateChanged
        NilaiMenangis.setText(SkalaMenangis.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaMenangisItemStateChanged

    private void SkalaMenangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaMenangisKeyPressed
        Valid.pindah(evt,SkalaAktifitas,SkalaBersuara);
    }//GEN-LAST:event_SkalaMenangisKeyPressed

    private void SkalaKakiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaKakiItemStateChanged
        NilaiKaki.setText(SkalaKaki.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaKakiItemStateChanged

    private void SkalaKakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaKakiKeyPressed
        Valid.pindah(evt,SkalaWajah,SkalaAktifitas);
    }//GEN-LAST:event_SkalaKakiKeyPressed

    private void SkalaBersuaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaBersuaraItemStateChanged
        NilaiBersuara.setText(SkalaBersuara.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaBersuaraItemStateChanged

    private void SkalaBersuaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaBersuaraKeyPressed
        Valid.pindah(evt,SkalaMenangis,Nyeri);
    }//GEN-LAST:event_SkalaBersuaraKeyPressed

    private void SkalaAktifitasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaAktifitasItemStateChanged
        NilaiAktifitas.setText(SkalaAktifitas.getSelectedIndex()+"");
        SkalaNyeri.setText(""+(Integer.parseInt(NilaiWajah.getText())+Integer.parseInt(NilaiKaki.getText())+Integer.parseInt(NilaiAktifitas.getText())+Integer.parseInt(NilaiMenangis.getText())+Integer.parseInt(NilaiBersuara.getText())));
    }//GEN-LAST:event_SkalaAktifitasItemStateChanged

    private void SkalaAktifitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaAktifitasKeyPressed
        Valid.pindah(evt,SkalaKaki,SkalaMenangis);
    }//GEN-LAST:event_SkalaAktifitasKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        Valid.pindah(evt,SkalaBersuara,Lokasi);
    }//GEN-LAST:event_NyeriKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,Nyeri,Durasi);
    }//GEN-LAST:event_LokasiKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed
        Valid.pindah(evt,Durasi,NyeriHilang);
    }//GEN-LAST:event_FrekuensiKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,Lokasi,Frekuensi);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Frekuensi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,NyeriPadaDokter);
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void NyeriPadaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriPadaDokterKeyPressed
        Valid.pindah(evt,KetNyeri,NyeriKetDokter);
    }//GEN-LAST:event_NyeriPadaDokterKeyPressed

    private void NyeriKetDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKetDokterKeyPressed
        Valid.pindah(evt,NyeriPadaDokter,InformasiPerencanaanPulang);
    }//GEN-LAST:event_NyeriKetDokterKeyPressed

    private void InformasiPerencanaanPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiPerencanaanPulangKeyPressed
        Valid.pindah(evt,NyeriKetDokter,LamaRatarata);
    }//GEN-LAST:event_InformasiPerencanaanPulangKeyPressed

    private void TanggalPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPulangKeyPressed
        Valid.pindah(evt,LamaRatarata,KondisiPulang);
    }//GEN-LAST:event_TanggalPulangKeyPressed

    private void LamaRatarataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaRatarataKeyPressed
        Valid.pindah(evt,InformasiPerencanaanPulang,TanggalPulang);
    }//GEN-LAST:event_LamaRatarataKeyPressed

    private void KondisiPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiPulangKeyPressed
        Valid.pindah(evt,TanggalPulang,PerawatanLanjutan);
    }//GEN-LAST:event_KondisiPulangKeyPressed

    private void PerawatanLanjutanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanLanjutanKeyPressed
        Valid.pindah2(evt,KondisiPulang,CaraTransportasiPulang);
    }//GEN-LAST:event_PerawatanLanjutanKeyPressed

    private void CaraTransportasiPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraTransportasiPulangKeyPressed
        Valid.pindah(evt,PerawatanLanjutan,TransportasiYangDigunakan);
    }//GEN-LAST:event_CaraTransportasiPulangKeyPressed

    private void TransportasiYangDigunakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiYangDigunakanKeyPressed
        Valid.pindah(evt,CaraTransportasiPulang,TCariMasalah);
    }//GEN-LAST:event_TransportasiYangDigunakanKeyPressed

    private void BtnKeluarImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarImunisasiActionPerformed
        DlgRiwayatImunisasi.dispose();
    }//GEN-LAST:event_BtnKeluarImunisasiActionPerformed

    private void BtnSimpanImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanImunisasiActionPerformed
        if(KdImunisasi.getText().trim().equals("")||NmImunisasi.getText().trim().equals("")){
            Valid.textKosong(BtnImunisasi,"Imunisasi");
        }else{
            if(Sequel.menyimpantf("riwayat_imunisasi","?,?,?","Riwayat Imunisasi",3,new String[]{
                TNoRM.getText(),KdImunisasi.getText(),ImunisasiKe.getSelectedItem().toString()
            })==true){
                KdImunisasi.setText("");
                NmImunisasi.setText("");
                ImunisasiKe.setSelectedIndex(0);
                tampilImunisasi();
            }
        }
    }//GEN-LAST:event_BtnSimpanImunisasiActionPerformed

    private void BtnImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImunisasiActionPerformed
        imunisasi.isCek();
        imunisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        imunisasi.setLocationRelativeTo(internalFrame1);
        imunisasi.setVisible(true);
    }//GEN-LAST:event_BtnImunisasiActionPerformed

    private void BtnImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnImunisasiKeyPressed
        Valid.pindah(evt,Alergi,BtnSimpan);
    }//GEN-LAST:event_BtnImunisasiKeyPressed

    private void KdImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdImunisasiKeyPressed

    }//GEN-LAST:event_KdImunisasiKeyPressed

    private void ImunisasiKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImunisasiKeKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_ImunisasiKeKeyPressed

    private void BtnHapusImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusImunisasiActionPerformed
        Sequel.queryu2("delete from riwayat_imunisasi where no_rkm_medis=? and kode_imunisasi=? and no_imunisasi=?",3,new String[]{TNoRM.getText(),KdImunisasi.getText(),ImunisasiKe.getSelectedItem().toString()});
        DlgRiwayatImunisasi.dispose();
        tampilImunisasi();
    }//GEN-LAST:event_BtnHapusImunisasiActionPerformed

    private void PekerjaanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanPJKeyPressed
        Valid.pindah(evt,KeteranganTinggalDengan,NilaiKepercayaan);
    }//GEN-LAST:event_PekerjaanPJKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanRanapBayiAnak dialog = new RMPenilaianAwalKeperawatanRanapBayiAnak(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AdakahPerilaku;
    private widget.TextBox Agama;
    private widget.TextBox Alergi;
    private widget.TextBox Anakke;
    private widget.ComboBox Anamnesis;
    private widget.TextBox BAB;
    private widget.TextBox BAK;
    private widget.TextBox BB;
    private widget.TextBox BBLahir;
    private widget.TextBox Bahasa;
    private widget.TextBox BahasaSehari;
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
    private widget.Button BtnHapusImunisasi;
    private widget.Button BtnImunisasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarImunisasi;
    private widget.Button BtnPanggilHapusImunisasi;
    private widget.Button BtnPetugas;
    private widget.Button BtnPetugas2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanImunisasi;
    private widget.Button BtnTambahImunisasi;
    private widget.Button BtnTambahMasalah;
    private widget.Button BtnTambahRencana;
    private widget.ComboBox ButuhPenerjemah;
    private widget.TextBox CaraBayar;
    private widget.ComboBox CaraBelajarDisukai;
    private widget.ComboBox CaraKelahiran;
    private widget.ComboBox CaraTransportasiPulang;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DariSaudara;
    private widget.TextArea DetailRencana;
    private javax.swing.JDialog DlgRiwayatImunisasi;
    private widget.TextBox Durasi;
    private widget.ComboBox EdukasiPsikolgis;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Frekuensi;
    private widget.TextBox GCS;
    private widget.TextBox GangguanEmosi;
    private widget.ComboBox GangguanJiwa;
    private widget.ComboBox GastrointestinalAbdomen;
    private widget.ComboBox GastrointestinalAnus;
    private widget.ComboBox GastrointestinalGigi;
    private widget.ComboBox GastrointestinalLidah;
    private widget.ComboBox GastrointestinalMulut;
    private widget.ComboBox GastrointestinalTenggorakan;
    private widget.ComboBox GastrointestinalUsus;
    private widget.ComboBox HambatanBahasaIsyarat;
    private widget.ComboBox HambatanBelajar;
    private widget.ComboBox HambatanCaraBicara;
    private widget.ComboBox HubunganAnggotaKeluarga;
    private widget.ComboBox ImunisasiKe;
    private widget.ComboBox InformasiPerencanaanPulang;
    private widget.ComboBox IntegumentDecubitus;
    private widget.ComboBox IntegumentKulit;
    private widget.ComboBox IntegumentTurgor;
    private widget.ComboBox IntegumentWarnaKulit;
    private widget.TextBox Jk;
    private widget.TextBox KBAB;
    private widget.ComboBox KardiovaskularDenyutNadi;
    private widget.ComboBox KardiovaskularPulsasi;
    private widget.ComboBox KardiovaskularSirkulasi;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdImunisasi;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugas2;
    private widget.ComboBox KelainanBawaan;
    private widget.ComboBox KemampuanBacaTulis;
    private widget.ComboBox Kesadaran;
    private widget.ComboBox KesediaanMenerimaInformasi;
    private widget.TextBox KetAnamnesis;
    private widget.TextBox KetCaraKelahiran;
    private widget.TextBox KetGastrointestinalAbdomen;
    private widget.TextBox KetGastrointestinalGigi;
    private widget.TextBox KetGastrointestinalLidah;
    private widget.TextBox KetGastrointestinalMulut;
    private widget.TextBox KetGastrointestinalTenggorakan;
    private widget.TextBox KetKardiovaskularSirkulasi;
    private widget.TextBox KetKelainanBawaan;
    private widget.TextBox KetMuskuloskletalFraktur;
    private widget.TextBox KetMuskuloskletalNyeriSendi;
    private widget.TextBox KetMuskuloskletalOedema;
    private widget.TextBox KetNeurologiBicara;
    private widget.TextBox KetNeurologiPenglihatan;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetRespirasiJenisPernafasan;
    private widget.TextBox KetSistemSarafKejang;
    private widget.TextBox KetSistemSarafKepala;
    private widget.TextBox KetSistemSarafWajah;
    private widget.TextBox KeteranganAdakahPerilaku;
    private widget.TextBox KeteranganButuhPenerjemah;
    private widget.TextBox KeteranganEdukasiPsikologis;
    private widget.TextBox KeteranganHambatanBelajar;
    private widget.TextBox KeteranganKesediaanMenerimaInformasi;
    private widget.TextBox KeteranganNilaiKepercayaan;
    private widget.TextBox KeteranganSkriningGizi;
    private widget.TextBox KeteranganTinggalDengan;
    private widget.TextBox KeteranganTingkatRisiko;
    private widget.ComboBox KondisiPsikologis;
    private widget.TextBox KondisiPulang;
    private widget.TextBox LBAK;
    private widget.Label LCount;
    private widget.TextBox LD;
    private widget.TextBox LK;
    private widget.TextBox LP;
    private widget.TextBox LamaRatarata;
    private widget.TextBox Lokasi;
    private widget.ComboBox MuskuloskletalFraktur;
    private widget.ComboBox MuskuloskletalKekuatanOtot;
    private widget.ComboBox MuskuloskletalNyeriSendi;
    private widget.ComboBox MuskuloskletalOedema;
    private widget.ComboBox MuskuloskletalPegerakanSendi;
    private widget.TextBox Nadi;
    private widget.ComboBox NeurologiAlatBantuPenglihatan;
    private widget.ComboBox NeurologiBicara;
    private widget.ComboBox NeurologiMotorik;
    private widget.ComboBox NeurologiOtot;
    private widget.ComboBox NeurologiPendengaran;
    private widget.ComboBox NeurologiPenglihatan;
    private widget.ComboBox NeurologiSensorik;
    private widget.TextBox NilaiAktifitas;
    private widget.TextBox NilaiBersuara;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGizi3;
    private widget.TextBox NilaiGizi4;
    private widget.TextBox NilaiKaki;
    private widget.ComboBox NilaiKepercayaan;
    private widget.TextBox NilaiMenangis;
    private widget.TextBox NilaiResiko1;
    private widget.TextBox NilaiResiko2;
    private widget.TextBox NilaiResiko3;
    private widget.TextBox NilaiResiko4;
    private widget.TextBox NilaiResiko5;
    private widget.TextBox NilaiResiko6;
    private widget.TextBox NilaiResiko7;
    private widget.TextBox NilaiResikoTotal;
    private widget.TextBox NilaiWajah;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmImunisasi;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugas2;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.TextBox NyeriKetDokter;
    private widget.ComboBox NyeriPadaDokter;
    private widget.TextBox PBLahir;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.TextBox PekerjaanPJ;
    private widget.ComboBox PemahamanNutrisi;
    private widget.ComboBox PemahamanPengobatan;
    private widget.ComboBox PemahamanPenyakit;
    private widget.ComboBox PemahamanPerawatan;
    private widget.ComboBox PendidikanPJ;
    private widget.TextBox PendidikanPasien;
    private widget.TextArea PerawatanLanjutan;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextArea RPS;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ComboBox RespirasiBatuk;
    private widget.ComboBox RespirasiIrama;
    private widget.ComboBox RespirasiJenisPernafasan;
    private widget.ComboBox RespirasiPolaNafas;
    private widget.ComboBox RespirasiRetraksi;
    private widget.ComboBox RespirasiSuaraNafas;
    private widget.ComboBox RespirasiVolume;
    private widget.TextBox RiwayatPersalinanLainnya;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ComboBox SG4;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ComboBox SistemSarafKejang;
    private widget.ComboBox SistemSarafKepala;
    private widget.ComboBox SistemSarafLeher;
    private widget.ComboBox SistemSarafSensorik;
    private widget.ComboBox SistemSarafWajah;
    private widget.ComboBox SkalaAktifitas;
    private widget.ComboBox SkalaBersuara;
    private widget.ComboBox SkalaKaki;
    private widget.ComboBox SkalaMenangis;
    private widget.TextBox SkalaNyeri;
    private widget.ComboBox SkalaResiko1;
    private widget.ComboBox SkalaResiko2;
    private widget.ComboBox SkalaResiko3;
    private widget.ComboBox SkalaResiko4;
    private widget.ComboBox SkalaResiko5;
    private widget.ComboBox SkalaResiko6;
    private widget.ComboBox SkalaResiko7;
    private widget.ComboBox SkalaWajah;
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
    private widget.Tanggal TanggalPulang;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TerdapatHambatanBelajar;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TibadiRuang;
    private widget.ComboBox TinggalDengan;
    private widget.Label TingkatResiko1;
    private widget.TextBox TotalNilaiGizi;
    private widget.ComboBox TransportasiYangDigunakan;
    private widget.ComboBox UmurKelahiran;
    private widget.TextBox UsiaBerdiri;
    private widget.TextBox UsiaBerjalan;
    private widget.TextBox UsiaBicara;
    private widget.TextBox UsiaDuduk;
    private widget.TextBox UsiaGigi;
    private widget.TextBox UsiaMembaca;
    private widget.TextBox UsiaMenulis;
    private widget.TextBox UsiaTengkurap;
    private widget.TextBox WBAB;
    private widget.TextBox WBAK;
    private widget.TextBox XBAB;
    private widget.TextBox XBAK;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
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
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
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
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
    private widget.Label jLabel171;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel174;
    private widget.Label jLabel175;
    private widget.Label jLabel176;
    private widget.Label jLabel177;
    private widget.Label jLabel178;
    private widget.Label jLabel179;
    private widget.Label jLabel18;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel187;
    private widget.Label jLabel188;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel191;
    private widget.Label jLabel192;
    private widget.Label jLabel193;
    private widget.Label jLabel194;
    private widget.Label jLabel195;
    private widget.Label jLabel196;
    private widget.Label jLabel197;
    private widget.Label jLabel198;
    private widget.Label jLabel199;
    private widget.Label jLabel20;
    private widget.Label jLabel200;
    private widget.Label jLabel201;
    private widget.Label jLabel202;
    private widget.Label jLabel203;
    private widget.Label jLabel204;
    private widget.Label jLabel21;
    private widget.Label jLabel214;
    private widget.Label jLabel215;
    private widget.Label jLabel216;
    private widget.Label jLabel217;
    private widget.Label jLabel218;
    private widget.Label jLabel219;
    private widget.Label jLabel22;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel23;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel24;
    private widget.Label jLabel240;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel25;
    private widget.Label jLabel250;
    private widget.Label jLabel256;
    private widget.Label jLabel257;
    private widget.Label jLabel259;
    private widget.Label jLabel26;
    private widget.Label jLabel260;
    private widget.Label jLabel261;
    private widget.Label jLabel262;
    private widget.Label jLabel263;
    private widget.Label jLabel264;
    private widget.Label jLabel265;
    private widget.Label jLabel266;
    private widget.Label jLabel27;
    private widget.Label jLabel272;
    private widget.Label jLabel28;
    private widget.Label jLabel284;
    private widget.Label jLabel285;
    private widget.Label jLabel286;
    private widget.Label jLabel287;
    private widget.Label jLabel288;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label29;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbImunisasi;
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
                "select penilaian_awal_keperawatan_ranap_bayi.no_rawat,penilaian_awal_keperawatan_ranap_bayi.tanggal,penilaian_awal_keperawatan_ranap_bayi.informasi,penilaian_awal_keperawatan_ranap_bayi.ket_informasi,"+
                "penilaian_awal_keperawatan_ranap_bayi.tiba_diruang_rawat,penilaian_awal_keperawatan_ranap_bayi.rps,penilaian_awal_keperawatan_ranap_bayi.rpd,penilaian_awal_keperawatan_ranap_bayi.rpk,penilaian_awal_keperawatan_ranap_bayi.rpo,"+
                "penilaian_awal_keperawatan_ranap_bayi.alergi,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_tengkurap,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_duduk,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_berdiri,"+
                "penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_gigi_pertama,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_berjalan,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_bicara,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_membaca,"+
                "penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_menulis,penilaian_awal_keperawatan_ranap_bayi.tumbuh_kembang_gangguan_emosi,penilaian_awal_keperawatan_ranap_bayi.persalinan_anakke,penilaian_awal_keperawatan_ranap_bayi.persalinan_darisaudara,"+
                "penilaian_awal_keperawatan_ranap_bayi.persalinan_kelahiran,penilaian_awal_keperawatan_ranap_bayi.persalinan_kelahiran_keterangan,penilaian_awal_keperawatan_ranap_bayi.persalinan_umur_kelahiran,penilaian_awal_keperawatan_ranap_bayi.persalinan_kelainan_bawaan,"+
                "penilaian_awal_keperawatan_ranap_bayi.persalinan_kelainan_bawaan_keterangan,penilaian_awal_keperawatan_ranap_bayi.persalinan_bb_lahir,penilaian_awal_keperawatan_ranap_bayi.persalinan_pb_lahir,penilaian_awal_keperawatan_ranap_bayi.persalinan_lainnya,"+
                "penilaian_awal_keperawatan_ranap_bayi.fisik_kesadaran,penilaian_awal_keperawatan_ranap_bayi.fisik_gcs,penilaian_awal_keperawatan_ranap_bayi.fisik_td,penilaian_awal_keperawatan_ranap_bayi.fisik_rr,penilaian_awal_keperawatan_ranap_bayi.fisik_suhu,"+
                "penilaian_awal_keperawatan_ranap_bayi.fisik_nadi,penilaian_awal_keperawatan_ranap_bayi.fisik_bb,penilaian_awal_keperawatan_ranap_bayi.fisik_tb,penilaian_awal_keperawatan_ranap_bayi.fisik_lp,penilaian_awal_keperawatan_ranap_bayi.fisik_lk,"+
                "penilaian_awal_keperawatan_ranap_bayi.fisik_ld,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_kepala,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_kepala_keterangan,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_wajah,"+
                "penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_wajah_keterangan,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_leher,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_kejang,penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_kejang_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.saraf_pusat_sensorik,penilaian_awal_keperawatan_ranap_bayi.kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap_bayi.kardiovaskuler_sirkulasi,penilaian_awal_keperawatan_ranap_bayi.kardiovaskuler_sirkulasi_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap_bayi.respirasi_retraksi,penilaian_awal_keperawatan_ranap_bayi.respirasi_pola_nafas,penilaian_awal_keperawatan_ranap_bayi.respirasi_suara_nafas,"+
                "penilaian_awal_keperawatan_ranap_bayi.respirasi_batuk,penilaian_awal_keperawatan_ranap_bayi.respirasi_volume,penilaian_awal_keperawatan_ranap_bayi.respirasi_jenis_pernapasan,penilaian_awal_keperawatan_ranap_bayi.respirasi_jenis_pernapasan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.respirasi_irama,penilaian_awal_keperawatan_ranap_bayi.gastro_mulut,penilaian_awal_keperawatan_ranap_bayi.gastro_mulut_keterangan,penilaian_awal_keperawatan_ranap_bayi.gastro_tenggorakan,"+
                "penilaian_awal_keperawatan_ranap_bayi.gastro_tenggorakan_keterangan,penilaian_awal_keperawatan_ranap_bayi.gastro_lidah,penilaian_awal_keperawatan_ranap_bayi.gastro_lidah_keterangan,penilaian_awal_keperawatan_ranap_bayi.gastro_abdomen,"+
                "penilaian_awal_keperawatan_ranap_bayi.gastro_abdomen_keterangan,penilaian_awal_keperawatan_ranap_bayi.gastro_gigi,penilaian_awal_keperawatan_ranap_bayi.gastro_gigi_keterangan,penilaian_awal_keperawatan_ranap_bayi.gastro_usus,"+
                "penilaian_awal_keperawatan_ranap_bayi.gastro_anus,penilaian_awal_keperawatan_ranap_bayi.neurologi_sensorik,penilaian_awal_keperawatan_ranap_bayi.neurologi_pengilihatan,penilaian_awal_keperawatan_ranap_bayi.neurologi_pengilihatan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap_bayi.neurologi_motorik,penilaian_awal_keperawatan_ranap_bayi.neurologi_pendengaran,penilaian_awal_keperawatan_ranap_bayi.neurologi_bicara,"+
                "penilaian_awal_keperawatan_ranap_bayi.neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap_bayi.neurologi_otot,penilaian_awal_keperawatan_ranap_bayi.inte_kulit,penilaian_awal_keperawatan_ranap_bayi.inte_warna_kulit,"+
                "penilaian_awal_keperawatan_ranap_bayi.inte_tugor,penilaian_awal_keperawatan_ranap_bayi.inte_decubi,penilaian_awal_keperawatan_ranap_bayi.musku_odema,penilaian_awal_keperawatan_ranap_bayi.musku_odema_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.musku_pegerakansendi,penilaian_awal_keperawatan_ranap_bayi.musku_otot,penilaian_awal_keperawatan_ranap_bayi.musku_fraktur,penilaian_awal_keperawatan_ranap_bayi.musku_fraktur_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.musku_nyerisendi,penilaian_awal_keperawatan_ranap_bayi.musku_nyerisendi_keterangan,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bab_frekuensi,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bab_frekuensi_per,"+
                "penilaian_awal_keperawatan_ranap_bayi.eliminasi_bab_konsistesi,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bab_warna,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bak_frekuensi,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bak_frekuensi_per,"+
                "penilaian_awal_keperawatan_ranap_bayi.eliminasi_bak_warna,penilaian_awal_keperawatan_ranap_bayi.eliminasi_bak_lainlain,penilaian_awal_keperawatan_ranap_bayi.psiko_kondisi,penilaian_awal_keperawatan_ranap_bayi.psiko_perilaku,"+
                "penilaian_awal_keperawatan_ranap_bayi.psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap_bayi.psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap_bayi.psiko_hubungan_pasien,penilaian_awal_keperawatan_ranap_bayi.psiko_tinggal_dengan,"+
                "penilaian_awal_keperawatan_ranap_bayi.psiko_tinggal_dengan_keterangan,penilaian_awal_keperawatan_ranap_bayi.psiko_pekerjaan_pj,penilaian_awal_keperawatan_ranap_bayi.psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap_bayi.psiko_nilai_kepercayaan_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap_bayi.psiko_edukasi,penilaian_awal_keperawatan_ranap_bayi.psiko_edukasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.edukasi_bahasa,"+
                "penilaian_awal_keperawatan_ranap_bayi.edukasi_baca_tulis,penilaian_awal_keperawatan_ranap_bayi.edukasi_penerjemah,penilaian_awal_keperawatan_ranap_bayi.edukasi_penerjemah_keterangan,penilaian_awal_keperawatan_ranap_bayi.edukasi_terdapat_hambatan,"+
                "penilaian_awal_keperawatan_ranap_bayi.edukasi_hambatan_belajar,penilaian_awal_keperawatan_ranap_bayi.edukasi_hambatan_belajar_keterangan,penilaian_awal_keperawatan_ranap_bayi.edukasi_hambatan_bicara,penilaian_awal_keperawatan_ranap_bayi.edukasi_bahasa_isyarat,"+
                "penilaian_awal_keperawatan_ranap_bayi.edukasi_cara_belajar,penilaian_awal_keperawatan_ranap_bayi.edukasi_menerima_informasi,penilaian_awal_keperawatan_ranap_bayi.edukasi_menerima_informasi_keterangan,penilaian_awal_keperawatan_ranap_bayi.edukasi_nutrisi,"+
                "penilaian_awal_keperawatan_ranap_bayi.edukasi_penyakit,penilaian_awal_keperawatan_ranap_bayi.edukasi_pengobatan,penilaian_awal_keperawatan_ranap_bayi.edukasi_perawatan,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi1,"+
                "penilaian_awal_keperawatan_ranap_bayi.nilai_gizi1,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi2,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi2,penilaian_awal_keperawatan_ranap_bayi.skrining_gizi3,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi3,"+
                "penilaian_awal_keperawatan_ranap_bayi.skrining_gizi4,penilaian_awal_keperawatan_ranap_bayi.nilai_gizi4,penilaian_awal_keperawatan_ranap_bayi.total_nilai,penilaian_awal_keperawatan_ranap_bayi.keterangan_skrining_gizi,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala1,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai1,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala2,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai2,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala3,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai3,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala4,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai4,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala5,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai5,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala6,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai6,"+
                "penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_skala7,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_nilai7,penilaian_awal_keperawatan_ranap_bayi.penilaian_humptydumpty_totalnilai,"+
                "penilaian_awal_keperawatan_ranap_bayi.hasil_skrining_penilaian_humptydumpty,penilaian_awal_keperawatan_ranap_bayi.nyeri_wajah,penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_wajah,penilaian_awal_keperawatan_ranap_bayi.nyeri_kaki,"+
                "penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_kaki,penilaian_awal_keperawatan_ranap_bayi.nyeri_aktifitas,penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_aktifitas,penilaian_awal_keperawatan_ranap_bayi.nyeri_menangis,"+
                "penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_menangis,penilaian_awal_keperawatan_ranap_bayi.nyeri_bersuara,penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_bersuara,penilaian_awal_keperawatan_ranap_bayi.nyeri_nilai_total,"+
                "penilaian_awal_keperawatan_ranap_bayi.nyeri_kondisi,penilaian_awal_keperawatan_ranap_bayi.nyeri_lokasi,penilaian_awal_keperawatan_ranap_bayi.nyeri_durasi,penilaian_awal_keperawatan_ranap_bayi.nyeri_frekuensi,"+
                "penilaian_awal_keperawatan_ranap_bayi.nyeri_hilang,penilaian_awal_keperawatan_ranap_bayi.nyeri_hilang_keterangan,penilaian_awal_keperawatan_ranap_bayi.nyeri_diberitahukan_pada_dokter,penilaian_awal_keperawatan_ranap_bayi.nyeri_diberitahukan_pada_dokter_keterangan,"+
                "penilaian_awal_keperawatan_ranap_bayi.informasi_perencanaan_pulang,penilaian_awal_keperawatan_ranap_bayi.lama_ratarata,penilaian_awal_keperawatan_ranap_bayi.perencanaan_pulang,penilaian_awal_keperawatan_ranap_bayi.kondisi_klinis_pulang,"+
                "penilaian_awal_keperawatan_ranap_bayi.perawatan_lanjutan_dirumah,penilaian_awal_keperawatan_ranap_bayi.cara_transportasi_pulang,penilaian_awal_keperawatan_ranap_bayi.transportasi_digunakan,penilaian_awal_keperawatan_ranap_bayi.rencana,"+
                "penilaian_awal_keperawatan_ranap_bayi.nip1,penilaian_awal_keperawatan_ranap_bayi.nip2,penilaian_awal_keperawatan_ranap_bayi.kd_dokter,pasien.tgl_lahir,pasien.jk,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.agama,pasien.pnd,penjab.png_jawab,bahasa_pasien.nama_bahasa "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_awal_keperawatan_ranap_bayi on reg_periksa.no_rawat=penilaian_awal_keperawatan_ranap_bayi.no_rawat "+
                "inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap_bayi.nip1=pengkaji1.nip "+
                "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap_bayi.nip2=pengkaji2.nip "+
                "inner join dokter on penilaian_awal_keperawatan_ranap_bayi.kd_dokter=dokter.kd_dokter "+
                "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where "+
                "penilaian_awal_keperawatan_ranap_bayi.tanggal between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or penilaian_awal_keperawatan_ranap_bayi.nip1 like ? or "+
                "pengkaji1.nama like ? or penilaian_awal_keperawatan_ranap_bayi.kd_dokter like ? or dokter.nm_dokter like ?)")+
                " order by penilaian_awal_keperawatan_ranap_bayi.tanggal");
            
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
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("ket_informasi"),rs.getString("tiba_diruang_rawat"),rs.getString("rps"),rs.getString("rpd"),rs.getString("rpk"),
                        rs.getString("rpo"),rs.getString("alergi"),rs.getString("tumbuh_kembang_tengkurap"),rs.getString("tumbuh_kembang_duduk"),rs.getString("tumbuh_kembang_berdiri"),rs.getString("tumbuh_kembang_gigi_pertama"),rs.getString("tumbuh_kembang_berjalan"),
                        rs.getString("tumbuh_kembang_bicara"),rs.getString("tumbuh_kembang_membaca"),rs.getString("tumbuh_kembang_menulis"),rs.getString("tumbuh_kembang_gangguan_emosi"),rs.getString("persalinan_anakke"),rs.getString("persalinan_darisaudara"),
                        rs.getString("persalinan_kelahiran"),rs.getString("persalinan_kelahiran_keterangan"),rs.getString("persalinan_umur_kelahiran"),rs.getString("persalinan_kelainan_bawaan"),rs.getString("persalinan_kelainan_bawaan_keterangan"),
                        rs.getString("persalinan_bb_lahir"),rs.getString("persalinan_pb_lahir"),rs.getString("persalinan_lainnya"),rs.getString("fisik_kesadaran"),rs.getString("fisik_gcs"),rs.getString("fisik_td"),rs.getString("fisik_rr"),rs.getString("fisik_suhu"),
                        rs.getString("fisik_nadi"),rs.getString("fisik_bb"),rs.getString("fisik_tb"),rs.getString("fisik_lp"),rs.getString("fisik_lk"),rs.getString("fisik_ld"),rs.getString("saraf_pusat_kepala"),rs.getString("saraf_pusat_kepala_keterangan"),
                        rs.getString("saraf_pusat_wajah"),rs.getString("saraf_pusat_wajah_keterangan"),rs.getString("saraf_pusat_leher"),rs.getString("saraf_pusat_kejang"),rs.getString("saraf_pusat_kejang_keterangan"),rs.getString("saraf_pusat_sensorik"),
                        rs.getString("kardiovaskuler_pulsasi"),rs.getString("kardiovaskuler_sirkulasi"),rs.getString("kardiovaskuler_sirkulasi_keterangan"),rs.getString("kardiovaskuler_denyut_nadi"),rs.getString("respirasi_retraksi"),rs.getString("respirasi_pola_nafas"),
                        rs.getString("respirasi_suara_nafas"),rs.getString("respirasi_batuk"),rs.getString("respirasi_volume"),rs.getString("respirasi_jenis_pernapasan"),rs.getString("respirasi_jenis_pernapasan_keterangan"),rs.getString("respirasi_irama"),
                        rs.getString("gastro_mulut"),rs.getString("gastro_mulut_keterangan"),rs.getString("gastro_tenggorakan"),rs.getString("gastro_tenggorakan_keterangan"),rs.getString("gastro_lidah"),rs.getString("gastro_lidah_keterangan"),rs.getString("gastro_abdomen"),
                        rs.getString("gastro_abdomen_keterangan"),rs.getString("gastro_gigi"),rs.getString("gastro_gigi_keterangan"),rs.getString("gastro_usus"),rs.getString("gastro_anus"),rs.getString("neurologi_sensorik"),rs.getString("neurologi_pengilihatan"),
                        rs.getString("neurologi_pengilihatan_keterangan"),rs.getString("neurologi_alat_bantu_penglihatan"),rs.getString("neurologi_motorik"),rs.getString("neurologi_pendengaran"),rs.getString("neurologi_bicara"),rs.getString("neurologi_bicara_keterangan"),
                        rs.getString("neurologi_otot"),rs.getString("inte_kulit"),rs.getString("inte_warna_kulit"),rs.getString("inte_tugor"),rs.getString("inte_decubi"),rs.getString("musku_odema"),rs.getString("musku_odema_keterangan"),rs.getString("musku_pegerakansendi"),
                        rs.getString("musku_otot"),rs.getString("musku_fraktur"),rs.getString("musku_fraktur_keterangan"),rs.getString("musku_nyerisendi"),rs.getString("musku_nyerisendi_keterangan"),rs.getString("eliminasi_bab_frekuensi"),rs.getString("eliminasi_bab_frekuensi_per"),
                        rs.getString("eliminasi_bab_konsistesi"),rs.getString("eliminasi_bab_warna"),rs.getString("eliminasi_bak_frekuensi"),rs.getString("eliminasi_bak_frekuensi_per"),rs.getString("eliminasi_bak_warna"),rs.getString("eliminasi_bak_lainlain"),
                        rs.getString("psiko_kondisi"),rs.getString("psiko_perilaku"),rs.getString("psiko_perilaku_keterangan"),rs.getString("psiko_gangguan_jiwa"),rs.getString("psiko_hubungan_pasien"),rs.getString("agama"),rs.getString("psiko_tinggal_dengan"),
                        rs.getString("psiko_tinggal_dengan_keterangan"),rs.getString("psiko_pekerjaan_pj"),rs.getString("png_jawab"),rs.getString("psiko_nilai_kepercayaan"),rs.getString("nama_bahasa"),rs.getString("pnd"),rs.getString("psiko_nilai_kepercayaan_keterangan"),
                        rs.getString("psiko_pendidikan_pj"),rs.getString("psiko_edukasi"),rs.getString("psiko_edukasi_keterangan"),rs.getString("edukasi_bahasa"),rs.getString("edukasi_baca_tulis"),rs.getString("edukasi_penerjemah"),rs.getString("edukasi_penerjemah_keterangan"),
                        rs.getString("edukasi_terdapat_hambatan"),rs.getString("edukasi_hambatan_belajar"),rs.getString("edukasi_hambatan_belajar_keterangan"),rs.getString("edukasi_hambatan_bicara"),rs.getString("edukasi_bahasa_isyarat"),rs.getString("edukasi_cara_belajar"),
                        rs.getString("edukasi_menerima_informasi"),rs.getString("edukasi_menerima_informasi_keterangan"),rs.getString("edukasi_nutrisi"),rs.getString("edukasi_penyakit"),rs.getString("edukasi_pengobatan"),rs.getString("edukasi_perawatan"),
                        rs.getString("skrining_gizi1"),rs.getString("nilai_gizi1"),rs.getString("skrining_gizi2"),rs.getString("nilai_gizi2"),rs.getString("skrining_gizi3"),rs.getString("nilai_gizi3"),rs.getString("skrining_gizi4"),rs.getString("nilai_gizi4"),
                        rs.getString("total_nilai"),rs.getString("keterangan_skrining_gizi"),rs.getString("penilaian_humptydumpty_skala1"),rs.getString("penilaian_humptydumpty_nilai1"),rs.getString("penilaian_humptydumpty_skala2"),rs.getString("penilaian_humptydumpty_nilai2"),
                        rs.getString("penilaian_humptydumpty_skala3"),rs.getString("penilaian_humptydumpty_nilai3"),rs.getString("penilaian_humptydumpty_skala4"),rs.getString("penilaian_humptydumpty_nilai4"),rs.getString("penilaian_humptydumpty_skala5"),
                        rs.getString("penilaian_humptydumpty_nilai5"),rs.getString("penilaian_humptydumpty_skala6"),rs.getString("penilaian_humptydumpty_nilai6"),rs.getString("penilaian_humptydumpty_skala7"),rs.getString("penilaian_humptydumpty_nilai7"),
                        rs.getString("penilaian_humptydumpty_totalnilai"),rs.getString("hasil_skrining_penilaian_humptydumpty"),rs.getString("nyeri_wajah"),rs.getString("nyeri_nilai_wajah"),rs.getString("nyeri_kaki"),rs.getString("nyeri_nilai_kaki"),rs.getString("nyeri_aktifitas"),
                        rs.getString("nyeri_nilai_aktifitas"),rs.getString("nyeri_menangis"),rs.getString("nyeri_nilai_menangis"),rs.getString("nyeri_bersuara"),rs.getString("nyeri_nilai_bersuara"),rs.getString("nyeri_nilai_total"),rs.getString("nyeri_kondisi"),
                        rs.getString("nyeri_lokasi"),rs.getString("nyeri_durasi"),rs.getString("nyeri_frekuensi"),rs.getString("nyeri_hilang"),rs.getString("nyeri_hilang_keterangan"),rs.getString("nyeri_diberitahukan_pada_dokter"),rs.getString("nyeri_diberitahukan_pada_dokter_keterangan"),
                        rs.getString("informasi_perencanaan_pulang"),rs.getString("lama_ratarata"),rs.getString("perencanaan_pulang"),rs.getString("kondisi_klinis_pulang"),rs.getString("perawatan_lanjutan_dirumah"),rs.getString("cara_transportasi_pulang"),
                        rs.getString("transportasi_digunakan"),rs.getString("rencana")
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
        TibadiRuang.setSelectedIndex(0);
        RPS.setText("");
        RPD.setText("");
        RPK.setText("");
        RPO.setText("");
        Alergi.setText("");
        UsiaTengkurap.setText("");
        UsiaDuduk.setText("");
        UsiaBerdiri.setText("");
        UsiaGigi.setText("");
        UsiaBerjalan.setText("");
        UsiaBicara.setText("");
        UsiaMembaca.setText("");
        UsiaMenulis.setText("");
        GangguanEmosi.setText("");
        Anakke.setText("");
        DariSaudara.setText("");
        CaraKelahiran.setSelectedIndex(0);
        KetCaraKelahiran.setText("");
        UmurKelahiran.setSelectedIndex(0);
        KelainanBawaan.setSelectedIndex(0);
        KetKelainanBawaan.setText("");
        BBLahir.setText("");
        PBLahir.setText("");
        RiwayatPersalinanLainnya.setText("");
        Kesadaran.setSelectedIndex(0);
        GCS.setText("");
        TD.setText("");
        RR.setText("");
        Suhu.setText("");
        Nadi.setText("");
        BB.setText("");
        TB.setText("");
        LP.setText("");
        LK.setText("");
        LD.setText("");
        SistemSarafKepala.setSelectedIndex(0);
        KetSistemSarafKepala.setText("");
        SistemSarafWajah.setSelectedIndex(0);
        KetSistemSarafWajah.setText("");
        SistemSarafLeher.setSelectedIndex(0);
        SistemSarafKejang.setSelectedIndex(0);
        KetSistemSarafKejang.setText("");
        SistemSarafSensorik.setSelectedIndex(0);
        KardiovaskularPulsasi.setSelectedIndex(0);
        KardiovaskularSirkulasi.setSelectedIndex(0);
        KetKardiovaskularSirkulasi.setText("");
        KardiovaskularDenyutNadi.setSelectedIndex(0);
        RespirasiRetraksi.setSelectedIndex(0);
        RespirasiPolaNafas.setSelectedIndex(0);
        RespirasiSuaraNafas.setSelectedIndex(0);
        RespirasiBatuk.setSelectedIndex(0);
        RespirasiVolume.setSelectedIndex(0);
        RespirasiJenisPernafasan.setSelectedIndex(0);
        KetRespirasiJenisPernafasan.setText("");
        RespirasiIrama.setSelectedIndex(0);
        GastrointestinalMulut.setSelectedIndex(0);
        KetGastrointestinalMulut.setText("");
        GastrointestinalTenggorakan.setSelectedIndex(0);
        KetGastrointestinalTenggorakan.setText("");
        GastrointestinalLidah.setSelectedIndex(0);
        KetGastrointestinalLidah.setText("");
        GastrointestinalAbdomen.setSelectedIndex(0);
        KetGastrointestinalAbdomen.setText("");
        GastrointestinalGigi.setSelectedIndex(0);
        KetGastrointestinalGigi.setText("");
        GastrointestinalUsus.setSelectedIndex(0);
        GastrointestinalAnus.setSelectedIndex(0);
        NeurologiSensorik.setSelectedIndex(0);
        NeurologiPenglihatan.setSelectedIndex(0);
        KetNeurologiPenglihatan.setText("");
        NeurologiAlatBantuPenglihatan.setSelectedIndex(0);
        NeurologiMotorik.setSelectedIndex(0);
        NeurologiPendengaran.setSelectedIndex(0);
        NeurologiBicara.setSelectedIndex(0);
        KetNeurologiBicara.setText("");
        NeurologiOtot.setSelectedIndex(0);
        IntegumentKulit.setSelectedIndex(0);
        IntegumentWarnaKulit.setSelectedIndex(0);
        IntegumentTurgor.setSelectedIndex(0);
        IntegumentDecubitus.setSelectedIndex(0);
        MuskuloskletalOedema.setSelectedIndex(0);
        KetMuskuloskletalOedema.setText("");
        MuskuloskletalPegerakanSendi.setSelectedIndex(0);
        MuskuloskletalKekuatanOtot.setSelectedIndex(0);
        MuskuloskletalFraktur.setSelectedIndex(0);
        KetMuskuloskletalFraktur.setText("");
        MuskuloskletalNyeriSendi.setSelectedIndex(0);
        KetMuskuloskletalNyeriSendi.setText("");
        BAB.setText("");
        XBAB.setText("");
        KBAB.setText("");
        WBAB.setText("");
        BAK.setText("");
        XBAK.setText("");
        WBAK.setText("");
        LBAK.setText("");
        KondisiPsikologis.setSelectedIndex(0);
        AdakahPerilaku.setSelectedIndex(0);
        KeteranganAdakahPerilaku.setText("");
        GangguanJiwa.setSelectedIndex(0);
        HubunganAnggotaKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KeteranganTinggalDengan.setText("");
        PekerjaanPJ.setText("");
        NilaiKepercayaan.setSelectedIndex(0);
        KeteranganNilaiKepercayaan.setText("");
        PendidikanPJ.setSelectedIndex(0);
        EdukasiPsikolgis.setSelectedIndex(0);
        KeteranganEdukasiPsikologis.setText("");
        BahasaSehari.setText("");
        KemampuanBacaTulis.setSelectedIndex(0);
        ButuhPenerjemah.setSelectedIndex(0);
        KeteranganButuhPenerjemah.setText("");
        TerdapatHambatanBelajar.setSelectedIndex(0);
        HambatanBelajar.setSelectedIndex(0);
        KeteranganHambatanBelajar.setText("");
        HambatanCaraBicara.setSelectedIndex(0);
        HambatanBahasaIsyarat.setSelectedIndex(0);
        CaraBelajarDisukai.setSelectedIndex(0);
        KesediaanMenerimaInformasi.setSelectedIndex(0);
        KeteranganKesediaanMenerimaInformasi.setText("");
        PemahamanNutrisi.setSelectedIndex(0);
        PemahamanPenyakit.setSelectedIndex(0);
        PemahamanPengobatan.setSelectedIndex(0);
        PemahamanPerawatan.setSelectedIndex(0);
        SG1.setSelectedIndex(0);
        NilaiGizi1.setText("0");
        SG2.setSelectedIndex(0);
        NilaiGizi2.setText("0");
        SG3.setSelectedIndex(0);
        NilaiGizi3.setText("0");
        SG4.setSelectedIndex(0);
        NilaiGizi4.setText("0");
        TotalNilaiGizi.setText("0");
        KeteranganSkriningGizi.setText("");
        SkalaResiko1.setSelectedIndex(3);
        NilaiResiko1.setText("1");
        SkalaResiko2.setSelectedIndex(1);
        NilaiResiko2.setText("1");
        SkalaResiko3.setSelectedIndex(3);
        NilaiResiko3.setText("1");
        SkalaResiko4.setSelectedIndex(2);
        NilaiResiko4.setText("1");
        SkalaResiko5.setSelectedIndex(3);
        NilaiResiko5.setText("1");
        SkalaResiko6.setSelectedIndex(2);
        NilaiResiko6.setText("1");
        SkalaResiko7.setSelectedIndex(2);
        NilaiResiko7.setText("1");
        NilaiResikoTotal.setText("7");
        KeteranganTingkatRisiko.setText("Risiko Rendah 7 - 11");
        SkalaWajah.setSelectedIndex(0);
        NilaiWajah.setText("0");
        SkalaKaki.setSelectedIndex(0);
        NilaiKaki.setText("0");
        SkalaAktifitas.setSelectedIndex(0);
        NilaiAktifitas.setText("0");
        SkalaMenangis.setSelectedIndex(0);
        NilaiMenangis.setText("0");
        SkalaBersuara.setSelectedIndex(0);
        NilaiBersuara.setText("0");
        SkalaNyeri.setText("0");
        Nyeri.setSelectedIndex(0);
        Lokasi.setText("");
        Durasi.setText("");
        Frekuensi.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        NyeriPadaDokter.setSelectedIndex(0);
        NyeriKetDokter.setText("");
        InformasiPerencanaanPulang.setSelectedIndex(0);
        LamaRatarata.setText("");
        TanggalPulang.setDate(new Date());
        KondisiPulang.setText("");
        PerawatanLanjutan.setText("");
        CaraTransportasiPulang.setSelectedIndex(0);
        TransportasiYangDigunakan.setSelectedIndex(0);
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        Valid.tabelKosong(tabModeRencana);
        TabRawat.setSelectedIndex(0);
        TibadiRuang.requestFocus();
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
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),174).toString());
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
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,pasien.pnd,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,penjab.png_jawab "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Agama.setText(rs.getString("agama"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    PendidikanPasien.setText(rs.getString("pnd"));
                    CaraBayar.setText(rs.getString("png_jawab"));
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_bayi());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_bayi());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_bayi());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_ranap_bayi()); 
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
                        "inner join penilaian_awal_keperawatan_ranap_bayi_masalah on penilaian_awal_keperawatan_ranap_bayi_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                        "where penilaian_awal_keperawatan_ranap_bayi_masalah.no_rawat=? order by penilaian_awal_keperawatan_ranap_bayi_masalah.kode_masalah");
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
                        "inner join penilaian_awal_keperawatan_ranap_bayi_rencana on penilaian_awal_keperawatan_ranap_bayi_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
                        "where penilaian_awal_keperawatan_ranap_bayi_rencana.no_rawat=? order by penilaian_awal_keperawatan_ranap_bayi_rencana.kode_rencana");
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
        if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ranap_bayi where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_awal_keperawatan_ranap_bayi","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,ket_informasi=?,tiba_diruang_rawat=?,kasus_trauma=?,cara_masuk=?,rps=?,rpd=?,rpk=?,rpo=?,riwayat_pembedahan=?,riwayat_dirawat_dirs=?,alat_bantu_dipakai=?,riwayat_kehamilan=?,riwayat_kehamilan_perkiraan=?,riwayat_tranfusi=?,riwayat_alergi=?,riwayat_merokok=?,riwayat_merokok_jumlah=?,riwayat_alkohol=?,riwayat_alkohol_jumlah=?,riwayat_narkoba=?,riwayat_olahraga=?,pemeriksaan_mental=?,pemeriksaan_keadaan_umum=?,pemeriksaan_gcs=?,pemeriksaan_td=?,pemeriksaan_nadi=?,pemeriksaan_rr=?,pemeriksaan_suhu=?,pemeriksaan_spo2=?,pemeriksaan_bb=?,pemeriksaan_tb=?,pemeriksaan_susunan_kepala=?,pemeriksaan_susunan_kepala_keterangan=?,pemeriksaan_susunan_wajah=?,pemeriksaan_susunan_wajah_keterangan=?,pemeriksaan_susunan_leher=?,pemeriksaan_susunan_kejang=?,pemeriksaan_susunan_kejang_keterangan=?,pemeriksaan_susunan_sensorik=?,pemeriksaan_kardiovaskuler_denyut_nadi=?,pemeriksaan_kardiovaskuler_sirkulasi=?,pemeriksaan_kardiovaskuler_sirkulasi_keterangan=?,pemeriksaan_kardiovaskuler_pulsasi=?,pemeriksaan_respirasi_pola_nafas=?,pemeriksaan_respirasi_retraksi=?,pemeriksaan_respirasi_suara_nafas=?,pemeriksaan_respirasi_volume_pernafasan=?,pemeriksaan_respirasi_jenis_pernafasan=?,pemeriksaan_respirasi_jenis_pernafasan_keterangan=?,pemeriksaan_respirasi_irama_nafas=?,pemeriksaan_respirasi_batuk=?,pemeriksaan_gastrointestinal_mulut=?,pemeriksaan_gastrointestinal_mulut_keterangan=?,pemeriksaan_gastrointestinal_gigi=?,pemeriksaan_gastrointestinal_gigi_keterangan=?,pemeriksaan_gastrointestinal_lidah=?,pemeriksaan_gastrointestinal_lidah_keterangan=?,pemeriksaan_gastrointestinal_tenggorokan=?,pemeriksaan_gastrointestinal_tenggorokan_keterangan=?,pemeriksaan_gastrointestinal_abdomen=?,pemeriksaan_gastrointestinal_abdomen_keterangan=?,pemeriksaan_gastrointestinal_peistatik_usus=?,pemeriksaan_gastrointestinal_anus=?,pemeriksaan_neurologi_pengelihatan=?,pemeriksaan_neurologi_pengelihatan_keterangan=?,pemeriksaan_neurologi_alat_bantu_penglihatan=?,pemeriksaan_neurologi_pendengaran=?,pemeriksaan_neurologi_bicara=?,pemeriksaan_neurologi_bicara_keterangan=?,pemeriksaan_neurologi_sensorik=?,pemeriksaan_neurologi_motorik=?,pemeriksaan_neurologi_kekuatan_otot=?,pemeriksaan_integument_warnakulit=?,pemeriksaan_integument_turgor=?,pemeriksaan_integument_kulit=?,pemeriksaan_integument_dekubitas=?,pemeriksaan_muskuloskletal_pergerakan_sendi=?,pemeriksaan_muskuloskletal_kekauatan_otot=?,pemeriksaan_muskuloskletal_nyeri_sendi=?,pemeriksaan_muskuloskletal_nyeri_sendi_keterangan=?,pemeriksaan_muskuloskletal_oedema=?,pemeriksaan_muskuloskletal_oedema_keterangan=?,pemeriksaan_muskuloskletal_fraktur=?,pemeriksaan_muskuloskletal_fraktur_keterangan=?,pemeriksaan_eliminasi_bab_frekuensi_jumlah=?,pemeriksaan_eliminasi_bab_frekuensi_durasi=?,pemeriksaan_eliminasi_bab_konsistensi=?,pemeriksaan_eliminasi_bab_warna=?,pemeriksaan_eliminasi_bak_frekuensi_jumlah=?,pemeriksaan_eliminasi_bak_frekuensi_durasi=?,pemeriksaan_eliminasi_bak_warna=?,pemeriksaan_eliminasi_bak_lainlain=?,pola_aktifitas_makanminum=?,pola_aktifitas_mandi=?,pola_aktifitas_eliminasi=?,pola_aktifitas_berpakaian=?,pola_aktifitas_berpindah=?,pola_nutrisi_frekuesi_makan=?,pola_nutrisi_jenis_makanan=?,pola_nutrisi_porsi_makan=?,pola_tidur_lama_tidur=?,pola_tidur_gangguan=?,pengkajian_fungsi_kemampuan_sehari=?,pengkajian_fungsi_aktifitas=?,pengkajian_fungsi_berjalan=?,pengkajian_fungsi_berjalan_keterangan=?,pengkajian_fungsi_ambulasi=?,pengkajian_fungsi_ekstrimitas_atas=?,pengkajian_fungsi_ekstrimitas_atas_keterangan=?,pengkajian_fungsi_ekstrimitas_bawah=?,pengkajian_fungsi_ekstrimitas_bawah_keterangan=?,pengkajian_fungsi_menggenggam=?,pengkajian_fungsi_menggenggam_keterangan=?,pengkajian_fungsi_koordinasi=?,pengkajian_fungsi_koordinasi_keterangan=?,pengkajian_fungsi_kesimpulan=?,riwayat_psiko_kondisi_psiko=?,riwayat_psiko_gangguan_jiwa=?,riwayat_psiko_perilaku=?,riwayat_psiko_perilaku_keterangan=?,riwayat_psiko_hubungan_keluarga=?,riwayat_psiko_tinggal=?,riwayat_psiko_tinggal_keterangan=?,riwayat_psiko_nilai_kepercayaan=?,riwayat_psiko_nilai_kepercayaan_keterangan=?,riwayat_psiko_pendidikan_pj=?,riwayat_psiko_edukasi_diberikan=?,riwayat_psiko_edukasi_diberikan_keterangan=?,penilaian_nyeri=?,penilaian_nyeri_penyebab=?,penilaian_nyeri_ket_penyebab=?,penilaian_nyeri_kualitas=?,penilaian_nyeri_ket_kualitas=?,penilaian_nyeri_lokasi=?,penilaian_nyeri_menyebar=?,penilaian_nyeri_skala=?,penilaian_nyeri_waktu=?,penilaian_nyeri_hilang=?,penilaian_nyeri_ket_hilang=?,penilaian_nyeri_diberitahukan_dokter=?,penilaian_nyeri_jam_diberitahukan_dokter=?,penilaian_jatuhmorse_skala1=?,penilaian_jatuhmorse_nilai1=?,penilaian_jatuhmorse_skala2=?,penilaian_jatuhmorse_nilai2=?,penilaian_jatuhmorse_skala3=?,penilaian_jatuhmorse_nilai3=?,penilaian_jatuhmorse_skala4=?,penilaian_jatuhmorse_nilai4=?,penilaian_jatuhmorse_skala5=?,penilaian_jatuhmorse_nilai5=?,penilaian_jatuhmorse_skala6=?,penilaian_jatuhmorse_nilai6=?,penilaian_jatuhmorse_totalnilai=?,penilaian_jatuhsydney_skala1=?,penilaian_jatuhsydney_nilai1=?,penilaian_jatuhsydney_skala2=?,penilaian_jatuhsydney_nilai2=?,penilaian_jatuhsydney_skala3=?,penilaian_jatuhsydney_nilai3=?,penilaian_jatuhsydney_skala4=?,penilaian_jatuhsydney_nilai4=?,penilaian_jatuhsydney_skala5=?,penilaian_jatuhsydney_nilai5=?,penilaian_jatuhsydney_skala6=?,penilaian_jatuhsydney_nilai6=?,penilaian_jatuhsydney_skala7=?,penilaian_jatuhsydney_nilai7=?,penilaian_jatuhsydney_skala8=?,penilaian_jatuhsydney_nilai8=?,penilaian_jatuhsydney_skala9=?,penilaian_jatuhsydney_nilai9=?,penilaian_jatuhsydney_skala10=?,penilaian_jatuhsydney_nilai10=?,penilaian_jatuhsydney_skala11=?,penilaian_jatuhsydney_nilai11=?,penilaian_jatuhsydney_totalnilai=?,skrining_gizi1=?,nilai_gizi1=?,skrining_gizi2=?,nilai_gizi2=?,nilai_total_gizi=?,skrining_gizi_diagnosa_khusus=?,skrining_gizi_ket_diagnosa_khusus=?,skrining_gizi_diketahui_dietisen=?,skrining_gizi_jam_diketahui_dietisen=?,rencana=?,nip1=?,nip2=?,kd_dokter=?",194,new String[]{
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
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);                
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);                
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);                
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);                
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);                
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);                
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);                
                tbObat.setValueAt(KdPetugas2.getText(),tbObat.getSelectedRow(),7);                
                tbObat.setValueAt(NmPetugas2.getText(),tbObat.getSelectedRow(),8);                
                tbObat.setValueAt(KdDPJP.getText(),tbObat.getSelectedRow(),9);                
                tbObat.setValueAt(NmDPJP.getText(),tbObat.getSelectedRow(),10);                
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),11);                
                tbObat.setValueAt(MacamKasus.getSelectedItem().toString(),tbObat.getSelectedRow(),12);                
                tbObat.setValueAt(Anamnesis.getSelectedItem().toString()+", "+KetAnamnesis.getText(),tbObat.getSelectedRow(),13);                
                tbObat.setValueAt(TibadiRuang.getSelectedItem().toString(),tbObat.getSelectedRow(),14);                
                tbObat.setValueAt(CaraMasuk.getSelectedItem().toString(),tbObat.getSelectedRow(),15);                
                tbObat.setValueAt(RPS.getText(),tbObat.getSelectedRow(),16);                
                tbObat.setValueAt(RPD.getText(),tbObat.getSelectedRow(),17);                
                tbObat.setValueAt(RPK.getText(),tbObat.getSelectedRow(),18);                
                tbObat.setValueAt(RPO.getText(),tbObat.getSelectedRow(),19);                
                tbObat.setValueAt(RPembedahan.getText(),tbObat.getSelectedRow(),20);                
                tbObat.setValueAt(RDirawatRS.getText(),tbObat.getSelectedRow(),21);                
                tbObat.setValueAt(AlatBantuDipakai.getSelectedItem().toString(),tbObat.getSelectedRow(),22);                
                tbObat.setValueAt(SedangMenyusui.getSelectedItem().toString()+", "+KetSedangMenyusui.getText(),tbObat.getSelectedRow(),23);                
                tbObat.setValueAt(RTranfusi.getText(),tbObat.getSelectedRow(),24);                
                tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),25);                
                tbObat.setValueAt(KebiasaanMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),26);                
                tbObat.setValueAt(KebiasaanJumlahRokok.getText(),tbObat.getSelectedRow(),27);                
                tbObat.setValueAt(KebiasaanAlkohol.getSelectedItem().toString(),tbObat.getSelectedRow(),28);                
                tbObat.setValueAt(KebiasaanJumlahAlkohol.getText(),tbObat.getSelectedRow(),29);                
                tbObat.setValueAt(KebiasaanNarkoba.getSelectedItem().toString(),tbObat.getSelectedRow(),30);                
                tbObat.setValueAt(OlahRaga.getSelectedItem().toString(),tbObat.getSelectedRow(),31);                
                tbObat.setValueAt(KesadaranMental.getText(),tbObat.getSelectedRow(),32);                
                tbObat.setValueAt(KeadaanMentalUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),33);                
                tbObat.setValueAt(GCS.getText(),tbObat.getSelectedRow(),34);                
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),35);                
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),36);                
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),37);                
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),38);                
                tbObat.setValueAt(SpO2.getText(),tbObat.getSelectedRow(),39);                
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),40);                
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),41);                
                tbObat.setValueAt(SistemSarafKepala.getSelectedItem().toString()+", "+KetSistemSarafKepala.getText(),tbObat.getSelectedRow(),42);                
                tbObat.setValueAt(SistemSarafWajah.getSelectedItem().toString()+", "+KetSistemSarafWajah.getText(),tbObat.getSelectedRow(),43);                
                tbObat.setValueAt(SistemSarafLeher.getSelectedItem().toString(),tbObat.getSelectedRow(),44);                
                tbObat.setValueAt(SistemSarafKejang.getSelectedItem().toString()+", "+KetSistemSarafKejang.getText(),tbObat.getSelectedRow(),45);                
                tbObat.setValueAt(SistemSarafSensorik.getSelectedItem().toString(),tbObat.getSelectedRow(),46);                
                tbObat.setValueAt(KardiovaskularPulsasi.getSelectedItem().toString(),tbObat.getSelectedRow(),47);                
                tbObat.setValueAt(KardiovaskularSirkulasi.getSelectedItem().toString()+", "+KetKardiovaskularSirkulasi.getText(),tbObat.getSelectedRow(),48);                
                tbObat.setValueAt(KardiovaskularDenyutNadi.getSelectedItem().toString(),tbObat.getSelectedRow(),49);                
                tbObat.setValueAt(RespirasiRetraksi.getSelectedItem().toString(),tbObat.getSelectedRow(),50);                
                tbObat.setValueAt(RespirasiPolaNafas.getSelectedItem().toString(),tbObat.getSelectedRow(),51);                
                tbObat.setValueAt(RespirasiSuaraNafas.getSelectedItem().toString(),tbObat.getSelectedRow(),52);                
                tbObat.setValueAt(RespirasiBatuk.getSelectedItem().toString(),tbObat.getSelectedRow(),53);                
                tbObat.setValueAt(RespirasiVolume.getSelectedItem().toString(),tbObat.getSelectedRow(),54);                
                tbObat.setValueAt(RespirasiJenisPernafasan.getSelectedItem()+", "+KetRespirasiJenisPernafasan.getText(),tbObat.getSelectedRow(),55);                
                tbObat.setValueAt(RespirasiIrama.getSelectedItem().toString(),tbObat.getSelectedRow(),56);                
                tbObat.setValueAt(GastrointestinalMulut.getSelectedItem().toString()+", "+KetGastrointestinalMulut.getText(),tbObat.getSelectedRow(),57);                
                tbObat.setValueAt(GastrointestinalLidah.getSelectedItem()+", "+KetGastrointestinalLidah.getText(),tbObat.getSelectedRow(),58);                
                tbObat.setValueAt(GastrointestinalGigi.getSelectedItem().toString()+", "+KetGastrointestinalGigi.getText(),tbObat.getSelectedRow(),59);                
                tbObat.setValueAt(GastrointestinalTenggorakan.getSelectedItem().toString()+", "+KetGastrointestinalTenggorakan.getText(),tbObat.getSelectedRow(),60);                
                tbObat.setValueAt(GastrointestinalAbdomen.getSelectedItem().toString()+", "+KetGastrointestinalAbdomen.getText(),tbObat.getSelectedRow(),61);                
                tbObat.setValueAt(GastrointestinalUsus.getSelectedItem().toString(),tbObat.getSelectedRow(),62);                
                tbObat.setValueAt(GastrointestinalAnus.getSelectedItem().toString(),tbObat.getSelectedRow(),63);                
                tbObat.setValueAt(NeurologiSensorik.getSelectedItem().toString(),tbObat.getSelectedRow(),64);                
                tbObat.setValueAt(NeurologiPenglihatan.getSelectedItem().toString()+", "+KetNeurologiPenglihatan.getText(),tbObat.getSelectedRow(),65);                
                tbObat.setValueAt(NeurologiAlatBantuPenglihatan.getSelectedItem().toString(),tbObat.getSelectedRow(),66);                
                tbObat.setValueAt(NeurologiMotorik.getSelectedItem().toString(),tbObat.getSelectedRow(),67);                
                tbObat.setValueAt(NeurologiPendengaran.getSelectedItem().toString(),tbObat.getSelectedRow(),68);                
                tbObat.setValueAt(NeurologiBicara.getSelectedItem().toString()+", "+KetNeurologiBicara.getText(),tbObat.getSelectedRow(),69);                
                tbObat.setValueAt(NeurologiOtot.getSelectedItem().toString(),tbObat.getSelectedRow(),70);                
                tbObat.setValueAt(IntegumentKulit.getSelectedItem().toString(),tbObat.getSelectedRow(),71);                
                tbObat.setValueAt(IntegumentWarnaKulit.getSelectedItem().toString(),tbObat.getSelectedRow(),72);                
                tbObat.setValueAt(IntegumentTurgor.getSelectedItem().toString(),tbObat.getSelectedRow(),73);                
                tbObat.setValueAt(IntegumentDecubitus.getSelectedItem().toString(),tbObat.getSelectedRow(),74);                
                tbObat.setValueAt(MuskuloskletalOedema.getSelectedItem()+", "+KetMuskuloskletalOedema.getText(),tbObat.getSelectedRow(),75);                
                tbObat.setValueAt(MuskuloskletalPegerakanSendi.getSelectedItem().toString(),tbObat.getSelectedRow(),76);                
                tbObat.setValueAt(MuskuloskletalKekuatanOtot.getSelectedItem().toString(),tbObat.getSelectedRow(),77);                
                tbObat.setValueAt(MuskuloskletalFraktur.getSelectedItem()+", "+KetMuskuloskletalFraktur.getText(),tbObat.getSelectedRow(),78);                
                tbObat.setValueAt(MuskuloskletalNyeriSendi.getSelectedItem()+", "+KetMuskuloskletalNyeriSendi.getText(),tbObat.getSelectedRow(),79);                
                tbObat.setValueAt(BAB.getText(),tbObat.getSelectedRow(),80);                
                tbObat.setValueAt(XBAB.getText(),tbObat.getSelectedRow(),81);                
                tbObat.setValueAt(KBAB.getText(),tbObat.getSelectedRow(),82);                
                tbObat.setValueAt(WBAB.getText(),tbObat.getSelectedRow(),83);                
                tbObat.setValueAt(BAK.getText(),tbObat.getSelectedRow(),84);                
                tbObat.setValueAt(XBAK.getText(),tbObat.getSelectedRow(),85);                
                tbObat.setValueAt(WBAK.getText(),tbObat.getSelectedRow(),86);                
                tbObat.setValueAt(LBAK.getText(),tbObat.getSelectedRow(),87);                
                tbObat.setValueAt(PolaAktifitasMandi.getSelectedItem().toString(),tbObat.getSelectedRow(),88);                
                tbObat.setValueAt(PolaAktifitasMakan.getSelectedItem().toString(),tbObat.getSelectedRow(),89);                
                tbObat.setValueAt(PolaAktifitasBerpakaian.getSelectedItem().toString(),tbObat.getSelectedRow(),90);                
                tbObat.setValueAt(PolaAktifitasEliminasi.getSelectedItem().toString(),tbObat.getSelectedRow(),91);                
                tbObat.setValueAt(PolaAktifitasBerpindah.getSelectedItem().toString(),tbObat.getSelectedRow(),92);                
                tbObat.setValueAt(PolaNutrisiPorsi.getText(),tbObat.getSelectedRow(),93);                
                tbObat.setValueAt(PolaNutrisiFrekuensi.getText(),tbObat.getSelectedRow(),94);                
                tbObat.setValueAt(PolaNutrisiJenis.getText(),tbObat.getSelectedRow(),95);                
                tbObat.setValueAt(PolaTidurLama.getText(),tbObat.getSelectedRow(),96);                
                tbObat.setValueAt(PolaTidurGangguan.getSelectedItem().toString(),tbObat.getSelectedRow(),97);                
                tbObat.setValueAt(AktifitasSehari2.getSelectedItem().toString(),tbObat.getSelectedRow(),98);                
                tbObat.setValueAt(Berjalan.getSelectedItem()+", "+KeteranganBerjalan.getText(),tbObat.getSelectedRow(),99);                
                tbObat.setValueAt(Aktifitas.getSelectedItem().toString(),tbObat.getSelectedRow(),100);                
                tbObat.setValueAt(AlatAmbulasi.getSelectedItem().toString(),tbObat.getSelectedRow(),101);                
                tbObat.setValueAt(EkstrimitasAtas.getSelectedItem()+", "+KeteranganEkstrimitasAtas.getText(),tbObat.getSelectedRow(),102);                
                tbObat.setValueAt(EkstrimitasBawah.getSelectedItem()+", "+KeteranganEkstrimitasBawah.getText(),tbObat.getSelectedRow(),103);                
                tbObat.setValueAt(KemampuanMenggenggam.getSelectedItem()+", "+KeteranganKemampuanMenggenggam.getText(),tbObat.getSelectedRow(),104);                
                tbObat.setValueAt(KemampuanKoordinasi.getSelectedItem()+", "+KeteranganKemampuanKoordinasi.getText(),tbObat.getSelectedRow(),105);                
                tbObat.setValueAt(KesimpulanGangguanFungsi.getSelectedItem().toString(),tbObat.getSelectedRow(),106);                
                tbObat.setValueAt(KondisiPsikologis.getSelectedItem().toString(),tbObat.getSelectedRow(),107);                
                tbObat.setValueAt(AdakahPerilaku.getSelectedItem()+", "+KeteranganAdakahPerilaku.getText(),tbObat.getSelectedRow(),108);                
                tbObat.setValueAt(GangguanJiwa.getSelectedItem().toString(),tbObat.getSelectedRow(),109);                
                tbObat.setValueAt(HubunganAnggotaKeluarga.getSelectedItem().toString(),tbObat.getSelectedRow(),110);                
                tbObat.setValueAt(Agama.getText(),tbObat.getSelectedRow(),111);                
                tbObat.setValueAt(TinggalDengan.getSelectedItem()+", "+KeteranganTinggalDengan.getText(),tbObat.getSelectedRow(),112);                
                tbObat.setValueAt(PekerjaanPasien.getText(),tbObat.getSelectedRow(),113);                
                tbObat.setValueAt(CaraBayar.getText(),tbObat.getSelectedRow(),114);                
                tbObat.setValueAt(NilaiKepercayaan.getSelectedItem()+", "+KeteranganNilaiKepercayaan.getText(),tbObat.getSelectedRow(),115);                
                tbObat.setValueAt(Bahasa.getText(),tbObat.getSelectedRow(),116);                
                tbObat.setValueAt(PendidikanPasien.getText(),tbObat.getSelectedRow(),117);                
                tbObat.setValueAt(PendidikanPJ.getSelectedItem().toString(),tbObat.getSelectedRow(),118);                
                tbObat.setValueAt(EdukasiPsikolgis.getSelectedItem()+", "+KeteranganEdukasiPsikologis.getText(),tbObat.getSelectedRow(),119);                
                tbObat.setValueAt(Nyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),120);                
                tbObat.setValueAt(Provokes.getSelectedItem()+", "+KetProvokes.getText(),tbObat.getSelectedRow(),121);                
                tbObat.setValueAt(Quality.getSelectedItem()+", "+KetQuality.getText(),tbObat.getSelectedRow(),122);                
                tbObat.setValueAt(Lokasi.getText(),tbObat.getSelectedRow(),123);                
                tbObat.setValueAt(Menyebar.getSelectedItem().toString(),tbObat.getSelectedRow(),124);                
                tbObat.setValueAt(SkalaNyeri.getSelectedItem().toString(),tbObat.getSelectedRow(),125);                
                tbObat.setValueAt(Durasi.getText(),tbObat.getSelectedRow(),126);                
                tbObat.setValueAt(NyeriHilang.getSelectedItem()+", "+KetNyeri.getText(),tbObat.getSelectedRow(),127);                
                tbObat.setValueAt(PadaDokter.getSelectedItem()+", "+KetPadaDokter.getText(),tbObat.getSelectedRow(),128);                
                tbObat.setValueAt(SkalaResiko1.getSelectedItem().toString(),tbObat.getSelectedRow(),129);                
                tbObat.setValueAt(NilaiResiko1.getText(),tbObat.getSelectedRow(),130);                
                tbObat.setValueAt(SkalaResiko2.getSelectedItem().toString(),tbObat.getSelectedRow(),131);                
                tbObat.setValueAt(NilaiResiko2.getText(),tbObat.getSelectedRow(),132);                
                tbObat.setValueAt(SkalaResiko3.getSelectedItem().toString(),tbObat.getSelectedRow(),133);                
                tbObat.setValueAt(NilaiResiko3.getText(),tbObat.getSelectedRow(),134);                
                tbObat.setValueAt(SkalaResiko4.getSelectedItem().toString(),tbObat.getSelectedRow(),135);                
                tbObat.setValueAt(NilaiResiko4.getText(),tbObat.getSelectedRow(),136);                
                tbObat.setValueAt(SkalaResiko5.getSelectedItem().toString(),tbObat.getSelectedRow(),137);                
                tbObat.setValueAt(NilaiResiko5.getText(),tbObat.getSelectedRow(),138);                
                tbObat.setValueAt(SkalaResiko6.getSelectedItem().toString(),tbObat.getSelectedRow(),139);                
                tbObat.setValueAt(NilaiResiko6.getText(),tbObat.getSelectedRow(),140);                
                tbObat.setValueAt(NilaiResikoTotal.getText(),tbObat.getSelectedRow(),141);                
                tbObat.setValueAt(SkalaSydney1.getSelectedItem().toString(),tbObat.getSelectedRow(),142);                
                tbObat.setValueAt(NilaiSydney1.getText(),tbObat.getSelectedRow(),143);                
                tbObat.setValueAt(SkalaSydney2.getSelectedItem().toString(),tbObat.getSelectedRow(),144);                
                tbObat.setValueAt(NilaiSydney2.getText(),tbObat.getSelectedRow(),145);                
                tbObat.setValueAt(SkalaSydney3.getSelectedItem().toString(),tbObat.getSelectedRow(),146);                
                tbObat.setValueAt(NilaiSydney3.getText(),tbObat.getSelectedRow(),147);                
                tbObat.setValueAt(SkalaSydney4.getSelectedItem().toString(),tbObat.getSelectedRow(),148);                
                tbObat.setValueAt(NilaiSydney4.getText(),tbObat.getSelectedRow(),149);                
                tbObat.setValueAt(SkalaSydney5.getSelectedItem().toString(),tbObat.getSelectedRow(),150);                
                tbObat.setValueAt(NilaiSydney5.getText(),tbObat.getSelectedRow(),151);                
                tbObat.setValueAt(SkalaSydney6.getSelectedItem().toString(),tbObat.getSelectedRow(),152);                
                tbObat.setValueAt(NilaiSydney6.getText(),tbObat.getSelectedRow(),153);                
                tbObat.setValueAt(SkalaSydney7.getSelectedItem().toString(),tbObat.getSelectedRow(),154);                
                tbObat.setValueAt(NilaiSydney7.getText(),tbObat.getSelectedRow(),155);                
                tbObat.setValueAt(SkalaSydney8.getSelectedItem().toString(),tbObat.getSelectedRow(),156);                
                tbObat.setValueAt(NilaiSydney8.getText(),tbObat.getSelectedRow(),157);                
                tbObat.setValueAt(SkalaSydney9.getSelectedItem().toString(),tbObat.getSelectedRow(),158);                
                tbObat.setValueAt(NilaiSydney9.getText(),tbObat.getSelectedRow(),159);                
                tbObat.setValueAt(SkalaSydney10.getSelectedItem().toString(),tbObat.getSelectedRow(),160);                
                tbObat.setValueAt(NilaiSydney10.getText(),tbObat.getSelectedRow(),161);                
                tbObat.setValueAt(SkalaSydney11.getSelectedItem().toString(),tbObat.getSelectedRow(),162);                
                tbObat.setValueAt(NilaiSydney11.getText(),tbObat.getSelectedRow(),163);                
                tbObat.setValueAt(NilaiSydneyTotal.getText(),tbObat.getSelectedRow(),164);                
                tbObat.setValueAt(SkalaGizi1.getSelectedItem().toString(),tbObat.getSelectedRow(),165);                
                tbObat.setValueAt(NilaiGizi1.getText(),tbObat.getSelectedRow(),166);                
                tbObat.setValueAt(SkalaGizi2.getSelectedItem().toString(),tbObat.getSelectedRow(),167);                
                tbObat.setValueAt(NilaiGizi2.getText(),tbObat.getSelectedRow(),168);                
                tbObat.setValueAt(NilaiGiziTotal.getText(),tbObat.getSelectedRow(),169);                
                tbObat.setValueAt(DiagnosaKhususGizi.getSelectedItem().toString(),tbObat.getSelectedRow(),170);                
                tbObat.setValueAt(KeteranganDiagnosaKhususGizi.getText(),tbObat.getSelectedRow(),171);                
                tbObat.setValueAt(DiketahuiDietisen.getSelectedItem().toString(),tbObat.getSelectedRow(),172);                
                tbObat.setValueAt(KeteranganDiketahuiDietisen.getText(),tbObat.getSelectedRow(),173);                
                tbObat.setValueAt(Rencana.getText(),tbObat.getSelectedRow(),174);  
                Sequel.meghapus("penilaian_awal_keperawatan_ranap_bayi_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Sequel.meghapus("penilaian_awal_keperawatan_ranap_bayi_rencana","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ranap_bayi_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailMasalah.addRow(new String[]{
                                tbMasalahKeperawatan.getValueAt(i,1).toString(),tbMasalahKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ranap_bayi_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailRencana.addRow(new String[]{
                                tbRencanaKeperawatan.getValueAt(i,1).toString(),tbRencanaKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                DetailRencana.setText(Rencana.getText());
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
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

    

    private void simpan() {
        if(Sequel.menyimpantf("penilaian_awal_keperawatan_ranap_bayi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",186,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),KetAnamnesis.getText(),TibadiRuang.getSelectedItem().toString(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),UsiaTengkurap.getText(),UsiaDuduk.getText(),UsiaBerdiri.getText(),UsiaGigi.getText(),UsiaBerjalan.getText(),UsiaBicara.getText(),UsiaMembaca.getText(),UsiaMenulis.getText(),
                GangguanEmosi.getText(),Anakke.getText(),DariSaudara.getText(),CaraKelahiran.getSelectedItem().toString(),KetCaraKelahiran.getText(),UmurKelahiran.getSelectedItem().toString(),KelainanBawaan.getSelectedItem().toString(),KetKelainanBawaan.getText(),BBLahir.getText(),PBLahir.getText(),RiwayatPersalinanLainnya.getText(),Kesadaran.getSelectedItem().toString(),GCS.getText(),TD.getText(),RR.getText(),Suhu.getText(),Nadi.getText(),BB.getText(),TB.getText(),LP.getText(),
                LK.getText(),LD.getText(),SistemSarafKepala.getSelectedItem().toString(),KetSistemSarafKepala.getText(),SistemSarafWajah.getSelectedItem().toString(),KetSistemSarafWajah.getText(),SistemSarafLeher.getSelectedItem().toString(),SistemSarafKejang.getSelectedItem().toString(),KetSistemSarafKejang.getText(),SistemSarafSensorik.getSelectedItem().toString(),KardiovaskularPulsasi.getSelectedItem().toString(),KardiovaskularSirkulasi.getSelectedItem().toString(),
                KetKardiovaskularSirkulasi.getText(),KardiovaskularDenyutNadi.getSelectedItem().toString(),RespirasiRetraksi.getSelectedItem().toString(),RespirasiPolaNafas.getSelectedItem().toString(),RespirasiSuaraNafas.getSelectedItem().toString(),RespirasiBatuk.getSelectedItem().toString(),RespirasiVolume.getSelectedItem().toString(),RespirasiJenisPernafasan.getSelectedItem().toString(),KetRespirasiJenisPernafasan.getText(),RespirasiIrama.getSelectedItem().toString(),
                GastrointestinalMulut.getSelectedItem().toString(),KetGastrointestinalMulut.getText(),GastrointestinalTenggorakan.getSelectedItem().toString(),KetGastrointestinalTenggorakan.getText(),GastrointestinalLidah.getSelectedItem().toString(),KetGastrointestinalLidah.getText(),GastrointestinalAbdomen.getSelectedItem().toString(),KetGastrointestinalAbdomen.getText(),GastrointestinalGigi.getSelectedItem().toString(),KetGastrointestinalGigi.getText(),GastrointestinalUsus.getSelectedItem().toString(),
                GastrointestinalAnus.getSelectedItem().toString(),NeurologiSensorik.getSelectedItem().toString(),NeurologiPenglihatan.getSelectedItem().toString(),KetNeurologiPenglihatan.getText(),NeurologiAlatBantuPenglihatan.getSelectedItem().toString(),NeurologiMotorik.getSelectedItem().toString(),NeurologiPendengaran.getSelectedItem().toString(),NeurologiBicara.getSelectedItem().toString(),KetNeurologiBicara.getText(),NeurologiOtot.getSelectedItem().toString(),IntegumentKulit.getSelectedItem().toString(),
                IntegumentWarnaKulit.getSelectedItem().toString(),IntegumentTurgor.getSelectedItem().toString(),IntegumentDecubitus.getSelectedItem().toString(),MuskuloskletalOedema.getSelectedItem().toString(),KetMuskuloskletalOedema.getText(),MuskuloskletalPegerakanSendi.getSelectedItem().toString(),MuskuloskletalKekuatanOtot.getSelectedItem().toString(),MuskuloskletalFraktur.getSelectedItem().toString(),KetMuskuloskletalFraktur.getText(),MuskuloskletalNyeriSendi.getSelectedItem().toString(),
                KetMuskuloskletalNyeriSendi.getText(),"eliminasi_bab_frekuensi","eliminasi_bab_frekuensi_per","eliminasi_bab_konsistesi","eliminasi_bab_warna","eliminasi_bak_frekuensi","eliminasi_bak_frekuensi_per","eliminasi_bak_warna","eliminasi_bak_lainlain","psiko_kondisi","psiko_perilaku","psiko_perilaku_keterangan","psiko_gangguan_jiwa","psiko_hubungan_pasien","psiko_tinggal_dengan","psiko_tinggal_dengan_keterangan","psiko_pekerjaan_pj","psiko_nilai_kepercayaan","psiko_nilai_kepercayaan_keterangan","psiko_pendidikan_pj","psiko_edukasi","psiko_edukasi_keterangan","edukasi_bahasa","edukasi_baca_tulis","edukasi_penerjemah","edukasi_penerjemah_keterangan","edukasi_terdapat_hambatan","edukasi_hambatan_belajar","edukasi_hambatan_belajar_keterangan","edukasi_hambatan_bicara","edukasi_bahasa_isyarat","edukasi_cara_belajar","edukasi_menerima_informasi","edukasi_menerima_informasi_keterangan","edukasi_nutrisi","edukasi_penyakit","edukasi_pengobatan","edukasi_perawatan","skrining_gizi1","nilai_gizi1","skrining_gizi2","nilai_gizi2","skrining_gizi3","nilai_gizi3","skrining_gizi4","nilai_gizi4","total_nilai","keterangan_skrining_gizi","penilaian_humptydumpty_skala1","penilaian_humptydumpty_nilai1","penilaian_humptydumpty_skala2","penilaian_humptydumpty_nilai2","penilaian_humptydumpty_skala3","penilaian_humptydumpty_nilai3","penilaian_humptydumpty_skala4","penilaian_humptydumpty_nilai4","penilaian_humptydumpty_skala5","penilaian_humptydumpty_nilai5","penilaian_humptydumpty_skala6","penilaian_humptydumpty_nilai6","penilaian_humptydumpty_skala7","penilaian_humptydumpty_nilai7","penilaian_humptydumpty_totalnilai","hasil_skrining_penilaian_humptydumpty","nyeri_wajah","nyeri_nilai_wajah","nyeri_kaki","nyeri_nilai_kaki","nyeri_aktifitas","nyeri_nilai_aktifitas","nyeri_menangis","nyeri_nilai_menangis","nyeri_bersuara","nyeri_nilai_bersuara","nyeri_nilai_total","nyeri_kondisi","nyeri_lokasi","nyeri_durasi","nyeri_frekuensi","nyeri_hilang","nyeri_hilang_keterangan","nyeri_diberitahukan_pada_dokter","nyeri_diberitahukan_pada_dokter_keterangan","informasi_perencanaan_pulang","lama_ratarata","perencanaan_pulang","kondisi_klinis_pulang","perawatan_lanjutan_dirumah","cara_transportasi_pulang","transportasi_digunakan","rencana","nip1","nip2","kd_dokter"
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),KdPetugas2.getText(),NmPetugas2.getText(),KdDPJP.getText(),NmDPJP.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                    
                });
                for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                    if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ranap_bayi_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailMasalah.addRow(new String[]{
                                tbMasalahKeperawatan.getValueAt(i,1).toString(),tbMasalahKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                for (i = 0; i < tbRencanaKeperawatan.getRowCount(); i++) {
                    if(tbRencanaKeperawatan.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("penilaian_awal_keperawatan_ranap_bayi_rencana","?,?",2,new String[]{TNoRw.getText(),tbRencanaKeperawatan.getValueAt(i,1).toString()})==true){
                            tabModeDetailRencana.addRow(new String[]{
                                tbRencanaKeperawatan.getValueAt(i,1).toString(),tbRencanaKeperawatan.getValueAt(i,2).toString()
                            });
                        }
                    }
                }
                DetailRencana.setText(Rencana.getText());
                TNoRM1.setText(TNoRM.getText());
                TPasien1.setText(TPasien.getText());
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
        }
    }
    
    private void isTotalResikoJatuh(){
        try {
            NilaiResikoTotal.setText((Integer.parseInt(NilaiResiko1.getText())+Integer.parseInt(NilaiResiko2.getText())+Integer.parseInt(NilaiResiko3.getText())+Integer.parseInt(NilaiResiko4.getText())+Integer.parseInt(NilaiResiko5.getText())+Integer.parseInt(NilaiResiko6.getText())+Integer.parseInt(NilaiResiko7.getText()))+"");
            if(Integer.parseInt(NilaiResikoTotal.getText())<12){
                KeteranganTingkatRisiko.setText("Risiko Rendah 7 - 11");
            }else if(Integer.parseInt(NilaiResikoTotal.getText())>=12){
                KeteranganTingkatRisiko.setText("Risiko Tinggi >=12");
            }
        } catch (Exception e) {
            NilaiResikoTotal.setText("0");
            KeteranganTingkatRisiko.setText("Risiko Rendah 7 - 11");
        }
    }
    
    private void tampilImunisasi() {
        try {
            Valid.tabelKosong(tabModeImunisasi);
            ps=koneksi.prepareStatement(
                    "select master_imunisasi.kode_imunisasi,master_imunisasi.nama_imunisasi from master_imunisasi inner join riwayat_imunisasi on riwayat_imunisasi.kode_imunisasi=master_imunisasi.kode_imunisasi "+
                    "where riwayat_imunisasi.no_rkm_medis=? group by master_imunisasi.kode_imunisasi order by master_imunisasi.kode_imunisasi  ");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                while(rs.next()){
                    ke1=false;ke2=false;ke3=false;ke4=false;ke5=false;ke6=false;
                    ps2=koneksi.prepareStatement("select * from riwayat_imunisasi where riwayat_imunisasi.no_rkm_medis=? and riwayat_imunisasi.kode_imunisasi=?");
                    try {
                        ps2.setString(1,TNoRM.getText());
                        ps2.setString(2,rs.getString(1));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            if(rs2.getInt("no_imunisasi")==1){
                                ke1=true;
                            }
                            if(rs2.getInt("no_imunisasi")==2){
                                ke2=true;
                            }
                            if(rs2.getInt("no_imunisasi")==3){
                                ke3=true;
                            }
                            if(rs2.getInt("no_imunisasi")==4){
                                ke4=true;
                            }
                            if(rs2.getInt("no_imunisasi")==5){
                                ke5=true;
                            }
                            if(rs2.getInt("no_imunisasi")==6){
                                ke6=true;
                            }
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
                    
                    tabModeImunisasi.addRow(new Object[]{rs.getString(1),rs.getString(2),ke1,ke2,ke3,ke4,ke5,ke6});
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

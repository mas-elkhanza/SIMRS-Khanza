/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package simrskhanza;

import rekammedis.RMRiwayatPerawatan;
import surat.SuratKontrol;
import keuangan.DlgCariPerawatanRanap;
import keuangan.DlgCariPerawatanRanap2;
import inventory.DlgPemberianObat;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat2;
import inventory.DlgCariObat3;
import inventory.DlgCopyResep;
import inventory.DlgPeresepanDokter;
import inventory.DlgPermintaanResepPulang;
import inventory.DlgPermintaanStokPasien;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;
import permintaan.DlgBookingOperasi;
import permintaan.DlgPermintaanKonsultasiMedik;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanPelayananInformasiObat;
import permintaan.DlgPermintaanRadiologi;
import rekammedis.RMCari5SOAPTerakhir;
import rekammedis.RMCatatanADIMEGizi;
import rekammedis.RMCatatanAnastesiSedasi;
import rekammedis.RMCatatanPengkajianPaskaOperasi;
import rekammedis.RMCatatanPersalinan;
import rekammedis.RMChecklistKesiapanAnestesi;
import rekammedis.RMChecklistKriteriaKeluarHCU;
import rekammedis.RMChecklistKriteriaKeluarICU;
import rekammedis.RMChecklistKriteriaMasukHCU;
import rekammedis.RMChecklistKriteriaMasukICU;
import rekammedis.RMChecklistPemberianFibrinolitik;
import rekammedis.RMChecklistPostOperasi;
import rekammedis.RMChecklistPreOperasi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataCatatanCairanHemodialisa;
import rekammedis.RMDataCatatanCekGDS;
import rekammedis.RMDataCatatanKeperawatanRanap;
import rekammedis.RMDataCatatanKeseimbanganCairan;
import rekammedis.RMDataCatatanObservasiBayi;
import rekammedis.RMDataCatatanObservasiCHBP;
import rekammedis.RMDataCatatanObservasiHemodialisa;
import rekammedis.RMDataCatatanObservasiInduksiPersalinan;
import rekammedis.RMDataCatatanObservasiRanap;
import rekammedis.RMDataCatatanObservasiRanapKebidanan;
import rekammedis.RMDataCatatanObservasiRanapPostPartum;
import rekammedis.RMDataCatatanObservasiRestrainNonFarmakologi;
import rekammedis.RMDataCatatanObservasiVentilator;
import rekammedis.RMDataFollowUpDBD;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataMonitoringReaksiTranfusi;
import rekammedis.RMDataResumePasienRanap;
import rekammedis.RMDataSkriningGiziLanjut;
import rekammedis.RMHasilEndoskopiFaringLaring;
import rekammedis.RMHasilEndoskopiHidung;
import rekammedis.RMHasilEndoskopiTelinga;
import rekammedis.RMHasilPemeriksaanEKG;
import rekammedis.RMHasilPemeriksaanEcho;
import rekammedis.RMHasilPemeriksaanOCT;
import rekammedis.RMHasilPemeriksaanSlitLamp;
import rekammedis.RMHasilPemeriksaanUSG;
import rekammedis.RMHasilPemeriksaanUSGGynecologi;
import rekammedis.RMHasilPemeriksaanUSGNeonatus;
import rekammedis.RMHasilPemeriksaanUSGUrologi;
import rekammedis.RMHasilTindakanESWL;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMLaporanTindakan;
import rekammedis.RMMonitoringAldrettePascaAnestesi;
import rekammedis.RMMonitoringBromagePascaAnestesi;
import rekammedis.RMMonitoringStewardPascaAnestesi;
import rekammedis.RMPelaksanaanInformasiEdukasi;
import rekammedis.RMPemantauanMEOWS;
import rekammedis.RMPemantauanPEWS;
import rekammedis.RMPemantauanEWSD;
import rekammedis.RMPemantauanEWSNeonatus;
import rekammedis.RMPengkajianRestrain;
import rekammedis.RMPenilaianAwalKeperawatanKebidananRanap;
import rekammedis.RMPenilaianAwalKeperawatanRanap;
import rekammedis.RMPenilaianAwalKeperawatanRanapBayiAnak;
import rekammedis.RMPenilaianAwalKeperawatanRanapNeonatus;
import rekammedis.RMPenilaianAwalMedisHemodialisa;
import rekammedis.RMPenilaianAwalMedisRanapDewasa;
import rekammedis.RMPenilaianAwalMedisRanapKandungan;
import rekammedis.RMPenilaianAwalMedisRanapNeonatus;
import rekammedis.RMPenilaianBayiBaruLahir;
import rekammedis.RMPenilaianDerajatDehidrasi;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMPenilaianKorbanKekerasan;
import rekammedis.RMPenilaianLanjutanRisikoJatuhAnak;
import rekammedis.RMPenilaianLanjutanRisikoJatuhDewasa;
import rekammedis.RMPenilaianLanjutanRisikoJatuhGeriatri;
import rekammedis.RMPenilaianLanjutanRisikoJatuhLansia;
import rekammedis.RMPenilaianLanjutanRisikoJatuhPsikiatri;
import rekammedis.RMPenilaianLanjutanSkriningFungsional;
import rekammedis.RMPenilaianLevelKecemasanRanapAnak;
import rekammedis.RMPenilaianPasienImunitasRendah;
import rekammedis.RMPenilaianPasienPenyakitMenular;
import rekammedis.RMPenilaianPasienTerminal;
import rekammedis.RMPenilaianPreAnastesi;
import rekammedis.RMPenilaianPreInduksi;
import rekammedis.RMPenilaianPreOperasi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMPenilaianPsikologiKlinis;
import rekammedis.RMPenilaianRisikoDekubitus;
import rekammedis.RMPenilaianRisikoJatuhNeonatus;
import rekammedis.RMPenilaianTambahanBunuhDiri;
import rekammedis.RMPenilaianTambahanGeriatri;
import rekammedis.RMPenilaianTambahanMelarikanDiri;
import rekammedis.RMPenilaianTambahanPerilakuKekerasan;
import rekammedis.RMPenilaianUlangNyeri;
import rekammedis.RMPerencanaanPemulangan;
import rekammedis.RMRekonsiliasiObat;
import rekammedis.RMSignInSebelumAnastesi;
import rekammedis.RMSignOutSebelumMenutupLuka;
import rekammedis.RMSkriningNutrisiAnak;
import rekammedis.RMSkriningNutrisiDewasa;
import rekammedis.RMSkriningNutrisiLansia;
import rekammedis.RMTimeOutSebelumInsisi;
import rekammedis.RMTransferPasienAntarRuang;
import simrskhanza.DlgCariPasien;
import simrskhanza.DlgCatatan;
import simrskhanza.DlgRujuk;

/**
 *
 * @author perpustakaan
 */
public final class DlgRawatInap extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr,tabModePr,tabModeDrPr,
            tabModePemeriksaan,tabModeObstetri,tabModeGinekologi;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    public  DlgCariPerawatanRanap perawatan=new DlgCariPerawatanRanap(null,false);
    public  DlgCariPerawatanRanap2 perawatan2=new DlgCariPerawatanRanap2(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);  
    public  DlgCariPasien pasien=new DlgCariPasien(null,false);
    private PreparedStatement ps,ps2,ps3,ps4,ps5,psrekening,ps6;
    private ResultSet rs,rsrekening;
    private int i=0,tinggi=0;
    private boolean sukses=false;  
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttlpendapatan=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0;
    private String Suspen_Piutang_Tindakan_Ranap="",Tindakan_Ranap="",Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="",Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="",
            Beban_Jasa_Sarana_Tindakan_Ranap="",Utang_Jasa_Sarana_Tindakan_Ranap="",Beban_Jasa_Menejemen_Tindakan_Ranap="",Utang_Jasa_Menejemen_Tindakan_Ranap="",
            HPP_BHP_Tindakan_Ranap="",Persediaan_BHP_Tindakan_Ranap="",kode_poli="",kamar="",jenisbayar="",TANGGALMUNDUR="yes";

    /** Creates new form DlgRawatInap
     * @param parent
     * @param modal */
    public DlgRawatInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initRawatInap();

        this.setLocation(8,1);
        setSize(885,674);
        
        tabModeDr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","Tgl.Rawat","Jam Rawat","Biaya","Kode","Tarif Dokter","KSO","Jasa Sarana","BHP","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDr.setModel(tabModeDr);
        //tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","NIP","Petugas Yg Menangani","Tgl.Rawat","Jam Rawat","Biaya","Kode","Tarif Perawat","KSO","Jasa Sarana","BHP","Menejemen"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatPr.setModel(tabModePr);
        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDrPr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter",
            "Dokter Yg Menangani","NIP","Petugas Yg Menangani","Tgl.Rawat","Jam Rawat",
            "Biaya","Kode","Tarif Dokter","Tarif Petugas","KSO","Jasa Sarana","BHP","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrPr.setModel(tabModeDrPr);
        tbRawatDrPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbRawatDrPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDrPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaan=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tgl.Rawat","Jam","Suhu(C)","Tensi","Nadi(/menit)",
            "Respirasi(/menit)","Tinggi(Cm)","Berat(Kg)","SpO2(%)","GCS(E,V,M)","Kesadaran","Subjek","Objek","Alergi","Asesmen","Plan",
            "Instruksi","Evaluasi","NIP","Dokter/Paramedis","Profesi/Jabatan"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(45);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(73);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(63);
            }else if(i==11){
                column.setPreferredWidth(57);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(64);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(180);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(130);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(100);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObstetri=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M","Nama Pasien","Tgl.Rawat","Jam Rawat",
            "Tinggi Fundus","Janin", "Letak","Panggul","Denyut","Kontraksi",
            "Kualitas Mnt", "Kualitas Detik","Fluksus","Albus","Vulva",
            "Portio","Dalam","Tebal","Arah","Pembukaan","Penurunan",
            "Denominator","Ketuban","Feto"}) {
             @Override public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        
        tbPemeriksaanObstetri.setModel(tabModeObstetri);
        tbPemeriksaanObstetri.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaanObstetri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 26; i++) {
            TableColumn column = tbPemeriksaanObstetri.getColumnModel().getColumn(i);
            if(i==0) {
                column.setPreferredWidth(20);
            }else if(i==1) {
                column.setPreferredWidth(105);
            }else if(i==2) {
                column.setPreferredWidth(70);
            }else if(i==3) {
                column.setPreferredWidth(180);
            }else if(i==4) {
                column.setPreferredWidth(80);
            }else if(i==5) {
                column.setPreferredWidth(70);
            }else if(i==6) {
                column.setPreferredWidth(80);
            }else if(i==7) {
                column.setPreferredWidth(60);
            }else if(i==8) {
                column.setPreferredWidth(60);
            }else if(i==9) {
                column.setPreferredWidth(60);
            }else if(i==10) {
                column.setPreferredWidth(60);
            }else if(i==11) {
                column.setPreferredWidth(60);
            }else if(i==12) {
                column.setPreferredWidth(70);
            }else if(i==13) {
                column.setPreferredWidth(80);
            }else if(i==14) {
                column.setPreferredWidth(50);
            }else if(i==15) {
                column.setPreferredWidth(40);
            }else if(i==16) {
                column.setPreferredWidth(170);
            }else if(i==17) {
                column.setPreferredWidth(170);
            }else if(i==18) {
                column.setPreferredWidth(60);
            }else if(i==19) {
                column.setPreferredWidth(50);
            }else if(i==20) {
                column.setPreferredWidth(60);
            }else if(i==21) {
                column.setPreferredWidth(170);
            }else if(i==22) {
                column.setPreferredWidth(170);
            }else if(i==23) {
                column.setPreferredWidth(170);
            }else if(i==24) {
                column.setPreferredWidth(50);
            }else if(i==25) {
                column.setPreferredWidth(70);
            }
        }
        tbPemeriksaanObstetri.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeGinekologi=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M","Nama Pasien","Tgl.Rawat","Jam Rawat",
            "Inpeksi","Inspeksi Vulva/Uretra/Vagina", "Inspekulo","Fluxus",
            "Fluor Albus", "Inspekulo Vulva/Vagina", "Inspekulo Portio", "Inspekulo Sondage",
            "Pemeriksaan Dalam Portio", "Pemeriksaan Dalam Bentuk","Pemeriksaan Dalam Cavum Uteri","Mobilitas",
            "Ukuran Cavum Uteri","Nyeri Tekan","Pemeriksaan Dalam Adnexa Kanan","Pemeriksaan Dalam Adnexa Kiri",
            "Pemeriksaan Dalam Cavum Douglas"}) {
             @Override public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class
                 
             };
            @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
                }
            };
        
        tbPemeriksaanGinekologi.setModel(tabModeGinekologi);
        tbPemeriksaanGinekologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaanGinekologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 23; i++) {
            TableColumn column = tbPemeriksaanGinekologi.getColumnModel().getColumn(i);
            if(i==0) {
                column.setPreferredWidth(20);
            }else if(i==1) {
                column.setPreferredWidth(105);
            }else if(i==2) {
                column.setPreferredWidth(70);
            }else if(i==3) {
                column.setPreferredWidth(180);
            }else if(i==4) {
                column.setPreferredWidth(80);
            }else if(i==5) {
                column.setPreferredWidth(70);
            }else if(i==6) {
                column.setPreferredWidth(200);
            }else if(i==7) {
                column.setPreferredWidth(200);
            }else if(i==8) {
                column.setPreferredWidth(200);
            }else if(i==9) {
                column.setPreferredWidth(42);
            }else if(i==10) {
                column.setPreferredWidth(62);
            }else if(i==11) {
                column.setPreferredWidth(200);
            }else if(i==12) {
                column.setPreferredWidth(200);
            }else if(i==13) {
                column.setPreferredWidth(200);
            }else if(i==14) {
                column.setPreferredWidth(200);
            }else if(i==15) {
                column.setPreferredWidth(200);
            }else if(i==16) {
                column.setPreferredWidth(200);
            }else if(i==17) {
                column.setPreferredWidth(50);
            }else if(i==18) {
                column.setPreferredWidth(200);
            }else if(i==19) {
                column.setPreferredWidth(67);
            }else if(i==20) {
                column.setPreferredWidth(200);
            }else if(i==21) {
                column.setPreferredWidth(200);
            }else if(i==22) {
                column.setPreferredWidth(200);
            }  
        }
        tbPemeriksaanGinekologi.setDefaultRenderer(Object.class, new WarnaTable());

        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte)20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte)20).getKata(KdDok2));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));        
        TKdPrw.setDocument(new batasInput((byte)15).getKata(TKdPrw));
        TKdPrwPetugas.setDocument(new batasInput((byte)15).getKata(TKdPrwPetugas));
        TKdPrwDokterPetugas.setDocument(new batasInput((byte)15).getKata(TKdPrwDokterPetugas));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TLetak.setDocument(new batasInput((byte)50).getKata(TLetak));
        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TCariPasien.setDocument(new batasInput((byte)20).getKata(TCariPasien));
        TKeluhan.setDocument(new batasInput((int)2000).getKata(TKeluhan));  
        TPemeriksaan.setDocument(new batasInput((int)2000).getKata(TPemeriksaan));    
        TPenilaian.setDocument(new batasInput((int)2000).getKata(TPenilaian));  
        TEvaluasi.setDocument(new batasInput((int)2000).getKata(TEvaluasi));
        TindakLanjut.setDocument(new batasInput((int)2000).getKata(TindakLanjut));  
        TInstruksi.setDocument(new batasInput((int)2000).getKata(TInstruksi));      
        TTinggi.setDocument(new batasInput((byte)5).getKata(TTinggi));
        TBerat.setDocument(new batasInput((byte)5).getKata(TBerat));
        SpO2.setDocument(new batasInput((byte)3).getOnlyAngka(SpO2));
        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));      
        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS)); 
        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));       
        TInspeksi.setDocument(new batasInput((byte)50).getKata(TInspeksi));
        TInspeksiVulva.setDocument(new batasInput((byte)50).getKata(TInspeksiVulva));
        TInspekuloGine.setDocument(new batasInput((byte)50).getKata(TInspekuloGine));
        TVulvaInspekulo.setDocument(new batasInput((byte)50).getKata(TVulvaInspekulo));
        TPortioInspekulo.setDocument(new batasInput((byte)50).getKata(TPortioInspekulo));
        TSondage.setDocument(new batasInput((byte)50).getKata(TSondage));
        TPortioDalam.setDocument(new batasInput((byte)50).getKata(TPortioDalam));
        TBentuk.setDocument(new batasInput((byte)50).getKata(TBentuk));
        TCavumUteri.setDocument(new batasInput((byte)50).getKata(TCavumUteri));
        TUkuran.setDocument(new batasInput((byte)50).getKata(TUkuran));
        TAdnexaKanan.setDocument(new batasInput((byte)50).getKata(TAdnexaKanan));
        TAdnexaKiri.setDocument(new batasInput((byte)50).getKata(TAdnexaKiri));
        TCavumDouglas.setDocument(new batasInput((byte)50).getKata(TCavumDouglas));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampilDr();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampilPr();
                        }else if(TabRawat.getSelectedIndex()==2){
                            tampilDrPr();
                        }else if(TabRawat.getSelectedIndex()==3){
                            tampilPemeriksaan();
                        }else if(TabRawat.getSelectedIndex()==4){
                            tampilPemeriksaanObstetri();
                        }else if(TabRawat.getSelectedIndex()==5){
                            tampilPemeriksaanGinekologi();
                        }
                    }                        
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampilDr();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampilPr();
                        }else if(TabRawat.getSelectedIndex()==2){
                            tampilDrPr();
                        }else if(TabRawat.getSelectedIndex()==3){
                            tampilPemeriksaan();
                        }else if(TabRawat.getSelectedIndex()==4){
                            tampilPemeriksaanObstetri();
                        }else if(TabRawat.getSelectedIndex()==5){
                            tampilPemeriksaanGinekologi();
                        }
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        if(TabRawat.getSelectedIndex()==0){
                            tampilDr();
                        }else if(TabRawat.getSelectedIndex()==1){
                            tampilPr();
                        }else if(TabRawat.getSelectedIndex()==2){
                            tampilDrPr();
                        }else if(TabRawat.getSelectedIndex()==3){
                            tampilPemeriksaan();
                        }else if(TabRawat.getSelectedIndex()==4){
                            tampilPemeriksaanObstetri();
                        }else if(TabRawat.getSelectedIndex()==5){
                            tampilPemeriksaanGinekologi();
                        }
                    }
                }
            });
        } 
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(pasien.getTable().getSelectedRow()!= -1){                   
                        TCariPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),0).toString());
                    }   
                    TCariPasien.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
                    if(pegawai.getTable().getSelectedRow()!= -1){   
                        KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                        Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),3).toString());
                        KdPeg.requestFocus();                    
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
        
        perawatan.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(perawatan.dokter.getTable().getSelectedRow()!= -1){
                        if(TabRawat.getSelectedIndex()==0){
                            KdDok.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),1).toString());
                            KdDok.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            KdDok2.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),0).toString());
                            TDokter2.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(),1).toString());
                            KdDok2.requestFocus();
                        }  
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
        
        perawatan.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(perawatan.petugas.getTable().getSelectedRow()!= -1){                   
                        if(TabRawat.getSelectedIndex()==1){
                            kdptg.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),1).toString());
                            kdptg.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            kdptg2.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat2.setText(perawatan.petugas.getTable().getValueAt(perawatan.petugas.getTable().getSelectedRow(),1).toString());
                            kdptg2.requestFocus();
                        }
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
                
        perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(perawatan.getTable().getSelectedRow()!= -1){   
                        if(TabRawat.getSelectedIndex()==0){
                            TKdPrw.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                            TNmPrw.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                            TKdPrw.requestFocus();  
                        }else if(TabRawat.getSelectedIndex()==1){
                            TKdPrwPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                            TNmPrwPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                            TKdPrwPetugas.requestFocus(); 
                        }else if(TabRawat.getSelectedIndex()==2){
                            TKdPrwDokterPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                            TNmPrwDokterPetugas.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                            TKdPrwDokterPetugas.requestFocus(); 
                        }
                    }  
                    BtnCariActionPerformed(null);
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
             
        perawatan2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatInap")){
                    if(perawatan2.getTable().getSelectedRow()!= -1){ 
                        if(TabRawat.getSelectedIndex()==0){
                            TKdPrw.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),4).toString());   
                            TNmPrw.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),7).toString());
                            TKdPrw.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==1){
                            TKdPrwPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),4).toString());   
                            TNmPrwPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),7).toString());
                            TKdPrwPetugas.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            TKdPrwDokterPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),4).toString());   
                            TNmPrwDokterPetugas.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),7).toString());
                            TKdPrwDokterPetugas.requestFocus();
                        }
                    } 
                    BtnCariActionPerformed(null);
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
        
        ChkInput.setSelected(false);
        isForm(); 
        ChkInput1.setSelected(false);
        isForm2();
        ChkInput2.setSelected(false);
        isForm3(); 
        ChkAccor.setSelected(true);
        isMenu();
        jam();
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ranap=rsrekening.getString("Suspen_Piutang_Tindakan_Ranap");
                    Tindakan_Ranap=rsrekening.getString("Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Beban_KSO_Tindakan_Ranap=rsrekening.getString("Beban_KSO_Tindakan_Ranap");
                    Utang_KSO_Tindakan_Ranap=rsrekening.getString("Utang_KSO_Tindakan_Ranap");
                    Beban_Jasa_Sarana_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ranap");
                    Utang_Jasa_Sarana_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ranap");
                    Beban_Jasa_Menejemen_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ranap");
                    Utang_Jasa_Menejemen_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ranap");
                    HPP_BHP_Tindakan_Ranap=rsrekening.getString("HPP_BHP_Tindakan_Ranap");
                    Persediaan_BHP_Tindakan_Ranap=rsrekening.getString("Persediaan_BHP_Tindakan_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
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

        BagianRS = new javax.swing.JTextField();
        Bhp = new javax.swing.JTextField();
        JmDokter = new javax.swing.JTextField();
        JmPerawat = new javax.swing.JTextField();
        TTnd = new javax.swing.JTextField();
        KSO = new javax.swing.JTextField();
        Menejemen = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel20 = new widget.Label();
        TCariPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        jLabel11 = new widget.Label();
        TKdPrw = new widget.TextBox();
        TNmPrw = new widget.TextBox();
        btnTindakan = new widget.Button();
        btnTindakan3 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        panelGlass13 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        jLabel28 = new widget.Label();
        TKdPrwPetugas = new widget.TextBox();
        TNmPrwPetugas = new widget.TextBox();
        btnTindakan4 = new widget.Button();
        btnTindakan5 = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbRawatDrPr = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        jLabel29 = new widget.Label();
        TKdPrwDokterPetugas = new widget.TextBox();
        TNmPrwDokterPetugas = new widget.TextBox();
        btnTindakan6 = new widget.Button();
        btnTindakan7 = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        jLabel15 = new widget.Label();
        TAlergi = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        jLabel37 = new widget.Label();
        jLabel26 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        jLabel53 = new widget.Label();
        KdPeg = new widget.TextBox();
        TPegawai = new widget.TextBox();
        BtnSeekPegawai = new widget.Button();
        Jabatan = new widget.TextBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        TInstruksi = new widget.TextArea();
        jLabel56 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel22 = new widget.Label();
        TGCS = new widget.TextBox();
        jLabel58 = new widget.Label();
        cmbKesadaran = new widget.ComboBox();
        scrollPane8 = new widget.ScrollPane();
        TEvaluasi = new widget.TextArea();
        jLabel59 = new widget.Label();
        Btn5Soap = new widget.Button();
        jLabel7 = new widget.Label();
        TSuhu = new widget.TextBox();
        jLabel17 = new widget.Label();
        TTinggi = new widget.TextBox();
        jLabel4 = new widget.Label();
        TTensi = new widget.TextBox();
        TRespirasi = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel16 = new widget.Label();
        TBerat = new widget.TextBox();
        TNadi = new widget.TextBox();
        jLabel61 = new widget.Label();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbPemeriksaanObstetri = new widget.Table();
        PanelInput2 = new javax.swing.JPanel();
        ChkInput1 = new widget.CekBox();
        panelGlass14 = new widget.panelisi();
        jLabel27 = new widget.Label();
        TTinggi_uteri = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        TLetak = new widget.TextBox();
        jLabel32 = new widget.Label();
        TKualitas_dtk = new widget.TextBox();
        jLabel33 = new widget.Label();
        cmbPanggul = new widget.ComboBox();
        jLabel34 = new widget.Label();
        TTebal = new widget.TextBox();
        TDenyut = new widget.TextBox();
        jLabel36 = new widget.Label();
        TDenominator = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        TKualitas_mnt = new widget.TextBox();
        jLabel40 = new widget.Label();
        cmbFeto = new widget.ComboBox();
        jLabel42 = new widget.Label();
        cmbJanin = new widget.ComboBox();
        cmbKetuban = new widget.ComboBox();
        TPortio = new widget.TextBox();
        jLabel43 = new widget.Label();
        TVulva = new widget.TextBox();
        cmbKontraksi = new widget.ComboBox();
        cmbAlbus = new widget.ComboBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel44 = new widget.Label();
        cmbFluksus = new widget.ComboBox();
        jLabel48 = new widget.Label();
        cmbDalam = new widget.ComboBox();
        jLabel49 = new widget.Label();
        TPembukaan = new widget.TextBox();
        TPenurunan = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        cmbArah = new widget.ComboBox();
        jLabel52 = new widget.Label();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbPemeriksaanGinekologi = new widget.Table();
        PanelInput3 = new javax.swing.JPanel();
        ChkInput2 = new widget.CekBox();
        panelGlass15 = new widget.panelisi();
        jLabel35 = new widget.Label();
        TInspeksiVulva = new widget.TextBox();
        TAdnexaKanan = new widget.TextBox();
        jLabel57 = new widget.Label();
        cmbMobilitas = new widget.ComboBox();
        jLabel60 = new widget.Label();
        TInspekuloGine = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel67 = new widget.Label();
        TPortioInspekulo = new widget.TextBox();
        TCavumUteri = new widget.TextBox();
        cmbFluorGine = new widget.ComboBox();
        TInspeksi = new widget.TextBox();
        cmbFluxusGine = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        TVulvaInspekulo = new widget.TextBox();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        TPortioDalam = new widget.TextBox();
        TBentuk = new widget.TextBox();
        jLabel78 = new widget.Label();
        cmbNyeriTekan = new widget.ComboBox();
        TSondage = new widget.TextBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        TAdnexaKiri = new widget.TextBox();
        jLabel81 = new widget.Label();
        TCavumDouglas = new widget.TextBox();
        TUkuran = new widget.TextBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel18 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnRiwayat = new widget.Button();
        BtnResepObat = new widget.Button();
        BtnCopyResep = new widget.Button();
        BtnPermintaanStok = new widget.Button();
        BtnPermintaanResepPulang = new widget.Button();
        BtnInputObat = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnRujukKeluar = new widget.Button();
        BtnDiagnosa = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalKeperawatanUmum = new widget.Button();
        BtnAwalKeperawatanKandungan = new widget.Button();
        BtnAwalFisioterapi = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnAwalMedisHemodialisa = new widget.Button();
        BtnChecklistPreOperasi = new widget.Button();
        BtnSignInSebelumAnestesi = new widget.Button();
        BtnTimeOutSebelumInsisi = new widget.Button();
        BtnSignOutSebelumMenutupLuka = new widget.Button();
        BtnChecklistPostOperasi = new widget.Button();
        BtnPenilaianPreOperasi = new widget.Button();
        BtnPenilaianPreAnestesi = new widget.Button();
        BtnSkorAldrettePascaAnestesi = new widget.Button();
        BtnSkorStewardPascaAnestesi = new widget.Button();
        BtnPenilaianPsikolog = new widget.Button();
        BtnPerencanaanPemulangan = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhDewasa = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhAnak = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhLansia = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhNeonatus = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhGeriatri = new widget.Button();
        BtnPenilaianLanjutanResikoJatuhPsikiatri = new widget.Button();
        BtnPenilaianLanjutanSkriningFungsional = new widget.Button();
        BtnPenilaianResikoDekubitus = new widget.Button();
        BtnHasilPemeriksaanUSG = new widget.Button();
        BtnDokumentasiESWL = new widget.Button();
        BtnCatatanPersalinan = new widget.Button();
        BtnCatatan = new widget.Button();
        BtnCatatanObservasiRanap = new widget.Button();
        BtnCatatanObservasiRanapKebidanan = new widget.Button();
        BtnCatatanObservasiRanapPostPartum = new widget.Button();
        BtnFollowUpDBD = new widget.Button();
        BtnCatatanKeperawatan = new widget.Button();
        BtnCatatanCekGDS = new widget.Button();
        BtnPenilaianUlangNyeri = new widget.Button();
        BtnPemantauanPEWSAnak = new widget.Button();
        BtnPemantauanPEWSDewasa = new widget.Button();
        BtnPemantauanMEOWS = new widget.Button();
        BtnPemantauanEWSNeonatus = new widget.Button();
        BtnChecklistKriteriaMasukHCU = new widget.Button();
        BtnChecklistKriteriaKeluarHCU = new widget.Button();
        BtnChecklistKriteriaMasukICU = new widget.Button();
        BtnChecklistKriteriaKeluarICU = new widget.Button();
        BtnMonitoringReaksiTranfusi = new widget.Button();
        BtnSkriningNutrisiDewasa = new widget.Button();
        BtnSkriningNutrisiLansia = new widget.Button();
        BtnSkriningNutrisiAnak = new widget.Button();
        BtnSkriningGiziLanjut = new widget.Button();
        BtnAsuhanGizi = new widget.Button();
        BtnMonitoringAsuhanGizi = new widget.Button();
        BtnCatatanADIMEGizi = new widget.Button();
        BtnKonselingFarmasi = new widget.Button();
        BtnInformasiObat = new widget.Button();
        BtnRekonsiliasiObat = new widget.Button();
        BtnTransferAntarRuang = new widget.Button();
        BtnPengkajianRestrain = new widget.Button();
        BtnPenilaianPasienTerminal = new widget.Button();
        BtnPenilaianKorbanKekerasan = new widget.Button();
        BtnPenilaianKecemasanAnak = new widget.Button();
        BtnPenilaianPasienPenyakitMenular = new widget.Button();
        BtnPenilaianTambahanGeriatri = new widget.Button();
        BtnPenilaianTambahanBunuhDiri = new widget.Button();
        BtnPenilaianTambahanPerilakuKekerasan = new widget.Button();
        BtnPenilaianTambahanMelarikanDiri = new widget.Button();

        BagianRS.setEditable(false);
        BagianRS.setText("0");
        BagianRS.setName("BagianRS"); // NOI18N

        Bhp.setEditable(false);
        Bhp.setText("0");
        Bhp.setName("Bhp"); // NOI18N

        JmDokter.setEditable(false);
        JmDokter.setText("0");
        JmDokter.setName("JmDokter"); // NOI18N

        JmPerawat.setEditable(false);
        JmPerawat.setText("0");
        JmPerawat.setName("JmPerawat"); // NOI18N

        TTnd.setEditable(false);
        TTnd.setText("0");
        TTnd.setName("TTnd"); // NOI18N

        KSO.setEditable(false);
        KSO.setText("0");
        KSO.setName("KSO"); // NOI18N

        Menejemen.setEditable(false);
        Menejemen.setText("0");
        Menejemen.setName("Menejemen"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setBackground(new java.awt.Color(215, 225, 215));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setIconTextGap(3);
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
        BtnBatal.setIconTextGap(3);
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
        BtnHapus.setIconTextGap(3);
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
        BtnEdit.setIconTextGap(3);
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
        BtnPrint.setIconTextGap(3);
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
        BtnAll.setIconTextGap(3);
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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari2);

        jLabel20.setText("No.RM :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel20);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass10.add(TCariPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass10.add(btnPasien);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass10.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(273, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 105, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(108, 10, 130, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(849, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(240, 10, 606, 23);

        jLabel11.setText("Tindakan/Tagihan :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass7.add(jLabel11);
        jLabel11.setBounds(0, 40, 105, 23);

        TKdPrw.setName("TKdPrw"); // NOI18N
        TKdPrw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwKeyPressed(evt);
            }
        });
        panelGlass7.add(TKdPrw);
        TKdPrw.setBounds(108, 40, 100, 23);

        TNmPrw.setEditable(false);
        TNmPrw.setName("TNmPrw"); // NOI18N
        panelGlass7.add(TNmPrw);
        TNmPrw.setBounds(210, 40, 605, 23);

        btnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan.setMnemonic('3');
        btnTindakan.setToolTipText("Alt+3");
        btnTindakan.setName("btnTindakan"); // NOI18N
        btnTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanActionPerformed(evt);
            }
        });
        btnTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakanKeyPressed(evt);
            }
        });
        panelGlass7.add(btnTindakan);
        btnTindakan.setBounds(818, 40, 28, 23);

        btnTindakan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan3.setMnemonic('3');
        btnTindakan3.setToolTipText("Alt+3");
        btnTindakan3.setName("btnTindakan3"); // NOI18N
        btnTindakan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakan3ActionPerformed(evt);
            }
        });
        btnTindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakan3KeyPressed(evt);
            }
        });
        panelGlass7.add(btnTindakan3);
        btnTindakan3.setBounds(849, 40, 28, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Penanganan Dokter", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyReleased(evt);
            }
        });
        Scroll1.setViewportView(tbRawatPr);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass13.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass13.add(jLabel13);
        jLabel13.setBounds(0, 10, 105, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass13.add(kdptg);
        kdptg.setBounds(108, 10, 130, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass13.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(849, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        panelGlass13.add(TPerawat);
        TPerawat.setBounds(240, 10, 606, 23);

        jLabel28.setText("Tindakan/Tagihan :");
        jLabel28.setName("jLabel28"); // NOI18N
        panelGlass13.add(jLabel28);
        jLabel28.setBounds(0, 40, 105, 23);

        TKdPrwPetugas.setName("TKdPrwPetugas"); // NOI18N
        TKdPrwPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwPetugasKeyPressed(evt);
            }
        });
        panelGlass13.add(TKdPrwPetugas);
        TKdPrwPetugas.setBounds(108, 40, 100, 23);

        TNmPrwPetugas.setEditable(false);
        TNmPrwPetugas.setName("TNmPrwPetugas"); // NOI18N
        panelGlass13.add(TNmPrwPetugas);
        TNmPrwPetugas.setBounds(210, 40, 605, 23);

        btnTindakan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan4.setMnemonic('3');
        btnTindakan4.setToolTipText("Alt+3");
        btnTindakan4.setName("btnTindakan4"); // NOI18N
        btnTindakan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakan4ActionPerformed(evt);
            }
        });
        btnTindakan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakan4KeyPressed(evt);
            }
        });
        panelGlass13.add(btnTindakan4);
        btnTindakan4.setBounds(818, 40, 28, 23);

        btnTindakan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan5.setMnemonic('3');
        btnTindakan5.setToolTipText("Alt+3");
        btnTindakan5.setName("btnTindakan5"); // NOI18N
        btnTindakan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakan5ActionPerformed(evt);
            }
        });
        btnTindakan5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakan5KeyPressed(evt);
            }
        });
        panelGlass13.add(btnTindakan5);
        btnTindakan5.setBounds(849, 40, 28, 23);

        internalFrame3.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Penanganan Petugas", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbRawatDrPr.setAutoCreateRowSorter(true);
        tbRawatDrPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrPr.setName("tbRawatDrPr"); // NOI18N
        tbRawatDrPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrMouseClicked(evt);
            }
        });
        tbRawatDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbRawatDrPrKeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbRawatDrPr);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 105, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); // NOI18N
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(108, 40, 130, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(849, 40, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); // NOI18N
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(240, 40, 606, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 105, 23);

        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); // NOI18N
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(108, 10, 130, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        TDokter2.setName("TDokter2"); // NOI18N
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(240, 10, 606, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.setName("BtnSeekDokter2"); // NOI18N
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(849, 10, 28, 23);

        jLabel29.setText("Tindakan/Tagihan :");
        jLabel29.setName("jLabel29"); // NOI18N
        panelGlass11.add(jLabel29);
        jLabel29.setBounds(0, 70, 105, 23);

        TKdPrwDokterPetugas.setName("TKdPrwDokterPetugas"); // NOI18N
        TKdPrwDokterPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwDokterPetugasKeyPressed(evt);
            }
        });
        panelGlass11.add(TKdPrwDokterPetugas);
        TKdPrwDokterPetugas.setBounds(108, 70, 100, 23);

        TNmPrwDokterPetugas.setEditable(false);
        TNmPrwDokterPetugas.setName("TNmPrwDokterPetugas"); // NOI18N
        panelGlass11.add(TNmPrwDokterPetugas);
        TNmPrwDokterPetugas.setBounds(210, 70, 605, 23);

        btnTindakan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan6.setMnemonic('3');
        btnTindakan6.setToolTipText("Alt+3");
        btnTindakan6.setName("btnTindakan6"); // NOI18N
        btnTindakan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakan6ActionPerformed(evt);
            }
        });
        btnTindakan6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakan6KeyPressed(evt);
            }
        });
        panelGlass11.add(btnTindakan6);
        btnTindakan6.setBounds(818, 70, 28, 23);

        btnTindakan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan7.setMnemonic('3');
        btnTindakan7.setToolTipText("Alt+3");
        btnTindakan7.setName("btnTindakan7"); // NOI18N
        btnTindakan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakan7ActionPerformed(evt);
            }
        });
        btnTindakan7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakan7KeyPressed(evt);
            }
        });
        panelGlass11.add(btnTindakan7);
        btnTindakan7.setBounds(849, 70, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Penanganan Dokter & Petugas", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPemeriksaan.setAutoCreateRowSorter(true);
        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbPemeriksaan);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setOpaque(false);
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 273));
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass12.setLayout(null);

        jLabel15.setText("Alergi :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelGlass12.add(jLabel15);
        jLabel15.setBounds(450, 10, 90, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        panelGlass12.add(TAlergi);
        TAlergi.setBounds(543, 10, 360, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKeluhan);

        panelGlass12.add(scrollPane1);
        scrollPane1.setBounds(73, 70, 360, 38);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        panelGlass12.add(scrollPane2);
        scrollPane2.setBounds(73, 115, 360, 38);

        jLabel8.setText("Subjek :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass12.add(jLabel8);
        jLabel8.setBounds(0, 70, 70, 23);

        jLabel9.setText("Objek :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass12.add(jLabel9);
        jLabel9.setBounds(0, 115, 70, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TPenilaian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPenilaian.setColumns(20);
        TPenilaian.setRows(5);
        TPenilaian.setName("TPenilaian"); // NOI18N
        TPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenilaianKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TPenilaian);

        panelGlass12.add(scrollPane3);
        scrollPane3.setBounds(543, 40, 360, 38);

        jLabel37.setText("Asesmen :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass12.add(jLabel37);
        jLabel37.setBounds(450, 40, 90, 23);

        jLabel26.setText("Plan :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelGlass12.add(jLabel26);
        jLabel26.setBounds(450, 85, 90, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TindakLanjut);

        panelGlass12.add(scrollPane4);
        scrollPane4.setBounds(543, 85, 360, 47);

        jLabel53.setText("Dilakukan :");
        jLabel53.setName("jLabel53"); // NOI18N
        panelGlass12.add(jLabel53);
        jLabel53.setBounds(0, 10, 70, 23);

        KdPeg.setHighlighter(null);
        KdPeg.setName("KdPeg"); // NOI18N
        KdPeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegKeyPressed(evt);
            }
        });
        panelGlass12.add(KdPeg);
        KdPeg.setBounds(73, 10, 115, 23);

        TPegawai.setEditable(false);
        TPegawai.setHighlighter(null);
        TPegawai.setName("TPegawai"); // NOI18N
        panelGlass12.add(TPegawai);
        TPegawai.setBounds(190, 10, 212, 23);

        BtnSeekPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPegawai.setMnemonic('4');
        BtnSeekPegawai.setToolTipText("ALt+4");
        BtnSeekPegawai.setName("BtnSeekPegawai"); // NOI18N
        BtnSeekPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawaiActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnSeekPegawai);
        BtnSeekPegawai.setBounds(405, 10, 28, 23);

        Jabatan.setEditable(false);
        Jabatan.setHighlighter(null);
        Jabatan.setName("Jabatan"); // NOI18N
        panelGlass12.add(Jabatan);
        Jabatan.setBounds(193, 40, 209, 23);

        jLabel54.setText("Profesi / Jabatan / Departemen :");
        jLabel54.setName("jLabel54"); // NOI18N
        panelGlass12.add(jLabel54);
        jLabel54.setBounds(0, 40, 190, 23);

        jLabel55.setText("Instruksi :");
        jLabel55.setName("jLabel55"); // NOI18N
        panelGlass12.add(jLabel55);
        jLabel55.setBounds(450, 139, 90, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        TInstruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TInstruksi.setColumns(20);
        TInstruksi.setRows(5);
        TInstruksi.setName("TInstruksi"); // NOI18N
        TInstruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInstruksiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(TInstruksi);

        panelGlass12.add(scrollPane7);
        scrollPane7.setBounds(543, 139, 360, 50);

        jLabel56.setText("SpO2 (%) :");
        jLabel56.setName("jLabel56"); // NOI18N
        panelGlass12.add(jLabel56);
        jLabel56.setBounds(0, 220, 70, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        panelGlass12.add(SpO2);
        SpO2.setBounds(73, 220, 42, 23);

        jLabel22.setText("GCS (E,V,M) :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(120, 220, 70, 23);

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); // NOI18N
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });
        panelGlass12.add(TGCS);
        TGCS.setBounds(193, 220, 42, 23);

        jLabel58.setText("Kesadaran :");
        jLabel58.setName("jLabel58"); // NOI18N
        panelGlass12.add(jLabel58);
        jLabel58.setBounds(234, 220, 70, 23);

        cmbKesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma","Apatis","Delirium" }));
        cmbKesadaran.setName("cmbKesadaran"); // NOI18N
        cmbKesadaran.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKesadaranKeyPressed(evt);
            }
        });
        panelGlass12.add(cmbKesadaran);
        cmbKesadaran.setBounds(307, 220, 126, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        TEvaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TEvaluasi.setColumns(20);
        TEvaluasi.setRows(5);
        TEvaluasi.setName("TEvaluasi"); // NOI18N
        TEvaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TEvaluasiKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(TEvaluasi);

        panelGlass12.add(scrollPane8);
        scrollPane8.setBounds(543, 196, 360, 44);

        jLabel59.setText("Evaluasi :");
        jLabel59.setName("jLabel59"); // NOI18N
        panelGlass12.add(jLabel59);
        jLabel59.setBounds(450, 196, 90, 23);

        Btn5Soap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Btn5Soap.setMnemonic('4');
        Btn5Soap.setToolTipText("ALt+4");
        Btn5Soap.setName("Btn5Soap"); // NOI18N
        Btn5Soap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn5SoapActionPerformed(evt);
            }
        });
        panelGlass12.add(Btn5Soap);
        Btn5Soap.setBounds(405, 40, 28, 23);

        jLabel7.setText("Suhu (°C) :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass12.add(jLabel7);
        jLabel7.setBounds(0, 160, 70, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        panelGlass12.add(TSuhu);
        TSuhu.setBounds(73, 160, 55, 23);

        jLabel17.setText("TB (Cm) :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelGlass12.add(jLabel17);
        jLabel17.setBounds(0, 190, 70, 23);

        TTinggi.setFocusTraversalPolicyProvider(true);
        TTinggi.setName("TTinggi"); // NOI18N
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTinggi);
        TTinggi.setBounds(73, 190, 55, 23);

        jLabel4.setText("Tensi (mmHg) :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass12.add(jLabel4);
        jLabel4.setBounds(130, 160, 90, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTensi);
        TTensi.setBounds(223, 160, 74, 23);

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); // NOI18N
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });
        panelGlass12.add(TRespirasi);
        TRespirasi.setBounds(223, 190, 55, 23);

        jLabel41.setText("RR (/menit) :");
        jLabel41.setName("jLabel41"); // NOI18N
        panelGlass12.add(jLabel41);
        jLabel41.setBounds(130, 190, 90, 23);

        jLabel16.setText("Berat (Kg) :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(296, 160, 79, 23);

        TBerat.setHighlighter(null);
        TBerat.setName("TBerat"); // NOI18N
        TBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBeratKeyPressed(evt);
            }
        });
        panelGlass12.add(TBerat);
        TBerat.setBounds(378, 160, 55, 23);

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); // NOI18N
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        panelGlass12.add(TNadi);
        TNadi.setBounds(378, 190, 55, 23);

        jLabel61.setText("Nadi (/menit) :");
        jLabel61.setName("jLabel61"); // NOI18N
        panelGlass12.add(jLabel61);
        jLabel61.setBounds(296, 190, 79, 23);

        PanelInput1.add(panelGlass12, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPemeriksaanObstetri.setAutoCreateRowSorter(true);
        tbPemeriksaanObstetri.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanObstetri.setName("tbPemeriksaanObstetri"); // NOI18N
        tbPemeriksaanObstetri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanObstetriMouseClicked(evt);
            }
        });
        tbPemeriksaanObstetri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanObstetriKeyReleased(evt);
            }
        });
        Scroll4.setViewportView(tbPemeriksaanObstetri);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelInput2.setName("PanelInput2"); // NOI18N
        PanelInput2.setOpaque(false);
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); // NOI18N
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass14.setLayout(null);

        jLabel27.setText("Tinggi Fundus Uteri (Cm) :");
        jLabel27.setName("jLabel27"); // NOI18N
        panelGlass14.add(jLabel27);
        jLabel27.setBounds(0, 10, 135, 23);

        TTinggi_uteri.setHighlighter(null);
        TTinggi_uteri.setName("TTinggi_uteri"); // NOI18N
        TTinggi_uteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggi_uteriKeyPressed(evt);
            }
        });
        panelGlass14.add(TTinggi_uteri);
        TTinggi_uteri.setBounds(138, 10, 62, 23);

        jLabel30.setText("Janin :");
        jLabel30.setName("jLabel30"); // NOI18N
        panelGlass14.add(jLabel30);
        jLabel30.setBounds(194, 10, 45, 23);

        jLabel31.setText("Letak :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelGlass14.add(jLabel31);
        jLabel31.setBounds(375, 10, 40, 23);

        TLetak.setHighlighter(null);
        TLetak.setName("TLetak"); // NOI18N
        TLetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLetakKeyPressed(evt);
            }
        });
        panelGlass14.add(TLetak);
        TLetak.setBounds(418, 10, 50, 23);

        jLabel32.setText("Bagian Bawah Panggul :");
        jLabel32.setName("jLabel32"); // NOI18N
        panelGlass14.add(jLabel32);
        jLabel32.setBounds(476, 10, 130, 23);

        TKualitas_dtk.setFocusTraversalPolicyProvider(true);
        TKualitas_dtk.setName("TKualitas_dtk"); // NOI18N
        TKualitas_dtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_dtkKeyPressed(evt);
            }
        });
        panelGlass14.add(TKualitas_dtk);
        TKualitas_dtk.setBounds(401, 40, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("detik");
        jLabel33.setName("jLabel33"); // NOI18N
        panelGlass14.add(jLabel33);
        jLabel33.setBounds(455, 40, 30, 23);

        cmbPanggul.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "5/5", "4/5", "3/5", "2/5", "1/5" }));
        cmbPanggul.setName("cmbPanggul"); // NOI18N
        cmbPanggul.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPanggul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPanggulKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbPanggul);
        cmbPanggul.setBounds(609, 10, 62, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("/10 menit/");
        jLabel34.setName("jLabel34"); // NOI18N
        panelGlass14.add(jLabel34);
        jLabel34.setBounds(345, 40, 55, 23);

        TTebal.setHighlighter(null);
        TTebal.setName("TTebal"); // NOI18N
        TTebal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTebalKeyPressed(evt);
            }
        });
        panelGlass14.add(TTebal);
        TTebal.setBounds(709, 70, 50, 23);

        TDenyut.setHighlighter(null);
        TDenyut.setName("TDenyut"); // NOI18N
        TDenyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenyutKeyPressed(evt);
            }
        });
        panelGlass14.add(TDenyut);
        TDenyut.setBounds(846, 10, 62, 23);

        jLabel36.setText("Denyut Jantung Fetus (x/mnt) :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelGlass14.add(jLabel36);
        jLabel36.setBounds(683, 10, 160, 23);

        TDenominator.setHighlighter(null);
        TDenominator.setName("TDenominator"); // NOI18N
        TDenominator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenominatorKeyPressed(evt);
            }
        });
        panelGlass14.add(TDenominator);
        TDenominator.setBounds(548, 100, 125, 23);

        jLabel38.setText("Penurunan :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelGlass14.add(jLabel38);
        jLabel38.setBounds(267, 100, 70, 23);

        jLabel39.setText("Imbang Feto-Pelvik :");
        jLabel39.setName("jLabel39"); // NOI18N
        panelGlass14.add(jLabel39);
        jLabel39.setBounds(673, 100, 110, 23);

        TKualitas_mnt.setHighlighter(null);
        TKualitas_mnt.setName("TKualitas_mnt"); // NOI18N
        TKualitas_mnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_mntKeyPressed(evt);
            }
        });
        panelGlass14.add(TKualitas_mnt);
        TKualitas_mnt.setBounds(293, 40, 50, 23);

        jLabel40.setText("Portio Inspekulo :");
        jLabel40.setName("jLabel40"); // NOI18N
        panelGlass14.add(jLabel40);
        jLabel40.setBounds(272, 70, 90, 23);

        cmbFeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Susp.CPD-FPD", "CPD-FPD" }));
        cmbFeto.setName("cmbFeto"); // NOI18N
        cmbFeto.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFetoKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFeto);
        cmbFeto.setBounds(786, 100, 122, 23);

        jLabel42.setText("Denominator :");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel42);
        jLabel42.setBounds(470, 100, 75, 23);

        cmbJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tunggal", "Gemelli" }));
        cmbJanin.setName("cmbJanin"); // NOI18N
        cmbJanin.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJaninKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbJanin);
        cmbJanin.setBounds(242, 10, 115, 23);

        cmbKetuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "+" }));
        cmbKetuban.setName("cmbKetuban"); // NOI18N
        cmbKetuban.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKetubanKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbKetuban);
        cmbKetuban.setBounds(846, 40, 62, 23);

        TPortio.setFocusTraversalPolicyProvider(true);
        TPortio.setName("TPortio"); // NOI18N
        TPortio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortio);
        TPortio.setBounds(365, 70, 125, 23);

        jLabel43.setText("Kualitas (x/mnt) : ");
        jLabel43.setName("jLabel43"); // NOI18N
        panelGlass14.add(jLabel43);
        jLabel43.setBounds(193, 40, 100, 23);

        TVulva.setHighlighter(null);
        TVulva.setName("TVulva"); // NOI18N
        TVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaKeyPressed(evt);
            }
        });
        panelGlass14.add(TVulva);
        TVulva.setBounds(138, 70, 125, 23);

        cmbKontraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbKontraksi.setName("cmbKontraksi"); // NOI18N
        cmbKontraksi.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKontraksiKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbKontraksi);
        cmbKontraksi.setBounds(138, 40, 62, 23);

        cmbAlbus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbAlbus.setName("cmbAlbus"); // NOI18N
        cmbAlbus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbAlbus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAlbusKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbAlbus);
        cmbAlbus.setBounds(683, 40, 62, 23);

        jLabel45.setText("Kontraksi :");
        jLabel45.setName("jLabel45"); // NOI18N
        panelGlass14.add(jLabel45);
        jLabel45.setBounds(0, 40, 135, 23);

        jLabel46.setText("Fluor Albus :");
        jLabel46.setName("jLabel46"); // NOI18N
        panelGlass14.add(jLabel46);
        jLabel46.setBounds(608, 40, 72, 23);

        jLabel47.setText("Vulva/Vagina :");
        jLabel47.setName("jLabel47"); // NOI18N
        panelGlass14.add(jLabel47);
        jLabel47.setBounds(0, 70, 135, 23);

        jLabel44.setText("Fluksus :");
        jLabel44.setName("jLabel44"); // NOI18N
        panelGlass14.add(jLabel44);
        jLabel44.setBounds(483, 40, 58, 23);

        cmbFluksus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluksus.setName("cmbFluksus"); // NOI18N
        cmbFluksus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluksus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluksusKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluksus);
        cmbFluksus.setBounds(544, 40, 62, 23);

        jLabel48.setText("Dalam :");
        jLabel48.setName("jLabel48"); // NOI18N
        panelGlass14.add(jLabel48);
        jLabel48.setBounds(500, 70, 47, 23);

        cmbDalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kenyal", "Lunak" }));
        cmbDalam.setName("cmbDalam"); // NOI18N
        cmbDalam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDalamKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbDalam);
        cmbDalam.setBounds(550, 70, 90, 23);

        jLabel49.setText("Pembukaan :");
        jLabel49.setName("jLabel49"); // NOI18N
        panelGlass14.add(jLabel49);
        jLabel49.setBounds(0, 100, 135, 23);

        TPembukaan.setHighlighter(null);
        TPembukaan.setName("TPembukaan"); // NOI18N
        TPembukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPembukaanKeyPressed(evt);
            }
        });
        panelGlass14.add(TPembukaan);
        TPembukaan.setBounds(138, 100, 125, 23);

        TPenurunan.setHighlighter(null);
        TPenurunan.setName("TPenurunan"); // NOI18N
        TPenurunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenurunanKeyPressed(evt);
            }
        });
        panelGlass14.add(TPenurunan);
        TPenurunan.setBounds(340, 100, 125, 23);

        jLabel50.setText("Tebal/cm :");
        jLabel50.setName("jLabel50"); // NOI18N
        panelGlass14.add(jLabel50);
        jLabel50.setBounds(646, 70, 60, 23);

        jLabel51.setText("Selaput Ketuban :");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel51.setName("jLabel51"); // NOI18N
        panelGlass14.add(jLabel51);
        jLabel51.setBounds(753, 40, 90, 23);

        cmbArah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depan", "Axial", "Belakang" }));
        cmbArah.setName("cmbArah"); // NOI18N
        cmbArah.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbArah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbArahKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbArah);
        cmbArah.setBounds(806, 70, 102, 23);

        jLabel52.setText("Arah :");
        jLabel52.setName("jLabel52"); // NOI18N
        panelGlass14.add(jLabel52);
        jLabel52.setBounds(763, 70, 40, 23);

        PanelInput2.add(panelGlass14, java.awt.BorderLayout.CENTER);

        internalFrame6.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Obstetri", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Scroll5KeyPressed(evt);
            }
        });

        tbPemeriksaanGinekologi.setAutoCreateRowSorter(true);
        tbPemeriksaanGinekologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanGinekologi.setName("tbPemeriksaanGinekologi"); // NOI18N
        tbPemeriksaanGinekologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanGinekologiMouseClicked(evt);
            }
        });
        tbPemeriksaanGinekologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanGinekologiKeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbPemeriksaanGinekologi);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelInput3.setName("PanelInput3"); // NOI18N
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 245));
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setMnemonic('I');
        ChkInput2.setText(".: Input Data");
        ChkInput2.setToolTipText("Alt+I");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2"); // NOI18N
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        panelGlass15.setName("panelGlass15"); // NOI18N
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass15.setLayout(null);

        jLabel35.setText("Inspeksi :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelGlass15.add(jLabel35);
        jLabel35.setBounds(0, 10, 70, 23);

        TInspeksiVulva.setHighlighter(null);
        TInspeksiVulva.setName("TInspeksiVulva"); // NOI18N
        TInspeksiVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiVulvaKeyPressed(evt);
            }
        });
        panelGlass15.add(TInspeksiVulva);
        TInspeksiVulva.setBounds(118, 40, 223, 23);

        TAdnexaKanan.setHighlighter(null);
        TAdnexaKanan.setName("TAdnexaKanan"); // NOI18N
        TAdnexaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKananKeyPressed(evt);
            }
        });
        panelGlass15.add(TAdnexaKanan);
        TAdnexaKanan.setBounds(510, 120, 355, 23);

        jLabel57.setText("Fluor Albus :");
        jLabel57.setName("jLabel57"); // NOI18N
        panelGlass15.add(jLabel57);
        jLabel57.setBounds(206, 100, 70, 23);

        cmbMobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbMobilitas.setName("cmbMobilitas"); // NOI18N
        cmbMobilitas.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMobilitasKeyPressed(evt);
            }
        });
        panelGlass15.add(cmbMobilitas);
        cmbMobilitas.setBounds(803, 60, 62, 23);

        jLabel60.setText("Sondage :");
        jLabel60.setName("jLabel60"); // NOI18N
        jLabel60.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel60);
        jLabel60.setBounds(20, 190, 95, 23);

        TInspekuloGine.setHighlighter(null);
        TInspekuloGine.setName("TInspekuloGine"); // NOI18N
        TInspekuloGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspekuloGineKeyPressed(evt);
            }
        });
        panelGlass15.add(TInspekuloGine);
        TInspekuloGine.setBounds(73, 70, 268, 23);

        jLabel62.setText("Vulva/Uretra/Vagina :");
        jLabel62.setName("jLabel62"); // NOI18N
        panelGlass15.add(jLabel62);
        jLabel62.setBounds(0, 40, 115, 23);

        jLabel64.setText("Inspekulo :");
        jLabel64.setName("jLabel64"); // NOI18N
        panelGlass15.add(jLabel64);
        jLabel64.setBounds(0, 70, 70, 23);

        jLabel67.setText("Fluxus :");
        jLabel67.setName("jLabel67"); // NOI18N
        panelGlass15.add(jLabel67);
        jLabel67.setBounds(0, 100, 115, 23);

        TPortioInspekulo.setHighlighter(null);
        TPortioInspekulo.setName("TPortioInspekulo"); // NOI18N
        TPortioInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioInspekuloKeyPressed(evt);
            }
        });
        panelGlass15.add(TPortioInspekulo);
        TPortioInspekulo.setBounds(118, 160, 223, 23);

        TCavumUteri.setHighlighter(null);
        TCavumUteri.setName("TCavumUteri"); // NOI18N
        TCavumUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumUteriKeyPressed(evt);
            }
        });
        panelGlass15.add(TCavumUteri);
        TCavumUteri.setBounds(468, 60, 272, 23);

        cmbFluorGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluorGine.setName("cmbFluorGine"); // NOI18N
        cmbFluorGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluorGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluorGineKeyPressed(evt);
            }
        });
        panelGlass15.add(cmbFluorGine);
        cmbFluorGine.setBounds(279, 100, 62, 23);

        TInspeksi.setHighlighter(null);
        TInspeksi.setName("TInspeksi"); // NOI18N
        TInspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiKeyPressed(evt);
            }
        });
        panelGlass15.add(TInspeksi);
        TInspeksi.setBounds(73, 10, 268, 23);

        cmbFluxusGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluxusGine.setName("cmbFluxusGine"); // NOI18N
        cmbFluxusGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluxusGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluxusGineKeyPressed(evt);
            }
        });
        panelGlass15.add(cmbFluxusGine);
        cmbFluxusGine.setBounds(118, 100, 62, 23);

        jLabel71.setText("Adnexa/Parametrium :");
        jLabel71.setName("jLabel71"); // NOI18N
        jLabel71.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel71);
        jLabel71.setBounds(340, 120, 125, 23);

        jLabel72.setText("Portio :");
        jLabel72.setName("jLabel72"); // NOI18N
        jLabel72.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel72);
        jLabel72.setBounds(20, 160, 95, 23);

        jLabel73.setText("Vulva/Vagina :");
        jLabel73.setName("jLabel73"); // NOI18N
        jLabel73.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel73);
        jLabel73.setBounds(20, 130, 95, 23);

        jLabel74.setText("Pemeriksaan Dalam :");
        jLabel74.setName("jLabel74"); // NOI18N
        jLabel74.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel74);
        jLabel74.setBounds(340, 10, 110, 23);

        jLabel75.setText("Kanan :");
        jLabel75.setName("jLabel75"); // NOI18N
        jLabel75.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel75);
        jLabel75.setBounds(437, 120, 70, 23);

        TVulvaInspekulo.setHighlighter(null);
        TVulvaInspekulo.setName("TVulvaInspekulo"); // NOI18N
        TVulvaInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaInspekuloKeyPressed(evt);
            }
        });
        panelGlass15.add(TVulvaInspekulo);
        TVulvaInspekulo.setBounds(118, 130, 223, 23);

        jLabel76.setText(", Bentuk :");
        jLabel76.setName("jLabel76"); // NOI18N
        jLabel76.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel76);
        jLabel76.setBounds(640, 30, 50, 23);

        jLabel77.setText(", Mobilitas :");
        jLabel77.setName("jLabel77"); // NOI18N
        jLabel77.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel77);
        jLabel77.setBounds(740, 60, 60, 23);

        TPortioDalam.setHighlighter(null);
        TPortioDalam.setName("TPortioDalam"); // NOI18N
        TPortioDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioDalamKeyPressed(evt);
            }
        });
        panelGlass15.add(TPortioDalam);
        TPortioDalam.setBounds(468, 30, 173, 23);

        TBentuk.setHighlighter(null);
        TBentuk.setName("TBentuk"); // NOI18N
        TBentuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBentukKeyPressed(evt);
            }
        });
        panelGlass15.add(TBentuk);
        TBentuk.setBounds(693, 30, 172, 23);

        jLabel78.setText("Ukuran :");
        jLabel78.setName("jLabel78"); // NOI18N
        jLabel78.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel78);
        jLabel78.setBounds(437, 90, 70, 23);

        cmbNyeriTekan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbNyeriTekan.setName("cmbNyeriTekan"); // NOI18N
        cmbNyeriTekan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbNyeriTekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbNyeriTekanKeyPressed(evt);
            }
        });
        panelGlass15.add(cmbNyeriTekan);
        cmbNyeriTekan.setBounds(803, 90, 62, 23);

        TSondage.setHighlighter(null);
        TSondage.setName("TSondage"); // NOI18N
        TSondage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSondageKeyPressed(evt);
            }
        });
        panelGlass15.add(TSondage);
        TSondage.setBounds(118, 190, 223, 23);

        jLabel79.setText("Cavum Uteri :");
        jLabel79.setName("jLabel79"); // NOI18N
        jLabel79.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel79);
        jLabel79.setBounds(340, 60, 125, 23);

        jLabel80.setText("Kiri :");
        jLabel80.setName("jLabel80"); // NOI18N
        jLabel80.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel80);
        jLabel80.setBounds(437, 150, 70, 23);

        TAdnexaKiri.setHighlighter(null);
        TAdnexaKiri.setName("TAdnexaKiri"); // NOI18N
        TAdnexaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKiriKeyPressed(evt);
            }
        });
        panelGlass15.add(TAdnexaKiri);
        TAdnexaKiri.setBounds(510, 150, 355, 23);

        jLabel81.setText("Cavum Douglas :");
        jLabel81.setName("jLabel81"); // NOI18N
        jLabel81.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel81);
        jLabel81.setBounds(340, 180, 125, 23);

        TCavumDouglas.setHighlighter(null);
        TCavumDouglas.setName("TCavumDouglas"); // NOI18N
        TCavumDouglas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumDouglasKeyPressed(evt);
            }
        });
        panelGlass15.add(TCavumDouglas);
        TCavumDouglas.setBounds(468, 180, 397, 23);

        TUkuran.setHighlighter(null);
        TUkuran.setName("TUkuran"); // NOI18N
        TUkuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUkuranKeyPressed(evt);
            }
        });
        panelGlass15.add(TUkuran);
        TUkuran.setBounds(510, 90, 217, 23);

        jLabel82.setText(", Nyeri Tekan :");
        jLabel82.setName("jLabel82"); // NOI18N
        jLabel82.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel82);
        jLabel82.setBounds(724, 90, 76, 23);

        jLabel83.setText("Portio :");
        jLabel83.setName("jLabel83"); // NOI18N
        jLabel83.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass15.add(jLabel83);
        jLabel83.setBounds(340, 30, 125, 23);

        PanelInput3.add(panelGlass15, java.awt.BorderLayout.CENTER);

        internalFrame7.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Ginekologi", internalFrame7);

        TabRawat.setSelectedIndex(3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TNoRwMouseClicked(evt);
            }
        });
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(283, 10, 260, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        jLabel18.setText("Tanggal :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(554, 10, 60, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(841, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(205, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(135, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat.setText("Input Resep");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); // NOI18N
        BtnResepObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); // NOI18N
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });

        BtnPermintaanStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanStok.setText("Permintaan Stok Pasien");
        BtnPermintaanStok.setFocusPainted(false);
        BtnPermintaanStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanStok.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanStok.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanStok.setName("BtnPermintaanStok"); // NOI18N
        BtnPermintaanStok.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanStok.setRoundRect(false);
        BtnPermintaanStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanStokActionPerformed(evt);
            }
        });

        BtnPermintaanResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanResepPulang.setText("Permintaan Resep Pulang");
        BtnPermintaanResepPulang.setFocusPainted(false);
        BtnPermintaanResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanResepPulang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanResepPulang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanResepPulang.setName("BtnPermintaanResepPulang"); // NOI18N
        BtnPermintaanResepPulang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanResepPulang.setRoundRect(false);
        BtnPermintaanResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanResepPulangActionPerformed(evt);
            }
        });

        BtnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInputObat.setText("Input Obat & BHP");
        BtnInputObat.setFocusPainted(false);
        BtnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInputObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInputObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInputObat.setName("BtnInputObat"); // NOI18N
        BtnInputObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInputObat.setRoundRect(false);
        BtnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInputObatActionPerformed(evt);
            }
        });

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); // NOI18N
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); // NOI18N
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); // NOI18N
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); // NOI18N
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); // NOI18N
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); // NOI18N
        BtnSKDP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });

        BtnRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukKeluar.setText("Rujuk Keluar");
        BtnRujukKeluar.setFocusPainted(false);
        BtnRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukKeluar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukKeluar.setName("BtnRujukKeluar"); // NOI18N
        BtnRujukKeluar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukKeluar.setRoundRect(false);
        BtnRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukKeluarActionPerformed(evt);
            }
        });

        BtnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDiagnosa.setText("Diagnosa");
        BtnDiagnosa.setFocusPainted(false);
        BtnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDiagnosa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDiagnosa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDiagnosa.setName("BtnDiagnosa"); // NOI18N
        BtnDiagnosa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDiagnosa.setRoundRect(false);
        BtnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosaActionPerformed(evt);
            }
        });

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResume.setText("Resume Pasien");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); // NOI18N
        BtnResume.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanUmum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanUmum.setText("Awal Keperawatan Umum");
        BtnAwalKeperawatanUmum.setFocusPainted(false);
        BtnAwalKeperawatanUmum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanUmum.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanUmum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanUmum.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanUmum.setName("BtnAwalKeperawatanUmum"); // NOI18N
        BtnAwalKeperawatanUmum.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanUmum.setRoundRect(false);
        BtnAwalKeperawatanUmum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanUmumActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanKandungan.setText("Awal Keperawatan Kandungan");
        BtnAwalKeperawatanKandungan.setFocusPainted(false);
        BtnAwalKeperawatanKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanKandungan.setName("BtnAwalKeperawatanKandungan"); // NOI18N
        BtnAwalKeperawatanKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanKandungan.setRoundRect(false);
        BtnAwalKeperawatanKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanKandunganActionPerformed(evt);
            }
        });

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); // NOI18N
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis.setText("Awal Medis Umum");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); // NOI18N
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); // NOI18N
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });

        BtnAwalMedisHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisHemodialisa.setText("Awal Medis Hemodialisa");
        BtnAwalMedisHemodialisa.setFocusPainted(false);
        BtnAwalMedisHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisHemodialisa.setName("BtnAwalMedisHemodialisa"); // NOI18N
        BtnAwalMedisHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisHemodialisa.setRoundRect(false);
        BtnAwalMedisHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisHemodialisaActionPerformed(evt);
            }
        });

        BtnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPreOperasi.setText("Check List Pre Operasi");
        BtnChecklistPreOperasi.setFocusPainted(false);
        BtnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPreOperasi.setName("BtnChecklistPreOperasi"); // NOI18N
        BtnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPreOperasi.setRoundRect(false);
        BtnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPreOperasiActionPerformed(evt);
            }
        });

        BtnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        BtnSignInSebelumAnestesi.setFocusPainted(false);
        BtnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignInSebelumAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignInSebelumAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignInSebelumAnestesi.setName("BtnSignInSebelumAnestesi"); // NOI18N
        BtnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignInSebelumAnestesi.setRoundRect(false);
        BtnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignInSebelumAnestesiActionPerformed(evt);
            }
        });

        BtnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        BtnTimeOutSebelumInsisi.setFocusPainted(false);
        BtnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTimeOutSebelumInsisi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTimeOutSebelumInsisi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTimeOutSebelumInsisi.setName("BtnTimeOutSebelumInsisi"); // NOI18N
        BtnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTimeOutSebelumInsisi.setRoundRect(false);
        BtnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });

        BtnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        BtnSignOutSebelumMenutupLuka.setFocusPainted(false);
        BtnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSignOutSebelumMenutupLuka.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignOutSebelumMenutupLuka.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignOutSebelumMenutupLuka.setName("BtnSignOutSebelumMenutupLuka"); // NOI18N
        BtnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignOutSebelumMenutupLuka.setRoundRect(false);
        BtnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });

        BtnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistPostOperasi.setText("Check List Post Operasi");
        BtnChecklistPostOperasi.setFocusPainted(false);
        BtnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistPostOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPostOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPostOperasi.setName("BtnChecklistPostOperasi"); // NOI18N
        BtnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPostOperasi.setRoundRect(false);
        BtnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPostOperasiActionPerformed(evt);
            }
        });

        BtnPenilaianPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreOperasi.setText("Pengkajian Pre Operasi");
        BtnPenilaianPreOperasi.setFocusPainted(false);
        BtnPenilaianPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreOperasi.setName("BtnPenilaianPreOperasi"); // NOI18N
        BtnPenilaianPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreOperasi.setRoundRect(false);
        BtnPenilaianPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreOperasiActionPerformed(evt);
            }
        });

        BtnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPreAnestesi.setText("Pengkajian Pre Anestesi");
        BtnPenilaianPreAnestesi.setFocusPainted(false);
        BtnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPreAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreAnestesi.setName("BtnPenilaianPreAnestesi"); // NOI18N
        BtnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreAnestesi.setRoundRect(false);
        BtnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreAnestesiActionPerformed(evt);
            }
        });

        BtnSkorAldrettePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorAldrettePascaAnestesi.setText("Skor Aldrette Pasca Anestesi");
        BtnSkorAldrettePascaAnestesi.setFocusPainted(false);
        BtnSkorAldrettePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorAldrettePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorAldrettePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorAldrettePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorAldrettePascaAnestesi.setName("BtnSkorAldrettePascaAnestesi"); // NOI18N
        BtnSkorAldrettePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorAldrettePascaAnestesi.setRoundRect(false);
        BtnSkorAldrettePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorAldrettePascaAnestesiActionPerformed(evt);
            }
        });

        BtnSkorStewardPascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkorStewardPascaAnestesi.setText("Skor Steward Pasca Anestesi");
        BtnSkorStewardPascaAnestesi.setFocusPainted(false);
        BtnSkorStewardPascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkorStewardPascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorStewardPascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorStewardPascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorStewardPascaAnestesi.setName("BtnSkorStewardPascaAnestesi"); // NOI18N
        BtnSkorStewardPascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorStewardPascaAnestesi.setRoundRect(false);
        BtnSkorStewardPascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorStewardPascaAnestesiActionPerformed(evt);
            }
        });

        BtnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPsikolog.setText("Pengkajian Psikologi");
        BtnPenilaianPsikolog.setFocusPainted(false);
        BtnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPsikolog.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikolog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikolog.setName("BtnPenilaianPsikolog"); // NOI18N
        BtnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikolog.setRoundRect(false);
        BtnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPsikologActionPerformed(evt);
            }
        });

        BtnPerencanaanPemulangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPerencanaanPemulangan.setText("Perencanaan Pemulangan");
        BtnPerencanaanPemulangan.setFocusPainted(false);
        BtnPerencanaanPemulangan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPerencanaanPemulangan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPerencanaanPemulangan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPerencanaanPemulangan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPerencanaanPemulangan.setName("BtnPerencanaanPemulangan"); // NOI18N
        BtnPerencanaanPemulangan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPerencanaanPemulangan.setRoundRect(false);
        BtnPerencanaanPemulangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerencanaanPemulanganActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setText("Lanjutan Risiko Jatuh Dewasa");
        BtnPenilaianLanjutanResikoJatuhDewasa.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhDewasa.setName("BtnPenilaianLanjutanResikoJatuhDewasa"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhDewasa.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setText("Lanjutan Risiko Jatuh Anak");
        BtnPenilaianLanjutanResikoJatuhAnak.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhAnak.setName("BtnPenilaianLanjutanResikoJatuhAnak"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhAnak.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhAnakActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setText("Lanjutan Risiko Jatuh Lansia");
        BtnPenilaianLanjutanResikoJatuhLansia.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhLansia.setName("BtnPenilaianLanjutanResikoJatuhLansia"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhLansia.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setText("Lanjutan Risiko Jatuh Neonatus");
        BtnPenilaianLanjutanResikoJatuhNeonatus.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setName("BtnPenilaianLanjutanResikoJatuhNeonatus"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhNeonatus.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setText("Lanjutan Risiko Jatuh Geriatri");
        BtnPenilaianLanjutanResikoJatuhGeriatri.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setName("BtnPenilaianLanjutanResikoJatuhGeriatri"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhGeriatri.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanResikoJatuhPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setText("Lanjutan Risiko Jatuh Psikiatri");
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setFocusPainted(false);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setName("BtnPenilaianLanjutanResikoJatuhPsikiatri"); // NOI18N
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setRoundRect(false);
        BtnPenilaianLanjutanResikoJatuhPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanSkriningFungsional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setText("Lanjutan Skrining Fungsional");
        BtnPenilaianLanjutanSkriningFungsional.setFocusPainted(false);
        BtnPenilaianLanjutanSkriningFungsional.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanSkriningFungsional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanSkriningFungsional.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanSkriningFungsional.setName("BtnPenilaianLanjutanSkriningFungsional"); // NOI18N
        BtnPenilaianLanjutanSkriningFungsional.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanSkriningFungsional.setRoundRect(false);
        BtnPenilaianLanjutanSkriningFungsional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanSkriningFungsionalActionPerformed(evt);
            }
        });

        BtnPenilaianResikoDekubitus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianResikoDekubitus.setText("Risiko Dekubitus");
        BtnPenilaianResikoDekubitus.setFocusPainted(false);
        BtnPenilaianResikoDekubitus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianResikoDekubitus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianResikoDekubitus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianResikoDekubitus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianResikoDekubitus.setName("BtnPenilaianResikoDekubitus"); // NOI18N
        BtnPenilaianResikoDekubitus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianResikoDekubitus.setRoundRect(false);
        BtnPenilaianResikoDekubitus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianResikoDekubitusActionPerformed(evt);
            }
        });

        BtnHasilPemeriksaanUSG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnHasilPemeriksaanUSG.setText("Hasil USG Kandungan");
        BtnHasilPemeriksaanUSG.setFocusPainted(false);
        BtnHasilPemeriksaanUSG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG.setName("BtnHasilPemeriksaanUSG"); // NOI18N
        BtnHasilPemeriksaanUSG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG.setRoundRect(false);
        BtnHasilPemeriksaanUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSGActionPerformed(evt);
            }
        });

        BtnDokumentasiESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDokumentasiESWL.setText("Dokumentasi Tindakan ESWL");
        BtnDokumentasiESWL.setFocusPainted(false);
        BtnDokumentasiESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDokumentasiESWL.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiESWL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiESWL.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiESWL.setName("BtnDokumentasiESWL"); // NOI18N
        BtnDokumentasiESWL.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDokumentasiESWL.setRoundRect(false);
        BtnDokumentasiESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiESWLActionPerformed(evt);
            }
        });

        BtnCatatanPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanPersalinan.setText("Catatan Persalinan");
        BtnCatatanPersalinan.setFocusPainted(false);
        BtnCatatanPersalinan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanPersalinan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanPersalinan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanPersalinan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanPersalinan.setName("BtnCatatanPersalinan"); // NOI18N
        BtnCatatanPersalinan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanPersalinan.setRoundRect(false);
        BtnCatatanPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanPersalinanActionPerformed(evt);
            }
        });

        BtnCatatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatan.setText("Catatan Pasien");
        BtnCatatan.setFocusPainted(false);
        BtnCatatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatan.setName("BtnCatatan"); // NOI18N
        BtnCatatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatan.setRoundRect(false);
        BtnCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanActionPerformed(evt);
            }
        });

        BtnCatatanObservasiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanap.setText("Observasi Ranap");
        BtnCatatanObservasiRanap.setFocusPainted(false);
        BtnCatatanObservasiRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanap.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanap.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanap.setName("BtnCatatanObservasiRanap"); // NOI18N
        BtnCatatanObservasiRanap.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanap.setRoundRect(false);
        BtnCatatanObservasiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapActionPerformed(evt);
            }
        });

        BtnCatatanObservasiRanapKebidanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setText("Observasi Kebidanan");
        BtnCatatanObservasiRanapKebidanan.setFocusPainted(false);
        BtnCatatanObservasiRanapKebidanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanapKebidanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanapKebidanan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanapKebidanan.setName("BtnCatatanObservasiRanapKebidanan"); // NOI18N
        BtnCatatanObservasiRanapKebidanan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanapKebidanan.setRoundRect(false);
        BtnCatatanObservasiRanapKebidanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapKebidananActionPerformed(evt);
            }
        });

        BtnCatatanObservasiRanapPostPartum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setText("Observasi Post Partum");
        BtnCatatanObservasiRanapPostPartum.setFocusPainted(false);
        BtnCatatanObservasiRanapPostPartum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRanapPostPartum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRanapPostPartum.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRanapPostPartum.setName("BtnCatatanObservasiRanapPostPartum"); // NOI18N
        BtnCatatanObservasiRanapPostPartum.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRanapPostPartum.setRoundRect(false);
        BtnCatatanObservasiRanapPostPartum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiRanapPostPartumActionPerformed(evt);
            }
        });

        BtnFollowUpDBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnFollowUpDBD.setText("Follow Up DBD");
        BtnFollowUpDBD.setFocusPainted(false);
        BtnFollowUpDBD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnFollowUpDBD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnFollowUpDBD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnFollowUpDBD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnFollowUpDBD.setName("BtnFollowUpDBD"); // NOI18N
        BtnFollowUpDBD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnFollowUpDBD.setRoundRect(false);
        BtnFollowUpDBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFollowUpDBDActionPerformed(evt);
            }
        });

        BtnCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanKeperawatan.setText("Catatan Keperawatan");
        BtnCatatanKeperawatan.setFocusPainted(false);
        BtnCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanKeperawatan.setName("BtnCatatanKeperawatan"); // NOI18N
        BtnCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanKeperawatan.setRoundRect(false);
        BtnCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanKeperawatanActionPerformed(evt);
            }
        });

        BtnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanCekGDS.setText("Catatan Cek GDS");
        BtnCatatanCekGDS.setFocusPainted(false);
        BtnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanCekGDS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCekGDS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCekGDS.setName("BtnCatatanCekGDS"); // NOI18N
        BtnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCekGDS.setRoundRect(false);
        BtnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanCekGDSActionPerformed(evt);
            }
        });

        BtnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianUlangNyeri.setText("Pengkajian Ulang Nyeri");
        BtnPenilaianUlangNyeri.setFocusPainted(false);
        BtnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianUlangNyeri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianUlangNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianUlangNyeri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianUlangNyeri.setName("BtnPenilaianUlangNyeri"); // NOI18N
        BtnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianUlangNyeri.setRoundRect(false);
        BtnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianUlangNyeriActionPerformed(evt);
            }
        });

        BtnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSAnak.setText("Pemantauan PEWS Anak");
        BtnPemantauanPEWSAnak.setFocusPainted(false);
        BtnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSAnak.setName("BtnPemantauanPEWSAnak"); // NOI18N
        BtnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSAnak.setRoundRect(false);
        BtnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSAnakActionPerformed(evt);
            }
        });

        BtnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanPEWSDewasa.setText("Pemantauan EWS Dewasa");
        BtnPemantauanPEWSDewasa.setFocusPainted(false);
        BtnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanPEWSDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSDewasa.setName("BtnPemantauanPEWSDewasa"); // NOI18N
        BtnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSDewasa.setRoundRect(false);
        BtnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });

        BtnPemantauanMEOWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanMEOWS.setText("Pemantauan MEOWS Obstetri");
        BtnPemantauanMEOWS.setFocusPainted(false);
        BtnPemantauanMEOWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanMEOWS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanMEOWS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanMEOWS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanMEOWS.setName("BtnPemantauanMEOWS"); // NOI18N
        BtnPemantauanMEOWS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanMEOWS.setRoundRect(false);
        BtnPemantauanMEOWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanMEOWSActionPerformed(evt);
            }
        });

        BtnPemantauanEWSNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPemantauanEWSNeonatus.setText("Pemantauan EWS Neonatus");
        BtnPemantauanEWSNeonatus.setFocusPainted(false);
        BtnPemantauanEWSNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPemantauanEWSNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanEWSNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanEWSNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanEWSNeonatus.setName("BtnPemantauanEWSNeonatus"); // NOI18N
        BtnPemantauanEWSNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanEWSNeonatus.setRoundRect(false);
        BtnPemantauanEWSNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanEWSNeonatusActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukHCU.setText("Check List Masuk HCU");
        BtnChecklistKriteriaMasukHCU.setFocusPainted(false);
        BtnChecklistKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukHCU.setName("BtnChecklistKriteriaMasukHCU"); // NOI18N
        BtnChecklistKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukHCU.setRoundRect(false);
        BtnChecklistKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukHCUActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaKeluarHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setText("Check List Keluar HCU");
        BtnChecklistKriteriaKeluarHCU.setFocusPainted(false);
        BtnChecklistKriteriaKeluarHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaKeluarHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaKeluarHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaKeluarHCU.setName("BtnChecklistKriteriaKeluarHCU"); // NOI18N
        BtnChecklistKriteriaKeluarHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaKeluarHCU.setRoundRect(false);
        BtnChecklistKriteriaKeluarHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaKeluarHCUActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaMasukICU.setText("Check List Masuk ICU");
        BtnChecklistKriteriaMasukICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaMasukICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukICU.setName("BtnChecklistKriteriaMasukICU"); // NOI18N
        BtnChecklistKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukICU.setRoundRect(false);
        BtnChecklistKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukICUActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaKeluarICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnChecklistKriteriaKeluarICU.setText("Check List Keluar ICU");
        BtnChecklistKriteriaKeluarICU.setFocusPainted(false);
        BtnChecklistKriteriaKeluarICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnChecklistKriteriaKeluarICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaKeluarICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaKeluarICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaKeluarICU.setName("BtnChecklistKriteriaKeluarICU"); // NOI18N
        BtnChecklistKriteriaKeluarICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaKeluarICU.setRoundRect(false);
        BtnChecklistKriteriaKeluarICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaKeluarICUActionPerformed(evt);
            }
        });

        BtnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        BtnMonitoringReaksiTranfusi.setFocusPainted(false);
        BtnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringReaksiTranfusi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringReaksiTranfusi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringReaksiTranfusi.setName("BtnMonitoringReaksiTranfusi"); // NOI18N
        BtnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringReaksiTranfusi.setRoundRect(false);
        BtnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiDewasa.setText("Skrining Nutrisi Dewasa");
        BtnSkriningNutrisiDewasa.setFocusPainted(false);
        BtnSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiDewasa.setName("BtnSkriningNutrisiDewasa"); // NOI18N
        BtnSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiDewasa.setRoundRect(false);
        BtnSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiDewasaActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiLansia.setText("Skrining Nutrisi Lansia");
        BtnSkriningNutrisiLansia.setFocusPainted(false);
        BtnSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiLansia.setName("BtnSkriningNutrisiLansia"); // NOI18N
        BtnSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiLansia.setRoundRect(false);
        BtnSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiLansiaActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningNutrisiAnak.setText("Skrining Nutrisi Anak");
        BtnSkriningNutrisiAnak.setFocusPainted(false);
        BtnSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningNutrisiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiAnak.setName("BtnSkriningNutrisiAnak"); // NOI18N
        BtnSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiAnak.setRoundRect(false);
        BtnSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiAnakActionPerformed(evt);
            }
        });

        BtnSkriningGiziLanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        BtnSkriningGiziLanjut.setFocusPainted(false);
        BtnSkriningGiziLanjut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSkriningGiziLanjut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziLanjut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziLanjut.setName("BtnSkriningGiziLanjut"); // NOI18N
        BtnSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziLanjut.setRoundRect(false);
        BtnSkriningGiziLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningGiziLanjutActionPerformed(evt);
            }
        });

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); // NOI18N
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); // NOI18N
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });

        BtnCatatanADIMEGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatanADIMEGizi.setText("Catatan ADIME Gizi");
        BtnCatatanADIMEGizi.setFocusPainted(false);
        BtnCatatanADIMEGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatanADIMEGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanADIMEGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanADIMEGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanADIMEGizi.setName("BtnCatatanADIMEGizi"); // NOI18N
        BtnCatatanADIMEGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanADIMEGizi.setRoundRect(false);
        BtnCatatanADIMEGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanADIMEGiziActionPerformed(evt);
            }
        });

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); // NOI18N
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); // NOI18N
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });

        BtnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        BtnRekonsiliasiObat.setFocusPainted(false);
        BtnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRekonsiliasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRekonsiliasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRekonsiliasiObat.setName("BtnRekonsiliasiObat"); // NOI18N
        BtnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRekonsiliasiObat.setRoundRect(false);
        BtnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekonsiliasiObatActionPerformed(evt);
            }
        });

        BtnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnTransferAntarRuang.setText("Transfer Antar Ruang");
        BtnTransferAntarRuang.setFocusPainted(false);
        BtnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnTransferAntarRuang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTransferAntarRuang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTransferAntarRuang.setName("BtnTransferAntarRuang"); // NOI18N
        BtnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTransferAntarRuang.setRoundRect(false);
        BtnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTransferAntarRuangActionPerformed(evt);
            }
        });

        BtnPengkajianRestrain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPengkajianRestrain.setText("Pengkajian Restrain");
        BtnPengkajianRestrain.setFocusPainted(false);
        BtnPengkajianRestrain.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPengkajianRestrain.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPengkajianRestrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPengkajianRestrain.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPengkajianRestrain.setName("BtnPengkajianRestrain"); // NOI18N
        BtnPengkajianRestrain.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPengkajianRestrain.setRoundRect(false);
        BtnPengkajianRestrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPengkajianRestrainActionPerformed(evt);
            }
        });

        BtnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienTerminal.setText("Pengkajian Pasien Terminal");
        BtnPenilaianPasienTerminal.setFocusPainted(false);
        BtnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienTerminal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienTerminal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienTerminal.setName("BtnPenilaianPasienTerminal"); // NOI18N
        BtnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienTerminal.setRoundRect(false);
        BtnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienTerminalActionPerformed(evt);
            }
        });

        BtnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKorbanKekerasan.setText("Pengkajian Korban Kekerasan");
        BtnPenilaianKorbanKekerasan.setFocusPainted(false);
        BtnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKorbanKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKorbanKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKorbanKekerasan.setName("BtnPenilaianKorbanKekerasan"); // NOI18N
        BtnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKorbanKekerasan.setRoundRect(false);
        BtnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });

        BtnPenilaianKecemasanAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianKecemasanAnak.setText("Pengkajian Kecemasan Anak");
        BtnPenilaianKecemasanAnak.setFocusPainted(false);
        BtnPenilaianKecemasanAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianKecemasanAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKecemasanAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKecemasanAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKecemasanAnak.setName("BtnPenilaianKecemasanAnak"); // NOI18N
        BtnPenilaianKecemasanAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKecemasanAnak.setRoundRect(false);
        BtnPenilaianKecemasanAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKecemasanAnakActionPerformed(evt);
            }
        });

        BtnPenilaianPasienPenyakitMenular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setText("Pasien Penyakit Menular");
        BtnPenilaianPasienPenyakitMenular.setFocusPainted(false);
        BtnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienPenyakitMenular.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienPenyakitMenular.setName("BtnPenilaianPasienPenyakitMenular"); // NOI18N
        BtnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienPenyakitMenular.setRoundRect(false);
        BtnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanGeriatri.setText("Tambahan Pasien Geriatri");
        BtnPenilaianTambahanGeriatri.setFocusPainted(false);
        BtnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanGeriatri.setName("BtnPenilaianTambahanGeriatri"); // NOI18N
        BtnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanGeriatri.setRoundRect(false);
        BtnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setText("Tambahan Bunuh Diri");
        BtnPenilaianTambahanBunuhDiri.setFocusPainted(false);
        BtnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanBunuhDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanBunuhDiri.setName("BtnPenilaianTambahanBunuhDiri"); // NOI18N
        BtnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanBunuhDiri.setRoundRect(false);
        BtnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setText("Tambahan Perilaku Kekerasan");
        BtnPenilaianTambahanPerilakuKekerasan.setFocusPainted(false);
        BtnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanPerilakuKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanPerilakuKekerasan.setName("BtnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        BtnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanPerilakuKekerasan.setRoundRect(false);
        BtnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanMelarikanDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setText("Tambahan Melarikan Diri");
        BtnPenilaianTambahanMelarikanDiri.setFocusPainted(false);
        BtnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanMelarikanDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanMelarikanDiri.setName("BtnPenilaianTambahanMelarikanDiri"); // NOI18N
        BtnPenilaianTambahanMelarikanDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanMelarikanDiri.setRoundRect(false);
        BtnPenilaianTambahanMelarikanDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanMelarikanDiriActionPerformed(evt);
            }
        });

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            jenisbayar=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
        }else{         
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,DTPTgl,KdDok);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,DTPTgl,kdptg);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,DTPTgl,KdDok2);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,DTPTgl,KdPeg);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,DTPTgl,TTinggi_uteri);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,DTPTgl,TInspeksi);
            }
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{    
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }  
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,TKdPrw,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,TKdPrwPetugas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,TKdPrwDokterPetugas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,TEvaluasi,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,cmbFeto,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,TCavumDouglas,BtnBatal);
            }
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        ChkInput1.setSelected(true);
        ChkInput2.setSelected(true);
        isForm(); 
        isForm2();
        isForm3();
        TSuhu.setText("");
        TKdPrw.setText("");
        TNmPrw.setText("");
        TKdPrwPetugas.setText("");
        TNmPrwPetugas.setText("");        
        TKdPrwDokterPetugas.setText("");
        TNmPrwDokterPetugas.setText("");
        TTensi.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TindakLanjut.setText("");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
        SpO2.setText("");
        TEvaluasi.setText("");
        TRespirasi.setText("");
        TGCS.setText("");
        TAlergi.setText("");
        TTnd.setText("0");
        BagianRS.setText("0");
        Bhp.setText("0");
        JmDokter.setText("0");
        JmPerawat.setText("0");
        TInstruksi.setText("");
        DTPTgl.setDate(new Date());
        TTinggi_uteri.setText("");
        TLetak.setText("");
        TDenyut.setText("");
        TVulva.setText("");
        TPortio.setText("");
        TTebal.setText("");
        TPembukaan.setText("");
        TPenurunan.setText("");
        TDenominator.setText("");
        TKualitas_mnt.setText("");
        TKualitas_dtk.setText("");
        TInspeksi.setText("");
        TInspeksiVulva.setText("");
        TInspekuloGine.setText("");
        TVulvaInspekulo.setText("");
        TPortioInspekulo.setText("");
        TSondage.setText("");
        TPortioDalam.setText("");
        TBentuk.setText("");
        TCavumUteri.setText("");
        TUkuran.setText("");
        TAdnexaKanan.setText("");
        TAdnexaKiri.setText("");
        TCavumDouglas.setText("");
        cmbKesadaran.setSelectedIndex(0);
        TNoRw.requestFocus();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(tabModeDr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    tbRawatDr.setValueAt(false,i,0);
                                    TCari.requestFocus();
                                }else{
                                    if(Sequel.queryutf("delete from rawat_inap_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
                                            "' and kd_jenis_prw='"+tbRawatDr.getValueAt(i,10)+
                                            "' and kd_dokter='"+tbRawatDr.getValueAt(i,5).toString()+
                                            "' and tgl_perawatan='"+tbRawatDr.getValueAt(i,7).toString()+
                                            "' and jam_rawat='"+tbRawatDr.getValueAt(i,8).toString()+"'")==true){
                                        ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDr.getValueAt(i,11).toString());
                                        ttlkso=ttlkso+Double.parseDouble(tbRawatDr.getValueAt(i,12).toString());
                                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                                        ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDr.getValueAt(i,13).toString());
                                        ttlbhp=ttlbhp+Double.parseDouble(tbRawatDr.getValueAt(i,14).toString());
                                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDr.getValueAt(i,15).toString());
                                    }else{
                                        sukses=false;
                                    }
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatDr.getValueAt(i,7).toString()+" "+tbRawatDr.getValueAt(i,8).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(i,1).toString())>0){
                                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                        tbRawatDr.setValueAt(false,i,0);
                                        TCari.requestFocus();
                                    }else{
                                        if(Sequel.queryutf("delete from rawat_inap_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
                                                "' and kd_jenis_prw='"+tbRawatDr.getValueAt(i,10)+
                                                "' and kd_dokter='"+tbRawatDr.getValueAt(i,5).toString()+
                                                "' and tgl_perawatan='"+tbRawatDr.getValueAt(i,7).toString()+
                                                "' and jam_rawat='"+tbRawatDr.getValueAt(i,8).toString()+"'")==true){
                                            ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDr.getValueAt(i,11).toString());
                                            ttlkso=ttlkso+Double.parseDouble(tbRawatDr.getValueAt(i,12).toString());
                                            ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                                            ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDr.getValueAt(i,13).toString());
                                            ttlbhp=ttlbhp+Double.parseDouble(tbRawatDr.getValueAt(i,14).toString());
                                            ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDr.getValueAt(i,15).toString());
                                        }else{
                                            sukses=false;
                                        }
                                    }
                                }else{
                                    tbRawatDr.setValueAt(false,i,0);
                                }
                            }
                        }
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmdokter>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                        }
                    }
                      
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatDr.getRowCount();i++){
                            if(tbRawatDr.getValueAt(i,0).toString().equals("true")){ 
                                tabModeDr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModeDr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
            case 1:
                if(tabModePr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    tbRawatPr.setValueAt(false,i,0);
                                    TCari.requestFocus();
                                }else{
                                    if(Sequel.queryutf("delete from rawat_inap_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
                                            "' and kd_jenis_prw='"+tbRawatPr.getValueAt(i,10)+
                                            "' and nip='"+tbRawatPr.getValueAt(i,5).toString()+
                                            "' and tgl_perawatan='"+tbRawatPr.getValueAt(i,7).toString()+
                                            "' and jam_rawat='"+tbRawatPr.getValueAt(i,8).toString()+"' ")==true){
                                        ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatPr.getValueAt(i,11).toString());
                                        ttlkso=ttlkso+Double.parseDouble(tbRawatPr.getValueAt(i,12).toString());
                                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatPr.getValueAt(i,9).toString());
                                        ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatPr.getValueAt(i,13).toString());
                                        ttlbhp=ttlbhp+Double.parseDouble(tbRawatPr.getValueAt(i,14).toString());
                                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatPr.getValueAt(i,15).toString());
                                    }else{
                                        sukses=false;
                                    }
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatPr.getValueAt(i,7).toString()+" "+tbRawatPr.getValueAt(i,8).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(i,1).toString())>0){
                                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                        tbRawatPr.setValueAt(false,i,0);
                                        TCari.requestFocus();
                                    }else{
                                        if(Sequel.queryutf("delete from rawat_inap_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
                                                "' and kd_jenis_prw='"+tbRawatPr.getValueAt(i,10)+
                                                "' and nip='"+tbRawatPr.getValueAt(i,5).toString()+
                                                "' and tgl_perawatan='"+tbRawatPr.getValueAt(i,7).toString()+
                                                "' and jam_rawat='"+tbRawatPr.getValueAt(i,8).toString()+"' ")==true){
                                            ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatPr.getValueAt(i,11).toString());
                                            ttlkso=ttlkso+Double.parseDouble(tbRawatPr.getValueAt(i,12).toString());
                                            ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatPr.getValueAt(i,9).toString());
                                            ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatPr.getValueAt(i,13).toString());
                                            ttlbhp=ttlbhp+Double.parseDouble(tbRawatPr.getValueAt(i,14).toString());
                                            ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatPr.getValueAt(i,15).toString());
                                        }else{
                                            sukses=false;
                                        }
                                    }
                                }else{
                                    tbRawatPr.setValueAt(false,i,0);
                                }
                            }
                        }                            
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmperawat>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }     
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                        }
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatPr.getRowCount();i++){
                            if(tbRawatPr.getValueAt(i,0).toString().equals("true")){ 
                                tabModePr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModePr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
            case 2:
                if(tabModeDrPr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    Sequel.AutoComitFalse();
                    sukses=true;
                    ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    tbRawatDrPr.setValueAt(false,i,0);
                                    TCari.requestFocus();
                                }else{
                                    if(Sequel.queryutf("delete from rawat_inap_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
                                            "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(i,12)+
                                            "' and kd_dokter='"+tbRawatDrPr.getValueAt(i,5).toString()+
                                            "' and nip='"+tbRawatDrPr.getValueAt(i,7).toString()+
                                            "' and tgl_perawatan='"+tbRawatDrPr.getValueAt(i,9).toString()+
                                            "' and jam_rawat='"+tbRawatDrPr.getValueAt(i,10).toString()+"' ")==true){
                                        ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDrPr.getValueAt(i,13).toString());
                                        ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatDrPr.getValueAt(i,14).toString());
                                        ttlkso=ttlkso+Double.parseDouble(tbRawatDrPr.getValueAt(i,15).toString());
                                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDrPr.getValueAt(i,11).toString());
                                        ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDrPr.getValueAt(i,16).toString());
                                        ttlbhp=ttlbhp+Double.parseDouble(tbRawatDrPr.getValueAt(i,17).toString());
                                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDrPr.getValueAt(i,18).toString());
                                    }else{
                                        sukses=false;
                                    }
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatDrPr.getValueAt(i,9).toString()+" "+tbRawatDrPr.getValueAt(i,10).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i,1).toString())>0){
                                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                        tbRawatDrPr.setValueAt(false,i,0);
                                        TCari.requestFocus();
                                    }else{
                                        if(Sequel.queryutf("delete from rawat_inap_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
                                                "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(i,12)+
                                                "' and kd_dokter='"+tbRawatDrPr.getValueAt(i,5).toString()+
                                                "' and nip='"+tbRawatDrPr.getValueAt(i,7).toString()+
                                                "' and tgl_perawatan='"+tbRawatDrPr.getValueAt(i,9).toString()+
                                                "' and jam_rawat='"+tbRawatDrPr.getValueAt(i,10).toString()+"' ")==true){
                                            ttljmdokter=ttljmdokter+Double.parseDouble(tbRawatDrPr.getValueAt(i,13).toString());
                                            ttljmperawat=ttljmperawat+Double.parseDouble(tbRawatDrPr.getValueAt(i,14).toString());
                                            ttlkso=ttlkso+Double.parseDouble(tbRawatDrPr.getValueAt(i,15).toString());
                                            ttlpendapatan=ttlpendapatan+Double.parseDouble(tbRawatDrPr.getValueAt(i,11).toString());
                                            ttljasasarana=ttljasasarana+Double.parseDouble(tbRawatDrPr.getValueAt(i,16).toString());
                                            ttlbhp=ttlbhp+Double.parseDouble(tbRawatDrPr.getValueAt(i,17).toString());
                                            ttlmenejemen=ttlmenejemen+Double.parseDouble(tbRawatDrPr.getValueAt(i,18).toString());
                                        }else{
                                            sukses=false;
                                        }
                                    }
                                }else{
                                    tbRawatDrPr.setValueAt(false,i,0);
                                }
                            }
                        }                            
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmdokter>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttljmperawat>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }     
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                        }
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        for(i=0;i<tbRawatDrPr.getRowCount();i++){
                            if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){ 
                                tabModeDrPr.removeRow(i);
                                i--;
                            }
                        }
                        LCount.setText(""+tabModeDrPr.getRowCount());
                    }else{
                        sukses=false;
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue();
                }   break;
            case 3:
                if(tabModePemeriksaan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaan.getRowCount();i++){
                        if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
                                tabModePemeriksaan.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaan.getValueAt(i,4).toString()+" "+tbPemeriksaan.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(akses.getkode().equals(tbPemeriksaan.getValueAt(i,22).toString())){
                                        Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
                                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
                                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
                                        tabModePemeriksaan.removeRow(i);
                                        i--;
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter/petugas yang bersangkutan..!!");
                                    }
                                }
                            }
                        }
                    }
                }   break;
            case 4:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaanObstetri.getRowCount();i++){
                        if(tbPemeriksaanObstetri.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_obstetri_ranap where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                                tabModeObstetri.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanObstetri.getValueAt(i,4).toString()+" "+tbPemeriksaanObstetri.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    Sequel.queryu("delete from pemeriksaan_obstetri_ranap where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                            "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                            "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                                    tabModeObstetri.removeRow(i);
                                    i--;
                                }
                            }
                        }
                    }
                }   break;
            case 5:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!");
                    TNoRw.requestFocus();
                    
                }else {
                    for(i=0;i<tbPemeriksaanGinekologi.getRowCount();i++){
                        if(tbPemeriksaanGinekologi.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_ginekologi_ranap where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                                tabModeGinekologi.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanGinekologi.getValueAt(i,4).toString()+" "+tbPemeriksaanGinekologi.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    Sequel.queryu("delete from pemeriksaan_ginekologi_ranap where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                                    tabModeGinekologi.removeRow(i);
                                    i--;
                                }
                            }
                        }
                    }
                }   break;
            default:
                break;
        }

        BtnBatalActionPerformed(evt);
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(tabModeDr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeDr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapDr.jasper","report","::[ Data Rawat Inap Yang Ditangani Dokter ]::",
                            "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan_inap.nm_perawatan,rawat_inap_dr.kd_dokter,dokter.nm_dokter,"+
                                    "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                                    "dokter inner join rawat_inap_dr "+
                                    "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                                    "where "+tgl+" and rawat_inap_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_inap_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' "+
                                            " order by rawat_inap_dr.no_rawat desc",param);
                    
                }   break;
            case 1:
                if(tabModePr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModePr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapPr.jasper","report","::[ Data Rawat Inap Yang Ditangani Perawat ]::",
                            "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan_inap.nm_perawatan,rawat_inap_pr.nip,petugas.nama,"+
                                    "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                                    "petugas inner join rawat_inap_pr "+
                                    "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "and rawat_inap_pr.nip=petugas.nip where  "+
                                    tgl+"and rawat_inap_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_inap_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_inap_pr.tgl_perawatan like '%"+TCari.getText().trim()+"%'  "+
                                            "order by rawat_inap_pr.no_rawat desc",param);
                }   break;
            case 2:
                if(tabModeDrPr.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeDrPr.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapDrPr.jasper","report","::[ Data Rawat Inap Yang Ditangani Dokter ]::",
                            "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,"+
                                    "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                                    "dokter inner join rawat_inap_drpr inner join "+
                                    "petugas on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                                    "and rawat_inap_drpr.nip=petugas.nip "+
                                    "where "+tgl+" and rawat_inap_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_inap_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_inap_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' "+
                                            " order by rawat_inap_drpr.no_rawat desc",param);
                }   break;
            case 3:
                if(tabModePemeriksaan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModePemeriksaan.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapPemeriksaan.jasper","report","::[ Data Pemeriksaan Rawat Inap ]::",
                            "select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                            "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                            "pemeriksaan_ranap.berat,pemeriksaan_ranap.spo2,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                            "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                            "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.evaluasi,pemeriksaan_ranap.nip,pegawai.nama "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join pemeriksaan_ranap on pemeriksaan_ranap.no_rawat=reg_periksa.no_rawat "+
                            "inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where "+
                            tgl+"and (pemeriksaan_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or pemeriksaan_ranap.alergi like '%"+TCari.getText().trim()+"%' or pemeriksaan_ranap.keluhan like '%"+TCari.getText().trim()+"%' or "+
                            "pemeriksaan_ranap.penilaian like '%"+TCari.getText().trim()+"%' or pemeriksaan_ranap.rtl like '%"+TCari.getText().trim()+"%' or pemeriksaan_ranap.pemeriksaan like '%"+TCari.getText().trim()+"%' or "+
                            "pegawai.nama like '%"+TCari.getText().trim()+"%') order by pemeriksaan_ranap.no_rawat desc",param);
                }   break;
            case 4:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeObstetri.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_obstetri_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapObstetri.jasper","report","::[ Data Pemeriksaan Obstetri Rawat Jalan ]::",
                            "select pemeriksaan_obstetri_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                            "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                            "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                            "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                            "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ranap "+
                            "on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_obstetri_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_obstetri_ranap.tinggi_uteri like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ranap.janin like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ranap.letak like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_obstetri_ranap.no_rawat desc",param);
                }   break;
            case 5:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(tabModeGinekologi.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_ginekologi_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapGinekologi.jasper","report","::[ Data Pemeriksaan Ginekologi Rawat Inap ]::",
                            "select pemeriksaan_ginekologi_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, " +
                            "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, " +
                            "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, " +
                            "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri," +
                            "pemeriksaan_ginekologi_ranap.cavum_douglas " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ranap "+
                            "on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_ginekologi_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_ginekologi_ranap.inspeksi like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ranap.inspeksi_vulva like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ranap.inspekulo_gine like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_ginekologi_ranap.no_rawat desc",param);
                }   break;
            default:
                break;
        }

        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCariPasien.setText("");
        BtnCariActionPerformed(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilDr();
                break;
            case 1:
                tampilPr();
                break;
            case 2:
                tampilDrPr();
                break;
            case 3:
                tampilPemeriksaan();
                break;
            case 4:
                tampilPemeriksaanObstetri();
                break;
            case 5:
                tampilPemeriksaanGinekologi();
                break;
            default:
                break;
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        isJns();
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilDr();
                break;
            case 1:
                tampilPr();
                break;
            case 2:
                tampilDrPr();
                break;
            case 3:
                tampilPemeriksaan();
                break;
            case 4:
                tampilPemeriksaanObstetri();
                break;
            case 5:
                tampilPemeriksaanGinekologi();
                break;
            default:
                break;
        }
}//GEN-LAST:event_TabRawatMouseClicked

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TKdPrw,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,TKeluhan);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgRawatInap");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
}//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
}//GEN-LAST:event_btnPasienKeyPressed

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(tabModeDr.getRowCount()!=0){
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }           
            
        }
}//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
        if(tabModePr.getRowCount()!=0){
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbRawatPrMouseClicked

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if(KdDok.getText().trim().equals("")||TDokter.getText().trim().equals("")){
                        Valid.textKosong(KdDok,"Dokter");
                    }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                        Valid.textKosong(TKdPrw,"perawatan");                            
                    }else{
                        if(tbRawatDr.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                gantitindakandokter();
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7)+" "+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        gantitindakandokter();
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                        
                    }   
                    break;
                case 1:
                    if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                        Valid.textKosong(kdptg,"Petugas");
                    }else if(TKdPrwPetugas.getText().trim().equals("")||TNmPrwPetugas.getText().trim().equals("")){
                        Valid.textKosong(TKdPrwPetugas,"perawatan");
                    }else{
                        if(tbRawatPr.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                gantitindakanpetugas();
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7)+" "+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        gantitindakanpetugas();
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   
                    break;
                case 2:
                    if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                        Valid.textKosong(KdDok2,"Dokter");
                    }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                        Valid.textKosong(kdptg2,"Petugas");
                    }else if(TKdPrwDokterPetugas.getText().trim().equals("")||TNmPrwDokterPetugas.getText().trim().equals("")){
                        Valid.textKosong(TKdPrwDokterPetugas,"perawatan");                            
                    }else{
                        if(tbRawatDrPr.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                gantitindakandokterpetugas();
                            }else{
                                if(Sequel.cekTanggal48jam(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9)+" "+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        gantitindakandokterpetugas();
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 3:
                    if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||(!TSuhu.getText().trim().equals(""))||
                            (!TTensi.getText().trim().equals(""))||(!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                            (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||(!TNadi.getText().trim().equals(""))||
                            (!TGCS.getText().trim().equals(""))||(!TindakLanjut.getText().trim().equals(""))||(!TPenilaian.getText().trim().equals(""))||
                            (!KdPeg.getText().trim().equals(""))||(!TPegawai.getText().trim().equals(""))||(!TInstruksi.getText().trim().equals(""))||
                            (!SpO2.getText().trim().equals(""))||(!TEvaluasi.getText().trim().equals(""))){
                        if(tbPemeriksaan.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_ranap","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                    "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                    "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                    "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                    "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',spo2='"+SpO2.getText()+"',"+
                                    "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                                    "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',alergi='"+TAlergi.getText()+"',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                    "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                    "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',instruksi='"+TInstruksi.getText()+"',evaluasi='"+TEvaluasi.getText()+"',nip='"+KdPeg.getText()+"'")==true){
                                    tbPemeriksaan.setValueAt(TNoRw.getText(),tbPemeriksaan.getSelectedRow(), 1);
                                    tbPemeriksaan.setValueAt(TNoRM.getText(),tbPemeriksaan.getSelectedRow(), 2);
                                    tbPemeriksaan.setValueAt(TPasien.getText(),tbPemeriksaan.getSelectedRow(), 3);
                                    tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaan.getSelectedRow(), 4);
                                    tbPemeriksaan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaan.getSelectedRow(), 5);
                                    tbPemeriksaan.setValueAt(TSuhu.getText(),tbPemeriksaan.getSelectedRow(), 6);
                                    tbPemeriksaan.setValueAt(TTensi.getText(),tbPemeriksaan.getSelectedRow(), 7);
                                    tbPemeriksaan.setValueAt(TNadi.getText(),tbPemeriksaan.getSelectedRow(), 8);
                                    tbPemeriksaan.setValueAt(TRespirasi.getText(),tbPemeriksaan.getSelectedRow(), 9);
                                    tbPemeriksaan.setValueAt(TTinggi.getText(),tbPemeriksaan.getSelectedRow(), 10);
                                    tbPemeriksaan.setValueAt(TBerat.getText(),tbPemeriksaan.getSelectedRow(), 11);
                                    tbPemeriksaan.setValueAt(SpO2.getText(),tbPemeriksaan.getSelectedRow(), 12);
                                    tbPemeriksaan.setValueAt(TGCS.getText(),tbPemeriksaan.getSelectedRow(), 13);
                                    tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 14);
                                    tbPemeriksaan.setValueAt(TKeluhan.getText(),tbPemeriksaan.getSelectedRow(), 15);
                                    tbPemeriksaan.setValueAt(TPemeriksaan.getText(),tbPemeriksaan.getSelectedRow(), 16);
                                    tbPemeriksaan.setValueAt(TAlergi.getText(),tbPemeriksaan.getSelectedRow(), 17);
                                    tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                    tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                    tbPemeriksaan.setValueAt(TInstruksi.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                    tbPemeriksaan.setValueAt(TEvaluasi.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                    tbPemeriksaan.setValueAt(KdPeg.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                    tbPemeriksaan.setValueAt(TPegawai.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                    tbPemeriksaan.setValueAt(Jabatan.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                    BtnBatalActionPerformed(evt);
                                }
                            }else{
                                if(akses.getkode().equals(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),22).toString())){
                                    if(Sequel.cekTanggal48jam(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+" "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                        if(TanggalRegistrasi.getText().equals("")){
                                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                        }
                                        if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                            if(Sequel.mengedittf("pemeriksaan_ranap","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                                "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                                "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',spo2='"+SpO2.getText()+"',"+
                                                "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                                                "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',alergi='"+TAlergi.getText()+"',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                                "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',instruksi='"+TInstruksi.getText()+"',evaluasi='"+TEvaluasi.getText()+"'")==true){
                                                tbPemeriksaan.setValueAt(TNoRw.getText(),tbPemeriksaan.getSelectedRow(), 1);
                                                tbPemeriksaan.setValueAt(TNoRM.getText(),tbPemeriksaan.getSelectedRow(), 2);
                                                tbPemeriksaan.setValueAt(TPasien.getText(),tbPemeriksaan.getSelectedRow(), 3);
                                                tbPemeriksaan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaan.getSelectedRow(), 4);
                                                tbPemeriksaan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaan.getSelectedRow(), 5);
                                                tbPemeriksaan.setValueAt(TSuhu.getText(),tbPemeriksaan.getSelectedRow(), 6);
                                                tbPemeriksaan.setValueAt(TTensi.getText(),tbPemeriksaan.getSelectedRow(), 7);
                                                tbPemeriksaan.setValueAt(TNadi.getText(),tbPemeriksaan.getSelectedRow(), 8);
                                                tbPemeriksaan.setValueAt(TRespirasi.getText(),tbPemeriksaan.getSelectedRow(), 9);
                                                tbPemeriksaan.setValueAt(TTinggi.getText(),tbPemeriksaan.getSelectedRow(), 10);
                                                tbPemeriksaan.setValueAt(TBerat.getText(),tbPemeriksaan.getSelectedRow(), 11);
                                                tbPemeriksaan.setValueAt(SpO2.getText(),tbPemeriksaan.getSelectedRow(), 12);
                                                tbPemeriksaan.setValueAt(TGCS.getText(),tbPemeriksaan.getSelectedRow(), 13);
                                                tbPemeriksaan.setValueAt(cmbKesadaran.getSelectedItem().toString(),tbPemeriksaan.getSelectedRow(), 14);
                                                tbPemeriksaan.setValueAt(TKeluhan.getText(),tbPemeriksaan.getSelectedRow(), 15);
                                                tbPemeriksaan.setValueAt(TPemeriksaan.getText(),tbPemeriksaan.getSelectedRow(), 16);
                                                tbPemeriksaan.setValueAt(TAlergi.getText(),tbPemeriksaan.getSelectedRow(), 17);
                                                tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                                tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                                tbPemeriksaan.setValueAt(TInstruksi.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                                tbPemeriksaan.setValueAt(TEvaluasi.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                                tbPemeriksaan.setValueAt(KdPeg.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                                tbPemeriksaan.setValueAt(TPegawai.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                                tbPemeriksaan.setValueAt(Jabatan.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                                BtnBatalActionPerformed(evt);
                                            }
                                        }
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter/petugas yang bersangkutan..!!");
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 4:
                    if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                            (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                            (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                            (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                            (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                            (!TDenominator.getText().trim().equals(""))){
                        if(tbPemeriksaanObstetri.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_obstetri_ranap","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
                                        "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4)+
                                        "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5)+"'",
                                        "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                        "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                        "tinggi_uteri='"+TTinggi_uteri.getText()+"', janin='"+cmbJanin.getSelectedItem()+"', letak='"+TLetak.getText()+"', "+
                                        "panggul='"+cmbPanggul.getSelectedItem()+"', denyut='"+TDenyut.getText()+"', kontraksi='"+cmbKontraksi.getSelectedItem()+"', "+
                                        "kualitas_mnt='"+TKualitas_mnt.getText()+"', kualitas_dtk='"+TKualitas_dtk.getText()+"', "+
                                        "fluksus='"+cmbFluksus.getSelectedItem()+"', albus='"+cmbAlbus.getSelectedItem()+"', vulva='"+TVulva.getText()+"',"+
                                        "portio='"+TPortio.getText()+"', dalam='"+cmbDalam.getSelectedItem()+"', tebal='"+TTebal.getText()+"', "+
                                        "arah='"+cmbArah.getSelectedItem()+"', pembukaan='"+TPembukaan.getText()+"', penurunan='"+TPenurunan.getText()+"', "+
                                        "denominator='"+TDenominator.getText()+"', ketuban='"+cmbKetuban.getSelectedItem()+"', feto='"+cmbFeto.getSelectedItem()+"'")==true){
                                        tbPemeriksaanObstetri.setValueAt(TNoRw.getText(),tbPemeriksaanObstetri.getSelectedRow(), 1);
                                        tbPemeriksaanObstetri.setValueAt(TNoRM.getText(),tbPemeriksaanObstetri.getSelectedRow(), 2);
                                        tbPemeriksaanObstetri.setValueAt(TPasien.getText(),tbPemeriksaanObstetri.getSelectedRow(), 3);
                                        tbPemeriksaanObstetri.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanObstetri.getSelectedRow(), 4);
                                        tbPemeriksaanObstetri.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanObstetri.getSelectedRow(), 5);
                                        tbPemeriksaanObstetri.setValueAt(TTinggi_uteri.getText(),tbPemeriksaanObstetri.getSelectedRow(), 6);
                                        tbPemeriksaanObstetri.setValueAt(cmbJanin.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 7);
                                        tbPemeriksaanObstetri.setValueAt(TLetak.getText(),tbPemeriksaanObstetri.getSelectedRow(), 8);
                                        tbPemeriksaanObstetri.setValueAt(cmbPanggul.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 9);
                                        tbPemeriksaanObstetri.setValueAt(TDenyut.getText(),tbPemeriksaanObstetri.getSelectedRow(), 10);
                                        tbPemeriksaanObstetri.setValueAt(cmbKontraksi.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 11);
                                        tbPemeriksaanObstetri.setValueAt(TKualitas_mnt.getText(),tbPemeriksaanObstetri.getSelectedRow(), 12);
                                        tbPemeriksaanObstetri.setValueAt(TKualitas_dtk.getText(),tbPemeriksaanObstetri.getSelectedRow(), 13);
                                        tbPemeriksaanObstetri.setValueAt(cmbFluksus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 14);
                                        tbPemeriksaanObstetri.setValueAt(cmbAlbus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 15);
                                        tbPemeriksaanObstetri.setValueAt(TVulva.getText(),tbPemeriksaanObstetri.getSelectedRow(), 16);
                                        tbPemeriksaanObstetri.setValueAt(TPortio.getText(),tbPemeriksaanObstetri.getSelectedRow(), 17);
                                        tbPemeriksaanObstetri.setValueAt(cmbDalam.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 18);
                                        tbPemeriksaanObstetri.setValueAt(TTebal.getText(),tbPemeriksaanObstetri.getSelectedRow(), 19);
                                        tbPemeriksaanObstetri.setValueAt(cmbArah.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 20);
                                        tbPemeriksaanObstetri.setValueAt(TPembukaan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 21);
                                        tbPemeriksaanObstetri.setValueAt(TPenurunan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 22);
                                        tbPemeriksaanObstetri.setValueAt(TDenominator.getText(),tbPemeriksaanObstetri.getSelectedRow(), 23);
                                        tbPemeriksaanObstetri.setValueAt(cmbKetuban.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 24);
                                        tbPemeriksaanObstetri.setValueAt(cmbFeto.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 25);
                                        BtnBatalActionPerformed(evt);
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4)+" "+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        if(Sequel.mengedittf("pemeriksaan_obstetri_ranap","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
                                                "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4)+
                                                "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5)+"'",
                                                "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                                "tinggi_uteri='"+TTinggi_uteri.getText()+"', janin='"+cmbJanin.getSelectedItem()+"', letak='"+TLetak.getText()+"', "+
                                                "panggul='"+cmbPanggul.getSelectedItem()+"', denyut='"+TDenyut.getText()+"', kontraksi='"+cmbKontraksi.getSelectedItem()+"', "+
                                                "kualitas_mnt='"+TKualitas_mnt.getText()+"', kualitas_dtk='"+TKualitas_dtk.getText()+"', "+
                                                "fluksus='"+cmbFluksus.getSelectedItem()+"', albus='"+cmbAlbus.getSelectedItem()+"', vulva='"+TVulva.getText()+"',"+
                                                "portio='"+TPortio.getText()+"', dalam='"+cmbDalam.getSelectedItem()+"', tebal='"+TTebal.getText()+"', "+
                                                "arah='"+cmbArah.getSelectedItem()+"', pembukaan='"+TPembukaan.getText()+"', penurunan='"+TPenurunan.getText()+"', "+
                                                "denominator='"+TDenominator.getText()+"', ketuban='"+cmbKetuban.getSelectedItem()+"', feto='"+cmbFeto.getSelectedItem()+"'")==true){
                                                tbPemeriksaanObstetri.setValueAt(TNoRw.getText(),tbPemeriksaanObstetri.getSelectedRow(), 1);
                                                tbPemeriksaanObstetri.setValueAt(TNoRM.getText(),tbPemeriksaanObstetri.getSelectedRow(), 2);
                                                tbPemeriksaanObstetri.setValueAt(TPasien.getText(),tbPemeriksaanObstetri.getSelectedRow(), 3);
                                                tbPemeriksaanObstetri.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanObstetri.getSelectedRow(), 4);
                                                tbPemeriksaanObstetri.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanObstetri.getSelectedRow(), 5);
                                                tbPemeriksaanObstetri.setValueAt(TTinggi_uteri.getText(),tbPemeriksaanObstetri.getSelectedRow(), 6);
                                                tbPemeriksaanObstetri.setValueAt(cmbJanin.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 7);
                                                tbPemeriksaanObstetri.setValueAt(TLetak.getText(),tbPemeriksaanObstetri.getSelectedRow(), 8);
                                                tbPemeriksaanObstetri.setValueAt(cmbPanggul.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 9);
                                                tbPemeriksaanObstetri.setValueAt(TDenyut.getText(),tbPemeriksaanObstetri.getSelectedRow(), 10);
                                                tbPemeriksaanObstetri.setValueAt(cmbKontraksi.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 11);
                                                tbPemeriksaanObstetri.setValueAt(TKualitas_mnt.getText(),tbPemeriksaanObstetri.getSelectedRow(), 12);
                                                tbPemeriksaanObstetri.setValueAt(TKualitas_dtk.getText(),tbPemeriksaanObstetri.getSelectedRow(), 13);
                                                tbPemeriksaanObstetri.setValueAt(cmbFluksus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 14);
                                                tbPemeriksaanObstetri.setValueAt(cmbAlbus.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 15);
                                                tbPemeriksaanObstetri.setValueAt(TVulva.getText(),tbPemeriksaanObstetri.getSelectedRow(), 16);
                                                tbPemeriksaanObstetri.setValueAt(TPortio.getText(),tbPemeriksaanObstetri.getSelectedRow(), 17);
                                                tbPemeriksaanObstetri.setValueAt(cmbDalam.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 18);
                                                tbPemeriksaanObstetri.setValueAt(TTebal.getText(),tbPemeriksaanObstetri.getSelectedRow(), 19);
                                                tbPemeriksaanObstetri.setValueAt(cmbArah.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 20);
                                                tbPemeriksaanObstetri.setValueAt(TPembukaan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 21);
                                                tbPemeriksaanObstetri.setValueAt(TPenurunan.getText(),tbPemeriksaanObstetri.getSelectedRow(), 22);
                                                tbPemeriksaanObstetri.setValueAt(TDenominator.getText(),tbPemeriksaanObstetri.getSelectedRow(), 23);
                                                tbPemeriksaanObstetri.setValueAt(cmbKetuban.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 24);
                                                tbPemeriksaanObstetri.setValueAt(cmbFeto.getSelectedItem().toString(),tbPemeriksaanObstetri.getSelectedRow(), 25);
                                                BtnBatalActionPerformed(evt);
                                        }
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 5:
                   if((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                            (!TInspekuloGine.getText().trim().equals(""))||(!TVulvaInspekulo.getText().trim().equals(""))||
                            (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                            (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                            (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                            (!TCavumDouglas.getText().trim().equals(""))){
                        if(tbPemeriksaanGinekologi.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_ginekologi_ranap","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
                                        "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+
                                        "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5)+"'",
                                        "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                        "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                        "inspeksi='"+TInspeksi.getText()+"', inspeksi_vulva='"+TInspeksiVulva.getText()+"', inspekulo_gine='"+TInspekuloGine.getText()+"', "+
                                        "fluxus_gine='"+cmbFluxusGine.getSelectedItem()+"', fluor_gine='"+cmbFluorGine.getSelectedItem()+"', "+
                                        "vulva_inspekulo='"+TVulvaInspekulo.getText()+"', portio_inspekulo='"+TPortioInspekulo.getText()+"', sondage='"+TSondage.getText()+"', "+
                                        "portio_dalam='"+TPortioDalam.getText()+"', bentuk='"+TBentuk.getText()+"', cavum_uteri='"+TCavumUteri.getText()+"', "+
                                        "mobilitas='"+cmbMobilitas.getSelectedItem()+"', ukuran='"+TUkuran.getText()+"', nyeri_tekan='"+cmbNyeriTekan.getSelectedItem()+"',"+
                                        "adnexa_kanan='"+TAdnexaKanan.getText()+"', adnexa_kiri='"+TAdnexaKiri.getText()+"', cavum_douglas='"+TCavumDouglas.getText()+"'")==true){
                                        tbPemeriksaanGinekologi.setValueAt(TNoRw.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 1);
                                        tbPemeriksaanGinekologi.setValueAt(TNoRM.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 2);
                                        tbPemeriksaanGinekologi.setValueAt(TPasien.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 3);
                                        tbPemeriksaanGinekologi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanGinekologi.getSelectedRow(), 4);
                                        tbPemeriksaanGinekologi.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanGinekologi.getSelectedRow(), 5);
                                        tbPemeriksaanGinekologi.setValueAt(TInspeksi.getText(),tbPemeriksaanGinekologi.getSelectedRow(),6);
                                        tbPemeriksaanGinekologi.setValueAt(TInspeksiVulva.getText(),tbPemeriksaanGinekologi.getSelectedRow(),7);
                                        tbPemeriksaanGinekologi.setValueAt(TInspekuloGine.getText(),tbPemeriksaanGinekologi.getSelectedRow(),8);
                                        tbPemeriksaanGinekologi.setValueAt(cmbFluxusGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),9);
                                        tbPemeriksaanGinekologi.setValueAt(cmbFluorGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),10);
                                        tbPemeriksaanGinekologi.setValueAt(TVulvaInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),11);
                                        tbPemeriksaanGinekologi.setValueAt(TPortioInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),12);
                                        tbPemeriksaanGinekologi.setValueAt(TSondage.getText(),tbPemeriksaanGinekologi.getSelectedRow(),13);
                                        tbPemeriksaanGinekologi.setValueAt(TPortioDalam.getText(),tbPemeriksaanGinekologi.getSelectedRow(),14);
                                        tbPemeriksaanGinekologi.setValueAt(TBentuk.getText(),tbPemeriksaanGinekologi.getSelectedRow(),15);
                                        tbPemeriksaanGinekologi.setValueAt(TCavumUteri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),16);
                                        tbPemeriksaanGinekologi.setValueAt(cmbMobilitas.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),17);
                                        tbPemeriksaanGinekologi.setValueAt(TUkuran.getText(),tbPemeriksaanGinekologi.getSelectedRow(),18);
                                        tbPemeriksaanGinekologi.setValueAt(cmbNyeriTekan.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),19);
                                        tbPemeriksaanGinekologi.setValueAt(TAdnexaKanan.getText(),tbPemeriksaanGinekologi.getSelectedRow(),20);
                                        tbPemeriksaanGinekologi.setValueAt(TAdnexaKiri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),21);
                                        tbPemeriksaanGinekologi.setValueAt(TCavumDouglas.getText(),tbPemeriksaanGinekologi.getSelectedRow(),22);
                                        BtnBatalActionPerformed(evt);
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+" "+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        if(Sequel.mengedittf("pemeriksaan_ginekologi_ranap","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
                                                "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+
                                                "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5)+"'",
                                                "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                                "inspeksi='"+TInspeksi.getText()+"', inspeksi_vulva='"+TInspeksiVulva.getText()+"', inspekulo_gine='"+TInspekuloGine.getText()+"', "+
                                                "fluxus_gine='"+cmbFluxusGine.getSelectedItem()+"', fluor_gine='"+cmbFluorGine.getSelectedItem()+"', "+
                                                "vulva_inspekulo='"+TVulvaInspekulo.getText()+"', portio_inspekulo='"+TPortioInspekulo.getText()+"', sondage='"+TSondage.getText()+"', "+
                                                "portio_dalam='"+TPortioDalam.getText()+"', bentuk='"+TBentuk.getText()+"', cavum_uteri='"+TCavumUteri.getText()+"', "+
                                                "mobilitas='"+cmbMobilitas.getSelectedItem()+"', ukuran='"+TUkuran.getText()+"', nyeri_tekan='"+cmbNyeriTekan.getSelectedItem()+"',"+
                                                "adnexa_kanan='"+TAdnexaKanan.getText()+"', adnexa_kiri='"+TAdnexaKiri.getText()+"', cavum_douglas='"+TCavumDouglas.getText()+"'")==true){
                                                tbPemeriksaanGinekologi.setValueAt(TNoRw.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 1);
                                                tbPemeriksaanGinekologi.setValueAt(TNoRM.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 2);
                                                tbPemeriksaanGinekologi.setValueAt(TPasien.getText(),tbPemeriksaanGinekologi.getSelectedRow(), 3);
                                                tbPemeriksaanGinekologi.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbPemeriksaanGinekologi.getSelectedRow(), 4);
                                                tbPemeriksaanGinekologi.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbPemeriksaanGinekologi.getSelectedRow(), 5);
                                                tbPemeriksaanGinekologi.setValueAt(TInspeksi.getText(),tbPemeriksaanGinekologi.getSelectedRow(),6);
                                                tbPemeriksaanGinekologi.setValueAt(TInspeksiVulva.getText(),tbPemeriksaanGinekologi.getSelectedRow(),7);
                                                tbPemeriksaanGinekologi.setValueAt(TInspekuloGine.getText(),tbPemeriksaanGinekologi.getSelectedRow(),8);
                                                tbPemeriksaanGinekologi.setValueAt(cmbFluxusGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),9);
                                                tbPemeriksaanGinekologi.setValueAt(cmbFluorGine.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),10);
                                                tbPemeriksaanGinekologi.setValueAt(TVulvaInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),11);
                                                tbPemeriksaanGinekologi.setValueAt(TPortioInspekulo.getText(),tbPemeriksaanGinekologi.getSelectedRow(),12);
                                                tbPemeriksaanGinekologi.setValueAt(TSondage.getText(),tbPemeriksaanGinekologi.getSelectedRow(),13);
                                                tbPemeriksaanGinekologi.setValueAt(TPortioDalam.getText(),tbPemeriksaanGinekologi.getSelectedRow(),14);
                                                tbPemeriksaanGinekologi.setValueAt(TBentuk.getText(),tbPemeriksaanGinekologi.getSelectedRow(),15);
                                                tbPemeriksaanGinekologi.setValueAt(TCavumUteri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),16);
                                                tbPemeriksaanGinekologi.setValueAt(cmbMobilitas.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),17);
                                                tbPemeriksaanGinekologi.setValueAt(TUkuran.getText(),tbPemeriksaanGinekologi.getSelectedRow(),18);
                                                tbPemeriksaanGinekologi.setValueAt(cmbNyeriTekan.getSelectedItem().toString(),tbPemeriksaanGinekologi.getSelectedRow(),19);
                                                tbPemeriksaanGinekologi.setValueAt(TAdnexaKanan.getText(),tbPemeriksaanGinekologi.getSelectedRow(),20);
                                                tbPemeriksaanGinekologi.setValueAt(TAdnexaKiri.getText(),tbPemeriksaanGinekologi.getSelectedRow(),21);
                                                tbPemeriksaanGinekologi.setValueAt(TCavumDouglas.getText(),tbPemeriksaanGinekologi.getSelectedRow(),22);
                                                BtnBatalActionPerformed(evt);
                                        }
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break; 
                default:
                    break;                
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void tbRawatDrPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrPrMouseClicked
        if(tabModeDrPr.getRowCount()!=0){
            try {
                getDataDrPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
    }//GEN-LAST:event_tbRawatDrPrMouseClicked

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabModePemeriksaan.getRowCount()!=0){
            try {
                getDataPemeriksaan();
            } catch (java.lang.NullPointerException e) {
            }           
            
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        Valid.pindah(evt,cmbKesadaran,TPenilaian);
    }//GEN-LAST:event_TAlergiKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter.setText(perawatan.dokter.tampil3(KdDok.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,TKdPrw);
        }
    }//GEN-LAST:event_KdDokKeyPressed

    private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        akses.setform("DlgRawatInap");
        perawatan.dokter.emptTeks();
        perawatan.dokter.isCek();
        perawatan.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perawatan.dokter.setLocationRelativeTo(internalFrame1);
        perawatan.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokterActionPerformed

    private void TKdPrwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakanActionPerformed(null);
        }else{            
            Valid.pindah(evt,KdDok,BtnSimpan);
        }
    }//GEN-LAST:event_TKdPrwKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TPerawat.setText(perawatan.petugas.tampil3(kdptg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,TKdPrwPetugas);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        akses.setform("DlgRawatInap");
        perawatan.petugas.emptTeks();
        perawatan.petugas.isCek();
        perawatan.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perawatan.petugas.setLocationRelativeTo(internalFrame1);
        perawatan.petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugasActionPerformed

    private void TKdPrwPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakan4ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptg,BtnSimpan);
        }
    }//GEN-LAST:event_TKdPrwPetugasKeyPressed

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TPerawat2.setText(perawatan.petugas.tampil3(kdptg2.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugas2ActionPerformed(null);
        }else{
            Valid.pindah(evt,KdDok2,TKdPrwDokterPetugas);
        }
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        akses.setform("DlgRawatInap");
        perawatan.petugas.emptTeks();
        perawatan.petugas.isCek();
        perawatan.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perawatan.petugas.setLocationRelativeTo(internalFrame1);
        perawatan.petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TDokter2.setText(perawatan.dokter.tampil3(KdDok2.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter2ActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,kdptg2);
        }
    }//GEN-LAST:event_KdDok2KeyPressed

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter2ActionPerformed
        akses.setform("DlgRawatInap");
        perawatan.dokter.emptTeks();
        perawatan.dokter.isCek();
        perawatan.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perawatan.dokter.setLocationRelativeTo(internalFrame1);
        perawatan.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter2ActionPerformed

    private void TKdPrwDokterPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwDokterPetugasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakan6ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptg2,BtnSimpan);
        }
    }//GEN-LAST:event_TKdPrwDokterPetugasKeyPressed

    private void tbPemeriksaanObstetriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanObstetriMouseClicked
        // TODO add your handling code here:
        if(tabModeObstetri.getRowCount()!=0) {
            try {
                getDataPemeriksaanObstetri();

            } catch (java.lang.NullPointerException e) {

            }
        }
    }//GEN-LAST:event_tbPemeriksaanObstetriMouseClicked

    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput1ActionPerformed
        // TODO add your handling code here:
        isForm2();
    }//GEN-LAST:event_ChkInput1ActionPerformed

    private void TTinggi_uteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggi_uteriKeyPressed
        Valid.pindah(evt,TKdPrw,cmbJanin);
    }//GEN-LAST:event_TTinggi_uteriKeyPressed

    private void TLetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLetakKeyPressed
        Valid.pindah(evt,cmbJanin,cmbPanggul);
    }//GEN-LAST:event_TLetakKeyPressed

    private void TKualitas_dtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKualitas_dtkKeyPressed
        Valid.pindah(evt,TKualitas_mnt,cmbFluksus);
    }//GEN-LAST:event_TKualitas_dtkKeyPressed

    private void cmbPanggulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPanggulKeyPressed
        Valid.pindah(evt,TLetak,TDenyut);
    }//GEN-LAST:event_cmbPanggulKeyPressed

    private void TTebalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTebalKeyPressed
        Valid.pindah(evt,cmbDalam,cmbArah);
    }//GEN-LAST:event_TTebalKeyPressed

    private void TDenyutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDenyutKeyPressed
        Valid.pindah(evt,cmbPanggul,cmbKontraksi);
    }//GEN-LAST:event_TDenyutKeyPressed

    private void TDenominatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDenominatorKeyPressed
        Valid.pindah(evt,TPenurunan,cmbFeto);
    }//GEN-LAST:event_TDenominatorKeyPressed

    private void TKualitas_mntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKualitas_mntKeyPressed
        Valid.pindah(evt,cmbKontraksi,TKualitas_dtk);
    }//GEN-LAST:event_TKualitas_mntKeyPressed

    private void cmbFetoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFetoKeyPressed
        Valid.pindah(evt,TDenominator,BtnSimpan);
    }//GEN-LAST:event_cmbFetoKeyPressed

    private void cmbJaninKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJaninKeyPressed
        Valid.pindah(evt,TTinggi_uteri,TLetak);
    }//GEN-LAST:event_cmbJaninKeyPressed

    private void cmbKetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKetubanKeyPressed
        Valid.pindah(evt,cmbAlbus,TVulva);
    }//GEN-LAST:event_cmbKetubanKeyPressed

    private void TPortioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioKeyPressed
        Valid.pindah(evt,TVulva,cmbDalam);
    }//GEN-LAST:event_TPortioKeyPressed

    private void TVulvaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVulvaKeyPressed
        Valid.pindah(evt,cmbKetuban,TPortio);
    }//GEN-LAST:event_TVulvaKeyPressed

    private void cmbKontraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKontraksiKeyPressed
        Valid.pindah(evt,TDenyut,TKualitas_mnt);
    }//GEN-LAST:event_cmbKontraksiKeyPressed

    private void cmbAlbusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAlbusKeyPressed
        Valid.pindah(evt,cmbFluksus,cmbKetuban);
    }//GEN-LAST:event_cmbAlbusKeyPressed

    private void cmbFluksusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluksusKeyPressed
        Valid.pindah(evt,TKualitas_dtk,cmbAlbus);
    }//GEN-LAST:event_cmbFluksusKeyPressed

    private void cmbDalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDalamKeyPressed
        Valid.pindah(evt,TPortio,TTebal);
    }//GEN-LAST:event_cmbDalamKeyPressed

    private void TPembukaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPembukaanKeyPressed
        Valid.pindah(evt,cmbArah,TPenurunan);
    }//GEN-LAST:event_TPembukaanKeyPressed

    private void TPenurunanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenurunanKeyPressed
        Valid.pindah(evt,TPembukaan,TDenominator);
    }//GEN-LAST:event_TPenurunanKeyPressed

    private void cmbArahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbArahKeyPressed
        Valid.pindah(evt,TTebal,TPembukaan);
    }//GEN-LAST:event_cmbArahKeyPressed

    private void btnTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDok,"Dokter");
        }else{  
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan.setPetugas(KdDok.getText(),TDokter.getText(),"-","-");
                akses.setform("DlgRawatInap");
                perawatan.emptTeks();
                perawatan.isCek();
                perawatan.tampil();
                perawatan.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan.setLocationRelativeTo(internalFrame1);
                perawatan.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakanActionPerformed

    private void btnTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakanKeyPressed
        Valid.pindah(evt,TKdPrw,TKeluhan);
    }//GEN-LAST:event_btnTindakanKeyPressed

    private void btnTindakan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakan3ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDok,"Dokter");
        }else{  
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan2.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan2.setPetugas(KdDok.getText(),TDokter.getText(),TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","",
                    TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());
                akses.setform("DlgRawatInap");
                perawatan2.isCek();
                perawatan2.tampil2();
                perawatan2.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan2.setLocationRelativeTo(internalFrame1);
                perawatan2.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakan3ActionPerformed

    private void btnTindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakan3KeyPressed

    private void btnTindakan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakan4ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TPerawat.getText().trim().equals("")){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan.setPetugas(kdptg.getText(),TPerawat.getText(),"-","-");
                akses.setform("DlgRawatInap");
                perawatan.emptTeks();
                perawatan.isCek();
                perawatan.tampil();
                perawatan.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan.setLocationRelativeTo(internalFrame1);
                perawatan.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakan4ActionPerformed

    private void btnTindakan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakan4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakan4KeyPressed

    private void btnTindakan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakan5ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TPerawat.getText().trim().equals("")){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan2.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan2.setPetugas(kdptg.getText(),TPerawat.getText(),TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","",
                    TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());
                akses.setform("DlgRawatInap");
                perawatan2.isCek();
                perawatan2.tampil2();
                perawatan2.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan2.setLocationRelativeTo(internalFrame1);
                perawatan2.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakan5ActionPerformed

    private void btnTindakan5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakan5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakan5KeyPressed

    private void btnTindakan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakan6ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter2.getText().trim().equals("")){
            Valid.textKosong(KdDok2,"Dokter");
        }else if(TPerawat2.getText().trim().equals("")){
            Valid.textKosong(kdptg2,"perawat");
        }else{ 
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan.setNoRm(TNoRw.getText(),"rawat_inap_drpr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan.setPetugas(KdDok2.getText(),TDokter2.getText(),"-","-");
                akses.setform("DlgRawatInap");
                perawatan.emptTeks();
                perawatan.isCek();
                perawatan.tampil();
                perawatan.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan.setLocationRelativeTo(internalFrame1);
                perawatan.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakan6ActionPerformed

    private void btnTindakan6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakan6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakan6KeyPressed

    private void btnTindakan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakan7ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter2.getText().trim().equals("")){
            Valid.textKosong(KdDok2,"Dokter");
        }else if(TPerawat2.getText().trim().equals("")){
            Valid.textKosong(kdptg2,"perawat");
        }else{ 
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                perawatan2.setNoRm(TNoRw.getText(),"rawat_inap_drpr",DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false,TPasien.getText());
                perawatan2.setPetugas(KdDok2.getText(),TDokter2.getText(),TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),kdptg2.getText(),TPerawat2.getText(),
                    TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());
                akses.setform("DlgRawatInap");
                perawatan2.isCek();
                perawatan2.tampil2();
                perawatan2.setSize(this.getWidth()-20,this.getHeight()-20);
                perawatan2.setLocationRelativeTo(internalFrame1);
                perawatan2.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnTindakan7ActionPerformed

    private void btnTindakan7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakan7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakan7KeyPressed

    private void tbPemeriksaanGinekologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanGinekologiMouseClicked
        // TODO add your handling code here:
        if(tabModeGinekologi.getRowCount()!=0) {
            try {
                getDataPemeriksaanGinekologi();

            } catch (java.lang.NullPointerException e) {

            }
        }
    }//GEN-LAST:event_tbPemeriksaanGinekologiMouseClicked

    private void Scroll5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Scroll5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll5KeyPressed

    private void ChkInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput2ActionPerformed
        isForm3();
    }//GEN-LAST:event_ChkInput2ActionPerformed

    private void TInspeksiVulvaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspeksiVulvaKeyPressed
        Valid.pindah(evt,TInspeksi,TInspekuloGine);
    }//GEN-LAST:event_TInspeksiVulvaKeyPressed

    private void TAdnexaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAdnexaKananKeyPressed
        Valid.pindah(evt,cmbNyeriTekan,TAdnexaKiri);
    }//GEN-LAST:event_TAdnexaKananKeyPressed

    private void cmbMobilitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMobilitasKeyPressed
        Valid.pindah(evt,TCavumUteri,TUkuran);
    }//GEN-LAST:event_cmbMobilitasKeyPressed

    private void TInspekuloGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspekuloGineKeyPressed
        Valid.pindah(evt,TInspeksiVulva,cmbFluxusGine);
    }//GEN-LAST:event_TInspekuloGineKeyPressed

    private void TPortioInspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioInspekuloKeyPressed
        Valid.pindah(evt,TVulvaInspekulo,TSondage);
    }//GEN-LAST:event_TPortioInspekuloKeyPressed

    private void TCavumUteriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCavumUteriKeyPressed
        Valid.pindah(evt,TBentuk,cmbMobilitas);
    }//GEN-LAST:event_TCavumUteriKeyPressed

    private void cmbFluorGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluorGineKeyPressed
        Valid.pindah(evt,cmbFluxusGine,TVulvaInspekulo);
    }//GEN-LAST:event_cmbFluorGineKeyPressed

    private void TInspeksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInspeksiKeyPressed
        Valid.pindah(evt,TNoRw,TInspeksiVulva);
    }//GEN-LAST:event_TInspeksiKeyPressed

    private void cmbFluxusGineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbFluxusGineKeyPressed
        Valid.pindah(evt,TInspekuloGine,cmbFluorGine);
    }//GEN-LAST:event_cmbFluxusGineKeyPressed

    private void TVulvaInspekuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TVulvaInspekuloKeyPressed
        Valid.pindah(evt,cmbFluorGine,TPortioInspekulo);
    }//GEN-LAST:event_TVulvaInspekuloKeyPressed

    private void TPortioDalamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPortioDalamKeyPressed
        Valid.pindah(evt,TSondage,TBentuk);
    }//GEN-LAST:event_TPortioDalamKeyPressed

    private void TBentukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBentukKeyPressed
        Valid.pindah(evt,TPortioDalam,TCavumUteri);
    }//GEN-LAST:event_TBentukKeyPressed

    private void cmbNyeriTekanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbNyeriTekanKeyPressed
        Valid.pindah(evt,TUkuran,TAdnexaKanan);
    }//GEN-LAST:event_cmbNyeriTekanKeyPressed

    private void TSondageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSondageKeyPressed
        Valid.pindah(evt,TPortioInspekulo,TPortioDalam);
    }//GEN-LAST:event_TSondageKeyPressed

    private void TAdnexaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAdnexaKiriKeyPressed
        Valid.pindah(evt,TAdnexaKanan,TCavumDouglas);
    }//GEN-LAST:event_TAdnexaKiriKeyPressed

    private void TCavumDouglasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCavumDouglasKeyPressed
        Valid.pindah(evt,TAdnexaKiri,BtnSimpan);
    }//GEN-LAST:event_TCavumDouglasKeyPressed

    private void TUkuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUkuranKeyPressed
        Valid.pindah(evt,cmbMobilitas,cmbNyeriTekan);
    }//GEN-LAST:event_TUkuranKeyPressed

    private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        Valid.pindah2(evt,KdPeg,TPemeriksaan);
    }//GEN-LAST:event_TKeluhanKeyPressed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        Valid.pindah2(evt,TKeluhan,TSuhu);
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void tbRawatDrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyReleased
        if(tabModeDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }
                       
        }
    }//GEN-LAST:event_tbRawatDrKeyReleased

    private void tbRawatPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyReleased
        if(tabModePr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
    }//GEN-LAST:event_tbRawatPrKeyReleased

    private void tbRawatDrPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrPrKeyReleased
        if(tabModeDrPr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDrPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
    }//GEN-LAST:event_tbRawatDrPrKeyReleased

    private void tbPemeriksaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyReleased
        if(tabModePemeriksaan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPemeriksaan();
                } catch (java.lang.NullPointerException e) {
                }
            }
                       
        }
    }//GEN-LAST:event_tbPemeriksaanKeyReleased

    private void tbPemeriksaanObstetriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanObstetriKeyReleased
        if(tabModeObstetri.getRowCount()!=0) {
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanObstetri();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanObstetriKeyReleased

    private void tbPemeriksaanGinekologiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanGinekologiKeyReleased
        if(tabModeGinekologi.getRowCount()!=0) {
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanGinekologi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanGinekologiKeyReleased

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),
                        cmbDtk.getSelectedItem().toString(),KdDok.getText(),TDokter.getText(),"ranap");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnResepObatActionPerformed

    private void BtnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar=new DlgCopyResep(null,false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),jenisbayar,"ranap");
            daftar.tampil();
            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCopyResepActionPerformed

    private void BtnInputObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInputObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                if(Sequel.cariInteger("select count(stok_obat_pasien.no_rawat) from stok_obat_pasien where stok_obat_pasien.no_rawat=? ",TNoRw.getText())>0){
                    DlgCariObat3 dlgobt=new DlgCariObat3(null,false);
                    dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPTgl.getDate());
                    dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgobt.setLocationRelativeTo(internalFrame1);
                    dlgobt.setVisible(true);
                }else{
                    DlgCariObat2 dlgobt=new DlgCariObat2(null,false);
                    dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPTgl.getDate());
                    dlgobt.isCek();
                    dlgobt.tampil();
                    dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgobt.setLocationRelativeTo(internalFrame1);
                    dlgobt.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_BtnInputObatActionPerformed

    private void BtnObatBhpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObatBhpActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm2(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ranap");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnObatBhpActionPerformed

    private void BtnBerkasDigitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBerkasDigitalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnBerkasDigitalActionPerformed

    private void BtnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanLabActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),"Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPermintaanLabActionPerformed

    private void BtnPermintaanRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanRadActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),"Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPermintaanRadActionPerformed

    private void BtnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSKDPActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            SuratKontrol form=new SuratKontrol(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            kode_poli=Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
            form.setNoRm(TNoRM.getText(),TPasien.getText(), kode_poli,Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),KdDok.getText(),TDokter.getText());
            form.setVisible(true);
        }
    }//GEN-LAST:event_BtnSKDPActionPerformed

    private void BtnRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukKeluarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgRujuk dlgrjk=new DlgRujuk(null,false);
                dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.emptTeks();
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                dlgrjk.tampil();
                dlgrjk.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }            
        }
    }//GEN-LAST:event_BtnRujukKeluarActionPerformed

    private void BtnCatatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{       
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,true);
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanActionPerformed

    private void BtnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{            
            DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.isCek();
            resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ranap");
            resep.panelDiagnosa1.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_BtnDiagnosaActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenilaianKeyPressed
        Valid.pindah2(evt,TAlergi,TindakLanjut);
    }//GEN-LAST:event_TPenilaianKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah2(evt,TPenilaian,TInstruksi);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void BtnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResumeActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasienRanap resume=new RMDataResumePasienRanap(null,false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnResumeActionPerformed

    private void BtnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataAsuhanGizi form=new RMDataAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAsuhanGiziActionPerformed

    private void BtnMonitoringAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringAsuhanGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringAsuhanGizi form=new RMDataMonitoringAsuhanGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringAsuhanGiziActionPerformed

    private void BtnPermintaanStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanStokActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPermintaanStokPasien resep=new DlgPermintaanStokPasien(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate());
                resep.isCek();
                resep.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnPermintaanStokActionPerformed

    private void KdPegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPegawaiActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,TKeluhan);
        }
    }//GEN-LAST:event_KdPegKeyPressed

    private void BtnSeekPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPegawaiActionPerformed
        akses.setform("DlgRawatJalan");
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeekPegawaiActionPerformed

    private void TInstruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInstruksiKeyPressed
        Valid.pindah2(evt,TindakLanjut,BtnSimpan);
    }//GEN-LAST:event_TInstruksiKeyPressed

    private void BtnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            DlgBookingOperasi form=new DlgBookingOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),kamar,"Ranap");
            form.setVisible(true);
        }
    }//GEN-LAST:event_BtnJadwalOperasiActionPerformed

    private void BtnAwalKeperawatanKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanKebidananRanap form=new RMPenilaianAwalKeperawatanKebidananRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari1.getDate(),jenisbayar,TNoRM.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanKandunganActionPerformed

    private void BtnAwalMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapDewasa form=new RMPenilaianAwalMedisRanapDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisActionPerformed

    private void BtnAwalMedisKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapKandungan form=new RMPenilaianAwalMedisRanapKandungan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisKandunganActionPerformed

    private void BtnAwalFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalFisioterapiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalFisioterapiActionPerformed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,TNadi,TGCS);
    }//GEN-LAST:event_SpO2KeyPressed

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        Valid.pindah(evt,TNadi,cmbKesadaran);
    }//GEN-LAST:event_TGCSKeyPressed

    private void cmbKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKesadaranKeyPressed
        Valid.pindah(evt,TGCS,TAlergi);
    }//GEN-LAST:event_cmbKesadaranKeyPressed

    private void TEvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TEvaluasiKeyPressed
        Valid.pindah2(evt,TInstruksi,BtnSimpan);
    }//GEN-LAST:event_TEvaluasiKeyPressed

    private void BtnPermintaanResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanResepPulangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                DlgPermintaanResepPulang resep=new DlgPermintaanResepPulang(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setVisible(true);
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate());
                resep.isCek();
            }
        }
    }//GEN-LAST:event_BtnPermintaanResepPulangActionPerformed

    private void BtnCatatanObservasiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanap form=new RMDataCatatanObservasiRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapActionPerformed

    private void BtnCatatanObservasiRanapKebidananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapKebidananActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanapKebidanan form=new RMDataCatatanObservasiRanapKebidanan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapKebidananActionPerformed

    private void BtnCatatanObservasiRanapPostPartumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapPostPartumActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRanapPostPartum form=new RMDataCatatanObservasiRanapPostPartum(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiRanapPostPartumActionPerformed

    private void BtnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPsikologActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPsikologi form=new RMPenilaianPsikologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPsikologActionPerformed

    private void BtnAwalKeperawatanUmumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanUmumActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRanap form=new RMPenilaianAwalKeperawatanRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),jenisbayar,TNoRM.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanUmumActionPerformed

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TNoRwMouseClicked
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        }
    }//GEN-LAST:event_TNoRwMouseClicked

    private void BtnCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanKeperawatanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanKeperawatanRanap form=new RMDataCatatanKeperawatanRanap(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanKeperawatanActionPerformed

    private void BtnPemantauanPEWSAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanPEWS form=new RMPemantauanPEWS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSAnakActionPerformed

    private void BtnPenilaianPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreOperasi form=new RMPenilaianPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreOperasiActionPerformed

    private void BtnPenilaianPreAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreAnastesi form=new RMPenilaianPreAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreAnestesiActionPerformed

    private void BtnPerencanaanPemulanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerencanaanPemulanganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPerencanaanPemulangan form=new RMPerencanaanPemulangan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPerencanaanPemulanganActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhDewasa form=new RMPenilaianLanjutanRisikoJatuhDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhDewasaActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhAnak form=new RMPenilaianLanjutanRisikoJatuhAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhAnakActionPerformed

    private void Btn5SoapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn5SoapActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else if(TPegawai.getText().trim().equals("")||KdPeg.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu petugas/dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCari5SOAPTerakhir soapterakhir=new RMCari5SOAPTerakhir(null,false);  
        
            soapterakhir.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(soapterakhir.getTable().getSelectedRow()!= -1){   
                        TKeluhan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),2).toString());
                        TPemeriksaan.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),3).toString());
                        TPenilaian.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),4).toString());
                        TindakLanjut.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),5).toString());
                        TInstruksi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),6).toString());
                        TEvaluasi.setText(soapterakhir.getTable().getValueAt(soapterakhir.getTable().getSelectedRow(),7).toString());
                        TEvaluasi.requestFocus();                    
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
            
            soapterakhir.setNoRM(TNoRM.getText(),KdPeg.getText(),"Ranap");
            soapterakhir.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            soapterakhir.setLocationRelativeTo(internalFrame1);
            soapterakhir.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_Btn5SoapActionPerformed

    private void BtnPenilaianTambahanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanGeriatri form=new RMPenilaianTambahanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanGeriatriActionPerformed

    private void BtnSkriningNutrisiDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiDewasaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiDewasa form=new RMSkriningNutrisiDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiDewasaActionPerformed

    private void BtnHasilPemeriksaanUSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilPemeriksaanUSGActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSG form=new RMHasilPemeriksaanUSG(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnHasilPemeriksaanUSGActionPerformed

    private void BtnSkriningNutrisiLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiLansiaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiLansia form=new RMSkriningNutrisiLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiLansiaActionPerformed

    private void BtnSkriningNutrisiAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningNutrisiAnakActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningNutrisiAnak form=new RMSkriningNutrisiAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setVisible(true);
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiAnakActionPerformed

    private void BtnSkriningGiziLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningGiziLanjutActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataSkriningGiziLanjut form=new RMDataSkriningGiziLanjut(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningGiziLanjutActionPerformed

    private void BtnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKonselingFarmasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMKonselingFarmasi form=new RMKonselingFarmasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnKonselingFarmasiActionPerformed

    private void BtnInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInformasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnInformasiObatActionPerformed

    private void BtnTransferAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTransferAntarRuangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form=new RMTransferPasienAntarRuang(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTransferAntarRuangActionPerformed

    private void BtnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanCekGDSActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanCekGDS form=new RMDataCatatanCekGDS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanCekGDSActionPerformed

    private void BtnChecklistPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPreOperasi form=new RMChecklistPreOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPreOperasiActionPerformed

    private void BtnSignInSebelumAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignInSebelumAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignInSebelumAnastesi form=new RMSignInSebelumAnastesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignInSebelumAnestesiActionPerformed

    private void BtnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTimeOutSebelumInsisiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTimeOutSebelumInsisi form=new RMTimeOutSebelumInsisi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTimeOutSebelumInsisiActionPerformed

    private void BtnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignOutSebelumMenutupLukaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSignOutSebelumMenutupLuka form=new RMSignOutSebelumMenutupLuka(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSignOutSebelumMenutupLukaActionPerformed

    private void BtnChecklistPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistPostOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPostOperasi form=new RMChecklistPostOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistPostOperasiActionPerformed

    private void BtnRekonsiliasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekonsiliasiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRekonsiliasiObat form=new RMRekonsiliasiObat(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnRekonsiliasiObatActionPerformed

    private void BtnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienTerminalActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienTerminal form=new RMPenilaianPasienTerminal(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienTerminalActionPerformed

    private void BtnMonitoringReaksiTranfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMonitoringReaksiTranfusiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataMonitoringReaksiTranfusi form=new RMDataMonitoringReaksiTranfusi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringReaksiTranfusiActionPerformed

    private void BtnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianKorbanKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianKorbanKekerasan form=new RMPenilaianKorbanKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianKorbanKekerasanActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhLansia form=new RMPenilaianLanjutanRisikoJatuhLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhLansiaActionPerformed

    private void BtnPenilaianPasienPenyakitMenularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienPenyakitMenularActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienPenyakitMenular form=new RMPenilaianPasienPenyakitMenular(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienPenyakitMenularActionPerformed

    private void BtnPemantauanPEWSDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanPEWSDewasaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSD form=new RMPemantauanEWSD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanPEWSDewasaActionPerformed

    private void BtnPenilaianTambahanBunuhDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanBunuhDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanBunuhDiri form=new RMPenilaianTambahanBunuhDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanBunuhDiriActionPerformed

    private void BtnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanPerilakuKekerasan form=new RMPenilaianTambahanPerilakuKekerasan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanPerilakuKekerasanActionPerformed

    private void BtnPenilaianTambahanMelarikanDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTambahanMelarikanDiri form=new RMPenilaianTambahanMelarikanDiri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed

    private void BtnPemantauanMEOWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanMEOWSActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanMEOWS form=new RMPemantauanMEOWS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanMEOWSActionPerformed

    private void BtnCatatanADIMEGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanADIMEGiziActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanADIMEGizi form=new RMCatatanADIMEGizi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            form.emptTeks();
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanADIMEGiziActionPerformed

    private void BtnChecklistKriteriaMasukHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukHCUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukHCU form=new RMChecklistKriteriaMasukHCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukHCUActionPerformed

    private void BtnChecklistKriteriaKeluarHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaKeluarHCUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaKeluarHCU form=new RMChecklistKriteriaKeluarHCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaKeluarHCUActionPerformed

    private void BtnPenilaianResikoDekubitusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianResikoDekubitusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianRisikoDekubitus form=new RMPenilaianRisikoDekubitus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianResikoDekubitusActionPerformed

    private void BtnDokumentasiESWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokumentasiESWLActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilTindakanESWL form=new RMHasilTindakanESWL(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnDokumentasiESWLActionPerformed

    private void BtnChecklistKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukICU form=new RMChecklistKriteriaMasukICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukICUActionPerformed

    private void BtnChecklistKriteriaKeluarICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaKeluarICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaKeluarICU form=new RMChecklistKriteriaKeluarICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaKeluarICUActionPerformed

    private void BtnFollowUpDBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFollowUpDBDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataFollowUpDBD form=new RMDataFollowUpDBD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnFollowUpDBDActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianRisikoJatuhNeonatus form=new RMPenilaianRisikoJatuhNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhNeonatusActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhGeriatri form=new RMPenilaianLanjutanRisikoJatuhGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhGeriatriActionPerformed

    private void BtnPemantauanEWSNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemantauanEWSNeonatusActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPemantauanEWSNeonatus form=new RMPemantauanEWSNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanEWSNeonatusActionPerformed

    private void BtnAwalMedisHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisHemodialisaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisHemodialisa form=new RMPenilaianAwalMedisHemodialisa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),"Rawat Inap");
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisHemodialisaActionPerformed

    private void BtnPenilaianKecemasanAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianKecemasanAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLevelKecemasanRanapAnak form=new RMPenilaianLevelKecemasanRanapAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianKecemasanAnakActionPerformed

    private void BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanRisikoJatuhPsikiatri form=new RMPenilaianLanjutanRisikoJatuhPsikiatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanResikoJatuhPsikiatriActionPerformed

    private void BtnPenilaianLanjutanSkriningFungsionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanSkriningFungsionalActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianLanjutanSkriningFungsional form=new RMPenilaianLanjutanSkriningFungsional(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanSkriningFungsionalActionPerformed

    private void BtnPenilaianUlangNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianUlangNyeriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianUlangNyeri form=new RMPenilaianUlangNyeri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianUlangNyeriActionPerformed

    private void BtnPengkajianRestrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPengkajianRestrainActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPengkajianRestrain form=new RMPengkajianRestrain(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPengkajianRestrainActionPerformed

    private void BtnCatatanPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanPersalinanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanPersalinan form=new RMCatatanPersalinan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanPersalinanActionPerformed

    private void BtnSkorAldrettePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkorAldrettePascaAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringAldrettePascaAnestesi form=new RMMonitoringAldrettePascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkorAldrettePascaAnestesiActionPerformed

    private void BtnSkorStewardPascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkorStewardPascaAnestesiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringStewardPascaAnestesi form=new RMMonitoringStewardPascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkorStewardPascaAnestesiActionPerformed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TPemeriksaan,TTensi);
    }//GEN-LAST:event_TSuhuKeyPressed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        Valid.pindah(evt,TBerat,TRespirasi);
    }//GEN-LAST:event_TTinggiKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TBerat);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        Valid.pindah(evt,TTinggi,TNadi);
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        Valid.pindah(evt,TTensi,TTinggi);
    }//GEN-LAST:event_TBeratKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,TRespirasi,SpO2);
    }//GEN-LAST:event_TNadiKeyPressed

    private void BtnSkorBromagePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMonitoringBromagePascaAnestesi form=new RMMonitoringBromagePascaAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }   
    
    private void BtnPenilaianPreInduksiActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPreInduksi form=new RMPenilaianPreInduksi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }  
    
    private void BtnHasilPemeriksaanUSGUrologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGUrologi form=new RMHasilPemeriksaanUSGUrologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }   
    
    private void BtnHasilPemeriksaanUSGGynecologiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGGynecologi form=new RMHasilPemeriksaanUSGGynecologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilPemeriksaanEKGActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanEKG form=new RMHasilPemeriksaanEKG(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilPemeriksaanUSGNeonatusActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanUSGNeonatus form=new RMHasilPemeriksaanUSGNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilEndoskopiFaringLaringActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilEndoskopiFaringLaring form=new RMHasilEndoskopiFaringLaring(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilEndoskopiHidungActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilEndoskopiHidung form=new RMHasilEndoskopiHidung(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilEndoskopiTelingaActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilEndoskopiTelinga form=new RMHasilEndoskopiTelinga(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnAwalKeperawatanNeonatusActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRanapNeonatus form=new RMPenilaianAwalKeperawatanRanapNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPenilaianPasienImunitasRendahActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienImunitasRendah form=new RMPenilaianPasienImunitasRendah(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanKeseimbanganCairanActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanKeseimbanganCairan form=new RMDataCatatanKeseimbanganCairan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiCHBPActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiCHBP form=new RMDataCatatanObservasiCHBP(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiInduksiPersalinanActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiInduksiPersalinan form=new RMDataCatatanObservasiInduksiPersalinan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPermintaanKonsultasiMedikActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanKonsultasiMedik form=new DlgPermintaanKonsultasiMedik(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnAwalKeperawatanBayiAnakActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRanapBayiAnak form=new RMPenilaianAwalKeperawatanRanapBayiAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiRestrainNonfarmakologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiRestrainNonFarmakologi form=new RMDataCatatanObservasiRestrainNonFarmakologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiVentilatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiRanapActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiVentilator form=new RMDataCatatanObservasiVentilator(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanAnastesiSedasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanAnastesiSedasi form=new RMCatatanAnastesiSedasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnChecklistPemberianFibrinolitikActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistPemberianFibrinolitik form=new RMChecklistPemberianFibrinolitik(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPenilaianPsikologKlinisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPsikologActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPsikologiKlinis form=new RMPenilaianPsikologiKlinis(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnAwalMedisNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRanapNeonatus form=new RMPenilaianAwalMedisRanapNeonatus(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPenilaianDerajatDehidrasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianDerajatDehidrasi form=new RMPenilaianDerajatDehidrasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnHasilPemeriksaanECHOActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanEcho form=new RMHasilPemeriksaanEcho(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPenilaianBayiBaruLahirActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianBayiBaruLahir form=new RMPenilaianBayiBaruLahir(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnLaporanTindakanActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMLaporanTindakan form=new RMLaporanTindakan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnPelaksanaanInformasiEdukasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPelaksanaanInformasiEdukasi form=new RMPelaksanaanInformasiEdukasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiHemodialisa form=new RMDataCatatanObservasiHemodialisa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanCairanHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanCairanHemodialisa form=new RMDataCatatanCairanHemodialisa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanPengkajianPaskaOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPreOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMCatatanPengkajianPaskaOperasi form=new RMCatatanPengkajianPaskaOperasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiBayi form=new RMDataCatatanObservasiBayi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnChecklistKesiapanAnestesiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKesiapanAnestesi form=new RMChecklistKesiapanAnestesi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnHasilPemeriksaanSlitLampActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanSlitLamp form=new RMHasilPemeriksaanSlitLamp(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    } 
    
    private void BtnHasilPemeriksaanOCTActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanOCT form=new RMHasilPemeriksaanOCT(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatInap dialog = new DlgRawatInap(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField BagianRS;
    private javax.swing.JTextField Bhp;
    private widget.Button Btn5Soap;
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalKeperawatanKandungan;
    private widget.Button BtnAwalKeperawatanUmum;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedisHemodialisa;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnCari;
    private widget.Button BtnCatatan;
    private widget.Button BtnCatatanADIMEGizi;
    private widget.Button BtnCatatanCekGDS;
    private widget.Button BtnCatatanKeperawatan;
    private widget.Button BtnCatatanObservasiRanap;
    private widget.Button BtnCatatanObservasiRanapKebidanan;
    private widget.Button BtnCatatanObservasiRanapPostPartum;
    private widget.Button BtnCatatanPersalinan;
    private widget.Button BtnChecklistKriteriaKeluarHCU;
    private widget.Button BtnChecklistKriteriaKeluarICU;
    private widget.Button BtnChecklistKriteriaMasukHCU;
    private widget.Button BtnChecklistKriteriaMasukICU;
    private widget.Button BtnChecklistPostOperasi;
    private widget.Button BtnChecklistPreOperasi;
    private widget.Button BtnCopyResep;
    private widget.Button BtnDiagnosa;
    private widget.Button BtnDokumentasiESWL;
    private widget.Button BtnEdit;
    private widget.Button BtnFollowUpDBD;
    private widget.Button BtnHapus;
    private widget.Button BtnHasilPemeriksaanUSG;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnInputObat;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnMonitoringReaksiTranfusi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnPemantauanEWSNeonatus;
    private widget.Button BtnPemantauanMEOWS;
    private widget.Button BtnPemantauanPEWSAnak;
    private widget.Button BtnPemantauanPEWSDewasa;
    private widget.Button BtnPengkajianRestrain;
    private widget.Button BtnPenilaianKecemasanAnak;
    private widget.Button BtnPenilaianKorbanKekerasan;
    private widget.Button BtnPenilaianLanjutanResikoJatuhAnak;
    private widget.Button BtnPenilaianLanjutanResikoJatuhDewasa;
    private widget.Button BtnPenilaianLanjutanResikoJatuhGeriatri;
    private widget.Button BtnPenilaianLanjutanResikoJatuhLansia;
    private widget.Button BtnPenilaianLanjutanResikoJatuhNeonatus;
    private widget.Button BtnPenilaianLanjutanResikoJatuhPsikiatri;
    private widget.Button BtnPenilaianLanjutanSkriningFungsional;
    private widget.Button BtnPenilaianPasienPenyakitMenular;
    private widget.Button BtnPenilaianPasienTerminal;
    private widget.Button BtnPenilaianPreAnestesi;
    private widget.Button BtnPenilaianPreOperasi;
    private widget.Button BtnPenilaianPsikolog;
    private widget.Button BtnPenilaianResikoDekubitus;
    private widget.Button BtnPenilaianTambahanBunuhDiri;
    private widget.Button BtnPenilaianTambahanGeriatri;
    private widget.Button BtnPenilaianTambahanMelarikanDiri;
    private widget.Button BtnPenilaianTambahanPerilakuKekerasan;
    private widget.Button BtnPenilaianUlangNyeri;
    private widget.Button BtnPerencanaanPemulangan;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPermintaanResepPulang;
    private widget.Button BtnPermintaanStok;
    private widget.Button BtnPrint;
    private widget.Button BtnRekonsiliasiObat;
    private widget.Button BtnResepObat;
    private widget.Button BtnResume;
    private widget.Button BtnRiwayat;
    private widget.Button BtnRujukKeluar;
    private widget.Button BtnSKDP;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekPegawai;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSignInSebelumAnestesi;
    private widget.Button BtnSignOutSebelumMenutupLuka;
    private widget.Button BtnSimpan;
    private widget.Button BtnSkorAldrettePascaAnestesi;
    private widget.Button BtnSkorStewardPascaAnestesi;
    private widget.Button BtnSkriningGiziLanjut;
    private widget.Button BtnSkriningNutrisiAnak;
    private widget.Button BtnSkriningNutrisiDewasa;
    private widget.Button BtnSkriningNutrisiLansia;
    private widget.Button BtnTimeOutSebelumInsisi;
    private widget.Button BtnTransferAntarRuang;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkInput2;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Jabatan;
    private javax.swing.JTextField JmDokter;
    private javax.swing.JTextField JmPerawat;
    private javax.swing.JTextField KSO;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.TextBox KdPeg;
    private widget.Label LCount;
    private javax.swing.JTextField Menejemen;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput1;
    private javax.swing.JPanel PanelInput2;
    private javax.swing.JPanel PanelInput3;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox SpO2;
    private widget.TextBox TAdnexaKanan;
    private widget.TextBox TAdnexaKiri;
    private widget.TextBox TAlergi;
    private widget.TextBox TBentuk;
    private widget.TextBox TBerat;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TCavumDouglas;
    private widget.TextBox TCavumUteri;
    private widget.TextBox TDenominator;
    private widget.TextBox TDenyut;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextArea TEvaluasi;
    private widget.TextBox TGCS;
    private widget.TextBox TInspeksi;
    private widget.TextBox TInspeksiVulva;
    private widget.TextBox TInspekuloGine;
    private widget.TextArea TInstruksi;
    private widget.TextBox TKdPrw;
    private widget.TextBox TKdPrwDokterPetugas;
    private widget.TextBox TKdPrwPetugas;
    private widget.TextArea TKeluhan;
    private widget.TextBox TKualitas_dtk;
    private widget.TextBox TKualitas_mnt;
    private widget.TextBox TLetak;
    private widget.TextBox TNadi;
    private widget.TextBox TNmPrw;
    private widget.TextBox TNmPrwDokterPetugas;
    private widget.TextBox TNmPrwPetugas;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPegawai;
    private widget.TextBox TPembukaan;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TPenilaian;
    private widget.TextBox TPenurunan;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private widget.TextBox TPortio;
    private widget.TextBox TPortioDalam;
    private widget.TextBox TPortioInspekulo;
    private widget.TextBox TRespirasi;
    private widget.TextBox TSondage;
    private widget.TextBox TSuhu;
    private widget.TextBox TTebal;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private widget.TextBox TTinggi_uteri;
    private javax.swing.JTextField TTnd;
    private widget.TextBox TUkuran;
    private widget.TextBox TVulva;
    private widget.TextBox TVulvaInspekulo;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea TindakLanjut;
    private widget.Button btnPasien;
    private widget.Button btnTindakan;
    private widget.Button btnTindakan3;
    private widget.Button btnTindakan4;
    private widget.Button btnTindakan5;
    private widget.Button btnTindakan6;
    private widget.Button btnTindakan7;
    private widget.ComboBox cmbAlbus;
    private widget.ComboBox cmbArah;
    private widget.ComboBox cmbDalam;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbFeto;
    private widget.ComboBox cmbFluksus;
    private widget.ComboBox cmbFluorGine;
    private widget.ComboBox cmbFluxusGine;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJanin;
    private widget.ComboBox cmbKesadaran;
    private widget.ComboBox cmbKetuban;
    private widget.ComboBox cmbKontraksi;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMobilitas;
    private widget.ComboBox cmbNyeriTekan;
    private widget.ComboBox cmbPanggul;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
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
    private widget.Label jLabel4;
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
    private widget.Label jLabel5;
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
    private widget.Label jLabel64;
    private widget.Label jLabel67;
    private widget.Label jLabel7;
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
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg2;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaanGinekologi;
    private widget.Table tbPemeriksaanObstetri;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    private widget.TextBox TanggalRegistrasi;
    // End of variables declaration//GEN-END:variables
    private widget.Button BtnSkorBromagePascaAnestesi,BtnPenilaianPreInduksi,BtnHasilPemeriksaanUSGUrologi,BtnHasilPemeriksaanUSGGynecologi,BtnHasilPemeriksaanEKG,BtnHasilPemeriksaanUSGNeonatus,BtnHasilEndoskopiFaringLaring,BtnHasilEndoskopiHidung,BtnHasilEndoskopiTelinga,
                          BtnAwalKeperawatanNeonatus,BtnPenilaianPasienImunitasRendah,BtnCatatanKeseimbanganCairan,BtnCatatanObservasiCHBP,BtnCatatanObservasiInduksiPersalinan,BtnPermintaanKonsultasiMedik,BtnAwalKeperawatanBayiAnak,BtnCatatanObservasiRestrainNonfarmakologi,
                          BtnCatatanObservasiVentilator,BtnCatatanAnastesiSedasi,BtnChecklistPemberianFibrinolitik,BtnPenilaianPsikologKlinis,BtnAwalMedisNeonatus,BtnPenilaianDerajatDehidrasi,BtnHasilPemeriksaanECHO,BtnPenilaianBayiBaruLahir,BtnLaporanTindakan,
                          BtnPelaksanaanInformasiEdukasi,BtnCatatanObservasiHemodialisa,BtnCatatanCairanHemodialisa,BtnCatatanPengkajianPaskaOperasi,BtnCatatanObservasiBayi,BtnChecklistKesiapanAnestesi,BtnHasilPemeriksaanSlitLamp,BtnHasilPemeriksaanOCT;
    
    public void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps=koneksi.prepareStatement("select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_dr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat,rawat_inap_dr.kd_jenis_prw, " +
                   "rawat_inap_dr.tarif_tindakandr,rawat_inap_dr.kso,rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? order by rawat_inap_dr.no_rawat,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat desc");
            }else{
                ps=koneksi.prepareStatement("select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_dr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat,rawat_inap_dr.kd_jenis_prw, " +
                   "rawat_inap_dr.tarif_tindakandr,rawat_inap_dr.kso,rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                   "(rawat_inap_dr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan_inap.nm_perawatan like ? or rawat_inap_dr.kd_dokter like ? or dokter.nm_dokter like ?) "+
                   " order by rawat_inap_dr.no_rawat,rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat desc");
            }
            
            try {
                if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCariPasien.getText()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getDouble(9),rs.getString("kd_jenis_prw"),rs.getString("tarif_tindakandr"),rs.getString("kso"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
        LCount.setText(""+tabModeDr.getRowCount());
    }
    
    private void getDataDr() {
        if(tbRawatDr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString().substring(6,8));
            TNmPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());
            TKdPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),10).toString());
            Valid.SetTgl(DTPTgl,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7).toString());
            isJns();
        }
    }

    private void tampilPr() {
        Valid.tabelKosong(tabModePr);
        try{  
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps2=koneksi.prepareStatement("select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_pr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_pr.nip,petugas.nama,"+
                   "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat,rawat_inap_pr.kd_jenis_prw, " +
                   "rawat_inap_pr.tarif_tindakanpr,rawat_inap_pr.kso,rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                   "where rawat_inap_pr.tgl_perawatan between ? and ? order by rawat_inap_pr.no_rawat,rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat desc"); 
            }else{
                ps2=koneksi.prepareStatement("select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_pr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_pr.nip,petugas.nama,"+
                   "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat,rawat_inap_pr.kd_jenis_prw, " +
                   "rawat_inap_pr.tarif_tindakanpr,rawat_inap_pr.kso,rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                   "where rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                   "(rawat_inap_pr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan_inap.nm_perawatan like ? or rawat_inap_pr.nip like ? or petugas.nama like ?) "+
                   "order by rawat_inap_pr.no_rawat,rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat desc"); 
            }
                
            try{
                if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                    ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(3,"%"+TCariPasien.getText()+"%");
                    ps2.setString(4,"%"+TCari.getText().trim()+"%");
                    ps2.setString(5,"%"+TCari.getText().trim()+"%");
                    ps2.setString(6,"%"+TCari.getText().trim()+"%");
                    ps2.setString(7,"%"+TCari.getText().trim()+"%");
                    ps2.setString(8,"%"+TCari.getText().trim()+"%");
                    ps2.setString(9,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps2.executeQuery();
                while(rs.next()){
                    tabModePr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                         rs.getString(4),rs.getString(5),rs.getString(6),
                         rs.getString(7),rs.getString(8),rs.getDouble(9),
                         rs.getString("kd_jenis_prw"),rs.getString("tarif_tindakanpr"),
                         rs.getString("kso"),rs.getString("material"),
                         rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePr.getRowCount());
    }

    private void getDataPr() {
        if(tbRawatPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());   
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            cmbJam.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString().substring(6,8));
            TNmPrwPetugas.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());
            TKdPrwPetugas.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),10).toString());
            Valid.SetTgl(DTPTgl,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7).toString());
            isJns();
        }
    }
    
    public void tampilDrPr() {
        Valid.tabelKosong(tabModeDrPr);
        try{
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps3=koneksi.prepareStatement("select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_drpr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.biaya_rawat,rawat_inap_drpr.kd_jenis_prw, " +
                   "rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,rawat_inap_drpr.kso,rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                   "inner join petugas on rawat_inap_drpr.nip=petugas.nip "+
                   "where rawat_inap_drpr.tgl_perawatan between ? and ? order by rawat_inap_drpr.no_rawat,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat desc");
            }else{
                ps3=koneksi.prepareStatement("select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_drpr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.biaya_rawat,rawat_inap_drpr.kd_jenis_prw, " +
                   "rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,rawat_inap_drpr.kso,rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                   "inner join petugas on rawat_inap_drpr.nip=petugas.nip "+
                   "where rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                   "(rawat_inap_drpr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan_inap.nm_perawatan like ? or rawat_inap_drpr.kd_dokter like ? or dokter.nm_dokter like ? or "+
                   "rawat_inap_drpr.nip like ? or petugas.nama like ?) "+
                   " order by rawat_inap_drpr.no_rawat,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat desc");
            }
                
            try{
                if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                    ps3.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps3.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(3,"%"+TCariPasien.getText()+"%");
                    ps3.setString(4,"%"+TCari.getText().trim()+"%");
                    ps3.setString(5,"%"+TCari.getText().trim()+"%");
                    ps3.setString(6,"%"+TCari.getText().trim()+"%");
                    ps3.setString(7,"%"+TCari.getText().trim()+"%");
                    ps3.setString(8,"%"+TCari.getText().trim()+"%");
                    ps3.setString(9,"%"+TCari.getText().trim()+"%");
                    ps3.setString(10,"%"+TCari.getText().trim()+"%");
                    ps3.setString(11,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps3.executeQuery();
                while(rs.next()){
                    tabModeDrPr.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),rs.getString("kd_jenis_prw"),
                        rs.getString("tarif_tindakandr"),rs.getString("tarif_tindakanpr"),rs.getString("kso"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("menejemen")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }              
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeDrPr.getRowCount());
    }
    
    private void getDataDrPr() {
        if(tbRawatDrPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),3).toString());
            KdDok2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),5).toString());
            TDokter2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),6).toString());
            kdptg2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),7).toString());
            TPerawat2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),8).toString());
            cmbJam.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10).toString().substring(6,8));
            TNmPrwDokterPetugas.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),4).toString());
            TKdPrwDokterPetugas.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),12).toString());
            Valid.SetTgl(DTPTgl,tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9).toString());
            isJns();
        }
    }

    private void isJns(){
        if(TabRawat.getSelectedIndex()==0){
            if(!TKdPrw.getText().equals("")){
                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Bhp,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",BagianRS,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmDokter,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmPerawat,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",KSO,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Menejemen,TKdPrw.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrdr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TTnd,TKdPrw.getText());
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(!TKdPrwPetugas.getText().equals("")){
                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrwPetugas,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Bhp,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",BagianRS,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmDokter,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmPerawat,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",KSO,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Menejemen,TKdPrwPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TTnd,TKdPrwPetugas.getText());
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(!TKdPrwDokterPetugas.getText().equals("")){
                Sequel.cariIsi("select jns_perawatan_inap.nm_perawatan from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TNmPrwDokterPetugas,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.bhp from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Bhp,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.material from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",BagianRS,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakandr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmDokter,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.tarif_tindakanpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",JmPerawat,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.kso from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",KSO,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.menejemen from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",Menejemen,TKdPrwDokterPetugas.getText());
                Sequel.cariIsi("select jns_perawatan_inap.total_byrdrpr from jns_perawatan_inap where jns_perawatan_inap.kd_jenis_prw=? ",TTnd,TKdPrwDokterPetugas.getText());
            }
        }
    }    
    
    private void isRawat(){
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,concat(pasien.nm_pasien,' (',reg_periksa.umurdaftar,' ',reg_periksa.sttsumur,')') as pasien,reg_periksa.kd_dokter,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TCariPasien.setText(TNoRM.getText());
                    TPasien.setText(rs.getString("pasien"));
                    KdDok.setText(Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap where dpjp_ranap.no_rawat=?",TNoRw.getText()));
                    if(KdDok.getText().equals("")){
                        KdDok.setText(rs.getString("kd_dokter"));
                    }
                    TDokter.setText(perawatan.dokter.tampil3(KdDok.getText()));
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
    
    public void setNoRm(String norwt,Date awal,Date akhir) {
        TNoRw.setText(norwt);
        isRawat();
        KdDok2.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText()); 
        DTPCari1.setDate(awal);
        DTPCari2.setDate(akhir);
        TCari.setText(norwt);
        ChkInput.setSelected(true);
        isForm();
        ChkInput1.setSelected(true);
        isForm2(); 
        TabRawatMouseClicked(null);
    }
    
    public void setKamar(String kamar) {
        this.kamar=kamar;
    }
    
    public void setJenisBayar(String jenisbayar) {
        this.jenisbayar=jenisbayar;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH,273));
            panelGlass12.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput1.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass12.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        tinggi=0;
        BtnSimpan.setEnabled(akses.gettindakan_ranap());
        BtnHapus.setEnabled(akses.gettindakan_ranap());
        BtnEdit.setEnabled(akses.gettindakan_ranap());
        BtnPrint.setEnabled(akses.gettindakan_ranap());
        
        BtnDiagnosa.setVisible(akses.getdiagnosa_pasien());    
        if(akses.getdiagnosa_pasien()==true){
            tinggi=tinggi+24;
        }       
        BtnRiwayat.setVisible(akses.getresume_pasien()); 
        if(akses.getresume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnResepObat.setVisible(akses.getresep_dokter());
        BtnCopyResep.setVisible(akses.getresep_dokter());
        if(akses.getresep_dokter()==true){
            tinggi=tinggi+48;
        }
        BtnPermintaanStok.setVisible(akses.getpermintaan_stok_obat_pasien());
        if(akses.getpermintaan_stok_obat_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnObatBhp.setVisible(akses.getberi_obat());  
        BtnInputObat.setVisible(akses.getberi_obat());  
        if(akses.getberi_obat()==true){
            tinggi=tinggi+48;
        }
        BtnPermintaanLab.setVisible(akses.getpermintaan_lab());   
        if(akses.getpermintaan_lab()==true){
            tinggi=tinggi+24;
        }
        BtnBerkasDigital.setVisible(akses.getberkas_digital_perawatan()); 
        if(akses.getberkas_digital_perawatan()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanRad.setVisible(akses.getpermintaan_radiologi());  
        if(akses.getpermintaan_radiologi()==true){
            tinggi=tinggi+24;
        }
        BtnRujukKeluar.setVisible(akses.getrujukan_keluar());
        if(akses.getrujukan_keluar()==true){
            tinggi=tinggi+24;
        }
        BtnSKDP.setVisible(akses.getskdp_bpjs());   
        if(akses.getskdp_bpjs()==true){
            tinggi=tinggi+24;
        }
        BtnCatatan.setVisible(akses.getcatatan_pasien());  
        if(akses.getcatatan_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnResume.setVisible(akses.getdata_resume_pasien());
        if(akses.getdata_resume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if(akses.getasuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if(akses.getmonitoring_asuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnJadwalOperasi.setVisible(akses.getbooking_operasi());   
        if(akses.getbooking_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanKandungan.setVisible(akses.getpenilaian_awal_keperawatan_ranapkebidanan());   
        if(akses.getpenilaian_awal_keperawatan_ranapkebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanUmum.setVisible(akses.getpenilaian_awal_keperawatan_ranap());   
        if(akses.getpenilaian_awal_keperawatan_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanNeonatus.setVisible(akses.getpenilaian_awal_keperawatan_ranap_neonatus());   
        if(akses.getpenilaian_awal_keperawatan_ranap_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedis.setVisible(akses.getpenilaian_awal_medis_ranap());   
        if(akses.getpenilaian_awal_medis_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisNeonatus.setVisible(akses.getpenilaian_awal_medis_ranap_neonatus());   
        if(akses.getpenilaian_awal_medis_ranap_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisKandungan.setVisible(akses.getpenilaian_awal_medis_ranap_kebidanan());   
        if(akses.getpenilaian_awal_medis_ranap_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalFisioterapi.setVisible(akses.getpenilaian_fisioterapi());   
        if(akses.getpenilaian_fisioterapi()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanResepPulang.setVisible(akses.getpermintaan_resep_pulang());   
        if(akses.getpermintaan_resep_pulang()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanap.setVisible(akses.getcatatan_observasi_ranap());   
        if(akses.getcatatan_observasi_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanapKebidanan.setVisible(akses.getcatatan_observasi_ranap_kebidanan());   
        if(akses.getcatatan_observasi_ranap_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRanapPostPartum.setVisible(akses.getcatatan_observasi_ranap_postpartum());   
        if(akses.getcatatan_observasi_ranap_postpartum()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiRestrainNonfarmakologi.setVisible(akses.getcatatan_observasi_restrain_nonfarma());   
        if(akses.getcatatan_observasi_restrain_nonfarma()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiVentilator.setVisible(akses.getcatatan_observasi_ventilator());   
        if(akses.getcatatan_observasi_ventilator()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPsikolog.setVisible(akses.getpenilaian_psikologi()); 
        if(akses.getpenilaian_psikologi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPsikologKlinis.setVisible(akses.getpenilaian_psikologi_klinis()); 
        if(akses.getpenilaian_psikologi_klinis()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanKeperawatan.setVisible(akses.getcatatan_keperawatan_ranap()); 
        if(akses.getcatatan_keperawatan_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanPEWSAnak.setVisible(akses.getpemantauan_pews_anak()); 
        if(akses.getpemantauan_pews_anak()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreOperasi.setVisible(akses.getpenilaian_pre_operasi()); 
        if(akses.getpenilaian_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreAnestesi.setVisible(akses.getpenilaian_pre_anestesi()); 
        if(akses.getpenilaian_pre_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPerencanaanPemulangan.setVisible(akses.getperencanaan_pemulangan()); 
        if(akses.getperencanaan_pemulangan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhDewasa.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhAnak.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_anak()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_anak()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanGeriatri.setVisible(akses.getpenilaian_tambahan_pasien_geriatri()); 
        if(akses.getpenilaian_tambahan_pasien_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiDewasa.setVisible(akses.getskrining_nutrisi_dewasa()); 
        if(akses.getskrining_nutrisi_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiLansia.setVisible(akses.getskrining_nutrisi_lansia()); 
        if(akses.getskrining_nutrisi_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningNutrisiAnak.setVisible(akses.getskrining_nutrisi_anak()); 
        if(akses.getskrining_nutrisi_anak()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningGiziLanjut.setVisible(akses.getskrining_gizi()); 
        if(akses.getskrining_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSG.setVisible(akses.gethasil_pemeriksaan_usg()); 
        if(akses.gethasil_pemeriksaan_usg()==true){
            tinggi=tinggi+24;
        }
        BtnKonselingFarmasi.setVisible(akses.getkonseling_farmasi()); 
        if(akses.getkonseling_farmasi()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanPengkajianPaskaOperasi.setVisible(akses.getcatatan_pengkajian_paska_operasi()); 
        if(akses.getcatatan_pengkajian_paska_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnInformasiObat.setVisible(akses.getpelayanan_informasi_obat()); 
        if(akses.getpelayanan_informasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnTransferAntarRuang.setVisible(akses.gettransfer_pasien_antar_ruang()); 
        if(akses.gettransfer_pasien_antar_ruang()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanCekGDS.setVisible(akses.getcatatan_cek_gds()); 
        if(akses.getcatatan_cek_gds()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPreOperasi.setVisible(akses.getchecklist_pre_operasi()); 
        if(akses.getchecklist_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnSignInSebelumAnestesi.setVisible(akses.getsignin_sebelum_anestesi()); 
        if(akses.getsignin_sebelum_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnTimeOutSebelumInsisi.setVisible(akses.gettimeout_sebelum_insisi()); 
        if(akses.gettimeout_sebelum_insisi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanSlitLamp.setVisible(akses.gethasil_pemeriksaan_slit_lamp()); 
        if(akses.gethasil_pemeriksaan_slit_lamp()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanOCT.setVisible(akses.gethasil_pemeriksaan_oct()); 
        if(akses.gethasil_pemeriksaan_oct()==true){
            tinggi=tinggi+24;
        }
        BtnSignOutSebelumMenutupLuka.setVisible(akses.getsignout_sebelum_menutup_luka()); 
        if(akses.getsignout_sebelum_menutup_luka()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPostOperasi.setVisible(akses.getchecklist_post_operasi()); 
        if(akses.getchecklist_post_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnRekonsiliasiObat.setVisible(akses.getrekonsiliasi_obat()); 
        if(akses.getrekonsiliasi_obat()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienTerminal.setVisible(akses.getpenilaian_pasien_terminal()); 
        if(akses.getpenilaian_pasien_terminal()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringReaksiTranfusi.setVisible(akses.getmonitoring_reaksi_tranfusi()); 
        if(akses.getmonitoring_reaksi_tranfusi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianKorbanKekerasan.setVisible(akses.getpenilaian_korban_kekerasan()); 
        if(akses.getpenilaian_korban_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhLansia.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienPenyakitMenular.setVisible(akses.getpenilaian_pasien_penyakit_menular()); 
        if(akses.getpenilaian_pasien_penyakit_menular()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanPEWSDewasa.setVisible(akses.getpemantauan_pews_dewasa()); 
        if(akses.getpemantauan_pews_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanBunuhDiri.setVisible(akses.getpenilaian_tambahan_bunuh_diri()); 
        if(akses.getpenilaian_tambahan_bunuh_diri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanPerilakuKekerasan.setVisible(akses.getpenilaian_tambahan_perilaku_kekerasan()); 
        if(akses.getpenilaian_tambahan_perilaku_kekerasan()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianTambahanMelarikanDiri.setVisible(akses.getpenilaian_tambahan_beresiko_melarikan_diri()); 
        if(akses.getpenilaian_tambahan_beresiko_melarikan_diri()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanMEOWS.setVisible(akses.getpemantauan_meows_obstetri()); 
        if(akses.getpemantauan_meows_obstetri()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanADIMEGizi.setVisible(akses.getcatatan_adime_gizi()); 
        if(akses.getcatatan_adime_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukHCU.setVisible(akses.getchecklist_kriteria_masuk_hcu()); 
        if(akses.getchecklist_kriteria_masuk_hcu()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaKeluarHCU.setVisible(akses.getchecklist_kriteria_keluar_hcu()); 
        if(akses.getchecklist_kriteria_keluar_hcu()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianResikoDekubitus.setVisible(akses.getpenilaian_risiko_dekubitus()); 
        if(akses.getpenilaian_risiko_dekubitus()==true){
            tinggi=tinggi+24;
        }
        BtnDokumentasiESWL.setVisible(akses.gethasil_tindakan_eswl()); 
        if(akses.gethasil_tindakan_eswl()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukICU.setVisible(akses.getchecklist_kriteria_masuk_icu()); 
        if(akses.getchecklist_kriteria_masuk_icu()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaKeluarICU.setVisible(akses.getchecklist_kriteria_keluar_icu()); 
        if(akses.getchecklist_kriteria_keluar_icu()==true){
            tinggi=tinggi+24;
        }
        BtnFollowUpDBD.setVisible(akses.getfollow_up_dbd()); 
        if(akses.getfollow_up_dbd()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhNeonatus.setVisible(akses.getpenilaian_risiko_jatuh_neonatus()); 
        if(akses.getpenilaian_risiko_jatuh_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhGeriatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanEWSNeonatus.setVisible(akses.getpemantauan_ews_neonatus()); 
        if(akses.getpemantauan_ews_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisHemodialisa.setVisible(akses.getpenilaian_medis_ralan_hemodialisa()); 
        if(akses.getpenilaian_medis_ralan_hemodialisa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianKecemasanAnak.setVisible(akses.getpenilaian_level_kecemasan_ranap_anak()); 
        if(akses.getpenilaian_level_kecemasan_ranap_anak()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiBayi.setVisible(akses.getcatatan_observasi_bayi()); 
        if(akses.getcatatan_observasi_bayi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanResikoJatuhPsikiatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanSkriningFungsional.setVisible(akses.getpenilaian_lanjutan_skrining_fungsional()); 
        if(akses.getpenilaian_lanjutan_skrining_fungsional()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianUlangNyeri.setVisible(akses.getpenilaian_ulang_nyeri()); 
        if(akses.getpenilaian_ulang_nyeri()==true){
            tinggi=tinggi+24;
        }
        BtnPengkajianRestrain.setVisible(akses.getpengkajian_restrain()); 
        if(akses.getpengkajian_restrain()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianBayiBaruLahir.setVisible(akses.getpenilaian_bayi_baru_lahir()); 
        if(akses.getpenilaian_bayi_baru_lahir()==true){
            tinggi=tinggi+24;
        }
        BtnLaporanTindakan.setVisible(akses.getlaporan_tindakan());   
        if(akses.getlaporan_tindakan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanPersalinan.setVisible(akses.getcatatan_persalinan()); 
        if(akses.getcatatan_persalinan()==true){
            tinggi=tinggi+24;
        }
        BtnSkorAldrettePascaAnestesi.setVisible(akses.getskor_aldrette_pasca_anestesi()); 
        if(akses.getskor_aldrette_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnSkorStewardPascaAnestesi.setVisible(akses.getskor_steward_pasca_anestesi()); 
        if(akses.getskor_steward_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnSkorBromagePascaAnestesi.setVisible(akses.getskor_bromage_pasca_anestesi()); 
        if(akses.getskor_bromage_pasca_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreInduksi.setVisible(akses.getpenilaian_pre_induksi()); 
        if(akses.getpenilaian_pre_induksi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSGUrologi.setVisible(akses.gethasil_usg_urologi()); 
        if(akses.gethasil_usg_urologi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSGNeonatus.setVisible(akses.gethasil_usg_neonatus()); 
        if(akses.gethasil_usg_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanUSGGynecologi.setVisible(akses.gethasil_usg_gynecologi()); 
        if(akses.gethasil_usg_gynecologi()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanEKG.setVisible(akses.gethasil_pemeriksaan_ekg()); 
        if(akses.gethasil_pemeriksaan_ekg()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanECHO.setVisible(akses.gethasil_pemeriksaan_echo()); 
        if(akses.gethasil_pemeriksaan_echo()==true){
            tinggi=tinggi+24;
        }
        BtnHasilEndoskopiFaringLaring.setVisible(akses.gethasil_endoskopi_faring_laring()); 
        if(akses.gethasil_endoskopi_faring_laring()==true){
            tinggi=tinggi+24;
        }
        BtnHasilEndoskopiHidung.setVisible(akses.gethasil_endoskopi_hidung()); 
        if(akses.gethasil_endoskopi_hidung()==true){
            tinggi=tinggi+24;
        }
        BtnHasilEndoskopiTelinga.setVisible(akses.gethasil_endoskopi_telinga()); 
        if(akses.gethasil_endoskopi_telinga()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienImunitasRendah.setVisible(akses.getpenilaian_pasien_imunitas_rendah()); 
        if(akses.getpenilaian_pasien_imunitas_rendah()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanKeseimbanganCairan.setVisible(akses.getbalance_cairan()); 
        if(akses.getbalance_cairan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiCHBP.setVisible(akses.getcatatan_observasi_ranap_postpartum());   
        if(akses.getcatatan_observasi_chbp()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiInduksiPersalinan.setVisible(akses.getcatatan_observasi_induksi_persalinan());   
        if(akses.getcatatan_observasi_induksi_persalinan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiHemodialisa.setVisible(akses.getcatatan_observasi_hemodialisa());   
        if(akses.getcatatan_observasi_hemodialisa()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanCairanHemodialisa.setVisible(akses.getcatatan_cairan_hemodialisa());   
        if(akses.getcatatan_cairan_hemodialisa()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistPemberianFibrinolitik.setVisible(akses.getchecklist_pemberian_fibrinolitik());   
        if(akses.getchecklist_pemberian_fibrinolitik()==true){
            tinggi=tinggi+24;
        }
        BtnPermintaanKonsultasiMedik.setVisible(akses.getkonsultasi_medik());   
        if(akses.getkonsultasi_medik()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanBayiAnak.setVisible(akses.getpenilaian_awal_keperawatan_ranap_bayi());   
        if(akses.getpenilaian_awal_keperawatan_ranap_bayi()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanAnastesiSedasi.setVisible(akses.getcatatan_anestesi_sedasi());   
        if(akses.getcatatan_anestesi_sedasi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianDerajatDehidrasi.setVisible(akses.getpenilaian_derajat_dehidrasi());   
        if(akses.getpenilaian_derajat_dehidrasi()==true){
            tinggi=tinggi+24;
        }
        BtnPelaksanaanInformasiEdukasi.setVisible(akses.getpelaksanaan_informasi_edukasi());   
        if(akses.getpelaksanaan_informasi_edukasi()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKesiapanAnestesi.setVisible(akses.getchecklist_kesiapan_anestesi()); 
        if(akses.getchecklist_kesiapan_anestesi()==true){
            tinggi=tinggi+24;
        }
        FormMenu.setPreferredSize(new Dimension(195,(tinggi+10)));
        
        if(akses.getjml2()>=1){
            KdPeg.setText(akses.getkode());
            TPegawai.setText(pegawai.tampil3(KdPeg.getText()));
            Jabatan.setText(pegawai.tampilJbatan(KdPeg.getText()));
        }
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                DTPTgl.setEditable(false);
                DTPTgl.setEnabled(false);
                ChkJln.setEnabled(false);
                cmbJam.setEnabled(false);
                cmbMnt.setEnabled(false);
                cmbDtk.setEnabled(false);
            }
        }
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try{  
            ps4=koneksi.prepareStatement("select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                "pemeriksaan_ranap.berat,pemeriksaan_ranap.spo2,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.evaluasi,pemeriksaan_ranap.nip,pegawai.nama,pegawai.jbtn "+
                "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join pemeriksaan_ranap on pemeriksaan_ranap.no_rawat=reg_periksa.no_rawat "+
                "inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where "+
                "pemeriksaan_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                (TCari.getText().trim().equals("")?"":"and (pemeriksaan_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                "pemeriksaan_ranap.alergi like ? or pemeriksaan_ranap.keluhan like ? or pemeriksaan_ranap.penilaian like ? or "+
                "pemeriksaan_ranap.rtl like ? or pemeriksaan_ranap.pemeriksaan like ? or pegawai.nama like ?)")+
                "order by pemeriksaan_ranap.no_rawat,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat desc"); 
            try{
                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps4.setString(4,"%"+TCari.getText().trim()+"%");
                    ps4.setString(5,"%"+TCari.getText().trim()+"%");
                    ps4.setString(6,"%"+TCari.getText().trim()+"%");
                    ps4.setString(7,"%"+TCari.getText().trim()+"%");
                    ps4.setString(8,"%"+TCari.getText().trim()+"%");
                    ps4.setString(9,"%"+TCari.getText().trim()+"%");
                    ps4.setString(10,"%"+TCari.getText().trim()+"%");
                    ps4.setString(11,"%"+TCari.getText().trim()+"%");
                    ps4.setString(12,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps4.executeQuery();
                while(rs.next()){
                    tabModePemeriksaan.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
                        rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15),
                        rs.getString(16),rs.getString(17),rs.getString(18),rs.getString(19),
                        rs.getString(20),rs.getString(21),rs.getString(22),rs.getString(23),
                        rs.getString(24)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps4!=null){
                    ps4.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePemeriksaan.getRowCount());
    }

    private void getDataPemeriksaan() {
        if(tbPemeriksaan.getSelectedRow()!= -1){
            TNoRw.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),3).toString());             
            TSuhu.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),6).toString()); 
            TTensi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),7).toString()); 
            TNadi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),8).toString()); 
            TRespirasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),9).toString()); 
            TTinggi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),10).toString()); 
            TBerat.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),11).toString());  
            SpO2.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),12).toString());  
            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),13).toString()); 
            cmbKesadaran.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),14).toString()); 
            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),15).toString()); 
            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),16).toString()); 
            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),17).toString()); 
            TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),18).toString()); 
            TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),19).toString()); 
            TInstruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),20).toString());  
            TEvaluasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),21).toString()); 
            cmbJam.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString());
        }
    }
    
    private void tampilPemeriksaanObstetri() {
        Valid.tabelKosong(tabModeObstetri);
        try{
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps5=koneksi.prepareStatement("select pemeriksaan_obstetri_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                    "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                    "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                    "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                    "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_obstetri_ranap on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat "+
                    "where pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? order by pemeriksaan_obstetri_ranap.no_rawat,pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat desc");
            }else{
                ps5=koneksi.prepareStatement("select pemeriksaan_obstetri_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                    "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                    "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                    "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                    "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_obstetri_ranap on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat "+
                    "where pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                    "(pemeriksaan_obstetri_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or pemeriksaan_obstetri_ranap.tinggi_uteri like ? or pemeriksaan_obstetri_ranap.janin like ? or pemeriksaan_obstetri_ranap.letak like ?) "+
                    "order by pemeriksaan_obstetri_ranap.no_rawat,pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat desc");
            }
                
            try {
                if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                    ps5.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps5.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(3,"%"+TCariPasien.getText()+"%");
                    ps5.setString(4,"%"+TCari.getText().trim()+"%");
                    ps5.setString(5,"%"+TCari.getText().trim()+"%");
                    ps5.setString(6,"%"+TCari.getText().trim()+"%");
                    ps5.setString(7,"%"+TCari.getText().trim()+"%");
                    ps5.setString(8,"%"+TCari.getText().trim()+"%");
                    ps5.setString(9,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps5.executeQuery();
                while(rs.next()) {
                    tabModeObstetri.addRow(new Object[] {
                        false, rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("tinggi_uteri"),
                        rs.getString("janin"),rs.getString("letak"),rs.getString("panggul"),
                        rs.getString("denyut"),rs.getString("kontraksi"),rs.getString("kualitas_mnt"),
                        rs.getString("kualitas_dtk"),rs.getString("fluksus"),rs.getString("albus"),
                        rs.getString("vulva"),rs.getString("portio"),rs.getString("dalam"),
                        rs.getString("tebal"),rs.getString("arah"),rs.getString("pembukaan"),
                        rs.getString("penurunan"),rs.getString("denominator"),rs.getString("ketuban"),
                        rs.getString("feto")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);    
            } finally {
                if(rs!=null) {
                    rs.close();
                }
                if(ps5!=null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" +e);
        }
        LCount.setText(""+tabModeObstetri.getRowCount());
    }     
        
    private void getDataPemeriksaanObstetri() {
        if(tbPemeriksaanObstetri.getSelectedRow()!= -1) {
            TNoRw.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),3).toString());
            Valid.SetTgl(DTPTgl,tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5).toString().substring(6,8));
            TTinggi_uteri.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),6).toString());
            cmbJanin.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),7).toString());
            TLetak.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),8).toString());
            cmbPanggul.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),9).toString());
            TDenyut.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),10).toString());
            cmbKontraksi.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),11).toString());
            TKualitas_mnt.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),12).toString());
            TKualitas_dtk.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),13).toString());
            cmbFluksus.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),14).toString());
            cmbAlbus.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),15).toString());
            TVulva.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),16).toString());
            TPortio.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),17).toString());
            cmbDalam.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),18).toString());
            TTebal.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),19).toString());
            cmbArah.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),20).toString());
            TPembukaan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),21).toString());
            TPenurunan.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),22).toString());
            TDenominator.setText(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),23).toString());
            cmbKetuban.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),24).toString());
            cmbFeto.setSelectedItem(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),25).toString());
        }
    }
    
    private void isForm2(){
        if(ChkInput1.isSelected()==true){
            ChkInput1.setVisible(false);
            PanelInput2.setPreferredSize(new Dimension(WIDTH,156));
            panelGlass14.setVisible(true);      
            ChkInput1.setVisible(true);
        }else if(ChkInput1.isSelected()==false){           
            ChkInput1.setVisible(false);            
            PanelInput2.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass14.setVisible(false);      
            ChkInput1.setVisible(true);
        }
    }
    
    private void tampilPemeriksaanGinekologi() {
        Valid.tabelKosong(tabModeGinekologi);
        try{
            ps6=koneksi.prepareStatement("select pemeriksaan_ginekologi_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, " +
                    "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, " +
                    "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, " +
                    "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri," +
                    "pemeriksaan_ginekologi_ranap.cavum_douglas " +
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_ginekologi_ranap on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat "+
                    "where pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                    "(pemeriksaan_ginekologi_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or pemeriksaan_ginekologi_ranap.inspeksi like ? or pemeriksaan_ginekologi_ranap.inspeksi_vulva like ? or pemeriksaan_ginekologi_ranap.inspekulo_gine like ?) "+
                    "order by pemeriksaan_ginekologi_ranap.no_rawat,pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat desc");
            try {
                ps6.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(3,"%"+TCariPasien.getText()+"%");
                ps6.setString(4,"%"+TCari.getText().trim()+"%");
                ps6.setString(5,"%"+TCari.getText().trim()+"%");
                ps6.setString(6,"%"+TCari.getText().trim()+"%");
                ps6.setString(7,"%"+TCari.getText().trim()+"%");
                ps6.setString(8,"%"+TCari.getText().trim()+"%");
                ps6.setString(9,"%"+TCari.getText().trim()+"%");
                
                rs=ps6.executeQuery();
                while(rs.next()) {
                    tabModeGinekologi.addRow(new Object[] {
                        false, rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tgl_perawatan"),rs.getString("jam_rawat"),rs.getString("inspeksi"),
                        rs.getString("inspeksi_vulva"),rs.getString("inspekulo_gine"),rs.getString("fluxus_gine"),
                        rs.getString("fluor_gine"),rs.getString("vulva_inspekulo"),rs.getString("portio_inspekulo"),
                        rs.getString("sondage"),rs.getString("portio_dalam"),rs.getString("bentuk"),
                        rs.getString("cavum_uteri"),rs.getString("mobilitas"),rs.getString("ukuran"),
                        rs.getString("nyeri_tekan"),rs.getString("adnexa_kanan"),rs.getString("adnexa_kiri"),
                        rs.getString("cavum_douglas")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);    
            } finally {
                if(rs!=null) {
                    rs.close();
                }
                if(ps5!=null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi :" +e);
        }
        LCount.setText(""+tabModeGinekologi.getRowCount());
    }
    
    private void getDataPemeriksaanGinekologi() {
         if(tbPemeriksaanGinekologi.getSelectedRow()!= -1) {
            TNoRw.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),3).toString());
            Valid.SetTgl(DTPTgl,tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4).toString());
            cmbJam.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5).toString().substring(6,8));
            TInspeksi.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),6).toString());
            TInspeksiVulva.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),7).toString());
            TInspekuloGine.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),8).toString());
            cmbFluxusGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),9).toString());
            cmbFluorGine.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),10).toString());
            TVulvaInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),11).toString());
            TPortioInspekulo.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),12).toString());
            TSondage.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),13).toString());
            TPortioDalam.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),14).toString());
            TBentuk.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),15).toString());
            TCavumUteri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),16).toString());
            cmbMobilitas.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),17).toString());
            TUkuran.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),18).toString());
            cmbNyeriTekan.setSelectedItem(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),19).toString());
            TAdnexaKanan.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),20).toString());
            TAdnexaKiri.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),21).toString());
            TCavumDouglas.setText(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),22).toString());
        }
    }
    
    private void isForm3(){
        if(ChkInput2.isSelected()==true){
            ChkInput2.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH,246));
            panelGlass15.setVisible(true);      
            ChkInput2.setVisible(true);
        }else if(ChkInput2.isSelected()==false){           
            ChkInput2.setVisible(false);            
            PanelInput3.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass15.setVisible(false);      
            ChkInput2.setVisible(true);
        }
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(205,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
    
    private void initRawatInap(){
        BtnSkorBromagePascaAnestesi = new widget.Button();
        BtnSkorBromagePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkorBromagePascaAnestesi.setText("Skor Bromage Pasca Anestesi");
        BtnSkorBromagePascaAnestesi.setFocusPainted(false);
        BtnSkorBromagePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkorBromagePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorBromagePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorBromagePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorBromagePascaAnestesi.setName("BtnSkorBromagePascaAnestesi"); 
        BtnSkorBromagePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorBromagePascaAnestesi.setRoundRect(false);
        BtnSkorBromagePascaAnestesi.addActionListener(this::BtnSkorBromagePascaAnestesiActionPerformed);
        
        BtnPenilaianPreInduksi = new widget.Button();
        BtnPenilaianPreInduksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPreInduksi.setText("Pengkajian Pre Induksi");
        BtnPenilaianPreInduksi.setFocusPainted(false);
        BtnPenilaianPreInduksi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPreInduksi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreInduksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreInduksi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreInduksi.setName("Pengkajian Pre Induksi"); 
        BtnPenilaianPreInduksi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreInduksi.setRoundRect(false);
        BtnPenilaianPreInduksi.addActionListener(this::BtnPenilaianPreInduksiActionPerformed);
        
        BtnHasilPemeriksaanUSGUrologi = new widget.Button();
        BtnHasilPemeriksaanUSGUrologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGUrologi.setText("Hasil USG Urologi");
        BtnHasilPemeriksaanUSGUrologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGUrologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGUrologi.setName("BtnHasilPemeriksaanUSGUrologi"); // NOI18N
        BtnHasilPemeriksaanUSGUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGUrologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGUrologi.addActionListener(this::BtnHasilPemeriksaanUSGUrologiActionPerformed);
        
        BtnHasilPemeriksaanUSGGynecologi = new widget.Button();
        BtnHasilPemeriksaanUSGGynecologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGGynecologi.setText("Hasil USG Gynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGGynecologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGGynecologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGGynecologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGGynecologi.setName("BtnHasilPemeriksaanUSGGynecologi"); // NOI18N
        BtnHasilPemeriksaanUSGGynecologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGGynecologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGGynecologi.addActionListener(this::BtnHasilPemeriksaanUSGGynecologiActionPerformed);
        
        BtnHasilPemeriksaanUSGNeonatus = new widget.Button();
        BtnHasilPemeriksaanUSGNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGNeonatus.setText("Hasil USG Neonatus");
        BtnHasilPemeriksaanUSGNeonatus.setFocusPainted(false);
        BtnHasilPemeriksaanUSGNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGNeonatus.setName("BtnHasilPemeriksaanUSGNeonatus");
        BtnHasilPemeriksaanUSGNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGNeonatus.setRoundRect(false);
        BtnHasilPemeriksaanUSGNeonatus.addActionListener(this::BtnHasilPemeriksaanUSGNeonatusActionPerformed);
        
        BtnHasilPemeriksaanEKG = new widget.Button();
        BtnHasilPemeriksaanEKG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanEKG.setText("Hasil Pemeriksaan EKG");
        BtnHasilPemeriksaanEKG.setFocusPainted(false);
        BtnHasilPemeriksaanEKG.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnHasilPemeriksaanEKG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanEKG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanEKG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanEKG.setName("BtnHasilPemeriksaanEKG"); // NOI18N
        BtnHasilPemeriksaanEKG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanEKG.setRoundRect(false);
        BtnHasilPemeriksaanEKG.addActionListener(this::BtnHasilPemeriksaanEKGActionPerformed);
        
        BtnHasilPemeriksaanECHO = new widget.Button();
        BtnHasilPemeriksaanECHO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanECHO.setText("Hasil Pemeriksaan ECHO");
        BtnHasilPemeriksaanECHO.setFocusPainted(false);
        BtnHasilPemeriksaanECHO.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanECHO.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanECHO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanECHO.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanECHO.setName("BtnHasilPemeriksaanECHO");
        BtnHasilPemeriksaanECHO.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanECHO.setRoundRect(false);
        BtnHasilPemeriksaanECHO.addActionListener(this::BtnHasilPemeriksaanECHOActionPerformed);
        
        BtnHasilEndoskopiFaringLaring = new widget.Button();
        BtnHasilEndoskopiFaringLaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilEndoskopiFaringLaring.setText("Hasil Endoskopi Faring/Laring");
        BtnHasilEndoskopiFaringLaring.setFocusPainted(false);
        BtnHasilEndoskopiFaringLaring.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilEndoskopiFaringLaring.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEndoskopiFaringLaring.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEndoskopiFaringLaring.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEndoskopiFaringLaring.setName("BtnHasilEndoskopiFaringLaring");
        BtnHasilEndoskopiFaringLaring.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEndoskopiFaringLaring.setRoundRect(false);
        BtnHasilEndoskopiFaringLaring.addActionListener(this::BtnHasilEndoskopiFaringLaringActionPerformed);
        
        BtnHasilEndoskopiHidung = new widget.Button();
        BtnHasilEndoskopiHidung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilEndoskopiHidung.setText("Hasil Endoskopi Hidung");
        BtnHasilEndoskopiHidung.setFocusPainted(false);
        BtnHasilEndoskopiHidung.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilEndoskopiHidung.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEndoskopiHidung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEndoskopiHidung.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEndoskopiHidung.setName("BtnHasilEndoskopiHidung");
        BtnHasilEndoskopiHidung.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEndoskopiHidung.setRoundRect(false);
        BtnHasilEndoskopiHidung.addActionListener(this::BtnHasilEndoskopiHidungActionPerformed);
        
        BtnHasilEndoskopiTelinga = new widget.Button();
        BtnHasilEndoskopiTelinga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilEndoskopiTelinga.setText("Hasil Endoskopi Telinga");
        BtnHasilEndoskopiTelinga.setFocusPainted(false);
        BtnHasilEndoskopiTelinga.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilEndoskopiTelinga.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilEndoskopiTelinga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilEndoskopiTelinga.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilEndoskopiTelinga.setName("BtnHasilEndoskopiTelinga");
        BtnHasilEndoskopiTelinga.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilEndoskopiTelinga.setRoundRect(false);
        BtnHasilEndoskopiTelinga.addActionListener(this::BtnHasilEndoskopiTelingaActionPerformed);
        
        BtnAwalKeperawatanNeonatus = new widget.Button();
        BtnAwalKeperawatanNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanNeonatus.setText("Awal Keperawatan Neonatus");
        BtnAwalKeperawatanNeonatus.setFocusPainted(false);
        BtnAwalKeperawatanNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnAwalKeperawatanNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanNeonatus.setName("BtnAwalKeperawatanNeonatus");
        BtnAwalKeperawatanNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanNeonatus.setRoundRect(false);
        BtnAwalKeperawatanNeonatus.addActionListener(this::BtnAwalKeperawatanNeonatusActionPerformed);
        
        BtnAwalKeperawatanBayiAnak = new widget.Button();
        BtnAwalKeperawatanBayiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanBayiAnak.setText("Awal Keperawatan Bayi/Anak");
        BtnAwalKeperawatanBayiAnak.setFocusPainted(false);
        BtnAwalKeperawatanBayiAnak.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnAwalKeperawatanBayiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanBayiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanBayiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanBayiAnak.setName("BtnAwalKeperawatanBayiAnak");
        BtnAwalKeperawatanBayiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanBayiAnak.setRoundRect(false);
        BtnAwalKeperawatanBayiAnak.addActionListener(this::BtnAwalKeperawatanBayiAnakActionPerformed);
        
        BtnPenilaianPasienImunitasRendah = new widget.Button();
        BtnPenilaianPasienImunitasRendah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPasienImunitasRendah.setText("Pasien Imunitas Rendah");
        BtnPenilaianPasienImunitasRendah.setFocusPainted(false);
        BtnPenilaianPasienImunitasRendah.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPasienImunitasRendah.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienImunitasRendah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienImunitasRendah.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienImunitasRendah.setName("BtnPenilaianPasienImunitasRendah"); 
        BtnPenilaianPasienImunitasRendah.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienImunitasRendah.setRoundRect(false);
        BtnPenilaianPasienImunitasRendah.addActionListener(this::BtnPenilaianPasienImunitasRendahActionPerformed);
        
        BtnCatatanKeseimbanganCairan = new widget.Button();
        BtnCatatanKeseimbanganCairan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanKeseimbanganCairan.setText("Keseimbangan Cairan");
        BtnCatatanKeseimbanganCairan.setFocusPainted(false);
        BtnCatatanKeseimbanganCairan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanKeseimbanganCairan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanKeseimbanganCairan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanKeseimbanganCairan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanKeseimbanganCairan.setName("BtnCatatanKeseimbanganCairan"); 
        BtnCatatanKeseimbanganCairan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanKeseimbanganCairan.setRoundRect(false);
        BtnCatatanKeseimbanganCairan.addActionListener(this::BtnCatatanKeseimbanganCairanActionPerformed);
        
        BtnCatatanObservasiCHBP = new widget.Button();
        BtnCatatanObservasiCHBP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiCHBP.setText("Observasi CHBP");
        BtnCatatanObservasiCHBP.setFocusPainted(false);
        BtnCatatanObservasiCHBP.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiCHBP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiCHBP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiCHBP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiCHBP.setName("BtnCatatanObservasiCHBP"); 
        BtnCatatanObservasiCHBP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiCHBP.setRoundRect(false);
        BtnCatatanObservasiCHBP.addActionListener(this::BtnCatatanObservasiCHBPActionPerformed);
        
        BtnCatatanPengkajianPaskaOperasi = new widget.Button();
        BtnCatatanPengkajianPaskaOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanPengkajianPaskaOperasi.setText("Pengkajian Paska Operasi");
        BtnCatatanPengkajianPaskaOperasi.setFocusPainted(false);
        BtnCatatanPengkajianPaskaOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanPengkajianPaskaOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanPengkajianPaskaOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanPengkajianPaskaOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanPengkajianPaskaOperasi.setName("BtnCatatanPengkajianPaskaOperasi"); 
        BtnCatatanPengkajianPaskaOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanPengkajianPaskaOperasi.setRoundRect(false);
        BtnCatatanPengkajianPaskaOperasi.addActionListener(this::BtnCatatanPengkajianPaskaOperasiActionPerformed);
        
        BtnCatatanObservasiInduksiPersalinan = new widget.Button();
        BtnCatatanObservasiInduksiPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiInduksiPersalinan.setText("Observasi Induksi Persalinan");
        BtnCatatanObservasiInduksiPersalinan.setFocusPainted(false);
        BtnCatatanObservasiInduksiPersalinan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiInduksiPersalinan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiInduksiPersalinan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiInduksiPersalinan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiInduksiPersalinan.setName("BtnCatatanObservasiInduksiPersalinan"); 
        BtnCatatanObservasiInduksiPersalinan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiInduksiPersalinan.setRoundRect(false);
        BtnCatatanObservasiInduksiPersalinan.addActionListener(this::BtnCatatanObservasiInduksiPersalinanActionPerformed);
        
        BtnCatatanObservasiHemodialisa = new widget.Button();
        BtnCatatanObservasiHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiHemodialisa.setText("Observasi Hemodialisa");
        BtnCatatanObservasiHemodialisa.setFocusPainted(false);
        BtnCatatanObservasiHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiHemodialisa.setName("BtnCatatanObservasiHemodialisa"); 
        BtnCatatanObservasiHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiHemodialisa.setRoundRect(false);
        BtnCatatanObservasiHemodialisa.addActionListener(this::BtnCatatanObservasiHemodialisaActionPerformed);
        
        BtnCatatanCairanHemodialisa = new widget.Button();
        BtnCatatanCairanHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanCairanHemodialisa.setText("Cairan Hemodialisa");
        BtnCatatanCairanHemodialisa.setFocusPainted(false);
        BtnCatatanCairanHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanCairanHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCairanHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCairanHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCairanHemodialisa.setName("BtnCatatanCairanHemodialisa"); 
        BtnCatatanCairanHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCairanHemodialisa.setRoundRect(false);
        BtnCatatanCairanHemodialisa.addActionListener(this::BtnCatatanCairanHemodialisaActionPerformed);
        
        BtnChecklistPemberianFibrinolitik = new widget.Button();
        BtnChecklistPemberianFibrinolitik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistPemberianFibrinolitik.setText("Check List Pemberian Fibrinoli");
        BtnChecklistPemberianFibrinolitik.setFocusPainted(false);
        BtnChecklistPemberianFibrinolitik.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistPemberianFibrinolitik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPemberianFibrinolitik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPemberianFibrinolitik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPemberianFibrinolitik.setName("BtnChecklistPemberianFibrinolitik"); 
        BtnChecklistPemberianFibrinolitik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPemberianFibrinolitik.setRoundRect(false);
        BtnChecklistPemberianFibrinolitik.addActionListener(this::BtnChecklistPemberianFibrinolitikActionPerformed);
        
        BtnCatatanObservasiRestrainNonfarmakologi = new widget.Button();
        BtnCatatanObservasiRestrainNonfarmakologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiRestrainNonfarmakologi.setText("Observasi Restrain Nonfarma");
        BtnCatatanObservasiRestrainNonfarmakologi.setFocusPainted(false);
        BtnCatatanObservasiRestrainNonfarmakologi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiRestrainNonfarmakologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiRestrainNonfarmakologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiRestrainNonfarmakologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiRestrainNonfarmakologi.setName("BtnCatatanObservasiRestrainNonfarmakologi"); 
        BtnCatatanObservasiRestrainNonfarmakologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiRestrainNonfarmakologi.setRoundRect(false);
        BtnCatatanObservasiRestrainNonfarmakologi.addActionListener(this::BtnCatatanObservasiRestrainNonfarmakologiActionPerformed);
        
        BtnCatatanObservasiVentilator = new widget.Button();
        BtnCatatanObservasiVentilator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiVentilator.setText("Observasi Ventilator");
        BtnCatatanObservasiVentilator.setFocusPainted(false);
        BtnCatatanObservasiVentilator.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiVentilator.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiVentilator.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiVentilator.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiVentilator.setName("BtnCatatanObservasiVentilator"); 
        BtnCatatanObservasiVentilator.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiVentilator.setRoundRect(false);
        BtnCatatanObservasiVentilator.addActionListener(this::BtnCatatanObservasiVentilatorActionPerformed);
        
        BtnPermintaanKonsultasiMedik = new widget.Button();
        BtnPermintaanKonsultasiMedik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPermintaanKonsultasiMedik.setText("Konsultasi Medik");
        BtnPermintaanKonsultasiMedik.setFocusPainted(false);
        BtnPermintaanKonsultasiMedik.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPermintaanKonsultasiMedik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanKonsultasiMedik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanKonsultasiMedik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanKonsultasiMedik.setName("BtnPermintaanKonsultasiMedik"); 
        BtnPermintaanKonsultasiMedik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanKonsultasiMedik.setRoundRect(false);
        BtnPermintaanKonsultasiMedik.addActionListener(this::BtnPermintaanKonsultasiMedikActionPerformed);
        
        BtnAwalMedisNeonatus = new widget.Button();
        BtnAwalMedisNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisNeonatus.setText("Awal Medis Neonatus");
        BtnAwalMedisNeonatus.setFocusPainted(false);
        BtnAwalMedisNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisNeonatus.setName("BtnAwalMedisNeonatus"); 
        BtnAwalMedisNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisNeonatus.setRoundRect(false);
        BtnAwalMedisNeonatus.addActionListener(this::BtnAwalMedisNeonatusActionPerformed);
        
        BtnPenilaianPsikologKlinis = new widget.Button();
        BtnPenilaianPsikologKlinis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPsikologKlinis.setText("Pengkajian Psikologi Klinis");
        BtnPenilaianPsikologKlinis.setFocusPainted(false);
        BtnPenilaianPsikologKlinis.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPsikologKlinis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikologKlinis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikologKlinis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikologKlinis.setName("BtnPenilaianPsikologKlinis"); 
        BtnPenilaianPsikologKlinis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikologKlinis.setRoundRect(false);
        BtnPenilaianPsikologKlinis.addActionListener(this::BtnPenilaianPsikologKlinisActionPerformed);
        
        BtnPenilaianDerajatDehidrasi = new widget.Button();
        BtnPenilaianDerajatDehidrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianDerajatDehidrasi.setText("Pengkajian Derajat Dehidrasi");
        BtnPenilaianDerajatDehidrasi.setFocusPainted(false);
        BtnPenilaianDerajatDehidrasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianDerajatDehidrasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianDerajatDehidrasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianDerajatDehidrasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianDerajatDehidrasi.setName("BtnPenilaianDerajatDehidrasi"); 
        BtnPenilaianDerajatDehidrasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianDerajatDehidrasi.setRoundRect(false);
        BtnPenilaianDerajatDehidrasi.addActionListener(this::BtnPenilaianDerajatDehidrasiActionPerformed);
        
        BtnCatatanAnastesiSedasi = new widget.Button();
        BtnCatatanAnastesiSedasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanAnastesiSedasi.setText("Catatan Anestesi-Sedasi");
        BtnCatatanAnastesiSedasi.setFocusPainted(false);
        BtnCatatanAnastesiSedasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanAnastesiSedasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanAnastesiSedasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanAnastesiSedasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanAnastesiSedasi.setName("BtnCatatanAnastesiSedasi"); 
        BtnCatatanAnastesiSedasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanAnastesiSedasi.setRoundRect(false);
        BtnCatatanAnastesiSedasi.addActionListener(this::BtnCatatanAnastesiSedasiActionPerformed);
        
        BtnPenilaianBayiBaruLahir = new widget.Button();
        BtnPenilaianBayiBaruLahir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianBayiBaruLahir.setText("Pengkajian Bayi Baru Lahir");
        BtnPenilaianBayiBaruLahir.setFocusPainted(false);
        BtnPenilaianBayiBaruLahir.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianBayiBaruLahir.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianBayiBaruLahir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianBayiBaruLahir.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianBayiBaruLahir.setName("BtnPenilaianBayiBaruLahir"); 
        BtnPenilaianBayiBaruLahir.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianBayiBaruLahir.setRoundRect(false);
        BtnPenilaianBayiBaruLahir.addActionListener(this::BtnPenilaianBayiBaruLahirActionPerformed);
        
        BtnLaporanTindakan = new widget.Button();
        BtnLaporanTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnLaporanTindakan.setText("Laporan Tindakan");
        BtnLaporanTindakan.setFocusPainted(false);
        BtnLaporanTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnLaporanTindakan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnLaporanTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnLaporanTindakan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnLaporanTindakan.setName("BtnLaporanTindakan"); 
        BtnLaporanTindakan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnLaporanTindakan.setRoundRect(false);
        BtnLaporanTindakan.addActionListener(this::BtnLaporanTindakanActionPerformed);
        
        BtnPelaksanaanInformasiEdukasi = new widget.Button();
        BtnPelaksanaanInformasiEdukasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPelaksanaanInformasiEdukasi.setText("Pelaksanaan Edukasi");
        BtnPelaksanaanInformasiEdukasi.setFocusPainted(false);
        BtnPelaksanaanInformasiEdukasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPelaksanaanInformasiEdukasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPelaksanaanInformasiEdukasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPelaksanaanInformasiEdukasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPelaksanaanInformasiEdukasi.setName("BtnPelaksanaanInformasiEdukasi"); 
        BtnPelaksanaanInformasiEdukasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPelaksanaanInformasiEdukasi.setRoundRect(false);
        BtnPelaksanaanInformasiEdukasi.addActionListener(this::BtnPelaksanaanInformasiEdukasiActionPerformed);
        
        BtnCatatanObservasiBayi = new widget.Button();
        BtnCatatanObservasiBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiBayi.setText("Observasi Bayi");
        BtnCatatanObservasiBayi.setFocusPainted(false);
        BtnCatatanObservasiBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiBayi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiBayi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiBayi.setName("BtnCatatanObservasiBayi"); 
        BtnCatatanObservasiBayi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiBayi.setRoundRect(false);
        BtnCatatanObservasiBayi.addActionListener(this::BtnCatatanObservasiBayiActionPerformed);
        
        BtnChecklistKesiapanAnestesi = new widget.Button();
        BtnChecklistKesiapanAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistKesiapanAnestesi.setText("Check List Kesiapan Anestesi");
        BtnChecklistKesiapanAnestesi.setFocusPainted(false);
        BtnChecklistKesiapanAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistKesiapanAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKesiapanAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKesiapanAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKesiapanAnestesi.setName("BtnChecklistKesiapanAnestesi"); 
        BtnChecklistKesiapanAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKesiapanAnestesi.setRoundRect(false);
        BtnChecklistKesiapanAnestesi.addActionListener(this::BtnChecklistKesiapanAnestesiActionPerformed);
        
        BtnHasilPemeriksaanSlitLamp = new widget.Button();
        BtnHasilPemeriksaanSlitLamp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanSlitLamp.setText("Hasil Pemeriksaan Slit Lamp");
        BtnHasilPemeriksaanSlitLamp.setFocusPainted(false);
        BtnHasilPemeriksaanSlitLamp.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanSlitLamp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanSlitLamp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanSlitLamp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanSlitLamp.setName("BtnHasilPemeriksaanSlitLamp");
        BtnHasilPemeriksaanSlitLamp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanSlitLamp.setRoundRect(false);
        BtnHasilPemeriksaanSlitLamp.addActionListener(this::BtnHasilPemeriksaanSlitLampActionPerformed);
        
        BtnHasilPemeriksaanOCT = new widget.Button();
        BtnHasilPemeriksaanOCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanOCT.setText("Hasil Pemeriksaan OCT");
        BtnHasilPemeriksaanOCT.setFocusPainted(false);
        BtnHasilPemeriksaanOCT.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanOCT.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanOCT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanOCT.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanOCT.setName("BtnHasilPemeriksaanOCT");
        BtnHasilPemeriksaanOCT.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanOCT.setRoundRect(false);
        BtnHasilPemeriksaanOCT.addActionListener(this::BtnHasilPemeriksaanOCTActionPerformed);
        
        TanggalRegistrasi = new widget.TextBox();
        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi");
        
        FormMenu.add(BtnRiwayat);
        FormMenu.add(BtnResepObat);
        FormMenu.add(BtnCopyResep);
        FormMenu.add(BtnPermintaanStok);
        FormMenu.add(BtnPermintaanResepPulang);
        FormMenu.add(BtnInputObat);
        FormMenu.add(BtnObatBhp);
        FormMenu.add(BtnBerkasDigital);
        FormMenu.add(BtnPermintaanLab);
        FormMenu.add(BtnPermintaanRad);
        FormMenu.add(BtnPermintaanKonsultasiMedik);
        FormMenu.add(BtnJadwalOperasi);
        FormMenu.add(BtnSKDP);
        FormMenu.add(BtnRujukKeluar);
        FormMenu.add(BtnDiagnosa);
        FormMenu.add(BtnResume);
        FormMenu.add(BtnAwalKeperawatanUmum);
        FormMenu.add(BtnAwalKeperawatanKandungan);
        FormMenu.add(BtnAwalKeperawatanNeonatus);
        FormMenu.add(BtnAwalKeperawatanBayiAnak);
        FormMenu.add(BtnAwalFisioterapi);
        FormMenu.add(BtnAwalMedis);
        FormMenu.add(BtnAwalMedisKandungan);
        FormMenu.add(BtnAwalMedisNeonatus);
        FormMenu.add(BtnAwalMedisHemodialisa);
        FormMenu.add(BtnPenilaianPreInduksi);
        FormMenu.add(BtnChecklistPreOperasi);
        FormMenu.add(BtnSignInSebelumAnestesi);
        FormMenu.add(BtnTimeOutSebelumInsisi);
        FormMenu.add(BtnSignOutSebelumMenutupLuka);
        FormMenu.add(BtnChecklistPostOperasi);
        FormMenu.add(BtnPenilaianPreOperasi);
        FormMenu.add(BtnCatatanAnastesiSedasi);
        FormMenu.add(BtnPenilaianPreAnestesi);
        FormMenu.add(BtnChecklistKesiapanAnestesi);
        FormMenu.add(BtnSkorAldrettePascaAnestesi);
        FormMenu.add(BtnSkorStewardPascaAnestesi);
        FormMenu.add(BtnSkorBromagePascaAnestesi);
        FormMenu.add(BtnCatatanPengkajianPaskaOperasi);
        FormMenu.add(BtnPenilaianPsikolog);
        FormMenu.add(BtnPenilaianPsikologKlinis);
        FormMenu.add(BtnPerencanaanPemulangan);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhDewasa);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhAnak);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhLansia);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhNeonatus);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhGeriatri);
        FormMenu.add(BtnPenilaianLanjutanResikoJatuhPsikiatri);
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);
        FormMenu.add(BtnPenilaianResikoDekubitus);
        FormMenu.add(BtnHasilPemeriksaanUSG);
        FormMenu.add(BtnHasilPemeriksaanUSGUrologi);
        FormMenu.add(BtnHasilPemeriksaanUSGNeonatus);
        FormMenu.add(BtnHasilPemeriksaanUSGGynecologi);
        FormMenu.add(BtnHasilPemeriksaanEKG);
        FormMenu.add(BtnHasilPemeriksaanECHO);
        FormMenu.add(BtnHasilPemeriksaanSlitLamp);
        FormMenu.add(BtnHasilPemeriksaanOCT);
        FormMenu.add(BtnHasilEndoskopiFaringLaring);
        FormMenu.add(BtnHasilEndoskopiHidung);
        FormMenu.add(BtnHasilEndoskopiTelinga);
        FormMenu.add(BtnDokumentasiESWL);
        FormMenu.add(BtnCatatanPersalinan);
        FormMenu.add(BtnLaporanTindakan);
        FormMenu.add(BtnCatatan);
        FormMenu.add(BtnCatatanObservasiRanap);
        FormMenu.add(BtnCatatanObservasiRanapKebidanan);
        FormMenu.add(BtnCatatanObservasiRanapPostPartum);
        FormMenu.add(BtnCatatanObservasiCHBP);
        FormMenu.add(BtnCatatanObservasiInduksiPersalinan);
        FormMenu.add(BtnCatatanObservasiBayi);
        FormMenu.add(BtnCatatanObservasiRestrainNonfarmakologi);
        FormMenu.add(BtnCatatanObservasiVentilator);
        FormMenu.add(BtnCatatanObservasiHemodialisa);
        FormMenu.add(BtnCatatanKeseimbanganCairan);
        FormMenu.add(BtnCatatanCairanHemodialisa);
        FormMenu.add(BtnChecklistPemberianFibrinolitik);
        FormMenu.add(BtnFollowUpDBD);
        FormMenu.add(BtnCatatanKeperawatan);
        FormMenu.add(BtnCatatanCekGDS);
        FormMenu.add(BtnPenilaianUlangNyeri);
        FormMenu.add(BtnPemantauanPEWSAnak);
        FormMenu.add(BtnPemantauanPEWSDewasa);
        FormMenu.add(BtnPemantauanMEOWS);
        FormMenu.add(BtnPemantauanEWSNeonatus);
        FormMenu.add(BtnChecklistKriteriaMasukHCU);
        FormMenu.add(BtnChecklistKriteriaKeluarHCU);
        FormMenu.add(BtnChecklistKriteriaMasukICU);
        FormMenu.add(BtnChecklistKriteriaKeluarICU);
        FormMenu.add(BtnMonitoringReaksiTranfusi);
        FormMenu.add(BtnSkriningNutrisiDewasa);
        FormMenu.add(BtnSkriningNutrisiLansia);
        FormMenu.add(BtnSkriningNutrisiAnak);
        FormMenu.add(BtnSkriningGiziLanjut);
        FormMenu.add(BtnAsuhanGizi);
        FormMenu.add(BtnMonitoringAsuhanGizi);
        FormMenu.add(BtnCatatanADIMEGizi);
        FormMenu.add(BtnKonselingFarmasi);
        FormMenu.add(BtnInformasiObat);
        FormMenu.add(BtnRekonsiliasiObat);
        FormMenu.add(BtnTransferAntarRuang);
        FormMenu.add(BtnPelaksanaanInformasiEdukasi);
        FormMenu.add(BtnPengkajianRestrain);
        FormMenu.add(BtnPenilaianBayiBaruLahir);
        FormMenu.add(BtnPenilaianPasienTerminal);
        FormMenu.add(BtnPenilaianKorbanKekerasan);
        FormMenu.add(BtnPenilaianKecemasanAnak);
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);
        FormMenu.add(BtnPenilaianPasienImunitasRendah);
        FormMenu.add(BtnPenilaianTambahanGeriatri);
        FormMenu.add(BtnPenilaianTambahanBunuhDiri);
        FormMenu.add(BtnPenilaianTambahanPerilakuKekerasan);
        FormMenu.add(BtnPenilaianTambahanMelarikanDiri);
        FormMenu.add(BtnPenilaianDerajatDehidrasi);
    }

    private void simpan() {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(KdDok.getText().trim().equals("")||TDokter.getText().trim().equals("")){
                    Valid.textKosong(KdDok,"Dokter");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText().trim())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    }else{
                        Sequel.AutoComitFalse();
                        sukses=true;
                        if(Sequel.menyimpantf("rawat_inap_dr","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                            TNoRw.getText(),TKdPrw.getText(),KdDok.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            BagianRS.getText(),Bhp.getText(),JmDokter.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                        })==true){
                            Sequel.queryu("delete from tampjurnal");
                            if(Valid.SetAngka(TTnd.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }                              
                                if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(JmDokter.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(KSO.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(BagianRS.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Bhp.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Menejemen.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(sukses==true){
                                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode());
                            }
                        }else{
                            sukses=false;
                        }

                        if(sukses==true){
                            Sequel.Commit();
                            tabModeDr.addRow(new Object[]{
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TNmPrw.getText(),KdDok.getText(),TDokter.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),Double.parseDouble(TTnd.getText()),TKdPrw.getText(),JmDokter.getText(),KSO.getText(),
                                BagianRS.getText(),Bhp.getText(),Menejemen.getText()
                            });
                            LCount.setText(""+tabModeDr.getRowCount());
                            BtnBatalActionPerformed(null);
                        }else{
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }
                }   break;
            case 1:
                if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){                              
                    Valid.textKosong(kdptg,"Petugas");
                }else if(TKdPrwPetugas.getText().trim().equals("")||TNmPrwPetugas.getText().trim().equals("")){
                    Valid.textKosong(TKdPrwPetugas,"perawatan");
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText().trim())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    }else{
                        Sequel.AutoComitFalse();
                        sukses=true;
                        if(Sequel.menyimpantf("rawat_inap_pr","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                            TNoRw.getText(),TKdPrwPetugas.getText(),kdptg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            BagianRS.getText(),Bhp.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                        })==true){
                            Sequel.queryu("delete from tampjurnal");
                            if(Valid.SetAngka(TTnd.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(JmPerawat.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(KSO.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(BagianRS.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Bhp.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Menejemen.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(sukses==true){
                                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode()); 
                            }
                        }else{
                            sukses=false;
                        }
                        if(sukses==true){
                            Sequel.Commit();
                            tabModePr.addRow(new Object[]{
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TNmPrwPetugas.getText(),kdptg.getText(),TPerawat.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),Double.parseDouble(TTnd.getText()),TKdPrwPetugas.getText(),
                                JmPerawat.getText(),KSO.getText(),BagianRS.getText(),Bhp.getText(),Menejemen.getText()
                            });
                            LCount.setText(""+tabModePr.getRowCount());
                            BtnBatalActionPerformed(null);
                        }else{
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }                            
                }   break;
            case 2:
                if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){                              
                    Valid.textKosong(KdDok2,"Dokter");
                }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                    Valid.textKosong(kdptg2,"Petugas");
                }else if(TKdPrwDokterPetugas.getText().trim().equals("")||TNmPrwDokterPetugas.getText().trim().equals("")){
                    Valid.textKosong(TKdPrwDokterPetugas,"perawatan");
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText().trim())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    }else{
                        Sequel.AutoComitFalse();
                        sukses=true;
                        if(Sequel.menyimpantf("rawat_inap_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
                            TNoRw.getText(),TKdPrwDokterPetugas.getText(),KdDok2.getText(),kdptg2.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            BagianRS.getText(),Bhp.getText(),JmDokter.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                        })==true){
                            Sequel.queryu("delete from tampjurnal");
                            if(Valid.SetAngka(TTnd.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(JmDokter.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(JmPerawat.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(KSO.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(BagianRS.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Bhp.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(Valid.SetAngka(Menejemen.getText())>0){
                                if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                                if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }   
                            }
                            if(sukses==true){
                                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode());
                            }
                        }else{
                            sukses=false;
                        }

                        if(sukses==true){
                            Sequel.Commit();
                            tabModeDrPr.addRow(new Object[]{
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TNmPrwDokterPetugas.getText(),KdDok2.getText(),TDokter2.getText(),kdptg2.getText(),TPerawat2.getText(),
                                Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),Double.parseDouble(TTnd.getText()),
                                TKdPrwDokterPetugas.getText(),JmDokter.getText(),JmPerawat.getText(),KSO.getText(),BagianRS.getText(),Bhp.getText(),Menejemen.getText()
                            });
                            LCount.setText(""+tabModeDrPr.getRowCount());
                            BtnBatalActionPerformed(null);
                        }else{
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }                            
                }   break;
            case 3:
                if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||(!TSuhu.getText().trim().equals(""))||
                        (!TTensi.getText().trim().equals(""))||(!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                        (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||(!TNadi.getText().trim().equals(""))||
                        (!TGCS.getText().trim().equals(""))||(!TindakLanjut.getText().trim().equals(""))||(!TPenilaian.getText().trim().equals(""))||
                        (!TInstruksi.getText().trim().equals(""))||(!SpO2.getText().trim().equals(""))||(!TEvaluasi.getText().trim().equals(""))){
                    if(KdPeg.getText().trim().equals("")||TPegawai.getText().trim().equals("")){
                        Valid.textKosong(KdPeg,"Dokter/Paramedis masih kosong...!!");
                    }else{
                        if(akses.getkode().equals("Admin Utama")){
                            if(Sequel.menyimpantf("pemeriksaan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20,new String[]{
                                    TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                      
                                    TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),
                                    cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                    TPenilaian.getText(),TindakLanjut.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText()
                                })==true){
                                tabModePemeriksaan.addRow(new Object[]{
                                    false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                    TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),TPenilaian.getText(),TindakLanjut.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText(),TPegawai.getText(),
                                    Jabatan.getText()
                                });
                                LCount.setText(""+tabModePemeriksaan.getRowCount());
                                BtnBatalActionPerformed(null);
                            }
                        }else{
                            if(akses.getkode().equals(KdPeg.getText())){
                                if(Sequel.menyimpantf("pemeriksaan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20,new String[]{
                                    TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                      
                                    TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),
                                    cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                    TPenilaian.getText(),TindakLanjut.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText()
                                })==true){
                                    tabModePemeriksaan.addRow(new Object[]{
                                        false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                        TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                        TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),TPenilaian.getText(),TindakLanjut.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText(),TPegawai.getText(),
                                        Jabatan.getText()
                                    });
                                    LCount.setText(""+tabModePemeriksaan.getRowCount());
                                    BtnBatalActionPerformed(null);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                            }
                        }
                    }
                }   break;
            case 4:
                if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                        (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                        (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                        (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                        (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                        (!TDenominator.getText().trim().equals(""))){
                    if(Sequel.menyimpantf("pemeriksaan_obstetri_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",23,new String[]{
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),
                            cmbKontraksi.getSelectedItem().toString(),TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),
                            cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),cmbDalam.getSelectedItem().toString(),TTebal.getText(),
                            cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),cmbKetuban.getSelectedItem().toString(),
                            cmbFeto.getSelectedItem().toString()
                        })==true){
                            tabModeObstetri.addRow(new Object[] {
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),cmbKontraksi.getSelectedItem().toString(),
                                TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),
                                cmbDalam.getSelectedItem().toString(),TTebal.getText(),cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),
                                cmbKetuban.getSelectedItem().toString(),cmbFeto.getSelectedItem().toString() 
                            });
                            TTinggi_uteri.setText("");cmbJanin.setSelectedIndex(0);TLetak.setText("");cmbPanggul.setSelectedIndex(0);TDenyut.setText("");
                            cmbKontraksi.setSelectedIndex(0);TKualitas_mnt.setText("");TKualitas_dtk.setText("");cmbFluksus.setSelectedIndex(0);
                            cmbAlbus.setSelectedIndex(0);TVulva.setText("");TPortio.setText("");cmbDalam.setSelectedIndex(0);TTebal.setText("");
                            cmbArah.setSelectedIndex(0);TPembukaan.setText("");TPenurunan.setText("");TDenominator.setText("");cmbKetuban.setSelectedIndex(0);
                            cmbFeto.getSelectedItem().toString();
                            LCount.setText(""+tabModeObstetri.getRowCount());
                    }
                }   break; 
            case 5:
                if ((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                        (!TInspekuloGine.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                        (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                        (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                        (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                        (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                        (!TCavumDouglas.getText().trim().equals(""))) {
                    if(Sequel.menyimpantf("pemeriksaan_ginekologi_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20, new String[] {
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),
                            cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                            TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                            TBentuk.getText(), TCavumUteri.getText(), cmbMobilitas.getSelectedItem().toString(),
                            TUkuran.getText(), cmbNyeriTekan.getSelectedItem().toString(),
                            TAdnexaKanan.getText(), TAdnexaKiri.getText(), TCavumDouglas.getText()
                        })==true){
                            tabModeGinekologi.addRow(new Object[] {
                                false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(),TVulvaInspekulo.getText(),
                                TPortioInspekulo.getText(),TSondage.getText(),TPortioDalam.getText(),TBentuk.getText(),TCavumUteri.getText(),cmbMobilitas.getSelectedItem().toString(),TUkuran.getText(),
                                cmbNyeriTekan.getSelectedItem().toString(),TAdnexaKanan.getText(),TAdnexaKiri.getText(),TCavumDouglas.getText()
                            });
                            TInspeksi.setText("");TInspeksiVulva.setText("");TInspekuloGine.setText("");
                            cmbFluxusGine.setSelectedIndex(0);cmbFluorGine.setSelectedIndex(0); TVulvaInspekulo.setText("");
                            TPortioInspekulo.setText(""); TSondage.setText(""); TPortioDalam.setText("");
                            TBentuk.setText(""); TCavumUteri.setText(""); cmbMobilitas.setSelectedIndex(0);
                            TUkuran.setText(""); cmbNyeriTekan.setSelectedIndex(0);
                            TAdnexaKanan.setText(""); TAdnexaKiri.setText(""); TCavumDouglas.getText();
                            LCount.setText(""+tabModeGinekologi.getRowCount());
                    }
                } break;
            default:
                break;
        }
    }

    private void gantitindakandokter() {
        if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.mengedittf("rawat_inap_dr","no_rawat='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1)+
                    "' and kd_jenis_prw='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),10)+  
                    "' and kd_dokter='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5)+
                    "' and tgl_perawatan='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7)+
                    "' and jam_rawat='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8)+"'",
                    "no_rawat='"+TNoRw.getText()+"',kd_jenis_prw='"+TKdPrw.getText()+
                    "',kd_dokter='"+KdDok.getText()+"',material='"+BagianRS.getText()+
                    "',bhp='"+Bhp.getText()+"',tarif_tindakandr='"+JmDokter.getText()+"',biaya_rawat='"+TTnd.getText()+
                    "',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                    "kso='"+KSO.getText()+"',menejemen='"+Menejemen.getText()+"'")==true){

                Sequel.queryu("delete from tampjurnal");
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }
                }
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                }
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                }
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }  
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                }
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                }
                if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                                    sukses=false;
                                }
                }
                
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PERUBAHAN TINDAKAN RAWAT INAP PASIEN OLEH "+akses.getkode());
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                        if(Valid.SetAngka(TTnd.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(JmDokter.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(KSO.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(BagianRS.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Bhp.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Menejemen.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(sukses==true){
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PERUBAHAN TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                    }
                }
            }else{
                sukses=false;
            }  

            if(sukses==true){
                Sequel.Commit();
                tbRawatDr.setValueAt(TNoRw.getText(),tbRawatDr.getSelectedRow(), 1);
                tbRawatDr.setValueAt(TNoRM.getText(),tbRawatDr.getSelectedRow(), 2);
                tbRawatDr.setValueAt(TPasien.getText(),tbRawatDr.getSelectedRow(), 3);
                tbRawatDr.setValueAt(TNmPrw.getText(),tbRawatDr.getSelectedRow(), 4);
                tbRawatDr.setValueAt(KdDok.getText(),tbRawatDr.getSelectedRow(), 5);
                tbRawatDr.setValueAt(TDokter.getText(),tbRawatDr.getSelectedRow(), 6);
                tbRawatDr.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbRawatDr.getSelectedRow(), 7);
                tbRawatDr.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbRawatDr.getSelectedRow(), 8);
                tbRawatDr.setValueAt(Double.parseDouble(TTnd.getText()),tbRawatDr.getSelectedRow(), 9);
                tbRawatDr.setValueAt(TKdPrw.getText(),tbRawatDr.getSelectedRow(), 10);
                tbRawatDr.setValueAt(JmDokter.getText(),tbRawatDr.getSelectedRow(), 11);
                tbRawatDr.setValueAt(KSO.getText(),tbRawatDr.getSelectedRow(), 12);
                tbRawatDr.setValueAt(BagianRS.getText(),tbRawatDr.getSelectedRow(), 13);
                tbRawatDr.setValueAt(Bhp.getText(),tbRawatDr.getSelectedRow(), 14);
                tbRawatDr.setValueAt(Menejemen.getText(),tbRawatDr.getSelectedRow(), 15);
                BtnBatalActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
        }
    }
    
    private void gantitindakanpetugas(){
        if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.mengedittf("rawat_inap_pr","no_rawat='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1)+
                    "' and kd_jenis_prw='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),10)+
                    "' and nip='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5)+
                    "' and tgl_perawatan='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7)+
                    "' and jam_rawat='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8)+"'",
                    "no_rawat='"+TNoRw.getText()+"',kd_jenis_prw='"+TKdPrwPetugas.getText()+
                    "',nip='"+kdptg.getText()+"',material='"+BagianRS.getText()+
                    "',bhp='"+Bhp.getText()+"',tarif_tindakanpr='"+JmPerawat.getText()+"',biaya_rawat='"+TTnd.getText()+
                    "',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                    "kso='"+KSO.getText()+"',menejemen='"+Menejemen.getText()+"'")==true){

                Sequel.queryu("delete from tampjurnal");
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }
                }
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }  
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }  
                }
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }  
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }
                }
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    if(Valid.SetAngka(TTnd.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(JmPerawat.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(KSO.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(BagianRS.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Bhp.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Menejemen.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(sukses==true){
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                    }
                }
            }else{
                sukses=false;
            } 
            if(sukses==true){
                Sequel.Commit();
                tbRawatPr.setValueAt(TNoRw.getText(),tbRawatPr.getSelectedRow(), 1);
                tbRawatPr.setValueAt(TNoRM.getText(),tbRawatPr.getSelectedRow(), 2);
                tbRawatPr.setValueAt(TPasien.getText(),tbRawatPr.getSelectedRow(), 3);
                tbRawatPr.setValueAt(TNmPrwPetugas.getText(),tbRawatPr.getSelectedRow(), 4);
                tbRawatPr.setValueAt(kdptg.getText(),tbRawatPr.getSelectedRow(), 5);
                tbRawatPr.setValueAt(TPerawat.getText(),tbRawatPr.getSelectedRow(), 6);
                tbRawatPr.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbRawatPr.getSelectedRow(), 7);
                tbRawatPr.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbRawatPr.getSelectedRow(), 8);
                tbRawatPr.setValueAt(Double.parseDouble(TTnd.getText()),tbRawatPr.getSelectedRow(), 9);
                tbRawatPr.setValueAt(TKdPrwPetugas.getText(),tbRawatPr.getSelectedRow(), 10);
                tbRawatPr.setValueAt(JmPerawat.getText(),tbRawatPr.getSelectedRow(), 11);
                tbRawatPr.setValueAt(KSO.getText(),tbRawatPr.getSelectedRow(), 12);
                tbRawatPr.setValueAt(BagianRS.getText(),tbRawatPr.getSelectedRow(), 13);
                tbRawatPr.setValueAt(Bhp.getText(),tbRawatPr.getSelectedRow(), 14);
                tbRawatPr.setValueAt(Menejemen.getText(),tbRawatPr.getSelectedRow(), 15);
                BtnBatalActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
        }
    }
    
    private void gantitindakandokterpetugas(){
        if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1).toString())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.mengedittf("rawat_inap_drpr","no_rawat='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1)+
                    "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),12)+
                    "' and kd_dokter='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),5)+
                    "' and nip='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),7)+
                    "' and tgl_perawatan='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9)+
                    "' and jam_rawat='"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),10)+"'",
                    "no_rawat='"+TNoRw.getText()+"',kd_jenis_prw='"+TKdPrwDokterPetugas.getText()+
                    "',nip='"+kdptg2.getText()+"',kd_dokter='"+KdDok2.getText()+"',material='"+BagianRS.getText()+
                    "',bhp='"+Bhp.getText()+"',tarif_tindakanpr='"+JmPerawat.getText()+"',tarif_tindakandr='"+JmDokter.getText()+"',biaya_rawat='"+TTnd.getText()+
                    "',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                    "kso='"+KSO.getText()+"',menejemen='"+Menejemen.getText()+"'")==true){

                Sequel.queryu("delete from tampjurnal");
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }   
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }     
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }         
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }  
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }                               
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    } 
                }
                if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString())>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }    
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                        sukses=false;
                    }  
                }
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                }

                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");
                    if(Valid.SetAngka(TTnd.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(JmDokter.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(JmPerawat.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(KSO.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(BagianRS.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Bhp.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(Valid.SetAngka(Menejemen.getText())>0){
                        if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }  
                        if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'")==false){
                            sukses=false;
                        }   
                    }
                    if(sukses==true){
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                    }
                }   
            }else{
                sukses=false;
            } 

            if(sukses==true){
                Sequel.Commit();
                tbRawatDrPr.setValueAt(TNoRw.getText(),tbRawatDrPr.getSelectedRow(), 1);
                tbRawatDrPr.setValueAt(TNoRM.getText(),tbRawatDrPr.getSelectedRow(), 2);
                tbRawatDrPr.setValueAt(TPasien.getText(),tbRawatDrPr.getSelectedRow(), 3);
                tbRawatDrPr.setValueAt(TNmPrwDokterPetugas.getText(),tbRawatDrPr.getSelectedRow(), 4);
                tbRawatDrPr.setValueAt(KdDok2.getText(),tbRawatDrPr.getSelectedRow(), 5);
                tbRawatDrPr.setValueAt(TDokter2.getText(),tbRawatDrPr.getSelectedRow(), 6);
                tbRawatDrPr.setValueAt(kdptg2.getText(),tbRawatDrPr.getSelectedRow(), 7);
                tbRawatDrPr.setValueAt(TPerawat2.getText(),tbRawatDrPr.getSelectedRow(), 8);
                tbRawatDrPr.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbRawatDrPr.getSelectedRow(), 9);
                tbRawatDrPr.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbRawatDrPr.getSelectedRow(), 10);
                tbRawatDrPr.setValueAt(TTnd.getText(),tbRawatDrPr.getSelectedRow(), 11);
                tbRawatDrPr.setValueAt(TKdPrwDokterPetugas.getText(),tbRawatDrPr.getSelectedRow(), 12);
                tbRawatDrPr.setValueAt(JmDokter.getText(),tbRawatDrPr.getSelectedRow(), 13);
                tbRawatDrPr.setValueAt(JmPerawat.getText(),tbRawatDrPr.getSelectedRow(), 14);
                tbRawatDrPr.setValueAt(KSO.getText(),tbRawatDrPr.getSelectedRow(), 15);
                tbRawatDrPr.setValueAt(BagianRS.getText(),tbRawatDrPr.getSelectedRow(), 16);
                tbRawatDrPr.setValueAt(Bhp.getText(),tbRawatDrPr.getSelectedRow(), 17);
                tbRawatDrPr.setValueAt(Menejemen.getText(),tbRawatDrPr.getSelectedRow(), 18);
                BtnBatalActionPerformed(null);
            }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();                                    
        }
    }
}

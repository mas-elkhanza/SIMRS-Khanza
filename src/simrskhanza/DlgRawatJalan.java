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

import surat.SuratKontrol;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import inventory.DlgPemberianObat;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import inventory.DlgCopyResep;
import inventory.DlgPeresepanDokter;
import inventory.InventoryResepLuar;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import keuangan.DlgJnsPerawatanRalan;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import permintaan.DlgBookingOperasi;
import permintaan.DlgPermintaanKonsultasiMedik;
import rekammedis.RMDataResumePasien;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanPelayananInformasiObat;
import permintaan.DlgPermintaanRadiologi;
import rekammedis.MasterCariTemplatePemeriksaan;
import rekammedis.RMCari5SOAPTerakhir;
import rekammedis.RMCatatanADIMEGizi;
import rekammedis.RMCatatanAnastesiSedasi;
import rekammedis.RMCatatanPengkajianPaskaOperasi;
import rekammedis.RMCatatanPersalinan;
import rekammedis.RMChecklistKesiapanAnestesi;
import rekammedis.RMChecklistKriteriaMasukHCU;
import rekammedis.RMChecklistKriteriaMasukICU;
import rekammedis.RMChecklistKriteriaMasukNICU;
import rekammedis.RMChecklistKriteriaMasukPICU;
import rekammedis.RMChecklistPemberianFibrinolitik;
import rekammedis.RMChecklistPostOperasi;
import rekammedis.RMChecklistPreOperasi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataCatatanCairanHemodialisa;
import rekammedis.RMDataCatatanCekGDS;
import rekammedis.RMDataCatatanKeperawatanRalan;
import rekammedis.RMDataCatatanKeseimbanganCairan;
import rekammedis.RMDataCatatanObservasiBayi;
import rekammedis.RMDataCatatanObservasiCHBP;
import rekammedis.RMDataCatatanObservasiHemodialisa;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMDataCatatanObservasiInduksiPersalinan;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataMonitoringReaksiTranfusi;
import rekammedis.RMDataSkriningGiziKehamilan;
import rekammedis.RMDataSkriningGiziLanjut;
import rekammedis.RMEdukasiPasienKeluargaRawatJalan;
import rekammedis.RMHasilEndoskopiFaringLaring;
import rekammedis.RMHasilEndoskopiHidung;
import rekammedis.RMHasilEndoskopiTelinga;
import rekammedis.RMHasilPemeriksaanEKG;
import rekammedis.RMHasilPemeriksaanEcho;
import rekammedis.RMHasilPemeriksaanEchoPediatrik;
import rekammedis.RMHasilPemeriksaanOCT;
import rekammedis.RMHasilPemeriksaanSlitLamp;
import rekammedis.RMHasilPemeriksaanTreadmill;
import rekammedis.RMHasilPemeriksaanUSG;
import rekammedis.RMHasilPemeriksaanUSGGynecologi;
import rekammedis.RMHasilPemeriksaanUSGNeonatus;
import rekammedis.RMHasilPemeriksaanUSGUrologi;
import rekammedis.RMHasilTindakanESWL;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMLaporanTindakan;
import rekammedis.RMLayananKedokteranFisikRehabilitasi;
import rekammedis.RMMCU;
import rekammedis.RMMonitoringAldrettePascaAnestesi;
import rekammedis.RMMonitoringBromagePascaAnestesi;
import rekammedis.RMMonitoringStewardPascaAnestesi;
import rekammedis.RMPelaksanaanInformasiEdukasi;
import rekammedis.RMPemantauanMEOWS;
import rekammedis.RMPemantauanPEWS;
import rekammedis.RMPemantauanEWSD;
import rekammedis.RMPemantauanEWSNeonatus;
import rekammedis.RMPenatalaksanaanTerapiOkupasi;
import rekammedis.RMPengkajianRestrain;
import rekammedis.RMPenilaianAwalKeperawatanBayiAnak;
import rekammedis.RMPenilaianAwalKeperawatanGigi;
import rekammedis.RMPenilaianAwalKeperawatanIGD;
import rekammedis.RMPenilaianAwalKeperawatanKebidanan;
import rekammedis.RMPenilaianAwalKeperawatanRalan;
import rekammedis.RMPenilaianAwalKeperawatanRalanGeriatri;
import rekammedis.RMPenilaianAwalKeperawatanRalanPsikiatri;
import rekammedis.RMPenilaianAwalMedisHemodialisa;
import rekammedis.RMPenilaianAwalMedisIGD;
import rekammedis.RMPenilaianAwalMedisIGDPsikiatri;
import rekammedis.RMPenilaianAwalMedisRalanAnak;
import rekammedis.RMPenilaianAwalMedisRalanBedah;
import rekammedis.RMPenilaianAwalMedisRalanBedahMulut;
import rekammedis.RMPenilaianAwalMedisRalanDewasa;
import rekammedis.RMPenilaianAwalMedisRalanGeriatri;
import rekammedis.RMPenilaianAwalMedisRalanJantung;
import rekammedis.RMPenilaianAwalMedisRalanKandungan;
import rekammedis.RMPenilaianAwalMedisRalanKulitDanKelamin;
import rekammedis.RMPenilaianAwalMedisRalanMata;
import rekammedis.RMPenilaianAwalMedisRalanNeurologi;
import rekammedis.RMPenilaianAwalMedisRalanOrthopedi;
import rekammedis.RMPenilaianAwalMedisRalanParu;
import rekammedis.RMPenilaianAwalMedisRalanPenyakitDalam;
import rekammedis.RMPenilaianAwalMedisRalanPsikiatrik;
import rekammedis.RMPenilaianAwalMedisRalanRehabMedik;
import rekammedis.RMPenilaianAwalMedisRalanTHT;
import rekammedis.RMPenilaianAwalMedisRalanUrologi;
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
import rekammedis.RMPenilaianPasienImunitasRendah;
import rekammedis.RMPenilaianPasienKeracunan;
import rekammedis.RMPenilaianPasienPenyakitMenular;
import rekammedis.RMPenilaianPasienTerminal;
import rekammedis.RMPenilaianPreAnastesi;
import rekammedis.RMPenilaianPreInduksi;
import rekammedis.RMPenilaianPreOperasi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMPenilaianPsikologiKlinis;
import rekammedis.RMPenilaianRisikoJatuhNeonatus;
import rekammedis.RMPenilaianTambahanBunuhDiri;
import rekammedis.RMPenilaianTambahanGeriatri;
import rekammedis.RMPenilaianTambahanMelarikanDiri;
import rekammedis.RMPenilaianTambahanPerilakuKekerasan;
import rekammedis.RMPenilaianTerapiWicara;
import rekammedis.RMPenilaianUlangNyeri;
import rekammedis.RMRekonsiliasiObat;
import rekammedis.RMRiwayatPerawatan;
import rekammedis.RMSignInSebelumAnastesi;
import rekammedis.RMSignOutSebelumMenutupLuka;
import rekammedis.RMSkriningAdiksiNikotin;
import rekammedis.RMSkriningAnemia;
import rekammedis.RMSkriningCURB65;
import rekammedis.RMSkriningDiabetesMelitus;
import rekammedis.RMSkriningFrailtySyndrome;
import rekammedis.RMSkriningHipertensi;
import rekammedis.RMSkriningIndraPendengaran;
import rekammedis.RMSkriningInstrumenACRS;
import rekammedis.RMSkriningInstrumenAMT;
import rekammedis.RMSkriningInstrumenMentalEmosional;
import rekammedis.RMSkriningInstrumenSDQ;
import rekammedis.RMSkriningKankerKolorektal;
import rekammedis.RMSkriningKekerasanPadaPerempuan;
import rekammedis.RMSkriningKesehatanGigiMulutBalita;
import rekammedis.RMSkriningKesehatanGigiMulutDewasa;
import rekammedis.RMSkriningKesehatanGigiMulutLansia;
import rekammedis.RMSkriningKesehatanGigiMulutRemaja;
import rekammedis.RMSkriningKesehatanPenglihatan;
import rekammedis.RMSkriningMerokokUsiaSekolahRemaja;
import rekammedis.RMSkriningNutrisiAnak;
import rekammedis.RMSkriningNutrisiDewasa;
import rekammedis.RMSkriningNutrisiLansia;
import rekammedis.RMSkriningObesitas;
import rekammedis.RMSkriningPUMA;
import rekammedis.RMSkriningPneumoniaSeverityIndex;
import rekammedis.RMSkriningRisikoKankerParu;
import rekammedis.RMSkriningRisikoKankerPayudara;
import rekammedis.RMSkriningRisikoKankerServiks;
import rekammedis.RMSkriningSRQ;
import rekammedis.RMSkriningTBC;
import rekammedis.RMSkriningTalasemia;
import rekammedis.RMTimeOutSebelumInsisi;
import rekammedis.RMTransferPasienAntarRuang;
import rekammedis.RMTriaseIGD;
import rekammedis.RMUjiFungsiKFR;

/**
 *
 * @author dosen
 */
public final class DlgRawatJalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr,tabModePr,tabModeDrPr,
            tabModePemeriksaan,tabModeObstetri,tabModeGinekologi,
            TabModeTindakan,TabModeTindakan2,TabModeTindakan3,TabModeCatatan;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,pstindakan,psset_tarif,psrekening;
    private ResultSet rs,rstindakan,rsset_tarif,rsrekening;
    private int i=0,jmlparsial=0,jml=0,index=0,tinggi=0;
    private String aktifkanparsial="no",kode_poli="",kd_pj="",poli_ralan="No",cara_bayar_ralan="No",TANGGALMUNDUR="yes",
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",
            Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",
            Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="";
    private boolean[] pilih; 
    private String[] kode,nama,kategori;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen;
    private boolean sukses=false;
    public  boolean bypassranap=false;
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;
    private Jurnal jur=new Jurnal();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initRawatJalan();

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
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","NIP","Petugas Yg Menangani","Tgl.Rawat","Jam Rawat",
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
                 java.lang.Object.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
            "Respirasi(/menit)","Tinggi(Cm)","Berat(Kg)","SpO2(%)","GCS(E,V,M)","Kesadaran","Subjek","Objek","Alergi",
            "L.P.(Cm)","Plan","Asesmen","Inst/Impl","Evaluasi","NIP","Dokter/Paramedis","Profesi/Jabatan"}){
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
                 java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
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
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(180);
            }else if(i==22){
                column.setPreferredWidth(180);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
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
        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
            "P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakan2=new DefaultTableModel(null,new Object[]{"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan2.setModel(TabModeTindakan2);
        tbTindakan2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakan3=new DefaultTableModel(null,new Object[]{"P","Kode","Nama Perawatan","Kategori Perawatan","Tarif/Biaya","Bagian RS","BHP","JM Dokter","JM Perawat","KSO","Menejemen"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan3.setModel(TabModeTindakan3);
        tbTindakan3.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbTindakan3.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbTindakan3.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeCatatan=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tanggal","Jam","Kode Dokter","Nama Dokter","Catatan"}){
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
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbCatatan.setModel(TabModeCatatan);
        tbCatatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbCatatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbCatatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(700);
            }
        }
        tbCatatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte)20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte)20).getKata(KdDok2));
        KdPeg.setDocument(new batasInput((byte)20).getKata(KdPeg));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TKeluhan.setDocument(new batasInput((int)2000).getKata(TKeluhan));
        TPemeriksaan.setDocument(new batasInput((int)2000).getKata(TPemeriksaan));
        TPenilaian.setDocument(new batasInput((int)2000).getKata(TPenilaian));    
        TInstruksi.setDocument(new batasInput((int)2000).getKata(TInstruksi));     
        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));       
        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS));
        TTinggi.setDocument(new batasInput((byte)5).getKata(TTinggi));
        LingkarPerut.setDocument(new batasInput((byte)5).getKata(LingkarPerut));
        TBerat.setDocument(new batasInput((byte)5).getKata(TBerat));
        TindakLanjut.setDocument(new batasInput((int)2000).getKata(TindakLanjut));
        TEvaluasi.setDocument(new batasInput((int)2000).getKata(TEvaluasi));
        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
        SpO2.setDocument(new batasInput((byte)3).getOnlyAngka(SpO2));
        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));
        TTinggi_uteri.setDocument(new batasInput((byte)5).getKata(TTinggi_uteri));
        TLetak.setDocument(new batasInput((byte)50).getKata(TLetak));
        TDenyut.setDocument(new batasInput((byte)5).getKata(TDenyut));
        TKualitas_dtk.setDocument(new batasInput((byte)5).getKata(TKualitas_dtk));
        TKualitas_mnt.setDocument(new batasInput((byte)5).getKata(TKualitas_mnt));
        TVulva.setDocument(new batasInput((byte)50).getKata(TVulva));
        TPortio.setDocument(new batasInput((byte)50).getKata(TPortio));
        TTebal.setDocument(new batasInput((byte)5).getKata(TTebal));
        TPembukaan.setDocument(new batasInput((byte)50).getKata(TPembukaan));
        TPenurunan.setDocument(new batasInput((byte)50).getKata(TPenurunan));
        TDenominator.setDocument(new batasInput((byte)50).getKata(TDenominator));
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
        Catatan.setDocument(new batasInput((int)700).getKata(Catatan));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TampilkanData();
                    }
                }
            });
        }  
        
        panelDiagnosa1.TabRawat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LCount.setText(panelDiagnosa1.getRecord()+"");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbDiagnosaPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                } 
            }
        });
        
        panelDiagnosa1.tbDiagnosaPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbTindakanPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbTindakanPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                } 
            }
        });
        
        ChkInput.setSelected(false);
        isForm(); 
        ChkInput1.setSelected(false);
        isForm2(); 
        ChkInput2.setSelected(false);
        isForm3(); 
        ChkInput3.setSelected(false);
        isForm4();
        ChkAccor.setSelected(true);
        isMenu(); 
        jam();
        
        try {
            aktifkanparsial=koneksiDB.AKTIFKANBILLINGPARSIAL();
        } catch (Exception ex) {            
            aktifkanparsial="no";
        }
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        try {
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_KSO_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ralan=rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan=rsrekening.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rsrekening.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rsrekening.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rsrekening.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rsrekening.getString("Persediaan_BHP_Tindakan_Ralan");
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
        
        try {
            psset_tarif=koneksi.prepareStatement("select set_tarif.poli_ralan,set_tarif.cara_bayar_ralan from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    poli_ralan=rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan=rsset_tarif.getString("cara_bayar_ralan");
                }else{
                    poli_ralan="Yes";
                    cara_bayar_ralan="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        TCariPasien = new widget.TextBox();
        btnPasien = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnTambahTindakan = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        TabRawatTindakanDokter = new javax.swing.JTabbedPane();
        Scroll6 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        TabRawatTindakanPetugas = new javax.swing.JTabbedPane();
        Scroll7 = new widget.ScrollPane();
        tbTindakan2 = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        TabRawatTindakanDokterPetugas = new javax.swing.JTabbedPane();
        Scroll9 = new widget.ScrollPane();
        tbTindakan3 = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbRawatDrPr = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        jLabel8 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKeluhan = new widget.TextArea();
        jLabel28 = new widget.Label();
        jLabel26 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        TSuhu = new widget.TextBox();
        TTensi = new widget.TextBox();
        TTinggi = new widget.TextBox();
        TRespirasi = new widget.TextBox();
        TBerat = new widget.TextBox();
        TNadi = new widget.TextBox();
        TGCS = new widget.TextBox();
        TAlergi = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        TPenilaian = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        TindakLanjut = new widget.TextArea();
        jLabel29 = new widget.Label();
        cmbKesadaran = new widget.ComboBox();
        jLabel37 = new widget.Label();
        KdPeg = new widget.TextBox();
        TPegawai = new widget.TextBox();
        BtnSeekPegawai = new widget.Button();
        Jabatan = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel53 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        TInstruksi = new widget.TextArea();
        jLabel54 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel56 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        TEvaluasi = new widget.TextArea();
        LingkarPerut = new widget.TextBox();
        Btn5Soap = new widget.Button();
        BtnTemplatePemeriksaan = new widget.Button();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbPemeriksaanObstetri = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput1 = new widget.CekBox();
        panelGlass13 = new widget.panelisi();
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
        PanelInput2 = new javax.swing.JPanel();
        ChkInput2 = new widget.CekBox();
        panelGlass14 = new widget.panelisi();
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
        panelDiagnosa1 = new laporan.PanelDiagnosa();
        internalFrame8 = new widget.InternalFrame();
        PanelInput3 = new javax.swing.JPanel();
        ChkInput3 = new widget.CekBox();
        panelGlass15 = new widget.panelisi();
        jLabel55 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Catatan = new widget.TextArea();
        jLabel11 = new widget.Label();
        KdDok3 = new widget.TextBox();
        TDokter3 = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbCatatan = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel23 = new widget.Label();
        DTPTgl = new widget.Tanggal();
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
        BtnResepLuar = new widget.Button();
        BtnInputObat = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnKamar = new widget.Button();
        BtnTriaseIGD = new widget.Button();
        BtnRujukInternal = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalKeperawatanIGD = new widget.Button();
        BtnAwalKeperawatan = new widget.Button();
        BtnAwalKeperawatanGigi = new widget.Button();
        BtnAwalKeperawatanKandungan = new widget.Button();
        BtnAwalKeperawatanAnak = new widget.Button();
        BtnAwalKeperawatanPsikiatri = new widget.Button();
        BtnAwalKeperawatanGeriatri = new widget.Button();
        BtnAwalFisioterapi = new widget.Button();
        BtnAwalTerapiWicara = new widget.Button();
        BtnAwalMedisIGD = new widget.Button();
        BtnAwalMedisIGDPsikiatri = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnAwalMedisAnak = new widget.Button();
        BtnAwalMedisTHT = new widget.Button();
        BtnAwalMedisPsikiatri = new widget.Button();
        BtnAwalMedisPenyakitDalam = new widget.Button();
        BtnAwalMedisMata = new widget.Button();
        BtnAwalMedisNeurologi = new widget.Button();
        BtnAwalMedisOrthopedi = new widget.Button();
        BtnAwalMedisBedah = new widget.Button();
        BtnAwalMedisBedahMulut = new widget.Button();
        BtnAwalMedisGeriatri = new widget.Button();
        BtnAwalMedisKulitKelamin = new widget.Button();
        BtnAwalMedisParu = new widget.Button();
        BtnAwalMedisRehabMedik = new widget.Button();
        BtnAwalMedisHemodialisa = new widget.Button();
        BtnRujukKeluar = new widget.Button();
        BtnCatatan = new widget.Button();
        BtnCatatanObservasiIGD = new widget.Button();
        BtnCatatanCekGDS = new widget.Button();
        BtnCatatanKeperawatan = new widget.Button();
        BtnPenilaianUlangNyeri = new widget.Button();
        BtnPemantauanPEWSAnak = new widget.Button();
        BtnPemantauanPEWSDewasa = new widget.Button();
        BtnPemantauanMEOWS = new widget.Button();
        BtnPemantauanEWSNeonatus = new widget.Button();
        BtnMonitoringReaksiTranfusi = new widget.Button();
        BtnUjiFungsiKFR = new widget.Button();
        BtnChecklistKriteriaMasukHCU = new widget.Button();
        BtnChecklistKriteriaMasukICU = new widget.Button();
        BtnChecklistPreOperasi = new widget.Button();
        BtnSignInSebelumAnestesi = new widget.Button();
        BtnTimeOutSebelumInsisi = new widget.Button();
        BtnSignOutSebelumMenutupLuka = new widget.Button();
        BtnChecklistPostOperasi = new widget.Button();
        BtnPenilaianPreOperasi = new widget.Button();
        BtnPenilaianPreAnestesi = new widget.Button();
        BtnSkorAldrettePascaAnestesi = new widget.Button();
        BtnSkorStewardPascaAnestesi = new widget.Button();
        BtnMedicalCheckUp = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhDewasa = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhAnak = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhLansia = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhNeonatus = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhGeriatri = new widget.Button();
        BtnPenilaianLanjutanRisikoJatuhPsikiatri = new widget.Button();
        BtnPenilaianLanjutanSkriningFungsional = new widget.Button();
        BtnHasilPemeriksaanUSG = new widget.Button();
        BtnDokumentasiESWL = new widget.Button();
        BtnCatatanPersalinanan = new widget.Button();
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
        BtnEdukasiPasienKeluarga = new widget.Button();
        BtnPengkajianRestrain = new widget.Button();
        BtnPenilaianPasienTerminal = new widget.Button();
        BtnPenilaianKorbanKekerasan = new widget.Button();
        BtnPenilaianPasienPenyakitMenular = new widget.Button();
        BtnPenilaianPasienKeracunan = new widget.Button();
        BtnPenilaianTambahanGeriatri = new widget.Button();
        BtnPenilaianTambahanBunuhDiri = new widget.Button();
        BtnPenilaianTambahanPerilakuKekerasan = new widget.Button();
        BtnPenilaianTambahanMelarikanDiri = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); 
        internalFrame1.setName("internalFrame1"); 
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); 
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); 
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); 
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); 
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); 
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); 
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); 
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); 
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); 
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); 
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); 
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); 
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); 
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); 
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
        jLabel10.setName("jLabel10"); 
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); 
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); 
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); 
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

        panelGlass9.setName("panelGlass9"); 
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); 
        jLabel19.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); 
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); 
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); 
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel24.setText("No.RM :");
        jLabel24.setName("jLabel24"); 
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel24);

        TCariPasien.setName("TCariPasien"); 
        TCariPasien.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass9.add(TCariPasien);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); 
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
        panelGlass9.add(btnPasien);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); 
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); 
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); 
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); 
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
        BtnCari.setName("BtnCari"); 
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

        BtnTambahTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); 
        BtnTambahTindakan.setMnemonic('3');
        BtnTambahTindakan.setToolTipText("Alt+3");
        BtnTambahTindakan.setName("BtnTambahTindakan"); 
        BtnTambahTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnTambahTindakan);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        TabRawat.setName("TabRawat"); 
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setForeground(new java.awt.Color(50, 50, 50));
        internalFrame2.setName("internalFrame2"); 
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); 
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); 
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 55, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); 
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(58, 10, 146, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); 
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(749, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); 
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 540, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokter.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokter.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        TabRawatTindakanDokter.setName("TabRawatTindakanDokter"); 
        TabRawatTindakanDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterMouseClicked(evt);
            }
        });

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); 
        Scroll6.setOpaque(true);

        tbTindakan.setToolTipText("");
        tbTindakan.setName("tbTindakan"); 
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbTindakan);

        TabRawatTindakanDokter.addTab("Daftar Tindakan/Tagihan", Scroll6);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); 
        Scroll.setOpaque(true);

        tbRawatDr.setName("tbRawatDr"); 
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

        TabRawatTindakanDokter.addTab("Tindakan Dilakukan", Scroll);

        internalFrame2.add(TabRawatTindakanDokter, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); 
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); 
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); 
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 63, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); 
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(66, 10, 146, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); 
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(749, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); 
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(214, 10, 532, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        TabRawatTindakanPetugas.setName("TabRawatTindakanPetugas"); 
        TabRawatTindakanPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanPetugasMouseClicked(evt);
            }
        });

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setName("Scroll7"); 
        Scroll7.setOpaque(true);

        tbTindakan2.setToolTipText("");
        tbTindakan2.setName("tbTindakan2"); 
        tbTindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan2KeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbTindakan2);

        TabRawatTindakanPetugas.addTab("Daftar Tindakan/Tagihan", Scroll7);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setName("Scroll8"); 
        Scroll8.setOpaque(true);

        tbRawatPr.setName("tbRawatPr"); 
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
        Scroll8.setViewportView(tbRawatPr);

        TabRawatTindakanPetugas.addTab("Tindakan Dilakukan", Scroll8);

        internalFrame3.add(TabRawatTindakanPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Petugas", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); 
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(null);
        panelGlass11.setName("panelGlass11"); 
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); 
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 65, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); 
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(68, 40, 130, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); 
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(749, 40, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); 
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(200, 40, 546, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); 
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 65, 23);

        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); 
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(68, 10, 130, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        TDokter2.setName("TDokter2"); 
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(200, 10, 546, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.setName("BtnSeekDokter2"); 
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(749, 10, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawatTindakanDokterPetugas.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatTindakanDokterPetugas.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatTindakanDokterPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        TabRawatTindakanDokterPetugas.setName("TabRawatTindakanDokterPetugas"); 
        TabRawatTindakanDokterPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatTindakanDokterPetugasMouseClicked(evt);
            }
        });

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setName("Scroll9"); 
        Scroll9.setOpaque(true);

        tbTindakan3.setToolTipText("");
        tbTindakan3.setName("tbTindakan3"); 
        tbTindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakan3KeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbTindakan3);

        TabRawatTindakanDokterPetugas.addTab("Daftar Tindakan/Tagihan", Scroll9);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setName("Scroll10"); 
        Scroll10.setOpaque(true);

        tbRawatDrPr.setName("tbRawatDrPr"); 
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
        Scroll10.setViewportView(tbRawatDrPr);

        TabRawatTindakanDokterPetugas.addTab("Tindakan Dilakukan", Scroll10);

        internalFrame4.add(TabRawatTindakanDokterPetugas, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penanganan Dokter & Petugas", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame5.setName("internalFrame5"); 
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); 
        Scroll3.setOpaque(true);

        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setName("tbPemeriksaan"); 
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

        PanelInput.setName("PanelInput"); 
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 273));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); 
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        panelGlass12.setName("panelGlass12"); 
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass12.setLayout(null);

        jLabel8.setText("Subjek :");
        jLabel8.setName("jLabel8"); 
        panelGlass12.add(jLabel8);
        jLabel8.setBounds(0, 70, 70, 23);

        jLabel7.setText("Suhu (°C) :");
        jLabel7.setName("jLabel7"); 
        panelGlass12.add(jLabel7);
        jLabel7.setBounds(0, 160, 70, 23);

        jLabel4.setText("Tensi (mmHg) :");
        jLabel4.setName("jLabel4"); 
        panelGlass12.add(jLabel4);
        jLabel4.setBounds(130, 160, 90, 23);

        jLabel16.setText("Berat (Kg) :");
        jLabel16.setName("jLabel16"); 
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(296, 160, 79, 23);

        jLabel18.setText("Nadi (/menit) :");
        jLabel18.setName("jLabel18"); 
        panelGlass12.add(jLabel18);
        jLabel18.setBounds(296, 190, 79, 23);

        jLabel25.setText("L.P. (Cm) :");
        jLabel25.setName("jLabel25"); 
        panelGlass12.add(jLabel25);
        jLabel25.setBounds(450, 10, 90, 23);

        jLabel17.setText("TB (Cm) :");
        jLabel17.setName("jLabel17"); 
        panelGlass12.add(jLabel17);
        jLabel17.setBounds(0, 190, 70, 23);

        jLabel9.setText("Objek :");
        jLabel9.setName("jLabel9"); 
        panelGlass12.add(jLabel9);
        jLabel9.setBounds(0, 115, 70, 23);

        jLabel15.setText("Alergi :");
        jLabel15.setName("jLabel15"); 
        panelGlass12.add(jLabel15);
        jLabel15.setBounds(624, 10, 70, 23);

        jLabel20.setText("RR (/menit) :");
        jLabel20.setName("jLabel20"); 
        panelGlass12.add(jLabel20);
        jLabel20.setBounds(130, 190, 90, 23);

        jLabel22.setText("GCS (E,V,M) :");
        jLabel22.setName("jLabel22"); 
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(120, 220, 70, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); 

        TKeluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeluhan.setColumns(20);
        TKeluhan.setRows(5);
        TKeluhan.setName("TKeluhan"); 
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKeluhan);

        panelGlass12.add(scrollPane1);
        scrollPane1.setBounds(73, 70, 360, 38);

        jLabel28.setText("Asesmen :");
        jLabel28.setName("jLabel28"); 
        panelGlass12.add(jLabel28);
        jLabel28.setBounds(450, 40, 90, 23);

        jLabel26.setText("Plan :");
        jLabel26.setName("jLabel26"); 
        panelGlass12.add(jLabel26);
        jLabel26.setBounds(450, 85, 90, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); 

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); 
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        panelGlass12.add(scrollPane2);
        scrollPane2.setBounds(73, 115, 360, 38);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); 
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        panelGlass12.add(TSuhu);
        TSuhu.setBounds(73, 160, 55, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); 
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTensi);
        TTensi.setBounds(223, 160, 74, 23);

        TTinggi.setFocusTraversalPolicyProvider(true);
        TTinggi.setName("TTinggi"); 
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTinggi);
        TTinggi.setBounds(73, 190, 55, 23);

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); 
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });
        panelGlass12.add(TRespirasi);
        TRespirasi.setBounds(223, 190, 55, 23);

        TBerat.setHighlighter(null);
        TBerat.setName("TBerat"); 
        TBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBeratKeyPressed(evt);
            }
        });
        panelGlass12.add(TBerat);
        TBerat.setBounds(378, 160, 55, 23);

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); 
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        panelGlass12.add(TNadi);
        TNadi.setBounds(378, 190, 55, 23);

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); 
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });
        panelGlass12.add(TGCS);
        TGCS.setBounds(193, 220, 42, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); 
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        panelGlass12.add(TAlergi);
        TAlergi.setBounds(697, 10, 206, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); 

        TPenilaian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPenilaian.setColumns(20);
        TPenilaian.setRows(5);
        TPenilaian.setName("TPenilaian"); 
        TPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenilaianKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TPenilaian);

        panelGlass12.add(scrollPane3);
        scrollPane3.setBounds(543, 40, 360, 38);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); 

        TindakLanjut.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakLanjut.setColumns(20);
        TindakLanjut.setRows(5);
        TindakLanjut.setName("TindakLanjut"); 
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(TindakLanjut);

        panelGlass12.add(scrollPane6);
        scrollPane6.setBounds(543, 85, 360, 47);

        jLabel29.setText("Kesadaran :");
        jLabel29.setName("jLabel29"); 
        panelGlass12.add(jLabel29);
        jLabel29.setBounds(234, 220, 70, 23);

        cmbKesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma","Apatis","Delirium","Meninggal"}));
        cmbKesadaran.setName("cmbKesadaran"); 
        cmbKesadaran.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKesadaranKeyPressed(evt);
            }
        });
        panelGlass12.add(cmbKesadaran);
        cmbKesadaran.setBounds(307, 220, 126, 23);

        jLabel37.setText("Dilakukan :");
        jLabel37.setName("jLabel37"); 
        panelGlass12.add(jLabel37);
        jLabel37.setBounds(0, 10, 70, 23);

        KdPeg.setHighlighter(null);
        KdPeg.setName("KdPeg"); 
        KdPeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegKeyPressed(evt);
            }
        });
        panelGlass12.add(KdPeg);
        KdPeg.setBounds(73, 10, 115, 23);

        TPegawai.setEditable(false);
        TPegawai.setHighlighter(null);
        TPegawai.setName("TPegawai"); 
        panelGlass12.add(TPegawai);
        TPegawai.setBounds(190, 10, 212, 23);

        BtnSeekPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekPegawai.setMnemonic('4');
        BtnSeekPegawai.setToolTipText("ALt+4");
        BtnSeekPegawai.setName("BtnSeekPegawai"); 
        BtnSeekPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPegawaiActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnSeekPegawai);
        BtnSeekPegawai.setBounds(405, 10, 28, 23);

        Jabatan.setEditable(false);
        Jabatan.setHighlighter(null);
        Jabatan.setName("Jabatan"); 
        panelGlass12.add(Jabatan);
        Jabatan.setBounds(193, 40, 178, 23);

        jLabel41.setText("Profesi / Jabatan / Departemen :");
        jLabel41.setName("jLabel41"); 
        panelGlass12.add(jLabel41);
        jLabel41.setBounds(0, 40, 190, 23);

        jLabel53.setText("Inst/Impl :");
        jLabel53.setName("jLabel53"); 
        panelGlass12.add(jLabel53);
        jLabel53.setBounds(450, 139, 90, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); 

        TInstruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TInstruksi.setColumns(20);
        TInstruksi.setRows(5);
        TInstruksi.setName("TInstruksi"); 
        TInstruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInstruksiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(TInstruksi);

        panelGlass12.add(scrollPane7);
        scrollPane7.setBounds(543, 139, 360, 50);

        jLabel54.setText("SpO2 (%) :");
        jLabel54.setName("jLabel54"); 
        panelGlass12.add(jLabel54);
        jLabel54.setBounds(0, 220, 70, 23);

        SpO2.setFocusTraversalPolicyProvider(true);
        SpO2.setName("SpO2"); 
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        panelGlass12.add(SpO2);
        SpO2.setBounds(73, 220, 42, 23);

        jLabel56.setText("Evaluasi :");
        jLabel56.setName("jLabel56"); 
        panelGlass12.add(jLabel56);
        jLabel56.setBounds(450, 196, 90, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); 

        TEvaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TEvaluasi.setColumns(20);
        TEvaluasi.setRows(5);
        TEvaluasi.setName("TEvaluasi"); 
        TEvaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TEvaluasiKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(TEvaluasi);

        panelGlass12.add(scrollPane8);
        scrollPane8.setBounds(543, 196, 360, 44);

        LingkarPerut.setHighlighter(null);
        LingkarPerut.setName("LingkarPerut"); 
        LingkarPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarPerutKeyPressed(evt);
            }
        });
        panelGlass12.add(LingkarPerut);
        LingkarPerut.setBounds(543, 10, 55, 23);

        Btn5Soap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        Btn5Soap.setMnemonic('4');
        Btn5Soap.setToolTipText("ALt+4");
        Btn5Soap.setName("Btn5Soap"); 
        Btn5Soap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn5SoapActionPerformed(evt);
            }
        });
        panelGlass12.add(Btn5Soap);
        Btn5Soap.setBounds(374, 40, 28, 23);

        BtnTemplatePemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnTemplatePemeriksaan.setMnemonic('4');
        BtnTemplatePemeriksaan.setToolTipText("ALt+4");
        BtnTemplatePemeriksaan.setName("BtnTemplatePemeriksaan"); 
        BtnTemplatePemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplatePemeriksaanActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnTemplatePemeriksaan);
        BtnTemplatePemeriksaan.setBounds(405, 40, 28, 23);

        PanelInput.add(panelGlass12, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame6.setName("internalFrame6"); 
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); 
        Scroll4.setOpaque(true);

        tbPemeriksaanObstetri.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanObstetri.setName("tbPemeriksaanObstetri"); 
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

        PanelInput1.setName("PanelInput1"); 
        PanelInput1.setOpaque(false);
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); 
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        panelGlass13.setName("panelGlass13"); 
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass13.setLayout(null);

        jLabel27.setText("Tinggi Fundus Uteri (Cm) :");
        jLabel27.setName("jLabel27"); 
        panelGlass13.add(jLabel27);
        jLabel27.setBounds(0, 10, 135, 23);

        TTinggi_uteri.setHighlighter(null);
        TTinggi_uteri.setName("TTinggi_uteri"); 
        TTinggi_uteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggi_uteriKeyPressed(evt);
            }
        });
        panelGlass13.add(TTinggi_uteri);
        TTinggi_uteri.setBounds(138, 10, 50, 23);

        jLabel30.setText("Janin :");
        jLabel30.setName("jLabel30"); 
        panelGlass13.add(jLabel30);
        jLabel30.setBounds(194, 10, 45, 23);

        jLabel31.setText("Letak :");
        jLabel31.setName("jLabel31"); 
        panelGlass13.add(jLabel31);
        jLabel31.setBounds(375, 10, 40, 23);

        TLetak.setHighlighter(null);
        TLetak.setName("TLetak"); 
        TLetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLetakKeyPressed(evt);
            }
        });
        panelGlass13.add(TLetak);
        TLetak.setBounds(418, 10, 50, 23);

        jLabel32.setText("Bagian Bawah Panggul :");
        jLabel32.setName("jLabel32"); 
        panelGlass13.add(jLabel32);
        jLabel32.setBounds(486, 10, 130, 23);

        TKualitas_dtk.setFocusTraversalPolicyProvider(true);
        TKualitas_dtk.setName("TKualitas_dtk"); 
        TKualitas_dtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_dtkKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_dtk);
        TKualitas_dtk.setBounds(402, 40, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("detik");
        jLabel33.setName("jLabel33"); 
        panelGlass13.add(jLabel33);
        jLabel33.setBounds(455, 40, 30, 23);

        cmbPanggul.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "5/5", "4/5", "3/5", "2/5", "1/5" }));
        cmbPanggul.setName("cmbPanggul"); 
        cmbPanggul.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPanggul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPanggulKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbPanggul);
        cmbPanggul.setBounds(619, 10, 62, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("/10 menit/");
        jLabel34.setName("jLabel34"); 
        panelGlass13.add(jLabel34);
        jLabel34.setBounds(343, 40, 58, 23);

        TTebal.setHighlighter(null);
        TTebal.setName("TTebal"); 
        TTebal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTebalKeyPressed(evt);
            }
        });
        panelGlass13.add(TTebal);
        TTebal.setBounds(709, 70, 50, 23);

        TDenyut.setHighlighter(null);
        TDenyut.setName("TDenyut"); 
        TDenyut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenyutKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenyut);
        TDenyut.setBounds(876, 10, 50, 23);

        jLabel36.setText("Denyut Jantung Fetus (x/mnt) :");
        jLabel36.setName("jLabel36"); 
        panelGlass13.add(jLabel36);
        jLabel36.setBounds(693, 10, 170, 23);

        TDenominator.setHighlighter(null);
        TDenominator.setName("TDenominator"); 
        TDenominator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDenominatorKeyPressed(evt);
            }
        });
        panelGlass13.add(TDenominator);
        TDenominator.setBounds(548, 100, 125, 23);

        jLabel38.setText("Penurunan :");
        jLabel38.setName("jLabel38"); 
        panelGlass13.add(jLabel38);
        jLabel38.setBounds(267, 100, 70, 23);

        jLabel39.setText("Imbang Feto-Pelvik :");
        jLabel39.setName("jLabel39"); 
        panelGlass13.add(jLabel39);
        jLabel39.setBounds(673, 100, 110, 23);

        TKualitas_mnt.setHighlighter(null);
        TKualitas_mnt.setName("TKualitas_mnt"); 
        TKualitas_mnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKualitas_mntKeyPressed(evt);
            }
        });
        panelGlass13.add(TKualitas_mnt);
        TKualitas_mnt.setBounds(293, 40, 50, 23);

        jLabel40.setText("Portio Inspekulo :");
        jLabel40.setName("jLabel40"); 
        panelGlass13.add(jLabel40);
        jLabel40.setBounds(272, 70, 90, 23);

        cmbFeto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Susp.CPD-FPD", "CPD-FPD" }));
        cmbFeto.setName("cmbFeto"); 
        cmbFeto.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFetoKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFeto);
        cmbFeto.setBounds(786, 100, 140, 23);

        jLabel42.setText("Denominator :");
        jLabel42.setName("jLabel42"); 
        jLabel42.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass13.add(jLabel42);
        jLabel42.setBounds(470, 100, 75, 23);

        cmbJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Tunggal", "Gemelli" }));
        cmbJanin.setName("cmbJanin"); 
        cmbJanin.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJanin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJaninKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbJanin);
        cmbJanin.setBounds(242, 10, 115, 23);

        cmbKetuban.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "+" }));
        cmbKetuban.setName("cmbKetuban"); 
        cmbKetuban.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKetuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKetubanKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKetuban);
        cmbKetuban.setBounds(864, 40, 62, 23);

        TPortio.setFocusTraversalPolicyProvider(true);
        TPortio.setName("TPortio"); 
        TPortio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioKeyPressed(evt);
            }
        });
        panelGlass13.add(TPortio);
        TPortio.setBounds(365, 70, 125, 23);

        jLabel43.setText("Kualitas (x/mnt) : ");
        jLabel43.setName("jLabel43"); 
        panelGlass13.add(jLabel43);
        jLabel43.setBounds(193, 40, 100, 23);

        TVulva.setHighlighter(null);
        TVulva.setName("TVulva"); 
        TVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaKeyPressed(evt);
            }
        });
        panelGlass13.add(TVulva);
        TVulva.setBounds(138, 70, 125, 23);

        cmbKontraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbKontraksi.setName("cmbKontraksi"); 
        cmbKontraksi.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbKontraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKontraksiKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbKontraksi);
        cmbKontraksi.setBounds(138, 40, 62, 23);

        cmbAlbus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbAlbus.setName("cmbAlbus"); 
        cmbAlbus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbAlbus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAlbusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbAlbus);
        cmbAlbus.setBounds(698, 40, 62, 23);

        jLabel45.setText("Kontraksi :");
        jLabel45.setName("jLabel45"); 
        panelGlass13.add(jLabel45);
        jLabel45.setBounds(0, 40, 135, 23);

        jLabel46.setText("Fluor Albus :");
        jLabel46.setName("jLabel46"); 
        panelGlass13.add(jLabel46);
        jLabel46.setBounds(623, 40, 72, 23);

        jLabel47.setText("Vulva/Vagina :");
        jLabel47.setName("jLabel47"); 
        panelGlass13.add(jLabel47);
        jLabel47.setBounds(0, 70, 135, 23);

        jLabel44.setText("Fluksus :");
        jLabel44.setName("jLabel44"); 
        panelGlass13.add(jLabel44);
        jLabel44.setBounds(488, 40, 58, 23);

        cmbFluksus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluksus.setName("cmbFluksus"); 
        cmbFluksus.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluksus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluksusKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbFluksus);
        cmbFluksus.setBounds(549, 40, 62, 23);

        jLabel48.setText("Dalam :");
        jLabel48.setName("jLabel48"); 
        panelGlass13.add(jLabel48);
        jLabel48.setBounds(500, 70, 47, 23);

        cmbDalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kenyal", "Lunak" }));
        cmbDalam.setName("cmbDalam"); 
        cmbDalam.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDalamKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbDalam);
        cmbDalam.setBounds(550, 70, 95, 23);

        jLabel49.setText("Pembukaan :");
        jLabel49.setName("jLabel49"); 
        panelGlass13.add(jLabel49);
        jLabel49.setBounds(0, 100, 135, 23);

        TPembukaan.setHighlighter(null);
        TPembukaan.setName("TPembukaan"); 
        TPembukaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPembukaanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPembukaan);
        TPembukaan.setBounds(138, 100, 125, 23);

        TPenurunan.setHighlighter(null);
        TPenurunan.setName("TPenurunan"); 
        TPenurunan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPenurunanKeyPressed(evt);
            }
        });
        panelGlass13.add(TPenurunan);
        TPenurunan.setBounds(340, 100, 125, 23);

        jLabel50.setText("Tebal/cm :");
        jLabel50.setName("jLabel50"); 
        panelGlass13.add(jLabel50);
        jLabel50.setBounds(646, 70, 60, 23);

        jLabel51.setText("Selaput Ketuban :");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel51.setName("jLabel51"); 
        panelGlass13.add(jLabel51);
        jLabel51.setBounds(771, 40, 90, 23);

        cmbArah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depan", "Axial", "Belakang" }));
        cmbArah.setName("cmbArah"); 
        cmbArah.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbArah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbArahKeyPressed(evt);
            }
        });
        panelGlass13.add(cmbArah);
        cmbArah.setBounds(806, 70, 120, 23);

        jLabel52.setText("Arah :");
        jLabel52.setName("jLabel52"); 
        panelGlass13.add(jLabel52);
        jLabel52.setBounds(763, 70, 40, 23);

        PanelInput1.add(panelGlass13, java.awt.BorderLayout.CENTER);

        internalFrame6.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Obstetri", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame7.setName("internalFrame7"); 
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); 
        Scroll5.setOpaque(true);
        Scroll5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Scroll5KeyPressed(evt);
            }
        });

        tbPemeriksaanGinekologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaanGinekologi.setName("tbPemeriksaanGinekologi"); 
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

        PanelInput2.setName("PanelInput2"); 
        PanelInput2.setOpaque(false);
        PanelInput2.setPreferredSize(new java.awt.Dimension(192, 245));
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput2.setMnemonic('I');
        ChkInput2.setText(".: Input Data");
        ChkInput2.setToolTipText("Alt+I");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2"); 
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        panelGlass14.setName("panelGlass14"); 
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass14.setLayout(null);

        jLabel35.setText("Inspeksi :");
        jLabel35.setName("jLabel35"); 
        panelGlass14.add(jLabel35);
        jLabel35.setBounds(0, 10, 70, 23);

        TInspeksiVulva.setHighlighter(null);
        TInspeksiVulva.setName("TInspeksiVulva"); 
        TInspeksiVulva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiVulvaKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksiVulva);
        TInspeksiVulva.setBounds(118, 40, 223, 23);

        TAdnexaKanan.setHighlighter(null);
        TAdnexaKanan.setName("TAdnexaKanan"); 
        TAdnexaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKananKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKanan);
        TAdnexaKanan.setBounds(510, 120, 355, 23);

        jLabel57.setText("Fluor Albus :");
        jLabel57.setName("jLabel57"); 
        panelGlass14.add(jLabel57);
        jLabel57.setBounds(206, 100, 70, 23);

        cmbMobilitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbMobilitas.setName("cmbMobilitas"); 
        cmbMobilitas.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbMobilitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMobilitasKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbMobilitas);
        cmbMobilitas.setBounds(803, 60, 62, 23);

        jLabel60.setText("Sondage :");
        jLabel60.setName("jLabel60"); 
        jLabel60.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel60);
        jLabel60.setBounds(20, 190, 95, 23);

        TInspekuloGine.setHighlighter(null);
        TInspekuloGine.setName("TInspekuloGine"); 
        TInspekuloGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspekuloGineKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspekuloGine);
        TInspekuloGine.setBounds(73, 70, 268, 23);

        jLabel62.setText("Vulva/Uretra/Vagina :");
        jLabel62.setName("jLabel62"); 
        panelGlass14.add(jLabel62);
        jLabel62.setBounds(0, 40, 115, 23);

        jLabel64.setText("Inspekulo :");
        jLabel64.setName("jLabel64"); 
        panelGlass14.add(jLabel64);
        jLabel64.setBounds(0, 70, 70, 23);

        jLabel67.setText("Fluxus :");
        jLabel67.setName("jLabel67"); 
        panelGlass14.add(jLabel67);
        jLabel67.setBounds(0, 100, 115, 23);

        TPortioInspekulo.setHighlighter(null);
        TPortioInspekulo.setName("TPortioInspekulo"); 
        TPortioInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioInspekulo);
        TPortioInspekulo.setBounds(118, 160, 223, 23);

        TCavumUteri.setHighlighter(null);
        TCavumUteri.setName("TCavumUteri"); 
        TCavumUteri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumUteriKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumUteri);
        TCavumUteri.setBounds(468, 60, 272, 23);

        cmbFluorGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluorGine.setName("cmbFluorGine"); 
        cmbFluorGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluorGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluorGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluorGine);
        cmbFluorGine.setBounds(279, 100, 62, 23);

        TInspeksi.setHighlighter(null);
        TInspeksi.setName("TInspeksi"); 
        TInspeksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInspeksiKeyPressed(evt);
            }
        });
        panelGlass14.add(TInspeksi);
        TInspeksi.setBounds(73, 10, 268, 23);

        cmbFluxusGine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbFluxusGine.setName("cmbFluxusGine"); 
        cmbFluxusGine.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbFluxusGine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbFluxusGineKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbFluxusGine);
        cmbFluxusGine.setBounds(118, 100, 62, 23);

        jLabel71.setText("Adnexa/Parametrium :");
        jLabel71.setName("jLabel71"); 
        jLabel71.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel71);
        jLabel71.setBounds(340, 120, 125, 23);

        jLabel72.setText("Portio :");
        jLabel72.setName("jLabel72"); 
        jLabel72.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel72);
        jLabel72.setBounds(20, 160, 95, 23);

        jLabel73.setText("Vulva/Vagina :");
        jLabel73.setName("jLabel73"); 
        jLabel73.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel73);
        jLabel73.setBounds(20, 130, 95, 23);

        jLabel74.setText("Pemeriksaan Dalam :");
        jLabel74.setName("jLabel74"); 
        jLabel74.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel74);
        jLabel74.setBounds(340, 10, 110, 23);

        jLabel75.setText("Kanan :");
        jLabel75.setName("jLabel75"); 
        jLabel75.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel75);
        jLabel75.setBounds(437, 120, 70, 23);

        TVulvaInspekulo.setHighlighter(null);
        TVulvaInspekulo.setName("TVulvaInspekulo"); 
        TVulvaInspekulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TVulvaInspekuloKeyPressed(evt);
            }
        });
        panelGlass14.add(TVulvaInspekulo);
        TVulvaInspekulo.setBounds(118, 130, 223, 23);

        jLabel76.setText(", Bentuk :");
        jLabel76.setName("jLabel76"); 
        jLabel76.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel76);
        jLabel76.setBounds(640, 30, 50, 23);

        jLabel77.setText(", Mobilitas :");
        jLabel77.setName("jLabel77"); 
        jLabel77.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel77);
        jLabel77.setBounds(740, 60, 60, 23);

        TPortioDalam.setHighlighter(null);
        TPortioDalam.setName("TPortioDalam"); 
        TPortioDalam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPortioDalamKeyPressed(evt);
            }
        });
        panelGlass14.add(TPortioDalam);
        TPortioDalam.setBounds(468, 30, 173, 23);

        TBentuk.setHighlighter(null);
        TBentuk.setName("TBentuk"); 
        TBentuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBentukKeyPressed(evt);
            }
        });
        panelGlass14.add(TBentuk);
        TBentuk.setBounds(693, 30, 173, 23);

        jLabel78.setText("Ukuran :");
        jLabel78.setName("jLabel78"); 
        jLabel78.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel78);
        jLabel78.setBounds(437, 90, 70, 23);

        cmbNyeriTekan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        cmbNyeriTekan.setName("cmbNyeriTekan"); 
        cmbNyeriTekan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbNyeriTekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbNyeriTekanKeyPressed(evt);
            }
        });
        panelGlass14.add(cmbNyeriTekan);
        cmbNyeriTekan.setBounds(803, 90, 62, 23);

        TSondage.setHighlighter(null);
        TSondage.setName("TSondage"); 
        TSondage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSondageKeyPressed(evt);
            }
        });
        panelGlass14.add(TSondage);
        TSondage.setBounds(118, 190, 223, 23);

        jLabel79.setText("Cavum Uteri :");
        jLabel79.setName("jLabel79"); 
        jLabel79.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel79);
        jLabel79.setBounds(340, 60, 125, 23);

        jLabel80.setText("Kiri :");
        jLabel80.setName("jLabel80"); 
        jLabel80.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel80);
        jLabel80.setBounds(437, 150, 70, 23);

        TAdnexaKiri.setHighlighter(null);
        TAdnexaKiri.setName("TAdnexaKiri"); 
        TAdnexaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAdnexaKiriKeyPressed(evt);
            }
        });
        panelGlass14.add(TAdnexaKiri);
        TAdnexaKiri.setBounds(510, 150, 355, 23);

        jLabel81.setText("Cavum Douglas :");
        jLabel81.setName("jLabel81"); 
        jLabel81.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel81);
        jLabel81.setBounds(340, 180, 125, 23);

        TCavumDouglas.setHighlighter(null);
        TCavumDouglas.setName("TCavumDouglas"); 
        TCavumDouglas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCavumDouglasKeyPressed(evt);
            }
        });
        panelGlass14.add(TCavumDouglas);
        TCavumDouglas.setBounds(468, 180, 397, 23);

        TUkuran.setHighlighter(null);
        TUkuran.setName("TUkuran"); 
        TUkuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUkuranKeyPressed(evt);
            }
        });
        panelGlass14.add(TUkuran);
        TUkuran.setBounds(510, 90, 217, 23);

        jLabel82.setText(", Nyeri Tekan :");
        jLabel82.setName("jLabel82"); 
        jLabel82.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel82);
        jLabel82.setBounds(724, 90, 76, 23);

        jLabel83.setText("Portio :");
        jLabel83.setName("jLabel83"); 
        jLabel83.setPreferredSize(new java.awt.Dimension(63, 14));
        panelGlass14.add(jLabel83);
        jLabel83.setBounds(340, 30, 125, 23);

        PanelInput2.add(panelGlass14, java.awt.BorderLayout.CENTER);

        internalFrame7.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Ginekologi", internalFrame7);

        panelDiagnosa1.setBorder(null);
        panelDiagnosa1.setName("panelDiagnosa1"); 
        TabRawat.addTab("Diagnosa", panelDiagnosa1);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame8.setName("internalFrame8"); 
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput3.setName("PanelInput3"); 
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 140));
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput3.setMnemonic('I');
        ChkInput3.setText(".: Input Data");
        ChkInput3.setToolTipText("Alt+I");
        ChkInput3.setBorderPainted(true);
        ChkInput3.setBorderPaintedFlat(true);
        ChkInput3.setFocusable(false);
        ChkInput3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput3.setName("ChkInput3"); 
        ChkInput3.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); 
        ChkInput3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); 
        ChkInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput3ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput3, java.awt.BorderLayout.PAGE_END);

        panelGlass15.setName("panelGlass15"); 
        panelGlass15.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass15.setLayout(null);

        jLabel55.setText("Catatan :");
        jLabel55.setName("jLabel55"); 
        panelGlass15.add(jLabel55);
        jLabel55.setBounds(0, 40, 60, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); 

        Catatan.setBorder(null);
        Catatan.setColumns(20);
        Catatan.setRows(5);
        Catatan.setName("Catatan"); 
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Catatan);

        panelGlass15.add(scrollPane4);
        scrollPane4.setBounds(64, 40, 713, 68);

        jLabel11.setText("Dokter :");
        jLabel11.setName("jLabel11"); 
        panelGlass15.add(jLabel11);
        jLabel11.setBounds(0, 10, 60, 23);

        KdDok3.setHighlighter(null);
        KdDok3.setName("KdDok3"); 
        KdDok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok3KeyPressed(evt);
            }
        });
        panelGlass15.add(KdDok3);
        KdDok3.setBounds(64, 10, 146, 23);

        TDokter3.setEditable(false);
        TDokter3.setHighlighter(null);
        TDokter3.setName("TDokter3"); 
        panelGlass15.add(TDokter3);
        TDokter3.setBounds(212, 10, 534, 23);

        BtnSeekDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); 
        BtnSeekDokter3.setMnemonic('4');
        BtnSeekDokter3.setToolTipText("ALt+4");
        BtnSeekDokter3.setName("BtnSeekDokter3"); 
        BtnSeekDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter3ActionPerformed(evt);
            }
        });
        panelGlass15.add(BtnSeekDokter3);
        BtnSeekDokter3.setBounds(749, 10, 28, 23);

        PanelInput3.add(panelGlass15, java.awt.BorderLayout.CENTER);

        internalFrame8.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        Scroll11.setName("Scroll11"); 
        Scroll11.setOpaque(true);

        tbCatatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbCatatan.setName("tbCatatan"); 
        tbCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCatatanMouseClicked(evt);
            }
        });
        tbCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbCatatanKeyReleased(evt);
            }
        });
        Scroll11.setViewportView(tbCatatan);

        internalFrame8.add(Scroll11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Dokter", internalFrame8);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        FormInput.setName("FormInput"); 
        FormInput.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); 
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); 
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
        TNoRM.setName("TNoRM"); 
        FormInput.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); 
        FormInput.add(TPasien);
        TPasien.setBounds(283, 10, 270, 23);

        jLabel23.setText("Tanggal :");
        jLabel23.setName("jLabel23"); 
        FormInput.add(jLabel23);
        jLabel23.setBounds(554, 10, 60, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2023" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); 
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(617, 10, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); 
        cmbJam.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(711, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); 
        cmbMnt.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(776, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); 
        cmbDtk.setPreferredSize(new java.awt.Dimension(62, 28));
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
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); 
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); 
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(906, 10, 23, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); 
        PanelAccor.setPreferredSize(new java.awt.Dimension(205, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); 
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); 
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); 
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); 
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); 
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); 
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(130, 383));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); 
        FormMenu.setPreferredSize(new java.awt.Dimension(150, 483));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); 
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnResepObat.setText("Input Resep");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); 
        BtnResepObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); 
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });

        BtnResepLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnResepLuar.setText("Resep Luar");
        BtnResepLuar.setFocusPainted(false);
        BtnResepLuar.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnResepLuar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepLuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepLuar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepLuar.setName("BtnResepLuar"); 
        BtnResepLuar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResepLuar.setRoundRect(false);
        BtnResepLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepLuarActionPerformed(evt);
            }
        });

        BtnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnInputObat.setText("Input Obat & BHP");
        BtnInputObat.setFocusPainted(false);
        BtnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnInputObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInputObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInputObat.setName("BtnInputObat"); 
        BtnInputObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInputObat.setRoundRect(false);
        BtnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInputObatActionPerformed(evt);
            }
        });

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); 
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); 
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); 
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); 
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); 
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); 
        BtnSKDP.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });

        BtnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnKamar.setText("Kamar Inap");
        BtnKamar.setFocusPainted(false);
        BtnKamar.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnKamar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKamar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKamar.setName("BtnKamar"); 
        BtnKamar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKamar.setRoundRect(false);
        BtnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKamarActionPerformed(evt);
            }
        });

        BtnTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnTriaseIGD.setText("Triase IGD");
        BtnTriaseIGD.setFocusPainted(false);
        BtnTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnTriaseIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTriaseIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTriaseIGD.setName("BtnTriaseIGD"); 
        BtnTriaseIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTriaseIGD.setRoundRect(false);
        BtnTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTriaseIGDActionPerformed(evt);
            }
        });

        BtnRujukInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnRujukInternal.setText("Rujuk Internal");
        BtnRujukInternal.setFocusPainted(false);
        BtnRujukInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnRujukInternal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukInternal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukInternal.setName("BtnRujukInternal"); 
        BtnRujukInternal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukInternal.setRoundRect(false);
        BtnRujukInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukInternalActionPerformed(evt);
            }
        });

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnResume.setText("Resume Pasien");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); 
        BtnResume.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanIGD.setText("Awal Keperawatan IGD");
        BtnAwalKeperawatanIGD.setFocusPainted(false);
        BtnAwalKeperawatanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanIGD.setName("BtnAwalKeperawatanIGD"); 
        BtnAwalKeperawatanIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanIGD.setRoundRect(false);
        BtnAwalKeperawatanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanIGDActionPerformed(evt);
            }
        });

        BtnAwalKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatan.setText("Awal Keperawatan Umum");
        BtnAwalKeperawatan.setFocusPainted(false);
        BtnAwalKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatan.setName("BtnAwalKeperawatan"); 
        BtnAwalKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatan.setRoundRect(false);
        BtnAwalKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanGigi.setText("Awal Keperawatan Gigi");
        BtnAwalKeperawatanGigi.setFocusPainted(false);
        BtnAwalKeperawatanGigi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanGigi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGigi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGigi.setName("BtnAwalKeperawatanGigi"); 
        BtnAwalKeperawatanGigi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGigi.setRoundRect(false);
        BtnAwalKeperawatanGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGigiActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanKandungan.setText("Awal Keperawatan Kandungan");
        BtnAwalKeperawatanKandungan.setFocusPainted(false);
        BtnAwalKeperawatanKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanKandungan.setName("BtnAwalKeperawatanKandungan"); 
        BtnAwalKeperawatanKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanKandungan.setRoundRect(false);
        BtnAwalKeperawatanKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanKandunganActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanAnak.setText("Awal Keperawatan Bayi/Anak");
        BtnAwalKeperawatanAnak.setFocusPainted(false);
        BtnAwalKeperawatanAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanAnak.setName("BtnAwalKeperawatanAnak"); 
        BtnAwalKeperawatanAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanAnak.setRoundRect(false);
        BtnAwalKeperawatanAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanAnakActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanPsikiatri.setText("Awal Keperawatan Psikiatri");
        BtnAwalKeperawatanPsikiatri.setFocusPainted(false);
        BtnAwalKeperawatanPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanPsikiatri.setName("BtnAwalKeperawatanPsikiatri"); 
        BtnAwalKeperawatanPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanPsikiatri.setRoundRect(false);
        BtnAwalKeperawatanPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanPsikiatriActionPerformed(evt);
            }
        });

        BtnAwalKeperawatanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalKeperawatanGeriatri.setText("Awal Keperawatan Geriatri");
        BtnAwalKeperawatanGeriatri.setFocusPainted(false);
        BtnAwalKeperawatanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalKeperawatanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanGeriatri.setName("BtnAwalKeperawatanGeriatri"); 
        BtnAwalKeperawatanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalKeperawatanGeriatri.setRoundRect(false);
        BtnAwalKeperawatanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanGeriatriActionPerformed(evt);
            }
        });

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); 
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });

        BtnAwalTerapiWicara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalTerapiWicara.setText("Terapi Wicara");
        BtnAwalTerapiWicara.setFocusPainted(false);
        BtnAwalTerapiWicara.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalTerapiWicara.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalTerapiWicara.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalTerapiWicara.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalTerapiWicara.setName("BtnAwalTerapiWicara"); 
        BtnAwalTerapiWicara.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalTerapiWicara.setRoundRect(false);
        BtnAwalTerapiWicara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalTerapiWicaraActionPerformed(evt);
            }
        });

        BtnAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisIGD.setText("Awal Medis IGD");
        BtnAwalMedisIGD.setFocusPainted(false);
        BtnAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisIGD.setName("BtnAwalMedisIGD"); 
        BtnAwalMedisIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisIGD.setRoundRect(false);
        BtnAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisIGDActionPerformed(evt);
            }
        });

        BtnAwalMedisIGDPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisIGDPsikiatri.setText("Awal Medis IGD Psikiatri");
        BtnAwalMedisIGDPsikiatri.setFocusPainted(false);
        BtnAwalMedisIGDPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisIGDPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisIGDPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisIGDPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisIGDPsikiatri.setName("BtnAwalMedisIGDPsikiatri"); 
        BtnAwalMedisIGDPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisIGDPsikiatri.setRoundRect(false);
        BtnAwalMedisIGDPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisIGDPsikiatriActionPerformed(evt);
            }
        });

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedis.setText("Awal Medis Umum");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); 
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); 
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });

        BtnAwalMedisAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisAnak.setText("Awal Medis Bayi/Anak");
        BtnAwalMedisAnak.setFocusPainted(false);
        BtnAwalMedisAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisAnak.setName("BtnAwalMedisAnak"); 
        BtnAwalMedisAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisAnak.setRoundRect(false);
        BtnAwalMedisAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisAnakActionPerformed(evt);
            }
        });

        BtnAwalMedisTHT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisTHT.setText("Awal Medis THT");
        BtnAwalMedisTHT.setFocusPainted(false);
        BtnAwalMedisTHT.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisTHT.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisTHT.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisTHT.setName("BtnAwalMedisTHT"); 
        BtnAwalMedisTHT.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisTHT.setRoundRect(false);
        BtnAwalMedisTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisTHTActionPerformed(evt);
            }
        });

        BtnAwalMedisPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisPsikiatri.setText("Awal Medis Psikiatri");
        BtnAwalMedisPsikiatri.setFocusPainted(false);
        BtnAwalMedisPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPsikiatri.setName("BtnAwalMedisPsikiatri"); 
        BtnAwalMedisPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPsikiatri.setRoundRect(false);
        BtnAwalMedisPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPsikiatriActionPerformed(evt);
            }
        });

        BtnAwalMedisPenyakitDalam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisPenyakitDalam.setText("Awal Medis Penyakit Dalam");
        BtnAwalMedisPenyakitDalam.setFocusPainted(false);
        BtnAwalMedisPenyakitDalam.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisPenyakitDalam.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisPenyakitDalam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisPenyakitDalam.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisPenyakitDalam.setName("BtnAwalMedisPenyakitDalam"); 
        BtnAwalMedisPenyakitDalam.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisPenyakitDalam.setRoundRect(false);
        BtnAwalMedisPenyakitDalam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisPenyakitDalamActionPerformed(evt);
            }
        });

        BtnAwalMedisMata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisMata.setText("Awal Medis Mata");
        BtnAwalMedisMata.setFocusPainted(false);
        BtnAwalMedisMata.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisMata.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisMata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisMata.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisMata.setName("BtnAwalMedisMata"); 
        BtnAwalMedisMata.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisMata.setRoundRect(false);
        BtnAwalMedisMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisMataActionPerformed(evt);
            }
        });

        BtnAwalMedisNeurologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisNeurologi.setText("Awal Medis Neurologi");
        BtnAwalMedisNeurologi.setFocusPainted(false);
        BtnAwalMedisNeurologi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisNeurologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisNeurologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisNeurologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisNeurologi.setName("BtnAwalMedisNeurologi"); 
        BtnAwalMedisNeurologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisNeurologi.setRoundRect(false);
        BtnAwalMedisNeurologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisNeurologiActionPerformed(evt);
            }
        });

        BtnAwalMedisOrthopedi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisOrthopedi.setText("Awal Medis Orthopedi");
        BtnAwalMedisOrthopedi.setFocusPainted(false);
        BtnAwalMedisOrthopedi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisOrthopedi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisOrthopedi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisOrthopedi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisOrthopedi.setName("BtnAwalMedisOrthopedi"); 
        BtnAwalMedisOrthopedi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisOrthopedi.setRoundRect(false);
        BtnAwalMedisOrthopedi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisOrthopediActionPerformed(evt);
            }
        });

        BtnAwalMedisBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisBedah.setText("Awal Medis Bedah");
        BtnAwalMedisBedah.setFocusPainted(false);
        BtnAwalMedisBedah.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisBedah.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisBedah.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisBedah.setName("BtnAwalMedisBedah"); 
        BtnAwalMedisBedah.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisBedah.setRoundRect(false);
        BtnAwalMedisBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisBedahActionPerformed(evt);
            }
        });

        BtnAwalMedisBedahMulut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisBedahMulut.setText("Awal Medis Bedah Mulut");
        BtnAwalMedisBedahMulut.setFocusPainted(false);
        BtnAwalMedisBedahMulut.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisBedahMulut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisBedahMulut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisBedahMulut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisBedahMulut.setName("BtnAwalMedisBedahMulut"); 
        BtnAwalMedisBedahMulut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisBedahMulut.setRoundRect(false);
        BtnAwalMedisBedahMulut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisBedahMulutActionPerformed(evt);
            }
        });

        BtnAwalMedisGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisGeriatri.setText("Awal Medis Geriatri");
        BtnAwalMedisGeriatri.setFocusPainted(false);
        BtnAwalMedisGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisGeriatri.setName("BtnAwalMedisGeriatri"); 
        BtnAwalMedisGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisGeriatri.setRoundRect(false);
        BtnAwalMedisGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisGeriatriActionPerformed(evt);
            }
        });

        BtnAwalMedisKulitKelamin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisKulitKelamin.setText("Awal Medis Kulit & Kelamin");
        BtnAwalMedisKulitKelamin.setFocusPainted(false);
        BtnAwalMedisKulitKelamin.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisKulitKelamin.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKulitKelamin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKulitKelamin.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKulitKelamin.setName("BtnAwalMedisKulitKelamin"); 
        BtnAwalMedisKulitKelamin.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisKulitKelamin.setRoundRect(false);
        BtnAwalMedisKulitKelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKulitKelaminActionPerformed(evt);
            }
        });

        BtnAwalMedisParu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisParu.setText("Awal Medis Paru");
        BtnAwalMedisParu.setFocusPainted(false);
        BtnAwalMedisParu.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisParu.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisParu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisParu.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisParu.setName("BtnAwalMedisParu"); 
        BtnAwalMedisParu.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisParu.setRoundRect(false);
        BtnAwalMedisParu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisParuActionPerformed(evt);
            }
        });

        BtnAwalMedisRehabMedik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisRehabMedik.setText("Awal Medis Fisik & Rehabilitasi");
        BtnAwalMedisRehabMedik.setFocusPainted(false);
        BtnAwalMedisRehabMedik.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisRehabMedik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisRehabMedik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisRehabMedik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisRehabMedik.setName("BtnAwalMedisRehabMedik"); 
        BtnAwalMedisRehabMedik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisRehabMedik.setRoundRect(false);
        BtnAwalMedisRehabMedik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisRehabMedikActionPerformed(evt);
            }
        });

        BtnAwalMedisHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisHemodialisa.setText("Awal Medis Hemodialisa");
        BtnAwalMedisHemodialisa.setFocusPainted(false);
        BtnAwalMedisHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisHemodialisa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisHemodialisa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisHemodialisa.setName("BtnAwalMedisHemodialisa"); 
        BtnAwalMedisHemodialisa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisHemodialisa.setRoundRect(false);
        BtnAwalMedisHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisHemodialisaActionPerformed(evt);
            }
        });

        BtnRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnRujukKeluar.setText("Rujuk Keluar");
        BtnRujukKeluar.setFocusPainted(false);
        BtnRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnRujukKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukKeluar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukKeluar.setName("BtnRujukKeluar"); 
        BtnRujukKeluar.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRujukKeluar.setRoundRect(false);
        BtnRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukKeluarActionPerformed(evt);
            }
        });

        BtnCatatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatan.setText("Catatan Pasien");
        BtnCatatan.setFocusPainted(false);
        BtnCatatan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatan.setName("BtnCatatan"); 
        BtnCatatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatan.setRoundRect(false);
        BtnCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanActionPerformed(evt);
            }
        });

        BtnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanObservasiIGD.setText("Observasi IGD");
        BtnCatatanObservasiIGD.setFocusPainted(false);
        BtnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanObservasiIGD.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanObservasiIGD.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanObservasiIGD.setName("BtnCatatanObservasiIGD"); 
        BtnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanObservasiIGD.setRoundRect(false);
        BtnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanObservasiIGDActionPerformed(evt);
            }
        });

        BtnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanCekGDS.setText("Catatan Cek GDS");
        BtnCatatanCekGDS.setFocusPainted(false);
        BtnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanCekGDS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanCekGDS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanCekGDS.setName("BtnCatatanCekGDS"); 
        BtnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanCekGDS.setRoundRect(false);
        BtnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanCekGDSActionPerformed(evt);
            }
        });

        BtnCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanKeperawatan.setText("Catatan Keperawatan");
        BtnCatatanKeperawatan.setFocusPainted(false);
        BtnCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanKeperawatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanKeperawatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanKeperawatan.setName("BtnCatatanKeperawatan"); 
        BtnCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanKeperawatan.setRoundRect(false);
        BtnCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanKeperawatanActionPerformed(evt);
            }
        });

        BtnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianUlangNyeri.setText("Pengkajian Ulang Nyeri");
        BtnPenilaianUlangNyeri.setFocusPainted(false);
        BtnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianUlangNyeri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianUlangNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianUlangNyeri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianUlangNyeri.setName("BtnPenilaianUlangNyeri"); 
        BtnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianUlangNyeri.setRoundRect(false);
        BtnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianUlangNyeriActionPerformed(evt);
            }
        });

        BtnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPemantauanPEWSAnak.setText("Pemantauan PEWS Anak");
        BtnPemantauanPEWSAnak.setFocusPainted(false);
        BtnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPemantauanPEWSAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSAnak.setName("BtnPemantauanPEWSAnak"); 
        BtnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSAnak.setRoundRect(false);
        BtnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSAnakActionPerformed(evt);
            }
        });

        BtnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPemantauanPEWSDewasa.setText("Pemantauan EWS Dewasa");
        BtnPemantauanPEWSDewasa.setFocusPainted(false);
        BtnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPemantauanPEWSDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanPEWSDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanPEWSDewasa.setName("BtnPemantauanPEWSDewasa"); 
        BtnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanPEWSDewasa.setRoundRect(false);
        BtnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });

        BtnPemantauanMEOWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPemantauanMEOWS.setText("Pemantauan MEOWS Obstetri");
        BtnPemantauanMEOWS.setFocusPainted(false);
        BtnPemantauanMEOWS.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPemantauanMEOWS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanMEOWS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanMEOWS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanMEOWS.setName("BtnPemantauanMEOWS"); 
        BtnPemantauanMEOWS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanMEOWS.setRoundRect(false);
        BtnPemantauanMEOWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanMEOWSActionPerformed(evt);
            }
        });

        BtnPemantauanEWSNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPemantauanEWSNeonatus.setText("Pemantauan EWS Neonatus");
        BtnPemantauanEWSNeonatus.setFocusPainted(false);
        BtnPemantauanEWSNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPemantauanEWSNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPemantauanEWSNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPemantauanEWSNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPemantauanEWSNeonatus.setName("BtnPemantauanEWSNeonatus"); 
        BtnPemantauanEWSNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPemantauanEWSNeonatus.setRoundRect(false);
        BtnPemantauanEWSNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemantauanEWSNeonatusActionPerformed(evt);
            }
        });

        BtnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        BtnMonitoringReaksiTranfusi.setFocusPainted(false);
        BtnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnMonitoringReaksiTranfusi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringReaksiTranfusi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringReaksiTranfusi.setName("BtnMonitoringReaksiTranfusi"); 
        BtnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringReaksiTranfusi.setRoundRect(false);
        BtnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });

        BtnUjiFungsiKFR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnUjiFungsiKFR.setText("Uji Fungsi/Prosedur KFR");
        BtnUjiFungsiKFR.setFocusPainted(false);
        BtnUjiFungsiKFR.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnUjiFungsiKFR.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnUjiFungsiKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnUjiFungsiKFR.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnUjiFungsiKFR.setName("BtnUjiFungsiKFR"); 
        BtnUjiFungsiKFR.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnUjiFungsiKFR.setRoundRect(false);
        BtnUjiFungsiKFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUjiFungsiKFRActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistKriteriaMasukHCU.setText("Check List Masuk HCU");
        BtnChecklistKriteriaMasukHCU.setFocusPainted(false);
        BtnChecklistKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistKriteriaMasukHCU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukHCU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukHCU.setName("BtnChecklistKriteriaMasukHCU"); 
        BtnChecklistKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukHCU.setRoundRect(false);
        BtnChecklistKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukHCUActionPerformed(evt);
            }
        });

        BtnChecklistKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistKriteriaMasukICU.setText("Check List Masuk ICU");
        BtnChecklistKriteriaMasukICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistKriteriaMasukICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukICU.setName("BtnChecklistKriteriaMasukICU"); 
        BtnChecklistKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukICU.setRoundRect(false);
        BtnChecklistKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistKriteriaMasukICUActionPerformed(evt);
            }
        });

        BtnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistPreOperasi.setText("Check List Pre Operasi");
        BtnChecklistPreOperasi.setFocusPainted(false);
        BtnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPreOperasi.setName("BtnChecklistPreOperasi"); 
        BtnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPreOperasi.setRoundRect(false);
        BtnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPreOperasiActionPerformed(evt);
            }
        });

        BtnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        BtnSignInSebelumAnestesi.setFocusPainted(false);
        BtnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSignInSebelumAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignInSebelumAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignInSebelumAnestesi.setName("BtnSignInSebelumAnestesi"); 
        BtnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignInSebelumAnestesi.setRoundRect(false);
        BtnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignInSebelumAnestesiActionPerformed(evt);
            }
        });

        BtnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        BtnTimeOutSebelumInsisi.setFocusPainted(false);
        BtnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnTimeOutSebelumInsisi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTimeOutSebelumInsisi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTimeOutSebelumInsisi.setName("BtnTimeOutSebelumInsisi"); 
        BtnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTimeOutSebelumInsisi.setRoundRect(false);
        BtnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });

        BtnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        BtnSignOutSebelumMenutupLuka.setFocusPainted(false);
        BtnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSignOutSebelumMenutupLuka.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSignOutSebelumMenutupLuka.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSignOutSebelumMenutupLuka.setName("BtnSignOutSebelumMenutupLuka"); 
        BtnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSignOutSebelumMenutupLuka.setRoundRect(false);
        BtnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });

        BtnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnChecklistPostOperasi.setText("Check List Post Operasi");
        BtnChecklistPostOperasi.setFocusPainted(false);
        BtnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistPostOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistPostOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistPostOperasi.setName("BtnChecklistPostOperasi"); 
        BtnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistPostOperasi.setRoundRect(false);
        BtnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnChecklistPostOperasiActionPerformed(evt);
            }
        });

        BtnPenilaianPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPreOperasi.setText("Pengkajian Pre Operasi");
        BtnPenilaianPreOperasi.setFocusPainted(false);
        BtnPenilaianPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPreOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreOperasi.setName("BtnPenilaianPreOperasi"); 
        BtnPenilaianPreOperasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreOperasi.setRoundRect(false);
        BtnPenilaianPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreOperasiActionPerformed(evt);
            }
        });

        BtnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPreAnestesi.setText("Pengkajian Pre Anestesi");
        BtnPenilaianPreAnestesi.setFocusPainted(false);
        BtnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPreAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPreAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPreAnestesi.setName("BtnPenilaianPreAnestesi"); 
        BtnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPreAnestesi.setRoundRect(false);
        BtnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPreAnestesiActionPerformed(evt);
            }
        });

        BtnSkorAldrettePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkorAldrettePascaAnestesi.setText("Skor Aldrette Pasca Anestesi");
        BtnSkorAldrettePascaAnestesi.setFocusPainted(false);
        BtnSkorAldrettePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkorAldrettePascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorAldrettePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorAldrettePascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorAldrettePascaAnestesi.setName("BtnSkorAldrettePascaAnestesi"); 
        BtnSkorAldrettePascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorAldrettePascaAnestesi.setRoundRect(false);
        BtnSkorAldrettePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorAldrettePascaAnestesiActionPerformed(evt);
            }
        });

        BtnSkorStewardPascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkorStewardPascaAnestesi.setText("Skor Steward Pasca Anestesi");
        BtnSkorStewardPascaAnestesi.setFocusPainted(false);
        BtnSkorStewardPascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkorStewardPascaAnestesi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkorStewardPascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkorStewardPascaAnestesi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkorStewardPascaAnestesi.setName("BtnSkorStewardPascaAnestesi"); 
        BtnSkorStewardPascaAnestesi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkorStewardPascaAnestesi.setRoundRect(false);
        BtnSkorStewardPascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkorStewardPascaAnestesiActionPerformed(evt);
            }
        });

        BtnMedicalCheckUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnMedicalCheckUp.setText("Medical Check Up");
        BtnMedicalCheckUp.setFocusPainted(false);
        BtnMedicalCheckUp.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnMedicalCheckUp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMedicalCheckUp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMedicalCheckUp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMedicalCheckUp.setName("BtnMedicalCheckUp"); 
        BtnMedicalCheckUp.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMedicalCheckUp.setRoundRect(false);
        BtnMedicalCheckUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMedicalCheckUpActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhDewasa.setText("Lanjutan Risiko Jatuh Dewasa");
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setName("BtnPenilaianLanjutanRisikoJatuhDewasa"); 
        BtnPenilaianLanjutanRisikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhDewasa.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhAnak.setText("Lanjutan Risiko Jatuh Anak");
        BtnPenilaianLanjutanRisikoJatuhAnak.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhAnak.setName("BtnPenilaianLanjutanRisikoJatuhAnak"); 
        BtnPenilaianLanjutanRisikoJatuhAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhAnak.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhLansia.setText("Lanjutan Risiko Jatuh Lansia");
        BtnPenilaianLanjutanRisikoJatuhLansia.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhLansia.setName("BtnPenilaianLanjutanRisikoJatuhLansia"); 
        BtnPenilaianLanjutanRisikoJatuhLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhLansia.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setText("Lanjutan Risiko Jatuh Neonatus");
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setName("BtnPenilaianLanjutanRisikoJatuhNeonatus"); 
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setText("Lanjutan Risiko Jatuh Geriatri");
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setName("BtnPenilaianLanjutanRisikoJatuhGeriatri"); 
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setText("Lanjutan Risiko Jatuh Psikiatri");
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setFocusPainted(false);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setName("BtnPenilaianLanjutanRisikoJatuhPsikiatri"); 
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setRoundRect(false);
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed(evt);
            }
        });

        BtnPenilaianLanjutanSkriningFungsional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianLanjutanSkriningFungsional.setText("Lanjutan Skrining Fungsional");
        BtnPenilaianLanjutanSkriningFungsional.setFocusPainted(false);
        BtnPenilaianLanjutanSkriningFungsional.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianLanjutanSkriningFungsional.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianLanjutanSkriningFungsional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianLanjutanSkriningFungsional.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianLanjutanSkriningFungsional.setName("BtnPenilaianLanjutanSkriningFungsional"); 
        BtnPenilaianLanjutanSkriningFungsional.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianLanjutanSkriningFungsional.setRoundRect(false);
        BtnPenilaianLanjutanSkriningFungsional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianLanjutanSkriningFungsionalActionPerformed(evt);
            }
        });

        BtnHasilPemeriksaanUSG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnHasilPemeriksaanUSG.setText("Hasil USG Kandungan");
        BtnHasilPemeriksaanUSG.setFocusPainted(false);
        BtnHasilPemeriksaanUSG.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanUSG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSG.setName("BtnHasilPemeriksaanUSG"); 
        BtnHasilPemeriksaanUSG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSG.setRoundRect(false);
        BtnHasilPemeriksaanUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilPemeriksaanUSGActionPerformed(evt);
            }
        });

        BtnDokumentasiESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnDokumentasiESWL.setText("Dokumentasi Tindakan ESWL");
        BtnDokumentasiESWL.setFocusPainted(false);
        BtnDokumentasiESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnDokumentasiESWL.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDokumentasiESWL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDokumentasiESWL.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDokumentasiESWL.setName("BtnDokumentasiESWL"); 
        BtnDokumentasiESWL.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnDokumentasiESWL.setRoundRect(false);
        BtnDokumentasiESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokumentasiESWLActionPerformed(evt);
            }
        });

        BtnCatatanPersalinanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanPersalinanan.setText("Catatan Persalinan");
        BtnCatatanPersalinanan.setFocusPainted(false);
        BtnCatatanPersalinanan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanPersalinanan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanPersalinanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanPersalinanan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanPersalinanan.setName("BtnCatatanPersalinanan"); 
        BtnCatatanPersalinanan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanPersalinanan.setRoundRect(false);
        BtnCatatanPersalinanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanPersalinananActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningNutrisiDewasa.setText("Skrining Nutrisi Dewasa");
        BtnSkriningNutrisiDewasa.setFocusPainted(false);
        BtnSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningNutrisiDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiDewasa.setName("BtnSkriningNutrisiDewasa"); 
        BtnSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiDewasa.setRoundRect(false);
        BtnSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiDewasaActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningNutrisiLansia.setText("Skrining Nutrisi Lansia");
        BtnSkriningNutrisiLansia.setFocusPainted(false);
        BtnSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningNutrisiLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiLansia.setName("BtnSkriningNutrisiLansia"); 
        BtnSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiLansia.setRoundRect(false);
        BtnSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiLansiaActionPerformed(evt);
            }
        });

        BtnSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningNutrisiAnak.setText("Skrining Nutrisi Anak");
        BtnSkriningNutrisiAnak.setFocusPainted(false);
        BtnSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningNutrisiAnak.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningNutrisiAnak.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningNutrisiAnak.setName("BtnSkriningNutrisiAnak"); 
        BtnSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningNutrisiAnak.setRoundRect(false);
        BtnSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningNutrisiAnakActionPerformed(evt);
            }
        });

        BtnSkriningGiziLanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        BtnSkriningGiziLanjut.setFocusPainted(false);
        BtnSkriningGiziLanjut.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningGiziLanjut.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziLanjut.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziLanjut.setName("BtnSkriningGiziLanjut"); 
        BtnSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziLanjut.setRoundRect(false);
        BtnSkriningGiziLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSkriningGiziLanjutActionPerformed(evt);
            }
        });

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); 
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); 
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });

        BtnCatatanADIMEGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnCatatanADIMEGizi.setText("Catatan ADIME Gizi");
        BtnCatatanADIMEGizi.setFocusPainted(false);
        BtnCatatanADIMEGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnCatatanADIMEGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatanADIMEGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatanADIMEGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatanADIMEGizi.setName("BtnCatatanADIMEGizi"); 
        BtnCatatanADIMEGizi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCatatanADIMEGizi.setRoundRect(false);
        BtnCatatanADIMEGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanADIMEGiziActionPerformed(evt);
            }
        });

        BtnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnKonselingFarmasi.setText("Konseling Farmasi");
        BtnKonselingFarmasi.setFocusPainted(false);
        BtnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnKonselingFarmasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKonselingFarmasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKonselingFarmasi.setName("BtnKonselingFarmasi"); 
        BtnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKonselingFarmasi.setRoundRect(false);
        BtnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonselingFarmasiActionPerformed(evt);
            }
        });

        BtnInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnInformasiObat.setText("Informasi Obat");
        BtnInformasiObat.setFocusPainted(false);
        BtnInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnInformasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInformasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInformasiObat.setName("BtnInformasiObat"); 
        BtnInformasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnInformasiObat.setRoundRect(false);
        BtnInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInformasiObatActionPerformed(evt);
            }
        });

        BtnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        BtnRekonsiliasiObat.setFocusPainted(false);
        BtnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnRekonsiliasiObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRekonsiliasiObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRekonsiliasiObat.setName("BtnRekonsiliasiObat"); 
        BtnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnRekonsiliasiObat.setRoundRect(false);
        BtnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekonsiliasiObatActionPerformed(evt);
            }
        });

        BtnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnTransferAntarRuang.setText("Transfer Antar Ruang");
        BtnTransferAntarRuang.setFocusPainted(false);
        BtnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnTransferAntarRuang.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnTransferAntarRuang.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnTransferAntarRuang.setName("BtnTransferAntarRuang"); 
        BtnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnTransferAntarRuang.setRoundRect(false);
        BtnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTransferAntarRuangActionPerformed(evt);
            }
        });

        BtnEdukasiPasienKeluarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnEdukasiPasienKeluarga.setText("Edukasi Pasien & Keluarga");
        BtnEdukasiPasienKeluarga.setFocusPainted(false);
        BtnEdukasiPasienKeluarga.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnEdukasiPasienKeluarga.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnEdukasiPasienKeluarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEdukasiPasienKeluarga.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnEdukasiPasienKeluarga.setName("BtnEdukasiPasienKeluarga"); 
        BtnEdukasiPasienKeluarga.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnEdukasiPasienKeluarga.setRoundRect(false);
        BtnEdukasiPasienKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdukasiPasienKeluargaActionPerformed(evt);
            }
        });

        BtnPengkajianRestrain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPengkajianRestrain.setText("Pengkajian Restrain");
        BtnPengkajianRestrain.setFocusPainted(false);
        BtnPengkajianRestrain.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPengkajianRestrain.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPengkajianRestrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPengkajianRestrain.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPengkajianRestrain.setName("BtnPengkajianRestrain"); 
        BtnPengkajianRestrain.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPengkajianRestrain.setRoundRect(false);
        BtnPengkajianRestrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPengkajianRestrainActionPerformed(evt);
            }
        });

        BtnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPasienTerminal.setText("Pengkajian Pasien Terminal");
        BtnPenilaianPasienTerminal.setFocusPainted(false);
        BtnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPasienTerminal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienTerminal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienTerminal.setName("BtnPenilaianPasienTerminal"); 
        BtnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienTerminal.setRoundRect(false);
        BtnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienTerminalActionPerformed(evt);
            }
        });

        BtnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianKorbanKekerasan.setText("Pengkajian Korban Kekerasan");
        BtnPenilaianKorbanKekerasan.setFocusPainted(false);
        BtnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianKorbanKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianKorbanKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianKorbanKekerasan.setName("BtnPenilaianKorbanKekerasan"); 
        BtnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianKorbanKekerasan.setRoundRect(false);
        BtnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });

        BtnPenilaianPasienPenyakitMenular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPasienPenyakitMenular.setText("Pasien Penyakit Menular");
        BtnPenilaianPasienPenyakitMenular.setFocusPainted(false);
        BtnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPasienPenyakitMenular.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienPenyakitMenular.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienPenyakitMenular.setName("BtnPenilaianPasienPenyakitMenular"); 
        BtnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienPenyakitMenular.setRoundRect(false);
        BtnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });

        BtnPenilaianPasienKeracunan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPasienKeracunan.setText("Pasien Keracunan");
        BtnPenilaianPasienKeracunan.setFocusPainted(false);
        BtnPenilaianPasienKeracunan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPasienKeracunan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPasienKeracunan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPasienKeracunan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPasienKeracunan.setName("BtnPenilaianPasienKeracunan"); 
        BtnPenilaianPasienKeracunan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPasienKeracunan.setRoundRect(false);
        BtnPenilaianPasienKeracunan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianPasienKeracunanActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianTambahanGeriatri.setText("Tambahan Pasien Geriatri");
        BtnPenilaianTambahanGeriatri.setFocusPainted(false);
        BtnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianTambahanGeriatri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanGeriatri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanGeriatri.setName("BtnPenilaianTambahanGeriatri"); 
        BtnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanGeriatri.setRoundRect(false);
        BtnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianTambahanBunuhDiri.setText("Tambahan Bunuh Diri");
        BtnPenilaianTambahanBunuhDiri.setFocusPainted(false);
        BtnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianTambahanBunuhDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanBunuhDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanBunuhDiri.setName("BtnPenilaianTambahanBunuhDiri"); 
        BtnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanBunuhDiri.setRoundRect(false);
        BtnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianTambahanPerilakuKekerasan.setText("Tambahan Perilaku Kekerasan");
        BtnPenilaianTambahanPerilakuKekerasan.setFocusPainted(false);
        BtnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianTambahanPerilakuKekerasan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanPerilakuKekerasan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanPerilakuKekerasan.setName("BtnPenilaianTambahanPerilakuKekerasan"); 
        BtnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianTambahanPerilakuKekerasan.setRoundRect(false);
        BtnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });

        BtnPenilaianTambahanMelarikanDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianTambahanMelarikanDiri.setText("Tambahan Melarikan Diri");
        BtnPenilaianTambahanMelarikanDiri.setFocusPainted(false);
        BtnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianTambahanMelarikanDiri.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianTambahanMelarikanDiri.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianTambahanMelarikanDiri.setName("BtnPenilaianTambahanMelarikanDiri"); 
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
            kd_pj=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
            kode_poli=Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
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
            }else if(TabRawat.getSelectedIndex()==8){
                Valid.pindah(evt,DTPTgl,KdDok3);
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
                Valid.pindah(evt,BtnSeekDokter,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,BtnSeekPetugas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,BtnSeekPetugas2,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,TEvaluasi,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,cmbFeto,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,TCavumDouglas,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==8){
                Valid.pindah(evt,Catatan,BtnBatal);
            }
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        ChkInput1.setSelected(true);
        ChkInput2.setSelected(true);
        ChkInput3.setSelected(true);
        isForm(); 
        isForm2();
        isForm3();
        isForm4();
        TSuhu.setText("");    
        TTensi.setText("");
        TKeluhan.setText("");
        TInstruksi.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TAlergi.setText("");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
        TRespirasi.setText("");
        TGCS.setText("");
        TindakLanjut.setText("");
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
        SpO2.setText("");
        TEvaluasi.setText("");
        TPortioInspekulo.setText("");
        TSondage.setText("");
        TPortioDalam.setText("");
        TBentuk.setText("");
        TCavumUteri.setText("");
        TUkuran.setText("");
        TAdnexaKanan.setText("");
        TAdnexaKiri.setText("");
        TCavumDouglas.setText("");
        Catatan.setText("");
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
                                    if(Sequel.queryutf("delete from rawat_jl_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
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
                                        if(Sequel.queryutf("delete from rawat_jl_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
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
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmdokter>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
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
                                    if(Sequel.queryutf("delete from rawat_jl_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
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
                                        if(Sequel.queryutf("delete from rawat_jl_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
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
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmperawat>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }     
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
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
                                    if(Sequel.queryutf("delete from rawat_jl_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
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
                                        if(Sequel.queryutf("delete from rawat_jl_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
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
                            if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }   
                            if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                             
                        }
                        if(ttljmdokter>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttljmperawat>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }     
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlkso>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                               
                        }
                        if(ttlmenejemen>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                                
                        }
                        if(ttljasasarana>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                              
                        }
                        if(ttlbhp>0){
                            if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }    
                            if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                                sukses=false;
                            }                            
                        }
                        if(sukses==true){
                            sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
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
                                Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
                                tabModePemeriksaan.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaan.getValueAt(i,4).toString()+" "+tbPemeriksaan.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(akses.getkode().equals(tbPemeriksaan.getValueAt(i,23).toString())){
                                        Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
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
                    LCount.setText(""+tabModePemeriksaan.getRowCount());
                }   break;
            case 4:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaanObstetri.getRowCount();i++){
                        if(tbPemeriksaanObstetri.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_obstetri_ralan where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                                tabModeObstetri.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanObstetri.getValueAt(i,4).toString()+" "+tbPemeriksaanObstetri.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    Sequel.queryu("delete from pemeriksaan_obstetri_ralan where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                            "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                            "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                                    tabModeObstetri.removeRow(i);
                                    i--;
                                }
                            }
                        }
                    }
                    LCount.setText(""+tabModeObstetri.getRowCount());
                }   break;
            case 5:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!");
                    TNoRw.requestFocus();                    
                }else {
                    for(i=0;i<tbPemeriksaanGinekologi.getRowCount();i++){
                        if(tbPemeriksaanGinekologi.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from pemeriksaan_ginekologi_ralan where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                        "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                        "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                                tabModeGinekologi.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanGinekologi.getValueAt(i,4).toString()+" "+tbPemeriksaanGinekologi.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    Sequel.queryu("delete from pemeriksaan_ginekologi_ralan where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                            "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                            "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                                    tabModeGinekologi.removeRow(i);
                                    i--;
                                }
                            }
                        }
                    }
                    LCount.setText(""+tabModeGinekologi.getRowCount());
                }   break;
            case 6:
                panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                panelDiagnosa1.hapus();
                LCount.setText(panelDiagnosa1.getRecord()+"");
                break;
            case 7:
                if(TabModeCatatan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbCatatan.getRowCount();i++){
                        if(tbCatatan.getValueAt(i,0).toString().equals("true")){
                            if(akses.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from catatan_perawatan where no_rawat='"+tbCatatan.getValueAt(i,1).toString()+
                                        "' and tanggal='"+tbCatatan.getValueAt(i,4).toString()+
                                        "' and jam='"+tbCatatan.getValueAt(i,5).toString()+
                                        "' and kd_dokter='"+tbCatatan.getValueAt(i,6).toString()+"' ");
                                TabModeCatatan.removeRow(i);
                                i--;
                            }else{
                                if(Sequel.cekTanggal48jam(tbCatatan.getValueAt(i,4).toString()+" "+tbCatatan.getValueAt(i,5).toString(),Sequel.ambiltanggalsekarang())==true){
                                    if(akses.getkode().equals(tbCatatan.getValueAt(i,6).toString())){
                                        Sequel.queryu("delete from catatan_perawatan where no_rawat='"+tbCatatan.getValueAt(i,1).toString()+
                                                "' and tanggal='"+tbCatatan.getValueAt(i,4).toString()+
                                                "' and jam='"+tbCatatan.getValueAt(i,5).toString()+
                                                "' and kd_dokter='"+tbCatatan.getValueAt(i,6).toString()+"' ");
                                        TabModeCatatan.removeRow(i);
                                        i--;
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                                    }
                                }
                            }
                        }
                    }
                    LCount.setText(""+TabModeCatatan.getRowCount());
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
                    
                    String tgl=" rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanDr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,dokter.nm_dokter,"+
                                    "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "dokter inner join rawat_jl_dr "+
                                    "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                                    "where "+tgl+" and "+
                                    "(rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%') order by rawat_jl_dr.no_rawat desc",param);
                    
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
                    
                    String tgl=" rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanPr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                            "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_pr.nip,petugas.nama,"+
                                    "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "petugas inner join rawat_jl_pr "+
                                    "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_pr.nip=petugas.nip where  "+
                                    tgl+"and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_pr.tgl_perawatan like '%"+TCari.getText().trim()+"%'  "+
                                            "order by rawat_jl_pr.no_rawat desc",param);
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
                    
                    String tgl=" rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanDrPr.jasper","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                            "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                    "jns_perawatan.nm_perawatan,rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,"+
                                    "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat " +
                                    "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                                    "dokter inner join rawat_jl_drpr inner join "+
                                    "petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                    "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                                    "and rawat_jl_drpr.nip=petugas.nip "+
                                    "where "+tgl+" and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' "+
                                            " order by rawat_jl_drpr.no_rawat desc",param);
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
                    
                    String tgl=" pemeriksaan_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanPemeriksaan.jasper","report","::[ Data Pemeriksaan Rawat Jalan ]::",
                            "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                            "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                            "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, " +
                            "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,"+
                            "pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "+
                            tgl+"and (pemeriksaan_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or pemeriksaan_ralan.alergi like '%"+TCari.getText().trim()+"%' or "+
                            "pemeriksaan_ralan.keluhan like '%"+TCari.getText().trim()+"%' or pemeriksaan_ralan.penilaian like '%"+TCari.getText().trim()+"%' or "+
                            "pemeriksaan_ralan.pemeriksaan like '%"+TCari.getText().trim()+"%' or pegawai.nama like '%"+TCari.getText().trim()+"%') "+
                            "order by pemeriksaan_ralan.no_rawat desc",param);
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
                    
                    String tgl=" pemeriksaan_obstetri_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanObstetri.jasper","report","::[ Data Pemeriksaan Obstetri Rawat Jalan ]::",
                            "select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                            "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                            "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                            "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                            "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "+
                            "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_obstetri_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_obstetri_ralan.tinggi_uteri like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ralan.janin like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_obstetri_ralan.letak like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_obstetri_ralan.no_rawat desc",param);
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
                    
                    String tgl=" pemeriksaan_ginekologi_ralan.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptJalanGinekologi.jasper","report","::[ Data Pemeriksaan Ginekologi Rawat Jalan ]::",
                            "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                            "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                            "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                            "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                            "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                            "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "+
                            "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                            tgl+"and pemeriksaan_ginekologi_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspeksi like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspeksi_vulva like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pemeriksaan_ginekologi_ralan.inspekulo_gine like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_ginekologi_ralan.no_rawat desc",param);
                }   
                break;
            case 6:
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.cetak();
                } 
                break;
            case 7:
                if(TabModeCatatan.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    BtnBatal.requestFocus();
                }else if(TabModeCatatan.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" catatan_perawatan.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptCatatanDokter.jasper","report","::[ Data Catatan Dokter ]::",
                            "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                            "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                            "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "and catatan_perawatan.kd_dokter=dokter.kd_dokter where  "+
                            tgl+" and catatan_perawatan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  "+
                            tgl+" and catatan_perawatan.catatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and catatan_perawatan.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+" and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                            "order by catatan_perawatan.no_rawat desc",param);
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
        try {
            i=JOptionPane.showConfirmDialog(null, "Mau skalian update status pasien sudah diperiksa ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(i==JOptionPane.YES_OPTION){
                if(Sequel.mengedittf("reg_periksa","no_rawat=?","stts=?",2,new String[]{"Sudah",TNoRw.getText()})==true){
                    Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Kembali',now(),'0000-00-00 00:00:00',now(),'0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Kembali',kembali=now()","no_rawat='"+TNoRw.getText()+"'");
                }
            }
        } catch (Exception e) {
        }
        dispose();
    }

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCariPasien.setText("");
        TampilkanData();
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
            TampilkanData();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if(TabRawatTindakanDokter.getSelectedIndex()==0){
                        tbTindakan.requestFocus();
                    }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
                        tbRawatDr.requestFocus();
                    }
                    break;
                case 1:
                    if(TabRawatTindakanPetugas.getSelectedIndex()==0){
                        tbTindakan2.requestFocus();
                    }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
                        tbRawatPr.requestFocus();
                    }
                    break;
                case 2:
                    if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
                        tbTindakan3.requestFocus();
                    }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
                        tbRawatDrPr.requestFocus();
                    }
                    break;
                case 3:
                    tbPemeriksaan.requestFocus();
                    break;
                case 4:
                    tbPemeriksaanObstetri.requestFocus();
                    break;
                case 5:
                    tbPemeriksaanGinekologi.requestFocus();
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TampilkanData();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true);
                TCari.setPreferredSize(new Dimension(207,23));            
                TabRawatTindakanDokterMouseClicked(null);
                break;
            case 1:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true); 
                TCari.setPreferredSize(new Dimension(207,23));
                TabRawatTindakanPetugasMouseClicked(null);
                break;
            case 2:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(true); 
                TCari.setPreferredSize(new Dimension(207,23));
                TabRawatTindakanDokterPetugasMouseClicked(null);
                break;
            case 3:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                runBackground(() ->tampilPemeriksaan());
                break;
            case 4:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                runBackground(() ->tampilPemeriksaanObstetri());
                break;
            case 5:
                BtnSimpan.setEnabled(akses.gettindakan_ralan());
                BtnHapus.setEnabled(akses.gettindakan_ralan());
                BtnEdit.setEnabled(akses.gettindakan_ralan());
                BtnPrint.setEnabled(akses.gettindakan_ralan());
                BtnTambahTindakan.setVisible(false); 
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                runBackground(() ->tampilPemeriksaanGinekologi());
                break;
            case 6:
                BtnSimpan.setEnabled(akses.getdiagnosa_pasien());
                BtnHapus.setEnabled(akses.getdiagnosa_pasien());
                BtnEdit.setEnabled(akses.getdiagnosa_pasien());
                BtnPrint.setEnabled(akses.getdiagnosa_pasien());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""), Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord()+"");
                } 
                break;
            case 7:
                BtnSimpan.setEnabled(akses.getcatatan_perawatan());
                BtnHapus.setEnabled(akses.getcatatan_perawatan());
                BtnEdit.setEnabled(akses.getcatatan_perawatan());
                BtnPrint.setEnabled(akses.getcatatan_perawatan());
                BtnTambahTindakan.setVisible(false);
                TCari.setPreferredSize(new Dimension(240,23));
                TCariPasien.setText(TNoRM.getText());
                if(akses.getcatatan_perawatan()==true){
                    runBackground(() ->tampilCatatan());
                } 
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

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

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,BtnSeekDokter);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDok.requestFocus();
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
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,BtnSeekPetugas);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        DlgCariPetugas petugas=new DlgCariPetugas(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){   
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    TPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    kdptg.requestFocus();
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
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            switch (TabRawat.getSelectedIndex()) {
                case 3:
                    if(KdPeg.getText().trim().equals("")||TPegawai.getText().trim().equals("")){
                            Valid.textKosong(KdPeg,"Dokter/Paramedis masih kosong...!!");
                    }else if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||(!TSuhu.getText().trim().equals(""))||
                            (!TTensi.getText().trim().equals(""))||(!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                            (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||(!TNadi.getText().trim().equals(""))||
                            (!TGCS.getText().trim().equals(""))||(!TindakLanjut.getText().trim().equals(""))||(!TPenilaian.getText().trim().equals(""))||
                            (!KdPeg.getText().trim().equals(""))||(!TPegawai.getText().trim().equals(""))||(!TInstruksi.getText().trim().equals(""))||
                            (!SpO2.getText().trim().equals(""))||(!TEvaluasi.getText().trim().equals(""))){
                        if(tbPemeriksaan.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("pemeriksaan_ralan","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                    "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                    "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                    "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                    "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                                    "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',"+
                                    "tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',spo2='"+SpO2.getText()+"',"+
                                    "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',"+
                                    "alergi='"+TAlergi.getText()+"',lingkar_perut='"+LingkarPerut.getText()+"',"+
                                    "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                    "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                    "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',"+
                                    "instruksi='"+TInstruksi.getText()+"',evaluasi='"+TEvaluasi.getText()+"',nip='"+KdPeg.getText()+"'")==true){
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
                                        tbPemeriksaan.setValueAt(LingkarPerut.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                        tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                        tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                        tbPemeriksaan.setValueAt(TInstruksi.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                        tbPemeriksaan.setValueAt(TEvaluasi.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                        tbPemeriksaan.setValueAt(KdPeg.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                        tbPemeriksaan.setValueAt(TPegawai.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                        tbPemeriksaan.setValueAt(Jabatan.getText(),tbPemeriksaan.getSelectedRow(), 25);
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");LingkarPerut.setText("");
                                        TindakLanjut.setText("");TPenilaian.setText("");TInstruksi.setText("");
                                        SpO2.setText("");TEvaluasi.setText("");
                                }   
                            }else{
                                if(akses.getkode().equals(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString())){
                                    if(Sequel.cekTanggal48jam(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+" "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                        if(TanggalRegistrasi.getText().equals("")){
                                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                        }
                                        if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                            if(Sequel.mengedittf("pemeriksaan_ralan","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                                "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                                "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                                                "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',"+
                                                "tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',spo2='"+SpO2.getText()+"',"+
                                                "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',"+
                                                "alergi='"+TAlergi.getText()+"',lingkar_perut='"+LingkarPerut.getText()+"',"+
                                                "tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                                "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',"+
                                                "instruksi='"+TInstruksi.getText()+"',evaluasi='"+TEvaluasi.getText()+"'")==true){
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
                                                    tbPemeriksaan.setValueAt(LingkarPerut.getText(),tbPemeriksaan.getSelectedRow(), 18);
                                                    tbPemeriksaan.setValueAt(TindakLanjut.getText(),tbPemeriksaan.getSelectedRow(), 19);
                                                    tbPemeriksaan.setValueAt(TPenilaian.getText(),tbPemeriksaan.getSelectedRow(), 20);
                                                    tbPemeriksaan.setValueAt(TInstruksi.getText(),tbPemeriksaan.getSelectedRow(), 21);
                                                    tbPemeriksaan.setValueAt(TEvaluasi.getText(),tbPemeriksaan.getSelectedRow(), 22);
                                                    tbPemeriksaan.setValueAt(KdPeg.getText(),tbPemeriksaan.getSelectedRow(), 23);
                                                    tbPemeriksaan.setValueAt(TPegawai.getText(),tbPemeriksaan.getSelectedRow(), 24);
                                                    tbPemeriksaan.setValueAt(Jabatan.getText(),tbPemeriksaan.getSelectedRow(), 25);
                                                    TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                                    TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                                    TPemeriksaan.setText("");TAlergi.setText("");LingkarPerut.setText("");
                                                    TindakLanjut.setText("");TPenilaian.setText("");TInstruksi.setText("");
                                                    SpO2.setText("");TEvaluasi.setText("");
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
                                if(Sequel.mengedittf("pemeriksaan_obstetri_ralan","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
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
                                        TTinggi_uteri.setText("");cmbJanin.setSelectedIndex(0);TLetak.setText("");cmbPanggul.setSelectedIndex(0);TDenyut.setText("");
                                        cmbKontraksi.setSelectedIndex(0);TKualitas_mnt.setText("");TKualitas_dtk.setText("");cmbFluksus.setSelectedIndex(0);
                                        cmbAlbus.setSelectedIndex(0);TVulva.setText("");TPortio.setText("");cmbDalam.setSelectedIndex(0);TTebal.setText("");
                                        cmbArah.setSelectedIndex(0);TPembukaan.setText("");TPenurunan.setText("");TDenominator.setText("");cmbKetuban.setSelectedIndex(0);
                                        cmbFeto.getSelectedItem().toString();
                                }
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),4)+" "+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        if(Sequel.mengedittf("pemeriksaan_obstetri_ralan","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
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
                                                TTinggi_uteri.setText("");cmbJanin.setSelectedIndex(0);TLetak.setText("");cmbPanggul.setSelectedIndex(0);TDenyut.setText("");
                                                cmbKontraksi.setSelectedIndex(0);TKualitas_mnt.setText("");TKualitas_dtk.setText("");cmbFluksus.setSelectedIndex(0);
                                                cmbAlbus.setSelectedIndex(0);TVulva.setText("");TPortio.setText("");cmbDalam.setSelectedIndex(0);TTebal.setText("");
                                                cmbArah.setSelectedIndex(0);TPembukaan.setText("");TPenurunan.setText("");TDenominator.setText("");cmbKetuban.setSelectedIndex(0);
                                                cmbFeto.getSelectedItem().toString();
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
                                if(Sequel.mengedittf("pemeriksaan_ginekologi_ralan","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
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
                                        TInspeksi.setText("");TInspeksiVulva.setText("");TInspekuloGine.setText("");
                                        cmbFluxusGine.setSelectedIndex(0);cmbFluorGine.setSelectedIndex(0); TVulvaInspekulo.setText("");
                                        TPortioInspekulo.setText(""); TSondage.setText(""); TPortioDalam.setText("");
                                        TBentuk.setText(""); TCavumUteri.setText(""); cmbMobilitas.setSelectedIndex(0);
                                        TUkuran.setText(""); cmbNyeriTekan.setSelectedIndex(0);
                                        TAdnexaKanan.setText(""); TAdnexaKiri.setText(""); TCavumDouglas.getText();
                                }  
                            }else{
                                if(Sequel.cekTanggal48jam(tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+" "+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                    if(TanggalRegistrasi.getText().equals("")){
                                        TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                    }
                                    if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                        if(Sequel.mengedittf("pemeriksaan_ginekologi_ralan","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
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
                                                TInspeksi.setText("");TInspeksiVulva.setText("");TInspekuloGine.setText("");
                                                cmbFluxusGine.setSelectedIndex(0);cmbFluorGine.setSelectedIndex(0); TVulvaInspekulo.setText("");
                                                TPortioInspekulo.setText(""); TSondage.setText(""); TPortioDalam.setText("");
                                                TBentuk.setText(""); TCavumUteri.setText(""); cmbMobilitas.setSelectedIndex(0);
                                                TUkuran.setText(""); cmbNyeriTekan.setSelectedIndex(0);
                                                TAdnexaKanan.setText(""); TAdnexaKiri.setText(""); TCavumDouglas.getText();
                                        }  
                                    }
                                }
                            }                          
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break; 
                case 7:
                    if(!Catatan.getText().trim().equals("")){
                        if(tbCatatan.getSelectedRow()>-1){
                            if(akses.getkode().equals("Admin Utama")){
                                if(Sequel.mengedittf("catatan_perawatan","no_rawat='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),1)+
                                    "' and tanggal='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4)+
                                    "' and jam='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5)+
                                    "' and kd_dokter='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6)+"'",
                                    "no_rawat='"+TNoRw.getText()+"',catatan='"+Catatan.getText()+"',"+
                                    "kd_dokter='"+KdDok3.getText()+"',tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                    "jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'")==true){
                                        tbCatatan.setValueAt(TNoRw.getText(),tbCatatan.getSelectedRow(), 1);
                                        tbCatatan.setValueAt(TNoRM.getText(),tbCatatan.getSelectedRow(), 2);
                                        tbCatatan.setValueAt(TPasien.getText(),tbCatatan.getSelectedRow(), 3);
                                        tbCatatan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbCatatan.getSelectedRow(), 4);
                                        tbCatatan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbCatatan.getSelectedRow(), 5);
                                        tbCatatan.setValueAt(KdDok3.getText(),tbCatatan.getSelectedRow(), 6);
                                        tbCatatan.setValueAt(TDokter3.getText(),tbCatatan.getSelectedRow(), 7);
                                        tbCatatan.setValueAt(Catatan.getText(),tbCatatan.getSelectedRow(), 8);
                                        Catatan.setText("");
                                } 
                            }else{
                                if(akses.getkode().equals(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6))){
                                    if(Sequel.cekTanggal48jam(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4)+" "+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5),Sequel.ambiltanggalsekarang())==true){
                                        if(TanggalRegistrasi.getText().equals("")){
                                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                                        }
                                        if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem())==true){
                                            if(Sequel.mengedittf("catatan_perawatan","no_rawat='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),1)+
                                                "' and tanggal='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4)+
                                                "' and jam='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5)+
                                                "' and kd_dokter='"+tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6)+"'",
                                                "no_rawat='"+TNoRw.getText()+"',catatan='"+Catatan.getText()+"',"+
                                                "kd_dokter='"+KdDok3.getText()+"',tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                                "jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'")==true){
                                                    tbCatatan.setValueAt(TNoRw.getText(),tbCatatan.getSelectedRow(), 1);
                                                    tbCatatan.setValueAt(TNoRM.getText(),tbCatatan.getSelectedRow(), 2);
                                                    tbCatatan.setValueAt(TPasien.getText(),tbCatatan.getSelectedRow(), 3);
                                                    tbCatatan.setValueAt(Valid.SetTgl(DTPTgl.getSelectedItem()+""),tbCatatan.getSelectedRow(), 4);
                                                    tbCatatan.setValueAt(cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbCatatan.getSelectedRow(), 5);
                                                    tbCatatan.setValueAt(KdDok3.getText(),tbCatatan.getSelectedRow(), 6);
                                                    tbCatatan.setValueAt(TDokter3.getText(),tbCatatan.getSelectedRow(), 7);
                                                    tbCatatan.setValueAt(Catatan.getText(),tbCatatan.getSelectedRow(), 8);
                                                    Catatan.setText("");
                                            } 
                                        }
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugas2ActionPerformed(null);
        }else{
            Valid.pindah(evt,KdDok2,BtnSeekPetugas2);
        }    
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        DlgCariPetugas petugas=new DlgCariPetugas(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){   
                    kdptg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    TPerawat2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    kdptg2.requestFocus();   
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
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter2ActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,kdptg2);
        }
    }//GEN-LAST:event_KdDok2KeyPressed

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter2ActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDok2.requestFocus();
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
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter2ActionPerformed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabModePemeriksaan.getRowCount()!=0){
            try {
                getDataPemeriksaan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnSeekDokter,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,TCari);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgRawatJalan");
        DlgCariPasien pasien=new DlgCariPasien(null,false);
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgRawatJalan")){
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
                if(akses.getform().equals("DlgRawatJalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
    }//GEN-LAST:event_btnPasienKeyPressed

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
        Valid.pindah(evt,TNoRw,cmbJanin);
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
        // TODO add your handling code here:
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
        // TODO add your handling code here:
        if(tabModeGinekologi.getRowCount()!=0) {
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)) {
                try {
                    getDataPemeriksaanGinekologi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanGinekologiKeyReleased

    private void TabRawatTindakanDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanDokterMouseClicked
        if(TabRawatTindakanDokter.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokter(); 
    }//GEN-LAST:event_TabRawatTindakanDokterMouseClicked

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanKeyPressed
        if(tbTindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan.getSelectedRow()>-1){
                            tbTindakan.setValueAt(true,tbTindakan.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakanKeyPressed

    private void tbTindakan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakan2KeyPressed
        if(tbTindakan2.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan2.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan2.getSelectedRow()>-1){
                            tbTindakan2.setValueAt(true,tbTindakan2.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakan2KeyPressed

    private void TabRawatTindakanPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanPetugasMouseClicked
        if(TabRawatTindakanPetugas.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananPetugas(); 
    }//GEN-LAST:event_TabRawatTindakanPetugasMouseClicked

    private void tbTindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakan3KeyPressed
        if(tbTindakan3.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan3.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan3.getSelectedRow()>-1){
                            tbTindakan3.setValueAt(true,tbTindakan3.getSelectedRow(),0);
                        }
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbTindakan3KeyPressed

    private void TabRawatTindakanDokterPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatTindakanDokterPetugasMouseClicked
        if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
            TCari.setText("");
        }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
            TCari.setText("");
            TCariPasien.setText(TNoRM.getText());
        }
        TCari.requestFocus();
        tampilkanPenangananDokterPetugas(); 
    }//GEN-LAST:event_TabRawatTindakanDokterPetugasMouseClicked

    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTindakanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanRalan perawatan=new DlgJnsPerawatanRalan(null,false);
        perawatan.emptTeks();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahTindakanActionPerformed

    private void BtnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if((Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0)&&(bypassranap==false)){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputResep();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        inputResep();
                    }
                }                     
            }            
        }
    }//GEN-LAST:event_BtnResepObatActionPerformed

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
            dlgrwinap.setNoRm2(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
            dlgrwinap.tampilPO3();
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
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());  
            }          
        }
    }//GEN-LAST:event_BtnPermintaanLabActionPerformed

    private void BtnPermintaanRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPermintaanRadActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }            
        }
    }//GEN-LAST:event_BtnPermintaanRadActionPerformed

    private void BtnInputObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInputObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                }
                if(jmlparsial>0){
                    inputObat();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        inputObat();
                    }
                }                    
            }            
        }
    }//GEN-LAST:event_BtnInputObatActionPerformed

    private void BtnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSKDPActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                SuratKontrol form=new SuratKontrol(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                form.setLocationRelativeTo(internalFrame1);      
                form.emptTeks();      
                form.setNoRm(TNoRM.getText(),TPasien.getText(), kode_poli,Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),KdDok.getText(),TDokter.getText());
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_BtnSKDPActionPerformed

    private void BtnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCopyResep daftar=new DlgCopyResep(null,false);
            daftar.isCek();
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),kd_pj,"ralan");
            daftar.tampil2();
            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            daftar.setLocationRelativeTo(internalFrame1);
            daftar.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } 
    }//GEN-LAST:event_BtnCopyResepActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKamarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{ 
                    inputKamar();
                }                     
            }            
        }
    }//GEN-LAST:event_BtnKamarActionPerformed

    private void BtnRujukInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukInternalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),this.getWidth()+20,this.getHeight()+20);
                    dlgrjk.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }                     
            }            
        }
    }//GEN-LAST:event_BtnRujukInternalActionPerformed

    private void BtnRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRujukKeluarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
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

    private void ChkInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput3ActionPerformed
        isForm4();
    }//GEN-LAST:event_ChkInput3ActionPerformed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah2(evt,KdDok3,BtnSimpan);
    }//GEN-LAST:event_CatatanKeyPressed

    private void tbCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCatatanMouseClicked
        if(TabModeCatatan.getRowCount()!=0){
            try {
                getDataCatatan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbCatatanMouseClicked

    private void tbCatatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbCatatanKeyReleased
        if(TabModeCatatan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataCatatan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbCatatanKeyReleased

    private void KdDok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter3ActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,BtnSeekDokter3);
        }
    }//GEN-LAST:event_KdDok3KeyPressed

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter3ActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDok3.requestFocus();
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
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter3ActionPerformed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        Valid.pindah2(evt,TKeluhan,TSuhu);
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TPemeriksaan,TTensi);
    }//GEN-LAST:event_TSuhuKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TBerat);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        Valid.pindah(evt,TBerat,TRespirasi);
    }//GEN-LAST:event_TTinggiKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        Valid.pindah(evt,TTinggi,TNadi);
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        Valid.pindah(evt,TTensi,TTinggi);
    }//GEN-LAST:event_TBeratKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,TRespirasi,SpO2);
    }//GEN-LAST:event_TNadiKeyPressed

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        Valid.pindah(evt,TNadi,cmbKesadaran);
    }//GEN-LAST:event_TGCSKeyPressed

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        Valid.pindah(evt,LingkarPerut,TPenilaian);
    }//GEN-LAST:event_TAlergiKeyPressed

    private void TPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPenilaianKeyPressed
        Valid.pindah2(evt,TAlergi,TindakLanjut);
    }//GEN-LAST:event_TPenilaianKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah2(evt,TPenilaian,TInstruksi);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void BtnTriaseIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTriaseIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{  
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTriaseIGD form=new RMTriaseIGD(null,false);
            form.isCek();
            form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTriaseIGDActionPerformed

    private void BtnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResumeActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataResumePasien resume=new RMDataResumePasien(null,false);
            resume.isCek();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnResumeActionPerformed

    private void cmbKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKesadaranKeyPressed
        Valid.pindah(evt,TGCS,LingkarPerut);
    }//GEN-LAST:event_cmbKesadaranKeyPressed

    private void KdPegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPegawaiActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,TKeluhan);
        }
    }//GEN-LAST:event_KdPegKeyPressed

    private void BtnSeekPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPegawaiActionPerformed
        DlgCariPegawai pegawai=new DlgCariPegawai(null,false); 
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){   
                    KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    TPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    Jabatan.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),3).toString());
                    KdPeg.requestFocus();                    
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
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnSeekPegawaiActionPerformed

    private void TInstruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInstruksiKeyPressed
        Valid.pindah2(evt,TindakLanjut,TEvaluasi);
    }//GEN-LAST:event_TInstruksiKeyPressed

    private void BtnResepLuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepLuarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap...!!!");
            }else {
                InventoryResepLuar resep=new InventoryResepLuar(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),KdDok.getText(),TDokter.getText(),TNoRM.getText()+" "+TPasien.getText());
                resep.isCek();
                resep.setVisible(true);                    
            }            
        }
    }//GEN-LAST:event_BtnResepLuarActionPerformed

    private void BtnAwalKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanActionPerformed

    private void BtnAwalKeperawatanGigiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanGigiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanGigi form=new RMPenilaianAwalKeperawatanGigi(null,false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanGigiActionPerformed

    private void BtnAwalKeperawatanKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanKebidanan form=new RMPenilaianAwalKeperawatanKebidanan(null,false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanKandunganActionPerformed

    private void BtnAwalKeperawatanAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanBayiAnak form=new RMPenilaianAwalKeperawatanBayiAnak(null,false);
            form.isCek();
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanAnakActionPerformed

    private void BtnAwalMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanDewasa form=new RMPenilaianAwalMedisRalanDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisActionPerformed

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

    private void BtnAwalMedisKandunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisKandunganActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanKandungan form=new RMPenilaianAwalMedisRalanKandungan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisKandunganActionPerformed

    private void BtnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalOperasiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);            
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?",kode_poli),"Ralan"); 
                form.setVisible(true);
            }           
        }
    }//GEN-LAST:event_BtnJadwalOperasiActionPerformed

    private void BtnAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisIGD form=new RMPenilaianAwalMedisIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisIGDActionPerformed

    private void BtnAwalMedisAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisAnakActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanAnak form=new RMPenilaianAwalMedisRalanAnak(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisAnakActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalFisioterapiActionPerformed

    private void BtnMedicalCheckUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMedicalCheckUpActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMMCU form=new RMMCU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMedicalCheckUpActionPerformed

    private void BtnUjiFungsiKFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUjiFungsiKFRActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMUjiFungsiKFR form=new RMUjiFungsiKFR(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnUjiFungsiKFRActionPerformed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,TNadi,TGCS);
    }//GEN-LAST:event_SpO2KeyPressed

    private void TEvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TEvaluasiKeyPressed
        Valid.pindah2(evt,TInstruksi,BtnSimpan);
    }//GEN-LAST:event_TEvaluasiKeyPressed

    private void BtnAwalKeperawatanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{ 
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanIGD form=new RMPenilaianAwalKeperawatanIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanIGDActionPerformed

    private void BtnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanObservasiIGDActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanObservasiIGD form=new RMDataCatatanObservasiIGD(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanObservasiIGDActionPerformed

    private void BtnAwalMedisTHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisTHTActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanTHT form=new RMPenilaianAwalMedisRalanTHT(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisTHTActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPsikologActionPerformed

    private void LingkarPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarPerutKeyPressed
        Valid.pindah(evt,cmbKesadaran,TAlergi); 
    }//GEN-LAST:event_LingkarPerutKeyPressed

    private void BtnAwalMedisPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPsikiatrik form=new RMPenilaianAwalMedisRalanPsikiatrik(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisPsikiatriActionPerformed

    private void TNoRwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TNoRwMouseClicked
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setLocationRelativeTo(internalFrame1);
                win.toFront();
            }
        }
    }//GEN-LAST:event_TNoRwMouseClicked

    private void BtnAwalMedisPenyakitDalamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisPenyakitDalamActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanPenyakitDalam form=new RMPenilaianAwalMedisRalanPenyakitDalam(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisPenyakitDalamActionPerformed

    private void BtnAwalMedisMataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisMataActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanMata form=new RMPenilaianAwalMedisRalanMata(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisMataActionPerformed

    private void BtnAwalMedisNeurologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisNeurologiActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanNeurologi form=new RMPenilaianAwalMedisRalanNeurologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisNeurologiActionPerformed

    private void BtnAwalMedisOrthopediActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisOrthopediActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanOrthopedi form=new RMPenilaianAwalMedisRalanOrthopedi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisOrthopediActionPerformed

    private void BtnAwalMedisBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisBedahActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanBedah form=new RMPenilaianAwalMedisRalanBedah(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisBedahActionPerformed

    private void BtnAwalKeperawatanPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanPsikiatri form=new RMPenilaianAwalKeperawatanRalanPsikiatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanPsikiatriActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPreAnestesiActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhDewasaActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhAnakActionPerformed

    private void BtnAwalMedisGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanGeriatri form=new RMPenilaianAwalMedisRalanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisGeriatriActionPerformed

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
            
            soapterakhir.setNoRM(TNoRM.getText(),KdPeg.getText(),"Ralan");
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

    private void BtnHasilPemeriksaanUSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilPemeriksaanUSGActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningNutrisiDewasaActionPerformed

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
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkriningGiziLanjutActionPerformed

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
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnMonitoringAsuhanGiziActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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

    private void BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhLansiaActionPerformed

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

    private void BtnEdukasiPasienKeluargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdukasiPasienKeluargaActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMEdukasiPasienKeluargaRawatJalan form=new RMEdukasiPasienKeluargaRawatJalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnEdukasiPasienKeluargaActionPerformed

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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianTambahanMelarikanDiriActionPerformed

    private void BtnAwalMedisBedahMulutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisBedahMulutActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanBedahMulut form=new RMPenilaianAwalMedisRalanBedahMulut(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisBedahMulutActionPerformed

    private void BtnPenilaianPasienKeracunanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianPasienKeracunanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianPasienKeracunan form=new RMPenilaianPasienKeracunan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianPasienKeracunanActionPerformed

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
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanADIMEGiziActionPerformed

    private void BtnAwalKeperawatanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalKeperawatanGeriatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalKeperawatanRalanGeriatri form=new RMPenilaianAwalKeperawatanRalanGeriatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalKeperawatanGeriatriActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukHCUActionPerformed

    private void BtnDokumentasiESWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokumentasiESWLActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnChecklistKriteriaMasukICUActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhNeonatusActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhGeriatriActionPerformed

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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPemantauanEWSNeonatusActionPerformed

    private void BtnAwalMedisKulitKelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisKulitKelaminActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanKulitDanKelamin form=new RMPenilaianAwalMedisRalanKulitDanKelamin(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisKulitKelaminActionPerformed

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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),"Rawat Jalan/IGD");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisHemodialisaActionPerformed

    private void BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanRisikoJatuhPsikiatriActionPerformed

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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPenilaianLanjutanSkriningFungsionalActionPerformed

    private void BtnAwalMedisRehabMedikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisRehabMedikActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanRehabMedik form=new RMPenilaianAwalMedisRalanRehabMedik(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisRehabMedikActionPerformed

    private void BtnAwalMedisIGDPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisIGDPsikiatriActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisIGDPsikiatri form=new RMPenilaianAwalMedisIGDPsikiatri(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisIGDPsikiatriActionPerformed

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

    private void BtnTemplatePemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplatePemeriksaanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else if(TPegawai.getText().trim().equals("")||KdPeg.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dokter pemberi asuhan...!!!");
            TCari.requestFocus();
        }else{
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
            }
            if(jmlparsial>0){    
                inputTemplate();
            }else{
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    inputTemplate();
                }
            } 
        }
    }//GEN-LAST:event_BtnTemplatePemeriksaanActionPerformed

    private void BtnAwalTerapiWicaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalTerapiWicaraActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianTerapiWicara form=new RMPenilaianTerapiWicara(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalTerapiWicaraActionPerformed

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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPengkajianRestrainActionPerformed

    private void BtnAwalMedisParuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAwalMedisParuActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanParu form=new RMPenilaianAwalMedisRalanParu(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalMedisParuActionPerformed

    private void BtnCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanKeperawatanActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataCatatanKeperawatanRalan form=new RMDataCatatanKeperawatanRalan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanKeperawatanActionPerformed

    private void BtnCatatanPersalinananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCatatanPersalinananActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCatatanPersalinananActionPerformed

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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSkorStewardPascaAnestesiActionPerformed

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
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
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
    
    private void BtnPenatalaksanaanTerapiOkupasiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenatalaksanaanTerapiOkupasi form=new RMPenatalaksanaanTerapiOkupasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            form.tampil2();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningMerokokUsiaRemajaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningMerokokUsiaSekolahRemaja form=new RMSkriningMerokokUsiaSekolahRemaja(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKekerasanPadaWanitaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKekerasanPadaPerempuan form=new RMSkriningKekerasanPadaPerempuan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningObesitasActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningObesitas form=new RMSkriningObesitas(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningRisikoKankerPayudaraActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningRisikoKankerPayudara form=new RMSkriningRisikoKankerPayudara(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningRisikoKankerParuActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningRisikoKankerParu form=new RMSkriningRisikoKankerParu(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKesehatanGigiMulutremajaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKesehatanGigiMulutRemaja form=new RMSkriningKesehatanGigiMulutRemaja(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningTBCActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningTBC form=new RMSkriningTBC(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningPUMAActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningPUMA form=new RMSkriningPUMA(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningAdiksiNikotinActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningAdiksiNikotin form=new RMSkriningAdiksiNikotin(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningThalassemiaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningTalasemia form=new RMSkriningTalasemia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningInstrumenSDQActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningInstrumenSDQ form=new RMSkriningInstrumenSDQ(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningInstrumenSRQActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningSRQ form=new RMSkriningSRQ(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKankerKolorektalActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKankerKolorektal form=new RMSkriningKankerKolorektal(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
    
    private void BtnSkriningDiabetesMelitusActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningDiabetesMelitus form=new RMSkriningDiabetesMelitus(null,false);
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
    
    private void BtnLayananKedokteranFisikRehabilitasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMLayananKedokteranFisikRehabilitasi form=new RMLayananKedokteranFisikRehabilitasi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKesehatanGigiMulutBalitaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKesehatanGigiMulutBalita form=new RMSkriningKesehatanGigiMulutBalita(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningAnemiaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningAnemia form=new RMSkriningAnemia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningHipertensiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningHipertensi form=new RMSkriningHipertensi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKesehatanPenglihatanActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKesehatanPenglihatan form=new RMSkriningKesehatanPenglihatan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningKesehatanGigiMulutDewasaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKesehatanGigiMulutDewasa form=new RMSkriningKesehatanGigiMulutDewasa(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningRisikoKankerServiksActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningRisikoKankerServiks form=new RMSkriningRisikoKankerServiks(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
    
    private void BtnSkriningKesehatanGigiMulutLansiaActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningKesehatanGigiMulutLansia form=new RMSkriningKesehatanGigiMulutLansia(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningIndraPendengaranActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningIndraPendengaran form=new RMSkriningIndraPendengaran(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
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
    
    private void BtnSkriningFrailtySyndromeActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningFrailtySyndrome form=new RMSkriningFrailtySyndrome(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnCatatanObservasiBayiActionPerformed(java.awt.event.ActionEvent evt) {
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
    
    private void BtnSkriningInstrumenACRSActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningInstrumenACRS form=new RMSkriningInstrumenACRS(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnChecklistKriteriaMasukNICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukNICU form=new RMChecklistKriteriaMasukNICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnChecklistKriteriaMasukPICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnChecklistKriteriaMasukICUActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMChecklistKriteriaMasukPICU form=new RMChecklistKriteriaMasukPICU(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningInstrumenMentalEmosionalActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningInstrumenMentalEmosional form=new RMSkriningInstrumenMentalEmosional(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningInstrumenAMTActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningInstrumenAMT form=new RMSkriningInstrumenAMT(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnSkriningPneumoniaSeverityIndexActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningPneumoniaSeverityIndex form=new RMSkriningPneumoniaSeverityIndex(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnAwalMedisJantungActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanJantung form=new RMPenilaianAwalMedisRalanJantung(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnAwalMedisUrologiActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMPenilaianAwalMedisRalanUrologi form=new RMPenilaianAwalMedisRalanUrologi(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void BtnHasilPemeriksaanTreadmillActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanTreadmill form=new RMHasilPemeriksaanTreadmill(null,false);
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
    
    private void BtnHasilPemeriksaanECHOPediatrikActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHasilPemeriksaanEchoPediatrik form=new RMHasilPemeriksaanEchoPediatrik(null,false);
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
    
    private void BtnSkriningCURB65ActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMSkriningCURB65 form=new RMSkriningCURB65(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void MnSOAPDokterActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPemeriksaan.getSelectedRow()>-1){
                if(!Sequel.CariDokter(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString()).equals("")){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),24).toString()+"\nID "+(finger.equals("")?
                             tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString():finger)+"\n"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString()+" "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()); 
                    Valid.MyReportqry("rptFormulirSOAPDokter.jasper","report","::[ Formulir SOAP Dokter ]::",
                            "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,"+
                            "pemeriksaan_ralan.keluhan,pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where reg_periksa.no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString()+"' and "+
                            "pemeriksaan_ralan.tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString()+"' and pemeriksaan_ralan.jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()+"'",param);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, bukan dokter...!!!");
                }
            }                
        }
    }
    
    private void MnSOAPPetugasActionPerformed(java.awt.event.ActionEvent evt) {
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPemeriksaan.getSelectedRow()>-1){
                if(!Sequel.CariPetugas(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString()).equals("")){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString());
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),24).toString()+"\nID "+(finger.equals("")?
                             tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),23).toString():finger)+"\n"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString()+" "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()); 
                    String kodedokter=Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString());
                    String namadokter=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kodedokter);
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokter);
                    param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+
                            tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString()+" "+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()); 
                    Valid.MyReportqry("rptFormulirSOAPPetugas.jasper","report","::[ Formulir SOAP Petugas ]::",
                            "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,"+
                            "pemeriksaan_ralan.keluhan,pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,pemeriksaan_ralan.nip,pegawai.nama,reg_periksa.kd_dokter,dokter.nm_dokter from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString()+"' and "+
                            "pemeriksaan_ralan.tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString()+"' and pemeriksaan_ralan.jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()+"'",param);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, bukan petugas...!!!");
                }
            }                
        }
    }
    
    private void BtnSkriningGiziKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSkriningGiziLanjutActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMDataSkriningGiziKehamilan form=new RMDataSkriningGiziKehamilan(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.tampil2();
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatJalan dialog = new DlgRawatJalan(new javax.swing.JFrame(), true);
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
    private widget.Button Btn5Soap;
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalKeperawatan;
    private widget.Button BtnAwalKeperawatanAnak;
    private widget.Button BtnAwalKeperawatanGeriatri;
    private widget.Button BtnAwalKeperawatanGigi;
    private widget.Button BtnAwalKeperawatanIGD;
    private widget.Button BtnAwalKeperawatanKandungan;
    private widget.Button BtnAwalKeperawatanPsikiatri;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedisAnak;
    private widget.Button BtnAwalMedisBedah;
    private widget.Button BtnAwalMedisBedahMulut;
    private widget.Button BtnAwalMedisGeriatri;
    private widget.Button BtnAwalMedisHemodialisa;
    private widget.Button BtnAwalMedisIGD;
    private widget.Button BtnAwalMedisIGDPsikiatri;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnAwalMedisKulitKelamin;
    private widget.Button BtnAwalMedisMata;
    private widget.Button BtnAwalMedisNeurologi;
    private widget.Button BtnAwalMedisOrthopedi;
    private widget.Button BtnAwalMedisParu;
    private widget.Button BtnAwalMedisPenyakitDalam;
    private widget.Button BtnAwalMedisPsikiatri;
    private widget.Button BtnAwalMedisRehabMedik;
    private widget.Button BtnAwalMedisTHT;
    private widget.Button BtnAwalTerapiWicara;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnCari;
    private widget.Button BtnCatatan;
    private widget.Button BtnCatatanADIMEGizi;
    private widget.Button BtnCatatanCekGDS;
    private widget.Button BtnCatatanKeperawatan;
    private widget.Button BtnCatatanObservasiIGD;
    private widget.Button BtnCatatanPersalinanan;
    private widget.Button BtnChecklistKriteriaMasukHCU;
    private widget.Button BtnChecklistKriteriaMasukICU;
    private widget.Button BtnChecklistPostOperasi;
    private widget.Button BtnChecklistPreOperasi;
    private widget.Button BtnCopyResep;
    private widget.Button BtnDokumentasiESWL;
    private widget.Button BtnEdit;
    private widget.Button BtnEdukasiPasienKeluarga;
    private widget.Button BtnHapus;
    private widget.Button BtnHasilPemeriksaanUSG;
    private widget.Button BtnInformasiObat;
    private widget.Button BtnInputObat;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnKamar;
    private widget.Button BtnKeluar;
    private widget.Button BtnKonselingFarmasi;
    private widget.Button BtnMedicalCheckUp;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnMonitoringReaksiTranfusi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnPemantauanEWSNeonatus;
    private widget.Button BtnPemantauanMEOWS;
    private widget.Button BtnPemantauanPEWSAnak;
    private widget.Button BtnPemantauanPEWSDewasa;
    private widget.Button BtnPengkajianRestrain;
    private widget.Button BtnPenilaianKorbanKekerasan;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhAnak;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhDewasa;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhGeriatri;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhLansia;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhNeonatus;
    private widget.Button BtnPenilaianLanjutanRisikoJatuhPsikiatri;
    private widget.Button BtnPenilaianLanjutanSkriningFungsional;
    private widget.Button BtnPenilaianPasienKeracunan;
    private widget.Button BtnPenilaianPasienPenyakitMenular;
    private widget.Button BtnPenilaianPasienTerminal;
    private widget.Button BtnPenilaianPreAnestesi;
    private widget.Button BtnPenilaianPreOperasi;
    private widget.Button BtnPenilaianTambahanBunuhDiri;
    private widget.Button BtnPenilaianTambahanGeriatri;
    private widget.Button BtnPenilaianTambahanMelarikanDiri;
    private widget.Button BtnPenilaianTambahanPerilakuKekerasan;
    private widget.Button BtnPenilaianUlangNyeri;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPrint;
    private widget.Button BtnRekonsiliasiObat;
    private widget.Button BtnResepLuar;
    private widget.Button BtnResepObat;
    private widget.Button BtnResume;
    private widget.Button BtnRiwayat;
    private widget.Button BtnRujukInternal;
    private widget.Button BtnRujukKeluar;
    private widget.Button BtnSKDP;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekDokter3;
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
    private widget.Button BtnTambahTindakan;
    private widget.Button BtnTemplatePemeriksaan;
    private widget.Button BtnTimeOutSebelumInsisi;
    private widget.Button BtnTransferAntarRuang;
    private widget.Button BtnTriaseIGD;
    private widget.Button BtnUjiFungsiKFR;
    private widget.TextArea Catatan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkInput2;
    private widget.CekBox ChkInput3;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Jabatan;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.TextBox KdDok3;
    private widget.TextBox KdPeg;
    private widget.Label LCount;
    private widget.TextBox LingkarPerut;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPanel PanelInput1;
    private javax.swing.JPanel PanelInput2;
    private javax.swing.JPanel PanelInput3;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
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
    private widget.TextBox TDokter3;
    private widget.TextArea TEvaluasi;
    private widget.TextBox TGCS;
    private widget.TextBox TInspeksi;
    private widget.TextBox TInspeksiVulva;
    private widget.TextBox TInspekuloGine;
    private widget.TextArea TInstruksi;
    private widget.TextArea TKeluhan;
    private widget.TextBox TKualitas_dtk;
    private widget.TextBox TKualitas_mnt;
    private widget.TextBox TLetak;
    private widget.TextBox TNadi;
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
    private widget.TextBox TUkuran;
    private widget.TextBox TVulva;
    private widget.TextBox TVulvaInspekulo;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawatTindakanDokter;
    private javax.swing.JTabbedPane TabRawatTindakanDokterPetugas;
    private javax.swing.JTabbedPane TabRawatTindakanPetugas;
    private widget.TextArea TindakLanjut;
    private widget.Button btnPasien;
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
    private widget.InternalFrame internalFrame8;
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
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
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
    private widget.Label jLabel6;
    private widget.Label jLabel60;
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
    private laporan.PanelDiagnosa panelDiagnosa1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelGlass15;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbCatatan;
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaanGinekologi;
    private widget.Table tbPemeriksaanObstetri;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    private widget.Table tbTindakan;
    private widget.Table tbTindakan2;
    private widget.Table tbTindakan3;
    private widget.TextBox TanggalRegistrasi;
    // End of variables declaration//GEN-END:variables
    private widget.Button BtnSkorBromagePascaAnestesi,BtnPenilaianPreInduksi,BtnHasilPemeriksaanUSGUrologi,BtnHasilPemeriksaanUSGGynecologi,BtnHasilPemeriksaanEKG,BtnPenatalaksanaanTerapiOkupasi,BtnPenilaianPsikolog,
                          BtnHasilPemeriksaanUSGNeonatus,BtnHasilEndoskopiFaringLaring,BtnHasilEndoskopiHidung,BtnHasilEndoskopiTelinga,BtnPenilaianPasienImunitasRendah,BtnCatatanKeseimbanganCairan,BtnCatatanObservasiCHBP,
                          BtnCatatanObservasiInduksiPersalinan,BtnPermintaanKonsultasiMedik,BtnSkriningMerokokUsiaRemaja,BtnSkriningKekerasanPadaWanita,BtnSkriningObesitas,BtnSkriningRisikoKankerPayudara,BtnSkriningRisikoKankerParu,
                          BtnSkriningKesehatanGigiMulutremaja,BtnSkriningTBC,BtnCatatanAnastesiSedasi,BtnSkriningPUMA,BtnSkriningAdiksiNikotin,BtnSkriningThalassemia,BtnSkriningInstrumenSDQ,BtnSkriningInstrumenSRQ,
                          BtnChecklistPemberianFibrinolitik,BtnSkriningKankerKolorektal,BtnPenilaianPsikologKlinis,BtnPenilaianDerajatDehidrasi,BtnHasilPemeriksaanECHO,BtnPenilaianBayiBaruLahir,BtnSkriningDiabetesMelitus,
                          BtnLaporanTindakan,BtnPelaksanaanInformasiEdukasi,BtnLayananKedokteranFisikRehabilitasi,BtnSkriningKesehatanGigiMulutBalita,BtnSkriningAnemia,BtnSkriningHipertensi,BtnSkriningKesehatanPenglihatan,
                          BtnCatatanObservasiHemodialisa,BtnSkriningKesehatanGigiMulutDewasa,BtnSkriningRisikoKankerServiks,BtnCatatanCairanHemodialisa,BtnSkriningKesehatanGigiMulutLansia,BtnSkriningIndraPendengaran,
                          BtnCatatanPengkajianPaskaOperasi,BtnSkriningFrailtySyndrome,BtnCatatanObservasiBayi,BtnChecklistKesiapanAnestesi,BtnHasilPemeriksaanSlitLamp,BtnHasilPemeriksaanOCT,BtnSkriningInstrumenACRS,
                          BtnChecklistKriteriaMasukNICU,BtnChecklistKriteriaMasukPICU,BtnSkriningInstrumenMentalEmosional,BtnSkriningInstrumenAMT,BtnSkriningPneumoniaSeverityIndex,BtnAwalMedisJantung,BtnAwalMedisUrologi,
                          BtnHasilPemeriksaanTreadmill,BtnHasilPemeriksaanECHOPediatrik,BtnSkriningCURB65,BtnSkriningGiziKehamilan;   
    private javax.swing.JPopupMenu PopupSOAP;
    private javax.swing.JMenuItem MnSOAPDokter,MnSOAPPetugas;
    
    private void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            ps=koneksi.prepareStatement("select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,rawat_jl_dr.biaya_rawat,rawat_jl_dr.kd_jenis_prw, " +
                   "rawat_jl_dr.tarif_tindakandr,rawat_jl_dr.kso,rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_jl_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_dr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_dr.kd_dokter like ? or dokter.nm_dokter like ? )")+
                   " order by rawat_jl_dr.no_rawat,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
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
            Valid.SetTgl(DTPTgl,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7).toString());
        }
    }

    private void tampilPr() {
        Valid.tabelKosong(tabModePr);
        try{  
            ps2=koneksi.prepareStatement("select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,"+
                   "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat,rawat_jl_pr.biaya_rawat,rawat_jl_pr.kd_jenis_prw, " +
                   "rawat_jl_pr.tarif_tindakanpr,rawat_jl_pr.kso,rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.menejemen "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                   "where rawat_jl_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_pr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_pr.nip like ? or petugas.nama like ?) ")+
                   "order by rawat_jl_pr.no_rawat,rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat desc"); 
            try{
                ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
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
            Valid.SetTgl(DTPTgl,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7).toString());
        }
    }
    
    private void tampilDrPr() {
        Valid.tabelKosong(tabModeDrPr);
        try{
            ps3=koneksi.prepareStatement("select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_drpr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,rawat_jl_drpr.biaya_rawat,rawat_jl_drpr.kd_jenis_prw, " +
                   "rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,rawat_jl_drpr.kso,rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.menejemen  "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                   "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                   "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                   "where rawat_jl_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                   (TCari.getText().trim().equals("")?"":"and (rawat_jl_drpr.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                   "jns_perawatan.nm_perawatan like ? or rawat_jl_drpr.kd_dokter like ? or dokter.nm_dokter like ? or "+
                   "rawat_jl_drpr.nip like ? or petugas.nama like ?)")+
                   " order by rawat_jl_drpr.no_rawat,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat desc");
            try{
                ps3.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
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
            Valid.SetTgl(DTPTgl,tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9).toString());
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
                    KdDok.setText(rs.getString("kd_dokter"));
                    TDokter.setText(Sequel.CariDokter(KdDok.getText()));
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
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText("");
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        KdDok2.setText(KdDok.getText());
        KdDok3.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText()); 
        TDokter3.setText(TDokter.getText()); 
        ChkInput.setSelected(true);
        isForm();
        ChkInput1.setSelected(true);
        isForm2();
        ChkInput2.setSelected(true);
        isForm3(); 
        ChkInput3.setSelected(true);
        isForm4();
        TabRawatMouseClicked(null);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,273));
            panelGlass12.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass12.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void isForm4(){
        if(ChkInput3.isSelected()==true){
            ChkInput3.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH,140));
            panelGlass15.setVisible(true);      
            ChkInput3.setVisible(true);
        }else if(ChkInput3.isSelected()==false){           
            ChkInput3.setVisible(false);            
            PanelInput3.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass15.setVisible(false);      
            ChkInput3.setVisible(true);
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
    
    public void isCek(){
        tinggi=0;
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        BtnTambahTindakan.setEnabled(akses.gettarif_ralan());    
        BtnResepObat.setVisible(akses.getresep_dokter());
        BtnCopyResep.setVisible(akses.getresep_dokter());
        BtnTemplatePemeriksaan.setEnabled(akses.gettemplate_pemeriksaan());
        if(akses.getresep_dokter()==true){
            tinggi=tinggi+48;
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
        BtnKamar.setVisible(akses.getkamar_inap());   
        if(akses.getkamar_inap()==true){
            tinggi=tinggi+24;
        }
        BtnRujukInternal.setVisible(akses.getrujukan_poli_internal());
        if(akses.getrujukan_poli_internal()==true){
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
        BtnTriaseIGD.setVisible(akses.getdata_triase_igd());  
        if(akses.getdata_triase_igd()==true){
            tinggi=tinggi+24;
        }
        BtnResume.setVisible(akses.getdata_resume_pasien());   
        if(akses.getdata_resume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnResepLuar.setVisible(akses.getresep_luar()); 
        if(akses.getresep_luar()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatan.setVisible(akses.getpenilaian_awal_keperawatan_ralan());  
        if(akses.getpenilaian_awal_keperawatan_ralan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanIGD.setVisible(akses.getpenilaian_awal_keperawatan_igd());  
        if(akses.getpenilaian_awal_keperawatan_igd()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanGigi.setVisible(akses.getpenilaian_awal_keperawatan_gigi());   
        if(akses.getpenilaian_awal_keperawatan_gigi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanKandungan.setVisible(akses.getpenilaian_awal_keperawatan_kebidanan()); 
        if(akses.getpenilaian_awal_keperawatan_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanAnak.setVisible(akses.getpenilaian_awal_keperawatan_anak());
        if(akses.getpenilaian_awal_keperawatan_anak()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanPsikiatri.setVisible(akses.getpenilaian_awal_keperawatan_psikiatri());
        if(akses.getpenilaian_awal_keperawatan_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedis.setVisible(akses.getpenilaian_awal_medis_ralan()); 
        if(akses.getpenilaian_awal_medis_ralan()==true){
            tinggi=tinggi+24;
        }      
        BtnAwalMedisKandungan.setVisible(akses.getpenilaian_awal_medis_ralan_kebidanan()); 
        if(akses.getpenilaian_awal_medis_ralan_kebidanan()==true){
            tinggi=tinggi+24;
        } 
        BtnRiwayat.setVisible(akses.getresume_pasien());
        Btn5Soap.setEnabled(akses.getresume_pasien());
        if(akses.getresume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnJadwalOperasi.setVisible(akses.getbooking_operasi());   
        if(akses.getbooking_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisIGD.setVisible(akses.getpenilaian_awal_medis_igd()); 
        if(akses.getpenilaian_awal_medis_igd()==true){
            tinggi=tinggi+24;
        }  
        BtnAwalMedisAnak.setVisible(akses.getpenilaian_awal_medis_ralan_anak()); 
        if(akses.getpenilaian_awal_medis_ralan_anak()==true){
            tinggi=tinggi+24;
        }
        BtnAwalFisioterapi.setVisible(akses.getpenilaian_fisioterapi()); 
        if(akses.getpenilaian_fisioterapi()==true){
            tinggi=tinggi+24;
        }
        BtnMedicalCheckUp.setVisible(akses.getpenilaian_mcu()); 
        if(akses.getpenilaian_mcu()==true){
            tinggi=tinggi+24;
        }
        BtnUjiFungsiKFR.setVisible(akses.getuji_fungsi_kfr()); 
        if(akses.getuji_fungsi_kfr()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiIGD.setVisible(akses.getcatatan_observasi_igd()); 
        if(akses.getcatatan_observasi_igd()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanObservasiBayi.setVisible(akses.getcatatan_observasi_bayi()); 
        if(akses.getcatatan_observasi_bayi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisTHT.setVisible(akses.getpenilaian_awal_medis_ralan_tht()); 
        if(akses.getpenilaian_awal_medis_ralan_tht()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisPsikiatri.setVisible(akses.getpenilaian_awal_medis_ralan_psikiatri()); 
        if(akses.getpenilaian_awal_medis_ralan_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisPenyakitDalam.setVisible(akses.getpenilaian_awal_medis_ralan_penyakit_dalam()); 
        if(akses.getpenilaian_awal_medis_ralan_penyakit_dalam()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisMata.setVisible(akses.getpenilaian_awal_medis_ralan_mata()); 
        if(akses.getpenilaian_awal_medis_ralan_mata()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisNeurologi.setVisible(akses.getpenilaian_awal_medis_ralan_neurologi()); 
        if(akses.getpenilaian_awal_medis_ralan_neurologi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisOrthopedi.setVisible(akses.getpenilaian_awal_medis_ralan_orthopedi()); 
        if(akses.getpenilaian_awal_medis_ralan_orthopedi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisBedah.setVisible(akses.getpenilaian_awal_medis_ralan_bedah()); 
        if(akses.getpenilaian_awal_medis_ralan_bedah()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisJantung.setVisible(akses.getpenilaian_awal_medis_ralan_jantung()); 
        if(akses.getpenilaian_awal_medis_ralan_jantung()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisUrologi.setVisible(akses.getpenilaian_awal_medis_ralan_urologi()); 
        if(akses.getpenilaian_awal_medis_ralan_urologi()==true){
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
        BtnPenilaianBayiBaruLahir.setVisible(akses.getpenilaian_bayi_baru_lahir()); 
        if(akses.getpenilaian_bayi_baru_lahir()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanPEWSAnak.setVisible(akses.getpemantauan_pews_anak()); 
        if(akses.getpenilaian_psikologi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreOperasi.setVisible(akses.getpenilaian_pre_operasi()); 
        if(akses.getpenilaian_pre_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanPengkajianPaskaOperasi.setVisible(akses.getcatatan_pengkajian_paska_operasi()); 
        if(akses.getcatatan_pengkajian_paska_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPreAnestesi.setVisible(akses.getpenilaian_pre_anestesi()); 
        if(akses.getpenilaian_pre_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKesiapanAnestesi.setVisible(akses.getchecklist_kesiapan_anestesi()); 
        if(akses.getchecklist_kesiapan_anestesi()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhDewasa.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhAnak.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_anak()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_anak()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisGeriatri.setVisible(akses.getpenilaian_awal_medis_ralan_geriatri());
        if(akses.getpenilaian_awal_medis_ralan_geriatri()==true){
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
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if(akses.getasuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if(akses.getmonitoring_asuhan_gizi()==true){
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
        BtnPenilaianLanjutanRisikoJatuhLansia.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_lansia()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienPenyakitMenular.setVisible(akses.getpenilaian_pasien_penyakit_menular()); 
        if(akses.getpenilaian_pasien_penyakit_menular()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienImunitasRendah.setVisible(akses.getpenilaian_pasien_imunitas_rendah()); 
        if(akses.getpenilaian_pasien_imunitas_rendah()==true){
            tinggi=tinggi+24;
        }
        BtnEdukasiPasienKeluarga.setVisible(akses.getedukasi_pasien_keluarga_rj()); 
        if(akses.getedukasi_pasien_keluarga_rj()==true){
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
        BtnAwalMedisBedahMulut.setVisible(akses.getpenilaian_awal_medis_ralan_bedah_mulut()); 
        if(akses.getpenilaian_awal_medis_ralan_bedah_mulut()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianPasienKeracunan.setVisible(akses.getpenilaian_pasien_keracunan()); 
        if(akses.getpenilaian_pasien_keracunan()==true){
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
        BtnAwalKeperawatanGeriatri.setVisible(akses.getpenilaian_awal_keperawatan_ralan_geriatri()); 
        if(akses.getpenilaian_awal_keperawatan_ralan_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukHCU.setVisible(akses.getchecklist_kriteria_masuk_hcu()); 
        if(akses.getchecklist_kriteria_masuk_hcu()==true){
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
        BtnPenilaianLanjutanRisikoJatuhNeonatus.setVisible(akses.getpenilaian_risiko_jatuh_neonatus()); 
        if(akses.getpenilaian_risiko_jatuh_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhGeriatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri()==true){
            tinggi=tinggi+24;
        }
        BtnPemantauanEWSNeonatus.setVisible(akses.getpemantauan_ews_neonatus()); 
        if(akses.getpemantauan_ews_neonatus()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisKulitKelamin.setVisible(akses.getpenilaian_awal_medis_ralan_kulit_kelamin()); 
        if(akses.getpenilaian_awal_medis_ralan_kulit_kelamin()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisHemodialisa.setVisible(akses.getpenilaian_medis_ralan_hemodialisa()); 
        if(akses.getpenilaian_medis_ralan_hemodialisa()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanRisikoJatuhPsikiatri.setVisible(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()); 
        if(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianLanjutanSkriningFungsional.setVisible(akses.getpenilaian_lanjutan_skrining_fungsional()); 
        if(akses.getpenilaian_lanjutan_skrining_fungsional()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisRehabMedik.setVisible(akses.getpenilaian_medis_ralan_rehab_medik()); 
        if(akses.getpenilaian_medis_ralan_rehab_medik()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisIGDPsikiatri.setVisible(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri()); 
        if(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri()==true){
            tinggi=tinggi+24;
        }
        BtnPenilaianUlangNyeri.setVisible(akses.getpenilaian_ulang_nyeri()); 
        if(akses.getpenilaian_ulang_nyeri()==true){
            tinggi=tinggi+24;
        }
        BtnAwalTerapiWicara.setVisible(akses.getpenilaian_terapi_wicara()); 
        if(akses.getpenilaian_terapi_wicara()==true){
            tinggi=tinggi+24;
        }
        BtnPengkajianRestrain.setVisible(akses.getpengkajian_restrain()); 
        if(akses.getpengkajian_restrain()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisParu.setVisible(akses.getpenilaian_awal_medis_ralan_paru()); 
        if(akses.getpenilaian_awal_medis_ralan_paru()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanKeperawatan.setVisible(akses.getcatatan_keperawatan_ralan()); 
        if(akses.getcatatan_keperawatan_ralan()==true){
            tinggi=tinggi+24;
        }
        BtnCatatanPersalinanan.setVisible(akses.getcatatan_persalinan()); 
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
        BtnHasilPemeriksaanEKG.setVisible(akses.gethasil_pemeriksaan_ekg()); 
        if(akses.gethasil_pemeriksaan_ekg()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanTreadmill.setVisible(akses.gethasil_pemeriksaan_treadmill()); 
        if(akses.gethasil_pemeriksaan_treadmill()==true){
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
        BtnHasilPemeriksaanECHO.setVisible(akses.gethasil_pemeriksaan_echo()); 
        if(akses.gethasil_pemeriksaan_echo()==true){
            tinggi=tinggi+24;
        }
        BtnHasilPemeriksaanECHOPediatrik.setVisible(akses.gethasil_pemeriksaan_echo_pediatrik()); 
        if(akses.gethasil_pemeriksaan_echo_pediatrik()==true){
            tinggi=tinggi+24;
        }
        BtnPenatalaksanaanTerapiOkupasi.setVisible(akses.getpenatalaksanaan_terapi_okupasi()); 
        if(akses.getpenatalaksanaan_terapi_okupasi()==true){
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
        BtnSkriningMerokokUsiaRemaja.setVisible(akses.getskrining_perilaku_merokok_sekolah_remaja());   
        if(akses.getskrining_perilaku_merokok_sekolah_remaja()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningKekerasanPadaWanita.setVisible(akses.getskrining_kekerasan_pada_perempuan());   
        if(akses.getskrining_kekerasan_pada_perempuan()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningObesitas.setVisible(akses.getskrining_obesitas());   
        if(akses.getskrining_obesitas()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningRisikoKankerPayudara.setVisible(akses.getskrining_risiko_kanker_payudara());   
        if(akses.getskrining_risiko_kanker_payudara()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningRisikoKankerParu.setVisible(akses.getskrining_risiko_kanker_paru());   
        if(akses.getskrining_risiko_kanker_paru()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningKesehatanGigiMulutremaja.setVisible(akses.getskrining_kesehatan_gigi_mulut_remaja());   
        if(akses.getskrining_kesehatan_gigi_mulut_remaja()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningKesehatanGigiMulutBalita.setVisible(akses.getskrining_kesehatan_gigi_mulut_balita());   
        if(akses.getskrining_kesehatan_gigi_mulut_balita()==true){
            tinggi=tinggi+24;
        }
        
        BtnSkriningKesehatanGigiMulutLansia.setVisible(akses.getskrining_kesehatan_gigi_mulut_lansia());   
        if(akses.getskrining_kesehatan_gigi_mulut_lansia()==true){
            tinggi=tinggi+24;
        }
        
        BtnSkriningKesehatanGigiMulutDewasa.setVisible(akses.getskrining_kesehatan_gigi_mulut_dewasa());   
        if(akses.getskrining_kesehatan_gigi_mulut_dewasa()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningRisikoKankerServiks.setVisible(akses.getskrining_risiko_kanker_serviks());   
        if(akses.getskrining_risiko_kanker_serviks()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningAnemia.setVisible(akses.getskrining_anemia());   
        if(akses.getskrining_anemia()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningHipertensi.setVisible(akses.getskrining_hipertensi());   
        if(akses.getskrining_hipertensi()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningKesehatanPenglihatan.setVisible(akses.getskrining_kesehatan_penglihatan());   
        if(akses.getskrining_kesehatan_penglihatan()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningIndraPendengaran.setVisible(akses.getskrining_indra_pendengaran());   
        if(akses.getskrining_indra_pendengaran()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningFrailtySyndrome.setVisible(akses.getskrining_frailty_syndrome());   
        if(akses.getskrining_frailty_syndrome()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningTBC.setVisible(akses.getskrining_tbc());   
        if(akses.getskrining_tbc()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningPUMA.setVisible(akses.getskrining_puma());   
        if(akses.getskrining_puma()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningAdiksiNikotin.setVisible(akses.getskrining_adiksi_nikotin());   
        if(akses.getskrining_adiksi_nikotin()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningThalassemia.setVisible(akses.getskrining_thalassemia());   
        if(akses.getskrining_thalassemia()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningInstrumenSDQ.setVisible(akses.getskrining_instrumen_sdq());   
        if(akses.getskrining_instrumen_sdq()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningInstrumenSRQ.setVisible(akses.getskrining_instrumen_srq());   
        if(akses.getskrining_instrumen_srq()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningInstrumenACRS.setVisible(akses.getskrining_instrumen_acrs());   
        if(akses.getskrining_instrumen_acrs()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningInstrumenMentalEmosional.setVisible(akses.getskrining_instrumen_mental_emosional());   
        if(akses.getskrining_instrumen_mental_emosional()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningInstrumenAMT.setVisible(akses.getskrining_instrumen_amt());   
        if(akses.getskrining_instrumen_amt()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningPneumoniaSeverityIndex.setVisible(akses.getskrining_pneumonia_severity_index());   
        if(akses.getskrining_pneumonia_severity_index()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningCURB65.setVisible(akses.getskrining_curb65());   
        if(akses.getskrining_curb65()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningGiziKehamilan.setVisible(akses.getskrining_gizi_kehamilan());   
        if(akses.getskrining_gizi_kehamilan()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningKankerKolorektal.setVisible(akses.getskrining_kanker_kolorektal());   
        if(akses.getskrining_kanker_kolorektal()==true){
            tinggi=tinggi+24;
        }
        BtnSkriningDiabetesMelitus.setVisible(akses.getskrining_diabetes_melitus());   
        if(akses.getskrining_diabetes_melitus()==true){
            tinggi=tinggi+24;
        }
        BtnLaporanTindakan.setVisible(akses.getlaporan_tindakan());   
        if(akses.getlaporan_tindakan()==true){
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
        BtnLayananKedokteranFisikRehabilitasi.setVisible(akses.getlayanan_kedokteran_fisik_rehabilitasi());   
        if(akses.getlayanan_kedokteran_fisik_rehabilitasi()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukNICU.setVisible(akses.getkriteria_masuk_nicu()); 
        if(akses.getkriteria_masuk_nicu()==true){
            tinggi=tinggi+24;
        }
        BtnChecklistKriteriaMasukPICU.setVisible(akses.getkriteria_masuk_picu()); 
        if(akses.getkriteria_masuk_picu()==true){
            tinggi=tinggi+24;
        }
        FormMenu.setPreferredSize(new Dimension(195,(tinggi+10)));
        TCari.setPreferredSize(new Dimension(207,23));
        
        if(akses.getjml2()>=1){
            KdPeg.setText(akses.getkode());
            TPegawai.setText(Sequel.CariPegawai(KdPeg.getText()));
            Jabatan.setText(Sequel.CariJabatanPegawai(KdPeg.getText()));
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

    private void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try{  
            ps4=koneksi.prepareStatement("select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                    "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                    "pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, " +
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,"+
                    "pemeriksaan_ralan.penilaian,pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn "+
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_ralan on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where  "+
                    "pemeriksaan_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "pemeriksaan_ralan.alergi like ? or pemeriksaan_ralan.keluhan like ? or pemeriksaan_ralan.penilaian like ? or "+
                    "pemeriksaan_ralan.pemeriksaan like ? or pegawai.nama like ?) ")+"order by pemeriksaan_ralan.no_rawat,pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat desc"); 
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
                        rs.getString(24),rs.getString(25)
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
    
    private void tampilCatatan() {
        Valid.tabelKosong(TabModeCatatan);
        try{  
            ps4=koneksi.prepareStatement("select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                    "catatan_perawatan.catatan from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join catatan_perawatan on catatan_perawatan.no_rawat=reg_periksa.no_rawat "+
                    "inner join dokter on catatan_perawatan.kd_dokter=dokter.kd_dokter "+
                    "where catatan_perawatan.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (catatan_perawatan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "+
                    "catatan_perawatan.catatan like ? or catatan_perawatan.kd_dokter like ? or dokter.nm_dokter like ?) ")+
                    "order by catatan_perawatan.no_rawat,catatan_perawatan.tanggal,catatan_perawatan.jam desc"); 
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
                }
                rs=ps4.executeQuery();
                while(rs.next()){
                    TabModeCatatan.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Catatan : "+e);
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
        LCount.setText(""+TabModeCatatan.getRowCount());
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
            LingkarPerut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),18).toString()); 
            TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),19).toString()); 
            TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),20).toString()); 
            TInstruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),21).toString()); 
            TEvaluasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),22).toString()); 
            cmbJam.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4).toString());
        }
    }
    
    private void getDataCatatan() {
        if(tbCatatan.getSelectedRow()!= -1){
            TNoRw.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),1).toString());
            TNoRM.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),2).toString());
            TPasien.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),3).toString()); 
            KdDok3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),6).toString());  
            TDokter3.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),7).toString());
            Catatan.setText(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),8).toString());             
            cmbJam.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbCatatan.getValueAt(tbCatatan.getSelectedRow(),5).toString().substring(6,8));
            Valid.SetTgl(DTPTgl,tbCatatan.getValueAt(tbCatatan.getSelectedRow(),4).toString());
        }
    }
    
    private void tampilPemeriksaanObstetri() {
        Valid.tabelKosong(tabModeObstetri);
        try{
            ps5=koneksi.prepareStatement("select pemeriksaan_obstetri_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                    "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                    "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                    "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                    "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_obstetri_ralan on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat where  "+
                    "pemeriksaan_obstetri_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_obstetri_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or  "+
                    "pemeriksaan_obstetri_ralan.tinggi_uteri like ? or pemeriksaan_obstetri_ralan.janin like ? or pemeriksaan_obstetri_ralan.letak like ?) ")+
                    "order by pemeriksaan_obstetri_ralan.no_rawat,pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat desc");
            try {
                ps5.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps5.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps5.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
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
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2,String kodedokter, String namadokter) {
        TNoRw.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm(); 
        ChkInput1.setSelected(true);
        isForm2(); 
        ChkInput2.setSelected(true);
        isForm3(); 
        ChkInput3.setSelected(true);
        isForm4();
        KdDok.setText(kodedokter);
        KdDok2.setText(kodedokter);
        KdDok3.setText(kodedokter);
        TDokter.setText(namadokter);
        TDokter2.setText(namadokter); 
        TDokter3.setText(namadokter); 
        TabRawatMouseClicked(null);
    }
    
    public void SetPoli(String KodePoli){
        this.kode_poli=KodePoli;
    }
    
    public void SetPj(String KodePj){
        this.kd_pj=KodePj;
    }
    
    private void isForm2(){
        if(ChkInput1.isSelected()==true){
            ChkInput1.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH,156));
            panelGlass13.setVisible(true);      
            ChkInput1.setVisible(true);
        }else if(ChkInput1.isSelected()==false){           
            ChkInput1.setVisible(false);            
            PanelInput1.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass13.setVisible(false);      
            ChkInput1.setVisible(true);
        }
    }
    
    private void tampilPemeriksaanGinekologi() {
        Valid.tabelKosong(tabModeGinekologi);
        try{
            ps6=koneksi.prepareStatement("select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                    "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                    "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                    "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                    "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_ginekologi_ralan on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat "+
                    "where pemeriksaan_ginekologi_ralan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? "+
                    (TCari.getText().trim().equals("")?"":"and (pemeriksaan_ginekologi_ralan.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or  pemeriksaan_ginekologi_ralan.inspeksi like ? or pemeriksaan_ginekologi_ralan.inspeksi_vulva like ? or "+
                    "pemeriksaan_ginekologi_ralan.inspekulo_gine like ?) ")+
                    "order by pemeriksaan_ginekologi_ralan.no_rawat,pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat desc");
            try {
                ps6.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(3,"%"+TCariPasien.getText()+"%");
                if(!TCari.getText().trim().equals("")){
                    ps6.setString(4,"%"+TCari.getText().trim()+"%");
                    ps6.setString(5,"%"+TCari.getText().trim()+"%");
                    ps6.setString(6,"%"+TCari.getText().trim()+"%");
                    ps6.setString(7,"%"+TCari.getText().trim()+"%");
                    ps6.setString(8,"%"+TCari.getText().trim()+"%");
                    ps6.setString(9,"%"+TCari.getText().trim()+"%");
                }
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
            PanelInput2.setPreferredSize(new Dimension(WIDTH,246));
            panelGlass14.setVisible(true);      
            ChkInput2.setVisible(true);
        }else if(ChkInput2.isSelected()==false){           
            ChkInput2.setVisible(false);            
            PanelInput2.setPreferredSize(new Dimension(WIDTH,20));
            panelGlass14.setVisible(false);      
            ChkInput2.setVisible(true);
        }
    }
    
    private void tampilTindakanDr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan.getRowCount();i++){
                if(TabModeTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            kategori=new String[jml];
            totaltnd=new double[jml];  
            bagianrs=new double[jml];
            bhp=new double[jml];
            jmdokter=new double[jml];
            jmperawat=new double[jml];
            kso=new double[jml];
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan.getRowCount();i++){
                if(TabModeTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan);

            for(i=0;i<jml;i++){
                TabModeTindakan.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdr>0 and jns_perawatan.status='1' and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });    
                }                   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan.getRowCount());
    }
    
    private void tampilTindakanPr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan2.getRowCount();i++){
                if(TabModeTindakan2.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            kategori=new String[jml];
            totaltnd=new double[jml];  
            bagianrs=new double[jml];
            bhp=new double[jml];
            jmdokter=new double[jml];
            jmperawat=new double[jml];
            kso=new double[jml];
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan2.getRowCount();i++){
                if(TabModeTindakan2.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan2.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan2.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan2.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan2.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan2);

            for(i=0;i<jml;i++){
                TabModeTindakan2.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrpr>0 and jns_perawatan.status='1' and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan2.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });        
                }                      
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan2.getRowCount());
    }
    
    private void tampilTindakanDrPr() {
        try{     
            jml=0;
            for(i=0;i<TabModeTindakan3.getRowCount();i++){
                if(TabModeTindakan3.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            kategori=new String[jml];
            totaltnd=new double[jml];  
            bagianrs=new double[jml];
            bhp=new double[jml];
            jmdokter=new double[jml];
            jmperawat=new double[jml];
            kso=new double[jml];
            menejemen=new double[jml];

            index=0;        
            for(i=0;i<TabModeTindakan3.getRowCount();i++){
                if(TabModeTindakan3.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=TabModeTindakan3.getValueAt(i,1).toString();
                    nama[index]=TabModeTindakan3.getValueAt(i,2).toString();
                    kategori[index]=TabModeTindakan3.getValueAt(i,3).toString();
                    totaltnd[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,4).toString());
                    bagianrs[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,5).toString());
                    bhp[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,8).toString());  
                    kso[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(TabModeTindakan3.getValueAt(i,10).toString());  
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan3);

            for(i=0;i<jml;i++){
                TabModeTindakan3.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]
                });
            }
            
            if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");        
            }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan ");     
            }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.total_byrdrpr>0 and jns_perawatan.status='1' and "+
                   "(jns_perawatan.kd_jenis_prw like ? or jns_perawatan.nm_perawatan like ? or kategori_perawatan.nm_kategori like ?) order by jns_perawatan.nm_perawatan "); 
            }
            
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,kode_poli.trim());
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(5,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan.setString(1,kd_pj.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,kode_poli.trim());
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(4,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan.setString(1,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(2,"%"+TCari.getText().trim()+"%");
                    pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                }
                
                while(rstindakan.next()){
                    TabModeTindakan3.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                        rstindakan.getDouble("menejemen")
                    });    
                }   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+TabModeTindakan3.getRowCount());
    }
    
    private void TampilkanData(){
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampilkanPenangananDokter();
                break;
            case 1:
                tampilkanPenangananPetugas();
                break;
            case 2:
                tampilkanPenangananDokterPetugas();
                break;
            case 3:
                runBackground(() ->tampilPemeriksaan());
                break;
            case 4:
                runBackground(() ->tampilPemeriksaanObstetri());
                break;
            case 5:
                runBackground(() ->tampilPemeriksaanGinekologi());
                break;
            case 6:
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""), Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                    panelDiagnosa1.pilihTab();
                    LCount.setText(panelDiagnosa1.getRecord()+"");
                }  
                break;
            case 7:
                if(akses.getcatatan_perawatan()==true){
                    runBackground(() ->tampilCatatan());
                }  
                break;
            default:
                break;
        }
    }

    private void tampilkanPenangananDokter() {
        if(TabRawatTindakanDokter.getSelectedIndex()==0){
            runBackground(() ->tampilTindakanDr());
        }else if(TabRawatTindakanDokter.getSelectedIndex()==1){
            runBackground(() ->tampilDr());
        }
    }
    
    private void SimpanPenangananDokter(){        
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan.getRowCount();i++){ 
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_dr","?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",11,new String[]{
                        TNoRw.getText(),tbTindakan.getValueAt(i,1).toString(),KdDok.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbTindakan.getValueAt(i,5).toString(),
                        tbTindakan.getValueAt(i,6).toString(),tbTindakan.getValueAt(i,7).toString(),tbTindakan.getValueAt(i,9).toString(),
                        tbTindakan.getValueAt(i,10).toString(),tbTindakan.getValueAt(i,4).toString()
                    })==true){
                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan.getValueAt(i,7).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }    
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                             
                }
                if(ttljmdokter>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                               
                }
                if(ttlkso>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttljasasarana>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }     
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttlbhp>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }      
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                           
                }
                if(ttlmenejemen>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                            
                }
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode()); 
                }                                               
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    tbTindakan.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void SimpanPenangananPetugas(){
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan2.getRowCount();i++){ 
                if(tbTindakan2.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_pr","?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",11,new String[]{
                        TNoRw.getText(),tbTindakan2.getValueAt(i,1).toString(),kdptg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                        cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),tbTindakan2.getValueAt(i,5).toString(),
                        tbTindakan2.getValueAt(i,6).toString(),tbTindakan2.getValueAt(i,8).toString(),tbTindakan2.getValueAt(i,9).toString(),
                        tbTindakan2.getValueAt(i,10).toString(),tbTindakan2.getValueAt(i,4).toString()
                    })==true){
                        ttljmperawat=ttljmperawat+Double.parseDouble(tbTindakan2.getValueAt(i,8).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan2.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan2.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan2.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan2.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan2.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }    
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                             
                }
                if(ttljmperawat>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                             
                }
                if(ttlkso>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttljasasarana>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }     
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttlbhp>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }      
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                           
                }
                if(ttlmenejemen>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                            
                }
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode());  
                }                                              
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan2.getRowCount();i++){ 
                    tbTindakan2.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void SimpanPenangananDokterPetugas(){        
        try {
            ChkJln.setSelected(false);
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            for(i=0;i<tbTindakan3.getRowCount();i++){ 
                if(tbTindakan3.getValueAt(i,0).toString().equals("true")){  
                    if(Sequel.menyimpantf("rawat_jl_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum'","Tindakan",13,new String[]{
                        TNoRw.getText(),tbTindakan3.getValueAt(i,1).toString(),KdDok2.getText(),kdptg2.getText(),
                        Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        tbTindakan3.getValueAt(i,5).toString(),tbTindakan3.getValueAt(i,6).toString(),tbTindakan3.getValueAt(i,7).toString(),
                        tbTindakan3.getValueAt(i,8).toString(),tbTindakan3.getValueAt(i,9).toString(),tbTindakan3.getValueAt(i,10).toString(),
                        tbTindakan3.getValueAt(i,4).toString()
                    })==true){
                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTindakan3.getValueAt(i,7).toString());
                        ttljmperawat=ttljmperawat+Double.parseDouble(tbTindakan3.getValueAt(i,8).toString());
                        ttlkso=ttlkso+Double.parseDouble(tbTindakan3.getValueAt(i,9).toString());
                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTindakan3.getValueAt(i,4).toString());
                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTindakan3.getValueAt(i,5).toString());
                        ttlbhp=ttlbhp+Double.parseDouble(tbTindakan3.getValueAt(i,6).toString());
                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTindakan3.getValueAt(i,10).toString());
                    }else{
                        sukses=false;
                    }
                }                           
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }    
                    if(Sequel.menyimpantf("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                             
                }
                if(ttljmdokter>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                               
                }
                if(ttljmperawat>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                             
                }
                if(ttlkso>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttljasasarana>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }     
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                              
                }
                if(ttlbhp>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }      
                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                           
                }
                if(ttlmenejemen>0){
                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }       
                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'")==false){
                        sukses=false;
                    }                            
                }
                if(sukses==true){
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRM.getText()+" "+TPasien.getText()+", DIPOSTING OLEH "+akses.getkode()); 
                }                                               
            }
            
            if(sukses==true){
                Sequel.Commit();
                for(i=0;i<tbTindakan3.getRowCount();i++){ 
                    tbTindakan3.setValueAt(false,i,0);
                }
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            ChkJln.setSelected(true);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private void tampilkanPenangananPetugas() {
        if(TabRawatTindakanPetugas.getSelectedIndex()==0){
            runBackground(() ->tampilTindakanPr());
        }else if(TabRawatTindakanPetugas.getSelectedIndex()==1){
            runBackground(() ->tampilPr());
        }
    }

    private void tampilkanPenangananDokterPetugas() {
        if(TabRawatTindakanDokterPetugas.getSelectedIndex()==0){
            runBackground(() ->tampilTindakanDrPr());
        }else if(TabRawatTindakanDokterPetugas.getSelectedIndex()==1){
            runBackground(() ->tampilDrPr());
        }
    }

    private void inputObat() {
        DlgCariObat dlgobt=new DlgCariObat(null,false);
        dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem());
        dlgobt.isCek();
        dlgobt.setDokter(KdDok.getText(),TDokter.getText());
        dlgobt.tampilobat();
        dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgobt.setLocationRelativeTo(internalFrame1);
        dlgobt.setVisible(true);
    }

    private void inputResep() {
        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),KdDok.getText(),TDokter.getText(),"ralan");
        resep.isCek();
        resep.tampilobat();
        resep.setVisible(true);
    }

    private void inputKamar() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setstatus(true);
        DlgKamarInap dlgki=new DlgKamarInap(null,false);
        dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dlgki.setLocationRelativeTo(internalFrame1);
        dlgki.emptTeks();
        dlgki.isCek();
        dlgki.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());  
        dlgki.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    private void inputTemplate(){
        if(Sequel.CariDokter(KdPeg.getText()).equals("")){
            JOptionPane.showMessageDialog(null,"Template pemeriksaan hanya untuk dokter...!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterCariTemplatePemeriksaan templatepemeriksaan=new MasterCariTemplatePemeriksaan(null,false);
            templatepemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            templatepemeriksaan.setLocationRelativeTo(internalFrame1);
            templatepemeriksaan.isCek();
            templatepemeriksaan.setDokter(KdPeg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),TNoRM.getText());
            templatepemeriksaan.tampil2();
            templatepemeriksaan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    public void emptTeks(){
        BtnBatalActionPerformed(null);
        TabRawat.setSelectedIndex(3);
    }
    
    private void initRawatJalan(){
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
        BtnHasilPemeriksaanUSGUrologi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGUrologi.setName("BtnHasilPemeriksaanUSGUrologi");
        BtnHasilPemeriksaanUSGUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanUSGUrologi.setRoundRect(false);
        BtnHasilPemeriksaanUSGUrologi.addActionListener(this::BtnHasilPemeriksaanUSGUrologiActionPerformed);
        
        BtnHasilPemeriksaanUSGGynecologi = new widget.Button();
        BtnHasilPemeriksaanUSGGynecologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanUSGGynecologi.setText("Hasil USG Gynecologi");
        BtnHasilPemeriksaanUSGGynecologi.setFocusPainted(false);
        BtnHasilPemeriksaanUSGGynecologi.setFont(new java.awt.Font("Tahoma", 0, 11));
        BtnHasilPemeriksaanUSGGynecologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanUSGGynecologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanUSGGynecologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanUSGGynecologi.setName("BtnHasilPemeriksaanUSGGynecologi");
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
        BtnHasilPemeriksaanEKG.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanEKG.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanEKG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanEKG.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanEKG.setName("BtnHasilPemeriksaanEKG");
        BtnHasilPemeriksaanEKG.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanEKG.setRoundRect(false);
        BtnHasilPemeriksaanEKG.addActionListener(this::BtnHasilPemeriksaanEKGActionPerformed);
        
        BtnHasilPemeriksaanTreadmill = new widget.Button();
        BtnHasilPemeriksaanTreadmill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanTreadmill.setText("Hasil Pemeriksaan Treadmill");
        BtnHasilPemeriksaanTreadmill.setFocusPainted(false);
        BtnHasilPemeriksaanTreadmill.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanTreadmill.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanTreadmill.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanTreadmill.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanTreadmill.setName("BtnHasilPemeriksaanTreadmill");
        BtnHasilPemeriksaanTreadmill.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanTreadmill.setRoundRect(false);
        BtnHasilPemeriksaanTreadmill.addActionListener(this::BtnHasilPemeriksaanTreadmillActionPerformed);
        
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
        
        BtnHasilPemeriksaanECHOPediatrik = new widget.Button();
        BtnHasilPemeriksaanECHOPediatrik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnHasilPemeriksaanECHOPediatrik.setText("Hasil ECHO Pediatrik");
        BtnHasilPemeriksaanECHOPediatrik.setFocusPainted(false);
        BtnHasilPemeriksaanECHOPediatrik.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnHasilPemeriksaanECHOPediatrik.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnHasilPemeriksaanECHOPediatrik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnHasilPemeriksaanECHOPediatrik.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnHasilPemeriksaanECHOPediatrik.setName("BtnHasilPemeriksaanECHOPediatrik");
        BtnHasilPemeriksaanECHOPediatrik.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnHasilPemeriksaanECHOPediatrik.setRoundRect(false);
        BtnHasilPemeriksaanECHOPediatrik.addActionListener(this::BtnHasilPemeriksaanECHOPediatrikActionPerformed);
        
        BtnPenatalaksanaanTerapiOkupasi = new widget.Button();
        BtnPenatalaksanaanTerapiOkupasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnPenatalaksanaanTerapiOkupasi.setText("Tatalaksana Terapi Okupasi");
        BtnPenatalaksanaanTerapiOkupasi.setFocusPainted(false);
        BtnPenatalaksanaanTerapiOkupasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenatalaksanaanTerapiOkupasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenatalaksanaanTerapiOkupasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenatalaksanaanTerapiOkupasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenatalaksanaanTerapiOkupasi.setName("BtnPenatalaksanaanTerapiOkupasi"); 
        BtnPenatalaksanaanTerapiOkupasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenatalaksanaanTerapiOkupasi.setRoundRect(false);
        BtnPenatalaksanaanTerapiOkupasi.addActionListener(this::BtnPenatalaksanaanTerapiOkupasiActionPerformed);
        
        BtnPenilaianPsikolog = new widget.Button();
        BtnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnPenilaianPsikolog.setText("Pengkajian Psikologi");
        BtnPenilaianPsikolog.setFocusPainted(false);
        BtnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnPenilaianPsikolog.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPenilaianPsikolog.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPenilaianPsikolog.setName("BtnPenilaianPsikolog"); 
        BtnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnPenilaianPsikolog.setRoundRect(false);
        BtnPenilaianPsikolog.addActionListener(this::BtnPenilaianPsikologActionPerformed);
        
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
        
        BtnLayananKedokteranFisikRehabilitasi = new widget.Button();
        BtnLayananKedokteranFisikRehabilitasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnLayananKedokteranFisikRehabilitasi.setText("Kedokteran Fisik & Rehabilitasi");
        BtnLayananKedokteranFisikRehabilitasi.setFocusPainted(false);
        BtnLayananKedokteranFisikRehabilitasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnLayananKedokteranFisikRehabilitasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnLayananKedokteranFisikRehabilitasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnLayananKedokteranFisikRehabilitasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnLayananKedokteranFisikRehabilitasi.setName("BtnLayananKedokteranFisikRehabilitasi"); 
        BtnLayananKedokteranFisikRehabilitasi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnLayananKedokteranFisikRehabilitasi.setRoundRect(false);
        BtnLayananKedokteranFisikRehabilitasi.addActionListener(this::BtnLayananKedokteranFisikRehabilitasiActionPerformed);
        
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
        
        BtnSkriningMerokokUsiaRemaja = new widget.Button();
        BtnSkriningMerokokUsiaRemaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningMerokokUsiaRemaja.setText("Skrining Merokok Usia Remaja");
        BtnSkriningMerokokUsiaRemaja.setFocusPainted(false);
        BtnSkriningMerokokUsiaRemaja.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningMerokokUsiaRemaja.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningMerokokUsiaRemaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningMerokokUsiaRemaja.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningMerokokUsiaRemaja.setName("BtnSkriningMerokokUsiaRemaja"); 
        BtnSkriningMerokokUsiaRemaja.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningMerokokUsiaRemaja.setRoundRect(false);
        BtnSkriningMerokokUsiaRemaja.addActionListener(this::BtnSkriningMerokokUsiaRemajaActionPerformed);
        
        BtnSkriningKekerasanPadaWanita = new widget.Button();
        BtnSkriningKekerasanPadaWanita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKekerasanPadaWanita.setText("Kekerasan Pada Perempuan");
        BtnSkriningKekerasanPadaWanita.setFocusPainted(false);
        BtnSkriningKekerasanPadaWanita.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKekerasanPadaWanita.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKekerasanPadaWanita.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKekerasanPadaWanita.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKekerasanPadaWanita.setName("BtnSkriningKekerasanPadaWanita"); 
        BtnSkriningKekerasanPadaWanita.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKekerasanPadaWanita.setRoundRect(false);
        BtnSkriningKekerasanPadaWanita.addActionListener(this::BtnSkriningKekerasanPadaWanitaActionPerformed);
        
        BtnSkriningObesitas = new widget.Button();
        BtnSkriningObesitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningObesitas.setText("Skrining Obesitas");
        BtnSkriningObesitas.setFocusPainted(false);
        BtnSkriningObesitas.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningObesitas.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningObesitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningObesitas.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningObesitas.setName("BtnSkriningObesitas"); 
        BtnSkriningObesitas.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningObesitas.setRoundRect(false);
        BtnSkriningObesitas.addActionListener(this::BtnSkriningObesitasActionPerformed);
        
        BtnSkriningRisikoKankerPayudara = new widget.Button();
        BtnSkriningRisikoKankerPayudara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningRisikoKankerPayudara.setText("Skrining Risiko Kanker Payudara");
        BtnSkriningRisikoKankerPayudara.setFocusPainted(false);
        BtnSkriningRisikoKankerPayudara.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningRisikoKankerPayudara.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningRisikoKankerPayudara.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningRisikoKankerPayudara.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningRisikoKankerPayudara.setName("BtnSkriningRisikoKankerPayudara"); 
        BtnSkriningRisikoKankerPayudara.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningRisikoKankerPayudara.setRoundRect(false);
        BtnSkriningRisikoKankerPayudara.addActionListener(this::BtnSkriningRisikoKankerPayudaraActionPerformed);
        
        BtnSkriningRisikoKankerParu = new widget.Button();
        BtnSkriningRisikoKankerParu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningRisikoKankerParu.setText("Skrining Risiko Kanker Paru");
        BtnSkriningRisikoKankerParu.setFocusPainted(false);
        BtnSkriningRisikoKankerParu.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningRisikoKankerParu.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningRisikoKankerParu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningRisikoKankerParu.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningRisikoKankerParu.setName("BtnSkriningRisikoKankerParu"); 
        BtnSkriningRisikoKankerParu.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningRisikoKankerParu.setRoundRect(false);
        BtnSkriningRisikoKankerParu.addActionListener(this::BtnSkriningRisikoKankerParuActionPerformed);
        
        BtnSkriningKesehatanGigiMulutremaja = new widget.Button();
        BtnSkriningKesehatanGigiMulutremaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKesehatanGigiMulutremaja.setText("Skrining Gigi Mulut Remaja");
        BtnSkriningKesehatanGigiMulutremaja.setFocusPainted(false);
        BtnSkriningKesehatanGigiMulutremaja.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKesehatanGigiMulutremaja.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKesehatanGigiMulutremaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKesehatanGigiMulutremaja.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKesehatanGigiMulutremaja.setName("BtnSkriningKesehatanGigiMulutremaja"); 
        BtnSkriningKesehatanGigiMulutremaja.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKesehatanGigiMulutremaja.setRoundRect(false);
        BtnSkriningKesehatanGigiMulutremaja.addActionListener(this::BtnSkriningKesehatanGigiMulutremajaActionPerformed);
        
        BtnSkriningKesehatanGigiMulutBalita = new widget.Button();
        BtnSkriningKesehatanGigiMulutBalita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKesehatanGigiMulutBalita.setText("Skrining Gigi Mulut Balita");
        BtnSkriningKesehatanGigiMulutBalita.setFocusPainted(false);
        BtnSkriningKesehatanGigiMulutBalita.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKesehatanGigiMulutBalita.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKesehatanGigiMulutBalita.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKesehatanGigiMulutBalita.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKesehatanGigiMulutBalita.setName("BtnSkriningKesehatanGigiMulutBalita"); 
        BtnSkriningKesehatanGigiMulutBalita.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKesehatanGigiMulutBalita.setRoundRect(false);
        BtnSkriningKesehatanGigiMulutBalita.addActionListener(this::BtnSkriningKesehatanGigiMulutBalitaActionPerformed);
        
        BtnSkriningKesehatanGigiMulutLansia = new widget.Button();
        BtnSkriningKesehatanGigiMulutLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKesehatanGigiMulutLansia.setText("Skrining Gigi Mulut Lansia");
        BtnSkriningKesehatanGigiMulutLansia.setFocusPainted(false);
        BtnSkriningKesehatanGigiMulutLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKesehatanGigiMulutLansia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKesehatanGigiMulutLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKesehatanGigiMulutLansia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKesehatanGigiMulutLansia.setName("BtnSkriningKesehatanGigiMulutLansia"); 
        BtnSkriningKesehatanGigiMulutLansia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKesehatanGigiMulutLansia.setRoundRect(false);
        BtnSkriningKesehatanGigiMulutLansia.addActionListener(this::BtnSkriningKesehatanGigiMulutLansiaActionPerformed);
        
        BtnSkriningKesehatanGigiMulutDewasa = new widget.Button();
        BtnSkriningKesehatanGigiMulutDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKesehatanGigiMulutDewasa.setText("Skrining Gigi Mulut Dewasa");
        BtnSkriningKesehatanGigiMulutDewasa.setFocusPainted(false);
        BtnSkriningKesehatanGigiMulutDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKesehatanGigiMulutDewasa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKesehatanGigiMulutDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKesehatanGigiMulutDewasa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKesehatanGigiMulutDewasa.setName("BtnSkriningKesehatanGigiMulutDewasa"); 
        BtnSkriningKesehatanGigiMulutDewasa.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKesehatanGigiMulutDewasa.setRoundRect(false);
        BtnSkriningKesehatanGigiMulutDewasa.addActionListener(this::BtnSkriningKesehatanGigiMulutDewasaActionPerformed);
        
        BtnSkriningRisikoKankerServiks = new widget.Button();
        BtnSkriningRisikoKankerServiks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningRisikoKankerServiks.setText("Skrining Risiko Kanker Serviks");
        BtnSkriningRisikoKankerServiks.setFocusPainted(false);
        BtnSkriningRisikoKankerServiks.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningRisikoKankerServiks.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningRisikoKankerServiks.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningRisikoKankerServiks.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningRisikoKankerServiks.setName("BtnSkriningRisikoKankerServiks"); 
        BtnSkriningRisikoKankerServiks.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningRisikoKankerServiks.setRoundRect(false);
        BtnSkriningRisikoKankerServiks.addActionListener(this::BtnSkriningRisikoKankerServiksActionPerformed);
        
        BtnSkriningAnemia = new widget.Button();
        BtnSkriningAnemia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningAnemia.setText("Skrining Anemia");
        BtnSkriningAnemia.setFocusPainted(false);
        BtnSkriningAnemia.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningAnemia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningAnemia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningAnemia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningAnemia.setName("BtnSkriningAnemia"); 
        BtnSkriningAnemia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningAnemia.setRoundRect(false);
        BtnSkriningAnemia.addActionListener(this::BtnSkriningAnemiaActionPerformed);
        
        BtnSkriningHipertensi = new widget.Button();
        BtnSkriningHipertensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningHipertensi.setText("Skrining Hipertensi");
        BtnSkriningHipertensi.setFocusPainted(false);
        BtnSkriningHipertensi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningHipertensi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningHipertensi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningHipertensi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningHipertensi.setName("BtnSkriningHipertensi"); 
        BtnSkriningHipertensi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningHipertensi.setRoundRect(false);
        BtnSkriningHipertensi.addActionListener(this::BtnSkriningHipertensiActionPerformed);
        
        BtnSkriningKesehatanPenglihatan = new widget.Button();
        BtnSkriningKesehatanPenglihatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKesehatanPenglihatan.setText("Skrining Kesehatan Penglihatan");
        BtnSkriningKesehatanPenglihatan.setFocusPainted(false);
        BtnSkriningKesehatanPenglihatan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKesehatanPenglihatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKesehatanPenglihatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKesehatanPenglihatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKesehatanPenglihatan.setName("BtnSkriningKesehatanPenglihatan"); 
        BtnSkriningKesehatanPenglihatan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKesehatanPenglihatan.setRoundRect(false);
        BtnSkriningKesehatanPenglihatan.addActionListener(this::BtnSkriningKesehatanPenglihatanActionPerformed);
        
        BtnSkriningIndraPendengaran = new widget.Button();
        BtnSkriningIndraPendengaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningIndraPendengaran.setText("Skrining Indra Pendengaran");
        BtnSkriningIndraPendengaran.setFocusPainted(false);
        BtnSkriningIndraPendengaran.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningIndraPendengaran.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningIndraPendengaran.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningIndraPendengaran.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningIndraPendengaran.setName("BtnSkriningIndraPendengaran"); 
        BtnSkriningIndraPendengaran.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningIndraPendengaran.setRoundRect(false);
        BtnSkriningIndraPendengaran.addActionListener(this::BtnSkriningIndraPendengaranActionPerformed);
        
        BtnSkriningFrailtySyndrome = new widget.Button();
        BtnSkriningFrailtySyndrome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningFrailtySyndrome.setText("Skrining Frailty Syndrome");
        BtnSkriningFrailtySyndrome.setFocusPainted(false);
        BtnSkriningFrailtySyndrome.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningFrailtySyndrome.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningFrailtySyndrome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningFrailtySyndrome.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningFrailtySyndrome.setName("BtnSkriningFrailtySyndrome"); 
        BtnSkriningFrailtySyndrome.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningFrailtySyndrome.setRoundRect(false);
        BtnSkriningFrailtySyndrome.addActionListener(this::BtnSkriningFrailtySyndromeActionPerformed);
        
        BtnSkriningTBC = new widget.Button();
        BtnSkriningTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningTBC.setText("Skrining TBC");
        BtnSkriningTBC.setFocusPainted(false);
        BtnSkriningTBC.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningTBC.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningTBC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningTBC.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningTBC.setName("BtnSkriningTBC"); 
        BtnSkriningTBC.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningTBC.setRoundRect(false);
        BtnSkriningTBC.addActionListener(this::BtnSkriningTBCActionPerformed);
        
        BtnSkriningPUMA = new widget.Button();
        BtnSkriningPUMA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningPUMA.setText("Skrining PUMA");
        BtnSkriningPUMA.setFocusPainted(false);
        BtnSkriningPUMA.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningPUMA.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningPUMA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningPUMA.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningPUMA.setName("BtnSkriningPUMA"); 
        BtnSkriningPUMA.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningPUMA.setRoundRect(false);
        BtnSkriningPUMA.addActionListener(this::BtnSkriningPUMAActionPerformed);
        
        BtnSkriningAdiksiNikotin = new widget.Button();
        BtnSkriningAdiksiNikotin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningAdiksiNikotin.setText("Skrining Adiksi Nikotin");
        BtnSkriningAdiksiNikotin.setFocusPainted(false);
        BtnSkriningAdiksiNikotin.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningAdiksiNikotin.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningAdiksiNikotin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningAdiksiNikotin.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningAdiksiNikotin.setName("BtnSkriningAdiksiNikotin"); 
        BtnSkriningAdiksiNikotin.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningAdiksiNikotin.setRoundRect(false);
        BtnSkriningAdiksiNikotin.addActionListener(this::BtnSkriningAdiksiNikotinActionPerformed);
        
        BtnSkriningThalassemia = new widget.Button();
        BtnSkriningThalassemia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningThalassemia.setText("Skrining Thalassemia");
        BtnSkriningThalassemia.setFocusPainted(false);
        BtnSkriningThalassemia.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningThalassemia.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningThalassemia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningThalassemia.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningThalassemia.setName("BtnSkriningThalassemia"); 
        BtnSkriningThalassemia.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningThalassemia.setRoundRect(false);
        BtnSkriningThalassemia.addActionListener(this::BtnSkriningThalassemiaActionPerformed);
        
        BtnSkriningInstrumenSDQ = new widget.Button();
        BtnSkriningInstrumenSDQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningInstrumenSDQ.setText("Skrining Instrumen SDQ");
        BtnSkriningInstrumenSDQ.setFocusPainted(false);
        BtnSkriningInstrumenSDQ.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningInstrumenSDQ.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningInstrumenSDQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningInstrumenSDQ.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningInstrumenSDQ.setName("BtnSkriningInstrumenSDQ"); 
        BtnSkriningInstrumenSDQ.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningInstrumenSDQ.setRoundRect(false);
        BtnSkriningInstrumenSDQ.addActionListener(this::BtnSkriningInstrumenSDQActionPerformed);
        
        BtnSkriningInstrumenACRS = new widget.Button();
        BtnSkriningInstrumenACRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningInstrumenACRS.setText("Skrining Instrumen ACRS");
        BtnSkriningInstrumenACRS.setFocusPainted(false);
        BtnSkriningInstrumenACRS.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningInstrumenACRS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningInstrumenACRS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningInstrumenACRS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningInstrumenACRS.setName("BtnSkriningInstrumenACRS"); 
        BtnSkriningInstrumenACRS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningInstrumenACRS.setRoundRect(false);
        BtnSkriningInstrumenACRS.addActionListener(this::BtnSkriningInstrumenACRSActionPerformed);
        
        BtnSkriningInstrumenMentalEmosional = new widget.Button();
        BtnSkriningInstrumenMentalEmosional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningInstrumenMentalEmosional.setText("Skrining Mental Emosional");
        BtnSkriningInstrumenMentalEmosional.setFocusPainted(false);
        BtnSkriningInstrumenMentalEmosional.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningInstrumenMentalEmosional.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningInstrumenMentalEmosional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningInstrumenMentalEmosional.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningInstrumenMentalEmosional.setName("BtnSkriningInstrumenMentalEmosional"); 
        BtnSkriningInstrumenMentalEmosional.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningInstrumenMentalEmosional.setRoundRect(false);
        BtnSkriningInstrumenMentalEmosional.addActionListener(this::BtnSkriningInstrumenMentalEmosionalActionPerformed);
        
        BtnSkriningInstrumenAMT = new widget.Button();
        BtnSkriningInstrumenAMT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningInstrumenAMT.setText("Skrining Instrumen AMT");
        BtnSkriningInstrumenAMT.setFocusPainted(false);
        BtnSkriningInstrumenAMT.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningInstrumenAMT.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningInstrumenAMT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningInstrumenAMT.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningInstrumenAMT.setName("BtnSkriningInstrumenAMT"); 
        BtnSkriningInstrumenAMT.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningInstrumenAMT.setRoundRect(false);
        BtnSkriningInstrumenAMT.addActionListener(this::BtnSkriningInstrumenAMTActionPerformed);
        
        BtnSkriningPneumoniaSeverityIndex = new widget.Button();
        BtnSkriningPneumoniaSeverityIndex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningPneumoniaSeverityIndex.setText("Skrining PSI");
        BtnSkriningPneumoniaSeverityIndex.setFocusPainted(false);
        BtnSkriningPneumoniaSeverityIndex.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningPneumoniaSeverityIndex.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningPneumoniaSeverityIndex.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningPneumoniaSeverityIndex.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningPneumoniaSeverityIndex.setName("BtnSkriningPneumoniaSeverityIndex"); 
        BtnSkriningPneumoniaSeverityIndex.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningPneumoniaSeverityIndex.setRoundRect(false);
        BtnSkriningPneumoniaSeverityIndex.addActionListener(this::BtnSkriningPneumoniaSeverityIndexActionPerformed);
        
        BtnSkriningCURB65 = new widget.Button();
        BtnSkriningCURB65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningCURB65.setText("Skrining CURB-65");
        BtnSkriningCURB65.setFocusPainted(false);
        BtnSkriningCURB65.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningCURB65.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningCURB65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningCURB65.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningCURB65.setName("BtnSkriningCURB65"); 
        BtnSkriningCURB65.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningCURB65.setRoundRect(false);
        BtnSkriningCURB65.addActionListener(this::BtnSkriningCURB65ActionPerformed);
        
        BtnSkriningInstrumenSRQ = new widget.Button();
        BtnSkriningInstrumenSRQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningInstrumenSRQ.setText("Skrining Instrumen SRQ");
        BtnSkriningInstrumenSRQ.setFocusPainted(false);
        BtnSkriningInstrumenSRQ.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningInstrumenSRQ.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningInstrumenSRQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningInstrumenSRQ.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningInstrumenSRQ.setName("BtnSkriningInstrumenSRQ"); 
        BtnSkriningInstrumenSRQ.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningInstrumenSRQ.setRoundRect(false);
        BtnSkriningInstrumenSRQ.addActionListener(this::BtnSkriningInstrumenSRQActionPerformed);
        
        BtnSkriningKankerKolorektal = new widget.Button();
        BtnSkriningKankerKolorektal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningKankerKolorektal.setText("Skrining Kanker Kolorektal");
        BtnSkriningKankerKolorektal.setFocusPainted(false);
        BtnSkriningKankerKolorektal.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningKankerKolorektal.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningKankerKolorektal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningKankerKolorektal.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningKankerKolorektal.setName("BtnSkriningKankerKolorektal"); 
        BtnSkriningKankerKolorektal.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningKankerKolorektal.setRoundRect(false);
        BtnSkriningKankerKolorektal.addActionListener(this::BtnSkriningKankerKolorektalActionPerformed);
        
        BtnSkriningDiabetesMelitus = new widget.Button();
        BtnSkriningDiabetesMelitus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnSkriningDiabetesMelitus.setText("Skrining Diabetes Melitus");
        BtnSkriningDiabetesMelitus.setFocusPainted(false);
        BtnSkriningDiabetesMelitus.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningDiabetesMelitus.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningDiabetesMelitus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningDiabetesMelitus.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningDiabetesMelitus.setName("BtnSkriningDiabetesMelitus"); 
        BtnSkriningDiabetesMelitus.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningDiabetesMelitus.setRoundRect(false);
        BtnSkriningDiabetesMelitus.addActionListener(this::BtnSkriningDiabetesMelitusActionPerformed);
        
        BtnAwalMedisJantung = new widget.Button();
        BtnAwalMedisJantung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisJantung.setText("Awal Medis Jantung");
        BtnAwalMedisJantung.setFocusPainted(false);
        BtnAwalMedisJantung.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisJantung.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisJantung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisJantung.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisJantung.setName("BtnAwalMedisJantung"); 
        BtnAwalMedisJantung.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisJantung.setRoundRect(false);
        BtnAwalMedisJantung.addActionListener(this::BtnAwalMedisJantungActionPerformed);
        
        BtnAwalMedisUrologi = new widget.Button();
        BtnAwalMedisUrologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); 
        BtnAwalMedisUrologi.setText("Awal Medis Urologi");
        BtnAwalMedisUrologi.setFocusPainted(false);
        BtnAwalMedisUrologi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnAwalMedisUrologi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisUrologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisUrologi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisUrologi.setName("BtnAwalMedisUrologi"); 
        BtnAwalMedisUrologi.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAwalMedisUrologi.setRoundRect(false);
        BtnAwalMedisUrologi.addActionListener(this::BtnAwalMedisUrologiActionPerformed);
        
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
        
        BtnChecklistKriteriaMasukNICU = new widget.Button();
        BtnChecklistKriteriaMasukNICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnChecklistKriteriaMasukNICU.setText("Check List Masuk NICU");
        BtnChecklistKriteriaMasukNICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukNICU.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistKriteriaMasukNICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukNICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukNICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukNICU.setName("BtnChecklistKriteriaMasukNICU");
        BtnChecklistKriteriaMasukNICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukNICU.setRoundRect(false);
        BtnChecklistKriteriaMasukNICU.addActionListener(this::BtnChecklistKriteriaMasukNICUActionPerformed);
        
        BtnChecklistKriteriaMasukPICU = new widget.Button();
        BtnChecklistKriteriaMasukPICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnChecklistKriteriaMasukPICU.setText("Check List Masuk PICU");
        BtnChecklistKriteriaMasukPICU.setFocusPainted(false);
        BtnChecklistKriteriaMasukPICU.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnChecklistKriteriaMasukPICU.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnChecklistKriteriaMasukPICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnChecklistKriteriaMasukPICU.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnChecklistKriteriaMasukPICU.setName("BtnChecklistKriteriaMasukPICU");
        BtnChecklistKriteriaMasukPICU.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnChecklistKriteriaMasukPICU.setRoundRect(false);
        BtnChecklistKriteriaMasukPICU.addActionListener(this::BtnChecklistKriteriaMasukPICUActionPerformed);
        
        BtnSkriningGiziKehamilan = new widget.Button();
        BtnSkriningGiziKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png")));
        BtnSkriningGiziKehamilan.setText("Skrining Gizi Kehamilan");
        BtnSkriningGiziKehamilan.setFocusPainted(false);
        BtnSkriningGiziKehamilan.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        BtnSkriningGiziKehamilan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSkriningGiziKehamilan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSkriningGiziKehamilan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSkriningGiziKehamilan.setName("BtnSkriningGiziKehamilan");
        BtnSkriningGiziKehamilan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnSkriningGiziKehamilan.setRoundRect(false);
        BtnSkriningGiziKehamilan.addActionListener(this::BtnSkriningGiziKehamilanActionPerformed);
        
        PopupSOAP = new javax.swing.JPopupMenu();
        PopupSOAP.setName("PopupSOAP");
        tbPemeriksaan.setComponentPopupMenu(PopupSOAP);
        
        MnSOAPDokter = new javax.swing.JMenuItem();
        MnSOAPDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnSOAPDokter.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSOAPDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnSOAPDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSOAPDokter.setText("Formulir SOAP Dokter");
        MnSOAPDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSOAPDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSOAPDokter.setName("MnSOAPDokter");
        MnSOAPDokter.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSOAPDokter.addActionListener(this::MnSOAPDokterActionPerformed);
        
        MnSOAPPetugas = new javax.swing.JMenuItem();
        MnSOAPPetugas.setBackground(new java.awt.Color(255, 255, 254));
        MnSOAPPetugas.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSOAPPetugas.setForeground(new java.awt.Color(50, 50, 50));
        MnSOAPPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSOAPPetugas.setText("Formulir SOAP Petugas");
        MnSOAPPetugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSOAPPetugas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSOAPPetugas.setName("MnSOAPPetugas");
        MnSOAPPetugas.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSOAPPetugas.addActionListener(this::MnSOAPPetugasActionPerformed);
        
        TanggalRegistrasi = new widget.TextBox();
        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi");
        
        PopupSOAP.add(MnSOAPDokter);
        PopupSOAP.add(MnSOAPPetugas);
        
        FormMenu.add(BtnRiwayat);
        FormMenu.add(BtnResepObat);
        FormMenu.add(BtnCopyResep);
        FormMenu.add(BtnResepLuar);
        FormMenu.add(BtnInputObat);
        FormMenu.add(BtnObatBhp);
        FormMenu.add(BtnBerkasDigital);
        FormMenu.add(BtnPermintaanLab);
        FormMenu.add(BtnPermintaanRad);
        FormMenu.add(BtnPermintaanKonsultasiMedik);
        FormMenu.add(BtnJadwalOperasi);
        FormMenu.add(BtnSKDP);
        FormMenu.add(BtnKamar);
        FormMenu.add(BtnTriaseIGD);
        FormMenu.add(BtnRujukInternal);
        FormMenu.add(BtnResume);
        FormMenu.add(BtnAwalKeperawatanIGD);
        FormMenu.add(BtnAwalKeperawatan);
        FormMenu.add(BtnAwalKeperawatanGigi);
        FormMenu.add(BtnAwalKeperawatanKandungan);
        FormMenu.add(BtnAwalKeperawatanAnak);
        FormMenu.add(BtnAwalKeperawatanPsikiatri);
        FormMenu.add(BtnAwalKeperawatanGeriatri);
        FormMenu.add(BtnAwalFisioterapi);
        FormMenu.add(BtnAwalTerapiWicara);
        FormMenu.add(BtnPenatalaksanaanTerapiOkupasi);
        FormMenu.add(BtnAwalMedisIGD);
        FormMenu.add(BtnAwalMedisIGDPsikiatri);
        FormMenu.add(BtnAwalMedis);
        FormMenu.add(BtnAwalMedisKandungan);
        FormMenu.add(BtnAwalMedisAnak);
        FormMenu.add(BtnAwalMedisTHT);
        FormMenu.add(BtnAwalMedisPsikiatri);
        FormMenu.add(BtnAwalMedisPenyakitDalam);
        FormMenu.add(BtnAwalMedisMata);
        FormMenu.add(BtnAwalMedisNeurologi);
        FormMenu.add(BtnAwalMedisOrthopedi);
        FormMenu.add(BtnAwalMedisBedah);
        FormMenu.add(BtnAwalMedisBedahMulut);
        FormMenu.add(BtnAwalMedisGeriatri);
        FormMenu.add(BtnAwalMedisKulitKelamin);
        FormMenu.add(BtnAwalMedisParu);
        FormMenu.add(BtnAwalMedisJantung);
        FormMenu.add(BtnAwalMedisUrologi);
        FormMenu.add(BtnAwalMedisRehabMedik);
        FormMenu.add(BtnAwalMedisHemodialisa);
        FormMenu.add(BtnRujukKeluar);
        FormMenu.add(BtnCatatan);
        FormMenu.add(BtnCatatanObservasiIGD);
        FormMenu.add(BtnCatatanObservasiCHBP);
        FormMenu.add(BtnCatatanObservasiInduksiPersalinan);
        FormMenu.add(BtnCatatanObservasiBayi);
        FormMenu.add(BtnCatatanObservasiHemodialisa);
        FormMenu.add(BtnCatatanCekGDS);
        FormMenu.add(BtnCatatanKeperawatan);
        FormMenu.add(BtnCatatanKeseimbanganCairan);
        FormMenu.add(BtnCatatanCairanHemodialisa);
        FormMenu.add(BtnChecklistPemberianFibrinolitik);
        FormMenu.add(BtnPenilaianUlangNyeri);
        FormMenu.add(BtnPemantauanPEWSAnak);
        FormMenu.add(BtnPemantauanPEWSDewasa);
        FormMenu.add(BtnPemantauanMEOWS);
        FormMenu.add(BtnPemantauanEWSNeonatus);
        FormMenu.add(BtnMonitoringReaksiTranfusi);
        FormMenu.add(BtnLayananKedokteranFisikRehabilitasi);
        FormMenu.add(BtnUjiFungsiKFR);
        FormMenu.add(BtnChecklistKriteriaMasukHCU);
        FormMenu.add(BtnChecklistKriteriaMasukICU);
        FormMenu.add(BtnChecklistKriteriaMasukNICU);
        FormMenu.add(BtnChecklistKriteriaMasukPICU);
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
        FormMenu.add(BtnMedicalCheckUp);
        FormMenu.add(BtnPenilaianPsikolog);
        FormMenu.add(BtnPenilaianPsikologKlinis);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhDewasa);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhAnak);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhLansia);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhNeonatus);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhGeriatri);
        FormMenu.add(BtnPenilaianLanjutanRisikoJatuhPsikiatri);
        FormMenu.add(BtnPenilaianLanjutanSkriningFungsional);
        FormMenu.add(BtnHasilPemeriksaanUSG);
        FormMenu.add(BtnHasilPemeriksaanUSGUrologi);
        FormMenu.add(BtnHasilPemeriksaanUSGNeonatus);
        FormMenu.add(BtnHasilPemeriksaanUSGGynecologi);
        FormMenu.add(BtnHasilPemeriksaanEKG);
        FormMenu.add(BtnHasilPemeriksaanECHO);
        FormMenu.add(BtnHasilPemeriksaanECHOPediatrik);
        FormMenu.add(BtnHasilPemeriksaanSlitLamp);
        FormMenu.add(BtnHasilPemeriksaanOCT);
        FormMenu.add(BtnHasilPemeriksaanTreadmill);
        FormMenu.add(BtnHasilEndoskopiFaringLaring);
        FormMenu.add(BtnHasilEndoskopiHidung);
        FormMenu.add(BtnHasilEndoskopiTelinga);
        FormMenu.add(BtnDokumentasiESWL);
        FormMenu.add(BtnCatatanPersalinanan);
        FormMenu.add(BtnLaporanTindakan);
        FormMenu.add(BtnSkriningMerokokUsiaRemaja);
        FormMenu.add(BtnSkriningKekerasanPadaWanita);
        FormMenu.add(BtnSkriningObesitas);
        FormMenu.add(BtnSkriningRisikoKankerPayudara);
        FormMenu.add(BtnSkriningRisikoKankerParu);
        FormMenu.add(BtnSkriningRisikoKankerServiks);
        FormMenu.add(BtnSkriningKesehatanGigiMulutremaja);
        FormMenu.add(BtnSkriningKesehatanGigiMulutBalita);
        FormMenu.add(BtnSkriningKesehatanGigiMulutDewasa);
        FormMenu.add(BtnSkriningKesehatanGigiMulutLansia);
        FormMenu.add(BtnSkriningTBC);
        FormMenu.add(BtnSkriningPUMA);
        FormMenu.add(BtnSkriningPneumoniaSeverityIndex);
        FormMenu.add(BtnSkriningCURB65);
        FormMenu.add(BtnSkriningAdiksiNikotin);
        FormMenu.add(BtnSkriningThalassemia);
        FormMenu.add(BtnSkriningInstrumenSDQ);
        FormMenu.add(BtnSkriningInstrumenSRQ);
        FormMenu.add(BtnSkriningInstrumenACRS);
        FormMenu.add(BtnSkriningInstrumenMentalEmosional);
        FormMenu.add(BtnSkriningInstrumenAMT);
        FormMenu.add(BtnSkriningKankerKolorektal);
        FormMenu.add(BtnSkriningDiabetesMelitus);
        FormMenu.add(BtnSkriningAnemia);
        FormMenu.add(BtnSkriningHipertensi);
        FormMenu.add(BtnSkriningKesehatanPenglihatan);
        FormMenu.add(BtnSkriningIndraPendengaran);
        FormMenu.add(BtnSkriningFrailtySyndrome);
        FormMenu.add(BtnSkriningNutrisiDewasa);
        FormMenu.add(BtnSkriningNutrisiLansia);
        FormMenu.add(BtnSkriningNutrisiAnak);
        FormMenu.add(BtnSkriningGiziKehamilan);
        FormMenu.add(BtnSkriningGiziLanjut);
        FormMenu.add(BtnAsuhanGizi);
        FormMenu.add(BtnMonitoringAsuhanGizi);
        FormMenu.add(BtnCatatanADIMEGizi);
        FormMenu.add(BtnKonselingFarmasi);
        FormMenu.add(BtnInformasiObat);
        FormMenu.add(BtnRekonsiliasiObat);
        FormMenu.add(BtnTransferAntarRuang);
        FormMenu.add(BtnEdukasiPasienKeluarga);
        FormMenu.add(BtnPelaksanaanInformasiEdukasi);
        FormMenu.add(BtnPengkajianRestrain);
        FormMenu.add(BtnPenilaianBayiBaruLahir);
        FormMenu.add(BtnPenilaianPasienTerminal);
        FormMenu.add(BtnPenilaianKorbanKekerasan);
        FormMenu.add(BtnPenilaianPasienPenyakitMenular);
        FormMenu.add(BtnPenilaianPasienImunitasRendah);
        FormMenu.add(BtnPenilaianPasienKeracunan);
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
                }else{                        
                    try {
                        jmlparsial=0;
                        if(aktifkanparsial.equals("yes")){
                            jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                        }
                        if(jmlparsial>0){    
                            SimpanPenangananDokter();
                        }else{
                            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            }else{
                                SimpanPenangananDokter();
                            }
                        } 
                    } catch (Exception e) {
                    }                      
                } 
                break;
            case 1:
                if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                    Valid.textKosong(kdptg,"Petugas");
                }else{
                    try {
                        jmlparsial=0;
                        if(aktifkanparsial.equals("yes")){
                            jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                        }
                        if(jmlparsial>0){ 
                            SimpanPenangananPetugas();
                        }else{
                            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            }else{
                                SimpanPenangananPetugas();
                            }
                        } 
                    } catch (Exception e) {
                    } 
                }  
                break;
            case 2:
                if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                    Valid.textKosong(KdDok2,"Dokter");
                }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                    Valid.textKosong(kdptg2,"Petugas");
                }else{      
                    try {
                        jmlparsial=0;
                        if(aktifkanparsial.equals("yes")){
                            jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kd_pj);
                        }
                        if(jmlparsial>0){ 
                            SimpanPenangananDokterPetugas();
                        }else {
                            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            }else{
                                SimpanPenangananDokterPetugas();
                            }
                        } 
                    } catch (Exception e) {
                    }                            
                }  
                break;
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
                            if(Sequel.menyimpantf("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
                                TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),
                                SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                LingkarPerut.getText(),TindakLanjut.getText(),TPenilaian.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText()})==true){
                                    tabModePemeriksaan.addRow(new Object[]{
                                        false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                        TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                        TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),LingkarPerut.getText(),TindakLanjut.getText(),TPenilaian.getText(),TInstruksi.getText(),TEvaluasi.getText(),
                                        KdPeg.getText(),TPegawai.getText(),Jabatan.getText()
                                    });
                                    TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                    TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                    TPemeriksaan.setText("");TAlergi.setText("");LingkarPerut.setText("");
                                    TindakLanjut.setText("");TPenilaian.setText("");TInstruksi.setText("");SpO2.setText("");
                                    TEvaluasi.setText("");cmbKesadaran.setSelectedIndex(0);
                                    LCount.setText(""+tabModePemeriksaan.getRowCount());
                            }
                        }else{
                            if(akses.getkode().equals(KdPeg.getText())){
                                if(Sequel.menyimpantf("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
                                    TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                    TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),
                                    SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                    LingkarPerut.getText(),TindakLanjut.getText(),TPenilaian.getText(),TInstruksi.getText(),TEvaluasi.getText(),KdPeg.getText()})==true){
                                        tabModePemeriksaan.addRow(new Object[]{
                                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                            TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),SpO2.getText(),TGCS.getText(),cmbKesadaran.getSelectedItem().toString(),
                                            TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),LingkarPerut.getText(),TindakLanjut.getText(),TPenilaian.getText(),TInstruksi.getText(),TEvaluasi.getText(),
                                            KdPeg.getText(),TPegawai.getText(),Jabatan.getText()
                                        });
                                        TSuhu.setText("");TTensi.setText("");TNadi.setText("");TRespirasi.setText("");
                                        TTinggi.setText("");TBerat.setText("");TGCS.setText("");TKeluhan.setText("");
                                        TPemeriksaan.setText("");TAlergi.setText("");LingkarPerut.setText("");
                                        TindakLanjut.setText("");TPenilaian.setText("");TInstruksi.setText("");SpO2.setText("");
                                        TEvaluasi.setText("");cmbKesadaran.setSelectedIndex(0);
                                        LCount.setText(""+tabModePemeriksaan.getRowCount());
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Hanya bisa disimpan oleh dokter/petugas yang bersangkutan..!!");
                            }
                        }
                    }
                }
                break;
            case 4:
                if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                        (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                        (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                        (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                        (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                        (!TDenominator.getText().trim().equals(""))){
                    if(Sequel.menyimpantf("pemeriksaan_obstetri_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",23,new String[]{
                        TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),
                        cmbKontraksi.getSelectedItem().toString(),TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),
                        cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),cmbDalam.getSelectedItem().toString(),TTebal.getText(),
                        cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),cmbKetuban.getSelectedItem().toString(),
                        cmbFeto.getSelectedItem().toString()})==true){
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
                }  
                break;
            case 5:
                if ((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                        (!TInspekuloGine.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                        (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                        (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                        (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                        (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                        (!TCavumDouglas.getText().trim().equals(""))) {
                    if(Sequel.menyimpantf("pemeriksaan_ginekologi_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20, new String[] {
                        TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),
                        cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                        TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                        TBentuk.getText(), TCavumUteri.getText(), cmbMobilitas.getSelectedItem().toString(),
                        TUkuran.getText(), cmbNyeriTekan.getSelectedItem().toString(),
                        TAdnexaKanan.getText(), TAdnexaKiri.getText(), TCavumDouglas.getText()})==true){
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
                }
                break;
            case 6:
                if(akses.getdiagnosa_pasien()==true){
                    panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Ralan",TCari.getText().trim());
                    panelDiagnosa1.simpan();
                }
                break;
            case 7:
                if((!KdDok3.getText().trim().equals(""))&&(!TDokter3.getText().trim().equals(""))&&(!Catatan.getText().trim().equals(""))){
                    if(Sequel.menyimpantf("catatan_perawatan","?,?,?,?,?","Data",5,new String[]{
                        Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                        TNoRw.getText(),KdDok3.getText(),Catatan.getText()
                    })==true){
                        TabModeCatatan.addRow(new Object[]{
                            false,TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),KdDok3.getText(),TDokter3.getText(),Catatan.getText()
                        });
                        Catatan.setText("");
                        LCount.setText(""+TabModeCatatan.getRowCount());
                    }
                }
                break;
            default:
                break;
        } 
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}


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
import inventory.DlgPermintaanStokPasien;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import rekammedis.RMDataResumePasien;
import laporan.DlgDiagnosaPenyakit;
import permintaan.DlgBookingOperasi;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMPenilaianAwalKeperawatanKebidananRanap;
import rekammedis.RMPenilaianAwalMedisRanapDewasa;
import rekammedis.RMPenilaianAwalMedisRanapKandungan;
import rekammedis.RMPenilaianFisioterapi;

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
    private DlgPasien pasien=new DlgPasien(null,false);
    private PreparedStatement ps,ps2,ps3,ps4,ps5,psrekening,ps6;
    private ResultSet rs,rsrekening;
    private int i=0,tinggi=0;
    private boolean sukses=false;  
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttlpendapatan=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0;
    private String Suspen_Piutang_Tindakan_Ranap="",Tindakan_Ranap="",Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="",Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="",
            Beban_Jasa_Sarana_Tindakan_Ranap="",Utang_Jasa_Sarana_Tindakan_Ranap="",Beban_Jasa_Menejemen_Tindakan_Ranap="",Utang_Jasa_Menejemen_Tindakan_Ranap="",
            HPP_BHP_Tindakan_Ranap="",Persediaan_BHP_Tindakan_Ranap="",kode_poli="",kamar="",jenisbayar="";

    /** Creates new form DlgRawatInap
     * @param parent
     * @param modal */
    public DlgRawatInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

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
            "Respirasi(/menit)","Tinggi(Cm)","Berat(Kg)","GCS(E,V,M)","Kesadaran","Subjek","Objek","Alergi","Asesmen","Plan",
            "Instruksi","NIP","Dokter/Paramedis","Profesi/Jabatan"}){
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
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
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
                column.setPreferredWidth(64);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(180);
            }else if(i==16){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(180);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
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
        TKdPrw1.setDocument(new batasInput((byte)15).getKata(TKdPrw1));
        TKdPrw2.setDocument(new batasInput((byte)15).getKata(TKdPrw2));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TLetak.setDocument(new batasInput((byte)50).getKata(TLetak));
        TTensi.setDocument(new batasInput((byte)8).getKata(TTensi));
        TCariPasien.setDocument(new batasInput((byte)20).getKata(TCariPasien));
        TKeluhan.setDocument(new batasInput((int)400).getKata(TKeluhan));      
        TPenilaian.setDocument(new batasInput((int)400).getKata(TPenilaian));  
        TindakLanjut.setDocument(new batasInput((int)400).getKata(TindakLanjut));        
        TTinggi.setDocument(new batasInput((byte)5).getKata(TTinggi));
        TBerat.setDocument(new batasInput((byte)5).getKata(TBerat));
        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));      
        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS)); 
        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TPemeriksaan.setDocument(new batasInput((int)400).getKata(TPemeriksaan));
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
                        TCariPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    }    
                    if(pasien.getTable2().getSelectedRow()!= -1){                   
                        TCariPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                    }
                    if(pasien.getTable3().getSelectedRow()!= -1){                   
                        TCariPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
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
        
        pasien.getTable2().addKeyListener(new KeyListener() {
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
        
        pasien.getTable3().addKeyListener(new KeyListener() {
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
                            TKdPrw1.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                            TNmPrw1.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                            TKdPrw1.requestFocus(); 
                        }else if(TabRawat.getSelectedIndex()==2){
                            TKdPrw2.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                            TNmPrw2.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                            BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                            Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                            JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                            JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                            KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                            Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                            TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                            TKdPrw2.requestFocus(); 
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
                            TKdPrw1.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),4).toString());   
                            TNmPrw1.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),7).toString());
                            TKdPrw1.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            TKdPrw2.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),4).toString());   
                            TNmPrw2.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),5).toString());
                            BagianRS.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),8).toString());
                            Bhp.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),9).toString());
                            JmDokter.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),10).toString());
                            JmPerawat.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),11).toString());
                            KSO.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),12).toString());
                            Menejemen.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),13).toString());
                            TTnd.setText(perawatan2.getTable().getValueAt(perawatan2.getTable().getSelectedRow(),7).toString());
                            TKdPrw2.requestFocus();
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
        ChkAccor.setSelected(false);
        isMenu();
        jam();
        
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
        TKdPrw1 = new widget.TextBox();
        TNmPrw1 = new widget.TextBox();
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
        TKdPrw2 = new widget.TextBox();
        TNmPrw2 = new widget.TextBox();
        btnTindakan6 = new widget.Button();
        btnTindakan7 = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        jLabel7 = new widget.Label();
        TSuhu = new widget.TextBox();
        jLabel4 = new widget.Label();
        TTensi = new widget.TextBox();
        jLabel25 = new widget.Label();
        TBerat = new widget.TextBox();
        TNadi = new widget.TextBox();
        jLabel24 = new widget.Label();
        TTinggi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel15 = new widget.Label();
        TAlergi = new widget.TextBox();
        jLabel23 = new widget.Label();
        TRespirasi = new widget.TextBox();
        jLabel22 = new widget.Label();
        TGCS = new widget.TextBox();
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
        jLabel41 = new widget.Label();
        cmbKesadaran = new widget.ComboBox();
        jLabel53 = new widget.Label();
        KdPeg = new widget.TextBox();
        TPegawai = new widget.TextBox();
        BtnSeekPegawai = new widget.Button();
        Jabatan = new widget.TextBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        TInstruksi = new widget.TextArea();
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
        BtnInputObat = new widget.Button();
        BtnObatBhp = new widget.Button();
        BtnBerkasDigital = new widget.Button();
        BtnPermintaanLab = new widget.Button();
        BtnPermintaanRad = new widget.Button();
        BtnJadwalOperasi = new widget.Button();
        BtnSKDP = new widget.Button();
        BtnRujukKeluar = new widget.Button();
        BtnCatatan = new widget.Button();
        BtnDiagnosa = new widget.Button();
        BtnAsuhanGizi = new widget.Button();
        BtnMonitoringAsuhanGizi = new widget.Button();
        BtnResume = new widget.Button();
        BtnAwalKeperawatanKandungan = new widget.Button();
        BtnAwalMedis = new widget.Button();
        BtnAwalMedisKandungan = new widget.Button();
        BtnAwalFisioterapi = new widget.Button();

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2021" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2021" }));
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

        TKdPrw1.setName("TKdPrw1"); // NOI18N
        TKdPrw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrw1KeyPressed(evt);
            }
        });
        panelGlass13.add(TKdPrw1);
        TKdPrw1.setBounds(108, 40, 100, 23);

        TNmPrw1.setEditable(false);
        TNmPrw1.setName("TNmPrw1"); // NOI18N
        panelGlass13.add(TNmPrw1);
        TNmPrw1.setBounds(210, 40, 605, 23);

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

        TKdPrw2.setName("TKdPrw2"); // NOI18N
        TKdPrw2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrw2KeyPressed(evt);
            }
        });
        panelGlass11.add(TKdPrw2);
        TKdPrw2.setBounds(108, 70, 100, 23);

        TNmPrw2.setEditable(false);
        TNmPrw2.setName("TNmPrw2"); // NOI18N
        panelGlass11.add(TNmPrw2);
        TNmPrw2.setBounds(210, 70, 605, 23);

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
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 243));
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
        ChkInput.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkInputItemStateChanged(evt);
            }
        });
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass12.setLayout(null);

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

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass12.add(jLabel4);
        jLabel4.setBounds(0, 190, 70, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTensi);
        TTensi.setBounds(73, 190, 55, 23);

        jLabel25.setText("Berat(Kg) :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelGlass12.add(jLabel25);
        jLabel25.setBounds(294, 160, 79, 23);

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
        TNadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNadiActionPerformed(evt);
            }
        });
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        panelGlass12.add(TNadi);
        TNadi.setBounds(378, 190, 55, 23);

        jLabel24.setText("Nadi(/menit) :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelGlass12.add(jLabel24);
        jLabel24.setBounds(294, 190, 79, 23);

        TTinggi.setFocusTraversalPolicyProvider(true);
        TTinggi.setName("TTinggi"); // NOI18N
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });
        panelGlass12.add(TTinggi);
        TTinggi.setBounds(237, 160, 55, 23);

        jLabel16.setText("Tinggi Badan(Cm) :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelGlass12.add(jLabel16);
        jLabel16.setBounds(134, 160, 100, 23);

        jLabel15.setText("Alergi :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelGlass12.add(jLabel15);
        jLabel15.setBounds(450, 40, 90, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        panelGlass12.add(TAlergi);
        TAlergi.setBounds(543, 40, 360, 23);

        jLabel23.setText("Respirasi(/menit) :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelGlass12.add(jLabel23);
        jLabel23.setBounds(134, 190, 100, 23);

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); // NOI18N
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });
        panelGlass12.add(TRespirasi);
        TRespirasi.setBounds(237, 190, 55, 23);

        jLabel22.setText("GCS(E,V,M) :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass12.add(jLabel22);
        jLabel22.setBounds(450, 10, 90, 23);

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); // NOI18N
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });
        panelGlass12.add(TGCS);
        TGCS.setBounds(543, 10, 55, 23);

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
        scrollPane3.setBounds(543, 70, 360, 42);

        jLabel37.setText("Asesmen :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass12.add(jLabel37);
        jLabel37.setBounds(450, 70, 90, 23);

        jLabel26.setText("Plan :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelGlass12.add(jLabel26);
        jLabel26.setBounds(450, 119, 90, 23);

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
        scrollPane4.setBounds(543, 119, 360, 43);

        jLabel41.setText("Kesadaran :");
        jLabel41.setName("jLabel41"); // NOI18N
        panelGlass12.add(jLabel41);
        jLabel41.setBounds(624, 10, 80, 23);

        cmbKesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma" }));
        cmbKesadaran.setName("cmbKesadaran"); // NOI18N
        cmbKesadaran.setPreferredSize(new java.awt.Dimension(62, 28));
        cmbKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKesadaranKeyPressed(evt);
            }
        });
        panelGlass12.add(cmbKesadaran);
        cmbKesadaran.setBounds(707, 10, 196, 23);

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
        Jabatan.setBounds(193, 40, 240, 23);

        jLabel54.setText("Profesi / Jabatan / Departemen :");
        jLabel54.setName("jLabel54"); // NOI18N
        panelGlass12.add(jLabel54);
        jLabel54.setBounds(0, 40, 190, 23);

        jLabel55.setText("Instruksi :");
        jLabel55.setName("jLabel55"); // NOI18N
        panelGlass12.add(jLabel55);
        jLabel55.setBounds(450, 169, 90, 23);

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
        scrollPane7.setBounds(543, 169, 360, 43);

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
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2021" }));
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

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
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
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRiwayat.setText("Riwayat Pasien");
        BtnRiwayat.setFocusPainted(false);
        BtnRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRiwayat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRiwayat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnRiwayat.setRoundRect(false);
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRiwayat);

        BtnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResepObat.setText("Input Resep");
        BtnResepObat.setFocusPainted(false);
        BtnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResepObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResepObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResepObat.setName("BtnResepObat"); // NOI18N
        BtnResepObat.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnResepObat.setRoundRect(false);
        BtnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResepObat);

        BtnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCopyResep.setText("Copy Resep");
        BtnCopyResep.setFocusPainted(false);
        BtnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCopyResep.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCopyResep.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCopyResep.setName("BtnCopyResep"); // NOI18N
        BtnCopyResep.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnCopyResep.setRoundRect(false);
        BtnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCopyResepActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCopyResep);

        BtnPermintaanStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanStok.setText("Permintaan Stok");
        BtnPermintaanStok.setFocusPainted(false);
        BtnPermintaanStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanStok.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanStok.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanStok.setName("BtnPermintaanStok"); // NOI18N
        BtnPermintaanStok.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnPermintaanStok.setRoundRect(false);
        BtnPermintaanStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanStokActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanStok);

        BtnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnInputObat.setText("Input Obat & BHP");
        BtnInputObat.setFocusPainted(false);
        BtnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnInputObat.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnInputObat.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnInputObat.setName("BtnInputObat"); // NOI18N
        BtnInputObat.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnInputObat.setRoundRect(false);
        BtnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInputObatActionPerformed(evt);
            }
        });
        FormMenu.add(BtnInputObat);

        BtnObatBhp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnObatBhp.setText("Data Obat & BHP");
        BtnObatBhp.setFocusPainted(false);
        BtnObatBhp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnObatBhp.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObatBhp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnObatBhp.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnObatBhp.setName("BtnObatBhp"); // NOI18N
        BtnObatBhp.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnObatBhp.setRoundRect(false);
        BtnObatBhp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObatBhpActionPerformed(evt);
            }
        });
        FormMenu.add(BtnObatBhp);

        BtnBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBerkasDigital.setText("Berkas Digital");
        BtnBerkasDigital.setFocusPainted(false);
        BtnBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBerkasDigital.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBerkasDigital.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBerkasDigital.setName("BtnBerkasDigital"); // NOI18N
        BtnBerkasDigital.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnBerkasDigital.setRoundRect(false);
        BtnBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBerkasDigitalActionPerformed(evt);
            }
        });
        FormMenu.add(BtnBerkasDigital);

        BtnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanLab.setText("Permintaan Lab");
        BtnPermintaanLab.setFocusPainted(false);
        BtnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanLab.setName("BtnPermintaanLab"); // NOI18N
        BtnPermintaanLab.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnPermintaanLab.setRoundRect(false);
        BtnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanLabActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanLab);

        BtnPermintaanRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnPermintaanRad.setText("Permintaan Rad");
        BtnPermintaanRad.setFocusPainted(false);
        BtnPermintaanRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnPermintaanRad.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnPermintaanRad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPermintaanRad.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnPermintaanRad.setName("BtnPermintaanRad"); // NOI18N
        BtnPermintaanRad.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnPermintaanRad.setRoundRect(false);
        BtnPermintaanRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPermintaanRadActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPermintaanRad);

        BtnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnJadwalOperasi.setText("Jadwal Operasi");
        BtnJadwalOperasi.setFocusPainted(false);
        BtnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnJadwalOperasi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnJadwalOperasi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnJadwalOperasi.setName("BtnJadwalOperasi"); // NOI18N
        BtnJadwalOperasi.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnJadwalOperasi.setRoundRect(false);
        BtnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalOperasiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnJadwalOperasi);

        BtnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnSKDP.setText("Surat Kontrol");
        BtnSKDP.setFocusPainted(false);
        BtnSKDP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnSKDP.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnSKDP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSKDP.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnSKDP.setName("BtnSKDP"); // NOI18N
        BtnSKDP.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnSKDP.setRoundRect(false);
        BtnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSKDPActionPerformed(evt);
            }
        });
        FormMenu.add(BtnSKDP);

        BtnRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnRujukKeluar.setText("Rujuk Keluar");
        BtnRujukKeluar.setFocusPainted(false);
        BtnRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnRujukKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnRujukKeluar.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnRujukKeluar.setName("BtnRujukKeluar"); // NOI18N
        BtnRujukKeluar.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnRujukKeluar.setRoundRect(false);
        BtnRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRujukKeluarActionPerformed(evt);
            }
        });
        FormMenu.add(BtnRujukKeluar);

        BtnCatatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCatatan.setText("Catatan Pasien");
        BtnCatatan.setFocusPainted(false);
        BtnCatatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCatatan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCatatan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCatatan.setName("BtnCatatan"); // NOI18N
        BtnCatatan.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnCatatan.setRoundRect(false);
        BtnCatatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCatatanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCatatan);

        BtnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnDiagnosa.setText("Diagnosa");
        BtnDiagnosa.setFocusPainted(false);
        BtnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnDiagnosa.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnDiagnosa.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnDiagnosa.setName("BtnDiagnosa"); // NOI18N
        BtnDiagnosa.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnDiagnosa.setRoundRect(false);
        BtnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnDiagnosa);

        BtnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAsuhanGizi.setText("Asuhan Gizi");
        BtnAsuhanGizi.setFocusPainted(false);
        BtnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAsuhanGizi.setName("BtnAsuhanGizi"); // NOI18N
        BtnAsuhanGizi.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnAsuhanGizi.setRoundRect(false);
        BtnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAsuhanGizi);

        BtnMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnMonitoringAsuhanGizi.setText("Monitoring Gizi");
        BtnMonitoringAsuhanGizi.setFocusPainted(false);
        BtnMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnMonitoringAsuhanGizi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMonitoringAsuhanGizi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnMonitoringAsuhanGizi.setName("BtnMonitoringAsuhanGizi"); // NOI18N
        BtnMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnMonitoringAsuhanGizi.setRoundRect(false);
        BtnMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMonitoringAsuhanGiziActionPerformed(evt);
            }
        });
        FormMenu.add(BtnMonitoringAsuhanGizi);

        BtnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnResume.setText("Resume Pasien");
        BtnResume.setFocusPainted(false);
        BtnResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnResume.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnResume.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnResume.setName("BtnResume"); // NOI18N
        BtnResume.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnResume.setRoundRect(false);
        BtnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResumeActionPerformed(evt);
            }
        });
        FormMenu.add(BtnResume);

        BtnAwalKeperawatanKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalKeperawatanKandungan.setText("Awal Keperawatan Kandungan");
        BtnAwalKeperawatanKandungan.setFocusPainted(false);
        BtnAwalKeperawatanKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalKeperawatanKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalKeperawatanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalKeperawatanKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalKeperawatanKandungan.setName("BtnAwalKeperawatanKandungan"); // NOI18N
        BtnAwalKeperawatanKandungan.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnAwalKeperawatanKandungan.setRoundRect(false);
        BtnAwalKeperawatanKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalKeperawatanKandunganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalKeperawatanKandungan);

        BtnAwalMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedis.setText("Awal Medis Umum");
        BtnAwalMedis.setFocusPainted(false);
        BtnAwalMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedis.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedis.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedis.setName("BtnAwalMedis"); // NOI18N
        BtnAwalMedis.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnAwalMedis.setRoundRect(false);
        BtnAwalMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedis);

        BtnAwalMedisKandungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalMedisKandungan.setText("Awal Medis Kandungan");
        BtnAwalMedisKandungan.setFocusPainted(false);
        BtnAwalMedisKandungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalMedisKandungan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalMedisKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalMedisKandungan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalMedisKandungan.setName("BtnAwalMedisKandungan"); // NOI18N
        BtnAwalMedisKandungan.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnAwalMedisKandungan.setRoundRect(false);
        BtnAwalMedisKandungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalMedisKandunganActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalMedisKandungan);

        BtnAwalFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAwalFisioterapi.setText("Awal Fisioterapi");
        BtnAwalFisioterapi.setFocusPainted(false);
        BtnAwalFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAwalFisioterapi.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAwalFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAwalFisioterapi.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAwalFisioterapi.setName("BtnAwalFisioterapi"); // NOI18N
        BtnAwalFisioterapi.setPreferredSize(new java.awt.Dimension(180, 23));
        BtnAwalFisioterapi.setRoundRect(false);
        BtnAwalFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAwalFisioterapiActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAwalFisioterapi);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
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

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TPemeriksaan,TTinggi);
}//GEN-LAST:event_TSuhuKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TBerat,TRespirasi);
}//GEN-LAST:event_TTensiKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
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
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");                              
                                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(JmDokter.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(KSO.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(BagianRS.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Bhp.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Menejemen.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                }
                                if(jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode())==false){
                                    sukses=false;
                                }
                            }else{
                                sukses=false;
                            }
                            
                            if(sukses==true){
                                Sequel.Commit();
                                tampilDr();
                                BtnBatalActionPerformed(evt);
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
                    }else if(TKdPrw1.getText().trim().equals("")||TNmPrw1.getText().trim().equals("")){
                        Valid.textKosong(TKdPrw1,"perawatan");
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText().trim())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        }else{
                            Sequel.AutoComitFalse();
                            sukses=true;
                            if(Sequel.menyimpantf("rawat_inap_pr","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                                TNoRw.getText(),TKdPrw1.getText(),kdptg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                BagianRS.getText(),Bhp.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                            })==true){
                                Sequel.queryu("delete from tampjurnal");
                                if(Valid.SetAngka(TTnd.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(JmPerawat.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(KSO.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(BagianRS.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Bhp.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Menejemen.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                }
                                if(jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode())==false){
                                    sukses=false;
                                }
                            }else{
                                sukses=false;
                            }
                            if(sukses==true){
                                Sequel.Commit();
                                tampilPr();
                                BtnBatalActionPerformed(evt);
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
                    }else if(TKdPrw2.getText().trim().equals("")||TNmPrw2.getText().trim().equals("")){
                        Valid.textKosong(TKdPrw2,"perawatan");
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText().trim())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        }else{
                            Sequel.AutoComitFalse();
                            sukses=true;
                            if(Sequel.menyimpantf("rawat_inap_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
                                TNoRw.getText(),TKdPrw2.getText(),KdDok2.getText(),kdptg2.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                                cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                                BagianRS.getText(),Bhp.getText(),JmDokter.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                            })==true){
                                Sequel.queryu("delete from tampjurnal");
                                if(Valid.SetAngka(TTnd.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(JmDokter.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(JmPerawat.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(KSO.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(BagianRS.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Bhp.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(Menejemen.getText())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                }
                                if(jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode())==false){
                                    sukses=false;
                                }
                            }else{
                                sukses=false;
                            }
                            
                            if(sukses==true){
                                Sequel.Commit();
                                tampilDrPr();
                                BtnBatalActionPerformed(evt);
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
                            (!TInstruksi.getText().trim().equals(""))){
                        if(KdPeg.getText().trim().equals("")||TPegawai.getText().trim().equals("")){
                            Valid.textKosong(KdPeg,"Dokter/Paramedis masih kosong...!!");
                        }else{
                            Sequel.menyimpan("pemeriksaan_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",18,new String[]{
                                TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),                      
                                TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),TBerat.getText(),TGCS.getText(),
                                cmbKesadaran.getSelectedItem().toString(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText(),
                                TPenilaian.getText(),TindakLanjut.getText(),TInstruksi.getText(),KdPeg.getText()
                            });
                            tampilPemeriksaan();
                            BtnBatalActionPerformed(evt);
                        }
                    }   break;
                case 4:
                    if((!TTinggi_uteri.getText().trim().equals(""))||(!TLetak.getText().trim().equals(""))||
                            (!TDenyut.getText().trim().equals(""))||(!TKualitas_mnt.getText().trim().equals(""))||
                            (!TKualitas_dtk.getText().trim().equals(""))||(!TVulva.getText().trim().equals(""))||
                            (!TPortio.getText().trim().equals(""))||(!TTebal.getText().trim().equals(""))||
                            (!TPembukaan.getText().trim().equals(""))||(!TPenurunan.getText().trim().equals(""))||
                            (!TDenominator.getText().trim().equals(""))){
                        Sequel.menyimpan("pemeriksaan_obstetri_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",23,new String[]{
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TTinggi_uteri.getText(),cmbJanin.getSelectedItem().toString(),TLetak.getText(),cmbPanggul.getSelectedItem().toString(),TDenyut.getText(),
                            cmbKontraksi.getSelectedItem().toString(),TKualitas_mnt.getText(),TKualitas_dtk.getText(),cmbFluksus.getSelectedItem().toString(),
                            cmbAlbus.getSelectedItem().toString(),TVulva.getText(),TPortio.getText(),cmbDalam.getSelectedItem().toString(),TTebal.getText(),
                            cmbArah.getSelectedItem().toString(),TPembukaan.getText(),TPenurunan.getText(),TDenominator.getText(),cmbKetuban.getSelectedItem().toString(),
                            cmbFeto.getSelectedItem().toString()
                        });
                        tampilPemeriksaanObstetri();
                        BtnBatalActionPerformed(evt);
                    }   break; 
                case 5:
                    if ((!TInspeksi.getText().trim().equals(""))||(!TInspeksiVulva.getText().trim().equals(""))||
                            (!TInspekuloGine.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TPortioInspekulo.getText().trim().equals(""))||(!TSondage.getText().trim().equals(""))||
                            (!TPortioDalam.getText().trim().equals(""))||(!TBentuk.getText().trim().equals(""))||
                            (!TCavumUteri.getText().trim().equals(""))||(!TUkuran.getText().trim().equals(""))||
                            (!TAdnexaKanan.getText().trim().equals(""))||(!TAdnexaKiri.getText().trim().equals(""))||
                            (!TCavumDouglas.getText().trim().equals(""))) {
                        Sequel.menyimpan("pemeriksaan_ginekologi_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",20, new String[] {
                            TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                            TInspeksi.getText(),TInspeksiVulva.getText(),TInspekuloGine.getText(),
                            cmbFluxusGine.getSelectedItem().toString(),cmbFluorGine.getSelectedItem().toString(), TVulvaInspekulo.getText(),
                            TPortioInspekulo.getText(), TSondage.getText(), TPortioDalam.getText(),
                            TBentuk.getText(), TCavumUteri.getText(), cmbMobilitas.getSelectedItem().toString(),
                            TUkuran.getText(), cmbNyeriTekan.getSelectedItem().toString(),
                            TAdnexaKanan.getText(), TAdnexaKiri.getText(), TCavumDouglas.getText()
                        });
                        tampilPemeriksaanGinekologi();
                        BtnBatalActionPerformed(evt);
                    } break;
                default:
                    break;
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
                Valid.pindah(evt,TKdPrw1,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,TKdPrw2,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,TInstruksi,BtnBatal);
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
        TKdPrw1.setText("");
        TNmPrw1.setText("");        
        TKdPrw2.setText("");
        TNmPrw2.setText("");
        TTensi.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TPenilaian.setText("");
        TindakLanjut.setText("");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
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
                            if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
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
                        }
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'");                             
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                      
                    if(sukses==true){
                        Sequel.Commit();
                        tampilDr();
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
                            if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
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
                        }                            
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'");                             
                        }
                        if(ttljmperawat>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        tampilPr();
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
                            if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i,1).toString())>0){
                                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
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
                        }                            
                    }
                    if(sukses==true){
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ranap+"'");                             
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");                              
                        }
                        if(ttljmperawat>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");                              
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");                               
                        }
                        if(ttlmenejemen>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");                                
                        }
                        if(ttljasasarana>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");                            
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());
                    }
                        
                    if(sukses==true){
                        Sequel.Commit();
                        tampilDrPr();
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
                            Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+
                                    "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(i,4).toString()+
                                    "' and jam_rawat='"+tbPemeriksaan.getValueAt(i,5).toString()+"' ");
                        }
                    }
                    tampilPemeriksaan();
                }   break;
            case 4:
                if(tabModeObstetri.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                }else{
                    for(i=0;i<tbPemeriksaanObstetri.getRowCount();i++){
                        if(tbPemeriksaanObstetri.getValueAt(i,0).toString().equals("true")){
                            Sequel.queryu("delete from pemeriksaan_obstetri_ranap where no_rawat='"+tbPemeriksaanObstetri.getValueAt(i,1).toString()+
                                    "' and tgl_perawatan='"+tbPemeriksaanObstetri.getValueAt(i,4).toString()+
                                    "' and jam_rawat='"+tbPemeriksaanObstetri.getValueAt(i,5).toString()+"' ");
                        }
                    }
                    tampilPemeriksaanObstetri();
                }   break;
            case 5:
                if(tabModeGinekologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!");
                    TNoRw.requestFocus();
                    
                }else {
                    for(i=0;i<tbPemeriksaanGinekologi.getRowCount();i++){
                        if(tbPemeriksaanGinekologi.getValueAt(i,0).toString().equals("true")){
                            Sequel.queryu("delete from pemeriksaan_ginekologi_ranap where no_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,1).toString()+
                                    "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(i,4).toString()+
                                    "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(i,5).toString()+"' ");
                        }
                    }
                    tampilPemeriksaanGinekologi();
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    String pas=" and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' ";
                    
                    String tgl=" pemeriksaan_ranap.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+pas;
                    Valid.MyReportqry("rptInapPemeriksaan.jasper","report","::[ Data Pemeriksaan Rawat Inap ]::",
                            "select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                            "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                            "pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                            "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                            "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.nip,pegawai.nama "+
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
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
                                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString()+"'","kd_rek='"+Tindakan_Ranap+"'");
                                    }
                                    if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),11).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");  
                                    }
                                    if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),14).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");
                                    }
                                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PERUBAHAN TINDAKAN RAWAT INAP PASIEN OLEH "+akses.getkode());

                                    if(sukses==true){
                                        Sequel.queryu("delete from tampjurnal");
                                            if(Valid.SetAngka(TTnd.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(JmDokter.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(KSO.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(BagianRS.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Bhp.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Menejemen.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                        }
                                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","PERUBAHAN TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                                    }
                                }else{
                                    sukses=false;
                                }  
                                
                                if(sukses==true){
                                    Sequel.Commit();
                                    tampilDr();
                                    BtnBatalActionPerformed(evt);
                                }else{
                                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                    Sequel.RollBack();
                                }
                                Sequel.AutoComitTrue();
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                        
                    }   break;
                case 1:
                    if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                        Valid.textKosong(kdptg,"Petugas");
                    }else if(TKdPrw1.getText().trim().equals("")||TNmPrw1.getText().trim().equals("")){
                        Valid.textKosong(TKdPrw1,"perawatan");
                    }else{
                        if(tbRawatPr.getSelectedRow()>-1){
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
                                        "no_rawat='"+TNoRw.getText()+"',kd_jenis_prw='"+TKdPrw1.getText()+
                                        "',nip='"+kdptg.getText()+"',material='"+BagianRS.getText()+
                                        "',bhp='"+Bhp.getText()+"',tarif_tindakanpr='"+JmPerawat.getText()+"',biaya_rawat='"+TTnd.getText()+
                                        "',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                        "kso='"+KSO.getText()+"',menejemen='"+Menejemen.getText()+"'")==true){
                                    
                                    Sequel.queryu("delete from tampjurnal");
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString()+"'","kd_rek='"+Tindakan_Ranap+"'");
                                    }
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");  
                                    }
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");
                                    }
                                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());

                                    if(sukses==true){
                                        Sequel.queryu("delete from tampjurnal");
                                        if(Valid.SetAngka(TTnd.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(JmPerawat.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(KSO.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(BagianRS.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Bhp.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Menejemen.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                        }
                                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                                    }
                                }else{
                                    sukses=false;
                                } 
                                if(sukses==true){
                                    Sequel.Commit();
                                    tampilPr();
                                    BtnBatalActionPerformed(evt);
                                }else{
                                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                    Sequel.RollBack();
                                }
                                Sequel.AutoComitTrue();
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                            TCari.requestFocus();
                        }
                    }   break;
                case 2:
                    if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                        Valid.textKosong(KdDok2,"Dokter");
                    }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                        Valid.textKosong(kdptg2,"Petugas");
                    }else if(TKdPrw2.getText().trim().equals("")||TNmPrw2.getText().trim().equals("")){
                        Valid.textKosong(TKdPrw2,"perawatan");                            
                    }else{
                        if(tbRawatDrPr.getSelectedRow()>-1){
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
                                        "no_rawat='"+TNoRw.getText()+"',kd_jenis_prw='"+TKdPrw2.getText()+
                                        "',nip='"+kdptg2.getText()+"',kd_dokter='"+KdDok2.getText()+"',material='"+BagianRS.getText()+
                                        "',bhp='"+Bhp.getText()+"',tarif_tindakanpr='"+JmPerawat.getText()+"',tarif_tindakandr='"+JmDokter.getText()+"',biaya_rawat='"+TTnd.getText()+
                                        "',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                        "kso='"+KSO.getText()+"',menejemen='"+Menejemen.getText()+"'")==true){
                                    
                                    Sequel.queryu("delete from tampjurnal");
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString()+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),13).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),14).toString()+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");         
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),15).toString()+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");                               
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),16).toString()+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'"); 
                                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),17).toString()+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'"); 
                                    }
                                    if(Valid.SetAngka(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','0','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kredit=kredit+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");    
                                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"','0'","debet=debet+'"+tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),18).toString()+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                    }
                                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","PEMBATALAN TINDAKAN RAWAT INAP PASIEN "+TNoRM.getText()+" "+TPasien.getText()+" OLEH "+akses.getkode());

                                    if(sukses==true){
                                        Sequel.queryu("delete from tampjurnal");
                                        if(Valid.SetAngka(TTnd.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','"+TTnd.getText()+"','0'","debet=debet+'"+(TTnd.getText())+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','0','"+TTnd.getText()+"'","kredit=kredit+'"+(TTnd.getText())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(JmDokter.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','"+JmDokter.getText()+"','0'","debet=debet+'"+(JmDokter.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+JmDokter.getText()+"'","kredit=kredit+'"+(JmDokter.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(JmPerawat.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','"+JmPerawat.getText()+"','0'","debet=debet+'"+(JmPerawat.getText())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','0','"+JmPerawat.getText()+"'","kredit=kredit+'"+(JmPerawat.getText())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(KSO.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','"+KSO.getText()+"','0'","debet=debet+'"+(KSO.getText())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','0','"+KSO.getText()+"'","kredit=kredit+'"+(KSO.getText())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(BagianRS.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban Jasa Sarana Tindakan Ranap','"+BagianRS.getText()+"','0'","debet=debet+'"+(BagianRS.getText())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang Jasa Sarana Tindakan Ranap','0','"+BagianRS.getText()+"'","kredit=kredit+'"+(BagianRS.getText())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Bhp.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP BHP Tindakan Ranap','"+Bhp.getText()+"','0'","debet=debet+'"+(Bhp.getText())+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ranap+"','Persediaan BHP Tindakan Ranap','0','"+Bhp.getText()+"'","kredit=kredit+'"+(Bhp.getText())+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ranap+"'");   
                                        }
                                        if(Valid.SetAngka(Menejemen.getText())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban Jasa Menejemen Tindakan Ranap','"+Menejemen.getText()+"','0'","debet=debet+'"+(Menejemen.getText())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");  
                                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang Jasa Menejemen Tindakan Ranap','0','"+Menejemen.getText()+"'","kredit=kredit+'"+(Menejemen.getText())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                        }
                                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT INAP PASIEN "+TPasien.getText());
                                    }   
                                }else{
                                    sukses=false;
                                } 
                                
                                if(sukses==true){
                                    Sequel.Commit();
                                    tampilDrPr();
                                    BtnBatalActionPerformed(evt);
                                }else{
                                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                    Sequel.RollBack();
                                }
                                Sequel.AutoComitTrue();                                    
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
                            (!KdPeg.getText().trim().equals(""))||(!TPegawai.getText().trim().equals(""))||(!TInstruksi.getText().trim().equals(""))){
                        if(tbPemeriksaan.getSelectedRow()>-1){
                            Sequel.mengedit("pemeriksaan_ranap","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+
                                "' and tgl_perawatan='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),4)+
                                "' and jam_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5)+"'",
                                "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                                "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                                "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                                "gcs='"+TGCS.getText()+"',kesadaran='"+cmbKesadaran.getSelectedItem()+"',alergi='"+TAlergi.getText()+"',tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',"+
                                "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',"+
                                "rtl='"+TindakLanjut.getText()+"',penilaian='"+TPenilaian.getText()+"',instruksi='"+TInstruksi.getText()+"',nip='"+KdPeg.getText()+"'");
                            tampilPemeriksaan();
                            BtnBatalActionPerformed(evt);
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
                            Sequel.mengedit("pemeriksaan_obstetri_ranap","no_rawat='"+tbPemeriksaanObstetri.getValueAt(tbPemeriksaanObstetri.getSelectedRow(),1)+
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
                                    "denominator='"+TDenominator.getText()+"', ketuban='"+cmbKetuban.getSelectedItem()+"', feto='"+cmbFeto.getSelectedItem()+"'");
                            tampilPemeriksaanObstetri();
                            BtnBatalActionPerformed(evt);
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
                            Sequel.mengedit("pemeriksaan_ginekologi_ranap","no_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),1)+
                                    "' and tgl_perawatan='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),4)+
                                    "' and jam_rawat='"+tbPemeriksaanGinekologi.getValueAt(tbPemeriksaanGinekologi.getSelectedRow(),5)+"'",
                                    "no_rawat='"+TNoRw.getText()+"', tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"', "+
                                    "jam_rawat='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"', "+
                                    "inspeksi='"+TInspeksi.getText()+"', inspeksi_vulva='"+TInspeksiVulva.getText()+"', inspekulo_gine='"+TInspekuloGine.getText()+"', "+
                                    "fluxus_gine='"+cmbFluxusGine.getSelectedItem()+"', fluor_gine='"+cmbFluorGine.getSelectedItem()+"', "+
                                    "vulva_inspekulo='"+TVulvaInspekulo.getText()+"', portio_inspekulo='"+TPortioInspekulo.getText()+"', sondage='"+TSondage.getText()+"', "+
                                    "portio_dalam='"+TPortioDalam.getText()+"', bentuk='"+TBentuk.getText()+"', cavum_uteri='"+TCavumUteri.getText()+"', "+
                                    "mobilitas='"+cmbMobilitas.getSelectedItem()+"', ukuran='"+TUkuran.getText()+"', nyeri_tekan='"+cmbNyeriTekan.getSelectedItem()+"',"+
                                    "adnexa_kanan='"+TAdnexaKanan.getText()+"', adnexa_kiri='"+TAdnexaKiri.getText()+"', cavum_douglas='"+TCavumDouglas.getText()+"'");
                            tampilPemeriksaanGinekologi();
                            BtnBatalActionPerformed(evt);
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

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        Valid.pindah(evt,TNadi,cmbKesadaran);
    }//GEN-LAST:event_TGCSKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        Valid.pindah(evt,TTensi,TNadi);
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,TRespirasi,TGCS);
    }//GEN-LAST:event_TNadiKeyPressed

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        Valid.pindah(evt,TTinggi,TTensi);
    }//GEN-LAST:event_TBeratKeyPressed

    private void TNadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNadiActionPerformed
        
    }//GEN-LAST:event_TNadiActionPerformed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        Valid.pindah(evt,TSuhu,TBerat);
    }//GEN-LAST:event_TTinggiKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
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
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,kdptg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,TNoRw,TKdPrw1);
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

    private void TKdPrw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrw1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakan4ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptg,BtnSimpan);
        }
    }//GEN-LAST:event_TKdPrw1KeyPressed

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat2,kdptg2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugas2ActionPerformed(null);
        }else{
            Valid.pindah(evt,KdDok2,TKdPrw2);
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
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter2,KdDok2.getText());
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

    private void TKdPrw2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrw2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakan6ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptg2,BtnSimpan);
        }
    }//GEN-LAST:event_TKdPrw2KeyPressed

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

    private void ChkInputItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkInputItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkInputItemStateChanged

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
            daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDok.getText(),Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()),"ranap");
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
                if(Sequel.cariInteger("select count(no_rawat) from stok_obat_pasien where no_rawat=? ",TNoRw.getText())>0){
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
                berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+TNoRw.getText());
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
            kode_poli=Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText());
            form.setNoRm(TNoRM.getText(),TPasien.getText(), kode_poli,Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?",kode_poli),KdDok.getText(),TDokter.getText());
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
            RMDataResumePasien resume=new RMDataResumePasien(null,false);
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

    private void cmbKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKesadaranKeyPressed
        Valid.pindah(evt,TGCS,TAlergi);
    }//GEN-LAST:event_cmbKesadaranKeyPressed

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
            Sequel.cariIsi("select nama from pegawai where nik=?",TPegawai,KdPeg.getText());
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
            form.emptTeks();
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),jenisbayar,TNoRM.getText());
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
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
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
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
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
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
            form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
            form.emptTeks();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAwalFisioterapiActionPerformed

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
    private widget.Button BtnAll;
    private widget.Button BtnAsuhanGizi;
    private widget.Button BtnAwalFisioterapi;
    private widget.Button BtnAwalKeperawatanKandungan;
    private widget.Button BtnAwalMedis;
    private widget.Button BtnAwalMedisKandungan;
    private widget.Button BtnBatal;
    private widget.Button BtnBerkasDigital;
    private widget.Button BtnCari;
    private widget.Button BtnCatatan;
    private widget.Button BtnCopyResep;
    private widget.Button BtnDiagnosa;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnInputObat;
    private widget.Button BtnJadwalOperasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnMonitoringAsuhanGizi;
    private widget.Button BtnObatBhp;
    private widget.Button BtnPermintaanLab;
    private widget.Button BtnPermintaanRad;
    private widget.Button BtnPermintaanStok;
    private widget.Button BtnPrint;
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
    private widget.Button BtnSimpan;
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
    private widget.TextBox TGCS;
    private widget.TextBox TInspeksi;
    private widget.TextBox TInspeksiVulva;
    private widget.TextBox TInspekuloGine;
    private widget.TextArea TInstruksi;
    private widget.TextBox TKdPrw;
    private widget.TextBox TKdPrw1;
    private widget.TextBox TKdPrw2;
    private widget.TextArea TKeluhan;
    private widget.TextBox TKualitas_dtk;
    private widget.TextBox TKualitas_mnt;
    private widget.TextBox TLetak;
    private widget.TextBox TNadi;
    private widget.TextBox TNmPrw;
    private widget.TextBox TNmPrw1;
    private widget.TextBox TNmPrw2;
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
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaanGinekologi;
    private widget.Table tbPemeriksaanObstetri;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    // End of variables declaration//GEN-END:variables

    public void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps=koneksi.prepareStatement("select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_dr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat,rawat_inap_dr.kd_jenis_prw, " +
                   "rawat_inap_dr.tarif_tindakandr,rawat_inap_dr.kso,rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_dr "+
                   "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? order by rawat_inap_dr.no_rawat desc");
            }else{
                ps=koneksi.prepareStatement("select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_dr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_dr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,rawat_inap_dr.biaya_rawat,rawat_inap_dr.kd_jenis_prw, " +
                   "rawat_inap_dr.tarif_tindakandr,rawat_inap_dr.kso,rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_dr "+
                   "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_dr.no_rawat like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_dr.kd_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and dokter.nm_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and tgl_perawatan like ? "+
                   " order by rawat_inap_dr.no_rawat desc");
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
                    ps.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(7,"%"+TCariPasien.getText()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(11,"%"+TCariPasien.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(15,"%"+TCariPasien.getText()+"%");
                    ps.setString(16,"%"+TCari.getText().trim()+"%");
                    ps.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(19,"%"+TCariPasien.getText()+"%");
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                    ps.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(23,"%"+TCariPasien.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(27,"%"+TCariPasien.getText()+"%");
                    ps.setString(28,"%"+TCari.getText().trim()+"%");
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
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "petugas inner join rawat_inap_pr "+
                   "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_pr.nip=petugas.nip where  "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? order by rawat_inap_pr.no_rawat desc"); 
            }else{
                ps2=koneksi.prepareStatement("select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_pr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_pr.nip,petugas.nama,"+
                   "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,rawat_inap_pr.biaya_rawat,rawat_inap_pr.kd_jenis_prw, " +
                   "rawat_inap_pr.tarif_tindakanpr,rawat_inap_pr.kso,rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "petugas inner join rawat_inap_pr "+
                   "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_pr.nip=petugas.nip where  "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.no_rawat like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.nip like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and petugas.nama like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_pr.tgl_perawatan like ? "+
                   "order by rawat_inap_pr.no_rawat desc"); 
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
                    ps2.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(7,"%"+TCariPasien.getText()+"%");
                    ps2.setString(8,"%"+TCari.getText().trim()+"%");
                    ps2.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(11,"%"+TCariPasien.getText()+"%");
                    ps2.setString(12,"%"+TCari.getText().trim()+"%");
                    ps2.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(15,"%"+TCariPasien.getText()+"%");
                    ps2.setString(16,"%"+TCari.getText().trim()+"%");
                    ps2.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(19,"%"+TCariPasien.getText()+"%");
                    ps2.setString(20,"%"+TCari.getText().trim()+"%");
                    ps2.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(23,"%"+TCariPasien.getText()+"%");
                    ps2.setString(24,"%"+TCari.getText().trim()+"%");
                    ps2.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps2.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps2.setString(27,"%"+TCariPasien.getText()+"%");
                    ps2.setString(28,"%"+TCari.getText().trim()+"%");
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
            TNmPrw1.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());
            TKdPrw1.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),10).toString());
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
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_drpr inner join petugas on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter and rawat_inap_drpr.nip=petugas.nip "+
                   "where rawat_inap_drpr.tgl_perawatan between ? and ? order by rawat_inap_drpr.no_rawat desc");
            }else{
                ps3=koneksi.prepareStatement("select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_inap_drpr.kd_jenis_prw,' ',jns_perawatan_inap.nm_perawatan),rawat_inap_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.biaya_rawat,rawat_inap_drpr.kd_jenis_prw, " +
                   "rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,rawat_inap_drpr.kso,rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.menejemen "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_drpr inner join petugas on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter and rawat_inap_drpr.nip=petugas.nip "+
                   "where rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_drpr.no_rawat like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_drpr.kd_dokter like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and dokter.nm_dokter like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and rawat_inap_drpr.nip like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and petugas.nama like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and tgl_perawatan like ?  "+
                   " order by rawat_inap_drpr.no_rawat desc");
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
                    ps3.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(7,"%"+TCariPasien.getText()+"%");
                    ps3.setString(8,"%"+TCari.getText().trim()+"%");
                    ps3.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(11,"%"+TCariPasien.getText()+"%");
                    ps3.setString(12,"%"+TCari.getText().trim()+"%");
                    ps3.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(15,"%"+TCariPasien.getText()+"%");
                    ps3.setString(16,"%"+TCari.getText().trim()+"%");
                    ps3.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(19,"%"+TCariPasien.getText()+"%");
                    ps3.setString(20,"%"+TCari.getText().trim()+"%");
                    ps3.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(23,"%"+TCariPasien.getText()+"%");
                    ps3.setString(24,"%"+TCari.getText().trim()+"%");
                    ps3.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(27,"%"+TCariPasien.getText()+"%");
                    ps3.setString(28,"%"+TCari.getText().trim()+"%");
                    ps3.setString(29,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(30,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(31,"%"+TCariPasien.getText()+"%");
                    ps3.setString(32,"%"+TCari.getText().trim()+"%");
                    ps3.setString(33,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps3.setString(34,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps3.setString(35,"%"+TCariPasien.getText()+"%");
                    ps3.setString(36,"%"+TCari.getText().trim()+"%");
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
            TNmPrw2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),4).toString());
            TKdPrw2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),12).toString());
            Valid.SetTgl(DTPTgl,tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),9).toString());
            isJns();
        }
    }

    private void isRawat(){
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void isJns(){
        if(TabRawat.getSelectedIndex()==0){
            if(!TKdPrw.getText().equals("")){
                Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
                Sequel.cariIsi("select bhp from jns_perawatan_inap where kd_jenis_prw=? ",Bhp,TKdPrw.getText());
                Sequel.cariIsi("select material from jns_perawatan_inap where kd_jenis_prw=? ",BagianRS,TKdPrw.getText());
                Sequel.cariIsi("select tarif_tindakandr from jns_perawatan_inap where kd_jenis_prw=? ",JmDokter,TKdPrw.getText());
                Sequel.cariIsi("select tarif_tindakanpr from jns_perawatan_inap where kd_jenis_prw=? ",JmPerawat,TKdPrw.getText());
                Sequel.cariIsi("select kso from jns_perawatan_inap where kd_jenis_prw=? ",KSO,TKdPrw.getText());
                Sequel.cariIsi("select menejemen from jns_perawatan_inap where kd_jenis_prw=? ",Menejemen,TKdPrw.getText());
                Sequel.cariIsi("select total_byrdr from jns_perawatan_inap where kd_jenis_prw=? ",TTnd,TKdPrw.getText());
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(!TKdPrw1.getText().equals("")){
                Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where kd_jenis_prw=? ",TNmPrw1,TKdPrw1.getText());
                Sequel.cariIsi("select bhp from jns_perawatan_inap where kd_jenis_prw=? ",Bhp,TKdPrw1.getText());
                Sequel.cariIsi("select material from jns_perawatan_inap where kd_jenis_prw=? ",BagianRS,TKdPrw1.getText());
                Sequel.cariIsi("select tarif_tindakandr from jns_perawatan_inap where kd_jenis_prw=? ",JmDokter,TKdPrw1.getText());
                Sequel.cariIsi("select tarif_tindakanpr from jns_perawatan_inap where kd_jenis_prw=? ",JmPerawat,TKdPrw1.getText());
                Sequel.cariIsi("select kso from jns_perawatan_inap where kd_jenis_prw=? ",KSO,TKdPrw1.getText());
                Sequel.cariIsi("select menejemen from jns_perawatan_inap where kd_jenis_prw=? ",Menejemen,TKdPrw1.getText());
                Sequel.cariIsi("select total_byrpr from jns_perawatan_inap where kd_jenis_prw=? ",TTnd,TKdPrw1.getText());
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(!TKdPrw2.getText().equals("")){
                Sequel.cariIsi("select nm_perawatan from jns_perawatan_inap where kd_jenis_prw=? ",TNmPrw2,TKdPrw2.getText());
                Sequel.cariIsi("select bhp from jns_perawatan_inap where kd_jenis_prw=? ",Bhp,TKdPrw2.getText());
                Sequel.cariIsi("select material from jns_perawatan_inap where kd_jenis_prw=? ",BagianRS,TKdPrw2.getText());
                Sequel.cariIsi("select tarif_tindakandr from jns_perawatan_inap where kd_jenis_prw=? ",JmDokter,TKdPrw2.getText());
                Sequel.cariIsi("select tarif_tindakanpr from jns_perawatan_inap where kd_jenis_prw=? ",JmPerawat,TKdPrw2.getText());
                Sequel.cariIsi("select kso from jns_perawatan_inap where kd_jenis_prw=? ",KSO,TKdPrw2.getText());
                Sequel.cariIsi("select menejemen from jns_perawatan_inap where kd_jenis_prw=? ",Menejemen,TKdPrw2.getText());
                Sequel.cariIsi("select total_byrdrpr from jns_perawatan_inap where kd_jenis_prw=? ",TTnd,TKdPrw2.getText());
            }
        }
    }    
    
    public void setNoRm(String norwt,Date awal,Date akhir) {
        TNoRw.setText(norwt);
        KdDok.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",norwt));
        if(KdDok.getText().equals("")){
            KdDok.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt));
        }
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        KdDok2.setText(KdDok.getText());
        TDokter2.setText(TDokter.getText()); 
        KdPeg.setText(KdDok.getText());
        TPegawai.setText(TDokter.getText()); ;
        Sequel.cariIsi("select jbtn from pegawai where nik=?",Jabatan,KdPeg.getText());
        
        isRawat();
        isPsien();
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
            PanelInput1.setPreferredSize(new Dimension(WIDTH,243));
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
        BtnAsuhanGizi.setVisible(akses.getasuhan_gizi());
        if(akses.getasuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnResume.setVisible(akses.getdata_resume_pasien());
        if(akses.getdata_resume_pasien()==true){
            tinggi=tinggi+24;
        }
        BtnMonitoringAsuhanGizi.setVisible(akses.getmonitoring_asuhan_gizi());
        if(akses.getmonitoring_asuhan_gizi()==true){
            tinggi=tinggi+24;
        }
        BtnJadwalOperasi.setEnabled(akses.getbooking_operasi());   
        if(akses.getbooking_operasi()==true){
            tinggi=tinggi+24;
        }
        BtnAwalKeperawatanKandungan.setEnabled(akses.getpenilaian_awal_keperawatan_ranapkebidanan());   
        if(akses.getpenilaian_awal_keperawatan_ranapkebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedis.setEnabled(akses.getpenilaian_awal_medis_ranap());   
        if(akses.getpenilaian_awal_medis_ranap()==true){
            tinggi=tinggi+24;
        }
        BtnAwalMedisKandungan.setEnabled(akses.getpenilaian_awal_medis_ranap_kebidanan());   
        if(akses.getpenilaian_awal_medis_ranap_kebidanan()==true){
            tinggi=tinggi+24;
        }
        BtnAwalFisioterapi.setEnabled(akses.getpenilaian_fisioterapi());   
        if(akses.getpenilaian_fisioterapi()==true){
            tinggi=tinggi+24;
        }
        FormMenu.setPreferredSize(new Dimension(150,(tinggi+10)));
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
            if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                ps4=koneksi.prepareStatement("select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                    "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                    "pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                    "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                    "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.nip,pegawai.nama,pegawai.jbtn "+
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_ranap on pemeriksaan_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where "+
                    "pemeriksaan_ranap.tgl_perawatan between ? and ? order by pemeriksaan_ranap.no_rawat desc"); 
            }else{
                ps4=koneksi.prepareStatement("select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                    "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                    "pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                    "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                    "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.nip,pegawai.nama,pegawai.jbtn "+
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pemeriksaan_ranap on pemeriksaan_ranap.no_rawat=reg_periksa.no_rawat "+
                    "inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where "+
                    "pemeriksaan_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and "+
                    "(pemeriksaan_ranap.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "pemeriksaan_ranap.alergi like ? or pemeriksaan_ranap.keluhan like ? or pemeriksaan_ranap.penilaian like ? or "+
                    "pemeriksaan_ranap.rtl like ? or pemeriksaan_ranap.pemeriksaan like ? or pegawai.nama like ?) "+
                   "order by pemeriksaan_ranap.no_rawat desc"); 
            }
                
            try{
                if(TCari.getText().equals("")&&TCariPasien.getText().equals("")){
                    ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps4.setString(3,"%"+TCariPasien.getText()+"%");
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
                        rs.getString(20),rs.getString(21),rs.getString(22)
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
            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),12).toString()); 
            cmbKesadaran.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),13).toString()); 
            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),14).toString()); 
            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),15).toString()); 
            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),16).toString()); 
            TPenilaian.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),17).toString()); 
            TindakLanjut.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),18).toString()); 
            TInstruksi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),19).toString()); 
            KdPeg.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),20).toString()); 
            TPegawai.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),21).toString()); 
            Jabatan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),22).toString()); 
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
                    "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ranap "+
                    "on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? order by pemeriksaan_obstetri_ranap.no_rawat desc");
            }else{
                ps5=koneksi.prepareStatement("select pemeriksaan_obstetri_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                    "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                    "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                    "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                    "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                    "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ranap "+
                    "on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_obstetri_ranap.no_rawat like ? or "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or  "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_obstetri_ranap.tinggi_uteri like ? or "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_obstetri_ranap.janin like ? or "+
                    "pemeriksaan_obstetri_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_obstetri_ranap.letak like ? "+
                    "order by pemeriksaan_obstetri_ranap.no_rawat desc");
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
                    ps5.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(7,"%"+TCariPasien.getText()+"%");
                    ps5.setString(8,"%"+TCari.getText().trim()+"%");
                    ps5.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(11,"%"+TCariPasien.getText()+"%");
                    ps5.setString(12,"%"+TCari.getText().trim()+"%");
                    ps5.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(15,"%"+TCariPasien.getText()+"%");
                    ps5.setString(16,"%"+TCari.getText().trim()+"%");
                    ps5.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(19,"%"+TCariPasien.getText()+"%");
                    ps5.setString(20,"%"+TCari.getText().trim()+"%");
                    ps5.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps5.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps5.setString(23,"%"+TCariPasien.getText()+"%");
                    ps5.setString(24,"%"+TCari.getText().trim()+"%");
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
                    "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ranap "+
                    "on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.no_rawat like ? or "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or  "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspeksi like ? or "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspeksi_vulva like ? or "+
                    "pemeriksaan_ginekologi_ranap.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? and pemeriksaan_ginekologi_ranap.inspekulo_gine like ? "+
                    "order by pemeriksaan_ginekologi_ranap.no_rawat desc");
            try {
                ps6.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(3,"%"+TCariPasien.getText()+"%");
                ps6.setString(4,"%"+TCari.getText().trim()+"%");
                ps6.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(7,"%"+TCariPasien.getText()+"%");
                ps6.setString(8,"%"+TCari.getText().trim()+"%");
                ps6.setString(9,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(10,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(11,"%"+TCariPasien.getText()+"%");
                ps6.setString(12,"%"+TCari.getText().trim()+"%");
                ps6.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(15,"%"+TCariPasien.getText()+"%");
                ps6.setString(16,"%"+TCari.getText().trim()+"%");
                ps6.setString(17,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(18,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(19,"%"+TCariPasien.getText()+"%");
                ps6.setString(20,"%"+TCari.getText().trim()+"%");
                ps6.setString(21,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps6.setString(22,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps6.setString(23,"%"+TCariPasien.getText()+"%");
                ps6.setString(24,"%"+TCari.getText().trim()+"%");
                
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
    
    
}

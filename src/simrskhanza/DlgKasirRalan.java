package simrskhanza;
import bridging.BPJSCekDataIndukKecelakaan;
import bridging.BPJSCekSuplesiJasaRaharja;
import rekammedis.RMRiwayatPerawatan;
import bridging.BPJSDataSEP;
import bridging.BPJSProgramPRB;
import bridging.BPJSSPRI;
import bridging.BPJSSuratKontrol;
import bridging.CoronaPasien;
import bridging.DlgDataTB;
import permintaan.DlgBookingOperasi;
import surat.SuratKontrol;
import bridging.INACBGPerawatanCorona;
import bridging.InhealthDataSJP;
import bridging.PCareDataPendaftaran;
import bridging.SisruteRujukanKeluar;
import inventory.DlgResepObat;
import inventory.DlgPemberianObat;
import laporan.DlgDiagnosaPenyakit;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import inventory.DlgCopyResep;
import inventory.DlgPenjualan;
import inventory.DlgPeresepanDokter;
import inventory.DlgPiutang;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingParsialRalan;
import keuangan.DlgCariPerawatanRalan;
import keuangan.DlgLhtPiutang;
import keuangan.DlgRBObatPoli;
import keuangan.DlgRBJmDokter;
import keuangan.DlgRBJmParamedis;
import keuangan.DlgRBTindakanPoli;
import keuangan.DlgRHJmDokter;
import keuangan.DlgRHJmParamedis;
import keuangan.Jurnal;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import rekammedis.RMDataResumePasien;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import permintaan.DlgPermintaanRanap;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMHemodialisa;
import rekammedis.RMDeteksiDiniCorona;
import rekammedis.RMMCU;
import rekammedis.RMPenilaianAwalKeperawatanBayiAnak;
import rekammedis.RMPenilaianAwalKeperawatanGigi;
import rekammedis.RMPenilaianAwalKeperawatanIGD;
import rekammedis.RMPenilaianAwalKeperawatanKebidanan;
import rekammedis.RMPenilaianAwalKeperawatanRalan;
import rekammedis.RMPenilaianAwalMedisIGD;
import rekammedis.RMPenilaianAwalMedisRalanAnak;
import rekammedis.RMPenilaianAwalMedisRalanDewasa;
import rekammedis.RMPenilaianAwalMedisRalanKandungan;
import rekammedis.RMPenilaianAwalMedisRalanPsikiatrik;
import rekammedis.RMPenilaianAwalMedisRalanTHT;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMTriaseIGD;
import rekammedis.RMUjiFungsiKFR;
import surat.SuratBebasNarkoba;
import surat.SuratBebasTato;
import surat.SuratButaWarna;
import surat.SuratCutiHamil;
import surat.SuratKeteranganBebasTBC;
import surat.SuratKeteranganCovid;
import surat.SuratKeteranganSehat;
import surat.SuratKewaspadaanKesehatan;
import surat.SuratPersetujuanPenolakanTindakan;
import surat.SuratSakit;
import surat.SuratSakitPihak2;
import surat.SuratTidakHamil;

/**
 *
 * @author dosen
 */
public final class DlgKasirRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModekasir,tabModekasir2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psotomatis,psotomatis2,pskasir,pscaripiutang,psrekening;
    private ResultSet rskasir,rsrekening;
    private String aktifkanparsial="no",kamar_inap_kasir_ralan=Sequel.cariIsi("select set_jam_minimal.kamar_inap_kasir_ralan from set_jam_minimal"),caripenjab="",filter="no",bangsal=Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi limit 1"),nonota="",
            sqlpsotomatis2="insert into rawat_jl_dr values (?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatis2petugas="insert into rawat_jl_pr values (?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatis2dokterpetugas="insert into rawat_jl_drpr values (?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatisdokterpetugas=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.total_byrdrpr,"+
                "jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan_dokterpetugas "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan_dokterpetugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan_dokterpetugas.kd_dokter=? and set_otomatis_tindakan_ralan_dokterpetugas.kd_pj=?",
            sqlpsotomatispetugas=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakanpr,jns_perawatan.total_byrpr,jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan_petugas "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan_petugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan_petugas.kd_pj=?",
            sqlpsotomatis=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakandr,jns_perawatan.total_byrdr,jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan.kd_dokter=? and set_otomatis_tindakan_ralan.kd_pj=?",
            namadokter="",namapoli="",order="reg_periksa.no_rawat desc",
            validasicatatan=Sequel.cariIsi("select set_validasi_catatan.tampilkan_catatan from set_validasi_catatan"),
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",
            Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",
            Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="",tampildiagnosa="",finger="",norawatdipilih="",normdipilih="";
    public DlgBilingRalan billing=new DlgBilingRalan(null,false);
    private int i=0,pilihan=0,sudah=0,jmlparsial=0;
    public DlgKamarInap kamarinap=new DlgKamarInap(null,false);
    private DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
    private boolean semua;
    private boolean sukses=false;
    private Jurnal jur=new Jurnal();
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgKasirRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        tabModekasir=new DefaultTableModel(null,new String[]{
            "Kd.Dokter","Dokter Dituju","No.RM","Pasien",
            "Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan P.J.",
            "Biaya Reg","Jenis Bayar","Status","No.Rawat","Tanggal",
            "Jam","No.Reg","Status Bayar","Stts Poli","Kd PJ","Kd Poli","No.Telp Pasien"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan.setModel(tabModekasir);

        tbKasirRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbKasirRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(130);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(105);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(47);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(50);
            }else if(i==17){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(95);
            }
        }
        tbKasirRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModekasir2=new DefaultTableModel(null,new String[]{
            "Kd.Dokter","Dokter Rujukan","Nomer RM","Pasien",
            "Poliklinik Rujukan","Penanggung Jawab","Alamat P.J.","Hubungan P.J.",
            "Jenis Bayar","Status","No.Rawat","Tanggal","Jam","Kode Poli","Kode PJ","No.Telp Pasien"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan2.setModel(tabModekasir2);

        tbKasirRalan2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbKasirRalan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(140);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(105);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(55);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(110);
            }
        }
        tbKasirRalan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte)100).getKata(CrPoli));
        TotalObat.setDocument(new batasInput((byte)20).getOnlyAngka(TotalObat));
        CrPtg.setDocument(new batasInput((byte)100).getKata(CrPtg)); 
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
            });
        }         
         
        billing.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgKasirRalan")){
                    if(billing.dokter.getTable().getSelectedRow()!= -1){
                        if(pilihan==1){
                            kddokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            kddokter.requestFocus();
                        }else if(pilihan==2){
                            CrPtg.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            TabRawatMouseClicked(null);
                            CrPtg.requestFocus();
                        }else if(pilihan==3){
                            CrDokter3.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            CrDokter3.requestFocus();
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
        
        
        billing.poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgKasirRalan")){
                    if(billing.poli.getTable().getSelectedRow()!= -1){
                        if(pilihan==1){
                            kdpoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),0).toString());
                            nmpoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),1).toString());
                            kdpoli.requestFocus();
                        }else if(pilihan==2){
                            CrPoli.setText(billing.poli.getTable().getValueAt(billing.poli.getTable().getSelectedRow(),1).toString());
                            CrPoli.requestFocus();   
                            TabRawatMouseClicked(null);
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
        
        billing.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgKasirRalan")){
                    tampilkasir();
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
        
        billing.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgKasirRalan")){
                    if(billing.penjab.getTable().getSelectedRow()!= -1){
                        if(filter.equals("no")){
                            kdpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),1).toString());
                            nmpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),2).toString());
                            kdpenjab.requestFocus();
                        }else if(filter.equals("yes")){
                            caripenjab=billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),1).toString();
                            tampilkasir();
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
        
        billing.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgKasirRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        billing.penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        dlgrwjl2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                TabRawatMouseClicked(null);
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
        
        DlgCatatan.setSize(595,34); 
        
        try {
            try {
                namapoli=koneksiDB.POLIAKTIFKASIRRALAN();
            } catch (Exception e) {
                namapoli="";
            }
            
            try {
                aktifkanparsial=koneksiDB.AKTIFKANBILLINGPARSIAL();
            } catch (Exception e) {
                aktifkanparsial="no";
            }
                
            try {
                if(koneksiDB.MENUTRANSPARAN().equals("yes")){
                    DlgCatatan.setOpacity(0.77f);
                }
            } catch (Exception e) {
                System.out.println("Notif Tansparant : "+e);
            }
        } catch (Exception ex) {
            System.out.println("Notif Load XML : "+ex);
        }
        
        try {
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,set_akun_ralan.Utang_KSO_Tindakan_Ralan,"+
                    "set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
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
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDataRM = new javax.swing.JMenu();
        MnRMIGD = new javax.swing.JMenu();
        MnDataTriaseIGD = new javax.swing.JMenuItem();
        MnPenilaianAwalKeperawatanIGD = new javax.swing.JMenuItem();
        MnPenilaianAwalMedisIGD = new javax.swing.JMenuItem();
        MnCatatanObservasiIGD = new javax.swing.JMenuItem();
        MnRMRawatJalan = new javax.swing.JMenu();
        MnPeniliaianAwalKeperawatanRalan = new javax.swing.JMenuItem();
        MnPeniliaianAwalKeperawatanGigi = new javax.swing.JMenuItem();
        MnPeniliaianAwalKeperawatanKebidanan = new javax.swing.JMenuItem();
        MnPeniliaianAwalKeperawatanBayiAnak = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisRalan = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisRalanKebidanan = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisRalanBayi = new javax.swing.JMenuItem();
        MnPenilaianAwalMedisRalanTHT = new javax.swing.JMenuItem();
        MnPenilaianAwalMedisRalanPsikiatri = new javax.swing.JMenuItem();
        MnPenilaianFisioterapi = new javax.swing.JMenuItem();
        MnPenilaianPsikolog = new javax.swing.JMenuItem();
        MnUjiFungsiKFR = new javax.swing.JMenuItem();
        MnPenilaianMCU = new javax.swing.JMenuItem();
        ppResume = new javax.swing.JMenuItem();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppDeteksiDIniCorona = new javax.swing.JMenuItem();
        MnHemodialisa = new javax.swing.JMenuItem();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnPermintaanRanap = new javax.swing.JMenuItem();
        ppMasukPoli = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnTindakanRalan = new javax.swing.JMenu();
        MnDataRalan = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaLabPA = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnObatRalan = new javax.swing.JMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnObatLangsung = new javax.swing.JMenuItem();
        MnDataPemberianObat = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnPiutangObat = new javax.swing.JMenuItem();
        MnCopyResep = new javax.swing.JMenuItem();
        MnPilihBilling = new javax.swing.JMenu();
        MnBillingParsial = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        MnRekap = new javax.swing.JMenu();
        MnRekapHarianDokter = new javax.swing.JMenuItem();
        MnRekapHarianParamedis = new javax.swing.JMenuItem();
        MnRekapBulananDokter = new javax.swing.JMenuItem();
        MnRekapBulananParamedis = new javax.swing.JMenuItem();
        MnRekapHarianPoli = new javax.swing.JMenuItem();
        MnRekapHarianObat = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcode2 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnGelang1 = new javax.swing.JMenuItem();
        MnGelang2 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang6 = new javax.swing.JMenuItem();
        MnGelang7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        MnPersetujuanPenolakanTindakan = new javax.swing.JMenuItem();
        MnSuratKontrol = new javax.swing.JMenuItem();
        MnSuratButaWarna = new javax.swing.JMenuItem();
        MnSuratBebasTato = new javax.swing.JMenuItem();
        MnSuratKewaspadaanKesehatan = new javax.swing.JMenuItem();
        MnCetakSuratBebasTBC = new javax.swing.JMenuItem();
        MnCetakSuratSehat = new javax.swing.JMenuItem();
        MnCetakSuratSehat1 = new javax.swing.JMenuItem();
        MnCetakSuratSehat2 = new javax.swing.JMenuItem();
        MnCetakBebasNarkoba = new javax.swing.JMenuItem();
        MnCetakSuratSakit = new javax.swing.JMenuItem();
        MnCetakSuratSakitPihak2 = new javax.swing.JMenuItem();
        MnCetakSuratSakit1 = new javax.swing.JMenuItem();
        MnCetakSuratHamil = new javax.swing.JMenuItem();
        MnCetakSuratCutiHamil = new javax.swing.JMenuItem();
        MnCetakSuratCovid = new javax.swing.JMenuItem();
        MnPersetujuanMedis = new javax.swing.JMenuItem();
        MnPersetujuanMedis1 = new javax.swing.JMenuItem();
        MnBuktiPelayananRalan = new javax.swing.JMenuItem();
        MnLembarRalan = new javax.swing.JMenuItem();
        MnBlangkoResep = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnPoliInternal = new javax.swing.JMenuItem();
        MnBridging = new javax.swing.JMenu();
        MnSEP = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppSuratPRI = new javax.swing.JMenuItem();
        ppProgramPRB = new javax.swing.JMenuItem();
        ppSuplesiJasaRaharja = new javax.swing.JMenuItem();
        ppDataIndukKecelakaan = new javax.swing.JMenuItem();
        MnSJP = new javax.swing.JMenuItem();
        MnPCare = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        ppPasienCorona = new javax.swing.JMenuItem();
        ppPerawatanCorona = new javax.swing.JMenuItem();
        MnTeridentifikasiTB = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkasRanap = new javax.swing.JMenuItem();
        ppBerkasDIterima = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBatal = new javax.swing.JMenuItem();
        MnDirujuk = new javax.swing.JMenuItem();
        MnDIrawat = new javax.swing.JMenuItem();
        MnMeninggal = new javax.swing.JMenuItem();
        MnPulangPaksa = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        MnStatusBaru = new javax.swing.JMenuItem();
        MnStatusLama = new javax.swing.JMenuItem();
        MnUrut = new javax.swing.JMenu();
        MnUrutNoRawatDesc = new javax.swing.JMenuItem();
        MnUrutNoRawatAsc = new javax.swing.JMenuItem();
        MnUrutTanggalDesc = new javax.swing.JMenuItem();
        MnUrutTanggalAsc = new javax.swing.JMenuItem();
        MnUrutDokterDesc = new javax.swing.JMenuItem();
        MnUrutDokterAsc = new javax.swing.JMenuItem();
        MnUrutPoliklinikDesc = new javax.swing.JMenuItem();
        MnUrutPoliklinikAsc = new javax.swing.JMenuItem();
        MnUrutPenjabDesc = new javax.swing.JMenuItem();
        MnUrutPenjabAsc = new javax.swing.JMenuItem();
        MnUrutStatusDesc = new javax.swing.JMenuItem();
        MnUrutStatusAsc = new javax.swing.JMenuItem();
        MnUrutRegDesc1 = new javax.swing.JMenuItem();
        MnUrutRegAsc1 = new javax.swing.JMenuItem();
        MnUrutRMDesc = new javax.swing.JMenuItem();
        MnUrutRMAsc = new javax.swing.JMenuItem();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        ppTampilkanBelumDiagnosa = new javax.swing.JMenuItem();
        MnGanti = new javax.swing.JMenu();
        MnPoli = new javax.swing.JMenuItem();
        MnDokter = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnGabungNoRawat = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusDeposit = new javax.swing.JMenuItem();
        MnHapusDiet = new javax.swing.JMenuItem();
        MnHapusDiagnosa = new javax.swing.JMenuItem();
        MnHapusDpjp = new javax.swing.JMenuItem();
        MnHapusHemodialisa = new javax.swing.JMenuItem();
        MnHapusKamarInap = new javax.swing.JMenuItem();
        MnHapusPotongan = new javax.swing.JMenuItem();
        MnHapusPiutang = new javax.swing.JMenuItem();
        MnHapusProsedur = new javax.swing.JMenuItem();
        MnHapusRanapGabung = new javax.swing.JMenuItem();
        MnHapusRujukKeluar = new javax.swing.JMenuItem();
        MnHapusRujukMasuk = new javax.swing.JMenuItem();
        MnHapusTambahan = new javax.swing.JMenuItem();
        MnHapusBookingOperasi = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnHapusTindakanRanapDokter = new javax.swing.JMenuItem();
        MnHapusTindakanRanapDokterParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRanapParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRalanDokter = new javax.swing.JMenuItem();
        MnHapusTindakanRalanDokterParamedis = new javax.swing.JMenuItem();
        MnHapusTindakanRalanParamedis = new javax.swing.JMenuItem();
        MnPemeriksaan = new javax.swing.JMenu();
        MnHapusPemeriksaanRalan = new javax.swing.JMenuItem();
        MnHapusPemeriksaanRanap = new javax.swing.JMenuItem();
        MnHapusLab = new javax.swing.JMenuItem();
        MnHapusRadiologi = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnHapusAturanPkaiObat = new javax.swing.JMenuItem();
        MnHapusObat = new javax.swing.JMenuItem();
        MnHapusResepObat = new javax.swing.JMenuItem();
        MnHapusResepPulang = new javax.swing.JMenuItem();
        MnHapusReturObat = new javax.swing.JMenuItem();
        MnHapusStokObatRanap = new javax.swing.JMenuItem();
        MnHapusSemua = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        WindowObatBhp = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel3 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        WindowGantiDokter = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan1 = new widget.Button();
        jLabel13 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        btnCariDokter = new widget.Button();
        Kd2 = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        WindowGantiPoli = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel18 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        btnCariPoli = new widget.Button();
        WindowCaraBayar = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnBayar = new widget.Button();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnPermintaan1 = new javax.swing.JMenu();
        MnJadwalOperasi1 = new javax.swing.JMenuItem();
        MnPermintaanLab1 = new javax.swing.JMenuItem();
        MnPermintaanRadiologi1 = new javax.swing.JMenuItem();
        MnKamarInap1 = new javax.swing.JMenuItem();
        MnTindakan1 = new javax.swing.JMenu();
        MnRawatJalan1 = new javax.swing.JMenuItem();
        MnPeriksaLab1 = new javax.swing.JMenuItem();
        MnPeriksaLabPA2 = new javax.swing.JMenuItem();
        MnPeriksaRadiologi1 = new javax.swing.JMenuItem();
        MnOperasi1 = new javax.swing.JMenuItem();
        MnDataRalan1 = new javax.swing.JMenuItem();
        MnObat1 = new javax.swing.JMenu();
        MnPemberianObat1 = new javax.swing.JMenuItem();
        MnNoResep1 = new javax.swing.JMenuItem();
        MnResepDOkter1 = new javax.swing.JMenuItem();
        MnObatLangsung1 = new javax.swing.JMenuItem();
        MnDataPemberianObat1 = new javax.swing.JMenuItem();
        MnPenjualan1 = new javax.swing.JMenuItem();
        MnCopyResep2 = new javax.swing.JMenuItem();
        MnBilling1 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        MenuInputData1 = new javax.swing.JMenu();
        ppBerkasDigital1 = new javax.swing.JMenuItem();
        ppIKP1 = new javax.swing.JMenuItem();
        MnDiagnosa1 = new javax.swing.JMenuItem();
        MnUrut1 = new javax.swing.JMenu();
        MnUrutNoRawatDesc2 = new javax.swing.JMenuItem();
        MnUrutNoRawatAsc2 = new javax.swing.JMenuItem();
        MnUrutTanggalDesc2 = new javax.swing.JMenuItem();
        MnUrutTanggalAsc2 = new javax.swing.JMenuItem();
        MnUrutDokterDesc2 = new javax.swing.JMenuItem();
        MnUrutDokterAsc2 = new javax.swing.JMenuItem();
        MnUrutPoliklinikDesc2 = new javax.swing.JMenuItem();
        MnUrutPoliklinikAsc2 = new javax.swing.JMenuItem();
        MnUrutPenjabDesc2 = new javax.swing.JMenuItem();
        MnUrutPenjabAsc2 = new javax.swing.JMenuItem();
        MnUrutStatusDesc2 = new javax.swing.JMenuItem();
        MnUrutStatusAsc2 = new javax.swing.JMenuItem();
        ppRiwayat1 = new javax.swing.JMenuItem();
        MnHapusRujukan = new javax.swing.JMenuItem();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        DlgSakit = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TglSakit1 = new widget.Tanggal();
        jLabel31 = new widget.Label();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        jLabel32 = new widget.Label();
        TglSakit2 = new widget.Tanggal();
        jLabel33 = new widget.Label();
        lmsakit = new widget.TextBox();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel37 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel24 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrPtg = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel20 = new widget.Label();
        cmbStatusBayar = new widget.ComboBox();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll1 = new widget.ScrollPane();
        tbKasirRalan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbKasirRalan2 = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel4 = new widget.Label();
        TNoRwCari = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoReg = new widget.TextBox();
        jLabel7 = new widget.Label();
        TNoRMCari = new widget.TextBox();
        jLabel8 = new widget.Label();
        TPasienCari = new widget.TextBox();

        jPopupMenu1.setForeground(new java.awt.Color(50, 50, 50));
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDataRM.setBackground(new java.awt.Color(250, 255, 245));
        MnDataRM.setForeground(new java.awt.Color(50, 50, 50));
        MnDataRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRM.setText("Data Rekam Medis");
        MnDataRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRM.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRM.setName("MnDataRM"); // NOI18N
        MnDataRM.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRMIGD.setBackground(new java.awt.Color(250, 255, 245));
        MnRMIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnRMIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMIGD.setText("RM Gawat Darurat");
        MnRMIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMIGD.setName("MnRMIGD"); // NOI18N
        MnRMIGD.setPreferredSize(new java.awt.Dimension(190, 26));

        MnDataTriaseIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnDataTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataTriaseIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnDataTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataTriaseIGD.setText("Triase Gawat Darurat");
        MnDataTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataTriaseIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataTriaseIGD.setName("MnDataTriaseIGD"); // NOI18N
        MnDataTriaseIGD.setPreferredSize(new java.awt.Dimension(210, 26));
        MnDataTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataTriaseIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnDataTriaseIGD);

        MnPenilaianAwalKeperawatanIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalKeperawatanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalKeperawatanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setText("Penilaian Awal Keperawatan IGD");
        MnPenilaianAwalKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalKeperawatanIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalKeperawatanIGD.setName("MnPenilaianAwalKeperawatanIGD"); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianAwalKeperawatanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalKeperawatanIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPenilaianAwalKeperawatanIGD);

        MnPenilaianAwalMedisIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalMedisIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalMedisIGD.setText("Penilaian Awal Medis IGD");
        MnPenilaianAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalMedisIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalMedisIGD.setName("MnPenilaianAwalMedisIGD"); // NOI18N
        MnPenilaianAwalMedisIGD.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalMedisIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPenilaianAwalMedisIGD);

        MnCatatanObservasiIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanObservasiIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanObservasiIGD.setText("Catatan Observasi IGD");
        MnCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanObservasiIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanObservasiIGD.setName("MnCatatanObservasiIGD"); // NOI18N
        MnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanObservasiIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnCatatanObservasiIGD);

        MnDataRM.add(MnRMIGD);

        MnRMRawatJalan.setBackground(new java.awt.Color(250, 255, 245));
        MnRMRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        MnRMRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMRawatJalan.setText("RM Rawat Jalan");
        MnRMRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMRawatJalan.setName("MnRMRawatJalan"); // NOI18N
        MnRMRawatJalan.setPreferredSize(new java.awt.Dimension(190, 26));

        MnPeniliaianAwalKeperawatanRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalKeperawatanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalKeperawatanRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalKeperawatanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalKeperawatanRalan.setText("Penilaian Awal Keperawatan Umum");
        MnPeniliaianAwalKeperawatanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalKeperawatanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalKeperawatanRalan.setName("MnPeniliaianAwalKeperawatanRalan"); // NOI18N
        MnPeniliaianAwalKeperawatanRalan.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalKeperawatanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalKeperawatanRalanActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalKeperawatanRalan);

        MnPeniliaianAwalKeperawatanGigi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalKeperawatanGigi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalKeperawatanGigi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalKeperawatanGigi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalKeperawatanGigi.setText("Penilaian Awal Keperawatan Gigi & Mulut");
        MnPeniliaianAwalKeperawatanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalKeperawatanGigi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalKeperawatanGigi.setName("MnPeniliaianAwalKeperawatanGigi"); // NOI18N
        MnPeniliaianAwalKeperawatanGigi.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalKeperawatanGigi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalKeperawatanGigiActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalKeperawatanGigi);

        MnPeniliaianAwalKeperawatanKebidanan.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalKeperawatanKebidanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalKeperawatanKebidanan.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalKeperawatanKebidanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalKeperawatanKebidanan.setText("Penilaian Awal Keperawatan Kebidanan & Kandungan");
        MnPeniliaianAwalKeperawatanKebidanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalKeperawatanKebidanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalKeperawatanKebidanan.setName("MnPeniliaianAwalKeperawatanKebidanan"); // NOI18N
        MnPeniliaianAwalKeperawatanKebidanan.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalKeperawatanKebidanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalKeperawatanKebidananActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalKeperawatanKebidanan);

        MnPeniliaianAwalKeperawatanBayiAnak.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalKeperawatanBayiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalKeperawatanBayiAnak.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalKeperawatanBayiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalKeperawatanBayiAnak.setText("Penilaian Awal Keperawatan Bayi/Anak");
        MnPeniliaianAwalKeperawatanBayiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalKeperawatanBayiAnak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalKeperawatanBayiAnak.setName("MnPeniliaianAwalKeperawatanBayiAnak"); // NOI18N
        MnPeniliaianAwalKeperawatanBayiAnak.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalKeperawatanBayiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalKeperawatanBayiAnakActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalKeperawatanBayiAnak);

        MnPeniliaianAwalMedisRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisRalan.setText("Penilaian Awal Medis Umum");
        MnPeniliaianAwalMedisRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisRalan.setName("MnPeniliaianAwalMedisRalan"); // NOI18N
        MnPeniliaianAwalMedisRalan.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalMedisRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisRalanActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalMedisRalan);

        MnPeniliaianAwalMedisRalanKebidanan.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisRalanKebidanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisRalanKebidanan.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisRalanKebidanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisRalanKebidanan.setText("Penilaian Awal Medis Kebidanan & Kandungan");
        MnPeniliaianAwalMedisRalanKebidanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisRalanKebidanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisRalanKebidanan.setName("MnPeniliaianAwalMedisRalanKebidanan"); // NOI18N
        MnPeniliaianAwalMedisRalanKebidanan.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalMedisRalanKebidanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisRalanKebidananActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalMedisRalanKebidanan);

        MnPeniliaianAwalMedisRalanBayi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisRalanBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisRalanBayi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisRalanBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisRalanBayi.setText("Penilaian Awal Medis Bayi/Anak");
        MnPeniliaianAwalMedisRalanBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisRalanBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisRalanBayi.setName("MnPeniliaianAwalMedisRalanBayi"); // NOI18N
        MnPeniliaianAwalMedisRalanBayi.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPeniliaianAwalMedisRalanBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisRalanBayiActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPeniliaianAwalMedisRalanBayi);

        MnPenilaianAwalMedisRalanTHT.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalMedisRalanTHT.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalMedisRalanTHT.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalMedisRalanTHT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalMedisRalanTHT.setText("Penilaian Awal Medis THT");
        MnPenilaianAwalMedisRalanTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalMedisRalanTHT.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalMedisRalanTHT.setName("MnPenilaianAwalMedisRalanTHT"); // NOI18N
        MnPenilaianAwalMedisRalanTHT.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPenilaianAwalMedisRalanTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalMedisRalanTHTActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPenilaianAwalMedisRalanTHT);

        MnPenilaianAwalMedisRalanPsikiatri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalMedisRalanPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalMedisRalanPsikiatri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalMedisRalanPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalMedisRalanPsikiatri.setText("Penilaian Awal Medis Psikiatri");
        MnPenilaianAwalMedisRalanPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalMedisRalanPsikiatri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalMedisRalanPsikiatri.setName("MnPenilaianAwalMedisRalanPsikiatri"); // NOI18N
        MnPenilaianAwalMedisRalanPsikiatri.setPreferredSize(new java.awt.Dimension(310, 26));
        MnPenilaianAwalMedisRalanPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalMedisRalanPsikiatriActionPerformed(evt);
            }
        });
        MnRMRawatJalan.add(MnPenilaianAwalMedisRalanPsikiatri);

        MnDataRM.add(MnRMRawatJalan);

        MnPenilaianFisioterapi.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianFisioterapi.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianFisioterapi.setText("Penilaian Awal Fisioterapi");
        MnPenilaianFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianFisioterapi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianFisioterapi.setName("MnPenilaianFisioterapi"); // NOI18N
        MnPenilaianFisioterapi.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPenilaianFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianFisioterapiActionPerformed(evt);
            }
        });
        MnDataRM.add(MnPenilaianFisioterapi);

        MnPenilaianPsikolog.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPsikolog.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPsikolog.setText("Penilaian Psikolog");
        MnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPsikolog.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPsikolog.setName("MnPenilaianPsikolog"); // NOI18N
        MnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPsikologActionPerformed(evt);
            }
        });
        MnDataRM.add(MnPenilaianPsikolog);

        MnUjiFungsiKFR.setBackground(new java.awt.Color(255, 255, 254));
        MnUjiFungsiKFR.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUjiFungsiKFR.setForeground(new java.awt.Color(50, 50, 50));
        MnUjiFungsiKFR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUjiFungsiKFR.setText("Uji Fungsi/Prosedur KFR");
        MnUjiFungsiKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUjiFungsiKFR.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUjiFungsiKFR.setName("MnUjiFungsiKFR"); // NOI18N
        MnUjiFungsiKFR.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUjiFungsiKFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUjiFungsiKFRActionPerformed(evt);
            }
        });
        MnDataRM.add(MnUjiFungsiKFR);

        MnPenilaianMCU.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMCU.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMCU.setText("Penilaian Medical Check Up");
        MnPenilaianMCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianMCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianMCU.setName("MnPenilaianMCU"); // NOI18N
        MnPenilaianMCU.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPenilaianMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMCUActionPerformed(evt);
            }
        });
        MnDataRM.add(MnPenilaianMCU);

        ppResume.setBackground(new java.awt.Color(255, 255, 254));
        ppResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResume.setForeground(new java.awt.Color(50, 50, 50));
        ppResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResume.setText("Resume");
        ppResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResume.setName("ppResume"); // NOI18N
        ppResume.setPreferredSize(new java.awt.Dimension(190, 26));
        ppResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResumeBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppResume);

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(190, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MnDataRM.add(MnDiagnosa);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(190, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppRiwayat);

        ppDeteksiDIniCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppDeteksiDIniCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDeteksiDIniCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppDeteksiDIniCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDeteksiDIniCorona.setText("Deteksi Dini Corona");
        ppDeteksiDIniCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDeteksiDIniCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDeteksiDIniCorona.setName("ppDeteksiDIniCorona"); // NOI18N
        ppDeteksiDIniCorona.setPreferredSize(new java.awt.Dimension(190, 26));
        ppDeteksiDIniCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDeteksiDIniCoronaBtnPrintActionPerformed(evt);
            }
        });
        MnDataRM.add(ppDeteksiDIniCorona);

        MnHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHemodialisa.setText("Hemodialisa");
        MnHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHemodialisa.setName("MnHemodialisa"); // NOI18N
        MnHemodialisa.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHemodialisaActionPerformed(evt);
            }
        });
        MnDataRM.add(MnHemodialisa);

        jPopupMenu1.add(MnDataRM);

        MnPermintaan.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Pemeriksaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        MnPermintaanRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRanap.setText("Rawat Inap");
        MnPermintaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRanap.setName("MnPermintaanRanap"); // NOI18N
        MnPermintaanRanap.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRanapActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRanap);

        jPopupMenu1.add(MnPermintaan);

        ppMasukPoli.setBackground(new java.awt.Color(255, 255, 254));
        ppMasukPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppMasukPoli.setForeground(new java.awt.Color(50, 50, 50));
        ppMasukPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppMasukPoli.setText("Antrian Masuk Poli");
        ppMasukPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppMasukPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppMasukPoli.setName("ppMasukPoli"); // NOI18N
        ppMasukPoli.setPreferredSize(new java.awt.Dimension(200, 26));
        ppMasukPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppMasukPoliBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppMasukPoli);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(50, 50, 50));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnTindakanRalan.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakanRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnTindakanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakanRalan.setText("Tindakan & Pemeriksaan");
        MnTindakanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakanRalan.setName("MnTindakanRalan"); // NOI18N
        MnTindakanRalan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnDataRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnDataRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnDataRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRalan.setText("Data Tindakan Rawat Jalan");
        MnDataRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRalan.setName("MnDataRalan"); // NOI18N
        MnDataRalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataRalanActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnDataRalan);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab PK");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnPeriksaLab);

        MnPeriksaLabPA.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLabPA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLabPA.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLabPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLabPA.setText("Periksa Lab PA");
        MnPeriksaLabPA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLabPA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLabPA.setName("MnPeriksaLabPA"); // NOI18N
        MnPeriksaLabPA.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaLabPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabPAActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnPeriksaLabPA);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnPeriksaRadiologi);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnOperasi);

        jPopupMenu1.add(MnTindakanRalan);

        MnObatRalan.setBackground(new java.awt.Color(250, 255, 245));
        MnObatRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnObatRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatRalan.setText("Obat");
        MnObatRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatRalan.setName("MnObatRalan"); // NOI18N
        MnObatRalan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnPemberianObat);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(50, 50, 50));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(200, 26));
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnNoResep);

        MnResepDOkter.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(50, 50, 50));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(200, 26));
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnResepDOkter);

        MnObatLangsung.setBackground(new java.awt.Color(255, 255, 254));
        MnObatLangsung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung.setForeground(new java.awt.Color(50, 50, 50));
        MnObatLangsung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung.setText("Total Tagihan Obat");
        MnObatLangsung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung.setName("MnObatLangsung"); // NOI18N
        MnObatLangsung.setPreferredSize(new java.awt.Dimension(200, 26));
        MnObatLangsung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsungActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnObatLangsung);

        MnDataPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnDataPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataPemberianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnDataPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataPemberianObat.setText("Data Pemberian Obat");
        MnDataPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataPemberianObat.setName("MnDataPemberianObat"); // NOI18N
        MnDataPemberianObat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDataPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataPemberianObatActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnDataPemberianObat);

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnPenjualan);

        MnPiutangObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPiutangObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPiutangObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPiutangObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPiutangObat.setText("Piutang Obat/Alkes/Barang");
        MnPiutangObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPiutangObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPiutangObat.setName("MnPiutangObat"); // NOI18N
        MnPiutangObat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPiutangObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPiutangObatActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnPiutangObat);

        MnCopyResep.setBackground(new java.awt.Color(255, 255, 254));
        MnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyResep.setForeground(new java.awt.Color(50, 50, 50));
        MnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyResep.setText("Copy Resep Dokter");
        MnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCopyResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCopyResep.setName("MnCopyResep"); // NOI18N
        MnCopyResep.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyResepActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnCopyResep);

        jPopupMenu1.add(MnObatRalan);

        MnPilihBilling.setBackground(new java.awt.Color(250, 255, 245));
        MnPilihBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnPilihBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPilihBilling.setText("Billing/Pembayaran Pasien");
        MnPilihBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihBilling.setName("MnPilihBilling"); // NOI18N
        MnPilihBilling.setPreferredSize(new java.awt.Dimension(200, 26));

        MnBillingParsial.setBackground(new java.awt.Color(255, 255, 254));
        MnBillingParsial.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBillingParsial.setForeground(new java.awt.Color(50, 50, 50));
        MnBillingParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBillingParsial.setText("Billing Parsial");
        MnBillingParsial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBillingParsial.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBillingParsial.setName("MnBillingParsial"); // NOI18N
        MnBillingParsial.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBillingParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingParsialActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBillingParsial);

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing Total");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBilling);

        jPopupMenu1.add(MnPilihBilling);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(200, 1));
        jPopupMenu1.add(jSeparator12);

        MnRekap.setBackground(new java.awt.Color(250, 255, 245));
        MnRekap.setForeground(new java.awt.Color(50, 50, 50));
        MnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekap.setText("Rekap Data");
        MnRekap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekap.setName("MnRekap"); // NOI18N
        MnRekap.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRekapHarianDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHarianDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapHarianDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianDokter.setText("Rekap Harian Dokter ");
        MnRekapHarianDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianDokter.setName("MnRekapHarianDokter"); // NOI18N
        MnRekapHarianDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianDokter);

        MnRekapHarianParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHarianParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapHarianParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianParamedis.setText("Rekap Harian Paramedis");
        MnRekapHarianParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianParamedis.setName("MnRekapHarianParamedis"); // NOI18N
        MnRekapHarianParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianParamedis);

        MnRekapBulananDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulananDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapBulananDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananDokter.setLabel("Rekap Bulanan Dokter ");
        MnRekapBulananDokter.setName("MnRekapBulananDokter"); // NOI18N
        MnRekapBulananDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananDokter);

        MnRekapBulananParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapBulananParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapBulananParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananParamedis.setText("Rekap Bulanan Paramedis");
        MnRekapBulananParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananParamedis.setName("MnRekapBulananParamedis"); // NOI18N
        MnRekapBulananParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananParamedis);

        MnRekapHarianPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHarianPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianPoli.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapHarianPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianPoli.setText("Rekap Harian Poli");
        MnRekapHarianPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianPoli.setName("MnRekapHarianPoli"); // NOI18N
        MnRekapHarianPoli.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianPoliActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianPoli);

        MnRekapHarianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapHarianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnRekapHarianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianObat.setText("Rekap Harian Obat");
        MnRekapHarianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianObat.setName("MnRekapHarianObat"); // NOI18N
        MnRekapHarianObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianObatActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianObat);

        jPopupMenu1.add(MnRekap);

        jMenu6.setBackground(new java.awt.Color(250, 255, 245));
        jMenu6.setForeground(new java.awt.Color(50, 50, 50));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu6.setText("Label & Barcode");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu6.setName("jMenu6"); // NOI18N
        jMenu6.setPreferredSize(new java.awt.Dimension(200, 26));

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker1.setText("Label Tracker 2");
        MnLabelTracker1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker1.setName("MnLabelTracker1"); // NOI18N
        MnLabelTracker1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker2.setText("Label Tracker 3");
        MnLabelTracker2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker2.setName("MnLabelTracker2"); // NOI18N
        MnLabelTracker2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker3.setText("Label Tracker 4");
        MnLabelTracker3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker3.setName("MnLabelTracker3"); // NOI18N
        MnLabelTracker3.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker3);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Barcode Perawatan 1");
        MnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 2");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode1);

        MnBarcode2.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode2.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode2.setText("Barcode RM");
        MnBarcode2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode2.setName("MnBarcode2"); // NOI18N
        MnBarcode2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode2);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label Rekam Medis 10");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcodeRM9);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien 1");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang1);

        MnGelang2.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang2.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang2.setText("Gelang Pasien 2");
        MnGelang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang2.setName("MnGelang2"); // NOI18N
        MnGelang2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang2);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang3.setText("Gelang Pasien 3");
        MnGelang3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang3.setName("MnGelang3"); // NOI18N
        MnGelang3.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang3);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang4.setText("Gelang Pasien 4");
        MnGelang4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang4.setName("MnGelang4"); // NOI18N
        MnGelang4.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang4ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang4);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang5.setText("Gelang Pasien 5");
        MnGelang5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang5.setName("MnGelang5"); // NOI18N
        MnGelang5.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang5ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang5);

        MnGelang6.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang6.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang6.setText("Gelang Pasien 6");
        MnGelang6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang6.setName("MnGelang6"); // NOI18N
        MnGelang6.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang6ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang6);

        MnGelang7.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang7.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang7.setText("Gelang Pasien 7");
        MnGelang7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang7.setName("MnGelang7"); // NOI18N
        MnGelang7.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang7ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang7);

        jPopupMenu1.add(jMenu6);

        jMenu4.setBackground(new java.awt.Color(250, 255, 245));
        jMenu4.setForeground(new java.awt.Color(50, 50, 50));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu4.setText("Surat-Surat");
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(240, 26));

        MnPersetujuanPenolakanTindakan.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanPenolakanTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanPenolakanTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanPenolakanTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanPenolakanTindakan.setText("Persetujuan/Penolakan Tindakan");
        MnPersetujuanPenolakanTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanPenolakanTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanPenolakanTindakan.setName("MnPersetujuanPenolakanTindakan"); // NOI18N
        MnPersetujuanPenolakanTindakan.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPersetujuanPenolakanTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanPenolakanTindakanActionPerformed(evt);
            }
        });
        jMenu4.add(MnPersetujuanPenolakanTindakan);

        MnSuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratKontrol.setText("Surat Kontrol");
        MnSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratKontrol.setName("MnSuratKontrol"); // NOI18N
        MnSuratKontrol.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratKontrolActionPerformed(evt);
            }
        });
        jMenu4.add(MnSuratKontrol);

        MnSuratButaWarna.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratButaWarna.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratButaWarna.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratButaWarna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratButaWarna.setText("Surat Buta Warna");
        MnSuratButaWarna.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratButaWarna.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratButaWarna.setName("MnSuratButaWarna"); // NOI18N
        MnSuratButaWarna.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratButaWarna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratButaWarnaActionPerformed(evt);
            }
        });
        jMenu4.add(MnSuratButaWarna);

        MnSuratBebasTato.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratBebasTato.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratBebasTato.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratBebasTato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratBebasTato.setText("Surat Bebas Tato");
        MnSuratBebasTato.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratBebasTato.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratBebasTato.setName("MnSuratBebasTato"); // NOI18N
        MnSuratBebasTato.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratBebasTato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratBebasTatoActionPerformed(evt);
            }
        });
        jMenu4.add(MnSuratBebasTato);

        MnSuratKewaspadaanKesehatan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratKewaspadaanKesehatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratKewaspadaanKesehatan.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratKewaspadaanKesehatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratKewaspadaanKesehatan.setText("Surat Kewaspadaan Kesehatan");
        MnSuratKewaspadaanKesehatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratKewaspadaanKesehatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratKewaspadaanKesehatan.setName("MnSuratKewaspadaanKesehatan"); // NOI18N
        MnSuratKewaspadaanKesehatan.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratKewaspadaanKesehatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratKewaspadaanKesehatanActionPerformed(evt);
            }
        });
        jMenu4.add(MnSuratKewaspadaanKesehatan);

        MnCetakSuratBebasTBC.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratBebasTBC.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratBebasTBC.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratBebasTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratBebasTBC.setText("Surat Keterangan Bebas TBC");
        MnCetakSuratBebasTBC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratBebasTBC.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratBebasTBC.setName("MnCetakSuratBebasTBC"); // NOI18N
        MnCetakSuratBebasTBC.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratBebasTBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratBebasTBCActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratBebasTBC);

        MnCetakSuratSehat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSehat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat.setText("Surat Keterangan Sehat 1");
        MnCetakSuratSehat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat.setName("MnCetakSuratSehat"); // NOI18N
        MnCetakSuratSehat.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSehat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehatActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat);

        MnCetakSuratSehat1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSehat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat1.setText("Surat Keterangan Sehat 2");
        MnCetakSuratSehat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat1.setName("MnCetakSuratSehat1"); // NOI18N
        MnCetakSuratSehat1.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSehat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehat1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat1);

        MnCetakSuratSehat2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSehat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat2.setText("Surat Keterangan Sehat 3");
        MnCetakSuratSehat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat2.setName("MnCetakSuratSehat2"); // NOI18N
        MnCetakSuratSehat2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSehat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehat2ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat2);

        MnCetakBebasNarkoba.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakBebasNarkoba.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakBebasNarkoba.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakBebasNarkoba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakBebasNarkoba.setText("Surat Keterangan Bebas Narkoba");
        MnCetakBebasNarkoba.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakBebasNarkoba.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakBebasNarkoba.setName("MnCetakBebasNarkoba"); // NOI18N
        MnCetakBebasNarkoba.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakBebasNarkoba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakBebasNarkobaActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakBebasNarkoba);

        MnCetakSuratSakit.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit.setText("Surat Cuti Sakit");
        MnCetakSuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit.setName("MnCetakSuratSakit"); // NOI18N
        MnCetakSuratSakit.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit);

        MnCetakSuratSakitPihak2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakitPihak2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakitPihak2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakitPihak2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakitPihak2.setText("Surat Cuti Sakit Pihak Ke 2");
        MnCetakSuratSakitPihak2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakitPihak2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakitPihak2.setName("MnCetakSuratSakitPihak2"); // NOI18N
        MnCetakSuratSakitPihak2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakitPihak2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitPihak2ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakitPihak2);

        MnCetakSuratSakit1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit1.setText("Surat Keterangan Rawat Inap");
        MnCetakSuratSakit1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit1.setName("MnCetakSuratSakit1"); // NOI18N
        MnCetakSuratSakit1.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit1);

        MnCetakSuratHamil.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratHamil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratHamil.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratHamil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratHamil.setText("Surat Keterangan Hamil/Tidak Hamil");
        MnCetakSuratHamil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratHamil.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratHamil.setName("MnCetakSuratHamil"); // NOI18N
        MnCetakSuratHamil.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratHamil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratHamilActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratHamil);

        MnCetakSuratCutiHamil.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratCutiHamil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratCutiHamil.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratCutiHamil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratCutiHamil.setText("Surat Cuti Hamil");
        MnCetakSuratCutiHamil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratCutiHamil.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratCutiHamil.setName("MnCetakSuratCutiHamil"); // NOI18N
        MnCetakSuratCutiHamil.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratCutiHamil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratCutiHamilActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratCutiHamil);

        MnCetakSuratCovid.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratCovid.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratCovid.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratCovid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratCovid.setText("Surat Keterangan Covid");
        MnCetakSuratCovid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratCovid.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratCovid.setName("MnCetakSuratCovid"); // NOI18N
        MnCetakSuratCovid.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratCovid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratCovidActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratCovid);

        MnPersetujuanMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanMedis.setText("Persetujuan Medis");
        MnPersetujuanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanMedis.setName("MnPersetujuanMedis"); // NOI18N
        MnPersetujuanMedis.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPersetujuanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanMedisActionPerformed(evt);
            }
        });
        jMenu4.add(MnPersetujuanMedis);

        MnPersetujuanMedis1.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanMedis1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanMedis1.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanMedis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanMedis1.setText("Persetujuan Medis 2");
        MnPersetujuanMedis1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanMedis1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanMedis1.setName("MnPersetujuanMedis1"); // NOI18N
        MnPersetujuanMedis1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPersetujuanMedis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanMedis1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnPersetujuanMedis1);

        MnBuktiPelayananRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnBuktiPelayananRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBuktiPelayananRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnBuktiPelayananRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBuktiPelayananRalan.setText("Surat Jaminan & Bukti Pelayanan Ralan");
        MnBuktiPelayananRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBuktiPelayananRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBuktiPelayananRalan.setName("MnBuktiPelayananRalan"); // NOI18N
        MnBuktiPelayananRalan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnBuktiPelayananRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBuktiPelayananRalanActionPerformed(evt);
            }
        });
        jMenu4.add(MnBuktiPelayananRalan);

        MnLembarRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarRalan.setMnemonic('w');
        MnLembarRalan.setText("Lembar Rawat Jalan");
        MnLembarRalan.setToolTipText("W");
        MnLembarRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarRalan.setName("MnLembarRalan"); // NOI18N
        MnLembarRalan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnLembarRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarRalanActionPerformed(evt);
            }
        });
        jMenu4.add(MnLembarRalan);

        MnBlangkoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnBlangkoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBlangkoResep.setForeground(new java.awt.Color(50, 50, 50));
        MnBlangkoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBlangkoResep.setMnemonic('w');
        MnBlangkoResep.setText("Blanko Resep");
        MnBlangkoResep.setToolTipText("W");
        MnBlangkoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBlangkoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBlangkoResep.setName("MnBlangkoResep"); // NOI18N
        MnBlangkoResep.setPreferredSize(new java.awt.Dimension(250, 26));
        MnBlangkoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBlangkoResepActionPerformed(evt);
            }
        });
        jMenu4.add(MnBlangkoResep);

        jPopupMenu1.add(jMenu4);

        jSeparator13.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator13.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setPreferredSize(new java.awt.Dimension(200, 1));
        jPopupMenu1.add(jSeparator13);

        MnRujukan.setBackground(new java.awt.Color(250, 255, 245));
        MnRujukan.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnPoliInternal.setBackground(new java.awt.Color(255, 255, 254));
        MnPoliInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoliInternal.setForeground(new java.awt.Color(50, 50, 50));
        MnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoliInternal.setText("Poli Internal");
        MnPoliInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoliInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoliInternal.setName("MnPoliInternal"); // NOI18N
        MnPoliInternal.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliInternalActionPerformed(evt);
            }
        });
        MnRujukan.add(MnPoliInternal);

        jPopupMenu1.add(MnRujukan);

        MnBridging.setBackground(new java.awt.Color(250, 255, 245));
        MnBridging.setForeground(new java.awt.Color(50, 50, 50));
        MnBridging.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBridging.setText("Bridging");
        MnBridging.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBridging.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBridging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBridging.setName("MnBridging"); // NOI18N
        MnBridging.setPreferredSize(new java.awt.Dimension(240, 26));

        MnSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(50, 50, 50));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEP.setText("SEP BPJS");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(230, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });
        MnBridging.add(MnSEP);

        ppSuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol.setText("Rencana Kontrol BPJS");
        ppSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol.setName("ppSuratKontrol"); // NOI18N
        ppSuratKontrol.setPreferredSize(new java.awt.Dimension(230, 26));
        ppSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrolBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppSuratKontrol);

        ppSuratPRI.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPRI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPRI.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPRI.setText("Perintah Rawat Inap BPJS");
        ppSuratPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPRI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPRI.setName("ppSuratPRI"); // NOI18N
        ppSuratPRI.setPreferredSize(new java.awt.Dimension(230, 26));
        ppSuratPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPRIBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppSuratPRI);

        ppProgramPRB.setBackground(new java.awt.Color(255, 255, 254));
        ppProgramPRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppProgramPRB.setForeground(new java.awt.Color(50, 50, 50));
        ppProgramPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppProgramPRB.setText("Program PRB BPJS");
        ppProgramPRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppProgramPRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppProgramPRB.setName("ppProgramPRB"); // NOI18N
        ppProgramPRB.setPreferredSize(new java.awt.Dimension(230, 26));
        ppProgramPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppProgramPRBActionPerformed(evt);
            }
        });
        MnBridging.add(ppProgramPRB);

        ppSuplesiJasaRaharja.setBackground(new java.awt.Color(255, 255, 254));
        ppSuplesiJasaRaharja.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuplesiJasaRaharja.setForeground(new java.awt.Color(50, 50, 50));
        ppSuplesiJasaRaharja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuplesiJasaRaharja.setText("Suplesi Jasa Raharja BPJS");
        ppSuplesiJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuplesiJasaRaharja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuplesiJasaRaharja.setName("ppSuplesiJasaRaharja"); // NOI18N
        ppSuplesiJasaRaharja.setPreferredSize(new java.awt.Dimension(230, 26));
        ppSuplesiJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuplesiJasaRaharjaBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppSuplesiJasaRaharja);

        ppDataIndukKecelakaan.setBackground(new java.awt.Color(255, 255, 254));
        ppDataIndukKecelakaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataIndukKecelakaan.setForeground(new java.awt.Color(50, 50, 50));
        ppDataIndukKecelakaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataIndukKecelakaan.setText("Data Induk Kecelakaan BPJS ");
        ppDataIndukKecelakaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataIndukKecelakaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataIndukKecelakaan.setName("ppDataIndukKecelakaan"); // NOI18N
        ppDataIndukKecelakaan.setPreferredSize(new java.awt.Dimension(230, 26));
        ppDataIndukKecelakaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataIndukKecelakaanBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppDataIndukKecelakaan);

        MnSJP.setBackground(new java.awt.Color(255, 255, 254));
        MnSJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSJP.setForeground(new java.awt.Color(50, 50, 50));
        MnSJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSJP.setText("SJP Inhealth");
        MnSJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSJP.setName("MnSJP"); // NOI18N
        MnSJP.setPreferredSize(new java.awt.Dimension(230, 26));
        MnSJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSJPActionPerformed(evt);
            }
        });
        MnBridging.add(MnSJP);

        MnPCare.setBackground(new java.awt.Color(255, 255, 254));
        MnPCare.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPCare.setForeground(new java.awt.Color(50, 50, 50));
        MnPCare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPCare.setText("Data PCare");
        MnPCare.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPCare.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPCare.setName("MnPCare"); // NOI18N
        MnPCare.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPCare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPCareActionPerformed(evt);
            }
        });
        MnBridging.add(MnPCare);

        MnRujukSisrute.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(230, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });
        MnBridging.add(MnRujukSisrute);

        ppPasienCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPasienCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPasienCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPasienCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPasienCorona.setText("Pasien Corona Kemenkes");
        ppPasienCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPasienCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPasienCorona.setName("ppPasienCorona"); // NOI18N
        ppPasienCorona.setPreferredSize(new java.awt.Dimension(230, 26));
        ppPasienCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPasienCoronaBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppPasienCorona);

        ppPerawatanCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPerawatanCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPerawatanCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPerawatanCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPerawatanCorona.setText("Perawatan Pasien Corona INACBG");
        ppPerawatanCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPerawatanCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPerawatanCorona.setName("ppPerawatanCorona"); // NOI18N
        ppPerawatanCorona.setPreferredSize(new java.awt.Dimension(230, 26));
        ppPerawatanCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPerawatanCoronaBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppPerawatanCorona);

        MnTeridentifikasiTB.setBackground(new java.awt.Color(255, 255, 254));
        MnTeridentifikasiTB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTeridentifikasiTB.setForeground(new java.awt.Color(50, 50, 50));
        MnTeridentifikasiTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTeridentifikasiTB.setText("Teridentifikasi TB Kemenkes");
        MnTeridentifikasiTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTeridentifikasiTB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTeridentifikasiTB.setName("MnTeridentifikasiTB"); // NOI18N
        MnTeridentifikasiTB.setPreferredSize(new java.awt.Dimension(230, 26));
        MnTeridentifikasiTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTeridentifikasiTBActionPerformed(evt);
            }
        });
        MnBridging.add(MnTeridentifikasiTB);

        jPopupMenu1.add(MnBridging);

        MenuInputData.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData.setForeground(new java.awt.Color(50, 50, 50));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(200, 26));

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(50, 50, 50));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(200, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppIKP.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(50, 50, 50));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(200, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        jPopupMenu1.add(MenuInputData);

        MnStatus.setBackground(new java.awt.Color(250, 255, 245));
        MnStatus.setForeground(new java.awt.Color(50, 50, 50));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setPreferredSize(new java.awt.Dimension(200, 26));

        ppBerkasRanap.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasRanap.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasRanap.setText("Berkas R.M. Di Ranap");
        ppBerkasRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasRanap.setName("ppBerkasRanap"); // NOI18N
        ppBerkasRanap.setPreferredSize(new java.awt.Dimension(180, 26));
        ppBerkasRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasRanapBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkasRanap);

        ppBerkasDIterima.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDIterima.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDIterima.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDIterima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDIterima.setText("Berkas R.M. Diterima");
        ppBerkasDIterima.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDIterima.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDIterima.setName("ppBerkasDIterima"); // NOI18N
        ppBerkasDIterima.setPreferredSize(new java.awt.Dimension(180, 26));
        ppBerkasDIterima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDIterimaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkasDIterima);

        MnSudah.setBackground(new java.awt.Color(255, 255, 254));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(50, 50, 50));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 254));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(50, 50, 50));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBatal.setBackground(new java.awt.Color(255, 255, 254));
        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(50, 50, 50));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setBackground(new java.awt.Color(255, 255, 254));
        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(50, 50, 50));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setBackground(new java.awt.Color(255, 255, 254));
        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(50, 50, 50));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        MnPulangPaksa.setBackground(new java.awt.Color(255, 255, 254));
        MnPulangPaksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangPaksa.setForeground(new java.awt.Color(50, 50, 50));
        MnPulangPaksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPulangPaksa.setText("Pulang Paksa");
        MnPulangPaksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangPaksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangPaksa.setName("MnPulangPaksa"); // NOI18N
        MnPulangPaksa.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPulangPaksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangPaksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(MnPulangPaksa);

        jMenu7.setBackground(new java.awt.Color(250, 255, 245));
        jMenu7.setForeground(new java.awt.Color(50, 50, 50));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu7.setText("Status Poli");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(180, 26));

        MnStatusBaru.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Poli Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setName("MnStatusBaru"); // NOI18N
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusBaru);

        MnStatusLama.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusLama.setText("Status Poli Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setName("MnStatusLama"); // NOI18N
        MnStatusLama.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusLamaActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusLama);

        MnStatus.add(jMenu7);

        jPopupMenu1.add(MnStatus);

        MnUrut.setBackground(new java.awt.Color(250, 255, 245));
        MnUrut.setForeground(new java.awt.Color(50, 50, 50));
        MnUrut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut.setText("Urutkan Data Berdasar");
        MnUrut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut.setName("MnUrut"); // NOI18N
        MnUrut.setPreferredSize(new java.awt.Dimension(200, 26));

        MnUrutNoRawatDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutNoRawatDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc.setText("No.Rawat Descending");
        MnUrutNoRawatDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc.setName("MnUrutNoRawatDesc"); // NOI18N
        MnUrutNoRawatDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatDesc);

        MnUrutNoRawatAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutNoRawatAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc.setName("MnUrutNoRawatAsc"); // NOI18N
        MnUrutNoRawatAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatAsc);

        MnUrutTanggalDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutTanggalDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc.setText("Tanggal Descending");
        MnUrutTanggalDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc.setName("MnUrutTanggalDesc"); // NOI18N
        MnUrutTanggalDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalDesc);

        MnUrutTanggalAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutTanggalAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc.setText("Tanggal Ascending");
        MnUrutTanggalAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc.setName("MnUrutTanggalAsc"); // NOI18N
        MnUrutTanggalAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalAsc);

        MnUrutDokterDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutDokterDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc.setText("Dokter Descending");
        MnUrutDokterDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc.setName("MnUrutDokterDesc"); // NOI18N
        MnUrutDokterDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterDesc);

        MnUrutDokterAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutDokterAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc.setText("Dokter Ascending");
        MnUrutDokterAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc.setName("MnUrutDokterAsc"); // NOI18N
        MnUrutDokterAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterAsc);

        MnUrutPoliklinikDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPoliklinikDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc.setName("MnUrutPoliklinikDesc"); // NOI18N
        MnUrutPoliklinikDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikDesc);

        MnUrutPoliklinikAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPoliklinikAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc.setName("MnUrutPoliklinikAsc"); // NOI18N
        MnUrutPoliklinikAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikAsc);

        MnUrutPenjabDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPenjabDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc.setText("Cara Bayar Descending");
        MnUrutPenjabDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc.setName("MnUrutPenjabDesc"); // NOI18N
        MnUrutPenjabDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabDesc);

        MnUrutPenjabAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPenjabAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc.setName("MnUrutPenjabAsc"); // NOI18N
        MnUrutPenjabAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabAsc);

        MnUrutStatusDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutStatusDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc.setText("Status Descending");
        MnUrutStatusDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc.setName("MnUrutStatusDesc"); // NOI18N
        MnUrutStatusDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusDesc);

        MnUrutStatusAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutStatusAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc.setText("Status Ascending");
        MnUrutStatusAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc.setName("MnUrutStatusAsc"); // NOI18N
        MnUrutStatusAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusAsc);

        MnUrutRegDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRegDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegDesc1.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutRegDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegDesc1.setText("No. Reg. Descending");
        MnUrutRegDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegDesc1.setName("MnUrutRegDesc1"); // NOI18N
        MnUrutRegDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegDesc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegDesc1);

        MnUrutRegAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRegAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegAsc1.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutRegAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegAsc1.setText("No. Reg. Ascending");
        MnUrutRegAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegAsc1.setName("MnUrutRegAsc1"); // NOI18N
        MnUrutRegAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegAsc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegAsc1);

        MnUrutRMDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRMDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRMDesc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutRMDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRMDesc.setText("No. RM. Descending");
        MnUrutRMDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRMDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRMDesc.setName("MnUrutRMDesc"); // NOI18N
        MnUrutRMDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRMDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRMDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRMDesc);

        MnUrutRMAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRMAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRMAsc.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutRMAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRMAsc.setText("No. RM. Ascending");
        MnUrutRMAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRMAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRMAsc.setName("MnUrutRMAsc"); // NOI18N
        MnUrutRMAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRMAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRMAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRMAsc);

        jPopupMenu1.add(MnUrut);

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(50, 50, 50));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(200, 26));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        ppTampilkanBelumDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBelumDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBelumDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        ppTampilkanBelumDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBelumDiagnosa.setText("Tampilkan Belum Masuk Diagnosa");
        ppTampilkanBelumDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBelumDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBelumDiagnosa.setName("ppTampilkanBelumDiagnosa"); // NOI18N
        ppTampilkanBelumDiagnosa.setPreferredSize(new java.awt.Dimension(200, 26));
        ppTampilkanBelumDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBelumDiagnosaBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBelumDiagnosa);

        MnGanti.setBackground(new java.awt.Color(250, 255, 245));
        MnGanti.setForeground(new java.awt.Color(50, 50, 50));
        MnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGanti.setText("Ganti");
        MnGanti.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGanti.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGanti.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGanti.setName("MnGanti"); // NOI18N
        MnGanti.setPreferredSize(new java.awt.Dimension(200, 26));

        MnPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoli.setForeground(new java.awt.Color(50, 50, 50));
        MnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoli.setText("Poliklinik");
        MnPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoli.setName("MnPoli"); // NOI18N
        MnPoli.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliActionPerformed(evt);
            }
        });
        MnGanti.add(MnPoli);

        MnDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokter.setText("Dokter Poli");
        MnDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDokter.setName("MnDokter"); // NOI18N
        MnDokter.setPreferredSize(new java.awt.Dimension(160, 26));
        MnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokterActionPerformed(evt);
            }
        });
        MnGanti.add(MnDokter);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        MnGanti.add(MnPenjab);

        MnGabungNoRawat.setBackground(new java.awt.Color(255, 255, 254));
        MnGabungNoRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGabungNoRawat.setForeground(new java.awt.Color(50, 50, 50));
        MnGabungNoRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGabungNoRawat.setText("Gabung Nomor Rawat");
        MnGabungNoRawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGabungNoRawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGabungNoRawat.setName("MnGabungNoRawat"); // NOI18N
        MnGabungNoRawat.setPreferredSize(new java.awt.Dimension(160, 26));
        MnGabungNoRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGabungNoRawatActionPerformed(evt);
            }
        });
        MnGanti.add(MnGabungNoRawat);

        jPopupMenu1.add(MnGanti);

        MnHapusData.setBackground(new java.awt.Color(250, 255, 245));
        MnHapusData.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setPreferredSize(new java.awt.Dimension(200, 26));

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        MnHapusDeposit.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDeposit.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDeposit.setText("Deposit");
        MnHapusDeposit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDeposit.setName("MnHapusDeposit"); // NOI18N
        MnHapusDeposit.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDepositActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDeposit);

        MnHapusDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiet.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiet.setText("Pemberian Diet");
        MnHapusDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiet.setName("MnHapusDiet"); // NOI18N
        MnHapusDiet.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDietActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiet);

        MnHapusDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiagnosa.setText("Diagnosa/Penyakit");
        MnHapusDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiagnosa.setName("MnHapusDiagnosa"); // NOI18N
        MnHapusDiagnosa.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDiagnosaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiagnosa);

        MnHapusDpjp.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDpjp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDpjp.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusDpjp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDpjp.setText("DPJP Ranap");
        MnHapusDpjp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDpjp.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDpjp.setName("MnHapusDpjp"); // NOI18N
        MnHapusDpjp.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDpjpActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDpjp);

        MnHapusHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusHemodialisa.setText("Hemodialisa");
        MnHapusHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusHemodialisa.setName("MnHapusHemodialisa"); // NOI18N
        MnHapusHemodialisa.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusHemodialisaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusHemodialisa);

        MnHapusKamarInap.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusKamarInap.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusKamarInap.setText("Kamar Inap");
        MnHapusKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusKamarInap.setName("MnHapusKamarInap"); // NOI18N
        MnHapusKamarInap.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusKamarInapActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusKamarInap);

        MnHapusPotongan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusPotongan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPotongan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPotongan.setText("Potongan Biaya");
        MnHapusPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPotongan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPotongan.setName("MnHapusPotongan"); // NOI18N
        MnHapusPotongan.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPotonganActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPotongan);

        MnHapusPiutang.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPiutang.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPiutang.setText("Piutang Pasien");
        MnHapusPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPiutang.setName("MnHapusPiutang"); // NOI18N
        MnHapusPiutang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPiutangActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPiutang);

        MnHapusProsedur.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusProsedur.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusProsedur.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusProsedur.setText("Prosedur Tindakan");
        MnHapusProsedur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusProsedur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusProsedur.setName("MnHapusProsedur"); // NOI18N
        MnHapusProsedur.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusProsedurActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusProsedur);

        MnHapusRanapGabung.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRanapGabung.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRanapGabung.setText("Ranap Gabung");
        MnHapusRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRanapGabung.setName("MnHapusRanapGabung"); // NOI18N
        MnHapusRanapGabung.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRanapGabungActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRanapGabung);

        MnHapusRujukKeluar.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukKeluar.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukKeluar.setText("Rujuk Keluar");
        MnHapusRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukKeluar.setName("MnHapusRujukKeluar"); // NOI18N
        MnHapusRujukKeluar.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukKeluarActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukKeluar);

        MnHapusRujukMasuk.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukMasuk.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukMasuk.setText("Rujuk Masuk");
        MnHapusRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukMasuk.setName("MnHapusRujukMasuk"); // NOI18N
        MnHapusRujukMasuk.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukMasukActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukMasuk);

        MnHapusTambahan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTambahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTambahan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTambahan.setText("Tambahan Biaya");
        MnHapusTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTambahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTambahan.setName("MnHapusTambahan"); // NOI18N
        MnHapusTambahan.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTambahanActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTambahan);

        MnHapusBookingOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusBookingOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusBookingOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusBookingOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusBookingOperasi.setText("Booking Operasi");
        MnHapusBookingOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusBookingOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusBookingOperasi.setName("MnHapusBookingOperasi"); // NOI18N
        MnHapusBookingOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusBookingOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusBookingOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusBookingOperasi);

        MnTindakan.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnHapusTindakanRanapDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRanapDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRanapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokter.setText("Ranap Dokter");
        MnHapusTindakanRanapDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokter.setName("MnHapusTindakanRanapDokter"); // NOI18N
        MnHapusTindakanRanapDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokter);

        MnHapusTindakanRanapDokterParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRanapDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setText("Ranap Dokter & Paramedis");
        MnHapusTindakanRanapDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokterParamedis.setName("MnHapusTindakanRanapDokterParamedis"); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokterParamedis);

        MnHapusTindakanRanapParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRanapParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRanapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapParamedis.setText("Ranap Paramedis");
        MnHapusTindakanRanapParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapParamedis.setName("MnHapusTindakanRanapParamedis"); // NOI18N
        MnHapusTindakanRanapParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapParamedis);

        MnHapusTindakanRalanDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRalanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokter.setText("Ralan Dokter");
        MnHapusTindakanRalanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokter.setName("MnHapusTindakanRalanDokter"); // NOI18N
        MnHapusTindakanRalanDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokter);

        MnHapusTindakanRalanDokterParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRalanDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setText("Ralan Dokter & Paramedis");
        MnHapusTindakanRalanDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokterParamedis.setName("MnHapusTindakanRalanDokterParamedis"); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokterParamedis);

        MnHapusTindakanRalanParamedis.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTindakanRalanParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanParamedis.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTindakanRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanParamedis.setText("Ralan Paramedis");
        MnHapusTindakanRalanParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanParamedis.setName("MnHapusTindakanRalanParamedis"); // NOI18N
        MnHapusTindakanRalanParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanParamedis);

        MnHapusData.add(MnTindakan);

        MnPemeriksaan.setBackground(new java.awt.Color(250, 255, 245));
        MnPemeriksaan.setForeground(new java.awt.Color(50, 50, 50));
        MnPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemeriksaan.setText("Pemeriksaan");
        MnPemeriksaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemeriksaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemeriksaan.setName("MnPemeriksaan"); // NOI18N
        MnPemeriksaan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnHapusPemeriksaanRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusPemeriksaanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusPemeriksaanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRalan.setText("Ralan");
        MnHapusPemeriksaanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRalan.setName("MnHapusPemeriksaanRalan"); // NOI18N
        MnHapusPemeriksaanRalan.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusPemeriksaanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRalanActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRalan);

        MnHapusPemeriksaanRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusPemeriksaanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusPemeriksaanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRanap.setText("Ranap");
        MnHapusPemeriksaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRanap.setName("MnHapusPemeriksaanRanap"); // NOI18N
        MnHapusPemeriksaanRanap.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusPemeriksaanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRanapActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRanap);

        MnHapusLab.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusLab.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusLab.setText("Laborat");
        MnHapusLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusLab.setName("MnHapusLab"); // NOI18N
        MnHapusLab.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusLabActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusLab);

        MnHapusRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRadiologi.setText("Radiologi");
        MnHapusRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRadiologi.setName("MnHapusRadiologi"); // NOI18N
        MnHapusRadiologi.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRadiologiActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusRadiologi);

        MnHapusData.add(MnPemeriksaan);

        MnObat.setBackground(new java.awt.Color(250, 255, 245));
        MnObat.setForeground(new java.awt.Color(50, 50, 50));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setPreferredSize(new java.awt.Dimension(200, 26));

        MnHapusAturanPkaiObat.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusAturanPkaiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusAturanPkaiObat.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusAturanPkaiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusAturanPkaiObat.setText("Aturan Pakai Obat");
        MnHapusAturanPkaiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusAturanPkaiObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusAturanPkaiObat.setName("MnHapusAturanPkaiObat"); // NOI18N
        MnHapusAturanPkaiObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusAturanPkaiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusAturanPkaiObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusAturanPkaiObat);

        MnHapusObat.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObat.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObat.setText("Pemberian Obat");
        MnHapusObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObat.setName("MnHapusObat"); // NOI18N
        MnHapusObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusObat);

        MnHapusResepObat.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepObat.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepObat.setText("Resep Obat");
        MnHapusResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepObat.setName("MnHapusResepObat"); // NOI18N
        MnHapusResepObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepObat);

        MnHapusResepPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepPulang.setText("Resep Pulang");
        MnHapusResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepPulang.setName("MnHapusResepPulang"); // NOI18N
        MnHapusResepPulang.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepPulangActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepPulang);

        MnHapusReturObat.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusReturObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusReturObat.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusReturObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusReturObat.setText("Retur Obat Pasien");
        MnHapusReturObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusReturObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusReturObat.setName("MnHapusReturObat"); // NOI18N
        MnHapusReturObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusReturObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusReturObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusReturObat);

        MnHapusStokObatRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusStokObatRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusStokObatRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusStokObatRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusStokObatRanap.setText("Stok Obat Ranap");
        MnHapusStokObatRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusStokObatRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusStokObatRanap.setName("MnHapusStokObatRanap"); // NOI18N
        MnHapusStokObatRanap.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusStokObatRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusStokObatRanapActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusStokObatRanap);

        MnHapusData.add(MnObat);

        MnHapusSemua.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusSemua.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusSemua.setText("Semua Transaksi & Registrasi");
        MnHapusSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusSemua.setName("MnHapusSemua"); // NOI18N
        MnHapusSemua.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusSemuaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusSemua);

        jPopupMenu1.add(MnHapusData);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });

        WindowObatBhp.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowObatBhp.setName("WindowObatBhp"); // NOI18N
        WindowObatBhp.setUndecorated(true);
        WindowObatBhp.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total Tagihan Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        TotalObat.setHighlighter(null);
        TotalObat.setName("TotalObat"); // NOI18N
        TotalObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalObatKeyPressed(evt);
            }
        });
        internalFrame2.add(TotalObat);
        TotalObat.setBounds(60, 30, 180, 23);

        jLabel3.setText("Total :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 30, 57, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(465, 30, 100, 30);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(255, 30, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal.setMnemonic('H');
        BtnBatal.setText("Hapus");
        BtnBatal.setToolTipText("Alt+H");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(360, 30, 100, 30);

        WindowObatBhp.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowGantiDokter.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokter.setName("WindowGantiDokter"); // NOI18N
        WindowGantiDokter.setUndecorated(true);
        WindowGantiDokter.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Dokter Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(null);

        BtnCloseIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn1.setMnemonic('U');
        BtnCloseIn1.setText("Tutup");
        BtnCloseIn1.setToolTipText("Alt+U");
        BtnCloseIn1.setName("BtnCloseIn1"); // NOI18N
        BtnCloseIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn1ActionPerformed(evt);
            }
        });
        BtnCloseIn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseIn1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseIn1);
        BtnCloseIn1.setBounds(510, 30, 100, 30);

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpan1);
        BtnSimpan1.setBounds(405, 30, 100, 30);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame3.add(jLabel13);
        jLabel13.setBounds(0, 32, 77, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        internalFrame3.add(kddokter);
        kddokter.setBounds(81, 32, 100, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        internalFrame3.add(TDokter);
        TDokter.setBounds(183, 32, 181, 23);

        btnCariDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnCariDokter.setMnemonic('7');
        btnCariDokter.setToolTipText("ALt+7");
        btnCariDokter.setName("btnCariDokter"); // NOI18N
        btnCariDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDokterActionPerformed(evt);
            }
        });
        internalFrame3.add(btnCariDokter);
        btnCariDokter.setBounds(366, 32, 28, 23);

        WindowGantiDokter.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TKdPny.setName("TKdPny"); // NOI18N

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N

        WindowGantiPoli.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiPoli.setName("WindowGantiPoli"); // NOI18N
        WindowGantiPoli.setUndecorated(true);
        WindowGantiPoli.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel18.setText("Poli Dituju :");
        jLabel18.setName("jLabel18"); // NOI18N
        internalFrame5.add(jLabel18);
        jLabel18.setBounds(0, 32, 77, 23);

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpoli);
        kdpoli.setBounds(81, 32, 100, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        internalFrame5.add(nmpoli);
        nmpoli.setBounds(183, 32, 181, 23);

        btnCariPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnCariPoli.setMnemonic('7');
        btnCariPoli.setToolTipText("ALt+7");
        btnCariPoli.setName("btnCariPoli"); // NOI18N
        btnCariPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPoliActionPerformed(evt);
            }
        });
        internalFrame5.add(btnCariPoli);
        btnCariPoli.setBounds(366, 32, 28, 23);

        WindowGantiPoli.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowCaraBayar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCaraBayar.setName("WindowCaraBayar"); // NOI18N
        WindowCaraBayar.setUndecorated(true);
        WindowCaraBayar.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(510, 30, 100, 30);

        BtnSimpan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan5.setMnemonic('S');
        BtnSimpan5.setText("Simpan");
        BtnSimpan5.setToolTipText("Alt+S");
        BtnSimpan5.setName("BtnSimpan5"); // NOI18N
        BtnSimpan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpan5);
        BtnSimpan5.setBounds(405, 30, 100, 30);

        jLabel19.setText("Jenis Bayar :");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame6.add(jLabel19);
        jLabel19.setBounds(0, 32, 77, 23);

        kdpenjab.setHighlighter(null);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame6.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        internalFrame6.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBayar.setMnemonic('7');
        btnBayar.setToolTipText("ALt+7");
        btnBayar.setName("btnBayar"); // NOI18N
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        internalFrame6.add(btnBayar);
        btnBayar.setBounds(366, 32, 28, 23);

        WindowCaraBayar.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnPermintaan1.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan1.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan1.setText("Permintaan");
        MnPermintaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan1.setName("MnPermintaan1"); // NOI18N
        MnPermintaan1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnJadwalOperasi1.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi1.setForeground(new java.awt.Color(50, 50, 50));
        MnJadwalOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi1.setText("Jadwal Operasi");
        MnJadwalOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi1.setName("MnJadwalOperasi1"); // NOI18N
        MnJadwalOperasi1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasi1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnJadwalOperasi1);

        MnPermintaanLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab1.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab1.setText("Pemeriksaan Lab");
        MnPermintaanLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab1.setName("MnPermintaanLab1"); // NOI18N
        MnPermintaanLab1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLab1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnPermintaanLab1);

        MnPermintaanRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi1.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi1.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi1.setName("MnPermintaanRadiologi1"); // NOI18N
        MnPermintaanRadiologi1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologi1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnPermintaanRadiologi1);

        jPopupMenu2.add(MnPermintaan1);

        MnKamarInap1.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap1.setForeground(new java.awt.Color(50, 50, 50));
        MnKamarInap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap1.setText("Kamar Inap");
        MnKamarInap1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap1.setName("MnKamarInap1"); // NOI18N
        MnKamarInap1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnKamarInap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInap1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnKamarInap1);

        MnTindakan1.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan1.setForeground(new java.awt.Color(50, 50, 50));
        MnTindakan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan1.setText("Tindakan & Pemeriksaan");
        MnTindakan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan1.setName("MnTindakan1"); // NOI18N
        MnTindakan1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnRawatJalan1.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan1.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan1.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan1.setName("MnRawatJalan1"); // NOI18N
        MnRawatJalan1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalan1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnRawatJalan1);

        MnPeriksaLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab1.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab1.setText("Periksa Laborat PK");
        MnPeriksaLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab1.setName("MnPeriksaLab1"); // NOI18N
        MnPeriksaLab1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLab1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaLab1);

        MnPeriksaLabPA2.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLabPA2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLabPA2.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLabPA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLabPA2.setText("Periksa Laborat PA");
        MnPeriksaLabPA2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLabPA2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLabPA2.setName("MnPeriksaLabPA2"); // NOI18N
        MnPeriksaLabPA2.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLabPA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabPA2ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaLabPA2);

        MnPeriksaRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi1.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi1.setText("Periksa Radiologi");
        MnPeriksaRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi1.setName("MnPeriksaRadiologi1"); // NOI18N
        MnPeriksaRadiologi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaRadiologi1);

        MnOperasi1.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi1.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi1.setText("Tagihan Operasi/VK");
        MnOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi1.setName("MnOperasi1"); // NOI18N
        MnOperasi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnOperasi1);

        MnDataRalan1.setBackground(new java.awt.Color(255, 255, 254));
        MnDataRalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRalan1.setForeground(new java.awt.Color(50, 50, 50));
        MnDataRalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRalan1.setText("Data Tindakan Rawat Jalan");
        MnDataRalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRalan1.setName("MnDataRalan1"); // NOI18N
        MnDataRalan1.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataRalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataRalan1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnDataRalan1);

        jPopupMenu2.add(MnTindakan1);

        MnObat1.setBackground(new java.awt.Color(250, 255, 245));
        MnObat1.setForeground(new java.awt.Color(50, 50, 50));
        MnObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat1.setText("Obat");
        MnObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat1.setName("MnObat1"); // NOI18N
        MnObat1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnPemberianObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat1.setForeground(new java.awt.Color(50, 50, 50));
        MnPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat1.setText("Pemberian Obat/BHP");
        MnPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat1.setName("MnPemberianObat1"); // NOI18N
        MnPemberianObat1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObat1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnPemberianObat1);

        MnNoResep1.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep1.setForeground(new java.awt.Color(50, 50, 50));
        MnNoResep1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep1.setText("Input No.Resep");
        MnNoResep1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep1.setName("MnNoResep1"); // NOI18N
        MnNoResep1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnNoResep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResep1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnNoResep1);

        MnResepDOkter1.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter1.setForeground(new java.awt.Color(50, 50, 50));
        MnResepDOkter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter1.setText("Input Resep Dokter");
        MnResepDOkter1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter1.setName("MnResepDOkter1"); // NOI18N
        MnResepDOkter1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnResepDOkter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkter1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnResepDOkter1);

        MnObatLangsung1.setBackground(new java.awt.Color(255, 255, 254));
        MnObatLangsung1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung1.setForeground(new java.awt.Color(50, 50, 50));
        MnObatLangsung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung1.setText("Total Tagihan Obat");
        MnObatLangsung1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung1.setName("MnObatLangsung1"); // NOI18N
        MnObatLangsung1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnObatLangsung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsung1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnObatLangsung1);

        MnDataPemberianObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnDataPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataPemberianObat1.setForeground(new java.awt.Color(50, 50, 50));
        MnDataPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataPemberianObat1.setText("Data Pemberian Obat");
        MnDataPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataPemberianObat1.setName("MnDataPemberianObat1"); // NOI18N
        MnDataPemberianObat1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDataPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataPemberianObat1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnDataPemberianObat1);

        MnPenjualan1.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjualan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan1.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjualan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan1.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan1.setName("MnPenjualan1"); // NOI18N
        MnPenjualan1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPenjualan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualan1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnPenjualan1);

        MnCopyResep2.setBackground(new java.awt.Color(255, 255, 254));
        MnCopyResep2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyResep2.setForeground(new java.awt.Color(50, 50, 50));
        MnCopyResep2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyResep2.setText("Copy Resep Dokter");
        MnCopyResep2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCopyResep2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCopyResep2.setName("MnCopyResep2"); // NOI18N
        MnCopyResep2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCopyResep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyResep2ActionPerformed(evt);
            }
        });
        MnObat1.add(MnCopyResep2);

        jPopupMenu2.add(MnObat1);

        MnBilling1.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling1.setForeground(new java.awt.Color(50, 50, 50));
        MnBilling1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling1.setText("Billing/Pembayaran Pasien");
        MnBilling1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling1.setName("MnBilling1"); // NOI18N
        MnBilling1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnBilling1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBilling1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnBilling1);

        jSeparator14.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator14.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator14.setName("jSeparator14"); // NOI18N
        jSeparator14.setPreferredSize(new java.awt.Dimension(210, 1));
        jPopupMenu2.add(jSeparator14);

        MenuInputData1.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData1.setForeground(new java.awt.Color(50, 50, 50));
        MenuInputData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData1.setText("Input Data");
        MenuInputData1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData1.setName("MenuInputData1"); // NOI18N
        MenuInputData1.setPreferredSize(new java.awt.Dimension(210, 26));

        ppBerkasDigital1.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital1.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital1.setText("Berkas Digital Perawatan");
        ppBerkasDigital1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital1.setName("ppBerkasDigital1"); // NOI18N
        ppBerkasDigital1.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBerkasDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigital1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppBerkasDigital1);

        ppIKP1.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP1.setForeground(new java.awt.Color(50, 50, 50));
        ppIKP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP1.setText("Insiden Keselamatan Pasien");
        ppIKP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP1.setName("ppIKP1"); // NOI18N
        ppIKP1.setPreferredSize(new java.awt.Dimension(200, 26));
        ppIKP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKP1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppIKP1);

        MnDiagnosa1.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa1.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa1.setText("Diagnosa Pasien");
        MnDiagnosa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa1.setName("MnDiagnosa1"); // NOI18N
        MnDiagnosa1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosa1ActionPerformed(evt);
            }
        });
        MenuInputData1.add(MnDiagnosa1);

        jPopupMenu2.add(MenuInputData1);

        MnUrut1.setBackground(new java.awt.Color(250, 255, 245));
        MnUrut1.setForeground(new java.awt.Color(50, 50, 50));
        MnUrut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut1.setText("Urutkan Data Berdasar");
        MnUrut1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut1.setName("MnUrut1"); // NOI18N
        MnUrut1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnUrutNoRawatDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutNoRawatDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc2.setText("No.Rawat Descending");
        MnUrutNoRawatDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc2.setName("MnUrutNoRawatDesc2"); // NOI18N
        MnUrutNoRawatDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatDesc2);

        MnUrutNoRawatAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutNoRawatAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc2.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc2.setName("MnUrutNoRawatAsc2"); // NOI18N
        MnUrutNoRawatAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatAsc2);

        MnUrutTanggalDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutTanggalDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc2.setText("Tanggal Descending");
        MnUrutTanggalDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc2.setName("MnUrutTanggalDesc2"); // NOI18N
        MnUrutTanggalDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalDesc2);

        MnUrutTanggalAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutTanggalAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc2.setText("Tanggal Ascending");
        MnUrutTanggalAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc2.setName("MnUrutTanggalAsc2"); // NOI18N
        MnUrutTanggalAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalAsc2);

        MnUrutDokterDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutDokterDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc2.setText("Dokter Descending");
        MnUrutDokterDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc2.setName("MnUrutDokterDesc2"); // NOI18N
        MnUrutDokterDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterDesc2);

        MnUrutDokterAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutDokterAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc2.setText("Dokter Ascending");
        MnUrutDokterAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc2.setName("MnUrutDokterAsc2"); // NOI18N
        MnUrutDokterAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterAsc2);

        MnUrutPoliklinikDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPoliklinikDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc2.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc2.setName("MnUrutPoliklinikDesc2"); // NOI18N
        MnUrutPoliklinikDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikDesc2);

        MnUrutPoliklinikAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPoliklinikAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc2.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc2.setName("MnUrutPoliklinikAsc2"); // NOI18N
        MnUrutPoliklinikAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikAsc2);

        MnUrutPenjabDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPenjabDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc2.setText("Cara Bayar Descending");
        MnUrutPenjabDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc2.setName("MnUrutPenjabDesc2"); // NOI18N
        MnUrutPenjabDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabDesc2);

        MnUrutPenjabAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutPenjabAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc2.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc2.setName("MnUrutPenjabAsc2"); // NOI18N
        MnUrutPenjabAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabAsc2);

        MnUrutStatusDesc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutStatusDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc2.setText("Status Descending");
        MnUrutStatusDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc2.setName("MnUrutStatusDesc2"); // NOI18N
        MnUrutStatusDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusDesc2);

        MnUrutStatusAsc2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc2.setForeground(new java.awt.Color(50, 50, 50));
        MnUrutStatusAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc2.setText("Status Ascending");
        MnUrutStatusAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc2.setName("MnUrutStatusAsc2"); // NOI18N
        MnUrutStatusAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusAsc2);

        jPopupMenu2.add(MnUrut1);

        ppRiwayat1.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat1.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat1.setText("Riwayat Perawatan");
        ppRiwayat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat1.setName("ppRiwayat1"); // NOI18N
        ppRiwayat1.setPreferredSize(new java.awt.Dimension(210, 26));
        ppRiwayat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayat1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(ppRiwayat1);

        MnHapusRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukan.setText("Hapus Rujukan Poli Internal");
        MnHapusRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukan.setName("MnHapusRujukan"); // NOI18N
        MnHapusRujukan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnHapusRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukanActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnHapusRujukan);

        DlgCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCatatan.setName("DlgCatatan"); // NOI18N
        DlgCatatan.setUndecorated(true);
        DlgCatatan.setResizable(false);

        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setWarnaAtas(new java.awt.Color(100, 100, 10));
        internalFrame7.setWarnaBawah(new java.awt.Color(100, 100, 10));
        internalFrame7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internalFrame7MouseClicked(evt);
            }
        });
        internalFrame7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        LabelCatatan.setForeground(new java.awt.Color(255, 255, 255));
        LabelCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCatatan.setText("Catatan");
        LabelCatatan.setName("LabelCatatan"); // NOI18N
        LabelCatatan.setPreferredSize(new java.awt.Dimension(580, 23));
        LabelCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelCatatanMouseClicked(evt);
            }
        });
        internalFrame7.add(LabelCatatan);

        DlgCatatan.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        DlgSakit.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit.setName("DlgSakit"); // NOI18N
        DlgSakit.setUndecorated(true);
        DlgSakit.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Cuti Sakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TglSakit1.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2022" }));
        TglSakit1.setDisplayFormat("dd-MM-yyyy");
        TglSakit1.setName("TglSakit1"); // NOI18N
        TglSakit1.setOpaque(false);
        panelBiasa2.add(TglSakit1);
        TglSakit1.setBounds(70, 10, 100, 23);

        jLabel31.setText("Lama Sakit :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelBiasa2.add(jLabel31);
        jLabel31.setBounds(297, 10, 110, 23);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Cetak");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(10, 50, 100, 30);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar2.setMnemonic('U');
        BtnKeluar2.setText("Tutup");
        BtnKeluar2.setToolTipText("Alt+U");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 50, 100, 30);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("s/d");
        jLabel32.setName("jLabel32"); // NOI18N
        panelBiasa2.add(jLabel32);
        jLabel32.setBounds(176, 10, 20, 23);

        TglSakit2.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2022" }));
        TglSakit2.setDisplayFormat("dd-MM-yyyy");
        TglSakit2.setName("TglSakit2"); // NOI18N
        TglSakit2.setOpaque(false);
        panelBiasa2.add(TglSakit2);
        TglSakit2.setBounds(200, 10, 100, 23);

        jLabel33.setText("Tanggal :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 10, 66, 23);

        lmsakit.setHighlighter(null);
        lmsakit.setName("lmsakit"); // NOI18N
        panelBiasa2.add(lmsakit);
        lmsakit.setBounds(410, 10, 110, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgSakit.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel37.setText("Nomor Surat Keterangan :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelBiasa4.add(jLabel37);
        jLabel37.setBounds(7, 10, 140, 23);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(10, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar4.setMnemonic('U');
        BtnKeluar4.setText("Tutup");
        BtnKeluar4.setToolTipText("Alt+U");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(430, 80, 100, 30);

        NomorSurat.setHighlighter(null);
        NomorSurat.setName("NomorSurat"); // NOI18N
        panelBiasa4.add(NomorSurat);
        NomorSurat.setBounds(150, 10, 370, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(CrDokter3);
        CrDokter3.setBounds(150, 40, 340, 23);

        jLabel24.setText("Dokter Png. Jawab :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel24);
        jLabel24.setBounds(7, 40, 140, 23);

        internalFrame8.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Pasien Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(303, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass6.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass7.add(jLabel14);

        CrPtg.setEditable(false);
        CrPtg.setName("CrPtg"); // NOI18N
        CrPtg.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPtg);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('4');
        BtnSeek3.setToolTipText("ALt+4");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek3);

        jLabel16.setText("Poliklinik :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass7.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek4);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-07-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(DTPCari2);

        jLabel12.setText("Status Periksa :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum", "Sudah", "Batal", "Berkas Diterima", "Dirujuk", "Meninggal", "Dirawat", "Pulang Paksa" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass8.add(cmbStatus);

        jLabel20.setText("Status Bayar :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(jLabel20);

        cmbStatusBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Bayar", "Belum Bayar" }));
        cmbStatusBayar.setName("cmbStatusBayar"); // NOI18N
        cmbStatusBayar.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass8.add(cmbStatusBayar);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll1.setComponentPopupMenu(jPopupMenu1);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbKasirRalan.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        tbKasirRalan.setComponentPopupMenu(jPopupMenu1);
        tbKasirRalan.setName("tbKasirRalan"); // NOI18N
        tbKasirRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKasirRalanMouseClicked(evt);
            }
        });
        tbKasirRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKasirRalanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbKasirRalan);

        TabRawat.addTab("Registrasi Awal", Scroll1);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll2.setComponentPopupMenu(jPopupMenu2);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbKasirRalan2.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        tbKasirRalan2.setComponentPopupMenu(jPopupMenu2);
        tbKasirRalan2.setName("tbKasirRalan2"); // NOI18N
        tbKasirRalan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKasirRalan2MouseClicked(evt);
            }
        });
        tbKasirRalan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKasirRalan2KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbKasirRalan2);

        TabRawat.addTab("Rujukan Internal Poli", Scroll2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel4.setText("No. Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel4);

        TNoRwCari.setEditable(false);
        TNoRwCari.setHighlighter(null);
        TNoRwCari.setName("TNoRwCari"); // NOI18N
        TNoRwCari.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass9.add(TNoRwCari);

        jLabel5.setText("No. Reg. :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel5);

        TNoReg.setEditable(false);
        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.setPreferredSize(new java.awt.Dimension(50, 23));
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        panelGlass9.add(TNoReg);

        jLabel7.setText("No.R.M. :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        TNoRMCari.setEditable(false);
        TNoRMCari.setHighlighter(null);
        TNoRMCari.setName("TNoRMCari"); // NOI18N
        TNoRMCari.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(TNoRMCari);

        jLabel8.setText("Nama Pasien :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel8);

        TPasienCari.setEditable(false);
        TPasienCari.setHighlighter(null);
        TPasienCari.setName("TPasienCari"); // NOI18N
        TPasienCari.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass9.add(TPasienCari);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.dispose();
            }
        }
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,cmbStatus,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if(namadokter.equals("")){
            CrPtg.setText("");               
        }
        
        if(namapoli.equals("")){
            CrPoli.setText("");
        }
        
        cmbStatus.setSelectedItem("Semua");
        cmbStatusBayar.setSelectedItem("Semua");
        
        TCari.setText("");
        caripenjab="";
        tampildiagnosa="";
        filter="no";
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            TabRawatMouseClicked(null);
        }else{
            Valid.pindah(evt, TCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            if(TabRawat.getSelectedIndex()==0){
                tbKasirRalan.requestFocus();
            }else if(TabRawat.getSelectedIndex()==1){
                tbKasirRalan2.requestFocus();
            }
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbKasirRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKasirRalanMouseClicked
        if(tabModekasir.getRowCount()!=0){
            try {
                getDatakasir();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==1){
                if(norawatdipilih.equals("")){
                    i=tbKasirRalan.getSelectedColumn();
                    if(i==3){
                        if(validasicatatan.equals("Yes")){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            LabelCatatan.setText(Sequel.cariIsi("select catatan_pasien.catatan from catatan_pasien where catatan_pasien.no_rkm_medis=?",TNoRMCari.getText()));
                            if(!LabelCatatan.getText().equals("")){
                                DlgCatatan.setLocationRelativeTo(TabRawat);
                                DlgCatatan.setVisible(true);
                            }else{
                                DlgCatatan.setVisible(false);
                            }                            
                            this.setCursor(Cursor.getDefaultCursor());
                        }  
                    } 
                }else{
                    if(tbKasirRalan.getSelectedRow()!= -1){
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                        }else{
                            if(normdipilih.equals(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString())){
                                Sequel.queryu2("update asuhan_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update aturan_pakai set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_detail_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_detail_periksa_lab_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_anak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_anestesi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_pjanak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_umum set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator1 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator3 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_lab_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_radiologi_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_inap_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_inap_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_jl_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_jl_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update beri_bhp_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update beri_obat_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update berkas_digital_perawatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update billing set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update booking_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_inhealth set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_sep set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_sep_internal set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_surat_pri_bpjs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update catatan_perawatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_HAIs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_klasifikasi_pasien_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_tb set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala1 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala3 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala4 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala5 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igdprimer set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igdsekunder set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update deposit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_beri_diet set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_nota_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_nota_jalan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_obat_racikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_pemberian_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_penagihan_piutang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_labpa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_labpa_gambar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_piutang_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update deteksi_dini_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update diagnosa_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update dpjp_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update gambar_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update hasil_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update hemodialisa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update inacbg_klaim_baru2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update inacbg_noklaim_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update insiden_keselamatan_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update kamar_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update laporan_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update monitoring_asuhan_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update mutasi_berkas set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update nota_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update nota_jalan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update obat_racikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_kunjungan_umum set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_obat_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_pendaftaran set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_tindakan_ralan_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_tindakan_ranap_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ginekologi_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ginekologi_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_obstetri_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_obstetri_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pengembalian_deposit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pengurangan_biaya set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_gigi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_gigi_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_igd_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_kebidanan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_kebidanan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_mata set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_mata_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_bayi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_bayi_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_rencana set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_fisioterapi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_mcu set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan_anak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan_kandungan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ranap_kandungan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update perawatan_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update periksa_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update perkiraan_biaya_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_labpa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_registrasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_resep_pulang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_stok_obat_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update piutang_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update prosedur_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update ranap_gabung set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_pr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_pr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update referensi_mobilejkn_bpjs_taskid set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_luar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_pulang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resume_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resume_pasien_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update returpasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                if(Sequel.queryu2tf("update rujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                )==false){
                                    Sequel.meghapus("rujuk","no_rawat", norawatdipilih);
                                }
                                if(Sequel.queryu2tf("update rujuk_masuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                )==false){
                                    Sequel.meghapus("rujuk_masuk","no_rawat", norawatdipilih);
                                }
                                Sequel.queryu2("update rujukan_internal_poli set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rvp_klaim_bpjs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update saran_kesan_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update sisrute_rujukan_keluar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update skrining_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update stok_obat_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_cuti_hamil set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tato set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tbc set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_hamil set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_buta_warna set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_covid set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_rawat_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_sehat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_kewaspadaan_kesehatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_skbn set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update suratsakit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update suratsakitpihak2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update tagihan_obat_langsung set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_luar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tbc set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_buta_warna set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tato set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update tambahan_biaya set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update ubah_penjab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update uji_fungsi_kfr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("delete from reg_periksa where no_rawat=?",1,
                                    new String[]{
                                        norawatdipilih
                                    }
                                );
                                tampilkasir();
                            }else{
                                JOptionPane.showMessageDialog(rootPane,"Tidak bisa digabung karena No.RM berbeda");
                            }
                            norawatdipilih="";
                            normdipilih="";
                        }   
                    }
                }               
            }else if(evt.getClickCount()==2){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    if(akses.gettindakan_ralan()==true){
                        MnDataRalanActionPerformed(null);
                    }
                }else if(i==1){
                    if(akses.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(MnKamarInap.isEnabled()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(akses.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                    }                    
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanMouseClicked

    private void tbKasirRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKasirRalanKeyPressed
        if(tabModekasir.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDatakasir();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    if(akses.gettindakan_ralan()==true){
                        MnDataRalanActionPerformed(null);
                    }
                }else if(i==1){
                    if(akses.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(MnKamarInap.isEnabled()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(akses.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                    }                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbKasirRalanKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
       akses.setform("DlgKasirRalan");
       pilihan=2;
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
       akses.setform("DlgKasirRalan");
       pilihan=2;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString());
                }
                if(jmlparsial>0){
                    panggilformobat();
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        panggilformobat();
                    }
                }                
            }            
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{          
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                try {
                    sudah=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
                    pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try{                                                
                        pscaripiutang.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
                        rskasir=pscaripiutang.executeQuery();
                        if(rskasir.next()){
                            i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                                 DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                 piutang.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),rskasir.getDate(1));
                                 piutang.tampil();
                                 piutang.isCek();
                                 piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                 piutang.setLocationRelativeTo(internalFrame1);
                                 piutang.setVisible(true);
                            }else{
                                if(akses.getbilling_ralan()==true){
                                    otomatisRalan();
                                }
                                  
                                akses.setform("DlgKasirRalan");
                                billing.TNoRw.setText(TNoRw.getText());  
                                billing.isCek();
                                billing.isRawat(); 
                                if(sudah>0){
                                    billing.setPiutang();
                                }
                                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                billing.setLocationRelativeTo(internalFrame1);
                                billing.setVisible(true);
                            }
                        }else{
                            if(akses.getbilling_ralan()==true){
                                otomatisRalan();
                            }
                            akses.setform("DlgKasirRalan");
                            billing.TNoRw.setText(TNoRw.getText());  
                            billing.isCek();
                            billing.isRawat(); 
                            billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billing.setLocationRelativeTo(internalFrame1);
                            billing.setVisible(true);
                        }
                    }catch(Exception ex){
                        System.out.println("Notifikasi : "+ex);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pscaripiutang!=null){
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }            
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        //Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Sudah'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
}//GEN-LAST:event_MnSudahActionPerformed

private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
           if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
           }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
           }
            
        }
}//GEN-LAST:event_MnBelumActionPerformed

private void MnDataRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    dlgrwjl2.isCek();
                    dlgrwjl2.emptTeks();
                    dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgrwjl2.setLocationRelativeTo(internalFrame1);
                    dlgrwjl2.SetPoli(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),18).toString());
                    dlgrwjl2.SetPj(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString());
                    dlgrwjl2.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
                    dlgrwjl2.setVisible(true);
                } 
            }                               
        }
}//GEN-LAST:event_MnDataRalanActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnSimpan);

}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowObatBhp.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowObatBhp.dispose();
        }else{Valid.pindah(evt, BtnBatal, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowObatBhp.setVisible(false);
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,TotalObat,BtnBatal);
}//GEN-LAST:event_BtnSimpanKeyPressed

private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat=? ",TNoRw.getText());
            WindowObatBhp.setVisible(false);
        }
}//GEN-LAST:event_BtnBatalActionPerformed

private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatalKeyPressed

private void MnObatLangsungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsungActionPerformed
    if(tabModekasir.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        //TNoReg.requestFocus();
    }else if(TNoRw.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbKasirRalan.requestFocus();
    }else{   
        if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        }else {
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{
                TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));
                WindowObatBhp.setSize(590,80);
                WindowObatBhp.setLocationRelativeTo(internalFrame1);
                WindowObatBhp.setVisible(true);
            }        
        }
    }        
}//GEN-LAST:event_MnObatLangsungActionPerformed

private void BtnCloseIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn1ActionPerformed
    WindowGantiDokter.dispose();
}//GEN-LAST:event_BtnCloseIn1ActionPerformed

private void BtnCloseIn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseIn1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCloseIn1KeyPressed

private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kddokter.getText().trim().equals("")||TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModekasir,"rawat_jl_dr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModekasir,"rawat_jl_drpr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            tampilkasir();
            WindowGantiDokter.dispose();
        }
}//GEN-LAST:event_BtnSimpan1ActionPerformed

private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSimpan1KeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
           Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariDokterActionPerformed(null);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void btnCariDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDokterActionPerformed
       akses.setform("DlgKasirRalan");
       pilihan=1;
       billing.dokter.emptTeks();
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_btnCariDokterActionPerformed

private void MnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokterActionPerformed
    if(tabModekasir.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    }else if(TNoRw.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        tbKasirRalan.requestFocus();
    }else{
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
        }else{ 
            WindowGantiDokter.setSize(630,80);
            WindowGantiDokter.setLocationRelativeTo(internalFrame1);
            WindowGantiDokter.setVisible(true);
        }  
    }
}//GEN-LAST:event_MnDokterActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPenjualan penjualan=new DlgPenjualan(null,false);
                penjualan.isCek();
                penjualan.setPasien(TNoRMCari.getText());  
                penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                penjualan.setLocationRelativeTo(internalFrame1);
                penjualan.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnPenjualanActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
                periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(TNoRw.getText(),"Ralan"); 
                periksalab.isCek();
                periksalab.setVisible(true);
            }            
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),10).toString().equals("Batal")){
                    JOptionPane.showMessageDialog(null,"Pasien berstatus batal periksa...!");
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                    }else{ 
                        akses.setstatus(true);
                        kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        kamarinap.setLocationRelativeTo(internalFrame1);
                        kamarinap.emptTeks();
                        if(akses.getbilling_ralan()==true){
                            otomatisRalan();
                        }
                        kamarinap.isCek();
                        kamarinap.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText()); 
                        kamarinap.setVisible(true);                    
                    }
                }
            }            
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_Kd2KeyPressed

private void MnRekapHarianDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianDokterActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmDokter rhtindakandokter=new DlgRHJmDokter(null,false);
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianDokterActionPerformed

private void MnRekapHarianParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianParamedisActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHJmParamedis rhtindakanparamedis=new DlgRHJmParamedis(null,false);
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianParamedisActionPerformed

private void MnRekapBulananDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulananDokterActionPerformed
   this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmDokter rhtindakandokter=new DlgRBJmDokter(null,false);
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapBulananDokterActionPerformed

private void MnRekapBulananParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapBulananParamedisActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBJmParamedis rhtindakanparamedis=new DlgRBJmParamedis(null,false);
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapBulananParamedisActionPerformed

private void MnRekapHarianPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianPoliActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanPoli rhtindakandokter=new DlgRBTindakanPoli(null,false);
        rhtindakandokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianPoliActionPerformed

private void MnDataPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataPemberianObatActionPerformed
    if(TNoRw.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
        TNoRw.requestFocus();
    }else{
        if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        }else {
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan"); 
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
        }                
    }
}//GEN-LAST:event_MnDataPemberianObatActionPerformed

    private void MnRekapHarianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianObatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatPoli rhtindakandokter=new DlgRBObatPoli(null,false);
        rhtindakandokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHarianObatActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgResepObat resep=new DlgResepObat(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8),"ralan");
                resep.setDokterRalan();
                resep.tampil();
                resep.setVisible(true);
            }            
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaRadiologi periksarad=new DlgPeriksaRadiologi(null,false);
                periksarad.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                periksarad.setLocationRelativeTo(internalFrame1);
                periksarad.emptTeks();
                periksarad.setNoRm(TNoRw.getText(),"Ralan"); 
                periksarad.tampil(); 
                periksarad.isCek();
                periksarad.setVisible(true);
            }            
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowGantiPoli.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kdpoli.getText().trim().equals("")||nmpoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Poli");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw," kd_poli='"+kdpoli.getText()+"'");
            tampilkasir();
            WindowGantiPoli.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void btnCariPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPoliActionPerformed
        akses.setform("DlgKasirRalan");
        pilihan=1;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setAlwaysOnTop(false);
        billing.poli.setVisible(true);
    }//GEN-LAST:event_btnCariPoliActionPerformed

    private void MnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                WindowGantiPoli.setSize(630,80);
                WindowGantiPoli.setLocationRelativeTo(internalFrame1);
                WindowGantiPoli.setAlwaysOnTop(false);
                WindowGantiPoli.setVisible(true);  
            }  
        }
    }//GEN-LAST:event_MnPoliActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ralan");
                resep.panelDiagnosa1.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Batal',biaya_reg='0'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString()+", "+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),"Ralan");
                dlgro.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from laporan_operasi where no_rawat='"+TNoRw.getText()+"'");
            }   
        }
    }//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        filter="no";
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                WindowCaraBayar.setSize(630,80);
                WindowCaraBayar.setLocationRelativeTo(internalFrame1);
                WindowCaraBayar.setVisible(true);
            }   
        }
    }//GEN-LAST:event_MnPenjabActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowCaraBayar.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TCari,"No.Rawat");
        }if(kdpenjab.getText().trim().equals("")||nmpenjab.getText().trim().equals("")){
            Valid.textKosong(kdpenjab,"Jenis Bayar");
        }else{
            //String kdpj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norawat.getText());
            
            //Sequel.meghapus("ubah_penjab","no_rawat",norawat.getText());
            Sequel.mengedit("reg_periksa","no_rawat=?"," kd_pj=?",2,new String[]{kdpenjab.getText(),TNoRw.getText()});
            //Sequel.menyimpan("ubah_penjab","?,?,?,?","Ubah Jenis Bayar",4,new String[]{norawat.getText(),now,kdpj,kdpenjab.getText()});
            
            tampilkasir();
            WindowCaraBayar.dispose();
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBayarActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        akses.setform("DlgKasirRalan");
        billing.penjab.emptTeks();
        billing.penjab.isCek();
        billing.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.penjab.setLocationRelativeTo(internalFrame1);
        billing.penjab.setVisible(true);
    }//GEN-LAST:event_btnBayarActionPerformed

    private void MnHapusAturanPkaiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusAturanPkaiObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from aturan_pakai where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusAturanPkaiObatActionPerformed

    private void MnHapusDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDepositActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from deposit where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusDepositActionPerformed

    private void MnHapusDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDietActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from detail_beri_diet where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDietActionPerformed

    private void MnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from tagihan_obat_langsung where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_obat_racikan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from obat_racikan where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusObatActionPerformed

    private void MnHapusLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusLabActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from saran_kesan_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from periksa_lab where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusLabActionPerformed

    private void MnHapusDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from diagnosa_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDiagnosaActionPerformed

    private void MnHapusDpjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDpjpActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from dpjp_ranap where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusDpjpActionPerformed

    private void MnHapusHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusHemodialisaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from hemodialisa where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusHemodialisaActionPerformed

    private void MnHapusKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusKamarInapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from kamar_inap where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusKamarInapActionPerformed

    private void MnHapusPemeriksaanRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPemeriksaanRalanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPemeriksaanRalanActionPerformed

    private void MnHapusPemeriksaanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPemeriksaanRanapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPemeriksaanRanapActionPerformed

    private void MnHapusPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPotonganActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusPotonganActionPerformed

    private void MnHapusRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRadiologiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from beri_bhp_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from hasil_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from gambar_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusRadiologiActionPerformed

    private void MnHapusPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPiutangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusPiutangActionPerformed

    private void MnHapusProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusProsedurActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from prosedur_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusProsedurActionPerformed

    private void MnHapusRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRanapGabungActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from ranap_gabung where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRanapGabungActionPerformed

    private void MnHapusTindakanRanapDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapDokterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterActionPerformed

    private void MnHapusTindakanRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterParamedisActionPerformed

    private void MnHapusTindakanRanapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRanapParamedisActionPerformed

    private void MnHapusTindakanRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterActionPerformed

    private void MnHapusTindakanRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterParamedisActionPerformed

    private void MnHapusTindakanRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTindakanRalanParamedisActionPerformed

    private void MnHapusResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusResepObatActionPerformed

    private void MnHapusResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepPulangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusResepPulangActionPerformed

    private void MnHapusReturObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusReturObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from returpasien where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusReturObatActionPerformed

    private void MnHapusRujukKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukKeluarActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rujuk where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRujukKeluarActionPerformed

    private void MnHapusRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukMasukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rujuk_masuk where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRujukMasukActionPerformed

    private void MnHapusStokObatRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusStokObatRanapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusStokObatRanapActionPerformed

    private void MnHapusTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTambahanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from tambahan_biaya where no_rawat='"+TNoRw.getText()+"'");
            }
        }
    }//GEN-LAST:event_MnHapusTambahanActionPerformed

    private void MnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusSemuaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from laporan_operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from saran_kesan_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from billing where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from nota_inap where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from nota_jalan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from deposit where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_beri_diet where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from diagnosa_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from dpjp_ranap where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from hemodialisa where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from kamar_inap where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from prosedur_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from ranap_gabung where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rvp_klaim_bpjs where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rujuk where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rujuk_masuk where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from tambahan_biaya where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from pemeriksaan_ranap where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from periksa_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from beri_bhp_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from aturan_pakai where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from tagihan_obat_langsung where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from returpasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_nota_jalan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from mutasi_berkas where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from gambar_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from hasil_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_obat_racikan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from obat_racikan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from booking_operasi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from catatan_perawatan where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from permintaan_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from permintaan_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from pcare_pendaftaran where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from detail_penagihan_piutang where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from permintaan_stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_detail_periksa_lab_perujuk where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_dokter_anak where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_dokter_anestesi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_dokter_pjanak where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_dokter_umum where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_operator1 where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_operator2 where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_operasi_operator3 where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_periksa_lab_perujuk where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_periksa_radiologi_perujuk where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from bayar_rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
                Sequel.queryu("delete from reg_periksa where no_rawat='"+TNoRw.getText()+"'");

                tampilkasir();
            }
        }
    }//GEN-LAST:event_MnHapusSemuaActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRMCari.getText(),TPasienCari.getText());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppBerkasRanapBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasRanapBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Masuk Ranap',now(),'0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00',now()","status='Masuk Ranap',ranap=now()","no_rawat='"+TNoRw.getText()+"'");
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
    }//GEN-LAST:event_ppBerkasRanapBtnPrintActionPerformed

    private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirujuk'");
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
    }//GEN-LAST:event_MnRujukActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgCatatan catatan=new DlgCatatan(null,true);
                catatan.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
                catatan.setSize(720,330);
                catatan.setLocationRelativeTo(internalFrame1);
                catatan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirujuk'");
                MnRujukActionPerformed(evt);
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnDirujukActionPerformed

    private void MnDIrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDIrawatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'"); 
                if(MnKamarInap.isEnabled()==true){
                    MnKamarInapActionPerformed(null);
                    if(tabModekasir.getRowCount()!=0){tampilkasir();}
                }else{
                    if(tabModekasir.getRowCount()!=0){tampilkasir();}
                }                    
            }            
        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Meninggal'");
                DlgPasienMati dlgPasienMati=new DlgPasienMati(null,false);
                dlgPasienMati.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString()); 
                dlgPasienMati.isCek();
                dlgPasienMati.setVisible(true);                
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnMeninggalActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    jmlparsial=0;
                    if(aktifkanparsial.equals("yes")){
                        jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString());
                    }
                    if(jmlparsial>0){
                        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                        resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.setNoRm(TNoRw.getText(),new Date(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8),
                                tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"ralan");
                        resep.isCek();
                        resep.tampilobat();
                        resep.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                        }else{ 
                            DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                            resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            resep.setLocationRelativeTo(internalFrame1);
                            resep.setNoRm(TNoRw.getText(),new Date(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8),
                                    tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"ralan");
                            resep.isCek();
                            resep.tampilobat();
                            resep.setVisible(true);
                        }
                    }                      
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKasirRalan.requestFocus();
            }          
        }
    }//GEN-LAST:event_MnResepDOkterActionPerformed

    private void MnUrutNoRawatDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatDescActionPerformed
        order="reg_periksa.no_rawat desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutNoRawatDescActionPerformed

    private void MnUrutNoRawatAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatAscActionPerformed
        order="reg_periksa.no_rawat asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutNoRawatAscActionPerformed

    private void MnUrutTanggalDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalDescActionPerformed
        order="reg_periksa.tgl_registrasi desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutTanggalDescActionPerformed

    private void MnUrutTanggalAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalAscActionPerformed
        order="reg_periksa.tgl_registrasi asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutTanggalAscActionPerformed

    private void MnUrutDokterDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterDescActionPerformed
        order="dokter.nm_dokter desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutDokterDescActionPerformed

    private void MnUrutDokterAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterAscActionPerformed
        order="dokter.nm_dokter asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutDokterAscActionPerformed

    private void MnUrutPoliklinikDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikDescActionPerformed
        order="poliklinik.nm_poli desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutPoliklinikDescActionPerformed

    private void MnUrutPoliklinikAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikAscActionPerformed
        order="poliklinik.nm_poli asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutPoliklinikAscActionPerformed

    private void MnUrutPenjabDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabDescActionPerformed
        order="penjab.png_jawab desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutPenjabDescActionPerformed

    private void MnUrutPenjabAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabAscActionPerformed
        order="penjab.png_jawab asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutPenjabAscActionPerformed

    private void MnUrutStatusDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusDescActionPerformed
        order="reg_periksa.stts desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutStatusDescActionPerformed

    private void MnUrutStatusAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusAscActionPerformed
        order="reg_periksa.stts asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutStatusAscActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampilkasir();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilkasir2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbKasirRalan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKasirRalan2MouseClicked
        if(tabModekasir2.getRowCount()!=0){
            try {
                getDatakasir2();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                i=tbKasirRalan2.getSelectedColumn();
                if(i==0){
                    if(akses.gettindakan_ralan()==true){
                        MnRawatJalan1ActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(akses.getberi_obat()==true){
                        MnPemberianObat1ActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBilling1ActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(MnKamarInap1.isEnabled()==true){
                        MnKamarInap1ActionPerformed(null);
                    }                    
                }
            }
        }
    }//GEN-LAST:event_tbKasirRalan2MouseClicked

    private void tbKasirRalan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKasirRalan2KeyPressed
        if(tabModekasir2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDatakasir2();
                } catch (java.lang.NullPointerException e) {
                }
            }
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKasirRalan2.getSelectedColumn();
                if(i==0){
                    if(akses.gettindakan_ralan()==true){
                        MnRawatJalan1ActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(akses.getberi_obat()==true){
                        MnPemberianObat1ActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBilling1ActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(MnKamarInap1.isEnabled()==true){
                        MnKamarInap1ActionPerformed(null);
                    }                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbKasirRalan2KeyPressed

    private void MnPoliInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliInternalActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            }
        }
    }//GEN-LAST:event_MnPoliInternalActionPerformed

    private void MnUrutNoRawatDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatDesc2ActionPerformed
        order="reg_periksa.no_rawat desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutNoRawatDesc2ActionPerformed

    private void MnUrutNoRawatAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatAsc2ActionPerformed
        order="reg_periksa.no_rawat asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutNoRawatAsc2ActionPerformed

    private void MnUrutTanggalDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalDesc2ActionPerformed
        order="reg_periksa.tgl_registrasi desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutTanggalDesc2ActionPerformed

    private void MnUrutTanggalAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalAsc2ActionPerformed
        order="reg_periksa.tgl_registrasi asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutTanggalAsc2ActionPerformed

    private void MnUrutDokterDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterDesc2ActionPerformed
        order="dokter.nm_dokter desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutDokterDesc2ActionPerformed

    private void MnUrutDokterAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterAsc2ActionPerformed
        order="dokter.nm_dokter asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutDokterAsc2ActionPerformed

    private void MnUrutPoliklinikDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikDesc2ActionPerformed
        order="poliklinik.nm_poli desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutPoliklinikDesc2ActionPerformed

    private void MnUrutPoliklinikAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikAsc2ActionPerformed
        order="poliklinik.nm_poli asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutPoliklinikAsc2ActionPerformed

    private void MnUrutPenjabDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabDesc2ActionPerformed
        order="penjab.png_jawab desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutPenjabDesc2ActionPerformed

    private void MnUrutPenjabAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabAsc2ActionPerformed
        order="penjab.png_jawab asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutPenjabAsc2ActionPerformed

    private void MnUrutStatusDesc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusDesc2ActionPerformed
        order="reg_periksa.stts desc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutStatusDesc2ActionPerformed

    private void MnUrutStatusAsc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusAsc2ActionPerformed
        order="reg_periksa.stts asc";
        tampilkasir2();
    }//GEN-LAST:event_MnUrutStatusAsc2ActionPerformed

    private void MnKamarInap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInap1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){     
                if(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),9).toString().equals("Batal")){
                    JOptionPane.showMessageDialog(null,"Pasien berstatus batal periksa...!");
                }else{
                    if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                    }else{ 
                        akses.setstatus(true);
                        kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        kamarinap.setLocationRelativeTo(internalFrame1);
                        kamarinap.emptTeks();
                        kamarinap.isCek();
                        kamarinap.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString());   
                        kamarinap.setVisible(true);                    
                    }
                }
            }               
        }
    }//GEN-LAST:event_MnKamarInap1ActionPerformed

    private void MnObatLangsung1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsung1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                        WindowObatBhp.setSize(590,80);
                        WindowObatBhp.setLocationRelativeTo(internalFrame1);
                        WindowObatBhp.setVisible(true);
                    }        
                }
            }                  
        }  
    }//GEN-LAST:event_MnObatLangsung1ActionPerformed

    private void MnDataPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataPemberianObat1ActionPerformed
       if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
                    dlgrwinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgrwinap.setLocationRelativeTo(internalFrame1);
                    dlgrwinap.isCek();
                    dlgrwinap.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),"ralan"
                    ); 
                    dlgrwinap.setDokter(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    dlgrwinap.tampilPO();
                    dlgrwinap.setVisible(true);
                } 
            }                               
        }
    }//GEN-LAST:event_MnDataPemberianObat1ActionPerformed

    private void MnDataRalan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataRalan1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{                    
                    dlgrwjl2.isCek();
                    dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgrwjl2.setLocationRelativeTo(internalFrame1);
                    dlgrwjl2.SetPoli(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),13).toString());
                    dlgrwjl2.SetPj(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),14).toString());
                    dlgrwjl2.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );    
                    dlgrwjl2.setVisible(true);
                } 
            }                               
        }
    }//GEN-LAST:event_MnDataRalan1ActionPerformed

    private void MnHapusRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukanActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{ 
                    Sequel.queryu2("delete from rujukan_internal_poli where no_rawat=? and kd_dokter=?",2,new String[]{
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString()
                    });
                    tampilkasir2();
                }
            }
        }
    }//GEN-LAST:event_MnHapusRujukanActionPerformed

    private void MnRawatJalan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalan1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        DlgCariPerawatanRalan dlgrwjl=new DlgCariPerawatanRalan(null,false);
                        dlgrwjl.setNoRm(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),
                            "rawat_jl_dr","-","-"
                        );  
                        dlgrwjl.isCek();
                        dlgrwjl.setPoli(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),13).toString());
                        dlgrwjl.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgrwjl.setLocationRelativeTo(internalFrame1);
                        dlgrwjl.setVisible(true);
                    }
                }
            }               
        }
    }//GEN-LAST:event_MnRawatJalan1ActionPerformed

    private void MnPeriksaLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLab1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
                    periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    periksalab.setLocationRelativeTo(internalFrame1);
                    periksalab.emptTeks();
                    periksalab.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan"); 
                    periksalab.setDokterPerujuk(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    periksalab.isCek();
                    periksalab.setVisible(true);
                } 
            }                           
        }
    }//GEN-LAST:event_MnPeriksaLab1ActionPerformed

    private void MnPeriksaRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologi1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgPeriksaRadiologi periksarad=new DlgPeriksaRadiologi(null,false);
                    periksarad.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    periksarad.setLocationRelativeTo(internalFrame1);
                    periksarad.emptTeks();
                    periksarad.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan"); 
                    periksarad.setDokterPerujuk(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    periksarad.tampil(); 
                    periksarad.isCek();
                    periksarad.setVisible(true);
                }    
            }        
        }
    }//GEN-LAST:event_MnPeriksaRadiologi1ActionPerformed

    private void MnOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasi1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
                    dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString()+
                        ", "+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString(),"Ralan"
                    );
                    dlgro.setVisible(true);
                }                    
            }                
        }
    }//GEN-LAST:event_MnOperasi1ActionPerformed

    private void MnPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObat1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        TKdPny.setText("-");
                        DlgCariObat dlgobt=new DlgCariObat(null,false);
                        dlgobt.setNoRm(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString(),
                            Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'"),
                            Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'")
                        );
                        dlgobt.isCek();
                        dlgobt.setDokter(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                        );
                        dlgobt.tampilobat();
                        dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgobt.setLocationRelativeTo(internalFrame1);
                        dlgobt.setVisible(true);
                    }                    
                }  
            }            
        }
    }//GEN-LAST:event_MnPemberianObat1ActionPerformed

    private void MnNoResep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResep1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgResepObat resep=new DlgResepObat(null,false);
                    resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.emptTeks();
                    resep.isCek();
                    resep.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(0,2),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(3,5),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(6,8),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),"ralan"                            
                    );
                    resep.tampil();
                    resep.setVisible(true);
                }  
            }                          
        }
    }//GEN-LAST:event_MnNoResep1ActionPerformed

    private void MnResepDOkter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkter1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                    resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        new Date(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(0,2),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(3,5),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(6,8),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),"ralan"
                    );
                    resep.isCek();                    
                    resep.tampilobat();
                    resep.setVisible(true);
                } 
            }           
        }
    }//GEN-LAST:event_MnResepDOkter1ActionPerformed

    private void MnBilling1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBilling1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    try {
                        sudah=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
                        pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                        try{                                                
                            pscaripiutang.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString());
                            rskasir=pscaripiutang.executeQuery();
                            if(rskasir.next()){
                                i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                                if(i==JOptionPane.YES_OPTION){
                                     DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                     piutang.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),rskasir.getDate(1));
                                     piutang.tampil();
                                     piutang.isCek();
                                     piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                     piutang.setLocationRelativeTo(internalFrame1);
                                     piutang.setVisible(true);
                                }else{
                                    if(akses.getbilling_ralan()==true){
                                        otomatisRalan2();
                                    }

                                    billing.TNoRw.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());  
                                    billing.isCek();
                                    billing.isRawat(); 
                                    if(sudah>0){
                                        billing.setPiutang();
                                    }
                                    billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                    billing.setLocationRelativeTo(internalFrame1);
                                    tampilkasir2();
                                    billing.setVisible(true);
                                }
                            }else{
                                if(akses.getbilling_ralan()==true){
                                    otomatisRalan2();
                                }
                                billing.TNoRw.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());  
                                billing.isCek();
                                billing.isRawat(); 
                                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                billing.setLocationRelativeTo(internalFrame1);
                                tampilkasir2();
                                billing.setVisible(true);
                            }
                        }catch(Exception ex){
                            System.out.println("Notifikasi : "+ex);
                        } finally{
                            if(rskasir!=null){
                                rskasir.close();
                            }
                            if(pscaripiutang!=null){
                                pscaripiutang.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }                
            }            
        }
    }//GEN-LAST:event_MnBilling1ActionPerformed

    private void MnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosa1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                    resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.isCek();
                    resep.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),"Ralan"
                    );
                    resep.panelDiagnosa1.tampil();
                    resep.setVisible(true);
                }                    
            }
        }
    }//GEN-LAST:event_MnDiagnosa1ActionPerformed

    private void ppRiwayat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayat1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                resume.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString());
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
    }//GEN-LAST:event_ppRiwayat1ActionPerformed

    private void MnUrutRegDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRegDesc1ActionPerformed
        order="reg_periksa.no_reg desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutRegDesc1ActionPerformed

    private void MnUrutRegAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRegAsc1ActionPerformed
        order="reg_periksa.no_reg asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutRegAsc1ActionPerformed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        Valid.pindah(evt,TCari,TNoRw);
    }//GEN-LAST:event_TNoRegKeyPressed

    private void LabelCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelCatatanMouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_LabelCatatanMouseClicked

    private void internalFrame7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_internalFrame7MouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_internalFrame7MouseClicked

    private void MnBillingParsialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingParsialActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{          
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                try {
                    sudah=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
                    pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try{                                                
                        pscaripiutang.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
                        rskasir=pscaripiutang.executeQuery();
                        if(rskasir.next()){
                            i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                                 DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                 piutang.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),rskasir.getDate(1));
                                 piutang.tampil();
                                 piutang.isCek();
                                 piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                 piutang.setLocationRelativeTo(internalFrame1);
                                 piutang.setVisible(true);
                            }else{
                                if(akses.getbilling_ralan()==true){
                                    otomatisRalan();
                                }
                                billingparsial();
                            }
                        }else{
                            if(akses.getbilling_ralan()==true){
                                otomatisRalan();
                            }
                            billingparsial();
                        }
                    }catch(Exception ex){
                        System.out.println("Notifikasi : "+ex);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pscaripiutang!=null){
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }            
        }
    }//GEN-LAST:event_MnBillingParsialActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void ppBerkasDigital1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigital1BtnPrintActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
                berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
                
                try {
                    if(akses.gethapus_berkas_digital_perawatan()==true){
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
                    }else{
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
                    }   
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                berkas.setLocationRelativeTo(internalFrame1);
                berkas.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }                
    }//GEN-LAST:event_ppBerkasDigital1BtnPrintActionPerformed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        akses.setform("DlgKasirRalan");
        filter="yes";
        billing.penjab.emptTeks();
        billing.penjab.isCek();
        billing.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.penjab.setLocationRelativeTo(internalFrame1);
        billing.penjab.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    private void MnStatusBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBaruActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"status_poli='Baru'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
    }//GEN-LAST:event_MnStatusBaruActionPerformed

    private void MnStatusLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusLamaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"status_poli='Lama'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
    }//GEN-LAST:event_MnStatusLamaActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataInsidenKeselamatan aplikasi=new DlgDataInsidenKeselamatan(null,false);
            aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.isCek();
            aplikasi.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),4).toString());
            aplikasi.tampil();
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void ppIKP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKP1BtnPrintActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataInsidenKeselamatan aplikasi=new DlgDataInsidenKeselamatan(null,false);
                aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                aplikasi.setLocationRelativeTo(internalFrame1);
                aplikasi.isCek();
                aplikasi.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),DTPCari1.getDate(),DTPCari2.getDate(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),4).toString());
                aplikasi.tampil();
                aplikasi.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppIKP1BtnPrintActionPerformed

    private void MnPenjualan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualan1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRwCari.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPenjualan penjualan=new DlgPenjualan(null,false);
                penjualan.isCek();
                penjualan.setPasien(TNoRMCari.getText());  
                penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                penjualan.setLocationRelativeTo(internalFrame1);
                penjualan.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnPenjualan1ActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);            
                    form.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),4).toString(),"Ralan"); 
                    form.setVisible(true);
                } 
            }               
        }
    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLabelTracker.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLabelTracker2.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelTracker3.jasper","report","::[ Label Tracker ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker2ActionPerformed

    private void MnLabelTracker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker3ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelTracker4.jasper","report","::[ Label Tracker ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker3ActionPerformed

    private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeActionPerformed
        if(!TPasienCari.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasienCari.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRMCari.getText()));
            param.put("norm",TNoRMCari.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            Valid.MyReportqry("rptBarcodeRawat.jasper","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if(!TPasienCari.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasienCari.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRMCari.getText()));
            param.put("norm",TNoRMCari.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            Valid.MyReportqry("rptBarcodeRawat2.jasper","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM18.jasper","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM6.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnGelang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang2ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM7.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang2ActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang3ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM8.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang3ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang4ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM10.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang4ActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang5ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM14.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang5ActionPerformed

    private void MnGelang6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang6ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM16.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang6ActionPerformed

    private void MnGelang7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang7ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM19.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang7ActionPerformed

    private void MnSuratKontrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratKontrolActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    SuratKontrol form=new SuratKontrol(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);      
                    form.emptTeks();      
                    form.setNoRm(TNoRMCari.getText(),TPasienCari.getText()); 
                    form.setVisible(true);
                }   
            }             
        }
    }//GEN-LAST:event_MnSuratKontrolActionPerformed

    private void MnHapusBookingOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusBookingOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from booking_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusBookingOperasiActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                    dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(TNoRw.getText(),"Ralan");
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                    dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(TNoRw.getText(),"Ralan");
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnPulangPaksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangPaksaBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Pulang Paksa'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }

        }
    }//GEN-LAST:event_MnPulangPaksaBtnPrintActionPerformed

    private void MnJadwalOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasi1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()>-1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),4).toString(),"Ralan");
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnJadwalOperasi1ActionPerformed

    private void MnPermintaanLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLab1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()>-1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                    dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan",
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString());
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnPermintaanLab1ActionPerformed

    private void MnPermintaanRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologi1ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()>-1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                    dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan",
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString());
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologi1ActionPerformed

    private void ppBerkasDIterimaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDIterimaBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Diterima',diterima=now()","no_rawat='"+TNoRw.getText()+"'");
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Berkas Diterima'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
    }//GEN-LAST:event_ppBerkasDIterimaBtnPrintActionPerformed

    private void MnPiutangObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPiutangObatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPiutang penjualan=new DlgPiutang(null,false);
                penjualan.isCek();
                penjualan.emptTeks();
                penjualan.setPasien(TNoRwCari.getText(),TNoRMCari.getText(),TPasienCari.getText());  
                penjualan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                penjualan.setLocationRelativeTo(internalFrame1);
                penjualan.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnPiutangObatActionPerformed

    private void MnCetakSuratSehatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehatActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString()+"\nID "+(finger.equals("")?tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString():finger)+"\n"+Valid.SetTgl3(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString()));  
                Valid.MyReportqry("rptSuratSehat.jasper","report","::[ Surat Keterangan Sehat ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.tgl_lahir,pasien.jk,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y')as tgl_registrasi,"+
                    " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat "+
                    " from reg_periksa inner join pasien inner join dokter "+
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "+
                    "where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSehatActionPerformed

    private void MnCetakSuratSehat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehat1ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("norawat",TNoRw.getText());
                param.put("bb",Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
                param.put("td",Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
                param.put("tb",Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString()+"\nID "+(finger.equals("")?tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString():finger)+"\n"+Valid.SetTgl3(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString()));  
                Valid.MyReport("rptSuratSehat2.jasper",param,"::[ Surat Keterangan Sehat ]::");
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSehat1ActionPerformed

    private void MnCetakBebasNarkobaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakBebasNarkobaActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratBebasNarkoba resume=new SuratBebasNarkoba(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakBebasNarkobaActionPerformed

    private void MnCetakSuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratSakit resume=new SuratSakit(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSakitActionPerformed

    private void MnCetakSuratSakit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit1ActionPerformed
        DlgSakit2.setSize(550,151);
        DlgSakit2.setLocationRelativeTo(internalFrame1);
        DlgSakit2.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratSakit1ActionPerformed

    private void MnPersetujuanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanMedisActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("persetujuantindakanmedis.jasper","report","::[ Persetujuan Tindakan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPersetujuanMedisActionPerformed

    private void MnBuktiPelayananRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBuktiPelayananRalanActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiPelayananRalan.jasper","report","::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBuktiPelayananRalanActionPerformed

    private void MnLembarRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptRM11.jasper","report","::[ Lembar Rawat Jalan ]::",
                "SELECT reg_periksa.tgl_registrasi, reg_periksa.jam_reg, "+
                "poliklinik.nm_poli, pasien.no_rkm_medis, pasien.nm_pasien, "+
                "pasien.no_ktp, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir,"+
                "pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel"+
                ",', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) AS alamat,"+
                "pasien.gol_darah,pasien.pekerjaan,pasien.stts_nikah,"+
                "pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd,pasien.keluarga,pasien.namakeluarga,"+
                "penjab.png_jawab,pasien.pekerjaanpj,concat(pasien.alamatpj,"+
                "', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',"+
                "pasien.kabupatenpj) AS alamatpj FROM pasien INNER JOIN "+
                "kelurahan INNER JOIN kecamatan INNER JOIN kabupaten "+
                "INNER JOIN penjab ON pasien.kd_kel = kelurahan.kd_kel "+
                "AND pasien.kd_kec = kecamatan.kd_kec AND pasien.kd_kab = kabupaten.kd_kab "+
                "INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "+
                "AND reg_periksa.kd_pj = penjab.kd_pj INNER JOIN poliklinik "+
                "ON poliklinik.kd_poli = reg_periksa.kd_poli WHERE "+
                "reg_periksa.no_rawat ='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarRalanActionPerformed

    private void MnBlangkoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBlangkoResepActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBlankoResep.jasper","report","::[ Bukti Register ]::",
                "select pasien.no_ktp,pasien.no_peserta,reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBlangkoResepActionPerformed

    private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            if(pilihan==1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("hari",lmsakit.getText());
                param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
                param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptSuratSakit.jasper","report","::[ Surat Sakit ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                    " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                    " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "+
                    "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                    "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());
            }else if(pilihan==2){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("hari",lmsakit.getText());
                param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
                param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptSuratSakit3.jasper","report","::[ Surat Sakit ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                    " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                    " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                    " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "+
                    "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                    "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());

            }else if(pilihan==3){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("hari",lmsakit.getText());
                param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
                param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("penyakit",Sequel.cariIsi("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join reg_periksa inner join penyakit "+
                    "on diagnosa_pasien.no_rawat=reg_periksa.no_rawat and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat=? and diagnosa_pasien.prioritas='1'",TNoRw.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptSuratSakit4.jasper","report","::[ Surat Sakit ]::",
                "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());

        }

        }
    }//GEN-LAST:event_BtnPrint2ActionPerformed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        DlgSakit.dispose();
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari",lmsakit.getText());
            param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("nomersurat",NomorSurat.getText());
            param.put("dokterpj",CrDokter3.getText());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptSuratSakit2.jasper","report","::[ Surat Sakit ]::",
                "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat" +
                " from reg_periksa inner join pasien inner join dokter" +
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        akses.setform("DlgKasirRalan");
        pilihan=3;
        billing.dokter.isCek();
        billing.dokter.TCari.requestFocus();
        billing.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabModekasir.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            semua=caripenjab.equals("")&&CrPoli.getText().trim().equals("")&&CrPtg.getText().trim().equals("")&&cmbStatus.getSelectedItem().toString().equals("Semua")&&cmbStatusBayar.getSelectedItem().toString().equals("Semua")&&TCari.getText().trim().equals("");
            Valid.MyReportqry("rptKasirRalan.jasper","report","::[ Data Registrasi Periksa ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                    "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts,penjab.png_jawab,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur, "+
                    "reg_periksa.status_bayar,reg_periksa.status_poli,reg_periksa.kd_pj,reg_periksa.kd_poli,pasien.no_tlp "+
                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  "+
                    "reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+tampildiagnosa+
                    (semua?"":"and reg_periksa.kd_pj like '%"+caripenjab+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and dokter.nm_dokter like '%"+CrPtg.getText()+"%' and "+
                    "reg_periksa.stts like '%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%' and reg_periksa.status_bayar like '%"+cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+
                    "(reg_periksa.no_reg like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.tgl_registrasi like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.kd_dokter like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or reg_periksa.p_jawab like '%"+TCari.getText().trim()+"%' or "+
                    "penjab.png_jawab like '%"+TCari.getText().trim()+"%' or reg_periksa.almt_pj like '%"+TCari.getText().trim()+"%' or reg_periksa.status_bayar like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.hubunganpj like '%"+TCari.getText().trim()+"%' )")+
                    "order by "+order,param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                akses.setform("DlgKasirRalan");
                BPJSDataSEP dlgki=new BPJSDataSEP(null,false);
                dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgki.setLocationRelativeTo(internalFrame1);
                dlgki.isCek();
                dlgki.setNoRm2(TNoRw.getText(),Valid.SetTgl2(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString()),"2. Ralan",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),18).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),4).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());   
                dlgki.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSEPActionPerformed

    private void MnSJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSJPActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgKasirRalan");
            InhealthDataSJP dlgki=new InhealthDataSJP(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText(),Valid.SetTgl2(Tanggal.getText()),"1 RJTP RAWAT JALAN TINGKAT PERTAMA","IGDK","Unit IGD/UGD");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSJPActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukSisruteActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgKasirRalan");
            SisruteRujukanKeluar dlgki=new SisruteRujukanKeluar(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setPasien(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnTeridentifikasiTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTeridentifikasiTBActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataTB resep=new DlgDataTB(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.emptTeks();
                resep.setNoRM(TNoRw.getText());
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTeridentifikasiTBActionPerformed

    private void MnUrutRMDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRMDescActionPerformed
        order="reg_periksa.no_rkm_medis desc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutRMDescActionPerformed

    private void MnUrutRMAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRMAscActionPerformed
        order="reg_periksa.no_rkm_medis asc";
        tampilkasir();
    }//GEN-LAST:event_MnUrutRMAscActionPerformed

    private void MnDataTriaseIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataTriaseIGDActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMTriaseIGD form=new RMTriaseIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnDataTriaseIGDActionPerformed

    private void ppResumeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResumeBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
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
        }
    }//GEN-LAST:event_ppResumeBtnPrintActionPerformed

    private void MnPeniliaianAwalKeperawatanRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalKeperawatanRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanRalan form=new RMPenilaianAwalKeperawatanRalan(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalKeperawatanRalanActionPerformed

    private void ppPasienCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPasienCoronaBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CoronaPasien form=new CoronaPasien(null,false);
            form.setPasien(TNoRMCari.getText());
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppPasienCoronaBtnPrintActionPerformed

    private void ppPerawatanCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPerawatanCoronaBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            INACBGPerawatanCorona form=new INACBGPerawatanCorona(null,false);
            form.emptTeks();
            form.setPasien(TNoRwCari.getText(),TNoRMCari.getText(),TPasienCari.getText());
            form.tampil();
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppPerawatanCoronaBtnPrintActionPerformed

    private void MnPeniliaianAwalKeperawatanGigiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalKeperawatanGigiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanGigi form=new RMPenilaianAwalKeperawatanGigi(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalKeperawatanGigiActionPerformed

    private void ppDeteksiDIniCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDeteksiDIniCoronaBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDeteksiDiniCorona form=new RMDeteksiDiniCorona(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppDeteksiDIniCoronaBtnPrintActionPerformed

    private void MnCetakSuratHamilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratHamilActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratTidakHamil resume=new SuratTidakHamil(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratHamilActionPerformed

    private void MnPeniliaianAwalKeperawatanKebidananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalKeperawatanKebidananActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRMCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
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
        }
    }//GEN-LAST:event_MnPeniliaianAwalKeperawatanKebidananActionPerformed

    private void MnPeniliaianAwalKeperawatanBayiAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalKeperawatanBayiAnakActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanBayiAnak form=new RMPenilaianAwalKeperawatanBayiAnak(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalKeperawatanBayiAnakActionPerformed

    private void MnPCareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPCareActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                akses.setform("DlgReg");
                PCareDataPendaftaran dlgki=new PCareDataPendaftaran(null,false);
                dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgki.setLocationRelativeTo(internalFrame1);
                dlgki.isCek();
                dlgki.setNoRm(TNoRw.getText());
                dlgki.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
                tbKasirRalan.requestFocus();
            }   
        }
    }//GEN-LAST:event_MnPCareActionPerformed

    private void MnBarcode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode2ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptBarcodeRawat3.jasper",param,"::[ Barcode No.RM ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode2ActionPerformed

    private void MnCetakSuratCovidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratCovidActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKeteranganCovid resume=new SuratKeteranganCovid(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratCovidActionPerformed

    private void MnHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHemodialisaActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMHemodialisa resume=new RMHemodialisa(null,false);
                resume.emptTeks();
                resume.setNoRm(TNoRw.getText());
                resume.isCek();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnHemodialisaActionPerformed

    private void MnCetakSuratCutiHamilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratCutiHamilActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratCutiHamil resume=new SuratCutiHamil(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratCutiHamilActionPerformed

    private void MnPeriksaLabPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabPAActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaLaboratoriumPA periksalab=new DlgPeriksaLaboratoriumPA(null,false);
                periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(TNoRw.getText(),"Ralan"); 
                periksalab.isCek();
                periksalab.setVisible(true);
            }            
        }
    }//GEN-LAST:event_MnPeriksaLabPAActionPerformed

    private void MnPeriksaLabPA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabPA2ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgPeriksaLaboratoriumPA periksalab=new DlgPeriksaLaboratoriumPA(null,false);
                    periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    periksalab.setLocationRelativeTo(internalFrame1);
                    periksalab.emptTeks();
                    periksalab.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan"); 
                    periksalab.setDokterPerujuk(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    periksalab.isCek();
                    periksalab.setVisible(true);
                } 
            }                           
        }
    }//GEN-LAST:event_MnPeriksaLabPA2ActionPerformed

    private void MnPermintaanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRanapActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    DlgPermintaanRanap form=new DlgPermintaanRanap(null,false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);               
                    form.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),9).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),4).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),19).toString()); 
                    form.setVisible(true);
                } 
            }               
        }
    }//GEN-LAST:event_MnPermintaanRanapActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                try {
                    pskasir=koneksi.prepareStatement("select no_sep,no_kartu,tanggal_lahir,jkel,nmdiagnosaawal from bridging_sep where no_rawat=?");
                    try {
                        pskasir.setString(1,TNoRw.getText());
                        rskasir=pskasir.executeQuery();
                        if(rskasir.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSSuratKontrol form=new BPJSSuratKontrol(null,false);
                            form.setNoRm(TNoRwCari.getText(),rskasir.getString("no_sep"),rskasir.getString("no_kartu"),TNoRMCari.getText(),TPasienCari.getText(),rskasir.getString("tanggal_lahir"),rskasir.getString("jkel"),rskasir.getString("nmdiagnosaawal"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pskasir!=null){
                            pskasir.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_ppSuratKontrolBtnPrintActionPerformed

    private void MnCetakSuratSehat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehat2ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKeteranganSehat resume=new SuratKeteranganSehat(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSehat2ActionPerformed

    private void ppSuratPRIBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPRIBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                try {
                    pskasir=koneksi.prepareStatement("select no_peserta,tgl_lahir,jk from pasien where no_rkm_medis=?");
                    try {
                        pskasir.setString(1,TNoRMCari.getText());
                        rskasir=pskasir.executeQuery();
                        if(rskasir.next()){
                            if(rskasir.getString("no_peserta").length()<13){
                                JOptionPane.showMessageDialog(null,"Kartu BPJS Pasien tidak valid, silahkan hubungi bagian terkait..!!");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSSPRI form=new BPJSSPRI(null,false);
                                form.setNoRm(TNoRwCari.getText(),rskasir.getString("no_peserta"),TNoRMCari.getText(),TPasienCari.getText(),rskasir.getString("tgl_lahir"),rskasir.getString("jk"),"-");
                                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pskasir!=null){
                            pskasir.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_ppSuratPRIBtnPrintActionPerformed

    private void MnCetakSuratSakitPihak2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitPihak2ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratSakitPihak2 resume=new SuratSakitPihak2(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),6).toString(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSakitPihak2ActionPerformed

    private void MnCetakSuratBebasTBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratBebasTBCActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKeteranganBebasTBC resume=new SuratKeteranganBebasTBC(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratBebasTBCActionPerformed

    private void MnSuratButaWarnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratButaWarnaActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratButaWarna resume=new SuratButaWarna(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratButaWarnaActionPerformed

    private void MnSuratBebasTatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratBebasTatoActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratBebasTato resume=new SuratBebasTato(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratBebasTatoActionPerformed

    private void MnSuratKewaspadaanKesehatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratKewaspadaanKesehatanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKewaspadaanKesehatan resume=new SuratKewaspadaanKesehatan(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRMCari.getText(),TPasienCari.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratKewaspadaanKesehatanActionPerformed

    private void MnPeniliaianAwalMedisRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisRalanDewasa form=new RMPenilaianAwalMedisRalanDewasa(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisRalanActionPerformed

    private void MnPersetujuanMedis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanMedis1ActionPerformed
        if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("persetujuantindakanmedis2.jasper","report","::[ Persetujuan Tindakan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            Valid.MyReportqry("persetujuantindakanmedis3.jasper","report","::[ Persetujuan Tindakan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRMCari.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPersetujuanMedis1ActionPerformed

    private void MnPeniliaianAwalMedisRalanKebidananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisRalanKebidananActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisRalanKandungan form=new RMPenilaianAwalMedisRalanKandungan(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisRalanKebidananActionPerformed

    private void MnPenilaianAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianAwalMedisIGDActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisIGD form=new RMPenilaianAwalMedisIGD(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianAwalMedisIGDActionPerformed

    private void MnPeniliaianAwalMedisRalanBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisRalanBayiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisRalanAnak form=new RMPenilaianAwalMedisRalanAnak(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisRalanBayiActionPerformed

    private void MnPenilaianFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianFisioterapiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianFisioterapiActionPerformed

    private void ppProgramPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppProgramPRBActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                try {
                    pskasir=koneksi.prepareStatement("select bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.kddpjp,bridging_sep.nmdpdjp,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pasien.email "+
                        "from bridging_sep inner join pasien on bridging_sep.nomr=pasien.no_rkm_medis "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where bridging_sep.no_rawat=?");
                    try {
                        pskasir.setString(1,TNoRw.getText());
                        rskasir=pskasir.executeQuery();
                        if(rskasir.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSProgramPRB form=new BPJSProgramPRB(null,false);
                            form.setNoRm(TNoRwCari.getText(),rskasir.getString("no_sep"),rskasir.getString("no_kartu"),TNoRMCari.getText(),TPasienCari.getText(),rskasir.getString("alamat"),rskasir.getString("email"),rskasir.getString("kddpjp"),rskasir.getString("nmdpdjp"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pskasir!=null){
                            pskasir.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_ppProgramPRBActionPerformed

    private void ppTampilkanBelumDiagnosaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBelumDiagnosaBtnPrintActionPerformed
        tampildiagnosa=" and reg_periksa.no_rawat not in (select no_rawat from diagnosa_pasien) ";
        tampilkasir();
    }//GEN-LAST:event_ppTampilkanBelumDiagnosaBtnPrintActionPerformed

    private void ppSuplesiJasaRaharjaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                try {
                    pskasir=koneksi.prepareStatement("select no_kartu,nama_pasien,tglsep from bridging_sep where no_rawat=?");
                    try {
                        pskasir.setString(1,TNoRw.getText());
                        rskasir=pskasir.executeQuery();
                        if(rskasir.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekSuplesiJasaRaharja form=new BPJSCekSuplesiJasaRaharja(null,false);
                            form.setRM(rskasir.getString("no_kartu"),rskasir.getString("nama_pasien"),rskasir.getDate("tglsep"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pskasir!=null){
                            pskasir.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed

    private void ppDataIndukKecelakaanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataIndukKecelakaanBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                try {
                    pskasir=koneksi.prepareStatement("select no_kartu,nama_pasien from bridging_sep where no_rawat=?");
                    try {
                        pskasir.setString(1,TNoRw.getText());
                        rskasir=pskasir.executeQuery();
                        if(rskasir.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekDataIndukKecelakaan form=new BPJSCekDataIndukKecelakaan(null,false);
                            form.setRM(rskasir.getString("no_kartu"),rskasir.getString("nama_pasien"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(pskasir!=null){
                            pskasir.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_ppDataIndukKecelakaanBtnPrintActionPerformed

    private void MnPenilaianMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMCUActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMMCU form=new RMMCU(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianMCUActionPerformed

    private void MnUjiFungsiKFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUjiFungsiKFRActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMUjiFungsiKFR form=new RMUjiFungsiKFR(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnUjiFungsiKFRActionPerformed

    private void ppMasukPoliBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppMasukPoliBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                Sequel.queryu("delete from antripoli where kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"' and kd_poli='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),18).toString()+"'");
                Sequel.queryu("insert into antripoli values('"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"','"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),18).toString()+"','1','"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString()+"')");
            }
        }
    }//GEN-LAST:event_ppMasukPoliBtnPrintActionPerformed

    private void MnPenilaianAwalKeperawatanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianAwalKeperawatanIGDActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanIGD form=new RMPenilaianAwalKeperawatanIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianAwalKeperawatanIGDActionPerformed

    private void MnGabungNoRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGabungNoRawatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{
                    norawatdipilih=tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString();
                    normdipilih=tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString();
                    JOptionPane.showMessageDialog(rootPane,"Silahkan pilih No.Rawat yang mau digabung...!");
                }
            }
        }
    }//GEN-LAST:event_MnGabungNoRawatActionPerformed

    private void MnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanObservasiIGD form=new RMDataCatatanObservasiIGD(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCatatanObservasiIGDActionPerformed

    private void MnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    jmlparsial=0;
                    if(aktifkanparsial.equals("yes")){
                        jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString());
                    }
                    if(jmlparsial>0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgCopyResep daftar=new DlgCopyResep(null,false);
                        daftar.isCek();
                        daftar.setRM(TNoRwCari.getText(),TNoRMCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString(),"ralan");
                        daftar.tampil();
                        daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        daftar.setLocationRelativeTo(internalFrame1);
                        daftar.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                        }else{ 
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgCopyResep daftar=new DlgCopyResep(null,false);
                            daftar.isCek();
                            daftar.setRM(TNoRwCari.getText(),TNoRMCari.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString(),"ralan");
                            daftar.tampil();
                            daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            daftar.setLocationRelativeTo(internalFrame1);
                            daftar.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }                      
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbKasirRalan.requestFocus();
            }          
        }
    }//GEN-LAST:event_MnCopyResepActionPerformed

    private void MnCopyResep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyResep2ActionPerformed
        if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgResepObat resep=new DlgResepObat(null,false);
                    resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.emptTeks();
                    resep.isCek();
                    resep.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(0,2),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(3,5),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(6,8),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),"ralan"                            
                    );
                    resep.tampil();
                    resep.setVisible(true);
                    
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgCopyResep daftar=new DlgCopyResep(null,false);
                    daftar.isCek();
                    daftar.setRM(TNoRwCari.getText(),TNoRMCari.getText(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),14).toString(),"ralan");
                    daftar.tampil();
                    daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    daftar.setLocationRelativeTo(internalFrame1);
                    daftar.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }  
            }                          
        }
    }//GEN-LAST:event_MnCopyResep2ActionPerformed

    private void MnPenilaianAwalMedisRalanTHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianAwalMedisRalanTHTActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisRalanTHT form=new RMPenilaianAwalMedisRalanTHT(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianAwalMedisRalanTHTActionPerformed

    private void MnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPsikologActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPsikologi form=new RMPenilaianPsikologi(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPsikologActionPerformed

    private void MnPersetujuanPenolakanTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanPenolakanTindakanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasienCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    SuratPersetujuanPenolakanTindakan resume=new SuratPersetujuanPenolakanTindakan(null,false);
                    resume.isCek();
                    resume.emptTeks();
                    resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resume.setLocationRelativeTo(internalFrame1);
                    resume.setNoRm(TNoRw.getText(),DTPCari1.getDate());
                    resume.tampil();
                    resume.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }   
            }             
        }
    }//GEN-LAST:event_MnPersetujuanPenolakanTindakanActionPerformed

    private void MnPenilaianAwalMedisRalanPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianAwalMedisRalanPsikiatriActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(tbKasirRalan.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisRalanPsikiatrik form=new RMPenilaianAwalMedisRalanPsikiatrik(null,false);
                form.isCek();
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianAwalMedisRalanPsikiatriActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKasirRalan dialog = new DlgKasirRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn1;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar4;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.TextBox CrDokter3;
    private widget.TextBox CrPoli;
    private widget.TextBox CrPtg;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgCatatan;
    private javax.swing.JDialog DlgSakit;
    private javax.swing.JDialog DlgSakit2;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenu MenuInputData1;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcode2;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnBilling1;
    private javax.swing.JMenuItem MnBillingParsial;
    private javax.swing.JMenuItem MnBlangkoResep;
    private javax.swing.JMenu MnBridging;
    private javax.swing.JMenuItem MnBuktiPelayananRalan;
    private javax.swing.JMenuItem MnCatatanObservasiIGD;
    private javax.swing.JMenuItem MnCetakBebasNarkoba;
    private javax.swing.JMenuItem MnCetakSuratBebasTBC;
    private javax.swing.JMenuItem MnCetakSuratCovid;
    private javax.swing.JMenuItem MnCetakSuratCutiHamil;
    private javax.swing.JMenuItem MnCetakSuratHamil;
    private javax.swing.JMenuItem MnCetakSuratSakit;
    private javax.swing.JMenuItem MnCetakSuratSakit1;
    private javax.swing.JMenuItem MnCetakSuratSakitPihak2;
    private javax.swing.JMenuItem MnCetakSuratSehat;
    private javax.swing.JMenuItem MnCetakSuratSehat1;
    private javax.swing.JMenuItem MnCetakSuratSehat2;
    private javax.swing.JMenuItem MnCopyResep;
    private javax.swing.JMenuItem MnCopyResep2;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenuItem MnDataPemberianObat;
    private javax.swing.JMenuItem MnDataPemberianObat1;
    private javax.swing.JMenu MnDataRM;
    private javax.swing.JMenuItem MnDataRalan;
    private javax.swing.JMenuItem MnDataRalan1;
    private javax.swing.JMenuItem MnDataTriaseIGD;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDiagnosa1;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenuItem MnDokter;
    private javax.swing.JMenuItem MnGabungNoRawat;
    private javax.swing.JMenu MnGanti;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnGelang2;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenuItem MnGelang6;
    private javax.swing.JMenuItem MnGelang7;
    private javax.swing.JMenuItem MnHapusAturanPkaiObat;
    private javax.swing.JMenuItem MnHapusBookingOperasi;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusDeposit;
    private javax.swing.JMenuItem MnHapusDiagnosa;
    private javax.swing.JMenuItem MnHapusDiet;
    private javax.swing.JMenuItem MnHapusDpjp;
    private javax.swing.JMenuItem MnHapusHemodialisa;
    private javax.swing.JMenuItem MnHapusKamarInap;
    private javax.swing.JMenuItem MnHapusLab;
    private javax.swing.JMenuItem MnHapusObat;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusPemeriksaanRalan;
    private javax.swing.JMenuItem MnHapusPemeriksaanRanap;
    private javax.swing.JMenuItem MnHapusPiutang;
    private javax.swing.JMenuItem MnHapusPotongan;
    private javax.swing.JMenuItem MnHapusProsedur;
    private javax.swing.JMenuItem MnHapusRadiologi;
    private javax.swing.JMenuItem MnHapusRanapGabung;
    private javax.swing.JMenuItem MnHapusResepObat;
    private javax.swing.JMenuItem MnHapusResepPulang;
    private javax.swing.JMenuItem MnHapusReturObat;
    private javax.swing.JMenuItem MnHapusRujukKeluar;
    private javax.swing.JMenuItem MnHapusRujukMasuk;
    private javax.swing.JMenuItem MnHapusRujukan;
    private javax.swing.JMenuItem MnHapusSemua;
    private javax.swing.JMenuItem MnHapusStokObatRanap;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnHapusTambahan;
    private javax.swing.JMenuItem MnHapusTindakanRalanDokter;
    private javax.swing.JMenuItem MnHapusTindakanRalanDokterParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRalanParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRanapDokter;
    private javax.swing.JMenuItem MnHapusTindakanRanapDokterParamedis;
    private javax.swing.JMenuItem MnHapusTindakanRanapParamedis;
    private javax.swing.JMenuItem MnHemodialisa;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnJadwalOperasi1;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnKamarInap1;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
    private javax.swing.JMenuItem MnLembarRalan;
    private javax.swing.JMenuItem MnMeninggal;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenuItem MnNoResep1;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenu MnObat1;
    private javax.swing.JMenuItem MnObatLangsung;
    private javax.swing.JMenuItem MnObatLangsung1;
    private javax.swing.JMenu MnObatRalan;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnOperasi1;
    private javax.swing.JMenuItem MnPCare;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPemberianObat1;
    private javax.swing.JMenu MnPemeriksaan;
    private javax.swing.JMenuItem MnPenilaianAwalKeperawatanIGD;
    private javax.swing.JMenuItem MnPenilaianAwalMedisIGD;
    private javax.swing.JMenuItem MnPenilaianAwalMedisRalanPsikiatri;
    private javax.swing.JMenuItem MnPenilaianAwalMedisRalanTHT;
    private javax.swing.JMenuItem MnPenilaianFisioterapi;
    private javax.swing.JMenuItem MnPenilaianMCU;
    private javax.swing.JMenuItem MnPenilaianPsikolog;
    private javax.swing.JMenuItem MnPeniliaianAwalKeperawatanBayiAnak;
    private javax.swing.JMenuItem MnPeniliaianAwalKeperawatanGigi;
    private javax.swing.JMenuItem MnPeniliaianAwalKeperawatanKebidanan;
    private javax.swing.JMenuItem MnPeniliaianAwalKeperawatanRalan;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisRalan;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisRalanBayi;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisRalanKebidanan;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPenjualan1;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaLab1;
    private javax.swing.JMenuItem MnPeriksaLabPA;
    private javax.swing.JMenuItem MnPeriksaLabPA2;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPeriksaRadiologi1;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenu MnPermintaan1;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanLab1;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnPermintaanRadiologi1;
    private javax.swing.JMenuItem MnPermintaanRanap;
    private javax.swing.JMenuItem MnPersetujuanMedis;
    private javax.swing.JMenuItem MnPersetujuanMedis1;
    private javax.swing.JMenuItem MnPersetujuanPenolakanTindakan;
    private javax.swing.JMenu MnPilihBilling;
    private javax.swing.JMenuItem MnPiutangObat;
    private javax.swing.JMenuItem MnPoli;
    private javax.swing.JMenuItem MnPoliInternal;
    private javax.swing.JMenuItem MnPulangPaksa;
    private javax.swing.JMenu MnRMIGD;
    private javax.swing.JMenu MnRMRawatJalan;
    private javax.swing.JMenuItem MnRawatJalan1;
    private javax.swing.JMenu MnRekap;
    private javax.swing.JMenuItem MnRekapBulananDokter;
    private javax.swing.JMenuItem MnRekapBulananParamedis;
    private javax.swing.JMenuItem MnRekapHarianDokter;
    private javax.swing.JMenuItem MnRekapHarianObat;
    private javax.swing.JMenuItem MnRekapHarianParamedis;
    private javax.swing.JMenuItem MnRekapHarianPoli;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnResepDOkter1;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSJP;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenuItem MnSuratBebasTato;
    private javax.swing.JMenuItem MnSuratButaWarna;
    private javax.swing.JMenuItem MnSuratKewaspadaanKesehatan;
    private javax.swing.JMenuItem MnSuratKontrol;
    private javax.swing.JMenuItem MnTeridentifikasiTB;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenu MnTindakan1;
    private javax.swing.JMenu MnTindakanRalan;
    private javax.swing.JMenuItem MnUjiFungsiKFR;
    private javax.swing.JMenu MnUrut;
    private javax.swing.JMenu MnUrut1;
    private javax.swing.JMenuItem MnUrutDokterAsc;
    private javax.swing.JMenuItem MnUrutDokterAsc2;
    private javax.swing.JMenuItem MnUrutDokterDesc;
    private javax.swing.JMenuItem MnUrutDokterDesc2;
    private javax.swing.JMenuItem MnUrutNoRawatAsc;
    private javax.swing.JMenuItem MnUrutNoRawatAsc2;
    private javax.swing.JMenuItem MnUrutNoRawatDesc;
    private javax.swing.JMenuItem MnUrutNoRawatDesc2;
    private javax.swing.JMenuItem MnUrutPenjabAsc;
    private javax.swing.JMenuItem MnUrutPenjabAsc2;
    private javax.swing.JMenuItem MnUrutPenjabDesc;
    private javax.swing.JMenuItem MnUrutPenjabDesc2;
    private javax.swing.JMenuItem MnUrutPoliklinikAsc;
    private javax.swing.JMenuItem MnUrutPoliklinikAsc2;
    private javax.swing.JMenuItem MnUrutPoliklinikDesc;
    private javax.swing.JMenuItem MnUrutPoliklinikDesc2;
    private javax.swing.JMenuItem MnUrutRMAsc;
    private javax.swing.JMenuItem MnUrutRMDesc;
    private javax.swing.JMenuItem MnUrutRegAsc1;
    private javax.swing.JMenuItem MnUrutRegDesc1;
    private javax.swing.JMenuItem MnUrutStatusAsc;
    private javax.swing.JMenuItem MnUrutStatusAsc2;
    private javax.swing.JMenuItem MnUrutStatusDesc;
    private javax.swing.JMenuItem MnUrutStatusDesc2;
    private javax.swing.JMenuItem MnUrutTanggalAsc;
    private javax.swing.JMenuItem MnUrutTanggalAsc2;
    private javax.swing.JMenuItem MnUrutTanggalDesc;
    private javax.swing.JMenuItem MnUrutTanggalDesc2;
    private widget.TextBox NomorSurat;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRMCari;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRwCari;
    private widget.TextBox TPasienCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.Tanggal TglSakit1;
    private widget.Tanggal TglSakit2;
    private widget.TextBox TotalObat;
    private javax.swing.JDialog WindowCaraBayar;
    private javax.swing.JDialog WindowGantiDokter;
    private javax.swing.JDialog WindowGantiPoli;
    private javax.swing.JDialog WindowObatBhp;
    private widget.Button btnBayar;
    private widget.Button btnCariDokter;
    private widget.Button btnCariPoli;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbStatus;
    private widget.ComboBox cmbStatusBayar;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel24;
    private widget.Label jLabel3;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel37;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.TextBox lmsakit;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBerkasDIterima;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppBerkasDigital1;
    private javax.swing.JMenuItem ppBerkasRanap;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDataIndukKecelakaan;
    private javax.swing.JMenuItem ppDeteksiDIniCorona;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppIKP1;
    private javax.swing.JMenuItem ppMasukPoli;
    private javax.swing.JMenuItem ppPasienCorona;
    private javax.swing.JMenuItem ppPerawatanCorona;
    private javax.swing.JMenuItem ppProgramPRB;
    private javax.swing.JMenuItem ppResume;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppRiwayat1;
    private javax.swing.JMenuItem ppSuplesiJasaRaharja;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratPRI;
    private javax.swing.JMenuItem ppTampilkanBelumDiagnosa;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.Table tbKasirRalan;
    private widget.Table tbKasirRalan2;
    // End of variables declaration//GEN-END:variables

    private void tampilkasir() {     
        Valid.tabelKosong(tabModekasir);
        try{   
            semua=caripenjab.equals("")&&CrPoli.getText().trim().equals("")&&CrPtg.getText().trim().equals("")&&cmbStatus.getSelectedItem().toString().equals("Semua")&&cmbStatusBayar.getSelectedItem().toString().equals("Semua")&&TCari.getText().trim().equals("");
            pskasir=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts,penjab.png_jawab,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur, "+
                "reg_periksa.status_bayar,reg_periksa.status_poli,reg_periksa.kd_pj,reg_periksa.kd_poli,pasien.no_tlp "+
                "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  "+
                "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut='Ralan' "+tampildiagnosa+
                (semua?"":"and reg_periksa.kd_pj like ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.status_bayar like ? and "+
                "(reg_periksa.no_reg like ? or reg_periksa.no_rawat like ? or reg_periksa.tgl_registrasi like ? or reg_periksa.kd_dokter like ? or dokter.nm_dokter like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ? or "+
                "reg_periksa.p_jawab like ? or penjab.png_jawab like ? or reg_periksa.almt_pj like ? or reg_periksa.status_bayar like ? or reg_periksa.hubunganpj like ?)")+
                "order by "+order);
            try{
                pskasir.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    pskasir.setString(3,"%"+caripenjab+"%");
                    pskasir.setString(4,"%"+CrPoli.getText()+"%");
                    pskasir.setString(5,"%"+CrPtg.getText()+"%");
                    pskasir.setString(6,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    pskasir.setString(7,"%"+cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    pskasir.setString(8,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(9,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(10,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(11,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(12,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(13,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(14,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(15,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(16,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(17,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(18,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(19,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(20,"%"+TCari.getText().trim()+"%");
                }
                
                rskasir=pskasir.executeQuery();
                while(rskasir.next()){
                    tabModekasir.addRow(new String[] {
                        rskasir.getString(5),rskasir.getString(6),rskasir.getString(7),rskasir.getString(8)+" ("+rskasir.getString("umur")+")",
                        rskasir.getString(9),rskasir.getString(10),rskasir.getString(11),rskasir.getString(12),Valid.SetAngka(rskasir.getDouble(13)),
                        rskasir.getString("png_jawab"),rskasir.getString(14),rskasir.getString("no_rawat"),rskasir.getString("tgl_registrasi"),
                        rskasir.getString("jam_reg"),rskasir.getString(1),rskasir.getString("status_bayar"),rskasir.getString("status_poli"),
                        rskasir.getString("kd_pj"),rskasir.getString("kd_poli"),rskasir.getString("no_tlp")
                    });
                }                
            } catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } finally{
               if(rskasir!=null){
                   rskasir.close();
               } 
               if(pskasir!=null){
                   pskasir.close();
               } 
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModekasir.getRowCount());
    }
    
    private void tampilkasir2() {                   
        Valid.tabelKosong(tabModekasir2);
        try{   
            semua=CrPoli.getText().trim().equals("")&&CrPtg.getText().trim().equals("")&&cmbStatus.getSelectedItem().toString().equals("Semua")&&TCari.getText().trim().equals("");
            pskasir=koneksi.prepareStatement("select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "rujukan_internal_poli.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.stts,penjab.png_jawab,rujukan_internal_poli.kd_poli,"+
                "concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.kd_pj,pasien.no_tlp "+
                "from reg_periksa inner join rujukan_internal_poli on rujukan_internal_poli.no_rawat=reg_periksa.no_rawat "+
                "inner join dokter on rujukan_internal_poli.kd_dokter=dokter.kd_dokter inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                "where reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? "+
                (semua?"":"and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and "+
                "(reg_periksa.no_reg like ? or reg_periksa.no_rawat like ? or reg_periksa.tgl_registrasi like ? or rujukan_internal_poli.kd_dokter like ? "+
                "or dokter.nm_dokter like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ? "+
                "or reg_periksa.p_jawab like ? or penjab.png_jawab like ? or reg_periksa.almt_pj like ? or reg_periksa.hubunganpj like ?)")+    
                "order by "+order);
            try{
                pskasir.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!semua){
                    pskasir.setString(3,"%"+CrPoli.getText()+"%");
                    pskasir.setString(4,"%"+CrPtg.getText()+"%");
                    pskasir.setString(5,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    pskasir.setString(6,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(7,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(8,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(9,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(10,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(11,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(12,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(13,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(14,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(15,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(16,"%"+TCari.getText().trim()+"%");
                    pskasir.setString(17,"%"+TCari.getText().trim()+"%");
                }
                rskasir=pskasir.executeQuery();
                while(rskasir.next()){
                    tabModekasir2.addRow(new String[] {
                        rskasir.getString("kd_dokter"),rskasir.getString("nm_dokter"),
                        rskasir.getString("no_rkm_medis"),rskasir.getString("nm_pasien")+" ("+rskasir.getString("umur")+")",
                        rskasir.getString("nm_poli"),rskasir.getString("p_jawab"),rskasir.getString("almt_pj"),rskasir.getString("hubunganpj"),
                        rskasir.getString("png_jawab"),rskasir.getString("stts"),rskasir.getString("no_rawat"),rskasir.getString("tgl_registrasi"),
                        rskasir.getString("jam_reg"),rskasir.getString("kd_poli"),rskasir.getString("kd_pj"),rskasir.getString("no_tlp")
                    });
                }                
            } catch(Exception e){
                System.out.println("Notifikasi : "+e);
            } finally{
               if(rskasir!=null){
                   rskasir.close();
               } 
               if(pskasir!=null){
                   pskasir.close();
               } 
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModekasir2.getRowCount());
    }


    private void getDatakasir() {
        if(tbKasirRalan.getSelectedRow()!= -1){
            TNoRw.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString());
            Tanggal.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString());
            Jam.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),13).toString());
            TNoRwCari.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString());
            TNoReg.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),14).toString());
            TNoRMCari.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
            TPasienCari.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString());
        }
    }

    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        MnRawatJalan1.setEnabled(akses.gettindakan_ralan());
        MnPemberianObat.setEnabled(akses.getberi_obat());
        MnPemberianObat1.setEnabled(akses.getberi_obat());
        MnPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnPeriksaLab1.setEnabled(akses.getperiksa_lab());
        MnPeriksaLabPA.setEnabled(akses.getpemeriksaan_lab_pa());
        MnPeriksaLabPA2.setEnabled(akses.getpemeriksaan_lab_pa());
        MnResepDOkter.setEnabled(akses.getresep_dokter());
        MnResepDOkter1.setEnabled(akses.getresep_dokter());
        MnPeriksaRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnPeriksaRadiologi1.setEnabled(akses.getperiksa_radiologi());
        MnOperasi.setEnabled(akses.getoperasi());
        MnOperasi1.setEnabled(akses.getoperasi());
        MnNoResep.setEnabled(akses.getresep_obat());
        MnNoResep1.setEnabled(akses.getresep_obat());
        MnObatLangsung.setEnabled(akses.getberi_obat());
        MnObatLangsung1.setEnabled(akses.getberi_obat());
        MnBillingParsial.setEnabled(akses.getbilling_parsial());
        MnBilling.setEnabled(akses.getbilling_ralan());
        MnHemodialisa.setEnabled(akses.gethemodialisa());
        MnDataRalan.setEnabled(akses.gettindakan_ralan());
        MnDataRalan1.setEnabled(akses.gettindakan_ralan());
        MnDataPemberianObat.setEnabled(akses.getberi_obat());
        MnDataPemberianObat1.setEnabled(akses.getberi_obat());
        MnDokter.setEnabled(akses.getkasir_ralan());
        MnPenjab.setEnabled(akses.getkasir_ralan());
        MnPoli.setEnabled(akses.getkasir_ralan());
        MnPenjualan.setEnabled(akses.getpenjualan_obat());
        MnPiutangObat.setEnabled(akses.getpiutang_obat());
        MnRekapHarianDokter.setEnabled(akses.getharian_dokter());
        MnRekapHarianParamedis.setEnabled(akses.getharian_paramedis());
        MnRekapBulananDokter.setEnabled(akses.getbulanan_dokter());
        MnRekapBulananParamedis.setEnabled(akses.getbulanan_paramedis());
        MnRekapHarianPoli.setEnabled(akses.getharian_tindakan_poli());
        MnRekapHarianObat.setEnabled(akses.getobat_per_poli());
        MnDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
        MnDiagnosa1.setEnabled(akses.getdiagnosa_pasien());   
        ppRiwayat.setEnabled(akses.getresume_pasien());     
        ppRiwayat1.setEnabled(akses.getresume_pasien());   
        MnRujuk.setEnabled(akses.getrujukan_keluar());
        MnPoliInternal.setEnabled(akses.getrujukan_poli_internal());
        MnHapusRujukan.setEnabled(akses.getrujukan_poli_internal());        
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());        
        ppBerkasDigital1.setEnabled(akses.getberkas_digital_perawatan());      
        ppIKP.setEnabled(akses.getinsiden_keselamatan_pasien());
        ppIKP1.setEnabled(akses.getinsiden_keselamatan_pasien());    
        MnJadwalOperasi.setEnabled(akses.getbooking_operasi());      
        MnSuratKontrol.setEnabled(akses.getskdp_bpjs()); 
        MnPermintaanLab.setEnabled(akses.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(akses.getpermintaan_radiologi());
        MnJadwalOperasi1.setEnabled(akses.getbooking_operasi());   
        MnPermintaanLab1.setEnabled(akses.getpermintaan_lab());
        MnPermintaanRadiologi1.setEnabled(akses.getpermintaan_radiologi());
        MnDataTriaseIGD.setEnabled(akses.getdata_triase_igd());
        MnCetakSuratSakit.setEnabled(akses.getsurat_sakit());
        ppResume.setEnabled(akses.getdata_resume_pasien());
        MnPeniliaianAwalKeperawatanRalan.setEnabled(akses.getpenilaian_awal_keperawatan_ralan());
        MnPeniliaianAwalKeperawatanGigi.setEnabled(akses.getpenilaian_awal_keperawatan_gigi());
        MnPeniliaianAwalKeperawatanKebidanan.setEnabled(akses.getpenilaian_awal_keperawatan_kebidanan());
        MnPeniliaianAwalKeperawatanBayiAnak.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        ppPasienCorona.setEnabled(akses.getpasien_corona());
        ppPerawatanCorona.setEnabled(akses.getpasien_corona());
        ppDeteksiDIniCorona.setEnabled(akses.getdeteksi_corona());
        MnCetakSuratHamil.setEnabled(akses.getsurat_hamil());
        MnCetakSuratCutiHamil.setEnabled(akses.getsurat_cuti_hamil());
        MnCetakBebasNarkoba.setEnabled(akses.getsurat_bebas_narkoba());
        MnPCare.setEnabled(akses.getbridging_pcare_daftar()); 
        MnSEP.setEnabled(akses.getbpjs_sep());  
        MnSJP.setEnabled(akses.getinhealth_sjp());  
        MnCetakSuratCovid.setEnabled(akses.getsurat_keterangan_covid());
        MnRujukSisrute.setEnabled(akses.getsisrute_rujukan_keluar());
        MnTeridentifikasiTB.setEnabled(akses.getkemenkes_sitt());
        ppCatatanPasien.setEnabled(akses.getcatatan_pasien());
        MnBilling1.setEnabled(akses.getbilling_ralan());
        ppSuratKontrol.setEnabled(akses.getbpjs_surat_kontrol());    
        ppSuratPRI.setEnabled(akses.getbpjs_surat_pri());  
        MnCetakSuratSehat2.setEnabled(akses.getsurat_keterangan_sehat());   
        MnCetakSuratSakitPihak2.setEnabled(akses.getsurat_sakit_pihak_2()); 
        MnCetakSuratBebasTBC.setEnabled(akses.getsurat_bebas_tbc());     
        MnSuratButaWarna.setEnabled(akses.getsurat_buta_warna());      
        MnSuratBebasTato.setEnabled(akses.getsurat_bebas_tato());  
        MnSuratKewaspadaanKesehatan.setEnabled(akses.getsurat_kewaspadaan_kesehatan());     
        MnPeniliaianAwalMedisRalan.setEnabled(akses.getpenilaian_awal_medis_ralan());       
        MnPeniliaianAwalMedisRalanKebidanan.setEnabled(akses.getpenilaian_awal_medis_ralan_kebidanan());      
        MnPenilaianAwalMedisIGD.setEnabled(akses.getpenilaian_awal_medis_igd());    
        MnPeniliaianAwalMedisRalanBayi.setEnabled(akses.getpenilaian_awal_medis_ralan_anak()); 
        MnPenilaianFisioterapi.setEnabled(akses.getpenilaian_fisioterapi());            
        ppProgramPRB.setEnabled(akses.getbpjs_program_prb());      
        ppSuplesiJasaRaharja.setEnabled(akses.getbpjs_suplesi_jasaraharja());  
        ppDataIndukKecelakaan.setEnabled(akses.getbpjs_data_induk_kecelakaan());    
        MnPenilaianMCU.setEnabled(akses.getpenilaian_mcu());             
        MnUjiFungsiKFR.setEnabled(akses.getuji_fungsi_kfr());    
        MnPenilaianAwalKeperawatanIGD.setEnabled(akses.getpenilaian_awal_keperawatan_igd());    
        MnGabungNoRawat.setEnabled(akses.getgabung_norawat());
        MnCatatanObservasiIGD.setEnabled(akses.getcatatan_observasi_igd());
        MnPenilaianAwalMedisRalanTHT.setEnabled(akses.getpenilaian_awal_medis_ralan_tht());
        MnPenilaianAwalMedisRalanPsikiatri.setEnabled(akses.getpenilaian_awal_medis_ralan_psikiatri());
        MnCopyResep.setEnabled(akses.getresep_dokter());
        MnCopyResep2.setEnabled(akses.getresep_dokter());
        MnPenilaianPsikolog.setEnabled(akses.getpenilaian_psikologi());
        MnPersetujuanPenolakanTindakan.setEnabled(akses.getpersetujuan_penolakan_tindakan());
        
        if(akses.getkode().equals("Admin Utama")){
            MnHapusData.setEnabled(true);
        }else{
            MnHapusData.setEnabled(false);
        } 
        
        if(akses.getkode().equals("Admin Utama")){
            MnKamarInap.setEnabled(true);
            MnKamarInap1.setEnabled(true); 
        }else{
            if(kamar_inap_kasir_ralan.equals("Yes")){
                MnKamarInap.setEnabled(akses.getkamar_inap());
                MnKamarInap1.setEnabled(akses.getkamar_inap());
            }else{
                MnKamarInap.setEnabled(false);
                MnKamarInap1.setEnabled(false);
            }
        }
        
        try {
            namadokter=koneksiDB.DOKTERAKTIFKASIRRALAN();
        } catch (Exception e) {
            namadokter="";
        }   
        
        if(!namadokter.equals("")){
            if(akses.getkode().equals("Admin Utama")){
                CrPtg.setText("");
                BtnSeek3.setEnabled(true);
                CrPtg.setEditable(true);
            }else{
                CrPtg.setText(namadokter);
                BtnSeek3.setEnabled(false);
                CrPtg.setEditable(false);
            }                
        }else{
            namadokter=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",akses.getkode());
            if(!namadokter.equals("")){
                CrPtg.setText(namadokter);
                BtnSeek3.setEnabled(false);
                CrPtg.setEditable(false);
            }else{
                CrPtg.setText("");
                BtnSeek3.setEnabled(true);
                CrPtg.setEditable(true);
            }
        }
        
        if(!namapoli.equals("")){
            if(akses.getkode().equals("Admin Utama")){
                CrPoli.setText("");
                BtnSeek4.setEnabled(true);
                CrPoli.setEditable(true);
            }else{
                CrPoli.setText(namapoli);
                BtnSeek4.setEnabled(false);
                CrPoli.setEditable(false);
            }                
        }else{
            BtnSeek4.setEnabled(true);
            CrPoli.setEditable(true);
        }
        
    }

    private void otomatisRalan() {
        if(Sequel.cariRegistrasi(TNoRw.getText())==0){
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                psotomatis=koneksi.prepareStatement(sqlpsotomatis);
                try {
                    psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                    psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                    rskasir=psotomatis.executeQuery();
                    while(rskasir.next()){    
                        if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                           "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                           "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                            try {
                                psotomatis2.setString(1,TNoRw.getText()); 
                                psotomatis2.setString(2,rskasir.getString(1));
                                psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                                psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                psotomatis2.executeUpdate();
                            } catch (Exception e) {
                                sukses=false;
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
                            } 
                            if(sukses==true){
                                ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                                ttlkso=ttlkso+rskasir.getDouble("kso");
                                ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdr");
                                ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                            }                
                        }
                    }   
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally {
                    if(rskasir!=null){
                        rskasir.close();
                    }
                    if(psotomatis!=null){
                        psotomatis.close();
                    }
                }    

                if(!akses.getkode().equals("Admin Utama")){
                    psotomatis=koneksi.prepareStatement(sqlpsotomatispetugas);
                    try {                
                        psotomatis.setString(1,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                        rskasir=psotomatis.executeQuery();
                        while(rskasir.next()){    
                            if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_pr where "+
                               "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                               "and nip='"+akses.getkode()+"'")==0){
                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2petugas);
                                try {
                                    psotomatis2.setString(1,TNoRw.getText()); 
                                    psotomatis2.setString(2,rskasir.getString(1));
                                    psotomatis2.setString(3,akses.getkode());
                                    psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                    psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                    psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                    psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                    psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakanpr"));
                                    psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                    psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                    psotomatis2.setDouble(11,rskasir.getDouble("total_byrpr"));
                                    psotomatis2.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
                                }
                                if(sukses==true){
                                    ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                    ttlkso=ttlkso+rskasir.getDouble("kso");
                                    ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrpr");
                                    ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                    ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                    ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                                } 
                            }
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally {
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(psotomatis!=null){
                            psotomatis.close();
                        }
                    } 
                }

                if(!akses.getkode().equals("Admin Utama")){
                    psotomatis=koneksi.prepareStatement(sqlpsotomatisdokterpetugas);
                    try {
                        psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                        psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                        rskasir=psotomatis.executeQuery();
                        while(rskasir.next()){    
                            if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_drpr where "+
                               "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                               "and kd_dokter='"+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString()+"'")==0){
                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2dokterpetugas);
                                try {
                                    psotomatis2.setString(1,TNoRw.getText()); 
                                    psotomatis2.setString(2,rskasir.getString(1));
                                    psotomatis2.setString(3,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                                    psotomatis2.setString(4,akses.getkode());
                                    psotomatis2.setString(5,Sequel.cariIsi("select current_date()"));
                                    psotomatis2.setString(6,Sequel.cariIsi("select current_time()"));
                                    psotomatis2.setDouble(7,rskasir.getDouble("material"));
                                    psotomatis2.setDouble(8,rskasir.getDouble("bhp"));
                                    psotomatis2.setDouble(9,rskasir.getDouble("tarif_tindakandr"));
                                    psotomatis2.setDouble(10,rskasir.getDouble("tarif_tindakanpr"));
                                    psotomatis2.setDouble(11,rskasir.getDouble("kso"));
                                    psotomatis2.setDouble(12,rskasir.getDouble("menejemen"));
                                    psotomatis2.setDouble(13,rskasir.getDouble("total_byrdrpr"));
                                    psotomatis2.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
                                } 
                                if(sukses==true){
                                    ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                                    ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                    ttlkso=ttlkso+rskasir.getDouble("kso");
                                    ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdrpr");
                                    ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                    ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                    ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                                } 
                            }
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally {
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(psotomatis!=null){
                            psotomatis.close();
                        }
                    } 
                }
                
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");    
                    if(ttlpendapatan>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                    }
                    if(ttljmdokter>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                    }
                    if(ttljmperawat>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                             
                    }
                    if(ttlkso>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                    }
                    if(ttljasasarana>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                    }
                    if(ttlbhp>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                    }
                    if(ttlmenejemen>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                    }
                    sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRw.getText()+" DIPOSTING OLEH "+akses.getkode());     
                }
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    System.out.println("Terjadi kesalahan saat pemrosesan data, transaksi tindakan otomatis dibatalkan!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
        }            
    }
    
    private void otomatisRalan2() {
        try {
            Sequel.AutoComitFalse();
            sukses=true;
            ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
            psotomatis=koneksi.prepareStatement(sqlpsotomatis);
            try {
                psotomatis.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString());
                psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                rskasir=psotomatis.executeQuery();
                while(rskasir.next()){    
                    if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_dr where "+
                       "no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                       "and kd_dokter='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString()+"'")==0){
                        psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                        try {
                            psotomatis2.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()); 
                            psotomatis2.setString(2,rskasir.getString(1));
                            psotomatis2.setString(3,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString());
                            psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                            psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                            psotomatis2.setDouble(6,rskasir.getDouble("material"));
                            psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                            psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                            psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                            psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                            psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                            psotomatis2.executeUpdate();
                        } catch (Exception e) {
                            sukses=false;
                            System.out.println("proses input data "+e);
                        } finally{
                            if(psotomatis2!=null){
                                psotomatis2.close();
                            }
                        } 
                        if(sukses==true){
                            ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                            ttlkso=ttlkso+rskasir.getDouble("kso");
                            ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdr");
                            ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                            ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                            ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                        }  
                    }
                }   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally {
                if(rskasir!=null){
                    rskasir.close();
                }
                if(psotomatis!=null){
                    psotomatis.close();
                }
            }    
            
            if(!akses.getkode().equals("Admin Utama")){
                psotomatis=koneksi.prepareStatement(sqlpsotomatispetugas);
                try {                
                    psotomatis.setString(1,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                    rskasir=psotomatis.executeQuery();
                    while(rskasir.next()){    
                        if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_pr where "+
                           "no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                           "and nip='"+akses.getkode()+"'")==0){
                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2petugas);
                            try {
                                psotomatis2.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()); 
                                psotomatis2.setString(2,rskasir.getString(1));
                                psotomatis2.setString(3,akses.getkode());
                                psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakanpr"));
                                psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                psotomatis2.setDouble(11,rskasir.getDouble("total_byrpr"));
                                psotomatis2.executeUpdate();
                            } catch (Exception e) {
                                sukses=false;
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
                            }      
                            if(sukses==true){
                                ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                ttlkso=ttlkso+rskasir.getDouble("kso");
                                ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrpr");
                                ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                            } 
                        }
                    }   
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally {
                    if(rskasir!=null){
                        rskasir.close();
                    }
                    if(psotomatis!=null){
                        psotomatis.close();
                    }
                } 
            }
            
            if(!akses.getkode().equals("Admin Utama")){
                psotomatis=koneksi.prepareStatement(sqlpsotomatisdokterpetugas);
                try {
                    psotomatis.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString());
                    psotomatis.setString(2,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                    rskasir=psotomatis.executeQuery();
                    while(rskasir.next()){    
                        if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_drpr where "+
                           "no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                           "and kd_dokter='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString()+"'")==0){
                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2dokterpetugas);
                            try {
                                psotomatis2.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()); 
                                psotomatis2.setString(2,rskasir.getString(1));
                                psotomatis2.setString(3,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString());
                                psotomatis2.setString(4,akses.getkode());
                                psotomatis2.setString(5,Sequel.cariIsi("select current_date()"));
                                psotomatis2.setString(6,Sequel.cariIsi("select current_time()"));
                                psotomatis2.setDouble(7,rskasir.getDouble("material"));
                                psotomatis2.setDouble(8,rskasir.getDouble("bhp"));
                                psotomatis2.setDouble(9,rskasir.getDouble("tarif_tindakandr"));
                                psotomatis2.setDouble(10,rskasir.getDouble("tarif_tindakanpr"));
                                psotomatis2.setDouble(11,rskasir.getDouble("kso"));
                                psotomatis2.setDouble(12,rskasir.getDouble("menejemen"));
                                psotomatis2.setDouble(13,rskasir.getDouble("total_byrdrpr"));
                                psotomatis2.executeUpdate();
                            } catch (Exception e) {
                                sukses=false;
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
                            }    
                            if(sukses==true){
                                ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                                ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                ttlkso=ttlkso+rskasir.getDouble("kso");
                                ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdrpr");
                                ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                            }
                        }
                    }   
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally {
                    if(rskasir!=null){
                        rskasir.close();
                    }
                    if(psotomatis!=null){
                        psotomatis.close();
                    }
                } 
            }
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");    
                if(ttlpendapatan>0){
                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                }
                if(ttljmdokter>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                }
                if(ttljmperawat>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                             
                }
                if(ttlkso>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                }
                if(ttljasasarana>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                }
                if(ttlbhp>0){
                    Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                }
                if(ttlmenejemen>0){
                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                }
                sukses=jur.simpanJurnal(TNoRw.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+TNoRw.getText()+" DIPOSTING OLEH "+akses.getkode());     
            }

            if(sukses==true){
                Sequel.Commit();
            }else{
                System.out.println("Terjadi kesalahan saat pemrosesan data, transaksi tindakan otomatis dibatalkan!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDatakasir2() {
        if(tbKasirRalan2.getSelectedRow()!= -1){
            TNoRwCari.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
            TNoReg.setText("-");
            TNoRMCari.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString());
            TPasienCari.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString());
        }
    }
    
    public void setCariKosong() {
        TCari.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampilkasir();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilkasir2();
        }
    }

    private void panggilformobat() {
        if(tbKasirRalan.getSelectedRow()!= -1){
            TKdPny.setText("-");
            DlgCariObat dlgobt=new DlgCariObat(null,false);
            dlgobt.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),
                                tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),
                                Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"'"),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
            dlgobt.isCek();
            dlgobt.setDokter("","");
            dlgobt.tampilobat();
            dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgobt.setLocationRelativeTo(internalFrame1);
            dlgobt.setVisible(true);
        }            
    }

    private void billingparsial() {
        if(tbKasirRalan.getSelectedRow()!= -1){
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),17).toString());
            }
            if(jmlparsial>0){
                DlgBilingParsialRalan parsialralan=new DlgBilingParsialRalan(null,false);
                parsialralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                parsialralan.setLocationRelativeTo(internalFrame1);
                //parsialralan.emptTeks();
                parsialralan.isCek();
                parsialralan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText()));   
                parsialralan.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Cara bayar "+tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),9).toString()+" tidak diijinkan menggunakan Billing Parsial...!!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        } 
    }
    
}

package simrskhanza;
import permintaan.DlgBookingOperasi;
import bridging.DlgSKDPBPJS;
import inventory.DlgResepObat;
import inventory.DlgPemberianObat;
import laporan.DlgDiagnosaPenyakit;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPenjualan;
import inventory.DlgPeresepanDokter;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingParsialRalan;
import keuangan.DlgLhtPiutang;
import keuangan.DlgRBObatPoli;
import keuangan.DlgRBJmDokter;
import keuangan.DlgRBJmParamedis;
import keuangan.DlgRBTindakanPoli;
import keuangan.DlgRHJmDokter;
import keuangan.DlgRHJmParamedis;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;

/**
 *
 * @author dosen
 */
public final class DlgKasirRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModekasir,tabModekasir2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psotomatis,psotomatis2,pskasir,pscaripiutang;
    private ResultSet rskasir;
    private final Properties prop = new Properties();
    private Date cal=new Date();
    private DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
    private String kamar_inap_kasir_ralan=Sequel.cariIsi("select kamar_inap_kasir_ralan from set_jam_minimal"),caripenjab="",filter="no",bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1"),nonota="",
            sqlpsotomatis2="insert into rawat_jl_dr values (?,?,?,?,?,?,?,?,?,?,?)",
            sqlpsotomatis2petugas="insert into rawat_jl_pr values (?,?,?,?,?,?,?,?,?,?,?)",
            sqlpsotomatis2dokterpetugas="insert into rawat_jl_drpr values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
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
            validasicatatan=Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan");
    public DlgBilingRalan billing=new DlgBilingRalan(null,false);
    private int i=0,pilihan=0,sudah=0;
    public DlgKamarInap kamarinap=new DlgKamarInap(null,false);
    private DlgBilingParsialRalan parsialralan=new DlgBilingParsialRalan(null,false);

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgKasirRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        tabModekasir=new DefaultTableModel(null,new String[]{
            "Kd.Dokter","Dokter Dituju","Nomer RM","Pasien",
            "Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan P.J.",
            "Biaya Regristrasi","Jenis Bayar","Status","No.Rawat","Tanggal",
            "Jam","No.Reg","Status Bayar","Stts Poli"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan.setModel(tabModekasir);

        tbKasirRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbKasirRalan.getColumnModel().getColumn(i);
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
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(105);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(47);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(60);
            }
        }
        tbKasirRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModekasir2=new DefaultTableModel(null,new String[]{
            "Kd.Dokter","Dokter Rujukan","Nomer RM","Pasien",
            "Poliklinik Rujukan","Penanggung Jawab","Alamat P.J.","Hubungan P.J.",
            "Jenis Bayar","Status","No.Rawat","Tanggal","Jam","Kode Poli"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan2.setModel(tabModekasir2);

        tbKasirRalan2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
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
            }
        }
        tbKasirRalan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte)100).getKata(CrPoli));
        TotalObat.setDocument(new batasInput((byte)20).getOnlyAngka(TotalObat));
        CrPtg.setDocument(new batasInput((byte)100).getKata(CrPtg)); 
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {TabRawatMouseClicked(null);}
                @Override
                public void removeUpdate(DocumentEvent e) {TabRawatMouseClicked(null);}
                @Override
                public void changedUpdate(DocumentEvent e) {TabRawatMouseClicked(null);}
            });
        }         
         
        billing.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgKasirRalan")){
                    if(billing.dokter.getTable().getSelectedRow()!= -1){
                        if(pilihan==1){
                            kddokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            kddokter.requestFocus();
                        }else if(pilihan==2){
                            CrPtg.setText(billing.dokter.getTable().getValueAt(billing.dokter.getTable().getSelectedRow(),1).toString());
                            TabRawatMouseClicked(null);
                            CrPtg.requestFocus();
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
                if(var.getform().equals("DlgKasirRalan")){
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
                tampilkasir();
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
                if(var.getform().equals("DlgKasirRalan")){
                    if(billing.penjab.getTable().getSelectedRow()!= -1){
                        if(filter=="no"){
                            kdpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),1).toString());
                            nmpenjab.setText(billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),2).toString());
                        }else if(filter=="yes"){
                            caripenjab=billing.penjab.getTable().getValueAt(billing.penjab.getTable().getSelectedRow(),1).toString();
                            TabRawatMouseClicked(null);
                        }                            
                    } 
                    kdpenjab.requestFocus();
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
                if(var.getform().equals("DlgKasirRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        billing.penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        DlgCatatan.setSize(595,34); 
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            namadokter=prop.getProperty("DOKTERAKTIFKASIRRALAN");
            namapoli=prop.getProperty("POLIAKTIFKASIRRALAN");
            
            try{    
                if(prop.getProperty("MENUTRANSPARAN").equals("yes")){
                    com.sun.awt.AWTUtilities.setWindowOpacity(DlgCatatan,0.5f);
                }     
            }catch(Exception e){    
            }
        } catch (Exception ex) {
            namadokter="";
            namapoli="";
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
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnSKDPBPJS = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnTindakanRalan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnDataRalan = new javax.swing.JMenuItem();
        MnObatRalan = new javax.swing.JMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnObatLangsung = new javax.swing.JMenuItem();
        MnDataPemberianObat = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnPilihBilling = new javax.swing.JMenu();
        MnBillingParsial = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnGanti = new javax.swing.JMenu();
        MnPoli = new javax.swing.JMenuItem();
        MnDokter = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnPoliInternal = new javax.swing.JMenuItem();
        MnRekap = new javax.swing.JMenu();
        MnRekapHarianDokter = new javax.swing.JMenuItem();
        MnRekapHarianParamedis = new javax.swing.JMenuItem();
        MnRekapBulananDokter = new javax.swing.JMenuItem();
        MnRekapBulananParamedis = new javax.swing.JMenuItem();
        MnRekapHarianPoli = new javax.swing.JMenuItem();
        MnRekapHarianObat = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkas = new javax.swing.JMenuItem();
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
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusBilling = new javax.swing.JMenuItem();
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
        jMenu6 = new javax.swing.JMenu();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnGelang1 = new javax.swing.JMenuItem();
        MnGelang2 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang6 = new javax.swing.JMenuItem();
        MnGelang7 = new javax.swing.JMenuItem();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
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
        MnKamarInap1 = new javax.swing.JMenuItem();
        MnHapusRujukan = new javax.swing.JMenuItem();
        MnTindakan1 = new javax.swing.JMenu();
        MnRawatJalan1 = new javax.swing.JMenuItem();
        MnPeriksaLab1 = new javax.swing.JMenuItem();
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
        MnBilling1 = new javax.swing.JMenuItem();
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
        MenuInputData1 = new javax.swing.JMenu();
        ppBerkasDigital1 = new javax.swing.JMenuItem();
        ppIKP1 = new javax.swing.JMenuItem();
        ppRiwayat1 = new javax.swing.JMenuItem();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
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
        TabRawat = new javax.swing.JTabbedPane();
        Scroll1 = new widget.ScrollPane();
        tbKasirRalan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbKasirRalan2 = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel4 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoReg = new widget.TextBox();
        jLabel7 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TPasien = new widget.TextBox();

        jPopupMenu1.setForeground(new java.awt.Color(130, 100, 100));
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPermintaan.setBackground(new java.awt.Color(252, 255, 250));
        MnPermintaan.setForeground(new java.awt.Color(130, 100, 100));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setIconTextGap(5);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setOpaque(true);
        MnPermintaan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(130, 100, 100));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setIconTextGap(5);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(150, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnSKDPBPJS.setBackground(new java.awt.Color(255, 255, 255));
        MnSKDPBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKDPBPJS.setForeground(new java.awt.Color(130, 100, 100));
        MnSKDPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSKDPBPJS.setText("SKDP BPJS");
        MnSKDPBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSKDPBPJS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSKDPBPJS.setIconTextGap(5);
        MnSKDPBPJS.setName("MnSKDPBPJS"); // NOI18N
        MnSKDPBPJS.setPreferredSize(new java.awt.Dimension(150, 26));
        MnSKDPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKDPBPJSActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnSKDPBPJS);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(130, 100, 100));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Pemeriksaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setIconTextGap(5);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(130, 100, 100));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setIconTextGap(5);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        jPopupMenu1.add(MnPermintaan);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(130, 100, 100));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setIconTextGap(5);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnTindakanRalan.setBackground(new java.awt.Color(252, 255, 250));
        MnTindakanRalan.setForeground(new java.awt.Color(130, 100, 100));
        MnTindakanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakanRalan.setText("Tindakan & Pemeriksaan");
        MnTindakanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakanRalan.setIconTextGap(5);
        MnTindakanRalan.setName("MnTindakanRalan"); // NOI18N
        MnTindakanRalan.setOpaque(true);
        MnTindakanRalan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(130, 100, 100));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setIconTextGap(5);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnRawatJalan);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(130, 100, 100));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setIconTextGap(5);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnPeriksaLab);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(130, 100, 100));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setIconTextGap(5);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnPeriksaRadiologi);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(130, 100, 100));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setIconTextGap(5);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnOperasi);

        MnDataRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnDataRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRalan.setForeground(new java.awt.Color(130, 100, 100));
        MnDataRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRalan.setText("Data Tindakan Rawat Jalan");
        MnDataRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRalan.setIconTextGap(5);
        MnDataRalan.setName("MnDataRalan"); // NOI18N
        MnDataRalan.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataRalanActionPerformed(evt);
            }
        });
        MnTindakanRalan.add(MnDataRalan);

        jPopupMenu1.add(MnTindakanRalan);

        MnObatRalan.setBackground(new java.awt.Color(252, 255, 250));
        MnObatRalan.setForeground(new java.awt.Color(130, 100, 100));
        MnObatRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatRalan.setText("Obat");
        MnObatRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatRalan.setIconTextGap(5);
        MnObatRalan.setName("MnObatRalan"); // NOI18N
        MnObatRalan.setOpaque(true);
        MnObatRalan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(130, 100, 100));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setIconTextGap(5);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnPemberianObat);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 255));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(130, 100, 100));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setIconTextGap(5);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(190, 26));
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnNoResep);

        MnResepDOkter.setBackground(new java.awt.Color(255, 255, 255));
        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(130, 100, 100));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setIconTextGap(5);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(190, 26));
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnResepDOkter);

        MnObatLangsung.setBackground(new java.awt.Color(255, 255, 255));
        MnObatLangsung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung.setForeground(new java.awt.Color(130, 100, 100));
        MnObatLangsung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung.setText("Total Tagihan Obat");
        MnObatLangsung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung.setIconTextGap(5);
        MnObatLangsung.setName("MnObatLangsung"); // NOI18N
        MnObatLangsung.setPreferredSize(new java.awt.Dimension(190, 26));
        MnObatLangsung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsungActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnObatLangsung);

        MnDataPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnDataPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataPemberianObat.setForeground(new java.awt.Color(130, 100, 100));
        MnDataPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataPemberianObat.setText("Data Pemberian Obat");
        MnDataPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataPemberianObat.setIconTextGap(5);
        MnDataPemberianObat.setName("MnDataPemberianObat"); // NOI18N
        MnDataPemberianObat.setPreferredSize(new java.awt.Dimension(190, 26));
        MnDataPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataPemberianObatActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnDataPemberianObat);

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(new java.awt.Color(130, 100, 100));
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan.setIconTextGap(5);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        MnObatRalan.add(MnPenjualan);

        jPopupMenu1.add(MnObatRalan);

        MnPilihBilling.setBackground(new java.awt.Color(252, 255, 250));
        MnPilihBilling.setForeground(new java.awt.Color(130, 100, 100));
        MnPilihBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPilihBilling.setText("Billing/Pembayaran Pasien");
        MnPilihBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihBilling.setIconTextGap(5);
        MnPilihBilling.setName("MnPilihBilling"); // NOI18N
        MnPilihBilling.setOpaque(true);
        MnPilihBilling.setPreferredSize(new java.awt.Dimension(200, 26));

        MnBillingParsial.setBackground(new java.awt.Color(255, 255, 255));
        MnBillingParsial.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBillingParsial.setForeground(new java.awt.Color(130, 100, 100));
        MnBillingParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBillingParsial.setText("Billing Parsial");
        MnBillingParsial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBillingParsial.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBillingParsial.setIconTextGap(5);
        MnBillingParsial.setName("MnBillingParsial"); // NOI18N
        MnBillingParsial.setPreferredSize(new java.awt.Dimension(160, 26));
        MnBillingParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingParsialActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBillingParsial);

        MnBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(130, 100, 100));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing Total");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setIconTextGap(5);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(160, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBilling);

        jPopupMenu1.add(MnPilihBilling);

        MnGanti.setBackground(new java.awt.Color(252, 255, 250));
        MnGanti.setForeground(new java.awt.Color(130, 100, 100));
        MnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGanti.setText("Ganti");
        MnGanti.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGanti.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGanti.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGanti.setIconTextGap(5);
        MnGanti.setName("MnGanti"); // NOI18N
        MnGanti.setOpaque(true);
        MnGanti.setPreferredSize(new java.awt.Dimension(200, 26));

        MnPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoli.setForeground(new java.awt.Color(130, 100, 100));
        MnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoli.setText("Poliklinik");
        MnPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoli.setIconTextGap(5);
        MnPoli.setName("MnPoli"); // NOI18N
        MnPoli.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliActionPerformed(evt);
            }
        });
        MnGanti.add(MnPoli);

        MnDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokter.setForeground(new java.awt.Color(130, 100, 100));
        MnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokter.setText("Dokter Poli");
        MnDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDokter.setIconTextGap(5);
        MnDokter.setName("MnDokter"); // NOI18N
        MnDokter.setPreferredSize(new java.awt.Dimension(150, 26));
        MnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokterActionPerformed(evt);
            }
        });
        MnGanti.add(MnDokter);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(130, 100, 100));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setIconTextGap(5);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        MnGanti.add(MnPenjab);

        jPopupMenu1.add(MnGanti);

        MnRujukan.setBackground(new java.awt.Color(252, 255, 250));
        MnRujukan.setForeground(new java.awt.Color(130, 100, 100));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setIconTextGap(5);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setOpaque(true);
        MnRujukan.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(130, 100, 100));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setIconTextGap(5);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnPoliInternal.setBackground(new java.awt.Color(255, 255, 255));
        MnPoliInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoliInternal.setForeground(new java.awt.Color(130, 100, 100));
        MnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoliInternal.setText("Poli Internal");
        MnPoliInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoliInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoliInternal.setIconTextGap(5);
        MnPoliInternal.setName("MnPoliInternal"); // NOI18N
        MnPoliInternal.setPreferredSize(new java.awt.Dimension(150, 26));
        MnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliInternalActionPerformed(evt);
            }
        });
        MnRujukan.add(MnPoliInternal);

        jPopupMenu1.add(MnRujukan);

        MnRekap.setBackground(new java.awt.Color(252, 255, 250));
        MnRekap.setForeground(new java.awt.Color(130, 100, 100));
        MnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekap.setText("Rekap Data");
        MnRekap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekap.setIconTextGap(5);
        MnRekap.setName("MnRekap"); // NOI18N
        MnRekap.setOpaque(true);
        MnRekap.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRekapHarianDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianDokter.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapHarianDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianDokter.setText("Rekap Harian Dokter ");
        MnRekapHarianDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianDokter.setIconTextGap(5);
        MnRekapHarianDokter.setName("MnRekapHarianDokter"); // NOI18N
        MnRekapHarianDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianDokter);

        MnRekapHarianParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapHarianParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianParamedis.setText("Rekap Harian Paramedis");
        MnRekapHarianParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianParamedis.setIconTextGap(5);
        MnRekapHarianParamedis.setName("MnRekapHarianParamedis"); // NOI18N
        MnRekapHarianParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianParamedis);

        MnRekapBulananDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapBulananDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananDokter.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapBulananDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananDokter.setIconTextGap(5);
        MnRekapBulananDokter.setLabel("Rekap Bulanan Dokter ");
        MnRekapBulananDokter.setName("MnRekapBulananDokter"); // NOI18N
        MnRekapBulananDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananDokterActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananDokter);

        MnRekapBulananParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapBulananParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapBulananParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapBulananParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapBulananParamedis.setText("Rekap Bulanan Paramedis");
        MnRekapBulananParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapBulananParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapBulananParamedis.setIconTextGap(5);
        MnRekapBulananParamedis.setName("MnRekapBulananParamedis"); // NOI18N
        MnRekapBulananParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapBulananParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapBulananParamedisActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapBulananParamedis);

        MnRekapHarianPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianPoli.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapHarianPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianPoli.setText("Rekap Harian Poli");
        MnRekapHarianPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianPoli.setIconTextGap(5);
        MnRekapHarianPoli.setName("MnRekapHarianPoli"); // NOI18N
        MnRekapHarianPoli.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianPoliActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianPoli);

        MnRekapHarianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianObat.setForeground(new java.awt.Color(130, 100, 100));
        MnRekapHarianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianObat.setText("Rekap Harian Obat");
        MnRekapHarianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapHarianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapHarianObat.setIconTextGap(5);
        MnRekapHarianObat.setName("MnRekapHarianObat"); // NOI18N
        MnRekapHarianObat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRekapHarianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianObatActionPerformed(evt);
            }
        });
        MnRekap.add(MnRekapHarianObat);

        jPopupMenu1.add(MnRekap);

        MnStatus.setBackground(new java.awt.Color(252, 255, 250));
        MnStatus.setForeground(new java.awt.Color(130, 100, 100));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setIconTextGap(5);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setOpaque(true);
        MnStatus.setPreferredSize(new java.awt.Dimension(200, 26));

        ppBerkas.setBackground(new java.awt.Color(255, 255, 255));
        ppBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkas.setForeground(new java.awt.Color(130, 100, 100));
        ppBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkas.setText("Berkas R.M. Diterima");
        ppBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkas.setIconTextGap(5);
        ppBerkas.setName("ppBerkas"); // NOI18N
        ppBerkas.setPreferredSize(new java.awt.Dimension(180, 26));
        ppBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkas);

        MnSudah.setBackground(new java.awt.Color(255, 255, 255));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(130, 100, 100));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setIconTextGap(5);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 255));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(130, 100, 100));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setIconTextGap(5);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBatal.setBackground(new java.awt.Color(255, 255, 255));
        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(130, 100, 100));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setIconTextGap(5);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(130, 100, 100));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setIconTextGap(5);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setBackground(new java.awt.Color(255, 255, 255));
        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(130, 100, 100));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setIconTextGap(5);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setBackground(new java.awt.Color(255, 255, 255));
        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(130, 100, 100));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setIconTextGap(5);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        MnPulangPaksa.setBackground(new java.awt.Color(255, 255, 255));
        MnPulangPaksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangPaksa.setForeground(new java.awt.Color(130, 100, 100));
        MnPulangPaksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPulangPaksa.setText("Pulang Paksa");
        MnPulangPaksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangPaksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangPaksa.setIconTextGap(5);
        MnPulangPaksa.setName("MnPulangPaksa"); // NOI18N
        MnPulangPaksa.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPulangPaksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangPaksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(MnPulangPaksa);

        jMenu7.setBackground(new java.awt.Color(255, 255, 255));
        jMenu7.setForeground(new java.awt.Color(130, 100, 100));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu7.setText("Status Poli");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu7.setIconTextGap(5);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setOpaque(true);
        jMenu7.setPreferredSize(new java.awt.Dimension(180, 26));

        MnStatusBaru.setBackground(new java.awt.Color(255, 255, 255));
        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(130, 100, 100));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Poli Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setIconTextGap(5);
        MnStatusBaru.setName("MnStatusBaru"); // NOI18N
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusBaru);

        MnStatusLama.setBackground(new java.awt.Color(255, 255, 255));
        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(130, 100, 100));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusLama.setText("Status Poli Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setIconTextGap(5);
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

        MnHapusData.setBackground(new java.awt.Color(252, 255, 250));
        MnHapusData.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setIconTextGap(5);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setOpaque(true);
        MnHapusData.setPreferredSize(new java.awt.Dimension(200, 26));

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setIconTextGap(5);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setIconTextGap(5);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        MnHapusBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusBilling.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusBilling.setText("Billing");
        MnHapusBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusBilling.setIconTextGap(5);
        MnHapusBilling.setName("MnHapusBilling"); // NOI18N
        MnHapusBilling.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusBillingActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusBilling);

        MnHapusDeposit.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDeposit.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDeposit.setText("Deposit");
        MnHapusDeposit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDeposit.setIconTextGap(5);
        MnHapusDeposit.setName("MnHapusDeposit"); // NOI18N
        MnHapusDeposit.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDepositActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDeposit);

        MnHapusDiet.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiet.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiet.setText("Pemberian Diet");
        MnHapusDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiet.setIconTextGap(5);
        MnHapusDiet.setName("MnHapusDiet"); // NOI18N
        MnHapusDiet.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDietActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiet);

        MnHapusDiagnosa.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDiagnosa.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDiagnosa.setText("Diagnosa/Penyakit");
        MnHapusDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDiagnosa.setIconTextGap(5);
        MnHapusDiagnosa.setName("MnHapusDiagnosa"); // NOI18N
        MnHapusDiagnosa.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDiagnosaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDiagnosa);

        MnHapusDpjp.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusDpjp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDpjp.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusDpjp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDpjp.setText("DPJP Ranap");
        MnHapusDpjp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDpjp.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDpjp.setIconTextGap(5);
        MnHapusDpjp.setName("MnHapusDpjp"); // NOI18N
        MnHapusDpjp.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusDpjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDpjpActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDpjp);

        MnHapusHemodialisa.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusHemodialisa.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusHemodialisa.setText("Hemodialisa");
        MnHapusHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusHemodialisa.setIconTextGap(5);
        MnHapusHemodialisa.setName("MnHapusHemodialisa"); // NOI18N
        MnHapusHemodialisa.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusHemodialisaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusHemodialisa);

        MnHapusKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusKamarInap.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusKamarInap.setText("Kamar Inap");
        MnHapusKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusKamarInap.setIconTextGap(5);
        MnHapusKamarInap.setName("MnHapusKamarInap"); // NOI18N
        MnHapusKamarInap.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusKamarInapActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusKamarInap);

        MnHapusPotongan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPotongan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPotongan.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPotongan.setText("Potongan Biaya");
        MnHapusPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPotongan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPotongan.setIconTextGap(5);
        MnHapusPotongan.setName("MnHapusPotongan"); // NOI18N
        MnHapusPotongan.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPotonganActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPotongan);

        MnHapusPiutang.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPiutang.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPiutang.setText("Piutang Pasien");
        MnHapusPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPiutang.setIconTextGap(5);
        MnHapusPiutang.setName("MnHapusPiutang"); // NOI18N
        MnHapusPiutang.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPiutangActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusPiutang);

        MnHapusProsedur.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusProsedur.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusProsedur.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusProsedur.setText("Prosedur Tindakan");
        MnHapusProsedur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusProsedur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusProsedur.setIconTextGap(5);
        MnHapusProsedur.setName("MnHapusProsedur"); // NOI18N
        MnHapusProsedur.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusProsedurActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusProsedur);

        MnHapusRanapGabung.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRanapGabung.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRanapGabung.setText("Ranap Gabung");
        MnHapusRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRanapGabung.setIconTextGap(5);
        MnHapusRanapGabung.setName("MnHapusRanapGabung"); // NOI18N
        MnHapusRanapGabung.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRanapGabungActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRanapGabung);

        MnHapusRujukKeluar.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRujukKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukKeluar.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusRujukKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukKeluar.setText("Rujuk Keluar");
        MnHapusRujukKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukKeluar.setIconTextGap(5);
        MnHapusRujukKeluar.setName("MnHapusRujukKeluar"); // NOI18N
        MnHapusRujukKeluar.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusRujukKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukKeluarActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukKeluar);

        MnHapusRujukMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukMasuk.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukMasuk.setText("Rujuk Masuk");
        MnHapusRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukMasuk.setIconTextGap(5);
        MnHapusRujukMasuk.setName("MnHapusRujukMasuk"); // NOI18N
        MnHapusRujukMasuk.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukMasukActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusRujukMasuk);

        MnHapusTambahan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTambahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTambahan.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTambahan.setText("Tambahan Biaya");
        MnHapusTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTambahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTambahan.setIconTextGap(5);
        MnHapusTambahan.setName("MnHapusTambahan"); // NOI18N
        MnHapusTambahan.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTambahanActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTambahan);

        MnHapusBookingOperasi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusBookingOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusBookingOperasi.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusBookingOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusBookingOperasi.setText("Booking Operasi");
        MnHapusBookingOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusBookingOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusBookingOperasi.setIconTextGap(5);
        MnHapusBookingOperasi.setName("MnHapusBookingOperasi"); // NOI18N
        MnHapusBookingOperasi.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusBookingOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusBookingOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusBookingOperasi);

        MnTindakan.setBackground(new java.awt.Color(252, 255, 250));
        MnTindakan.setForeground(new java.awt.Color(130, 100, 100));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setIconTextGap(5);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setOpaque(true);
        MnTindakan.setPreferredSize(new java.awt.Dimension(190, 26));

        MnHapusTindakanRanapDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokter.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRanapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokter.setText("Ranap Dokter");
        MnHapusTindakanRanapDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokter.setIconTextGap(5);
        MnHapusTindakanRanapDokter.setName("MnHapusTindakanRanapDokter"); // NOI18N
        MnHapusTindakanRanapDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokter);

        MnHapusTindakanRanapDokterParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setText("Ranap Dokter & Paramedis");
        MnHapusTindakanRanapDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapDokterParamedis.setIconTextGap(5);
        MnHapusTindakanRanapDokterParamedis.setName("MnHapusTindakanRanapDokterParamedis"); // NOI18N
        MnHapusTindakanRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapDokterParamedis);

        MnHapusTindakanRanapParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRanapParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRanapParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRanapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRanapParamedis.setText("Ranap Paramedis");
        MnHapusTindakanRanapParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRanapParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRanapParamedis.setIconTextGap(5);
        MnHapusTindakanRanapParamedis.setName("MnHapusTindakanRanapParamedis"); // NOI18N
        MnHapusTindakanRanapParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRanapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRanapParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRanapParamedis);

        MnHapusTindakanRalanDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokter.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokter.setText("Ralan Dokter");
        MnHapusTindakanRalanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokter.setIconTextGap(5);
        MnHapusTindakanRalanDokter.setName("MnHapusTindakanRalanDokter"); // NOI18N
        MnHapusTindakanRalanDokter.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokter);

        MnHapusTindakanRalanDokterParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanDokterParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setText("Ralan Dokter & Paramedis");
        MnHapusTindakanRalanDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanDokterParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanDokterParamedis.setIconTextGap(5);
        MnHapusTindakanRalanDokterParamedis.setName("MnHapusTindakanRalanDokterParamedis"); // NOI18N
        MnHapusTindakanRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanDokterParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanDokterParamedis);

        MnHapusTindakanRalanParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTindakanRalanParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTindakanRalanParamedis.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusTindakanRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTindakanRalanParamedis.setText("Ralan Paramedis");
        MnHapusTindakanRalanParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTindakanRalanParamedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTindakanRalanParamedis.setIconTextGap(5);
        MnHapusTindakanRalanParamedis.setName("MnHapusTindakanRalanParamedis"); // NOI18N
        MnHapusTindakanRalanParamedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTindakanRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTindakanRalanParamedisActionPerformed(evt);
            }
        });
        MnTindakan.add(MnHapusTindakanRalanParamedis);

        MnHapusData.add(MnTindakan);

        MnPemeriksaan.setBackground(new java.awt.Color(252, 255, 250));
        MnPemeriksaan.setForeground(new java.awt.Color(130, 100, 100));
        MnPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemeriksaan.setText("Pemeriksaan");
        MnPemeriksaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemeriksaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemeriksaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemeriksaan.setIconTextGap(5);
        MnPemeriksaan.setName("MnPemeriksaan"); // NOI18N
        MnPemeriksaan.setOpaque(true);
        MnPemeriksaan.setPreferredSize(new java.awt.Dimension(190, 26));

        MnHapusPemeriksaanRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPemeriksaanRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRalan.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusPemeriksaanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRalan.setText("Ralan");
        MnHapusPemeriksaanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRalan.setIconTextGap(5);
        MnHapusPemeriksaanRalan.setName("MnHapusPemeriksaanRalan"); // NOI18N
        MnHapusPemeriksaanRalan.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusPemeriksaanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRalanActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRalan);

        MnHapusPemeriksaanRanap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusPemeriksaanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusPemeriksaanRanap.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusPemeriksaanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusPemeriksaanRanap.setText("Ranap");
        MnHapusPemeriksaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusPemeriksaanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusPemeriksaanRanap.setIconTextGap(5);
        MnHapusPemeriksaanRanap.setName("MnHapusPemeriksaanRanap"); // NOI18N
        MnHapusPemeriksaanRanap.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusPemeriksaanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusPemeriksaanRanapActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusPemeriksaanRanap);

        MnHapusLab.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusLab.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusLab.setText("Laborat");
        MnHapusLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusLab.setIconTextGap(5);
        MnHapusLab.setName("MnHapusLab"); // NOI18N
        MnHapusLab.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusLabActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusLab);

        MnHapusRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRadiologi.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRadiologi.setText("Radiologi");
        MnHapusRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRadiologi.setIconTextGap(5);
        MnHapusRadiologi.setName("MnHapusRadiologi"); // NOI18N
        MnHapusRadiologi.setPreferredSize(new java.awt.Dimension(140, 26));
        MnHapusRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRadiologiActionPerformed(evt);
            }
        });
        MnPemeriksaan.add(MnHapusRadiologi);

        MnHapusData.add(MnPemeriksaan);

        MnObat.setBackground(new java.awt.Color(252, 255, 250));
        MnObat.setForeground(new java.awt.Color(130, 100, 100));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setIconTextGap(5);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setOpaque(true);
        MnObat.setPreferredSize(new java.awt.Dimension(190, 26));

        MnHapusAturanPkaiObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusAturanPkaiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusAturanPkaiObat.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusAturanPkaiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusAturanPkaiObat.setText("Aturan Pakai Obat");
        MnHapusAturanPkaiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusAturanPkaiObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusAturanPkaiObat.setIconTextGap(5);
        MnHapusAturanPkaiObat.setName("MnHapusAturanPkaiObat"); // NOI18N
        MnHapusAturanPkaiObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusAturanPkaiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusAturanPkaiObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusAturanPkaiObat);

        MnHapusObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObat.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObat.setText("Pemberian Obat");
        MnHapusObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObat.setIconTextGap(5);
        MnHapusObat.setName("MnHapusObat"); // NOI18N
        MnHapusObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusObat);

        MnHapusResepObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepObat.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepObat.setText("Resep Obat");
        MnHapusResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepObat.setIconTextGap(5);
        MnHapusResepObat.setName("MnHapusResepObat"); // NOI18N
        MnHapusResepObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepObat);

        MnHapusResepPulang.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusResepPulang.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusResepPulang.setText("Resep Pulang");
        MnHapusResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusResepPulang.setIconTextGap(5);
        MnHapusResepPulang.setName("MnHapusResepPulang"); // NOI18N
        MnHapusResepPulang.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusResepPulangActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusResepPulang);

        MnHapusReturObat.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusReturObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusReturObat.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusReturObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusReturObat.setText("Retur Obat Pasien");
        MnHapusReturObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusReturObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusReturObat.setIconTextGap(5);
        MnHapusReturObat.setName("MnHapusReturObat"); // NOI18N
        MnHapusReturObat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusReturObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusReturObatActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusReturObat);

        MnHapusStokObatRanap.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusStokObatRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusStokObatRanap.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusStokObatRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusStokObatRanap.setText("Stok Obat Ranap");
        MnHapusStokObatRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusStokObatRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusStokObatRanap.setIconTextGap(5);
        MnHapusStokObatRanap.setName("MnHapusStokObatRanap"); // NOI18N
        MnHapusStokObatRanap.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHapusStokObatRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusStokObatRanapActionPerformed(evt);
            }
        });
        MnObat.add(MnHapusStokObatRanap);

        MnHapusData.add(MnObat);

        MnHapusSemua.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusSemua.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusSemua.setText("Semua Transaksi & Registrasi");
        MnHapusSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusSemua.setIconTextGap(5);
        MnHapusSemua.setName("MnHapusSemua"); // NOI18N
        MnHapusSemua.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusSemuaActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusSemua);

        jPopupMenu1.add(MnHapusData);

        MnUrut.setBackground(new java.awt.Color(252, 255, 250));
        MnUrut.setForeground(new java.awt.Color(130, 100, 100));
        MnUrut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut.setText("Urutkan Data Berdasar");
        MnUrut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut.setIconTextGap(5);
        MnUrut.setName("MnUrut"); // NOI18N
        MnUrut.setOpaque(true);
        MnUrut.setPreferredSize(new java.awt.Dimension(200, 26));

        MnUrutNoRawatDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutNoRawatDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutNoRawatDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc.setText("No.Rawat Descending");
        MnUrutNoRawatDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc.setIconTextGap(5);
        MnUrutNoRawatDesc.setName("MnUrutNoRawatDesc"); // NOI18N
        MnUrutNoRawatDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatDesc);

        MnUrutNoRawatAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutNoRawatAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutNoRawatAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc.setIconTextGap(5);
        MnUrutNoRawatAsc.setName("MnUrutNoRawatAsc"); // NOI18N
        MnUrutNoRawatAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatAsc);

        MnUrutTanggalDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutTanggalDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutTanggalDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc.setText("Tanggal Descending");
        MnUrutTanggalDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc.setIconTextGap(5);
        MnUrutTanggalDesc.setName("MnUrutTanggalDesc"); // NOI18N
        MnUrutTanggalDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalDesc);

        MnUrutTanggalAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutTanggalAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutTanggalAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc.setText("Tanggal Ascending");
        MnUrutTanggalAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc.setIconTextGap(5);
        MnUrutTanggalAsc.setName("MnUrutTanggalAsc"); // NOI18N
        MnUrutTanggalAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalAsc);

        MnUrutDokterDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutDokterDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutDokterDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc.setText("Dokter Descending");
        MnUrutDokterDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc.setIconTextGap(5);
        MnUrutDokterDesc.setName("MnUrutDokterDesc"); // NOI18N
        MnUrutDokterDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterDesc);

        MnUrutDokterAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutDokterAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutDokterAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc.setText("Dokter Ascending");
        MnUrutDokterAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc.setIconTextGap(5);
        MnUrutDokterAsc.setName("MnUrutDokterAsc"); // NOI18N
        MnUrutDokterAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterAsc);

        MnUrutPoliklinikDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPoliklinikDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPoliklinikDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc.setIconTextGap(5);
        MnUrutPoliklinikDesc.setName("MnUrutPoliklinikDesc"); // NOI18N
        MnUrutPoliklinikDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikDesc);

        MnUrutPoliklinikAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPoliklinikAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPoliklinikAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc.setIconTextGap(5);
        MnUrutPoliklinikAsc.setName("MnUrutPoliklinikAsc"); // NOI18N
        MnUrutPoliklinikAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikAsc);

        MnUrutPenjabDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPenjabDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPenjabDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc.setText("Cara Bayar Descending");
        MnUrutPenjabDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc.setIconTextGap(5);
        MnUrutPenjabDesc.setName("MnUrutPenjabDesc"); // NOI18N
        MnUrutPenjabDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabDesc);

        MnUrutPenjabAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPenjabAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPenjabAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc.setIconTextGap(5);
        MnUrutPenjabAsc.setName("MnUrutPenjabAsc"); // NOI18N
        MnUrutPenjabAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabAsc);

        MnUrutStatusDesc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutStatusDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutStatusDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc.setText("Status Descending");
        MnUrutStatusDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc.setIconTextGap(5);
        MnUrutStatusDesc.setName("MnUrutStatusDesc"); // NOI18N
        MnUrutStatusDesc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusDesc);

        MnUrutStatusAsc.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutStatusAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutStatusAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc.setText("Status Ascending");
        MnUrutStatusAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc.setIconTextGap(5);
        MnUrutStatusAsc.setName("MnUrutStatusAsc"); // NOI18N
        MnUrutStatusAsc.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusAsc);

        MnUrutRegDesc1.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutRegDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegDesc1.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutRegDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegDesc1.setText("No. Reg. Descending");
        MnUrutRegDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegDesc1.setIconTextGap(5);
        MnUrutRegDesc1.setName("MnUrutRegDesc1"); // NOI18N
        MnUrutRegDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegDesc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegDesc1);

        MnUrutRegAsc1.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutRegAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegAsc1.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutRegAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegAsc1.setText("No. Reg. Ascending");
        MnUrutRegAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegAsc1.setIconTextGap(5);
        MnUrutRegAsc1.setName("MnUrutRegAsc1"); // NOI18N
        MnUrutRegAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegAsc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegAsc1);

        jPopupMenu1.add(MnUrut);

        jMenu6.setBackground(new java.awt.Color(252, 255, 250));
        jMenu6.setForeground(new java.awt.Color(130, 100, 100));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu6.setText("Label & Barcode");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu6.setIconTextGap(5);
        jMenu6.setName("jMenu6"); // NOI18N
        jMenu6.setOpaque(true);
        jMenu6.setPreferredSize(new java.awt.Dimension(200, 26));

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 255));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(130, 100, 100));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setIconTextGap(5);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 255));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(130, 100, 100));
        MnLabelTracker1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker1.setText("Label Tracker 2");
        MnLabelTracker1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker1.setIconTextGap(5);
        MnLabelTracker1.setName("MnLabelTracker1"); // NOI18N
        MnLabelTracker1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 255));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(130, 100, 100));
        MnLabelTracker2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker2.setText("Label Tracker 3");
        MnLabelTracker2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker2.setIconTextGap(5);
        MnLabelTracker2.setName("MnLabelTracker2"); // NOI18N
        MnLabelTracker2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 255));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(130, 100, 100));
        MnLabelTracker3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker3.setText("Label Tracker 4");
        MnLabelTracker3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker3.setIconTextGap(5);
        MnLabelTracker3.setName("MnLabelTracker3"); // NOI18N
        MnLabelTracker3.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker3);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(130, 100, 100));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Barcode Perawatan 1");
        MnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode.setIconTextGap(5);
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(130, 100, 100));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 2");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setIconTextGap(5);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode1);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(130, 100, 100));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label Rekam Medis 10");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setIconTextGap(5);
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcodeRM9);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien 1");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setIconTextGap(5);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang1);

        MnGelang2.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang2.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang2.setText("Gelang Pasien 2");
        MnGelang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang2.setIconTextGap(5);
        MnGelang2.setName("MnGelang2"); // NOI18N
        MnGelang2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang2);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang3.setText("Gelang Pasien 3");
        MnGelang3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang3.setIconTextGap(5);
        MnGelang3.setName("MnGelang3"); // NOI18N
        MnGelang3.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang3);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang4.setText("Gelang Pasien 4");
        MnGelang4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang4.setIconTextGap(5);
        MnGelang4.setName("MnGelang4"); // NOI18N
        MnGelang4.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang4ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang4);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang5.setText("Gelang Pasien 5");
        MnGelang5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang5.setIconTextGap(5);
        MnGelang5.setName("MnGelang5"); // NOI18N
        MnGelang5.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang5ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang5);

        MnGelang6.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang6.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang6.setText("Gelang Pasien 6");
        MnGelang6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang6.setIconTextGap(5);
        MnGelang6.setName("MnGelang6"); // NOI18N
        MnGelang6.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang6ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang6);

        MnGelang7.setBackground(new java.awt.Color(255, 255, 255));
        MnGelang7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang7.setForeground(new java.awt.Color(130, 100, 100));
        MnGelang7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang7.setText("Gelang Pasien 7");
        MnGelang7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang7.setIconTextGap(5);
        MnGelang7.setName("MnGelang7"); // NOI18N
        MnGelang7.setPreferredSize(new java.awt.Dimension(180, 26));
        MnGelang7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang7ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang7);

        jPopupMenu1.add(jMenu6);

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 255));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(130, 100, 100));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setIconTextGap(5);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(200, 26));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        MenuInputData.setBackground(new java.awt.Color(252, 255, 250));
        MenuInputData.setForeground(new java.awt.Color(130, 100, 100));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setIconTextGap(5);
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setOpaque(true);
        MenuInputData.setPreferredSize(new java.awt.Dimension(200, 26));

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 255));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(130, 100, 100));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa Pasien");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setIconTextGap(5);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MenuInputData.add(MnDiagnosa);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 255));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(130, 100, 100));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setIconTextGap(5);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(180, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 255));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(130, 100, 100));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setIconTextGap(5);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(180, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppIKP.setBackground(new java.awt.Color(255, 255, 255));
        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(130, 100, 100));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setIconTextGap(5);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(180, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        jPopupMenu1.add(MenuInputData);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 255));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(130, 100, 100));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setIconTextGap(5);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(200, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

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
        internalFrame2.setWarnaBawah(new java.awt.Color(245, 250, 240));
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
        internalFrame3.setWarnaBawah(new java.awt.Color(245, 250, 240));
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

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(240, 245, 235));
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

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaBawah(new java.awt.Color(245, 250, 240));
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

        MnKamarInap1.setBackground(new java.awt.Color(255, 255, 255));
        MnKamarInap1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap1.setForeground(new java.awt.Color(130, 100, 100));
        MnKamarInap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap1.setText("Kamar Inap");
        MnKamarInap1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap1.setIconTextGap(5);
        MnKamarInap1.setName("MnKamarInap1"); // NOI18N
        MnKamarInap1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnKamarInap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInap1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnKamarInap1);

        MnHapusRujukan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukan.setForeground(new java.awt.Color(130, 100, 100));
        MnHapusRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukan.setText("Hapus Rujukan Poli Internal");
        MnHapusRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukan.setIconTextGap(5);
        MnHapusRujukan.setName("MnHapusRujukan"); // NOI18N
        MnHapusRujukan.setPreferredSize(new java.awt.Dimension(190, 26));
        MnHapusRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukanActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnHapusRujukan);

        MnTindakan1.setBackground(new java.awt.Color(252, 255, 250));
        MnTindakan1.setForeground(new java.awt.Color(130, 100, 100));
        MnTindakan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan1.setText("Tindakan & Pemeriksaan");
        MnTindakan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan1.setIconTextGap(5);
        MnTindakan1.setName("MnTindakan1"); // NOI18N
        MnTindakan1.setOpaque(true);
        MnTindakan1.setPreferredSize(new java.awt.Dimension(190, 26));

        MnRawatJalan1.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan1.setForeground(new java.awt.Color(130, 100, 100));
        MnRawatJalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan1.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan1.setIconTextGap(5);
        MnRawatJalan1.setName("MnRawatJalan1"); // NOI18N
        MnRawatJalan1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalan1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnRawatJalan1);

        MnPeriksaLab1.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab1.setForeground(new java.awt.Color(130, 100, 100));
        MnPeriksaLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab1.setText("Periksa Laborat");
        MnPeriksaLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab1.setIconTextGap(5);
        MnPeriksaLab1.setName("MnPeriksaLab1"); // NOI18N
        MnPeriksaLab1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLab1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaLab1);

        MnPeriksaRadiologi1.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi1.setForeground(new java.awt.Color(130, 100, 100));
        MnPeriksaRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi1.setText("Periksa Radiologi");
        MnPeriksaRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi1.setIconTextGap(5);
        MnPeriksaRadiologi1.setName("MnPeriksaRadiologi1"); // NOI18N
        MnPeriksaRadiologi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaRadiologi1);

        MnOperasi1.setBackground(new java.awt.Color(255, 255, 255));
        MnOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi1.setForeground(new java.awt.Color(130, 100, 100));
        MnOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi1.setText("Tagihan Operasi/VK");
        MnOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi1.setIconTextGap(5);
        MnOperasi1.setName("MnOperasi1"); // NOI18N
        MnOperasi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnOperasi1);

        MnDataRalan1.setBackground(new java.awt.Color(255, 255, 255));
        MnDataRalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRalan1.setForeground(new java.awt.Color(130, 100, 100));
        MnDataRalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRalan1.setText("Data Tindakan Rawat Jalan");
        MnDataRalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRalan1.setIconTextGap(5);
        MnDataRalan1.setName("MnDataRalan1"); // NOI18N
        MnDataRalan1.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDataRalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataRalan1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnDataRalan1);

        jPopupMenu2.add(MnTindakan1);

        MnObat1.setBackground(new java.awt.Color(252, 255, 250));
        MnObat1.setForeground(new java.awt.Color(130, 100, 100));
        MnObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat1.setText("Obat");
        MnObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat1.setIconTextGap(5);
        MnObat1.setName("MnObat1"); // NOI18N
        MnObat1.setOpaque(true);
        MnObat1.setPreferredSize(new java.awt.Dimension(190, 26));

        MnPemberianObat1.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat1.setForeground(new java.awt.Color(130, 100, 100));
        MnPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat1.setText("Pemberian Obat/BHP");
        MnPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat1.setIconTextGap(5);
        MnPemberianObat1.setName("MnPemberianObat1"); // NOI18N
        MnPemberianObat1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObat1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnPemberianObat1);

        MnNoResep1.setBackground(new java.awt.Color(255, 255, 255));
        MnNoResep1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep1.setForeground(new java.awt.Color(130, 100, 100));
        MnNoResep1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep1.setText("Input No.Resep");
        MnNoResep1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep1.setIconTextGap(5);
        MnNoResep1.setName("MnNoResep1"); // NOI18N
        MnNoResep1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnNoResep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResep1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnNoResep1);

        MnResepDOkter1.setBackground(new java.awt.Color(255, 255, 255));
        MnResepDOkter1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter1.setForeground(new java.awt.Color(130, 100, 100));
        MnResepDOkter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter1.setText("Input Resep Dokter");
        MnResepDOkter1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter1.setIconTextGap(5);
        MnResepDOkter1.setName("MnResepDOkter1"); // NOI18N
        MnResepDOkter1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnResepDOkter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkter1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnResepDOkter1);

        MnObatLangsung1.setBackground(new java.awt.Color(255, 255, 255));
        MnObatLangsung1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung1.setForeground(new java.awt.Color(130, 100, 100));
        MnObatLangsung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung1.setText("Total Tagihan Obat");
        MnObatLangsung1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung1.setIconTextGap(5);
        MnObatLangsung1.setName("MnObatLangsung1"); // NOI18N
        MnObatLangsung1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnObatLangsung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsung1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnObatLangsung1);

        MnDataPemberianObat1.setBackground(new java.awt.Color(255, 255, 255));
        MnDataPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataPemberianObat1.setForeground(new java.awt.Color(130, 100, 100));
        MnDataPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataPemberianObat1.setText("Data Pemberian Obat");
        MnDataPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataPemberianObat1.setIconTextGap(5);
        MnDataPemberianObat1.setName("MnDataPemberianObat1"); // NOI18N
        MnDataPemberianObat1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnDataPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataPemberianObat1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnDataPemberianObat1);

        MnPenjualan1.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjualan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan1.setForeground(new java.awt.Color(130, 100, 100));
        MnPenjualan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan1.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan1.setIconTextGap(5);
        MnPenjualan1.setName("MnPenjualan1"); // NOI18N
        MnPenjualan1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPenjualan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualan1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnPenjualan1);

        jPopupMenu2.add(MnObat1);

        MnBilling1.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling1.setForeground(new java.awt.Color(130, 100, 100));
        MnBilling1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling1.setText("Billing/Pembayaran Pasien");
        MnBilling1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling1.setIconTextGap(5);
        MnBilling1.setName("MnBilling1"); // NOI18N
        MnBilling1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnBilling1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBilling1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnBilling1);

        MnDiagnosa1.setBackground(new java.awt.Color(255, 255, 255));
        MnDiagnosa1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa1.setForeground(new java.awt.Color(130, 100, 100));
        MnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa1.setText("Diagnosa Pasien");
        MnDiagnosa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa1.setIconTextGap(5);
        MnDiagnosa1.setName("MnDiagnosa1"); // NOI18N
        MnDiagnosa1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosa1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnDiagnosa1);

        MnUrut1.setBackground(new java.awt.Color(252, 255, 250));
        MnUrut1.setForeground(new java.awt.Color(130, 100, 100));
        MnUrut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut1.setText("Urutkan Data Berdasar");
        MnUrut1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut1.setIconTextGap(5);
        MnUrut1.setName("MnUrut1"); // NOI18N
        MnUrut1.setOpaque(true);
        MnUrut1.setPreferredSize(new java.awt.Dimension(190, 26));

        MnUrutNoRawatDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutNoRawatDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutNoRawatDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc2.setText("No.Rawat Descending");
        MnUrutNoRawatDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc2.setIconTextGap(5);
        MnUrutNoRawatDesc2.setName("MnUrutNoRawatDesc2"); // NOI18N
        MnUrutNoRawatDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatDesc2);

        MnUrutNoRawatAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutNoRawatAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutNoRawatAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc2.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc2.setIconTextGap(5);
        MnUrutNoRawatAsc2.setName("MnUrutNoRawatAsc2"); // NOI18N
        MnUrutNoRawatAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutNoRawatAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatAsc2);

        MnUrutTanggalDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutTanggalDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutTanggalDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc2.setText("Tanggal Descending");
        MnUrutTanggalDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc2.setIconTextGap(5);
        MnUrutTanggalDesc2.setName("MnUrutTanggalDesc2"); // NOI18N
        MnUrutTanggalDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalDesc2);

        MnUrutTanggalAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutTanggalAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutTanggalAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc2.setText("Tanggal Ascending");
        MnUrutTanggalAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc2.setIconTextGap(5);
        MnUrutTanggalAsc2.setName("MnUrutTanggalAsc2"); // NOI18N
        MnUrutTanggalAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutTanggalAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalAsc2);

        MnUrutDokterDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutDokterDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutDokterDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc2.setText("Dokter Descending");
        MnUrutDokterDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc2.setIconTextGap(5);
        MnUrutDokterDesc2.setName("MnUrutDokterDesc2"); // NOI18N
        MnUrutDokterDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterDesc2);

        MnUrutDokterAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutDokterAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutDokterAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc2.setText("Dokter Ascending");
        MnUrutDokterAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc2.setIconTextGap(5);
        MnUrutDokterAsc2.setName("MnUrutDokterAsc2"); // NOI18N
        MnUrutDokterAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutDokterAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterAsc2);

        MnUrutPoliklinikDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPoliklinikDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPoliklinikDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc2.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc2.setIconTextGap(5);
        MnUrutPoliklinikDesc2.setName("MnUrutPoliklinikDesc2"); // NOI18N
        MnUrutPoliklinikDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikDesc2);

        MnUrutPoliklinikAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPoliklinikAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPoliklinikAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc2.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc2.setIconTextGap(5);
        MnUrutPoliklinikAsc2.setName("MnUrutPoliklinikAsc2"); // NOI18N
        MnUrutPoliklinikAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPoliklinikAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikAsc2);

        MnUrutPenjabDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPenjabDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPenjabDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc2.setText("Cara Bayar Descending");
        MnUrutPenjabDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc2.setIconTextGap(5);
        MnUrutPenjabDesc2.setName("MnUrutPenjabDesc2"); // NOI18N
        MnUrutPenjabDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabDesc2);

        MnUrutPenjabAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutPenjabAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutPenjabAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc2.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc2.setIconTextGap(5);
        MnUrutPenjabAsc2.setName("MnUrutPenjabAsc2"); // NOI18N
        MnUrutPenjabAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutPenjabAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabAsc2);

        MnUrutStatusDesc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutStatusDesc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutStatusDesc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc2.setText("Status Descending");
        MnUrutStatusDesc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc2.setIconTextGap(5);
        MnUrutStatusDesc2.setName("MnUrutStatusDesc2"); // NOI18N
        MnUrutStatusDesc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusDesc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDesc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusDesc2);

        MnUrutStatusAsc2.setBackground(new java.awt.Color(255, 255, 255));
        MnUrutStatusAsc2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc2.setForeground(new java.awt.Color(130, 100, 100));
        MnUrutStatusAsc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc2.setText("Status Ascending");
        MnUrutStatusAsc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc2.setIconTextGap(5);
        MnUrutStatusAsc2.setName("MnUrutStatusAsc2"); // NOI18N
        MnUrutStatusAsc2.setPreferredSize(new java.awt.Dimension(190, 26));
        MnUrutStatusAsc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAsc2ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusAsc2);

        jPopupMenu2.add(MnUrut1);

        MenuInputData1.setBackground(new java.awt.Color(252, 255, 250));
        MenuInputData1.setForeground(new java.awt.Color(130, 100, 100));
        MenuInputData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData1.setText("Input Data");
        MenuInputData1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData1.setIconTextGap(5);
        MenuInputData1.setName("MenuInputData1"); // NOI18N
        MenuInputData1.setOpaque(true);
        MenuInputData1.setPreferredSize(new java.awt.Dimension(190, 26));

        ppBerkasDigital1.setBackground(new java.awt.Color(255, 255, 255));
        ppBerkasDigital1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital1.setForeground(new java.awt.Color(130, 100, 100));
        ppBerkasDigital1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital1.setText("Berkas Digital Perawatan");
        ppBerkasDigital1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital1.setIconTextGap(5);
        ppBerkasDigital1.setName("ppBerkasDigital1"); // NOI18N
        ppBerkasDigital1.setPreferredSize(new java.awt.Dimension(240, 26));
        ppBerkasDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigital1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppBerkasDigital1);

        ppIKP1.setBackground(new java.awt.Color(255, 255, 255));
        ppIKP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP1.setForeground(new java.awt.Color(130, 100, 100));
        ppIKP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP1.setText("Insiden Keselamatan Pasien");
        ppIKP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP1.setIconTextGap(5);
        ppIKP1.setName("ppIKP1"); // NOI18N
        ppIKP1.setPreferredSize(new java.awt.Dimension(240, 26));
        ppIKP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKP1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppIKP1);

        jPopupMenu2.add(MenuInputData1);

        ppRiwayat1.setBackground(new java.awt.Color(255, 255, 255));
        ppRiwayat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat1.setForeground(new java.awt.Color(130, 100, 100));
        ppRiwayat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat1.setText("Riwayat Perawatan");
        ppRiwayat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat1.setIconTextGap(5);
        ppRiwayat1.setName("ppRiwayat1"); // NOI18N
        ppRiwayat1.setPreferredSize(new java.awt.Dimension(190, 26));
        ppRiwayat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayat1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(ppRiwayat1);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kasir Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
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
        TCari.setPreferredSize(new java.awt.Dimension(405, 23));
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

        DTPCari1.setEditable(false);
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-09-2018" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel17);

        DTPCari2.setEditable(false);
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-09-2018" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari2);

        jLabel12.setText("Status Periksa :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum", "Sudah", "Batal", "Berkas Diterima", "Dirujuk", "Meninggal", "Dirawat" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setOpaque(false);
        cmbStatus.setPreferredSize(new java.awt.Dimension(308, 23));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        panelGlass8.add(cmbStatus);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(130, 100, 100));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

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

        TNoRw1.setEditable(false);
        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass9.add(TNoRw1);

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

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(TNoRM);

        jLabel8.setText("Nama Pasien :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel8);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass9.add(TPasien);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        DlgCatatan.dispose();
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
        
        TCari.setText("");
        caripenjab="";
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

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari2KeyPressed

    private void tbKasirRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKasirRalanMouseClicked
        if(tabModekasir.getRowCount()!=0){
            try {
                getDatakasir();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==1){
                i=tbKasirRalan.getSelectedColumn();
                if(i==3){
                    if(validasicatatan.equals("Yes")){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?",TNoRM.getText()));
                        if(!LabelCatatan.getText().equals("")){
                            DlgCatatan.setLocationRelativeTo(TabRawat);
                            DlgCatatan.setVisible(true);
                        }else{
                            DlgCatatan.setVisible(false);
                        }                            
                        this.setCursor(Cursor.getDefaultCursor());
                    }  
                }                
            }else if(evt.getClickCount()==2){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(var.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                        tampilkasir();
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
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBillingActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==4){
                    if(var.getkasir_ralan()==true){
                        MnSudahActionPerformed(null);
                        tampilkasir();
                    }                    
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=2;
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=2;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{      
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else{
                if(var.getkode().equals("Admin Utama")){
                    billing.dlgrwjl.perawatan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"rawat_jl_dr","","","","","","","-","-","","","","","","");
                    billing.dlgrwjl.perawatan.isCek();
                    billing.dlgrwjl.perawatan.tampil();
                    billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                    billing.dlgrwjl.perawatan.setVisible(true);
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{
                        billing.dlgrwjl.perawatan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"rawat_jl_dr","","","","","","","-","-","","","","","","");
                        billing.dlgrwjl.perawatan.isCek();
                        billing.dlgrwjl.perawatan.tampil();
                        billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                        billing.dlgrwjl.perawatan.setVisible(true);
                    }
                }                    
            }            
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{   
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(var.getkode().equals("Admin Utama")){
                    TKdPny.setText("-");
                    billing.dlgobt.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),
                                        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'"),
                                        Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
                    billing.dlgobt.isCek();
                    billing.dlgobt.setDokter("","");
                    billing.dlgobt.tampilobat();
                    billing.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    billing.dlgobt.setLocationRelativeTo(internalFrame1);
                    billing.dlgobt.setVisible(true);
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        TKdPny.setText("-");
                        billing.dlgobt.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),
                                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'"),
                                            Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
                        billing.dlgobt.isCek();
                        billing.dlgobt.setDokter("","");
                        billing.dlgobt.tampilobat();
                        billing.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billing.dlgobt.setLocationRelativeTo(internalFrame1);
                        billing.dlgobt.setVisible(true);
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
                                 piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                 piutang.setLocationRelativeTo(internalFrame1);
                                 piutang.setVisible(true);
                            }else{
                                if(var.getbilling_ralan()==true){
                                    otomatisRalan();
                                }
                                    
                                billing.TNoRw.setText(TNoRw.getText());  
                                billing.isCek();
                                billing.isRawat(); 
                                if(sudah>0){
                                    billing.setPiutang();
                                }
                                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                billing.setLocationRelativeTo(internalFrame1);
                                tampilkasir();
                                billing.setVisible(true);
                            }
                        }else{
                            if(var.getbilling_ralan()==true){
                                otomatisRalan();
                            }
                            billing.TNoRw.setText(TNoRw.getText());  
                            billing.isCek();
                            billing.isRawat(); 
                            billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billing.setLocationRelativeTo(internalFrame1);
                            tampilkasir();
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
           if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
                dlgrwjl2.isCek();
                dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgrwjl2.setLocationRelativeTo(internalFrame1);
            
                dlgrwjl2.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
                dlgrwjl2.setVisible(true);
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
        if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        }else {
            if(var.getkode().equals("Admin Utama")){
                TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));
                WindowObatBhp.setSize(590,80);
                WindowObatBhp.setLocationRelativeTo(internalFrame1);
                WindowObatBhp.setVisible(true);
            }else{
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
           Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariDokterActionPerformed(null);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void btnCariDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDokterActionPerformed
       var.setform("DlgKasirRalan");
       pilihan=1;
       billing.dokter.emptTeks();
        billing.dokter.isCek();
        billing.dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_btnCariDokterActionPerformed

private void MnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokterActionPerformed
    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
    }else {
        if(var.getkode().equals("Admin Utama")){
            WindowGantiDokter.setSize(630,80);
            WindowGantiDokter.setLocationRelativeTo(internalFrame1);
            WindowGantiDokter.setVisible(true);
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
            }else{ 
                WindowGantiDokter.setSize(630,80);
                WindowGantiDokter.setLocationRelativeTo(internalFrame1);
                WindowGantiDokter.setVisible(true);
            }
        }             
    }
    
}//GEN-LAST:event_MnDokterActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{  
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPenjualan penjualan=new DlgPenjualan(null,false);
                penjualan.isCek();
                penjualan.setPasien(TNoRM.getText());  
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                kamarinap.billing.periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                kamarinap.billing.periksalab.setLocationRelativeTo(internalFrame1);
                kamarinap.billing.periksalab.emptTeks();
                kamarinap.billing.periksalab.setNoRm(TNoRw.getText(),"Ralan"); 
                kamarinap.billing.periksalab.isCek();
                kamarinap.billing.periksalab.setVisible(true);
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
            if(var.getkode().equals("Admin Utama")){
                var.setstatus(true);
                kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                kamarinap.setLocationRelativeTo(internalFrame1);
                kamarinap.emptTeks();
                kamarinap.isCek();
                kamarinap.setNoRm(TNoRw.getText());  
                kamarinap.setVisible(true);
            }else{
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{ 
                    var.setstatus(true);
                    kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    kamarinap.setLocationRelativeTo(internalFrame1);
                    kamarinap.emptTeks();
                    kamarinap.isCek();
                    kamarinap.setNoRm(TNoRw.getText()); 
                    kamarinap.setVisible(true);                    
                }
            }                
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
    TabRawatMouseClicked(null);
}//GEN-LAST:event_cmbStatusItemStateChanged

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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgResepObat resep=new DlgResepObat(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8));
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                kamarinap.billing.periksarad.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                kamarinap.billing.periksarad.setLocationRelativeTo(internalFrame1);
                kamarinap.billing.periksarad.emptTeks();
                kamarinap.billing.periksarad.setNoRm(TNoRw.getText(),"Ralan"); 
                kamarinap.billing.periksarad.tampil(); 
                kamarinap.billing.periksarad.isCek();
                kamarinap.billing.periksarad.setVisible(true);
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
        var.setform("DlgKasirRalan");
        pilihan=1;
        billing.poli.isCek();
        billing.poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setAlwaysOnTop(false);
        billing.poli.setVisible(true);
    }//GEN-LAST:event_btnCariPoliActionPerformed

    private void MnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliActionPerformed
        WindowGantiPoli.setSize(630,80);
        WindowGantiPoli.setLocationRelativeTo(internalFrame1);
        WindowGantiPoli.setAlwaysOnTop(false);
        WindowGantiPoli.setVisible(true);                     
    }//GEN-LAST:event_MnPoliActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ralan");
                resep.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Batal'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
            Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
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
            WindowCaraBayar.setSize(630,80);
            WindowCaraBayar.setLocationRelativeTo(internalFrame1);
            WindowCaraBayar.setVisible(true);
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
            Sequel.AutoComitFalse();
            //Sequel.meghapus("ubah_penjab","no_rawat",norawat.getText());
            Sequel.mengedit("reg_periksa","no_rawat=?"," kd_pj=?",2,new String[]{kdpenjab.getText(),TNoRw.getText()});
            //Sequel.menyimpan("ubah_penjab","?,?,?,?","Ubah Jenis Bayar",4,new String[]{norawat.getText(),now,kdpj,kdpenjab.getText()});
            Sequel.AutoComitTrue();
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
        var.setform("DlgKasirRalan");
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
            Sequel.queryu("delete from aturan_pakai where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusAturanPkaiObatActionPerformed

    private void MnHapusBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusBillingActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from billing where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_inap where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from nota_jalan where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusBillingActionPerformed

    private void MnHapusDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDepositActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from deposit where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from detail_obat_racikan where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from obat_racikan where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusObatActionPerformed

    private void MnHapusLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusLabActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from saran_kesan_lab where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_lab where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from hemodialisa where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusHemodialisaActionPerformed

    private void MnHapusKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusKamarInapActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from kamar_inap where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusPotonganActionPerformed

    private void MnHapusRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRadiologiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from beri_bhp_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from hasil_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from gambar_radiologi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from periksa_radiologi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusRadiologiActionPerformed

    private void MnHapusPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusPiutangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from rawat_inap_dr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterActionPerformed

    private void MnHapusTindakanRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_inap_drpr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapDokterParamedisActionPerformed

    private void MnHapusTindakanRanapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRanapParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_inap_pr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRanapParamedisActionPerformed

    private void MnHapusTindakanRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterActionPerformed

    private void MnHapusTindakanRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanDokterParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanDokterParamedisActionPerformed

    private void MnHapusTindakanRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTindakanRalanParamedisActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTindakanRalanParamedisActionPerformed

    private void MnHapusResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusResepObatActionPerformed

    private void MnHapusResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusResepPulangActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusResepPulangActionPerformed

    private void MnHapusReturObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusReturObatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from returpasien where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from stok_obat_pasien where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusStokObatRanapActionPerformed

    private void MnHapusTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTambahanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from tambahan_biaya where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTambahanActionPerformed

    private void MnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusSemuaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
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
            Sequel.queryu("delete from reg_periksa where no_rawat='"+TNoRw.getText()+"'");
            Sequel.AutoComitTrue();
            tampilkasir();
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
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString());
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppBerkasBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasBtnPrintActionPerformed
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
    }//GEN-LAST:event_ppBerkasBtnPrintActionPerformed

    private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
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
    }//GEN-LAST:event_MnRujukActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,true);
            catatan.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'"); 
                MnKamarInapActionPerformed(evt);
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }
            
        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Meninggal'");
                DlgPasienMati dlgPasienMati=new DlgPasienMati(null,false);
                dlgPasienMati.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString()); 
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),Jam.getText().substring(0,2),Jam.getText().substring(3,5),Jam.getText().substring(6,8),
                        tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString());
                resep.tampilobat();
                resep.setVisible(true);
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
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalan1ActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObat1ActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBilling1ActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
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
                    if(var.gettindakan_ralan()==true){
                        MnRawatJalan1ActionPerformed(null);                        
                    }
                }else if(i==1){
                    if(var.getberi_obat()==true){
                        MnPemberianObat1ActionPerformed(null);
                    }                    
                }else if(i==2){
                    //if(var.getbilling_ralan()==true){
                        MnBilling1ActionPerformed(null);
                    //}                    
                }else if(i==3){
                    if(var.getkamar_inap()==true){
                        MnKamarInap1ActionPerformed(null);
                    }                    
                }
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
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString(),
                    tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString(),internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
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
                if(var.getkode().equals("Admin Utama")){
                    var.setstatus(true);
                    kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    kamarinap.setLocationRelativeTo(internalFrame1);
                    kamarinap.emptTeks();
                    kamarinap.isCek();
                    kamarinap.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());   
                    kamarinap.setVisible(true);
                }else{
                    if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                    }else{ 
                        var.setstatus(true);
                        kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        kamarinap.setLocationRelativeTo(internalFrame1);
                        kamarinap.emptTeks();
                        kamarinap.isCek();
                        kamarinap.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());   
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                        WindowObatBhp.setSize(590,80);
                        WindowObatBhp.setLocationRelativeTo(internalFrame1);
                        WindowObatBhp.setVisible(true);
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
        }  
    }//GEN-LAST:event_MnObatLangsung1ActionPerformed

    private void MnDataPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataPemberianObat1ActionPerformed
       if(tabModekasir2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbKasirRalan2.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
                    dlgrwjl2.isCek();
                    dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgrwjl2.setLocationRelativeTo(internalFrame1);
                    dlgrwjl2.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),DTPCari2.getDate(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );    
                    dlgrwjl2.SetPoli(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),13).toString());
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
                if(var.getkode().equals("Admin Utama")){
                    Sequel.queryu2("delete from rujukan_internal_poli where no_rawat=? and kd_dokter=?",2,new String[]{
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString()
                    });
                    tampilkasir2();
                }
            }else{
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        billing.dlgrwjl.perawatan.setNoRm(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),
                            "rawat_jl_dr","","","","","","","-","-","","","","","",""
                        );
                        billing.dlgrwjl.perawatan.isCek();
                        billing.dlgrwjl.perawatan.setPoli(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),13).toString());
                        billing.dlgrwjl.perawatan.tampil();
                        billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                        billing.dlgrwjl.perawatan.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                        }else{
                            billing.dlgrwjl.perawatan.setNoRm(
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString(),
                                "rawat_jl_dr","","","","","","","-","-","","","","","",""
                            );  
                            billing.dlgrwjl.perawatan.isCek();
                            billing.dlgrwjl.perawatan.setPoli(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),13).toString());
                            billing.dlgrwjl.perawatan.tampil();
                            billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                            billing.dlgrwjl.perawatan.setVisible(true);
                        }
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    kamarinap.billing.periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    kamarinap.billing.periksalab.setLocationRelativeTo(internalFrame1);
                    kamarinap.billing.periksalab.emptTeks();
                    kamarinap.billing.periksalab.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan"); 
                    kamarinap.billing.periksalab.setDokterPerujuk(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    kamarinap.billing.periksalab.isCek();
                    kamarinap.billing.periksalab.setVisible(true);
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    kamarinap.billing.periksarad.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    kamarinap.billing.periksarad.setLocationRelativeTo(internalFrame1);
                    kamarinap.billing.periksarad.emptTeks();
                    kamarinap.billing.periksarad.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),"Ralan"); 
                    kamarinap.billing.periksarad.setDokterPerujuk(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
                    kamarinap.billing.periksarad.tampil(); 
                    kamarinap.billing.periksarad.isCek();
                    kamarinap.billing.periksarad.setVisible(true);
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        TKdPny.setText("-");
                        billing.dlgobt.setNoRm(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString(),
                            Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'"),
                            Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'")
                        );
                        billing.dlgobt.isCek();
                        billing.dlgobt.setDokter(
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                            tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                        );
                        billing.dlgobt.tampilobat();
                        billing.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billing.dlgobt.setLocationRelativeTo(internalFrame1);
                        billing.dlgobt.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                        }else{ 
                            TKdPny.setText("-");
                            billing.dlgobt.setNoRm(
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString(),
                                Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'"),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"'")
                            );
                            billing.dlgobt.isCek();
                            billing.dlgobt.setDokter(
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                                tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                            );
                            billing.dlgobt.tampilobat();
                            billing.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billing.dlgobt.setLocationRelativeTo(internalFrame1);
                            billing.dlgobt.setVisible(true);
                        }
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
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
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()                            
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else{
                    DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                    resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.isCek();
                    resep.setNoRm(
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString(),
                        DTPCari1.getDate(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(0,2),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(3,5),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),12).toString().substring(6,8),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),0).toString(),
                        tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),1).toString()
                    );
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
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
                                     piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                     piutang.setLocationRelativeTo(internalFrame1);
                                     piutang.setVisible(true);
                                }else{
                                    if(var.getbilling_ralan()==true){
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
                                if(var.getbilling_ralan()==true){
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
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString())>0){
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
                    resep.tampil();
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
                DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
                resume.setNoRm(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString(),tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString());
                resume.tampil();
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
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{            
            parsialralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            parsialralan.setLocationRelativeTo(internalFrame1);
            //parsialralan.emptTeks();
            parsialralan.isCek();
            parsialralan.setNoRm(TNoRw.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText()));   
            parsialralan.setVisible(true);                        
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
                berkas.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+TNoRw.getText());
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
                    berkas.loadURL("http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
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
        var.setform("DlgKasirRalan");
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
        }else if(TPasien.getText().trim().equals("")){
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
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw1.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPenjualan penjualan=new DlgPenjualan(null,false);
                penjualan.isCek();
                penjualan.setPasien(TNoRM.getText());  
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
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);            
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),4).toString(),"Ralan"); 
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker.jrxml",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker2.jrxml",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker3.jrxml","report","::[ Label Tracker ]::",
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
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker4.jrxml","report","::[ Label Tracker ]::",
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
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            Valid.MyReport("rptBarcodeRawat.jrxml","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            Valid.MyReport("rptBarcodeRawat2.jrxml","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM18.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM6.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnGelang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang2ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM7.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang2ActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang3ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM8.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang3ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang4ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM10.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang4ActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang5ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM14.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang5ActionPerformed

    private void MnGelang6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang6ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM16.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang6ActionPerformed

    private void MnGelang7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang7ActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",Valid.SetTgl3(Tanggal.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM19.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang7ActionPerformed

    private void MnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKDPBPJSActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgSKDPBPJS form=new DlgSKDPBPJS(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);      
                form.emptTeks();      
                form.setNoRm(TNoRM.getText(),TPasien.getText()); 
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnSKDPBPJSActionPerformed

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
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
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
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnPulangPaksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangPaksaBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Pulang Paksa'");
                if(tabModekasir.getRowCount()!=0){tampilkasir();}
            }

        }
    }//GEN-LAST:event_MnPulangPaksaBtnPrintActionPerformed

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
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.TextBox CrPoli;
    private widget.TextBox CrPtg;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgCatatan;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenu MenuInputData1;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnBilling1;
    private javax.swing.JMenuItem MnBillingParsial;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenuItem MnDataPemberianObat;
    private javax.swing.JMenuItem MnDataPemberianObat1;
    private javax.swing.JMenuItem MnDataRalan;
    private javax.swing.JMenuItem MnDataRalan1;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDiagnosa1;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenuItem MnDokter;
    private javax.swing.JMenu MnGanti;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnGelang2;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenuItem MnGelang6;
    private javax.swing.JMenuItem MnGelang7;
    private javax.swing.JMenuItem MnHapusAturanPkaiObat;
    private javax.swing.JMenuItem MnHapusBilling;
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
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnKamarInap1;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
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
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPemberianObat1;
    private javax.swing.JMenu MnPemeriksaan;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPenjualan1;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaLab1;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPeriksaRadiologi1;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenu MnPilihBilling;
    private javax.swing.JMenuItem MnPoli;
    private javax.swing.JMenuItem MnPoliInternal;
    private javax.swing.JMenuItem MnPulangPaksa;
    private javax.swing.JMenuItem MnRawatJalan;
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
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSKDPBPJS;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenu MnTindakan1;
    private javax.swing.JMenu MnTindakanRalan;
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
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
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
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBerkas;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppBerkasDigital1;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppIKP1;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppRiwayat1;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.Table tbKasirRalan;
    private widget.Table tbKasirRalan2;
    // End of variables declaration//GEN-END:variables

    private void tampilkasir() {                
        Valid.tabelKosong(tabModekasir);
        try{   
            pskasir=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts,penjab.png_jawab,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur, "+
                "reg_periksa.status_bayar,reg_periksa.status_poli from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_reg like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  penjab.png_jawab like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.status_bayar like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? order by "+order);
            try{
                pskasir.setString(1,"%"+caripenjab+"%");
                pskasir.setString(2,"%"+CrPoli.getText()+"%");
                pskasir.setString(3,"%"+CrPtg.getText()+"%");
                pskasir.setString(4,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(7,"%"+TCari.getText().trim()+"%");
                pskasir.setString(8,"%"+caripenjab+"%");
                pskasir.setString(9,"%"+CrPoli.getText()+"%");
                pskasir.setString(10,"%"+CrPtg.getText()+"%");
                pskasir.setString(11,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(12,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(13,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(14,"%"+TCari.getText().trim()+"%");
                pskasir.setString(15,"%"+caripenjab+"%");
                pskasir.setString(16,"%"+CrPoli.getText()+"%");
                pskasir.setString(17,"%"+CrPtg.getText()+"%");
                pskasir.setString(18,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(21,"%"+TCari.getText().trim()+"%");
                pskasir.setString(22,"%"+caripenjab+"%");
                pskasir.setString(23,"%"+CrPoli.getText()+"%");
                pskasir.setString(24,"%"+CrPtg.getText()+"%");
                pskasir.setString(25,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(26,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(27,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(28,"%"+TCari.getText().trim()+"%");
                pskasir.setString(29,"%"+caripenjab+"%");
                pskasir.setString(30,"%"+CrPoli.getText()+"%");
                pskasir.setString(31,"%"+CrPtg.getText()+"%");
                pskasir.setString(32,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(33,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(34,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(35,"%"+TCari.getText().trim()+"%");
                pskasir.setString(36,"%"+caripenjab+"%");
                pskasir.setString(37,"%"+CrPoli.getText()+"%");
                pskasir.setString(38,"%"+CrPtg.getText()+"%");
                pskasir.setString(39,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(42,"%"+TCari.getText().trim()+"%");
                pskasir.setString(43,"%"+caripenjab+"%");
                pskasir.setString(44,"%"+CrPoli.getText()+"%");
                pskasir.setString(45,"%"+CrPtg.getText()+"%");
                pskasir.setString(46,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(47,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(48,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(49,"%"+TCari.getText().trim()+"%");
                pskasir.setString(50,"%"+caripenjab+"%");
                pskasir.setString(51,"%"+CrPoli.getText()+"%");
                pskasir.setString(52,"%"+CrPtg.getText()+"%");
                pskasir.setString(53,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(54,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(55,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(56,"%"+TCari.getText().trim()+"%");
                pskasir.setString(57,"%"+caripenjab+"%");
                pskasir.setString(58,"%"+CrPoli.getText()+"%");
                pskasir.setString(59,"%"+CrPtg.getText()+"%");
                pskasir.setString(60,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(61,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(62,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(63,"%"+TCari.getText().trim()+"%");
                pskasir.setString(64,"%"+caripenjab+"%");
                pskasir.setString(65,"%"+CrPoli.getText()+"%");
                pskasir.setString(66,"%"+CrPtg.getText()+"%");
                pskasir.setString(67,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(68,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(69,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(70,"%"+TCari.getText().trim()+"%");
                pskasir.setString(71,"%"+caripenjab+"%");
                pskasir.setString(72,"%"+CrPoli.getText()+"%");
                pskasir.setString(73,"%"+CrPtg.getText()+"%");
                pskasir.setString(74,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(75,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(76,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(77,"%"+TCari.getText().trim()+"%");
                pskasir.setString(78,"%"+caripenjab+"%");
                pskasir.setString(79,"%"+CrPoli.getText()+"%");
                pskasir.setString(80,"%"+CrPtg.getText()+"%");
                pskasir.setString(81,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(82,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(83,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(84,"%"+TCari.getText().trim()+"%");
                pskasir.setString(85,"%"+caripenjab+"%");
                pskasir.setString(86,"%"+CrPoli.getText()+"%");
                pskasir.setString(87,"%"+CrPtg.getText()+"%");
                pskasir.setString(88,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(89,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(90,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(91,"%"+TCari.getText().trim()+"%");
                rskasir=pskasir.executeQuery();
                while(rskasir.next()){
                    tabModekasir.addRow(new String[] {rskasir.getString(5),
                                   rskasir.getString(6),
                                   rskasir.getString(7),
                                   rskasir.getString(8)+" ("+rskasir.getString("umur")+")",
                                   rskasir.getString(9),
                                   rskasir.getString(10),
                                   rskasir.getString(11),
                                   rskasir.getString(12),
                                   Valid.SetAngka(rskasir.getDouble(13)),
                                   rskasir.getString("png_jawab"),
                                   rskasir.getString(14),
                                   rskasir.getString("no_rawat"),
                                   rskasir.getString("tgl_registrasi"),
                                   rskasir.getString("jam_reg"),rskasir.getString(1),rskasir.getString("status_bayar"),rskasir.getString("status_poli")});
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
            pskasir=koneksi.prepareStatement("select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "rujukan_internal_poli.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.stts,penjab.png_jawab,rujukan_internal_poli.kd_poli,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab inner join rujukan_internal_poli "+
                "on rujukan_internal_poli.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                "and rujukan_internal_poli.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and rujukan_internal_poli.kd_poli=poliklinik.kd_poli  where  "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_reg like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  rujukan_internal_poli.kd_dokter like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  penjab.png_jawab like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                " reg_periksa.kd_pj like ? and reg_periksa.status_lanjut='Ralan' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? order by "+order);
            try{
                pskasir.setString(1,"%"+caripenjab+"%");
                pskasir.setString(2,"%"+CrPoli.getText()+"%");
                pskasir.setString(3,"%"+CrPtg.getText()+"%");
                pskasir.setString(4,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(5,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(6,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(7,"%"+TCari.getText().trim()+"%");
                pskasir.setString(8,"%"+caripenjab+"%");
                pskasir.setString(9,"%"+CrPoli.getText()+"%");
                pskasir.setString(10,"%"+CrPtg.getText()+"%");
                pskasir.setString(11,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(12,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(13,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(14,"%"+TCari.getText().trim()+"%");
                pskasir.setString(15,"%"+caripenjab+"%");
                pskasir.setString(16,"%"+CrPoli.getText()+"%");
                pskasir.setString(17,"%"+CrPtg.getText()+"%");
                pskasir.setString(18,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(21,"%"+TCari.getText().trim()+"%");
                pskasir.setString(22,"%"+caripenjab+"%");
                pskasir.setString(23,"%"+CrPoli.getText()+"%");
                pskasir.setString(24,"%"+CrPtg.getText()+"%");
                pskasir.setString(25,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(26,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(27,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(28,"%"+TCari.getText().trim()+"%");
                pskasir.setString(29,"%"+caripenjab+"%");
                pskasir.setString(30,"%"+CrPoli.getText()+"%");
                pskasir.setString(31,"%"+CrPtg.getText()+"%");
                pskasir.setString(32,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(33,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(34,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(35,"%"+TCari.getText().trim()+"%");
                pskasir.setString(36,"%"+caripenjab+"%");
                pskasir.setString(37,"%"+CrPoli.getText()+"%");
                pskasir.setString(38,"%"+CrPtg.getText()+"%");
                pskasir.setString(39,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(42,"%"+TCari.getText().trim()+"%");
                pskasir.setString(43,"%"+caripenjab+"%");
                pskasir.setString(44,"%"+CrPoli.getText()+"%");
                pskasir.setString(45,"%"+CrPtg.getText()+"%");
                pskasir.setString(46,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(47,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(48,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(49,"%"+TCari.getText().trim()+"%");
                pskasir.setString(50,"%"+caripenjab+"%");
                pskasir.setString(51,"%"+CrPoli.getText()+"%");
                pskasir.setString(52,"%"+CrPtg.getText()+"%");
                pskasir.setString(53,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(54,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(55,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(56,"%"+TCari.getText().trim()+"%");
                pskasir.setString(57,"%"+caripenjab+"%");
                pskasir.setString(58,"%"+CrPoli.getText()+"%");
                pskasir.setString(59,"%"+CrPtg.getText()+"%");
                pskasir.setString(60,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(61,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(62,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(63,"%"+TCari.getText().trim()+"%");
                pskasir.setString(64,"%"+caripenjab+"%");
                pskasir.setString(65,"%"+CrPoli.getText()+"%");
                pskasir.setString(66,"%"+CrPtg.getText()+"%");
                pskasir.setString(67,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(68,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(69,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(70,"%"+TCari.getText().trim()+"%");
                pskasir.setString(71,"%"+caripenjab+"%");
                pskasir.setString(72,"%"+CrPoli.getText()+"%");
                pskasir.setString(73,"%"+CrPtg.getText()+"%");
                pskasir.setString(74,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(75,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(76,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(77,"%"+TCari.getText().trim()+"%");
                pskasir.setString(78,"%"+caripenjab+"%");
                pskasir.setString(79,"%"+CrPoli.getText()+"%");
                pskasir.setString(80,"%"+CrPtg.getText()+"%");
                pskasir.setString(81,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
                pskasir.setString(82,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pskasir.setString(83,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pskasir.setString(84,"%"+TCari.getText().trim()+"%");
                rskasir=pskasir.executeQuery();
                while(rskasir.next()){
                    tabModekasir2.addRow(new String[] {
                        rskasir.getString("kd_dokter"),rskasir.getString("nm_dokter"),
                        rskasir.getString("no_rkm_medis"),rskasir.getString("nm_pasien")+" ("+rskasir.getString("umur")+")",
                        rskasir.getString("nm_poli"),rskasir.getString("p_jawab"),
                        rskasir.getString("almt_pj"),rskasir.getString("hubunganpj"),
                        rskasir.getString("png_jawab"),rskasir.getString("stts"),
                        rskasir.getString("no_rawat"),rskasir.getString("tgl_registrasi"),
                        rskasir.getString("jam_reg"),rskasir.getString("kd_poli")
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
            TNoRw1.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString());
            TNoReg.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),14).toString());
            TNoRM.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),2).toString());
            TPasien.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),3).toString());
        }
    }

    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        MnRawatJalan.setEnabled(var.gettindakan_ralan());
        MnRawatJalan1.setEnabled(var.gettindakan_ralan());
        MnPemberianObat.setEnabled(var.getberi_obat());
        MnPemberianObat1.setEnabled(var.getberi_obat());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
        MnPeriksaLab1.setEnabled(var.getperiksa_lab());
        MnResepDOkter.setEnabled(var.getresep_dokter());
        MnResepDOkter1.setEnabled(var.getresep_dokter());
        MnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());
        MnPeriksaRadiologi1.setEnabled(var.getperiksa_radiologi());
        MnOperasi.setEnabled(var.getoperasi());
        MnOperasi1.setEnabled(var.getoperasi());
        MnNoResep.setEnabled(var.getresep_obat());
        MnNoResep1.setEnabled(var.getresep_obat());
        MnObatLangsung.setEnabled(var.getberi_obat());
        MnObatLangsung1.setEnabled(var.getberi_obat());
        MnBillingParsial.setEnabled(var.getbilling_parsial());
        //MnBilling.setEnabled(var.getbilling_ralan());
        MnDataRalan.setEnabled(var.gettindakan_ralan());
        MnDataRalan1.setEnabled(var.gettindakan_ralan());
        MnDataPemberianObat.setEnabled(var.getberi_obat());
        MnDataPemberianObat1.setEnabled(var.getberi_obat());
        MnDokter.setEnabled(var.getkasir_ralan());
        MnPenjab.setEnabled(var.getkasir_ralan());
        MnPenjualan.setEnabled(var.getpenjualan_obat());
        MnRekapHarianDokter.setEnabled(var.getharian_dokter());
        MnRekapHarianParamedis.setEnabled(var.getharian_paramedis());
        MnRekapBulananDokter.setEnabled(var.getbulanan_dokter());
        MnRekapBulananParamedis.setEnabled(var.getbulanan_paramedis());
        MnRekapHarianPoli.setEnabled(var.getharian_tindakan_poli());
        MnRekapHarianObat.setEnabled(var.getobat_per_poli());
        MnDiagnosa.setEnabled(var.getdiagnosa_pasien());   
        MnDiagnosa1.setEnabled(var.getdiagnosa_pasien());   
        ppRiwayat.setEnabled(var.getresume_pasien());     
        ppRiwayat1.setEnabled(var.getresume_pasien());   
        MnRujuk.setEnabled(var.getrujukan_keluar());
        MnPoliInternal.setEnabled(var.getrujukan_poli_internal());
        MnHapusRujukan.setEnabled(var.getrujukan_poli_internal());        
        ppBerkasDigital.setEnabled(var.getberkas_digital_perawatan());        
        ppBerkasDigital1.setEnabled(var.getberkas_digital_perawatan());      
        ppIKP.setEnabled(var.getinsiden_keselamatan_pasien());
        ppIKP1.setEnabled(var.getinsiden_keselamatan_pasien());    
        MnJadwalOperasi.setEnabled(var.getbooking_operasi());      
        MnSKDPBPJS.setEnabled(var.getskdp_bpjs()); 
        MnPermintaanLab.setEnabled(var.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(var.getpermintaan_radiologi());
        
        if(var.getkode().equals("Admin Utama")){
            MnHapusData.setEnabled(true);
        }else{
            MnHapusData.setEnabled(false);
        } 
        
        if(var.getkode().equals("Admin Utama")){
            MnKamarInap.setEnabled(true);
            MnKamarInap1.setEnabled(true); 
        }else{
            if(kamar_inap_kasir_ralan.equals("Yes")){
                MnKamarInap.setEnabled(var.getkamar_inap());
                MnKamarInap1.setEnabled(var.getkamar_inap());
            }else{
                MnKamarInap.setEnabled(false);
                MnKamarInap1.setEnabled(false);
            }
        }
        
        if(!namadokter.equals("")){
            if(var.getkode().equals("Admin Utama")){
                CrPtg.setText("");
                BtnSeek3.setEnabled(true);
                CrPtg.setEditable(true);
            }else{
                CrPtg.setText(namadokter);
                BtnSeek3.setEnabled(false);
                CrPtg.setEditable(false);
            }                
        }else{
            BtnSeek3.setEnabled(true);
            CrPtg.setEditable(true);
        }
        
        if(!namapoli.equals("")){
            if(var.getkode().equals("Admin Utama")){
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
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
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

                if(!var.getkode().equals("Admin Utama")){
                    psotomatis=koneksi.prepareStatement(sqlpsotomatispetugas);
                    try {                
                        psotomatis.setString(1,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
                        rskasir=psotomatis.executeQuery();
                        while(rskasir.next()){    
                            if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_pr where "+
                               "no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                               "and nip='"+var.getkode()+"'")==0){
                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2petugas);
                                try {
                                    psotomatis2.setString(1,TNoRw.getText()); 
                                    psotomatis2.setString(2,rskasir.getString(1));
                                    psotomatis2.setString(3,var.getkode());
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
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
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

                if(!var.getkode().equals("Admin Utama")){
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
                                    psotomatis2.setString(4,var.getkode());
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
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
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
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
        }            
    }
    
    private void otomatisRalan2() {
        try {
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
                            System.out.println("proses input data "+e);
                        } finally{
                            if(psotomatis2!=null){
                                psotomatis2.close();
                            }
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
            
            if(!var.getkode().equals("Admin Utama")){
                psotomatis=koneksi.prepareStatement(sqlpsotomatispetugas);
                try {                
                    psotomatis.setString(1,Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()));
                    rskasir=psotomatis.executeQuery();
                    while(rskasir.next()){    
                        if(Sequel.cariIsiAngka("select count(no_rawat) from rawat_jl_pr where "+
                           "no_rawat='"+tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()+"' and kd_jenis_prw='"+rskasir.getString(1)+"' "+
                           "and nip='"+var.getkode()+"'")==0){
                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2petugas);
                            try {
                                psotomatis2.setString(1,tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString()); 
                                psotomatis2.setString(2,rskasir.getString(1));
                                psotomatis2.setString(3,var.getkode());
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
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
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
            
            if(!var.getkode().equals("Admin Utama")){
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
                                psotomatis2.setString(4,var.getkode());
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
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
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
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDatakasir2() {
        if(tbKasirRalan2.getSelectedRow()!= -1){
            TNoRw1.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),10).toString());
            TNoReg.setText("-");
            TNoRM.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),2).toString());
            TPasien.setText(tbKasirRalan2.getValueAt(tbKasirRalan2.getSelectedRow(),3).toString());
        }
    }
    
    public void setCariKosong() {
      TCari.setText("");
    }
    
}

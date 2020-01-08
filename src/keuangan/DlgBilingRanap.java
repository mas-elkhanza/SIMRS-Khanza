
/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package keuangan;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgReturJual;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPeriksaLab;
import simrskhanza.DlgCariPeriksaRadiologi;
import inventory.DlgPemberianObat;
import simrskhanza.DlgPenanggungJawab;
import simrskhanza.DlgPeriksaLaboratorium;
import simrskhanza.DlgPeriksaRadiologi;
import simrskhanza.DlgRawatInap;
import simrskhanza.DlgRawatJalan;
import inventory.DlgResepPulang;
import simrskhanza.DlgTagihanOperasi;

/**
 *
 * @author perpustakaan
 */
public class DlgBilingRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabModeRwJlDr,tabModeTambahan,tabModePotongan,tabModeKamIn,tabModeAkunBayar,tabModeAkunPiutang;
    public DlgResepPulang reseppulang=new DlgResepPulang(null,false);

    public DlgPemberianObat beriobat=new DlgPemberianObat(null,false);
    public DlgRawatInap rawatinap=new DlgRawatInap(null,false);
    public DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
    public DlgPeriksaRadiologi periksarad=new DlgPeriksaRadiologi(null,false);
    public DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    public DlgDeposit deposit=new DlgDeposit(null,false);
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pscekbilling,pscarirm,pscaripasien,psreg,pskamar,pscarialamat,psbiling,
            psdokterranap,psdokterralan,pscariobat,psobatlangsung,psobatoperasi,
            psreturobat,psdetaillab,pstamkur,psrekening,psakunbayar,psakunpiutang,
            pskamarin,psbiayasekali,psbiayaharian,psreseppulang,pstambahanbiaya,pspotonganbiaya,pstemporary,
            psralandokter,psralandrpr,psranapdrpr,psranapdokter,
            psoperasi,psralanperawat,psranapperawat,
            psperiksalab,pssudahmasuk,pskategori,psubahpenjab,psperiksarad,psanak,psnota,psservice;
    private ResultSet rscekbilling,rscarirm,rscaripasien,rsreg,rskamar,rscarialamat,rsdetaillab,
            rsdokterranap,rsranapdrpr,rsdokterralan,rscariobat,rsobatlangsung,rsobatoperasi,rsreturobat,rsubahpenjab,
            rskamarin,rsbiayasekali,rsbiayaharian,rsreseppulang,rstambahanbiaya,rspotonganbiaya,
            rsralandokter,rsralandrpr,rsranapdokter,rsoperasi,rsralanperawat,rsranapperawat,rsperiksalab,rskategori,
            rsperiksarad,rsanak,rstamkur,rsrekening,rsservice,rsakunbayar,rsakunpiutang;
    private String umur="",biaya="",tambahan="",totals="",norawatbayi="",centangdokterranap="",kd_pj="",
            rinciandokterranap="",rincianoperasi="",hariawal="",notaranap="",tampilkan_administrasi_di_billingranap="",
            Tindakan_Ranap="",Laborat_Ranap="",Radiologi_Ranap="",Obat_Ranap="",Registrasi_Ranap="",
            Tambahan_Ranap="",Potongan_Ranap="",Retur_Obat_Ranap="",Resep_Pulang_Ranap="",Kamar_Inap="",Operasi_Ranap="",
            Harian_Ranap="",Uang_Muka_Ranap="",Piutang_Pasien_Ranap="",tampilkan_ppnobat_ranap="",
            Service_Ranap="",status="",centangobatranap="No",
            sqlpscekbilling="select count(billing.no_rawat) from billing where billing.no_rawat=?",
            sqlpsdokterranap="select dokter.nm_dokter from rawat_inap_dr "+
                    "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                    "where no_rawat=? group by rawat_inap_dr.kd_dokter",
            sqlpsdokterralan="select dokter.nm_dokter from rawat_jl_dr "+
                    "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                    "where no_rawat=? group by rawat_jl_dr.kd_dokter",
            sqlpsobatoperasi="select obatbhp_ok.nm_obat,beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah, "+
                    "(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                    "from obatbhp_ok inner join beri_obat_operasi "+
                    "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat where "+
                    "beri_obat_operasi.no_rawat=? group by beri_obat_operasi.kd_obat",
            sqlpsreturobat="select databarang.nama_brng,detreturjual.h_retur, "+
                    "sum(detreturjual.jml_retur * -1) as jml, "+
                    "sum(detreturjual.subtotal * -1) as ttl from detreturjual inner join databarang inner join returjual "+
                    "on detreturjual.kode_brng=databarang.kode_brng "+
                    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual like ? group by databarang.kode_brng",
            sqlpsobatlangsung="select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",
            sqlpskamarin="select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,"+
                    "kamar_inap.lama,kamar_inap.ttl_biaya as total,kamar_inap.tgl_masuk, "+
                    "kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar,"+
                    "if(kamar_inap.jam_keluar='00:00:00',current_time(),kamar_inap.jam_keluar) as jam_keluar "+
                    "from kamar_inap inner join bangsal inner join kamar "+
                    "on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "and kamar.kd_bangsal=bangsal.kd_bangsal where "+
                    "kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk,kamar_inap.kd_kamar",
            sqlpsbiayasekali="select nama_biaya,besar_biaya,(besar_biaya*1) as total from biaya_sekali "+
                    " where kd_kamar=? order by nama_biaya",
            sqlpsbiayaharian="select nama_biaya,besar_biaya,jml,(jml*besar_biaya*?) as total from biaya_harian "+
                    " where kd_kamar=? order by nama_biaya",
            sqlpsreseppulang="select databarang.nama_brng,resep_pulang.harga,"+
                    "resep_pulang.jml_barang,resep_pulang.dosis,resep_pulang.total "+
                    "from resep_pulang inner join databarang "+
                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                    "resep_pulang.no_rawat=? order by databarang.nama_brng",
            sqlpstambahanbiaya="select nama_biaya, besar_biaya from tambahan_biaya where no_rawat=?  ",
            sqlpspotonganbiaya="select nama_pengurangan, besar_pengurangan from pengurangan_biaya where no_rawat=?  ",
            sqlpsralandokter="select jns_perawatan.nm_perawatan,rawat_jl_dr.biaya_rawat as total_byrdr,count(rawat_jl_dr.kd_jenis_prw) as jml, "+
                    "sum(rawat_jl_dr.biaya_rawat) as biaya,"+
                    "sum(rawat_jl_dr.bhp) as totalbhp,"+
                    "(sum(rawat_jl_dr.material)+sum(rawat_jl_dr.menejemen)+sum(rawat_jl_dr.kso)) as totalmaterial,"+
                    "rawat_jl_dr.tarif_tindakandr,"+
                    "sum(rawat_jl_dr.tarif_tindakandr) as totaltarif_tindakandr  "+
                    "from rawat_jl_dr inner join jns_perawatan inner join kategori_perawatan "+
                    "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
                    "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where "+
                    "rawat_jl_dr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_dr.kd_jenis_prw",
            sqlpsralandrpr="select jns_perawatan.nm_perawatan,rawat_jl_drpr.biaya_rawat as total_byrdr,count(rawat_jl_drpr.kd_jenis_prw) as jml, "+
                    "sum(rawat_jl_drpr.biaya_rawat) as biaya,"+
                    "sum(rawat_jl_drpr.bhp) as totalbhp,"+
                    "(sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.menejemen)+sum(rawat_jl_drpr.kso)) as totalmaterial,"+
                    "rawat_jl_drpr.tarif_tindakandr,"+
                    "sum(rawat_jl_drpr.tarif_tindakanpr) as totaltarif_tindakanpr,"+
                    "sum(rawat_jl_drpr.tarif_tindakandr) as totaltarif_tindakandr  "+
                    "from rawat_jl_drpr inner join jns_perawatan inner join kategori_perawatan "+
                    "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
                    "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where "+
                    "rawat_jl_drpr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_drpr.kd_jenis_prw",
            sqlpsranapdokter="select jns_perawatan_inap.nm_perawatan,rawat_inap_dr.biaya_rawat as total_byrdr,count(rawat_inap_dr.kd_jenis_prw) as jml, "+
                    "sum(rawat_inap_dr.biaya_rawat) as biaya,"+
                    "sum(rawat_inap_dr.bhp) as totalbhp,"+
                    "(sum(rawat_inap_dr.material)+sum(rawat_inap_dr.menejemen)+sum(rawat_inap_dr.kso)) as totalmaterial,"+
                    "rawat_inap_dr.tarif_tindakandr,"+
                    "sum(rawat_inap_dr.tarif_tindakandr) as totaltarif_tindakandr "+
                    "from rawat_inap_dr inner join jns_perawatan_inap inner join kategori_perawatan "+
                    "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "+
                    "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori where "+
                    "rawat_inap_dr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_inap_dr.kd_jenis_prw",
            sqlpsranapdrpr="select jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.biaya_rawat as total_byrdr,count(rawat_inap_drpr.kd_jenis_prw) as jml, "+
                    "sum(rawat_inap_drpr.biaya_rawat) as biaya,"+
                    "sum(rawat_inap_drpr.bhp) as totalbhp,"+
                    "(sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.menejemen)+sum(rawat_inap_drpr.kso)) as totalmaterial,"+
                    "rawat_inap_drpr.tarif_tindakandr,"+
                    "sum(rawat_inap_drpr.tarif_tindakanpr) as totaltarif_tindakanpr, "+
                    "sum(rawat_inap_drpr.tarif_tindakandr) as totaltarif_tindakandr "+
                    "from rawat_inap_drpr inner join jns_perawatan_inap inner join kategori_perawatan "+
                    "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "+
                    "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori where "+
                    "rawat_inap_drpr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_inap_drpr.kd_jenis_prw",
            sqlpsralanperawat="select jns_perawatan.nm_perawatan,jns_perawatan.total_byrpr,count(jns_perawatan.nm_perawatan) as jml, "+
                                           "jns_perawatan.total_byrpr*count(jns_perawatan.nm_perawatan) as biaya "+
                                           "from rawat_jl_pr inner join jns_perawatan inner join kategori_perawatan  "+
                                           "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw  and "+
                                           "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where "+
                                           "rawat_jl_pr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_pr.kd_jenis_prw ",
            sqlpsranapperawat="select jns_perawatan_inap.nm_perawatan,jns_perawatan_inap.total_byrpr,count(jns_perawatan_inap.nm_perawatan) as jml, "+
                                           "jns_perawatan_inap.total_byrpr*count(jns_perawatan_inap.nm_perawatan) as biaya "+
                                           "from rawat_inap_pr inner join jns_perawatan_inap  inner join kategori_perawatan "+
                                           "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw  and "+
                                           "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori where "+
                                           "rawat_inap_pr.no_rawat=? and kategori_perawatan.kd_kategori=?  group by rawat_inap_pr.kd_jenis_prw",
            sqlpsoperasi="select paket_operasi.nm_perawatan,(operasi.biayaoperator1+operasi.biayaoperator2+"+
                         "operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+"+
                         "operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+"+
                         "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+"+
                         "operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                         "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+"+
                         "operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+"+
                         "operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+"+
                         "operasi.biaya_dokter_umum) as biaya,operasi.biayaoperator1,"+
                         "operasi.biayaoperator2,operasi.biayaoperator3,operasi.biayaasisten_operator1,operasi.biayaasisten_operator2,operasi.biayaasisten_operator3,"+
                         "operasi.biayainstrumen,operasi.biayadokter_anak,operasi.biayaperawaat_resusitas,"+
                         "operasi.biayadokter_anestesi,operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2,operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3,operasi.biayaperawat_luar,"+
                         "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                         "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum "+
                         "from operasi inner join paket_operasi "+
                         "on operasi.kode_paket=paket_operasi.kode_paket where "+
                         "operasi.no_rawat=? and operasi.status like ?",
            sqlpsnota="insert into nota_inap values(?,?,?,?,?)",
            sqlpsbiling="insert into billing values(?,?,?,?,?,?,?,?,?,?,?)",
            sqlpssudahmasuk="select no,nm_perawatan, if(biaya<>0,biaya,null) as satu, if(jumlah<>0,jumlah,null) as dua,"+
                                           "if(tambahan<>0,tambahan,null) as tiga, if(totalbiaya<>0,totalbiaya,null) as empat,pemisah,status "+
                                           "from billing where no_rawat=?  order by noindex",
            sqlpskategori="SELECT kd_kategori, nm_kategori FROM kategori_perawatan",
            sqlpstamkur="select biaya from temporary_tambahan_potongan where no_rawat=? and nama_tambahan=? and status=?",
            sqlpsanak="select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2 from reg_periksa inner join pasien inner join ranap_gabung on "+
                    "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?",
            sqlpstemporary="insert into temporary_bayar_ranap values('0',?,?,?,?,?,?,?,?,'','','','','','','','','')",
            sqlpsubahpenjab="select tgl_ubah,kd_pj1,kd_pj2 from ubah_penjab where no_rawat=?";    
    private double ttl=0,y=0,subttl=0,lab,ttl1,ttl2,ttlobat,ttlretur,ppnobat,piutang=0,kekurangan=0,itembayar=0,itempiutang=0, 
            tamkur=0,detailjs=0,detailbhp=0,ppn=0,besarppn=0,tagihanppn=0,bayar=0,total=0,uangdeposit=0,
            ttlLaborat=0,ttlRadiologi=0,ttlOperasi=0,ttlObat=0,ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlRalan_Dokter=0,
            ttlRalan_Paramedis=0,ttlTambahan=0,ttlPotongan=0,ttlKamar=0,ttlRegistrasi=0,ttlHarian=0,ttlRetur_Obat=0,ttlResep_Pulang=0,
            laboratserv=0,radiologiserv=0,operasiserv=0,obatserv=0,
            ranap_dokterserv=0,ranap_paramedisserv=0,ralan_dokterserv=0,
            ralan_paramedisserv=0,tambahanserv=0,potonganserv=0,
            kamarserv=0,registrasiserv=0,harianserv=0,retur_Obatserv=0,resep_Pulangserv=0,ttlService=0,
            persenbayi=Sequel.cariInteger("select bayi from set_jam_minimal");
    private int x=0,z=0,i=0,countbayar=0,jml=0,r=0,row2=0;
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private String[] Nama_Akun_Piutang,Kode_Rek_Piutang,Kd_PJ,Besar_Piutang,Jatuh_Tempo,
            Nama_Akun_Bayar,Kode_Rek_Bayar,Bayar,PPN_Persen,PPN_Besar;

    /** Creates new form DlgBiling
     * @param parent
     * @param modal */
    public DlgBilingRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //this.setLocation(8,1);
        //setSize(891,640);

        Object[] rowRwJlDr={"Pilih","Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jumlah","Tambahan","Total Biaya",""};
        tabModeRwJlDr=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==6)||(colIndex==0)) {
                        a=true;
                    }
                    return a;
              }
              
              Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBilling.setModel(tabModeRwJlDr);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbBilling.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbBilling.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 9; i++) {
            TableColumn column = tbBilling.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(370);
            }else if(i==3){
                column.setPreferredWidth(15);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){           
                column.setMinWidth(0);     
                column.setMaxWidth(0);
            }
        }

        tbBilling.setDefaultRenderer(Object.class, new WarnaTable());
        
        //tambahan biaya
        Object[] rowTambahan={"Tambahan Biaya","Besar Biaya"};
        tabModeTambahan=new DefaultTableModel(null,rowTambahan){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){return true;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTambahan.setModel(tabModeTambahan);

        tbTambahan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbTambahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbTambahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(150);
            }
        }
        tbTambahan.setDefaultRenderer(Object.class, new WarnaTable());
        
        //potongan biaya
        Object[] rowPotongan={"Potongan Biaya","Besar Potongan"};
        tabModePotongan=new DefaultTableModel(null,rowPotongan){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){return true;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPotongan.setModel(tabModePotongan);

        tbPotongan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPotongan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPotongan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(150);
            }
        }
        tbPotongan.setDefaultRenderer(Object.class, new WarnaTable());

        //ubah lama inap
        Object[] rowUbahLama={"Kode Kamar","Nama Kamar","Tgl.Masuk","Jam Masuk","Tgl.Keluar","Jam Keluar","Lama Inap"};
        tabModeKamIn=new DefaultTableModel(null,rowUbahLama){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                 boolean a = false;
                 if ((colIndex==2)||(colIndex==3)||(colIndex==4)||(colIndex==5||(colIndex==6))) {
                      a=true;
                 }
                 return a;
             }
              
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                  return types [columnIndex];
             }
        };
        tbUbahLama.setModel(tabModeKamIn);

        tbUbahLama.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbUbahLama.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbUbahLama.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else{
                column.setPreferredWidth(80);
            }
        }

        tbUbahLama.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        
        tabModeAkunBayar=new DefaultTableModel(null,new Object[]{"Nama Akun","Kode Rek","Bayar","PPN(%)","PPN(Rp)"}){             
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==2)) {
                    a=true;
                }
                return a;
            }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAkunBayar.setModel(tabModeAkunBayar);

        tbAkunBayar.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAkunBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbAkunBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(345);
            }else if(i==1){
                //column.setPreferredWidth(70);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(112);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(90);
            }
        }
        warna.kolom=2;
        tbAkunBayar.setDefaultRenderer(Object.class,warna);
        
        tabModeAkunPiutang=new DefaultTableModel(null,new Object[]{"Nama Akun","Kode Rek","Kd PJ","Piutang","Jatuh Tempo"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==3)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAkunPiutang.setModel(tabModeAkunPiutang);

        tbAkunPiutang.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAkunPiutang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbAkunPiutang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(405);
            }else if(i==1){
                //column.setPreferredWidth(70);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                //column.setPreferredWidth(70);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(112);
            }else if(i==4){
                column.setPreferredWidth(90);
            }
        }
        warna2.kolom=3;
        tbAkunPiutang.setDefaultRenderer(Object.class,warna2);

        beriobat.dlgobt.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {if(akses.getform().equals("DLgBilingRanap")){isRawat();}}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        beriobat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {if(akses.getform().equals("DLgBilingRanap")){isRawat();}}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        rawatinap.perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {if(akses.getform().equals("DLgBilingRanap")){isRawat();}}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        reseppulang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {if(akses.getform().equals("DLgBilingRanap")){isRawat();}}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        reseppulang.inputresep.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {if(akses.getform().equals("DLgBilingRanap")){isRawat();}}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        deposit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DLgBilingRanap")){
                    if(status.equals("belum")){
                        uangdeposit=Sequel.cariIsiAngka("select ifnull(sum(besar_deposit),0) from deposit where no_rawat=?",TNoRw.getText());                  
                    }else{
                        uangdeposit=Sequel.cariIsiAngka("select ifnull(sum(Uang_Muka),0) from nota_inap where no_rawat=?",TNoRw.getText());
                    }
                    Deposit.setText(Valid.SetAngka(uangdeposit));
                    isKembali();
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
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DLgBilingRanap")){
                    if(penjab.getTable().getSelectedRow()!= -1){
                        kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                        nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
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
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DLgBilingRanap")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        try {
            notaranap=Sequel.cariIsi("select cetaknotasimpanranap from set_nota");
            centangdokterranap=Sequel.cariIsi("select centangdokterranap from set_nota"); 
            rinciandokterranap=Sequel.cariIsi("select rinciandokterranap from set_nota"); 
            rincianoperasi=Sequel.cariIsi("select rincianoperasi from set_nota"); 
            tampilkan_administrasi_di_billingranap=Sequel.cariIsi("select tampilkan_administrasi_di_billingranap from set_nota"); 
            tampilkan_ppnobat_ranap=Sequel.cariIsi("select tampilkan_ppnobat_ranap from set_nota");
            centangobatranap=Sequel.cariIsi("select centangobatranap from set_nota"); 
        } catch (Exception e) {
            notaranap="No";
            centangdokterranap="No";
            rinciandokterranap="No";
            rincianoperasi="No";
            tampilkan_administrasi_di_billingranap="No"; 
            tampilkan_ppnobat_ranap="No";
            centangobatranap="No";
        }
            
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                if(rsrekening.next()){
                    Tindakan_Ranap=rsrekening.getString("Suspen_Piutang_Tindakan_Ranap");
                    Laborat_Ranap=rsrekening.getString("Suspen_Piutang_Laborat_Ranap");
                    Radiologi_Ranap=rsrekening.getString("Suspen_Piutang_Radiologi_Ranap");
                    Obat_Ranap=rsrekening.getString("Suspen_Piutang_Obat_Ranap");
                    Registrasi_Ranap=rsrekening.getString("Registrasi_Ranap");
                    Tambahan_Ranap=rsrekening.getString("Tambahan_Ranap");
                    Potongan_Ranap=rsrekening.getString("Potongan_Ranap");
                    Retur_Obat_Ranap=rsrekening.getString("Retur_Obat_Ranap");
                    Resep_Pulang_Ranap=rsrekening.getString("Resep_Pulang_Ranap");
                    Kamar_Inap=rsrekening.getString("Kamar_Inap");
                    Operasi_Ranap=rsrekening.getString("Operasi_Ranap");
                    Harian_Ranap=rsrekening.getString("Harian_Ranap");
                    Uang_Muka_Ranap=rsrekening.getString("Uang_Muka_Ranap");
                    Piutang_Pasien_Ranap=rsrekening.getString("Piutang_Pasien_Ranap");
                    Service_Ranap=rsrekening.getString("Service_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnRawatInap = new javax.swing.JMenuItem();
        MnInputObat = new javax.swing.JMenuItem();
        MnInputResepPulang = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnTagihanOperasi = new javax.swing.JMenuItem();
        MnTambahan = new javax.swing.JMenuItem();
        MnPotongan = new javax.swing.JMenuItem();
        MnUbahLamaInap = new javax.swing.JMenuItem();
        MnObatLangsung = new javax.swing.JMenuItem();
        MnReturJual = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnDataObat = new javax.swing.JMenuItem();
        MnDataResepPulang = new javax.swing.JMenuItem();
        MnCariPeriksaLab = new javax.swing.JMenuItem();
        MnCariRadiologi = new javax.swing.JMenuItem();
        MnBayi = new javax.swing.JMenu();
        MnRawatJalan1 = new javax.swing.JMenuItem();
        MnRawatInap1 = new javax.swing.JMenuItem();
        MnInputObat1 = new javax.swing.JMenuItem();
        MnInputResepPulang1 = new javax.swing.JMenuItem();
        MnPeriksaRadiologi1 = new javax.swing.JMenuItem();
        MnPeriksaLab1 = new javax.swing.JMenuItem();
        MnTambahan1 = new javax.swing.JMenuItem();
        MnPotongan1 = new javax.swing.JMenuItem();
        MnObatLangsung1 = new javax.swing.JMenuItem();
        MnDataObat1 = new javax.swing.JMenuItem();
        MnDataResepPulang1 = new javax.swing.JMenuItem();
        MnReturJual1 = new javax.swing.JMenuItem();
        MnCariPeriksaLab1 = new javax.swing.JMenuItem();
        MnCariRadiologi1 = new javax.swing.JMenuItem();
        MnHapusTagihan = new javax.swing.JMenuItem();
        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel8 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan2 = new widget.Button();
        BtnBatal1 = new widget.Button();
        WindowInput3 = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbTambahan = new widget.Table();
        panelisi1 = new widget.panelisi();
        label15 = new widget.Label();
        norawattambahan = new widget.TextBox();
        BtnTambah = new widget.Button();
        BtnSimpan3 = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowInput4 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbPotongan = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        norawatpotongan = new widget.TextBox();
        BtnTambahPotongan = new widget.Button();
        BtnSimpanPotongan = new widget.Button();
        BtnHapusPotongan = new widget.Button();
        BtnKeluarPotongan = new widget.Button();
        WindowInput5 = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        scrollPane3 = new widget.ScrollPane();
        tbUbahLama = new widget.Table();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        norawatubahlama = new widget.TextBox();
        label18 = new widget.Label();
        BtnSimpanUbahLama = new widget.Button();
        BtnKeluarUbahLama = new widget.Button();
        WindowInput6 = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnPenjab = new widget.Button();
        panelGlass8 = new widget.panelisi();
        PopupPiutang = new javax.swing.JPopupMenu();
        ppBersihkan1 = new javax.swing.JMenuItem();
        PopupBayar = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass1 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel4 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        panelGlass2 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnNota = new widget.Button();
        BtnView = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbBilling = new widget.Table();
        panelBayar = new widget.panelisi();
        TtlSemua = new widget.TextBox();
        TKembali = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel15 = new widget.Label();
        TagihanPPn = new widget.TextBox();
        scrollPane4 = new widget.ScrollPane();
        tbAkunBayar = new widget.Table();
        jLabel6 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        tbAkunPiutang = new widget.Table();
        TCari = new widget.TextBox();
        BtnCariBayar = new widget.Button();
        TCari1 = new widget.TextBox();
        btnCariPiutang = new widget.Button();
        jLabel13 = new widget.Label();
        Deposit = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        ChkPiutang = new widget.CekBox();
        chkRalan = new widget.CekBox();
        chkRanap = new widget.CekBox();
        jLabel18 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatInap.setText("Tagihan/Tindakan Rawat Inap");
        MnRawatInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatInap.setName("MnRawatInap"); // NOI18N
        MnRawatInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatInap);

        MnInputObat.setBackground(new java.awt.Color(255, 255, 254));
        MnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputObat.setForeground(new java.awt.Color(50, 50, 50));
        MnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputObat.setText("Input Pemberian Obat");
        MnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputObat.setName("MnInputObat"); // NOI18N
        MnInputObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputObat);

        MnInputResepPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnInputResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputResepPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnInputResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputResepPulang.setText("Input Resep Pulang");
        MnInputResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputResepPulang.setName("MnInputResepPulang"); // NOI18N
        MnInputResepPulang.setPreferredSize(new java.awt.Dimension(250, 28));
        MnInputResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputResepPulangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputResepPulang);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Input Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Input Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaRadiologi);

        MnTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTagihanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTagihanOperasi.setText("Tagihan Operasi/VK");
        MnTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTagihanOperasi.setName("MnTagihanOperasi"); // NOI18N
        MnTagihanOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTagihanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTagihanOperasi);

        MnTambahan.setBackground(new java.awt.Color(255, 255, 254));
        MnTambahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTambahan.setForeground(new java.awt.Color(50, 50, 50));
        MnTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTambahan.setText("Tambahan Biaya");
        MnTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTambahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTambahan.setName("MnTambahan"); // NOI18N
        MnTambahan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTambahanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTambahan);

        MnPotongan.setBackground(new java.awt.Color(255, 255, 254));
        MnPotongan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPotongan.setForeground(new java.awt.Color(50, 50, 50));
        MnPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPotongan.setText("Potongan Biaya");
        MnPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPotongan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPotongan.setName("MnPotongan"); // NOI18N
        MnPotongan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPotonganActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPotongan);

        MnUbahLamaInap.setBackground(new java.awt.Color(255, 255, 254));
        MnUbahLamaInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahLamaInap.setForeground(new java.awt.Color(50, 50, 50));
        MnUbahLamaInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahLamaInap.setText("Ubah Lama Inap");
        MnUbahLamaInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUbahLamaInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUbahLamaInap.setName("MnUbahLamaInap"); // NOI18N
        MnUbahLamaInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnUbahLamaInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahLamaInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbahLamaInap);

        MnObatLangsung.setBackground(new java.awt.Color(255, 255, 254));
        MnObatLangsung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung.setForeground(new java.awt.Color(50, 50, 50));
        MnObatLangsung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung.setText("Tagihan BHP & Obat Langsung");
        MnObatLangsung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung.setName("MnObatLangsung"); // NOI18N
        MnObatLangsung.setPreferredSize(new java.awt.Dimension(250, 25));
        MnObatLangsung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsungActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnObatLangsung);

        MnReturJual.setBackground(new java.awt.Color(255, 255, 254));
        MnReturJual.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnReturJual.setForeground(new java.awt.Color(50, 50, 50));
        MnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnReturJual.setText("Retur Obat/Barang/Alkes");
        MnReturJual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnReturJual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnReturJual.setName("MnReturJual"); // NOI18N
        MnReturJual.setPreferredSize(new java.awt.Dimension(250, 25));
        MnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnReturJualActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnReturJual);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Ganti Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjab);

        MnDataObat.setBackground(new java.awt.Color(255, 255, 254));
        MnDataObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataObat.setForeground(new java.awt.Color(50, 50, 50));
        MnDataObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataObat.setText("Data Pemberian Obat");
        MnDataObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataObat.setName("MnDataObat"); // NOI18N
        MnDataObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDataObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataObat);

        MnDataResepPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnDataResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataResepPulang.setForeground(new java.awt.Color(50, 50, 50));
        MnDataResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataResepPulang.setText("Data Resep Pulang");
        MnDataResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataResepPulang.setName("MnDataResepPulang"); // NOI18N
        MnDataResepPulang.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDataResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataResepPulangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataResepPulang);

        MnCariPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnCariPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCariPeriksaLab.setForeground(new java.awt.Color(50, 50, 50));
        MnCariPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCariPeriksaLab.setText("Data Pemeriksaan Lab");
        MnCariPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCariPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCariPeriksaLab.setName("MnCariPeriksaLab"); // NOI18N
        MnCariPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 25));
        MnCariPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCariPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCariPeriksaLab);

        MnCariRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnCariRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCariRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnCariRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCariRadiologi.setText("Data Pemeriksaan Radiologi");
        MnCariRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCariRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCariRadiologi.setName("MnCariRadiologi"); // NOI18N
        MnCariRadiologi.setPreferredSize(new java.awt.Dimension(250, 25));
        MnCariRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCariRadiologiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCariRadiologi);

        MnBayi.setBackground(new java.awt.Color(255, 253, 247));
        MnBayi.setForeground(new java.awt.Color(50, 50, 50));
        MnBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBayi.setText("Tagihan Bayi");
        MnBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBayi.setIconTextGap(5);
        MnBayi.setName("MnBayi"); // NOI18N
        MnBayi.setPreferredSize(new java.awt.Dimension(250, 28));

        MnRawatJalan1.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan1.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan1.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan1.setName("MnRawatJalan1"); // NOI18N
        MnRawatJalan1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalan1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnRawatJalan1);

        MnRawatInap1.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatInap1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatInap1.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatInap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatInap1.setText("Tagihan/Tindakan Rawat Inap");
        MnRawatInap1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatInap1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatInap1.setName("MnRawatInap1"); // NOI18N
        MnRawatInap1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatInap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatInap1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnRawatInap1);

        MnInputObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnInputObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputObat1.setForeground(new java.awt.Color(50, 50, 50));
        MnInputObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputObat1.setText("Input Pemberian Obat");
        MnInputObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputObat1.setName("MnInputObat1"); // NOI18N
        MnInputObat1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnInputObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputObat1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnInputObat1);

        MnInputResepPulang1.setBackground(new java.awt.Color(255, 255, 254));
        MnInputResepPulang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputResepPulang1.setForeground(new java.awt.Color(50, 50, 50));
        MnInputResepPulang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputResepPulang1.setText("Input Resep Pulang");
        MnInputResepPulang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputResepPulang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputResepPulang1.setName("MnInputResepPulang1"); // NOI18N
        MnInputResepPulang1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnInputResepPulang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputResepPulang1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnInputResepPulang1);

        MnPeriksaRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi1.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi1.setText("Input Periksa Radiologi");
        MnPeriksaRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi1.setName("MnPeriksaRadiologi1"); // NOI18N
        MnPeriksaRadiologi1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologi1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnPeriksaRadiologi1);

        MnPeriksaLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab1.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab1.setText("Input Periksa Lab");
        MnPeriksaLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab1.setName("MnPeriksaLab1"); // NOI18N
        MnPeriksaLab1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLab1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnPeriksaLab1);

        MnTambahan1.setBackground(new java.awt.Color(255, 255, 254));
        MnTambahan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTambahan1.setForeground(new java.awt.Color(50, 50, 50));
        MnTambahan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTambahan1.setText("Tambahan Biaya");
        MnTambahan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTambahan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTambahan1.setName("MnTambahan1"); // NOI18N
        MnTambahan1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnTambahan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTambahan1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnTambahan1);

        MnPotongan1.setBackground(new java.awt.Color(255, 255, 254));
        MnPotongan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPotongan1.setForeground(new java.awt.Color(50, 50, 50));
        MnPotongan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPotongan1.setText("Potongan Biaya");
        MnPotongan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPotongan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPotongan1.setName("MnPotongan1"); // NOI18N
        MnPotongan1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPotongan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPotongan1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnPotongan1);

        MnObatLangsung1.setBackground(new java.awt.Color(255, 255, 254));
        MnObatLangsung1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung1.setForeground(new java.awt.Color(50, 50, 50));
        MnObatLangsung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung1.setText("Tagihan BHP & Obat Langsung");
        MnObatLangsung1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObatLangsung1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObatLangsung1.setName("MnObatLangsung1"); // NOI18N
        MnObatLangsung1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnObatLangsung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnObatLangsung1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnObatLangsung1);

        MnDataObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnDataObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataObat1.setForeground(new java.awt.Color(50, 50, 50));
        MnDataObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataObat1.setText("Data Pemberian Obat");
        MnDataObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataObat1.setName("MnDataObat1"); // NOI18N
        MnDataObat1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDataObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataObat1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnDataObat1);

        MnDataResepPulang1.setBackground(new java.awt.Color(255, 255, 254));
        MnDataResepPulang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataResepPulang1.setForeground(new java.awt.Color(50, 50, 50));
        MnDataResepPulang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataResepPulang1.setText("Data Resep Pulang");
        MnDataResepPulang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataResepPulang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataResepPulang1.setName("MnDataResepPulang1"); // NOI18N
        MnDataResepPulang1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDataResepPulang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataResepPulang1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnDataResepPulang1);

        MnReturJual1.setBackground(new java.awt.Color(255, 255, 254));
        MnReturJual1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnReturJual1.setForeground(new java.awt.Color(50, 50, 50));
        MnReturJual1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnReturJual1.setText("Retur Obat/Barang/Alkes");
        MnReturJual1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnReturJual1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnReturJual1.setName("MnReturJual1"); // NOI18N
        MnReturJual1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnReturJual1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnReturJual1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnReturJual1);

        MnCariPeriksaLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnCariPeriksaLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCariPeriksaLab1.setForeground(new java.awt.Color(50, 50, 50));
        MnCariPeriksaLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCariPeriksaLab1.setText("Data Pemeriksaan Lab");
        MnCariPeriksaLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCariPeriksaLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCariPeriksaLab1.setName("MnCariPeriksaLab1"); // NOI18N
        MnCariPeriksaLab1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnCariPeriksaLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCariPeriksaLab1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnCariPeriksaLab1);

        MnCariRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnCariRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCariRadiologi1.setForeground(new java.awt.Color(50, 50, 50));
        MnCariRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCariRadiologi1.setText("Data Pemeriksaan Radiologi");
        MnCariRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCariRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCariRadiologi1.setName("MnCariRadiologi1"); // NOI18N
        MnCariRadiologi1.setPreferredSize(new java.awt.Dimension(250, 25));
        MnCariRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCariRadiologi1ActionPerformed(evt);
            }
        });
        MnBayi.add(MnCariRadiologi1);

        jPopupMenu1.add(MnBayi);

        MnHapusTagihan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTagihan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihan.setText("Hapus Nota Salah");
        MnHapusTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihan.setName("MnHapusTagihan"); // NOI18N
        MnHapusTagihan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihan);

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Input Total BHP & Obat]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        TotalObat.setName("TotalObat"); // NOI18N
        TotalObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalObatKeyPressed(evt);
            }
        });
        internalFrame2.add(TotalObat);
        TotalObat.setBounds(60, 30, 180, 23);

        jLabel8.setText("Total :");
        jLabel8.setName("jLabel8"); // NOI18N
        internalFrame2.add(jLabel8);
        jLabel8.setBounds(0, 30, 57, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('3');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+3");
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

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('1');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+1");
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });
        internalFrame2.add(BtnSimpan2);
        BtnSimpan2.setBounds(255, 30, 100, 30);

        BtnBatal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal1.setMnemonic('2');
        BtnBatal1.setText("Hapus");
        BtnBatal1.setToolTipText("Alt+2");
        BtnBatal1.setName("BtnBatal1"); // NOI18N
        BtnBatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal1ActionPerformed(evt);
            }
        });
        BtnBatal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal1KeyPressed(evt);
            }
        });
        internalFrame2.add(BtnBatal1);
        BtnBatal1.setBounds(360, 30, 100, 30);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowInput3.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput3.setName("WindowInput3"); // NOI18N
        WindowInput3.setUndecorated(true);
        WindowInput3.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Tambah Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbTambahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan.setName("tbTambahan"); // NOI18N
        scrollPane1.setViewportView(tbTambahan);

        internalFrame4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label15);

        norawattambahan.setEditable(false);
        norawattambahan.setName("norawattambahan"); // NOI18N
        norawattambahan.setPreferredSize(new java.awt.Dimension(150, 23));
        norawattambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawattambahanKeyPressed(evt);
            }
        });
        panelisi1.add(norawattambahan);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnSimpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan3.setMnemonic('S');
        BtnSimpan3.setText("Simpan");
        BtnSimpan3.setToolTipText("Alt+S");
        BtnSimpan3.setName("BtnSimpan3"); // NOI18N
        BtnSimpan3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan3ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnSimpan3);

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
        panelisi1.add(BtnHapus);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowInput3.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Potongan Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbPotongan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPotongan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPotongan.setName("tbPotongan"); // NOI18N
        scrollPane2.setViewportView(tbPotongan);

        internalFrame5.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label16.setText("No.Rawat :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(label16);

        norawatpotongan.setEditable(false);
        norawatpotongan.setName("norawatpotongan"); // NOI18N
        norawatpotongan.setPreferredSize(new java.awt.Dimension(150, 23));
        norawatpotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatpotonganKeyPressed(evt);
            }
        });
        panelisi2.add(norawatpotongan);

        BtnTambahPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambahPotongan.setMnemonic('T');
        BtnTambahPotongan.setText("Tambah");
        BtnTambahPotongan.setToolTipText("Alt+T");
        BtnTambahPotongan.setName("BtnTambahPotongan"); // NOI18N
        BtnTambahPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambahPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnTambahPotongan);

        BtnSimpanPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanPotongan.setMnemonic('S');
        BtnSimpanPotongan.setText("Simpan");
        BtnSimpanPotongan.setToolTipText("Alt+S");
        BtnSimpanPotongan.setName("BtnSimpanPotongan"); // NOI18N
        BtnSimpanPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnSimpanPotongan);

        BtnHapusPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusPotongan.setMnemonic('H');
        BtnHapusPotongan.setText("Hapus");
        BtnHapusPotongan.setToolTipText("Alt+H");
        BtnHapusPotongan.setName("BtnHapusPotongan"); // NOI18N
        BtnHapusPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnHapusPotongan);

        BtnKeluarPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarPotongan.setMnemonic('K');
        BtnKeluarPotongan.setText("Keluar");
        BtnKeluarPotongan.setToolTipText("Alt+K");
        BtnKeluarPotongan.setName("BtnKeluarPotongan"); // NOI18N
        BtnKeluarPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluarPotongan);

        internalFrame5.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowInput5.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput5.setName("WindowInput5"); // NOI18N
        WindowInput5.setUndecorated(true);
        WindowInput5.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Ubah Manual Lama Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbUbahLama.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbUbahLama.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbUbahLama.setName("tbUbahLama"); // NOI18N
        scrollPane3.setViewportView(tbUbahLama);

        internalFrame6.add(scrollPane3, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("No.Rawat :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label17);

        norawatubahlama.setEditable(false);
        norawatubahlama.setName("norawatubahlama"); // NOI18N
        norawatubahlama.setPreferredSize(new java.awt.Dimension(200, 23));
        norawatubahlama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatubahlamaKeyPressed(evt);
            }
        });
        panelisi3.add(norawatubahlama);

        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label18);

        BtnSimpanUbahLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanUbahLama.setMnemonic('S');
        BtnSimpanUbahLama.setText("Simpan");
        BtnSimpanUbahLama.setToolTipText("Alt+S");
        BtnSimpanUbahLama.setName("BtnSimpanUbahLama"); // NOI18N
        BtnSimpanUbahLama.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanUbahLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanUbahLamaActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpanUbahLama);

        BtnKeluarUbahLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarUbahLama.setMnemonic('K');
        BtnKeluarUbahLama.setText("Keluar");
        BtnKeluarUbahLama.setToolTipText("Alt+K");
        BtnKeluarUbahLama.setName("BtnKeluarUbahLama"); // NOI18N
        BtnKeluarUbahLama.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarUbahLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarUbahLamaActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluarUbahLama);

        internalFrame6.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        WindowInput5.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        WindowInput6.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput6.setName("WindowInput6"); // NOI18N
        WindowInput6.setUndecorated(true);
        WindowInput6.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('P');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+P");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnCloseIn4);
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
        internalFrame7.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel17.setText("Jenis Bayar :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame7.add(jLabel17);
        jLabel17.setBounds(0, 32, 77, 23);

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame7.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        internalFrame7.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        btnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab.setMnemonic('7');
        btnPenjab.setToolTipText("ALt+7");
        btnPenjab.setName("btnPenjab"); // NOI18N
        btnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjabActionPerformed(evt);
            }
        });
        internalFrame7.add(btnPenjab);
        btnPenjab.setBounds(366, 32, 28, 23);

        WindowInput6.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        PopupPiutang.setName("PopupPiutang"); // NOI18N

        ppBersihkan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan1.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan1.setText("Bersihkan Piutang");
        ppBersihkan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan1.setName("ppBersihkan1"); // NOI18N
        ppBersihkan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkan1ActionPerformed(evt);
            }
        });
        PopupPiutang.add(ppBersihkan1);

        PopupBayar.setName("PopupBayar"); // NOI18N

        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pembayaran");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        PopupBayar.add(ppBersihkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Billing/Pembayaran Ranap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass1.setPreferredSize(new java.awt.Dimension(100, 45));
        panelGlass1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 10));

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass1.add(jLabel3);

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(150, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass1.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass1.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(330, 23));
        panelGlass1.add(TPasien);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('R');
        BtnCari.setToolTipText("Alt+R");
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
        panelGlass1.add(BtnCari);

        jLabel4.setText("Tanggal :");
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass1.add(jLabel4);

        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-04-2019 17:20:47" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelGlass1.add(DTPTgl);

        internalFrame1.add(panelGlass1, java.awt.BorderLayout.PAGE_START);

        panelGlass2.setForeground(new java.awt.Color(50, 50, 50));
        panelGlass2.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        panelGlass2.add(BtnSimpan);

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('B');
        BtnNota.setText(" Nota");
        BtnNota.setToolTipText("Alt+B");
        BtnNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelGlass2.add(BtnNota);

        BtnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnView.setMnemonic('L');
        BtnView.setText("Lihat");
        BtnView.setToolTipText("Alt+L");
        BtnView.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnView.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnView.setName("BtnView"); // NOI18N
        BtnView.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnViewActionPerformed(evt);
            }
        });
        BtnView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnViewKeyPressed(evt);
            }
        });
        panelGlass2.add(BtnView);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        panelGlass2.add(BtnKeluar);

        internalFrame1.add(panelGlass2, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBilling.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbBilling.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBilling.setComponentPopupMenu(jPopupMenu1);
        tbBilling.setName("tbBilling"); // NOI18N
        tbBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBillingMouseClicked(evt);
            }
        });
        tbBilling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBillingKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBilling);

        TabRawat.addTab("Data Tagihan", Scroll);

        panelBayar.setBorder(null);
        panelBayar.setForeground(new java.awt.Color(50, 50, 50));
        panelBayar.setName("panelBayar"); // NOI18N
        panelBayar.setPreferredSize(new java.awt.Dimension(100, 137));
        panelBayar.setLayout(null);

        TtlSemua.setEditable(false);
        TtlSemua.setText("0");
        TtlSemua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TtlSemua.setHighlighter(null);
        TtlSemua.setName("TtlSemua"); // NOI18N
        TtlSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TtlSemuaKeyPressed(evt);
            }
        });
        panelBayar.add(TtlSemua);
        TtlSemua.setBounds(340, 10, 220, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        TKembali.setName("TKembali"); // NOI18N
        panelBayar.add(TKembali);
        TKembali.setBounds(680, 380, 220, 23);

        jLabel5.setText("Bayar : Rp.");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel5);
        jLabel5.setBounds(20, 40, 90, 23);

        jLabel15.setText("Total Tagihan : Rp.");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel15);
        jLabel15.setBounds(230, 10, 110, 23);

        TagihanPPn.setEditable(false);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TagihanPPn.setHighlighter(null);
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        panelBayar.add(TagihanPPn);
        TagihanPPn.setBounds(680, 10, 220, 23);

        scrollPane4.setComponentPopupMenu(PopupBayar);
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbAkunBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAkunBayar.setToolTipText("");
        tbAkunBayar.setComponentPopupMenu(PopupBayar);
        tbAkunBayar.setName("tbAkunBayar"); // NOI18N
        tbAkunBayar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAkunBayarPropertyChange(evt);
            }
        });
        tbAkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAkunBayarKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbAkunBayar);

        panelBayar.add(scrollPane4);
        scrollPane4.setBounds(110, 65, 790, 140);

        jLabel6.setText("Kembali : Rp.");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel6);
        jLabel6.setBounds(590, 380, 90, 23);

        scrollPane5.setComponentPopupMenu(PopupPiutang);
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbAkunPiutang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAkunPiutang.setToolTipText("");
        tbAkunPiutang.setComponentPopupMenu(PopupPiutang);
        tbAkunPiutang.setName("tbAkunPiutang"); // NOI18N
        tbAkunPiutang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAkunPiutangMouseClicked(evt);
            }
        });
        tbAkunPiutang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAkunPiutangPropertyChange(evt);
            }
        });
        tbAkunPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAkunPiutangKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(tbAkunPiutang);

        panelBayar.add(scrollPane5);
        scrollPane5.setBounds(110, 235, 790, 140);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelBayar.add(TCari);
        TCari.setBounds(110, 40, 762, 23);

        BtnCariBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariBayar.setMnemonic('3');
        BtnCariBayar.setToolTipText("Alt+3");
        BtnCariBayar.setName("BtnCariBayar"); // NOI18N
        BtnCariBayar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariBayarActionPerformed(evt);
            }
        });
        BtnCariBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariBayarKeyPressed(evt);
            }
        });
        panelBayar.add(BtnCariBayar);
        BtnCariBayar.setBounds(875, 40, 25, 23);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelBayar.add(TCari1);
        TCari1.setBounds(110, 210, 762, 23);

        btnCariPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCariPiutang.setMnemonic('3');
        btnCariPiutang.setToolTipText("Alt+3");
        btnCariPiutang.setName("btnCariPiutang"); // NOI18N
        btnCariPiutang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCariPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPiutangActionPerformed(evt);
            }
        });
        btnCariPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariPiutangKeyPressed(evt);
            }
        });
        panelBayar.add(btnCariPiutang);
        btnCariPiutang.setBounds(875, 210, 25, 23);

        jLabel13.setText("Deposit : Rp.");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel13);
        jLabel13.setBounds(0, 380, 110, 23);

        Deposit.setEditable(false);
        Deposit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        Deposit.setName("Deposit"); // NOI18N
        panelBayar.add(Deposit);
        Deposit.setBounds(110, 380, 220, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        panelBayar.add(BtnSeek2);
        BtnSeek2.setBounds(332, 380, 25, 23);

        ChkPiutang.setText("Piutang : Rp.");
        ChkPiutang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkPiutang.setName("ChkPiutang"); // NOI18N
        ChkPiutang.setOpaque(false);
        ChkPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPiutangActionPerformed(evt);
            }
        });
        panelBayar.add(ChkPiutang);
        ChkPiutang.setBounds(0, 210, 110, 23);

        chkRalan.setSelected(true);
        chkRalan.setText("Rawat Jalan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelBayar.add(chkRalan);
        chkRalan.setBounds(0, 10, 110, 23);

        chkRanap.setSelected(true);
        chkRanap.setText("Rawat Inap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelBayar.add(chkRanap);
        chkRanap.setBounds(120, 10, 95, 23);

        jLabel18.setText("Tagihan + PPN : Rp.");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel18);
        jLabel18.setBounds(570, 10, 110, 23);

        TabRawat.addTab("Pembayaran", panelBayar);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(akses.getbilling_ranap()==true){
            try {
                pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
                try {
                    pscekbilling.setString(1,TNoRw.getText());
                    rscekbilling=pscekbilling.executeQuery();
                    if(rscekbilling.next()){
                        i=rscekbilling.getInt(1);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rscekbilling!=null){
                        rscekbilling.close();
                    }
                    if(pscekbilling!=null){
                        pscekbilling.close();
                    }
                }
                    

                if(i<=0){
                    int jawab=JOptionPane.showConfirmDialog(null, "Data pembayaran belum tersimpan, apa anda mau menyimpannya...????","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if(jawab==JOptionPane.YES_OPTION){
                        BtnSimpanActionPerformed(evt);
                        dispose();
                    }else{
                        WindowInput.dispose();
                        WindowInput3.dispose();
                        WindowInput4.dispose();
                        WindowInput5.dispose();
                        WindowInput6.dispose();
                        dispose(); 
                    }                
                }else if(i>0){
                    WindowInput.dispose();
                    WindowInput3.dispose();
                    WindowInput4.dispose();
                    WindowInput5.dispose();
                    WindowInput6.dispose();
                    dispose();                
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            WindowInput.dispose();
            WindowInput3.dispose();
            WindowInput4.dispose();
            WindowInput5.dispose();
            WindowInput6.dispose();
            dispose();  
        }
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnView,BtnSimpan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnView);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnViewActionPerformed
            Object[] options = {"Tagihan Masuk", "Piutang Pasien"};
            
            String input;
            int pilih = 0;
            try{
                input = (String) JOptionPane.showInputDialog(null,"Silahkan pilih yang mau ditampilkan!","Keuangan",JOptionPane.QUESTION_MESSAGE,null,options,"Nota 1");
                switch (input) {
                    case "Tagihan Masuk":
                        pilih=1;
                        break;
                    case "Piutang Pasien":
                        pilih=2;
                        break;
                }
            }catch(Exception e){
                pilih=0;
            }        
            
            if(pilih>0){
                if(pilih==1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtBiaya billing=new DlgLhtBiaya(null,false);
                    billing.setSize(this.getWidth()-20,this.getHeight()-20);
                    billing.setLocationRelativeTo(this);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else if(pilih==2){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtPiutang billing=new DlgLhtPiutang(null,false);
                    billing.tampil();        
                    billing.setSize(this.getWidth()-20,this.getHeight()-20);
                    billing.setLocationRelativeTo(this);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }   
    }//GEN-LAST:event_BtnViewActionPerformed

    private void BtnViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnViewKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnViewActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnViewKeyPressed

private void tbBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBillingMouseClicked
       if(tabModeRwJlDr.getRowCount()!=0){
            if(evt.getClickCount()==1){
                int kolom=tbBilling.getSelectedColumn();
                if(kolom==1){
                    try {
                        akses.setform("DLgBilingRanap");
                        if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Rincian Biaya")){
                            if(akses.gettindakan_ranap()==true){
                                rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true,TPasien.getText());
                                rawatinap.perawatan.setPetugas("","","","","","", "","","","","","","","");
                                rawatinap.perawatan.isCek();
                                rawatinap.perawatan.tampil();
                                rawatinap.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                                rawatinap.perawatan.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Resep Pulang")){
                            if(akses.getresep_pulang()==true){
                                reseppulang.inputresep.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                                reseppulang.inputresep.isCek();
                                reseppulang.inputresep.tampil();
                                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                                reseppulang.inputresep.setVisible(true); 
                            }                                               
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Obat & BHP")){
                            if(akses.getberi_obat()==true){
                                beriobat.dlgobt.setNoRm(TNoRw.getText(),DTPTgl.getDate(),"00","00","00",true);
                                beriobat.dlgobt.isCek();
                                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                beriobat.dlgobt.tampil();
                                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                                beriobat.dlgobt.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Tambahan Biaya")){
                            if(akses.gettambahan_biaya()==true){
                                MnTambahanActionPerformed(null);
                            }
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Potongan Biaya")){
                            if(akses.getpotongan_biaya()==true){
                                MnPotonganActionPerformed(null);
                            }
                        }
                    } catch (Exception e) {
                        akses.setform("DLgBilingRanap");
                        if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Rincian Biaya")){
                            if(akses.gettindakan_ranap()==true){
                                rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true,TPasien.getText());
                                rawatinap.perawatan.setPetugas("","","","","","","","","","","","","","");
                                rawatinap.perawatan.isCek();
                                rawatinap.perawatan.tampil();
                                rawatinap.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                                rawatinap.perawatan.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Resep Pulang")){
                            if(akses.getresep_pulang()==true){
                                reseppulang.inputresep.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                                reseppulang.inputresep.isCek();
                                reseppulang.inputresep.tampil();
                                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                                reseppulang.inputresep.setVisible(true); 
                            }                                               
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Obat & BHP")){
                            if(akses.getberi_obat()==true){
                                beriobat.dlgobt.setNoRm(TNoRw.getText(),DTPTgl.getDate(),"00","00","00",true);
                                beriobat.dlgobt.isCek();
                                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                beriobat.dlgobt.tampil();
                                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                                beriobat.dlgobt.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Tambahan Biaya")){
                            if(akses.gettambahan_biaya()==true){
                                MnTambahanActionPerformed(null);
                            }
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Potongan Biaya")){
                            if(akses.getpotongan_biaya()==true){
                                MnPotonganActionPerformed(null);
                            }
                        }
                    }
                        
                }
            }
        }
}//GEN-LAST:event_tbBillingMouseClicked

private void tbBillingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBillingKeyPressed
        if(tbBilling.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                i=tbBilling.getSelectedColumn();
                if(i==6){  
                    if(akses.getbilling_ranap()==true){
                        try {
                            switch (tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()) {
                                case "Laborat":
                                    JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan/potongan laborat gunakan pada Tambahan/Potongan Biaya");
                                    isRawat();
                                    break;
                                case "Operasi":
                                    JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan/potongan Operasi gunakan pada Tambahan/Potongan Biaya");
                                    isRawat();
                                    break;
                                case "Obat":
                                    JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan obat hanya bisa diisi embalase.\nGunakan Tambahan Biaya jika ingin tambahan lain");
                                    isRawat();
                                    break;
                                default:
                                    //if(chkPj1.isVisible()==true){
                                    //    JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan gunakan pada Tambahan Biaya");
                                    //    isRawat();
                                    //}else{
                                        try{
                                            if(Double.parseDouble(tbBilling.getValueAt(tbBilling.getSelectedRow(),6).toString())!=0){
                                                Sequel.queryu2("delete from temporary_tambahan_potongan where no_rawat=? and nama_tambahan=? and status=?",3,new String[]{
                                                    TNoRw.getText(),tbBilling.getValueAt(tbBilling.getSelectedRow(),2).toString(),tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()
                                                });
                                                Sequel.menyimpan("temporary_tambahan_potongan","?,?,?,?",4,new String[]{
                                                    TNoRw.getText(),tbBilling.getValueAt(tbBilling.getSelectedRow(),2).toString(),tbBilling.getValueAt(tbBilling.getSelectedRow(),6).toString(),
                                                    tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()
                                                });
                                            }else{
                                                Sequel.queryu2("delete from temporary_tambahan_potongan where no_rawat=? and nama_tambahan=? and status=?",3,new String[]{
                                                    TNoRw.getText(),tbBilling.getValueAt(tbBilling.getSelectedRow(),2).toString(),tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()
                                                });
                                                tbBilling.setValueAt(0,tbBilling.getSelectedRow(),0);
                                            }                                    
                                        }catch(Exception e){
                                            Sequel.queryu2("delete from temporary_tambahan_potongan where no_rawat=? and nama_tambahan=? and status=?",3,new String[]{
                                                TNoRw.getText(),tbBilling.getValueAt(tbBilling.getSelectedRow(),2).toString(),tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()
                                            });
                                            tbBilling.setValueAt(0,tbBilling.getSelectedRow(),0);
                                        }     

                                        isRawat();
                                    //}                                        
                                    break;
                            }
                        } catch (Exception e) {
                            isRawat();
                        }                            
                    }                        
                }                    
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                int kolom=tbBilling.getSelectedColumn();
                if(kolom==1){
                    try {
                        akses.setform("DLgBilingRanap");
                        if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Rincian Biaya")){
                            if(akses.gettindakan_ranap()==true){
                                rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true,TPasien.getText());
                                rawatinap.perawatan.setPetugas("","","","","","", "","","","","","","","");
                                rawatinap.perawatan.isCek();
                                rawatinap.perawatan.tampil();
                                rawatinap.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                                rawatinap.perawatan.setVisible(true);                            
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Resep Pulang")){
                            if(akses.getresep_pulang()==true){
                                reseppulang.inputresep.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                                reseppulang.inputresep.isCek();
                                reseppulang.inputresep.tampil();
                                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                                reseppulang.inputresep.setVisible(true);
                            }                                                
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Obat & BHP")){
                            if(akses.getberi_obat()==true){
                                beriobat.dlgobt.setNoRm(TNoRw.getText(),DTPTgl.getDate(),"00","00","00",true);
                                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                beriobat.dlgobt.isCek();
                                beriobat.dlgobt.tampil();
                                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                                beriobat.dlgobt.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Tambahan Biaya")){
                            if(akses.gettambahan_biaya()==true){
                                MnTambahanActionPerformed(null);
                            }
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Potongan Biaya")){
                            if(akses.getpotongan_biaya()==true){
                                MnPotonganActionPerformed(null);
                            }
                        }
                    } catch (Exception e) {
                        akses.setform("DLgBilingRanap");
                        if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Rincian Biaya")){
                            if(akses.gettindakan_ranap()==true){
                                rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true,TPasien.getText());
                                rawatinap.perawatan.setPetugas("","","","","","", "","","","","","","","");
                                rawatinap.perawatan.isCek();
                                rawatinap.perawatan.tampil();
                                rawatinap.perawatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                                rawatinap.perawatan.setVisible(true);                            
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Resep Pulang")){
                            if(akses.getresep_pulang()==true){
                                reseppulang.inputresep.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                                reseppulang.inputresep.isCek();
                                reseppulang.inputresep.tampil();
                                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                                reseppulang.inputresep.setVisible(true);
                            }                                                
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Obat & BHP")){
                            if(akses.getberi_obat()==true){
                                beriobat.dlgobt.setNoRm(TNoRw.getText(),DTPTgl.getDate(),"00","00","00",true);
                                beriobat.dlgobt.isCek();
                                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                beriobat.dlgobt.tampil();
                                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                                beriobat.dlgobt.setVisible(true);
                            }                        
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Tambahan Biaya")){
                            if(akses.gettambahan_biaya()==true){
                                MnTambahanActionPerformed(null);
                            }
                        }else if(tbBilling.getValueAt(tbBilling.getSelectedRow(), kolom).toString().contains("Potongan Biaya")){
                            if(akses.getpotongan_biaya()==true){
                                MnPotonganActionPerformed(null);
                            }
                        }
                    }                        
                }
            }
        }
}//GEN-LAST:event_tbBillingKeyPressed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
            dlgrwjl.isCek();
            dlgrwjl.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgrwjl.setLocationRelativeTo(internalFrame1);
            dlgrwjl.SetPoli("-");
            dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
            dlgrwjl.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());  
            dlgrwjl.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatInapActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
           akses.setform("DLgBilingRanap");
            rawatinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            rawatinap.setLocationRelativeTo(internalFrame1);
            rawatinap.isCek();
            rawatinap.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());    
            rawatinap.tampilDr();
            rawatinap.setVisible(true);
        }
}//GEN-LAST:event_MnRawatInapActionPerformed

private void MnInputResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputResepPulangActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                BtnNota.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                reseppulang.inputresep.isCek();
                reseppulang.inputresep.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                        Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                reseppulang.inputresep.tampil();
                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                reseppulang.inputresep.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnInputResepPulangActionPerformed

private void MnInputObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                BtnNota.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                beriobat.dlgobt.setNoRm(TNoRw.getText(),DTPTgl.getDate(),"00","00","00",true);
                beriobat.dlgobt.isCek();
                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                beriobat.dlgobt.tampil();
                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                beriobat.dlgobt.setVisible(true);         
            }                 
        }
}//GEN-LAST:event_MnInputObatActionPerformed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TNoRw,BtnSimpan);
}//GEN-LAST:event_DTPTglKeyPressed

private void MnHapusTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanActionPerformed
    try {
        i=0;
        pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
	try{
            pscekbilling.setString(1,TNoRw.getText());
            rscekbilling=pscekbilling.executeQuery();
            if(rscekbilling.next()){
                i=rscekbilling.getInt(1);
            }
        }catch (Exception e) {
            i=0;
            System.out.println("Notifikasi : "+e);
        } finally{
            if(rscekbilling != null){
                rscekbilling.close();
            }
            if(pscekbilling != null){
                pscekbilling.close();
            }
        }
        if(i>0){
            
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"status_bayar='Belum Bayar'");
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            Sequel.queryu2("delete from tampjurnal");
            
            if((-1*ttlPotongan)>0){
                Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','Potongan Ranap','0','"+(-1*ttlPotongan)+"'","Rekening");    
            }

            if((-1*ttlRetur_Obat)>0){
                Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','Retur Obat Ranap','0','"+(-1*ttlRetur_Obat)+"'","Rekening");    
            }

            if(ttlRegistrasi>0){
                Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','Registrasi Ranap','"+ttlRegistrasi+"','0'","Rekening");    
            }

            if(ttlTambahan>0){
                Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','Tambahan Ranap','"+ttlTambahan+"','0'","Rekening");    
            }

            if(ttlResep_Pulang>0){
                Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','Resep Pulang Ranap','"+ttlResep_Pulang+"','0'","Rekening");    
            }

            if(ttlKamar>0){
                Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','Kamar Inap','"+ttlKamar+"','0'","Rekening");    
            }

            if(ttlHarian>0){
                Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','Harian Ranap','"+ttlHarian+"','0'","Rekening");    
            }
            
            if((ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)>0){
                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Tindakan Ranap','"+(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)+"','0'", 
                                 "debet=debet+"+(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis),"kd_rek='"+Tindakan_Ranap+"'");                            
            }

            if(ttlLaborat>0){
                Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Laborat','"+ttlLaborat+"','0'", 
                                 "debet=debet+"+ttlLaborat,"kd_rek='"+Laborat_Ranap+"'");                    
            }

            if(ttlRadiologi>0){
                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','Radiologi','"+ttlRadiologi+"','0'", 
                                 "debet=debet+"+ttlRadiologi,"kd_rek='"+Radiologi_Ranap+"'");     
            }

            if(ttlObat>0){
                Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','Obat','"+ttlObat+"','0'", 
                                 "debet=debet+"+ttlObat,"kd_rek='"+Obat_Ranap+"'");      
            }

            if(ttlOperasi>0){
                Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Operasi','"+ttlOperasi+"','0'", 
                                 "debet=debet+"+ttlOperasi,"kd_rek='"+Operasi_Ranap+"'");  
            }
            
            if(uangdeposit>0){
                Sequel.menyimpan("tampjurnal","'"+Uang_Muka_Ranap+"','Kontra Akun Uang Muka','0','"+uangdeposit+"'",
                                 "kredit=kredit+"+uangdeposit,"kd_rek='"+Uang_Muka_Ranap+"'");    
            }

            if(ttlService>0){
                Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','Biaya Service Ranap','"+ttlService+"','0'","Rekening");    
            }
            
            psakunbayar=koneksi.prepareStatement(
                     "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_inap.besar_bayar,"+
                     "akun_bayar.ppn,detail_nota_inap.besarppn from akun_bayar inner join detail_nota_inap "+
                     "on akun_bayar.nama_bayar=detail_nota_inap.nama_bayar where detail_nota_inap.no_rawat=? order by nama_bayar");
             try{
                 psakunbayar.setString(1,TNoRw.getText());
                 rsakunbayar=psakunbayar.executeQuery();
                 while(rsakunbayar.next()){
                     Sequel.menyimpan("tampjurnal","'"+rsakunbayar.getString("kd_rek")+"','"+rsakunbayar.getString("nama_bayar")+"','0','"+rsakunbayar.getString("besar_bayar")+"'",
                                 "kredit=kredit+"+rsakunbayar.getString("besar_bayar"),"kd_rek='"+rsakunbayar.getString("kd_rek")+"'");   
                 } 
             }catch (Exception e) {
                 System.out.println("Notifikasi Akun Bayar Tersimpan : "+e);
             } finally{
                 if(rsakunbayar != null){
                     rsakunbayar.close();
                 } 
                 if(psakunbayar != null){
                     psakunbayar.close();
                 } 
             }
             
             psakunpiutang=koneksi.prepareStatement(
                     "select akun_piutang.nama_bayar,akun_piutang.kd_rek,akun_piutang.kd_pj, "+
                     "detail_piutang_pasien.totalpiutang,date_format(detail_piutang_pasien.tgltempo,'%d/%m/%Y') from "+
                     "akun_piutang inner join detail_piutang_pasien on akun_piutang.nama_bayar=detail_piutang_pasien.nama_bayar "+
                     "where detail_piutang_pasien.no_rawat=? order by nama_bayar");
             try{
                 psakunpiutang.setString(1,TNoRw.getText());
                 rsakunpiutang=psakunpiutang.executeQuery();
                 while(rsakunpiutang.next()){      
                     Sequel.menyimpan("tampjurnal","'"+rsakunpiutang.getString("kd_rek")+"','"+rsakunpiutang.getString("nama_bayar")+"','0','"+rsakunpiutang.getString("totalpiutang")+"'",
                                      "kredit=kredit+"+rsakunpiutang.getString("totalpiutang"),"kd_rek='"+rsakunpiutang.getString("kd_rek")+"'");  
                 } 
             }catch (Exception e) {
                 System.out.println("Notifikasi Akun Piutang Tersimpan : "+e);
             } finally{
                 if(rsakunpiutang != null){
                     rsakunpiutang.close();
                 } 
                 if(psakunpiutang != null){
                     psakunpiutang.close();
                 } 
             }

            if(piutang>0){
                jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN PIUTANG PASIEN RAWAT INAP, DIBATALKAN OLEH "+akses.getkode());    
            }else if(piutang<=0){
                jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN PEMBAYARAN PASIEN RAWAT INAP, DIBATALKAN OLEH "+akses.getkode());    
            }
            
            Sequel.queryu2("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu2("delete from detail_piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu2("delete from nota_inap where no_rawat='"+TNoRw.getText()+"'");            
            Sequel.queryu2("delete from detail_nota_inap where no_rawat='"+TNoRw.getText()+"'");
            Valid.hapusTable(tabModeRwJlDr,TNoRw,"billing","no_rawat");
            Valid.hapusTable(tabModeRwJlDr,TNoRw,"tagihan_sadewa","no_nota");
           this.setCursor(Cursor.getDefaultCursor());
           
           JOptionPane.showMessageDialog(rootPane,"Proses hapus data Nota Salah selesai..!!");
           Valid.tabelKosong(tabModeAkunBayar);
           Valid.tabelKosong(tabModeAkunPiutang);
           isRawat();
        }else if(i<=0){
            JOptionPane.showMessageDialog(rootPane,"Data belum pernah disimpan/diverifikasi.\n"+
                    "Tidak perlu ada penghapusan data salah..!!");
        }
    } catch (Exception e) {
        System.out.println("Notifikasi : "+e);
    }
}//GEN-LAST:event_MnHapusTagihanActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            akses.setform("DLgBilingRanap");
            periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            periksalab.setLocationRelativeTo(internalFrame1);
            periksalab.emptTeks();
            periksalab.setNoRm(TNoRw.getText(),"Ranap");  
            periksalab.isCek();
            periksalab.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnObatLangsungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsungActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
        BtnNota.requestFocus();
    }else{
        TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));  
        WindowInput.setSize(590,80);
        WindowInput.setLocationRelativeTo(internalFrame1);
        WindowInput.setVisible(true);
    }        
}//GEN-LAST:event_MnObatLangsungActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnSimpan);
}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal1, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowInput.setVisible(false);
            isRawat();
            isKembali();
        }
}//GEN-LAST:event_BtnSimpan2ActionPerformed

private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        Valid.pindah(evt,TotalObat,BtnBatal1);
}//GEN-LAST:event_BtnSimpan2KeyPressed

private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat=? ",TNoRw.getText());
            WindowInput.setVisible(false);
            isRawat();
            isKembali();
        }
}//GEN-LAST:event_BtnBatal1ActionPerformed

private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatal1ActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatal1KeyPressed

private void norawattambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawattambahanKeyPressed
    Valid.pindah(evt, BtnKeluar, BtnSimpan);
}//GEN-LAST:event_norawattambahanKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
    tabModeTambahan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan3ActionPerformed
    if(norawattambahan.getText().trim().equals("")||(tbTambahan.getRowCount()<=0)){
        Valid.textKosong(norawattambahan,"Data");
    }else{
        for(int r=0;r<tbTambahan.getRowCount();r++){
            if(Valid.SetAngka(tbTambahan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("tambahan_biaya","'"+norawattambahan.getText()+"','"+tbTambahan.getValueAt(r,0).toString()+
                        "','"+tbTambahan.getValueAt(r,1).toString()+"'","Tambahan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowInput3.dispose();
    }
}//GEN-LAST:event_BtnSimpan3ActionPerformed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    Sequel.queryu("delete from tambahan_biaya where no_rawat='"+norawattambahan.getText()+
            "' and nama_biaya='"+tbTambahan.getValueAt(tbTambahan.getSelectedRow(),0).toString() +"'");
    tabModeTambahan.removeRow(tbTambahan.getSelectedRow());
    isRawat();
    isKembali();
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
    WindowInput3.dispose();
}//GEN-LAST:event_BtnKeluar1ActionPerformed

private void MnTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTambahanActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
        BtnNota.requestFocus();
    }else{
        norawattambahan.setText(TNoRw.getText());
        tampilTambahan(norawattambahan.getText());
        WindowInput3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        WindowInput3.setLocationRelativeTo(internalFrame1);
        WindowInput3.setVisible(true);
    }        
}//GEN-LAST:event_MnTambahanActionPerformed

private void MnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnReturJualActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
        BtnNota.requestFocus();
    }else{
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        akses.setform("DlgBilingRanap");
        DlgReturJual returjual=new DlgReturJual(null,false);     
        returjual.emptTeks();  
        returjual.isCek(); 
        returjual.setPasien(TNoRM.getText(),TNoRw.getText());
        returjual.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        returjual.setLocationRelativeTo(internalFrame1);
        returjual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }           
}//GEN-LAST:event_MnReturJualActionPerformed

private void norawatpotonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatpotonganKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_norawatpotonganKeyPressed

private void BtnTambahPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPotonganActionPerformed
  tabModePotongan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahPotonganActionPerformed

private void BtnSimpanPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanPotonganActionPerformed
   if(norawatpotongan.getText().trim().equals("")||(tbPotongan.getRowCount()<=0)){
        Valid.textKosong(norawatpotongan,"Data");
    }else{
        for(int r=0;r<tbPotongan.getRowCount();r++){
            if(Valid.SetAngka(tbPotongan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("pengurangan_biaya","'"+norawatpotongan.getText()+"','"+tbPotongan.getValueAt(r,0).toString()+
                        "','"+tbPotongan.getValueAt(r,1).toString()+"'","Potongan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowInput4.dispose();
    }
}//GEN-LAST:event_BtnSimpanPotonganActionPerformed

private void BtnHapusPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusPotonganActionPerformed
    Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+norawatpotongan.getText()+
            "' and nama_pengurangan='"+tbPotongan.getValueAt(tbPotongan.getSelectedRow(),0).toString() +"'");
    tabModePotongan.removeRow(tbPotongan.getSelectedRow());
    isRawat();
    isKembali();
}//GEN-LAST:event_BtnHapusPotonganActionPerformed

private void BtnKeluarPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarPotonganActionPerformed
  WindowInput4.dispose();
}//GEN-LAST:event_BtnKeluarPotonganActionPerformed

private void MnPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPotonganActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
        BtnNota.requestFocus();
    }else{
        norawatpotongan.setText(TNoRw.getText());
        tampilPotongan(norawatpotongan.getText());
        WindowInput4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        WindowInput4.setLocationRelativeTo(internalFrame1);
        WindowInput4.setVisible(true);
    }        
}//GEN-LAST:event_MnPotonganActionPerformed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        isRawat();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        try {
            pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
            try {
                pscekbilling.setString(1,TNoRw.getText());
                rscekbilling=pscekbilling.executeQuery();
                if(rscekbilling.next()){
                    i=rscekbilling.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscekbilling!=null){
                    rscekbilling.close();
                }
                if(pscekbilling!=null){
                    pscekbilling.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }         
        
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(i>0){
            JOptionPane.showMessageDialog(null,"Maaf, data tagihan pasien dengan No.Rawat tersebut sudah pernah disimpan...!!!");
        }else if(i==0){ 
            if(piutang<=0){
                if(kekurangan<0){
                    JOptionPane.showMessageDialog(null,"Maaf, pembayaran pasien masih kurang ...!!!");
                }else if(kekurangan>0){
                    if(countbayar>1){
                        JOptionPane.showMessageDialog(null,"Maaf, kembali harus bernilai 0 untuk cara bayar lebih dari 1...!!!");
                    }else{
                        if(ChkPiutang.isSelected()==true){
                            JOptionPane.showMessageDialog(null,"Maaf, matikan centang di piutang ...!!!");
                        }else{
                            isSimpan();
                        }                            
                    }                        
                }else if(kekurangan==0){
                    if(ChkPiutang.isSelected()==true){
                        JOptionPane.showMessageDialog(null,"Maaf, matikan centang di piutang ...!!!");
                    }else{
                        isSimpan();
                    } 
                }                
            }else if(piutang>=1){
                if(ChkPiutang.isSelected()==true){
                    if(kekurangan<0){
                        JOptionPane.showMessageDialog(null,"Maaf, piutang belum genap. Silahkan isi di jumlah piutang ...!!!");
                    }else if(kekurangan>0){
                        JOptionPane.showMessageDialog(null,"Maaf, terjadi kelebihan piutang ...!!!");
                    }else{
                        isSimpan();
                    }    
                }else if(ChkPiutang.isSelected()==false){
                    JOptionPane.showMessageDialog(rootPane,"Silahkan centang terlebih dahulu pada pilihan piutang...!!");
                }                
            }
        }           
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void norawatubahlamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatubahlamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_norawatubahlamaKeyPressed

    private void BtnSimpanUbahLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanUbahLamaActionPerformed
        if(norawatubahlama.getText().trim().equals("")||(tbUbahLama.getRowCount()<=0)){
            Valid.textKosong(norawatubahlama,"Data");
        }else{
            
            for(int r=0;r<tbUbahLama.getRowCount();r++){
                if(Valid.SetAngka(tbUbahLama.getValueAt(r,6).toString())>-1){                    
                    Sequel.mengedit("kamar_inap","no_rawat='"+norawatubahlama.getText()+"' and kd_kamar='"+tbUbahLama.getValueAt(r,0)+"'",
                           "tgl_keluar='"+tbUbahLama.getValueAt(r,4).toString()+"',jam_keluar='"+tbUbahLama.getValueAt(r,5).toString()+"',"+
                           "tgl_masuk='"+tbUbahLama.getValueAt(r,2).toString()+"',jam_masuk='"+tbUbahLama.getValueAt(r,3).toString()+"',"+
                           "lama='"+tbUbahLama.getValueAt(r,6).toString()+"',"+
                           "ttl_biaya="+tbUbahLama.getValueAt(r,6).toString()+"*trf_kamar");                       
                }
            }
            
                       
            isRawat();
            isKembali();
            WindowInput5.dispose();
        }
    }//GEN-LAST:event_BtnSimpanUbahLamaActionPerformed

    private void BtnKeluarUbahLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarUbahLamaActionPerformed
        WindowInput5.dispose();
    }//GEN-LAST:event_BtnKeluarUbahLamaActionPerformed

    private void MnUbahLamaInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahLamaInapActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            BtnNota.requestFocus();
        }else{
            norawatubahlama.setText(TNoRw.getText());
            tampilUbahLama(norawatubahlama.getText());
            WindowInput5.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            WindowInput5.setLocationRelativeTo(internalFrame1);
            WindowInput5.setAlwaysOnTop(false);
            WindowInput5.setVisible(true);
        }            
    }//GEN-LAST:event_MnUbahLamaInapActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        WindowInput6.setSize(630,80);
        WindowInput6.setLocationRelativeTo(internalFrame1);
        WindowInput6.setAlwaysOnTop(false);
        WindowInput6.setVisible(true);
    }//GEN-LAST:event_MnPenjabActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowInput6.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(kdpenjab.getText().trim().equals("")||nmpenjab.getText().trim().equals("")){
            Valid.textKosong(kdpenjab,"Jenis Bayar");
        }else{
            Sequel.mengedit("reg_periksa","no_rawat=?"," kd_pj=?",2,new String[]{kdpenjab.getText(),TNoRw.getText()});      
            isRawat();
            WindowInput6.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
        akses.setform("DLgBilingRanap");
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_btnPenjabActionPerformed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
            akses.setform("DLgBilingRanap");
            deposit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            deposit.setLocationRelativeTo(internalFrame1);
            deposit.isCek();
            deposit.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate());
            deposit.setAlwaysOnTop(false);
            deposit.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void MnDataObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataObatActionPerformed
        akses.setform("DLgBilingRanap");
        beriobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        beriobat.setLocationRelativeTo(internalFrame1); 
        beriobat.isCek();
        beriobat.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date(),"ranap"); 
        beriobat.setVisible(true);
    }//GEN-LAST:event_MnDataObatActionPerformed

    private void MnDataResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataResepPulangActionPerformed
        akses.setform("DLgBilingRanap");
        reseppulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        reseppulang.setLocationRelativeTo(internalFrame1); 
        reseppulang.isCek();
        reseppulang.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date()); 
        reseppulang.setVisible(true);
    }//GEN-LAST:event_MnDataResepPulangActionPerformed

    private void MnCariPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariPeriksaLabActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            DlgCariPeriksaLab cperiksalab=new DlgCariPeriksaLab(null,false);
            cperiksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            cperiksalab.setLocationRelativeTo(internalFrame1);
            cperiksalab.SetNoRw(TNoRw.getText());
            cperiksalab.setAlwaysOnTop(false);
            cperiksalab.setVisible(true);
        }
    }//GEN-LAST:event_MnCariPeriksaLabActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            akses.setform("DLgBilingRanap");
            periksarad.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            periksarad.setLocationRelativeTo(internalFrame1);
            periksarad.emptTeks();
            periksarad.setNoRm(TNoRw.getText(),"Ranap");
            periksarad.tampil();
            periksarad.isCek();
            periksarad.setAlwaysOnTop(false);
            periksarad.setVisible(true);
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void MnCariRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariRadiologiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            DlgCariPeriksaRadiologi cperiksalab=new DlgCariPeriksaRadiologi(null,false);
            cperiksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            cperiksalab.setLocationRelativeTo(internalFrame1);
            cperiksalab.SetNoRw(TNoRw.getText());
            cperiksalab.setAlwaysOnTop(false);
            cperiksalab.setVisible(true);
        }
    }//GEN-LAST:event_MnCariRadiologiActionPerformed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        i=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(tbBilling.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tbBilling.getRowCount()!=0){
            try{
                koneksi.setAutoCommit(false);
                Sequel.queryu2("truncate table temporary_bayar_ranap"); 
                for(i=0;i<tbBilling.getRowCount();i++){  
                    if(tbBilling.getValueAt(i,0).toString().equals("true")){
                        biaya="";
                        try {
                            biaya=Valid.SetAngka(Double.parseDouble(tbBilling.getValueAt(i,4).toString())); 
                        } catch (Exception e) {
                            biaya="";
                        }                            
                        tambahan="";
                        try {
                            tambahan=Valid.SetAngka(Double.parseDouble(tbBilling.getValueAt(i,6).toString())); 
                        } catch (Exception e) {
                            tambahan="";
                        }
                        totals="";
                        try {
                            totals=Valid.SetAngka(Double.parseDouble(tbBilling.getValueAt(i,7).toString())); 
                        } catch (Exception e) {
                            totals="";
                        }
                        pstemporary=koneksi.prepareStatement(sqlpstemporary);
                        try {
                            pstemporary.setString(1,tbBilling.getValueAt(i,1).toString().replaceAll("'","`"));
                            pstemporary.setString(2,tbBilling.getValueAt(i,2).toString().replaceAll("'","`"));
                            pstemporary.setString(3,tbBilling.getValueAt(i,3).toString().replaceAll("'","`"));
                            pstemporary.setString(4,biaya);
                            try {
                                pstemporary.setString(5,tbBilling.getValueAt(i,5).toString().replaceAll("'","`"));
                            } catch (Exception e) {
                                pstemporary.setString(5,"");
                            }                            
                            pstemporary.setString(6,tambahan);
                            pstemporary.setString(7,totals);
                            try {
                                pstemporary.setString(8,tbBilling.getValueAt(i,8).toString().replaceAll("'","`"));  
                            } catch (Exception e) {
                                pstemporary.setString(8,""); 
                            }                              
                            pstemporary.executeUpdate();                         
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(pstemporary!=null){
                                pstemporary.close();
                            }
                        }                            
                    }                
                }                
                
                if(ChkPiutang.isSelected()==false){                        
                    Sequel.menyimpan("temporary_bayar_ranap","'0','','','','','','','','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','TOTAL TAGIHAN',':','','','','','<b>"+TtlSemua.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','PPN',':','','','','','<b>"+Valid.SetAngka(besarppn)+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','TAGIHAN+PPN',':','','','','','<b>"+TagihanPPn.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','','','','','','','','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','DEPOSIT',':','','','','','<b>"+Deposit.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','BAYAR',':','','','','','<b>"+Valid.SetAngka(bayar)+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','KEMBALI',':','','','','','<b>"+TKembali.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                }else if(ChkPiutang.isSelected()==true){                                            
                    Sequel.menyimpan("temporary_bayar_ranap","'0','','','','','','','','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','TOTAL TAGIHAN',':','','','','','<b>"+TtlSemua.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','PPN',':','','','','','<b>"+Valid.SetAngka(besarppn)+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','TAGIHAN + PPN',':','','','','','<b>"+TagihanPPn.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','','','','','','','','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','DEPOSIT',':','','','','','<b>"+Deposit.getText()+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','EKSES',':','','','','','<b>"+Valid.SetAngka(bayar)+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    Sequel.menyimpan("temporary_bayar_ranap","'0','SISA PIUTANG',':','','','','','<b>"+Valid.SetAngka(piutang)+"</b>','Tagihan','','','','','','','','',''","Rekap Harian Tindakan Dokter");                                      
                }                

                i = 0;
                try{
                      biaya = (String)JOptionPane.showInputDialog(null,"Silahkan pilih nota yang mau dicetak!","Nota",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Nota 1", "Nota 2", "Kwitansi", "Nota & Kwitansi"},"Nota 1");
                      switch (biaya) {
                            case "Nota 1":
                                  i=1;
                                  break;
                            case "Nota 2":
                                  i=2;
                                  break;
                            case "Kwitansi":
                                  i=3;
                                  break;
                            case "Nota & Kwitansi":
                                  i=4;
                                  break;
                      }
                }catch(Exception e){
                      i=0;
                }            

                if(i>0){                       
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText());
                    if(i==1){
                        ttl=(ttlLaborat+ttlRadiologi+ttlOperasi+ttlObat+ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+
                                ttlRalan_Paramedis+ttlTambahan+ttlKamar+ttlRegistrasi+ttlHarian+ttlRetur_Obat+ttlResep_Pulang+ttlService);
                        Valid.panggilUrl("billing/LaporanBilling2.php?petugas="+akses.getkode().replaceAll(" ","_")+"&ttl="+ttl+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                    }else if(i==2){
                        Valid.panggilUrl("billing/LaporanBilling3.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                    }else if(i==3){
                        if(piutang>0){
                            Valid.panggilUrl("billing/LaporanBilling8.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(kamar_inap.no_rawat) from kamar_inap inner join reg_periksa "+
                                    "on kamar_inap.no_rawat=reg_periksa.no_rawat where reg_periksa.kd_pj='"+kd_pj+"' and kamar_inap.tgl_keluar like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RI/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                        }else if(piutang<=0){
                            Valid.panggilUrl("billing/LaporanBilling4.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(kamar_inap.no_rawat) from kamar_inap inner join reg_periksa "+
                                    "on kamar_inap.no_rawat=reg_periksa.no_rawat where reg_periksa.kd_pj='"+kd_pj+"' and kamar_inap.tgl_keluar like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RI/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                        }                            
                    }else if(i==4){
                        ttl=(ttlLaborat+ttlRadiologi+ttlOperasi+ttlObat+ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+
                                ttlRalan_Paramedis+ttlTambahan+ttlKamar+ttlRegistrasi+ttlHarian+ttlRetur_Obat+ttlResep_Pulang+ttlService);
                        Valid.panggilUrl("billing/LaporanBilling2.php?petugas="+akses.getkode().replaceAll(" ","_")+"&ttl="+ttl+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                        Valid.panggilUrl("billing/LaporanBilling3.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                        if(piutang>0){
                            Valid.panggilUrl("billing/LaporanBilling8.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(kamar_inap.no_rawat) from kamar_inap inner join reg_periksa "+
                                    "on kamar_inap.no_rawat=reg_periksa.no_rawat where reg_periksa.kd_pj='"+kd_pj+"' and kamar_inap.tgl_keluar like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RI/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                        }else if(piutang<=0){
                            Valid.panggilUrl("billing/LaporanBilling4.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(kamar_inap.no_rawat) from kamar_inap inner join reg_periksa "+
                                    "on kamar_inap.no_rawat=reg_periksa.no_rawat where reg_periksa.kd_pj='"+kd_pj+"' and kamar_inap.tgl_keluar like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RI/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                        }
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                }
                
                koneksi.setAutoCommit(true);
            }catch(Exception ex){
                System.out.println(ex);
            }                                    
            
        }
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnView);
        }
    }//GEN-LAST:event_BtnNotaKeyPressed

    private void MnInputObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputObat1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                beriobat.dlgobt.isCek();
                beriobat.dlgobt.setNoRm(norawatbayi,DTPTgl.getDate(),"00","00","00",true);
                beriobat.dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                beriobat.dlgobt.tampil();
                beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                beriobat.dlgobt.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnInputObat1ActionPerformed

    private void MnPotongan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPotongan1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            norawatpotongan.setText(norawatbayi);
            tampilPotongan(norawatpotongan.getText());
            WindowInput4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            WindowInput4.setLocationRelativeTo(internalFrame1);
            WindowInput4.setVisible(true);
        }            
    }//GEN-LAST:event_MnPotongan1ActionPerformed

    private void MnCariPeriksaLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariPeriksaLab1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                DlgCariPeriksaLab cperiksalab=new DlgCariPeriksaLab(null,false);
                cperiksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                cperiksalab.setLocationRelativeTo(internalFrame1);
                cperiksalab.SetNoRw(norawatbayi);
                cperiksalab.setAlwaysOnTop(false);
                cperiksalab.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnCariPeriksaLab1ActionPerformed

    private void MnTambahan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTambahan1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            norawattambahan.setText(norawatbayi);
            tampilTambahan(norawattambahan.getText());
            WindowInput3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            WindowInput3.setLocationRelativeTo(internalFrame1);
            WindowInput3.setVisible(true);
        }
            
    }//GEN-LAST:event_MnTambahan1ActionPerformed

    private void MnPeriksaRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologi1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(norawatbayi,"Ranap");
                periksalab.setAlwaysOnTop(false);
                periksalab.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnPeriksaRadiologi1ActionPerformed

    private void MnRawatInap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatInap1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                rawatinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                rawatinap.setLocationRelativeTo(internalFrame1);
                rawatinap.isCek();
                rawatinap.setNoRm(norawatbayi,DTPTgl.getDate(),new Date());
                rawatinap.tampilDr();
                rawatinap.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnRawatInap1ActionPerformed

    private void MnRawatJalan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalan1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
                dlgrwjl.isCek();
                dlgrwjl.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgrwjl.setLocationRelativeTo(internalFrame1);
                dlgrwjl.SetPoli("-");
                dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));

                dlgrwjl.setNoRm(norawatbayi,DTPTgl.getDate(),new Date());
                dlgrwjl.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnRawatJalan1ActionPerformed

    private void MnDataObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataObat1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            akses.setform("DLgBilingRanap");
            beriobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            beriobat.setLocationRelativeTo(internalFrame1);
            beriobat.isCek();
            beriobat.setNoRm(norawatbayi,DTPTgl.getDate(),new Date(),"ranap");
            beriobat.setVisible(true);
        }            
    }//GEN-LAST:event_MnDataObat1ActionPerformed

    private void MnInputResepPulang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputResepPulang1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                reseppulang.inputresep.isCek();
                reseppulang.inputresep.setNoRm(norawatbayi,"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                reseppulang.inputresep.tampil();
                reseppulang.inputresep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                reseppulang.inputresep.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnInputResepPulang1ActionPerformed

    private void MnReturJual1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnReturJual1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgReturJual returjual=new DlgReturJual(null,false);
            returjual.emptTeks();
            returjual.isCek();
            returjual.setPasien(Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",norawatbayi),norawatbayi);
            returjual.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            returjual.setLocationRelativeTo(internalFrame1);
            returjual.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_MnReturJual1ActionPerformed

    private void MnPeriksaLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLab1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                akses.setform("DLgBilingRanap");
                periksalab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(norawatbayi,"Ranap");
                periksalab.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnPeriksaLab1ActionPerformed

    private void MnCariRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariRadiologi1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                DlgCariPeriksaRadiologi cperiksalab=new DlgCariPeriksaRadiologi(null,false);
                cperiksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                cperiksalab.setLocationRelativeTo(internalFrame1);
                cperiksalab.SetNoRw(norawatbayi);
                cperiksalab.setAlwaysOnTop(false);
                cperiksalab.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnCariRadiologi1ActionPerformed

    private void MnDataResepPulang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataResepPulang1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            akses.setform("DLgBilingRanap");
            reseppulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            reseppulang.setLocationRelativeTo(internalFrame1);
            reseppulang.isCek();
            reseppulang.setNoRm(norawatbayi,DTPTgl.getDate(),new Date());
            reseppulang.setVisible(true);
        }            
    }//GEN-LAST:event_MnDataResepPulang1ActionPerformed

    private void MnObatLangsung1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsung1ActionPerformed
        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        }else{
            TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",norawatbayi));  
            WindowInput.setSize(590,80);
            WindowInput.setLocationRelativeTo(internalFrame1);
            WindowInput.setVisible(true);
        }            
    }//GEN-LAST:event_MnObatLangsung1ActionPerformed

    private void MnTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTagihanOperasiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            akses.setform("DLgBilingRanap");            
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.isCek();
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(),TNoRM.getText()+", "+TPasien.getText(),"Ranap");  
            dlgro.setVisible(true);
        }
    }//GEN-LAST:event_MnTagihanOperasiActionPerformed

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
        Valid.pindah(evt,BtnKeluar,BtnNota);
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void tbAkunBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunBayarPropertyChange
        if(this.isVisible()==true){
            isRawat();
        }
    }//GEN-LAST:event_tbAkunBayarPropertyChange

    private void tbAkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunBayarKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbAkunBayar.getRowCount()!=0){
                    if((tbAkunBayar.getSelectedColumn()==2)||(tbAkunBayar.getSelectedColumn()==3)||(tbAkunBayar.getSelectedColumn()==4)){
                        try {
                            if(!tabModeAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString().equals("")){
                                tbAkunBayar.setValueAt(
                                    Valid.roundUp((Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),3).toString())/100)*
                                        Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString()),100),tbAkunBayar.getSelectedRow(),4);
                            }else{
                                tbAkunBayar.setValueAt("",tbAkunBayar.getSelectedRow(),4);
                            }
                        } catch (Exception e) {
                        }                            
                    }
                }
                isRawat();
            }
        }
    }//GEN-LAST:event_tbAkunBayarKeyPressed

    private void tbAkunPiutangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunPiutangPropertyChange
        if(this.isVisible()==true){
            isRawat();
        }
    }//GEN-LAST:event_tbAkunPiutangPropertyChange

    private void tbAkunPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunPiutangKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(ChkPiutang.isSelected()==true){
                    isRawat();
                }else if(ChkPiutang.isSelected()==false){
                    JOptionPane.showMessageDialog(rootPane,"Silahkan centang terlebih dahulu pada pilihan piutang...!!");
                }
            }
        }
    }//GEN-LAST:event_tbAkunPiutangKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariBayarActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariBayarActionPerformed
        if(status.equals("belum")){
            tampilAkunBayar();
        }else if(status.equals("sudah")){
            tampilAkunBayarTersimpan();
        }
        isKembali();
    }//GEN-LAST:event_BtnCariBayarActionPerformed

    private void BtnCariBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariBayarKeyPressed

    }//GEN-LAST:event_BtnCariBayarKeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnCariPiutangActionPerformed(null);
        }
    }//GEN-LAST:event_TCari1KeyPressed

    private void btnCariPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPiutangActionPerformed
        if(ChkPiutang.isSelected()==true){
            if(status.equals("belum")){
                tampilAkunPiutang();
            }else if(status.equals("sudah")){
                tampilAkunPiutangTersimpan();
            }
            isKembali();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan centang terlebih dahulu pada pilihan piutang...!!");
        }            
    }//GEN-LAST:event_btnCariPiutangActionPerformed

    private void btnCariPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPiutangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariPiutangKeyPressed

    private void ChkPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPiutangActionPerformed
        if(ChkPiutang.isSelected()==false){
            ppBersihkan1ActionPerformed(evt);
        }
        isRawat();
    }//GEN-LAST:event_ChkPiutangActionPerformed

    private void ppBersihkan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkan1ActionPerformed
        Valid.tabelKosong(tabModeAkunPiutang);
        if(status.equals("belum")){
            tampilAkunPiutang();
        }else if(status.equals("sudah")){
            tampilAkunPiutangTersimpan();
        }
    }//GEN-LAST:event_ppBersihkan1ActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        Valid.tabelKosong(tabModeAkunBayar);
        if(status.equals("belum")){
            tampilAkunBayar();
        }else if(status.equals("sudah")){
            tampilAkunBayarTersimpan();
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void tbAkunPiutangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAkunPiutangMouseClicked
        if(ChkPiutang.isSelected()==false){
            ChkPiutang.setSelected(true);
            isRawat();
        }            
    }//GEN-LAST:event_tbAkunPiutangMouseClicked

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        isRawat();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        isRawat();
    }//GEN-LAST:event_chkRanapActionPerformed



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBilingRanap dialog = new DlgBilingRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnCariBayar;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusPotongan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluarPotongan;
    private widget.Button BtnKeluarUbahLama;
    private widget.Button BtnNota;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan2;
    private widget.Button BtnSimpan3;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpanPotongan;
    private widget.Button BtnSimpanUbahLama;
    private widget.Button BtnTambah;
    private widget.Button BtnTambahPotongan;
    private widget.Button BtnView;
    private widget.CekBox ChkPiutang;
    private widget.Tanggal DTPTgl;
    public widget.TextBox Deposit;
    private javax.swing.JMenu MnBayi;
    private javax.swing.JMenuItem MnCariPeriksaLab;
    private javax.swing.JMenuItem MnCariPeriksaLab1;
    private javax.swing.JMenuItem MnCariRadiologi;
    private javax.swing.JMenuItem MnCariRadiologi1;
    private javax.swing.JMenuItem MnDataObat;
    private javax.swing.JMenuItem MnDataObat1;
    private javax.swing.JMenuItem MnDataResepPulang;
    private javax.swing.JMenuItem MnDataResepPulang1;
    private javax.swing.JMenuItem MnHapusTagihan;
    private javax.swing.JMenuItem MnInputObat;
    private javax.swing.JMenuItem MnInputObat1;
    private javax.swing.JMenuItem MnInputResepPulang;
    private javax.swing.JMenuItem MnInputResepPulang1;
    private javax.swing.JMenuItem MnObatLangsung;
    private javax.swing.JMenuItem MnObatLangsung1;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaLab1;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPeriksaRadiologi1;
    private javax.swing.JMenuItem MnPotongan;
    private javax.swing.JMenuItem MnPotongan1;
    private javax.swing.JMenuItem MnRawatInap;
    private javax.swing.JMenuItem MnRawatInap1;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRawatJalan1;
    private javax.swing.JMenuItem MnReturJual;
    private javax.swing.JMenuItem MnReturJual1;
    private javax.swing.JMenuItem MnTagihanOperasi;
    private javax.swing.JMenuItem MnTambahan;
    private javax.swing.JMenuItem MnTambahan1;
    private javax.swing.JMenuItem MnUbahLamaInap;
    private javax.swing.JPopupMenu PopupBayar;
    private javax.swing.JPopupMenu PopupPiutang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TagihanPPn;
    private widget.TextBox TotalObat;
    private widget.TextBox TtlSemua;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput3;
    private javax.swing.JDialog WindowInput4;
    private javax.swing.JDialog WindowInput5;
    private javax.swing.JDialog WindowInput6;
    private widget.Button btnCariPiutang;
    private widget.Button btnPenjab;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenjab;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmpenjab;
    private widget.TextBox norawatpotongan;
    private widget.TextBox norawattambahan;
    private widget.TextBox norawatubahlama;
    private widget.panelisi panelBayar;
    private widget.panelisi panelGlass1;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppBersihkan1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbAkunBayar;
    private widget.Table tbAkunPiutang;
    private widget.Table tbBilling;
    private widget.Table tbPotongan;
    private widget.Table tbTambahan;
    private widget.Table tbUbahLama;
    // End of variables declaration//GEN-END:variables

    public void isRawat() {
         try {      
            pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
            try {
                pscekbilling.setString(1,TNoRw.getText());
                rscekbilling=pscekbilling.executeQuery();
                if(rscekbilling.next()){
                    i=rscekbilling.getInt(1);
                }    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscekbilling!=null){
                    rscekbilling.close();
                }
                if(pscekbilling!=null){
                    pscekbilling.close();
                }
            }         
            
            pscarirm=koneksi.prepareStatement("select no_rkm_medis from reg_periksa where no_rawat=?");
            try {
                pscarirm.setString(1,TNoRw.getText());
                rscarirm=pscarirm.executeQuery();
                if(rscarirm.next()){
                    TNoRM.setText(rscarirm.getString(1));
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscarirm!=null){
                    rscarirm.close();
                }
                if(pscarirm!=null){
                    pscarirm.close();
                }
            }
            
            pscaripasien=koneksi.prepareStatement("select nm_pasien from pasien where no_rkm_medis=? ");
            try {
                pscaripasien.setString(1,TNoRM.getText());
                rscaripasien=pscaripasien.executeQuery();
                if(rscaripasien.next()){
                    TPasien.setText(rscaripasien.getString(1));
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscaripasien!=null){
                    rscaripasien.close();
                }
                if(pscaripasien!=null){
                    pscaripasien.close();
                }
            }
            
            if(i<=0){
                uangdeposit=Sequel.cariIsiAngka("select ifnull(sum(besar_deposit),0) from deposit where no_rawat=?",TNoRw.getText());
                Deposit.setText(Valid.SetAngka(uangdeposit));
                prosesCariReg();                     
                prosesCariKamar();  
                
                if(!norawatbayi.equals("")){
                    MnBayi.setVisible(true);
                    tabModeRwJlDr.addRow(new Object[]{true,"Biaya Perawatan Ibu",":","",null,null,null,null,"-"});
                }else{                    
                    MnBayi.setVisible(false);
                }        
                
                prosesCariTindakan(TNoRw.getText());
                prosesCariOperasi(TNoRw.getText());
                prosesCariObat(TNoRw.getText());                   
                prosesResepPulang(TNoRw.getText());
                prosesCariTambahan(TNoRw.getText());  
                prosesCariPotongan(TNoRw.getText());     
                if(!norawatbayi.equals("")){
                    tabModeRwJlDr.addRow(new Object[]{false,"","","",null,null,null,null,"-"});
                    tabModeRwJlDr.addRow(new Object[]{true,"Biaya Perawatan Bayi",":","",null,null,null,null,"-"});                    
                    prosesCariTindakan(norawatbayi);   
                    prosesCariOperasi(norawatbayi);
                    prosesCariObat(norawatbayi);  
                    prosesResepPulang(norawatbayi);
                    prosesCariTambahan(norawatbayi);  
                    prosesCariPotongan(norawatbayi);
                }
                TCari.setText("");
                TCari1.setText("");
                tampilAkunBayar();
                tampilAkunPiutang();
                isHitung(); 
                status="belum";
            }else if(i>0){
                uangdeposit=Sequel.cariIsiAngka("select ifnull(sum(Uang_Muka),0) from nota_inap where no_rawat=?",TNoRw.getText());
                Deposit.setText(Valid.SetAngka(uangdeposit));
                Valid.SetTgl2(DTPTgl,Sequel.cariIsi("select concat(tanggal,' ',jam) from nota_inap where no_rawat='"+TNoRw.getText()+"'"));
                Valid.tabelKosong(tabModeRwJlDr);  
                pssudahmasuk=koneksi.prepareStatement(sqlpssudahmasuk);
                try {
                    pssudahmasuk.setString(1,TNoRw.getText());
                    rsreg=pssudahmasuk.executeQuery();
                    while(rsreg.next()){
                        if(!rsreg.getString("status").equals("Tagihan")){
                            tabModeRwJlDr.addRow(new Object[]{true,rsreg.getString("no"),
                                           rsreg.getString("nm_perawatan"),
                                           rsreg.getString("pemisah"),
                                           rsreg.getObject("satu"),
                                           rsreg.getObject("dua"),
                                           rsreg.getObject("tiga"),
                                           rsreg.getObject("empat"),
                                           rsreg.getString("status")});  
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsreg!=null){
                        rsreg.close();
                    }
                    if(pssudahmasuk!=null){
                        pssudahmasuk.close();
                    }
                }
                TCari.setText("");
                TCari1.setText("");                
                tampilAkunBayarTersimpan();
                tampilAkunPiutangTersimpan();
                isHitung(); 
                status="sudah";
            }   
         } catch (Exception ex) {
            System.out.println(ex);
         } 
         isKembali();
    }
    
    private void isRawat2(){
        prosesCariReg();                     
        prosesCariKamar();  

        if(!norawatbayi.equals("")){
            MnBayi.setVisible(true);
            tabModeRwJlDr.addRow(new Object[]{true,"Biaya Perawatan Ibu",":","",null,null,null,null,"-"});
        }else{                    
            MnBayi.setVisible(false);
        }        

        prosesCariTindakan(TNoRw.getText());
        prosesCariOperasi(TNoRw.getText());
        prosesCariObat(TNoRw.getText());                   
        prosesResepPulang(TNoRw.getText());
        prosesCariTambahan(TNoRw.getText());  
        prosesCariPotongan(TNoRw.getText());     
        if(!norawatbayi.equals("")){
            tabModeRwJlDr.addRow(new Object[]{false,"","","",null,null,null,null,"-"});
            tabModeRwJlDr.addRow(new Object[]{true,"Biaya Perawatan Bayi",":","",null,null,null,null,"-"});                    
            prosesCariTindakan(norawatbayi);   
            prosesCariOperasi(norawatbayi);
            prosesCariObat(norawatbayi);  
            prosesResepPulang(norawatbayi);
            prosesCariTambahan(norawatbayi);  
            prosesCariPotongan(norawatbayi);
        }
        isHitung();          
        isKembali();
    }
    
    private void prosesCariReg() {        
        Valid.tabelKosong(tabModeRwJlDr);
        try{  
            psreg=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,concat(DATE_FORMAT(reg_periksa.tgl_registrasi, '%e %M %Y'),' ',reg_periksa.jam_reg) as registrasi,kamar_inap.kd_kamar,concat(if(kamar_inap.tgl_keluar='0000-00-00',DATE_FORMAT(CURDATE(), '%e %M %Y'),DATE_FORMAT(kamar_inap.tgl_keluar, '%e %M %Y')),' ',kamar_inap.jam_keluar) as keluar,  "+
                    "(select sum(kamar_inap.lama) from kamar_inap where kamar_inap.no_rawat=reg_periksa.no_rawat ) as lama,reg_periksa.biaya_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rawat=? "+
                    "order by kamar_inap.tgl_keluar desc limit 1");
            try {
                psreg.setString(1,TNoRw.getText());
                rsreg=psreg.executeQuery();            
                while(rsreg.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"No.Nota",": "+Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6),"",null,null,null,null,"-"});
                    
                    pskamar=koneksi.prepareStatement("select concat(kamar.kd_kamar,', ',bangsal.nm_bangsal) from bangsal inner join kamar "+
                        "on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=?");
                    try {
                        pskamar.setString(1,rsreg.getString("kd_kamar"));
                        rskamar=pskamar.executeQuery();
                        if(rskamar.next()){
                            tabModeRwJlDr.addRow(new Object[]{true,"Bangsal/Kamar",": "+rskamar.getString(1),"",null,null,null,null,"-"});
                        }           
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rskamar!=null){
                            rskamar.close();
                        }
                        if(pskamar!=null){
                            pskamar.close();
                        }
                    }
                        
                    DTPTgl.setDate(new Date());
                    tabModeRwJlDr.addRow(new Object[]{true,"Tgl.Perawatan",": "+rsreg.getString("registrasi")+" s.d. "+rsreg.getString("keluar")+" ( "+rsreg.getString("lama")+" Hari )","",null,null,null,null,"-"});

                    norawatbayi="";
                    psanak=koneksi.prepareStatement(sqlpsanak);
                    try {
                        psanak.setString(1,TNoRw.getText());
                        rsanak=psanak.executeQuery();
                        if(rsanak.next()){
                            norawatbayi=rsanak.getString("no_rawat2");
                            tabModeRwJlDr.addRow(new Object[]{true,"No.R.M. Ibu",": "+TNoRM.getText(),"",null,null,null,null,"-"});
                            tabModeRwJlDr.addRow(new Object[]{true,"Nama Ibu",": "+TPasien.getText(),"",null,null,null,null,"-"});
                            tabModeRwJlDr.addRow(new Object[]{true,"No.R.M. Bayi",": "+rsanak.getString("no_rkm_medis"),"",null,null,null,null,"-"});
                            tabModeRwJlDr.addRow(new Object[]{true,"Nama Bayi",": "+rsanak.getString("nm_pasien"),"",null,null,null,null,"-"});
                        }else{
                            tabModeRwJlDr.addRow(new Object[]{true,"No.R.M.",": "+TNoRM.getText(),"",null,null,null,null,"-"});
                            tabModeRwJlDr.addRow(new Object[]{true,"Nama Pasien",": "+TPasien.getText()+" ("+rsreg.getString("umurdaftar")+rsreg.getString("sttsumur")+")","",null,null,null,null,"-"});
                            norawatbayi="";
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsanak!=null){
                            rsanak.close();
                        }
                        if(psanak!=null){
                            psanak.close();
                        }
                    }

                    pscarialamat=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");
                    try {
                        pscarialamat.setString(1,TNoRM.getText());
                        rscarialamat=pscarialamat.executeQuery();
                        if(rscarialamat.next()){
                            tabModeRwJlDr.addRow(new Object[]{true,"Alamat Pasien",": "+rscarialamat.getString(1),"",null,null,null,null,"-"});
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rscarialamat!=null){
                            rscarialamat.close();
                        }
                        if(pscarialamat!=null){
                            pscarialamat.close();
                        }
                    }
                                     
                    //cari dokter yang menangandi  
                    if(centangdokterranap.equals("Yes")){
                        psdokterranap=koneksi.prepareStatement(sqlpsdokterranap);
                        psdokterralan=koneksi.prepareStatement(sqlpsdokterralan);                        
                        try {
                            psdokterranap.setString(1,TNoRw.getText());
                            rsdokterranap=psdokterranap.executeQuery();                

                            psdokterralan.setString(1,TNoRw.getText());
                            rsdokterralan=psdokterralan.executeQuery();

                            if(rsdokterralan.next()||rsdokterranap.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,"Dokter ",":","",null,null,null,null,"-"});  
                            }
                            x=1;
                            rsdokterranap.beforeFirst();
                            while(rsdokterranap.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsdokterranap.getString("nm_dokter"),"",null,null,null,null,"Dokter"});x++;   
                            }
                            //rs2.close();
                            rsdokterralan.beforeFirst();
                            while(rsdokterralan.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsdokterralan.getString("nm_dokter"),"",null,null,null,null,"Dokter"});x++;   
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsdokterranap!=null){
                                rsdokterranap.close();
                            }
                            if(rsdokterralan!=null){
                                rsdokterralan.close();
                            }
                            if(psdokterranap!=null){
                                psdokterranap.close();
                            }
                            if(psdokterralan!=null){
                                psdokterralan.close();
                            }                            
                        }                            
                    }else{
                        psdokterranap=koneksi.prepareStatement(sqlpsdokterranap);
                        psdokterralan=koneksi.prepareStatement(sqlpsdokterralan);                        
                        try {
                            psdokterranap.setString(1,TNoRw.getText());
                            rsdokterranap=psdokterranap.executeQuery();                

                            psdokterralan.setString(1,TNoRw.getText());
                            rsdokterralan=psdokterralan.executeQuery();

                            if(rsdokterralan.next()||rsdokterranap.next()){
                                tabModeRwJlDr.addRow(new Object[]{false,"Dokter ",":","",null,null,null,null,"-"});  
                            }
                            x=1;
                            rsdokterranap.beforeFirst();
                            while(rsdokterranap.next()){
                                tabModeRwJlDr.addRow(new Object[]{false,"                           ",rsdokterranap.getString("nm_dokter"),"",null,null,null,null,"Dokter"});x++;   
                            }
                            //rs2.close();
                            rsdokterralan.beforeFirst();
                            while(rsdokterralan.next()){
                                tabModeRwJlDr.addRow(new Object[]{false,"                           ",rsdokterralan.getString("nm_dokter"),"",null,null,null,null,"Dokter"});x++;   
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsdokterranap!=null){
                                rsdokterranap.close();
                            }
                            if(rsdokterralan!=null){
                                rsdokterralan.close();
                            }
                            if(psdokterranap!=null){
                                psdokterranap.close();
                            }
                            if(psdokterralan!=null){
                                psdokterralan.close();
                            }                            
                        } 
                    }  

                    if(tampilkan_administrasi_di_billingranap.equals("Yes")){
                        tabModeRwJlDr.addRow(new Object[]{true,"Registrasi",":","",null,null,null,rsreg.getDouble("biaya_reg"),"Registrasi"});
                    }                
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsreg!=null){
                    rsreg.close();
                }
                if(psreg!=null){
                    psreg.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    private void prosesCariObat(String norawat) {                   
        tabModeRwJlDr.addRow(new Object[]{true,x+". Obat & BHP",":","",null,null,null,null,"Obat"});
        x++;
        subttl=0;
        ttlobat=0;
        ttlretur=0;
        try{
            pscariobat=koneksi.prepareStatement(
                    "select databarang.nama_brng,jenis.nama,detail_pemberian_obat.biaya_obat,"+
                    "sum(detail_pemberian_obat.jml) as jml,sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah) as tambahan,"+
                    "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total "+
                    "from detail_pemberian_obat inner join databarang inner join jenis "+
                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns where "+
                    "detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.status like ? group by databarang.kode_brng,detail_pemberian_obat.biaya_obat order by jenis.nama");
            try {
                pscariobat.setString(1,norawat);
                if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==true)){
                    pscariobat.setString(2,"%%");
                }else if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==false)){
                    pscariobat.setString(2,"%Ralan%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==true)){
                    pscariobat.setString(2,"%Ranap%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==false)){
                    pscariobat.setString(2,"%Kosong%");
                }
                rscariobat=pscariobat.executeQuery();
                if(centangobatranap.equals("Yes")){
                    while(rscariobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rscariobat.getString("nama_brng")+" ("+rscariobat.getString("nama")+")",":",
                                       rscariobat.getDouble("biaya_obat"),rscariobat.getDouble("jml"),rscariobat.getDouble("tambahan"),
                                       (rscariobat.getDouble("total")+rscariobat.getDouble("tambahan")),"Obat"});
                        subttl=subttl+rscariobat.getDouble("total")+rscariobat.getDouble("tambahan");
                    }
                }else{
                    while(rscariobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{false,"                           ",rscariobat.getString("nama_brng")+" ("+rscariobat.getString("nama")+")",":",
                                       rscariobat.getDouble("biaya_obat"),rscariobat.getDouble("jml"),rscariobat.getDouble("tambahan"),
                                       (rscariobat.getDouble("total")+rscariobat.getDouble("tambahan")),"Obat"});
                        subttl=subttl+rscariobat.getDouble("total")+rscariobat.getDouble("tambahan");
                    }
                }
                    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscariobat!=null){
                    rscariobat.close();
                }
                if(pscariobat!=null){
                    pscariobat.close();
                }                
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        try{            
            psobatlangsung=koneksi.prepareStatement(sqlpsobatlangsung);
            try {
                psobatlangsung.setString(1,norawat);
                rsobatlangsung=psobatlangsung.executeQuery();
                if(centangobatranap.equals("Yes")){
                    if(rsobatlangsung.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ","Obat & BHP",":",rsobatlangsung.getDouble("besar_tagihan"),1,0,rsobatlangsung.getDouble("besar_tagihan"),"Obat"});                
                        subttl=subttl+rsobatlangsung.getDouble("besar_tagihan");
                    }
                }else{
                    if(rsobatlangsung.next()){
                        tabModeRwJlDr.addRow(new Object[]{false,"                           ","Obat & BHP",":",rsobatlangsung.getDouble("besar_tagihan"),1,0,rsobatlangsung.getDouble("besar_tagihan"),"Obat"});                
                        subttl=subttl+rsobatlangsung.getDouble("besar_tagihan");
                    }
                }
                    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsobatlangsung!=null){
                    rsobatlangsung.close();
                }
                if(psobatlangsung!=null){
                    psobatlangsung.close();
                }
            }
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        try{ 
            psobatoperasi=koneksi.prepareStatement(sqlpsobatoperasi);
            try {
                psobatoperasi.setString(1,norawat);
                rsobatoperasi=psobatoperasi.executeQuery();
                if(centangobatranap.equals("Yes")){
                    while(rsobatoperasi.next()){
                         tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsobatoperasi.getString("nm_obat"),":",
                                       rsobatoperasi.getDouble("hargasatuan"),rsobatoperasi.getDouble("jumlah"),0,
                                       rsobatoperasi.getDouble("total"),"Obat"});
                         subttl=subttl+rsobatoperasi.getDouble("total");
                    }
                }else{
                    while(rsobatoperasi.next()){
                         tabModeRwJlDr.addRow(new Object[]{false,"                           ",rsobatoperasi.getString("nm_obat"),":",
                                       rsobatoperasi.getDouble("hargasatuan"),rsobatoperasi.getDouble("jumlah"),0,
                                       rsobatoperasi.getDouble("total"),"Obat"});
                         subttl=subttl+rsobatoperasi.getDouble("total");
                    }
                }                    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsobatoperasi!=null){
                    rsobatoperasi.close();
                }
                if(psobatoperasi!=null){
                    psobatoperasi.close();
                }
            }
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        if(subttl>1){
            ttlobat=subttl;
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Obat & BHP : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlObat"});
        }
                
        subttl=0;        
        try{   
            psreturobat=koneksi.prepareStatement(sqlpsreturobat);
            try {
                psreturobat.setString(1,"%"+norawat+"%");
                rsreturobat=psreturobat.executeQuery();
                if(rsreturobat.next()){                
                    tabModeRwJlDr.addRow(new Object[]{true,"","Retur Obat :","",null,null,null,null,"Retur Obat"});          
                }
                rsreturobat.beforeFirst();
                if(centangobatranap.equals("Yes")){
                    while(rsreturobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{
                            true,"                           ",rsreturobat.getString("nama_brng"),":",
                            rsreturobat.getDouble("h_retur"),rsreturobat.getDouble("jml"),0,
                            rsreturobat.getDouble("ttl"),"Retur Obat"
                        });
                        subttl=subttl+rsreturobat.getDouble("ttl");
                    }
                }else{
                    while(rsreturobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{
                            false,"                           ",rsreturobat.getString("nama_brng"),":",
                            rsreturobat.getDouble("h_retur"),rsreturobat.getDouble("jml"),0,
                            rsreturobat.getDouble("ttl"),"Retur Obat"
                        });
                        subttl=subttl+rsreturobat.getDouble("ttl");
                    }
                }                    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsreturobat!=null){
                    rsreturobat.close();
                }
                if(psreturobat!=null){
                    psreturobat.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }  
        if(subttl<0){
            ttlretur=subttl;
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Retur Obat : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlRetur Obat"});
        }
        
        if((ttlobat-ttlretur)>0){
            if(tampilkan_ppnobat_ranap.equals("Yes")){
                ppnobat=Valid.roundUp((ttlobat+ttlretur)*0.1,100);
                tabModeRwJlDr.addRow(new Object[]{true,"","PPN Obat",":",ppnobat,1,0,ppnobat,"Obat"});
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Obat Bersih : "+Valid.SetAngka3(ttlobat+ttlretur+ppnobat),"",null,null,null,null,"TtlRetur Obat"});
            }else{
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Obat Bersih : "+Valid.SetAngka3(ttlobat+ttlretur),"",null,null,null,null,"TtlRetur Obat"});
            }            
        } 
        
        if(detailbhp>0){
            tabModeRwJlDr.addRow(new Object[]{true,x+". Paket Obat/BHP",":","",null,null,null,detailbhp,"Ralan Dokter"});
            x++;
        }
    }
    
    private void prosesCariKamar() {
        tabModeRwJlDr.addRow(new Object[]{true,"Ruang",":","",null,null,null,null,"Kamar"}); 
        subttl=0;
        try{         
            pskamarin=koneksi.prepareStatement(sqlpskamarin);
            try {
                pskamarin.setString(1,TNoRw.getText());
                rskamarin=pskamarin.executeQuery();
                while(rskamarin.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                    try {
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal"));
                        pstamkur.setString(3,"Kamar");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur!=null){
                            rstamkur.close();
                        }
                        if(pstamkur!=null){
                            pstamkur.close();
                        }
                    }
                    if(!norawatbayi.equals("")){
                        if(persenbayi>0){
                            tabModeRwJlDr.addRow(new Object[]{true,"                           ",rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal")+" (Ibu)",":",
                                           rskamarin.getDouble("trf_kamar"),rskamarin.getDouble("lama"),tamkur,(rskamarin.getDouble("total")+tamkur),"Kamar"});
                            subttl=subttl+rskamarin.getDouble("total")+tamkur;
                        
                            tabModeRwJlDr.addRow(new Object[]{true,"                           ",rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal")+" (Bayi)",":",
                                           (rskamarin.getDouble("trf_kamar")*(persenbayi/100)),rskamarin.getDouble("lama"),tamkur,((rskamarin.getDouble("total")*(persenbayi/100))+tamkur),"Kamar"});
                            subttl=subttl+(rskamarin.getDouble("total")*(persenbayi/100))+tamkur;
                        }else{
                            tabModeRwJlDr.addRow(new Object[]{true,"                           ",rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal"),":",
                                           rskamarin.getDouble("trf_kamar"),rskamarin.getDouble("lama"),tamkur,(rskamarin.getDouble("total")+tamkur),"Kamar"});
                            subttl=subttl+rskamarin.getDouble("total")+tamkur; 
                        }                            
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal"),":",
                                       rskamarin.getDouble("trf_kamar"),rskamarin.getDouble("lama"),tamkur,(rskamarin.getDouble("total")+tamkur),"Kamar"});
                        subttl=subttl+rskamarin.getDouble("total")+tamkur;  
                    }                         

                    psbiayasekali=koneksi.prepareStatement(sqlpsbiayasekali);
                    try {
                        psbiayasekali.setString(1,rskamarin.getString("kd_kamar"));
                        rsbiayasekali=psbiayasekali.executeQuery();             
                        if(rsbiayasekali.next()){
                            tabModeRwJlDr.addRow(new Object[]{true,"-","Biaya Kamar :","",null,null,null,null,"Kamar"});          
                        }
                        rsbiayasekali.beforeFirst();
                        z=1;
                        while(rsbiayasekali.next()){
                            tamkur=0;
                            pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                            try {
                                pstamkur.setString(1,TNoRw.getText());
                                pstamkur.setString(2,rsbiayasekali.getString("nama_biaya"));
                                pstamkur.setString(3,"Kamar");
                                rstamkur=pstamkur.executeQuery();
                                if(rstamkur.next()){
                                    tamkur=rstamkur.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rstamkur!=null){
                                    rstamkur.close();
                                }
                                if(pstamkur!=null){
                                    pstamkur.close();
                                }
                            }
                            tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsbiayasekali.getString("nama_biaya"),":",
                                       rsbiayasekali.getDouble("besar_biaya"),1,tamkur,(rsbiayasekali.getDouble("total")+tamkur),"Kamar"});z++;  
                            subttl=subttl+rsbiayasekali.getDouble("total")+tamkur;   
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsbiayasekali!=null){
                            rsbiayasekali.close();
                        }
                        if(psbiayasekali!=null){
                            psbiayasekali.close();
                        }
                    }

                    psbiayaharian=koneksi.prepareStatement(sqlpsbiayaharian);
                    try {
                        psbiayaharian.setDouble(1,rskamarin.getDouble("lama"));             
                        psbiayaharian.setString(2,rskamarin.getString("kd_kamar"));   
                        rsbiayaharian=psbiayaharian.executeQuery();      
                        if(rsbiayaharian.next()){
                            tabModeRwJlDr.addRow(new Object[]{true,"-","Biaya Harian :","",null,null,null,null,"Harian"});          
                        }
                        rsbiayaharian.beforeFirst();
                        z=1;
                        while(rsbiayaharian.next()){
                            tamkur=0;
                            pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                            try {
                                pstamkur.setString(1,TNoRw.getText());
                                pstamkur.setString(2,rsbiayaharian.getString("nama_biaya"));
                                pstamkur.setString(3,"Harian");
                                rstamkur=pstamkur.executeQuery();
                                if(rstamkur.next()){
                                    tamkur=rstamkur.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rstamkur!=null){
                                    rstamkur.close();
                                }
                                if(pstamkur!=null){
                                    pstamkur.close();
                                }
                            }
                                
                            tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsbiayaharian.getString("nama_biaya"),":",
                                       rsbiayaharian.getDouble("besar_biaya"),(rsbiayaharian.getDouble("jml")*rskamarin.getDouble("lama")),tamkur,(tamkur+rsbiayaharian.getDouble("total")),"Harian"});z++;     
                            subttl=subttl+rsbiayaharian.getDouble("total")+tamkur;   
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsbiayaharian!=null){
                            rsbiayaharian.close();
                        }
                        if(psbiayaharian!=null){
                            psbiayaharian.close();
                        }
                    }             
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rskamarin!=null){
                    rskamarin.close();
                }
                if(pskamarin!=null){
                    pskamarin.close();
                }
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        if(subttl>1){
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Kamar Inap : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlKamar"});
        }
    }
    
    private void prosesResepPulang(String norawat) {
        if(Sequel.cariInteger("select count(resep_pulang.kode_brng) from resep_pulang where resep_pulang.no_rawat=?",norawat)>0){
            tabModeRwJlDr.addRow(new Object[]{true,"Resep Pulang",":","",null,null,null,null,"Resep Pulang"});
        }else{
            tabModeRwJlDr.addRow(new Object[]{false,"Resep Pulang",":","",null,null,null,null,"Resep Pulang"});
        }
        
        x++;
        subttl=0;
        try{     
            psreseppulang=koneksi.prepareStatement(sqlpsreseppulang);
            try {
                psreseppulang.setString(1,norawat);
                rsreseppulang=psreseppulang.executeQuery();
                while(rsreseppulang.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                    try {
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rsreseppulang.getString("nama_brng")+" "+rsreseppulang.getString("dosis"));
                        pstamkur.setString(3,"Resep Pulang");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur!=null){
                            rstamkur.close();
                        }
                        if(pstamkur!=null){
                            pstamkur.close();
                        }
                    }
                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsreseppulang.getString("nama_brng")+" "+rsreseppulang.getString("dosis"),":",
                                   rsreseppulang.getDouble("harga"),rsreseppulang.getDouble("jml_barang"),tamkur,(tamkur+rsreseppulang.getDouble("total")),"Resep Pulang"});
                    subttl=subttl+rsreseppulang.getDouble("total")+tamkur;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsreseppulang!=null){
                    rsreseppulang.close();
                }
                if(psreseppulang!=null){
                    psreseppulang.close();
                }
            }
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        if(subttl>1){ 
             tabModeRwJlDr.addRow(new Object[]{true,"","Total Resep Pulang : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlResep Pulang"});
        }
    }


    private void isHitung() {              
        ttl=0;        
        ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
        ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;
        ttlService=0;
        int row=tabModeRwJlDr.getRowCount();
        if(row>0){
            for(int r=0;r<row;r++){     
                y=0;
                try {
                    y=Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());  
                } catch (Exception e) {
                   y=0;
                }  
                switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                    case "Laborat":
                        ttlLaborat=ttlLaborat+y;
                        break;
                    case "Radiologi":
                        ttlRadiologi=ttlRadiologi+y;
                        break;
                    case "Operasi":
                        ttlOperasi=ttlOperasi+y;
                        break;
                    case "Obat":
                        ttlObat=ttlObat+y;
                        break;
                    case "Ranap Dokter":
                        ttlRanap_Dokter=ttlRanap_Dokter+y;
                        break;
                    case "Ranap Dokter Paramedis":
                        ttlRanap_Dokter=ttlRanap_Dokter+y;
                        break;
                    case "Ranap Paramedis":
                        ttlRanap_Paramedis=ttlRanap_Paramedis+y;
                        break;
                    case "Ralan Dokter":
                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                        break;
                    case "Ralan Dokter Paramedis":
                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                        break;
                    case "Ralan Paramedis":
                        ttlRalan_Paramedis=ttlRalan_Paramedis+y;
                        break;
                    case "Tambahan":
                        ttlTambahan=ttlTambahan+y;
                        break;
                    case "Potongan":
                        ttlPotongan=ttlPotongan+y;
                        break;
                    case "Kamar":
                        ttlKamar=ttlKamar+y;
                        break;
                    case "Registrasi":
                        ttlRegistrasi=ttlRegistrasi+y;
                        break;
                    case "Harian":
                        ttlHarian=ttlHarian+y;
                        break;
                    case "Retur Obat":
                        ttlRetur_Obat=ttlRetur_Obat+y;
                        break;
                    case "Resep Pulang":
                        ttlResep_Pulang=ttlResep_Pulang+y;
                        break;
                    case "Service":
                        ttlService=ttlService+y;
                        break;
                }
                ttl=ttl+y;
            }       
            
            try {
                i=0;
                pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
                try {
                    pscekbilling.setString(1,TNoRw.getText());
                    rscekbilling=pscekbilling.executeQuery();
                    if(rscekbilling.next()){
                        i=rscekbilling.getInt(1);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rscekbilling!=null){
                        rscekbilling.close();
                    }
                    if(pscekbilling!=null){
                        pscekbilling.close();
                    }
                }
                if(i==0){ 
                    if(ChkPiutang.isSelected()==false){
                        psservice=koneksi.prepareStatement("select * from set_service_ranap");
                    }else{
                        psservice=koneksi.prepareStatement("select * from set_service_ranap_piutang");
                    }

                    try {
                        ttlService=0;
                        laboratserv=0;radiologiserv=0;operasiserv=0;obatserv=0;
                        ranap_dokterserv=0;ranap_paramedisserv=0;ralan_dokterserv=0;
                        ralan_paramedisserv=0;tambahanserv=0;potonganserv=0;
                        kamarserv=0;registrasiserv=0;harianserv=0;retur_Obatserv=0;resep_Pulangserv=0;

                        rsservice=psservice.executeQuery();
                        if(rsservice.next()){
                            if(rsservice.getString("laborat").equals("Yes")){
                                laboratserv=ttlLaborat;
                            }
                            if(rsservice.getString("radiologi").equals("Yes")){
                                radiologiserv=ttlRadiologi;
                            }
                            if(rsservice.getString("operasi").equals("Yes")){
                                operasiserv=ttlOperasi;
                            }
                            if(rsservice.getString("obat").equals("Yes")){
                                obatserv=ttlObat;
                            }
                            if(rsservice.getString("ranap_dokter").equals("Yes")){
                                ranap_dokterserv=ttlRanap_Dokter;
                            }
                            if(rsservice.getString("ranap_paramedis").equals("Yes")){
                                ranap_paramedisserv=ttlRanap_Paramedis;
                            }
                            if(rsservice.getString("ralan_dokter").equals("Yes")){
                                ralan_dokterserv=ttlRalan_Dokter;
                            }
                            if(rsservice.getString("ralan_paramedis").equals("Yes")){
                                ralan_paramedisserv=ttlRalan_Paramedis;
                            }
                            if(rsservice.getString("tambahan").equals("Yes")){
                                tambahanserv=ttlTambahan;
                            }
                            if(rsservice.getString("potongan").equals("Yes")){
                                potonganserv=ttlPotongan;
                            }
                            if(rsservice.getString("kamar").equals("Yes")){
                                kamarserv=ttlKamar;
                            }
                            if(rsservice.getString("registrasi").equals("Yes")){
                                registrasiserv=ttlRegistrasi;
                            }
                            if(rsservice.getString("harian").equals("Yes")){
                                harianserv=ttlHarian;
                            }
                            if(rsservice.getString("retur_Obat").equals("Yes")){
                                retur_Obatserv=ttlRetur_Obat;
                            }
                            if(rsservice.getString("resep_Pulang").equals("Yes")){
                                resep_Pulangserv=ttlResep_Pulang;
                            }
                            ttlService=Valid.roundUp((rsservice.getDouble("besar")/100)*
                                    (laboratserv+radiologiserv+operasiserv+obatserv+
                                    ranap_dokterserv+ranap_paramedisserv+ralan_dokterserv+
                                    ralan_paramedisserv+tambahanserv+potonganserv+
                                    kamarserv+registrasiserv+harianserv+retur_Obatserv+resep_Pulangserv),100);
                            ttl=ttl+ttlService;
                            tabModeRwJlDr.addRow(new Object[]{true,rsservice.getString("nama_service"),":","",null,null,null,ttlService,"Service"});
                        }                    
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsservice!=null){
                            rsservice.close();
                        }
                        if(psservice!=null){
                            psservice.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } 
            TtlSemua.setText(Valid.SetAngka3(ttl));
        }        
    }
    

    public void isCek(){
        Valid.tabelKosong(tabModeAkunBayar);
        Valid.tabelKosong(tabModeAkunPiutang);
        DTPTgl.setDate(new Date());
        BtnSimpan.setEnabled(akses.getbilling_ranap());
        BtnNota.setEnabled(akses.getbilling_ranap());
        BtnView.setEnabled(akses.getbilling_ranap());
        MnRawatJalan.setEnabled(akses.gettindakan_ralan());
        MnRawatInap.setEnabled(akses.gettindakan_ranap());
        MnInputObat.setEnabled(akses.getberi_obat());
        MnInputResepPulang.setEnabled(akses.getresep_pulang());
        MnPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnTambahan.setEnabled(akses.gettambahan_biaya());
        MnPotongan.setEnabled(akses.getpotongan_biaya());
        MnUbahLamaInap.setEnabled(akses.getkamar_inap());
        MnObatLangsung.setEnabled(akses.getberi_obat());
        MnReturJual.setEnabled(akses.getretur_dari_pembeli());
        MnHapusTagihan.setEnabled(akses.gethapus_nota_salah());
        MnPenjab.setEnabled(akses.getbilling_ranap());
        MnTagihanOperasi.setEnabled(akses.getoperasi());
        MnDataObat.setEnabled(akses.getberi_obat());
        MnDataResepPulang.setEnabled(akses.getresep_pulang());
        MnCariPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnCariRadiologi.setEnabled(akses.getperiksa_radiologi());
        if(Sequel.cariIsi("select tampilkan_tombol_nota_ranap from set_nota").equals("Yes")){
            BtnNota.setVisible(true);
        }else{
            if(akses.getkode().equals("Admin Utama")){
                BtnNota.setVisible(true);
            }else{
                BtnNota.setVisible(false);
            }            
        }
        
    }
    
    public void isKembali(){
        bayar=0;total=0;ppn=0;besarppn=0;tagihanppn=0;y=0;piutang=0;kekurangan=0;countbayar=0;
        row2=tabModeAkunBayar.getRowCount();
        for(r=0;r<row2;r++){ 
            if(!tabModeAkunBayar.getValueAt(r,2).toString().equals("")){
                countbayar++;
                try {
                    bayar=bayar+Double.parseDouble(tabModeAkunBayar.getValueAt(r,2).toString()); 
                } catch (Exception e) {
                    bayar=bayar+0;
                }               
            }  
            
            if(!tabModeAkunBayar.getValueAt(r,4).toString().equals("")){
                try {
                    besarppn=besarppn+Valid.roundUp(Double.parseDouble(tabModeAkunBayar.getValueAt(r,4).toString()),100); 
                } catch (Exception e) {
                    besarppn=besarppn+0;
                }               
            }   
        }
        
        row2=tabModeAkunPiutang.getRowCount();
        for(r=0;r<row2;r++){ 
            if(!tabModeAkunPiutang.getValueAt(r,3).toString().equals("")){
                try {
                    piutang=piutang+Double.parseDouble(tabModeAkunPiutang.getValueAt(r,3).toString()); 
                } catch (Exception e) {
                    piutang=piutang+0;
                }               
            }             
        }
        
            
        
        if((ttl)>0) {
            total=ttl; 
        }
        
        tagihanppn=besarppn+total;
        TagihanPPn.setText(Valid.SetAngka3(tagihanppn));
        
        if(piutang<=0){
            kekurangan=(bayar+uangdeposit+besarppn)-tagihanppn;
            jLabel5.setText("Bayar : Rp.");
            if(kekurangan<0){
                jLabel6.setText("Kekurangan : Rp.");
            }else{
                jLabel6.setText("Kembali : Rp.");
            }
                 
            TKembali.setText(Valid.SetAngka3(kekurangan));            
        }else{
            kekurangan=(tagihanppn-(bayar+uangdeposit+besarppn)-piutang)* -1;
            jLabel5.setText("Uang Muka : Rp.");
            if(kekurangan>0){
                jLabel6.setText("Kelebihan : Rp.");
            }else{
                jLabel6.setText("Kekurangan : Rp.");
            }
                
            TKembali.setText(Valid.SetAngka3(kekurangan));  
        }  
    }   

    private void prosesCariTindakan(String norawat){
        detailjs=0;
        detailbhp=0;
        try {
            tabModeRwJlDr.addRow(new Object[]{true,"Rincian Biaya",":","",null,null,null,null,"Ranap Dokter"});
            pskategori=koneksi.prepareStatement(sqlpskategori);
            try {
                rskategori=pskategori.executeQuery();
                x=1;
                while(rskategori.next()){    
                    psralandokter=koneksi.prepareStatement(sqlpsralandokter);
                    psralandrpr=koneksi.prepareStatement(sqlpsralandrpr);
                    psranapdokter=koneksi.prepareStatement(sqlpsranapdokter);
                    psranapdrpr=koneksi.prepareStatement(sqlpsranapdrpr);
                    psralanperawat=koneksi.prepareStatement(sqlpsralanperawat);
                    psranapperawat=koneksi.prepareStatement(sqlpsranapperawat);
                    try {
                        psralandokter.setString(1,norawat);
                        psralandokter.setString(2,rskategori.getString(1));
                        rsralandokter=psralandokter.executeQuery();

                        psralandrpr.setString(1,norawat);
                        psralandrpr.setString(2,rskategori.getString(1));
                        rsralandrpr=psralandrpr.executeQuery();

                        psranapdokter.setString(1,norawat);
                        psranapdokter.setString(2,rskategori.getString(1));
                        rsranapdokter=psranapdokter.executeQuery();

                        psranapdrpr.setString(1,norawat);
                        psranapdrpr.setString(2,rskategori.getString(1));
                        rsranapdrpr=psranapdrpr.executeQuery();

                        psralanperawat.setString(1,norawat);
                        psralanperawat.setString(2,rskategori.getString(1));
                        rsralanperawat=psralanperawat.executeQuery();  

                        psranapperawat.setString(1,norawat);
                        psranapperawat.setString(2,rskategori.getString(1));
                        rsranapperawat=psranapperawat.executeQuery();

                        subttl=0;   
                        if(chkRalan.isSelected()==true){
                            if(rsralandrpr.next()||rsralandokter.next()||rsralanperawat.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,x+". "+rskategori.getString(2),":","",null,null,null,null,"Ranap Dokter"}); 
                                x++;
                            }
                            rsralandokter.beforeFirst();
                            while(rsralandokter.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsralandokter.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ralan Dokter");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }

                                if(rinciandokterranap.equals("Yes")){
                                    detailbhp=detailbhp+rsralandokter.getDouble("totalbhp");
                                    detailjs=detailjs+rsralandokter.getDouble("totalmaterial");
                                    tabModeRwJlDr.addRow(new Object[]{true,"",rsralandokter.getString("nm_perawatan"),":",
                                                rsralandokter.getDouble("tarif_tindakandr"),rsralandokter.getDouble("jml"),tamkur,(rsralandokter.getDouble("totaltarif_tindakandr")+tamkur),"Ralan Dokter"});
                                    subttl=subttl+rsralandokter.getDouble("totaltarif_tindakandr")+tamkur; 
                                }else{
                                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsralandokter.getString("nm_perawatan"),":",
                                                rsralandokter.getDouble("total_byrdr"),rsralandokter.getDouble("jml"),tamkur,(tamkur+rsralandokter.getDouble("biaya")),"Ralan Dokter"});
                                    subttl=subttl+rsralandokter.getDouble("biaya")+tamkur;
                                }                        
                            }
                            
                            rsralandrpr.beforeFirst();
                            while(rsralandrpr.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsralandrpr.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ralan Dokter Paramedis");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }

                                if(rinciandokterranap.equals("Yes")){
                                    detailbhp=detailbhp+rsralandrpr.getDouble("totalbhp");
                                    detailjs=detailjs+rsralandrpr.getDouble("totalmaterial")+rsralandrpr.getDouble("totaltarif_tindakanpr");
                                    tabModeRwJlDr.addRow(new Object[]{true,"",rsralandrpr.getString("nm_perawatan"),":",
                                                rsralandrpr.getDouble("tarif_tindakandr"),rsralandrpr.getDouble("jml"),tamkur,(rsralandrpr.getDouble("totaltarif_tindakandr")+tamkur),"Ralan Dokter Paramedis"});
                                    subttl=subttl+rsralandrpr.getDouble("totaltarif_tindakandr")+tamkur; 
                                }else{
                                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsralandrpr.getString("nm_perawatan"),":",
                                                rsralandrpr.getDouble("total_byrdr"),rsralandrpr.getDouble("jml"),tamkur,(tamkur+rsralandrpr.getDouble("biaya")),"Ralan Dokter Paramedis"});
                                    subttl=subttl+rsralandrpr.getDouble("biaya")+tamkur;
                                }                        
                            }
                            
                            rsralanperawat.beforeFirst();          
                            while(rsralanperawat.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsralanperawat.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ralan Paramedis");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }

                                tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsralanperawat.getString("nm_perawatan"),":",
                                               rsralanperawat.getDouble("total_byrpr"),rsralanperawat.getDouble("jml"),tamkur,(tamkur+rsralanperawat.getDouble("biaya")),"Ralan Paramedis"});
                                subttl=subttl+rsralanperawat.getDouble("biaya")+tamkur;
                            }  
                        }
                            

                        if(chkRanap.isSelected()==true){
                            if(rsranapdrpr.next()||rsranapdokter.next()||rsranapperawat.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,x+". "+rskategori.getString(2),":","",null,null,null,null,"Ranap Dokter"}); 
                                x++;
                            }
                            rsranapdokter.beforeFirst();
                            while(rsranapdokter.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsranapdokter.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ranap Dokter");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }

                                if(rinciandokterranap.equals("Yes")){
                                    detailbhp=detailbhp+rsranapdokter.getDouble("totalbhp");
                                    detailjs=detailjs+rsranapdokter.getDouble("totalmaterial");
                                    tabModeRwJlDr.addRow(new Object[]{true,"",rsranapdokter.getString("nm_perawatan"),":",
                                                rsranapdokter.getDouble("tarif_tindakandr"),rsranapdokter.getDouble("jml"),tamkur,(rsranapdokter.getDouble("totaltarif_tindakandr")+tamkur),"Ranap Dokter"});
                                    subttl=subttl+rsranapdokter.getDouble("totaltarif_tindakandr")+tamkur; 
                                }else{
                                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsranapdokter.getString("nm_perawatan"),":",
                                                       rsranapdokter.getDouble("total_byrdr"),rsranapdokter.getDouble("jml"),tamkur,(tamkur+rsranapdokter.getDouble("biaya")),"Ranap Dokter"});
                                    subttl=subttl+rsranapdokter.getDouble("biaya")+tamkur;
                                }                        
                            }

                            rsranapdrpr.beforeFirst();
                            while(rsranapdrpr.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsranapdrpr.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ranap Dokter Paramedis");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }

                                if(rinciandokterranap.equals("Yes")){
                                    detailbhp=detailbhp+rsranapdrpr.getDouble("totalbhp");
                                    detailjs=detailjs+rsranapdrpr.getDouble("totalmaterial")+rsranapdrpr.getDouble("totaltarif_tindakanpr");
                                    tabModeRwJlDr.addRow(new Object[]{true,"",rsranapdrpr.getString("nm_perawatan"),":",
                                                rsranapdrpr.getDouble("tarif_tindakandr"),rsranapdrpr.getDouble("jml"),tamkur,(rsranapdrpr.getDouble("totaltarif_tindakandr")+tamkur),"Ranap Dokter Paramedis"});
                                    subttl=subttl+rsranapdrpr.getDouble("totaltarif_tindakandr")+tamkur; 
                                }else{
                                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsranapdrpr.getString("nm_perawatan"),":",
                                                       rsranapdrpr.getDouble("total_byrdr"),rsranapdrpr.getDouble("jml"),tamkur,(tamkur+rsranapdrpr.getDouble("biaya")),"Ranap Dokter Paramedis"});
                                    subttl=subttl+rsranapdrpr.getDouble("biaya")+tamkur;
                                }

                            }
                            
                            rsranapperawat.beforeFirst();
                            while(rsranapperawat.next()){
                                tamkur=0;
                                pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                                try {
                                    pstamkur.setString(1,TNoRw.getText());
                                    pstamkur.setString(2,rsranapperawat.getString("nm_perawatan"));
                                    pstamkur.setString(3,"Ranap Paramedis");
                                    rstamkur=pstamkur.executeQuery();
                                    if(rstamkur.next()){
                                        tamkur=rstamkur.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(rstamkur!=null){
                                        rstamkur.close();
                                    }
                                    if(pstamkur!=null){
                                        pstamkur.close();
                                    }
                                }
                                tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsranapperawat.getString("nm_perawatan"),":",
                                               rsranapperawat.getDouble("total_byrpr"),rsranapperawat.getDouble("jml"),tamkur,(tamkur+rsranapperawat.getDouble("biaya")),"Ranap Paramedis"});
                                subttl=subttl+rsranapperawat.getDouble("biaya")+tamkur;
                            }  
                        }      
                                  
                        if(subttl>1){
                           tabModeRwJlDr.addRow(new Object[]{true,"","Total "+rskategori.getString(2)+" : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlRanap Dokter"});
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsralandokter!=null){
                            rsralandokter.close();
                        }
                        if(rsralandrpr!=null){
                            rsralandrpr.close();
                        }
                        if(rsranapdokter!=null){
                            rsranapdokter.close(); 
                        }
                        if(rsranapdrpr!=null){
                            rsranapdrpr.close(); 
                        }
                        if(rsralanperawat!=null){
                            rsralanperawat.close(); 
                        }
                        if(rsranapperawat!=null){
                            rsranapperawat.close(); 
                        }
                        if(psralandokter!=null){
                            psralandokter.close();
                        }
                        if(psralandrpr!=null){
                            psralandrpr.close();
                        }
                        if(psranapdokter!=null){
                            psranapdokter.close(); 
                        }
                        if(psranapdrpr!=null){
                            psranapdrpr.close(); 
                        }
                        if(psralanperawat!=null){
                            psralanperawat.close(); 
                        }
                        if(psranapperawat!=null){
                            psranapperawat.close(); 
                        }
                    }                    
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rskategori!=null){
                    rskategori.close();
                }
                if(pskategori!=null){
                    pskategori.close();
                }
            }  
            
            subttl=0;
            psperiksalab=koneksi.prepareStatement(
                   "select jns_perawatan_lab.nm_perawatan, count(periksa_lab.kd_jenis_prw) as jml,periksa_lab.biaya as biaya, "+
                   " sum(periksa_lab.biaya) as total,jns_perawatan_lab.kd_jenis_prw "+
                   " from periksa_lab inner join jns_perawatan_lab "+
                   " on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "+
                   " periksa_lab.no_rawat=? and periksa_lab.status like ? group by periksa_lab.kd_jenis_prw  ");
            try {
                psperiksalab.setString(1,norawat);
                if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==true)){
                    psperiksalab.setString(2,"%%");
                }else if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==false)){
                    psperiksalab.setString(2,"%Ralan%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==true)){
                    psperiksalab.setString(2,"%Ranap%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==false)){
                    psperiksalab.setString(2,"%Kosong%");
                }
                rsperiksalab=psperiksalab.executeQuery();
                if(rsperiksalab.next()){
                       tabModeRwJlDr.addRow(new Object[]{true,x+". Pemeriksaan Lab",":","",null,null,null,null,"Laborat"}); 
                       x++;
                }
                rsperiksalab.beforeFirst();
                while(rsperiksalab.next()){
                    psdetaillab=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.biaya_item) as total from detail_periksa_lab where detail_periksa_lab.no_rawat=? "+
                            "and detail_periksa_lab.kd_jenis_prw=? ");
                    try {
                        psdetaillab.setString(1,norawat);
                        psdetaillab.setString(2,rsperiksalab.getString("kd_jenis_prw"));
                        rsdetaillab=psdetaillab.executeQuery();
                        lab=0;
                        while(rsdetaillab.next()){  
                            lab=rsdetaillab.getDouble("total");               
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Detail Lab : "+e);
                    } finally{
                        if(rsdetaillab!=null){
                            rsdetaillab.close();
                        }
                        if(psdetaillab!=null){
                            psdetaillab.close();
                        }
                    }
                        
                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsperiksalab.getString("nm_perawatan"),":",
                                  rsperiksalab.getDouble("biaya"),rsperiksalab.getDouble("jml"),lab,(rsperiksalab.getDouble("total")+lab),"Laborat"});
                    subttl=subttl+rsperiksalab.getDouble("total")+lab;
                }

                if(subttl>1){
                   tabModeRwJlDr.addRow(new Object[]{true,"","Total Periksa Lab : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlLaborat"});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Periksa Lab : "+e);
            } finally{
                if(rsperiksalab!=null){
                    rsperiksalab.close();
                }
                if(psperiksalab!=null){
                    psperiksalab.close();
                }
            }
                
            
            subttl=0;
            psperiksarad=koneksi.prepareStatement(
                   "select jns_perawatan_radiologi.nm_perawatan, count(periksa_radiologi.kd_jenis_prw) as jml,periksa_radiologi.biaya as biaya, "+
                   " sum(periksa_radiologi.biaya) as total,jns_perawatan_radiologi.kd_jenis_prw "+
                   " from periksa_radiologi inner join jns_perawatan_radiologi "+
                   " on jns_perawatan_radiologi.kd_jenis_prw=periksa_radiologi.kd_jenis_prw where "+
                   " periksa_radiologi.no_rawat=? and periksa_radiologi.status like ? group by periksa_radiologi.kd_jenis_prw  ");            
            try {
                psperiksarad.setString(1,norawat);
                if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==true)){
                    psperiksarad.setString(2,"%%");
                }else if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==false)){
                    psperiksarad.setString(2,"%Ralan%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==true)){
                    psperiksarad.setString(2,"%Ranap%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==false)){
                    psperiksarad.setString(2,"%Kosong%");
                }
                rsperiksarad=psperiksarad.executeQuery();            
                if(rsperiksarad.next()){
                       tabModeRwJlDr.addRow(new Object[]{true,x+". Pemeriksaan Radiologi",":","",null,null,null,null,"Radiologi"}); 
                       x++;
                }
                rsperiksarad.beforeFirst();
                while(rsperiksarad.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);            
                    try {
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rsperiksarad.getString("nm_perawatan"));
                        pstamkur.setString(3,"Radiologi");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }    
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur!=null){
                            rstamkur.close();
                        }
                        if(pstamkur!=null){
                            pstamkur.close();
                        }
                    }
                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsperiksarad.getString("nm_perawatan"),":",
                                  rsperiksarad.getDouble("biaya"),rsperiksarad.getDouble("jml"),tamkur,(tamkur+rsperiksarad.getDouble("total")),"Radiologi"});
                    subttl=subttl+rsperiksarad.getDouble("total")+tamkur;
                }            

                if(subttl>1){
                   tabModeRwJlDr.addRow(new Object[]{true,"","Total Periksa Radiologi : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlRadiologi"});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Periksa Radiologi : "+e);
            } finally{
                if(rsperiksarad!=null){
                    rsperiksarad.close();
                }
                if(psperiksarad!=null){
                    psperiksarad.close();
                }
            }                
            
            if(detailjs>0){
                tabModeRwJlDr.addRow(new Object[]{true,x+". Jasa Sarpras",":","",null,null,null,detailjs,"Ralan Dokter"});
                x++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    
    private void prosesCariOperasi(String norawat){
        try{            
            subttl=0;
            psoperasi=koneksi.prepareStatement(sqlpsoperasi);
            try {
                psoperasi.setString(1,norawat);
                if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==true)){
                    psoperasi.setString(2,"%%");
                }else if((chkRalan.isSelected()==true)&&(chkRanap.isSelected()==false)){
                    psoperasi.setString(2,"%Ralan%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==true)){
                    psoperasi.setString(2,"%Ranap%");
                }else if((chkRalan.isSelected()==false)&&(chkRanap.isSelected()==false)){
                    psoperasi.setString(2,"%Kosong%");
                }
                rsoperasi=psoperasi.executeQuery();
                if(rsoperasi.next()){
                       tabModeRwJlDr.addRow(new Object[]{true,x+". Operasi",":","",null,null,null,null,"Operasi"}); 
                       x++;
                }
                rsoperasi.beforeFirst();
                if(rincianoperasi.equals("Yes")){
                    while(rsoperasi.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsoperasi.getString("nm_perawatan"),":",null,null,null,null,"Operasi"});
                        if(rsoperasi.getDouble("biayaoperator1")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Operator 1",":",rsoperasi.getDouble("biayaoperator1"),1,0,rsoperasi.getDouble("biayaoperator1"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaoperator2")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Operator 2",":",rsoperasi.getDouble("biayaoperator2"),1,0,rsoperasi.getDouble("biayaoperator2"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaoperator3")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Operator 3",":",rsoperasi.getDouble("biayaoperator3"),1,0,rsoperasi.getDouble("biayaoperator3"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaasisten_operator1")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Asisten Operator 1",":",rsoperasi.getDouble("biayaasisten_operator1"),1,0,rsoperasi.getDouble("biayaasisten_operator1"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaasisten_operator2")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Asisten Operator 2",":",rsoperasi.getDouble("biayaasisten_operator2"),1,0,rsoperasi.getDouble("biayaasisten_operator2"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayaasisten_operator3")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Asisten Operator 3",":",rsoperasi.getDouble("biayaasisten_operator3"),1,0,rsoperasi.getDouble("biayaasisten_operator3"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayainstrumen")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Instrumen",":",rsoperasi.getDouble("biayainstrumen"),1,0,rsoperasi.getDouble("biayainstrumen"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayadokter_anak")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Dokter Anak",":",rsoperasi.getDouble("biayadokter_anak"),1,0,rsoperasi.getDouble("biayadokter_anak"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaperawaat_resusitas")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Perawat Resusitas",":",rsoperasi.getDouble("biayaperawaat_resusitas"),1,0,rsoperasi.getDouble("biayaperawaat_resusitas"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayadokter_anestesi")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Dokter Anastesi",":",rsoperasi.getDouble("biayadokter_anestesi"),1,0,rsoperasi.getDouble("biayadokter_anestesi"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayaasisten_anestesi")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Asisten Anastesi 1",":",rsoperasi.getDouble("biayaasisten_anestesi"),1,0,rsoperasi.getDouble("biayaasisten_anestesi"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayaasisten_anestesi2")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Asisten Anastesi 2",":",rsoperasi.getDouble("biayaasisten_anestesi2"),1,0,rsoperasi.getDouble("biayaasisten_anestesi2"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayabidan")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Bidan 1",":",rsoperasi.getDouble("biayabidan"),1,0,rsoperasi.getDouble("biayabidan"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayabidan2")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Bidan 2",":",rsoperasi.getDouble("biayabidan2"),1,0,rsoperasi.getDouble("biayabidan2"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayabidan3")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Bidan 3",":",rsoperasi.getDouble("biayabidan3"),1,0,rsoperasi.getDouble("biayabidan3"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biayaperawat_luar")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Perawat Luar",":",rsoperasi.getDouble("biayaperawat_luar"),1,0,rsoperasi.getDouble("biayaperawat_luar"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayaalat")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Alat",":",rsoperasi.getDouble("biayaalat"),1,0,rsoperasi.getDouble("biayaalat"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayasewaok")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Sewa OK/VK",":",rsoperasi.getDouble("biayasewaok"),1,0,rsoperasi.getDouble("biayasewaok"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("akomodasi")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Akomodasi",":",rsoperasi.getDouble("akomodasi"),1,0,rsoperasi.getDouble("akomodasi"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_omloop")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Onloop 1",":",rsoperasi.getDouble("biaya_omloop"),1,0,rsoperasi.getDouble("biaya_omloop"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_omloop2")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Onloop 2",":",rsoperasi.getDouble("biaya_omloop2"),1,0,rsoperasi.getDouble("biaya_omloop2"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_omloop3")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Onloop 3",":",rsoperasi.getDouble("biaya_omloop3"),1,0,rsoperasi.getDouble("biaya_omloop3"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_omloop4")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Onloop 4",":",rsoperasi.getDouble("biaya_omloop4"),1,0,rsoperasi.getDouble("biaya_omloop4"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_omloop5")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Onloop 5",":",rsoperasi.getDouble("biaya_omloop5"),1,0,rsoperasi.getDouble("biaya_omloop5"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("bagian_rs")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  N.M.S.",":",rsoperasi.getDouble("bagian_rs"),1,0,rsoperasi.getDouble("bagian_rs"),"Operasi"}); 
                        }

                        if(rsoperasi.getDouble("biayasarpras")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Sarpras",":",rsoperasi.getDouble("biayasarpras"),1,0,rsoperasi.getDouble("biayasarpras"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_dokter_pjanak")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Dokter PJ Anak",":",rsoperasi.getDouble("biaya_dokter_pjanak"),1,0,rsoperasi.getDouble("biaya_dokter_pjanak"),"Operasi"}); 
                        }
                        
                        if(rsoperasi.getDouble("biaya_dokter_umum")>0){
                           tabModeRwJlDr.addRow(new Object[]{true,"                           ","  Biaya Dokter Umum",":",rsoperasi.getDouble("biaya_dokter_umum"),1,0,rsoperasi.getDouble("biaya_dokter_umum"),"Operasi"}); 
                        }
                        subttl=subttl+rsoperasi.getDouble("biaya");
                    }
                }else{
                    while(rsoperasi.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsoperasi.getString("nm_perawatan"),":",rsoperasi.getDouble("biaya"),1,0,rsoperasi.getDouble("biaya"),"Operasi"});
                        subttl=subttl+rsoperasi.getDouble("biaya");
                    }
                }

                if(subttl>0){
                   tabModeRwJlDr.addRow(new Object[]{true,"","Total Operasi : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlOperasi"});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsoperasi!=null){
                    rsoperasi.close();
                }
                if(psoperasi!=null){
                    psoperasi.close();
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void prosesCariTambahan(String norawat) {             
             x++;  
             subttl=0;
             try {
                pstambahanbiaya=koneksi.prepareStatement(sqlpstambahanbiaya);
                try {
                    pstambahanbiaya.setString(1,norawat);
                    rstambahanbiaya=pstambahanbiaya.executeQuery();
                    rstambahanbiaya.last();
                    if(rstambahanbiaya.getRow()>0){
                        tabModeRwJlDr.addRow(new Object[]{true,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});     
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{false,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});     
                    }
                    rstambahanbiaya.beforeFirst();
                    while(rstambahanbiaya.next()){                    
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rstambahanbiaya.getString("nama_biaya"),":",
                                   rstambahanbiaya.getDouble("besar_biaya"),1,0,rstambahanbiaya.getDouble("besar_biaya"),"Tambahan"});
                        subttl=subttl+rstambahanbiaya.getDouble("besar_biaya");
                    }   
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rstambahanbiaya!=null){
                        rstambahanbiaya.close();
                    }
                    if(pstambahanbiaya!=null){
                        pstambahanbiaya.close();
                    }
                } 
             } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
             }
             if(subttl>1){
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Tambahan : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlTambahan"});
             } 
    }
    
    private void prosesCariPotongan(String norawat) {             
             x++;
             subttl=0;
             try {
                pspotonganbiaya=koneksi.prepareStatement(sqlpspotonganbiaya);
                try {
                    pspotonganbiaya.setString(1,norawat);
                    rspotonganbiaya=pspotonganbiaya.executeQuery();
                    rspotonganbiaya.last();
                    if(rspotonganbiaya.getRow()>0){
                        tabModeRwJlDr.addRow(new Object[]{true,"Potongan Biaya",":","",null,null,null,null,"Potongan"});       
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{false,"Potongan Biaya",":","",null,null,null,null,"Potongan"});       
                    }
                    rspotonganbiaya.beforeFirst();
                    while(rspotonganbiaya.next()){                    
                        tabModeRwJlDr.addRow(new Object[]{true,"                           ",rspotonganbiaya.getString("nama_pengurangan"),":",
                                   rspotonganbiaya.getDouble("besar_pengurangan"),1,0,(-1*rspotonganbiaya.getDouble("besar_pengurangan")),"Potongan"});
                        subttl=subttl+rspotonganbiaya.getDouble("besar_pengurangan");
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rspotonganbiaya!=null){
                        rspotonganbiaya.close();
                    }     
                    if(pspotonganbiaya!=null){
                        pspotonganbiaya.close();
                    }     
                }
             } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
             }
             if(subttl>1){
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Potongan : "+Valid.SetAngka(subttl),"",null,null,null,null,"TtlPotongan"});
             } 
    }
    
    public void tampilTambahan(String NoRawat) {
        norawattambahan.setText(NoRawat);
        Valid.tabelKosong(tabModeTambahan);
        try{
            pstambahanbiaya=koneksi.prepareStatement(sqlpstambahanbiaya);
            try {
                pstambahanbiaya.setString(1,norawattambahan.getText());
                rstambahanbiaya=pstambahanbiaya.executeQuery();
                while(rstambahanbiaya.next()){
                    tabModeTambahan.addRow(new Object[]{rstambahanbiaya.getString(1),rstambahanbiaya.getString(2)});
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstambahanbiaya!=null){
                    rstambahanbiaya.close();
                }
                if(pstambahanbiaya!=null){
                    pstambahanbiaya.close();
                }
            }                 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }            
    }
    
    public void tampilPotongan(String NoRawat) {
        norawatpotongan.setText(NoRawat);
        Valid.tabelKosong(tabModePotongan);
        try{     
            pspotonganbiaya=koneksi.prepareStatement(sqlpspotonganbiaya);
            try {
                pspotonganbiaya.setString(1,norawatpotongan.getText());
                rspotonganbiaya=pspotonganbiaya.executeQuery();
                while(rspotonganbiaya.next()){
                    tabModePotongan.addRow(new Object[]{rspotonganbiaya.getString(1),rspotonganbiaya.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rspotonganbiaya!=null){
                    rspotonganbiaya.close();
                }     
                if(pspotonganbiaya!=null){
                    pspotonganbiaya.close();
                }     
            }       
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }            
    }
    
    public void tampilUbahLama(String NoRawat) {
        norawatubahlama.setText(NoRawat);
        Valid.tabelKosong(tabModeKamIn);
        try{   
            pskamarin=koneksi.prepareStatement(sqlpskamarin);
            try {
                pskamarin.setString(1,norawatubahlama.getText());
                rskamarin=pskamarin.executeQuery();
                while(rskamarin.next()){
                    tabModeKamIn.addRow(new Object[]{
                        rskamarin.getString("kd_kamar"),rskamarin.getString("nm_bangsal"),
                        rskamarin.getString("tgl_masuk"),rskamarin.getString("jam_masuk"),
                        rskamarin.getString("tgl_keluar"),rskamarin.getString("jam_keluar"),
                        rskamarin.getString("lama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rskamarin!=null){
                    rskamarin.close();
                }
                if(pskamarin!=null){
                    pskamarin.close();
                }
            }        
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }            
    }
    
    private void tampilAkunBayar() {         
         try{           
             jml=0;
             for(z=0;z<tbAkunBayar.getRowCount();z++){
                if(!tbAkunBayar.getValueAt(z,2).toString().equals("")){
                    jml++;
                }
             }
             Nama_Akun_Bayar=null;
             Kode_Rek_Bayar=null;
             Bayar=null;
             PPN_Persen=null;
             PPN_Besar=null;
             Nama_Akun_Bayar=new String[jml];
             Kode_Rek_Bayar=new String[jml];
             Bayar=new String[jml];
             PPN_Persen=new String[jml];
             PPN_Besar=new String[jml];
             
             jml=0;
             for(z=0;z<tbAkunBayar.getRowCount();z++){
                if(!tbAkunBayar.getValueAt(z,2).toString().equals("")){
                    Nama_Akun_Bayar[jml]=tbAkunBayar.getValueAt(z,0).toString();
                    Kode_Rek_Bayar[jml]=tbAkunBayar.getValueAt(z,1).toString();
                    Bayar[jml]=tbAkunBayar.getValueAt(z,2).toString();
                    PPN_Persen[jml]=tbAkunBayar.getValueAt(z,3).toString();
                    PPN_Besar[jml]=tbAkunBayar.getValueAt(z,4).toString();
                    jml++;
                }
             }
             
             Valid.tabelKosong(tabModeAkunBayar);
             
             for(z=0;z<jml;z++){
                tabModeAkunBayar.addRow(new Object[] {
                    Nama_Akun_Bayar[z],Kode_Rek_Bayar[z],Bayar[z],PPN_Persen[z],PPN_Besar[z]
                });
             }
             
             psakunbayar=koneksi.prepareStatement("select * from akun_bayar where nama_bayar like ? order by nama_bayar");
             try{
                 psakunbayar.setString(1,"%"+TCari.getText()+"%");
                 rsakunbayar=psakunbayar.executeQuery();
                 while(rsakunbayar.next()){                    
                     tabModeAkunBayar.addRow(new Object[]{rsakunbayar.getString(1),rsakunbayar.getString(2),"",rsakunbayar.getDouble(3),""});
                 } 
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rsakunbayar != null){
                     rsakunbayar.close();
                 } 
                 if(psakunbayar != null){
                     psakunbayar.close();
                 } 
             }

         }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
         }
    }
    
    private void tampilAkunBayarTersimpan() {
         try{           
             Valid.tabelKosong(tabModeAkunBayar);
             psakunbayar=koneksi.prepareStatement(
                     "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_inap.besar_bayar,"+
                     "akun_bayar.ppn,detail_nota_inap.besarppn from akun_bayar inner join detail_nota_inap "+
                     "on akun_bayar.nama_bayar=detail_nota_inap.nama_bayar where detail_nota_inap.no_rawat=? and akun_bayar.nama_bayar like ? order by nama_bayar");
             try{                 
                 psakunbayar.setString(1,TNoRw.getText());
                 psakunbayar.setString(2,"%"+TCari.getText()+"%");
                 rsakunbayar=psakunbayar.executeQuery();
                 while(rsakunbayar.next()){
                    tabModeAkunBayar.addRow(new Object[]{rsakunbayar.getString(1),rsakunbayar.getString(2),rsakunbayar.getString(3),rsakunbayar.getString(4),rsakunbayar.getString(5)});
                 } 
             }catch (Exception e) {
                 System.out.println("Notifikasi Akun Bayar Tersimpan : "+e);
             } finally{
                 if(rsakunbayar != null){
                     rsakunbayar.close();
                 } 
                 if(psakunbayar != null){
                     psakunbayar.close();
                 } 
             }

         }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
         }
    }
    
    private void tampilAkunPiutang() {
         try{        
             jml=0;
             for(z=0;z<tbAkunPiutang.getRowCount();z++){
                if(!tbAkunPiutang.getValueAt(z,3).toString().equals("")){
                    jml++;
                }
             }
            
             Nama_Akun_Piutang=null;
             Nama_Akun_Piutang=new String[jml];
             Kode_Rek_Piutang=null;
             Kode_Rek_Piutang=new String[jml];
             Kd_PJ=null;
             Kd_PJ=new String[jml];
             Besar_Piutang=null;
             Besar_Piutang=new String[jml];
             Jatuh_Tempo=null;
             Jatuh_Tempo=new String[jml];
             
             jml=0;             
             for(z=0;z<tbAkunPiutang.getRowCount();z++){
                if(!tbAkunPiutang.getValueAt(z,3).toString().equals("")){
                    Nama_Akun_Piutang[jml]=tbAkunPiutang.getValueAt(z,0).toString();
                    Kode_Rek_Piutang[jml]=tbAkunPiutang.getValueAt(z,1).toString();
                    Kd_PJ[jml]=tbAkunPiutang.getValueAt(z,2).toString();
                    Besar_Piutang[jml]=tbAkunPiutang.getValueAt(z,3).toString();
                    Jatuh_Tempo[jml]=tbAkunPiutang.getValueAt(z,4).toString(); 
                    jml++;
                }
             }
             
             Valid.tabelKosong(tabModeAkunPiutang);             
        
             for(z=0;z<jml;z++){
                tabModeAkunPiutang.addRow(new Object[] {
                    Nama_Akun_Piutang[z],Kode_Rek_Piutang[z],Kd_PJ[z],Besar_Piutang[z],Jatuh_Tempo[z]
                });
             }
             
             psakunpiutang=koneksi.prepareStatement("select * from akun_piutang where nama_bayar like ? order by nama_bayar");
             try{
                 psakunpiutang.setString(1,"%"+TCari1.getText()+"%");
                 rsakunpiutang=psakunpiutang.executeQuery();
                 while(rsakunpiutang.next()){                    
                     tabModeAkunPiutang.addRow(new Object[]{rsakunpiutang.getString(1),rsakunpiutang.getString(2),rsakunpiutang.getString(3),"",DTPTgl.getSelectedItem().toString().substring(0,10)});
                 } 
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rsakunpiutang != null){
                     rsakunpiutang.close();
                 } 
                 if(psakunpiutang != null){
                     psakunpiutang.close();
                 } 
             }

         }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
         }
    }
    
    private void tampilAkunPiutangTersimpan() {
         try{    
             Valid.tabelKosong(tabModeAkunPiutang);
             psakunpiutang=koneksi.prepareStatement(
                     "select akun_piutang.nama_bayar,akun_piutang.kd_rek,akun_piutang.kd_pj, "+
                     "detail_piutang_pasien.totalpiutang,date_format(detail_piutang_pasien.tgltempo,'%d/%m/%Y') from "+
                     "akun_piutang inner join detail_piutang_pasien on akun_piutang.nama_bayar=detail_piutang_pasien.nama_bayar "+
                     "where detail_piutang_pasien.no_rawat=? and akun_piutang.nama_bayar like ? order by nama_bayar");
             try{
                 psakunpiutang.setString(1,TNoRw.getText());
                 psakunpiutang.setString(2,"%"+TCari1.getText()+"%");
                 rsakunpiutang=psakunpiutang.executeQuery();
                 while(rsakunpiutang.next()){                    
                     tabModeAkunPiutang.addRow(new Object[]{rsakunpiutang.getString(1),rsakunpiutang.getString(2),rsakunpiutang.getString(3),rsakunpiutang.getString(4),rsakunpiutang.getString(5)});
                 } 
                 if(tabModeAkunPiutang.getRowCount()>0){
                     ChkPiutang.setSelected(true);
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi Akun Piutang Tersimpan : "+e);
             } finally{
                 if(rsakunpiutang != null){
                     rsakunpiutang.close();
                 } 
                 if(psakunpiutang != null){
                     psakunpiutang.close();
                 } 
             }
         }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
         }
    }

    private void isSimpan() {
        if(notaranap.equals("Yes")){
            BtnNotaActionPerformed(null);
        }
        
        try {  
            try {
                Sequel.meghapus("nota_inap","no_rawat",TNoRw.getText());    
                psnota=koneksi.prepareStatement(sqlpsnota);
                try {
                    psnota.setString(1,TNoRw.getText());
                    psnota.setString(2,Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6));
                    psnota.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                    psnota.setString(4,DTPTgl.getSelectedItem().toString().substring(11,19));
                    psnota.setDouble(5,uangdeposit);
                    psnota.executeUpdate();
                } catch (Exception e) {
                    Sequel.meghapus("nota_inap","no_rawat",TNoRw.getText());               
                    tbBilling.setValueAt(": "+Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6),0,2);
                    psnota=koneksi.prepareStatement(sqlpsnota);
                    try {
                        psnota.setString(1,TNoRw.getText());
                        psnota.setString(2,Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6));
                        psnota.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                        psnota.setString(4,DTPTgl.getSelectedItem().toString().substring(11,19));
                        psnota.setDouble(5,uangdeposit);
                        psnota.executeUpdate();
                    }  catch (Exception ex) {
                        System.out.println("Notifikasi Nota 2 : "+ex);
                    } finally{
                        psnota.close();
                    }
                } finally{
                    psnota.close();
                }
            } catch (Exception e) {
                Sequel.meghapus("nota_inap","no_rawat",TNoRw.getText());               
                tbBilling.setValueAt(": "+Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6),0,2);
                psnota=koneksi.prepareStatement(sqlpsnota);
                try {
                    psnota.setString(1,TNoRw.getText());
                    psnota.setString(2,Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_inap where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RI/",6));
                    psnota.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                    psnota.setString(4,DTPTgl.getSelectedItem().toString().substring(11,19));
                    psnota.setDouble(5,uangdeposit);
                    psnota.executeUpdate();
                }  catch (Exception ex) {
                    System.out.println("Notifikasi Nota 2 : "+ex);
                } finally{
                    psnota.close();
                }
            }
            koneksi.setAutoCommit(false);                
            for(i=0;i<tbBilling.getRowCount();i++){  
                psbiling=koneksi.prepareStatement(sqlpsbiling);
                try {
                    psbiling.setInt(1,i);
                    psbiling.setString(2,TNoRw.getText());
                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                    psbiling.setString(4,tbBilling.getValueAt(i,1).toString());
                    psbiling.setString(5,tbBilling.getValueAt(i,2).toString().replaceAll("'","`"));
                    psbiling.setString(6,tbBilling.getValueAt(i,3).toString());
                    try {                        
                        psbiling.setDouble(7,Valid.SetAngka(tbBilling.getValueAt(i,4).toString()));
                    } catch (Exception e) {
                        psbiling.setDouble(7,0);
                    }
                    try {
                        psbiling.setDouble(8,Valid.SetAngka(tbBilling.getValueAt(i,5).toString()));
                    } catch (Exception e) {
                        psbiling.setDouble(8,0);
                    }
                    subttl=0;
                    try {
                        if((!tbBilling.getValueAt(i,8).toString().equals("Laborat"))&&(!tbBilling.getValueAt(i,8).toString().equals("Obat"))&&(!tbBilling.getValueAt(i,8).toString().equals("Operasi"))){
                            subttl=Valid.SetAngka(tbBilling.getValueAt(i,6).toString());
                        }
                        psbiling.setDouble(9,Valid.SetAngka(tbBilling.getValueAt(i,6).toString()));                        
                    } catch (Exception e) {
                        subttl=0;
                        psbiling.setDouble(9,0);   
                    }
                    if(subttl>0){
                        Sequel.queryu2("delete from tambahan_biaya where no_rawat=? and nama_biaya=?",2,new String[]{
                            TNoRw.getText(),"Tambahan "+tbBilling.getValueAt(i,2).toString()
                        });
                        Sequel.menyimpan("tambahan_biaya","'"+TNoRw.getText()+"','Tambahan "+tbBilling.getValueAt(i,2).toString()+
                                "','"+tbBilling.getValueAt(i,6).toString()+"'","Tambahan Biaya");                        
                    }
                    if(subttl<0){
                        Sequel.queryu2("delete from pengurangan_biaya where no_rawat=? and nama_pengurangan=?",2,new String[]{
                            TNoRw.getText(),"Potongan "+tbBilling.getValueAt(i,2).toString()
                        });
                        Sequel.menyimpan("pengurangan_biaya","'"+TNoRw.getText()+"','Potongan "+tbBilling.getValueAt(i,2).toString()+
                                "','"+tbBilling.getValueAt(i,6).toString()+"'","Potongan Biaya");                        
                    }
                    try {
                        psbiling.setDouble(10,Valid.SetAngka(tbBilling.getValueAt(i,7).toString())); 
                    } catch (Exception e) {
                        psbiling.setDouble(10,0);
                    }   
                    psbiling.setString(11,tbBilling.getValueAt(i,8).toString());
                    psbiling.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(psbiling!=null){
                        psbiling.close();
                    }
                }
            }                
            
            String alamat=Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ",TNoRM.getText());
            
            //lakukan jurnal
            Sequel.queryu2("delete from tampjurnal");

            itembayar=0;besarppn=0;
            row2=tbAkunBayar.getRowCount();                
            for(r=0;r<row2;r++){
                if(Valid.SetAngka(tbAkunBayar.getValueAt(r,2).toString())>0){
                    try {
                        itembayar=Double.parseDouble(tbAkunBayar.getValueAt(r,2).toString()); 
                    } catch (Exception e) {
                        itembayar=0;
                    }    

                    if(!tbAkunBayar.getValueAt(r,4).toString().equals("")){
                        try {
                            besarppn=Valid.roundUp(Double.parseDouble(tbAkunBayar.getValueAt(r,4).toString()),100); 
                        } catch (Exception e) {
                            besarppn=0;
                        }               
                    }  

                    if(countbayar>1){
                        if(Sequel.menyimpantf2("detail_nota_inap","?,?,?,?","Akun bayar",4,new String[]{
                                TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                            })==true){
                                Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar)+"','0'",
                                                 "debet=debet+"+Double.toString(itembayar),"kd_rek='"+tbAkunBayar.getValueAt(r,1).toString()+"'");                 
                        }
                    }else if(countbayar==1){
                        if(piutang<=0){
                            if(Sequel.menyimpantf2("detail_nota_inap","?,?,?,?","Akun bayar",4,new String[]{
                                    TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar-kekurangan)
                                })==true){
                                    Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar-kekurangan)+"','0'",
                                                 "debet=debet+"+Double.toString(itembayar-kekurangan),"kd_rek='"+tbAkunBayar.getValueAt(r,1).toString()+"'"); 
                            } 
                        }else{
                            if(Sequel.menyimpantf2("detail_nota_inap","?,?,?,?","Akun bayar",4,new String[]{
                                    TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                                })==true){
                                    Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar)+"','0'",
                                                 "debet=debet+"+Double.toString(itembayar),"kd_rek='"+tbAkunBayar.getValueAt(r,1).toString()+"'");                 
                            }                                
                        }                                
                    }                        
                }  

            }

            itempiutang=0;
            row2=tabModeAkunPiutang.getRowCount();
            for(r=0;r<row2;r++){ 
                if(!tabModeAkunPiutang.getValueAt(r,3).toString().equals("")){
                    try {
                        itempiutang=Double.parseDouble(tabModeAkunPiutang.getValueAt(r,3).toString()); 
                    } catch (Exception e) {
                        itempiutang=0;
                    } 

                    if(Sequel.menyimpantf2("detail_piutang_pasien","?,?,?,?,?,?","Akun Piutang",6,new String[]{
                            TNoRw.getText(),tabModeAkunPiutang.getValueAt(r,0).toString(),tabModeAkunPiutang.getValueAt(r,2).toString(),
                            Double.toString(itempiutang),Double.toString(itempiutang),Valid.SetTgl(tabModeAkunPiutang.getValueAt(r,4).toString())
                        })==true){
                            Sequel.menyimpan("tampjurnal","'"+tabModeAkunPiutang.getValueAt(r,1).toString()+"','"+tabModeAkunPiutang.getValueAt(r,0).toString()+"','"+Double.toString(itempiutang)+"','0'",
                                                 "debet=debet+"+Double.toString(itempiutang),"kd_rek='"+tabModeAkunPiutang.getValueAt(r,1).toString()+"'");                 
                    }
                }             
            }  
            
            if(uangdeposit>0){  
                Sequel.menyimpan("tampjurnal","'"+Uang_Muka_Ranap+"','Kontra Akun Uang Muka','"+uangdeposit+"','0'",
                                 "debet=debet+"+uangdeposit,"kd_rek='"+Uang_Muka_Ranap+"'"); 
            }
            
            if((-1*ttlPotongan)>0){
                Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','Potongan Ranap','"+(-1*ttlPotongan)+"','0'","Rekening");    
            }

            if((-1*ttlRetur_Obat)>0){
                Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','Retur Obat Ranap','"+(-1*ttlRetur_Obat)+"','0'","Rekening");    
            }

            if(ttlRegistrasi>0){
                Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','Registrasi Ranap','0','"+ttlRegistrasi+"'","Rekening");    
            }

            if(ttlTambahan>0){
                Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','Tambahan Ranap','0','"+ttlTambahan+"'","Rekening");    
            }

            if(ttlResep_Pulang>0){
                Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','Resep Pulang Ranap','0','"+ttlResep_Pulang+"'","Rekening");    
            }

            if(ttlKamar>0){
                Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','Kamar Inap','0','"+ttlKamar+"'","Rekening");    
            }

            if(ttlHarian>0){
                Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','Harian Ranap','0','"+ttlHarian+"'","Rekening");    
            }
            
            if((ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)>0){
                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Tindakan Ranap','0','"+(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)+"'", 
                                 "kredit=kredit+"+(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis),"kd_rek='"+Tindakan_Ranap+"'");                            
            }

            if(ttlLaborat>0){
                Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Laborat','0','"+ttlLaborat+"'", 
                                 "kredit=kredit+"+ttlLaborat,"kd_rek='"+Laborat_Ranap+"'");                    
            }

            if(ttlRadiologi>0){
                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','Radiologi','0','"+ttlRadiologi+"'", 
                                 "kredit=kredit+"+ttlRadiologi,"kd_rek='"+Radiologi_Ranap+"'");     
            }

            if(ttlObat>0){
                Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','Obat','0','"+ttlObat+"'", 
                                 "kredit=kredit+"+ttlObat,"kd_rek='"+Obat_Ranap+"'");      
            }

            if(ttlOperasi>0){
                Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Operasi','0','"+ttlOperasi+"'", 
                                 "kredit=kredit+"+ttlOperasi,"kd_rek='"+Operasi_Ranap+"'");  
            }

            if(ttlService>0){
                Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','Biaya Service Ranap','0','"+ttlService+"'","Rekening");    
            }

            if(piutang>0){
                jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PIUTANG PASIEN RAWAT INAP, DIPOSTING OLEH "+akses.getkode());
                if((bayar+uangdeposit)>0){
                    Sequel.menyimpan2("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+
                            "',' ',CURTIME()),'Uang Muka','"+total+"','"+(bayar+uangdeposit)+"','Belum','"+akses.getkode()+"'","No.Rawat");
                }
                Sequel.queryu2("insert into piutang_pasien values ('"+TNoRw.getText()+"','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                        TNoRM.getText()+"','Belum Lunas','"+total+"','"+(bayar+uangdeposit)+"','"+piutang+"','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"')");
            }else if(piutang<=0){
                jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBAYARAN PASIEN RAWAT INAP, DIPOSTING OLEH "+akses.getkode());
                Sequel.menyimpan2("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+
                            "',' ',CURTIME()),'Pelunasan','"+total+"','"+total+"','Sudah','"+akses.getkode()+"'","No.Rawat");
            }

            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"status_bayar='Sudah Bayar'");
            Sequel.meghapus("temporary_tambahan_potongan","no_rawat",TNoRw.getText());
            koneksi.setAutoCommit(true);
            JOptionPane.showMessageDialog(null,"Proses simpan selesai...!"); 
            if(notaranap.equals("Yes")){
                this.dispose();
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);            
            JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Data yang sama dimasukkan sebelumnya...!");
        }
    }
}

package keuangan;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariObat;
import inventory.DlgPenjualan;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPeriksaLab;
import simrskhanza.DlgCariPeriksaRadiologi;
import simrskhanza.DlgCariPoli;
import inventory.DlgPemberianObat;
import javax.swing.event.DocumentEvent;
import simrskhanza.DlgPenanggungJawab;
import simrskhanza.DlgPeriksaLaboratorium;
import simrskhanza.DlgPeriksaRadiologi;
import simrskhanza.DlgRawatJalan;
import simrskhanza.DlgTagihanOperasi;

/**
 *
 * @author perpustakaan
 */
public class DlgBilingRalan extends javax.swing.JDialog {
    private DefaultTableModel tabModeRwJlDr;
    private final DefaultTableModel tabModeTambahan,tabModePotongan,tabModeAkunBayar,tabModeAkunPiutang,tabModeLab,tabModeRad,tabModeApotek;
    private boolean sukses=false;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb(); 
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    public DlgCariPoli poli=new DlgCariPoli(null,false);   
    public DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private double ttl=0,y=0,subttl=0,ralanparamedis=0,piutang=0,itembayar=0,itempiutang=0, 
                   bayar=0,total=0,tamkur=0,detailjs=0,detailbhp=0,ppn=0,besarppn=0,tagihanppn=0,
                   ttlLaborat=0,ttlRadiologi=0,ttlObat=0,ttlRalan_Dokter=0,ttlRalan_Paramedis=0,
                   ttlTambahan=0,ttlPotongan=0,ttlRegistrasi=0,ttlRalan_Dokter_Param=0,ppnobat=0,ttlOperasi=0,
                   kekurangan=0;
    private int i,r,cek,row2,countbayar=0,z=0,jml=0;
    private String nota_jalan="",dokterrujukan="",polirujukan="",status="",biaya="",tambahan="",totals="",kdptg="",nmptg="",kd_pj="",notaralan="",centangdokterralan="",
            rinciandokterralan="",Tindakan_Ralan="",Laborat_Ralan="",Radiologi_Ralan="",
            Obat_Ralan="",Registrasi_Ralan="",Tambahan_Ralan="",Potongan_Ralan="",
            Operasi_Ralan="",tampilkan_ppnobat_ralan="",rincianoperasi="",centangobatralan="No",
            sqlpscekbilling="select count(billing.no_rawat) from billing where billing.no_rawat=?",
            sqlpscarirm="select no_rkm_medis from reg_periksa where no_rawat=?",
            sqlpscaripasien="select nm_pasien from pasien where no_rkm_medis=? ",
            sqlpsreg="select reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,"+
                    "reg_periksa.kd_poli,reg_periksa.no_rawat,reg_periksa.biaya_reg,current_time() as jam,"+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa where reg_periksa.no_rawat=?",
            sqlpscaripoli="select nm_poli from poliklinik where kd_poli=?",
            sqlpscarialamat="select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?",
            sqlpsrekening="select * from set_akun_ralan",
            sqlpsdokterralan="select dokter.nm_dokter from reg_periksa "+
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=?",
            sqlpsdokterralan2="select dokter.nm_dokter from rujukan_internal_poli "+
                            "inner join dokter on rujukan_internal_poli.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=?",
            sqlpscaripoli2="select poliklinik.nm_poli from rujukan_internal_poli "+
                            "inner join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli "+
                            "where no_rawat=?",
            sqlpscariralandokter="select jns_perawatan.nm_perawatan,rawat_jl_dr.biaya_rawat as total_byrdr,"+
                    "count(rawat_jl_dr.kd_jenis_prw) as jml, "+
                    "sum(rawat_jl_dr.biaya_rawat) as biaya,"+
                    "sum(rawat_jl_dr.bhp) as totalbhp,"+
                    "(sum(rawat_jl_dr.material)+sum(rawat_jl_dr.menejemen)+sum(rawat_jl_dr.kso)) as totalmaterial,"+
                    "rawat_jl_dr.tarif_tindakandr,"+
                    "sum(rawat_jl_dr.tarif_tindakandr) as totaltarif_tindakandr "+
                    "from rawat_jl_dr inner join jns_perawatan "+
                    "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_dr.no_rawat=? group by jns_perawatan.nm_perawatan",
            sqlpscariralanperawat="select jns_perawatan.nm_perawatan,rawat_jl_pr.biaya_rawat as total_byrpr,"+
                    "count(rawat_jl_pr.kd_jenis_prw) as jml, "+
                    "sum(rawat_jl_pr.biaya_rawat) as biaya, "+
                    "sum(rawat_jl_pr.bhp) as totalbhp,"+
                    "(sum(rawat_jl_pr.material)+sum(rawat_jl_pr.menejemen)+sum(rawat_jl_pr.kso)) as totalmaterial,"+
                    "sum(rawat_jl_pr.tarif_tindakanpr) as totaltarif_tindakanpr "+
                    "from rawat_jl_pr inner join jns_perawatan "+
                    "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_pr.no_rawat=? group by jns_perawatan.nm_perawatan ",
            sqlpscariralandrpr="select jns_perawatan.nm_perawatan,rawat_jl_drpr.biaya_rawat as total_byrdrpr,"+
                    "count(rawat_jl_drpr.kd_jenis_prw) as jml, "+
                    "sum(rawat_jl_drpr.biaya_rawat) as biaya,"+
                    "sum(rawat_jl_drpr.bhp) as totalbhp,"+
                    "(sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.menejemen)+sum(rawat_jl_drpr.kso)) as totalmaterial,"+
                    "rawat_jl_drpr.tarif_tindakandr,"+
                    "sum(rawat_jl_drpr.tarif_tindakanpr) as totaltarif_tindakanpr, "+
                    "sum(rawat_jl_drpr.tarif_tindakandr) as totaltarif_tindakandr "+
                    "from rawat_jl_drpr inner join jns_perawatan "+
                    "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_drpr.no_rawat=? group by jns_perawatan.nm_perawatan",
            sqlpscarilab="select jns_perawatan_lab.nm_perawatan, count(periksa_lab.kd_jenis_prw) as jml,periksa_lab.biaya as biaya, "+
                    "sum(periksa_lab.biaya) as total,jns_perawatan_lab.kd_jenis_prw,sum(periksa_lab.tarif_perujuk+periksa_lab.tarif_tindakan_dokter) as totaldokter, "+
                    "sum(periksa_lab.tarif_tindakan_petugas) as totalpetugas,sum(periksa_lab.kso) as totalkso,sum(periksa_lab.bhp) as totalbhp "+
                    " from periksa_lab inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "+
                    " periksa_lab.no_rawat=? group by periksa_lab.kd_jenis_prw  ",
            sqlpscariobat="select databarang.nama_brng,jenis.nama,detail_pemberian_obat.biaya_obat,"+
                          "sum(detail_pemberian_obat.jml) as jml,sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah) as tambahan,"+
                          "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total, "+
                          "sum((detail_pemberian_obat.h_beli*detail_pemberian_obat.jml)) as totalbeli "+
                          "from detail_pemberian_obat inner join databarang inner join jenis "+
                          "on detail_pemberian_obat.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns where "+
                          "detail_pemberian_obat.no_rawat=? group by detail_pemberian_obat.kode_brng order by jenis.nama",
            sqlpsdetaillab="select sum(detail_periksa_lab.biaya_item) as total,sum(detail_periksa_lab.bagian_perujuk+detail_periksa_lab.bagian_dokter) as totaldokter, "+
                           "sum(detail_periksa_lab.bagian_laborat) as totalpetugas,sum(detail_periksa_lab.kso) as totalkso,sum(detail_periksa_lab.bhp) as totalbhp "+
                           "from detail_periksa_lab where detail_periksa_lab.no_rawat=? "+
                           "and detail_periksa_lab.kd_jenis_prw=?",
            sqlpsobatlangsung="select besar_tagihan from tagihan_obat_langsung where "+
                    "no_rawat=? ",
            sqlpsreturobat="select databarang.nama_brng,detreturjual.h_retur, "+
                        "(detreturjual.jml_retur * -1) as jml, "+
                        "(detreturjual.subtotal * -1) as ttl from detreturjual inner join databarang inner join returjual "+
                        "on detreturjual.kode_brng=databarang.kode_brng "+
                        "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual=? group by databarang.nama_brng",
            sqlpstambahan="select nama_biaya, besar_biaya from tambahan_biaya where no_rawat=?  ",
            sqlpsbiling="insert into billing values(?,?,?,?,?,?,?,?,?,?,?)",
            sqlpstemporary="insert into temporary_bayar_ralan values('0',?,?,?,?,?,?,?,?,?,'','','','','','','','')",
            sqlpspotongan="select nama_pengurangan,besar_pengurangan from pengurangan_biaya where no_rawat=?",
            sqlpsbilling="select no,nm_perawatan, if(biaya<>0,biaya,null) as satu, if(jumlah<>0,jumlah,null) as dua,"+
                        "if(tambahan<>0,tambahan,null) as tiga, if(totalbiaya<>0,totalbiaya,null) as empat,pemisah,status "+
                        "from billing where no_rawat=? order by noindex",
            sqlpscariradiologi="select jns_perawatan_radiologi.nm_perawatan, count(periksa_radiologi.kd_jenis_prw) as jml,periksa_radiologi.biaya as biaya, "+
                    "sum(periksa_radiologi.biaya) as total,jns_perawatan_radiologi.kd_jenis_prw,sum(periksa_radiologi.tarif_perujuk+periksa_radiologi.tarif_tindakan_dokter) as totaldokter, "+
                    "sum(periksa_radiologi.tarif_tindakan_petugas) as totalpetugas,sum(periksa_radiologi.kso) as totalkso,sum(periksa_radiologi.bhp) as totalbhp "+
                    " from periksa_radiologi inner join jns_perawatan_radiologi on jns_perawatan_radiologi.kd_jenis_prw=periksa_radiologi.kd_jenis_prw where "+
                    " periksa_radiologi.no_rawat=? group by periksa_radiologi.kd_jenis_prw  ",
            sqlpsnota="insert into nota_jalan values(?,?,?,?)",
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
                         "operasi.no_rawat=?",
            sqlpsobatoperasi="select obatbhp_ok.nm_obat,beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah, "+
                    "(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                    "from obatbhp_ok inner join beri_obat_operasi "+
                    "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat where "+
                    "beri_obat_operasi.no_rawat=? group by obatbhp_ok.nm_obat",
            sqlpstamkur="select biaya from temporary_tambahan_potongan where no_rawat=? and nama_tambahan=? and status=?";
    private String[] Nama_Akun_Piutang,Kode_Rek_Piutang,Kd_PJ,Besar_Piutang,Jatuh_Tempo,
            Nama_Akun_Bayar,Kode_Rek_Bayar,Bayar,PPN_Persen,PPN_Besar;
            
    private PreparedStatement pscaripoli2,pscekbilling,pscarirm,pscaripasien,psreg,pscaripoli,pscarialamat,psrekening,
            psdokterralan,psdokterralan2,pscariralandokter,pscariralanperawat,pscariralandrpr,pscarilab,pscariobat,psdetaillab,
            psobatlangsung,psreturobat,pstambahan,psbiling,pstemporary,pspotongan,psbilling,pscariradiologi,
            pstamkur,psnota,psoperasi,psobatoperasi,psakunbayar,psakunpiutang;
    private ResultSet rscekbilling,rscarirm,rscaripasien,rsreg,rscaripoli,rscarialamat,rsrekening,rsobatoperasi,
            rsdokterralan,rsdokterralan2,rscariralandokter,rscariralanperawat,rscariralandrpr,rscarilab,rscariobat,rsdetaillab,
            rsobatlangsung,rsreturobat,rstambahan,rspotongan,rsbilling,rscariradiologi,rstamkur,rsoperasi,
            rsakunbayar,rsakunpiutang,rscaripoli2;
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();

    /** Creates new form DlgBiling
     * @param parent
     * @param modal */
    public DlgBilingRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabModeRwJlDr=new DefaultTableModel(null,new Object[]{
            "Pilih","Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jml","Tambahan","Total Biaya",""}){
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
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(420);
            }else if(i==3){
                column.setPreferredWidth(10);
            }else if(i==4){
                column.setPreferredWidth(95);
            }else if(i==5){
                column.setPreferredWidth(30);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbBilling.setDefaultRenderer(Object.class, new WarnaTable());
        
        //tambahan biaya
        tabModeTambahan=new DefaultTableModel(null,new Object[]{"Tambahan Biaya","Besar Biaya"}){
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
        
        tabModeLab=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","Tanggal","Jam","Dokter Perujuk","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLab.setModel(tabModeLab);

        tbLab.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbLab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 5; i++) {
            TableColumn column = tbLab.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(100);
            }
        }
        tbLab.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRad=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","Tanggal","Jam","Dokter Perujuk","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRadiologi.setModel(tabModeRad);

        tbRadiologi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 5; i++) {
            TableColumn column = tbRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(100);
            }
        }
        tbRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeApotek=new DefaultTableModel(null,new Object[]{
            "No.Resep","Tanggal","Jam","Dokter Peresep","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbApotek.setModel(tabModeApotek);

        tbApotek.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbApotek.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 5; i++) {
            TableColumn column = tbApotek.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(100);
            }
        }
        tbApotek.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kdpoli.setDocument(new batasInput((byte)5).getKata(kdpoli));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));        
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBilingRalan")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    kddokter.requestFocus();
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBilingRalan")){
                    if(poli.getTable().getSelectedRow()!= -1){
                        kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                        nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    }  
                    kdpoli.requestFocus();
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
                if(akses.getform().equals("DlgBilingRalan")){
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
                if(akses.getform().equals("DlgBilingRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        
        try {
            notaralan=Sequel.cariIsi("select cetaknotasimpanralan from set_nota"); 
            centangdokterralan=Sequel.cariIsi("select centangdokterralan from set_nota"); 
            rinciandokterralan=Sequel.cariIsi("select rinciandokterralan from set_nota"); 
            rincianoperasi=Sequel.cariIsi("select rincianoperasi from set_nota"); 
            tampilkan_ppnobat_ralan=Sequel.cariIsi("select tampilkan_ppnobat_ralan from set_nota"); 
            centangobatralan=Sequel.cariIsi("select centangobatralan from set_nota"); 
        } catch (Exception e) {
            notaralan="No"; 
            centangdokterralan="No";
            rinciandokterralan="No";
            rincianoperasi="No";
            tampilkan_ppnobat_ralan="No";
            centangobatralan="No";
        }
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TCari1.setDocument(new batasInput((int)100).getKata(TCari1));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
            });
            TCari1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari1.getText().length()>2){
                        btnCariPiutangActionPerformed(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari1.getText().length()>2){
                        btnCariPiutangActionPerformed(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari1.getText().length()>2){
                        btnCariPiutangActionPerformed(null);
                    }
                }
            });
        } 
        
        try {
            psrekening=koneksi.prepareStatement(sqlpsrekening);
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Tindakan_Ralan=rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Laborat_Ralan=rsrekening.getString("Suspen_Piutang_Laborat_Ralan");
                    Radiologi_Ralan=rsrekening.getString("Suspen_Piutang_Radiologi_Ralan");
                    Obat_Ralan=rsrekening.getString("Suspen_Piutang_Obat_Ralan");
                    Registrasi_Ralan=rsrekening.getString("Registrasi_Ralan");
                    Tambahan_Ralan=rsrekening.getString("Tambahan_Ralan");
                    Potongan_Ralan=rsrekening.getString("Potongan_Ralan");
                    Operasi_Ralan=rsrekening.getString("Suspen_Piutang_Operasi_Ralan");
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

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnInputTindakan = new javax.swing.JMenuItem();
        MnInputObat = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnTambahan = new javax.swing.JMenuItem();
        MnPotongan = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnObatLangsung = new javax.swing.JMenuItem();
        MnPoli = new javax.swing.JMenuItem();
        MnDokter = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnCariPeriksaLab = new javax.swing.JMenuItem();
        MnCariRadiologi = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnHapusTagihan = new javax.swing.JMenuItem();
        WindowGantiDokterPoli = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan1 = new widget.Button();
        jLabel13 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        btnCariDokter = new widget.Button();
        WindowObatLangsung = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel8 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan2 = new widget.Button();
        BtnBatal1 = new widget.Button();
        WindowTambahanBiaya = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbTambahan = new widget.Table();
        panelisi1 = new widget.panelisi();
        label15 = new widget.Label();
        norawat = new widget.TextBox();
        BtnTambah = new widget.Button();
        BtnSimpan3 = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowGantiPoli = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel14 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        btnCariPoli = new widget.Button();
        WindowPotonganBiaya = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbPotongan = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        norawatpotongan = new widget.TextBox();
        BtnTambahPotongan = new widget.Button();
        BtnSimpanPotongan = new widget.Button();
        BtnHapusPotongan = new widget.Button();
        BtnKeluarPotongan = new widget.Button();
        WindowGantiPenjab = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnPenjab = new widget.Button();
        PopupBayar = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        PopupPiutang = new javax.swing.JPopupMenu();
        ppBersihkan1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass1 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel4 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbBilling = new widget.Table();
        panelBayar = new widget.panelisi();
        TtlSemua = new widget.TextBox();
        TKembali = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel9 = new widget.Label();
        chkPotongan = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkTarifDokter = new widget.CekBox();
        chkTarifPrm = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        chkTambahan = new widget.CekBox();
        chkObat = new widget.CekBox();
        jLabel12 = new widget.Label();
        chkSarpras = new widget.CekBox();
        TagihanPPn = new widget.TextBox();
        chkAdministrasi = new widget.CekBox();
        scrollPane3 = new widget.ScrollPane();
        tbAkunBayar = new widget.Table();
        jLabel6 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        tbAkunPiutang = new widget.Table();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCariBayar = new widget.Button();
        TCari1 = new widget.TextBox();
        btnCariPiutang = new widget.Button();
        panelPermintaan = new widget.panelisi();
        scrollPane5 = new widget.ScrollPane();
        tbLab = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        tbRadiologi = new widget.Table();
        scrollPane7 = new widget.ScrollPane();
        tbApotek = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnNota = new widget.Button();
        BtnView = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnInputTindakan.setBackground(new java.awt.Color(255, 255, 254));
        MnInputTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnInputTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputTindakan.setText("Input Tindakan Ralan");
        MnInputTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputTindakan.setName("MnInputTindakan"); // NOI18N
        MnInputTindakan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnInputTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputTindakanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputTindakan);

        MnInputObat.setBackground(new java.awt.Color(255, 255, 254));
        MnInputObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputObat.setForeground(new java.awt.Color(50, 50, 50));
        MnInputObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputObat.setText("Input Obat/Barang/Alkes");
        MnInputObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputObat.setName("MnInputObat"); // NOI18N
        MnInputObat.setPreferredSize(new java.awt.Dimension(250, 25));
        MnInputObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputObat);

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

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(250, 28));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnOperasi);

        MnObatLangsung.setBackground(new java.awt.Color(255, 255, 254));
        MnObatLangsung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObatLangsung.setForeground(new java.awt.Color(50, 50, 50));
        MnObatLangsung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObatLangsung.setText("Tagihan BHP & Obat");
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

        MnPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoli.setForeground(new java.awt.Color(50, 50, 50));
        MnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoli.setText("Ganti Poliklinik");
        MnPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoli.setName("MnPoli"); // NOI18N
        MnPoli.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPoli);

        MnDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokter.setText("Ganti Dokter Poli");
        MnDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDokter.setName("MnDokter"); // NOI18N
        MnDokter.setPreferredSize(new java.awt.Dimension(250, 25));
        MnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokterActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDokter);

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

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Data Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Data Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

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

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjualan);

        MnHapusTagihan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihan.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTagihan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihan.setText("Hapus Nota Salah");
        MnHapusTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihan.setName("MnHapusTagihan"); // NOI18N
        MnHapusTagihan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnHapusTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihan);

        WindowGantiDokterPoli.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokterPoli.setName("WindowGantiDokterPoli"); // NOI18N
        WindowGantiDokterPoli.setUndecorated(true);
        WindowGantiDokterPoli.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Dokter Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaBawah(new java.awt.Color(240, 245, 235));
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
        TDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDokterKeyPressed(evt);
            }
        });
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

        WindowGantiDokterPoli.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        WindowObatLangsung.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowObatLangsung.setName("WindowObatLangsung"); // NOI18N
        WindowObatLangsung.setUndecorated(true);
        WindowObatLangsung.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total BHP & Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame2.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(240, 245, 235));
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

        jLabel8.setText("Total :");
        jLabel8.setName("jLabel8"); // NOI18N
        internalFrame2.add(jLabel8);
        jLabel8.setBounds(0, 30, 57, 23);

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

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+S");
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
        BtnBatal1.setMnemonic('H');
        BtnBatal1.setText("Hapus");
        BtnBatal1.setToolTipText("Alt+H");
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

        WindowObatLangsung.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowTambahanBiaya.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowTambahanBiaya.setName("WindowTambahanBiaya"); // NOI18N
        WindowTambahanBiaya.setUndecorated(true);
        WindowTambahanBiaya.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tambah Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        norawat.setEditable(false);
        norawat.setName("norawat"); // NOI18N
        norawat.setPreferredSize(new java.awt.Dimension(150, 23));
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        panelisi1.add(norawat);

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

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar1.setMnemonic('U');
        BtnKeluar1.setText("Tutup");
        BtnKeluar1.setToolTipText("Alt+U");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowTambahanBiaya.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowGantiPoli.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiPoli.setName("WindowGantiPoli"); // NOI18N
        WindowGantiPoli.setUndecorated(true);
        WindowGantiPoli.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel14.setText("Poli Dituju :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame5.add(jLabel14);
        jLabel14.setBounds(0, 32, 77, 23);

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

        WindowPotonganBiaya.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPotonganBiaya.setName("WindowPotonganBiaya"); // NOI18N
        WindowPotonganBiaya.setUndecorated(true);
        WindowPotonganBiaya.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Potongan Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame6.add(scrollPane2, java.awt.BorderLayout.CENTER);

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

        BtnKeluarPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarPotongan.setMnemonic('U');
        BtnKeluarPotongan.setText("Tutup");
        BtnKeluarPotongan.setToolTipText("Alt+U");
        BtnKeluarPotongan.setName("BtnKeluarPotongan"); // NOI18N
        BtnKeluarPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluarPotongan);

        internalFrame6.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowPotonganBiaya.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        WindowGantiPenjab.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiPenjab.setName("WindowGantiPenjab"); // NOI18N
        WindowGantiPenjab.setUndecorated(true);
        WindowGantiPenjab.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame7.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('P');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+P");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnCloseIn5);
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
        internalFrame7.add(BtnSimpan5);
        BtnSimpan5.setBounds(405, 30, 100, 30);

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

        WindowGantiPenjab.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        PopupBayar.setName("PopupBayar"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
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

        PopupPiutang.setName("PopupPiutang"); // NOI18N

        ppBersihkan1.setBackground(new java.awt.Color(255, 255, 254));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Billing/Pembayaran Ralan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass1.setPreferredSize(new java.awt.Dimension(100, 45));
        panelGlass1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 10));

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass1.add(jLabel3);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(150, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass1.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass1.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(320, 23));
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

        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-07-2020 19:31:14" }));
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

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBilling.setToolTipText("");
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
        TtlSemua.setBounds(110, 37, 230, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        TKembali.setName("TKembali"); // NOI18N
        panelBayar.add(TKembali);
        TKembali.setBounds(110, 377, 230, 23);

        jLabel5.setText("Bayar : Rp.");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel5);
        jLabel5.setBounds(19, 67, 90, 23);

        jLabel9.setText("Total Tagihan : Rp.");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel9);
        jLabel9.setBounds(0, 37, 109, 23);

        chkPotongan.setSelected(true);
        chkPotongan.setText("Potongan");
        chkPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPotongan.setName("chkPotongan"); // NOI18N
        chkPotongan.setOpaque(false);
        chkPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPotonganActionPerformed(evt);
            }
        });
        panelBayar.add(chkPotongan);
        chkPotongan.setBounds(395, 8, 90, 23);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelBayar.add(chkLaborat);
        chkLaborat.setBounds(15, 8, 95, 23);

        chkTarifDokter.setSelected(true);
        chkTarifDokter.setText("Tarif Dokter");
        chkTarifDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTarifDokter.setName("chkTarifDokter"); // NOI18N
        chkTarifDokter.setOpaque(false);
        chkTarifDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarifDokterActionPerformed(evt);
            }
        });
        panelBayar.add(chkTarifDokter);
        chkTarifDokter.setBounds(205, 8, 90, 23);

        chkTarifPrm.setSelected(true);
        chkTarifPrm.setText("Tarif Paramedis");
        chkTarifPrm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTarifPrm.setName("chkTarifPrm"); // NOI18N
        chkTarifPrm.setOpaque(false);
        chkTarifPrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTarifPrmActionPerformed(evt);
            }
        });
        panelBayar.add(chkTarifPrm);
        chkTarifPrm.setBounds(585, 8, 120, 23);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelBayar.add(chkRadiologi);
        chkRadiologi.setBounds(110, 8, 90, 23);

        chkTambahan.setSelected(true);
        chkTambahan.setText("Tambahan");
        chkTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTambahan.setName("chkTambahan"); // NOI18N
        chkTambahan.setOpaque(false);
        chkTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTambahanActionPerformed(evt);
            }
        });
        panelBayar.add(chkTambahan);
        chkTambahan.setBounds(300, 8, 90, 23);

        chkObat.setSelected(true);
        chkObat.setText("Obat");
        chkObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkObat.setName("chkObat"); // NOI18N
        chkObat.setOpaque(false);
        chkObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkObatActionPerformed(evt);
            }
        });
        panelBayar.add(chkObat);
        chkObat.setBounds(490, 8, 90, 23);

        jLabel12.setText("Tagihan + PPN : Rp.");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel12);
        jLabel12.setBounds(531, 37, 110, 23);

        chkSarpras.setSelected(true);
        chkSarpras.setText("Sarpras");
        chkSarpras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSarpras.setName("chkSarpras"); // NOI18N
        chkSarpras.setOpaque(false);
        chkSarpras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSarprasActionPerformed(evt);
            }
        });
        panelBayar.add(chkSarpras);
        chkSarpras.setBounds(805, 8, 90, 23);

        TagihanPPn.setEditable(false);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TagihanPPn.setHighlighter(null);
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        panelBayar.add(TagihanPPn);
        TagihanPPn.setBounds(642, 37, 230, 23);

        chkAdministrasi.setSelected(true);
        chkAdministrasi.setText("Administrasi");
        chkAdministrasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAdministrasi.setName("chkAdministrasi"); // NOI18N
        chkAdministrasi.setOpaque(false);
        chkAdministrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAdministrasiActionPerformed(evt);
            }
        });
        panelBayar.add(chkAdministrasi);
        chkAdministrasi.setBounds(710, 8, 95, 23);

        scrollPane3.setComponentPopupMenu(PopupBayar);
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

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
        scrollPane3.setViewportView(tbAkunBayar);

        panelBayar.add(scrollPane3);
        scrollPane3.setBounds(110, 92, 790, 125);

        jLabel6.setText("Kembali : Rp.");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel6);
        jLabel6.setBounds(19, 377, 90, 23);

        scrollPane4.setComponentPopupMenu(PopupPiutang);
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

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
        scrollPane4.setViewportView(tbAkunPiutang);

        panelBayar.add(scrollPane4);
        scrollPane4.setBounds(110, 247, 790, 125);

        jLabel16.setText("Piutang : Rp.");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(95, 23));
        panelBayar.add(jLabel16);
        jLabel16.setBounds(19, 222, 90, 23);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelBayar.add(TCari);
        TCari.setBounds(110, 67, 762, 23);

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
        BtnCariBayar.setBounds(875, 67, 25, 23);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelBayar.add(TCari1);
        TCari1.setBounds(110, 222, 762, 23);

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
        btnCariPiutang.setBounds(875, 222, 25, 23);

        TabRawat.addTab("Pembayaran", panelBayar);

        panelPermintaan.setBorder(null);
        panelPermintaan.setName("panelPermintaan"); // NOI18N
        panelPermintaan.setPreferredSize(new java.awt.Dimension(100, 137));
        panelPermintaan.setLayout(new java.awt.GridLayout(3, 0));

        scrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "1. Permintaan Laborat : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane5.setComponentPopupMenu(PopupBayar);
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbLab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbLab.setToolTipText("");
        tbLab.setComponentPopupMenu(PopupBayar);
        tbLab.setName("tbLab"); // NOI18N
        scrollPane5.setViewportView(tbLab);

        panelPermintaan.add(scrollPane5);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "2. Permintaan Radiologi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setComponentPopupMenu(PopupBayar);
        scrollPane6.setName("scrollPane6"); // NOI18N
        scrollPane6.setOpaque(true);

        tbRadiologi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRadiologi.setToolTipText("");
        tbRadiologi.setComponentPopupMenu(PopupBayar);
        tbRadiologi.setName("tbRadiologi"); // NOI18N
        scrollPane6.setViewportView(tbRadiologi);

        panelPermintaan.add(scrollPane6);

        scrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "3. Permintaan Resep : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane7.setComponentPopupMenu(PopupBayar);
        scrollPane7.setName("scrollPane7"); // NOI18N
        scrollPane7.setOpaque(true);

        tbApotek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbApotek.setToolTipText("");
        tbApotek.setComponentPopupMenu(PopupBayar);
        tbApotek.setName("tbApotek"); // NOI18N
        scrollPane7.setViewportView(tbApotek);

        panelPermintaan.add(scrollPane7);

        TabRawat.addTab("Status Permintaan", panelPermintaan);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnSimpan);

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('N');
        BtnNota.setText(" Nota");
        BtnNota.setToolTipText("Alt+N");
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
        panelGlass8.add(BtnNota);

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
        panelGlass8.add(BtnView);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(akses.getbilling_ralan()==true){
            try {
                pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
                try {
                    pscekbilling.setString(1,TNoRw.getText());
                    rscekbilling=pscekbilling.executeQuery();
                    if(rscekbilling.next()){
                        i=rscekbilling.getInt(1);
                    }
                } catch (Exception e) {
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

                if(i<=0){
                    int jawab=JOptionPane.showConfirmDialog(null, "Data pembayaran belum tersimpan, apa anda mau menyimpannya...????","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if(jawab==JOptionPane.YES_OPTION){
                        chkLaborat.setSelected(true);
                        chkRadiologi.setSelected(true);
                        isRawat();
                        BtnSimpanActionPerformed(evt);
                        dispose();
                    }else{
                        WindowObatLangsung.dispose();
                        WindowGantiDokterPoli.dispose();
                        WindowTambahanBiaya.dispose();
                        WindowGantiPoli.dispose();
                        WindowPotonganBiaya.dispose();
                        dispose();    
                    }                
                }else if(i>0){
                    WindowObatLangsung.dispose();
                    WindowGantiDokterPoli.dispose();
                    WindowTambahanBiaya.dispose();
                    WindowGantiPoli.dispose();
                    WindowPotonganBiaya.dispose();
                    dispose();                
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            WindowObatLangsung.dispose();
            WindowGantiDokterPoli.dispose();
            WindowTambahanBiaya.dispose();
            WindowGantiPoli.dispose();
            WindowPotonganBiaya.dispose();
            dispose(); 
        }
                        
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnView,BtnNota);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
        Valid.pindah(evt,BtnKeluar,BtnNota);
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
            if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                Valid.textKosong(TNoRw,"Pasien");
            }else if(tabModeRwJlDr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabModeRwJlDr.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try{
                    koneksi.setAutoCommit(false);
                    Sequel.queryu2("delete from temporary_bayar_ralan where temp9='"+akses.getkode()+"'"); 
                    for(i=0;i<tabModeRwJlDr.getRowCount();i++){  
                        if(tabModeRwJlDr.getValueAt(i,0).toString().equals("true")){
                            biaya="";
                            try {
                                biaya=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,4).toString())); 
                            } catch (Exception e) {
                                biaya="";
                            }                            
                            tambahan="";
                            try {
                                tambahan=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,6).toString())); 
                            } catch (Exception e) {
                                tambahan="";
                            }
                            totals="";
                            try {
                                totals=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,7).toString())); 
                            } catch (Exception e) {
                                totals="";
                            }
                            
                            pstemporary=koneksi.prepareStatement(sqlpstemporary);
                            try {
                                pstemporary.setString(1,tabModeRwJlDr.getValueAt(i,1).toString().replaceAll("'","`"));
                                pstemporary.setString(2,tabModeRwJlDr.getValueAt(i,2).toString().replaceAll("'","`"));
                                pstemporary.setString(3,tabModeRwJlDr.getValueAt(i,3).toString().replaceAll("'","`"));
                                pstemporary.setString(4,biaya);
                                try {
                                    pstemporary.setString(5,tabModeRwJlDr.getValueAt(i,5).toString().replaceAll("'","`"));
                                } catch (Exception e) {
                                    pstemporary.setString(5,"");
                                }                            
                                pstemporary.setString(6,tambahan);
                                pstemporary.setString(7,totals);
                                try {
                                   pstemporary.setString(8,tabModeRwJlDr.getValueAt(i,8).toString().replaceAll("'","`"));  
                                } catch (Exception e) {
                                    pstemporary.setString(8,""); 
                                }    
                                pstemporary.setString(9,akses.getkode());
                                pstemporary.executeUpdate();                         
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(pstemporary != null){
                                    pstemporary.close();
                                } 
                            }
                        }                
                    }
                    
                    if(piutang<=0){                        
                        Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','PPN',':','','','','','"+Valid.SetAngka(besarppn)+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL BAYAR',':','','','','','"+TagihanPPn.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                    }else if(piutang>0){                                                   
                        Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','PPN',':','','','','','"+Valid.SetAngka(besarppn)+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','TAGIHAN + PPN',':','','','','','"+TagihanPPn.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','EKSES',':','','','','','"+Valid.SetAngka(bayar)+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                        Sequel.menyimpan("temporary_bayar_ralan","'0','SISA PIUTANG',':','','','','','"+Valid.SetAngka(piutang)+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan");                                           
                    }

                    i = 0;
                    try{
                          biaya = (String)JOptionPane.showInputDialog(null,"Silahkan pilih nota yang mau dicetak!","Nota",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Nota", "Kwitansi", "Nota & Kwitansi"},"Nota");
                          switch (biaya) {
                                case "Nota":
                                      i=1;
                                      break;
                                case "Kwitansi":
                                      i=2;
                                      break;
                                case "Nota & Kwitansi":
                                      i=3;
                                      break;
                          }
                    }catch(Exception e){
                          i=0;
                    }            

                    if(i>0){                       
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText());
                        if(i==1){
                            Valid.panggilUrl("billing/LaporanBilling.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                        }else if(i==2){
                            if(piutang>0){
                                Valid.panggilUrl("billing/LaporanBilling7.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                        "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4)); 
                            }else if(piutang<=0){
                                Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                        "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                            }
                        }else if(i==3){
                            Valid.panggilUrl("billing/LaporanBilling.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                            if(piutang>0){
                                Valid.panggilUrl("billing/LaporanBilling7.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                        "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4)); 
                            }else if(piutang<=0){
                                Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                        "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                            }                                
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                    
                    koneksi.setAutoCommit(true);
                    this.setCursor(Cursor.getDefaultCursor());
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

    private void BtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnViewActionPerformed
            Object[] options = {"Tagihan Masuk", "Piutang Pasien"};
            
            String input;
            i = 0;
            try{
                input = (String)JOptionPane.showInputDialog(null,"Silahkan pilih yang mau ditampilkan!","Keuangan",JOptionPane.QUESTION_MESSAGE,null,options,"Nota 1");
                switch (input) {
                    case "Tagihan Masuk":
                        i=1;
                        break;
                    case "Piutang Pasien":
                        i=2;
                        break;
                }
            }catch(Exception e){
                i=0;
            }        
            
            if(i>0){
                if(i==1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtBiaya billing=new DlgLhtBiaya(null,false);      
                    billing.setSize(this.getWidth(),this.getHeight());
                    billing.setLocationRelativeTo(this);
                    billing.setAlwaysOnTop(false);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else if(i==2){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtPiutang billing=new DlgLhtPiutang(null,false);
                    billing.tampil();   
                    billing.isCek();
                    billing.setSize(this.getWidth(),this.getHeight());
                    billing.setLocationRelativeTo(this);
                    billing.setAlwaysOnTop(false);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }   
    }//GEN-LAST:event_BtnViewActionPerformed

    private void BtnViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnViewKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnViewActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnNota,BtnKeluar);
        }
    }//GEN-LAST:event_BtnViewKeyPressed

private void tbBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBillingMouseClicked
       if(tabModeRwJlDr.getRowCount()!=0){      
           if(tbBilling.getSelectedRow()>-1){
               if(evt.getClickCount()==1){
                    i=tbBilling.getSelectedColumn();
                    if(i==1){
                        try {
                            akses.setform("DlgBilingRalan");
                            switch (tbBilling.getValueAt(tbBilling.getSelectedRow(),i).toString()) {
                                case "Tindakan":
                                    if(akses.gettindakan_ralan()==true){
                                        MnInputTindakanActionPerformed(null);
                                    }                            
                                    break;
                                case "Obat & BHP":
                                    if(akses.getberi_obat()==true){
                                        MnInputObatActionPerformed(null);
                                    }                            
                                    //dispose();
                                    break;
                                case "Tambahan Biaya":
                                    if(akses.gettambahan_biaya()==true){
                                        MnTambahanActionPerformed(null);
                                    }                            
                                    break;
                                case "Potongan Biaya":
                                    if(akses.getpotongan_biaya()==true){
                                        MnPotonganActionPerformed(null);
                                    }
                                    break;
                            }                     
                        } catch (Exception e) {
                            akses.setform("DlgBilingRalan");
                            switch (tbBilling.getValueAt(tbBilling.getSelectedRow(),i).toString()) {
                                case "Tindakan":
                                    if(akses.gettindakan_ralan()==true){
                                        MnInputTindakanActionPerformed(null);
                                    }                            
                                    break;
                                case "Obat & BHP":
                                    if(akses.getberi_obat()==true){
                                        MnInputObatActionPerformed(null);
                                    }                            
                                    //dispose();
                                    break;
                                case "Tambahan Biaya":
                                    if(akses.gettambahan_biaya()==true){
                                        MnTambahanActionPerformed(null);
                                    }                            
                                    break;
                                case "Potongan Biaya":
                                    if(akses.getpotongan_biaya()==true){
                                        MnPotonganActionPerformed(null);
                                    }
                                    break;
                            }                     
                        }                        
                    }
                }
           }
        }
}//GEN-LAST:event_tbBillingMouseClicked

private void tbBillingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBillingKeyPressed
        if(tbBilling.getRowCount()!=0){
            if(tbBilling.getSelectedRow()>-1){
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                    i=tbBilling.getSelectedColumn();
                    if(i==6){  
                        if(akses.getbilling_ralan()==true){
                            try {
                                switch (tbBilling.getValueAt(tbBilling.getSelectedRow(),8).toString()) {
                                    case "Laborat":
                                        JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan/potongan laborat gunakan pada Tambahan/Potongan Biaya");
                                        isRawat();
                                        break;
                                    case "Obat":
                                        JOptionPane.showMessageDialog(rootPane,"Maaf, untuk tambahan potongan obat hanya bisa diisi embalase.\nGunakan Tambahan Biaya jika ingin tambahan lain");
                                        isRawat();
                                        break;
                                    default:
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
                                        break;
                                }
                            } catch (Exception e) {
                                isRawat();
                            }                            
                        }                        
                    }                    
                }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                    i=tbBilling.getSelectedColumn();
                    if(i==1){
                        try {
                            akses.setform("DlgBilingRalan");
                            switch (tbBilling.getValueAt(tbBilling.getSelectedRow(), i).toString()) {
                                case "Tindakan":
                                    if(akses.gettindakan_ralan()==true){
                                        MnInputTindakanActionPerformed(null);
                                    }
                                    break;
                                case "Obat & BHP":
                                    if(akses.getberi_obat()==true){
                                        MnInputObatActionPerformed(null);
                                    }                            
                                    //dispose();
                                    break;
                                case "Tambahan Biaya":
                                    if(akses.gettambahan_biaya()==true){
                                        MnTambahanActionPerformed(null);
                                    }                            
                                    break;
                                case "Potongan Biaya":
                                    if(akses.getpotongan_biaya()==true){
                                        MnPotonganActionPerformed(null);
                                    }
                                    break;
                            }
                        } catch (Exception e) {
                            akses.setform("DlgBilingRalan");
                            switch (tbBilling.getValueAt(tbBilling.getSelectedRow(), i).toString()) {
                                case "Tindakan":
                                    if(akses.gettindakan_ralan()==true){
                                        MnInputTindakanActionPerformed(null);
                                    }
                                    break;
                                case "Obat & BHP":
                                    if(akses.getberi_obat()==true){
                                        MnInputObatActionPerformed(null);
                                    }                            
                                    //dispose();
                                    break;
                                case "Tambahan Biaya":
                                    if(akses.gettambahan_biaya()==true){
                                        MnTambahanActionPerformed(null);
                                    }                            
                                    break;
                                case "Potongan Biaya":
                                    if(akses.getpotongan_biaya()==true){
                                        MnPotonganActionPerformed(null);
                                    }
                                    break;
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
            akses.setform("DlgBilingRalan");
            DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
            dlgrwjl2.isCek();
            dlgrwjl2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgrwjl2.setLocationRelativeTo(internalFrame1);
            dlgrwjl2.SetPoli("-");
            dlgrwjl2.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText()));
            dlgrwjl2.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate());    
            dlgrwjl2.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date(),"ralan"); 
            dlgrwinap.tampilPO();
            dlgrwinap.setAlwaysOnTop(false);
            dlgrwinap.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TNoRw,BtnNota);
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
             Sequel.AutoComitFalse();
             sukses=true;
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));            
            
             Sequel.queryu2("delete from tampjurnal");
             if((-1*ttlPotongan)>0){
                 Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','Potongan_Ralan','0','"+(-1*ttlPotongan)+"'","kredit=kredit+'"+(-1*ttlPotongan)+"'","kd_rek='"+Potongan_Ralan+"'"); 
             }

             if((ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)>0){
                 Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Tindakan Ralan','"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)+"','0'","debet=debet+'"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)+"'","kd_rek='"+Tindakan_Ralan+"'");    
             }

             if(ttlLaborat>0){
                 Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Laborat Ralan','"+ttlLaborat+"','0'","debet=debet+'"+(ttlLaborat)+"'","kd_rek='"+Laborat_Ralan+"'");  
             }

             if(ttlRadiologi>0){
                 Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','Radiologi Ralan','"+ttlRadiologi+"','0'","debet=debet+'"+(ttlRadiologi)+"'","kd_rek='"+Radiologi_Ralan+"'");    
             }

             if(ttlObat>0){
                 Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','Obat Ralan','"+ttlObat+"','0'","debet=debet+'"+(ttlObat)+"'","kd_rek='"+Obat_Ralan+"'");   
             }

             if(ttlRegistrasi>0){
                 Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','Registrasi Ralan','"+ttlRegistrasi+"','0'","debet=debet+'"+(ttlRegistrasi)+"'","kd_rek='"+Registrasi_Ralan+"'");  
             }

             if(ttlTambahan>0){
                 Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','Tambahan Ralan','"+ttlTambahan+"','0'","debet=debet+'"+(ttlTambahan)+"'","kd_rek='"+Tambahan_Ralan+"'");   
             }

             if(ttlOperasi>0){
                 Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','Operasi Ralan','"+ttlOperasi+"','0'","debet=debet+'"+(ttlOperasi)+"'","kd_rek='"+Operasi_Ralan+"'");    
             }

             psakunbayar=koneksi.prepareStatement(
                     "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_jalan.besar_bayar,"+
                     "akun_bayar.ppn,detail_nota_jalan.besarppn from akun_bayar inner join detail_nota_jalan "+
                     "on akun_bayar.nama_bayar=detail_nota_jalan.nama_bayar where detail_nota_jalan.no_rawat=? order by nama_bayar");
             try{
                 psakunbayar.setString(1,TNoRw.getText());
                 rsakunbayar=psakunbayar.executeQuery();
                 while(rsakunbayar.next()){
                    Sequel.menyimpan("tampjurnal","'"+rsakunbayar.getString("kd_rek")+"','"+rsakunbayar.getString("nama_bayar")+"','0','"+rsakunbayar.getString("besar_bayar")+"'","Rekening");
                 } 
             }catch (Exception e) {
                 sukses=false;
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
                     Sequel.menyimpan("tampjurnal","'"+rsakunpiutang.getString("kd_rek")+"','"+rsakunpiutang.getString("nama_bayar")+"','0','"+rsakunpiutang.getString("totalpiutang")+"'","Rekening");
                 } 
             }catch (Exception e) {
                 sukses=false;
                 System.out.println("Notifikasi Akun Piutang Tersimpan : "+e);
             } finally{
                 if(rsakunpiutang != null){
                     rsakunpiutang.close();
                 } 
                 if(psakunpiutang != null){
                     psakunpiutang.close();
                 } 
             }
             
             if(sukses==true){
                 if(piutang>0){
                    sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN PIUTANG PASIEN RAWAT JALAN, DIBATALKAN OLEH "+akses.getkode());    
                 }else if(piutang<=0){
                    sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN PEMBAYARAN PASIEN RAWAT JALAN, DIBATALKAN OLEH "+akses.getkode());    
                 } 
             }
             
             if(sukses==true){
                 Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"status_bayar='Belum Bayar'");
                 Sequel.queryu2("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                 Sequel.queryu2("delete from detail_piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                 Sequel.queryu2("delete from nota_jalan where no_rawat='"+TNoRw.getText()+"'");            
                 Sequel.queryu2("delete from detail_nota_jalan where no_rawat='"+TNoRw.getText()+"'");
                 Valid.hapusTable(tabModeRwJlDr,TNoRw,"billing","no_rawat");
                 Valid.hapusTable(tabModeRwJlDr,TNoRw,"tagihan_sadewa","no_nota");
                 Sequel.Commit();
                 JOptionPane.showMessageDialog(rootPane,"Proses hapus data Nota Salah selesai..!!");
                 Valid.tabelKosong(tabModeAkunBayar);
                 Valid.tabelKosong(tabModeAkunPiutang);
                 isRawat();
             }else{
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }

            Sequel.AutoComitTrue();
            this.setCursor(Cursor.getDefaultCursor());
        }else if(i<=0){
            JOptionPane.showMessageDialog(rootPane,"Data belum pernah disimpan/diverifikasi.\n"+
                    "Tidak perlu ada penghapusan data salah..!!");
        }
    } catch (Exception e) {
        System.out.println("Notifikasi : "+e);
    }            
}//GEN-LAST:event_MnHapusTagihanActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModeRwJlDr.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            DlgPenjualan penjualan=new DlgPenjualan(null,false); 
            penjualan.isCek();
            penjualan.setPasien(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));

            penjualan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            penjualan.setLocationRelativeTo(internalFrame1);
            penjualan.setAlwaysOnTop(false);
            penjualan.setVisible(true);
        }
}//GEN-LAST:event_MnPenjualanActionPerformed

private void MnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokterActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
    }else{ 
        WindowGantiDokterPoli.setSize(630,80);
        WindowGantiDokterPoli.setLocationRelativeTo(internalFrame1);
        WindowGantiDokterPoli.setAlwaysOnTop(false);
        WindowGantiDokterPoli.setVisible(true);
    }
}//GEN-LAST:event_MnDokterActionPerformed

private void BtnCloseIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn1ActionPerformed
    WindowGantiDokterPoli.dispose();
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
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModeRwJlDr,"rawat_jl_dr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModeRwJlDr,"rawat_jl_drpr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            isRawat();
            WindowGantiDokterPoli.dispose();
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
        }else{
            Valid.pindah(evt,BtnCloseIn1,BtnSimpan1);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        
}//GEN-LAST:event_TDokterKeyPressed

private void btnCariDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDokterActionPerformed
    akses.setform("DlgBilingRalan");
    dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
}//GEN-LAST:event_btnCariDokterActionPerformed

private void MnObatLangsungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnObatLangsungActionPerformed
    TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));  
    WindowObatLangsung.setSize(590,80);
    WindowObatLangsung.setLocationRelativeTo(internalFrame1);
    TotalObat.requestFocus();
    WindowObatLangsung.setAlwaysOnTop(false);
    WindowObatLangsung.setVisible(true);
}//GEN-LAST:event_MnObatLangsungActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnNota);
}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowObatLangsung.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowObatLangsung.dispose();
        }else{Valid.pindah(evt, BtnNota, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowObatLangsung.setVisible(false);
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
            WindowObatLangsung.setVisible(false);
            isRawat();isKembali();
        }
}//GEN-LAST:event_BtnBatal1ActionPerformed

private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatal1ActionPerformed(null);
        }else{Valid.pindah(evt, BtnNota, BtnCloseIn);}
}//GEN-LAST:event_BtnBatal1KeyPressed

private void MnTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTambahanActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
    }else{ 
        norawat.setText(TNoRw.getText());
        tampilTambahan(norawat.getText());
        WindowTambahanBiaya.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        WindowTambahanBiaya.setLocationRelativeTo(internalFrame1);
        WindowTambahanBiaya.setAlwaysOnTop(false);
        WindowTambahanBiaya.setVisible(true);             
    }   
}//GEN-LAST:event_MnTambahanActionPerformed

private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
    Valid.pindah(evt, BtnKeluar, BtnNota);
}//GEN-LAST:event_norawatKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
    tabModeTambahan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan3ActionPerformed
    if(norawat.getText().trim().equals("")||(tbTambahan.getRowCount()<=0)){
        Valid.textKosong(norawat,"Data");
    }else{
        for(i=0;i<tbTambahan.getRowCount();i++){
            if(Valid.SetAngka(tbTambahan.getValueAt(i,1).toString())>0){
                Sequel.menyimpan("tambahan_biaya","'"+norawat.getText()+"','"+tbTambahan.getValueAt(i,0).toString()+
                        "','"+tbTambahan.getValueAt(i,1).toString()+"'","Tambahan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowTambahanBiaya.dispose();
    }
}//GEN-LAST:event_BtnSimpan3ActionPerformed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    Sequel.queryu("delete from tambahan_biaya where no_rawat='"+norawat.getText()+
            "' and nama_biaya='"+tbTambahan.getValueAt(tbTambahan.getSelectedRow(),0).toString() +"'");
    tabModeTambahan.removeRow(tbTambahan.getSelectedRow());
    isRawat();
    isKembali();
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
    WindowTambahanBiaya.dispose();
}//GEN-LAST:event_BtnKeluar1ActionPerformed

private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
    WindowGantiPoli.dispose();
}//GEN-LAST:event_BtnCloseIn4ActionPerformed

private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kdpoli.getText().trim().equals("")||nmpoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Poli");
        }else{
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw," kd_poli='"+kdpoli.getText()+"'");
            isRawat();
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
        akses.setform("DlgBilingRalan");
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
}//GEN-LAST:event_btnCariPoliActionPerformed

private void MnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliActionPerformed
    WindowGantiPoli.setSize(630,80);
    WindowGantiPoli.setLocationRelativeTo(internalFrame1);
    WindowGantiPoli.setAlwaysOnTop(false);
    WindowGantiPoli.setVisible(true);
}//GEN-LAST:event_MnPoliActionPerformed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        isRawat();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TNoRw,DTPTgl);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void norawatpotonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatpotonganKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_norawatpotonganKeyPressed

private void BtnTambahPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPotonganActionPerformed
  tabModePotongan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahPotonganActionPerformed

private void BtnSimpanPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanPotonganActionPerformed
   if(norawatpotongan.getText().trim().equals("")||(tbPotongan.getRowCount()<=0)){
        Valid.textKosong(norawat,"Data");
    }else{
        for(r=0;r<tbPotongan.getRowCount();r++){
            if(Valid.SetAngka(tbPotongan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("pengurangan_biaya","'"+norawatpotongan.getText()+"','"+tbPotongan.getValueAt(r,0).toString()+
                        "','"+tbPotongan.getValueAt(r,1).toString()+"'","Potongan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowPotonganBiaya.dispose();
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
  WindowPotonganBiaya.dispose();
}//GEN-LAST:event_BtnKeluarPotonganActionPerformed

private void MnPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPotonganActionPerformed
    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
    }else{ 
        norawatpotongan.setText(TNoRw.getText());
        tampilPotongan(norawatpotongan.getText());
        WindowPotonganBiaya.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        WindowPotonganBiaya.setLocationRelativeTo(internalFrame1);
        WindowPotonganBiaya.setAlwaysOnTop(false);
        WindowPotonganBiaya.setVisible(true);                
    }    
}//GEN-LAST:event_MnPotonganActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{     
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
                periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(TNoRw.getText(),"Ralan");  
                periksalab.isCek();
                periksalab.setAlwaysOnTop(false);
                periksalab.setVisible(true);
            }
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        try {
            pscekbilling=koneksi.prepareStatement(sqlpscekbilling);
            try {
                pscekbilling.setString(1,TNoRw.getText());
                rscekbilling=pscekbilling.executeQuery();
                if(rscekbilling.next()){
                    cek=rscekbilling.getInt(1);
                }
            } catch (Exception e) {
                cek=0;
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscekbilling != null){
                    rscekbilling.close();
                }
                if(pscekbilling != null){
                    pscekbilling.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }    
        
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if((chkObat.isSelected()==false)||(chkPotongan.isSelected()==false)||
                (chkTambahan.isSelected()==false)||(chkTarifDokter.isSelected()==false)||(chkTarifPrm.isSelected()==false)){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan tampilkan semua pilihan tagihan...!!!");
        }else if(cek>0){
            JOptionPane.showMessageDialog(null,"Maaf, data tagihan pasien dengan No.Rawat tersebut sudah pernah disimpan...!!!");
        }else if(cek==0){
            if(piutang<=0){
                if(kekurangan<0){
                    JOptionPane.showMessageDialog(null,"Maaf, pembayaran pasien masih kurang ...!!!");
                }else if(kekurangan>0){
                    if(countbayar>1){
                        JOptionPane.showMessageDialog(null,"Maaf, kembali harus bernilai 0 untuk cara bayar lebih dari 1...!!!");
                    }else{
                        isSimpan();
                    }                        
                }else if(kekurangan==0){
                    isSimpan();
                }                
            }else if(piutang>=1){
                if(kekurangan<0){
                    JOptionPane.showMessageDialog(null,"Maaf, piutang belum genap. Silahkan isi di jumlah piutang ...!!!");
                }else if(kekurangan>0){
                    JOptionPane.showMessageDialog(null,"Maaf, terjadi kelebihan piutang ...!!!");
                }else{
                    isSimpan();
                }                    
            }
        }
        
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void chkPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPotonganActionPerformed
        isRawat();
    }//GEN-LAST:event_chkPotonganActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        isRawat();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkTarifDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarifDokterActionPerformed
        isRawat();
    }//GEN-LAST:event_chkTarifDokterActionPerformed

    private void chkTarifPrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTarifPrmActionPerformed
        isRawat();
    }//GEN-LAST:event_chkTarifPrmActionPerformed

    private void chkTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTambahanActionPerformed
        isRawat();
    }//GEN-LAST:event_chkTambahanActionPerformed

    private void chkObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkObatActionPerformed
        isRawat();
    }//GEN-LAST:event_chkObatActionPerformed

    private void MnInputObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                DlgCariObat dlgobt=new DlgCariObat(null,false);
                dlgobt.emptTeksobat();
                dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                        Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                dlgobt.isCek();
                dlgobt.tampilobat();
                dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgobt.setLocationRelativeTo(internalFrame1);
                dlgobt.setAlwaysOnTop(false);
                dlgobt.setVisible(true);  
            }
        }
    }//GEN-LAST:event_MnInputObatActionPerformed

    private void MnInputTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputTindakanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
            }else{ 
                akses.setform("DlgBilingRalan");
                kdptg=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                nmptg=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kdptg);
                DlgCariPerawatanRalan dlgrwjl=new DlgCariPerawatanRalan(null,false);
                dlgrwjl.setNoRm(TNoRw.getText(),kdptg,nmptg,"rawat_jl_dr","-","-");
                dlgrwjl.isCek();
                dlgrwjl.tampil();
                dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwjl.setLocationRelativeTo(internalFrame1);
                dlgrwjl.setAlwaysOnTop(false);
                dlgrwjl.setVisible(true);                            
            }            
        }                            
    }//GEN-LAST:event_MnInputTindakanActionPerformed

    private void MnCariPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariPeriksaLabActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{           
            DlgCariPeriksaLab periksalab=new DlgCariPeriksaLab(null,false);
            periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            periksalab.setLocationRelativeTo(internalFrame1);
            periksalab.SetNoRw(TNoRw.getText());  
            periksalab.setAlwaysOnTop(false);
            periksalab.setVisible(true);
        }
    }//GEN-LAST:event_MnCariPeriksaLabActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        isRawat();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{               
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
            }else{ 
                DlgPeriksaRadiologi periksalab=new DlgPeriksaRadiologi(null,false);
                periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                periksalab.setLocationRelativeTo(internalFrame1);
                periksalab.emptTeks();
                periksalab.setNoRm(TNoRw.getText(),"Ralan");  
                periksalab.tampil();
                periksalab.isCek();
                periksalab.setAlwaysOnTop(false);
                periksalab.setVisible(true);                            
            }
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void MnCariRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCariRadiologiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{           
            DlgCariPeriksaRadiologi periksalab=new DlgCariPeriksaRadiologi(null,false);
            periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            periksalab.setLocationRelativeTo(internalFrame1);
            periksalab.SetNoRw(TNoRw.getText());  
            periksalab.setAlwaysOnTop(false);
            periksalab.setVisible(true);
        }
    }//GEN-LAST:event_MnCariRadiologiActionPerformed

    private void chkAdministrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAdministrasiActionPerformed
        isRawat();
    }//GEN-LAST:event_chkAdministrasiActionPerformed

    private void chkSarprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSarprasActionPerformed
        isRawat();
    }//GEN-LAST:event_chkSarprasActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{ 
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(),TNoRM.getText()+", "+TPasien.getText(),"Ralan");
            dlgro.setVisible(true);
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        WindowGantiPenjab.setSize(630,80);
        WindowGantiPenjab.setLocationRelativeTo(internalFrame1);
        WindowGantiPenjab.setAlwaysOnTop(false);
        WindowGantiPenjab.setVisible(true);
    }//GEN-LAST:event_MnPenjabActionPerformed

    private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
        akses.setform("DlgBilingRalan");
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_btnPenjabActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if(kdpenjab.getText().trim().equals("")||nmpenjab.getText().trim().equals("")){
            Valid.textKosong(kdpenjab,"Jenis Bayar");
        }else{
            Sequel.mengedit("reg_periksa","no_rawat=?"," kd_pj=?",2,new String[]{kdpenjab.getText(),TNoRw.getText()});
            isRawat();
            WindowGantiPenjab.dispose();
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowGantiPenjab.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void tbAkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunBayarKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbAkunBayar.getRowCount()!=0){
                    if((tbAkunBayar.getSelectedColumn()==2)||(tbAkunBayar.getSelectedColumn()==3)||(tbAkunBayar.getSelectedColumn()==4)){
                        if(!tabModeAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString().equals("")){
                            tbAkunBayar.setValueAt(
                                    Valid.roundUp((Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),3).toString())/100)*
                                    Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString()),100),tbAkunBayar.getSelectedRow(),4);
                        }else{
                            tbAkunBayar.setValueAt("",tbAkunBayar.getSelectedRow(),4);                        
                        }                            
                    }
                }
                isKembali();
            }
        }
    }//GEN-LAST:event_tbAkunBayarKeyPressed

    private void tbAkunPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunPiutangKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                isKembali();
            }
        }
    }//GEN-LAST:event_tbAkunPiutangKeyPressed

    private void tbAkunBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunBayarPropertyChange
        if(this.isVisible()==true){
              isKembali();
        }
    }//GEN-LAST:event_tbAkunBayarPropertyChange

    private void tbAkunPiutangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunPiutangPropertyChange
        if(this.isVisible()==true){
              isKembali();
        }
    }//GEN-LAST:event_tbAkunPiutangPropertyChange

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
        isHitung();
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
        if(status.equals("belum")){
            tampilAkunPiutang();
        }else if(status.equals("sudah")){
            tampilAkunPiutangTersimpan();
        }
        isHitung();
        isKembali();
    }//GEN-LAST:event_btnCariPiutangActionPerformed

    private void btnCariPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPiutangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariPiutangKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        Valid.tabelKosong(tabModeAkunBayar);
        if(status.equals("belum")){
            tampilAkunBayar();
        }else if(status.equals("sudah")){
            tampilAkunBayarTersimpan();
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppBersihkan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkan1ActionPerformed
        Valid.tabelKosong(tabModeAkunPiutang);
        if(status.equals("belum")){
            tampilAkunPiutang();
        }else if(status.equals("sudah")){
            tampilAkunPiutangTersimpan();
        }
    }//GEN-LAST:event_ppBersihkan1ActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==2){
            try {
                Valid.tabelKosong(tabModeLab);
                pscarilab=koneksi.prepareStatement("select permintaan_lab.noorder,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                    "dokter.nm_dokter from permintaan_lab inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                    "where permintaan_lab.status='ralan' and permintaan_lab.no_rawat=? order by permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan desc");
                try {
                    pscarilab.setString(1,TNoRw.getText());
                    rscarilab=pscarilab.executeQuery();
                    while(rscarilab.next()){
                        tabModeLab.addRow(new String[]{
                            rscarilab.getString("noorder"),rscarilab.getString("tgl_permintaan"),rscarilab.getString("jam_permintaan"),rscarilab.getString("nm_dokter"),rscarilab.getString("status")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rscarilab!=null){
                        rscarilab.close();
                    }
                    if(pscarilab!=null){
                        pscarilab.close();
                    }
                }
                
                Valid.tabelKosong(tabModeRad);
                pscariradiologi=koneksi.prepareStatement("select permintaan_radiologi.noorder,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,"+
                    "if(permintaan_radiologi.tgl_hasil='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,"+
                    "dokter.nm_dokter from permintaan_radiologi inner join dokter on permintaan_radiologi.dokter_perujuk=dokter.kd_dokter "+
                    "where permintaan_radiologi.status='ralan' and permintaan_radiologi.no_rawat=? order by permintaan_radiologi.tgl_permintaan,permintaan_radiologi.jam_permintaan desc");
                try {
                    pscariradiologi.setString(1,TNoRw.getText());
                    rscariradiologi=pscariradiologi.executeQuery();
                    while(rscariradiologi.next()){
                        tabModeRad.addRow(new String[]{
                            rscariradiologi.getString("noorder"),rscariradiologi.getString("tgl_permintaan"),rscariradiologi.getString("jam_permintaan"),rscariradiologi.getString("nm_dokter"),rscariradiologi.getString("status")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rscariradiologi!=null){
                        rscariradiologi.close();
                    }
                    if(pscariradiologi!=null){
                        pscariradiologi.close();
                    }
                }
                
                Valid.tabelKosong(tabModeApotek);
                psobatlangsung=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                    " dokter.nm_dokter,if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status "+
                    " from resep_obat inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                    " where resep_obat.status='ralan' and resep_obat.no_rawat=? order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
                try {
                    psobatlangsung.setString(1,TNoRw.getText());
                    rscariobat=psobatlangsung.executeQuery();
                    while(rscariobat.next()){
                        tabModeApotek.addRow(new String[]{
                            rscariobat.getString("no_resep"),rscariobat.getString("tgl_peresepan"),rscariobat.getString("jam_peresepan"),rscariobat.getString("nm_dokter"),rscariobat.getString("status")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rscariobat!=null){
                        rscariobat.close();
                    }
                    if(psobatlangsung!=null){
                        psobatlangsung.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }//GEN-LAST:event_TabRawatMouseClicked

 

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBilingRalan dialog = new DlgBilingRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn1;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusPotongan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluarPotongan;
    private widget.Button BtnNota;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan2;
    private widget.Button BtnSimpan3;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.Button BtnSimpanPotongan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambahPotongan;
    private widget.Button BtnView;
    private widget.Tanggal DTPTgl;
    private javax.swing.JMenuItem MnCariPeriksaLab;
    private javax.swing.JMenuItem MnCariRadiologi;
    private javax.swing.JMenuItem MnDokter;
    private javax.swing.JMenuItem MnHapusTagihan;
    private javax.swing.JMenuItem MnInputObat;
    private javax.swing.JMenuItem MnInputTindakan;
    private javax.swing.JMenuItem MnObatLangsung;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPoli;
    private javax.swing.JMenuItem MnPotongan;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnTambahan;
    private javax.swing.JPopupMenu PopupBayar;
    private javax.swing.JPopupMenu PopupPiutang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private widget.TextBox TDokter;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TagihanPPn;
    private widget.TextBox TotalObat;
    private widget.TextBox TtlSemua;
    private javax.swing.JDialog WindowGantiDokterPoli;
    private javax.swing.JDialog WindowGantiPenjab;
    private javax.swing.JDialog WindowGantiPoli;
    private javax.swing.JDialog WindowObatLangsung;
    private javax.swing.JDialog WindowPotonganBiaya;
    private javax.swing.JDialog WindowTambahanBiaya;
    private widget.Button btnCariDokter;
    private widget.Button btnCariPiutang;
    private widget.Button btnCariPoli;
    private widget.Button btnPenjab;
    private widget.CekBox chkAdministrasi;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkObat;
    private widget.CekBox chkPotongan;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkSarpras;
    private widget.CekBox chkTambahan;
    private widget.CekBox chkTarifDokter;
    private widget.CekBox chkTarifPrm;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.Label label15;
    private widget.Label label16;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.TextBox norawat;
    private widget.TextBox norawatpotongan;
    private widget.panelisi panelBayar;
    private widget.panelisi panelGlass1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelPermintaan;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppBersihkan1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbAkunBayar;
    private widget.Table tbAkunPiutang;
    private widget.Table tbApotek;
    private widget.Table tbBilling;
    private widget.Table tbLab;
    private widget.Table tbPotongan;
    private widget.Table tbRadiologi;
    private widget.Table tbTambahan;
    // End of variables declaration//GEN-END:variables

    public void isRawat() {
        try {    
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
                            
            pscarirm=koneksi.prepareStatement(sqlpscarirm);
            try{
                pscarirm.setString(1,TNoRw.getText());
                rscarirm=pscarirm.executeQuery();
                if(rscarirm.next()){
                    TNoRM.setText(rscarirm.getString(1));
                }
	    }catch (Exception e) {
                TNoRM.setText("");
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscarirm != null){
                    rscarirm.close();
                }
                if(pscarirm != null){
                    pscarirm.close();
                }
            }
            
            pscaripasien=koneksi.prepareStatement(sqlpscaripasien);
            try{
                pscaripasien.setString(1,TNoRM.getText());
                rscaripasien=pscaripasien.executeQuery();
                if(rscaripasien.next()){
                    TPasien.setText(rscaripasien.getString(1));
                }
	    }catch (Exception e) {
                TPasien.setText("");
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscaripasien != null){
                    rscaripasien.close();
                }
                if(pscaripasien != null){
                    pscaripasien.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        if(i<=0){
             prosesCariReg();    
             if((chkLaborat.isSelected()==true)||(chkTarifDokter.isSelected()==true)||(chkTarifPrm.isSelected()==true)||(chkRadiologi.isSelected()==true)){
                 tabModeRwJlDr.addRow(new Object[]{true,"Tindakan",":","",null,null,null,null,"Ralan Dokter"});
             }             
             if(chkTarifDokter.isSelected()==true){prosesCariRwJlDr();prosesCariRwJlDrPr();}
             if(chkTarifPrm.isSelected()==true){prosesCariRwJlPr();}
             if(chkLaborat.isSelected()==true){prosesCariPeriksaLab();}
             if(chkRadiologi.isSelected()==true){prosesCariRadiologi();}    
             prosesCariOperasi();
             if(chkSarpras.isSelected()==true){
                if(detailjs>0){
                   tabModeRwJlDr.addRow(new Object[]{true,"","Jasa Sarana dan Prasarana",":",null,null,null,detailjs,"Ralan Dokter"});
                } 
             }                
             if(chkObat.isSelected()==true){
                tabModeRwJlDr.addRow(new Object[]{true,"Obat & BHP",":","",null,null,null,null,"Obat"});
                prosesCariObat(); 
                if(detailbhp>0){
                    tabModeRwJlDr.addRow(new Object[]{true,"","Paket Obat/BHP",":",null,null,null,detailbhp,"Ralan Dokter"});
                }
             }
             if(chkTambahan.isSelected()==true){                           
                try {
                    pstambahan=koneksi.prepareStatement(sqlpstambahan);
                    try {
                        pstambahan.setString(1,TNoRw.getText());
                        rstambahan=pstambahan.executeQuery();
                        rstambahan.last();
                        if(rstambahan.getRow()>0){
                            tabModeRwJlDr.addRow(new Object[]{true,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});
                        }else{
                            tabModeRwJlDr.addRow(new Object[]{false,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});
                        }
                        rstambahan.beforeFirst();
                        while(rstambahan.next()){                    
                            tabModeRwJlDr.addRow(new Object[]{true,"",rstambahan.getString("nama_biaya"),":",
                                       rstambahan.getDouble("besar_biaya"),1,null,rstambahan.getDouble("besar_biaya"),"Tambahan"});
                        } 
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstambahan != null){
                            rstambahan.close();
                        }
                        if(pstambahan != null){
                            pstambahan.close();
                        }
                    }
                } catch (SQLException ex) {
                   System.out.println("Notifikasi : "+ex);
                }
             }
             if(chkPotongan.isSelected()==true){                          
                try {
                    pspotongan=koneksi.prepareStatement(sqlpspotongan);
                    try{
                        pspotongan.setString(1,TNoRw.getText());
                        rspotongan=pspotongan.executeQuery();
                        rspotongan.last();
                        if(rspotongan.getRow()>0){
                            tabModeRwJlDr.addRow(new Object[]{true,"Potongan Biaya",":","",null,null,null,null,"Potongan"});  
                        }else{
                            tabModeRwJlDr.addRow(new Object[]{false,"Potongan Biaya",":","",null,null,null,null,"Potongan"});  
                        }
                        rspotongan.beforeFirst();
                        while(rspotongan.next()){                    
                            tabModeRwJlDr.addRow(new Object[]{true,"",rspotongan.getString("nama_pengurangan"),":",
                                       rspotongan.getDouble("besar_pengurangan"),1,null,(-1*rspotongan.getDouble("besar_pengurangan")),"Potongan"});
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rspotongan != null){
                            rspotongan.close();
                        } 
                        if(pspotongan != null){
                            pspotongan.close();
                        } 
                    }
                } catch (SQLException ex) {
                   System.out.println("Notifikasi : "+ex);
                }
             }  
             TCari.setText("");
             TCari1.setText("");
             tampilAkunBayar();
             tampilAkunPiutang();
             isHitung(); 
             status="belum";
         }else if(i>0){
             Valid.SetTgl2(DTPTgl,Sequel.cariIsi("select concat(tanggal,' ',jam) from nota_jalan where no_rawat='"+TNoRw.getText()+"'"));
             Valid.tabelKosong(tabModeRwJlDr);
             try{                
                psbilling=koneksi.prepareStatement(sqlpsbilling);    
                try {
                    psbilling.setString(1,TNoRw.getText());
                    rsbilling=psbilling.executeQuery();
                    while(rsbilling.next()){
                        if(!rsbilling.getString("status").equals("Tagihan")){
                            tabModeRwJlDr.addRow(new Object[]{true,rsbilling.getString("no"),
                                        rsbilling.getString("nm_perawatan"),
                                        rsbilling.getString("pemisah"),
                                        rsbilling.getObject("satu"),
                                        rsbilling.getObject("dua"),
                                        rsbilling.getObject("tiga"),
                                        rsbilling.getObject("empat"),
                                        rsbilling.getString("status")});  
                        }
                    } 
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsbilling != null){
                        rsbilling.close();
                    } 
                    if(psbilling != null){
                        psbilling.close();
                    } 
                }
                
                TCari.setText("");
                TCari1.setText("");                
                tampilAkunBayarTersimpan();
                tampilAkunPiutangTersimpan();
                isHitung(); 
                status="sudah";
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }             
         }
         isKembali();
    }
    
    public void isRawat2() {
        prosesCariReg();    
        if((chkLaborat.isSelected()==true)||(chkTarifDokter.isSelected()==true)||(chkTarifPrm.isSelected()==true)||(chkRadiologi.isSelected()==true)){
            tabModeRwJlDr.addRow(new Object[]{true,"Tindakan",":","",null,null,null,null,"Ralan Dokter"});
        }
        if(chkTarifDokter.isSelected()==true){prosesCariRwJlDr();prosesCariRwJlDrPr();}
        if(chkTarifPrm.isSelected()==true){prosesCariRwJlPr();}
        prosesCariOperasi();
        if(chkLaborat.isSelected()==true){prosesCariPeriksaLab();}
        if(chkRadiologi.isSelected()==true){prosesCariRadiologi();}             
        if(chkSarpras.isSelected()==true){
            if(detailjs>0){
               tabModeRwJlDr.addRow(new Object[]{true,"","Jasa Sarana dan Prasarana",":",null,null,null,detailjs,"Ralan Dokter"});
            } 
        }                
        if(chkObat.isSelected()==true){
            tabModeRwJlDr.addRow(new Object[]{true,"Obat & BHP",":","",null,null,null,null,"Obat"});
            prosesCariObat(); 
            if(detailbhp>0){
                tabModeRwJlDr.addRow(new Object[]{true,"","Paket Obat/BHP",":",null,null,null,detailbhp,"Ralan Dokter"});
            }
        }
        if(chkTambahan.isSelected()==true){                           
            try {
                pstambahan=koneksi.prepareStatement(sqlpstambahan);
                try {
                    pstambahan.setString(1,TNoRw.getText());
                    rstambahan=pstambahan.executeQuery();
                    rstambahan.last();
                    if(rstambahan.getRow()>0){
                        tabModeRwJlDr.addRow(new Object[]{true,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{false,"Tambahan Biaya",":","",null,null,null,null,"Tambahan"});
                    }
                    rstambahan.beforeFirst();
                    while(rstambahan.next()){                    
                        tabModeRwJlDr.addRow(new Object[]{true,"",rstambahan.getString("nama_biaya"),":",
                                   rstambahan.getDouble("besar_biaya"),1,null,rstambahan.getDouble("besar_biaya"),"Tambahan"});
                    } 
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rstambahan != null){
                        rstambahan.close();
                    }
                    if(pstambahan != null){
                        pstambahan.close();
                    }
                }
            } catch (SQLException ex) {
               System.out.println("Notifikasi : "+ex);
            }
        }
        if(chkPotongan.isSelected()==true){                          
            try {
                pspotongan=koneksi.prepareStatement(sqlpspotongan);
                try{
                    pspotongan.setString(1,TNoRw.getText());
                    rspotongan=pspotongan.executeQuery();
                    rspotongan.last();
                    if(rspotongan.getRow()>0){
                        tabModeRwJlDr.addRow(new Object[]{true,"Potongan Biaya",":","",null,null,null,null,"Potongan"});  
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{false,"Potongan Biaya",":","",null,null,null,null,"Potongan"});  
                    }
                    rspotongan.beforeFirst();
                    while(rspotongan.next()){                    
                        tabModeRwJlDr.addRow(new Object[]{true,"",rspotongan.getString("nama_pengurangan"),":",
                                   rspotongan.getDouble("besar_pengurangan"),1,null,(-1*rspotongan.getDouble("besar_pengurangan")),"Potongan"});
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rspotongan != null){
                        rspotongan.close();
                    } 
                    if(pspotongan != null){
                        pspotongan.close();
                    } 
                }
            } catch (SQLException ex) {
               System.out.println("Notifikasi : "+ex);
            }
        }  
         
        isHitung();          
        isKembali();
    }

    private void prosesCariReg() {        
        Valid.tabelKosong(tabModeRwJlDr);
        try{   
            psreg=koneksi.prepareStatement(sqlpsreg);
            try{
                psreg.setString(1,TNoRw.getText());
                rsreg=psreg.executeQuery();            
                if(rsreg.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"No.Nota",": "+Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_jalan where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RJ/",6),"",null,null,null,null,"-"});                
                    pscaripoli=koneksi.prepareStatement(sqlpscaripoli);
                    try{
                        pscaripoli.setString(1,rsreg.getString("kd_poli"));
                        rscaripoli=pscaripoli.executeQuery();
                        if(rscaripoli.next()){
                            polirujukan="";
                            pscaripoli2=koneksi.prepareStatement(sqlpscaripoli2);
                            try {
                                pscaripoli2.setString(1,TNoRw.getText());
                                rscaripoli2=pscaripoli2.executeQuery();
                                while(rscaripoli2.next()){
                                    polirujukan=polirujukan+", "+rscaripoli2.getString("nm_poli");
                                }
                            } catch (Exception e) {
                                System.out.println("Dokter : "+e);
                            } finally{
                                if(rscaripoli2!=null){
                                    rscaripoli2.close();
                                }
                                if(pscaripoli2!=null){
                                    pscaripoli2.close();
                                }
                            }
                            tabModeRwJlDr.addRow(new Object[]{true,"Unit/Instansi",": "+rscaripoli.getString(1)+polirujukan,"",null,null,null,null,"-"});
                        }
                    }catch (Exception e) {
                        tabModeRwJlDr.addRow(new Object[]{true,"Unit/Instansi",": ","",null,null,null,null,"-"});
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rscaripoli != null){
                            rscaripoli.close();
                        }
                        if(pscaripoli != null){
                            pscaripoli.close();
                        }
                    }
                                        
                    tabModeRwJlDr.addRow(new Object[]{true,"Tanggal & Jam",": "+rsreg.getString("tgl_registrasi")+" "+rsreg.getString("jam"),"",null,null,null,null,"-"});
                    tabModeRwJlDr.addRow(new Object[]{true,"No.RM",": "+TNoRM.getText(),"",null,null,null,null,"-"});
                    tabModeRwJlDr.addRow(new Object[]{true,"Nama Pasien",": "+TPasien.getText()+" ("+rsreg.getString("umurdaftar")+rsreg.getString("sttsumur")+")","",null,null,null,null,"-"});
                    pscarialamat=koneksi.prepareStatement(sqlpscarialamat); 
                    try{
                        pscarialamat.setString(1,TNoRM.getText());
                        rscarialamat=pscarialamat.executeQuery();
                        if(rscarialamat.next()){
                            tabModeRwJlDr.addRow(new Object[]{true,"Alamat Pasien",": "+rscarialamat.getString(1),"",null,null,null,null,"-"});
                        }
                    }catch (Exception e) {
                        tabModeRwJlDr.addRow(new Object[]{true,"Alamat Pasien",": ","",null,null,null,null,"-"});
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rscarialamat != null){
                            rscarialamat.close();
                        }
                        if(pscarialamat != null){
                            pscarialamat.close();
                        }
                    }
                    //cari dokter yang menangandi  

                    psdokterralan=koneksi.prepareStatement(sqlpsdokterralan);
                    try{
                        psdokterralan.setString(1,TNoRw.getText());
                        rsdokterralan=psdokterralan.executeQuery();
                        if(centangdokterralan.equals("Yes")){
                            if(rsdokterralan.next()){
                                tabModeRwJlDr.addRow(new Object[]{true,"Dokter ",":","",null,null,null,null,"-"});  
                            }
                            rsdokterralan.beforeFirst();
                            if(rsdokterralan.next()){
                                dokterrujukan="";
                                psdokterralan2=koneksi.prepareStatement(sqlpsdokterralan2);
                                try {
                                    psdokterralan2.setString(1,TNoRw.getText());
                                    rsdokterralan2=psdokterralan2.executeQuery();
                                    while(rsdokterralan2.next()){
                                        dokterrujukan=dokterrujukan+", "+rsdokterralan2.getString("nm_dokter");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Dokter : "+e);
                                } finally{
                                    if(rsdokterralan2!=null){
                                        rsdokterralan2.close();
                                    }
                                    if(psdokterralan2!=null){
                                        psdokterralan2.close();
                                    }
                                }
                                tabModeRwJlDr.addRow(new Object[]{true,"",rsdokterralan.getString("nm_dokter")+dokterrujukan,"",null,null,null,null,"Dokter"});   
                            }
                        }else{
                            if(rsdokterralan.next()){
                                tabModeRwJlDr.addRow(new Object[]{false,"Dokter ",":","",null,null,null,null,"-"});  
                            }
                            rsdokterralan.beforeFirst();
                            if(rsdokterralan.next()){
                                dokterrujukan="";
                                psdokterralan2=koneksi.prepareStatement(sqlpsdokterralan2);
                                try {
                                    psdokterralan2.setString(1,TNoRw.getText());
                                    rsdokterralan2=psdokterralan2.executeQuery();
                                    while(rsdokterralan2.next()){
                                        dokterrujukan=dokterrujukan+", "+rsdokterralan2.getString("nm_dokter");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Dokter : "+e);
                                } finally{
                                    if(rsdokterralan2!=null){
                                        rsdokterralan2.close();
                                    }
                                    if(psdokterralan2!=null){
                                        psdokterralan2.close();
                                    }
                                }
                                tabModeRwJlDr.addRow(new Object[]{false,"",rsdokterralan.getString("nm_dokter")+dokterrujukan,"",null,null,null,null,"Dokter"});   
                            }
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rsdokterralan != null){
                            rsdokterralan.close();
                        }
                        if(psdokterralan != null){
                            psdokterralan.close();
                        }
                    }
                    

                    if(chkAdministrasi.isSelected()==true){
                        tabModeRwJlDr.addRow(new Object[]{true,"Registrasi",":","",null,null,null,rsreg.getDouble("biaya_reg"),"Registrasi"});
                    }
                }       
	    }catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsreg != null){
                    rsreg.close();
                }
                if(psreg != null){
                    psreg.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    private void prosesCariRwJlDr() {
        try{  
            pscariralandokter=koneksi.prepareStatement(sqlpscariralandokter);
            try {
                pscariralandokter.setString(1,TNoRw.getText());
                rscariralandokter=pscariralandokter.executeQuery();
                subttl=0;
                detailbhp=0;
                detailjs=0;
                while(rscariralandokter.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);
                    try {
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rscariralandokter.getString("nm_perawatan"));
                        pstamkur.setString(3,"Ralan Dokter");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur != null){
                            rstamkur.close();
                        } 
                        if(pstamkur != null){
                            pstamkur.close();
                        } 
                    }
                    if(rinciandokterralan.equals("Yes")){
                        detailbhp=detailbhp+rscariralandokter.getDouble("totalbhp");
                        detailjs=detailjs+rscariralandokter.getDouble("totalmaterial");
                        tabModeRwJlDr.addRow(new Object[]{true,"",rscariralandokter.getString("nm_perawatan"),":",
                                       rscariralandokter.getDouble("tarif_tindakandr"),rscariralandokter.getDouble("jml"),tamkur,(rscariralandokter.getDouble("totaltarif_tindakandr")+tamkur),"Ralan Dokter"});
                        subttl=subttl+rscariralandokter.getDouble("totaltarif_tindakandr")+tamkur; 
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{true,"",rscariralandokter.getString("nm_perawatan"),":",
                                       rscariralandokter.getDouble("total_byrdr"),rscariralandokter.getDouble("jml"),tamkur,(rscariralandokter.getDouble("biaya")+tamkur),"Ralan Dokter"});
                        subttl=subttl+rscariralandokter.getDouble("biaya")+tamkur;
                    }                    
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rscariralandokter != null){
                    rscariralandokter.close();
                }
                if(pscariralandokter != null){
                    pscariralandokter.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void prosesCariRwJlDrPr() {
        try{            
            pscariralandrpr=koneksi.prepareStatement(sqlpscariralandrpr);
            try {
                pscariralandrpr.setString(1,TNoRw.getText());
                rscariralandrpr=pscariralandrpr.executeQuery();
                subttl=0;
                while(rscariralandrpr.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);
                    try{
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rscariralandrpr.getString("nm_perawatan"));
                        pstamkur.setString(3,"Ralan Dokter Paramedis");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    }catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur != null){
                            rstamkur.close();
                        } 
                        if(pstamkur != null){
                            pstamkur.close();
                        } 
                    }
                        
                    if(rinciandokterralan.equals("Yes")){
                        detailbhp=detailbhp+rscariralandrpr.getDouble("totalbhp");
                        detailjs=detailjs+rscariralandrpr.getDouble("totalmaterial")+rscariralandrpr.getDouble("totaltarif_tindakanpr");
                        tabModeRwJlDr.addRow(new Object[]{true,"",rscariralandrpr.getString("nm_perawatan"),":",
                                       rscariralandrpr.getDouble("tarif_tindakandr"),rscariralandrpr.getDouble("jml"),tamkur,(rscariralandrpr.getDouble("totaltarif_tindakandr")+tamkur),"Ralan Dokter Paramedis"});
                        subttl=subttl+rscariralandrpr.getDouble("totaltarif_tindakandr")+tamkur; 
                    }else{
                        tabModeRwJlDr.addRow(new Object[]{true,"",rscariralandrpr.getString("nm_perawatan"),":",
                                       rscariralandrpr.getDouble("total_byrdrpr"),rscariralandrpr.getDouble("jml"),tamkur,(rscariralandrpr.getDouble("biaya")+tamkur),"Ralan Dokter Paramedis"});
                        subttl=subttl+rscariralandrpr.getDouble("biaya")+tamkur;
                    }

                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e); 
            } finally{
                if(rscariralandrpr!=null){
                    rscariralandrpr.close();
                }
                if(pscariralandrpr!=null){
                    pscariralandrpr.close();
                }
            }
            //rs.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariRwJlPr() {
        try{        
            pscariralanperawat=koneksi.prepareStatement(sqlpscariralanperawat);
            try {
                pscariralanperawat.setString(1,TNoRw.getText());
                rscariralanperawat=pscariralanperawat.executeQuery();
                subttl=0;
                while(rscariralanperawat.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);
                    try{
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rscariralanperawat.getString("nm_perawatan"));
                        pstamkur.setString(3,"Ralan Paramedis");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur != null){
                            rstamkur.close();
                        } 
                        if(pstamkur != null){
                            pstamkur.close();
                        } 
                    }
                        
                    tabModeRwJlDr.addRow(new Object[]{true,"",rscariralanperawat.getString("nm_perawatan"),":",
                                   rscariralanperawat.getDouble("total_byrpr"),rscariralanperawat.getDouble("jml"),tamkur,(rscariralanperawat.getDouble("biaya")+tamkur),"Ralan Paramedis"});
                    subttl=subttl+rscariralanperawat.getDouble("biaya")+tamkur;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e); 
            } finally{
                if(rscariralanperawat!=null){
                    rscariralanperawat.close();
                }
                if(pscariralanperawat!=null){
                    pscariralanperawat.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void prosesCariPeriksaLab() {
        try{
            pscarilab=koneksi.prepareStatement(sqlpscarilab);
            try {
                pscarilab.setString(1,TNoRw.getText());
                rscarilab=pscarilab.executeQuery();
                subttl=0;
                while(rscarilab.next()){
                    psdetaillab=koneksi.prepareStatement(sqlpsdetaillab);
                    try {
                        psdetaillab.setString(1,TNoRw.getText());
                        psdetaillab.setString(2,rscarilab.getString("kd_jenis_prw"));
                        rsdetaillab=psdetaillab.executeQuery();
                        ralanparamedis=0;
                        while(rsdetaillab.next()){  
                            ralanparamedis=rsdetaillab.getDouble("total");               
                        }
                    } catch (Exception e) {
                        ralanparamedis=0;
                        System.out.println("Notifikasi : "+e); 
                    } finally{
                        if(rsdetaillab!=null){
                            rsdetaillab.close();
                        }
                        if(psdetaillab!=null){
                            psdetaillab.close();
                        }
                    }
                    tabModeRwJlDr.addRow(new Object[]{true,"",rscarilab.getString("nm_perawatan"),":",
                                   rscarilab.getDouble("biaya"),rscarilab.getDouble("jml"),ralanparamedis,(rscarilab.getDouble("total")+ralanparamedis),"Laborat"});
                    subttl=subttl+rscarilab.getDouble("total")+ralanparamedis;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e); 
            } finally{
                if(rscarilab!=null){
                    rscarilab.close();
                }
                if(pscarilab!=null){
                    pscarilab.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void prosesCariRadiologi() {
        try{
            pscariradiologi=koneksi.prepareStatement(sqlpscariradiologi);
            try {
                pscariradiologi.setString(1,TNoRw.getText());
                rscariradiologi=pscariradiologi.executeQuery();
                subttl=0;
                while(rscariradiologi.next()){
                    tamkur=0;
                    pstamkur=koneksi.prepareStatement(sqlpstamkur);
                    try{
                        pstamkur.setString(1,TNoRw.getText());
                        pstamkur.setString(2,rscariradiologi.getString("nm_perawatan"));
                        pstamkur.setString(3,"Radiologi");
                        rstamkur=pstamkur.executeQuery();
                        if(rstamkur.next()){
                            tamkur=rstamkur.getDouble(1);
                        }
                    }catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rstamkur != null){
                            rstamkur.close();
                        } 
                        if(pstamkur != null){
                            pstamkur.close();
                        } 
                    }
                        
                    tabModeRwJlDr.addRow(new Object[]{true,"",rscariradiologi.getString("nm_perawatan"),":",
                                   rscariradiologi.getDouble("biaya"),rscariradiologi.getDouble("jml"),tamkur,(rscariradiologi.getDouble("total")+tamkur),"Radiologi"});
                    subttl=subttl+rscariradiologi.getDouble("total")+tamkur;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e); 
            } finally{
                if(rscariradiologi!=null){
                    rscariradiologi.close();
                }
                if(pscariradiologi!=null){
                    pscariradiologi.close();
                }
            }            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariObat() { 
        subttl=0;
        try{     
            psobatlangsung=koneksi.prepareStatement(sqlpsobatlangsung);
            try {
                psobatlangsung.setString(1,TNoRw.getText());
                rsobatlangsung=psobatlangsung.executeQuery();
                if(rsobatlangsung.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"","Obat & BHP ",":",rsobatlangsung.getDouble("besar_tagihan"),1,0,rsobatlangsung.getDouble("besar_tagihan"),"Obat"});
                    subttl=subttl+rsobatlangsung.getDouble("besar_tagihan");
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        try{      
            pscariobat=koneksi.prepareStatement(sqlpscariobat);
            try {
                pscariobat.setString(1,TNoRw.getText());
                rscariobat=pscariobat.executeQuery();
                //embalase=0;
                if(centangobatralan.equals("Yes")){
                    while(rscariobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,"",rscariobat.getString("nama_brng")+" ("+rscariobat.getString("nama")+")",":",
                                       rscariobat.getDouble("biaya_obat"),rscariobat.getDouble("jml"),rscariobat.getDouble("tambahan"),
                                       (rscariobat.getDouble("total")+rscariobat.getDouble("tambahan")),"Obat"});
                        subttl=subttl+rscariobat.getDouble("total")+rscariobat.getDouble("tambahan");
                    }
                }else{
                    while(rscariobat.next()){
                        tabModeRwJlDr.addRow(new Object[]{false,"",rscariobat.getString("nama_brng")+" ("+rscariobat.getString("nama")+")",":",
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
            psobatoperasi=koneksi.prepareStatement(sqlpsobatoperasi);
            try{
                psobatoperasi.setString(1,TNoRw.getText());
                rsobatoperasi=psobatoperasi.executeQuery();
                if(centangobatralan.equals("Yes")){
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
        
        try{
            psreturobat=koneksi.prepareStatement(sqlpsreturobat);
            try {
                psreturobat.setString(1,TNoRw.getText());

                rsreturobat=psreturobat.executeQuery();     
                if(rsreturobat.next()){                
                    tabModeRwJlDr.addRow(new Object[]{true,"","Retur Obat :","",null,null,null,null,"Retur Obat"});          
                }
                rsreturobat.beforeFirst();
                while(rsreturobat.next()){
                    Object[] data={true,"",rsreturobat.getString("nama_brng"),":",
                                   rsreturobat.getDouble("h_retur"),rsreturobat.getDouble("jml"),null,
                                   rsreturobat.getDouble("ttl"),"Retur Obat"};
                    tabModeRwJlDr.addRow(data);
                    subttl=subttl+rsreturobat.getDouble("ttl");
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
        
        if(subttl>0){ 
            if(tampilkan_ppnobat_ralan.equals("Yes")){
                ppnobat=Valid.roundUp(subttl*0.1,100);
                if(centangobatralan.equals("Yes")){
                    tabModeRwJlDr.addRow(new Object[]{true,"","PPN Obat",":",ppnobat,1,0,ppnobat,"Obat"});
                }else{
                    tabModeRwJlDr.addRow(new Object[]{false,"","PPN Obat",":",ppnobat,1,0,ppnobat,"Obat"});
                }
                tabModeRwJlDr.addRow(new Object[]{true,"",""+Valid.SetAngka3(subttl+ppnobat),"",null,null,null,null,"TtlObat"});            
            }else{
                tabModeRwJlDr.addRow(new Object[]{true,"",""+Valid.SetAngka3(subttl),"",null,null,null,null,"TtlObat"});            
            }                
        }
        
    }


    private void isHitung() {   
        ttl=0;
        y=0;
        ttlLaborat=0;ttlRadiologi=0;ttlObat=0;ttlRalan_Dokter=0;ttlRalan_Paramedis=0;ttlTambahan=0;
        ttlPotongan=0;ttlRegistrasi=0;ttlRalan_Dokter_Param=0;ttlOperasi=0;
        int a=tbBilling.getRowCount();
        for(r=0;r<a;r++){ 
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
                case "Obat":
                        ttlObat=ttlObat+y;
                        break;
                case "Ralan Dokter":
                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                        break;     
                case "Ralan Dokter Paramedis":
                        ttlRalan_Dokter_Param=ttlRalan_Dokter_Param+y;
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
                case "Registrasi":
                        ttlRegistrasi=ttlRegistrasi+y;
                        break;
                case "Operasi":
                        ttlOperasi=ttlOperasi+y;
                        break;
            }                                
            ttl=ttl+y;             
        }
        TtlSemua.setText(Valid.SetAngka3(ttl));
    }    
    
    
      
    public void isCek(){
        Valid.tabelKosong(tabModeAkunBayar);
        Valid.tabelKosong(tabModeAkunPiutang);
        DTPTgl.setDate(new Date());
        BtnNota.setEnabled(akses.getbilling_ralan());
        BtnSimpan.setEnabled(akses.getbilling_ralan());
        BtnView.setEnabled(akses.getbilling_ralan());
        MnRawatJalan.setEnabled(akses.gettindakan_ralan());
        MnInputTindakan.setEnabled(akses.gettindakan_ralan());
        MnPemberianObat.setEnabled(akses.getberi_obat());
        MnInputObat.setEnabled(akses.getberi_obat());
        MnOperasi.setEnabled(akses.getoperasi());
        MnObatLangsung.setEnabled(akses.getberi_obat());
        MnTambahan.setEnabled(akses.gettambahan_biaya());
        MnPotongan.setEnabled(akses.getpotongan_biaya());
        MnPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnCariPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnCariRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnPenjualan.setEnabled(akses.getpenjualan_obat());        
        MnHapusTagihan.setEnabled(akses.gethapus_nota_salah());  
        MnPoli.setEnabled(akses.getbilling_ralan());
        MnDokter.setEnabled(akses.getbilling_ralan());
        MnPenjab.setEnabled(akses.getbilling_ralan());
        if(Sequel.cariIsi("select tampilkan_tombol_nota_ralan from set_nota").equals("Yes")){
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
        
        if(ttl>0) {
            total=ttl; 
        }
        
        
        tagihanppn=besarppn+total;
        TagihanPPn.setText(Valid.SetAngka3(tagihanppn));
        
        if(piutang<=0){
            kekurangan=(bayar+besarppn)-tagihanppn;
            jLabel5.setText("Bayar : Rp.");
            if(kekurangan<0){
                jLabel6.setText("Kekurangan : Rp.");
            }else{
                jLabel6.setText("Kembali : Rp.");
            }
                 
            TKembali.setText(Valid.SetAngka3(kekurangan));            
        }else{
            kekurangan=(tagihanppn-(bayar+besarppn)-piutang)* -1;
            jLabel5.setText("Uang Muka : Rp.");
            if(kekurangan>0){
                jLabel6.setText("Kelebihan : Rp.");
            }else{
                jLabel6.setText("Kekurangan : Rp.");
            }
                
            TKembali.setText(Valid.SetAngka3(kekurangan));  
        }  
    }
    
    
    public void tampilTambahan(String NoRawat) {
        norawat.setText(NoRawat);
        Valid.tabelKosong(tabModeTambahan);
        try{     
            pstambahan=koneksi.prepareStatement(sqlpstambahan);
            try{
                pstambahan.setString(1,norawat.getText());
                rstambahan=pstambahan.executeQuery();
                while(rstambahan.next()){
                    tabModeTambahan.addRow(new Object[]{rstambahan.getString(1),rstambahan.getString(2)});
                } 
            }catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstambahan != null){
                    rstambahan.close();
                } 
                if(pstambahan != null){
                    pstambahan.close();
                } 
            }      
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }            
    }  
    
    private void tampilPotongan(String NoRawat) {
             norawatpotongan.setText(NoRawat);
             Valid.tabelKosong(tabModePotongan);
             try{           
                 pspotongan=koneksi.prepareStatement(sqlpspotongan);
                 try{
                     pspotongan.setString(1,TNoRw.getText());
                     rspotongan=pspotongan.executeQuery();
                     while(rspotongan.next()){                    
                         tabModePotongan.addRow(new Object[]{rspotongan.getString(1),rspotongan.getString(2)});
                     } 
                 }catch (Exception e) {
                     System.out.println("Notifikasi : "+e);
                 } finally{
                     if(rspotongan != null){
                         rspotongan.close();
                     } 
                     if(pspotongan != null){
                         pspotongan.close();
                     } 
                 }
                    
             }catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
             }
    }
    
    private void prosesCariOperasi(){
        try{            
            subttl=0;
            psoperasi=koneksi.prepareStatement(sqlpsoperasi);
            try {
                psoperasi.setString(1,TNoRw.getText());
                rsoperasi=psoperasi.executeQuery();
                if(rsoperasi.next()){
                       tabModeRwJlDr.addRow(new Object[]{true,"Operasi",":","",null,null,null,null,"Operasi"}); 
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
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rsoperasi != null){
                    rsoperasi.close();
                } 
                if(psoperasi != null){
                    psoperasi.close();
                } 
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void setPiutang(){
        chkRadiologi.setSelected(true);
        chkLaborat.setSelected(true);
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
                     "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_jalan.besar_bayar,"+
                     "akun_bayar.ppn,detail_nota_jalan.besarppn from akun_bayar inner join detail_nota_jalan "+
                     "on akun_bayar.nama_bayar=detail_nota_jalan.nama_bayar where detail_nota_jalan.no_rawat=? and akun_bayar.nama_bayar like ? order by nama_bayar");
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
    
    private void isSimpan(){
        if(notaralan.equals("Yes")){
            BtnNotaActionPerformed(null);
            chkLaborat.setSelected(true);
            chkRadiologi.setSelected(true);    
            chkPotongan.setSelected(true);  
            chkTambahan.setSelected(true);  
            chkObat.setSelected(true);  
            chkAdministrasi.setSelected(true);  
            chkSarpras.setSelected(true);  
            chkTarifDokter.setSelected(true);  
            chkTarifPrm.setSelected(true);  
            isRawat2();            
        }

        if((chkLaborat.isSelected()==false)||(chkRadiologi.isSelected()==false)){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan tampilkan semua pilihan tagihan...!!!");
        }else{
            try {
                psnota=koneksi.prepareStatement(sqlpsnota);
                try {                                       
                    psnota.setString(1,TNoRw.getText());
                    psnota.setString(2,Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_jalan where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RJ/",6));
                    psnota.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                    psnota.setString(4,DTPTgl.getSelectedItem().toString().substring(11,19));
                    psnota.executeUpdate();
                } catch (Exception e) {
                    nota_jalan=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_jalan where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RJ/",6);
                    Sequel.meghapus("nota_jalan","no_rawat",TNoRw.getText());               
                    tbBilling.setValueAt(": "+nota_jalan,0,2);
                    psnota.setString(1,TNoRw.getText());
                    psnota.setString(2,nota_jalan);
                    psnota.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                    psnota.setString(4,DTPTgl.getSelectedItem().toString().substring(11,19));
                    psnota.executeUpdate();
                } finally{
                    if(psnota != null){
                        psnota.close();
                    } 
                }

                Sequel.AutoComitFalse();
                sukses=true; 
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
                            if((!tbBilling.getValueAt(i,8).toString().equals("Laborat"))&&(!tbBilling.getValueAt(i,8).toString().equals("Obat"))){
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
                        sukses=false;
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(psbiling != null){
                            psbiling.close();
                        } 
                    }
                }

                if(sukses==true){
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
                                if(Sequel.menyimpantf2("detail_nota_jalan","?,?,?,?","Akun bayar",4,new String[]{
                                        TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                                    })==true){
                                        Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar)+"','0'","Rekening");                 
                                }else{
                                    sukses=false;
                                }
                            }else if(countbayar==1){
                                if(piutang<=0){
                                    if(Sequel.menyimpantf2("detail_nota_jalan","?,?,?,?","Akun bayar",4,new String[]{
                                            TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(total)
                                        })==true){
                                            Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(total)+"','0'","Rekening");                 
                                    }else{
                                        sukses=false;
                                    } 
                                }else{
                                    if(Sequel.menyimpantf2("detail_nota_jalan","?,?,?,?","Akun bayar",4,new String[]{
                                            TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                                        })==true){
                                            Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar)+"','0'","Rekening");                 
                                    }else{
                                        sukses=false;
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

                            if(Sequel.menyimpantf2("detail_piutang_pasien","?,?,?,?,?,?","Aku Piutang",6,new String[]{
                                    TNoRw.getText(),tabModeAkunPiutang.getValueAt(r,0).toString(),tabModeAkunPiutang.getValueAt(r,2).toString(),
                                    Double.toString(itempiutang),Double.toString(itempiutang),Valid.SetTgl(tabModeAkunPiutang.getValueAt(r,4).toString())
                                })==true){
                                    Sequel.menyimpan("tampjurnal","'"+tabModeAkunPiutang.getValueAt(r,1).toString()+"','"+tabModeAkunPiutang.getValueAt(r,0).toString()+"','"+Double.toString(itempiutang)+"','0'","Rekening");                 
                            }else{
                                sukses=false;
                            }
                        }             
                    }
                    
                    if(sukses==true){
                        if((-1*ttlPotongan)>0){
                            Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','Potongan_Ralan','"+(-1*ttlPotongan)+"','0'","debet=debet+'"+(-1*ttlPotongan)+"'","kd_rek='"+Potongan_Ralan+"'");    
                        }

                        if((ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)>0){
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Tindakan Ralan','0','"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)+"'","kredit=kredit+'"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)+"'","kd_rek='"+Tindakan_Ralan+"'");    
                        }

                        if(ttlLaborat>0){
                            Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Laborat Ralan','0','"+ttlLaborat+"'","kredit=kredit+'"+(ttlLaborat)+"'","kd_rek='"+Laborat_Ralan+"'");    
                        }

                        if(ttlRadiologi>0){
                            Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','Radiologi Ralan','0','"+ttlRadiologi+"'","kredit=kredit+'"+(ttlRadiologi)+"'","kd_rek='"+Radiologi_Ralan+"'");    
                        }

                        if(ttlObat>0){
                            Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','Obat Ralan','0','"+ttlObat+"'","kredit=kredit+'"+(ttlObat)+"'","kd_rek='"+Obat_Ralan+"'");    
                        }

                        if(ttlRegistrasi>0){
                            Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','Registrasi Ralan','0','"+ttlRegistrasi+"'","kredit=kredit+'"+(ttlRegistrasi)+"'","kd_rek='"+Registrasi_Ralan+"'");    
                        }

                        if(ttlTambahan>0){
                            Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','Tambahan Ralan','0','"+ttlTambahan+"'","kredit=kredit+'"+(ttlTambahan)+"'","kd_rek='"+Tambahan_Ralan+"'");    
                        }

                        if(ttlOperasi>0){
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','Operasi Ralan','0','"+ttlOperasi+"'","kredit=kredit+'"+(ttlOperasi)+"'","kd_rek='"+Operasi_Ralan+"'");    
                        }

                        String alamat=Sequel.cariIsi("select almt_pj from reg_periksa where no_rawat=? ",TNoRw.getText());

                        if(piutang>0){
                            sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PIUTANG PASIEN RAWAT JALAN, DIPOSTING OLEH "+akses.getkode());
                            if(bayar>0){
                                Sequel.menyimpan2("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText().replaceAll("'","")+"','"+alamat.replaceAll("'","")+"',concat('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+
                                        "',' ',CURTIME()),'Uang Muka','"+total+"','"+bayar+"','Belum','"+akses.getkode()+"'","No.Rawat");
                            }
                            Sequel.queryu2("insert into piutang_pasien values ('"+TNoRw.getText()+"','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                                    TNoRM.getText()+"','Belum Lunas','"+total+"','"+bayar+"','"+piutang+"','"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"')");
                        }else if(piutang<=0){
                            sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBAYARAN PASIEN RAWAT JALAN, DIPOSTING OLEH "+akses.getkode());
                            Sequel.menyimpan2("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText().replaceAll("'","")+"','"+alamat.replaceAll("'","")+"',concat('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+
                                        "',' ',CURTIME()),'Pelunasan','"+total+"','"+total+"','Sudah','"+akses.getkode()+"'","No.Rawat");
                        }
                    }
                }
                    
                if(sukses==true){
                    Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"status_bayar='Sudah Bayar'");
                    Sequel.meghapus("temporary_tambahan_potongan","no_rawat",TNoRw.getText());
                    Sequel.Commit();
                    JOptionPane.showMessageDialog(null,"Proses simpan selesai...!");     
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                
                if(sukses==true){
                    if(notaralan.equals("Yes")){
                        this.dispose();
                    }
                }
            }catch (SQLException ex) {
                System.out.println("Notifikasi : "+ex);            
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Data yang sama dimasukkan sebelumnya...!");
            }
        }
    }
    
    
}

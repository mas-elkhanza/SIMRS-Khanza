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
package simrskhanza;

import bridging.BPJSCekNIK;
import bridging.BPJSCekNoKartu;
import bridging.BPJSNik;
import bridging.BPJSPeserta;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.grafikjkel;
import fungsi.grafikpasienperagama;
import fungsi.grafikpasienperpekerjaaan;
import fungsi.grafiksql;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.DocumentEvent;


/**
 *
 * @author igos
 */
public class DlgPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    public  DlgKabupaten kab=new DlgKabupaten(null,false);
    public  DlgKecamatan kec=new DlgKecamatan(null,false);
    public  DlgKelurahan kel=new DlgKelurahan(null,false);
    private int pilih=0,z=0,j=0,p_no_ktp=0,p_tmp_lahir=0,p_nm_ibu=0,p_alamat=0,
            p_pekerjaan=0,p_no_tlp=0,p_umur=0,p_namakeluarga=0,p_no_peserta=0,
            p_kelurahan=0,p_kecamatan=0,p_kabupaten=0,p_pekerjaanpj=0,
            p_alamatpj=0,p_kelurahanpj=0,p_kecamatanpj=0,p_kabupatenpj=0;
    private double jumlah=0,x=0,i=0;
    private String klg="SAUDARA",say="",pengurutan="",asalform="",bulan="",tahun="",awalantahun="",awalanbulan="",posisitahun="",
            no_ktp="",tmp_lahir="",nm_ibu="",alamat="",pekerjaan="",no_tlp="",
            umur="",namakeluarga="",no_peserta="",kelurahan="",kecamatan="",
            kabupaten="",pekerjaanpj="",alamatpj="",kelurahanpj="",kecamatanpj="",
            kabupatenpj="";
    private PreparedStatement ps,ps2,pscariwilayah,pssetalamat,pskelengkapan;
    private ResultSet rs;
    private BPJSCekNIK cekViaBPJS=new BPJSCekNIK();
    private BPJSCekNoKartu cekViaBPJSKartu=new BPJSCekNoKartu();
    private Date lahir;
    private LocalDate today=LocalDate.now();
    private LocalDate birthday;
    private Period p;
    private long p2;

    /** Creates new form DlgPas
     * @param parent
     * @param modal*/
    public DlgPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"P","No.R.M","Nama Pasien","No.SIM/KTP","J.K.","Tmp.Lahir","Tgl.Lahir","Nama Ibu","Alamat",
                      "G.D.","Pekerjaan","Stts.Nikah","Agama","Tgl.Daftar","No.Telp/HP","Umur","Pendidikan",
                      "Keluarga","Nama Keluarga","Asuransi/Askes","No.Peserta","Daftar","Pekerjaan P.J.","Alamat P.J."};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPasien.setModel(tabMode);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbPasien.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (z = 0; z < 24; z++) {
            TableColumn column = tbPasien.getColumnModel().getColumn(z);
            if(z==0){
                column.setPreferredWidth(20);
            }else if(z==1){
                column.setPreferredWidth(85);
            }else if(z==2){
                column.setPreferredWidth(190);
            }else if(z==3){
                column.setPreferredWidth(100);
            }else if(z==4){
                column.setPreferredWidth(35);
            }else if(z==5){
                column.setPreferredWidth(100);
            }else if(z==6){
                column.setPreferredWidth(70);
            }else if(z==7){
                column.setPreferredWidth(150);
            }else if(z==8){
                column.setPreferredWidth(190);
            }else if(z==9){
                column.setPreferredWidth(35);
            }else if(z==10){
                column.setPreferredWidth(100);
            }else if(z==11){
                column.setPreferredWidth(75);
            }else if(z==12){
                column.setPreferredWidth(75);
            }else if(z==13){
                column.setPreferredWidth(75);
            }else if(z==14){
                column.setPreferredWidth(80);
            }else if(z==15){
                column.setPreferredWidth(90);
            }else if(z==16){
                column.setPreferredWidth(80);
            }else if(z==17){
                column.setPreferredWidth(80);
            }else if(z==18){
                column.setPreferredWidth(150);
            }else if(z==19){
                column.setPreferredWidth(120);
            }else if(z==20){
                column.setPreferredWidth(100);
            }else if(z==21){
                column.setPreferredWidth(60);
            }else if(z==22){
                column.setPreferredWidth(85);
            }else if(z==23){
                column.setPreferredWidth(160);
            }
        }
        tbPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TNo.setDocument(new batasInput((byte)15).getKata(TNo));
        TNm.setDocument(new batasInput((byte)40).getKata(TNm));
        NmIbu.setDocument(new batasInput((byte)40).getKata(NmIbu));
        TKtp.setDocument(new batasInput((byte)20).getKata(TKtp));
        Kdpnj.setDocument(new batasInput((byte)3).getKata(Kdpnj));
        TTlp.setDocument(new batasInput((int)40).getKata(TTlp));
        TTmp.setDocument(new batasInput((byte)15).getKata(TTmp));
        Alamat.setDocument(new batasInput((int)200).getFilter(Alamat));
        AlamatPj.setDocument(new batasInput((int)100).getFilter(AlamatPj));
        Pekerjaan.setDocument(new batasInput((byte)15).getKata(Pekerjaan));
        PekerjaanPj.setDocument(new batasInput((byte)15).getKata(PekerjaanPj));
        TUmurTh.setDocument(new batasInput((byte)5).getOnlyAngka(TUmurTh));
        Saudara.setDocument(new batasInput((byte)50).getKata(Saudara));
        Kabupaten.setDocument(new batasInput((byte)60).getFilter(Kabupaten));
        Kecamatan.setDocument(new batasInput((byte)60).getFilter(Kecamatan));
        Kelurahan.setDocument(new batasInput((byte)60).getFilter(Kelurahan));
        KabupatenPj.setDocument(new batasInput((byte)60).getFilter(KabupatenPj));
        KecamatanPj.setDocument(new batasInput((byte)60).getFilter(KecamatanPj));
        KelurahanPj.setDocument(new batasInput((byte)60).getFilter(KelurahanPj));
        Kabupaten2.setDocument(new batasInput((byte)60).getFilter(Kabupaten2));
        Kecamatan2.setDocument(new batasInput((byte)60).getFilter(Kecamatan2));
        Kelurahan2.setDocument(new batasInput((byte)60).getFilter(Kelurahan2));
        TNoPeserta.setDocument(new batasInput((byte)25).getKata(TNoPeserta));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
        ChkInput.setSelected(false);
        isForm();
        
        pengurutan=Sequel.cariIsi("select urutan from set_urut_no_rkm_medis");
        tahun=Sequel.cariIsi("select tahun from set_urut_no_rkm_medis");
        bulan=Sequel.cariIsi("select bulan from set_urut_no_rkm_medis");
        posisitahun=Sequel.cariIsi("select posisi_tahun_bulan from set_urut_no_rkm_medis");
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPasien")){
                    if(penjab.getTable().getSelectedRow()!= -1){
                        Kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                        nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    }  
                    Kdpnj.requestFocus();
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
                if(var.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        penjab.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPasien")){
                    if(kab.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kabupaten.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                            Kabupaten.requestFocus();
                        }else if(pilih==2){                    
                            Kabupaten2.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                            Kabupaten2.requestFocus();
                        }else if(pilih==3){                    
                            KabupatenPj.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                            KabupatenPj.requestFocus();
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
        
        kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPasien")){
                    if(kec.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kecamatan.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                            Kecamatan.requestFocus();
                        }else if(pilih==2){                    
                            Kecamatan2.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                            Kecamatan2.requestFocus();
                        }else if(pilih==3){                    
                            KecamatanPj.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                            KecamatanPj.requestFocus();
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
        
        kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPasien")){
                    if(kel.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kelurahan.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                            Kelurahan.requestFocus();
                        }else if(pilih==2){                    
                            Kelurahan2.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                            Kelurahan2.requestFocus();
                        }else if(pilih==3){                    
                            KelurahanPj.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                            KelurahanPj.requestFocus();
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
        
        try {
            pssetalamat=koneksi.prepareStatement("select * from set_alamat_pasien");
            try {
                rs=pssetalamat.executeQuery();
                while(rs.next()){
                    Kelurahan.setEditable(rs.getBoolean("kelurahan"));
                    KelurahanPj.setEditable(rs.getBoolean("kelurahan"));
                    Kecamatan.setEditable(rs.getBoolean("kecamatan"));
                    KecamatanPj.setEditable(rs.getBoolean("kecamatan"));                    
                    Kabupaten.setEditable(rs.getBoolean("kabupaten"));
                    KabupatenPj.setEditable(rs.getBoolean("kabupaten"));
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pssetalamat!=null){
                    pssetalamat.close();
                }
            }
            
            pskelengkapan=koneksi.prepareStatement("select * from set_kelengkapan_data_pasien");
            try {
                rs=pskelengkapan.executeQuery();
                while(rs.next()){
                    no_ktp=rs.getString("no_ktp");
                    p_no_ktp=rs.getInt("p_no_ktp");
                    tmp_lahir=rs.getString("tmp_lahir");
                    p_tmp_lahir=rs.getInt("p_tmp_lahir");
                    nm_ibu=rs.getString("nm_ibu");
                    p_nm_ibu=rs.getInt("p_nm_ibu");
                    alamat=rs.getString("alamat");
                    p_alamat=rs.getInt("p_alamat");
                    pekerjaan=rs.getString("pekerjaan");
                    p_pekerjaan=rs.getInt("p_pekerjaan");
                    no_tlp=rs.getString("no_tlp");
                    p_no_tlp=rs.getInt("p_no_tlp");
                    umur=rs.getString("umur");
                    p_umur=rs.getInt("p_umur");
                    namakeluarga=rs.getString("namakeluarga");
                    p_namakeluarga=rs.getInt("p_namakeluarga");
                    no_peserta=rs.getString("no_peserta");
                    p_no_peserta=rs.getInt("p_no_peserta");
                    kelurahan=rs.getString("kelurahan");
                    p_kelurahan=rs.getInt("p_kelurahan");
                    kecamatan=rs.getString("kecamatan");
                    p_kecamatan=rs.getInt("p_kecamatan");
                    kabupaten=rs.getString("kabupaten");
                    p_kabupaten=rs.getInt("p_kabupaten");
                    pekerjaanpj=rs.getString("pekerjaanpj");
                    p_pekerjaanpj=rs.getInt("p_pekerjaanpj");
                    alamatpj=rs.getString("alamatpj");
                    p_alamatpj=rs.getInt("p_alamatpj");
                    kelurahanpj=rs.getString("kelurahanpj");
                    p_kelurahanpj=rs.getInt("p_kelurahanpj");
                    kecamatanpj=rs.getString("kecamatanpj");
                    p_kecamatanpj=rs.getInt("p_kecamatanpj");
                    kabupatenpj=rs.getString("kabupatenpj");
                    p_kabupatenpj=rs.getInt("p_kabupatenpj");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pskelengkapan!=null){
                    pskelengkapan.close();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        KartuPasien = new javax.swing.JMenu();
        MnKartu1 = new javax.swing.JMenuItem();
        MnKartu2 = new javax.swing.JMenuItem();
        MnKartu3 = new javax.swing.JMenuItem();
        MnKartu4 = new javax.swing.JMenuItem();
        MnKartu5 = new javax.swing.JMenuItem();
        Barcode = new javax.swing.JMenu();
        MnBarcodeRM = new javax.swing.JMenuItem();
        MnBarcodeRM1 = new javax.swing.JMenuItem();
        MnBarcodeRM2 = new javax.swing.JMenuItem();
        MnBarcodeRM3 = new javax.swing.JMenuItem();
        MnBarcodeRM4 = new javax.swing.JMenuItem();
        MnBarcodeRM5 = new javax.swing.JMenuItem();
        MnBarcodeRM6 = new javax.swing.JMenuItem();
        MnBarcodeRM7 = new javax.swing.JMenuItem();
        MenuIdentitas = new javax.swing.JMenu();
        MnIdentitas = new javax.swing.JMenuItem();
        MnIdentitas2 = new javax.swing.JMenuItem();
        MnIdentitas3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnKartuStatus = new javax.swing.JMenuItem();
        MenuBPJS = new javax.swing.JMenu();
        MnCekKepesertaan = new javax.swing.JMenuItem();
        MnCekNIK = new javax.swing.JMenuItem();
        ppKelahiranBayi = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MnLaporanRM = new javax.swing.JMenuItem();
        MnLaporanRM1 = new javax.swing.JMenuItem();
        MnLaporanRM2 = new javax.swing.JMenuItem();
        MnFormulirPendaftaran = new javax.swing.JMenuItem();
        MnSCreening = new javax.swing.JMenuItem();
        MnCopyResep = new javax.swing.JMenuItem();
        MnLembarKeluarMasuk = new javax.swing.JMenuItem();
        MnLembarKeluarMasuk2 = new javax.swing.JMenuItem();
        MnLaporanIGD = new javax.swing.JMenuItem();
        MnLembarAnamNesa = new javax.swing.JMenuItem();
        MnLembarGrafik = new javax.swing.JMenuItem();
        MnLembarCatatanPerkembangan = new javax.swing.JMenuItem();
        MnLembarCatatanKeperawatan = new javax.swing.JMenuItem();
        MnLaporanAnestesia = new javax.swing.JMenuItem();
        MnPengantarHemodalisa = new javax.swing.JMenuItem();
        MnCover = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ppGrafikPerAgama = new javax.swing.JMenuItem();
        ppGrafikPerPekerjaan = new javax.swing.JMenuItem();
        ppGrafikjkbayi = new javax.swing.JMenuItem();
        ppGrafikDemografi = new javax.swing.JMenuItem();
        ppRegistrasi = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppGabungRM = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Kd2 = new widget.TextBox();
        DlgDemografi = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        Kelurahan2 = new widget.TextBox();
        BtnSeek8 = new widget.Button();
        Kecamatan2 = new widget.TextBox();
        BtnSeek9 = new widget.Button();
        Kabupaten2 = new widget.TextBox();
        BtnSeek10 = new widget.Button();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        BtnPrint3 = new widget.Button();
        NoRm = new widget.TextBox();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnViaBPJSNik = new javax.swing.JMenuItem();
        MnViaBPJSNoKartu = new javax.swing.JMenuItem();
        WindowGabungRM = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        label40 = new widget.Label();
        NoRmTujuan = new widget.TextBox();
        NmPasienTujuan = new widget.TextBox();
        label41 = new widget.Label();
        BtnCari1 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPasien = new widget.Table();
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
        jLabel11 = new widget.Label();
        Carialamat = new widget.TextBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TTmp = new widget.TextBox();
        CmbJk = new widget.ComboBox();
        TNm = new widget.TextBox();
        jLabel8 = new widget.Label();
        CMbGd = new widget.ComboBox();
        jLabel9 = new widget.Label();
        jLabel13 = new widget.Label();
        DTPLahir = new widget.Tanggal();
        jLabel18 = new widget.Label();
        cmbAgama = new widget.ComboBox();
        jLabel19 = new widget.Label();
        CmbStts = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel12 = new widget.Label();
        Alamat = new widget.TextBox();
        TTlp = new widget.TextBox();
        TNo = new widget.TextBox();
        jLabel15 = new widget.Label();
        TKtp = new widget.TextBox();
        DTPDaftar = new widget.Tanggal();
        jLabel22 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel23 = new widget.Label();
        CMbPnd = new widget.ComboBox();
        Saudara = new widget.TextBox();
        R5 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R1 = new widget.RadioButton();
        jLabel24 = new widget.Label();
        Kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        Kelurahan = new widget.TextBox();
        Kecamatan = new widget.TextBox();
        Kabupaten = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        BtnKecamatan = new widget.Button();
        BtnKabupaten = new widget.Button();
        ChkDaftar = new widget.CekBox();
        jLabel14 = new widget.Label();
        NmIbu = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel25 = new widget.Label();
        PekerjaanPj = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        AlamatPj = new widget.TextBox();
        KecamatanPj = new widget.TextBox();
        BtnKecamatanPj = new widget.Button();
        KabupatenPj = new widget.TextBox();
        BtnKabupatenPj = new widget.Button();
        BtnKelurahanPj = new widget.Button();
        KelurahanPj = new widget.TextBox();
        jLabel28 = new widget.Label();
        TNoPeserta = new widget.TextBox();
        ChkRM = new widget.CekBox();
        R6 = new widget.RadioButton();
        BtnKelurahan1 = new widget.Button();
        TUmurTh = new widget.TextBox();
        jLabel31 = new widget.Label();
        TUmurBl = new widget.TextBox();
        jLabel29 = new widget.Label();
        TUmurHr = new widget.TextBox();
        jLabel30 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        KartuPasien.setBackground(new java.awt.Color(255, 255, 255));
        KartuPasien.setForeground(new java.awt.Color(60, 80, 50));
        KartuPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        KartuPasien.setText("Kartu Pasien");
        KartuPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        KartuPasien.setName("KartuPasien"); // NOI18N
        KartuPasien.setPreferredSize(new java.awt.Dimension(220, 26));

        MnKartu1.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu1.setForeground(new java.awt.Color(60, 80, 50));
        MnKartu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu1.setText("Kartu Pasien 1");
        MnKartu1.setName("MnKartu1"); // NOI18N
        MnKartu1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKartu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu1ActionPerformed(evt);
            }
        });
        KartuPasien.add(MnKartu1);

        MnKartu2.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu2.setForeground(new java.awt.Color(60, 80, 50));
        MnKartu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu2.setText("Kartu Pasien 2");
        MnKartu2.setName("MnKartu2"); // NOI18N
        MnKartu2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKartu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu2ActionPerformed(evt);
            }
        });
        KartuPasien.add(MnKartu2);

        MnKartu3.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu3.setForeground(new java.awt.Color(60, 80, 50));
        MnKartu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu3.setText("Kartu Pasien 3");
        MnKartu3.setName("MnKartu3"); // NOI18N
        MnKartu3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKartu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu3ActionPerformed(evt);
            }
        });
        KartuPasien.add(MnKartu3);

        MnKartu4.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu4.setForeground(new java.awt.Color(60, 80, 50));
        MnKartu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu4.setText("Kartu Pasien 4");
        MnKartu4.setName("MnKartu4"); // NOI18N
        MnKartu4.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKartu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu4ActionPerformed(evt);
            }
        });
        KartuPasien.add(MnKartu4);

        MnKartu5.setBackground(new java.awt.Color(255, 255, 255));
        MnKartu5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu5.setForeground(new java.awt.Color(60, 80, 50));
        MnKartu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartu5.setText("Kartu Pasien 5");
        MnKartu5.setName("MnKartu5"); // NOI18N
        MnKartu5.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKartu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu5ActionPerformed(evt);
            }
        });
        KartuPasien.add(MnKartu5);

        jPopupMenu1.add(KartuPasien);

        Barcode.setBackground(new java.awt.Color(255, 255, 255));
        Barcode.setForeground(new java.awt.Color(60, 80, 50));
        Barcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Barcode.setText("Label Rekam Medis");
        Barcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Barcode.setName("Barcode"); // NOI18N
        Barcode.setPreferredSize(new java.awt.Dimension(220, 26));

        MnBarcodeRM.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM.setText("Label Rekam Medis 1");
        MnBarcodeRM.setName("MnBarcodeRM"); // NOI18N
        MnBarcodeRM.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRMActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM);

        MnBarcodeRM1.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM1.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM1.setText("Label Rekam Medis 2");
        MnBarcodeRM1.setName("MnBarcodeRM1"); // NOI18N
        MnBarcodeRM1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM1ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM1);

        MnBarcodeRM2.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM2.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM2.setText("Label Rekam Medis 3");
        MnBarcodeRM2.setName("MnBarcodeRM2"); // NOI18N
        MnBarcodeRM2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM2ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM2);

        MnBarcodeRM3.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM3.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM3.setText("Label Rekam Medis 4");
        MnBarcodeRM3.setName("MnBarcodeRM3"); // NOI18N
        MnBarcodeRM3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM3ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM3);

        MnBarcodeRM4.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM4.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM4.setText("Label Rekam Medis 5");
        MnBarcodeRM4.setName("MnBarcodeRM4"); // NOI18N
        MnBarcodeRM4.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM4ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM4);

        MnBarcodeRM5.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM5.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM5.setText("Label Rekam Medis 6");
        MnBarcodeRM5.setName("MnBarcodeRM5"); // NOI18N
        MnBarcodeRM5.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM5ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM5);

        MnBarcodeRM6.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM6.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM6.setText("Label Rekam Medis 7");
        MnBarcodeRM6.setName("MnBarcodeRM6"); // NOI18N
        MnBarcodeRM6.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM6ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM6);

        MnBarcodeRM7.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcodeRM7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM7.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcodeRM7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM7.setText("Label Rekam Medis 8");
        MnBarcodeRM7.setToolTipText("");
        MnBarcodeRM7.setName("MnBarcodeRM7"); // NOI18N
        MnBarcodeRM7.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM7ActionPerformed(evt);
            }
        });
        Barcode.add(MnBarcodeRM7);

        jPopupMenu1.add(Barcode);

        MenuIdentitas.setBackground(new java.awt.Color(255, 255, 255));
        MenuIdentitas.setForeground(new java.awt.Color(60, 80, 50));
        MenuIdentitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuIdentitas.setText("Identitas Pasien");
        MenuIdentitas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuIdentitas.setName("MenuIdentitas"); // NOI18N
        MenuIdentitas.setPreferredSize(new java.awt.Dimension(220, 26));

        MnIdentitas.setBackground(new java.awt.Color(255, 255, 255));
        MnIdentitas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnIdentitas.setForeground(new java.awt.Color(60, 80, 50));
        MnIdentitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnIdentitas.setText("Identitas Pasien");
        MnIdentitas.setName("MnIdentitas"); // NOI18N
        MnIdentitas.setPreferredSize(new java.awt.Dimension(200, 26));
        MnIdentitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnIdentitasActionPerformed(evt);
            }
        });
        MenuIdentitas.add(MnIdentitas);

        MnIdentitas2.setBackground(new java.awt.Color(255, 255, 255));
        MnIdentitas2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnIdentitas2.setForeground(new java.awt.Color(60, 80, 50));
        MnIdentitas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnIdentitas2.setText("Identitas Pasien 2");
        MnIdentitas2.setName("MnIdentitas2"); // NOI18N
        MnIdentitas2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnIdentitas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnIdentitas2ActionPerformed(evt);
            }
        });
        MenuIdentitas.add(MnIdentitas2);

        MnIdentitas3.setBackground(new java.awt.Color(255, 255, 255));
        MnIdentitas3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnIdentitas3.setForeground(new java.awt.Color(60, 80, 50));
        MnIdentitas3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnIdentitas3.setText("Identitas Pasien 3");
        MnIdentitas3.setName("MnIdentitas3"); // NOI18N
        MnIdentitas3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnIdentitas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnIdentitas3ActionPerformed(evt);
            }
        });
        MenuIdentitas.add(MnIdentitas3);

        jPopupMenu1.add(MenuIdentitas);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 255));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(60, 80, 50));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Kartu Indeks Keseluruhan");
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBarcode);

        MnKartuStatus.setBackground(new java.awt.Color(255, 255, 255));
        MnKartuStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartuStatus.setForeground(new java.awt.Color(60, 80, 50));
        MnKartuStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKartuStatus.setText("Kartu Indeks Pasien Yang Dipilih");
        MnKartuStatus.setName("MnKartuStatus"); // NOI18N
        MnKartuStatus.setPreferredSize(new java.awt.Dimension(220, 26));
        MnKartuStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuStatusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKartuStatus);

        MenuBPJS.setBackground(new java.awt.Color(255, 255, 255));
        MenuBPJS.setForeground(new java.awt.Color(60, 80, 50));
        MenuBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuBPJS.setText("BPJS");
        MenuBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuBPJS.setName("MenuBPJS"); // NOI18N
        MenuBPJS.setPreferredSize(new java.awt.Dimension(220, 26));

        MnCekKepesertaan.setBackground(new java.awt.Color(255, 255, 255));
        MnCekKepesertaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekKepesertaan.setForeground(new java.awt.Color(60, 80, 50));
        MnCekKepesertaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekKepesertaan.setText("Pencarian Peserta Berdasarkan Nomor Kepesertaan");
        MnCekKepesertaan.setName("MnCekKepesertaan"); // NOI18N
        MnCekKepesertaan.setPreferredSize(new java.awt.Dimension(300, 26));
        MnCekKepesertaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekKepesertaanActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekKepesertaan);

        MnCekNIK.setBackground(new java.awt.Color(255, 255, 255));
        MnCekNIK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekNIK.setForeground(new java.awt.Color(60, 80, 50));
        MnCekNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekNIK.setText("Pencarian Peserta Berdasarkan NIK/No.KTP");
        MnCekNIK.setName("MnCekNIK"); // NOI18N
        MnCekNIK.setPreferredSize(new java.awt.Dimension(300, 26));
        MnCekNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekNIKActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekNIK);

        jPopupMenu1.add(MenuBPJS);

        ppKelahiranBayi.setBackground(new java.awt.Color(255, 255, 255));
        ppKelahiranBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppKelahiranBayi.setForeground(new java.awt.Color(60, 80, 50));
        ppKelahiranBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppKelahiranBayi.setText("Data Kelahiran Bayi");
        ppKelahiranBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppKelahiranBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppKelahiranBayi.setName("ppKelahiranBayi"); // NOI18N
        ppKelahiranBayi.setPreferredSize(new java.awt.Dimension(220, 26));
        ppKelahiranBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppKelahiranBayiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppKelahiranBayi);

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setForeground(new java.awt.Color(60, 80, 50));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu1.setText("Berkas Rekam Medis");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(220, 26));

        MnLaporanRM.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRM.setForeground(new java.awt.Color(60, 80, 50));
        MnLaporanRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRM.setText("Lembar Rawat Jalan Model 1");
        MnLaporanRM.setName("MnLaporanRM"); // NOI18N
        MnLaporanRM.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLaporanRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRMActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRM);

        MnLaporanRM1.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRM1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRM1.setForeground(new java.awt.Color(60, 80, 50));
        MnLaporanRM1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRM1.setText("Lembar Rawat Jalan Model 2");
        MnLaporanRM1.setName("MnLaporanRM1"); // NOI18N
        MnLaporanRM1.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLaporanRM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRM1ActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRM1);

        MnLaporanRM2.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRM2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRM2.setForeground(new java.awt.Color(60, 80, 50));
        MnLaporanRM2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRM2.setText("Lembar Rawat Jalan Model 3");
        MnLaporanRM2.setName("MnLaporanRM2"); // NOI18N
        MnLaporanRM2.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLaporanRM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRM2ActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRM2);

        MnFormulirPendaftaran.setBackground(new java.awt.Color(255, 255, 255));
        MnFormulirPendaftaran.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPendaftaran.setForeground(new java.awt.Color(60, 80, 50));
        MnFormulirPendaftaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPendaftaran.setText("Formulir Pendaftaran Pasien");
        MnFormulirPendaftaran.setName("MnFormulirPendaftaran"); // NOI18N
        MnFormulirPendaftaran.setPreferredSize(new java.awt.Dimension(300, 26));
        MnFormulirPendaftaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPendaftaranActionPerformed(evt);
            }
        });
        jMenu1.add(MnFormulirPendaftaran);

        MnSCreening.setBackground(new java.awt.Color(255, 255, 255));
        MnSCreening.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSCreening.setForeground(new java.awt.Color(60, 80, 50));
        MnSCreening.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSCreening.setText("Lembar Screening Awal Pasien Masuk Rawat Jalan");
        MnSCreening.setName("MnSCreening"); // NOI18N
        MnSCreening.setPreferredSize(new java.awt.Dimension(300, 26));
        MnSCreening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSCreeningActionPerformed(evt);
            }
        });
        jMenu1.add(MnSCreening);

        MnCopyResep.setBackground(new java.awt.Color(255, 255, 255));
        MnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyResep.setForeground(new java.awt.Color(60, 80, 50));
        MnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyResep.setText("Formulir Penempelan Copy Resep");
        MnCopyResep.setName("MnCopyResep"); // NOI18N
        MnCopyResep.setPreferredSize(new java.awt.Dimension(300, 26));
        MnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyResepActionPerformed(evt);
            }
        });
        jMenu1.add(MnCopyResep);

        MnLembarKeluarMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarKeluarMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarKeluarMasuk.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarKeluarMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarKeluarMasuk.setText("Lembar Masuk Keluar Model 1");
        MnLembarKeluarMasuk.setName("MnLembarKeluarMasuk"); // NOI18N
        MnLembarKeluarMasuk.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarKeluarMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarKeluarMasukActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarKeluarMasuk);

        MnLembarKeluarMasuk2.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarKeluarMasuk2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarKeluarMasuk2.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarKeluarMasuk2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarKeluarMasuk2.setText("Lembar Masuk Keluar Model 2");
        MnLembarKeluarMasuk2.setName("MnLembarKeluarMasuk2"); // NOI18N
        MnLembarKeluarMasuk2.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarKeluarMasuk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarKeluarMasuk2ActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarKeluarMasuk2);

        MnLaporanIGD.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanIGD.setForeground(new java.awt.Color(60, 80, 50));
        MnLaporanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanIGD.setText("Laporan IGD");
        MnLaporanIGD.setName("MnLaporanIGD"); // NOI18N
        MnLaporanIGD.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLaporanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanIGDActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanIGD);

        MnLembarAnamNesa.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarAnamNesa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarAnamNesa.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarAnamNesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarAnamNesa.setText("Lembar Anamnesa");
        MnLembarAnamNesa.setName("MnLembarAnamNesa"); // NOI18N
        MnLembarAnamNesa.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarAnamNesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarAnamNesaActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarAnamNesa);

        MnLembarGrafik.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarGrafik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarGrafik.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarGrafik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarGrafik.setText("Lembar Grafik");
        MnLembarGrafik.setName("MnLembarGrafik"); // NOI18N
        MnLembarGrafik.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarGrafik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarGrafikActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarGrafik);

        MnLembarCatatanPerkembangan.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarCatatanPerkembangan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCatatanPerkembangan.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarCatatanPerkembangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCatatanPerkembangan.setText("Lembar Catatan Perkembangan");
        MnLembarCatatanPerkembangan.setName("MnLembarCatatanPerkembangan"); // NOI18N
        MnLembarCatatanPerkembangan.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarCatatanPerkembangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCatatanPerkembanganActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarCatatanPerkembangan);

        MnLembarCatatanKeperawatan.setBackground(new java.awt.Color(255, 255, 255));
        MnLembarCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCatatanKeperawatan.setForeground(new java.awt.Color(60, 80, 50));
        MnLembarCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCatatanKeperawatan.setText("Lembar Catatan Keperawatan");
        MnLembarCatatanKeperawatan.setName("MnLembarCatatanKeperawatan"); // NOI18N
        MnLembarCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLembarCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCatatanKeperawatanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLembarCatatanKeperawatan);

        MnLaporanAnestesia.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanAnestesia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanAnestesia.setForeground(new java.awt.Color(60, 80, 50));
        MnLaporanAnestesia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanAnestesia.setText("Lembar Laporan Anastesia");
        MnLaporanAnestesia.setName("MnLaporanAnestesia"); // NOI18N
        MnLaporanAnestesia.setPreferredSize(new java.awt.Dimension(300, 26));
        MnLaporanAnestesia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanAnestesiaActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanAnestesia);

        MnPengantarHemodalisa.setBackground(new java.awt.Color(255, 255, 255));
        MnPengantarHemodalisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPengantarHemodalisa.setForeground(new java.awt.Color(60, 80, 50));
        MnPengantarHemodalisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPengantarHemodalisa.setText("Pengantar Hemodialisa");
        MnPengantarHemodalisa.setName("MnPengantarHemodalisa"); // NOI18N
        MnPengantarHemodalisa.setPreferredSize(new java.awt.Dimension(300, 26));
        MnPengantarHemodalisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPengantarHemodalisaActionPerformed(evt);
            }
        });
        jMenu1.add(MnPengantarHemodalisa);

        MnCover.setBackground(new java.awt.Color(255, 255, 255));
        MnCover.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCover.setForeground(new java.awt.Color(60, 80, 50));
        MnCover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCover.setText("Cover Rekam Medis");
        MnCover.setName("MnCover"); // NOI18N
        MnCover.setPreferredSize(new java.awt.Dimension(300, 26));
        MnCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCoverActionPerformed(evt);
            }
        });
        jMenu1.add(MnCover);

        jPopupMenu1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setForeground(new java.awt.Color(60, 80, 50));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu2.setText("Grafik Analisa");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(220, 26));

        ppGrafikPerAgama.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerAgama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerAgama.setForeground(new java.awt.Color(60, 80, 50));
        ppGrafikPerAgama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerAgama.setText("Grafik Pasien Per Agama");
        ppGrafikPerAgama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerAgama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerAgama.setName("ppGrafikPerAgama"); // NOI18N
        ppGrafikPerAgama.setPreferredSize(new java.awt.Dimension(230, 26));
        ppGrafikPerAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerAgamaActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerAgama);

        ppGrafikPerPekerjaan.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerPekerjaan.setForeground(new java.awt.Color(60, 80, 50));
        ppGrafikPerPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerPekerjaan.setText("Grafik Pasien Per Pekerjaan");
        ppGrafikPerPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerPekerjaan.setName("ppGrafikPerPekerjaan"); // NOI18N
        ppGrafikPerPekerjaan.setPreferredSize(new java.awt.Dimension(230, 26));
        ppGrafikPerPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerPekerjaanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerPekerjaan);

        ppGrafikjkbayi.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikjkbayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikjkbayi.setForeground(new java.awt.Color(60, 80, 50));
        ppGrafikjkbayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikjkbayi.setText("Grafik Jenis Kelamin Pasien");
        ppGrafikjkbayi.setActionCommand("Grafik Pasien Per Jenis Kelamin");
        ppGrafikjkbayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikjkbayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikjkbayi.setName("ppGrafikjkbayi"); // NOI18N
        ppGrafikjkbayi.setPreferredSize(new java.awt.Dimension(230, 26));
        ppGrafikjkbayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikjkbayiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikjkbayi);

        ppGrafikDemografi.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikDemografi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikDemografi.setForeground(new java.awt.Color(60, 80, 50));
        ppGrafikDemografi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikDemografi.setText("Demografi Pasien");
        ppGrafikDemografi.setActionCommand("Grafik Demografi Pasien");
        ppGrafikDemografi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikDemografi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikDemografi.setName("ppGrafikDemografi"); // NOI18N
        ppGrafikDemografi.setPreferredSize(new java.awt.Dimension(230, 26));
        ppGrafikDemografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikDemografiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikDemografi);

        jPopupMenu1.add(jMenu2);

        ppRegistrasi.setBackground(new java.awt.Color(255, 255, 255));
        ppRegistrasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRegistrasi.setForeground(new java.awt.Color(60, 80, 50));
        ppRegistrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRegistrasi.setText("Tampilkan Banyak Daftar");
        ppRegistrasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRegistrasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRegistrasi.setName("ppRegistrasi"); // NOI18N
        ppRegistrasi.setPreferredSize(new java.awt.Dimension(220, 26));
        ppRegistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRegistrasiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRegistrasi);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 255));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(60, 80, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(220, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 255));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(60, 80, 50));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(220, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCatatanPasien);

        ppGabungRM.setBackground(new java.awt.Color(255, 255, 255));
        ppGabungRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGabungRM.setForeground(new java.awt.Color(60, 80, 50));
        ppGabungRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGabungRM.setText("Gabungkan Data RM");
        ppGabungRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGabungRM.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGabungRM.setName("ppGabungRM"); // NOI18N
        ppGabungRM.setPreferredSize(new java.awt.Dimension(220, 26));
        ppGabungRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGabungRMBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGabungRM);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgDemografi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgDemografi.setName("DlgDemografi"); // NOI18N
        DlgDemografi.setUndecorated(true);
        DlgDemografi.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Demografi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Grafik");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(110, 110, 100, 30);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 110, 100, 30);

        Kelurahan2.setHighlighter(null);
        Kelurahan2.setName("Kelurahan2"); // NOI18N
        Kelurahan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kelurahan2);
        Kelurahan2.setBounds(105, 70, 350, 23);

        BtnSeek8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek8.setMnemonic('1');
        BtnSeek8.setToolTipText("ALt+1");
        BtnSeek8.setName("BtnSeek8"); // NOI18N
        BtnSeek8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek8ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek8);
        BtnSeek8.setBounds(460, 70, 28, 23);

        Kecamatan2.setHighlighter(null);
        Kecamatan2.setName("Kecamatan2"); // NOI18N
        Kecamatan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kecamatan2);
        Kecamatan2.setBounds(105, 40, 350, 23);

        BtnSeek9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek9.setMnemonic('1');
        BtnSeek9.setToolTipText("ALt+1");
        BtnSeek9.setName("BtnSeek9"); // NOI18N
        BtnSeek9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek9ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek9);
        BtnSeek9.setBounds(460, 40, 28, 23);

        Kabupaten2.setHighlighter(null);
        Kabupaten2.setName("Kabupaten2"); // NOI18N
        Kabupaten2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(Kabupaten2);
        Kabupaten2.setBounds(105, 10, 350, 23);

        BtnSeek10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek10.setMnemonic('1');
        BtnSeek10.setToolTipText("ALt+1");
        BtnSeek10.setName("BtnSeek10"); // NOI18N
        BtnSeek10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek10ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSeek10);
        BtnSeek10.setBounds(460, 10, 28, 23);

        jLabel33.setText("Kelurahan :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 70, 100, 23);

        jLabel34.setText("Kabupaten :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa2.add(jLabel34);
        jLabel34.setBounds(0, 10, 100, 23);

        jLabel35.setText("Kecamatan :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa2.add(jLabel35);
        jLabel35.setBounds(0, 40, 100, 23);

        BtnPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint3.setMnemonic('T');
        BtnPrint3.setText("Cetak");
        BtnPrint3.setToolTipText("Alt+T");
        BtnPrint3.setName("BtnPrint3"); // NOI18N
        BtnPrint3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint3ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint3);
        BtnPrint3.setBounds(10, 110, 100, 30);

        internalFrame3.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgDemografi.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        NoRm.setHighlighter(null);
        NoRm.setName("NoRm"); // NOI18N
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnViaBPJSNik.setBackground(new java.awt.Color(255, 255, 255));
        MnViaBPJSNik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnViaBPJSNik.setForeground(new java.awt.Color(60, 80, 50));
        MnViaBPJSNik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnViaBPJSNik.setText("Cek Via NIK Web Servis BPJS");
        MnViaBPJSNik.setName("MnViaBPJSNik"); // NOI18N
        MnViaBPJSNik.setPreferredSize(new java.awt.Dimension(250, 25));
        MnViaBPJSNik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnViaBPJSNikActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnViaBPJSNik);

        MnViaBPJSNoKartu.setBackground(new java.awt.Color(255, 255, 255));
        MnViaBPJSNoKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnViaBPJSNoKartu.setForeground(new java.awt.Color(60, 80, 50));
        MnViaBPJSNoKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnViaBPJSNoKartu.setText("Cek Via No Kartu Web Servis BPJS");
        MnViaBPJSNoKartu.setName("MnViaBPJSNoKartu"); // NOI18N
        MnViaBPJSNoKartu.setPreferredSize(new java.awt.Dimension(250, 25));
        MnViaBPJSNoKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnViaBPJSNoKartuActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnViaBPJSNoKartu);

        WindowGabungRM.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGabungRM.setModal(true);
        WindowGabungRM.setName("WindowGabungRM"); // NOI18N
        WindowGabungRM.setUndecorated(true);
        WindowGabungRM.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Gabungkan Ke Nomor RM ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame8.setLayout(null);

        BtnCloseIn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn6.setMnemonic('P');
        BtnCloseIn6.setText("Tutup");
        BtnCloseIn6.setToolTipText("Alt+P");
        BtnCloseIn6.setName("BtnCloseIn6"); // NOI18N
        BtnCloseIn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn6ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnCloseIn6);
        BtnCloseIn6.setBounds(130, 87, 100, 30);

        BtnSimpan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan6.setMnemonic('S');
        BtnSimpan6.setText("Simpan");
        BtnSimpan6.setToolTipText("Alt+S");
        BtnSimpan6.setName("BtnSimpan6"); // NOI18N
        BtnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan6ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnSimpan6);
        BtnSimpan6.setBounds(20, 87, 100, 30);

        label40.setText("No.Rekam Medik :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(35, 23));
        internalFrame8.add(label40);
        label40.setBounds(0, 22, 105, 23);

        NoRmTujuan.setHighlighter(null);
        NoRmTujuan.setName("NoRmTujuan"); // NOI18N
        NoRmTujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmTujuanKeyPressed(evt);
            }
        });
        internalFrame8.add(NoRmTujuan);
        NoRmTujuan.setBounds(108, 22, 100, 23);

        NmPasienTujuan.setEditable(false);
        NmPasienTujuan.setHighlighter(null);
        NmPasienTujuan.setName("NmPasienTujuan"); // NOI18N
        internalFrame8.add(NmPasienTujuan);
        NmPasienTujuan.setBounds(108, 52, 300, 23);

        label41.setText("Nama Pasien :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(35, 23));
        internalFrame8.add(label41);
        label41.setBounds(0, 52, 105, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnCari1);
        BtnCari1.setBounds(210, 22, 28, 23);

        WindowGabungRM.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPasien.setAutoCreateRowSorter(true);
        tbPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPasien.setComponentPopupMenu(jPopupMenu1);
        tbPasien.setName("tbPasien"); // NOI18N
        tbPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPasienMouseClicked(evt);
            }
        });
        tbPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPasienKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPasien);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(58, 23));
        panelGlass9.add(jLabel11);

        Carialamat.setName("Carialamat"); // NOI18N
        Carialamat.setPreferredSize(new java.awt.Dimension(340, 23));
        Carialamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CarialamatKeyPressed(evt);
            }
        });
        panelGlass9.add(Carialamat);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel7.setText("Key Word :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel7);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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

        jLabel6.setText("Limit Data :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100", "200", "300", "400", "500", "1000", "Semua" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setOpaque(false);
        cmbHlm.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(cmbHlm);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 309));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 168));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rekam Medis :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(4, 12, 95, 23);

        jLabel4.setText("Nama Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(4, 42, 95, 23);

        TTmp.setName("TTmp"); // NOI18N
        TTmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpKeyPressed(evt);
            }
        });
        FormInput.add(TTmp);
        TTmp.setBounds(102, 102, 188, 23);

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.setOpaque(false);
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });
        FormInput.add(CmbJk);
        CmbJk.setBounds(102, 72, 110, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(102, 42, 290, 23);

        jLabel8.setText("J.K. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(4, 72, 95, 23);

        CMbGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "AB", "O" }));
        CMbGd.setName("CMbGd"); // NOI18N
        CMbGd.setOpaque(false);
        CMbGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbGdKeyPressed(evt);
            }
        });
        FormInput.add(CMbGd);
        CMbGd.setBounds(322, 72, 70, 23);

        jLabel9.setText("Gol. Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(247, 72, 72, 23);

        jLabel13.setText("Tmp/Tgl. Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(4, 102, 95, 23);

        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-09-2017" }));
        DTPLahir.setDisplayFormat("dd-MM-yyyy");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPLahirItemStateChanged(evt);
            }
        });
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir);
        DTPLahir.setBounds(292, 102, 100, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(402, 12, 90, 23);

        cmbAgama.setBackground(new java.awt.Color(245, 253, 240));
        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "KATOLIK", "HINDU", "BUDHA", "KONG HU CHU", "-" }));
        cmbAgama.setLightWeightPopupEnabled(false);
        cmbAgama.setName("cmbAgama"); // NOI18N
        cmbAgama.setOpaque(false);
        cmbAgama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAgamaKeyPressed(evt);
            }
        });
        FormInput.add(cmbAgama);
        cmbAgama.setBounds(496, 12, 130, 23);

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(629, 12, 120, 23);

        CmbStts.setBackground(new java.awt.Color(245, 253, 240));
        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.setOpaque(false);
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormInput.add(CmbStts);
        CmbStts.setBounds(753, 12, 120, 23);

        jLabel20.setText("Alamat Pasien :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(402, 162, 90, 23);

        jLabel21.setText("No.Telp :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(402, 102, 90, 23);

        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(496, 132, 120, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(402, 132, 90, 23);

        Alamat.setText("ALAMAT");
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatMouseMoved(evt);
            }
        });
        Alamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatMouseExited(evt);
            }
        });
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormInput.add(Alamat);
        Alamat.setBounds(496, 162, 213, 23);

        TTlp.setHighlighter(null);
        TTlp.setName("TTlp"); // NOI18N
        TTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTlpKeyPressed(evt);
            }
        });
        FormInput.add(TTlp);
        TTlp.setBounds(496, 102, 150, 23);

        TNo.setEditable(false);
        TNo.setBackground(new java.awt.Color(245, 250, 240));
        TNo.setHighlighter(null);
        TNo.setName("TNo"); // NOI18N
        TNo.setOpaque(true);
        TNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoKeyPressed(evt);
            }
        });
        FormInput.add(TNo);
        TNo.setBounds(102, 12, 160, 23);

        jLabel15.setText("No.KTP/SIM :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(628, 132, 80, 23);

        TKtp.setComponentPopupMenu(jPopupMenu2);
        TKtp.setName("TKtp"); // NOI18N
        TKtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtpKeyPressed(evt);
            }
        });
        FormInput.add(TKtp);
        TKtp.setBounds(712, 132, 130, 23);

        DTPDaftar.setEditable(false);
        DTPDaftar.setForeground(new java.awt.Color(50, 70, 50));
        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-09-2017" }));
        DTPDaftar.setDisplayFormat("dd-MM-yyyy");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);
        DTPDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPDaftarKeyPressed(evt);
            }
        });
        FormInput.add(DTPDaftar);
        DTPDaftar.setBounds(753, 102, 93, 23);

        jLabel22.setText("Pertama Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(649, 102, 100, 23);

        jLabel17.setText("Umur :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(4, 132, 95, 23);

        jLabel23.setText("Pendidikan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(247, 132, 72, 23);

        CMbPnd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        CMbPnd.setName("CMbPnd"); // NOI18N
        CMbPnd.setOpaque(false);
        CMbPnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbPndKeyPressed(evt);
            }
        });
        FormInput.add(CMbPnd);
        CMbPnd.setBounds(322, 132, 70, 23);

        Saudara.setName("Saudara"); // NOI18N
        Saudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaudaraKeyPressed(evt);
            }
        });
        FormInput.add(Saudara);
        Saudara.setBounds(102, 222, 290, 23);

        R5.setBorder(null);
        buttonGroup1.add(R5);
        R5.setSelected(true);
        R5.setText("Saudara");
        R5.setIconTextGap(0);
        R5.setName("R5"); // NOI18N
        FormInput.add(R5);
        R5.setBounds(287, 192, 60, 23);

        R4.setBorder(null);
        buttonGroup1.add(R4);
        R4.setText("Suami");
        R4.setIconTextGap(0);
        R4.setName("R4"); // NOI18N
        FormInput.add(R4);
        R4.setBounds(236, 192, 50, 23);

        R3.setBorder(null);
        buttonGroup1.add(R3);
        R3.setText("Istri");
        R3.setIconTextGap(0);
        R3.setName("R3"); // NOI18N
        FormInput.add(R3);
        R3.setBounds(191, 192, 44, 23);

        R2.setBorder(null);
        buttonGroup1.add(R2);
        R2.setText("Ibu");
        R2.setIconTextGap(0);
        R2.setName("R2"); // NOI18N
        FormInput.add(R2);
        R2.setBounds(149, 192, 40, 23);

        R1.setBorder(null);
        buttonGroup1.add(R1);
        R1.setText("Ayah");
        R1.setIconTextGap(0);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(R1);
        R1.setBounds(101, 192, 46, 23);

        jLabel24.setText("Askes/Asuransi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(402, 42, 90, 23);

        Kdpnj.setText("-");
        Kdpnj.setHighlighter(null);
        Kdpnj.setName("Kdpnj"); // NOI18N
        Kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdpnjKeyPressed(evt);
            }
        });
        FormInput.add(Kdpnj);
        Kdpnj.setBounds(496, 42, 80, 23);

        nmpnj.setEditable(false);
        nmpnj.setText("-");
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(578, 42, 265, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("ALt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(BtnPenjab);
        BtnPenjab.setBounds(845, 42, 28, 23);

        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        FormInput.add(Kelurahan);
        Kelurahan.setBounds(712, 162, 130, 23);

        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        FormInput.add(Kecamatan);
        Kecamatan.setBounds(496, 192, 130, 23);

        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        FormInput.add(Kabupaten);
        Kabupaten.setBounds(663, 192, 130, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahan);
        BtnKelurahan.setBounds(845, 162, 28, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        FormInput.add(BtnKecamatan);
        BtnKecamatan.setBounds(629, 192, 28, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        FormInput.add(BtnKabupaten);
        BtnKabupaten.setBounds(796, 192, 28, 23);

        ChkDaftar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(175, 180, 170)));
        ChkDaftar.setForeground(new java.awt.Color(153, 0, 51));
        ChkDaftar.setBorderPainted(true);
        ChkDaftar.setBorderPaintedFlat(true);
        ChkDaftar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDaftar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDaftar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDaftar.setName("ChkDaftar"); // NOI18N
        ChkDaftar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkDaftarItemStateChanged(evt);
            }
        });
        FormInput.add(ChkDaftar);
        ChkDaftar.setBounds(850, 102, 23, 23);

        jLabel14.setText("Nama Ibu :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(4, 162, 95, 23);

        NmIbu.setName("NmIbu"); // NOI18N
        NmIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmIbuKeyPressed(evt);
            }
        });
        FormInput.add(NmIbu);
        NmIbu.setBounds(102, 162, 290, 23);

        jLabel16.setText("Png. Jawab :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(4, 192, 95, 23);

        jLabel25.setText("Png. Jawab :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(4, 222, 95, 23);

        PekerjaanPj.setName("PekerjaanPj"); // NOI18N
        PekerjaanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanPjKeyPressed(evt);
            }
        });
        FormInput.add(PekerjaanPj);
        PekerjaanPj.setBounds(102, 252, 290, 23);

        jLabel26.setText("Pekerjaan P.J. :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(4, 252, 95, 23);

        jLabel27.setText("Alamat P.J. :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(402, 222, 90, 23);

        AlamatPj.setText("ALAMAT");
        AlamatPj.setHighlighter(null);
        AlamatPj.setName("AlamatPj"); // NOI18N
        AlamatPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatPjMouseMoved(evt);
            }
        });
        AlamatPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatPjMouseExited(evt);
            }
        });
        AlamatPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPjKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPj);
        AlamatPj.setBounds(496, 222, 213, 23);

        KecamatanPj.setText("KECAMATAN");
        KecamatanPj.setHighlighter(null);
        KecamatanPj.setName("KecamatanPj"); // NOI18N
        KecamatanPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanPjMouseMoved(evt);
            }
        });
        KecamatanPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanPjMouseExited(evt);
            }
        });
        KecamatanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanPjKeyPressed(evt);
            }
        });
        FormInput.add(KecamatanPj);
        KecamatanPj.setBounds(496, 252, 130, 23);

        BtnKecamatanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatanPj.setMnemonic('3');
        BtnKecamatanPj.setToolTipText("ALt+3");
        BtnKecamatanPj.setName("BtnKecamatanPj"); // NOI18N
        BtnKecamatanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanPjActionPerformed(evt);
            }
        });
        FormInput.add(BtnKecamatanPj);
        BtnKecamatanPj.setBounds(629, 252, 28, 23);

        KabupatenPj.setText("KABUPATEN");
        KabupatenPj.setHighlighter(null);
        KabupatenPj.setName("KabupatenPj"); // NOI18N
        KabupatenPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenPjMouseMoved(evt);
            }
        });
        KabupatenPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenPjMouseExited(evt);
            }
        });
        KabupatenPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenPjKeyPressed(evt);
            }
        });
        FormInput.add(KabupatenPj);
        KabupatenPj.setBounds(663, 252, 130, 23);

        BtnKabupatenPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupatenPj.setMnemonic('4');
        BtnKabupatenPj.setToolTipText("ALt+4");
        BtnKabupatenPj.setName("BtnKabupatenPj"); // NOI18N
        BtnKabupatenPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenPjActionPerformed(evt);
            }
        });
        FormInput.add(BtnKabupatenPj);
        BtnKabupatenPj.setBounds(796, 252, 28, 23);

        BtnKelurahanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahanPj.setMnemonic('2');
        BtnKelurahanPj.setToolTipText("ALt+2");
        BtnKelurahanPj.setName("BtnKelurahanPj"); // NOI18N
        BtnKelurahanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanPjActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahanPj);
        BtnKelurahanPj.setBounds(845, 222, 28, 23);

        KelurahanPj.setText("KELURAHAN");
        KelurahanPj.setHighlighter(null);
        KelurahanPj.setName("KelurahanPj"); // NOI18N
        KelurahanPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanPjMouseMoved(evt);
            }
        });
        KelurahanPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanPjMouseExited(evt);
            }
        });
        KelurahanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanPjKeyPressed(evt);
            }
        });
        FormInput.add(KelurahanPj);
        KelurahanPj.setBounds(712, 222, 130, 23);

        jLabel28.setText("No.Peserta :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(392, 72, 100, 23);

        TNoPeserta.setHighlighter(null);
        TNoPeserta.setName("TNoPeserta"); // NOI18N
        TNoPeserta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPesertaKeyPressed(evt);
            }
        });
        FormInput.add(TNoPeserta);
        TNoPeserta.setBounds(496, 72, 270, 23);

        ChkRM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(175, 180, 170)));
        ChkRM.setForeground(new java.awt.Color(153, 0, 51));
        ChkRM.setSelected(true);
        ChkRM.setBorderPainted(true);
        ChkRM.setBorderPaintedFlat(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRM);
        ChkRM.setBounds(266, 12, 23, 23);

        R6.setBorder(null);
        buttonGroup1.add(R6);
        R6.setText("Anak");
        R6.setIconTextGap(0);
        R6.setName("R6"); // NOI18N
        FormInput.add(R6);
        R6.setBounds(348, 192, 60, 23);

        BtnKelurahan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnKelurahan1.setMnemonic('2');
        BtnKelurahan1.setToolTipText("ALt+2");
        BtnKelurahan1.setComponentPopupMenu(jPopupMenu2);
        BtnKelurahan1.setName("BtnKelurahan1"); // NOI18N
        BtnKelurahan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahan1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnKelurahan1);
        BtnKelurahan1.setBounds(845, 132, 28, 23);

        TUmurTh.setName("TUmurTh"); // NOI18N
        TUmurTh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurThKeyPressed(evt);
            }
        });
        FormInput.add(TUmurTh);
        TUmurTh.setBounds(102, 132, 35, 23);

        jLabel31.setText("Th");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(131, 132, 20, 23);

        TUmurBl.setName("TUmurBl"); // NOI18N
        TUmurBl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurBlKeyPressed(evt);
            }
        });
        FormInput.add(TUmurBl);
        TUmurBl.setBounds(153, 132, 35, 23);

        jLabel29.setText("Bl");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(178, 132, 20, 23);

        TUmurHr.setName("TUmurHr"); // NOI18N
        TUmurHr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurHrKeyPressed(evt);
            }
        });
        FormInput.add(TUmurHr);
        TUmurHr.setBounds(200, 132, 35, 23);

        jLabel30.setText("Hr");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(229, 132, 20, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void tbPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPasienMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPasienMouseClicked

private void tbPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPasienKeyPressed
       if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                var.setform(asalform);
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbPasienKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNo.getText().trim().equals("")){
            Valid.textKosong(TNo,"No.Rekam Medis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama pasien");
        }else if(nmpnj.getText().trim().equals("")||Kdpnj.getText().trim().equals("")){
            Valid.textKosong(Kdpnj,"Asuransi/Askes/Png.Jawab");
        }else if(no_ktp.equals("Yes")&&(TKtp.getText().trim().length()<p_no_ktp)){
            Valid.textKosong(TKtp,"No.KTP/SIM minimal "+p_no_ktp+" karakter dan ");            
        }else if(tmp_lahir.equals("Yes")&&(TTmp.getText().trim().length()<p_tmp_lahir)){
            Valid.textKosong(TTmp,"Tempat Lahir minimal "+p_tmp_lahir+" karakter dan ");            
        }else if(nm_ibu.equals("Yes")&&(NmIbu.getText().trim().length()<p_nm_ibu)){
            Valid.textKosong(NmIbu,"Nama Ibu minimal "+p_nm_ibu+" karakter dan ");            
        }else if(alamat.equals("Yes")&&(Alamat.getText().trim().length()<p_alamat)){
            Valid.textKosong(Alamat,"Alamat Pasien minimal "+p_alamat+" karakter dan ");            
        }else if(pekerjaan.equals("Yes")&&(Pekerjaan.getText().trim().length()<p_pekerjaan)){
            Valid.textKosong(Pekerjaan,"Pekerjaan Pasien minimal "+p_pekerjaan+" karakter dan ");            
        }else if(no_tlp.equals("Yes")&&(TTlp.getText().trim().length()<p_no_tlp)){
            Valid.textKosong(TTlp,"Telp Pasien minimal "+p_no_tlp+" karakter dan ");            
        }else if(umur.equals("Yes")&&(TUmurTh.getText().trim().length()<p_umur)){
            Valid.textKosong(TUmurTh,"Umur Pasien minimal "+p_umur+" karakter dan ");            
        }else if(namakeluarga.equals("Yes")&&(Saudara.getText().trim().length()<p_namakeluarga)){
            Valid.textKosong(Saudara,"Penanggung Jawab Pasien minimal "+p_namakeluarga+" karakter dan ");            
        }else if(no_peserta.equals("Yes")&&(TNoPeserta.getText().trim().length()<p_no_peserta)){
            Valid.textKosong(TNoPeserta,"No.Peserta Pasien minimal "+p_no_peserta+" karakter dan ");            
        }else if(kelurahan.equals("Yes")&&(Kelurahan.getText().trim().length()<p_kelurahan)){
            Valid.textKosong(Kelurahan,"Kelurahan minimal "+p_kelurahan+" karakter dan ");            
        }else if(kecamatan.equals("Yes")&&(Kecamatan.getText().trim().length()<p_kecamatan)){
            Valid.textKosong(Kecamatan,"Kecamatan minimal "+p_kecamatan+" karakter dan ");            
        }else if(kabupaten.equals("Yes")&&(Kabupaten.getText().trim().length()<p_kabupaten)){
            Valid.textKosong(Kabupaten,"Kabupaten minimal "+p_kabupaten+" karakter dan ");            
        }else if(pekerjaanpj.equals("Yes")&&(PekerjaanPj.getText().trim().length()<p_pekerjaanpj)){
            Valid.textKosong(PekerjaanPj,"Pekerjaan P.J. minimal "+p_pekerjaanpj+" karakter dan ");            
        }else if(alamatpj.equals("Yes")&&(AlamatPj.getText().trim().length()<p_alamatpj)){
            Valid.textKosong(AlamatPj,"Alamat P.J. minimal "+p_alamatpj+" karakter dan ");            
        }else if(kelurahanpj.equals("Yes")&&(KelurahanPj.getText().trim().length()<p_kelurahanpj)){
            Valid.textKosong(KelurahanPj,"Kelurahan P.J. minimal "+p_kelurahanpj+" karakter dan ");            
        }else if(kecamatanpj.equals("Yes")&&(KecamatanPj.getText().trim().length()<p_kecamatanpj)){
            Valid.textKosong(KecamatanPj,"Kecamatan P.J. minimal "+p_kecamatanpj+" karakter dan ");            
        }else if(kabupatenpj.equals("Yes")&&(KabupatenPj.getText().trim().length()<p_kabupatenpj)){
            Valid.textKosong(KabupatenPj,"Kabupaten P.J. minimal "+p_kabupatenpj+" karakter dan ");            
        }else{
            if(R1.isSelected()==true){
                klg="AYAH";
            }else if(R2.isSelected()==true){
                klg="IBU";
            }else if(R3.isSelected()==true){
                klg="ISTRI";
            }else if(R4.isSelected()==true){
                klg="SUAMI";
            }else if(R5.isSelected()==true){
                klg="SAUDARA";
            }else if(R6.isSelected()==true){
                klg="ANAK";
            }    
            
            Sequel.AutoComitFalse();                
            Sequel.queryu3("insert into kelurahan values(?,?)",2,new String[]{"0",Kelurahan.getText().replaceAll("KELURAHAN","-")});
            Sequel.queryu3("insert into kecamatan values(?,?)",2,new String[]{"0",Kecamatan.getText().replaceAll("KECAMATAN","-")});
            Sequel.queryu3("insert into kabupaten values(?,?)",2,new String[]{"0",Kabupaten.getText().replaceAll("KABUPATEN","-")});
            
            if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                    TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                    Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                    PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                })==true){
                if(var.getform().equals("DlgReg")){
                    TCari.setText(TNo.getText());
                }
                tampil();  
                if(ChkRM.isSelected()==true){
                    Sequel.queryu2("delete from set_no_rkm_medis");
                    Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                }                
                emptTeks(); 
            }else{
                autoNomor();
                if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                        TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                        Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                        Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                        DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                        TTlp.getText(),TUmurTh.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                        Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                        Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                        Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                        PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                    })==true){
                    if(var.getform().equals("DlgReg")){
                        TCari.setText(TNo.getText());
                    }
                    tampil();
                    if(ChkRM.isSelected()==true){
                        Sequel.queryu2("delete from set_no_rkm_medis");
                        Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                    }                
                    emptTeks(); 
                }else{
                    autoNomor();
                    if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                            TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                            Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                            Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                            DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                            TTlp.getText(),TUmurTh.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                            Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                            Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                            Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                            PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                        })==true){
                        if(var.getform().equals("DlgReg")){
                            TCari.setText(TNo.getText());
                        }
                        tampil();
                        if(ChkRM.isSelected()==true){
                            Sequel.queryu2("delete from set_no_rkm_medis");
                            Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                        }                
                        emptTeks(); 
                    }else{
                        autoNomor();
                        if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                                TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                                Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                                Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                                DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                                TTlp.getText(),TUmurTh.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                                Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                                Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                                Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                                PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                            })==true){
                            if(var.getform().equals("DlgReg")){
                                TCari.setText(TNo.getText());
                            }
                            tampil();
                            if(ChkRM.isSelected()==true){
                                Sequel.queryu2("delete from set_no_rkm_medis");
                                Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                            }                
                            emptTeks(); 
                        }else{
                            autoNomor();
                            if(Sequel.menyimpantf("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                                    TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                                    Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                                    TTlp.getText(),TUmurTh.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                                    PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                                })==true){
                                if(var.getform().equals("DlgReg")){
                                    TCari.setText(TNo.getText());
                                }
                                tampil();
                                if(ChkRM.isSelected()==true){
                                    Sequel.queryu2("delete from set_no_rkm_medis");
                                    Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                                }                
                                emptTeks(); 
                            }else{
                                TNm.requestFocus();
                                autoNomor();
                            }
                        }
                    }
                }
            }
            Sequel.AutoComitTrue();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KabupatenPj.getText().equals("KABUPATEN")){
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnBatal.requestFocus();
        }
        
}//GEN-LAST:event_BtnSimpanKeyPressed

private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
   for(z=0;z<tbPasien.getRowCount();z++){ 
            if(tbPasien.getValueAt(z,0).toString().equals("true")){
                Sequel.meghapus("pasien","no_rkm_medis",tbPasien.getValueAt(z,1).toString());
            }
   } 
   tampil();
   emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNo.getText().trim().equals("")){
            Valid.textKosong(TNo,"No.Rekam Medis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama pasien");
        }else if(nmpnj.getText().trim().equals("")||Kdpnj.getText().trim().equals("")){
            Valid.textKosong(Kdpnj,"Asuransi/Askes/Png.Jawab");
        }else if(no_ktp.equals("Yes")&&(TKtp.getText().trim().length()<p_no_ktp)){
            Valid.textKosong(TKtp,"No.KTP/SIM minimal "+p_no_ktp+" karakter dan ");            
        }else if(tmp_lahir.equals("Yes")&&(TTmp.getText().trim().length()<p_tmp_lahir)){
            Valid.textKosong(TTmp,"Tempat Lahir minimal "+p_tmp_lahir+" karakter dan ");            
        }else if(nm_ibu.equals("Yes")&&(NmIbu.getText().trim().length()<p_nm_ibu)){
            Valid.textKosong(NmIbu,"Nama Ibu minimal "+p_nm_ibu+" karakter dan ");            
        }else if(alamat.equals("Yes")&&(Alamat.getText().trim().length()<p_alamat)){
            Valid.textKosong(Alamat,"Alamat Pasien minimal "+p_alamat+" karakter dan ");            
        }else if(pekerjaan.equals("Yes")&&(Pekerjaan.getText().trim().length()<p_pekerjaan)){
            Valid.textKosong(Pekerjaan,"Pekerjaan Pasien minimal "+p_pekerjaan+" karakter dan ");            
        }else if(no_tlp.equals("Yes")&&(TTlp.getText().trim().length()<p_no_tlp)){
            Valid.textKosong(TTlp,"Telp Pasien minimal "+p_no_tlp+" karakter dan ");            
        }else if(umur.equals("Yes")&&(TUmurTh.getText().trim().length()<p_umur)){
            Valid.textKosong(TUmurTh,"Umur Pasien minimal "+p_umur+" karakter dan ");            
        }else if(namakeluarga.equals("Yes")&&(Saudara.getText().trim().length()<p_namakeluarga)){
            Valid.textKosong(Saudara,"Penanggung Jawab Pasien minimal "+p_namakeluarga+" karakter dan ");            
        }else if(no_peserta.equals("Yes")&&(TNoPeserta.getText().trim().length()<p_no_peserta)){
            Valid.textKosong(TNoPeserta,"No.Peserta Pasien minimal "+p_no_peserta+" karakter dan ");            
        }else if(kelurahan.equals("Yes")&&(Kelurahan.getText().trim().length()<p_kelurahan)){
            Valid.textKosong(Kelurahan,"Kelurahan minimal "+p_kelurahan+" karakter dan ");            
        }else if(kecamatan.equals("Yes")&&(Kecamatan.getText().trim().length()<p_kecamatan)){
            Valid.textKosong(Kecamatan,"Kecamatan minimal "+p_kecamatan+" karakter dan ");            
        }else if(kabupaten.equals("Yes")&&(Kabupaten.getText().trim().length()<p_kabupaten)){
            Valid.textKosong(Kabupaten,"Kabupaten minimal "+p_kabupaten+" karakter dan ");            
        }else if(pekerjaanpj.equals("Yes")&&(PekerjaanPj.getText().trim().length()<p_pekerjaanpj)){
            Valid.textKosong(PekerjaanPj,"Pekerjaan P.J. minimal "+p_pekerjaanpj+" karakter dan ");            
        }else if(alamatpj.equals("Yes")&&(AlamatPj.getText().trim().length()<p_alamatpj)){
            Valid.textKosong(AlamatPj,"Alamat P.J. minimal "+p_alamatpj+" karakter dan ");            
        }else if(kelurahanpj.equals("Yes")&&(KelurahanPj.getText().trim().length()<p_kelurahanpj)){
            Valid.textKosong(KelurahanPj,"Kelurahan P.J. minimal "+p_kelurahanpj+" karakter dan ");            
        }else if(kecamatanpj.equals("Yes")&&(KecamatanPj.getText().trim().length()<p_kecamatanpj)){
            Valid.textKosong(KecamatanPj,"Kecamatan P.J. minimal "+p_kecamatanpj+" karakter dan ");            
        }else if(kabupatenpj.equals("Yes")&&(KabupatenPj.getText().trim().length()<p_kabupatenpj)){
            Valid.textKosong(KabupatenPj,"Kabupaten P.J. minimal "+p_kabupatenpj+" karakter dan ");            
        }else{
            if(R1.isSelected()==true){
                klg="AYAH";
            }else if(R2.isSelected()==true){
                klg="IBU";
            }else if(R3.isSelected()==true){
                klg="ISTRI";
            }else if(R4.isSelected()==true){
                klg="SUAMI";
            }else if(R5.isSelected()==true){
                klg="SAUDARA";
            }else if(R6.isSelected()==true){
                klg="ANAK";
            }  
            
            Sequel.AutoComitFalse();
            Sequel.queryu2("insert into kelurahan values(?,?)",2,new String[]{"0",Kelurahan.getText().replaceAll("KELURAHAN","-")});
            Sequel.queryu2("insert into kecamatan values(?,?)",2,new String[]{"0",Kecamatan.getText().replaceAll("KECAMATAN","-")});
            Sequel.queryu2("insert into kabupaten values(?,?)",2,new String[]{"0",Kabupaten.getText().replaceAll("KABUPATEN","-")});
            Valid.editTable(tabMode,"pasien","no_rkm_medis","?","no_rkm_medis=?,nm_pasien=?,no_ktp=?,jk=?,tmp_lahir=?,"+
                        "tgl_lahir=?,alamat=?,gol_darah=?,pekerjaan=?,stts_nikah=?,agama=?,tgl_daftar=?,no_tlp=?,umur=?"+
                        ",pnd=?,keluarga=?,namakeluarga=?,kd_pj=?,no_peserta=?,kd_kel=?,kd_kec=?,kd_kab=?,nm_ibu=?,pekerjaanpj=?,"+
                        "alamatpj=?,kelurahanpj=?,kecamatanpj=?,kabupatenpj=?",29,
                        new String[]{TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                            Valid.SetTgl(DTPLahir.getSelectedItem()+""),
                            Alamat.getText(),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                            DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                            TTlp.getText(),TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                            Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText()),
                            Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText()),
                            Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText()), 
                            NmIbu.getText(),PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),
                            KabupatenPj.getText(),Kd2.getText()});
            Sequel.AutoComitTrue();
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
                       
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            
            if(!cmbHlm.getSelectedItem().toString().equals("Semua")){
                Valid.MyReport("rptPasien.jrxml","report","::[ Data Pasien Umum ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                        "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                        "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                        "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten "+
                        "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_ktp like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_peserta like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tmp_lahir like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_ibu like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.gol_darah like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.pekerjaan like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.agama like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_daftar like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_tlp like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc"+" LIMIT "+cmbHlm.getSelectedItem(),param);
            }else{
                Valid.MyReport("rptPasien.jrxml","report","::[ Data Pasien Umum ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                        "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                        "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                        "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten "+
                        "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_ktp like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_peserta like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tmp_lahir like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_ibu like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.gol_darah like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.pekerjaan like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.agama like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_daftar like '%"+TCari.getText().trim()+"%' "+
                        "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_tlp like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc ",param);
            }
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

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        Carialamat.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
    DlgDemografi.dispose();
    dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbPasien.requestFocus();
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

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

private void TTmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpKeyPressed
   Valid.pindah(evt,CMbGd,DTPLahir);
}//GEN-LAST:event_TTmpKeyPressed

private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
   Valid.pindah(evt,TNm,CMbGd);
}//GEN-LAST:event_CmbJkKeyPressed

private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCari.setText(TNm.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(TNm.getText().toLowerCase().contains("tn.")){
                CmbJk.setSelectedItem("LAKI-LAKI");
            }else if(TNm.getText().toLowerCase().contains("sdr.")){
                CmbJk.setSelectedItem("LAKI-LAKI");
            }else if(TNm.getText().toLowerCase().contains("ny.")){
                CmbJk.setSelectedItem("PEREMPUAN");
            }else if(TNm.getText().toLowerCase().contains("nn.")){
                CmbJk.setSelectedItem("PEREMPUAN");
            }
            CmbJk.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            TNo.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){   
            TCari.requestFocus();  
        }
}//GEN-LAST:event_TNmKeyPressed

private void CMbGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbGdKeyPressed
   Valid.pindah(evt,CmbJk,TTmp);
}//GEN-LAST:event_CMbGdKeyPressed

private void cmbAgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAgamaKeyPressed
    Valid.pindah(evt,PekerjaanPj,CmbStts);
}//GEN-LAST:event_cmbAgamaKeyPressed

private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed
   Valid.pindah(evt,cmbAgama,Kdpnj);
}//GEN-LAST:event_CmbSttsKeyPressed

private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
   Valid.pindah(evt,TTlp,TKtp);
}//GEN-LAST:event_PekerjaanKeyPressed

private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Alamat.getText().equals("")){
           Alamat.setText("ALAMAT");
       }
       if(Kelurahan.getText().equals("KELURAHAN")){
           Kelurahan.setText("");
       }
       Kelurahan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Alamat.getText().equals("")){
           Alamat.setText("ALAMAT");
       }
       TKtp.requestFocus();
   }
}//GEN-LAST:event_AlamatKeyPressed

private void TTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTlpKeyPressed
   Valid.pindah(evt,TNoPeserta,Pekerjaan);
}//GEN-LAST:event_TTlpKeyPressed

private void TNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TCari.setText(TNo.getText());
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TNm.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){   
            KabupatenPj.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){   
            TCari.requestFocus();  
        }
}//GEN-LAST:event_TNoKeyPressed

private void TKtpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtpKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Pekerjaan.requestFocus();
        }
}//GEN-LAST:event_TKtpKeyPressed

private void DTPDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPDaftarKeyPressed
   Valid.pindah2(evt,Pekerjaan,BtnSimpan);
}//GEN-LAST:event_DTPDaftarKeyPressed

private void CMbPndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbPndKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       NmIbu.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       TUmurTh.requestFocus();
   }
}//GEN-LAST:event_CMbPndKeyPressed

private void SaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaudaraKeyPressed
   Valid.pindah(evt,NmIbu,PekerjaanPj);
}//GEN-LAST:event_SaudaraKeyPressed

private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptKartuPasien.jrxml","report","::[ Kartu Periksa Pasien(Umum) ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab  from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_ktp like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.jk like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tmp_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_lahir like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.alamat like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.gol_darah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.pekerjaan like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.stts_nikah like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.agama like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.tgl_daftar like '%"+TCari.getText().trim()+"%' "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%"+Carialamat.getText().trim()+"%'  and pasien.no_tlp like '%"+TCari.getText().trim()+"%'  order by pasien.no_rkm_medis desc",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnBarcodeActionPerformed

private void MnKartuStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuStatusActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptKartuPasien.jrxml","report","::[ Kartu Periksa Pasien(Umum) ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnKartuStatusActionPerformed

private void DTPLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahirItemStateChanged
    lahir = DTPLahir.getDate();    
    birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    p = Period.between(birthday,today);
    p2 =ChronoUnit.DAYS.between(birthday,today);
    TUmurTh.setText(String.valueOf(p.getYears()));
    TUmurBl.setText(String.valueOf(p.getMonths()));
    TUmurHr.setText(String.valueOf(p.getDays()));
}//GEN-LAST:event_DTPLahirItemStateChanged

private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
           Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,CmbStts,TNoPeserta);
        }
}//GEN-LAST:event_KdpnjKeyPressed

private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        var.setform("DlgPasien");
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
}//GEN-LAST:event_BtnPenjabActionPerformed

private void MnKartu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu2ActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptKartuBerobat.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnKartu2ActionPerformed

private void ppGrafikjkbayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikjkbayiActionPerformed
       grafikjkel kas=new grafikjkel("Grafik Jenis Kelamin Pasien "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikjkbayiActionPerformed

private void MnLaporanRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRMActionPerformed
      if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRM2.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
        }
}//GEN-LAST:event_MnLaporanRMActionPerformed

private void MnLaporanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanIGDActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptKartuIgd.jrxml","report","::[ Laporan Rekam Medik ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLaporanIGDActionPerformed

private void ppKelahiranBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppKelahiranBayiActionPerformed
    if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgIKBBayi resume=new DlgIKBBayi(null,false);
            resume.setNoRM(TNo.getText(),TNm.getText(),Saudara.getText(),Alamat.getText(),CmbJk.getSelectedItem().toString(),
                    TUmurTh.getText(),DTPLahir.getDate(),DTPDaftar.getDate());
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_ppKelahiranBayiActionPerformed

private void MnLembarKeluarMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarKeluarMasukActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptLembarKeluarMasuk.jrxml","report","::[ Ringkasan Masuk Keluar ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnLembarKeluarMasukActionPerformed

private void MnLembarAnamNesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarAnamNesaActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarAnamnesi.jrxml","report","::[ Lembar Anamnesa ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarAnamNesaActionPerformed

private void MnLembarGrafikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarGrafikActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarGrafik.jrxml","report","::[ Lembar Grafik ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarGrafikActionPerformed

private void MnLembarCatatanPerkembanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCatatanPerkembanganActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarPerkembangan.jrxml","report","::[ Lembar Catatan Perkembangan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarCatatanPerkembanganActionPerformed

private void MnLembarCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCatatanKeperawatanActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLembarPerkembangan.jrxml","report","::[ Lembar Catatan Keperawatan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLembarCatatanKeperawatanActionPerformed

private void MnLaporanAnestesiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanAnestesiaActionPerformed
     if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Valid.MyReport("rptLaporanAnestesia.jrxml","report","::[ Lembar Catatan Keperawatan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
        }
}//GEN-LAST:event_MnLaporanAnestesiaActionPerformed

private void CarialamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CarialamatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){          
            BtnCariActionPerformed(null);
            TNo.requestFocus();
        }
}//GEN-LAST:event_CarialamatKeyPressed

private void MnKartu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu3ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptKartuBerobat2.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnKartu3ActionPerformed

private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kelurahan.getText().equals("")){
           Kelurahan.setText("KELURAHAN");
       }
       if(Kecamatan.getText().equals("KECAMATAN")){
           Kecamatan.setText("");
       }
       Kecamatan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Kelurahan.getText().equals("")){
           Kelurahan.setText("KELURAHAN");
       }
       if(Alamat.getText().equals("ALAMAT")){
          Alamat.setText("");
       }     
       Alamat.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_UP){
       BtnKelurahanActionPerformed(null);
   }
}//GEN-LAST:event_KelurahanKeyPressed

private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kecamatan.getText().equals("")){
           Kecamatan.setText("KECAMATAN");
       }
       if(Kabupaten.getText().equals("KABUPATEN")){
           Kabupaten.setText("");
       }
       Kabupaten.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Kecamatan.getText().equals("")){
           Kecamatan.setText("KECAMATAN");
       }
       if(Kelurahan.getText().equals("KELURAHAN")){
          Kelurahan.setText("");
       }     
       Kelurahan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_UP){
       BtnKecamatanActionPerformed(null);
   }
}//GEN-LAST:event_KecamatanKeyPressed

private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
       if(Kabupaten.getText().equals("")){
           Kabupaten.setText("KABUPATEN");
       }
       if(AlamatPj.getText().equals("ALAMAT")){
           AlamatPj.setText("");
       }
       AlamatPj.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
       if(Kabupaten.getText().equals("")){
           Kabupaten.setText("KABUPATEN");
       }
       if(Kecamatan.getText().equals("KECAMATAN")){
          Kecamatan.setText("");
       }     
       Kecamatan.requestFocus();
   }else if(evt.getKeyCode()==KeyEvent.VK_UP){
       BtnKabupatenActionPerformed(null);
   }
}//GEN-LAST:event_KabupatenKeyPressed

private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
       var.setform("DlgPasien");
       pilih=1;
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
}//GEN-LAST:event_BtnKelurahanActionPerformed

private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        var.setform("DlgPasien");
        pilih=1;
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
}//GEN-LAST:event_BtnKecamatanActionPerformed

private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        var.setform("DlgPasien");
        pilih=1;
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
}//GEN-LAST:event_BtnKabupatenActionPerformed

private void ppGrafikDemografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikDemografiActionPerformed
        DlgDemografi.setSize(550,180);
        DlgDemografi.setLocationRelativeTo(internalFrame1);
        DlgDemografi.setVisible(true);
}//GEN-LAST:event_ppGrafikDemografiActionPerformed

private void ppGrafikPerAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerAgamaActionPerformed
       grafikpasienperagama kas=new grafikpasienperagama("Grafik Pasien Per Agama "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerAgamaActionPerformed

private void ppGrafikPerPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerPekerjaanActionPerformed
       grafikpasienperpekerjaaan kas=new grafikpasienperpekerjaaan("Grafik Pasien Per Pekerjaan "," ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerPekerjaanActionPerformed

private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                        " pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        " where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and kelurahan.nm_kel='"+Kelurahan2.getText()+"'", 
                        "pasien.alamat","Area");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                        " pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                         "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"'", 
                         "kelurahan.nm_kel","Kelurahan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                         " pasien inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"'", 
                         "kecamatan.nm_kec","Kecamatan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kabupaten ]::",
                         " pasien inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab", 
                          "kabupaten.nm_kab","Kabupaten");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            } 
}//GEN-LAST:event_BtnPrint2ActionPerformed

private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
   DlgDemografi.dispose();
}//GEN-LAST:event_BtnKeluar2ActionPerformed

private void BtnSeek8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek8ActionPerformed
   if(Kecamatan2.getText().equals("")){
       Valid.textKosong(Kecamatan2,"Kecamatan");
   }else{
       var.setform("DlgPasien");
       pilih=2;
        kel.emptTeks();
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek8ActionPerformed

private void BtnSeek9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek9ActionPerformed
   if(Kabupaten2.getText().equals("")){
       Valid.textKosong(Kabupaten2,"Kabupaten");
   }else{
       var.setform("DlgPasien");
       pilih=2;
        kec.emptTeks();
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek9ActionPerformed

private void BtnSeek10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek10ActionPerformed
        var.setform("DlgPasien");
        pilih=2;
        kab.emptTeks();
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);   
}//GEN-LAST:event_BtnSeek10ActionPerformed

private void BtnPrint3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Area");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select  pasien.alamat as area,count(pasien.alamat) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' "+
                   "and kelurahan.nm_kel='"+Kelurahan2.getText()+"' group by pasien.alamat order by pasien.alamat",data);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Kelurahan");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kelurahan.nm_kel as area,count(kelurahan.nm_kel) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' group by kelurahan.nm_kel order by kelurahan.nm_kel",data);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kecamatan Kabupaten "+Kabupaten2.getText());
                data.put("area","Kecamatan");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kecamatan.nm_kec as area,count(kecamatan.nm_kec) as jumlah from pasien "+
                   "inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"' group by kecamatan.nm_kec order by kecamatan.nm_kec",data);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kabupaten");
                data.put("area","Kabupaten");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kabupaten ]::","select kabupaten.nm_kab as area,count(kabupaten.nm_kab) as jumlah from pasien "+
                   "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab group by kabupaten.nm_kab order by kabupaten.nm_kab",data);
            }                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrint3ActionPerformed

private void ppRegistrasiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRegistrasiBtnPrintActionPerformed
     prosesCari2();
}//GEN-LAST:event_ppRegistrasiBtnPrintActionPerformed

private void AlamatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseExited
        if(Alamat.getText().equals("")){
            Alamat.setText("ALAMAT");
        }
}//GEN-LAST:event_AlamatMouseExited

private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if(Kelurahan.getText().equals("")){
            Kelurahan.setText("KELURAHAN");
        }
}//GEN-LAST:event_KelurahanMouseExited

private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if(Kecamatan.getText().equals("")){
            Kecamatan.setText("KECAMATAN");
        }
}//GEN-LAST:event_KecamatanMouseExited

private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
       if(Kabupaten.getText().equals("")){
            Kabupaten.setText("KABUPATEN");
        }
}//GEN-LAST:event_KabupatenMouseExited

private void AlamatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseMoved
        if(Alamat.getText().equals("ALAMAT")){
            Alamat.setText("");
        }
}//GEN-LAST:event_AlamatMouseMoved

private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if(Kelurahan.getText().equals("KELURAHAN")){
            Kelurahan.setText("");
        }
}//GEN-LAST:event_KelurahanMouseMoved

private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if(Kecamatan.getText().equals("KECAMATAN")){
            Kecamatan.setText("");
        }
}//GEN-LAST:event_KecamatanMouseMoved

private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if(Kabupaten.getText().equals("KABUPATEN")){
            Kabupaten.setText("");
        }
}//GEN-LAST:event_KabupatenMouseMoved

    private void ChkDaftarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkDaftarItemStateChanged
        if(ChkDaftar.isSelected()==true){
            DTPDaftar.setEditable(true);
        }else if(ChkDaftar.isSelected()==false){
            DTPDaftar.setEditable(false);            
        }
        DTPDaftar.requestFocus();
    }//GEN-LAST:event_ChkDaftarItemStateChanged

    private void MnIdentitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnIdentitasActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM1.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnIdentitasActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void NmIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmIbuKeyPressed
        Valid.pindah(evt,CMbPnd,Saudara);
    }//GEN-LAST:event_NmIbuKeyPressed

    private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRmKeyPressed

    private void MnPengantarHemodalisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPengantarHemodalisaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            String nip=Sequel.cariIsi("select kd_dokterhemodialisa from set_pjlab");
            param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+nip+"'"));   
            param.put("nipdokter",nip);   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRM3.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
        }
    }//GEN-LAST:event_MnPengantarHemodalisaActionPerformed

    private void PekerjaanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanPjKeyPressed
        Valid.pindah(evt,Saudara,cmbAgama);
    }//GEN-LAST:event_PekerjaanPjKeyPressed

    private void AlamatPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatPjMouseMoved
        if(AlamatPj.getText().equals("ALAMAT")){
            AlamatPj.setText("");
        }
    }//GEN-LAST:event_AlamatPjMouseMoved

    private void AlamatPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatPjMouseExited
        if(AlamatPj.getText().equals("")){
            AlamatPj.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatPjMouseExited

    private void AlamatPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(AlamatPj.getText().equals("")){
                AlamatPj.setText("ALAMAT");
            }
            if(KelurahanPj.getText().equals("KELURAHAN")){
                KelurahanPj.setText("");
            }
            KelurahanPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(AlamatPj.getText().equals("")){
                AlamatPj.setText("ALAMAT");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }
    }//GEN-LAST:event_AlamatPjKeyPressed

    private void KecamatanPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanPjMouseMoved
        if(KecamatanPj.getText().equals("KECAMATAN")){
            KecamatanPj.setText("");
        }
    }//GEN-LAST:event_KecamatanPjMouseMoved

    private void KecamatanPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanPjMouseExited
        if(KecamatanPj.getText().equals("")){
            KecamatanPj.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanPjMouseExited

    private void KecamatanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanPjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(KecamatanPj.getText().equals("")){
                KecamatanPj.setText("KECAMATAN");
            }
            if(KabupatenPj.getText().equals("KABUPATEN")){
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KecamatanPj.getText().equals("")){
                KecamatanPj.setText("KECAMATAN");
            }
            if(KelurahanPj.getText().equals("KELURAHAN")){
               KelurahanPj.setText("");
            }     
            KelurahanPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKecamatanPjActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanPjKeyPressed

    private void BtnKecamatanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanPjActionPerformed
        var.setform("DlgPasien");
        pilih=3;
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKecamatanPjActionPerformed

    private void KabupatenPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenPjMouseMoved
        if(KabupatenPj.getText().equals("KABUPATEN")){
            KabupatenPj.setText("");
        }
    }//GEN-LAST:event_KabupatenPjMouseMoved

    private void KabupatenPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenPjMouseExited
        if(KabupatenPj.getText().equals("")){
            KabupatenPj.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenPjMouseExited

    private void KabupatenPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenPjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(KabupatenPj.getText().equals("")){
                KabupatenPj.setText("KABUPATEN");
            }
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KabupatenPj.getText().equals("")){
                KabupatenPj.setText("KABUPATEN");
            }
            if(KecamatanPj.getText().equals("KECAMATAN")){
               KecamatanPj.setText("");
            }     
            KecamatanPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKabupatenPjActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenPjKeyPressed

    private void BtnKabupatenPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenPjActionPerformed
        var.setform("DlgPasien");
        pilih=3;
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenPjActionPerformed

    private void BtnKelurahanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanPjActionPerformed
        var.setform("DlgPasien");
        pilih=3;
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanPjActionPerformed

    private void KelurahanPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanPjMouseMoved
        if(KelurahanPj.getText().equals("KELURAHAN")){
            KelurahanPj.setText("");
        }
    }//GEN-LAST:event_KelurahanPjMouseMoved

    private void KelurahanPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanPjMouseExited
        if(KelurahanPj.getText().equals("")){
            KelurahanPj.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanPjMouseExited

    private void KelurahanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanPjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(KelurahanPj.getText().equals("")){
                KelurahanPj.setText("KELURAHAN");
            }
            if(KecamatanPj.getText().equals("KECAMATAN")){
                KecamatanPj.setText("");
            }
            KecamatanPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KelurahanPj.getText().equals("")){
                KelurahanPj.setText("KELURAHAN");
            }
            if(AlamatPj.getText().equals("ALAMAT")){
               AlamatPj.setText("");
            }     
            AlamatPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnKelurahanPjActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanPjKeyPressed

    private void TNoPesertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPesertaKeyPressed
        Valid.pindah(evt,Kdpnj,TTlp);
    }//GEN-LAST:event_TNoPesertaKeyPressed

    private void MnBarcodeRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRMActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRMActionPerformed

    private void MnBarcodeRM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM2.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM2ActionPerformed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            TNo.setEditable(false);
            TNo.setBackground(new Color(245,250,240));            
        }else if(ChkRM.isSelected()==false){
            TNo.setEditable(true);
            TNo.setBackground(new Color(250,255,245));
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void MnCekKepesertaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCekKepesertaanActionPerformed
        if(!TNoPeserta.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BPJSPeserta form=new BPJSPeserta(null, true);
            form.tampil(TNoPeserta.getText());
            form.setSize(640,internalFrame1.getHeight()-40);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Nomor kepesertaan kosong...!!!");
        }
            
    }//GEN-LAST:event_MnCekKepesertaanActionPerformed

    private void MnCekNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCekNIKActionPerformed
        if(!TKtp.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BPJSNik form=new BPJSNik(null, true);
            form.tampil(TKtp.getText());
            form.setSize(640,internalFrame1.getHeight()-40);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, NIK KTP kosong...!!!");
        }
    }//GEN-LAST:event_MnCekNIKActionPerformed

    private void MnBarcodeRM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM3.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM1ActionPerformed

    private void MnBarcodeRM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM3ActionPerformed
                if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM9.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM3ActionPerformed

    private void MnKartu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptKartuBerobat5.jrxml","report","::[ Kartu Periksa Pasien ]::","select * from pasien where pasien.no_rkm_medis='"+TNo.getText()+"' ");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnKartu1ActionPerformed

    private void MnBarcodeRM4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM4ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM11.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM4ActionPerformed

    private void MnBarcodeRM5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM5ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM12.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM5ActionPerformed

    private void MnBarcodeRM6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM6ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM13.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM6ActionPerformed

    private void MnViaBPJSNikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnViaBPJSNikActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cekViaBPJS.tampil(TKtp.getText());
        TNm.setText(cekViaBPJS.nama);
        CmbJk.setSelectedItem(cekViaBPJS.jk);
        TNoPeserta.setText(cekViaBPJS.nokartu);
        Pekerjaan.setText(cekViaBPJS.pekerjaan);
        TUmurTh.setText(cekViaBPJS.umur);
        Valid.SetTgl(DTPLahir,cekViaBPJS.tgl_lahir);
        jPopupMenu2.setVisible(false);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnViaBPJSNikActionPerformed

    private void MnViaBPJSNoKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnViaBPJSNoKartuActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cekViaBPJSKartu.tampil(TNoPeserta.getText());
        TNm.setText(cekViaBPJSKartu.nama);
        CmbJk.setSelectedItem(cekViaBPJSKartu.jk);
        TKtp.setText(cekViaBPJSKartu.nik);
        Pekerjaan.setText(cekViaBPJSKartu.pekerjaan);
        TUmurTh.setText(cekViaBPJSKartu.umur);
        Valid.SetTgl(DTPLahir,cekViaBPJSKartu.tgl_lahir);
        jPopupMenu2.setVisible(false);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnViaBPJSNoKartuActionPerformed

    private void BtnKelurahan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahan1ActionPerformed
        if(TKtp.getText().trim().equals("")&&TNoPeserta.getText().trim().equals("")){
            TKtp.requestFocus();
            JOptionPane.showMessageDialog(null,"Silahkan isi terlebih dahulu No.Peserta/NIK/No.KTP..!!");
        }else{
            jPopupMenu2.setLocation(712,222);
            jPopupMenu2.setVisible(true);
        }            
    }//GEN-LAST:event_BtnKelurahan1ActionPerformed

    private void MnLaporanRM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRM1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM4.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLaporanRM1ActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(TNo.getText(),TNm.getText());
            resume.tampil();
            resume.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnIdentitas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnIdentitas2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM5.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj,pasien.no_peserta from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnIdentitas2ActionPerformed

    private void MnLaporanRM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRM2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM6.jrxml","report","::[ Lembar Rawat Jalan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLaporanRM2ActionPerformed

    private void MnFormulirPendaftaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirPendaftaranActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM7.jrxml","report","::[ Formulir Pendaftaran ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnFormulirPendaftaranActionPerformed

    private void MnSCreeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSCreeningActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM8.jrxml","report","::[ Screening Awal ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSCreeningActionPerformed

    private void MnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyResepActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM9.jrxml","report","::[ Copy Resep ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCopyResepActionPerformed

    private void MnKartu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu4ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptKartuBerobat6.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnKartu4ActionPerformed

    private void MnLembarKeluarMasuk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarKeluarMasuk2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptLembarKeluarMasuk2.jrxml","report","::[ Ringkasan Masuk Keluar ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarKeluarMasuk2ActionPerformed

    private void MnIdentitas3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnIdentitas3ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptRM10.jrxml","report","::[ Identitas Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnIdentitas3ActionPerformed

    private void MnBarcodeRM7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM7ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
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
            Valid.MyReport("rptBarcodeRM15.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM7ActionPerformed

    private void TUmurThKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurThKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TUmurThKeyPressed

    private void TUmurBlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurBlKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TUmurBlKeyPressed

    private void TUmurHrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurHrKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //       CmbUmur.requestFocus();
            try {
                Valid.SetTgl(DTPLahir,Sequel.cariIsi("select DATE_SUB('"+Valid.SetTgl(DTPLahir.getSelectedItem()+"")+"', interval "+TUmurTh.getText()+" year)"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            DTPLahir.requestFocus();
        }
    }//GEN-LAST:event_TUmurHrKeyPressed

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
             TUmurTh.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
             TTmp.requestFocus();
        }
    }//GEN-LAST:event_DTPLahirKeyPressed

    private void MnKartu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu5ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs()); 
            Valid.MyReport("rptKartuBerobat7.jrxml","report","::[ Kartu Periksa Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,pasien.nm_ibu,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnKartu5ActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,true);
            catatan.setNoRm(TNo.getText());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCoverActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNo.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPasien.requestFocus();
        }else{
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCoverMap.jrxml","report","::[ Cover Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,pasien.no_peserta,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNo.getText()+"' ",param);
        }
    }//GEN-LAST:event_MnCoverActionPerformed

    private void ppGabungRMBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGabungRMBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNo.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien yang mau digabung data rekam medisnya...!!!");
            TCari.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien yang mau digabung data rekam medisnya...!!!");
            TCari.requestFocus();
        }else{
            NoRmTujuan.requestFocus();
            NoRmTujuan.setText("");
            NmPasienTujuan.setText("");
            WindowGabungRM.setSize(430,130);
            WindowGabungRM.setLocationRelativeTo(internalFrame1);
            WindowGabungRM.setAlwaysOnTop(false);
            WindowGabungRM.setVisible(true);
        }
    }//GEN-LAST:event_ppGabungRMBtnPrintActionPerformed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        WindowGabungRM.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        if(NoRmTujuan.getText().trim().equals("")){
            Valid.textKosong(NoRmTujuan,"No.R.M Tujuan masih kosong");
        }else if(NmPasienTujuan.getText().trim().equals("")){
            Valid.textKosong(NmPasienTujuan,"Nama Pasien Tujuan");
        }else if(NoRmTujuan.getText().trim().equals(TNo.getText().trim())){
            JOptionPane.showMessageDialog(rootPane,"No.R.M dan No.R.M. tujuan tidak boleh sama...!!");
        }else{
            if(Sequel.mengedittf("reg_periksa","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                NoRmTujuan.getText(),TNo.getText()
            })==true){
                Sequel.mengedit("bayar_piutang","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("catatan_pasien","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("pasien_bayi","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("pasien_mati","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("peminjaman_berkas","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("penjualan","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("piutang","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("piutang_pasien","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("retensi_pasien","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("returjual","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("returpiutang","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("rujukanranap_dokter_rs","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.mengedit("sidikjaripasien","no_rkm_medis=?","no_rkm_medis=?",2,new String[]{
                    NoRmTujuan.getText(),TNo.getText()
                });
                Sequel.meghapus("pasien","no_rkm_medis",TNo.getText());
                tampil();
                emptTeks();
                WindowGabungRM.dispose();
            }            
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void NoRmTujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmTujuanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!NoRmTujuan.getText().trim().equals("")){
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",NmPasienTujuan,NoRmTujuan.getText());
                if(NmPasienTujuan.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane,"Akun Rekening tidak ditemukan");
                }
                NoRmTujuan.requestFocus();
            }
        }
    }//GEN-LAST:event_NoRmTujuanKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?",NmPasienTujuan,NoRmTujuan.getText());
        if(NmPasienTujuan.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Data pasien tidak ditemukan..!!");
        }
        NoRmTujuan.requestFocus();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    /**
     * @data args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgPasien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            DlgPasien dialog = new DlgPasien(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.TextBox AlamatPj;
    private javax.swing.JMenu Barcode;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCloseIn6;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKabupatenPj;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKecamatanPj;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKelurahan;
    private widget.Button BtnKelurahan1;
    private widget.Button BtnKelurahanPj;
    private widget.Button BtnPenjab;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint3;
    private widget.Button BtnSeek10;
    private widget.Button BtnSeek8;
    private widget.Button BtnSeek9;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan6;
    private widget.ComboBox CMbGd;
    private widget.ComboBox CMbPnd;
    private widget.TextBox Carialamat;
    private widget.CekBox ChkDaftar;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkRM;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private javax.swing.JDialog DlgDemografi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kabupaten;
    private widget.TextBox Kabupaten2;
    private widget.TextBox KabupatenPj;
    private javax.swing.JMenu KartuPasien;
    private widget.TextBox Kd2;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox Kecamatan2;
    private widget.TextBox KecamatanPj;
    private widget.TextBox Kelurahan;
    private widget.TextBox Kelurahan2;
    private widget.TextBox KelurahanPj;
    private widget.Label LCount;
    private javax.swing.JMenu MenuBPJS;
    private javax.swing.JMenu MenuIdentitas;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcodeRM;
    private javax.swing.JMenuItem MnBarcodeRM1;
    private javax.swing.JMenuItem MnBarcodeRM2;
    private javax.swing.JMenuItem MnBarcodeRM3;
    private javax.swing.JMenuItem MnBarcodeRM4;
    private javax.swing.JMenuItem MnBarcodeRM5;
    private javax.swing.JMenuItem MnBarcodeRM6;
    private javax.swing.JMenuItem MnBarcodeRM7;
    private javax.swing.JMenuItem MnCekKepesertaan;
    private javax.swing.JMenuItem MnCekNIK;
    private javax.swing.JMenuItem MnCopyResep;
    private javax.swing.JMenuItem MnCover;
    private javax.swing.JMenuItem MnFormulirPendaftaran;
    private javax.swing.JMenuItem MnIdentitas;
    private javax.swing.JMenuItem MnIdentitas2;
    private javax.swing.JMenuItem MnIdentitas3;
    private javax.swing.JMenuItem MnKartu1;
    private javax.swing.JMenuItem MnKartu2;
    private javax.swing.JMenuItem MnKartu3;
    private javax.swing.JMenuItem MnKartu4;
    private javax.swing.JMenuItem MnKartu5;
    private javax.swing.JMenuItem MnKartuStatus;
    private javax.swing.JMenuItem MnLaporanAnestesia;
    private javax.swing.JMenuItem MnLaporanIGD;
    private javax.swing.JMenuItem MnLaporanRM;
    private javax.swing.JMenuItem MnLaporanRM1;
    private javax.swing.JMenuItem MnLaporanRM2;
    private javax.swing.JMenuItem MnLembarAnamNesa;
    private javax.swing.JMenuItem MnLembarCatatanKeperawatan;
    private javax.swing.JMenuItem MnLembarCatatanPerkembangan;
    private javax.swing.JMenuItem MnLembarGrafik;
    private javax.swing.JMenuItem MnLembarKeluarMasuk;
    private javax.swing.JMenuItem MnLembarKeluarMasuk2;
    private javax.swing.JMenuItem MnPengantarHemodalisa;
    private javax.swing.JMenuItem MnSCreening;
    private javax.swing.JMenuItem MnViaBPJSNik;
    private javax.swing.JMenuItem MnViaBPJSNoKartu;
    private widget.TextBox NmIbu;
    private widget.TextBox NmPasienTujuan;
    private widget.TextBox NoRm;
    private widget.TextBox NoRmTujuan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox PekerjaanPj;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.RadioButton R5;
    private widget.RadioButton R6;
    private widget.TextBox Saudara;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKtp;
    private widget.TextBox TNm;
    private widget.TextBox TNo;
    private widget.TextBox TNoPeserta;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.TextBox TUmurBl;
    private widget.TextBox TUmurHr;
    private widget.TextBox TUmurTh;
    private javax.swing.JDialog WindowGabungRM;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbAgama;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
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
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JSeparator jSeparator5;
    private widget.Label label40;
    private widget.Label label41;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppGabungRM;
    private javax.swing.JMenuItem ppGrafikDemografi;
    private javax.swing.JMenuItem ppGrafikPerAgama;
    private javax.swing.JMenuItem ppGrafikPerPekerjaan;
    private javax.swing.JMenuItem ppGrafikjkbayi;
    private javax.swing.JMenuItem ppKelahiranBayi;
    private javax.swing.JMenuItem ppRegistrasi;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Table tbPasien;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_peserta like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_ibu like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc LIMIT ? ");           
            ps2=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_peserta like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_ibu like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc");               
            try{
                if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                    ps2.setString(1,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(2,"%"+TCari.getText().trim()+"%");
                    ps2.setString(3,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(4, "%"+TCari.getText().trim()+"%");
                    ps2.setString(5,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(6, "%"+TCari.getText().trim()+"%");
                    ps2.setString(7,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(8, "%"+TCari.getText().trim()+"%");
                    ps2.setString(9,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(10, "%"+TCari.getText().trim()+"%");
                    ps2.setString(11,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(12, "%"+TCari.getText().trim()+"%");
                    ps2.setString(13,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(14, "%"+TCari.getText().trim()+"%");
                    ps2.setString(15,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(16, "%"+TCari.getText().trim()+"%");
                    ps2.setString(17,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(18, "%"+TCari.getText().trim()+"%");
                    ps2.setString(19,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(20, "%"+TCari.getText().trim()+"%");
                    ps2.setString(21,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(22, "%"+TCari.getText().trim()+"%");
                    ps2.setString(23,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(24, "%"+TCari.getText().trim()+"%");
                    ps2.setString(25,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(26, "%"+TCari.getText().trim()+"%");
                    ps2.setString(27,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(28, "%"+TCari.getText().trim()+"%");
                    ps2.setString(29,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(30, "%"+TCari.getText().trim()+"%");
                    rs=ps2.executeQuery();
                }else{
                    ps.setString(1,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(4, "%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(6, "%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(8, "%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(10, "%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(12, "%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(14, "%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(16, "%"+TCari.getText().trim()+"%");
                    ps.setString(17,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(18, "%"+TCari.getText().trim()+"%");
                    ps.setString(19,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(20, "%"+TCari.getText().trim()+"%");
                    ps.setString(21,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(22, "%"+TCari.getText().trim()+"%");
                    ps.setString(23,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(24, "%"+TCari.getText().trim()+"%");
                    ps.setString(25,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(26, "%"+TCari.getText().trim()+"%");
                    ps.setString(27,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(28, "%"+TCari.getText().trim()+"%");
                    ps.setString(29,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(30, "%"+TCari.getText().trim()+"%");
                    ps.setInt(31,Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                    rs=ps.executeQuery();
                }
                while(rs.next()){
                    tabMode.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7),
                                   rs.getString(8),
                                   rs.getString(9),
                                   rs.getString(10),
                                   rs.getString(11),
                                   rs.getString(12),
                                   rs.getString(13),
                                   rs.getString(14),
                                   rs.getString(15),
                                   rs.getString(16),
                                   rs.getString(17),
                                   rs.getString(18),
                                   rs.getString(19),
                                   rs.getString(20),"Klik Kanan, Tampilkan Banyak Daftar",
                                   rs.getString(21),
                                   rs.getString(22)});
                }          
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
                
                if(ps2 != null){
                    ps2.close();
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
            
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TNo.setText("");
        Kd2.setText("");
        TNm.setText("");
        CmbJk.setSelectedIndex(0);
        CMbGd.setSelectedIndex(0);
        TTmp.setText("");
        cmbAgama.setSelectedIndex(0);
        CmbStts.setSelectedIndex(0);
        Alamat.setText("ALAMAT");
        AlamatPj.setText("ALAMAT");
        TKtp.setText("");
        TNoPeserta.setText("");
        Pekerjaan.setText("");
        PekerjaanPj.setText("");
        TTlp.setText("");
        TUmurTh.setText("");
        Saudara.setText("");     
        NmIbu.setText("");
        Kelurahan.setText("KELURAHAN");      
        Kecamatan.setText("KECAMATAN");      
        Kabupaten.setText("KABUPATEN"); 
        KelurahanPj.setText("KELURAHAN");      
        KecamatanPj.setText("KECAMATAN");      
        KabupatenPj.setText("KABUPATEN"); 
        R5.setSelected(true);
        DTPLahir.setDate(new Date());
        DTPDaftar.setDate(new Date());
        
        autoNomor();
           
        TNo.requestFocus();
    }

    private void getData() {
        if(tbPasien.getSelectedRow()!= -1){                
            try {                
                TNo.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString());
                Kd2.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString());
                TNm.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),2).toString());
                TKtp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),3).toString());

                switch (tbPasien.getValueAt(tbPasien.getSelectedRow(),4).toString()) {
                    case "L":
                        CmbJk.setSelectedItem("LAKI-LAKI");
                        break;
                    case "P":
                        CmbJk.setSelectedItem("PEREMPUAN");
                        break;
                }

                TTmp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),5).toString());
                
                pscariwilayah=koneksi.prepareStatement(
                        "select pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,pasien.pekerjaanpj,"+
                        "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");
                try {
                    pscariwilayah.setString(1,tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString());
                    rs=pscariwilayah.executeQuery();
                    if(rs.next()){
                        Alamat.setText(rs.getString("alamat"));
                        Kabupaten.setText(rs.getString("nm_kab"));
                        Kecamatan.setText(rs.getString("nm_kec"));
                        Kelurahan.setText(rs.getString("nm_kel"));
                        PekerjaanPj.setText(rs.getString("pekerjaanpj"));
                        AlamatPj.setText(rs.getString("alamatpj"));
                        KelurahanPj.setText(rs.getString("kelurahanpj"));
                        KecamatanPj.setText(rs.getString("kecamatanpj"));
                        KabupatenPj.setText(rs.getString("kabupatenpj"));
                    }
                } catch (Exception e) {
                    System.out.println("Notofikasi : "+e);
                } finally{
                    if(rs != null){
                        rs.close();
                    }
                    
                    if(pscariwilayah != null){
                        pscariwilayah.close();
                    }
                }
                            
                CMbGd.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),9).toString());
                Pekerjaan.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),10).toString());
                CmbStts.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),11).toString());
                cmbAgama.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),12).toString());
                TTlp.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),14).toString());
                Saudara.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),18).toString());
                Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis='"+TNo.getText()+"'",Kdpnj);
                nmpnj.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),19).toString());
                TNoPeserta.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),20).toString());
                NmIbu.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),7).toString());
                CMbPnd.setSelectedItem(tbPasien.getValueAt(tbPasien.getSelectedRow(),16).toString());
                switch (tbPasien.getValueAt(tbPasien.getSelectedRow(),17).toString()) {
                    case "AYAH":
                        R1.setSelected(true);
                        break;
                    case "IBU":
                        R2.setSelected(true);
                        break;
                    case "ISTRI":
                        R3.setSelected(true);
                        break;
                    case "SUAMI":  
                        R4.setSelected(true);
                        break;
                    case "SAUDARA":
                        R5.setSelected(true);
                        break;
                    case "ANAK":
                        R6.setSelected(true);
                        break;
                        
                }                
            } catch (Exception ex) {
            }   
            
            Valid.SetTgl(DTPLahir,tbPasien.getValueAt(tbPasien.getSelectedRow(),6).toString());
            Valid.SetTgl(DTPDaftar,tbPasien.getValueAt(tbPasien.getSelectedRow(),13).toString());                        
        }
    }
    
    public JTable getTable(){
        return tbPasien;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,309));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getpasien());
        BtnHapus.setEnabled(var.getpasien());
        BtnEdit.setEnabled(var.getpasien());
        BtnPrint.setEnabled(var.getpasien());
        ppGabungRM.setEnabled(var.getpasien());
        ppRiwayat.setEnabled(var.getresume_pasien());
        ppCatatanPasien.setEnabled(var.getcatatan_pasien());
        asalform=var.getform();
    }

    private void prosesCari2() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaan,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_peserta like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_ibu like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc LIMIT ? ");           
            ps2=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaan,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "where concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ? and pasien.no_rkm_medis like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_pasien like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_ktp like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_peserta like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tmp_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_lahir like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and penjab.png_jawab like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.alamat like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.gol_darah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.nm_ibu like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.pekerjaan like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.stts_nikah like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.agama like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.tgl_daftar like ? "+
                "or  concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like ?  and pasien.no_tlp like ?  order by pasien.no_rkm_medis desc");               
            try {
                if(cmbHlm.getSelectedItem().toString().equals("Semua")){
                    ps2.setString(1,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(2,"%"+TCari.getText().trim()+"%");
                    ps2.setString(3,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(4, "%"+TCari.getText().trim()+"%");
                    ps2.setString(5,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(6, "%"+TCari.getText().trim()+"%");
                    ps2.setString(7,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(8, "%"+TCari.getText().trim()+"%");
                    ps2.setString(9,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(10, "%"+TCari.getText().trim()+"%");
                    ps2.setString(11,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(12, "%"+TCari.getText().trim()+"%");
                    ps2.setString(13,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(14, "%"+TCari.getText().trim()+"%");
                    ps2.setString(15,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(16, "%"+TCari.getText().trim()+"%");
                    ps2.setString(17,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(18, "%"+TCari.getText().trim()+"%");
                    ps2.setString(19,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(20, "%"+TCari.getText().trim()+"%");
                    ps2.setString(21,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(22, "%"+TCari.getText().trim()+"%");
                    ps2.setString(23,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(24, "%"+TCari.getText().trim()+"%");
                    ps2.setString(25,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(26, "%"+TCari.getText().trim()+"%");
                    ps2.setString(27,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(28, "%"+TCari.getText().trim()+"%");
                    ps2.setString(29,"%"+Carialamat.getText().trim()+"%");
                    ps2.setString(30, "%"+TCari.getText().trim()+"%");
                    rs=ps2.executeQuery();
                }else{
                    ps.setString(1,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(4, "%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(6, "%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(8, "%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(10, "%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(12, "%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(14, "%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(16, "%"+TCari.getText().trim()+"%");
                    ps.setString(17,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(18, "%"+TCari.getText().trim()+"%");
                    ps.setString(19,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(20, "%"+TCari.getText().trim()+"%");
                    ps.setString(21,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(22, "%"+TCari.getText().trim()+"%");
                    ps.setString(23,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(24, "%"+TCari.getText().trim()+"%");
                    ps.setString(25,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(26, "%"+TCari.getText().trim()+"%");
                    ps.setString(27,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(28, "%"+TCari.getText().trim()+"%");
                    ps.setString(29,"%"+Carialamat.getText().trim()+"%");
                    ps.setString(30, "%"+TCari.getText().trim()+"%");
                    ps.setInt(31,Integer.parseInt(cmbHlm.getSelectedItem().toString()));
                    rs=ps.executeQuery();
                }
                while(rs.next()){
                    tabMode.addRow(new Object[] {false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7),
                                   rs.getString(8),
                                   rs.getString(9),
                                   rs.getString(10),
                                   rs.getString(11),
                                   rs.getString(12),
                                   rs.getString(13),
                                   rs.getString(14),
                                   rs.getString(15),
                                   rs.getString(16),
                                   rs.getString(17),
                                   rs.getString(18),
                                   rs.getString(19),
                                   rs.getString(20),
                                   Sequel.cariIsi("select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=?",rs.getString(1))+" X",
                                   rs.getString(21),
                                   rs.getString(22)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
                
                if(ps2 != null){
                    ps2.close();
                }
            }
                      
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void TutupJendela(){
        ChkInput.setSelected(false);
        isForm();
    }

    private void autoNomor() {  
        if(Kd2.getText().equals("")){
            if(ChkRM.isSelected()==true){
                if(tahun.equals("Yes")){
                    awalantahun=DTPDaftar.getSelectedItem().toString().substring(8,10);
                }else{
                    awalantahun="";
                }

                if(bulan.equals("Yes")){
                    awalanbulan=DTPDaftar.getSelectedItem().toString().substring(3,5);
                }else{
                    awalanbulan="";
                }

                if(posisitahun.equals("Depan")){
                    switch (pengurutan) {
                        case "Straight":
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                        case "Terminal":
                            Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),5,2),SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                        case "Middle":
                            Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2),SUBSTRING(RIGHT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                    }
                }else if(posisitahun.equals("Belakang")){
                    switch (pengurutan) {
                        case "Straight":
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(LEFT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                        case "Terminal":
                            Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),5,2),SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                        case "Middle":
                            Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2),SUBSTRING(LEFT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                            break;
                    }            
                }

                if(posisitahun.equals("Depan")){
                    TNo.setText(awalantahun+awalanbulan+NoRm.getText());
                }else if(posisitahun.equals("Belakang")){
                    if(!(awalanbulan+awalantahun).equals("")){
                        TNo.setText(NoRm.getText()+"-"+awalanbulan+awalantahun);
                    }else{
                        TNo.setText(NoRm.getText());
                    }            
                }
            }
        }
    }
}

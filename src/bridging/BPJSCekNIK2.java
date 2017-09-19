/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgKamar.java
 *
 * Created on May 23, 2010, 12:07:21 AM
 */

package bridging;

import fungsi.WarnaTable;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import keuangan.DlgKamar;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import simrskhanza.DlgCariDokter;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgPasien;
import simrskhanza.DlgPenanggungJawab;
import simrskhanza.DlgPilihanCetakDokumen;

/**
 *
 * @author dosen
 */
public final class BPJSCekNIK2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final Properties prop = new Properties();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgPasien pasien=new DlgPasien(null,false);
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private DlgKabupaten kab=new DlgKabupaten(null,false);
    private DlgKecamatan kec=new DlgKecamatan(null,false);
    private DlgKelurahan kel=new DlgKelurahan(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKamar kamar=new DlgKamar(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private BPJSApi api=new BPJSApi();
    private int pilih=0,p_no_ktp=0,p_tmp_lahir=0,p_nm_ibu=0,p_alamat=0,
            p_pekerjaan=0,p_no_tlp=0,p_umur=0,p_namakeluarga=0,p_no_peserta=0,
            p_kelurahan=0,p_kecamatan=0,p_kabupaten=0,p_pekerjaanpj=0,
            p_alamatpj=0,p_kelurahanpj=0,p_kecamatanpj=0,p_kabupatenpj=0,jmlhari=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String klg="SAUDARA",statuspasien="",pengurutan="",tahun="",bulan="",posisitahun="",awalantahun="",awalanbulan="",
            no_ktp="",tmp_lahir="",nm_ibu="",alamat="",pekerjaan="",no_tlp="",
            umur="",namakeluarga="",no_peserta="",kelurahan="",kecamatan="",sttsumur="",
            kabupaten="",pekerjaanpj="",alamatpj="",kelurahanpj="",kecamatanpj="",
            kabupatenpj="",hariawal="",requestJson,URL="",nosep="",user="";
    private PreparedStatement ps,pskelengkapan,pscariumur;
    private ResultSet rs;
    private double biaya=0;
    

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public BPJSCekNIK2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(170);
            }else if(i==1){
                column.setPreferredWidth(470);
            }
        }
        
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    if(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),3).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya NIK...!");
                    }else{
                        NoKartu.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),3).toString());
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });       
    
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(faskes.getTable().getSelectedRow()!= -1){                   
                    KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),3).toString());
                    NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),4).toString());
                }  
                KdPpkRujukan.requestFocus();
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
        
        faskes.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    faskes.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penyakit.getTable().getSelectedRow()!= -1){                   
                    KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                }  
                KdPenyakit.requestFocus();
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
        
        penyakit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penyakit.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){                   
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                    isNumber();
                    isPoli();
                    KdPoli.requestFocus();
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
        
        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    poli.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    Kdpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpnj.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
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
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
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
                if(kab.getTable().getSelectedRow()!= -1){
                    if(pilih==1){                    
                        Kabupaten.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                        Kabupaten.requestFocus();
                    }else if(pilih==2){                    
                        KabupatenPj.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(),0).toString());
                        KabupatenPj.requestFocus();
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
                if(kec.getTable().getSelectedRow()!= -1){
                    if(pilih==1){                    
                        Kecamatan.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                        Kecamatan.requestFocus();
                    }else if(pilih==2){                    
                        KecamatanPj.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(),0).toString());
                        KecamatanPj.requestFocus();
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
                if(kel.getTable().getSelectedRow()!= -1){
                    if(pilih==1){                    
                        Kelurahan.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                        Kelurahan.requestFocus();
                    }else if(pilih==2){                    
                        KelurahanPj.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(),0).toString());
                        KelurahanPj.requestFocus();
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
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){    
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    isNumber();
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
        
        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kamar.getTable().getSelectedRow()!= -1){   
                    if(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),6).toString().equals("KOSONG")){
                        KdPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());  
                        NmPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString());  
                        TBiaya.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),5).toString());  
                        KdPoli.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null,"Maaf, status kamar isi. Silahkan cari yang kosong..!!");
                        KdPoli.requestFocus();
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
        
        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kamar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        ChkCari.setSelected(false);
        isForm();
        NoKartu.setDocument(new batasInput((int)80).getKata(NoKartu));
        TNo.setDocument(new batasInput((byte)15).getKata(TNo));
        TNm.setDocument(new batasInput((byte)40).getKata(TNm));
        NmIbu.setDocument(new batasInput((byte)40).getKata(NmIbu));
        TKtp.setDocument(new batasInput((byte)20).getKata(TKtp));
        Kdpnj.setDocument(new batasInput((byte)3).getKata(Kdpnj));
        TTlp.setDocument(new batasInput((byte)13).getOnlyAngka(TTlp));
        TTmp.setDocument(new batasInput((byte)15).getKata(TTmp));
        Alamat.setDocument(new batasInput((int)200).getFilter(Alamat));
        AlamatPj.setDocument(new batasInput((int)100).getFilter(AlamatPj));
        Pekerjaan.setDocument(new batasInput((byte)15).getKata(Pekerjaan));
        PekerjaanPj.setDocument(new batasInput((byte)15).getKata(PekerjaanPj));
        TUmur.setDocument(new batasInput((byte)10).getKata(TUmur));
        Saudara.setDocument(new batasInput((byte)50).getKata(Saudara));
        Kabupaten.setDocument(new batasInput((byte)60).getFilter(Kabupaten));
        Kecamatan.setDocument(new batasInput((byte)60).getFilter(Kecamatan));
        Kelurahan.setDocument(new batasInput((byte)60).getFilter(Kelurahan));
        KabupatenPj.setDocument(new batasInput((byte)60).getFilter(KabupatenPj));
        KecamatanPj.setDocument(new batasInput((byte)60).getFilter(KecamatanPj));
        KelurahanPj.setDocument(new batasInput((byte)60).getFilter(KelurahanPj));
        TNoPeserta.setDocument(new batasInput((byte)25).getKata(TNoPeserta));
        TNoReg.setDocument(new batasInput((byte)8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        NoRujukan.setDocument(new batasInput((byte)20).getKata(NoRujukan));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));
        LokasiLaka.setDocument(new batasInput((byte)100).getKata(LokasiLaka));
        try{
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting"));            
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
        }catch(Exception e){
            System.out.println(e);
        }
        hariawal=Sequel.cariIsi("select hariawal from set_jam_minimal");
        pengurutan=Sequel.cariIsi("select urutan from set_urut_no_rkm_medis");
        tahun=Sequel.cariIsi("select tahun from set_urut_no_rkm_medis");
        bulan=Sequel.cariIsi("select bulan from set_urut_no_rkm_medis");
        posisitahun=Sequel.cariIsi("select posisi_tahun_bulan from set_urut_no_rkm_medis");
        
        try {
            user=var.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=var.getkode();
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

        TNm = new widget.TextBox();
        CmbJk = new widget.ComboBox();
        DTPLahir = new widget.Tanggal();
        TUmur = new widget.TextBox();
        TNoPeserta = new widget.TextBox();
        TKtp = new widget.TextBox();
        NoRm = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        kdpoli = new widget.TextBox();
        TPoli = new widget.TextBox();
        TBiaya = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDocument = new javax.swing.JMenuItem();
        NoBalasan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        panelCari = new widget.panelisi();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        FormKelengkapanPasien = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TTmp = new widget.TextBox();
        CMbGd = new widget.ComboBox();
        jLabel9 = new widget.Label();
        jLabel13 = new widget.Label();
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
        DTPDaftar = new widget.Tanggal();
        jLabel22 = new widget.Label();
        jLabel24 = new widget.Label();
        CMbPnd = new widget.ComboBox();
        Saudara = new widget.TextBox();
        R5 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R1 = new widget.RadioButton();
        jLabel25 = new widget.Label();
        Kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        Kelurahan = new widget.TextBox();
        Kecamatan = new widget.TextBox();
        Kabupaten = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        BtnKecamatan = new widget.Button();
        BtnKabupaten = new widget.Button();
        jLabel14 = new widget.Label();
        NmIbu = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        PekerjaanPj = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        AlamatPj = new widget.TextBox();
        KecamatanPj = new widget.TextBox();
        BtnKecamatanPj = new widget.Button();
        KabupatenPj = new widget.TextBox();
        BtnKabupatenPj = new widget.Button();
        BtnKelurahanPj = new widget.Button();
        KelurahanPj = new widget.TextBox();
        ChkRM = new widget.CekBox();
        R6 = new widget.RadioButton();
        FormKelengkapanSEP = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel23 = new widget.Label();
        TanggalSEP = new widget.Tanggal();
        jLabel30 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        jLabel31 = new widget.Label();
        NoRujukan = new widget.TextBox();
        jLabel10 = new widget.Label();
        KdPPK = new widget.TextBox();
        NmPPK = new widget.TextBox();
        btnPPKRujukan = new widget.Button();
        jLabel11 = new widget.Label();
        KdPpkRujukan = new widget.TextBox();
        NmPpkRujukan = new widget.TextBox();
        jLabel15 = new widget.Label();
        KdPenyakit = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        btnDiagnosa = new widget.Button();
        btnPoli = new widget.Button();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        LabelPoli = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        Catatan = new widget.TextBox();
        JenisPelayanan = new widget.ComboBox();
        LabelKelas = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel34 = new widget.Label();
        LakaLantas = new widget.ComboBox();
        jLabel35 = new widget.Label();
        LokasiLaka = new widget.TextBox();
        jLabel5 = new widget.Label();
        TNoReg = new widget.TextBox();
        jLabel36 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        ChkCari = new widget.CekBox();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        NoKartu = new widget.TextBox();
        btnPasien = new widget.Button();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.setOpaque(false);
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });

        DTPLahir.setEditable(false);
        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-08-2017" }));
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

        TUmur.setName("TUmur"); // NOI18N
        TUmur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurKeyPressed(evt);
            }
        });

        TNoPeserta.setHighlighter(null);
        TNoPeserta.setName("TNoPeserta"); // NOI18N
        TNoPeserta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPesertaKeyPressed(evt);
            }
        });

        TKtp.setName("TKtp"); // NOI18N
        TKtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtpKeyPressed(evt);
            }
        });

        NoRm.setHighlighter(null);
        NoRm.setName("NoRm"); // NOI18N
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });

        TPoli.setEditable(false);
        TPoli.setName("TPoli"); // NOI18N

        TBiaya.setName("TBiaya"); // NOI18N
        TBiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBiayaKeyPressed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDocument.setBackground(new java.awt.Color(255, 255, 255));
        MnDocument.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDocument.setForeground(java.awt.Color.darkGray);
        MnDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDocument.setText("Cetak Document");
        MnDocument.setName("MnDocument"); // NOI18N
        MnDocument.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDocumentActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDocument);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Peserta BPJS Berdasarkan NIK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("");
        tbKamar.setComponentPopupMenu(jPopupMenu1);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelInput.setBorder(null);
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(200, 517));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 44));
        panelCari.setLayout(new java.awt.BorderLayout());

        scrollPane2.setBorder(null);
        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setPreferredSize(new java.awt.Dimension(1093, 138));

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 168));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        FormKelengkapanPasien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), "::[ Kelengkapan Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        FormKelengkapanPasien.setName("FormKelengkapanPasien"); // NOI18N
        FormKelengkapanPasien.setOpaque(false);
        FormKelengkapanPasien.setPreferredSize(new java.awt.Dimension(1000, 245));
        FormKelengkapanPasien.setLayout(null);

        jLabel3.setText("No.Rekam Medis :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormKelengkapanPasien.add(jLabel3);
        jLabel3.setBounds(3, 25, 100, 23);

        TTmp.setName("TTmp"); // NOI18N
        TTmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TTmp);
        TTmp.setBounds(107, 55, 120, 23);

        CMbGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "AB", "O" }));
        CMbGd.setName("CMbGd"); // NOI18N
        CMbGd.setOpaque(false);
        CMbGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbGdKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CMbGd);
        CMbGd.setBounds(325, 55, 90, 23);

        jLabel9.setText("G.Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormKelengkapanPasien.add(jLabel9);
        jLabel9.setBounds(235, 55, 85, 23);

        jLabel13.setText("Tempat Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormKelengkapanPasien.add(jLabel13);
        jLabel13.setBounds(3, 55, 100, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormKelengkapanPasien.add(jLabel18);
        jLabel18.setBounds(3, 85, 100, 23);

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
        FormKelengkapanPasien.add(cmbAgama);
        cmbAgama.setBounds(107, 85, 120, 23);

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormKelengkapanPasien.add(jLabel19);
        jLabel19.setBounds(235, 85, 85, 23);

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
        FormKelengkapanPasien.add(CmbStts);
        CmbStts.setBounds(325, 85, 90, 23);

        jLabel20.setText("Alamat Pasien :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormKelengkapanPasien.add(jLabel20);
        jLabel20.setBounds(430, 25, 100, 23);

        jLabel21.setText("No.Telp :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormKelengkapanPasien.add(jLabel21);
        jLabel21.setBounds(3, 145, 100, 23);

        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Pekerjaan);
        Pekerjaan.setBounds(107, 115, 120, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormKelengkapanPasien.add(jLabel12);
        jLabel12.setBounds(3, 115, 100, 23);

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
        FormKelengkapanPasien.add(Alamat);
        Alamat.setBounds(534, 25, 213, 23);

        TTlp.setHighlighter(null);
        TTlp.setName("TTlp"); // NOI18N
        TTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTlpKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TTlp);
        TTlp.setBounds(107, 145, 120, 23);

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
        FormKelengkapanPasien.add(TNo);
        TNo.setBounds(107, 25, 160, 23);

        DTPDaftar.setEditable(false);
        DTPDaftar.setForeground(new java.awt.Color(50, 70, 50));
        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-08-2017" }));
        DTPDaftar.setDisplayFormat("dd-MM-yyyy");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);
        DTPDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPDaftarKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(DTPDaftar);
        DTPDaftar.setBounds(325, 145, 90, 23);

        jLabel22.setText("Pertama Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormKelengkapanPasien.add(jLabel22);
        jLabel22.setBounds(235, 145, 85, 23);

        jLabel24.setText("Pendidikan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormKelengkapanPasien.add(jLabel24);
        jLabel24.setBounds(235, 115, 85, 23);

        CMbPnd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        CMbPnd.setName("CMbPnd"); // NOI18N
        CMbPnd.setOpaque(false);
        CMbPnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbPndKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CMbPnd);
        CMbPnd.setBounds(325, 115, 90, 23);

        Saudara.setName("Saudara"); // NOI18N
        Saudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaudaraKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Saudara);
        Saudara.setBounds(534, 115, 292, 23);

        R5.setBorder(null);
        buttonGroup1.add(R5);
        R5.setSelected(true);
        R5.setText("Saudara");
        R5.setIconTextGap(0);
        R5.setName("R5"); // NOI18N
        FormKelengkapanPasien.add(R5);
        R5.setBounds(721, 85, 60, 23);

        R4.setBorder(null);
        buttonGroup1.add(R4);
        R4.setText("Suami");
        R4.setIconTextGap(0);
        R4.setName("R4"); // NOI18N
        FormKelengkapanPasien.add(R4);
        R4.setBounds(669, 85, 50, 23);

        R3.setBorder(null);
        buttonGroup1.add(R3);
        R3.setText("Istri");
        R3.setIconTextGap(0);
        R3.setName("R3"); // NOI18N
        FormKelengkapanPasien.add(R3);
        R3.setBounds(623, 85, 44, 23);

        R2.setBorder(null);
        buttonGroup1.add(R2);
        R2.setText("Ibu");
        R2.setIconTextGap(0);
        R2.setName("R2"); // NOI18N
        FormKelengkapanPasien.add(R2);
        R2.setBounds(581, 85, 40, 23);

        R1.setBorder(null);
        buttonGroup1.add(R1);
        R1.setText("Ayah");
        R1.setIconTextGap(0);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(40, 20));
        FormKelengkapanPasien.add(R1);
        R1.setBounds(534, 85, 46, 23);

        jLabel25.setText("Askes/Asuransi :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormKelengkapanPasien.add(jLabel25);
        jLabel25.setBounds(3, 205, 100, 23);

        Kdpnj.setText("-");
        Kdpnj.setHighlighter(null);
        Kdpnj.setName("Kdpnj"); // NOI18N
        Kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdpnjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Kdpnj);
        Kdpnj.setBounds(107, 205, 80, 23);

        nmpnj.setEditable(false);
        nmpnj.setText("-");
        nmpnj.setName("nmpnj"); // NOI18N
        FormKelengkapanPasien.add(nmpnj);
        nmpnj.setBounds(189, 205, 196, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("ALt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPenjab);
        BtnPenjab.setBounds(387, 205, 28, 23);

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
        FormKelengkapanPasien.add(Kelurahan);
        Kelurahan.setBounds(749, 25, 130, 23);

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
        FormKelengkapanPasien.add(Kecamatan);
        Kecamatan.setBounds(534, 55, 130, 23);

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
        FormKelengkapanPasien.add(Kabupaten);
        Kabupaten.setBounds(696, 55, 130, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKelurahan);
        BtnKelurahan.setBounds(881, 25, 28, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKecamatan);
        BtnKecamatan.setBounds(666, 55, 28, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKabupaten);
        BtnKabupaten.setBounds(828, 55, 28, 23);

        jLabel14.setText("Nama Ibu :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormKelengkapanPasien.add(jLabel14);
        jLabel14.setBounds(3, 175, 100, 23);

        NmIbu.setName("NmIbu"); // NOI18N
        NmIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmIbuKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(NmIbu);
        NmIbu.setBounds(107, 175, 308, 23);

        jLabel26.setText("Png. Jawab :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormKelengkapanPasien.add(jLabel26);
        jLabel26.setBounds(430, 85, 100, 23);

        jLabel27.setText("Png. Jawab :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormKelengkapanPasien.add(jLabel27);
        jLabel27.setBounds(430, 115, 100, 23);

        PekerjaanPj.setName("PekerjaanPj"); // NOI18N
        PekerjaanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(PekerjaanPj);
        PekerjaanPj.setBounds(534, 145, 292, 23);

        jLabel28.setText("Pekerjaan P.J. :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormKelengkapanPasien.add(jLabel28);
        jLabel28.setBounds(430, 145, 100, 23);

        jLabel29.setText("Alamat P.J. :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormKelengkapanPasien.add(jLabel29);
        jLabel29.setBounds(430, 175, 100, 23);

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
        FormKelengkapanPasien.add(AlamatPj);
        AlamatPj.setBounds(534, 175, 213, 23);

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
        FormKelengkapanPasien.add(KecamatanPj);
        KecamatanPj.setBounds(534, 205, 130, 23);

        BtnKecamatanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatanPj.setMnemonic('3');
        BtnKecamatanPj.setToolTipText("ALt+3");
        BtnKecamatanPj.setName("BtnKecamatanPj"); // NOI18N
        BtnKecamatanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKecamatanPj);
        BtnKecamatanPj.setBounds(666, 205, 28, 23);

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
        FormKelengkapanPasien.add(KabupatenPj);
        KabupatenPj.setBounds(696, 205, 130, 23);

        BtnKabupatenPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupatenPj.setMnemonic('4');
        BtnKabupatenPj.setToolTipText("ALt+4");
        BtnKabupatenPj.setName("BtnKabupatenPj"); // NOI18N
        BtnKabupatenPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKabupatenPj);
        BtnKabupatenPj.setBounds(828, 205, 28, 23);

        BtnKelurahanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahanPj.setMnemonic('2');
        BtnKelurahanPj.setToolTipText("ALt+2");
        BtnKelurahanPj.setName("BtnKelurahanPj"); // NOI18N
        BtnKelurahanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKelurahanPj);
        BtnKelurahanPj.setBounds(881, 175, 28, 23);

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
        FormKelengkapanPasien.add(KelurahanPj);
        KelurahanPj.setBounds(749, 175, 130, 23);

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
        FormKelengkapanPasien.add(ChkRM);
        ChkRM.setBounds(269, 25, 23, 23);

        R6.setBorder(null);
        buttonGroup1.add(R6);
        R6.setText("Anak");
        R6.setIconTextGap(0);
        R6.setName("R6"); // NOI18N
        FormKelengkapanPasien.add(R6);
        R6.setBounds(783, 85, 60, 23);

        FormInput.add(FormKelengkapanPasien);

        FormKelengkapanSEP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), "::[ Kelengkapan Data SEP, Registrasi & Kamar Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        FormKelengkapanSEP.setName("FormKelengkapanSEP"); // NOI18N
        FormKelengkapanSEP.setOpaque(false);
        FormKelengkapanSEP.setPreferredSize(new java.awt.Dimension(1000, 187));
        FormKelengkapanSEP.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormKelengkapanSEP.add(jLabel4);
        jLabel4.setBounds(3, 25, 100, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormKelengkapanSEP.add(TNoRw);
        TNoRw.setBounds(107, 25, 152, 23);

        jLabel23.setText("Tgl.SEP :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel23);
        jLabel23.setBounds(430, 55, 100, 23);

        TanggalSEP.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-08-2017 07:56:30" }));
        TanggalSEP.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalSEP.setName("TanggalSEP"); // NOI18N
        TanggalSEP.setOpaque(false);
        TanggalSEP.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSEPKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalSEP);
        TanggalSEP.setBounds(534, 55, 140, 23);

        jLabel30.setText("Tgl.Rujuk :");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel30);
        jLabel30.setBounds(430, 25, 100, 23);

        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-08-2017 07:56:30" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalRujuk);
        TanggalRujuk.setBounds(534, 25, 140, 23);

        jLabel31.setText("No.Rujukan :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel31);
        jLabel31.setBounds(675, 25, 80, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoRujukan);
        NoRujukan.setBounds(759, 25, 165, 23);

        jLabel10.setText("PPK Pelayanan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormKelengkapanSEP.add(jLabel10);
        jLabel10.setBounds(3, 55, 100, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormKelengkapanSEP.add(KdPPK);
        KdPPK.setBounds(107, 55, 80, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormKelengkapanSEP.add(NmPPK);
        NmPPK.setBounds(189, 55, 226, 23);

        btnPPKRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan.setMnemonic('X');
        btnPPKRujukan.setToolTipText("Alt+X");
        btnPPKRujukan.setName("btnPPKRujukan"); // NOI18N
        btnPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukanActionPerformed(evt);
            }
        });
        btnPPKRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnPPKRujukan);
        btnPPKRujukan.setBounds(387, 85, 28, 23);

        jLabel11.setText("PPK Rujukan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormKelengkapanSEP.add(jLabel11);
        jLabel11.setBounds(3, 85, 100, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(107, 85, 80, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan.setHighlighter(null);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(189, 85, 196, 23);

        jLabel15.setText("Diagnosa Awal :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormKelengkapanSEP.add(jLabel15);
        jLabel15.setBounds(3, 115, 100, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        FormKelengkapanSEP.add(KdPenyakit);
        KdPenyakit.setBounds(107, 115, 80, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit.setHighlighter(null);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        FormKelengkapanSEP.add(NmPenyakit);
        NmPenyakit.setBounds(189, 115, 196, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('X');
        btnDiagnosa.setToolTipText("Alt+X");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnDiagnosa);
        btnDiagnosa.setBounds(387, 115, 28, 23);

        btnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli.setMnemonic('X');
        btnPoli.setToolTipText("Alt+X");
        btnPoli.setName("btnPoli"); // NOI18N
        btnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliActionPerformed(evt);
            }
        });
        btnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnPoli);
        btnPoli.setBounds(387, 145, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormKelengkapanSEP.add(NmPoli);
        NmPoli.setBounds(189, 145, 196, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormKelengkapanSEP.add(KdPoli);
        KdPoli.setBounds(107, 145, 80, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli);
        LabelPoli.setBounds(3, 145, 100, 23);

        jLabel32.setText("Jns.Pelayanan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormKelengkapanSEP.add(jLabel32);
        jLabel32.setBounds(430, 115, 100, 23);

        jLabel33.setText("Catatan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormKelengkapanSEP.add(jLabel33);
        jLabel33.setBounds(430, 85, 100, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Catatan);
        Catatan.setBounds(534, 85, 140, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
        JenisPelayanan.setOpaque(false);
        JenisPelayanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayananItemStateChanged(evt);
            }
        });
        JenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayananKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(JenisPelayanan);
        JenisPelayanan.setBounds(534, 115, 140, 23);

        LabelKelas.setText("Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas);
        LabelKelas.setBounds(675, 115, 80, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. kelas 3" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.setOpaque(false);
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Kelas);
        Kelas.setBounds(759, 115, 165, 23);

        jLabel34.setText("Laka Lantas :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormKelengkapanSEP.add(jLabel34);
        jLabel34.setBounds(675, 55, 80, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kasus Kecelakaan", "2. Bukan Kasus Kecelakaan" }));
        LakaLantas.setSelectedIndex(1);
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.setOpaque(false);
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LakaLantas);
        LakaLantas.setBounds(759, 55, 165, 23);

        jLabel35.setText("Lokasi Laka :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormKelengkapanSEP.add(jLabel35);
        jLabel35.setBounds(675, 85, 80, 23);

        LokasiLaka.setHighlighter(null);
        LokasiLaka.setName("LokasiLaka"); // NOI18N
        LokasiLaka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiLakaKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LokasiLaka);
        LokasiLaka.setBounds(759, 85, 165, 23);

        jLabel5.setText("No. Reg. :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormKelengkapanSEP.add(jLabel5);
        jLabel5.setBounds(274, 25, 77, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TNoReg);
        TNoReg.setBounds(355, 25, 60, 23);

        jLabel36.setText("Dr Dituju/DPJP :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormKelengkapanSEP.add(jLabel36);
        jLabel36.setBounds(430, 145, 100, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(kddokter);
        kddokter.setBounds(534, 145, 130, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormKelengkapanSEP.add(TDokter);
        TDokter.setBounds(666, 145, 228, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(BtnDokter);
        BtnDokter.setBounds(896, 145, 28, 23);

        FormInput.add(FormKelengkapanSEP);

        scrollPane2.setViewportView(FormInput);

        panelCari.add(scrollPane2, java.awt.BorderLayout.CENTER);

        PanelInput.add(panelCari, java.awt.BorderLayout.CENTER);

        ChkCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setMnemonic('I');
        ChkCari.setText("  .: Input Registrasi");
        ChkCari.setToolTipText("Alt+I");
        ChkCari.setBorderPainted(true);
        ChkCari.setBorderPaintedFlat(true);
        ChkCari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkCari.setIconTextGap(2);
        ChkCari.setName("ChkCari"); // NOI18N
        ChkCari.setPreferredSize(new java.awt.Dimension(632, 22));
        ChkCari.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkCariActionPerformed(evt);
            }
        });
        PanelInput.add(ChkCari, java.awt.BorderLayout.PAGE_START);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("NIK Pasien :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass6.add(jLabel16);

        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.setPreferredSize(new java.awt.Dimension(250, 23));
        NoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKartuKeyPressed(evt);
            }
        });
        panelGlass6.add(NoKartu);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('5');
        btnPasien.setToolTipText("Alt+5");
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
        panelGlass6.add(btnPasien);

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

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass6.add(jLabel17);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnSimpan);

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

        PanelInput.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Pengadaan Ipsrs"); 
            }
            Sequel.AutoComitTrue();
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJSNoPeserta.jrxml","report","[ Pencarian Peserta BPJS Berdasarkan Nomor Kepesertaan ]",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void NoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKartuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_NoKartuKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        pasien.emptTeks();
        pasien.isCek();
        pasien.TutupJendela();
        pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,NoKartu,BtnPrint);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(ChkCari.isSelected()==true){
            ChkCari.setSelected(false);
            isForm();
        }            
        tampil(NoKartu.getText());
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,NoKartu,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void ChkCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCariActionPerformed
        if(tabMode.getRowCount()==0){
            nosep="";
            ChkCari.setSelected(false);
            isForm();
            JOptionPane.showMessageDialog(null,"Maaf, data peserta BPJS masih kosong. Silahkan lakukan pencarian berdasar No.Kartu...!!!!");
        }else{
            isForm();
        }            
    }//GEN-LAST:event_ChkCariActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void TTmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpKeyPressed
        Valid.pindah(evt,TNo,CMbGd);
    }//GEN-LAST:event_TTmpKeyPressed

    private void CMbGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbGdKeyPressed
        Valid.pindah(evt,TTmp,cmbAgama);
    }//GEN-LAST:event_CMbGdKeyPressed

    private void cmbAgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAgamaKeyPressed
        Valid.pindah(evt,CMbGd,CmbStts);
    }//GEN-LAST:event_cmbAgamaKeyPressed

    private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed
        Valid.pindah(evt,cmbAgama,Pekerjaan);
    }//GEN-LAST:event_CmbSttsKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        Valid.pindah(evt,CmbStts,CMbPnd);
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void AlamatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseMoved
        if(Alamat.getText().equals("ALAMAT")){
            Alamat.setText("");
        }
    }//GEN-LAST:event_AlamatMouseMoved

    private void AlamatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseExited
        if(Alamat.getText().equals("")){
            Alamat.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatMouseExited

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
            Kdpnj.requestFocus();
        }
    }//GEN-LAST:event_AlamatKeyPressed

    private void TTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTlpKeyPressed
       Valid.pindah(evt,CMbPnd,DTPDaftar);
    }//GEN-LAST:event_TTlpKeyPressed

    private void TNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TTmp.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KabupatenPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            kddokter.requestFocus();
        }
    }//GEN-LAST:event_TNoKeyPressed

    private void DTPDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPDaftarKeyPressed
       Valid.pindah(evt,TTlp,NmIbu);
    }//GEN-LAST:event_DTPDaftarKeyPressed

    private void CMbPndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbPndKeyPressed
        Valid.pindah(evt,Pekerjaan,TTlp);
    }//GEN-LAST:event_CMbPndKeyPressed

    private void SaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaudaraKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            PekerjaanPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }
    }//GEN-LAST:event_SaudaraKeyPressed

    private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NmIbu.requestFocus();
        }
    }//GEN-LAST:event_KdpnjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
       penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if(Kelurahan.getText().equals("KELURAHAN")){
            Kelurahan.setText("");
        }
    }//GEN-LAST:event_KelurahanMouseMoved

    private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if(Kelurahan.getText().equals("")){
            Kelurahan.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanMouseExited

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

    private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if(Kecamatan.getText().equals("KECAMATAN")){
            Kecamatan.setText("");
        }
    }//GEN-LAST:event_KecamatanMouseMoved

    private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if(Kecamatan.getText().equals("")){
            Kecamatan.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanMouseExited

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

    private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if(Kabupaten.getText().equals("KABUPATEN")){
            Kabupaten.setText("");
        }
    }//GEN-LAST:event_KabupatenMouseMoved

    private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
        if(Kabupaten.getText().equals("")){
            Kabupaten.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenMouseExited

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Saudara.requestFocus();
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
       pilih=1;
        kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        pilih=1;
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
       pilih=1;
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void NmIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmIbuKeyPressed
        Valid.pindah(evt,DTPDaftar,Kdpnj);
    }//GEN-LAST:event_NmIbuKeyPressed

    private void PekerjaanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanPjKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(AlamatPj.getText().equals("ALAMAT")){
                AlamatPj.setText("");
            }
            AlamatPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Saudara.requestFocus();
        }
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
            PekerjaanPj.requestFocus();
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
       pilih=2;
        kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);     
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
            TNoReg.requestFocus();
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
       pilih=2;
        kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenPjActionPerformed

    private void BtnKelurahanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanPjActionPerformed
        pilih=2;
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

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if(ChkRM.isSelected()==true){
            TNo.setEditable(false);
            TNo.setBackground(new Color(245,250,240));
            if(statuspasien.equals("baru")){
                autoNomor();
            }
        }else if(ChkRM.isSelected()==false){
            TNo.setEditable(true);
            TNo.setBackground(new Color(250,255,245));
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah2(evt, NoRujukan,LakaLantas);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah2(evt, TNoReg,NoRujukan);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt,TanggalRujuk, TanggalSEP);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        faskes.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt,TanggalSEP,btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,btnPPKRujukan,btnPoli);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        if(JenisPelayanan.getSelectedIndex()==0){
            kamar.load();
            kamar.isCek();
            kamar.emptTeks();
            kamar.tampil();
            kamar.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            kamar.setLocationRelativeTo(internalFrame1);
            kamar.setVisible(true);
        }else{
            poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            poli.setLocationRelativeTo(internalFrame1);
            poli.setVisible(true);
        }            
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        Valid.pindah(evt,btnDiagnosa,LakaLantas);
    }//GEN-LAST:event_btnPoliKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,LakaLantas,LokasiLaka);
    }//GEN-LAST:event_CatatanKeyPressed

    private void JenisPelayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayananItemStateChanged
        if(JenisPelayanan.getSelectedIndex()==1){
            LabelPoli.setText("Poli Tujuan :");
        }else if(JenisPelayanan.getSelectedIndex()==0){
            LabelPoli.setText("Kamar Tujuan :");
        }
        KdPoli.setText("");
        NmPoli.setText("");
    }//GEN-LAST:event_JenisPelayananItemStateChanged

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt,LokasiLaka,Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,JenisPelayanan,kddokter);
    }//GEN-LAST:event_KelasKeyPressed

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt,TanggalSEP,Catatan);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void LokasiLakaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiLakaKeyPressed
        Valid.pindah(evt,Catatan,JenisPelayanan);
    }//GEN-LAST:event_LokasiLakaKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TanggalRujuk.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(KabupatenPj.getText().equals("KABUPATEN")){
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        }
    }//GEN-LAST:event_TNoRegKeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isNumber();
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,Kelas,BtnSimpan);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

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
        }else if(umur.equals("Yes")&&(TUmur.getText().trim().length()<p_umur)){
            Valid.textKosong(TUmur,"Umur Pasien minimal "+p_umur+" karakter dan ");            
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
        }else if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"dokter");
        }else if(Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "+
            "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "+
            "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?",TNo.getText())>0){
                JOptionPane.showMessageDialog(null,"Pasien sedang dalam masa perawatan di kamar inap..!!");
                NoRujukan.requestFocus();
        }else if(NoRujukan.getText().trim().equals("")){
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        }else if(KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")){
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        }else if(KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")){
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        }else if(KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")){
            Valid.textKosong(KdPenyakit, "Diagnosa");
        }else if(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli, "Poli/Kamar");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan, "Catatan");
        }else{            
            if((JenisPelayanan.getSelectedIndex()==1)&&(NmPoli.getText().toLowerCase().contains("darurat"))){
                if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                        "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                        "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"%' and "+
                        "nmpolitujuan like '%darurat%'")>=3){
                    JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan 3x pembuatan SEP di jenis pelayanan yang sama..!!");
                    NoKartu.requestFocus();
                }else{
                   insertPasien(); 
                }
            }else if((JenisPelayanan.getSelectedIndex()==1)&&(!NmPoli.getText().toLowerCase().contains("darurat"))){
                if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                        "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                        "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"%' and "+
                        "nmpolitujuan not like '%darurat%'")>=1){
                    JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan pembuatan SEP di jenis pelayanan rawat jalan..!!");
                    NoKartu.requestFocus();
                }else{
                   insertPasien(); 
                }
            }else if(JenisPelayanan.getSelectedIndex()==0){
                insertPasien();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kddokter,NoKartu);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        BtnSimpan.setVisible(false);        
    }//GEN-LAST:event_formWindowOpened

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        
    }//GEN-LAST:event_TNmKeyPressed

    private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
        Valid.pindah(evt,TNm,CMbGd);
    }//GEN-LAST:event_CmbJkKeyPressed

    private void DTPLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahirItemStateChanged

    }//GEN-LAST:event_DTPLahirItemStateChanged

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
       
    }//GEN-LAST:event_DTPLahirKeyPressed

    private void TUmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurKeyPressed
       
    }//GEN-LAST:event_TUmurKeyPressed

    private void TNoPesertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPesertaKeyPressed
        Valid.pindah(evt,Kdpnj,TTlp);
    }//GEN-LAST:event_TNoPesertaKeyPressed

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

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        
    }//GEN-LAST:event_kdpoliKeyPressed

    private void TBiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBiayaKeyPressed
        Valid.pindah(evt,kdpoli,BtnSimpan);
    }//GEN-LAST:event_TBiayaKeyPressed

    private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRmKeyPressed

    private void MnDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDocumentActionPerformed
        if(!nosep.equals("")){
            DlgPilihanCetakDokumen pilihan=new DlgPilihanCetakDokumen(null,true);
            pilihan.tampil();
            if(JenisPelayanan.getSelectedIndex()==1){
                pilihan.setNoRm(TNoRw.getText(),TNo.getText(),nosep,TNoReg.getText(),TPoli.getText(),nmpnj.getText(), 
                        TDokter.getText(),TNm.getText(),Alamat.getText()+", "+Kelurahan.getText()+", "+Kecamatan.getText()+", "+Kabupaten.getText(), 
                        Saudara.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),"ranap");
            }else{
                pilihan.setNoRm(TNoRw.getText(),TNo.getText(),nosep,TNoReg.getText(),TPoli.getText(),nmpnj.getText(), 
                        TDokter.getText(),TNm.getText(),Alamat.getText()+", "+Kelurahan.getText()+", "+Kecamatan.getText()+", "+Kabupaten.getText(), 
                        Saudara.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),"ralan");
            }

            pilihan.setSize(500,400);
            pilihan.setLocationRelativeTo(internalFrame1);
            pilihan.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Data SEP belum tersimpan, berkas tidak bisa dicetak...!");
        }
    }//GEN-LAST:event_MnDocumentActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSCekNIK2 dialog = new BPJSCekNIK2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKabupatenPj;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKecamatanPj;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnKelurahanPj;
    private widget.Button BtnPenjab;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CMbGd;
    private widget.ComboBox CMbPnd;
    private widget.TextBox Catatan;
    private widget.CekBox ChkCari;
    private widget.CekBox ChkRM;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormKelengkapanPasien;
    private widget.PanelBiasa FormKelengkapanSEP;
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox Kabupaten;
    private widget.TextBox KabupatenPj;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox KecamatanPj;
    private widget.ComboBox Kelas;
    private widget.TextBox Kelurahan;
    private widget.TextBox KelurahanPj;
    private widget.Label LabelKelas;
    private widget.Label LabelPoli;
    private widget.ComboBox LakaLantas;
    private widget.TextBox LokasiLaka;
    private javax.swing.JMenuItem MnDocument;
    private widget.TextBox NmIbu;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRm;
    private widget.TextBox NoRujukan;
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
    private widget.TextBox TBiaya;
    private widget.TextBox TDokter;
    private widget.TextBox TKtp;
    private widget.TextBox TNm;
    private widget.TextBox TNo;
    private widget.TextBox TNoPeserta;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPoli;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.TextBox TUmur;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalSEP;
    private widget.Button btnDiagnosa;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPasien;
    private widget.Button btnPoli;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbAgama;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpoli;
    private widget.TextBox nmpnj;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil(String nomorpeserta) {
        BPJSApi api=new BPJSApi();
        try {
            nosep="";
            statuspasien="";
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLAPIBPJS")+"/Peserta/Peserta/nik/"+nomorpeserta;	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
	    headers.add("X-Signature",api.getHmac());
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();	
            
            //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metadata");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("message").asText().equals("OK")){
                Valid.tabelKosong(tabMode);
                JsonNode response = root.path("response");                
                tabMode.addRow(new Object[]{
                    "Nama",": "+response.path("peserta").path("nama").asText()
                });
                TNm.setText(response.path("peserta").path("nama").asText());
                tabMode.addRow(new Object[]{
                    "NIK",": "+response.path("peserta").path("nik").asText()
                });
                TKtp.setText(response.path("peserta").path("nik").asText());
                tabMode.addRow(new Object[]{
                    "Nomor Kartu",": "+response.path("peserta").path("noKartu").asText()
                });
                TNoPeserta.setText(response.path("peserta").path("noKartu").asText());
                tabMode.addRow(new Object[]{
                    "Nomor MR",": "+response.path("peserta").path("noMr").asText()
                });
                tabMode.addRow(new Object[]{
                    "Pisa",": "+response.path("peserta").path("pisa").asText()
                });
                tabMode.addRow(new Object[]{
                    "Jenis Kelamin",": "+response.path("peserta").path("sex").asText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")
                });
                switch (response.path("peserta").path("sex").asText()) {
                    case "L":
                        CmbJk.setSelectedItem("LAKI-LAKI");
                        break;
                    case "P":
                        CmbJk.setSelectedItem("PEREMPUAN");
                        break;
                }
                tabMode.addRow(new Object[]{
                    "Status Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Keterangan",": "+response.path("peserta").path("statusPeserta").path("keterangan").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Kode",": "+response.path("peserta").path("statusPeserta").path("kode").asText()
                });
                tabMode.addRow(new Object[]{
                    "Jenis Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Jenis Peserta",": "+response.path("peserta").path("jenisPeserta").path("kdJenisPeserta").asText()
                });
                Kdpnj.setText("BPJ");
                nmpnj.setText("BPJS");
                tabMode.addRow(new Object[]{
                    "       Nama Jenis Peserta",": "+response.path("peserta").path("jenisPeserta").path("nmJenisPeserta").asText()
                });                
                Pekerjaan.setText(response.path("peserta").path("jenisPeserta").path("nmJenisPeserta").asText());
                tabMode.addRow(new Object[]{
                    "Kelas Tanggungan",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Kelas",": "+response.path("peserta").path("kelasTanggungan").path("kdKelas").asText()
                });
                if(response.path("peserta").path("kelasTanggungan").path("kdKelas").asText().equals("1")){
                    Kelas.setSelectedIndex(0);
                }else if(response.path("peserta").path("kelasTanggungan").path("kdKelas").asText().equals("2")){
                    Kelas.setSelectedIndex(1);
                }else if(response.path("peserta").path("kelasTanggungan").path("kdKelas").asText().equals("3")){
                    Kelas.setSelectedIndex(2);
                }
                tabMode.addRow(new Object[]{
                    "       Nama Kelas",": "+response.path("peserta").path("kelasTanggungan").path("nmKelas").asText()
                });
                tabMode.addRow(new Object[]{
                    "Provider Umum",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Cabang",": "+response.path("peserta").path("provUmum").path("kdCabang").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Kode Provider",": "+response.path("peserta").path("provUmum").path("kdProvider").asText()
                });                
                KdPpkRujukan.setText(response.path("peserta").path("provUmum").path("kdProvider").asText());
                tabMode.addRow(new Object[]{
                    "       Nama Cabang",": "+response.path("peserta").path("provUmum").path("nmCabang").asText()
                });
                Kabupaten.setText(response.path("peserta").path("provUmum").path("nmCabang").asText());
                KabupatenPj.setText(response.path("peserta").path("provUmum").path("nmCabang").asText());
                tabMode.addRow(new Object[]{
                    "       Nama Provider",": "+response.path("peserta").path("provUmum").path("nmProvider").asText()
                });
                NmPpkRujukan.setText(response.path("peserta").path("provUmum").path("nmProvider").asText());                
                tabMode.addRow(new Object[]{
                    "Tanggal Cetak Kartu",": "+response.path("peserta").path("tglCetakKartu").asText()
                });
                tabMode.addRow(new Object[]{
                    "Tanggal Lahir",": "+response.path("peserta").path("tglLahir").asText()
                });
                Valid.SetTgl(DTPLahir,response.path("peserta").path("tglLahir").asText());
                tabMode.addRow(new Object[]{
                    "Tanggal TAT",": "+response.path("peserta").path("tglTAT").asText()
                });
                tabMode.addRow(new Object[]{
                    "Tanggal TMT",": "+response.path("peserta").path("tglTMT").asText()
                });
                tabMode.addRow(new Object[]{
                    "Umur",":"
                });
                tabMode.addRow(new Object[]{
                    "       Umur Saat Pelayanan",": "+response.path("peserta").path("umur").path("umurSaatPelayanan").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Umur Sekarang",": "+response.path("peserta").path("umur").path("umurSekarang").asText().replaceAll("tahun ,","Th ").replaceAll("bulan ,","Bl ").replaceAll("hari","Hr")
                });
                tabMode.addRow(new Object[]{
                    "Informasi",":"
                });
                tabMode.addRow(new Object[]{
                    "       Dinsos",": "+response.path("peserta").path("informasi").path("dinsos").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Iuran",": "+response.path("peserta").path("informasi").path("iuran").asText()
                });
                tabMode.addRow(new Object[]{
                    "       No.SKTM",": "+response.path("peserta").path("informasi").path("noSKTM").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Prolanis PRB",": "+response.path("peserta").path("informasi").path("prolanisPRB").asText()
                });
                TUmur.setText(response.path("peserta").path("umur").path("umurSekarang").asText().replaceAll("tahun ,","Th ").replaceAll("bulan ,","Bl ").replaceAll("hari","Hr"));
                ps=koneksi.prepareStatement(
                   "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,"+
                   "kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab, pasien.gol_darah, "+
                   "pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,"+
                   "pasien.no_tlp,pasien.umur,pasien.pnd, pasien.keluarga, pasien.namakeluarga,"+
                   "penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,pasien.alamatpj,"+
                   "pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                   "where pasien.no_peserta=?");
                try {
                    ps.setString(1,response.path("peserta").path("noKartu").asText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        statuspasien="lama";                        
                        TNo.setText(rs.getString("no_rkm_medis"));
                        TNm.setText(rs.getString("nm_pasien"));
                        CMbGd.setSelectedItem(rs.getString("gol_darah"));
                        TTmp.setText(rs.getString("tmp_lahir"));
                        cmbAgama.setSelectedItem(rs.getString("agama"));
                        CmbStts.setSelectedItem(rs.getString("stts_nikah"));
                        Alamat.setText(rs.getString("alamat"));
                        AlamatPj.setText(rs.getString("alamatpj"));
                        Pekerjaan.setText(rs.getString("pekerjaan"));
                        PekerjaanPj.setText(rs.getString("pekerjaanpj"));
                        TTlp.setText(rs.getString("no_tlp"));
                        Saudara.setText(rs.getString("namakeluarga"));     
                        NmIbu.setText(rs.getString("nm_ibu"));
                        Kelurahan.setText(rs.getString("nm_kel"));      
                        Kecamatan.setText(rs.getString("nm_kec"));      
                        Kabupaten.setText(rs.getString("nm_kab")); 
                        KelurahanPj.setText(rs.getString("kelurahanpj"));      
                        KecamatanPj.setText(rs.getString("kecamatanpj"));      
                        KabupatenPj.setText(rs.getString("kabupatenpj")); 
                        switch (rs.getString("namakeluarga")) {
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
                    }else{
                        statuspasien="baru";
                        autoNomor();
                    }
                } catch (Exception e) {
                    System.out.println("Notif Cari Pasien : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }else {
                emptTeks();
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }  
    
    private void isForm(){
        if(ChkCari.isSelected()==true){
            panelCari.setVisible(true);
            FormInput.setPreferredSize(new Dimension(955,430));
            if(internalFrame1.getHeight()>530){
                PanelInput.setPreferredSize(new Dimension(WIDTH,510));
                scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                if(internalFrame1.getWidth()<960){
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    FormKelengkapanPasien.setPreferredSize(new Dimension(952,245));
                    FormKelengkapanSEP.setPreferredSize(new Dimension(952,184));
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                    scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-14,245));
                    FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-14,184));
                }
            }else{
                scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-20));
                if(internalFrame1.getWidth()<960){
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    FormKelengkapanPasien.setPreferredSize(new Dimension(952,245));
                    FormKelengkapanSEP.setPreferredSize(new Dimension(952,184));
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,245));
                    FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,184));
                }
            }
            isNumber();
            BtnSimpan.setVisible(true);
            TTmp.requestFocus();
        }else if(ChkCari.isSelected()==false){           
            panelCari.setVisible(false);      
            PanelInput.setPreferredSize(new Dimension(WIDTH,77));
            BtnSimpan.setVisible(false);
        }
    }
    
    public void emptTeks() {
        TNo.setText("");
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
        TUmur.setText("");
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
        TNoReg.setText("");
        TNoRw.setText("");
        Kdpnj.setText("");
        nmpnj.setText("");
        TanggalSEP.setDate(new Date());
        TanggalRujuk.setDate(new Date());
        NoRujukan.setText("");
        KdPpkRujukan.setText("");
        NmPpkRujukan.setText("");
        Catatan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Kelas.setSelectedIndex(0);
        LakaLantas.setSelectedIndex(1);
        LokasiLaka.setText("");
        JenisPelayanan.setSelectedIndex(1);
        JenisPelayananItemStateChanged(null);
        statuspasien="";
           
        TNo.requestFocus();
    }
    
    private void isNumber(){     
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"' ","BR/"+dateformat.format(TanggalSEP.getDate())+"/",4,NoBalasan); 
        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"'","",3,TNoReg); 
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"' ",dateformat.format(TanggalSEP.getDate())+"/",6,TNoRw);                              
    }
    
    private void isPoli(){
        try {
            ps=koneksi.prepareStatement("select kd_poli, nm_poli, registrasi, registrasilama "+
                " from poliklinik where kd_poli like ? order by nm_poli");
            try{            
                ps.setString(1,"%"+KdPoli.getText().trim()+"%");
                rs=ps.executeQuery();
                if(rs.next()){
                    kdpoli.setText(rs.getString("kd_poli"));
                    TPoli.setText(rs.getString("nm_poli"));
                    if(statuspasien.equals("lama")){
                        TBiaya.setText(rs.getString("registrasilama"));
                    }else if(statuspasien.equals("baru")){
                        TBiaya.setText(rs.getString("registrasi"));
                    }
                }else{
                    if(Sequel.menyimpantf2("poliklinik","?,?,?,?","Poli",4,new String[]{KdPoli.getText(),NmPoli.getText(),"0","0"})==true){
                        kdpoli.setText(KdPoli.getText());
                        TPoli.setText(NmPoli.getText());
                        TBiaya.setText("0");
                    }
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
            }
        } catch (Exception e) {
            System.out.println("Notif Cari Poli : "+e);
        }
    }
 
    private void autoNomor() {        
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
    
    private void insertSEP(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            URL = prop.getProperty("URLAPIBPJS")+"/SEP/insert";	

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            requestJson ="{" +
                          "\"request\":" +
                             "{" +
                                "\"t_sep\":" +
                                   "{" +
                                    "\"noKartu\":\""+TNoPeserta.getText()+"\"," +
                                    "\"tglSep\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19)+"\"," +
                                    "\"tglRujukan\":\""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19)+"\"," +
                                    "\"noRujukan\":\""+NoRujukan.getText()+"\"," +
                                    "\"ppkRujukan\":\""+KdPpkRujukan.getText()+"\"," +
                                    "\"ppkPelayanan\":\""+KdPPK.getText()+"\"," +
                                    "\"jnsPelayanan\":\""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"catatan\":\""+Catatan.getText()+"\"," +
                                    "\"diagAwal\":\""+KdPenyakit.getText()+"\"," +
                                    "\"poliTujuan\":\""+KdPoli.getText()+"\"," +
                                    "\"klsRawat\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"lakaLantas\":\""+LakaLantas.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"lokasiLaka\":\""+LokasiLaka.getText()+"\"," +
                                    "\"user\":\""+user+"\"," +
                                    "\"noMr\":\""+TNo.getText()+"\"" +
                                   "}" +
                             "}" +
                         "}";
            HttpEntity requestEntity = new HttpEntity(requestJson,headers);
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metadata");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            JsonNode response = root.path("response");
            if(nameNode.path("message").asText().equals("OK")){
                nosep=response.asText();
                if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",26,new String[]{
                    response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                    Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                    NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                    JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                    NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                    LakaLantas.getSelectedItem().toString().substring(0,1),LokasiLaka.getText(),user, 
                    TNo.getText(),TNm.getText(),Valid.SetTgl(DTPLahir.getSelectedItem()+""),nmpnj.getText(),CmbJk.getSelectedItem().toString(),NoKartu.getText(),"0000-00-00 00:00:00"
                })==true){
                    if(JenisPelayanan.getSelectedIndex()==1){
                        try {
                            prop.loadFromXML(new FileInputStream("setting/database.xml"));
                            URL = prop.getProperty("URLAPIBPJS")+"/Sep/updtglplg";	

                            HttpHeaders headers2 = new HttpHeaders();
                            headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            headers2.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                            headers2.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                            headers2.add("X-Signature",api.getHmac());
                            requestJson ="{" +
                                          "\"request\":" +
                                             "{" +
                                                "\"t_sep\":" +
                                                   "{" +
                                                    "\"noSep\":\""+response.asText()+"\"," +
                                                    "\"tglPlg\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19)+"\"," +
                                                    "\"ppkPelayanan\":\""+KdPPK.getText()+"\"," +                                            
                                                   "}" +
                                             "}" +
                                         "}";
                            HttpEntity requestEntity2 = new HttpEntity(requestJson,headers2);
                            RestTemplate rest2 = new RestTemplate();
                            ObjectMapper mapper2 = new ObjectMapper();
                            JsonNode root2 = mapper2.readTree(rest2.exchange(URL, HttpMethod.PUT, requestEntity2, String.class).getBody());
                            JsonNode nameNode2 = root2.path("metadata");
                            System.out.println("code : "+nameNode2.path("code").asText());
                            System.out.println("message : "+nameNode2.path("message").asText());
                            JsonNode response2 = root2.path("response");
                            if(nameNode2.path("message").asText().equals("OK")){
                                Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                                     Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                                     response.asText()
                                }); 
                            }else{
                                JOptionPane.showMessageDialog(null,nameNode2.path("message").asText());
                            }
                        }catch (Exception ex) {
                            System.out.println("Notifikasi Bridging : "+ex);
                        }
                    }    
                    JOptionPane.showMessageDialog(null,"Proses Selesai...!");
                    if(!nosep.equals("")){
                        DlgPilihanCetakDokumen pilihan=new DlgPilihanCetakDokumen(null,true);
                        pilihan.tampil();
                        if(JenisPelayanan.getSelectedIndex()==1){
                            pilihan.setNoRm(TNoRw.getText(),TNo.getText(),nosep,TNoReg.getText(),TPoli.getText(),nmpnj.getText(), 
                                    TDokter.getText(),TNm.getText(),Alamat.getText()+", "+Kelurahan.getText()+", "+Kecamatan.getText()+", "+Kabupaten.getText(), 
                                    Saudara.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),"ranap");
                        }else{
                            pilihan.setNoRm(TNoRw.getText(),TNo.getText(),nosep,TNoReg.getText(),TPoli.getText(),nmpnj.getText(), 
                                    TDokter.getText(),TNm.getText(),Alamat.getText()+", "+Kelurahan.getText()+", "+Kecamatan.getText()+", "+Kabupaten.getText(), 
                                    Saudara.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),"ralan");
                        }
                        
                        pilihan.setSize(500,400);
                        pilihan.setLocationRelativeTo(internalFrame1);
                        pilihan.setVisible(true);
                    }
                }                     
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi Bridging : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    private void inputRegistrasi(){
        try {
            pscariumur=koneksi.prepareStatement(
                "select TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "+
                "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "+
                "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "+
                "from pasien where no_rkm_medis=?");
            try {
                pscariumur.setString(1,TNo.getText());                            
                rs=pscariumur.executeQuery();
                if(rs.next()){
                    umur="0";
                    sttsumur="Th";
                    if(rs.getInt("tahun")>0){
                        umur=rs.getString("tahun");
                        sttsumur="Th";
                    }else if(rs.getInt("tahun")==0){
                        if(rs.getInt("bulan")>0){
                            umur=rs.getString("bulan");
                            sttsumur="Bl";
                        }else if(rs.getInt("bulan")==0){
                            umur=rs.getString("hari");
                            sttsumur="Hr";
                        }
                    }                                
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Umur : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pscariumur!=null){
                    pscariumur.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        if(JenisPelayanan.getSelectedIndex()==1){
            isNumber();
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",17,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),kdpoli.getText(),Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,TBiaya.getText(),"Belum","Lama","Ralan",Kdpnj.getText(),umur,sttsumur
                })==true){
                    UpdateUmur();
                    Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");             
                    Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdPenyakit.getText(),NmPenyakit.getText(),NmPenyakit.getText(),"-","-","Tidak Menular"});
                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?","Penyakit",4,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1"});
                    insertSEP();
            }
        }else if(JenisPelayanan.getSelectedIndex()==0){
            isNumber();
            Sequel.menyimpan("poliklinik","?,?,?,?",4,new String[]{"IGDK","Unit IGD","0","0"});
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",17,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),"IGDK",Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,Sequel.cariIsi("select registrasilama from poliklinik where kd_poli='IGDK'"),"Belum","Lama","Ralan",Kdpnj.getText(),umur,sttsumur
                })==true){
                    UpdateUmur();
                    Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");                                     
                    Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdPenyakit.getText(),NmPenyakit.getText(),NmPenyakit.getText(),"-","-","Tidak Menular"});
                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?","Penyakit",4,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1"});                            
                    if(Sequel.menyimpantf2("kamar_inap","'"+TNoRw.getText()+"','"+KdPoli.getText()+"','"+TBiaya.getText()+"','"+
                            KdPenyakit.getText()+"','-','"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"','"+
                            TanggalSEP.getSelectedItem().toString().substring(11,19)+"','0000-00-00',"+
                            "'00:00:00','"+jmlhari+"','"+biaya+"','-'","No.Rawat"
                        )==true){
                            Sequel.mengedit("reg_periksa","no_rawat='"+TNoRw.getText()+"'","status_lanjut='Ranap'");
                            Sequel.mengedit("kamar","kd_kamar='"+KdPoli.getText()+"'","status='ISI'"); 
                            insertSEP();
                    }      
            }
        }
    }

    private void insertPasien() {
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
        Sequel.queryu3("insert into penjab values(?,?)",2,new String[]{Kdpnj.getText(),nmpnj.getText()});

        jmlhari=0;
        biaya=0;
        if(hariawal.equals("Yes")){
            jmlhari=1;
            biaya=Double.parseDouble(TBiaya.getText());
        }

        if(statuspasien.equals("baru")){
            autoNomor();
            if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",28,new String[]{
                    TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                    Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmur.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                    PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText()
                })==true){
                    if(ChkRM.isSelected()==true){
                        Sequel.queryu2("delete from set_no_rkm_medis");
                        Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                    }             

                    inputRegistrasi();                        
            }else{
                autoNomor();                    
            }
        }else if(statuspasien.equals("lama")){
            Valid.editTable(tabMode,"pasien","no_rkm_medis","?","no_rkm_medis=?,nm_pasien=?,no_ktp=?,jk=?,tmp_lahir=?,"+
                    "tgl_lahir=?,alamat=?,gol_darah=?,pekerjaan=?,stts_nikah=?,agama=?,tgl_daftar=?,no_tlp=?,umur=?"+
                    ",pnd=?,keluarga=?,namakeluarga=?,kd_pj=?,no_peserta=?,kd_kel=?,kd_kec=?,kd_kab=?,nm_ibu=?,pekerjaanpj=?,"+
                    "alamatpj=?,kelurahanpj=?,kecamatanpj=?,kabupatenpj=?",29,
                new String[]{TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),
                    Alamat.getText(),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmur.getText()+" Th",CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText()),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText()),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText()), 
                    NmIbu.getText(),PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),
                    KabupatenPj.getText(),TNo.getText()
            });     

            inputRegistrasi();                                
        }                
        Sequel.AutoComitTrue();
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{TNo.getText()});
    }
}

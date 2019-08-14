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
import fungsi.akses;
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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgBahasa;
import simrskhanza.DlgCacatFisik;
import laporan.DlgCariPenyakit;
import simrskhanza.DlgGolonganPolri;
import simrskhanza.DlgGolonganTNI;
import simrskhanza.DlgJabatanPolri;
import simrskhanza.DlgJabatanTNI;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgPangkatPolri;
import simrskhanza.DlgPangkatTNI;
import simrskhanza.DlgPasien;
import simrskhanza.DlgPenanggungJawab;
import simrskhanza.DlgPerusahaan;
import simrskhanza.DlgPilihanCetakDokumen;
import simrskhanza.DlgPropinsi;
import simrskhanza.DlgSatuanPolri;
import simrskhanza.DlgSatuanTNI;
import simrskhanza.DlgSuku;

/**
 *
 * @author windiarto nugroho
 */
public final class InhealthCekEligibilitas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final Properties prop = new Properties();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgPasien pasien=new DlgPasien(null,false);
    private InhealthCekReferensiFaskes faskes=new InhealthCekReferensiFaskes(null,false);
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private InhealthCekReferensiPoli poli=new InhealthCekReferensiPoli(null,false);
    private DlgKabupaten kab=new DlgKabupaten(null,false);
    private DlgKecamatan kec=new DlgKecamatan(null,false);
    private DlgKelurahan kel=new DlgKelurahan(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private InhealthCariReferensiJenpelRuang kamar=new InhealthCariReferensiJenpelRuang(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    public  DlgPropinsi propin=new DlgPropinsi(null,false);
    public  DlgPerusahaan perusahaan=new DlgPerusahaan(null,false);
    public  DlgBahasa bahasa=new DlgBahasa(null,false);
    public  DlgCacatFisik cacat=new DlgCacatFisik(null,false);
    public  DlgSuku suku=new DlgSuku(null,false);
    public  DlgGolonganTNI golongantni=new DlgGolonganTNI(null,false);
    public  DlgSatuanTNI satuantni=new DlgSatuanTNI(null,false);
    public  DlgPangkatTNI pangkattni=new DlgPangkatTNI(null,false);
    public  DlgJabatanTNI jabatantni=new DlgJabatanTNI(null,false);
    public  DlgGolonganPolri golonganpolri=new DlgGolonganPolri(null,false);
    public  DlgSatuanPolri satuanpolri=new DlgSatuanPolri(null,false);
    private DlgPilihanCetakDokumen pilihan=new DlgPilihanCetakDokumen(null,false);
            
    public  DlgPangkatPolri pangkatpolri=new DlgPangkatPolri(null,false);
    public  DlgJabatanPolri jabatanpolri=new DlgJabatanPolri(null,false);
    private int pilih=0,p_no_ktp=0,p_tmp_lahir=0,p_nm_ibu=0,p_alamat=0,
            p_pekerjaan=0,p_no_tlp=0,p_umur=0,p_namakeluarga=0,p_no_peserta=0,
            p_kelurahan=0,p_kecamatan=0,p_kabupaten=0,p_pekerjaanpj=0,
            p_alamatpj=0,p_kelurahanpj=0,p_kecamatanpj=0,p_kabupatenpj=0,jmlhari=0,
            p_propinsi=0,p_propinsipj=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String klg="SAUDARA",statuspasien="",pengurutan="",tahun="",bulan="",posisitahun="",awalantahun="",awalanbulan="",
            no_ktp="",tmp_lahir="",nm_ibu="",alamat="",pekerjaan="",no_tlp="",
            umur="",namakeluarga="",no_peserta="",kelurahan="",kecamatan="",sttsumur="",
            kabupaten="",pekerjaanpj="",alamatpj="",kelurahanpj="",kecamatanpj="",
            kabupatenpj="",hariawal="",requestJson,URL="",nosep="",user="",status="Baru",propinsi="",propinsipj="",
            tampilkantni=Sequel.cariIsi("select tampilkan_tni_polri from set_tni_polri");
    private PreparedStatement ps,pskelengkapan,pscariumur,pssetalamat,pstni,pspolri;
    private ResultSet rs,rs2;
    private double biaya=0;
    private Date lahir;
    private LocalDate today=LocalDate.now();
    private LocalDate birthday;
    private Period p;
    private long p2;
    

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public InhealthCekEligibilitas(java.awt.Frame parent, boolean modal) {
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
                    if(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya Nomor Kartu...!");
                    }else{
                        NoKartu.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),20).toString());
                    }                     
                }  
                if(pasien.getTable2().getSelectedRow()!= -1){ 
                    if(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),20).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya Nomor Kartu...!");
                    }else{
                        NoKartu.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),20).toString());
                    } 
                }
                if(pasien.getTable3().getSelectedRow()!= -1){ 
                    if(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),20).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya Nomor Kartu...!");
                    }else{
                        NoKartu.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),20).toString());
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
        
        pasien.getTable2().addKeyListener(new KeyListener() {
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
        
        pasien.getTable3().addKeyListener(new KeyListener() {
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
                    KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),1).toString());
                    NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),2).toString());
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
                    if(pilih==1){  
                        KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString());
                        NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    }else if(pilih==2){
                        KdPenyakit2.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString());
                        NmPenyakit2.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    }
                        
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
                    if(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),7).toString().equals("KOSONG")){
                        KdPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),0).toString());  
                        NmPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),2).toString());  
                        TBiaya.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),6).toString()); 
                        KdJenpel.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString());  
                        NmJenpel.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),4).toString());
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
        
        perusahaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(perusahaan.getTable().getSelectedRow()!= -1){
                        kdperusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),0).toString());
                        nmperusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),1).toString());
                    }  
                    kdperusahaan.requestFocus();
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
        
        perusahaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        perusahaan.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        golongantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(golongantni.getTable().getSelectedRow()!= -1){
                        kdgolongantni.setText(golongantni.getTable().getValueAt(golongantni.getTable().getSelectedRow(),0).toString());
                        nmgolongantni.setText(golongantni.getTable().getValueAt(golongantni.getTable().getSelectedRow(),1).toString());
                    }  
                    kdgolongantni.requestFocus();
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
        
        golongantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        golongantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jabatantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(jabatantni.getTable().getSelectedRow()!= -1){
                        kdjabatantni.setText(jabatantni.getTable().getValueAt(jabatantni.getTable().getSelectedRow(),0).toString());
                        nmjabatantni.setText(jabatantni.getTable().getValueAt(jabatantni.getTable().getSelectedRow(),1).toString());
                    }  
                    kdjabatantni.requestFocus();
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
        
        jabatantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        jabatantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        satuantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(satuantni.getTable().getSelectedRow()!= -1){
                        kdsatuantni.setText(satuantni.getTable().getValueAt(satuantni.getTable().getSelectedRow(),0).toString());
                        nmsatuantni.setText(satuantni.getTable().getValueAt(satuantni.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsatuantni.requestFocus();
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
        
        satuantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        satuantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pangkattni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(pangkattni.getTable().getSelectedRow()!= -1){
                        kdpangkattni.setText(pangkattni.getTable().getValueAt(pangkattni.getTable().getSelectedRow(),0).toString());
                        nmpangkattni.setText(pangkattni.getTable().getValueAt(pangkattni.getTable().getSelectedRow(),1).toString());
                    }  
                    kdpangkattni.requestFocus();
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
        
        pangkattni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pangkattni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        golonganpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(golonganpolri.getTable().getSelectedRow()!= -1){
                        kdgolonganpolri.setText(golonganpolri.getTable().getValueAt(golonganpolri.getTable().getSelectedRow(),0).toString());
                        nmgolonganpolri.setText(golonganpolri.getTable().getValueAt(golonganpolri.getTable().getSelectedRow(),1).toString());
                    }  
                    kdgolonganpolri.requestFocus();
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
        
        golonganpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        golonganpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jabatanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(jabatanpolri.getTable().getSelectedRow()!= -1){
                        kdjabatanpolri.setText(jabatanpolri.getTable().getValueAt(jabatanpolri.getTable().getSelectedRow(),0).toString());
                        nmjabatanpolri.setText(jabatanpolri.getTable().getValueAt(jabatanpolri.getTable().getSelectedRow(),1).toString());
                    }  
                    kdjabatanpolri.requestFocus();
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
        
        jabatanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        jabatanpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        satuanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(satuanpolri.getTable().getSelectedRow()!= -1){
                        kdsatuanpolri.setText(satuanpolri.getTable().getValueAt(satuanpolri.getTable().getSelectedRow(),0).toString());
                        nmsatuanpolri.setText(satuanpolri.getTable().getValueAt(satuanpolri.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsatuanpolri.requestFocus();
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
        
        satuanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        satuanpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pangkatpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(pangkatpolri.getTable().getSelectedRow()!= -1){
                        kdpangkatpolri.setText(pangkatpolri.getTable().getValueAt(pangkatpolri.getTable().getSelectedRow(),0).toString());
                        nmpangkatpolri.setText(pangkatpolri.getTable().getValueAt(pangkatpolri.getTable().getSelectedRow(),1).toString());
                    }  
                    kdpangkatpolri.requestFocus();
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
        
        pangkatpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pangkatpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        bahasa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(bahasa.getTable().getSelectedRow()!= -1){
                        kdbahasa.setText(bahasa.getTable().getValueAt(bahasa.getTable().getSelectedRow(),0).toString());
                        nmbahasa.setText(bahasa.getTable().getValueAt(bahasa.getTable().getSelectedRow(),1).toString());
                    }  
                    kdbahasa.requestFocus();
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
        
        bahasa.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        bahasa.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        suku.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(suku.getTable().getSelectedRow()!= -1){
                        kdsuku.setText(suku.getTable().getValueAt(suku.getTable().getSelectedRow(),0).toString());
                        nmsukubangsa.setText(suku.getTable().getValueAt(suku.getTable().getSelectedRow(),1).toString());
                    }  
                    kdsuku.requestFocus();
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
        
        suku.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        suku.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        propin.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(propin.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Propinsi.setText(propin.getTable().getValueAt(propin.getTable().getSelectedRow(),0).toString());
                            Propinsi.requestFocus();
                        }else if(pilih==2){                    
                            PropinsiPj.setText(propin.getTable().getValueAt(propin.getTable().getSelectedRow(),0).toString());
                            PropinsiPj.requestFocus();
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
        
        cacat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(cacat.getTable().getSelectedRow()!= -1){
                        kdcacat.setText(cacat.getTable().getValueAt(cacat.getTable().getSelectedRow(),0).toString());
                        nmcacat.setText(cacat.getTable().getValueAt(cacat.getTable().getSelectedRow(),1).toString());
                    }  
                    kdcacat.requestFocus();
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
        
        cacat.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPasien")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        cacat.dispose();
                    }                
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
        Propinsi.setDocument(new batasInput((byte)30).getFilter(Propinsi));
        PropinsiPj.setDocument(new batasInput((byte)30).getFilter(PropinsiPj));
        EMail.setDocument(new batasInput((byte)50).getFilter(EMail));
        NIP.setDocument(new batasInput((byte)30).getFilter(NIP));
        TNoReg.setDocument(new batasInput((byte)8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        NoRujukan.setDocument(new batasInput((byte)20).getKata(NoRujukan));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));
        LokasiLaka.setDocument(new batasInput((byte)100).getKata(LokasiLaka));
        try{
            KdPPK.setText(Sequel.cariIsi("select kode_ppkinhealth from setting")); 
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting")); 
            
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
                    Propinsi.setEditable(rs.getBoolean("propinsi"));
                    PropinsiPj.setEditable(rs.getBoolean("propinsi"));
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
                    propinsi=rs.getString("propinsi");
                    p_propinsi=rs.getInt("p_propinsi");
                    propinsipj=rs.getString("propinsipj");
                    p_propinsipj=rs.getInt("p_propinsipj");
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
            user=akses.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=akses.getkode();
        }
        
        if(tampilkantni.equals("Yes")){
            chkPolri.setVisible(true);
            nmgolonganpolri.setVisible(true);
            BtnGolonganPolri.setVisible(true);
            nmsatuanpolri.setVisible(true);
            BtnSatuanPolri.setVisible(true);
            nmpangkatpolri.setVisible(true);
            BtnPangkatPolri.setVisible(true);
            nmjabatanpolri.setVisible(true);
            BtnJabatanPolri.setVisible(true);
            chkTNI.setVisible(true);
            nmgolongantni.setVisible(true);
            BtnGolonganTNI.setVisible(true);
            nmsatuantni.setVisible(true);
            BtnSatuanTNI.setVisible(true);
            nmpangkattni.setVisible(true);
            BtnPangkatTNI.setVisible(true);
            nmjabatantni.setVisible(true);
            BtnJabatanTNI.setVisible(true);
            LabelGolonganPolri.setVisible(true);
            LabelGolonganTNI.setVisible(true);
            LabelSatuanPolri.setVisible(true);
            LabelSatuanTNI.setVisible(true);
            LabelPangkatPolri.setVisible(true);
            LabelPangkatTNI.setVisible(true);
            LabelJabatanPolri.setVisible(true);
            LabelJabatanTNI.setVisible(true);
        }else{
            chkPolri.setVisible(false);
            nmgolonganpolri.setVisible(false);
            BtnGolonganPolri.setVisible(false);
            nmsatuanpolri.setVisible(false);
            BtnSatuanPolri.setVisible(false);
            nmpangkatpolri.setVisible(false);
            BtnPangkatPolri.setVisible(false);
            nmjabatanpolri.setVisible(false);
            BtnJabatanPolri.setVisible(false);
            chkTNI.setVisible(false);
            nmgolongantni.setVisible(false);
            BtnGolonganTNI.setVisible(false);
            nmsatuantni.setVisible(false);
            BtnSatuanTNI.setVisible(false);
            nmpangkattni.setVisible(false);
            BtnPangkatTNI.setVisible(false);
            nmjabatantni.setVisible(false);
            BtnJabatanTNI.setVisible(false);     
            LabelGolonganPolri.setVisible(false);
            LabelGolonganTNI.setVisible(false);
            LabelSatuanPolri.setVisible(false);
            LabelSatuanTNI.setVisible(false);
            LabelPangkatPolri.setVisible(false);
            LabelPangkatTNI.setVisible(false);
            LabelJabatanPolri.setVisible(false);
            LabelJabatanTNI.setVisible(false);
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
        DTPLahir = new widget.Tanggal();
        TUmur = new widget.TextBox();
        TNoPeserta = new widget.TextBox();
        NoRm = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        kdpoli = new widget.TextBox();
        TPoli = new widget.TextBox();
        TBiaya = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDocument = new javax.swing.JMenuItem();
        NoBalasan = new widget.TextBox();
        kdsuku = new widget.TextBox();
        kdbahasa = new widget.TextBox();
        kdgolongantni = new widget.TextBox();
        kdsatuantni = new widget.TextBox();
        kdpangkattni = new widget.TextBox();
        kdjabatantni = new widget.TextBox();
        kdgolonganpolri = new widget.TextBox();
        kdsatuanpolri = new widget.TextBox();
        kdpangkatpolri = new widget.TextBox();
        kdjabatanpolri = new widget.TextBox();
        kdperusahaan = new widget.TextBox();
        kdcacat = new widget.TextBox();
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
        jLabel40 = new widget.Label();
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
        jLabel41 = new widget.Label();
        nmsukubangsa = new widget.TextBox();
        BtnSuku = new widget.Button();
        jLabel42 = new widget.Label();
        nmbahasa = new widget.TextBox();
        BtnBahasa = new widget.Button();
        jLabel43 = new widget.Label();
        nmcacat = new widget.TextBox();
        BtnCacat = new widget.Button();
        jLabel44 = new widget.Label();
        nmperusahaan = new widget.TextBox();
        BtnPerusahaan = new widget.Button();
        jLabel45 = new widget.Label();
        NIP = new widget.TextBox();
        jLabel46 = new widget.Label();
        EMail = new widget.TextBox();
        Propinsi = new widget.TextBox();
        BtnPropinsi = new widget.Button();
        PropinsiPj = new widget.TextBox();
        btnPropinsiPj = new widget.Button();
        chkTNI = new widget.CekBox();
        LabelGolonganTNI = new widget.Label();
        nmgolongantni = new widget.TextBox();
        BtnGolonganTNI = new widget.Button();
        BtnSatuanTNI = new widget.Button();
        nmsatuantni = new widget.TextBox();
        LabelSatuanTNI = new widget.Label();
        LabelPangkatTNI = new widget.Label();
        nmpangkattni = new widget.TextBox();
        BtnPangkatTNI = new widget.Button();
        BtnJabatanTNI = new widget.Button();
        nmjabatantni = new widget.TextBox();
        LabelJabatanTNI = new widget.Label();
        chkPolri = new widget.CekBox();
        LabelGolonganPolri = new widget.Label();
        nmgolonganpolri = new widget.TextBox();
        BtnGolonganPolri = new widget.Button();
        BtnSatuanPolri = new widget.Button();
        nmsatuanpolri = new widget.TextBox();
        LabelSatuanPolri = new widget.Label();
        LabelPangkatPolri = new widget.Label();
        nmpangkatpolri = new widget.TextBox();
        BtnPangkatPolri = new widget.Button();
        BtnJabatanPolri = new widget.Button();
        nmjabatanpolri = new widget.TextBox();
        LabelJabatanPolri = new widget.Label();
        jLabel38 = new widget.Label();
        TKtp = new widget.TextBox();
        jLabel18 = new widget.Label();
        CmbJk = new widget.ComboBox();
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
        jLabel39 = new widget.Label();
        KdPenyakit2 = new widget.TextBox();
        NmPenyakit2 = new widget.TextBox();
        btnDiagnosa1 = new widget.Button();
        LabelJenpel = new widget.Label();
        KdJenpel = new widget.TextBox();
        NmJenpel = new widget.TextBox();
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

        DTPLahir.setEditable(false);
        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
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

        MnDocument.setBackground(new java.awt.Color(255, 255, 254));
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

        kdsuku.setName("kdsuku"); // NOI18N
        kdsuku.setPreferredSize(new java.awt.Dimension(207, 23));

        kdbahasa.setName("kdbahasa"); // NOI18N
        kdbahasa.setPreferredSize(new java.awt.Dimension(207, 23));

        kdgolongantni.setName("kdgolongantni"); // NOI18N
        kdgolongantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdsatuantni.setName("kdsatuantni"); // NOI18N
        kdsatuantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdpangkattni.setName("kdpangkattni"); // NOI18N
        kdpangkattni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdjabatantni.setName("kdjabatantni"); // NOI18N
        kdjabatantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdgolonganpolri.setName("kdgolonganpolri"); // NOI18N
        kdgolonganpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdsatuanpolri.setName("kdsatuanpolri"); // NOI18N
        kdsatuanpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdpangkatpolri.setName("kdpangkatpolri"); // NOI18N
        kdpangkatpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdjabatanpolri.setName("kdjabatanpolri"); // NOI18N
        kdjabatanpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdperusahaan.setEditable(false);
        kdperusahaan.setHighlighter(null);
        kdperusahaan.setName("kdperusahaan"); // NOI18N
        kdperusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdperusahaanKeyPressed(evt);
            }
        });

        kdcacat.setEditable(false);
        kdcacat.setHighlighter(null);
        kdcacat.setName("kdcacat"); // NOI18N
        kdcacat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdcacatKeyPressed(evt);
            }
        });

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Eligibilitas Peserta Inhealth ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(200, 807));
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

        FormKelengkapanPasien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "::[ Kelengkapan Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        FormKelengkapanPasien.setName("FormKelengkapanPasien"); // NOI18N
        FormKelengkapanPasien.setOpaque(false);
        FormKelengkapanPasien.setPreferredSize(new java.awt.Dimension(1000, 515));
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

        jLabel40.setText("Agama :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormKelengkapanPasien.add(jLabel40);
        jLabel40.setBounds(3, 85, 100, 23);

        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "KATOLIK", "HINDU", "BUDHA", "KONG HU CHU", "-" }));
        cmbAgama.setLightWeightPopupEnabled(false);
        cmbAgama.setName("cmbAgama"); // NOI18N
        cmbAgama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAgamaKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(cmbAgama);
        cmbAgama.setBounds(107, 85, 120, 23);

        jLabel19.setText("Status :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormKelengkapanPasien.add(jLabel19);
        jLabel19.setBounds(235, 85, 45, 23);

        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CmbStts);
        CmbStts.setBounds(285, 85, 130, 23);

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
        Alamat.setBounds(534, 25, 375, 23);

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

        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
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
        Saudara.setBounds(534, 145, 142, 23);

        R5.setSelected(true);
        R5.setText("Saudara");
        R5.setIconTextGap(0);
        R5.setName("R5"); // NOI18N
        FormKelengkapanPasien.add(R5);
        R5.setBounds(750, 115, 60, 23);

        R4.setText("Suami");
        R4.setIconTextGap(0);
        R4.setName("R4"); // NOI18N
        FormKelengkapanPasien.add(R4);
        R4.setBounds(690, 115, 50, 23);

        R3.setText("Istri");
        R3.setIconTextGap(0);
        R3.setName("R3"); // NOI18N
        FormKelengkapanPasien.add(R3);
        R3.setBounds(640, 115, 44, 23);

        R2.setText("Ibu");
        R2.setIconTextGap(0);
        R2.setName("R2"); // NOI18N
        FormKelengkapanPasien.add(R2);
        R2.setBounds(592, 115, 40, 23);

        R1.setText("Ayah");
        R1.setIconTextGap(0);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(40, 20));
        FormKelengkapanPasien.add(R1);
        R1.setBounds(534, 115, 46, 23);

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
        Kelurahan.setBounds(534, 55, 156, 23);

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
        Kecamatan.setBounds(723, 55, 156, 23);

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
        Kabupaten.setBounds(534, 85, 156, 23);

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
        BtnKelurahan.setBounds(692, 55, 28, 23);

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
        BtnKecamatan.setBounds(881, 55, 28, 23);

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
        BtnKabupaten.setBounds(692, 85, 28, 23);

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
        jLabel26.setBounds(430, 115, 100, 23);

        jLabel27.setText("Png. Jawab :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormKelengkapanPasien.add(jLabel27);
        jLabel27.setBounds(430, 145, 100, 23);

        PekerjaanPj.setName("PekerjaanPj"); // NOI18N
        PekerjaanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(PekerjaanPj);
        PekerjaanPj.setBounds(767, 145, 142, 23);

        jLabel28.setText("Pekerjaan P.J. :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormKelengkapanPasien.add(jLabel28);
        jLabel28.setBounds(680, 145, 83, 23);

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
        AlamatPj.setBounds(534, 175, 375, 23);

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
        KecamatanPj.setBounds(723, 205, 156, 23);

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
        BtnKecamatanPj.setBounds(881, 205, 28, 23);

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
        KabupatenPj.setBounds(534, 235, 156, 23);

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
        BtnKabupatenPj.setBounds(692, 235, 28, 23);

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
        BtnKelurahanPj.setBounds(692, 205, 28, 23);

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
        KelurahanPj.setBounds(534, 205, 156, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
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

        R6.setText("Anak");
        R6.setIconTextGap(0);
        R6.setName("R6"); // NOI18N
        FormKelengkapanPasien.add(R6);
        R6.setBounds(825, 115, 60, 23);

        jLabel41.setText("Suku/Bangsa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormKelengkapanPasien.add(jLabel41);
        jLabel41.setBounds(3, 265, 100, 23);

        nmsukubangsa.setEditable(false);
        nmsukubangsa.setName("nmsukubangsa"); // NOI18N
        FormKelengkapanPasien.add(nmsukubangsa);
        nmsukubangsa.setBounds(107, 265, 278, 23);

        BtnSuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSuku.setMnemonic('1');
        BtnSuku.setToolTipText("ALt+1");
        BtnSuku.setName("BtnSuku"); // NOI18N
        BtnSuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSukuActionPerformed(evt);
            }
        });
        BtnSuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSukuKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnSuku);
        BtnSuku.setBounds(387, 265, 28, 23);

        jLabel42.setText("Bahasa :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormKelengkapanPasien.add(jLabel42);
        jLabel42.setBounds(3, 295, 100, 23);

        nmbahasa.setEditable(false);
        nmbahasa.setName("nmbahasa"); // NOI18N
        FormKelengkapanPasien.add(nmbahasa);
        nmbahasa.setBounds(107, 295, 278, 23);

        BtnBahasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBahasa.setMnemonic('1');
        BtnBahasa.setToolTipText("ALt+1");
        BtnBahasa.setName("BtnBahasa"); // NOI18N
        BtnBahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBahasaActionPerformed(evt);
            }
        });
        BtnBahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBahasaKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnBahasa);
        BtnBahasa.setBounds(387, 295, 28, 23);

        jLabel43.setText("Cacat Fisik :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormKelengkapanPasien.add(jLabel43);
        jLabel43.setBounds(3, 325, 100, 23);

        nmcacat.setEditable(false);
        nmcacat.setName("nmcacat"); // NOI18N
        FormKelengkapanPasien.add(nmcacat);
        nmcacat.setBounds(107, 325, 278, 23);

        BtnCacat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCacat.setMnemonic('1');
        BtnCacat.setToolTipText("ALt+1");
        BtnCacat.setName("BtnCacat"); // NOI18N
        BtnCacat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCacatActionPerformed(evt);
            }
        });
        BtnCacat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCacatKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnCacat);
        BtnCacat.setBounds(387, 325, 28, 23);

        jLabel44.setText("Instansi Pasien :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormKelengkapanPasien.add(jLabel44);
        jLabel44.setBounds(430, 265, 100, 23);

        nmperusahaan.setEditable(false);
        nmperusahaan.setName("nmperusahaan"); // NOI18N
        FormKelengkapanPasien.add(nmperusahaan);
        nmperusahaan.setBounds(534, 265, 340, 23);

        BtnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerusahaan.setMnemonic('1');
        BtnPerusahaan.setToolTipText("ALt+1");
        BtnPerusahaan.setName("BtnPerusahaan"); // NOI18N
        BtnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerusahaanActionPerformed(evt);
            }
        });
        BtnPerusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerusahaanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPerusahaan);
        BtnPerusahaan.setBounds(880, 265, 28, 23);

        jLabel45.setText("NIP/NRP :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormKelengkapanPasien.add(jLabel45);
        jLabel45.setBounds(690, 295, 84, 23);

        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(NIP);
        NIP.setBounds(778, 295, 130, 23);

        jLabel46.setText("Email :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormKelengkapanPasien.add(jLabel46);
        jLabel46.setBounds(430, 295, 100, 23);

        EMail.setHighlighter(null);
        EMail.setName("EMail"); // NOI18N
        EMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EMailKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(EMail);
        EMail.setBounds(534, 295, 161, 23);

        Propinsi.setText("PROPINSI");
        Propinsi.setHighlighter(null);
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiMouseMoved(evt);
            }
        });
        Propinsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiMouseExited(evt);
            }
        });
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Propinsi);
        Propinsi.setBounds(723, 85, 156, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('4');
        BtnPropinsi.setToolTipText("ALt+4");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPropinsi);
        BtnPropinsi.setBounds(881, 85, 28, 23);

        PropinsiPj.setText("PROPINSI");
        PropinsiPj.setHighlighter(null);
        PropinsiPj.setName("PropinsiPj"); // NOI18N
        PropinsiPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiPjMouseMoved(evt);
            }
        });
        PropinsiPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiPjMouseExited(evt);
            }
        });
        PropinsiPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(PropinsiPj);
        PropinsiPj.setBounds(723, 235, 156, 23);

        btnPropinsiPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPropinsiPj.setMnemonic('4');
        btnPropinsiPj.setToolTipText("ALt+4");
        btnPropinsiPj.setName("btnPropinsiPj"); // NOI18N
        btnPropinsiPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropinsiPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(btnPropinsiPj);
        btnPropinsiPj.setBounds(881, 235, 28, 23);

        chkTNI.setText("Anggota TNI :");
        chkTNI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTNI.setName("chkTNI"); // NOI18N
        chkTNI.setOpaque(false);
        chkTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTNIActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(chkTNI);
        chkTNI.setBounds(10, 365, 95, 23);

        LabelGolonganTNI.setText("Golongan :");
        LabelGolonganTNI.setName("LabelGolonganTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelGolonganTNI);
        LabelGolonganTNI.setBounds(4, 385, 112, 23);

        nmgolongantni.setEditable(false);
        nmgolongantni.setName("nmgolongantni"); // NOI18N
        FormKelengkapanPasien.add(nmgolongantni);
        nmgolongantni.setBounds(120, 385, 250, 23);

        BtnGolonganTNI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolonganTNI.setMnemonic('1');
        BtnGolonganTNI.setToolTipText("ALt+1");
        BtnGolonganTNI.setEnabled(false);
        BtnGolonganTNI.setName("BtnGolonganTNI"); // NOI18N
        BtnGolonganTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganTNIActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnGolonganTNI);
        BtnGolonganTNI.setBounds(370, 385, 28, 23);

        BtnSatuanTNI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuanTNI.setMnemonic('1');
        BtnSatuanTNI.setToolTipText("ALt+1");
        BtnSatuanTNI.setEnabled(false);
        BtnSatuanTNI.setName("BtnSatuanTNI"); // NOI18N
        BtnSatuanTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuanTNIActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnSatuanTNI);
        BtnSatuanTNI.setBounds(370, 415, 28, 23);

        nmsatuantni.setEditable(false);
        nmsatuantni.setName("nmsatuantni"); // NOI18N
        FormKelengkapanPasien.add(nmsatuantni);
        nmsatuantni.setBounds(120, 415, 250, 23);

        LabelSatuanTNI.setText("Kesatuan :");
        LabelSatuanTNI.setName("LabelSatuanTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelSatuanTNI);
        LabelSatuanTNI.setBounds(4, 415, 112, 23);

        LabelPangkatTNI.setText("Pangkat :");
        LabelPangkatTNI.setName("LabelPangkatTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelPangkatTNI);
        LabelPangkatTNI.setBounds(4, 445, 112, 23);

        nmpangkattni.setEditable(false);
        nmpangkattni.setName("nmpangkattni"); // NOI18N
        FormKelengkapanPasien.add(nmpangkattni);
        nmpangkattni.setBounds(120, 445, 250, 23);

        BtnPangkatTNI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPangkatTNI.setMnemonic('1');
        BtnPangkatTNI.setToolTipText("ALt+1");
        BtnPangkatTNI.setEnabled(false);
        BtnPangkatTNI.setName("BtnPangkatTNI"); // NOI18N
        BtnPangkatTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPangkatTNIActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPangkatTNI);
        BtnPangkatTNI.setBounds(370, 445, 28, 23);

        BtnJabatanTNI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJabatanTNI.setMnemonic('1');
        BtnJabatanTNI.setToolTipText("ALt+1");
        BtnJabatanTNI.setEnabled(false);
        BtnJabatanTNI.setName("BtnJabatanTNI"); // NOI18N
        BtnJabatanTNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJabatanTNIActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnJabatanTNI);
        BtnJabatanTNI.setBounds(370, 475, 28, 23);

        nmjabatantni.setEditable(false);
        nmjabatantni.setName("nmjabatantni"); // NOI18N
        FormKelengkapanPasien.add(nmjabatantni);
        nmjabatantni.setBounds(120, 475, 250, 23);

        LabelJabatanTNI.setText("Jabatan :");
        LabelJabatanTNI.setName("LabelJabatanTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelJabatanTNI);
        LabelJabatanTNI.setBounds(4, 475, 112, 23);

        chkPolri.setText("Anggota POLRI :");
        chkPolri.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkPolri.setName("chkPolri"); // NOI18N
        chkPolri.setOpaque(false);
        chkPolri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPolriActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(chkPolri);
        chkPolri.setBounds(440, 365, 130, 23);

        LabelGolonganPolri.setText("Golongan :");
        LabelGolonganPolri.setName("LabelGolonganPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelGolonganPolri);
        LabelGolonganPolri.setBounds(441, 385, 135, 23);

        nmgolonganpolri.setEditable(false);
        nmgolonganpolri.setName("nmgolonganpolri"); // NOI18N
        FormKelengkapanPasien.add(nmgolonganpolri);
        nmgolonganpolri.setBounds(580, 385, 273, 23);

        BtnGolonganPolri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolonganPolri.setMnemonic('1');
        BtnGolonganPolri.setToolTipText("ALt+1");
        BtnGolonganPolri.setEnabled(false);
        BtnGolonganPolri.setName("BtnGolonganPolri"); // NOI18N
        BtnGolonganPolri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganPolriActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnGolonganPolri);
        BtnGolonganPolri.setBounds(855, 385, 28, 23);

        BtnSatuanPolri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuanPolri.setMnemonic('1');
        BtnSatuanPolri.setToolTipText("ALt+1");
        BtnSatuanPolri.setEnabled(false);
        BtnSatuanPolri.setName("BtnSatuanPolri"); // NOI18N
        BtnSatuanPolri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuanPolriActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnSatuanPolri);
        BtnSatuanPolri.setBounds(855, 415, 28, 23);

        nmsatuanpolri.setEditable(false);
        nmsatuanpolri.setName("nmsatuanpolri"); // NOI18N
        FormKelengkapanPasien.add(nmsatuanpolri);
        nmsatuanpolri.setBounds(580, 415, 273, 23);

        LabelSatuanPolri.setText("Kesatuan :");
        LabelSatuanPolri.setName("LabelSatuanPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelSatuanPolri);
        LabelSatuanPolri.setBounds(441, 415, 135, 23);

        LabelPangkatPolri.setText("Pangkat :");
        LabelPangkatPolri.setName("LabelPangkatPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelPangkatPolri);
        LabelPangkatPolri.setBounds(441, 445, 135, 23);

        nmpangkatpolri.setEditable(false);
        nmpangkatpolri.setName("nmpangkatpolri"); // NOI18N
        FormKelengkapanPasien.add(nmpangkatpolri);
        nmpangkatpolri.setBounds(580, 445, 273, 23);

        BtnPangkatPolri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPangkatPolri.setMnemonic('1');
        BtnPangkatPolri.setToolTipText("ALt+1");
        BtnPangkatPolri.setEnabled(false);
        BtnPangkatPolri.setName("BtnPangkatPolri"); // NOI18N
        BtnPangkatPolri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPangkatPolriActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPangkatPolri);
        BtnPangkatPolri.setBounds(855, 445, 28, 23);

        BtnJabatanPolri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJabatanPolri.setMnemonic('1');
        BtnJabatanPolri.setToolTipText("ALt+1");
        BtnJabatanPolri.setEnabled(false);
        BtnJabatanPolri.setName("BtnJabatanPolri"); // NOI18N
        BtnJabatanPolri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJabatanPolriActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnJabatanPolri);
        BtnJabatanPolri.setBounds(855, 475, 28, 23);

        nmjabatanpolri.setEditable(false);
        nmjabatanpolri.setName("nmjabatanpolri"); // NOI18N
        FormKelengkapanPasien.add(nmjabatanpolri);
        nmjabatanpolri.setBounds(580, 475, 273, 23);

        LabelJabatanPolri.setText("Jabatan :");
        LabelJabatanPolri.setName("LabelJabatanPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelJabatanPolri);
        LabelJabatanPolri.setBounds(441, 475, 135, 23);

        jLabel38.setText("No.KTP/SIM :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormKelengkapanPasien.add(jLabel38);
        jLabel38.setBounds(3, 235, 100, 23);

        TKtp.setName("TKtp"); // NOI18N
        TKtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtpKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TKtp);
        TKtp.setBounds(107, 235, 120, 23);

        jLabel18.setText("J.K. :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormKelengkapanPasien.add(jLabel18);
        jLabel18.setBounds(236, 235, 65, 23);

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CmbJk);
        CmbJk.setBounds(305, 235, 110, 23);

        FormInput.add(FormKelengkapanPasien);

        FormKelengkapanSEP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "::[ Kelengkapan Data SJP, Registrasi & Kamar Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        FormKelengkapanSEP.setName("FormKelengkapanSEP"); // NOI18N
        FormKelengkapanSEP.setOpaque(false);
        FormKelengkapanSEP.setPreferredSize(new java.awt.Dimension(1000, 217));
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

        jLabel23.setText("Tgl.SJP :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel23);
        jLabel23.setBounds(430, 55, 100, 23);

        TanggalSEP.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019 23:03:33" }));
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
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019 23:03:33" }));
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
        jLabel31.setBounds(682, 25, 80, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoRujukan);
        NoRujukan.setBounds(765, 25, 165, 23);

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

        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        KdPpkRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPpkRujukanKeyPressed(evt);
            }
        });
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

        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        KdPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPenyakitKeyPressed(evt);
            }
        });
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
        btnPoli.setBounds(902, 145, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormKelengkapanSEP.add(NmPoli);
        NmPoli.setBounds(640, 145, 259, 23);

        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPoliKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(KdPoli);
        KdPoli.setBounds(534, 145, 104, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli);
        LabelPoli.setBounds(430, 145, 100, 23);

        jLabel32.setText("Kelas :");
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

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 RJTP RAWAT JALAN TINGKAT PERTAMA", "2 RITP RAWAT INAP TINGKAT PERTAMA", "3 RJTL RAWAT JALAN TINGKAT LANJUT", "4 RITL RAWAT INAP TINGKAT LANJUT", " " }));
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
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
        JenisPelayanan.setBounds(725, 115, 205, 23);

        LabelKelas.setText("Jenis :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas);
        LabelKelas.setBounds(682, 115, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "000 Non Kelas", "100 Kelas 1", "101 Kelas 2", "102 Kelas 3", "103 Kelas VIP", "104 Kelas VVIP", "110 VIP", "200 Kelas 1A", "201 Kelas 2A", "202 Kelas 3A", "203 Kelas VIP A", "204 Kelas VVIP A", "210 UTAMA", "300 Kelas 1 B", "301 Kelas 2 B", "302 Kelas 3 B", "303 Kelas VIP B", "304 Kelas VVIP B", "310 ICU", "311 IA", "312 IB", "400 Kelas 1 C", "401 Kelas 2 C", "402 Kelas 3 C", "403 Kelas VIP C", "404 Kelas VVIP C", "410 HCU", "411 IIA", "412 IIB", "413 HCU 3", "500 KHUSUS", "510 III", "511 IIIA", "512 IIIB", "610 NICU", "611 NICU 1", "612 NICU 2", "613 NICU 3", "710 PICU", "711 PICU 1", "712 PICU 2", "713 PICU 3", "910 ICCU", "911 ICCU 1", "912 ICCU 2", "913 ICCU 3" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Kelas);
        Kelas.setBounds(534, 115, 140, 23);

        jLabel34.setText("Laka Lantas :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormKelengkapanSEP.add(jLabel34);
        jLabel34.setBounds(682, 55, 80, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 Biasa", "1 Kecelakaan Kerja", "2 Kecelakaan Lalu Lintas" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LakaLantas);
        LakaLantas.setBounds(765, 55, 165, 23);

        jLabel35.setText("Lokasi Laka :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormKelengkapanSEP.add(jLabel35);
        jLabel35.setBounds(682, 85, 80, 23);

        LokasiLaka.setHighlighter(null);
        LokasiLaka.setName("LokasiLaka"); // NOI18N
        LokasiLaka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiLakaKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LokasiLaka);
        LokasiLaka.setBounds(765, 85, 165, 23);

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
        jLabel36.setBounds(3, 175, 100, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(kddokter);
        kddokter.setBounds(107, 175, 80, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormKelengkapanSEP.add(TDokter);
        TDokter.setBounds(189, 175, 196, 23);

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
        BtnDokter.setBounds(387, 175, 28, 23);

        jLabel39.setText("Diag. Tambahan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormKelengkapanSEP.add(jLabel39);
        jLabel39.setBounds(3, 145, 100, 23);

        KdPenyakit2.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit2.setHighlighter(null);
        KdPenyakit2.setName("KdPenyakit2"); // NOI18N
        KdPenyakit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPenyakit2KeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(KdPenyakit2);
        KdPenyakit2.setBounds(107, 145, 80, 23);

        NmPenyakit2.setEditable(false);
        NmPenyakit2.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit2.setHighlighter(null);
        NmPenyakit2.setName("NmPenyakit2"); // NOI18N
        FormKelengkapanSEP.add(NmPenyakit2);
        NmPenyakit2.setBounds(189, 145, 196, 23);

        btnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa1.setMnemonic('X');
        btnDiagnosa1.setToolTipText("Alt+X");
        btnDiagnosa1.setName("btnDiagnosa1"); // NOI18N
        btnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosa1ActionPerformed(evt);
            }
        });
        btnDiagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosa1KeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnDiagnosa1);
        btnDiagnosa1.setBounds(387, 145, 28, 23);

        LabelJenpel.setText("Jns.Ruang Rawat :");
        LabelJenpel.setName("LabelJenpel"); // NOI18N
        FormKelengkapanSEP.add(LabelJenpel);
        LabelJenpel.setBounds(430, 175, 100, 23);

        KdJenpel.setHighlighter(null);
        KdJenpel.setName("KdJenpel"); // NOI18N
        KdJenpel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdJenpelKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(KdJenpel);
        KdJenpel.setBounds(534, 175, 124, 23);

        NmJenpel.setEditable(false);
        NmJenpel.setName("NmJenpel"); // NOI18N
        FormKelengkapanSEP.add(NmJenpel);
        NmJenpel.setBounds(660, 175, 270, 23);

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

        jLabel16.setText("No.Kartu :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
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
        kab.dispose();
        kec.dispose();
        kel.dispose();
        dokter.dispose();
        kamar.dispose();
        penjab.dispose();
        propin.dispose();
        perusahaan.dispose();
        bahasa.dispose();
        cacat.dispose();
        suku.dispose();
        golongantni.dispose();
        satuantni.dispose();
        pangkattni.dispose();
        jabatantni.dispose();
        golonganpolri.dispose();
        satuanpolri.dispose();
        pangkatpolri.dispose();
        jabatanpolri.dispose();
        pilihan.dispose();
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
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Pengadaan Ipsrs"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJSNoPeserta.jasper","report","[ Pencarian Peserta BPJS Berdasarkan Nomor Kepesertaan ]",param);
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
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
        ChkRM.setSelected(true);
        cekEligibilitas(NoKartu.getText());
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
            JOptionPane.showMessageDialog(null,"Maaf, data peserta Inhealth masih kosong. Silahkan lakukan pencarian berdasar No.Kartu...!!!!");
        }else{
            TTmp.requestFocus();
            isForm();
        }            
    }//GEN-LAST:event_ChkCariActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah2(evt, NoRujukan,LakaLantas);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah2(evt, kddokter,NoRujukan);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt,TanggalRujuk, TanggalSEP);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt,TanggalSEP,btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        pilih=1;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.isCek();
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,btnPPKRujukan,btnPoli);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        if((JenisPelayanan.getSelectedIndex()==1)||(JenisPelayanan.getSelectedIndex()==3)){
            kamar.isCek();
            kamar.emptTeks();
            kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kamar.setLocationRelativeTo(internalFrame1);
            kamar.setVisible(true);
        }else{
            poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
        if((JenisPelayanan.getSelectedIndex()==0)||(JenisPelayanan.getSelectedIndex()==2)){
            LabelPoli.setText("Poli Tujuan :");
            LabelJenpel.setVisible(false);
            KdJenpel.setVisible(false);
            NmJenpel.setVisible(false);
        }else if((JenisPelayanan.getSelectedIndex()==1)||(JenisPelayanan.getSelectedIndex()==3)){
            LabelPoli.setText("Kamar Tujuan :");
            LabelJenpel.setVisible(true);
            KdJenpel.setVisible(true);
            NmJenpel.setVisible(true);
        }
        KdPoli.setText("");
        NmPoli.setText("");
    }//GEN-LAST:event_JenisPelayananItemStateChanged

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt,Kelas,kdpoli);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,LokasiLaka,JenisPelayanan);
    }//GEN-LAST:event_KelasKeyPressed

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt,TanggalSEP,Catatan);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void LokasiLakaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiLakaKeyPressed
        Valid.pindah(evt,Catatan,Kelas);
    }//GEN-LAST:event_LokasiLakaKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            KdPpkRujukan.requestFocus();
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
            Valid.pindah(evt,KdPenyakit2,TanggalRujuk);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
        }else if(propinsi.equals("Yes")&&(Propinsi.getText().trim().length()<p_propinsi)){
            Valid.textKosong(Propinsi,"Propinsi minimal "+p_propinsi+" karakter dan ");            
        }else if(propinsipj.equals("Yes")&&(PropinsiPj.getText().trim().length()<p_propinsipj)){
            Valid.textKosong(PropinsiPj,"Propinsi P.J. minimal "+p_propinsipj+" karakter dan ");            
        }else if(nmsukubangsa.getText().trim().equals("")){
            Valid.textKosong(nmsukubangsa,"Suku/Bangsa");
        }else if(nmbahasa.getText().trim().equals("")){
            Valid.textKosong(nmbahasa,"Bahasa");
        }else if(nmperusahaan.getText().trim().equals("")){
            Valid.textKosong(nmperusahaan,"Perusahaan/Instansi");
        }else if((chkTNI.isSelected()==true)&&nmgolongantni.getText().trim().equals("")){
            Valid.textKosong(nmgolongantni,"Golongan TNI");
        }else if((chkTNI.isSelected()==true)&&nmsatuantni.getText().trim().equals("")){
            Valid.textKosong(nmsatuantni,"Satuan TNI");
        }else if((chkTNI.isSelected()==true)&&nmpangkattni.getText().trim().equals("")){
            Valid.textKosong(nmpangkattni,"Pangkat TNI");
        }else if((chkTNI.isSelected()==true)&&nmjabatantni.getText().trim().equals("")){
            Valid.textKosong(nmjabatantni,"Jabatan TNI");
        }else if((chkPolri.isSelected()==true)&&nmgolonganpolri.getText().trim().equals("")){
            Valid.textKosong(nmgolonganpolri,"Golongan POLRI");
        }else if((chkPolri.isSelected()==true)&&nmsatuanpolri.getText().trim().equals("")){
            Valid.textKosong(nmsatuanpolri,"Satuan POLRI");
        }else if((chkPolri.isSelected()==true)&&nmpangkatpolri.getText().trim().equals("")){
            Valid.textKosong(nmpangkatpolri,"Pangkat POLRI");
        }else if((chkPolri.isSelected()==true)&&nmjabatanpolri.getText().trim().equals("")){
            Valid.textKosong(nmjabatanpolri,"Jabatan POLRI");
        }else if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"dokter");
        }else if(NoKartu.getText().trim().equals("")){
            Valid.textKosong(NoKartu, "Nomor Kartu");
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
            insertPasien();            
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KdPoli,NoKartu);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        BtnSimpan.setVisible(false);        
    }//GEN-LAST:event_formWindowOpened

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        
    }//GEN-LAST:event_TNmKeyPressed

    private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TKtp.requestFocus();
        }
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
        Valid.pindah(evt,Kdpnj,CmbJk);
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
            pilihan.tampil3();
            if((JenisPelayanan.getSelectedIndex()==0)||(JenisPelayanan.getSelectedIndex()==2)){
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
            JOptionPane.showMessageDialog(null,"Data SJP belum tersimpan, berkas tidak bisa dicetak...!");
        }
    }//GEN-LAST:event_MnDocumentActionPerformed

    private void btnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosa1ActionPerformed
        pilih=2;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.isCek();
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosa1ActionPerformed

    private void btnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosa1KeyPressed

    private void KdJenpelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdJenpelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdJenpelKeyPressed

    private void KdPpkRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPpkRujukanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            KdPenyakit.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoReg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPPKRujukanActionPerformed(null);
        }
    }//GEN-LAST:event_KdPpkRujukanKeyPressed

    private void KdPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            KdPenyakit2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdPPK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDiagnosaActionPerformed(null);
        }
    }//GEN-LAST:event_KdPenyakitKeyPressed

    private void KdPenyakit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPenyakit2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kddokter.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdPenyakit.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDiagnosa1ActionPerformed(null);
        }
    }//GEN-LAST:event_KdPenyakit2KeyPressed

    private void KdPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            JenisPelayanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPoliActionPerformed(null);
        }
    }//GEN-LAST:event_KdPoliKeyPressed

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
            BtnCacat.requestFocus();
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
            if(Propinsi.getText().equals("PROPINSI")){
                Propinsi.setText("");
            }
            Propinsi.requestFocus();
        }
    }//GEN-LAST:event_SaudaraKeyPressed

    private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,Kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPenjabActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSuku.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NmIbu.requestFocus();
        }
    }//GEN-LAST:event_KdpnjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            if(Propinsi.getText().equals("PROPINSI")){
                Propinsi.setText("");
            }
            if(Kabupaten.getText().equals("")){
                Kabupaten.setText("KABUPATEN");
            }
            Propinsi.requestFocus();
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
        kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        pilih=1;
        kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        pilih=1;
        kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
        kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            if(PropinsiPj.getText().equals("PROPINSI")){
                PropinsiPj.setText("");
            }
            PropinsiPj.requestFocus();
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
        kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenPjActionPerformed

    private void BtnKelurahanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanPjActionPerformed
        pilih=2;
        kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            if(statuspasien.equals("Baru")){
                autoNomor();
            }
        }else if(ChkRM.isSelected()==false){
            TNo.setEditable(true);
            TNo.setBackground(new Color(250,255,245));
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void BtnSukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSukuActionPerformed
        akses.setform("DlgPasien");
        suku.isCek();
        suku.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        suku.setLocationRelativeTo(internalFrame1);
        suku.setVisible(true);
    }//GEN-LAST:event_BtnSukuActionPerformed

    private void BtnSukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSukuActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Kdpnj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnBahasa.requestFocus();
        }
    }//GEN-LAST:event_BtnSukuKeyPressed

    private void BtnBahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBahasaActionPerformed
        akses.setform("DlgPasien");
        bahasa.isCek();
        bahasa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bahasa.setLocationRelativeTo(internalFrame1);
        bahasa.setVisible(true);
    }//GEN-LAST:event_BtnBahasaActionPerformed

    private void BtnBahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBahasaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBahasaActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnSuku.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCacat.requestFocus();
        }
    }//GEN-LAST:event_BtnBahasaKeyPressed

    private void BtnCacatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCacatActionPerformed
        akses.setform("DlgPasien");
        cacat.isCek();
        cacat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        cacat.setLocationRelativeTo(internalFrame1);
        cacat.setVisible(true);
    }//GEN-LAST:event_BtnCacatActionPerformed

    private void BtnCacatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCacatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCacatActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnBahasa.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Alamat.getText().equals("ALAMAT")){
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }
    }//GEN-LAST:event_BtnCacatKeyPressed

    private void BtnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerusahaanActionPerformed
        akses.setform("DlgPasien");
        perusahaan.isCek();
        perusahaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perusahaan.setLocationRelativeTo(internalFrame1);
        perusahaan.setVisible(true);
    }//GEN-LAST:event_BtnPerusahaanActionPerformed

    private void BtnPerusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerusahaanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPerusahaanActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(PropinsiPj.getText().equals("PROPINSI")){
                PropinsiPj.setText("");
            }
            PropinsiPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            EMail.requestFocus();
        }
    }//GEN-LAST:event_BtnPerusahaanKeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        Valid.pindah(evt,BtnPerusahaan,TNoReg);
    }//GEN-LAST:event_NIPKeyPressed

    private void EMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EMailKeyPressed
        Valid.pindah(evt,BtnPerusahaan,NIP);
    }//GEN-LAST:event_EMailKeyPressed

    private void PropinsiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseMoved
        if(Propinsi.getText().equals("PROPINSI")){
            Propinsi.setText("");
        }
    }//GEN-LAST:event_PropinsiMouseMoved

    private void PropinsiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseExited
        if(Propinsi.getText().equals("")){
            Propinsi.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiMouseExited

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            Saudara.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(Propinsi.getText().equals("")){
                Propinsi.setText("PROPINSI");
            }
            if(Kabupaten.getText().equals("KABUPATEN")){
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPropinsiActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiKeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        akses.setform("DlgPasien");
        pilih=1;
        propin.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propin.setLocationRelativeTo(internalFrame1);
        propin.setVisible(true);
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void PropinsiPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiPjMouseMoved
        if(PropinsiPj.getText().equals("PROPINSI")){
            PropinsiPj.setText("");
        }
    }//GEN-LAST:event_PropinsiPjMouseMoved

    private void PropinsiPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiPjMouseExited
        if(PropinsiPj.getText().equals("")){
            PropinsiPj.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiPjMouseExited

    private void PropinsiPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiPjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(PropinsiPj.getText().equals("")){
                PropinsiPj.setText("PROPINSI");
            }
            BtnPerusahaan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            if(PropinsiPj.getText().equals("")){
                PropinsiPj.setText("PROPINSI");
            }
            if(KabupatenPj.getText().equals("KABUPATEN")){
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPropinsiPjActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiPjKeyPressed

    private void btnPropinsiPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropinsiPjActionPerformed
        akses.setform("DlgPasien");
        pilih=3;
        propin.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propin.setLocationRelativeTo(internalFrame1);
        propin.setVisible(true);
    }//GEN-LAST:event_btnPropinsiPjActionPerformed

    private void chkTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTNIActionPerformed
        if(chkTNI.isSelected()==true){
            chkPolri.setSelected(false);
            kdgolonganpolri.setText("");
            nmgolonganpolri.setText("");
            kdsatuanpolri.setText("");
            nmsatuanpolri.setText("");
            kdpangkatpolri.setText("");
            nmpangkatpolri.setText("");
            kdjabatanpolri.setText("");
            nmjabatanpolri.setText("");
            BtnGolonganPolri.setEnabled(false);
            BtnSatuanPolri.setEnabled(false);
            BtnJabatanPolri.setEnabled(false);
            BtnPangkatPolri.setEnabled(false);
            BtnGolonganTNI.setEnabled(true);
            BtnSatuanTNI.setEnabled(true);
            BtnJabatanTNI.setEnabled(true);
            BtnPangkatTNI.setEnabled(true);
        }
    }//GEN-LAST:event_chkTNIActionPerformed

    private void BtnGolonganTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganTNIActionPerformed
        akses.setform("DlgPasien");
        golongantni.isCek();
        golongantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        golongantni.setLocationRelativeTo(internalFrame1);
        golongantni.setVisible(true);
    }//GEN-LAST:event_BtnGolonganTNIActionPerformed

    private void BtnSatuanTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanTNIActionPerformed
        akses.setform("DlgPasien");
        satuantni.isCek();
        satuantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        satuantni.setLocationRelativeTo(internalFrame1);
        satuantni.setVisible(true);
    }//GEN-LAST:event_BtnSatuanTNIActionPerformed

    private void BtnPangkatTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPangkatTNIActionPerformed
        akses.setform("DlgPasien");
        pangkattni.isCek();
        pangkattni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pangkattni.setLocationRelativeTo(internalFrame1);
        pangkattni.setVisible(true);
    }//GEN-LAST:event_BtnPangkatTNIActionPerformed

    private void BtnJabatanTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJabatanTNIActionPerformed
        akses.setform("DlgPasien");
        jabatantni.isCek();
        jabatantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jabatantni.setLocationRelativeTo(internalFrame1);
        jabatantni.setVisible(true);
    }//GEN-LAST:event_BtnJabatanTNIActionPerformed

    private void chkPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPolriActionPerformed
        if(chkPolri.isSelected()==true){
            chkTNI.setSelected(false);
            kdgolongantni.setText("");
            nmgolongantni.setText("");
            kdsatuantni.setText("");
            nmsatuantni.setText("");
            kdpangkattni.setText("");
            nmpangkattni.setText("");
            kdjabatantni.setText("");
            nmjabatantni.setText("");
            BtnGolonganPolri.setEnabled(true);
            BtnSatuanPolri.setEnabled(true);
            BtnJabatanPolri.setEnabled(true);
            BtnPangkatPolri.setEnabled(true);
            BtnGolonganTNI.setEnabled(false);
            BtnSatuanTNI.setEnabled(false);
            BtnJabatanTNI.setEnabled(false);
            BtnPangkatTNI.setEnabled(false);
        }
    }//GEN-LAST:event_chkPolriActionPerformed

    private void BtnGolonganPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganPolriActionPerformed
        akses.setform("DlgPasien");
        golonganpolri.isCek();
        golonganpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        golonganpolri.setLocationRelativeTo(internalFrame1);
        golonganpolri.setVisible(true);
    }//GEN-LAST:event_BtnGolonganPolriActionPerformed

    private void BtnSatuanPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanPolriActionPerformed
        akses.setform("DlgPasien");
        satuanpolri.isCek();
        satuanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        satuanpolri.setLocationRelativeTo(internalFrame1);
        satuanpolri.setVisible(true);
    }//GEN-LAST:event_BtnSatuanPolriActionPerformed

    private void BtnPangkatPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPangkatPolriActionPerformed
        akses.setform("DlgPasien");
        pangkatpolri.isCek();
        pangkatpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pangkatpolri.setLocationRelativeTo(internalFrame1);
        pangkatpolri.setVisible(true);
    }//GEN-LAST:event_BtnPangkatPolriActionPerformed

    private void BtnJabatanPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJabatanPolriActionPerformed
        akses.setform("DlgPasien");
        jabatanpolri.isCek();
        jabatanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jabatanpolri.setLocationRelativeTo(internalFrame1);
        jabatanpolri.setVisible(true);
    }//GEN-LAST:event_BtnJabatanPolriActionPerformed

    private void kdperusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdperusahaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdperusahaanKeyPressed

    private void kdcacatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdcacatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdcacatKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InhealthCekEligibilitas dialog = new InhealthCekEligibilitas(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBahasa;
    private widget.Button BtnCacat;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnGolonganPolri;
    private widget.Button BtnGolonganTNI;
    private widget.Button BtnJabatanPolri;
    private widget.Button BtnJabatanTNI;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKabupatenPj;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKecamatanPj;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnKelurahanPj;
    private widget.Button BtnPangkatPolri;
    private widget.Button BtnPangkatTNI;
    private widget.Button BtnPenjab;
    private widget.Button BtnPerusahaan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSatuanPolri;
    private widget.Button BtnSatuanTNI;
    private widget.Button BtnSimpan;
    private widget.Button BtnSuku;
    private widget.ComboBox CMbGd;
    private widget.ComboBox CMbPnd;
    private widget.TextBox Catatan;
    private widget.CekBox ChkCari;
    private widget.CekBox ChkRM;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private widget.TextBox EMail;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormKelengkapanPasien;
    private widget.PanelBiasa FormKelengkapanSEP;
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox Kabupaten;
    private widget.TextBox KabupatenPj;
    private widget.TextBox KdJenpel;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPenyakit2;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox KecamatanPj;
    private widget.ComboBox Kelas;
    private widget.TextBox Kelurahan;
    private widget.TextBox KelurahanPj;
    private widget.Label LabelGolonganPolri;
    private widget.Label LabelGolonganTNI;
    private widget.Label LabelJabatanPolri;
    private widget.Label LabelJabatanTNI;
    private widget.Label LabelJenpel;
    private widget.Label LabelKelas;
    private widget.Label LabelPangkatPolri;
    private widget.Label LabelPangkatTNI;
    private widget.Label LabelPoli;
    private widget.Label LabelSatuanPolri;
    private widget.Label LabelSatuanTNI;
    private widget.ComboBox LakaLantas;
    private widget.TextBox LokasiLaka;
    private javax.swing.JMenuItem MnDocument;
    private widget.TextBox NIP;
    private widget.TextBox NmIbu;
    private widget.TextBox NmJenpel;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPenyakit2;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRm;
    private widget.TextBox NoRujukan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox PekerjaanPj;
    private widget.TextBox Propinsi;
    private widget.TextBox PropinsiPj;
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
    private widget.Button btnDiagnosa1;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPasien;
    private widget.Button btnPoli;
    private widget.Button btnPropinsiPj;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.CekBox chkPolri;
    private widget.CekBox chkTNI;
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
    private widget.Label jLabel5;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbahasa;
    private widget.TextBox kdcacat;
    private widget.TextBox kddokter;
    private widget.TextBox kdgolonganpolri;
    private widget.TextBox kdgolongantni;
    private widget.TextBox kdjabatanpolri;
    private widget.TextBox kdjabatantni;
    private widget.TextBox kdpangkatpolri;
    private widget.TextBox kdpangkattni;
    private widget.TextBox kdperusahaan;
    private widget.TextBox kdpoli;
    private widget.TextBox kdsatuanpolri;
    private widget.TextBox kdsatuantni;
    private widget.TextBox kdsuku;
    private widget.TextBox nmbahasa;
    private widget.TextBox nmcacat;
    private widget.TextBox nmgolonganpolri;
    private widget.TextBox nmgolongantni;
    private widget.TextBox nmjabatanpolri;
    private widget.TextBox nmjabatantni;
    private widget.TextBox nmpangkatpolri;
    private widget.TextBox nmpangkattni;
    private widget.TextBox nmperusahaan;
    private widget.TextBox nmpnj;
    private widget.TextBox nmsatuanpolri;
    private widget.TextBox nmsatuantni;
    private widget.TextBox nmsukubangsa;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void cekEligibilitas(String nomorpeserta){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLAPIINHEALTH")+"/api/EligibilitasPeserta";	
	    HttpHeaders headers = new HttpHeaders();            
            headers.add("Content-Type","application/json");
	    requestJson ="{ \"token\": \""+prop.getProperty("TOKENINHEALTH")+"\"," +
                            "\"kodeprovider\": \""+KdPPK.getText()+"\"," +
                            "\"nokainhealth\": \""+NoKartu.getText()+"\"," +
                            "\"tglpelayanan\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                            "\"jenispelayanan\": \"3\"," +
                            "\"poli\": \"UMU\"" +
                         "}";
            HttpEntity requestEntity = new HttpEntity(requestJson,headers);
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            //System.out.println("JSON : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            if(root.path("ERRORCODE").asText().equals("00")){
                Valid.tabelKosong(tabMode);
                tabMode.addRow(new Object[]{
                    "No Kartu Peserta",": "+root.path("NOKAPST").asText()
                });
                TNoPeserta.setText(root.path("NOKAPST").asText());                
                tabMode.addRow(new Object[]{
                    "Nama Peserta",": "+root.path("NMPST").asText()
                });
                TNm.setText(root.path("NMPST").asText());
                tabMode.addRow(new Object[]{
                    "Tanggal Lahir",": "+root.path("TGLLAHIR").asText()
                });
                
                Valid.SetTgl(DTPLahir,root.path("TGLLAHIR").asText());
                lahir = DTPLahir.getDate();    
                birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                p = Period.between(birthday,today);
                p2 =ChronoUnit.DAYS.between(birthday,today);
                TUmur.setText(String.valueOf(p.getYears())+" Th "+String.valueOf(p.getMonths())+" Bl "+String.valueOf(p.getDays())+" Hr");
                
                tabMode.addRow(new Object[]{
                    "Kode Produk",": "+root.path("KODEPRODUK").asText()
                });
                tabMode.addRow(new Object[]{
                    "Nama Produk",": "+root.path("NAMAPRODUK").asText()
                });
                tabMode.addRow(new Object[]{
                    "Kode Kelas",": "+root.path("KODEKELASRAWAT").asText()
                });
                tabMode.addRow(new Object[]{
                    "Nama Kelas",": "+root.path("NAMAKELASRAWAT").asText()
                });
                tabMode.addRow(new Object[]{
                    "Kode Badan Usaha",": "+root.path("KODEBADANUSAHA").asText()
                });
                tabMode.addRow(new Object[]{
                    "Nama Badan Usaha",": "+root.path("NAMABADANUSAHA").asText()
                });
                tabMode.addRow(new Object[]{
                    "Kode Provider",": "+root.path("KODEPROVIDER").asText()
                });
                tabMode.addRow(new Object[]{
                    "Nama Provider",": "+root.path("NAMAPROVIDER").asText()
                });
                KdPpkRujukan.setText(root.path("KODEPROVIDER").asText());
                NmPpkRujukan.setText(root.path("NAMAPROVIDER").asText());
                tabMode.addRow(new Object[]{
                    "No Kartu BPJS",": "+root.path("NOKAPSTBPJS").asText()
                });
                tabMode.addRow(new Object[]{
                    "Kode Provider BPJS",": "+root.path("KODEPROVIDERBPJS").asText()
                });                
                tabMode.addRow(new Object[]{
                    "Nama Provider BPJS",": "+root.path("NAMAPROVIDERBPJS").asText()
                });
                tabMode.addRow(new Object[]{
                    "Flag Peserta BPJS",": "+root.path("FLAGPSTBPJS").asText()
                });
                tabMode.addRow(new Object[]{
                    "Produk COB",": "+root.path("PRODUKCOB").asText()
                });                                
                Kdpnj.setText("INH");
                nmpnj.setText("Mandiri Inhealth");
                ps=koneksi.prepareStatement(
                   "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                   "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"+
                   "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"+
                   "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"+
                   "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten inner join perusahaan_pasien inner join cacat_fisik inner join propinsi "+
                   "inner join bahasa_pasien inner join suku_bangsa inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.cacat_fisik=cacat_fisik.id "+
                   "and pasien.kd_kel=kelurahan.kd_kel and perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien and pasien.kd_prop=propinsi.kd_prop "+
                   "and bahasa_pasien.id=pasien.bahasa_pasien and suku_bangsa.id=pasien.suku_bangsa and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                   "where pasien.no_peserta=?");
                try {
                    ps.setString(1,root.path("NOKAPST").asText());
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
                        Propinsi.setText(rs.getString("nm_prop")); 
                        KelurahanPj.setText(rs.getString("kelurahanpj"));      
                        KecamatanPj.setText(rs.getString("kecamatanpj"));      
                        KabupatenPj.setText(rs.getString("kabupatenpj"));    
                        PropinsiPj.setText(rs.getString("propinsipj")); 
                        EMail.setText(rs.getString("email"));
                        NIP.setText(rs.getString("nip"));
                        kdsuku.setText(rs.getString("suku_bangsa"));
                        nmsukubangsa.setText(rs.getString("nama_suku_bangsa"));
                        kdbahasa.setText(rs.getString("bahasa_pasien"));
                        nmbahasa.setText(rs.getString("nama_bahasa"));
                        kdcacat.setText(rs.getString("cacat_fisik"));
                        nmcacat.setText(rs.getString("nama_cacat"));
                        kdperusahaan.setText(rs.getString("kode_perusahaan"));
                        nmperusahaan.setText(rs.getString("nama_perusahaan")); 
                        TKtp.setText(rs.getString("no_ktp"));
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
                        if(tampilkantni.equals("Yes")){
                            pstni=koneksi.prepareStatement(
                                "select pasien_tni.no_rkm_medis,pasien_tni.golongan_tni,golongan_tni.nama_golongan,"+
                                "pasien_tni.satuan_tni,satuan_tni.nama_satuan,pasien_tni.pangkat_tni,"+
                                "pangkat_tni.nama_pangkat,pasien_tni.jabatan_tni,jabatan_tni.nama_jabatan "+
                                "from pasien_tni inner join golongan_tni inner join satuan_tni "+
                                "inner join pangkat_tni inner join jabatan_tni on pasien_tni.golongan_tni=golongan_tni.id "+
                                "and pasien_tni.pangkat_tni=pangkat_tni.id and pasien_tni.satuan_tni=satuan_tni.id "+
                                "and pasien_tni.jabatan_tni=jabatan_tni.id where pasien_tni.no_rkm_medis=?");
                            try {
                                pstni.setString(1,rs.getString("no_rkm_medis"));
                                rs2=pstni.executeQuery();
                                if(rs2.next()){
                                    kdgolongantni.setText(rs2.getString("golongan_tni"));
                                    nmgolongantni.setText(rs2.getString("nama_golongan"));
                                    kdsatuantni.setText(rs2.getString("satuan_tni"));
                                    nmsatuantni.setText(rs2.getString("nama_satuan"));
                                    kdpangkattni.setText(rs2.getString("pangkat_tni"));
                                    nmpangkattni.setText(rs2.getString("nama_pangkat"));
                                    kdjabatantni.setText(rs2.getString("jabatan_tni"));
                                    nmjabatantni.setText(rs2.getString("nama_jabatan"));
                                    chkTNI.setSelected(true);
                                    chkTNIActionPerformed(null);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif TNI : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(pstni!=null){
                                    pstni.close();
                                }
                            }
                                
                            pspolri=koneksi.prepareStatement(
                                "select pasien_polri.no_rkm_medis,pasien_polri.golongan_polri,golongan_polri.nama_golongan,"+
                                "pasien_polri.satuan_polri,satuan_polri.nama_satuan,pasien_polri.pangkat_polri,"+
                                "pangkat_polri.nama_pangkat,pasien_polri.jabatan_polri,jabatan_polri.nama_jabatan "+
                                "from pasien_polri inner join golongan_polri inner join satuan_polri "+
                                "inner join pangkat_polri inner join jabatan_polri on pasien_polri.golongan_polri=golongan_polri.id "+
                                "and pasien_polri.pangkat_polri=pangkat_polri.id and pasien_polri.satuan_polri=satuan_polri.id "+
                                "and pasien_polri.jabatan_polri=jabatan_polri.id where pasien_polri.no_rkm_medis=?");
                            try {
                                pspolri.setString(1,rs.getString("no_rkm_medis"));
                                rs2=pspolri.executeQuery();
                                if(rs2.next()){
                                    kdgolonganpolri.setText(rs2.getString("golongan_polri"));
                                    nmgolonganpolri.setText(rs2.getString("nama_golongan"));
                                    kdsatuanpolri.setText(rs2.getString("satuan_polri"));
                                    nmsatuanpolri.setText(rs2.getString("nama_satuan"));
                                    kdpangkatpolri.setText(rs2.getString("pangkat_polri"));
                                    nmpangkatpolri.setText(rs2.getString("nama_pangkat"));
                                    kdjabatanpolri.setText(rs2.getString("jabatan_polri"));
                                    nmjabatanpolri.setText(rs2.getString("nama_jabatan"));
                                    chkPolri.setSelected(true);
                                    chkPolriActionPerformed(null);
                                }
                            } catch (Exception e) {
                                System.out.println("Notif TNI : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(pspolri!=null){
                                    pspolri.close();
                                }
                            }
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
                JOptionPane.showMessageDialog(null,root.path("ERRORDESC").asText());                
            } 
        } catch (Exception e) {
            System.out.println("Notifikasi Peserta : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Inhealth terputus...!");
            }
        }
    }
    
      
    
    private void isForm(){
        if(ChkCari.isSelected()==true){
            panelCari.setVisible(true);
                
            if(tampilkantni.equals("Yes")){
                FormInput.setPreferredSize(new Dimension(955,730));
                if(internalFrame1.getHeight()>530){
                    PanelInput.setPreferredSize(new Dimension(WIDTH,570));
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,515));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,214));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,515));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,214));
                    }
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-20));
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,515));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,214));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,515));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,214));
                    }
                }
            }else{
                FormInput.setPreferredSize(new Dimension(955,580));
                if(internalFrame1.getHeight()>530){
                    PanelInput.setPreferredSize(new Dimension(WIDTH,570));
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,365));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,214));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,365));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,214));
                    }
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-20));
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,365));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,214));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,365));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,214));
                    }
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
        JenisPelayanan.setSelectedIndex(0);
        Catatan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Kelas.setSelectedIndex(0);
        LakaLantas.setSelectedIndex(0);
        LokasiLaka.setText("");
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
    
    private void insertSJP(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLAPIINHEALTH")+"/api/SimpanSJP";	
	    HttpHeaders headers = new HttpHeaders();            
            headers.add("Content-Type","application/json");
	    requestJson ="{ \"token\": \""+prop.getProperty("TOKENINHEALTH")+"\"," +
                            "\"kodeprovider\": \""+KdPPK.getText()+"\"," +
                            "\"tanggalpelayanan\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\","+
                            "\"jenispelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\","+
                            "\"nokainhealth\": \""+NoKartu.getText()+"\","+
                            "\"nomormedicalreport\": \""+TNo.getText()+"\","+
                            "\"nomorasalrujukan\": \""+NoRujukan.getText()+"\","+
                            "\"kodeproviderasalrujukan\": \""+KdPpkRujukan.getText()+"\","+
                            "\"tanggalasalrujukan\": \""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+"\","+
                            "\"kodediagnosautama\": \""+KdPenyakit.getText()+"\","+
                            "\"poli\": \""+KdPoli.getText()+"\","+
                            "\"username\": \""+user+"\","+
                            "\"informasitambahan\": \""+Catatan.getText()+"\","+
                            "\"kodediagnosatambahan\": \""+KdPenyakit2.getText()+"\","+
                            "\"kecelakaankerja\": \""+LakaLantas.getSelectedItem().toString().substring(0,1)+"\","+
                            "\"kelasrawat\": \""+Kelas.getSelectedItem().toString().substring(0,3)+"\","+
                            "\"kodejenpelruangrawat\": \""+KdJenpel.getText()+"\""+
                         "}";
            HttpEntity requestEntity = new HttpEntity(requestJson,headers);
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            if(root.path("ERRORCODE").asText().equals("00")){
                nosep=root.path("NOSJP").asText();
                if(Sequel.menyimpantf("bridging_inhealth","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data SJP",35,new String[]{
                      nosep,TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                      Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                      NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(),
                      JenisPelayanan.getSelectedItem().toString().substring(0,1),Catatan.getText(),KdPenyakit.getText(), 
                      NmPenyakit.getText(),KdPenyakit2.getText(),NmPenyakit2.getText(),KdPoli.getText(),NmPoli.getText(),
                      Kelas.getSelectedItem().toString().substring(0,3),root.path("KELASDESC").asText(),root.path("KDBU").asText(),
                      root.path("NMBU").asText(),LakaLantas.getSelectedItem().toString().substring(0,1),LokasiLaka.getText(),user,
                      root.path("NOMEDICALRECORD").asText(),root.path("NAMAPESERTA").asText(),root.path("TGLLAHIR").asText().replaceAll("T"," ").replaceAll("Z",""),
                      CmbJk.getSelectedItem().toString(),root.path("NOKAPESERTA").asText(),"0000-00-00 00:00:00",
                      root.path("PLAN").asText(),root.path("PLANDESC").asText(),root.path("IDAKOMODASI").asText(),
                      root.path("TIPESJP").asText(),root.path("TIPECOB").asText()
                  })==true){
                    if(!nosep.equals("")){
                        pilihan.tampil3();
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
                JOptionPane.showMessageDialog(null,root.path("ERRORDESC").asText());
                NoKartu.requestFocus();
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi Bridging : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Inhealth terputus...!");
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
        
        status=Sequel.cariIsi("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='"+TNo.getText()+"' and kd_poli='"+kdpoli.getText()+"')>0,'Lama','Baru' )");
        if((JenisPelayanan.getSelectedIndex()==0)||(JenisPelayanan.getSelectedIndex()==2)){
            isNumber();
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),kdpoli.getText(),Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,TBiaya.getText(),"Belum","Lama","Ralan",Kdpnj.getText(),umur,sttsumur,"Belum Bayar",status
                })==true){
                    UpdateUmur();
                    Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");             
                    Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdPenyakit.getText(),NmPenyakit.getText(),NmPenyakit.getText(),"-","-","Tidak Menular"});
                    if(Sequel.cariInteger(
                            "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                            "inner join reg_periksa inner join pasien on "+
                            "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                            "pasien.no_rkm_medis='"+TNo.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdPenyakit.getText()+"'")>0){
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1","Lama"});
                    }else{
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1","Baru"});
                    }
                    if(Sequel.cariInteger(
                            "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                            "inner join reg_periksa inner join pasien on "+
                            "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                            "pasien.no_rkm_medis='"+TNo.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdPenyakit2.getText()+"'")>0){
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit2.getText(),"Ralan","2","Lama"});
                    }else{
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit2.getText(),"Ralan","2","Baru"});
                    }
                    insertSJP();
            }
        }else if((JenisPelayanan.getSelectedIndex()==1)||(JenisPelayanan.getSelectedIndex()==3)){
            isNumber();
            Sequel.menyimpan("poliklinik","?,?,?,?",4,new String[]{"IGDK","Unit IGD","0","0"});
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),"IGDK",Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,Sequel.cariIsi("select registrasilama from poliklinik where kd_poli='IGDK'"),"Belum","Lama","Ralan",Kdpnj.getText(),umur,sttsumur,"Belum Bayar",status
                })==true){
                    UpdateUmur();
                    Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");                                     
                    Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdPenyakit.getText(),NmPenyakit.getText(),NmPenyakit.getText(),"-","-","Tidak Menular"});
                    if(Sequel.cariInteger(
                            "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                            "inner join reg_periksa inner join pasien on "+
                            "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                            "pasien.no_rkm_medis='"+TNo.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdPenyakit.getText()+"'")>0){
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1","Lama"});
                    }else{
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit.getText(),"Ralan","1","Baru"});
                    }
                    if(Sequel.cariInteger(
                            "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                            "inner join reg_periksa inner join pasien on "+
                            "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                            "pasien.no_rkm_medis='"+TNo.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdPenyakit2.getText()+"'")>0){
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit2.getText(),"Ralan","2","Lama"});
                    }else{
                        Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdPenyakit2.getText(),"Ralan","2","Baru"});
                    }
                    
                    if(Sequel.menyimpantf2("kamar_inap","'"+TNoRw.getText()+"','"+kdpoli.getText()+"','"+TBiaya.getText()+"','"+
                            KdPenyakit.getText()+"','-','"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"','"+
                            TanggalSEP.getSelectedItem().toString().substring(11,19)+"','0000-00-00',"+
                            "'00:00:00','"+jmlhari+"','"+biaya+"','-'","No.Rawat"
                        )==true){
                            Sequel.mengedit("reg_periksa","no_rawat='"+TNoRw.getText()+"'","status_lanjut='Ranap'");
                            Sequel.mengedit("kamar","kd_kamar='"+kdpoli.getText()+"'","status='ISI'"); 
                            insertSJP();
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

                        
        Sequel.queryu4("insert into kelurahan values(?,?)",2,new String[]{"0",Kelurahan.getText().replaceAll("KELURAHAN","-")});
        Sequel.queryu4("insert into kecamatan values(?,?)",2,new String[]{"0",Kecamatan.getText().replaceAll("KECAMATAN","-")});
        Sequel.queryu4("insert into kabupaten values(?,?)",2,new String[]{"0",Kabupaten.getText().replaceAll("KABUPATEN","-")});
        Sequel.queryu4("insert into penjab values(?,?)",2,new String[]{Kdpnj.getText(),nmpnj.getText()});
        Sequel.queryu4("insert into propinsi values(?,?)",2,new String[]{"0",Propinsi.getText().replaceAll("PROPINSI","-")});
        
        jmlhari=0;
        biaya=0;
        if(hariawal.equals("Yes")){
            jmlhari=1;
            biaya=Double.parseDouble(TBiaya.getText());
        }

        if(statuspasien.equals("baru")){
            autoNomor();
            if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                    TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                    Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmur.getText(),CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-")),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-")),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-")),
                    PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText(),kdperusahaan.getText(),
                    kdsuku.getText(),kdbahasa.getText(),kdcacat.getText(),EMail.getText(),NIP.getText(),
                    Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",Propinsi.getText().replaceAll("PROPINSI","-")),PropinsiPj.getText()
                })==true){
                    if(chkTNI.isSelected()==true){
                        Sequel.menyimpan2("pasien_tni","?,?,?,?,?","Data",5,new String[]{
                            TNo.getText(),kdgolongantni.getText(),kdpangkattni.getText(),
                            kdsatuantni.getText(),kdjabatantni.getText()
                        });
                    }  
                    if(chkPolri.isSelected()==true){
                        Sequel.menyimpan2("pasien_polri","?,?,?,?,?,?","Data",5,new String[]{
                            TNo.getText(),kdgolonganpolri.getText(),kdpangkatpolri.getText(),
                            kdsatuanpolri.getText(),kdjabatanpolri.getText()
                        });
                    }
                    if(ChkRM.isSelected()==true){
                        Sequel.queryu2("delete from set_no_rkm_medis");
                        Sequel.queryu2("insert into set_no_rkm_medis values(?)",1,new String[]{TNo.getText()});            
                    }             

                    inputRegistrasi();                        
            }else{
                autoNomor();                    
            }
        }else if(statuspasien.equals("lama")){
            Sequel.mengedit("pasien","no_rkm_medis=?","no_rkm_medis=?,nm_pasien=?,no_ktp=?,jk=?,tmp_lahir=?,"+
                "tgl_lahir=?,alamat=?,gol_darah=?,pekerjaan=?,stts_nikah=?,agama=?,tgl_daftar=?,no_tlp=?,umur=?"+
                ",pnd=?,keluarga=?,namakeluarga=?,kd_pj=?,no_peserta=?,kd_kel=?,kd_kec=?,kd_kab=?,nm_ibu=?,pekerjaanpj=?,"+
                "alamatpj=?,kelurahanpj=?,kecamatanpj=?,kabupatenpj=?,perusahaan_pasien=?,suku_bangsa=?,bahasa_pasien=?,"+
                "cacat_fisik=?,email=?,nip=?,kd_prop=?,propinsipj=?",37,
                new String[]{TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),
                    Alamat.getText(),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmur.getText(),CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText()),
                    Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText()),
                    Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText()), 
                    NmIbu.getText(),PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),
                    KabupatenPj.getText(),kdperusahaan.getText(),kdsuku.getText(),kdbahasa.getText(),kdcacat.getText(),EMail.getText(),NIP.getText(),
                    Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",Propinsi.getText()),
                    PropinsiPj.getText(),TNo.getText()
            });   

            inputRegistrasi();                                
        }                
        
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{TNo.getText()});
    }
}

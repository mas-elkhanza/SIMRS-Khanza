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
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgPasien;
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
    private BPJSCekMappingPoli poli=new BPJSCekMappingPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKamar kamar=new DlgKamar(null,false);
    private DlgPilihanCetakDokumen pilihan=new DlgPilihanCetakDokumen(null,false);
    private BPJSCekReferensiDokterDPJP dpjp=new BPJSCekReferensiDokterDPJP(null,false);
    private DlgSKDPBPJS skdp=new DlgSKDPBPJS(null,false);
    private BPJSCekReferensiPropinsi propinsikll=new BPJSCekReferensiPropinsi(null,false);
    private BPJSCekReferensiKabupaten kabupatenkll=new BPJSCekReferensiKabupaten(null,false);
    private BPJSCekReferensiKecamatan kecamatankll=new BPJSCekReferensiKecamatan(null,false);
    private BPJSCekHistoriPelayanan historiPelayanan=new BPJSCekHistoriPelayanan(null,false);
    private BPJSApi api=new BPJSApi();
    private int pilih=0,p_no_ktp=0,p_tmp_lahir=0,p_nm_ibu=0,p_alamat=0,
            p_pekerjaan=0,p_no_tlp=0,p_umur=0,p_namakeluarga=0,p_no_peserta=0,
            p_kelurahan=0,p_kecamatan=0,p_kabupaten=0,p_pekerjaanpj=0,
            p_alamatpj=0,p_kelurahanpj=0,p_kecamatanpj=0,p_kabupatenpj=0,jmlhari=0,
            p_propinsi=0,p_propinsipj=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String kdkel="",kdkec="",kdkab="",kdprop="",nosisrute="",BASENOREG="",URUTNOREG="",link="",klg="SAUDARA",statuspasien="",pengurutan="",tahun="",bulan="",posisitahun="",awalantahun="",awalanbulan="",
            no_ktp="",tmp_lahir="",nm_ibu="",alamat="",pekerjaan="",no_tlp="",tglkkl="0000-00-00",
            umur="",namakeluarga="",no_peserta="",kelurahan="",kecamatan="",sttsumur="",
            kabupaten="",pekerjaanpj="",alamatpj="",kelurahanpj="",kecamatanpj="",
            kabupatenpj="",hariawal="",requestJson,URL="",nosep="",user="",prb="",peserta="",
            penjamin="",jasaraharja="",BPJS="",Taspen="",Asabri="",status="Baru",propinsi="",propinsipj="",
            tampilkantni=Sequel.cariIsi("select tampilkan_tni_polri from set_tni_polri");
    private PreparedStatement ps,pskelengkapan,pscariumur,pssetalamat,pstni,pspolri;
    private ResultSet rs,rs2;
    private double biaya=0;
    private BPJSCekNIK cekViaBPJS=new BPJSCekNIK();
    private boolean empt=false;
    private HttpHeaders headers;
    private HttpHeaders headers2;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    

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
                        TNik.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),3).toString());
                    }                            
                }
                if(pasien.getTable2().getSelectedRow()!= -1){ 
                    if(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),3).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya NIK...!");
                    }else{
                        TNik.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),3).toString());
                    }  
                }
                if(pasien.getTable3().getSelectedRow()!= -1){ 
                    if(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),3).toString().equals("")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien tidak punya NIK...!");
                    }else{
                        TNik.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),3).toString());
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
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    TPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),3).toString());
                    isNumber();
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
        
        pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.penjab.getTable().getSelectedRow()!= -1){
                        Kdpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),1).toString());
                        nmpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),2).toString());
                        Kdpnj.requestFocus();
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
        
        pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.penjab.dispose();
                    } 
                }                                   
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.kab.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kabupaten.setText(pasien.kab.getTable().getValueAt(pasien.kab.getTable().getSelectedRow(),0).toString());
                            kdkab=pasien.kab.getTable().getValueAt(pasien.kab.getTable().getSelectedRow(),1).toString();
                            Kabupaten.requestFocus();
                        }else if(pilih==2){                    
                            KabupatenPj.setText(pasien.kab.getTable().getValueAt(pasien.kab.getTable().getSelectedRow(),0).toString());
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
        
        pasien.kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.kec.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kecamatan.setText(pasien.kec.getTable().getValueAt(pasien.kec.getTable().getSelectedRow(),0).toString());
                            kdkec=pasien.kec.getTable().getValueAt(pasien.kec.getTable().getSelectedRow(),1).toString();
                            Kecamatan.requestFocus();
                        }else if(pilih==2){                    
                            KecamatanPj.setText(pasien.kec.getTable().getValueAt(pasien.kec.getTable().getSelectedRow(),0).toString());
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
        
        pasien.kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.kel.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Kelurahan.setText(pasien.kel.getTable().getValueAt(pasien.kel.getTable().getSelectedRow(),0).toString());
                            kdkel=pasien.kel.getTable().getValueAt(pasien.kel.getTable().getSelectedRow(),1).toString();
                            Kelurahan.requestFocus();
                        }else if(pilih==2){                    
                            KelurahanPj.setText(pasien.kel.getTable().getValueAt(pasien.kel.getTable().getSelectedRow(),0).toString());
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
        
        pasien.perusahaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.perusahaan.getTable().getSelectedRow()!= -1){
                        kdperusahaan.setText(pasien.perusahaan.getTable().getValueAt(pasien.perusahaan.getTable().getSelectedRow(),0).toString());
                        nmperusahaan.setText(pasien.perusahaan.getTable().getValueAt(pasien.perusahaan.getTable().getSelectedRow(),1).toString());
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
        
        pasien.perusahaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.perusahaan.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.golongantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.golongantni.getTable().getSelectedRow()!= -1){
                        kdgolongantni.setText(pasien.golongantni.getTable().getValueAt(pasien.golongantni.getTable().getSelectedRow(),0).toString());
                        nmgolongantni.setText(pasien.golongantni.getTable().getValueAt(pasien.golongantni.getTable().getSelectedRow(),1).toString());
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
        
        pasien.golongantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.golongantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.jabatantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.jabatantni.getTable().getSelectedRow()!= -1){
                        kdjabatantni.setText(pasien.jabatantni.getTable().getValueAt(pasien.jabatantni.getTable().getSelectedRow(),0).toString());
                        nmjabatantni.setText(pasien.jabatantni.getTable().getValueAt(pasien.jabatantni.getTable().getSelectedRow(),1).toString());
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
        
        pasien.jabatantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.jabatantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.satuantni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.satuantni.getTable().getSelectedRow()!= -1){
                        kdsatuantni.setText(pasien.satuantni.getTable().getValueAt(pasien.satuantni.getTable().getSelectedRow(),0).toString());
                        nmsatuantni.setText(pasien.satuantni.getTable().getValueAt(pasien.satuantni.getTable().getSelectedRow(),1).toString());
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
        
        pasien.satuantni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.satuantni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.pangkattni.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.pangkattni.getTable().getSelectedRow()!= -1){
                        kdpangkattni.setText(pasien.pangkattni.getTable().getValueAt(pasien.pangkattni.getTable().getSelectedRow(),0).toString());
                        nmpangkattni.setText(pasien.pangkattni.getTable().getValueAt(pasien.pangkattni.getTable().getSelectedRow(),1).toString());
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
        
        pasien.pangkattni.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.pangkattni.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.golonganpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.golonganpolri.getTable().getSelectedRow()!= -1){
                        kdgolonganpolri.setText(pasien.golonganpolri.getTable().getValueAt(pasien.golonganpolri.getTable().getSelectedRow(),0).toString());
                        nmgolonganpolri.setText(pasien.golonganpolri.getTable().getValueAt(pasien.golonganpolri.getTable().getSelectedRow(),1).toString());
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
        
        pasien.golonganpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.golonganpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.jabatanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.jabatanpolri.getTable().getSelectedRow()!= -1){
                        kdjabatanpolri.setText(pasien.jabatanpolri.getTable().getValueAt(pasien.jabatanpolri.getTable().getSelectedRow(),0).toString());
                        nmjabatanpolri.setText(pasien.jabatanpolri.getTable().getValueAt(pasien.jabatanpolri.getTable().getSelectedRow(),1).toString());
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
        
        pasien.jabatanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.jabatanpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.satuanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.satuanpolri.getTable().getSelectedRow()!= -1){
                        kdsatuanpolri.setText(pasien.satuanpolri.getTable().getValueAt(pasien.satuanpolri.getTable().getSelectedRow(),0).toString());
                        nmsatuanpolri.setText(pasien.satuanpolri.getTable().getValueAt(pasien.satuanpolri.getTable().getSelectedRow(),1).toString());
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
        
        pasien.satuanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.satuanpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.pangkatpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.pangkatpolri.getTable().getSelectedRow()!= -1){
                        kdpangkatpolri.setText(pasien.pangkatpolri.getTable().getValueAt(pasien.pangkatpolri.getTable().getSelectedRow(),0).toString());
                        nmpangkatpolri.setText(pasien.pangkatpolri.getTable().getValueAt(pasien.pangkatpolri.getTable().getSelectedRow(),1).toString());
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
        
        pasien.pangkatpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.pangkatpolri.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.bahasa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.bahasa.getTable().getSelectedRow()!= -1){
                        kdbahasa.setText(pasien.bahasa.getTable().getValueAt(pasien.bahasa.getTable().getSelectedRow(),0).toString());
                        nmbahasa.setText(pasien.bahasa.getTable().getValueAt(pasien.bahasa.getTable().getSelectedRow(),1).toString());
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
        
        pasien.bahasa.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.bahasa.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.suku.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.suku.getTable().getSelectedRow()!= -1){
                        kdsuku.setText(pasien.suku.getTable().getValueAt(pasien.suku.getTable().getSelectedRow(),0).toString());
                        nmsukubangsa.setText(pasien.suku.getTable().getValueAt(pasien.suku.getTable().getSelectedRow(),1).toString());
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
        
        pasien.suku.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.suku.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasien.prop.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.prop.getTable().getSelectedRow()!= -1){
                        if(pilih==1){                    
                            Propinsi.setText(pasien.prop.getTable().getValueAt(pasien.prop.getTable().getSelectedRow(),0).toString());
                            kdprop=pasien.prop.getTable().getValueAt(pasien.prop.getTable().getSelectedRow(),1).toString();
                            Propinsi.requestFocus();
                        }else if(pilih==2){                    
                            PropinsiPj.setText(pasien.prop.getTable().getValueAt(pasien.prop.getTable().getSelectedRow(),0).toString());
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
        
        pasien.cacat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(pasien.cacat.getTable().getSelectedRow()!= -1){
                        kdcacat.setText(pasien.cacat.getTable().getValueAt(pasien.cacat.getTable().getSelectedRow(),0).toString());
                        nmcacat.setText(pasien.cacat.getTable().getValueAt(pasien.cacat.getTable().getSelectedRow(),1).toString());
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
        
        pasien.cacat.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBridgingBPJS")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.cacat.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dpjp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dpjp.getTable().getSelectedRow()!= -1){  
                    KdDPJP.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(),1).toString());
                    NmDPJP.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(),2).toString());
                    try{
                        ps=koneksi.prepareStatement(
                                "select maping_dokter_dpjpvclaim.kd_dokter,dokter.nm_dokter from maping_dokter_dpjpvclaim inner join dokter "+
                                "on maping_dokter_dpjpvclaim.kd_dokter=dokter.kd_dokter where maping_dokter_dpjpvclaim.kd_dokter_bpjs=?");
                        try{
                            ps.setString(1,KdDPJP.getText());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                kddokter.setText(rs.getString("kd_dokter"));
                                TDokter.setText(rs.getString("nm_dokter"));
                            }
                        }catch(Exception ex){
                            System.out.println("Notif : "+ex);
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }  
                        }
                    }catch(Exception ex){
                        System.out.println("Notif : "+ex);
                    }
                    KdDPJP.requestFocus();             
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
        
        dpjp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dpjp.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        propinsikll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(propinsikll.getTable().getSelectedRow()!= -1){                   
                    KdPropinsi.setText(propinsikll.getTable().getValueAt(propinsikll.getTable().getSelectedRow(),1).toString());
                    NmPropinsi.setText(propinsikll.getTable().getValueAt(propinsikll.getTable().getSelectedRow(),2).toString());
                    KdPropinsi.requestFocus();
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
        
        propinsikll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    propinsikll.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kabupatenkll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupatenkll.getTable().getSelectedRow()!= -1){                   
                    KdKabupaten.setText(kabupatenkll.getTable().getValueAt(kabupatenkll.getTable().getSelectedRow(),1).toString());
                    NmKabupaten.setText(kabupatenkll.getTable().getValueAt(kabupatenkll.getTable().getSelectedRow(),2).toString());
                    KdKabupaten.requestFocus();
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
        
        kabupatenkll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kabupatenkll.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kecamatankll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kecamatankll.getTable().getSelectedRow()!= -1){                   
                    KdKecamatan.setText(kecamatankll.getTable().getValueAt(kecamatankll.getTable().getSelectedRow(),1).toString());
                    NmKecamatan.setText(kecamatankll.getTable().getValueAt(kecamatankll.getTable().getSelectedRow(),2).toString());
                    KdKecamatan.requestFocus();
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
        
        kecamatankll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kecamatankll.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        skdp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(skdp.getTable().getSelectedRow()!= -1){                   
                    NoSKDP.setText(skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(),11).toString());
                    NoSKDP.requestFocus();
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
        
        skdp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    skdp.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        historiPelayanan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(historiPelayanan.getTable().getSelectedRow()!= -1){         
                    if((historiPelayanan.getTable().getSelectedColumn()==6)||(historiPelayanan.getTable().getSelectedColumn()==7)){
                        NoRujukan.setText(historiPelayanan.getTable().getValueAt(historiPelayanan.getTable().getSelectedRow(),historiPelayanan.getTable().getSelectedColumn()).toString());
                    }
                }  
                NoRujukan.requestFocus();
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
        
        historiPelayanan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    historiPelayanan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        ChkCari.setSelected(false);
        isForm();
        TNik.setDocument(new batasInput((int)80).getKata(TNik));
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
        NoRujukan.setDocument(new batasInput((byte)40).getKata(NoRujukan));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));        
        NoSKDP.setDocument(new batasInput((byte)6).getKata(NoSKDP));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        NoSEPSuplesi.setDocument(new batasInput((byte)40).getKata(NoSEPSuplesi));
        Propinsi.setDocument(new batasInput((byte)30).getFilter(Propinsi));
        PropinsiPj.setDocument(new batasInput((byte)30).getFilter(PropinsiPj));
        EMail.setDocument(new batasInput((byte)50).getFilter(EMail));
        NIP.setDocument(new batasInput((byte)30).getFilter(NIP));
        try{
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
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
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml")); 
            link=prop.getProperty("URLAPIBPJS");
            URUTNOREG=prop.getProperty("URUTNOREG");
            BASENOREG=prop.getProperty("BASENOREG");
        } catch (Exception e) {
            URUTNOREG="";
            BASENOREG="";
            System.out.println("E : "+e);
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
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
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
        jLabel40 = new widget.Label();
        nmsukubangsa = new widget.TextBox();
        BtnSuku = new widget.Button();
        jLabel41 = new widget.Label();
        nmbahasa = new widget.TextBox();
        BtnBahasa = new widget.Button();
        jLabel42 = new widget.Label();
        nmcacat = new widget.TextBox();
        BtnCacat = new widget.Button();
        jLabel43 = new widget.Label();
        nmperusahaan = new widget.TextBox();
        BtnPerusahaan = new widget.Button();
        jLabel44 = new widget.Label();
        NIP = new widget.TextBox();
        jLabel45 = new widget.Label();
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
        jLabel5 = new widget.Label();
        TNoReg = new widget.TextBox();
        jLabel36 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel37 = new widget.Label();
        AsalRujukan = new widget.ComboBox();
        jLabel38 = new widget.Label();
        Eksekutif = new widget.ComboBox();
        LabelKelas1 = new widget.Label();
        COB = new widget.ComboBox();
        jLabel39 = new widget.Label();
        ChkJasaRaharja = new widget.CekBox();
        ChkBPJSTenaga = new widget.CekBox();
        ChkTaspen = new widget.CekBox();
        ChkAsa = new widget.CekBox();
        LabelPoli2 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        btnDPJP = new widget.Button();
        jLabel46 = new widget.Label();
        Katarak = new widget.ComboBox();
        jLabel47 = new widget.Label();
        TanggalKKL = new widget.Tanggal();
        jLabel48 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel49 = new widget.Label();
        Suplesi = new widget.ComboBox();
        jLabel50 = new widget.Label();
        NoSEPSuplesi = new widget.TextBox();
        LabelPoli3 = new widget.Label();
        KdPropinsi = new widget.TextBox();
        NmPropinsi = new widget.TextBox();
        btnPropinsi = new widget.Button();
        btnKabupaten = new widget.Button();
        NmKabupaten = new widget.TextBox();
        KdKabupaten = new widget.TextBox();
        LabelPoli4 = new widget.Label();
        LabelPoli5 = new widget.Label();
        KdKecamatan = new widget.TextBox();
        NmKecamatan = new widget.TextBox();
        btnKecamatan = new widget.Button();
        jLabel51 = new widget.Label();
        NoSKDP = new widget.TextBox();
        btnSKDP = new widget.Button();
        btnRiwayat = new widget.Button();
        ChkCari = new widget.CekBox();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        TNik = new widget.TextBox();
        btnPasien = new widget.Button();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnCari1 = new widget.Button();
        BtnKeluar = new widget.Button();

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "L", "P" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });

        DTPLahir.setEditable(false);
        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
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

        MnDocument.setBackground(new java.awt.Color(255, 255, 254));
        MnDocument.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDocument.setForeground(new java.awt.Color(50,50,50));
        MnDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDocument.setText("Cetak Document");
        MnDocument.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDocument.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDocument.setName("MnDocument"); // NOI18N
        MnDocument.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDocumentActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDocument);

        ppPengajuan.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(50,50,50));
        ppPengajuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan.setText("Pengajuan SEP");
        ppPengajuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan.setName("ppPengajuan"); // NOI18N
        ppPengajuan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuanBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan);

        ppPengajuan1.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan1.setForeground(new java.awt.Color(50,50,50));
        ppPengajuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan1.setText("Aproval SEP");
        ppPengajuan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan1.setName("ppPengajuan1"); // NOI18N
        ppPengajuan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan1BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan1);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Peserta BPJS Berdasarkan NIK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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
        PanelInput.setPreferredSize(new java.awt.Dimension(200, 907));
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
        FormKelengkapanPasien.setPreferredSize(new java.awt.Dimension(1000, 485));
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
        CMbGd.setBounds(345, 55, 90, 23);

        jLabel9.setText("G.Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormKelengkapanPasien.add(jLabel9);
        jLabel9.setBounds(256, 55, 85, 23);

        jLabel13.setText("Tempat Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormKelengkapanPasien.add(jLabel13);
        jLabel13.setBounds(3, 55, 100, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormKelengkapanPasien.add(jLabel18);
        jLabel18.setBounds(3, 85, 100, 23);

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

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormKelengkapanPasien.add(jLabel19);
        jLabel19.setBounds(228, 85, 65, 23);

        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CmbStts);
        CmbStts.setBounds(297, 85, 138, 23);

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

        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        DTPDaftar.setDisplayFormat("dd-MM-yyyy");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);
        DTPDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPDaftarKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(DTPDaftar);
        DTPDaftar.setBounds(345, 145, 90, 23);

        jLabel22.setText("Pertama Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormKelengkapanPasien.add(jLabel22);
        jLabel22.setBounds(241, 145, 100, 23);

        jLabel24.setText("Pendidikan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormKelengkapanPasien.add(jLabel24);
        jLabel24.setBounds(256, 115, 85, 23);

        CMbPnd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        CMbPnd.setName("CMbPnd"); // NOI18N
        CMbPnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbPndKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CMbPnd);
        CMbPnd.setBounds(345, 115, 90, 23);

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
        nmpnj.setBounds(189, 205, 216, 23);

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
        BtnPenjab.setBounds(407, 205, 28, 23);

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
        NmIbu.setBounds(107, 175, 328, 23);

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

        jLabel40.setText("Suku/Bangsa :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormKelengkapanPasien.add(jLabel40);
        jLabel40.setBounds(3, 235, 100, 23);

        nmsukubangsa.setEditable(false);
        nmsukubangsa.setName("nmsukubangsa"); // NOI18N
        FormKelengkapanPasien.add(nmsukubangsa);
        nmsukubangsa.setBounds(107, 235, 298, 23);

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
        BtnSuku.setBounds(407, 235, 28, 23);

        jLabel41.setText("Bahasa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormKelengkapanPasien.add(jLabel41);
        jLabel41.setBounds(3, 265, 100, 23);

        nmbahasa.setEditable(false);
        nmbahasa.setName("nmbahasa"); // NOI18N
        FormKelengkapanPasien.add(nmbahasa);
        nmbahasa.setBounds(107, 265, 298, 23);

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
        BtnBahasa.setBounds(407, 265, 28, 23);

        jLabel42.setText("Cacat Fisik :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormKelengkapanPasien.add(jLabel42);
        jLabel42.setBounds(3, 295, 100, 23);

        nmcacat.setEditable(false);
        nmcacat.setName("nmcacat"); // NOI18N
        FormKelengkapanPasien.add(nmcacat);
        nmcacat.setBounds(107, 295, 298, 23);

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
        BtnCacat.setBounds(407, 295, 28, 23);

        jLabel43.setText("Instansi Pasien :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormKelengkapanPasien.add(jLabel43);
        jLabel43.setBounds(430, 265, 100, 23);

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

        jLabel44.setText("NIP/NRP :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormKelengkapanPasien.add(jLabel44);
        jLabel44.setBounds(690, 295, 84, 23);

        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(NIP);
        NIP.setBounds(778, 295, 130, 23);

        jLabel45.setText("Email :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormKelengkapanPasien.add(jLabel45);
        jLabel45.setBounds(430, 295, 100, 23);

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
        chkTNI.setBounds(10, 335, 95, 23);

        LabelGolonganTNI.setText("Golongan :");
        LabelGolonganTNI.setName("LabelGolonganTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelGolonganTNI);
        LabelGolonganTNI.setBounds(4, 355, 112, 23);

        nmgolongantni.setEditable(false);
        nmgolongantni.setName("nmgolongantni"); // NOI18N
        FormKelengkapanPasien.add(nmgolongantni);
        nmgolongantni.setBounds(120, 355, 270, 23);

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
        BtnGolonganTNI.setBounds(390, 355, 28, 23);

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
        BtnSatuanTNI.setBounds(390, 385, 28, 23);

        nmsatuantni.setEditable(false);
        nmsatuantni.setName("nmsatuantni"); // NOI18N
        FormKelengkapanPasien.add(nmsatuantni);
        nmsatuantni.setBounds(120, 385, 270, 23);

        LabelSatuanTNI.setText("Kesatuan :");
        LabelSatuanTNI.setName("LabelSatuanTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelSatuanTNI);
        LabelSatuanTNI.setBounds(4, 385, 112, 23);

        LabelPangkatTNI.setText("Pangkat :");
        LabelPangkatTNI.setName("LabelPangkatTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelPangkatTNI);
        LabelPangkatTNI.setBounds(4, 415, 112, 23);

        nmpangkattni.setEditable(false);
        nmpangkattni.setName("nmpangkattni"); // NOI18N
        FormKelengkapanPasien.add(nmpangkattni);
        nmpangkattni.setBounds(120, 415, 270, 23);

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
        BtnPangkatTNI.setBounds(390, 415, 28, 23);

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
        BtnJabatanTNI.setBounds(390, 445, 28, 23);

        nmjabatantni.setEditable(false);
        nmjabatantni.setName("nmjabatantni"); // NOI18N
        FormKelengkapanPasien.add(nmjabatantni);
        nmjabatantni.setBounds(120, 445, 270, 23);

        LabelJabatanTNI.setText("Jabatan :");
        LabelJabatanTNI.setName("LabelJabatanTNI"); // NOI18N
        FormKelengkapanPasien.add(LabelJabatanTNI);
        LabelJabatanTNI.setBounds(4, 445, 112, 23);

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
        chkPolri.setBounds(440, 335, 130, 23);

        LabelGolonganPolri.setText("Golongan :");
        LabelGolonganPolri.setName("LabelGolonganPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelGolonganPolri);
        LabelGolonganPolri.setBounds(441, 355, 135, 23);

        nmgolonganpolri.setEditable(false);
        nmgolonganpolri.setName("nmgolonganpolri"); // NOI18N
        FormKelengkapanPasien.add(nmgolonganpolri);
        nmgolonganpolri.setBounds(580, 355, 273, 23);

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
        BtnGolonganPolri.setBounds(855, 355, 28, 23);

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
        BtnSatuanPolri.setBounds(855, 385, 28, 23);

        nmsatuanpolri.setEditable(false);
        nmsatuanpolri.setName("nmsatuanpolri"); // NOI18N
        FormKelengkapanPasien.add(nmsatuanpolri);
        nmsatuanpolri.setBounds(580, 385, 273, 23);

        LabelSatuanPolri.setText("Kesatuan :");
        LabelSatuanPolri.setName("LabelSatuanPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelSatuanPolri);
        LabelSatuanPolri.setBounds(441, 385, 135, 23);

        LabelPangkatPolri.setText("Pangkat :");
        LabelPangkatPolri.setName("LabelPangkatPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelPangkatPolri);
        LabelPangkatPolri.setBounds(441, 415, 135, 23);

        nmpangkatpolri.setEditable(false);
        nmpangkatpolri.setName("nmpangkatpolri"); // NOI18N
        FormKelengkapanPasien.add(nmpangkatpolri);
        nmpangkatpolri.setBounds(580, 415, 273, 23);

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
        BtnPangkatPolri.setBounds(855, 415, 28, 23);

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
        BtnJabatanPolri.setBounds(855, 445, 28, 23);

        nmjabatanpolri.setEditable(false);
        nmjabatanpolri.setName("nmjabatanpolri"); // NOI18N
        FormKelengkapanPasien.add(nmjabatanpolri);
        nmjabatanpolri.setBounds(580, 445, 273, 23);

        LabelJabatanPolri.setText("Jabatan :");
        LabelJabatanPolri.setName("LabelJabatanPolri"); // NOI18N
        FormKelengkapanPasien.add(LabelJabatanPolri);
        LabelJabatanPolri.setBounds(441, 445, 135, 23);

        FormInput.add(FormKelengkapanPasien);

        FormKelengkapanSEP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "::[ Kelengkapan Data SEP, Registrasi & Kamar Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        FormKelengkapanSEP.setName("FormKelengkapanSEP"); // NOI18N
        FormKelengkapanSEP.setOpaque(false);
        FormKelengkapanSEP.setPreferredSize(new java.awt.Dimension(1000, 337));
        FormKelengkapanSEP.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormKelengkapanSEP.add(jLabel4);
        jLabel4.setBounds(3, 25, 100, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormKelengkapanSEP.add(TNoRw);
        TNoRw.setBounds(107, 25, 190, 23);

        jLabel23.setText("Tgl.SEP :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel23);
        jLabel23.setBounds(495, 55, 90, 23);

        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019 23:16:26" }));
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
        TanggalSEP.setBounds(590, 55, 160, 23);

        jLabel30.setText("Tanggal :");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel30);
        jLabel30.setBounds(331, 265, 60, 23);

        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalRujuk);
        TanggalRujuk.setBounds(395, 265, 90, 23);

        jLabel31.setText("No.Rujuk :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel31);
        jLabel31.setBounds(495, 25, 90, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoRujukan);
        NoRujukan.setBounds(589, 25, 160, 23);

        jLabel10.setText("PPK Pelayanan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormKelengkapanSEP.add(jLabel10);
        jLabel10.setBounds(3, 55, 100, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormKelengkapanSEP.add(KdPPK);
        KdPPK.setBounds(107, 55, 70, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormKelengkapanSEP.add(NmPPK);
        NmPPK.setBounds(179, 55, 306, 23);

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
        btnPPKRujukan.setBounds(457, 115, 28, 23);

        jLabel11.setText("PPK Rujukan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormKelengkapanSEP.add(jLabel11);
        jLabel11.setBounds(3, 115, 100, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(107, 115, 90, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan.setHighlighter(null);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(199, 115, 255, 23);

        jLabel15.setText("Diagnosa Awal :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormKelengkapanSEP.add(jLabel15);
        jLabel15.setBounds(3, 145, 100, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        FormKelengkapanSEP.add(KdPenyakit);
        KdPenyakit.setBounds(107, 145, 90, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit.setHighlighter(null);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        FormKelengkapanSEP.add(NmPenyakit);
        NmPenyakit.setBounds(199, 145, 255, 23);

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
        btnDiagnosa.setBounds(457, 145, 28, 23);

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
        btnPoli.setBounds(457, 175, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormKelengkapanSEP.add(NmPoli);
        NmPoli.setBounds(199, 175, 255, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormKelengkapanSEP.add(KdPoli);
        KdPoli.setBounds(107, 175, 90, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli);
        LabelPoli.setBounds(3, 175, 100, 23);

        jLabel32.setText("Jns.Pelayanan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormKelengkapanSEP.add(jLabel32);
        jLabel32.setBounds(3, 235, 100, 23);

        jLabel33.setText("Catatan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormKelengkapanSEP.add(jLabel33);
        jLabel33.setBounds(3, 295, 100, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Catatan);
        Catatan.setBounds(107, 295, 378, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Ranap", "2. Ralan" }));
        JenisPelayanan.setSelectedIndex(1);
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
        JenisPelayanan.setBounds(107, 235, 93, 23);

        LabelKelas.setText("Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas);
        LabelKelas.setBounds(200, 235, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. kelas 3" }));
        Kelas.setSelectedIndex(2);
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Kelas);
        Kelas.setBounds(244, 235, 98, 23);

        jLabel34.setText("KLL :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormKelengkapanSEP.add(jLabel34);
        jLabel34.setBounds(774, 25, 40, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1. Ya" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LakaLantas);
        LakaLantas.setBounds(818, 25, 90, 23);

        jLabel5.setText("No. Reg. :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormKelengkapanSEP.add(jLabel5);
        jLabel5.setBounds(331, 25, 70, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TNoReg);
        TNoReg.setBounds(405, 25, 80, 23);

        jLabel36.setText("Dr Dituju/DPJP :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormKelengkapanSEP.add(jLabel36);
        jLabel36.setBounds(495, 295, 90, 23);

        kddokter.setEditable(false);
        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(kddokter);
        kddokter.setBounds(589, 295, 90, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormKelengkapanSEP.add(TDokter);
        TDokter.setBounds(681, 295, 197, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(BtnDokter);
        BtnDokter.setBounds(880, 295, 28, 23);

        jLabel37.setText("Asal Rujukan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormKelengkapanSEP.add(jLabel37);
        jLabel37.setBounds(3, 85, 100, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(AsalRujukan);
        AsalRujukan.setBounds(107, 85, 130, 23);

        jLabel38.setText("Eksekutif :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormKelengkapanSEP.add(jLabel38);
        jLabel38.setBounds(3, 265, 100, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Eksekutif);
        Eksekutif.setBounds(107, 265, 93, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas1);
        LabelKelas1.setBounds(200, 265, 40, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(COB);
        COB.setBounds(244, 265, 98, 23);

        jLabel39.setText("Penjamin Laka :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormKelengkapanSEP.add(jLabel39);
        jLabel39.setBounds(495, 85, 90, 23);

        ChkJasaRaharja.setText("Jasa raharja PT");
        ChkJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkJasaRaharja.setName("ChkJasaRaharja"); // NOI18N
        ChkJasaRaharja.setOpaque(false);
        ChkJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJasaRaharjaActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(ChkJasaRaharja);
        ChkJasaRaharja.setBounds(589, 85, 110, 23);

        ChkBPJSTenaga.setText("BPJS Ketenagakerjaan");
        ChkBPJSTenaga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkBPJSTenaga.setName("ChkBPJSTenaga"); // NOI18N
        ChkBPJSTenaga.setOpaque(false);
        ChkBPJSTenaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkBPJSTenagaActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(ChkBPJSTenaga);
        ChkBPJSTenaga.setBounds(758, 85, 150, 23);

        ChkTaspen.setText("TASPEN PT");
        ChkTaspen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkTaspen.setName("ChkTaspen"); // NOI18N
        ChkTaspen.setOpaque(false);
        ChkTaspen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkTaspenActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(ChkTaspen);
        ChkTaspen.setBounds(589, 115, 80, 23);

        ChkAsa.setText("ASABRI PT");
        ChkAsa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkAsa.setName("ChkAsa"); // NOI18N
        ChkAsa.setOpaque(false);
        ChkAsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAsaActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(ChkAsa);
        ChkAsa.setBounds(758, 115, 150, 23);

        LabelPoli2.setText("DPJP SEP :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli2);
        LabelPoli2.setBounds(3, 205, 100, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormKelengkapanSEP.add(KdDPJP);
        KdDPJP.setBounds(107, 205, 90, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormKelengkapanSEP.add(NmDPJP);
        NmDPJP.setBounds(199, 205, 255, 23);

        btnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJP.setMnemonic('X');
        btnDPJP.setToolTipText("Alt+X");
        btnDPJP.setName("btnDPJP"); // NOI18N
        btnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPActionPerformed(evt);
            }
        });
        btnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJPKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnDPJP);
        btnDPJP.setBounds(457, 205, 28, 23);

        jLabel46.setText("Katarak :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormKelengkapanSEP.add(jLabel46);
        jLabel46.setBounds(331, 235, 60, 23);

        Katarak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Katarak.setName("Katarak"); // NOI18N
        Katarak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KatarakKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Katarak);
        Katarak.setBounds(395, 235, 90, 23);

        jLabel47.setText("Tgl.KLL :");
        jLabel47.setName("jLabel47"); // NOI18N
        jLabel47.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel47);
        jLabel47.setBounds(744, 55, 70, 23);

        TanggalKKL.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKKL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        TanggalKKL.setDisplayFormat("dd-MM-yyyy");
        TanggalKKL.setName("TanggalKKL"); // NOI18N
        TanggalKKL.setOpaque(false);
        TanggalKKL.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalKKL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKKLKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalKKL);
        TanggalKKL.setBounds(818, 55, 90, 23);

        jLabel48.setText("Keterangan :");
        jLabel48.setName("jLabel48"); // NOI18N
        jLabel48.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel48);
        jLabel48.setBounds(495, 145, 90, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Keterangan);
        Keterangan.setBounds(589, 145, 319, 23);

        jLabel49.setText("Suplesi :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormKelengkapanSEP.add(jLabel49);
        jLabel49.setBounds(495, 175, 90, 23);

        Suplesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Suplesi.setName("Suplesi"); // NOI18N
        Suplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuplesiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Suplesi);
        Suplesi.setBounds(589, 175, 90, 23);

        jLabel50.setText("No.SEP Suplesi :");
        jLabel50.setName("jLabel50"); // NOI18N
        jLabel50.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel50);
        jLabel50.setBounds(680, 175, 85, 23);

        NoSEPSuplesi.setHighlighter(null);
        NoSEPSuplesi.setName("NoSEPSuplesi"); // NOI18N
        NoSEPSuplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPSuplesiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoSEPSuplesi);
        NoSEPSuplesi.setBounds(768, 175, 140, 23);

        LabelPoli3.setText("Propinsi KLL :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli3);
        LabelPoli3.setBounds(495, 205, 90, 23);

        KdPropinsi.setEditable(false);
        KdPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        KdPropinsi.setHighlighter(null);
        KdPropinsi.setName("KdPropinsi"); // NOI18N
        FormKelengkapanSEP.add(KdPropinsi);
        KdPropinsi.setBounds(589, 205, 70, 23);

        NmPropinsi.setEditable(false);
        NmPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        NmPropinsi.setHighlighter(null);
        NmPropinsi.setName("NmPropinsi"); // NOI18N
        FormKelengkapanSEP.add(NmPropinsi);
        NmPropinsi.setBounds(661, 205, 217, 23);

        btnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPropinsi.setMnemonic('X');
        btnPropinsi.setToolTipText("Alt+X");
        btnPropinsi.setName("btnPropinsi"); // NOI18N
        btnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropinsiActionPerformed(evt);
            }
        });
        btnPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPropinsiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnPropinsi);
        btnPropinsi.setBounds(880, 205, 28, 23);

        btnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKabupaten.setMnemonic('X');
        btnKabupaten.setToolTipText("Alt+X");
        btnKabupaten.setName("btnKabupaten"); // NOI18N
        btnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKabupatenActionPerformed(evt);
            }
        });
        btnKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKabupatenKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnKabupaten);
        btnKabupaten.setBounds(880, 235, 28, 23);

        NmKabupaten.setEditable(false);
        NmKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        NmKabupaten.setHighlighter(null);
        NmKabupaten.setName("NmKabupaten"); // NOI18N
        FormKelengkapanSEP.add(NmKabupaten);
        NmKabupaten.setBounds(661, 235, 217, 23);

        KdKabupaten.setEditable(false);
        KdKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        KdKabupaten.setHighlighter(null);
        KdKabupaten.setName("KdKabupaten"); // NOI18N
        FormKelengkapanSEP.add(KdKabupaten);
        KdKabupaten.setBounds(589, 235, 70, 23);

        LabelPoli4.setText("Kabupaten KLL :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli4);
        LabelPoli4.setBounds(495, 235, 90, 23);

        LabelPoli5.setText("Kecamatan KLL :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli5);
        LabelPoli5.setBounds(495, 265, 90, 23);

        KdKecamatan.setEditable(false);
        KdKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        KdKecamatan.setHighlighter(null);
        KdKecamatan.setName("KdKecamatan"); // NOI18N
        FormKelengkapanSEP.add(KdKecamatan);
        KdKecamatan.setBounds(589, 265, 70, 23);

        NmKecamatan.setEditable(false);
        NmKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        NmKecamatan.setHighlighter(null);
        NmKecamatan.setName("NmKecamatan"); // NOI18N
        FormKelengkapanSEP.add(NmKecamatan);
        NmKecamatan.setBounds(661, 265, 217, 23);

        btnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKecamatan.setMnemonic('X');
        btnKecamatan.setToolTipText("Alt+X");
        btnKecamatan.setName("btnKecamatan"); // NOI18N
        btnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKecamatanActionPerformed(evt);
            }
        });
        btnKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKecamatanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnKecamatan);
        btnKecamatan.setBounds(880, 265, 28, 23);

        jLabel51.setText("No.SKDP :");
        jLabel51.setName("jLabel51"); // NOI18N
        jLabel51.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel51);
        jLabel51.setBounds(270, 85, 70, 23);

        NoSKDP.setHighlighter(null);
        NoSKDP.setName("NoSKDP"); // NOI18N
        NoSKDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSKDPKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoSKDP);
        NoSKDP.setBounds(344, 85, 110, 23);

        btnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSKDP.setMnemonic('X');
        btnSKDP.setToolTipText("Alt+X");
        btnSKDP.setName("btnSKDP"); // NOI18N
        btnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSKDPActionPerformed(evt);
            }
        });
        btnSKDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSKDPKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnSKDP);
        btnSKDP.setBounds(457, 85, 28, 23);

        btnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRiwayat.setMnemonic('X');
        btnRiwayat.setToolTipText("Alt+X");
        btnRiwayat.setName("btnRiwayat"); // NOI18N
        btnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatActionPerformed(evt);
            }
        });
        FormKelengkapanSEP.add(btnRiwayat);
        btnRiwayat.setBounds(750, 25, 28, 23);

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

        TNik.setName("TNik"); // NOI18N
        TNik.setPreferredSize(new java.awt.Dimension(250, 23));
        TNik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNikKeyPressed(evt);
            }
        });
        panelGlass6.add(TNik);

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

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('E');
        BtnCari1.setText("Cari");
        BtnCari1.setToolTipText("Alt+E");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnCari1);

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
        pasien.kab.dispose();
        pasien.kec.dispose();
        pasien.kel.dispose();
        dokter.dispose();
        kamar.dispose();
        pasien.penjab.dispose();
        pasien.prop.dispose();
        pasien.perusahaan.dispose();
        pasien.bahasa.dispose();
        pasien.cacat.dispose();
        pasien.suku.dispose();
        pasien.golongantni.dispose();
        pasien.satuantni.dispose();
        pasien.pangkattni.dispose();
        pasien.jabatantni.dispose();
        pasien.golonganpolri.dispose();
        pasien.satuanpolri.dispose();
        pasien.pangkatpolri.dispose();
        pasien.jabatanpolri.dispose();
        pilihan.dispose();
        dpjp.dispose();
        skdp.dispose();
        propinsikll.dispose();
        kabupatenkll.dispose();
        kecamatankll.dispose();
        akses.setAktif(false);
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

    private void TNikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNikKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TNikKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt,TNik,BtnPrint);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(ChkCari.isSelected()==true){
            ChkCari.setSelected(false);
            isForm();
        }       
        ChkRM.setSelected(true);
        emptTeks();
        tampil(TNik.getText());
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TNik,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void ChkCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCariActionPerformed
        if(tabMode.getRowCount()==0){
            nosep="";
            ChkCari.setSelected(false);
            isForm();
            JOptionPane.showMessageDialog(null,"Maaf, data peserta BPJS masih kosong. Silahkan lakukan pencarian berdasar No.Kartu...!!!!");
        }else{
            TTmp.requestFocus();
            isForm();
        }            
    }//GEN-LAST:event_ChkCariActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(empt==false){
            emptTeks();
            BtnSimpan.setVisible(false);
        }else if(empt==true){            
            ChkCari.setSelected(true);
            chkPolri.setSelected(false);
            chkTNI.setSelected(false);
            chkTNI.setSelected(false);
            chkPolri.setSelected(false);     
            BtnGolonganPolri.setEnabled(false);
            BtnSatuanPolri.setEnabled(false);
            BtnJabatanPolri.setEnabled(false);
            BtnPangkatPolri.setEnabled(false);
            BtnGolonganTNI.setEnabled(false);
            BtnSatuanTNI.setEnabled(false);
            BtnJabatanTNI.setEnabled(false);
            BtnPangkatTNI.setEnabled(false);
            R5.setSelected(true);
            LakaLantas.setSelectedIndex(0);
            Katarak.setSelectedIndex(0);        
            Suplesi.setSelectedIndex(0);
            TNo.requestFocus();
            ChkCari.setSelected(false);
            isForm();
        }       
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

    private void ppPengajuanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tbKamar.getRowCount()>0){
            try {
                URL = link+"/Sep/pengajuanSEP";
                headers= new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                requestJson =" {" +
                    "\"request\": {" +
                        "\"t_sep\": {" +
                            "\"noKartu\": \""+TNoPeserta.getText()+"\"," +
                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                            "\"keterangan\": \""+Catatan.getText()+"\"," +
                            "\"user\": \""+user+"\"" +
                        "}" +
                    "}" +
                "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                response = root.path("response");
                if(nameNode.path("code").asText().equals("200")){
                    JOptionPane.showMessageDialog(null,"Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
                }else{
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                }
            }catch (Exception ex) {
                System.out.println("Notifikasi Bridging : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
            NoRujukan.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuanBtnPrintActionPerformed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tbKamar.getRowCount()>0){
            try {
                URL = link+"/Sep/aprovalSEP";
                headers= new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                requestJson =" {" +
                    "\"request\": {" +
                        "\"t_sep\": {" +
                            "\"noKartu\": \""+TNoPeserta.getText()+"\"," +
                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                            "\"keterangan\": \""+Catatan.getText()+"\"," +
                            "\"user\": \""+user+"\"" +
                        "}" +
                    "}" +
                "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                response = root.path("response");
                if(nameNode.path("code").asText().equals("200")){
                    JOptionPane.showMessageDialog(null,"Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
                }else{
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                }
            }catch (Exception ex) {
                System.out.println("Notifikasi Bridging : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
            NoRujukan.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan1BtnPrintActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSDataSEP form=new BPJSDataSEP(null,false);
        form.isCek();
        form.tampil();
        form.tutupInput();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        Valid.pindah(evt, BtnSimpan, BtnKeluar);
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void kdperusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdperusahaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdperusahaanKeyPressed

    private void kdcacatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdcacatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdcacatKeyPressed

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
        akses.setform("DlgBridgingBPJS");
        pasien.penjab.isCek();
        pasien.penjab.onCari();
        pasien.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.penjab.setLocationRelativeTo(internalFrame1);
        pasien.penjab.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pilih=1;
        pasien.kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kel.setLocationRelativeTo(internalFrame1);
        pasien.kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih=1;
        pasien.kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kec.setLocationRelativeTo(internalFrame1);
        pasien.kec.setVisible(true);
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih=1;
        pasien.kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kab.setLocationRelativeTo(internalFrame1);
        pasien.kab.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pilih=2;
        pasien.kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kec.setLocationRelativeTo(internalFrame1);
        pasien.kec.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pilih=2;
        pasien.kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kab.setLocationRelativeTo(internalFrame1);
        pasien.kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenPjActionPerformed

    private void BtnKelurahanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanPjActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih=2;
        pasien.kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kel.setLocationRelativeTo(internalFrame1);
        pasien.kel.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.suku.isCek();
        pasien.suku.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.suku.setLocationRelativeTo(internalFrame1);
        pasien.suku.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.bahasa.isCek();
        pasien.bahasa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.bahasa.setLocationRelativeTo(internalFrame1);
        pasien.bahasa.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.cacat.isCek();
        pasien.cacat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.cacat.setLocationRelativeTo(internalFrame1);
        pasien.cacat.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.perusahaan.isCek();
        pasien.perusahaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.perusahaan.setLocationRelativeTo(internalFrame1);
        pasien.perusahaan.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pilih=1;
        pasien.prop.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.prop.setLocationRelativeTo(internalFrame1);
        pasien.prop.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pilih=2;
        pasien.prop.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.prop.setLocationRelativeTo(internalFrame1);
        pasien.prop.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.golongantni.isCek();
        pasien.golongantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.golongantni.setLocationRelativeTo(internalFrame1);
        pasien.golongantni.setVisible(true);
    }//GEN-LAST:event_BtnGolonganTNIActionPerformed

    private void BtnSatuanTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanTNIActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.satuantni.isCek();
        pasien.satuantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.satuantni.setLocationRelativeTo(internalFrame1);
        pasien.satuantni.setVisible(true);
    }//GEN-LAST:event_BtnSatuanTNIActionPerformed

    private void BtnPangkatTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPangkatTNIActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.pangkattni.isCek();
        pasien.pangkattni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.pangkattni.setLocationRelativeTo(internalFrame1);
        pasien.pangkattni.setVisible(true);
    }//GEN-LAST:event_BtnPangkatTNIActionPerformed

    private void BtnJabatanTNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJabatanTNIActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.jabatantni.isCek();
        pasien.jabatantni.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.jabatantni.setLocationRelativeTo(internalFrame1);
        pasien.jabatantni.setVisible(true);
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
        akses.setform("DlgBridgingBPJS");
        pasien.golonganpolri.isCek();
        pasien.golonganpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.golonganpolri.setLocationRelativeTo(internalFrame1);
        pasien.golonganpolri.setVisible(true);
    }//GEN-LAST:event_BtnGolonganPolriActionPerformed

    private void BtnSatuanPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanPolriActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.satuanpolri.isCek();
        pasien.satuanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.satuanpolri.setLocationRelativeTo(internalFrame1);
        pasien.satuanpolri.setVisible(true);
    }//GEN-LAST:event_BtnSatuanPolriActionPerformed

    private void BtnPangkatPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPangkatPolriActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.pangkatpolri.isCek();
        pasien.pangkatpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.pangkatpolri.setLocationRelativeTo(internalFrame1);
        pasien.pangkatpolri.setVisible(true);
    }//GEN-LAST:event_BtnPangkatPolriActionPerformed

    private void BtnJabatanPolriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJabatanPolriActionPerformed
        akses.setform("DlgBridgingBPJS");
        pasien.jabatanpolri.isCek();
        pasien.jabatanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.jabatanpolri.setLocationRelativeTo(internalFrame1);
        pasien.jabatanpolri.setVisible(true);
    }//GEN-LAST:event_BtnJabatanPolriActionPerformed

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah(evt, LakaLantas,TanggalKKL);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah(evt, COB,Catatan);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt,Catatan,LakaLantas);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt,AsalRujukan,btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kamar.setLocationRelativeTo(internalFrame1);
            kamar.setVisible(true);
        }else{
            poli.isCek();
            poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            poli.setLocationRelativeTo(internalFrame1);
            poli.setVisible(true);
        }
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        Valid.pindah(evt,btnDiagnosa,btnDPJP);
    }//GEN-LAST:event_btnPoliKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,TanggalRujuk,NoRujukan);
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
        Valid.pindah(evt,btnDPJP,Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,JenisPelayanan,Katarak);
    }//GEN-LAST:event_KelasKeyPressed

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt,NoRujukan,TanggalSEP);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            AsalRujukan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            NIP.requestFocus();
        }
    }//GEN-LAST:event_TNoRegKeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isNumber();
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,COB,BtnSimpan);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,btnKecamatan,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        Valid.pindah(evt,TNoReg,NoSKDP);
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void EksekutifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EksekutifKeyPressed
        Valid.pindah(evt,Katarak,COB);
    }//GEN-LAST:event_EksekutifKeyPressed

    private void COBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COBKeyPressed
        Valid.pindah(evt,Eksekutif,TanggalRujuk);
    }//GEN-LAST:event_COBKeyPressed

    private void ChkJasaRaharjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJasaRaharjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJasaRaharjaActionPerformed

    private void ChkBPJSTenagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkBPJSTenagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkBPJSTenagaActionPerformed

    private void ChkTaspenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkTaspenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTaspenActionPerformed

    private void ChkAsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAsaActionPerformed

    }//GEN-LAST:event_ChkAsaActionPerformed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        dpjp.setPoli(KdPoli.getText(),NmPoli.getText());
        dpjp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dpjp.setLocationRelativeTo(internalFrame1);
        dpjp.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed
        if(btnPoli.isVisible()==true){
            Valid.pindah(evt,btnPoli,JenisPelayanan);
        }else{
            Valid.pindah(evt,btnDiagnosa,JenisPelayanan);
        }
    }//GEN-LAST:event_btnDPJPKeyPressed

    private void KatarakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KatarakKeyPressed
        Valid.pindah(evt,Kelas,Eksekutif);
    }//GEN-LAST:event_KatarakKeyPressed

    private void TanggalKKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKKLKeyPressed
        Valid.pindah(evt,TanggalSEP,Keterangan);
    }//GEN-LAST:event_TanggalKKLKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,TanggalKKL,Suplesi);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void SuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuplesiKeyPressed
        Valid.pindah(evt,Keterangan,NoSEPSuplesi);
    }//GEN-LAST:event_SuplesiKeyPressed

    private void NoSEPSuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSEPSuplesiKeyPressed
        Valid.pindah(evt,Suplesi,btnPropinsi);
    }//GEN-LAST:event_NoSEPSuplesiKeyPressed

    private void btnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropinsiActionPerformed
        propinsikll.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propinsikll.setLocationRelativeTo(internalFrame1);
        propinsikll.setVisible(true);
    }//GEN-LAST:event_btnPropinsiActionPerformed

    private void btnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPropinsiKeyPressed
        Valid.pindah(evt,NoSEPSuplesi,btnKabupaten);
    }//GEN-LAST:event_btnPropinsiKeyPressed

    private void btnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabupatenActionPerformed
        if(KdPropinsi.getText().trim().equals("")||NmPropinsi.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih propinsi dulu..!!");
            btnPropinsi.requestFocus();
        }else{
            kabupatenkll.setPropinsi(KdPropinsi.getText(),NmPropinsi.getText());
            kabupatenkll.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kabupatenkll.setLocationRelativeTo(internalFrame1);
            kabupatenkll.setVisible(true);
        }
    }//GEN-LAST:event_btnKabupatenActionPerformed

    private void btnKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKabupatenKeyPressed
        Valid.pindah(evt,btnPropinsi,btnKecamatan);
    }//GEN-LAST:event_btnKabupatenKeyPressed

    private void btnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKecamatanActionPerformed
        if(KdKabupaten.getText().trim().equals("")||NmKabupaten.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih kabupaten dulu..!!");
            btnKabupaten.requestFocus();
        }else{
            kecamatankll.setPropinsi(KdKabupaten.getText(),NmKabupaten.getText());
            kecamatankll.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kecamatankll.setLocationRelativeTo(internalFrame1);
            kecamatankll.setVisible(true);
        }
    }//GEN-LAST:event_btnKecamatanActionPerformed

    private void btnKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKecamatanKeyPressed
        Valid.pindah(evt,btnKabupaten,BtnDokter);
    }//GEN-LAST:event_btnKecamatanKeyPressed

    private void NoSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSKDPKeyPressed
        Valid.pindah(evt, AsalRujukan,btnPPKRujukan);
    }//GEN-LAST:event_NoSKDPKeyPressed

    private void btnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSKDPActionPerformed
        skdp.setNoRm(TNo.getText(),TNm.getText());
        skdp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        skdp.setLocationRelativeTo(internalFrame1);
        skdp.setVisible(true);
    }//GEN-LAST:event_btnSKDPActionPerformed

    private void btnSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSKDPKeyPressed
        Valid.pindah(evt,AsalRujukan,btnPPKRujukan);
    }//GEN-LAST:event_btnSKDPKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(no_ktp.equals("Yes")&&(TKtp.getText().trim().length()<p_no_ktp)){
            TKtp.setText("-");
        }
        if(TNo.getText().trim().equals("")){
            Valid.textKosong(TNo,"No.Rekam Medis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama pasien");
        }else if(nmpnj.getText().trim().equals("")||Kdpnj.getText().trim().equals("")){
            Valid.textKosong(Kdpnj,"Asuransi/Askes/Png.Jawab");
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
        }else if(nmcacat.getText().trim().equals("")){
            Valid.textKosong(nmcacat,"Cacat Fisik");
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
        }else if((LakaLantas.getSelectedIndex()==1)&&Keterangan.getText().equals("")) {
            Valid.textKosong(Keterangan,"Keterangan");
        }else if((LakaLantas.getSelectedIndex()==1)&&NmPropinsi.getText().equals("")) {
            Valid.textKosong(btnPropinsi,"Propinsi");
        }else if((LakaLantas.getSelectedIndex()==1)&&NmKabupaten.getText().equals("")) {
            Valid.textKosong(btnKabupaten,"Kabupaten");
        }else if((LakaLantas.getSelectedIndex()==1)&&NmKecamatan.getText().equals("")) {
            Valid.textKosong(btnKecamatan,"Kecamatan");
        }else if (KdDPJP.getText().trim().equals("")||NmDPJP.getText().trim().equals("")) {
            Valid.textKosong(KdDPJP, "DPJP");
        }/*else if (NoSKDP.getText().trim().equals("")) {
            Valid.textKosong(NoSKDP, "No.SKDP");
        }*/else if(Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "+
            "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "+
            "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?",TNo.getText())>0){
            JOptionPane.showMessageDialog(null,"Pasien sedang dalam masa perawatan di kamar inap..!!");
            NoRujukan.requestFocus();
        }else if(TNik.getText().trim().equals("")){
            Valid.textKosong(TNik, "NIK");
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
                    TNik.requestFocus();
                }else{
                    insertPasien();
                }
            }else if((JenisPelayanan.getSelectedIndex()==1)&&(!NmPoli.getText().toLowerCase().contains("darurat"))){
                if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                    "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                    "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"%' and "+
                    "nmpolitujuan not like '%darurat%'")>=1){
                    JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan pembuatan SEP di jenis pelayanan rawat jalan..!!");
                    TNik.requestFocus();
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
            Valid.pindah(evt,BtnDokter,TNik);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatActionPerformed
        historiPelayanan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        historiPelayanan.setLocationRelativeTo(internalFrame1);
        historiPelayanan.setKartu(TNoPeserta.getText());
        historiPelayanan.setVisible(true);
    }//GEN-LAST:event_btnRiwayatActionPerformed

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
    private widget.ComboBox AsalRujukan;
    private widget.Button BtnBahasa;
    private widget.Button BtnCacat;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
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
    private widget.ComboBox COB;
    private widget.TextBox Catatan;
    private widget.CekBox ChkAsa;
    private widget.CekBox ChkBPJSTenaga;
    private widget.CekBox ChkCari;
    private widget.CekBox ChkJasaRaharja;
    private widget.CekBox ChkRM;
    private widget.CekBox ChkTaspen;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private widget.TextBox EMail;
    private widget.ComboBox Eksekutif;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormKelengkapanPasien;
    private widget.PanelBiasa FormKelengkapanSEP;
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox Kabupaten;
    private widget.TextBox KabupatenPj;
    private widget.ComboBox Katarak;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdKabupaten;
    private widget.TextBox KdKecamatan;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox KdPropinsi;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox KecamatanPj;
    private widget.ComboBox Kelas;
    private widget.TextBox Kelurahan;
    private widget.TextBox KelurahanPj;
    private widget.TextBox Keterangan;
    private widget.Label LabelGolonganPolri;
    private widget.Label LabelGolonganTNI;
    private widget.Label LabelJabatanPolri;
    private widget.Label LabelJabatanTNI;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelPangkatPolri;
    private widget.Label LabelPangkatTNI;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelSatuanPolri;
    private widget.Label LabelSatuanTNI;
    private widget.ComboBox LakaLantas;
    private javax.swing.JMenuItem MnDocument;
    private widget.TextBox NIP;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmIbu;
    private widget.TextBox NmKabupaten;
    private widget.TextBox NmKecamatan;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NmPropinsi;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoRm;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoSEPSuplesi;
    private widget.TextBox NoSKDP;
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
    private widget.ComboBox Suplesi;
    private widget.TextBox TBiaya;
    private widget.TextBox TDokter;
    private widget.TextBox TKtp;
    private widget.TextBox TNik;
    private widget.TextBox TNm;
    private widget.TextBox TNo;
    private widget.TextBox TNoPeserta;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPoli;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.TextBox TUmur;
    private widget.Tanggal TanggalKKL;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalSEP;
    private widget.Button btnDPJP;
    private widget.Button btnDiagnosa;
    private widget.Button btnKabupaten;
    private widget.Button btnKecamatan;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPasien;
    private widget.Button btnPoli;
    private widget.Button btnPropinsi;
    private widget.Button btnPropinsiPj;
    private widget.Button btnRiwayat;
    private widget.Button btnSKDP;
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
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil(String nomorpeserta) {
        try {
            nosep="";
            statuspasien="";      
            peserta="";      
            cekViaBPJS.tampil(nomorpeserta);
            if(cekViaBPJS.informasi.equals("OK")){
                Valid.tabelKosong(tabMode);
                Valid.tabelKosong(tabMode);             
                tabMode.addRow(new Object[]{
                    "Nama",": "+cekViaBPJS.nama
                });
                TNm.setText(cekViaBPJS.nama);
                tabMode.addRow(new Object[]{
                    "NIK",": "+TNik.getText()
                });
                TKtp.setText(cekViaBPJS.nik);
                tabMode.addRow(new Object[]{
                    "Nomor Kartu",": "+cekViaBPJS.noKartu
                });
                TNoPeserta.setText(cekViaBPJS.noKartu);
                tabMode.addRow(new Object[]{
                    "Nomor MR",": "+cekViaBPJS.mrnoMR
                });
                tabMode.addRow(new Object[]{
                    "Nomor Telp",": "+cekViaBPJS.mrnoTelepon
                });
                TTlp.setText(cekViaBPJS.mrnoTelepon);
                tabMode.addRow(new Object[]{
                    "Pisa",": "+cekViaBPJS.pisa
                });
                tabMode.addRow(new Object[]{
                    "Jenis Kelamin",": "+cekViaBPJS.sex.replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")
                });
                switch (cekViaBPJS.sex) {
                    case "L":
                        CmbJk.setSelectedItem("L");
                        break;
                    case "P":
                        CmbJk.setSelectedItem("P");
                        break;
                }
                tabMode.addRow(new Object[]{
                    "Status Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Keterangan",": "+cekViaBPJS.statusPesertaketerangan
                });
                tabMode.addRow(new Object[]{
                    "       Kode",": "+cekViaBPJS.statusPesertakode
                });
                tabMode.addRow(new Object[]{
                    "Jenis Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Jenis Peserta",": "+cekViaBPJS.jenisPesertakode
                });
                tabMode.addRow(new Object[]{
                    "       Nama Jenis Peserta",": "+cekViaBPJS.jenisPesertaketerangan
                });            
                Kdpnj.setText("BPJ");
                nmpnj.setText("BPJS");
                Pekerjaan.setText(cekViaBPJS.jenisPesertaketerangan);
                peserta=cekViaBPJS.jenisPesertaketerangan;
                tabMode.addRow(new Object[]{
                    "Kelas Tanggungan",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Kelas",": "+cekViaBPJS.hakKelaskode
                });
                if(cekViaBPJS.hakKelaskode.equals("1")){
                    Kelas.setSelectedIndex(0);
                }else if(cekViaBPJS.hakKelaskode.equals("2")){
                    Kelas.setSelectedIndex(1);
                }else if(cekViaBPJS.hakKelaskode.equals("3")){
                    Kelas.setSelectedIndex(2);
                }
                tabMode.addRow(new Object[]{
                    "       Nama Kelas",": "+cekViaBPJS.hakKelasketerangan
                });
                tabMode.addRow(new Object[]{
                    "Provider Umum",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Provider",": "+cekViaBPJS.provUmumkdProvider
                });                
                KdPpkRujukan.setText(cekViaBPJS.provUmumkdProvider);
                tabMode.addRow(new Object[]{
                    "       Nama Provider",": "+cekViaBPJS.provUmumnmProvider
                });
                NmPpkRujukan.setText(cekViaBPJS.provUmumnmProvider);                
                tabMode.addRow(new Object[]{
                    "Tanggal Cetak Kartu",": "+cekViaBPJS.tglCetakKartu
                });
                tabMode.addRow(new Object[]{
                    "Tanggal Lahir",": "+cekViaBPJS.tglLahir
                });
                Valid.SetTgl(DTPLahir,cekViaBPJS.tglLahir);
                tabMode.addRow(new Object[]{
                    "Tanggal TAT",": "+cekViaBPJS.tglTAT
                });
                tabMode.addRow(new Object[]{
                    "Tanggal TMT",": "+cekViaBPJS.tglTMT
                });
                tabMode.addRow(new Object[]{
                    "Umur",":"
                });
                tabMode.addRow(new Object[]{
                    "       Umur Saat Pelayanan",": "+cekViaBPJS.umurumurSaatPelayanan
                });
                tabMode.addRow(new Object[]{
                    "       Umur Sekarang",": "+cekViaBPJS.umurumurSekarang.replaceAll("tahun ,","Th ").replaceAll("bulan ,","Bl ").replaceAll("hari","Hr")
                });
                tabMode.addRow(new Object[]{
                    "Informasi",":"
                });
                tabMode.addRow(new Object[]{
                    "       Dinsos",": "+cekViaBPJS.informasidinsos
                });
                tabMode.addRow(new Object[]{
                    "       No.SKTM",": "+cekViaBPJS.informasinoSKTM
                });
                tabMode.addRow(new Object[]{
                    "       Prolanis PRB",": "+cekViaBPJS.informasiprolanisPRB
                });
                tabMode.addRow(new Object[]{
                    "COB",":"
                });
                tabMode.addRow(new Object[]{
                    "       Nama Asuransi",": "+cekViaBPJS.cobnmAsuransi
                });
                tabMode.addRow(new Object[]{
                    "       No Asuransi",": "+cekViaBPJS.cobnoAsuransi
                });
                tabMode.addRow(new Object[]{
                    "       Tanggal TAT",": "+cekViaBPJS.cobtglTAT
                });
                tabMode.addRow(new Object[]{
                    "       Tanggal TMT",": "+cekViaBPJS.cobtglTMT
                });
                prb=cekViaBPJS.informasiprolanisPRB.replaceAll("null","");
                TUmur.setText(cekViaBPJS.umurumurSekarang.replaceAll("tahun ,","Th ").replaceAll("bulan ,","Bl ").replaceAll("hari","Hr"));
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
                    ps.setString(1,cekViaBPJS.noKartu);
                    rs=ps.executeQuery();
                    if(rs.next()){
                        statuspasien="Lama";                        
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
                        statuspasien="Baru";
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
                ChkCari.setSelected(false);
                isForm();
                JOptionPane.showMessageDialog(null,cekViaBPJS.informasi);                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);            
        }
    }  
    
   private void isForm(){
        if(ChkCari.isSelected()==true){
            panelCari.setVisible(true);
                
            if(tampilkantni.equals("Yes")){
                FormInput.setPreferredSize(new Dimension(955,820));
                if(internalFrame1.getHeight()>530){
                    PanelInput.setPreferredSize(new Dimension(WIDTH,570));
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,485));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,334));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,485));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,334));
                    }
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-20));
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,485));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,334));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,485));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,334));
                    }
                }
            }else{
                FormInput.setPreferredSize(new Dimension(955,670));
                if(internalFrame1.getHeight()>530){
                    PanelInput.setPreferredSize(new Dimension(WIDTH,570));
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,334));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,334));
                    }
                }else{
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-20));
                    if(internalFrame1.getWidth()<960){
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952,335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952,34));
                    }else{
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(internalFrame1.getWidth()-32,334));
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
        Propinsi.setText("PROPINSI");
        KelurahanPj.setText("KELURAHAN");      
        KecamatanPj.setText("KECAMATAN");      
        KabupatenPj.setText("KABUPATEN"); 
        PropinsiPj.setText("PROPINSI");
        kdcacat.setText("");
        nmcacat.setText("");
        NIP.setText("");
        EMail.setText("");
        chkPolri.setSelected(false);
        chkTNI.setSelected(false);
        kdsuku.setText("");
        nmsukubangsa.setText("");
        kdbahasa.setText("");
        nmbahasa.setText("");
        kdperusahaan.setText("");
        nmperusahaan.setText("");
        kdgolongantni.setText("");
        nmgolongantni.setText("");
        kdsatuantni.setText("");
        nmsatuantni.setText("");
        kdpangkattni.setText("");
        nmpangkattni.setText("");
        kdjabatantni.setText("");
        nmjabatantni.setText("");
        kdgolonganpolri.setText("");
        nmgolonganpolri.setText("");
        kdsatuanpolri.setText("");
        nmsatuanpolri.setText("");
        kdpangkatpolri.setText("");
        nmpangkatpolri.setText("");
        kdjabatanpolri.setText("");
        nmjabatanpolri.setText("");
        chkTNI.setSelected(false);
        chkPolri.setSelected(false);     
        BtnGolonganPolri.setEnabled(false);
        BtnSatuanPolri.setEnabled(false);
        BtnJabatanPolri.setEnabled(false);
        BtnPangkatPolri.setEnabled(false);
        BtnGolonganTNI.setEnabled(false);
        BtnSatuanTNI.setEnabled(false);
        BtnJabatanTNI.setEnabled(false);
        BtnPangkatTNI.setEnabled(false);
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
        Kelas.setSelectedIndex(2);
        LakaLantas.setSelectedIndex(0);
        Katarak.setSelectedIndex(0);        
        Suplesi.setSelectedIndex(0);
        NoSKDP.setText("");
        NoSEPSuplesi.setText("");
        Keterangan.setText("");
        KdPropinsi.setText("");
        NmPropinsi.setText("");
        KdKabupaten.setText("");
        NmKabupaten.setText("");
        KdKecamatan.setText("");
        NmKecamatan.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        TanggalKKL.setDate(new Date());
        JenisPelayanan.setSelectedIndex(1);
        JenisPelayananItemStateChanged(null);
        statuspasien="";
        TBiaya.setText("0");
           
        TNo.requestFocus();
    }
    
    private void isNumber(){    
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"' ","BR/"+dateformat.format(TanggalSEP.getDate())+"/",4,NoBalasan); 
        if(BASENOREG.equals("booking")){
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+kdpoli.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+kddokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter + poli":             
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+kddokter.getText()+"' and kd_poli='"+kdpoli.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+kddokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
            }
        }else{
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+kdpoli.getText()+"' and tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter + poli":             
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and kd_poli='"+kdpoli.getText()+"' and tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
            }
        } 
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"' ",dateformat.format(TanggalSEP.getDate())+"/",6,TNoRw);                              
    }
    
    private void isPoli(){
        try {
            ps=koneksi.prepareStatement("select registrasi, registrasilama "+
                " from poliklinik where kd_poli=? order by nm_poli");
            try{            
                ps.setString(1,kdpoli.getText().trim());
                rs=ps.executeQuery();
                if(rs.next()){
                    if(statuspasien.equals("Lama")){
                        TBiaya.setText(rs.getString("registrasilama"));
                    }else if(statuspasien.equals("Baru")){
                        TBiaya.setText(rs.getString("registrasi"));
                    }else{
                        TBiaya.setText(rs.getString("registrasi"));
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
            jasaraharja="";BPJS="";Taspen="";Asabri="";
            penjamin="";
            if(ChkJasaRaharja.isSelected()==true){
                jasaraharja="1,";
            }
            if(ChkBPJSTenaga.isSelected()==true){
                BPJS="2,";
            }
            if(ChkTaspen.isSelected()==true){
                Taspen="3,";
            }
            if(ChkAsa.isSelected()==true){
                Asabri="4,";
            }
            
            if((ChkJasaRaharja.isSelected()==true)||(ChkBPJSTenaga.isSelected()==true)||(ChkTaspen.isSelected()==true)||(ChkAsa.isSelected()==true)){
                penjamin=jasaraharja+BPJS+Taspen+Asabri+penjamin;
            }else{
                penjamin="";
            }
            
            if(penjamin.endsWith(",")){
                penjamin = penjamin.substring(0,penjamin.length() - 1);
            }	
            
            tglkkl="0000-00-00";
            if(LakaLantas.getSelectedIndex()==1){
                tglkkl=Valid.SetTgl(TanggalKKL.getSelectedItem()+"");
            }
            
            headers= new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            URL = link+"/SEP/1.1/insert";
            requestJson ="{" +
                          "\"request\":" +
                             "{" +
                                "\"t_sep\":" +
                                   "{" +
                                    "\"noKartu\":\""+TNoPeserta.getText()+"\"," +
                                    "\"tglSep\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                    "\"ppkPelayanan\":\""+KdPPK.getText()+"\"," +
                                    "\"jnsPelayanan\":\""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"klsRawat\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"noMR\":\""+TNo.getText()+"\"," +
                                    "\"rujukan\": {"+
                                        "\"asalRujukan\":\""+AsalRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"tglRujukan\":\""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+"\"," +
                                        "\"noRujukan\":\""+NoRujukan.getText()+"\"," +
                                        "\"ppkRujukan\":\""+KdPpkRujukan.getText()+"\"" +
                                    "},"+
                                    "\"catatan\":\""+Catatan.getText()+"\"," +
                                    "\"diagAwal\":\""+KdPenyakit.getText()+"\"," +
                                    "\"poli\": {" +
                                        "\"tujuan\": \""+KdPoli.getText()+"\"," +
                                        "\"eksekutif\": \""+Eksekutif.getSelectedItem().toString().substring(0,1)+"\"" +
                                    "},"+
                                    "\"cob\": {" +
                                        "\"cob\": \""+COB.getSelectedItem().toString().substring(0,1)+"\"" +
                                    "},"+
                                    "\"katarak\": {" +
                                        "\"katarak\": \""+Katarak.getSelectedItem().toString().substring(0,1)+"\"" +
                                    "},"+
                                    "\"jaminan\": {"+
                                        "\"lakaLantas\":\""+LakaLantas.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"penjamin\": {" +
                                            "\"penjamin\": \""+penjamin+"\"," +
                                            "\"tglKejadian\": \""+tglkkl+"\"," +
                                            "\"keterangan\": \""+Keterangan.getText()+"\"," +
                                            "\"suplesi\": {" +
                                                "\"suplesi\": \""+Suplesi.getSelectedItem().toString().substring(0,1)+"\"," +
                                                "\"noSepSuplesi\": \""+NoSEPSuplesi.getText()+"\"," +
                                                "\"lokasiLaka\": {" +
                                                    "\"kdPropinsi\": \""+KdPropinsi.getText()+"\"," +
                                                    "\"kdKabupaten\": \""+KdKabupaten.getText()+"\"," +
                                                    "\"kdKecamatan\": \""+KdKecamatan.getText()+"\"" +
                                                "}" +
                                            "}" +
                                        "}"+
                                    "}," +
                                    "\"skdp\": {" +
                                        "\"noSurat\": \""+NoSKDP.getText()+"\"," +
                                        "\"kodeDPJP\": \""+KdDPJP.getText()+"\"" +
                                    "},"+
                                    "\"noTelp\": \""+TTlp.getText()+"\","+
                                    "\"user\":\""+user+"\"" +
                                   "}" +
                             "}" +
                         "}";
            System.out.println("JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            requestJson=api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("JSON : "+requestJson);
            root = mapper.readTree(requestJson);
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            response = root.path("response").path("sep").path("noSep");
            if(nameNode.path("code").asText().equals("200")){
                nosep=response.asText();
                System.out.println("No.SEP : "+nosep);
                if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",44,new String[]{
                     response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""), 
                     NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                     JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                     NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                     LakaLantas.getSelectedItem().toString().substring(0,1),user,TNo.getText(),TNm.getText(),
                     Valid.SetTgl(DTPLahir.getSelectedItem()+""),peserta,CmbJk.getSelectedItem().toString(),TNoPeserta.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                     COB.getSelectedItem().toString(),penjamin,TTlp.getText(),Katarak.getSelectedItem().toString(),
                     tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),
                     NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                     KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText()
                 })==true){
                    if(JenisPelayanan.getSelectedIndex()==1){
                        Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                             Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                             response.asText()
                        }); 
                    }    
                    JOptionPane.showMessageDialog(null,"Proses Selesai...!");
                    if(!nosep.equals("")){
                        pilihan.tampil();
                        if(JenisPelayanan.getSelectedIndex()==0){
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
                    Sequel.mengedit3("skdp_bpjs","no_rkm_medis=? and tanggal_datang=?","status='Sudah Periksa'",2,new String[]{
                        TNo.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")
                    });
                    Sequel.queryu2("update booking_registrasi set status='Terdaftar' where no_rkm_medis=? and tanggal_periksa=?",2,new String[]{
                        TNo.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")
                    });
                    if(!prb.equals("")){
                        if(Sequel.menyimpantf("bpjs_prb","?,?","PRB",2,new String[]{response.asText(),prb})==true){
                            prb="";
                        } 
                    }
                    nosisrute="";
                }                     
            }else{                
                Sequel.meghapus3("kamar_inap","no_rawat",TNoRw.getText());
                Sequel.meghapus3("diagnosa_pasien","no_rawat",TNoRw.getText());
                Sequel.meghapus3("rujuk_masuk","no_rawat",TNoRw.getText());
                Sequel.meghapus3("reg_periksa","no_rawat",TNoRw.getText());
                if(statuspasien.equals("Baru")){
                    Sequel.meghapus3("pasien","no_rkm_medis",TNo.getText());
                }
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        }catch (Exception ex) {            
            Sequel.meghapus3("kamar_inap","no_rawat",TNoRw.getText());
            Sequel.meghapus3("diagnosa_pasien","no_rawat",TNoRw.getText());
            Sequel.meghapus3("rujuk_masuk","no_rawat",TNoRw.getText());
            Sequel.meghapus3("reg_periksa","no_rawat",TNoRw.getText());
            if(statuspasien.equals("Baru")){
                Sequel.meghapus3("pasien","no_rkm_medis",TNo.getText());
            }
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
        
        status="Baru";
        if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis='"+TNo.getText()+"' and kd_poli='"+kdpoli.getText()+"'")>0){
            status="Lama";
        }
        if(JenisPelayanan.getSelectedIndex()==1){
            isNumber();
            isPoli();
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),kdpoli.getText(),Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,TBiaya.getText(),"Belum",statuspasien,"Ralan",Kdpnj.getText(),umur,sttsumur,"Belum Bayar",status
                })==true){
                    UpdateUmur();
                    if(nosisrute.equals("")){
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");             
                    }else{
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+nosisrute+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");   
                    }
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
                    insertSEP();
            }
        }else if(JenisPelayanan.getSelectedIndex()==0){
            isNumber();
            Sequel.menyimpan("poliklinik","?,?,?,?",4,new String[]{"IGDK","Unit IGD","0","0"});
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),TanggalSEP.getSelectedItem().toString().substring(11,19),
                    kddokter.getText(),TNo.getText(),"IGDK",Saudara.getText(),AlamatPj.getText()+", "+KelurahanPj.getText()+", "+KecamatanPj.getText()+", "+KabupatenPj.getText(),
                    klg,Sequel.cariIsi("select registrasilama from poliklinik where kd_poli='IGDK'"),"Belum",statuspasien,"Ralan",Kdpnj.getText(),umur,sttsumur,"Belum Bayar",status
                })==true){
                    UpdateUmur();
                    if(nosisrute.equals("")){
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+NoRujukan.getText()+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");             
                    }else{
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+NmPpkRujukan.getText()+"','"+Kabupaten.getText()+"','"+nosisrute+"','0','"+NmPPK.getText()+"','"+KdPenyakit.getText()+"','-','-','"+NoBalasan.getText()+"'","No.Rujuk");   
                    }
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
                    
                    jmlhari=0;
                    biaya=0;
                    if(hariawal.equals("Yes")){
                        jmlhari=1;
                        biaya=Double.parseDouble(TBiaya.getText());
                    }
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

        if(Kelurahan.isEditable()==true){
            Sequel.queryu4("insert into kelurahan values(?,?)",2,new String[]{"0",Kelurahan.getText().replaceAll("KELURAHAN","-")});
            kdkel=Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-"));
        }else if(Kelurahan.isEditable()==false){
            if(kdkel.equals("")){
                Sequel.queryu4("insert into kelurahan values(?,?)",2,new String[]{"0",Kelurahan.getText().replaceAll("KELURAHAN","-")});
                kdkel=Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-"));
            }
        }

        if(Kecamatan.isEditable()==true){
            Sequel.queryu4("insert into kecamatan values(?,?)",2,new String[]{"0",Kecamatan.getText().replaceAll("KECAMATAN","-")});
            kdkec=Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-"));
        }else if(Kecamatan.isEditable()==false){
            if(kdkec.equals("")){
                Sequel.queryu4("insert into kecamatan values(?,?)",2,new String[]{"0",Kecamatan.getText().replaceAll("KECAMATAN","-")});
                kdkec=Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-"));
            }
        }

        if(Kabupaten.isEditable()==true){
            Sequel.queryu4("insert into kabupaten values(?,?)",2,new String[]{"0",Kabupaten.getText().replaceAll("KABUPATEN","-")});
            kdkab=Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-"));
        }else if(Kabupaten.isEditable()==false){
            if(kdkab.equals("")){
                Sequel.queryu4("insert into kabupaten values(?,?)",2,new String[]{"0",Kabupaten.getText().replaceAll("KABUPATEN","-")});
                kdkab=Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-"));
            }
        }

        if(Propinsi.isEditable()==true){
           Sequel.queryu4("insert into propinsi values(?,?)",2,new String[]{"0",Propinsi.getText().replaceAll("PROPINSI","-")}); 
           kdprop=Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",Propinsi.getText().replaceAll("PROPINSI","-"));
        }else if(Propinsi.isEditable()==false){
            if(kdprop.equals("")){
                Sequel.queryu4("insert into propinsi values(?,?)",2,new String[]{"0",Propinsi.getText().replaceAll("PROPINSI","-")}); 
                kdprop=Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",Propinsi.getText().replaceAll("PROPINSI","-"));
            }
        }
        
        if(statuspasien.equals("Baru")){
            autoNomor();
            if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                    TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                    Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                    TTlp.getText(),TUmur.getText(),CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                    kdkel,kdkec,kdkab,PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText(),kdperusahaan.getText(),
                    kdsuku.getText(),kdbahasa.getText(),kdcacat.getText(),EMail.getText(),NIP.getText(),
                    kdprop,PropinsiPj.getText()
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
                if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                        TNo.getText(),TNm.getText(),TKtp.getText(),CmbJk.getSelectedItem().toString().substring(0,1),TTmp.getText(),
                        Valid.SetTgl(DTPLahir.getSelectedItem()+""),NmIbu.getText(),
                        Alamat.getText().replaceAll("ALAMAT",""),CMbGd.getSelectedItem().toString(),Pekerjaan.getText(),CmbStts.getSelectedItem().toString(),cmbAgama.getSelectedItem().toString(),
                        DTPDaftar.getSelectedItem().toString().substring(6,10)+"-"+DTPDaftar.getSelectedItem().toString().substring(3,5)+"-"+DTPDaftar.getSelectedItem().toString().substring(0,2),
                        TTlp.getText(),TUmur.getText(),CMbPnd.getSelectedItem().toString(),klg,Saudara.getText(),Kdpnj.getText(),TNoPeserta.getText(),
                        kdkel,kdkec,kdkab,PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),KabupatenPj.getText(),kdperusahaan.getText(),
                        kdsuku.getText(),kdbahasa.getText(),kdcacat.getText(),EMail.getText(),NIP.getText(),
                        kdprop,PropinsiPj.getText()
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
            }
        }else if(statuspasien.equals("Lama")){
            if(kdkel.equals("")){
                kdkel=Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",Kelurahan.getText().replaceAll("KELURAHAN","-"));
            }
            if(kdkec.equals("")){
                kdkec=Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",Kecamatan.getText().replaceAll("KECAMATAN","-"));
            }            
            if(kdkab.equals("")){
                kdkab=Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",Kabupaten.getText().replaceAll("KABUPATEN","-"));
            }            
            if(kdprop.equals("")){
                kdprop=Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",Propinsi.getText().replaceAll("PROPINSI","-"));
            }
            
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
                    kdkel,kdkec,kdkab,NmIbu.getText(),PekerjaanPj.getText(),AlamatPj.getText(),KelurahanPj.getText(),KecamatanPj.getText(),
                    KabupatenPj.getText(),kdperusahaan.getText(),kdsuku.getText(),kdbahasa.getText(),kdcacat.getText(),EMail.getText(),NIP.getText(),
                    kdprop,PropinsiPj.getText(),TNo.getText()
            });
            if(chkTNI.isSelected()==true){
                Sequel.meghapus("pasien_tni","no_rkm_medis",TNo.getText());
                Sequel.menyimpan2("pasien_tni","?,?,?,?,?","Data",5,new String[]{
                    TNo.getText(),kdgolongantni.getText(),kdpangkattni.getText(),
                    kdsatuantni.getText(),kdjabatantni.getText()
                });
            }else if(chkTNI.isSelected()==false){
                Sequel.meghapus("pasien_tni","no_rkm_medis",TNo.getText());
            }  
            if(chkPolri.isSelected()==true){
                Sequel.meghapus("pasien_polri","no_rkm_medis",TNo.getText());
                Sequel.menyimpan2("pasien_polri","?,?,?,?,?","Data",5,new String[]{
                    TNo.getText(),kdgolonganpolri.getText(),kdpangkatpolri.getText(),
                    kdsatuanpolri.getText(),kdjabatanpolri.getText()
                });
            }else if(chkPolri.isSelected()==false){
                Sequel.meghapus("pasien_polri","no_rkm_medis",TNo.getText());
            }    
            inputRegistrasi();                                
        }                
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{TNo.getText()});
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbpjs_sep());
        ppPengajuan.setEnabled(akses.getbpjs_sep());
        ppPengajuan1.setEnabled(akses.getbpjs_sep());
    }
    
    public void SetNoKTP(String NoKTP){
        emptTeks();
        TNik.setText(NoKTP);
        tampil(NoKTP);
        empt=true;
    }
    
    public void SetNoRujuk(String norujuk){
        this.nosisrute=norujuk;
    }
}

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

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import simrskhanza.DlgKamarInap;
import simrskhanza.DlgResumePerawatan;


/**
 *
 * @author perpustakaan
 */
public final class BPJSDataSEP extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private BPJSApi api=new BPJSApi();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private BPJSCekNoKartu cekViaBPJSKartu=new BPJSCekNoKartu();
    private BPJSCekReferensiDokterDPJP dokter=new BPJSCekReferensiDokterDPJP(null,false);
    private DlgSKDPBPJS skdp=new DlgSKDPBPJS(null,false);
    private BPJSCekReferensiPropinsi propinsi=new BPJSCekReferensiPropinsi(null,false);
    private BPJSCekReferensiKabupaten kabupaten=new BPJSCekReferensiKabupaten(null,false);
    private BPJSCekReferensiKecamatan kecamatan=new BPJSCekReferensiKecamatan(null,false);
    private String prb="",no_peserta="",link="", requestJson,URL="",jkel="",duplikat="",user="",penjamin="",jasaraharja="",BPJS="",Taspen="",Asabri="",kddokter="",tglkkl="0000-00-00";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private BPJSCekHistoriPelayanan historiPelayanan=new BPJSCekHistoriPelayanan(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public BPJSDataSEP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        WindowUpdatePulang.setSize(630,80);
        WindowRujukan.setSize(810,172);
        WindowCariSEP.setSize(410,115);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan", 
                "No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis Layanan","Catatan", "Kode Diagnosa", 
                "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas", 
                "User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang",
                "Asal Rujukan","Eksekutif","COB","Penjamin","No.Telp","Katarak",
                "Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop",
                "Propinsi","Kd Kab","Kabupaten","Kd Kec","Kecamatan","No.SKDP",
                "Kd DPJP","DPJP"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 44; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(125);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(25);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(120);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(60);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(130);
            }else if(i==29){
                column.setPreferredWidth(85);
            }else if(i==30){
                column.setPreferredWidth(55);
            }else if(i==31){
                column.setPreferredWidth(70);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(55);
            }else if(i==34){
                column.setPreferredWidth(120);
            }else if(i==35){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==36){
                column.setPreferredWidth(135);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(135);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(135);
            }else if(i==41){
                column.setPreferredWidth(60);
            }else if(i==42){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==43){
                column.setPreferredWidth(135);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NoRujukan.setDocument(new batasInput((byte)40).getKata(NoRujukan));
        NoSKDP.setDocument(new batasInput((byte)6).getKata(NoSKDP));
        NoSEPSuplesi.setDocument(new batasInput((byte)40).getKata(NoSEPSuplesi));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        Catatan1.setDocument(new batasInput((byte)50).getKata(Catatan1));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(faskes.getTable().getSelectedRow()!= -1){  
                    if(pilihan==1){
                        KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),1).toString());
                        NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),2).toString());
                        KdPpkRujukan.requestFocus();
                    }else if(pilihan==2){
                        KdPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),1).toString());
                        NmPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),2).toString());
                        KdPpkRujukan1.requestFocus();
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
                    if(pilihan==1){
                        KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                        KdPenyakit.requestFocus();
                    }else if(pilihan==2){
                        KdPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        NmPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                        KdPenyakit1.requestFocus();
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
                    if(pilihan==1){
                        KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KdPoli.requestFocus();
                    }else if(pilihan==2){
                        KdPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KdPoli1.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){  
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
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
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        propinsi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(propinsi.getTable().getSelectedRow()!= -1){                   
                    KdPropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),1).toString());
                    NmPropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),2).toString());
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
        
        propinsi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    propinsi.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupaten.getTable().getSelectedRow()!= -1){                   
                    KdKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),1).toString());
                    NmKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),2).toString());
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
        
        kabupaten.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kabupaten.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        kecamatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kecamatan.getTable().getSelectedRow()!= -1){                   
                    KdKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),1).toString());
                    NmKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),2).toString());
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
        
        kecamatan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kecamatan.dispose();
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
        
        try{
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting"));            
        }catch(Exception e){
            System.out.println(e);
        }

        try {
            user=akses.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=akses.getkode();
        }
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml")); 
            link=prop.getProperty("URLAPIBPJS");
        } catch (Exception e) {
            System.out.println("E : "+e);
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

        Popup = new javax.swing.JPopupMenu();
        ppSEP = new javax.swing.JMenuItem();
        ppSEP1 = new javax.swing.JMenuItem();
        ppSEP2 = new javax.swing.JMenuItem();
        ppSEP3 = new javax.swing.JMenuItem();
        ppSEP4 = new javax.swing.JMenuItem();
        ppSEP5 = new javax.swing.JMenuItem();
        ppSEP6 = new javax.swing.JMenuItem();
        ppSEP7 = new javax.swing.JMenuItem();
        ppPulang = new javax.swing.JMenuItem();
        ppDetailSEPPeserta = new javax.swing.JMenuItem();
        ppRujukan = new javax.swing.JMenuItem();
        ppRiwayatPerawatan = new javax.swing.JMenuItem();
        WindowUpdatePulang = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        NoBalasan = new widget.TextBox();
        WindowRujukan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel30 = new widget.Label();
        TanggalRujukKeluar = new widget.Tanggal();
        jLabel12 = new widget.Label();
        KdPpkRujukan1 = new widget.TextBox();
        NmPpkRujukan1 = new widget.TextBox();
        btnPPKRujukan1 = new widget.Button();
        jLabel31 = new widget.Label();
        JenisPelayanan1 = new widget.ComboBox();
        jLabel32 = new widget.Label();
        KdPenyakit1 = new widget.TextBox();
        NmPenyakit1 = new widget.TextBox();
        btnDiagnosa1 = new widget.Button();
        LabelPoli1 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        btnPoli1 = new widget.Button();
        jLabel33 = new widget.Label();
        TipeRujukan = new widget.ComboBox();
        jLabel34 = new widget.Label();
        Catatan1 = new widget.TextBox();
        Popup1 = new javax.swing.JPopupMenu();
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
        ppAmbilSep = new javax.swing.JMenuItem();
        WindowCariSEP = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        jLabel35 = new widget.Label();
        NoSEP = new widget.TextBox();
        BtnCari1 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel20 = new widget.Label();
        TanggalSEP = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        jLabel23 = new widget.Label();
        NoRujukan = new widget.TextBox();
        jLabel9 = new widget.Label();
        KdPPK = new widget.TextBox();
        NmPPK = new widget.TextBox();
        btnPPKRujukan = new widget.Button();
        jLabel10 = new widget.Label();
        KdPpkRujukan = new widget.TextBox();
        NmPpkRujukan = new widget.TextBox();
        jLabel11 = new widget.Label();
        KdPenyakit = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        btnDiagnosa = new widget.Button();
        btnPoli = new widget.Button();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        LabelPoli = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Catatan = new widget.TextBox();
        JenisPelayanan = new widget.ComboBox();
        LabelKelas = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel16 = new widget.Label();
        LakaLantas = new widget.ComboBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        JK = new widget.TextBox();
        jLabel24 = new widget.Label();
        JenisPeserta = new widget.TextBox();
        jLabel25 = new widget.Label();
        Status = new widget.TextBox();
        jLabel27 = new widget.Label();
        AsalRujukan = new widget.ComboBox();
        jLabel15 = new widget.Label();
        Eksekutif = new widget.ComboBox();
        LabelKelas1 = new widget.Label();
        COB = new widget.ComboBox();
        jLabel28 = new widget.Label();
        ChkAsa = new widget.CekBox();
        ChkJasaRaharja = new widget.CekBox();
        ChkBPJSTenaga = new widget.CekBox();
        ChkTaspen = new widget.CekBox();
        jLabel29 = new widget.Label();
        NoTelp = new widget.TextBox();
        Katarak = new widget.ComboBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        TanggalKKL = new widget.Tanggal();
        jLabel39 = new widget.Label();
        NoSKDP = new widget.TextBox();
        btnSKDP = new widget.Button();
        LabelPoli2 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        btnDPJP = new widget.Button();
        jLabel36 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel40 = new widget.Label();
        Suplesi = new widget.ComboBox();
        NoSEPSuplesi = new widget.TextBox();
        jLabel41 = new widget.Label();
        LabelPoli3 = new widget.Label();
        KdPropinsi = new widget.TextBox();
        NmPropinsi = new widget.TextBox();
        btnPropinsi = new widget.Button();
        LabelPoli4 = new widget.Label();
        KdKabupaten = new widget.TextBox();
        NmKabupaten = new widget.TextBox();
        btnKabupaten = new widget.Button();
        LabelPoli5 = new widget.Label();
        KdKecamatan = new widget.TextBox();
        NmKecamatan = new widget.TextBox();
        btnKecamatan = new widget.Button();
        btnRiwayat = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppSEP.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP.setText("Print SEP Model 1");
        ppSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP.setName("ppSEP"); // NOI18N
        ppSEP.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEPBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP);

        ppSEP1.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP1.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP1.setText("Print SEP Model 2");
        ppSEP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP1.setName("ppSEP1"); // NOI18N
        ppSEP1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP1BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP1);

        ppSEP2.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP2.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP2.setText("Print SEP Model 3");
        ppSEP2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP2.setName("ppSEP2"); // NOI18N
        ppSEP2.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP2BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP2);

        ppSEP3.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP3.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP3.setText("Print SEP Model 4");
        ppSEP3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP3.setName("ppSEP3"); // NOI18N
        ppSEP3.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP3BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP3);

        ppSEP4.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP4.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP4.setText("Print SEP Model 1 PDF");
        ppSEP4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP4.setName("ppSEP4"); // NOI18N
        ppSEP4.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP4BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP4);

        ppSEP5.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP5.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP5.setText("Print SEP Model 2 PDF");
        ppSEP5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP5.setName("ppSEP5"); // NOI18N
        ppSEP5.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP5BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP5);

        ppSEP6.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP6.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP6.setText("Print SEP Model 3 PDF");
        ppSEP6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP6.setName("ppSEP6"); // NOI18N
        ppSEP6.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP6BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP6);

        ppSEP7.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP7.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP7.setText("Print SEP Model 4 PDF");
        ppSEP7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP7.setName("ppSEP7"); // NOI18N
        ppSEP7.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP7BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP7);

        ppPulang.setBackground(new java.awt.Color(255, 255, 254));
        ppPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPulang.setForeground(new java.awt.Color(70, 70, 70));
        ppPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPulang.setText("Update Tanggal Pulang");
        ppPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPulang.setName("ppPulang"); // NOI18N
        ppPulang.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPulangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPulang);

        ppDetailSEPPeserta.setBackground(new java.awt.Color(255, 255, 254));
        ppDetailSEPPeserta.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDetailSEPPeserta.setForeground(new java.awt.Color(70, 70, 70));
        ppDetailSEPPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDetailSEPPeserta.setText("Detail SEP Peserta");
        ppDetailSEPPeserta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDetailSEPPeserta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDetailSEPPeserta.setName("ppDetailSEPPeserta"); // NOI18N
        ppDetailSEPPeserta.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDetailSEPPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDetailSEPPesertaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDetailSEPPeserta);

        ppRujukan.setBackground(new java.awt.Color(255, 255, 254));
        ppRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRujukan.setForeground(new java.awt.Color(70, 70, 70));
        ppRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukan.setText("Buat Rujukan Keluar");
        ppRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRujukan.setName("ppRujukan"); // NOI18N
        ppRujukan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRujukan);

        ppRiwayatPerawatan.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayatPerawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayatPerawatan.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayatPerawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayatPerawatan.setText("Riwayat Perawatan");
        ppRiwayatPerawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayatPerawatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayatPerawatan.setName("ppRiwayatPerawatan"); // NOI18N
        ppRiwayatPerawatan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRiwayatPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatPerawatanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRiwayatPerawatan);

        WindowUpdatePulang.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUpdatePulang.setName("WindowUpdatePulang"); // NOI18N
        WindowUpdatePulang.setUndecorated(true);
        WindowUpdatePulang.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Tanggal Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        jLabel26.setText("Tanggal Pulang :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019 15:41:01" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 220, 23);

        WindowUpdatePulang.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        WindowRujukan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRujukan.setName("WindowRujukan"); // NOI18N
        WindowRujukan.setUndecorated(true);
        WindowRujukan.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Buat Rujukan Keluar VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        BtnCloseIn5.setBounds(692, 125, 100, 30);

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
        BtnSimpan5.setBounds(20, 125, 100, 30);

        jLabel30.setText("Tanggal Rujukan :");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame6.add(jLabel30);
        jLabel30.setBounds(0, 25, 102, 23);

        TanggalRujukKeluar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujukKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        TanggalRujukKeluar.setDisplayFormat("dd-MM-yyyy");
        TanggalRujukKeluar.setName("TanggalRujukKeluar"); // NOI18N
        TanggalRujukKeluar.setOpaque(false);
        TanggalRujukKeluar.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame6.add(TanggalRujukKeluar);
        TanggalRujukKeluar.setBounds(105, 25, 90, 23);

        jLabel12.setText("PPK Rujukan :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame6.add(jLabel12);
        jLabel12.setBounds(0, 55, 102, 23);

        KdPpkRujukan1.setEditable(false);
        KdPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan1.setHighlighter(null);
        KdPpkRujukan1.setName("KdPpkRujukan1"); // NOI18N
        internalFrame6.add(KdPpkRujukan1);
        KdPpkRujukan1.setBounds(105, 55, 75, 23);

        NmPpkRujukan1.setEditable(false);
        NmPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan1.setHighlighter(null);
        NmPpkRujukan1.setName("NmPpkRujukan1"); // NOI18N
        internalFrame6.add(NmPpkRujukan1);
        NmPpkRujukan1.setBounds(182, 55, 200, 23);

        btnPPKRujukan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan1.setMnemonic('X');
        btnPPKRujukan1.setToolTipText("Alt+X");
        btnPPKRujukan1.setName("btnPPKRujukan1"); // NOI18N
        btnPPKRujukan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukan1ActionPerformed(evt);
            }
        });
        btnPPKRujukan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukan1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnPPKRujukan1);
        btnPPKRujukan1.setBounds(385, 55, 28, 23);

        jLabel31.setText("Jns.Pelayanan :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame6.add(jLabel31);
        jLabel31.setBounds(425, 55, 85, 23);

        JenisPelayanan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan1.setSelectedIndex(1);
        JenisPelayanan1.setName("JenisPelayanan1"); // NOI18N
        JenisPelayanan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayanan1ItemStateChanged(evt);
            }
        });
        JenisPelayanan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayanan1KeyPressed(evt);
            }
        });
        internalFrame6.add(JenisPelayanan1);
        JenisPelayanan1.setBounds(514, 55, 278, 23);

        jLabel32.setText("Diagnosa Rujuk :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame6.add(jLabel32);
        jLabel32.setBounds(0, 85, 102, 23);

        KdPenyakit1.setEditable(false);
        KdPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit1.setHighlighter(null);
        KdPenyakit1.setName("KdPenyakit1"); // NOI18N
        internalFrame6.add(KdPenyakit1);
        KdPenyakit1.setBounds(105, 85, 75, 23);

        NmPenyakit1.setEditable(false);
        NmPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit1.setHighlighter(null);
        NmPenyakit1.setName("NmPenyakit1"); // NOI18N
        internalFrame6.add(NmPenyakit1);
        NmPenyakit1.setBounds(182, 85, 200, 23);

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
        internalFrame6.add(btnDiagnosa1);
        btnDiagnosa1.setBounds(385, 85, 28, 23);

        LabelPoli1.setText("Poli Tujuan :");
        LabelPoli1.setName("LabelPoli1"); // NOI18N
        internalFrame6.add(LabelPoli1);
        LabelPoli1.setBounds(425, 85, 85, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame6.add(KdPoli1);
        KdPoli1.setBounds(514, 85, 65, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        internalFrame6.add(NmPoli1);
        NmPoli1.setBounds(581, 85, 180, 23);

        btnPoli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli1.setMnemonic('X');
        btnPoli1.setToolTipText("Alt+X");
        btnPoli1.setName("btnPoli1"); // NOI18N
        btnPoli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli1ActionPerformed(evt);
            }
        });
        btnPoli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnPoli1);
        btnPoli1.setBounds(764, 85, 28, 23);

        jLabel33.setText("Tipe Rujukan :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame6.add(jLabel33);
        jLabel33.setBounds(200, 25, 80, 23);

        TipeRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Penuh", "1. Partial", "2. Rujuk Balik" }));
        TipeRujukan.setName("TipeRujukan"); // NOI18N
        TipeRujukan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TipeRujukanItemStateChanged(evt);
            }
        });
        TipeRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipeRujukanActionPerformed(evt);
            }
        });
        TipeRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipeRujukanKeyPressed(evt);
            }
        });
        internalFrame6.add(TipeRujukan);
        TipeRujukan.setBounds(283, 25, 130, 23);

        jLabel34.setText("Catatan :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(425, 25, 85, 23);

        Catatan1.setHighlighter(null);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        internalFrame6.add(Catatan1);
        Catatan1.setBounds(514, 25, 278, 23);

        WindowRujukan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        Popup1.setName("Popup1"); // NOI18N

        ppPengajuan.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(70, 70, 70));
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
        Popup1.add(ppPengajuan);

        ppPengajuan1.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan1.setForeground(new java.awt.Color(70, 70, 70));
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
        Popup1.add(ppPengajuan1);

        ppAmbilSep.setBackground(new java.awt.Color(255, 255, 254));
        ppAmbilSep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppAmbilSep.setForeground(new java.awt.Color(70, 70, 70));
        ppAmbilSep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppAmbilSep.setText("Ambil SEP di VClaim");
        ppAmbilSep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppAmbilSep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppAmbilSep.setName("ppAmbilSep"); // NOI18N
        ppAmbilSep.setPreferredSize(new java.awt.Dimension(200, 25));
        ppAmbilSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppAmbilSepBtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppAmbilSep);

        WindowCariSEP.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariSEP.setName("WindowCariSEP"); // NOI18N
        WindowCariSEP.setUndecorated(true);
        WindowCariSEP.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ambil SEP di VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(null);

        BtnCloseIn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn6.setMnemonic('U');
        BtnCloseIn6.setText("Tutup");
        BtnCloseIn6.setToolTipText("Alt+U");
        BtnCloseIn6.setName("BtnCloseIn6"); // NOI18N
        BtnCloseIn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn6ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnCloseIn6);
        BtnCloseIn6.setBounds(290, 70, 100, 30);

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
        internalFrame7.add(BtnSimpan6);
        BtnSimpan6.setBounds(20, 70, 100, 30);

        jLabel35.setText("No.SEP VClaim :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame7.add(jLabel35);
        jLabel35.setBounds(0, 25, 102, 23);

        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        NoSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPKeyPressed(evt);
            }
        });
        internalFrame7.add(NoSEP);
        NoSEP.setBounds(106, 25, 240, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('3');
        BtnCari1.setToolTipText("Alt+3");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
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
        internalFrame7.add(BtnCari1);
        BtnCari1.setBounds(350, 25, 28, 23);

        WindowCariSEP.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 90, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 12, 152, 23);

        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(359, 12, 368, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 12, 110, 23);

        jLabel5.setText("No.Kartu :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 72, 90, 23);

        NoKartu.setEditable(false);
        NoKartu.setBackground(new java.awt.Color(245, 250, 240));
        NoKartu.setComponentPopupMenu(Popup1);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(93, 72, 152, 23);

        jLabel20.setText("Tgl.SEP :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(187, 102, 65, 23);

        TanggalSEP.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        TanggalSEP.setDisplayFormat("dd-MM-yyyy");
        TanggalSEP.setName("TanggalSEP"); // NOI18N
        TanggalSEP.setOpaque(false);
        TanggalSEP.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSEPKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSEP);
        TanggalSEP.setBounds(255, 102, 95, 23);

        jLabel22.setText("Tgl.Rujuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 102, 90, 23);

        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
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
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(93, 102, 95, 23);

        jLabel23.setText("No.Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel23);
        jLabel23.setBounds(474, 72, 70, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormInput.add(NoRujukan);
        NoRujukan.setBounds(547, 72, 150, 23);

        jLabel9.setText("PPK Pelayanan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 282, 90, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormInput.add(KdPPK);
        KdPPK.setBounds(93, 282, 75, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormInput.add(NmPPK);
        NmPPK.setBounds(170, 282, 180, 23);

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
        FormInput.add(btnPPKRujukan);
        btnPPKRujukan.setBounds(352, 162, 28, 23);

        jLabel10.setText("PPK Rujukan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 162, 90, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        FormInput.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(93, 162, 75, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan.setHighlighter(null);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        FormInput.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(170, 162, 180, 23);

        jLabel11.setText("Diagnosa Awal :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 192, 90, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        FormInput.add(KdPenyakit);
        KdPenyakit.setBounds(93, 192, 75, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit.setHighlighter(null);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        FormInput.add(NmPenyakit);
        NmPenyakit.setBounds(170, 192, 180, 23);

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
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(352, 192, 28, 23);

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
        FormInput.add(btnPoli);
        btnPoli.setBounds(352, 222, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(170, 222, 180, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(93, 222, 75, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormInput.add(LabelPoli);
        LabelPoli.setBounds(0, 222, 90, 23);

        jLabel13.setText("Jns.Pelayanan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 312, 90, 23);

        jLabel14.setText("Catatan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 372, 90, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormInput.add(Catatan);
        Catatan.setBounds(93, 372, 257, 23);

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
        FormInput.add(JenisPelayanan);
        JenisPelayanan.setBounds(93, 312, 100, 23);

        LabelKelas.setText("Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormInput.add(LabelKelas);
        LabelKelas.setBounds(210, 312, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. Kelas 3" }));
        Kelas.setSelectedIndex(2);
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(250, 312, 100, 23);

        jLabel16.setText("Laka Lantas :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(380, 132, 87, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1. Ya" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormInput.add(LakaLantas);
        LakaLantas.setBounds(470, 132, 95, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 90, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(93, 42, 152, 23);

        jLabel18.setText("J.K.:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(504, 42, 70, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(577, 42, 150, 23);

        jLabel24.setText("Peserta :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(278, 42, 60, 23);

        JenisPeserta.setEditable(false);
        JenisPeserta.setBackground(new java.awt.Color(245, 250, 240));
        JenisPeserta.setHighlighter(null);
        JenisPeserta.setName("JenisPeserta"); // NOI18N
        FormInput.add(JenisPeserta);
        JenisPeserta.setBounds(341, 42, 150, 23);

        jLabel25.setText("Status :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(248, 72, 60, 23);

        Status.setEditable(false);
        Status.setBackground(new java.awt.Color(245, 250, 240));
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(311, 72, 150, 23);

        jLabel27.setText("Asal Rujukan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 132, 90, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(93, 132, 125, 23);

        jLabel15.setText("Eksekutif :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 342, 90, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormInput.add(Eksekutif);
        Eksekutif.setBounds(93, 342, 100, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormInput.add(LabelKelas1);
        LabelKelas1.setBounds(210, 342, 40, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormInput.add(COB);
        COB.setBounds(250, 342, 100, 23);

        jLabel28.setText("Penjamin Laka :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(380, 162, 87, 23);

        ChkAsa.setText("ASABRI PT");
        ChkAsa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkAsa.setName("ChkAsa"); // NOI18N
        ChkAsa.setOpaque(false);
        ChkAsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAsaActionPerformed(evt);
            }
        });
        FormInput.add(ChkAsa);
        ChkAsa.setBounds(590, 192, 140, 23);

        ChkJasaRaharja.setText("Jasa raharja PT");
        ChkJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkJasaRaharja.setName("ChkJasaRaharja"); // NOI18N
        ChkJasaRaharja.setOpaque(false);
        ChkJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJasaRaharjaActionPerformed(evt);
            }
        });
        FormInput.add(ChkJasaRaharja);
        ChkJasaRaharja.setBounds(470, 162, 110, 23);

        ChkBPJSTenaga.setText("BPJS Ketenagakerjaan");
        ChkBPJSTenaga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkBPJSTenaga.setName("ChkBPJSTenaga"); // NOI18N
        ChkBPJSTenaga.setOpaque(false);
        ChkBPJSTenaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkBPJSTenagaActionPerformed(evt);
            }
        });
        FormInput.add(ChkBPJSTenaga);
        ChkBPJSTenaga.setBounds(590, 162, 140, 23);

        ChkTaspen.setText("TASPEN PT");
        ChkTaspen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkTaspen.setName("ChkTaspen"); // NOI18N
        ChkTaspen.setOpaque(false);
        ChkTaspen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkTaspenActionPerformed(evt);
            }
        });
        FormInput.add(ChkTaspen);
        ChkTaspen.setBounds(470, 192, 80, 23);

        jLabel29.setText("No.Telp :");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel29);
        jLabel29.setBounds(571, 102, 58, 23);

        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(632, 102, 95, 23);

        Katarak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Katarak.setName("Katarak"); // NOI18N
        Katarak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KatarakKeyPressed(evt);
            }
        });
        FormInput.add(Katarak);
        Katarak.setBounds(470, 102, 95, 23);

        jLabel37.setText("Katarak :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(380, 102, 87, 23);

        jLabel38.setText("Tgl.KLL :");
        jLabel38.setName("jLabel38"); // NOI18N
        jLabel38.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel38);
        jLabel38.setBounds(571, 132, 58, 23);

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
        FormInput.add(TanggalKKL);
        TanggalKKL.setBounds(632, 132, 95, 23);

        jLabel39.setText("No.SKDP :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel39);
        jLabel39.setBounds(217, 132, 65, 23);

        NoSKDP.setHighlighter(null);
        NoSKDP.setName("NoSKDP"); // NOI18N
        NoSKDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSKDPKeyPressed(evt);
            }
        });
        FormInput.add(NoSKDP);
        NoSKDP.setBounds(285, 132, 65, 23);

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
        FormInput.add(btnSKDP);
        btnSKDP.setBounds(352, 132, 28, 23);

        LabelPoli2.setText("Dokter DPJP :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormInput.add(LabelPoli2);
        LabelPoli2.setBounds(0, 252, 90, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(93, 252, 75, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(170, 252, 180, 23);

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
        FormInput.add(btnDPJP);
        btnDPJP.setBounds(352, 252, 28, 23);

        jLabel36.setText("Keterangan :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel36);
        jLabel36.setBounds(380, 222, 87, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(470, 222, 257, 23);

        jLabel40.setText("Suplesi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(380, 252, 87, 23);

        Suplesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Suplesi.setName("Suplesi"); // NOI18N
        Suplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuplesiKeyPressed(evt);
            }
        });
        FormInput.add(Suplesi);
        Suplesi.setBounds(470, 252, 90, 23);

        NoSEPSuplesi.setHighlighter(null);
        NoSEPSuplesi.setName("NoSEPSuplesi"); // NOI18N
        NoSEPSuplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPSuplesiKeyPressed(evt);
            }
        });
        FormInput.add(NoSEPSuplesi);
        NoSEPSuplesi.setBounds(632, 252, 95, 23);

        jLabel41.setText("SEP Suplesi :");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel41);
        jLabel41.setBounds(562, 252, 68, 23);

        LabelPoli3.setText("Propinsi KLL :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(380, 282, 87, 23);

        KdPropinsi.setEditable(false);
        KdPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        KdPropinsi.setHighlighter(null);
        KdPropinsi.setName("KdPropinsi"); // NOI18N
        FormInput.add(KdPropinsi);
        KdPropinsi.setBounds(470, 282, 55, 23);

        NmPropinsi.setEditable(false);
        NmPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        NmPropinsi.setHighlighter(null);
        NmPropinsi.setName("NmPropinsi"); // NOI18N
        FormInput.add(NmPropinsi);
        NmPropinsi.setBounds(527, 282, 170, 23);

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
        FormInput.add(btnPropinsi);
        btnPropinsi.setBounds(699, 282, 28, 23);

        LabelPoli4.setText("Kabupaten KLL :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(380, 312, 87, 23);

        KdKabupaten.setEditable(false);
        KdKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        KdKabupaten.setHighlighter(null);
        KdKabupaten.setName("KdKabupaten"); // NOI18N
        FormInput.add(KdKabupaten);
        KdKabupaten.setBounds(470, 312, 55, 23);

        NmKabupaten.setEditable(false);
        NmKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        NmKabupaten.setHighlighter(null);
        NmKabupaten.setName("NmKabupaten"); // NOI18N
        FormInput.add(NmKabupaten);
        NmKabupaten.setBounds(527, 312, 170, 23);

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
        FormInput.add(btnKabupaten);
        btnKabupaten.setBounds(699, 312, 28, 23);

        LabelPoli5.setText("Kecamatan KLL :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(380, 342, 87, 23);

        KdKecamatan.setEditable(false);
        KdKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        KdKecamatan.setHighlighter(null);
        KdKecamatan.setName("KdKecamatan"); // NOI18N
        FormInput.add(KdKecamatan);
        KdKecamatan.setBounds(470, 342, 55, 23);

        NmKecamatan.setEditable(false);
        NmKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        NmKecamatan.setHighlighter(null);
        NmKecamatan.setName("NmKecamatan"); // NOI18N
        FormInput.add(NmKecamatan);
        NmKecamatan.setBounds(527, 342, 170, 23);

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
        FormInput.add(btnKecamatan);
        btnKecamatan.setBounds(699, 342, 28, 23);

        btnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRiwayat.setMnemonic('X');
        btnRiwayat.setToolTipText("Alt+X");
        btnRiwayat.setName("btnRiwayat"); // NOI18N
        btnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatActionPerformed(evt);
            }
        });
        FormInput.add(btnRiwayat);
        btnRiwayat.setBounds(699, 72, 28, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input SEP", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data SEP", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        }else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        }else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        }else if (KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        }else if (KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        }else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        }else if((JenisPelayanan.getSelectedIndex()==1)&&(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals(""))){
            Valid.textKosong(KdPoli, "Poli Tujuan");        
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
        }*/else{  
            if(JenisPelayanan.getSelectedIndex()==0){
                insertSEP();
            }else if(JenisPelayanan.getSelectedIndex()==1){
                if(NmPoli.getText().toLowerCase().contains("darurat")){
                    if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                            "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                            "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"%' and "+
                            "nmpolitujuan like '%darurat%'")>=3){
                        JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan 3x pembuatan SEP di jenis pelayanan yang sama..!!");
                        TCari.requestFocus();
                    }else{
                        insertSEP();
                    }
                }else if(!NmPoli.getText().toLowerCase().contains("darurat")){
                    if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                            "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                            "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"%' and "+
                            "nmpolitujuan not like '%darurat%'")>=1){
                        JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan pembuatan SEP di jenis pelayanan yang sama..!!");
                        TCari.requestFocus();
                    }else{
                        insertSEP();
                    }
                } 
            }                
            
        }   
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,btnKecamatan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            try {
                bodyWithDeleteRequest();
            }catch (Exception ex) {
                System.out.println("Notifikasi Bridging : "+ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
        }            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            }else if (NoKartu.getText().trim().equals("")) {
                Valid.textKosong(NoKartu, "Nomor Kartu");
            }else if (NoRujukan.getText().trim().equals("")) {
                Valid.textKosong(NoRujukan, "Nomor Rujukan");
            }else if (KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")) {
                Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
            }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
                Valid.textKosong(KdPPK, "PPK Pelayanan");
            }else if (KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")) {
                Valid.textKosong(KdPenyakit, "Diagnosa");
            }else if (Catatan.getText().trim().equals("")) {
                Valid.textKosong(Catatan, "Catatan");
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
            }else if (NoSKDP.getText().trim().equals("")) {
                Valid.textKosong(NoSKDP, "No.SKDP");
            }else{
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
                    
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    URL = link+"/SEP/1.1/Update";	
                    requestJson ="{" +
                                  "\"request\":" +
                                     "{" +
                                        "\"t_sep\":" +
                                           "{" +
                                            "\"noSep\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"," +
                                            "\"klsRawat\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"noMR\":\""+TNoRM.getText()+"\"," +
                                            "\"rujukan\": {"+
                                                "\"asalRujukan\":\""+AsalRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                                "\"tglRujukan\":\""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+"\"," +
                                                "\"noRujukan\":\""+NoRujukan.getText()+"\"," +
                                                "\"ppkRujukan\":\""+KdPpkRujukan.getText()+"\"" +
                                            "},"+
                                            "\"catatan\":\""+Catatan.getText()+"\"," +
                                            "\"diagAwal\":\""+KdPenyakit.getText()+"\"," +
                                            "\"poli\": {" +
                                                "\"eksekutif\": \""+Eksekutif.getSelectedItem().toString().substring(0,1)+"\"" +
                                            "},"+
                                            "\"cob\": {" +
                                                "\"cob\": \""+COB.getSelectedItem().toString().substring(0,1)+"\"" +
                                            "},"+
                                            "\"katarak\": {" +
                                                "\"katarak\": \""+Katarak.getSelectedItem().toString().substring(0,1)+"\"" +
                                            "},"+
                                            "\"skdp\": {" +
                                                "\"noSurat\": \""+NoSKDP.getText()+"\"," +
                                                "\"kodeDPJP\": \""+KdDPJP.getText()+"\"" +
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
                                            "\"noTelp\":\""+NoTelp.getText()+"\","+
                                            "\"user\":\""+user+"\"" +
                                           "}" +
                                     "}" +
                                 "}";
                    requestEntity = new HttpEntity(requestJson,headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    response = root.path("response");
                    if(nameNode.path("code").asText().equals("200")){
                        Sequel.mengedit("bridging_sep",
                             "no_sep=?","no_sep=?,no_rawat=?,tglrujukan=?,no_rujukan=?,kdppkrujukan=?,"+
                             "nmppkrujukan=?,kdppkpelayanan=?,nmppkpelayanan=?,catatan=?,diagawal=?,"+
                             "nmdiagnosaawal=?,klsrawat=?,lakalantas=?,user=?,nomr=?,nama_pasien=?,"+
                             "tanggal_lahir=?,peserta=?,jkel=?,no_kartu=?,asal_rujukan=?,eksekutif=?,"+
                             "cob=?,penjamin=?,notelep=?,katarak=?,tglkkl=?,keterangankkl=?,suplesi=?,"+
                             "no_sep_suplesi=?,kdprop=?,nmprop=?,kdkab=?,nmkab=?,kdkec=?,nmkec=?,noskdp=?,"+
                             "kddpjp=?,nmdpdjp=?",40,new String[]{
                             response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""), 
                             NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), 
                             NmPPK.getText(),Catatan.getText(),KdPenyakit.getText(), 
                             NmPenyakit.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                             LakaLantas.getSelectedItem().toString().substring(0,1),user, 
                             TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                             AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                             COB.getSelectedItem().toString(),penjamin,NoTelp.getText(),Katarak.getSelectedItem().toString(),
                             tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),
                             NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                             KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText(),
                             tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                         });
                         Sequel.mengedit("rujuk_masuk","no_rawat=?","no_rawat=?,perujuk=?,no_rujuk=?",4,new String[]{
                             TNoRw.getText(),NmPpkRujukan.getText(),NoRujukan.getText(),
                             tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
                         });
                         emptTeks(); 
                         TabRawat.setSelectedIndex(1);
                         tampil();          
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
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau diganti..!!");
        } 
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowRujukan.dispose();
        WindowUpdatePulang.dispose();
        WindowCariSEP.dispose();
        faskes.dispose();
        penyakit.dispose();
        skdp.dispose();
        propinsi.dispose();
        kabupaten.dispose();
        kecamatan.dispose();
        dokter.dispose();
        poli.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                param.put("parameter","%"+TCari.getText().trim()+"%");
            Valid.MyReport("rptBridgingDaftar.jasper","report","::[ Data Bridging SEP ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
               getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                i=tbObat.getSelectedColumn();
                if(i==0){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==1){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }else{
                    TabRawat.setSelectedIndex(0);
                }
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
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
                i=tbObat.getSelectedColumn();
                if(i==0){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==1){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }
                
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt, TCari, TanggalRujuk);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        pilihan=1;
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);        
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt,NoSKDP,btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        pilihan=1;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);        
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,btnPPKRujukan,btnPoli);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        pilihan=1;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);        
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        Valid.pindah(evt,btnDiagnosa,btnDPJP);
    }//GEN-LAST:event_btnPoliKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,COB,Katarak);
    }//GEN-LAST:event_CatatanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(akses.getform().equals("DlgReg")||akses.getform().equals("DlgIGD")||akses.getform().equals("DlgKamarInap")){
            prb="";
            no_peserta=Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText());
            if(no_peserta.trim().equals("")){
                JOptionPane.showMessageDialog(null,"Pasien tidak mempunyai kepesertaan BPJS");
                dispose();
            }else{
                cekViaBPJSKartu.tampil(no_peserta);
                if(cekViaBPJSKartu.informasi.equals("OK")){
                    if(cekViaBPJSKartu.statusPesertaketerangan.equals("AKTIF")){
                        TPasien.setText(cekViaBPJSKartu.nama);
                        TglLahir.setText(cekViaBPJSKartu.tglLahir);
                        JK.setText(cekViaBPJSKartu.sex);
                        NoKartu.setText(no_peserta);
                        JenisPeserta.setText(cekViaBPJSKartu.jenisPesertaketerangan);
                        Status.setText(cekViaBPJSKartu.statusPesertaketerangan);
                        KdPpkRujukan.setText(cekViaBPJSKartu.provUmumkdProvider);
                        NmPpkRujukan.setText(cekViaBPJSKartu.provUmumnmProvider);
                        if(cekViaBPJSKartu.hakKelaskode.equals("1")){
                            Kelas.setSelectedIndex(0);
                        }else if(cekViaBPJSKartu.hakKelaskode.equals("2")){
                            Kelas.setSelectedIndex(1);
                        }else if(cekViaBPJSKartu.hakKelaskode.equals("3")){
                            Kelas.setSelectedIndex(2);
                        }
                        NoTelp.setText(cekViaBPJSKartu.mrnoTelepon);
                        prb=cekViaBPJSKartu.informasiprolanisPRB.replaceAll("null","");
                        NoRujukan.requestFocus();                                               
                    }else{
                        JOptionPane.showMessageDialog(null,"Status kepesertaan tidak aktif..!!");
                        dispose();
                    }
                }else{
                    dispose();
                }                    
            } 
        }
    }//GEN-LAST:event_formWindowOpened

    private void ppSEPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEPBtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReport("rptBridgingSEP.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP2.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }        
    }//GEN-LAST:event_ppSEPBtnPrintActionPerformed

    private void ppPulangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPulangBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            WindowUpdatePulang.setLocationRelativeTo(internalFrame1);
            WindowUpdatePulang.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau diupdate tanggal pulangnya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPulangBtnPrintActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowUpdatePulang.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        }else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        }else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        }else if (KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        }else if (KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        }else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        }else{
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                URL = link+"/Sep/updtglplg";	
                requestJson ="{" +
                              "\"request\":" +
                                 "{" +
                                    "\"t_sep\":" +
                                       "{" +
                                        "\"noSep\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"," +
                                        "\"tglPulang\":\""+Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+"\"," +
                                        "\"user\":\""+user+"\"" +                                            
                                       "}" +
                                 "}" +
                             "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                response = root.path("response");
                if(nameNode.path("code").asText().equals("200")){
                    Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                         Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+" "+TanggalPulang.getSelectedItem().toString().substring(11,19),
                         tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    });
                    emptTeks();                         
                    tampil();     
                    reply = JOptionPane.showConfirmDialog(rootPane,"Proses update pulang di BPJS selesai.\nApakah mau skalian mengupdate data kamar inap..?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        akses.setstatus(false);
                        DlgKamarInap kamarinap=new DlgKamarInap(null,false);
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                        }else{ 
                            kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            kamarinap.setLocationRelativeTo(internalFrame1);
                            kamarinap.emptTeks();
                            kamarinap.isCek();
                            kamarinap.setNoRm(TNoRw.getText()); 
                            kamarinap.setVisible(true);                    
                        }  
                    }
                    WindowUpdatePulang.dispose();
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
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah(evt, NoRujukan, TanggalSEP);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah(evt, TanggalRujuk,AsalRujukan);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt,NoTelp,TanggalKKL);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt,btnDPJP,Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,JenisPelayanan,Eksekutif);
    }//GEN-LAST:event_KelasKeyPressed

    private void ppPengajuanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                URL = link+"/Sep/pengajuanSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
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
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data peserta yang mau dimapping transaksinya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuanBtnPrintActionPerformed

    private void ppDetailSEPPesertaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDetailSEPPesertaBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            BPJSCekDetailSEP detail=new BPJSCekDetailSEP(null,true);
            detail.tampil(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            detail.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            detail.setLocationRelativeTo(internalFrame1);
            detail.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP ...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());            
    }//GEN-LAST:event_ppDetailSEPPesertaBtnPrintActionPerformed

    private void JenisPelayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayananItemStateChanged
        if(JenisPelayanan.getSelectedIndex()==0){
            KdPoli.setText("");
            NmPoli.setText("");
            LabelPoli.setVisible(false);
            KdPoli.setVisible(false);
            NmPoli.setVisible(false);
            btnPoli.setVisible(false);
        }else if(JenisPelayanan.getSelectedIndex()==1){  
            LabelPoli.setVisible(true);
            KdPoli.setVisible(true);
            NmPoli.setVisible(true);
            btnPoli.setVisible(true);
        }
    }//GEN-LAST:event_JenisPelayananItemStateChanged

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        Valid.pindah(evt, TanggalSEP,NoSKDP);
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void EksekutifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EksekutifKeyPressed
        Valid.pindah(evt,Kelas,COB);
    }//GEN-LAST:event_EksekutifKeyPressed

    private void COBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COBKeyPressed
        Valid.pindah(evt,Eksekutif,Catatan);
    }//GEN-LAST:event_COBKeyPressed

    private void ChkAsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAsaActionPerformed
        
    }//GEN-LAST:event_ChkAsaActionPerformed

    private void ChkJasaRaharjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJasaRaharjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJasaRaharjaActionPerformed

    private void ChkBPJSTenagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkBPJSTenagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkBPJSTenagaActionPerformed

    private void ChkTaspenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkTaspenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTaspenActionPerformed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt,Katarak,LakaLantas);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                URL = link+"/Sep/aprovalSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
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
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data peserta yang mau dimapping transaksinya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan1BtnPrintActionPerformed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void ppRujukanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            WindowRujukan.setLocationRelativeTo(internalFrame1);
            WindowRujukan.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dibuatkan rujukan...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppRujukanBtnPrintActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowRujukan.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        }else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        }else if (KdPpkRujukan1.getText().trim().equals("")||NmPpkRujukan1.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan1, "PPK Rujukan");
        }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        }else if (KdPenyakit1.getText().trim().equals("")||NmPenyakit1.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit1, "Diagnosa");
        }else if (Catatan1.getText().trim().equals("")) {
            Valid.textKosong(Catatan1, "Catatan");
        }else if(NmPoli1.getText().trim().equals("")&&(!TipeRujukan.getSelectedItem().toString().equals("2. Rujuk Balik"))){
            Valid.textKosong(KdPoli1, "Poli Tujuan");        
        }else{  
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                URL = link+"/Rujukan/insert";	
                requestJson ="{" +
                                "\"request\": {" +
                                    "\"t_rujukan\": {" +
                                        "\"noSep\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"," +
                                        "\"tglRujukan\": \""+Valid.SetTgl(TanggalRujukKeluar.getSelectedItem()+"")+"\"," +
                                        "\"ppkDirujuk\": \""+KdPpkRujukan1.getText()+"\"," +
                                        "\"jnsPelayanan\": \""+JenisPelayanan1.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"catatan\": \""+Catatan1.getText()+"\"," +
                                        "\"diagRujukan\": \""+KdPenyakit1.getText()+"\"," +
                                        "\"tipeRujukan\": \""+TipeRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"poliRujukan\": \""+KdPoli1.getText()+"\"," +
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
                    if(Sequel.menyimpantf2("bridging_rujukan_bpjs","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rujukan",13,new String[]{
                            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),Valid.SetTgl(TanggalRujukKeluar.getSelectedItem()+""),
                            KdPpkRujukan1.getText(),NmPpkRujukan1.getText(),JenisPelayanan1.getSelectedItem().toString().substring(0,1),
                            Catatan1.getText(),KdPenyakit1.getText(),NmPenyakit1.getText(),
                            TipeRujukan.getSelectedItem().toString(),KdPoli1.getText(),
                            NmPoli1.getText(),response.path("rujukan").path("noRujukan").asText(),
                            user
                        })==true){
                        Sequel.menyimpan("rujuk","'"+response.path("rujukan").path("noRujukan").asText()+"','"+
                            TNoRw.getText()+"','"+NmPpkRujukan1.getText()+"','"+
                            Valid.SetTgl(TanggalRujukKeluar.getSelectedItem()+"")+"','"+ 
                            NmPenyakit1.getText()+"','"+Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText())+
                            "','-','-','"+Catatan1.getText()+"','12:00:01'","No.Rujuk");
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("norujuk",response.path("rujukan").path("noRujukan").asText());
                        param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                        Valid.MyReport("rptBridgingRujukanBPJS.jasper",param,"::[ Surat Rujukan Keluar VClaim ]::");
                        this.setCursor(Cursor.getDefaultCursor());
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
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void btnPPKRujukan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukan1ActionPerformed
        pilihan=2;
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);        
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukan1ActionPerformed

    private void btnPPKRujukan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPKRujukan1KeyPressed

    private void JenisPelayanan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayanan1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1ItemStateChanged

    private void JenisPelayanan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayanan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1KeyPressed

    private void btnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosa1ActionPerformed
        pilihan=2;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);        
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosa1ActionPerformed

    private void btnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosa1KeyPressed

    private void btnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli1ActionPerformed
        pilihan=2;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);        
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoli1ActionPerformed

    private void btnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1KeyPressed

    private void TipeRujukanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TipeRujukanItemStateChanged
        if(TipeRujukan.getSelectedItem().equals("2. Rujuk Balik")){
            KdPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
        }
    }//GEN-LAST:event_TipeRujukanItemStateChanged

    private void TipeRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipeRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanKeyPressed

    private void Catatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan1KeyPressed

    private void TipeRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipeRujukanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanActionPerformed

    private void ppSEP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP1BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReport("rptBridgingSEP3.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP4.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }   
    }//GEN-LAST:event_ppSEP1BtnPrintActionPerformed

    private void ppSEP2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP2BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",TNoRw.getText());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReport("rptBridgingSEP5.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP6.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        } 
    }//GEN-LAST:event_ppSEP2BtnPrintActionPerformed

    private void ppSEP3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP3BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",TNoRw.getText());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReport("rptBridgingSEP7.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP8.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        } 
    }//GEN-LAST:event_ppSEP3BtnPrintActionPerformed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        WindowCariSEP.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        if(!NoSEP.getText().equals("")){
            if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            }else if (NoKartu.getText().trim().equals("")) {
                Valid.textKosong(NoKartu, "Nomor Kartu");
            }else if (NoRujukan.getText().trim().equals("")) {
                Valid.textKosong(NoRujukan, "Nomor Rujukan");
            }else if (KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")) {
                Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
            }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
                Valid.textKosong(KdPPK, "PPK Pelayanan");
            }else if (KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")) {
                Valid.textKosong(KdPenyakit, "Diagnosa");
            }else if (Catatan.getText().trim().equals("")) {
                Valid.textKosong(Catatan, "Catatan");
            }else if((JenisPelayanan.getSelectedIndex()==1)&&(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals(""))){
                Valid.textKosong(KdPoli, "Poli Tujuan");        
            }else if((LakaLantas.getSelectedIndex()==1)&&Keterangan.getText().equals("")) {
                Valid.textKosong(Keterangan,"Keterangan");
            }else if((LakaLantas.getSelectedIndex()==1)&&NmPropinsi.getText().equals("")) {
                Valid.textKosong(btnPropinsi,"Propinsi");
            }else if((LakaLantas.getSelectedIndex()==1)&&NmKabupaten.getText().equals("")) {
                Valid.textKosong(btnKabupaten,"Kabupaten");
            }else if((LakaLantas.getSelectedIndex()==1)&&NmKecamatan.getText().equals("")) {
                Valid.textKosong(btnKecamatan,"Kecamatan");
            }else if (NoSKDP.getText().trim().equals("")) {
                Valid.textKosong(NoSKDP, "No.SKDP");
            }else{ 
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
                
                URL = link+"/SEP/1.1/insert";	

                tglkkl="0000-00-00";
                if(LakaLantas.getSelectedIndex()==1){
                    tglkkl=Valid.SetTgl(TanggalKKL.getSelectedItem()+"");
                }
                
                if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",44,new String[]{
                     NoSEP.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""), 
                     NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                     JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                     NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                     LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),
                     TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                     COB.getSelectedItem().toString(),penjamin,NoTelp.getText(),Katarak.getSelectedItem().toString().substring(0,1),
                     tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString().substring(0,1),
                     NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                     KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText()
                })==true){
                    tampil();
                    WindowCariSEP.dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void NoSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSEPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSEPKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(!NoSEP.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
                URL = link+"/SEP/"+NoSEP.getText();	
                requestEntity = new HttpEntity(headers);
                //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("message").asText().equals("Sukses")){
                    response = root.path("response");
                    Catatan.setText(response.path("catatan").asText());
                    NmPenyakit.setText(response.path("diagnosa").asText());
                    if(response.path("jnsPelayanan").asText().toLowerCase().contains("inap")){
                        JenisPelayanan.setSelectedIndex(0);
                    }else{
                        JenisPelayanan.setSelectedIndex(1);
                    }
                    
                    if(response.path("kelasRawat").asText().toLowerCase().equals("1")){
                        Kelas.setSelectedIndex(0);
                    }else if(response.path("kelasRawat").asText().toLowerCase().equals("2")){
                        Kelas.setSelectedIndex(1);
                    }else if(response.path("kelasRawat").asText().toLowerCase().equals("3")){
                        Kelas.setSelectedIndex(2);
                    }
                    
                    if(response.path("poliEksekutif").asText().equals("0")){
                        Eksekutif.setSelectedIndex(0);
                    }else{
                        Eksekutif.setSelectedIndex(1);
                    }
                }else {
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi Peserta : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                    dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void ppAmbilSepBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppAmbilSepBtnPrintActionPerformed
        if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        }else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        }else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        }else if (KdPpkRujukan.getText().trim().equals("")||NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        }else if (KdPenyakit.getText().trim().equals("")||NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        }else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        }else if((JenisPelayanan.getSelectedIndex()==1)&&(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals(""))){
            Valid.textKosong(KdPoli, "Poli Tujuan");        
        }else{  
            WindowCariSEP.setLocationRelativeTo(internalFrame1);
            WindowCariSEP.setVisible(true);
        }
    }//GEN-LAST:event_ppAmbilSepBtnPrintActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<530){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,410));
            if(this.getWidth()<780){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,410));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<780){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,FormInput.HEIGHT));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void KatarakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KatarakKeyPressed
        Valid.pindah(evt,Catatan,NoTelp);
    }//GEN-LAST:event_KatarakKeyPressed

    private void TanggalKKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKKLKeyPressed
        Valid.pindah(evt,LakaLantas,Keterangan);
    }//GEN-LAST:event_TanggalKKLKeyPressed

    private void NoSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSKDPKeyPressed
        Valid.pindah(evt, AsalRujukan,btnPPKRujukan);
    }//GEN-LAST:event_NoSKDPKeyPressed

    private void btnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSKDPActionPerformed
        skdp.setNoRm(TNoRM.getText(),TPasien.getText());
        skdp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        skdp.setLocationRelativeTo(internalFrame1);        
        skdp.setVisible(true);
    }//GEN-LAST:event_btnSKDPActionPerformed

    private void btnSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSKDPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSKDPKeyPressed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);        
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed
        if(btnPoli.isVisible()==true){
            Valid.pindah(evt,btnPoli,JenisPelayanan);
        }else{
            Valid.pindah(evt,btnDiagnosa,JenisPelayanan);
        }            
    }//GEN-LAST:event_btnDPJPKeyPressed

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
        propinsi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propinsi.setLocationRelativeTo(internalFrame1);
        propinsi.setVisible(true);
    }//GEN-LAST:event_btnPropinsiActionPerformed

    private void btnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPropinsiKeyPressed
        Valid.pindah(evt,NoSEPSuplesi,btnKabupaten);
    }//GEN-LAST:event_btnPropinsiKeyPressed

    private void btnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabupatenActionPerformed
        if(KdPropinsi.getText().trim().equals("")||NmPropinsi.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih propinsi dulu..!!");
            btnPropinsi.requestFocus();
        }else{
            kabupaten.setPropinsi(KdPropinsi.getText(),NmPropinsi.getText());
            kabupaten.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kabupaten.setLocationRelativeTo(internalFrame1);
            kabupaten.setVisible(true);
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
            kecamatan.setPropinsi(KdKabupaten.getText(),NmKabupaten.getText());
            kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kecamatan.setLocationRelativeTo(internalFrame1);
            kecamatan.setVisible(true);
        }
    }//GEN-LAST:event_btnKecamatanActionPerformed

    private void btnKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKecamatanKeyPressed
        Valid.pindah(evt,btnKabupaten,BtnSimpan);
    }//GEN-LAST:event_btnKecamatanKeyPressed

    private void ppRiwayatPerawatanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatPerawatanBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
                resume.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
                resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
    }//GEN-LAST:event_ppRiwayatPerawatanBtnPrintActionPerformed

    private void ppSEP4BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP4BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReportPDF("rptBridgingSEP.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReportPDF("rptBridgingSEP2.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }  
    }//GEN-LAST:event_ppSEP4BtnPrintActionPerformed

    private void ppSEP5BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP5BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReportPDF("rptBridgingSEP3.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReportPDF("rptBridgingSEP4.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }  
    }//GEN-LAST:event_ppSEP5BtnPrintActionPerformed

    private void ppSEP6BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP6BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",TNoRw.getText());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReportPDF("rptBridgingSEP5.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReportPDF("rptBridgingSEP6.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        } 
    }//GEN-LAST:event_ppSEP6BtnPrintActionPerformed

    private void ppSEP7BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP7BtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",TNoRw.getText());
            param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReportPDF("rptBridgingSEP7.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReportPDF("rptBridgingSEP8.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        } 
    }//GEN-LAST:event_ppSEP7BtnPrintActionPerformed

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatActionPerformed
        historiPelayanan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        historiPelayanan.setLocationRelativeTo(internalFrame1);
        historiPelayanan.setKartu(NoKartu.getText());
        historiPelayanan.setVisible(true);
    }//GEN-LAST:event_btnRiwayatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSDataSEP dialog = new BPJSDataSEP(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AsalRujukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnCloseIn6;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.Button BtnSimpan6;
    private widget.ComboBox COB;
    private widget.TextBox Catatan;
    private widget.TextBox Catatan1;
    private widget.CekBox ChkAsa;
    private widget.CekBox ChkBPJSTenaga;
    private widget.CekBox ChkJasaRaharja;
    private widget.CekBox ChkTaspen;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Eksekutif;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox JenisPelayanan;
    private widget.ComboBox JenisPelayanan1;
    private widget.TextBox JenisPeserta;
    private widget.ComboBox Katarak;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdKabupaten;
    private widget.TextBox KdKecamatan;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPenyakit1;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox KdPpkRujukan1;
    private widget.TextBox KdPropinsi;
    private widget.ComboBox Kelas;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli1;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.ComboBox LakaLantas;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmKabupaten;
    private widget.TextBox NmKecamatan;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPenyakit1;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NmPpkRujukan1;
    private widget.TextBox NmPropinsi;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSEPSuplesi;
    private widget.TextBox NoSKDP;
    private widget.TextBox NoTelp;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu Popup1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Status;
    private widget.ComboBox Suplesi;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalKKL;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalRujukKeluar;
    private widget.Tanggal TanggalSEP;
    private widget.TextBox TglLahir;
    private widget.ComboBox TipeRujukan;
    private javax.swing.JDialog WindowCariSEP;
    private javax.swing.JDialog WindowRujukan;
    private javax.swing.JDialog WindowUpdatePulang;
    private widget.Button btnDPJP;
    private widget.Button btnDiagnosa;
    private widget.Button btnDiagnosa1;
    private widget.Button btnKabupaten;
    private widget.Button btnKecamatan;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPPKRujukan1;
    private widget.Button btnPoli;
    private widget.Button btnPoli1;
    private widget.Button btnPropinsi;
    private widget.Button btnRiwayat;
    private widget.Button btnSKDP;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
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
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppAmbilSep;
    private javax.swing.JMenuItem ppDetailSEPPeserta;
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private javax.swing.JMenuItem ppPulang;
    private javax.swing.JMenuItem ppRiwayatPerawatan;
    private javax.swing.JMenuItem ppRujukan;
    private javax.swing.JMenuItem ppSEP;
    private javax.swing.JMenuItem ppSEP1;
    private javax.swing.JMenuItem ppSEP2;
    private javax.swing.JMenuItem ppSEP3;
    private javax.swing.JMenuItem ppSEP4;
    private javax.swing.JMenuItem ppSEP5;
    private javax.swing.JMenuItem ppSEP6;
    private javax.swing.JMenuItem ppSEP7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"+
                    "if(bridging_sep.lakalantas='0','0. Tidak','1. Ya'),bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,"+
                    "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,"+
                    "bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,"+
                    "bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"+
                    "bridging_sep.nmprop,bridging_sep.kdkab,"+
                    "bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,"+
                    "bridging_sep.kddpjp,bridging_sep.nmdpdjp from bridging_sep where "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.no_sep like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nomr like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nama_pasien like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmppkrujukan like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.diagawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmdiagnosaawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.no_rawat like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.no_kartu like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmprop like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmkab like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmkec like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmdpdjp like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.asal_rujukan like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.notelep like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmpolitujuan like ? order by bridging_sep.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                ps.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(42,"%"+TCari.getText().trim()+"%");
                ps.setString(43,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(44,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(45,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(14)+" "+rs.getString(15),rs.getString(16),
                        rs.getString(17),rs.getString(18),rs.getString(19),rs.getString(20),
                        rs.getString(21),rs.getString(22),rs.getString(23),rs.getString(24),
                        rs.getString(25),rs.getString(26),rs.getString(27),rs.getString(28),
                        rs.getString(29).replaceAll("1","1.Jasa Raharja").replaceAll("2","2.BPJS").replaceAll("3","3.Taspen").replaceAll("4","4.Asabri"),
                        rs.getString(30),rs.getString(31),rs.getString(32),
                        rs.getString(33),rs.getString(34),rs.getString(35),rs.getString(36),
                        rs.getString(37),rs.getString(38),rs.getString(39),rs.getString(40),
                        rs.getString(41),rs.getString(42),rs.getString(43),rs.getString(44)
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
        LCount.setText(""+tabMode.getRowCount()); 
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());  
        Catatan.setText("-");
    }
    
    private void emptTeks(){
        TNoRw.setText("");
        TPasien.setText("");
        TanggalSEP.setDate(new Date());
        TanggalRujuk.setDate(new Date());
        TglLahir.setText("");
        NoKartu.setText("");
        JenisPeserta.setText("");
        Status.setText("");
        JK.setText("");
        NoRujukan.setText("");
        KdPpkRujukan.setText("");
        NmPpkRujukan.setText("");
        JenisPelayanan.setSelectedIndex(1);
        Catatan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Kelas.setSelectedIndex(2);
        LakaLantas.setSelectedIndex(0);
        TNoRM.setText("");
        NoSKDP.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        Keterangan.setText("");
        NoSEPSuplesi.setText("");
        KdPropinsi.setText("");
        NmPropinsi.setText("");
        KdKabupaten.setText("");
        NmKabupaten.setText("");
        KdKecamatan.setText("");
        NmKecamatan.setText("");
        Katarak.setSelectedIndex(0);
        Suplesi.setSelectedIndex(0);
        TanggalKKL.setDate(new Date());
        ChkAsa.setSelected(false);
        ChkBPJSTenaga.setSelected(false);
        ChkJasaRaharja.setSelected(false);
        ChkTaspen.setSelected(false);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"' ","BR/"+dateformat.format(TanggalSEP.getDate())+"/",4,NoBalasan);
        NoRujukan.requestFocus();
    }
    
    public void setNoRm(String norwt, Date tgl1,String status,String kdpoli,String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?",kdpoli));
        NmPoli.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?",KdPoli.getText()));
        JenisPelayanan.setSelectedItem(status);
        JenisPelayananItemStateChanged(null);
        isRawat();            
    }
      
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbpjs_sep());
        BtnHapus.setEnabled(akses.getbpjs_sep());
        BtnPrint.setEnabled(akses.getbpjs_sep());
        BtnEdit.setEnabled(akses.getbpjs_sep());      
        ppDetailSEPPeserta.setEnabled(akses.getbpjs_sep());
        ppPengajuan.setEnabled(akses.getbpjs_sep());
        ppPengajuan1.setEnabled(akses.getbpjs_sep());
        ppPulang.setEnabled(akses.getbpjs_sep());
        ppSEP.setEnabled(akses.getbpjs_sep());               
        ppRiwayatPerawatan.setEnabled(akses.getresume_pasien());    
        ppRujukan.setEnabled(akses.getbpjs_rujukan_keluar());
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            NoRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KdPpkRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmPpkRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KdPPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NmPPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            JenisPelayanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Catatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().replaceAll(KdPenyakit.getText(),""));
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            NmPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Kelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            LakaLantas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            JenisPeserta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NoKartu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            AsalRujukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Eksekutif.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            COB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().contains("1")){
                ChkJasaRaharja.setSelected(true);
            }else{
                ChkJasaRaharja.setSelected(false);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().contains("2")){
                ChkBPJSTenaga.setSelected(true);
            }else{
                ChkBPJSTenaga.setSelected(false);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().contains("3")){
                ChkTaspen.setSelected(true);
            }else{
                ChkTaspen.setSelected(false);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().contains("3")){
                ChkAsa.setSelected(true);
            }else{
                ChkAsa.setSelected(false);
            }
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            /*"Kd Prop",
                "Propinsi","Kd Kab","Kabupaten","Kd Kec","Kecamatan","No.SKDP",
                "Kd DPJP","DPJP"*/
            Katarak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Suplesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            NoSEPSuplesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KdPropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            NmPropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KdKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            NmKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            KdKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            NmKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            NoSKDP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            
            Valid.SetTgl(TanggalSEP,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(TanggalRujuk,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());            
            Valid.SetTgl(TanggalKKL,tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());            
            Status.setText("AKTIF");
            
        }
    }
    
    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }

    @Test
    public void bodyWithDeleteRequest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
    
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(){
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);
        
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            URL = link+"/SEP/Delete";
            requestJson ="{\"request\":{\"t_sep\":{\"noSep\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\",\"user\":\""+user+"\"}}}";            
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_sep","no_sep",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
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
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            URL = link+"/SEP/1.1/insert";            
            requestJson ="{" +
                          "\"request\":" +
                             "{" +
                                "\"t_sep\":" +
                                   "{" +
                                    "\"noKartu\":\""+NoKartu.getText()+"\"," +
                                    "\"tglSep\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                    "\"ppkPelayanan\":\""+KdPPK.getText()+"\"," +
                                    "\"jnsPelayanan\":\""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"klsRawat\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"noMR\":\""+TNoRM.getText()+"\"," +
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
                                    "\"noTelp\": \""+NoTelp.getText()+"\","+
                                    "\"user\":\""+user+"\"" +
                                   "}" +
                             "}" +
                         "}";
            //System.out.println("JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            response = root.path("response").path("sep").path("noSep");
            if(nameNode.path("code").asText().equals("200")){
                 System.out.println("SEP : "+response.asText());
                 if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",44,new String[]{
                     response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""), 
                     NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                     JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                     NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                     LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),
                     TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                     COB.getSelectedItem().toString(),penjamin,NoTelp.getText(),Katarak.getSelectedItem().toString(),
                     tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),
                     NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                     KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText()
                 })==true){
                     Sequel.menyimpan("rujuk_masuk","?,?,?,?,?,?,?,?,?,?",10,new String[]{
                         TNoRw.getText(),NmPpkRujukan.getText(),"-",NoRujukan.getText(),"0",NmPpkRujukan.getText(),KdPenyakit.getText(),"-",
                         "-",NoBalasan.getText()
                     });
                     if(JenisPelayanan.getSelectedIndex()==1){
                        /*try {
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                            headers.add("X-Signature",api.getHmac());
                            URL = link+"/Sep/updtglplg";	
                            requestJson ="{" +
                                          "\"request\":" +
                                             "{" +
                                                "\"t_sep\":" +
                                                   "{" +
                                                    "\"noSep\":\""+response.asText()+"\"," +
                                                    "\"tglPulang\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                                    "\"user\":\""+user+"\"" +                                            
                                                   "}" +
                                             "}" +
                                         "}";
                            requestEntity = new HttpEntity(requestJson,headers);
                            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                            nameNode = root.path("metaData");
                            System.out.println("code : "+nameNode.path("code").asText());
                            System.out.println("message : "+nameNode.path("message").asText());
                            response = root.path("response");
                            if(nameNode.path("code").asText().equals("200")){*/
                                Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                                     Valid.SetTgl(TanggalSEP.getSelectedItem()+""),
                                     response.asText()
                                });
                                emptTeks();                         
                                tampil();     
                            /*}else{
                                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                            }
                        }catch (Exception ex) {
                            System.out.println("Notifikasi Bridging : "+ex);
                            if(ex.toString().contains("UnknownHostException")){
                                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                            }
                        }*/
                     } 
                     if(!prb.equals("")){
                        if(Sequel.menyimpantf("bpjs_prb","?,?","PRB",2,new String[]{response.asText(),prb})==true){
                            prb="";
                        } 
                     }
                        
                     emptTeks();
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
}

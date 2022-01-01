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
import rekammedis.RMRiwayatPerawatan;


/**
 *
 * @author perpustakaan
 */
public final class BPJSDataSEP extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabModeInternal;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0,tab=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private ApiBPJS api=new ApiBPJS();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private BPJSCekNoKartu cekViaBPJSKartu=new BPJSCekNoKartu();
    private BPJSCekReferensiDokterDPJP dokter=new BPJSCekReferensiDokterDPJP(null,false);
    private BPJSSuratKontrol skdp=new BPJSSuratKontrol(null,false);
    private BPJSSPRI skdp2=new BPJSSPRI(null,false);
    private BPJSCekReferensiPropinsi propinsi=new BPJSCekReferensiPropinsi(null,false);
    private BPJSCekReferensiKabupaten kabupaten=new BPJSCekReferensiKabupaten(null,false);
    private BPJSCekReferensiKecamatan kecamatan=new BPJSCekReferensiKecamatan(null,false);
    private String prb="",no_peserta="",link="", requestJson,URL="",query="",utc="",user="",kddokter="",tglkkl="0000-00-00",penunjang="";
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
        
        WindowUpdatePulang.setSize(525,135);
        WindowRujukan.setSize(830,172);
        WindowCariSEP.setSize(410,115);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan","No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis","Catatan", "Kode Diagnosa","Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat","Naik Kelas",
                "Pembiayaan","P.J.Naik Kelas","Laka Lantas","User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif",
                "COB","No.Telp","Katarak","Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop","Propinsi","Kd Kab","Kabupaten","Kd Kec",
                "Kecamatan","No.SKDP","Kd DPJP","DPJP","Tujuan Kunjungan","Flag Prosedur","Penunjang","Asesmen Pelayanan","Kd DPJP Layan","DPJP Layanan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataSEP.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDataSEP.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataSEP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
            TableColumn column = tbDataSEP.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(67);
            }else if(i==5){
                column.setPreferredWidth(67);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(140);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(140);
            }else if(i==21){
                column.setPreferredWidth(68);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(67);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(30);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(115);
            }else if(i==28){
                column.setPreferredWidth(75);
            }else if(i==29){
                column.setPreferredWidth(53);
            }else if(i==30){
                column.setPreferredWidth(53);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(53);
            }else if(i==33){
                column.setPreferredWidth(67);
            }else if(i==34){
                column.setPreferredWidth(140);
            }else if(i==35){
                column.setPreferredWidth(53);
            }else if(i==36){
                column.setPreferredWidth(125);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(110);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(110);
            }else if(i==41){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==42){
                column.setPreferredWidth(110);
            }else if(i==43){
                column.setPreferredWidth(110);
            }else if(i==44){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==45){
                column.setPreferredWidth(160);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(170);
            }else if(i==50){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==51){
                column.setPreferredWidth(170);
            }
        }
        tbDataSEP.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeInternal=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan","No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis","Catatan", "Kode Diagnosa","Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat","Naik Kelas",
                "Pembiayaan","P.J.Naik Kelas","Laka Lantas","User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif",
                "COB","No.Telp","Katarak","Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop","Propinsi","Kd Kab","Kabupaten","Kd Kec",
                "Kecamatan","No.SKDP","Kd DPJP","DPJP","Tujuan Kunjungan","Flag Prosedur","Penunjang","Asesmen Pelayanan","Kd DPJP Layan","DPJP Layanan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataSEPInternal.setModel(tabModeInternal);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDataSEPInternal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataSEPInternal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
            TableColumn column = tbDataSEPInternal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(67);
            }else if(i==5){
                column.setPreferredWidth(67);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(140);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(140);
            }else if(i==21){
                column.setPreferredWidth(68);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(67);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(30);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(115);
            }else if(i==28){
                column.setPreferredWidth(75);
            }else if(i==29){
                column.setPreferredWidth(53);
            }else if(i==30){
                column.setPreferredWidth(53);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(53);
            }else if(i==33){
                column.setPreferredWidth(67);
            }else if(i==34){
                column.setPreferredWidth(140);
            }else if(i==35){
                column.setPreferredWidth(53);
            }else if(i==36){
                column.setPreferredWidth(125);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(110);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(110);
            }else if(i==41){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==42){
                column.setPreferredWidth(110);
            }else if(i==43){
                column.setPreferredWidth(110);
            }else if(i==44){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==45){
                column.setPreferredWidth(160);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(170);
            }else if(i==50){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==51){
                column.setPreferredWidth(170);
            }
        }
        tbDataSEPInternal.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCariInternal.setDocument(new batasInput((byte)100).getKata(TCariInternal));
        NoRujukan.setDocument(new batasInput((byte)40).getKata(NoRujukan));
        NoSKDP.setDocument(new batasInput((byte)40).getKata(NoSKDP));
        NoSEPSuplesi.setDocument(new batasInput((byte)40).getKata(NoSEPSuplesi));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
        PenanggungJawab.setDocument(new batasInput((byte)100).getKata(PenanggungJawab));
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
            
            TCariInternal.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariInternal.getText().length()>2){
                        tampilInternal();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariInternal.getText().length()>2){
                        tampilInternal();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariInternal.getText().length()>2){
                        tampilInternal();
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
                    if(pilihan==1){
                        KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                        if(JenisPelayanan.getSelectedIndex()==1){ 
                            KdDPJPLayanan.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            NmDPJPLayanan.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                        }
                        KdDPJP.requestFocus();  
                    }else{
                        KdDPJPLayanan.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        NmDPJPLayanan.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                        KdDPJPLayanan.requestFocus();  
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
                    NoSKDP.setText(skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(),9).toString());
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
        
        skdp2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(skdp2.getTable().getSelectedRow()!= -1){                   
                    NoSKDP.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(),8).toString());
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
        
        skdp2.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    skdp2.dispose();
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
            link=koneksiDB.URLAPIBPJS();
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
        ppDaftarRujukan = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppSuratPRI = new javax.swing.JMenuItem();
        ppProgramPRB = new javax.swing.JMenuItem();
        ppSuplesiJasaRaharja = new javax.swing.JMenuItem();
        ppDataIndukKecelakaan = new javax.swing.JMenuItem();
        ppDataSEPInternal = new javax.swing.JMenuItem();
        ppRiwayatPerawatan = new javax.swing.JMenuItem();
        ppSepRujukSama = new javax.swing.JMenuItem();
        ppSepRujukBeda = new javax.swing.JMenuItem();
        WindowUpdatePulang = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        jLabel46 = new widget.Label();
        StatusPulang = new widget.ComboBox();
        jLabel47 = new widget.Label();
        NoSuratKematian = new widget.TextBox();
        jLabel48 = new widget.Label();
        TanggalKematian = new widget.Tanggal();
        jLabel49 = new widget.Label();
        NoLPManual = new widget.TextBox();
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
        jLabel50 = new widget.Label();
        TanggalKunjungRujukan = new widget.Tanggal();
        Popup1 = new javax.swing.JPopupMenu();
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
        ppPengajuan2 = new javax.swing.JMenuItem();
        ppPengajuan3 = new javax.swing.JMenuItem();
        ppAmbilSep = new javax.swing.JMenuItem();
        ppStatusFinger = new javax.swing.JMenuItem();
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
        jLabel29 = new widget.Label();
        NoTelp = new widget.TextBox();
        Katarak = new widget.ComboBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        TanggalKKL = new widget.Tanggal();
        jLabel39 = new widget.Label();
        NoSKDP = new widget.TextBox();
        btnSPRI = new widget.Button();
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
        btnSKDP = new widget.Button();
        NaikKelas = new widget.ComboBox();
        Pembiayaan = new widget.ComboBox();
        jLabel17 = new widget.Label();
        PenanggungJawab = new widget.TextBox();
        jLabel42 = new widget.Label();
        TujuanKunjungan = new widget.ComboBox();
        FlagProsedur = new widget.ComboBox();
        jLabel28 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        Penunjang = new widget.ComboBox();
        jLabel45 = new widget.Label();
        AsesmenPoli = new widget.ComboBox();
        LabelPoli6 = new widget.Label();
        KdDPJPLayanan = new widget.TextBox();
        NmDPJPLayanan = new widget.TextBox();
        btnDPJPLayanan = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDataSEP = new widget.Table();
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
        internalFrame8 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbDataSEPInternal = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel51 = new widget.Label();
        DTPCariInternal = new widget.Tanggal();
        jLabel52 = new widget.Label();
        DTPCariInternal2 = new widget.Tanggal();
        jLabel53 = new widget.Label();
        TCariInternal = new widget.TextBox();
        BtnCariInternal = new widget.Button();
        jLabel54 = new widget.Label();
        LCount1 = new widget.Label();
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
        ppSEP.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP.setText("Print SEP Model 1");
        ppSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP.setName("ppSEP"); // NOI18N
        ppSEP.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEPBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP);

        ppSEP1.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP1.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP1.setText("Print SEP Model 2");
        ppSEP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP1.setName("ppSEP1"); // NOI18N
        ppSEP1.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP1BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP1);

        ppSEP2.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP2.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP2.setText("Print SEP Model 3");
        ppSEP2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP2.setName("ppSEP2"); // NOI18N
        ppSEP2.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP2BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP2);

        ppSEP3.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP3.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP3.setText("Print SEP Model 4");
        ppSEP3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP3.setName("ppSEP3"); // NOI18N
        ppSEP3.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP3BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP3);

        ppSEP4.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP4.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP4.setText("Print SEP Model 1 PDF");
        ppSEP4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP4.setName("ppSEP4"); // NOI18N
        ppSEP4.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP4BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP4);

        ppSEP5.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP5.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP5.setText("Print SEP Model 2 PDF");
        ppSEP5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP5.setName("ppSEP5"); // NOI18N
        ppSEP5.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP5BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP5);

        ppSEP6.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP6.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP6.setText("Print SEP Model 3 PDF");
        ppSEP6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP6.setName("ppSEP6"); // NOI18N
        ppSEP6.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP6BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP6);

        ppSEP7.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP7.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP7.setText("Print SEP Model 4 PDF");
        ppSEP7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP7.setName("ppSEP7"); // NOI18N
        ppSEP7.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP7BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP7);

        ppPulang.setBackground(new java.awt.Color(255, 255, 254));
        ppPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPulang.setForeground(new java.awt.Color(50, 50, 50));
        ppPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPulang.setText("Update Tanggal Pulang");
        ppPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPulang.setName("ppPulang"); // NOI18N
        ppPulang.setPreferredSize(new java.awt.Dimension(300, 25));
        ppPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPulangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPulang);

        ppDetailSEPPeserta.setBackground(new java.awt.Color(255, 255, 254));
        ppDetailSEPPeserta.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDetailSEPPeserta.setForeground(new java.awt.Color(50, 50, 50));
        ppDetailSEPPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDetailSEPPeserta.setText("Detail SEP Peserta");
        ppDetailSEPPeserta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDetailSEPPeserta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDetailSEPPeserta.setName("ppDetailSEPPeserta"); // NOI18N
        ppDetailSEPPeserta.setPreferredSize(new java.awt.Dimension(300, 25));
        ppDetailSEPPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDetailSEPPesertaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDetailSEPPeserta);

        ppRujukan.setBackground(new java.awt.Color(255, 255, 254));
        ppRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRujukan.setForeground(new java.awt.Color(50, 50, 50));
        ppRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukan.setText("Buat Rujukan Keluar");
        ppRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRujukan.setName("ppRujukan"); // NOI18N
        ppRujukan.setPreferredSize(new java.awt.Dimension(300, 25));
        ppRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRujukan);

        ppDaftarRujukan.setBackground(new java.awt.Color(255, 255, 254));
        ppDaftarRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDaftarRujukan.setForeground(new java.awt.Color(50, 50, 50));
        ppDaftarRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDaftarRujukan.setText("Daftar Rujukan Keluar");
        ppDaftarRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDaftarRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDaftarRujukan.setName("ppDaftarRujukan"); // NOI18N
        ppDaftarRujukan.setPreferredSize(new java.awt.Dimension(300, 25));
        ppDaftarRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDaftarRujukanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDaftarRujukan);

        ppSuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol.setText("Rencana Kontrol");
        ppSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol.setName("ppSuratKontrol"); // NOI18N
        ppSuratKontrol.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrolBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSuratKontrol);

        ppSuratPRI.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPRI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPRI.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPRI.setText("Perintah Rawat Inap");
        ppSuratPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPRI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPRI.setName("ppSuratPRI"); // NOI18N
        ppSuratPRI.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSuratPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPRIBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSuratPRI);

        ppProgramPRB.setBackground(new java.awt.Color(255, 255, 254));
        ppProgramPRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppProgramPRB.setForeground(new java.awt.Color(50, 50, 50));
        ppProgramPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppProgramPRB.setText("Program PRB BPJS");
        ppProgramPRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppProgramPRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppProgramPRB.setName("ppProgramPRB"); // NOI18N
        ppProgramPRB.setPreferredSize(new java.awt.Dimension(300, 25));
        ppProgramPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppProgramPRBActionPerformed(evt);
            }
        });
        Popup.add(ppProgramPRB);

        ppSuplesiJasaRaharja.setBackground(new java.awt.Color(255, 255, 254));
        ppSuplesiJasaRaharja.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuplesiJasaRaharja.setForeground(new java.awt.Color(50, 50, 50));
        ppSuplesiJasaRaharja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuplesiJasaRaharja.setText("Suplesi Jasa Raharja");
        ppSuplesiJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuplesiJasaRaharja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuplesiJasaRaharja.setName("ppSuplesiJasaRaharja"); // NOI18N
        ppSuplesiJasaRaharja.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSuplesiJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuplesiJasaRaharjaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSuplesiJasaRaharja);

        ppDataIndukKecelakaan.setBackground(new java.awt.Color(255, 255, 254));
        ppDataIndukKecelakaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataIndukKecelakaan.setForeground(new java.awt.Color(50, 50, 50));
        ppDataIndukKecelakaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataIndukKecelakaan.setText("Data Induk Kecelakaan");
        ppDataIndukKecelakaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataIndukKecelakaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataIndukKecelakaan.setName("ppDataIndukKecelakaan"); // NOI18N
        ppDataIndukKecelakaan.setPreferredSize(new java.awt.Dimension(300, 25));
        ppDataIndukKecelakaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataIndukKecelakaanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDataIndukKecelakaan);

        ppDataSEPInternal.setBackground(new java.awt.Color(255, 255, 254));
        ppDataSEPInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataSEPInternal.setForeground(new java.awt.Color(50, 50, 50));
        ppDataSEPInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataSEPInternal.setText("Data SEP Internal");
        ppDataSEPInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataSEPInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataSEPInternal.setName("ppDataSEPInternal"); // NOI18N
        ppDataSEPInternal.setPreferredSize(new java.awt.Dimension(300, 25));
        ppDataSEPInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataSEPInternalBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDataSEPInternal);

        ppRiwayatPerawatan.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayatPerawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayatPerawatan.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayatPerawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayatPerawatan.setText("Riwayat Perawatan");
        ppRiwayatPerawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayatPerawatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayatPerawatan.setName("ppRiwayatPerawatan"); // NOI18N
        ppRiwayatPerawatan.setPreferredSize(new java.awt.Dimension(300, 25));
        ppRiwayatPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatPerawatanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRiwayatPerawatan);

        ppSepRujukSama.setBackground(new java.awt.Color(255, 255, 254));
        ppSepRujukSama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSepRujukSama.setForeground(new java.awt.Color(50, 50, 50));
        ppSepRujukSama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSepRujukSama.setText("Tampilkan Tanggal SEP & Tanggal Rujuk Sama");
        ppSepRujukSama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSepRujukSama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSepRujukSama.setName("ppSepRujukSama"); // NOI18N
        ppSepRujukSama.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSepRujukSama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSepRujukSamaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSepRujukSama);

        ppSepRujukBeda.setBackground(new java.awt.Color(255, 255, 254));
        ppSepRujukBeda.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSepRujukBeda.setForeground(new java.awt.Color(50, 50, 50));
        ppSepRujukBeda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSepRujukBeda.setText("Tampilkan Tanggal SEP & Tanggal Rujuk Beda");
        ppSepRujukBeda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSepRujukBeda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSepRujukBeda.setName("ppSepRujukBeda"); // NOI18N
        ppSepRujukBeda.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSepRujukBeda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSepRujukBedaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSepRujukBeda);

        WindowUpdatePulang.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUpdatePulang.setName("WindowUpdatePulang"); // NOI18N
        WindowUpdatePulang.setUndecorated(true);
        WindowUpdatePulang.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Tanggal Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnCloseIn4.setBounds(405, 92, 100, 30);

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
        BtnSimpan4.setBounds(300, 92, 100, 30);

        jLabel26.setText("Tgl.Pulang :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 70, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021 22:24:58" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(80, 32, 130, 23);

        jLabel46.setText("Stts Pulang :");
        jLabel46.setName("jLabel46"); // NOI18N
        internalFrame5.add(jLabel46);
        jLabel46.setBounds(230, 32, 81, 23);

        StatusPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Atas Persetujuan Dokter", "3. Atas Permintaan Sendiri", "4. Meninggal", "5. Lain-lain" }));
        StatusPulang.setName("StatusPulang"); // NOI18N
        StatusPulang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                StatusPulangItemStateChanged(evt);
            }
        });
        StatusPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPulangKeyPressed(evt);
            }
        });
        internalFrame5.add(StatusPulang);
        StatusPulang.setBounds(315, 32, 190, 23);

        jLabel47.setText("Nomor Surat Kematian :");
        jLabel47.setName("jLabel47"); // NOI18N
        jLabel47.setPreferredSize(new java.awt.Dimension(68, 23));
        internalFrame5.add(jLabel47);
        jLabel47.setBounds(0, 62, 135, 23);

        NoSuratKematian.setEditable(false);
        NoSuratKematian.setHighlighter(null);
        NoSuratKematian.setName("NoSuratKematian"); // NOI18N
        NoSuratKematian.setPreferredSize(new java.awt.Dimension(130, 23));
        internalFrame5.add(NoSuratKematian);
        NoSuratKematian.setBounds(139, 62, 145, 23);

        jLabel48.setText("Tanggal Kematian :");
        jLabel48.setName("jLabel48"); // NOI18N
        internalFrame5.add(jLabel48);
        jLabel48.setBounds(291, 62, 120, 23);

        TanggalKematian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
        TanggalKematian.setDisplayFormat("dd-MM-yyyy");
        TanggalKematian.setEnabled(false);
        TanggalKematian.setName("TanggalKematian"); // NOI18N
        TanggalKematian.setOpaque(false);
        TanggalKematian.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalKematian);
        TanggalKematian.setBounds(415, 62, 90, 23);

        jLabel49.setText("Nomor LP Manual :");
        jLabel49.setName("jLabel49"); // NOI18N
        jLabel49.setPreferredSize(new java.awt.Dimension(68, 23));
        internalFrame5.add(jLabel49);
        jLabel49.setBounds(0, 92, 109, 23);

        NoLPManual.setEditable(false);
        NoLPManual.setHighlighter(null);
        NoLPManual.setName("NoLPManual"); // NOI18N
        NoLPManual.setPreferredSize(new java.awt.Dimension(130, 23));
        internalFrame5.add(NoLPManual);
        NoLPManual.setBounds(113, 92, 171, 23);

        WindowUpdatePulang.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        WindowRujukan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRujukan.setName("WindowRujukan"); // NOI18N
        WindowRujukan.setUndecorated(true);
        WindowRujukan.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Buat Rujukan Keluar VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnCloseIn5.setBounds(712, 125, 100, 30);

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
        TanggalRujukKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
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
        JenisPelayanan1.setBounds(514, 55, 123, 23);

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
        NmPoli1.setBounds(581, 85, 200, 23);

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
        btnPoli1.setBounds(784, 85, 28, 23);

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
        Catatan1.setBounds(514, 25, 298, 23);

        jLabel50.setText("R.Kunjungan :");
        jLabel50.setName("jLabel50"); // NOI18N
        internalFrame6.add(jLabel50);
        jLabel50.setBounds(638, 55, 80, 23);

        TanggalKunjungRujukan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKunjungRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
        TanggalKunjungRujukan.setDisplayFormat("dd-MM-yyyy");
        TanggalKunjungRujukan.setName("TanggalKunjungRujukan"); // NOI18N
        TanggalKunjungRujukan.setOpaque(false);
        TanggalKunjungRujukan.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame6.add(TanggalKunjungRujukan);
        TanggalKunjungRujukan.setBounds(722, 55, 90, 23);

        WindowRujukan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        Popup1.setName("Popup1"); // NOI18N

        ppPengajuan.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan.setText("Pengajuan SEP Backdate");
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
        ppPengajuan1.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan1.setText("Aproval SEP Backdate");
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

        ppPengajuan2.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan2.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan2.setText("Aproval SEP Finger");
        ppPengajuan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan2.setName("ppPengajuan2"); // NOI18N
        ppPengajuan2.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan2BtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppPengajuan2);

        ppPengajuan3.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan3.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan3.setText("Pengajuan SEP Finger");
        ppPengajuan3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan3.setName("ppPengajuan3"); // NOI18N
        ppPengajuan3.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan3BtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppPengajuan3);

        ppAmbilSep.setBackground(new java.awt.Color(255, 255, 254));
        ppAmbilSep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppAmbilSep.setForeground(new java.awt.Color(50, 50, 50));
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

        ppStatusFinger.setBackground(new java.awt.Color(255, 255, 254));
        ppStatusFinger.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStatusFinger.setForeground(new java.awt.Color(50, 50, 50));
        ppStatusFinger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStatusFinger.setText("Status Finger");
        ppStatusFinger.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStatusFinger.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStatusFinger.setName("ppStatusFinger"); // NOI18N
        ppStatusFinger.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStatusFinger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStatusFingerBtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppStatusFinger);

        WindowCariSEP.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariSEP.setName("WindowCariSEP"); // NOI18N
        WindowCariSEP.setUndecorated(true);
        WindowCariSEP.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ambil SEP di VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
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
        FormInput.setPreferredSize(new java.awt.Dimension(745, 467));
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
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
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
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
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
        jLabel14.setBounds(0, 432, 90, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormInput.add(Catatan);
        Catatan.setBounds(93, 432, 257, 23);

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
        LabelKelas.setBounds(207, 312, 40, 23);

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

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Bukan KLL", "1. KLL Bukan KK", "2. KLL dan KK", "3. KK" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LakaLantasItemStateChanged(evt);
            }
        });
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormInput.add(LakaLantas);
        LakaLantas.setBounds(470, 132, 127, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 42, 90, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(93, 42, 80, 23);

        jLabel18.setText("J.K.:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(414, 42, 40, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(457, 42, 40, 23);

        jLabel24.setText("Peserta :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(178, 42, 60, 23);

        JenisPeserta.setEditable(false);
        JenisPeserta.setBackground(new java.awt.Color(245, 250, 240));
        JenisPeserta.setHighlighter(null);
        JenisPeserta.setName("JenisPeserta"); // NOI18N
        FormInput.add(JenisPeserta);
        JenisPeserta.setBounds(241, 42, 173, 23);

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
        jLabel27.setBounds(497, 42, 90, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(590, 42, 137, 23);

        jLabel15.setText("Eksekutif :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 402, 90, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormInput.add(Eksekutif);
        Eksekutif.setBounds(93, 402, 100, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormInput.add(LabelKelas1);
        LabelKelas1.setBounds(207, 402, 40, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormInput.add(COB);
        COB.setBounds(250, 402, 100, 23);

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

        jLabel38.setText("Tgl :");
        jLabel38.setName("jLabel38"); // NOI18N
        jLabel38.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel38);
        jLabel38.setBounds(594, 132, 40, 23);

        TanggalKKL.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKKL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
        TanggalKKL.setDisplayFormat("dd-MM-yyyy");
        TanggalKKL.setEnabled(false);
        TanggalKKL.setName("TanggalKKL"); // NOI18N
        TanggalKKL.setOpaque(false);
        TanggalKKL.setPreferredSize(new java.awt.Dimension(90, 23));
        TanggalKKL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKKLKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKKL);
        TanggalKKL.setBounds(637, 132, 90, 23);

        jLabel39.setText("No.SKDP/SPRI :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 132, 90, 23);

        NoSKDP.setHighlighter(null);
        NoSKDP.setName("NoSKDP"); // NOI18N
        NoSKDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSKDPKeyPressed(evt);
            }
        });
        FormInput.add(NoSKDP);
        NoSKDP.setBounds(93, 132, 227, 23);

        btnSPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSPRI.setMnemonic('X');
        btnSPRI.setToolTipText("Alt+X");
        btnSPRI.setName("btnSPRI"); // NOI18N
        btnSPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSPRIActionPerformed(evt);
            }
        });
        FormInput.add(btnSPRI);
        btnSPRI.setBounds(352, 132, 28, 23);

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
        jLabel36.setBounds(380, 162, 87, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(470, 162, 257, 23);

        jLabel40.setText("Suplesi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(380, 192, 87, 23);

        Suplesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Suplesi.setName("Suplesi"); // NOI18N
        Suplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuplesiKeyPressed(evt);
            }
        });
        FormInput.add(Suplesi);
        Suplesi.setBounds(470, 192, 90, 23);

        NoSEPSuplesi.setHighlighter(null);
        NoSEPSuplesi.setName("NoSEPSuplesi"); // NOI18N
        NoSEPSuplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPSuplesiKeyPressed(evt);
            }
        });
        FormInput.add(NoSEPSuplesi);
        NoSEPSuplesi.setBounds(632, 192, 95, 23);

        jLabel41.setText("SEP Suplesi :");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel41);
        jLabel41.setBounds(562, 192, 68, 23);

        LabelPoli3.setText("Propinsi KLL :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(380, 222, 87, 23);

        KdPropinsi.setEditable(false);
        KdPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        KdPropinsi.setHighlighter(null);
        KdPropinsi.setName("KdPropinsi"); // NOI18N
        FormInput.add(KdPropinsi);
        KdPropinsi.setBounds(470, 222, 55, 23);

        NmPropinsi.setEditable(false);
        NmPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        NmPropinsi.setHighlighter(null);
        NmPropinsi.setName("NmPropinsi"); // NOI18N
        FormInput.add(NmPropinsi);
        NmPropinsi.setBounds(527, 222, 170, 23);

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
        btnPropinsi.setBounds(699, 222, 28, 23);

        LabelPoli4.setText("Kabupaten KLL :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(380, 252, 87, 23);

        KdKabupaten.setEditable(false);
        KdKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        KdKabupaten.setHighlighter(null);
        KdKabupaten.setName("KdKabupaten"); // NOI18N
        FormInput.add(KdKabupaten);
        KdKabupaten.setBounds(470, 252, 55, 23);

        NmKabupaten.setEditable(false);
        NmKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        NmKabupaten.setHighlighter(null);
        NmKabupaten.setName("NmKabupaten"); // NOI18N
        FormInput.add(NmKabupaten);
        NmKabupaten.setBounds(527, 252, 170, 23);

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
        btnKabupaten.setBounds(699, 252, 28, 23);

        LabelPoli5.setText("Kecamatan KLL :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(380, 282, 87, 23);

        KdKecamatan.setEditable(false);
        KdKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        KdKecamatan.setHighlighter(null);
        KdKecamatan.setName("KdKecamatan"); // NOI18N
        FormInput.add(KdKecamatan);
        KdKecamatan.setBounds(470, 282, 55, 23);

        NmKecamatan.setEditable(false);
        NmKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        NmKecamatan.setHighlighter(null);
        NmKecamatan.setName("NmKecamatan"); // NOI18N
        FormInput.add(NmKecamatan);
        NmKecamatan.setBounds(527, 282, 170, 23);

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
        btnKecamatan.setBounds(699, 282, 28, 23);

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

        btnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSKDP.setMnemonic('X');
        btnSKDP.setToolTipText("Alt+X");
        btnSKDP.setName("btnSKDP"); // NOI18N
        btnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSKDPActionPerformed(evt);
            }
        });
        FormInput.add(btnSKDP);
        btnSKDP.setBounds(322, 132, 28, 23);

        NaikKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. VVIP", "2. VIP", "3. Kelas I", "4. Kelas II", "5. Kelas III", "6. ICCU", "7. ICU" }));
        NaikKelas.setEnabled(false);
        NaikKelas.setName("NaikKelas"); // NOI18N
        NaikKelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NaikKelasItemStateChanged(evt);
            }
        });
        NaikKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NaikKelasKeyPressed(evt);
            }
        });
        FormInput.add(NaikKelas);
        NaikKelas.setBounds(93, 342, 100, 23);

        Pembiayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "1. Pribadi", "2. Pemberi Kerja", "3. Asuransi Lain" }));
        Pembiayaan.setEnabled(false);
        Pembiayaan.setName("Pembiayaan"); // NOI18N
        Pembiayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembiayaanKeyPressed(evt);
            }
        });
        FormInput.add(Pembiayaan);
        Pembiayaan.setBounds(197, 342, 154, 23);

        jLabel17.setText("P.J. Naik Kelas :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 372, 90, 23);

        PenanggungJawab.setEditable(false);
        PenanggungJawab.setBackground(new java.awt.Color(245, 250, 240));
        PenanggungJawab.setHighlighter(null);
        PenanggungJawab.setName("PenanggungJawab"); // NOI18N
        FormInput.add(PenanggungJawab);
        PenanggungJawab.setBounds(93, 372, 258, 23);

        jLabel42.setText("Tujuan Kunjungan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(357, 312, 110, 23);

        TujuanKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Normal", "1. Prosedur", "2. Konsul Dokter" }));
        TujuanKunjungan.setName("TujuanKunjungan"); // NOI18N
        TujuanKunjungan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TujuanKunjunganItemStateChanged(evt);
            }
        });
        TujuanKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(TujuanKunjungan);
        TujuanKunjungan.setBounds(470, 312, 257, 23);

        FlagProsedur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Prosedur Tidak Berkelanjutan", "1. Prosedur dan Terapi Berkelanjutan" }));
        FlagProsedur.setEnabled(false);
        FlagProsedur.setName("FlagProsedur"); // NOI18N
        FlagProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FlagProsedurKeyPressed(evt);
            }
        });
        FormInput.add(FlagProsedur);
        FlagProsedur.setBounds(470, 342, 257, 23);

        jLabel28.setText("Naik Kelas :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 342, 90, 23);

        jLabel43.setText("Flag Prosedur :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(357, 342, 110, 23);

        jLabel44.setText("Penunjang :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(357, 372, 110, 23);

        Penunjang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Radioterapi", "2. Kemoterapi", "3. Rehabilitasi Medik", "4. Rehabilitasi Psikososial", "5. Transfusi Darah", "6. Pelayanan Gigi", "7. Laboratorium", "8. USG", "9. Farmasi", "10. Lain-Lain", "11. MRI", "12. HEMODIALISA" }));
        Penunjang.setEnabled(false);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        FormInput.add(Penunjang);
        Penunjang.setBounds(470, 372, 257, 23);

        jLabel45.setText("Asesmen Pelayanan :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(357, 402, 110, 23);

        AsesmenPoli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Poli spesialis tidak tersedia pada hari sebelumnya", "2. Jam Poli telah berakhir pada hari sebelumnya", "3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya", "4. Atas Instruksi RS", "5. Tujuan Kontrol" }));
        AsesmenPoli.setName("AsesmenPoli"); // NOI18N
        AsesmenPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenPoliKeyPressed(evt);
            }
        });
        FormInput.add(AsesmenPoli);
        AsesmenPoli.setBounds(470, 402, 257, 23);

        LabelPoli6.setText("DPJP Layanan :");
        LabelPoli6.setName("LabelPoli6"); // NOI18N
        FormInput.add(LabelPoli6);
        LabelPoli6.setBounds(357, 430, 110, 23);

        KdDPJPLayanan.setEditable(false);
        KdDPJPLayanan.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJPLayanan.setHighlighter(null);
        KdDPJPLayanan.setName("KdDPJPLayanan"); // NOI18N
        FormInput.add(KdDPJPLayanan);
        KdDPJPLayanan.setBounds(470, 430, 55, 23);

        NmDPJPLayanan.setEditable(false);
        NmDPJPLayanan.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJPLayanan.setHighlighter(null);
        NmDPJPLayanan.setName("NmDPJPLayanan"); // NOI18N
        FormInput.add(NmDPJPLayanan);
        NmDPJPLayanan.setBounds(527, 430, 170, 23);

        btnDPJPLayanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJPLayanan.setMnemonic('X');
        btnDPJPLayanan.setToolTipText("Alt+X");
        btnDPJPLayanan.setName("btnDPJPLayanan"); // NOI18N
        btnDPJPLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPLayananActionPerformed(evt);
            }
        });
        btnDPJPLayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJPLayananKeyPressed(evt);
            }
        });
        FormInput.add(btnDPJPLayanan);
        btnDPJPLayanan.setBounds(699, 430, 28, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input SEP", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataSEP.setAutoCreateRowSorter(true);
        tbDataSEP.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataSEP.setComponentPopupMenu(Popup);
        tbDataSEP.setName("tbDataSEP"); // NOI18N
        tbDataSEP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSEPMouseClicked(evt);
            }
        });
        tbDataSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataSEPKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataSEP);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
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

        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDataSEPInternal.setAutoCreateRowSorter(true);
        tbDataSEPInternal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataSEPInternal.setComponentPopupMenu(Popup);
        tbDataSEPInternal.setName("tbDataSEPInternal"); // NOI18N
        tbDataSEPInternal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSEPInternalMouseClicked(evt);
            }
        });
        tbDataSEPInternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataSEPInternalKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDataSEPInternal);

        internalFrame8.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel51.setText("Tgl. SEP :");
        jLabel51.setName("jLabel51"); // NOI18N
        jLabel51.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel51);

        DTPCariInternal.setForeground(new java.awt.Color(50, 70, 50));
        DTPCariInternal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
        DTPCariInternal.setDisplayFormat("dd-MM-yyyy");
        DTPCariInternal.setName("DTPCariInternal"); // NOI18N
        DTPCariInternal.setOpaque(false);
        DTPCariInternal.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCariInternal);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("s.d.");
        jLabel52.setName("jLabel52"); // NOI18N
        jLabel52.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel52);

        DTPCariInternal2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCariInternal2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-12-2021" }));
        DTPCariInternal2.setDisplayFormat("dd-MM-yyyy");
        DTPCariInternal2.setName("DTPCariInternal2"); // NOI18N
        DTPCariInternal2.setOpaque(false);
        DTPCariInternal2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCariInternal2);

        jLabel53.setText("Key Word :");
        jLabel53.setName("jLabel53"); // NOI18N
        jLabel53.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel53);

        TCariInternal.setName("TCariInternal"); // NOI18N
        TCariInternal.setPreferredSize(new java.awt.Dimension(205, 23));
        TCariInternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariInternalKeyPressed(evt);
            }
        });
        panelGlass10.add(TCariInternal);

        BtnCariInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariInternal.setMnemonic('3');
        BtnCariInternal.setToolTipText("Alt+3");
        BtnCariInternal.setName("BtnCariInternal"); // NOI18N
        BtnCariInternal.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariInternalActionPerformed(evt);
            }
        });
        BtnCariInternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariInternalKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnCariInternal);

        jLabel54.setText("Record :");
        jLabel54.setName("jLabel54"); // NOI18N
        jLabel54.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel54);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount1);

        internalFrame8.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data SEP Internal", internalFrame8);

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
        }else{  
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
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                try {
                    bodyWithDeleteRequest();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            } 
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                try {
                    bodyWithDeleteRequestInternal();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            } 
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
        if(tab==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "Pasien");
                }else if (NoKartu.getText().trim().equals("")) {
                    Valid.textKosong(NoKartu, "Nomor Kartu");
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
                }/*else if (NoSKDP.getText().trim().equals("")) {
                    Valid.textKosong(NoSKDP, "No.SKDP");
                }*/else{
                    try {
                        tglkkl="0000-00-00";
                        if(LakaLantas.getSelectedIndex()==1){
                            tglkkl=Valid.SetTgl(TanggalKKL.getSelectedItem()+"");
                        }

                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("X-Timestamp",utc);
                        headers.add("X-Signature",api.getHmac(utc));
                        headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                        URL = link+"/SEP/2.0/update";	
                        requestJson ="{" +
                                      "\"request\":{" +
                                            "\"t_sep\":{" +
                                                "\"noSep\":\""+tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()+"\"," +
                                                "\"klsRawat\":{"+
                                                    "\"klsRawatHak\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\","+
                                                    "\"klsRawatNaik\":\""+(NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):"")+"\","+
                                                    "\"pembiayaan\":\""+(Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):"")+"\","+
                                                    "\"penanggungJawab\":\""+(PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText())+"\""+
                                                "},"+
                                                "\"noMR\":\""+TNoRM.getText()+"\"," +
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
                                                        "\"tglKejadian\": \""+tglkkl.replaceAll("0000-00-00","")+"\"," +
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
                                                "\"dpjpLayan\": \""+(KdDPJPLayanan.getText().equals("")?"":KdDPJPLayanan.getText())+"\","+
                                                "\"noTelp\":\""+NoTelp.getText()+"\","+
                                                "\"user\":\""+user+"\"" +
                                               "}" +
                                         "}" +
                                     "}";
                        System.out.println("JSON : "+requestJson);
                        requestEntity = new HttpEntity(requestJson,headers);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                        nameNode = root.path("metaData");
                        System.out.println("code : "+nameNode.path("code").asText());
                        System.out.println("message : "+nameNode.path("message").asText());
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                        if(nameNode.path("code").asText().equals("200")){
                            Sequel.mengedit("bridging_sep",
                                 "no_sep=?","no_rawat=?,catatan=?,diagawal=?,nmdiagnosaawal=?,kdpolitujuan=?,nmpolitujuan=?,klsrawat=?,klsnaik=?,pembiayaan=?,"+
                                 "pjnaikkelas=?,lakalantas=?,user=?,nomr=?,nama_pasien=?,tanggal_lahir=?,peserta=?,jkel=?,eksekutif=?,cob=?,notelep=?,katarak=?,tglkkl=?,"+
                                 "keterangankkl=?,suplesi=?,no_sep_suplesi=?,kdprop=?,nmprop=?,kdkab=?,nmkab=?,kdkec=?,nmkec=?,kddpjplayanan=?,nmdpjplayanan=?",34,new String[]{
                                 TNoRw.getText(),Catatan.getText(),KdPenyakit.getText(),NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(),Kelas.getSelectedItem().toString().substring(0,1),
                                 (NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):""),(Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):""),
                                 (PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText()),LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),TglLahir.getText(),
                                 JenisPeserta.getText(),JK.getText(),Eksekutif.getSelectedItem().toString().substring(0,1),COB.getSelectedItem().toString().substring(0,1),NoTelp.getText(),
                                 Katarak.getSelectedItem().toString().substring(0,1),tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString().substring(0,1),NoSEPSuplesi.getText(),
                                 KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),KdKecamatan.getText(),NmKecamatan.getText(),KdDPJPLayanan.getText(),
                                 NmDPJPLayanan.getText(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()
                             });
                             Sequel.mengedit("rujuk_masuk","no_rawat=?","no_rawat=?,perujuk=?,no_rujuk=?",4,new String[]{
                                 TNoRw.getText(),NmPpkRujukan.getText(),NoRujukan.getText(),
                                 tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString()
                             });
                             emptTeks(); 
                             TabRawat.setSelectedIndex(1);
                             tampil();          
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
        }else if(tab==2){
            JOptionPane.showMessageDialog(null,"SEP Internal tidak bisa diubah...!!!");
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
        if(TabRawat.getSelectedIndex()==1){
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeInternal.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCariInternal.requestFocus();
            }else if(tabModeInternal.getRowCount()!=0){            
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
                param.put("parameter","%"+TCariInternal.getText().trim()+"%");
                Valid.MyReport("rptBridgingDaftarInternal.jasper","report","::[ Data Bridging SEP Internal ]::",param);
            }
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
        query="";
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
        query="";
        if(TabRawat.getSelectedIndex()==1){
            TCari.setText("");
            tampil();
        }else if(TabRawat.getSelectedIndex()==2){
            TCariInternal.setText("");
            tampilInternal();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbDataSEPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSEPMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
               getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                i=tbDataSEP.getSelectedColumn();
                if(i==0){
                    TabRawat.setSelectedIndex(0);
                }else if(i==1){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==3){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbDataSEPMouseClicked

    private void tbDataSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataSEPKeyPressed
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
                i=tbDataSEP.getSelectedColumn();
                if(i==0){
                    TabRawat.setSelectedIndex(0);
                }else if(i==1){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==3){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }
                
        }
}//GEN-LAST:event_tbDataSEPKeyPressed

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
            System.out.println("No.Peserta : "+no_peserta);
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
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReport("rptBridgingSEPInternal.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReport("rptBridgingSEPInternal2.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            } 
        }       
    }//GEN-LAST:event_ppSEPBtnPrintActionPerformed

    private void ppPulangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPulangBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                if(LakaLantas.getSelectedIndex()==1){
                    NoLPManual.setEditable(true);
                }else{
                    NoLPManual.setEditable(false);
                }
                WindowUpdatePulang.setLocationRelativeTo(internalFrame1);
                WindowUpdatePulang.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau diupdate tanggal pulangnya...!!!!");
                BtnBatal.requestFocus();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                if(LakaLantas.getSelectedIndex()==1){
                    NoLPManual.setEditable(true);
                }else{
                    NoLPManual.setEditable(false);
                }
                WindowUpdatePulang.setLocationRelativeTo(internalFrame1);
                WindowUpdatePulang.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau diupdate tanggal pulangnya...!!!!");
                BtnBatal.requestFocus();
            }
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
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/SEP/2.0/updtglplg";	
                requestJson ="{" +
                              "\"request\":" +
                                 "{" +
                                    "\"t_sep\":" +
                                       "{" +
                                        "\"noSep\":\""+tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()+"\"," +
                                        "\"statusPulang\":\""+StatusPulang.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"noSuratMeninggal\":\""+(NoSuratKematian.getText().equals("")?"":NoSuratKematian.getText())+"\"," +
                                        "\"tglMeninggal\":\""+(TanggalKematian.isEnabled()==false?"":Valid.SetTgl(TanggalKematian.getSelectedItem()+""))+"\"," +
                                        "\"tglPulang\":\""+Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+"\"," +
                                        "\"noLPManual\":\""+(NoLPManual.getText().equals("")?"":NoLPManual.getText())+"\"," +
                                        "\"user\":\""+user+"\"" +                                            
                                       "}" +
                                 "}" +
                             "}";
                System.out.println("JSON : "+requestJson);
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("code").asText().equals("200")){
                    Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                         Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+" "+TanggalPulang.getSelectedItem().toString().substring(11,19),
                         tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()
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
                            kamarinap.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText()); 
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
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/Sep/pengajuanSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"jnsPengajuan\": \"1\"," +
                                            "\"keterangan\": \""+Catatan.getText()+"\"," +
                                            "\"user\": \""+user+"\"" +
                                        "}" +
                                    "}" +
                                "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                //System.out.println("message : "+nameNode.path("message").asText());
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
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                BPJSCekDetailSEP detail=new BPJSCekDetailSEP(null,false);
                detail.tampil(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
                detail.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                detail.setLocationRelativeTo(internalFrame1);
                detail.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP ...!!!!");
                BtnBatal.requestFocus();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                BPJSCekDetailSEP detail=new BPJSCekDetailSEP(null,false);
                detail.tampil(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                detail.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                detail.setLocationRelativeTo(internalFrame1);
                detail.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP ...!!!!");
                BtnBatal.requestFocus();
            }
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
            NaikKelas.setEnabled(true);
            Pembiayaan.setEnabled(true);
            PenanggungJawab.setEditable(true);
            KdDPJPLayanan.setText("");
            NmDPJPLayanan.setText("");
            btnDPJPLayanan.setEnabled(false);
        }else if(JenisPelayanan.getSelectedIndex()==1){  
            LabelPoli.setVisible(true);
            KdPoli.setVisible(true);
            NmPoli.setVisible(true);
            btnPoli.setVisible(true);
            NaikKelas.setEnabled(false);
            NaikKelas.setSelectedIndex(0);
            Pembiayaan.setEnabled(false);
            Pembiayaan.setSelectedIndex(0);
            PenanggungJawab.setEditable(false);
            PenanggungJawab.setText("");
            btnDPJPLayanan.setEnabled(true);
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

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt,Katarak,LakaLantas);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/Sep/aprovalSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"jnsPengajuan\": \"1\"," +
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
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                WindowRujukan.setLocationRelativeTo(internalFrame1);
                WindowRujukan.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dibuatkan rujukan...!!!!");
                BtnBatal.requestFocus();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                WindowRujukan.setLocationRelativeTo(internalFrame1);
                WindowRujukan.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dibuatkan rujukan...!!!!");
                BtnBatal.requestFocus();
            }
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
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/Rujukan/2.0/insert";	
                requestJson ="{" +
                                "\"request\": {" +
                                    "\"t_rujukan\": {" +
                                        "\"noSep\": \""+tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()+"\"," +
                                        "\"tglRujukan\": \""+Valid.SetTgl(TanggalRujukKeluar.getSelectedItem()+"")+"\"," +
                                        "\"tglRencanaKunjungan\": \""+Valid.SetTgl(TanggalKunjungRujukan.getSelectedItem()+"")+"\"," +
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
                System.out.println("JSON : "+requestJson);
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("code").asText().equals("200")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                    //response = root.path("response");
                
                    if(Sequel.menyimpantf2("bridging_rujukan_bpjs","?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rujukan",14,new String[]{
                            tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString(),Valid.SetTgl(TanggalRujukKeluar.getSelectedItem()+""),
                            Valid.SetTgl(TanggalKunjungRujukan.getSelectedItem()+""),KdPpkRujukan1.getText(),NmPpkRujukan1.getText(),
                            JenisPelayanan1.getSelectedItem().toString().substring(0,1),Catatan1.getText(),KdPenyakit1.getText(),NmPenyakit1.getText(),
                            TipeRujukan.getSelectedItem().toString(),KdPoli1.getText(),NmPoli1.getText(),response.path("rujukan").path("noRujukan").asText(),
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
            KdPpkRujukan1.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),7).toString());
            NmPpkRujukan1.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),8).toString());
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
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReport("rptBridgingSEPInternal3.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReport("rptBridgingSEPInternal4.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            }
        }
               
    }//GEN-LAST:event_ppSEP1BtnPrintActionPerformed

    private void ppSEP2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP2BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReport("rptBridgingSEPInternal5.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReport("rptBridgingSEPInternal6.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            } 
        }
    }//GEN-LAST:event_ppSEP2BtnPrintActionPerformed

    private void ppSEP3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP3BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReport("rptBridgingSEPInternal7.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReport("rptBridgingSEPInternal8.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            }
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
            }else{ 
                tglkkl="0000-00-00";
                if(LakaLantas.getSelectedIndex()==1){
                    tglkkl=Valid.SetTgl(TanggalKKL.getSelectedItem()+"");
                }
                
                if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",52,new String[]{
                     NoSEP.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""),NoRujukan.getText(),KdPpkRujukan.getText(), 
                     NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(),JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(),NmPenyakit.getText(),
                     KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1),(NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):""),
                     (Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):""),(PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText()),
                     LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),COB.getSelectedItem().toString(),NoTelp.getText(),Katarak.getSelectedItem().toString(),
                     tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                     KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText(),TujuanKunjungan.getSelectedItem().toString().substring(0,1),
                     (FlagProsedur.getSelectedIndex()>0?FlagProsedur.getSelectedItem().toString().substring(0,1):""),(Penunjang.getSelectedIndex()>0?Penunjang.getSelectedIndex()+"":""),
                     (AsesmenPoli.getSelectedIndex()>0?AsesmenPoli.getSelectedItem().toString().substring(0,1):""),KdDPJPLayanan.getText(),NmDPJPLayanan.getText()
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
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/SEP/"+NoSEP.getText();	
                requestEntity = new HttpEntity(headers);
                //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("message").asText().equals("Sukses")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                    //response = root.path("response");
                    
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
                    KdPenyakit.setText(Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit=?",response.path("diagnosa").asText()));
                    NmPenyakit.setText(response.path("diagnosa").asText());
                    NoRujukan.setText(response.path("noRujukan").asText());
                    KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where nm_poli_bpjs=?",response.path("poli").asText()));
                    NmPoli.setText(response.path("poli").asText());
                    if(response.path("poliEksekutif").asText().equals("0")){
                        Eksekutif.setSelectedIndex(0);
                    }else{
                        Eksekutif.setSelectedIndex(1);
                    }
                    Catatan.setText(response.path("catatan").asText());
                    if(!response.path("kdStatusKecelakaan").asText().equals("")){
                        LakaLantas.setSelectedIndex(1);
                        KdKecamatan.setText(response.path("lokasiKejadian").path("kdKec").asText());
                        NmKecamatan.setText(kecamatan.tampilKan(response.path("lokasiKejadian").path("kdKec").asText(),KdKabupaten.getText()));
                        KdKabupaten.setText(response.path("lokasiKejadian").path("kdKab").asText());
                        NmKabupaten.setText(kabupaten.tampilKan(response.path("lokasiKejadian").path("kdKab").asText(),KdPropinsi.getText()));
                        KdPropinsi.setText(response.path("lokasiKejadian").path("kdProp").asText());
                        NmPropinsi.setText(propinsi.tampilKan(response.path("lokasiKejadian").path("kdProp").asText()));
                        Keterangan.setText(response.path("lokasiKejadian").path("ketKejadian").asText());
                        Valid.SetTgl(TanggalKKL,response.path("lokasiKejadian").path("tglKejadian").asText());
                    }
                    KdDPJPLayanan.setText(response.path("dpjp").path("kdDPJP").asText());
                    NmDPJPLayanan.setText(response.path("dpjp").path("nmDPJP").asText());
                    JenisPeserta.setText(response.path("peserta").path("jnsPeserta").asText());
                    KdDPJP.setText(response.path("kontrol").path("kdDokter").asText());
                    NmDPJP.setText(response.path("kontrol").path("nmDokter").asText());
                    NoSKDP.setText(response.path("kontrol").path("noSurat").asText());
                    
                    Valid.SetTgl(TanggalSEP,response.path("tglSep").asText());
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
        }else if (KdPPK.getText().trim().equals("")||NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
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

    private void KatarakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KatarakKeyPressed
        Valid.pindah(evt,Catatan,NoTelp);
    }//GEN-LAST:event_KatarakKeyPressed

    private void TanggalKKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKKLKeyPressed
        Valid.pindah(evt,LakaLantas,Keterangan);
    }//GEN-LAST:event_TanggalKKLKeyPressed

    private void NoSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSKDPKeyPressed
        Valid.pindah(evt, AsalRujukan,btnPPKRujukan);
    }//GEN-LAST:event_NoSKDPKeyPressed

    private void btnSPRIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSPRIActionPerformed
        skdp2.setNoRm(NoKartu.getText());
        skdp2.isCek();
        skdp2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        skdp2.setLocationRelativeTo(internalFrame1);        
        skdp2.setVisible(true);
    }//GEN-LAST:event_btnSPRIActionPerformed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        pilihan=1;
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
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,false);
                    resume.setNoRm(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString());
                    resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resume.setLocationRelativeTo(internalFrame1);
                    resume.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,false);
                    resume.setNoRm(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString());
                    resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resume.setLocationRelativeTo(internalFrame1);
                    resume.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }               
        }
    }//GEN-LAST:event_ppRiwayatPerawatanBtnPrintActionPerformed

    private void ppSEP4BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP4BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReportPDF("rptBridgingSEPInternal.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReportPDF("rptBridgingSEPInternal2.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            } 
        } 
    }//GEN-LAST:event_ppSEP4BtnPrintActionPerformed

    private void ppSEP5BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP5BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReportPDF("rptBridgingSEPInternal3.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReportPDF("rptBridgingSEPInternal4.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            }
        }
    }//GEN-LAST:event_ppSEP5BtnPrintActionPerformed

    private void ppSEP6BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP6BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReportPDF("rptBridgingSEPInternal5.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReportPDF("rptBridgingSEPInternal6.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            } 
        }
    }//GEN-LAST:event_ppSEP6BtnPrintActionPerformed

    private void ppSEP7BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP7BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataSEP.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
                param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
                param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
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
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbDataSEPInternal.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
                kddokter=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("norawat",TNoRw.getText());
                param.put("prb",Sequel.cariIsi("select prb from bpjs_prb where no_sep=?",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()));
                param.put("dokter",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddokter));
                param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select bpjs from gambar")); param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("no_sep",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                param.put("noskdp",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
                param.put("tglrujukan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());
                param.put("kdpolitujuan",tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
                if(JenisPelayanan.getSelectedIndex()==0){
                    Valid.MyReportPDF("rptBridgingSEPInternal7.jasper","report","::[ Cetak SEP Internal ]::",param);
                }else{
                    Valid.MyReportPDF("rptBridgingSEPInternal8.jasper","report","::[ Cetak SEP Internal ]::",param);
                }                
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
                BtnBatal.requestFocus();
            }
        }
    }//GEN-LAST:event_ppSEP7BtnPrintActionPerformed

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatActionPerformed
        historiPelayanan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        historiPelayanan.setLocationRelativeTo(internalFrame1);
        historiPelayanan.setKartu(NoKartu.getText());
        historiPelayanan.setVisible(true);
    }//GEN-LAST:event_btnRiwayatActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSSuratKontrol form=new BPJSSuratKontrol(null,false);
                    form.setNoRm(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),23).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),25).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),14).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSSuratKontrol form=new BPJSSuratKontrol(null,false);
                    form.setNoRm(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),1).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),23).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),25).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),14).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }               
        }
    }//GEN-LAST:event_ppSuratKontrolBtnPrintActionPerformed

    private void btnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSKDPActionPerformed
        skdp.setNoRm(NoKartu.getText());
        skdp.isCek();
        skdp.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        skdp.setLocationRelativeTo(internalFrame1);        
        skdp.setVisible(true);
    }//GEN-LAST:event_btnSKDPActionPerformed

    private void ppSepRujukSamaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSepRujukSamaBtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            query=" and bridging_sep.tglsep=bridging_sep.tglrujukan ";
            tampil();
        }else if(TabRawat.getSelectedIndex()==2){
            query=" and bridging_sep_internal.tglsep=bridging_sep_internal.tglrujukan ";
            tampilInternal();
        }
    }//GEN-LAST:event_ppSepRujukSamaBtnPrintActionPerformed

    private void ppSepRujukBedaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSepRujukBedaBtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            query=" and bridging_sep_internal.tglsep<>bridging_sep_internal.tglrujukan ";
            tampil();
        }else if(TabRawat.getSelectedIndex()==2){
            query=" and bridging_sep_internal.tglsep<>bridging_sep_internal.tglrujukan ";
            tampilInternal();
        }
            
    }//GEN-LAST:event_ppSepRujukBedaBtnPrintActionPerformed

    private void ppSuratPRIBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPRIBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSSPRI form=new BPJSSPRI(null,false);
                    form.setNoRm(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),23).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),25).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSSPRI form=new BPJSSPRI(null,false);
                    form.setNoRm(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),1).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),23).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),25).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_ppSuratPRIBtnPrintActionPerformed

    private void ppProgramPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppProgramPRBActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    try {
                        ps=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pasien.email "+
                            "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis=?");
                        try {
                            ps.setString(1,tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSProgramPRB form=new BPJSProgramPRB(null,false);
                                form.setNoRm(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString(),rs.getString("alamat"),rs.getString("email"),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),44).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),45).toString());
                                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }else{
                                JOptionPane.showMessageDialog(null,"No.RM tidak ditemukan");
                                TCari.requestFocus();
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }
                }
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    try {
                        ps=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pasien.email "+
                            "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis=?");
                        try {
                            ps.setString(1,tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSProgramPRB form=new BPJSProgramPRB(null,false);
                                form.setNoRm(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),1).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString(),rs.getString("alamat"),rs.getString("email"),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),44).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),45).toString());
                                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }else{
                                JOptionPane.showMessageDialog(null,"No.RM tidak ditemukan");
                                TCari.requestFocus();
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }
                }
            }
        }
    }//GEN-LAST:event_ppProgramPRBActionPerformed

    private void NaikKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NaikKelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NaikKelasKeyPressed

    private void PembiayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembiayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PembiayaanKeyPressed

    private void TujuanKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanKunjunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TujuanKunjunganKeyPressed

    private void FlagProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FlagProsedurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlagProsedurKeyPressed

    private void TujuanKunjunganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TujuanKunjunganItemStateChanged
        if(TujuanKunjungan.getSelectedIndex()==0){
            FlagProsedur.setEnabled(false);
            FlagProsedur.setSelectedIndex(0);
            Penunjang.setEnabled(false);
            Penunjang.setSelectedIndex(0);
            AsesmenPoli.setEnabled(true);
        }else{
            if(TujuanKunjungan.getSelectedIndex()==1){
                AsesmenPoli.setSelectedIndex(0);
                AsesmenPoli.setEnabled(false);
            }else{
                AsesmenPoli.setEnabled(true);
            }
            if(FlagProsedur.getSelectedIndex()==0){
                FlagProsedur.setSelectedIndex(2);
            }
            FlagProsedur.setEnabled(true);
            if(Penunjang.getSelectedIndex()==0){
                Penunjang.setSelectedIndex(10);
            }
            Penunjang.setEnabled(true);
        }
    }//GEN-LAST:event_TujuanKunjunganItemStateChanged

    private void NaikKelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NaikKelasItemStateChanged
        if(NaikKelas.getSelectedIndex()>0){
            if(Pembiayaan.getSelectedIndex()==0){
                Pembiayaan.setSelectedIndex(1);
            }
        }else if(NaikKelas.getSelectedIndex()==0){
            Pembiayaan.setSelectedIndex(0);
        }
    }//GEN-LAST:event_NaikKelasItemStateChanged

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenunjangKeyPressed

    private void AsesmenPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenPoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsesmenPoliKeyPressed

    private void btnDPJPLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPLayananActionPerformed
        pilihan=2;
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);        
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJPLayananActionPerformed

    private void btnDPJPLayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPLayananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDPJPLayananKeyPressed

    private void LakaLantasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LakaLantasItemStateChanged
        if(LakaLantas.getSelectedIndex()==0){
            TanggalKKL.setEnabled(false);
            Keterangan.setEditable(false);
            Keterangan.setText("");
        }else{
            TanggalKKL.setEnabled(true);
            Keterangan.setEditable(true);
        }
    }//GEN-LAST:event_LakaLantasItemStateChanged

    private void StatusPulangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_StatusPulangItemStateChanged
        if(StatusPulang.getSelectedIndex()==2){
            NoSuratKematian.setEditable(true);
            TanggalKematian.setEnabled(true);
        }else{
            NoSuratKematian.setEditable(false);
            TanggalKematian.setEnabled(false);
        }
    }//GEN-LAST:event_StatusPulangItemStateChanged

    private void StatusPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPulangKeyPressed

    private void ppSuplesiJasaRaharjaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekSuplesiJasaRaharja form=new BPJSCekSuplesiJasaRaharja(null,false);
                    form.setRM(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString(),Valid.SetTgl2(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),4).toString()));
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekSuplesiJasaRaharja form=new BPJSCekSuplesiJasaRaharja(null,false);
                    form.setRM(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString(),Valid.SetTgl2(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),4).toString()));
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed

    private void ppDataIndukKecelakaanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataIndukKecelakaanBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekDataIndukKecelakaan form=new BPJSCekDataIndukKecelakaan(null,false);
                    form.setRM(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString(),tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekDataIndukKecelakaan form=new BPJSCekDataIndukKecelakaan(null,false);
                    form.setRM(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_ppDataIndukKecelakaanBtnPrintActionPerformed

    private void ppPengajuan2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan2BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/Sep/pengajuanSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"jnsPengajuan\": \"2\"," +
                                            "\"keterangan\": \""+Catatan.getText()+"\"," +
                                            "\"user\": \""+user+"\"" +
                                        "}" +
                                    "}" +
                                "}";
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                //System.out.println("message : "+nameNode.path("message").asText());
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
    }//GEN-LAST:event_ppPengajuan2BtnPrintActionPerformed

    private void ppPengajuan3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan3BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/Sep/aprovalSEP";	
                requestJson =" {" +
                                    "\"request\": {" +
                                        "\"t_sep\": {" +
                                            "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                            "\"tglSep\": \""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                            "\"jnsPelayanan\": \""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                            "\"jnsPengajuan\": \"2\"," +
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
    }//GEN-LAST:event_ppPengajuan3BtnPrintActionPerformed

    private void ppDataSEPInternalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataSEPInternalBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(TabRawat.getSelectedIndex()==1){
                if(tbDataSEP.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekDataSEPInternal form=new BPJSCekDataSEPInternal(null,false);
                    form.setSEP(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }else if(TabRawat.getSelectedIndex()==2){
                if(tbDataSEPInternal.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSCekDataSEPInternal form=new BPJSCekDataSEPInternal(null,false);
                    form.setSEP(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString());
                    form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_ppDataSEPInternalBtnPrintActionPerformed

    private void ppStatusFingerBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStatusFingerBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(!NoKartu.getText().equals("")){
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/SEP/FingerPrint/Peserta/"+NoKartu.getText()+"/TglPelayanan/"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"");
                requestEntity = new HttpEntity(headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                //System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("code").asText().equals("200")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                    JOptionPane.showMessageDialog(null,response.path("status").asText());
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
    }//GEN-LAST:event_ppStatusFingerBtnPrintActionPerformed

    private void ppDaftarRujukanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDaftarRujukanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BPJSRujukanKeluar rujuk=new BPJSRujukanKeluar(null,false);
        rujuk.tampil();
        rujuk.emptTeks();
        rujuk.isCek();
        rujuk.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rujuk.setLocationRelativeTo(internalFrame1);
        rujuk.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppDaftarRujukanBtnPrintActionPerformed

    private void tbDataSEPInternalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSEPInternalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
               getDataInternal();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                i=tbDataSEPInternal.getSelectedColumn();
                if(i==0){
                    TabRawat.setSelectedIndex(0);
                }else if(i==1){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==3){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbDataSEPInternalMouseClicked

    private void tbDataSEPInternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataSEPInternalKeyPressed
        if(tabModeInternal.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                   getDataInternal();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getDataInternal();
                } catch (java.lang.NullPointerException e) {
                }
                i=tbDataSEPInternal.getSelectedColumn();
                if(i==0){
                    TabRawat.setSelectedIndex(0);
                }else if(i==1){
                    ppSEPBtnPrintActionPerformed(null);
                }else if(i==2){
                    ppPulangBtnPrintActionPerformed(null);
                }else if(i==3){
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }
                
        }
    }//GEN-LAST:event_tbDataSEPInternalKeyPressed

    private void TCariInternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariInternalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariInternalActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariInternal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariInternalKeyPressed

    private void BtnCariInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariInternalActionPerformed
        query="";
        tampilInternal();
    }//GEN-LAST:event_BtnCariInternalActionPerformed

    private void BtnCariInternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariInternalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariInternalActionPerformed(null);
        }else{
            Valid.pindah(evt, TCariInternal, BtnAll);
        }
    }//GEN-LAST:event_BtnCariInternalKeyPressed

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
    private widget.ComboBox AsesmenPoli;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCariInternal;
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCariInternal;
    private widget.Tanggal DTPCariInternal2;
    private widget.ComboBox Eksekutif;
    private widget.ComboBox FlagProsedur;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox JenisPelayanan;
    private widget.ComboBox JenisPelayanan1;
    private widget.TextBox JenisPeserta;
    private widget.ComboBox Katarak;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdDPJPLayanan;
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
    private widget.Label LCount1;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli1;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelPoli6;
    private widget.ComboBox LakaLantas;
    private widget.ComboBox NaikKelas;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmDPJPLayanan;
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
    private widget.TextBox NoLPManual;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSEPSuplesi;
    private widget.TextBox NoSKDP;
    private widget.TextBox NoSuratKematian;
    private widget.TextBox NoTelp;
    private widget.ComboBox Pembiayaan;
    private widget.TextBox PenanggungJawab;
    private widget.ComboBox Penunjang;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu Popup1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox Status;
    private widget.ComboBox StatusPulang;
    private widget.ComboBox Suplesi;
    private widget.TextBox TCari;
    private widget.TextBox TCariInternal;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalKKL;
    private widget.Tanggal TanggalKematian;
    private widget.Tanggal TanggalKunjungRujukan;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalRujukKeluar;
    private widget.Tanggal TanggalSEP;
    private widget.TextBox TglLahir;
    private widget.ComboBox TipeRujukan;
    private widget.ComboBox TujuanKunjungan;
    private javax.swing.JDialog WindowCariSEP;
    private javax.swing.JDialog WindowRujukan;
    private javax.swing.JDialog WindowUpdatePulang;
    private widget.Button btnDPJP;
    private widget.Button btnDPJPLayanan;
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
    private widget.Button btnSPRI;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppAmbilSep;
    private javax.swing.JMenuItem ppDaftarRujukan;
    private javax.swing.JMenuItem ppDataIndukKecelakaan;
    private javax.swing.JMenuItem ppDataSEPInternal;
    private javax.swing.JMenuItem ppDetailSEPPeserta;
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private javax.swing.JMenuItem ppPengajuan2;
    private javax.swing.JMenuItem ppPengajuan3;
    private javax.swing.JMenuItem ppProgramPRB;
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
    private javax.swing.JMenuItem ppSepRujukBeda;
    private javax.swing.JMenuItem ppSepRujukSama;
    private javax.swing.JMenuItem ppStatusFinger;
    private javax.swing.JMenuItem ppSuplesiJasaRaharja;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratPRI;
    private widget.Table tbDataSEP;
    private widget.Table tbDataSEPInternal;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,bridging_sep.tglrujukan,"+
                    "bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan') as jnspelayanan,bridging_sep.catatan,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,"+
                    "bridging_sep.nmpolitujuan,if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as klsrawat,bridging_sep.klsnaik,"+
                    "bridging_sep.pembiayaan,bridging_sep.pjnaikkelas,bridging_sep.lakalantas,bridging_sep.user,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,"+
                    "bridging_sep.notelep,bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"+
                    "bridging_sep.nmprop,bridging_sep.kdkab,bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,bridging_sep.kddpjp,bridging_sep.nmdpdjp,"+
                    "bridging_sep.tujuankunjungan,bridging_sep.flagprosedur,bridging_sep.penunjang,bridging_sep.asesmenpelayanan,bridging_sep.kddpjplayanan,bridging_sep.nmdpjplayanan "+
                    "from bridging_sep where bridging_sep.tglsep between ? and ? "+query+(TCari.getText().trim().equals("")?"":" and (bridging_sep.no_sep like ? or "+
                    "bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_sep.nmppkrujukan like ? or bridging_sep.diagawal like ? or "+
                    "bridging_sep.nmdiagnosaawal like ? or bridging_sep.no_rawat like ? or bridging_sep.no_kartu like ? or bridging_sep.nmprop like ? or "+
                    "bridging_sep.nmkab like ? or bridging_sep.nmkec like ? or bridging_sep.nmdpdjp like ? or bridging_sep.asal_rujukan like ? or bridging_sep.notelep like ? "+
                    "or bridging_sep.nmpolitujuan like ?) ")+" order by bridging_sep.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,"%"+TCari.getText().trim()+"%");
                    ps.setString(17,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    penunjang="";
                    switch(rs.getString("penunjang")){
                        case "1":
                            penunjang="1. Radioterapi";
                            break;
                        case "2":
                            penunjang="2. Kemoterapi";
                            break;
                        case "3":
                            penunjang="3. Rehabilitasi Medik";
                            break;
                        case "4":
                            penunjang="4. Rehabilitasi Psikososial";
                            break;
                        case "5":
                            penunjang="5. Transfusi Darah";
                            break;
                        case "6":
                            penunjang="6. Pelayanan Gigi";
                            break;
                        case "7":
                            penunjang="7. Laboratorium";
                            break;
                        case "8":
                            penunjang="8. USG";
                            break;
                        case "9":
                            penunjang="9. Farmasi";
                            break;
                        case "10":
                            penunjang="10. Lain-Lain";
                            break;
                        case "11":
                            penunjang="11. MRI";
                            break;
                        case "12":
                            penunjang="12. HEMODIALISA";
                            break;
                        default :
                            penunjang="";
                            break;
                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("tglsep"),rs.getString("tglrujukan"),
                        rs.getString("no_rujukan"),rs.getString("kdppkrujukan"),rs.getString("nmppkrujukan"),rs.getString("kdppkpelayanan"),rs.getString("nmppkpelayanan"),
                        rs.getString("jnspelayanan"),rs.getString("catatan"),rs.getString("diagawal"),rs.getString("nmdiagnosaawal"),rs.getString("kdpolitujuan"),
                        rs.getString("nmpolitujuan"),rs.getString("klsrawat"),rs.getString("klsnaik").replaceAll("1","1. VVIP").replaceAll("2","2. VIP").
                        replaceAll("3","3. Kelas I").replaceAll("4","4. Kelas II").replaceAll("5","5. Kelas III").replaceAll("6","6. ICCU").replaceAll("7","7. ICU"),
                        rs.getString("pembiayaan").replaceAll("1","1. Pribadi").replaceAll("2","2. Pemberi Kerja").replaceAll("2","3. Asuransi Lain"),rs.getString("pjnaikkelas"),
                        rs.getString("lakalantas").replaceAll("0","0. Bukan KLL").replaceAll("1","1. KLL Bukan KK").replaceAll("2","2. KLL dan KK").replaceAll("3","3. KK"),
                        rs.getString("user"),rs.getString("tanggal_lahir"),rs.getString("peserta"),rs.getString("jkel"),rs.getString("no_kartu"),
                        rs.getString("tglpulang"),rs.getString("asal_rujukan"),rs.getString("eksekutif"),rs.getString("cob"),rs.getString("notelep"),rs.getString("katarak"),
                        rs.getString("tglkkl"),rs.getString("keterangankkl"),rs.getString("suplesi"),rs.getString("no_sep_suplesi"),rs.getString("kdprop"),rs.getString("nmprop"),
                        rs.getString("kdkab"),rs.getString("nmkab"),rs.getString("kdkec"),rs.getString("nmkec"),rs.getString("noskdp"),rs.getString("kddpjp"),rs.getString("nmdpdjp"),
                        rs.getString("tujuankunjungan").replaceAll("0","0. Normal").replaceAll("1","1. Prosedur").replaceAll("2","2. Konsul Dokter"),rs.getString("flagprosedur").
                        replaceAll("0","0. Prosedur Tidak Berkelanjutan").replaceAll("1","1. Prosedur dan Terapi Berkelanjutan"),penunjang,rs.getString("asesmenpelayanan").
                        replaceAll("1","1. Poli spesialis tidak tersedia pada hari sebelumnya").replaceAll("2","2. Jam Poli telah berakhir pada hari sebelumnya").
                        replaceAll("3","3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya").replaceAll("4","4. Atas Instruksi RS").replaceAll("5","5. Tujuan Kontrol"),
                        rs.getString("kddpjplayanan"),rs.getString("nmdpjplayanan")
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
    
    public void tampilInternal() {        
        Valid.tabelKosong(tabModeInternal);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep_internal.no_sep, bridging_sep_internal.no_rawat,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,bridging_sep_internal.tglsep,bridging_sep_internal.tglrujukan,"+
                    "bridging_sep_internal.no_rujukan,bridging_sep_internal.kdppkrujukan,bridging_sep_internal.nmppkrujukan,bridging_sep_internal.kdppkpelayanan,bridging_sep_internal.nmppkpelayanan,"+
                    "if(bridging_sep_internal.jnspelayanan='1','1. Ranap','2. Ralan') as jnspelayanan,bridging_sep_internal.catatan,bridging_sep_internal.diagawal,bridging_sep_internal.nmdiagnosaawal,bridging_sep_internal.kdpolitujuan,"+
                    "bridging_sep_internal.nmpolitujuan,if(bridging_sep_internal.klsrawat='1','1. Kelas 1',if(bridging_sep_internal.klsrawat='2','2. Kelas 2','3. Kelas 3')) as klsrawat,bridging_sep_internal.klsnaik,"+
                    "bridging_sep_internal.pembiayaan,bridging_sep_internal.pjnaikkelas,bridging_sep_internal.lakalantas,bridging_sep_internal.user,bridging_sep_internal.tanggal_lahir,"+
                    "bridging_sep_internal.peserta,bridging_sep_internal.jkel,bridging_sep_internal.no_kartu,bridging_sep_internal.tglpulang,bridging_sep_internal.asal_rujukan,bridging_sep_internal.eksekutif,bridging_sep_internal.cob,"+
                    "bridging_sep_internal.notelep,bridging_sep_internal.katarak,bridging_sep_internal.tglkkl,bridging_sep_internal.keterangankkl,bridging_sep_internal.suplesi,bridging_sep_internal.no_sep_suplesi,bridging_sep_internal.kdprop,"+
                    "bridging_sep_internal.nmprop,bridging_sep_internal.kdkab,bridging_sep_internal.nmkab,bridging_sep_internal.kdkec,bridging_sep_internal.nmkec,bridging_sep_internal.noskdp,bridging_sep_internal.kddpjp,bridging_sep_internal.nmdpdjp,"+
                    "bridging_sep_internal.tujuankunjungan,bridging_sep_internal.flagprosedur,bridging_sep_internal.penunjang,bridging_sep_internal.asesmenpelayanan,bridging_sep_internal.kddpjplayanan,bridging_sep_internal.nmdpjplayanan "+
                    "from bridging_sep_internal where bridging_sep_internal.tglsep between ? and ? "+query+(TCari.getText().trim().equals("")?"":" and (bridging_sep_internal.no_sep like ? or "+
                    "bridging_sep_internal.nomr like ? or bridging_sep_internal.nama_pasien like ? or bridging_sep_internal.nmppkrujukan like ? or bridging_sep_internal.diagawal like ? or "+
                    "bridging_sep_internal.nmdiagnosaawal like ? or bridging_sep_internal.no_rawat like ? or bridging_sep_internal.no_kartu like ? or bridging_sep_internal.nmprop like ? or "+
                    "bridging_sep_internal.nmkab like ? or bridging_sep_internal.nmkec like ? or bridging_sep_internal.nmdpdjp like ? or bridging_sep_internal.asal_rujukan like ? or bridging_sep_internal.notelep like ? "+
                    "or bridging_sep_internal.nmpolitujuan like ?) ")+" order by bridging_sep_internal.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(DTPCariInternal.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCariInternal2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(4,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(5,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(6,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(7,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(8,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(9,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(10,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(11,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(12,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(13,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(14,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(15,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(16,"%"+TCariInternal.getText().trim()+"%");
                    ps.setString(17,"%"+TCariInternal.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    penunjang="";
                    switch(rs.getString("penunjang")){
                        case "1":
                            penunjang="1. Radioterapi";
                            break;
                        case "2":
                            penunjang="2. Kemoterapi";
                            break;
                        case "3":
                            penunjang="3. Rehabilitasi Medik";
                            break;
                        case "4":
                            penunjang="4. Rehabilitasi Psikososial";
                            break;
                        case "5":
                            penunjang="5. Transfusi Darah";
                            break;
                        case "6":
                            penunjang="6. Pelayanan Gigi";
                            break;
                        case "7":
                            penunjang="7. Laboratorium";
                            break;
                        case "8":
                            penunjang="8. USG";
                            break;
                        case "9":
                            penunjang="9. Farmasi";
                            break;
                        case "10":
                            penunjang="10. Lain-Lain";
                            break;
                        case "11":
                            penunjang="11. MRI";
                            break;
                        case "12":
                            penunjang="12. HEMODIALISA";
                            break;
                        default :
                            penunjang="";
                            break;
                    }
                    tabModeInternal.addRow(new Object[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("tglsep"),rs.getString("tglrujukan"),
                        rs.getString("no_rujukan"),rs.getString("kdppkrujukan"),rs.getString("nmppkrujukan"),rs.getString("kdppkpelayanan"),rs.getString("nmppkpelayanan"),
                        rs.getString("jnspelayanan"),rs.getString("catatan"),rs.getString("diagawal"),rs.getString("nmdiagnosaawal"),rs.getString("kdpolitujuan"),
                        rs.getString("nmpolitujuan"),rs.getString("klsrawat"),rs.getString("klsnaik").replaceAll("1","1. VVIP").replaceAll("2","2. VIP").
                        replaceAll("3","3. Kelas I").replaceAll("4","4. Kelas II").replaceAll("5","5. Kelas III").replaceAll("6","6. ICCU").replaceAll("7","7. ICU"),
                        rs.getString("pembiayaan").replaceAll("1","1. Pribadi").replaceAll("2","2. Pemberi Kerja").replaceAll("2","3. Asuransi Lain"),rs.getString("pjnaikkelas"),
                        rs.getString("lakalantas").replaceAll("0","0. Bukan KLL").replaceAll("1","1. KLL Bukan KK").replaceAll("2","2. KLL dan KK").replaceAll("3","3. KK"),
                        rs.getString("user"),rs.getString("tanggal_lahir"),rs.getString("peserta"),rs.getString("jkel"),rs.getString("no_kartu"),
                        rs.getString("tglpulang"),rs.getString("asal_rujukan"),rs.getString("eksekutif"),rs.getString("cob"),rs.getString("notelep"),rs.getString("katarak"),
                        rs.getString("tglkkl"),rs.getString("keterangankkl"),rs.getString("suplesi"),rs.getString("no_sep_suplesi"),rs.getString("kdprop"),rs.getString("nmprop"),
                        rs.getString("kdkab"),rs.getString("nmkab"),rs.getString("kdkec"),rs.getString("nmkec"),rs.getString("noskdp"),rs.getString("kddpjp"),rs.getString("nmdpdjp"),
                        rs.getString("tujuankunjungan").replaceAll("0","0. Normal").replaceAll("1","1. Prosedur").replaceAll("2","2. Konsul Dokter"),rs.getString("flagprosedur").
                        replaceAll("0","0. Prosedur Tidak Berkelanjutan").replaceAll("1","1. Prosedur dan Terapi Berkelanjutan"),penunjang,rs.getString("asesmenpelayanan").
                        replaceAll("1","1. Poli spesialis tidak tersedia pada hari sebelumnya").replaceAll("2","2. Jam Poli telah berakhir pada hari sebelumnya").
                        replaceAll("3","3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya").replaceAll("4","4. Atas Instruksi RS").replaceAll("5","5. Tujuan Kontrol"),
                        rs.getString("kddpjplayanan"),rs.getString("nmdpjplayanan")
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
        LCount.setText(""+tabModeInternal.getRowCount()); 
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
        TanggalKKL.setEnabled(false);
        Keterangan.setEditable(false);
        NaikKelas.setEnabled(false);
        NaikKelas.setSelectedIndex(0);
        Pembiayaan.setEnabled(false);
        Pembiayaan.setSelectedIndex(0);
        PenanggungJawab.setEditable(false);
        PenanggungJawab.setText("");
        TujuanKunjungan.setSelectedIndex(0);
        FlagProsedur.setSelectedIndex(0);
        FlagProsedur.setEnabled(false);
        Penunjang.setSelectedIndex(0);
        Penunjang.setEnabled(false);
        AsesmenPoli.setSelectedIndex(0);
        AsesmenPoli.setEnabled(true);
        KdDPJPLayanan.setText("");
        NmDPJPLayanan.setText("");
        btnDPJPLayanan.setEnabled(true);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"' ","BR/"+dateformat.format(TanggalSEP.getDate())+"/",4,NoBalasan);
        NoRujukan.requestFocus();
    }
    
    public void setNoRm2(String norwt, Date tgl1,String status,String kdpoli,String namapoli,String kddokter) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?",kdpoli));
        NmPoli.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?",KdPoli.getText()));
        KdDPJP.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?",kddokter));
        NmDPJP.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?",kddokter));
        JenisPelayanan.setSelectedItem(status);
        JenisPelayananItemStateChanged(null);
        isRawat();            
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
        ppPengajuan2.setEnabled(akses.getbpjs_sep());
        ppPengajuan3.setEnabled(akses.getbpjs_sep());
        ppStatusFinger.setEnabled(akses.getbpjs_sep());
        ppPulang.setEnabled(akses.getbpjs_sep());
        ppSEP.setEnabled(akses.getbpjs_sep());               
        ppRiwayatPerawatan.setEnabled(akses.getresume_pasien());    
        ppSuratKontrol.setEnabled(akses.getbpjs_surat_kontrol());    
        ppSuratPRI.setEnabled(akses.getbpjs_surat_pri());     
        ppProgramPRB.setEnabled(akses.getbpjs_program_prb());   
        ppSuplesiJasaRaharja.setEnabled(akses.getbpjs_suplesi_jasaraharja());  
        ppDataIndukKecelakaan.setEnabled(akses.getbpjs_data_induk_kecelakaan());  
        ppDataSEPInternal.setEnabled(akses.getbpjs_sep_internal());    
        ppRujukan.setEnabled(akses.getbpjs_rujukan_keluar()); 
        ppDaftarRujukan.setEnabled(akses.getbpjs_rujukan_keluar());
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    private void getData() {
        if(tbDataSEP.getSelectedRow()!= -1){
            tab=1;
            TNoRw.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString());
            TNoRM.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),2).toString());
            TPasien.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),3).toString());
            NoRujukan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),6).toString());
            KdPpkRujukan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),7).toString());
            NmPpkRujukan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),8).toString());
            KdPPK.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),9).toString());
            NmPPK.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),10).toString());
            JenisPelayanan.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),11).toString());
            Catatan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),12).toString());
            KdPenyakit.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),13).toString());
            NmPenyakit.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),14).toString().replaceAll(KdPenyakit.getText(),""));
            KdPoli.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),15).toString());
            NmPoli.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),16).toString());
            Kelas.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),17).toString());
            NaikKelas.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),18).toString());
            Pembiayaan.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),19).toString());
            PenanggungJawab.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),20).toString());
            LakaLantas.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),21).toString());
            TglLahir.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),23).toString());
            JenisPeserta.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),24).toString());
            JK.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),25).toString());
            NoKartu.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString());
            AsalRujukan.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),28).toString());
            Eksekutif.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),29).toString());
            COB.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),30).toString());
            NoTelp.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),31).toString());
            Katarak.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),32).toString());
            Keterangan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),34).toString());
            Suplesi.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),35).toString());
            NoSEPSuplesi.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),36).toString());
            KdPropinsi.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),37).toString());
            NmPropinsi.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),38).toString());
            KdKabupaten.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),39).toString());
            NmKabupaten.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),40).toString());
            KdKecamatan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),41).toString());
            NmKecamatan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),42).toString());
            NoSKDP.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),43).toString());
            KdDPJP.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),44).toString());
            NmDPJP.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),45).toString());
            TujuanKunjungan.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),46).toString());
            FlagProsedur.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),47).toString());
            Penunjang.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),48).toString());
            AsesmenPoli.setSelectedItem(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),49).toString());
            KdDPJPLayanan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),50).toString());
            NmDPJPLayanan.setText(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),51).toString());
            
            Valid.SetTgl(TanggalSEP,tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),4).toString());
            Valid.SetTgl(TanggalRujuk,tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),5).toString());            
            Valid.SetTgl(TanggalKKL,tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),33).toString());            
            Status.setText("AKTIF");
        }
    }
    
    private void getDataInternal() {
        if(tbDataSEPInternal.getSelectedRow()!= -1){
            tab=2;
            TNoRw.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),1).toString());
            TNoRM.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),2).toString());
            TPasien.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),3).toString());
            NoRujukan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),6).toString());
            KdPpkRujukan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),7).toString());
            NmPpkRujukan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),8).toString());
            KdPPK.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),9).toString());
            NmPPK.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),10).toString());
            JenisPelayanan.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),11).toString());
            Catatan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),12).toString());
            KdPenyakit.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),13).toString());
            NmPenyakit.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),14).toString().replaceAll(KdPenyakit.getText(),""));
            KdPoli.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString());
            NmPoli.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),16).toString());
            Kelas.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),17).toString());
            NaikKelas.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),18).toString());
            Pembiayaan.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),19).toString());
            PenanggungJawab.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),20).toString());
            LakaLantas.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),21).toString());
            TglLahir.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),23).toString());
            JenisPeserta.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),24).toString());
            JK.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),25).toString());
            NoKartu.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),26).toString());
            AsalRujukan.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),28).toString());
            Eksekutif.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),29).toString());
            COB.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),30).toString());
            NoTelp.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),31).toString());
            Katarak.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),32).toString());
            Keterangan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),34).toString());
            Suplesi.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),35).toString());
            NoSEPSuplesi.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),36).toString());
            KdPropinsi.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),37).toString());
            NmPropinsi.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),38).toString());
            KdKabupaten.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),39).toString());
            NmKabupaten.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),40).toString());
            KdKecamatan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),41).toString());
            NmKecamatan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),42).toString());
            NoSKDP.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString());
            KdDPJP.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),44).toString());
            NmDPJP.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),45).toString());
            TujuanKunjungan.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),46).toString());
            FlagProsedur.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),47).toString());
            Penunjang.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),48).toString());
            AsesmenPoli.setSelectedItem(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),49).toString());
            KdDPJPLayanan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),50).toString());
            NmDPJPLayanan.setText(tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),51).toString());
            
            Valid.SetTgl(TanggalSEP,tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),4).toString());
            Valid.SetTgl(TanggalRujuk,tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString());            
            Valid.SetTgl(TanggalKKL,tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),33).toString());            
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
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            URL = link+"/SEP/2.0/delete";
            requestJson ="{\"request\":{\"t_sep\":{\"noSep\":\""+tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()+"\",\"user\":\""+user+"\"}}}";  
            System.out.println("JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_sep","no_sep",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
                tampil();
                emptTeks();
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    @Test
    public void bodyWithDeleteRequestInternal() throws Exception {
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
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            URL = link+"/SEP/Internal/delete";
            requestJson ="{" +
                                "\"request\": {" +
                                    "\"t_sep\": {" +
                                        "\"noSep\": \""+tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString()+"\"," +
                                        "\"noSurat\": \""+tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString()+"\"," +
                                        "\"tglRujukanInternal\": \""+tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString()+"\"," +
                                        "\"kdPoliTuj\": \""+tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString()+"\"," +
                                        "\"user\": \""+user+"\"" +
                                    "}" +
                                "}" +
                          "}";            
            System.out.println("JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.queryu2("delete from bridging_sep_internal where no_sep=? and noskdp=? and tglrujukan=? and kdpolitujuan=?",4,new String[]{
                    tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),0).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),43).toString(),
                    tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),5).toString(),tbDataSEPInternal.getValueAt(tbDataSEPInternal.getSelectedRow(),15).toString()
                });
                tampilInternal();
                emptTeks();
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
            tglkkl="0000-00-00";
            if(LakaLantas.getSelectedIndex()==1){
                tglkkl=Valid.SetTgl(TanggalKKL.getSelectedItem()+"");
            }
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
            URL = link+"/SEP/2.0/insert";            
            requestJson ="{" +
                          "\"request\":{" +
                                "\"t_sep\":{" +
                                    "\"noKartu\":\""+NoKartu.getText()+"\"," +
                                    "\"tglSep\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                    "\"ppkPelayanan\":\""+KdPPK.getText()+"\"," +
                                    "\"jnsPelayanan\":\""+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"klsRawat\":{"+
                                        "\"klsRawatHak\":\""+Kelas.getSelectedItem().toString().substring(0,1)+"\","+
                                        "\"klsRawatNaik\":\""+(NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):"")+"\","+
                                        "\"pembiayaan\":\""+(Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):"")+"\","+
                                        "\"penanggungJawab\":\""+(PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText())+"\""+
                                    "},"+
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
                                            "\"tglKejadian\": \""+tglkkl.replaceAll("0000-00-00","")+"\"," +
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
                                    "\"tujuanKunj\": \""+TujuanKunjungan.getSelectedItem().toString().substring(0,1)+"\","+
                                    "\"flagProcedure\": \""+(FlagProsedur.getSelectedIndex()>0?FlagProsedur.getSelectedItem().toString().substring(0,1):"")+"\","+
                                    "\"kdPenunjang\": \""+(Penunjang.getSelectedIndex()>0?Penunjang.getSelectedIndex()+"":"")+"\","+
                                    "\"assesmentPel\": \""+(AsesmenPoli.getSelectedIndex()>0?AsesmenPoli.getSelectedItem().toString().substring(0,1):"")+"\","+
                                    "\"skdp\": {" +
                                        "\"noSurat\": \""+NoSKDP.getText()+"\"," +
                                        "\"kodeDPJP\": \""+KdDPJP.getText()+"\"" +
                                    "},"+
                                    "\"dpjpLayan\": \""+(KdDPJPLayanan.getText().equals("")?"":KdDPJPLayanan.getText())+"\","+
                                    "\"noTelp\": \""+NoTelp.getText()+"\","+
                                    "\"user\":\""+user+"\"" +
                                   "}" +
                             "}" +
                         "}";
            System.out.println("JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                 response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc)).path("sep").path("noSep");
                 //response = root.path("response").path("sep").path("noSep");
                 if(Sequel.menyimpantf2("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",52,new String[]{
                     response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""),NoRujukan.getText(),KdPpkRujukan.getText(), 
                     NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(),JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(),NmPenyakit.getText(),
                     KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1),(NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):""),
                     (Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):""),(PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText()),
                     LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),COB.getSelectedItem().toString(),NoTelp.getText(),Katarak.getSelectedItem().toString(),
                     tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                     KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText(),TujuanKunjungan.getSelectedItem().toString().substring(0,1),
                     (FlagProsedur.getSelectedIndex()>0?FlagProsedur.getSelectedItem().toString().substring(0,1):""),(Penunjang.getSelectedIndex()>0?Penunjang.getSelectedIndex()+"":""),
                     (AsesmenPoli.getSelectedIndex()>0?AsesmenPoli.getSelectedItem().toString().substring(0,1):""),KdDPJPLayanan.getText(),NmDPJPLayanan.getText()
                 })==true){
                     Sequel.menyimpan("rujuk_masuk","?,?,?,?,?,?,?,?,?,?",10,new String[]{
                         TNoRw.getText(),NmPpkRujukan.getText(),"-",NoRujukan.getText(),"0",NmPpkRujukan.getText(),KdPenyakit.getText(),"-",
                         "-",NoBalasan.getText()
                     });
                     if(JenisPelayanan.getSelectedIndex()==1){
                            Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                                 Valid.SetTgl(TanggalSEP.getSelectedItem()+""),
                                 response.asText()
                            });
                            emptTeks();                         
                            tampil();     
                     } 
                     if(!prb.equals("")){
                        if(Sequel.menyimpantf("bpjs_prb","?,?","PRB",2,new String[]{response.asText(),prb})==true){
                            prb="";
                        } 
                     }
                        
                     emptTeks();
                 }else{
                     if(Sequel.menyimpantf("bridging_sep_internal","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",52,new String[]{
                        response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+""),Valid.SetTgl(TanggalRujuk.getSelectedItem()+""),NoRujukan.getText(),KdPpkRujukan.getText(), 
                        NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(),JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(),NmPenyakit.getText(),
                        KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1),(NaikKelas.getSelectedIndex()>0?NaikKelas.getSelectedItem().toString().substring(0,1):""),
                        (Pembiayaan.getSelectedIndex()>0?Pembiayaan.getSelectedItem().toString().substring(0,1):""),(PenanggungJawab.getText().equals("")?"":PenanggungJawab.getText()),
                        LakaLantas.getSelectedItem().toString().substring(0,1),user,TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                        "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),COB.getSelectedItem().toString(),NoTelp.getText(),Katarak.getSelectedItem().toString(),
                        tglkkl,Keterangan.getText(),Suplesi.getSelectedItem().toString(),NoSEPSuplesi.getText(),KdPropinsi.getText(),NmPropinsi.getText(),KdKabupaten.getText(),NmKabupaten.getText(),
                        KdKecamatan.getText(),NmKecamatan.getText(),NoSKDP.getText(),KdDPJP.getText(),NmDPJP.getText(),TujuanKunjungan.getSelectedItem().toString().substring(0,1),
                        (FlagProsedur.getSelectedIndex()>0?FlagProsedur.getSelectedItem().toString().substring(0,1):""),(Penunjang.getSelectedIndex()>0?Penunjang.getSelectedIndex()+"":""),
                        (AsesmenPoli.getSelectedIndex()>0?AsesmenPoli.getSelectedItem().toString().substring(0,1):""),KdDPJPLayanan.getText(),NmDPJPLayanan.getText()
                    })==true){
                        Sequel.menyimpan("rujuk_masuk","?,?,?,?,?,?,?,?,?,?",10,new String[]{
                            TNoRw.getText(),NmPpkRujukan.getText(),"-",NoRujukan.getText(),"0",NmPpkRujukan.getText(),KdPenyakit.getText(),"-",
                            "-",NoBalasan.getText()
                        });
                        if(JenisPelayanan.getSelectedIndex()==1){
                               Sequel.mengedit("bridging_sep_internal","no_sep=?","tglpulang=?",2,new String[]{                             
                                    Valid.SetTgl(TanggalSEP.getSelectedItem()+""),
                                    response.asText()
                               });
                               emptTeks();                         
                               tampil();     
                        } 
                        if(!prb.equals("")){
                           if(Sequel.menyimpantf("bpjs_prb","?,?","PRB",2,new String[]{response.asText(),prb})==true){
                               prb="";
                           } 
                        }

                        emptTeks();
                    }
                 }                     
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi Bridging : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
}

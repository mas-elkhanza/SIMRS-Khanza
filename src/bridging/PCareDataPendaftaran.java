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
import inventory.DlgCariObat;
import inventory.DlgCariObat2;
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
import keuangan.DlgCariPerawatanRalan;
import keuangan.DlgCariPerawatanRanap;
import org.apache.commons.codec.binary.Base64;
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
import simrskhanza.DlgResumePerawatan;


/**
 *
 * @author perpustakaan
 */
public final class PCareDataPendaftaran extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1;
    private final Properties prop = new Properties();
    private PCareCekReferensiPoli poli=new PCareCekReferensiPoli(null,false); 
    private PCareCekReferensiKesadaran kesadaran=new PCareCekReferensiKesadaran(null,false);
    private PCareCekReferensiStatusPulang statuspulang=new PCareCekReferensiStatusPulang(null,false);
    private PCareCekReferensiDokter dokter=new PCareCekReferensiDokter(null,false);
    private PCareCekReferensiPenyakit penyakit=new PCareCekReferensiPenyakit(null,false);
    private PCareCekReferensiSarana sarana=new PCareCekReferensiSarana(null,false);
    private PCareCekReferensiSubspesialis subspesialis=new PCareCekReferensiSubspesialis(null,false);
    private PCareCekReferensiProvider provider=new PCareCekReferensiProvider(null,false);
    private PCareCekReferensiKhusus khusus=new PCareCekReferensiKhusus(null,false);
    private PcareApi api=new PcareApi();
    private String URL="",bangsal="",requestJson="",kunjungansakit="true",diagnosa2="",diagnosa3="",otorisasi,kamar="",divreg="",kacab="",userpcare="";
    private HttpHeaders headers,headers2;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String kdptg,nmptg;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PCareDataPendaftaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Tgl.Daftar","No.RM","Nama Pasien","Kode Provider","No.Kartu","KodePoli", 
                "Nama Poli","Keluhan","Jenis Kunjungan","Sis","Dias","B.B.","T.B.","Respiratory Rate",
                "Heart Rate","Rujuk Balik","Perawatan","No.Urut"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPendaftaran.setModel(tabMode);
        tbPendaftaran.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPendaftaran.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbPendaftaran.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(140);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setPreferredWidth(95);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(35);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setPreferredWidth(120);
            }else if(i==18){
                column.setPreferredWidth(45);
            }
        }
        tbPendaftaran.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.Kunjungan","Tgl.Daftar","No.RM","Nm.Pasien","No.Kartu",
                "Kd.Poli","Nm.Poli","Keluhan","Kd.Sadar","Kesadaran","Sis","Dias","B.B.",
                "T.B.","Respiratory Rate","Heart Rate","Terapi","Kd.Pulang","Stts.Pulang",
                "Tgl.Pulang","Kode Dokter","Nama Dokter","ICDX 1","Nama Diagnosa 1", 
                "ICDX 2","Nama Diagnosa 2","ICDX 3", "Nama Diagnosa 3"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKunjungan.setModel(tabMode2);
        tbKunjungan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKunjungan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbKunjungan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(140);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(35);
            }else if(i==14){
                column.setPreferredWidth(35);
            }else if(i==15){
                column.setPreferredWidth(110);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(140);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==22){
                column.setPreferredWidth(140);
            }else if(i==23){
                column.setPreferredWidth(45);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(45);
            }else if(i==28){
                column.setPreferredWidth(150);
            }
        }
        tbKunjungan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Keluhan.setDocument(new batasInput((int)400).getKata(Keluhan));
        Sistole.setDocument(new batasInput((byte)3).getOnlyAngka(Sistole));
        Diastole.setDocument(new batasInput((byte)3).getOnlyAngka(Diastole));
        TinggiBadan.setDocument(new batasInput((byte)5).getOnlyAngka(TinggiBadan));
        BeratBadan.setDocument(new batasInput((byte)5).getOnlyAngka(BeratBadan));
        Respiratory.setDocument(new batasInput((byte)3).getOnlyAngka(Respiratory));
        Heartrate.setDocument(new batasInput((byte)3).getOnlyAngka(Heartrate));
        
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    if(pilihan==1){
                        KdPoliTujuan.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoliTujuan.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KdPoliTujuan.requestFocus();
                    }else if(pilihan==2){
                        KdPoliInternal.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoliInternal.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KdPoliInternal.requestFocus();
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
        
        kesadaran.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kesadaran.getTable().getSelectedRow()!= -1){   
                    KdSadar.setText(kesadaran.getTable().getValueAt(kesadaran.getTable().getSelectedRow(),1).toString());
                    NmSadar.setText(kesadaran.getTable().getValueAt(kesadaran.getTable().getSelectedRow(),2).toString());
                    KdSadar.requestFocus();                      
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
        
        kesadaran.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kesadaran.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        statuspulang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statuspulang.getTable().getSelectedRow()!= -1){   
                    KdStatusPulang.setText(statuspulang.getTable().getValueAt(statuspulang.getTable().getSelectedRow(),1).toString());
                    NmStatusPulang.setText(statuspulang.getTable().getValueAt(statuspulang.getTable().getSelectedRow(),2).toString());
                    KdStatusPulang.requestFocus();                      
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
        
       statuspulang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    statuspulang.dispose();
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
                    KdTenagaMedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    NmTenagaMedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                    KdTenagaMedis.requestFocus();                      
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
       
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penyakit.getTable().getSelectedRow()!= -1){   
                    if(pilihan==1){
                        KdDiagnosa1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        NmDiagnosa1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                        KdDiagnosa1.requestFocus();
                    }else if(pilihan==2){
                        KdDiagnosa2.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        NmDiagnosa2.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                        KdDiagnosa2.requestFocus();
                    }else if(pilihan==3){
                        KdDiagnosa3.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                        NmDiagnosa3.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),2).toString());
                        KdDiagnosa3.requestFocus();
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
        
        subspesialis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(subspesialis.getTable().getSelectedRow()!= -1){   
                    if(pilihan==1){
                        KdSubSpesialis.setText(subspesialis.getTable().getValueAt(subspesialis.getTable().getSelectedRow(),1).toString());
                        NmSubSpesialis.setText(subspesialis.getTable().getValueAt(subspesialis.getTable().getSelectedRow(),2).toString());
                        KdSubSpesialis.requestFocus();   
                    }else if(pilihan==2){
                        KdSubKhusus.setText(subspesialis.getTable().getValueAt(subspesialis.getTable().getSelectedRow(),1).toString());
                        NmSubKhusus.setText(subspesialis.getTable().getValueAt(subspesialis.getTable().getSelectedRow(),2).toString());
                        KdSubKhusus.requestFocus();   
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
        
        subspesialis.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    subspesialis.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        sarana.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sarana.getTable().getSelectedRow()!= -1){   
                    KdSarana.setText(sarana.getTable().getValueAt(sarana.getTable().getSelectedRow(),1).toString());
                    NmSarana.setText(sarana.getTable().getValueAt(sarana.getTable().getSelectedRow(),2).toString());
                    KdSarana.requestFocus();                      
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
        
        sarana.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sarana.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        provider.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(provider.getTable().getSelectedRow()!= -1){   
                    KdPPKRujukan.setText(provider.getTable().getValueAt(provider.getTable().getSelectedRow(),1).toString());
                    NmPPKRujukan.setText(provider.getTable().getValueAt(provider.getTable().getSelectedRow(),2).toString());
                    KdPPKRujukan.requestFocus();                      
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
        
        provider.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    provider.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        khusus.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(khusus.getTable().getSelectedRow()!= -1){   
                    KdKhusus.setText(khusus.getTable().getValueAt(khusus.getTable().getSelectedRow(),1).toString());
                    NmKhusus.setText(khusus.getTable().getValueAt(khusus.getTable().getSelectedRow(),2).toString());
                    KdKhusus.requestFocus();                      
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
        
        khusus.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    khusus.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));  
            otorisasi=prop.getProperty("USERPCARE")+":"+prop.getProperty("PASSPCARE")+":095";
            URL=prop.getProperty("URLAPIPCARE");
            divreg=prop.getProperty("DIVREGPCARE");
            kacab=prop.getProperty("KACABPCARE");
            userpcare=prop.getProperty("USERPCARE");
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

        ProviderPeserta = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnGelang = new javax.swing.JMenu();
        MnGelang1 = new javax.swing.JMenuItem();
        MnGelang2 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang6 = new javax.swing.JMenuItem();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcode2 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnPemberianObat1 = new javax.swing.JMenuItem();
        MnTIndakan = new javax.swing.JMenuItem();
        MnTIndakan1 = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppBuktiKunjungan = new javax.swing.JMenuItem();
        tanggal = new widget.Tanggal();
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
        jLabel22 = new widget.Label();
        TanggalDaftar = new widget.Tanggal();
        jLabel14 = new widget.Label();
        Keluhan = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        JK = new widget.TextBox();
        jLabel24 = new widget.Label();
        JenisPeserta = new widget.TextBox();
        jLabel25 = new widget.Label();
        Status = new widget.TextBox();
        jLabel27 = new widget.Label();
        JenisKunjungan = new widget.ComboBox();
        Perawatan = new widget.ComboBox();
        jLabel28 = new widget.Label();
        LabelPoli = new widget.Label();
        KdPoliTujuan = new widget.TextBox();
        NmPoliTujuan = new widget.TextBox();
        btnPoliTujuan = new widget.Button();
        LabelPoli2 = new widget.Label();
        jLabel15 = new widget.Label();
        TinggiBadan = new widget.TextBox();
        BeratBadan = new widget.TextBox();
        jLabel16 = new widget.Label();
        LabelPoli3 = new widget.Label();
        jLabel17 = new widget.Label();
        Sistole = new widget.TextBox();
        Diastole = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Respiratory = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        Heartrate = new widget.TextBox();
        jLabel41 = new widget.Label();
        chkSubspesialis = new widget.CekBox();
        jLabel26 = new widget.Label();
        TanggalKunjungan = new widget.Tanggal();
        LabelPoli4 = new widget.Label();
        KdSadar = new widget.TextBox();
        NmSadar = new widget.TextBox();
        BtnKesadaran = new widget.Button();
        jLabel30 = new widget.Label();
        Terapi = new widget.TextBox();
        LabelPoli5 = new widget.Label();
        KdStatusPulang = new widget.TextBox();
        NmStatusPulang = new widget.TextBox();
        BtnStatusPulang = new widget.Button();
        TanggalPulang = new widget.Tanggal();
        jLabel31 = new widget.Label();
        LabelPoli6 = new widget.Label();
        KdTenagaMedis = new widget.TextBox();
        NmTenagaMedis = new widget.TextBox();
        BtnTenagaMedis = new widget.Button();
        LabelPoli7 = new widget.Label();
        KdDiagnosa1 = new widget.TextBox();
        NmDiagnosa1 = new widget.TextBox();
        BtnDiagnosa1 = new widget.Button();
        LabelPoli8 = new widget.Label();
        KdDiagnosa2 = new widget.TextBox();
        NmDiagnosa2 = new widget.TextBox();
        BtnDiagnosa2 = new widget.Button();
        LabelPoli9 = new widget.Label();
        KdDiagnosa3 = new widget.TextBox();
        NmDiagnosa3 = new widget.TextBox();
        BtnDiagnosa3 = new widget.Button();
        KdPoliInternal = new widget.TextBox();
        NmPoliInternal = new widget.TextBox();
        BtnPoliInternal = new widget.Button();
        jLabel32 = new widget.Label();
        TanggalEstRujuk = new widget.Tanggal();
        LabelPoli12 = new widget.Label();
        KdPPKRujukan = new widget.TextBox();
        NmPPKRujukan = new widget.TextBox();
        BtnPPKRujukan = new widget.Button();
        chkKunjungan = new widget.CekBox();
        ChkInternal = new widget.CekBox();
        ChkRujukLanjut = new widget.CekBox();
        KdSubSpesialis = new widget.TextBox();
        NmSubSpesialis = new widget.TextBox();
        BtnSubSpesialis = new widget.Button();
        LabelPoli10 = new widget.Label();
        KdSarana = new widget.TextBox();
        NmSarana = new widget.TextBox();
        BtnSarana = new widget.Button();
        chkKhusus = new widget.CekBox();
        KdKhusus = new widget.TextBox();
        NmKhusus = new widget.TextBox();
        btnKhusus = new widget.Button();
        BtnSubKhusus = new widget.Button();
        NmSubKhusus = new widget.TextBox();
        KdSubKhusus = new widget.TextBox();
        LabelPoli11 = new widget.Label();
        jLabel33 = new widget.Label();
        CatatanKhusus = new widget.TextBox();
        jLabel34 = new widget.Label();
        TACC = new widget.ComboBox();
        jLabel35 = new widget.Label();
        AlasanTACC = new widget.ComboBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPendaftaran = new widget.Table();
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
        internalFrame5 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbKunjungan = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel42 = new widget.Label();
        DTPCari3 = new widget.Tanggal();
        jLabel43 = new widget.Label();
        DTPCari4 = new widget.Tanggal();
        jLabel9 = new widget.Label();
        TCari1 = new widget.TextBox();
        BtnCari1 = new widget.Button();
        jLabel10 = new widget.Label();
        LCount1 = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        ProviderPeserta.setEditable(false);
        ProviderPeserta.setBackground(new java.awt.Color(245, 250, 240));
        ProviderPeserta.setHighlighter(null);
        ProviderPeserta.setName("ProviderPeserta"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnGelang.setBackground(new java.awt.Color(252, 255, 250));
        MnGelang.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang.setText("Label & Barcode");
        MnGelang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang.setIconTextGap(5);
        MnGelang.setName("MnGelang"); // NOI18N
        MnGelang.setPreferredSize(new java.awt.Dimension(190, 26));
        MnGelang.setRequestFocusEnabled(false);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang1);

        MnGelang2.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang2.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang2);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang3);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang4);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang5);

        MnGelang6.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang6.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnGelang6);

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnLabelTracker3);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 1");
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
        MnGelang.add(MnBarcode1);

        MnBarcode2.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode2.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcode2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode2.setText("Barcode Perawatan 2");
        MnBarcode2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode2.setIconTextGap(5);
        MnBarcode2.setName("MnBarcode2"); // NOI18N
        MnBarcode2.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode2);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(70, 70, 70));
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
        MnGelang.add(MnBarcodeRM9);

        jPopupMenu1.add(MnGelang);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(70, 70, 70));
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
        jPopupMenu1.add(MnPemberianObat);

        MnPemberianObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat1.setForeground(new java.awt.Color(70, 70, 70));
        MnPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat1.setText("Data Pemberian Obat");
        MnPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat1.setIconTextGap(5);
        MnPemberianObat1.setName("MnPemberianObat1"); // NOI18N
        MnPemberianObat1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObat1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat1);

        MnTIndakan.setBackground(new java.awt.Color(255, 255, 254));
        MnTIndakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTIndakan.setForeground(new java.awt.Color(70, 70, 70));
        MnTIndakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTIndakan.setText("Pemberian Tindakan");
        MnTIndakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTIndakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTIndakan.setIconTextGap(5);
        MnTIndakan.setName("MnTIndakan"); // NOI18N
        MnTIndakan.setPreferredSize(new java.awt.Dimension(190, 26));
        MnTIndakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTIndakanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTIndakan);

        MnTIndakan1.setBackground(new java.awt.Color(255, 255, 254));
        MnTIndakan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTIndakan1.setForeground(new java.awt.Color(70, 70, 70));
        MnTIndakan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTIndakan1.setText("Data Pemberian Tindakan");
        MnTIndakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTIndakan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTIndakan1.setIconTextGap(5);
        MnTIndakan1.setName("MnTIndakan1"); // NOI18N
        MnTIndakan1.setPreferredSize(new java.awt.Dimension(190, 26));
        MnTIndakan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTIndakan1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTIndakan1);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setIconTextGap(5);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(190, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        ppBuktiKunjungan.setBackground(new java.awt.Color(255, 255, 254));
        ppBuktiKunjungan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBuktiKunjungan.setForeground(new java.awt.Color(70, 70, 70));
        ppBuktiKunjungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBuktiKunjungan.setText("Bukti Kunjungan");
        ppBuktiKunjungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBuktiKunjungan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBuktiKunjungan.setIconTextGap(5);
        ppBuktiKunjungan.setName("ppBuktiKunjungan"); // NOI18N
        ppBuktiKunjungan.setPreferredSize(new java.awt.Dimension(190, 26));
        ppBuktiKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBuktiKunjunganBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBuktiKunjungan);

        tanggal.setEditable(false);
        tanggal.setForeground(new java.awt.Color(50, 70, 50));
        tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019 10:37:43" }));
        tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tanggal.setName("tanggal"); // NOI18N
        tanggal.setOpaque(false);
        tanggal.setPreferredSize(new java.awt.Dimension(95, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pendaftaran PCare ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
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
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 12, 152, 23);

        TPasien.setEditable(false);
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
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(93, 72, 152, 23);

        jLabel22.setText("Tgl.Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 102, 90, 23);

        TanggalDaftar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        TanggalDaftar.setDisplayFormat("dd-MM-yyyy");
        TanggalDaftar.setName("TanggalDaftar"); // NOI18N
        TanggalDaftar.setOpaque(false);
        TanggalDaftar.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalDaftarKeyPressed(evt);
            }
        });
        FormInput.add(TanggalDaftar);
        TanggalDaftar.setBounds(93, 102, 95, 23);

        jLabel14.setText("Keluhan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(384, 132, 80, 23);

        Keluhan.setHighlighter(null);
        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        FormInput.add(Keluhan);
        Keluhan.setBounds(467, 132, 260, 23);

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
        jLabel25.setBounds(278, 72, 60, 23);

        Status.setEditable(false);
        Status.setBackground(new java.awt.Color(245, 250, 240));
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(341, 72, 150, 23);

        jLabel27.setText("Jenis Kunjungan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(200, 102, 108, 23);

        JenisKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kunjungan Sakit", "Kunjungan Sehat" }));
        JenisKunjungan.setName("JenisKunjungan"); // NOI18N
        JenisKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(JenisKunjungan);
        JenisKunjungan.setBounds(311, 102, 150, 23);

        Perawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10 Rawat Jalan", "20 Rawat Inap", "50 Promotif Preventif" }));
        Perawatan.setName("Perawatan"); // NOI18N
        Perawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanKeyPressed(evt);
            }
        });
        FormInput.add(Perawatan);
        Perawatan.setBounds(547, 102, 180, 23);

        jLabel28.setText("Perawatan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(474, 102, 70, 23);

        LabelPoli.setText("Pemeriksaan Fisik :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormInput.add(LabelPoli);
        LabelPoli.setBounds(0, 162, 110, 23);

        KdPoliTujuan.setEditable(false);
        KdPoliTujuan.setBackground(new java.awt.Color(245, 250, 240));
        KdPoliTujuan.setHighlighter(null);
        KdPoliTujuan.setName("KdPoliTujuan"); // NOI18N
        KdPoliTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPoliTujuanActionPerformed(evt);
            }
        });
        FormInput.add(KdPoliTujuan);
        KdPoliTujuan.setBounds(93, 132, 60, 23);

        NmPoliTujuan.setEditable(false);
        NmPoliTujuan.setBackground(new java.awt.Color(245, 250, 240));
        NmPoliTujuan.setHighlighter(null);
        NmPoliTujuan.setName("NmPoliTujuan"); // NOI18N
        NmPoliTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPoliTujuanActionPerformed(evt);
            }
        });
        FormInput.add(NmPoliTujuan);
        NmPoliTujuan.setBounds(155, 132, 195, 23);

        btnPoliTujuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliTujuan.setMnemonic('X');
        btnPoliTujuan.setToolTipText("Alt+X");
        btnPoliTujuan.setName("btnPoliTujuan"); // NOI18N
        btnPoliTujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliTujuanActionPerformed(evt);
            }
        });
        btnPoliTujuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliTujuanKeyPressed(evt);
            }
        });
        FormInput.add(btnPoliTujuan);
        btnPoliTujuan.setBounds(350, 132, 28, 23);

        LabelPoli2.setText("Poli Tujuan :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormInput.add(LabelPoli2);
        LabelPoli2.setBounds(0, 132, 90, 23);

        jLabel15.setText("Tinggi Badan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(37, 182, 90, 23);

        TinggiBadan.setText("0");
        TinggiBadan.setHighlighter(null);
        TinggiBadan.setName("TinggiBadan"); // NOI18N
        TinggiBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiBadanKeyPressed(evt);
            }
        });
        FormInput.add(TinggiBadan);
        TinggiBadan.setBounds(130, 182, 60, 23);

        BeratBadan.setText("0");
        BeratBadan.setHighlighter(null);
        BeratBadan.setName("BeratBadan"); // NOI18N
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        FormInput.add(BeratBadan);
        BeratBadan.setBounds(130, 212, 60, 23);

        jLabel16.setText("Berat Badan :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(37, 212, 90, 23);

        LabelPoli3.setText("Tekanan Darah :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(210, 162, 110, 23);

        jLabel17.setText("Sistole :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(247, 182, 90, 23);

        Sistole.setText("0");
        Sistole.setHighlighter(null);
        Sistole.setName("Sistole"); // NOI18N
        Sistole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistoleKeyPressed(evt);
            }
        });
        FormInput.add(Sistole);
        Sistole.setBounds(340, 182, 60, 23);

        Diastole.setText("0");
        Diastole.setHighlighter(null);
        Diastole.setName("Diastole"); // NOI18N
        Diastole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiastoleKeyPressed(evt);
            }
        });
        FormInput.add(Diastole);
        Diastole.setBounds(340, 212, 60, 23);

        jLabel20.setText("Diastole :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(247, 212, 90, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(410, 212, 40, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("cm");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(200, 182, 30, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("kg");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(200, 212, 30, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("mmHg");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(410, 182, 40, 23);

        jLabel38.setText("Respiratory Rate :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(464, 162, 110, 23);

        Respiratory.setText("0");
        Respiratory.setHighlighter(null);
        Respiratory.setName("Respiratory"); // NOI18N
        Respiratory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespiratoryKeyPressed(evt);
            }
        });
        FormInput.add(Respiratory);
        Respiratory.setBounds(577, 162, 60, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("per minute");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(650, 162, 80, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("bpm");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(650, 192, 80, 23);

        Heartrate.setText("0");
        Heartrate.setHighlighter(null);
        Heartrate.setName("Heartrate"); // NOI18N
        Heartrate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeartrateKeyPressed(evt);
            }
        });
        FormInput.add(Heartrate);
        Heartrate.setBounds(577, 192, 60, 23);

        jLabel41.setText("Heart Rate :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(464, 192, 110, 23);

        chkSubspesialis.setText("Subspesialis :");
        chkSubspesialis.setEnabled(false);
        chkSubspesialis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkSubspesialis.setName("chkSubspesialis"); // NOI18N
        chkSubspesialis.setOpaque(false);
        chkSubspesialis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSubspesialisItemStateChanged(evt);
            }
        });
        FormInput.add(chkSubspesialis);
        chkSubspesialis.setBounds(7, 440, 120, 23);

        jLabel26.setText("Tgl.Kunjungan :");
        jLabel26.setName("jLabel26"); // NOI18N
        jLabel26.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel26);
        jLabel26.setBounds(30, 270, 97, 23);

        TanggalKunjungan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        TanggalKunjungan.setDisplayFormat("dd-MM-yyyy");
        TanggalKunjungan.setEnabled(false);
        TanggalKunjungan.setName("TanggalKunjungan"); // NOI18N
        TanggalKunjungan.setOpaque(false);
        TanggalKunjungan.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKunjungan);
        TanggalKunjungan.setBounds(130, 270, 90, 23);

        LabelPoli4.setText("Kesadaran :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(30, 300, 97, 23);

        KdSadar.setEditable(false);
        KdSadar.setBackground(new java.awt.Color(245, 250, 240));
        KdSadar.setHighlighter(null);
        KdSadar.setName("KdSadar"); // NOI18N
        FormInput.add(KdSadar);
        KdSadar.setBounds(130, 300, 50, 23);

        NmSadar.setEditable(false);
        NmSadar.setBackground(new java.awt.Color(245, 250, 240));
        NmSadar.setHighlighter(null);
        NmSadar.setName("NmSadar"); // NOI18N
        FormInput.add(NmSadar);
        NmSadar.setBounds(182, 300, 170, 23);

        BtnKesadaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKesadaran.setMnemonic('X');
        BtnKesadaran.setToolTipText("Alt+X");
        BtnKesadaran.setEnabled(false);
        BtnKesadaran.setName("BtnKesadaran"); // NOI18N
        BtnKesadaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKesadaranActionPerformed(evt);
            }
        });
        BtnKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKesadaranKeyPressed(evt);
            }
        });
        FormInput.add(BtnKesadaran);
        BtnKesadaran.setBounds(354, 300, 28, 23);

        jLabel30.setText("Terapi :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(30, 330, 97, 23);

        Terapi.setEnabled(false);
        Terapi.setHighlighter(null);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        FormInput.add(Terapi);
        Terapi.setBounds(130, 330, 252, 23);

        LabelPoli5.setText("Status Pulang :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(30, 360, 97, 23);

        KdStatusPulang.setEditable(false);
        KdStatusPulang.setBackground(new java.awt.Color(245, 250, 240));
        KdStatusPulang.setHighlighter(null);
        KdStatusPulang.setName("KdStatusPulang"); // NOI18N
        FormInput.add(KdStatusPulang);
        KdStatusPulang.setBounds(130, 360, 50, 23);

        NmStatusPulang.setEditable(false);
        NmStatusPulang.setBackground(new java.awt.Color(245, 250, 240));
        NmStatusPulang.setHighlighter(null);
        NmStatusPulang.setName("NmStatusPulang"); // NOI18N
        FormInput.add(NmStatusPulang);
        NmStatusPulang.setBounds(182, 360, 170, 23);

        BtnStatusPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusPulang.setMnemonic('X');
        BtnStatusPulang.setToolTipText("Alt+X");
        BtnStatusPulang.setEnabled(false);
        BtnStatusPulang.setName("BtnStatusPulang"); // NOI18N
        BtnStatusPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusPulangActionPerformed(evt);
            }
        });
        BtnStatusPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusPulangKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusPulang);
        BtnStatusPulang.setBounds(354, 360, 28, 23);

        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy");
        TanggalPulang.setEnabled(false);
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPulangKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPulang);
        TanggalPulang.setBounds(293, 270, 90, 23);

        jLabel31.setText("Tgl.Pulang :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel31);
        jLabel31.setBounds(220, 270, 70, 23);

        LabelPoli6.setText("Tenaga Medis :");
        LabelPoli6.setName("LabelPoli6"); // NOI18N
        FormInput.add(LabelPoli6);
        LabelPoli6.setBounds(380, 270, 94, 23);

        KdTenagaMedis.setEditable(false);
        KdTenagaMedis.setBackground(new java.awt.Color(245, 250, 240));
        KdTenagaMedis.setHighlighter(null);
        KdTenagaMedis.setName("KdTenagaMedis"); // NOI18N
        FormInput.add(KdTenagaMedis);
        KdTenagaMedis.setBounds(477, 270, 50, 23);

        NmTenagaMedis.setEditable(false);
        NmTenagaMedis.setBackground(new java.awt.Color(245, 250, 240));
        NmTenagaMedis.setHighlighter(null);
        NmTenagaMedis.setName("NmTenagaMedis"); // NOI18N
        FormInput.add(NmTenagaMedis);
        NmTenagaMedis.setBounds(528, 270, 170, 23);

        BtnTenagaMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnTenagaMedis.setMnemonic('X');
        BtnTenagaMedis.setToolTipText("Alt+X");
        BtnTenagaMedis.setEnabled(false);
        BtnTenagaMedis.setName("BtnTenagaMedis"); // NOI18N
        BtnTenagaMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTenagaMedisActionPerformed(evt);
            }
        });
        BtnTenagaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTenagaMedisKeyPressed(evt);
            }
        });
        FormInput.add(BtnTenagaMedis);
        BtnTenagaMedis.setBounds(700, 270, 28, 23);

        LabelPoli7.setText("Diganosa 1 :");
        LabelPoli7.setName("LabelPoli7"); // NOI18N
        FormInput.add(LabelPoli7);
        LabelPoli7.setBounds(380, 300, 94, 23);

        KdDiagnosa1.setEditable(false);
        KdDiagnosa1.setBackground(new java.awt.Color(245, 250, 240));
        KdDiagnosa1.setHighlighter(null);
        KdDiagnosa1.setName("KdDiagnosa1"); // NOI18N
        FormInput.add(KdDiagnosa1);
        KdDiagnosa1.setBounds(477, 300, 50, 23);

        NmDiagnosa1.setEditable(false);
        NmDiagnosa1.setBackground(new java.awt.Color(245, 250, 240));
        NmDiagnosa1.setHighlighter(null);
        NmDiagnosa1.setName("NmDiagnosa1"); // NOI18N
        FormInput.add(NmDiagnosa1);
        NmDiagnosa1.setBounds(528, 300, 170, 23);

        BtnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDiagnosa1.setMnemonic('X');
        BtnDiagnosa1.setToolTipText("Alt+X");
        BtnDiagnosa1.setEnabled(false);
        BtnDiagnosa1.setName("BtnDiagnosa1"); // NOI18N
        BtnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosa1ActionPerformed(evt);
            }
        });
        BtnDiagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDiagnosa1KeyPressed(evt);
            }
        });
        FormInput.add(BtnDiagnosa1);
        BtnDiagnosa1.setBounds(700, 300, 28, 23);

        LabelPoli8.setText("Diganosa 2 :");
        LabelPoli8.setName("LabelPoli8"); // NOI18N
        FormInput.add(LabelPoli8);
        LabelPoli8.setBounds(380, 330, 94, 23);

        KdDiagnosa2.setEditable(false);
        KdDiagnosa2.setBackground(new java.awt.Color(245, 250, 240));
        KdDiagnosa2.setHighlighter(null);
        KdDiagnosa2.setName("KdDiagnosa2"); // NOI18N
        FormInput.add(KdDiagnosa2);
        KdDiagnosa2.setBounds(477, 330, 50, 23);

        NmDiagnosa2.setEditable(false);
        NmDiagnosa2.setBackground(new java.awt.Color(245, 250, 240));
        NmDiagnosa2.setHighlighter(null);
        NmDiagnosa2.setName("NmDiagnosa2"); // NOI18N
        FormInput.add(NmDiagnosa2);
        NmDiagnosa2.setBounds(528, 330, 170, 23);

        BtnDiagnosa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDiagnosa2.setMnemonic('X');
        BtnDiagnosa2.setToolTipText("Alt+X");
        BtnDiagnosa2.setEnabled(false);
        BtnDiagnosa2.setName("BtnDiagnosa2"); // NOI18N
        BtnDiagnosa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosa2ActionPerformed(evt);
            }
        });
        BtnDiagnosa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDiagnosa2KeyPressed(evt);
            }
        });
        FormInput.add(BtnDiagnosa2);
        BtnDiagnosa2.setBounds(700, 330, 28, 23);

        LabelPoli9.setText("Diganosa 3 :");
        LabelPoli9.setName("LabelPoli9"); // NOI18N
        FormInput.add(LabelPoli9);
        LabelPoli9.setBounds(380, 360, 94, 23);

        KdDiagnosa3.setEditable(false);
        KdDiagnosa3.setBackground(new java.awt.Color(245, 250, 240));
        KdDiagnosa3.setHighlighter(null);
        KdDiagnosa3.setName("KdDiagnosa3"); // NOI18N
        FormInput.add(KdDiagnosa3);
        KdDiagnosa3.setBounds(477, 360, 50, 23);

        NmDiagnosa3.setEditable(false);
        NmDiagnosa3.setBackground(new java.awt.Color(245, 250, 240));
        NmDiagnosa3.setHighlighter(null);
        NmDiagnosa3.setName("NmDiagnosa3"); // NOI18N
        FormInput.add(NmDiagnosa3);
        NmDiagnosa3.setBounds(528, 360, 170, 23);

        BtnDiagnosa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDiagnosa3.setMnemonic('X');
        BtnDiagnosa3.setToolTipText("Alt+X");
        BtnDiagnosa3.setEnabled(false);
        BtnDiagnosa3.setName("BtnDiagnosa3"); // NOI18N
        BtnDiagnosa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosa3ActionPerformed(evt);
            }
        });
        BtnDiagnosa3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDiagnosa3KeyPressed(evt);
            }
        });
        FormInput.add(BtnDiagnosa3);
        BtnDiagnosa3.setBounds(700, 360, 28, 23);

        KdPoliInternal.setEditable(false);
        KdPoliInternal.setBackground(new java.awt.Color(245, 250, 240));
        KdPoliInternal.setHighlighter(null);
        KdPoliInternal.setName("KdPoliInternal"); // NOI18N
        FormInput.add(KdPoliInternal);
        KdPoliInternal.setBounds(130, 500, 50, 23);

        NmPoliInternal.setEditable(false);
        NmPoliInternal.setBackground(new java.awt.Color(245, 250, 240));
        NmPoliInternal.setHighlighter(null);
        NmPoliInternal.setName("NmPoliInternal"); // NOI18N
        FormInput.add(NmPoliInternal);
        NmPoliInternal.setBounds(182, 500, 170, 23);

        BtnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoliInternal.setMnemonic('X');
        BtnPoliInternal.setToolTipText("Alt+X");
        BtnPoliInternal.setEnabled(false);
        BtnPoliInternal.setName("BtnPoliInternal"); // NOI18N
        BtnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliInternalActionPerformed(evt);
            }
        });
        BtnPoliInternal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoliInternalKeyPressed(evt);
            }
        });
        FormInput.add(BtnPoliInternal);
        BtnPoliInternal.setBounds(354, 500, 28, 23);

        jLabel32.setText("Tgl.Est Rujukan :");
        jLabel32.setName("jLabel32"); // NOI18N
        jLabel32.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel32);
        jLabel32.setBounds(90, 410, 95, 23);

        TanggalEstRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalEstRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        TanggalEstRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalEstRujuk.setEnabled(false);
        TanggalEstRujuk.setName("TanggalEstRujuk"); // NOI18N
        TanggalEstRujuk.setOpaque(false);
        TanggalEstRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalEstRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalEstRujukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalEstRujuk);
        TanggalEstRujuk.setBounds(188, 410, 90, 23);

        LabelPoli12.setText("PPK Rujukan :");
        LabelPoli12.setName("LabelPoli12"); // NOI18N
        FormInput.add(LabelPoli12);
        LabelPoli12.setBounds(290, 410, 80, 23);

        KdPPKRujukan.setEditable(false);
        KdPPKRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPPKRujukan.setHighlighter(null);
        KdPPKRujukan.setName("KdPPKRujukan"); // NOI18N
        KdPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPoliTujuanActionPerformed(evt);
            }
        });
        FormInput.add(KdPPKRujukan);
        KdPPKRujukan.setBounds(373, 410, 90, 23);

        NmPPKRujukan.setEditable(false);
        NmPPKRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPPKRujukan.setHighlighter(null);
        NmPPKRujukan.setName("NmPPKRujukan"); // NOI18N
        NmPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdPoliTujuanActionPerformed(evt);
            }
        });
        FormInput.add(NmPPKRujukan);
        NmPPKRujukan.setBounds(465, 410, 233, 23);

        BtnPPKRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPPKRujukan.setMnemonic('X');
        BtnPPKRujukan.setToolTipText("Alt+X");
        BtnPPKRujukan.setEnabled(false);
        BtnPPKRujukan.setName("BtnPPKRujukan"); // NOI18N
        BtnPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPPKRujukanActionPerformed(evt);
            }
        });
        BtnPPKRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPPKRujukanKeyPressed(evt);
            }
        });
        FormInput.add(BtnPPKRujukan);
        BtnPPKRujukan.setBounds(700, 410, 28, 23);

        chkKunjungan.setText("Kunjungan :");
        chkKunjungan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkKunjungan.setName("chkKunjungan"); // NOI18N
        chkKunjungan.setOpaque(false);
        chkKunjungan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkKunjunganItemStateChanged(evt);
            }
        });
        chkKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(chkKunjungan);
        chkKunjungan.setBounds(0, 250, 90, 23);

        ChkInternal.setText("Internal :");
        ChkInternal.setEnabled(false);
        ChkInternal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkInternal.setName("ChkInternal"); // NOI18N
        ChkInternal.setOpaque(false);
        ChkInternal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkInternalItemStateChanged(evt);
            }
        });
        FormInput.add(ChkInternal);
        ChkInternal.setBounds(7, 500, 120, 23);

        ChkRujukLanjut.setText("Rujuk Lanjut :");
        ChkRujukLanjut.setEnabled(false);
        ChkRujukLanjut.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkRujukLanjut.setName("ChkRujukLanjut"); // NOI18N
        ChkRujukLanjut.setOpaque(false);
        ChkRujukLanjut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRujukLanjutItemStateChanged(evt);
            }
        });
        FormInput.add(ChkRujukLanjut);
        ChkRujukLanjut.setBounds(0, 410, 100, 23);

        KdSubSpesialis.setEditable(false);
        KdSubSpesialis.setBackground(new java.awt.Color(245, 250, 240));
        KdSubSpesialis.setHighlighter(null);
        KdSubSpesialis.setName("KdSubSpesialis"); // NOI18N
        FormInput.add(KdSubSpesialis);
        KdSubSpesialis.setBounds(130, 440, 50, 23);

        NmSubSpesialis.setEditable(false);
        NmSubSpesialis.setBackground(new java.awt.Color(245, 250, 240));
        NmSubSpesialis.setHighlighter(null);
        NmSubSpesialis.setName("NmSubSpesialis"); // NOI18N
        FormInput.add(NmSubSpesialis);
        NmSubSpesialis.setBounds(182, 440, 170, 23);

        BtnSubSpesialis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSubSpesialis.setMnemonic('X');
        BtnSubSpesialis.setToolTipText("Alt+X");
        BtnSubSpesialis.setEnabled(false);
        BtnSubSpesialis.setName("BtnSubSpesialis"); // NOI18N
        BtnSubSpesialis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSubSpesialisActionPerformed(evt);
            }
        });
        BtnSubSpesialis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSubSpesialisKeyPressed(evt);
            }
        });
        FormInput.add(BtnSubSpesialis);
        BtnSubSpesialis.setBounds(354, 440, 28, 23);

        LabelPoli10.setText("Sarana :");
        LabelPoli10.setName("LabelPoli10"); // NOI18N
        FormInput.add(LabelPoli10);
        LabelPoli10.setBounds(7, 470, 120, 23);

        KdSarana.setEditable(false);
        KdSarana.setBackground(new java.awt.Color(245, 250, 240));
        KdSarana.setHighlighter(null);
        KdSarana.setName("KdSarana"); // NOI18N
        FormInput.add(KdSarana);
        KdSarana.setBounds(130, 470, 50, 23);

        NmSarana.setEditable(false);
        NmSarana.setBackground(new java.awt.Color(245, 250, 240));
        NmSarana.setHighlighter(null);
        NmSarana.setName("NmSarana"); // NOI18N
        FormInput.add(NmSarana);
        NmSarana.setBounds(182, 470, 170, 23);

        BtnSarana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSarana.setMnemonic('X');
        BtnSarana.setToolTipText("Alt+X");
        BtnSarana.setEnabled(false);
        BtnSarana.setName("BtnSarana"); // NOI18N
        BtnSarana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSaranaActionPerformed(evt);
            }
        });
        BtnSarana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSaranaKeyPressed(evt);
            }
        });
        FormInput.add(BtnSarana);
        BtnSarana.setBounds(354, 470, 28, 23);

        chkKhusus.setText("Khusus :");
        chkKhusus.setEnabled(false);
        chkKhusus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkKhusus.setName("chkKhusus"); // NOI18N
        chkKhusus.setOpaque(false);
        chkKhusus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkKhususItemStateChanged(evt);
            }
        });
        FormInput.add(chkKhusus);
        chkKhusus.setBounds(380, 440, 95, 23);

        KdKhusus.setEditable(false);
        KdKhusus.setBackground(new java.awt.Color(245, 250, 240));
        KdKhusus.setHighlighter(null);
        KdKhusus.setName("KdKhusus"); // NOI18N
        FormInput.add(KdKhusus);
        KdKhusus.setBounds(477, 440, 50, 23);

        NmKhusus.setEditable(false);
        NmKhusus.setBackground(new java.awt.Color(245, 250, 240));
        NmKhusus.setHighlighter(null);
        NmKhusus.setName("NmKhusus"); // NOI18N
        FormInput.add(NmKhusus);
        NmKhusus.setBounds(528, 440, 170, 23);

        btnKhusus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKhusus.setMnemonic('X');
        btnKhusus.setToolTipText("Alt+X");
        btnKhusus.setEnabled(false);
        btnKhusus.setName("btnKhusus"); // NOI18N
        btnKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhususActionPerformed(evt);
            }
        });
        btnKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKhususKeyPressed(evt);
            }
        });
        FormInput.add(btnKhusus);
        btnKhusus.setBounds(700, 440, 28, 23);

        BtnSubKhusus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSubKhusus.setMnemonic('X');
        BtnSubKhusus.setToolTipText("Alt+X");
        BtnSubKhusus.setEnabled(false);
        BtnSubKhusus.setName("BtnSubKhusus"); // NOI18N
        BtnSubKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSubKhususActionPerformed(evt);
            }
        });
        BtnSubKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSubKhususKeyPressed(evt);
            }
        });
        FormInput.add(BtnSubKhusus);
        BtnSubKhusus.setBounds(700, 470, 28, 23);

        NmSubKhusus.setEditable(false);
        NmSubKhusus.setBackground(new java.awt.Color(245, 250, 240));
        NmSubKhusus.setHighlighter(null);
        NmSubKhusus.setName("NmSubKhusus"); // NOI18N
        FormInput.add(NmSubKhusus);
        NmSubKhusus.setBounds(528, 470, 170, 23);

        KdSubKhusus.setEditable(false);
        KdSubKhusus.setBackground(new java.awt.Color(245, 250, 240));
        KdSubKhusus.setHighlighter(null);
        KdSubKhusus.setName("KdSubKhusus"); // NOI18N
        FormInput.add(KdSubKhusus);
        KdSubKhusus.setBounds(477, 470, 50, 23);

        LabelPoli11.setText("Subspesilis :");
        LabelPoli11.setName("LabelPoli11"); // NOI18N
        FormInput.add(LabelPoli11);
        LabelPoli11.setBounds(380, 470, 94, 23);

        jLabel33.setText("Catatan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(380, 500, 94, 23);

        CatatanKhusus.setEnabled(false);
        CatatanKhusus.setHighlighter(null);
        CatatanKhusus.setName("CatatanKhusus"); // NOI18N
        CatatanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKhususKeyPressed(evt);
            }
        });
        FormInput.add(CatatanKhusus);
        CatatanKhusus.setBounds(477, 500, 251, 23);

        jLabel34.setText("TACC :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 530, 99, 23);

        TACC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 Tanpa TACC", "1 Time", "2 Age", "3 Complication", "4 Comorbidity" }));
        TACC.setEnabled(false);
        TACC.setName("TACC"); // NOI18N
        TACC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TACCItemStateChanged(evt);
            }
        });
        TACC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TACCKeyPressed(evt);
            }
        });
        FormInput.add(TACC);
        TACC.setBounds(103, 530, 200, 23);

        jLabel35.setText("Alasan TACC :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(320, 530, 80, 23);

        AlasanTACC.setEnabled(false);
        AlasanTACC.setName("AlasanTACC"); // NOI18N
        AlasanTACC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanTACCKeyPressed(evt);
            }
        });
        FormInput.add(AlasanTACC);
        AlasanTACC.setBounds(403, 530, 325, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pendaftaran & Kunjungan", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPendaftaran.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPendaftaran.setName("tbPendaftaran"); // NOI18N
        tbPendaftaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPendaftaranMouseClicked(evt);
            }
        });
        tbPendaftaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPendaftaranKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPendaftaran);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Daftar :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        TabRawat.addTab("Data Pendaftaran", internalFrame4);

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbKunjungan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKunjungan.setComponentPopupMenu(jPopupMenu1);
        tbKunjungan.setName("tbKunjungan"); // NOI18N
        tbKunjungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKunjunganMouseClicked(evt);
            }
        });
        tbKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKunjunganKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbKunjungan);

        internalFrame5.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel42.setText("Tgl.Kunjungan :");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass10.add(jLabel42);

        DTPCari3.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        DTPCari3.setDisplayFormat("dd-MM-yyyy");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
        DTPCari3.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari3);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("s.d.");
        jLabel43.setName("jLabel43"); // NOI18N
        jLabel43.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel43);

        DTPCari4.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        DTPCari4.setDisplayFormat("dd-MM-yyyy");
        DTPCari4.setName("DTPCari4"); // NOI18N
        DTPCari4.setOpaque(false);
        DTPCari4.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari4);

        jLabel9.setText("Key Word :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel9);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(175, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelGlass10.add(TCari1);

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
        panelGlass10.add(BtnCari1);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel10);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount1);

        internalFrame5.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Kunjungan", internalFrame5);

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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NoKartu.getText().trim().equals("")){
            Valid.textKosong(NoKartu,"No.Kartu");
        }else if(KdPoliTujuan.getText().trim().equals("")||NmPoliTujuan.getText().trim().equals("")){
            Valid.textKosong(btnPoliTujuan,"Poli Tujuan");
        }else if(Keluhan.getText().trim().equals("")){
            Valid.textKosong(Keluhan,"Keluhan");
        }else if(TinggiBadan.getText().trim().equals("")||TinggiBadan.getText().trim().equals("0")){
            Valid.textKosong(TinggiBadan,"Tinggi Badan");
        }else if(BeratBadan.getText().trim().equals("")||BeratBadan.getText().trim().equals("0")){
            Valid.textKosong(BeratBadan,"Berat Badan");
        }else if(Sistole.getText().trim().equals("")||Sistole.getText().trim().equals("0")){
            Valid.textKosong(Sistole,"Sistole");
        }else if(Diastole.getText().trim().equals("")||Diastole.getText().trim().equals("0")){
            Valid.textKosong(Diastole,"Diastole");
        }else if(Respiratory.getText().trim().equals("")||Respiratory.getText().trim().equals("0")){
            Valid.textKosong(Respiratory,"Respiratory Rate");
        }else if(Heartrate.getText().trim().equals("")||Heartrate.getText().trim().equals("0")){
            Valid.textKosong(Heartrate,"Heart Rate");
        }else if((chkKunjungan.isSelected()==true)&&(NmSadar.getText().equals(""))){
            Valid.textKosong(BtnKesadaran,"Kesadaran");
        }else if((chkKunjungan.isSelected()==true)&&(Terapi.getText().equals(""))){
            Valid.textKosong(Terapi,"Terapi");
        }else if((chkKunjungan.isSelected()==true)&&(NmStatusPulang.getText().equals(""))){
            Valid.textKosong(BtnStatusPulang,"Status Pulang");
        }else if((chkKunjungan.isSelected()==true)&&(NmTenagaMedis.getText().equals(""))){
            Valid.textKosong(BtnTenagaMedis,"Tenaga Medis");
        }else if((chkKunjungan.isSelected()==true)&&(NmDiagnosa1.getText().equals(""))){
            Valid.textKosong(BtnDiagnosa1,"Diagnosa 1");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from pcare_pendaftaran where no_rawat=?",TNoRw.getText())==0){
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                    kunjungansakit="true";
                    if(JenisKunjungan.getSelectedItem().toString().equals("Kunjungan Sehat")){
                        kunjungansakit="false";
                    }
                    requestJson ="{" +
                                    "\"kdProviderPeserta\": \""+ProviderPeserta.getText()+"\"," +
                                    "\"tglDaftar\": \""+TanggalDaftar.getSelectedItem()+"\"," +
                                    "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                    "\"kdPoli\": \""+KdPoliTujuan.getText()+"\"," +
                                    "\"keluhan\": \""+Keluhan.getText()+"\"," +
                                    "\"kunjSakit\": "+kunjungansakit+"," +
                                    "\"sistole\": "+Sistole.getText()+"," +
                                    "\"diastole\": "+Diastole.getText()+"," +
                                    "\"beratBadan\": "+BeratBadan.getText()+"," +
                                    "\"tinggiBadan\": "+TinggiBadan.getText()+"," +
                                    "\"respRate\": "+Respiratory.getText()+"," +
                                    "\"heartRate\": "+Heartrate.getText()+"," +
                                    "\"rujukBalik\": 0," +
                                    "\"kdTkp\": \""+Perawatan.getSelectedItem().toString().substring(0,2)+"\"" +
                                 "}";
                    System.out.println(requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);
                    requestJson=api.getRest().exchange(URL+"/pendaftaran", HttpMethod.POST, requestEntity, String.class).getBody();
                    System.out.println(requestJson);
                    root = mapper.readTree(requestJson);
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("201")){
                        response = root.path("response").path("message");
                        System.out.println("noUrut : "+response.asText());
                        if(Sequel.menyimpantf("pcare_pendaftaran","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Urut",19,new String[]{
                            TNoRw.getText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),TNoRM.getText(),TPasien.getText(),ProviderPeserta.getText(),
                            NoKartu.getText(),KdPoliTujuan.getText(),NmPoliTujuan.getText(),Keluhan.getText(),JenisKunjungan.getSelectedItem().toString(),
                            Sistole.getText(),Diastole.getText(),BeratBadan.getText(),TinggiBadan.getText(),Respiratory.getText(),Heartrate.getText(),"0",
                            Perawatan.getSelectedItem().toString(),response.asText()
                        })==true){  
                            Sequel.menyimpan2("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",15,new String[]{
                                TNoRw.getText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),Sequel.cariIsi("select current_time()"),
                                "",Sistole.getText()+"/"+Diastole.getText(),Heartrate.getText(),Respiratory.getText(),TinggiBadan.getText(), 
                                BeratBadan.getText(),"", Keluhan.getText(),"","","-",""
                            });                        
                            simpanKunjungan();
                            emptTeks();
                        }                     
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                    }else if(ex.toString().contains("401")){
                        JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                    }else if(ex.toString().contains("408")){
                        JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                    }else if(ex.toString().contains("424")){
                        JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                    }else if(ex.toString().contains("412")){
                        JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                    }else if(ex.toString().contains("204")){
                        JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                    }
                } 
            }else{
                if(Sequel.cariInteger("select count(no_rawat) from pcare_kunjungan_umum where no_rawat=?",TNoRw.getText())==0){
                    simpanKunjungan();
                    emptTeks();
                }
            }
                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        
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
            if(tbPendaftaran.getSelectedRow()!= -1){
                try {
                    bodyWithDeleteRequest();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);                    
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            } 
        }else if(TabRawat.getSelectedIndex()==2){
            if(tbKunjungan.getSelectedRow()!= -1){
                try {
                    bodyWithDeleteRequest3();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);                    
                }
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
        if(pilihan==1){
            if(tbPendaftaran.getSelectedRow()!= -1){
                if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                    Valid.textKosong(TNoRw,"Pasien");
                }else if(NoKartu.getText().trim().equals("")){
                    Valid.textKosong(NoKartu,"No.Kartu");
                }else if(KdPoliTujuan.getText().trim().equals("")||NmPoliTujuan.getText().trim().equals("")){
                    Valid.textKosong(btnPoliTujuan,"Poli Tujuan");
                }else if(Keluhan.getText().trim().equals("")){
                    Valid.textKosong(Keluhan,"Keluhan");
                }else if(TinggiBadan.getText().trim().equals("")){
                    Valid.textKosong(TinggiBadan,"Tinggi Badan");
                }else if(BeratBadan.getText().trim().equals("")){
                    Valid.textKosong(BeratBadan,"Berat Badan");
                }else if(Sistole.getText().trim().equals("")){
                    Valid.textKosong(Sistole,"Sistole");
                }else if(Diastole.getText().trim().equals("")){
                    Valid.textKosong(Diastole,"Diastole");
                }else if(Respiratory.getText().trim().equals("")){
                    Valid.textKosong(Respiratory,"Respiratory Rate");
                }else if(Heartrate.getText().trim().equals("")){
                    Valid.textKosong(Heartrate,"Heart Rate");
                }else{
                    try {
                        bodyWithDeleteRequest2();                    
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                        headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                        headers.add("X-Signature",api.getHmac());
                        headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                        kunjungansakit="true";
                        if(JenisKunjungan.getSelectedItem().toString().equals("Kunjungan Sehat")){
                            kunjungansakit="false";
                        }
                        requestJson ="{" +
                                        "\"kdProviderPeserta\": \""+ProviderPeserta.getText()+"\"," +
                                        "\"tglDaftar\": \""+TanggalDaftar.getSelectedItem()+"\"," +
                                        "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                        "\"kdPoli\": \""+KdPoliTujuan.getText()+"\"," +
                                        "\"keluhan\": \""+Keluhan.getText()+"\"," +
                                        "\"kunjSakit\": "+kunjungansakit+"," +
                                        "\"sistole\": "+Sistole.getText()+"," +
                                        "\"diastole\": "+Diastole.getText()+"," +
                                        "\"beratBadan\": "+BeratBadan.getText()+"," +
                                        "\"tinggiBadan\": "+TinggiBadan.getText()+"," +
                                        "\"respRate\": "+Respiratory.getText()+"," +
                                        "\"heartRate\": "+Heartrate.getText()+"," +
                                        "\"rujukBalik\": 0," +
                                        "\"kdTkp\": \""+Perawatan.getSelectedItem().toString().substring(0,2)+"\"" +
                                     "}";
                        System.out.println(requestJson);
                        requestEntity = new HttpEntity(requestJson,headers);
                        requestJson=api.getRest().exchange(URL+"/pendaftaran", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println(requestJson);
                        root = mapper.readTree(requestJson);
                        nameNode = root.path("metaData");
                        System.out.println("code : "+nameNode.path("code").asText());
                        System.out.println("message : "+nameNode.path("message").asText());
                        if(nameNode.path("code").asText().equals("201")){
                            response = root.path("response").path("message");
                            System.out.println("noUrut : "+response.asText());
                            if(Sequel.menyimpantf("pcare_pendaftaran","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Urut",19,new String[]{
                                TNoRw.getText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),TNoRM.getText(),TPasien.getText(),ProviderPeserta.getText(),
                                NoKartu.getText(),KdPoliTujuan.getText(),NmPoliTujuan.getText(),Keluhan.getText(),JenisKunjungan.getSelectedItem().toString(),
                                Sistole.getText(),Diastole.getText(),BeratBadan.getText(),TinggiBadan.getText(),Respiratory.getText(),Heartrate.getText(),"0",
                                Perawatan.getSelectedItem().toString(),response.asText()
                            })==true){  
                                Sequel.menyimpan2("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",15,new String[]{
                                    TNoRw.getText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),Sequel.cariIsi("select current_time()"),
                                    "",Sistole.getText()+"/"+Diastole.getText(),Heartrate.getText(),Respiratory.getText(),TinggiBadan.getText(), 
                                    BeratBadan.getText(),"", Keluhan.getText(),"","","-",""
                                });
                                emptTeks();   
                            }                     
                        }else{
                            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                        }
                    }catch (Exception ex) {
                        System.out.println("Notifikasi Bridging : "+ex);
                        if(ex.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                        }else if(ex.toString().contains("500")){
                            JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                        }else if(ex.toString().contains("401")){
                            JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                        }else if(ex.toString().contains("408")){
                            JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                        }else if(ex.toString().contains("424")){
                            JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                        }else if(ex.toString().contains("412")){
                            JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                        }else if(ex.toString().contains("204")){
                            JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                        }
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            }    
        }else if(pilihan==2){
            if(tbKunjungan.getSelectedRow()!= -1){
                if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                    Valid.textKosong(TNoRw,"Pasien");
                }else if(NoKartu.getText().trim().equals("")){
                    Valid.textKosong(NoKartu,"No.Kartu");
                }else if(KdPoliTujuan.getText().trim().equals("")||NmPoliTujuan.getText().trim().equals("")){
                    Valid.textKosong(btnPoliTujuan,"Poli Tujuan");
                }else if(Keluhan.getText().trim().equals("")){
                    Valid.textKosong(Keluhan,"Keluhan");
                }else if(TinggiBadan.getText().trim().equals("")){
                    Valid.textKosong(TinggiBadan,"Tinggi Badan");
                }else if(BeratBadan.getText().trim().equals("")){
                    Valid.textKosong(BeratBadan,"Berat Badan");
                }else if(Sistole.getText().trim().equals("")){
                    Valid.textKosong(Sistole,"Sistole");
                }else if(Diastole.getText().trim().equals("")){
                    Valid.textKosong(Diastole,"Diastole");
                }else if(Respiratory.getText().trim().equals("")){
                    Valid.textKosong(Respiratory,"Respiratory Rate");
                }else if(Heartrate.getText().trim().equals("")){
                    Valid.textKosong(Heartrate,"Heart Rate");
                }else{
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                        headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                        headers.add("X-Signature",api.getHmac());
                        headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                        
                        diagnosa2="null";
                        if(!KdDiagnosa2.getText().equals("")){
                            diagnosa2="\""+KdDiagnosa2.getText()+"\"";
                        }
                        diagnosa3="null";
                        if(!KdDiagnosa3.getText().equals("")){
                            diagnosa3="\""+KdDiagnosa3.getText()+"\"";
                        }
                        requestJson ="{" +
                                        "\"noKunjungan\": \""+tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString()+"\"," +
                                        "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                        "\"tglDaftar\": \""+TanggalDaftar.getSelectedItem()+"\"," +
                                        "\"kdPoli\": \""+KdPoliTujuan.getText()+"\"," +
                                        "\"keluhan\": \""+Keluhan.getText()+"\"," +
                                        "\"kdSadar\": \""+KdSadar.getText()+"\"," +
                                        "\"sistole\": "+Sistole.getText()+"," +
                                        "\"diastole\": "+Diastole.getText()+"," +
                                        "\"beratBadan\": "+BeratBadan.getText()+"," +
                                        "\"tinggiBadan\": "+TinggiBadan.getText()+"," +
                                        "\"respRate\": "+Respiratory.getText()+"," +
                                        "\"heartRate\": "+Heartrate.getText()+"," +
                                        "\"terapi\": \""+Terapi.getText()+"\"," +
                                        "\"kdStatusPulang\": \"3\"," +
                                        "\"tglPulang\": \""+TanggalPulang.getSelectedItem()+"\"," +
                                        "\"kdDokter\": \""+KdTenagaMedis.getText()+"\"," +
                                        "\"kdDiag1\": \""+KdDiagnosa1.getText()+"\"," +
                                        "\"kdDiag2\": "+diagnosa2+"," +
                                        "\"kdDiag3\": "+diagnosa3+"," +
                                        "\"kdPoliRujukInternal\":null," +
                                        "\"rujukLanjut\": null," +
                                        "\"kdTacc\": 0," +
                                        "\"alasanTacc\": null" +
                                      "}";
                        System.out.println(requestJson);
                        requestEntity = new HttpEntity(requestJson,headers);
                        requestJson=api.getRest().exchange(URL+"/kunjungan", HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println(requestJson);
                        root = mapper.readTree(requestJson);
                        nameNode = root.path("metaData");
                        System.out.println("code : "+nameNode.path("code").asText());
                        System.out.println("message : "+nameNode.path("message").asText());
                        if(nameNode.path("code").asText().equals("200")){
                            Sequel.mengedit("pcare_kunjungan_umum",
                                "no_rawat=?","no_rawat=?,noKunjungan=?,tglDaftar=?,no_rkm_medis=?,nm_pasien=?,noKartu=?,kdPoli=?,nmPoli=?,keluhan=?,kdSadar=?,nmSadar=?,sistole=?,diastole=?,beratBadan=?,tinggiBadan=?,respRate=?,heartRate=?,terapi=?,kdStatusPulang=?,nmStatusPulang=?,tglPulang=?,kdDokter=?,nmDokter=?,kdDiag1=?,nmDiag1=?,kdDiag2=?,nmDiag2=?,kdDiag3=?,nmDiag3=?",30,new String[]{
                                TNoRw.getText(),response.asText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),TNoRM.getText(),TPasien.getText(),
                                NoKartu.getText(),KdPoliTujuan.getText(),NmPoliTujuan.getText(),Valid.MaxTeks(Keluhan.getText(),400),KdSadar.getText(),NmSadar.getText(),
                                Sistole.getText(),Diastole.getText(),BeratBadan.getText(),TinggiBadan.getText(),Respiratory.getText(),Heartrate.getText(),
                                Valid.MaxTeks(Terapi.getText(),400),"3","Berobat Jalan",Valid.SetTgl(TanggalPulang.getSelectedItem()+""),
                                KdTenagaMedis.getText(),Valid.MaxTeks(NmTenagaMedis.getText(),50),KdDiagnosa1.getText(),Valid.MaxTeks(NmDiagnosa1.getText(),400),KdDiagnosa2.getText(),
                                Valid.MaxTeks(NmDiagnosa2.getText(),400),KdDiagnosa3.getText(),Valid.MaxTeks(NmDiagnosa3.getText(),400),
                                tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),0).toString()
                            }); 
                            emptTeks();
                        }else{
                            JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                        }
                    }catch (Exception ex) {
                        System.out.println("Notifikasi Bridging : "+ex);
                        if(ex.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                        }else if(ex.toString().contains("500")){
                            JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                        }else if(ex.toString().contains("401")){
                            JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                        }else if(ex.toString().contains("408")){
                            JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                        }else if(ex.toString().contains("424")){
                            JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                        }else if(ex.toString().contains("412")){
                            JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                        }else if(ex.toString().contains("204")){
                            JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                        }
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            }    
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
        poli.dispose();
        kesadaran.dispose();
        statuspulang.dispose();
        dokter.dispose();
        penyakit.dispose();
        sarana.dispose();
        subspesialis.dispose();
        provider.dispose();
        khusus.dispose();
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
                    param.put("parameter","%"+TCari.getText()+"%"); 
                Valid.MyReport("rptPCarePendaftaran.jasper","report","::[ Data Pendaftaran PCare ]::",param);
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabMode2.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari1.requestFocus();
            }else if(tabMode2.getRowCount()!=0){            
                Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    param.put("tanggal1",Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                    param.put("parameter","%"+TCari1.getText()+"%"); 
                Valid.MyReport("rptPCareKunjungan.jasper","report","::[ Data Kunjungan PCare ]::",param);
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
        TCari1.setText("");
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            TCari1.setText("");
            TabRawatMouseClicked(null);
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbPendaftaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPendaftaranMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                emptTeks();
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
               pilihan=1;
               TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbPendaftaranMouseClicked

    private void tbPendaftaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPendaftaranKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                   emptTeks();
                   getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    pilihan=1;
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }   
        }
}//GEN-LAST:event_tbPendaftaranKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        Valid.pindah(evt,btnPoliTujuan,TinggiBadan);
    }//GEN-LAST:event_KeluhanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(akses.getform().equals("DlgReg")||akses.getform().equals("DlgIGD")||akses.getform().equals("DlgKamarInap")){
            NoKartu.setText(Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText()));
            if(NoKartu.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Pasien tidak mempunyai kepesertaan BPJS");
                dispose();
            }else{
                try {
                    headers = new HttpHeaders();
                    headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                    requestEntity = new HttpEntity(headers);
                    //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                    root = mapper.readTree(api.getRest().exchange(URL+"/peserta/"+NoKartu.getText(), HttpMethod.GET, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    //System.out.println("code : "+nameNode.path("code").asText());
                    //System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("message").asText().equals("OK")){
                        response = root.path("response");
                        if(response.path("ketAktif").asText().equals("AKTIF")){
                            TPasien.setText(response.path("nama").asText());
                            TglLahir.setText(response.path("tglLahir").asText());
                            JK.setText(response.path("sex").asText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                            JenisPeserta.setText(response.path("jnsPeserta").path("nama").asText());
                            Status.setText(response.path("ketAktif").asText()); 
                            ProviderPeserta.setText(response.path("kdProviderPst").path("kdProvider").asText());
                        }else{
                            JOptionPane.showMessageDialog(null,response.path("ketAktif").asText());
                            dispose();
                        }                            
                    }else {
                        dispose();
                    }  
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                    }else if(ex.toString().contains("401")){
                        JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                    }else if(ex.toString().contains("408")){
                        JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                    }else if(ex.toString().contains("424")){
                        JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                    }else if(ex.toString().contains("412")){
                        JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                    }else if(ex.toString().contains("204")){
                        JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                    }
                }                 
            } 
            TNoRw.requestFocus();
        }
    }//GEN-LAST:event_formWindowOpened

    private void TanggalDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalDaftarKeyPressed
        Valid.pindah(evt,TNoRw,JenisKunjungan);
    }//GEN-LAST:event_TanggalDaftarKeyPressed

    private void JenisKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKunjunganKeyPressed
        Valid.pindah(evt,TanggalDaftar,Perawatan);
    }//GEN-LAST:event_JenisKunjunganKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<700){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,580));
            if(this.getWidth()<780){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,580));
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

    private void PerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanKeyPressed
        Valid.pindah(evt,JenisKunjungan,btnPoliTujuan);
    }//GEN-LAST:event_PerawatanKeyPressed

    private void btnPoliTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliTujuanActionPerformed
        pilihan=1;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliTujuanActionPerformed

    private void btnPoliTujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliTujuanKeyPressed
        Valid.pindah(evt,Perawatan,Keluhan);
    }//GEN-LAST:event_btnPoliTujuanKeyPressed

    private void TinggiBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggiBadanKeyPressed
        Valid.pindah(evt,Keluhan,BeratBadan);
    }//GEN-LAST:event_TinggiBadanKeyPressed

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        Valid.pindah(evt,TinggiBadan,Sistole);
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void SistoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistoleKeyPressed
        Valid.pindah(evt,BeratBadan,Diastole);
    }//GEN-LAST:event_SistoleKeyPressed

    private void DiastoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiastoleKeyPressed
        Valid.pindah(evt,Sistole,Respiratory);
    }//GEN-LAST:event_DiastoleKeyPressed

    private void RespiratoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespiratoryKeyPressed
        Valid.pindah(evt,Diastole,Heartrate);
    }//GEN-LAST:event_RespiratoryKeyPressed

    private void HeartrateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HeartrateKeyPressed
        Valid.pindah(evt,Respiratory,chkKunjungan);
    }//GEN-LAST:event_HeartrateKeyPressed

    private void TanggalKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKunjunganKeyPressed
        Valid.pindah(evt,chkKunjungan,TanggalPulang);
    }//GEN-LAST:event_TanggalKunjunganKeyPressed

    private void BtnKesadaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKesadaranActionPerformed
        kesadaran.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kesadaran.setLocationRelativeTo(internalFrame1);
        kesadaran.setVisible(true);
    }//GEN-LAST:event_BtnKesadaranActionPerformed

    private void BtnKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKesadaranKeyPressed
        Valid.pindah(evt,TanggalPulang,Terapi);
    }//GEN-LAST:event_BtnKesadaranKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiKeyPressed

    private void BtnStatusPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusPulangActionPerformed
        statuspulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statuspulang.setLocationRelativeTo(internalFrame1);
        statuspulang.setVisible(true);
    }//GEN-LAST:event_BtnStatusPulangActionPerformed

    private void BtnStatusPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnStatusPulangKeyPressed

    private void TanggalPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPulangKeyPressed
        Valid.pindah(evt,TanggalKunjungan,BtnKesadaran);
    }//GEN-LAST:event_TanggalPulangKeyPressed

    private void BtnTenagaMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTenagaMedisActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnTenagaMedisActionPerformed

    private void BtnTenagaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTenagaMedisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTenagaMedisKeyPressed

    private void BtnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosa1ActionPerformed
        pilihan=1;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_BtnDiagnosa1ActionPerformed

    private void BtnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDiagnosa1KeyPressed

    private void BtnDiagnosa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosa2ActionPerformed
        pilihan=2;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_BtnDiagnosa2ActionPerformed

    private void BtnDiagnosa2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDiagnosa2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDiagnosa2KeyPressed

    private void BtnDiagnosa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosa3ActionPerformed
        pilihan=3;
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_BtnDiagnosa3ActionPerformed

    private void BtnDiagnosa3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDiagnosa3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDiagnosa3KeyPressed

    private void BtnPoliInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliInternalActionPerformed
        pilihan=2;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliInternalActionPerformed

    private void BtnPoliInternalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoliInternalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPoliInternalKeyPressed

    private void TanggalEstRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalEstRujukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalEstRujukKeyPressed

    private void BtnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPPKRujukanActionPerformed
        provider.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        provider.setLocationRelativeTo(internalFrame1);
        provider.setVisible(true);
    }//GEN-LAST:event_BtnPPKRujukanActionPerformed

    private void BtnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPPKRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPPKRujukanKeyPressed

    private void BtnSubSpesialisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSubSpesialisActionPerformed
        pilihan=1;
        subspesialis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        subspesialis.setLocationRelativeTo(internalFrame1);
        subspesialis.setVisible(true);
    }//GEN-LAST:event_BtnSubSpesialisActionPerformed

    private void BtnSubSpesialisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSubSpesialisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSubSpesialisKeyPressed

    private void BtnSaranaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSaranaActionPerformed
        sarana.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sarana.setLocationRelativeTo(internalFrame1);
        sarana.setVisible(true);
    }//GEN-LAST:event_BtnSaranaActionPerformed

    private void BtnSaranaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSaranaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSaranaKeyPressed

    private void btnKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhususActionPerformed
        khusus.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        khusus.setLocationRelativeTo(internalFrame1);
        khusus.setVisible(true);
    }//GEN-LAST:event_btnKhususActionPerformed

    private void btnKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKhususKeyPressed

    private void BtnSubKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSubKhususActionPerformed
        pilihan=2;
        subspesialis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        subspesialis.setLocationRelativeTo(internalFrame1);
        subspesialis.setVisible(true);
    }//GEN-LAST:event_BtnSubKhususActionPerformed

    private void BtnSubKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSubKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSubKhususKeyPressed

    private void CatatanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CatatanKhususKeyPressed

    private void TACCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TACCKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TACCKeyPressed

    private void AlasanTACCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanTACCKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanTACCKeyPressed

    private void TACCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TACCItemStateChanged
        if(TACC.getSelectedIndex()==0){
            AlasanTACC.removeAllItems();
        }else if(TACC.getSelectedIndex()==1){
            AlasanTACC.removeAllItems();
            AlasanTACC.addItem("< 3 Hari");
            AlasanTACC.addItem(">= 3 - 7 Hari");
            AlasanTACC.addItem(">= 7 Hari");
        }else if(TACC.getSelectedIndex()==2){
            AlasanTACC.removeAllItems();
            AlasanTACC.addItem("< 1 Bulan");
            AlasanTACC.addItem(">= 1 Bulan s/d < 12 Bulan");
            AlasanTACC.addItem(">= 1 Tahun s/d < 5 Tahun");
            AlasanTACC.addItem(">= 5 Tahun s/d < 12 Tahun");
            AlasanTACC.addItem(">= 12 Tahun s/d < 55 Tahun");
            AlasanTACC.addItem(">= 55 Tahun");
        }else if(TACC.getSelectedIndex()==3){
            AlasanTACC.removeAllItems();
            AlasanTACC.addItem(KdDiagnosa1.getText()+" - "+NmDiagnosa1.getText());
            if(!KdDiagnosa2.getText().equals("")){
                AlasanTACC.addItem(KdDiagnosa2.getText()+" - "+NmDiagnosa2.getText());
            }
            if(!KdDiagnosa3.getText().equals("")){
                AlasanTACC.addItem(KdDiagnosa3.getText()+" - "+NmDiagnosa3.getText());
            }
        }else if(TACC.getSelectedIndex()==4){
            AlasanTACC.removeAllItems();
            AlasanTACC.addItem("< 3 Hari");
            AlasanTACC.addItem(">= 3 - 7 Hari");
            AlasanTACC.addItem(">= 7 Hari");
        }
    }//GEN-LAST:event_TACCItemStateChanged

    private void KdPoliTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdPoliTujuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPoliTujuanActionPerformed

    private void chkKunjunganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkKunjunganItemStateChanged
        if(chkKunjungan.isSelected()==true){
            TanggalKunjungan.setEnabled(true);
            TanggalPulang.setEnabled(true);
            BtnKesadaran.setEnabled(true);
            Terapi.setEnabled(true);
            BtnStatusPulang.setEnabled(true);
            BtnTenagaMedis.setEnabled(true);
            BtnDiagnosa1.setEnabled(true);
            BtnDiagnosa2.setEnabled(true);
            BtnDiagnosa3.setEnabled(true);
            ChkRujukLanjut.setEnabled(true);
            ChkRujukLanjut.setSelected(false);
            ChkRujukLanjutItemStateChanged(null);
        }else{
            TanggalKunjungan.setEnabled(false);
            TanggalPulang.setEnabled(false);
            BtnKesadaran.setEnabled(false);
            Terapi.setEnabled(false);
            BtnStatusPulang.setEnabled(false);
            BtnTenagaMedis.setEnabled(false);
            BtnDiagnosa1.setEnabled(false);
            BtnDiagnosa2.setEnabled(false);
            BtnDiagnosa3.setEnabled(false);            
            ChkRujukLanjut.setEnabled(false);
            ChkRujukLanjut.setSelected(false);
            ChkRujukLanjutItemStateChanged(null);
        }
    }//GEN-LAST:event_chkKunjunganItemStateChanged

    private void ChkRujukLanjutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRujukLanjutItemStateChanged
        if(ChkRujukLanjut.isSelected()==true){
            TanggalEstRujuk.setEnabled(true);
            BtnPPKRujukan.setEnabled(true);
            chkSubspesialis.setEnabled(true);
            chkSubspesialis.setSelected(false);
            chkSubspesialisItemStateChanged(null);
            chkKhusus.setEnabled(true);
            chkKhusus.setSelected(false);
            chkKhususItemStateChanged(null);
            ChkInternal.setEnabled(true);
            ChkInternal.setSelected(false);
            ChkInternalItemStateChanged(null);
            CatatanKhusus.setEnabled(true);
            TACC.setEnabled(true);
            AlasanTACC.setEnabled(true);
        }else{            
            TanggalEstRujuk.setEnabled(false);
            BtnPPKRujukan.setEnabled(false);
            chkSubspesialis.setEnabled(false);
            chkSubspesialis.setSelected(false);
            chkSubspesialisItemStateChanged(null);
            chkKhusus.setEnabled(false);
            chkKhusus.setSelected(false);
            chkKhususItemStateChanged(null);
            ChkInternal.setEnabled(false);
            ChkInternal.setSelected(false);
            ChkInternalItemStateChanged(null);
            CatatanKhusus.setEnabled(false);
            TACC.setEnabled(false);
            AlasanTACC.setEnabled(false);
        }
    }//GEN-LAST:event_ChkRujukLanjutItemStateChanged

    private void chkSubspesialisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSubspesialisItemStateChanged
        if(chkSubspesialis.isSelected()==true){
            BtnSubSpesialis.setEnabled(true);
            BtnSarana.setEnabled(true);   
            ChkInternal.setSelected(false);
            KdPoliInternal.setText("");
            NmPoliInternal.setText("");
            BtnPoliInternal.setEnabled(false);
            chkKhusus.setSelected(false);
            KdKhusus.setText("");
            NmKhusus.setText("");
            btnKhusus.setSelected(false);
            KdSubKhusus.setText("");
            NmSubKhusus.setText("");
            BtnSubKhusus.setEnabled(false);
            CatatanKhusus.setText("");
            CatatanKhusus.setEnabled(false);
        }else{
            BtnSubSpesialis.setEnabled(false);
            BtnSarana.setEnabled(false);
            KdSubSpesialis.setText("");
            NmSubSpesialis.setText("");
            KdSarana.setText("");
            NmSarana.setText("");
        }
    }//GEN-LAST:event_chkSubspesialisItemStateChanged

    private void ChkInternalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkInternalItemStateChanged
        if(ChkInternal.isSelected()==true){
            BtnPoliInternal.setEnabled(true);
            chkSubspesialis.setSelected(false);
            KdSubSpesialis.setText("");
            NmSubSpesialis.setText("");
            BtnSubSpesialis.setEnabled(false);
            KdSarana.setText("");
            NmSarana.setText("");
            BtnSarana.setEnabled(false);
            chkKhusus.setSelected(false);
            KdKhusus.setText("");
            NmKhusus.setText("");
            btnKhusus.setSelected(false);
            KdSubKhusus.setText("");
            NmSubKhusus.setText("");
            BtnSubKhusus.setEnabled(false);
            CatatanKhusus.setText("");
            CatatanKhusus.setEnabled(false);
        }else{
            BtnPoliInternal.setEnabled(false);
            KdPoliInternal.setText("");
            NmPoliInternal.setText("");
        }
    }//GEN-LAST:event_ChkInternalItemStateChanged

    private void chkKhususItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkKhususItemStateChanged
        if(chkKhusus.isSelected()==true){
            btnKhusus.setEnabled(true);
            BtnSubKhusus.setEnabled(true);      
            CatatanKhusus.setEnabled(true);
            chkSubspesialis.setSelected(false);
            KdSubSpesialis.setText("");
            NmSubSpesialis.setText("");
            BtnSubSpesialis.setEnabled(false);
            KdSarana.setText("");
            NmSarana.setText("");
            BtnSarana.setEnabled(false);
            ChkInternal.setSelected(false);
            KdPoliInternal.setText("");
            NmPoliInternal.setText("");
            BtnPoliInternal.setEnabled(false);
        }else{
            btnKhusus.setEnabled(false);
            BtnSubKhusus.setEnabled(false);      
            CatatanKhusus.setEnabled(false);
            KdKhusus.setText("");
            NmKhusus.setText("");
            KdSubKhusus.setText("");
            NmSubKhusus.setText("");
            CatatanKhusus.setText("");
        }        
    }//GEN-LAST:event_chkKhususItemStateChanged

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
            if(akses.getform().equals("DlgReg")||akses.getform().equals("DlgIGD")||akses.getform().equals("DlgKamarInap")){
                NoKartu.setText(Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText()));
                if(NoKartu.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Pasien tidak mempunyai kepesertaan BPJS");
                    dispose();
                }else{
                    try {
                        headers = new HttpHeaders();
                        headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                        headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                        headers.add("X-Signature",api.getHmac());
                        headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                        requestEntity = new HttpEntity(headers);
                        //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        root = mapper.readTree(api.getRest().exchange(URL+"/peserta/"+NoKartu.getText(), HttpMethod.GET, requestEntity, String.class).getBody());
                        nameNode = root.path("metaData");
                        //System.out.println("code : "+nameNode.path("code").asText());
                        //System.out.println("message : "+nameNode.path("message").asText());
                        if(nameNode.path("message").asText().equals("OK")){
                            response = root.path("response");
                            if(response.path("ketAktif").asText().equals("AKTIF")){
                                TPasien.setText(response.path("nama").asText());
                                TglLahir.setText(response.path("tglLahir").asText());
                                JK.setText(response.path("sex").asText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                JenisPeserta.setText(response.path("jnsPeserta").path("nama").asText());
                                Status.setText(response.path("ketAktif").asText()); 
                                ProviderPeserta.setText(response.path("kdProviderPst").path("kdProvider").asText());
                            }else{
                                JOptionPane.showMessageDialog(null,response.path("ketAktif").asText());
                                dispose();
                            }                            
                        }else {
                            dispose();
                        }  
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                        if(ex.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                        }else if(ex.toString().contains("500")){
                            JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                        }else if(ex.toString().contains("401")){
                            JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                        }else if(ex.toString().contains("408")){
                            JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                        }else if(ex.toString().contains("424")){
                            JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                        }else if(ex.toString().contains("412")){
                            JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                        }else if(ex.toString().contains("204")){
                            JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                        }
                    }                 
                } 
            }
            TanggalDaftar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void chkKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkKunjunganKeyPressed
        Valid.pindah(evt,Heartrate,TanggalKunjungan);
    }//GEN-LAST:event_chkKunjunganKeyPressed

    private void tbKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKunjunganMouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                emptTeks();
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==2){
                pilihan=2;
                chkKunjungan.setSelected(true);
                TabRawat.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tbKunjunganMouseClicked

    private void tbKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKunjunganKeyPressed
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    emptTeks();
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    pilihan=2;
                    chkKunjungan.setSelected(true);
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }   
        }
    }//GEN-LAST:event_tbKunjunganKeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCari1KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKunjungan.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM18.jasper","report","::[ Label Rekam Medis ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void MnBarcode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode2ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            Valid.MyReport("rptBarcodeRawat2.jasper","report","::[ Barcode No.Rawat ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode2ActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            Valid.MyReport("rptBarcodeRawat.jasper","report","::[ Barcode No.Rawat ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnLabelTracker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker3ActionPerformed
        if(TPasien.getText().trim().equals("")){
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
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("no_rawat",TNoRw.getText());
            Valid.MyReport("rptLabelTracker4.jasper","report","::[ Label Tracker ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker3ActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if(TPasien.getText().trim().equals("")){
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
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("no_rawat",TNoRw.getText());
            Valid.MyReport("rptLabelTracker3.jasper","report","::[ Label Tracker ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker2ActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if(TPasien.getText().trim().equals("")){
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
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker2.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if(TPasien.getText().trim().equals("")){
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
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnGelang6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang6ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM16.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang6ActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang5ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM14.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang5ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang4ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM10.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang4ActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang3ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM8.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang3ActionPerformed

    private void MnGelang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang2ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM7.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang2ActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("parameter",TNoRM.getText()); 
            Valid.MyReport("rptBarcodeRM6.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKunjungan.requestFocus();
        }else{
            if(tbKunjungan.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{
                    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",TNoRw.getText());
                        bangsal=Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?",Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",kamar));
                        if(bangsal.equals("")){
                            if(Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?",kamar));
                            }else{
                                akses.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        }else{
                            akses.setkdbangsal(bangsal);
                        }
                        DlgCariObat2 dlgobt2=new DlgCariObat2(null,false);
                        dlgobt2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgobt2.setLocationRelativeTo(internalFrame1);
                        dlgobt2.setNoRm(TNoRw.getText(),tanggal.getDate());
                        dlgobt2.setPCare("yes",tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString());
                        dlgobt2.isCek();
                        dlgobt2.tampil();
                        dlgobt2.setVisible(true);
                    }else {
                        DlgCariObat dlgobt=new DlgCariObat(null,false);
                        dlgobt.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(tanggal.getSelectedItem()+""),tanggal.getSelectedItem().toString().substring(11,19));
                        dlgobt.setPCare("yes",tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString());
                        dlgobt.isCek();
                        dlgobt.setDokter("","");
                        dlgobt.tampilobat();
                        dlgobt.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgobt.setLocationRelativeTo(internalFrame1);
                        dlgobt.setVisible(true);
                    }
                }                    
            }                
        }
    }//GEN-LAST:event_MnPemberianObatActionPerformed

    private void MnTIndakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTIndakanActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKunjungan.requestFocus();
        }else{
            if(tbKunjungan.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                }else{
                    if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        DlgCariPerawatanRanap perawatan=new DlgCariPerawatanRanap(null,false);
                        kdptg=Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",TNoRw.getText());
                        if(kdptg.equals("")){
                            kdptg=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                        }
                        nmptg=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kdptg);
                        perawatan.setNoRm(
                                TNoRw.getText(),"rawat_inap_dr",tanggal.getDate(),tanggal.getSelectedItem().toString().substring(11,13),
                                tanggal.getSelectedItem().toString().substring(14,16),tanggal.getSelectedItem().toString().substring(17,19),
                                false,TPasien.getText()
                        );
                        perawatan.setPetugas(kdptg,nmptg,"","","","","","","","","","","","");
                        perawatan.emptTeks();
                        perawatan.setPCare("yes",tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString());
                        perawatan.isCek();
                        perawatan.tampil();
                        perawatan.setSize(this.getWidth()-20,this.getHeight()-20);
                        perawatan.setLocationRelativeTo(internalFrame1);
                        perawatan.setVisible(true);
                    }else {
                        DlgCariPerawatanRalan dlgrwjl=new DlgCariPerawatanRalan(null,false);
                        kdptg=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                        nmptg=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kdptg);
                        dlgrwjl.setNoRm(TNoRw.getText(),kdptg,nmptg,"rawat_jl_dr","","","","","","","-","-","","","","","","");
                        dlgrwjl.setPCare("yes",tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString());
                        dlgrwjl.isCek();
                        dlgrwjl.tampil();
                        dlgrwjl.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgrwjl.setLocationRelativeTo(internalFrame1);
                        dlgrwjl.setAlwaysOnTop(false);
                        dlgrwjl.setVisible(true); 
                    }
                }                    
            }                
        }
    }//GEN-LAST:event_MnTIndakanActionPerformed

    private void MnPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObat1ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKunjungan.requestFocus();
        }else{
            if(tbKunjungan.getSelectedRow()!= -1){
                PCareDataPemberianObat dlgrwinap=new PCareDataPemberianObat(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari3.getDate(),DTPCari4.getDate()); 
                dlgrwinap.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnPemberianObat1ActionPerformed

    private void MnTIndakan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTIndakan1ActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKunjungan.requestFocus();
        }else{
            if(tbKunjungan.getSelectedRow()!= -1){
                PCareDataPemberianTindakan dlgrwinap=new PCareDataPemberianTindakan(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari3.getDate(),DTPCari4.getDate()); 
                dlgrwinap.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnTIndakan1ActionPerformed

    private void ppBuktiKunjunganBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBuktiKunjunganBtnPrintActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kunjungan sudah habis...!!!!");
            TCari1.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data kunjungan pada table...!!!");
            TCari1.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("divreg",divreg);
            param.put("kacab",kacab);
            param.put("userpcare",userpcare);
            param.put("lahir",Sequel.cariIsi2("select tgl_lahir from pasien where no_rkm_medis='"+tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),3).toString()+"'"));
            param.put("jk",Sequel.cariIsi("select jk from pasien where no_rkm_medis='"+tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),3).toString()+"'"));
            param.put("umur",Sequel.cariIsi("select concat(umurdaftar,' ',sttsumur) from reg_periksa where no_rawat='"+TNoRw.getText()+"'").replaceAll("Th","Tahun").replaceAll("Bl","Bulan").replaceAll("Hr","Hari"));
            param.put("tanggal",TanggalDaftar.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            param.put("no_rawat",TNoRw.getText()); 
            Valid.MyReport("rptBuktiKunjunganPCare.jasper","report","::[ Bukti Kunjungan PCare ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppBuktiKunjunganBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCareDataPendaftaran dialog = new PCareDataPendaftaran(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AlasanTACC;
    private widget.TextBox BeratBadan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnDiagnosa1;
    private widget.Button BtnDiagnosa2;
    private widget.Button BtnDiagnosa3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKesadaran;
    private widget.Button BtnPPKRujukan;
    private widget.Button BtnPoliInternal;
    private widget.Button BtnPrint;
    private widget.Button BtnSarana;
    private widget.Button BtnSimpan;
    private widget.Button BtnStatusPulang;
    private widget.Button BtnSubKhusus;
    private widget.Button BtnSubSpesialis;
    private widget.Button BtnTenagaMedis;
    private widget.TextBox CatatanKhusus;
    private widget.CekBox ChkInternal;
    private widget.CekBox ChkRujukLanjut;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    private widget.TextBox Diastole;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Heartrate;
    private widget.TextBox JK;
    private widget.ComboBox JenisKunjungan;
    private widget.TextBox JenisPeserta;
    private widget.TextBox KdDiagnosa1;
    private widget.TextBox KdDiagnosa2;
    private widget.TextBox KdDiagnosa3;
    private widget.TextBox KdKhusus;
    private widget.TextBox KdPPKRujukan;
    private widget.TextBox KdPoliInternal;
    private widget.TextBox KdPoliTujuan;
    private widget.TextBox KdSadar;
    private widget.TextBox KdSarana;
    private widget.TextBox KdStatusPulang;
    private widget.TextBox KdSubKhusus;
    private widget.TextBox KdSubSpesialis;
    private widget.TextBox KdTenagaMedis;
    private widget.TextBox Keluhan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli10;
    private widget.Label LabelPoli11;
    private widget.Label LabelPoli12;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelPoli6;
    private widget.Label LabelPoli7;
    private widget.Label LabelPoli8;
    private widget.Label LabelPoli9;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcode2;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenu MnGelang;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnGelang2;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenuItem MnGelang6;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPemberianObat1;
    private javax.swing.JMenuItem MnTIndakan;
    private javax.swing.JMenuItem MnTIndakan1;
    private widget.TextBox NmDiagnosa1;
    private widget.TextBox NmDiagnosa2;
    private widget.TextBox NmDiagnosa3;
    private widget.TextBox NmKhusus;
    private widget.TextBox NmPPKRujukan;
    private widget.TextBox NmPoliInternal;
    private widget.TextBox NmPoliTujuan;
    private widget.TextBox NmSadar;
    private widget.TextBox NmSarana;
    private widget.TextBox NmStatusPulang;
    private widget.TextBox NmSubKhusus;
    private widget.TextBox NmSubSpesialis;
    private widget.TextBox NmTenagaMedis;
    private widget.TextBox NoKartu;
    private widget.ComboBox Perawatan;
    private widget.TextBox ProviderPeserta;
    private widget.TextBox Respiratory;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox Sistole;
    private widget.TextBox Status;
    private widget.ComboBox TACC;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalDaftar;
    private widget.Tanggal TanggalEstRujuk;
    private widget.Tanggal TanggalKunjungan;
    private widget.Tanggal TanggalPulang;
    private widget.TextBox Terapi;
    private widget.TextBox TglLahir;
    private widget.TextBox TinggiBadan;
    private widget.Button btnKhusus;
    private widget.Button btnPoliTujuan;
    private widget.CekBox chkKhusus;
    private widget.CekBox chkKunjungan;
    private widget.CekBox chkSubspesialis;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
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
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBuktiKunjungan;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Tanggal tanggal;
    private widget.Table tbKunjungan;
    private widget.Table tbPendaftaran;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                "select pcare_pendaftaran.no_rawat,pcare_pendaftaran.tglDaftar,pcare_pendaftaran.no_rkm_medis,"+
                "pcare_pendaftaran.nm_pasien,pcare_pendaftaran.kdProviderPeserta,pcare_pendaftaran.noKartu,"+
                "pcare_pendaftaran.kdPoli,pcare_pendaftaran.nmPoli,pcare_pendaftaran.keluhan,pcare_pendaftaran.kunjSakit,"+
                "pcare_pendaftaran.sistole,pcare_pendaftaran.diastole,pcare_pendaftaran.beratBadan,pcare_pendaftaran.tinggiBadan,"+
                "pcare_pendaftaran.respRate,pcare_pendaftaran.heartRate,pcare_pendaftaran.rujukBalik,pcare_pendaftaran.kdTkp,"+
                "pcare_pendaftaran.noUrut from pcare_pendaftaran where "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.no_rawat like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.no_rkm_medis like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.nm_pasien like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.noKartu like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.nmPoli like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.kunjSakit like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.kdTkp like ? or "+
                "pcare_pendaftaran.tglDaftar between ? and ? and pcare_pendaftaran.keluhan like ? order by pcare_pendaftaran.tglDaftar");
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
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("tglDaftar"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("kdProviderPeserta"),rs.getString("noKartu"),
                        rs.getString("kdPoli"),rs.getString("nmPoli"),rs.getString("keluhan"),
                        rs.getString("kunjSakit"),rs.getString("sistole"),rs.getString("diastole"),
                        rs.getString("beratBadan"),rs.getString("tinggiBadan"),rs.getString("respRate"),
                        rs.getString("heartRate"),rs.getString("rujukBalik"),rs.getString("kdTkp"),
                        rs.getString("noUrut")
                    });
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
    
    public void tampil2() {        
        Valid.tabelKosong(tabMode2);
        try {
            ps=koneksi.prepareStatement(
                "select no_rawat, noKunjungan, tglDaftar, no_rkm_medis, nm_pasien, noKartu,"+
                "kdPoli, nmPoli, keluhan, kdSadar, nmSadar, sistole, diastole, beratBadan, "+
                "tinggiBadan, respRate, heartRate, terapi, kdStatusPulang, nmStatusPulang, "+
                "tglPulang, kdDokter, nmDokter, kdDiag1, nmDiag1, kdDiag2, nmDiag2, kdDiag3, "+
                "nmDiag3 from pcare_kunjungan_umum where "+
                "tglDaftar between ? and ? and no_rawat like ? or "+
                "tglDaftar between ? and ? and noKunjungan like ? or "+
                "tglDaftar between ? and ? and no_rkm_medis like ? or "+
                "tglDaftar between ? and ? and nm_pasien like ? or "+
                "tglDaftar between ? and ? and noKartu like ? or "+
                "tglDaftar between ? and ? and nmPoli like ? or "+
                "tglDaftar between ? and ? and nmSadar like ? or "+
                "tglDaftar between ? and ? and nmStatusPulang like ? or "+
                "tglDaftar between ? and ? and nmDokter like ? or "+
                "tglDaftar between ? and ? and nmDiag1 like ? order by tglDaftar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(3,"%"+TCari1.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(6,"%"+TCari1.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(9,"%"+TCari1.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(12,"%"+TCari1.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(15,"%"+TCari1.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(18,"%"+TCari1.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(21,"%"+TCari1.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(24,"%"+TCari1.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(27,"%"+TCari1.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari3.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari4.getSelectedItem()+""));
                ps.setString(30,"%"+TCari1.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("noKunjungan"),rs.getString("tglDaftar"),
                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("noKartu"),
                        rs.getString("kdPoli"),rs.getString("nmPoli"),rs.getString("keluhan"),
                        rs.getString("kdSadar"),rs.getString("nmSadar"),rs.getString("sistole"),
                        rs.getString("diastole"),rs.getString("beratBadan"),rs.getString("tinggiBadan"),
                        rs.getString("respRate"),rs.getString("heartRate"),rs.getString("terapi"),
                        rs.getString("kdStatusPulang"),rs.getString("nmStatusPulang"),rs.getString("tglPulang"),
                        rs.getString("kdDokter"),rs.getString("nmDokter"),rs.getString("kdDiag1"),
                        rs.getString("nmDiag1"),rs.getString("kdDiag2"),rs.getString("nmDiag2"),
                        rs.getString("kdDiag3"),rs.getString("nmDiag3")
                    });
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

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());  
        Keluhan.setText("");
        TinggiBadan.setText("0");
        BeratBadan.setText("0");
        Sistole.setText("0");
        Diastole.setText("0");
        Respiratory.setText("0");
        Heartrate.setText("0");
    }
    
    private void emptTeks(){
        TNoRw.setText("");
        TPasien.setText("");
        TanggalDaftar.setDate(new Date());
        TglLahir.setText("");
        NoKartu.setText("");
        JenisPeserta.setText("");
        Status.setText("");
        JK.setText("");
        Keluhan.setText("");
        KdPoliTujuan.setText("");
        NmPoliTujuan.setText("");
        TNoRM.setText("");
        TinggiBadan.setText("0");
        BeratBadan.setText("0");
        Sistole.setText("0");
        Diastole.setText("0");
        Respiratory.setText("0");
        Heartrate.setText("0");
        chkKunjungan.setSelected(false);
        ChkRujukLanjut.setSelected(false);
        KdSadar.setText("");
        NmSadar.setText("");
        Terapi.setText("");
        KdStatusPulang.setText("");
        NmStatusPulang.setText("");
        KdTenagaMedis.setText("");
        NmTenagaMedis.setText("");
        KdDiagnosa1.setText("");
        KdDiagnosa2.setText("");
        KdDiagnosa3.setText("");
        NmDiagnosa1.setText("");
        NmDiagnosa2.setText("");
        NmDiagnosa3.setText("");
        KdPPKRujukan.setText("");
        NmPPKRujukan.setText("");
        KdSubSpesialis.setText("");
        NmSubSpesialis.setText("");
        KdSarana.setText("");
        NmSarana.setText("");
        KdPoliInternal.setText("");
        NmPoliTujuan.setText("");
        KdKhusus.setText("");
        NmKhusus.setText("");
        KdSubKhusus.setText("");
        NmSubKhusus.setText("");
        CatatanKhusus.setText("");
        TACC.setSelectedIndex(0);
        TanggalDaftar.requestFocus();
    }
    
    public void setNoRm(String norwt, Date tgl1,String status,String kdpoli,String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoliTujuan.setText(Sequel.cariIsi("select kd_poli_pcare from maping_poliklinik_pcare where kd_poli_rs=?",kdpoli));
        NmPoliTujuan.setText(Sequel.cariIsi("select nm_poli_pcare from maping_poliklinik_pcare where kd_poli_pcare=?",KdPoliTujuan.getText()));
        isRawat();            
    }
      
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbridging_pcare_daftar());
        BtnEdit.setEnabled(akses.getbridging_pcare_daftar());
        BtnHapus.setEnabled(akses.getbridging_pcare_daftar());
        BtnPrint.setEnabled(akses.getbridging_pcare_daftar());
        MnPemberianObat1.setEnabled(akses.getpcare_pemberian_obat());
        MnPemberianObat.setEnabled(akses.getpcare_pemberian_obat());
        MnTIndakan.setEnabled(akses.getpcare_pemberian_tindakan());
        MnTIndakan1.setEnabled(akses.getpcare_pemberian_tindakan());
        ppRiwayat.setEnabled(akses.getresume_pasien());            
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    private void getData() {
        if(tbPendaftaran.getSelectedRow()!= -1){
            TNoRw.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),0).toString());
            TNoRM.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),2).toString());
            TPasien.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),3).toString());
            TglLahir.setText(Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?",TNoRM.getText()));
            JK.setText(Sequel.cariIsi("select jk from pasien where no_rkm_medis=?",TNoRM.getText()).replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
            JenisPeserta.setText(Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",TNoRM.getText()));
            ProviderPeserta.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),4).toString());
            NoKartu.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),5).toString());
            KdPoliTujuan.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),6).toString());
            NmPoliTujuan.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),7).toString());
            Keluhan.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),8).toString());
            JenisKunjungan.setSelectedItem(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),9).toString());
            Sistole.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),10).toString());
            Diastole.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),11).toString());
            BeratBadan.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),12).toString());
            TinggiBadan.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),13).toString());
            Respiratory.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),14).toString());
            Heartrate.setText(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),15).toString());
            Perawatan.setSelectedItem(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),17).toString());
            Valid.SetTgl(TanggalDaftar,tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),1).toString());
        }
    }
    
    private void getData2() {
        if(tbKunjungan.getSelectedRow()!= -1){
            TNoRw.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),0).toString());
            Valid.SetTgl(TanggalDaftar,tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),2).toString());
            TNoRM.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),3).toString());
            TPasien.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),4).toString());
            JK.setText(Sequel.cariIsi("select jk from pasien where no_rkm_medis=?",TNoRM.getText()).replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
            JenisPeserta.setText(Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",TNoRM.getText()));
            NoKartu.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),5).toString());
            KdPoliTujuan.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),6).toString());
            NmPoliTujuan.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),7).toString());
            Keluhan.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),8).toString());
            KdSadar.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),9).toString());
            NmSadar.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),10).toString());
            Sistole.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),11).toString());
            Diastole.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),12).toString());
            BeratBadan.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),13).toString());
            TinggiBadan.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),14).toString());
            Respiratory.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),15).toString());
            Heartrate.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),16).toString());
            Terapi.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),17).toString());
            KdStatusPulang.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),18).toString());
            NmStatusPulang.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),19).toString());
            KdTenagaMedis.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),21).toString());
            NmTenagaMedis.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),22).toString());
            KdDiagnosa1.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),23).toString());
            NmDiagnosa1.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),24).toString());
            KdDiagnosa2.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),25).toString());
            NmDiagnosa2.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),26).toString());
            KdDiagnosa3.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),27).toString());
            NmDiagnosa3.setText(tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),28).toString());
            Valid.SetTgl(TanggalPulang,tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),20).toString());
            TglLahir.setText(Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?",TNoRM.getText()));
        }
    }

    private void simpanKunjungan() {
        try {
            headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers2.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
            headers2.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers2.add("X-Signature",api.getHmac());
            headers2.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
            if(chkKunjungan.isSelected()==true){
                if(ChkRujukLanjut.isSelected()==false){
                    diagnosa2="null";
                    if(!KdDiagnosa2.getText().equals("")){
                        diagnosa2="\""+KdDiagnosa2.getText()+"\"";
                    }
                    diagnosa3="null";
                    if(!KdDiagnosa3.getText().equals("")){
                        diagnosa3="\""+KdDiagnosa3.getText()+"\"";
                    }
                    requestJson ="{" +
                                    "\"noKunjungan\": null," +
                                    "\"noKartu\": \""+NoKartu.getText()+"\"," +
                                    "\"tglDaftar\": \""+TanggalDaftar.getSelectedItem()+"\"," +
                                    "\"kdPoli\": \""+KdPoliTujuan.getText()+"\"," +
                                    "\"keluhan\": \""+Keluhan.getText()+"\"," +
                                    "\"kdSadar\": \""+KdSadar.getText()+"\"," +
                                    "\"sistole\": "+Sistole.getText()+"," +
                                    "\"diastole\": "+Diastole.getText()+"," +
                                    "\"beratBadan\": "+BeratBadan.getText()+"," +
                                    "\"tinggiBadan\": "+TinggiBadan.getText()+"," +
                                    "\"respRate\": "+Respiratory.getText()+"," +
                                    "\"heartRate\": "+Heartrate.getText()+"," +
                                    "\"terapi\": \""+Terapi.getText()+"\"," +
                                    "\"kdStatusPulang\": \"3\"," +
                                    "\"tglPulang\": \""+TanggalPulang.getSelectedItem()+"\"," +
                                    "\"kdDokter\": \""+KdTenagaMedis.getText()+"\"," +
                                    "\"kdDiag1\": \""+KdDiagnosa1.getText()+"\"," +
                                    "\"kdDiag2\": "+diagnosa2+"," +
                                    "\"kdDiag3\": "+diagnosa3+"," +
                                    "\"kdPoliRujukInternal\":null," +
                                    "\"rujukLanjut\": null," +
                                    "\"kdTacc\": 0," +
                                    "\"alasanTacc\": null" +
                                  "}";
                    System.out.println(requestJson);
                    requestEntity = new HttpEntity(requestJson,headers2);
                    requestJson=api.getRest().exchange(URL+"/kunjungan", HttpMethod.POST, requestEntity, String.class).getBody();
                    System.out.println(requestJson);
                    root = mapper.readTree(requestJson);
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("201")){
                        response = root.path("response").path("message");
                        if(Sequel.menyimpantf2("pcare_kunjungan_umum","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Urut",29,new String[]{
                            TNoRw.getText(),response.asText(),Valid.SetTgl(TanggalDaftar.getSelectedItem()+""),TNoRM.getText(),TPasien.getText(),
                            NoKartu.getText(),KdPoliTujuan.getText(),NmPoliTujuan.getText(),Valid.MaxTeks(Keluhan.getText(),400),KdSadar.getText(),NmSadar.getText(),
                            Sistole.getText(),Diastole.getText(),BeratBadan.getText(),TinggiBadan.getText(),Respiratory.getText(),Heartrate.getText(),
                            Valid.MaxTeks(Terapi.getText(),400),"3","Berobat Jalan",Valid.SetTgl(TanggalPulang.getSelectedItem()+""),
                            KdTenagaMedis.getText(),Valid.MaxTeks(NmTenagaMedis.getText(),50),KdDiagnosa1.getText(),Valid.MaxTeks(NmDiagnosa1.getText(),400),KdDiagnosa2.getText(),
                            Valid.MaxTeks(NmDiagnosa2.getText(),400),KdDiagnosa3.getText(),Valid.MaxTeks(NmDiagnosa3.getText(),400)
                        })==true){
                            Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdDiagnosa1.getText(),NmDiagnosa1.getText(),NmDiagnosa1.getText(),"-","-","Tidak Menular"});
                            if(Sequel.cariInteger(
                                    "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                                    "inner join reg_periksa inner join pasien on "+
                                    "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                                    "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                    "pasien.no_rkm_medis='"+TNoRM.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdDiagnosa1.getText()+"'")>0){
                                Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa1.getText(),"Ralan","1","Lama"});
                            }else{
                                Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa1.getText(),"Ralan","1","Baru"});
                            }

                            if(!NmDiagnosa2.equals("")){
                                Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdDiagnosa2.getText(),NmDiagnosa2.getText(),NmDiagnosa2.getText(),"-","-","Tidak Menular"});
                                if(Sequel.cariInteger(
                                        "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                                        "inner join reg_periksa inner join pasien on "+
                                        "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pasien.no_rkm_medis='"+TNoRM.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdDiagnosa2.getText()+"'")>0){
                                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa2.getText(),"Ralan","2","Lama"});
                                }else{
                                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa2.getText(),"Ralan","2","Baru"});
                                }
                            }

                            if(!NmDiagnosa3.equals("")){
                                Sequel.menyimpan2("penyakit","?,?,?,?,?,?","Penyakit",6,new String[]{KdDiagnosa3.getText(),NmDiagnosa3.getText(),NmDiagnosa3.getText(),"-","-","Tidak Menular"});
                                if(Sequel.cariInteger(
                                        "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                                        "inner join reg_periksa inner join pasien on "+
                                        "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pasien.no_rkm_medis='"+TNoRM.getText()+"' and diagnosa_pasien.kd_penyakit='"+KdDiagnosa3.getText()+"'")>0){
                                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa3.getText(),"Ralan","3","Lama"});
                                }else{
                                    Sequel.menyimpan2("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{TNoRw.getText(),KdDiagnosa3.getText(),"Ralan","3","Baru"});
                                }
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }
                }
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi Bridging : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
            }else if(ex.toString().contains("500")){
                JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
            }else if(ex.toString().contains("401")){
                JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
            }else if(ex.toString().contains("408")){
                JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
            }else if(ex.toString().contains("424")){
                JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
            }else if(ex.toString().contains("412")){
                JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
            }else if(ex.toString().contains("204")){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
            }
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
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
            root = mapper.readTree(restTemplate.exchange(URL+"/pendaftaran/peserta/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),5).toString()+"/tglDaftar/"+Valid.SetTgl3(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),1).toString())+"/noUrut/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),18).toString()+"/kdPoli/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),6).toString(), HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("pcare_pendaftaran","no_rawat",tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),0).toString());
                tampil();
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
            }else if(e.toString().contains("500")){
                JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
            }else if(e.toString().contains("401")){
                JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
            }else if(e.toString().contains("408")){
                JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
            }else if(e.toString().contains("424")){
                JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
            }else if(e.toString().contains("412")){
                JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
            }else if(e.toString().contains("204")){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
            }
        }
    }
    
    @Test
    public void bodyWithDeleteRequest2() throws Exception {
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
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
            mapper = new ObjectMapper();
            root = mapper.readTree(restTemplate.exchange(URL+"/pendaftaran/peserta/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),5).toString()+"/tglDaftar/"+Valid.SetTgl3(tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),1).toString())+"/noUrut/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),18).toString()+"/kdPoli/"+tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),6).toString(), HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("pcare_pendaftaran","no_rawat",tbPendaftaran.getValueAt(tbPendaftaran.getSelectedRow(),0).toString());
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
            }else if(e.toString().contains("500")){
                JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
            }else if(e.toString().contains("401")){
                JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
            }else if(e.toString().contains("408")){
                JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
            }else if(e.toString().contains("424")){
                JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
            }else if(e.toString().contains("412")){
                JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
            }else if(e.toString().contains("204")){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
            }
        }
    }
    
    @Test
    public void bodyWithDeleteRequest3() throws Exception {
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
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
            root = mapper.readTree(restTemplate.exchange(URL+"/kunjungan/"+tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),1).toString(), HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("pcare_kunjungan_umum","no_rawat",tbKunjungan.getValueAt(tbKunjungan.getSelectedRow(),0).toString());
                tampil2();
            }else{
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }
        } catch (Exception e) {   
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
            }else if(e.toString().contains("500")){
                JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
            }else if(e.toString().contains("401")){
                JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
            }else if(e.toString().contains("408")){
                JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
            }else if(e.toString().contains("424")){
                JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
            }else if(e.toString().contains("412")){
                JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
            }else if(e.toString().contains("204")){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
            }
        }
    }
    
}

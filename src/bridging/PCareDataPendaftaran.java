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
import fungsi.var;
import java.awt.Dimension;
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
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


/**
 *
 * @author perpustakaan
 */
public final class PCareDataPendaftaran extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd"); 
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
    private String URL = "";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PCareDataPendaftaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
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
            URL=prop.getProperty("URLAPIPCARE");
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
        TabRawat.setBorder(null);
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

        TanggalDaftar.setEditable(false);
        TanggalDaftar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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
        jLabel27.setBounds(230, 102, 108, 23);

        JenisKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kunjungan Sakit", "Kunjungan Sehat" }));
        JenisKunjungan.setName("JenisKunjungan"); // NOI18N
        JenisKunjungan.setOpaque(false);
        JenisKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(JenisKunjungan);
        JenisKunjungan.setBounds(341, 102, 150, 23);

        Perawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10 Rawat Jalan", "20 Rawat Inap", "50 Promotif Preventif" }));
        Perawatan.setName("Perawatan"); // NOI18N
        Perawatan.setOpaque(false);
        Perawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanKeyPressed(evt);
            }
        });
        FormInput.add(Perawatan);
        Perawatan.setBounds(577, 102, 150, 23);

        jLabel28.setText("Perawatan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(504, 102, 70, 23);

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

        TinggiBadan.setHighlighter(null);
        TinggiBadan.setName("TinggiBadan"); // NOI18N
        TinggiBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiBadanKeyPressed(evt);
            }
        });
        FormInput.add(TinggiBadan);
        TinggiBadan.setBounds(130, 182, 60, 23);

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

        Sistole.setHighlighter(null);
        Sistole.setName("Sistole"); // NOI18N
        Sistole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SistoleKeyPressed(evt);
            }
        });
        FormInput.add(Sistole);
        Sistole.setBounds(340, 182, 60, 23);

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

        chkSubspesialis.setText("Subspesilias :");
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

        TanggalKunjungan.setEditable(false);
        TanggalKunjungan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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

        TanggalPulang.setEditable(false);
        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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

        TanggalEstRujuk.setEditable(false);
        TanggalEstRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalEstRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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
        TACC.setOpaque(false);
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
        TACC.setBounds(103, 530, 170, 23);

        jLabel35.setText("Alasan TACC :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(290, 530, 80, 23);

        AlasanTACC.setEnabled(false);
        AlasanTACC.setName("AlasanTACC"); // NOI18N
        AlasanTACC.setOpaque(false);
        AlasanTACC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanTACCKeyPressed(evt);
            }
        });
        FormInput.add(AlasanTACC);
        AlasanTACC.setBounds(373, 530, 355, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pendaftaran & Kunjungan", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2018" }));
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

        TabRawat.addTab("Data Pendaftaran", internalFrame4);

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
                   
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        
}//GEN-LAST:event_tbObatKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        
    }//GEN-LAST:event_KeluhanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(var.getform().equals("DlgReg")||var.getform().equals("DlgIGD")||var.getform().equals("DlgKamarInap")){
            NoKartu.setText(Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText()));
            if(NoKartu.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Pasien tidak mempunyai kepesertaan BPJS");
                dispose();
            }else{
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    String otorisasi=prop.getProperty("USERPCARE")+":"+prop.getProperty("PASSPCARE")+":095";
                    headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                    HttpEntity requestEntity = new HttpEntity(headers);
                    //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(api.getRest().exchange(URL+"/peserta/"+NoKartu.getText(), HttpMethod.GET, requestEntity, String.class).getBody());
                    JsonNode nameNode = root.path("metaData");
                    //System.out.println("code : "+nameNode.path("code").asText());
                    //System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("message").asText().equals("OK")){
                        JsonNode response = root.path("response");
                        if(response.path("ketAktif").asText().equals("AKTIF")){
                            TPasien.setText(response.path("nama").asText());
                            TglLahir.setText(response.path("tglLahir").asText());
                            JK.setText(response.path("sex").asText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                            JenisPeserta.setText(response.path("jnsPeserta").path("nama").asText());
                            Status.setText(response.path("ketAktif").asText());                            
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
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server PCARE terputus...!");
                    }
                }                 
            } 
        }
    }//GEN-LAST:event_formWindowOpened

    private void TanggalDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalDaftarKeyPressed
        
    }//GEN-LAST:event_TanggalDaftarKeyPressed

    private void JenisKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKunjunganKeyPressed
        
    }//GEN-LAST:event_JenisKunjunganKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_PerawatanKeyPressed

    private void btnPoliTujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliTujuanActionPerformed
        pilihan=1;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliTujuanActionPerformed

    private void btnPoliTujuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliTujuanKeyPressed
        
    }//GEN-LAST:event_btnPoliTujuanKeyPressed

    private void TinggiBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggiBadanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TinggiBadanKeyPressed

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void SistoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SistoleKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SistoleKeyPressed

    private void DiastoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiastoleKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiastoleKeyPressed

    private void RespiratoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespiratoryKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RespiratoryKeyPressed

    private void HeartrateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HeartrateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HeartrateKeyPressed

    private void TanggalKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKunjunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKunjunganKeyPressed

    private void BtnKesadaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKesadaranActionPerformed
        kesadaran.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kesadaran.setLocationRelativeTo(internalFrame1);
        kesadaran.setVisible(true);
    }//GEN-LAST:event_BtnKesadaranActionPerformed

    private void BtnKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKesadaranKeyPressed
        // TODO add your handling code here:
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
        // TODO add your handling code here:
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
    private widget.TextBox Respiratory;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Sistole;
    private widget.TextBox Status;
    private widget.ComboBox TACC;
    private widget.TextBox TCari;
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
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());  
        Keluhan.setText("-");
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
    }
    
    public void setNoRm(String norwt, Date tgl1,String status,String kdpoli,String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoliTujuan.setText(Sequel.cariIsi("select kd_poli_pcare from maping_poliklinik_pcare where kd_poli_rs=?",kdpoli));
        NmPoliTujuan.setText(Sequel.cariIsi("select nm_poli_pcare from maping_poliklinik_pcare where kd_poli_pcare=?",KdPoliTujuan.getText()));
        isRawat();            
    }
      
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getbridging_pcare_daftar());
        BtnEdit.setEnabled(var.getbridging_pcare_daftar());
        BtnHapus.setEnabled(var.getbridging_pcare_daftar());
        BtnPrint.setEnabled(var.getbridging_pcare_daftar());
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
           
        }
    }
    
}

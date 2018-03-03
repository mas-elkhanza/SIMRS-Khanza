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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


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
    private int i=0,pilihan=1;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private BPJSApi api=new BPJSApi();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private BPJSCekNoKartu cekViaBPJSKartu=new BPJSCekNoKartu();
    private String no_peserta="", requestJson,URL="",jkel="",duplikat="",user="",penjamin="",jasaraharja="",BPJS="",Taspen="",Asabri="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public BPJSDataSEP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        WindowUpdatePulang.setSize(630,80);
        WindowRujukan.setSize(810,172);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tanggal SEP","Tanggal Rujukan", 
                "No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis Pelayanan","Catatan", "Kode Diagnosa", 
                "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas", 
                "Lokasi Laka Lantas", "User Input","Tgl.Lahir","Peserta",
                "J.Kel","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif","COB","Penjamin","No.Telp"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
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
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==22){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==23){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==24){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==25){
                column.setPreferredWidth(120);
            }else{
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NoRujukan.setDocument(new batasInput((byte)20).getKata(NoRujukan));
        Catatan.setDocument(new batasInput((byte)50).getKata(Catatan));
        Catatan1.setDocument(new batasInput((byte)50).getKata(Catatan1));
        LokasiLaka.setDocument(new batasInput((byte)100).getKata(LokasiLaka));
        
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
        
        try{
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting"));            
        }catch(Exception e){
            System.out.println(e);
        }

        try {
            user=var.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=var.getkode();
        }
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception e) {
            System.out.println("SEP XML : "+e);
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
        ppPulang = new javax.swing.JMenuItem();
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
        ppDetailSEPPeserta = new javax.swing.JMenuItem();
        ppRujukan = new javax.swing.JMenuItem();
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
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
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
        jLabel17 = new widget.Label();
        LokasiLaka = new widget.TextBox();
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

        Popup.setName("Popup"); // NOI18N

        ppSEP.setBackground(new java.awt.Color(255, 255, 255));
        ppSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP.setForeground(new java.awt.Color(102, 51, 0));
        ppSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP.setText("Print SEP");
        ppSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP.setIconTextGap(8);
        ppSEP.setName("ppSEP"); // NOI18N
        ppSEP.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEPBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP);

        ppPulang.setBackground(new java.awt.Color(255, 255, 255));
        ppPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPulang.setForeground(new java.awt.Color(102, 51, 0));
        ppPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPulang.setText("Update Tanggal Pulang");
        ppPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPulang.setIconTextGap(8);
        ppPulang.setName("ppPulang"); // NOI18N
        ppPulang.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPulangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPulang);

        ppPengajuan.setBackground(new java.awt.Color(255, 255, 255));
        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(102, 51, 0));
        ppPengajuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan.setText("Pengajuan SEP");
        ppPengajuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan.setIconTextGap(8);
        ppPengajuan.setName("ppPengajuan"); // NOI18N
        ppPengajuan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPengajuan);

        ppPengajuan1.setBackground(new java.awt.Color(255, 255, 255));
        ppPengajuan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan1.setForeground(new java.awt.Color(102, 51, 0));
        ppPengajuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan1.setText("Aproval SEP");
        ppPengajuan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan1.setIconTextGap(8);
        ppPengajuan1.setName("ppPengajuan1"); // NOI18N
        ppPengajuan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan1BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPengajuan1);

        ppDetailSEPPeserta.setBackground(new java.awt.Color(255, 255, 255));
        ppDetailSEPPeserta.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDetailSEPPeserta.setForeground(new java.awt.Color(102, 51, 0));
        ppDetailSEPPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDetailSEPPeserta.setText("Detail SEP Peserta");
        ppDetailSEPPeserta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDetailSEPPeserta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDetailSEPPeserta.setIconTextGap(8);
        ppDetailSEPPeserta.setName("ppDetailSEPPeserta"); // NOI18N
        ppDetailSEPPeserta.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDetailSEPPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDetailSEPPesertaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDetailSEPPeserta);

        ppRujukan.setBackground(new java.awt.Color(255, 255, 255));
        ppRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRujukan.setForeground(new java.awt.Color(102, 51, 0));
        ppRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukan.setText("Buat Rujukan Keluar");
        ppRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRujukan.setIconTextGap(8);
        ppRujukan.setName("ppRujukan"); // NOI18N
        ppRujukan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRujukan);

        WindowUpdatePulang.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUpdatePulang.setName("WindowUpdatePulang"); // NOI18N
        WindowUpdatePulang.setUndecorated(true);
        WindowUpdatePulang.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Tanggal Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(245, 250, 240));
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

        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018 19:19:00" }));
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

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Buat Rujukan Keluar VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaBawah(new java.awt.Color(245, 250, 240));
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
        TanggalRujukKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018" }));
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
        NmPpkRujukan1.setBounds(182, 55, 180, 23);

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
        btnPPKRujukan1.setBounds(365, 55, 28, 23);

        jLabel31.setText("Jns.Pelayanan :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame6.add(jLabel31);
        jLabel31.setBounds(415, 55, 85, 23);

        JenisPelayanan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan1.setSelectedIndex(1);
        JenisPelayanan1.setName("JenisPelayanan1"); // NOI18N
        JenisPelayanan1.setOpaque(false);
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
        JenisPelayanan1.setBounds(504, 55, 288, 23);

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
        NmPenyakit1.setBounds(182, 85, 180, 23);

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
        btnDiagnosa1.setBounds(365, 85, 28, 23);

        LabelPoli1.setText("Poli Tujuan :");
        LabelPoli1.setName("LabelPoli1"); // NOI18N
        internalFrame6.add(LabelPoli1);
        LabelPoli1.setBounds(415, 85, 85, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame6.add(KdPoli1);
        KdPoli1.setBounds(504, 85, 75, 23);

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

        TipeRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Penuh", "1. Partial ", "2. Rujuk Balik" }));
        TipeRujukan.setName("TipeRujukan"); // NOI18N
        TipeRujukan.setOpaque(false);
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
        TipeRujukan.setBounds(283, 25, 110, 23);

        jLabel34.setText("Catatan :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(415, 25, 85, 23);

        Catatan1.setHighlighter(null);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        internalFrame6.add(Catatan1);
        Catatan1.setBounds(504, 25, 288, 23);

        WindowRujukan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(90, 120, 80))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018" }));
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 339));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setSelected(true);
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

        jLabel20.setText("Tgl.SEP :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(382, 102, 85, 23);

        TanggalSEP.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018 19:18:59" }));
        TanggalSEP.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalSEP.setName("TanggalSEP"); // NOI18N
        TanggalSEP.setOpaque(false);
        TanggalSEP.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSEPKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSEP);
        TanggalSEP.setBounds(470, 102, 220, 23);

        jLabel22.setText("Tgl.Rujuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 102, 90, 23);

        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-03-2018 19:18:59" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(93, 102, 220, 23);

        jLabel23.setText("No.Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel23);
        jLabel23.setBounds(504, 72, 70, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormInput.add(NoRujukan);
        NoRujukan.setBounds(577, 72, 150, 23);

        jLabel9.setText("PPK Pelayanan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(382, 132, 85, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormInput.add(KdPPK);
        KdPPK.setBounds(470, 132, 75, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormInput.add(NmPPK);
        NmPPK.setBounds(547, 132, 180, 23);

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
        jLabel13.setBounds(382, 162, 85, 23);

        jLabel14.setText("Catatan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(382, 222, 85, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormInput.add(Catatan);
        Catatan.setBounds(470, 222, 257, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan.setSelectedIndex(1);
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
        FormInput.add(JenisPelayanan);
        JenisPelayanan.setBounds(470, 162, 110, 23);

        LabelKelas.setText("Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormInput.add(LabelKelas);
        LabelKelas.setBounds(584, 162, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. Kelas 3" }));
        Kelas.setName("Kelas"); // NOI18N
        Kelas.setOpaque(false);
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(627, 162, 100, 23);

        jLabel16.setText("Laka Lantas :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 252, 90, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1. Ya" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.setOpaque(false);
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormInput.add(LakaLantas);
        LakaLantas.setBounds(93, 252, 257, 23);

        jLabel17.setText("Lokasi Laka :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(382, 252, 85, 23);

        LokasiLaka.setHighlighter(null);
        LokasiLaka.setName("LokasiLaka"); // NOI18N
        LokasiLaka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiLakaKeyPressed(evt);
            }
        });
        FormInput.add(LokasiLaka);
        LokasiLaka.setBounds(470, 252, 257, 23);

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

        jLabel27.setText("Asal Rujukan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 132, 90, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.setOpaque(false);
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(93, 132, 257, 23);

        jLabel15.setText("Eksekutif :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(382, 192, 85, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.setOpaque(false);
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormInput.add(Eksekutif);
        Eksekutif.setBounds(470, 192, 110, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormInput.add(LabelKelas1);
        LabelKelas1.setBounds(584, 192, 40, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.setOpaque(false);
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormInput.add(COB);
        COB.setBounds(627, 192, 100, 23);

        jLabel28.setText("Penjamin Laka :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 282, 90, 23);

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
        ChkAsa.setBounds(417, 282, 80, 23);

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
        ChkJasaRaharja.setBounds(93, 282, 110, 23);

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
        ChkBPJSTenaga.setBounds(197, 282, 140, 23);

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
        ChkTaspen.setBounds(335, 282, 80, 23);

        jLabel29.setText("No.Telp :");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel29);
        jLabel29.setBounds(504, 282, 100, 23);

        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(607, 282, 120, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

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
        }else{  
            if(JenisPelayanan.getSelectedIndex()==0){
                insertSEP();
            }else if(JenisPelayanan.getSelectedIndex()==1){
                if(NmPoli.getText().toLowerCase().contains("darurat")){
                    if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                            "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                            "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"%' and "+
                            "nmpolitujuan like '%darurat%'")>=3){
                        JOptionPane.showMessageDialog(null,"Maaf, sebelumnya sudah dilakukan 3x pembuatan SEP di jenis pelayanan yang sama..!!");
                        TCari.requestFocus();
                    }else{
                        insertSEP();
                    }
                }else if(!NmPoli.getText().toLowerCase().contains("darurat")){
                    if(Sequel.cariInteger("select count(no_kartu) from bridging_sep where "+
                            "no_kartu='"+no_peserta+"' and jnspelayanan='"+JenisPelayanan.getSelectedItem().toString().substring(0,1)+"' "+
                            "and tglsep like '%"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"").substring(0,10)+"%' and "+
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
            Valid.pindah(evt,LokasiLaka,BtnBatal);
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
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                }
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
                        Taspen="4,";
                    }
                    if((ChkJasaRaharja.isSelected()==true)||(ChkBPJSTenaga.isSelected()==true)||(ChkTaspen.isSelected()==true)||(ChkAsa.isSelected()==true)){
                        penjamin=jasaraharja+BPJS+Taspen+penjamin;
                    }else{
                        penjamin="";
                    }
                    
                    URL = prop.getProperty("URLAPIBPJS")+"/Sep/Update";	

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
                                            "\"jaminan\": {"+
                                                "\"lakaLantas\":\""+LakaLantas.getSelectedItem().toString().substring(0,1)+"\"," +
                                                "\"penjamin\": \""+penjamin+"\","+
                                                "\"lokasiLaka\":\""+LokasiLaka.getText()+"\"," +
                                            "},"+                                            
                                            "\"noTelp\":\""+NoTelp.getText()+"\","+
                                            "\"user\":\""+user+"\"" +
                                           "}" +
                                     "}" +
                                 "}";
                    HttpEntity requestEntity = new HttpEntity(requestJson,headers);
                    RestTemplate rest = new RestTemplate();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    JsonNode nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    JsonNode response = root.path("response");
                    if(nameNode.path("code").asText().equals("200")){
                        Sequel.mengedit("bridging_sep",
                             "no_sep=?","no_sep=?,no_rawat=?,tglsep=?,tglrujukan=?,no_rujukan=?,kdppkrujukan=?,"+
                             "nmppkrujukan=?,kdppkpelayanan=?,nmppkpelayanan=?,jnspelayanan=?,catatan=?,diagawal=?,"+
                             "nmdiagnosaawal=?,kdpolitujuan=?,nmpolitujuan=?,klsrawat=?,lakalantas=?,lokasilaka=?,"+
                             "user=?,nomr=?,nama_pasien=?,tanggal_lahir=?,peserta=?,jkel=?,no_kartu=?,asal_rujukan=?,eksekutif=?,cob=?,penjamin=?,notelep=?",31,new String[]{
                             response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                             Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                             NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                             JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                             NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                             LakaLantas.getSelectedItem().toString().substring(0,1),LokasiLaka.getText(),user, 
                             TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                             AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                             COB.getSelectedItem().toString(),penjamin,NoTelp.getText(),
                             tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                         });
                         Sequel.mengedit("rujuk_masuk","no_rawat=?","no_rawat=?,perujuk=?,no_rujuk=?",4,new String[]{
                             TNoRw.getText(),NmPpkRujukan.getText(),NoRujukan.getText(),
                             tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
                         });
                         emptTeks();                         
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
        faskes.dispose();
        penyakit.dispose();
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
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptBridgingDaftar.jrxml","report","::[ Data Bridging SEP ]::",
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"+
                    "if(bridging_sep.lakalantas='1','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),bridging_sep.lokasilaka,bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang from bridging_sep where "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.no_sep like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.nomr like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.nama_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.nmppkrujukan like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.diagawal like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.nmdiagnosaawal like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.tglsep between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and bridging_sep.nmpolitujuan like '%"+TCari.getText().trim()+"%' order by bridging_sep.tglsep",param);
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
            tampil();
            TCari.setText("");
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
        faskes.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        faskes.setLocationRelativeTo(internalFrame1);        
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt,AsalRujukan,btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        pilihan=1;
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);        
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,btnPPKRujukan,btnPoli);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        pilihan=1;
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);        
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        Valid.pindah(evt,btnDiagnosa,LakaLantas);
    }//GEN-LAST:event_btnPoliKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,COB,LokasiLaka);
    }//GEN-LAST:event_CatatanKeyPressed

    private void LokasiLakaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiLakaKeyPressed
        Valid.pindah(evt,Catatan,BtnSimpan);
    }//GEN-LAST:event_LokasiLakaKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        if(var.getform().equals("DlgReg")||var.getform().equals("DlgIGD")||var.getform().equals("DlgKamarInap")){
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ppSEPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEPBtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("logo",Sequel.cariGambar("select bpjs from gambar")); 
            if(JenisPelayanan.getSelectedIndex()==0){
                Valid.MyReport("rptBridgingSEP.jrxml","report","::[ Cetak SEP ]::","select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                        "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                        "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                        "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                        "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                        "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"+
                        "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),bridging_sep.lokasilaka,bridging_sep.user, "+
                        "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }else{
                Valid.MyReport("rptBridgingSEP2.jrxml","report","::[ Cetak SEP ]::","select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                        "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                        "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                        "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                        "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                        "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"+
                        "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),bridging_sep.lokasilaka,bridging_sep.user, "+
                        "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
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
                URL = prop.getProperty("URLAPIBPJS")+"/Sep/updtglplg";	

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
                                        "\"noSep\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"," +
                                        "\"tglPulang\":\""+Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+"\"," +
                                        "\"user\":\""+user+"\"" +                                            
                                       "}" +
                                 "}" +
                             "}";
                HttpEntity requestEntity = new HttpEntity(requestJson,headers);
                RestTemplate rest = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                JsonNode nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                JsonNode response = root.path("response");
                if(nameNode.path("code").asText().equals("200")){
                    Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                         Valid.SetTgl(TanggalPulang.getSelectedItem()+"")+" "+TanggalPulang.getSelectedItem().toString().substring(11,19),
                         tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    });
                    emptTeks();                         
                    tampil();     
                    JOptionPane.showMessageDialog(null,"Proses update pulang selesai..!!");
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
        Valid.pindah(evt,btnPoli,JenisPelayanan);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt,LakaLantas,Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt,JenisPelayanan,Eksekutif);
    }//GEN-LAST:event_KelasKeyPressed

    private void ppPengajuanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            try {
                URL = prop.getProperty("URLAPIBPJS")+"/Sep/pengajuanSEP";	

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
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
                HttpEntity requestEntity = new HttpEntity(requestJson,headers);
                RestTemplate rest = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                JsonNode nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                JsonNode response = root.path("response");
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
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dimapping transaksinya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuanBtnPrintActionPerformed

    private void ppDetailSEPPesertaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDetailSEPPesertaBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            BPJSCekDetailSEP detail=new BPJSCekDetailSEP(null,true);
            detail.tampil(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            detail.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
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
        Valid.pindah(evt, TanggalSEP,btnPPKRujukan);
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
        // TODO add your handling code here:
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(tbObat.getSelectedRow()!= -1){
            try {
                URL = prop.getProperty("URLAPIBPJS")+"/Sep/aprovalSEP";	

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
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
                HttpEntity requestEntity = new HttpEntity(requestJson,headers);
                RestTemplate rest = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                JsonNode nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                JsonNode response = root.path("response");
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
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dimapping transaksinya...!!!!");
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
        }else if(KdPoli1.getText().trim().equals("")||NmPoli1.getText().trim().equals("")){
            Valid.textKosong(KdPoli1, "Poli Tujuan");        
        }else{  
            try {
                URL = prop.getProperty("URLAPIBPJS")+"/Rujukan/insert";	
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                headers.add("X-Signature",api.getHmac());
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
                HttpEntity requestEntity = new HttpEntity(requestJson,headers);
                RestTemplate rest = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                JsonNode nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                JsonNode response = root.path("response");
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
        faskes.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
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
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);        
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosa1ActionPerformed

    private void btnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosa1KeyPressed

    private void btnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli1ActionPerformed
        pilihan=2;
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);        
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoli1ActionPerformed

    private void btnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1KeyPressed

    private void TipeRujukanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TipeRujukanItemStateChanged
        // TODO add your handling code here:
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
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.ComboBox COB;
    private widget.TextBox Catatan;
    private widget.TextBox Catatan1;
    private widget.CekBox ChkAsa;
    private widget.CekBox ChkBPJSTenaga;
    private widget.CekBox ChkInput;
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
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPenyakit1;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox KdPpkRujukan1;
    private widget.ComboBox Kelas;
    private widget.Label LCount;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli1;
    private widget.ComboBox LakaLantas;
    private widget.TextBox LokasiLaka;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPenyakit1;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NmPpkRujukan1;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoTelp;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalRujukKeluar;
    private widget.Tanggal TanggalSEP;
    private widget.TextBox TglLahir;
    private widget.ComboBox TipeRujukan;
    private javax.swing.JDialog WindowRujukan;
    private javax.swing.JDialog WindowUpdatePulang;
    private widget.Button btnDiagnosa;
    private widget.Button btnDiagnosa1;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPPKRujukan1;
    private widget.Button btnPoli;
    private widget.Button btnPoli1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppDetailSEPPeserta;
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private javax.swing.JMenuItem ppPulang;
    private javax.swing.JMenuItem ppRujukan;
    private javax.swing.JMenuItem ppSEP;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"+
                    "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"+
                    "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"+
                    "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"+
                    "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"+
                    "if(bridging_sep.lakalantas='0','0. Tidak','1. Ya'),bridging_sep.lokasilaka,bridging_sep.user, "+
                    "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,"+
                    "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.no_sep like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nomr like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nama_pasien like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmppkrujukan like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.diagawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmdiagnosaawal like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.no_rawat like ? or "+
                    "bridging_sep.tglsep between ? and ? and bridging_sep.nmpolitujuan like ? order by bridging_sep.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
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
                        rs.getString(29),rs.getString(30),rs.getString(31)});
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
        Kelas.setSelectedIndex(0);
        LakaLantas.setSelectedIndex(0);
        LokasiLaka.setText("");
        TNoRM.setText("");
        NoRujukan.requestFocus();
        ChkAsa.setSelected(false);
        ChkBPJSTenaga.setSelected(false);
        ChkInput.setSelected(false);
        ChkJasaRaharja.setSelected(false);
        ChkTaspen.setSelected(false);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"' ","BR/"+dateformat.format(TanggalSEP.getDate())+"/",4,NoBalasan);
        
    }
    
    public void setNoRm(String norwt, Date tgl1,String status,String kdpoli,String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdPoli.setText(kdpoli);
        NmPoli.setText(namapoli);
        JenisPelayanan.setSelectedItem(status);
        JenisPelayananItemStateChanged(null);
        isRawat();            
    }
      
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getbpjs_sep());
        BtnHapus.setEnabled(var.getbpjs_sep());
        BtnPrint.setEnabled(var.getbpjs_sep());
        BtnEdit.setEnabled(var.getbpjs_sep());      
        ppDetailSEPPeserta.setEnabled(var.getbpjs_sep());
        ppPengajuan.setEnabled(var.getbpjs_sep());
        ppPengajuan1.setEnabled(var.getbpjs_sep());
        ppPulang.setEnabled(var.getbpjs_sep());
        ppSEP.setEnabled(var.getbpjs_sep());
        ppRujukan.setEnabled(var.getbpjs_rujukan_keluar());
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
            LokasiLaka.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            JenisPeserta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            NoKartu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            AsalRujukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Eksekutif.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            COB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().contains("1")){
                ChkJasaRaharja.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().contains("2")){
                ChkBPJSTenaga.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().contains("3")){
                ChkTaspen.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().contains("3")){
                ChkAsa.setSelected(true);
            }
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Valid.SetTgl2(TanggalSEP,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl2(TanggalRujuk,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());            
            Status.setText("AKTIF");
            
        }
    }
    
    RestTemplate restTemplate = new RestTemplate();
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
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        });
        
        try {
            URL = prop.getProperty("URLAPIBPJS")+"/SEP/Delete";	

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            requestJson ="{\"request\":{\"t_sep\":{\"noSep\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\",\"user\":\""+user+"\"}}}";            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,new HttpEntity<String>(requestJson,headers), String.class).getBody());
            JsonNode nameNode = root.path("metaData");
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
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,339));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
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
                Taspen="4,";
            }
            if((ChkJasaRaharja.isSelected()==true)||(ChkBPJSTenaga.isSelected()==true)||(ChkTaspen.isSelected()==true)||(ChkAsa.isSelected()==true)){
                penjamin=jasaraharja+BPJS+Taspen+penjamin;
            }else{
                penjamin="";
            }
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
                                    "\"jaminan\": {"+
                                        "\"lakaLantas\":\""+LakaLantas.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"penjamin\": \""+penjamin+"\","+
                                        "\"lokasiLaka\":\""+LokasiLaka.getText()+"\"" +
                                    "},"+
                                    "\"noTelp\": \""+NoTelp.getText()+"\","+
                                    "\"user\":\""+user+"\"" +
                                   "}" +
                             "}" +
                         "}";
            HttpEntity requestEntity = new HttpEntity(requestJson,headers);
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            JsonNode response = root.path("response").path("sep").path("noSep");
            if(nameNode.path("code").asText().equals("200")){
                System.out.println("SEP : "+response.asText());
                 if(Sequel.menyimpantf("bridging_sep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","SEP",31,new String[]{
                     response.asText(),TNoRw.getText(),Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                     Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                     NoRujukan.getText(),KdPpkRujukan.getText(), NmPpkRujukan.getText(),KdPPK.getText(), NmPPK.getText(), 
                     JenisPelayanan.getSelectedItem().toString().substring(0,1), Catatan.getText(),KdPenyakit.getText(), 
                     NmPenyakit.getText(),KdPoli.getText(),NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0,1), 
                     LakaLantas.getSelectedItem().toString().substring(0,1),LokasiLaka.getText(),user, 
                     TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JenisPeserta.getText(),JK.getText(),NoKartu.getText(),
                     "0000-00-00 00:00:00",AsalRujukan.getSelectedItem().toString(),Eksekutif.getSelectedItem().toString(),
                     COB.getSelectedItem().toString(),penjamin,NoTelp.getText()
                 })==true){
                     Sequel.menyimpan("rujuk_masuk","?,?,?,?,?,?,?,?,?,?",10,new String[]{
                         TNoRw.getText(),NmPpkRujukan.getText(),"-",NoRujukan.getText(),"0",NmPpkRujukan.getText(),KdPenyakit.getText(),"-",
                         "-",NoBalasan.getText()
                     });
                     if(JenisPelayanan.getSelectedIndex()==1){
                        try {
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
                                                    "\"tglPulang\":\""+Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+"\"," +
                                                    "\"user\":\""+user+"\"" +                                            
                                                   "}" +
                                             "}" +
                                         "}";
                            HttpEntity requestEntity2 = new HttpEntity(requestJson,headers2);
                            RestTemplate rest2 = new RestTemplate();
                            ObjectMapper mapper2 = new ObjectMapper();
                            JsonNode root2 = mapper2.readTree(rest2.exchange(URL, HttpMethod.PUT, requestEntity2, String.class).getBody());
                            JsonNode nameNode2 = root2.path("metaData");
                            System.out.println("code : "+nameNode2.path("code").asText());
                            System.out.println("message : "+nameNode2.path("message").asText());
                            JsonNode response2 = root2.path("response");
                            if(nameNode2.path("code").asText().equals("200")){
                                Sequel.mengedit("bridging_sep","no_sep=?","tglpulang=?",2,new String[]{                             
                                     Valid.SetTgl(TanggalSEP.getSelectedItem()+"")+" "+TanggalSEP.getSelectedItem().toString().substring(11,19),
                                     response.asText()
                                });
                                emptTeks();                         
                                tampil();     
                            }else{
                                JOptionPane.showMessageDialog(null,nameNode2.path("message").asText());
                            }
                        }catch (Exception ex) {
                            System.out.println("Notifikasi Bridging : "+ex);
                            if(ex.toString().contains("UnknownHostException")){
                                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
                            }
                        }
                     }                         
                     emptTeks();                         
                     tampil();
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

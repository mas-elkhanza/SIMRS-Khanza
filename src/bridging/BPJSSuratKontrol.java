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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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

/**
 *
 * @author dosen
 */
public class BPJSSuratKontrol extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String link="",requestJson="",URL="",user="",URUTNOREG="",utc="",JADIKANBOOKINGSURATKONTROLAPIBPJS="no",kodedokter="",kodepoli="",noreg="";
    private ApiBPJS api=new ApiBPJS();
    private boolean status=false;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public BPJSSuratKontrol(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.SEP","No.Kartu","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Diagnosa","Tgl.Surat",
                "No.Surat","Tgl.Kontrol","Kode Dokter","Nama Dokter/Sepesialis","Kode Poli","Nama Poli/Unit","Status PRB",
                "HBA1C","GDP","GD2JPP","eGFR","TD Sis","TD Dias","LDL","Rerata TD Sis","Rerata TD Dias","Jantung Koroner",
                "Stroke","Vaskular Perifer","Aritmia","Atrial Fibrilasi","RR Istirahat","Sesak Napas 3 Bulan",
                "Nyeri Dada 3 Bulan","Sesak Napas Aktivitas","Nyeri Dada Aktivitas","Terkontrol","Gejala 2x Minggu",
                "Bangun Malam","Keterbatasan Fisik","Fungsi Paru","Skor MMRC","Eksaserbasi 1 Tahun","Mampu Aktivitas",
                "Epileptik 6 Bulan","Efek Samping OAB","Hamil Menyusui","Remisi","Terapi Rumatan","Usia","Asam Urat",
                "Remisi SLE","Hamil"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(125);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(50);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(50);
            }else if(i==20){
                column.setPreferredWidth(50);
            }else if(i==21){
                column.setPreferredWidth(50);
            }else if(i==22){
                column.setPreferredWidth(50);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(90);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(70);
            }else if(i==31){
                column.setPreferredWidth(110);
            }else if(i==32){
                column.setPreferredWidth(100);
            }else if(i==33){
                column.setPreferredWidth(115);
            }else if(i==34){
                column.setPreferredWidth(110);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(90);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(100);
            }else if(i==39){
                column.setPreferredWidth(67);
            }else if(i==40){
                column.setPreferredWidth(67);
            }else if(i==41){
                column.setPreferredWidth(110);
            }else if(i==42){
                column.setPreferredWidth(90);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(100);
            }else if(i==45){
                column.setPreferredWidth(90);
            }else if(i==46){
                column.setPreferredWidth(50);
            }else if(i==47){
                column.setPreferredWidth(90);
            }else if(i==48){
                column.setPreferredWidth(50);
            }else if(i==49){
                column.setPreferredWidth(65);
            }else if(i==50){
                column.setPreferredWidth(65);
            }else if(i==51){
                column.setPreferredWidth(90);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        NoRawat.setDocument(new batasInput((byte)15).getKata(NoRawat));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        HBA1C.setDocument(new batasInput((byte)5).getOnlyAngka(HBA1C));
        GDP.setDocument(new batasInput((byte)5).getOnlyAngka(GDP));
        GD2JPP.setDocument(new batasInput((byte)5).getOnlyAngka(GD2JPP));
        eGFR.setDocument(new batasInput((byte)5).getOnlyAngka(eGFR));
        LDL.setDocument(new batasInput((byte)5).getOnlyAngka(LDL));
        TDSistolik.setDocument(new batasInput((byte)5).getOnlyAngka(TDSistolik));
        TDDiastolik.setDocument(new batasInput((byte)5).getOnlyAngka(TDDiastolik));
        RerataTDSistolik.setDocument(new batasInput((byte)5).getOnlyAngka(RerataTDSistolik));
        RerataTDDiastolik.setDocument(new batasInput((byte)5).getOnlyAngka(RerataTDDiastolik));
        RRIstirahat.setDocument(new batasInput((byte)5).getOnlyAngka(RRIstirahat));
        FungsiParu.setDocument(new batasInput((byte)5).getOnlyAngka(FungsiParu));
        SkorMMRC.setDocument(new batasInput((byte)5).getOnlyAngka(SkorMMRC));
        Remisi.setDocument(new batasInput((byte)5).getOnlyAngka(Remisi));
        Usia.setDocument(new batasInput((byte)5).getOnlyAngka(Usia));
        AsamUrat.setDocument(new batasInput((byte)5).getOnlyAngka(AsamUrat));
        RemisiSLE.setDocument(new batasInput((byte)5).getOnlyAngka(RemisiSLE));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        } 
        
        ChkInput.setSelected(false);
        isForm();
        
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
        
        try {
            URUTNOREG=koneksiDB.URUTNOREG();
        } catch (Exception ex) {
            URUTNOREG="";
        }
        
        try {
            JADIKANBOOKINGSURATKONTROLAPIBPJS=koneksiDB.JADIKANBOOKINGSURATKONTROLAPIBPJS();
        } catch (Exception ex) {
            JADIKANBOOKINGSURATKONTROLAPIBPJS="no";
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSurat = new javax.swing.JMenuItem();
        NoKartu = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        DTPTanggalSurat1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPTanggalSurat2 = new widget.Tanggal();
        LCount1 = new widget.Label();
        R2 = new widget.RadioButton();
        DTPTanggalKontrol1 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPTanggalKontrol2 = new widget.Tanggal();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        NoRawat = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDokter = new widget.TextBox();
        NoSEP = new widget.TextBox();
        TanggalSurat = new widget.Tanggal();
        jLabel10 = new widget.Label();
        KdDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel14 = new widget.Label();
        TanggalKontrol = new widget.Tanggal();
        jLabel15 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel12 = new widget.Label();
        NmPasien = new widget.TextBox();
        NoRM = new widget.TextBox();
        TglLahir = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel16 = new widget.Label();
        JK = new widget.TextBox();
        jLabel17 = new widget.Label();
        Diagnosa = new widget.TextBox();
        jLabel18 = new widget.Label();
        StatusPRB = new widget.ComboBox();
        jLabel19 = new widget.Label();
        HBA1C = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        GDP = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        GD2JPP = new widget.TextBox();
        jLabel26 = new widget.Label();
        eGFR = new widget.TextBox();
        jLabel27 = new widget.Label();
        TDSistolik = new widget.TextBox();
        jLabel28 = new widget.Label();
        TDDiastolik = new widget.TextBox();
        jLabel29 = new widget.Label();
        LDL = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        RerataTDSistolik = new widget.TextBox();
        RerataTDDiastolik = new widget.TextBox();
        jLabel33 = new widget.Label();
        JantungKoroner = new widget.ComboBox();
        jLabel34 = new widget.Label();
        Stroke = new widget.ComboBox();
        jLabel35 = new widget.Label();
        RRIstirahat = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        VaskularPerifer = new widget.ComboBox();
        jLabel38 = new widget.Label();
        Aritmia = new widget.ComboBox();
        jLabel39 = new widget.Label();
        AtrialFibrilasi = new widget.ComboBox();
        SesakNapas3Bulan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        NyeriDada3Bulan = new widget.ComboBox();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel45 = new widget.Label();
        SesakNapasAktivitas = new widget.ComboBox();
        jLabel44 = new widget.Label();
        jLabel46 = new widget.Label();
        NyeriDadaAktivitas = new widget.ComboBox();
        jLabel47 = new widget.Label();
        Terkontrol = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Gejala2xMinggu = new widget.ComboBox();
        jLabel50 = new widget.Label();
        BangunMalam = new widget.ComboBox();
        jLabel49 = new widget.Label();
        jLabel51 = new widget.Label();
        KeterbatasanFisik = new widget.ComboBox();
        jLabel52 = new widget.Label();
        SkorMMRC = new widget.TextBox();
        jLabel53 = new widget.Label();
        FungsiParu = new widget.TextBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        Eksaserbasi1Tahun = new widget.ComboBox();
        jLabel56 = new widget.Label();
        MampuAktivitas = new widget.ComboBox();
        jLabel57 = new widget.Label();
        Epileptik6Bulan = new widget.ComboBox();
        jLabel59 = new widget.Label();
        EfekSampingOAB = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel60 = new widget.Label();
        HamilMenyusui = new widget.ComboBox();
        jLabel61 = new widget.Label();
        TerapiRumatan = new widget.ComboBox();
        jLabel62 = new widget.Label();
        Remisi = new widget.TextBox();
        Usia = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel63 = new widget.Label();
        AsamUrat = new widget.TextBox();
        jLabel64 = new widget.Label();
        RemisiSLE = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Hamil = new widget.ComboBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSurat.setForeground(new java.awt.Color(50, 50, 50));
        MnSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSurat.setText("Surat Kontrol");
        MnSurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSurat.setName("MnSurat"); // NOI18N
        MnSurat.setPreferredSize(new java.awt.Dimension(160, 26));
        MnSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSurat);

        NoKartu.setEditable(false);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat Kontrol VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Tanggal Surat :");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(115, 23));
        panelCari.add(R1);

        DTPTanggalSurat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026" }));
        DTPTanggalSurat1.setDisplayFormat("dd-MM-yyyy");
        DTPTanggalSurat1.setName("DTPTanggalSurat1"); // NOI18N
        DTPTanggalSurat1.setOpaque(false);
        DTPTanggalSurat1.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPTanggalSurat1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPTanggalSurat1ItemStateChanged(evt);
            }
        });
        DTPTanggalSurat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTanggalSurat1KeyPressed(evt);
            }
        });
        panelCari.add(DTPTanggalSurat1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPTanggalSurat2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026" }));
        DTPTanggalSurat2.setDisplayFormat("dd-MM-yyyy");
        DTPTanggalSurat2.setName("DTPTanggalSurat2"); // NOI18N
        DTPTanggalSurat2.setOpaque(false);
        DTPTanggalSurat2.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPTanggalSurat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTanggalSurat2KeyPressed(evt);
            }
        });
        panelCari.add(DTPTanggalSurat2);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(45, 23));
        panelCari.add(LCount1);

        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Tanggal Kontrol :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(120, 23));
        panelCari.add(R2);

        DTPTanggalKontrol1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026" }));
        DTPTanggalKontrol1.setDisplayFormat("dd-MM-yyyy");
        DTPTanggalKontrol1.setName("DTPTanggalKontrol1"); // NOI18N
        DTPTanggalKontrol1.setOpaque(false);
        DTPTanggalKontrol1.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPTanggalKontrol1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPTanggalKontrol1ItemStateChanged(evt);
            }
        });
        DTPTanggalKontrol1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTanggalKontrol1KeyPressed(evt);
            }
        });
        panelCari.add(DTPTanggalKontrol1);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        DTPTanggalKontrol2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026" }));
        DTPTanggalKontrol2.setDisplayFormat("dd-MM-yyyy");
        DTPTanggalKontrol2.setName("DTPTanggalKontrol2"); // NOI18N
        DTPTanggalKontrol2.setOpaque(false);
        DTPTanggalKontrol2.setPreferredSize(new java.awt.Dimension(95, 23));
        DTPTanggalKontrol2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPTanggalKontrol2ItemStateChanged(evt);
            }
        });
        DTPTanggalKontrol2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTanggalKontrol2KeyPressed(evt);
            }
        });
        panelCari.add(DTPTanggalKontrol2);

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 475));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(740, 452));
        FormInput.setLayout(null);

        jLabel4.setText("No.SEP :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(232, 10, 50, 23);

        NoRawat.setEditable(false);
        NoRawat.setHighlighter(null);
        NoRawat.setName("NoRawat"); // NOI18N
        FormInput.add(NoRawat);
        NoRawat.setBounds(94, 10, 130, 23);

        jLabel9.setText("Spesialis/Sub :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 100, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(184, 100, 160, 23);

        NoSEP.setEditable(false);
        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        FormInput.add(NoSEP);
        NoSEP.setBounds(286, 10, 150, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSuratKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSurat);
        TanggalSurat.setBounds(381, 70, 95, 23);

        jLabel10.setText("Tanggal Surat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(277, 70, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(94, 100, 87, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
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
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(347, 100, 28, 23);

        jLabel11.setText("Unit/Poli :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(394, 100, 60, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(458, 100, 70, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(531, 100, 165, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('X');
        BtnPoli.setToolTipText("Alt+X");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        BtnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoliKeyPressed(evt);
            }
        });
        FormInput.add(BtnPoli);
        BtnPoli.setBounds(699, 100, 28, 23);

        jLabel14.setText("Tanggal Kontrol :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(491, 70, 100, 23);

        TanggalKontrol.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-01-2026 17:10:41" }));
        TanggalKontrol.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalKontrol.setName("TanggalKontrol"); // NOI18N
        TanggalKontrol.setOpaque(false);
        TanggalKontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKontrolKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKontrol);
        TanggalKontrol.setBounds(595, 70, 132, 23);

        jLabel15.setText("No.Surat :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 70, 90, 23);

        NoSurat.setEditable(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(94, 70, 170, 23);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 90, 23);

        jLabel12.setText("Pasien :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 90, 23);

        NmPasien.setEditable(false);
        NmPasien.setHighlighter(null);
        NmPasien.setName("NmPasien"); // NOI18N
        FormInput.add(NmPasien);
        NmPasien.setBounds(197, 40, 230, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(94, 40, 100, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(495, 40, 95, 23);

        jLabel13.setText("Tgl.Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(429, 40, 62, 23);

        jLabel16.setText("J.K. :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(588, 40, 40, 23);

        JK.setEditable(false);
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(632, 40, 95, 23);

        jLabel17.setText("Diagnosa :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(440, 10, 65, 23);

        Diagnosa.setEditable(false);
        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(509, 10, 218, 23);

        jLabel18.setText("Status PRB :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 130, 90, 23);

        StatusPRB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "01. Diabetes Melitus", "02. Hipertensi", "03. Asma", "04. Penyakit Jantung", "05. PPOK", "06. Skizofrenia", "07. Stroke", "08. Epilepsi", "09. SLE" }));
        StatusPRB.setName("StatusPRB"); // NOI18N
        StatusPRB.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(StatusPRB);
        StatusPRB.setBounds(94, 130, 200, 23);

        jLabel19.setText("Asesmen PRB :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 160, 90, 23);

        HBA1C.setHighlighter(null);
        HBA1C.setName("HBA1C"); // NOI18N
        FormInput.add(HBA1C);
        HBA1C.setBounds(104, 180, 50, 23);

        jLabel20.setText(":");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 180, 100, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("HBA1C");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(60, 180, 50, 23);

        GDP.setHighlighter(null);
        GDP.setName("GDP"); // NOI18N
        FormInput.add(GDP);
        GDP.setBounds(207, 180, 50, 23);

        jLabel23.setText("GDP :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(163, 180, 40, 23);

        jLabel24.setText("Rerata TD Diastolik :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(207, 210, 118, 23);

        GD2JPP.setHighlighter(null);
        GD2JPP.setName("GD2JPP"); // NOI18N
        FormInput.add(GD2JPP);
        GD2JPP.setBounds(325, 180, 50, 23);

        jLabel26.setText("eGFR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 180, 50, 23);

        eGFR.setHighlighter(null);
        eGFR.setName("eGFR"); // NOI18N
        FormInput.add(eGFR);
        eGFR.setBounds(432, 180, 50, 23);

        jLabel27.setText("TD :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(577, 180, 40, 23);

        TDSistolik.setHighlighter(null);
        TDSistolik.setName("TDSistolik"); // NOI18N
        FormInput.add(TDSistolik);
        TDSistolik.setBounds(621, 180, 50, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("/");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(664, 180, 20, 23);

        TDDiastolik.setHighlighter(null);
        TDDiastolik.setName("TDDiastolik"); // NOI18N
        FormInput.add(TDDiastolik);
        TDDiastolik.setBounds(677, 180, 50, 23);

        jLabel29.setText("LDL :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(487, 180, 40, 23);

        LDL.setHighlighter(null);
        LDL.setName("LDL"); // NOI18N
        FormInput.add(LDL);
        LDL.setBounds(531, 180, 50, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Rerata TD Sistolik");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(60, 210, 110, 23);

        jLabel31.setText(":");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 210, 154, 23);

        RerataTDSistolik.setHighlighter(null);
        RerataTDSistolik.setName("RerataTDSistolik"); // NOI18N
        FormInput.add(RerataTDSistolik);
        RerataTDSistolik.setBounds(158, 210, 50, 23);

        RerataTDDiastolik.setHighlighter(null);
        RerataTDDiastolik.setName("RerataTDDiastolik"); // NOI18N
        FormInput.add(RerataTDDiastolik);
        RerataTDDiastolik.setBounds(329, 210, 50, 23);

        jLabel33.setText("Jantung Koroner :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(385, 210, 99, 23);

        JantungKoroner.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        JantungKoroner.setName("JantungKoroner"); // NOI18N
        JantungKoroner.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(JantungKoroner);
        JantungKoroner.setBounds(488, 210, 90, 23);

        jLabel34.setText("Stroke :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(583, 210, 50, 23);

        Stroke.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Stroke.setName("Stroke"); // NOI18N
        Stroke.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Stroke);
        Stroke.setBounds(637, 210, 90, 23);

        jLabel35.setText("RR Istirahat :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(593, 240, 80, 23);

        RRIstirahat.setHighlighter(null);
        RRIstirahat.setName("RRIstirahat"); // NOI18N
        FormInput.add(RRIstirahat);
        RRIstirahat.setBounds(677, 240, 50, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Vaskular Perifer");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(60, 240, 110, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 240, 145, 23);

        VaskularPerifer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        VaskularPerifer.setName("VaskularPerifer"); // NOI18N
        VaskularPerifer.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(VaskularPerifer);
        VaskularPerifer.setBounds(149, 240, 90, 23);

        jLabel38.setText("Aritmia :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(245, 240, 60, 23);

        Aritmia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Aritmia.setName("Aritmia"); // NOI18N
        Aritmia.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Aritmia);
        Aritmia.setBounds(309, 240, 90, 23);

        jLabel39.setText("Atrial Fibrilasi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(405, 240, 90, 23);

        AtrialFibrilasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        AtrialFibrilasi.setName("AtrialFibrilasi"); // NOI18N
        AtrialFibrilasi.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(AtrialFibrilasi);
        AtrialFibrilasi.setBounds(499, 240, 90, 23);

        SesakNapas3Bulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        SesakNapas3Bulan.setName("SesakNapas3Bulan"); // NOI18N
        SesakNapas3Bulan.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(SesakNapas3Bulan);
        SesakNapas3Bulan.setBounds(171, 270, 90, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Sesak Napas 3 Bulan");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(60, 270, 130, 23);

        jLabel41.setText("Nyeri Dada 3 Bulan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(273, 270, 120, 23);

        NyeriDada3Bulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        NyeriDada3Bulan.setName("NyeriDada3Bulan"); // NOI18N
        NyeriDada3Bulan.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(NyeriDada3Bulan);
        NyeriDada3Bulan.setBounds(397, 270, 90, 23);

        jLabel42.setText("GD2JPP :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(251, 180, 70, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 270, 167, 23);

        jLabel45.setText("Sesak Napas Aktivitas :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(493, 270, 140, 23);

        SesakNapasAktivitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        SesakNapasAktivitas.setName("SesakNapasAktivitas"); // NOI18N
        SesakNapasAktivitas.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(SesakNapasAktivitas);
        SesakNapasAktivitas.setBounds(637, 270, 90, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Nyeri Dada Aktivitas");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(60, 300, 130, 23);

        jLabel46.setText(":");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(0, 300, 165, 23);

        NyeriDadaAktivitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        NyeriDadaAktivitas.setName("NyeriDadaAktivitas"); // NOI18N
        NyeriDadaAktivitas.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(NyeriDadaAktivitas);
        NyeriDadaAktivitas.setBounds(169, 300, 90, 23);

        jLabel47.setText("Terkontrol :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(266, 300, 79, 23);

        Terkontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Terkontrol.setName("Terkontrol"); // NOI18N
        Terkontrol.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Terkontrol);
        Terkontrol.setBounds(349, 300, 90, 23);

        jLabel48.setText("Gejala 2x/Lebih Dalam Seminggu :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(443, 300, 190, 23);

        Gejala2xMinggu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Gejala2xMinggu.setName("Gejala2xMinggu"); // NOI18N
        Gejala2xMinggu.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Gejala2xMinggu);
        Gejala2xMinggu.setBounds(637, 300, 90, 23);

        jLabel50.setText(":");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 330, 138, 23);

        BangunMalam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        BangunMalam.setName("BangunMalam"); // NOI18N
        BangunMalam.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(BangunMalam);
        BangunMalam.setBounds(142, 330, 90, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Bangun Malam");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(60, 330, 130, 23);

        jLabel51.setText("Keterbatasan Fisik :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(244, 330, 110, 23);

        KeterbatasanFisik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        KeterbatasanFisik.setName("KeterbatasanFisik"); // NOI18N
        KeterbatasanFisik.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(KeterbatasanFisik);
        KeterbatasanFisik.setBounds(358, 330, 90, 23);

        jLabel52.setText("Skor MMRC :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(593, 330, 80, 23);

        SkorMMRC.setHighlighter(null);
        SkorMMRC.setName("SkorMMRC"); // NOI18N
        FormInput.add(SkorMMRC);
        SkorMMRC.setBounds(677, 330, 50, 23);

        jLabel53.setText("Fungsi Paru :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(456, 330, 80, 23);

        FungsiParu.setHighlighter(null);
        FungsiParu.setName("FungsiParu"); // NOI18N
        FormInput.add(FungsiParu);
        FungsiParu.setBounds(540, 330, 50, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Eksaserbasi Selama 1 Tahun");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(60, 360, 150, 23);

        jLabel55.setText(":");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 360, 205, 23);

        Eksaserbasi1Tahun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Eksaserbasi1Tahun.setName("Eksaserbasi1Tahun"); // NOI18N
        Eksaserbasi1Tahun.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Eksaserbasi1Tahun);
        Eksaserbasi1Tahun.setBounds(209, 360, 90, 23);

        jLabel56.setText("Mampu Beraktivitas :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(306, 360, 120, 23);

        MampuAktivitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        MampuAktivitas.setName("MampuAktivitas"); // NOI18N
        MampuAktivitas.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(MampuAktivitas);
        MampuAktivitas.setBounds(430, 360, 90, 23);

        jLabel57.setText("Epileptik 6 Bulan :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(533, 360, 100, 23);

        Epileptik6Bulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Epileptik6Bulan.setName("Epileptik6Bulan"); // NOI18N
        Epileptik6Bulan.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Epileptik6Bulan);
        Epileptik6Bulan.setBounds(637, 360, 90, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 390, 157, 23);

        EfekSampingOAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        EfekSampingOAB.setName("EfekSampingOAB"); // NOI18N
        EfekSampingOAB.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(EfekSampingOAB);
        EfekSampingOAB.setBounds(161, 390, 90, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Efek Samping OAB");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(60, 390, 150, 23);

        jLabel60.setText("Menyusui :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(252, 390, 70, 23);

        HamilMenyusui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        HamilMenyusui.setName("HamilMenyusui"); // NOI18N
        HamilMenyusui.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(HamilMenyusui);
        HamilMenyusui.setBounds(326, 390, 90, 23);

        jLabel61.setText("Terapi Rumatan :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(533, 390, 100, 23);

        TerapiRumatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        TerapiRumatan.setName("TerapiRumatan"); // NOI18N
        TerapiRumatan.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(TerapiRumatan);
        TerapiRumatan.setBounds(637, 390, 90, 23);

        jLabel62.setText("Remisi :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(416, 390, 60, 23);

        Remisi.setHighlighter(null);
        Remisi.setName("Remisi"); // NOI18N
        FormInput.add(Remisi);
        Remisi.setBounds(480, 390, 50, 23);

        Usia.setHighlighter(null);
        Usia.setName("Usia"); // NOI18N
        FormInput.add(Usia);
        Usia.setBounds(92, 420, 50, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 420, 88, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Usia");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(60, 420, 50, 23);

        AsamUrat.setHighlighter(null);
        AsamUrat.setName("AsamUrat"); // NOI18N
        FormInput.add(AsamUrat);
        AsamUrat.setBounds(229, 420, 50, 23);

        jLabel64.setText("Asam Urat :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(145, 420, 80, 23);

        RemisiSLE.setHighlighter(null);
        RemisiSLE.setName("RemisiSLE"); // NOI18N
        FormInput.add(RemisiSLE);
        RemisiSLE.setBounds(365, 420, 50, 23);

        jLabel65.setText("RemisiSLE :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(291, 420, 70, 23);

        jLabel66.setText("Hamil :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(413, 420, 60, 23);

        Hamil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Tidak", "1. Ya" }));
        Hamil.setName("Hamil"); // NOI18N
        Hamil.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(Hamil);
        Hamil.setBounds(477, 420, 90, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,TCari,TanggalKontrol);
}//GEN-LAST:event_TanggalSuratKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRawat.getText().trim().equals("")||NoSEP.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"pasien");
        }else if(NmDokter.getText().trim().equals("")||KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NmPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Poli");
        }else{
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp",utc);
                headers.add("X-Signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                URL = link+"/RencanaKontrol/v2/Insert";            
                requestJson ="{" +
                                "\"request\": {" +
                                    "\"noSEP\":\""+NoSEP.getText()+"\"," +
                                    "\"kodeDokter\":\""+KdDokter.getText()+"\"," +
                                    "\"poliKontrol\":\""+KdPoli.getText()+"\"," +
                                    "\"tglRencanaKontrol\":\""+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"\"," +
                                    "\"user\":\""+user+"\"," +
                                    "\"formPRB\": {" +
                                        "\"kdStatusPRB\": "+(StatusPRB.getSelectedIndex()==0?"null":StatusPRB.getSelectedItem().toString().substring(0,2))+"," +
                                        "\"data\": {" +
                                            "\"HBA1C\": "+(HBA1C.getText().trim().equals("")?"null":HBA1C.getText())+"," +
                                            "\"GDP\": "+(GDP.getText().trim().equals("")?"null":GDP.getText())+"," +
                                            "\"GD2JPP\": "+(GD2JPP.getText().trim().equals("")?"null":GD2JPP.getText())+"," +
                                            "\"eGFR\": "+(eGFR.getText().trim().equals("")?"null":eGFR.getText())+"," +
                                            "\"TD_Sistolik\": "+(TDSistolik.getText().trim().equals("")?"null":TDSistolik.getText())+"," +
                                            "\"TD_Diastolik\": "+(TDDiastolik.getText().trim().equals("")?"null":TDDiastolik.getText())+"," +
                                            "\"LDL\": "+(LDL.getText().trim().equals("")?"null":LDL.getText())+"," +
                                            "\"Rata_TD_Sistolik\": "+(RerataTDSistolik.getText().trim().equals("")?"null":RerataTDSistolik.getText())+"," +
                                            "\"Rata_TD_Diastolik\": "+(RerataTDDiastolik.getText().trim().equals("")?"null":RerataTDDiastolik.getText())+"," +
                                            "\"JantungKoroner\": "+(JantungKoroner.getSelectedIndex()==0?"null":JantungKoroner.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Stroke\": "+(Stroke.getSelectedIndex()==0?"null":Stroke.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"VaskularPerifer\": "+(VaskularPerifer.getSelectedIndex()==0?"null":VaskularPerifer.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Aritmia\": "+(Aritmia.getSelectedIndex()==0?"null":Aritmia.getSelectedItem().toString().substring(0,1))+", " +
                                            "\"AtrialFibrilasi\": "+(AtrialFibrilasi.getSelectedIndex()==0?"null":AtrialFibrilasi.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"NadiIstirahat\": "+(RRIstirahat.getText().trim().equals("")?"null":RRIstirahat.getText())+"," +
                                            "\"SesakNapas3Bulan\": "+(SesakNapas3Bulan.getSelectedIndex()==0?"null":SesakNapas3Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"NyeriDada3Bulan\": "+(NyeriDada3Bulan.getSelectedIndex()==0?"null":NyeriDada3Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"SesakNapasAktivitas\": "+(SesakNapasAktivitas.getSelectedIndex()==0?"null":SesakNapasAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"NyeriDadaAktivitas\": "+(NyeriDadaAktivitas.getSelectedIndex()==0?"null":NyeriDadaAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Terkontrol\": "+(Terkontrol.getSelectedIndex()==0?"null":Terkontrol.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Gejala2xMinggu\": "+(Gejala2xMinggu.getSelectedIndex()==0?"null":Gejala2xMinggu.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"BangunMalam\": "+(BangunMalam.getSelectedIndex()==0?"null":BangunMalam.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"KeterbatasanFisik\": "+(KeterbatasanFisik.getSelectedIndex()==0?"null":KeterbatasanFisik.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"FungsiParu\": "+(FungsiParu.getText().trim().equals("")?"null":FungsiParu.getText())+"," +
                                            "\"SkorMMRC\": "+(SkorMMRC.getText().trim().equals("")?"null":SkorMMRC.getText())+"," +
                                            "\"Eksaserbasi1Tahun\": "+(Eksaserbasi1Tahun.getSelectedIndex()==0?"null":Eksaserbasi1Tahun.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"MampuAktivitas\": "+(MampuAktivitas.getSelectedIndex()==0?"null":MampuAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Epileptik6Bulan\": "+(Epileptik6Bulan.getSelectedIndex()==0?"null":Epileptik6Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"EfekSampingOAB\": "+(EfekSampingOAB.getSelectedIndex()==0?"null":EfekSampingOAB.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"HamilMenyusui\": "+(HamilMenyusui.getSelectedIndex()==0?"null":HamilMenyusui.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Remisi\": "+(Remisi.getText().trim().equals("")?"null":Remisi.getText())+"," +
                                            "\"TerapiRumatan\": "+(TerapiRumatan.getSelectedIndex()==0?"null":TerapiRumatan.getSelectedItem().toString().substring(0,1))+"," +
                                            "\"Usia\": "+(Usia.getText().trim().equals("")?"null":Usia.getText())+"," +
                                            "\"AsamUrat\": "+(AsamUrat.getText().trim().equals("")?"null":AsamUrat.getText())+"," +
                                            "\"RemisiSLE\": "+(RemisiSLE.getText().trim().equals("")?"null":RemisiSLE.getText())+"," +
                                            "\"Hamil\": "+(Hamil.getSelectedIndex()==0?"null":Hamil.getSelectedItem().toString().substring(0,1))+" " +
                                        "}" +
                                    "}"+
                                "}" +
                             "}";
                System.out.println("JSON : "+requestJson);
                requestEntity = new HttpEntity(requestJson,headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : "+nameNode.path("code").asText());
                System.out.println("message : "+nameNode.path("message").asText());
                if(nameNode.path("code").asText().equals("200")){
                    response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc)).path("noSuratKontrol");
                    if(Sequel.menyimpantf("bridging_surat_kontrol_bpjs","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",45,new String[]{
                            NoSEP.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),response.asText(),Valid.SetTgl(TanggalKontrol.getSelectedItem()+""),KdDokter.getText(),NmDokter.getText(),KdPoli.getText(),NmPoli.getText(),
                            StatusPRB.getSelectedItem().toString().trim(),HBA1C.getText(),GDP.getText(),GD2JPP.getText(),eGFR.getText(),TDSistolik.getText(),TDDiastolik.getText(),LDL.getText(),RerataTDSistolik.getText(),RerataTDDiastolik.getText(),
                            JantungKoroner.getSelectedItem().toString().trim(),Stroke.getSelectedItem().toString().trim(),VaskularPerifer.getSelectedItem().toString().trim(),Aritmia.getSelectedItem().toString().trim(),AtrialFibrilasi.getSelectedItem().toString().trim(),
                            RRIstirahat.getText(),SesakNapas3Bulan.getSelectedItem().toString().trim(),NyeriDada3Bulan.getSelectedItem().toString().trim(),SesakNapasAktivitas.getSelectedItem().toString().trim(),NyeriDadaAktivitas.getSelectedItem().toString().trim(), 
                            Terkontrol.getSelectedItem().toString().trim(),Gejala2xMinggu.getSelectedItem().toString().trim(),BangunMalam.getSelectedItem().toString().trim(),KeterbatasanFisik.getSelectedItem().toString().trim(),FungsiParu.getText(), 
                            SkorMMRC.getText(),Eksaserbasi1Tahun.getSelectedItem().toString().trim(),MampuAktivitas.getSelectedItem().toString().trim(),Epileptik6Bulan.getSelectedItem().toString().trim(),EfekSampingOAB.getSelectedItem().toString().trim(), 
                            HamilMenyusui.getSelectedItem().toString().trim(),Remisi.getText(),TerapiRumatan.getSelectedItem().toString().trim(),Usia.getText(),AsamUrat.getText(),RemisiSLE.getText(),Hamil.getSelectedItem().toString().trim()
                        })==true){
                        emptTeks();
                        runBackground(() ->tampil());
                        if(JADIKANBOOKINGSURATKONTROLAPIBPJS.equals("yes")){
                            if(isBooking()==false){
                                JOptionPane.showMessageDialog(null,"Gagal menyimpan booking, silahkan hubungi administrator...!!!!");
                            }
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
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,NoSurat,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
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
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(R1.isSelected()==true){
                Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptBridgingSuratKontrol.jasper","report","::[ Data Surat Kontrol VClaim ]::",
                    "select bridging_sep.no_rawat,bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.jkel,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_surat_kontrol_bpjs.tgl_surat,bridging_surat_kontrol_bpjs.no_surat,"+
                    "bridging_surat_kontrol_bpjs.tgl_rencana,bridging_surat_kontrol_bpjs.kd_dokter_bpjs,bridging_surat_kontrol_bpjs.nm_dokter_bpjs,"+
                    "bridging_surat_kontrol_bpjs.kd_poli_bpjs,bridging_surat_kontrol_bpjs.nm_poli_bpjs from bridging_sep inner join bridging_surat_kontrol_bpjs "+
                    "on bridging_surat_kontrol_bpjs.no_sep=bridging_sep.no_sep where bridging_surat_kontrol_bpjs.tgl_surat between '"+Valid.SetTgl(DTPTanggalSurat1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPTanggalSurat2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (bridging_sep.no_rawat like '%"+TCari.getText().trim()+"%' or bridging_sep.no_sep like '%"+TCari.getText().trim()+"%' or bridging_sep.no_kartu like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.nomr like '%"+TCari.getText().trim()+"%' or bridging_sep.nama_pasien like '%"+TCari.getText().trim()+"%' or bridging_surat_kontrol_bpjs.no_surat like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_surat_kontrol_bpjs.nm_poli_bpjs like '%"+TCari.getText().trim()+"%' or bridging_surat_kontrol_bpjs.nm_dokter_bpjs like '%"+TCari.getText().trim()+"%')")+
                    "order by bridging_surat_kontrol_bpjs.tgl_surat",param);
            }else if(R2.isSelected()==true){
                Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptBridgingSuratKontrol.jasper","report","::[ Data Surat Kontrol VClaim ]::",
                    "select bridging_sep.no_rawat,bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.jkel,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_surat_kontrol_bpjs.tgl_surat,bridging_surat_kontrol_bpjs.no_surat,"+
                    "bridging_surat_kontrol_bpjs.tgl_rencana,bridging_surat_kontrol_bpjs.kd_dokter_bpjs,bridging_surat_kontrol_bpjs.nm_dokter_bpjs,"+
                    "bridging_surat_kontrol_bpjs.kd_poli_bpjs,bridging_surat_kontrol_bpjs.nm_poli_bpjs from bridging_sep inner join bridging_surat_kontrol_bpjs "+
                    "on bridging_surat_kontrol_bpjs.no_sep=bridging_sep.no_sep where bridging_surat_kontrol_bpjs.tgl_rencana between '"+Valid.SetTgl(DTPTanggalKontrol1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPTanggalKontrol2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (bridging_sep.no_rawat like '%"+TCari.getText().trim()+"%' or bridging_sep.no_sep like '%"+TCari.getText().trim()+"%' or bridging_sep.no_kartu like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_sep.nomr like '%"+TCari.getText().trim()+"%' or bridging_sep.nama_pasien like '%"+TCari.getText().trim()+"%' or bridging_surat_kontrol_bpjs.no_surat like '%"+TCari.getText().trim()+"%' or "+
                    "bridging_surat_kontrol_bpjs.nm_poli_bpjs like '%"+TCari.getText().trim()+"%' or bridging_surat_kontrol_bpjs.nm_dokter_bpjs like '%"+TCari.getText().trim()+"%')")+
                    "order by bridging_surat_kontrol_bpjs.tgl_rencana",param);
            }
                
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
        runBackground(() ->tampil());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, NoSEP);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
    if(KdPoli.getText().equals("")||NmPoli.getText().equals("")){
        Valid.textKosong(BtnPoli,"Unit/Poli");
    }else{
        BPJSCekReferensiDokterKontrol dokter=new BPJSCekReferensiDokterKontrol(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
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
        dokter.SetKontrol(KdPoli.getText(),"2: Rencana Kontrol",Valid.SetTgl(TanggalKontrol.getSelectedItem()+""));
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }
        
}//GEN-LAST:event_BtnDokterActionPerformed

private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        BtnDokterActionPerformed(null);
    }else{
        Valid.pindah(evt,TanggalKontrol,BtnPoli);
    }        
}//GEN-LAST:event_BtnDokterKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void DTPTanggalKontrol1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTanggalKontrol1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTanggalKontrol1KeyPressed

    private void DTPTanggalKontrol2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTanggalKontrol2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTanggalKontrol2KeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRawat.getText().trim().equals("")||NoSEP.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"pasien");
        }else if(NmDokter.getText().trim().equals("")||KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NmPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Poli");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("X-Timestamp",utc);
                    headers.add("X-Signature",api.getHmac(utc));
                    headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
                    URL = link+"/RencanaKontrol/v2/Update";            
                    requestJson ="{" +
                                    "\"request\": {" +
                                        "\"noSuratKontrol\":\""+NoSurat.getText()+"\"," +
                                        "\"noSEP\":\""+NoSEP.getText()+"\"," +
                                        "\"kodeDokter\":\""+KdDokter.getText()+"\"," +
                                        "\"poliKontrol\":\""+KdPoli.getText()+"\"," +
                                        "\"tglRencanaKontrol\":\""+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"\"," +
                                        "\"user\":\""+user+"\"," +
                                        "\"formPRB\": {" +
                                            "\"kdStatusPRB\": "+(StatusPRB.getSelectedIndex()==0?"null":StatusPRB.getSelectedItem().toString().substring(0,2))+"," +
                                            "\"data\": {" +
                                                "\"HBA1C\": "+(HBA1C.getText().trim().equals("")?"null":HBA1C.getText())+"," +
                                                "\"GDP\": "+(GDP.getText().trim().equals("")?"null":GDP.getText())+"," +
                                                "\"GD2JPP\": "+(GD2JPP.getText().trim().equals("")?"null":GD2JPP.getText())+"," +
                                                "\"eGFR\": "+(eGFR.getText().trim().equals("")?"null":eGFR.getText())+"," +
                                                "\"TD_Sistolik\": "+(TDSistolik.getText().trim().equals("")?"null":TDSistolik.getText())+"," +
                                                "\"TD_Diastolik\": "+(TDDiastolik.getText().trim().equals("")?"null":TDDiastolik.getText())+"," +
                                                "\"LDL\": "+(LDL.getText().trim().equals("")?"null":LDL.getText())+"," +
                                                "\"Rata_TD_Sistolik\": "+(RerataTDSistolik.getText().trim().equals("")?"null":RerataTDSistolik.getText())+"," +
                                                "\"Rata_TD_Diastolik\": "+(RerataTDDiastolik.getText().trim().equals("")?"null":RerataTDDiastolik.getText())+"," +
                                                "\"JantungKoroner\": "+(JantungKoroner.getSelectedIndex()==0?"null":JantungKoroner.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Stroke\": "+(Stroke.getSelectedIndex()==0?"null":Stroke.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"VaskularPerifer\": "+(VaskularPerifer.getSelectedIndex()==0?"null":VaskularPerifer.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Aritmia\": "+(Aritmia.getSelectedIndex()==0?"null":Aritmia.getSelectedItem().toString().substring(0,1))+", " +
                                                "\"AtrialFibrilasi\": "+(AtrialFibrilasi.getSelectedIndex()==0?"null":AtrialFibrilasi.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"NadiIstirahat\": "+(RRIstirahat.getText().trim().equals("")?"null":RRIstirahat.getText())+"," +
                                                "\"SesakNapas3Bulan\": "+(SesakNapas3Bulan.getSelectedIndex()==0?"null":SesakNapas3Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"NyeriDada3Bulan\": "+(NyeriDada3Bulan.getSelectedIndex()==0?"null":NyeriDada3Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"SesakNapasAktivitas\": "+(SesakNapasAktivitas.getSelectedIndex()==0?"null":SesakNapasAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"NyeriDadaAktivitas\": "+(NyeriDadaAktivitas.getSelectedIndex()==0?"null":NyeriDadaAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Terkontrol\": "+(Terkontrol.getSelectedIndex()==0?"null":Terkontrol.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Gejala2xMinggu\": "+(Gejala2xMinggu.getSelectedIndex()==0?"null":Gejala2xMinggu.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"BangunMalam\": "+(BangunMalam.getSelectedIndex()==0?"null":BangunMalam.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"KeterbatasanFisik\": "+(KeterbatasanFisik.getSelectedIndex()==0?"null":KeterbatasanFisik.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"FungsiParu\": "+(FungsiParu.getText().trim().equals("")?"null":FungsiParu.getText())+"," +
                                                "\"SkorMMRC\": "+(SkorMMRC.getText().trim().equals("")?"null":SkorMMRC.getText())+"," +
                                                "\"Eksaserbasi1Tahun\": "+(Eksaserbasi1Tahun.getSelectedIndex()==0?"null":Eksaserbasi1Tahun.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"MampuAktivitas\": "+(MampuAktivitas.getSelectedIndex()==0?"null":MampuAktivitas.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Epileptik6Bulan\": "+(Epileptik6Bulan.getSelectedIndex()==0?"null":Epileptik6Bulan.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"EfekSampingOAB\": "+(EfekSampingOAB.getSelectedIndex()==0?"null":EfekSampingOAB.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"HamilMenyusui\": "+(HamilMenyusui.getSelectedIndex()==0?"null":HamilMenyusui.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Remisi\": "+(Remisi.getText().trim().equals("")?"null":Remisi.getText())+"," +
                                                "\"TerapiRumatan\": "+(TerapiRumatan.getSelectedIndex()==0?"null":TerapiRumatan.getSelectedItem().toString().substring(0,1))+"," +
                                                "\"Usia\": "+(Usia.getText().trim().equals("")?"null":Usia.getText())+"," +
                                                "\"AsamUrat\": "+(AsamUrat.getText().trim().equals("")?"null":AsamUrat.getText())+"," +
                                                "\"RemisiSLE\": "+(RemisiSLE.getText().trim().equals("")?"null":RemisiSLE.getText())+"," +
                                                "\"Hamil\": "+(Hamil.getSelectedIndex()==0?"null":Hamil.getSelectedItem().toString().substring(0,1))+" " +
                                            "}" +
                                        "}"+
                                    "}" +
                                 "}";
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("200")){
                        if(Sequel.mengedittf("bridging_surat_kontrol_bpjs","no_surat=?","tgl_surat=?,tgl_rencana=?,kd_dokter_bpjs=?,nm_dokter_bpjs=?,kd_poli_bpjs=?,nm_poli_bpjs=?,status_prb=?,HBA1C=?,GDP=?,GD2JPP=?,eGFR=?,TD_Sistolik=?,TD_Diastolik=?,LDL=?,"+
                                "Rata_TD_Sistolik=?,Rata_TD_Diastolik=?,JantungKoroner=?,Stroke=?,VaskularPerifer=?,Aritmia=?,AtrialFibrilasi=?,NadiIstirahat=?,SesakNapas3Bulan=?,NyeriDada3Bulan=?,SesakNapasAktivitas=?,NyeriDadaAktivitas=?,"+
                                "Terkontrol=?,Gejala2xMinggu=?,BangunMalam=?,KeterbatasanFisik=?,FungsiParu=?,SkorMMRC=?,Eksaserbasi1Tahun=?,MampuAktivitas=?,Epileptik6Bulan=?,EfekSampingOAB=?,HamilMenyusui=?,Remisi=?,TerapiRumatan=?,Usia=?,"+
                                "AsamUrat=?,RemisiSLE=?,Hamil=?",44,new String[]{
                                Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Valid.SetTgl(TanggalKontrol.getSelectedItem()+""),KdDokter.getText(),NmDokter.getText(),KdPoli.getText(),NmPoli.getText(),StatusPRB.getSelectedItem().toString().trim(),HBA1C.getText(),GDP.getText(),
                                GD2JPP.getText(),eGFR.getText(),TDSistolik.getText(),TDDiastolik.getText(),LDL.getText(),RerataTDSistolik.getText(),RerataTDDiastolik.getText(),JantungKoroner.getSelectedItem().toString().trim(),Stroke.getSelectedItem().toString().trim(),
                                VaskularPerifer.getSelectedItem().toString().trim(),Aritmia.getSelectedItem().toString().trim(),AtrialFibrilasi.getSelectedItem().toString().trim(),RRIstirahat.getText(),SesakNapas3Bulan.getSelectedItem().toString().trim(),
                                NyeriDada3Bulan.getSelectedItem().toString().trim(),SesakNapasAktivitas.getSelectedItem().toString().trim(),NyeriDadaAktivitas.getSelectedItem().toString().trim(),Terkontrol.getSelectedItem().toString().trim(),
                                Gejala2xMinggu.getSelectedItem().toString().trim(),BangunMalam.getSelectedItem().toString().trim(),KeterbatasanFisik.getSelectedItem().toString().trim(),FungsiParu.getText(),SkorMMRC.getText(),
                                Eksaserbasi1Tahun.getSelectedItem().toString().trim(),MampuAktivitas.getSelectedItem().toString().trim(),Epileptik6Bulan.getSelectedItem().toString().trim(),EfekSampingOAB.getSelectedItem().toString().trim(), 
                                HamilMenyusui.getSelectedItem().toString().trim(),Remisi.getText(),TerapiRumatan.getSelectedItem().toString().trim(),Usia.getText(),AsamUrat.getText(),RemisiSLE.getText(),Hamil.getSelectedItem().toString().trim(),NoSurat.getText()
                            })==true){
                            emptTeks();
                            runBackground(() ->tampil());
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
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda ganti...\n Klik data pada table untuk memilih data...!!!!");
            }                
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void DTPTanggalKontrol1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTanggalKontrol1ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPTanggalKontrol1ItemStateChanged

    private void DTPTanggalKontrol2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTanggalKontrol2ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPTanggalKontrol2ItemStateChanged

    private void TanggalKontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKontrolKeyPressed
        Valid.pindah(evt,TanggalSurat,BtnDokter);
    }//GEN-LAST:event_TanggalKontrolKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TanggalSurat,TanggalKontrol);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void BtnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokter,TanggalKontrol);
        }
    }//GEN-LAST:event_BtnPoliKeyPressed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        BPJSCekReferensiSpesialistikKontrol poli=new BPJSCekReferensiSpesialistikKontrol(null,false);
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
        poli.SetKontrol(NoSEP.getText(),"2: Rencana Kontrol",Valid.SetTgl(TanggalKontrol.getSelectedItem()+""));
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void MnSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar")); 
            param.put("parameter",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()+"\nID "+tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()));
            Valid.MyReportqry("rptBridgingSuratKontrol2.jasper","report","::[ Data Surat Kontrol VClaim ]::",
                    "select bridging_sep.no_rawat,bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.jkel,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_surat_kontrol_bpjs.tgl_surat,bridging_surat_kontrol_bpjs.no_surat,"+
                    "bridging_surat_kontrol_bpjs.tgl_rencana,bridging_surat_kontrol_bpjs.kd_dokter_bpjs,bridging_surat_kontrol_bpjs.nm_dokter_bpjs,"+
                    "bridging_surat_kontrol_bpjs.kd_poli_bpjs,bridging_surat_kontrol_bpjs.nm_poli_bpjs from bridging_sep inner join bridging_surat_kontrol_bpjs "+
                    "on bridging_surat_kontrol_bpjs.no_sep=bridging_sep.no_sep where bridging_surat_kontrol_bpjs.no_surat='"+NoSurat.getText()+"'",param);              
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data Surat Kontrol yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }  
    }//GEN-LAST:event_MnSuratActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void DTPTanggalSurat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTanggalSurat1KeyPressed

    }//GEN-LAST:event_DTPTanggalSurat1KeyPressed

    private void DTPTanggalSurat1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTanggalSurat1ItemStateChanged
        R1.setSelected(true);
    }//GEN-LAST:event_DTPTanggalSurat1ItemStateChanged

    private void DTPTanggalSurat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTanggalSurat2KeyPressed
        R1.setSelected(true);
    }//GEN-LAST:event_DTPTanggalSurat2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSSuratKontrol dialog = new BPJSSuratKontrol(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Aritmia;
    private widget.TextBox AsamUrat;
    private widget.ComboBox AtrialFibrilasi;
    private widget.ComboBox BangunMalam;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPTanggalKontrol1;
    private widget.Tanggal DTPTanggalKontrol2;
    private widget.Tanggal DTPTanggalSurat1;
    private widget.Tanggal DTPTanggalSurat2;
    private widget.TextBox Diagnosa;
    private widget.ComboBox EfekSampingOAB;
    private widget.ComboBox Eksaserbasi1Tahun;
    private widget.ComboBox Epileptik6Bulan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox FungsiParu;
    private widget.TextBox GD2JPP;
    private widget.TextBox GDP;
    private widget.ComboBox Gejala2xMinggu;
    private widget.TextBox HBA1C;
    private widget.ComboBox Hamil;
    private widget.ComboBox HamilMenyusui;
    private widget.TextBox JK;
    private widget.ComboBox JantungKoroner;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPoli;
    private widget.ComboBox KeterbatasanFisik;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.TextBox LDL;
    private widget.ComboBox MampuAktivitas;
    private javax.swing.JMenuItem MnSurat;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPasien;
    private widget.TextBox NmPoli;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSurat;
    private widget.ComboBox NyeriDada3Bulan;
    private widget.ComboBox NyeriDadaAktivitas;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.TextBox RRIstirahat;
    private widget.TextBox Remisi;
    private widget.TextBox RemisiSLE;
    private widget.TextBox RerataTDDiastolik;
    private widget.TextBox RerataTDSistolik;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SesakNapas3Bulan;
    private widget.ComboBox SesakNapasAktivitas;
    private widget.TextBox SkorMMRC;
    private widget.ComboBox StatusPRB;
    private widget.ComboBox Stroke;
    private widget.TextBox TCari;
    private widget.TextBox TDDiastolik;
    private widget.TextBox TDSistolik;
    private widget.Tanggal TanggalKontrol;
    private widget.Tanggal TanggalSurat;
    private widget.ComboBox TerapiRumatan;
    private widget.ComboBox Terkontrol;
    private widget.TextBox TglLahir;
    private widget.TextBox Usia;
    private widget.ComboBox VaskularPerifer;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.TextBox eGFR;
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
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {     
        Valid.tabelKosong(tabMode);
        try {
           if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select bridging_sep.no_rawat,bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.jkel,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_surat_kontrol_bpjs.tgl_surat,bridging_surat_kontrol_bpjs.no_surat,"+
                    "bridging_surat_kontrol_bpjs.tgl_rencana,bridging_surat_kontrol_bpjs.kd_dokter_bpjs,bridging_surat_kontrol_bpjs.nm_dokter_bpjs,"+
                    "bridging_surat_kontrol_bpjs.kd_poli_bpjs,bridging_surat_kontrol_bpjs.nm_poli_bpjs,bridging_surat_kontrol_bpjs.status_prb,bridging_surat_kontrol_bpjs.HBA1C,bridging_surat_kontrol_bpjs.GDP,"+
                    "bridging_surat_kontrol_bpjs.GD2JPP,bridging_surat_kontrol_bpjs.eGFR,bridging_surat_kontrol_bpjs.TD_Sistolik,bridging_surat_kontrol_bpjs.TD_Diastolik,"+
                    "bridging_surat_kontrol_bpjs.LDL,bridging_surat_kontrol_bpjs.Rata_TD_Sistolik,bridging_surat_kontrol_bpjs.Rata_TD_Diastolik,"+
                    "bridging_surat_kontrol_bpjs.JantungKoroner,bridging_surat_kontrol_bpjs.Stroke,bridging_surat_kontrol_bpjs.VaskularPerifer,"+
                    "bridging_surat_kontrol_bpjs.Aritmia,bridging_surat_kontrol_bpjs.AtrialFibrilasi,bridging_surat_kontrol_bpjs.NadiIstirahat,"+
                    "bridging_surat_kontrol_bpjs.SesakNapas3Bulan,bridging_surat_kontrol_bpjs.NyeriDada3Bulan,bridging_surat_kontrol_bpjs.SesakNapasAktivitas,"+
                    "bridging_surat_kontrol_bpjs.NyeriDadaAktivitas,bridging_surat_kontrol_bpjs.Terkontrol,bridging_surat_kontrol_bpjs.Gejala2xMinggu,"+
                    "bridging_surat_kontrol_bpjs.BangunMalam,bridging_surat_kontrol_bpjs.KeterbatasanFisik,bridging_surat_kontrol_bpjs.FungsiParu,"+
                    "bridging_surat_kontrol_bpjs.SkorMMRC,bridging_surat_kontrol_bpjs.Eksaserbasi1Tahun,bridging_surat_kontrol_bpjs.MampuAktivitas,"+
                    "bridging_surat_kontrol_bpjs.Epileptik6Bulan,bridging_surat_kontrol_bpjs.EfekSampingOAB,bridging_surat_kontrol_bpjs.HamilMenyusui,"+
                    "bridging_surat_kontrol_bpjs.Remisi,bridging_surat_kontrol_bpjs.TerapiRumatan,bridging_surat_kontrol_bpjs.Usia,bridging_surat_kontrol_bpjs.AsamUrat,"+
                    "bridging_surat_kontrol_bpjs.RemisiSLE,bridging_surat_kontrol_bpjs.Hamil from bridging_sep inner join bridging_surat_kontrol_bpjs "+
                    "on bridging_surat_kontrol_bpjs.no_sep=bridging_sep.no_sep where bridging_surat_kontrol_bpjs.tgl_surat between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (bridging_sep.no_rawat like ? or bridging_sep.no_sep like ? or bridging_sep.no_kartu like ? or "+
                    "bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_surat_kontrol_bpjs.no_surat like ? or "+
                    "bridging_surat_kontrol_bpjs.nm_poli_bpjs like ? or bridging_surat_kontrol_bpjs.nm_dokter_bpjs like ?)")+
                    "order by bridging_surat_kontrol_bpjs.tgl_surat");
                try {
                    ps.setString(1,Valid.SetTgl(DTPTanggalSurat1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPTanggalSurat2.getSelectedItem()+""));
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                        ps.setString(9,"%"+TCari.getText().trim()+"%");
                        ps.setString(10,"%"+TCari.getText().trim()+"%");
                    }
                        
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("no_kartu"),rs.getString("nomr"),rs.getString("nama_pasien"),
                            rs.getString("tanggal_lahir"),rs.getString("jkel"),rs.getString("diagawal")+" "+rs.getString("nmdiagnosaawal"),rs.getString("tgl_surat"),rs.getString("no_surat"),
                            rs.getString("tgl_rencana"),rs.getString("kd_dokter_bpjs"),rs.getString("nm_dokter_bpjs"),rs.getString("kd_poli_bpjs"),rs.getString("nm_poli_bpjs"),
                            rs.getString("status_prb"),rs.getString("HBA1C"),rs.getString("GDP"),rs.getString("GD2JPP"),rs.getString("eGFR"),rs.getString("TD_Sistolik"),rs.getString("TD_Diastolik"),rs.getString("LDL"),
                            rs.getString("Rata_TD_Sistolik"),rs.getString("Rata_TD_Diastolik"),rs.getString("JantungKoroner"),rs.getString("Stroke"),rs.getString("VaskularPerifer"),
                            rs.getString("Aritmia"),rs.getString("AtrialFibrilasi"),rs.getString("NadiIstirahat"),rs.getString("SesakNapas3Bulan"),rs.getString("NyeriDada3Bulan"),
                            rs.getString("SesakNapasAktivitas"),rs.getString("NyeriDadaAktivitas"),rs.getString("Terkontrol"),rs.getString("Gejala2xMinggu"),rs.getString("BangunMalam"),
                            rs.getString("KeterbatasanFisik"),rs.getString("FungsiParu"),rs.getString("SkorMMRC"),rs.getString("Eksaserbasi1Tahun"),rs.getString("MampuAktivitas"),
                            rs.getString("Epileptik6Bulan"),rs.getString("EfekSampingOAB"),rs.getString("HamilMenyusui"),rs.getString("Remisi"),rs.getString("TerapiRumatan"),
                            rs.getString("Usia"),rs.getString("AsamUrat"),rs.getString("RemisiSLE"),rs.getString("Hamil")
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
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select bridging_sep.no_rawat,bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.jkel,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_surat_kontrol_bpjs.tgl_surat,bridging_surat_kontrol_bpjs.no_surat,"+
                    "bridging_surat_kontrol_bpjs.tgl_rencana,bridging_surat_kontrol_bpjs.kd_dokter_bpjs,bridging_surat_kontrol_bpjs.nm_dokter_bpjs,"+
                    "bridging_surat_kontrol_bpjs.kd_poli_bpjs,bridging_surat_kontrol_bpjs.nm_poli_bpjs,bridging_surat_kontrol_bpjs.status_prb,bridging_surat_kontrol_bpjs.HBA1C,bridging_surat_kontrol_bpjs.GDP,"+
                    "bridging_surat_kontrol_bpjs.GD2JPP,bridging_surat_kontrol_bpjs.eGFR,bridging_surat_kontrol_bpjs.TD_Sistolik,bridging_surat_kontrol_bpjs.TD_Diastolik,"+
                    "bridging_surat_kontrol_bpjs.LDL,bridging_surat_kontrol_bpjs.Rata_TD_Sistolik,bridging_surat_kontrol_bpjs.Rata_TD_Diastolik,"+
                    "bridging_surat_kontrol_bpjs.JantungKoroner,bridging_surat_kontrol_bpjs.Stroke,bridging_surat_kontrol_bpjs.VaskularPerifer,"+
                    "bridging_surat_kontrol_bpjs.Aritmia,bridging_surat_kontrol_bpjs.AtrialFibrilasi,bridging_surat_kontrol_bpjs.NadiIstirahat,"+
                    "bridging_surat_kontrol_bpjs.SesakNapas3Bulan,bridging_surat_kontrol_bpjs.NyeriDada3Bulan,bridging_surat_kontrol_bpjs.SesakNapasAktivitas,"+
                    "bridging_surat_kontrol_bpjs.NyeriDadaAktivitas,bridging_surat_kontrol_bpjs.Terkontrol,bridging_surat_kontrol_bpjs.Gejala2xMinggu,"+
                    "bridging_surat_kontrol_bpjs.BangunMalam,bridging_surat_kontrol_bpjs.KeterbatasanFisik,bridging_surat_kontrol_bpjs.FungsiParu,"+
                    "bridging_surat_kontrol_bpjs.SkorMMRC,bridging_surat_kontrol_bpjs.Eksaserbasi1Tahun,bridging_surat_kontrol_bpjs.MampuAktivitas,"+
                    "bridging_surat_kontrol_bpjs.Epileptik6Bulan,bridging_surat_kontrol_bpjs.EfekSampingOAB,bridging_surat_kontrol_bpjs.HamilMenyusui,"+
                    "bridging_surat_kontrol_bpjs.Remisi,bridging_surat_kontrol_bpjs.TerapiRumatan,bridging_surat_kontrol_bpjs.Usia,bridging_surat_kontrol_bpjs.AsamUrat,"+
                    "bridging_surat_kontrol_bpjs.RemisiSLE,bridging_surat_kontrol_bpjs.Hamil from bridging_sep inner join bridging_surat_kontrol_bpjs "+
                    "on bridging_surat_kontrol_bpjs.no_sep=bridging_sep.no_sep where bridging_surat_kontrol_bpjs.tgl_rencana between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (bridging_sep.no_rawat like ? or bridging_sep.no_sep like ? or bridging_sep.no_kartu like ? or "+
                    "bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_surat_kontrol_bpjs.no_surat like ? or "+
                    "bridging_surat_kontrol_bpjs.nm_poli_bpjs like ? or bridging_surat_kontrol_bpjs.nm_dokter_bpjs like ?)")+
                    "order by bridging_surat_kontrol_bpjs.tgl_rencana");
                try {
                    ps.setString(1,Valid.SetTgl(DTPTanggalKontrol1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPTanggalKontrol2.getSelectedItem()+""));
                    if(!TCari.getText().trim().equals("")){
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                        ps.setString(9,"%"+TCari.getText().trim()+"%");
                        ps.setString(10,"%"+TCari.getText().trim()+"%");
                    }
                        
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("no_kartu"),rs.getString("nomr"),rs.getString("nama_pasien"),
                            rs.getString("tanggal_lahir"),rs.getString("jkel"),rs.getString("diagawal")+" "+rs.getString("nmdiagnosaawal"),rs.getString("tgl_surat"),rs.getString("no_surat"),
                            rs.getString("tgl_rencana"),rs.getString("kd_dokter_bpjs"),rs.getString("nm_dokter_bpjs"),rs.getString("kd_poli_bpjs"),rs.getString("nm_poli_bpjs"),
                            rs.getString("status_prb"),rs.getString("HBA1C"),rs.getString("GDP"),rs.getString("GD2JPP"),rs.getString("eGFR"),rs.getString("TD_Sistolik"),rs.getString("TD_Diastolik"),rs.getString("LDL"),
                            rs.getString("Rata_TD_Sistolik"),rs.getString("Rata_TD_Diastolik"),rs.getString("JantungKoroner"),rs.getString("Stroke"),rs.getString("VaskularPerifer"),
                            rs.getString("Aritmia"),rs.getString("AtrialFibrilasi"),rs.getString("NadiIstirahat"),rs.getString("SesakNapas3Bulan"),rs.getString("NyeriDada3Bulan"),
                            rs.getString("SesakNapasAktivitas"),rs.getString("NyeriDadaAktivitas"),rs.getString("Terkontrol"),rs.getString("Gejala2xMinggu"),rs.getString("BangunMalam"),
                            rs.getString("KeterbatasanFisik"),rs.getString("FungsiParu"),rs.getString("SkorMMRC"),rs.getString("Eksaserbasi1Tahun"),rs.getString("MampuAktivitas"),
                            rs.getString("Epileptik6Bulan"),rs.getString("EfekSampingOAB"),rs.getString("HamilMenyusui"),rs.getString("Remisi"),rs.getString("TerapiRumatan"),
                            rs.getString("Usia"),rs.getString("AsamUrat"),rs.getString("RemisiSLE"),rs.getString("Hamil")
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
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        LCount.setText(""+tabMode.getRowCount());
    }


    public void emptTeks() {
        NoRawat.setText("");
        NoSEP.setText("");
        NoKartu.setText("");
        NoRM.setText("");
        NmPasien.setText("");
        TglLahir.setText("");
        JK.setText("");
        Diagnosa.setText("");
        TanggalSurat.setDate(new Date());
        NoSurat.setText("");
        TanggalKontrol.setDate(new Date());
        KdDokter.setText("");
        NmDokter.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        HBA1C.setText("");
        GDP.setText("");
        GD2JPP.setText("");
        eGFR.setText("");
        TDSistolik.setText("");
        TDDiastolik.setText("");
        LDL.setText("");
        RerataTDSistolik.setText("");
        RerataTDDiastolik.setText("");
        JantungKoroner.setSelectedIndex(0);
        Stroke.setSelectedIndex(0);
        VaskularPerifer.setSelectedIndex(0);
        Aritmia.setSelectedIndex(0);
        AtrialFibrilasi.setSelectedIndex(0);
        RRIstirahat.setText("");
        SesakNapas3Bulan.setSelectedIndex(0);
        NyeriDada3Bulan.setSelectedIndex(0);
        SesakNapasAktivitas.setSelectedIndex(0);
        NyeriDadaAktivitas.setSelectedIndex(0);
        Terkontrol.setSelectedIndex(0);
        Gejala2xMinggu.setSelectedIndex(0);
        BangunMalam.setSelectedIndex(0);
        KeterbatasanFisik.setSelectedIndex(0);
        FungsiParu.setText("");
        SkorMMRC.setText("");
        Eksaserbasi1Tahun.setSelectedIndex(0);
        MampuAktivitas.setSelectedIndex(0);
        Epileptik6Bulan.setSelectedIndex(0);
        EfekSampingOAB.setSelectedIndex(0); 
        HamilMenyusui.setSelectedIndex(0);
        Remisi.setText("");
        TerapiRumatan.setSelectedIndex(0);
        StatusPRB.setSelectedIndex(0);
        Usia.setText("");
        AsamUrat.setText("");
        RemisiSLE.setText("");
        Hamil.setSelectedIndex(0);
        TanggalSurat.requestFocus();
    }
   

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            NoSEP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            NmPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().replaceAll("P","PEREMPUAN").replaceAll("L","LAKI-LAKI"));
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            StatusPRB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().trim());
            HBA1C.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            GDP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            GD2JPP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            eGFR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TDSistolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TDDiastolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            LDL.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            RerataTDSistolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            RerataTDDiastolik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            JantungKoroner.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().trim());
            Stroke.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString().trim());
            VaskularPerifer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString().trim());
            Aritmia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString().trim());
            AtrialFibrilasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString().trim());
            RRIstirahat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            SesakNapas3Bulan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString().trim());
            NyeriDada3Bulan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString().trim());
            SesakNapasAktivitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString().trim());
            NyeriDadaAktivitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString().trim());
            Terkontrol.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString().trim());
            Gejala2xMinggu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString().trim());
            BangunMalam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString().trim());
            KeterbatasanFisik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString().trim());
            FungsiParu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SkorMMRC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Eksaserbasi1Tahun.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString().trim());
            MampuAktivitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString().trim());
            Epileptik6Bulan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString().trim());
            EfekSampingOAB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString().trim());
            HamilMenyusui.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString().trim());
            Remisi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            TerapiRumatan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString().trim());
            Usia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            AsamUrat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            RemisiSLE.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Hamil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString().trim());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Valid.SetTgl(TanggalKontrol,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        }
    }
    
    public void setNoRm(String norawat,String nosep,String nokartu,String norm,String namapasien,String tanggallahir,String jk,String diagnosa) {
        NoRawat.setText(norawat);
        NoSEP.setText(nosep);
        NoKartu.setText(nokartu);
        NoRM.setText(norm);
        NmPasien.setText(namapasien);
        TglLahir.setText(tanggallahir);
        JK.setText(jk.replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN"));
        Diagnosa.setText(diagnosa);
        TCari.setText(nosep);
        ChkInput.setSelected(true);
        isForm();
        runBackground(() ->tampil());
    }
    
    public void setNoRm(String norm) {
        TCari.setText(norm);
        ChkInput.setSelected(false);
        isForm();
        runBackground(() ->tampil());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>647){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,475));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbpjs_surat_kontrol());
        BtnHapus.setEnabled(akses.getbpjs_surat_kontrol());
        BtnPrint.setEnabled(akses.getbpjs_surat_kontrol());
        BtnEdit.setEnabled(akses.getbpjs_surat_kontrol());
    }

    public JTable getTable(){
        return tbObat;
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
            URL = link+"/RencanaKontrol/Delete";
            requestJson ="{\"request\":{\"t_suratkontrol\":{\"noSuratKontrol\":\""+NoSurat.getText()+"\",\"user\":\""+user+"\"}}}";            
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                Sequel.meghapus("bridging_surat_kontrol_bpjs","no_surat",NoSurat.getText());
                tabMode.removeRow(tbObat.getSelectedRow());
                LCount.setText(""+tabMode.getRowCount());
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
    
    private boolean isBooking(){
        status=true;
        kodedokter=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter_bpjs=?",KdDokter.getText());
        if(kodedokter.equals("")){
            status=false;
            System.out.println("Notif : Mapping kode dokter tidak ditemukan");
        } 
        kodepoli=Sequel.cariIsi("select maping_poli_bpjs.kd_poli_rs from maping_poli_bpjs where maping_poli_bpjs.kd_poli_bpjs=?",KdPoli.getText());
        if(kodepoli.equals("")){
            status=false;
            System.out.println("Notif : Mapping kode poli tidak ditemukan");
        } 
        if(status==true){
            noreg="";
            switch (URUTNOREG) {
                case "poli":
                    noreg=Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_poli='"+kodepoli+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"'","",3);
                    break;
                case "dokter":
                    noreg=Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+kodedokter+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"'","",3);
                    break;
                case "dokter + poli":             
                    noreg=Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+kodedokter+"' and booking_registrasi.kd_poli='"+kodepoli+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"'","",3);
                    break;
                default:
                    noreg=Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+kodedokter+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+"'","",3);
                    break;
            }
            status=Sequel.menyimpantf2("booking_registrasi","?,?,?,?,?,?,?,?,?,?,?","Pasien dan Tanggal",11,new String[]{
                Valid.SetTgl(TanggalSurat.getSelectedItem()+""),"08:00:00",NoRM.getText(),Valid.SetTgl(TanggalKontrol.getSelectedItem()+""),
                kodedokter,kodepoli,noreg,Sequel.cariIsi("select pasien.kd_pj from pasien where pasien.no_rkm_medis=?",NoRM.getText()),"0",
                Valid.SetTgl(TanggalKontrol.getSelectedItem()+"")+" "+TanggalKontrol.getSelectedItem().toString().substring(11,19),"belum"
            });
        }
            
        return status;
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        ceksukses = true;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        executor.submit(() -> {
            try {
                task.run();
            } finally {
                ceksukses = false;
                SwingUtilities.invokeLater(() -> {
                    this.setCursor(Cursor.getDefaultCursor());
                });
            }
        });
    }
}

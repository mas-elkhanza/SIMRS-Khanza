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
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.riwayatobat;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import keuangan.Jurnal;
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
public final class ApotekBPJSDaftarResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDetail,tabModeRekap;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0, reply=0;
    private ApiApotekBPJS api=new ApiApotekBPJS();
    private String URL="",link="",utc="",requestJson="",JADIKANPIUTANGAPOTEKBPJS,nopiutang="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private Jurnal jur=new Jurnal();
    private riwayatobat Trackobat=new riwayatobat();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private boolean sukses=true;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public ApotekBPJSDaftarResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "No.SEP Asal","No.SEP Apotek","Tanggal SEP","No.Rawat","No.RM","Nama Pasien","No.Kartu","Jenis","ID User SEP","No.Resep","Tgl.Resep","Tgl.Pelayanan","Iterasi","PPK Rujukan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResep.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(117);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(117);
            }else if(i==11){
                column.setPreferredWidth(117);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(70);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetail=new DefaultTableModel(null,new Object[]{
                "No.Racik","Kode Obat","Nama Obat","Signa 1","Signa 2","Jml.Obat","Permintaan","Jml.Harian"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDetailResep.setModel(tabModeDetail);
        tbDetailResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDetailResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(52);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(60);
            }
        }
        tbDetailResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRekap=new DefaultTableModel(null,new Object[]{
                "No.SEP Asal","No.SEP Apotek","Tanggal SEP","No.Rawat","No.RM","Nama Pasien","No.Kartu","Jenis","ID User SEP","No.Resep","Tgl.Resep",
                "Tgl.Pelayanan","Iterasi","PPK Rujukan","No.Racik","Kode Obat","Nama Obat","Signa 1","Signa 2","Jml.Obat","Permintaan","Jml.Harian"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapResep.setModel(tabModeRekap);

        tbRekapResep.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRekapResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 22; i++) {
            TableColumn column = tbRekapResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(125);
            }else if(i==2){
                column.setPreferredWidth(117);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(117);
            }else if(i==11){
                column.setPreferredWidth(117);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(50);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(45);
            }else if(i==19){
                column.setPreferredWidth(52);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(60);
            }
        }
        tbRekapResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
        scrollPaneDetail.setVisible(false); 
        
        try {
            link=koneksiDB.URLAPIAPOTEKBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        
        try {
            JADIKANPIUTANGAPOTEKBPJS = koneksiDB.JADIKANPIUTANGAPOTEKBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
            JADIKANPIUTANGAPOTEKBPJS = "no";
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

        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        jLabel17 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabData = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tbResep = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        scrollPaneDetail = new widget.ScrollPane();
        tbDetailResep = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbRekapResep = new widget.Table();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Daftar Obat Apotek Online BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel17.setText("Resep :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass6.add(jLabel17);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass6.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(DTPCari2);

        jLabel16.setText("Keyword :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass6.add(LCount);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass6.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        TabData.setBackground(new java.awt.Color(255, 255, 253));
        TabData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbResep.setAutoCreateRowSorter(true);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbResep);

        jPanel2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        scrollPaneDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Detail Resep Obat :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPaneDetail.setName("scrollPaneDetail"); // NOI18N
        scrollPaneDetail.setOpaque(true);

        tbDetailResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailResep.setName("tbDetailResep"); // NOI18N
        scrollPaneDetail.setViewportView(tbDetailResep);

        PanelAccor.add(scrollPaneDetail, java.awt.BorderLayout.CENTER);

        jPanel2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabData.addTab("Data Resep Obat", jPanel2);

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbRekapResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapResep.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapResep.setName("tbRekapResep"); // NOI18N
        scrollPane2.setViewportView(tbRekapResep);

        TabData.addTab("Rekap Resep Obat", scrollPane2);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

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
        if(TabData.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                try{
                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                    );
                    bg.close();

                    File f;            
                    BufferedWriter bw;
                    StringBuilder htmlContent;

                    String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Apotek</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID User SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pelayanan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Iterasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PPK Rujukan</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabMode.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,13).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataResepApolBPJS.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA RESEP APOTEK ONLINE BPJS<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>")
                                );
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 2 (WPS)":
                                htmlContent = new StringBuilder();
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Apotek</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID User SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pelayanan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Iterasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PPK Rujukan</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabMode.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbResep.getValueAt(i,13).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataResepApolBPJS.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA RESEP APOTEK ONLINE BPJS<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>")
                                );
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 3 (CSV)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "\"No.SEP Asal\";\"No.SEP Apotek\";\"Tanggal SEP\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"No.Kartu\";\"Jenis\";\"ID User SEP\";\"No.Resep\";\"Tgl.Resep\";\"Tgl.Pelayanan\";\"Iterasi\";\"PPK Rujukan\"\n"
                                ); 
                                for (int i = 0; i < tabMode.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbResep.getValueAt(i,0).toString()).append("\";\"").append(tbResep.getValueAt(i,1).toString()).append("\";\"").append(tbResep.getValueAt(i,2).toString()).append("\";\"").append(tbResep.getValueAt(i,3).toString()).append("\";\"").append(tbResep.getValueAt(i,4).toString()).append("\";\"").append(tbResep.getValueAt(i,5).toString()).append("\";\"").append(tbResep.getValueAt(i,6).toString()).append("\";\"").append(tbResep.getValueAt(i,7).toString()).append("\";\"").append(tbResep.getValueAt(i,8).toString()).append("\";\"").append(tbResep.getValueAt(i,9).toString()).append("\";\"").append(tbResep.getValueAt(i,10).toString()).append("\";\"").append(tbResep.getValueAt(i,11).toString()).append("\";\"").append(tbResep.getValueAt(i,12).toString()).append("\";\"").append(tbResep.getValueAt(i,13).toString()).append("\"\n");
                                }
                                f = new File("DataResepApolBPJS.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
                    }   
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabData.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRekap.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabModeRekap.getRowCount()!=0){
                try{
                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                    );
                    bg.close();

                    File f;            
                    BufferedWriter bw;
                    StringBuilder htmlContent;

                    String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Apotek</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID User SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pelayanan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Iterasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PPK Rujukan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Racik</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Signa 1</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Signa 2</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Harian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekap.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,21).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapResepApolBPJS.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP RESEP APOTEK ONLINE BPJS<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>")
                                );
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 2 (WPS)":
                                htmlContent = new StringBuilder();
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Asal</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP Apotek</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Kartu</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID User SEP</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Resep</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pelayanan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Iterasi</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PPK Rujukan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Racik</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Signa 1</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Signa 2</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Obat</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Permintaan</b></td>").
                                                append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Harian</b></td>").
                                            append("</tr>");
                                for (int i = 0; i < tabModeRekap.getRowCount(); i++) {
                                    htmlContent.append("<tr class='isi'>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,0).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,1).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,2).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,3).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,4).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,5).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,6).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,7).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,8).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,9).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,10).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,11).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,12).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,13).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,14).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,15).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,16).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,17).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,18).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,19).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,20).toString()).append("</td>").
                                                    append("<td valign='top'>").append(tbRekapResep.getValueAt(i,21).toString()).append("</td>").
                                                append("</tr>");
                                }
                                LoadHTML.setText(
                                    "<html>"+
                                      "<table width='1900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                       htmlContent.toString()+
                                      "</table>"+
                                    "</html>"
                                );

                                f = new File("DataRekapResepApolBPJS.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                            "<table width='1900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DATA REKAP RESEP APOTEK ONLINE BPJS<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>")
                                );
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 3 (CSV)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "\"No.SEP Asal\";\"No.SEP Apotek\";\"Tanggal SEP\";\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"No.Kartu\";\"Jenis\";\"ID User SEP\";\"No.Resep\";\"Tgl.Resep\";\"Tgl.Pelayanan\";\"Iterasi\";\"PPK Rujukan\";\"No.Racik\";\"Kode Obat\";\"Nama Obat\";\"Signa 1\";\"Signa 2\";\"Jml.Obat\";\"Permintaan\";\"Jml.Harian\"\n"
                                ); 
                                for (int i = 0; i < tabModeRekap.getRowCount(); i++) {
                                    htmlContent.append("\"").append(tbRekapResep.getValueAt(i,0).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,1).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,2).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,3).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,4).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,5).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,6).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,7).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,8).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,9).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,10).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,11).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,12).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,13).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,14).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,15).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,16).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,17).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,18).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,19).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,20).toString()).append("\";\"").append(tbRekapResep.getValueAt(i,21).toString()).append("\"\n");
                                }
                                f = new File("DataRekapResepApolBPJS.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());
                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
                    }   
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }
            }
            this.setCursor(Cursor.getDefaultCursor());
        }       
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TabDataMouseClicked(null);
            BtnPrint.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            TabDataMouseClicked(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabDataMouseClicked(null);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TabDataMouseClicked(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabData.getSelectedIndex()==0){
            if(tbResep.getSelectedRow()!= -1){
                if(ChkAccor.isSelected()==true){
                    reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah yakin data No.SEP Apotek : "+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString()+",\nNo.SEP Asal : "+tbResep.getValueAt(tbResep.getSelectedRow(),0).toString()+", No.Resep : "+tbResep.getValueAt(tbResep.getSelectedRow(),9).toString()+"\nmau dihapus beserta seluruh obatnya...?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        sukses=true;
                        for(i=0;i<tbDetailResep.getRowCount();i++){ 
                            try {
                                HapusDetailResep(
                                    tbResep.getValueAt(tbResep.getSelectedRow(),1).toString(),tbResep.getValueAt(tbResep.getSelectedRow(),9).toString().substring(tbResep.getValueAt(tbResep.getSelectedRow(),9).toString().length()-5),tbDetailResep.getValueAt(i,1).toString(),tbDetailResep.getValueAt(i,0).toString(),i
                                );
                            }catch (Exception ex) {
                                System.out.println("Notifikasi Bridging : "+ex);
                            }
                        }
                        
                        if(sukses==true){
                            try {
                                nopiutang=tbResep.getValueAt(tbResep.getSelectedRow(),1).toString();
                                HapusResep();
                            }catch (Exception ex) {
                                System.out.println("Notifikasi Bridging : "+ex);
                            }
                        }
                        
                        if(sukses==true){
                            if(JADIKANPIUTANGAPOTEKBPJS.equals("yes")){
                                try {
                                    ps=koneksi.prepareStatement(
                                            "select piutang.nota_piutang,piutang.kd_bangsal,piutang.sisapiutang from piutang where piutang.nota_piutang=?");
                                    try {
                                       ps.setString(1,nopiutang);
                                       rs=ps.executeQuery();
                                       if(rs.next()){
                                           Sequel.AutoComitFalse();
                                           ps2=koneksi.prepareStatement(
                                                "select detailpiutang.kode_brng,detailpiutang.jumlah,detailpiutang.no_batch,detailpiutang.no_faktur from detailpiutang where detailpiutang.nota_piutang=? ");
                                           try {
                                               ps2.setString(1,rs.getString(1));
                                               rs2=ps2.executeQuery();
                                               while(rs2.next()){
                                                   Trackobat.catatRiwayat(rs2.getString("kode_brng"),rs2.getDouble("jumlah"),0,"Piutang",akses.getkode(),rs.getString("kd_bangsal"),"Hapus","","",rs.getString("nota_piutang"));
                                                   Sequel.menyimpan("gudangbarang","'"+rs2.getString("kode_brng") +"','"+rs.getString("kd_bangsal") +"','"+rs2.getString("jumlah") +"','',''","stok=stok+'"+rs2.getString("jumlah") +"'","kode_brng='"+rs2.getString("kode_brng")+"' and kd_bangsal='"+rs.getString("kd_bangsal") +"' and no_batch='' and no_faktur=''");
                                               }
                                           } catch (Exception e) {
                                               sukses=false;
                                               System.out.println("Notif : "+e);
                                           } finally{
                                               if(rs2!=null){
                                                   rs2.close();
                                               }
                                               if(ps2!=null){
                                                   ps2.close();
                                               }
                                           }

                                           if(sukses==true){
                                               Sequel.queryu("delete from tampjurnal");
                                               if(Sequel.menyimpantf2("tampjurnal","'"+Sequel.cariIsi("select set_akun.Piutang_Obat from set_akun")+"','PIUTANG PASIEN','0','"+rs.getString("sisapiutang")+"'","Rekening")==false){
                                                  sukses=false;
                                               }    
                                               if(Sequel.menyimpantf2("tampjurnal","'"+Sequel.cariIsi("select set_akun.Kontra_Piutang_Obat from set_akun")+"','KAS DI TANGAN','"+rs.getString("sisapiutang")+"','0'","Rekening")==false){
                                                  sukses=false;
                                               } 
                                               if(sukses==true){
                                                  sukses=jur.simpanJurnal(rs.getString("nota_piutang"),"U","BATAL PIUTANG OBAT DI "+Sequel.CariBangsal(rs.getString("kd_bangsal")).toUpperCase()+", OLEH "+akses.getkode());
                                               }
                                           }

                                           if(sukses==true){
                                               Sequel.queryu("delete from piutang where nota_piutang='"+nopiutang+"'");
                                               Sequel.Commit();
                                           }else{
                                               sukses=false;
                                               JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi piutang gagal dibatalkan...!!");
                                               Sequel.RollBack();
                                           }

                                           Sequel.AutoComitTrue();
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
                                 } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                 }
                            }
                        }
                    }
                }else if(ChkAccor.isSelected()==false){
                    JOptionPane.showMessageDialog(null,"Silahkan tampilkan data detail resep obat apotek online BPJS...!!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data resep obat apotek online BPJS...!!!");
            }
        }else if(TabData.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Silahkan pilih data resep obat apotek online BPJS...!!!");
            TabData.setSelectedIndex(0);
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabDataMouseClicked(null);
                    }
                }
            });
        } 
    }//GEN-LAST:event_formWindowOpened

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TabDataMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbResep.getSelectedRow()!= -1){
            if(ChkAccor.isSelected()==true){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(670,HEIGHT));
                scrollPaneDetail.setVisible(true);
                ChkAccor.setVisible(true);
                Valid.tabelKosong(tabModeDetail);
                try {
                    ps=koneksi.prepareStatement(
                        "select bridging_resep_apotek_bpjs_racikan.nomor_racik,bridging_resep_apotek_bpjs_racikan.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,bridging_resep_apotek_bpjs_racikan.signa1,"+
                        "bridging_resep_apotek_bpjs_racikan.signa2,bridging_resep_apotek_bpjs_racikan.jml_obat,bridging_resep_apotek_bpjs_racikan.permintaan,bridging_resep_apotek_bpjs_racikan.jho "+
                        "from bridging_resep_apotek_bpjs_racikan inner join maping_obat_apotek_bpjs on maping_obat_apotek_bpjs.kode_brng_apotek_bpjs=bridging_resep_apotek_bpjs_racikan.kode_brng_apotek_bpjs "+
                        "where bridging_resep_apotek_bpjs_racikan.no_sep_apotek=? and bridging_resep_apotek_bpjs_racikan.no_resep=?"
                    );
                    try {
                        ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString());
                        ps.setString(2,tbResep.getValueAt(tbResep.getSelectedRow(),9).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetail.addRow(new Object[]{
                                rs.getString("nomor_racik"),rs.getString("kode_brng_apotek_bpjs"),rs.getString("nama_brng_apotek_bpjs"),rs.getString("signa1"),rs.getString("signa2"),rs.getString("jml_obat"),rs.getString("permintaan"),rs.getString("jho")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
                
                try {
                    ps=koneksi.prepareStatement(
                        "select bridging_resep_apotek_bpjs_nonracikan.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,bridging_resep_apotek_bpjs_nonracikan.signa1,"+
                        "bridging_resep_apotek_bpjs_nonracikan.signa2,bridging_resep_apotek_bpjs_nonracikan.jml_obat,bridging_resep_apotek_bpjs_nonracikan.jho "+
                        "from bridging_resep_apotek_bpjs_nonracikan inner join maping_obat_apotek_bpjs on maping_obat_apotek_bpjs.kode_brng_apotek_bpjs=bridging_resep_apotek_bpjs_nonracikan.kode_brng_apotek_bpjs "+
                        "where bridging_resep_apotek_bpjs_nonracikan.no_sep_apotek=? and bridging_resep_apotek_bpjs_nonracikan.no_resep=?"
                    );
                    try {
                        ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),1).toString());
                        ps.setString(2,tbResep.getValueAt(tbResep.getSelectedRow(),9).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            tabModeDetail.addRow(new Object[]{
                                "N",rs.getString("kode_brng_apotek_bpjs"),rs.getString("nama_brng_apotek_bpjs"),rs.getString("signa1"),rs.getString("signa2"),rs.getString("jml_obat"),rs.getString("jml_obat"),rs.getString("jho")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }else if(ChkAccor.isSelected()==false){
                ChkAccor.setVisible(false);
                PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
                scrollPaneDetail.setVisible(false);
                ChkAccor.setVisible(true);
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih data resep...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            runBackground(() ->tampil());
        }else if(TabData.getSelectedIndex()==1){
            runBackground(() ->tampil2());
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        ChkAccorActionPerformed(null);
    }//GEN-LAST:event_tbResepMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ApotekBPJSDaftarResepObat dialog = new ApotekBPJSDaftarResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel21;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel2;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPaneDetail;
    private widget.Table tbDetailResep;
    private widget.Table tbRekapResep;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select bridging_resep_apotek_bpjs.no_sep,bridging_resep_apotek_bpjs.no_sep_apotek,bridging_resep_apotek_bpjs.tgl_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,"+
                "bridging_sep.no_kartu,bridging_resep_apotek_bpjs.kdjenis,bridging_resep_apotek_bpjs.id_user_sep,bridging_resep_apotek_bpjs.no_resep,bridging_resep_apotek_bpjs.tgl_resep,"+
                "bridging_resep_apotek_bpjs.tgl_pelayanan,bridging_resep_apotek_bpjs.iterasi,bridging_resep_apotek_bpjs.kdppkrujukan from bridging_resep_apotek_bpjs "+
                "inner join bridging_sep on bridging_sep.no_sep=bridging_resep_apotek_bpjs.no_sep where bridging_resep_apotek_bpjs.tgl_resep between ? and ? "+
                (TCari.getText().trim().equals("")?"":"and (bridging_resep_apotek_bpjs.no_sep like ? or bridging_resep_apotek_bpjs.no_sep_apotek like ? or bridging_sep.no_rawat like ? or "+
                "bridging_sep.nomr like ? or bridging_resep_apotek_bpjs.kdjenis like ? or bridging_resep_apotek_bpjs.no_resep like ? or bridging_sep.nama_pasien like ?)")
            );
            try {
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+" 00:00:01"));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+" 23:59:59"));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_sep"),rs.getString("no_sep_apotek"),rs.getString("tgl_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),
                        rs.getString("no_kartu"),rs.getString("kdjenis"),rs.getString("id_user_sep"),rs.getString("no_resep"),rs.getString("tgl_resep"),rs.getString("tgl_pelayanan"),
                        rs.getString("iterasi"),rs.getString("kdppkrujukan")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }  
    
    private void tampil2() {
        Valid.tabelKosong(tabModeRekap);
        try{
            ps=koneksi.prepareStatement(
                "select bridging_resep_apotek_bpjs.no_sep,bridging_resep_apotek_bpjs.no_sep_apotek,bridging_resep_apotek_bpjs.tgl_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,"+
                "bridging_sep.no_kartu,bridging_resep_apotek_bpjs.kdjenis,bridging_resep_apotek_bpjs.id_user_sep,bridging_resep_apotek_bpjs.no_resep,bridging_resep_apotek_bpjs.tgl_resep,"+
                "bridging_resep_apotek_bpjs.tgl_pelayanan,bridging_resep_apotek_bpjs.iterasi,bridging_resep_apotek_bpjs.kdppkrujukan,bridging_resep_apotek_bpjs_racikan.nomor_racik,"+
                "bridging_resep_apotek_bpjs_racikan.kode_brng_apotek_bpjs,maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,bridging_resep_apotek_bpjs_racikan.signa1,"+
                "bridging_resep_apotek_bpjs_racikan.signa2,bridging_resep_apotek_bpjs_racikan.jml_obat,bridging_resep_apotek_bpjs_racikan.permintaan,bridging_resep_apotek_bpjs_racikan.jho "+
                "from bridging_resep_apotek_bpjs inner join bridging_sep on bridging_sep.no_sep=bridging_resep_apotek_bpjs.no_sep "+
                "inner join bridging_resep_apotek_bpjs_racikan on bridging_resep_apotek_bpjs.no_sep_apotek=bridging_resep_apotek_bpjs_racikan.no_sep_apotek "+
                "inner join maping_obat_apotek_bpjs on maping_obat_apotek_bpjs.kode_brng_apotek_bpjs=bridging_resep_apotek_bpjs_racikan.kode_brng_apotek_bpjs "+
                "where bridging_resep_apotek_bpjs.tgl_resep between ? and ? "+(TCari.getText().trim().equals("")?"":"and (bridging_resep_apotek_bpjs.no_sep like ? or "+
                "bridging_resep_apotek_bpjs.no_sep_apotek like ? or bridging_sep.no_rawat like ? or bridging_sep.nomr like ? or bridging_resep_apotek_bpjs.kdjenis like ? or "+
                "bridging_resep_apotek_bpjs.no_resep like ? or bridging_resep_apotek_bpjs.id_user_sep like ? or bridging_resep_apotek_bpjs_racikan.kode_brng_apotek_bpjs like ? or "+
                "maping_obat_apotek_bpjs.nama_brng_apotek_bpjs like ?)")
            );
            try {
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+" 00:00:01"));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+" 23:59:59"));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRekap.addRow(new String[]{
                        rs.getString("no_sep"),rs.getString("no_sep_apotek"),rs.getString("tgl_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),
                        rs.getString("no_kartu"),rs.getString("kdjenis"),rs.getString("id_user_sep"),rs.getString("no_resep"),rs.getString("tgl_resep"),rs.getString("tgl_pelayanan"),
                        rs.getString("iterasi"),rs.getString("kdppkrujukan"),rs.getString("nomor_racik"),rs.getString("kode_brng_apotek_bpjs"),rs.getString("nama_brng_apotek_bpjs"),
                        rs.getString("signa1"),rs.getString("signa2"),rs.getString("jml_obat"),rs.getString("permintaan"),rs.getString("jho")
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
            
            ps=koneksi.prepareStatement(
                "select bridging_resep_apotek_bpjs.no_sep,bridging_resep_apotek_bpjs.no_sep_apotek,bridging_resep_apotek_bpjs.tgl_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,"+
                "bridging_sep.no_kartu,bridging_resep_apotek_bpjs.kdjenis,bridging_resep_apotek_bpjs.id_user_sep,bridging_resep_apotek_bpjs.no_resep,bridging_resep_apotek_bpjs.tgl_resep,"+
                "bridging_resep_apotek_bpjs.tgl_pelayanan,bridging_resep_apotek_bpjs.iterasi,bridging_resep_apotek_bpjs.kdppkrujukan,bridging_resep_apotek_bpjs_nonracikan.kode_brng_apotek_bpjs,"+
                "maping_obat_apotek_bpjs.nama_brng_apotek_bpjs,bridging_resep_apotek_bpjs_nonracikan.signa1,bridging_resep_apotek_bpjs_nonracikan.signa2,bridging_resep_apotek_bpjs_nonracikan.jml_obat,"+
                "bridging_resep_apotek_bpjs_nonracikan.jho from bridging_resep_apotek_bpjs inner join bridging_sep on bridging_sep.no_sep=bridging_resep_apotek_bpjs.no_sep "+
                "inner join bridging_resep_apotek_bpjs_nonracikan on bridging_resep_apotek_bpjs.no_sep_apotek=bridging_resep_apotek_bpjs_nonracikan.no_sep_apotek "+
                "inner join maping_obat_apotek_bpjs on maping_obat_apotek_bpjs.kode_brng_apotek_bpjs=bridging_resep_apotek_bpjs_nonracikan.kode_brng_apotek_bpjs "+
                "where bridging_resep_apotek_bpjs.tgl_resep between ? and ? "+(TCari.getText().trim().equals("")?"":"and (bridging_resep_apotek_bpjs.no_sep like ? or "+
                "bridging_resep_apotek_bpjs.no_sep_apotek like ? or bridging_sep.no_rawat like ? or bridging_sep.nomr like ? or bridging_resep_apotek_bpjs.kdjenis like ? or "+
                "bridging_resep_apotek_bpjs.no_resep like ? or bridging_resep_apotek_bpjs.id_user_sep like ? or bridging_resep_apotek_bpjs_nonracikan.kode_brng_apotek_bpjs like ? or "+
                "maping_obat_apotek_bpjs.nama_brng_apotek_bpjs like ?)")
            );
            try {
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+" 00:00:01"));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+" 23:59:59"));
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRekap.addRow(new String[]{
                        rs.getString("no_sep"),rs.getString("no_sep_apotek"),rs.getString("tgl_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),
                        rs.getString("no_kartu"),rs.getString("kdjenis"),rs.getString("id_user_sep"),rs.getString("no_resep"),rs.getString("tgl_resep"),rs.getString("tgl_pelayanan"),
                        rs.getString("iterasi"),rs.getString("kdppkrujukan"),"N",rs.getString("kode_brng_apotek_bpjs"),rs.getString("nama_brng_apotek_bpjs"),rs.getString("signa1"),
                        rs.getString("signa2"),rs.getString("jml_obat"),rs.getString("jml_obat"),rs.getString("jho")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeRekap.getRowCount());
    }  

    public JTable getTable(){
        return tbResep;
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
    public void HapusDetailResep(String nosepapotek,String noresep,String kodeobat,String tipeobat,int baris) throws Exception {
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
            headers.add("x-cons-id",koneksiDB.CONSIDAPIAPOTEKBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("x-timestamp",utc);
	    headers.add("x-signature",api.getHmac(utc));
	    headers.add("user_key",koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/pelayanan/obat/hapus";
            requestJson ="{\"nosepapotek\":\""+nosepapotek+"\",\"noresep\":\""+noresep+"\",\"kodeobat\":\""+kodeobat+"\",\"tipeobat\":\""+tipeobat+"\"}";
            System.out.println(URL);
            System.out.println("JSON Dikirim : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(!nameNode.path("code").asText().equals("200")){
                sukses=false;
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                if(nameNode.path("code").asText().equals("404")){
                    if(tipeobat.equals("N")){
                        Sequel.queryu2("delete from bridging_resep_apotek_bpjs_nonracikan where no_sep_apotek=? and kode_brng_apotek_bpjs=?",2,new String[]{nosepapotek,kodeobat});
                    }else{
                        Sequel.queryu2("delete from bridging_resep_apotek_bpjs_racikan where no_sep_apotek=? and kode_brng_apotek_bpjs=?",2,new String[]{nosepapotek,kodeobat});
                    }
                }
            }
        } catch (Exception e) {   
            sukses=false;
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    @Test
    public void HapusResep() throws Exception {
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
            headers.add("x-cons-id", koneksiDB.CONSIDAPIAPOTEKBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPOTEKBPJS());
            URL = link+"/hapusresep";
            requestJson ="{\"nosjp\":\""+tbResep.getValueAt(tbResep.getSelectedRow(),1).toString()+"\",\"refasalsjp\":\""+tbResep.getValueAt(tbResep.getSelectedRow(),0).toString()+"\",\"noresep\":\""+tbResep.getValueAt(tbResep.getSelectedRow(),9).toString().substring(tbResep.getValueAt(tbResep.getSelectedRow(),9).toString().length()-5)+"\"}";            
            System.out.println(URL);
            System.out.println("JSON Dikirim : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE,requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(!nameNode.path("code").asText().equals("200")){
                sukses=false;
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
            }else{
                if(!tbResep.getValueAt(tbResep.getSelectedRow(),12).toString().equals("0. Tanpa Iterasi")){
                    ps=koneksi.prepareStatement(
                        "select permintaan_resep_iterasi_bpjs.no_resep from permintaan_resep_iterasi_bpjs where permintaan_resep_iterasi_bpjs.no_resep_awal=?"
                    );
                    try {
                        ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),9).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            Sequel.meghapus("resep_obat","no_resep",rs.getString("no_resep"));
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
                Sequel.meghapus("bridging_resep_apotek_bpjs","no_sep_apotek",tbResep.getValueAt(tbResep.getSelectedRow(),1).toString());
                Valid.tabelKosong(tabModeDetail);
                tabMode.removeRow(tbResep.getSelectedRow());
            }
        } catch (Exception e) {   
            sukses=false;
            System.out.println("Notif : "+e);
            if(e.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

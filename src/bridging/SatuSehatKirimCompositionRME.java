/*
  By Mas Elkhanza
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
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatKirimCompositionRME extends javax.swing.JDialog {
    private final DefaultTableModel tabModeIGDPrimer,tabModeIGDSekunder,tabModeAwalKeperawatanIGD;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;   
    private int i=0,cekada=0;
    private String link="",json="",iddokter="",idpasien="",sistole="0",diastole="0";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private String[] arrSplit;
    private SatuSehatCekNIK cekViaSatuSehat=new SatuSehatCekNIK();  
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public SatuSehatKirimCompositionRME(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabModeIGDPrimer=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","ID Encounter","Petugas/Dokter/Praktisi","No.KTP Praktisi","Tanggal","Cara Masuk","ID Observation Cara Masuk",
                "Alat Transportasi","ID Observation Alat Transportasi","Alasan Kedatangan","Keterangan Kedatangan","ID Observation Alasan Kedatangan",
                "Macam Kasus","ID Observation Macam Kasus","TD","ID Observation TD","Nadi","ID Observation Nadi","RR","ID Observation RR","Suhu","ID Observation Suhu",
                "Saturasi O²","ID Observation Saturasi O²","Nyeri","ID Observation Nyeri","Keluhan Utama","ID Observation Keluhan Utama",
                "Kebutuhan Khusus","ID Observation Kebutuhan Khusus","Catatan","ID Observation Catatan","Plan/Keputusan","ID Careplan Keputusan",
                "ID Observation Skala","ID Composition"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbIGDPrimer.setModel(tabModeIGDPrimer);
        tbIGDPrimer.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIGDPrimer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 40; i++) {
            TableColumn column = tbIGDPrimer.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(215);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(66);
            }else if(i==10){
                column.setPreferredWidth(215);
            }else if(i==11){
                column.setPreferredWidth(91);
            }else if(i==12){
                column.setPreferredWidth(215);
            }else if(i==13){
                column.setPreferredWidth(101);
            }else if(i==14){
                column.setPreferredWidth(130);
            }else if(i==15){
                column.setPreferredWidth(215);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(215);
            }else if(i==18){
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(215);
            }else if(i==20){
                column.setPreferredWidth(45);
            }else if(i==21){
                column.setPreferredWidth(215);
            }else if(i==22){
                column.setPreferredWidth(45);
            }else if(i==23){
                column.setPreferredWidth(215);
            }else if(i==24){
                column.setPreferredWidth(45);
            }else if(i==25){
                column.setPreferredWidth(215);
            }else if(i==26){
                column.setPreferredWidth(65);
            }else if(i==27){
                column.setPreferredWidth(215);
            }else if(i==28){
                column.setPreferredWidth(45);
            }else if(i==29){
                column.setPreferredWidth(215);
            }else if(i==30){
                column.setPreferredWidth(200);
            }else if(i==31){
                column.setPreferredWidth(215);
            }else if(i==32){
                column.setPreferredWidth(99);
            }else if(i==33){
                column.setPreferredWidth(215);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(215);
            }else if(i==36){
                column.setPreferredWidth(90);
            }else if(i==37){
                column.setPreferredWidth(215);
            }else if(i==38){
                column.setPreferredWidth(215);
            }else if(i==39){
                column.setPreferredWidth(215);
            }
        }
        tbIGDPrimer.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeIGDSekunder=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","ID Encounter","Petugas/Dokter/Praktisi","No.KTP Praktisi","Tanggal","Cara Masuk","ID Observation Cara Masuk",
                "Alat Transportasi","ID Observation Alat Transportasi","Alasan Kedatangan","Keterangan Kedatangan","ID Observation Alasan Kedatangan",
                "Macam Kasus","ID Observation Macam Kasus","TD","ID Observation TD","Nadi","ID Observation Nadi","RR","ID Observation RR","Suhu","ID Observation Suhu",
                "Saturasi O²","ID Observation Saturasi O²","Nyeri","ID Observation Nyeri","Anamnesa Singkat","ID Clinicalimpression Anamnesa","Catatan","ID Observation Catatan",
                "Plan/Keputusan","ID Careplan Keputusan","ID Observation Skala","ID Composition"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbIGDSekunder.setModel(tabModeIGDSekunder);
        tbIGDSekunder.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbIGDSekunder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 38; i++) {
            TableColumn column = tbIGDSekunder.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(215);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(66);
            }else if(i==10){
                column.setPreferredWidth(215);
            }else if(i==11){
                column.setPreferredWidth(91);
            }else if(i==12){
                column.setPreferredWidth(215);
            }else if(i==13){
                column.setPreferredWidth(101);
            }else if(i==14){
                column.setPreferredWidth(130);
            }else if(i==15){
                column.setPreferredWidth(215);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(215);
            }else if(i==18){
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(215);
            }else if(i==20){
                column.setPreferredWidth(45);
            }else if(i==21){
                column.setPreferredWidth(215);
            }else if(i==22){
                column.setPreferredWidth(45);
            }else if(i==23){
                column.setPreferredWidth(215);
            }else if(i==24){
                column.setPreferredWidth(45);
            }else if(i==25){
                column.setPreferredWidth(215);
            }else if(i==26){
                column.setPreferredWidth(65);
            }else if(i==27){
                column.setPreferredWidth(215);
            }else if(i==28){
                column.setPreferredWidth(45);
            }else if(i==29){
                column.setPreferredWidth(215);
            }else if(i==30){
                column.setPreferredWidth(215);
            }else if(i==31){
                column.setPreferredWidth(215);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(215);
            }else if(i==34){
                column.setPreferredWidth(90);
            }else if(i==35){
                column.setPreferredWidth(215);
            }else if(i==36){
                column.setPreferredWidth(215);
            }else if(i==37){
                column.setPreferredWidth(215);
            }
        }
        tbIGDSekunder.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeAwalKeperawatanIGD=new DefaultTableModel(null,new String[]{
                "P","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","ID Encounter","Petugas/Dokter/Praktisi","No.KTP Praktisi","Tanggal"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                 java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAwalKeperawatanIGD.setModel(tabModeAwalKeperawatanIGD);
        tbAwalKeperawatanIGD.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAwalKeperawatanIGD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbAwalKeperawatanIGD.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(215);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(115);
            }
        }
        tbAwalKeperawatanIGD.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        try {
            link=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }    
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTMLIGDPrimer.setEditable(true);
        LoadHTMLIGDPrimer.setEditorKit(kit);
        LoadHTMLIGDSekunder.setEditable(true);
        LoadHTMLIGDSekunder.setEditorKit(kit);
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
        LoadHTMLIGDPrimer.setDocument(doc);
        LoadHTMLIGDSekunder.setDocument(doc);
        
        ChkAccorIGDPrimer.setSelected(false);
        isMenuIGDPrimer();
        
        ChkAccorIGDSekunder.setSelected(false);
        isMenuIGDSekunder();
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnAll1 = new widget.Button();
        BtnKirim = new widget.Button();
        BtnUpdate = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalTriaseIGDPrimer = new widget.InternalFrame();
        PanelAccorIGDPrimer = new widget.PanelBiasa();
        ChkAccorIGDPrimer = new widget.CekBox();
        ScrollHTMLIGDPrimer = new widget.ScrollPane();
        LoadHTMLIGDPrimer = new widget.editorpane();
        ScrollIGDPrimer = new widget.ScrollPane();
        tbIGDPrimer = new widget.Table();
        internalTriaseIGDSekunder = new widget.InternalFrame();
        PanelAccorIGDSekunder = new widget.PanelBiasa();
        ChkAccorIGDSekunder = new widget.CekBox();
        ScrollHTMLIGDSekunder = new widget.ScrollPane();
        LoadHTMLIGDSekunder = new widget.editorpane();
        ScrollIGDSekunder = new widget.ScrollPane();
        tbIGDSekunder = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbAwalKeperawatanIGD = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbNadi = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbSpO2 = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbGCS = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbKesadaran = new widget.Table();
        Scroll6 = new widget.ScrollPane();
        tbTensi = new widget.Table();
        Scroll7 = new widget.ScrollPane();
        tbTB = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbBB = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbLP = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(150, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(150, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Data Composition RME Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(LCount);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setText("Semua");
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll1);

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnKirim.setMnemonic('K');
        BtnKirim.setText("Kirim");
        BtnKirim.setToolTipText("Alt+K");
        BtnKirim.setName("BtnKirim"); // NOI18N
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKirim);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update");
        BtnUpdate.setToolTipText("Alt+U");
        BtnUpdate.setName("BtnUpdate"); // NOI18N
        BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnUpdate);

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
        panelGlass8.add(BtnPrint);

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

        jLabel15.setText("Tgl.Registrasi :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-09-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d.");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-09-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel16.setText("Key Word :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalTriaseIGDPrimer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalTriaseIGDPrimer.setName("internalTriaseIGDPrimer"); // NOI18N
        internalTriaseIGDPrimer.setLayout(new java.awt.BorderLayout(1, 1));

        PanelAccorIGDPrimer.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccorIGDPrimer.setName("PanelAccorIGDPrimer"); // NOI18N
        PanelAccorIGDPrimer.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccorIGDPrimer.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccorIGDPrimer.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccorIGDPrimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccorIGDPrimer.setSelected(true);
        ChkAccorIGDPrimer.setFocusable(false);
        ChkAccorIGDPrimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccorIGDPrimer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccorIGDPrimer.setName("ChkAccorIGDPrimer"); // NOI18N
        ChkAccorIGDPrimer.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccorIGDPrimer.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccorIGDPrimer.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccorIGDPrimer.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccorIGDPrimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorIGDPrimerActionPerformed(evt);
            }
        });
        PanelAccorIGDPrimer.add(ChkAccorIGDPrimer, java.awt.BorderLayout.WEST);

        ScrollHTMLIGDPrimer.setBorder(null);
        ScrollHTMLIGDPrimer.setName("ScrollHTMLIGDPrimer"); // NOI18N
        ScrollHTMLIGDPrimer.setOpaque(true);
        ScrollHTMLIGDPrimer.setPreferredSize(new java.awt.Dimension(470, 16));

        LoadHTMLIGDPrimer.setBorder(null);
        LoadHTMLIGDPrimer.setName("LoadHTMLIGDPrimer"); // NOI18N
        ScrollHTMLIGDPrimer.setViewportView(LoadHTMLIGDPrimer);

        PanelAccorIGDPrimer.add(ScrollHTMLIGDPrimer, java.awt.BorderLayout.CENTER);

        internalTriaseIGDPrimer.add(PanelAccorIGDPrimer, java.awt.BorderLayout.EAST);

        ScrollIGDPrimer.setComponentPopupMenu(jPopupMenu1);
        ScrollIGDPrimer.setName("ScrollIGDPrimer"); // NOI18N
        ScrollIGDPrimer.setOpaque(true);

        tbIGDPrimer.setComponentPopupMenu(jPopupMenu1);
        tbIGDPrimer.setName("tbIGDPrimer"); // NOI18N
        tbIGDPrimer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbIGDPrimerMouseClicked(evt);
            }
        });
        tbIGDPrimer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbIGDPrimerKeyReleased(evt);
            }
        });
        ScrollIGDPrimer.setViewportView(tbIGDPrimer);

        internalTriaseIGDPrimer.add(ScrollIGDPrimer, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Triase IGD Primer", internalTriaseIGDPrimer);

        internalTriaseIGDSekunder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalTriaseIGDSekunder.setName("internalTriaseIGDSekunder"); // NOI18N
        internalTriaseIGDSekunder.setLayout(new java.awt.BorderLayout(1, 1));

        PanelAccorIGDSekunder.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccorIGDSekunder.setName("PanelAccorIGDSekunder"); // NOI18N
        PanelAccorIGDSekunder.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccorIGDSekunder.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccorIGDSekunder.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccorIGDSekunder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccorIGDSekunder.setSelected(true);
        ChkAccorIGDSekunder.setFocusable(false);
        ChkAccorIGDSekunder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccorIGDSekunder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccorIGDSekunder.setName("ChkAccorIGDSekunder"); // NOI18N
        ChkAccorIGDSekunder.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccorIGDSekunder.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccorIGDSekunder.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccorIGDSekunder.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccorIGDSekunder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorIGDSekunderActionPerformed(evt);
            }
        });
        PanelAccorIGDSekunder.add(ChkAccorIGDSekunder, java.awt.BorderLayout.WEST);

        ScrollHTMLIGDSekunder.setBorder(null);
        ScrollHTMLIGDSekunder.setName("ScrollHTMLIGDSekunder"); // NOI18N
        ScrollHTMLIGDSekunder.setOpaque(true);
        ScrollHTMLIGDSekunder.setPreferredSize(new java.awt.Dimension(470, 16));

        LoadHTMLIGDSekunder.setBorder(null);
        LoadHTMLIGDSekunder.setName("LoadHTMLIGDSekunder"); // NOI18N
        ScrollHTMLIGDSekunder.setViewportView(LoadHTMLIGDSekunder);

        PanelAccorIGDSekunder.add(ScrollHTMLIGDSekunder, java.awt.BorderLayout.CENTER);

        internalTriaseIGDSekunder.add(PanelAccorIGDSekunder, java.awt.BorderLayout.EAST);

        ScrollIGDSekunder.setComponentPopupMenu(jPopupMenu1);
        ScrollIGDSekunder.setName("ScrollIGDSekunder"); // NOI18N
        ScrollIGDSekunder.setOpaque(true);

        tbIGDSekunder.setComponentPopupMenu(jPopupMenu1);
        tbIGDSekunder.setName("tbIGDSekunder"); // NOI18N
        tbIGDSekunder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbIGDSekunderMouseClicked(evt);
            }
        });
        tbIGDSekunder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbIGDSekunderKeyPressed(evt);
            }
        });
        ScrollIGDSekunder.setViewportView(tbIGDSekunder);

        internalTriaseIGDSekunder.add(ScrollIGDSekunder, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Triase IGD Sekunder", internalTriaseIGDSekunder);

        Scroll1.setComponentPopupMenu(jPopupMenu1);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbAwalKeperawatanIGD.setComponentPopupMenu(jPopupMenu1);
        tbAwalKeperawatanIGD.setName("tbAwalKeperawatanIGD"); // NOI18N
        Scroll1.setViewportView(tbAwalKeperawatanIGD);

        TabRawat.addTab("Awal Keperawatan IGD", Scroll1);

        Scroll2.setComponentPopupMenu(jPopupMenu1);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNadi.setComponentPopupMenu(jPopupMenu1);
        tbNadi.setName("tbNadi"); // NOI18N
        Scroll2.setViewportView(tbNadi);

        TabRawat.addTab("Awal Keperawatan Ralan", Scroll2);

        Scroll3.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbSpO2.setComponentPopupMenu(jPopupMenu1);
        tbSpO2.setName("tbSpO2"); // NOI18N
        Scroll3.setViewportView(tbSpO2);

        TabRawat.addTab("Awal Keperawatan Ralan Gigi", Scroll3);

        Scroll4.setComponentPopupMenu(jPopupMenu1);
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbGCS.setComponentPopupMenu(jPopupMenu1);
        tbGCS.setName("tbGCS"); // NOI18N
        Scroll4.setViewportView(tbGCS);

        TabRawat.addTab("Awal Keperawatan Ralan Bayi", Scroll4);

        Scroll5.setComponentPopupMenu(jPopupMenu1);
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbKesadaran.setComponentPopupMenu(jPopupMenu1);
        tbKesadaran.setName("tbKesadaran"); // NOI18N
        Scroll5.setViewportView(tbKesadaran);

        TabRawat.addTab("Awal Keperawatan Ralan Kandungan", Scroll5);

        Scroll6.setComponentPopupMenu(jPopupMenu1);
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbTensi.setComponentPopupMenu(jPopupMenu1);
        tbTensi.setName("tbTensi"); // NOI18N
        Scroll6.setViewportView(tbTensi);

        TabRawat.addTab("Awal Keperawatan Ralan Pesikiatri", Scroll6);

        Scroll7.setComponentPopupMenu(jPopupMenu1);
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbTB.setComponentPopupMenu(jPopupMenu1);
        tbTB.setName("tbTB"); // NOI18N
        Scroll7.setViewportView(tbTB);

        TabRawat.addTab("Awal Keperawatan Ralan Geriatri", Scroll7);

        Scroll8.setComponentPopupMenu(jPopupMenu1);
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbBB.setComponentPopupMenu(jPopupMenu1);
        tbBB.setName("tbBB"); // NOI18N
        Scroll8.setViewportView(tbBB);

        TabRawat.addTab("Pengkajian Awal Fisioterapi", Scroll8);

        Scroll9.setComponentPopupMenu(jPopupMenu1);
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbLP.setComponentPopupMenu(jPopupMenu1);
        tbLP.setName("tbLP"); // NOI18N
        Scroll9.setViewportView(tbLP);

        TabRawat.addTab("Pengkajian Terapi Wicara", Scroll9);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                try{
                    StringBuilder htmlContent= new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Registrasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stts Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stts Lanjut</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Pulang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Encounter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu (°C)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas/Dokter/Praktisi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Praktisi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jam</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Observation Suhu</b></td>"+
                        "</tr>"
                    );
                    for (i = 0; i < tabModeIGDPrimer.getRowCount(); i++) {
                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,1).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,2).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,3).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,4).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,5).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,6).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,7).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,8).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,9).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,10).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,11).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,12).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,13).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,14).toString()+"</td>"+
                                "<td valign='top'>"+tbIGDPrimer.getValueAt(i,15).toString()+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='1500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );
                    htmlContent=null;

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

                    File f = new File("DataSatuSehatObservationTTVSuhu.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='1500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENGIRIMAN SATU SEHAT OBSERVATION-TTV SUHU TUBUH<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }
                break;
            default:
                break;
        }
        this.setCursor(Cursor.getDefaultCursor());    
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbIGDPrimer.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TabRawatMouseClicked(null);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            KirimTriaseIGDPrimer();
        }else if(TabRawat.getSelectedIndex()==1){
            KirimTriaseIGDSekunder();
        }else if(TabRawat.getSelectedIndex()==2){
            
        }
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                for(i=0;i<tbIGDPrimer.getRowCount();i++){
                    tbIGDPrimer.setValueAt(true,i,0);
                }   break;
            case 1:
                for(i=0;i<tbIGDSekunder.getRowCount();i++){
                    tbIGDSekunder.setValueAt(true,i,0);
                }   break;
            case 2:
                for(i=0;i<tbNadi.getRowCount();i++){
                    tbNadi.setValueAt(true,i,0);
                }   break;
            default:
                break;
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                for(i=0;i<tbIGDPrimer.getRowCount();i++){
                    tbIGDPrimer.setValueAt(false,i,0);
                }   break;
            case 1:
                for(i=0;i<tbIGDSekunder.getRowCount();i++){
                    tbIGDSekunder.setValueAt(false,i,0);
                }   break;
            case 2:
                for(i=0;i<tbNadi.getRowCount();i++){
                    tbNadi.setValueAt(false,i,0);
                }   break;
            default:
                break;
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                runBackground(() ->tampilIGDPrimer());
                break;
            case 1:
                runBackground(() ->tampilIGDSekunder());
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCari.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            TabRawatMouseClicked(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        TabRawatMouseClicked(null);
                    }
                }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorIGDPrimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorIGDPrimerActionPerformed
        if(tbIGDPrimer.getSelectedRow()!= -1){
            isMenuIGDPrimer();
            getDataIGDPrimer();
        }else{
            ChkAccorIGDPrimer.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan triasenya...!!!!");
        }
    }//GEN-LAST:event_ChkAccorIGDPrimerActionPerformed

    private void tbIGDPrimerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbIGDPrimerMouseClicked
        if(ChkAccorIGDPrimer.isSelected()==true){
            if(tbIGDPrimer.getSelectedRow()!= -1){
                getDataIGDPrimer();
            }
        }
    }//GEN-LAST:event_tbIGDPrimerMouseClicked

    private void tbIGDPrimerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbIGDPrimerKeyReleased
        if(tabModeIGDPrimer.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                if(tbIGDPrimer.getSelectedRow()!= -1){
                    getDataIGDPrimer();
                }
            }          
        }
    }//GEN-LAST:event_tbIGDPrimerKeyReleased

    private void ChkAccorIGDSekunderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorIGDSekunderActionPerformed
        if(tbIGDSekunder.getSelectedRow()!= -1){
            isMenuIGDSekunder();
            getDataIGDSekunder();
        }else{
            ChkAccorIGDSekunder.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan triasenya...!!!!");
        }
    }//GEN-LAST:event_ChkAccorIGDSekunderActionPerformed

    private void tbIGDSekunderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbIGDSekunderMouseClicked
        if(ChkAccorIGDSekunder.isSelected()==true){
            if(tbIGDSekunder.getSelectedRow()!= -1){
                getDataIGDSekunder();
            }
        }
    }//GEN-LAST:event_tbIGDSekunderMouseClicked

    private void tbIGDSekunderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbIGDSekunderKeyPressed
        if(tabModeIGDSekunder.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                if(tbIGDSekunder.getSelectedRow()!= -1){
                    getDataIGDSekunder();
                }
            }          
        }
    }//GEN-LAST:event_tbIGDSekunderKeyPressed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            UpdateTriaseIGDPrimer();
        }else if(TabRawat.getSelectedIndex()==1){
            UpdateTriaseIGDSekunder();
        }else if(TabRawat.getSelectedIndex()==2){
            
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatKirimCompositionRME dialog = new SatuSehatKirimCompositionRME(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Button BtnUpdate;
    private widget.CekBox ChkAccorIGDPrimer;
    private widget.CekBox ChkAccorIGDSekunder;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTMLIGDPrimer;
    private widget.editorpane LoadHTMLIGDSekunder;
    private widget.PanelBiasa PanelAccorIGDPrimer;
    private widget.PanelBiasa PanelAccorIGDSekunder;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ScrollPane ScrollHTMLIGDPrimer;
    private widget.ScrollPane ScrollHTMLIGDSekunder;
    private widget.ScrollPane ScrollIGDPrimer;
    private widget.ScrollPane ScrollIGDSekunder;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalTriaseIGDPrimer;
    private widget.InternalFrame internalTriaseIGDSekunder;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbAwalKeperawatanIGD;
    private widget.Table tbBB;
    private widget.Table tbGCS;
    private widget.Table tbIGDPrimer;
    private widget.Table tbIGDSekunder;
    private widget.Table tbKesadaran;
    private widget.Table tbLP;
    private widget.Table tbNadi;
    private widget.Table tbSpO2;
    private widget.Table tbTB;
    private widget.Table tbTensi;
    // End of variables declaration//GEN-END:variables
    
    private void tampilIGDPrimer() {
        Valid.tabelKosong(tabModeIGDPrimer);
        try{
            ps=koneksi.prepareStatement(
               "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,satu_sehat_encounter.id_encounter,pegawai.nama,pegawai.no_ktp as ktppraktisi,data_triase_igdprimer.tanggaltriase,data_triase_igd.cara_masuk,data_triase_igd.id_observation_cara_masuk,"+
               "data_triase_igd.alat_transportasi,data_triase_igd.id_observation_alat_transportasi,data_triase_igd.alasan_kedatangan,data_triase_igd.keterangan_kedatangan,data_triase_igd.id_observation_alasan_kedatangan,master_triase_macam_kasus.macam_kasus,data_triase_igd.id_observation_macam_kasus,"+
               "data_triase_igd.tekanan_darah,data_triase_igd.id_observation_tekanan_darah,data_triase_igd.nadi,data_triase_igd.id_observation_nadi,data_triase_igd.pernapasan,data_triase_igd.id_observation_pernapasan,data_triase_igd.suhu,data_triase_igd.id_observation_suhu,data_triase_igd.saturasi_o2,"+
               "data_triase_igd.id_observation_saturasi_o2,data_triase_igd.nyeri,data_triase_igd.id_observation_nyeri,data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.id_observation_keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.id_observation_kebutuhan_khusus,"+
               "data_triase_igdprimer.catatan,data_triase_igdprimer.id_observation_catatan,data_triase_igdprimer.plan,data_triase_igdprimer.id_careplan_keputusan,data_triase_igdprimer.id_observation_skala,data_triase_igd.id_composition from reg_periksa inner join data_triase_igd on reg_periksa.no_rawat=data_triase_igd.no_rawat "+
               "inner join data_triase_igdprimer on data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat inner join pegawai on data_triase_igdprimer.nik=pegawai.nik inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus  "+
               "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat where data_triase_igdprimer.tanggaltriase between ? and ? "+(TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or "+
               "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.no_ktp like ? or pegawai.no_ktp like ? or pegawai.nama like ? or master_triase_macam_kasus.macam_kasus like ?)")
            );
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIGDPrimer.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_ktp"),rs.getString("id_encounter"),rs.getString("nama"),rs.getString("ktppraktisi"),rs.getString("tanggaltriase"),rs.getString("cara_masuk"),rs.getString("id_observation_cara_masuk"),
                        rs.getString("alat_transportasi"),rs.getString("id_observation_alat_transportasi"),rs.getString("alasan_kedatangan"),rs.getString("keterangan_kedatangan"),rs.getString("id_observation_alasan_kedatangan"),rs.getString("macam_kasus"),rs.getString("id_observation_macam_kasus"),
                        rs.getString("tekanan_darah"),rs.getString("id_observation_tekanan_darah"),rs.getString("nadi"),rs.getString("id_observation_nadi"),rs.getString("pernapasan"),rs.getString("id_observation_pernapasan"),rs.getString("suhu"),rs.getString("id_observation_suhu"),rs.getString("saturasi_o2"),
                        rs.getString("id_observation_saturasi_o2"),rs.getString("nyeri"),rs.getString("id_observation_nyeri"),rs.getString("keluhan_utama"),rs.getString("id_observation_keluhan_utama"),rs.getString("kebutuhan_khusus"),rs.getString("id_observation_kebutuhan_khusus"),rs.getString("catatan"),
                        rs.getString("id_observation_catatan"),rs.getString("plan"),rs.getString("id_careplan_keputusan"),rs.getString("id_observation_skala"),rs.getString("id_composition")
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
            System.out.println("Notifikasi IGD Primer : "+e);
        }
        LCount.setText(""+tabModeIGDPrimer.getRowCount());
    }
    
    private void tampilIGDSekunder() {
        Valid.tabelKosong(tabModeIGDSekunder);
        try{
            ps=koneksi.prepareStatement(
               "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,satu_sehat_encounter.id_encounter,pegawai.nama,pegawai.no_ktp as ktppraktisi,data_triase_igdsekunder.tanggaltriase,data_triase_igd.cara_masuk,data_triase_igd.id_observation_cara_masuk,"+
               "data_triase_igd.alat_transportasi,data_triase_igd.id_observation_alat_transportasi,data_triase_igd.alasan_kedatangan,data_triase_igd.keterangan_kedatangan,data_triase_igd.id_observation_alasan_kedatangan,master_triase_macam_kasus.macam_kasus,data_triase_igd.id_observation_macam_kasus,"+
               "data_triase_igd.tekanan_darah,data_triase_igd.id_observation_tekanan_darah,data_triase_igd.nadi,data_triase_igd.id_observation_nadi,data_triase_igd.pernapasan,data_triase_igd.id_observation_pernapasan,data_triase_igd.suhu,data_triase_igd.id_observation_suhu,data_triase_igd.saturasi_o2,"+
               "data_triase_igd.id_observation_saturasi_o2,data_triase_igd.nyeri,data_triase_igd.id_observation_nyeri,data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.id_clinicalimpression_anamnesa,data_triase_igdsekunder.catatan,data_triase_igdsekunder.id_observation_catatan,"+
               "data_triase_igdsekunder.plan,data_triase_igdsekunder.id_careplan_keputusan,data_triase_igdsekunder.id_observation_skala,data_triase_igd.id_composition from reg_periksa inner join data_triase_igd on reg_periksa.no_rawat=data_triase_igd.no_rawat "+
               "inner join data_triase_igdsekunder on data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat inner join pegawai on data_triase_igdsekunder.nik=pegawai.nik inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus  "+
               "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat where data_triase_igdsekunder.tanggaltriase between ? and ? "+(TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or "+
               "reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.no_ktp like ? or pegawai.no_ktp like ? or pegawai.nama like ? or master_triase_macam_kasus.macam_kasus like ?)")
            );
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeIGDSekunder.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("no_ktp"),rs.getString("id_encounter"),rs.getString("nama"),rs.getString("ktppraktisi"),rs.getString("tanggaltriase"),rs.getString("cara_masuk"),rs.getString("id_observation_cara_masuk"),
                        rs.getString("alat_transportasi"),rs.getString("id_observation_alat_transportasi"),rs.getString("alasan_kedatangan"),rs.getString("keterangan_kedatangan"),rs.getString("id_observation_alasan_kedatangan"),rs.getString("macam_kasus"),rs.getString("id_observation_macam_kasus"),
                        rs.getString("tekanan_darah"),rs.getString("id_observation_tekanan_darah"),rs.getString("nadi"),rs.getString("id_observation_nadi"),rs.getString("pernapasan"),rs.getString("id_observation_pernapasan"),rs.getString("suhu"),rs.getString("id_observation_suhu"),rs.getString("saturasi_o2"),
                        rs.getString("id_observation_saturasi_o2"),rs.getString("nyeri"),rs.getString("id_observation_nyeri"),rs.getString("anamnesa_singkat"),rs.getString("id_clinicalimpression_anamnesa"),rs.getString("catatan"),rs.getString("id_observation_catatan"),rs.getString("plan"),
                        rs.getString("id_careplan_keputusan"),rs.getString("id_observation_skala"),rs.getString("id_composition")
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
            System.out.println("Notifikasi IGD Sekunder : "+e);
        }
        LCount.setText(""+tabModeIGDSekunder.getRowCount());
    }
    
    public void isCek(){
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_composition());
        BtnUpdate.setEnabled(akses.getsatu_sehat_kirim_composition());
        BtnPrint.setEnabled(akses.getsatu_sehat_kirim_composition());
    }
    
    private void isMenuIGDPrimer(){
        if(ChkAccorIGDPrimer.isSelected()==true){
            ChkAccorIGDPrimer.setVisible(false);
            PanelAccorIGDPrimer.setPreferredSize(new Dimension(520,HEIGHT));
            ScrollHTMLIGDPrimer.setVisible(true);  
            ChkAccorIGDPrimer.setVisible(true);
        }else if(ChkAccorIGDPrimer.isSelected()==false){   
            ChkAccorIGDPrimer.setVisible(false);
            PanelAccorIGDPrimer.setPreferredSize(new Dimension(15,HEIGHT));
            ScrollHTMLIGDPrimer.setVisible(false);
            ChkAccorIGDPrimer.setVisible(true);
        }
    }
    
    private void isMenuIGDSekunder(){
        if(ChkAccorIGDSekunder.isSelected()==true){
            ChkAccorIGDSekunder.setVisible(false);
            PanelAccorIGDSekunder.setPreferredSize(new Dimension(520,HEIGHT));
            ScrollHTMLIGDSekunder.setVisible(true);  
            ChkAccorIGDSekunder.setVisible(true);
        }else if(ChkAccorIGDSekunder.isSelected()==false){   
            ChkAccorIGDSekunder.setVisible(false);
            PanelAccorIGDSekunder.setPreferredSize(new Dimension(15,HEIGHT));
            ScrollHTMLIGDSekunder.setVisible(false);
            ChkAccorIGDSekunder.setVisible(true);
        }
    }
    
    private void getDataIGDPrimer() {
        try {
            cekada=0;
            StringBuilder htmlContent= new StringBuilder();
            ps=koneksi.prepareStatement(
                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                "inner join master_triase_skala1 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan "+
                "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 "+
                "where data_triase_igddetail_skala1.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan"
            );
            try {
                ps.setString(1,tbIGDPrimer.getValueAt(tbIGDPrimer.getSelectedRow(),1).toString());
                rs=ps.executeQuery();
                if(rs.next()){
                    htmlContent.append("<tr class='isi'>").
                                    append("<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Pemeriksaan</td>").
                                    append("<td valign='middle' bgcolor='#AA0000' color='ffffff' align='left' width='60%'>Immediate/Segera</td>").
                                append("</tr>");
                    rs.beforeFirst();
                    while(rs.next()){
                        htmlContent.append("<tr class='isi'>").
                                    append("<td valign='middle'>").append(rs.getString("nama_pemeriksaan")).append("</td>").
                                    append("<td valign='middle' bgcolor='#AA0000' color='ffffff'>").
                                    append("<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>");
                        ps2=koneksi.prepareStatement(
                            "select data_triase_igddetail_skala1.id_observation_skala1,master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                            "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "+
                            "order by data_triase_igddetail_skala1.kode_skala1"
                        );
                        try {
                            ps2.setString(1,rs.getString("kode_pemeriksaan"));
                            ps2.setString(2,tbIGDPrimer.getValueAt(tbIGDPrimer.getSelectedRow(),1).toString());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                htmlContent.append("<tr class='isi'>").
                                                append("<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='50%'>").append(rs2.getString("pengkajian_skala1")).append("</td>").
                                                append("<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='50%'>").append(rs2.getString("id_observation_skala1")).append("</td>").
                                            append("</tr>");
                                cekada++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        htmlContent.append("</table>").
                                    append("</td>").
                                    append("</tr>");
                    }
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

            if(cekada==0){
                ps=koneksi.prepareStatement(
                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                    "inner join master_triase_skala2 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan "+
                    "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 "+
                    "where data_triase_igddetail_skala2.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan"
                );
                try {
                    ps.setString(1,tbIGDPrimer.getValueAt(tbIGDPrimer.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle' bgcolor='#FFFAF8' align='center' width='60%'>Pemeriksaan</td>").                                    
                                        append("<td valign='middle' bgcolor='#FF0000' color='ffffff' align='left' width='40%'>Emergensi</td>").                                    
                                    append("</tr>");
                        rs.beforeFirst();
                        while(rs.next()){
                            htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle'>").append(rs.getString("nama_pemeriksaan")).append("</td>").                                    
                                        append("<td valign='middle' bgcolor='#FF0000' color='ffffff'>").
                                        append("<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>");
                            ps2=koneksi.prepareStatement(
                                "select data_triase_igddetail_skala2.id_observation_skala2,master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "+
                                "order by data_triase_igddetail_skala2.kode_skala2"
                            );
                            try {
                                ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                ps2.setString(2,rs.getString("no_rawat"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    htmlContent.append("<tr class='isi'>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='50%'>").append(rs2.getString("pengkajian_skala2")).append("</td>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='50%'>").append(rs2.getString("id_observation_skala2")).append("</td>").                                    
                                                append("</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            htmlContent.append("</table>").
                                        append("</td>").                                    
                                        append("</tr>");
                        }
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

            LoadHTMLIGDPrimer.setText(
                "<html>"+
                  "<table width='550px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void getDataIGDSekunder() {
        try {
            cekada=0;
            StringBuilder htmlContent= new StringBuilder();
            ps=koneksi.prepareStatement(
                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                "inner join master_triase_skala3 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan "+
                "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 "+
                "where data_triase_igddetail_skala3.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan"
            );
            try {
                ps.setString(1,tbIGDSekunder.getValueAt(tbIGDSekunder.getSelectedRow(),1).toString());
                rs=ps.executeQuery();
                if(rs.next()){
                    htmlContent.append("<tr class='isi'>").
                                    append("<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Pemeriksaan</td>").
                                    append("<td valign='middle' bgcolor='#C8C800' color='ffffff' align='left' width='60%'>Immediate/Segera</td>").
                                append("</tr>");
                    rs.beforeFirst();
                    while(rs.next()){
                        htmlContent.append("<tr class='isi'>").
                                    append("<td valign='middle'>").append(rs.getString("nama_pemeriksaan")).append("</td>").
                                    append("<td valign='middle' bgcolor='#C8C800' color='ffffff'>").
                                    append("<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>");
                        ps2=koneksi.prepareStatement(
                            "select data_triase_igddetail_skala3.id_observation_skala3,master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                            "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
                            "order by data_triase_igddetail_skala3.kode_skala3"
                        );
                        try {
                            ps2.setString(1,rs.getString("kode_pemeriksaan"));
                            ps2.setString(2,tbIGDSekunder.getValueAt(tbIGDSekunder.getSelectedRow(),1).toString());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                htmlContent.append("<tr class='isi'>").
                                                append("<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='50%'>").append(rs2.getString("pengkajian_skala3")).append("</td>").
                                                append("<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='50%'>").append(rs2.getString("id_observation_skala3")).append("</td>").
                                            append("</tr>");
                                cekada++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        htmlContent.append("</table>").
                                    append("</td>").
                                    append("</tr>");
                    }
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

            if(cekada==0){
                ps=koneksi.prepareStatement(
                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                    "inner join master_triase_skala4 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan "+
                    "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 "+
                    "where data_triase_igddetail_skala4.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan"
                );
                try {
                    ps.setString(1,tbIGDSekunder.getValueAt(tbIGDSekunder.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle' bgcolor='#FFFAF8' align='center' width='60%'>Pemeriksaan</td>").                                    
                                        append("<td valign='middle' bgcolor='#00AA00' color='ffffff' align='left' width='40%'>Emergensi</td>").                                    
                                    append("</tr>");
                        rs.beforeFirst();
                        while(rs.next()){
                            htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle'>").append(rs.getString("nama_pemeriksaan")).append("</td>").                                    
                                        append("<td valign='middle' bgcolor='#00AA00' color='ffffff'>").
                                        append("<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>");
                            ps2=koneksi.prepareStatement(
                                "select data_triase_igddetail_skala4.id_observation_skala4,master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
                                "order by data_triase_igddetail_skala4.kode_skala4"
                            );
                            try {
                                ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                ps2.setString(2,rs.getString("no_rawat"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    htmlContent.append("<tr class='isi'>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='50%'>").append(rs2.getString("pengkajian_skala4")).append("</td>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='50%'>").append(rs2.getString("id_observation_skala4")).append("</td>").                                    
                                                append("</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            htmlContent.append("</table>").
                                        append("</td>").                                    
                                        append("</tr>");
                        }
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
            
            if(cekada==0){
                ps=koneksi.prepareStatement(
                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                    "inner join master_triase_skala5 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan "+
                    "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 "+
                    "where data_triase_igddetail_skala5.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan"
                );
                try {
                    ps.setString(1,tbIGDSekunder.getValueAt(tbIGDSekunder.getSelectedRow(),1).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle' bgcolor='#FFFAF8' align='center' width='60%'>Pemeriksaan</td>").                                    
                                        append("<td valign='middle' bgcolor='#969696' color='ffffff' align='left' width='40%'>Emergensi</td>").                                    
                                    append("</tr>");
                        rs.beforeFirst();
                        while(rs.next()){
                            htmlContent.append("<tr class='isi'>").                                    
                                        append("<td valign='middle'>").append(rs.getString("nama_pemeriksaan")).append("</td>").                                    
                                        append("<td valign='middle' bgcolor='#969696' color='ffffff'>").
                                        append("<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>");
                            ps2=koneksi.prepareStatement(
                                "select data_triase_igddetail_skala5.id_observation_skala5,master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
                                "order by data_triase_igddetail_skala5.kode_skala5"
                            );
                            try {
                                ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                ps2.setString(2,rs.getString("no_rawat"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    htmlContent.append("<tr class='isi'>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='50%'>").append(rs2.getString("pengkajian_skala5")).append("</td>").                                    
                                                    append("<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='50%'>").append(rs2.getString("id_observation_skala5")).append("</td>").                                    
                                                append("</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            htmlContent.append("</table>").
                                        append("</td>").                                    
                                        append("</tr>");
                        }
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

            LoadHTMLIGDSekunder.setText(
                "<html>"+
                  "<table width='550px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void KirimTriaseIGDPrimer() {
        for(i=0;i<tbIGDPrimer.getRowCount();i++){
            if(tbIGDPrimer.getValueAt(i,0).toString().equals("true")&&(!tbIGDPrimer.getValueAt(i,4).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,7).toString().equals(""))){
                try {
                    iddokter = cekViaSatuSehat.tampilIDParktisi(tbIGDPrimer.getValueAt(i,7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbIGDPrimer.getValueAt(i,4).toString());
                    //Cara Masuk
                    if(tbIGDPrimer.getValueAt(i,10).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://snomed.info/sct\"," +
                                                    "\"code\": \"301998005\"," +
                                                    "\"display\": \"Ability to move\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Cara masuk IGD\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,9).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_cara_masuk=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,10);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Cara Masuk IGD : "+e);
                        }
                    }

                    //Transportasi
                    if(tbIGDPrimer.getValueAt(i,12).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11459-5\"," +
                                                    "\"display\": \"Transport mode EMS system\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alat transportasi kedatangan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,11).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_alat_transportasi=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,12);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Alat Transportasi : "+e);
                        } 
                    }

                    //Alasan Kedatangan
                    if(tbIGDPrimer.getValueAt(i,15).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11293-8\"," +
                                                    "\"display\": \"Type of Referral source\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alasan Kedatangan/Alasan Rujukan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,13).toString()+(tbIGDPrimer.getValueAt(i,14).toString().equals("")?"":", Keterangan : "+tbIGDPrimer.getValueAt(i,14).toString())+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_alasan_kedatangan=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,15);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Alasan Kedatangan : "+e);
                        } 
                    }

                    //Macam Kasus
                    if(tbIGDPrimer.getValueAt(i,17).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"29298-7\"," +
                                                    "\"display\": \"Reason for visit\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Macam Kasus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,16).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_macam_kasus=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,17);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Macam Kasus : "+e);
                        } 
                    }

                    //Tekanan Darah
                    if(tbIGDPrimer.getValueAt(i,19).toString().equals("")){
                        arrSplit = tbIGDPrimer.getValueAt(i,18).toString().split("/");
                        sistole="0";
                        try {
                            if(!arrSplit[0].equals("")){
                                sistole=arrSplit[0];
                            }
                        } catch (Exception e) {
                            sistole="0";
                        }
                        diastole="0";
                        try {
                            if(!arrSplit[1].equals("")){
                                diastole=arrSplit[1];
                            }
                        } catch (Exception e) {
                            diastole="0";
                        }
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"35094-2\"," +
                                                    "\"display\": \"Blood pressure panel\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Tekanan Darah\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"component\" : ["+
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8480-6\"," +
                                                            "\"display\" : \"Systolic blood pressure\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+sistole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}," +
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8462-4\"," +
                                                            "\"display\" : \"Diastolic blood pressure\"" +
                                                        "}"+
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+diastole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}"+
                                        "]" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_tekanan_darah=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,19);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Tekanan Darah : "+e);
                        }
                    }

                    //Nadi
                    if(tbIGDPrimer.getValueAt(i,21).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8867-4\"," +
                                                    "\"display\": \"Heart rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nadi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 20).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_nadi=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,21);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Nadi : "+e);
                        }
                    }

                    //Respirasi
                    if(tbIGDPrimer.getValueAt(i,23).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"9279-1\"," +
                                                    "\"display\": \"Respiratory rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Respirasi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 22).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_pernapasan=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,23);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Respirasi : "+e);
                        }
                    }

                    //Suhu Tubuh
                    if(tbIGDPrimer.getValueAt(i,25).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8310-5\"," +
                                                    "\"display\": \"Body temperature\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Suhu Tubuh\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 24).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"degree Celsius\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"Cel\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_suhu=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,25);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Suhu Tubuh : "+e);
                        }
                    }

                    //Saturasi Oksigen
                    if(tbIGDPrimer.getValueAt(i,27).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"59408-5\"," +
                                                    "\"display\": \"Oxygen saturation in Arterial blood by Pulse oximetry\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Saturasi Oksigen\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 26).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"%\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"%\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_saturasi_o2=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,27);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Saturasi O²(%) : "+e);
                        }
                    }

                    //Nyeri
                    if(tbIGDPrimer.getValueAt(i,29).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"72514-3\"," +
                                                    "\"display\": \"Pain severity 0-10 Score Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nyeri\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueInteger\": " + tbIGDPrimer.getValueAt(i, 28).toString().replaceAll("[^0-9]", "") +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_nyeri=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,29);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Nyeri : "+e);
                        }
                    }

                    //Keluhan Utama
                    if(tbIGDPrimer.getValueAt(i,31).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8661-1\"," +
                                                    "\"display\": \"Chief complaint - Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Keluhan Utama\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" + tbIGDPrimer.getValueAt(i,30).toString().replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ") + "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_observation_keluhan_utama=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,31);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Keluhan Utama : "+e);
                        }
                    }

                    //Kebutuhan Khusus
                    if((!tbIGDPrimer.getValueAt(i, 32).toString().equals("-"))&&tbIGDPrimer.getValueAt(i,33).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"74018-3\"," +
                                                    "\"display\": \"Alert category\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Kebutuhan Khusus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" + tbIGDPrimer.getValueAt(i, 32).toString() + "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_observation_kebutuhan_khusus=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,33);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Kebutuhan Khusus : "+e);
                        }
                    }

                    //Catatan
                    if((!tbIGDPrimer.getValueAt(i, 34).toString().equals("")) && tbIGDPrimer.getValueAt(i, 35).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"48767-8\"," +
                                                    "\"display\": \"Annotation comment [Interpretation] Narrative\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Catatan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" +tbIGDPrimer.getValueAt(i, 34).toString().replace("\"", "'")+ "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_observation_catatan=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,35);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Catatan : "+e);
                        }
                    }

                    //Keputusan
                    if(tbIGDPrimer.getValueAt(i, 37).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"CarePlan\"," +
                                        "\"status\": \"active\"," +
                                        "\"intent\": \"plan\"," +
                                        "\"title\": \"Rencana Penempatan Pasien IGD\"," +
                                        "\"description\": \"Zona Merah " + tbIGDPrimer.getValueAt(i, 36).toString()+"\"," +
                                        "\"category\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://snomed.info/sct\","+
                                                        "\"code\": \"771082000\","+
                                                        "\"display\": \"Acute medicine care plan\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]," +
                                        "\"subject\": {"+
                                            "\"reference\": \"Patient/" + idpasien + "\""+
                                        "}," +
                                        "\"encounter\": {"+
                                            "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                        "}," +
                                        "\"author\": {"+
                                            "\"reference\": \"Practitioner/" + iddokter + "\""+
                                        "}," +
                                        "\"period\": {"+
                                            "\"start\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/CarePlan");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/CarePlan", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_careplan_keputusan=?",2,new String[]{
                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDPrimer.setValueAt(response.asText(), i,37);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Keputusan : "+e);
                        }
                    }

                    //Triase Skala 1
                    cekada=0;
                    StringBuilder iyembuilder = new StringBuilder();
                    ps=koneksi.prepareStatement(
                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                        "inner join master_triase_skala1 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan "+
                        "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 "+
                        "where data_triase_igddetail_skala1.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                    );
                    try {
                        ps.setString(1,tbIGDPrimer.getValueAt(i,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(tbIGDPrimer.getValueAt(i, 38).toString().equals("")){
                                try{
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                    json = "{" +
                                                "\"resourceType\": \"Observation\"," +
                                                "\"status\": \"final\"," +
                                                "\"category\": [" +
                                                    "{" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                "\"code\": \"exam\"," +
                                                                "\"display\": \"exam\"" +
                                                            "}" +
                                                        "]" +
                                                    "}" +
                                                "]," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"75910-0\"," +
                                                            "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"Skala Triase\"" +
                                                "}," +
                                                "\"subject\": {" +
                                                    "\"reference\": \"Patient/"+idpasien+"\"" +
                                                "}," +
                                                "\"performer\": [" +
                                                    "{" +
                                                        "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                    "}" +
                                                "]," +
                                                "\"encounter\": {" +
                                                    "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                                "}," +
                                                "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"valueCodeableConcept\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"LA6112-2\"," +
                                                            "\"display\": \"1. Resusitasi\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"1. Resusitasi\"" +
                                                "}" +
                                           "}";
                                    System.out.println("URL : "+link+"/Observation");
                                    System.out.println("Request JSON : "+json);
                                    requestEntity = new HttpEntity(json,headers);
                                    json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                    System.out.println("Result JSON : "+json);
                                    root = mapper.readTree(json);
                                    response = root.path("id");
                                    if(!response.asText().equals("")){
                                        if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_observation_skala=?",2,new String[]{
                                                response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                            })==true){
                                            tbIGDPrimer.setValueAt(response.asText(), i,38);
                                        }
                                    }
                                }catch(Exception e){
                                    System.out.println("Notifikasi Triase Skala 1 : "+e);
                                }
                            }

                            rs.beforeFirst();
                            while(rs.next()){
                                ps2=koneksi.prepareStatement(
                                    "select data_triase_igddetail_skala1.kode_skala1,master_triase_skala1.pengkajian_skala1,data_triase_igddetail_skala1.id_observation_skala1 from master_triase_skala1 "+
                                    "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where master_triase_skala1.kode_pemeriksaan=? and "+
                                    "data_triase_igddetail_skala1.no_rawat=?"
                                );
                                try {
                                    ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                    ps2.setString(2,tbIGDPrimer.getValueAt(i,1).toString());
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        if(rs2.getString("id_observation_skala1").equals("")){
                                            try{
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                json = "{" +
                                                            "\"resourceType\": \"Observation\"," +
                                                            "\"status\": \"final\"," +
                                                            "\"category\": ["+
                                                                "{"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                            "\"code\": \"exam\","+
                                                                            "\"display\": \"exam\""+
                                                                        "}"+
                                                                    "]"+
                                                                "}"+
                                                            "]," +
                                                            "\"code\": {"+
                                                                "\"coding\": ["+
                                                                    "{"+
                                                                        "\"system\": \"http://loinc.org\","+
                                                                        "\"code\": \"75321-0\","+
                                                                        "\"display\": \"Clinical finding\""+
                                                                    "}"+
                                                                "],"+
                                                                "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                            "}," +
                                                            "\"subject\": {"+
                                                                "\"reference\": \"Patient/" + idpasien + "\""+
                                                            "}," +
                                                            "\"performer\": ["+
                                                                "{"+
                                                                    "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                "}"+
                                                            "]," +
                                                            "\"encounter\": {"+
                                                                "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                                            "}," +
                                                            "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"valueString\": \"" + rs2.getString("pengkajian_skala1").replace("\"", "'") + "\"" +
                                                       "}";
                                                System.out.println("URL : "+link+"/Observation");
                                                System.out.println("Request JSON : "+json);
                                                requestEntity = new HttpEntity(json,headers);
                                                json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                                System.out.println("Result JSON : "+json);
                                                root = mapper.readTree(json);
                                                response = root.path("id");
                                                if(!response.asText().equals("")){
                                                    Sequel.mengedit("data_triase_igddetail_skala1","no_rawat=? AND kode_skala1=?","id_observation_skala1=?",3,new String[]{
                                                        response.asText(),tbIGDPrimer.getValueAt(i,1).toString(),rs2.getString("kode_skala1")
                                                    });
                                                    iyembuilder.append("{\"reference\": \"Observation/").append(response.asText()).append("\"},");
                                                }
                                            }catch(Exception e){
                                                System.out.println("Notifikasi Triase Skala 1 : "+e);
                                            }
                                        }else{
                                            iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala1")).append("\"},");
                                        }
                                            
                                        cekada++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }

                    //Triase Skala 2
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala2 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 "+
                            "where data_triase_igddetail_skala2.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDPrimer.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(tbIGDPrimer.getValueAt(i, 38).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA6113-0\"," +
                                                                "\"display\": \"2. Emergensi\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"2. Emergensi\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation");
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                        root = mapper.readTree(json);
                                        response = root.path("id");
                                        if(!response.asText().equals("")){
                                            if(Sequel.mengedittf("data_triase_igdprimer","no_rawat=?","id_observation_skala=?",3,new String[]{
                                                    response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                                })==true){
                                                tbIGDPrimer.setValueAt(response.asText(), i,38);
                                            }
                                        }
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 2 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala2.kode_skala2,master_triase_skala2.pengkajian_skala2,data_triase_igddetail_skala2.id_observation_skala2 from master_triase_skala2 "+
                                        "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where master_triase_skala2.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala2.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDPrimer.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(rs2.getString("id_observation_skala2").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala2").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation");
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        Sequel.mengedit("data_triase_igddetail_skala2","no_rawat=? AND kode_skala2=?","id_observation_skala2=?",2,new String[]{
                                                            response.asText(),tbIGDPrimer.getValueAt(i,1).toString(),rs2.getString("kode_skala2")
                                                        });
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(response.asText()).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 2 : "+e);
                                                }
                                            }else{
                                                iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala2")).append("\"},");
                                            }
                                                
                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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
                    
                    //Composition IGD
                    if((!tbIGDPrimer.getValueAt(i,10).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,12).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,15).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,17).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,19).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,21).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,23).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,25).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,27).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,29).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,31).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,33).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,35).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,37).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,38).toString().equals(""))&&tbIGDPrimer.getValueAt(i,39).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Composition\"," +
                                        "\"status\": \"final\"," +
                                        "\"type\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"75500-9\"," +
                                                    "\"display\": \"Triage note\"" +
                                                "}" +
                                            "]" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"author\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"date\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"title\": \"Data Triase IGD\"," +
                                        "\"section\": [" +
                                            "{" +
                                                "\"title\": \"Identitas & Kedatangan\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11459-5\"," +
                                                            "\"display\": \"Mode of arrival\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,10).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,12).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,15).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,17).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Keluhan Utama\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"10154-3\"," +
                                                            "\"display\": \"Chief complaint\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,31).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Tanda Vital\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"8716-3\"," +
                                                            "\"display\": \"Vital signs\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,19).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,21).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,23).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,25).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,27).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Kebutuhan Khusus\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,33).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Pengkajian & Skala/Kategori Triase\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11283-9\"," +
                                                            "\"display\": \"Acuity assessment\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    iyembuilder.toString()+
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,29).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,38).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Catatan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,35).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Plan/Keputusan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"CarePlan/"+tbIGDPrimer.getValueAt(i,37).toString()+"\"}," +
                                                    "{\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}";
                            System.out.println("URL : "+link+"/Composition");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Composition",HttpMethod.POST,requestEntity,String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("ata_triase_igd","no_rawat=?","id_composition=?",2,new String[]{
                                    response.asText(),tbIGDPrimer.getValueAt(i,1).toString()
                                })==true){
                                    tbIGDPrimer.setValueAt(response.asText(),i,39);
                                    tbIGDPrimer.setValueAt(false,i,0);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Composition Triase IGD : "+e);
                        }
                    }
                    iyembuilder=null;
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
    }
    
    private void KirimTriaseIGDSekunder() {
        for(i=0;i<tbIGDSekunder.getRowCount();i++){
            if(tbIGDSekunder.getValueAt(i,0).toString().equals("true")&&(!tbIGDSekunder.getValueAt(i,4).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,7).toString().equals(""))){
                try {
                    iddokter = cekViaSatuSehat.tampilIDParktisi(tbIGDSekunder.getValueAt(i,7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbIGDSekunder.getValueAt(i,4).toString());
                    //Cara Masuk
                    if(tbIGDSekunder.getValueAt(i,10).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://snomed.info/sct\"," +
                                                    "\"code\": \"301998005\"," +
                                                    "\"display\": \"Ability to move\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Cara masuk IGD\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,9).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_cara_masuk=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,10);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Cara Masuk IGD : "+e);
                        }
                    }

                    //Transportasi
                    if(tbIGDSekunder.getValueAt(i,12).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11459-5\"," +
                                                    "\"display\": \"Transport mode EMS system\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alat transportasi kedatangan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,11).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_alat_transportasi=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,12);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Alat Transportasi : "+e);
                        } 
                    }

                    //Alasan Kedatangan
                    if(tbIGDSekunder.getValueAt(i,15).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11293-8\"," +
                                                    "\"display\": \"Type of Referral source\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alasan Kedatangan/Alasan Rujukan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,13).toString()+(tbIGDSekunder.getValueAt(i,14).toString().equals("")?"":", Keterangan : "+tbIGDSekunder.getValueAt(i,14).toString())+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_alasan_kedatangan=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,15);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Alasan Kedatangan : "+e);
                        } 
                    }

                    //Macam Kasus
                    if(tbIGDSekunder.getValueAt(i,17).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"29298-7\"," +
                                                    "\"display\": \"Reason for visit\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Macam Kasus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,16).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_macam_kasus=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,17);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Macam Kasus : "+e);
                        } 
                    }

                    //Tekanan Darah
                    if(tbIGDSekunder.getValueAt(i,19).toString().equals("")){
                        arrSplit = tbIGDSekunder.getValueAt(i,18).toString().split("/");
                        sistole="0";
                        try {
                            if(!arrSplit[0].equals("")){
                                sistole=arrSplit[0];
                            }
                        } catch (Exception e) {
                            sistole="0";
                        }
                        diastole="0";
                        try {
                            if(!arrSplit[1].equals("")){
                                diastole=arrSplit[1];
                            }
                        } catch (Exception e) {
                            diastole="0";
                        }
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"35094-2\"," +
                                                    "\"display\": \"Blood pressure panel\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Tekanan Darah\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"component\" : ["+
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8480-6\"," +
                                                            "\"display\" : \"Systolic blood pressure\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+sistole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}," +
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8462-4\"," +
                                                            "\"display\" : \"Diastolic blood pressure\"" +
                                                        "}"+
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+diastole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}"+
                                        "]" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_tekanan_darah=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,19);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Tekanan Darah : "+e);
                        }
                    }

                    //Nadi
                    if(tbIGDSekunder.getValueAt(i,21).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8867-4\"," +
                                                    "\"display\": \"Heart rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nadi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 20).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_nadi=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,21);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Nadi : "+e);
                        }
                    }

                    //Respirasi
                    if(tbIGDSekunder.getValueAt(i,23).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"9279-1\"," +
                                                    "\"display\": \"Respiratory rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Respirasi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 22).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_pernapasan=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,23);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Respirasi : "+e);
                        }
                    }

                    //Suhu Tubuh
                    if(tbIGDSekunder.getValueAt(i,25).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8310-5\"," +
                                                    "\"display\": \"Body temperature\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Suhu Tubuh\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 24).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"degree Celsius\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"Cel\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_suhu=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,25);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Suhu Tubuh : "+e);
                        }
                    }

                    //Saturasi Oksigen
                    if(tbIGDSekunder.getValueAt(i,27).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"59408-5\"," +
                                                    "\"display\": \"Oxygen saturation in Arterial blood by Pulse oximetry\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Saturasi Oksigen\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 26).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"%\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"%\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_saturasi_o2=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,27);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Saturasi O²(%) : "+e);
                        }
                    }

                    //Nyeri
                    if(tbIGDSekunder.getValueAt(i,29).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"72514-3\"," +
                                                    "\"display\": \"Pain severity 0-10 Score Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nyeri\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueInteger\": " + tbIGDSekunder.getValueAt(i, 28).toString().replaceAll("[^0-9]", "") +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_observation_nyeri=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,29);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Nyeri : "+e);
                        }
                    }
                    
                    //Anamnesa Singkat
                    if(tbIGDSekunder.getValueAt(i,31).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"ClinicalImpression\"," +
                                        "\"status\": \"completed\","+
                                        "\"description\" : \""+tbIGDSekunder.getValueAt(i,30).toString().replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ") +"\"," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"assessor\": {" +
                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"date\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"prognosisCodeableConcept\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\","+
                                                        "\"code\": \"PR000001\","+
                                                        "\"display\": \"Prognosis\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]"+
                                   "}";
                            System.out.println("URL : "+link+"/ClinicalImpression");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/ClinicalImpression", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_clinicalimpression_anamnesa=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,31);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Anamnesa Singkat : "+e);
                        }
                    }
                    
                    //Catatan
                    if((!tbIGDSekunder.getValueAt(i, 32).toString().equals("")) && tbIGDSekunder.getValueAt(i, 33).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"48767-8\"," +
                                                    "\"display\": \"Annotation comment [Interpretation] Narrative\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Catatan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" +tbIGDSekunder.getValueAt(i, 32).toString().replace("\"", "'")+ "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_observation_catatan=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,33);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Catatan : "+e);
                        }
                    }

                    //Keputusan
                    if(tbIGDSekunder.getValueAt(i, 35).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"CarePlan\"," +
                                        "\"status\": \"active\"," +
                                        "\"intent\": \"plan\"," +
                                        "\"title\": \"Rencana Penempatan Pasien IGD\"," +
                                        "\"description\": \"" + tbIGDSekunder.getValueAt(i, 34).toString()+"\"," +
                                        "\"category\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://snomed.info/sct\","+
                                                        "\"code\": \"771082000\","+
                                                        "\"display\": \"Acute medicine care plan\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]," +
                                        "\"subject\": {"+
                                            "\"reference\": \"Patient/" + idpasien + "\""+
                                        "}," +
                                        "\"encounter\": {"+
                                            "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                        "}," +
                                        "\"author\": {"+
                                            "\"reference\": \"Practitioner/" + iddokter + "\""+
                                        "}," +
                                        "\"period\": {"+
                                            "\"start\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/CarePlan");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/CarePlan", HttpMethod.POST, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_careplan_keputusan=?",2,new String[]{
                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                    })==true){
                                    tbIGDSekunder.setValueAt(response.asText(), i,35);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Keputusan : "+e);
                        }
                    }
                    
                    //Triase Skala 3
                    cekada=0;
                    StringBuilder iyembuilder = new StringBuilder();
                    ps=koneksi.prepareStatement(
                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                        "inner join master_triase_skala3 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan "+
                        "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 "+
                        "where data_triase_igddetail_skala3.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                    );
                    try {
                        ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                try{
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                    json = "{" +
                                                "\"resourceType\": \"Observation\"," +
                                                "\"status\": \"final\"," +
                                                "\"category\": [" +
                                                    "{" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                "\"code\": \"exam\"," +
                                                                "\"display\": \"exam\"" +
                                                            "}" +
                                                        "]" +
                                                    "}" +
                                                "]," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"75910-0\"," +
                                                            "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"Skala Triase\"" +
                                                "}," +
                                                "\"subject\": {" +
                                                    "\"reference\": \"Patient/"+idpasien+"\"" +
                                                "}," +
                                                "\"performer\": [" +
                                                    "{" +
                                                        "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                    "}" +
                                                "]," +
                                                "\"encounter\": {" +
                                                    "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                "}," +
                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"valueCodeableConcept\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"LA6114-8\"," +
                                                            "\"display\": \"3. Urgent\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"3. Urgent\"" +
                                                "}" +
                                           "}";
                                    System.out.println("URL : "+link+"/Observation");
                                    System.out.println("Request JSON : "+json);
                                    requestEntity = new HttpEntity(json,headers);
                                    json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                    System.out.println("Result JSON : "+json);
                                    root = mapper.readTree(json);
                                    response = root.path("id");
                                    if(!response.asText().equals("")){
                                        if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_observation_skala=?",2,new String[]{
                                                response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                            })==true){
                                            tbIGDSekunder.setValueAt(response.asText(), i,36);
                                        }
                                    }
                                }catch(Exception e){
                                    System.out.println("Notifikasi Triase Skala 3 : "+e);
                                }
                            }

                            rs.beforeFirst();
                            while(rs.next()){
                                ps2=koneksi.prepareStatement(
                                    "select data_triase_igddetail_skala3.kode_skala3,master_triase_skala3.pengkajian_skala3,data_triase_igddetail_skala3.id_observation_skala3 from master_triase_skala3 "+
                                    "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where master_triase_skala3.kode_pemeriksaan=? and "+
                                    "data_triase_igddetail_skala3.no_rawat=?"
                                );
                                try {
                                    ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                    ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        if(rs2.getString("id_observation_skala3").equals("")){
                                            try{
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                json = "{" +
                                                            "\"resourceType\": \"Observation\"," +
                                                            "\"status\": \"final\"," +
                                                            "\"category\": ["+
                                                                "{"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                            "\"code\": \"exam\","+
                                                                            "\"display\": \"exam\""+
                                                                        "}"+
                                                                    "]"+
                                                                "}"+
                                                            "]," +
                                                            "\"code\": {"+
                                                                "\"coding\": ["+
                                                                    "{"+
                                                                        "\"system\": \"http://loinc.org\","+
                                                                        "\"code\": \"75321-0\","+
                                                                        "\"display\": \"Clinical finding\""+
                                                                    "}"+
                                                                "],"+
                                                                "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                            "}," +
                                                            "\"subject\": {"+
                                                                "\"reference\": \"Patient/" + idpasien + "\""+
                                                            "}," +
                                                            "\"performer\": ["+
                                                                "{"+
                                                                    "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                "}"+
                                                            "]," +
                                                            "\"encounter\": {"+
                                                                "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                            "}," +
                                                            "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"valueString\": \"" + rs2.getString("pengkajian_skala3").replace("\"", "'") + "\"" +
                                                       "}";
                                                System.out.println("URL : "+link+"/Observation");
                                                System.out.println("Request JSON : "+json);
                                                requestEntity = new HttpEntity(json,headers);
                                                json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                                System.out.println("Result JSON : "+json);
                                                root = mapper.readTree(json);
                                                response = root.path("id");
                                                if(!response.asText().equals("")){
                                                    Sequel.mengedit("data_triase_igddetail_skala3","no_rawat=? AND kode_skala3=?","id_observation_skala3=?",3,new String[]{
                                                        response.asText(),tbIGDSekunder.getValueAt(i,1).toString(),rs2.getString("kode_skala3")
                                                    });
                                                    iyembuilder.append("{\"reference\": \"Observation/").append(response.asText()).append("\"},");
                                                }
                                            }catch(Exception e){
                                                System.out.println("Notifikasi Triase Skala 3 : "+e);
                                            }
                                        }else{
                                            iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala3")).append("\"},");
                                        }
                                            
                                        cekada++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }

                    //Triase Skala 4
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala4 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 "+
                            "where data_triase_igddetail_skala4.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA6115-5\"," +
                                                                "\"display\": \"4. Less Urgent\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"4. Less Urgent\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation");
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                        root = mapper.readTree(json);
                                        response = root.path("id");
                                        if(!response.asText().equals("")){
                                            if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_observation_skala=?",3,new String[]{
                                                    response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                                })==true){
                                                tbIGDSekunder.setValueAt(response.asText(), i,36);
                                            }
                                        }
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 4 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala4.kode_skala4,master_triase_skala4.pengkajian_skala4,data_triase_igddetail_skala4.id_observation_skala4 from master_triase_skala4 "+
                                        "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where master_triase_skala4.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala4.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(rs2.getString("id_observation_skala4").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala4").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation");
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        Sequel.mengedit("data_triase_igddetail_skala4","no_rawat=? AND kode_skala4=?","id_observation_skala4=?",2,new String[]{
                                                            response.asText(),tbIGDSekunder.getValueAt(i,1).toString(),rs2.getString("kode_skala4")
                                                        });
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(response.asText()).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 4 : "+e);
                                                }
                                            }else{
                                                iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala4")).append("\"},");
                                            }
                                                
                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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
                    
                    //Triase Skala 5
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala5 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 "+
                            "where data_triase_igddetail_skala5.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA10137-0\"," +
                                                                "\"display\": \"5. Non Urgent\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"5. Non Urgent\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation");
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                        root = mapper.readTree(json);
                                        response = root.path("id");
                                        if(!response.asText().equals("")){
                                            if(Sequel.mengedittf("data_triase_igdsekunder","no_rawat=?","id_observation_skala=?",3,new String[]{
                                                    response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                                })==true){
                                                tbIGDSekunder.setValueAt(response.asText(), i,36);
                                            }
                                        }
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 5 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala5.kode_skala5,master_triase_skala5.pengkajian_skala5,data_triase_igddetail_skala5.id_observation_skala5 from master_triase_skala5 "+
                                        "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where master_triase_skala5.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala5.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(rs2.getString("id_observation_skala5").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala5").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation");
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation", HttpMethod.POST, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        Sequel.mengedit("data_triase_igddetail_skala5","no_rawat=? AND kode_skala5=?","id_observation_skala5=?",2,new String[]{
                                                            response.asText(),tbIGDSekunder.getValueAt(i,1).toString(),rs2.getString("kode_skala5")
                                                        });
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(response.asText()).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 5 : "+e);
                                                }
                                            }else{
                                                iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala5")).append("\"},");
                                            }
                                                
                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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
                    
                    //Composition IGD Sekunder
                    if((!tbIGDSekunder.getValueAt(i,10).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,12).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,15).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,17).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,19).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,21).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,23).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,25).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,27).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,29).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,31).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,33).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,35).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,36).toString().equals(""))&&tbIGDSekunder.getValueAt(i,37).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Composition\"," +
                                        "\"status\": \"final\"," +
                                        "\"type\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"75500-9\"," +
                                                    "\"display\": \"Triage note\"" +
                                                "}" +
                                            "]" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"author\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"date\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"title\": \"Data Triase IGD Sekunder\"," +
                                        "\"section\": [" +
                                            "{" +
                                                "\"title\": \"Identitas & Kedatangan\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11459-5\"," +
                                                            "\"display\": \"Mode of arrival\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,10).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,12).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,15).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,17).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Anamnesa\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"ClinicalImpression/"+tbIGDSekunder.getValueAt(i,31).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Tanda Vital\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"8716-3\"," +
                                                            "\"display\": \"Vital signs\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,19).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,21).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,23).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,25).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,27).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Pengkajian & Skala/Kategori Triase\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11283-9\"," +
                                                            "\"display\": \"Acuity assessment\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    iyembuilder.toString()+
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,29).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,36).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Catatan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,33).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Plan/Keputusan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"CarePlan/"+tbIGDSekunder.getValueAt(i,35).toString()+"\"}," +
                                                    "{\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}";
                            System.out.println("URL : "+link+"/Composition");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Composition",HttpMethod.POST,requestEntity,String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            root = mapper.readTree(json);
                            response = root.path("id");
                            if(!response.asText().equals("")){
                                if(Sequel.mengedittf("data_triase_igd","no_rawat=?","id_composition=?",2,new String[]{
                                    response.asText(),tbIGDSekunder.getValueAt(i,1).toString()
                                })==true){
                                    tbIGDSekunder.setValueAt(response.asText(),i,37);
                                    tbIGDSekunder.setValueAt(false,i,0);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Notifikasi Composition Triase IGD Sekunder : "+e);
                        }
                    }
                    iyembuilder=null;
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
    }
    
    private void UpdateTriaseIGDPrimer() {
        for(i=0;i<tbIGDPrimer.getRowCount();i++){
            if(tbIGDPrimer.getValueAt(i,0).toString().equals("true")&&(!tbIGDPrimer.getValueAt(i,4).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,7).toString().equals(""))){
                try {
                    iddokter = cekViaSatuSehat.tampilIDParktisi(tbIGDPrimer.getValueAt(i,7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbIGDPrimer.getValueAt(i,4).toString());
                    //Cara Masuk
                    if(!tbIGDPrimer.getValueAt(i,10).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,10).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://snomed.info/sct\"," +
                                                    "\"code\": \"301998005\"," +
                                                    "\"display\": \"Ability to move\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Cara masuk IGD\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,9).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,10).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Cara Masuk IGD : "+e);
                        }
                    }

                    //Transportasi
                    if(!tbIGDPrimer.getValueAt(i,12).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,12).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11459-5\"," +
                                                    "\"display\": \"Transport mode EMS system\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alat transportasi kedatangan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,11).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,12).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Alat Transportasi : "+e);
                        }
                    }

                    //Alasan Kedatangan
                    if(!tbIGDPrimer.getValueAt(i,15).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,15).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11293-8\"," +
                                                    "\"display\": \"Type of Referral source\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alasan Kedatangan/Alasan Rujukan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,13).toString()+(tbIGDPrimer.getValueAt(i,14).toString().equals("")?"":", Keterangan : "+tbIGDPrimer.getValueAt(i,14).toString())+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,15).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Alasan Kedatangan : "+e);
                        }
                    }

                    //Macam Kasus
                    if(!tbIGDPrimer.getValueAt(i,17).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,17).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"29298-7\"," +
                                                    "\"display\": \"Reason for visit\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Macam Kasus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDPrimer.getValueAt(i,16).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,17).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Macam Kasus : "+e);
                        }
                    }

                    //Tekanan Darah
                    if(!tbIGDPrimer.getValueAt(i,19).toString().equals("")){
                        arrSplit = tbIGDPrimer.getValueAt(i,18).toString().split("/");
                        sistole="0";
                        try {
                            if(!arrSplit[0].equals("")){
                                sistole=arrSplit[0];
                            }
                        } catch (Exception e) {
                            sistole="0";
                        }
                        diastole="0";
                        try {
                            if(!arrSplit[1].equals("")){
                                diastole=arrSplit[1];
                            }
                        } catch (Exception e) {
                            diastole="0";
                        }
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,19).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"35094-2\"," +
                                                    "\"display\": \"Blood pressure panel\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Tekanan Darah\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"component\" : ["+
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8480-6\"," +
                                                            "\"display\" : \"Systolic blood pressure\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+sistole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}," +
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8462-4\"," +
                                                            "\"display\" : \"Diastolic blood pressure\"" +
                                                        "}"+
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+diastole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}"+
                                        "]" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,19).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Tekanan Darah : "+e);
                        }
                    }

                    //Nadi
                    if(!tbIGDPrimer.getValueAt(i,21).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,21).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8867-4\"," +
                                                    "\"display\": \"Heart rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nadi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 20).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,21).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Nadi : "+e);
                        }
                    }

                    //Respirasi
                    if(!tbIGDPrimer.getValueAt(i,23).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,23).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"9279-1\"," +
                                                    "\"display\": \"Respiratory rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Respirasi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 22).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,23).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Respirasi : "+e);
                        }
                    }

                    //Suhu Tubuh
                    if(!tbIGDPrimer.getValueAt(i,25).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,25).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8310-5\"," +
                                                    "\"display\": \"Body temperature\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Suhu Tubuh\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 24).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"degree Celsius\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"Cel\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,25).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Suhu Tubuh : "+e);
                        }
                    }

                    //Saturasi Oksigen
                    if(!tbIGDPrimer.getValueAt(i,27).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,27).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"59408-5\"," +
                                                    "\"display\": \"Oxygen saturation in Arterial blood by Pulse oximetry\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Saturasi Oksigen\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDPrimer.getValueAt(i, 26).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"%\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"%\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,27).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Saturasi O²(%) : "+e);
                        }
                    }

                    //Nyeri
                    if(!tbIGDPrimer.getValueAt(i,29).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,29).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"72514-3\"," +
                                                    "\"display\": \"Pain severity 0-10 Score Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nyeri\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueInteger\": " + tbIGDPrimer.getValueAt(i, 28).toString().replaceAll("[^0-9]", "") +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,29).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Nyeri : "+e);
                        }
                    }

                    //Keluhan Utama
                    if(!tbIGDPrimer.getValueAt(i,31).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,31).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8661-1\"," +
                                                    "\"display\": \"Chief complaint - Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Keluhan Utama\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" + tbIGDPrimer.getValueAt(i,30).toString().replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ") + "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,31).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Keluhan Utama : "+e);
                        }
                    }

                    //Kebutuhan Khusus
                    if((!tbIGDPrimer.getValueAt(i, 32).toString().equals("-"))&&(!tbIGDPrimer.getValueAt(i,33).toString().equals(""))){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,33).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"74018-3\"," +
                                                    "\"display\": \"Alert category\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Kebutuhan Khusus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" + tbIGDPrimer.getValueAt(i, 32).toString() + "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,33).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Kebutuhan Khusus : "+e);
                        }
                    }

                    //Catatan
                    if((!tbIGDPrimer.getValueAt(i, 34).toString().equals("")) && (!tbIGDPrimer.getValueAt(i, 35).toString().equals(""))){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,35).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"48767-8\"," +
                                                    "\"display\": \"Annotation comment [Interpretation] Narrative\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Catatan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" +tbIGDPrimer.getValueAt(i, 34).toString().replace("\"", "'")+ "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,35).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Catatan : "+e);
                        }
                    }

                    //Keputusan
                    if(!tbIGDPrimer.getValueAt(i, 37).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"CarePlan\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,37).toString()+"\"," +
                                        "\"status\": \"active\"," +
                                        "\"intent\": \"plan\"," +
                                        "\"title\": \"Rencana Penempatan Pasien IGD\"," +
                                        "\"description\": \"Zona Merah " + tbIGDPrimer.getValueAt(i, 36).toString()+"\"," +
                                        "\"category\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://snomed.info/sct\","+
                                                        "\"code\": \"771082000\","+
                                                        "\"display\": \"Acute medicine care plan\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]," +
                                        "\"subject\": {"+
                                            "\"reference\": \"Patient/" + idpasien + "\""+
                                        "}," +
                                        "\"encounter\": {"+
                                            "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                        "}," +
                                        "\"author\": {"+
                                            "\"reference\": \"Practitioner/" + iddokter + "\""+
                                        "}," +
                                        "\"period\": {"+
                                            "\"start\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/CarePlan");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/CarePlan/"+tbIGDPrimer.getValueAt(i,37).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Keputusan : "+e);
                        }
                    }

                    //Triase Skala 1
                    cekada=0;
                    StringBuilder iyembuilder = new StringBuilder();
                    ps=koneksi.prepareStatement(
                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                        "inner join master_triase_skala1 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan "+
                        "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 "+
                        "where data_triase_igddetail_skala1.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                    );
                    try {
                        ps.setString(1,tbIGDPrimer.getValueAt(i,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(!tbIGDPrimer.getValueAt(i, 38).toString().equals("")){
                                try{
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                    json = "{" +
                                                "\"resourceType\": \"Observation\"," +
                                                "\"id\": \""+tbIGDPrimer.getValueAt(i,38).toString()+"\"," +
                                                "\"status\": \"final\"," +
                                                "\"category\": [" +
                                                    "{" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                "\"code\": \"exam\"," +
                                                                "\"display\": \"exam\"" +
                                                            "}" +
                                                        "]" +
                                                    "}" +
                                                "]," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"75910-0\"," +
                                                            "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"Skala Triase\"" +
                                                "}," +
                                                "\"subject\": {" +
                                                    "\"reference\": \"Patient/"+idpasien+"\"" +
                                                "}," +
                                                "\"performer\": [" +
                                                    "{" +
                                                        "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                    "}" +
                                                "]," +
                                                "\"encounter\": {" +
                                                    "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                                "}," +
                                                "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"valueCodeableConcept\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"LA6112-2\"," +
                                                            "\"display\": \"1. Resusitasi\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"1. Resusitasi\"" +
                                                "}" +
                                           "}";
                                    System.out.println("URL : "+link+"/Observation");
                                    System.out.println("Request JSON : "+json);
                                    requestEntity = new HttpEntity(json,headers);
                                    json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,38).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                                    System.out.println("Result JSON : "+json);
                                }catch(Exception e){
                                    System.out.println("Notifikasi Triase Skala 1 : "+e);
                                }
                            }

                            rs.beforeFirst();
                            while(rs.next()){
                                ps2=koneksi.prepareStatement(
                                    "select data_triase_igddetail_skala1.kode_skala1,master_triase_skala1.pengkajian_skala1,data_triase_igddetail_skala1.id_observation_skala1 from master_triase_skala1 "+
                                    "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where master_triase_skala1.kode_pemeriksaan=? and "+
                                    "data_triase_igddetail_skala1.no_rawat=?"
                                );
                                try {
                                    ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                    ps2.setString(2,tbIGDPrimer.getValueAt(i,1).toString());
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        if(!rs2.getString("id_observation_skala1").equals("")){
                                            try{
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                json = "{" +
                                                            "\"resourceType\": \"Observation\"," +
                                                            "\"id\": \""+rs2.getString("id_observation_skala1")+"\"," +
                                                            "\"status\": \"final\"," +
                                                            "\"category\": ["+
                                                                "{"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                            "\"code\": \"exam\","+
                                                                            "\"display\": \"exam\""+
                                                                        "}"+
                                                                    "]"+
                                                                "}"+
                                                            "]," +
                                                            "\"code\": {"+
                                                                "\"coding\": ["+
                                                                    "{"+
                                                                        "\"system\": \"http://loinc.org\","+
                                                                        "\"code\": \"75321-0\","+
                                                                        "\"display\": \"Clinical finding\""+
                                                                    "}"+
                                                                "],"+
                                                                "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                            "}," +
                                                            "\"subject\": {"+
                                                                "\"reference\": \"Patient/" + idpasien + "\""+
                                                            "}," +
                                                            "\"performer\": ["+
                                                                "{"+
                                                                    "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                "}"+
                                                            "]," +
                                                            "\"encounter\": {"+
                                                                "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                                            "}," +
                                                            "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"valueString\": \"" + rs2.getString("pengkajian_skala1").replace("\"", "'") + "\"" +
                                                       "}";
                                                System.out.println("URL : "+link+"/Observation");
                                                System.out.println("Request JSON : "+json);
                                                requestEntity = new HttpEntity(json,headers);
                                                json=api.getRest().exchange(link+"/Observation/"+rs2.getString("id_observation_skala1"), HttpMethod.PUT, requestEntity, String.class).getBody();
                                                System.out.println("Result JSON : "+json);
                                                root = mapper.readTree(json);
                                                response = root.path("id");
                                                if(!response.asText().equals("")){
                                                    iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala1")).append("\"},");
                                                }
                                            }catch(Exception e){
                                                System.out.println("Notifikasi Triase Skala 1 : "+e);
                                            }
                                        }

                                        cekada++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }

                    //Triase Skala 2
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala2 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 "+
                            "where data_triase_igddetail_skala2.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDPrimer.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(!tbIGDPrimer.getValueAt(i, 38).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"id\": \""+tbIGDPrimer.getValueAt(i,38).toString()+"\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA6113-0\"," +
                                                                "\"display\": \"2. Emergensi\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"2. Emergensi\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation");
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation/"+tbIGDPrimer.getValueAt(i,38).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 2 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala2.kode_skala2,master_triase_skala2.pengkajian_skala2,data_triase_igddetail_skala2.id_observation_skala2 from master_triase_skala2 "+
                                        "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where master_triase_skala2.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala2.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDPrimer.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(!rs2.getString("id_observation_skala2").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"id\": \""+rs2.getString("id_observation_skala2")+"\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDPrimer.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala2").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation");
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation/"+rs2.getString("id_observation_skala2"), HttpMethod.PUT, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala2")).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 2 : "+e);
                                                }
                                            }

                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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

                    //Composition IGD
                    if((!tbIGDPrimer.getValueAt(i,10).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,12).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,15).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,17).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,19).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,21).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,23).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,25).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,27).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,29).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,31).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,33).toString().equals(""))&&
                            (!tbIGDPrimer.getValueAt(i,35).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,37).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,38).toString().equals(""))&&(!tbIGDPrimer.getValueAt(i,39).toString().equals(""))){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Composition\"," +
                                        "\"id\": \""+tbIGDPrimer.getValueAt(i,39).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"type\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"75500-9\"," +
                                                    "\"display\": \"Triage note\"" +
                                                "}" +
                                            "]" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"author\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"date\": \""+tbIGDPrimer.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"title\": \"Data Triase IGD\"," +
                                        "\"section\": [" +
                                            "{" +
                                                "\"title\": \"Identitas & Kedatangan\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11459-5\"," +
                                                            "\"display\": \"Mode of arrival\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,10).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,12).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,15).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,17).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Keluhan Utama\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"10154-3\"," +
                                                            "\"display\": \"Chief complaint\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,31).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Tanda Vital\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"8716-3\"," +
                                                            "\"display\": \"Vital signs\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,19).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,21).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,23).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,25).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,27).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Kebutuhan Khusus\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,33).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Pengkajian & Skala/Kategori Triase\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11283-9\"," +
                                                            "\"display\": \"Acuity assessment\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    iyembuilder.toString()+
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,29).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,38).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Catatan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDPrimer.getValueAt(i,35).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Plan/Keputusan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"CarePlan/"+tbIGDPrimer.getValueAt(i,37).toString()+"\"}," +
                                                    "{\"reference\": \"Encounter/"+tbIGDPrimer.getValueAt(i,5).toString()+"\"}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}";
                            System.out.println("URL : "+link+"/Composition");
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Composition",HttpMethod.PUT,requestEntity,String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            tbIGDPrimer.setValueAt(false,i,0);
                        }catch(Exception e){
                            System.out.println("Notifikasi Composition Triase IGD : "+e);
                        }
                    }
                    iyembuilder=null;
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
    }
    
    private void UpdateTriaseIGDSekunder() {
        for(i=0;i<tbIGDSekunder.getRowCount();i++){
            if(tbIGDSekunder.getValueAt(i,0).toString().equals("true")&&(!tbIGDSekunder.getValueAt(i,4).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,7).toString().equals(""))){
                try {
                    iddokter = cekViaSatuSehat.tampilIDParktisi(tbIGDSekunder.getValueAt(i,7).toString());
                    idpasien = cekViaSatuSehat.tampilIDPasien(tbIGDSekunder.getValueAt(i,4).toString());
                    //Cara Masuk
                    if(!tbIGDSekunder.getValueAt(i,10).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,10).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://snomed.info/sct\"," +
                                                    "\"code\": \"301998005\"," +
                                                    "\"display\": \"Ability to move\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Cara masuk IGD\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,9).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,10).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,10).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Cara Masuk IGD : "+e);
                        }
                    }

                    //Transportasi
                    if(!tbIGDSekunder.getValueAt(i,12).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,12).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11459-5\"," +
                                                    "\"display\": \"Transport mode EMS system\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alat transportasi kedatangan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,11).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,12).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,12).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Alat Transportasi : "+e);
                        }
                    }

                    //Alasan Kedatangan
                    if(!tbIGDSekunder.getValueAt(i,15).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,15).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"11293-8\"," +
                                                    "\"display\": \"Type of Referral source\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Alasan Kedatangan/Alasan Rujukan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,13).toString()+(tbIGDSekunder.getValueAt(i,14).toString().equals("")?"":", Keterangan : "+tbIGDSekunder.getValueAt(i,14).toString())+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,15).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,15).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Alasan Kedatangan : "+e);
                        }
                    }

                    //Macam Kasus
                    if(!tbIGDSekunder.getValueAt(i,17).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,17).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"29298-7\"," +
                                                    "\"display\": \"Reason for visit\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Macam Kasus\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \""+tbIGDSekunder.getValueAt(i,16).toString()+"\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,17).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,17).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Macam Kasus : "+e);
                        }
                    }

                    //Tekanan Darah
                    if(!tbIGDSekunder.getValueAt(i,19).toString().equals("")){
                        arrSplit = tbIGDSekunder.getValueAt(i,18).toString().split("/");
                        sistole="0";
                        try {
                            if(!arrSplit[0].equals("")){
                                sistole=arrSplit[0];
                            }
                        } catch (Exception e) {
                            sistole="0";
                        }
                        diastole="0";
                        try {
                            if(!arrSplit[1].equals("")){
                                diastole=arrSplit[1];
                            }
                        } catch (Exception e) {
                            diastole="0";
                        }
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,19).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"35094-2\"," +
                                                    "\"display\": \"Blood pressure panel\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Tekanan Darah\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"component\" : ["+
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8480-6\"," +
                                                            "\"display\" : \"Systolic blood pressure\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+sistole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}," +
                                            "{" +
                                                "\"code\" : {" +
                                                    "\"coding\" : ["+
                                                        "{" +
                                                            "\"system\" : \"http://loinc.org\"," +
                                                            "\"code\" : \"8462-4\"," +
                                                            "\"display\" : \"Diastolic blood pressure\"" +
                                                        "}"+
                                                    "]" +
                                                "}," +
                                                "\"valueQuantity\" : {" +
                                                    "\"value\" : "+diastole+"," +
                                                    "\"unit\" : \"mmHg\"," +
                                                    "\"system\" : \"http://unitsofmeasure.org\"," +
                                                    "\"code\" : \"mm[Hg]\"" +
                                                "}" +
                                            "}"+
                                        "]" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,19).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,19).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Tekanan Darah : "+e);
                        }
                    }

                    //Nadi
                    if(!tbIGDSekunder.getValueAt(i,21).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,21).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8867-4\"," +
                                                    "\"display\": \"Heart rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nadi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 20).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,21).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,21).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Nadi : "+e);
                        }
                    }

                    //Respirasi
                    if(!tbIGDSekunder.getValueAt(i,23).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,23).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"9279-1\"," +
                                                    "\"display\": \"Respiratory rate\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Respirasi\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 22).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"/min\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"/min\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,23).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,23).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Respirasi : "+e);
                        }
                    }

                    //Suhu Tubuh
                    if(!tbIGDSekunder.getValueAt(i,25).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,25).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"8310-5\"," +
                                                    "\"display\": \"Body temperature\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Suhu Tubuh\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 24).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"degree Celsius\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"Cel\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,25).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,25).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Suhu Tubuh : "+e);
                        }
                    }

                    //Saturasi Oksigen
                    if(!tbIGDSekunder.getValueAt(i,27).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,27).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"59408-5\"," +
                                                    "\"display\": \"Oxygen saturation in Arterial blood by Pulse oximetry\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Saturasi Oksigen\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueQuantity\": {"+
                                            "\"value\": " + tbIGDSekunder.getValueAt(i, 26).toString().replaceAll(",", ".") + ","+
                                            "\"unit\": \"%\","+
                                            "\"system\": \"http://unitsofmeasure.org\","+
                                            "\"code\": \"%\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,27).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,27).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Saturasi O²(%) : "+e);
                        }
                    }

                    //Nyeri
                    if(!tbIGDSekunder.getValueAt(i,29).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,29).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"vital-signs\"," +
                                                        "\"display\": \"Vital Signs\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"72514-3\"," +
                                                    "\"display\": \"Pain severity 0-10 Score Reported\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Nyeri\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueInteger\": " + tbIGDSekunder.getValueAt(i, 28).toString().replaceAll("[^0-9]", "") +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,29).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,29).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Nyeri : "+e);
                        }
                    }

                    //Anamnesa Singkat
                    if(!tbIGDSekunder.getValueAt(i,31).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"ClinicalImpression\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,31).toString()+"\"," +
                                        "\"status\": \"completed\","+
                                        "\"description\" : \""+tbIGDSekunder.getValueAt(i,30).toString().replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ") +"\"," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"assessor\": {" +
                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"date\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"prognosisCodeableConcept\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://terminology.kemkes.go.id/CodeSystem/clinical-term\","+
                                                        "\"code\": \"PR000001\","+
                                                        "\"display\": \"Prognosis\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]"+
                                   "}";
                            System.out.println("URL : "+link+"/ClinicalImpression/"+tbIGDSekunder.getValueAt(i,31).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/ClinicalImpression/"+tbIGDSekunder.getValueAt(i,31).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Anamnesa Singkat : "+e);
                        }
                    }

                    //Catatan
                    if((!tbIGDSekunder.getValueAt(i, 32).toString().equals("")) && (!tbIGDSekunder.getValueAt(i, 33).toString().equals(""))){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Observation\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,33).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"category\": [" +
                                            "{" +
                                                "\"coding\": [" +
                                                    "{" +
                                                        "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                        "\"code\": \"exam\"," +
                                                        "\"display\": \"exam\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]," +
                                        "\"code\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"48767-8\"," +
                                                    "\"display\": \"Annotation comment [Interpretation] Narrative\"" +
                                                "}" +
                                            "]," +
                                            "\"text\": \"Catatan\"" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"performer\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"valueString\": \"" +tbIGDSekunder.getValueAt(i, 32).toString().replace("\"", "'")+ "\"" +
                                   "}";
                            System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,33).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,33).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Catatan : "+e);
                        }
                    }

                    //Keputusan
                    if(!tbIGDSekunder.getValueAt(i, 35).toString().equals("")){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"CarePlan\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,35).toString()+"\"," +
                                        "\"status\": \"active\"," +
                                        "\"intent\": \"plan\"," +
                                        "\"title\": \"Rencana Penempatan Pasien IGD\"," +
                                        "\"description\": \"" + tbIGDSekunder.getValueAt(i, 34).toString()+"\"," +
                                        "\"category\": ["+
                                            "{"+
                                                "\"coding\": ["+
                                                    "{"+
                                                        "\"system\": \"http://snomed.info/sct\","+
                                                        "\"code\": \"771082000\","+
                                                        "\"display\": \"Acute medicine care plan\""+
                                                    "}"+
                                                "]"+
                                            "}"+
                                        "]," +
                                        "\"subject\": {"+
                                            "\"reference\": \"Patient/" + idpasien + "\""+
                                        "}," +
                                        "\"encounter\": {"+
                                            "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                        "}," +
                                        "\"author\": {"+
                                            "\"reference\": \"Practitioner/" + iddokter + "\""+
                                        "}," +
                                        "\"period\": {"+
                                            "\"start\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\""+
                                        "}" +
                                   "}";
                            System.out.println("URL : "+link+"/CarePlan/"+tbIGDSekunder.getValueAt(i,35).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/CarePlan/"+tbIGDSekunder.getValueAt(i,35).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                            System.out.println("Result JSON : "+json);
                        }catch(Exception e){
                            System.out.println("Notifikasi Keputusan : "+e);
                        }
                    }

                    //Triase Skala 3
                    cekada=0;
                    StringBuilder iyembuilder = new StringBuilder();
                    ps=koneksi.prepareStatement(
                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                        "inner join master_triase_skala3 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan "+
                        "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 "+
                        "where data_triase_igddetail_skala3.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                    );
                    try {
                        ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(!tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                try{
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                    json = "{" +
                                                "\"resourceType\": \"Observation\"," +
                                                "\"id\": \""+tbIGDSekunder.getValueAt(i,36).toString()+"\"," +
                                                "\"status\": \"final\"," +
                                                "\"category\": [" +
                                                    "{" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                "\"code\": \"exam\"," +
                                                                "\"display\": \"exam\"" +
                                                            "}" +
                                                        "]" +
                                                    "}" +
                                                "]," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"75910-0\"," +
                                                            "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"Skala Triase\"" +
                                                "}," +
                                                "\"subject\": {" +
                                                    "\"reference\": \"Patient/"+idpasien+"\"" +
                                                "}," +
                                                "\"performer\": [" +
                                                    "{" +
                                                        "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                    "}" +
                                                "]," +
                                                "\"encounter\": {" +
                                                    "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                "}," +
                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                "\"valueCodeableConcept\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"LA6114-8\"," +
                                                            "\"display\": \"3. Urgent\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"text\": \"3. Urgent\"" +
                                                "}" +
                                           "}";
                                    System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString());
                                    System.out.println("Request JSON : "+json);
                                    requestEntity = new HttpEntity(json,headers);
                                    json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                                    System.out.println("Result JSON : "+json);
                                }catch(Exception e){
                                    System.out.println("Notifikasi Triase Skala 3 : "+e);
                                }
                            }

                            rs.beforeFirst();
                            while(rs.next()){
                                ps2=koneksi.prepareStatement(
                                    "select data_triase_igddetail_skala3.kode_skala3,master_triase_skala3.pengkajian_skala3,data_triase_igddetail_skala3.id_observation_skala3 from master_triase_skala3 "+
                                    "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where master_triase_skala3.kode_pemeriksaan=? and "+
                                    "data_triase_igddetail_skala3.no_rawat=?"
                                );
                                try {
                                    ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                    ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        if(!rs2.getString("id_observation_skala3").equals("")){
                                            try{
                                                headers = new HttpHeaders();
                                                headers.setContentType(MediaType.APPLICATION_JSON);
                                                headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                json = "{" +
                                                            "\"resourceType\": \"Observation\"," +
                                                            "\"id\": \""+rs2.getString("id_observation_skala3")+"\"," +
                                                            "\"status\": \"final\"," +
                                                            "\"category\": ["+
                                                                "{"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                            "\"code\": \"exam\","+
                                                                            "\"display\": \"exam\""+
                                                                        "}"+
                                                                    "]"+
                                                                "}"+
                                                            "]," +
                                                            "\"code\": {"+
                                                                "\"coding\": ["+
                                                                    "{"+
                                                                        "\"system\": \"http://loinc.org\","+
                                                                        "\"code\": \"75321-0\","+
                                                                        "\"display\": \"Clinical finding\""+
                                                                    "}"+
                                                                "],"+
                                                                "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                            "}," +
                                                            "\"subject\": {"+
                                                                "\"reference\": \"Patient/" + idpasien + "\""+
                                                            "}," +
                                                            "\"performer\": ["+
                                                                "{"+
                                                                    "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                "}"+
                                                            "]," +
                                                            "\"encounter\": {"+
                                                                "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                            "}," +
                                                            "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                            "\"valueString\": \"" + rs2.getString("pengkajian_skala3").replace("\"", "'") + "\"" +
                                                       "}";
                                                System.out.println("URL : "+link+"/Observation/"+rs2.getString("id_observation_skala3"));
                                                System.out.println("Request JSON : "+json);
                                                requestEntity = new HttpEntity(json,headers);
                                                json=api.getRest().exchange(link+"/Observation/"+rs2.getString("id_observation_skala3"), HttpMethod.PUT, requestEntity, String.class).getBody();
                                                System.out.println("Result JSON : "+json);
                                                root = mapper.readTree(json);
                                                response = root.path("id");
                                                if(!response.asText().equals("")){
                                                    iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala3")).append("\"},");
                                                }
                                            }catch(Exception e){
                                                System.out.println("Notifikasi Triase Skala 3 : "+e);
                                            }
                                        }

                                        cekada++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }

                    //Triase Skala 4
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala4 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 "+
                            "where data_triase_igddetail_skala4.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(!tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"id\": \""+tbIGDSekunder.getValueAt(i,36).toString()+"\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA6115-5\"," +
                                                                "\"display\": \"4. Less Urgent\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"4. Less Urgent\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString());
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 4 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala4.kode_skala4,master_triase_skala4.pengkajian_skala4,data_triase_igddetail_skala4.id_observation_skala4 from master_triase_skala4 "+
                                        "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where master_triase_skala4.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala4.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(!rs2.getString("id_observation_skala4").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"id\": \""+rs2.getString("id_observation_skala4")+"\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala4").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation/"+rs2.getString("id_observation_skala4"));
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation/"+rs2.getString("id_observation_skala4"), HttpMethod.PUT, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala4")).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 4 : "+e);
                                                }
                                            }

                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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

                    //Triase Skala 5
                    if(cekada==0){
                        ps=koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan from master_triase_pemeriksaan "+
                            "inner join master_triase_skala5 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan "+
                            "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 "+
                            "where data_triase_igddetail_skala5.no_rawat=? group by master_triase_pemeriksaan.kode_pemeriksaan"
                        );
                        try {
                            ps.setString(1,tbIGDSekunder.getValueAt(i,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                if(!tbIGDSekunder.getValueAt(i, 36).toString().equals("")){
                                    try{
                                        headers = new HttpHeaders();
                                        headers.setContentType(MediaType.APPLICATION_JSON);
                                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                        json = "{" +
                                                    "\"resourceType\": \"Observation\"," +
                                                    "\"id\": \""+tbIGDSekunder.getValueAt(i,36).toString()+"\"," +
                                                    "\"status\": \"final\"," +
                                                    "\"category\": [" +
                                                        "{" +
                                                            "\"coding\": [" +
                                                                "{" +
                                                                    "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\"," +
                                                                    "\"code\": \"exam\"," +
                                                                    "\"display\": \"exam\"" +
                                                                "}" +
                                                            "]" +
                                                        "}" +
                                                    "]," +
                                                    "\"code\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"75910-0\"," +
                                                                "\"display\": \"Canadian triage and acuity scale [CTAS]\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"Skala Triase\"" +
                                                    "}," +
                                                    "\"subject\": {" +
                                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                                    "}," +
                                                    "\"performer\": [" +
                                                        "{" +
                                                            "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                                        "}" +
                                                    "]," +
                                                    "\"encounter\": {" +
                                                        "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                                    "}," +
                                                    "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                    "\"valueCodeableConcept\": {" +
                                                        "\"coding\": [" +
                                                            "{" +
                                                                "\"system\": \"http://loinc.org\"," +
                                                                "\"code\": \"LA10137-0\"," +
                                                                "\"display\": \"5. Non Urgent\"" +
                                                            "}" +
                                                        "]," +
                                                        "\"text\": \"5. Non Urgent\"" +
                                                    "}" +
                                               "}";
                                        System.out.println("URL : "+link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString());
                                        System.out.println("Request JSON : "+json);
                                        requestEntity = new HttpEntity(json,headers);
                                        json=api.getRest().exchange(link+"/Observation/"+tbIGDSekunder.getValueAt(i,36).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                                        System.out.println("Result JSON : "+json);
                                    }catch(Exception e){
                                        System.out.println("Notifikasi Triase Skala 5 : "+e);
                                    }
                                }

                                rs.beforeFirst();
                                while(rs.next()){
                                    ps2=koneksi.prepareStatement(
                                        "select data_triase_igddetail_skala5.kode_skala5,master_triase_skala5.pengkajian_skala5,data_triase_igddetail_skala5.id_observation_skala5 from master_triase_skala5 "+
                                        "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where master_triase_skala5.kode_pemeriksaan=? and "+
                                        "data_triase_igddetail_skala5.no_rawat=?"
                                    );
                                    try {
                                        ps2.setString(1,rs.getString("kode_pemeriksaan"));
                                        ps2.setString(2,tbIGDSekunder.getValueAt(i,1).toString());
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            if(!rs2.getString("id_observation_skala5").equals("")){
                                                try{
                                                    headers = new HttpHeaders();
                                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                                                    json = "{" +
                                                                "\"resourceType\": \"Observation\"," +
                                                                "\"id\": \""+rs2.getString("id_observation_skala5")+"\"," +
                                                                "\"status\": \"final\"," +
                                                                "\"category\": ["+
                                                                    "{"+
                                                                        "\"coding\": ["+
                                                                            "{"+
                                                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/observation-category\","+
                                                                                "\"code\": \"exam\","+
                                                                                "\"display\": \"exam\""+
                                                                            "}"+
                                                                        "]"+
                                                                    "}"+
                                                                "]," +
                                                                "\"code\": {"+
                                                                    "\"coding\": ["+
                                                                        "{"+
                                                                            "\"system\": \"http://loinc.org\","+
                                                                            "\"code\": \"75321-0\","+
                                                                            "\"display\": \"Clinical finding\""+
                                                                        "}"+
                                                                    "],"+
                                                                    "\"text\": \"" + rs.getString("nama_pemeriksaan") + "\""+
                                                                "}," +
                                                                "\"subject\": {"+
                                                                    "\"reference\": \"Patient/" + idpasien + "\""+
                                                                "}," +
                                                                "\"performer\": ["+
                                                                    "{"+
                                                                        "\"reference\": \"Practitioner/" + iddokter + "\""+
                                                                    "}"+
                                                                "]," +
                                                                "\"encounter\": {"+
                                                                    "\"reference\": \"Encounter/" + tbIGDSekunder.getValueAt(i, 5).toString() + "\""+
                                                                "}," +
                                                                "\"effectiveDateTime\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"issued\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                                                "\"valueString\": \"" + rs2.getString("pengkajian_skala5").replace("\"", "'") + "\"" +
                                                           "}";
                                                    System.out.println("URL : "+link+"/Observation/"+rs2.getString("id_observation_skala5"));
                                                    System.out.println("Request JSON : "+json);
                                                    requestEntity = new HttpEntity(json,headers);
                                                    json=api.getRest().exchange(link+"/Observation/"+rs2.getString("id_observation_skala5"), HttpMethod.PUT, requestEntity, String.class).getBody();
                                                    System.out.println("Result JSON : "+json);
                                                    root = mapper.readTree(json);
                                                    response = root.path("id");
                                                    if(!response.asText().equals("")){
                                                        iyembuilder.append("{\"reference\": \"Observation/").append(rs2.getString("id_observation_skala5")).append("\"},");
                                                    }
                                                }catch(Exception e){
                                                    System.out.println("Notifikasi Triase Skala 5 : "+e);
                                                }
                                            }

                                            cekada++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                }
                            }
                        }catch (Exception e) {
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

                    //Composition IGD Sekunder
                    if((!tbIGDSekunder.getValueAt(i,10).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,12).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,15).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,17).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,19).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,21).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,23).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,25).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,27).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,29).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,31).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,33).toString().equals(""))&&
                            (!tbIGDSekunder.getValueAt(i,35).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,36).toString().equals(""))&&(!tbIGDSekunder.getValueAt(i,37).toString().equals(""))){
                        try{
                            headers = new HttpHeaders();
                            headers.setContentType(MediaType.APPLICATION_JSON);
                            headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                            json = "{" +
                                        "\"resourceType\": \"Composition\"," +
                                        "\"id\": \""+tbIGDSekunder.getValueAt(i,37).toString()+"\"," +
                                        "\"status\": \"final\"," +
                                        "\"type\": {" +
                                            "\"coding\": [" +
                                                "{" +
                                                    "\"system\": \"http://loinc.org\"," +
                                                    "\"code\": \"75500-9\"," +
                                                    "\"display\": \"Triage note\"" +
                                                "}" +
                                            "]" +
                                        "}," +
                                        "\"subject\": {" +
                                            "\"reference\": \"Patient/"+idpasien+"\"" +
                                        "}," +
                                        "\"encounter\": {" +
                                            "\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"" +
                                        "}," +
                                        "\"author\": [" +
                                            "{" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "]," +
                                        "\"date\": \""+tbIGDSekunder.getValueAt(i,8).toString().replace(" ", "T")+"+07:00\"," +
                                        "\"title\": \"Data Triase IGD Sekunder\"," +
                                        "\"section\": [" +
                                            "{" +
                                                "\"title\": \"Identitas & Kedatangan\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11459-5\"," +
                                                            "\"display\": \"Mode of arrival\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,10).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,12).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,15).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,17).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Anamnesa\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"ClinicalImpression/"+tbIGDSekunder.getValueAt(i,31).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Tanda Vital\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"8716-3\"," +
                                                            "\"display\": \"Vital signs\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,19).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,21).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,23).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,25).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,27).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Pengkajian & Skala/Kategori Triase\"," +
                                                "\"code\": {" +
                                                    "\"coding\": [" +
                                                        "{" +
                                                            "\"system\": \"http://loinc.org\"," +
                                                            "\"code\": \"11283-9\"," +
                                                            "\"display\": \"Acuity assessment\"" +
                                                        "}" +
                                                    "]" +
                                                "}," +
                                                "\"entry\": [" +
                                                    iyembuilder.toString()+
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,29).toString()+"\"}," +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,36).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Catatan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"Observation/"+tbIGDSekunder.getValueAt(i,33).toString()+"\"}" +
                                                "]" +
                                            "}," +
                                            "{" +
                                                "\"title\": \"Plan/Keputusan\"," +
                                                "\"entry\": [" +
                                                    "{\"reference\": \"CarePlan/"+tbIGDSekunder.getValueAt(i,35).toString()+"\"}," +
                                                    "{\"reference\": \"Encounter/"+tbIGDSekunder.getValueAt(i,5).toString()+"\"}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}";
                            System.out.println("URL : "+link+"/Composition/"+tbIGDSekunder.getValueAt(i,37).toString());
                            System.out.println("Request JSON : "+json);
                            requestEntity = new HttpEntity(json,headers);
                            json=api.getRest().exchange(link+"/Composition/"+tbIGDSekunder.getValueAt(i,37).toString(),HttpMethod.PUT,requestEntity,String.class).getBody();
                            System.out.println("Result JSON : "+json);
                            tbIGDSekunder.setValueAt(false,i,0);
                        }catch(Exception e){
                            System.out.println("Notifikasi Composition Triase IGD Sekunder : "+e);
                        }
                    }
                    iyembuilder=null;
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
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

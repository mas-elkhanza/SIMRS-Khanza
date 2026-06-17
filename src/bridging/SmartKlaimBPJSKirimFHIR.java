/*
  By Mas Elkhanza
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import rekammedis.RMRiwayatPerawatan;

/**
 *
 * @author dosen
 */
public final class SmartKlaimBPJSKirimFHIR extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;   
    private int i=0;
    private String link="",requestJson="",json="",idpasien="",utc="";
    private ApiBPJSSmartClaim api=new ApiBPJSSmartClaim();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;    
    private JsonNode nameNode;
    private StringBuilder htmlContent; 
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private RMRiwayatPerawatan resume;
    
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public SmartKlaimBPJSKirimFHIR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Alamat","No.KTP","No.BPJS","Tgl.Lahir","J.K.","No.Telp","Tgl.SEP","Jenis","Tgl.Kirim",
                "Kode Dokter","Nama Dokter","Kode Poli","Nama Poli","Tgl.Pulang","No.Rujukan","ICD 10","Kode Snomed","Display","Stts Nikah",
                "Kabupaten","Kode Kec","Kecamatan"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(105);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(65);
            }else if(i==18){
                column.setPreferredWidth(110);
            }else if(i==19){
                column.setPreferredWidth(50);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        JSONFHIR.setDocument(new batasInput((int)1000000).getKata(JSONFHIR));
        
        try {
            link=koneksiDB.URLAPISMARTCLAIM();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
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
        WindowFHIR = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnCloseIn5 = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        JSONFHIR = new widget.TextArea();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        chkPatient = new widget.CekBox();
        chkOrganization = new widget.CekBox();
        chkPractioner = new widget.CekBox();
        chkEncounter = new widget.CekBox();
        chkMedicationRequest = new widget.CekBox();
        chkCondition = new widget.CekBox();
        chkProcedure = new widget.CekBox();
        chkDevice = new widget.CekBox();
        chkDiagnosticReport = new widget.CekBox();
        chkComposition = new widget.CekBox();
        DTPTanggal = new widget.Tanggal();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel39 = new widget.Label();
        CmbStatus = new widget.ComboBox();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKirim = new widget.Button();
        BtnRiwayat = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel40 = new widget.Label();
        CmbJenis = new widget.ComboBox();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        WindowFHIR.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowFHIR.setName("WindowFHIR"); // NOI18N
        WindowFHIR.setUndecorated(true);
        WindowFHIR.setResizable(false);
        WindowFHIR.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kirim Data FHIR ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+U");
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

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCloseIn5);

        internalFrame6.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "JSON FHIR :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll3.setName("Scroll3"); // NOI18N

        JSONFHIR.setColumns(20);
        JSONFHIR.setRows(5);
        JSONFHIR.setName("JSONFHIR"); // NOI18N
        Scroll3.setViewportView(JSONFHIR);

        internalFrame6.add(Scroll3, java.awt.BorderLayout.CENTER);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(130, 100));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(130, 240));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        chkPatient.setSelected(true);
        chkPatient.setText("Patient");
        chkPatient.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPatient.setName("chkPatient"); // NOI18N
        chkPatient.setOpaque(false);
        chkPatient.setPreferredSize(new java.awt.Dimension(125, 22));
        chkPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkPatient);

        chkOrganization.setSelected(true);
        chkOrganization.setText("Organization");
        chkOrganization.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOrganization.setName("chkOrganization"); // NOI18N
        chkOrganization.setOpaque(false);
        chkOrganization.setPreferredSize(new java.awt.Dimension(125, 22));
        chkOrganization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkOrganization);

        chkPractioner.setSelected(true);
        chkPractioner.setText("Practitioner");
        chkPractioner.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPractioner.setName("chkPractioner"); // NOI18N
        chkPractioner.setOpaque(false);
        chkPractioner.setPreferredSize(new java.awt.Dimension(125, 22));
        chkPractioner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkPractioner);

        chkEncounter.setSelected(true);
        chkEncounter.setText("Encounter");
        chkEncounter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkEncounter.setName("chkEncounter"); // NOI18N
        chkEncounter.setOpaque(false);
        chkEncounter.setPreferredSize(new java.awt.Dimension(125, 22));
        chkEncounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkEncounter);

        chkMedicationRequest.setSelected(true);
        chkMedicationRequest.setText("MedicationRequest");
        chkMedicationRequest.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMedicationRequest.setName("chkMedicationRequest"); // NOI18N
        chkMedicationRequest.setOpaque(false);
        chkMedicationRequest.setPreferredSize(new java.awt.Dimension(125, 22));
        chkMedicationRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkMedicationRequest);

        chkCondition.setSelected(true);
        chkCondition.setText("Condition");
        chkCondition.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCondition.setName("chkCondition"); // NOI18N
        chkCondition.setOpaque(false);
        chkCondition.setPreferredSize(new java.awt.Dimension(125, 22));
        chkCondition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkCondition);

        chkProcedure.setSelected(true);
        chkProcedure.setText("Procedure");
        chkProcedure.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkProcedure.setName("chkProcedure"); // NOI18N
        chkProcedure.setOpaque(false);
        chkProcedure.setPreferredSize(new java.awt.Dimension(125, 22));
        chkProcedure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkProcedure);

        chkDevice.setSelected(true);
        chkDevice.setText("Device");
        chkDevice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkDevice.setName("chkDevice"); // NOI18N
        chkDevice.setOpaque(false);
        chkDevice.setPreferredSize(new java.awt.Dimension(125, 22));
        chkDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkDevice);

        chkDiagnosticReport.setSelected(true);
        chkDiagnosticReport.setText("DiagnosticReport");
        chkDiagnosticReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkDiagnosticReport.setName("chkDiagnosticReport"); // NOI18N
        chkDiagnosticReport.setOpaque(false);
        chkDiagnosticReport.setPreferredSize(new java.awt.Dimension(125, 22));
        chkDiagnosticReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkDiagnosticReport);

        chkComposition.setSelected(true);
        chkComposition.setText("Composition");
        chkComposition.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkComposition.setName("chkComposition"); // NOI18N
        chkComposition.setOpaque(false);
        chkComposition.setPreferredSize(new java.awt.Dimension(125, 22));
        chkComposition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        FormMenu.add(chkComposition);

        ScrollMenu.setViewportView(FormMenu);

        internalFrame6.add(ScrollMenu, java.awt.BorderLayout.WEST);

        WindowFHIR.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DTPTanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-05-2026 09:12:59" }));
        DTPTanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPTanggal.setName("DTPTanggal"); // NOI18N
        DTPTanggal.setOpaque(false);
        DTPTanggal.setPreferredSize(new java.awt.Dimension(90, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Data FHIR Smart Klaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setName("tbObat"); // NOI18N
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel39.setText("Status Kirim :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass8.add(jLabel39);

        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah", "Belum" }));
        CmbStatus.setName("CmbStatus"); // NOI18N
        CmbStatus.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass8.add(CmbStatus);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass8.add(LCount);

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

        BtnRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2276087_document_extension_format_jpg_paper_icon.png"))); // NOI18N
        BtnRiwayat.setMnemonic('U');
        BtnRiwayat.setText("Riwayat");
        BtnRiwayat.setToolTipText("Alt+U");
        BtnRiwayat.setName("BtnRiwayat"); // NOI18N
        BtnRiwayat.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnRiwayat);

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

        jLabel15.setText("Tgl.SEP :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(51, 23));
        panelGlass9.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-05-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d.");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-05-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel40.setText("Jenis :");
        jLabel40.setName("jLabel40"); // NOI18N
        jLabel40.setPreferredSize(new java.awt.Dimension(44, 23));
        panelGlass9.add(jLabel40);

        CmbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Ranap", "Ralan" }));
        CmbJenis.setName("CmbJenis"); // NOI18N
        CmbJenis.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(CmbJenis);

        jLabel16.setText("Key Word :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowFHIR.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.SEP</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.BPJS</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Telp</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.SEP</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Kirim</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Poli</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Poli</b></td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pulang</b></td>"+
                "</tr>"
            );
            for (i = 0; i < tabMode.getRowCount(); i++) {
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                        "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                    "</tr>");
            }
            LoadHTML.setText(
                "<html>"+
                  "<table width='1600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

            File f = new File("DataSmartKlaimBPJS.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='1600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                    "<font size='2' face='Tahoma'>DATA PENGIRIMAN FHIR SMART KLAIM BPJS<br><br></font>"+        
                                "</td>"+
                           "</tr>"+
                        "</table>")
            );
            bw.close();                         
            Desktop.getDesktop().browse(f.toURI());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
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
            tbObat.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        runBackground(() ->tampil());
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
        if(tabMode.getRowCount()!=0){
            try{
                if(tbObat.getSelectedRow()!= -1){
                    if(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Display snomed masih kosong, silakan mapping terlebih dahulu...!!");
                    }else{
                        DTPTanggal.setDate(new Date());
                        StringBuilder iyembuilder = new StringBuilder();
                        if(chkPatient.isSelected()==true){
                            iyembuilder.append("{").
                                            append("\"resource\": {").
                                                append("\"resourceType\": \"Patient\",").
                                                append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                append("\"identifier\": [").
                                                    append("{").
                                                        append("\"use\": \"usual\",").
                                                        append("\"type\": {\"coding\": [{\"system\": \"http://hl7.org/fhir/v2/0203\", \"code\": \"MR\"}]},").
                                                        append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()).append("\",").
                                                        append("\"assigner\": {\"display\": \"").append(akses.getnamars()).append("\"}").
                                                    append("},").
                                                    append("{").
                                                        append("\"use\": \"official\",").
                                                        append("\"type\": {\"coding\": [{\"system\": \"http://hl7.org/fhir/v2/0203\", \"code\": \"MB\"}]},").
                                                        append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString()).append("\",").
                                                        append("\"assigner\": {\"display\": \"BPJS KESEHATAN\"}").
                                                    append("},").
                                                    append("{").
                                                        append("\"use\": \"official\",").
                                                        append("\"type\": {\"coding\": [{\"system\": \"http://hl7.org/fhir/v2/0203\", \"code\": \"NNIDN\"}]},").
                                                        append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()).append("\",").
                                                        append("\"assigner\": {\"display\": \"KEMENDAGRI\"}").
                                                    append("}").
                                                append("],").
                                                append("\"active\": true,").
                                                append("\"name\": [{\"use\": \"official\", \"text\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\"}],").
                                                append("\"maritalStatus\": {").
                                                    append("\"coding\": [{").
                                                        append("\"system\": \"http://terminology.hl7.org/CodeSystem/v3-MaritalStatus\",").
                                                        append("\"code\": \"").append((tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().equals("MENIKAH")?"M":(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().equals("BELUM MENIKAH")?"S":"U"))).append("\"").
                                                    append("}]").
                                                append("},").
                                                append("\"telecom\": [").
                                                    append("{").
                                                        append("\"system\": \"phone\",").
                                                        append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()).append("\",").
                                                        append("\"use\": \"mobile\"").
                                                    append("}").
                                                append("],").
                                                append("\"gender\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().replaceAll("L","male").replaceAll("P","female")).append("\",").
                                                append("\"birthDate\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()).append("\",").
                                                append("\"deceasedBoolean\":false,").
                                                append("\"address\": [{").
                                                    append("\"line\": [\"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()).append("\"],").
                                                    append("\"city\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString()).append("\",").
                                                    append("\"district\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString()).append("\",").
                                                    append("\"state\": \"").append(akses.getpropinsirs()).append("\",").
                                                    append("\"postalCode\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString()).append("\",").
                                                    append("\"text\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()).append("\",").
                                                    append("\"use\": \"home\",").
                                                    append("\"type\": \"both\"").
                                                append("}],").
                                                append("\"managingOrganization\": {").
                                                    append("\"reference\": \"Organization/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(akses.getkodeppkbpjs() + akses.getnamars())).append("\",").
                                                    append("\"display\": \"").append(akses.getnamars()).append("\"").
                                                append("}").
                                            append("}").
                                        append("},");
                        }

                        if(chkOrganization.isSelected()==true){
                            iyembuilder.append("{").
                                            append("\"resource\": {").
                                                append("\"resourceType\": \"Organization\",").
                                                append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(akses.getkodeppkbpjs()+akses.getnamars())).append("\",").
                                                append("\"identifier\": [").
                                                    append("{").
                                                        append("\"use\": \"official\",").
                                                        append("\"system\": \"urn:oid:bpjs\",").
                                                        append("\"value\": \"").append(akses.getkodeppkbpjs()).append("\"").
                                                    append("},").
                                                    append("{").
                                                        append("\"use\": \"official\",").
                                                        append("\"system\": \"urn:oid:kemkes\",").
                                                        append("\"value\": \"").append(akses.getkodeppkkemenkes()).append("\"").
                                                    append("}").
                                                append("],").
                                                append("\"type\": [{").
                                                    append("\"coding\": [").
                                                        append("{").
                                                            append("\"system\": \"http://hl7.org/fhir/organization-type\",").
                                                            append("\"code\": \"prov\",").
                                                            append("\"display\": \"Healthcare Provider\"").
                                                        append("}").
                                                    append("],").
                                                    append("\"text\": \"").append(akses.getnamars()).append("\"").
                                                append("}],").
                                                append("\"name\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()).append("\",").
                                                append("\"alias\": [").
                                                    append("\"").append(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()).append("\"").
                                                append("],").
                                                append("\"telecom\": [{").
                                                        append("\"system\": \"phone\",").
                                                        append("\"value\": \"").append(akses.getkontakrs()).append("\",").
                                                        append("\"use\": \"work\"").
                                                append("}],").
                                                append("\"address\": [{").
                                                    append("\"use\": \"work\",").
                                                    append("\"type\": \"physical\",").
                                                    append("\"text\": \"").append(akses.getalamatrs()).append("\",").
                                                    append("\"line\": [").
                                                        append("\"").append(akses.getalamatrs()).append("\"").
                                                    append("],").
                                                    append("\"city\": \"").append(akses.getkabupatenrs()).append("\",").
                                                    append("\"state\": \"").append(akses.getpropinsirs()).append("\",").
                                                    append("\"postalCode\": \"").append(koneksiDB.KODEPOSSATUSEHAT()).append("\",").
                                                    append("\"country\": \"IDN\"").
                                                append("}],").
                                                append("\"contact\": [{").
                                                    append("\"purpose\": {").
                                                        append("\"coding\": [").
                                                            append("{").
                                                                append("\"system\": \"http://terminology.hl7.org/CodeSystem/contactentity-type\",").
                                                                append("\"code\": \"PATINF\",").
                                                                append("\"display\": \"Patient Information\"").
                                                            append("}").
                                                        append("]").
                                                    append("},").
                                                    append("\"telecom\": [").
                                                        append("{").
                                                            append("\"system\": \"phone\",").
                                                            append("\"value\": \"").append(akses.getkontakrs()).append("\"").
                                                        append("}").
                                                    append("]").
                                                append("}]").
                                            append("}").
                                        append("},");
                        }

                        if(chkPractioner.isSelected()==true){
                            ps=koneksi.prepareStatement(
                                "SELECT dokter.no_ijn_praktek,pegawai.no_ktp,dokter.no_telp,dokter.jk,dokter.tgl_lahir,pegawai.alamat,pegawai.kota,dokter.email "+
                                "FROM reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter inner JOIN pegawai ON dokter.kd_dokter=pegawai.nik "+
                                "WHERE reg_periksa.no_rawat=?"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"Practitioner\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())).append("\",").
                                                        append("\"identifier\": [").
                                                            append("{").
                                                                append("\"use\": \"official\",").
                                                                append("\"system\": \"urn:oid:nomor_sip\",").
                                                                append("\"value\": \"").append(rs.getString("no_ijn_praktek")).append("\"").
                                                            append("},").
                                                            append("{").
                                                                append("\"use\": \"official\",").
                                                                append("\"type\": {").
                                                                    append("\"coding\": [{").
                                                                        append("\"system\": \"http://hl7.org/fhir/v2/0203\",").
                                                                        append("\"code\": \"NNIDN\"").
                                                                    append("}]").
                                                                append("},").
                                                                append("\"value\": \"").append(rs.getString("no_ktp")).append("\",").
                                                                append("\"assigner\": {").
                                                                    append("\"display\": \"KEMDAGRI\"").
                                                                append("}").
                                                            append("}").
                                                        append("],").
                                                        append("\"name\": [{").
                                                            append("\"use\": \"official\",").
                                                            append("\"text\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()).append("\"").
                                                        append("}],").
                                                        append("\"telecom\": [").
                                                            append("{").
                                                                append("\"system\": \"phone\",").
                                                                append("\"value\": \"").append(rs.getString("no_telp")).append("\",").
                                                                append("\"use\": \"work\"").
                                                            append("},").
                                                            append("{").
                                                                append("\"system\": \"email\",").
                                                                append("\"value\": \"").append(rs.getString("email")).append("\",").
                                                                append("\"use\": \"work\"").
                                                            append("},").
                                                            append("{").
                                                                append("\"system\": \"fax\",").
                                                                append("\"value\": \"").append(akses.getkontakrs()).append("\",").
                                                                append("\"use\": \"work\"").
                                                            append("},").
                                                            append("{").
                                                                append("\"system\": \"home\",").
                                                                append("\"value\": \"").append(rs.getString("alamat")).append("\",").
                                                                append("\"use\": \"home\"").
                                                            append("}").
                                                        append("],").
                                                        append("\"address\": [{").
                                                            append("\"use\": \"work\",").
                                                            append("\"type\": \"physical\",").
                                                            append("\"text\": \"-\",").
                                                            append("\"line\": [").
                                                                append("\"").append(rs.getString("alamat")).append("\"").
                                                            append("],").
                                                            append("\"city\": \"").append(rs.getString("kota")).append("\",").
                                                            append("\"district\": \"").append(rs.getString("kota")).append("\",").
                                                            append("\"state\": \"").append(akses.getpropinsirs()).append("\",").
                                                            append("\"postalCode\": \"").append("1234567").append("\",").
                                                            append("\"country\": \"IDN\"").
                                                        append("}],").
                                                        append("\"gender\": \"").append(rs.getString("jk").replaceAll("L","male").replaceAll("P","female")).append("\",").
                                                        append("\"birthDate\": \"").append(rs.getString("tgl_lahir")).append("\"").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Praktisi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }

                        if(chkEncounter.isSelected()==true){
                            String norujukaninternal=Sequel.cariIsi("SELECT bridging_sep_internal.no_rujukan FROM bridging_sep_internal WHERE bridging_sep_internal.no_rawat = ? LIMIT 1",tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
                            iyembuilder.append("{").
                                            append("\"resource\": {").
                                                append("\"resourceType\": \"Encounter\",").
                                                append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\",").
                                                append("\"identifier\": [{").
                                                    append("\"system\": \"http://api.bpjs-kesehatan.go.id:8080/Vclaim-rest/SEP/\",").
                                                    append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()).append("\"").
                                                append("}],").
                                                append("\"subject\": {").
                                                    append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                    append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\",").
                                                    append("\"noSep\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()).append("\"").
                                                append("},").
                                                append("\"class\": {").
                                                    append("\"system\": \"http://terminology.hl7.org/CodeSystem/v3-ActCode\",").
                                                    append("\"code\": \"").append((tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().equals("2. Ralan") ? "AMB" : "IMP")).append("\",").
                                                    append("\"display\": \"").append((tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().equals("2. Ralan") ? "ambulatory" : "inpatient encounter")).append("\"").
                                                append("},").
                                                append("\"incomingReferral\": [{").
                                                    append("\"identifier\": [").
                                                        append("{").
                                                            append("\"system\": \"http://sys-ids.kemkes.go.id/no-rujukan-bpjs\",").
                                                            append("\"value\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString().equals("")? "Tidak Ada Nomor Rujukan" : tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString()).append("\"").
                                                        append("},").
                                                        append("{").
                                                            append("\"system\": \"http://sys-ids.kemkes.go.id/no-rujukan-internal\",").
                                                            append("\"value\": \"").append(norujukaninternal.equals("") ? "Tidak Ada Nomor Rujukan" : norujukaninternal).append("\"").
                                                        append("}").
                                                    append("]").
                                                append("}],").
                                                append("\"reason\": [{").
                                                    append("\"coding\": [{").
                                                        append("\"code\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()).append("\",").
                                                        append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()).append("\",").
                                                        append("\"system\": \"http://snomed.info/sct\"").
                                                    append("}],").
                                                    append("\"text\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()).append("\"").
                                                append("}],").
                                                append("\"diagnosis\": [{").
                                                    append("\"condition\": {").
                                                        append("\"reference\": \"Condition/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()+Sequel.cariIsi("SELECT diagnosa_pasien.kd_penyakit FROM diagnosa_pasien WHERE diagnosa_pasien.no_rawat = ? order by diagnosa_pasien.prioritas limit 1",tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()))).append("\",").
                                                        append("\"role\": {").
                                                            append("\"coding\": [{").
                                                                append("\"system\": \"http://terminology.hl7.org/CodeSystem/diagnosis-role\",").
                                                                append("\"code\": \"DD\",").
                                                                append("\"display\": \"Discharge diagnosis\"").
                                                            append("}]").
                                                        append("},").
                                                        append("\"rank\": ").append("1").
                                                    append("}").
                                                append("}],").
                                                append("\"hospitalization\": {").
                                                    append("\"dischargeDisposition\": [{").
                                                        append("\"coding\": [{").
                                                            append("\"system\": \"http://terminology.hl7.org/CodeSystem/discharge-disposition\",").
                                                            append("\"code\": \"home\",").
                                                            append("\"display\": \"Home\"").
                                                        append("}]").
                                                    append("}]").
                                                append("},").
                                                append("\"period\": {").
                                                    append("\"start\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()).append("\",").
                                                    append("\"end\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()).append("\"").
                                                append("},").
                                                append("\"status\": \"finished\",").
                                                append("\"text\": {").
                                                    append("\"div\": \"Admitted to ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString()).append(" ").append(akses.getnamars()).append(" between ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()).append(" and ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()).append("\",").
                                                    append("\"status\": \"generated\"").
                                                append("}").
                                            append("}").
                                        append("},");
                        }
                        
                        if(chkCondition.isSelected()==true){
                            ps=koneksi.prepareStatement(
                                "SELECT diagnosa_pasien.no_rawat,diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit FROM diagnosa_pasien "+
                                "INNER JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE diagnosa_pasien.no_rawat = ? "+
                                "ORDER BY diagnosa_pasien.prioritas"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"Condition\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()+rs.getString("kd_penyakit"))).append("\",").
                                                        append("\"clinicalStatus\": \"active\",").
                                                        append("\"verificationStatus\": \"confirmed\",").
                                                        append("\"category\": [{").
                                                            append("\"coding\": [{").
                                                                append("\"system\": \"http://hl7.org/fhir/condition-category\",").
                                                                append("\"code\": \"encounter-diagnosis\",").
                                                                append("\"display\": \"Encounter Diagnosis\"").
                                                            append("}]").
                                                        append("}],").
                                                        append("\"code\": {").
                                                            append("\"coding\": [{").
                                                                append("\"system\": \"http://hl7.org/fhir/sid/icd-10\",").
                                                                append("\"code\": \"").append(rs.getString("kd_penyakit")).append("\",").
                                                                append("\"display\": \"").append(rs.getString("nm_penyakit")).append("\"").
                                                            append("}],").
                                                            append("\"text\": \"").append(rs.getString("nm_penyakit")).append("\"").
                                                        append("},").
                                                        append("\"subject\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\"").
                                                        append("},").
                                                        append("\"onsetDateTime\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()).append("\"").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Condition : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }
                            
                        String jalannyapenyakit="";
                        if (chkComposition.isSelected() == true) {
                            if(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("1. Ranap")){
                                ps=koneksi.prepareStatement(
                                    "SELECT resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.pemeriksaan_fisik,"+
                                    "resume_pasien_ranap.jalannya_penyakit,resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.tindakan_dan_operasi,"+
                                    "resume_pasien_ranap.obat_di_rs,resume_pasien_ranap.alergi,resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,"+
                                    "resume_pasien_ranap.cara_keluar,resume_pasien_ranap.ket_keluar,resume_pasien_ranap.keadaan,resume_pasien_ranap.ket_keadaan,resume_pasien_ranap.dilanjutkan,"+
                                    "resume_pasien_ranap.ket_dilanjutkan,resume_pasien_ranap.kontrol,resume_pasien_ranap.obat_pulang,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                                    "FROM resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat where resume_pasien_ranap.no_rawat=?"
                                );
                                try {
                                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                    rs=ps.executeQuery();
                                    if(rs.next()){
                                        iyembuilder.append("{").
                                                        append("\"resource\": {").
                                                            append("\"resourceType\": \"Composition\",").
                                                            append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()+tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString())).append("\",").
                                                            append("\"status\": \"final\",").
                                                            append("\"type\": {").
                                                                append("\"coding\": [{").
                                                                    append("\"system\": \"http://loinc.org\",").
                                                                    append("\"code\": \"81218-0\"").
                                                                append("}],").
                                                                append("\"text\": \"Discharge Summary\"").
                                                            append("},").
                                                            append("\"subject\": {").
                                                                append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\"").
                                                            append("},").
                                                            append("\"encounter\": {").
                                                                append("\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"").
                                                            append("},").
                                                            append("\"date\": \"").append(rs.getString("tgl_registrasi")).append(" ").append(rs.getString("jam_reg")).append("\",").
                                                            append("\"author\": [{").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()).append("\"").
                                                            append("}],").
                                                            append("\"title\": \"Discharge Summary\",").
                                                            append("\"confidentiality\": \"N\",").
                                                            append("\"section\": {").
                                                                append("\"0\": {").
                                                                    append("\"title\": \"Reason for admission\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"29299-5\",").
                                                                        append("\"display\": \"Reason for visit Narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("alasan")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"1\": {").
                                                                    append("\"title\": \"Admission diagnosis\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"42347-5\",").
                                                                        append("\"display\": \"Admission diagnosis Narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("diagnosa_awal")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"2\": {").
                                                                    append("\"title\": \"Chief complaint\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"10154-3\",").
                                                                        append("\"display\": \"Chief complaint Narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"3\": {").
                                                                    append("\"title\": \"Physical findings\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"29545-1\",").
                                                                        append("\"display\": \"Physical findings\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("pemeriksaan_fisik").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"4\": {").
                                                                    append("\"title\": \"Clinical course\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"8648-8\",").
                                                                        append("\"display\": \"Hospital course in medical records narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"5\": {").
                                                                    append("\"title\": \"Relevant diagnostic tests\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"30954-2\",").
                                                                        append("\"display\": \"Relevant diagnostic tests/lab data\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append("Radiologi: ").append(rs.getString("pemeriksaan_penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append(". Lab: ").append(rs.getString("hasil_laborat").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append(". Pending : ").append(rs.getString("lab_belum").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"6\": {").
                                                                    append("\"title\": \"History of procedures\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"47519-4\",").
                                                                        append("\"display\": \"History of Procedures\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("tindakan_dan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"7\": {").
                                                                    append("\"title\": \"History of medication use\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"10160-0\",").
                                                                        append("\"display\": \"History of medication use\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("obat_di_rs").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"8\": {").
                                                                    append("\"title\": \"Medications on Discharge\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"75311-1\",").
                                                                        append("\"display\": \"Hospital discharge medications Narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("obat_pulang").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"9\": {").
                                                                    append("\"title\": \"Known allergies\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"48765-2\",").
                                                                        append("\"display\": \"Allergies and adverse reactions\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("alergi")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"10\": {").
                                                                    append("\"title\": \"Diet\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"42344-2\",").
                                                                        append("\"display\": \"Discharge diet\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("diet").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"11\": {").
                                                                    append("\"title\": \"Educations & Instructions\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"69730-0\",").
                                                                        append("\"display\": \"Instructions at discharge\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"12\": {").
                                                                    append("\"title\": \"Discharge condition\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"89243-0\",").
                                                                        append("\"display\": \"Discharge condition\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("keadaan")+" - "+rs.getString("ket_keadaan")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"13\": {").
                                                                    append("\"title\": \"Plan of care\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"18776-5\",").
                                                                        append("\"display\": \"Plan of care\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append("Cara Keluar : ").append(rs.getString("cara_keluar")).append(" - ").append(rs.getString("ket_keluar")).append(". Dilanjutkan : ").append(rs.getString("dilanjutkan")).append(" - ").append(rs.getString("ket_dilanjutkan")).append(". Kontrol : ").append(rs.getString("kontrol")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("}"). 
                                                            append("}").
                                                        append("}").
                                                    append("},");
                                        jalannyapenyakit=rs.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ");
                                    }
                                } catch (Exception e) {
                                     System.out.println("Notif Resume Rawat Inap : "+e);
                                } finally{
                                    if(rs!=null){
                                        rs.close();
                                    }
                                    if(ps!=null){
                                        ps.close();
                                    }
                                }
                            }else{
                                ps=koneksi.prepareStatement(
                                    "SELECT resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit,resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,"+
                                    "resume_pasien.kondisi_pulang,resume_pasien.obat_pulang,reg_periksa.tgl_registrasi,reg_periksa.jam_reg FROM resume_pasien "+
                                    "inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat where resume_pasien.no_rawat=?"
                                );
                                try {
                                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                    rs=ps.executeQuery();
                                    if(rs.next()){
                                        iyembuilder.append("{").
                                                        append("\"resource\": {").
                                                            append("\"resourceType\": \"Composition\",").
                                                            append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()+tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString())).append("\",").
                                                            append("\"status\": \"final\",").
                                                            append("\"type\": {").
                                                                append("\"coding\": [{").
                                                                    append("\"system\": \"http://loinc.org\",").
                                                                    append("\"code\": \"81218-0\"").
                                                                append("}],").
                                                                append("\"text\": \"Discharge Summary\"").
                                                            append("},").
                                                            append("\"subject\": {").
                                                                append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\"").
                                                            append("},").
                                                            append("\"encounter\": {").
                                                                append("\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"").
                                                            append("},").
                                                            append("\"date\": \"").append(rs.getString("tgl_registrasi")).append(" ").append(rs.getString("jam_reg")).append("\",").
                                                            append("\"author\": [{").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()).append("\"").
                                                            append("}],").
                                                            append("\"title\": \"Discharge Summary\",").
                                                            append("\"confidentiality\": \"N\",").
                                                            append("\"section\": {").
                                                                append("\"0\": {").
                                                                    append("\"title\": \"Chief complaint\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"10154-3\",").
                                                                        append("\"display\": \"Chief complaint Narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"1\": {").
                                                                    append("\"title\": \"Clinical course\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"8648-8\",").
                                                                        append("\"display\": \"Hospital course in medical records narrative\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"2\": {").
                                                                    append("\"title\": \"Relevant diagnostic tests\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"30954-2\",").
                                                                        append("\"display\": \"Relevant diagnostic tests/lab data\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append("Radiologi : ").append(rs.getString("pemeriksaan_penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append(". Lab : ").append(rs.getString("hasil_laborat").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"3\": {").
                                                                    append("\"title\": \"Discharge counseling note\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"74209-6\",").
                                                                        append("\"display\": \"Discharge counseling note\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("obat_pulang").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("},").
                                                                append("\"4\": {").
                                                                    append("\"title\": \"Discharge condition\",").
                                                                    append("\"code\": {\"coding\": [{").
                                                                        append("\"system\": \"http://loinc.org\",").
                                                                        append("\"code\": \"89243-0\",").
                                                                        append("\"display\": \"Discharge condition\"").
                                                                    append("}]},").
                                                                    append("\"text\": {\"status\": \"additional\",\"div\":\"").append(rs.getString("kondisi_pulang")).append("\"},").
                                                                    append("\"mode\": \"working\",").
                                                                    append("\"entry\": [{\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\"}]").
                                                                append("}").
                                                            append("}").
                                                        append("}").
                                                    append("},");
                                        jalannyapenyakit=rs.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>").replaceAll("\t", " ");
                                    }
                                } catch (Exception e) {
                                     System.out.println("Notif Resume Rawat Jalan : "+e);
                                } finally{
                                    if(rs!=null){
                                        rs.close();
                                    }
                                    if(ps!=null){
                                        ps.close();
                                    }
                                }
                            }
                        }

                        if (chkProcedure.isSelected() == true) {
                            ps=koneksi.prepareStatement(
                                "SELECT prosedur_pasien.kode,icd9.deskripsi_panjang,mapping_prosedur_smart_klaim_bpjs.kode_snomed,mapping_prosedur_smart_klaim_bpjs.display,"+
                                "mapping_prosedur_smart_klaim_bpjs.system FROM prosedur_pasien INNER JOIN icd9 ON prosedur_pasien.kode = icd9.kode "+
                                "INNER JOIN mapping_prosedur_smart_klaim_bpjs ON prosedur_pasien.kode = mapping_prosedur_smart_klaim_bpjs.icd9 " +
                                "WHERE prosedur_pasien.no_rawat = ? ORDER BY prosedur_pasien.prioritas"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": [");
                                    do{
                                        iyembuilder.append("{").
                                                        append("\"resourceType\": \"Procedure\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()+rs.getString("kode"))).append("\",").
                                                        append("\"text\": {").
                                                            append("\"status\": \"generated\",").
                                                            append("\"div\": \"Generated Narrative with Details\"").
                                                        append("},").
                                                        append("\"status\": \"completed\",").
                                                        append("\"code\": {").
                                                            append("\"coding\": [").
                                                                append("{").
                                                                    append("\"system\": \"").append(rs.getString("system")).append("\",").
                                                                    append("\"code\": \"").append(rs.getString("kode_snomed")).append("\",").
                                                                    append("\"display\": \"").append(rs.getString("display").replace("\"", "'")).append("\"").
                                                                append("}").
                                                            append("]").
                                                        append("},").
                                                        append("\"subject\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                            append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\"").
                                                        append("},").
                                                        append("\"context\": {").
                                                            append("\"reference\": \"Encounter/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString())).append("\",").
                                                            append("\"display\": \"Admitted to ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString()).append(" ").append(akses.getnamars()).append(" between ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()).append(" and ").append(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()).append("\"").
                                                        append("},").
                                                        append("\"performedPeriod\": {").
                                                            append("\"start\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()).append("\",").
                                                            append("\"end\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()).append("\"").
                                                        append("},").
                                                        append("\"performer\": [{").
                                                            append("\"role\": {").
                                                                append("\"coding\": [").
                                                                    append("{").
                                                                        append("\"system\": \"http://snomed.info/sct\",").
                                                                        append("\"code\": \"309343006\",").
                                                                        append("\"display\": \"Physician\"").
                                                                    append("}").
                                                                append("]").
                                                            append("},").
                                                            append("\"actor\": {").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()).append("\"").
                                                            append("}").
                                                        append("}],").
                                                        append("\"reasonCode\": [").
                                                            append("{").
                                                                append("\"text\": \"").append(jalannyapenyakit).append("\"").
                                                            append("}").
                                                        append("],").
                                                        append("\"note\": [").
                                                            append("{").
                                                                append("\"text\": \"").append("Prognosa : ").append((jalannyapenyakit.equals("")?"Sesuai prognosa di resume":jalannyapenyakit)).append(". Prosedur/Tindakan : ").append(rs.getString("deskripsi_panjang")).append("\"").
                                                            append("}").
                                                        append("]").
                                                   append("},");
                                    }while(rs.next());
                                    iyembuilder.deleteCharAt(iyembuilder.length()-1);
                                    iyembuilder.append("]").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Procedure : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }   
                        
                        if (chkDevice.isSelected() == true) {
                            ps=koneksi.prepareStatement(
                                "SELECT databarang.kode_brng,databarang.nama_brng,databarang.kode_industri,data_batch.tgl_kadaluarsa,data_batch.tgl_beli,detail_pemberian_obat.no_batch,"+
                                "industrifarmasi.nama_industri,industrifarmasi.no_telp FROM detail_pemberian_obat INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng " +
                                "INNER JOIN industrifarmasi ON databarang.kode_industri = industrifarmasi.kode_industri inner join jenis on jenis.kdjns=databarang.kdjns " +
                                "INNER JOIN data_batch on data_batch.kode_brng=detail_pemberian_obat.kode_brng and detail_pemberian_obat.no_batch=data_batch.no_batch "+
                                "WHERE detail_pemberian_obat.no_rawat = ? AND (jenis.nama like '%alkes%' or jenis.nama like '%alat kesehatan%')"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"Device\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kode_brng")+rs.getString("no_batch"))).append("\",").
                                                        append("\"text\": {").
                                                            append("\"status\": \"generated\",").
                                                            append("\"div\": \"<div xmlns=\\\"http://www.w3.org/1999/xhtml\\\">id: ").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kode_brng")+rs.getString("no_batch"))).
                                                            append("<br/>identifier: ").append(rs.getString("kode_brng")).
                                                            append("<br/>type: ").append(rs.getString("nama_brng")).append(" (Details : {http://acme.com/devices code = ").append(rs.getString("kode_brng")).append(", given as ").append(rs.getString("nama_brng")).append("})").
                                                            append("<br/>lotNumber: ").append(rs.getString("no_batch")).
                                                            append("<br/>manufacturer: ").append(rs.getString("nama_industri")).
                                                            append("<br/>model: ").
                                                            append("<br/>patient: Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).
                                                            append("<br/>contact: ").append(rs.getString("no_telp")).
                                                            append("</div>\"").
                                                        append("},").
                                                        append("\"identifier\": [{").
                                                            append("\"system\": \"http://sys-ids.kemkes.go.id/device/").append(akses.getkodeppkkemenkes()).append("\",").
                                                            append("\"value\": \"").append(rs.getString("kode_brng")).append("\"").
                                                        append("}],").
                                                        append("\"status\": \"active\",").
                                                        append("\"type\": {").
                                                            append("\"coding\": [{").
                                                                append("\"system\": \"http://acme.com/devices\",").
                                                                append("\"code\": \"").append(rs.getString("kode_brng")).append("\",").
                                                                append("\"display\": \"").append(rs.getString("nama_brng").replace("\"", "'")).append("\"").
                                                            append("}],").
                                                            append("\"text\": \"").append(rs.getString("nama_brng").replace("\"", "'")).append("\"").
                                                        append("},").
                                                        append("\"lotNumber\": \"").append(rs.getString("no_batch")).append("\",").
                                                        append("\"manufacturer\": \"").append(rs.getString("nama_industri").replace("\"", "'")).append("\",").
                                                        append("\"manufactureDate\": \"").append(rs.getString("tgl_beli")).append("\",").
                                                        append("\"expirationDate\": \"").append(rs.getString("tgl_kadaluarsa")).append("\",").
                                                        append("\"model\": \"\",").
                                                        append("\"patient\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\"").
                                                        append("},").
                                                        append("\"contact\": [{").
                                                            append("\"system\": \"phone\",").
                                                            append("\"value\": \"").append(rs.getString("no_telp")).append("\",").
                                                            append("\"use\": \"work\"").
                                                        append("}]").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Device : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }
                            

                        if(chkMedicationRequest.isSelected()==true){
                            String[] arrSplit;
                            String signa1,signa2;
                            ps=koneksi.prepareStatement(
                                "select satu_sehat_mapping_obat.obat_code,satu_sehat_mapping_obat.obat_system,resep_dokter.kode_brng,satu_sehat_mapping_obat.obat_display,satu_sehat_mapping_obat.form_code,"+
                                "satu_sehat_mapping_obat.form_system,satu_sehat_mapping_obat.form_display,satu_sehat_mapping_obat.route_code,satu_sehat_mapping_obat.route_system,satu_sehat_mapping_obat.route_display,"+
                                "satu_sehat_mapping_obat.denominator_code,satu_sehat_mapping_obat.denominator_system,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,resep_dokter.jml,resep_dokter.aturan_pakai,"+
                                "resep_dokter.no_resep from resep_obat inner join resep_dokter on resep_dokter.no_resep=resep_obat.no_resep "+
                                "inner join satu_sehat_mapping_obat on satu_sehat_mapping_obat.kode_brng=resep_dokter.kode_brng "+
                                "inner join satu_sehat_medication on satu_sehat_medication.kode_brng=satu_sehat_mapping_obat.kode_brng "+
                                "where resep_obat.no_rawat=?"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    arrSplit = rs.getString("aturan_pakai").toLowerCase().split("x");
                                    signa1 = "1";
                                    try {
                                        if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa1 = "1";
                                    }
                                    signa2 = "1";
                                    try {
                                        if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa2 = "1";
                                    }

                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"MedicationRequest\",").
                                                        append("\"text\": {").
                                                            append("\"div\": \"").append(rs.getString("obat_display").replace("\"", "'")).append("\"").
                                                        append("},").
                                                        append("\"identifier\": {").
                                                            append("\"system\": \"id_resep_pulang\",").
                                                            append("\"value\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("no_resep")+rs.getString("kode_brng"))).append("\"").
                                                        append("},").
                                                        append("\"subject\": {").
                                                            append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\",").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\"").
                                                        append("},").
                                                        append("\"intent\": \"final\",").
                                                        append("\"medicationCodeableConcept\": {").
                                                            append("\"coding\": [{").
                                                                append("\"code\": \"").append(rs.getString("obat_code")).append("\",").
                                                                append("\"system\": \"").append(rs.getString("obat_system")).append("\"").
                                                            append("}],").
                                                            append("\"text\": \"").append(rs.getString("obat_display").replace("\"", "'")).append("\"").
                                                        append("},").
                                                        append("\"dosageInstruction\": [{").
                                                            append("\"doseQuantity\": {").
                                                                append("\"code\": \"").append(rs.getString("denominator_code")).append("\",").
                                                                append("\"system\": \"").append(rs.getString("denominator_system")).append("\",").
                                                                append("\"unit\": \"").append(rs.getString("denominator_code")).append("\",").
                                                                append("\"value\": \"").append(signa2).append("\"").
                                                            append("},").
                                                            append("\"route\": {").
                                                                append("\"coding\": [{").
                                                                    append("\"code\": \"").append(rs.getString("route_code")).append("\",").
                                                                    append("\"display\": \"").append(rs.getString("route_display").replace("\"", "'")).append("\",").
                                                                    append("\"system\": \"").append(rs.getString("route_system")).append("\"").
                                                                append("}]").
                                                            append("},").
                                                            append("\"timing\": {").
                                                                append("\"repeat\": {").
                                                                    append("\"frequency\": \"").append(signa1).append("\",").
                                                                    append("\"period\": 1,").
                                                                    append("\"periodUnit\": \"d\"").
                                                                append("}").
                                                            append("},").
                                                            append("\"additionalInstruction\": [{").
                                                                append("\"text\": \"").append(rs.getString("aturan_pakai").replace("\"", "'")).append("\"").
                                                            append("}]").
                                                        append("}],").
                                                        append("\"reasonCode\": [{").
                                                            append("\"coding\": [{").
                                                                append("\"code\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString()).append("\",").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()).append("\",").
                                                                append("\"system\": \"http://snomed.info/sct\"").
                                                            append("}],").
                                                            append("\"text\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString()).append("\"").
                                                        append("}],").
                                                        append("\"requester\": {").
                                                            append("\"agent\": {").
                                                                append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString()).append("\",").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())).append("\"").
                                                            append("},").
                                                            append("\"onBehalfOf\": {").
                                                                append("\"reference\": \"Organization/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)).append("-").append(jadikanUUID(akses.getkodeppkbpjs()+akses.getnamars())).append("\"").
                                                            append("}").
                                                        append("},").
                                                        append("\"meta\": {").
                                                            append("\"lastUpdated\": \"").append(rs.getString("tgl_peresepan")).append(" ").append(rs.getString("jam_peresepan")).append("\"").
                                                        append("}").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Medication Request : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }
                        
                        if (chkDiagnosticReport.isSelected() == true) {
                            ps=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,periksa_radiologi.kd_dokter,pegawai.nama,pegawai.no_ktp as ktpdokter,"+
                                "satu_sehat_encounter.id_encounter,permintaan_radiologi.noorder,permintaan_radiologi.tgl_hasil,permintaan_radiologi.jam_hasil,permintaan_radiologi.diagnosa_klinis,"+
                                "jns_perawatan_radiologi.nm_perawatan,satu_sehat_mapping_radiologi.code,satu_sehat_mapping_radiologi.system,satu_sehat_mapping_radiologi.display,"+
                                "satu_sehat_servicerequest_radiologi.id_servicerequest,permintaan_pemeriksaan_radiologi.kd_jenis_prw,satu_sehat_specimen_radiologi.id_specimen,"+
                                "satu_sehat_observation_radiologi.id_observation,hasil_radiologi.hasil "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat inner join permintaan_radiologi on permintaan_radiologi.no_rawat=reg_periksa.no_rawat "+
                                "inner join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder=permintaan_radiologi.noorder "+
                                "inner join jns_perawatan_radiologi on jns_perawatan_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw "+
                                "inner join satu_sehat_mapping_radiologi on satu_sehat_mapping_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                "inner join satu_sehat_servicerequest_radiologi on satu_sehat_servicerequest_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder "+
                                "and satu_sehat_servicerequest_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw "+
                                "inner join satu_sehat_specimen_radiologi on satu_sehat_servicerequest_radiologi.noorder=satu_sehat_specimen_radiologi.noorder "+
                                "and satu_sehat_servicerequest_radiologi.kd_jenis_prw=satu_sehat_specimen_radiologi.kd_jenis_prw "+
                                "inner join periksa_radiologi on periksa_radiologi.no_rawat=permintaan_radiologi.no_rawat and periksa_radiologi.tgl_periksa=permintaan_radiologi.tgl_hasil "+
                                "and periksa_radiologi.jam=permintaan_radiologi.jam_hasil and periksa_radiologi.dokter_perujuk=permintaan_radiologi.dokter_perujuk "+
                                "inner join hasil_radiologi on periksa_radiologi.no_rawat=hasil_radiologi.no_rawat and periksa_radiologi.tgl_periksa=hasil_radiologi.tgl_periksa "+
                                "and periksa_radiologi.jam=hasil_radiologi.jam "+
                                "inner join satu_sehat_observation_radiologi on satu_sehat_specimen_radiologi.noorder=satu_sehat_observation_radiologi.noorder "+
                                "and satu_sehat_specimen_radiologi.kd_jenis_prw=satu_sehat_observation_radiologi.kd_jenis_prw "+
                                "left join satu_sehat_diagnosticreport_radiologi on satu_sehat_servicerequest_radiologi.noorder=satu_sehat_diagnosticreport_radiologi.noorder "+
                                "and satu_sehat_servicerequest_radiologi.kd_jenis_prw=satu_sehat_diagnosticreport_radiologi.kd_jenis_prw "+
                                "inner join pegawai on periksa_radiologi.kd_dokter=pegawai.nik "+
                                "where reg_periksa.no_rawat=?"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"DiagnosticReport\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("noorder")+rs.getString("kd_jenis_prw"))).append("\",").
                                                        append("\"subject\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                            append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\",").
                                                            append("\"noSep\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()).append("\"").
                                                        append("},").
                                                        append("\"category\": {").
                                                            append("\"coding\": {").
                                                                append("\"system\": \"http://hl7.org/fhir/v2/0074\",").
                                                                append("\"code\": \"RAD\",").
                                                                append("\"display\": \"Radiology\"").
                                                            append("}").
                                                        append("},").
                                                        append("\"status\": \"final\",").
                                                        append("\"performer\": [{").
                                                            append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                            append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                        append("}],").
                                                        append("\"result\": [{").
                                                            append("\"resourceType\": \"Observation\",").
                                                            append("\"id\": \"").append(rs.getString("id_observation")).append("\",").
                                                            append("\"status\": \"final\",").
                                                            append("\"text\": {").
                                                                append("\"status\": \"generated\",").
                                                                append("\"div\": \"").append(rs.getString("hasil").replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)", "<br>").replaceAll("\t", " ")).append("\"").
                                                            append("},").
                                                            append("\"issued\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"effectiveDateTime\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"code\": {").
                                                                append("\"coding\": {").
                                                                    append("\"system\": \"").append(rs.getString("system")).append("\",").
                                                                    append("\"code\": \"").append(rs.getString("code")).append("\",").
                                                                    append("\"display\": \"").append(rs.getString("display").replace("\"", "'")).append("\"").
                                                                append("},").
                                                                append("\"text\": \"").append(rs.getString("nm_perawatan").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"performer\": {").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                                append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"image\": [{").
                                                                append("\"comment\": \"\",").
                                                                append("\"link\": {").
                                                                    append("\"reference\": \"\",").
                                                                    append("\"display\": \"\"").
                                                                append("}").
                                                            append("}],").
                                                            append("\"conclusion\": \"").append(rs.getString("diagnosa_klinis").replace("\"", "'")).append("\"").
                                                        append("}]").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Hasil Radiologi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                            
                            ps=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,periksa_lab.kd_dokter,pegawai.nama,pegawai.no_ktp as ktpdokter,"+
                                "satu_sehat_encounter.id_encounter,permintaan_lab.noorder,permintaan_lab.tgl_hasil,permintaan_lab.jam_hasil,permintaan_lab.diagnosa_klinis,"+
                                "template_laboratorium.Pemeriksaan,satu_sehat_mapping_lab.code,satu_sehat_mapping_lab.system,satu_sehat_mapping_lab.display,"+
                                "satu_sehat_servicerequest_lab.id_servicerequest,permintaan_detail_permintaan_lab.id_template,satu_sehat_specimen_lab.id_specimen,"+
                                "satu_sehat_observation_lab.id_observation,saran_kesan_lab.kesan,template_laboratorium.kd_jenis_prw "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat inner join permintaan_lab on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                                "inner join permintaan_detail_permintaan_lab on permintaan_detail_permintaan_lab.noorder=permintaan_lab.noorder "+
                                "inner join template_laboratorium on template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template "+
                                "inner join satu_sehat_mapping_lab on satu_sehat_mapping_lab.id_template=template_laboratorium.id_template "+
                                "inner join satu_sehat_servicerequest_lab on satu_sehat_servicerequest_lab.noorder=permintaan_detail_permintaan_lab.noorder "+
                                "and satu_sehat_servicerequest_lab.id_template=permintaan_detail_permintaan_lab.id_template "+
                                "and satu_sehat_servicerequest_lab.kd_jenis_prw=permintaan_detail_permintaan_lab.kd_jenis_prw "+
                                "inner join satu_sehat_specimen_lab on satu_sehat_servicerequest_lab.noorder=satu_sehat_specimen_lab.noorder "+
                                "and satu_sehat_servicerequest_lab.id_template=satu_sehat_specimen_lab.id_template "+
                                "and satu_sehat_servicerequest_lab.kd_jenis_prw=satu_sehat_specimen_lab.kd_jenis_prw "+
                                "inner join periksa_lab on periksa_lab.no_rawat=permintaan_lab.no_rawat and periksa_lab.tgl_periksa=permintaan_lab.tgl_hasil "+
                                "and periksa_lab.jam=permintaan_lab.jam_hasil and periksa_lab.dokter_perujuk=permintaan_lab.dokter_perujuk "+
                                "inner join saran_kesan_lab on periksa_lab.no_rawat=saran_kesan_lab.no_rawat and periksa_lab.tgl_periksa=saran_kesan_lab.tgl_periksa "+
                                "and periksa_lab.jam=saran_kesan_lab.jam "+
                                "inner join satu_sehat_observation_lab on satu_sehat_specimen_lab.noorder=satu_sehat_observation_lab.noorder "+
                                "and satu_sehat_specimen_lab.id_template=satu_sehat_observation_lab.id_template "+
                                "and satu_sehat_specimen_lab.kd_jenis_prw=satu_sehat_observation_lab.kd_jenis_prw "+
                                "left join satu_sehat_diagnosticreport_lab on satu_sehat_servicerequest_lab.noorder=satu_sehat_diagnosticreport_lab.noorder "+
                                "and satu_sehat_servicerequest_lab.id_template=satu_sehat_diagnosticreport_lab.id_template "+
                                "and satu_sehat_servicerequest_lab.kd_jenis_prw=satu_sehat_diagnosticreport_lab.kd_jenis_prw "+
                                "inner join pegawai on periksa_lab.kd_dokter=pegawai.nik "+
                                "where reg_periksa.no_rawat=?"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"DiagnosticReport\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("noorder")+rs.getString("id_template"))).append("\",").
                                                        append("\"subject\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                            append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\",").
                                                            append("\"noSep\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()).append("\"").
                                                        append("},").
                                                        append("\"category\": {").
                                                            append("\"coding\": {").
                                                                append("\"system\": \"http://hl7.org/fhir/v2/0074\",").
                                                                append("\"code\": \"LAB\",").
                                                                append("\"display\": \"Laboratory\"").
                                                            append("}").
                                                        append("},").
                                                        append("\"status\": \"final\",").
                                                        append("\"performer\": [{").
                                                            append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                            append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                        append("}],").
                                                        append("\"result\": [{").
                                                            append("\"resourceType\": \"Observation\",").
                                                            append("\"id\": \"").append(rs.getString("id_observation")).append("\",").
                                                            append("\"status\": \"final\",").
                                                            append("\"text\": {").
                                                                append("\"status\": \"generated\",").
                                                                append("\"div\": \"").append(rs.getString("kesan").replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)", "<br>").replaceAll("\t", " ")).append("\"").
                                                            append("},").
                                                            append("\"issued\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"effectiveDateTime\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"code\": {").
                                                                append("\"coding\": {").
                                                                    append("\"system\": \"").append(rs.getString("system")).append("\",").
                                                                    append("\"code\": \"").append(rs.getString("code")).append("\",").
                                                                    append("\"display\": \"").append(rs.getString("display").replace("\"", "'")).append("\"").
                                                                append("},").
                                                                append("\"text\": \"").append(rs.getString("Pemeriksaan").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"performer\": {").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                                append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"image\": [{").
                                                                append("\"comment\": \"\",").
                                                                append("\"link\": {").
                                                                    append("\"reference\": \"\",").
                                                                    append("\"display\": \"\"").
                                                                append("}").
                                                            append("}],").
                                                            append("\"conclusion\": \"").append(rs.getString("kesan").replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)", " ").replaceAll("\t", " ")).append("\"").
                                                        append("}]").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Saran Kesan Lab PK : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                            
                            ps=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,periksa_lab.kd_dokter,pegawai.nama,pegawai.no_ktp as ktpdokter,"+
                                "satu_sehat_encounter.id_encounter,permintaan_labmb.noorder,permintaan_labmb.tgl_hasil,permintaan_labmb.jam_hasil,permintaan_labmb.diagnosa_klinis,"+
                                "template_laboratorium.Pemeriksaan,satu_sehat_mapping_lab.code,satu_sehat_mapping_lab.system,satu_sehat_mapping_lab.display,"+
                                "satu_sehat_servicerequest_lab_mb.id_servicerequest,permintaan_detail_permintaan_labmb.id_template,satu_sehat_specimen_lab_mb.id_specimen,"+
                                "satu_sehat_observation_lab_mb.id_observation,saran_kesan_lab.kesan,template_laboratorium.kd_jenis_prw "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat inner join permintaan_labmb on permintaan_labmb.no_rawat=reg_periksa.no_rawat "+
                                "inner join permintaan_detail_permintaan_labmb on permintaan_detail_permintaan_labmb.noorder=permintaan_labmb.noorder "+
                                "inner join template_laboratorium on template_laboratorium.id_template=permintaan_detail_permintaan_labmb.id_template "+
                                "inner join satu_sehat_mapping_lab on satu_sehat_mapping_lab.id_template=template_laboratorium.id_template "+
                                "inner join satu_sehat_servicerequest_lab_mb on satu_sehat_servicerequest_lab_mb.noorder=permintaan_detail_permintaan_labmb.noorder "+
                                "and satu_sehat_servicerequest_lab_mb.id_template=permintaan_detail_permintaan_labmb.id_template "+
                                "and satu_sehat_servicerequest_lab_mb.kd_jenis_prw=permintaan_detail_permintaan_labmb.kd_jenis_prw "+
                                "inner join satu_sehat_specimen_lab_mb on satu_sehat_servicerequest_lab_mb.noorder=satu_sehat_specimen_lab_mb.noorder "+
                                "and satu_sehat_servicerequest_lab_mb.id_template=satu_sehat_specimen_lab_mb.id_template "+
                                "and satu_sehat_servicerequest_lab_mb.kd_jenis_prw=satu_sehat_specimen_lab_mb.kd_jenis_prw "+
                                "inner join periksa_lab on periksa_lab.no_rawat=permintaan_labmb.no_rawat and periksa_lab.tgl_periksa=permintaan_labmb.tgl_hasil "+
                                "and periksa_lab.jam=permintaan_labmb.jam_hasil and periksa_lab.dokter_perujuk=permintaan_labmb.dokter_perujuk "+
                                "inner join saran_kesan_lab on periksa_lab.no_rawat=saran_kesan_lab.no_rawat and periksa_lab.tgl_periksa=saran_kesan_lab.tgl_periksa "+
                                "and periksa_lab.jam=saran_kesan_lab.jam "+
                                "inner join satu_sehat_observation_lab_mb on satu_sehat_specimen_lab_mb.noorder=satu_sehat_observation_lab_mb.noorder "+
                                "and satu_sehat_specimen_lab_mb.id_template=satu_sehat_observation_lab_mb.id_template "+
                                "and satu_sehat_specimen_lab_mb.kd_jenis_prw=satu_sehat_observation_lab_mb.kd_jenis_prw "+
                                "left join satu_sehat_diagnosticreport_lab_mb on satu_sehat_servicerequest_lab_mb.noorder=satu_sehat_diagnosticreport_lab_mb.noorder "+
                                "and satu_sehat_servicerequest_lab_mb.id_template=satu_sehat_diagnosticreport_lab_mb.id_template "+
                                "and satu_sehat_servicerequest_lab_mb.kd_jenis_prw=satu_sehat_diagnosticreport_lab_mb.kd_jenis_prw "+
                                "inner join pegawai on periksa_lab.kd_dokter=pegawai.nik "+
                                "where reg_periksa.no_rawat=?"
                            );
                            try {
                                ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    iyembuilder.append("{").
                                                    append("\"resource\": {").
                                                        append("\"resourceType\": \"DiagnosticReport\",").
                                                        append("\"id\": \"").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("noorder")+rs.getString("id_template")+"mb")).append("\",").
                                                        append("\"subject\": {").
                                                            append("\"reference\": \"Patient/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString())).append("\",").
                                                            append("\"display\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()).append("\",").
                                                            append("\"noSep\": \"").append(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()).append("\"").
                                                        append("},").
                                                        append("\"category\": {").
                                                            append("\"coding\": {").
                                                                append("\"system\": \"http://hl7.org/fhir/v2/0074\",").
                                                                append("\"code\": \"LAB\",").
                                                                append("\"display\": \"Laboratory\"").
                                                            append("}").
                                                        append("},").
                                                        append("\"status\": \"final\",").
                                                        append("\"performer\": [{").
                                                            append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                            append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                        append("}],").
                                                        append("\"result\": [{").
                                                            append("\"resourceType\": \"Observation\",").
                                                            append("\"id\": \"").append(rs.getString("id_observation")).append("\",").
                                                            append("\"status\": \"final\",").
                                                            append("\"text\": {").
                                                                append("\"status\": \"generated\",").
                                                                append("\"div\": \"").append(rs.getString("kesan").replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)", "<br>").replaceAll("\t", " ")).append("\"").
                                                            append("},").
                                                            append("\"issued\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"effectiveDateTime\": \"").append(rs.getString("tgl_hasil")).append(" ").append(rs.getString("jam_hasil")).append("\",").
                                                            append("\"code\": {").
                                                                append("\"coding\": {").
                                                                    append("\"system\": \"").append(rs.getString("system")).append("\",").
                                                                    append("\"code\": \"").append(rs.getString("code")).append("\",").
                                                                    append("\"display\": \"").append(rs.getString("display").replace("\"", "'")).append("\"").
                                                                append("},").
                                                                append("\"text\": \"").append(rs.getString("Pemeriksaan").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"performer\": {").
                                                                append("\"reference\": \"Practitioner/").append(akses.getkodeppkbpjs()).append("-").append(akses.getkodeppkkemenkes()).append("-").append(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString().substring(0, 1)).append("-").append(jadikanUUID(rs.getString("kd_dokter"))).append("\",").
                                                                append("\"display\": \"").append(rs.getString("nama").replace("\"", "'")).append("\"").
                                                            append("},").
                                                            append("\"image\": [{").
                                                                append("\"comment\": \"\",").
                                                                append("\"link\": {").
                                                                    append("\"reference\": \"\",").
                                                                    append("\"display\": \"\"").
                                                                append("}").
                                                            append("}],").
                                                            append("\"conclusion\": \"").append(rs.getString("kesan").replace("\"", "'").replaceAll("(\r\n|\r|\n|\n\r)", " ").replaceAll("\t", " ")).append("\"").
                                                        append("}]").
                                                    append("}").
                                                append("},");
                                }
                            } catch (Exception e) {
                                 System.out.println("Notif Saran Kesan Lab MB : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }

                        if (iyembuilder.length() > 0) {
                            iyembuilder.setLength(iyembuilder.length() - 1);
                        }

                        String stringJSON = "{" +
                                                "\"resourceType\": \"Bundle\"," +
                                                "\"id\": \""+akses.getkodeppkbpjs()+"-"+akses.getkodeppkkemenkes()+"-"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().substring(0,1)+"-"+jadikanUUID(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString())+"\"," +
                                                "\"meta\": {" +
                                                    "\"lastUpdated\": \""+Valid.SetTgl(DTPTanggal.getSelectedItem()+"")+"T"+DTPTanggal.getSelectedItem().toString().substring(11,19)+".000+07:00\"" +
                                                "}," +
                                                "\"identifier\": {" +
                                                    "\"system\": \"sep\"," +
                                                    "\"value\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"" +
                                                "}," +
                                                "\"type\": \"document\"," +
                                                "\"entry\": [" +
                                                    iyembuilder.toString()+
                                                "]" +
                                            "}";
                        try {
                            mapper = new ObjectMapper();
                            mapper.enable(SerializationFeature.INDENT_OUTPUT);
                            Object datajson = mapper.readValue(stringJSON, Object.class);
                            String prettyJson = mapper.writeValueAsString(datajson);
                            JSONFHIR.setText(prettyJson);
                        } catch (Exception e) {
                            JSONFHIR.setText(stringJSON);
                        }

                        WindowFHIR.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        WindowFHIR.setLocationRelativeTo(internalFrame1);
                        WindowFHIR.setVisible(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data..!!");
                }
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data..!!");
        }
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        if(tabMode.getRowCount()!=0){
            if(tbObat.getSelectedRow()!= -1){
                if (resume == null || !resume.isDisplayable()) {
                    resume=new RMRiwayatPerawatan(null,false);
                    resume.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    resume.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            resume=null;
                        }
                    });
                    resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    resume.setLocationRelativeTo(internalFrame1);
                }
                if (resume == null) return;
                if (!resume.isVisible()) {
                    resume.setNoRawat(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                    resume.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
                }  
                if (resume.isVisible()) {
                    resume.toFront();
                    return;
                }    
                resume.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data..!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data..!!");
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        CmbJenis.setSelectedIndex(0);
        CmbStatus.setSelectedIndex(0);
        runBackground(() ->tampil());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
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
    }//GEN-LAST:event_formWindowOpened

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(!JSONFHIR.getText().equals("")){
            if(tbObat.getSelectedRow()!= -1){
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
                    headers.add("X-Cons-ID",koneksiDB.CONSIDAPISMARTCLAIM());
                    utc=String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("X-Timestamp",utc);
                    headers.add("X-Signature",api.getHmac(utc));
                    headers.add("user_key",koneksiDB.USERKEYAPISMARTCLAIM());
                    requestJson ="{" +
                                    "\"request\": {" +
                                        "\"noSep\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"," +
                                        "\"jnsPelayanan\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,1)+"\"," +
                                        "\"bulan\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().substring(5,7).replaceAll("0","")+"\"," +
                                        "\"tahun\": \""+tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().substring(0,4)+"\"," +
                                        "\"dataMR\": \""+api.encryptSmartClaimData(JSONFHIR.getText(),akses.getkodeppkbpjs())+"\"" +
                                    "}" +
                                "}";
                    System.out.println("JSON : "+requestJson);
                    System.out.println("URL : "+link+"/eclaim/rekammedis/insert");
                    requestEntity = new HttpEntity(requestJson,headers);
                    root = mapper.readTree(api.getRest().exchange(link+"/eclaim/rekammedis/insert", HttpMethod.POST, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("200")){
                        if(Sequel.menyimpantf("bridging_smart_klaim_bpjs","?,?","No.SEP",2,new String[]{
                                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),Valid.SetTgl(DTPTanggal.getSelectedItem()+"")
                            })==true){
                            tbObat.setValueAt(Valid.SetTgl(DTPTanggal.getSelectedItem()+""),tbObat.getSelectedRow(),12);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }   
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    String pesanError;
                    if (ex.toString().contains("UnknownHostException")) {
                        pesanError = "Koneksi ke server BPJS terputus...!";
                    } else if (ex.getCause() != null) {
                        pesanError = "Error: " + ex.getCause().getMessage();
                    } else {
                        pesanError = "Error: " + ex.getMessage();
                    }
                    JOptionPane.showMessageDialog(null, pesanError);
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"JSON FHIR masih kosong ...!!");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowFHIR.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SmartKlaimBPJSKirimFHIR dialog = new SmartKlaimBPJSKirimFHIR(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn5;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayat;
    private widget.Button BtnSimpan;
    private widget.ComboBox CmbJenis;
    private widget.ComboBox CmbStatus;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTanggal;
    private widget.PanelBiasa FormMenu;
    private widget.TextArea JSONFHIR;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private javax.swing.JDialog WindowFHIR;
    private widget.CekBox chkComposition;
    private widget.CekBox chkCondition;
    private widget.CekBox chkDevice;
    private widget.CekBox chkDiagnosticReport;
    private widget.CekBox chkEncounter;
    private widget.CekBox chkMedicationRequest;
    private widget.CekBox chkOrganization;
    private widget.CekBox chkPatient;
    private widget.CekBox chkPractioner;
    private widget.CekBox chkProcedure;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,pasien.no_ktp,bridging_sep.no_kartu,"+
                    "bridging_sep.tanggal_lahir,bridging_sep.jkel,bridging_sep.notelep,bridging_sep.tglsep,if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan') as jnspelayanan,ifnull(bridging_smart_klaim_bpjs.tanggal_kirim,'') as tanggalkirim,"+
                    "bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,bridging_sep.kddpjp,bridging_sep.nmdpdjp,DATE_FORMAT(bridging_sep.tglpulang, '%Y-%m-%d') as tglpulang,bridging_sep.no_rujukan,bridging_sep.diagawal,"+
                    "ifnull(mapping_penyakit_smart_klaim_bpjs.kode_snomed,'') as kode_snomed,ifnull(mapping_penyakit_smart_klaim_bpjs.display,'') as display,pasien.stts_nikah,kabupaten.nm_kab,kecamatan.kd_kec,kecamatan.nm_kec "+
                    "from bridging_sep inner join pasien on pasien.no_rkm_medis=bridging_sep.nomr inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join propinsi on pasien.kd_prop=propinsi.kd_prop left join bridging_smart_klaim_bpjs on bridging_smart_klaim_bpjs.no_sep=bridging_sep.no_sep "+
                    "left join mapping_penyakit_smart_klaim_bpjs on bridging_sep.diagawal=mapping_penyakit_smart_klaim_bpjs.icd10 "+
                    "where bridging_sep.tglsep between ? and ? "+
                    (CmbStatus.getSelectedIndex()>0?(CmbStatus.getSelectedIndex()==1?"and ifnull(bridging_smart_klaim_bpjs.tanggal_kirim,'')<>'' ":"and ifnull(bridging_smart_klaim_bpjs.tanggal_kirim,'')='' "):"")+
                    (CmbJenis.getSelectedIndex()>0?(CmbJenis.getSelectedIndex()==1?"and bridging_sep.jnspelayanan='1' ":"and bridging_sep.jnspelayanan='2' "):"")+(TCari.getText().trim().equals("")?"":
                    "and (bridging_sep.no_sep like ? or bridging_sep.no_rawat like ? or bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_sep.no_kartu like ? or pasien.no_ktp like ?) ")+
                    "order by bridging_sep.tglsep"
            );
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
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop"),
                        rs.getString("no_ktp"),rs.getString("no_kartu"),rs.getString("tanggal_lahir"),rs.getString("jkel"),rs.getString("notelep"),rs.getString("tglsep"),rs.getString("jnspelayanan"),rs.getString("tanggalkirim"),rs.getString("kddpjp"),rs.getString("nmdpdjp"),
                        rs.getString("kdpolitujuan"),rs.getString("nmpolitujuan"),rs.getString("tglpulang"),rs.getString("no_rujukan"),rs.getString("diagawal"),rs.getString("kode_snomed"),rs.getString("display"),rs.getString("stts_nikah"),rs.getString("nm_kab"),
                        rs.getString("kd_kec"),rs.getString("nm_kec")
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

    public void isCek(){
        BtnKirim.setEnabled(akses.getbridging_smart_klaim_bpjs());
        BtnRiwayat.setEnabled(akses.getbridging_smart_klaim_bpjs());
        BtnPrint.setEnabled(akses.getbridging_smart_klaim_bpjs());
    }
    
    public JTable getTable(){
        return tbObat;
    }
    
    private String jadikanUUID(String uuidString) {
        String finalUUID;
        try {
            UUID uuid = UUID.fromString(uuidString);
            finalUUID = uuid.toString();
        } catch (IllegalArgumentException e) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(uuidString.getBytes(StandardCharsets.UTF_8));
                long mostSigBits = 0;
                long leastSigBits = 0;
                for (int i = 0; i < 8; i++) {
                    mostSigBits = (mostSigBits << 8) | (hash[i] & 0xff);
                }
                for (int i = 8; i < 16; i++) {
                    leastSigBits = (leastSigBits << 8) | (hash[i] & 0xff);
                }

                UUID staticUUID = new UUID(mostSigBits, leastSigBits);
                finalUUID=staticUUID.toString();
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException("SHA-256 algorithm not found", ex);
            }
        }
        return finalUUID;
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

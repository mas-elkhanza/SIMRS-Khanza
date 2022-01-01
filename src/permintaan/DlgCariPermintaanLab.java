package permintaan;
import bridging.ApiLICA;
import bridging.ApiMEDQLAB;
import bridging.koneksiDBELIMS;
import bridging.koneksiDBSMARTLAB;
import bridging.koneksiDBSysmex;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPeriksaLaboratorium;

public class DlgCariPermintaanLab extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Connection koneksisysmex;
    private Connection koneksielims;
    private Connection koneksismartlab;
    private int i,nilai_detik,permintaanbaru=0;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariBangsal ruang=new DlgCariBangsal(null,false);
    private BackgroundMusic music;
    private Date now;
    private boolean aktif=false,semua;
    private ApiLICA lica=new ApiLICA();
    private ObjectMapper mapper = new ObjectMapper();
    private ApiMEDQLAB medqlab=new ApiMEDQLAB();
    private WebEngine engine;
    private JsonNode root;
    private JsonNode response;
    private String pilihan="",alarm="",formalarm="",nol_detik,detik,tglsampel="",tglhasil="",norm="",kamar="",namakamar="",la="",ld="",pa="",pd="",InformasiTambahan,DiagnosaKlinis,
                    NoPermintaan="",NoRawat="",Pasien="",Permintaan="",JamPermintaan="",Sampel="",JamSampel="",Hasil="",JamHasil="",KodeDokter="",DokterPerujuk="",Ruang="",json="",finger="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPermintaanLab(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        WindowAmbilSampel.setSize(530,80);
        WindowTerkirim.setSize(300,90);
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam","Sampel","Jam","Hasil","Jam",
            "Kode Dokter","Dokter Perujuk","Poli Registrasi","Informasi Tambahan","Diagnosis Klinis",
            "Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLabRalan.setModel(tabMode);

        tbLabRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbLabRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbLabRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(130);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(110);
            }
        }
        tbLabRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Detail Pemeriksaan","Satuan","Nilai Rujukan","Permintaan","Jam","Sampel","Jam","Hasil",
                "Jam","Kode Dokter","Dokter Perujuk","Poli Registrasi","Informasi Tambahan","Diagnosis Klinis","Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLabRalan2.setModel(tabMode2);

        tbLabRalan2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbLabRalan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbLabRalan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(210);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(110);
            }
        }
        tbLabRalan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","Pasien","Permintaan","Jam","Sampel","Jam","Hasil","Jam","Kode Dokter",
            "Dokter Perujuk","Kamar Terakhir","Informasi Tambahan","Diagnosis Klinis","Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLabRanap.setModel(tabMode3);

        tbLabRanap.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbLabRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbLabRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(65);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(130);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(110);
            }
        }
        tbLabRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode4=new DefaultTableModel(null,new Object[]{
                "No.Permintaan","No.Rawat","Pasien","Pemeriksaan","Detail Pemeriksaan","Satuan","Nilai Rujukan","Permintaan","Jam",
                "Sampel","Jam","Hasil","Jam","Kode Dokter","Dokter Perujuk","Kamar Terakhir","Informasi Tambahan","Diagnosis Klinis",
                "Kode Bayar","Jenis Bayar"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLabRanap2.setModel(tabMode4);

        tbLabRanap2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbLabRanap2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbLabRanap2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(210);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(110);
            }
        }
        tbLabRanap2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){ 
                    if(TabPilihRawat.getSelectedIndex()==0){
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter.requestFocus();
                    }else if(TabPilihRawat.getSelectedIndex()==1){
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        CrDokter2.requestFocus();
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    CrPoli.requestFocus();
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
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){   
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());  
                    Kamar.requestFocus();
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
        
        try {
            alarm=koneksiDB.ALARMLAB();
            formalarm=koneksiDB.FORMALARMLAB();
        } catch (Exception ex) {
            alarm="no";
            formalarm="ralan + ranap";
        }
        
        if(alarm.equals("yes")){
            jam();
        }
        
        ChkAccor.setSelected(false);
        isMenu();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowAmbilSampel = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        WindowTerkirim = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnSampel = new widget.Button();
        BtnHasil = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabPilihRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        TabRawatJalan = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbLabRalan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbLabRalan2 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        TabRawatInap = new javax.swing.JTabbedPane();
        scrollPane3 = new widget.ScrollPane();
        tbLabRanap = new widget.Table();
        scrollPane4 = new widget.ScrollPane();
        tbLabRanap2 = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        BtnCetakHasilLab = new widget.Button();
        BtnBarcodePermintaan = new widget.Button();
        BtnBarcodePermintaan2 = new widget.Button();
        BtnKirimLica = new widget.Button();
        BtnAmbilLica = new widget.Button();
        BtnKirimSysmex = new widget.Button();
        BtnAmbilSysmex = new widget.Button();
        BtnKirimLISELIMS = new widget.Button();
        BtnAmbilLISELIMS = new widget.Button();
        BtnKirimLISTeras = new widget.Button();
        BtnAmbilLISTeras = new widget.Button();
        BtnKirimLISMADQLAB = new widget.Button();
        BtnAmbilLISMADQLAB = new widget.Button();
        BtnKirimLISSMARTLAB = new widget.Button();
        BtnAmbilLISSMARTLAB = new widget.Button();

        WindowAmbilSampel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilSampel.setName("WindowAmbilSampel"); // NOI18N
        WindowAmbilSampel.setUndecorated(true);
        WindowAmbilSampel.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Waktu Pengambilan Sampel ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnCloseIn4.setBounds(410, 30, 100, 30);

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
        BtnSimpan4.setBounds(305, 30, 100, 30);

        jLabel26.setText("Tanggal & Jam :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-10-2021 13:45:55" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 150, 23);

        WindowAmbilSampel.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowTerkirim.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowTerkirim.setTitle("Informasi Bridging Teras LIS");
        WindowTerkirim.setName("WindowTerkirim"); // NOI18N
        WindowTerkirim.setResizable(false);
        WindowTerkirim.getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Berhasil terkirim..!!");
        jLabel1.setName("jLabel1"); // NOI18N
        WindowTerkirim.getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 15, 150, 23);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        WindowTerkirim.getContentPane().add(jButton1);
        jButton1.setBounds(200, 15, 75, 30);

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
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Laboratorium PK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(112, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(318, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelGlass8.add(BtnCari);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnHapus);

        BtnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnSampel.setMnemonic('P');
        BtnSampel.setText("Sampel");
        BtnSampel.setToolTipText("Alt+P");
        BtnSampel.setName("BtnSampel"); // NOI18N
        BtnSampel.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSampelActionPerformed(evt);
            }
        });
        BtnSampel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSampelKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSampel);

        BtnHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnHasil.setMnemonic('I');
        BtnHasil.setText("Hasil");
        BtnHasil.setToolTipText("Alt+I");
        BtnHasil.setName("BtnHasil"); // NOI18N
        BtnHasil.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHasilActionPerformed(evt);
            }
        });
        BtnHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHasilKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHasil);

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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnPrint);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(LCount);

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
        panelisi1.add(BtnKeluar);

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabPilihRawat.setName("TabPilihRawat"); // NOI18N
        TabPilihRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(257, 23));
        panelGlass9.add(CrDokter);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('6');
        BtnSeek3.setToolTipText("ALt+6");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(257, 23));
        panelGlass9.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek4);

        internalFrame2.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatJalan.setName("TabRawatJalan"); // NOI18N
        TabRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatJalanMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbLabRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbLabRalan.setName("tbLabRalan"); // NOI18N
        tbLabRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabRalanMouseClicked(evt);
            }
        });
        tbLabRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabRalanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbLabRalan);

        TabRawatJalan.addTab("Data Permintaan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbLabRalan2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbLabRalan2.setName("tbLabRalan2"); // NOI18N
        tbLabRalan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabRalan2MouseClicked(evt);
            }
        });
        tbLabRalan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabRalan2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbLabRalan2);

        TabRawatJalan.addTab("Item Permintaan", scrollPane2);

        internalFrame2.add(TabRawatJalan, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Jalan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(CrDokter2);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek5);

        jLabel17.setText("Ruang/Kamar :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass10.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek6);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        TabRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatInap.setName("TabRawatInap"); // NOI18N
        TabRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatInapMouseClicked(evt);
            }
        });

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbLabRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbLabRanap.setName("tbLabRanap"); // NOI18N
        tbLabRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabRanapMouseClicked(evt);
            }
        });
        tbLabRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabRanapKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbLabRanap);

        TabRawatInap.addTab("Data Permintaan", scrollPane3);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbLabRanap2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbLabRanap2.setName("tbLabRanap2"); // NOI18N
        tbLabRanap2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabRanap2MouseClicked(evt);
            }
        });
        tbLabRanap2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabRanap2KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbLabRanap2);

        TabRawatInap.addTab("Item Permintaan", scrollPane4);

        internalFrame3.add(TabRawatInap, java.awt.BorderLayout.CENTER);

        TabPilihRawat.addTab("Rawat Inap", internalFrame3);

        internalFrame1.add(TabPilihRawat, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(205, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(187, 380));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        BtnCetakHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCetakHasilLab.setText("Cetak Permintaan Lab");
        BtnCetakHasilLab.setFocusPainted(false);
        BtnCetakHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnCetakHasilLab.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCetakHasilLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnCetakHasilLab.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnCetakHasilLab.setName("BtnCetakHasilLab"); // NOI18N
        BtnCetakHasilLab.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnCetakHasilLab.setRoundRect(false);
        BtnCetakHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCetakHasilLabActionPerformed(evt);
            }
        });
        FormMenu.add(BtnCetakHasilLab);

        BtnBarcodePermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBarcodePermintaan.setText("Barcode No.Permintaan");
        BtnBarcodePermintaan.setFocusPainted(false);
        BtnBarcodePermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBarcodePermintaan.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBarcodePermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBarcodePermintaan.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBarcodePermintaan.setName("BtnBarcodePermintaan"); // NOI18N
        BtnBarcodePermintaan.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBarcodePermintaan.setRoundRect(false);
        BtnBarcodePermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBarcodePermintaanActionPerformed(evt);
            }
        });
        FormMenu.add(BtnBarcodePermintaan);

        BtnBarcodePermintaan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnBarcodePermintaan2.setText("Barcode No.Permintaan 2");
        BtnBarcodePermintaan2.setFocusPainted(false);
        BtnBarcodePermintaan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnBarcodePermintaan2.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnBarcodePermintaan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnBarcodePermintaan2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnBarcodePermintaan2.setName("BtnBarcodePermintaan2"); // NOI18N
        BtnBarcodePermintaan2.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnBarcodePermintaan2.setRoundRect(false);
        BtnBarcodePermintaan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBarcodePermintaan2ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnBarcodePermintaan2);

        BtnKirimLica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimLica.setText("Kirim Permintaan ke LICA");
        BtnKirimLica.setFocusPainted(false);
        BtnKirimLica.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimLica.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimLica.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimLica.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimLica.setName("BtnKirimLica"); // NOI18N
        BtnKirimLica.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimLica.setRoundRect(false);
        BtnKirimLica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimLicaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimLica);

        BtnAmbilLica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilLica.setText("Ambil Hasil dari LICA");
        BtnAmbilLica.setFocusPainted(false);
        BtnAmbilLica.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilLica.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilLica.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilLica.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilLica.setName("BtnAmbilLica"); // NOI18N
        BtnAmbilLica.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilLica.setRoundRect(false);
        BtnAmbilLica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilLicaActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilLica);

        BtnKirimSysmex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimSysmex.setText("Kirim Permintaan ke Sysmex");
        BtnKirimSysmex.setFocusPainted(false);
        BtnKirimSysmex.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimSysmex.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimSysmex.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimSysmex.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimSysmex.setName("BtnKirimSysmex"); // NOI18N
        BtnKirimSysmex.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimSysmex.setRoundRect(false);
        BtnKirimSysmex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimSysmexActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimSysmex);

        BtnAmbilSysmex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilSysmex.setText("Ambil Hasil dari Sysmex");
        BtnAmbilSysmex.setFocusPainted(false);
        BtnAmbilSysmex.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilSysmex.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilSysmex.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilSysmex.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilSysmex.setName("BtnAmbilSysmex"); // NOI18N
        BtnAmbilSysmex.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilSysmex.setRoundRect(false);
        BtnAmbilSysmex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilSysmexActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilSysmex);

        BtnKirimLISELIMS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimLISELIMS.setText("Kirim Permintaan ke ELIMS");
        BtnKirimLISELIMS.setFocusPainted(false);
        BtnKirimLISELIMS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimLISELIMS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimLISELIMS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimLISELIMS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimLISELIMS.setName("BtnKirimLISELIMS"); // NOI18N
        BtnKirimLISELIMS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimLISELIMS.setRoundRect(false);
        BtnKirimLISELIMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimLISELIMSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimLISELIMS);

        BtnAmbilLISELIMS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilLISELIMS.setText("Ambil Hasil dari ELIMS");
        BtnAmbilLISELIMS.setFocusPainted(false);
        BtnAmbilLISELIMS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilLISELIMS.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilLISELIMS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilLISELIMS.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilLISELIMS.setName("BtnAmbilLISELIMS"); // NOI18N
        BtnAmbilLISELIMS.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilLISELIMS.setRoundRect(false);
        BtnAmbilLISELIMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilLISELIMSActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilLISELIMS);

        BtnKirimLISTeras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimLISTeras.setText("Kirim Permintaan ke TERAS");
        BtnKirimLISTeras.setFocusPainted(false);
        BtnKirimLISTeras.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimLISTeras.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimLISTeras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimLISTeras.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimLISTeras.setName("BtnKirimLISTeras"); // NOI18N
        BtnKirimLISTeras.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimLISTeras.setRoundRect(false);
        BtnKirimLISTeras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimLISTerasActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimLISTeras);

        BtnAmbilLISTeras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilLISTeras.setText("Ambil Hasil dari TERAS");
        BtnAmbilLISTeras.setFocusPainted(false);
        BtnAmbilLISTeras.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilLISTeras.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilLISTeras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilLISTeras.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilLISTeras.setName("BtnAmbilLISTeras"); // NOI18N
        BtnAmbilLISTeras.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilLISTeras.setRoundRect(false);
        BtnAmbilLISTeras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilLISTerasActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilLISTeras);

        BtnKirimLISMADQLAB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimLISMADQLAB.setText("Kirim Permintaan ke MEDQLAB ");
        BtnKirimLISMADQLAB.setFocusPainted(false);
        BtnKirimLISMADQLAB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimLISMADQLAB.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimLISMADQLAB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimLISMADQLAB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimLISMADQLAB.setName("BtnKirimLISMADQLAB"); // NOI18N
        BtnKirimLISMADQLAB.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimLISMADQLAB.setRoundRect(false);
        BtnKirimLISMADQLAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimLISMADQLABActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimLISMADQLAB);

        BtnAmbilLISMADQLAB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilLISMADQLAB.setText("Ambil Hasil dari MEDQLAB");
        BtnAmbilLISMADQLAB.setFocusPainted(false);
        BtnAmbilLISMADQLAB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilLISMADQLAB.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilLISMADQLAB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilLISMADQLAB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilLISMADQLAB.setName("BtnAmbilLISMADQLAB"); // NOI18N
        BtnAmbilLISMADQLAB.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilLISMADQLAB.setRoundRect(false);
        BtnAmbilLISMADQLAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilLISMADQLABActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilLISMADQLAB);

        BtnKirimLISSMARTLAB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnKirimLISSMARTLAB.setText("Kirim Permintaan ke SMARTLAB ");
        BtnKirimLISSMARTLAB.setFocusPainted(false);
        BtnKirimLISSMARTLAB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimLISSMARTLAB.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnKirimLISSMARTLAB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKirimLISSMARTLAB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnKirimLISSMARTLAB.setName("BtnKirimLISSMARTLAB"); // NOI18N
        BtnKirimLISSMARTLAB.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnKirimLISSMARTLAB.setRoundRect(false);
        BtnKirimLISSMARTLAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimLISSMARTLABActionPerformed(evt);
            }
        });
        FormMenu.add(BtnKirimLISSMARTLAB);

        BtnAmbilLISSMARTLAB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnAmbilLISSMARTLAB.setText("Ambil Hasil dari SMARTLAB");
        BtnAmbilLISSMARTLAB.setFocusPainted(false);
        BtnAmbilLISSMARTLAB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnAmbilLISSMARTLAB.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnAmbilLISSMARTLAB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAmbilLISSMARTLAB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        BtnAmbilLISSMARTLAB.setName("BtnAmbilLISSMARTLAB"); // NOI18N
        BtnAmbilLISSMARTLAB.setPreferredSize(new java.awt.Dimension(190, 23));
        BtnAmbilLISSMARTLAB.setRoundRect(false);
        BtnAmbilLISSMARTLAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAmbilLISSMARTLABActionPerformed(evt);
            }
        });
        FormMenu.add(BtnAmbilLISSMARTLAB);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.WEST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            pilihTab();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        pilihTab();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            pilihTab();
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            CrDokter.setText("");
            CrPoli.setText("");
            TCari.setText("");
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            CrDokter2.setText("");
            Kamar.setText("");
            TCari.setText("");
            pilihRanap();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(tabMode.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode.getRowCount()!=0){
                    
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    int row=tabMode.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                            tabMode.getValueAt(i,0).toString()+"','"+
                            tabMode.getValueAt(i,1).toString()+"','"+
                            tabMode.getValueAt(i,2).toString()+"','"+
                            tabMode.getValueAt(i,3).toString()+"','"+
                            tabMode.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode.getValueAt(i,8).toString()+"','"+
                            tabMode.getValueAt(i,9).toString()+"','"+
                            tabMode.getValueAt(i,10).toString()+"','"+
                            tabMode.getValueAt(i,11).toString()+"','"+
                            tabMode.getValueAt(i,12).toString()+"','"+
                            tabMode.getValueAt(i,13).toString()+"','"+
                            tabMode.getValueAt(i,14).toString()+"','"+
                            tabMode.getValueAt(i,15).toString()+"','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanLab.jasper","report","::[ Data Permintaan Laboratorium ]::",param);
                }
            }else if(TabRawatJalan.getSelectedIndex()==1){
                if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode2.getRowCount()!=0){
                    
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    int row=tabMode2.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode2.getValueAt(i,9).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode2.getValueAt(i,11).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                            tabMode2.getValueAt(i,0).toString()+"','"+
                            tabMode2.getValueAt(i,1).toString()+"','"+
                            tabMode2.getValueAt(i,2).toString()+"','"+
                            tabMode2.getValueAt(i,3).toString()+"','"+
                            tabMode2.getValueAt(i,4).toString()+"','"+
                            tabMode2.getValueAt(i,5).toString()+"','"+
                            tabMode2.getValueAt(i,6).toString()+"','"+
                            tabMode2.getValueAt(i,7).toString()+"','"+
                            tabMode2.getValueAt(i,8).toString()+"','"+
                            tglsampel+"','"+
                            tabMode2.getValueAt(i,10).toString()+"','"+
                            tglhasil+"','"+
                            tabMode2.getValueAt(i,12).toString()+"','"+
                            tabMode2.getValueAt(i,13).toString()+"','"+
                            tabMode2.getValueAt(i,14).toString()+"','"+
                            tabMode2.getValueAt(i,15).toString()+"','"+
                            tabMode2.getValueAt(i,16).toString()+"','"+
                            tabMode2.getValueAt(i,17).toString()+"','"+
                            tabMode2.getValueAt(i,18).toString()+"','"+
                            tabMode2.getValueAt(i,19).toString()+"','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanLab2.jasper","report","::[ Data Detail Permintaan Laboratorium ]::",param);
                }
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(tabMode3.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode3.getRowCount()!=0){
                    
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    int row=tabMode3.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode3.getValueAt(i,5).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode3.getValueAt(i,7).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                            tabMode3.getValueAt(i,0).toString()+"','"+
                            tabMode3.getValueAt(i,1).toString()+"','"+
                            tabMode3.getValueAt(i,2).toString()+"','"+
                            tabMode3.getValueAt(i,3).toString()+"','"+
                            tabMode3.getValueAt(i,4).toString()+"','"+
                            tglsampel+"','"+
                            tabMode3.getValueAt(i,6).toString()+"','"+
                            tglhasil+"','"+
                            tabMode3.getValueAt(i,8).toString()+"','"+
                            tabMode3.getValueAt(i,9).toString()+"','"+
                            tabMode3.getValueAt(i,10).toString()+"','"+
                            tabMode3.getValueAt(i,11).toString()+"','"+
                            tabMode3.getValueAt(i,12).toString()+"','"+
                            tabMode3.getValueAt(i,13).toString()+"','"+
                            tabMode3.getValueAt(i,14).toString()+"','"+
                            tabMode3.getValueAt(i,15).toString()+"','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanLab3.jasper","report","::[ Data Permintaan Laboratorium ]::",param);
                }
            }else if(TabRawatInap.getSelectedIndex()==1){
                if(tabMode4.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode4.getRowCount()!=0){
                    
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    int row=tabMode4.getRowCount();
                    for(i=0;i<row;i++){  
                        tglsampel="";
                        try {
                            tglsampel=tabMode4.getValueAt(i,9).toString();
                        } catch (Exception e) {
                            tglsampel="";
                        }
                        tglhasil="";
                        try {
                            tglhasil=tabMode4.getValueAt(i,11).toString();
                        } catch (Exception e) {
                            tglhasil="";
                        }
                        Sequel.menyimpan("temporary_permintaan_lab","'0','"+
                                        tabMode4.getValueAt(i,0).toString()+"','"+
                                        tabMode4.getValueAt(i,1).toString()+"','"+
                                        tabMode4.getValueAt(i,2).toString()+"','"+
                                        tabMode4.getValueAt(i,3).toString()+"','"+
                                        tabMode4.getValueAt(i,4).toString()+"','"+
                                        tabMode4.getValueAt(i,5).toString()+"','"+
                                        tabMode4.getValueAt(i,6).toString()+"','"+
                                        tabMode4.getValueAt(i,7).toString()+"','"+
                                        tabMode4.getValueAt(i,8).toString()+"','"+
                                        tglsampel+"','"+
                                        tabMode4.getValueAt(i,10).toString()+"','"+
                                        tglhasil+"','"+
                                        tabMode4.getValueAt(i,12).toString()+"','"+
                                        tabMode4.getValueAt(i,13).toString()+"','"+
                                        tabMode4.getValueAt(i,14).toString()+"','"+
                                        tabMode4.getValueAt(i,15).toString()+"','"+
                                        tabMode4.getValueAt(i,16).toString()+"','"+
                                        tabMode4.getValueAt(i,17).toString()+"','"+
                                        tabMode4.getValueAt(i,18).toString()+"','"+
                                        tabMode4.getValueAt(i,19).toString()+"','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptLapPermintaanLab4.jasper","report","::[ Data Detail Permintaan Laboratorium ]::",param);
                }
            }
        }                       
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowAmbilSampel.dispose();
        WindowTerkirim.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowAmbilSampel.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnHapus);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(TabPilihRawat.getSelectedIndex()==0){
        if(TabRawatJalan.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if((Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_lab where stts_bayar='Sudah' and noorder=?",NoPermintaan)+
                            Sequel.cariInteger("select count(noorder) from permintaan_detail_permintaan_lab where stts_bayar='Sudah' and noorder=?",NoPermintaan))>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                    }else{
                        Sequel.meghapus("permintaan_lab","noorder",NoPermintaan);
                        TeksKosong();
                        tampil();
                    }   
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatJalan.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatJalan.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }else if(TabPilihRawat.getSelectedIndex()==1){
        if(TabRawatInap.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{
                    if((Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_lab where stts_bayar='Sudah' and noorder=?",NoPermintaan)+
                            Sequel.cariInteger("select count(noorder) from permintaan_detail_permintaan_lab where stts_bayar='Sudah' and noorder=?",NoPermintaan))>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                    }else{
                        Sequel.meghapus("permintaan_lab","noorder",NoPermintaan);
                        TeksKosong();
                        tampil3();
                    }   
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }
        }else if(TabRawatInap.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Permintaan..!!!");
            TabRawatInap.setSelectedIndex(0);
            TCari.requestFocus();
        } 
    }                
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbLabRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabRalanMouseClicked
    if(tabMode.getRowCount()!=0){
        try {
            getData();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbLabRalanMouseClicked

private void tbLabRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabRalanKeyPressed
   if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbLabRalanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        pilihTab();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatJalanMouseClicked
        TeksKosong();
        pilihRalan();
    }//GEN-LAST:event_TabRawatJalanMouseClicked

    private void tbLabRalan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabRalan2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabRalan2MouseClicked

    private void tbLabRalan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabRalan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabRalan2KeyPressed

    private void BtnHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHasilActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrder(NoPermintaan,NoRawat,"Ralan");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrder(NoPermintaan,NoRawat,"Ranap");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }             
    }//GEN-LAST:event_BtnHasilActionPerformed

    private void BtnHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHasilKeyPressed

    private void BtnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSampelActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        TanggalPulang.setDate(new Date());
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                        WindowAmbilSampel.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                }   
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }                        
    }//GEN-LAST:event_BtnSampelActionPerformed

    private void BtnSampelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSampelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSampelKeyPressed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowAmbilSampel.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_lab","noorder=?","tgl_sampel=?,jam_sampel=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),NoPermintaan
                        })==true){
                            WindowAmbilSampel.dispose();
                            pilihan = (String)JOptionPane.showInputDialog(null,"Waktu pengambilan sampel berhasil disimpan, apakah ada yang ingin dicetak..?","Konfirmasi",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Tidak Ada","Barcode No.Permintaan 1","Barcode No.Permintaan 2","Lembar Permintaan Lab","Lembar Permintaan Lab & Barcode No.Permintaan 1","Lembar Permintaan Lab & Barcode No.Permintaan 2"},"Tidak Ada");
                            switch (pilihan) {
                                case "Tidak Ada":
                                    break;
                                case "Barcode No.Permintaan 1":
                                    BtnBarcodePermintaanActionPerformed(evt);
                                    break;
                                case "Barcode No.Permintaan 2":
                                    BtnBarcodePermintaan2ActionPerformed(evt);
                                    break;
                                case "Lembar Permintaan Lab":
                                    BtnCetakHasilLabActionPerformed(evt);
                                    break;
                                case "Lembar Permintaan Lab & Barcode No.Permintaan 1":
                                    BtnBarcodePermintaanActionPerformed(evt);
                                    getData();
                                    getData2();
                                    BtnCetakHasilLabActionPerformed(evt);
                                    break;
                                case "Lembar Permintaan Lab & Barcode No.Permintaan 2":
                                    BtnBarcodePermintaan2ActionPerformed(evt);
                                    getData();
                                    getData2();
                                    BtnCetakHasilLabActionPerformed(evt);
                                    break;
                            }  
                            TeksKosong();
                            tampil();
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }  
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TanggalPulang,"No.Permintaan");
                }else{
                    if(Sequel.mengedittf("permintaan_lab","noorder=?","tgl_sampel=?,jam_sampel=?",3,new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem()+""),TanggalPulang.getSelectedItem().toString().substring(11,19),NoPermintaan
                        })==true){
                            WindowAmbilSampel.dispose();
                            pilihan = (String)JOptionPane.showInputDialog(null,"Waktu pengambilan sampel berhasil disimpan, Apakah ada yang ingin dicetak..?","Konfirmasi",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Tidak Ada","Barcode No.Permintaan 1","Barcode No.Permintaan 2","Lembar Permintaan Lab & Barcode No.Permintaan 1","Lembar Permintaan Lab & Barcode No.Permintaan 2"},"Tidak Ada");
                            switch (pilihan) {
                                case "Tidak Ada":
                                    break;
                                case "Barcode No.Permintaan 1":
                                    BtnBarcodePermintaanActionPerformed(evt);
                                    break;
                                case "Barcode No.Permintaan 2":
                                    BtnBarcodePermintaan2ActionPerformed(evt);
                                    break;
                                case "Lembar Permintaan Lab & Barcode No.Permintaan 1":
                                    BtnBarcodePermintaanActionPerformed(evt);
                                    BtnCetakHasilLabActionPerformed(null);
                                    break;
                                case "Lembar Permintaan Lab & Barcode No.Permintaan 2":
                                    BtnBarcodePermintaan2ActionPerformed(evt);
                                    BtnCetakHasilLabActionPerformed(null);
                                    break;
                            }  
                            TeksKosong();
                            tampil3();
                    }
                }
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            }  
        }
            
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        ruang.isCek();
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void TabPilihRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihRawatMouseClicked
        TeksKosong();
        pilihTab();
    }//GEN-LAST:event_TabPilihRawatMouseClicked

    private void tbLabRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabRanapMouseClicked
        if(tabMode3.getRowCount()!=0){
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLabRanapMouseClicked

    private void tbLabRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabRanapKeyPressed
        if(tabMode3.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLabRanapKeyPressed

    private void tbLabRanap2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabRanap2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabRanap2MouseClicked

    private void tbLabRanap2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabRanap2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabRanap2KeyPressed

    private void TabRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatInapMouseClicked
        TeksKosong();
        pilihRanap();
    }//GEN-LAST:event_TabRawatInapMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnCetakHasilLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCetakHasilLabActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{  
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    try {
                        ps2=koneksi.prepareStatement(
                                "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                                "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                                "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                "where permintaan_pemeriksaan_lab.noorder=?");
                        try {
                            ps2.setString(1,NoPermintaan);
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    "0",rs2.getString("nm_perawatan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                });
                                ps3=koneksi.prepareStatement(
                                        "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                        "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                        "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                        "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                        "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(2,NoPermintaan);
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        la="";ld="";pa="";pd="";
                                        if(!rs3.getString("nilai_rujukan_ld").equals("")){
                                            ld="LD : "+rs3.getString("nilai_rujukan_ld");
                                        }
                                        if(!rs3.getString("nilai_rujukan_la").equals("")){
                                            la=", LA : "+rs3.getString("nilai_rujukan_la");
                                        }
                                        if(!rs3.getString("nilai_rujukan_pa").equals("")){
                                            pd=", PD : "+rs3.getString("nilai_rujukan_pd");
                                        }
                                        if(!rs3.getString("nilai_rujukan_pd").equals("")){
                                            pa=" PA : "+rs3.getString("nilai_rujukan_pa");
                                        }
                                        Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                            "0","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),ld+la+pd+pa,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }            
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa",NoPermintaan);
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("norm",norm);
                    param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",norm));
                    param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",norm));
                    param.put("namapasien",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                    param.put("umur",Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",norm));
                    param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ",norm));
                    param.put("pengirim",DokterPerujuk);
                    param.put("tanggal",Valid.SetTgl3(Permintaan));
                    param.put("alamat",Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",norm));
                    kamar="Poli";
                    namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat=?",NoRawat);
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    param.put("jam",JamPermintaan);
                    param.put("informasitambahan",InformasiTambahan);
                    param.put("diagnosa",DiagnosaKlinis);
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodeDokter);
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+DokterPerujuk+"\nID "+(finger.equals("")?KodeDokter:finger)+"\n"+Valid.SetTgl3(Permintaan)); 
            
                    Valid.MyReport("rptPermintaanLab.jasper","report","::[ Permintaan Laboratorium ]::",param);            
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    
                    Sequel.queryu("truncate table temporary_permintaan_lab");
                    try {
                        ps2=koneksi.prepareStatement(
                                "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                                "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                                "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                "where permintaan_pemeriksaan_lab.noorder=?");
                        try {
                            ps2.setString(1,NoPermintaan);
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                    "0",rs2.getString("nm_perawatan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                });
                                ps3=koneksi.prepareStatement(
                                        "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                        "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                        "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                        "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                        "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(2,NoPermintaan);
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        la="";ld="";pa="";pd="";
                                        if(!rs3.getString("nilai_rujukan_ld").equals("")){
                                            ld="LD : "+rs3.getString("nilai_rujukan_ld");
                                        }
                                        if(!rs3.getString("nilai_rujukan_la").equals("")){
                                            la=", LA : "+rs3.getString("nilai_rujukan_la");
                                        }
                                        if(!rs3.getString("nilai_rujukan_pa").equals("")){
                                            pd=", PD : "+rs3.getString("nilai_rujukan_pd");
                                        }
                                        if(!rs3.getString("nilai_rujukan_pd").equals("")){
                                            pa=" PA : "+rs3.getString("nilai_rujukan_pa");
                                        }
                                        Sequel.menyimpan("temporary_permintaan_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                            "0","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),ld+la+pd+pa,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }            
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa",NoPermintaan);
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("norm",norm);
                    param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",norm));
                    param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",norm));
                    param.put("namapasien",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                    param.put("umur",Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",norm));
                    param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ",norm));
                    param.put("pengirim",DokterPerujuk);
                    param.put("tanggal",Valid.SetTgl3(Permintaan));
                    param.put("alamat",Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",norm));
                    kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",NoRawat);
                    namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " where kamar.kd_kamar=? ",kamar);            
                    kamar="Kamar";  
                    param.put("kamar",kamar);
                    param.put("informasitambahan",InformasiTambahan);
                    param.put("diagnosa",DiagnosaKlinis);
                    param.put("namakamar",namakamar);
                    param.put("jam",JamPermintaan);
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodeDokter);
                    param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+DokterPerujuk+"\nID "+(finger.equals("")?KodeDokter:finger)+"\n"+Valid.SetTgl3(Permintaan)); 
            
                    Valid.MyReport("rptPermintaanLab.jasper","report","::[ Permintaan Laboratorium ]::",param);            
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }  
    }//GEN-LAST:event_BtnCetakHasilLabActionPerformed

    private void BtnBarcodePermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBarcodePermintaanActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanLab.jasper","report","::[ Barcode No.Permintaan Lab ]::",
                            "select noorder from permintaan_lab where no_rawat='"+NoRawat+"'",param); 
                    TeksKosong();
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanLab.jasper","report","::[ Barcode No.Permintaan Lab ]::",
                            "select noorder from permintaan_lab where no_rawat='"+NoRawat+"'",param); 
                    TeksKosong();
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnBarcodePermintaanActionPerformed

    private void BtnBarcodePermintaan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBarcodePermintaan2ActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanLab2.jasper","report","::[ Barcode No.Permintaan Lab ]::",
                            "select noorder from permintaan_lab where no_rawat='"+NoRawat+"'",param); 
                    TeksKosong();
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{ 
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",NoRawat);
                    param.put("nama",Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",norm));
                    param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                    param.put("norm",norm);
                    param.put("parameter","%"+TCari.getText().trim()+"%");     
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    Valid.MyReportqry("rptBarcodePermintaanLab2.jasper","report","::[ Barcode No.Permintaan Lab ]::",
                            "select noorder from permintaan_lab where no_rawat='"+NoRawat+"'",param); 
                    TeksKosong();
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        } 
    }//GEN-LAST:event_BtnBarcodePermintaan2ActionPerformed

    private void BtnKirimLicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimLicaActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    lica.kirimRalan(NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    lica.kirimRanap(NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimLicaActionPerformed

    private void BtnAmbilLicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilLicaActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        lica.ambil(NoPermintaan);
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek(); 
                        dlgro.setOrderLICA(NoPermintaan,NoRawat,"Ralan");
                        dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                        TeksKosong();
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        lica.ambil(NoPermintaan);
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek(); 
                        dlgro.setOrderLICA(NoPermintaan,NoRawat,"Ranap");
                        dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                        TeksKosong();
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }  
    }//GEN-LAST:event_BtnAmbilLicaActionPerformed

    private void BtnKirimSysmexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimSysmexActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksisysmex=koneksiDBSysmex.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"+
                            "if(pasien.jk='L','1','2') as jk,permintaan_lab.dokter_perujuk,dokter.nm_dokter,reg_periksa.kd_poli,"+
                            "poliklinik.nm_poli,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "where permintaan_lab.noorder=?");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                pilihan="R";
                                if(rs.getString("informasi_tambahan").toLowerCase().contains("cito")){
                                    pilihan="U";
                                }
                                Permintaan="";
                                ps2=koneksi.prepareStatement("select kd_jenis_prw from permintaan_pemeriksaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        Permintaan=rs2.getString("kd_jenis_prw")+"~"+Permintaan;
                                    }
                                    Permintaan=Permintaan.substring(0,Permintaan.length() - 1);
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
                                
                                koneksisysmex.prepareStatement(
                                        "insert into LIS_ORDER values('0',now(),'NW','"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"',"+
                                        "'"+rs.getString("alamat")+"','"+rs.getString("nm_kel")+"','"+rs.getString("nm_kec")+"','"+rs.getString("nm_kab")+"',"+
                                        "'OP','"+rs.getString("tgl_lahir")+"','"+rs.getString("jk")+"','"+rs.getString("noorder")+"','"+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"',"+
                                        "'"+rs.getString("kd_poli")+"^"+rs.getString("nm_poli")+"','"+rs.getString("dokter_perujuk")+"^"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("kd_poli")+"','"+pilihan+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("no_rawat")+"',"+
                                        "'"+Permintaan+"','0')").executeUpdate();
                                
                                Permintaan="";
                                ps2=koneksi.prepareStatement("select id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        Permintaan=rs2.getString("id_template")+"~"+Permintaan;
                                    }
                                    Permintaan=Permintaan.substring(0,Permintaan.length() - 1);
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
                                
                                koneksisysmex.prepareStatement(
                                        "insert into LIS_ORDER_DETAIL values('0',now(),'NW','"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"',"+
                                        "'"+rs.getString("alamat")+"','"+rs.getString("nm_kel")+"','"+rs.getString("nm_kec")+"','"+rs.getString("nm_kab")+"',"+
                                        "'OP','"+rs.getString("tgl_lahir")+"','"+rs.getString("jk")+"','"+rs.getString("noorder")+"','"+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"',"+
                                        "'"+rs.getString("kd_poli")+"^"+rs.getString("nm_poli")+"','"+rs.getString("dokter_perujuk")+"^"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("kd_poli")+"','"+pilihan+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("no_rawat")+"',"+
                                        "'"+Permintaan+"','0')").executeUpdate();
                            }
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksisysmex=koneksiDBSysmex.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,if(pasien.jk='L','1','2') as jk,"+
                            "permintaan_lab.dokter_perujuk,dokter.nm_dokter,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                            "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "where permintaan_lab.noorder=? group by permintaan_lab.noorder");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                pilihan="R";
                                if(rs.getString("informasi_tambahan").toLowerCase().contains("cito")){
                                    pilihan="U";
                                }
                                Permintaan="";
                                ps2=koneksi.prepareStatement("select kd_jenis_prw from permintaan_pemeriksaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        Permintaan=rs2.getString("kd_jenis_prw")+"~"+Permintaan;
                                    }
                                    Permintaan=Permintaan.substring(0,Permintaan.length() - 1);
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
                                
                                koneksisysmex.prepareStatement(
                                        "insert into LIS_ORDER values('0',now(),'NW','"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"',"+
                                        "'"+rs.getString("alamat")+"','"+rs.getString("nm_kel")+"','"+rs.getString("nm_kec")+"','"+rs.getString("nm_kab")+"',"+
                                        "'OP','"+rs.getString("tgl_lahir")+"','"+rs.getString("jk")+"','"+rs.getString("noorder")+"','"+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"',"+
                                        "'"+rs.getString("kd_kamar")+"^"+rs.getString("nm_bangsal")+"','"+rs.getString("dokter_perujuk")+"^"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("kd_kamar")+"','"+pilihan+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("no_rawat")+"',"+
                                        "'"+Permintaan+"','0')").executeUpdate();
                                
                                Permintaan="";
                                ps2=koneksi.prepareStatement("select id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        Permintaan=rs2.getString("id_template")+"~"+Permintaan;
                                    }
                                    Permintaan=Permintaan.substring(0,Permintaan.length() - 1);
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
                                
                                koneksisysmex.prepareStatement(
                                        "insert into LIS_ORDER_DETAIL values('0',now(),'NW','"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"',"+
                                        "'"+rs.getString("alamat")+"','"+rs.getString("nm_kel")+"','"+rs.getString("nm_kec")+"','"+rs.getString("nm_kab")+"',"+
                                        "'OP','"+rs.getString("tgl_lahir")+"','"+rs.getString("jk")+"','"+rs.getString("noorder")+"','"+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"',"+
                                        "'"+rs.getString("kd_kamar")+"^"+rs.getString("nm_bangsal")+"','"+rs.getString("dokter_perujuk")+"^"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("kd_kamar")+"','"+pilihan+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("no_rawat")+"',"+
                                        "'"+Permintaan+"','0')").executeUpdate();
                            }
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimSysmexActionPerformed

    private void BtnAmbilSysmexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilSysmexActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrderSysmex(NoPermintaan,NoRawat,"Ralan");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrderSysmex(NoPermintaan,NoRawat,"Ranap");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        } 
    }//GEN-LAST:event_BtnAmbilSysmexActionPerformed

    private void BtnKirimLISELIMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimLISELIMSActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksielims=koneksiDBELIMS.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,pasien.email,"+
                            "pasien.jk,permintaan_lab.dokter_perujuk,dokter.nm_dokter,reg_periksa.kd_poli,pasien.tmp_lahir,permintaan_lab.status,"+
                            "poliklinik.nm_poli,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis,reg_periksa.kd_pj,penjab.png_jawab "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where permintaan_lab.noorder=?");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                koneksielims.prepareStatement(
                                        "insert into permintaan_lab values('"+rs.getString("noorder")+"','"+rs.getString("no_rawat")+"','"+rs.getString("no_rkm_medis")+"',"+
                                        "'"+rs.getString("nm_pasien")+"','"+rs.getString("email")+"','"+rs.getString("jk")+"','"+rs.getString("tmp_lahir")+"','"+rs.getString("tgl_lahir")+"',"+
                                        "'"+rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop")+"',"+
                                        "'"+rs.getString("tgl_permintaan")+"','"+rs.getString("jam_permintaan")+"','"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("status")+"','"+rs.getString("kd_poli")+"','"+rs.getString("nm_poli")+"','"+rs.getString("kd_pj")+"','"+rs.getString("png_jawab")+"',"+
                                        "'"+rs.getString("informasi_tambahan")+"','"+rs.getString("diagnosa_klinis")+"')").executeUpdate();
                                ps2=koneksi.prepareStatement("select noorder, kd_jenis_prw, id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        koneksielims.prepareStatement("insert into detail_permintaan_lab values('"+rs2.getString("noorder")+"','"+rs2.getString("kd_jenis_prw")+"','"+rs2.getString("id_template")+"')").executeUpdate();
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
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksielims=koneksiDBELIMS.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,pasien.jk,pasien.tmp_lahir,"+
                            "permintaan_lab.dokter_perujuk,dokter.nm_dokter,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,permintaan_lab.informasi_tambahan,"+
                            "pasien.email,permintaan_lab.diagnosa_klinis,permintaan_lab.status,reg_periksa.kd_pj,penjab.png_jawab "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                            "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where permintaan_lab.noorder=? group by permintaan_lab.noorder");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                koneksielims.prepareStatement(
                                        "insert into permintaan_lab values('"+rs.getString("noorder")+"','"+rs.getString("no_rawat")+"','"+rs.getString("no_rkm_medis")+"',"+
                                        "'"+rs.getString("nm_pasien")+"','"+rs.getString("email")+"','"+rs.getString("jk")+"','"+rs.getString("tmp_lahir")+"','"+rs.getString("tgl_lahir")+"',"+
                                        "'"+rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop")+"',"+
                                        "'"+rs.getString("tgl_permintaan")+"','"+rs.getString("jam_permintaan")+"','"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("status")+"','"+rs.getString("kd_kamar")+"','"+rs.getString("nm_bangsal")+"','"+rs.getString("kd_pj")+"','"+rs.getString("png_jawab")+"',"+
                                        "'"+rs.getString("informasi_tambahan")+"','"+rs.getString("diagnosa_klinis")+"')").executeUpdate();
                                
                                ps2=koneksi.prepareStatement("select noorder, kd_jenis_prw, id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        koneksielims.prepareStatement("insert into detail_permintaan_lab values('"+rs2.getString("noorder")+"','"+rs2.getString("kd_jenis_prw")+"','"+rs2.getString("id_template")+"')").executeUpdate();
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
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimLISELIMSActionPerformed

    private void BtnAmbilLISELIMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilLISELIMSActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrderELIMS(NoPermintaan,NoRawat,"Ralan");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        if(Sampel.equals("")){
                            JOptionPane.showMessageDialog(rootPane,"Maaf, silahkan ambil sampel terlebih dahulu..!!");
                        }else{
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrderELIMS(NoPermintaan,NoRawat,"Ranap");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        } 
    }//GEN-LAST:event_BtnAmbilLISELIMSActionPerformed

    private void BtnKirimLISTerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimLISTerasActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"apiteras.php?nopermintaan="+NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"apiteras.php?nopermintaan="+NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimLISTerasActionPerformed

    private void BtnAmbilLISTerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilLISTerasActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"apiteras2.php?nopermintaan="+NoPermintaan);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        json="";
                        loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"apiteras2.php?nopermintaan="+NoPermintaan);
                        if(!json.equals("")){
                            try {
                                root=mapper.readTree(json);
                                response = root.path("hasil");
                                Sequel.queryu("truncate table temporary_permintaan_lab");
                                if(response.isArray()){
                                    for(JsonNode list:response){
                                        Sequel.menyimpan("temporary_permintaan_lab","'0','"+root.path("idi").asText()+"','"+
                                                list.path("namatest").asText()+"','"+
                                                list.path("hasil").asText()+"','"+
                                                list.path("refer").asText()+"','"+
                                                list.path("unit").asText()+"','"+
                                                list.path("ac").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Notif : "+ex);
                            }
                            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            dlgro.setLocationRelativeTo(internalFrame1);
                            dlgro.emptTeks();
                            dlgro.isCek(); 
                            dlgro.setOrderLICA(NoPermintaan,NoRawat,"Ranap");
                            dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                            TeksKosong();
                            dlgro.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }  
    }//GEN-LAST:event_BtnAmbilLISTerasActionPerformed

    private void BtnKirimLISMADQLABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimLISMADQLABActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    medqlab.kirimRalan(NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    medqlab.kirimRanap(NoPermintaan);
                }
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimLISMADQLABActionPerformed

    private void BtnAmbilLISMADQLABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilLISMADQLABActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(TabRawatJalan.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        medqlab.ambil(NoPermintaan.substring(4,14));
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek(); 
                        dlgro.setOrderMEDQLAB(NoPermintaan,NoRawat,"Ralan");
                        dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                        TeksKosong();
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatJalan.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatJalan.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(TabRawatInap.getSelectedIndex()==0){
                if(!NoRawat.equals("")){
                    if(NoPermintaan.trim().equals("")){
                        Valid.textKosong(TCari,"No.Permintaan");
                    }else{ 
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        medqlab.ambil(NoPermintaan.substring(4,14));
                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek(); 
                        dlgro.setOrderMEDQLAB(NoPermintaan,NoRawat,"Ralan");
                        dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                        TeksKosong();
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }else{            
                    JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                    TCari.requestFocus();
                } 
            }else if(TabRawatInap.getSelectedIndex()==1){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih Data Permintaan...!!!!");
                TabRawatInap.setSelectedIndex(0);
                TCari.requestFocus();
            }
        }  
    }//GEN-LAST:event_BtnAmbilLISMADQLABActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        WindowTerkirim.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BtnKirimLISSMARTLABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimLISSMARTLABActionPerformed
        if(TabPilihRawat.getSelectedIndex()==0){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksismartlab=koneksiDBSMARTLAB.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,pasien.email,"+
                            "pasien.jk,permintaan_lab.dokter_perujuk,dokter.nm_dokter,reg_periksa.kd_poli,pasien.tmp_lahir,permintaan_lab.status,"+
                            "poliklinik.nm_poli,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis,reg_periksa.kd_pj,penjab.png_jawab "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where permintaan_lab.noorder=?");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                koneksismartlab.prepareStatement(
                                        "insert into HisToLisHeader values('0','"+rs.getString("no_rawat")+"','"+rs.getString("noorder")+"','Rawat Jalan',"+
                                        "'"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"','"+rs.getString("jk")+"','"+rs.getString("tgl_lahir")+"',"+
                                        "'"+rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop")+"',"+
                                        "'"+rs.getString("kd_poli")+"','"+rs.getString("nm_poli")+"','"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("png_jawab")+"',"+
                                        "'Ralan','0','"+Sequel.cariIsi("select count(noorder) from permintaan_detail_permintaan_lab where noorder='"+rs.getString("noorder")+"'")+"',"+
                                        "'0','"+rs.getString("noorder")+"','"+rs.getString("nm_dokter")+"','A','"+rs.getString("nm_poli")+"','N','0','"+rs.getString("tgl_permintaan")+"',"+
                                        "'"+rs.getString("tgl_permintaan")+"')").executeUpdate();
                                try {
                                    rs3=koneksismartlab.prepareStatement("select RowID from HisToLisHeader where HisNoOrder='"+rs.getString("noorder")+"'").executeQuery();
                                    if(rs3.next()){
                                        i=rs3.getInt("RowID");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }
                                ps2=koneksi.prepareStatement("select noorder, kd_jenis_prw, id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        koneksismartlab.prepareStatement("insert HisToLisDetail values('0','"+i+"', '"+rs2.getString("id_template")+"','N','"+rs.getString("tgl_permintaan")+"','0')").executeUpdate();
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
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }else if(TabPilihRawat.getSelectedIndex()==1){
            if(!NoRawat.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(NoPermintaan.trim().equals("")){
                    Valid.textKosong(TCari,"No.Permintaan");
                }else{   
                    try {
                        koneksismartlab=koneksiDBSMARTLAB.condb();
                        ps=koneksi.prepareStatement(
                            "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,pasien.tgl_lahir,"+
                            "permintaan_lab.jam_permintaan,pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,pasien.jk,pasien.tmp_lahir,"+
                            "permintaan_lab.dokter_perujuk,dokter.nm_dokter,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,permintaan_lab.informasi_tambahan,"+
                            "pasien.email,permintaan_lab.diagnosa_klinis,permintaan_lab.status,reg_periksa.kd_pj,penjab.png_jawab,kamar.kelas "+
                            "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                            "inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                            "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where permintaan_lab.noorder=? group by permintaan_lab.noorder");
                        try {
                            ps.setString(1,NoPermintaan);
                            rs=ps.executeQuery();
                            if(rs.next()){
                                koneksismartlab.prepareStatement(
                                        "insert into HisToLisHeader values('0','"+rs.getString("no_rawat")+"','"+rs.getString("noorder")+"','Rawat Inap',"+
                                        "'"+rs.getString("no_rkm_medis")+"','"+rs.getString("nm_pasien")+"','"+rs.getString("jk")+"','"+rs.getString("tgl_lahir")+"',"+
                                        "'"+rs.getString("alamat")+", "+rs.getString("nm_kel")+", "+rs.getString("nm_kec")+", "+rs.getString("nm_kab")+", "+rs.getString("nm_prop")+"',"+
                                        "'"+rs.getString("kd_bangsal")+"','"+rs.getString("nm_bangsal")+"','"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"',"+
                                        "'"+rs.getString("dokter_perujuk")+"','"+rs.getString("nm_dokter")+"','"+rs.getString("diagnosa_klinis")+"','"+rs.getString("png_jawab")+"',"+
                                        "'"+rs.getString("kelas")+"','0','"+Sequel.cariIsi("select count(noorder) from permintaan_detail_permintaan_lab where noorder='"+rs.getString("noorder")+"'")+"',"+
                                        "'0','"+rs.getString("noorder")+"','"+rs.getString("nm_dokter")+"','A','"+rs.getString("kd_kamar")+"','N','0','"+rs.getString("tgl_permintaan")+"',"+
                                        "'"+rs.getString("tgl_permintaan")+"')").executeUpdate();
                                try {
                                    rs3=koneksismartlab.prepareStatement("select RowID from HisToLisHeader where HisNoOrder='"+rs.getString("noorder")+"'").executeQuery();
                                    if(rs3.next()){
                                        i=rs3.getInt("RowID");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }
                                ps2=koneksi.prepareStatement("select noorder, kd_jenis_prw, id_template from permintaan_detail_permintaan_lab where noorder=?");
                                try {
                                    ps2.setString(1,NoPermintaan);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        koneksismartlab.prepareStatement("insert HisToLisDetail values('0','"+i+"', '"+rs2.getString("id_template")+"','N','"+rs.getString("tgl_permintaan")+"','0')").executeUpdate();
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
                            JOptionPane.showMessageDialog(null,"Proses kirim berhasil..!!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"Proses kirim gagal..!!"+e);
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
                TeksKosong();
                this.setCursor(Cursor.getDefaultCursor());
            }else{            
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data permintaan...!!!!");
                TCari.requestFocus();
            } 
        }
    }//GEN-LAST:event_BtnKirimLISSMARTLABActionPerformed

    private void BtnAmbilLISSMARTLABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAmbilLISSMARTLABActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAmbilLISSMARTLABActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanLab dialog = new DlgCariPermintaanLab(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAmbilLISELIMS;
    private widget.Button BtnAmbilLISMADQLAB;
    private widget.Button BtnAmbilLISSMARTLAB;
    private widget.Button BtnAmbilLISTeras;
    private widget.Button BtnAmbilLica;
    private widget.Button BtnAmbilSysmex;
    private widget.Button BtnBarcodePermintaan;
    private widget.Button BtnBarcodePermintaan2;
    private widget.Button BtnCari;
    private widget.Button BtnCetakHasilLab;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnHasil;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirimLISELIMS;
    private widget.Button BtnKirimLISMADQLAB;
    private widget.Button BtnKirimLISSMARTLAB;
    private widget.Button BtnKirimLISTeras;
    private widget.Button BtnKirimLica;
    private widget.Button BtnKirimSysmex;
    private widget.Button BtnPrint;
    private widget.Button BtnSampel;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSimpan4;
    private widget.CekBox ChkAccor;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter2;
    private widget.TextBox CrPoli;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Kamar;
    private widget.Label LCount;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabPilihRawat;
    private javax.swing.JTabbedPane TabRawatInap;
    private javax.swing.JTabbedPane TabRawatJalan;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowAmbilSampel;
    private javax.swing.JDialog WindowTerkirim;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel26;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbLabRalan;
    private widget.Table tbLabRalan2;
    private widget.Table tbLabRanap;
    private widget.Table tbLabRanap2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                    "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where permintaan_lab.status='ralan' and permintaan_lab.tgl_permintaan between ? and ? "+
                    (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                    "(permintaan_lab.noorder like ? or permintaan_lab.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or permintaan_lab.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+
                    "order by permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                    ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                }   
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_poli"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                            "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","Nilai Rujukan L.D.","Nilai Rujukan L.A.","Nilai Rujukan P.D.","Nilai Rujukan P.A.","",""
                            });
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                    "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                ps3.setString(2,rs.getString("noorder"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    tabMode.addRow(new Object[]{
                                        "","","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),"","","","","","",rs3.getString("nilai_rujukan_ld"),rs3.getString("nilai_rujukan_la"),rs3.getString("nilai_rujukan_pd"),rs3.getString("nilai_rujukan_pa"),"",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);  
        try {
            semua=CrDokter.getText().trim().equals("")&&CrPoli.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                "pasien.nm_pasien,jns_perawatan_lab.nm_perawatan,template_laboratorium.Pemeriksaan,"+
                "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,reg_periksa.kd_pj,"+
                "template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,"+
                "template_laboratorium.nilai_rujukan_pa,permintaan_lab.tgl_permintaan,penjab.png_jawab,"+
                "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,permintaan_lab.tgl_sampel,"+
                "if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel, permintaan_lab.tgl_hasil,"+
                "if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                "permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join permintaan_pemeriksaan_lab on permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder "+
                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw "+
                "inner join permintaan_detail_permintaan_lab on permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder and permintaan_detail_permintaan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw "+
                "inner join template_laboratorium on template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template "+
                "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                "where permintaan_lab.status='ralan' and permintaan_lab.tgl_permintaan between ? and ? "+
                (semua?"":"and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and "+
                "(permintaan_lab.noorder like ? or permintaan_lab.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                "pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or template_laboratorium.Pemeriksaan like ? or "+
                "permintaan_lab.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+
                "order by permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter.getText().trim()+"%");
                    ps.setString(4,"%"+CrPoli.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,"%"+TCari.getText()+"%");
                }
                
                rs=ps.executeQuery();
                while(rs.next()){
                    la="";ld="";pa="";pd="";
                    if(!rs.getString("nilai_rujukan_ld").equals("")){
                        ld="LD : "+rs.getString("nilai_rujukan_ld");
                    }
                    if(!rs.getString("nilai_rujukan_la").equals("")){
                        la=", LA : "+rs.getString("nilai_rujukan_la");
                    }
                    if(!rs.getString("nilai_rujukan_pa").equals("")){
                        pd=", PD : "+rs.getString("nilai_rujukan_pd");
                    }
                    if(!rs.getString("nilai_rujukan_pd").equals("")){
                        pa=" PA : "+rs.getString("nilai_rujukan_pa");
                    }
                    tabMode2.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_perawatan"),rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,
                        rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),rs.getString("tgl_sampel"),
                        rs.getString("jam_sampel"),rs.getString("tgl_hasil"),rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),
                        rs.getString("nm_dokter"),rs.getString("nm_poli"),rs.getString("informasi_tambahan"),
                        rs.getString("diagnosa_klinis"),rs.getString("kd_pj"),rs.getString("png_jawab")
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
            LCount.setText(""+tabMode2.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    
    private void getData() {
        if(tbLabRalan.getSelectedRow()!= -1){
            NoPermintaan=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),0).toString();
            NoRawat=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),1).toString();
            Pasien=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),2).toString();
            Permintaan=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),3).toString();
            JamPermintaan=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),4).toString();
            Sampel=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),5).toString();
            JamSampel=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),6).toString();
            Hasil=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),7).toString();
            JamHasil=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),8).toString();
            KodeDokter=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),9).toString();
            DokterPerujuk=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),10).toString();
            Ruang=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),11).toString();
            InformasiTambahan=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),12).toString();
            DiagnosaKlinis=tbLabRalan.getValueAt(tbLabRalan.getSelectedRow(),13).toString();
        }
    }
    
    private void getData2() {
        if(tbLabRanap.getSelectedRow()!= -1){
            NoPermintaan=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),0).toString();
            NoRawat=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),1).toString();
            Pasien=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),2).toString();
            Permintaan=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),3).toString();
            JamPermintaan=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),4).toString();
            Sampel=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),5).toString();
            JamSampel=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),6).toString();
            Hasil=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),7).toString();
            JamHasil=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),8).toString();
            KodeDokter=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),9).toString();
            DokterPerujuk=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),10).toString();
            Ruang=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),11).toString();
            InformasiTambahan=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),12).toString();
            DiagnosaKlinis=tbLabRanap.getValueAt(tbLabRanap.getSelectedRow(),13).toString();
        }
    }
    
    private void TeksKosong() {
        NoPermintaan="";
        NoRawat="";
        Pasien="";
        Permintaan="";
        JamPermintaan="";
        Sampel="";
        JamSampel="";
        Hasil="";
        JamHasil="";
        KodeDokter="";
        DokterPerujuk="";
        Ruang="";
    }
    
    public void isCek(){
        BtnCetakHasilLab.setEnabled(akses.getpermintaan_lab());
        BtnHasil.setEnabled(akses.getperiksa_lab());
        BtnSampel.setEnabled(akses.getperiksa_lab());
        BtnHapus.setEnabled(akses.getpermintaan_lab());
        BtnPrint.setEnabled(akses.getpermintaan_lab());
        BtnKirimLica.setEnabled(akses.getpermintaan_lab());
        BtnAmbilLica.setEnabled(akses.getperiksa_lab());
        BtnKirimSysmex.setEnabled(akses.getpermintaan_lab());
        BtnAmbilSysmex.setEnabled(akses.getperiksa_lab());
        BtnKirimLISTeras.setEnabled(akses.getpermintaan_lab());
        BtnAmbilLISTeras.setEnabled(akses.getperiksa_lab());
        BtnKirimLISMADQLAB.setEnabled(akses.getpermintaan_lab());
        BtnAmbilLISMADQLAB.setEnabled(akses.getperiksa_lab());
    }
    
    public void setPasien(String pasien){
        TCari.setText(pasien);
    }

    public void pilihTab(){
        if(TabPilihRawat.getSelectedIndex()==0){
            pilihRalan();
        }else if(TabPilihRawat.getSelectedIndex()==1){
            pilihRanap();
        }
    }
    
    public void pilihRalan(){
        if(TabRawatJalan.getSelectedIndex()==0){
            tampil();
        }else if(TabRawatJalan.getSelectedIndex()==1){
            tampil2();
        }
    }
    
    public void pilihRanap(){
        if(TabRawatInap.getSelectedIndex()==0){
            tampil3();
        }else if(TabRawatInap.getSelectedIndex()==1){
            tampil4();
        }
    }
    
    private void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,reg_periksa.kd_pj,penjab.png_jawab,"+
                "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                "permintaan_lab.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter "+
                "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                "where permintaan_lab.status='ranap' and permintaan_lab.tgl_permintaan between ? and ? "+
                (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                "(permintaan_lab.noorder like ? or permintaan_lab.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                "pasien.nm_pasien like ? or permintaan_lab.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ? )")+
                "group by permintaan_lab.noorder order by permintaan_lab.status='ranap' and permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode3.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+
                        rs.getString("nm_pasien"),rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),rs.getString("jam_sampel"),rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_bangsal"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on "+
                            "permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=?");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode3.addRow(new Object[]{
                                "","",rs2.getString("nm_perawatan"),"","","","","","","","Nilai Rujukan L.D.","Nilai Rujukan L.A.","Nilai Rujukan P.D.","Nilai Rujukan P.A.","",""
                            });
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,"+
                                    "template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.kd_jenis_prw=? and permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1,rs2.getString("kd_jenis_prw"));
                                ps3.setString(2,rs.getString("noorder"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    tabMode3.addRow(new Object[]{
                                        "","","  "+rs3.getString("Pemeriksaan"),rs3.getString("satuan"),"","","","","","",rs3.getString("nilai_rujukan_ld"),rs3.getString("nilai_rujukan_la"),rs3.getString("nilai_rujukan_pd"),rs3.getString("nilai_rujukan_pa"),"",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText(""+rs.getRow());
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
 
    private void tampil4() {
        Valid.tabelKosong(tabMode4);  
        try {
            semua=CrDokter2.getText().trim().equals("")&&Kamar.getText().trim().equals("")&&TCari.getText().trim().equals("");
            ps=koneksi.prepareStatement(
                    "select distinct permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,jns_perawatan_lab.nm_perawatan,template_laboratorium.Pemeriksaan,"+
                    "template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,reg_periksa.kd_pj,"+
                    "template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,"+
                    "template_laboratorium.nilai_rujukan_pa,permintaan_lab.tgl_permintaan,penjab.png_jawab,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,permintaan_lab.tgl_sampel,"+
                    "if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel, permintaan_lab.tgl_hasil,"+
                    "if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,ifnull(bangsal.nm_bangsal,'Ranap Gabung') as nm_bangsal,permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis "+
                    "from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join permintaan_pemeriksaan_lab on permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder "+
                    "left join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                    "left join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "left join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw "+
                    "inner join permintaan_detail_permintaan_lab on permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder and permintaan_detail_permintaan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw  "+
                    "inner join template_laboratorium on template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template "+
                    "inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter  "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where permintaan_lab.status='ranap' and permintaan_lab.tgl_permintaan between ? and ? "+
                    (semua?"":"and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and "+
                    "(permintaan_lab.noorder like ? or permintaan_lab.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or template_laboratorium.Pemeriksaan like ? or "+
                    "permintaan_lab.diagnosa_klinis like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?)")+
                    "group by permintaan_lab.noorder,permintaan_detail_permintaan_lab.id_template order by permintaan_lab.tgl_permintaan,permintaan_lab.jam_permintaan desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!semua){
                    ps.setString(3,"%"+CrDokter2.getText().trim()+"%");
                    ps.setString(4,"%"+Kamar.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    la="";ld="";pa="";pd="";
                    if(!rs.getString("nilai_rujukan_ld").equals("")){
                        ld="LD : "+rs.getString("nilai_rujukan_ld");
                    }
                    if(!rs.getString("nilai_rujukan_la").equals("")){
                        la=", LA : "+rs.getString("nilai_rujukan_la");
                    }
                    if(!rs.getString("nilai_rujukan_pa").equals("")){
                        pd=", PD : "+rs.getString("nilai_rujukan_pd");
                    }
                    if(!rs.getString("nilai_rujukan_pd").equals("")){
                        pa=" PA : "+rs.getString("nilai_rujukan_pa");
                    }
                    tabMode4.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_perawatan"),rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,
                        rs.getString("tgl_permintaan"),rs.getString("jam_permintaan"),rs.getString("tgl_sampel"),rs.getString("jam_sampel"),
                        rs.getString("tgl_hasil"),rs.getString("jam_hasil"),rs.getString("dokter_perujuk"),rs.getString("nm_dokter"),
                        rs.getString("nm_bangsal"),rs.getString("informasi_tambahan"),rs.getString("diagnosa_klinis"),
                        rs.getString("kd_pj"),rs.getString("png_jawab")
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
            LCount.setText(""+tabMode4.getRowCount());
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    permintaanbaru=0;
                    if(formalarm.contains("ralan")){
                        tampil();
                        for(i=0;i<tbLabRalan.getRowCount();i++){
                            if((!tbLabRalan.getValueAt(i,0).toString().equals(""))&&tbLabRalan.getValueAt(i,5).toString().equals("")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(formalarm.contains("ranap")){
                        tampil3();
                        for(i=0;i<tbLabRanap.getRowCount();i++){
                            if((!tbLabRanap.getValueAt(i,0).toString().equals(""))&&tbLabRanap.getValueAt(i,5).toString().equals("")){
                                permintaanbaru++;
                            }
                        }
                    }

                    if(permintaanbaru>0){
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }                
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void loadURL(String url) {
        try {
            createScene();
        } catch (Exception e) {
        }
        
        Platform.runLater(() -> {
            try {
                engine.load(url);
            }catch (Exception exception) {
                engine.load(url);
            }
        });   
    } 
    
    private void createScene() {        
        Platform.runLater(new Runnable() {

            public void run() {
                WebView view = new WebView();
                
                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);
                
                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });
                
                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    null,
                                    (value != null) ?
                                            engine.getLocation() + "\n" + value.getMessage() :
                                            engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            try {
                                System.out.println("Eksekusi URL : "+engine.getLocation());
                                if(engine.getLocation().contains("SuksesKirim")){
                                    WindowTerkirim.setLocationRelativeTo(null);
                                    WindowTerkirim.setVisible(true);
                                }else if(engine.getLocation().contains("json")){
                                    json="";
                                    json=engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/json=","").replaceAll("_"," ");
                                    if(!json.equals("")){
                                        try {
                                            root=mapper.readTree(json);
                                            response = root.path("hasil");
                                            Sequel.queryu("truncate table temporary_permintaan_lab");
                                            if(response.isArray()){
                                                for(JsonNode list:response){
                                                    Sequel.menyimpan("temporary_permintaan_lab","'0','"+root.path("idi").asText()+"','"+
                                                            list.path("namatest").asText()+"','"+
                                                            list.path("hasil").asText()+"','"+
                                                            list.path("refer").asText()+"','"+
                                                            list.path("unit").asText()+"','"+
                                                            list.path("ac").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                                                }
                                            }
                                        } catch (Exception ex) {
                                            System.out.println("Notif : "+ex);
                                        }
                                        DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                                        dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                        dlgro.setLocationRelativeTo(internalFrame1);
                                        dlgro.emptTeks();
                                        dlgro.isCek(); 
                                        dlgro.setOrderLICA(NoPermintaan,NoRawat,"Ralan");
                                        dlgro.setDokterPerujuk(KodeDokter,DokterPerujuk);
                                        TeksKosong();
                                        dlgro.setVisible(true);
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : "+ex);
                            }
                        }
                    }
                });
            }
        });
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(205,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }
    
}

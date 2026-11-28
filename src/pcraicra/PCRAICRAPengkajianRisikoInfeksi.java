package pcraicra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class PCRAICRAPengkajianRisikoInfeksi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeArea,tabModePengendalian,
            tabModeAreaData,tabModePengendalianData;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0,index=0;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private StringBuilder htmlContent;
    private File file;
    private FileWriter fileWriter;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private boolean[] pilih; 
    private String[] kode,nama,ket,rencana,pic;
    private PCRAICRALokasiKelompokRisikoArea lokasiarea;
    private PCRAICRATindakanPengendalian tindakanpengendalian;
    private PCRAICRACariPCRA caripcra;
    private PCRAICRACariJenisAktivitasProyek aktivitasproyek;
    private PCRAICRACariKelasRisikoPencegahan kelasrisikopencegahan;
    private DlgCariPegawai pegawai;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PCRAICRAPengkajianRisikoInfeksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.ICRA","No.PCRA","Nama Proyek","Lokasi Proyek","Pelaksana","Mulai","Selesai","Uraian Pekerjaan",
                "Tgl.Pengkajian","Lokasi/Ruang Sekitar","Rute Pekerja/Material","Jam Mulai","Jam Selesai","Waktu Kerja","Area Beroperasi","Ket. Operasi",
                "Kode Risiko","Risiko Pasien","Kode Aktivitas","Tipe Pekerjaan","Kode Kewaspadaan","Kelas ICRA","Perlu Izin","Alasan Penetapan Kelas",
                "Keputusan","Persyaratan/Catatan","NIP Pemohon","Pemohon","NIP IPSRS","IPSRS","NIP K3RS","K3RS",
                "NIP PPI","Tim PPI","NIP Manajemen","Manajemen"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(95);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(220);
            }else if(i==8){
                column.setPreferredWidth(125);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(110);
            }else if(i==18){
                column.setPreferredWidth(78);
            }else if(i==19){
                column.setPreferredWidth(220);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(60);
            }else if(i==23){
                column.setPreferredWidth(200);
            }else if(i==24){
                column.setPreferredWidth(170);
            }else if(i==25){
                column.setPreferredWidth(200);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(140);
            }else if(i==28){
                column.setPreferredWidth(90);
            }else if(i==29){
                column.setPreferredWidth(140);
            }else if(i==30){
                column.setPreferredWidth(90);
            }else if(i==31){
                column.setPreferredWidth(140);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(140);
            }else if(i==34){
                column.setPreferredWidth(90);
            }else if(i==35){
                column.setPreferredWidth(140);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeAreaData=new DefaultTableModel(null,new Object[]{"Kode","Kelompok Pasien / Area","Keterangan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbAreaData.setModel(tabModeAreaData);
        tbAreaData.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAreaData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 3; i++) {
            TableColumn column = tbAreaData.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }
        }
        tbAreaData.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePengendalianData=new DefaultTableModel(null,new Object[]{"Kode","Tindakan Pengendalian","Rencana Pengendalian","PIC"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengendalianData.setModel(tabModePengendalianData);
        tbPengendalianData.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengendalianData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 4; i++) {
            TableColumn column = tbPengendalianData.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(190);
            }else if(i==3){
                column.setPreferredWidth(90);
            }
        }
        tbPengendalianData.setDefaultRenderer(Object.class, new WarnaTable());
        
        ChkAccor.setSelected(false);
        isMenu();

        tabModeArea=new DefaultTableModel(null,new Object[]{"P","Kode","Kelompok Pasien / Area","Keterangan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0||colIndex==3) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbArea.setModel(tabModeArea);
        tbArea.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbArea.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 4; i++) {
            TableColumn column = tbArea.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(500);
            }
        }
        tbArea.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePengendalian=new DefaultTableModel(null,new Object[]{"P","Kode","Tindakan Pengendalian","Rencana Pengendalian","PIC"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0||colIndex==3||colIndex==4) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbPengendalian.setModel(tabModePengendalian);
        tbPengendalian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengendalian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbPengendalian.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(220);
            }else if(i==3){
                column.setPreferredWidth(400);
            }else if(i==4){
                column.setPreferredWidth(180);
            }
        }
        tbPengendalian.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TCariArea.setDocument(new batasInput((int)100).getKata(TCariArea));
        TCariPengendalian.setDocument(new batasInput((int)100).getKata(TCariPengendalian));
        NoICRA.setDocument(new batasInput((int)20).getKata(NoICRA));
        LokasiSekitar.setDocument(new batasInput((int)100).getKata(LokasiSekitar));
        RutePekerja.setDocument(new batasInput((int)150).getKata(RutePekerja));
        KeteranganOperasi.setDocument(new batasInput((int)100).getKata(KeteranganOperasi));
        AlasanKelas.setDocument(new batasInput((int)250).getKata(AlasanKelas));
        Persyaratan.setDocument(new batasInput((int)250).getKata(Persyaratan));
        
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
        jPopupMenu1 = new widget.PopupMenu();
        MnFormulir = new widget.MenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new widget.TabPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabelICRAData = new widget.Label();
        TNoICRAData = new widget.TextBox();
        TNamaProyekData = new widget.TextBox();
        scrollRelasiData = new widget.ScrollPane();
        FormRelasiData = new widget.PanelBiasa();
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
        icLabel1 = new widget.Label();
        NoICRA = new widget.TextBox();
        icLabel2 = new widget.Label();
        TanggalPengkajian = new widget.Tanggal();
        icLabel3 = new widget.Label();
        NoPCRA = new widget.TextBox();
        BtnPCRA = new widget.Button();
        icLabel4 = new widget.Label();
        NamaProyek = new widget.TextBox();
        icLabel5 = new widget.Label();
        LokasiProyek = new widget.TextBox();
        icLabel6 = new widget.Label();
        Pelaksana = new widget.TextBox();
        icLabel7 = new widget.Label();
        ProyekMulai = new widget.TextBox();
        icLabel8 = new widget.Label();
        ProyekSelesai = new widget.TextBox();
        icLabel9 = new widget.Label();
        UraianPekerjaan = new widget.TextBox();
        icSep1 = new javax.swing.JSeparator();
        icLabel10 = new widget.Label();
        icLabel11 = new widget.Label();
        LokasiSekitar = new widget.TextBox();
        icLabel12 = new widget.Label();
        RutePekerja = new widget.TextBox();
        icLabel13 = new widget.Label();
        JamMulaiJam = new widget.ComboBox();
        icLabel14 = new widget.Label();
        JamMulaiMenit = new widget.ComboBox();
        icLabel15 = new widget.Label();
        JamSelesaiJam = new widget.ComboBox();
        icLabel16 = new widget.Label();
        JamSelesaiMenit = new widget.ComboBox();
        WaktuKerja = new widget.ComboBox();
        icLabel17 = new widget.Label();
        AreaBeroperasi = new widget.ComboBox();
        icLabel18 = new widget.Label();
        KeteranganOperasi = new widget.TextBox();
        icSep2 = new javax.swing.JSeparator();
        icLabel19 = new widget.Label();
        icLabel20 = new widget.Label();
        icLabel21 = new widget.Label();
        TCariArea = new widget.TextBox();
        BtnCariArea = new widget.Button();
        BtnAllArea = new widget.Button();
        BtnTambahArea = new widget.Button();
        ScrollArea = new widget.ScrollPane();
        tbArea = new widget.Table();
        icSep3 = new javax.swing.JSeparator();
        icLabel22 = new widget.Label();
        icLabel23 = new widget.Label();
        KodeAktivitas = new widget.TextBox();
        NamaAktivitas = new widget.TextBox();
        BtnAktivitas = new widget.Button();
        icLabel24 = new widget.Label();
        KodeRisiko = new widget.TextBox();
        NamaRisiko = new widget.TextBox();
        BtnRisiko = new widget.Button();
        icLabel25 = new widget.Label();
        KodeKewaspadaan = new widget.TextBox();
        NamaKewaspadaan = new widget.TextBox();
        icLabel26 = new widget.Label();
        PerluIzin = new widget.TextBox();
        icLabel27 = new widget.Label();
        AlasanKelas = new widget.TextBox();
        icSep4 = new javax.swing.JSeparator();
        icLabel28 = new widget.Label();
        icLabel29 = new widget.Label();
        icLabel30 = new widget.Label();
        TCariPengendalian = new widget.TextBox();
        BtnCariPengendalian = new widget.Button();
        BtnAllPengendalian = new widget.Button();
        BtnTambahPengendalian = new widget.Button();
        ScrollPengendalian = new widget.ScrollPane();
        tbPengendalian = new widget.Table();
        icSep5 = new javax.swing.JSeparator();
        icLabel31 = new widget.Label();
        icLabel32 = new widget.Label();
        Keputusan = new widget.ComboBox();
        icLabel33 = new widget.Label();
        Persyaratan = new widget.TextBox();
        icLabel34 = new widget.Label();
        KodePemohon = new widget.TextBox();
        NamaPemohon = new widget.TextBox();
        BtnPemohon = new widget.Button();
        icLabel35 = new widget.Label();
        KodeIPSRS = new widget.TextBox();
        NamaIPSRS = new widget.TextBox();
        BtnIPSRS = new widget.Button();
        icLabel36 = new widget.Label();
        KodeK3RS = new widget.TextBox();
        NamaK3RS = new widget.TextBox();
        BtnK3RS = new widget.Button();
        icLabel37 = new widget.Label();
        KodePPI = new widget.TextBox();
        NamaPPI = new widget.TextBox();
        BtnPPI = new widget.Button();
        icLabel38 = new widget.Label();
        KodeManajemen = new widget.TextBox();
        NamaManajemen = new widget.TextBox();
        BtnManajemen = new widget.Button();
        scrollAreaData = new widget.ScrollPane();
        tbAreaData = new widget.Table();
        scrollPengendalianData = new widget.ScrollPane();
        tbPengendalianData = new widget.Table();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulir.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulir.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulir.setText("Formulir Pengkajian Risiko Pengendalian Infeksi/ICRA");
        MnFormulir.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulir.setName("MnFormulir"); // NOI18N
        MnFormulir.setPreferredSize(new java.awt.Dimension(300, 26));
        MnFormulir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnFormulir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengkajian Risiko Pengendalian Infeksi/ICRA (Infection Control Risk Assessment) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1160));
        FormInput.setLayout(null);

        icLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel1.setText("No.ICRA :");
        icLabel1.setName("icLabel1"); // NOI18N
        FormInput.add(icLabel1);
        icLabel1.setBounds(0, 10, 105, 23);

        NoICRA.setHighlighter(null);
        NoICRA.setName("NoICRA"); // NOI18N
        NoICRA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoICRAKeyPressed(evt);
            }
        });
        FormInput.add(NoICRA);
        NoICRA.setBounds(109, 10, 120, 23);

        icLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel2.setText("Tgl.Pengkajian :");
        icLabel2.setName("icLabel2"); // NOI18N
        FormInput.add(icLabel2);
        icLabel2.setBounds(240, 10, 85, 23);

        TanggalPengkajian.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPengkajian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2026 00:00:00" }));
        TanggalPengkajian.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPengkajian.setName("TanggalPengkajian"); // NOI18N
        TanggalPengkajian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPengkajianKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPengkajian);
        TanggalPengkajian.setBounds(329, 10, 150, 23);

        icLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel3.setText("No.PCRA :");
        icLabel3.setName("icLabel3"); // NOI18N
        FormInput.add(icLabel3);
        icLabel3.setBounds(486, 10, 62, 23);

        NoPCRA.setEditable(false);
        NoPCRA.setHighlighter(null);
        NoPCRA.setName("NoPCRA"); // NOI18N
        NoPCRA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPCRAKeyPressed(evt);
            }
        });
        FormInput.add(NoPCRA);
        NoPCRA.setBounds(552, 10, 130, 23);

        BtnPCRA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPCRA.setMnemonic('2');
        BtnPCRA.setToolTipText("Alt+2");
        BtnPCRA.setName("BtnPCRA"); // NOI18N
        BtnPCRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPCRAActionPerformed(evt);
            }
        });
        BtnPCRA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPCRAKeyPressed(evt);
            }
        });
        FormInput.add(BtnPCRA);
        BtnPCRA.setBounds(685, 10, 28, 23);

        icLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel4.setText("Nama Proyek :");
        icLabel4.setName("icLabel4"); // NOI18N
        FormInput.add(icLabel4);
        icLabel4.setBounds(0, 40, 105, 23);

        NamaProyek.setEditable(false);
        NamaProyek.setHighlighter(null);
        NamaProyek.setName("NamaProyek"); // NOI18N
        NamaProyek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaProyekKeyPressed(evt);
            }
        });
        FormInput.add(NamaProyek);
        NamaProyek.setBounds(109, 40, 745, 23);

        icLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel5.setText("Lokasi Proyek :");
        icLabel5.setName("icLabel5"); // NOI18N
        FormInput.add(icLabel5);
        icLabel5.setBounds(0, 70, 105, 23);

        LokasiProyek.setEditable(false);
        LokasiProyek.setHighlighter(null);
        LokasiProyek.setName("LokasiProyek"); // NOI18N
        LokasiProyek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiProyekKeyPressed(evt);
            }
        });
        FormInput.add(LokasiProyek);
        LokasiProyek.setBounds(109, 70, 330, 23);

        icLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel6.setText("Pelaksana :");
        icLabel6.setName("icLabel6"); // NOI18N
        FormInput.add(icLabel6);
        icLabel6.setBounds(445, 70, 75, 23);

        Pelaksana.setEditable(false);
        Pelaksana.setHighlighter(null);
        Pelaksana.setName("Pelaksana"); // NOI18N
        Pelaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PelaksanaKeyPressed(evt);
            }
        });
        FormInput.add(Pelaksana);
        Pelaksana.setBounds(524, 70, 330, 23);

        icLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel7.setText("Periode :");
        icLabel7.setName("icLabel7"); // NOI18N
        FormInput.add(icLabel7);
        icLabel7.setBounds(0, 100, 105, 23);

        ProyekMulai.setEditable(false);
        ProyekMulai.setHighlighter(null);
        ProyekMulai.setName("ProyekMulai"); // NOI18N
        ProyekMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProyekMulaiKeyPressed(evt);
            }
        });
        FormInput.add(ProyekMulai);
        ProyekMulai.setBounds(109, 100, 85, 23);

        icLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icLabel8.setText("s.d.");
        icLabel8.setName("icLabel8"); // NOI18N
        FormInput.add(icLabel8);
        icLabel8.setBounds(196, 100, 25, 23);

        ProyekSelesai.setEditable(false);
        ProyekSelesai.setHighlighter(null);
        ProyekSelesai.setName("ProyekSelesai"); // NOI18N
        ProyekSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProyekSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(ProyekSelesai);
        ProyekSelesai.setBounds(223, 100, 85, 23);

        icLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel9.setText("Uraian :");
        icLabel9.setName("icLabel9"); // NOI18N
        FormInput.add(icLabel9);
        icLabel9.setBounds(320, 100, 60, 23);

        UraianPekerjaan.setEditable(false);
        UraianPekerjaan.setHighlighter(null);
        UraianPekerjaan.setName("UraianPekerjaan"); // NOI18N
        UraianPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(UraianPekerjaan);
        UraianPekerjaan.setBounds(384, 100, 470, 23);

        icSep1.setBackground(new java.awt.Color(239, 244, 234));
        icSep1.setForeground(new java.awt.Color(239, 244, 234));
        icSep1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        icSep1.setName("icSep1"); // NOI18N
        FormInput.add(icSep1);
        icSep1.setBounds(0, 135, 880, 1);

        icLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel10.setText("I. IDENTIFIKASI RUANG LINGKUP PEKERJAAN");
        icLabel10.setName("icLabel10"); // NOI18N
        FormInput.add(icLabel10);
        icLabel10.setBounds(15, 138, 500, 23);

        icLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel11.setText("Lokasi/Ruang Sekitar :");
        icLabel11.setName("icLabel11"); // NOI18N
        FormInput.add(icLabel11);
        icLabel11.setBounds(0, 165, 130, 23);

        LokasiSekitar.setHighlighter(null);
        LokasiSekitar.setName("LokasiSekitar"); // NOI18N
        LokasiSekitar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiSekitarKeyPressed(evt);
            }
        });
        FormInput.add(LokasiSekitar);
        LokasiSekitar.setBounds(134, 165, 720, 23);

        icLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel12.setText("Rute Pekerja/Material :");
        icLabel12.setName("icLabel12"); // NOI18N
        FormInput.add(icLabel12);
        icLabel12.setBounds(0, 195, 130, 23);

        RutePekerja.setHighlighter(null);
        RutePekerja.setName("RutePekerja"); // NOI18N
        RutePekerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RutePekerjaKeyPressed(evt);
            }
        });
        FormInput.add(RutePekerja);
        RutePekerja.setBounds(134, 195, 720, 23);

        icLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel13.setText("Jam Kerja Mulai :");
        icLabel13.setName("icLabel13"); // NOI18N
        FormInput.add(icLabel13);
        icLabel13.setBounds(0, 225, 130, 23);

        JamMulaiJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamMulaiJam.setName("JamMulaiJam"); // NOI18N
        JamMulaiJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiJamKeyPressed(evt);
            }
        });
        FormInput.add(JamMulaiJam);
        JamMulaiJam.setBounds(134, 225, 50, 23);

        icLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icLabel14.setText(":");
        icLabel14.setName("icLabel14"); // NOI18N
        FormInput.add(icLabel14);
        icLabel14.setBounds(186, 225, 8, 23);

        JamMulaiMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        JamMulaiMenit.setName("JamMulaiMenit"); // NOI18N
        JamMulaiMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiMenitKeyPressed(evt);
            }
        });
        FormInput.add(JamMulaiMenit);
        JamMulaiMenit.setBounds(196, 225, 50, 23);

        icLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel15.setText("Selesai :");
        icLabel15.setName("icLabel15"); // NOI18N
        FormInput.add(icLabel15);
        icLabel15.setBounds(262, 225, 60, 23);

        JamSelesaiJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamSelesaiJam.setName("JamSelesaiJam"); // NOI18N
        JamSelesaiJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiJamKeyPressed(evt);
            }
        });
        FormInput.add(JamSelesaiJam);
        JamSelesaiJam.setBounds(326, 225, 50, 23);

        icLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icLabel16.setText(":");
        icLabel16.setName("icLabel16"); // NOI18N
        FormInput.add(icLabel16);
        icLabel16.setBounds(378, 225, 8, 23);

        JamSelesaiMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        JamSelesaiMenit.setName("JamSelesaiMenit"); // NOI18N
        JamSelesaiMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiMenitKeyPressed(evt);
            }
        });
        FormInput.add(JamSelesaiMenit);
        JamSelesaiMenit.setBounds(388, 225, 50, 23);

        WaktuKerja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Siang", "Malam", "24 Jam" }));
        WaktuKerja.setName("WaktuKerja"); // NOI18N
        WaktuKerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuKerjaKeyPressed(evt);
            }
        });
        FormInput.add(WaktuKerja);
        WaktuKerja.setBounds(450, 225, 110, 23);

        icLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel17.setText("Tetap Beroperasi :");
        icLabel17.setName("icLabel17"); // NOI18N
        FormInput.add(icLabel17);
        icLabel17.setBounds(0, 255, 130, 23);

        AreaBeroperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak", "Sebagian" }));
        AreaBeroperasi.setName("AreaBeroperasi"); // NOI18N
        AreaBeroperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaBeroperasiKeyPressed(evt);
            }
        });
        FormInput.add(AreaBeroperasi);
        AreaBeroperasi.setBounds(134, 255, 90, 23);

        icLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel18.setText("Keterangan :");
        icLabel18.setName("icLabel18"); // NOI18N
        FormInput.add(icLabel18);
        icLabel18.setBounds(232, 255, 80, 23);

        KeteranganOperasi.setHighlighter(null);
        KeteranganOperasi.setName("KeteranganOperasi"); // NOI18N
        KeteranganOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganOperasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganOperasi);
        KeteranganOperasi.setBounds(316, 255, 538, 23);

        icSep2.setBackground(new java.awt.Color(239, 244, 234));
        icSep2.setForeground(new java.awt.Color(239, 244, 234));
        icSep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        icSep2.setName("icSep2"); // NOI18N
        FormInput.add(icSep2);
        icSep2.setBounds(0, 290, 880, 1);

        icLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel19.setText("II. IDENTIFIKASI KELOMPOK PASIEN / AREA BERISIKO");
        icLabel19.setName("icLabel19"); // NOI18N
        FormInput.add(icLabel19);
        icLabel19.setBounds(15, 293, 500, 23);

        icLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel20.setText("Kelompok Pasien / Area Terdampak :");
        icLabel20.setName("icLabel20"); // NOI18N
        FormInput.add(icLabel20);
        icLabel20.setBounds(15, 320, 440, 23);

        icLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel21.setText("Key Word :");
        icLabel21.setName("icLabel21"); // NOI18N
        FormInput.add(icLabel21);
        icLabel21.setBounds(470, 320, 60, 23);

        TCariArea.setHighlighter(null);
        TCariArea.setName("TCariArea"); // NOI18N
        TCariArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariAreaKeyPressed(evt);
            }
        });
        FormInput.add(TCariArea);
        TCariArea.setBounds(534, 320, 200, 23);

        BtnCariArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariArea.setMnemonic('1');
        BtnCariArea.setToolTipText("Alt+1");
        BtnCariArea.setName("BtnCariArea"); // NOI18N
        BtnCariArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariAreaActionPerformed(evt);
            }
        });
        BtnCariArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariAreaKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariArea);
        BtnCariArea.setBounds(737, 320, 28, 23);

        BtnAllArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllArea.setMnemonic('2');
        BtnAllArea.setToolTipText("Alt+2");
        BtnAllArea.setName("BtnAllArea"); // NOI18N
        BtnAllArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllAreaActionPerformed(evt);
            }
        });
        BtnAllArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllAreaKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllArea);
        BtnAllArea.setBounds(769, 320, 28, 23);

        BtnTambahArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahArea.setMnemonic('3');
        BtnTambahArea.setToolTipText("Alt+3");
        BtnTambahArea.setName("BtnTambahArea"); // NOI18N
        BtnTambahArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahAreaActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahArea);
        BtnTambahArea.setBounds(801, 320, 28, 23);

        ScrollArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollArea.setName("ScrollArea"); // NOI18N
        ScrollArea.setOpaque(true);

        tbArea.setName("tbArea"); // NOI18N
        ScrollArea.setViewportView(tbArea);

        FormInput.add(ScrollArea);
        ScrollArea.setBounds(15, 345, 839, 140);

        icSep3.setBackground(new java.awt.Color(239, 244, 234));
        icSep3.setForeground(new java.awt.Color(239, 244, 234));
        icSep3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        icSep3.setName("icSep3"); // NOI18N
        FormInput.add(icSep3);
        icSep3.setBounds(0, 500, 880, 1);

        icLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel22.setText("III. PENETAPAN KELAS PENCEGAHAN ICRA");
        icLabel22.setName("icLabel22"); // NOI18N
        FormInput.add(icLabel22);
        icLabel22.setBounds(15, 503, 500, 23);

        icLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel23.setText("Tipe Pekerjaan :");
        icLabel23.setName("icLabel23"); // NOI18N
        FormInput.add(icLabel23);
        icLabel23.setBounds(0, 530, 105, 23);

        KodeAktivitas.setEditable(false);
        KodeAktivitas.setHighlighter(null);
        KodeAktivitas.setName("KodeAktivitas"); // NOI18N
        KodeAktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeAktivitasKeyPressed(evt);
            }
        });
        FormInput.add(KodeAktivitas);
        KodeAktivitas.setBounds(109, 530, 70, 23);

        NamaAktivitas.setEditable(false);
        NamaAktivitas.setHighlighter(null);
        NamaAktivitas.setName("NamaAktivitas"); // NOI18N
        NamaAktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaAktivitasKeyPressed(evt);
            }
        });
        FormInput.add(NamaAktivitas);
        NamaAktivitas.setBounds(183, 530, 280, 23);

        BtnAktivitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAktivitas.setMnemonic('2');
        BtnAktivitas.setToolTipText("Alt+2");
        BtnAktivitas.setName("BtnAktivitas"); // NOI18N
        BtnAktivitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAktivitasActionPerformed(evt);
            }
        });
        BtnAktivitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAktivitasKeyPressed(evt);
            }
        });
        FormInput.add(BtnAktivitas);
        BtnAktivitas.setBounds(466, 530, 28, 23);

        icLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel24.setText("Risiko Pasien :");
        icLabel24.setName("icLabel24"); // NOI18N
        FormInput.add(icLabel24);
        icLabel24.setBounds(0, 560, 105, 23);

        KodeRisiko.setEditable(false);
        KodeRisiko.setHighlighter(null);
        KodeRisiko.setName("KodeRisiko"); // NOI18N
        KodeRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeRisikoKeyPressed(evt);
            }
        });
        FormInput.add(KodeRisiko);
        KodeRisiko.setBounds(109, 560, 70, 23);

        NamaRisiko.setEditable(false);
        NamaRisiko.setHighlighter(null);
        NamaRisiko.setName("NamaRisiko"); // NOI18N
        NamaRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaRisikoKeyPressed(evt);
            }
        });
        FormInput.add(NamaRisiko);
        NamaRisiko.setBounds(183, 560, 280, 23);

        BtnRisiko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRisiko.setMnemonic('2');
        BtnRisiko.setToolTipText("Alt+2");
        BtnRisiko.setName("BtnRisiko"); // NOI18N
        BtnRisiko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRisikoActionPerformed(evt);
            }
        });
        BtnRisiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRisikoKeyPressed(evt);
            }
        });
        FormInput.add(BtnRisiko);
        BtnRisiko.setBounds(466, 560, 28, 23);

        icLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel25.setText("Kelas ICRA :");
        icLabel25.setName("icLabel25"); // NOI18N
        FormInput.add(icLabel25);
        icLabel25.setBounds(0, 590, 105, 23);

        KodeKewaspadaan.setEditable(false);
        KodeKewaspadaan.setHighlighter(null);
        KodeKewaspadaan.setName("KodeKewaspadaan"); // NOI18N
        KodeKewaspadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeKewaspadaanKeyPressed(evt);
            }
        });
        FormInput.add(KodeKewaspadaan);
        KodeKewaspadaan.setBounds(109, 590, 70, 23);

        NamaKewaspadaan.setEditable(false);
        NamaKewaspadaan.setHighlighter(null);
        NamaKewaspadaan.setName("NamaKewaspadaan"); // NOI18N
        NamaKewaspadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaKewaspadaanKeyPressed(evt);
            }
        });
        FormInput.add(NamaKewaspadaan);
        NamaKewaspadaan.setBounds(183, 590, 150, 23);

        icLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel26.setText("Perlu Izin :");
        icLabel26.setName("icLabel26"); // NOI18N
        FormInput.add(icLabel26);
        icLabel26.setBounds(340, 590, 70, 23);

        PerluIzin.setEditable(false);
        PerluIzin.setHighlighter(null);
        PerluIzin.setName("PerluIzin"); // NOI18N
        PerluIzin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerluIzinKeyPressed(evt);
            }
        });
        FormInput.add(PerluIzin);
        PerluIzin.setBounds(414, 590, 60, 23);

        icLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel27.setText("Alasan Kelas :");
        icLabel27.setName("icLabel27"); // NOI18N
        FormInput.add(icLabel27);
        icLabel27.setBounds(0, 620, 105, 23);

        AlasanKelas.setHighlighter(null);
        AlasanKelas.setName("AlasanKelas"); // NOI18N
        AlasanKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKelasKeyPressed(evt);
            }
        });
        FormInput.add(AlasanKelas);
        AlasanKelas.setBounds(109, 620, 745, 23);

        icSep4.setBackground(new java.awt.Color(239, 244, 234));
        icSep4.setForeground(new java.awt.Color(239, 244, 234));
        icSep4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        icSep4.setName("icSep4"); // NOI18N
        FormInput.add(icSep4);
        icSep4.setBounds(0, 655, 880, 1);

        icLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel28.setText("IV. RENCANA PENGENDALIAN");
        icLabel28.setName("icLabel28"); // NOI18N
        FormInput.add(icLabel28);
        icLabel28.setBounds(15, 658, 500, 23);

        icLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel29.setText("Rencana Pengendalian Yang Wajib Diterapkan (tetapkan sebelum mulai) :");
        icLabel29.setName("icLabel29"); // NOI18N
        FormInput.add(icLabel29);
        icLabel29.setBounds(15, 685, 440, 23);

        icLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel30.setText("Key Word :");
        icLabel30.setName("icLabel30"); // NOI18N
        FormInput.add(icLabel30);
        icLabel30.setBounds(470, 685, 60, 23);

        TCariPengendalian.setHighlighter(null);
        TCariPengendalian.setName("TCariPengendalian"); // NOI18N
        TCariPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(TCariPengendalian);
        TCariPengendalian.setBounds(534, 685, 200, 23);

        BtnCariPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPengendalian.setMnemonic('1');
        BtnCariPengendalian.setToolTipText("Alt+1");
        BtnCariPengendalian.setName("BtnCariPengendalian"); // NOI18N
        BtnCariPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPengendalianActionPerformed(evt);
            }
        });
        BtnCariPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(BtnCariPengendalian);
        BtnCariPengendalian.setBounds(737, 685, 28, 23);

        BtnAllPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPengendalian.setMnemonic('2');
        BtnAllPengendalian.setToolTipText("Alt+2");
        BtnAllPengendalian.setName("BtnAllPengendalian"); // NOI18N
        BtnAllPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPengendalianActionPerformed(evt);
            }
        });
        BtnAllPengendalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllPengendalianKeyPressed(evt);
            }
        });
        FormInput.add(BtnAllPengendalian);
        BtnAllPengendalian.setBounds(769, 685, 28, 23);

        BtnTambahPengendalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahPengendalian.setMnemonic('3');
        BtnTambahPengendalian.setToolTipText("Alt+3");
        BtnTambahPengendalian.setName("BtnTambahPengendalian"); // NOI18N
        BtnTambahPengendalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPengendalianActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahPengendalian);
        BtnTambahPengendalian.setBounds(801, 685, 28, 23);

        ScrollPengendalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollPengendalian.setName("ScrollPengendalian"); // NOI18N
        ScrollPengendalian.setOpaque(true);

        tbPengendalian.setName("tbPengendalian"); // NOI18N
        ScrollPengendalian.setViewportView(tbPengendalian);

        FormInput.add(ScrollPengendalian);
        ScrollPengendalian.setBounds(15, 710, 839, 170);

        icSep5.setBackground(new java.awt.Color(239, 244, 234));
        icSep5.setForeground(new java.awt.Color(239, 244, 234));
        icSep5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        icSep5.setName("icSep5"); // NOI18N
        FormInput.add(icSep5);
        icSep5.setBounds(0, 895, 880, 1);

        icLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        icLabel31.setText("V. KEPUTUSAN DAN PERSETUJUAN PRA-PEKERJAAN");
        icLabel31.setName("icLabel31"); // NOI18N
        FormInput.add(icLabel31);
        icLabel31.setBounds(15, 898, 500, 23);

        icLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel32.setText("Keputusan :");
        icLabel32.setName("icLabel32"); // NOI18N
        FormInput.add(icLabel32);
        icLabel32.setBounds(0, 925, 105, 23);

        Keputusan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disetujui", "Disetujui dengan persyaratan", "Ditunda" }));
        Keputusan.setName("Keputusan"); // NOI18N
        Keputusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanKeyPressed(evt);
            }
        });
        FormInput.add(Keputusan);
        Keputusan.setBounds(109, 925, 230, 23);

        icLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel33.setText("Persyaratan :");
        icLabel33.setName("icLabel33"); // NOI18N
        FormInput.add(icLabel33);
        icLabel33.setBounds(0, 955, 105, 23);

        Persyaratan.setHighlighter(null);
        Persyaratan.setName("Persyaratan"); // NOI18N
        Persyaratan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersyaratanKeyPressed(evt);
            }
        });
        FormInput.add(Persyaratan);
        Persyaratan.setBounds(109, 955, 745, 23);

        icLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel34.setText("Pemohon/Unit :");
        icLabel34.setName("icLabel34"); // NOI18N
        FormInput.add(icLabel34);
        icLabel34.setBounds(0, 990, 105, 23);

        KodePemohon.setEditable(false);
        KodePemohon.setHighlighter(null);
        KodePemohon.setName("KodePemohon"); // NOI18N
        KodePemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePemohonKeyPressed(evt);
            }
        });
        FormInput.add(KodePemohon);
        KodePemohon.setBounds(109, 990, 100, 23);

        NamaPemohon.setEditable(false);
        NamaPemohon.setHighlighter(null);
        NamaPemohon.setName("NamaPemohon"); // NOI18N
        NamaPemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPemohonKeyPressed(evt);
            }
        });
        FormInput.add(NamaPemohon);
        NamaPemohon.setBounds(213, 990, 300, 23);

        BtnPemohon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPemohon.setMnemonic('2');
        BtnPemohon.setToolTipText("Alt+2");
        BtnPemohon.setName("BtnPemohon"); // NOI18N
        BtnPemohon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPemohonActionPerformed(evt);
            }
        });
        BtnPemohon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPemohonKeyPressed(evt);
            }
        });
        FormInput.add(BtnPemohon);
        BtnPemohon.setBounds(516, 990, 28, 23);

        icLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel35.setText("IPSRS :");
        icLabel35.setName("icLabel35"); // NOI18N
        FormInput.add(icLabel35);
        icLabel35.setBounds(0, 1020, 105, 23);

        KodeIPSRS.setEditable(false);
        KodeIPSRS.setHighlighter(null);
        KodeIPSRS.setName("KodeIPSRS"); // NOI18N
        KodeIPSRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeIPSRSKeyPressed(evt);
            }
        });
        FormInput.add(KodeIPSRS);
        KodeIPSRS.setBounds(109, 1020, 100, 23);

        NamaIPSRS.setEditable(false);
        NamaIPSRS.setHighlighter(null);
        NamaIPSRS.setName("NamaIPSRS"); // NOI18N
        NamaIPSRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaIPSRSKeyPressed(evt);
            }
        });
        FormInput.add(NamaIPSRS);
        NamaIPSRS.setBounds(213, 1020, 300, 23);

        BtnIPSRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnIPSRS.setMnemonic('2');
        BtnIPSRS.setToolTipText("Alt+2");
        BtnIPSRS.setName("BtnIPSRS"); // NOI18N
        BtnIPSRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIPSRSActionPerformed(evt);
            }
        });
        BtnIPSRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnIPSRSKeyPressed(evt);
            }
        });
        FormInput.add(BtnIPSRS);
        BtnIPSRS.setBounds(516, 1020, 28, 23);

        icLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel36.setText("K3RS :");
        icLabel36.setName("icLabel36"); // NOI18N
        FormInput.add(icLabel36);
        icLabel36.setBounds(0, 1050, 105, 23);

        KodeK3RS.setEditable(false);
        KodeK3RS.setHighlighter(null);
        KodeK3RS.setName("KodeK3RS"); // NOI18N
        KodeK3RS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeK3RSKeyPressed(evt);
            }
        });
        FormInput.add(KodeK3RS);
        KodeK3RS.setBounds(109, 1050, 100, 23);

        NamaK3RS.setEditable(false);
        NamaK3RS.setHighlighter(null);
        NamaK3RS.setName("NamaK3RS"); // NOI18N
        NamaK3RS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaK3RSKeyPressed(evt);
            }
        });
        FormInput.add(NamaK3RS);
        NamaK3RS.setBounds(213, 1050, 300, 23);

        BtnK3RS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnK3RS.setMnemonic('2');
        BtnK3RS.setToolTipText("Alt+2");
        BtnK3RS.setName("BtnK3RS"); // NOI18N
        BtnK3RS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnK3RSActionPerformed(evt);
            }
        });
        BtnK3RS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnK3RSKeyPressed(evt);
            }
        });
        FormInput.add(BtnK3RS);
        BtnK3RS.setBounds(516, 1050, 28, 23);

        icLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel37.setText("Tim PPI :");
        icLabel37.setName("icLabel37"); // NOI18N
        FormInput.add(icLabel37);
        icLabel37.setBounds(0, 1080, 105, 23);

        KodePPI.setEditable(false);
        KodePPI.setHighlighter(null);
        KodePPI.setName("KodePPI"); // NOI18N
        KodePPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePPIKeyPressed(evt);
            }
        });
        FormInput.add(KodePPI);
        KodePPI.setBounds(109, 1080, 100, 23);

        NamaPPI.setEditable(false);
        NamaPPI.setHighlighter(null);
        NamaPPI.setName("NamaPPI"); // NOI18N
        NamaPPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPPIKeyPressed(evt);
            }
        });
        FormInput.add(NamaPPI);
        NamaPPI.setBounds(213, 1080, 300, 23);

        BtnPPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPPI.setMnemonic('2');
        BtnPPI.setToolTipText("Alt+2");
        BtnPPI.setName("BtnPPI"); // NOI18N
        BtnPPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPPIActionPerformed(evt);
            }
        });
        BtnPPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPPIKeyPressed(evt);
            }
        });
        FormInput.add(BtnPPI);
        BtnPPI.setBounds(516, 1080, 28, 23);

        icLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        icLabel38.setText("Manajemen :");
        icLabel38.setName("icLabel38"); // NOI18N
        FormInput.add(icLabel38);
        icLabel38.setBounds(0, 1110, 105, 23);

        KodeManajemen.setEditable(false);
        KodeManajemen.setHighlighter(null);
        KodeManajemen.setName("KodeManajemen"); // NOI18N
        KodeManajemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeManajemenKeyPressed(evt);
            }
        });
        FormInput.add(KodeManajemen);
        KodeManajemen.setBounds(109, 1110, 100, 23);

        NamaManajemen.setEditable(false);
        NamaManajemen.setHighlighter(null);
        NamaManajemen.setName("NamaManajemen"); // NOI18N
        NamaManajemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaManajemenKeyPressed(evt);
            }
        });
        FormInput.add(NamaManajemen);
        NamaManajemen.setBounds(213, 1110, 300, 23);

        BtnManajemen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnManajemen.setMnemonic('2');
        BtnManajemen.setToolTipText("Alt+2");
        BtnManajemen.setName("BtnManajemen"); // NOI18N
        BtnManajemen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnManajemenActionPerformed(evt);
            }
        });
        BtnManajemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnManajemenKeyPressed(evt);
            }
        });
        FormInput.add(BtnManajemen);
        BtnManajemen.setBounds(516, 1110, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pengkajian", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
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

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabelICRAData.setText("No.ICRA :");
        jLabelICRAData.setName("jLabelICRAData"); // NOI18N
        jLabelICRAData.setPreferredSize(new java.awt.Dimension(60, 23));
        FormMenu.add(jLabelICRAData);

        TNoICRAData.setEditable(false);
        TNoICRAData.setHighlighter(null);
        TNoICRAData.setName("TNoICRAData"); // NOI18N
        TNoICRAData.setPreferredSize(new java.awt.Dimension(120, 23));
        FormMenu.add(TNoICRAData);

        TNamaProyekData.setEditable(false);
        TNamaProyekData.setBackground(new java.awt.Color(245, 250, 240));
        TNamaProyekData.setHighlighter(null);
        TNamaProyekData.setName("TNamaProyekData"); // NOI18N
        TNamaProyekData.setPreferredSize(new java.awt.Dimension(265, 23));
        FormMenu.add(TNamaProyekData);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        scrollRelasiData.setBorder(null);
        scrollRelasiData.setName("scrollRelasiData"); // NOI18N
        scrollRelasiData.setOpaque(true);

        FormRelasiData.setBackground(new java.awt.Color(255, 255, 255));
        FormRelasiData.setBorder(null);
        FormRelasiData.setName("FormRelasiData"); // NOI18N
        FormRelasiData.setPreferredSize(new java.awt.Dimension(440, 320));
        FormRelasiData.setLayout(new java.awt.GridLayout(2, 1, 1, 1));

        scrollAreaData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), " Kelompok Pasien / Area Terdampak : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollAreaData.setName("scrollAreaData"); // NOI18N
        scrollAreaData.setOpaque(true);

        tbAreaData.setName("tbAreaData"); // NOI18N
        scrollAreaData.setViewportView(tbAreaData);

        FormRelasiData.add(scrollAreaData);

        scrollPengendalianData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), " Rencana Pengendalian : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPengendalianData.setName("scrollPengendalianData"); // NOI18N
        scrollPengendalianData.setOpaque(true);

        tbPengendalianData.setName("tbPengendalianData"); // NOI18N
        scrollPengendalianData.setViewportView(tbPengendalianData);

        FormRelasiData.add(scrollPengendalianData);

        scrollRelasiData.setViewportView(FormRelasiData);

        PanelAccor.add(scrollRelasiData, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Pengkajian :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-11-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Pengkajian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoICRA.getText().trim().equals("")){
            Valid.textKosong(NoICRA,"No.ICRA");
        }else if(NoPCRA.getText().trim().equals("")){
            Valid.textKosong(BtnPCRA,"No.PCRA (proyek)");
        }else if(KodeAktivitas.getText().trim().equals("")||NamaAktivitas.getText().trim().equals("")){
            Valid.textKosong(BtnAktivitas,"Tipe Pekerjaan");
        }else if(KodeRisiko.getText().trim().equals("")||NamaRisiko.getText().trim().equals("")){
            Valid.textKosong(BtnRisiko,"Risiko Pasien");
        }else if(KodeKewaspadaan.getText().trim().equals("")){
            Valid.textKosong(BtnRisiko,"Kelas ICRA (atur Matriks Kelas Kewaspadaan)");
        }else if(KodePemohon.getText().trim().equals("")||NamaPemohon.getText().trim().equals("")){
            Valid.textKosong(BtnPemohon,"Pemohon/Unit");
        }else if(KodePPI.getText().trim().equals("")||NamaPPI.getText().trim().equals("")){
            Valid.textKosong(BtnPPI,"Komite/Tim PPI");
        }else{
            if(Sequel.menyimpantf("pcra_icra_pengkajian_risiko_infeksi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
                NoICRA.getText(),NoPCRA.getText(),Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),
                LokasiSekitar.getText(),RutePekerja.getText(),JamMulaiJam.getSelectedItem()+":"+JamMulaiMenit.getSelectedItem()+":00",
                JamSelesaiJam.getSelectedItem()+":"+JamSelesaiMenit.getSelectedItem()+":00",WaktuKerja.getSelectedItem().toString(),AreaBeroperasi.getSelectedItem().toString(),
                KeteranganOperasi.getText(),KodeRisiko.getText(),KodeAktivitas.getText(),
                KodeKewaspadaan.getText(),AlasanKelas.getText(),Keputusan.getSelectedItem().toString(),
                Persyaratan.getText(),KodePemohon.getText(),KodeIPSRS.getText().trim().equals("")?null:KodeIPSRS.getText(),
                KodeK3RS.getText().trim().equals("")?null:KodeK3RS.getText(),KodePPI.getText(),KodeManajemen.getText().trim().equals("")?null:KodeManajemen.getText()
            })==true){
                for (i = 0; i < tbArea.getRowCount(); i++) {
                    if(tbArea.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpantf2("pcra_icra_pengkajian_risiko_infeksi_area","?,?,?",3,new String[]{NoICRA.getText(),tbArea.getValueAt(i,1).toString(),tbArea.getValueAt(i,3).toString()});
                    }
                }
                for (i = 0; i < tbPengendalian.getRowCount(); i++) {
                    if(tbPengendalian.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpantf2("pcra_icra_pengkajian_risiko_infeksi_pengendalian","?,?,?,?",4,new String[]{NoICRA.getText(),tbPengendalian.getValueAt(i,1).toString(),tbPengendalian.getValueAt(i,3).toString(),tbPengendalian.getValueAt(i,4).toString()});
                    }
                }
                tabMode.addRow(new Object[]{
                    NoICRA.getText(),NoPCRA.getText(),NamaProyek.getText(),
                    LokasiProyek.getText(),Pelaksana.getText(),ProyekMulai.getText(),
                    ProyekSelesai.getText(),UraianPekerjaan.getText(),Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),
                    LokasiSekitar.getText(),RutePekerja.getText(),JamMulaiJam.getSelectedItem()+":"+JamMulaiMenit.getSelectedItem()+":00",
                    JamSelesaiJam.getSelectedItem()+":"+JamSelesaiMenit.getSelectedItem()+":00",WaktuKerja.getSelectedItem().toString(),AreaBeroperasi.getSelectedItem().toString(),
                    KeteranganOperasi.getText(),KodeRisiko.getText(),NamaRisiko.getText(),
                    KodeAktivitas.getText(),NamaAktivitas.getText(),KodeKewaspadaan.getText(),
                    NamaKewaspadaan.getText(),PerluIzin.getText(),AlasanKelas.getText(),
                    Keputusan.getSelectedItem().toString(),Persyaratan.getText(),KodePemohon.getText(),
                    NamaPemohon.getText(),KodeIPSRS.getText(),NamaIPSRS.getText(),
                    KodeK3RS.getText(),NamaK3RS.getText(),KodePPI.getText(),
                    NamaPPI.getText(),KodeManajemen.getText(),NamaManajemen.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KodeManajemen,BtnBatal);
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
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh Tim PPI yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        if(NoICRA.getText().trim().equals("")){
            Valid.textKosong(NoICRA,"No.ICRA");
        }else if(NoPCRA.getText().trim().equals("")){
            Valid.textKosong(BtnPCRA,"No.PCRA (proyek)");
        }else if(KodeAktivitas.getText().trim().equals("")||NamaAktivitas.getText().trim().equals("")){
            Valid.textKosong(BtnAktivitas,"Tipe Pekerjaan");
        }else if(KodeRisiko.getText().trim().equals("")||NamaRisiko.getText().trim().equals("")){
            Valid.textKosong(BtnRisiko,"Risiko Pasien");
        }else if(KodeKewaspadaan.getText().trim().equals("")){
            Valid.textKosong(BtnRisiko,"Kelas ICRA (atur Matriks Kelas Kewaspadaan)");
        }else if(KodePemohon.getText().trim().equals("")||NamaPemohon.getText().trim().equals("")){
            Valid.textKosong(BtnPemohon,"Pemohon/Unit");
        }else if(KodePPI.getText().trim().equals("")||NamaPPI.getText().trim().equals("")){
            Valid.textKosong(BtnPPI,"Komite/Tim PPI");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),Sequel.ambiltanggalsekarang())==true){
                            ganti();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh Tim PPI yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='95px'><b>No.ICRA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.PCRA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Nama Proyek</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Lokasi Proyek</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Pelaksana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>Mulai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>Selesai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'><b>Uraian Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='125px'><b>Tgl.Pengkajian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Lokasi/Ruang Sekitar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Rute Pekerja/Material</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Jam Mulai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Jam Selesai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>Waktu Kerja</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='85px'><b>Area Beroperasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='130px'><b>Ket. Operasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>Kode Risiko</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='110px'><b>Risiko Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='78px'><b>Kode Aktivitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='220px'><b>Tipe Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Kode Kewaspadaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kelas ICRA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Perlu Izin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Alasan Penetapan Kelas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Keputusan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Persyaratan/Catatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>NIP Pemohon</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='140px'><b>Pemohon</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>NIP IPSRS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='140px'><b>IPSRS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>NIP K3RS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='140px'><b>K3RS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>NIP PPI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='140px'><b>Tim PPI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>NIP Manajemen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='140px'><b>Manajemen</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4328px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

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

                File f = new File("DataPCRAICRAPengkajianRisikoInfeksi.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4328px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGKAJIAN RISIKO PENGENDALIAN INFEKSI/ICRA (INFECTION CONTROL RISK ASSESSMENT)<br><br></font>"+
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notif : "+e);
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
            Valid.pindah(evt, BtnCari, NoICRA);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                ChkAccor.setSelected(true);
                isMenu();
                tampilRelasi();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                getData();
                TabRawat.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    tampilRelasi();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyPressed

    private void MnFormulirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirActionPerformed
        if(tbObat.getSelectedRow()>-1){
            String noicra=tbObat.getValueAt(tbObat.getSelectedRow(),0).toString();
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptFormulirPCRAICRAPengkajianRisikoInfeksi.jasper","report","::[ Formulir Pengkajian Risiko Pengendalian Infeksi/ICRA ]::",
                "select "+
                "pcra_icra_pengkajian_risiko_infeksi.no_icra,pcra_icra_pengkajian_risiko_infeksi.no_pcra,pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian,pcra_icra_pengkajian_risiko_prakonstruksi.no_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.nama_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.lokasi_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.pelaksana_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.mulai_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.perkiraan_selesai,pcra_icra_pengkajian_risiko_prakonstruksi.deskripsi_pekerjaan,pcra_icra_pengkajian_risiko_infeksi.lokasi_sekitar,pcra_icra_pengkajian_risiko_infeksi.rute_pekerja,pcra_icra_pengkajian_risiko_infeksi.jam_mulai,pcra_icra_pengkajian_risiko_infeksi.jam_selesai,pcra_icra_pengkajian_risiko_infeksi.waktu_kerja,pcra_icra_pengkajian_risiko_infeksi.area_tetap_beroperasi,pcra_icra_pengkajian_risiko_infeksi.keterangan_operasi,pcra_icra_jenis_aktivitas_proyek.nama_aktivitas,pcra_icra_kelas_risiko_pencegahan.nama_kelas "+
                "as "+
                "risiko_pasien,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,pcra_icra_kelas_kewaspadaan.perlu_izin,pcra_icra_pengkajian_risiko_infeksi.alasan_kelas,pcra_icra_pengkajian_risiko_infeksi.keputusan,pcra_icra_pengkajian_risiko_infeksi.persyaratan,pemohon.nama "+
                "as nama_pemohon,ipsrs.nama as nama_ipsrs,k3rs.nama as nama_k3rs,ppi.nama as nama_ppi,manajemen.nama as "+
                "nama_manajemen,daftar_jenis.urut,daftar_jenis.jenis,isi_jenis.uraian,isi_jenis.keterangan,isi_jenis.pic from pcra_icra_pengkajian_risiko_infeksi inner join "+
                "pcra_icra_pengkajian_risiko_prakonstruksi on pcra_icra_pengkajian_risiko_prakonstruksi.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner join "+
                "pcra_icra_jenis_aktivitas_proyek on pcra_icra_jenis_aktivitas_proyek.kode_aktivitas=pcra_icra_pengkajian_risiko_infeksi.kode_aktivitas inner join "+
                "pcra_icra_kelas_risiko_pencegahan on pcra_icra_kelas_risiko_pencegahan.kode_kelas=pcra_icra_pengkajian_risiko_infeksi.kode_kelas_risiko inner join "+
                "pcra_icra_kelas_kewaspadaan on pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_pengkajian_risiko_infeksi.kode_kewaspadaan inner join pegawai as pemohon on "+
                "pemohon.nik=pcra_icra_pengkajian_risiko_infeksi.nik_pemohon inner join pegawai as ppi on ppi.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ppi left join pegawai as ipsrs on "+
                "ipsrs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ipsrs left join pegawai as k3rs on k3rs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_k3rs left join pegawai as manajemen on "+
                "manajemen.nik=pcra_icra_pengkajian_risiko_infeksi.nik_manajemen inner join (select 1 as urut,'Risiko Teridentifikasi (dari PCRA)' as jenis union all select 2,'Kelompok "+
                "Pasien / Area Terdampak' union all select 3,'Rencana Pengendalian') as daftar_jenis left join (select 1 as urut,risiko.nama_risiko as uraian,'' as "+
                "keterangan,'' as pic from (select pcra_icra_identifkasi_risiko_infeksi.nama_risiko from pcra_icra_pengkajian_risiko_infeksi inner join "+
                "pcra_icra_pengkajian_risiko_prakonstruksi_infeksi on pcra_icra_pengkajian_risiko_prakonstruksi_infeksi.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner join "+
                "pcra_icra_identifkasi_risiko_infeksi on pcra_icra_identifkasi_risiko_infeksi.kode_risiko=pcra_icra_pengkajian_risiko_prakonstruksi_infeksi.kode_risiko "+
                "where pcra_icra_pengkajian_risiko_infeksi.no_icra='"+noicra+"' union all select pcra_icra_identifkasi_risiko_kebakaran.nama_risiko from pcra_icra_pengkajian_risiko_infeksi inner join "+
                "pcra_icra_pengkajian_risiko_prakonstruksi_kebakaran on pcra_icra_pengkajian_risiko_prakonstruksi_kebakaran.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner "+
                "join pcra_icra_identifkasi_risiko_kebakaran on "+
                "pcra_icra_identifkasi_risiko_kebakaran.kode_risiko=pcra_icra_pengkajian_risiko_prakonstruksi_kebakaran.kode_risiko where "+
                "pcra_icra_pengkajian_risiko_infeksi.no_icra='"+noicra+"' union all select pcra_icra_identifkasi_risiko_keselamatan.nama_risiko from pcra_icra_pengkajian_risiko_infeksi inner join "+
                "pcra_icra_pengkajian_risiko_prakonstruksi_keselamatan on pcra_icra_pengkajian_risiko_prakonstruksi_keselamatan.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner "+
                "join pcra_icra_identifkasi_risiko_keselamatan on "+
                "pcra_icra_identifkasi_risiko_keselamatan.kode_risiko=pcra_icra_pengkajian_risiko_prakonstruksi_keselamatan.kode_risiko where "+
                "pcra_icra_pengkajian_risiko_infeksi.no_icra='"+noicra+"' union all select pcra_icra_identifkasi_risiko_utilitas.nama_risiko from pcra_icra_pengkajian_risiko_infeksi inner join "+
                "pcra_icra_pengkajian_risiko_prakonstruksi_utilitas on pcra_icra_pengkajian_risiko_prakonstruksi_utilitas.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner join "+
                "pcra_icra_identifkasi_risiko_utilitas on "+
                "pcra_icra_identifkasi_risiko_utilitas.kode_risiko=pcra_icra_pengkajian_risiko_prakonstruksi_utilitas.kode_risiko where "+
                "pcra_icra_pengkajian_risiko_infeksi.no_icra='"+noicra+"') as risiko union all select 2,pcra_icra_lokasi_kelompok_risiko_area.nama_area as "+
                "uraian,pcra_icra_pengkajian_risiko_infeksi_area.keterangan as keterangan,'' as pic from pcra_icra_pengkajian_risiko_infeksi_area inner join pcra_icra_lokasi_kelompok_risiko_area on "+
                "pcra_icra_lokasi_kelompok_risiko_area.kode_area=pcra_icra_pengkajian_risiko_infeksi_area.kode_area where pcra_icra_pengkajian_risiko_infeksi_area.no_icra='"+noicra+"' union all select "+
                "3,pcra_icra_tindakan_pengendalian.nama_pengendalian as uraian,pcra_icra_pengkajian_risiko_infeksi_pengendalian.rencana as keterangan,pcra_icra_pengkajian_risiko_infeksi_pengendalian.pic "+
                "as pic from pcra_icra_pengkajian_risiko_infeksi_pengendalian inner join pcra_icra_tindakan_pengendalian on "+
                "pcra_icra_tindakan_pengendalian.kode_pengendalian=pcra_icra_pengkajian_risiko_infeksi_pengendalian.kode_pengendalian where "+
                "pcra_icra_pengkajian_risiko_infeksi_pengendalian.no_icra='"+noicra+"') as isi_jenis on isi_jenis.urut=daftar_jenis.urut where pcra_icra_pengkajian_risiko_infeksi.no_icra='"+noicra+"' "+
                "order by daftar_jenis.urut,isi_jenis.uraian",param
            );
        }
    }//GEN-LAST:event_MnFormulirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(Valid.daysOld("./cache/pcrapengkajianarea.iyem")<30){
            tampilArea2();
        }else{
            tampilArea();
        }
        
        if(Valid.daysOld("./cache/pcrapengkajianpengendalian.iyem")<30){
            tampilPengendalian2();
        }else{
            tampilPengendalian();
        }
        
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
            
            TCariArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariArea.getText().length()>2){
                        runBackground(() ->tampilArea2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariArea.getText().length()>2){
                        runBackground(() ->tampilArea2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariArea.getText().length()>2){
                        runBackground(() ->tampilArea2());
                    }
                }
            });
            
            TCariPengendalian.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCariPengendalian.getText().length()>2){
                        runBackground(() ->tampilPengendalian2());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCariPengendalian.getText().length()>2){
                        runBackground(() ->tampilPengendalian2());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCariPengendalian.getText().length()>2){
                        runBackground(() ->tampilPengendalian2());
                    }
                }
            });
            
        }
    }//GEN-LAST:event_formWindowOpened

    private void NoICRAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoICRAKeyPressed
        Valid.pindah(evt,TCari,TanggalPengkajian);
    }//GEN-LAST:event_NoICRAKeyPressed

    private void TanggalPengkajianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPengkajianKeyPressed
        Valid.pindah2(evt,NoICRA,NoPCRA);
    }//GEN-LAST:event_TanggalPengkajianKeyPressed

    private void NoPCRAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPCRAKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPCRAActionPerformed(null);
        }else{            
            Valid.pindah(evt,TanggalPengkajian,LokasiSekitar);
        }
    }//GEN-LAST:event_NoPCRAKeyPressed

    private void BtnPCRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPCRAActionPerformed
        if (caripcra == null || !caripcra.isDisplayable()) {
            caripcra=new PCRAICRACariPCRA(null,false);
            caripcra.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            caripcra.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(caripcra.getTable().getSelectedRow()!= -1){
                        NoPCRA.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),0).toString());
                        NamaProyek.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),2).toString());
                        LokasiProyek.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),3).toString());
                        ProyekMulai.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),4).toString());
                        ProyekSelesai.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),5).toString());
                        UraianPekerjaan.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),6).toString());
                        Pelaksana.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),7).toString());
                        KodeAktivitas.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),8).toString());
                        NamaAktivitas.setText(caripcra.getTable().getValueAt(caripcra.getTable().getSelectedRow(),9).toString());
                        PreparedStatement ps2=null;
                        ResultSet rs2=null;
                        if((!KodeAktivitas.getText().trim().equals(""))&&(!KodeRisiko.getText().trim().equals(""))){
                            try{
                                ps2=koneksi.prepareStatement("select pcra_icra_kelas_kewaspadaan.kode_kewaspadaan,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,pcra_icra_kelas_kewaspadaan.perlu_izin from pcra_icra_matriks_kelas_kewaspadaan "+
                                    "inner join pcra_icra_kelas_kewaspadaan on pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_matriks_kelas_kewaspadaan.kode_kewaspadaan "+
                                    "where pcra_icra_matriks_kelas_kewaspadaan.kode_aktivitas=? and pcra_icra_matriks_kelas_kewaspadaan.kode_kelas=?");
                                try {
                                    ps2.setString(1,KodeAktivitas.getText());
                                    ps2.setString(2,KodeRisiko.getText());
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        KodeKewaspadaan.setText(rs2.getString(1));
                                        NamaKewaspadaan.setText(rs2.getString(2));
                                        PerluIzin.setText(rs2.getString(3));
                                    }else{
                                        KodeKewaspadaan.setText("");
                                        NamaKewaspadaan.setText("");
                                        PerluIzin.setText("");
                                        JOptionPane.showMessageDialog(null,"Kelas ICRA untuk kombinasi tipe pekerjaan & risiko pasien ini belum diatur pada master Matriks Kelas Kewaspadaan..!!");
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }catch(Exception ex){
                                System.out.println("Notif : "+ex);
                            }
                        }
                    }
                    NoICRA.requestFocus();
                    caripcra=null;
                }
            });

            caripcra.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caripcra.setLocationRelativeTo(internalFrame1);
        }
        if (caripcra == null) return;
        if (!caripcra.isVisible()) {
            caripcra.isCek();
        }
        if (caripcra.isVisible()) {
            caripcra.toFront();
            return;
        }
        caripcra.setVisible(true);
    }//GEN-LAST:event_BtnPCRAActionPerformed

    private void BtnPCRAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPCRAKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPCRAActionPerformed(null);
        }
    }//GEN-LAST:event_BtnPCRAKeyPressed

    private void NamaProyekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaProyekKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaProyekKeyPressed

    private void LokasiProyekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiProyekKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_LokasiProyekKeyPressed

    private void PelaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PelaksanaKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_PelaksanaKeyPressed

    private void ProyekMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProyekMulaiKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_ProyekMulaiKeyPressed

    private void ProyekSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProyekSelesaiKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_ProyekSelesaiKeyPressed

    private void UraianPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianPekerjaanKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_UraianPekerjaanKeyPressed

    private void LokasiSekitarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiSekitarKeyPressed
        Valid.pindah(evt,NoPCRA,RutePekerja);
    }//GEN-LAST:event_LokasiSekitarKeyPressed

    private void RutePekerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RutePekerjaKeyPressed
        Valid.pindah(evt,LokasiSekitar,JamMulaiJam);
    }//GEN-LAST:event_RutePekerjaKeyPressed

    private void JamMulaiJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiJamKeyPressed
        Valid.pindah(evt,RutePekerja,JamMulaiMenit);
    }//GEN-LAST:event_JamMulaiJamKeyPressed

    private void JamMulaiMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiMenitKeyPressed
        Valid.pindah(evt,JamMulaiJam,JamSelesaiJam);
    }//GEN-LAST:event_JamMulaiMenitKeyPressed

    private void JamSelesaiJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiJamKeyPressed
        Valid.pindah(evt,JamMulaiMenit,JamSelesaiMenit);
    }//GEN-LAST:event_JamSelesaiJamKeyPressed

    private void JamSelesaiMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiMenitKeyPressed
        Valid.pindah(evt,JamSelesaiJam,WaktuKerja);
    }//GEN-LAST:event_JamSelesaiMenitKeyPressed

    private void WaktuKerjaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuKerjaKeyPressed
        Valid.pindah(evt,JamSelesaiMenit,AreaBeroperasi);
    }//GEN-LAST:event_WaktuKerjaKeyPressed

    private void AreaBeroperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaBeroperasiKeyPressed
        Valid.pindah(evt,WaktuKerja,KeteranganOperasi);
    }//GEN-LAST:event_AreaBeroperasiKeyPressed

    private void KeteranganOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganOperasiKeyPressed
        Valid.pindah(evt,AreaBeroperasi,TCariArea);
    }//GEN-LAST:event_KeteranganOperasiKeyPressed

    private void TCariAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariAreaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilArea2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            KodeAktivitas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KeteranganOperasi.requestFocus();
        }
    }//GEN-LAST:event_TCariAreaKeyPressed

    private void BtnCariAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariAreaActionPerformed
        runBackground(() ->tampilArea2());
    }//GEN-LAST:event_BtnCariAreaActionPerformed

    private void BtnCariAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariAreaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            runBackground(() ->tampilArea2());
        }
    }//GEN-LAST:event_BtnCariAreaKeyPressed

    private void BtnAllAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllAreaActionPerformed
        TCariArea.setText("");
        runBackground(() ->tampilArea());
    }//GEN-LAST:event_BtnAllAreaActionPerformed

    private void BtnAllAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllAreaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllAreaActionPerformed(null);
        }
    }//GEN-LAST:event_BtnAllAreaKeyPressed

    private void BtnTambahAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahAreaActionPerformed
        if (lokasiarea == null || !lokasiarea.isDisplayable()) {
            lokasiarea=new PCRAICRALokasiKelompokRisikoArea(null,false);
            lokasiarea.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            lokasiarea.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    lokasiarea=null;
                }
            });

            lokasiarea.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            lokasiarea.setLocationRelativeTo(internalFrame1);
        }
        if (lokasiarea == null) return;
        if (lokasiarea.isVisible()) {
            lokasiarea.toFront();
            return;
        }
        lokasiarea.setVisible(true);
    }//GEN-LAST:event_BtnTambahAreaActionPerformed

    private void KodeAktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeAktivitasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnAktivitasActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCariArea,KodeRisiko);
        }
    }//GEN-LAST:event_KodeAktivitasKeyPressed

    private void NamaAktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaAktivitasKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaAktivitasKeyPressed

    private void BtnAktivitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAktivitasActionPerformed
        if (aktivitasproyek == null || !aktivitasproyek.isDisplayable()) {
            aktivitasproyek=new PCRAICRACariJenisAktivitasProyek(null,false);
            aktivitasproyek.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            aktivitasproyek.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(aktivitasproyek.getTable().getSelectedRow()!= -1){
                        KodeAktivitas.setText(aktivitasproyek.getTable().getValueAt(aktivitasproyek.getTable().getSelectedRow(),0).toString());
                        NamaAktivitas.setText(aktivitasproyek.getTable().getValueAt(aktivitasproyek.getTable().getSelectedRow(),1).toString());
                        PreparedStatement ps2=null;
                        ResultSet rs2=null;
                        if((!KodeAktivitas.getText().trim().equals(""))&&(!KodeRisiko.getText().trim().equals(""))){
                            try{
                                ps2=koneksi.prepareStatement("select pcra_icra_kelas_kewaspadaan.kode_kewaspadaan,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,pcra_icra_kelas_kewaspadaan.perlu_izin from pcra_icra_matriks_kelas_kewaspadaan "+
                                    "inner join pcra_icra_kelas_kewaspadaan on pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_matriks_kelas_kewaspadaan.kode_kewaspadaan "+
                                    "where pcra_icra_matriks_kelas_kewaspadaan.kode_aktivitas=? and pcra_icra_matriks_kelas_kewaspadaan.kode_kelas=?");
                                try {
                                    ps2.setString(1,KodeAktivitas.getText());
                                    ps2.setString(2,KodeRisiko.getText());
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        KodeKewaspadaan.setText(rs2.getString(1));
                                        NamaKewaspadaan.setText(rs2.getString(2));
                                        PerluIzin.setText(rs2.getString(3));
                                    }else{
                                        KodeKewaspadaan.setText("");
                                        NamaKewaspadaan.setText("");
                                        PerluIzin.setText("");
                                        JOptionPane.showMessageDialog(null,"Kelas ICRA untuk kombinasi tipe pekerjaan & risiko pasien ini belum diatur pada master Matriks Kelas Kewaspadaan..!!");
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }catch(Exception ex){
                                System.out.println("Notif : "+ex);
                            }
                        }
                    }
                    KodeRisiko.requestFocus();
                    aktivitasproyek=null;
                }
            });

            aktivitasproyek.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aktivitasproyek.setLocationRelativeTo(internalFrame1);
        }
        if (aktivitasproyek == null) return;
        if (!aktivitasproyek.isVisible()) {
            aktivitasproyek.isCek();
        }
        if (aktivitasproyek.isVisible()) {
            aktivitasproyek.toFront();
            return;
        }
        aktivitasproyek.setVisible(true);
    }//GEN-LAST:event_BtnAktivitasActionPerformed

    private void BtnAktivitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAktivitasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAktivitasActionPerformed(null);
        }
    }//GEN-LAST:event_BtnAktivitasKeyPressed

    private void KodeRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeRisikoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnRisikoActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodeAktivitas,AlasanKelas);
        }
    }//GEN-LAST:event_KodeRisikoKeyPressed

    private void NamaRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaRisikoKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaRisikoKeyPressed

    private void BtnRisikoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRisikoActionPerformed
        if (kelasrisikopencegahan == null || !kelasrisikopencegahan.isDisplayable()) {
            kelasrisikopencegahan=new PCRAICRACariKelasRisikoPencegahan(null,false);
            kelasrisikopencegahan.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            kelasrisikopencegahan.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(kelasrisikopencegahan.getTable().getSelectedRow()!= -1){
                        KodeRisiko.setText(kelasrisikopencegahan.getTable().getValueAt(kelasrisikopencegahan.getTable().getSelectedRow(),0).toString());
                        NamaRisiko.setText(kelasrisikopencegahan.getTable().getValueAt(kelasrisikopencegahan.getTable().getSelectedRow(),1).toString());
                        PreparedStatement ps2=null;
                        ResultSet rs2=null;
                        if((!KodeAktivitas.getText().trim().equals(""))&&(!KodeRisiko.getText().trim().equals(""))){
                            try{
                                ps2=koneksi.prepareStatement("select pcra_icra_kelas_kewaspadaan.kode_kewaspadaan,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,pcra_icra_kelas_kewaspadaan.perlu_izin from pcra_icra_matriks_kelas_kewaspadaan "+
                                    "inner join pcra_icra_kelas_kewaspadaan on pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_matriks_kelas_kewaspadaan.kode_kewaspadaan "+
                                    "where pcra_icra_matriks_kelas_kewaspadaan.kode_aktivitas=? and pcra_icra_matriks_kelas_kewaspadaan.kode_kelas=?");
                                try {
                                    ps2.setString(1,KodeAktivitas.getText());
                                    ps2.setString(2,KodeRisiko.getText());
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        KodeKewaspadaan.setText(rs2.getString(1));
                                        NamaKewaspadaan.setText(rs2.getString(2));
                                        PerluIzin.setText(rs2.getString(3));
                                    }else{
                                        KodeKewaspadaan.setText("");
                                        NamaKewaspadaan.setText("");
                                        PerluIzin.setText("");
                                        JOptionPane.showMessageDialog(null,"Kelas ICRA untuk kombinasi tipe pekerjaan & risiko pasien ini belum diatur pada master Matriks Kelas Kewaspadaan..!!");
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notif : "+ex);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }
                            }catch(Exception ex){
                                System.out.println("Notif : "+ex);
                            }
                        }
                    }
                    AlasanKelas.requestFocus();
                    kelasrisikopencegahan=null;
                }
            });

            kelasrisikopencegahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kelasrisikopencegahan.setLocationRelativeTo(internalFrame1);
        }
        if (kelasrisikopencegahan == null) return;
        if (!kelasrisikopencegahan.isVisible()) {
            kelasrisikopencegahan.isCek();
        }
        if (kelasrisikopencegahan.isVisible()) {
            kelasrisikopencegahan.toFront();
            return;
        }
        kelasrisikopencegahan.setVisible(true);
    }//GEN-LAST:event_BtnRisikoActionPerformed

    private void BtnRisikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRisikoKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnRisikoActionPerformed(null);
        }
    }//GEN-LAST:event_BtnRisikoKeyPressed

    private void KodeKewaspadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeKewaspadaanKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_KodeKewaspadaanKeyPressed

    private void NamaKewaspadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaKewaspadaanKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaKewaspadaanKeyPressed

    private void PerluIzinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerluIzinKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_PerluIzinKeyPressed

    private void AlasanKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKelasKeyPressed
        Valid.pindah(evt,KodeRisiko,TCariPengendalian);
    }//GEN-LAST:event_AlasanKelasKeyPressed

    private void TCariPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPengendalianKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            runBackground(() ->tampilPengendalian2());
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Keputusan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            AlasanKelas.requestFocus();
        }
    }//GEN-LAST:event_TCariPengendalianKeyPressed

    private void BtnCariPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPengendalianActionPerformed
        runBackground(() ->tampilPengendalian2());
    }//GEN-LAST:event_BtnCariPengendalianActionPerformed

    private void BtnCariPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPengendalianKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            runBackground(() ->tampilPengendalian2());
        }
    }//GEN-LAST:event_BtnCariPengendalianKeyPressed

    private void BtnAllPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPengendalianActionPerformed
        TCariPengendalian.setText("");
        runBackground(() ->tampilPengendalian());
    }//GEN-LAST:event_BtnAllPengendalianActionPerformed

    private void BtnAllPengendalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPengendalianKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllPengendalianActionPerformed(null);
        }
    }//GEN-LAST:event_BtnAllPengendalianKeyPressed

    private void BtnTambahPengendalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPengendalianActionPerformed
        if (tindakanpengendalian == null || !tindakanpengendalian.isDisplayable()) {
            tindakanpengendalian=new PCRAICRATindakanPengendalian(null,false);
            tindakanpengendalian.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            tindakanpengendalian.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    tindakanpengendalian=null;
                }
            });

            tindakanpengendalian.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            tindakanpengendalian.setLocationRelativeTo(internalFrame1);
        }
        if (tindakanpengendalian == null) return;
        if (tindakanpengendalian.isVisible()) {
            tindakanpengendalian.toFront();
            return;
        }
        tindakanpengendalian.setVisible(true);
    }//GEN-LAST:event_BtnTambahPengendalianActionPerformed

    private void KeputusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanKeyPressed
        Valid.pindah(evt,TCariPengendalian,Persyaratan);
    }//GEN-LAST:event_KeputusanKeyPressed

    private void PersyaratanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersyaratanKeyPressed
        Valid.pindah(evt,Keputusan,KodePemohon);
    }//GEN-LAST:event_PersyaratanKeyPressed

    private void KodePemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePemohonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPemohonActionPerformed(null);
        }else{            
            Valid.pindah(evt,Persyaratan,KodeIPSRS);
        }
    }//GEN-LAST:event_KodePemohonKeyPressed

    private void NamaPemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPemohonKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaPemohonKeyPressed

    private void BtnPemohonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPemohonActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KodePemohon.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaPemohon.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    }
                    KodePemohon.requestFocus();
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            pegawai.emptTeks();
        }
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPemohonActionPerformed

    private void BtnPemohonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPemohonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPemohonActionPerformed(null);
        }
    }//GEN-LAST:event_BtnPemohonKeyPressed

    private void KodeIPSRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeIPSRSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnIPSRSActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodePemohon,KodeK3RS);
        }
    }//GEN-LAST:event_KodeIPSRSKeyPressed

    private void NamaIPSRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaIPSRSKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaIPSRSKeyPressed

    private void BtnIPSRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIPSRSActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KodeIPSRS.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaIPSRS.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    }
                    KodeIPSRS.requestFocus();
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            pegawai.emptTeks();
        }
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnIPSRSActionPerformed

    private void BtnIPSRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIPSRSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnIPSRSActionPerformed(null);
        }
    }//GEN-LAST:event_BtnIPSRSKeyPressed

    private void KodeK3RSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeK3RSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnK3RSActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodeIPSRS,KodePPI);
        }
    }//GEN-LAST:event_KodeK3RSKeyPressed

    private void NamaK3RSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaK3RSKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaK3RSKeyPressed

    private void BtnK3RSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnK3RSActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KodeK3RS.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaK3RS.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    }
                    KodeK3RS.requestFocus();
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            pegawai.emptTeks();
        }
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnK3RSActionPerformed

    private void BtnK3RSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnK3RSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnK3RSActionPerformed(null);
        }
    }//GEN-LAST:event_BtnK3RSKeyPressed

    private void KodePPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePPIKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPPIActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodeK3RS,KodeManajemen);
        }
    }//GEN-LAST:event_KodePPIKeyPressed

    private void NamaPPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPPIKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaPPIKeyPressed

    private void BtnPPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPPIActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KodePPI.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaPPI.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    }
                    KodePPI.requestFocus();
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            pegawai.emptTeks();
        }
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPPIActionPerformed

    private void BtnPPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPPIKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPPIActionPerformed(null);
        }
    }//GEN-LAST:event_BtnPPIKeyPressed

    private void KodeManajemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeManajemenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnManajemenActionPerformed(null);
        }else{            
            Valid.pindah(evt,KodePPI,BtnSimpan);
        }
    }//GEN-LAST:event_KodeManajemenKeyPressed

    private void NamaManajemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaManajemenKeyPressed
        // kolom hanya baca
    }//GEN-LAST:event_NamaManajemenKeyPressed

    private void BtnManajemenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnManajemenActionPerformed
        if (pegawai == null || !pegawai.isDisplayable()) {
            pegawai=new DlgCariPegawai(null,false);
            pegawai.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            pegawai.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(pegawai.getTable().getSelectedRow()!= -1){
                        KodeManajemen.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        NamaManajemen.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    }
                    KodeManajemen.requestFocus();
                    pegawai=null;
                }
            });

            pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pegawai.setLocationRelativeTo(internalFrame1);
        }
        if (pegawai == null) return;
        if (!pegawai.isVisible()) {
            pegawai.emptTeks();
        }
        if (pegawai.isVisible()) {
            pegawai.toFront();
            return;
        }
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnManajemenActionPerformed

    private void BtnManajemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnManajemenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnManajemenActionPerformed(null);
        }
    }//GEN-LAST:event_BtnManajemenKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCRAICRAPengkajianRisikoInfeksi dialog = new PCRAICRAPengkajianRisikoInfeksi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox AlasanKelas;
    private widget.ComboBox AreaBeroperasi;
    private widget.Button BtnAktivitas;
    private widget.Button BtnAll;
    private widget.Button BtnAllArea;
    private widget.Button BtnAllPengendalian;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariArea;
    private widget.Button BtnCariPengendalian;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnIPSRS;
    private widget.Button BtnK3RS;
    private widget.Button BtnKeluar;
    private widget.Button BtnManajemen;
    private widget.Button BtnPCRA;
    private widget.Button BtnPemohon;
    private widget.Button BtnPPI;
    private widget.Button BtnPrint;
    private widget.Button BtnRisiko;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahArea;
    private widget.Button BtnTambahPengendalian;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.PanelBiasa FormRelasiData;
    private widget.Label icLabel1;
    private widget.Label icLabel10;
    private widget.Label icLabel11;
    private widget.Label icLabel12;
    private widget.Label icLabel13;
    private widget.Label icLabel14;
    private widget.Label icLabel15;
    private widget.Label icLabel16;
    private widget.Label icLabel17;
    private widget.Label icLabel18;
    private widget.Label icLabel19;
    private widget.Label icLabel2;
    private widget.Label icLabel20;
    private widget.Label icLabel21;
    private widget.Label icLabel22;
    private widget.Label icLabel23;
    private widget.Label icLabel24;
    private widget.Label icLabel25;
    private widget.Label icLabel26;
    private widget.Label icLabel27;
    private widget.Label icLabel28;
    private widget.Label icLabel29;
    private widget.Label icLabel3;
    private widget.Label icLabel30;
    private widget.Label icLabel31;
    private widget.Label icLabel32;
    private widget.Label icLabel33;
    private widget.Label icLabel34;
    private widget.Label icLabel35;
    private widget.Label icLabel36;
    private widget.Label icLabel37;
    private widget.Label icLabel38;
    private widget.Label icLabel4;
    private widget.Label icLabel5;
    private widget.Label icLabel6;
    private widget.Label icLabel7;
    private widget.Label icLabel8;
    private widget.Label icLabel9;
    private javax.swing.JSeparator icSep1;
    private javax.swing.JSeparator icSep2;
    private javax.swing.JSeparator icSep3;
    private javax.swing.JSeparator icSep4;
    private javax.swing.JSeparator icSep5;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.ComboBox JamMulaiJam;
    private widget.ComboBox JamMulaiMenit;
    private widget.ComboBox JamSelesaiJam;
    private widget.ComboBox JamSelesaiMenit;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabelICRAData;
    private widget.PopupMenu jPopupMenu1;
    private widget.ComboBox Keputusan;
    private widget.TextBox KeteranganOperasi;
    private widget.TextBox KodeAktivitas;
    private widget.TextBox KodeIPSRS;
    private widget.TextBox KodeK3RS;
    private widget.TextBox KodeKewaspadaan;
    private widget.TextBox KodeManajemen;
    private widget.TextBox KodePemohon;
    private widget.TextBox KodePPI;
    private widget.TextBox KodeRisiko;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox LokasiProyek;
    private widget.TextBox LokasiSekitar;
    private widget.MenuItem MnFormulir;
    private widget.TextBox NamaAktivitas;
    private widget.TextBox NamaIPSRS;
    private widget.TextBox NamaK3RS;
    private widget.TextBox NamaKewaspadaan;
    private widget.TextBox NamaManajemen;
    private widget.TextBox NamaPemohon;
    private widget.TextBox NamaPPI;
    private widget.TextBox NamaProyek;
    private widget.TextBox NamaRisiko;
    private widget.TextBox NoICRA;
    private widget.TextBox NoPCRA;
    private widget.PanelBiasa PanelAccor;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TextBox Pelaksana;
    private widget.TextBox PerluIzin;
    private widget.TextBox Persyaratan;
    private widget.TextBox ProyekMulai;
    private widget.TextBox ProyekSelesai;
    private widget.TextBox RutePekerja;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane ScrollArea;
    private widget.ScrollPane scrollAreaData;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane ScrollPengendalian;
    private widget.ScrollPane scrollPengendalianData;
    private widget.ScrollPane scrollRelasiData;
    private widget.TabPane TabRawat;
    private widget.Tanggal TanggalPengkajian;
    private widget.Table tbArea;
    private widget.Table tbAreaData;
    private widget.Table tbObat;
    private widget.Table tbPengendalian;
    private widget.Table tbPengendalianData;
    private widget.TextBox TCari;
    private widget.TextBox TCariArea;
    private widget.TextBox TCariPengendalian;
    private widget.TextBox TNamaProyekData;
    private widget.TextBox TNoICRAData;
    private widget.TextBox UraianPekerjaan;
    private widget.ComboBox WaktuKerja;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select "+
                    "pcra_icra_pengkajian_risiko_infeksi.no_icra,pcra_icra_pengkajian_risiko_infeksi.no_pcra,pcra_icra_pengkajian_risiko_prakonstruksi.nama_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.lokasi_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.pelaksana_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.mulai_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.perkiraan_selesai,pcra_icra_pengkajian_risiko_prakonstruksi.deskripsi_pekerjaan,pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian,ifnull(pcra_icra_pengkajian_risiko_infeksi.lokasi_sekitar,'') "+
                    "as lokasi_sekitar,ifnull(pcra_icra_pengkajian_risiko_infeksi.rute_pekerja,'') as "+
                    "rute_pekerja,pcra_icra_pengkajian_risiko_infeksi.jam_mulai,pcra_icra_pengkajian_risiko_infeksi.jam_selesai,ifnull(pcra_icra_pengkajian_risiko_infeksi.waktu_kerja,'') as "+
                    "waktu_kerja,ifnull(pcra_icra_pengkajian_risiko_infeksi.area_tetap_beroperasi,'') as area_tetap_beroperasi,ifnull(pcra_icra_pengkajian_risiko_infeksi.keterangan_operasi,'') as "+
                    "keterangan_operasi,pcra_icra_pengkajian_risiko_infeksi.kode_kelas_risiko,pcra_icra_kelas_risiko_pencegahan.nama_kelas,pcra_icra_pengkajian_risiko_infeksi.kode_aktivitas,pcra_icra_jenis_aktivitas_proyek.nama_aktivitas,pcra_icra_pengkajian_risiko_infeksi.kode_kewaspadaan,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,ifnull(pcra_icra_kelas_kewaspadaan.perlu_izin,'') "+
                    "as perlu_izin,ifnull(pcra_icra_pengkajian_risiko_infeksi.alasan_kelas,'') as alasan_kelas,pcra_icra_pengkajian_risiko_infeksi.keputusan,ifnull(pcra_icra_pengkajian_risiko_infeksi.persyaratan,'') as "+
                    "persyaratan,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_pemohon,'') as nik_pemohon,ifnull(pemohon.nama,'') as nama_pemohon,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_ipsrs,'') as "+
                    "nik_ipsrs,ifnull(ipsrs.nama,'') as nama_ipsrs,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_k3rs,'') as nik_k3rs,ifnull(k3rs.nama,'') as "+
                    "nama_k3rs,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_ppi,'') as nik_ppi,ifnull(ppi.nama,'') as nama_ppi,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_manajemen,'') as "+
                    "nik_manajemen,ifnull(manajemen.nama,'') as nama_manajemen from pcra_icra_pengkajian_risiko_infeksi inner join pcra_icra_pengkajian_risiko_prakonstruksi on "+
                    "pcra_icra_pengkajian_risiko_prakonstruksi.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner join pcra_icra_kelas_risiko_pencegahan on "+
                    "pcra_icra_kelas_risiko_pencegahan.kode_kelas=pcra_icra_pengkajian_risiko_infeksi.kode_kelas_risiko inner join pcra_icra_jenis_aktivitas_proyek on "+
                    "pcra_icra_jenis_aktivitas_proyek.kode_aktivitas=pcra_icra_pengkajian_risiko_infeksi.kode_aktivitas inner join pcra_icra_kelas_kewaspadaan on "+
                    "pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_pengkajian_risiko_infeksi.kode_kewaspadaan left join pegawai as pemohon on "+
                    "pemohon.nik=pcra_icra_pengkajian_risiko_infeksi.nik_pemohon left join pegawai as ipsrs on ipsrs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ipsrs left join pegawai as k3rs on "+
                    "k3rs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_k3rs left join pegawai as ppi on ppi.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ppi left join pegawai as manajemen on "+
                    "manajemen.nik=pcra_icra_pengkajian_risiko_infeksi.nik_manajemen where pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian between ? and ?"
                );
            }else{
                ps=koneksi.prepareStatement(
                    "select "+
                    "pcra_icra_pengkajian_risiko_infeksi.no_icra,pcra_icra_pengkajian_risiko_infeksi.no_pcra,pcra_icra_pengkajian_risiko_prakonstruksi.nama_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.lokasi_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.pelaksana_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.mulai_proyek,pcra_icra_pengkajian_risiko_prakonstruksi.perkiraan_selesai,pcra_icra_pengkajian_risiko_prakonstruksi.deskripsi_pekerjaan,pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian,ifnull(pcra_icra_pengkajian_risiko_infeksi.lokasi_sekitar,'') "+
                    "as lokasi_sekitar,ifnull(pcra_icra_pengkajian_risiko_infeksi.rute_pekerja,'') as "+
                    "rute_pekerja,pcra_icra_pengkajian_risiko_infeksi.jam_mulai,pcra_icra_pengkajian_risiko_infeksi.jam_selesai,ifnull(pcra_icra_pengkajian_risiko_infeksi.waktu_kerja,'') as "+
                    "waktu_kerja,ifnull(pcra_icra_pengkajian_risiko_infeksi.area_tetap_beroperasi,'') as area_tetap_beroperasi,ifnull(pcra_icra_pengkajian_risiko_infeksi.keterangan_operasi,'') as "+
                    "keterangan_operasi,pcra_icra_pengkajian_risiko_infeksi.kode_kelas_risiko,pcra_icra_kelas_risiko_pencegahan.nama_kelas,pcra_icra_pengkajian_risiko_infeksi.kode_aktivitas,pcra_icra_jenis_aktivitas_proyek.nama_aktivitas,pcra_icra_pengkajian_risiko_infeksi.kode_kewaspadaan,pcra_icra_kelas_kewaspadaan.nama_kewaspadaan,ifnull(pcra_icra_kelas_kewaspadaan.perlu_izin,'') "+
                    "as perlu_izin,ifnull(pcra_icra_pengkajian_risiko_infeksi.alasan_kelas,'') as alasan_kelas,pcra_icra_pengkajian_risiko_infeksi.keputusan,ifnull(pcra_icra_pengkajian_risiko_infeksi.persyaratan,'') as "+
                    "persyaratan,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_pemohon,'') as nik_pemohon,ifnull(pemohon.nama,'') as nama_pemohon,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_ipsrs,'') as "+
                    "nik_ipsrs,ifnull(ipsrs.nama,'') as nama_ipsrs,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_k3rs,'') as nik_k3rs,ifnull(k3rs.nama,'') as "+
                    "nama_k3rs,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_ppi,'') as nik_ppi,ifnull(ppi.nama,'') as nama_ppi,ifnull(pcra_icra_pengkajian_risiko_infeksi.nik_manajemen,'') as "+
                    "nik_manajemen,ifnull(manajemen.nama,'') as nama_manajemen from pcra_icra_pengkajian_risiko_infeksi inner join pcra_icra_pengkajian_risiko_prakonstruksi on "+
                    "pcra_icra_pengkajian_risiko_prakonstruksi.no_pcra=pcra_icra_pengkajian_risiko_infeksi.no_pcra inner join pcra_icra_kelas_risiko_pencegahan on "+
                    "pcra_icra_kelas_risiko_pencegahan.kode_kelas=pcra_icra_pengkajian_risiko_infeksi.kode_kelas_risiko inner join pcra_icra_jenis_aktivitas_proyek on "+
                    "pcra_icra_jenis_aktivitas_proyek.kode_aktivitas=pcra_icra_pengkajian_risiko_infeksi.kode_aktivitas inner join pcra_icra_kelas_kewaspadaan on "+
                    "pcra_icra_kelas_kewaspadaan.kode_kewaspadaan=pcra_icra_pengkajian_risiko_infeksi.kode_kewaspadaan left join pegawai as pemohon on "+
                    "pemohon.nik=pcra_icra_pengkajian_risiko_infeksi.nik_pemohon left join pegawai as ipsrs on ipsrs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ipsrs left join pegawai as k3rs on "+
                    "k3rs.nik=pcra_icra_pengkajian_risiko_infeksi.nik_k3rs left join pegawai as ppi on ppi.nik=pcra_icra_pengkajian_risiko_infeksi.nik_ppi left join pegawai as manajemen on "+
                    "manajemen.nik=pcra_icra_pengkajian_risiko_infeksi.nik_manajemen where pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian between ? and ? and (pcra_icra_pengkajian_risiko_infeksi.no_icra like ? or "+
                    "pcra_icra_pengkajian_risiko_infeksi.no_pcra like ? or pcra_icra_pengkajian_risiko_prakonstruksi.nama_proyek like ? or pcra_icra_jenis_aktivitas_proyek.nama_aktivitas "+
                    "like ? or pcra_icra_kelas_kewaspadaan.nama_kewaspadaan like ?)"
                );
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_icra"),rs.getString("no_pcra"),rs.getString("nama_proyek"),rs.getString("lokasi_proyek"),
                        rs.getString("pelaksana_proyek"),rs.getString("mulai_proyek"),rs.getString("perkiraan_selesai"),rs.getString("deskripsi_pekerjaan"),
                        rs.getString("tanggal_pengkajian"),rs.getString("lokasi_sekitar"),rs.getString("rute_pekerja"),rs.getString("jam_mulai"),
                        rs.getString("jam_selesai"),rs.getString("waktu_kerja"),rs.getString("area_tetap_beroperasi"),rs.getString("keterangan_operasi"),
                        rs.getString("kode_kelas_risiko"),rs.getString("nama_kelas"),rs.getString("kode_aktivitas"),rs.getString("nama_aktivitas"),
                        rs.getString("kode_kewaspadaan"),rs.getString("nama_kewaspadaan"),rs.getString("perlu_izin"),rs.getString("alasan_kelas"),
                        rs.getString("keputusan"),rs.getString("persyaratan"),rs.getString("nik_pemohon"),rs.getString("nama_pemohon"),
                        rs.getString("nik_ipsrs"),rs.getString("nama_ipsrs"),rs.getString("nik_k3rs"),rs.getString("nama_k3rs"),
                        rs.getString("nik_ppi"),rs.getString("nama_ppi"),rs.getString("nik_manajemen"),rs.getString("nama_manajemen")
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

    public void emptTeks() {
        NoPCRA.setText("");
        NamaProyek.setText("");
        LokasiProyek.setText("");
        Pelaksana.setText("");
        ProyekMulai.setText("");
        ProyekSelesai.setText("");
        UraianPekerjaan.setText("");
        TanggalPengkajian.setDate(new Date());
        TCariArea.setText("");
        Valid.tabelKosong(tabModeArea);
        tampilArea2();
        TCariPengendalian.setText("");
        Valid.tabelKosong(tabModePengendalian);
        tampilPengendalian2();
        LokasiSekitar.setText("");
        RutePekerja.setText("");
        KeteranganOperasi.setText("");
        KodeAktivitas.setText("");
        NamaAktivitas.setText("");
        KodeRisiko.setText("");
        NamaRisiko.setText("");
        KodeKewaspadaan.setText("");
        NamaKewaspadaan.setText("");
        PerluIzin.setText("");
        AlasanKelas.setText("");
        Persyaratan.setText("");
        JamMulaiJam.setSelectedIndex(0);
        JamMulaiMenit.setSelectedIndex(0);
        JamSelesaiJam.setSelectedIndex(0);
        JamSelesaiMenit.setSelectedIndex(0);
        WaktuKerja.setSelectedIndex(0);
        AreaBeroperasi.setSelectedIndex(0);
        Keputusan.setSelectedIndex(0);
        KodePemohon.setText("");
        NamaPemohon.setText("");
        KodeIPSRS.setText("");
        NamaIPSRS.setText("");
        KodeK3RS.setText("");
        NamaK3RS.setText("");
        KodePPI.setText("");
        NamaPPI.setText("");
        KodeManajemen.setText("");
        NamaManajemen.setText("");
        if(akses.getjml2()>=1){
            KodePPI.setText(akses.getkode());
            NamaPPI.setText(Sequel.CariPegawai(KodePPI.getText()));
        }
        NoICRA.requestFocus();
        TabRawat.setSelectedIndex(0);
        Valid.autoNomer3(
            "select ifnull(MAX(CONVERT(RIGHT(pcra_icra_pengkajian_risiko_infeksi.no_icra,3),signed)),0) from pcra_icra_pengkajian_risiko_infeksi where date_format(pcra_icra_pengkajian_risiko_infeksi.tanggal_pengkajian,'%Y-%m-%d')='"+Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+"' ",
            "ICRA"+TanggalPengkajian.getSelectedItem().toString().substring(6,10)+TanggalPengkajian.getSelectedItem().toString().substring(3,5)+TanggalPengkajian.getSelectedItem().toString().substring(0,2),3,NoICRA
        ); 
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoICRA.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            NoPCRA.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NamaProyek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            LokasiProyek.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Pelaksana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            ProyekMulai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            ProyekSelesai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            UraianPekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            LokasiSekitar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            RutePekerja.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KeteranganOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KodeRisiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NamaRisiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KodeAktivitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NamaAktivitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KodeKewaspadaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NamaKewaspadaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            PerluIzin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            AlasanKelas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Persyaratan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KodePemohon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NamaPemohon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KodeIPSRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NamaIPSRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KodeK3RS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NamaK3RS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KodePPI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NamaPPI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KodeManajemen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NamaManajemen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Valid.SetTgl2(TanggalPengkajian,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            JamMulaiJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(0,2));
            JamMulaiMenit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().substring(3,5));
            JamSelesaiJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().substring(0,2));
            JamSelesaiMenit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().substring(3,5));
            WaktuKerja.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            AreaBeroperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Keputusan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Valid.tabelKosong(tabModeArea);
            for (i = 0; i < tbAreaData.getRowCount(); i++) {
                tabModeArea.addRow(new Object[]{
                    true,tbAreaData.getValueAt(i,0).toString(),tbAreaData.getValueAt(i,1).toString(),tbAreaData.getValueAt(i,2).toString()
                });
            }
            Valid.tabelKosong(tabModePengendalian);
            for (i = 0; i < tbPengendalianData.getRowCount(); i++) {
                tabModePengendalian.addRow(new Object[]{
                    true,tbPengendalianData.getValueAt(i,0).toString(),tbPengendalianData.getValueAt(i,1).toString(),tbPengendalianData.getValueAt(i,2).toString(),tbPengendalianData.getValueAt(i,3).toString()
                });
            }
        }
    }

    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);
            scrollRelasiData.setVisible(true);
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);
            scrollRelasiData.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void tampilRelasi() {
        if(tbObat.getSelectedRow()!= -1){
            TNoICRAData.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNamaProyekData.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            try {
                Valid.tabelKosong(tabModeAreaData);
                ps=koneksi.prepareStatement(
                    "select pcra_icra_lokasi_kelompok_risiko_area.kode_area,pcra_icra_lokasi_kelompok_risiko_area.nama_area,pcra_icra_pengkajian_risiko_infeksi_area.keterangan from pcra_icra_lokasi_kelompok_risiko_area "+
                    "inner join pcra_icra_pengkajian_risiko_infeksi_area on pcra_icra_pengkajian_risiko_infeksi_area.kode_area=pcra_icra_lokasi_kelompok_risiko_area.kode_area "+
                    "where pcra_icra_pengkajian_risiko_infeksi_area.no_icra=?"
                );
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeAreaData.addRow(new Object[]{rs.getString("kode_area"),rs.getString("nama_area"),rs.getString("keterangan")});
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
            
            try {
                Valid.tabelKosong(tabModePengendalianData);
                ps=koneksi.prepareStatement(
                    "select pcra_icra_tindakan_pengendalian.kode_pengendalian,pcra_icra_tindakan_pengendalian.nama_pengendalian,pcra_icra_pengkajian_risiko_infeksi_pengendalian.rencana,pcra_icra_pengkajian_risiko_infeksi_pengendalian.pic from pcra_icra_tindakan_pengendalian "+
                    "inner join pcra_icra_pengkajian_risiko_infeksi_pengendalian on pcra_icra_pengkajian_risiko_infeksi_pengendalian.kode_pengendalian=pcra_icra_tindakan_pengendalian.kode_pengendalian "+
                    "where pcra_icra_pengkajian_risiko_infeksi_pengendalian.no_icra=?"
                );
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModePengendalianData.addRow(new Object[]{rs.getString("kode_pengendalian"),rs.getString("nama_pengendalian"),rs.getString("rencana"),rs.getString("pic")});
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

    private void ganti() {
        if(Sequel.mengedittf2("pcra_icra_pengkajian_risiko_infeksi","no_icra=?","no_pcra=?,tanggal_pengkajian=?,lokasi_sekitar=?,rute_pekerja=?,jam_mulai=?,jam_selesai=?,waktu_kerja=?,area_tetap_beroperasi=?,keterangan_operasi=?,kode_kelas_risiko=?,kode_aktivitas=?,kode_kewaspadaan=?,alasan_kelas=?,keputusan=?,persyaratan=?,nik_pemohon=?,nik_ipsrs=?,nik_k3rs=?,nik_ppi=?,nik_manajemen=?,no_icra=?",22,new String[]{
            NoPCRA.getText(),Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),LokasiSekitar.getText(),
            RutePekerja.getText(),JamMulaiJam.getSelectedItem()+":"+JamMulaiMenit.getSelectedItem()+":00",JamSelesaiJam.getSelectedItem()+":"+JamSelesaiMenit.getSelectedItem()+":00",
            WaktuKerja.getSelectedItem().toString(),AreaBeroperasi.getSelectedItem().toString(),KeteranganOperasi.getText(),
            KodeRisiko.getText(),KodeAktivitas.getText(),KodeKewaspadaan.getText(),
            AlasanKelas.getText(),Keputusan.getSelectedItem().toString(),Persyaratan.getText(),
            KodePemohon.getText(),KodeIPSRS.getText().trim().equals("")?null:KodeIPSRS.getText(),KodeK3RS.getText().trim().equals("")?null:KodeK3RS.getText(),
            KodePPI.getText(),KodeManajemen.getText().trim().equals("")?null:KodeManajemen.getText(),NoICRA.getText(),
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            Valid.tabelKosong(tabModeAreaData);
            Valid.tabelKosong(tabModePengendalianData);
            Sequel.meghapus("pcra_icra_pengkajian_risiko_infeksi_area","no_icra",NoICRA.getText());
            Sequel.meghapus("pcra_icra_pengkajian_risiko_infeksi_pengendalian","no_icra",NoICRA.getText());
            for (i = 0; i < tbArea.getRowCount(); i++) {
                if(tbArea.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.menyimpantf2("pcra_icra_pengkajian_risiko_infeksi_area","?,?,?",3,new String[]{NoICRA.getText(),tbArea.getValueAt(i,1).toString(),tbArea.getValueAt(i,3).toString()})==true){
                        tabModeAreaData.addRow(new Object[]{tbArea.getValueAt(i,1).toString(),tbArea.getValueAt(i,2).toString(),tbArea.getValueAt(i,3).toString()});
                    }
                }
            }
            for (i = 0; i < tbPengendalian.getRowCount(); i++) {
                if(tbPengendalian.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.menyimpantf2("pcra_icra_pengkajian_risiko_infeksi_pengendalian","?,?,?,?",4,new String[]{NoICRA.getText(),tbPengendalian.getValueAt(i,1).toString(),tbPengendalian.getValueAt(i,3).toString(),tbPengendalian.getValueAt(i,4).toString()})==true){
                        tabModePengendalianData.addRow(new Object[]{tbPengendalian.getValueAt(i,1).toString(),tbPengendalian.getValueAt(i,2).toString(),tbPengendalian.getValueAt(i,3).toString(),tbPengendalian.getValueAt(i,4).toString()});
                    }
                }
            }
            int baris=tbObat.getSelectedRow();
            tabMode.setValueAt(NoICRA.getText(),baris,0);
            tabMode.setValueAt(NoPCRA.getText(),baris,1);
            tabMode.setValueAt(NamaProyek.getText(),baris,2);
            tabMode.setValueAt(LokasiProyek.getText(),baris,3);
            tabMode.setValueAt(Pelaksana.getText(),baris,4);
            tabMode.setValueAt(ProyekMulai.getText(),baris,5);
            tabMode.setValueAt(ProyekSelesai.getText(),baris,6);
            tabMode.setValueAt(UraianPekerjaan.getText(),baris,7);
            tabMode.setValueAt(Valid.SetTgl(TanggalPengkajian.getSelectedItem()+"")+" "+TanggalPengkajian.getSelectedItem().toString().substring(11,19),baris,8);
            tabMode.setValueAt(LokasiSekitar.getText(),baris,9);
            tabMode.setValueAt(RutePekerja.getText(),baris,10);
            tabMode.setValueAt(JamMulaiJam.getSelectedItem()+":"+JamMulaiMenit.getSelectedItem()+":00",baris,11);
            tabMode.setValueAt(JamSelesaiJam.getSelectedItem()+":"+JamSelesaiMenit.getSelectedItem()+":00",baris,12);
            tabMode.setValueAt(WaktuKerja.getSelectedItem().toString(),baris,13);
            tabMode.setValueAt(AreaBeroperasi.getSelectedItem().toString(),baris,14);
            tabMode.setValueAt(KeteranganOperasi.getText(),baris,15);
            tabMode.setValueAt(KodeRisiko.getText(),baris,16);
            tabMode.setValueAt(NamaRisiko.getText(),baris,17);
            tabMode.setValueAt(KodeAktivitas.getText(),baris,18);
            tabMode.setValueAt(NamaAktivitas.getText(),baris,19);
            tabMode.setValueAt(KodeKewaspadaan.getText(),baris,20);
            tabMode.setValueAt(NamaKewaspadaan.getText(),baris,21);
            tabMode.setValueAt(PerluIzin.getText(),baris,22);
            tabMode.setValueAt(AlasanKelas.getText(),baris,23);
            tabMode.setValueAt(Keputusan.getSelectedItem().toString(),baris,24);
            tabMode.setValueAt(Persyaratan.getText(),baris,25);
            tabMode.setValueAt(KodePemohon.getText(),baris,26);
            tabMode.setValueAt(NamaPemohon.getText(),baris,27);
            tabMode.setValueAt(KodeIPSRS.getText(),baris,28);
            tabMode.setValueAt(NamaIPSRS.getText(),baris,29);
            tabMode.setValueAt(KodeK3RS.getText(),baris,30);
            tabMode.setValueAt(NamaK3RS.getText(),baris,31);
            tabMode.setValueAt(KodePPI.getText(),baris,32);
            tabMode.setValueAt(NamaPPI.getText(),baris,33);
            tabMode.setValueAt(KodeManajemen.getText(),baris,34);
            tabMode.setValueAt(NamaManajemen.getText(),baris,35);
            TNoICRAData.setText(NoICRA.getText());
            TNamaProyekData.setText(NamaProyek.getText());
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from pcra_icra_pengkajian_risiko_infeksi where no_icra=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TNoICRAData.setText("");
            TNamaProyekData.setText("");
            Valid.tabelKosong(tabModeAreaData);
            Valid.tabelKosong(tabModePengendalianData);
            emptTeks();
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getpcra_icra_pengkajian_risiko_infeksi());
        BtnHapus.setEnabled(akses.getpcra_icra_pengkajian_risiko_infeksi());
        BtnEdit.setEnabled(akses.getpcra_icra_pengkajian_risiko_infeksi());
        if(akses.getjml2()>=1){
            KodePPI.setEditable(false);
            BtnPPI.setEnabled(false);
            KodePPI.setText(akses.getkode());
            NamaPPI.setText(Sequel.CariPegawai(KodePPI.getText()));
        } 
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void tampilArea() {
        try{
            Valid.tabelKosong(tabModeArea);
            file=new File("./cache/pcrapengkajianarea.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_lokasi_kelompok_risiko_area.kode_area,pcra_icra_lokasi_kelompok_risiko_area.nama_area from pcra_icra_lokasi_kelompok_risiko_area");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeArea.addRow(new Object[]{false,rs.getString(1),rs.getString(2),""});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcrapengkajianarea\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }finally {
            if (fileWriter != null) try { fileWriter.close(); } catch (Exception e) {}
        }
    }
    
    private void tampilArea2() {
        try{
            jml=0;
            for(i=0;i<tbArea.getRowCount();i++){
                if(tbArea.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            ket=new String[jml];

            index=0;        
            for(i=0;i<tbArea.getRowCount();i++){
                if(tbArea.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbArea.getValueAt(i,1).toString();
                    nama[index]=tbArea.getValueAt(i,2).toString();
                    ket[index]=tbArea.getValueAt(i,3).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeArea);

            for(i=0;i<jml;i++){
                tabModeArea.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],ket[i]
                });
            }
            
            myObj = new FileReader("./cache/pcrapengkajianarea.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcrapengkajianarea");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariArea.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariArea.getText().toLowerCase())){
                        boolean sudah=false;
                        for(i=0;i<jml;i++){
                            if(kode[i].equals(list.path("Kode").asText())){
                                sudah=true;
                            }
                        }
                        if(sudah==false){
                            tabModeArea.addRow(new Object[]{
                                false,list.path("Kode").asText(),list.path("Nama").asText(),""
                            });
                        }
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }finally {
            if (myObj != null) try { myObj.close(); } catch (Exception e) {}
            pilih=null;
            kode=null;
            nama=null;
            response = null;
            root = null;
        }
    }
    
    private void tampilPengendalian() {
        try{
            Valid.tabelKosong(tabModePengendalian);
            file=new File("./cache/pcrapengkajianpengendalian.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement("select pcra_icra_tindakan_pengendalian.kode_pengendalian,pcra_icra_tindakan_pengendalian.nama_pengendalian from pcra_icra_tindakan_pengendalian");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengendalian.addRow(new Object[]{false,rs.getString(1),rs.getString(2),"",""});
                    iyembuilder.append("{\"Kode\":\"").append(rs.getString(1)).append("\",\"Nama\":\"").append(rs.getString(2)).append("\"},");
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
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"pcrapengkajianpengendalian\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }finally {
            if (fileWriter != null) try { fileWriter.close(); } catch (Exception e) {}
        }
    }
    
    private void tampilPengendalian2() {
        try{
            jml=0;
            for(i=0;i<tbPengendalian.getRowCount();i++){
                if(tbPengendalian.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            rencana=new String[jml];
            pic=new String[jml];

            index=0;        
            for(i=0;i<tbPengendalian.getRowCount();i++){
                if(tbPengendalian.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPengendalian.getValueAt(i,1).toString();
                    nama[index]=tbPengendalian.getValueAt(i,2).toString();
                    rencana[index]=tbPengendalian.getValueAt(i,3).toString();
                    pic[index]=tbPengendalian.getValueAt(i,4).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModePengendalian);

            for(i=0;i<jml;i++){
                tabModePengendalian.addRow(new Object[] {
                    pilih[i],kode[i],nama[i],rencana[i],pic[i]
                });
            }
            
            myObj = new FileReader("./cache/pcrapengkajianpengendalian.iyem");
            root = mapper.readTree(myObj);
            response = root.path("pcrapengkajianpengendalian");
            if(response.isArray()){
                for(JsonNode list:response){
                    if(list.path("Kode").asText().toLowerCase().contains(TCariPengendalian.getText().toLowerCase())||list.path("Nama").asText().toLowerCase().contains(TCariPengendalian.getText().toLowerCase())){
                        boolean sudah=false;
                        for(i=0;i<jml;i++){
                            if(kode[i].equals(list.path("Kode").asText())){
                                sudah=true;
                            }
                        }
                        if(sudah==false){
                            tabModePengendalian.addRow(new Object[]{
                                false,list.path("Kode").asText(),list.path("Nama").asText(),"",""
                            });
                        }
                    }
                }
            }
            myObj.close();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }finally {
            if (myObj != null) try { myObj.close(); } catch (Exception e) {}
            pilih=null;
            kode=null;
            nama=null;
            response = null;
            root = null;
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

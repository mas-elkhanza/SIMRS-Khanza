package rekammedis;

import bridging.ApiOrthanc;
import bridging.OrthancDICOM;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;


/**
 *
 * @author perpustakaan
 */
public final class RMHasilPemeriksaanEchoPediatrik extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDicom;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    private JsonNode root;
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMHasilPemeriksaanEchoPediatrik(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Kode Dokter","Nama Dokter","Tanggal","Diagnosa Klinis","Kiriman Dari",
                "Situs","AV-VA","Drainase Vena Pulmonalis","Mitral","Aorta","Tricuspid","Pulmonal","Septum Atrium","Septum Ventrikal",
                "Arkus Aorta","Lain-lain","Ruang Jantung","IVDS","IVSS","LVID dextra","LVID sinistra","LVPW dextra","LVPW sinistra",
                "Ejection Fraction","Fraction Shotening","Doppler","Kesimpulan","Saran"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(180);
            }else if(i==10){
                column.setPreferredWidth(180);
            }else if(i==11){
                column.setPreferredWidth(180);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(180);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(180);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(180);
            }else if(i==20){
                column.setPreferredWidth(180);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(100);
            }else if(i==29){
                column.setPreferredWidth(180);
            }else if(i==30){
                column.setPreferredWidth(220);
            }else if(i==31){
                column.setPreferredWidth(180);
            }
        }
        
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDicom=new DefaultTableModel(null,new Object[]{
            "UUID Pasien","ID Studies","ID Series"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbListDicom.setModel(tabModeDicom);
        tbListDicom.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbListDicom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbListDicom.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(270);
            }else if(i==2){
                column.setPreferredWidth(270);
            }
        }
        tbListDicom.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KirimanDari.setDocument(new batasInput((int)50).getKata(KirimanDari));
        DiagnosaKlinis.setDocument(new batasInput((int)50).getKata(DiagnosaKlinis));
        Situs.setDocument(new batasInput((int)100).getKata(Situs));
        AVVA.setDocument(new batasInput((int)100).getKata(AVVA));
        Drainase.setDocument(new batasInput((int)100).getKata(Drainase));
        Mitral.setDocument(new batasInput((int)100).getKata(Mitral));
        Aorta.setDocument(new batasInput((int)100).getKata(Aorta));
        Tricuspid.setDocument(new batasInput((int)100).getKata(Tricuspid));
        Pulmonal.setDocument(new batasInput((int)100).getKata(Pulmonal));
        SeptumAtrium.setDocument(new batasInput((int)100).getKata(SeptumAtrium));
        SeptumVentrikal.setDocument(new batasInput((int)100).getKata(SeptumVentrikal));
        ArkusAorta.setDocument(new batasInput((int)100).getKata(ArkusAorta));
        Lainlain.setDocument(new batasInput((int)100).getKata(Lainlain));
        RuangJantung.setDocument(new batasInput((int)100).getKata(RuangJantung));
        IVDS.setDocument(new batasInput((int)20).getKata(IVDS));
        IVSS.setDocument(new batasInput((int)20).getKata(IVSS));
        LVIDdextra.setDocument(new batasInput((int)20).getKata(LVIDdextra));
        LVIDsinistra.setDocument(new batasInput((int)20).getKata(LVIDsinistra));
        LVPWdextra.setDocument(new batasInput((int)20).getKata(LVPWdextra));
        LVPWsinistra.setDocument(new batasInput((int)20).getKata(LVPWsinistra));
        EjectionFraction.setDocument(new batasInput((int)20).getKata(EjectionFraction));
        FractionShotening.setDocument(new batasInput((int)20).getKata(FractionShotening));
        Dopler.setDocument(new batasInput((int)100).getKata(Dopler));
        Kesimpulan.setDocument(new batasInput((int)250).getKata(Kesimpulan));
        Saran.setDocument(new batasInput((int)100).getKata(Saran));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(false);
        LoadHTML2.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Situs = new widget.TextBox();
        jLabel31 = new widget.Label();
        Pulmonal = new widget.TextBox();
        jLabel35 = new widget.Label();
        AVVA = new widget.TextBox();
        jLabel40 = new widget.Label();
        SeptumAtrium = new widget.TextBox();
        jLabel41 = new widget.Label();
        Tricuspid = new widget.TextBox();
        jLabel43 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        RuangJantung = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel42 = new widget.Label();
        Drainase = new widget.TextBox();
        Dopler = new widget.TextBox();
        jLabel30 = new widget.Label();
        KirimanDari = new widget.TextBox();
        jLabel32 = new widget.Label();
        DiagnosaKlinis = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel47 = new widget.Label();
        Mitral = new widget.TextBox();
        jLabel48 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        Aorta = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel55 = new widget.Label();
        SeptumVentrikal = new widget.TextBox();
        jLabel54 = new widget.Label();
        jLabel57 = new widget.Label();
        ArkusAorta = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel59 = new widget.Label();
        Lainlain = new widget.TextBox();
        jLabel58 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        IVDS = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        IVSS = new widget.TextBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        LVIDdextra = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        LVIDsinistra = new widget.TextBox();
        jLabel73 = new widget.Label();
        LVPWdextra = new widget.TextBox();
        jLabel70 = new widget.Label();
        LVPWsinistra = new widget.TextBox();
        jLabel71 = new widget.Label();
        EjectionFraction = new widget.TextBox();
        jLabel72 = new widget.Label();
        FractionShotening = new widget.TextBox();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        Saran = new widget.TextBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        TabData = new javax.swing.JTabbedPane();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        FormOrthan = new widget.PanelBiasa();
        Scroll6 = new widget.ScrollPane();
        tbListDicom = new widget.Table();
        panelGlass7 = new widget.panelisi();
        btnDicom = new widget.Button();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Formulir Hasil Pemeriksaan ECHOCARDIOGRAFI");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(320, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pemeriksaan ECHOCARDIOGRAFI Pediatrik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
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
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 763));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 110, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(186, 40, 295, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnDokter.setBounds(484, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(538, 40, 52, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025 23:45:34" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(594, 40, 130, 23);

        Situs.setFocusTraversalPolicyProvider(true);
        Situs.setName("Situs"); // NOI18N
        Situs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SitusKeyPressed(evt);
            }
        });
        FormInput.add(Situs);
        Situs.setBounds(51, 110, 673, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Situs");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(16, 110, 60, 23);

        Pulmonal.setFocusTraversalPolicyProvider(true);
        Pulmonal.setName("Pulmonal"); // NOI18N
        Pulmonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PulmonalKeyPressed(evt);
            }
        });
        FormInput.add(Pulmonal);
        Pulmonal.setBounds(91, 310, 633, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Pulmonal");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(36, 310, 135, 23);

        AVVA.setFocusTraversalPolicyProvider(true);
        AVVA.setName("AVVA"); // NOI18N
        AVVA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AVVAKeyPressed(evt);
            }
        });
        FormInput.add(AVVA);
        AVVA.setBounds(59, 140, 665, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("AV-VA");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(16, 140, 135, 23);

        SeptumAtrium.setFocusTraversalPolicyProvider(true);
        SeptumAtrium.setName("SeptumAtrium"); // NOI18N
        SeptumAtrium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeptumAtriumKeyPressed(evt);
            }
        });
        FormInput.add(SeptumAtrium);
        SeptumAtrium.setBounds(157, 360, 567, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("- Septum Atrium");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel41);
        jLabel41.setBounds(66, 360, 135, 23);

        Tricuspid.setFocusTraversalPolicyProvider(true);
        Tricuspid.setName("Tricuspid"); // NOI18N
        Tricuspid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TricuspidKeyPressed(evt);
            }
        });
        FormInput.add(Tricuspid);
        Tricuspid.setBounds(91, 280, 633, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Tricuspid");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(36, 280, 135, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Kesimpulan);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(83, 680, 641, 43);

        RuangJantung.setFocusTraversalPolicyProvider(true);
        RuangJantung.setName("RuangJantung"); // NOI18N
        RuangJantung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RuangJantungKeyPressed(evt);
            }
        });
        FormInput.add(RuangJantung);
        RuangJantung.setBounds(100, 480, 624, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Ruang Jantung");
        jLabel45.setName("jLabel45"); // NOI18N
        jLabel45.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel45);
        jLabel45.setBounds(16, 480, 135, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Drainase Vena Pulmonalis");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(16, 170, 135, 23);

        Drainase.setFocusTraversalPolicyProvider(true);
        Drainase.setName("Drainase"); // NOI18N
        Drainase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DrainaseKeyPressed(evt);
            }
        });
        FormInput.add(Drainase);
        Drainase.setBounds(154, 170, 570, 23);

        Dopler.setFocusTraversalPolicyProvider(true);
        Dopler.setName("Dopler"); // NOI18N
        Dopler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DoplerKeyPressed(evt);
            }
        });
        FormInput.add(Dopler);
        Dopler.setBounds(66, 650, 658, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Kiriman Dari");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(16, 80, 83, 23);

        KirimanDari.setFocusTraversalPolicyProvider(true);
        KirimanDari.setName("KirimanDari"); // NOI18N
        KirimanDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KirimanDariKeyPressed(evt);
            }
        });
        FormInput.add(KirimanDari);
        KirimanDari.setBounds(87, 80, 270, 23);

        jLabel32.setText("Diagnosa Klinis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(360, 80, 90, 23);

        DiagnosaKlinis.setFocusTraversalPolicyProvider(true);
        DiagnosaKlinis.setName("DiagnosaKlinis"); // NOI18N
        DiagnosaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKlinisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaKlinis);
        DiagnosaKlinis.setBounds(454, 80, 270, 23);

        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 80, 83, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 110, 47, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 140, 55, 23);

        jLabel37.setText(":");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 170, 150, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Katup :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(16, 200, 135, 23);

        Mitral.setFocusTraversalPolicyProvider(true);
        Mitral.setName("Mitral"); // NOI18N
        Mitral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MitralKeyPressed(evt);
            }
        });
        FormInput.add(Mitral);
        Mitral.setBounds(74, 220, 650, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Mitral");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(36, 220, 135, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 220, 70, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 250, 70, 23);

        Aorta.setFocusTraversalPolicyProvider(true);
        Aorta.setName("Aorta"); // NOI18N
        Aorta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AortaKeyPressed(evt);
            }
        });
        FormInput.add(Aorta);
        Aorta.setBounds(74, 250, 650, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Aorta");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(36, 250, 70, 23);

        jLabel50.setText(":");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 280, 87, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 310, 87, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Keterangan Lain :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(36, 340, 135, 23);

        jLabel53.setText(":");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 360, 153, 23);

        jLabel55.setText(":");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 390, 163, 23);

        SeptumVentrikal.setFocusTraversalPolicyProvider(true);
        SeptumVentrikal.setName("SeptumVentrikal"); // NOI18N
        SeptumVentrikal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeptumVentrikalKeyPressed(evt);
            }
        });
        FormInput.add(SeptumVentrikal);
        SeptumVentrikal.setBounds(167, 390, 557, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("- Septum Ventrikal");
        jLabel54.setName("jLabel54"); // NOI18N
        jLabel54.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel54);
        jLabel54.setBounds(66, 390, 135, 23);

        jLabel57.setText(":");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 420, 137, 23);

        ArkusAorta.setFocusTraversalPolicyProvider(true);
        ArkusAorta.setName("ArkusAorta"); // NOI18N
        ArkusAorta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ArkusAortaKeyPressed(evt);
            }
        });
        FormInput.add(ArkusAorta);
        ArkusAorta.setBounds(141, 420, 583, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("- Arkus Aorta");
        jLabel56.setName("jLabel56"); // NOI18N
        jLabel56.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel56);
        jLabel56.setBounds(66, 420, 135, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 450, 122, 23);

        Lainlain.setFocusTraversalPolicyProvider(true);
        Lainlain.setName("Lainlain"); // NOI18N
        Lainlain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKeyPressed(evt);
            }
        });
        FormInput.add(Lainlain);
        Lainlain.setBounds(126, 450, 598, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("- Lain-lain");
        jLabel58.setName("jLabel58"); // NOI18N
        jLabel58.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel58);
        jLabel58.setBounds(66, 450, 135, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 480, 96, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("M. Mode :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(16, 510, 135, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("IVDS");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(36, 530, 50, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 530, 67, 23);

        IVDS.setFocusTraversalPolicyProvider(true);
        IVDS.setName("IVDS"); // NOI18N
        IVDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IVDSKeyPressed(evt);
            }
        });
        FormInput.add(IVDS);
        IVDS.setBounds(71, 530, 260, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("IVSS");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(36, 560, 50, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 560, 67, 23);

        IVSS.setFocusTraversalPolicyProvider(true);
        IVSS.setName("IVSS"); // NOI18N
        IVSS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IVSSKeyPressed(evt);
            }
        });
        FormInput.add(IVSS);
        IVSS.setBounds(71, 560, 260, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("LVID dextra");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(36, 590, 70, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 590, 100, 23);

        LVIDdextra.setFocusTraversalPolicyProvider(true);
        LVIDdextra.setName("LVIDdextra"); // NOI18N
        LVIDdextra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LVIDdextraKeyPressed(evt);
            }
        });
        FormInput.add(LVIDdextra);
        LVIDdextra.setBounds(104, 590, 227, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("LVID sinistra");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(36, 620, 70, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 620, 105, 23);

        LVIDsinistra.setFocusTraversalPolicyProvider(true);
        LVIDsinistra.setName("LVIDsinistra"); // NOI18N
        LVIDsinistra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LVIDsinistraKeyPressed(evt);
            }
        });
        FormInput.add(LVIDsinistra);
        LVIDsinistra.setBounds(109, 620, 222, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Fraction Shotening");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(410, 620, 110, 23);

        LVPWdextra.setFocusTraversalPolicyProvider(true);
        LVPWdextra.setName("LVPWdextra"); // NOI18N
        LVPWdextra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LVPWdextraKeyPressed(evt);
            }
        });
        FormInput.add(LVPWdextra);
        LVPWdextra.setBounds(483, 530, 241, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("LVPW dextra");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(410, 530, 110, 23);

        LVPWsinistra.setFocusTraversalPolicyProvider(true);
        LVPWsinistra.setName("LVPWsinistra"); // NOI18N
        LVPWsinistra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LVPWsinistraKeyPressed(evt);
            }
        });
        FormInput.add(LVPWsinistra);
        LVPWsinistra.setBounds(488, 560, 236, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("LVPW sinistra");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(410, 560, 110, 23);

        EjectionFraction.setFocusTraversalPolicyProvider(true);
        EjectionFraction.setName("EjectionFraction"); // NOI18N
        EjectionFraction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EjectionFractionKeyPressed(evt);
            }
        });
        FormInput.add(EjectionFraction);
        EjectionFraction.setBounds(503, 590, 221, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Ejection Fraction");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(410, 590, 110, 23);

        FractionShotening.setFocusTraversalPolicyProvider(true);
        FractionShotening.setName("FractionShotening"); // NOI18N
        FractionShotening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FractionShoteningKeyPressed(evt);
            }
        });
        FormInput.add(FractionShotening);
        FractionShotening.setBounds(513, 620, 211, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Doppler");
        jLabel74.setName("jLabel74"); // NOI18N
        jLabel74.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel74);
        jLabel74.setBounds(16, 650, 135, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 650, 62, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Kesimpulan");
        jLabel76.setName("jLabel76"); // NOI18N
        jLabel76.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel76);
        jLabel76.setBounds(16, 680, 135, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 680, 79, 23);

        Saran.setFocusTraversalPolicyProvider(true);
        Saran.setName("Saran"); // NOI18N
        Saran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaranKeyPressed(evt);
            }
        });
        FormInput.add(Saran);
        Saran.setBounds(55, 730, 669, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Saran");
        jLabel78.setName("jLabel78"); // NOI18N
        jLabel78.setPreferredSize(new java.awt.Dimension(68, 14));
        FormInput.add(jLabel78);
        jLabel78.setBounds(16, 730, 135, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 730, 51, 23);

        jLabel80.setText(":");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(380, 530, 99, 23);

        jLabel81.setText(":");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(380, 560, 104, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(380, 590, 119, 23);

        jLabel83.setText(":");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(380, 620, 129, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Hasil Pemeriksaan ECHO Pediatrik", internalFrame2);

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

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
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

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabData.addTab("Gambar Pemeriksaan ECHO Pediatrik", FormPhoto);

        FormOrthan.setBackground(new java.awt.Color(255, 255, 255));
        FormOrthan.setBorder(null);
        FormOrthan.setName("FormOrthan"); // NOI18N
        FormOrthan.setPreferredSize(new java.awt.Dimension(115, 73));
        FormOrthan.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbListDicom.setName("tbListDicom"); // NOI18N
        Scroll6.setViewportView(tbListDicom);

        FormOrthan.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(115, 40));

        btnDicom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        btnDicom.setMnemonic('T');
        btnDicom.setText("Tampilkan DICOM");
        btnDicom.setToolTipText("Alt+T");
        btnDicom.setName("btnDicom"); // NOI18N
        btnDicom.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicomActionPerformed(evt);
            }
        });
        panelGlass7.add(btnDicom);

        FormOrthan.add(panelGlass7, java.awt.BorderLayout.PAGE_END);

        TabData.addTab("Integrasi Orthanc", FormOrthan);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Hasil Pemeriksaan ECHO Pediatrik", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
        TabRawat.getAccessibleContext().setAccessibleName("Input Hasil Pemeriksaan ECHOCARDIOGRAFI");

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Saran,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Klinis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kiriman Dari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Situs</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>AV-VA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Drainase Vena Pulmonalis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mitral</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Aorta</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tricuspid</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pulmonal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Septum Atrium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Septum Ventrikal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Arkus Aorta</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lain-lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ruang Jantung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>IVDS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>IVSS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>LVID dextra</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>LVID sinistra</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>LVPW dextra</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>LVPW sinistra</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ejection Fraction</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Fraction Shotening</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Doppler</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesimpulan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Saran</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataHasilPemeriksaanECHO.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA HASIL PEMERIKSAAN ECHO PEDIATRIK<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
                getData();
                tampilOrthanc();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,TCari,Tanggal);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,BtnDokter,Situs);
    }//GEN-LAST:event_TanggalKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),4).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())); 
            param.put("hasil","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanechopediatrik/"+Sequel.cariIsi("select hasil_pemeriksaan_echo_pediatrik_gambar.photo from hasil_pemeriksaan_echo_pediatrik_gambar where hasil_pemeriksaan_echo_pediatrik_gambar.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()));
            Valid.MyReportqry("rptCetakHasilPemeriksaanECHOPediatrik.jasper","report","::[ Formulir Hasil Pemeriksaan ECHOCARDIOGRAFI ]::",
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_echo_pediatrik.tanggal,"+
                            "hasil_pemeriksaan_echo_pediatrik.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_echo_pediatrik.diagnosa_klinis,hasil_pemeriksaan_echo_pediatrik.kiriman_dari,"+
                            "hasil_pemeriksaan_echo_pediatrik.situs,hasil_pemeriksaan_echo_pediatrik.av_va,hasil_pemeriksaan_echo_pediatrik.drainase_vena_pulmonalis,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_mitral,hasil_pemeriksaan_echo_pediatrik.katup_aorta,hasil_pemeriksaan_echo_pediatrik.katup_tricuspid,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_pulmonal,hasil_pemeriksaan_echo_pediatrik.katup_septum_atrium,hasil_pemeriksaan_echo_pediatrik.katup_septum_ventrikal,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_arkus_aorta,hasil_pemeriksaan_echo_pediatrik.katup_keterangan_lainnya,hasil_pemeriksaan_echo_pediatrik.ruang_jantung,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ivds,hasil_pemeriksaan_echo_pediatrik.mode_ivss,hasil_pemeriksaan_echo_pediatrik.mode_lvid_dextra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_lvid_sinistra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_dextra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_sinistra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ejection_fraction,hasil_pemeriksaan_echo_pediatrik.mode_fraction_shotening,hasil_pemeriksaan_echo_pediatrik.doppler,"+
                            "hasil_pemeriksaan_echo_pediatrik.kesimpulan,hasil_pemeriksaan_echo_pediatrik.saran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join hasil_pemeriksaan_echo_pediatrik on reg_periksa.no_rawat=hasil_pemeriksaan_echo_pediatrik.no_rawat "+
                            "inner join dokter on hasil_pemeriksaan_echo_pediatrik.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_echo_pediatrik.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void SitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SitusKeyPressed
        Valid.pindah(evt,DiagnosaKlinis,AVVA);
    }//GEN-LAST:event_SitusKeyPressed

    private void PulmonalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PulmonalKeyPressed
        Valid.pindah(evt,Tricuspid,SeptumAtrium);
    }//GEN-LAST:event_PulmonalKeyPressed

    private void AVVAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AVVAKeyPressed
        Valid.pindah(evt,Situs,Drainase);
    }//GEN-LAST:event_AVVAKeyPressed

    private void SeptumAtriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeptumAtriumKeyPressed
        Valid.pindah(evt,Pulmonal,SeptumVentrikal);
    }//GEN-LAST:event_SeptumAtriumKeyPressed

    private void TricuspidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TricuspidKeyPressed
        Valid.pindah(evt,Aorta,Pulmonal);
    }//GEN-LAST:event_TricuspidKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah2(evt,Dopler,Saran);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void RuangJantungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RuangJantungKeyPressed
        Valid.pindah(evt,Lainlain,IVDS);
    }//GEN-LAST:event_RuangJantungKeyPressed

    private void DrainaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DrainaseKeyPressed
        Valid.pindah(evt,AVVA,Mitral);
    }//GEN-LAST:event_DrainaseKeyPressed

    private void DoplerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DoplerKeyPressed
        Valid.pindah(evt,FractionShotening,Kesimpulan);
    }//GEN-LAST:event_DoplerKeyPressed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("hasilpemeriksaanechopediatrik/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Rawat terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Rawat terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void btnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicomActionPerformed
        if(tabModeDicom.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            if(tbListDicom.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                OrthancDICOM orthan=new OrthancDICOM(null,false);
                orthan.setJudul("::[ DICOM Orthanc Pasien "+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()+", Series "+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+" ]::",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().replaceAll("/","")+"_"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"_"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString().replaceAll(" ","_").replaceAll("/",""),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                try {
                    orthan.loadURL(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }
                orthan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                orthan.setLocationRelativeTo(internalFrame1);
                orthan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
            }
        }
    }//GEN-LAST:event_btnDicomActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        tampilOrthanc();
    }//GEN-LAST:event_TabDataMouseClicked

    private void KirimanDariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KirimanDariKeyPressed
        Valid.pindah(evt,BtnDokter,DiagnosaKlinis);
    }//GEN-LAST:event_KirimanDariKeyPressed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        Valid.pindah(evt,KirimanDari,Situs);
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void MitralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MitralKeyPressed
        Valid.pindah(evt,Drainase,Aorta);
    }//GEN-LAST:event_MitralKeyPressed

    private void AortaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AortaKeyPressed
        Valid.pindah(evt,Mitral,Tricuspid);
    }//GEN-LAST:event_AortaKeyPressed

    private void SeptumVentrikalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeptumVentrikalKeyPressed
        Valid.pindah(evt,SeptumAtrium,ArkusAorta);
    }//GEN-LAST:event_SeptumVentrikalKeyPressed

    private void ArkusAortaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ArkusAortaKeyPressed
        Valid.pindah(evt,SeptumVentrikal,Lainlain);
    }//GEN-LAST:event_ArkusAortaKeyPressed

    private void LainlainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKeyPressed
        Valid.pindah(evt,ArkusAorta,RuangJantung);
    }//GEN-LAST:event_LainlainKeyPressed

    private void IVDSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IVDSKeyPressed
        Valid.pindah(evt,RuangJantung,IVSS);
    }//GEN-LAST:event_IVDSKeyPressed

    private void IVSSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IVSSKeyPressed
        Valid.pindah(evt,IVDS,LVIDdextra);
    }//GEN-LAST:event_IVSSKeyPressed

    private void LVIDdextraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LVIDdextraKeyPressed
        Valid.pindah(evt,IVSS,LVIDsinistra);
    }//GEN-LAST:event_LVIDdextraKeyPressed

    private void LVIDsinistraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LVIDsinistraKeyPressed
        Valid.pindah(evt,LVIDdextra,LVPWdextra);
    }//GEN-LAST:event_LVIDsinistraKeyPressed

    private void LVPWdextraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LVPWdextraKeyPressed
        Valid.pindah(evt,LVIDsinistra,LVPWsinistra);
    }//GEN-LAST:event_LVPWdextraKeyPressed

    private void LVPWsinistraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LVPWsinistraKeyPressed
        Valid.pindah(evt,LVPWdextra,EjectionFraction);
    }//GEN-LAST:event_LVPWsinistraKeyPressed

    private void EjectionFractionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EjectionFractionKeyPressed
        Valid.pindah(evt,LVPWsinistra,FractionShotening);
    }//GEN-LAST:event_EjectionFractionKeyPressed

    private void FractionShoteningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FractionShoteningKeyPressed
        Valid.pindah(evt,EjectionFraction,Dopler);
    }//GEN-LAST:event_FractionShoteningKeyPressed

    private void SaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaranKeyPressed
        Valid.pindah(evt,Kesimpulan,BtnSimpan);
    }//GEN-LAST:event_SaranKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilPemeriksaanEchoPediatrik dialog = new RMHasilPemeriksaanEchoPediatrik(new javax.swing.JFrame(), true);
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
    private widget.TextBox AVVA;
    private widget.TextBox Aorta;
    private widget.TextBox ArkusAorta;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaKlinis;
    private widget.TextBox Dopler;
    private widget.TextBox Drainase;
    private widget.TextBox EjectionFraction;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormOrthan;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox FractionShotening;
    private widget.TextBox IVDS;
    private widget.TextBox IVSS;
    private widget.TextBox KdDokter;
    private widget.TextArea Kesimpulan;
    private widget.TextBox KirimanDari;
    private widget.Label LCount;
    private widget.TextBox LVIDdextra;
    private widget.TextBox LVIDsinistra;
    private widget.TextBox LVPWdextra;
    private widget.TextBox LVPWsinistra;
    private widget.TextBox Lainlain;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox Mitral;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Pulmonal;
    private widget.TextBox RuangJantung;
    private widget.TextBox Saran;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox SeptumAtrium;
    private widget.TextBox SeptumVentrikal;
    private widget.TextBox Situs;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tricuspid;
    private widget.Button btnAmbil;
    private widget.Button btnDicom;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel45;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
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
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane17;
    private widget.Table tbListDicom;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_echo_pediatrik.tanggal,"+
                            "hasil_pemeriksaan_echo_pediatrik.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_echo_pediatrik.diagnosa_klinis,hasil_pemeriksaan_echo_pediatrik.kiriman_dari,"+
                            "hasil_pemeriksaan_echo_pediatrik.situs,hasil_pemeriksaan_echo_pediatrik.av_va,hasil_pemeriksaan_echo_pediatrik.drainase_vena_pulmonalis,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_mitral,hasil_pemeriksaan_echo_pediatrik.katup_aorta,hasil_pemeriksaan_echo_pediatrik.katup_tricuspid,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_pulmonal,hasil_pemeriksaan_echo_pediatrik.katup_septum_atrium,hasil_pemeriksaan_echo_pediatrik.katup_septum_ventrikal,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_arkus_aorta,hasil_pemeriksaan_echo_pediatrik.katup_keterangan_lainnya,hasil_pemeriksaan_echo_pediatrik.ruang_jantung,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ivds,hasil_pemeriksaan_echo_pediatrik.mode_ivss,hasil_pemeriksaan_echo_pediatrik.mode_lvid_dextra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_lvid_sinistra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_dextra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_sinistra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ejection_fraction,hasil_pemeriksaan_echo_pediatrik.mode_fraction_shotening,hasil_pemeriksaan_echo_pediatrik.doppler,"+
                            "hasil_pemeriksaan_echo_pediatrik.kesimpulan,hasil_pemeriksaan_echo_pediatrik.saran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join hasil_pemeriksaan_echo_pediatrik on reg_periksa.no_rawat=hasil_pemeriksaan_echo_pediatrik.no_rawat "+
                            "inner join dokter on hasil_pemeriksaan_echo_pediatrik.kd_dokter=dokter.kd_dokter where "+
                            "hasil_pemeriksaan_echo_pediatrik.tanggal between ? and ? order by hasil_pemeriksaan_echo_pediatrik.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_echo_pediatrik.tanggal,"+
                            "hasil_pemeriksaan_echo_pediatrik.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_echo_pediatrik.diagnosa_klinis,hasil_pemeriksaan_echo_pediatrik.kiriman_dari,"+
                            "hasil_pemeriksaan_echo_pediatrik.situs,hasil_pemeriksaan_echo_pediatrik.av_va,hasil_pemeriksaan_echo_pediatrik.drainase_vena_pulmonalis,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_mitral,hasil_pemeriksaan_echo_pediatrik.katup_aorta,hasil_pemeriksaan_echo_pediatrik.katup_tricuspid,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_pulmonal,hasil_pemeriksaan_echo_pediatrik.katup_septum_atrium,hasil_pemeriksaan_echo_pediatrik.katup_septum_ventrikal,"+
                            "hasil_pemeriksaan_echo_pediatrik.katup_arkus_aorta,hasil_pemeriksaan_echo_pediatrik.katup_keterangan_lainnya,hasil_pemeriksaan_echo_pediatrik.ruang_jantung,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ivds,hasil_pemeriksaan_echo_pediatrik.mode_ivss,hasil_pemeriksaan_echo_pediatrik.mode_lvid_dextra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_lvid_sinistra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_dextra,hasil_pemeriksaan_echo_pediatrik.mode_lvpw_sinistra,"+
                            "hasil_pemeriksaan_echo_pediatrik.mode_ejection_fraction,hasil_pemeriksaan_echo_pediatrik.mode_fraction_shotening,hasil_pemeriksaan_echo_pediatrik.doppler,"+
                            "hasil_pemeriksaan_echo_pediatrik.kesimpulan,hasil_pemeriksaan_echo_pediatrik.saran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join hasil_pemeriksaan_echo_pediatrik on reg_periksa.no_rawat=hasil_pemeriksaan_echo_pediatrik.no_rawat "+
                            "inner join dokter on hasil_pemeriksaan_echo_pediatrik.kd_dokter=dokter.kd_dokter where "+
                            "hasil_pemeriksaan_echo_pediatrik.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "hasil_pemeriksaan_echo_pediatrik.kd_dokter like ? or dokter.nm_dokter like ?) order by hasil_pemeriksaan_echo_pediatrik.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("diagnosa_klinis"),rs.getString("kiriman_dari"),rs.getString("situs"),rs.getString("av_va"),rs.getString("drainase_vena_pulmonalis"),rs.getString("katup_mitral"),
                        rs.getString("katup_aorta"),rs.getString("katup_tricuspid"),rs.getString("katup_pulmonal"),rs.getString("katup_septum_atrium"),rs.getString("katup_septum_ventrikal"),
                        rs.getString("katup_arkus_aorta"),rs.getString("katup_keterangan_lainnya"),rs.getString("ruang_jantung"),rs.getString("mode_ivds"),rs.getString("mode_ivss"),rs.getString("mode_lvid_dextra"),
                        rs.getString("mode_lvid_sinistra"),rs.getString("mode_lvpw_dextra"),rs.getString("mode_lvpw_sinistra"),rs.getString("mode_ejection_fraction"),rs.getString("mode_fraction_shotening"),
                        rs.getString("doppler"),rs.getString("kesimpulan"),rs.getString("saran")
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
        KirimanDari.setText("");
        DiagnosaKlinis.setText("");
        Situs.setText("");
        AVVA.setText("");
        Drainase.setText("");
        Mitral.setText("");
        Aorta.setText("");
        Tricuspid.setText("");
        Pulmonal.setText("");
        SeptumAtrium.setText("");
        SeptumVentrikal.setText("");
        ArkusAorta.setText("");
        Lainlain.setText("");
        RuangJantung.setText("");
        IVDS.setText("");
        IVSS.setText("");
        LVPWdextra.setText("");
        LVPWsinistra.setText("");
        LVIDdextra.setText("");
        LVIDsinistra.setText("");
        Dopler.setText("");
        Kesimpulan.setText("");
        Saran.setText("");
        Tanggal.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        KirimanDari.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KirimanDari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Situs.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            AVVA.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Drainase.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Mitral.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Aorta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Tricuspid.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Pulmonal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            SeptumAtrium.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SeptumVentrikal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            ArkusAorta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Lainlain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RuangJantung.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            IVDS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            IVSS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            LVIDdextra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            LVIDsinistra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            LVPWdextra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            LVPWsinistra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            EjectionFraction.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            FractionShotening.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Dopler.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Saran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gethasil_pemeriksaan_echo_pediatrik());
        BtnHapus.setEnabled(akses.gethasil_pemeriksaan_echo_pediatrik());
        BtnEdit.setEnabled(akses.gethasil_pemeriksaan_echo_pediatrik());
        BtnEdit.setEnabled(akses.gethasil_pemeriksaan_echo_pediatrik());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter,KdDokter.getText());
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from hasil_pemeriksaan_echo_pediatrik where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("hasil_pemeriksaan_echo_pediatrik","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,sistolik=?,diastolic=?,kontraktilitas=?,dimensi_ruang=?,katup=?,"+
                "analisa_segmental=?,erap=?,lain_lain=?,kesimpulan=?",13,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                Situs.getText(),AVVA.getText(),Drainase.getText(),Tricuspid.getText(),Pulmonal.getText(),SeptumAtrium.getText(),
                RuangJantung.getText(),Dopler.getText(),Kesimpulan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(DiagnosaKlinis.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(KirimanDari.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Situs.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(AVVA.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(Drainase.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(Mitral.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Aorta.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Tricuspid.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Pulmonal.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(SeptumAtrium.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(SeptumVentrikal.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(ArkusAorta.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(Lainlain.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(RuangJantung.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(IVDS.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(IVSS.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(LVIDdextra.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(LVIDsinistra.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(LVPWdextra.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(LVPWsinistra.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(EjectionFraction.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(FractionShotening.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(Dopler.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(Saran.getText(),tbObat.getSelectedRow(),31);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(530,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            TabData.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select hasil_pemeriksaan_echo_pediatrik_gambar.photo from hasil_pemeriksaan_echo_pediatrik_gambar where hasil_pemeriksaan_echo_pediatrik_gambar.no_rawat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanechopediatrik/"+rs.getString("photo")+"'><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanechopediatrik/"+rs.getString("photo")+"' alt='photo' width='550' height='550'/></a></center></body></html>");
                        }  
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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

    private void simpan() {
        if(Sequel.menyimpantf("hasil_pemeriksaan_echo_pediatrik","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",28,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                DiagnosaKlinis.getText(),KirimanDari.getText(),Situs.getText(),AVVA.getText(),Drainase.getText(),Mitral.getText(),Aorta.getText(),Tricuspid.getText(), 
                Pulmonal.getText(),SeptumAtrium.getText(),SeptumVentrikal.getText(),ArkusAorta.getText(),Lainlain.getText(),RuangJantung.getText(), 
                IVDS.getText(),IVSS.getText(),LVIDdextra.getText(),LVIDsinistra.getText(),LVPWdextra.getText(),LVPWsinistra.getText(),EjectionFraction.getText(), 
                FractionShotening.getText(),Dopler.getText(),Kesimpulan.getText(),Saran.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    DiagnosaKlinis.getText(),KirimanDari.getText(),Situs.getText(),AVVA.getText(),Drainase.getText(),Mitral.getText(),Aorta.getText(),Tricuspid.getText(), 
                    Pulmonal.getText(),SeptumAtrium.getText(),SeptumVentrikal.getText(),ArkusAorta.getText(),Lainlain.getText(),RuangJantung.getText(), 
                    IVDS.getText(),IVSS.getText(),LVIDdextra.getText(),LVIDsinistra.getText(),LVPWdextra.getText(),LVPWsinistra.getText(),EjectionFraction.getText(), 
                    FractionShotening.getText(),Dopler.getText(),Kesimpulan.getText(),Saran.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
        }
    }
    
    private void tampilOrthanc() {
        if(TabData.isVisible()==true){
            if(tbObat.getSelectedRow()!= -1){
                 if(TabData.getSelectedIndex()==1){
                     try {
                         Valid.tabelKosong(tabModeDicom);
                         ApiOrthanc orthanc=new ApiOrthanc();
                         root=orthanc.AmbilSeries(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),Valid.SetTgl(DTPCari1.getSelectedItem()+"").replaceAll("-",""),Valid.SetTgl(DTPCari2.getSelectedItem()+"").replaceAll("-",""));
                         for(JsonNode list:root){
                             for(JsonNode sublist:list.path("Series")){
                                  tabModeDicom.addRow(new Object[]{
                                       list.path("PatientMainDicomTags").path("PatientID").asText(),list.path("ID").asText(),sublist.asText()
                                  });   
                             }        
                         }
                     } catch (Exception e) {
                         System.out.println("Notif : "+e);
                     }
                 }
            }
        }
    }
}

/*
 * By Mas Elkhanza
 */


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
public final class RMHasilEndoskopiTelinga extends javax.swing.JDialog {
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
    public RMHasilEndoskopiTelinga(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Kode Dokter","Nama Dokter","Tanggal","Kiriman Dari","Diagnosa Klinis","Telinga Kanan","Telinga Kiri",
                "Liang Telinga Kanan","Keterangan Liang Telinga Kanan","Liang Telinga Kiri","Keterangan Liang Telinga Kiri","Intak Kanan","Intak Kiri",
                "Perforasi Kanan","Keterangan Perforasi Kanan","Perforasi Kiri","Keterangan Perforasi Kiri","Mukosa Kanan","Mukosa Kiri","Osikel Kanan","Osikel Kiri",
                "Isthmus Kanan","Isthmus Kiri","Anterior Kanan","Anterior Kiri","Posterior Kanan","Posterior Kiri","Lain-lain Telinga Kanan","Lain-lain Telinga Kiri",
                "Kesimpulan","Anjuran"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
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
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(78);
            }else if(i==10){
                column.setPreferredWidth(64);
            }else if(i==11){
                column.setPreferredWidth(108);
            }else if(i==12){
                column.setPreferredWidth(167);
            }else if(i==13){
                column.setPreferredWidth(93);
            }else if(i==14){
                column.setPreferredWidth(151);
            }else if(i==15){
                column.setPreferredWidth(68);
            }else if(i==16){
                column.setPreferredWidth(55);
            }else if(i==17){
                column.setPreferredWidth(87);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(72);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(250);
            }else if(i==34){
                column.setPreferredWidth(250);
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
        KeteranganLiangKanan.setDocument(new batasInput((int)30).getKata(KeteranganLiangKanan));
        KeteranganLiangKiri.setDocument(new batasInput((int)30).getKata(KeteranganLiangKiri));
        KeteranganPerforasiKanan.setDocument(new batasInput((int)30).getKata(KeteranganPerforasiKanan));
        KeteranganPerforasiKiri.setDocument(new batasInput((int)30).getKata(KeteranganPerforasiKiri));
        MukosaKanan.setDocument(new batasInput((int)40).getKata(MukosaKanan));
        MukosaKiri.setDocument(new batasInput((int)40).getKata(MukosaKiri));
        OsikelKanan.setDocument(new batasInput((int)40).getKata(OsikelKanan));
        OsikelKiri.setDocument(new batasInput((int)40).getKata(OsikelKiri));
        IsthmusKanan.setDocument(new batasInput((int)40).getKata(IsthmusKanan));
        IsthmusKiri.setDocument(new batasInput((int)40).getKata(IsthmusKiri));
        AnteriorKanan.setDocument(new batasInput((int)40).getKata(AnteriorKanan));
        AnteriorKiri.setDocument(new batasInput((int)40).getKata(AnteriorKiri));
        PosteriorKanan.setDocument(new batasInput((int)40).getKata(PosteriorKanan));
        PosteriorKiri.setDocument(new batasInput((int)40).getKata(PosteriorKiri));
        LainlainKanan.setDocument(new batasInput((int)100).getKata(LainlainKanan));
        LainlainKiri.setDocument(new batasInput((int)100).getKata(LainlainKiri));
        Kesimpulan.setDocument(new batasInput((int)300).getKata(Kesimpulan));
        Anjuran.setDocument(new batasInput((int)300).getKata(Anjuran));
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
        jLabel30 = new widget.Label();
        KirimanDari = new widget.TextBox();
        jLabel32 = new widget.Label();
        DiagnosaKlinis = new widget.TextBox();
        jLabel44 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        jLabel11 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        LiangTelingaKanan = new widget.ComboBox();
        jLabel35 = new widget.Label();
        TelingaKanan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        IntakKanan = new widget.ComboBox();
        jLabel38 = new widget.Label();
        PerforasiKanan = new widget.ComboBox();
        jLabel39 = new widget.Label();
        KeteranganPerforasiKanan = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        MukosaKanan = new widget.TextBox();
        jLabel42 = new widget.Label();
        OsikelKanan = new widget.TextBox();
        jLabel45 = new widget.Label();
        IsthmusKanan = new widget.TextBox();
        jLabel46 = new widget.Label();
        AnteriorKanan = new widget.TextBox();
        PosteriorKanan = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        LainlainKanan = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel37 = new widget.Label();
        TelingaKiri = new widget.ComboBox();
        LiangTelingaKiri = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        PerforasiKiri = new widget.ComboBox();
        IntakKiri = new widget.ComboBox();
        MukosaKiri = new widget.TextBox();
        OsikelKiri = new widget.TextBox();
        IsthmusKiri = new widget.TextBox();
        AnteriorKiri = new widget.TextBox();
        PosteriorKiri = new widget.TextBox();
        KeteranganPerforasiKiri = new widget.TextBox();
        LainlainKiri = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        scrollPane18 = new widget.ScrollPane();
        Anjuran = new widget.TextArea();
        KeteranganLiangKanan = new widget.TextBox();
        KeteranganLiangKiri = new widget.TextBox();
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
        MnPenilaianMedis.setText("Formulir Hasil Pemeriksaan Endoskopi Telinga");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(280, 26));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pemeriksaan Tele Endoskopi Telinga ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(750, 613));
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

        jLabel10.setText(":");
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-03-2024 20:08:03" }));
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

        jLabel30.setText(":");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 80, 84, 23);

        KirimanDari.setFocusTraversalPolicyProvider(true);
        KirimanDari.setName("KirimanDari"); // NOI18N
        KirimanDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KirimanDariKeyPressed(evt);
            }
        });
        FormInput.add(KirimanDari);
        KirimanDari.setBounds(88, 80, 255, 23);

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

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Kesimpulan :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(16, 450, 95, 23);

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
        scrollPane17.setBounds(34, 470, 690, 53);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("No.Rawat");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(16, 10, 70, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Kiriman Dari");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(16, 80, 95, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Telinga Kanan");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(16, 110, 80, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("- Liang Telinga");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(30, 140, 90, 23);

        LiangTelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Serumen", "Sekret", "Jamur", "Kolesteatoma", "Massa/Jaringan", "Benda Asing", "Lainnya" }));
        LiangTelingaKanan.setName("LiangTelingaKanan"); // NOI18N
        LiangTelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LiangTelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(LiangTelingaKanan);
        LiangTelingaKanan.setBounds(113, 140, 130, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 110, 92, 23);

        TelingaKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lapang", "Sempit", "Destruksi" }));
        TelingaKanan.setName("TelingaKanan"); // NOI18N
        TelingaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKananKeyPressed(evt);
            }
        });
        FormInput.add(TelingaKanan);
        TelingaKanan.setBounds(96, 110, 100, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("- Membran Timpani :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(30, 170, 120, 23);

        IntakKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hiperemis", "Bulging", "Retraksi", "Sklerotik" }));
        IntakKanan.setName("IntakKanan"); // NOI18N
        IntakKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntakKananKeyPressed(evt);
            }
        });
        FormInput.add(IntakKanan);
        IntakKanan.setBounds(129, 220, 100, 23);

        jLabel38.setText("Intak :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(55, 220, 70, 23);

        PerforasiKanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sentral", "Atik", "Marginal", "Lainnya" }));
        PerforasiKanan.setName("PerforasiKanan"); // NOI18N
        PerforasiKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerforasiKananKeyPressed(evt);
            }
        });
        FormInput.add(PerforasiKanan);
        PerforasiKanan.setBounds(129, 190, 95, 23);

        jLabel39.setText("Perforasi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(55, 190, 70, 23);

        KeteranganPerforasiKanan.setFocusTraversalPolicyProvider(true);
        KeteranganPerforasiKanan.setName("KeteranganPerforasiKanan"); // NOI18N
        KeteranganPerforasiKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerforasiKananKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerforasiKanan);
        KeteranganPerforasiKanan.setBounds(227, 190, 127, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("- Kavum Timpani :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(30, 250, 120, 23);

        jLabel41.setText("Mukosa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(55, 270, 70, 23);

        MukosaKanan.setFocusTraversalPolicyProvider(true);
        MukosaKanan.setName("MukosaKanan"); // NOI18N
        MukosaKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukosaKananKeyPressed(evt);
            }
        });
        FormInput.add(MukosaKanan);
        MukosaKanan.setBounds(129, 270, 225, 23);

        jLabel42.setText("Osikel :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(55, 300, 70, 23);

        OsikelKanan.setFocusTraversalPolicyProvider(true);
        OsikelKanan.setName("OsikelKanan"); // NOI18N
        OsikelKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OsikelKananKeyPressed(evt);
            }
        });
        FormInput.add(OsikelKanan);
        OsikelKanan.setBounds(129, 300, 225, 23);

        jLabel45.setText("Isthmus :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(55, 330, 70, 23);

        IsthmusKanan.setFocusTraversalPolicyProvider(true);
        IsthmusKanan.setName("IsthmusKanan"); // NOI18N
        IsthmusKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IsthmusKananKeyPressed(evt);
            }
        });
        FormInput.add(IsthmusKanan);
        IsthmusKanan.setBounds(129, 330, 225, 23);

        jLabel46.setText("Anterior :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(55, 360, 70, 23);

        AnteriorKanan.setFocusTraversalPolicyProvider(true);
        AnteriorKanan.setName("AnteriorKanan"); // NOI18N
        AnteriorKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnteriorKananKeyPressed(evt);
            }
        });
        FormInput.add(AnteriorKanan);
        AnteriorKanan.setBounds(129, 360, 225, 23);

        PosteriorKanan.setFocusTraversalPolicyProvider(true);
        PosteriorKanan.setName("PosteriorKanan"); // NOI18N
        PosteriorKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosteriorKananKeyPressed(evt);
            }
        });
        FormInput.add(PosteriorKanan);
        PosteriorKanan.setBounds(129, 390, 225, 23);

        jLabel47.setText("Posterior :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(55, 390, 70, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("- Lain-lain");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(30, 420, 70, 23);

        LainlainKanan.setFocusTraversalPolicyProvider(true);
        LainlainKanan.setName("LainlainKanan"); // NOI18N
        LainlainKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKananKeyPressed(evt);
            }
        });
        FormInput.add(LainlainKanan);
        LainlainKanan.setBounds(90, 420, 264, 23);

        jLabel49.setText(":");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 140, 109, 23);

        jLabel50.setText(":");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 420, 86, 23);

        jLabel37.setText("Telinga Kiri :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(372, 110, 75, 23);

        TelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lapang", "Sempit", "Destruksi" }));
        TelingaKiri.setName("TelingaKiri"); // NOI18N
        TelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(TelingaKiri);
        TelingaKiri.setBounds(451, 110, 100, 23);

        LiangTelingaKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Serumen", "Sekret", "Jamur", "Kolesteatoma", "Massa/Jaringan", "Benda Asing", "Lainnya" }));
        LiangTelingaKiri.setName("LiangTelingaKiri"); // NOI18N
        LiangTelingaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LiangTelingaKiriKeyPressed(evt);
            }
        });
        FormInput.add(LiangTelingaKiri);
        LiangTelingaKiri.setBounds(483, 140, 130, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("- Liang Telinga");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(400, 140, 90, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(370, 140, 109, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("- Membran Timpani :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(400, 170, 120, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("- Kavum Timpani :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(400, 250, 120, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("- Lain-lain");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(400, 420, 70, 23);

        jLabel55.setText("Posterior :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(425, 390, 70, 23);

        jLabel56.setText("Anterior :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(425, 360, 70, 23);

        jLabel57.setText("Isthmus :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(425, 330, 70, 23);

        jLabel58.setText("Osikel :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(425, 300, 70, 23);

        jLabel59.setText("Mukosa :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(425, 270, 70, 23);

        jLabel60.setText("Intak :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(425, 220, 70, 23);

        jLabel61.setText("Perforasi :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(425, 190, 70, 23);

        PerforasiKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sentral", "Atik", "Marginal", "Lainnya" }));
        PerforasiKiri.setName("PerforasiKiri"); // NOI18N
        PerforasiKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerforasiKiriKeyPressed(evt);
            }
        });
        FormInput.add(PerforasiKiri);
        PerforasiKiri.setBounds(499, 190, 95, 23);

        IntakKiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Hiperemis", "Bulging", "Retraksi", "Sklerotik" }));
        IntakKiri.setName("IntakKiri"); // NOI18N
        IntakKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntakKiriKeyPressed(evt);
            }
        });
        FormInput.add(IntakKiri);
        IntakKiri.setBounds(499, 220, 100, 23);

        MukosaKiri.setFocusTraversalPolicyProvider(true);
        MukosaKiri.setName("MukosaKiri"); // NOI18N
        MukosaKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukosaKiriKeyPressed(evt);
            }
        });
        FormInput.add(MukosaKiri);
        MukosaKiri.setBounds(499, 270, 225, 23);

        OsikelKiri.setFocusTraversalPolicyProvider(true);
        OsikelKiri.setName("OsikelKiri"); // NOI18N
        OsikelKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OsikelKiriKeyPressed(evt);
            }
        });
        FormInput.add(OsikelKiri);
        OsikelKiri.setBounds(499, 300, 225, 23);

        IsthmusKiri.setFocusTraversalPolicyProvider(true);
        IsthmusKiri.setName("IsthmusKiri"); // NOI18N
        IsthmusKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IsthmusKiriKeyPressed(evt);
            }
        });
        FormInput.add(IsthmusKiri);
        IsthmusKiri.setBounds(499, 330, 225, 23);

        AnteriorKiri.setFocusTraversalPolicyProvider(true);
        AnteriorKiri.setName("AnteriorKiri"); // NOI18N
        AnteriorKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnteriorKiriKeyPressed(evt);
            }
        });
        FormInput.add(AnteriorKiri);
        AnteriorKiri.setBounds(499, 360, 225, 23);

        PosteriorKiri.setFocusTraversalPolicyProvider(true);
        PosteriorKiri.setName("PosteriorKiri"); // NOI18N
        PosteriorKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosteriorKiriKeyPressed(evt);
            }
        });
        FormInput.add(PosteriorKiri);
        PosteriorKiri.setBounds(499, 390, 225, 23);

        KeteranganPerforasiKiri.setFocusTraversalPolicyProvider(true);
        KeteranganPerforasiKiri.setName("KeteranganPerforasiKiri"); // NOI18N
        KeteranganPerforasiKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerforasiKiriKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerforasiKiri);
        KeteranganPerforasiKiri.setBounds(597, 190, 127, 23);

        LainlainKiri.setFocusTraversalPolicyProvider(true);
        LainlainKiri.setName("LainlainKiri"); // NOI18N
        LainlainKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKiriKeyPressed(evt);
            }
        });
        FormInput.add(LainlainKiri);
        LainlainKiri.setBounds(460, 420, 264, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(370, 420, 86, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Anjuran :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(16, 530, 95, 23);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        Anjuran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Anjuran.setColumns(20);
        Anjuran.setRows(5);
        Anjuran.setName("Anjuran"); // NOI18N
        Anjuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnjuranKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Anjuran);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(34, 550, 690, 53);

        KeteranganLiangKanan.setFocusTraversalPolicyProvider(true);
        KeteranganLiangKanan.setName("KeteranganLiangKanan"); // NOI18N
        KeteranganLiangKanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLiangKananKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLiangKanan);
        KeteranganLiangKanan.setBounds(246, 140, 108, 23);

        KeteranganLiangKiri.setFocusTraversalPolicyProvider(true);
        KeteranganLiangKiri.setName("KeteranganLiangKiri"); // NOI18N
        KeteranganLiangKiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLiangKiriKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLiangKiri);
        KeteranganLiangKiri.setBounds(616, 140, 108, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Hasil Pemeriksaan Endoskopi", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-03-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-03-2024" }));
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

        TabData.addTab("Gambar Pemeriksaan Endoskopi", FormPhoto);

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

        TabRawat.addTab("Data Hasil Pemeriksaan Endoskopi", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

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
        }else if(Anjuran.getText().trim().equals("")){
            Valid.textKosong(Anjuran,"Anjuran");
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
            Valid.pindah(evt,Anjuran,BtnBatal);
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
        }else if(Anjuran.getText().trim().equals("")){
            Valid.textKosong(Anjuran,"Anjuran");
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kiriman Dari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Klinis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Telinga Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Telinga Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Liang Telinga Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Liang Telinga Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Liang Telinga Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Liang Telinga Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Intak Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Intak Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perforasi Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Perforasi Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perforasi Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Perforasi Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mukosa Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mukosa Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Osikel Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Osikel Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Isthmus Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Isthmus Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anterior Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anterior Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Posterior Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Posterior Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain Telinga Kanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain Telinga Kiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anjuran</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataHasilEndoskopiTelinga.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA HASIL PEMERIKSAAN TELE ENDOSKOPI TELINGA<br><br></font>"+        
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
        Valid.pindah(evt,Kesimpulan,Tanggal);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,BtnDokter,KirimanDari);
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
            
            Valid.MyReportqry("rptCetakHasilEndoskipoTelinga.jasper","report","::[ Formulir Hasil Pemeriksaan Tele Endoskopi Telinga ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_endoskopi_telinga.tanggal,"+
                "hasil_endoskopi_telinga.kd_dokter,dokter.nm_dokter,hasil_endoskopi_telinga.diagnosa_klinis,hasil_endoskopi_telinga.kiriman_dari,"+
                "hasil_endoskopi_telinga.bentuk_liang_telinga_kanan,hasil_endoskopi_telinga.bentuk_liang_telinga_kiri,"+
                "hasil_endoskopi_telinga.kondisi_liang_telinga_kanan,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kanan,"+
                "hasil_endoskopi_telinga.kondisi_liang_telinga_kiri,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kiri,"+
                "hasil_endoskopi_telinga.membran_timpani_intak_kanan,hasil_endoskopi_telinga.membran_timpani_intak_kiri,"+
                "hasil_endoskopi_telinga.membran_timpani_perforasi_kanan,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kanan,"+
                "hasil_endoskopi_telinga.membran_timpani_perforasi_kiri,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kiri,"+
                "hasil_endoskopi_telinga.kavum_timpani_mukosa_kanan,hasil_endoskopi_telinga.kavum_timpani_mukosa_kiri,"+
                "hasil_endoskopi_telinga.kavum_timpani_osikel_kanan,hasil_endoskopi_telinga.kavum_timpani_osikel_kiri,"+
                "hasil_endoskopi_telinga.kavum_timpani_isthmus_kanan,hasil_endoskopi_telinga.kavum_timpani_isthmus_kiri,"+
                "hasil_endoskopi_telinga.kavum_timpani_anterior_kanan,hasil_endoskopi_telinga.kavum_timpani_anterior_kiri,"+
                "hasil_endoskopi_telinga.kavum_timpani_posterior_kanan,hasil_endoskopi_telinga.kavum_timpani_posterior_kiri,"+
                "hasil_endoskopi_telinga.lainlain_kanan,hasil_endoskopi_telinga.lainlain_kiri,hasil_endoskopi_telinga.kesimpulan,"+
                "hasil_endoskopi_telinga.anjuran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join hasil_endoskopi_telinga on reg_periksa.no_rawat=hasil_endoskopi_telinga.no_rawat "+
                "inner join dokter on hasil_endoskopi_telinga.kd_dokter=dokter.kd_dokter where hasil_endoskopi_telinga.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KirimanDariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KirimanDariKeyPressed
       Valid.pindah(evt,BtnDokter,DiagnosaKlinis);
    }//GEN-LAST:event_KirimanDariKeyPressed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        Valid.pindah(evt,KirimanDari,TelingaKanan);
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah2(evt,LainlainKiri,Anjuran);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Rawat..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("hasilpemeriksaanendoskopitelinga/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
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

    private void LiangTelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LiangTelingaKananKeyPressed
        Valid.pindah(evt,TelingaKiri,KeteranganLiangKanan);
    }//GEN-LAST:event_LiangTelingaKananKeyPressed

    private void TelingaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKananKeyPressed
        Valid.pindah(evt,DiagnosaKlinis,TelingaKiri);
    }//GEN-LAST:event_TelingaKananKeyPressed

    private void IntakKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntakKananKeyPressed
        Valid.pindah(evt,KeteranganPerforasiKiri,IntakKiri);
    }//GEN-LAST:event_IntakKananKeyPressed

    private void PerforasiKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerforasiKananKeyPressed
        Valid.pindah(evt,KeteranganLiangKiri,KeteranganPerforasiKanan);
    }//GEN-LAST:event_PerforasiKananKeyPressed

    private void KeteranganPerforasiKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerforasiKananKeyPressed
        Valid.pindah(evt,PerforasiKanan,PerforasiKiri);
    }//GEN-LAST:event_KeteranganPerforasiKananKeyPressed

    private void MukosaKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukosaKananKeyPressed
        Valid.pindah(evt,IntakKiri,MukosaKiri);
    }//GEN-LAST:event_MukosaKananKeyPressed

    private void OsikelKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OsikelKananKeyPressed
        Valid.pindah(evt,MukosaKiri,OsikelKiri);
    }//GEN-LAST:event_OsikelKananKeyPressed

    private void IsthmusKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsthmusKananKeyPressed
        Valid.pindah(evt,OsikelKiri,IsthmusKiri);
    }//GEN-LAST:event_IsthmusKananKeyPressed

    private void AnteriorKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnteriorKananKeyPressed
        Valid.pindah(evt,IsthmusKiri,AnteriorKiri);
    }//GEN-LAST:event_AnteriorKananKeyPressed

    private void PosteriorKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosteriorKananKeyPressed
        Valid.pindah(evt,AnteriorKiri,PosteriorKiri);
    }//GEN-LAST:event_PosteriorKananKeyPressed

    private void LainlainKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKananKeyPressed
        Valid.pindah(evt,PosteriorKiri,LainlainKiri);
    }//GEN-LAST:event_LainlainKananKeyPressed

    private void TelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKiriKeyPressed
        Valid.pindah(evt,TelingaKanan,LiangTelingaKanan);
    }//GEN-LAST:event_TelingaKiriKeyPressed

    private void LiangTelingaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LiangTelingaKiriKeyPressed
        Valid.pindah(evt,KeteranganLiangKanan,KeteranganLiangKiri);
    }//GEN-LAST:event_LiangTelingaKiriKeyPressed

    private void PerforasiKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerforasiKiriKeyPressed
        Valid.pindah(evt,KeteranganPerforasiKanan,KeteranganPerforasiKiri);
    }//GEN-LAST:event_PerforasiKiriKeyPressed

    private void IntakKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntakKiriKeyPressed
        Valid.pindah(evt,IntakKanan,MukosaKanan);
    }//GEN-LAST:event_IntakKiriKeyPressed

    private void MukosaKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukosaKiriKeyPressed
        Valid.pindah(evt,MukosaKanan,OsikelKanan);
    }//GEN-LAST:event_MukosaKiriKeyPressed

    private void OsikelKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OsikelKiriKeyPressed
        Valid.pindah(evt,OsikelKanan,IsthmusKanan);
    }//GEN-LAST:event_OsikelKiriKeyPressed

    private void IsthmusKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsthmusKiriKeyPressed
        Valid.pindah(evt,IsthmusKanan,AnteriorKanan);
    }//GEN-LAST:event_IsthmusKiriKeyPressed

    private void AnteriorKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnteriorKiriKeyPressed
        Valid.pindah(evt,AnteriorKanan,PosteriorKanan);
    }//GEN-LAST:event_AnteriorKiriKeyPressed

    private void PosteriorKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosteriorKiriKeyPressed
        Valid.pindah(evt,PosteriorKanan,LainlainKanan);
    }//GEN-LAST:event_PosteriorKiriKeyPressed

    private void KeteranganPerforasiKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerforasiKiriKeyPressed
        Valid.pindah(evt,PerforasiKiri,IntakKanan);
    }//GEN-LAST:event_KeteranganPerforasiKiriKeyPressed

    private void LainlainKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKiriKeyPressed
        Valid.pindah(evt,LainlainKanan,Kesimpulan);
    }//GEN-LAST:event_LainlainKiriKeyPressed

    private void AnjuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnjuranKeyPressed
        Valid.pindah2(evt,Kesimpulan,BtnSimpan);
    }//GEN-LAST:event_AnjuranKeyPressed

    private void KeteranganLiangKananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLiangKananKeyPressed
        Valid.pindah(evt,LiangTelingaKanan,LiangTelingaKiri);
    }//GEN-LAST:event_KeteranganLiangKananKeyPressed

    private void KeteranganLiangKiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLiangKiriKeyPressed
        Valid.pindah(evt,LiangTelingaKiri,PerforasiKanan);
    }//GEN-LAST:event_KeteranganLiangKiriKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilEndoskopiTelinga dialog = new RMHasilEndoskopiTelinga(new javax.swing.JFrame(), true);
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
    private widget.TextArea Anjuran;
    private widget.TextBox AnteriorKanan;
    private widget.TextBox AnteriorKiri;
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
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormOrthan;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox IntakKanan;
    private widget.ComboBox IntakKiri;
    private widget.TextBox IsthmusKanan;
    private widget.TextBox IsthmusKiri;
    private widget.TextBox KdDokter;
    private widget.TextArea Kesimpulan;
    private widget.TextBox KeteranganLiangKanan;
    private widget.TextBox KeteranganLiangKiri;
    private widget.TextBox KeteranganPerforasiKanan;
    private widget.TextBox KeteranganPerforasiKiri;
    private widget.TextBox KirimanDari;
    private widget.Label LCount;
    private widget.TextBox LainlainKanan;
    private widget.TextBox LainlainKiri;
    private widget.ComboBox LiangTelingaKanan;
    private widget.ComboBox LiangTelingaKiri;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox MukosaKanan;
    private widget.TextBox MukosaKiri;
    private widget.TextBox NmDokter;
    private widget.TextBox OsikelKanan;
    private widget.TextBox OsikelKiri;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox PerforasiKanan;
    private widget.ComboBox PerforasiKiri;
    private widget.TextBox PosteriorKanan;
    private widget.TextBox PosteriorKiri;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.ComboBox TelingaKanan;
    private widget.ComboBox TelingaKiri;
    private widget.TextBox TglLahir;
    private widget.Button btnAmbil;
    private widget.Button btnDicom;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
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
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.Table tbListDicom;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_endoskopi_telinga.tanggal,"+
                        "hasil_endoskopi_telinga.kd_dokter,dokter.nm_dokter,hasil_endoskopi_telinga.diagnosa_klinis,hasil_endoskopi_telinga.kiriman_dari,"+
                        "hasil_endoskopi_telinga.bentuk_liang_telinga_kanan,hasil_endoskopi_telinga.bentuk_liang_telinga_kiri,"+
                        "hasil_endoskopi_telinga.kondisi_liang_telinga_kanan,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kanan,"+
                        "hasil_endoskopi_telinga.kondisi_liang_telinga_kiri,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kiri,"+
                        "hasil_endoskopi_telinga.membran_timpani_intak_kanan,hasil_endoskopi_telinga.membran_timpani_intak_kiri,"+
                        "hasil_endoskopi_telinga.membran_timpani_perforasi_kanan,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kanan,"+
                        "hasil_endoskopi_telinga.membran_timpani_perforasi_kiri,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_mukosa_kanan,hasil_endoskopi_telinga.kavum_timpani_mukosa_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_osikel_kanan,hasil_endoskopi_telinga.kavum_timpani_osikel_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_isthmus_kanan,hasil_endoskopi_telinga.kavum_timpani_isthmus_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_anterior_kanan,hasil_endoskopi_telinga.kavum_timpani_anterior_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_posterior_kanan,hasil_endoskopi_telinga.kavum_timpani_posterior_kiri,"+
                        "hasil_endoskopi_telinga.lainlain_kanan,hasil_endoskopi_telinga.lainlain_kiri,hasil_endoskopi_telinga.kesimpulan,"+
                        "hasil_endoskopi_telinga.anjuran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_endoskopi_telinga on reg_periksa.no_rawat=hasil_endoskopi_telinga.no_rawat "+
                        "inner join dokter on hasil_endoskopi_telinga.kd_dokter=dokter.kd_dokter where "+
                        "hasil_endoskopi_telinga.tanggal between ? and ? order by hasil_endoskopi_telinga.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_endoskopi_telinga.tanggal,"+
                        "hasil_endoskopi_telinga.kd_dokter,dokter.nm_dokter,hasil_endoskopi_telinga.diagnosa_klinis,hasil_endoskopi_telinga.kiriman_dari,"+
                        "hasil_endoskopi_telinga.bentuk_liang_telinga_kanan,hasil_endoskopi_telinga.bentuk_liang_telinga_kiri,"+
                        "hasil_endoskopi_telinga.kondisi_liang_telinga_kanan,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kanan,"+
                        "hasil_endoskopi_telinga.kondisi_liang_telinga_kiri,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kiri,"+
                        "hasil_endoskopi_telinga.membran_timpani_intak_kanan,hasil_endoskopi_telinga.membran_timpani_intak_kiri,"+
                        "hasil_endoskopi_telinga.membran_timpani_perforasi_kanan,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kanan,"+
                        "hasil_endoskopi_telinga.membran_timpani_perforasi_kiri,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_mukosa_kanan,hasil_endoskopi_telinga.kavum_timpani_mukosa_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_osikel_kanan,hasil_endoskopi_telinga.kavum_timpani_osikel_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_isthmus_kanan,hasil_endoskopi_telinga.kavum_timpani_isthmus_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_anterior_kanan,hasil_endoskopi_telinga.kavum_timpani_anterior_kiri,"+
                        "hasil_endoskopi_telinga.kavum_timpani_posterior_kanan,hasil_endoskopi_telinga.kavum_timpani_posterior_kiri,"+
                        "hasil_endoskopi_telinga.lainlain_kanan,hasil_endoskopi_telinga.lainlain_kiri,hasil_endoskopi_telinga.kesimpulan,"+
                        "hasil_endoskopi_telinga.anjuran from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_endoskopi_telinga on reg_periksa.no_rawat=hasil_endoskopi_telinga.no_rawat "+
                        "inner join dokter on hasil_endoskopi_telinga.kd_dokter=dokter.kd_dokter where "+
                        "hasil_endoskopi_telinga.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "hasil_endoskopi_telinga.kd_dokter like ? or dokter.nm_dokter like ?) order by hasil_endoskopi_telinga.tanggal");
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
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("kiriman_dari"),rs.getString("diagnosa_klinis"),rs.getString("bentuk_liang_telinga_kanan"),rs.getString("bentuk_liang_telinga_kiri"),rs.getString("kondisi_liang_telinga_kanan"),
                        rs.getString("keterangan_kondisi_liang_telinga_kanan"),rs.getString("kondisi_liang_telinga_kiri"),rs.getString("keterangan_kondisi_liang_telinga_kiri"),rs.getString("membran_timpani_intak_kanan"),
                        rs.getString("membran_timpani_intak_kiri"),rs.getString("membran_timpani_perforasi_kanan"),rs.getString("keterangan_membran_timpani_perforasi_kanan"),rs.getString("membran_timpani_perforasi_kiri"),
                        rs.getString("keterangan_membran_timpani_perforasi_kiri"),rs.getString("kavum_timpani_mukosa_kanan"),rs.getString("kavum_timpani_mukosa_kiri"),rs.getString("kavum_timpani_osikel_kanan"),
                        rs.getString("kavum_timpani_osikel_kiri"),rs.getString("kavum_timpani_isthmus_kanan"),rs.getString("kavum_timpani_isthmus_kiri"),rs.getString("kavum_timpani_anterior_kanan"),
                        rs.getString("kavum_timpani_anterior_kiri"),rs.getString("kavum_timpani_posterior_kanan"),rs.getString("kavum_timpani_posterior_kiri"),rs.getString("lainlain_kanan"),rs.getString("lainlain_kiri"),
                        rs.getString("kesimpulan"),rs.getString("anjuran")
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
        TelingaKanan.setSelectedIndex(0);
        TelingaKiri.setSelectedIndex(0);
        LiangTelingaKanan.setSelectedIndex(0);
        LiangTelingaKiri.setSelectedIndex(0);
        KeteranganLiangKanan.setText("");
        KeteranganLiangKiri.setText("");
        PerforasiKanan.setSelectedIndex(0);
        PerforasiKiri.setSelectedIndex(0);
        KeteranganPerforasiKanan.setText("");
        KeteranganPerforasiKiri.setText("");
        IntakKanan.setSelectedIndex(0);
        IntakKiri.setSelectedIndex(0);
        MukosaKanan.setText("");
        MukosaKiri.setText("");
        OsikelKanan.setText("");
        OsikelKiri.setText("");
        IsthmusKanan.setText("");
        IsthmusKiri.setText("");
        AnteriorKanan.setText("");
        AnteriorKiri.setText("");
        PosteriorKanan.setText("");
        PosteriorKiri.setText("");
        LainlainKanan.setText("");
        LainlainKiri.setText("");
        Kesimpulan.setText("");
        Anjuran.setText("");
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
            KirimanDari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            LiangTelingaKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KeteranganLiangKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            LiangTelingaKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KeteranganLiangKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            IntakKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            IntakKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            PerforasiKanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeteranganPerforasiKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            PerforasiKiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KeteranganPerforasiKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            MukosaKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            MukosaKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            OsikelKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            OsikelKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            IsthmusKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            IsthmusKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            AnteriorKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            AnteriorKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            PosteriorKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            PosteriorKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            LainlainKanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            LainlainKiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Anjuran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
        BtnSimpan.setEnabled(akses.gethasil_endoskopi_telinga());
        BtnHapus.setEnabled(akses.gethasil_endoskopi_telinga());
        BtnEdit.setEnabled(akses.gethasil_endoskopi_telinga());
        BtnEdit.setEnabled(akses.gethasil_endoskopi_telinga());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
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
        if(Sequel.queryu2tf("delete from hasil_endoskopi_telinga where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("hasil_endoskopi_telinga","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,diagnosa_klinis=?,kiriman_dari=?,bentuk_liang_telinga_kanan=?,bentuk_liang_telinga_kiri=?,"+
                "kondisi_liang_telinga_kanan=?,keterangan_kondisi_liang_telinga_kanan=?,kondisi_liang_telinga_kiri=?,keterangan_kondisi_liang_telinga_kiri=?,membran_timpani_intak_kanan=?,"+
                "membran_timpani_intak_kiri=?,membran_timpani_perforasi_kanan=?,keterangan_membran_timpani_perforasi_kanan=?,membran_timpani_perforasi_kiri=?,keterangan_membran_timpani_perforasi_kiri=?,"+
                "kavum_timpani_mukosa_kanan=?,kavum_timpani_mukosa_kiri=?,kavum_timpani_osikel_kanan=?,kavum_timpani_osikel_kiri=?,kavum_timpani_isthmus_kanan=?,kavum_timpani_isthmus_kiri=?,"+
                "kavum_timpani_anterior_kanan=?,kavum_timpani_anterior_kiri=?,kavum_timpani_posterior_kanan=?,kavum_timpani_posterior_kiri=?,lainlain_kanan=?,lainlain_kiri=?,kesimpulan=?,anjuran=?",32,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                DiagnosaKlinis.getText(),KirimanDari.getText(),TelingaKanan.getSelectedItem().toString(),TelingaKiri.getSelectedItem().toString(),
                LiangTelingaKanan.getSelectedItem().toString(),KeteranganLiangKanan.getText(),LiangTelingaKiri.getSelectedItem().toString(),KeteranganLiangKiri.getText(),
                IntakKanan.getSelectedItem().toString(),IntakKiri.getSelectedItem().toString(),PerforasiKanan.getSelectedItem().toString(),KeteranganPerforasiKanan.getText(),
                PerforasiKiri.getSelectedItem().toString(),KeteranganPerforasiKiri.getText(),MukosaKanan.getText(),MukosaKiri.getText(),OsikelKanan.getText(),
                OsikelKiri.getText(),IsthmusKanan.getText(),IsthmusKiri.getText(),AnteriorKanan.getText(),AnteriorKiri.getText(),PosteriorKanan.getText(),
                PosteriorKiri.getText(),LainlainKanan.getText(),LainlainKiri.getText(),Kesimpulan.getText(),Anjuran.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(KirimanDari.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(DiagnosaKlinis.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(TelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(TelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(LiangTelingaKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(KeteranganLiangKanan.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(LiangTelingaKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(KeteranganLiangKiri.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(IntakKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(IntakKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(PerforasiKanan.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(KeteranganPerforasiKanan.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(PerforasiKiri.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(KeteranganPerforasiKiri.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(MukosaKanan.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(MukosaKiri.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(OsikelKanan.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(OsikelKiri.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(IsthmusKanan.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(IsthmusKiri.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(AnteriorKanan.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(AnteriorKiri.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(PosteriorKanan.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(PosteriorKiri.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(LainlainKanan.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(LainlainKiri.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(Anjuran.getText(),tbObat.getSelectedRow(),34);
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
                ps=koneksi.prepareStatement("select hasil_endoskopi_telinga_gambar.photo from hasil_endoskopi_telinga_gambar where hasil_endoskopi_telinga_gambar.no_rawat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanendoskopitelinga/"+rs.getString("photo")+"'><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanendoskopitelinga/"+rs.getString("photo")+"' alt='photo' width='550' height='550'/></a></center></body></html>");
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

    private void simpan() {
        if(Sequel.menyimpantf("hasil_endoskopi_telinga","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",31,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                DiagnosaKlinis.getText(),KirimanDari.getText(),TelingaKanan.getSelectedItem().toString(),TelingaKiri.getSelectedItem().toString(),
                LiangTelingaKanan.getSelectedItem().toString(),KeteranganLiangKanan.getText(),LiangTelingaKiri.getSelectedItem().toString(),KeteranganLiangKiri.getText(),
                IntakKanan.getSelectedItem().toString(),IntakKiri.getSelectedItem().toString(),PerforasiKanan.getSelectedItem().toString(),KeteranganPerforasiKanan.getText(),
                PerforasiKiri.getSelectedItem().toString(),KeteranganPerforasiKiri.getText(),MukosaKanan.getText(),MukosaKiri.getText(),OsikelKanan.getText(),
                OsikelKiri.getText(),IsthmusKanan.getText(),IsthmusKiri.getText(),AnteriorKanan.getText(),AnteriorKiri.getText(),PosteriorKanan.getText(),
                PosteriorKiri.getText(),LainlainKanan.getText(),LainlainKiri.getText(),Kesimpulan.getText(),Anjuran.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    KirimanDari.getText(),DiagnosaKlinis.getText(),TelingaKanan.getSelectedItem().toString(),TelingaKiri.getSelectedItem().toString(),
                    LiangTelingaKanan.getSelectedItem().toString(),KeteranganLiangKanan.getText(),LiangTelingaKiri.getSelectedItem().toString(),KeteranganLiangKiri.getText(),
                    IntakKanan.getSelectedItem().toString(),IntakKiri.getSelectedItem().toString(),PerforasiKanan.getSelectedItem().toString(),KeteranganPerforasiKanan.getText(),
                    PerforasiKiri.getSelectedItem().toString(),KeteranganPerforasiKiri.getText(),MukosaKanan.getText(),MukosaKiri.getText(),OsikelKanan.getText(),
                    OsikelKiri.getText(),IsthmusKanan.getText(),IsthmusKiri.getText(),AnteriorKanan.getText(),AnteriorKiri.getText(),PosteriorKanan.getText(),
                    PosteriorKiri.getText(),LainlainKanan.getText(),LainlainKiri.getText(),Kesimpulan.getText(),Anjuran.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
        }
    }
}

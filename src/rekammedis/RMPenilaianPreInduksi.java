/*
 * By Mas Elkhanza
 */


package rekammedis;

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
public final class RMPenilaianPreInduksi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianPreInduksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","TD(mmHg)","Nadi(x/menit)",
            "RR(x/menit)","Suhu(°C)","EKG","Lain-lain","Asesmen","Perencanaan","Infus Perifier, Tempat & Ukuran","CVC","Posisi",
            "Premedikasi","Keterangan Premedikasi","Induksi","Keterangan Induksi","Face Mask No","Oro/Nasopharing No","ETT No",
            "ETT Jenis","ETT Fiksasi","LMA No","LMA Jenis","Tracheostomi","Bronchoscopi Fiberoptik","Glidescopi","Tatalaksana Lain-lain",
            "Intubasi Sesudah Tidur","Intubasi Oral","Intubasi Tracheostomi","Keterangan Intubasi","Sulit Ventilasi","Sulit Intubasi",
            "Ventilasi","T.R.Jenis","T.R.Lokasi","T.R.Jenis Jarum","T.R.Kateter","T.R.Kateter Fiksasi","T.R.Obat Obatan","T.R.Komplikasi",
            "T.R.Hasil"
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
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        RR.setDocument(new batasInput((int)5).getKata(EKG));
        Suhu.setDocument(new batasInput((int)5).getKata(EKG));
        RR.setDocument(new batasInput((int)5).getKata(EKG));
        EKG.setDocument(new batasInput((int)5).getKata(EKG));
        Lainlain.setDocument(new batasInput((int)5).getKata(EKG));
        Perencanaan.setDocument(new batasInput((int)5).getKata(EKG));
        InfusPerifier.setDocument(new batasInput((int)5).getKata(EKG));
        CVC.setDocument(new batasInput((int)5).getKata(EKG));
        KeteranganPremedikasi.setDocument(new batasInput((int)5).getKata(EKG));
        KeteranganInfusInduksi.setDocument(new batasInput((int)5).getKata(EKG));
        FaceMaskNo.setDocument(new batasInput((int)5).getKata(EKG));
        NasopharingNo.setDocument(new batasInput((int)5).getKata(EKG));
        ETTNo.setDocument(new batasInput((int)5).getKata(EKG));
        ETTJenis.setDocument(new batasInput((int)5).getKata(EKG));
        ETTFiksasi.setDocument(new batasInput((int)5).getKata(EKG));
        LMANo.setDocument(new batasInput((int)5).getKata(EKG));
        LMAJenis.setDocument(new batasInput((int)5).getKata(EKG));
        Tracheostomi.setDocument(new batasInput((int)5).getKata(EKG));
        BronchoscopiFiberoptik.setDocument(new batasInput((int)5).getKata(EKG));
        Glidescopi.setDocument(new batasInput((int)5).getKata(EKG));
        TatalaksanaLainlain.setDocument(new batasInput((int)5).getKata(EKG));
        KeteranganIntubasi.setDocument(new batasInput((int)5).getKata(EKG));
        IntubasiSulitVentilasi.setDocument(new batasInput((int)5).getKata(EKG));
        IntubasiSulitIntubasi.setDocument(new batasInput((int)5).getKata(EKG));
        Ventilasi.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalJenis.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalLokasi.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikReginalJenisJarum.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalKateterFiksasi.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalObatobatan.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalKomplikasi.setDocument(new batasInput((int)5).getKata(EKG));
        TeknikRegionalHasil.setDocument(new batasInput((int)5).getKata(EKG));
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator2 = new javax.swing.JSeparator();
        EKG = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        Lainlain = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        IntubasiSulitIntubasi = new widget.TextBox();
        jLabel125 = new widget.Label();
        Asesmen = new widget.ComboBox();
        Ventilasi = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel18 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel31 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        Perencanaan = new widget.TextArea();
        jLabel127 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel128 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        InfusPerifier = new widget.TextArea();
        CVC = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel129 = new widget.Label();
        jLabel131 = new widget.Label();
        Premedikasi = new widget.ComboBox();
        PosisiInfus = new widget.ComboBox();
        jLabel130 = new widget.Label();
        jLabel32 = new widget.Label();
        KeteranganPremedikasi = new widget.TextBox();
        InfusInduksi = new widget.ComboBox();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        KeteranganInfusInduksi = new widget.TextBox();
        jLabel134 = new widget.Label();
        FaceMaskNo = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        NasopharingNo = new widget.TextBox();
        jLabel137 = new widget.Label();
        ETTNo = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel138 = new widget.Label();
        ETTFiksasi = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel139 = new widget.Label();
        ETTJenis = new widget.TextBox();
        jLabel140 = new widget.Label();
        LMANo = new widget.TextBox();
        jLabel141 = new widget.Label();
        LMAJenis = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel142 = new widget.Label();
        Tracheostomi = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel143 = new widget.Label();
        BronchoscopiFiberoptik = new widget.TextBox();
        jLabel144 = new widget.Label();
        Glidescopi = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel145 = new widget.Label();
        TatalaksanaLainlain = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel146 = new widget.Label();
        jLabel148 = new widget.Label();
        IntubasiSesudahTidur = new widget.ComboBox();
        jLabel147 = new widget.Label();
        jLabel149 = new widget.Label();
        IntubasiOral = new widget.ComboBox();
        jLabel150 = new widget.Label();
        IntubasiTracheostomi = new widget.ComboBox();
        scrollPane3 = new widget.ScrollPane();
        KeteranganIntubasi = new widget.TextArea();
        jLabel152 = new widget.Label();
        jLabel151 = new widget.Label();
        jLabel153 = new widget.Label();
        IntubasiSulitVentilasi = new widget.TextBox();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel154 = new widget.Label();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        jLabel157 = new widget.Label();
        jLabel158 = new widget.Label();
        TeknikRegionalJenis = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel159 = new widget.Label();
        TeknikRegionalLokasi = new widget.TextBox();
        jLabel45 = new widget.Label();
        TeknikReginalJenisJarum = new widget.TextBox();
        jLabel46 = new widget.Label();
        jLabel162 = new widget.Label();
        TeknikRegionalKateter = new widget.ComboBox();
        jLabel161 = new widget.Label();
        jLabel160 = new widget.Label();
        TeknikRegionalKateterFiksasi = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel164 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        TeknikRegionalObatobatan = new widget.TextArea();
        jLabel163 = new widget.Label();
        jLabel166 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        TeknikRegionalKomplikasi = new widget.TextArea();
        jLabel165 = new widget.Label();
        jLabel167 = new widget.Label();
        TeknikRegionalHasil = new widget.TextBox();
        jLabel48 = new widget.Label();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Pre Anestesi");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Pre Induksi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 1023));
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
        label14.setBounds(166, 40, 50, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(220, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(312, 40, 180, 23);

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
        BtnDokter.setBounds(494, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(74, 40, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

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

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2023 08:13:36" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(594, 40, 130, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 750, 1);

        EKG.setFocusTraversalPolicyProvider(true);
        EKG.setName("EKG"); // NOI18N
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        FormInput.add(EKG);
        EKG.setBounds(43, 110, 293, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("EKG");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(12, 110, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Lain-lain");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(12, 140, 70, 23);

        Lainlain.setFocusTraversalPolicyProvider(true);
        Lainlain.setName("Lainlain"); // NOI18N
        Lainlain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainlainKeyPressed(evt);
            }
        });
        FormInput.add(Lainlain);
        Lainlain.setBounds(66, 140, 270, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Tensi Darah");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(12, 80, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(82, 80, 76, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(161, 80, 50, 23);

        IntubasiSulitIntubasi.setFocusTraversalPolicyProvider(true);
        IntubasiSulitIntubasi.setName("IntubasiSulitIntubasi"); // NOI18N
        IntubasiSulitIntubasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntubasiSulitIntubasiKeyPressed(evt);
            }
        });
        FormInput.add(IntubasiSulitIntubasi);
        IntubasiSulitIntubasi.setBounds(150, 720, 574, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("Asesmen");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(12, 170, 60, 23);

        Asesmen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sesuai Asesmen Pre Sedasi/Anestesi", "Tidak Sesuai Asesmen Pre Sedasi/Anestesi" }));
        Asesmen.setSelectedIndex(1);
        Asesmen.setName("Asesmen"); // NOI18N
        Asesmen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenKeyPressed(evt);
            }
        });
        FormInput.add(Asesmen);
        Asesmen.setBounds(68, 170, 268, 23);

        Ventilasi.setFocusTraversalPolicyProvider(true);
        Ventilasi.setName("Ventilasi"); // NOI18N
        Ventilasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VentilasiKeyPressed(evt);
            }
        });
        FormInput.add(Ventilasi);
        Ventilasi.setBounds(98, 750, 626, 23);

        jLabel25.setText(":");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 80, 78, 23);

        jLabel18.setText("Nadi :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(236, 80, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(280, 80, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("x/menit");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(343, 80, 50, 23);

        jLabel26.setText("Premedikasi :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(230, 300, 80, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(457, 80, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("x/menit");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(520, 80, 50, 23);

        jLabel28.setText("Suhu :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(601, 80, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(645, 80, 60, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("°C");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(708, 80, 30, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 110, 39, 23);

        jLabel126.setText(":");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(0, 170, 64, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Perencanaan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(370, 110, 210, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        Perencanaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Perencanaan.setColumns(20);
        Perencanaan.setRows(5);
        Perencanaan.setName("Perencanaan"); // NOI18N
        Perencanaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerencanaanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(Perencanaan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(370, 130, 354, 63);

        jLabel127.setText(":");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(0, 140, 62, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 200, 750, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 200, 750, 1);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("Infus Perifier : Tempat & Ukuran");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(12, 200, 220, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        InfusPerifier.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        InfusPerifier.setColumns(20);
        InfusPerifier.setRows(5);
        InfusPerifier.setName("InfusPerifier"); // NOI18N
        InfusPerifier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfusPerifierKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(InfusPerifier);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(44, 220, 680, 43);

        CVC.setFocusTraversalPolicyProvider(true);
        CVC.setName("CVC"); // NOI18N
        CVC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CVCKeyPressed(evt);
            }
        });
        FormInput.add(CVC);
        CVC.setBounds(77, 270, 647, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("CVC");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(44, 270, 70, 23);

        jLabel129.setText(":");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(0, 270, 73, 23);

        jLabel131.setText(":");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(0, 300, 79, 23);

        Premedikasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oral", "IM", "IV" }));
        Premedikasi.setName("Premedikasi"); // NOI18N
        Premedikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PremedikasiKeyPressed(evt);
            }
        });
        FormInput.add(Premedikasi);
        Premedikasi.setBounds(314, 300, 75, 23);

        PosisiInfus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Supine", "Lithotomi", "Lateral", "Prone", "Perlindungan Mata", "Kanan", "Kiri", "Lain-lain" }));
        PosisiInfus.setName("PosisiInfus"); // NOI18N
        PosisiInfus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiInfusKeyPressed(evt);
            }
        });
        FormInput.add(PosisiInfus);
        PosisiInfus.setBounds(83, 300, 145, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("Posisi");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(44, 300, 60, 23);

        jLabel32.setText("RR :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(413, 80, 40, 23);

        KeteranganPremedikasi.setFocusTraversalPolicyProvider(true);
        KeteranganPremedikasi.setName("KeteranganPremedikasi"); // NOI18N
        KeteranganPremedikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPremedikasiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPremedikasi);
        KeteranganPremedikasi.setBounds(392, 300, 332, 23);

        InfusInduksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Intravena", "Inhalasi" }));
        InfusInduksi.setName("InfusInduksi"); // NOI18N
        InfusInduksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InfusInduksiKeyPressed(evt);
            }
        });
        FormInput.add(InfusInduksi);
        InfusInduksi.setBounds(90, 330, 100, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("Induksi");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(44, 330, 60, 23);

        jLabel133.setText(":");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(0, 330, 86, 23);

        KeteranganInfusInduksi.setFocusTraversalPolicyProvider(true);
        KeteranganInfusInduksi.setName("KeteranganInfusInduksi"); // NOI18N
        KeteranganInfusInduksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganInfusInduksiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganInfusInduksi);
        KeteranganInfusInduksi.setBounds(193, 330, 531, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("Tata Laksana Jalan Nafas :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(44, 360, 170, 23);

        FaceMaskNo.setFocusTraversalPolicyProvider(true);
        FaceMaskNo.setName("FaceMaskNo"); // NOI18N
        FaceMaskNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaceMaskNoKeyPressed(evt);
            }
        });
        FormInput.add(FaceMaskNo);
        FaceMaskNo.setBounds(152, 380, 200, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Face Mask No");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(74, 380, 110, 23);

        jLabel135.setText(":");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(0, 380, 148, 23);

        jLabel136.setText("Oro/Nasopharing No :");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(390, 380, 130, 23);

        NasopharingNo.setFocusTraversalPolicyProvider(true);
        NasopharingNo.setName("NasopharingNo"); // NOI18N
        NasopharingNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NasopharingNoKeyPressed(evt);
            }
        });
        FormInput.add(NasopharingNo);
        NasopharingNo.setBounds(524, 380, 200, 23);

        jLabel137.setText(":");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(0, 410, 116, 23);

        ETTNo.setFocusTraversalPolicyProvider(true);
        ETTNo.setName("ETTNo"); // NOI18N
        ETTNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ETTNoKeyPressed(evt);
            }
        });
        FormInput.add(ETTNo);
        ETTNo.setBounds(120, 410, 140, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("ETT No");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(74, 410, 80, 23);

        jLabel138.setText("ETT Fiksasi :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(481, 410, 80, 23);

        ETTFiksasi.setFocusTraversalPolicyProvider(true);
        ETTFiksasi.setName("ETTFiksasi"); // NOI18N
        ETTFiksasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ETTFiksasiKeyPressed(evt);
            }
        });
        FormInput.add(ETTFiksasi);
        ETTFiksasi.setBounds(565, 410, 140, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("cm");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(708, 410, 30, 23);

        jLabel139.setText("ETT Jenis :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(265, 410, 70, 23);

        ETTJenis.setFocusTraversalPolicyProvider(true);
        ETTJenis.setName("ETTJenis"); // NOI18N
        ETTJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ETTJenisKeyPressed(evt);
            }
        });
        FormInput.add(ETTJenis);
        ETTJenis.setBounds(339, 410, 140, 23);

        jLabel140.setText(":");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(0, 440, 118, 23);

        LMANo.setFocusTraversalPolicyProvider(true);
        LMANo.setName("LMANo"); // NOI18N
        LMANo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LMANoKeyPressed(evt);
            }
        });
        FormInput.add(LMANo);
        LMANo.setBounds(122, 440, 250, 23);

        jLabel141.setText("LMA Jenis :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(370, 440, 100, 23);

        LMAJenis.setFocusTraversalPolicyProvider(true);
        LMAJenis.setName("LMAJenis"); // NOI18N
        LMAJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LMAJenisKeyPressed(evt);
            }
        });
        FormInput.add(LMAJenis);
        LMAJenis.setBounds(474, 440, 250, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("LMA No");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(74, 440, 110, 23);

        jLabel142.setText(":");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(0, 470, 147, 23);

        Tracheostomi.setFocusTraversalPolicyProvider(true);
        Tracheostomi.setName("Tracheostomi"); // NOI18N
        Tracheostomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TracheostomiKeyPressed(evt);
            }
        });
        FormInput.add(Tracheostomi);
        Tracheostomi.setBounds(151, 470, 573, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Tracheostomi");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(74, 470, 110, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Bronchoscopi Fiberoptik");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(74, 500, 130, 23);

        jLabel143.setText(":");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(0, 500, 198, 23);

        BronchoscopiFiberoptik.setFocusTraversalPolicyProvider(true);
        BronchoscopiFiberoptik.setName("BronchoscopiFiberoptik"); // NOI18N
        BronchoscopiFiberoptik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BronchoscopiFiberoptikKeyPressed(evt);
            }
        });
        FormInput.add(BronchoscopiFiberoptik);
        BronchoscopiFiberoptik.setBounds(202, 500, 522, 23);

        jLabel144.setText(":");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(0, 530, 131, 23);

        Glidescopi.setFocusTraversalPolicyProvider(true);
        Glidescopi.setName("Glidescopi"); // NOI18N
        Glidescopi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GlidescopiKeyPressed(evt);
            }
        });
        FormInput.add(Glidescopi);
        Glidescopi.setBounds(135, 530, 589, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Glidescopi");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(74, 530, 80, 23);

        jLabel145.setText(":");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(0, 560, 124, 23);

        TatalaksanaLainlain.setFocusTraversalPolicyProvider(true);
        TatalaksanaLainlain.setName("TatalaksanaLainlain"); // NOI18N
        TatalaksanaLainlain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaLainlainKeyPressed(evt);
            }
        });
        FormInput.add(TatalaksanaLainlain);
        TatalaksanaLainlain.setBounds(128, 560, 596, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Lain-lain");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(74, 560, 80, 23);

        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setText("Intubasi :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(44, 590, 170, 23);

        jLabel148.setText(":");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(0, 610, 151, 23);

        IntubasiSesudahTidur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        IntubasiSesudahTidur.setName("IntubasiSesudahTidur"); // NOI18N
        IntubasiSesudahTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntubasiSesudahTidurKeyPressed(evt);
            }
        });
        FormInput.add(IntubasiSesudahTidur);
        IntubasiSesudahTidur.setBounds(155, 610, 85, 23);

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("Sesudah Tidur");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(74, 610, 90, 23);

        jLabel149.setText("Oral :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(313, 610, 60, 23);

        IntubasiOral.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        IntubasiOral.setName("IntubasiOral"); // NOI18N
        IntubasiOral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntubasiOralKeyPressed(evt);
            }
        });
        FormInput.add(IntubasiOral);
        IntubasiOral.setBounds(377, 610, 85, 23);

        jLabel150.setText("Tracheostomi :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(545, 610, 90, 23);

        IntubasiTracheostomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        IntubasiTracheostomi.setName("IntubasiTracheostomi"); // NOI18N
        IntubasiTracheostomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntubasiTracheostomiKeyPressed(evt);
            }
        });
        FormInput.add(IntubasiTracheostomi);
        IntubasiTracheostomi.setBounds(639, 610, 85, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        KeteranganIntubasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeteranganIntubasi.setColumns(20);
        KeteranganIntubasi.setRows(5);
        KeteranganIntubasi.setName("KeteranganIntubasi"); // NOI18N
        KeteranganIntubasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganIntubasiKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(KeteranganIntubasi);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(142, 640, 582, 43);

        jLabel152.setText(":");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(0, 640, 138, 23);

        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel151.setText("Keterangan");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(74, 640, 90, 23);

        jLabel153.setText(":");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(0, 690, 149, 23);

        IntubasiSulitVentilasi.setFocusTraversalPolicyProvider(true);
        IntubasiSulitVentilasi.setName("IntubasiSulitVentilasi"); // NOI18N
        IntubasiSulitVentilasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntubasiSulitVentilasiKeyPressed(evt);
            }
        });
        FormInput.add(IntubasiSulitVentilasi);
        IntubasiSulitVentilasi.setBounds(153, 690, 571, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Sulit Ventilasi");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(74, 690, 90, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Sulit Intubasi");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(74, 720, 90, 23);

        jLabel154.setText(":");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(0, 720, 146, 23);

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText("Ventilasi");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(44, 750, 170, 23);

        jLabel156.setText(":");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(0, 750, 94, 23);

        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setText("Teknik Regional/Block Perifier :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(44, 780, 190, 23);

        jLabel158.setText(":");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(0, 800, 107, 23);

        TeknikRegionalJenis.setFocusTraversalPolicyProvider(true);
        TeknikRegionalJenis.setName("TeknikRegionalJenis"); // NOI18N
        TeknikRegionalJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalJenisKeyPressed(evt);
            }
        });
        FormInput.add(TeknikRegionalJenis);
        TeknikRegionalJenis.setBounds(111, 800, 613, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Jenis");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(74, 800, 90, 23);

        jLabel159.setText(":");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(0, 830, 111, 23);

        TeknikRegionalLokasi.setFocusTraversalPolicyProvider(true);
        TeknikRegionalLokasi.setName("TeknikRegionalLokasi"); // NOI18N
        TeknikRegionalLokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalLokasiKeyPressed(evt);
            }
        });
        FormInput.add(TeknikRegionalLokasi);
        TeknikRegionalLokasi.setBounds(115, 830, 310, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Lokasi");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(74, 830, 90, 23);

        TeknikReginalJenisJarum.setFocusTraversalPolicyProvider(true);
        TeknikReginalJenisJarum.setName("TeknikReginalJenisJarum"); // NOI18N
        TeknikReginalJenisJarum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikReginalJenisJarumKeyPressed(evt);
            }
        });
        FormInput.add(TeknikReginalJenisJarum);
        TeknikReginalJenisJarum.setBounds(554, 830, 170, 23);

        jLabel46.setText("Jenis Jarum / No :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(450, 830, 100, 23);

        jLabel162.setText(":");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(0, 860, 118, 23);

        TeknikRegionalKateter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TeknikRegionalKateter.setName("TeknikRegionalKateter"); // NOI18N
        TeknikRegionalKateter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalKateterKeyPressed(evt);
            }
        });
        FormInput.add(TeknikRegionalKateter);
        TeknikRegionalKateter.setBounds(122, 860, 85, 23);

        jLabel161.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel161.setText("Keteter");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(74, 860, 90, 23);

        jLabel160.setText("Kateter Fiksasi :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(210, 860, 116, 23);

        TeknikRegionalKateterFiksasi.setFocusTraversalPolicyProvider(true);
        TeknikRegionalKateterFiksasi.setName("TeknikRegionalKateterFiksasi"); // NOI18N
        TeknikRegionalKateterFiksasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalKateterFiksasiKeyPressed(evt);
            }
        });
        FormInput.add(TeknikRegionalKateterFiksasi);
        TeknikRegionalKateterFiksasi.setBounds(330, 860, 140, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("cm");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(473, 860, 30, 23);

        jLabel164.setText(":");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(0, 890, 143, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TeknikRegionalObatobatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TeknikRegionalObatobatan.setColumns(20);
        TeknikRegionalObatobatan.setRows(5);
        TeknikRegionalObatobatan.setName("TeknikRegionalObatobatan"); // NOI18N
        TeknikRegionalObatobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalObatobatanKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(TeknikRegionalObatobatan);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(147, 890, 577, 43);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("Obat-obatan");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(74, 890, 90, 23);

        jLabel166.setText(":");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(0, 940, 133, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        TeknikRegionalKomplikasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TeknikRegionalKomplikasi.setColumns(20);
        TeknikRegionalKomplikasi.setRows(5);
        TeknikRegionalKomplikasi.setName("TeknikRegionalKomplikasi"); // NOI18N
        TeknikRegionalKomplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalKomplikasiKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(TeknikRegionalKomplikasi);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(137, 940, 587, 43);

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setText("Komplikasi");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(74, 940, 90, 23);

        jLabel167.setText(":");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(0, 990, 105, 23);

        TeknikRegionalHasil.setFocusTraversalPolicyProvider(true);
        TeknikRegionalHasil.setName("TeknikRegionalHasil"); // NOI18N
        TeknikRegionalHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikRegionalHasilKeyPressed(evt);
            }
        });
        FormInput.add(TeknikRegionalHasil);
        TeknikRegionalHasil.setBounds(109, 990, 615, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Hasil");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(74, 990, 90, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

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

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2023" }));
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else{
            if(Sequel.menyimpantf("penilaian_pre_anestesi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat, Tanggal & Jam",40,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                    Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "+TglOperasi.getSelectedItem().toString().substring(11,19),Diagnosa.getText(),RencanaTindakan.getText(), 
                    TB.getText(),BB.getText(),TD.getText(),IO2.getText(),Nadi.getText(),Pernapasan.getText(),Suhu.getText(),FisikCardio.getText(),FisikParu.getText(),
                    FisikAbdomen.getText(),FisikExtrimitas.getText(),FisikEndokrin.getText(),FisikGinjal.getText(),FisikObat.getText(),FisikLaborat.getText(), 
                    FisikPenunjang.getText(),PenyakitAlergiObat.getText(),PenyakitAlergiLainnya.getText(),PenyakitTerapi.getText(),PenyakitKebiasaanMerokok.getSelectedItem().toString(), 
                    PenyakitKebiasaanJumlahRokok.getText(),PenyakitKebiasaanAlkohol.getSelectedItem().toString(),PenyakitKebiasaanJumlahAlkohol.getText(),
                    PenyakitKebiasaanObat.getSelectedItem().toString(),PenyakitKebiasaanObatDiminum.getText(),MedisCardio.getText(),MedisRespiratory.getText(),MedisEndocrine.getText(),
                    MedisLainnya.getText(),AngkaASA.getSelectedItem().toString(),Valid.SetTgl(TglPuasa.getSelectedItem()+"")+" "+TglPuasa.getSelectedItem().toString().substring(11,19), 
                    RencanaAnestesi.getSelectedItem().toString(),RencanaPerawatan.getText(),CatatanKhusus.getText()
                })==true){
                    emptTeks();
            }
        }*/
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           //Valid.pindah(evt,CatatanKhusus,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }*/
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"+
                        "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
                        "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
                        "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
                        "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
                        "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
                        "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
                        "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
                        "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "+
                        "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pre_anestesi.tanggal between ? and ? order by penilaian_pre_anestesi.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"+
                        "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
                        "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
                        "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
                        "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
                        "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
                        "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
                        "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
                        "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "+
                        "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pre_anestesi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_pre_anestesi.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_pre_anestesi.tanggal");
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Operasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Tindakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>IO2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pernapasan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Cardiovasculer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Paru</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Extrimitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Endokrin</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Ginjal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Obat-obatan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Laborat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Asesmen Fisik Penunjang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Alergi Obat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Alergi Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Terapi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebiasaan Merokok</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml.Rokok</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebiasaan Alkohol</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml.Alko</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penggunaan Obat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Dikonsumsi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Cardiovasculer</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Respiratory</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Endocrine</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Medis Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Angka ASA</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mulai Puasa</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Anestesi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rencana Perawatan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Catatan Khusus</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_operasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("tb")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("io2")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("pernapasan")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_cardiovasculer")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_paru")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_extrimitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_endokrin")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_ginjal")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_obatobatan")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_laborat")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_penunjang")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_alergiobat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_alergilainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_terapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_merokok")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_merokok")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_alkohol")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_alkohol")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_obat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_obat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_cardiovasculer")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_respiratory")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_endocrine")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_lainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("asa")+"</td>"+
                               "<td valign='top'>"+rs.getString("puasa")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_anestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_perawatan")+"</td>"+
                               "<td valign='top'>"+rs.getString("catatan_khusus")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN PRE ANESTESI<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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
                getData();
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
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,BtnDokter,Diagnosa);
    }//GEN-LAST:event_TglAsuhanKeyPressed

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
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianPreAnestesi.jasper","report","::[ Laporan Penilaian Pre Anestesi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"+
                "penilaian_pre_anestesi.kd_dokter,DATE_FORMAT(penilaian_pre_anestesi.tanggal_operasi,'%d-%m-%Y %H:%m:%s') as tanggal_operasi,penilaian_pre_anestesi.diagnosa,"+
                "penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,"+
                "penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,"+
                "penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,"+
                "penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,"+
                "penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,"+
                "penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,"+
                "penilaian_pre_anestesi.riwayat_kebiasaan_obat,penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,"+
                "penilaian_pre_anestesi.riwayat_medis_respiratory,penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,"+
                "penilaian_pre_anestesi.asa,DATE_FORMAT(penilaian_pre_anestesi.puasa,'%d-%m-%Y %H:%m:%s') as puasa,penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,"+
                "penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "+
                "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where penilaian_pre_anestesi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                "and penilaian_pre_anestesi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        //Valid.pindah(evt,RencanaTindakan,BB);
    }//GEN-LAST:event_EKGKeyPressed

    private void LainlainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainlainKeyPressed
        Valid.pindah(evt,EKG,TD);
    }//GEN-LAST:event_LainlainKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Lainlain,IntubasiSulitIntubasi);
    }//GEN-LAST:event_TDKeyPressed

    private void IntubasiSulitIntubasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntubasiSulitIntubasiKeyPressed
        //Valid.pindah(evt,TD,Nadi);
    }//GEN-LAST:event_IntubasiSulitIntubasiKeyPressed

    private void AsesmenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenKeyPressed
        //Valid.pindah(evt,PenyakitTerapi,PenyakitKebiasaanJumlahRokok);
    }//GEN-LAST:event_AsesmenKeyPressed

    private void VentilasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VentilasiKeyPressed
        //Valid.pindah(evt,PenyakitKebiasaanMerokok,PenyakitKebiasaanAlkohol);
    }//GEN-LAST:event_VentilasiKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        //Valid.pindah(evt,RR,GCS);
    }//GEN-LAST:event_SuhuKeyPressed

    private void PerencanaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerencanaanKeyPressed
        //Valid.pindah2(evt,CaraMasuk,RPD);
    }//GEN-LAST:event_PerencanaanKeyPressed

    private void InfusPerifierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfusPerifierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InfusPerifierKeyPressed

    private void CVCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CVCKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CVCKeyPressed

    private void PremedikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PremedikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PremedikasiKeyPressed

    private void PosisiInfusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosisiInfusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PosisiInfusKeyPressed

    private void KeteranganPremedikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPremedikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPremedikasiKeyPressed

    private void InfusInduksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InfusInduksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InfusInduksiKeyPressed

    private void KeteranganInfusInduksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganInfusInduksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganInfusInduksiKeyPressed

    private void FaceMaskNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaceMaskNoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaceMaskNoKeyPressed

    private void NasopharingNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NasopharingNoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NasopharingNoKeyPressed

    private void ETTNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ETTNoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ETTNoKeyPressed

    private void ETTFiksasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ETTFiksasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ETTFiksasiKeyPressed

    private void ETTJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ETTJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ETTJenisKeyPressed

    private void LMANoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LMANoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LMANoKeyPressed

    private void LMAJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LMAJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LMAJenisKeyPressed

    private void TracheostomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TracheostomiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TracheostomiKeyPressed

    private void BronchoscopiFiberoptikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BronchoscopiFiberoptikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BronchoscopiFiberoptikKeyPressed

    private void GlidescopiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GlidescopiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GlidescopiKeyPressed

    private void TatalaksanaLainlainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaLainlainKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TatalaksanaLainlainKeyPressed

    private void IntubasiSesudahTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntubasiSesudahTidurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntubasiSesudahTidurKeyPressed

    private void IntubasiOralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntubasiOralKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntubasiOralKeyPressed

    private void IntubasiTracheostomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntubasiTracheostomiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntubasiTracheostomiKeyPressed

    private void KeteranganIntubasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganIntubasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganIntubasiKeyPressed

    private void IntubasiSulitVentilasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntubasiSulitVentilasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntubasiSulitVentilasiKeyPressed

    private void TeknikRegionalJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalJenisKeyPressed

    private void TeknikRegionalLokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalLokasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalLokasiKeyPressed

    private void TeknikReginalJenisJarumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikReginalJenisJarumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikReginalJenisJarumKeyPressed

    private void TeknikRegionalKateterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalKateterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalKateterKeyPressed

    private void TeknikRegionalKateterFiksasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalKateterFiksasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalKateterFiksasiKeyPressed

    private void TeknikRegionalObatobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalObatobatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalObatobatanKeyPressed

    private void TeknikRegionalKomplikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalKomplikasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalKomplikasiKeyPressed

    private void TeknikRegionalHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikRegionalHasilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TeknikRegionalHasilKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPreInduksi dialog = new RMPenilaianPreInduksi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Asesmen;
    private widget.TextBox BronchoscopiFiberoptik;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CVC;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox EKG;
    private widget.TextBox ETTFiksasi;
    private widget.TextBox ETTJenis;
    private widget.TextBox ETTNo;
    private widget.TextBox FaceMaskNo;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Glidescopi;
    private widget.ComboBox InfusInduksi;
    private widget.TextArea InfusPerifier;
    private widget.ComboBox IntubasiOral;
    private widget.ComboBox IntubasiSesudahTidur;
    private widget.TextBox IntubasiSulitIntubasi;
    private widget.TextBox IntubasiSulitVentilasi;
    private widget.ComboBox IntubasiTracheostomi;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KeteranganInfusInduksi;
    private widget.TextArea KeteranganIntubasi;
    private widget.TextBox KeteranganPremedikasi;
    private widget.Label LCount;
    private widget.TextBox LMAJenis;
    private widget.TextBox LMANo;
    private widget.TextBox Lainlain;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NasopharingNo;
    private widget.TextBox NmDokter;
    private widget.TextArea Perencanaan;
    private widget.ComboBox PosisiInfus;
    private widget.ComboBox Premedikasi;
    private widget.TextBox RR;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TatalaksanaLainlain;
    private widget.TextBox TeknikReginalJenisJarum;
    private widget.TextBox TeknikRegionalHasil;
    private widget.TextBox TeknikRegionalJenis;
    private widget.ComboBox TeknikRegionalKateter;
    private widget.TextBox TeknikRegionalKateterFiksasi;
    private widget.TextArea TeknikRegionalKomplikasi;
    private widget.TextBox TeknikRegionalLokasi;
    private widget.TextArea TeknikRegionalObatobatan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox Tracheostomi;
    private widget.TextBox Ventilasi;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel15;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
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
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"+
                        "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
                        "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
                        "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
                        "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
                        "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
                        "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
                        "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
                        "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "+
                        "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pre_anestesi.tanggal between ? and ? order by penilaian_pre_anestesi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pre_anestesi.tanggal,"+
                        "penilaian_pre_anestesi.kd_dokter,penilaian_pre_anestesi.tanggal_operasi,penilaian_pre_anestesi.diagnosa,penilaian_pre_anestesi.rencana_tindakan,penilaian_pre_anestesi.tb,"+
                        "penilaian_pre_anestesi.bb,penilaian_pre_anestesi.td,penilaian_pre_anestesi.io2,penilaian_pre_anestesi.nadi,penilaian_pre_anestesi.pernapasan,penilaian_pre_anestesi.suhu,"+
                        "penilaian_pre_anestesi.fisik_cardiovasculer,penilaian_pre_anestesi.fisik_paru,penilaian_pre_anestesi.fisik_abdomen,penilaian_pre_anestesi.fisik_extrimitas,"+
                        "penilaian_pre_anestesi.fisik_endokrin,penilaian_pre_anestesi.fisik_ginjal,penilaian_pre_anestesi.fisik_obatobatan,penilaian_pre_anestesi.fisik_laborat,"+
                        "penilaian_pre_anestesi.fisik_penunjang,penilaian_pre_anestesi.riwayat_penyakit_alergiobat,penilaian_pre_anestesi.riwayat_penyakit_alergilainnya,"+
                        "penilaian_pre_anestesi.riwayat_penyakit_terapi,penilaian_pre_anestesi.riwayat_kebiasaan_merokok,penilaian_pre_anestesi.riwayat_kebiasaan_ket_merokok,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_ket_alkohol,penilaian_pre_anestesi.riwayat_kebiasaan_obat,"+
                        "penilaian_pre_anestesi.riwayat_kebiasaan_ket_obat,penilaian_pre_anestesi.riwayat_medis_cardiovasculer,penilaian_pre_anestesi.riwayat_medis_respiratory,"+
                        "penilaian_pre_anestesi.riwayat_medis_endocrine,penilaian_pre_anestesi.riwayat_medis_lainnya,penilaian_pre_anestesi.asa,penilaian_pre_anestesi.puasa,"+
                        "penilaian_pre_anestesi.rencana_anestesi,penilaian_pre_anestesi.rencana_perawatan,penilaian_pre_anestesi.catatan_khusus,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pre_anestesi on reg_periksa.no_rawat=penilaian_pre_anestesi.no_rawat "+
                        "inner join dokter on penilaian_pre_anestesi.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pre_anestesi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_pre_anestesi.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_pre_anestesi.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("tanggal_operasi"),rs.getString("diagnosa"),rs.getString("rencana_tindakan"),rs.getString("tb"),rs.getString("bb"),rs.getString("td"),rs.getString("io2"),rs.getString("nadi"),rs.getString("pernapasan"),
                        rs.getString("suhu"),rs.getString("fisik_cardiovasculer"),rs.getString("fisik_paru"),rs.getString("fisik_abdomen"),rs.getString("fisik_extrimitas"),rs.getString("fisik_endokrin"),rs.getString("fisik_ginjal"),
                        rs.getString("fisik_obatobatan"),rs.getString("fisik_laborat"),rs.getString("fisik_penunjang"),rs.getString("riwayat_penyakit_alergiobat"),rs.getString("riwayat_penyakit_alergilainnya"),
                        rs.getString("riwayat_penyakit_terapi"),rs.getString("riwayat_kebiasaan_merokok"),rs.getString("riwayat_kebiasaan_ket_merokok"),rs.getString("riwayat_kebiasaan_alkohol"),rs.getString("riwayat_kebiasaan_ket_alkohol"),
                        rs.getString("riwayat_kebiasaan_obat"),rs.getString("riwayat_kebiasaan_ket_obat"),rs.getString("riwayat_medis_cardiovasculer"),rs.getString("riwayat_medis_respiratory"),rs.getString("riwayat_medis_endocrine"),
                        rs.getString("riwayat_medis_lainnya"),rs.getString("asa"),rs.getString("puasa"),rs.getString("rencana_anestesi"),rs.getString("rencana_perawatan"),rs.getString("catatan_khusus")
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
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        RR.setText("");
        EKG.setText("");
        Lainlain.setText("");
        Perencanaan.setText("");
        Asesmen.setSelectedIndex(0);
        InfusPerifier.setText("");
        CVC.setText("");
        PosisiInfus.setSelectedIndex(0);
        Premedikasi.setSelectedIndex(0);
        KeteranganPremedikasi.setText("");
        InfusInduksi.setSelectedIndex(0);
        KeteranganInfusInduksi.setText("");
        FaceMaskNo.setText("");
        NasopharingNo.setText("");
        ETTNo.setText("");
        ETTJenis.setText("");
        ETTFiksasi.setText("");
        LMANo.setText("");
        LMAJenis.setText("");
        Tracheostomi.setText("");
        BronchoscopiFiberoptik.setText("");
        Glidescopi.setText("");
        TatalaksanaLainlain.setText("");
        IntubasiSesudahTidur.setSelectedIndex(0);
        IntubasiOral.setSelectedIndex(0);
        IntubasiTracheostomi.setSelectedIndex(0);
        KeteranganIntubasi.setText("");
        IntubasiSulitVentilasi.setText("");
        IntubasiSulitIntubasi.setText("");
        Ventilasi.setText("");
        TeknikRegionalJenis.setText("");
        TeknikRegionalLokasi.setText("");
        TeknikReginalJenisJarum.setText("");
        TeknikRegionalKateter.setSelectedIndex(0);
        TeknikRegionalKateterFiksasi.setText("");
        TeknikRegionalObatobatan.setText("");
        TeknikRegionalKomplikasi.setText("");
        TeknikRegionalHasil.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
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
        BtnSimpan.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnHapus.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
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
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_pre_anestesi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("penilaian_pre_anestesi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kd_dokter=?,tanggal_operasi=?,diagnosa=?,rencana_tindakan=?,tb=?,bb=?,td=?,io2=?,nadi=?,"+
                "pernapasan=?,suhu=?,fisik_cardiovasculer=?,fisik_paru=?,fisik_abdomen=?,fisik_extrimitas=?,fisik_endokrin=?,fisik_ginjal=?,fisik_obatobatan=?,fisik_laborat=?,fisik_penunjang=?,"+
                "riwayat_penyakit_alergiobat=?,riwayat_penyakit_alergilainnya=?,riwayat_penyakit_terapi=?,riwayat_kebiasaan_merokok=?,riwayat_kebiasaan_ket_merokok=?,riwayat_kebiasaan_alkohol=?,"+
                "riwayat_kebiasaan_ket_alkohol=?,riwayat_kebiasaan_obat=?,riwayat_kebiasaan_ket_obat=?,riwayat_medis_cardiovasculer=?,riwayat_medis_respiratory=?,riwayat_medis_endocrine=?,"+
                "riwayat_medis_lainnya=?,asa=?,puasa=?,rencana_anestesi=?,rencana_perawatan=?,catatan_khusus=?",42,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "+TglOperasi.getSelectedItem().toString().substring(11,19),Diagnosa.getText(),RencanaTindakan.getText(), 
                TB.getText(),BB.getText(),TD.getText(),IO2.getText(),Nadi.getText(),Pernapasan.getText(),Suhu.getText(),FisikCardio.getText(),FisikParu.getText(),
                FisikAbdomen.getText(),FisikExtrimitas.getText(),FisikEndokrin.getText(),FisikGinjal.getText(),FisikObat.getText(),FisikLaborat.getText(), 
                FisikPenunjang.getText(),PenyakitAlergiObat.getText(),PenyakitAlergiLainnya.getText(),PenyakitTerapi.getText(),PenyakitKebiasaanMerokok.getSelectedItem().toString(), 
                PenyakitKebiasaanJumlahRokok.getText(),PenyakitKebiasaanAlkohol.getSelectedItem().toString(),PenyakitKebiasaanJumlahAlkohol.getText(),
                PenyakitKebiasaanObat.getSelectedItem().toString(),PenyakitKebiasaanObatDiminum.getText(),MedisCardio.getText(),MedisRespiratory.getText(),MedisEndocrine.getText(),
                MedisLainnya.getText(),AngkaASA.getSelectedItem().toString(),Valid.SetTgl(TglPuasa.getSelectedItem()+"")+" "+TglPuasa.getSelectedItem().toString().substring(11,19), 
                RencanaAnestesi.getSelectedItem().toString(),RencanaPerawatan.getText(),CatatanKhusus.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
}

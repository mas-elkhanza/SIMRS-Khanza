/*
 * by MAs ElKhanza
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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPerencanaanPemulangan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String pilihan="";
    private String finger="",lokasifile="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPerencanaanPemulangan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Masuk Dirawat","Rencana Pulang","Diagnosa Medis","Alasan Masuk / Dirawat",
            "Pengaruh RI Pasien & Keluarga","Keterangan Pengaruh RI Pasien & Keluarga","Pengaruh RI Pekerjaan/Sekolah","Keterangan Pengaruh RI Pekerjaan/Sekolah",
            "Pengaruh RI Keuangan","Keterangan Pengaruh RI Keuangan","Antisipasi Masalah","Keterangan Antisipasi Masalah","Bantuan Diperlukan","Keterangan Bantuan Diperlukan", 
            "Membantu Keperluan","Keterangan Yang Membantu Keperluan","Tinggal Sendiri","Keterangan Pasien Tinggal Sendiri","Peralatan Medis","Keterangan Peralatan Medis",
            "Alat Bantu","Keterangan Memerlukan Alat Bantu","Perawatan Khusus","Keterangan Perawatan Khusus","Memenuhi Kebutuhan","Keterangan Memenuhi Kebutuhan",
            "Nyeri Kronis","Keterangan Nyeri Kronis","Edukasi Kesehatan","Keterangan Edukasi Kesehatan","Keterampilkan Khusus","Keterangan Keterampilkan Khusus",
            "Pasien/Keluarga","NIP","Nama Petugas"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 40; i++) {
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(87);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(220);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(220);
            }else if(i==13){
                column.setPreferredWidth(119);
            }else if(i==14){
                column.setPreferredWidth(178);
            }else if(i==15){
                column.setPreferredWidth(99);
            }else if(i==16){
                column.setPreferredWidth(158);
            }else if(i==17){
                column.setPreferredWidth(108);
            }else if(i==18){
                column.setPreferredWidth(163);
            }else if(i==19){
                column.setPreferredWidth(110);
            }else if(i==20){
                column.setPreferredWidth(195);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(175);
            }else if(i==23){
                column.setPreferredWidth(87);
            }else if(i==24){
                column.setPreferredWidth(145);
            }else if(i==25){
                column.setPreferredWidth(61);
            }else if(i==26){
                column.setPreferredWidth(181);
            }else if(i==27){
                column.setPreferredWidth(98);
            }else if(i==28){
                column.setPreferredWidth(156);
            }else if(i==29){
                column.setPreferredWidth(112);
            }else if(i==30){
                column.setPreferredWidth(173);
            }else if(i==31){
                column.setPreferredWidth(67);
            }else if(i==32){
                column.setPreferredWidth(126);
            }else if(i==33){
                column.setPreferredWidth(98);
            }else if(i==34){
                column.setPreferredWidth(157);
            }else if(i==35){
                column.setPreferredWidth(116);
            }else if(i==36){
                column.setPreferredWidth(175);
            }else if(i==37){
                column.setPreferredWidth(150);
            }else if(i==38){
                column.setPreferredWidth(90);
            }else if(i==39){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaMedis.setDocument(new batasInput((int)50).getKata(DiagnosaMedis));
        AlasanMasuk.setDocument(new batasInput((int)150).getKata(AlasanMasuk));
        KeteranganPengaruhRIKeluarga.setDocument(new batasInput((int)100).getKata(KeteranganPengaruhRIKeluarga));
        KeteranganPengaruhRIPekerjaanSekolah.setDocument(new batasInput((int)100).getKata(KeteranganPengaruhRIPekerjaanSekolah));
        KeteranganPengaruhRIKeuangan.setDocument(new batasInput((int)100).getKata(KeteranganPengaruhRIKeuangan));
        KeteranganAntisipasiMasalah.setDocument(new batasInput((int)100).getKata(KeteranganAntisipasiMasalah));
        KeteranganBantuanDiperlukan.setDocument(new batasInput((int)100).getKata(KeteranganBantuanDiperlukan));
        KeteranganYangMembantuKeperluan.setDocument(new batasInput((int)100).getKata(KeteranganYangMembantuKeperluan));
        KeteranganTinggalSendiri.setDocument(new batasInput((int)100).getKata(KeteranganTinggalSendiri));
        KeteranganPeralatanMedis.setDocument(new batasInput((int)100).getKata(KeteranganPeralatanMedis));
        KeteranganAlatBantu.setDocument(new batasInput((int)100).getKata(KeteranganAlatBantu));
        KeteranganPerawatanKhusus.setDocument(new batasInput((int)100).getKata(KeteranganPerawatanKhusus));
        KeteranganMemenuhiKebutuhan.setDocument(new batasInput((int)100).getKata(KeteranganMemenuhiKebutuhan));
        KeteranganNyeriKronis.setDocument(new batasInput((int)100).getKata(KeteranganNyeriKronis));
        KeteranganEdukasiPasien.setDocument(new batasInput((int)100).getKata(KeteranganEdukasiPasien));
        KeteranganKeterampilanKhusus.setDocument(new batasInput((int)100).getKata(KeteranganKeterampilanKhusus));
        SaksiKeluarga.setDocument(new batasInput((int)50).getKata(SaksiKeluarga));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        isPhoto();
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    KdPetugas.requestFocus();
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
        LoadHTML2.setEditable(true);
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
        LoadHTML2.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jSeparator14 = new javax.swing.JSeparator();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TinggalSendiri = new widget.ComboBox();
        RencanaPemulangan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        KeteranganTinggalSendiri = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        label15 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel42 = new widget.Label();
        SaksiKeluarga = new widget.TextBox();
        MasukDirawat = new widget.TextBox();
        label12 = new widget.Label();
        jLabel40 = new widget.Label();
        DiagnosaMedis = new widget.TextBox();
        jLabel41 = new widget.Label();
        AlasanMasuk = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel43 = new widget.Label();
        KeteranganPengaruhRIKeluarga = new widget.TextBox();
        PengaruhRIKeluarga = new widget.ComboBox();
        jLabel44 = new widget.Label();
        PengaruhRIPekerjaanSekolah = new widget.ComboBox();
        KeteranganPengaruhRIPekerjaanSekolah = new widget.TextBox();
        jLabel45 = new widget.Label();
        PengaruhRIKeuangan = new widget.ComboBox();
        KeteranganPengaruhRIKeuangan = new widget.TextBox();
        jLabel54 = new widget.Label();
        AntisipasiMasalah = new widget.ComboBox();
        KeteranganAntisipasiMasalah = new widget.TextBox();
        jLabel55 = new widget.Label();
        BantuanDiperlukan = new widget.ComboBox();
        KeteranganBantuanDiperlukan = new widget.TextBox();
        jLabel56 = new widget.Label();
        YangMembantuKeperluan = new widget.ComboBox();
        KeteranganYangMembantuKeperluan = new widget.TextBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        PeralatanMedis = new widget.ComboBox();
        KeteranganPeralatanMedis = new widget.TextBox();
        jLabel59 = new widget.Label();
        AlatBantu = new widget.ComboBox();
        KeteranganAlatBantu = new widget.TextBox();
        jLabel60 = new widget.Label();
        PerawatanKhusus = new widget.ComboBox();
        KeteranganPerawatanKhusus = new widget.TextBox();
        jLabel61 = new widget.Label();
        MemenuhiKebutuhan = new widget.ComboBox();
        KeteranganMemenuhiKebutuhan = new widget.TextBox();
        jLabel62 = new widget.Label();
        NyeriKronis = new widget.ComboBox();
        KeteranganNyeriKronis = new widget.TextBox();
        jLabel63 = new widget.Label();
        EdukasiPasien = new widget.ComboBox();
        KeteranganEdukasiPasien = new widget.TextBox();
        jLabel64 = new widget.Label();
        KeterampilanKhusus = new widget.ComboBox();
        KeteranganKeterampilanKhusus = new widget.TextBox();
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
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Perencanaan Pemulangan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 813));
        FormInput.setLayout(null);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

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
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        label11.setText("Masuk Dirawat :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 94, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        TinggalSendiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TinggalSendiri.setName("TinggalSendiri"); // NOI18N
        TinggalSendiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalSendiriKeyPressed(evt);
            }
        });
        FormInput.add(TinggalSendiri);
        TinggalSendiri.setBounds(40, 380, 80, 23);

        RencanaPemulangan.setForeground(new java.awt.Color(50, 70, 50));
        RencanaPemulangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-05-2023" }));
        RencanaPemulangan.setDisplayFormat("dd-MM-yyyy");
        RencanaPemulangan.setName("RencanaPemulangan"); // NOI18N
        RencanaPemulangan.setOpaque(false);
        RencanaPemulangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaPemulanganKeyPressed(evt);
            }
        });
        FormInput.add(RencanaPemulangan);
        RencanaPemulangan.setBounds(364, 40, 90, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        KeteranganTinggalSendiri.setHighlighter(null);
        KeteranganTinggalSendiri.setName("KeteranganTinggalSendiri"); // NOI18N
        KeteranganTinggalSendiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTinggalSendiriKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTinggalSendiri);
        KeteranganTinggalSendiri.setBounds(124, 380, 730, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 760, 880, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("Proses Tanya Jawab Dilakukan Oleh :");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(15, 760, 220, 23);

        label15.setText("Perawat/Petugas :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 780, 130, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(134, 780, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(236, 780, 193, 23);

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
        BtnDokter.setBounds(431, 780, 28, 23);

        jLabel42.setText("Pasien/Keluarga :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(460, 780, 110, 23);

        SaksiKeluarga.setHighlighter(null);
        SaksiKeluarga.setName("SaksiKeluarga"); // NOI18N
        SaksiKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaksiKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(SaksiKeluarga);
        SaksiKeluarga.setBounds(574, 780, 280, 23);

        MasukDirawat.setEditable(false);
        MasukDirawat.setHighlighter(null);
        MasukDirawat.setName("MasukDirawat"); // NOI18N
        FormInput.add(MasukDirawat);
        MasukDirawat.setBounds(98, 40, 131, 23);

        label12.setText("Rencana Pemulangan :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(240, 40, 120, 23);

        jLabel40.setText("Diagnosa Medis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(453, 40, 100, 23);

        DiagnosaMedis.setHighlighter(null);
        DiagnosaMedis.setName("DiagnosaMedis"); // NOI18N
        DiagnosaMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMedisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaMedis);
        DiagnosaMedis.setBounds(557, 40, 297, 23);

        jLabel41.setText("Alasan Masuk / Dirawat :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 70, 137, 23);

        AlasanMasuk.setHighlighter(null);
        AlasanMasuk.setName("AlasanMasuk"); // NOI18N
        AlasanMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanMasukKeyPressed(evt);
            }
        });
        FormInput.add(AlasanMasuk);
        AlasanMasuk.setBounds(141, 70, 713, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("1. Pengaruh Rawat Inap Terhadap :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(15, 100, 590, 23);

        jLabel43.setText("Pasien & Keluarga Pasien :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 120, 180, 23);

        KeteranganPengaruhRIKeluarga.setHighlighter(null);
        KeteranganPengaruhRIKeluarga.setName("KeteranganPengaruhRIKeluarga"); // NOI18N
        KeteranganPengaruhRIKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPengaruhRIKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPengaruhRIKeluarga);
        KeteranganPengaruhRIKeluarga.setBounds(268, 120, 586, 23);

        PengaruhRIKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PengaruhRIKeluarga.setName("PengaruhRIKeluarga"); // NOI18N
        PengaruhRIKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengaruhRIKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(PengaruhRIKeluarga);
        PengaruhRIKeluarga.setBounds(184, 120, 80, 23);

        jLabel44.setText("Pekerjaan / Sekolah :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 150, 180, 23);

        PengaruhRIPekerjaanSekolah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PengaruhRIPekerjaanSekolah.setName("PengaruhRIPekerjaanSekolah"); // NOI18N
        PengaruhRIPekerjaanSekolah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengaruhRIPekerjaanSekolahKeyPressed(evt);
            }
        });
        FormInput.add(PengaruhRIPekerjaanSekolah);
        PengaruhRIPekerjaanSekolah.setBounds(184, 150, 80, 23);

        KeteranganPengaruhRIPekerjaanSekolah.setHighlighter(null);
        KeteranganPengaruhRIPekerjaanSekolah.setName("KeteranganPengaruhRIPekerjaanSekolah"); // NOI18N
        KeteranganPengaruhRIPekerjaanSekolah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPengaruhRIPekerjaanSekolahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPengaruhRIPekerjaanSekolah);
        KeteranganPengaruhRIPekerjaanSekolah.setBounds(268, 150, 586, 23);

        jLabel45.setText("Keuangan :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 180, 180, 23);

        PengaruhRIKeuangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PengaruhRIKeuangan.setName("PengaruhRIKeuangan"); // NOI18N
        PengaruhRIKeuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengaruhRIKeuanganKeyPressed(evt);
            }
        });
        FormInput.add(PengaruhRIKeuangan);
        PengaruhRIKeuangan.setBounds(184, 180, 80, 23);

        KeteranganPengaruhRIKeuangan.setHighlighter(null);
        KeteranganPengaruhRIKeuangan.setName("KeteranganPengaruhRIKeuangan"); // NOI18N
        KeteranganPengaruhRIKeuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPengaruhRIKeuanganKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPengaruhRIKeuangan);
        KeteranganPengaruhRIKeuangan.setBounds(268, 180, 586, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("2. Antisipasi Terhadap Masalah Saat Pulang ?");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(15, 210, 590, 23);

        AntisipasiMasalah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AntisipasiMasalah.setName("AntisipasiMasalah"); // NOI18N
        AntisipasiMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntisipasiMasalahKeyPressed(evt);
            }
        });
        FormInput.add(AntisipasiMasalah);
        AntisipasiMasalah.setBounds(40, 230, 80, 23);

        KeteranganAntisipasiMasalah.setHighlighter(null);
        KeteranganAntisipasiMasalah.setName("KeteranganAntisipasiMasalah"); // NOI18N
        KeteranganAntisipasiMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAntisipasiMasalahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAntisipasiMasalah);
        KeteranganAntisipasiMasalah.setBounds(124, 230, 730, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("3. Bantuan Diperlukan Dalam Hal ?");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(15, 260, 590, 23);

        BantuanDiperlukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menyiapkan Makanan", "Edukasi Kesehatan", "Makan", "Mandi", "Diet", "Berpakaian", "Menyiapkan Obat", "Transportasi", "Minum Obat" }));
        BantuanDiperlukan.setName("BantuanDiperlukan"); // NOI18N
        BantuanDiperlukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BantuanDiperlukanKeyPressed(evt);
            }
        });
        FormInput.add(BantuanDiperlukan);
        BantuanDiperlukan.setBounds(40, 280, 160, 23);

        KeteranganBantuanDiperlukan.setHighlighter(null);
        KeteranganBantuanDiperlukan.setName("KeteranganBantuanDiperlukan"); // NOI18N
        KeteranganBantuanDiperlukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBantuanDiperlukanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBantuanDiperlukan);
        KeteranganBantuanDiperlukan.setBounds(204, 280, 650, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("5. Apakah Pasien Tinggal Sendiri Setelah Keluar Dari Rumah Sakit ?");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(15, 360, 590, 23);

        YangMembantuKeperluan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ada" }));
        YangMembantuKeperluan.setName("YangMembantuKeperluan"); // NOI18N
        YangMembantuKeperluan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YangMembantuKeperluanKeyPressed(evt);
            }
        });
        FormInput.add(YangMembantuKeperluan);
        YangMembantuKeperluan.setBounds(40, 330, 80, 23);

        KeteranganYangMembantuKeperluan.setHighlighter(null);
        KeteranganYangMembantuKeperluan.setName("KeteranganYangMembantuKeperluan"); // NOI18N
        KeteranganYangMembantuKeperluan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganYangMembantuKeperluanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganYangMembantuKeperluan);
        KeteranganYangMembantuKeperluan.setBounds(124, 330, 730, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("4. Adakah Yang Membantu Keperluan Di Atas ?");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(15, 310, 590, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("6. Apakah Pasien Menggunakan Peralatan Medis (Kateter, NGT, Oksigen, Dll) Di Rumah Setelah Keluar / Pulang ?");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(15, 410, 820, 23);

        PeralatanMedis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PeralatanMedis.setName("PeralatanMedis"); // NOI18N
        PeralatanMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeralatanMedisKeyPressed(evt);
            }
        });
        FormInput.add(PeralatanMedis);
        PeralatanMedis.setBounds(40, 430, 80, 23);

        KeteranganPeralatanMedis.setHighlighter(null);
        KeteranganPeralatanMedis.setName("KeteranganPeralatanMedis"); // NOI18N
        KeteranganPeralatanMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPeralatanMedisKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPeralatanMedis);
        KeteranganPeralatanMedis.setBounds(124, 430, 730, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("7. Apakah Pasien Memerlukan Alat Bantu (Tongkat, Kursi Roda, Walker, Dll) Setelah Keluar Keluar / Pulang ?");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(15, 460, 820, 23);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(40, 480, 80, 23);

        KeteranganAlatBantu.setHighlighter(null);
        KeteranganAlatBantu.setName("KeteranganAlatBantu"); // NOI18N
        KeteranganAlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlatBantu);
        KeteranganAlatBantu.setBounds(124, 480, 730, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("8. Apakah Memerlukan Bantuan / Perawatan Khusus (Homecare, Home Visit) Di Rumah Setelah Keluar / Pulang ?");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(15, 510, 820, 23);

        PerawatanKhusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PerawatanKhusus.setName("PerawatanKhusus"); // NOI18N
        PerawatanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerawatanKhususKeyPressed(evt);
            }
        });
        FormInput.add(PerawatanKhusus);
        PerawatanKhusus.setBounds(40, 530, 80, 23);

        KeteranganPerawatanKhusus.setHighlighter(null);
        KeteranganPerawatanKhusus.setName("KeteranganPerawatanKhusus"); // NOI18N
        KeteranganPerawatanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPerawatanKhususKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPerawatanKhusus);
        KeteranganPerawatanKhusus.setBounds(124, 530, 730, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("9. Apakah Pasien Bermasalah Dalam Memenuhi Kebutuhan Pribadinya (Makan, Minum, BAK, BAB, Dll) Setelah Keluar / Pulang ?");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(15, 560, 820, 23);

        MemenuhiKebutuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        MemenuhiKebutuhan.setName("MemenuhiKebutuhan"); // NOI18N
        MemenuhiKebutuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MemenuhiKebutuhanKeyPressed(evt);
            }
        });
        FormInput.add(MemenuhiKebutuhan);
        MemenuhiKebutuhan.setBounds(40, 580, 80, 23);

        KeteranganMemenuhiKebutuhan.setHighlighter(null);
        KeteranganMemenuhiKebutuhan.setName("KeteranganMemenuhiKebutuhan"); // NOI18N
        KeteranganMemenuhiKebutuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMemenuhiKebutuhanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMemenuhiKebutuhan);
        KeteranganMemenuhiKebutuhan.setBounds(124, 580, 730, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("10. Apakah Pasien Memiliki Nyeri Kronis Dan Kelelahan Setelah Keluar / Pulang ?");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(15, 610, 820, 23);

        NyeriKronis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        NyeriKronis.setName("NyeriKronis"); // NOI18N
        NyeriKronis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKronisKeyPressed(evt);
            }
        });
        FormInput.add(NyeriKronis);
        NyeriKronis.setBounds(40, 630, 80, 23);

        KeteranganNyeriKronis.setHighlighter(null);
        KeteranganNyeriKronis.setName("KeteranganNyeriKronis"); // NOI18N
        KeteranganNyeriKronis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganNyeriKronisKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganNyeriKronis);
        KeteranganNyeriKronis.setBounds(124, 630, 730, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("11. Apakah Pasien & Keluarga Memerlukan Edukasi Kesehatan (Obatan-obatan, Efek Samping Obat, Nyeri Diit, Mencari Pertolongan, Follow Up, Dll) Setelah Keluar / Pulang ?");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(15, 660, 850, 23);

        EdukasiPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        EdukasiPasien.setName("EdukasiPasien"); // NOI18N
        EdukasiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiPasienKeyPressed(evt);
            }
        });
        FormInput.add(EdukasiPasien);
        EdukasiPasien.setBounds(40, 680, 80, 23);

        KeteranganEdukasiPasien.setHighlighter(null);
        KeteranganEdukasiPasien.setName("KeteranganEdukasiPasien"); // NOI18N
        KeteranganEdukasiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEdukasiPasienKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEdukasiPasien);
        KeteranganEdukasiPasien.setBounds(124, 680, 730, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("12. Apakah Pasien Dan Keluarga Memerlukan Keterampilan Khusus (Perawatan Luka, Injeksi, Perawatan Bayi, Dll) Setelah Keluar / Pulang ?");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(15, 710, 850, 23);

        KeterampilanKhusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KeterampilanKhusus.setName("KeterampilanKhusus"); // NOI18N
        KeterampilanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeterampilanKhususKeyPressed(evt);
            }
        });
        FormInput.add(KeterampilanKhusus);
        KeterampilanKhusus.setBounds(40, 730, 80, 23);

        KeteranganKeterampilanKhusus.setHighlighter(null);
        KeteranganKeterampilanKhusus.setName("KeteranganKeterampilanKhusus"); // NOI18N
        KeteranganKeterampilanKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeterampilanKhususKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKeterampilanKhusus);
        KeteranganKeterampilanKhusus.setBounds(124, 730, 730, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Perencanaan Pemulangan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        jLabel19.setText("Masuk Dirawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-05-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-05-2023" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(185, 23));
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

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Edukasi & Konfirmasi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Perencanaan Pemulangan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(AlasanMasuk.getText().trim().equals("")){
            Valid.textKosong(AlasanMasuk,"Alasan Masuk / Dirawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Pasien/Keluarga");
        }else{
            if(Sequel.menyimpantf("perencanaan_pemulangan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Pernyataan",34,new String[]{
                    TNoRw.getText(),Valid.SetTgl(RencanaPemulangan.getSelectedItem()+""),AlasanMasuk.getText(),DiagnosaMedis.getText(),PengaruhRIKeluarga.getSelectedItem().toString(),
                    KeteranganPengaruhRIKeluarga.getText(),PengaruhRIPekerjaanSekolah.getSelectedItem().toString(),KeteranganPengaruhRIPekerjaanSekolah.getText(),
                    PengaruhRIKeuangan.getSelectedItem().toString(),KeteranganPengaruhRIKeuangan.getText(),AntisipasiMasalah.getSelectedItem().toString(),KeteranganAntisipasiMasalah.getText(),
                    BantuanDiperlukan.getSelectedItem().toString(),KeteranganBantuanDiperlukan.getText(),YangMembantuKeperluan.getSelectedItem().toString(),KeteranganYangMembantuKeperluan.getText(),
                    TinggalSendiri.getSelectedItem().toString(),KeteranganTinggalSendiri.getText(),PeralatanMedis.getSelectedItem().toString(),KeteranganPeralatanMedis.getText(),
                    AlatBantu.getSelectedItem().toString(),KeteranganAlatBantu.getText(),PerawatanKhusus.getSelectedItem().toString(),KeteranganPerawatanKhusus.getText(),
                    MemenuhiKebutuhan.getSelectedItem().toString(),KeteranganMemenuhiKebutuhan.getText(),NyeriKronis.getSelectedItem().toString(),KeteranganNyeriKronis.getText(),
                    EdukasiPasien.getSelectedItem().toString(),KeteranganEdukasiPasien.getText(),KeterampilanKhusus.getSelectedItem().toString(),KeteranganKeterampilanKhusus.getText(),
                    SaksiKeluarga.getText(),KdPetugas.getText()
                })==true){
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SaksiKeluarga,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else if(DiagnosaMedis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaMedis,"Diagnosa Medis");
        }else if(AlasanMasuk.getText().trim().equals("")){
            Valid.textKosong(AlasanMasuk,"Alasan Masuk / Dirawat");
        }else if(SaksiKeluarga.getText().trim().equals("")){
            Valid.textKosong(SaksiKeluarga,"Pasien/Keluarga");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString())){
                        ganti();
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
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 
                
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                            "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                            "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                            "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                            "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                            "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                            "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                            "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                            "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                            "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                            "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                            "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                            "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                            "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                            "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                            "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                            "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                            "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                            "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                            "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                            "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                            "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                            "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                            "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                            "reg_periksa.tgl_registrasi between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                            "perencanaan_pemulangan.nip like ? or petugas.nama like ?) order by reg_periksa.tgl_registrasi");
                }

                try {
                    if(TCari.getText().trim().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                    } 
                    rs=ps.executeQuery();
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                    switch (pilihan) {
                        case "Laporan 1 (HTML)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Masuk Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Pulang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Masuk / Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Yang Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pasien Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memerlukan Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien/Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Petugas</td>"+
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
                                            "<td valign='top'>"+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_pasien_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RencanaPemulangan.html");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 2 (WPS)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.Rawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>No.RM</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Pasien</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tgl.Lahir</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>J.K.</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Masuk Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Rencana Pulang</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Diagnosa Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alasan Masuk / Dirawat</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pasien & Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Pekerjaan/Sekolah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pengaruh RI Keuangan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Antisipasi Masalah</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Bantuan Diperlukan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Yang Membantu Keperluan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Pasien Tinggal Sendiri</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Peralatan Medis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memerlukan Alat Bantu</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Perawatan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Memenuhi Kebutuhan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Nyeri Kronis</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Edukasi Kesehatan</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Keterangan Keterampilkan Khusus</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pasien/Keluarga</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>NIP</td>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Nama Petugas</td>"+
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
                                            "<td valign='top'>"+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"</td>"+
                                            "<td valign='top'>"+rs.getString("rencana_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("diagnosa_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("alasan_masuk")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pengaruh_ri_keuangan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bantuan_diperlukan_dalam")+"</td>"+
                                            "<td valign='top'>"+rs.getString("adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_tinggal_sendiri")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_perawatan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memiliki_nyeri_kronis")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"</td>"+
                                            "<td valign='top'>"+rs.getString("memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama_pasien_keluarga")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nip")+"</td>"+
                                            "<td valign='top'>"+rs.getString("nama")+"</td>"+
                                        "</tr>"
                                    );
                                }
                                f = new File("RencanaPemulangan.wps");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write("<html>"+
                                            "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                            "<body>"+
                                                "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    htmlContent.toString()+
                                                "</table>"+
                                            "</body>"+                   
                                         "</html>"
                                );

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break;
                        case "Laporan 3 (CSV)":
                                htmlContent = new StringBuilder();
                                htmlContent.append(                             
                                    "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Masuk Dirawat\";\"Rencana Pulang\";\"Diagnosa Medis\";\"Alasan Masuk / Dirawat\";\"Pengaruh RI Pasien & Keluarga\";\"Keterangan Pengaruh RI Pasien & Keluarga\";\"Pengaruh RI Pekerjaan/Sekolah\";\"Keterangan Pengaruh RI Pekerjaan/Sekolah\";\"Pengaruh RI Keuangan\";\"Keterangan Pengaruh RI Keuangan\";\"Antisipasi Masalah\";\"Keterangan Antisipasi Masalah\";\"Bantuan Diperlukan\";\"Keterangan Bantuan Diperlukan\";\"Membantu Keperluan\";\"Keterangan Yang Membantu Keperluan\";\"Tinggal Sendiri\";\"Keterangan Pasien Tinggal Sendiri\";\"Peralatan Medis\";\"Keterangan Peralatan Medis\";\"Alat Bantu\";\"Keterangan Memerlukan Alat Bantu\";\"Perawatan Khusus\";\"Keterangan Perawatan Khusus\";\"Memenuhi Kebutuhan\";\"Keterangan Memenuhi Kebutuhan\";\"Nyeri Kronis\";\"Keterangan Nyeri Kronis\";\"Edukasi Kesehatan\";\"Keterangan Edukasi Kesehatan\";\"Keterampilkan Khusus\";\"Keterangan Keterampilkan Khusus\";\"Pasien/Keluarga\";\"NIP\";\"Nama Petugas\"\n"
                                ); 
                                while(rs.next()){
                                    htmlContent.append(
                                        "\""+rs.getString("no_rawat")+"\";\""+rs.getString("no_rkm_medis")+"\";\""+rs.getString("nm_pasien")+"\";\""+rs.getString("tgl_lahir")+"\";\""+rs.getString("jk")+"\";\""+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"\";\""+rs.getString("rencana_pulang")+"\";\""+rs.getString("diagnosa_medis")+"\";\""+rs.getString("alasan_masuk")+"\";\""+rs.getString("pengaruh_ri_pasien_dan_keluarga")+"\";\""+rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga")+"\";\""+rs.getString("pengaruh_ri_pekerjaan_sekolah")+"\";\""+rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah")+"\";\""+rs.getString("pengaruh_ri_keuangan")+"\";\""+rs.getString("keterangan_pengaruh_ri_keuangan")+"\";\""+rs.getString("antisipasi_masalah_saat_pulang")+"\";\""+rs.getString("keterangan_antisipasi_masalah_saat_pulang")+"\";\""+rs.getString("bantuan_diperlukan_dalam")+"\";\""+rs.getString("keterangan_bantuan_diperlukan_dalam")+"\";\""+rs.getString("adakah_yang_membantu_keperluan")+"\";\""+rs.getString("keterangan_adakah_yang_membantu_keperluan")+"\";\""+rs.getString("pasien_tinggal_sendiri")+"\";\""+rs.getString("keterangan_pasien_tinggal_sendiri")+"\";\""+rs.getString("pasien_menggunakan_peralatan_medis")+"\";\""+rs.getString("keterangan_pasien_menggunakan_peralatan_medis")+"\";\""+rs.getString("pasien_memerlukan_alat_bantu")+"\";\""+rs.getString("keterangan_pasien_memerlukan_alat_bantu")+"\";\""+rs.getString("memerlukan_perawatan_khusus")+"\";\""+rs.getString("keterangan_memerlukan_perawatan_khusus")+"\";\""+rs.getString("bermasalah_memenuhi_kebutuhan")+"\";\""+rs.getString("keterangan_bermasalah_memenuhi_kebutuhan")+"\";\""+rs.getString("memiliki_nyeri_kronis")+"\";\""+rs.getString("keterangan_memiliki_nyeri_kronis")+"\";\""+rs.getString("memerlukan_edukasi_kesehatan")+"\";\""+rs.getString("keterangan_memerlukan_edukasi_kesehatan")+"\";\""+rs.getString("memerlukan_keterampilkan_khusus")+"\";\""+rs.getString("keterangan_memerlukan_keterampilkan_khusus")+"\";\""+rs.getString("nama_pasien_keluarga")+"\";\""+rs.getString("nip")+"\";\""+rs.getString("nama")+"\"\n"
                                    );
                                }
                                f = new File("RencanaPemulangan.csv");            
                                bw = new BufferedWriter(new FileWriter(f));            
                                bw.write(htmlContent.toString());

                                bw.close();                         
                                Desktop.getDesktop().browse(f.toURI());
                            break; 
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

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            //Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void RencanaPemulanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaPemulanganKeyPressed
        Valid.pindah(evt,SaksiKeluarga,DiagnosaMedis);
    }//GEN-LAST:event_RencanaPemulanganKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,KeteranganKeterampilanKhusus,SaksiKeluarga);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void SaksiKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaksiKeluargaKeyPressed
        Valid.pindah(evt,KeteranganKeterampilanKhusus,BtnSimpan);
    }//GEN-LAST:event_SaksiKeluargaKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripemulangan");
                Sequel.queryu("insert into antripemulangan values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"')");
                Sequel.queryu("delete from bukti_perencanaan_pemulangan_saksikeluarga where no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }   
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void KeteranganTinggalSendiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTinggalSendiriKeyPressed
        Valid.pindah(evt,TinggalSendiri,PeralatanMedis);
    }//GEN-LAST:event_KeteranganTinggalSendiriKeyPressed

    private void TinggalSendiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalSendiriKeyPressed
        Valid.pindah(evt,KeteranganYangMembantuKeperluan,KeteranganTinggalSendiri);
    }//GEN-LAST:event_TinggalSendiriKeyPressed

    private void DiagnosaMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMedisKeyPressed
        Valid.pindah(evt,RencanaPemulangan,AlasanMasuk);
    }//GEN-LAST:event_DiagnosaMedisKeyPressed

    private void AlasanMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanMasukKeyPressed
        Valid.pindah(evt,DiagnosaMedis,PengaruhRIKeluarga);
    }//GEN-LAST:event_AlasanMasukKeyPressed

    private void KeteranganPengaruhRIKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPengaruhRIKeluargaKeyPressed
        Valid.pindah(evt,PengaruhRIKeluarga,PengaruhRIPekerjaanSekolah);
    }//GEN-LAST:event_KeteranganPengaruhRIKeluargaKeyPressed

    private void PengaruhRIKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengaruhRIKeluargaKeyPressed
        Valid.pindah(evt,AlasanMasuk,KeteranganPengaruhRIKeluarga);
    }//GEN-LAST:event_PengaruhRIKeluargaKeyPressed

    private void PengaruhRIPekerjaanSekolahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengaruhRIPekerjaanSekolahKeyPressed
        Valid.pindah(evt,KeteranganPengaruhRIKeluarga,KeteranganPengaruhRIPekerjaanSekolah);
    }//GEN-LAST:event_PengaruhRIPekerjaanSekolahKeyPressed

    private void KeteranganPengaruhRIPekerjaanSekolahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPengaruhRIPekerjaanSekolahKeyPressed
        Valid.pindah(evt,PengaruhRIPekerjaanSekolah,PengaruhRIKeuangan);
    }//GEN-LAST:event_KeteranganPengaruhRIPekerjaanSekolahKeyPressed

    private void PengaruhRIKeuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengaruhRIKeuanganKeyPressed
        Valid.pindah(evt,KeteranganPengaruhRIPekerjaanSekolah,KeteranganPengaruhRIKeuangan);
    }//GEN-LAST:event_PengaruhRIKeuanganKeyPressed

    private void KeteranganPengaruhRIKeuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPengaruhRIKeuanganKeyPressed
        Valid.pindah(evt,PengaruhRIKeuangan,AntisipasiMasalah);
    }//GEN-LAST:event_KeteranganPengaruhRIKeuanganKeyPressed

    private void AntisipasiMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntisipasiMasalahKeyPressed
        Valid.pindah(evt,KeteranganPengaruhRIKeuangan,KeteranganAntisipasiMasalah);
    }//GEN-LAST:event_AntisipasiMasalahKeyPressed

    private void KeteranganAntisipasiMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAntisipasiMasalahKeyPressed
        Valid.pindah(evt,AntisipasiMasalah,BantuanDiperlukan);
    }//GEN-LAST:event_KeteranganAntisipasiMasalahKeyPressed

    private void BantuanDiperlukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BantuanDiperlukanKeyPressed
        Valid.pindah(evt,KeteranganAntisipasiMasalah,KeteranganBantuanDiperlukan);
    }//GEN-LAST:event_BantuanDiperlukanKeyPressed

    private void KeteranganBantuanDiperlukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBantuanDiperlukanKeyPressed
        Valid.pindah(evt,BantuanDiperlukan,YangMembantuKeperluan);
    }//GEN-LAST:event_KeteranganBantuanDiperlukanKeyPressed

    private void YangMembantuKeperluanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YangMembantuKeperluanKeyPressed
        Valid.pindah(evt,KeteranganBantuanDiperlukan,KeteranganYangMembantuKeperluan);
    }//GEN-LAST:event_YangMembantuKeperluanKeyPressed

    private void KeteranganYangMembantuKeperluanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganYangMembantuKeperluanKeyPressed
        Valid.pindah(evt,YangMembantuKeperluan,TinggalSendiri);
    }//GEN-LAST:event_KeteranganYangMembantuKeperluanKeyPressed

    private void PeralatanMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeralatanMedisKeyPressed
        Valid.pindah(evt,KeteranganTinggalSendiri,KeteranganPeralatanMedis);
    }//GEN-LAST:event_PeralatanMedisKeyPressed

    private void KeteranganPeralatanMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPeralatanMedisKeyPressed
        Valid.pindah(evt,PeralatanMedis,AlatBantu);
    }//GEN-LAST:event_KeteranganPeralatanMedisKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,KeteranganPeralatanMedis,KeteranganAlatBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KeteranganAlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlatBantuKeyPressed
        Valid.pindah(evt,AlatBantu,PerawatanKhusus);
    }//GEN-LAST:event_KeteranganAlatBantuKeyPressed

    private void PerawatanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerawatanKhususKeyPressed
        Valid.pindah(evt,KeteranganAlatBantu,KeteranganPerawatanKhusus);
    }//GEN-LAST:event_PerawatanKhususKeyPressed

    private void KeteranganPerawatanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPerawatanKhususKeyPressed
        Valid.pindah(evt,PerawatanKhusus,MemenuhiKebutuhan);
    }//GEN-LAST:event_KeteranganPerawatanKhususKeyPressed

    private void MemenuhiKebutuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MemenuhiKebutuhanKeyPressed
        Valid.pindah(evt,KeteranganPerawatanKhusus,KeteranganMemenuhiKebutuhan);
    }//GEN-LAST:event_MemenuhiKebutuhanKeyPressed

    private void KeteranganMemenuhiKebutuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMemenuhiKebutuhanKeyPressed
        Valid.pindah(evt,MemenuhiKebutuhan,NyeriKronis);
    }//GEN-LAST:event_KeteranganMemenuhiKebutuhanKeyPressed

    private void NyeriKronisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKronisKeyPressed
        Valid.pindah(evt,KeteranganMemenuhiKebutuhan,KeteranganNyeriKronis);
    }//GEN-LAST:event_NyeriKronisKeyPressed

    private void KeteranganNyeriKronisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganNyeriKronisKeyPressed
        Valid.pindah(evt,NyeriKronis,EdukasiPasien);
    }//GEN-LAST:event_KeteranganNyeriKronisKeyPressed

    private void EdukasiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiPasienKeyPressed
        Valid.pindah(evt,KeteranganNyeriKronis,KeteranganEdukasiPasien);
    }//GEN-LAST:event_EdukasiPasienKeyPressed

    private void KeteranganEdukasiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEdukasiPasienKeyPressed
        Valid.pindah(evt,EdukasiPasien,KeterampilanKhusus);
    }//GEN-LAST:event_KeteranganEdukasiPasienKeyPressed

    private void KeterampilanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeterampilanKhususKeyPressed
        Valid.pindah(evt,KeteranganEdukasiPasien,KeteranganKeterampilanKhusus);
    }//GEN-LAST:event_KeterampilanKhususKeyPressed

    private void KeteranganKeterampilanKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeterampilanKhususKeyPressed
        Valid.pindah(evt,KeterampilanKhusus,SaksiKeluarga);
    }//GEN-LAST:event_KeteranganKeterampilanKhususKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti penolakan anjuran medis terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/perencanaanpemulangan/"+lokasifile);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),39).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),38).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));
                Valid.MyReportqry("rptSuratPerencanaanPulang.jasper","report","::[ Surat Perencanaan Pulang ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.pekerjaan,reg_periksa.umurdaftar,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.sttsumur,pasien.tmp_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat_pasien,"+
		    "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
		    "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,perencanaan_pemulangan.adakah_yang_membantu_keperluan,"+
                    "perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.pasien_memerlukan_alat_bantu,"+
                    "perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.memiliki_nyeri_kronis,"+
                    "perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
		    "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis inner join petugas on petugas.nip=perencanaan_pemulangan.nip inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join propinsi on pasien.kd_prop=propinsi.kd_prop where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPerencanaanPemulangan dialog = new RMPerencanaanPemulangan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlasanMasuk;
    private widget.ComboBox AlatBantu;
    private widget.ComboBox AntisipasiMasalah;
    private widget.ComboBox BantuanDiperlukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaMedis;
    private widget.ComboBox EdukasiPasien;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KeterampilanKhusus;
    private widget.TextBox KeteranganAlatBantu;
    private widget.TextBox KeteranganAntisipasiMasalah;
    private widget.TextBox KeteranganBantuanDiperlukan;
    private widget.TextBox KeteranganEdukasiPasien;
    private widget.TextBox KeteranganKeterampilanKhusus;
    private widget.TextBox KeteranganMemenuhiKebutuhan;
    private widget.TextBox KeteranganNyeriKronis;
    private widget.TextBox KeteranganPengaruhRIKeluarga;
    private widget.TextBox KeteranganPengaruhRIKeuangan;
    private widget.TextBox KeteranganPengaruhRIPekerjaanSekolah;
    private widget.TextBox KeteranganPeralatanMedis;
    private widget.TextBox KeteranganPerawatanKhusus;
    private widget.TextBox KeteranganTinggalSendiri;
    private widget.TextBox KeteranganYangMembantuKeperluan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML2;
    private widget.TextBox MasukDirawat;
    private widget.ComboBox MemenuhiKebutuhan;
    private widget.TextBox NmPetugas;
    private widget.ComboBox NyeriKronis;
    private widget.PanelBiasa PanelAccor;
    private widget.ComboBox PengaruhRIKeluarga;
    private widget.ComboBox PengaruhRIKeuangan;
    private widget.ComboBox PengaruhRIPekerjaanSekolah;
    private widget.ComboBox PeralatanMedis;
    private widget.ComboBox PerawatanKhusus;
    private widget.Tanggal RencanaPemulangan;
    private widget.TextBox SaksiKeluarga;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalSendiri;
    private widget.ComboBox YangMembantuKeperluan;
    private widget.Button btnAmbil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                        "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                        "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                        "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                        "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                        "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                        "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                        "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                        "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                        "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                        "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                        "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                        "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                        "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                        "perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,"+
                        "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,"+
                        "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,"+
                        "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,"+
                        "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,"+
                        "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,"+
                        "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,"+
                        "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,"+
                        "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,"+
                        "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,"+
                        "perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.nip,petugas.nama "+
                        "from perencanaan_pemulangan inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join petugas on petugas.nip=perencanaan_pemulangan.nip where "+
                        "reg_periksa.tgl_registrasi between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "perencanaan_pemulangan.nip like ? or petugas.nama like ?) order by reg_periksa.tgl_registrasi");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"),
                        rs.getString("rencana_pulang"),rs.getString("diagnosa_medis"),rs.getString("alasan_masuk"),rs.getString("pengaruh_ri_pasien_dan_keluarga"),rs.getString("keterangan_pengaruh_ri_pasien_dan_keluarga"),
                        rs.getString("pengaruh_ri_pekerjaan_sekolah"),rs.getString("keterangan_pengaruh_ri_pekerjaan_sekolah"),rs.getString("pengaruh_ri_keuangan"),rs.getString("keterangan_pengaruh_ri_keuangan"),
                        rs.getString("antisipasi_masalah_saat_pulang"),rs.getString("keterangan_antisipasi_masalah_saat_pulang"),rs.getString("bantuan_diperlukan_dalam"),rs.getString("keterangan_bantuan_diperlukan_dalam"),
                        rs.getString("adakah_yang_membantu_keperluan"),rs.getString("keterangan_adakah_yang_membantu_keperluan"),rs.getString("pasien_tinggal_sendiri"),rs.getString("keterangan_pasien_tinggal_sendiri"),
                        rs.getString("pasien_menggunakan_peralatan_medis"),rs.getString("keterangan_pasien_menggunakan_peralatan_medis"),rs.getString("pasien_memerlukan_alat_bantu"),
                        rs.getString("keterangan_pasien_memerlukan_alat_bantu"),rs.getString("memerlukan_perawatan_khusus"),rs.getString("keterangan_memerlukan_perawatan_khusus"),rs.getString("bermasalah_memenuhi_kebutuhan"),
                        rs.getString("keterangan_bermasalah_memenuhi_kebutuhan"),rs.getString("memiliki_nyeri_kronis"),rs.getString("keterangan_memiliki_nyeri_kronis"),rs.getString("memerlukan_edukasi_kesehatan"),
                        rs.getString("keterangan_memerlukan_edukasi_kesehatan"),rs.getString("memerlukan_keterampilkan_khusus"),rs.getString("keterangan_memerlukan_keterampilkan_khusus"),rs.getString("nama_pasien_keluarga"),
                        rs.getString("nip"),rs.getString("nama")             
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
        TinggalSendiri.setSelectedIndex(0);
        RencanaPemulangan.setDate(new Date());
        DiagnosaMedis.setText("");
        AlasanMasuk.setText("");
        PengaruhRIKeluarga.setSelectedIndex(0);
        KeteranganPengaruhRIKeluarga.setText("");
        PengaruhRIPekerjaanSekolah.setSelectedIndex(0);
        KeteranganPengaruhRIPekerjaanSekolah.setText("");
        PengaruhRIKeuangan.setSelectedIndex(0);
        KeteranganPengaruhRIKeuangan.setText("");
        AntisipasiMasalah.setSelectedIndex(0);
        KeteranganAntisipasiMasalah.setText("");
        BantuanDiperlukan.setSelectedIndex(0);
        KeteranganBantuanDiperlukan.setText("");
        YangMembantuKeperluan.setSelectedIndex(0);
        KeteranganYangMembantuKeperluan.setText("");
        TinggalSendiri.setSelectedIndex(0);
        KeteranganTinggalSendiri.setText("");
        PeralatanMedis.setSelectedIndex(0);
        KeteranganPeralatanMedis.setText("");
        AlatBantu.setSelectedIndex(0);
        KeteranganAlatBantu.setText("");
        PerawatanKhusus.setSelectedIndex(0);
        KeteranganPerawatanKhusus.setText("");
        MemenuhiKebutuhan.setSelectedIndex(0);
        KeteranganMemenuhiKebutuhan.setText("");
        NyeriKronis.setSelectedIndex(0);
        KeteranganNyeriKronis.setText("");
        EdukasiPasien.setSelectedIndex(0);
        KeteranganEdukasiPasien.setText("");
        KeterampilanKhusus.setSelectedIndex(0);
        KeteranganKeterampilanKhusus.setText("");
        TabRawat.setSelectedIndex(0);
        SaksiKeluarga.setText("");
        TabRawat.setSelectedIndex(0);
        DiagnosaMedis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan")); 
            MasukDirawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            DiagnosaMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            AlasanMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            PengaruhRIKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KeteranganPengaruhRIKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            PengaruhRIPekerjaanSekolah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KeteranganPengaruhRIPekerjaanSekolah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            PengaruhRIKeuangan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KeteranganPengaruhRIKeuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            AntisipasiMasalah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KeteranganAntisipasiMasalah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            BantuanDiperlukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeteranganBantuanDiperlukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            YangMembantuKeperluan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KeteranganYangMembantuKeperluan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TinggalSendiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganTinggalSendiri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            PeralatanMedis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KeteranganPeralatanMedis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KeteranganAlatBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            PerawatanKhusus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KeteranganPerawatanKhusus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            MemenuhiKebutuhan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KeteranganMemenuhiKebutuhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NyeriKronis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KeteranganNyeriKronis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            EdukasiPasien.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KeteranganEdukasiPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KeterampilanKhusus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            KeteranganKeterampilanKhusus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            SaksiKeluarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Valid.SetTgl(RencanaPemulangan,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,"+
                    "pasien.no_tlp,pasien.umur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
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
                    KeteranganTinggalSendiri.setText(rs.getString("asal"));
                    MasukDirawat.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
        BtnSimpan.setEnabled(akses.getperencanaan_pemulangan());
        BtnHapus.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        BtnEdit.setEnabled(akses.getperencanaan_pemulangan());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from perencanaan_pemulangan where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("perencanaan_pemulangan","no_rawat=?","no_rawat=?,rencana_pulang=?,alasan_masuk=?,diagnosa_medis=?,pengaruh_ri_pasien_dan_keluarga=?,keterangan_pengaruh_ri_pasien_dan_keluarga=?,"+
                "pengaruh_ri_pekerjaan_sekolah=?,keterangan_pengaruh_ri_pekerjaan_sekolah=?,pengaruh_ri_keuangan=?,keterangan_pengaruh_ri_keuangan=?,antisipasi_masalah_saat_pulang=?,"+
                "keterangan_antisipasi_masalah_saat_pulang=?,bantuan_diperlukan_dalam=?,keterangan_bantuan_diperlukan_dalam=?,adakah_yang_membantu_keperluan=?,keterangan_adakah_yang_membantu_keperluan=?,"+
                "pasien_tinggal_sendiri=?,keterangan_pasien_tinggal_sendiri=?,pasien_menggunakan_peralatan_medis=?,keterangan_pasien_menggunakan_peralatan_medis=?,pasien_memerlukan_alat_bantu=?,"+
                "keterangan_pasien_memerlukan_alat_bantu=?,memerlukan_perawatan_khusus=?,keterangan_memerlukan_perawatan_khusus=?,bermasalah_memenuhi_kebutuhan=?,keterangan_bermasalah_memenuhi_kebutuhan=?,"+
                "memiliki_nyeri_kronis=?,keterangan_memiliki_nyeri_kronis=?,memerlukan_edukasi_kesehatan=?,keterangan_memerlukan_edukasi_kesehatan=?,memerlukan_keterampilkan_khusus=?,"+
                "keterangan_memerlukan_keterampilkan_khusus=?,nama_pasien_keluarga=?,nip=?",35,new String[]{
                TNoRw.getText(),Valid.SetTgl(RencanaPemulangan.getSelectedItem()+""),AlasanMasuk.getText(),DiagnosaMedis.getText(),PengaruhRIKeluarga.getSelectedItem().toString(),
                KeteranganPengaruhRIKeluarga.getText(),PengaruhRIPekerjaanSekolah.getSelectedItem().toString(),KeteranganPengaruhRIPekerjaanSekolah.getText(),
                PengaruhRIKeuangan.getSelectedItem().toString(),KeteranganPengaruhRIKeuangan.getText(),AntisipasiMasalah.getSelectedItem().toString(),KeteranganAntisipasiMasalah.getText(),
                BantuanDiperlukan.getSelectedItem().toString(),KeteranganBantuanDiperlukan.getText(),YangMembantuKeperluan.getSelectedItem().toString(),KeteranganYangMembantuKeperluan.getText(),
                TinggalSendiri.getSelectedItem().toString(),KeteranganTinggalSendiri.getText(),PeralatanMedis.getSelectedItem().toString(),KeteranganPeralatanMedis.getText(),
                AlatBantu.getSelectedItem().toString(),KeteranganAlatBantu.getText(),PerawatanKhusus.getSelectedItem().toString(),KeteranganPerawatanKhusus.getText(),
                MemenuhiKebutuhan.getSelectedItem().toString(),KeteranganMemenuhiKebutuhan.getText(),NyeriKronis.getSelectedItem().toString(),KeteranganNyeriKronis.getText(),
                EdukasiPasien.getSelectedItem().toString(),KeteranganEdukasiPasien.getText(),KeterampilanKhusus.getSelectedItem().toString(),KeteranganKeterampilanKhusus.getText(),
                SaksiKeluarga.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(430,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select bukti_perencanaan_pemulangan_saksikeluarga.photo from bukti_perencanaan_pemulangan_saksikeluarga where bukti_perencanaan_pemulangan_saksikeluarga.no_rawat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            lokasifile=rs.getString("photo");
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/perencanaanpemulangan/"+rs.getString("photo")+"' alt='photo' width='450' height='500'/></center></body></html>");
                        }  
                    }else{
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    lokasifile="";
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
}

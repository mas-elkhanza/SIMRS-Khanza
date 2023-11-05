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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianTerapiWicara extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger=""; 
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianTerapiWicara(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Tanggal","Informasi","Keluhan Utama","Riwayat Peyakit Sekarang","Riwayat Penyakit Dahulu & Penyerta", 
            "TD","HR","RR","Suhu","Nyeri Tekan","Nyeri Gerak","Nyeri Diam","Palpasi","Luas Gerak Sendi","Kekuatan Otot","Statis","Dinamis","Kognitif","Auskultasi",
            "Alat Bantu","Ket Alat Bantu","Prothesa","Keteranga Prothesa","Deformitas","Keterangan Deformitas","Resiko Jatuh","Keterangan Resiko Jatuh","ADL","Fungsional Lain",
            "Keterangan Fisik","Pemeriksaan Musculoskeletal","Pemeriksaan Neuromuscular","Pemeriksaan Cardiopulmonal","Pemeriksaan Integument","Pengukuran Musculoskeletal", 
            "Pengukuran Neuromuscular","Pengukuran Cardiopulmonal","Pengukuran Integument","Pemeriksaan Penunjang", "Diagnosis Fisio", "Rencana Intervensi Fisioterapi",
            "NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 48; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(270);
            }else if(i==8){
                column.setPreferredWidth(184);
            }else if(i==9){
                column.setPreferredWidth(184);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(30);
            }else if(i==12){
                column.setPreferredWidth(30);
            }else if(i==13){
                column.setPreferredWidth(35);
            }else if(i==14){
                column.setPreferredWidth(70);
            }else if(i==15){
                column.setPreferredWidth(70);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(130);
            }else if(i==18){
                column.setPreferredWidth(130);
            }else if(i==19){
                column.setPreferredWidth(130);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(130);
            }else if(i==22){
                column.setPreferredWidth(130);
            }else if(i==23){
                column.setPreferredWidth(130);
            }else if(i==24){
                column.setPreferredWidth(60);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(53);
            }else if(i==27){
                column.setPreferredWidth(105);
            }else if(i==28){
                column.setPreferredWidth(62);
            }else if(i==29){
                column.setPreferredWidth(120);
            }else if(i==30){
                column.setPreferredWidth(70);
            }else if(i==31){
                column.setPreferredWidth(130);
            }else if(i==32){
                column.setPreferredWidth(55);
            }else if(i==33){
                column.setPreferredWidth(120);
            }else if(i==34){
                column.setPreferredWidth(270);
            }else if(i==35){
                column.setPreferredWidth(160);
            }else if(i==36){
                column.setPreferredWidth(160);
            }else if(i==37){
                column.setPreferredWidth(160);
            }else if(i==38){
                column.setPreferredWidth(160);
            }else if(i==39){
                column.setPreferredWidth(160);
            }else if(i==40){
                column.setPreferredWidth(160);
            }else if(i==41){
                column.setPreferredWidth(160);
            }else if(i==42){
                column.setPreferredWidth(160);
            }else if(i==43){
                column.setPreferredWidth(200);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(200);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KeluhanUtama.setDocument(new batasInput((int)150).getKata(KeluhanUtama));
        RiwayatPenyakitSekarang.setDocument(new batasInput((int)100).getKata(RiwayatPenyakitSekarang));
        RiwayatPenyakitDahulu.setDocument(new batasInput((int)100).getKata(RiwayatPenyakitDahulu));
        
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
        MnPenilaianFisio = new javax.swing.JMenuItem();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakitSekarang = new widget.TextArea();
        jLabel31 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RiwayatPenyakitDahulu = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        KeluhanUtama1 = new widget.TextArea();
        jLabel32 = new widget.Label();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel29 = new widget.Label();
        Nyeri = new widget.TextBox();
        jLabel35 = new widget.Label();
        Nyeri1 = new widget.TextBox();
        jLabel36 = new widget.Label();
        Nyeri2 = new widget.TextBox();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        Nyeri3 = new widget.TextBox();
        jLabel39 = new widget.Label();
        Nyeri4 = new widget.TextBox();
        jLabel40 = new widget.Label();
        Nyeri5 = new widget.TextBox();
        jLabel41 = new widget.Label();
        Nyeri6 = new widget.TextBox();
        jLabel42 = new widget.Label();
        Nyeri7 = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        Nyeri8 = new widget.TextBox();
        jLabel46 = new widget.Label();
        Nyeri9 = new widget.TextBox();
        jLabel47 = new widget.Label();
        Nyeri10 = new widget.TextBox();
        jLabel48 = new widget.Label();
        Nyeri11 = new widget.TextBox();
        Nyeri12 = new widget.TextBox();
        jLabel49 = new widget.Label();
        Nyeri13 = new widget.TextBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        Nyeri14 = new widget.TextBox();
        Nyeri15 = new widget.TextBox();
        jLabel52 = new widget.Label();
        Nyeri16 = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        Nyeri17 = new widget.TextBox();
        jLabel56 = new widget.Label();
        Nyeri18 = new widget.TextBox();
        jLabel57 = new widget.Label();
        Nyeri19 = new widget.TextBox();
        jLabel58 = new widget.Label();
        Nyeri20 = new widget.TextBox();
        jLabel59 = new widget.Label();
        Nyeri21 = new widget.TextBox();
        jLabel60 = new widget.Label();
        Nyeri22 = new widget.TextBox();
        jLabel61 = new widget.Label();
        Nyeri23 = new widget.TextBox();
        jLabel62 = new widget.Label();
        Nyeri24 = new widget.TextBox();
        jLabel63 = new widget.Label();
        Nyeri25 = new widget.TextBox();
        jLabel64 = new widget.Label();
        jLabel12 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        RiwayatPenyakitDahulu1 = new widget.TextArea();
        scrollPane6 = new widget.ScrollPane();
        RiwayatPenyakitDahulu2 = new widget.TextArea();
        jLabel13 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RiwayatPenyakitDahulu3 = new widget.TextArea();
        scrollPane8 = new widget.ScrollPane();
        RiwayatPenyakitDahulu4 = new widget.TextArea();
        jLabel24 = new widget.Label();
        jLabel27 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        RiwayatPenyakitDahulu5 = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        RiwayatPenyakitDahulu6 = new widget.TextArea();
        jLabel67 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        jLabel66 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        Anamnesis1 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        Anamnesis2 = new widget.ComboBox();
        Anamnesis3 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        Anamnesis4 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        scrollPane11 = new widget.ScrollPane();
        RiwayatPenyakitSekarang1 = new widget.TextArea();
        jLabel75 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        RiwayatPenyakitSekarang2 = new widget.TextArea();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        Nyeri26 = new widget.TextBox();
        jLabel79 = new widget.Label();
        Nyeri27 = new widget.TextBox();
        jLabel80 = new widget.Label();
        Nyeri28 = new widget.TextBox();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        Nyeri29 = new widget.TextBox();
        jLabel83 = new widget.Label();
        Nyeri30 = new widget.TextBox();
        Nyeri31 = new widget.TextBox();
        jLabel84 = new widget.Label();
        Nyeri32 = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        RiwayatPenyakitSekarang3 = new widget.TextArea();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        KeluhanUtama2 = new widget.TextArea();
        jLabel89 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        KeluhanUtama3 = new widget.TextArea();
        jLabel90 = new widget.Label();
        scrollPane16 = new widget.ScrollPane();
        RiwayatPenyakitSekarang4 = new widget.TextArea();
        jLabel91 = new widget.Label();
        scrollPane17 = new widget.ScrollPane();
        RiwayatPenyakitSekarang5 = new widget.TextArea();
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

        MnPenilaianFisio.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianFisio.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianFisio.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianFisio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianFisio.setText("Laporan Penilaian Fisioterapi");
        MnPenilaianFisio.setName("MnPenilaianFisio"); // NOI18N
        MnPenilaianFisio.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianFisio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianFisioActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianFisio);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Fisioterapi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1693));
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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 130, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(207, 40, 331, 23);

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
        BtnDokter.setBounds(541, 40, 28, 23);

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

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(663, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2023 06:29:34" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(724, 40, 130, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Menghisap :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(80, 730, 200, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(20, 100, 400, 43);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Diagnosa Terapi Wicara :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(20, 80, 140, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakitSekarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang.setColumns(20);
        RiwayatPenyakitSekarang.setRows(5);
        RiwayatPenyakitSekarang.setName("RiwayatPenyakitSekarang"); // NOI18N
        RiwayatPenyakitSekarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarangKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RiwayatPenyakitSekarang);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(20, 170, 834, 53);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Anamnesa :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(20, 150, 182, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RiwayatPenyakitDahulu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu.setColumns(20);
        RiwayatPenyakitDahulu.setRows(5);
        RiwayatPenyakitDahulu.setName("RiwayatPenyakitDahulu"); // NOI18N
        RiwayatPenyakitDahulu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahuluKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RiwayatPenyakitDahulu);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(80, 750, 245, 63);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        KeluhanUtama1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama1.setColumns(20);
        KeluhanUtama1.setRows(5);
        KeluhanUtama1.setName("KeluhanUtama1"); // NOI18N
        KeluhanUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtama1KeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(KeluhanUtama1);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(454, 100, 400, 43);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Diagnosa Medis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(454, 80, 140, 23);

        jLabel22.setText("Tensi Darah :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 250, 114, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(118, 250, 86, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(207, 250, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(308, 250, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(352, 250, 65, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(420, 250, 30, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(498, 250, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(542, 250, 65, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(610, 250, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(678, 250, 65, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(747, 250, 65, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(815, 250, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Tanda-tanda Vital :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(20, 230, 182, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Perilaku Adaptif :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(20, 280, 182, 23);

        jLabel29.setText("Kontak Mata :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 300, 114, 23);

        Nyeri.setFocusTraversalPolicyProvider(true);
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(118, 300, 325, 23);

        jLabel35.setText("Perilaku :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 330, 114, 23);

        Nyeri1.setFocusTraversalPolicyProvider(true);
        Nyeri1.setName("Nyeri1"); // NOI18N
        Nyeri1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri1KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri1);
        Nyeri1.setBounds(118, 330, 325, 23);

        jLabel36.setText("Atensi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(445, 300, 80, 23);

        Nyeri2.setFocusTraversalPolicyProvider(true);
        Nyeri2.setName("Nyeri2"); // NOI18N
        Nyeri2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri2KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri2);
        Nyeri2.setBounds(529, 300, 325, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Kemampuan Bahasa :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(20, 360, 182, 23);

        jLabel38.setText("Pengujaran :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 380, 114, 23);

        Nyeri3.setFocusTraversalPolicyProvider(true);
        Nyeri3.setName("Nyeri3"); // NOI18N
        Nyeri3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri3KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri3);
        Nyeri3.setBounds(118, 380, 300, 23);

        jLabel39.setText("Bicara Spontan :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(430, 380, 120, 23);

        Nyeri4.setFocusTraversalPolicyProvider(true);
        Nyeri4.setName("Nyeri4"); // NOI18N
        Nyeri4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri4KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri4);
        Nyeri4.setBounds(554, 380, 300, 23);

        jLabel40.setText("Membaca :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 410, 114, 23);

        Nyeri5.setFocusTraversalPolicyProvider(true);
        Nyeri5.setName("Nyeri5"); // NOI18N
        Nyeri5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri5KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri5);
        Nyeri5.setBounds(118, 410, 300, 23);

        jLabel41.setText("Pemahaman Bahasa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(430, 410, 120, 23);

        Nyeri6.setFocusTraversalPolicyProvider(true);
        Nyeri6.setName("Nyeri6"); // NOI18N
        Nyeri6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri6KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri6);
        Nyeri6.setBounds(554, 410, 300, 23);

        jLabel42.setText("Penamaan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 440, 114, 23);

        Nyeri7.setFocusTraversalPolicyProvider(true);
        Nyeri7.setName("Nyeri7"); // NOI18N
        Nyeri7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri7KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri7);
        Nyeri7.setBounds(118, 440, 300, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Wicara :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(20, 470, 182, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Organ Wicara Anatomis :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(50, 490, 182, 23);

        jLabel45.setText("Lip :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 510, 142, 23);

        Nyeri8.setFocusTraversalPolicyProvider(true);
        Nyeri8.setName("Nyeri8"); // NOI18N
        Nyeri8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri8KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri8);
        Nyeri8.setBounds(146, 510, 180, 23);

        jLabel46.setText("Tongue :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(326, 510, 70, 23);

        Nyeri9.setFocusTraversalPolicyProvider(true);
        Nyeri9.setName("Nyeri9"); // NOI18N
        Nyeri9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri9KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri9);
        Nyeri9.setBounds(400, 510, 180, 23);

        jLabel47.setText("Hard Palate :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(580, 510, 90, 23);

        Nyeri10.setFocusTraversalPolicyProvider(true);
        Nyeri10.setName("Nyeri10"); // NOI18N
        Nyeri10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri10KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri10);
        Nyeri10.setBounds(674, 510, 180, 23);

        jLabel48.setText("Mandibula :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(580, 540, 90, 23);

        Nyeri11.setFocusTraversalPolicyProvider(true);
        Nyeri11.setName("Nyeri11"); // NOI18N
        Nyeri11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri11KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri11);
        Nyeri11.setBounds(674, 540, 180, 23);

        Nyeri12.setFocusTraversalPolicyProvider(true);
        Nyeri12.setName("Nyeri12"); // NOI18N
        Nyeri12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri12KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri12);
        Nyeri12.setBounds(400, 540, 180, 23);

        jLabel49.setText("Uvula :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(326, 540, 70, 23);

        Nyeri13.setFocusTraversalPolicyProvider(true);
        Nyeri13.setName("Nyeri13"); // NOI18N
        Nyeri13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri13KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri13);
        Nyeri13.setBounds(146, 540, 180, 23);

        jLabel50.setText("Soft Palate :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 540, 142, 23);

        jLabel51.setText("Faring :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(580, 570, 90, 23);

        Nyeri14.setFocusTraversalPolicyProvider(true);
        Nyeri14.setName("Nyeri14"); // NOI18N
        Nyeri14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri14KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri14);
        Nyeri14.setBounds(674, 570, 180, 23);

        Nyeri15.setFocusTraversalPolicyProvider(true);
        Nyeri15.setName("Nyeri15"); // NOI18N
        Nyeri15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri15KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri15);
        Nyeri15.setBounds(400, 570, 180, 23);

        jLabel52.setText("Dental :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(326, 570, 70, 23);

        Nyeri16.setFocusTraversalPolicyProvider(true);
        Nyeri16.setName("Nyeri16"); // NOI18N
        Nyeri16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri16KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri16);
        Nyeri16.setBounds(146, 570, 180, 23);

        jLabel53.setText("Maxilla :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 570, 142, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Organ Wicara Fisiologis :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(50, 600, 182, 23);

        jLabel55.setText("Lip :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 620, 142, 23);

        Nyeri17.setFocusTraversalPolicyProvider(true);
        Nyeri17.setName("Nyeri17"); // NOI18N
        Nyeri17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri17KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri17);
        Nyeri17.setBounds(146, 620, 180, 23);

        jLabel56.setText("Tongue :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(326, 620, 70, 23);

        Nyeri18.setFocusTraversalPolicyProvider(true);
        Nyeri18.setName("Nyeri18"); // NOI18N
        Nyeri18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri18KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri18);
        Nyeri18.setBounds(400, 620, 180, 23);

        jLabel57.setText("Hard Palate :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(580, 620, 90, 23);

        Nyeri19.setFocusTraversalPolicyProvider(true);
        Nyeri19.setName("Nyeri19"); // NOI18N
        Nyeri19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri19KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri19);
        Nyeri19.setBounds(674, 620, 180, 23);

        jLabel58.setText("Soft Palate :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 650, 142, 23);

        Nyeri20.setFocusTraversalPolicyProvider(true);
        Nyeri20.setName("Nyeri20"); // NOI18N
        Nyeri20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri20KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri20);
        Nyeri20.setBounds(146, 650, 180, 23);

        jLabel59.setText("Uvula :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(326, 650, 70, 23);

        Nyeri21.setFocusTraversalPolicyProvider(true);
        Nyeri21.setName("Nyeri21"); // NOI18N
        Nyeri21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri21KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri21);
        Nyeri21.setBounds(400, 650, 180, 23);

        jLabel60.setText("Mandibula :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(580, 650, 90, 23);

        Nyeri22.setFocusTraversalPolicyProvider(true);
        Nyeri22.setName("Nyeri22"); // NOI18N
        Nyeri22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri22KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri22);
        Nyeri22.setBounds(674, 650, 180, 23);

        jLabel61.setText("Maxilla :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 680, 142, 23);

        Nyeri23.setFocusTraversalPolicyProvider(true);
        Nyeri23.setName("Nyeri23"); // NOI18N
        Nyeri23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri23KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri23);
        Nyeri23.setBounds(146, 680, 180, 23);

        jLabel62.setText("Dental :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(326, 680, 70, 23);

        Nyeri24.setFocusTraversalPolicyProvider(true);
        Nyeri24.setName("Nyeri24"); // NOI18N
        Nyeri24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri24KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri24);
        Nyeri24.setBounds(400, 680, 180, 23);

        jLabel63.setText("Faring :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(580, 680, 90, 23);

        Nyeri25.setFocusTraversalPolicyProvider(true);
        Nyeri25.setName("Nyeri25"); // NOI18N
        Nyeri25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri25KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri25);
        Nyeri25.setBounds(674, 680, 180, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Aktifitas Oral :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(50, 710, 182, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Mengunyah :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(345, 730, 200, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        RiwayatPenyakitDahulu1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu1.setColumns(20);
        RiwayatPenyakitDahulu1.setRows(5);
        RiwayatPenyakitDahulu1.setName("RiwayatPenyakitDahulu1"); // NOI18N
        RiwayatPenyakitDahulu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu1KeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(RiwayatPenyakitDahulu1);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(345, 750, 245, 63);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        RiwayatPenyakitDahulu2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu2.setColumns(20);
        RiwayatPenyakitDahulu2.setRows(5);
        RiwayatPenyakitDahulu2.setName("RiwayatPenyakitDahulu2"); // NOI18N
        RiwayatPenyakitDahulu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu2KeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(RiwayatPenyakitDahulu2);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(609, 750, 245, 63);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Meniup :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(609, 730, 200, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Kemampuan Artikulasi :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(50, 820, 182, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Subtitusi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(80, 840, 200, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Omisi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(479, 840, 200, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RiwayatPenyakitDahulu3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu3.setColumns(20);
        RiwayatPenyakitDahulu3.setRows(5);
        RiwayatPenyakitDahulu3.setName("RiwayatPenyakitDahulu3"); // NOI18N
        RiwayatPenyakitDahulu3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu3KeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RiwayatPenyakitDahulu3);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(80, 860, 375, 43);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        RiwayatPenyakitDahulu4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu4.setColumns(20);
        RiwayatPenyakitDahulu4.setRows(5);
        RiwayatPenyakitDahulu4.setName("RiwayatPenyakitDahulu4"); // NOI18N
        RiwayatPenyakitDahulu4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu4KeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(RiwayatPenyakitDahulu4);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(479, 860, 375, 43);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Distorsi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(80, 910, 200, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Adisi :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(479, 910, 200, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        RiwayatPenyakitDahulu5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu5.setColumns(20);
        RiwayatPenyakitDahulu5.setRows(5);
        RiwayatPenyakitDahulu5.setName("RiwayatPenyakitDahulu5"); // NOI18N
        RiwayatPenyakitDahulu5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu5KeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(RiwayatPenyakitDahulu5);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(80, 930, 375, 43);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        RiwayatPenyakitDahulu6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitDahulu6.setColumns(20);
        RiwayatPenyakitDahulu6.setRows(5);
        RiwayatPenyakitDahulu6.setName("RiwayatPenyakitDahulu6"); // NOI18N
        RiwayatPenyakitDahulu6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitDahulu6KeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(RiwayatPenyakitDahulu6);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(479, 930, 375, 43);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 980, 107, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hiponasal", "Hipernasal", "Normal" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(111, 980, 110, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Resonansi :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(50, 980, 70, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Kemampuan Suara :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(20, 1010, 182, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Nada");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(50, 1030, 40, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 1030, 82, 23);

        Anamnesis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Resonansi", "Rendah", "Monoton", "Normal" }));
        Anamnesis1.setName("Anamnesis1"); // NOI18N
        Anamnesis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis1KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis1);
        Anamnesis1.setBounds(86, 1030, 105, 23);

        jLabel71.setText("Kualitas :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(190, 1030, 60, 23);

        Anamnesis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hoarssness", "Hassness", "Normal" }));
        Anamnesis2.setName("Anamnesis2"); // NOI18N
        Anamnesis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis2KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis2);
        Anamnesis2.setBounds(254, 1030, 110, 23);

        Anamnesis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gagap Primer", "Gagap Sekunder" }));
        Anamnesis3.setName("Anamnesis3"); // NOI18N
        Anamnesis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis3KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis3);
        Anamnesis3.setBounds(724, 1010, 130, 23);

        jLabel72.setText("Kenyaringan :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(362, 1030, 80, 23);

        jLabel73.setText("Kemampuan Irama Kelancaran :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(520, 1010, 200, 23);

        Anamnesis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nyaring", "Tidak Nyaring" }));
        Anamnesis4.setName("Anamnesis4"); // NOI18N
        Anamnesis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis4KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis4);
        Anamnesis4.setBounds(446, 1030, 120, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Kemampuan Menelan :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(20, 1060, 182, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        RiwayatPenyakitSekarang1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang1.setColumns(20);
        RiwayatPenyakitSekarang1.setRows(5);
        RiwayatPenyakitSekarang1.setName("RiwayatPenyakitSekarang1"); // NOI18N
        RiwayatPenyakitSekarang1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarang1KeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(RiwayatPenyakitSekarang1);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(20, 1080, 834, 43);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Pernapasan :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(20, 1130, 182, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        RiwayatPenyakitSekarang2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang2.setColumns(20);
        RiwayatPenyakitSekarang2.setRows(5);
        RiwayatPenyakitSekarang2.setName("RiwayatPenyakitSekarang2"); // NOI18N
        RiwayatPenyakitSekarang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarang2KeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(RiwayatPenyakitSekarang2);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(20, 1150, 834, 43);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Tingkat Komunikasi :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(20, 1200, 182, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Dekoding :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(50, 1220, 182, 23);

        jLabel78.setText("S1 : Pendengaran");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 1240, 166, 23);

        Nyeri26.setFocusTraversalPolicyProvider(true);
        Nyeri26.setName("Nyeri26"); // NOI18N
        Nyeri26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri26KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri26);
        Nyeri26.setBounds(170, 1240, 290, 23);

        jLabel79.setText("S2 : Penglihatan");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(470, 1240, 90, 23);

        Nyeri27.setFocusTraversalPolicyProvider(true);
        Nyeri27.setName("Nyeri27"); // NOI18N
        Nyeri27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri27KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri27);
        Nyeri27.setBounds(564, 1240, 290, 23);

        jLabel80.setText("S3 : Tak l Kinesek");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 1270, 166, 23);

        Nyeri28.setFocusTraversalPolicyProvider(true);
        Nyeri28.setName("Nyeri28"); // NOI18N
        Nyeri28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri28KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri28);
        Nyeri28.setBounds(170, 1270, 290, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Enkoding :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(50, 1300, 182, 23);

        jLabel82.setText("T1 : Bicara");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 1320, 136, 23);

        Nyeri29.setFocusTraversalPolicyProvider(true);
        Nyeri29.setName("Nyeri29"); // NOI18N
        Nyeri29.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri29KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri29);
        Nyeri29.setBounds(140, 1320, 310, 23);

        jLabel83.setText("T2 : Tulisan");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(450, 1320, 90, 23);

        Nyeri30.setFocusTraversalPolicyProvider(true);
        Nyeri30.setName("Nyeri30"); // NOI18N
        Nyeri30.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri30KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri30);
        Nyeri30.setBounds(544, 1320, 310, 23);

        Nyeri31.setFocusTraversalPolicyProvider(true);
        Nyeri31.setName("Nyeri31"); // NOI18N
        Nyeri31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri31KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri31);
        Nyeri31.setBounds(544, 1350, 310, 23);

        jLabel84.setText("T4 : Gesture");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(450, 1350, 90, 23);

        Nyeri32.setFocusTraversalPolicyProvider(true);
        Nyeri32.setName("Nyeri32"); // NOI18N
        Nyeri32.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Nyeri32KeyPressed(evt);
            }
        });
        FormInput.add(Nyeri32);
        Nyeri32.setBounds(140, 1350, 310, 23);

        jLabel85.setText("T3 : Mimik");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 1350, 136, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Penunjang Medis :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(20, 1380, 182, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        RiwayatPenyakitSekarang3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang3.setColumns(20);
        RiwayatPenyakitSekarang3.setRows(5);
        RiwayatPenyakitSekarang3.setName("RiwayatPenyakitSekarang3"); // NOI18N
        RiwayatPenyakitSekarang3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarang3KeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(RiwayatPenyakitSekarang3);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(20, 1400, 834, 43);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Perencanaan Terapi Wicara :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(20, 1450, 182, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Tujuan Terapi Wicara :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(50, 1470, 140, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        KeluhanUtama2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama2.setColumns(20);
        KeluhanUtama2.setRows(5);
        KeluhanUtama2.setName("KeluhanUtama2"); // NOI18N
        KeluhanUtama2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtama2KeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(KeluhanUtama2);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(50, 1490, 390, 53);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Program Terapi Wicara :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(464, 1470, 140, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        KeluhanUtama3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama3.setColumns(20);
        KeluhanUtama3.setRows(5);
        KeluhanUtama3.setName("KeluhanUtama3"); // NOI18N
        KeluhanUtama3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtama3KeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(KeluhanUtama3);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(464, 1490, 390, 53);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Edukasi :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(20, 1550, 182, 23);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        RiwayatPenyakitSekarang4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang4.setColumns(20);
        RiwayatPenyakitSekarang4.setRows(5);
        RiwayatPenyakitSekarang4.setName("RiwayatPenyakitSekarang4"); // NOI18N
        RiwayatPenyakitSekarang4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarang4KeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(RiwayatPenyakitSekarang4);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(20, 1570, 834, 43);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Tidak Lanjut :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(20, 1620, 182, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        RiwayatPenyakitSekarang5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSekarang5.setColumns(20);
        RiwayatPenyakitSekarang5.setRows(5);
        RiwayatPenyakitSekarang5.setName("RiwayatPenyakitSekarang5"); // NOI18N
        RiwayatPenyakitSekarang5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSekarang5KeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(RiwayatPenyakitSekarang5);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(20, 1640, 834, 43);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2023" }));
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
       /* if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RiwayatPenyakitSekarang.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakitSekarang,"Riwayat Penyakit Sekarang");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
           if(Sequel.menyimpantf("penilaian_fisioterapi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",43,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),
                    RiwayatPenyakitSekarang.getText(),RiwayatPenyakitDahulu.getText(),TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),NyeriTekan.getText(),NyeriGerak.getText(),NyeriDiam.getText(),
                    Palpasi.getText(),LuasGerakSendi.getText(),KekuatanOtot.getText(),Statis.getText(),Dinamis.getText(),Kognitif.getText(),Auskultasi.getText(),AlatBantu.getSelectedItem().toString(),
                    KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),Deformitas.getSelectedItem().toString(),KetDeformitas.getText(),ResikoJatuh.getSelectedItem().toString(),
                    KetResikoJatuh.getText(),ADL.getSelectedItem().toString(),LainlainFungsioal.getText(),KetFisik.getText(),PemeriksaanMuscu.getText(),PemeriksaanNeuro.getText(),PemeriksaanCardio.getText(),
                    PemeriksaanInte.getText(),PengukuranMuscu.getText(),PengukuranNeuro.getText(),PengukuranCardio.getText(),PengukuranInte.getText(),Penunjang.getText(),Diagnosis.getText(),Rencana.getText(),
                    KdPetugas.getText()
                })==true){
                    emptTeks();
            }
        }*/
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Rencana,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString())){
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
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                            "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                            "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                            "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                            "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                            "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                            "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                            "inner join petugas on penilaian_fisioterapi.nip=petugas.nip where "+
                            "penilaian_fisioterapi.tanggal between ? and ? order by penilaian_fisioterapi.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                            "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                            "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                            "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                            "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                            "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                            "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                            "inner join petugas on penilaian_fisioterapi.nip=petugas.nip where "+
                            "penilaian_fisioterapi.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                            "penilaian_fisioterapi.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                            "penilaian_fisioterapi.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                            "penilaian_fisioterapi.tanggal between ? and ? and penilaian_fisioterapi.nip like ? or "+
                            "penilaian_fisioterapi.tanggal between ? and ? and petugas.nama like ? order by penilaian_fisioterapi.tanggal");
                }

                try {
                    if(TCari.getText().equals("")){
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Informasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='270px'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='184px'><b>Riwayat Peyakit Sekarang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='184px'><b>Riwayat Penyakit Dahulu & Penyerta</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>TD</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30px'><b>HR</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30px'><b>RR</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35px'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Tekan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Gerak</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Nyeri Diam</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Palpasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Luas Gerak Sendi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Kekuatan Otot</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Statis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Dinamis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Kognitif</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Auskultasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>Alat Bantu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Ket Alat Bantu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='53px'><b>Prothesa</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>Keteranga Prothesa</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='62px'><b>Deformitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Keterangan Deformitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>Resiko Jatuh</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='130px'><b>Keterangan Resiko Jatuh</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>ADL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Fungsional Lain</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='270px'><b>Keterangan Fisik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Musculoskeletal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Neuromuscular</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Cardiopulmonal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pemeriksaan Integument</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Musculoskeletal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Neuromuscular</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Cardiopulmonal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='160px'><b>Pengukuran Integument</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Pemeriksaan Penunjang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis Fisio</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Rencana Intervensi Fisioterapi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Petugas</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("informasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("rps")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("hr")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("nyeri_tekan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nyeri_gerak")+"</td>"+
                               "<td valign='top'>"+rs.getString("nyeri_diam")+"</td>"+
                               "<td valign='top'>"+rs.getString("palpasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("luas_gerak_sendi")+"</td>"+
                               "<td valign='top'>"+rs.getString("kekuatan_otot")+"</td>"+
                               "<td valign='top'>"+rs.getString("statis")+"</td>"+
                               "<td valign='top'>"+rs.getString("dinamis")+"</td>"+
                               "<td valign='top'>"+rs.getString("kognitif")+"</td>"+
                               "<td valign='top'>"+rs.getString("auskultasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("alat_bantu")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_bantu")+"</td>"+
                               "<td valign='top'>"+rs.getString("prothesa")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_pro")+"</td>"+
                               "<td valign='top'>"+rs.getString("deformitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_deformitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("resikojatuh")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_resikojatuh")+"</td>"+
                               "<td valign='top'>"+rs.getString("adl")+"</td>"+
                               "<td valign='top'>"+rs.getString("lainlain_fungsional")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_fisik")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_musculoskeletal")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_neuromuscular")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_cardiopulmonal")+"</td>"+
                               "<td valign='top'>"+rs.getString("pemeriksaan_integument")+"</td>"+
                               "<td valign='top'>"+rs.getString("pengukuran_musculoskeletal")+"</td>"+
                               "<td valign='top'>"+rs.getString("pengukuran_neuromuscular")+"</td>"+
                               "<td valign='top'>"+rs.getString("pengukuran_cardiopulmonal")+"</td>"+
                               "<td valign='top'>"+rs.getString("pengukuran_integument")+"</td>"+
                               "<td valign='top'>"+rs.getString("penunjang")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosis_fisio")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_terapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='5500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRanap.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='5500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS IGD<br><br></font>"+        
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
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah2(evt,Informasi,RiwayatPenyakitSekarang);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RiwayatPenyakitSekarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarangKeyPressed
        Valid.pindah2(evt,KeluhanUtama,RiwayatPenyakitDahulu);
    }//GEN-LAST:event_RiwayatPenyakitSekarangKeyPressed

    private void RiwayatPenyakitDahuluKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahuluKeyPressed
        //Valid.pindah2(evt,RiwayatPenyakitSekarang,TD);
    }//GEN-LAST:event_RiwayatPenyakitDahuluKeyPressed

    private void MnPenilaianFisioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianFisioActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("nyeri",Sequel.cariGambar("select gambar.nyeri from gambar"));
            try {
                param.put("lokalis",getClass().getResource("/picture/fisiobody.png").openStream());
            } catch (Exception e) {
            }   
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),47).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),46).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()));

            Valid.MyReportqry("rptCetakPenilaianAwalFisioterapi.jasper","report","::[ Laporan Penilaian Awal Fisioterapi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                "inner join petugas on penilaian_fisioterapi.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianFisioActionPerformed

    private void KeluhanUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtama1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanUtama1KeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        //Valid.pindah(evt,Alergi,BB);
    }//GEN-LAST:event_TDKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        //Valid.pindah(evt,BB,Nadi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,Suhu,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        //Valid.pindah(evt,Nadi,Nyeri);
    }//GEN-LAST:event_RRKeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        //Valid.pindah(evt,RR,StatusNutrisi);
    }//GEN-LAST:event_NyeriKeyPressed

    private void Nyeri1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri1KeyPressed

    private void Nyeri2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri2KeyPressed

    private void Nyeri3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri3KeyPressed

    private void Nyeri4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri4KeyPressed

    private void Nyeri5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri5KeyPressed

    private void Nyeri6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri6KeyPressed

    private void Nyeri7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri7KeyPressed

    private void Nyeri8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri8KeyPressed

    private void Nyeri9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri9KeyPressed

    private void Nyeri10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri10KeyPressed

    private void Nyeri11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri11KeyPressed

    private void Nyeri12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri12KeyPressed

    private void Nyeri13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri13KeyPressed

    private void Nyeri14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri14KeyPressed

    private void Nyeri15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri15KeyPressed

    private void Nyeri16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri16KeyPressed

    private void Nyeri17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri17KeyPressed

    private void Nyeri18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri18KeyPressed

    private void Nyeri19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri19KeyPressed

    private void Nyeri20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri20KeyPressed

    private void Nyeri21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri21KeyPressed

    private void Nyeri22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri22KeyPressed

    private void Nyeri23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri23KeyPressed

    private void Nyeri24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri24KeyPressed

    private void Nyeri25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri25KeyPressed

    private void RiwayatPenyakitDahulu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu1KeyPressed

    private void RiwayatPenyakitDahulu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu2KeyPressed

    private void RiwayatPenyakitDahulu3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu3KeyPressed

    private void RiwayatPenyakitDahulu4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu4KeyPressed

    private void RiwayatPenyakitDahulu5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu5KeyPressed

    private void RiwayatPenyakitDahulu6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitDahulu6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitDahulu6KeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        //Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void Anamnesis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis1KeyPressed

    private void Anamnesis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis2KeyPressed

    private void Anamnesis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis3KeyPressed

    private void Anamnesis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis4KeyPressed

    private void RiwayatPenyakitSekarang1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarang1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSekarang1KeyPressed

    private void RiwayatPenyakitSekarang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarang2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSekarang2KeyPressed

    private void Nyeri26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri26KeyPressed

    private void Nyeri27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri27KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri27KeyPressed

    private void Nyeri28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri28KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri28KeyPressed

    private void Nyeri29KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri29KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri29KeyPressed

    private void Nyeri30KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri30KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri30KeyPressed

    private void Nyeri31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri31KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri31KeyPressed

    private void Nyeri32KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Nyeri32KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nyeri32KeyPressed

    private void RiwayatPenyakitSekarang3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarang3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSekarang3KeyPressed

    private void KeluhanUtama2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtama2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanUtama2KeyPressed

    private void KeluhanUtama3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtama3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeluhanUtama3KeyPressed

    private void RiwayatPenyakitSekarang4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarang4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSekarang4KeyPressed

    private void RiwayatPenyakitSekarang5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSekarang5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSekarang5KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianTerapiWicara dialog = new RMPenilaianTerapiWicara(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Anamnesis;
    private widget.ComboBox Anamnesis1;
    private widget.ComboBox Anamnesis2;
    private widget.ComboBox Anamnesis3;
    private widget.ComboBox Anamnesis4;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextArea KeluhanUtama1;
    private widget.TextArea KeluhanUtama2;
    private widget.TextArea KeluhanUtama3;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianFisio;
    private widget.TextBox Nadi;
    private widget.TextBox NmPetugas;
    private widget.TextBox Nyeri;
    private widget.TextBox Nyeri1;
    private widget.TextBox Nyeri10;
    private widget.TextBox Nyeri11;
    private widget.TextBox Nyeri12;
    private widget.TextBox Nyeri13;
    private widget.TextBox Nyeri14;
    private widget.TextBox Nyeri15;
    private widget.TextBox Nyeri16;
    private widget.TextBox Nyeri17;
    private widget.TextBox Nyeri18;
    private widget.TextBox Nyeri19;
    private widget.TextBox Nyeri2;
    private widget.TextBox Nyeri20;
    private widget.TextBox Nyeri21;
    private widget.TextBox Nyeri22;
    private widget.TextBox Nyeri23;
    private widget.TextBox Nyeri24;
    private widget.TextBox Nyeri25;
    private widget.TextBox Nyeri26;
    private widget.TextBox Nyeri27;
    private widget.TextBox Nyeri28;
    private widget.TextBox Nyeri29;
    private widget.TextBox Nyeri3;
    private widget.TextBox Nyeri30;
    private widget.TextBox Nyeri31;
    private widget.TextBox Nyeri32;
    private widget.TextBox Nyeri4;
    private widget.TextBox Nyeri5;
    private widget.TextBox Nyeri6;
    private widget.TextBox Nyeri7;
    private widget.TextBox Nyeri8;
    private widget.TextBox Nyeri9;
    private widget.TextBox RR;
    private widget.TextArea RiwayatPenyakitDahulu;
    private widget.TextArea RiwayatPenyakitDahulu1;
    private widget.TextArea RiwayatPenyakitDahulu2;
    private widget.TextArea RiwayatPenyakitDahulu3;
    private widget.TextArea RiwayatPenyakitDahulu4;
    private widget.TextArea RiwayatPenyakitDahulu5;
    private widget.TextArea RiwayatPenyakitDahulu6;
    private widget.TextArea RiwayatPenyakitSekarang;
    private widget.TextArea RiwayatPenyakitSekarang1;
    private widget.TextArea RiwayatPenyakitSekarang2;
    private widget.TextArea RiwayatPenyakitSekarang3;
    private widget.TextArea RiwayatPenyakitSekarang4;
    private widget.TextArea RiwayatPenyakitSekarang5;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
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
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                        "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                        "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                        "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                        "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                        "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                        "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                        "inner join petugas on penilaian_fisioterapi.nip=petugas.nip where "+
                        "penilaian_fisioterapi.tanggal between ? and ? order by penilaian_fisioterapi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,"+
                        "penilaian_fisioterapi.rps,penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                        "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                        "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                        "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                        "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                        "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_fisioterapi on reg_periksa.no_rawat=penilaian_fisioterapi.no_rawat "+
                        "inner join petugas on penilaian_fisioterapi.nip=petugas.nip where penilaian_fisioterapi.tanggal between ? and ? and "+
                        "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_fisioterapi.nip like ? or petugas.nama like ?) order by penilaian_fisioterapi.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("informasi"),rs.getString("keluhan_utama"),rs.getString("rps"),rs.getString("rpd"),
                        rs.getString("td"),rs.getString("hr"),rs.getString("rr"),rs.getString("suhu"),rs.getString("nyeri_tekan"),rs.getString("nyeri_gerak"),
                        rs.getString("nyeri_diam"),rs.getString("palpasi"),rs.getString("luas_gerak_sendi"),rs.getString("kekuatan_otot"),rs.getString("statis"),
                        rs.getString("dinamis"),rs.getString("kognitif"),rs.getString("auskultasi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),
                        rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("deformitas"),rs.getString("ket_deformitas"),rs.getString("resikojatuh"),
                        rs.getString("ket_resikojatuh"),rs.getString("adl"),rs.getString("lainlain_fungsional"),rs.getString("ket_fisik"),
                        rs.getString("pemeriksaan_musculoskeletal"),rs.getString("pemeriksaan_neuromuscular"),rs.getString("pemeriksaan_cardiopulmonal"),
                        rs.getString("pemeriksaan_integument"),rs.getString("pengukuran_musculoskeletal"),rs.getString("pengukuran_neuromuscular"),
                        rs.getString("pengukuran_cardiopulmonal"),rs.getString("pengukuran_integument"),rs.getString("penunjang"),rs.getString("diagnosis_fisio"),
                        rs.getString("rencana_terapi"),rs.getString("nip"),rs.getString("nama")
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
        TglAsuhan.setDate(new Date());
        KeluhanUtama.setText("");
        RiwayatPenyakitDahulu.setText("");
        RiwayatPenyakitSekarang.setText("");
        
        TabRawat.setSelectedIndex(0);
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnHapus.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnEdit.setEnabled(akses.getpenilaian_terapi_wicara());
        BtnEdit.setEnabled(akses.getpenilaian_terapi_wicara());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
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
        if(Sequel.queryu2tf("delete from penilaian_fisioterapi where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("penilaian_fisioterapi","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,keluhan_utama=?,rps=?,rpd=?,td=?,hr=?,rr=?,suhu=?,nyeri_tekan=?,nyeri_gerak=?,nyeri_diam=?,palpasi=?,luas_gerak_sendi=?,kekuatan_otot=?,statis=?,dinamis=?,kognitif=?,auskultasi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,deformitas=?,ket_deformitas=?,resikojatuh=?,ket_resikojatuh=?,adl=?,lainlain_fungsional=?,ket_fisik=?,pemeriksaan_musculoskeletal=?,pemeriksaan_neuromuscular=?,pemeriksaan_cardiopulmonal=?,pemeriksaan_integument=?,pengukuran_musculoskeletal=?,pengukuran_neuromuscular=?,pengukuran_cardiopulmonal=?,pengukuran_integument=?,penunjang=?,diagnosis_fisio=?,rencana_terapi=?,nip=?",44,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),KeluhanUtama.getText(),
                RiwayatPenyakitSekarang.getText(),RiwayatPenyakitDahulu.getText(),TD.getText(),HR.getText(),RR.getText(),Suhu.getText(),NyeriTekan.getText(),NyeriGerak.getText(),NyeriDiam.getText(),
                Palpasi.getText(),LuasGerakSendi.getText(),KekuatanOtot.getText(),Statis.getText(),Dinamis.getText(),Kognitif.getText(),Auskultasi.getText(),AlatBantu.getSelectedItem().toString(),
                KetBantu.getText(),Prothesa.getSelectedItem().toString(),KetProthesa.getText(),Deformitas.getSelectedItem().toString(),KetDeformitas.getText(),ResikoJatuh.getSelectedItem().toString(),
                KetResikoJatuh.getText(),ADL.getSelectedItem().toString(),LainlainFungsioal.getText(),KetFisik.getText(),PemeriksaanMuscu.getText(),PemeriksaanNeuro.getText(),PemeriksaanCardio.getText(),
                PemeriksaanInte.getText(),PengukuranMuscu.getText(),PengukuranNeuro.getText(),PengukuranCardio.getText(),PengukuranInte.getText(),Penunjang.getText(),Diagnosis.getText(),Rencana.getText(),
                KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
             })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(RiwayatPenyakitSekarang.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(RiwayatPenyakitDahulu.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(HR.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(NyeriTekan.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(NyeriGerak.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(NyeriDiam.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(Palpasi.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(LuasGerakSendi.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(KekuatanOtot.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(Statis.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(Dinamis.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(Kognitif.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(Auskultasi.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(AlatBantu.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(KetBantu.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(Prothesa.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(KetProthesa.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(Deformitas.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(KetDeformitas.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(ResikoJatuh.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(KetResikoJatuh.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(ADL.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(LainlainFungsioal.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(KetFisik.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(PemeriksaanMuscu.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(PemeriksaanNeuro.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(PemeriksaanCardio.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(PemeriksaanInte.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(PengukuranMuscu.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(PengukuranNeuro.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(PengukuranCardio.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(PengukuranInte.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(Rencana.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),47);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }*/
    }
}

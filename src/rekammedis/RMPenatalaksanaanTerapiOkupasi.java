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
public final class RMPenatalaksanaanTerapiOkupasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenatalaksanaanTerapiOkupasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP","Psikolog","Tanggal","Dikirim Dari","Tujuan","Informasi","Keterangan Informasi",
            "Rupa/Wajah","Bentuk Tubuh","Tindakan","Pakaian/Aksesoris","Penyampaian/Ekspresi","Berbicara","Penggunaan Kata","Ciri Yang Menyolok","Hasil Psikotes",
            "Kepribadian","Psikodinamika","Kesimpulan Psikolog"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
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
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(72);
            }else if(i==9){
                column.setPreferredWidth(62);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(165);
            }else if(i==12){
                column.setPreferredWidth(71);
            }else if(i==13){
                column.setPreferredWidth(79);
            }else if(i==14){
                column.setPreferredWidth(103);
            }else if(i==15){
                column.setPreferredWidth(99);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(196);
            }else if(i==18){
                column.setPreferredWidth(185);
            }else if(i==19){
                column.setPreferredWidth(200);
            }else if(i==20){
                column.setPreferredWidth(200);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(200);
            }else if(i==23){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KeluhanUtama.setDocument(new batasInput((int)400).getKata(KeluhanUtama));
        RPS.setDocument(new batasInput((int)300).getKata(RPS));
        RPD.setDocument(new batasInput((int)300).getKata(RPD));
        AnamnesaUmum.setDocument(new batasInput((int)300).getKata(AnamnesaUmum));
        TandaVital.setDocument(new batasInput((int)150).getKata(TandaVital));
        PemeriksaanPenunjang.setDocument(new batasInput((int)200).getKata(PemeriksaanPenunjang));
        KeteranganSpesialisasi.setDocument(new batasInput((int)30).getKata(KeteranganSpesialisasi));
        PemeriksaanOkupasiTerapi.setDocument(new batasInput((int)200).getKata(PemeriksaanOkupasiTerapi));
        Aset.setDocument(new batasInput((int)150).getKata(Aset));
        Limitasi.setDocument(new batasInput((int)150).getKata(Limitasi));
        DiagnosaOkupasi.setDocument(new batasInput((int)100).getKata(DiagnosaOkupasi));
        RencanaIntervensi.setDocument(new batasInput((int)400).getKata(RencanaIntervensi));
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
        KetLokalis = new widget.TextArea();
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
        jLabel99 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        DiagnosaOkupasi = new widget.TextArea();
        scrollPane14 = new widget.ScrollPane();
        RencanaIntervensi = new widget.TextArea();
        jLabel104 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        TandaVital = new widget.TextArea();
        Spesialisasi = new widget.ComboBox();
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
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel36 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel32 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        AnamnesaUmum = new widget.TextArea();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        RPS = new widget.TextArea();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel39 = new widget.Label();
        jLabel35 = new widget.Label();
        KeteranganSpesialisasi = new widget.TextBox();
        jLabel40 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        PemeriksaanOkupasiTerapi = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Aset = new widget.TextArea();
        scrollPane17 = new widget.ScrollPane();
        Limitasi = new widget.TextArea();
        jLabel42 = new widget.Label();
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
        MnPenilaianMedis.setText("Laporan Penilaian Psikolog");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        KetLokalis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KetLokalis.setColumns(20);
        KetLokalis.setRows(5);
        KetLokalis.setName("KetLokalis"); // NOI18N
        KetLokalis.setPreferredSize(new java.awt.Dimension(182, 92));
        KetLokalis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLokalisKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penatalaksaan Terapi Okupasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 863));
        FormInput.setLayout(null);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN ");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("IV. RENCANA INTERVENSI TERAPI OKUPASIONAL");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 680, 430, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. RIWAYAT PEMERIKSAAN ");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 240, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N
        scrollPane13.setVerifyInputWhenFocusTarget(false);

        DiagnosaOkupasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaOkupasi.setColumns(20);
        DiagnosaOkupasi.setRows(5);
        DiagnosaOkupasi.setName("DiagnosaOkupasi"); // NOI18N
        DiagnosaOkupasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaOkupasiKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(DiagnosaOkupasi);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 610, 810, 63);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        RencanaIntervensi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RencanaIntervensi.setColumns(20);
        RencanaIntervensi.setRows(15);
        RencanaIntervensi.setName("RencanaIntervensi"); // NOI18N
        RencanaIntervensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaIntervensiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(RencanaIntervensi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 700, 810, 153);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("III. DIAGNOSIS TERAPI OKUPASIONAL");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 590, 200, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        TandaVital.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TandaVital.setColumns(20);
        TandaVital.setRows(3);
        TandaVital.setName("TandaVital"); // NOI18N
        TandaVital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaVitalKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(TandaVital);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(44, 280, 810, 43);

        Spesialisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pediatri", "Dewasa", "Geriatri", "Psikososial", "-" }));
        Spesialisasi.setName("Spesialisasi"); // NOI18N
        Spesialisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpesialisasiKeyPressed(evt);
            }
        });
        FormInput.add(Spesialisasi);
        Spesialisasi.setBounds(112, 400, 110, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(319, 10, 250, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 100, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 80, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(84, 40, 120, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(206, 40, 332, 23);

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
        jLabel10.setBounds(0, 10, 80, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(660, 40, 60, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2024 14:18:36" }));
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

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 70, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 230, 880, 1);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 590, 880, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 590, 880, 1);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 680, 880, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 680, 880, 1);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Anamnesa General");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(44, 160, 100, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Keluhan Utama");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(44, 90, 90, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 90, 125, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 160, 143, 23);

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
        scrollPane1.setBounds(129, 90, 310, 63);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        AnamnesaUmum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AnamnesaUmum.setColumns(20);
        AnamnesaUmum.setRows(5);
        AnamnesaUmum.setName("AnamnesaUmum"); // NOI18N
        AnamnesaUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesaUmumKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(AnamnesaUmum);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(147, 160, 292, 63);

        jLabel30.setText("Riwayat Penyakit Sekarang :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(440, 90, 150, 23);

        jLabel31.setText("Riwayat Penyakit Dahulu :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(440, 160, 150, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        RPS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPS.setColumns(20);
        RPS.setRows(5);
        RPS.setName("RPS"); // NOI18N
        RPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(RPS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(594, 90, 260, 63);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(594, 160, 260, 63);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Tanda Vital :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(44, 260, 100, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Pemeriksan Penunjang :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(44, 330, 190, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(3);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(44, 350, 810, 43);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Spesialisasi");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(44, 400, 70, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 400, 108, 23);

        KeteranganSpesialisasi.setEditable(false);
        KeteranganSpesialisasi.setName("KeteranganSpesialisasi"); // NOI18N
        KeteranganSpesialisasi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KeteranganSpesialisasi);
        KeteranganSpesialisasi.setBounds(226, 400, 400, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Pemeriksaan Okupasi Terapi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(44, 430, 190, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        PemeriksaanOkupasiTerapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanOkupasiTerapi.setColumns(20);
        PemeriksaanOkupasiTerapi.setRows(6);
        PemeriksaanOkupasiTerapi.setName("PemeriksaanOkupasiTerapi"); // NOI18N
        PemeriksaanOkupasiTerapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanOkupasiTerapiKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(PemeriksaanOkupasiTerapi);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(44, 450, 810, 53);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Limitasi");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(454, 510, 400, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        Aset.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Aset.setColumns(20);
        Aset.setRows(5);
        Aset.setName("Aset"); // NOI18N
        Aset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsetKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Aset);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 530, 400, 53);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Limitasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Limitasi.setColumns(20);
        Limitasi.setRows(5);
        Limitasi.setName("Limitasi"); // NOI18N
        Limitasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LimitasiKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Limitasi);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(454, 530, 400, 53);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Aset");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(44, 510, 400, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penatalaksaan", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2024" }));
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

        TabRawat.addTab("Data Penatalaksaan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Ciriyangmenyolok.getText().trim().equals("")){
            Valid.textKosong(Ciriyangmenyolok,"CIRI YANG MENYOLOK");
        }else if(Hasilpsikotes.getText().trim().equals("")){
            Valid.textKosong(Hasilpsikotes,"HASIL PSIKOTES");
        }else if(Kepribadian.getText().trim().equals("")){
            Valid.textKosong(Kepribadian,"KEPRIBADIAN DAN ASPEK-ASPEKNYA");
        }else if(Psikodinamika.getText().trim().equals("")){
            Valid.textKosong(Psikodinamika,"PSIKODINAMIKA");
        }else if(Kesimpulanpsikolog.getText().trim().equals("")){
            Valid.textKosong(Kesimpulanpsikolog,"KESIMPULAN PSIKOLOG");            
        }else{
            if(Sequel.menyimpantf("penatalaksanaan_terapi_okupasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),Informasi.getSelectedItem().toString(),
                    Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),KetAlloAuto.getText(),Rupa.getSelectedItem().toString(),Bentuktubuh.getSelectedItem().toString(),
                    Tindakan.getSelectedItem().toString(),Pakaian.getSelectedItem().toString(),Ekspresi.getSelectedItem().toString(),Berbicara.getSelectedItem().toString(),
                    Penggunaankata.getSelectedItem().toString(),Ciriyangmenyolok.getText(),Hasilpsikotes.getText(),Kepribadian.getText(),Psikodinamika.getText(),Kesimpulanpsikolog.getText()
                })==true){
                    tabMode.addRow(new String[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                        Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),Informasi.getSelectedItem().toString(),KetAlloAuto.getText(),Rupa.getSelectedItem().toString(),Bentuktubuh.getSelectedItem().toString(),
                        Tindakan.getSelectedItem().toString(),Pakaian.getSelectedItem().toString(),Ekspresi.getSelectedItem().toString(),Berbicara.getSelectedItem().toString(),Penggunaankata.getSelectedItem().toString(),Ciriyangmenyolok.getText(),
                        Hasilpsikotes.getText(),Kepribadian.getText(),Psikodinamika.getText(),Kesimpulanpsikolog.getText()
                    });
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
            }
        }*/
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,RencanaIntervensi,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh psikolog yang bersangkutan..!!");
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
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Ciriyangmenyolok.getText().trim().equals("")){
            Valid.textKosong(Ciriyangmenyolok,"CIRI YANG MENYOLOK");
        }else if(Hasilpsikotes.getText().trim().equals("")){
            Valid.textKosong(Hasilpsikotes,"HASIL PSIKOTES");
        }else if(Kepribadian.getText().trim().equals("")){
            Valid.textKosong(Kepribadian,"KEPRIBADIAN DAN ASPEK-ASPEKNYA");
        }else if(Psikodinamika.getText().trim().equals("")){
            Valid.textKosong(Psikodinamika,"PSIKODINAMIKA");
        }else if(Kesimpulanpsikolog.getText().trim().equals("")){
            Valid.textKosong(Kesimpulanpsikolog,"KESIMPULAN PSIKOLOG");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh psikolog yang bersangkutan..!!");
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='72px'><b>Dikirim Dari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='62px'><b>Tujuan Pemeriksaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85px'><b>Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='165px'><b>Keterangan Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='71px'><b>Rupa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='79px'><b>Bentuk Tubuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='103px'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='99px'><b>Pakaian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Penyampaian/Ekspresi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='196px'><b>Berbicara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='185px'><b>Penggunaan Kata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ciri Yang Menyolok</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Hasil Psikotes</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kepribadian</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Psikodinamika</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Kesimpulan Psikolog</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2900' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianPsikolog.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PSIKOLOG<br><br></font>"+        
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

    private void KetLokalisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLokalisKeyPressed
        //Valid.pindah2(evt,Ciriyangmenyolok,Hasilpsikotes);
    }//GEN-LAST:event_KetLokalisKeyPressed

    private void DiagnosaOkupasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaOkupasiKeyPressed
        //Valid.pindah2(evt,Kepribadian,Kesimpulanpsikolog);
    }//GEN-LAST:event_DiagnosaOkupasiKeyPressed

    private void RencanaIntervensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaIntervensiKeyPressed
        Valid.pindah2(evt,DiagnosaOkupasi,BtnSimpan);
    }//GEN-LAST:event_RencanaIntervensiKeyPressed

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
            try {
                param.put("lokalis",getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            } 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianPsikolog.jasper","report","::[ Laporan Penilaian Psikolog ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penatalaksanaan_terapi_okupasi.tanggal,"+
                        "penatalaksanaan_terapi_okupasi.nip,penatalaksanaan_terapi_okupasi.anamnesis,penatalaksanaan_terapi_okupasi.dikirim_dari,penatalaksanaan_terapi_okupasi.tujuan_pemeriksaan,penatalaksanaan_terapi_okupasi.ket_anamnesis,penatalaksanaan_terapi_okupasi.rupa,penatalaksanaan_terapi_okupasi.bentuk_tubuh,penatalaksanaan_terapi_okupasi.tindakan,"+
                        "penatalaksanaan_terapi_okupasi.pakaian,penatalaksanaan_terapi_okupasi.ekspresi,penatalaksanaan_terapi_okupasi.berbicara,penatalaksanaan_terapi_okupasi.penggunaan_kata,penatalaksanaan_terapi_okupasi.ciri_menyolok,penatalaksanaan_terapi_okupasi.hasil_psikotes,penatalaksanaan_terapi_okupasi.kepribadian,penatalaksanaan_terapi_okupasi.psikodinamika,penatalaksanaan_terapi_okupasi.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penatalaksanaan_terapi_okupasi on reg_periksa.no_rawat=penatalaksanaan_terapi_okupasi.no_rawat "+
                        "inner join petugas on penatalaksanaan_terapi_okupasi.nip=petugas.nip where penatalaksanaan_terapi_okupasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void SpesialisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpesialisasiKeyPressed
        //Valid.pindah(evt,Dikirimdari,Pakaian);
    }//GEN-LAST:event_SpesialisasiKeyPressed

    private void TandaVitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaVitalKeyPressed
        //Valid.pindah2(evt,Berbicara,Hasilpsikotes);
    }//GEN-LAST:event_TandaVitalKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,RencanaIntervensi,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,BtnDokter,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah2(evt,Hubungan,RPS);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void AnamnesaUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesaUmumKeyPressed
        Valid.pindah2(evt,RPS,RPD);
    }//GEN-LAST:event_AnamnesaUmumKeyPressed

    private void RPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPSKeyPressed
        Valid.pindah2(evt,KeluhanUtama,AnamnesaUmum);
    }//GEN-LAST:event_RPSKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        //Valid.pindah2(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void AsetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsetKeyPressed
        //Valid.pindah2(evt,Diagnosis2,Terapi);
    }//GEN-LAST:event_AsetKeyPressed

    private void LimitasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LimitasiKeyPressed
        //Valid.pindah2(evt,Permasalahan,Tindakan);
    }//GEN-LAST:event_LimitasiKeyPressed

    private void PemeriksaanOkupasiTerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanOkupasiTerapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PemeriksaanOkupasiTerapiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenatalaksanaanTerapiOkupasi dialog = new RMPenatalaksanaanTerapiOkupasi(new javax.swing.JFrame(), true);
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
    private widget.TextArea AnamnesaUmum;
    private widget.TextArea Aset;
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
    private widget.TextArea DiagnosaOkupasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextArea KetLokalis;
    private widget.TextBox KeteranganSpesialisasi;
    private widget.Label LCount;
    private widget.TextArea Limitasi;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmPetugas;
    private widget.TextArea PemeriksaanOkupasiTerapi;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.TextArea RPD;
    private widget.TextArea RPS;
    private widget.TextArea RencanaIntervensi;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Spesialisasi;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea TandaVital;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel104;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penatalaksanaan_terapi_okupasi.tanggal,"+
                        "penatalaksanaan_terapi_okupasi.nip,penatalaksanaan_terapi_okupasi.anamnesis,penatalaksanaan_terapi_okupasi.dikirim_dari,penatalaksanaan_terapi_okupasi.tujuan_pemeriksaan,penatalaksanaan_terapi_okupasi.ket_anamnesis,penatalaksanaan_terapi_okupasi.rupa,penatalaksanaan_terapi_okupasi.bentuk_tubuh,penatalaksanaan_terapi_okupasi.tindakan,"+
                        "penatalaksanaan_terapi_okupasi.pakaian,penatalaksanaan_terapi_okupasi.ekspresi,penatalaksanaan_terapi_okupasi.berbicara,penatalaksanaan_terapi_okupasi.penggunaan_kata,penatalaksanaan_terapi_okupasi.ciri_menyolok,penatalaksanaan_terapi_okupasi.hasil_psikotes,penatalaksanaan_terapi_okupasi.kepribadian,penatalaksanaan_terapi_okupasi.psikodinamika,penatalaksanaan_terapi_okupasi.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penatalaksanaan_terapi_okupasi on reg_periksa.no_rawat=penatalaksanaan_terapi_okupasi.no_rawat "+
                        "inner join petugas on penatalaksanaan_terapi_okupasi.nip=petugas.nip where "+
                        "penatalaksanaan_terapi_okupasi.tanggal between ? and ? order by penatalaksanaan_terapi_okupasi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penatalaksanaan_terapi_okupasi.tanggal,"+
                        "penatalaksanaan_terapi_okupasi.nip,penatalaksanaan_terapi_okupasi.anamnesis,penatalaksanaan_terapi_okupasi.dikirim_dari,penatalaksanaan_terapi_okupasi.tujuan_pemeriksaan,penatalaksanaan_terapi_okupasi.ket_anamnesis,penatalaksanaan_terapi_okupasi.rupa,penatalaksanaan_terapi_okupasi.bentuk_tubuh,penatalaksanaan_terapi_okupasi.tindakan,"+
                        "penatalaksanaan_terapi_okupasi.pakaian,penatalaksanaan_terapi_okupasi.ekspresi,penatalaksanaan_terapi_okupasi.berbicara,penatalaksanaan_terapi_okupasi.penggunaan_kata,penatalaksanaan_terapi_okupasi.ciri_menyolok,penatalaksanaan_terapi_okupasi.hasil_psikotes,penatalaksanaan_terapi_okupasi.kepribadian,penatalaksanaan_terapi_okupasi.psikodinamika,penatalaksanaan_terapi_okupasi.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penatalaksanaan_terapi_okupasi on reg_periksa.no_rawat=penatalaksanaan_terapi_okupasi.no_rawat "+
                        "inner join petugas on penatalaksanaan_terapi_okupasi.nip=petugas.nip where "+
                        "penatalaksanaan_terapi_okupasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penatalaksanaan_terapi_okupasi.nip like ? or petugas.nama like ?) order by penatalaksanaan_terapi_okupasi.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("dikirim_dari"),rs.getString("tujuan_pemeriksaan"),rs.getString("anamnesis"),rs.getString("ket_anamnesis"),rs.getString("rupa"),rs.getString("bentuk_tubuh"),rs.getString("tindakan"),
                        rs.getString("pakaian"),rs.getString("ekspresi"),rs.getString("berbicara"),rs.getString("penggunaan_kata"),rs.getString("ciri_menyolok"),rs.getString("hasil_psikotes"),rs.getString("kepribadian"),rs.getString("psikodinamika"),rs.getString("kesimpulan_psikolog")                     
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
        /*Informasi.setSelectedIndex(0);
        Dikirimdari.setSelectedIndex(0);
        TujuanPemeriksaan.setSelectedIndex(0);
        KetAlloAuto.setText("");
        Rupa.setSelectedIndex(0);
        Bentuktubuh.setSelectedIndex(0);
        Tindakan.setSelectedIndex(0);
        Pakaian.setSelectedIndex(0);
        Ekspresi.setSelectedIndex(0);
        Berbicara.setSelectedIndex(0);
        Penggunaankata.setSelectedIndex(0);
        Ciriyangmenyolok.setText("");
        Hasilpsikotes.setText("");
        Kepribadian.setText("");
        Psikodinamika.setText("");
        Kesimpulanpsikolog.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        TujuanPemeriksaan.requestFocus();*/
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            /*TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Dikirimdari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TujuanPemeriksaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KetAlloAuto.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Rupa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Bentuktubuh.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Tindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Pakaian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Ekspresi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Berbicara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Penggunaankata.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Ciriyangmenyolok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Hasilpsikotes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kepribadian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Psikodinamika.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Kesimpulanpsikolog.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());*/
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
        BtnSimpan.setEnabled(akses.getpenatalaksanaan_terapi_okupasi());
        BtnHapus.setEnabled(akses.getpenatalaksanaan_terapi_okupasi());
        BtnEdit.setEnabled(akses.getpenatalaksanaan_terapi_okupasi());
        BtnEdit.setEnabled(akses.getpenatalaksanaan_terapi_okupasi());
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
        if(Sequel.queryu2tf("delete from penatalaksanaan_terapi_okupasi where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penatalaksanaan_terapi_okupasi","no_rawat=?","no_rawat=?,tanggal=?,nip=?,anamnesis=?,dikirim_dari=?,tujuan_pemeriksaan=?,ket_anamnesis=?,rupa=?,bentuk_tubuh=?,tindakan=?,pakaian=?,ekspresi=?,berbicara=?,penggunaan_kata=?,ciri_menyolok=?,hasil_psikotes=?,kepribadian=?,psikodinamika=?,kesimpulan_psikolog=?",20,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdPetugas.getText(),Informasi.getSelectedItem().toString(),Dikirimdari.getSelectedItem().toString(),TujuanPemeriksaan.getSelectedItem().toString(),KetAlloAuto.getText(),Rupa.getSelectedItem().toString(),
                Bentuktubuh.getSelectedItem().toString(),Tindakan.getSelectedItem().toString(),Pakaian.getSelectedItem().toString(),Ekspresi.getSelectedItem().toString(),Berbicara.getSelectedItem().toString(),Penggunaankata.getSelectedItem().toString(),Ciriyangmenyolok.getText(),Hasilpsikotes.getText(),Kepribadian.getText(),Psikodinamika.getText(),
                Kesimpulanpsikolog.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Dikirimdari.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(TujuanPemeriksaan.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(Informasi.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(KetAlloAuto.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(Rupa.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Bentuktubuh.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(Tindakan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Pakaian.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(Ekspresi.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(Berbicara.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(Penggunaankata.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(Ciriyangmenyolok.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(Hasilpsikotes.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(Kepribadian.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(Psikodinamika.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(Kesimpulanpsikolog.getText(),tbObat.getSelectedRow(),23);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }*/
    }
}

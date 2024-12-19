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
public final class RMPenilaianPsikologiKlinis extends javax.swing.JDialog {
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
    public RMPenilaianPsikologiKlinis(java.awt.Frame parent, boolean modal) {
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
        KetAlloAuto.setDocument(new batasInput((int)2000).getKata(KetAlloAuto));
        Kesimpulanpsikolog.setDocument(new batasInput((int)50).getKata(Kesimpulanpsikolog));
        KetLokalis.setDocument(new batasInput((int)3000).getKata(KetLokalis));
        Kesimpulanpsikolog.setDocument(new batasInput((int)1000).getKata(Kesimpulanpsikolog));
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
        scrollPane14 = new widget.ScrollPane();
        Kesimpulanpsikolog = new widget.TextArea();
        jLabel34 = new widget.Label();
        Rupa = new widget.ComboBox();
        jLabel42 = new widget.Label();
        Dikirimdari = new widget.ComboBox();
        jLabel56 = new widget.Label();
        TujuanPemeriksaan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        KetAlloAuto = new javax.swing.JTextArea();
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
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        TglAsuhan = new widget.Tanggal();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        scrollPane19 = new widget.ScrollPane();
        Tindakan = new widget.TextArea();
        jLabel114 = new widget.Label();
        scrollPane20 = new widget.ScrollPane();
        Tindakan1 = new widget.TextArea();
        jLabel115 = new widget.Label();
        scrollPane21 = new widget.ScrollPane();
        Tindakan2 = new widget.TextArea();
        jLabel58 = new widget.Label();
        jLabel9 = new widget.Label();
        TglLahir1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        TglLahir2 = new widget.TextBox();
        jLabel116 = new widget.Label();
        scrollPane22 = new widget.ScrollPane();
        Tindakan3 = new widget.TextArea();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        TglLahir3 = new widget.TextBox();
        jLabel59 = new widget.Label();
        TglLahir4 = new widget.TextBox();
        jLabel120 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel61 = new widget.Label();
        TglLahir5 = new widget.TextBox();
        jLabel122 = new widget.Label();
        jLabel62 = new widget.Label();
        TglLahir6 = new widget.TextBox();
        jLabel123 = new widget.Label();
        jLabel63 = new widget.Label();
        TglLahir7 = new widget.TextBox();
        jLabel124 = new widget.Label();
        jLabel64 = new widget.Label();
        TglLahir8 = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel65 = new widget.Label();
        TglLahir9 = new widget.TextBox();
        jLabel126 = new widget.Label();
        jLabel66 = new widget.Label();
        TglLahir10 = new widget.TextBox();
        jLabel127 = new widget.Label();
        jLabel67 = new widget.Label();
        TglLahir11 = new widget.TextBox();
        jLabel128 = new widget.Label();
        jLabel68 = new widget.Label();
        TglLahir12 = new widget.TextBox();
        jLabel129 = new widget.Label();
        TglLahir13 = new widget.TextBox();
        jLabel130 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel131 = new widget.Label();
        TglLahir14 = new widget.TextBox();
        jLabel70 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel71 = new widget.Label();
        TglLahir15 = new widget.TextBox();
        jLabel72 = new widget.Label();
        TglLahir16 = new widget.TextBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel73 = new widget.Label();
        TglLahir17 = new widget.TextBox();
        jLabel135 = new widget.Label();
        jLabel74 = new widget.Label();
        TglLahir18 = new widget.TextBox();
        jLabel136 = new widget.Label();
        jLabel75 = new widget.Label();
        TglLahir19 = new widget.TextBox();
        jLabel137 = new widget.Label();
        TglAsuhan1 = new widget.Tanggal();
        jLabel76 = new widget.Label();
        jLabel138 = new widget.Label();
        jLabel13 = new widget.Label();
        TglLahir20 = new widget.TextBox();
        jLabel139 = new widget.Label();
        jLabel77 = new widget.Label();
        TglLahir21 = new widget.TextBox();
        jLabel101 = new widget.Label();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        scrollPane15 = new widget.ScrollPane();
        Kesimpulanpsikolog1 = new widget.TextArea();
        jLabel102 = new widget.Label();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        scrollPane16 = new widget.ScrollPane();
        Kesimpulanpsikolog2 = new widget.TextArea();
        jLabel103 = new widget.Label();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        scrollPane17 = new widget.ScrollPane();
        Kesimpulanpsikolog3 = new widget.TextArea();
        jLabel104 = new widget.Label();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        TglLahir22 = new widget.TextBox();
        TglLahir23 = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        jSeparator20 = new javax.swing.JSeparator();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        TglLahir24 = new widget.TextBox();
        TglLahir25 = new widget.TextBox();
        jLabel143 = new widget.Label();
        TglLahir26 = new widget.TextBox();
        TglLahir27 = new widget.TextBox();
        jLabel144 = new widget.Label();
        TglLahir28 = new widget.TextBox();
        TglLahir29 = new widget.TextBox();
        jLabel145 = new widget.Label();
        TglLahir30 = new widget.TextBox();
        TglLahir31 = new widget.TextBox();
        jLabel146 = new widget.Label();
        TglLahir32 = new widget.TextBox();
        TglLahir33 = new widget.TextBox();
        jLabel147 = new widget.Label();
        TglLahir34 = new widget.TextBox();
        TglLahir35 = new widget.TextBox();
        jLabel105 = new widget.Label();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        scrollPane18 = new widget.ScrollPane();
        Kesimpulanpsikolog4 = new widget.TextArea();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Psikologi Klinis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1823));
        FormInput.setLayout(null);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. PEMERIKSAAN PSIKOLOGIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 130, 200, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 861, 880, 0);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("II. DINAMIKA PSIKOLOGIS");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 1150, 180, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Kesimpulanpsikolog.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulanpsikolog.setColumns(20);
        Kesimpulanpsikolog.setRows(5);
        Kesimpulanpsikolog.setName("Kesimpulanpsikolog"); // NOI18N
        Kesimpulanpsikolog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanpsikologKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Kesimpulanpsikolog);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1170, 810, 53);

        jLabel34.setText("Keterangan Anamnesis :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(220, 70, 150, 23);

        Rupa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sangat Serius", "Serius", "Cukup Serius" }));
        Rupa.setName("Rupa"); // NOI18N
        Rupa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RupaKeyPressed(evt);
            }
        });
        FormInput.add(Rupa);
        Rupa.setBounds(153, 400, 120, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Permasalah Saat Ini");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(44, 400, 115, 23);

        Dikirimdari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ruang Rawat", "Poliklinik", "Rehabilitasi", "After Care", "Dokter" }));
        Dikirimdari.setName("Dikirimdari"); // NOI18N
        Dikirimdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DikirimdariKeyPressed(evt);
            }
        });
        FormInput.add(Dikirimdari);
        Dikirimdari.setBounds(84, 100, 128, 23);

        jLabel56.setText("Dikirim Dari :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 100, 80, 23);

        TujuanPemeriksaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Klinik", "Bimbingan", "Forensik", " " }));
        TujuanPemeriksaan.setName("TujuanPemeriksaan"); // NOI18N
        TujuanPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(TujuanPemeriksaan);
        TujuanPemeriksaan.setBounds(744, 40, 110, 23);

        jLabel57.setText("Tujuan Pemeriksaan :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(620, 40, 120, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        KetAlloAuto.setColumns(20);
        KetAlloAuto.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        KetAlloAuto.setRows(5);
        KetAlloAuto.setName("KetAlloAuto"); // NOI18N
        KetAlloAuto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetAlloAutoKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(KetAlloAuto);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(374, 70, 480, 53);

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

        label14.setText("Psikolog :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 80, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(84, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(186, 40, 185, 23);

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
        BtnDokter.setBounds(373, 40, 28, 23);

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
        label11.setBounds(415, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel36.setText("Anamnesis :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 70, 80, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(84, 70, 128, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2024 14:44:15" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(476, 40, 130, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 130, 880, 1);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 1150, 880, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 1150, 880, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Keluhan Utama :");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 150, 320, 23);

        scrollPane19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane19.setName("scrollPane19"); // NOI18N

        Tindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan.setColumns(20);
        Tindakan.setRows(10);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        scrollPane19.setViewportView(Tindakan);

        FormInput.add(scrollPane19);
        scrollPane19.setBounds(44, 170, 810, 63);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Riwayat Penyakit :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(44, 240, 320, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        Tindakan1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan1.setColumns(20);
        Tindakan1.setRows(10);
        Tindakan1.setName("Tindakan1"); // NOI18N
        Tindakan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tindakan1KeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Tindakan1);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(44, 260, 810, 53);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Riwayat Keluhan :");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(44, 320, 320, 23);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N

        Tindakan2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan2.setColumns(20);
        Tindakan2.setRows(10);
        Tindakan2.setName("Tindakan2"); // NOI18N
        Tindakan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tindakan2KeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(Tindakan2);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(44, 340, 810, 53);

        jLabel58.setText(":");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 400, 149, 23);

        jLabel9.setText("Alasan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(277, 400, 50, 23);

        TglLahir1.setEditable(false);
        TglLahir1.setHighlighter(null);
        TglLahir1.setName("TglLahir1"); // NOI18N
        FormInput.add(TglLahir1);
        TglLahir1.setBounds(331, 400, 225, 23);

        jLabel12.setText("Ekspektasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(555, 400, 70, 23);

        TglLahir2.setEditable(false);
        TglLahir2.setHighlighter(null);
        TglLahir2.setName("TglLahir2"); // NOI18N
        FormInput.add(TglLahir2);
        TglLahir2.setBounds(629, 400, 225, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Riwayat Hidup Singkat :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(44, 430, 320, 23);

        scrollPane22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane22.setName("scrollPane22"); // NOI18N

        Tindakan3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tindakan3.setColumns(20);
        Tindakan3.setRows(10);
        Tindakan3.setName("Tindakan3"); // NOI18N
        Tindakan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tindakan3KeyPressed(evt);
            }
        });
        scrollPane22.setViewportView(Tindakan3);

        FormInput.add(scrollPane22);
        scrollPane22.setBounds(44, 450, 810, 43);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Observasi :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(44, 500, 320, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Kondisi Psikologis :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(64, 520, 320, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("- Penampilan");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(84, 540, 80, 23);

        TglLahir3.setEditable(false);
        TglLahir3.setHighlighter(null);
        TglLahir3.setName("TglLahir3"); // NOI18N
        FormInput.add(TglLahir3);
        TglLahir3.setBounds(159, 540, 695, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 540, 155, 23);

        TglLahir4.setEditable(false);
        TglLahir4.setHighlighter(null);
        TglLahir4.setName("TglLahir4"); // NOI18N
        FormInput.add(TglLahir4);
        TglLahir4.setBounds(176, 570, 678, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("- Ekspresi Wajah");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(84, 570, 110, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 570, 172, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("- Suasana Hati");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(84, 600, 90, 23);

        jLabel61.setText(":");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 600, 162, 23);

        TglLahir5.setEditable(false);
        TglLahir5.setHighlighter(null);
        TglLahir5.setName("TglLahir5"); // NOI18N
        FormInput.add(TglLahir5);
        TglLahir5.setBounds(166, 600, 688, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("- Tingkah Laku");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(84, 630, 90, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 630, 162, 23);

        TglLahir6.setEditable(false);
        TglLahir6.setHighlighter(null);
        TglLahir6.setName("TglLahir6"); // NOI18N
        FormInput.add(TglLahir6);
        TglLahir6.setBounds(166, 630, 688, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("- Fungsi Umum");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(84, 660, 90, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 660, 164, 23);

        TglLahir7.setEditable(false);
        TglLahir7.setHighlighter(null);
        TglLahir7.setName("TglLahir7"); // NOI18N
        FormInput.add(TglLahir7);
        TglLahir7.setBounds(168, 660, 686, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("- Fungsi Intelektual");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(84, 690, 110, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 690, 185, 23);

        TglLahir8.setEditable(false);
        TglLahir8.setHighlighter(null);
        TglLahir8.setName("TglLahir8"); // NOI18N
        FormInput.add(TglLahir8);
        TglLahir8.setBounds(189, 690, 665, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("- Pengalaman");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(84, 720, 110, 23);

        jLabel65.setText(":");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 720, 158, 23);

        TglLahir9.setEditable(false);
        TglLahir9.setHighlighter(null);
        TglLahir9.setName("TglLahir9"); // NOI18N
        FormInput.add(TglLahir9);
        TglLahir9.setBounds(162, 720, 692, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("- Lainnya");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(84, 750, 110, 23);

        jLabel66.setText(":");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 750, 135, 23);

        TglLahir10.setEditable(false);
        TglLahir10.setHighlighter(null);
        TglLahir10.setName("TglLahir10"); // NOI18N
        FormInput.add(TglLahir10);
        TglLahir10.setBounds(139, 750, 715, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Kondisi Patologis :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(64, 780, 320, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 800, 169, 23);

        TglLahir11.setEditable(false);
        TglLahir11.setHighlighter(null);
        TglLahir11.setName("TglLahir11"); // NOI18N
        FormInput.add(TglLahir11);
        TglLahir11.setBounds(173, 800, 681, 23);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("- Delusi/Waham");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(84, 800, 110, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 830, 166, 23);

        TglLahir12.setEditable(false);
        TglLahir12.setHighlighter(null);
        TglLahir12.setName("TglLahir12"); // NOI18N
        FormInput.add(TglLahir12);
        TglLahir12.setBounds(170, 830, 684, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("- Proses Pikiran");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(84, 830, 110, 23);

        TglLahir13.setEditable(false);
        TglLahir13.setHighlighter(null);
        TglLahir13.setName("TglLahir13"); // NOI18N
        FormInput.add(TglLahir13);
        TglLahir13.setBounds(151, 860, 703, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("- Halusinasi");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(84, 860, 110, 23);

        jLabel69.setText(":");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 860, 147, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("- Afek");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(84, 890, 50, 23);

        TglLahir14.setEditable(false);
        TglLahir14.setHighlighter(null);
        TglLahir14.setName("TglLahir14"); // NOI18N
        FormInput.add(TglLahir14);
        TglLahir14.setBounds(125, 890, 729, 23);

        jLabel70.setText(":");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 890, 121, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("- Insight");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(84, 920, 60, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 920, 132, 23);

        TglLahir15.setEditable(false);
        TglLahir15.setHighlighter(null);
        TglLahir15.setName("TglLahir15"); // NOI18N
        FormInput.add(TglLahir15);
        TglLahir15.setBounds(136, 920, 718, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 950, 149, 23);

        TglLahir16.setEditable(false);
        TglLahir16.setHighlighter(null);
        TglLahir16.setName("TglLahir16"); // NOI18N
        FormInput.add(TglLahir16);
        TglLahir16.setBounds(153, 950, 701, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("- Kesadaran");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(84, 950, 80, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("- Orientasi Ruang/Waktu/Tempat");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(84, 980, 170, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 980, 253, 23);

        TglLahir17.setEditable(false);
        TglLahir17.setHighlighter(null);
        TglLahir17.setName("TglLahir17"); // NOI18N
        FormInput.add(TglLahir17);
        TglLahir17.setBounds(257, 980, 597, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("- Atensi");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(84, 1010, 80, 23);

        jLabel74.setText(":");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 1010, 128, 23);

        TglLahir18.setEditable(false);
        TglLahir18.setHighlighter(null);
        TglLahir18.setName("TglLahir18"); // NOI18N
        FormInput.add(TglLahir18);
        TglLahir18.setBounds(132, 1010, 722, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("- Kontrol Impuls");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(84, 1040, 80, 23);

        jLabel75.setText(":");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(0, 1040, 169, 23);

        TglLahir19.setEditable(false);
        TglLahir19.setHighlighter(null);
        TglLahir19.setName("TglLahir19"); // NOI18N
        FormInput.add(TglLahir19);
        TglLahir19.setBounds(173, 1040, 681, 23);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("Psikotes :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(44, 1070, 320, 23);

        TglAsuhan1.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-12-2024" }));
        TglAsuhan1.setDisplayFormat("dd-MM-yyyy");
        TglAsuhan1.setName("TglAsuhan1"); // NOI18N
        TglAsuhan1.setOpaque(false);
        TglAsuhan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhan1KeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan1);
        TglAsuhan1.setBounds(178, 1090, 90, 23);

        jLabel76.setText(":");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 1090, 174, 23);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("Tanggal Pelaksanaan");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(64, 1090, 130, 23);

        jLabel13.setText("Nama Tes :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(290, 1090, 70, 23);

        TglLahir20.setEditable(false);
        TglLahir20.setHighlighter(null);
        TglLahir20.setName("TglLahir20"); // NOI18N
        FormInput.add(TglLahir20);
        TglLahir20.setBounds(364, 1090, 490, 23);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("Hasil");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(64, 1120, 130, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 1120, 94, 23);

        TglLahir21.setEditable(false);
        TglLahir21.setHighlighter(null);
        TglLahir21.setName("TglLahir21"); // NOI18N
        FormInput.add(TglLahir21);
        TglLahir21.setBounds(98, 1120, 756, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("III. DIAGNOSA PSIKOLOGIS");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1230, 180, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 1230, 880, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 1230, 880, 1);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Kesimpulanpsikolog1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulanpsikolog1.setColumns(20);
        Kesimpulanpsikolog1.setRows(5);
        Kesimpulanpsikolog1.setName("Kesimpulanpsikolog1"); // NOI18N
        Kesimpulanpsikolog1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesimpulanpsikolog1KeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Kesimpulanpsikolog1);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1250, 810, 53);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("IV. MANIFESTASI FUNGSI PSIKOLOGIS");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(10, 1310, 220, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 1310, 880, 1);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1310, 880, 1);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        Kesimpulanpsikolog2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulanpsikolog2.setColumns(20);
        Kesimpulanpsikolog2.setRows(5);
        Kesimpulanpsikolog2.setName("Kesimpulanpsikolog2"); // NOI18N
        Kesimpulanpsikolog2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesimpulanpsikolog2KeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(Kesimpulanpsikolog2);

        FormInput.add(scrollPane16);
        scrollPane16.setBounds(44, 1330, 810, 53);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("V. RENCANA INTERVENSI");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(10, 1390, 220, 23);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1390, 880, 1);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1390, 880, 1);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        Kesimpulanpsikolog3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulanpsikolog3.setColumns(20);
        Kesimpulanpsikolog3.setRows(5);
        Kesimpulanpsikolog3.setName("Kesimpulanpsikolog3"); // NOI18N
        Kesimpulanpsikolog3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesimpulanpsikolog3KeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(Kesimpulanpsikolog3);

        FormInput.add(scrollPane17);
        scrollPane17.setBounds(44, 1410, 810, 53);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("VI. PELAKSANAAN INTERVENSI");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 1470, 220, 23);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 1470, 880, 1);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 1470, 880, 1);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("Sesi");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(44, 1490, 30, 23);

        TglLahir22.setEditable(false);
        TglLahir22.setHighlighter(null);
        TglLahir22.setName("TglLahir22"); // NOI18N
        FormInput.add(TglLahir22);
        TglLahir22.setBounds(75, 1520, 380, 23);

        TglLahir23.setEditable(false);
        TglLahir23.setHighlighter(null);
        TglLahir23.setName("TglLahir23"); // NOI18N
        FormInput.add(TglLahir23);
        TglLahir23.setBounds(474, 1520, 380, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Tahapan Intervensi");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(75, 1490, 380, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Target Terapi");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(474, 1490, 380, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(44, 1511, 810, 1);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(44, 1511, 810, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("1.");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(44, 1520, 30, 23);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("3.");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(44, 1580, 30, 23);

        TglLahir24.setEditable(false);
        TglLahir24.setHighlighter(null);
        TglLahir24.setName("TglLahir24"); // NOI18N
        FormInput.add(TglLahir24);
        TglLahir24.setBounds(75, 1580, 380, 23);

        TglLahir25.setEditable(false);
        TglLahir25.setHighlighter(null);
        TglLahir25.setName("TglLahir25"); // NOI18N
        FormInput.add(TglLahir25);
        TglLahir25.setBounds(474, 1580, 380, 23);

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setText("2.");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(44, 1550, 30, 23);

        TglLahir26.setEditable(false);
        TglLahir26.setHighlighter(null);
        TglLahir26.setName("TglLahir26"); // NOI18N
        FormInput.add(TglLahir26);
        TglLahir26.setBounds(75, 1550, 380, 23);

        TglLahir27.setEditable(false);
        TglLahir27.setHighlighter(null);
        TglLahir27.setName("TglLahir27"); // NOI18N
        FormInput.add(TglLahir27);
        TglLahir27.setBounds(474, 1550, 380, 23);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("4.");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(44, 1610, 30, 23);

        TglLahir28.setEditable(false);
        TglLahir28.setHighlighter(null);
        TglLahir28.setName("TglLahir28"); // NOI18N
        FormInput.add(TglLahir28);
        TglLahir28.setBounds(75, 1610, 380, 23);

        TglLahir29.setEditable(false);
        TglLahir29.setHighlighter(null);
        TglLahir29.setName("TglLahir29"); // NOI18N
        FormInput.add(TglLahir29);
        TglLahir29.setBounds(474, 1610, 380, 23);

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setText("5.");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(44, 1640, 30, 23);

        TglLahir30.setEditable(false);
        TglLahir30.setHighlighter(null);
        TglLahir30.setName("TglLahir30"); // NOI18N
        FormInput.add(TglLahir30);
        TglLahir30.setBounds(75, 1640, 380, 23);

        TglLahir31.setEditable(false);
        TglLahir31.setHighlighter(null);
        TglLahir31.setName("TglLahir31"); // NOI18N
        FormInput.add(TglLahir31);
        TglLahir31.setBounds(474, 1640, 380, 23);

        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel146.setText("6.");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(44, 1670, 30, 23);

        TglLahir32.setEditable(false);
        TglLahir32.setHighlighter(null);
        TglLahir32.setName("TglLahir32"); // NOI18N
        FormInput.add(TglLahir32);
        TglLahir32.setBounds(75, 1670, 380, 23);

        TglLahir33.setEditable(false);
        TglLahir33.setHighlighter(null);
        TglLahir33.setName("TglLahir33"); // NOI18N
        FormInput.add(TglLahir33);
        TglLahir33.setBounds(474, 1670, 380, 23);

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("7.");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(44, 1700, 30, 23);

        TglLahir34.setEditable(false);
        TglLahir34.setHighlighter(null);
        TglLahir34.setName("TglLahir34"); // NOI18N
        FormInput.add(TglLahir34);
        TglLahir34.setBounds(75, 1700, 380, 23);

        TglLahir35.setEditable(false);
        TglLahir35.setHighlighter(null);
        TglLahir35.setName("TglLahir35"); // NOI18N
        FormInput.add(TglLahir35);
        TglLahir35.setBounds(474, 1700, 380, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("VII. EVALUASI");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(10, 1730, 220, 23);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 1730, 880, 1);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(0, 1730, 880, 1);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        Kesimpulanpsikolog4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulanpsikolog4.setColumns(20);
        Kesimpulanpsikolog4.setRows(5);
        Kesimpulanpsikolog4.setName("Kesimpulanpsikolog4"); // NOI18N
        Kesimpulanpsikolog4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesimpulanpsikolog4KeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Kesimpulanpsikolog4);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(44, 1750, 810, 63);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-12-2024" }));
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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
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
            if(Sequel.menyimpantf("penilaian_psikologi_klinis","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
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
            Valid.pindah(evt,Kesimpulanpsikolog,BtnBatal);
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
            Valid.textKosong(BtnDokter,"Petugas");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Petugas</b></td>"+
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

    private void KesimpulanpsikologKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanpsikologKeyPressed
        //.pindah2(evt,Psikodinamika,BtnSimpan);
    }//GEN-LAST:event_KesimpulanpsikologKeyPressed

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
            
            Valid.MyReportqry("rptCetakPenilaianPsikolog.jasper","report","::[ Laporan Penilaian Psikolog ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where penilaian_psikologi_klinis.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void RupaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RupaKeyPressed
        //Valid.pindah(evt,Dikirimdari,Pakaian);
    }//GEN-LAST:event_RupaKeyPressed

    private void DikirimdariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DikirimdariKeyPressed
        Valid.pindah(evt,KetAlloAuto,Rupa);
    }//GEN-LAST:event_DikirimdariKeyPressed

    private void TujuanPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanPemeriksaanKeyPressed
        Valid.pindah(evt,TglAsuhan,Informasi);
    }//GEN-LAST:event_TujuanPemeriksaanKeyPressed

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
        Valid.pindah(evt,Kesimpulanpsikolog,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TujuanPemeriksaan,KetAlloAuto);
    }//GEN-LAST:event_InformasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDokter,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void KetAlloAutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetAlloAutoKeyPressed
        Valid.pindah2(evt,Informasi,Dikirimdari);
    }//GEN-LAST:event_KetAlloAutoKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        //Valid.pindah2(evt,Terapi,Edukasi);
    }//GEN-LAST:event_TindakanKeyPressed

    private void Tindakan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tindakan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tindakan1KeyPressed

    private void Tindakan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tindakan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tindakan2KeyPressed

    private void Tindakan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tindakan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tindakan3KeyPressed

    private void TglAsuhan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhan1KeyPressed

    private void Kesimpulanpsikolog1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesimpulanpsikolog1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesimpulanpsikolog1KeyPressed

    private void Kesimpulanpsikolog2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesimpulanpsikolog2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesimpulanpsikolog2KeyPressed

    private void Kesimpulanpsikolog3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesimpulanpsikolog3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesimpulanpsikolog3KeyPressed

    private void Kesimpulanpsikolog4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesimpulanpsikolog4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesimpulanpsikolog4KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPsikologiKlinis dialog = new RMPenilaianPsikologiKlinis(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Dikirimdari;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextArea Kesimpulanpsikolog;
    private widget.TextArea Kesimpulanpsikolog1;
    private widget.TextArea Kesimpulanpsikolog2;
    private widget.TextArea Kesimpulanpsikolog3;
    private widget.TextArea Kesimpulanpsikolog4;
    private javax.swing.JTextArea KetAlloAuto;
    private widget.TextArea KetLokalis;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Rupa;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglAsuhan1;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahir1;
    private widget.TextBox TglLahir10;
    private widget.TextBox TglLahir11;
    private widget.TextBox TglLahir12;
    private widget.TextBox TglLahir13;
    private widget.TextBox TglLahir14;
    private widget.TextBox TglLahir15;
    private widget.TextBox TglLahir16;
    private widget.TextBox TglLahir17;
    private widget.TextBox TglLahir18;
    private widget.TextBox TglLahir19;
    private widget.TextBox TglLahir2;
    private widget.TextBox TglLahir20;
    private widget.TextBox TglLahir21;
    private widget.TextBox TglLahir22;
    private widget.TextBox TglLahir23;
    private widget.TextBox TglLahir24;
    private widget.TextBox TglLahir25;
    private widget.TextBox TglLahir26;
    private widget.TextBox TglLahir27;
    private widget.TextBox TglLahir28;
    private widget.TextBox TglLahir29;
    private widget.TextBox TglLahir3;
    private widget.TextBox TglLahir30;
    private widget.TextBox TglLahir31;
    private widget.TextBox TglLahir32;
    private widget.TextBox TglLahir33;
    private widget.TextBox TglLahir34;
    private widget.TextBox TglLahir35;
    private widget.TextBox TglLahir4;
    private widget.TextBox TglLahir5;
    private widget.TextBox TglLahir6;
    private widget.TextBox TglLahir7;
    private widget.TextBox TglLahir8;
    private widget.TextBox TglLahir9;
    private widget.TextArea Tindakan;
    private widget.TextArea Tindakan1;
    private widget.TextArea Tindakan2;
    private widget.TextArea Tindakan3;
    private widget.ComboBox TujuanPemeriksaan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel11;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
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
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel42;
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
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane19;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.ScrollPane scrollPane22;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where "+
                        "penilaian_psikologi_klinis.tanggal between ? and ? order by penilaian_psikologi_klinis.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_psikologi_klinis.tanggal,"+
                        "penilaian_psikologi_klinis.nip,penilaian_psikologi_klinis.anamnesis,penilaian_psikologi_klinis.dikirim_dari,penilaian_psikologi_klinis.tujuan_pemeriksaan,penilaian_psikologi_klinis.ket_anamnesis,penilaian_psikologi_klinis.rupa,penilaian_psikologi_klinis.bentuk_tubuh,penilaian_psikologi_klinis.tindakan,"+
                        "penilaian_psikologi_klinis.pakaian,penilaian_psikologi_klinis.ekspresi,penilaian_psikologi_klinis.berbicara,penilaian_psikologi_klinis.penggunaan_kata,penilaian_psikologi_klinis.ciri_menyolok,penilaian_psikologi_klinis.hasil_psikotes,penilaian_psikologi_klinis.kepribadian,penilaian_psikologi_klinis.psikodinamika,penilaian_psikologi_klinis.kesimpulan_psikolog,petugas.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_psikologi_klinis on reg_periksa.no_rawat=penilaian_psikologi_klinis.no_rawat "+
                        "inner join petugas on penilaian_psikologi_klinis.nip=petugas.nip where "+
                        "penilaian_psikologi_klinis.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_psikologi_klinis.nip like ? or petugas.nama like ?) order by penilaian_psikologi_klinis.tanggal");
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
        Informasi.setSelectedIndex(0);
        Dikirimdari.setSelectedIndex(0);
        TujuanPemeriksaan.setSelectedIndex(0);
        KetAlloAuto.setText("");
        Rupa.setSelectedIndex(0);
        Kesimpulanpsikolog.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        TujuanPemeriksaan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Dikirimdari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TujuanPemeriksaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KetAlloAuto.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Rupa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Kesimpulanpsikolog.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnHapus.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnEdit.setEnabled(akses.getpenilaian_psikologi_klinis());
        BtnEdit.setEnabled(akses.getpenilaian_psikologi_klinis());
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
        if(Sequel.queryu2tf("delete from penilaian_psikologi_klinis where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_psikologi_klinis","no_rawat=?","no_rawat=?,tanggal=?,nip=?,anamnesis=?,dikirim_dari=?,tujuan_pemeriksaan=?,ket_anamnesis=?,rupa=?,bentuk_tubuh=?,tindakan=?,pakaian=?,ekspresi=?,berbicara=?,penggunaan_kata=?,ciri_menyolok=?,hasil_psikotes=?,kepribadian=?,psikodinamika=?,kesimpulan_psikolog=?",20,new String[]{
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

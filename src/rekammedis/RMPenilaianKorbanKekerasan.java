/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
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
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianKorbanKekerasan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPegawai pemeriksa=new DlgCariPegawai(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianKorbanKekerasan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode/NIP","Nama Pemeriksa","Tanggal","Anamnesis","Hubungan",
            "Jml.Saudara","Kondisi Keluarga Inti","Hubungan Orang Terdekat","Jenis Kekerasan/Penganiayaan Yang Dialami",
            "Tempat Kejadian Kekerasan","Lama","Periode","Seberapa Sering Mengalami Kekerasan / Penganiayaan","Pemicu Kekerasan / Penganiayaan",
            "Yang Melakukan Kekerasan","Dampak Yang Terjadi Pada Korban Kekerasan","Tanda-tanda Yang Didapatkan Pada Korban Kekerasan",
            "Memerlukan Pendampingan","Riwayat Kelainan/Gangguan Jiwa","Pemeriksaan Kepala","Pemeriksaan Thoraks","Pemeriksaan Leher",
            "Pemeriksaan Abdomen","Pemeriksaan Genitalia","Pemeriksaan Ekstrimitas Atas","Pemeriksaan Ekstrimitas Bawah","Pemeriksaan Anus"
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
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(69);
            }else if(i==11){
                column.setPreferredWidth(108);
            }else if(i==12){
                column.setPreferredWidth(137);
            }else if(i==13){
                column.setPreferredWidth(240);
            }else if(i==14){
                column.setPreferredWidth(145);
            }else if(i==15){
                column.setPreferredWidth(37);
            }else if(i==16){
                column.setPreferredWidth(45);
            }else if(i==17){
                column.setPreferredWidth(277);
            }else if(i==18){
                column.setPreferredWidth(177);
            }else if(i==19){
                column.setPreferredWidth(143);
            }else if(i==20){
                column.setPreferredWidth(235);
            }else if(i==21){
                column.setPreferredWidth(275);
            }else if(i==22){
                column.setPreferredWidth(142);
            }else if(i==23){
                column.setPreferredWidth(173);
            }else if(i==24){
                column.setPreferredWidth(160);
            }else if(i==25){
                column.setPreferredWidth(160);
            }else if(i==26){
                column.setPreferredWidth(160);
            }else if(i==27){
                column.setPreferredWidth(160);
            }else if(i==28){
                column.setPreferredWidth(160);
            }else if(i==29){
                column.setPreferredWidth(160);
            }else if(i==30){
                column.setPreferredWidth(160);
            }else if(i==31){
                column.setPreferredWidth(160);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        JumlahSaudara.setDocument(new batasInput((byte)2).getKata(JumlahSaudara));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KekerasanYangDialami.setDocument(new batasInput((int)350).getKata(KekerasanYangDialami));
        TempatKejadian.setDocument(new batasInput((byte)40).getKata(TempatKejadian));
        LamaKekerasan.setDocument(new batasInput((byte)2).getKata(LamaKekerasan));
        JumlahSaudara.setDocument(new batasInput((byte)2).getKata(JumlahSaudara));
        SeberapaSeringPenganiayaan.setDocument(new batasInput((int)150).getKata(SeberapaSeringPenganiayaan));
        PemicuKekerasan.setDocument(new batasInput((int)150).getKata(PemicuKekerasan));
        SiapaYangMelakukanKekerasan.setDocument(new batasInput((int)50).getKata(SiapaYangMelakukanKekerasan));
        DampakYangTerjadi.setDocument(new batasInput((int)200).getKata(DampakYangTerjadi));
        TandaYangDidapatkan.setDocument(new batasInput((int)350).getKata(TandaYangDidapatkan));
        RiwayatKelainan.setDocument(new batasInput((int)50).getKata(RiwayatKelainan));
        PemeriksaanKepala.setDocument(new batasInput((int)50).getKata(PemeriksaanKepala));
        PemeriksaanThoraks.setDocument(new batasInput((int)50).getKata(PemeriksaanThoraks));
        PemeriksaanLeher.setDocument(new batasInput((int)50).getKata(PemeriksaanLeher));
        PemeriksaanAbdomen.setDocument(new batasInput((int)50).getKata(PemeriksaanAbdomen));
        PemeriksaanGenitalia.setDocument(new batasInput((int)50).getKata(PemeriksaanGenitalia));
        PemeriksaanEkstrimitasAtas.setDocument(new batasInput((int)50).getKata(PemeriksaanEkstrimitasAtas));
        PemeriksaanEkstrimitasBawah.setDocument(new batasInput((int)50).getKata(PemeriksaanEkstrimitasBawah));
        PemeriksaanAnus.setDocument(new batasInput((int)50).getKata(PemeriksaanAnus));
        
        
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
        
        pemeriksa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pemeriksa.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(pemeriksa.getTable().getValueAt(pemeriksa.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(pemeriksa.getTable().getValueAt(pemeriksa.getTable().getSelectedRow(),1).toString());
                    BtnDokter.requestFocus();
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
        MnPenilaianKorbanKekerasan = new javax.swing.JMenuItem();
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
        PemicuKekerasan = new widget.TextBox();
        SeberapaSeringPenganiayaan = new widget.TextBox();
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KekerasanYangDialami = new widget.TextArea();
        jLabel28 = new widget.Label();
        JumlahSaudara = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel39 = new widget.Label();
        KondisiKeluargaInti = new widget.ComboBox();
        PeriodeKekerasan = new widget.ComboBox();
        jLabel41 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel42 = new widget.Label();
        HubunganOrangTerdekat = new widget.ComboBox();
        jLabel29 = new widget.Label();
        TempatKejadian = new widget.TextBox();
        LamaKekerasan = new widget.TextBox();
        jLabel30 = new widget.Label();
        MemerlukanPendampingan = new widget.ComboBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        SiapaYangMelakukanKekerasan = new widget.TextBox();
        jLabel37 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        DampakYangTerjadi = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        TandaYangDidapatkan = new widget.TextArea();
        jLabel40 = new widget.Label();
        jLabel43 = new widget.Label();
        RiwayatKelainan = new widget.TextBox();
        jLabel44 = new widget.Label();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        jLabel31 = new widget.Label();
        PemeriksaanKepala = new widget.TextBox();
        jLabel32 = new widget.Label();
        PemeriksaanThoraks = new widget.TextBox();
        jLabel45 = new widget.Label();
        PemeriksaanLeher = new widget.TextBox();
        jLabel46 = new widget.Label();
        PemeriksaanAbdomen = new widget.TextBox();
        jLabel47 = new widget.Label();
        PemeriksaanGenitalia = new widget.TextBox();
        jLabel48 = new widget.Label();
        PemeriksaanEkstrimitasAtas = new widget.TextBox();
        jLabel49 = new widget.Label();
        PemeriksaanEkstrimitasBawah = new widget.TextBox();
        jLabel50 = new widget.Label();
        PemeriksaanAnus = new widget.TextBox();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
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

        MnPenilaianKorbanKekerasan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianKorbanKekerasan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianKorbanKekerasan.setText("Laporan Penilaian Korban Kekerasan");
        MnPenilaianKorbanKekerasan.setName("MnPenilaianKorbanKekerasan"); // NOI18N
        MnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianKorbanKekerasan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Pasien Korban Kekerasan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 773));
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

        label14.setText("Diperiksa :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.setBounds(348, 40, 28, 23);

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

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        PemicuKekerasan.setFocusTraversalPolicyProvider(true);
        PemicuKekerasan.setName("PemicuKekerasan"); // NOI18N
        PemicuKekerasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemicuKekerasanKeyPressed(evt);
            }
        });
        FormInput.add(PemicuKekerasan);
        PemicuKekerasan.setBounds(16, 280, 838, 23);

        SeberapaSeringPenganiayaan.setFocusTraversalPolicyProvider(true);
        SeberapaSeringPenganiayaan.setName("SeberapaSeringPenganiayaan"); // NOI18N
        SeberapaSeringPenganiayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeberapaSeringPenganiayaanKeyPressed(evt);
            }
        });
        FormInput.add(SeberapaSeringPenganiayaan);
        SeberapaSeringPenganiayaan.setBounds(16, 230, 838, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KekerasanYangDialami.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KekerasanYangDialami.setColumns(20);
        KekerasanYangDialami.setRows(5);
        KekerasanYangDialami.setName("KekerasanYangDialami"); // NOI18N
        KekerasanYangDialami.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KekerasanYangDialamiKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KekerasanYangDialami);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(16, 130, 838, 43);

        jLabel28.setText(":");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(1, 80, 100, 23);

        JumlahSaudara.setFocusTraversalPolicyProvider(true);
        JumlahSaudara.setName("JumlahSaudara"); // NOI18N
        JumlahSaudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahSaudaraKeyPressed(evt);
            }
        });
        FormInput.add(JumlahSaudara);
        JumlahSaudara.setBounds(105, 80, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Jenis Kekerasan/Penganiayaan Yang Dialami (Sebutkan) :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(16, 110, 300, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Apakah Memerlukan Pendampingan");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(16, 480, 190, 23);

        KondisiKeluargaInti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bahagia", "Broken Home" }));
        KondisiKeluargaInti.setName("KondisiKeluargaInti"); // NOI18N
        KondisiKeluargaInti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeluargaIntiKeyPressed(evt);
            }
        });
        FormInput.add(KondisiKeluargaInti);
        KondisiKeluargaInti.setBounds(336, 80, 125, 23);

        PeriodeKekerasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hari", "Bulan", "Tahun" }));
        PeriodeKekerasan.setName("PeriodeKekerasan"); // NOI18N
        PeriodeKekerasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeriodeKekerasanKeyPressed(evt);
            }
        });
        FormInput.add(PeriodeKekerasan);
        PeriodeKekerasan.setBounds(769, 180, 85, 23);

        jLabel41.setText("Kondisi Keluarga Inti :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(205, 80, 127, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2023 14:36:24" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(436, 40, 130, 23);

        jLabel42.setText("Hubungan Dengan Orang Terdekat :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(500, 80, 200, 23);

        HubunganOrangTerdekat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Masalah", "Ada Masalah" }));
        HubunganOrangTerdekat.setName("HubunganOrangTerdekat"); // NOI18N
        HubunganOrangTerdekat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganOrangTerdekatKeyPressed(evt);
            }
        });
        FormInput.add(HubunganOrangTerdekat);
        HubunganOrangTerdekat.setBounds(704, 80, 150, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Tempat Kejadian Kekerasan");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(16, 180, 150, 23);

        TempatKejadian.setFocusTraversalPolicyProvider(true);
        TempatKejadian.setName("TempatKejadian"); // NOI18N
        TempatKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatKejadianKeyPressed(evt);
            }
        });
        FormInput.add(TempatKejadian);
        TempatKejadian.setBounds(162, 180, 260, 23);

        LamaKekerasan.setFocusTraversalPolicyProvider(true);
        LamaKekerasan.setName("LamaKekerasan"); // NOI18N
        LamaKekerasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaKekerasanKeyPressed(evt);
            }
        });
        FormInput.add(LamaKekerasan);
        LamaKekerasan.setBounds(696, 180, 70, 23);

        jLabel30.setText("Lama Mengalami Kekerasan / Penganiayaan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(442, 180, 250, 23);

        MemerlukanPendampingan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MemerlukanPendampingan.setName("MemerlukanPendampingan"); // NOI18N
        MemerlukanPendampingan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MemerlukanPendampinganKeyPressed(evt);
            }
        });
        FormInput.add(MemerlukanPendampingan);
        MemerlukanPendampingan.setBounds(200, 480, 80, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Seberapa Sering Mengalami Kekerasan / Penganiayaan : ");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(16, 210, 300, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Apakah Ada Waktu Tertentu/Pemicu Kekerasan / Penganiayaan :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(16, 260, 330, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Siapa Yang Melakukan Kekerasan / Penganiayaan");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(16, 310, 250, 23);

        SiapaYangMelakukanKekerasan.setFocusTraversalPolicyProvider(true);
        SiapaYangMelakukanKekerasan.setName("SiapaYangMelakukanKekerasan"); // NOI18N
        SiapaYangMelakukanKekerasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SiapaYangMelakukanKekerasanKeyPressed(evt);
            }
        });
        FormInput.add(SiapaYangMelakukanKekerasan);
        SiapaYangMelakukanKekerasan.setBounds(266, 310, 588, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Dampak Yang Terjadi Pada Korban Kekerasan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(16, 340, 250, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        DampakYangTerjadi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DampakYangTerjadi.setColumns(20);
        DampakYangTerjadi.setRows(5);
        DampakYangTerjadi.setName("DampakYangTerjadi"); // NOI18N
        DampakYangTerjadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DampakYangTerjadiKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(DampakYangTerjadi);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(16, 360, 838, 43);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TandaYangDidapatkan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TandaYangDidapatkan.setColumns(20);
        TandaYangDidapatkan.setRows(5);
        TandaYangDidapatkan.setName("TandaYangDidapatkan"); // NOI18N
        TandaYangDidapatkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TandaYangDidapatkanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TandaYangDidapatkan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(16, 430, 838, 43);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Tanda-tanda Yang Didapatkan Pada Korban Kekerasan :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(16, 410, 290, 23);

        jLabel43.setText("Riwayat Kelainan / Gangguan Jiwa :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(320, 480, 190, 23);

        RiwayatKelainan.setFocusTraversalPolicyProvider(true);
        RiwayatKelainan.setName("RiwayatKelainan"); // NOI18N
        RiwayatKelainan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKelainanKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatKelainan);
        RiwayatKelainan.setBounds(514, 480, 340, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Pemeriksaan Fisik :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(16, 510, 132, 23);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/fisiobody.png"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall1.setRound(false);
        PanelWall1.setLayout(null);
        FormInput.add(PanelWall1);
        PanelWall1.setBounds(50, 530, 290, 243);

        jLabel31.setText("Kepala :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(350, 530, 136, 23);

        PemeriksaanKepala.setFocusTraversalPolicyProvider(true);
        PemeriksaanKepala.setName("PemeriksaanKepala"); // NOI18N
        PemeriksaanKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanKepalaKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanKepala);
        PemeriksaanKepala.setBounds(490, 530, 364, 23);

        jLabel32.setText("Thoraks :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(350, 560, 136, 23);

        PemeriksaanThoraks.setFocusTraversalPolicyProvider(true);
        PemeriksaanThoraks.setName("PemeriksaanThoraks"); // NOI18N
        PemeriksaanThoraks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanThoraksKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanThoraks);
        PemeriksaanThoraks.setBounds(490, 560, 364, 23);

        jLabel45.setText("Leher :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(350, 590, 136, 23);

        PemeriksaanLeher.setFocusTraversalPolicyProvider(true);
        PemeriksaanLeher.setName("PemeriksaanLeher"); // NOI18N
        PemeriksaanLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanLeherKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanLeher);
        PemeriksaanLeher.setBounds(490, 590, 364, 23);

        jLabel46.setText("Abdomen :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(350, 620, 136, 23);

        PemeriksaanAbdomen.setFocusTraversalPolicyProvider(true);
        PemeriksaanAbdomen.setName("PemeriksaanAbdomen"); // NOI18N
        PemeriksaanAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanAbdomen);
        PemeriksaanAbdomen.setBounds(490, 620, 364, 23);

        jLabel47.setText("Genitalia :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(350, 650, 136, 23);

        PemeriksaanGenitalia.setFocusTraversalPolicyProvider(true);
        PemeriksaanGenitalia.setName("PemeriksaanGenitalia"); // NOI18N
        PemeriksaanGenitalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanGenitaliaKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanGenitalia);
        PemeriksaanGenitalia.setBounds(490, 650, 364, 23);

        jLabel48.setText("Ekstremitas Atas :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(350, 680, 136, 23);

        PemeriksaanEkstrimitasAtas.setFocusTraversalPolicyProvider(true);
        PemeriksaanEkstrimitasAtas.setName("PemeriksaanEkstrimitasAtas"); // NOI18N
        PemeriksaanEkstrimitasAtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanEkstrimitasAtasKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanEkstrimitasAtas);
        PemeriksaanEkstrimitasAtas.setBounds(490, 680, 364, 23);

        jLabel49.setText("Ekstremitas Bawah :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(350, 710, 136, 23);

        PemeriksaanEkstrimitasBawah.setFocusTraversalPolicyProvider(true);
        PemeriksaanEkstrimitasBawah.setName("PemeriksaanEkstrimitasBawah"); // NOI18N
        PemeriksaanEkstrimitasBawah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanEkstrimitasBawahKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanEkstrimitasBawah);
        PemeriksaanEkstrimitasBawah.setBounds(490, 710, 364, 23);

        jLabel50.setText("Anus :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(350, 740, 136, 23);

        PemeriksaanAnus.setFocusTraversalPolicyProvider(true);
        PemeriksaanAnus.setName("PemeriksaanAnus"); // NOI18N
        PemeriksaanAnus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanAnusKeyPressed(evt);
            }
        });
        FormInput.add(PemeriksaanAnus);
        PemeriksaanAnus.setBounds(490, 740, 364, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Jumlah Saudara");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(16, 80, 90, 23);

        jLabel52.setText(":");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 180, 158, 23);

        jLabel53.setText(":");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 310, 262, 23);

        jLabel54.setText(":");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 480, 196, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2023" }));
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Pemeriksa");
        }else if(KekerasanYangDialami.getText().trim().equals("")){
            Valid.textKosong(KekerasanYangDialami,"Jenis Kekerasan/Penganiayaan Yang Dialami");
        }else if(TempatKejadian.getText().trim().equals("")){
            Valid.textKosong(TempatKejadian,"Tempat Kejadian Kekerasan");
        }else if(LamaKekerasan.getText().trim().equals("")){
            Valid.textKosong(LamaKekerasan,"Lama Mengalami Kekerasan / Penganiayaan");
        }else if(SeberapaSeringPenganiayaan.getText().trim().equals("")){
            Valid.textKosong(SeberapaSeringPenganiayaan,"Seberapa Sering Mengalami Kekerasan / Penganiayaan");
        }else if(PemicuKekerasan.getText().trim().equals("")){
            Valid.textKosong(PemicuKekerasan,"Pemicu Kekerasan");
        }else if(SiapaYangMelakukanKekerasan.getText().trim().equals("")){
            Valid.textKosong(SiapaYangMelakukanKekerasan,"Siapa Yang Melakukan Kekerasan");
        }else if(DampakYangTerjadi.getText().trim().equals("")){
            Valid.textKosong(DampakYangTerjadi,"Dampak Yang Terjadi Pada Korban Kekerasan");
        }else if(TandaYangDidapatkan.getText().trim().equals("")){
            Valid.textKosong(TandaYangDidapatkan,"Tanda-tanda Yang Didapatkan Pada Korban Kekerasan");
        }else{
            if(Sequel.menyimpantf("penilaian_korban_kekerasan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",27,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),
                    Hubungan.getText(),JumlahSaudara.getText(),KondisiKeluargaInti.getSelectedItem().toString(),HubunganOrangTerdekat.getSelectedItem().toString(),KekerasanYangDialami.getText(), 
                    TempatKejadian.getText(),LamaKekerasan.getText(),PeriodeKekerasan.getSelectedItem().toString(),SeberapaSeringPenganiayaan.getText(),PemicuKekerasan.getText(), 
                    SiapaYangMelakukanKekerasan.getText(),DampakYangTerjadi.getText(),TandaYangDidapatkan.getText(),MemerlukanPendampingan.getSelectedItem().toString(),RiwayatKelainan.getText(), 
                    PemeriksaanKepala.getText(),PemeriksaanThoraks.getText(),PemeriksaanLeher.getText(),PemeriksaanAbdomen.getText(),PemeriksaanGenitalia.getText(),
                    PemeriksaanEkstrimitasAtas.getText(),PemeriksaanEkstrimitasBawah.getText(),PemeriksaanAnus.getText(),KdDokter.getText()
                })==true){
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,PemeriksaanAnus,BtnBatal);
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
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh pemeriksa yang bersangkutan..!!");
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
            Valid.textKosong(BtnDokter,"Pemeriksa");
        }else if(KekerasanYangDialami.getText().trim().equals("")){
            Valid.textKosong(KekerasanYangDialami,"Jenis Kekerasan/Penganiayaan Yang Dialami");
        }else if(TempatKejadian.getText().trim().equals("")){
            Valid.textKosong(TempatKejadian,"Tempat Kejadian Kekerasan");
        }else if(LamaKekerasan.getText().trim().equals("")){
            Valid.textKosong(LamaKekerasan,"Lama Mengalami Kekerasan / Penganiayaan");
        }else if(SeberapaSeringPenganiayaan.getText().trim().equals("")){
            Valid.textKosong(SeberapaSeringPenganiayaan,"Seberapa Sering Mengalami Kekerasan / Penganiayaan");
        }else if(PemicuKekerasan.getText().trim().equals("")){
            Valid.textKosong(PemicuKekerasan,"Pemicu Kekerasan");
        }else if(SiapaYangMelakukanKekerasan.getText().trim().equals("")){
            Valid.textKosong(SiapaYangMelakukanKekerasan,"Siapa Yang Melakukan Kekerasan");
        }else if(DampakYangTerjadi.getText().trim().equals("")){
            Valid.textKosong(DampakYangTerjadi,"Dampak Yang Terjadi Pada Korban Kekerasan");
        }else if(TandaYangDidapatkan.getText().trim().equals("")){
            Valid.textKosong(TandaYangDidapatkan,"Tanda-tanda Yang Didapatkan Pada Korban Kekerasan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh pemeriksa yang bersangkutan..!!");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode/NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pemeriksa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jml.Saudara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kondisi Keluarga Inti</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan Orang Terdekat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jenis Kekerasan/Penganiayaan Yang Dialami</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tempat Kejadian Kekerasan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Periode</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Seberapa Sering Mengalami Kekerasan / Penganiayaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemicu Kekerasan / Penganiayaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Yang Melakukan Kekerasan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dampak Yang Terjadi Pada Korban Kekerasan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanda-tanda Yang Didapatkan Pada Korban Kekerasan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Memerlukan Pendampingan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Kelainan/Gangguan Jiwa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Thoraks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Genitalia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Ekstrimitas Atas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Ekstrimitas Bawah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemeriksaan Anus</b></td>"+
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
                      "<table width='3800px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianKorbanKekerasan.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3800px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN KORBAN KEKERASAN<br><br></font>"+        
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

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        pemeriksa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pemeriksa.setLocationRelativeTo(internalFrame1);
        pemeriksa.setAlwaysOnTop(false);
        pemeriksa.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,BtnSimpan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void PemicuKekerasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemicuKekerasanKeyPressed
        Valid.pindah(evt,SeberapaSeringPenganiayaan,SiapaYangMelakukanKekerasan);
    }//GEN-LAST:event_PemicuKekerasanKeyPressed

    private void SeberapaSeringPenganiayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeberapaSeringPenganiayaanKeyPressed
        Valid.pindah(evt,PeriodeKekerasan,PemicuKekerasan);
    }//GEN-LAST:event_SeberapaSeringPenganiayaanKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KekerasanYangDialamiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KekerasanYangDialamiKeyPressed
        Valid.pindah2(evt,HubunganOrangTerdekat,TempatKejadian);
    }//GEN-LAST:event_KekerasanYangDialamiKeyPressed

    private void JumlahSaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahSaudaraKeyPressed
        Valid.pindah(evt,Hubungan,KondisiKeluargaInti);
    }//GEN-LAST:event_JumlahSaudaraKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KondisiKeluargaIntiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeluargaIntiKeyPressed
        Valid.pindah(evt,JumlahSaudara,HubunganOrangTerdekat);
    }//GEN-LAST:event_KondisiKeluargaIntiKeyPressed

    private void PeriodeKekerasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeriodeKekerasanKeyPressed
        Valid.pindah(evt,LamaKekerasan,SeberapaSeringPenganiayaan);
    }//GEN-LAST:event_PeriodeKekerasanKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDokter,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,JumlahSaudara);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianKorbanKekerasanActionPerformed
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
                param.put("lokalis",getClass().getResource("/picture/fisiobody.png").openStream());
            } catch (Exception e) {
            }
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptFormulirPenilaianKorbanKekerasan.jasper","report","::[ Formulir Penilaian Korban Kekerasan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_korban_kekerasan.nip,pegawai.nama,penilaian_korban_kekerasan.tanggal, "+
                "penilaian_korban_kekerasan.informasi,penilaian_korban_kekerasan.hubungan_dengan_pasien,penilaian_korban_kekerasan.jumlah_saudara,penilaian_korban_kekerasan.kondisi_keluaga,penilaian_korban_kekerasan.hubungan_orang_terdekat,"+
                "penilaian_korban_kekerasan.kekerasan_yang_dialami,penilaian_korban_kekerasan.tempat_kejadian,penilaian_korban_kekerasan.lama_kekerasan,penilaian_korban_kekerasan.periode_kekerasan,penilaian_korban_kekerasan.seberapa_sering_mengalami,"+
                "penilaian_korban_kekerasan.pemicu_kekerasan,penilaian_korban_kekerasan.yang_melakukan_kekerasan,penilaian_korban_kekerasan.dampak_kekerasan,penilaian_korban_kekerasan.tanda_tanda_didapatkan,penilaian_korban_kekerasan.memerlukan_pendampingan,"+
                "penilaian_korban_kekerasan.riwayat_kelainan,penilaian_korban_kekerasan.pemeriksaan_kepala,penilaian_korban_kekerasan.pemeriksaan_thoraks,penilaian_korban_kekerasan.pemeriksaan_leher,penilaian_korban_kekerasan.pemeriksaan_abdomen,"+
                "penilaian_korban_kekerasan.pemeriksaan_genitalia,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_atas,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_bawah,penilaian_korban_kekerasan.pemeriksaan_anus "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_korban_kekerasan on reg_periksa.no_rawat=penilaian_korban_kekerasan.no_rawat "+
                "inner join pegawai on penilaian_korban_kekerasan.nip=pegawai.nik where penilaian_korban_kekerasan.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianKorbanKekerasanActionPerformed

    private void HubunganOrangTerdekatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganOrangTerdekatKeyPressed
        Valid.pindah(evt,KondisiKeluargaInti,KekerasanYangDialami);
    }//GEN-LAST:event_HubunganOrangTerdekatKeyPressed

    private void TempatKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatKejadianKeyPressed
        Valid.pindah(evt,KekerasanYangDialami,LamaKekerasan);
    }//GEN-LAST:event_TempatKejadianKeyPressed

    private void LamaKekerasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaKekerasanKeyPressed
        Valid.pindah(evt,TempatKejadian,PeriodeKekerasan);
    }//GEN-LAST:event_LamaKekerasanKeyPressed

    private void MemerlukanPendampinganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MemerlukanPendampinganKeyPressed
        Valid.pindah(evt,TandaYangDidapatkan,RiwayatKelainan);
    }//GEN-LAST:event_MemerlukanPendampinganKeyPressed

    private void SiapaYangMelakukanKekerasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SiapaYangMelakukanKekerasanKeyPressed
        Valid.pindah(evt,PemicuKekerasan,DampakYangTerjadi);
    }//GEN-LAST:event_SiapaYangMelakukanKekerasanKeyPressed

    private void DampakYangTerjadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DampakYangTerjadiKeyPressed
        Valid.pindah2(evt,SiapaYangMelakukanKekerasan,TandaYangDidapatkan);
    }//GEN-LAST:event_DampakYangTerjadiKeyPressed

    private void TandaYangDidapatkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TandaYangDidapatkanKeyPressed
        Valid.pindah2(evt,DampakYangTerjadi,MemerlukanPendampingan);
    }//GEN-LAST:event_TandaYangDidapatkanKeyPressed

    private void RiwayatKelainanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKelainanKeyPressed
        Valid.pindah(evt,MemerlukanPendampingan,PemeriksaanKepala);
    }//GEN-LAST:event_RiwayatKelainanKeyPressed

    private void PemeriksaanKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanKepalaKeyPressed
        Valid.pindah(evt,RiwayatKelainan,PemeriksaanThoraks);
    }//GEN-LAST:event_PemeriksaanKepalaKeyPressed

    private void PemeriksaanThoraksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanThoraksKeyPressed
        Valid.pindah(evt,PemeriksaanKepala,PemeriksaanLeher);
    }//GEN-LAST:event_PemeriksaanThoraksKeyPressed

    private void PemeriksaanLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanLeherKeyPressed
        Valid.pindah(evt,PemeriksaanThoraks,PemeriksaanAbdomen);
    }//GEN-LAST:event_PemeriksaanLeherKeyPressed

    private void PemeriksaanAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanAbdomenKeyPressed
        Valid.pindah(evt,PemeriksaanLeher,PemeriksaanGenitalia);
    }//GEN-LAST:event_PemeriksaanAbdomenKeyPressed

    private void PemeriksaanGenitaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanGenitaliaKeyPressed
        Valid.pindah(evt,PemeriksaanAbdomen,PemeriksaanEkstrimitasAtas);
    }//GEN-LAST:event_PemeriksaanGenitaliaKeyPressed

    private void PemeriksaanEkstrimitasAtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanEkstrimitasAtasKeyPressed
        Valid.pindah(evt,PemeriksaanGenitalia,PemeriksaanEkstrimitasBawah);
    }//GEN-LAST:event_PemeriksaanEkstrimitasAtasKeyPressed

    private void PemeriksaanEkstrimitasBawahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanEkstrimitasBawahKeyPressed
        Valid.pindah(evt,PemeriksaanEkstrimitasAtas,PemeriksaanAnus);
    }//GEN-LAST:event_PemeriksaanEkstrimitasBawahKeyPressed

    private void PemeriksaanAnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanAnusKeyPressed
        Valid.pindah(evt,PemeriksaanEkstrimitasBawah,BtnSimpan);
    }//GEN-LAST:event_PemeriksaanAnusKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianKorbanKekerasan dialog = new RMPenilaianKorbanKekerasan(new javax.swing.JFrame(), true);
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
    private widget.TextArea DampakYangTerjadi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hubungan;
    private widget.ComboBox HubunganOrangTerdekat;
    private widget.TextBox Jk;
    private widget.TextBox JumlahSaudara;
    private widget.TextBox KdDokter;
    private widget.TextArea KekerasanYangDialami;
    private widget.ComboBox KondisiKeluargaInti;
    private widget.Label LCount;
    private widget.TextBox LamaKekerasan;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MemerlukanPendampingan;
    private javax.swing.JMenuItem MnPenilaianKorbanKekerasan;
    private widget.TextBox NmDokter;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.TextBox PemeriksaanAbdomen;
    private widget.TextBox PemeriksaanAnus;
    private widget.TextBox PemeriksaanEkstrimitasAtas;
    private widget.TextBox PemeriksaanEkstrimitasBawah;
    private widget.TextBox PemeriksaanGenitalia;
    private widget.TextBox PemeriksaanKepala;
    private widget.TextBox PemeriksaanLeher;
    private widget.TextBox PemeriksaanThoraks;
    private widget.TextBox PemicuKekerasan;
    private widget.ComboBox PeriodeKekerasan;
    private widget.TextBox RiwayatKelainan;
    private widget.ScrollPane Scroll;
    private widget.TextBox SeberapaSeringPenganiayaan;
    private widget.TextBox SiapaYangMelakukanKekerasan;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextArea TandaYangDidapatkan;
    private widget.TextBox TempatKejadian;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel28;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_korban_kekerasan.nip,pegawai.nama,penilaian_korban_kekerasan.tanggal, "+
                        "penilaian_korban_kekerasan.informasi,penilaian_korban_kekerasan.hubungan_dengan_pasien,penilaian_korban_kekerasan.jumlah_saudara,penilaian_korban_kekerasan.kondisi_keluaga,penilaian_korban_kekerasan.hubungan_orang_terdekat,"+
                        "penilaian_korban_kekerasan.kekerasan_yang_dialami,penilaian_korban_kekerasan.tempat_kejadian,penilaian_korban_kekerasan.lama_kekerasan,penilaian_korban_kekerasan.periode_kekerasan,penilaian_korban_kekerasan.seberapa_sering_mengalami,"+
                        "penilaian_korban_kekerasan.pemicu_kekerasan,penilaian_korban_kekerasan.yang_melakukan_kekerasan,penilaian_korban_kekerasan.dampak_kekerasan,penilaian_korban_kekerasan.tanda_tanda_didapatkan,penilaian_korban_kekerasan.memerlukan_pendampingan,"+
                        "penilaian_korban_kekerasan.riwayat_kelainan,penilaian_korban_kekerasan.pemeriksaan_kepala,penilaian_korban_kekerasan.pemeriksaan_thoraks,penilaian_korban_kekerasan.pemeriksaan_leher,penilaian_korban_kekerasan.pemeriksaan_abdomen,"+
                        "penilaian_korban_kekerasan.pemeriksaan_genitalia,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_atas,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_bawah,penilaian_korban_kekerasan.pemeriksaan_anus "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_korban_kekerasan on reg_periksa.no_rawat=penilaian_korban_kekerasan.no_rawat "+
                        "inner join pegawai on penilaian_korban_kekerasan.nip=pegawai.nik where "+
                        "penilaian_korban_kekerasan.tanggal between ? and ? order by penilaian_korban_kekerasan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_korban_kekerasan.nip,pegawai.nama,penilaian_korban_kekerasan.tanggal, "+
                        "penilaian_korban_kekerasan.informasi,penilaian_korban_kekerasan.hubungan_dengan_pasien,penilaian_korban_kekerasan.jumlah_saudara,penilaian_korban_kekerasan.kondisi_keluaga,penilaian_korban_kekerasan.hubungan_orang_terdekat,"+
                        "penilaian_korban_kekerasan.kekerasan_yang_dialami,penilaian_korban_kekerasan.tempat_kejadian,penilaian_korban_kekerasan.lama_kekerasan,penilaian_korban_kekerasan.periode_kekerasan,penilaian_korban_kekerasan.seberapa_sering_mengalami,"+
                        "penilaian_korban_kekerasan.pemicu_kekerasan,penilaian_korban_kekerasan.yang_melakukan_kekerasan,penilaian_korban_kekerasan.dampak_kekerasan,penilaian_korban_kekerasan.tanda_tanda_didapatkan,penilaian_korban_kekerasan.memerlukan_pendampingan,"+
                        "penilaian_korban_kekerasan.riwayat_kelainan,penilaian_korban_kekerasan.pemeriksaan_kepala,penilaian_korban_kekerasan.pemeriksaan_thoraks,penilaian_korban_kekerasan.pemeriksaan_leher,penilaian_korban_kekerasan.pemeriksaan_abdomen,"+
                        "penilaian_korban_kekerasan.pemeriksaan_genitalia,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_atas,penilaian_korban_kekerasan.pemeriksaan_ekstrimitas_bawah,penilaian_korban_kekerasan.pemeriksaan_anus "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_korban_kekerasan on reg_periksa.no_rawat=penilaian_korban_kekerasan.no_rawat "+
                        "inner join pegawai on penilaian_korban_kekerasan.nip=pegawai.nik where "+
                        "penilaian_korban_kekerasan.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_korban_kekerasan.nip like ? or pegawai.nama like ?) order by penilaian_korban_kekerasan.tanggal");
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
                        rs.getString("informasi"),rs.getString("hubungan_dengan_pasien"),rs.getString("jumlah_saudara"),rs.getString("kondisi_keluaga"),rs.getString("hubungan_orang_terdekat"),
                        rs.getString("kekerasan_yang_dialami"),rs.getString("tempat_kejadian"),rs.getString("lama_kekerasan"),rs.getString("periode_kekerasan"),rs.getString("seberapa_sering_mengalami"),
                        rs.getString("pemicu_kekerasan"),rs.getString("yang_melakukan_kekerasan"),rs.getString("dampak_kekerasan"),rs.getString("tanda_tanda_didapatkan"),rs.getString("memerlukan_pendampingan"),
                        rs.getString("riwayat_kelainan"),rs.getString("pemeriksaan_kepala"),rs.getString("pemeriksaan_thoraks"),rs.getString("pemeriksaan_leher"),rs.getString("pemeriksaan_abdomen"),
                        rs.getString("pemeriksaan_genitalia"),rs.getString("pemeriksaan_ekstrimitas_atas"),rs.getString("pemeriksaan_ekstrimitas_bawah"),rs.getString("pemeriksaan_anus")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        JumlahSaudara.setText("");
        KondisiKeluargaInti.setSelectedIndex(0);
        HubunganOrangTerdekat.setSelectedIndex(0);
        KekerasanYangDialami.setText("");
        TempatKejadian.setText("");
        LamaKekerasan.setText("");
        PeriodeKekerasan.setSelectedIndex(0);
        SeberapaSeringPenganiayaan.setText("");
        PemicuKekerasan.setText("");
        SiapaYangMelakukanKekerasan.setText("");
        DampakYangTerjadi.setText("");
        TandaYangDidapatkan.setText("");
        MemerlukanPendampingan.setSelectedIndex(0);
        RiwayatKelainan.setText("");
        PemeriksaanKepala.setText("");
        PemeriksaanLeher.setText("");
        PemeriksaanThoraks.setText("");
        PemeriksaanAbdomen.setText("");
        PemeriksaanGenitalia.setText("");
        PemeriksaanEkstrimitasAtas.setText("");
        PemeriksaanEkstrimitasBawah.setText("");
        PemeriksaanAnus.setText("");
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            JumlahSaudara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KondisiKeluargaInti.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            HubunganOrangTerdekat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KekerasanYangDialami.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TempatKejadian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            LamaKekerasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            PeriodeKekerasan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            SeberapaSeringPenganiayaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PemicuKekerasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            SiapaYangMelakukanKekerasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            DampakYangTerjadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TandaYangDidapatkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            MemerlukanPendampingan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            RiwayatKelainan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            PemeriksaanKepala.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            PemeriksaanThoraks.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            PemeriksaanLeher.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            PemeriksaanAbdomen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            PemeriksaanGenitalia.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            PemeriksaanEkstrimitasAtas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            PemeriksaanEkstrimitasBawah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            PemeriksaanAnus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_korban_kekerasan());
        BtnHapus.setEnabled(akses.getpenilaian_korban_kekerasan());
        BtnEdit.setEnabled(akses.getpenilaian_korban_kekerasan());
        BtnEdit.setEnabled(akses.getpenilaian_korban_kekerasan());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(pemeriksa.tampil3(KdDokter.getText()));
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_korban_kekerasan where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("penilaian_korban_kekerasan","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,hubungan_dengan_pasien=?,jumlah_saudara=?,kondisi_keluaga=?,hubungan_orang_terdekat=?,kekerasan_yang_dialami=?,tempat_kejadian=?,lama_kekerasan=?,"+
                "periode_kekerasan=?,seberapa_sering_mengalami=?,pemicu_kekerasan=?,yang_melakukan_kekerasan=?,dampak_kekerasan=?,tanda_tanda_didapatkan=?,memerlukan_pendampingan=?,riwayat_kelainan=?,pemeriksaan_kepala=?,pemeriksaan_thoraks=?,"+
                "pemeriksaan_leher=?,pemeriksaan_abdomen=?,pemeriksaan_genitalia=?,pemeriksaan_ekstrimitas_atas=?,pemeriksaan_ekstrimitas_bawah=?,pemeriksaan_anus=?,nip=?",28,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Anamnesis.getSelectedItem().toString(),
                Hubungan.getText(),JumlahSaudara.getText(),KondisiKeluargaInti.getSelectedItem().toString(),HubunganOrangTerdekat.getSelectedItem().toString(),KekerasanYangDialami.getText(), 
                TempatKejadian.getText(),LamaKekerasan.getText(),PeriodeKekerasan.getSelectedItem().toString(),SeberapaSeringPenganiayaan.getText(),PemicuKekerasan.getText(), 
                SiapaYangMelakukanKekerasan.getText(),DampakYangTerjadi.getText(),TandaYangDidapatkan.getText(),MemerlukanPendampingan.getSelectedItem().toString(),RiwayatKelainan.getText(), 
                PemeriksaanKepala.getText(),PemeriksaanThoraks.getText(),PemeriksaanLeher.getText(),PemeriksaanAbdomen.getText(),PemeriksaanGenitalia.getText(),
                PemeriksaanEkstrimitasAtas.getText(),PemeriksaanEkstrimitasBawah.getText(),PemeriksaanAnus.getText(),KdDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(JumlahSaudara.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(KondisiKeluargaInti.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(HubunganOrangTerdekat.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(KekerasanYangDialami.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(TempatKejadian.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(LamaKekerasan.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(PeriodeKekerasan.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(SeberapaSeringPenganiayaan.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(PemicuKekerasan.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(SiapaYangMelakukanKekerasan.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(DampakYangTerjadi.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(TandaYangDidapatkan.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(MemerlukanPendampingan.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(RiwayatKelainan.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(PemeriksaanKepala.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(PemeriksaanThoraks.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(PemeriksaanLeher.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(PemeriksaanAbdomen.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(PemeriksaanGenitalia.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(PemeriksaanEkstrimitasAtas.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(PemeriksaanEkstrimitasBawah.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(PemeriksaanAnus.getText(),tbObat.getSelectedRow(),31);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }
}

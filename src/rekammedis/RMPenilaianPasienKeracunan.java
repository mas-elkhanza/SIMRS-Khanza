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
public final class RMPenilaianPasienKeracunan extends javax.swing.JDialog {
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
    public RMPenilaianPasienKeracunan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan",
            "Tempat Kejadian","Keterangan Tempat Kejadian","Keluhan Yang Dirasakan Saat Ini","Riwayat Penyakit Sekarang", 
            "Hamil","Menyusui","Perkiraan Penyebab Keracunan","Nama Bahan","Jml Bahan","Tipe Pemaparan","Keterangan Tipe Pemaparan", 
            "Tipe Kejadian","Bau Bahan","Keterangan Bau Bahan","Pupil","Keterangan Pupil","Kesadaran","Tensi(mmHg)","Nadi(x/menit)",
            "RR(x/menit)","Suhu(°C)","SpO2(%)","Urine(cc/j)","Pengobatan Sebelum Ke IGD","Diagnosa","Pemeriksaan Penunjang", 
            "Penatalaksanaan Yang Diberikan","Tindak Lanjut"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
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
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeluhanSaatIni.setDocument(new batasInput((int)2000).getKata(KeluhanSaatIni));
        RiwayatPenyakitSatIni.setDocument(new batasInput((int)2000).getKata(RiwayatPenyakitSatIni));
        KeteranganTempatKejadian.setDocument(new batasInput((int)50).getKata(KeteranganTempatKejadian));
        NamaBahan.setDocument(new batasInput((int)100).getKata(NamaBahan));
        JumlahBahan.setDocument(new batasInput((int)5).getKata(JumlahBahan));
        KeteranganTipePemaparan.setDocument(new batasInput((int)50).getKata(KeteranganTipePemaparan));
        KeteranganBauBahan.setDocument(new batasInput((int)30).getKata(KeteranganBauBahan));
        KeteranganPupil.setDocument(new batasInput((int)30).getKata(KeteranganPupil));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        SPO.setDocument(new batasInput((int)5).getKata(SPO));
        Urine.setDocument(new batasInput((int)5).getKata(Urine));
        PengobatanSebelumIGD.setDocument(new batasInput((int)500).getKata(PengobatanSebelumIGD));
        Diagnosa.setDocument(new batasInput((int)500).getKata(Diagnosa));
        Penunjang.setDocument(new batasInput((int)500).getKata(Penunjang));
        Penatalaksanaan.setDocument(new batasInput((int)500).getKata(Penatalaksanaan));
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
        Anamnesis = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanSaatIni = new widget.TextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        KeteranganTempatKejadian = new widget.TextBox();
        TempatKejadian = new widget.ComboBox();
        jLabel47 = new widget.Label();
        jLabel41 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakitSatIni = new widget.TextArea();
        Hamil = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Menyusui = new widget.ComboBox();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        Penyebab = new widget.ComboBox();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        KeteranganBauBahan = new widget.TextBox();
        jLabel54 = new widget.Label();
        JumlahBahan = new widget.TextBox();
        jLabel55 = new widget.Label();
        TipeKejadian = new widget.ComboBox();
        NamaBahan = new widget.TextBox();
        jLabel56 = new widget.Label();
        BauBahan = new widget.ComboBox();
        jLabel57 = new widget.Label();
        TipePemaparan = new widget.ComboBox();
        KeteranganTipePemaparan = new widget.TextBox();
        Pupil = new widget.ComboBox();
        jLabel58 = new widget.Label();
        KeteranganPupil = new widget.TextBox();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel39 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel29 = new widget.Label();
        SPO = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel30 = new widget.Label();
        Urine = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel42 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        PengobatanSebelumIGD = new widget.TextArea();
        jLabel94 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel43 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Diagnosa = new widget.TextArea();
        jLabel83 = new widget.Label();
        jLabel85 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        scrollPane18 = new widget.ScrollPane();
        Penatalaksanaan = new widget.TextArea();
        jLabel61 = new widget.Label();
        jLabel63 = new widget.Label();
        TindakLanjut = new widget.ComboBox();
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
        MnPenilaianMedis.setText("Laporan Penilaian Pasien Penyakit Menular");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(260, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Pasien Keracunan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 743));
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

        KeluhanSaatIni.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanSaatIni.setColumns(20);
        KeluhanSaatIni.setRows(5);
        KeluhanSaatIni.setName("KeluhanSaatIni"); // NOI18N
        KeluhanSaatIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanSaatIniKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanSaatIni);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(44, 110, 810, 53);

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

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Keluhan Yang Dirasakan Saat Ini :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(44, 90, 360, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(380, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-05-2023 11:47:38" }));
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

        KeteranganTempatKejadian.setFocusTraversalPolicyProvider(true);
        KeteranganTempatKejadian.setName("KeteranganTempatKejadian"); // NOI18N
        KeteranganTempatKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTempatKejadianKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTempatKejadian);
        KeteranganTempatKejadian.setBounds(624, 250, 230, 23);

        TempatKejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rumah", "Kantor", "Tempat Kerja", "Tempat Hiburan", "Lain-lain" }));
        TempatKejadian.setName("TempatKejadian"); // NOI18N
        TempatKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatKejadianKeyPressed(evt);
            }
        });
        FormInput.add(TempatKejadian);
        TempatKejadian.setBounds(491, 250, 130, 23);

        jLabel47.setText("Tempat Kejadian :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(377, 250, 110, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Riwayat Penyakit Sekarang :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(44, 170, 360, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakitSatIni.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitSatIni.setColumns(20);
        RiwayatPenyakitSatIni.setRows(5);
        RiwayatPenyakitSatIni.setName("RiwayatPenyakitSatIni"); // NOI18N
        RiwayatPenyakitSatIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitSatIniKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RiwayatPenyakitSatIni);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(44, 190, 810, 53);

        Hamil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Hamil.setSelectedIndex(1);
        Hamil.setName("Hamil"); // NOI18N
        Hamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HamilKeyPressed(evt);
            }
        });
        FormInput.add(Hamil);
        Hamil.setBounds(83, 250, 80, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Hamil");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(44, 250, 50, 23);

        Menyusui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Menyusui.setSelectedIndex(1);
        Menyusui.setName("Menyusui"); // NOI18N
        Menyusui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenyusuiKeyPressed(evt);
            }
        });
        FormInput.add(Menyusui);
        Menyusui.setBounds(271, 250, 80, 23);

        jLabel49.setText("Menyusui :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(197, 250, 70, 23);

        jLabel50.setText(":");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 250, 79, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Penyebab");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(44, 280, 70, 23);

        Penyebab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAPZA", "Obat", "Obat Tradisional", "Makanan/Minuman", "Suplemen Makanan/Vitamin", "Kosmetik", "Bahan Kimia", "Pestisida", "Gigitan Ular", "Binatang Selain Ular", "Tumbuhan Beracun", "Pencemar Lingkungan", "Gas", "Tidak Diketahui" }));
        Penyebab.setName("Penyebab"); // NOI18N
        Penyebab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyebabKeyPressed(evt);
            }
        });
        FormInput.add(Penyebab);
        Penyebab.setBounds(102, 280, 190, 23);

        jLabel52.setText(":");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 280, 98, 23);

        jLabel53.setText("Nama Bahan :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(293, 280, 85, 23);

        KeteranganBauBahan.setFocusTraversalPolicyProvider(true);
        KeteranganBauBahan.setName("KeteranganBauBahan"); // NOI18N
        KeteranganBauBahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBauBahanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBauBahan);
        KeteranganBauBahan.setBounds(209, 340, 230, 23);

        jLabel54.setText("Jumlah Bahan :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(710, 280, 90, 23);

        JumlahBahan.setFocusTraversalPolicyProvider(true);
        JumlahBahan.setName("JumlahBahan"); // NOI18N
        JumlahBahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahBahanKeyPressed(evt);
            }
        });
        FormInput.add(JumlahBahan);
        JumlahBahan.setBounds(804, 280, 50, 23);

        jLabel55.setText("Tipe Kejadian :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(630, 310, 90, 23);

        TipeKejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Disengaja", "Disengaja", "Tidak Diketahui" }));
        TipeKejadian.setName("TipeKejadian"); // NOI18N
        TipeKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipeKejadianKeyPressed(evt);
            }
        });
        FormInput.add(TipeKejadian);
        TipeKejadian.setBounds(724, 310, 130, 23);

        NamaBahan.setFocusTraversalPolicyProvider(true);
        NamaBahan.setName("NamaBahan"); // NOI18N
        NamaBahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaBahanKeyPressed(evt);
            }
        });
        FormInput.add(NamaBahan);
        NamaBahan.setBounds(382, 280, 325, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Bau Bahan");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(44, 340, 70, 23);

        BauBahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        BauBahan.setName("BauBahan"); // NOI18N
        BauBahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BauBahanKeyPressed(evt);
            }
        });
        FormInput.add(BauBahan);
        BauBahan.setBounds(106, 340, 100, 23);

        jLabel57.setText(" :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 310, 168, 23);

        TipePemaparan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mulut", "Mata", "Gigitan", "Injeksi", "Inhalasi", "Sengatan", "Kulit", "Lain-lain" }));
        TipePemaparan.setName("TipePemaparan"); // NOI18N
        TipePemaparan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipePemaparanKeyPressed(evt);
            }
        });
        FormInput.add(TipePemaparan);
        TipePemaparan.setBounds(172, 310, 100, 23);

        KeteranganTipePemaparan.setFocusTraversalPolicyProvider(true);
        KeteranganTipePemaparan.setName("KeteranganTipePemaparan"); // NOI18N
        KeteranganTipePemaparan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTipePemaparanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTipePemaparan);
        KeteranganTipePemaparan.setBounds(275, 310, 330, 23);

        Pupil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Isokor", "Unisokor", "Miosis", "Midriasis", "Lainnya" }));
        Pupil.setName("Pupil"); // NOI18N
        Pupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PupilKeyPressed(evt);
            }
        });
        FormInput.add(Pupil);
        Pupil.setBounds(524, 340, 97, 23);

        jLabel58.setText("Pupil :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(440, 340, 80, 23);

        KeteranganPupil.setFocusTraversalPolicyProvider(true);
        KeteranganPupil.setName("KeteranganPupil"); // NOI18N
        KeteranganPupil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPupilKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPupil);
        KeteranganPupil.setBounds(624, 340, 230, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Tipe Pemaparan Melalui");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(44, 310, 140, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 340, 102, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Gambaran Klinis :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(44, 370, 130, 23);

        jLabel39.setText("Kesadaran :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 390, 155, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Apatis", "Somnolen", "Sopor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(159, 390, 130, 23);

        jLabel22.setText("Tensi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(345, 390, 40, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(389, 390, 76, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(468, 390, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(545, 390, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(599, 390, 45, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(647, 390, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(723, 390, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(767, 390, 45, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(815, 390, 50, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 420, 155, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(159, 420, 45, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(207, 420, 30, 23);

        jLabel29.setText("SpO2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(256, 420, 60, 23);

        SPO.setFocusTraversalPolicyProvider(true);
        SPO.setName("SPO"); // NOI18N
        SPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SPOKeyPressed(evt);
            }
        });
        FormInput.add(SPO);
        SPO.setBounds(320, 420, 45, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(368, 420, 30, 23);

        jLabel30.setText("Urine :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(410, 420, 60, 23);

        Urine.setFocusTraversalPolicyProvider(true);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });
        FormInput.add(Urine);
        Urine.setBounds(474, 420, 45, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("cc/j");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(522, 420, 30, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Pengobatan Sebelum Ke IGD :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(44, 450, 360, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        PengobatanSebelumIGD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PengobatanSebelumIGD.setColumns(20);
        PengobatanSebelumIGD.setRows(5);
        PengobatanSebelumIGD.setName("PengobatanSebelumIGD"); // NOI18N
        PengobatanSebelumIGD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengobatanSebelumIGDKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(PengobatanSebelumIGD);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(44, 470, 810, 53);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. DIAGNOSIS/ASESMEN & TATALAKSANA");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 530, 300, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 530, 880, 1);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Diagnosa :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(44, 550, 360, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Diagnosa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosa.setColumns(20);
        Diagnosa.setRows(5);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Diagnosa);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(44, 570, 810, 53);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Pemeriksaan Penunjang :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 630, 150, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Penatalaksanaan Yang Diberikan :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(454, 630, 200, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Penunjang);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 650, 400, 53);

        scrollPane18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane18.setName("scrollPane18"); // NOI18N

        Penatalaksanaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penatalaksanaan.setColumns(20);
        Penatalaksanaan.setRows(5);
        Penatalaksanaan.setName("Penatalaksanaan"); // NOI18N
        Penatalaksanaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenatalaksanaanKeyPressed(evt);
            }
        });
        scrollPane18.setViewportView(Penatalaksanaan);

        FormInput.add(scrollPane18);
        scrollPane18.setBounds(454, 650, 400, 53);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Tindak Lanjut");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(44, 710, 80, 23);

        jLabel63.setText(":");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 710, 116, 23);

        TindakLanjut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Rawat Inap", "Dirujuk", "Pulang APS", "Pulang Sembuh", "Meninggal" }));
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut);
        TindakLanjut.setBounds(120, 710, 130, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-05-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-05-2023" }));
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
        }else if(KeluhanYangDirasakan.getText().trim().equals("")){
            Valid.textKosong(KeluhanYangDirasakan,"Riwayat Penyakit Sekarang/Keluhan Yang Dirasakan Saat Ini");
        }else if(RiwayatPenyakitKeluarga.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakitKeluarga,"Riwayat Penyakit Keluarga");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(DiagnosaTambahan.getText().trim().equals("")){
            Valid.textKosong(DiagnosaTambahan,"Diagnosa Tambahan");
        }else{
            if(Sequel.menyimpantf("penilaian_pasien_penyakit_menular","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    MengetahuiKondisi.getSelectedItem().toString(),PenyakitSama.getSelectedItem().toString(),RiwayatKontak.getSelectedItem().toString(),KeteranganRiwayatKontak.getText(),TransmisiPenularan.getSelectedItem().toString(), 
                    KeteranganTransmisiPenularan.getText(),KebutuhanRuangRawat.getSelectedItem().toString(),KeluhanYangDirasakan.getText(),RiwayatPenyakitKeluarga.getText(),RiwayatAlergi.getText(),RiwayatVaksin.getText(),
                    RiwayatPengobatan.getText(),DiagnosaUtama.getText(),DiagnosaTambahan.getText()
                })==true){
                    emptTeks();
            }
        }*/
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,DiagnosaTambahan,BtnBatal);
        }*/
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
        }else if(KeluhanYangDirasakan.getText().trim().equals("")){
            Valid.textKosong(KeluhanYangDirasakan,"Riwayat Penyakit Sekarang/Keluhan Yang Dirasakan Saat Ini");
        }else if(RiwayatPenyakitKeluarga.getText().trim().equals("")){
            Valid.textKosong(RiwayatPenyakitKeluarga,"Riwayat Penyakit Keluarga");
        }else if(DiagnosaUtama.getText().trim().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else if(DiagnosaTambahan.getText().trim().equals("")){
            Valid.textKosong(DiagnosaTambahan,"Diagnosa Tambahan");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anamnesis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mengetahui Penyakitnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penyakit Sama Serumah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Kontak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Riwayat Kontak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Transmisi Penularan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Transmisi Penularan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kebutuhan Ruang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keluhan Yang Dirasakan Saat Ini</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Penyakit Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Vaksinasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Riwayat Pengobatan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Tambahan</b></td>"+
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
                      "<table width='2600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianPasienPenyakitMenular.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PASIEN PENYAKIT MENULAR<br><br></font>"+        
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

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,DiagnosaTambahan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanSaatIniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanSaatIniKeyPressed
        //Valid.pindah2(evt,KebutuhanRuangRawat,RiwayatPenyakitKeluarga);
    }//GEN-LAST:event_KeluhanSaatIniKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,BtnDokter,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        //Valid.pindah(evt,Anamnesis,MengetahuiKondisi);
    }//GEN-LAST:event_HubunganKeyPressed

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
            
            Valid.MyReportqry("rptCetakPenilaianPasienPenyakitMenular.jasper","report","::[ Laporan Penilaian Pasien Penyakit Menular ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pasien_penyakit_menular.tanggal,"+
                "penilaian_pasien_penyakit_menular.kd_dokter,penilaian_pasien_penyakit_menular.anamnesis,penilaian_pasien_penyakit_menular.hubungan,penilaian_pasien_penyakit_menular.pasien_mengetahui_kondisi_penyakitnya,"+
                "penilaian_pasien_penyakit_menular.penyakit_sama_serumah,penilaian_pasien_penyakit_menular.riwayat_kontak,penilaian_pasien_penyakit_menular.keterangan_riwayat_kontak,"+
                "penilaian_pasien_penyakit_menular.transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.keterangan_transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.kebutuhan_ruang_rawat,"+
                "penilaian_pasien_penyakit_menular.keluhan_yang_dirasakan_saat_ini,penilaian_pasien_penyakit_menular.riwayat_penyakit_keluarga,penilaian_pasien_penyakit_menular.riwayat_alergi,"+
                "penilaian_pasien_penyakit_menular.riwayat_vaksinasi,penilaian_pasien_penyakit_menular.riwayat_pengobatan,penilaian_pasien_penyakit_menular.diagnosa_utama,penilaian_pasien_penyakit_menular.diagnosa_tambahan,"+
                "dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_pasien_penyakit_menular on reg_periksa.no_rawat=penilaian_pasien_penyakit_menular.no_rawat "+
                "inner join dokter on penilaian_pasien_penyakit_menular.kd_dokter=dokter.kd_dokter where penilaian_pasien_penyakit_menular.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KeteranganTempatKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTempatKejadianKeyPressed
        //Valid.pindah(evt,RiwayatKontak,TransmisiPenularan);
    }//GEN-LAST:event_KeteranganTempatKejadianKeyPressed

    private void TempatKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatKejadianKeyPressed
        //Valid.pindah(evt,PenyakitSama,KeteranganRiwayatKontak);
    }//GEN-LAST:event_TempatKejadianKeyPressed

    private void RiwayatPenyakitSatIniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitSatIniKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitSatIniKeyPressed

    private void HamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HamilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HamilKeyPressed

    private void MenyusuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenyusuiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenyusuiKeyPressed

    private void PenyebabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyebabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyebabKeyPressed

    private void KeteranganBauBahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBauBahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganBauBahanKeyPressed

    private void JumlahBahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahBahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahBahanKeyPressed

    private void TipeKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipeKejadianKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeKejadianKeyPressed

    private void NamaBahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaBahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaBahanKeyPressed

    private void BauBahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BauBahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BauBahanKeyPressed

    private void TipePemaparanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipePemaparanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipePemaparanKeyPressed

    private void KeteranganTipePemaparanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTipePemaparanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTipePemaparanKeyPressed

    private void PupilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PupilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PupilKeyPressed

    private void KeteranganPupilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPupilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganPupilKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        //Valid.pindah(evt,Keadaan,GCS);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        //Valid.pindah(evt,BB,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,SPO);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SPOKeyPressed
        //Valid.pindah(evt,Suhu,Kepala);
    }//GEN-LAST:event_SPOKeyPressed

    private void UrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrineKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrineKeyPressed

    private void PengobatanSebelumIGDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengobatanSebelumIGDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PengobatanSebelumIGDKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        //Valid.pindah2(evt,PemeriksaanLain,DiagnosisBdg);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void PenatalaksanaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenatalaksanaanKeyPressed
        //Valid.pindah2(evt,Diagnosis,Permasalahan);
    }//GEN-LAST:event_PenatalaksanaanKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjutKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPasienKeracunan dialog = new RMPenilaianPasienKeracunan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BauBahan;
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
    private widget.TextArea Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Hamil;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox JumlahBahan;
    private widget.TextBox KdDokter;
    private widget.TextArea KeluhanSaatIni;
    private widget.ComboBox Kesadaran;
    private widget.TextBox KeteranganBauBahan;
    private widget.TextBox KeteranganPupil;
    private widget.TextBox KeteranganTempatKejadian;
    private widget.TextBox KeteranganTipePemaparan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menyusui;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox NamaBahan;
    private widget.TextBox NmDokter;
    private widget.TextArea Penatalaksanaan;
    private widget.TextArea PengobatanSebelumIGD;
    private widget.TextArea Penunjang;
    private widget.ComboBox Penyebab;
    private widget.ComboBox Pupil;
    private widget.TextBox RR;
    private widget.TextArea RiwayatPenyakitSatIni;
    private widget.TextBox SPO;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox TempatKejadian;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TindakLanjut;
    private widget.ComboBox TipeKejadian;
    private widget.ComboBox TipePemaparan;
    private widget.TextBox Urine;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
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
    private widget.Label jLabel83;
    private widget.Label jLabel85;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane18;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pasien_penyakit_menular.tanggal,"+
                        "penilaian_pasien_penyakit_menular.kd_dokter,penilaian_pasien_penyakit_menular.anamnesis,penilaian_pasien_penyakit_menular.hubungan,penilaian_pasien_penyakit_menular.pasien_mengetahui_kondisi_penyakitnya,"+
                        "penilaian_pasien_penyakit_menular.penyakit_sama_serumah,penilaian_pasien_penyakit_menular.riwayat_kontak,penilaian_pasien_penyakit_menular.keterangan_riwayat_kontak,"+
                        "penilaian_pasien_penyakit_menular.transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.keterangan_transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.kebutuhan_ruang_rawat,"+
                        "penilaian_pasien_penyakit_menular.keluhan_yang_dirasakan_saat_ini,penilaian_pasien_penyakit_menular.riwayat_penyakit_keluarga,penilaian_pasien_penyakit_menular.riwayat_alergi,"+
                        "penilaian_pasien_penyakit_menular.riwayat_vaksinasi,penilaian_pasien_penyakit_menular.riwayat_pengobatan,penilaian_pasien_penyakit_menular.diagnosa_utama,penilaian_pasien_penyakit_menular.diagnosa_tambahan,"+
                        "dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pasien_penyakit_menular on reg_periksa.no_rawat=penilaian_pasien_penyakit_menular.no_rawat "+
                        "inner join dokter on penilaian_pasien_penyakit_menular.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pasien_penyakit_menular.tanggal between ? and ? order by penilaian_pasien_penyakit_menular.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_pasien_penyakit_menular.tanggal,"+
                        "penilaian_pasien_penyakit_menular.kd_dokter,penilaian_pasien_penyakit_menular.anamnesis,penilaian_pasien_penyakit_menular.hubungan,penilaian_pasien_penyakit_menular.pasien_mengetahui_kondisi_penyakitnya,"+
                        "penilaian_pasien_penyakit_menular.penyakit_sama_serumah,penilaian_pasien_penyakit_menular.riwayat_kontak,penilaian_pasien_penyakit_menular.keterangan_riwayat_kontak,"+
                        "penilaian_pasien_penyakit_menular.transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.keterangan_transmisi_penularan_penyakit,penilaian_pasien_penyakit_menular.kebutuhan_ruang_rawat,"+
                        "penilaian_pasien_penyakit_menular.keluhan_yang_dirasakan_saat_ini,penilaian_pasien_penyakit_menular.riwayat_penyakit_keluarga,penilaian_pasien_penyakit_menular.riwayat_alergi,"+
                        "penilaian_pasien_penyakit_menular.riwayat_vaksinasi,penilaian_pasien_penyakit_menular.riwayat_pengobatan,penilaian_pasien_penyakit_menular.diagnosa_utama,penilaian_pasien_penyakit_menular.diagnosa_tambahan,"+
                        "dokter.nm_dokter from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_pasien_penyakit_menular on reg_periksa.no_rawat=penilaian_pasien_penyakit_menular.no_rawat "+
                        "inner join dokter on penilaian_pasien_penyakit_menular.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_pasien_penyakit_menular.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_pasien_penyakit_menular.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_pasien_penyakit_menular.tanggal");
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
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("pasien_mengetahui_kondisi_penyakitnya"),rs.getString("penyakit_sama_serumah"),rs.getString("riwayat_kontak"),rs.getString("keterangan_riwayat_kontak"),
                        rs.getString("transmisi_penularan_penyakit"),rs.getString("keterangan_transmisi_penularan_penyakit"),rs.getString("kebutuhan_ruang_rawat"),rs.getString("keluhan_yang_dirasakan_saat_ini"),
                        rs.getString("riwayat_penyakit_keluarga"),rs.getString("riwayat_alergi"),rs.getString("riwayat_vaksinasi"),rs.getString("riwayat_pengobatan"),rs.getString("diagnosa_utama"),rs.getString("diagnosa_tambahan")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        TglAsuhan.setDate(new Date());
        KeluhanSaatIni.setText("");
        RiwayatPenyakitSatIni.setText("");
        Hamil.setSelectedIndex(1);
        Menyusui.setSelectedIndex(1);
        TempatKejadian.setSelectedIndex(0);
        KeteranganTempatKejadian.setText("");
        Penyebab.setSelectedIndex(0);
        NamaBahan.setText("");
        JumlahBahan.setText("");
        TipePemaparan.setSelectedIndex(0);
        KeteranganTipePemaparan.setText("");
        TipeKejadian.setSelectedIndex(0);
        BauBahan.setSelectedIndex(0);
        KeteranganBauBahan.setText("");
        Pupil.setSelectedIndex(0);
        KeteranganPupil.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        SPO.setText("");
        Urine.setText("");
        
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
            KeluhanSaatIni.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
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
        BtnSimpan.setEnabled(akses.getpenilaian_pasien_penyakit_menular());
        BtnHapus.setEnabled(akses.getpenilaian_pasien_penyakit_menular());
        BtnEdit.setEnabled(akses.getpenilaian_pasien_penyakit_menular());
        BtnEdit.setEnabled(akses.getpenilaian_pasien_penyakit_menular());
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
        if(Sequel.queryu2tf("delete from penilaian_pasien_penyakit_menular where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_pasien_penyakit_menular","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,pasien_mengetahui_kondisi_penyakitnya=?,penyakit_sama_serumah=?,riwayat_kontak=?,keterangan_riwayat_kontak=?,"+
                "transmisi_penularan_penyakit=?,keterangan_transmisi_penularan_penyakit=?,kebutuhan_ruang_rawat=?,keluhan_yang_dirasakan_saat_ini=?,riwayat_penyakit_keluarga=?,riwayat_alergi=?,riwayat_vaksinasi=?,riwayat_pengobatan=?,diagnosa_utama=?,"+
                "diagnosa_tambahan=?",20,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                MengetahuiKondisi.getSelectedItem().toString(),PenyakitSama.getSelectedItem().toString(),RiwayatKontak.getSelectedItem().toString(),KeteranganRiwayatKontak.getText(),TransmisiPenularan.getSelectedItem().toString(), 
                KeteranganTransmisiPenularan.getText(),KebutuhanRuangRawat.getSelectedItem().toString(),KeluhanYangDirasakan.getText(),RiwayatPenyakitKeluarga.getText(),RiwayatAlergi.getText(),RiwayatVaksin.getText(),
                RiwayatPengobatan.getText(),DiagnosaUtama.getText(),DiagnosaTambahan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText().substring(0,1),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(Anamnesis.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Hubungan.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(MengetahuiKondisi.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(PenyakitSama.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(RiwayatKontak.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(KeteranganRiwayatKontak.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(TransmisiPenularan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(KeteranganTransmisiPenularan.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(KebutuhanRuangRawat.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(KeluhanYangDirasakan.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(RiwayatPenyakitKeluarga.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(RiwayatAlergi.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(RiwayatVaksin.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(RiwayatPengobatan.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(DiagnosaUtama.getText(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(DiagnosaTambahan.getText(),tbObat.getSelectedRow(),23);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
}

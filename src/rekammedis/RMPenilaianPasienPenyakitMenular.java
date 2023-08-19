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
public final class RMPenilaianPasienPenyakitMenular extends javax.swing.JDialog {
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
    public RMPenilaianPasienPenyakitMenular(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Anamnesis","Hubungan","Mengetahui Penyakitnya",
            "Penyakit Sama Serumah","Riwayat Kontak","Keterangan Riwayat Kontak","Transmisi Penularan", "Keterangan Transmisi Penularan", 
            "Kebutuhan Ruang","Keluhan Yang Dirasakan Saat Ini","Riwayat Penyakit Keluarga","Riwayat Alergi","Riwayat Vaksinasi","Riwayat Pengobatan", 
            "Diagnosa Utama", "Diagnosa Tambahan"
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
            }else if(i==10){
                column.setPreferredWidth(127);
            }else if(i==11){
                column.setPreferredWidth(127);
            }else if(i==12){
                column.setPreferredWidth(250);
            }else if(i==13){
                column.setPreferredWidth(170);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(165);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(250);
            }else if(i==18){
                column.setPreferredWidth(220);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(250);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        KeteranganRiwayatKontak.setDocument(new batasInput((int)70).getKata(KeteranganRiwayatKontak));
        KeteranganTransmisiPenularan.setDocument(new batasInput((int)70).getKata(KeteranganTransmisiPenularan));
        KeluhanYangDirasakan.setDocument(new batasInput((int)1000).getKata(KeluhanYangDirasakan));
        RiwayatPenyakitKeluarga.setDocument(new batasInput((int)1000).getKata(RiwayatPenyakitKeluarga));
        RiwayatAlergi.setDocument(new batasInput((int)100).getKata(RiwayatAlergi));
        RiwayatVaksin.setDocument(new batasInput((int)100).getKata(RiwayatVaksin));
        RiwayatPengobatan.setDocument(new batasInput((int)1000).getKata(RiwayatPengobatan));
        DiagnosaUtama.setDocument(new batasInput((int)500).getKata(DiagnosaUtama));
        DiagnosaTambahan.setDocument(new batasInput((int)500).getKata(DiagnosaTambahan));
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
        KeluhanYangDirasakan = new widget.TextArea();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel40 = new widget.Label();
        MengetahuiKondisi = new widget.ComboBox();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator14 = new javax.swing.JSeparator();
        KeteranganRiwayatKontak = new widget.TextBox();
        PenyakitSama = new widget.ComboBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        RiwayatKontak = new widget.ComboBox();
        TransmisiPenularan = new widget.ComboBox();
        jLabel36 = new widget.Label();
        KeteranganTransmisiPenularan = new widget.TextBox();
        jLabel37 = new widget.Label();
        KebutuhanRuangRawat = new widget.ComboBox();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakitKeluarga = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RiwayatPengobatan = new widget.TextArea();
        jLabel42 = new widget.Label();
        jLabel39 = new widget.Label();
        RiwayatAlergi = new widget.TextBox();
        RiwayatVaksin = new widget.TextBox();
        jLabel43 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        DiagnosaUtama = new widget.TextArea();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        DiagnosaTambahan = new widget.TextArea();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Pasien Penyakit Menular ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 653));
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

        KeluhanYangDirasakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanYangDirasakan.setColumns(20);
        KeluhanYangDirasakan.setRows(5);
        KeluhanYangDirasakan.setName("KeluhanYangDirasakan"); // NOI18N
        KeluhanYangDirasakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanYangDirasakanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanYangDirasakan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(44, 200, 810, 63);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. DIAGNOSIS/ASESMEN");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 470, 180, 23);

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

        jLabel33.setText(":");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(1, 90, 280, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Riwayat Penyakit Sekarang/Keluhan Yang Dirasakan Saat Ini :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(44, 180, 360, 23);

        MengetahuiKondisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MengetahuiKondisi.setName("MengetahuiKondisi"); // NOI18N
        MengetahuiKondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MengetahuiKondisiKeyPressed(evt);
            }
        });
        FormInput.add(MengetahuiKondisi);
        MengetahuiKondisi.setBounds(285, 90, 90, 23);

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
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-03-2023 07:03:40" }));
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

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 470, 880, 1);

        KeteranganRiwayatKontak.setFocusTraversalPolicyProvider(true);
        KeteranganRiwayatKontak.setName("KeteranganRiwayatKontak"); // NOI18N
        KeteranganRiwayatKontak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatKontakKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatKontak);
        KeteranganRiwayatKontak.setBounds(463, 120, 391, 23);

        PenyakitSama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PenyakitSama.setName("PenyakitSama"); // NOI18N
        PenyakitSama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitSamaKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitSama);
        PenyakitSama.setBounds(764, 90, 90, 23);

        jLabel34.setText("Apakah Di Dalam Satu Rumah Ada Penyakit Yang Sama :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(460, 90, 300, 23);

        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 120, 126, 23);

        RiwayatKontak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bekerja Di Daerah KLB", "Merawat Pasien Penyakit Infeksi", "Berkunjung Ke Daerah Endemik Dalam 2 Minggu Terakhir", "Bekerja Di Laboratorium", "Kontak Langsung", "Lain-lain" }));
        RiwayatKontak.setName("RiwayatKontak"); // NOI18N
        RiwayatKontak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatKontakKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatKontak);
        RiwayatKontak.setBounds(130, 120, 330, 23);

        TransmisiPenularan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Airborne", "Droplet", "Kontak Langsung", "Cairan Tubuh Lainnya", "Lain-lain" }));
        TransmisiPenularan.setName("TransmisiPenularan"); // NOI18N
        TransmisiPenularan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransmisiPenularanKeyPressed(evt);
            }
        });
        FormInput.add(TransmisiPenularan);
        TransmisiPenularan.setBounds(198, 150, 157, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 150, 194, 23);

        KeteranganTransmisiPenularan.setFocusTraversalPolicyProvider(true);
        KeteranganTransmisiPenularan.setName("KeteranganTransmisiPenularan"); // NOI18N
        KeteranganTransmisiPenularan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTransmisiPenularanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTransmisiPenularan);
        KeteranganTransmisiPenularan.setBounds(358, 150, 230, 23);

        jLabel37.setText("Kebutuhan Ruang Rawat :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(595, 150, 140, 23);

        KebutuhanRuangRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Isolasi", "Ruang Biasa", "ICU", "ICU Isolasi" }));
        KebutuhanRuangRawat.setName("KebutuhanRuangRawat"); // NOI18N
        KebutuhanRuangRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanRuangRawatKeyPressed(evt);
            }
        });
        FormInput.add(KebutuhanRuangRawat);
        KebutuhanRuangRawat.setBounds(739, 150, 115, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakitKeluarga.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakitKeluarga.setColumns(20);
        RiwayatPenyakitKeluarga.setRows(5);
        RiwayatPenyakitKeluarga.setName("RiwayatPenyakitKeluarga"); // NOI18N
        RiwayatPenyakitKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeluargaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RiwayatPenyakitKeluarga);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(44, 290, 810, 53);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Riwayat Penyakit Keluarga :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(44, 270, 280, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RiwayatPengobatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPengobatan.setColumns(20);
        RiwayatPengobatan.setRows(5);
        RiwayatPengobatan.setName("RiwayatPengobatan"); // NOI18N
        RiwayatPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPengobatanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RiwayatPengobatan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(44, 400, 810, 63);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Riwayat Pengobatan/Penggunaan Obat-obatan Terlarang :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(44, 380, 420, 23);

        jLabel39.setText(":");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 350, 122, 23);

        RiwayatAlergi.setFocusTraversalPolicyProvider(true);
        RiwayatAlergi.setName("RiwayatAlergi"); // NOI18N
        RiwayatAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatAlergiKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatAlergi);
        RiwayatAlergi.setBounds(126, 350, 300, 23);

        RiwayatVaksin.setFocusTraversalPolicyProvider(true);
        RiwayatVaksin.setName("RiwayatVaksin"); // NOI18N
        RiwayatVaksin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatVaksinKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatVaksin);
        RiwayatVaksin.setBounds(554, 350, 300, 23);

        jLabel43.setText("Riwayat Vaksinasi :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(430, 350, 120, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        DiagnosaUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaUtama.setColumns(20);
        DiagnosaUtama.setRows(5);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(DiagnosaUtama);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(44, 510, 810, 53);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Diagnosa Utama :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(44, 490, 420, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Diagnosa Tambahan :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(44, 570, 420, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        DiagnosaTambahan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaTambahan.setColumns(20);
        DiagnosaTambahan.setRows(5);
        DiagnosaTambahan.setName("DiagnosaTambahan"); // NOI18N
        DiagnosaTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaTambahanKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(DiagnosaTambahan);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(44, 590, 810, 53);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Apakah Pasien Mengetahui Kondisi Penyakitnya");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(44, 90, 240, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Riwayat Kontak");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(44, 120, 90, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Transmisi Penularan Penyakit");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(44, 150, 160, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Riwayat Alergi");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(44, 350, 80, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-03-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-03-2023" }));
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
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,DiagnosaTambahan,BtnBatal);
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
        if(TNoRM.getText().trim().equals("")){
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
        Valid.pindah(evt,DiagnosaTambahan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void KeluhanYangDirasakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanYangDirasakanKeyPressed
        Valid.pindah2(evt,KebutuhanRuangRawat,RiwayatPenyakitKeluarga);
    }//GEN-LAST:event_KeluhanYangDirasakanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MengetahuiKondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MengetahuiKondisiKeyPressed
        Valid.pindah(evt,Hubungan,PenyakitSama);
    }//GEN-LAST:event_MengetahuiKondisiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,BtnDokter,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,Anamnesis,MengetahuiKondisi);
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

    private void KeteranganRiwayatKontakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatKontakKeyPressed
        Valid.pindah(evt,RiwayatKontak,TransmisiPenularan);
    }//GEN-LAST:event_KeteranganRiwayatKontakKeyPressed

    private void PenyakitSamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitSamaKeyPressed
        Valid.pindah(evt,MengetahuiKondisi,RiwayatKontak);
    }//GEN-LAST:event_PenyakitSamaKeyPressed

    private void RiwayatKontakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatKontakKeyPressed
        Valid.pindah(evt,PenyakitSama,KeteranganRiwayatKontak);
    }//GEN-LAST:event_RiwayatKontakKeyPressed

    private void TransmisiPenularanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransmisiPenularanKeyPressed
        Valid.pindah(evt,KeteranganRiwayatKontak,KeteranganTransmisiPenularan);
    }//GEN-LAST:event_TransmisiPenularanKeyPressed

    private void KeteranganTransmisiPenularanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTransmisiPenularanKeyPressed
        Valid.pindah(evt,TransmisiPenularan,KebutuhanRuangRawat);
    }//GEN-LAST:event_KeteranganTransmisiPenularanKeyPressed

    private void KebutuhanRuangRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanRuangRawatKeyPressed
        Valid.pindah(evt,KeteranganTransmisiPenularan,KeluhanYangDirasakan);
    }//GEN-LAST:event_KebutuhanRuangRawatKeyPressed

    private void RiwayatPenyakitKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeluargaKeyPressed
        Valid.pindah2(evt,KeluhanYangDirasakan,RiwayatAlergi);
    }//GEN-LAST:event_RiwayatPenyakitKeluargaKeyPressed

    private void RiwayatPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPengobatanKeyPressed
        Valid.pindah2(evt,RiwayatVaksin,DiagnosaUtama);
    }//GEN-LAST:event_RiwayatPengobatanKeyPressed

    private void RiwayatAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatAlergiKeyPressed
        Valid.pindah(evt,RiwayatPenyakitKeluarga,RiwayatVaksin);
    }//GEN-LAST:event_RiwayatAlergiKeyPressed

    private void RiwayatVaksinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatVaksinKeyPressed
        Valid.pindah(evt,RiwayatAlergi,RiwayatPengobatan);
    }//GEN-LAST:event_RiwayatVaksinKeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        Valid.pindah2(evt,RiwayatPengobatan,DiagnosaTambahan);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void DiagnosaTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaTambahanKeyPressed
        Valid.pindah2(evt,DiagnosaUtama,BtnSimpan);
    }//GEN-LAST:event_DiagnosaTambahanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianPasienPenyakitMenular dialog = new RMPenilaianPasienPenyakitMenular(new javax.swing.JFrame(), true);
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
    private widget.TextArea DiagnosaTambahan;
    private widget.TextArea DiagnosaUtama;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox KebutuhanRuangRawat;
    private widget.TextArea KeluhanYangDirasakan;
    private widget.TextBox KeteranganRiwayatKontak;
    private widget.TextBox KeteranganTransmisiPenularan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MengetahuiKondisi;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.ComboBox PenyakitSama;
    private widget.TextBox RiwayatAlergi;
    private widget.ComboBox RiwayatKontak;
    private widget.TextArea RiwayatPengobatan;
    private widget.TextArea RiwayatPenyakitKeluarga;
    private widget.TextBox RiwayatVaksin;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TransmisiPenularan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
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
        MengetahuiKondisi.setSelectedIndex(0);
        PenyakitSama.setSelectedIndex(0);
        RiwayatKontak.setSelectedIndex(0);
        KeteranganRiwayatKontak.setText("");
        TransmisiPenularan.setSelectedIndex(0);
        KeteranganTransmisiPenularan.setText("");
        KebutuhanRuangRawat.setSelectedIndex(0);
        KeluhanYangDirasakan.setText("");
        RiwayatPenyakitKeluarga.setText("");
        RiwayatAlergi.setText("");
        RiwayatVaksin.setText("");
        RiwayatPengobatan.setText("");
        DiagnosaUtama.setText("");
        DiagnosaTambahan.setText("");
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
            KeluhanYangDirasakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
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
        if(Sequel.mengedittf("penilaian_pasien_penyakit_menular","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,pasien_mengetahui_kondisi_penyakitnya=?,penyakit_sama_serumah=?,riwayat_kontak=?,keterangan_riwayat_kontak=?,"+
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
        }
    }
}

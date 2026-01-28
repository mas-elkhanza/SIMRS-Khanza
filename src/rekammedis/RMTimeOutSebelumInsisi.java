/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMTimeOutSebelumInsisi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;    
    private DlgCariPetugas petugas;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String finger="",finger2="";
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTimeOutSebelumInsisi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","SN/CN","Tindakan","Kode Dokter Bedah","Nama Dokter Bedah",
            "Kode Dokter Anest","Nama Dokter Anestesi","Verbal Identitas","Verbal Tindakan","Verbal Area Insisi","Penandaan Area Operasi",
            "Lama Operasi","Penayangan Radiologi","Penayangan CT Scan","Penayangan MRI","Pemberian Antibiotik","Nama Antibiotik Diberikan",
            "Jam Pemberian","Antisipasi Kehilangan Darah","Ada Hal Khusus","Hal Khusus Yang Perlu Diperhatikan","Tgl.Steril","Petunjuk Sterilisasi",
            "Verifikasi Pre Operatif","NIP OK","Petugas Ruang OK"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
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
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(88);
            }else if(i==13){
                column.setPreferredWidth(88);
            }else if(i==14){
                column.setPreferredWidth(95);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(76);
            }else if(i==17){
                column.setPreferredWidth(117);
            }else if(i==18){
                column.setPreferredWidth(110);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(111);
            }else if(i==21){
                column.setPreferredWidth(135);
            }else if(i==22){
                column.setPreferredWidth(84);
            }else if(i==23){
                column.setPreferredWidth(145);
            }else if(i==24){
                column.setPreferredWidth(83);
            }else if(i==25){
                column.setPreferredWidth(181);
            }else if(i==26){
                column.setPreferredWidth(60);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(117);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        SNCN.setDocument(new batasInput((byte)25).getKata(SNCN));
        Tindakan.setDocument(new batasInput((byte)50).getKata(Tindakan));
        AntisipisasiKehialnganDarah.setDocument(new batasInput((byte)30).getKata(AntisipisasiKehialnganDarah));
        NamaAntibiotikDIberikan.setDocument(new batasInput((byte)50).getKata(NamaAntibiotikDIberikan));
        JamPemberianAntibiotik.setDocument(new batasInput((byte)10).getKata(JamPemberianAntibiotik));
        PerkiraanLama.setDocument(new batasInput((byte)10).getKata(PerkiraanLama));
        AntisipisasiKehialnganDarah.setDocument(new batasInput((byte)50).getKata(AntisipisasiKehialnganDarah));
        HalKhususDiperhatikan.setDocument(new batasInput((byte)100).getKata(HalKhususDiperhatikan));
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        }
        
        ChkInput.setSelected(false);
        isForm();
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnTimeOutSebelumInsisi = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel20 = new widget.Label();
        SNCN = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokter2 = new widget.Button();
        NmDokter2 = new widget.TextBox();
        KdDokter2 = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        PemberianAntibiotik = new widget.ComboBox();
        jLabel51 = new widget.Label();
        AreaOperasi = new widget.ComboBox();
        Tindakan = new widget.TextBox();
        jLabel26 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel28 = new widget.Label();
        AntisipisasiKehialnganDarah = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel29 = new widget.Label();
        VerbalTindakan = new widget.ComboBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        VerbalArea = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel52 = new widget.Label();
        PenayanganRadiologi = new widget.ComboBox();
        jLabel55 = new widget.Label();
        PenayanganCTScan = new widget.ComboBox();
        jLabel56 = new widget.Label();
        PenayanganMRI = new widget.ComboBox();
        jLabel57 = new widget.Label();
        VerbalIdentitas = new widget.ComboBox();
        jLabel32 = new widget.Label();
        JamPemberianAntibiotik = new widget.TextBox();
        jLabel33 = new widget.Label();
        PerkiraanLama = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel34 = new widget.Label();
        AdaHalKhusus = new widget.ComboBox();
        jLabel58 = new widget.Label();
        NamaAntibiotikDIberikan = new widget.TextBox();
        jLabel59 = new widget.Label();
        HalKhususDiperhatikan = new widget.TextBox();
        jLabel17 = new widget.Label();
        TanggalSeteril = new widget.Tanggal();
        jLabel36 = new widget.Label();
        PetunjukSterilisasi = new widget.ComboBox();
        jLabel37 = new widget.Label();
        VerifikasiOperatif = new widget.ComboBox();
        jLabel38 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel27 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnTimeOutSebelumInsisi.setBackground(new java.awt.Color(255, 255, 254));
        MnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTimeOutSebelumInsisi.setForeground(new java.awt.Color(50, 50, 50));
        MnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTimeOutSebelumInsisi.setText("Formulir Time-Out Sebelum Tindakan Insisi");
        MnTimeOutSebelumInsisi.setName("MnTimeOutSebelumInsisi"); // NOI18N
        MnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(270, 26));
        MnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTimeOutSebelumInsisi);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Time-Out Sebelum Tindakan Insisi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 406));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 383));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026 08:06:52" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Konfirmasi Dipimpin Oleh Salah Satu Anggota Tim, Semua Kegiatan Ditangguhkan Kecuali Jika Mengancam Jiwa :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(21, 100, 600, 23);

        SNCN.setHighlighter(null);
        SNCN.setName("SNCN"); // NOI18N
        SNCN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SNCNKeyPressed(evt);
            }
        });
        FormInput.add(SNCN);
        SNCN.setBounds(264, 40, 120, 23);

        jLabel22.setText("SN/CN :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(210, 40, 50, 23);

        jLabel23.setText("Dokter Bedah :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(390, 40, 91, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(485, 40, 97, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(584, 40, 175, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("ALt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
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
        BtnDokter.setBounds(761, 40, 28, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("ALt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        BtnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(761, 70, 28, 23);

        NmDokter2.setEditable(false);
        NmDokter2.setName("NmDokter2"); // NOI18N
        FormInput.add(NmDokter2);
        NmDokter2.setBounds(584, 70, 175, 23);

        KdDokter2.setEditable(false);
        KdDokter2.setHighlighter(null);
        KdDokter2.setName("KdDokter2"); // NOI18N
        FormInput.add(KdDokter2);
        KdDokter2.setBounds(485, 70, 97, 23);

        jLabel24.setText("Dokter Anestesi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(390, 70, 91, 23);

        jLabel25.setText("Tindakan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 70, 75, 23);

        PemberianAntibiotik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemberianAntibiotik.setName("PemberianAntibiotik"); // NOI18N
        PemberianAntibiotik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemberianAntibiotikKeyPressed(evt);
            }
        });
        FormInput.add(PemberianAntibiotik);
        PemberianAntibiotik.setBounds(206, 220, 80, 23);

        jLabel51.setText("Penandaan Area Operasi :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(510, 120, 140, 23);

        AreaOperasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada", "Tidak Diperlukan" }));
        AreaOperasi.setName("AreaOperasi"); // NOI18N
        AreaOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaOperasiKeyPressed(evt);
            }
        });
        FormInput.add(AreaOperasi);
        AreaOperasi.setBounds(654, 120, 135, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(79, 70, 305, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Perawat Kamar Operasi");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(21, 350, 130, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(147, 350, 110, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(259, 350, 300, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("ALt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(561, 350, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 810, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 340, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 340, 810, 1);

        jLabel28.setText("Identitas :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(57, 140, 60, 23);

        AntisipisasiKehialnganDarah.setHighlighter(null);
        AntisipisasiKehialnganDarah.setName("AntisipisasiKehialnganDarah"); // NOI18N
        AntisipisasiKehialnganDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntisipisasiKehialnganDarahKeyPressed(evt);
            }
        });
        FormInput.add(AntisipisasiKehialnganDarah);
        AntisipisasiKehialnganDarah.setBounds(356, 250, 433, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Penayangan Hasil Pemeriksaan Penunjang :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(40, 170, 240, 23);

        jLabel29.setText(":");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(342, 220, 30, 23);

        VerbalTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalTindakan.setName("VerbalTindakan"); // NOI18N
        VerbalTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalTindakanKeyPressed(evt);
            }
        });
        FormInput.add(VerbalTindakan);
        VerbalTindakan.setBounds(266, 140, 80, 23);

        jLabel30.setText("Tindakan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(202, 140, 60, 23);

        jLabel31.setText("Area Insisi :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(349, 140, 67, 23);

        VerbalArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalArea.setName("VerbalArea"); // NOI18N
        VerbalArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalAreaKeyPressed(evt);
            }
        });
        FormInput.add(VerbalArea);
        VerbalArea.setBounds(420, 140, 80, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Verbalisasi Tim, Konfirmasi :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(40, 120, 200, 23);

        jLabel52.setText("Radiologi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(93, 190, 90, 23);

        PenayanganRadiologi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ditayangkan", "Benar", "Tidak Diperlukan" }));
        PenayanganRadiologi.setName("PenayanganRadiologi"); // NOI18N
        PenayanganRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenayanganRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(PenayanganRadiologi);
        PenayanganRadiologi.setBounds(187, 190, 135, 23);

        jLabel55.setText("CT Scan :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(370, 190, 60, 23);

        PenayanganCTScan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ditayangkan", "Benar", "Tidak Diperlukan" }));
        PenayanganCTScan.setName("PenayanganCTScan"); // NOI18N
        PenayanganCTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenayanganCTScanKeyPressed(evt);
            }
        });
        FormInput.add(PenayanganCTScan);
        PenayanganCTScan.setBounds(434, 190, 135, 23);

        jLabel56.setText("MRI :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(590, 190, 60, 23);

        PenayanganMRI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ditayangkan", "Benar", "Tidak Diperlukan" }));
        PenayanganMRI.setName("PenayanganMRI"); // NOI18N
        PenayanganMRI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenayanganMRIKeyPressed(evt);
            }
        });
        FormInput.add(PenayanganMRI);
        PenayanganMRI.setBounds(654, 190, 135, 23);

        jLabel57.setText(":");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(2, 220, 200, 23);

        VerbalIdentitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalIdentitas.setName("VerbalIdentitas"); // NOI18N
        VerbalIdentitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalIdentitasKeyPressed(evt);
            }
        });
        FormInput.add(VerbalIdentitas);
        VerbalIdentitas.setBounds(121, 140, 80, 23);

        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(685, 220, 40, 23);

        JamPemberianAntibiotik.setHighlighter(null);
        JamPemberianAntibiotik.setName("JamPemberianAntibiotik"); // NOI18N
        JamPemberianAntibiotik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPemberianAntibiotikKeyPressed(evt);
            }
        });
        FormInput.add(JamPemberianAntibiotik);
        JamPemberianAntibiotik.setBounds(729, 220, 60, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Jam");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(737, 150, 30, 23);

        PerkiraanLama.setHighlighter(null);
        PerkiraanLama.setName("PerkiraanLama"); // NOI18N
        PerkiraanLama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerkiraanLamaKeyPressed(evt);
            }
        });
        FormInput.add(PerkiraanLama);
        PerkiraanLama.setBounds(654, 150, 80, 23);

        jLabel35.setText("Perkiraan Lama Operasi :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(510, 150, 140, 23);

        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 280, 205, 23);

        AdaHalKhusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        AdaHalKhusus.setName("AdaHalKhusus"); // NOI18N
        AdaHalKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdaHalKhususKeyPressed(evt);
            }
        });
        FormInput.add(AdaHalKhusus);
        AdaHalKhusus.setBounds(209, 280, 100, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Antisipasi Kehilangan Darah > 500 ml (7 ml/Kg BB Untuk Anak)");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 250, 320, 23);

        NamaAntibiotikDIberikan.setHighlighter(null);
        NamaAntibiotikDIberikan.setName("NamaAntibiotikDIberikan"); // NOI18N
        NamaAntibiotikDIberikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaAntibiotikDIberikanKeyPressed(evt);
            }
        });
        FormInput.add(NamaAntibiotikDIberikan);
        NamaAntibiotikDIberikan.setBounds(376, 220, 258, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(337, 280, 30, 23);

        HalKhususDiperhatikan.setHighlighter(null);
        HalKhususDiperhatikan.setName("HalKhususDiperhatikan"); // NOI18N
        HalKhususDiperhatikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HalKhususDiperhatikanKeyPressed(evt);
            }
        });
        FormInput.add(HalKhususDiperhatikan);
        HalKhususDiperhatikan.setBounds(371, 280, 418, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 310, 115, 23);

        TanggalSeteril.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSeteril.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-01-2026" }));
        TanggalSeteril.setDisplayFormat("dd-MM-yyyy");
        TanggalSeteril.setName("TanggalSeteril"); // NOI18N
        TanggalSeteril.setOpaque(false);
        TanggalSeteril.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSeterilKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSeteril);
        TanggalSeteril.setBounds(119, 310, 90, 23);

        jLabel36.setText("Petunjuk Sterilisasi Telah Dikonfirmasi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(215, 310, 200, 23);

        PetunjukSterilisasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PetunjukSterilisasi.setName("PetunjukSterilisasi"); // NOI18N
        PetunjukSterilisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PetunjukSterilisasiKeyPressed(evt);
            }
        });
        FormInput.add(PetunjukSterilisasi);
        PetunjukSterilisasi.setBounds(419, 310, 80, 23);

        jLabel37.setText("Verifikasi Pre Operatif Telah Dilakukan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(495, 310, 210, 23);

        VerifikasiOperatif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerifikasiOperatif.setName("VerifikasiOperatif"); // NOI18N
        VerifikasiOperatif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerifikasiOperatifKeyPressed(evt);
            }
        });
        FormInput.add(VerifikasiOperatif);
        VerifikasiOperatif.setBounds(709, 310, 80, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText(", Jam Pemberian");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(637, 220, 90, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Pemberian Antibiotik Profilaksis");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(40, 220, 170, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText(", Jika Diberikan");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(290, 220, 85, 23);

        jLabel61.setText(":");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(2, 250, 350, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Hal Khusus Yang Perlu Perhatian");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(40, 280, 170, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText(", Jika Ada");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(313, 280, 60, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Tanggal Steril");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel18);
        jLabel18.setBounds(40, 310, 80, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 350, 143, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            //Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Bedah");
        }else if(KdDokter2.getText().trim().equals("")||NmDokter2.getText().trim().equals("")){
            Valid.textKosong(KdDokter2,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{
            if(Sequel.menyimpantf("timeout_sebelum_insisi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KdDokter.getText(),KdDokter2.getText(),VerbalIdentitas.getSelectedItem().toString(),VerbalTindakan.getSelectedItem().toString(),
                VerbalArea.getSelectedItem().toString(),AreaOperasi.getSelectedItem().toString(),PerkiraanLama.getText(),PenayanganRadiologi.getSelectedItem().toString(),
                PenayanganCTScan.getSelectedItem().toString(),PenayanganMRI.getSelectedItem().toString(),PemberianAntibiotik.getSelectedItem().toString(),
                NamaAntibiotikDIberikan.getText(),JamPemberianAntibiotik.getText(),AntisipisasiKehialnganDarah.getText(),AdaHalKhusus.getSelectedItem().toString(),
                HalKhususDiperhatikan.getText(),Valid.SetTgl(TanggalSeteril.getSelectedItem()+""),PetunjukSterilisasi.getSelectedItem().toString(),
                VerifikasiOperatif.getSelectedItem().toString(),KdPetugas.getText()
            })==true){
                runBackground(() ->tampil());
                emptTeks();
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnPetugas,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"User Login harus Petugas OK/Dokter Anestesi..!!");
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Bedah");
        }else if(KdDokter2.getText().trim().equals("")||NmDokter2.getText().trim().equals("")){
            Valid.textKosong(KdDokter2,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"User Login harus Petugas OK/Dokter Anestesi..!!");
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,timeout_sebelum_insisi.tanggal,"+
                        "timeout_sebelum_insisi.sncn,timeout_sebelum_insisi.tindakan,timeout_sebelum_insisi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "timeout_sebelum_insisi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,timeout_sebelum_insisi.verbal_identitas,"+
                        "timeout_sebelum_insisi.verbal_tindakan,timeout_sebelum_insisi.verbal_area_insisi,timeout_sebelum_insisi.penandaan_area_operasi,"+
                        "timeout_sebelum_insisi.lama_operasi,timeout_sebelum_insisi.penayangan_radiologi,timeout_sebelum_insisi.penayangan_ctscan,"+
                        "timeout_sebelum_insisi.penayangan_mri,timeout_sebelum_insisi.antibiotik_profilaks,timeout_sebelum_insisi.nama_antibiotik,"+
                        "timeout_sebelum_insisi.jam_pemberian,timeout_sebelum_insisi.antisipasi_kehilangan_darah,timeout_sebelum_insisi.hal_khusus,"+
                        "timeout_sebelum_insisi.hal_khusus_diperhatikan,timeout_sebelum_insisi.tanggal_steril,timeout_sebelum_insisi.petujuk_sterilisasi,"+
                        "timeout_sebelum_insisi.verifikasi_preoperatif,timeout_sebelum_insisi.nip_perawat_ok,petugas.nama "+
                        "from timeout_sebelum_insisi inner join reg_periksa on timeout_sebelum_insisi.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter as dokterbedah on dokterbedah.kd_dokter=timeout_sebelum_insisi.kd_dokter_bedah "+
                        "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=timeout_sebelum_insisi.kd_dokter_anestesi "+
                        "inner join petugas on petugas.nip=timeout_sebelum_insisi.nip_perawat_ok "+
                        "where timeout_sebelum_insisi.tanggal between ? and ? order by timeout_sebelum_insisi.tanggal ");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,timeout_sebelum_insisi.tanggal,"+
                        "timeout_sebelum_insisi.sncn,timeout_sebelum_insisi.tindakan,timeout_sebelum_insisi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "timeout_sebelum_insisi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,timeout_sebelum_insisi.verbal_identitas,"+
                        "timeout_sebelum_insisi.verbal_tindakan,timeout_sebelum_insisi.verbal_area_insisi,timeout_sebelum_insisi.penandaan_area_operasi,"+
                        "timeout_sebelum_insisi.lama_operasi,timeout_sebelum_insisi.penayangan_radiologi,timeout_sebelum_insisi.penayangan_ctscan,"+
                        "timeout_sebelum_insisi.penayangan_mri,timeout_sebelum_insisi.antibiotik_profilaks,timeout_sebelum_insisi.nama_antibiotik,"+
                        "timeout_sebelum_insisi.jam_pemberian,timeout_sebelum_insisi.antisipasi_kehilangan_darah,timeout_sebelum_insisi.hal_khusus,"+
                        "timeout_sebelum_insisi.hal_khusus_diperhatikan,timeout_sebelum_insisi.tanggal_steril,timeout_sebelum_insisi.petujuk_sterilisasi,"+
                        "timeout_sebelum_insisi.verifikasi_preoperatif,timeout_sebelum_insisi.nip_perawat_ok,petugas.nama "+
                        "from timeout_sebelum_insisi inner join reg_periksa on timeout_sebelum_insisi.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter as dokterbedah on dokterbedah.kd_dokter=timeout_sebelum_insisi.kd_dokter_bedah "+
                        "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=timeout_sebelum_insisi.kd_dokter_anestesi "+
                        "inner join petugas on petugas.nip=timeout_sebelum_insisi.nip_perawat_ok "+
                        "where timeout_sebelum_insisi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                        "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugas.nama like ?) "+
                        "order by timeout_sebelum_insisi.tanggal ");
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
                        ps.setString(8,"%"+TCari.getText()+"%");
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
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SN/CN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter Bedah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter Bedah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter Anest</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter Anestesi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Identitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Tindakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Area Insisi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penandaan Area Operasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lama Operasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penayangan Radiologi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penayangan CT Scan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penayangan MRI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pemberian Antibiotik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Antibiotik Diberikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jam Pemberian</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Antisipasi Kehilangan Darah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ada Hal Khusus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hal Khusus Yang Perlu Diperhatikan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Steril</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petunjuk Sterilisasi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verifikasi Pre Operatif</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP OK</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Ruang OK</b></td>"+
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
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("sncn")+"</td>"+
                               "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter_bedah")+"</td>"+
                               "<td valign='top'>"+rs.getString("dokterbedah")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter_anestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("dokteranestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_identitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_area_insisi")+"</td>"+
                               "<td valign='top'>"+rs.getString("penandaan_area_operasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("lama_operasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("penayangan_radiologi")+"</td>"+
                               "<td valign='top'>"+rs.getString("penayangan_ctscan")+"</td>"+
                               "<td valign='top'>"+rs.getString("penayangan_mri")+"</td>"+
                               "<td valign='top'>"+rs.getString("antibiotik_profilaks")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama_antibiotik")+"</td>"+
                               "<td valign='top'>"+rs.getString("jam_pemberian")+"</td>"+
                               "<td valign='top'>"+rs.getString("antisipasi_kehilangan_darah")+"</td>"+
                               "<td valign='top'>"+rs.getString("hal_khusus")+"</td>"+
                               "<td valign='top'>"+rs.getString("hal_khusus_diperhatikan")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_steril")+"</td>"+
                               "<td valign='top'>"+rs.getString("petujuk_sterilisasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("verifikasi_preoperatif")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_perawat_ok")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='2900px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataTimeOutSebelumInsisi.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='2900px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA TIME-OUT SEBELUM INSISI<br><br></font>"+        
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
        runBackground(() ->tampil());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            runBackground(() ->tampil());
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
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
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void MnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTimeOutSebelumInsisiActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),30).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),29).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()+"\nID "+(finger2.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),10).toString():finger2)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirTimeOutSebelumInsisi.jasper","report","::[ Formulir Time-Out Sebelum Tindakan Insisi ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,timeout_sebelum_insisi.tanggal,"+
                    "timeout_sebelum_insisi.sncn,timeout_sebelum_insisi.tindakan,timeout_sebelum_insisi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "timeout_sebelum_insisi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,timeout_sebelum_insisi.verbal_identitas,"+
                    "timeout_sebelum_insisi.verbal_tindakan,timeout_sebelum_insisi.verbal_area_insisi,timeout_sebelum_insisi.penandaan_area_operasi,"+
                    "timeout_sebelum_insisi.lama_operasi,timeout_sebelum_insisi.penayangan_radiologi,timeout_sebelum_insisi.penayangan_ctscan,"+
                    "timeout_sebelum_insisi.penayangan_mri,timeout_sebelum_insisi.antibiotik_profilaks,timeout_sebelum_insisi.nama_antibiotik,"+
                    "timeout_sebelum_insisi.jam_pemberian,timeout_sebelum_insisi.antisipasi_kehilangan_darah,timeout_sebelum_insisi.hal_khusus,"+
                    "timeout_sebelum_insisi.hal_khusus_diperhatikan,timeout_sebelum_insisi.tanggal_steril,timeout_sebelum_insisi.petujuk_sterilisasi,"+
                    "timeout_sebelum_insisi.verifikasi_preoperatif,timeout_sebelum_insisi.nip_perawat_ok,petugas.nama "+
                    "from timeout_sebelum_insisi inner join reg_periksa on timeout_sebelum_insisi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=timeout_sebelum_insisi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=timeout_sebelum_insisi.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=timeout_sebelum_insisi.nip_perawat_ok "+
                    "where timeout_sebelum_insisi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and timeout_sebelum_insisi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnTimeOutSebelumInsisiActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,SNCN);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }   
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,Tindakan,BtnDokter2);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NmDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnDokter2.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }   
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        Valid.pindah(evt,BtnDokter,VerbalIdentitas);
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void PemberianAntibiotikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemberianAntibiotikKeyPressed
        Valid.pindah(evt,PenayanganMRI,NamaAntibiotikDIberikan);
    }//GEN-LAST:event_PemberianAntibiotikKeyPressed

    private void AreaOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaOperasiKeyPressed
        Valid.pindah(evt,VerbalArea,PerkiraanLama);
    }//GEN-LAST:event_AreaOperasiKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        if (petugas == null || !petugas.isDisplayable()) {
            petugas=new DlgCariPetugas(null,false);
            petugas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            petugas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnPetugas.requestFocus();
                    petugas=null;
                }
            });

            petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            petugas.setLocationRelativeTo(internalFrame1);
        }
        if (petugas == null) return;
        if (!petugas.isVisible()) {
            petugas.isCek();    
            petugas.emptTeks();
        }
        
        if (petugas.isVisible()) {
            petugas.toFront();
            return;
        }
        petugas.setVisible(true); 
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,VerifikasiOperatif,BtnSimpan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void SNCNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SNCNKeyPressed
        Valid.pindah(evt,Tanggal,Tindakan);
    }//GEN-LAST:event_SNCNKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,SNCN,BtnDokter);
    }//GEN-LAST:event_TindakanKeyPressed

    private void AntisipisasiKehialnganDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntisipisasiKehialnganDarahKeyPressed
        Valid.pindah(evt,JamPemberianAntibiotik,AdaHalKhusus);
    }//GEN-LAST:event_AntisipisasiKehialnganDarahKeyPressed

    private void VerbalTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalTindakanKeyPressed
        Valid.pindah(evt,VerbalIdentitas,VerbalArea);
    }//GEN-LAST:event_VerbalTindakanKeyPressed

    private void VerbalAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalAreaKeyPressed
        Valid.pindah(evt,VerbalTindakan,AreaOperasi);
    }//GEN-LAST:event_VerbalAreaKeyPressed

    private void PenayanganRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenayanganRadiologiKeyPressed
        Valid.pindah(evt,PerkiraanLama,PenayanganCTScan);
    }//GEN-LAST:event_PenayanganRadiologiKeyPressed

    private void PenayanganCTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenayanganCTScanKeyPressed
        Valid.pindah(evt,PenayanganRadiologi,PenayanganMRI);
    }//GEN-LAST:event_PenayanganCTScanKeyPressed

    private void PenayanganMRIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenayanganMRIKeyPressed
        Valid.pindah(evt,PenayanganCTScan,PemberianAntibiotik);
    }//GEN-LAST:event_PenayanganMRIKeyPressed

    private void VerbalIdentitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalIdentitasKeyPressed
        Valid.pindah(evt,BtnDokter2,VerbalTindakan);
    }//GEN-LAST:event_VerbalIdentitasKeyPressed

    private void JamPemberianAntibiotikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPemberianAntibiotikKeyPressed
        Valid.pindah(evt,NamaAntibiotikDIberikan,AntisipisasiKehialnganDarah);
    }//GEN-LAST:event_JamPemberianAntibiotikKeyPressed

    private void PerkiraanLamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerkiraanLamaKeyPressed
        Valid.pindah(evt,AreaOperasi,PenayanganRadiologi);
    }//GEN-LAST:event_PerkiraanLamaKeyPressed

    private void AdaHalKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdaHalKhususKeyPressed
        Valid.pindah(evt,AntisipisasiKehialnganDarah,HalKhususDiperhatikan);
    }//GEN-LAST:event_AdaHalKhususKeyPressed

    private void NamaAntibiotikDIberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaAntibiotikDIberikanKeyPressed
        Valid.pindah(evt,PemberianAntibiotik,JamPemberianAntibiotik);
    }//GEN-LAST:event_NamaAntibiotikDIberikanKeyPressed

    private void HalKhususDiperhatikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HalKhususDiperhatikanKeyPressed
        Valid.pindah(evt,AdaHalKhusus,TanggalSeteril);
    }//GEN-LAST:event_HalKhususDiperhatikanKeyPressed

    private void TanggalSeterilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSeterilKeyPressed
        Valid.pindah2(evt,HalKhususDiperhatikan,PetunjukSterilisasi);
    }//GEN-LAST:event_TanggalSeterilKeyPressed

    private void PetunjukSterilisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PetunjukSterilisasiKeyPressed
        Valid.pindah(evt,TanggalSeteril,VerifikasiOperatif);
    }//GEN-LAST:event_PetunjukSterilisasiKeyPressed

    private void VerifikasiOperatifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerifikasiOperatifKeyPressed
        Valid.pindah(evt,PetunjukSterilisasi,BtnPetugas);
    }//GEN-LAST:event_VerifikasiOperatifKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTimeOutSebelumInsisi dialog = new RMTimeOutSebelumInsisi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AdaHalKhusus;
    private widget.TextBox AntisipisasiKehialnganDarah;
    private widget.ComboBox AreaOperasi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HalKhususDiperhatikan;
    private widget.TextBox JamPemberianAntibiotik;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter2;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnTimeOutSebelumInsisi;
    private widget.TextBox NamaAntibiotikDIberikan;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter2;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PemberianAntibiotik;
    private widget.ComboBox PenayanganCTScan;
    private widget.ComboBox PenayanganMRI;
    private widget.ComboBox PenayanganRadiologi;
    private widget.TextBox PerkiraanLama;
    private widget.ComboBox PetunjukSterilisasi;
    private widget.TextBox SNCN;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalSeteril;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox VerbalArea;
    private widget.ComboBox VerbalIdentitas;
    private widget.ComboBox VerbalTindakan;
    private widget.ComboBox VerifikasiOperatif;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel5;
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
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,timeout_sebelum_insisi.tanggal,"+
                    "timeout_sebelum_insisi.sncn,timeout_sebelum_insisi.tindakan,timeout_sebelum_insisi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "timeout_sebelum_insisi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,timeout_sebelum_insisi.verbal_identitas,"+
                    "timeout_sebelum_insisi.verbal_tindakan,timeout_sebelum_insisi.verbal_area_insisi,timeout_sebelum_insisi.penandaan_area_operasi,"+
                    "timeout_sebelum_insisi.lama_operasi,timeout_sebelum_insisi.penayangan_radiologi,timeout_sebelum_insisi.penayangan_ctscan,"+
                    "timeout_sebelum_insisi.penayangan_mri,timeout_sebelum_insisi.antibiotik_profilaks,timeout_sebelum_insisi.nama_antibiotik,"+
                    "timeout_sebelum_insisi.jam_pemberian,timeout_sebelum_insisi.antisipasi_kehilangan_darah,timeout_sebelum_insisi.hal_khusus,"+
                    "timeout_sebelum_insisi.hal_khusus_diperhatikan,timeout_sebelum_insisi.tanggal_steril,timeout_sebelum_insisi.petujuk_sterilisasi,"+
                    "timeout_sebelum_insisi.verifikasi_preoperatif,timeout_sebelum_insisi.nip_perawat_ok,petugas.nama "+
                    "from timeout_sebelum_insisi inner join reg_periksa on timeout_sebelum_insisi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=timeout_sebelum_insisi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=timeout_sebelum_insisi.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=timeout_sebelum_insisi.nip_perawat_ok "+
                    "where timeout_sebelum_insisi.tanggal between ? and ? order by timeout_sebelum_insisi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,timeout_sebelum_insisi.tanggal,"+
                    "timeout_sebelum_insisi.sncn,timeout_sebelum_insisi.tindakan,timeout_sebelum_insisi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "timeout_sebelum_insisi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,timeout_sebelum_insisi.verbal_identitas,"+
                    "timeout_sebelum_insisi.verbal_tindakan,timeout_sebelum_insisi.verbal_area_insisi,timeout_sebelum_insisi.penandaan_area_operasi,"+
                    "timeout_sebelum_insisi.lama_operasi,timeout_sebelum_insisi.penayangan_radiologi,timeout_sebelum_insisi.penayangan_ctscan,"+
                    "timeout_sebelum_insisi.penayangan_mri,timeout_sebelum_insisi.antibiotik_profilaks,timeout_sebelum_insisi.nama_antibiotik,"+
                    "timeout_sebelum_insisi.jam_pemberian,timeout_sebelum_insisi.antisipasi_kehilangan_darah,timeout_sebelum_insisi.hal_khusus,"+
                    "timeout_sebelum_insisi.hal_khusus_diperhatikan,timeout_sebelum_insisi.tanggal_steril,timeout_sebelum_insisi.petujuk_sterilisasi,"+
                    "timeout_sebelum_insisi.verifikasi_preoperatif,timeout_sebelum_insisi.nip_perawat_ok,petugas.nama "+
                    "from timeout_sebelum_insisi inner join reg_periksa on timeout_sebelum_insisi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=timeout_sebelum_insisi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=timeout_sebelum_insisi.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=timeout_sebelum_insisi.nip_perawat_ok "+
                    "where timeout_sebelum_insisi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugas.nama like ?) "+
                    "order by timeout_sebelum_insisi.tanggal ");
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("sncn"),rs.getString("tindakan"),rs.getString("kd_dokter_bedah"),rs.getString("dokterbedah"),
                        rs.getString("kd_dokter_anestesi"),rs.getString("dokteranestesi"),rs.getString("verbal_identitas"),rs.getString("verbal_tindakan"),
                        rs.getString("verbal_area_insisi"),rs.getString("penandaan_area_operasi"),rs.getString("lama_operasi"),rs.getString("penayangan_radiologi"),
                        rs.getString("penayangan_ctscan"),rs.getString("penayangan_mri"),rs.getString("antibiotik_profilaks"),rs.getString("nama_antibiotik"),
                        rs.getString("jam_pemberian"),rs.getString("antisipasi_kehilangan_darah"),rs.getString("hal_khusus"),rs.getString("hal_khusus_diperhatikan"),
                        rs.getString("tanggal_steril"),rs.getString("petujuk_sterilisasi"),rs.getString("verifikasi_preoperatif"),rs.getString("nip_perawat_ok"),
                        rs.getString("nama")
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
        SNCN.setText("");
        Tindakan.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        KdDokter2.setText("");
        NmDokter2.setText("");
        VerbalIdentitas.setSelectedIndex(0);
        VerbalTindakan.setSelectedIndex(0);
        VerbalArea.setSelectedIndex(0);
        AreaOperasi.setSelectedIndex(0);
        PerkiraanLama.setText("");
        PenayanganRadiologi.setSelectedIndex(0);
        PenayanganCTScan.setSelectedIndex(0);
        PenayanganMRI.setSelectedIndex(0);
        PemberianAntibiotik.setSelectedIndex(0);
        NamaAntibiotikDIberikan.setText("");
        JamPemberianAntibiotik.setText("");
        AntisipisasiKehialnganDarah.setText("");
        AdaHalKhusus.setSelectedIndex(0);
        HalKhususDiperhatikan.setText("");
        TanggalSeteril.setDate(new Date());
        Tanggal.setDate(new Date());
        PetunjukSterilisasi.setSelectedIndex(0);
        VerifikasiOperatif.setSelectedIndex(0);
        SNCN.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            SNCN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdDokter2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NmDokter2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            VerbalIdentitas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            VerbalTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            VerbalArea.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            AreaOperasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            PerkiraanLama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            PenayanganRadiologi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PenayanganCTScan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            PenayanganMRI.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PemberianAntibiotik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NamaAntibiotikDIberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            JamPemberianAntibiotik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            AntisipisasiKehialnganDarah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            AdaHalKhusus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            HalKhususDiperhatikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            PetunjukSterilisasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            VerifikasiOperatif.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Valid.SetTgl(TanggalSeteril,tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    public void setNoRm(String norwt, Date tgl2,String KodeDokter,String NamaDokter,String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
        KdDokter.setText(KodeDokter);
        NmDokter.setText(NamaDokter);
        Tindakan.setText(Operasi);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>538){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,406));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettimeout_sebelum_insisi());
        BtnHapus.setEnabled(akses.gettimeout_sebelum_insisi());
        BtnEdit.setEnabled(akses.gettimeout_sebelum_insisi());
        BtnPrint.setEnabled(akses.gettimeout_sebelum_insisi()); 
    }

    private void ganti() {
        Sequel.mengedit("timeout_sebelum_insisi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,sncn=?,tindakan=?,kd_dokter_bedah=?,kd_dokter_anestesi=?,verbal_identitas=?,verbal_tindakan=?,"+
            "verbal_area_insisi=?,penandaan_area_operasi=?,lama_operasi=?,penayangan_radiologi=?,penayangan_ctscan=?,penayangan_mri=?,antibiotik_profilaks=?,nama_antibiotik=?,jam_pemberian=?,"+
            "antisipasi_kehilangan_darah=?,hal_khusus=?,hal_khusus_diperhatikan=?,tanggal_steril=?,petujuk_sterilisasi=?,verifikasi_preoperatif=?,nip_perawat_ok=?",26,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KdDokter.getText(),KdDokter2.getText(),VerbalIdentitas.getSelectedItem().toString(),VerbalTindakan.getSelectedItem().toString(),
                VerbalArea.getSelectedItem().toString(),AreaOperasi.getSelectedItem().toString(),PerkiraanLama.getText(),PenayanganRadiologi.getSelectedItem().toString(),
                PenayanganCTScan.getSelectedItem().toString(),PenayanganMRI.getSelectedItem().toString(),PemberianAntibiotik.getSelectedItem().toString(),
                NamaAntibiotikDIberikan.getText(),JamPemberianAntibiotik.getText(),AntisipisasiKehialnganDarah.getText(),AdaHalKhusus.getSelectedItem().toString(),
                HalKhususDiperhatikan.getText(),Valid.SetTgl(TanggalSeteril.getSelectedItem()+""),PetunjukSterilisasi.getSelectedItem().toString(),
                VerifikasiOperatif.getSelectedItem().toString(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        });
            
        if(tabMode.getRowCount()!=0){runBackground(() ->tampil());}
        emptTeks();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from timeout_sebelum_insisi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

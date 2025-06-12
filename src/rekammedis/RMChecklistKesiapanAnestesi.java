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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMChecklistKesiapanAnestesi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;   
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKesiapanAnestesi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","NIP","Asisten Anestesi","Kode Dokter","Dokter Anestesi","Tindakan","Teknik Anestesi","Listrik 1","Listrik 2",
            "Listrik 3","Listrik 4","Gas Medis 1","Gas Medis 2","Gas Medis 3","Gas Medis 4","Gas Medis 5","Gas Medis 6","Mesin Anes 1","Mesin Anes 2","Mesin Anes 3","Mesin Anes 4",
            "Mesin Anes 5","Jalan Napas 1","Jalan Napas 2","Jalan Napas 3","Jalan Napas 4","Jalan Napas 5","Jalan Napas 6","Jalan Napas 7","Jalan Napas 8","Jalan Napas 9","Lain-lain 1",
            "Lain-lain 2","Lain-lain 3","Lain-lain 4","Lain-lain 5","Lain-lain 6","Lain-lain 7","Lain-lain 8","Obat-obat 1","Obat-obat 2","Obat-obat 3","Obat-obat 4","Obat-obat 5",
            "Obat-obat 6","Keterangan Lainnya"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 50; i++) {
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
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==49){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        KeteranganLainnya.setDocument(new batasInput((int)1000).getKata(KeteranganLainnya));
        TeknikAnestesi.setDocument(new batasInput((int)30).getKata(TeknikAnestesi));
        Tindakan.setDocument(new batasInput((int)100).getKata(Tindakan));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
                    BtnPetugas.requestFocus();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakLaporan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
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
        jLabel23 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        Listrik3 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        GasMedis2 = new widget.ComboBox();
        Listrik4 = new widget.ComboBox();
        Listrik2 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        Listrik1 = new widget.ComboBox();
        jLabel65 = new widget.Label();
        GasMedis1 = new widget.ComboBox();
        jLabel24 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel25 = new widget.Label();
        TeknikAnestesi = new widget.TextBox();
        BtnPetugas = new widget.Button();
        NamaPetugas = new widget.TextBox();
        KodePetugas = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel59 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        GasMedis3 = new widget.ComboBox();
        jLabel64 = new widget.Label();
        GasMedis4 = new widget.ComboBox();
        jLabel66 = new widget.Label();
        GasMedis5 = new widget.ComboBox();
        jLabel67 = new widget.Label();
        GasMedis6 = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel68 = new widget.Label();
        MesinAnestesi1 = new widget.ComboBox();
        MesinAnestesi3 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        MesinAnestesi5 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        MesinAnestesi4 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        MesinAnestesi2 = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel73 = new widget.Label();
        JalanNapas1 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        JalanNapas3 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        JalanNapas2 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        JalanNapas5 = new widget.ComboBox();
        jLabel77 = new widget.Label();
        JalanNapas7 = new widget.ComboBox();
        jLabel78 = new widget.Label();
        JalanNapas9 = new widget.ComboBox();
        JalanNapas4 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        JalanNapas6 = new widget.ComboBox();
        jLabel81 = new widget.Label();
        JalanNapas8 = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel57 = new widget.Label();
        jLabel82 = new widget.Label();
        LainLain1 = new widget.ComboBox();
        jLabel83 = new widget.Label();
        LainLain2 = new widget.ComboBox();
        jLabel84 = new widget.Label();
        LainLain7 = new widget.ComboBox();
        jLabel85 = new widget.Label();
        LainLain3 = new widget.ComboBox();
        jLabel86 = new widget.Label();
        LainLain8 = new widget.ComboBox();
        jLabel87 = new widget.Label();
        LainLain4 = new widget.ComboBox();
        jLabel88 = new widget.Label();
        LainLain5 = new widget.ComboBox();
        jLabel89 = new widget.Label();
        LainLain6 = new widget.ComboBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        ObatObat1 = new widget.ComboBox();
        jLabel92 = new widget.Label();
        ObatObat2 = new widget.ComboBox();
        jLabel93 = new widget.Label();
        ObatObat3 = new widget.ComboBox();
        ObatObat4 = new widget.ComboBox();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        ObatObat5 = new widget.ComboBox();
        jLabel96 = new widget.Label();
        ObatObat6 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel97 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        KeteranganLainnya = new widget.TextArea();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakLaporan.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakLaporan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakLaporan.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakLaporan.setText("Formulir Check List Kesiapan Anestesi");
        MnCetakLaporan.setName("MnCetakLaporan"); // NOI18N
        MnCetakLaporan.setPreferredSize(new java.awt.Dimension(260, 26));
        MnCetakLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakLaporanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakLaporan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kesiapan Anestesi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-06-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-06-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1023));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(16, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(331, 10, 290, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-06-2025 22:09:35" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 130, 23);

        jLabel23.setText("Dokter Anestesi :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(381, 70, 100, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setHighlighter(null);
        KodeDokter.setName("KodeDokter"); // NOI18N
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(485, 70, 97, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(584, 70, 175, 23);

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
        BtnDokter.setBounds(761, 70, 28, 23);

        Listrik3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Listrik3.setSelectedIndex(1);
        Listrik3.setName("Listrik3"); // NOI18N
        Listrik3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Listrik3KeyPressed(evt);
            }
        });
        FormInput.add(Listrik3);
        Listrik3.setBounds(699, 180, 90, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Syringe pump terhubung dengan sumber listrik, indikator (+) ?");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(30, 180, 460, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

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

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. LISTRIK");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 100, 180, 23);

        GasMedis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis2.setSelectedIndex(1);
        GasMedis2.setName("GasMedis2"); // NOI18N
        GasMedis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis2KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis2);
        GasMedis2.setBounds(699, 290, 90, 23);

        Listrik4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Listrik4.setSelectedIndex(1);
        Listrik4.setName("Listrik4"); // NOI18N
        Listrik4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Listrik4KeyPressed(evt);
            }
        });
        FormInput.add(Listrik4);
        Listrik4.setBounds(699, 210, 90, 23);

        Listrik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Listrik2.setSelectedIndex(1);
        Listrik2.setName("Listrik2"); // NOI18N
        Listrik2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Listrik2KeyPressed(evt);
            }
        });
        FormInput.add(Listrik2);
        Listrik2.setBounds(699, 150, 90, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Layar pemantauan terhubung dengan sumber listrik, indikator (+) ?");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(30, 150, 580, 23);

        Listrik1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Listrik1.setSelectedIndex(1);
        Listrik1.setName("Listrik1"); // NOI18N
        Listrik1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Listrik1KeyPressed(evt);
            }
        });
        FormInput.add(Listrik1);
        Listrik1.setBounds(699, 120, 90, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Mesin anestesi terhubung dengan sumber listrik, indikator (+) menyala ?");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(30, 120, 490, 23);

        GasMedis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis1.setSelectedIndex(1);
        GasMedis1.setName("GasMedis1"); // NOI18N
        GasMedis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis1KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis1);
        GasMedis1.setBounds(699, 260, 90, 23);

        jLabel24.setText("Tindakan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(206, 40, 65, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(275, 40, 310, 23);

        jLabel25.setText("Teknik Anestesi :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(585, 40, 100, 23);

        TeknikAnestesi.setHighlighter(null);
        TeknikAnestesi.setName("TeknikAnestesi"); // NOI18N
        TeknikAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(TeknikAnestesi);
        TeknikAnestesi.setBounds(689, 40, 100, 23);

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
        BtnPetugas.setBounds(350, 70, 28, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(173, 70, 175, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(74, 70, 97, 23);

        jLabel26.setText("Asisten :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 70, 70, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Defibrilator terhubung dengan sumber listrik, indikator (+) ?");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(30, 210, 460, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 240, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 240, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. GAS MEDIS");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 240, 180, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Selang oksigen terhubung antara sumber gas dengan mesin anestesi ?");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(30, 260, 460, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Flow meter O2 di mesin anestesi berfungsi, aliran gas keluar dari mesin dapat dirasakan ?");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(30, 290, 500, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Compressed air terhubung antara sumber gas dengan mesin anestesi ?");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(30, 320, 500, 23);

        GasMedis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis3.setSelectedIndex(1);
        GasMedis3.setName("GasMedis3"); // NOI18N
        GasMedis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis3KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis3);
        GasMedis3.setBounds(699, 320, 90, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Flow meter \"Air\" di mesin anestesi berfungsi, aliran gas keluar mesin dapat dirasakan ?");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(30, 350, 500, 23);

        GasMedis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis4.setSelectedIndex(1);
        GasMedis4.setName("GasMedis4"); // NOI18N
        GasMedis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis4KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis4);
        GasMedis4.setBounds(699, 350, 90, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("N2O terhubung antara sumber gas dengan mesin anestesi ?");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(30, 380, 500, 23);

        GasMedis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis5.setSelectedIndex(1);
        GasMedis5.setName("GasMedis5"); // NOI18N
        GasMedis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis5KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis5);
        GasMedis5.setBounds(699, 380, 90, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Flow meter N2O di mesin anestesi berfungsi, aliran gas keluar mesin dapat dirasakan ?");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(30, 410, 500, 23);

        GasMedis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        GasMedis6.setSelectedIndex(1);
        GasMedis6.setName("GasMedis6"); // NOI18N
        GasMedis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GasMedis6KeyPressed(evt);
            }
        });
        FormInput.add(GasMedis6);
        GasMedis6.setBounds(699, 410, 90, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 440, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 440, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. MESIN ANESTESI");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 440, 180, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Power ON ?");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(30, 460, 100, 23);

        MesinAnestesi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MesinAnestesi1.setSelectedIndex(1);
        MesinAnestesi1.setName("MesinAnestesi1"); // NOI18N
        MesinAnestesi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MesinAnestesi1KeyPressed(evt);
            }
        });
        FormInput.add(MesinAnestesi1);
        MesinAnestesi1.setBounds(125, 460, 90, 23);

        MesinAnestesi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MesinAnestesi3.setSelectedIndex(1);
        MesinAnestesi3.setName("MesinAnestesi3"); // NOI18N
        MesinAnestesi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MesinAnestesi3KeyPressed(evt);
            }
        });
        FormInput.add(MesinAnestesi3);
        MesinAnestesi3.setBounds(699, 460, 90, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Self calibration : DONE ?");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(570, 460, 140, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Tidak ada kebocoran sirkuit nafas ?");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(258, 490, 180, 23);

        MesinAnestesi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MesinAnestesi5.setSelectedIndex(1);
        MesinAnestesi5.setName("MesinAnestesi5"); // NOI18N
        MesinAnestesi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MesinAnestesi5KeyPressed(evt);
            }
        });
        FormInput.add(MesinAnestesi5);
        MesinAnestesi5.setBounds(438, 490, 90, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Zat volatil terisi ?");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(30, 490, 100, 23);

        MesinAnestesi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MesinAnestesi4.setSelectedIndex(1);
        MesinAnestesi4.setName("MesinAnestesi4"); // NOI18N
        MesinAnestesi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MesinAnestesi4KeyPressed(evt);
            }
        });
        FormInput.add(MesinAnestesi4);
        MesinAnestesi4.setBounds(125, 490, 90, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Absorber CO2 dalam kondisi baik ?");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(258, 460, 180, 23);

        MesinAnestesi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MesinAnestesi2.setSelectedIndex(1);
        MesinAnestesi2.setName("MesinAnestesi2"); // NOI18N
        MesinAnestesi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MesinAnestesi2KeyPressed(evt);
            }
        });
        FormInput.add(MesinAnestesi2);
        MesinAnestesi2.setBounds(438, 460, 90, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 520, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 520, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. MANAJEMEN JALAN NAPAS");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 520, 180, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Sungkup muka dalam ukuran yang benar ?");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(30, 540, 300, 23);

        JalanNapas1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas1.setSelectedIndex(1);
        JalanNapas1.setName("JalanNapas1"); // NOI18N
        JalanNapas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas1KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas1);
        JalanNapas1.setBounds(320, 540, 90, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Oropharygeal airway (Guedel) dalam ukuran yang benar ?");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(30, 570, 300, 23);

        JalanNapas3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas3.setSelectedIndex(1);
        JalanNapas3.setName("JalanNapas3"); // NOI18N
        JalanNapas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas3KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas3);
        JalanNapas3.setBounds(320, 570, 90, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Batang laringoskop berisi baterai ? ");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(505, 540, 200, 23);

        JalanNapas2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas2.setSelectedIndex(1);
        JalanNapas2.setName("JalanNapas2"); // NOI18N
        JalanNapas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas2KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas2);
        JalanNapas2.setBounds(699, 540, 90, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Bilah laringoskop dalam ukuran yang benar ?");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(30, 600, 300, 23);

        JalanNapas5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas5.setSelectedIndex(1);
        JalanNapas5.setName("JalanNapas5"); // NOI18N
        JalanNapas5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas5KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas5);
        JalanNapas5.setBounds(320, 600, 90, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Gagang dan bilah laringoskop berfungsi baik ?");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(30, 630, 300, 23);

        JalanNapas7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas7.setSelectedIndex(1);
        JalanNapas7.setName("JalanNapas7"); // NOI18N
        JalanNapas7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas7KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas7);
        JalanNapas7.setBounds(320, 630, 90, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("ETT atau LMA dalam ukuran yang benar, tidak bocor ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(30, 660, 300, 23);

        JalanNapas9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas9.setSelectedIndex(1);
        JalanNapas9.setName("JalanNapas9"); // NOI18N
        JalanNapas9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas9KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas9);
        JalanNapas9.setBounds(320, 660, 90, 23);

        JalanNapas4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas4.setSelectedIndex(1);
        JalanNapas4.setName("JalanNapas4"); // NOI18N
        JalanNapas4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas4KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas4);
        JalanNapas4.setBounds(699, 570, 90, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Stilet (introduser) ?");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(505, 570, 200, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Semprit untuk mengembangkan cuff ?");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(505, 600, 200, 23);

        JalanNapas6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas6.setSelectedIndex(1);
        JalanNapas6.setName("JalanNapas6"); // NOI18N
        JalanNapas6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas6KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas6);
        JalanNapas6.setBounds(699, 600, 90, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Forceps Magill ?");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(505, 630, 200, 23);

        JalanNapas8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        JalanNapas8.setSelectedIndex(1);
        JalanNapas8.setName("JalanNapas8"); // NOI18N
        JalanNapas8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanNapas8KeyPressed(evt);
            }
        });
        FormInput.add(JalanNapas8);
        JalanNapas8.setBounds(699, 630, 90, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 690, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 690, 810, 1);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("V. LAIN-LAIN");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 690, 180, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Stetoskop tersedia ?");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(30, 710, 140, 23);

        LainLain1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain1.setSelectedIndex(1);
        LainLain1.setName("LainLain1"); // NOI18N
        LainLain1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain1KeyPressed(evt);
            }
        });
        FormInput.add(LainLain1);
        LainLain1.setBounds(165, 710, 90, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Suction berfungsi baik ?");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(313, 710, 130, 23);

        LainLain2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain2.setSelectedIndex(1);
        LainLain2.setName("LainLain2"); // NOI18N
        LainLain2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain2KeyPressed(evt);
            }
        });
        FormInput.add(LainLain2);
        LainLain2.setBounds(440, 710, 90, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Selang suction terhubung, kateter suction dalam ukuran yang benar ?");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(30, 770, 410, 23);

        LainLain7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain7.setSelectedIndex(1);
        LainLain7.setName("LainLain7"); // NOI18N
        LainLain7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain7KeyPressed(evt);
            }
        });
        FormInput.add(LainLain7);
        LainLain7.setBounds(440, 770, 90, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("Plester untuk fiksasi ?");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(582, 710, 120, 23);

        LainLain3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain3.setSelectedIndex(1);
        LainLain3.setName("LainLain3"); // NOI18N
        LainLain3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain3KeyPressed(evt);
            }
        });
        FormInput.add(LainLain3);
        LainLain3.setBounds(699, 710, 90, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Blanket rolll hemotherm/radiant heater terhubung sumber listrik, berfungsi baik ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(30, 800, 410, 23);

        LainLain8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain8.setSelectedIndex(1);
        LainLain8.setName("LainLain8"); // NOI18N
        LainLain8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain8KeyPressed(evt);
            }
        });
        FormInput.add(LainLain8);
        LainLain8.setBounds(440, 800, 90, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Blanket roll dilapisi alas ?");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(30, 740, 140, 23);

        LainLain4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain4.setSelectedIndex(1);
        LainLain4.setName("LainLain4"); // NOI18N
        LainLain4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain4KeyPressed(evt);
            }
        });
        FormInput.add(LainLain4);
        LainLain4.setBounds(165, 740, 90, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Lidocaine spray/ jelly ?");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(313, 740, 130, 23);

        LainLain5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain5.setSelectedIndex(1);
        LainLain5.setName("LainLain5"); // NOI18N
        LainLain5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain5KeyPressed(evt);
            }
        });
        FormInput.add(LainLain5);
        LainLain5.setBounds(440, 740, 90, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Defibrillator jelly ?");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(582, 740, 120, 23);

        LainLain6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LainLain6.setSelectedIndex(1);
        LainLain6.setName("LainLain6"); // NOI18N
        LainLain6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLain6KeyPressed(evt);
            }
        });
        FormInput.add(LainLain6);
        LainLain6.setBounds(699, 740, 90, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 830, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 830, 810, 1);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("VI. OBAT-OBAT");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(10, 830, 180, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Epinefrin ?");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(30, 850, 70, 23);

        ObatObat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat1.setSelectedIndex(1);
        ObatObat1.setName("ObatObat1"); // NOI18N
        ObatObat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat1KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat1);
        ObatObat1.setBounds(90, 850, 90, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("Atropin ?");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(225, 850, 60, 23);

        ObatObat2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat2.setSelectedIndex(1);
        ObatObat2.setName("ObatObat2"); // NOI18N
        ObatObat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat2KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat2);
        ObatObat2.setBounds(278, 850, 90, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Antibiotika ?");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(410, 850, 70, 23);

        ObatObat3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat3.setSelectedIndex(1);
        ObatObat3.setName("ObatObat3"); // NOI18N
        ObatObat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat3KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat3);
        ObatObat3.setBounds(480, 850, 90, 23);

        ObatObat4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat4.setSelectedIndex(1);
        ObatObat4.setName("ObatObat4"); // NOI18N
        ObatObat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat4KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat4);
        ObatObat4.setBounds(699, 850, 90, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("Pelumpuh otot ?");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(610, 850, 90, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Sedatif (midazolam/propofol/etomidat/ketamin/tiopental) ?");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(30, 880, 300, 23);

        ObatObat5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat5.setSelectedIndex(1);
        ObatObat5.setName("ObatObat5"); // NOI18N
        ObatObat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat5KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat5);
        ObatObat5.setBounds(330, 880, 90, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Opiat/opioid ?");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(470, 880, 100, 23);

        ObatObat6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatObat6.setSelectedIndex(1);
        ObatObat6.setName("ObatObat6"); // NOI18N
        ObatObat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatObat6KeyPressed(evt);
            }
        });
        FormInput.add(ObatObat6);
        ObatObat6.setBounds(550, 880, 90, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 910, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 910, 810, 1);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("VII. KETERANGAN LAINNYA");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(10, 910, 180, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        KeteranganLainnya.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeteranganLainnya.setColumns(20);
        KeteranganLainnya.setRows(10);
        KeteranganLainnya.setName("KeteranganLainnya"); // NOI18N
        KeteranganLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLainnyaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(KeteranganLainnya);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(30, 930, 759, 83);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Anestesi");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Asisten Anestesi");
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
            Valid.pindah(evt,KeteranganLainnya,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        }else if(KodeDokter.getText().trim().equals("")||NamaDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Anestesi");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Asisten Anestesi");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;
                
                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asisten Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Teknik Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 7</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 8</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 9</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 7</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 8</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='4000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataChecklistKesiapanAnestesi.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA CHECK LIST KESIAPAN ANESTESI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Asisten Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Teknik Anestesi</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Listrik 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gas Medis 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mesin Anes 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 7</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 8</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Napas 9</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 7</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lain-lain 8</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 1</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 2</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 3</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 4</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 5</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat-obat 6</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,24).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,25).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,26).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,27).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,28).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,29).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,30).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,31).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,32).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,33).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,34).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,35).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,36).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,37).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,38).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,39).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,40).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,41).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,42).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,43).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,44).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,45).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,46).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,47).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,48).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,49).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbObat.getValueAt(i,50).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='4000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
                            
                            f = new File("DataChecklistKesiapanAnestesi.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA CHECK LIST KESIAPAN ANESTESI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Rawat\";\"No.RM\";\"Nama Pasien\";\"Tgl.Lahir\";\"J.K.\";\"Tanggal\";\"NIP\";\"Asisten Anestesi\";\"Kode Dokter\";\"Dokter Anestesi\";\"Tindakan\";\"Teknik Anestesi\";\"Listrik 1\";\"Listrik 2\";\"Listrik 3\";\"Listrik 4\";\"Gas Medis 1\";\"Gas Medis 2\";\"Gas Medis 3\";\"Gas Medis 4\";\"Gas Medis 5\";\"Gas Medis 6\";\"Mesin Anes 1\";\"Mesin Anes 2\";\"Mesin Anes 3\";\"Mesin Anes 4\";\"Mesin Anes 5\";\"Jalan Napas 1\";\"Jalan Napas 2\";\"Jalan Napas 3\";\"Jalan Napas 4\";\"Jalan Napas 5\";\"Jalan Napas 6\";\"Jalan Napas 7\";\"Jalan Napas 8\";\"Jalan Napas 9\";\"Lain-lain 1\";\"Lain-lain 2\";\"Lain-lain 3\";\"Lain-lain 4\";\"Lain-lain 5\";\"Lain-lain 6\";\"Lain-lain 7\";\"Lain-lain 8\";\"Obat-obat 1\";\"Obat-obat 2\";\"Obat-obat 3\";\"Obat-obat 4\";\"Obat-obat 5\";\"Obat-obat 6\";\"Keterangan Lainnya\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbObat.getValueAt(i,0).toString()).append("\";\"").append(tbObat.getValueAt(i,1).toString()).append("\";\"").append(tbObat.getValueAt(i,2).toString()).append("\";\"").append(tbObat.getValueAt(i,3).toString()).append("\";\"").append(tbObat.getValueAt(i,4).toString()).append("\";\"").append(tbObat.getValueAt(i,5).toString()).append("\";\"").append(tbObat.getValueAt(i,6).toString()).append("\";\"").append(tbObat.getValueAt(i,7).toString()).append("\";\"").append(tbObat.getValueAt(i,8).toString()).append("\";\"").append(tbObat.getValueAt(i,9).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,10).toString()).append("\";\"").append(tbObat.getValueAt(i,11).toString()).append("\";\"").append(tbObat.getValueAt(i,12).toString()).append("\";\"").append(tbObat.getValueAt(i,13).toString()).append("\";\"").append(tbObat.getValueAt(i,14).toString()).append("\";\"").append(tbObat.getValueAt(i,15).toString()).append("\";\"").append(tbObat.getValueAt(i,16).toString()).append("\";\"").append(tbObat.getValueAt(i,17).toString()).append("\";\"").append(tbObat.getValueAt(i,18).toString()).append("\";\"").append(tbObat.getValueAt(i,19).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,20).toString()).append("\";\"").append(tbObat.getValueAt(i,21).toString()).append("\";\"").append(tbObat.getValueAt(i,22).toString()).append("\";\"").append(tbObat.getValueAt(i,23).toString()).append("\";\"").append(tbObat.getValueAt(i,24).toString()).append("\";\"").append(tbObat.getValueAt(i,25).toString()).append("\";\"").append(tbObat.getValueAt(i,26).toString()).append("\";\"").append(tbObat.getValueAt(i,27).toString()).append("\";\"").append(tbObat.getValueAt(i,28).toString()).append("\";\"").append(tbObat.getValueAt(i,29).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,30).toString()).append("\";\"").append(tbObat.getValueAt(i,31).toString()).append("\";\"").append(tbObat.getValueAt(i,32).toString()).append("\";\"").append(tbObat.getValueAt(i,33).toString()).append("\";\"").append(tbObat.getValueAt(i,34).toString()).append("\";\"").append(tbObat.getValueAt(i,35).toString()).append("\";\"").append(tbObat.getValueAt(i,36).toString()).append("\";\"").append(tbObat.getValueAt(i,37).toString()).append("\";\"").append(tbObat.getValueAt(i,38).toString()).append("\";\"").append(tbObat.getValueAt(i,39).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,40).toString()).append("\";\"").append(tbObat.getValueAt(i,41).toString()).append("\";\"").append(tbObat.getValueAt(i,42).toString()).append("\";\"").append(tbObat.getValueAt(i,43).toString()).append("\";\"").append(tbObat.getValueAt(i,44).toString()).append("\";\"").append(tbObat.getValueAt(i,45).toString()).append("\";\"").append(tbObat.getValueAt(i,46).toString()).append("\";\"").append(tbObat.getValueAt(i,47).toString()).append("\";\"").append(tbObat.getValueAt(i,48).toString()).append("\";\"").append(tbObat.getValueAt(i,49).toString()).append("\";\"").
                                                         append(tbObat.getValueAt(i,50).toString()).append("\"\n");
                            }
                            f = new File("DataChecklistKesiapanAnestesi.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
                htmlContent=null;
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
            tampil();
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

    private void MnCetakLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakLaporanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=pegawai.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),6).toString():finger)+"\n"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            String finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=pegawai.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),8).toString():finger)+"\n"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            Valid.MyReportqry("rptFormulirChecklistKesiapanAnestesi.jasper","report","::[ Formulir Check List Kesiapan Anestesi ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kesiapan_anestesi.tanggal,"+
                    "checklist_kesiapan_anestesi.nip,petugas.nama,checklist_kesiapan_anestesi.kd_dokter,dokter.nm_dokter,checklist_kesiapan_anestesi.tindakan,"+
                    "checklist_kesiapan_anestesi.teknik_anestesi,checklist_kesiapan_anestesi.listrik1,checklist_kesiapan_anestesi.listrik2,checklist_kesiapan_anestesi.listrik3,"+
                    "checklist_kesiapan_anestesi.listrik4,checklist_kesiapan_anestesi.gasmedis1,checklist_kesiapan_anestesi.gasmedis2,checklist_kesiapan_anestesi.gasmedis3,"+
                    "checklist_kesiapan_anestesi.gasmedis4,checklist_kesiapan_anestesi.gasmedis5,checklist_kesiapan_anestesi.gasmedis6,checklist_kesiapan_anestesi.mesinanes1,"+
                    "checklist_kesiapan_anestesi.mesinanes2,checklist_kesiapan_anestesi.mesinanes3,checklist_kesiapan_anestesi.mesinanes4,checklist_kesiapan_anestesi.mesinanes5,"+
                    "checklist_kesiapan_anestesi.jalannapas1,checklist_kesiapan_anestesi.jalannapas2,checklist_kesiapan_anestesi.jalannapas3,checklist_kesiapan_anestesi.jalannapas4,"+
                    "checklist_kesiapan_anestesi.jalannapas5,checklist_kesiapan_anestesi.jalannapas6,checklist_kesiapan_anestesi.jalannapas7,checklist_kesiapan_anestesi.jalannapas8,"+
                    "checklist_kesiapan_anestesi.jalannapas9,checklist_kesiapan_anestesi.lainlain1,checklist_kesiapan_anestesi.lainlain2,checklist_kesiapan_anestesi.lainlain3,"+
                    "checklist_kesiapan_anestesi.lainlain4,checklist_kesiapan_anestesi.lainlain5,checklist_kesiapan_anestesi.lainlain6,checklist_kesiapan_anestesi.lainlain7,"+
                    "checklist_kesiapan_anestesi.lainlain8,checklist_kesiapan_anestesi.obatobat1,checklist_kesiapan_anestesi.obatobat2,checklist_kesiapan_anestesi.obatobat3,"+
                    "checklist_kesiapan_anestesi.obatobat4,checklist_kesiapan_anestesi.obatobat5,checklist_kesiapan_anestesi.obatobat6,checklist_kesiapan_anestesi.keterangan_lainnya "+
                    "from checklist_kesiapan_anestesi inner join reg_periksa on checklist_kesiapan_anestesi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=checklist_kesiapan_anestesi.nip "+
                    "inner join dokter on dokter.kd_dokter=checklist_kesiapan_anestesi.kd_dokter "+
                    "where checklist_kesiapan_anestesi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kesiapan_anestesi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnCetakLaporanActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,BtnDokter);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        DlgCariDokter dokter=new DlgCariDokter(null,false);
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){  
                    KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
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
        dokter.emptTeks();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       Valid.pindah(evt,TeknikAnestesi,Listrik1);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void Listrik3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Listrik3KeyPressed
       Valid.pindah(evt,GasMedis2,GasMedis4);
    }//GEN-LAST:event_Listrik3KeyPressed

    private void GasMedis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis2KeyPressed
        Valid.pindah(evt,GasMedis1,GasMedis3);
    }//GEN-LAST:event_GasMedis2KeyPressed

    private void Listrik4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Listrik4KeyPressed
        Valid.pindah(evt,Listrik3,GasMedis1);
    }//GEN-LAST:event_Listrik4KeyPressed

    private void Listrik2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Listrik2KeyPressed
        Valid.pindah(evt,Listrik1,Listrik3);
    }//GEN-LAST:event_Listrik2KeyPressed

    private void Listrik1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Listrik1KeyPressed
        Valid.pindah(evt,BtnDokter,Listrik2);
    }//GEN-LAST:event_Listrik1KeyPressed

    private void GasMedis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis1KeyPressed
        Valid.pindah(evt,Listrik4,GasMedis2);
    }//GEN-LAST:event_GasMedis1KeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,PerhatianUtamaFasePemulihan,BtnSimpan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void GasMedis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis3KeyPressed
        Valid.pindah(evt,GasMedis2,GasMedis4);
    }//GEN-LAST:event_GasMedis3KeyPressed

    private void GasMedis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis4KeyPressed
        Valid.pindah(evt,GasMedis3,GasMedis5);
    }//GEN-LAST:event_GasMedis4KeyPressed

    private void GasMedis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis5KeyPressed
        Valid.pindah(evt,GasMedis4,GasMedis6);
    }//GEN-LAST:event_GasMedis5KeyPressed

    private void GasMedis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GasMedis6KeyPressed
        Valid.pindah(evt,GasMedis5,MesinAnestesi1);
    }//GEN-LAST:event_GasMedis6KeyPressed

    private void MesinAnestesi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MesinAnestesi1KeyPressed
        Valid.pindah(evt,GasMedis6,MesinAnestesi2);
    }//GEN-LAST:event_MesinAnestesi1KeyPressed

    private void MesinAnestesi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MesinAnestesi3KeyPressed
        Valid.pindah(evt,MesinAnestesi2,MesinAnestesi4);
    }//GEN-LAST:event_MesinAnestesi3KeyPressed

    private void MesinAnestesi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MesinAnestesi5KeyPressed
        Valid.pindah(evt,MesinAnestesi4,JalanNapas1);
    }//GEN-LAST:event_MesinAnestesi5KeyPressed

    private void MesinAnestesi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MesinAnestesi4KeyPressed
        Valid.pindah(evt,MesinAnestesi3,MesinAnestesi5);
    }//GEN-LAST:event_MesinAnestesi4KeyPressed

    private void MesinAnestesi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MesinAnestesi2KeyPressed
        Valid.pindah(evt,MesinAnestesi1,MesinAnestesi3);
    }//GEN-LAST:event_MesinAnestesi2KeyPressed

    private void JalanNapas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas1KeyPressed
        Valid.pindah(evt,MesinAnestesi5,JalanNapas2);
    }//GEN-LAST:event_JalanNapas1KeyPressed

    private void JalanNapas3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas3KeyPressed
        Valid.pindah(evt,JalanNapas2,JalanNapas4);
    }//GEN-LAST:event_JalanNapas3KeyPressed

    private void JalanNapas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas2KeyPressed
        Valid.pindah(evt,JalanNapas1,JalanNapas3);
    }//GEN-LAST:event_JalanNapas2KeyPressed

    private void JalanNapas5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas5KeyPressed
        Valid.pindah(evt,JalanNapas4,JalanNapas6);
    }//GEN-LAST:event_JalanNapas5KeyPressed

    private void JalanNapas7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas7KeyPressed
        Valid.pindah(evt,JalanNapas6,JalanNapas8);
    }//GEN-LAST:event_JalanNapas7KeyPressed

    private void JalanNapas9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas9KeyPressed
        Valid.pindah(evt,JalanNapas8,LainLain1);
    }//GEN-LAST:event_JalanNapas9KeyPressed

    private void JalanNapas4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas4KeyPressed
        Valid.pindah(evt,JalanNapas3,JalanNapas5);
    }//GEN-LAST:event_JalanNapas4KeyPressed

    private void JalanNapas6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas6KeyPressed
        Valid.pindah(evt,JalanNapas5,JalanNapas7);
    }//GEN-LAST:event_JalanNapas6KeyPressed

    private void JalanNapas8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanNapas8KeyPressed
        Valid.pindah(evt,JalanNapas7,JalanNapas9);
    }//GEN-LAST:event_JalanNapas8KeyPressed

    private void LainLain1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain1KeyPressed
        Valid.pindah(evt,JalanNapas9,LainLain2);
    }//GEN-LAST:event_LainLain1KeyPressed

    private void LainLain2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain2KeyPressed
        Valid.pindah(evt,LainLain1,LainLain3);
    }//GEN-LAST:event_LainLain2KeyPressed

    private void LainLain7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain7KeyPressed
        Valid.pindah(evt,LainLain6,LainLain8);
    }//GEN-LAST:event_LainLain7KeyPressed

    private void LainLain3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain3KeyPressed
        Valid.pindah(evt,LainLain2,LainLain4);
    }//GEN-LAST:event_LainLain3KeyPressed

    private void LainLain8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain8KeyPressed
        Valid.pindah(evt,LainLain7,ObatObat1);
    }//GEN-LAST:event_LainLain8KeyPressed

    private void LainLain4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain4KeyPressed
        Valid.pindah(evt,LainLain3,LainLain5);
    }//GEN-LAST:event_LainLain4KeyPressed

    private void LainLain5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain5KeyPressed
        Valid.pindah(evt,LainLain4,LainLain6);
    }//GEN-LAST:event_LainLain5KeyPressed

    private void LainLain6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLain6KeyPressed
        Valid.pindah(evt,LainLain5,LainLain7);
    }//GEN-LAST:event_LainLain6KeyPressed

    private void ObatObat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat1KeyPressed
        Valid.pindah(evt,LainLain8,ObatObat2);
    }//GEN-LAST:event_ObatObat1KeyPressed

    private void ObatObat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat2KeyPressed
        Valid.pindah(evt,ObatObat1,ObatObat3);
    }//GEN-LAST:event_ObatObat2KeyPressed

    private void ObatObat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat3KeyPressed
        Valid.pindah(evt,ObatObat2,ObatObat4);
    }//GEN-LAST:event_ObatObat3KeyPressed

    private void ObatObat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat4KeyPressed
        Valid.pindah(evt,ObatObat3,ObatObat5);
    }//GEN-LAST:event_ObatObat4KeyPressed

    private void ObatObat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat5KeyPressed
        Valid.pindah(evt,ObatObat4,ObatObat6);
    }//GEN-LAST:event_ObatObat5KeyPressed

    private void ObatObat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatObat6KeyPressed
        Valid.pindah(evt,ObatObat5,KeteranganLainnya);
    }//GEN-LAST:event_ObatObat6KeyPressed

    private void KeteranganLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLainnyaKeyPressed
        Valid.pindah(evt,ObatObat6,BtnSimpan);
    }//GEN-LAST:event_KeteranganLainnyaKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,Tanggal,TeknikAnestesi);
    }//GEN-LAST:event_TindakanKeyPressed

    private void TeknikAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikAnestesiKeyPressed
        Valid.pindah(evt,Tindakan,BtnDokter);
    }//GEN-LAST:event_TeknikAnestesiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKesiapanAnestesi dialog = new RMChecklistKesiapanAnestesi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GasMedis1;
    private widget.ComboBox GasMedis2;
    private widget.ComboBox GasMedis3;
    private widget.ComboBox GasMedis4;
    private widget.ComboBox GasMedis5;
    private widget.ComboBox GasMedis6;
    private widget.TextBox JK;
    private widget.ComboBox JalanNapas1;
    private widget.ComboBox JalanNapas2;
    private widget.ComboBox JalanNapas3;
    private widget.ComboBox JalanNapas4;
    private widget.ComboBox JalanNapas5;
    private widget.ComboBox JalanNapas6;
    private widget.ComboBox JalanNapas7;
    private widget.ComboBox JalanNapas8;
    private widget.ComboBox JalanNapas9;
    private widget.TextArea KeteranganLainnya;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.ComboBox LainLain1;
    private widget.ComboBox LainLain2;
    private widget.ComboBox LainLain3;
    private widget.ComboBox LainLain4;
    private widget.ComboBox LainLain5;
    private widget.ComboBox LainLain6;
    private widget.ComboBox LainLain7;
    private widget.ComboBox LainLain8;
    private widget.ComboBox Listrik1;
    private widget.ComboBox Listrik2;
    private widget.ComboBox Listrik3;
    private widget.ComboBox Listrik4;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MesinAnestesi1;
    private widget.ComboBox MesinAnestesi2;
    private widget.ComboBox MesinAnestesi3;
    private widget.ComboBox MesinAnestesi4;
    private widget.ComboBox MesinAnestesi5;
    private javax.swing.JMenuItem MnCetakLaporan;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaPetugas;
    private widget.ComboBox ObatObat1;
    private widget.ComboBox ObatObat2;
    private widget.ComboBox ObatObat3;
    private widget.ComboBox ObatObat4;
    private widget.ComboBox ObatObat5;
    private widget.ComboBox ObatObat6;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TeknikAnestesi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
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
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kesiapan_anestesi.tanggal,"+
                    "checklist_kesiapan_anestesi.nip,petugas.nama,checklist_kesiapan_anestesi.kd_dokter,dokter.nm_dokter,checklist_kesiapan_anestesi.tindakan,"+
                    "checklist_kesiapan_anestesi.teknik_anestesi,checklist_kesiapan_anestesi.listrik1,checklist_kesiapan_anestesi.listrik2,checklist_kesiapan_anestesi.listrik3,"+
                    "checklist_kesiapan_anestesi.listrik4,checklist_kesiapan_anestesi.gasmedis1,checklist_kesiapan_anestesi.gasmedis2,checklist_kesiapan_anestesi.gasmedis3,"+
                    "checklist_kesiapan_anestesi.gasmedis4,checklist_kesiapan_anestesi.gasmedis5,checklist_kesiapan_anestesi.gasmedis6,checklist_kesiapan_anestesi.mesinanes1,"+
                    "checklist_kesiapan_anestesi.mesinanes2,checklist_kesiapan_anestesi.mesinanes3,checklist_kesiapan_anestesi.mesinanes4,checklist_kesiapan_anestesi.mesinanes5,"+
                    "checklist_kesiapan_anestesi.jalannapas1,checklist_kesiapan_anestesi.jalannapas2,checklist_kesiapan_anestesi.jalannapas3,checklist_kesiapan_anestesi.jalannapas4,"+
                    "checklist_kesiapan_anestesi.jalannapas5,checklist_kesiapan_anestesi.jalannapas6,checklist_kesiapan_anestesi.jalannapas7,checklist_kesiapan_anestesi.jalannapas8,"+
                    "checklist_kesiapan_anestesi.jalannapas9,checklist_kesiapan_anestesi.lainlain1,checklist_kesiapan_anestesi.lainlain2,checklist_kesiapan_anestesi.lainlain3,"+
                    "checklist_kesiapan_anestesi.lainlain4,checklist_kesiapan_anestesi.lainlain5,checklist_kesiapan_anestesi.lainlain6,checklist_kesiapan_anestesi.lainlain7,"+
                    "checklist_kesiapan_anestesi.lainlain8,checklist_kesiapan_anestesi.obatobat1,checklist_kesiapan_anestesi.obatobat2,checklist_kesiapan_anestesi.obatobat3,"+
                    "checklist_kesiapan_anestesi.obatobat4,checklist_kesiapan_anestesi.obatobat5,checklist_kesiapan_anestesi.obatobat6,checklist_kesiapan_anestesi.keterangan_lainnya "+
                    "from checklist_kesiapan_anestesi inner join reg_periksa on checklist_kesiapan_anestesi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=checklist_kesiapan_anestesi.nip "+
                    "inner join dokter on dokter.kd_dokter=checklist_kesiapan_anestesi.kd_dokter "+
                    "where checklist_kesiapan_anestesi.tanggal between ? and ? order by checklist_kesiapan_anestesi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kesiapan_anestesi.tanggal,"+
                    "checklist_kesiapan_anestesi.nip,petugas.nama,checklist_kesiapan_anestesi.kd_dokter,dokter.nm_dokter,checklist_kesiapan_anestesi.tindakan,"+
                    "checklist_kesiapan_anestesi.teknik_anestesi,checklist_kesiapan_anestesi.listrik1,checklist_kesiapan_anestesi.listrik2,checklist_kesiapan_anestesi.listrik3,"+
                    "checklist_kesiapan_anestesi.listrik4,checklist_kesiapan_anestesi.gasmedis1,checklist_kesiapan_anestesi.gasmedis2,checklist_kesiapan_anestesi.gasmedis3,"+
                    "checklist_kesiapan_anestesi.gasmedis4,checklist_kesiapan_anestesi.gasmedis5,checklist_kesiapan_anestesi.gasmedis6,checklist_kesiapan_anestesi.mesinanes1,"+
                    "checklist_kesiapan_anestesi.mesinanes2,checklist_kesiapan_anestesi.mesinanes3,checklist_kesiapan_anestesi.mesinanes4,checklist_kesiapan_anestesi.mesinanes5,"+
                    "checklist_kesiapan_anestesi.jalannapas1,checklist_kesiapan_anestesi.jalannapas2,checklist_kesiapan_anestesi.jalannapas3,checklist_kesiapan_anestesi.jalannapas4,"+
                    "checklist_kesiapan_anestesi.jalannapas5,checklist_kesiapan_anestesi.jalannapas6,checklist_kesiapan_anestesi.jalannapas7,checklist_kesiapan_anestesi.jalannapas8,"+
                    "checklist_kesiapan_anestesi.jalannapas9,checklist_kesiapan_anestesi.lainlain1,checklist_kesiapan_anestesi.lainlain2,checklist_kesiapan_anestesi.lainlain3,"+
                    "checklist_kesiapan_anestesi.lainlain4,checklist_kesiapan_anestesi.lainlain5,checklist_kesiapan_anestesi.lainlain6,checklist_kesiapan_anestesi.lainlain7,"+
                    "checklist_kesiapan_anestesi.lainlain8,checklist_kesiapan_anestesi.obatobat1,checklist_kesiapan_anestesi.obatobat2,checklist_kesiapan_anestesi.obatobat3,"+
                    "checklist_kesiapan_anestesi.obatobat4,checklist_kesiapan_anestesi.obatobat5,checklist_kesiapan_anestesi.obatobat6,checklist_kesiapan_anestesi.keterangan_lainnya "+
                    "from checklist_kesiapan_anestesi inner join reg_periksa on checklist_kesiapan_anestesi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=checklist_kesiapan_anestesi.nip "+
                    "inner join dokter on dokter.kd_dokter=checklist_kesiapan_anestesi.kd_dokter "+
                    "where checklist_kesiapan_anestesi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or petugas.nama like ? or checklist_kesiapan_anestesi.nip like ? or dokter.nm_dokter like ? or "+
                    "checklist_kesiapan_anestesi.kd_dokter like ?) order by checklist_kesiapan_anestesi.tanggal ");
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
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("nip"),rs.getString("nama"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("tindakan"),rs.getString("teknik_anestesi"),rs.getString("listrik1"),rs.getString("listrik2"),rs.getString("listrik3"),
                        rs.getString("listrik4"),rs.getString("gasmedis1"),rs.getString("gasmedis2"),rs.getString("gasmedis3"),rs.getString("gasmedis4"),
                        rs.getString("gasmedis5"),rs.getString("gasmedis6"),rs.getString("mesinanes1"),rs.getString("mesinanes2"),rs.getString("mesinanes3"),
                        rs.getString("mesinanes4"),rs.getString("mesinanes5"),rs.getString("jalannapas1"),rs.getString("jalannapas2"),rs.getString("jalannapas3"),
                        rs.getString("jalannapas4"),rs.getString("jalannapas5"),rs.getString("jalannapas6"),rs.getString("jalannapas7"),rs.getString("jalannapas8"),
                        rs.getString("jalannapas9"),rs.getString("lainlain1"),rs.getString("lainlain2"),rs.getString("lainlain3"),rs.getString("lainlain4"),
                        rs.getString("lainlain5"),rs.getString("lainlain6"),rs.getString("lainlain7"),rs.getString("lainlain8"),rs.getString("obatobat1"),
                        rs.getString("obatobat2"),rs.getString("obatobat3"),rs.getString("obatobat4"),rs.getString("obatobat5"),rs.getString("obatobat6"),
                        rs.getString("keterangan_lainnya")
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
        Listrik1.setSelectedIndex(1);
        Listrik2.setSelectedIndex(1);
        Listrik3.setSelectedIndex(1);
        Listrik4.setSelectedIndex(1);
        GasMedis1.setSelectedIndex(1);
        GasMedis2.setSelectedIndex(1);
        GasMedis3.setSelectedIndex(1);
        GasMedis4.setSelectedIndex(1);
        GasMedis5.setSelectedIndex(1);
        GasMedis6.setSelectedIndex(1);
        MesinAnestesi1.setSelectedIndex(1);
        MesinAnestesi2.setSelectedIndex(1);
        MesinAnestesi3.setSelectedIndex(1);
        MesinAnestesi4.setSelectedIndex(1);
        MesinAnestesi5.setSelectedIndex(1);
        JalanNapas1.setSelectedIndex(1);
        JalanNapas2.setSelectedIndex(1);
        JalanNapas3.setSelectedIndex(1);
        JalanNapas4.setSelectedIndex(1);
        JalanNapas5.setSelectedIndex(1);
        JalanNapas6.setSelectedIndex(1);
        JalanNapas7.setSelectedIndex(1);
        JalanNapas8.setSelectedIndex(1);
        JalanNapas9.setSelectedIndex(1);
        LainLain1.setSelectedIndex(1);
        LainLain2.setSelectedIndex(1);
        LainLain3.setSelectedIndex(1);
        LainLain4.setSelectedIndex(1);
        LainLain5.setSelectedIndex(1);
        LainLain6.setSelectedIndex(1);
        LainLain7.setSelectedIndex(1);
        LainLain8.setSelectedIndex(1);
        ObatObat1.setSelectedIndex(1);
        ObatObat2.setSelectedIndex(1);
        ObatObat3.setSelectedIndex(1);
        ObatObat4.setSelectedIndex(1);
        ObatObat5.setSelectedIndex(1);
        ObatObat6.setSelectedIndex(1);
        Tanggal.setDate(new Date());
        KeteranganLainnya.setText("");
        Tindakan.setText("");
        TeknikAnestesi.setText("");
        Tindakan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            TeknikAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Listrik1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Listrik2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Listrik3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Listrik4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            GasMedis1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            GasMedis2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            GasMedis3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            GasMedis4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            GasMedis5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            GasMedis6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            MesinAnestesi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            MesinAnestesi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            MesinAnestesi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            MesinAnestesi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            MesinAnestesi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            JalanNapas1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            JalanNapas2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            JalanNapas3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            JalanNapas4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            JalanNapas5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            JalanNapas6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            JalanNapas7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            JalanNapas8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            JalanNapas9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            LainLain1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            LainLain2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            LainLain3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            LainLain4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            LainLain5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            LainLain6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            LainLain7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            LainLain8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            ObatObat1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            ObatObat2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            ObatObat3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            ObatObat4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            ObatObat5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            ObatObat6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            KeteranganLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-182));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getchecklist_kesiapan_anestesi());
        BtnHapus.setEnabled(akses.getchecklist_kesiapan_anestesi());
        BtnEdit.setEnabled(akses.getchecklist_kesiapan_anestesi());
        BtnPrint.setEnabled(akses.getchecklist_kesiapan_anestesi()); 
        if(akses.getjml2()>=1){
            BtnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(akses.getkode()));
            if(NamaPetugas.getText().equals("")){
                KodePetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("checklist_kesiapan_anestesi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,nip=?,kd_dokter=?,tindakan=?,teknik_anestesi=?,listrik1=?,listrik2=?,listrik3=?,listrik4=?,"+
                "gasmedis1=?,gasmedis2=?,gasmedis3=?,gasmedis4=?,gasmedis5=?,gasmedis6=?,mesinanes1=?,mesinanes2=?,mesinanes3=?,mesinanes4=?,mesinanes5=?,jalannapas1=?,jalannapas2=?,jalannapas3=?,jalannapas4=?,"+
                "jalannapas5=?,jalannapas6=?,jalannapas7=?,jalannapas8=?,jalannapas9=?,lainlain1=?,lainlain2=?,lainlain3=?,lainlain4=?,lainlain5=?,lainlain6=?,lainlain7=?,lainlain8=?,obatobat1=?,obatobat2=?,"+
                "obatobat3=?,obatobat4=?,obatobat5=?,obatobat6=?,keterangan_lainnya=?",47,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KodePetugas.getText(),KodeDokter.getText(),Tindakan.getText(), 
                TeknikAnestesi.getText(),Listrik1.getSelectedItem().toString(),Listrik2.getSelectedItem().toString(),Listrik3.getSelectedItem().toString(),Listrik4.getSelectedItem().toString(),
                GasMedis1.getSelectedItem().toString(),GasMedis2.getSelectedItem().toString(),GasMedis3.getSelectedItem().toString(),GasMedis4.getSelectedItem().toString(),GasMedis5.getSelectedItem().toString(),
                GasMedis6.getSelectedItem().toString(),MesinAnestesi1.getSelectedItem().toString(),MesinAnestesi2.getSelectedItem().toString(),MesinAnestesi3.getSelectedItem().toString(),
                MesinAnestesi4.getSelectedItem().toString(),MesinAnestesi5.getSelectedItem().toString(),JalanNapas1.getSelectedItem().toString(),JalanNapas2.getSelectedItem().toString(),
                JalanNapas3.getSelectedItem().toString(),JalanNapas4.getSelectedItem().toString(),JalanNapas5.getSelectedItem().toString(),JalanNapas6.getSelectedItem().toString(),
                JalanNapas7.getSelectedItem().toString(),JalanNapas8.getSelectedItem().toString(),JalanNapas9.getSelectedItem().toString(),LainLain1.getSelectedItem().toString(),
                LainLain2.getSelectedItem().toString(),LainLain3.getSelectedItem().toString(),LainLain4.getSelectedItem().toString(),LainLain5.getSelectedItem().toString(),LainLain6.getSelectedItem().toString(),
                LainLain7.getSelectedItem().toString(),LainLain8.getSelectedItem().toString(),ObatObat1.getSelectedItem().toString(),ObatObat2.getSelectedItem().toString(),ObatObat3.getSelectedItem().toString(),
                ObatObat4.getSelectedItem().toString(),ObatObat5.getSelectedItem().toString(),ObatObat6.getSelectedItem().toString(),KeteranganLainnya.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KodeDokter.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaDokter.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(TeknikAnestesi.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Listrik1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Listrik2.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Listrik3.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Listrik4.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(GasMedis1.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(GasMedis2.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(GasMedis3.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(GasMedis4.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(GasMedis5.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(GasMedis6.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(MesinAnestesi1.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(MesinAnestesi2.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(MesinAnestesi3.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(MesinAnestesi4.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(MesinAnestesi5.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(JalanNapas1.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(JalanNapas2.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(JalanNapas3.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(JalanNapas4.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(JalanNapas5.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(JalanNapas6.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(JalanNapas7.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(JalanNapas8.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(JalanNapas9.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(LainLain1.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(LainLain2.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(LainLain3.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(LainLain4.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(LainLain5.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(LainLain6.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(LainLain7.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(LainLain8.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(ObatObat1.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(ObatObat2.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(ObatObat3.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
            tbObat.setValueAt(ObatObat4.getSelectedItem().toString(),tbObat.getSelectedRow(),47);
            tbObat.setValueAt(ObatObat5.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
            tbObat.setValueAt(ObatObat6.getSelectedItem().toString(),tbObat.getSelectedRow(),49);
            tbObat.setValueAt(KeteranganLainnya.getText(),tbObat.getSelectedRow(),50);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kesiapan_anestesi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("checklist_kesiapan_anestesi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",45,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KodePetugas.getText(),KodeDokter.getText(),Tindakan.getText(), 
            TeknikAnestesi.getText(),Listrik1.getSelectedItem().toString(),Listrik2.getSelectedItem().toString(),Listrik3.getSelectedItem().toString(),Listrik4.getSelectedItem().toString(),
            GasMedis1.getSelectedItem().toString(),GasMedis2.getSelectedItem().toString(),GasMedis3.getSelectedItem().toString(),GasMedis4.getSelectedItem().toString(),GasMedis5.getSelectedItem().toString(),
            GasMedis6.getSelectedItem().toString(),MesinAnestesi1.getSelectedItem().toString(),MesinAnestesi2.getSelectedItem().toString(),MesinAnestesi3.getSelectedItem().toString(),
            MesinAnestesi4.getSelectedItem().toString(),MesinAnestesi5.getSelectedItem().toString(),JalanNapas1.getSelectedItem().toString(),JalanNapas2.getSelectedItem().toString(),
            JalanNapas3.getSelectedItem().toString(),JalanNapas4.getSelectedItem().toString(),JalanNapas5.getSelectedItem().toString(),JalanNapas6.getSelectedItem().toString(),
            JalanNapas7.getSelectedItem().toString(),JalanNapas8.getSelectedItem().toString(),JalanNapas9.getSelectedItem().toString(),LainLain1.getSelectedItem().toString(),
            LainLain2.getSelectedItem().toString(),LainLain3.getSelectedItem().toString(),LainLain4.getSelectedItem().toString(),LainLain5.getSelectedItem().toString(),LainLain6.getSelectedItem().toString(),
            LainLain7.getSelectedItem().toString(),LainLain8.getSelectedItem().toString(),ObatObat1.getSelectedItem().toString(),ObatObat2.getSelectedItem().toString(),ObatObat3.getSelectedItem().toString(),
            ObatObat4.getSelectedItem().toString(),ObatObat5.getSelectedItem().toString(),ObatObat6.getSelectedItem().toString(),KeteranganLainnya.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                KodePetugas.getText(),NamaPetugas.getText(),KodeDokter.getText(),NamaDokter.getText(),Tindakan.getText(),TeknikAnestesi.getText(),Listrik1.getSelectedItem().toString(),Listrik2.getSelectedItem().toString(),
                Listrik3.getSelectedItem().toString(),Listrik4.getSelectedItem().toString(),GasMedis1.getSelectedItem().toString(),GasMedis2.getSelectedItem().toString(),GasMedis3.getSelectedItem().toString(),
                GasMedis4.getSelectedItem().toString(),GasMedis5.getSelectedItem().toString(),GasMedis6.getSelectedItem().toString(),MesinAnestesi1.getSelectedItem().toString(),MesinAnestesi2.getSelectedItem().toString(),
                MesinAnestesi3.getSelectedItem().toString(),MesinAnestesi4.getSelectedItem().toString(),MesinAnestesi5.getSelectedItem().toString(),JalanNapas1.getSelectedItem().toString(),JalanNapas2.getSelectedItem().toString(),
                JalanNapas3.getSelectedItem().toString(),JalanNapas4.getSelectedItem().toString(),JalanNapas5.getSelectedItem().toString(),JalanNapas6.getSelectedItem().toString(),JalanNapas7.getSelectedItem().toString(),
                JalanNapas8.getSelectedItem().toString(),JalanNapas9.getSelectedItem().toString(),LainLain1.getSelectedItem().toString(),LainLain2.getSelectedItem().toString(),LainLain3.getSelectedItem().toString(),
                LainLain4.getSelectedItem().toString(),LainLain5.getSelectedItem().toString(),LainLain6.getSelectedItem().toString(),LainLain7.getSelectedItem().toString(),LainLain8.getSelectedItem().toString(),
                ObatObat1.getSelectedItem().toString(),ObatObat2.getSelectedItem().toString(),ObatObat3.getSelectedItem().toString(),ObatObat4.getSelectedItem().toString(),ObatObat5.getSelectedItem().toString(),
                ObatObat6.getSelectedItem().toString(),KeteranganLainnya.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
}

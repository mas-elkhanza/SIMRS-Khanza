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
public final class RMCatatanAnastesiSedasi extends javax.swing.JDialog {
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
    public RMCatatanAnastesiSedasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Dokter","Tanggal","Tgl.Operasi","Diagnosa","Rencana Tindakan", 
            "TB","BB","TD","IO2","Nadi","Pernapasan","Suhu","Asesmen Fisik Cardiovasculer","Asesmen Fisik Paru","Asesmen Fisik Abdomen", 
            "Asesmen Fisik Extrimitas","Asesmen Fisik Endokrin","Asesmen Fisik Ginjal","Asesmen Fisik Obat-obatan","Asesmen Fisik Laborat", 
            "Asesmen Fisik Penunjang","Riwayat Penyakit Alergi Obat","Riwayat Penyakit Alergi Lainnya","Riwayat Penyakit Terapi", 
            "Kebiasaan Merokok","Jml.Rokok","Kebiasaan Alkohol","Jml.Alko","Penggunaan Obat","Obat Dikonsumsi","Riwayat Medis Cardiovasculer", 
            "Riwayat Medis Respiratory","Riwayat Medis Endocrine","Riwayat Medis Lainnya","Angka ASA","Mulai Puasa","Rencana Anestesi", 
            "Rencana Perawatan","Catatan Khusus"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 45; i++) {
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
                column.setPreferredWidth(115);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(170);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(50);
            }else if(i==14){
                column.setPreferredWidth(35);
            }else if(i==15){
                column.setPreferredWidth(35);
            }else if(i==16){
                column.setPreferredWidth(55);
            }else if(i==17){
                column.setPreferredWidth(35);
            }else if(i==18){
                column.setPreferredWidth(170);
            }else if(i==19){
                column.setPreferredWidth(170);
            }else if(i==20){
                column.setPreferredWidth(170);
            }else if(i==21){
                column.setPreferredWidth(170);
            }else if(i==22){
                column.setPreferredWidth(170);
            }else if(i==23){
                column.setPreferredWidth(170);
            }else if(i==24){
                column.setPreferredWidth(170);
            }else if(i==25){
                column.setPreferredWidth(170);
            }else if(i==26){
                column.setPreferredWidth(170);
            }else if(i==27){
                column.setPreferredWidth(170);
            }else if(i==28){
                column.setPreferredWidth(170);
            }else if(i==29){
                column.setPreferredWidth(170);
            }else if(i==30){
                column.setPreferredWidth(100);
            }else if(i==31){
                column.setPreferredWidth(55);
            }else if(i==32){
                column.setPreferredWidth(97);
            }else if(i==33){
                column.setPreferredWidth(49);
            }else if(i==34){
                column.setPreferredWidth(94);
            }else if(i==35){
                column.setPreferredWidth(130);
            }else if(i==36){
                column.setPreferredWidth(170);
            }else if(i==37){
                column.setPreferredWidth(170);
            }else if(i==38){
                column.setPreferredWidth(170);
            }else if(i==39){
                column.setPreferredWidth(170);
            }else if(i==40){
                column.setPreferredWidth(60);
            }else if(i==41){
                column.setPreferredWidth(115);
            }else if(i==42){
                column.setPreferredWidth(95);
            }else if(i==43){
                column.setPreferredWidth(140);
            }else if(i==44){
                column.setPreferredWidth(170);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Diagnosa.setDocument(new batasInput((byte)100).getKata(Diagnosa));
        RencanaTindakan.setDocument(new batasInput((byte)100).getKata(RencanaTindakan));
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
        jLabel12 = new widget.Label();
        Diagnosa = new widget.TextBox();
        jLabel13 = new widget.Label();
        RencanaTindakan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel14 = new widget.Label();
        Diagnosa1 = new widget.TextBox();
        label15 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        label16 = new widget.Label();
        KdDokter2 = new widget.TextBox();
        NmDokter2 = new widget.TextBox();
        BtnDokter2 = new widget.Button();
        label17 = new widget.Label();
        KdDokter3 = new widget.TextBox();
        NmDokter3 = new widget.TextBox();
        BtnDokter3 = new widget.Button();
        jLabel109 = new widget.Label();
        jLabel15 = new widget.Label();
        TB = new widget.TextBox();
        jLabel16 = new widget.Label();
        BB = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel29 = new widget.Label();
        IO2 = new widget.TextBox();
        PenyakitKebiasaanMerokok = new widget.ComboBox();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        BB1 = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        TB1 = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel30 = new widget.Label();
        BB2 = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        BB3 = new widget.TextBox();
        jLabel33 = new widget.Label();
        PenyakitKebiasaanMerokok1 = new widget.ComboBox();
        jLabel34 = new widget.Label();
        IO3 = new widget.TextBox();
        jLabel35 = new widget.Label();
        TB2 = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        BB4 = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        BB5 = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        Suhu1 = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        IO4 = new widget.TextBox();
        jLabel45 = new widget.Label();
        TB3 = new widget.TextBox();
        jLabel46 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel47 = new widget.Label();
        PenyakitKebiasaanMerokok2 = new widget.ComboBox();
        jLabel48 = new widget.Label();
        PenyakitKebiasaanMerokok3 = new widget.ComboBox();
        jLabel49 = new widget.Label();
        PenyakitKebiasaanMerokok4 = new widget.ComboBox();
        jLabel50 = new widget.Label();
        PenyakitKebiasaanMerokok5 = new widget.ComboBox();
        jLabel51 = new widget.Label();
        PenyakitKebiasaanMerokok6 = new widget.ComboBox();
        jLabel52 = new widget.Label();
        PenyakitKebiasaanMerokok7 = new widget.ComboBox();
        jLabel53 = new widget.Label();
        PenyakitKebiasaanMerokok8 = new widget.ComboBox();
        jLabel54 = new widget.Label();
        PenyakitKebiasaanMerokok9 = new widget.ComboBox();
        jLabel55 = new widget.Label();
        BB6 = new widget.TextBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel111 = new widget.Label();
        jLabel56 = new widget.Label();
        PenyakitKebiasaanMerokok10 = new widget.ComboBox();
        jLabel57 = new widget.Label();
        PenyakitKebiasaanMerokok11 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        PenyakitKebiasaanMerokok12 = new widget.ComboBox();
        jLabel59 = new widget.Label();
        PenyakitKebiasaanMerokok13 = new widget.ComboBox();
        jLabel60 = new widget.Label();
        PenyakitKebiasaanMerokok14 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        PenyakitKebiasaanMerokok15 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        PenyakitKebiasaanMerokok16 = new widget.ComboBox();
        PenyakitKebiasaanMerokok17 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        PenyakitKebiasaanMerokok18 = new widget.ComboBox();
        Suhu2 = new widget.TextBox();
        Suhu3 = new widget.TextBox();
        PenyakitKebiasaanMerokok19 = new widget.ComboBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        PenyakitKebiasaanMerokok20 = new widget.ComboBox();
        jLabel67 = new widget.Label();
        PenyakitKebiasaanMerokok21 = new widget.ComboBox();
        Suhu4 = new widget.TextBox();
        jLabel68 = new widget.Label();
        Suhu5 = new widget.TextBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel112 = new widget.Label();
        jLabel129 = new widget.Label();
        AngkaASA = new widget.ComboBox();
        jLabel69 = new widget.Label();
        BB7 = new widget.TextBox();
        jLabel70 = new widget.Label();
        PenyakitKebiasaanMerokok22 = new widget.ComboBox();
        Suhu6 = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        jLabel130 = new widget.Label();
        AngkaASA1 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        PenyakitKebiasaanMerokok23 = new widget.ComboBox();
        Suhu7 = new widget.TextBox();
        jLabel72 = new widget.Label();
        PenyakitKebiasaanMerokok24 = new widget.ComboBox();
        jLabel73 = new widget.Label();
        PenyakitKebiasaanMerokok25 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        PenyakitKebiasaanMerokok26 = new widget.ComboBox();
        Suhu8 = new widget.TextBox();
        jLabel75 = new widget.Label();
        PenyakitKebiasaanMerokok27 = new widget.ComboBox();
        Suhu9 = new widget.TextBox();
        jLabel76 = new widget.Label();
        PenyakitKebiasaanMerokok28 = new widget.ComboBox();
        Suhu10 = new widget.TextBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Anestesi-Sedasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 843));
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

        label14.setText("DPJP Anestesi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 110, 90, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(94, 110, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(187, 110, 150, 23);

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
        BtnDokter.setBounds(340, 110, 28, 23);

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
        jSeparator1.setBounds(0, 100, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(160, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024 10:25:18" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(216, 40, 130, 23);

        jLabel12.setText("Diagnosa Pra-Bedah :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(344, 40, 120, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(468, 40, 256, 23);

        jLabel13.setText("Tindakan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 70, 23);

        RencanaTindakan.setHighlighter(null);
        RencanaTindakan.setName("RencanaTindakan"); // NOI18N
        RencanaTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaTindakanKeyPressed(evt);
            }
        });
        FormInput.add(RencanaTindakan);
        RencanaTindakan.setBounds(74, 70, 256, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 750, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 750, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 170, 750, 1);

        jLabel14.setText("Diagnosa Paska-Bedah :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(334, 70, 130, 23);

        Diagnosa1.setHighlighter(null);
        Diagnosa1.setName("Diagnosa1"); // NOI18N
        Diagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa1KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa1);
        Diagnosa1.setBounds(468, 70, 256, 23);

        label15.setText("DPJP Bedah :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(370, 110, 76, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setName("KdDokter1"); // NOI18N
        KdDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter1);
        KdDokter1.setBounds(450, 110, 90, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setName("NmDokter1"); // NOI18N
        NmDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter1);
        NmDokter1.setBounds(543, 110, 150, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(696, 110, 28, 23);

        label16.setText("Pr. Anestesi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 140, 90, 23);

        KdDokter2.setEditable(false);
        KdDokter2.setName("KdDokter2"); // NOI18N
        KdDokter2.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter2);
        KdDokter2.setBounds(94, 140, 90, 23);

        NmDokter2.setEditable(false);
        NmDokter2.setName("NmDokter2"); // NOI18N
        NmDokter2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter2);
        NmDokter2.setBounds(187, 140, 150, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnDokter2.setBounds(340, 140, 28, 23);

        label17.setText("Pr. Bedah :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(370, 140, 76, 23);

        KdDokter3.setEditable(false);
        KdDokter3.setName("KdDokter3"); // NOI18N
        KdDokter3.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter3);
        KdDokter3.setBounds(450, 140, 90, 23);

        NmDokter3.setEditable(false);
        NmDokter3.setName("NmDokter3"); // NOI18N
        NmDokter3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter3);
        NmDokter3.setBounds(543, 140, 150, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        BtnDokter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter3KeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(696, 140, 28, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("I. Penilaian Pra Induksi :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 170, 130, 23);

        jLabel15.setText("Kesadaran :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(173, 190, 80, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(94, 190, 55, 23);

        jLabel16.setText("Nadi :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(600, 190, 40, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(644, 190, 55, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("x/m");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(702, 190, 30, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(414, 190, 40, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(458, 190, 76, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(537, 190, 50, 23);

        jLabel29.setText("Saturasi O2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(311, 220, 90, 23);

        IO2.setFocusTraversalPolicyProvider(true);
        IO2.setName("IO2"); // NOI18N
        IO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IO2KeyPressed(evt);
            }
        });
        FormInput.add(IO2);
        IO2.setBounds(405, 220, 55, 23);

        PenyakitKebiasaanMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma", "Alert", "Confusion", "Voice", "Pain", "Unresponsive", "Apatis", "Delirium" }));
        PenyakitKebiasaanMerokok.setName("PenyakitKebiasaanMerokok"); // NOI18N
        PenyakitKebiasaanMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokokKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok);
        PenyakitKebiasaanMerokok.setBounds(257, 190, 130, 23);

        jLabel18.setText("Jam :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 190, 90, 23);

        jLabel20.setText("RR :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 220, 90, 23);

        BB1.setFocusTraversalPolicyProvider(true);
        BB1.setName("BB1"); // NOI18N
        BB1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB1KeyPressed(evt);
            }
        });
        FormInput.add(BB1);
        BB1.setBounds(94, 220, 55, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("x/m");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(152, 220, 30, 23);

        jLabel25.setText("Suhu :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(170, 220, 60, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(234, 220, 55, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("°C");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(292, 220, 30, 23);

        jLabel27.setText("TB :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(470, 220, 40, 23);

        TB1.setFocusTraversalPolicyProvider(true);
        TB1.setName("TB1"); // NOI18N
        TB1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TB1KeyPressed(evt);
            }
        });
        FormInput.add(TB1);
        TB1.setBounds(514, 220, 55, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Cm");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(572, 220, 30, 23);

        jLabel30.setText("BB :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(600, 220, 40, 23);

        BB2.setFocusTraversalPolicyProvider(true);
        BB2.setName("BB2"); // NOI18N
        BB2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB2KeyPressed(evt);
            }
        });
        FormInput.add(BB2);
        BB2.setBounds(644, 220, 55, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Kg");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(702, 220, 30, 23);

        jLabel32.setText("GD :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 250, 90, 23);

        BB3.setFocusTraversalPolicyProvider(true);
        BB3.setName("BB3"); // NOI18N
        BB3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB3KeyPressed(evt);
            }
        });
        FormInput.add(BB3);
        BB3.setBounds(94, 250, 55, 23);

        jLabel33.setText("Rhesus :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(165, 250, 55, 23);

        PenyakitKebiasaanMerokok1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        PenyakitKebiasaanMerokok1.setName("PenyakitKebiasaanMerokok1"); // NOI18N
        PenyakitKebiasaanMerokok1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok1KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok1);
        PenyakitKebiasaanMerokok1.setBounds(224, 250, 60, 23);

        jLabel34.setText("HB :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(311, 250, 40, 23);

        IO3.setFocusTraversalPolicyProvider(true);
        IO3.setName("IO3"); // NOI18N
        IO3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IO3KeyPressed(evt);
            }
        });
        FormInput.add(IO3);
        IO3.setBounds(355, 250, 55, 23);

        jLabel35.setText("HT :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(470, 250, 40, 23);

        TB2.setFocusTraversalPolicyProvider(true);
        TB2.setName("TB2"); // NOI18N
        TB2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TB2KeyPressed(evt);
            }
        });
        FormInput.add(TB2);
        TB2.setBounds(514, 250, 55, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("gr/dl");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(413, 250, 30, 23);

        jLabel37.setText("Leko :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(600, 250, 40, 23);

        BB4.setFocusTraversalPolicyProvider(true);
        BB4.setName("BB4"); // NOI18N
        BB4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB4KeyPressed(evt);
            }
        });
        FormInput.add(BB4);
        BB4.setBounds(644, 250, 55, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("ul");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(702, 250, 30, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("%");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(572, 250, 30, 23);

        jLabel40.setText("Trombo :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 280, 90, 23);

        BB5.setFocusTraversalPolicyProvider(true);
        BB5.setName("BB5"); // NOI18N
        BB5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB5KeyPressed(evt);
            }
        });
        FormInput.add(BB5);
        BB5.setBounds(94, 280, 55, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("ul");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(152, 280, 30, 23);

        jLabel42.setText("BT-CT :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(165, 280, 55, 23);

        Suhu1.setFocusTraversalPolicyProvider(true);
        Suhu1.setName("Suhu1"); // NOI18N
        Suhu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu1KeyPressed(evt);
            }
        });
        FormInput.add(Suhu1);
        Suhu1.setBounds(224, 280, 55, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("mnt");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(282, 280, 30, 23);

        jLabel44.setText("GDS :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(311, 280, 40, 23);

        IO4.setFocusTraversalPolicyProvider(true);
        IO4.setName("IO4"); // NOI18N
        IO4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IO4KeyPressed(evt);
            }
        });
        FormInput.add(IO4);
        IO4.setBounds(355, 280, 55, 23);

        jLabel45.setText("Lain-lain :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(460, 280, 50, 23);

        TB3.setFocusTraversalPolicyProvider(true);
        TB3.setName("TB3"); // NOI18N
        TB3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TB3KeyPressed(evt);
            }
        });
        FormInput.add(TB3);
        TB3.setBounds(514, 280, 210, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("MG/dl");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(413, 280, 30, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 310, 750, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 310, 750, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("II. Teknik & Alat Khusus");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 310, 130, 23);

        jLabel47.setText("TCI :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 330, 90, 23);

        PenyakitKebiasaanMerokok2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok2.setName("PenyakitKebiasaanMerokok2"); // NOI18N
        PenyakitKebiasaanMerokok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok2KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok2);
        PenyakitKebiasaanMerokok2.setBounds(94, 330, 80, 23);

        jLabel48.setText("Glidescope :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(185, 330, 80, 23);

        PenyakitKebiasaanMerokok3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok3.setName("PenyakitKebiasaanMerokok3"); // NOI18N
        PenyakitKebiasaanMerokok3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok3KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok3);
        PenyakitKebiasaanMerokok3.setBounds(269, 330, 80, 23);

        jLabel49.setText("Stimulator Saraf :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(366, 330, 100, 23);

        PenyakitKebiasaanMerokok4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok4.setName("PenyakitKebiasaanMerokok4"); // NOI18N
        PenyakitKebiasaanMerokok4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok4KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok4);
        PenyakitKebiasaanMerokok4.setBounds(470, 330, 80, 23);

        jLabel50.setText("CPB :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(590, 330, 50, 23);

        PenyakitKebiasaanMerokok5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok5.setName("PenyakitKebiasaanMerokok5"); // NOI18N
        PenyakitKebiasaanMerokok5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok5KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok5);
        PenyakitKebiasaanMerokok5.setBounds(644, 330, 80, 23);

        jLabel51.setText("USG :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 360, 90, 23);

        PenyakitKebiasaanMerokok6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok6.setName("PenyakitKebiasaanMerokok6"); // NOI18N
        PenyakitKebiasaanMerokok6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok6KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok6);
        PenyakitKebiasaanMerokok6.setBounds(94, 360, 80, 23);

        jLabel52.setText("Ventilator :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(185, 360, 80, 23);

        PenyakitKebiasaanMerokok7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok7.setName("PenyakitKebiasaanMerokok7"); // NOI18N
        PenyakitKebiasaanMerokok7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok7KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok7);
        PenyakitKebiasaanMerokok7.setBounds(269, 360, 80, 23);

        jLabel53.setText("Broncoscopy :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(366, 360, 100, 23);

        PenyakitKebiasaanMerokok8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok8.setName("PenyakitKebiasaanMerokok8"); // NOI18N
        PenyakitKebiasaanMerokok8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok8KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok8);
        PenyakitKebiasaanMerokok8.setBounds(470, 360, 80, 23);

        jLabel54.setText("Hiopotensi :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(570, 360, 70, 23);

        PenyakitKebiasaanMerokok9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok9.setName("PenyakitKebiasaanMerokok9"); // NOI18N
        PenyakitKebiasaanMerokok9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok9KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok9);
        PenyakitKebiasaanMerokok9.setBounds(644, 360, 80, 23);

        jLabel55.setText("Lainnya :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 390, 90, 23);

        BB6.setFocusTraversalPolicyProvider(true);
        BB6.setName("BB6"); // NOI18N
        BB6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB6KeyPressed(evt);
            }
        });
        FormInput.add(BB6);
        BB6.setBounds(94, 390, 630, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 420, 750, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 420, 750, 1);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("III. Monitoring");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 420, 130, 23);

        jLabel56.setText("EtCO2 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 440, 90, 23);

        PenyakitKebiasaanMerokok10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok10.setName("PenyakitKebiasaanMerokok10"); // NOI18N
        PenyakitKebiasaanMerokok10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok10KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok10);
        PenyakitKebiasaanMerokok10.setBounds(94, 440, 80, 23);

        jLabel57.setText("SpO2 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 470, 90, 23);

        PenyakitKebiasaanMerokok11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok11.setName("PenyakitKebiasaanMerokok11"); // NOI18N
        PenyakitKebiasaanMerokok11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok11KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok11);
        PenyakitKebiasaanMerokok11.setBounds(94, 470, 80, 23);

        jLabel58.setText("Stetoskop :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(195, 440, 80, 23);

        PenyakitKebiasaanMerokok12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok12.setName("PenyakitKebiasaanMerokok12"); // NOI18N
        PenyakitKebiasaanMerokok12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok12KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok12);
        PenyakitKebiasaanMerokok12.setBounds(279, 440, 80, 23);

        jLabel59.setText("NIBP :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(195, 470, 80, 23);

        PenyakitKebiasaanMerokok13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok13.setName("PenyakitKebiasaanMerokok13"); // NOI18N
        PenyakitKebiasaanMerokok13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok13KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok13);
        PenyakitKebiasaanMerokok13.setBounds(279, 470, 80, 23);

        jLabel60.setText("Cath A Pulmo :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(383, 440, 100, 23);

        PenyakitKebiasaanMerokok14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok14.setName("PenyakitKebiasaanMerokok14"); // NOI18N
        PenyakitKebiasaanMerokok14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok14KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok14);
        PenyakitKebiasaanMerokok14.setBounds(487, 440, 80, 23);

        jLabel61.setText("Kateter Urine :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(383, 470, 100, 23);

        PenyakitKebiasaanMerokok15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok15.setName("PenyakitKebiasaanMerokok15"); // NOI18N
        PenyakitKebiasaanMerokok15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok15KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok15);
        PenyakitKebiasaanMerokok15.setBounds(487, 470, 80, 23);

        jLabel62.setText("NGT :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(590, 440, 50, 23);

        PenyakitKebiasaanMerokok16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok16.setName("PenyakitKebiasaanMerokok16"); // NOI18N
        PenyakitKebiasaanMerokok16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok16KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok16);
        PenyakitKebiasaanMerokok16.setBounds(644, 440, 80, 23);

        PenyakitKebiasaanMerokok17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok17.setName("PenyakitKebiasaanMerokok17"); // NOI18N
        PenyakitKebiasaanMerokok17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok17KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok17);
        PenyakitKebiasaanMerokok17.setBounds(644, 470, 80, 23);

        jLabel63.setText("BIS :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(590, 470, 50, 23);

        jLabel64.setText("CVP :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 500, 90, 23);

        PenyakitKebiasaanMerokok18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok18.setName("PenyakitKebiasaanMerokok18"); // NOI18N
        PenyakitKebiasaanMerokok18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok18KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok18);
        PenyakitKebiasaanMerokok18.setBounds(94, 500, 80, 23);

        Suhu2.setFocusTraversalPolicyProvider(true);
        Suhu2.setName("Suhu2"); // NOI18N
        Suhu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu2KeyPressed(evt);
            }
        });
        FormInput.add(Suhu2);
        Suhu2.setBounds(177, 500, 125, 23);

        Suhu3.setFocusTraversalPolicyProvider(true);
        Suhu3.setName("Suhu3"); // NOI18N
        Suhu3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu3KeyPressed(evt);
            }
        });
        FormInput.add(Suhu3);
        Suhu3.setBounds(461, 500, 125, 23);

        PenyakitKebiasaanMerokok19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok19.setName("PenyakitKebiasaanMerokok19"); // NOI18N
        PenyakitKebiasaanMerokok19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok19KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok19);
        PenyakitKebiasaanMerokok19.setBounds(378, 500, 80, 23);

        jLabel65.setText("Arteri Line :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(304, 500, 70, 23);

        jLabel66.setText("Temp. :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(590, 500, 50, 23);

        PenyakitKebiasaanMerokok20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok20.setName("PenyakitKebiasaanMerokok20"); // NOI18N
        PenyakitKebiasaanMerokok20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok20KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok20);
        PenyakitKebiasaanMerokok20.setBounds(644, 500, 80, 23);

        jLabel67.setText("EKG Lead :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 530, 90, 23);

        PenyakitKebiasaanMerokok21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok21.setName("PenyakitKebiasaanMerokok21"); // NOI18N
        PenyakitKebiasaanMerokok21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok21KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok21);
        PenyakitKebiasaanMerokok21.setBounds(94, 530, 80, 23);

        Suhu4.setFocusTraversalPolicyProvider(true);
        Suhu4.setName("Suhu4"); // NOI18N
        Suhu4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu4KeyPressed(evt);
            }
        });
        FormInput.add(Suhu4);
        Suhu4.setBounds(177, 530, 125, 23);

        jLabel68.setText("Lain-lain :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(304, 530, 70, 23);

        Suhu5.setFocusTraversalPolicyProvider(true);
        Suhu5.setName("Suhu5"); // NOI18N
        Suhu5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu5KeyPressed(evt);
            }
        });
        FormInput.add(Suhu5);
        Suhu5.setBounds(378, 530, 346, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 560, 750, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 560, 750, 1);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("IV. Status Fisik");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(10, 560, 130, 23);

        jLabel129.setText("Angka ASA :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(0, 580, 105, 23);

        AngkaASA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "E" }));
        AngkaASA.setName("AngkaASA"); // NOI18N
        AngkaASA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AngkaASAKeyPressed(evt);
            }
        });
        FormInput.add(AngkaASA);
        AngkaASA.setBounds(109, 580, 60, 23);

        jLabel69.setText("Penyulit Pra :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 610, 105, 23);

        BB7.setFocusTraversalPolicyProvider(true);
        BB7.setName("BB7"); // NOI18N
        BB7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BB7KeyPressed(evt);
            }
        });
        FormInput.add(BB7);
        BB7.setBounds(109, 610, 615, 23);

        jLabel70.setText("Alergi :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(195, 580, 70, 23);

        PenyakitKebiasaanMerokok22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok22.setName("PenyakitKebiasaanMerokok22"); // NOI18N
        PenyakitKebiasaanMerokok22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok22KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok22);
        PenyakitKebiasaanMerokok22.setBounds(269, 580, 80, 23);

        Suhu6.setFocusTraversalPolicyProvider(true);
        Suhu6.setName("Suhu6"); // NOI18N
        Suhu6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu6KeyPressed(evt);
            }
        });
        FormInput.add(Suhu6);
        Suhu6.setBounds(352, 580, 372, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 640, 750, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 640, 750, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("V. Perencanaan");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 640, 130, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("Lanjut Tindakan");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(44, 660, 125, 23);

        AngkaASA1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AngkaASA1.setName("AngkaASA1"); // NOI18N
        AngkaASA1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AngkaASA1KeyPressed(evt);
            }
        });
        FormInput.add(AngkaASA1);
        AngkaASA1.setBounds(133, 660, 80, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Sedasi");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(80, 690, 70, 23);

        PenyakitKebiasaanMerokok23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sedang", "Dalam", "Tidak", "Lain-lain" }));
        PenyakitKebiasaanMerokok23.setName("PenyakitKebiasaanMerokok23"); // NOI18N
        PenyakitKebiasaanMerokok23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok23KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok23);
        PenyakitKebiasaanMerokok23.setBounds(123, 690, 95, 23);

        Suhu7.setFocusTraversalPolicyProvider(true);
        Suhu7.setName("Suhu7"); // NOI18N
        Suhu7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu7KeyPressed(evt);
            }
        });
        FormInput.add(Suhu7);
        Suhu7.setBounds(220, 690, 200, 23);

        jLabel72.setText("Spinal :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(590, 690, 50, 23);

        PenyakitKebiasaanMerokok24.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok24.setName("PenyakitKebiasaanMerokok24"); // NOI18N
        PenyakitKebiasaanMerokok24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok24KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok24);
        PenyakitKebiasaanMerokok24.setBounds(644, 690, 80, 23);

        jLabel73.setText("Epidural :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(450, 690, 50, 23);

        PenyakitKebiasaanMerokok25.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok25.setName("PenyakitKebiasaanMerokok25"); // NOI18N
        PenyakitKebiasaanMerokok25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok25KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok25);
        PenyakitKebiasaanMerokok25.setBounds(500, 690, 80, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Anastesi Umum");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(80, 720, 100, 23);

        PenyakitKebiasaanMerokok26.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok26.setName("PenyakitKebiasaanMerokok26"); // NOI18N
        PenyakitKebiasaanMerokok26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok26KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok26);
        PenyakitKebiasaanMerokok26.setBounds(168, 720, 80, 23);

        Suhu8.setFocusTraversalPolicyProvider(true);
        Suhu8.setName("Suhu8"); // NOI18N
        Suhu8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu8KeyPressed(evt);
            }
        });
        FormInput.add(Suhu8);
        Suhu8.setBounds(250, 720, 150, 23);

        jLabel75.setText("Blok Perifer :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(390, 720, 100, 23);

        PenyakitKebiasaanMerokok27.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok27.setName("PenyakitKebiasaanMerokok27"); // NOI18N
        PenyakitKebiasaanMerokok27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok27KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok27);
        PenyakitKebiasaanMerokok27.setBounds(490, 720, 80, 23);

        Suhu9.setFocusTraversalPolicyProvider(true);
        Suhu9.setName("Suhu9"); // NOI18N
        Suhu9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu9KeyPressed(evt);
            }
        });
        FormInput.add(Suhu9);
        Suhu9.setBounds(580, 720, 150, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Batal Tindakan");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 750, 100, 23);

        PenyakitKebiasaanMerokok28.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PenyakitKebiasaanMerokok28.setName("PenyakitKebiasaanMerokok28"); // NOI18N
        PenyakitKebiasaanMerokok28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitKebiasaanMerokok28KeyPressed(evt);
            }
        });
        FormInput.add(PenyakitKebiasaanMerokok28);
        PenyakitKebiasaanMerokok28.setBounds(128, 750, 80, 23);

        Suhu10.setFocusTraversalPolicyProvider(true);
        Suhu10.setName("Suhu10"); // NOI18N
        Suhu10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Suhu10KeyPressed(evt);
            }
        });
        FormInput.add(Suhu10);
        Suhu10.setBounds(211, 750, 513, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 660, 129, 23);

        jLabel78.setText(":");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 750, 124, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 690, 119, 23);

        jLabel80.setText(":");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 720, 164, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Catatan", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
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

        TabRawat.addTab("Data Catatan", internalFrame3);

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
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else{
            /*if(Sequel.menyimpantf("penilaian_pre_anestesi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat, Tanggal & Jam",40,new String[]{
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
                    tabMode.addRow(new String[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),
                        Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "+TglOperasi.getSelectedItem().toString().substring(11,19),Diagnosa.getText(),RencanaTindakan.getText(),TB.getText(),BB.getText(),TD.getText(),IO2.getText(),Nadi.getText(),
                        Pernapasan.getText(),Suhu.getText(),FisikCardio.getText(),FisikParu.getText(),FisikAbdomen.getText(),FisikExtrimitas.getText(),FisikEndokrin.getText(),FisikGinjal.getText(),FisikObat.getText(),FisikLaborat.getText(), 
                        FisikPenunjang.getText(),PenyakitAlergiObat.getText(),PenyakitAlergiLainnya.getText(),PenyakitTerapi.getText(),PenyakitKebiasaanMerokok.getSelectedItem().toString(),PenyakitKebiasaanJumlahRokok.getText(),
                        PenyakitKebiasaanAlkohol.getSelectedItem().toString(),PenyakitKebiasaanJumlahAlkohol.getText(),PenyakitKebiasaanObat.getSelectedItem().toString(),PenyakitKebiasaanObatDiminum.getText(),MedisCardio.getText(),
                        MedisRespiratory.getText(),MedisEndocrine.getText(),MedisLainnya.getText(),AngkaASA.getSelectedItem().toString(),Valid.SetTgl(TglPuasa.getSelectedItem()+"")+" "+TglPuasa.getSelectedItem().toString().substring(11,19), 
                        RencanaAnestesi.getSelectedItem().toString(),RencanaPerawatan.getText(),CatatanKhusus.getText()
                    });
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
            }*/
        }
    
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
        if(TNoRM.getText().trim().equals("")){
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
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+ 
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

                File f = new File("DataPenilaianPreAnestesi.html");            
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

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDokter,Diagnosa);
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

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt,TglAsuhan,RencanaTindakan);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void RencanaTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaTindakanKeyPressed
        //Valid.pindah(evt,Diagnosa,TB);
    }//GEN-LAST:event_RencanaTindakanKeyPressed

    private void Diagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa1KeyPressed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter2KeyPressed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter3KeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,RencanaTindakan,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TB,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,BB,IO2);
    }//GEN-LAST:event_TDKeyPressed

    private void IO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IO2KeyPressed
        //Valid.pindah(evt,TD,Nadi);
    }//GEN-LAST:event_IO2KeyPressed

    private void PenyakitKebiasaanMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokokKeyPressed
        //Valid.pindah(evt,PenyakitTerapi,PenyakitKebiasaanJumlahRokok);
    }//GEN-LAST:event_PenyakitKebiasaanMerokokKeyPressed

    private void BB1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB1KeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        //Valid.pindah(evt,Nadi,Pernapasan);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TB1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TB1KeyPressed
        Valid.pindah(evt,RencanaTindakan,BB);
    }//GEN-LAST:event_TB1KeyPressed

    private void BB2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB2KeyPressed
        Valid.pindah(evt,TB,TD);
    }//GEN-LAST:event_BB2KeyPressed

    private void BB3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB3KeyPressed

    private void PenyakitKebiasaanMerokok1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok1KeyPressed

    private void IO3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IO3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IO3KeyPressed

    private void TB2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TB2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TB2KeyPressed

    private void BB4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB4KeyPressed

    private void BB5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB5KeyPressed

    private void Suhu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu1KeyPressed

    private void IO4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IO4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IO4KeyPressed

    private void TB3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TB3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TB3KeyPressed

    private void PenyakitKebiasaanMerokok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok2KeyPressed

    private void PenyakitKebiasaanMerokok3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok3KeyPressed

    private void PenyakitKebiasaanMerokok4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok4KeyPressed

    private void PenyakitKebiasaanMerokok5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok5KeyPressed

    private void PenyakitKebiasaanMerokok6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok6KeyPressed

    private void PenyakitKebiasaanMerokok7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok7KeyPressed

    private void PenyakitKebiasaanMerokok8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok8KeyPressed

    private void PenyakitKebiasaanMerokok9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok9KeyPressed

    private void BB6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB6KeyPressed

    private void PenyakitKebiasaanMerokok10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok10KeyPressed

    private void PenyakitKebiasaanMerokok11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok11KeyPressed

    private void PenyakitKebiasaanMerokok12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok12KeyPressed

    private void PenyakitKebiasaanMerokok13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok13KeyPressed

    private void PenyakitKebiasaanMerokok14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok14KeyPressed

    private void PenyakitKebiasaanMerokok15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok15KeyPressed

    private void PenyakitKebiasaanMerokok16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok16KeyPressed

    private void PenyakitKebiasaanMerokok17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok17KeyPressed

    private void PenyakitKebiasaanMerokok18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok18KeyPressed

    private void Suhu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu2KeyPressed

    private void Suhu3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu3KeyPressed

    private void PenyakitKebiasaanMerokok19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok19KeyPressed

    private void PenyakitKebiasaanMerokok20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok20KeyPressed

    private void PenyakitKebiasaanMerokok21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok21KeyPressed

    private void Suhu4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu4KeyPressed

    private void Suhu5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu5KeyPressed

    private void AngkaASAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AngkaASAKeyPressed
        //Valid.pindah(evt,RencanaAnestesi,RencanaPerawatan);
    }//GEN-LAST:event_AngkaASAKeyPressed

    private void BB7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BB7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BB7KeyPressed

    private void PenyakitKebiasaanMerokok22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok22KeyPressed

    private void Suhu6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu6KeyPressed

    private void AngkaASA1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AngkaASA1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AngkaASA1KeyPressed

    private void PenyakitKebiasaanMerokok23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok23KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok23KeyPressed

    private void Suhu7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu7KeyPressed

    private void PenyakitKebiasaanMerokok24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok24KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok24KeyPressed

    private void PenyakitKebiasaanMerokok25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok25KeyPressed

    private void PenyakitKebiasaanMerokok26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok26KeyPressed

    private void Suhu8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu8KeyPressed

    private void PenyakitKebiasaanMerokok27KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok27KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok27KeyPressed

    private void Suhu9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu9KeyPressed

    private void PenyakitKebiasaanMerokok28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitKebiasaanMerokok28KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenyakitKebiasaanMerokok28KeyPressed

    private void Suhu10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Suhu10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Suhu10KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCatatanAnastesiSedasi dialog = new RMCatatanAnastesiSedasi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AngkaASA;
    private widget.ComboBox AngkaASA1;
    private widget.TextBox BB;
    private widget.TextBox BB1;
    private widget.TextBox BB2;
    private widget.TextBox BB3;
    private widget.TextBox BB4;
    private widget.TextBox BB5;
    private widget.TextBox BB6;
    private widget.TextBox BB7;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter3;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Diagnosa;
    private widget.TextBox Diagnosa1;
    private widget.PanelBiasa FormInput;
    private widget.TextBox IO2;
    private widget.TextBox IO3;
    private widget.TextBox IO4;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdDokter2;
    private widget.TextBox KdDokter3;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmDokter2;
    private widget.TextBox NmDokter3;
    private widget.ComboBox PenyakitKebiasaanMerokok;
    private widget.ComboBox PenyakitKebiasaanMerokok1;
    private widget.ComboBox PenyakitKebiasaanMerokok10;
    private widget.ComboBox PenyakitKebiasaanMerokok11;
    private widget.ComboBox PenyakitKebiasaanMerokok12;
    private widget.ComboBox PenyakitKebiasaanMerokok13;
    private widget.ComboBox PenyakitKebiasaanMerokok14;
    private widget.ComboBox PenyakitKebiasaanMerokok15;
    private widget.ComboBox PenyakitKebiasaanMerokok16;
    private widget.ComboBox PenyakitKebiasaanMerokok17;
    private widget.ComboBox PenyakitKebiasaanMerokok18;
    private widget.ComboBox PenyakitKebiasaanMerokok19;
    private widget.ComboBox PenyakitKebiasaanMerokok2;
    private widget.ComboBox PenyakitKebiasaanMerokok20;
    private widget.ComboBox PenyakitKebiasaanMerokok21;
    private widget.ComboBox PenyakitKebiasaanMerokok22;
    private widget.ComboBox PenyakitKebiasaanMerokok23;
    private widget.ComboBox PenyakitKebiasaanMerokok24;
    private widget.ComboBox PenyakitKebiasaanMerokok25;
    private widget.ComboBox PenyakitKebiasaanMerokok26;
    private widget.ComboBox PenyakitKebiasaanMerokok27;
    private widget.ComboBox PenyakitKebiasaanMerokok28;
    private widget.ComboBox PenyakitKebiasaanMerokok3;
    private widget.ComboBox PenyakitKebiasaanMerokok4;
    private widget.ComboBox PenyakitKebiasaanMerokok5;
    private widget.ComboBox PenyakitKebiasaanMerokok6;
    private widget.ComboBox PenyakitKebiasaanMerokok7;
    private widget.ComboBox PenyakitKebiasaanMerokok8;
    private widget.ComboBox PenyakitKebiasaanMerokok9;
    private widget.TextBox RencanaTindakan;
    private widget.ScrollPane Scroll;
    private widget.TextBox Suhu;
    private widget.TextBox Suhu1;
    private widget.TextBox Suhu10;
    private widget.TextBox Suhu2;
    private widget.TextBox Suhu3;
    private widget.TextBox Suhu4;
    private widget.TextBox Suhu5;
    private widget.TextBox Suhu6;
    private widget.TextBox Suhu7;
    private widget.TextBox Suhu8;
    private widget.TextBox Suhu9;
    private widget.TextBox TB;
    private widget.TextBox TB1;
    private widget.TextBox TB2;
    private widget.TextBox TB3;
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
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel12;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
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
        Diagnosa.setText("");
        RencanaTindakan.setText("");
        
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Diagnosa.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            RencanaTindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
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
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(Valid.SetTgl(TglOperasi.getSelectedItem()+"")+" "+TglOperasi.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(Diagnosa.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(RencanaTindakan.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(IO2.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(Pernapasan.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(FisikCardio.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(FisikParu.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(FisikAbdomen.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(FisikExtrimitas.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(FisikEndokrin.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(FisikGinjal.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(FisikObat.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(FisikLaborat.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(FisikPenunjang.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(PenyakitAlergiObat.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(PenyakitAlergiLainnya.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(PenyakitTerapi.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(PenyakitKebiasaanMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(PenyakitKebiasaanJumlahRokok.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(PenyakitKebiasaanAlkohol.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(PenyakitKebiasaanJumlahAlkohol.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(PenyakitKebiasaanObat.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(PenyakitKebiasaanObatDiminum.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(MedisCardio.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(MedisRespiratory.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(MedisEndocrine.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(MedisLainnya.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(AngkaASA.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(Valid.SetTgl(TglPuasa.getSelectedItem()+"")+" "+TglPuasa.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(RencanaAnestesi.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(RencanaPerawatan.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(CatatanKhusus.getText(),tbObat.getSelectedRow(),44);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }*/
    }
}

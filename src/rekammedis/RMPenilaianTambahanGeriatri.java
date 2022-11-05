/*
 * Kontribusi dari tim IT RSUD Prembun
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
public final class RMPenilaianTambahanGeriatri extends javax.swing.JDialog {
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
    public RMPenilaianTambahanGeriatri(java.awt.Frame parent, boolean modal) {
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
        jSeparator2 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel38 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        Hubungan = new widget.TextBox();
        jLabel39 = new widget.Label();
        Anamnesis1 = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Anamnesis2 = new widget.ComboBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        Anamnesis3 = new widget.ComboBox();
        Anamnesis4 = new widget.ComboBox();
        jLabel16 = new widget.Label();
        Anamnesis5 = new widget.ComboBox();
        jLabel17 = new widget.Label();
        Anamnesis6 = new widget.ComboBox();
        jLabel18 = new widget.Label();
        Anamnesis7 = new widget.ComboBox();
        jLabel20 = new widget.Label();
        Anamnesis8 = new widget.ComboBox();
        jLabel22 = new widget.Label();
        Anamnesis9 = new widget.ComboBox();
        jLabel23 = new widget.Label();
        Anamnesis10 = new widget.ComboBox();
        jLabel24 = new widget.Label();
        Anamnesis11 = new widget.ComboBox();
        jLabel25 = new widget.Label();
        Anamnesis12 = new widget.ComboBox();
        jLabel26 = new widget.Label();
        Anamnesis13 = new widget.ComboBox();
        jLabel41 = new widget.Label();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        Anamnesis14 = new widget.ComboBox();
        Anamnesis15 = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        Anamnesis16 = new widget.ComboBox();
        jLabel31 = new widget.Label();
        Anamnesis17 = new widget.ComboBox();
        jLabel32 = new widget.Label();
        jLabel42 = new widget.Label();
        Anamnesis18 = new widget.ComboBox();
        Hubungan1 = new widget.TextBox();
        Hubungan2 = new widget.TextBox();
        Anamnesis19 = new widget.ComboBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        Anamnesis20 = new widget.ComboBox();
        Hubungan3 = new widget.TextBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel96 = new widget.Label();
        jLabel45 = new widget.Label();
        Anamnesis21 = new widget.ComboBox();
        Anamnesis22 = new widget.ComboBox();
        jLabel46 = new widget.Label();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Tambahan Pasien Geriatri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(750, 853));
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

        label14.setText("Dokter/Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(146, 40, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(250, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(342, 40, 160, 23);

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
        BtnDokter.setBounds(504, 40, 28, 23);

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
        jSeparator1.setBounds(0, 70, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(538, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2022 16:06:04" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(594, 40, 130, 23);

        jLabel12.setText("Diagnosa Medis :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 120, 145, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(149, 120, 350, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 750, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT KESEHATAN");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 140, 23);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(135, 90, 10, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Kursi Roda", "Dipapah", "Tempat Tidur" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(149, 90, 115, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(267, 90, 232, 23);

        jLabel39.setText("Anamnesis :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(521, 90, 71, 23);

        Anamnesis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis1.setName("Anamnesis1"); // NOI18N
        Anamnesis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis1KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis1);
        Anamnesis1.setBounds(596, 90, 128, 23);

        jLabel40.setText("Asal Masuk :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(521, 120, 71, 23);

        Anamnesis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IGD", "Kamar Bersalin", "Klinik" }));
        Anamnesis2.setName("Anamnesis2"); // NOI18N
        Anamnesis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis2KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis2);
        Anamnesis2.setBounds(596, 120, 128, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Riwayat Immunokompromais :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(45, 150, 180, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("-  Infeksi Telinga Baru Sebanyak 8 (Delapan) Kali Atau Lebih Dalam Setahun");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(65, 170, 570, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("-  Infeksi Berat Pada Sinus Sebanyak 2 (Dua) Kali Atau Lebih Dalam Setahun");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(65, 200, 570, 23);

        Anamnesis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis3.setName("Anamnesis3"); // NOI18N
        Anamnesis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis3KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis3);
        Anamnesis3.setBounds(644, 170, 80, 23);

        Anamnesis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis4.setName("Anamnesis4"); // NOI18N
        Anamnesis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis4KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis4);
        Anamnesis4.setBounds(644, 200, 80, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("-  Penggunaan Antibiotik Tanpa Dampak Selama 2 (Dua) Bulan Atau Lebih");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(65, 230, 570, 23);

        Anamnesis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis5.setName("Anamnesis5"); // NOI18N
        Anamnesis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis5KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis5);
        Anamnesis5.setBounds(644, 230, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("-  Pneumonia Sebanyak 2 (Dua) Kali Atau Lebih Dalam Setahun");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(65, 260, 570, 23);

        Anamnesis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis6.setName("Anamnesis6"); // NOI18N
        Anamnesis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis6KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis6);
        Anamnesis6.setBounds(644, 260, 80, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("-  Adanya Abses Berulang Pada Kulit Atau Organ Lainnya");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(65, 290, 570, 23);

        Anamnesis7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis7.setName("Anamnesis7"); // NOI18N
        Anamnesis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis7KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis7);
        Anamnesis7.setBounds(644, 290, 80, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("-  Adanya Sariawan Yang Menetap Atau Luka Pada Kulit");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(65, 320, 570, 23);

        Anamnesis8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis8.setName("Anamnesis8"); // NOI18N
        Anamnesis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis8KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis8);
        Anamnesis8.setBounds(644, 320, 80, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("-  Memerlukan Antibiotika Intravena Untuk Infeksi");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(65, 350, 570, 23);

        Anamnesis9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis9.setName("Anamnesis9"); // NOI18N
        Anamnesis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis9KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis9);
        Anamnesis9.setBounds(644, 350, 80, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("-  Terdapat 2 (Dua) Atau Lebih Infeksi Dalam (Misalnya : Meningitis, Osteomielitis, Selulitis, Sepsis)");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(65, 380, 570, 23);

        Anamnesis10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis10.setName("Anamnesis10"); // NOI18N
        Anamnesis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis10KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis10);
        Anamnesis10.setBounds(644, 380, 80, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("-  Adanya Riwayat Keluarga Terhadap Immunodefisiensi Primer");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(65, 410, 570, 23);

        Anamnesis11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis11.setName("Anamnesis11"); // NOI18N
        Anamnesis11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis11KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis11);
        Anamnesis11.setBounds(644, 410, 80, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("-  Adanya Jenis Kanker (Misalnya : Sarkoma Kaposi Atau Limfoma Non-Hodgins)");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(65, 440, 570, 23);

        Anamnesis12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis12.setName("Anamnesis12"); // NOI18N
        Anamnesis12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis12KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis12);
        Anamnesis12.setBounds(644, 440, 80, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("-  Adanya Infeksi Oportunistik (Misalnya : Pneumonia \"Pneumovystis Carinii\" Atau Infeksi Jamur Berulang)");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(65, 470, 570, 23);

        Anamnesis13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis13.setName("Anamnesis13"); // NOI18N
        Anamnesis13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis13KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis13);
        Anamnesis13.setBounds(644, 470, 80, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Kondisi Saat Masuk");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(45, 90, 100, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 500, 750, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("II. PENILAIAN KUALITAS HIDUP – EQ5D, POLA AKTIFITAS DAN ISTIRAHAT");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 500, 430, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kualitas Hidup Dengan EQ5D :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(45, 520, 180, 23);

        jLabel28.setText("Mobilitas :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(45, 570, 165, 23);

        Anamnesis14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Masalah Untuk Berjalan", "Ada Masalah Untuk Berjalan", "Hanya Mampu Berbaring" }));
        Anamnesis14.setName("Anamnesis14"); // NOI18N
        Anamnesis14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis14KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis14);
        Anamnesis14.setBounds(214, 570, 510, 23);

        Anamnesis15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Kesulitan Dalam Perawatan Diri Sendiri", "Mengalami Kesulitan Untuk Membasuh Badan, Mandi Atau Berpakaian", "Tidak Mampu Membasuh Badan, Mandi/Berpakaian Sendiri" }));
        Anamnesis15.setName("Anamnesis15"); // NOI18N
        Anamnesis15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis15KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis15);
        Anamnesis15.setBounds(214, 540, 510, 23);

        jLabel29.setText("Perawatan Diri Sendiri :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(45, 540, 165, 23);

        jLabel30.setText("Aktivitas Sehari-hari :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(45, 600, 165, 23);

        Anamnesis16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tak Mempunyai Kesulitan Dalam Melaksanakan Kegiatan Sehari-hari", "Mempunyai Keterbatasan Dalam Melaksanakan Kegiatan Sehari-hari", "Tak Mampu Melaksanakan Kegiatan Sehari-hari" }));
        Anamnesis16.setName("Anamnesis16"); // NOI18N
        Anamnesis16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis16KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis16);
        Anamnesis16.setBounds(214, 600, 510, 23);

        jLabel31.setText("Nyeri/Tidak Nyaman :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(45, 630, 165, 23);

        Anamnesis17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Mempunyai Keluhan Rasa Nyeri Atau Rasa Tak Nyaman", "Suka Merasakan Agak Nyeri/Agak Kurang Nyaman", "Menderita Karena Keluhan Rasa Nyeri/Tidak Nyaman" }));
        Anamnesis17.setName("Anamnesis17"); // NOI18N
        Anamnesis17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis17KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis17);
        Anamnesis17.setBounds(214, 630, 510, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Pola Aktifitas Dan Istirahat :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(45, 660, 180, 23);

        jLabel42.setText("Istirahat/Tidur :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(45, 680, 165, 23);

        Anamnesis18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TAK", "Insomnia", "Lainnya" }));
        Anamnesis18.setName("Anamnesis18"); // NOI18N
        Anamnesis18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis18KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis18);
        Anamnesis18.setBounds(214, 680, 100, 23);

        Hubungan1.setName("Hubungan1"); // NOI18N
        Hubungan1.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan1KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan1);
        Hubungan1.setBounds(317, 680, 407, 23);

        Hubungan2.setName("Hubungan2"); // NOI18N
        Hubungan2.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan2KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan2);
        Hubungan2.setBounds(297, 710, 427, 23);

        Anamnesis19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis19.setName("Anamnesis19"); // NOI18N
        Anamnesis19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis19KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis19);
        Anamnesis19.setBounds(214, 710, 80, 23);

        jLabel43.setText("Penggunaan Obat Tidur :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(45, 710, 165, 23);

        jLabel44.setText("Olahraga :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(45, 740, 165, 23);

        Anamnesis20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Anamnesis20.setName("Anamnesis20"); // NOI18N
        Anamnesis20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis20KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis20);
        Anamnesis20.setBounds(214, 740, 80, 23);

        Hubungan3.setName("Hubungan3"); // NOI18N
        Hubungan3.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Hubungan3KeyPressed(evt);
            }
        });
        FormInput.add(Hubungan3);
        Hubungan3.setBounds(297, 740, 427, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 770, 750, 1);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("III. SKRINING NYERI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 770, 430, 23);

        jLabel45.setText("Skala Nyeri Dengan FPS-hv :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(45, 790, 165, 23);

        Anamnesis21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 - Tidak Nyeri", "2 - Dapat Ditoleransi(Aktifitas Tidak Tergangu)", "4 - Dapat Ditoleransi(Beberapa Aktifitas Sedikit Terganggu)", "5 - Tidak Dapat Ditoleransi(Masih Bisa Menggunakan Telp, Menonton TV/Membaca)", "6 - Tidak Dapat Ditoleransi(Tidak Bisa Menggunakan Telp, Menonton TV/Membaca)", "8 - Tidak Dapat Ditoleransi(Masih Bisa Berbicara Kerenya Nyeri)", "10 - Tidak Dapat Ditoleransi(Tidak Bisa Berbicara Kerenya Nyeri)" }));
        Anamnesis21.setName("Anamnesis21"); // NOI18N
        Anamnesis21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis21KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis21);
        Anamnesis21.setBounds(214, 790, 510, 23);

        Anamnesis22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya, Dilanjutkan Asesmen Awal Nyeri" }));
        Anamnesis22.setName("Anamnesis22"); // NOI18N
        Anamnesis22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Anamnesis22KeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis22);
        Anamnesis22.setBounds(214, 820, 510, 23);

        jLabel46.setText("Nyeri/Tidak Nyaman :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(45, 820, 165, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-11-2022" }));
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
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else if(RencanaTindakan.getText().trim().equals("")){
            Valid.textKosong(RencanaTindakan,"Rencana Tindakan");
        }else{
            if(Sequel.menyimpantf("penilaian_pre_anestesi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat, Tanggal & Jam",40,new String[]{
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
                    emptTeks();
            }
        }*/
    
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
        /*if(TNoRM.getText().trim().equals("")){
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
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal_operasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosa")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("tb")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("io2")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("pernapasan")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_cardiovasculer")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_paru")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_extrimitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_endokrin")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_ginjal")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_obatobatan")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_laborat")+"</td>"+
                               "<td valign='top'>"+rs.getString("fisik_penunjang")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_alergiobat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_alergilainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_penyakit_terapi")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_merokok")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_merokok")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_alkohol")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_alkohol")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_obat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_kebiasaan_ket_obat")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_cardiovasculer")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_respiratory")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_endocrine")+"</td>"+
                               "<td valign='top'>"+rs.getString("riwayat_medis_lainnya")+"</td>"+
                               "<td valign='top'>"+rs.getString("asa")+"</td>"+
                               "<td valign='top'>"+rs.getString("puasa")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_anestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("rencana_perawatan")+"</td>"+
                               "<td valign='top'>"+rs.getString("catatan_khusus")+"</td>"+
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

                    File f = new File("DataPenilaianAwalMedisRalan.html");            
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
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

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
        //Valid.pindah(evt,TglAsuhan,RencanaTindakan);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        //Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void Anamnesis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis1KeyPressed
        //Valid.pindah(evt,TglAsuhan,Hubungan);
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

    private void Anamnesis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis5KeyPressed

    private void Anamnesis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis6KeyPressed

    private void Anamnesis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis7KeyPressed

    private void Anamnesis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis8KeyPressed

    private void Anamnesis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis9KeyPressed

    private void Anamnesis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis10KeyPressed

    private void Anamnesis11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis11KeyPressed

    private void Anamnesis12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis12KeyPressed

    private void Anamnesis13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis13KeyPressed

    private void Anamnesis14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis14KeyPressed

    private void Anamnesis15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis15KeyPressed

    private void Anamnesis16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis16KeyPressed

    private void Anamnesis17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis17KeyPressed

    private void Anamnesis18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis18KeyPressed

    private void Hubungan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan1KeyPressed

    private void Hubungan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan2KeyPressed

    private void Anamnesis19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis19KeyPressed

    private void Anamnesis20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis20KeyPressed

    private void Hubungan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Hubungan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Hubungan3KeyPressed

    private void Anamnesis21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis21KeyPressed

    private void Anamnesis22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Anamnesis22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Anamnesis22KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianTambahanGeriatri dialog = new RMPenilaianTambahanGeriatri(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Anamnesis10;
    private widget.ComboBox Anamnesis11;
    private widget.ComboBox Anamnesis12;
    private widget.ComboBox Anamnesis13;
    private widget.ComboBox Anamnesis14;
    private widget.ComboBox Anamnesis15;
    private widget.ComboBox Anamnesis16;
    private widget.ComboBox Anamnesis17;
    private widget.ComboBox Anamnesis18;
    private widget.ComboBox Anamnesis19;
    private widget.ComboBox Anamnesis2;
    private widget.ComboBox Anamnesis20;
    private widget.ComboBox Anamnesis21;
    private widget.ComboBox Anamnesis22;
    private widget.ComboBox Anamnesis3;
    private widget.ComboBox Anamnesis4;
    private widget.ComboBox Anamnesis5;
    private widget.ComboBox Anamnesis6;
    private widget.ComboBox Anamnesis7;
    private widget.ComboBox Anamnesis8;
    private widget.ComboBox Anamnesis9;
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
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Hubungan;
    private widget.TextBox Hubungan1;
    private widget.TextBox Hubungan2;
    private widget.TextBox Hubungan3;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
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
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label11;
    private widget.Label label14;
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
        TglAsuhan.setDate(new Date());
        Diagnosa.setText("");
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
        BtnSimpan.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnHapus.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
        BtnEdit.setEnabled(akses.getpenilaian_pre_anestesi());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter,KdDokter.getText());
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_pre_anestesi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
        })==true){
            tampil();
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
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
}

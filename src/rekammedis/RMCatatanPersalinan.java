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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMCatatanPersalinan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMCatatanPersalinan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Nama Ahli Bedah","NIP","Nama Asisten/Perawat","Waktu Mulai","Waktu Selesai",
            "Diagnosa","Tindakan","Obat Analgesik/Anastesi","Obat Lain","Uraian Tindakan","Focus","Rate","Power","Shock","Diintegrasi","Kekurangan","Anjungan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
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
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(115);
            }else if(i==10){
                column.setPreferredWidth(115);
            }else if(i==11){
                column.setPreferredWidth(200);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setPreferredWidth(300);
            }else if(i==16){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(200);
            }else if(i==18){
                column.setPreferredWidth(200);
            }else if(i==19){
                column.setPreferredWidth(200);
            }else if(i==20){
                column.setPreferredWidth(200);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        UraianTindakan.setDocument(new batasInput((int)500).getKata(UraianTindakan));
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    NIP.requestFocus();
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
        MnDokumenESWL = new javax.swing.JMenuItem();
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
        WaktuSelesai = new widget.Tanggal();
        scrollPane20 = new widget.ScrollPane();
        UraianTindakan = new widget.TextArea();
        label12 = new widget.Label();
        WaktuMulai = new widget.Tanggal();
        label15 = new widget.Label();
        NIP = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        Diagnosa = new widget.TextBox();
        label16 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        label17 = new widget.Label();
        label18 = new widget.Label();
        label19 = new widget.Label();
        Diagnosa1 = new widget.TextBox();
        label20 = new widget.Label();
        Diagnosa2 = new widget.TextBox();
        label21 = new widget.Label();
        Diagnosa3 = new widget.TextBox();
        label23 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        label22 = new widget.Label();
        label24 = new widget.Label();
        Diagnosa4 = new widget.TextBox();
        Diagnosa5 = new widget.TextBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        Diagnosa6 = new widget.TextBox();
        label27 = new widget.Label();
        Diagnosa7 = new widget.TextBox();
        label28 = new widget.Label();
        label29 = new widget.Label();
        Kesadaran1 = new widget.ComboBox();
        label30 = new widget.Label();
        Kesadaran2 = new widget.ComboBox();
        label31 = new widget.Label();
        Diagnosa8 = new widget.TextBox();
        label32 = new widget.Label();
        Diagnosa9 = new widget.TextBox();
        label33 = new widget.Label();
        label34 = new widget.Label();
        Diagnosa10 = new widget.TextBox();
        label35 = new widget.Label();
        label36 = new widget.Label();
        Diagnosa11 = new widget.TextBox();
        label38 = new widget.Label();
        Diagnosa12 = new widget.TextBox();
        label37 = new widget.Label();
        Diagnosa13 = new widget.TextBox();
        label39 = new widget.Label();
        label40 = new widget.Label();
        Diagnosa14 = new widget.TextBox();
        label41 = new widget.Label();
        label42 = new widget.Label();
        Diagnosa15 = new widget.TextBox();
        label43 = new widget.Label();
        label44 = new widget.Label();
        Diagnosa16 = new widget.TextBox();
        label45 = new widget.Label();
        Diagnosa17 = new widget.TextBox();
        label46 = new widget.Label();
        Diagnosa18 = new widget.TextBox();
        label47 = new widget.Label();
        Diagnosa19 = new widget.TextBox();
        label48 = new widget.Label();
        Diagnosa20 = new widget.TextBox();
        label49 = new widget.Label();
        label50 = new widget.Label();
        label51 = new widget.Label();
        Diagnosa21 = new widget.TextBox();
        label52 = new widget.Label();
        label53 = new widget.Label();
        Diagnosa22 = new widget.TextBox();
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

        MnDokumenESWL.setBackground(new java.awt.Color(255, 255, 254));
        MnDokumenESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokumenESWL.setForeground(new java.awt.Color(50, 50, 50));
        MnDokumenESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokumenESWL.setText("Laporan/Dokumentasi Tindakan ESWL");
        MnDokumenESWL.setName("MnDokumenESWL"); // NOI18N
        MnDokumenESWL.setPreferredSize(new java.awt.Dimension(240, 26));
        MnDokumenESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokumenESWLActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDokumenESWL);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Catatan Persalinan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(750, 773));
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

        label14.setText("DPJP :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 70, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 70, 170, 23);

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
        BtnDokter.setBounds(338, 70, 28, 23);

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

        label11.setText("Selesai Persalinan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(475, 40, 110, 23);

        WaktuSelesai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2023 08:50:46" }));
        WaktuSelesai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuSelesai.setName("WaktuSelesai"); // NOI18N
        WaktuSelesai.setOpaque(false);
        WaktuSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuSelesai);
        WaktuSelesai.setBounds(589, 40, 135, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        UraianTindakan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        UraianTindakan.setColumns(20);
        UraianTindakan.setRows(20);
        UraianTindakan.setName("UraianTindakan"); // NOI18N
        UraianTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianTindakanKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(UraianTindakan);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(40, 120, 684, 213);

        label12.setText("Mulai Persalinan :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(181, 40, 110, 23);

        WaktuMulai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2023 08:50:47" }));
        WaktuMulai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuMulai.setName("WaktuMulai"); // NOI18N
        WaktuMulai.setOpaque(false);
        WaktuMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuMulaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuMulai);
        WaktuMulai.setBounds(295, 40, 135, 23);

        label15.setText("Bidan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(380, 70, 50, 23);

        NIP.setEditable(false);
        NIP.setName("NIP"); // NOI18N
        NIP.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(NIP);
        NIP.setBounds(432, 70, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(524, 70, 170, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnPetugas.setBounds(696, 70, 28, 23);

        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(286, 360, 60, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("1. Waktu Persalinan : Kala I + Kala II + Kala III");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(40, 360, 250, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 340, 750, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("CATATAN PERSALINAN");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 100, 180, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("KESIMPULAN POSTPARTUM");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 340, 180, 23);

        label17.setText("=");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(0, 360, 282, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("+");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(349, 360, 23, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("+");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(438, 360, 23, 23);

        Diagnosa1.setName("Diagnosa1"); // NOI18N
        Diagnosa1.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa1KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa1);
        Diagnosa1.setBounds(375, 360, 60, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("=");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(527, 360, 23, 23);

        Diagnosa2.setName("Diagnosa2"); // NOI18N
        Diagnosa2.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa2KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa2);
        Diagnosa2.setBounds(464, 360, 60, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("Jam");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(626, 360, 50, 23);

        Diagnosa3.setName("Diagnosa3"); // NOI18N
        Diagnosa3.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa3KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa3);
        Diagnosa3.setBounds(553, 360, 70, 23);

        label23.setText(":");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(0, 390, 106, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Utuh", "Rupture", "Episiotomi" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(110, 390, 105, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("2. Perineum");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(40, 390, 80, 23);

        label24.setText("Jahitan Luar :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(228, 390, 85, 23);

        Diagnosa4.setName("Diagnosa4"); // NOI18N
        Diagnosa4.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa4KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa4);
        Diagnosa4.setBounds(317, 390, 60, 23);

        Diagnosa5.setName("Diagnosa5"); // NOI18N
        Diagnosa5.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa5KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa5);
        Diagnosa5.setBounds(406, 390, 60, 23);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label25.setText("dg");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(380, 390, 23, 23);

        label26.setText("Jahitan Dalam :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(481, 390, 90, 23);

        Diagnosa6.setName("Diagnosa6"); // NOI18N
        Diagnosa6.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa6KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa6);
        Diagnosa6.setBounds(575, 390, 60, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setText("dg");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(638, 390, 23, 23);

        Diagnosa7.setName("Diagnosa7"); // NOI18N
        Diagnosa7.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa7KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa7);
        Diagnosa7.setBounds(664, 390, 60, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("3. Anak");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(40, 420, 50, 23);

        label29.setText(":");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(0, 420, 84, 23);

        Kesadaran1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        Kesadaran1.setName("Kesadaran1"); // NOI18N
        Kesadaran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesadaran1KeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran1);
        Kesadaran1.setBounds(88, 420, 110, 23);

        label30.setText("Status :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(215, 420, 50, 23);

        Kesadaran2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Mati" }));
        Kesadaran2.setName("Kesadaran2"); // NOI18N
        Kesadaran2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kesadaran2KeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran2);
        Kesadaran2.setBounds(269, 420, 85, 23);

        label31.setText("APGAR Score :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(363, 420, 90, 23);

        Diagnosa8.setName("Diagnosa8"); // NOI18N
        Diagnosa8.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa8KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa8);
        Diagnosa8.setBounds(457, 420, 130, 23);

        label32.setText("BB :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(601, 420, 30, 23);

        Diagnosa9.setName("Diagnosa9"); // NOI18N
        Diagnosa9.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa9KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa9);
        Diagnosa9.setBounds(635, 420, 60, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("cm");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(138, 450, 40, 23);

        label34.setText("PB :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(0, 450, 71, 23);

        Diagnosa10.setName("Diagnosa10"); // NOI18N
        Diagnosa10.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa10KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa10);
        Diagnosa10.setBounds(75, 450, 60, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("gr");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(432, 480, 30, 23);

        label36.setText("Kelainan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(160, 450, 70, 23);

        Diagnosa11.setName("Diagnosa11"); // NOI18N
        Diagnosa11.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa11KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa11);
        Diagnosa11.setBounds(234, 450, 490, 23);

        label38.setText(":");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(0, 480, 99, 23);

        Diagnosa12.setName("Diagnosa12"); // NOI18N
        Diagnosa12.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa12KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa12);
        Diagnosa12.setBounds(103, 480, 75, 23);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("4. Ketuban");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(40, 480, 80, 23);

        Diagnosa13.setName("Diagnosa13"); // NOI18N
        Diagnosa13.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa13KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa13);
        Diagnosa13.setBounds(240, 480, 75, 23);

        label39.setText("Placenta :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(176, 480, 60, 23);

        label40.setText("Ukuran :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(305, 480, 60, 23);

        Diagnosa14.setName("Diagnosa14"); // NOI18N
        Diagnosa14.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa14KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa14);
        Diagnosa14.setBounds(369, 480, 60, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("gram");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(698, 420, 40, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("cm");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(574, 480, 40, 23);

        Diagnosa15.setName("Diagnosa15"); // NOI18N
        Diagnosa15.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa15KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa15);
        Diagnosa15.setBounds(511, 480, 60, 23);

        label43.setText("Tali Pusat :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(437, 480, 70, 23);

        label44.setText("Insertio :");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label44);
        label44.setBounds(585, 480, 60, 23);

        Diagnosa16.setName("Diagnosa16"); // NOI18N
        Diagnosa16.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa16KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa16);
        Diagnosa16.setBounds(649, 480, 75, 23);

        label45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label45.setText("cc");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label45);
        label45.setBounds(712, 510, 30, 23);

        Diagnosa17.setName("Diagnosa17"); // NOI18N
        Diagnosa17.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa17KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa17);
        Diagnosa17.setBounds(657, 510, 52, 23);

        label46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label46.setText("=");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label46);
        label46.setBounds(631, 510, 23, 23);

        Diagnosa18.setName("Diagnosa18"); // NOI18N
        Diagnosa18.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa18KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa18);
        Diagnosa18.setBounds(499, 510, 50, 23);

        label47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label47.setText("+");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label47);
        label47.setBounds(473, 510, 23, 23);

        Diagnosa19.setName("Diagnosa19"); // NOI18N
        Diagnosa19.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa19KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa19);
        Diagnosa19.setBounds(420, 510, 50, 23);

        label48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label48.setText("+");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label48);
        label48.setBounds(394, 510, 23, 23);

        Diagnosa20.setName("Diagnosa20"); // NOI18N
        Diagnosa20.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa20KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa20);
        Diagnosa20.setBounds(341, 510, 50, 23);

        label49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label49.setText("5. Darah Yang Keluar : Kala I + Kala II + Kala III + Kala IV");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label49);
        label49.setBounds(40, 510, 290, 23);

        label50.setText("=");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label50);
        label50.setBounds(0, 510, 337, 23);

        label51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label51.setText("+");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label51);
        label51.setBounds(552, 510, 23, 23);

        Diagnosa21.setName("Diagnosa21"); // NOI18N
        Diagnosa21.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa21KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa21);
        Diagnosa21.setBounds(578, 510, 50, 23);

        label52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label52.setText("6. Keadaan Ibu Post Partum (2 jam) :");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label52);
        label52.setBounds(40, 540, 290, 23);

        label53.setText("Kondisi Umum :");
        label53.setName("label53"); // NOI18N
        label53.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label53);
        label53.setBounds(0, 560, 130, 23);

        Diagnosa22.setName("Diagnosa22"); // NOI18N
        Diagnosa22.setPreferredSize(new java.awt.Dimension(80, 23));
        Diagnosa22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Diagnosa22KeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa22);
        Diagnosa22.setBounds(144, 560, 580, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Catatan Persalinan", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "27-11-2023" }));
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

        TabRawat.addTab("Data Catatan Persalinan", internalFrame3);

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
            Valid.textKosong(BtnDokter,"Ahli Bedah");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else if(UraianTindakan.getText().trim().equals("")){
            Valid.textKosong(UraianTindakan,"Uraian Tindakan");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else{
            if(Sequel.menyimpantf("hasil_tindakan_eswl","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Waktu Mulai",17,new String[]{
                    TNoRw.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                    Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),KdDokter.getText(), 
                    NIP.getText(),Diagnosa.getText(),Tindakan.getText(),ObatAnastesi.getText(),ObatLainLain.getText(),UraianTindakan.getText(), 
                    Focus.getText(),Rate.getText(),Power.getText(),Shock.getText(),Diintegrasi.getText(),Kekurangan.getText(),Anjungan.getText()
                })==true){
                    emptTeks();
            }
        }*/
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Anjungan,BtnBatal);
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
            Valid.textKosong(BtnDokter,"Ahli Bedah");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else if(UraianTindakan.getText().trim().equals("")){
            Valid.textKosong(UraianTindakan,"Uraian Tindakan");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Ahli Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Asisten/Perawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Waktu Mulai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Waktu Selesai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Analgesik/Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Uraian Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Focus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Power</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Shock</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diintegrasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kekurangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anjungan</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2300px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataDokumentasiTindakanESWL.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2300px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA DOKUMENTASI TINDAKAN ESWL<br><br></font>"+        
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
        Valid.pindah(evt,WaktuSelesai,BtnPetugas);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void WaktuSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuSelesaiKeyPressed
        Valid.pindah2(evt,WaktuMulai,BtnDokter);
    }//GEN-LAST:event_WaktuSelesaiKeyPressed

    private void MnDokumenESWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokumenESWLActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())); 
            
            Valid.MyReportqry("rptCetakDokumentasiTindakanESWL.jasper","report","::[ Laporan Dokumentasi Tindakan ESWL ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.diagnosa,hasil_tindakan_eswl.tindakan,hasil_tindakan_eswl.obat_analgesik,"+
                "hasil_tindakan_eswl.obat_lain,hasil_tindakan_eswl.uraian_tindakan,hasil_tindakan_eswl.uraian_tindakan_focus,hasil_tindakan_eswl.uraian_tindakan_rate,"+
                "hasil_tindakan_eswl.uraian_tindakan_power,hasil_tindakan_eswl.uraian_tindakan_shock,hasil_tindakan_eswl.diintegrasi,hasil_tindakan_eswl.kekurangan,hasil_tindakan_eswl.anjungan,"+
                "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where hasil_tindakan_eswl.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                "and hasil_tindakan_eswl.mulai='"+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+"'",param);
        }
    }//GEN-LAST:event_MnDokumenESWLActionPerformed

    private void UraianTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianTindakanKeyPressed
       // Valid.pindah2(evt,ObatLainLain,Focus);
    }//GEN-LAST:event_UraianTindakanKeyPressed

    private void WaktuMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuMulaiKeyPressed
       //Valid.pindah2(evt,Anjungan,WaktuSelesai);
    }//GEN-LAST:event_WaktuMulaiKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnDokter,Diagnosa);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        //Valid.pindah(evt,BtnPetugas,Tindakan);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void Diagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa1KeyPressed

    private void Diagnosa2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa2KeyPressed

    private void Diagnosa3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa3KeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        //Valid.pindah(evt,Keadaan,GCS);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void Diagnosa4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa4KeyPressed

    private void Diagnosa5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa5KeyPressed

    private void Diagnosa6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa6KeyPressed

    private void Diagnosa7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa7KeyPressed

    private void Kesadaran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesadaran1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesadaran1KeyPressed

    private void Kesadaran2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kesadaran2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kesadaran2KeyPressed

    private void Diagnosa8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa8KeyPressed

    private void Diagnosa9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa9KeyPressed

    private void Diagnosa10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa10KeyPressed

    private void Diagnosa11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa11KeyPressed

    private void Diagnosa12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa12KeyPressed

    private void Diagnosa13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa13KeyPressed

    private void Diagnosa14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa14KeyPressed

    private void Diagnosa15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa15KeyPressed

    private void Diagnosa16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa16KeyPressed

    private void Diagnosa17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa17KeyPressed

    private void Diagnosa18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa18KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa18KeyPressed

    private void Diagnosa19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa19KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa19KeyPressed

    private void Diagnosa20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa20KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa20KeyPressed

    private void Diagnosa21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa21KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa21KeyPressed

    private void Diagnosa22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Diagnosa22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Diagnosa22KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCatatanPersalinan dialog = new RMCatatanPersalinan(new javax.swing.JFrame(), true);
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Diagnosa;
    private widget.TextBox Diagnosa1;
    private widget.TextBox Diagnosa10;
    private widget.TextBox Diagnosa11;
    private widget.TextBox Diagnosa12;
    private widget.TextBox Diagnosa13;
    private widget.TextBox Diagnosa14;
    private widget.TextBox Diagnosa15;
    private widget.TextBox Diagnosa16;
    private widget.TextBox Diagnosa17;
    private widget.TextBox Diagnosa18;
    private widget.TextBox Diagnosa19;
    private widget.TextBox Diagnosa2;
    private widget.TextBox Diagnosa20;
    private widget.TextBox Diagnosa21;
    private widget.TextBox Diagnosa22;
    private widget.TextBox Diagnosa3;
    private widget.TextBox Diagnosa4;
    private widget.TextBox Diagnosa5;
    private widget.TextBox Diagnosa6;
    private widget.TextBox Diagnosa7;
    private widget.TextBox Diagnosa8;
    private widget.TextBox Diagnosa9;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.ComboBox Kesadaran;
    private widget.ComboBox Kesadaran1;
    private widget.ComboBox Kesadaran2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnDokumenESWL;
    private widget.TextBox NIP;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglLahir;
    private widget.TextArea UraianTindakan;
    private widget.Tanggal WaktuMulai;
    private widget.Tanggal WaktuSelesai;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label50;
    private widget.Label label51;
    private widget.Label label52;
    private widget.Label label53;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane20;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                        "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.diagnosa,hasil_tindakan_eswl.tindakan,hasil_tindakan_eswl.obat_analgesik,"+
                        "hasil_tindakan_eswl.obat_lain,hasil_tindakan_eswl.uraian_tindakan,hasil_tindakan_eswl.uraian_tindakan_focus,hasil_tindakan_eswl.uraian_tindakan_rate,"+
                        "hasil_tindakan_eswl.uraian_tindakan_power,hasil_tindakan_eswl.uraian_tindakan_shock,hasil_tindakan_eswl.diintegrasi,hasil_tindakan_eswl.kekurangan,hasil_tindakan_eswl.anjungan,"+
                        "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                        "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where hasil_tindakan_eswl.mulai between ? and ? order by hasil_tindakan_eswl.mulai");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                        "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.diagnosa,hasil_tindakan_eswl.tindakan,hasil_tindakan_eswl.obat_analgesik,"+
                        "hasil_tindakan_eswl.obat_lain,hasil_tindakan_eswl.uraian_tindakan,hasil_tindakan_eswl.uraian_tindakan_focus,hasil_tindakan_eswl.uraian_tindakan_rate,"+
                        "hasil_tindakan_eswl.uraian_tindakan_power,hasil_tindakan_eswl.uraian_tindakan_shock,hasil_tindakan_eswl.diintegrasi,hasil_tindakan_eswl.kekurangan,hasil_tindakan_eswl.anjungan,"+
                        "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                        "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where "+
                        "hasil_tindakan_eswl.mulai between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "hasil_tindakan_eswl.diagnosa like ? or hasil_tindakan_eswl.tindakan like ?) order by hasil_tindakan_eswl.mulai");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("mulai"),rs.getString("selesai"),rs.getString("diagnosa"),rs.getString("tindakan"),rs.getString("obat_analgesik"),rs.getString("obat_lain"),rs.getString("uraian_tindakan"),rs.getString("uraian_tindakan_focus"),
                        rs.getString("uraian_tindakan_rate"),rs.getString("uraian_tindakan_power"),rs.getString("uraian_tindakan_shock"),rs.getString("diintegrasi"),rs.getString("kekurangan"),rs.getString("anjungan")
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
        /*UraianTindakan.setText("");
        WaktuMulai.setDate(new Date());
        WaktuSelesai.setDate(new Date());
        Diagnosa.setText("");
        Tindakan.setText("");
        ObatAnastesi.setText("");
        ObatLainLain.setText("");
        UraianTindakan.setText("");
        Focus.setText("");
        Rate.setText("");
        Power.setText("");
        Shock.setText("");
        Diintegrasi.setText("");
        Kekurangan.setText("");
        Anjungan.setText("");
        TabRawat.setSelectedIndex(0);
        Diagnosa.requestFocus();*/
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            /*TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            ObatAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            ObatLainLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            UraianTindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Focus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Rate.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Power.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Shock.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Diintegrasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kekurangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Anjungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Valid.SetTgl2(WaktuMulai,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl2(WaktuSelesai,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());*/
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
        BtnSimpan.setEnabled(akses.gethasil_tindakan_eswl());
        BtnHapus.setEnabled(akses.gethasil_tindakan_eswl());
        BtnEdit.setEnabled(akses.gethasil_tindakan_eswl());
        BtnEdit.setEnabled(akses.gethasil_tindakan_eswl());
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
        if(Sequel.queryu2tf("delete from hasil_tindakan_eswl where no_rawat=? and mulai=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        /*if(Sequel.mengedittf("hasil_tindakan_eswl","no_rawat=? and mulai=?","no_rawat=?,mulai=?,selesai=?,kd_dokter=?,nip=?,diagnosa=?,tindakan=?,"+
                "obat_analgesik=?,obat_lain=?,uraian_tindakan=?,uraian_tindakan_focus=?,uraian_tindakan_rate=?,uraian_tindakan_power=?,uraian_tindakan_shock=?,"+
                "diintegrasi=?,kekurangan=?,anjungan=?",19,new String[]{
                TNoRw.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),KdDokter.getText(), 
                NIP.getText(),Diagnosa.getText(),Tindakan.getText(),ObatAnastesi.getText(),ObatLainLain.getText(),UraianTindakan.getText(), 
                Focus.getText(),Rate.getText(),Power.getText(),Shock.getText(),Diintegrasi.getText(),Kekurangan.getText(),Anjungan.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(Diagnosa.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(ObatAnastesi.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(ObatLainLain.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(UraianTindakan.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Focus.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(Rate.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(Power.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(Shock.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(Diintegrasi.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Kekurangan.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(Anjungan.getText(),tbObat.getSelectedRow(),22);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }*/
    }
}

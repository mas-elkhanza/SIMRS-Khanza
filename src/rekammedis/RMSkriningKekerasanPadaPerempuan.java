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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
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
public final class RMSkriningKekerasanPadaPerempuan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningKekerasanPadaPerempuan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Umur","Kode Petugas","Nama Petugas","Tanggal",
            "Pertanyaan Awal 1","N.P.A.1","Pertanyaan Awal 2","N.P.A.2","Pertanyaan Lanjutan 1","N.P.L.1",
            "Pertanyaan Lanjutan 2","N.P.L.2","Pertanyaan Lanjutan 3","N.P.L.3","Pertanyaan Lanjutan 4","N.P.L.4",
            "Pertanyaan Lanjutan 5","N.P.L.5","Pertanyaan Lanjutan 6","N.P.L.6","Total Skor","Hasil Skrining"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
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
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(118);
            }else if(i==9){
                column.setPreferredWidth(45);
            }else if(i==10){
                column.setPreferredWidth(118);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(118);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(118);
            }else if(i==15){
                column.setPreferredWidth(45);
            }else if(i==16){
                column.setPreferredWidth(118);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(118);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(118);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(118);
            }else if(i==23){
                column.setPreferredWidth(45);
            }else if(i==24){
                column.setPreferredWidth(60);
            }else if(i==25){
                column.setPreferredWidth(230);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
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
                }  
                KdPetugas.requestFocus();
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
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        jam();
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
        MnSkriningKekerasanPadaPerempuan = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Umur = new widget.TextBox();
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
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        PertanyaanAwal1 = new widget.ComboBox();
        jLabel92 = new widget.Label();
        PertanyaanAwal2 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel73 = new widget.Label();
        TotalHasil = new widget.TextBox();
        NilaiPertanyaanAwal1 = new widget.TextBox();
        NilaiPertanyaanAwal2 = new widget.TextBox();
        PertanyaanLanjutan1 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        NilaiPertanyaanLanjutan1 = new widget.TextBox();
        jLabel71 = new widget.Label();
        NilaiPertanyaanLanjutan2 = new widget.TextBox();
        PertanyaanLanjutan2 = new widget.ComboBox();
        jLabel148 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        PertanyaanLanjutan3 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        NilaiPertanyaanLanjutan3 = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        PertanyaanLanjutan4 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        NilaiPertanyaanLanjutan4 = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        PertanyaanLanjutan5 = new widget.ComboBox();
        jLabel89 = new widget.Label();
        NilaiPertanyaanLanjutan5 = new widget.TextBox();
        jLabel90 = new widget.Label();
        PertanyaanLanjutan6 = new widget.ComboBox();
        jLabel91 = new widget.Label();
        NilaiPertanyaanLanjutan6 = new widget.TextBox();
        jLabel93 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jLabel149 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningKekerasanPadaPerempuan.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningKekerasanPadaPerempuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningKekerasanPadaPerempuan.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningKekerasanPadaPerempuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningKekerasanPadaPerempuan.setText("Formulir Skrining Kekerasan Pada Perempuan");
        MnSkriningKekerasanPadaPerempuan.setName("MnSkriningKekerasanPadaPerempuan"); // NOI18N
        MnSkriningKekerasanPadaPerempuan.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningKekerasanPadaPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningKekerasanPadaPerempuanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningKekerasanPadaPerempuan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Kekerasan Pada Perempuan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 426));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 403));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

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

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-07-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        PertanyaanAwal1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Ketegangan", "Beberapa Ketegangan", "Banyak Ketegangan" }));
        PertanyaanAwal1.setName("PertanyaanAwal1"); // NOI18N
        PertanyaanAwal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanAwal1ItemStateChanged(evt);
            }
        });
        PertanyaanAwal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanAwal1KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanAwal1);
        PertanyaanAwal1.setBounds(530, 90, 160, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(685, 90, 50, 23);

        PertanyaanAwal2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Kesulitan", "Beberapa Kesulitan", "Kesulitan Besar" }));
        PertanyaanAwal2.setName("PertanyaanAwal2"); // NOI18N
        PertanyaanAwal2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanAwal2ItemStateChanged(evt);
            }
        });
        PertanyaanAwal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanAwal2KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanAwal2);
        PertanyaanAwal2.setBounds(530, 120, 160, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(685, 120, 50, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(665, 370, 70, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(739, 370, 50, 23);

        NilaiPertanyaanAwal1.setEditable(false);
        NilaiPertanyaanAwal1.setText("0");
        NilaiPertanyaanAwal1.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanAwal1.setName("NilaiPertanyaanAwal1"); // NOI18N
        FormInput.add(NilaiPertanyaanAwal1);
        NilaiPertanyaanAwal1.setBounds(739, 90, 50, 23);

        NilaiPertanyaanAwal2.setEditable(false);
        NilaiPertanyaanAwal2.setText("0");
        NilaiPertanyaanAwal2.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanAwal2.setName("NilaiPertanyaanAwal2"); // NOI18N
        FormInput.add(NilaiPertanyaanAwal2);
        NilaiPertanyaanAwal2.setBounds(739, 120, 50, 23);

        PertanyaanLanjutan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan1.setName("PertanyaanLanjutan1"); // NOI18N
        PertanyaanLanjutan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan1ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan1KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan1);
        PertanyaanLanjutan1.setBounds(560, 170, 130, 23);

        jLabel70.setText("Nilai :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(685, 170, 50, 23);

        NilaiPertanyaanLanjutan1.setEditable(false);
        NilaiPertanyaanLanjutan1.setText("0");
        NilaiPertanyaanLanjutan1.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan1.setName("NilaiPertanyaanLanjutan1"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan1);
        NilaiPertanyaanLanjutan1.setBounds(739, 170, 50, 23);

        jLabel71.setText("Nilai :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(685, 200, 50, 23);

        NilaiPertanyaanLanjutan2.setEditable(false);
        NilaiPertanyaanLanjutan2.setText("0");
        NilaiPertanyaanLanjutan2.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan2.setName("NilaiPertanyaanLanjutan2"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan2);
        NilaiPertanyaanLanjutan2.setBounds(739, 200, 50, 23);

        PertanyaanLanjutan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan2.setName("PertanyaanLanjutan2"); // NOI18N
        PertanyaanLanjutan2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan2ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan2KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan2);
        PertanyaanLanjutan2.setBounds(560, 200, 130, 23);

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Hasil Skrining");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(44, 370, 90, 23);

        HasilSkrining.setEditable(false);
        HasilSkrining.setFocusTraversalPolicyProvider(true);
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(121, 370, 420, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. PERTANYAAN AWAL");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 200, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 90, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Secara umum, bagaimana Anda menggambarkan hubungan Anda ?");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(57, 90, 430, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("2.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 120, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Seperti apa saat Anda dan pasangan berdebat ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(57, 120, 430, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 150, 807, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("II. PERTANYAAN LANJUTAN");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 150, 200, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Apakah pertengkaran pernah membuat Anda merasa sedih atau buruk tentang diri sendiri ?");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(57, 170, 470, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("1.");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(44, 170, 20, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("2.");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(44, 200, 20, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Apakah pertengkaran pernah menghasilkan pukulan, tendangan, atau dorongan ?");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(57, 200, 470, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("3.");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(44, 230, 20, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Apakah Anda pernah merasa takut dengan apa yang pasangan Anda katakan atau lakukan ?");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(57, 230, 470, 23);

        PertanyaanLanjutan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan3.setName("PertanyaanLanjutan3"); // NOI18N
        PertanyaanLanjutan3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan3ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan3KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan3);
        PertanyaanLanjutan3.setBounds(560, 230, 130, 23);

        jLabel72.setText("Nilai :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(685, 230, 50, 23);

        NilaiPertanyaanLanjutan3.setEditable(false);
        NilaiPertanyaanLanjutan3.setText("0");
        NilaiPertanyaanLanjutan3.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan3.setName("NilaiPertanyaanLanjutan3"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan3);
        NilaiPertanyaanLanjutan3.setBounds(739, 230, 50, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("4.");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(44, 260, 20, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Apakah pasangan Anda pernah melecehkan Anda secara fisik ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(57, 260, 470, 23);

        PertanyaanLanjutan4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan4.setName("PertanyaanLanjutan4"); // NOI18N
        PertanyaanLanjutan4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan4ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan4KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan4);
        PertanyaanLanjutan4.setBounds(560, 260, 130, 23);

        jLabel74.setText("Nilai :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(685, 260, 50, 23);

        NilaiPertanyaanLanjutan4.setEditable(false);
        NilaiPertanyaanLanjutan4.setText("0");
        NilaiPertanyaanLanjutan4.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan4.setName("NilaiPertanyaanLanjutan4"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan4);
        NilaiPertanyaanLanjutan4.setBounds(739, 260, 50, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Pernahkah pasangan Anda melecehkan Anda secara emosional ?");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(57, 290, 470, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("5.");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(44, 290, 20, 23);

        PertanyaanLanjutan5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan5.setName("PertanyaanLanjutan5"); // NOI18N
        PertanyaanLanjutan5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan5ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan5KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan5);
        PertanyaanLanjutan5.setBounds(560, 290, 130, 23);

        jLabel89.setText("Nilai :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(685, 290, 50, 23);

        NilaiPertanyaanLanjutan5.setEditable(false);
        NilaiPertanyaanLanjutan5.setText("0");
        NilaiPertanyaanLanjutan5.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan5.setName("NilaiPertanyaanLanjutan5"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan5);
        NilaiPertanyaanLanjutan5.setBounds(739, 290, 50, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Apakah pasangan Anda pernah melecehkan Anda secara seksual ?");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(57, 320, 470, 23);

        PertanyaanLanjutan6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Pernah", "Kadang-kadang", "Sering" }));
        PertanyaanLanjutan6.setName("PertanyaanLanjutan6"); // NOI18N
        PertanyaanLanjutan6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PertanyaanLanjutan6ItemStateChanged(evt);
            }
        });
        PertanyaanLanjutan6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PertanyaanLanjutan6KeyPressed(evt);
            }
        });
        FormInput.add(PertanyaanLanjutan6);
        PertanyaanLanjutan6.setBounds(560, 320, 130, 23);

        jLabel91.setText("Nilai :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(685, 320, 50, 23);

        NilaiPertanyaanLanjutan6.setEditable(false);
        NilaiPertanyaanLanjutan6.setText("0");
        NilaiPertanyaanLanjutan6.setFocusTraversalPolicyProvider(true);
        NilaiPertanyaanLanjutan6.setName("NilaiPertanyaanLanjutan6"); // NOI18N
        FormInput.add(NilaiPertanyaanLanjutan6);
        NilaiPertanyaanLanjutan6.setBounds(739, 320, 50, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("6.");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(44, 320, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 350, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("III. INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 350, 200, 23);

        jLabel149.setText(":");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(0, 370, 117, 23);

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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,PertanyaanLanjutan6,BtnBatal);
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
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
        petugas.dispose();
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Secara umum, bagaimana Anda<br>menggambarkan hubungan Anda ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.A.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Seperti apa saat Anda<br>dan pasangan berdebat ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.A.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah pertengkaran pernah membuat Anda merasa<br>sedih atau buruk tentang diri sendiri ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah pertengkaran pernah menghasilkan<br>pukulan, tendangan, atau dorongan ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah Anda pernah merasa takut dengan apa<br>yang pasangan Anda katakan atau lakukan ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah pasangan Anda pernah<br>melecehkan Anda secara fisik ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pernahkah pasangan Anda melecehkan<br>Anda secara emosional ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Apakah pasangan Anda pernah<br>melecehkan Anda secara seksual ?</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.P.L.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
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

                File f = new File("DataSkriningKekerasanPadaPerempuan.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING KEKERASAN PADA PEREMPUAN<br><br></font>"+        
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,TCari,PertanyaanAwal1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningKekerasanPadaPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningKekerasanPadaPerempuanActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningKekerasanPadaPerempuan.jasper","report","::[ Formulir Skrining Kekerasan Pada Perempuan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_kekerasan_pada_perempuan.nip,"+
                    "petugas.nama,skrining_kekerasan_pada_perempuan.tanggal,skrining_kekerasan_pada_perempuan.menggambarkan_hubungan,skrining_kekerasan_pada_perempuan.skor_menggambarkan_hubungan,"+
                    "skrining_kekerasan_pada_perempuan.berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.skor_berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.pertengkaran_membuat_sedih,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_membuat_sedih,skrining_kekerasan_pada_perempuan.pertengkaran_menghasilkan_pukulan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_menghasilkan_pukulan,skrining_kekerasan_pada_perempuan.pernah_merasa_takut_dengan_pasangan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pernah_merasa_takut_dengan_pasangan,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_fisik,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_fisik,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_imosional,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_imosional,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_seksual,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_seksual,skrining_kekerasan_pada_perempuan.totalskor,skrining_kekerasan_pada_perempuan.hasil_skrining "+
                    "from skrining_kekerasan_pada_perempuan inner join reg_periksa on skrining_kekerasan_pada_perempuan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kekerasan_pada_perempuan.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningKekerasanPadaPerempuanActionPerformed

    private void PertanyaanAwal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanAwal1ItemStateChanged
        NilaiPertanyaanAwal1.setText((PertanyaanAwal1.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanAwal1ItemStateChanged

    private void PertanyaanAwal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanAwal1KeyPressed
        Valid.pindah(evt,TCari,PertanyaanAwal2);
    }//GEN-LAST:event_PertanyaanAwal1KeyPressed

    private void PertanyaanAwal2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanAwal2ItemStateChanged
        NilaiPertanyaanAwal2.setText((PertanyaanAwal2.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanAwal2ItemStateChanged

    private void PertanyaanAwal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanAwal2KeyPressed
        Valid.pindah(evt,PertanyaanAwal1,PertanyaanLanjutan1);
    }//GEN-LAST:event_PertanyaanAwal2KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PertanyaanLanjutan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan1ItemStateChanged
        NilaiPertanyaanLanjutan1.setText((PertanyaanLanjutan1.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan1ItemStateChanged

    private void PertanyaanLanjutan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan1KeyPressed
        Valid.pindah(evt,PertanyaanAwal2,PertanyaanLanjutan2);
    }//GEN-LAST:event_PertanyaanLanjutan1KeyPressed

    private void PertanyaanLanjutan2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan2ItemStateChanged
        NilaiPertanyaanLanjutan2.setText((PertanyaanLanjutan2.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan2ItemStateChanged

    private void PertanyaanLanjutan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan2KeyPressed
        Valid.pindah(evt,PertanyaanLanjutan1,PertanyaanLanjutan3);
    }//GEN-LAST:event_PertanyaanLanjutan2KeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        //Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void PertanyaanLanjutan3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan3ItemStateChanged
        NilaiPertanyaanLanjutan3.setText((PertanyaanLanjutan3.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan3ItemStateChanged

    private void PertanyaanLanjutan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan3KeyPressed
        Valid.pindah(evt,PertanyaanLanjutan2,PertanyaanLanjutan4);
    }//GEN-LAST:event_PertanyaanLanjutan3KeyPressed

    private void PertanyaanLanjutan4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan4ItemStateChanged
        NilaiPertanyaanLanjutan4.setText((PertanyaanLanjutan4.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan4ItemStateChanged

    private void PertanyaanLanjutan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan4KeyPressed
        Valid.pindah(evt,PertanyaanLanjutan3,PertanyaanLanjutan5);
    }//GEN-LAST:event_PertanyaanLanjutan4KeyPressed

    private void PertanyaanLanjutan5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan5ItemStateChanged
        NilaiPertanyaanLanjutan5.setText((PertanyaanLanjutan5.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan5ItemStateChanged

    private void PertanyaanLanjutan5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan5KeyPressed
        Valid.pindah(evt,PertanyaanLanjutan4,PertanyaanLanjutan6);
    }//GEN-LAST:event_PertanyaanLanjutan5KeyPressed

    private void PertanyaanLanjutan6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan6ItemStateChanged
        NilaiPertanyaanLanjutan6.setText((PertanyaanLanjutan6.getSelectedIndex()+1)+"");
        isTotal();
    }//GEN-LAST:event_PertanyaanLanjutan6ItemStateChanged

    private void PertanyaanLanjutan6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PertanyaanLanjutan6KeyPressed
        Valid.pindah(evt,PertanyaanLanjutan5,BtnSimpan);
    }//GEN-LAST:event_PertanyaanLanjutan6KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningKekerasanPadaPerempuan dialog = new RMSkriningKekerasanPadaPerempuan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningKekerasanPadaPerempuan;
    private widget.TextBox NilaiPertanyaanAwal1;
    private widget.TextBox NilaiPertanyaanAwal2;
    private widget.TextBox NilaiPertanyaanLanjutan1;
    private widget.TextBox NilaiPertanyaanLanjutan2;
    private widget.TextBox NilaiPertanyaanLanjutan3;
    private widget.TextBox NilaiPertanyaanLanjutan4;
    private widget.TextBox NilaiPertanyaanLanjutan5;
    private widget.TextBox NilaiPertanyaanLanjutan6;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PertanyaanAwal1;
    private widget.ComboBox PertanyaanAwal2;
    private widget.ComboBox PertanyaanLanjutan1;
    private widget.ComboBox PertanyaanLanjutan2;
    private widget.ComboBox PertanyaanLanjutan3;
    private widget.ComboBox PertanyaanLanjutan4;
    private widget.ComboBox PertanyaanLanjutan5;
    private widget.ComboBox PertanyaanLanjutan6;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalHasil;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
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
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_kekerasan_pada_perempuan.nip,"+
                    "petugas.nama,skrining_kekerasan_pada_perempuan.tanggal,skrining_kekerasan_pada_perempuan.menggambarkan_hubungan,skrining_kekerasan_pada_perempuan.skor_menggambarkan_hubungan,"+
                    "skrining_kekerasan_pada_perempuan.berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.skor_berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.pertengkaran_membuat_sedih,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_membuat_sedih,skrining_kekerasan_pada_perempuan.pertengkaran_menghasilkan_pukulan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_menghasilkan_pukulan,skrining_kekerasan_pada_perempuan.pernah_merasa_takut_dengan_pasangan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pernah_merasa_takut_dengan_pasangan,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_fisik,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_fisik,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_imosional,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_imosional,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_seksual,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_seksual,skrining_kekerasan_pada_perempuan.totalskor,skrining_kekerasan_pada_perempuan.hasil_skrining "+
                    "from skrining_kekerasan_pada_perempuan inner join reg_periksa on skrining_kekerasan_pada_perempuan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kekerasan_pada_perempuan.nip=petugas.nip "+
                    "where skrining_kekerasan_pada_perempuan.tanggal between ? and ? order by skrining_kekerasan_pada_perempuan.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_kekerasan_pada_perempuan.nip,"+
                    "petugas.nama,skrining_kekerasan_pada_perempuan.tanggal,skrining_kekerasan_pada_perempuan.menggambarkan_hubungan,skrining_kekerasan_pada_perempuan.skor_menggambarkan_hubungan,"+
                    "skrining_kekerasan_pada_perempuan.berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.skor_berdebat_dengan_pasangan,skrining_kekerasan_pada_perempuan.pertengkaran_membuat_sedih,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_membuat_sedih,skrining_kekerasan_pada_perempuan.pertengkaran_menghasilkan_pukulan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pertengkaran_menghasilkan_pukulan,skrining_kekerasan_pada_perempuan.pernah_merasa_takut_dengan_pasangan,"+
                    "skrining_kekerasan_pada_perempuan.skor_pernah_merasa_takut_dengan_pasangan,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_fisik,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_fisik,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_imosional,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_imosional,skrining_kekerasan_pada_perempuan.pasangan_melecehkan_secara_seksual,"+
                    "skrining_kekerasan_pada_perempuan.skor_pasangan_melecehkan_secara_seksual,skrining_kekerasan_pada_perempuan.totalskor,skrining_kekerasan_pada_perempuan.hasil_skrining "+
                    "from skrining_kekerasan_pada_perempuan inner join reg_periksa on skrining_kekerasan_pada_perempuan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_kekerasan_pada_perempuan.nip=petugas.nip "+
                    "where skrining_kekerasan_pada_perempuan.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_kekerasan_pada_perempuan.nip like ? or petugas.nama like ?) "+
                    "order by skrining_kekerasan_pada_perempuan.tanggal ");
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
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("menggambarkan_hubungan"),rs.getString("skor_menggambarkan_hubungan"),rs.getString("berdebat_dengan_pasangan"),
                        rs.getString("skor_berdebat_dengan_pasangan"),rs.getString("pertengkaran_membuat_sedih"),rs.getString("skor_pertengkaran_membuat_sedih"),rs.getString("pertengkaran_menghasilkan_pukulan"),
                        rs.getString("skor_pertengkaran_menghasilkan_pukulan"),rs.getString("pernah_merasa_takut_dengan_pasangan"),rs.getString("skor_pernah_merasa_takut_dengan_pasangan"),
                        rs.getString("pasangan_melecehkan_secara_fisik"),rs.getString("skor_pasangan_melecehkan_secara_fisik"),rs.getString("pasangan_melecehkan_secara_imosional"),
                        rs.getString("skor_pasangan_melecehkan_secara_imosional"),rs.getString("pasangan_melecehkan_secara_seksual"),rs.getString("skor_pasangan_melecehkan_secara_seksual"),
                        rs.getString("totalskor"),rs.getString("hasil_skrining")
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
        Tanggal.setDate(new Date());
        PertanyaanAwal1.setSelectedIndex(0);
        NilaiPertanyaanAwal1.setText("1");
        PertanyaanAwal2.setSelectedIndex(0);
        NilaiPertanyaanAwal2.setText("1");
        PertanyaanLanjutan1.setSelectedIndex(0);
        NilaiPertanyaanLanjutan1.setText("1");
        PertanyaanLanjutan2.setSelectedIndex(0);
        NilaiPertanyaanLanjutan2.setText("1");
        PertanyaanLanjutan3.setSelectedIndex(0);
        NilaiPertanyaanLanjutan3.setText("1");
        PertanyaanLanjutan4.setSelectedIndex(0);
        NilaiPertanyaanLanjutan4.setText("1");
        PertanyaanLanjutan5.setSelectedIndex(0);
        NilaiPertanyaanLanjutan5.setText("1");
        PertanyaanLanjutan6.setSelectedIndex(0);
        NilaiPertanyaanLanjutan6.setText("1");
        TotalHasil.setText("8");
        HasilSkrining.setText("Pasien Tidak Terindikasi Mengalami Kekerasan");
        PertanyaanAwal1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            PertanyaanAwal1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiPertanyaanAwal1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            PertanyaanAwal2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiPertanyaanAwal2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PertanyaanLanjutan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiPertanyaanLanjutan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            PertanyaanLanjutan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiPertanyaanLanjutan2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            PertanyaanLanjutan3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiPertanyaanLanjutan3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PertanyaanLanjutan4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiPertanyaanLanjutan4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PertanyaanLanjutan5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiPertanyaanLanjutan5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            PertanyaanLanjutan6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiPertanyaanLanjutan6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            HasilSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>598){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,426));
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
        BtnSimpan.setEnabled(akses.getskrining_kekerasan_pada_perempuan());
        BtnHapus.setEnabled(akses.getskrining_kekerasan_pada_perempuan());
        BtnEdit.setEnabled(akses.getskrining_kekerasan_pada_perempuan());
        BtnPrint.setEnabled(akses.getskrining_kekerasan_pada_perempuan()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(Sequel.CariPetugas(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }  
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("skrining_kekerasan_pada_perempuan","no_rawat=?","no_rawat=?,tanggal=?,menggambarkan_hubungan=?,skor_menggambarkan_hubungan=?,berdebat_dengan_pasangan=?,skor_berdebat_dengan_pasangan=?,pertengkaran_membuat_sedih=?,"+
                "skor_pertengkaran_membuat_sedih=?,pertengkaran_menghasilkan_pukulan=?,skor_pertengkaran_menghasilkan_pukulan=?,pernah_merasa_takut_dengan_pasangan=?,skor_pernah_merasa_takut_dengan_pasangan=?,pasangan_melecehkan_secara_fisik=?,"+
                "skor_pasangan_melecehkan_secara_fisik=?,pasangan_melecehkan_secara_imosional=?,skor_pasangan_melecehkan_secara_imosional=?,pasangan_melecehkan_secara_seksual=?,skor_pasangan_melecehkan_secara_seksual=?,totalskor=?,hasil_skrining=?,"+
                "nip=?",22,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                PertanyaanAwal1.getSelectedItem().toString(),NilaiPertanyaanAwal1.getText(),PertanyaanAwal2.getSelectedItem().toString(),NilaiPertanyaanAwal2.getText(), 
                PertanyaanLanjutan1.getSelectedItem().toString(),NilaiPertanyaanLanjutan1.getText(),PertanyaanLanjutan2.getSelectedItem().toString(),NilaiPertanyaanLanjutan2.getText(), 
                PertanyaanLanjutan3.getSelectedItem().toString(),NilaiPertanyaanLanjutan3.getText(),PertanyaanLanjutan4.getSelectedItem().toString(),NilaiPertanyaanLanjutan4.getText(), 
                PertanyaanLanjutan5.getSelectedItem().toString(),NilaiPertanyaanLanjutan5.getText(),PertanyaanLanjutan6.getSelectedItem().toString(),NilaiPertanyaanLanjutan6.getText(), 
                TotalHasil.getText(),HasilSkrining.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(PertanyaanAwal1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiPertanyaanAwal1.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(PertanyaanAwal2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiPertanyaanAwal2.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(PertanyaanLanjutan1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiPertanyaanLanjutan1.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(PertanyaanLanjutan2.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiPertanyaanLanjutan2.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(PertanyaanLanjutan3.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiPertanyaanLanjutan3.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(PertanyaanLanjutan4.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiPertanyaanLanjutan4.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(PertanyaanLanjutan5.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiPertanyaanLanjutan5.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(PertanyaanLanjutan6.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiPertanyaanLanjutan6.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(TotalHasil.getText(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(HasilSkrining.getText(),tbObat.getSelectedRow(),25);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_kekerasan_pada_perempuan where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotal() {
        try {
            TotalHasil.setText(""+(Integer.parseInt(NilaiPertanyaanAwal1.getText())+Integer.parseInt(NilaiPertanyaanAwal2.getText())+Integer.parseInt(NilaiPertanyaanLanjutan1.getText())+Integer.parseInt(NilaiPertanyaanLanjutan2.getText())+Integer.parseInt(NilaiPertanyaanLanjutan3.getText())+Integer.parseInt(NilaiPertanyaanLanjutan4.getText())+Integer.parseInt(NilaiPertanyaanLanjutan5.getText())+Integer.parseInt(NilaiPertanyaanLanjutan6.getText())));
            if(Integer.parseInt(TotalHasil.getText())>12){
                HasilSkrining.setText("Pasien Terindikasi Mengalami Kekerasan");
            }else{
                HasilSkrining.setText("Pasien Tidak Terindikasi Mengalami Kekerasan");
            }
        } catch (Exception e) {
            HasilSkrining.setText("Pasien Tidak Terindikasi Mengalami Kekerasan");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_kekerasan_pada_perempuan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",21,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            PertanyaanAwal1.getSelectedItem().toString(),NilaiPertanyaanAwal1.getText(),PertanyaanAwal2.getSelectedItem().toString(),NilaiPertanyaanAwal2.getText(), 
            PertanyaanLanjutan1.getSelectedItem().toString(),NilaiPertanyaanLanjutan1.getText(),PertanyaanLanjutan2.getSelectedItem().toString(),NilaiPertanyaanLanjutan2.getText(), 
            PertanyaanLanjutan3.getSelectedItem().toString(),NilaiPertanyaanLanjutan3.getText(),PertanyaanLanjutan4.getSelectedItem().toString(),NilaiPertanyaanLanjutan4.getText(), 
            PertanyaanLanjutan5.getSelectedItem().toString(),NilaiPertanyaanLanjutan5.getText(),PertanyaanLanjutan6.getSelectedItem().toString(),NilaiPertanyaanLanjutan6.getText(), 
            TotalHasil.getText(),HasilSkrining.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Umur.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                PertanyaanAwal1.getSelectedItem().toString(),NilaiPertanyaanAwal1.getText(),PertanyaanAwal2.getSelectedItem().toString(),NilaiPertanyaanAwal2.getText(),PertanyaanLanjutan1.getSelectedItem().toString(),NilaiPertanyaanLanjutan1.getText(),
                PertanyaanLanjutan2.getSelectedItem().toString(),NilaiPertanyaanLanjutan2.getText(),PertanyaanLanjutan3.getSelectedItem().toString(),NilaiPertanyaanLanjutan3.getText(),PertanyaanLanjutan4.getSelectedItem().toString(),NilaiPertanyaanLanjutan4.getText(), 
                PertanyaanLanjutan5.getSelectedItem().toString(),NilaiPertanyaanLanjutan5.getText(),PertanyaanLanjutan6.getSelectedItem().toString(),NilaiPertanyaanLanjutan6.getText(),TotalHasil.getText(),HasilSkrining.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

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
import java.io.IOException;
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
public final class RMSkriningAdiksiNikotin extends javax.swing.JDialog {
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
    //private DlgCariSkalaMotivasi motivasi=new DlgCariSkalaMotivasi(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningAdiksiNikotin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Umur","Kode Petugas","Nama Petugas","Tanggal",
            "Banyak Rokok dalam 1 hari","Skor","Rokok Pertama Setelah Terjaga","Skor","Rokok Tidak direlakan di hentikan","Skor",
            "Kebiasan Merokok","Skor","Kesulitan Menahan Rokok","Skor","Merokok Saat Sakit","Skor",
            "Total Skor","Kesimpulan","Skala Motivasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
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
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(45);
            }else if(i==10){
                column.setPreferredWidth(109);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(125);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(140);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(250);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(190);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Kesimpulan.setDocument(new batasInput((byte)50).getKata(Kesimpulan));
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
        MnSkriningAdiksiNikotin = new javax.swing.JMenuItem();
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
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel99 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        HisapRokok = new widget.ComboBox();
        jLabel92 = new widget.Label();
        skorHisap = new widget.TextBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        CepatMerokok = new widget.ComboBox();
        jLabel69 = new widget.Label();
        skorCepatRokok = new widget.TextBox();
        jLabel96 = new widget.Label();
        jLabel95 = new widget.Label();
        RelakanRokok = new widget.ComboBox();
        jLabel97 = new widget.Label();
        skorRelakanRokok = new widget.TextBox();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        BanyakRokok = new widget.ComboBox();
        jLabel109 = new widget.Label();
        skorBanyakRokok = new widget.TextBox();
        jLabel111 = new widget.Label();
        jLabel110 = new widget.Label();
        jLabel113 = new widget.Label();
        Kesulitan = new widget.ComboBox();
        jLabel112 = new widget.Label();
        skorKesulitan = new widget.TextBox();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel118 = new widget.Label();
        Sakit = new widget.ComboBox();
        jLabel116 = new widget.Label();
        skorSakit = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        Kesimpulan = new widget.TextBox();
        jLabel73 = new widget.Label();
        TotalSkor = new widget.TextBox();
        jLabel150 = new widget.Label();
        jLabel152 = new widget.Label();
        SkalaMotivasi = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningAdiksiNikotin.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningAdiksiNikotin.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningAdiksiNikotin.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningAdiksiNikotin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningAdiksiNikotin.setText("Formulir Skrining Risiko Kanker Payudara");
        MnSkriningAdiksiNikotin.setName("MnSkriningAdiksiNikotin"); // NOI18N
        MnSkriningAdiksiNikotin.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningAdiksiNikotin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningAdiksiNikotinActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningAdiksiNikotin);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kuisioner Adiksi Nikotin Fagerstroom ( Skala Fagerstroom ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 849));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 450));
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
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 380));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 380));
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

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

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

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-11-2024" }));
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

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(11, 70, 290, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 90, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Berapa banyak rokok yang Anda hisap dalam satu hari ?");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(62, 90, 300, 23);

        HisapRokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1-10", "11-20", "21-30", ">=31" }));
        HisapRokok.setName("HisapRokok"); // NOI18N
        HisapRokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HisapRokokItemStateChanged(evt);
            }
        });
        HisapRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HisapRokokKeyPressed(evt);
            }
        });
        FormInput.add(HisapRokok);
        HisapRokok.setBounds(600, 90, 100, 23);

        jLabel92.setText("Skor :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(700, 90, 40, 23);

        skorHisap.setEditable(false);
        skorHisap.setText("0");
        skorHisap.setFocusTraversalPolicyProvider(true);
        skorHisap.setName("skorHisap"); // NOI18N
        FormInput.add(skorHisap);
        skorHisap.setBounds(744, 90, 45, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("2.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 120, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Seberapa cepat Anda menyalakan rokok pertama anda setelah terjaga ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(62, 120, 370, 23);

        CepatMerokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam 5 Menit", "6 Hingga 30 Menit ", "31 Hingga 60 Menit", "Setelah 60 Menit" }));
        CepatMerokok.setName("CepatMerokok"); // NOI18N
        CepatMerokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CepatMerokokItemStateChanged(evt);
            }
        });
        CepatMerokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CepatMerokokKeyPressed(evt);
            }
        });
        FormInput.add(CepatMerokok);
        CepatMerokok.setBounds(555, 120, 145, 23);

        jLabel69.setText("Skor :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(700, 120, 40, 23);

        skorCepatRokok.setEditable(false);
        skorCepatRokok.setText("0");
        skorCepatRokok.setFocusTraversalPolicyProvider(true);
        skorCepatRokok.setName("skorCepatRokok"); // NOI18N
        FormInput.add(skorCepatRokok);
        skorCepatRokok.setBounds(744, 120, 45, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("3.");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(44, 150, 20, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Rokok mana yang paling Anda tidak relakan untuk di hentikan ?");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(62, 150, 370, 23);

        RelakanRokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lainnya", "Rokok Pertama Pada Pagi Hari" }));
        RelakanRokok.setName("RelakanRokok"); // NOI18N
        RelakanRokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RelakanRokokItemStateChanged(evt);
            }
        });
        RelakanRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RelakanRokokKeyPressed(evt);
            }
        });
        FormInput.add(RelakanRokok);
        RelakanRokok.setBounds(500, 150, 200, 23);

        jLabel97.setText("Skor :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(700, 150, 40, 23);

        skorRelakanRokok.setEditable(false);
        skorRelakanRokok.setText("0");
        skorRelakanRokok.setFocusTraversalPolicyProvider(true);
        skorRelakanRokok.setName("skorRelakanRokok"); // NOI18N
        FormInput.add(skorRelakanRokok);
        skorRelakanRokok.setBounds(744, 150, 45, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("4.");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(44, 180, 20, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Apakah Anda merokok lebih banyak dalam jam pertama hari anda dari pada sisa hari Anda ?");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(62, 180, 470, 23);

        BanyakRokok.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BanyakRokok.setName("BanyakRokok"); // NOI18N
        BanyakRokok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BanyakRokokItemStateChanged(evt);
            }
        });
        BanyakRokok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BanyakRokokKeyPressed(evt);
            }
        });
        FormInput.add(BanyakRokok);
        BanyakRokok.setBounds(610, 180, 90, 23);

        jLabel109.setText("Skor :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(700, 180, 40, 23);

        skorBanyakRokok.setEditable(false);
        skorBanyakRokok.setText("0");
        skorBanyakRokok.setFocusTraversalPolicyProvider(true);
        skorBanyakRokok.setName("skorBanyakRokok"); // NOI18N
        FormInput.add(skorBanyakRokok);
        skorBanyakRokok.setBounds(744, 180, 45, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("5.");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(44, 210, 20, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Apakah Anda kesulitan menahan rasa ingin merokok di tempat yang dilarang seperti bangunan umum,");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(62, 205, 550, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("pesawat terbang, atau di tempat kerja ?");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(62, 217, 530, 23);

        Kesulitan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Kesulitan.setName("Kesulitan"); // NOI18N
        Kesulitan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KesulitanItemStateChanged(evt);
            }
        });
        Kesulitan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesulitanKeyPressed(evt);
            }
        });
        FormInput.add(Kesulitan);
        Kesulitan.setBounds(610, 210, 90, 23);

        jLabel112.setText("Skor :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(700, 210, 40, 23);

        skorKesulitan.setEditable(false);
        skorKesulitan.setText("0");
        skorKesulitan.setFocusTraversalPolicyProvider(true);
        skorKesulitan.setName("skorKesulitan"); // NOI18N
        FormInput.add(skorKesulitan);
        skorKesulitan.setBounds(744, 210, 45, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("6.");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(44, 240, 20, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Apakah Anda masih merokok ketika Anda sakit berat, sehingga Anda harus berbaring");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(62, 235, 440, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("dalam sebagian besar waktu Anda ?");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(62, 247, 530, 23);

        Sakit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Sakit.setName("Sakit"); // NOI18N
        Sakit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SakitItemStateChanged(evt);
            }
        });
        Sakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SakitKeyPressed(evt);
            }
        });
        FormInput.add(Sakit);
        Sakit.setBounds(610, 240, 90, 23);

        jLabel116.setText("Skor :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(700, 240, 40, 23);

        skorSakit.setEditable(false);
        skorSakit.setText("0");
        skorSakit.setFocusTraversalPolicyProvider(true);
        skorSakit.setName("skorSakit"); // NOI18N
        FormInput.add(skorSakit);
        skorSakit.setBounds(744, 240, 45, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Kesimpulan");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(44, 290, 80, 23);

        jLabel149.setText(":");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(-5, 290, 130, 23);

        Kesimpulan.setEditable(false);
        Kesimpulan.setFocusTraversalPolicyProvider(true);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(Kesimpulan);
        Kesimpulan.setBounds(130, 290, 540, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(670, 290, 70, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setText("0");
        TotalSkor.setFocusTraversalPolicyProvider(true);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(744, 290, 45, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("Skala Motivasi");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(44, 340, 90, 23);

        jLabel152.setText(":");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(-5, 340, 130, 23);

        SkalaMotivasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Saya SUDAH memutuskan TIDAK akan berhenti merokok seumur hidup saya", "2. Saya TIDAK PERNAH berpikir untuk berhenti merokok. Saya TIDAK PUNYA rencana untuk berhenti", "3. Saya PERNAH berpikir untuk berhentl merokok, tetapi Saya TIDAK PUNYA rencana", "4. TERKADANG saya berpikir untuk berhenti merokok, tetapi saya tidak punya rencana", "5. Saya SERING berpikir untuk berhentl merokok, tetapi saya tidak punya rencana", "6. Saya BERENCANA untuk berhenti merokok dalam 6 bulan ke depan", "7. Saya berencana untuk berhenti merokok dalam 30 hari ke depan", "8. Saya masih merokok, tetapi saya mau berubah. Saya siap untuk berhenti merokok", "9. Saya sudah berhenti merokok, tetapisaya khawatir akan merokok kembali, saya butuh lingkungan tanpa asap rokok", "10. Saya sudah berhenti merokok", " " }));
        SkalaMotivasi.setSelectedIndex(7);
        SkalaMotivasi.setName("SkalaMotivasi"); // NOI18N
        SkalaMotivasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SkalaMotivasiItemStateChanged(evt);
            }
        });
        SkalaMotivasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SkalaMotivasiKeyPressed(evt);
            }
        });
        FormInput.add(SkalaMotivasi);
        SkalaMotivasi.setBounds(130, 340, 659, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 270, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. INTERPRETASI");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 270, 200, 23);

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
            Valid.pindah(evt,SkalaMotivasi,BtnBatal);
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pemeriksaan SADANIS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindak Lanjut Sadanis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                      "<table width='6000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                try (BufferedWriter bg = new BufferedWriter(new FileWriter(g))) {
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
                }

                File f = new File("DataSkriningRisikoKankerPayudara.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='6000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING RISIKO KANKER PAYUDARA<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

            }catch(IOException e){
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
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                BtnCariActionPerformed(null);
                break;
            case KeyEvent.VK_PAGE_DOWN:
                BtnCari.requestFocus();
                break;
            case KeyEvent.VK_PAGE_UP:
                BtnKeluar.requestFocus();
                break;
            default:
                break;
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
        Valid.pindah(evt,TCari,HisapRokok);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningAdiksiNikotinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningAdiksiNikotinActionPerformed
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
            Valid.MyReportqry("rptFormulirSkriningRisikoKankerPayudara.jasper","report","::[ Formulir Skrining Risiko Kanker Payudara ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningAdiksiNikotinActionPerformed

    private void HisapRokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HisapRokokItemStateChanged
        if ((!HisapRokok.getSelectedItem().toString().equals(""))) {
            switch (HisapRokok.getSelectedItem().toString()) {
                case "1-10":
                    skorHisap.setText("0");
                    break;
                case "11-20":
                    skorHisap.setText("1");
                    break;
                case "21-30":
                    skorHisap.setText("2");
                    break;
                case "31 atau Lebih":
                    skorHisap.setText("3");
                    break;
                default:
                    break;
            }
        }
        isTotal();
    }//GEN-LAST:event_HisapRokokItemStateChanged

    private void HisapRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HisapRokokKeyPressed
        Valid.pindah(evt,TCari,CepatMerokok);
    }//GEN-LAST:event_HisapRokokKeyPressed

    private void CepatMerokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CepatMerokokItemStateChanged
        if ((!CepatMerokok.getSelectedItem().toString().equals(""))) {
            switch (CepatMerokok.getSelectedItem().toString()) {
                case "Dalam 5 menit":
                    skorCepatRokok.setText("3");
                    break;
                case "6 hingga 30 menit ":
                    skorCepatRokok.setText("2");
                    break;
                case "31 hingga 60 menit":
                    skorCepatRokok.setText("1");
                    break;
                case "Setelah 60 menit":
                    skorCepatRokok.setText("0");
                    break;
                default:
                    break;
            }
        }
        isTotal();
    }//GEN-LAST:event_CepatMerokokItemStateChanged

    private void CepatMerokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CepatMerokokKeyPressed
        Valid.pindah(evt,HisapRokok,RelakanRokok);
    }//GEN-LAST:event_CepatMerokokKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        //Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void RelakanRokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RelakanRokokItemStateChanged
        if(RelakanRokok.getSelectedIndex()==1){
            skorRelakanRokok.setText("1");
        }else{
            skorRelakanRokok.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_RelakanRokokItemStateChanged

    private void RelakanRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RelakanRokokKeyPressed
        Valid.pindah(evt,CepatMerokok,BanyakRokok);
    }//GEN-LAST:event_RelakanRokokKeyPressed

    private void BanyakRokokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BanyakRokokItemStateChanged
        if(BanyakRokok.getSelectedIndex()==1){
            skorBanyakRokok.setText("1");
        }else{
            skorBanyakRokok.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_BanyakRokokItemStateChanged

    private void BanyakRokokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BanyakRokokKeyPressed
        Valid.pindah(evt,RelakanRokok,Kesulitan);
    }//GEN-LAST:event_BanyakRokokKeyPressed

    private void KesulitanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KesulitanItemStateChanged
        if(Kesulitan.getSelectedIndex()==1){
            skorKesulitan.setText("1");
        }else{
            skorKesulitan.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KesulitanItemStateChanged

    private void KesulitanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesulitanKeyPressed
        Valid.pindah(evt,BanyakRokok,Sakit);
    }//GEN-LAST:event_KesulitanKeyPressed

    private void SakitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SakitItemStateChanged
        if(Sakit.getSelectedIndex()==1){
            skorSakit.setText("1");
        }else{
            skorSakit.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SakitItemStateChanged

    private void SakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SakitKeyPressed
        Valid.pindah(evt,Kesulitan,SkalaMotivasi);
    }//GEN-LAST:event_SakitKeyPressed

    private void SkalaMotivasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SkalaMotivasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaMotivasiKeyPressed

    private void SkalaMotivasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SkalaMotivasiItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SkalaMotivasiItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningAdiksiNikotin dialog = new RMSkriningAdiksiNikotin(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BanyakRokok;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CepatMerokok;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HisapRokok;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kesimpulan;
    private widget.ComboBox Kesulitan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningAdiksiNikotin;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox RelakanRokok;
    private widget.ComboBox Sakit;
    private widget.ScrollPane Scroll;
    private widget.ComboBox SkalaMotivasi;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalSkor;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel101;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel116;
    private widget.Label jLabel118;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel152;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel73;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel8;
    private widget.Label jLabel92;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.TextBox skorBanyakRokok;
    private widget.TextBox skorCepatRokok;
    private widget.TextBox skorHisap;
    private widget.TextBox skorKesulitan;
    private widget.TextBox skorRelakanRokok;
    private widget.TextBox skorSakit;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where skrining_risiko_kanker_payudara.tanggal between ? and ? order by skrining_risiko_kanker_payudara.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where skrining_risiko_kanker_payudara.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_risiko_kanker_payudara.nip like ? or petugas.nama like ?) "+
                    "order by skrining_risiko_kanker_payudara.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("faktor_risiko_awal1"),rs.getString("nilai_risiko_awal1"),rs.getString("faktor_risiko_awal2"),
                        rs.getString("nilai_risiko_awal2"),rs.getString("faktor_risiko_awal3"),rs.getString("nilai_risiko_awal3"),rs.getString("faktor_risiko_awal4"),rs.getString("nilai_risiko_awal4"),
                        rs.getString("faktor_risiko_awal5"),rs.getString("nilai_risiko_awal5"),rs.getString("faktor_risiko_awal6"),rs.getString("nilai_risiko_awal6"),rs.getString("faktor_risiko_awal7"),
                        rs.getString("nilai_risiko_awal7"),rs.getString("faktor_risiko_awal8"),rs.getString("nilai_risiko_awal8"),rs.getString("faktor_risiko_awal9"),rs.getString("nilai_risiko_awal9"),
                        rs.getString("faktor_risiko_awal10"),rs.getString("nilai_risiko_awal10"),rs.getString("faktor_risiko_awal11"),rs.getString("nilai_risiko_awal11"),rs.getString("faktor_risiko_awal12"),
                        rs.getString("nilai_risiko_awal12"),rs.getString("faktor_risiko_awal13"),rs.getString("nilai_risiko_awal13"),rs.getString("faktor_risiko_awal14"),rs.getString("nilai_risiko_awal14"),
                        rs.getString("faktor_risiko_tinggi1"),rs.getString("nilai_risiko_tinggi1"),rs.getString("faktor_risiko_tinggi2"),rs.getString("nilai_risiko_tinggi2"),rs.getString("faktor_risiko_tinggi3"),
                        rs.getString("nilai_risiko_tinggi3"),rs.getString("faktor_risiko_tinggi4"),rs.getString("nilai_risiko_tinggi4"),rs.getString("faktor_risiko_tinggi5"),rs.getString("nilai_risiko_tinggi5"),
                        rs.getString("faktor_risiko_tinggi6"),rs.getString("nilai_risiko_tinggi6"),rs.getString("faktor_risiko_tinggi7"),rs.getString("nilai_risiko_tinggi7"),rs.getString("faktor_risiko_tinggi8"),
                        rs.getString("nilai_risiko_tinggi8"),rs.getString("faktor_risiko_tinggi9"),rs.getString("nilai_risiko_tinggi9"),rs.getString("faktor_risiko_tinggi10"),rs.getString("nilai_risiko_tinggi10"),
                        rs.getString("faktor_risiko_tinggi11"),rs.getString("nilai_risiko_tinggi11"),rs.getString("faktor_risiko_tinggi12"),rs.getString("nilai_risiko_tinggi12"),rs.getString("faktor_risiko_tinggi13"),
                        rs.getString("nilai_risiko_tinggi13"),rs.getString("faktor_kecurigaan_ganas1"),rs.getString("nilai_kecurigaan_ganas1"),rs.getString("faktor_kecurigaan_ganas2"),rs.getString("nilai_kecurigaan_ganas2"),
                        rs.getString("faktor_kecurigaan_ganas3"),rs.getString("nilai_kecurigaan_ganas3"),rs.getString("faktor_kecurigaan_ganas4"),rs.getString("nilai_kecurigaan_ganas4"),rs.getString("faktor_kecurigaan_ganas5"),
                        rs.getString("nilai_kecurigaan_ganas5"),rs.getString("faktor_kecurigaan_ganas6"),rs.getString("nilai_kecurigaan_ganas6"),rs.getString("faktor_kecurigaan_ganas7"),rs.getString("nilai_kecurigaan_ganas7"),
                        rs.getString("faktor_kecurigaan_ganas8"),rs.getString("nilai_kecurigaan_ganas8"),rs.getString("total_skor"),rs.getString("hasil_sadanis"),rs.getString("tindak_lanjut_sadanis"),
                        rs.getString("hasil_skrining"),rs.getString("keterangan")
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
        HisapRokok.setSelectedIndex(0);
        skorHisap.setText("0");
        CepatMerokok.setSelectedIndex(0);
        skorCepatRokok.setText("0");
        RelakanRokok.setSelectedIndex(0);
        skorRelakanRokok.setText("0");
        BanyakRokok.setSelectedIndex(0);
        skorBanyakRokok.setText("0");
        Kesulitan.setSelectedIndex(0);
        skorKesulitan.setText("0");
        Sakit.setSelectedIndex(0);
        skorSakit.setText("0");
        TotalSkor.setText("0");
        Kesimpulan.setText("Edukasi Bahaya Rokok");
        SkalaMotivasi.setSelectedIndex(0);
        HisapRokok.requestFocus();
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
            HisapRokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            skorHisap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            CepatMerokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            skorCepatRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RelakanRokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            skorRelakanRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            BanyakRokok.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            skorBanyakRokok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Kesulitan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            skorKesulitan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Sakit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            skorSakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SkalaMotivasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
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
            if(internalFrame1.getHeight()>608){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,410));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        BtnSimpan.setEnabled(akses.getskrining_adiksi_nikotin());
        BtnHapus.setEnabled(akses.getskrining_adiksi_nikotin());
        BtnEdit.setEnabled(akses.getskrining_adiksi_nikotin());
        BtnPrint.setEnabled(akses.getskrining_adiksi_nikotin()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
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
        if(Sequel.mengedittf("skrining_risiko_kanker_payudara","no_rawat=?","no_rawat=?,tanggal=?,faktor_risiko_awal1=?,nilai_risiko_awal1=?,faktor_risiko_awal2=?,nilai_risiko_awal2=?,faktor_risiko_awal3=?,"+
                "nilai_risiko_awal3=?,faktor_risiko_awal4=?,nilai_risiko_awal4=?,faktor_risiko_awal5=?,nilai_risiko_awal5=?,faktor_risiko_awal6=?,nilai_risiko_awal6=?,faktor_risiko_awal7=?,nilai_risiko_awal7=?,"+
                "faktor_risiko_awal8=?,nilai_risiko_awal8=?,faktor_risiko_awal9=?,nilai_risiko_awal9=?,faktor_risiko_awal10=?,nilai_risiko_awal10=?,faktor_risiko_awal11=?,nilai_risiko_awal11=?,faktor_risiko_awal12=?,"+
                "nilai_risiko_awal12=?,faktor_risiko_awal13=?,nilai_risiko_awal13=?,faktor_risiko_awal14=?,nilai_risiko_awal14=?,faktor_risiko_tinggi1=?,nilai_risiko_tinggi1=?,faktor_risiko_tinggi2=?,nilai_risiko_tinggi2=?,"+
                "faktor_risiko_tinggi3=?,nilai_risiko_tinggi3=?,faktor_risiko_tinggi4=?,nilai_risiko_tinggi4=?,faktor_risiko_tinggi5=?,nilai_risiko_tinggi5=?,faktor_risiko_tinggi6=?,nilai_risiko_tinggi6=?,"+
                "faktor_risiko_tinggi7=?,nilai_risiko_tinggi7=?,faktor_risiko_tinggi8=?,nilai_risiko_tinggi8=?,faktor_risiko_tinggi9=?,nilai_risiko_tinggi9=?,faktor_risiko_tinggi10=?,nilai_risiko_tinggi10=?,"+
                "faktor_risiko_tinggi11=?,nilai_risiko_tinggi11=?,faktor_risiko_tinggi12=?,nilai_risiko_tinggi12=?,faktor_risiko_tinggi13=?,nilai_risiko_tinggi13=?,faktor_kecurigaan_ganas1=?,nilai_kecurigaan_ganas1=?,"+
                "faktor_kecurigaan_ganas2=?,nilai_kecurigaan_ganas2=?,faktor_kecurigaan_ganas3=?,nilai_kecurigaan_ganas3=?,faktor_kecurigaan_ganas4=?,nilai_kecurigaan_ganas4=?,faktor_kecurigaan_ganas5=?,nilai_kecurigaan_ganas5=?,"+
                "faktor_kecurigaan_ganas6=?,nilai_kecurigaan_ganas6=?,faktor_kecurigaan_ganas7=?,nilai_kecurigaan_ganas7=?,faktor_kecurigaan_ganas8=?,nilai_kecurigaan_ganas8=?,total_skor=?,hasil_sadanis=?,tindak_lanjut_sadanis=?,"+
                "hasil_skrining=?,keterangan=?,nip=?",79,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                HisapRokok.getSelectedItem().toString(),skorHisap.getText(),CepatMerokok.getSelectedItem().toString(),skorCepatRokok.getText(),RelakanRokok.getSelectedItem().toString(),skorRelakanRokok.getText(),
                BanyakRokok.getSelectedItem().toString(),skorBanyakRokok.getText(),
                Kesulitan.getSelectedItem().toString(),skorKesulitan.getText(),Sakit.getSelectedItem().toString(),skorSakit.getText(), 
                TotalSkor.getText(),Kesimpulan.getText(),SkalaMotivasi.getSelectedItem().toString(),
                KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(HisapRokok.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(skorHisap.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(CepatMerokok.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(skorCepatRokok.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(RelakanRokok.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(skorRelakanRokok.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(BanyakRokok.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(skorBanyakRokok.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Kesulitan.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(skorKesulitan.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(Sakit.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(skorSakit.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(SkalaMotivasi.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_risiko_kanker_payudara where no_rawat=?",1,new String[]{
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
            TotalSkor.setText(""+(
                    Integer.parseInt(skorHisap.getText())+Integer.parseInt(skorCepatRokok.getText())+
                    Integer.parseInt(skorRelakanRokok.getText())+
                    Integer.parseInt(skorBanyakRokok.getText())+
                    Integer.parseInt(skorKesulitan.getText())+Integer.parseInt(skorSakit.getText())
            ));
            if(Integer.parseInt(TotalSkor.getText())>7){
                Kesimpulan.setText("Ketergantungan Berat");
            }else if(Integer.parseInt(TotalSkor.getText())>4){
                Kesimpulan.setText("Ketergantungan Sedang");
            }else{
                Kesimpulan.setText("Ketergantungan Rendah");
            }
            

        } catch (Exception e) {
            Kesimpulan.setText("Ketergantungan Rendah");
        }
    }
      

    private void simpan() {
        if(Sequel.menyimpantf("skrining_risiko_kanker_payudara","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",78,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            HisapRokok.getSelectedItem().toString(),skorHisap.getText(),CepatMerokok.getSelectedItem().toString(),skorCepatRokok.getText(),RelakanRokok.getSelectedItem().toString(),skorRelakanRokok.getText(),
            BanyakRokok.getSelectedItem().toString(),skorBanyakRokok.getText(),
            Kesulitan.getSelectedItem().toString(),skorKesulitan.getText(),Sakit.getSelectedItem().toString(),skorSakit.getText(),
            TotalSkor.getText(),Kesimpulan.getText(),SkalaMotivasi.getSelectedItem().toString(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Umur.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                HisapRokok.getSelectedItem().toString(),skorHisap.getText(),CepatMerokok.getSelectedItem().toString(),skorCepatRokok.getText(),RelakanRokok.getSelectedItem().toString(),skorRelakanRokok.getText(),
                BanyakRokok.getSelectedItem().toString(),skorBanyakRokok.getText(),
                Kesulitan.getSelectedItem().toString(),skorKesulitan.getText(),Sakit.getSelectedItem().toString(),skorSakit.getText(), 
                TotalSkor.getText(),Kesimpulan.getText(),SkalaMotivasi.getSelectedItem().toString()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

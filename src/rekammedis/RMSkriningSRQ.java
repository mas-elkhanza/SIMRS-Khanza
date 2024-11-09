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
public final class RMSkriningSRQ extends javax.swing.JDialog {
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
    public RMSkriningSRQ(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Petugas","Nama Petugas","Tanggal",
            "SRQ1","N.SRQ1","SRQ2","N.SRQ2","SRQ3","N.SRQ3","SRQ4","N.SRQ4","SRQ5","N.SRQ5",
            "SRQ6","N.SRQ6","SRQ7","N.SRQ7","SRQ8","N.SRQ8","SRQ9","N.SRQ9","SRQ10","N.SRQ10",
            "SRQ11","N.SRQ11","SRQ12","N.SRQ12","SRQ13","N.SRQ13","SRQ14","N.SRQ14","SRQ15","N.SRQ15",
            "SRQ16","N.SRQ16","SRQ17","N.SRQ17","SRQ18","N.SRQ18","SRQ19","N.SRQ19","SRQ20","N.SRQ20",
            "N.Total","Kesimpulan"
            
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
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(48);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(48);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(48);
            }else if(i==14){
                column.setPreferredWidth(65);
            }else if(i==15){
                column.setPreferredWidth(48);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(48);
            }else if(i==18){
                column.setPreferredWidth(65);
            }else if(i==19){
                column.setPreferredWidth(48);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(48);
            }else if(i==22){
                column.setPreferredWidth(65);
            }else if(i==23){
                column.setPreferredWidth(48);
            }else if(i==24){
                column.setPreferredWidth(65);
            }else if(i==25){
                column.setPreferredWidth(48);
            }else if(i==26){
                column.setPreferredWidth(65);
            }else if(i==27){
                column.setPreferredWidth(52);
            }else if(i==28){
                column.setPreferredWidth(65);
            }else if(i==29){
                column.setPreferredWidth(52);
            }else if(i==30){
                column.setPreferredWidth(65);
            }else if(i==31){
                column.setPreferredWidth(52);
            }else if(i==32){
                column.setPreferredWidth(65);
            }else if(i==33){
                column.setPreferredWidth(52);
            }else if(i==34){
                column.setPreferredWidth(65);
            }else if(i==35){
                column.setPreferredWidth(52);
            }else if(i==36){
                column.setPreferredWidth(65);
            }else if(i==37){
                column.setPreferredWidth(52);
            }else if(i==38){
                column.setPreferredWidth(65);
            }else if(i==39){
                column.setPreferredWidth(52);
            }else if(i==40){
                column.setPreferredWidth(65);
            }else if(i==41){
                column.setPreferredWidth(52);
            }else if(i==42){
                column.setPreferredWidth(65);
            }else if(i==43){
                column.setPreferredWidth(52);
            }else if(i==44){
                column.setPreferredWidth(65);
            }else if(i==45){
                column.setPreferredWidth(52);
            }else if(i==46){
                column.setPreferredWidth(65);
            }else if(i==47){
                column.setPreferredWidth(52);
            }else if(i==48){
                column.setPreferredWidth(52);
            }else if(i==49){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Kesimpulan.setDocument(new batasInput((int)100).getKata(Kesimpulan));
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
        MnSkriningObesitas = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
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
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel102 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        jLabel106 = new widget.Label();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        jLabel109 = new widget.Label();
        SRQ1 = new widget.ComboBox();
        SRQ2 = new widget.ComboBox();
        SRQ3 = new widget.ComboBox();
        SRQ4 = new widget.ComboBox();
        SRQ5 = new widget.ComboBox();
        SRQ6 = new widget.ComboBox();
        SRQ7 = new widget.ComboBox();
        SRQ8 = new widget.ComboBox();
        SRQ9 = new widget.ComboBox();
        SRQ10 = new widget.ComboBox();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        jLabel129 = new widget.Label();
        SRQ11 = new widget.ComboBox();
        SRQ12 = new widget.ComboBox();
        SRQ13 = new widget.ComboBox();
        SRQ14 = new widget.ComboBox();
        SRQ15 = new widget.ComboBox();
        SRQ16 = new widget.ComboBox();
        SRQ17 = new widget.ComboBox();
        SRQ18 = new widget.ComboBox();
        SRQ19 = new widget.ComboBox();
        SRQ20 = new widget.ComboBox();
        jLabel130 = new widget.Label();
        NilaiSRQ1 = new widget.TextBox();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        jLabel144 = new widget.Label();
        jLabel145 = new widget.Label();
        jLabel146 = new widget.Label();
        jLabel147 = new widget.Label();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        jLabel150 = new widget.Label();
        jLabel151 = new widget.Label();
        jLabel152 = new widget.Label();
        jLabel153 = new widget.Label();
        jLabel154 = new widget.Label();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        jLabel157 = new widget.Label();
        jLabel158 = new widget.Label();
        jLabel159 = new widget.Label();
        NilaiSRQ2 = new widget.TextBox();
        NilaiSRQ3 = new widget.TextBox();
        NilaiSRQ4 = new widget.TextBox();
        NilaiSRQ5 = new widget.TextBox();
        NilaiSRQ6 = new widget.TextBox();
        NilaiSRQ7 = new widget.TextBox();
        NilaiSRQ8 = new widget.TextBox();
        NilaiSRQ9 = new widget.TextBox();
        NilaiSRQ10 = new widget.TextBox();
        NilaiSRQ11 = new widget.TextBox();
        NilaiSRQ12 = new widget.TextBox();
        NilaiSRQ13 = new widget.TextBox();
        NilaiSRQ14 = new widget.TextBox();
        NilaiSRQ15 = new widget.TextBox();
        NilaiSRQ16 = new widget.TextBox();
        NilaiSRQ17 = new widget.TextBox();
        NilaiSRQ18 = new widget.TextBox();
        NilaiSRQ19 = new widget.TextBox();
        NilaiSRQ20 = new widget.TextBox();
        jLabel160 = new widget.Label();
        jLabel161 = new widget.Label();
        Kesimpulan = new widget.TextBox();
        jLabel162 = new widget.Label();
        TotalNilai = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        jLabel112 = new widget.Label();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        jLabel116 = new widget.Label();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel131 = new widget.Label();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel137 = new widget.Label();
        jLabel138 = new widget.Label();
        jLabel139 = new widget.Label();
        jLabel140 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel163 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningObesitas.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningObesitas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningObesitas.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningObesitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningObesitas.setText("Formulir Skrining Obesitas");
        MnSkriningObesitas.setName("MnSkriningObesitas"); // NOI18N
        MnSkriningObesitas.setPreferredSize(new java.awt.Dimension(200, 26));
        MnSkriningObesitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningObesitasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningObesitas);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(492, 659));
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Self Reporting Quisionere (SRQ) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-11-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-11-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 436));
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
        FormInput.setPreferredSize(new java.awt.Dimension(830, 773));
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
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-11-2024" }));
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
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
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

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(11, 70, 200, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("1.");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(44, 90, 25, 23);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("2.");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(44, 120, 25, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("3.");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(44, 150, 25, 23);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("4.");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(44, 180, 25, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("5.");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(44, 210, 25, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("6.");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(44, 240, 25, 23);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("7.");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(44, 270, 25, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("8.");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(44, 300, 25, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("9.");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(44, 330, 25, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("10.");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(44, 360, 25, 23);

        SRQ1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ1.setName("SRQ1"); // NOI18N
        SRQ1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ1ItemStateChanged(evt);
            }
        });
        SRQ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ1KeyPressed(evt);
            }
        });
        FormInput.add(SRQ1);
        SRQ1.setBounds(610, 90, 90, 23);

        SRQ2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ2.setName("SRQ2"); // NOI18N
        SRQ2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ2ItemStateChanged(evt);
            }
        });
        SRQ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ2KeyPressed(evt);
            }
        });
        FormInput.add(SRQ2);
        SRQ2.setBounds(610, 120, 90, 23);

        SRQ3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ3.setName("SRQ3"); // NOI18N
        SRQ3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ3ItemStateChanged(evt);
            }
        });
        SRQ3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ3KeyPressed(evt);
            }
        });
        FormInput.add(SRQ3);
        SRQ3.setBounds(610, 150, 90, 23);

        SRQ4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ4.setName("SRQ4"); // NOI18N
        SRQ4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ4ItemStateChanged(evt);
            }
        });
        SRQ4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ4KeyPressed(evt);
            }
        });
        FormInput.add(SRQ4);
        SRQ4.setBounds(610, 180, 90, 23);

        SRQ5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ5.setName("SRQ5"); // NOI18N
        SRQ5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ5ItemStateChanged(evt);
            }
        });
        SRQ5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ5KeyPressed(evt);
            }
        });
        FormInput.add(SRQ5);
        SRQ5.setBounds(610, 210, 90, 23);

        SRQ6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ6.setName("SRQ6"); // NOI18N
        SRQ6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ6ItemStateChanged(evt);
            }
        });
        SRQ6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ6KeyPressed(evt);
            }
        });
        FormInput.add(SRQ6);
        SRQ6.setBounds(610, 240, 90, 23);

        SRQ7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ7.setName("SRQ7"); // NOI18N
        SRQ7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ7ItemStateChanged(evt);
            }
        });
        SRQ7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ7KeyPressed(evt);
            }
        });
        FormInput.add(SRQ7);
        SRQ7.setBounds(610, 270, 90, 23);

        SRQ8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ8.setName("SRQ8"); // NOI18N
        SRQ8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ8ItemStateChanged(evt);
            }
        });
        SRQ8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ8KeyPressed(evt);
            }
        });
        FormInput.add(SRQ8);
        SRQ8.setBounds(610, 300, 90, 23);

        SRQ9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ9.setName("SRQ9"); // NOI18N
        SRQ9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ9ItemStateChanged(evt);
            }
        });
        SRQ9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ9KeyPressed(evt);
            }
        });
        FormInput.add(SRQ9);
        SRQ9.setBounds(610, 330, 90, 23);

        SRQ10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ10.setName("SRQ10"); // NOI18N
        SRQ10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ10ItemStateChanged(evt);
            }
        });
        SRQ10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ10KeyPressed(evt);
            }
        });
        FormInput.add(SRQ10);
        SRQ10.setBounds(610, 360, 90, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("11.");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(44, 390, 25, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("12.");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(44, 420, 25, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("13.");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(44, 450, 25, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("14.");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(44, 480, 25, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("15.");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(44, 510, 25, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("16.");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(44, 540, 25, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("17.");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(44, 570, 25, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("18.");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(44, 600, 25, 23);

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel128.setText("19.");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(44, 630, 25, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("20.");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(44, 660, 25, 23);

        SRQ11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ11.setName("SRQ11"); // NOI18N
        SRQ11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ11ItemStateChanged(evt);
            }
        });
        SRQ11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ11KeyPressed(evt);
            }
        });
        FormInput.add(SRQ11);
        SRQ11.setBounds(610, 390, 90, 23);

        SRQ12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ12.setName("SRQ12"); // NOI18N
        SRQ12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ12ItemStateChanged(evt);
            }
        });
        SRQ12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ12KeyPressed(evt);
            }
        });
        FormInput.add(SRQ12);
        SRQ12.setBounds(610, 420, 90, 23);

        SRQ13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ13.setName("SRQ13"); // NOI18N
        SRQ13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ13ItemStateChanged(evt);
            }
        });
        SRQ13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ13KeyPressed(evt);
            }
        });
        FormInput.add(SRQ13);
        SRQ13.setBounds(610, 450, 90, 23);

        SRQ14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ14.setName("SRQ14"); // NOI18N
        SRQ14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ14ItemStateChanged(evt);
            }
        });
        SRQ14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ14KeyPressed(evt);
            }
        });
        FormInput.add(SRQ14);
        SRQ14.setBounds(610, 480, 90, 23);

        SRQ15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ15.setName("SRQ15"); // NOI18N
        SRQ15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ15ItemStateChanged(evt);
            }
        });
        SRQ15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ15KeyPressed(evt);
            }
        });
        FormInput.add(SRQ15);
        SRQ15.setBounds(610, 510, 90, 23);

        SRQ16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ16.setName("SRQ16"); // NOI18N
        SRQ16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ16ItemStateChanged(evt);
            }
        });
        SRQ16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ16KeyPressed(evt);
            }
        });
        FormInput.add(SRQ16);
        SRQ16.setBounds(610, 540, 90, 23);

        SRQ17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ17.setName("SRQ17"); // NOI18N
        SRQ17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ17ItemStateChanged(evt);
            }
        });
        SRQ17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ17KeyPressed(evt);
            }
        });
        FormInput.add(SRQ17);
        SRQ17.setBounds(610, 570, 90, 23);

        SRQ18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ18.setName("SRQ18"); // NOI18N
        SRQ18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ18ItemStateChanged(evt);
            }
        });
        SRQ18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ18KeyPressed(evt);
            }
        });
        FormInput.add(SRQ18);
        SRQ18.setBounds(610, 600, 90, 23);

        SRQ19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ19.setName("SRQ19"); // NOI18N
        SRQ19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ19ItemStateChanged(evt);
            }
        });
        SRQ19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ19KeyPressed(evt);
            }
        });
        FormInput.add(SRQ19);
        SRQ19.setBounds(610, 630, 90, 23);

        SRQ20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SRQ20.setName("SRQ20"); // NOI18N
        SRQ20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SRQ20ItemStateChanged(evt);
            }
        });
        SRQ20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SRQ20KeyPressed(evt);
            }
        });
        FormInput.add(SRQ20);
        SRQ20.setBounds(610, 660, 90, 23);

        jLabel130.setText("Nilai :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(700, 90, 40, 23);

        NilaiSRQ1.setHighlighter(null);
        NilaiSRQ1.setName("NilaiSRQ1"); // NOI18N
        FormInput.add(NilaiSRQ1);
        NilaiSRQ1.setBounds(744, 90, 45, 23);

        jLabel141.setText("Nilai :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(700, 120, 40, 23);

        jLabel142.setText("Nilai :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(700, 150, 40, 23);

        jLabel143.setText("Nilai :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(700, 180, 40, 23);

        jLabel144.setText("Nilai :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(700, 210, 40, 23);

        jLabel145.setText("Nilai :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(700, 240, 40, 23);

        jLabel146.setText("Nilai :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(700, 270, 40, 23);

        jLabel147.setText("Nilai :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(700, 300, 40, 23);

        jLabel148.setText("Nilai :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(700, 330, 40, 23);

        jLabel149.setText("Nilai :");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(700, 360, 40, 23);

        jLabel150.setText("Nilai :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(700, 390, 40, 23);

        jLabel151.setText("Nilai :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(700, 420, 40, 23);

        jLabel152.setText("Nilai :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(700, 450, 40, 23);

        jLabel153.setText("Nilai :");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(700, 480, 40, 23);

        jLabel154.setText("Nilai :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(700, 510, 40, 23);

        jLabel155.setText("Nilai :");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(700, 540, 40, 23);

        jLabel156.setText("Nilai :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(700, 570, 40, 23);

        jLabel157.setText("Nilai :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(700, 600, 40, 23);

        jLabel158.setText("Nilai :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(700, 630, 40, 23);

        jLabel159.setText("Nilai :");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(700, 660, 40, 23);

        NilaiSRQ2.setHighlighter(null);
        NilaiSRQ2.setName("NilaiSRQ2"); // NOI18N
        FormInput.add(NilaiSRQ2);
        NilaiSRQ2.setBounds(744, 120, 45, 23);

        NilaiSRQ3.setHighlighter(null);
        NilaiSRQ3.setName("NilaiSRQ3"); // NOI18N
        FormInput.add(NilaiSRQ3);
        NilaiSRQ3.setBounds(744, 150, 45, 23);

        NilaiSRQ4.setHighlighter(null);
        NilaiSRQ4.setName("NilaiSRQ4"); // NOI18N
        FormInput.add(NilaiSRQ4);
        NilaiSRQ4.setBounds(744, 180, 45, 23);

        NilaiSRQ5.setHighlighter(null);
        NilaiSRQ5.setName("NilaiSRQ5"); // NOI18N
        FormInput.add(NilaiSRQ5);
        NilaiSRQ5.setBounds(744, 210, 45, 23);

        NilaiSRQ6.setHighlighter(null);
        NilaiSRQ6.setName("NilaiSRQ6"); // NOI18N
        FormInput.add(NilaiSRQ6);
        NilaiSRQ6.setBounds(744, 240, 45, 23);

        NilaiSRQ7.setHighlighter(null);
        NilaiSRQ7.setName("NilaiSRQ7"); // NOI18N
        FormInput.add(NilaiSRQ7);
        NilaiSRQ7.setBounds(744, 270, 45, 23);

        NilaiSRQ8.setHighlighter(null);
        NilaiSRQ8.setName("NilaiSRQ8"); // NOI18N
        FormInput.add(NilaiSRQ8);
        NilaiSRQ8.setBounds(744, 300, 45, 23);

        NilaiSRQ9.setHighlighter(null);
        NilaiSRQ9.setName("NilaiSRQ9"); // NOI18N
        FormInput.add(NilaiSRQ9);
        NilaiSRQ9.setBounds(744, 330, 45, 23);

        NilaiSRQ10.setHighlighter(null);
        NilaiSRQ10.setName("NilaiSRQ10"); // NOI18N
        FormInput.add(NilaiSRQ10);
        NilaiSRQ10.setBounds(744, 360, 45, 23);

        NilaiSRQ11.setHighlighter(null);
        NilaiSRQ11.setName("NilaiSRQ11"); // NOI18N
        FormInput.add(NilaiSRQ11);
        NilaiSRQ11.setBounds(744, 390, 45, 23);

        NilaiSRQ12.setHighlighter(null);
        NilaiSRQ12.setName("NilaiSRQ12"); // NOI18N
        FormInput.add(NilaiSRQ12);
        NilaiSRQ12.setBounds(744, 420, 45, 23);

        NilaiSRQ13.setHighlighter(null);
        NilaiSRQ13.setName("NilaiSRQ13"); // NOI18N
        FormInput.add(NilaiSRQ13);
        NilaiSRQ13.setBounds(744, 450, 45, 23);

        NilaiSRQ14.setHighlighter(null);
        NilaiSRQ14.setName("NilaiSRQ14"); // NOI18N
        FormInput.add(NilaiSRQ14);
        NilaiSRQ14.setBounds(744, 480, 45, 23);

        NilaiSRQ15.setHighlighter(null);
        NilaiSRQ15.setName("NilaiSRQ15"); // NOI18N
        FormInput.add(NilaiSRQ15);
        NilaiSRQ15.setBounds(744, 510, 45, 23);

        NilaiSRQ16.setHighlighter(null);
        NilaiSRQ16.setName("NilaiSRQ16"); // NOI18N
        FormInput.add(NilaiSRQ16);
        NilaiSRQ16.setBounds(744, 540, 45, 23);

        NilaiSRQ17.setHighlighter(null);
        NilaiSRQ17.setName("NilaiSRQ17"); // NOI18N
        FormInput.add(NilaiSRQ17);
        NilaiSRQ17.setBounds(744, 570, 45, 23);

        NilaiSRQ18.setHighlighter(null);
        NilaiSRQ18.setName("NilaiSRQ18"); // NOI18N
        FormInput.add(NilaiSRQ18);
        NilaiSRQ18.setBounds(744, 600, 45, 23);

        NilaiSRQ19.setHighlighter(null);
        NilaiSRQ19.setName("NilaiSRQ19"); // NOI18N
        FormInput.add(NilaiSRQ19);
        NilaiSRQ19.setBounds(744, 630, 45, 23);

        NilaiSRQ20.setHighlighter(null);
        NilaiSRQ20.setName("NilaiSRQ20"); // NOI18N
        FormInput.add(NilaiSRQ20);
        NilaiSRQ20.setBounds(744, 660, 45, 23);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("Kesimpulan");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(44, 740, 70, 23);

        jLabel161.setText(":");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(0, 740, 107, 23);

        Kesimpulan.setHighlighter(null);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(Kesimpulan);
        Kesimpulan.setBounds(111, 740, 678, 23);

        jLabel162.setText("Total Nilai SRQ :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(610, 690, 130, 23);

        TotalNilai.setHighlighter(null);
        TotalNilai.setName("TotalNilai"); // NOI18N
        FormInput.add(TotalNilai);
        TotalNilai.setBounds(744, 690, 45, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Apakah Anda sering merasa sakit kepala ?");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(62, 90, 450, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Apakah Anda kehilangan nafsu makan ?");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(62, 120, 450, 23);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("Apakah tidur Anda tidak nyenyak ?");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(62, 150, 450, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Apakah Anda mudah merasa takut ?");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(62, 180, 450, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Apakah Anda merasa cemas, tegang, atau khawatir ?");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(62, 210, 490, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Apakah tangan Anda gemetar ?");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(62, 240, 450, 23);

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel116.setText("Apakah Anda mengalami gangguan pencernaan ?");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(62, 270, 480, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText(" Apakah Anda merasa sulit berpikir jernih ?");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(62, 300, 450, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Apakah Anda merasa tidak bahagia ?");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(62, 330, 450, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Apakah Anda lebih sering menangis?");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(62, 360, 450, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("Apakah Anda merasa sulit untuk menikmati aktivitas sehari-hari ?");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(62, 390, 550, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("Apakah Anda mengalami kesulitan untuk mengambil keputusan ?");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(62, 420, 550, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("Apakah aktivitas/tugas sehari-hari Anda terbengkalai ?");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(62, 450, 550, 23);

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel134.setText("Apakah Anda merasa tidak mampu berperan dalam kehidupan ini ?");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(62, 480, 540, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("Apakah Anda kehilangan minat terhadap banyak hal ?");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(62, 510, 550, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("Apakah Anda merasa tidak berharga ?");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(62, 540, 450, 23);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel137.setText("Apakah Anda mempunyai pikiran untuk mengakhiri hidup Anda ?");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(62, 570, 550, 23);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("Apakah Anda merasa lelah sepanjang waktu ?");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(62, 600, 540, 23);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("Apakah Anda merasa tidak enak di perut ?");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(62, 630, 450, 23);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("Apakah Anda mudah lelah ?");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(62, 660, 450, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 720, 807, 1);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("II. INTERPRETASI");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(10, 720, 200, 23);

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
            Valid.pindah(evt,Kesimpulan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NamaPasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>KodePetugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NamaPetugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ15</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ15</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ16</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ16</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ17</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ17</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ18</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ18</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ19</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ19</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SRQ20</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.SRQ20</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.Total</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningInstrumenSRQ.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING INSTRUMEN SRQ<br><br></font>"+        
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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Alergi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }*/
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed

    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningObesitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningObesitasActionPerformed
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
            Valid.MyReportqry("rptFormulirSkriningObesitas.jasper","report","::[ Formulir Skrining Obesitas ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_srq.nip,petugas.nama,skrining_instrumen_srq.tanggal,"+
                    "skrining_instrumen_srq.kebiasaan_makan_manis,skrining_instrumen_srq.aktifitas_fisik_setiap_hari,skrining_instrumen_srq.istirahat_cukup,skrining_instrumen_srq.risiko_merokok,"+
                    "skrining_instrumen_srq.riwayat_minum_alkohol_merokok_keluarga,skrining_instrumen_srq.riwayat_penggunaan_obat_steroid,skrining_instrumen_srq.berat_badan,skrining_instrumen_srq.tinggi_badan,"+
                    "skrining_instrumen_srq.imt,skrining_instrumen_srq.kasifikasi_imt,skrining_instrumen_srq.lingkar_pinggang,skrining_instrumen_srq.risiko_lingkar_pinggang,skrining_instrumen_srq.status_obesitas,"+
                    "skrining_instrumen_srq.keterangan from skrining_instrumen_srq inner join reg_periksa on skrining_instrumen_srq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_srq.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningObesitasActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void SRQ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ1KeyPressed
       Valid.pindah(evt,btnPetugas,SRQ2);
    }//GEN-LAST:event_SRQ1KeyPressed

    private void SRQ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ2KeyPressed
        Valid.pindah(evt,SRQ1,SRQ3);
    }//GEN-LAST:event_SRQ2KeyPressed

    private void SRQ3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ3KeyPressed
        Valid.pindah(evt,SRQ2,SRQ4);
    }//GEN-LAST:event_SRQ3KeyPressed

    private void SRQ4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ4KeyPressed
        Valid.pindah(evt,SRQ3,SRQ5);
    }//GEN-LAST:event_SRQ4KeyPressed

    private void SRQ5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ5KeyPressed
        Valid.pindah(evt,SRQ4,SRQ6);
    }//GEN-LAST:event_SRQ5KeyPressed

    private void SRQ6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ6KeyPressed
        Valid.pindah(evt,SRQ5,SRQ7);
    }//GEN-LAST:event_SRQ6KeyPressed

    private void SRQ7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ7KeyPressed
        Valid.pindah(evt,SRQ6,SRQ8);
    }//GEN-LAST:event_SRQ7KeyPressed

    private void SRQ8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ8KeyPressed
        Valid.pindah(evt,SRQ7,SRQ9);
    }//GEN-LAST:event_SRQ8KeyPressed

    private void SRQ9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ9KeyPressed
        Valid.pindah(evt,SRQ8,SRQ10);
    }//GEN-LAST:event_SRQ9KeyPressed

    private void SRQ10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ10KeyPressed
        Valid.pindah(evt,SRQ9,SRQ11);
    }//GEN-LAST:event_SRQ10KeyPressed

    private void SRQ11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ11KeyPressed
        Valid.pindah(evt,SRQ10,SRQ12);
    }//GEN-LAST:event_SRQ11KeyPressed

    private void SRQ12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ12KeyPressed
        Valid.pindah(evt,SRQ11,SRQ13);
    }//GEN-LAST:event_SRQ12KeyPressed

    private void SRQ13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ13KeyPressed
        Valid.pindah(evt,SRQ12,SRQ14);
    }//GEN-LAST:event_SRQ13KeyPressed

    private void SRQ14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ14KeyPressed
        Valid.pindah(evt,SRQ13,SRQ15);
    }//GEN-LAST:event_SRQ14KeyPressed

    private void SRQ15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ15KeyPressed
        Valid.pindah(evt,SRQ14,SRQ16);
    }//GEN-LAST:event_SRQ15KeyPressed

    private void SRQ16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ16KeyPressed
        Valid.pindah(evt,SRQ15,SRQ17);
    }//GEN-LAST:event_SRQ16KeyPressed

    private void SRQ17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ17KeyPressed
        Valid.pindah(evt,SRQ16,SRQ18);
    }//GEN-LAST:event_SRQ17KeyPressed

    private void SRQ18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ18KeyPressed
        Valid.pindah(evt,SRQ17,SRQ19);
    }//GEN-LAST:event_SRQ18KeyPressed

    private void SRQ19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ19KeyPressed
        Valid.pindah(evt,SRQ18,SRQ20);
    }//GEN-LAST:event_SRQ19KeyPressed

    private void SRQ20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SRQ20KeyPressed
        Valid.pindah(evt,SRQ19,Kesimpulan);
    }//GEN-LAST:event_SRQ20KeyPressed

    private void SRQ1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ1ItemStateChanged
        if(SRQ1.getSelectedIndex()==1){
            NilaiSRQ1.setText("1");
        }else{
            NilaiSRQ1.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ1ItemStateChanged

    private void SRQ2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ2ItemStateChanged
        if(SRQ2.getSelectedIndex()==1){
            NilaiSRQ2.setText("1");
        }else{
            NilaiSRQ2.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ2ItemStateChanged

    private void SRQ3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ3ItemStateChanged
       if(SRQ3.getSelectedIndex()==1){
            NilaiSRQ3.setText("1");
        }else{
            NilaiSRQ3.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ3ItemStateChanged

    private void SRQ4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ4ItemStateChanged
       if(SRQ4.getSelectedIndex()==1){
            NilaiSRQ4.setText("1");
        }else{
            NilaiSRQ4.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ4ItemStateChanged

    private void SRQ5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ5ItemStateChanged
        if(SRQ5.getSelectedIndex()==1){
            NilaiSRQ5.setText("1");
        }else{
            NilaiSRQ5.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ5ItemStateChanged

    private void SRQ6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ6ItemStateChanged
       if(SRQ6.getSelectedIndex()==1){
            NilaiSRQ6.setText("1");
        }else{
            NilaiSRQ6.setText("0");
        }
    }//GEN-LAST:event_SRQ6ItemStateChanged

    private void SRQ7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ7ItemStateChanged
        if(SRQ7.getSelectedIndex()==1){
            NilaiSRQ7.setText("1");
        }else{
            NilaiSRQ7.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ7ItemStateChanged

    private void SRQ8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ8ItemStateChanged
       if(SRQ8.getSelectedIndex()==1){
            NilaiSRQ8.setText("1");
        }else{
            NilaiSRQ8.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ8ItemStateChanged

    private void SRQ9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ9ItemStateChanged
        if(SRQ9.getSelectedIndex()==1){
            NilaiSRQ9.setText("1");
        }else{
            NilaiSRQ9.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ9ItemStateChanged

    private void SRQ10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ10ItemStateChanged
        if(SRQ10.getSelectedIndex()==1){
            NilaiSRQ10.setText("1");
        }else{
            NilaiSRQ10.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ10ItemStateChanged

    private void SRQ11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ11ItemStateChanged
       if(SRQ11.getSelectedIndex()==1){
            NilaiSRQ11.setText("1");
        }else{
            NilaiSRQ11.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ11ItemStateChanged

    private void SRQ12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ12ItemStateChanged
        if(SRQ12.getSelectedIndex()==1){
            NilaiSRQ12.setText("1");
        }else{
            NilaiSRQ12.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ12ItemStateChanged

    private void SRQ13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ13ItemStateChanged
        if(SRQ13.getSelectedIndex()==1){
            NilaiSRQ13.setText("1");
        }else{
            NilaiSRQ13.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ13ItemStateChanged

    private void SRQ14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ14ItemStateChanged
       if(SRQ14.getSelectedIndex()==1){
            NilaiSRQ14.setText("1");
        }else{
            NilaiSRQ14.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ14ItemStateChanged

    private void SRQ15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ15ItemStateChanged
       if(SRQ15.getSelectedIndex()==1){
            NilaiSRQ15.setText("1");
        }else{
            NilaiSRQ15.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ15ItemStateChanged

    private void SRQ16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ16ItemStateChanged
        if(SRQ16.getSelectedIndex()==1){
            NilaiSRQ16.setText("1");
        }else{
            NilaiSRQ16.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ16ItemStateChanged

    private void SRQ17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ17ItemStateChanged
        if(SRQ17.getSelectedIndex()==1){
            NilaiSRQ17.setText("1");
        }else{
            NilaiSRQ17.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ17ItemStateChanged

    private void SRQ18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ18ItemStateChanged
       if(SRQ18.getSelectedIndex()==1){
            NilaiSRQ18.setText("1");
        }else{
            NilaiSRQ18.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ18ItemStateChanged

    private void SRQ19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ19ItemStateChanged
        if(SRQ19.getSelectedIndex()==1){
            NilaiSRQ19.setText("1");
        }else{
            NilaiSRQ19.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_SRQ19ItemStateChanged

    private void SRQ20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SRQ20ItemStateChanged
       if(SRQ20.getSelectedIndex()==1){
            NilaiSRQ20.setText("1");
        }else{
            NilaiSRQ20.setText("0");
        }
       isTotal();
    }//GEN-LAST:event_SRQ20ItemStateChanged

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah(evt,SRQ19,BtnSimpan);
    }//GEN-LAST:event_KesimpulanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningSRQ dialog = new RMSkriningSRQ(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox Kesimpulan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningObesitas;
    private widget.TextBox NilaiSRQ1;
    private widget.TextBox NilaiSRQ10;
    private widget.TextBox NilaiSRQ11;
    private widget.TextBox NilaiSRQ12;
    private widget.TextBox NilaiSRQ13;
    private widget.TextBox NilaiSRQ14;
    private widget.TextBox NilaiSRQ15;
    private widget.TextBox NilaiSRQ16;
    private widget.TextBox NilaiSRQ17;
    private widget.TextBox NilaiSRQ18;
    private widget.TextBox NilaiSRQ19;
    private widget.TextBox NilaiSRQ2;
    private widget.TextBox NilaiSRQ20;
    private widget.TextBox NilaiSRQ3;
    private widget.TextBox NilaiSRQ4;
    private widget.TextBox NilaiSRQ5;
    private widget.TextBox NilaiSRQ6;
    private widget.TextBox NilaiSRQ7;
    private widget.TextBox NilaiSRQ8;
    private widget.TextBox NilaiSRQ9;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox SRQ1;
    private widget.ComboBox SRQ10;
    private widget.ComboBox SRQ11;
    private widget.ComboBox SRQ12;
    private widget.ComboBox SRQ13;
    private widget.ComboBox SRQ14;
    private widget.ComboBox SRQ15;
    private widget.ComboBox SRQ16;
    private widget.ComboBox SRQ17;
    private widget.ComboBox SRQ18;
    private widget.ComboBox SRQ19;
    private widget.ComboBox SRQ2;
    private widget.ComboBox SRQ20;
    private widget.ComboBox SRQ3;
    private widget.ComboBox SRQ4;
    private widget.ComboBox SRQ5;
    private widget.ComboBox SRQ6;
    private widget.ComboBox SRQ7;
    private widget.ComboBox SRQ8;
    private widget.ComboBox SRQ9;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalNilai;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
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
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_srq.nip,petugas.nama,skrining_instrumen_srq.tanggal,"+
                    "skrining_instrumen_srq.pernyataansrq1,skrining_instrumen_srq.nilai_srq1,skrining_instrumen_srq.pernyataansrq2,skrining_instrumen_srq.nilai_srq2,skrining_instrumen_srq.pernyataansrq3,"+
                    "skrining_instrumen_srq.nilai_srq3,skrining_instrumen_srq.pernyataansrq4,skrining_instrumen_srq.nilai_srq4,skrining_instrumen_srq.pernyataansrq5,skrining_instrumen_srq.nilai_srq5,"+
                    "skrining_instrumen_srq.pernyataansrq6,skrining_instrumen_srq.nilai_srq6,skrining_instrumen_srq.pernyataansrq7,skrining_instrumen_srq.nilai_srq7,skrining_instrumen_srq.pernyataansrq8,"+
                    "skrining_instrumen_srq.nilai_srq8,skrining_instrumen_srq.pernyataansrq9,skrining_instrumen_srq.nilai_srq9,skrining_instrumen_srq.pernyataansrq10,skrining_instrumen_srq.nilai_srq10,"+
                    "skrining_instrumen_srq.pernyataansrq11,skrining_instrumen_srq.nilai_srq11,skrining_instrumen_srq.pernyataansrq12,skrining_instrumen_srq.nilai_srq12,skrining_instrumen_srq.pernyataansrq13,"+
                    "skrining_instrumen_srq.nilai_srq13,skrining_instrumen_srq.pernyataansrq14,skrining_instrumen_srq.nilai_srq14,skrining_instrumen_srq.pernyataansrq15,skrining_instrumen_srq.nilai_srq15,"+
                    "skrining_instrumen_srq.pernyataansrq16,skrining_instrumen_srq.nilai_srq16,skrining_instrumen_srq.pernyataansrq17,skrining_instrumen_srq.nilai_srq17,skrining_instrumen_srq.pernyataansrq18,"+
                    "skrining_instrumen_srq.nilai_srq18,skrining_instrumen_srq.pernyataansrq19,skrining_instrumen_srq.nilai_srq19,skrining_instrumen_srq.pernyataansrq20,skrining_instrumen_srq.nilai_srq20,"+
                    "skrining_instrumen_srq.nilai_total_srq,skrining_instrumen_srq.kesimpulan from skrining_instrumen_srq inner join reg_periksa on skrining_instrumen_srq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_srq.nip=petugas.nip "+
                    "where skrining_instrumen_srq.tanggal between ? and ? order by skrining_instrumen_srq.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,skrining_instrumen_srq.nip,petugas.nama,skrining_instrumen_srq.tanggal,"+
                    "skrining_instrumen_srq.pernyataansrq1,skrining_instrumen_srq.nilai_srq1,skrining_instrumen_srq.pernyataansrq2,skrining_instrumen_srq.nilai_srq2,skrining_instrumen_srq.pernyataansrq3,"+
                    "skrining_instrumen_srq.nilai_srq3,skrining_instrumen_srq.pernyataansrq4,skrining_instrumen_srq.nilai_srq4,skrining_instrumen_srq.pernyataansrq5,skrining_instrumen_srq.nilai_srq5,"+
                    "skrining_instrumen_srq.pernyataansrq6,skrining_instrumen_srq.nilai_srq6,skrining_instrumen_srq.pernyataansrq7,skrining_instrumen_srq.nilai_srq7,skrining_instrumen_srq.pernyataansrq8,"+
                    "skrining_instrumen_srq.nilai_srq8,skrining_instrumen_srq.pernyataansrq9,skrining_instrumen_srq.nilai_srq9,skrining_instrumen_srq.pernyataansrq10,skrining_instrumen_srq.nilai_srq10,"+
                    "skrining_instrumen_srq.pernyataansrq11,skrining_instrumen_srq.nilai_srq11,skrining_instrumen_srq.pernyataansrq12,skrining_instrumen_srq.nilai_srq12,skrining_instrumen_srq.pernyataansrq13,"+
                    "skrining_instrumen_srq.nilai_srq13,skrining_instrumen_srq.pernyataansrq14,skrining_instrumen_srq.nilai_srq14,skrining_instrumen_srq.pernyataansrq15,skrining_instrumen_srq.nilai_srq15,"+
                    "skrining_instrumen_srq.pernyataansrq16,skrining_instrumen_srq.nilai_srq16,skrining_instrumen_srq.pernyataansrq17,skrining_instrumen_srq.nilai_srq17,skrining_instrumen_srq.pernyataansrq18,"+
                    "skrining_instrumen_srq.nilai_srq18,skrining_instrumen_srq.pernyataansrq19,skrining_instrumen_srq.nilai_srq19,skrining_instrumen_srq.pernyataansrq20,skrining_instrumen_srq.nilai_srq20,"+
                    "skrining_instrumen_srq.nilai_total_srq,skrining_instrumen_srq.kesimpulan from skrining_instrumen_srq inner join reg_periksa on skrining_instrumen_srq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_srq.nip=petugas.nip "+
                    "where skrining_instrumen_srq.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_instrumen_srq.nip like ? or petugas.nama like ?) "+
                    "order by skrining_instrumen_srq.tanggal ");
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
                        rs.getString("pernyataansrq1"),rs.getString("nilai_srq1"),rs.getString("pernyataansrq2"),rs.getString("nilai_srq2"),rs.getString("pernyataansrq3"),rs.getString("nilai_srq3"),rs.getString("pernyataansrq4"),
                        rs.getString("nilai_srq4"),rs.getString("pernyataansrq5"),rs.getString("nilai_srq5"),rs.getString("pernyataansrq6"),rs.getString("nilai_srq6"),rs.getString("pernyataansrq7"),rs.getString("nilai_srq7"),
                        rs.getString("pernyataansrq8"),rs.getString("nilai_srq8"),rs.getString("pernyataansrq9"),rs.getString("nilai_srq9"),rs.getString("pernyataansrq10"),rs.getString("nilai_srq10"),rs.getString("pernyataansrq11"),
                        rs.getString("nilai_srq11"),rs.getString("pernyataansrq12"),rs.getString("nilai_srq12"),rs.getString("pernyataansrq13"),rs.getString("nilai_srq13"),rs.getString("pernyataansrq14"),rs.getString("nilai_srq14"),
                        rs.getString("pernyataansrq15"),rs.getString("nilai_srq15"),rs.getString("pernyataansrq16"),rs.getString("nilai_srq16"),rs.getString("pernyataansrq17"),rs.getString("nilai_srq17"),rs.getString("pernyataansrq18"),
                        rs.getString("nilai_srq18"),rs.getString("pernyataansrq19"),rs.getString("nilai_srq19"),rs.getString("pernyataansrq20"),rs.getString("nilai_srq20"),rs.getString("nilai_total_srq"),rs.getString("kesimpulan")
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
        SRQ1.setSelectedIndex(0);
        NilaiSRQ1.setText("0");
        SRQ2.setSelectedIndex(0);
        NilaiSRQ2.setText("0");
        SRQ3.setSelectedIndex(0);
        NilaiSRQ3.setText("0");
        SRQ4.setSelectedIndex(0);
        NilaiSRQ4.setText("0");
        SRQ5.setSelectedIndex(0);
        NilaiSRQ5.setText("0");
        SRQ6.setSelectedIndex(0);
        NilaiSRQ6.setText("0");
        SRQ7.setSelectedIndex(0);
        NilaiSRQ7.setText("0");
        SRQ8.setSelectedIndex(0);
        NilaiSRQ8.setText("0");
        SRQ9.setSelectedIndex(0);
        NilaiSRQ9.setText("0");
        SRQ10.setSelectedIndex(0);
        NilaiSRQ10.setText("0");
        SRQ11.setSelectedIndex(0);
        NilaiSRQ11.setText("0");
        SRQ12.setSelectedIndex(0);
        NilaiSRQ12.setText("0");
        SRQ13.setSelectedIndex(0);
        NilaiSRQ13.setText("0");
        SRQ14.setSelectedIndex(0);
        NilaiSRQ14.setText("0");
        SRQ15.setSelectedIndex(0);
        NilaiSRQ15.setText("0");
        SRQ16.setSelectedIndex(0);
        NilaiSRQ16.setText("0");
        SRQ17.setSelectedIndex(0);
        NilaiSRQ17.setText("0");
        SRQ18.setSelectedIndex(0);
        NilaiSRQ18.setText("0");
        SRQ19.setSelectedIndex(0);
        NilaiSRQ19.setText("0");
        SRQ20.setSelectedIndex(0);
        NilaiSRQ20.setText("0");
        TotalNilai.setText("0");
        Kesimpulan.setText("");
        Tanggal.setDate(new Date());
        SRQ1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            SRQ1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiSRQ1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            SRQ2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiSRQ2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            SRQ3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiSRQ3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            SRQ4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiSRQ4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            SRQ5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiSRQ5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            SRQ6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiSRQ6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            SRQ7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiSRQ7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SRQ8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiSRQ8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SRQ9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiSRQ9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SRQ10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiSRQ10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SRQ11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NilaiSRQ11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SRQ12.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NilaiSRQ12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SRQ13.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NilaiSRQ13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            SRQ14.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NilaiSRQ14.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            SRQ15.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            NilaiSRQ15.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            SRQ16.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            NilaiSRQ16.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SRQ17.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            NilaiSRQ17.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            SRQ18.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            NilaiSRQ18.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SRQ19.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            NilaiSRQ19.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            SRQ20.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            NilaiSRQ20.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            TotalNilai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
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
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        BtnSimpan.setEnabled(akses.getskrining_instrumen_srq());
        BtnHapus.setEnabled(akses.getskrining_instrumen_srq());
        BtnEdit.setEnabled(akses.getskrining_instrumen_srq());
        BtnPrint.setEnabled(akses.getskrining_instrumen_srq()); 
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
        if(Sequel.mengedittf("skrining_instrumen_srq","no_rawat=?","no_rawat=?,tanggal=?,nip=?,pernyataansrq1=?,nilai_srq1=?,pernyataansrq2=?,nilai_srq2=?,pernyataansrq3=?,nilai_srq3=?,pernyataansrq4=?,nilai_srq4=?,pernyataansrq5=?,nilai_srq5=?,"+
                "pernyataansrq6=?,nilai_srq6=?,pernyataansrq7=?,nilai_srq7=?,pernyataansrq8=?,nilai_srq8=?,pernyataansrq9=?,nilai_srq9=?,pernyataansrq10=?,nilai_srq10=?,pernyataansrq11=?,nilai_srq11=?,pernyataansrq12=?,nilai_srq12=?,pernyataansrq13=?,"+
                "nilai_srq13=?,pernyataansrq14=?,nilai_srq14=?,pernyataansrq15=?,nilai_srq15=?,pernyataansrq16=?,nilai_srq16=?,pernyataansrq17=?,nilai_srq17=?,pernyataansrq18=?,nilai_srq18=?,pernyataansrq19=?,nilai_srq19=?,pernyataansrq20=?,nilai_srq20=?,"+
                "nilai_total_srq=?,kesimpulan=?",46,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
                SRQ1.getSelectedItem().toString(),NilaiSRQ1.getText(),SRQ2.getSelectedItem().toString(),NilaiSRQ2.getText(),SRQ3.getSelectedItem().toString(),NilaiSRQ3.getText(),SRQ4.getSelectedItem().toString(),NilaiSRQ4.getText(), 
                SRQ5.getSelectedItem().toString(),NilaiSRQ5.getText(),SRQ6.getSelectedItem().toString(),NilaiSRQ6.getText(),SRQ7.getSelectedItem().toString(),NilaiSRQ7.getText(),SRQ8.getSelectedItem().toString(),NilaiSRQ8.getText(), 
                SRQ9.getSelectedItem().toString(),NilaiSRQ9.getText(),SRQ10.getSelectedItem().toString(),NilaiSRQ10.getText(),SRQ11.getSelectedItem().toString(),NilaiSRQ11.getText(),SRQ12.getSelectedItem().toString(),NilaiSRQ12.getText(),
                SRQ13.getSelectedItem().toString(),NilaiSRQ13.getText(),SRQ14.getSelectedItem().toString(),NilaiSRQ14.getText(),SRQ15.getSelectedItem().toString(),NilaiSRQ15.getText(),SRQ16.getSelectedItem().toString(),NilaiSRQ16.getText(),
                SRQ17.getSelectedItem().toString(),NilaiSRQ17.getText(),SRQ18.getSelectedItem().toString(),NilaiSRQ18.getText(),SRQ19.getSelectedItem().toString(),NilaiSRQ19.getText(),SRQ20.getSelectedItem().toString(),NilaiSRQ20.getText(),
                TotalNilai.getText(),Kesimpulan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(SRQ1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiSRQ1.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(SRQ2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiSRQ2.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(SRQ3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiSRQ3.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(SRQ4.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiSRQ4.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(SRQ5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiSRQ5.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(SRQ6.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiSRQ6.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(SRQ7.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiSRQ7.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(SRQ8.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiSRQ8.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(SRQ9.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(NilaiSRQ9.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(SRQ10.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(NilaiSRQ10.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(SRQ11.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(NilaiSRQ11.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(SRQ12.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(NilaiSRQ12.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(SRQ13.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(NilaiSRQ13.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(SRQ14.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(NilaiSRQ14.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(SRQ15.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(NilaiSRQ15.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(SRQ16.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(NilaiSRQ16.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(SRQ17.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(NilaiSRQ17.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(SRQ18.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(NilaiSRQ18.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(SRQ19.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(NilaiSRQ19.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(SRQ20.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(NilaiSRQ20.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(TotalNilai.getText(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),49);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_instrumen_srq where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_instrumen_srq","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",45,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdPetugas.getText(),
            SRQ1.getSelectedItem().toString(),NilaiSRQ1.getText(),SRQ2.getSelectedItem().toString(),NilaiSRQ2.getText(),SRQ3.getSelectedItem().toString(),NilaiSRQ3.getText(),SRQ4.getSelectedItem().toString(),NilaiSRQ4.getText(), 
            SRQ5.getSelectedItem().toString(),NilaiSRQ5.getText(),SRQ6.getSelectedItem().toString(),NilaiSRQ6.getText(),SRQ7.getSelectedItem().toString(),NilaiSRQ7.getText(),SRQ8.getSelectedItem().toString(),NilaiSRQ8.getText(), 
            SRQ9.getSelectedItem().toString(),NilaiSRQ9.getText(),SRQ10.getSelectedItem().toString(),NilaiSRQ10.getText(),SRQ11.getSelectedItem().toString(),NilaiSRQ11.getText(),SRQ12.getSelectedItem().toString(),NilaiSRQ12.getText(),
            SRQ13.getSelectedItem().toString(),NilaiSRQ13.getText(),SRQ14.getSelectedItem().toString(),NilaiSRQ14.getText(),SRQ15.getSelectedItem().toString(),NilaiSRQ15.getText(),SRQ16.getSelectedItem().toString(),NilaiSRQ16.getText(),
            SRQ17.getSelectedItem().toString(),NilaiSRQ17.getText(),SRQ18.getSelectedItem().toString(),NilaiSRQ18.getText(),SRQ19.getSelectedItem().toString(),NilaiSRQ19.getText(),SRQ20.getSelectedItem().toString(),NilaiSRQ20.getText(),
            TotalNilai.getText(),Kesimpulan.getText()
        })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                SRQ1.getSelectedItem().toString(),NilaiSRQ1.getText(),SRQ2.getSelectedItem().toString(),NilaiSRQ2.getText(),SRQ3.getSelectedItem().toString(),NilaiSRQ3.getText(),SRQ4.getSelectedItem().toString(),NilaiSRQ4.getText(), 
                SRQ5.getSelectedItem().toString(),NilaiSRQ5.getText(),SRQ6.getSelectedItem().toString(),NilaiSRQ6.getText(),SRQ7.getSelectedItem().toString(),NilaiSRQ7.getText(),SRQ8.getSelectedItem().toString(),NilaiSRQ8.getText(), 
                SRQ9.getSelectedItem().toString(),NilaiSRQ9.getText(),SRQ10.getSelectedItem().toString(),NilaiSRQ10.getText(),SRQ11.getSelectedItem().toString(),NilaiSRQ11.getText(),SRQ12.getSelectedItem().toString(),NilaiSRQ12.getText(),
                SRQ13.getSelectedItem().toString(),NilaiSRQ13.getText(),SRQ14.getSelectedItem().toString(),NilaiSRQ14.getText(),SRQ15.getSelectedItem().toString(),NilaiSRQ15.getText(),SRQ16.getSelectedItem().toString(),NilaiSRQ16.getText(),
                SRQ17.getSelectedItem().toString(),NilaiSRQ17.getText(),SRQ18.getSelectedItem().toString(),NilaiSRQ18.getText(),SRQ19.getSelectedItem().toString(),NilaiSRQ19.getText(),SRQ20.getSelectedItem().toString(),NilaiSRQ20.getText(),
                TotalNilai.getText(),Kesimpulan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
    private void isTotal() {
        try {
            TotalNilai.setText(""+(
                    Integer.parseInt(NilaiSRQ1.getText())+Integer.parseInt(NilaiSRQ2.getText())+Integer.parseInt(NilaiSRQ3.getText())+Integer.parseInt(NilaiSRQ4.getText())+Integer.parseInt(NilaiSRQ5.getText())+
                    Integer.parseInt(NilaiSRQ6.getText())+Integer.parseInt(NilaiSRQ7.getText())+Integer.parseInt(NilaiSRQ8.getText())+Integer.parseInt(NilaiSRQ9.getText())+Integer.parseInt(NilaiSRQ10.getText())+
                    Integer.parseInt(NilaiSRQ11.getText())+Integer.parseInt(NilaiSRQ12.getText())+Integer.parseInt(NilaiSRQ13.getText())+Integer.parseInt(NilaiSRQ14.getText())+Integer.parseInt(NilaiSRQ15.getText())+
                    Integer.parseInt(NilaiSRQ16.getText())+Integer.parseInt(NilaiSRQ17.getText())+Integer.parseInt(NilaiSRQ18.getText())+Integer.parseInt(NilaiSRQ19.getText())+Integer.parseInt(NilaiSRQ20.getText())
                    
            ));
            if(Integer.parseInt(TotalNilai.getText())>5){
                Kesimpulan.setText("Gangguan Mental Emosional");
            }else{
                Kesimpulan.setText("Kondisi Normal");
            }
        } catch (Exception e) {
            Kesimpulan.setText("Kondisi Normal");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * Kontribusi oleh Haris Rochmatullah RS Bhayangkara Nganjuk.
 */

/*
 * DlgSkriningMPP.java
 *
 * Created on 24 Oktober 2021, 10:19:56
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class RMSkriningMPP extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14, param15, param16,finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningMPP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Alamat","Tgl.Skrining","Parameter 1","Parameter 2","Parameter 3","Parameter 4",
            "Parameter 5","Parameter 6","Parameter 7","Parameter 8","Parameter 9","Parameter 10","Parameter 11","Parameter 12","Parameter 13","Parameter 14",
            "Parameter 15","Parameter 16","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());   
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
        
        ChkInput.setSelected(false);
        isForm();
      
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
        MnCetakLembarSkrining = new javax.swing.JMenuItem();
        MnEvaluasiFormA = new javax.swing.JMenuItem();
        MnEvaluasiFormB = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        buttonGroup11 = new javax.swing.ButtonGroup();
        buttonGroup12 = new javax.swing.ButtonGroup();
        buttonGroup13 = new javax.swing.ButtonGroup();
        buttonGroup14 = new javax.swing.ButtonGroup();
        buttonGroup15 = new javax.swing.ButtonGroup();
        buttonGroup16 = new javax.swing.ButtonGroup();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        JK = new widget.TextBox();
        jLabel10 = new widget.Label();
        AlamatLengkap = new widget.TextBox();
        label11 = new widget.Label();
        TglSkrining = new widget.Tanggal();
        jLabel11 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel39 = new widget.Label();
        Param1Ya = new widget.RadioButton();
        Param1Tidak = new widget.RadioButton();
        Param2Tidak = new widget.RadioButton();
        Param2Ya = new widget.RadioButton();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        Param3Ya = new widget.RadioButton();
        Param3Tidak = new widget.RadioButton();
        jLabel47 = new widget.Label();
        Param4Ya = new widget.RadioButton();
        Param4Tidak = new widget.RadioButton();
        jLabel48 = new widget.Label();
        Param5Ya = new widget.RadioButton();
        Param5Tidak = new widget.RadioButton();
        jLabel49 = new widget.Label();
        Param6Ya = new widget.RadioButton();
        Param6Tidak = new widget.RadioButton();
        Param7Ya = new widget.RadioButton();
        Param7Tidak = new widget.RadioButton();
        jLabel51 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        Param8Ya = new widget.RadioButton();
        Param8Tidak = new widget.RadioButton();
        Param9Ya = new widget.RadioButton();
        Param9Tidak = new widget.RadioButton();
        Param10Ya = new widget.RadioButton();
        Param10Tidak = new widget.RadioButton();
        Param11Ya = new widget.RadioButton();
        Param11Tidak = new widget.RadioButton();
        Param12Ya = new widget.RadioButton();
        Param12Tidak = new widget.RadioButton();
        Param13Ya = new widget.RadioButton();
        Param13Tidak = new widget.RadioButton();
        Param14Ya = new widget.RadioButton();
        Param14Tidak = new widget.RadioButton();
        Param15Tidak = new widget.RadioButton();
        Param15Ya = new widget.RadioButton();
        Param16Ya = new widget.RadioButton();
        Param16Tidak = new widget.RadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakLembarSkrining.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakLembarSkrining.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakLembarSkrining.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakLembarSkrining.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakLembarSkrining.setText("Form Skrining Manager Pelayanan Pasien");
        MnCetakLembarSkrining.setName("MnCetakLembarSkrining"); // NOI18N
        MnCetakLembarSkrining.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakLembarSkrining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakLembarSkriningActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakLembarSkrining);

        MnEvaluasiFormA.setBackground(new java.awt.Color(255, 255, 254));
        MnEvaluasiFormA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEvaluasiFormA.setForeground(new java.awt.Color(50, 50, 50));
        MnEvaluasiFormA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnEvaluasiFormA.setText("Form - A Evaluasi Awal");
        MnEvaluasiFormA.setName("MnEvaluasiFormA"); // NOI18N
        MnEvaluasiFormA.setPreferredSize(new java.awt.Dimension(250, 26));
        MnEvaluasiFormA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEvaluasiFormAActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnEvaluasiFormA);

        MnEvaluasiFormB.setBackground(new java.awt.Color(255, 255, 254));
        MnEvaluasiFormB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEvaluasiFormB.setForeground(new java.awt.Color(50, 50, 50));
        MnEvaluasiFormB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnEvaluasiFormB.setText("Form - B Catatan Implementasi");
        MnEvaluasiFormB.setName("MnEvaluasiFormB"); // NOI18N
        MnEvaluasiFormB.setPreferredSize(new java.awt.Dimension(250, 26));
        MnEvaluasiFormB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEvaluasiFormBActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnEvaluasiFormB);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Skrining Manager Pelayanan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(462, 771));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        jLabel19.setText("Tgl. Skrining :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-03-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-03-2023" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 378));
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

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 539));
        FormInput.setLayout(null);

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
        FormInput.add(TPasien);
        TPasien.setBounds(331, 10, 266, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 112, 23);

        jLabel5.setText("Alamat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(175, 40, 70, 23);

        label14.setText("Petugas MPP :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 505, 85, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(89, 505, 130, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(221, 505, 360, 23);

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
        BtnPetugas.setBounds(585, 505, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 90, 23);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(74, 40, 100, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        AlamatLengkap.setEditable(false);
        AlamatLengkap.setHighlighter(null);
        AlamatLengkap.setName("AlamatLengkap"); // NOI18N
        FormInput.add(AlamatLengkap);
        AlamatLengkap.setBounds(249, 40, 348, 23);

        label11.setText("Tgl. Skrining :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(605, 40, 80, 23);

        TglSkrining.setDisplayFormat("dd-MM-yyyy");
        TglSkrining.setName("TglSkrining"); // NOI18N
        TglSkrining.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSkriningKeyPressed(evt);
            }
        });
        FormInput.add(TglSkrining);
        TglSkrining.setBounds(689, 40, 90, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jLabel36.setText("Paramater Skrining :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 70, 115, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Keluhan pembiayaan");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(77, 90, 590, 23);

        buttonGroup1.add(Param1Ya);
        Param1Ya.setText("Ya");
        Param1Ya.setName("Param1Ya"); // NOI18N
        Param1Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param1Ya);
        Param1Ya.setBounds(670, 90, 45, 23);

        buttonGroup1.add(Param1Tidak);
        Param1Tidak.setSelected(true);
        Param1Tidak.setText("Tidak");
        Param1Tidak.setName("Param1Tidak"); // NOI18N
        FormInput.add(Param1Tidak);
        Param1Tidak.setBounds(720, 90, 60, 23);

        buttonGroup2.add(Param2Tidak);
        Param2Tidak.setSelected(true);
        Param2Tidak.setText("Tidak");
        Param2Tidak.setName("Param2Tidak"); // NOI18N
        FormInput.add(Param2Tidak);
        Param2Tidak.setBounds(720, 115, 60, 23);

        buttonGroup2.add(Param2Ya);
        Param2Ya.setText("Ya");
        Param2Ya.setName("Param2Ya"); // NOI18N
        Param2Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param2Ya);
        Param2Ya.setBounds(670, 115, 45, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Penundaan tindakan diagnostik");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(77, 115, 590, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Keluhan pembiayaan atau biaya klaim melebihi selama masa perawatan");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(77, 140, 590, 23);

        buttonGroup3.add(Param3Ya);
        Param3Ya.setText("Ya");
        Param3Ya.setName("Param3Ya"); // NOI18N
        Param3Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param3Ya);
        Param3Ya.setBounds(670, 140, 45, 23);

        buttonGroup3.add(Param3Tidak);
        Param3Tidak.setSelected(true);
        Param3Tidak.setText("Tidak");
        Param3Tidak.setName("Param3Tidak"); // NOI18N
        FormInput.add(Param3Tidak);
        Param3Tidak.setBounds(720, 140, 60, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Berisiko tinggi terjadinya komplain");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(77, 165, 590, 23);

        buttonGroup4.add(Param4Ya);
        Param4Ya.setText("Ya");
        Param4Ya.setName("Param4Ya"); // NOI18N
        Param4Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param4Ya);
        Param4Ya.setBounds(670, 165, 45, 23);

        buttonGroup4.add(Param4Tidak);
        Param4Tidak.setSelected(true);
        Param4Tidak.setText("Tidak");
        Param4Tidak.setName("Param4Tidak"); // NOI18N
        FormInput.add(Param4Tidak);
        Param4Tidak.setBounds(720, 165, 60, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Sering masuk IGD dalam waktu 1x24 jam");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(77, 190, 590, 23);

        buttonGroup5.add(Param5Ya);
        Param5Ya.setText("Ya");
        Param5Ya.setName("Param5Ya"); // NOI18N
        Param5Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param5Ya);
        Param5Ya.setBounds(670, 190, 45, 23);

        buttonGroup5.add(Param5Tidak);
        Param5Tidak.setSelected(true);
        Param5Tidak.setText("Tidak");
        Param5Tidak.setName("Param5Tidak"); // NOI18N
        FormInput.add(Param5Tidak);
        Param5Tidak.setBounds(720, 190, 60, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Usia >65 tahun dengan ketergantungan");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(77, 215, 590, 23);

        buttonGroup6.add(Param6Ya);
        Param6Ya.setText("Ya");
        Param6Ya.setName("Param6Ya"); // NOI18N
        Param6Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param6Ya);
        Param6Ya.setBounds(670, 215, 45, 23);

        buttonGroup6.add(Param6Tidak);
        Param6Tidak.setSelected(true);
        Param6Tidak.setText("Tidak");
        Param6Tidak.setName("Param6Tidak"); // NOI18N
        FormInput.add(Param6Tidak);
        Param6Tidak.setBounds(720, 215, 60, 23);

        buttonGroup7.add(Param7Ya);
        Param7Ya.setText("Ya");
        Param7Ya.setName("Param7Ya"); // NOI18N
        Param7Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param7Ya);
        Param7Ya.setBounds(670, 240, 45, 23);

        buttonGroup7.add(Param7Tidak);
        Param7Tidak.setSelected(true);
        Param7Tidak.setText("Tidak");
        Param7Tidak.setName("Param7Tidak"); // NOI18N
        FormInput.add(Param7Tidak);
        Param7Tidak.setBounds(720, 240, 60, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Kasus kompleks (penyakit kronis, multiple diagnosa yang membutuhkan penanganan khusus, memerlukan biaya tinggi) ");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(77, 240, 590, 23);

        jLabel50.setText("1. ");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 90, 70, 23);

        jLabel52.setText("2. ");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 115, 70, 23);

        jLabel53.setText("3. ");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 140, 70, 23);

        jLabel54.setText("4. ");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(0, 165, 70, 23);

        jLabel55.setText("5. ");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 190, 70, 23);

        jLabel56.setText("6. ");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 215, 70, 23);

        jLabel57.setText("7. ");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 240, 70, 23);

        jLabel58.setText("8. ");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 265, 70, 23);

        jLabel59.setText("9. ");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 290, 70, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("Pasien APS (Atas Permintaan Sendiri)");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(77, 265, 590, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Tidak ada keluarga");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(77, 290, 590, 23);

        jLabel62.setText("10. ");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 315, 70, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Ditangani lebih dari 2 dokter spesialis dengan diagnose bermasalah");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(77, 315, 590, 23);

        jLabel64.setText("11. ");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 340, 70, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Penolakan tindakan diagnostik");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(77, 340, 590, 23);

        jLabel66.setText("12. ");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 365, 70, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Penolakan tindakan keperawatan");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(77, 365, 590, 23);

        jLabel68.setText("13. ");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 390, 70, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Penolakan tindakan medis");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(77, 390, 590, 23);

        jLabel70.setText("14. ");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 415, 70, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Penundaan tindakan medis");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(77, 415, 590, 23);

        jLabel72.setText("15. ");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 440, 70, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Riwayat gangguan mental, upaya bunuh diri, terlantar, kekerasan, pelecehan, tinggal sendiri, pemakai narkoba");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(77, 440, 590, 23);

        jLabel74.setText("16. ");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 465, 70, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Membutuhkan kontinuitas pelayanan pasca discharge");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(77, 465, 590, 23);

        buttonGroup8.add(Param8Ya);
        Param8Ya.setText("Ya");
        Param8Ya.setName("Param8Ya"); // NOI18N
        Param8Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param8Ya);
        Param8Ya.setBounds(670, 265, 45, 23);

        buttonGroup8.add(Param8Tidak);
        Param8Tidak.setSelected(true);
        Param8Tidak.setText("Tidak");
        Param8Tidak.setName("Param8Tidak"); // NOI18N
        FormInput.add(Param8Tidak);
        Param8Tidak.setBounds(720, 265, 60, 23);

        buttonGroup9.add(Param9Ya);
        Param9Ya.setText("Ya");
        Param9Ya.setName("Param9Ya"); // NOI18N
        Param9Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param9Ya);
        Param9Ya.setBounds(670, 290, 45, 23);

        buttonGroup9.add(Param9Tidak);
        Param9Tidak.setSelected(true);
        Param9Tidak.setText("Tidak");
        Param9Tidak.setName("Param9Tidak"); // NOI18N
        FormInput.add(Param9Tidak);
        Param9Tidak.setBounds(720, 290, 60, 23);

        buttonGroup10.add(Param10Ya);
        Param10Ya.setText("Ya");
        Param10Ya.setName("Param10Ya"); // NOI18N
        Param10Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param10Ya);
        Param10Ya.setBounds(670, 315, 45, 23);

        buttonGroup10.add(Param10Tidak);
        Param10Tidak.setSelected(true);
        Param10Tidak.setText("Tidak");
        Param10Tidak.setName("Param10Tidak"); // NOI18N
        FormInput.add(Param10Tidak);
        Param10Tidak.setBounds(720, 315, 60, 23);

        buttonGroup11.add(Param11Ya);
        Param11Ya.setText("Ya");
        Param11Ya.setName("Param11Ya"); // NOI18N
        Param11Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param11Ya);
        Param11Ya.setBounds(670, 340, 45, 23);

        buttonGroup11.add(Param11Tidak);
        Param11Tidak.setSelected(true);
        Param11Tidak.setText("Tidak");
        Param11Tidak.setName("Param11Tidak"); // NOI18N
        FormInput.add(Param11Tidak);
        Param11Tidak.setBounds(720, 340, 60, 23);

        buttonGroup12.add(Param12Ya);
        Param12Ya.setText("Ya");
        Param12Ya.setName("Param12Ya"); // NOI18N
        Param12Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param12Ya);
        Param12Ya.setBounds(670, 365, 45, 23);

        buttonGroup12.add(Param12Tidak);
        Param12Tidak.setSelected(true);
        Param12Tidak.setText("Tidak");
        Param12Tidak.setName("Param12Tidak"); // NOI18N
        FormInput.add(Param12Tidak);
        Param12Tidak.setBounds(720, 365, 60, 23);

        buttonGroup13.add(Param13Ya);
        Param13Ya.setText("Ya");
        Param13Ya.setName("Param13Ya"); // NOI18N
        Param13Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param13Ya);
        Param13Ya.setBounds(670, 390, 45, 23);

        buttonGroup13.add(Param13Tidak);
        Param13Tidak.setSelected(true);
        Param13Tidak.setText("Tidak");
        Param13Tidak.setName("Param13Tidak"); // NOI18N
        FormInput.add(Param13Tidak);
        Param13Tidak.setBounds(720, 390, 60, 23);

        buttonGroup14.add(Param14Ya);
        Param14Ya.setText("Ya");
        Param14Ya.setName("Param14Ya"); // NOI18N
        Param14Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param14Ya);
        Param14Ya.setBounds(670, 415, 45, 23);

        buttonGroup14.add(Param14Tidak);
        Param14Tidak.setSelected(true);
        Param14Tidak.setText("Tidak");
        Param14Tidak.setName("Param14Tidak"); // NOI18N
        FormInput.add(Param14Tidak);
        Param14Tidak.setBounds(720, 415, 60, 23);

        buttonGroup15.add(Param15Tidak);
        Param15Tidak.setSelected(true);
        Param15Tidak.setText("Tidak");
        Param15Tidak.setName("Param15Tidak"); // NOI18N
        FormInput.add(Param15Tidak);
        Param15Tidak.setBounds(720, 440, 60, 23);

        buttonGroup15.add(Param15Ya);
        Param15Ya.setText("Ya");
        Param15Ya.setName("Param15Ya"); // NOI18N
        Param15Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param15Ya);
        Param15Ya.setBounds(670, 440, 45, 23);

        buttonGroup16.add(Param16Ya);
        Param16Ya.setText("Ya");
        Param16Ya.setName("Param16Ya"); // NOI18N
        Param16Ya.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(Param16Ya);
        Param16Ya.setBounds(670, 465, 45, 23);

        buttonGroup16.add(Param16Tidak);
        Param16Tidak.setSelected(true);
        Param16Tidak.setText("Tidak");
        Param16Tidak.setName("Param16Tidak"); // NOI18N
        FormInput.add(Param16Tidak);
        Param16Tidak.setBounds(720, 465, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 795, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 495, 795, 1);

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
            Valid.pindah(evt,TCari,TglSkrining);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            param1="Tidak"; 
            param2="Tidak";
            param3="Tidak";
            param4="Tidak";
            param5="Tidak";
            param6="Tidak";
            param7="Tidak";
            param8="Tidak"; 
            param9="Tidak";
            param10="Tidak";
            param11="Tidak";
            param12="Tidak";
            param13="Tidak";
            param14="Tidak";
            param15="Tidak";
            param16="Tidak";
            if(Param1Ya.isSelected()==true){
                param1="Ya";
            }
            if(Param2Ya.isSelected()==true){
                param2="Ya";
            }
            if(Param3Ya.isSelected()==true){
                param3="Ya";
            }
            if(Param4Ya.isSelected()==true){
                param4="Ya";
            }
            if(Param5Ya.isSelected()==true){
                param5="Ya";
            }
            if(Param6Ya.isSelected()==true){
                param6="Ya";
            }
            if(Param7Ya.isSelected()==true){
                param7="Ya";
            }
            if(Param8Ya.isSelected()==true){
                param8="Ya";
            }
            if(Param9Ya.isSelected()==true){
                param9="Ya";
            }
            if(Param10Ya.isSelected()==true){
                param10="Ya";
            }
            if(Param11Ya.isSelected()==true){
                param11="Ya";
            }
            if(Param12Ya.isSelected()==true){
                param12="Ya";
            }
            if(Param13Ya.isSelected()==true){
                param13="Ya";
            }
            if(Param14Ya.isSelected()==true){
                param14="Ya";
            }
            if(Param15Ya.isSelected()==true){
                param15="Ya";
            }
            if(Param16Ya.isSelected()==true){
                param16="Ya";
            }
            if(Sequel.menyimpantf("mpp_skrining","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglSkrining.getSelectedItem()+""), param1, param2, param3, param4, param5, param6, param7,
                    param8, param9, param10, param11, param12, param13, param14, param15, param16,KdPetugas.getText()
                })==true){
                    tabMode.addRow(new String[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),JK.getText().substring(0,1),TglLahir.getText(),AlamatLengkap.getText(),Valid.SetTgl(TglSkrining.getSelectedItem()+""),
                        param1,param2,param3,param4,param5,param6,param7,param8,param9,param10,param11,param12,param13,param14,param15,param16,KdPetugas.getText(),NmPetugas.getText()
                    });
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TglSkrining,BtnBatal);
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
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())){
                    hapus();
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
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
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(TCari.getText().equals("")){
                    Valid.MyReportqry("rptDataSkriningMPP.jasper","report","::[ Data Skrining Manajer Pelayanan Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                        "mpp_skrining.tanggal,mpp_skrining.param1,mpp_skrining.param2,mpp_skrining.param3,mpp_skrining.param3,"+
                        "mpp_skrining.param4,mpp_skrining.param5,mpp_skrining.param5,mpp_skrining.param6,mpp_skrining.param7,"+
                        "mpp_skrining.param8,mpp_skrining.param9,mpp_skrining.param10,mpp_skrining.param11,mpp_skrining.param12,"+
                        "mpp_skrining.param13,mpp_skrining.param14,mpp_skrining.param15,mpp_skrining.param16,mpp_skrining.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_skrining on reg_periksa.no_rawat=mpp_skrining.no_rawat "+
                        "inner join pegawai on mpp_skrining.nip=pegawai.nik "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_skrining.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by mpp_skrining.tanggal",param);
                }else{
                    Valid.MyReportqry("rptDataSkriningMPP.jasper","report","::[ Data Skrining Manajer Pelayanan Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                        "mpp_skrining.tanggal,mpp_skrining.param1,mpp_skrining.param2,mpp_skrining.param3,mpp_skrining.param3,"+
                        "mpp_skrining.param4,mpp_skrining.param5,mpp_skrining.param5,mpp_skrining.param6,mpp_skrining.param7,"+
                        "mpp_skrining.param8,mpp_skrining.param9,mpp_skrining.param10,mpp_skrining.param11,mpp_skrining.param12,"+
                        "mpp_skrining.param13,mpp_skrining.param14,mpp_skrining.param15,mpp_skrining.param16,mpp_skrining.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_skrining on reg_periksa.no_rawat=mpp_skrining.no_rawat "+
                        "inner join pegawai on mpp_skrining.nip=pegawai.nik "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_skrining.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and "+
                        "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "mpp_skrining.nip like '%"+TCari.getText().trim()+"%' or pegawai.nama like '%"+TCari.getText().trim()+"%') order by mpp_skrining.tanggal",param);
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
                    ChkInput.setSelected(true);
                    isForm(); 
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,TNoRw,BtnSimpan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void MnCetakLembarSkriningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakLembarSkriningActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("alamat",AlamatLengkap.getText());   
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdPetugas.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPetugas.getText()+"\nID "+(finger.equals("")?TPasien.getText():finger)+"\n"+TglSkrining.getSelectedItem());  
            Valid.MyReportqry("rptCetakSkriningMPP.jasper","report","::[ Laporan Skrining Manager Pelayanan Pasien ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                        "mpp_skrining.tanggal,mpp_skrining.param1,mpp_skrining.param2,mpp_skrining.param3,mpp_skrining.param3,"+
                        "mpp_skrining.param4,mpp_skrining.param5,mpp_skrining.param5,mpp_skrining.param6,mpp_skrining.param7,"+
                        "mpp_skrining.param8,mpp_skrining.param9,mpp_skrining.param10,mpp_skrining.param11,mpp_skrining.param12,"+
                        "mpp_skrining.param13,mpp_skrining.param14,mpp_skrining.param15,mpp_skrining.param16,mpp_skrining.nip,pegawai.nama,reg_periksa.umurdaftar,reg_periksa.sttsumur, penjab.png_jawab "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_skrining on reg_periksa.no_rawat=mpp_skrining.no_rawat "+
                        "inner join pegawai on mpp_skrining.nip=pegawai.nik "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where mpp_skrining.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnCetakLembarSkriningActionPerformed

    private void TglSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSkriningKeyPressed
        Valid.pindah(evt,TCari,KdPetugas);
    }//GEN-LAST:event_TglSkriningKeyPressed

    private void MnEvaluasiFormAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEvaluasiFormAActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningMPPFormA form=new RMSkriningMPPFormA(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnEvaluasiFormAActionPerformed

    private void MnEvaluasiFormBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEvaluasiFormBActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningMPPFormB form=new RMSkriningMPPFormB(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnEvaluasiFormBActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningMPP dialog = new RMSkriningMPP(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatLengkap;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
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
    private widget.TextBox JK;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakLembarSkrining;
    private javax.swing.JMenuItem MnEvaluasiFormA;
    private javax.swing.JMenuItem MnEvaluasiFormB;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton Param10Tidak;
    private widget.RadioButton Param10Ya;
    private widget.RadioButton Param11Tidak;
    private widget.RadioButton Param11Ya;
    private widget.RadioButton Param12Tidak;
    private widget.RadioButton Param12Ya;
    private widget.RadioButton Param13Tidak;
    private widget.RadioButton Param13Ya;
    private widget.RadioButton Param14Tidak;
    private widget.RadioButton Param14Ya;
    private widget.RadioButton Param15Tidak;
    private widget.RadioButton Param15Ya;
    private widget.RadioButton Param16Tidak;
    private widget.RadioButton Param16Ya;
    private widget.RadioButton Param1Tidak;
    private widget.RadioButton Param1Ya;
    private widget.RadioButton Param2Tidak;
    private widget.RadioButton Param2Ya;
    private widget.RadioButton Param3Tidak;
    private widget.RadioButton Param3Ya;
    private widget.RadioButton Param4Tidak;
    private widget.RadioButton Param4Ya;
    private widget.RadioButton Param5Tidak;
    private widget.RadioButton Param5Ya;
    private widget.RadioButton Param6Tidak;
    private widget.RadioButton Param6Ya;
    private widget.RadioButton Param7Tidak;
    private widget.RadioButton Param7Ya;
    private widget.RadioButton Param8Tidak;
    private widget.RadioButton Param8Ya;
    private widget.RadioButton Param9Tidak;
    private widget.RadioButton Param9Ya;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglSkrining;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup11;
    private javax.swing.ButtonGroup buttonGroup12;
    private javax.swing.ButtonGroup buttonGroup13;
    private javax.swing.ButtonGroup buttonGroup14;
    private javax.swing.ButtonGroup buttonGroup15;
    private javax.swing.ButtonGroup buttonGroup16;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel36;
    private widget.Label jLabel39;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
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
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
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
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                        "mpp_skrining.tanggal,mpp_skrining.param1,mpp_skrining.param2,mpp_skrining.param3,mpp_skrining.param3,"+
                        "mpp_skrining.param4,mpp_skrining.param5,mpp_skrining.param5,mpp_skrining.param6,mpp_skrining.param7,"+
                        "mpp_skrining.param8,mpp_skrining.param9,mpp_skrining.param10,mpp_skrining.param11,mpp_skrining.param12,"+
                        "mpp_skrining.param13,mpp_skrining.param14,mpp_skrining.param15,mpp_skrining.param16,mpp_skrining.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_skrining on reg_periksa.no_rawat=mpp_skrining.no_rawat "+
                        "inner join pegawai on mpp_skrining.nip=pegawai.nik "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_skrining.tanggal between ? and ? order by mpp_skrining.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat,"+
                        "mpp_skrining.tanggal,mpp_skrining.param1,mpp_skrining.param2,mpp_skrining.param3,mpp_skrining.param3,"+
                        "mpp_skrining.param4,mpp_skrining.param5,mpp_skrining.param5,mpp_skrining.param6,mpp_skrining.param7,"+
                        "mpp_skrining.param8,mpp_skrining.param9,mpp_skrining.param10,mpp_skrining.param11,mpp_skrining.param12,"+
                        "mpp_skrining.param13,mpp_skrining.param14,mpp_skrining.param15,mpp_skrining.param16,mpp_skrining.nip,pegawai.nama "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join mpp_skrining on reg_periksa.no_rawat=mpp_skrining.no_rawat "+
                        "inner join pegawai on mpp_skrining.nip=pegawai.nik "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where "+
                        "mpp_skrining.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                        "pasien.nm_pasien like ? or mpp_skrining.nip like ? or pegawai.nama like ?) order by mpp_skrining.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("alamat"),
                        rs.getString("tanggal"),rs.getString("param1"),rs.getString("param2"),rs.getString("param3"),rs.getString("param4"),rs.getString("param5"),
                        rs.getString("param6"),rs.getString("param7"),rs.getString("param8"),rs.getString("param9"),rs.getString("param10"),rs.getString("param11"),
                        rs.getString("param12"),rs.getString("param13"),rs.getString("param14"),rs.getString("param15"),rs.getString("param16"),rs.getString("nip"),rs.getString("nama")
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
        TglSkrining.setDate(new Date());
        Param1Tidak.setSelected(true);
        Param2Tidak.setSelected(true);
        Param3Tidak.setSelected(true);
        Param4Tidak.setSelected(true);
        Param5Tidak.setSelected(true);
        Param6Tidak.setSelected(true);
        Param7Tidak.setSelected(true);
        Param8Tidak.setSelected(true);
        Param9Tidak.setSelected(true);
        Param10Tidak.setSelected(true);
        Param11Tidak.setSelected(true);
        Param12Tidak.setSelected(true);
        Param13Tidak.setSelected(true);
        Param14Tidak.setSelected(true);
        Param15Tidak.setSelected(true);
        Param16Tidak.setSelected(true);
        TNoRw.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));    
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            AlamatLengkap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            Valid.SetTgl(TglSkrining,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().equals("Ya")){
                Param1Ya.setSelected(true);
            }else{
                Param1Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("Ya")){
                Param2Ya.setSelected(true);
            }else{
                Param2Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("Ya")){
                Param3Ya.setSelected(true);
            }else{
                Param3Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("Ya")){
                Param4Ya.setSelected(true);
            }else{
                Param4Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("Ya")){
                Param5Ya.setSelected(true);
            }else{
                Param5Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString().equals("Ya")){
                Param6Ya.setSelected(true);
            }else{
                Param6Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString().equals("Ya")){
                Param7Ya.setSelected(true);
            }else{
                Param7Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString().equals("Ya")){
                Param8Ya.setSelected(true);
            }else{
                Param8Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().equals("Ya")){
                Param9Ya.setSelected(true);
            }else{
                Param9Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString().equals("Ya")){
                Param10Ya.setSelected(true);
            }else{
                Param10Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString().equals("Ya")){
                Param11Ya.setSelected(true);
            }else{
                Param11Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString().equals("Ya")){
                Param12Ya.setSelected(true);
            }else{
                Param12Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString().equals("Ya")){
                Param13Ya.setSelected(true);
            }else{
                Param13Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString().equals("Ya")){
                Param14Ya.setSelected(true);
            }else{
                Param14Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString().equals("Ya")){
                Param15Ya.setSelected(true);
            }else{
                Param15Tidak.setSelected(true);
            }
            if(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString().equals("Ya")){
                Param16Ya.setSelected(true);
            }else{
                Param16Tidak.setSelected(true);
            }
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());  
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.nm_pasien,pasien.jk,date_format(pasien.tgl_lahir,'%d-%m-%Y') as lahir,"+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat "+
                    "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where pasien.no_rkm_medis=? ");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk").replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));
                    TglLahir.setText(rs.getString("lahir"));
                    AlamatLengkap.setText(rs.getString("alamat"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }finally {
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
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-262));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getmpp_skrining());
        BtnHapus.setEnabled(akses.getmpp_skrining());
        BtnEdit.setEnabled(akses.getmpp_skrining());
        BtnPrint.setEnabled(akses.getmpp_skrining());   
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(pegawai.tampil3(KdPetugas.getText()));
        }         
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from mpp_skrining where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        param1="Tidak"; 
        param2="Tidak";
        param3="Tidak";
        param4="Tidak";
        param5="Tidak";
        param6="Tidak";
        param7="Tidak";
        param8="Tidak"; 
        param9="Tidak";
        param10="Tidak";
        param11="Tidak";
        param12="Tidak";
        param13="Tidak";
        param14="Tidak";
        param15="Tidak";
        param16="Tidak";
        if(Param1Ya.isSelected()==true){
            param1="Ya";
        }
        if(Param2Ya.isSelected()==true){
            param2="Ya";
        }
        if(Param3Ya.isSelected()==true){
            param3="Ya";
        }
        if(Param4Ya.isSelected()==true){
            param4="Ya";
        }
        if(Param5Ya.isSelected()==true){
            param5="Ya";
        }
        if(Param6Ya.isSelected()==true){
            param6="Ya";
        }
        if(Param7Ya.isSelected()==true){
            param7="Ya";
        }
        if(Param8Ya.isSelected()==true){
            param8="Ya";
        }
        if(Param9Ya.isSelected()==true){
            param9="Ya";
        }
        if(Param10Ya.isSelected()==true){
            param10="Ya";
        }
        if(Param11Ya.isSelected()==true){
            param11="Ya";
        }
        if(Param12Ya.isSelected()==true){
            param12="Ya";
        }
        if(Param13Ya.isSelected()==true){
            param13="Ya";
        }
        if(Param14Ya.isSelected()==true){
            param14="Ya";
        }
        if(Param15Ya.isSelected()==true){
            param15="Ya";
        }
        if(Param16Ya.isSelected()==true){
            param16="Ya";
        }  
        if(Sequel.mengedittf("mpp_skrining","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,param1=?,param2=?,param3=?,param4=?,param5=?,param6=?,param7=?,param8=?,param9=?,param10=?,param11=?,param12=?,param13=?,param14=?,param15=?,param16=?,nip=?",21,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglSkrining.getSelectedItem()+""), param1, param2, param3, param4, param5, param6, param7,param8, param9, param10, param11, param12, param13, param14, param15, param16,
                KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(JK.getText().substring(0,1),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(AlamatLengkap.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(TglSkrining.getSelectedItem()+""),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(param1,tbObat.getSelectedRow(),7);
                tbObat.setValueAt(param2,tbObat.getSelectedRow(),8);
                tbObat.setValueAt(param3,tbObat.getSelectedRow(),9);
                tbObat.setValueAt(param4,tbObat.getSelectedRow(),10);
                tbObat.setValueAt(param5,tbObat.getSelectedRow(),11);
                tbObat.setValueAt(param6,tbObat.getSelectedRow(),12);
                tbObat.setValueAt(param7,tbObat.getSelectedRow(),13);
                tbObat.setValueAt(param8,tbObat.getSelectedRow(),14);
                tbObat.setValueAt(param9,tbObat.getSelectedRow(),15);
                tbObat.setValueAt(param10,tbObat.getSelectedRow(),16);
                tbObat.setValueAt(param11,tbObat.getSelectedRow(),17);
                tbObat.setValueAt(param12,tbObat.getSelectedRow(),18);
                tbObat.setValueAt(param13,tbObat.getSelectedRow(),19);
                tbObat.setValueAt(param14,tbObat.getSelectedRow(),20);
                tbObat.setValueAt(param15,tbObat.getSelectedRow(),21);
                tbObat.setValueAt(param16,tbObat.getSelectedRow(),22);
                tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),24);
                emptTeks();
        }
    }
}

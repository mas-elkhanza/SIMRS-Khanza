/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kepegawaian;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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


/**
 *
 * @author perpustakaan
 */
public final class DlgAuditPenangananDarah extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariRuangAuditKepatuhan ruang=new DlgCariRuangAuditKepatuhan(null,false);
    private double menggunakan_apd_waktu_membuang_darah=0,komponen_darah_tidak_ada_dilantai=0,membuang_darah_pada_tempat_ditentukan=0,
                pembersihan_areal_tumbahan_darah=0,apd_dibuang_di_limbah_infeksius=0,ttlmenggunakan_apd_waktu_membuang_darah=0,melakukan_kebersihan_tangan_setelah_prosedur=0,
                ttlkomponen_darah_tidak_ada_dilantai=0,ttlmembuang_darah_pada_tempat_ditentukan=0,ttlpembersihan_areal_tumbahan_darah=0,ttlapd_dibuang_di_limbah_infeksius=0,
                ttlmelakukan_kebersihan_tangan_setelah_prosedur=0,ttlpenilaian=0;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgAuditPenangananDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Tanggal Audit","ID Ruang","Ruang/Unit","1.Menggunakan APD Waktu Membuang Darah / Komponen Darah",
            "2.Komponen Darah Tidak Ada Di Lantai","3.Membuang Darah / Komponen Darah Pada Tempat Yang Ditentukan",
            "4.Pembersihan Areal Tumpahan Darah Dengan Clorin / Spill Kit","5.APD Yang Digunakan Dibuang Di Limbah Infeksius",
            "6.Melakukan Kebersihan Tangan Setelah Prosedur Tersebut","Ttl.Nilai(%)"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(68);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        KdRuang.setDocument(new batasInput((byte)20).getKata(KdRuang));
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
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){                   
                    KdRuang.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),0).toString());
                    NmRuang.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());
                }  
                KdRuang.requestFocus();
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
        FormInput = new widget.PanelBiasa();
        Tanggal = new widget.Tanggal();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdRuang = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel14 = new widget.Label();
        MenggunakanApdWaktuMembuangDarah = new widget.ComboBox();
        jLabel17 = new widget.Label();
        KomponenDarahTidakAdaDilantai = new widget.ComboBox();
        jLabel23 = new widget.Label();
        MembuangDarahPadaTempatDitentukan = new widget.ComboBox();
        NmRuang = new widget.TextBox();
        PembersihanArealTumbahanDarah = new widget.ComboBox();
        ApdDibuangdiLimbahInfeksius = new widget.ComboBox();
        jLabel22 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        MelakukanKebersihanTanganSetelahProsedur = new widget.ComboBox();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Audit Penanganan & Pembuangan Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        jLabel7.setPreferredSize(new java.awt.Dimension(105, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(135, 23));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(405, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 154));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-06-2022" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(64, 10, 90, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 10, 60, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(160, 10, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(225, 10, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(290, 10, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(355, 10, 23, 23);

        jLabel18.setText("Ruang/Unit Diaudit :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(430, 10, 130, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        FormInput.add(KdRuang);
        KdRuang.setBounds(564, 10, 80, 23);

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
        btnPetugas.setBounds(850, 10, 28, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("1.  Menggunakan APD Waktu Membuang Darah / Komponen Darah");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(14, 40, 355, 23);

        MenggunakanApdWaktuMembuangDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MenggunakanApdWaktuMembuangDarah.setName("MenggunakanApdWaktuMembuangDarah"); // NOI18N
        MenggunakanApdWaktuMembuangDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenggunakanApdWaktuMembuangDarahKeyPressed(evt);
            }
        });
        FormInput.add(MenggunakanApdWaktuMembuangDarah);
        MenggunakanApdWaktuMembuangDarah.setBounds(370, 40, 78, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2.  Komponen Darah Tidak Ada Di Lantai");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(460, 40, 340, 23);

        KomponenDarahTidakAdaDilantai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KomponenDarahTidakAdaDilantai.setName("KomponenDarahTidakAdaDilantai"); // NOI18N
        KomponenDarahTidakAdaDilantai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomponenDarahTidakAdaDilantaiKeyPressed(evt);
            }
        });
        FormInput.add(KomponenDarahTidakAdaDilantai);
        KomponenDarahTidakAdaDilantai.setBounds(800, 40, 78, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("3.  Membuang Darah / Komponen Darah Pada Tempat Yang Ditentukan");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(14, 70, 355, 23);

        MembuangDarahPadaTempatDitentukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MembuangDarahPadaTempatDitentukan.setName("MembuangDarahPadaTempatDitentukan"); // NOI18N
        MembuangDarahPadaTempatDitentukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MembuangDarahPadaTempatDitentukanKeyPressed(evt);
            }
        });
        FormInput.add(MembuangDarahPadaTempatDitentukan);
        MembuangDarahPadaTempatDitentukan.setBounds(370, 70, 78, 23);

        NmRuang.setEditable(false);
        NmRuang.setName("NmRuang"); // NOI18N
        FormInput.add(NmRuang);
        NmRuang.setBounds(647, 10, 200, 23);

        PembersihanArealTumbahanDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PembersihanArealTumbahanDarah.setName("PembersihanArealTumbahanDarah"); // NOI18N
        PembersihanArealTumbahanDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembersihanArealTumbahanDarahKeyPressed(evt);
            }
        });
        FormInput.add(PembersihanArealTumbahanDarah);
        PembersihanArealTumbahanDarah.setBounds(800, 70, 78, 23);

        ApdDibuangdiLimbahInfeksius.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ApdDibuangdiLimbahInfeksius.setName("ApdDibuangdiLimbahInfeksius"); // NOI18N
        ApdDibuangdiLimbahInfeksius.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApdDibuangdiLimbahInfeksiusKeyPressed(evt);
            }
        });
        FormInput.add(ApdDibuangdiLimbahInfeksius);
        ApdDibuangdiLimbahInfeksius.setBounds(370, 100, 78, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("4.  Pembersihan Areal Tumpahan Darah Dengan Clorin / Spill Kit");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(460, 70, 340, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("5.  APD Yang Digunakan Dibuang Di Limbah Infeksius");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(14, 100, 355, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("6.  Melakukan Kebersihan Tangan Setelah Prosedur Tersebut");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(460, 100, 340, 23);

        MelakukanKebersihanTanganSetelahProsedur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        MelakukanKebersihanTanganSetelahProsedur.setName("MelakukanKebersihanTanganSetelahProsedur"); // NOI18N
        MelakukanKebersihanTanganSetelahProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MelakukanKebersihanTanganSetelahProsedurKeyPressed(evt);
            }
        });
        FormInput.add(MelakukanKebersihanTanganSetelahProsedur);
        MelakukanKebersihanTanganSetelahProsedur.setBounds(800, 100, 78, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(KdRuang.getText().trim().equals("")||NmRuang.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Ruang/Unit");
        }else{
            if(Sequel.menyimpantf("audit_penanganan_darah","?,?,?,?,?,?,?,?","Data",8,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),MenggunakanApdWaktuMembuangDarah.getSelectedItem().toString(),
                KomponenDarahTidakAdaDilantai.getSelectedItem().toString(),MembuangDarahPadaTempatDitentukan.getSelectedItem().toString(),PembersihanArealTumbahanDarah.getSelectedItem().toString(),
                ApdDibuangdiLimbahInfeksius.getSelectedItem().toString(),MelakukanKebersihanTanganSetelahProsedur.getSelectedItem().toString()
            })==true){
                tampil();
                emptTeks();
            }  
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,MelakukanKebersihanTanganSetelahProsedur,BtnBatal);
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
        if(tbObat.getSelectedRow()!= -1){
            if(Sequel.queryu2tf("delete from audit_penanganan_darah where id_ruang=? and tanggal=?",2,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
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
        if(KdRuang.getText().trim().equals("")||NmRuang.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Ruang/Unit");
        }else{    
            Sequel.mengedit("audit_penanganan_darah","id_ruang=? and tanggal=?","tanggal=?,id_ruang=?,menggunakan_apd_waktu_membuang_darah=?,komponen_darah_tidak_ada_dilantai=?,membuang_darah_pada_tempat_ditentukan=?,"+
                "pembersihan_areal_tumbahan_darah=?,apd_dibuang_di_limbah_infeksius=?,melakukan_kebersihan_tangan_setelah_prosedur=?",10,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),MenggunakanApdWaktuMembuangDarah.getSelectedItem().toString(),
                KomponenDarahTidakAdaDilantai.getSelectedItem().toString(),MembuangDarahPadaTempatDitentukan.getSelectedItem().toString(),PembersihanArealTumbahanDarah.getSelectedItem().toString(),
                ApdDibuangdiLimbahInfeksius.getSelectedItem().toString(),MelakukanKebersihanTanganSetelahProsedur.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            });
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
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
        ruang.dispose();
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
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptAuditPenangananDarah.jasper","report","::[ Data Audit Penanganan Darah ]::",
                    "select audit_penanganan_darah.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_penanganan_darah.tanggal,audit_penanganan_darah.menggunakan_apd_waktu_membuang_darah,"+
                    "audit_penanganan_darah.komponen_darah_tidak_ada_dilantai,audit_penanganan_darah.membuang_darah_pada_tempat_ditentukan,"+
                    "audit_penanganan_darah.pembersihan_areal_tumbahan_darah,audit_penanganan_darah.apd_dibuang_di_limbah_infeksius,"+
                    "audit_penanganan_darah.melakukan_kebersihan_tangan_setelah_prosedur from audit_penanganan_darah "+
                    "inner join ruang_audit_kepatuhan on audit_penanganan_darah.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_penanganan_darah.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by audit_penanganan_darah.tanggal",param);
            }else{
                Valid.MyReportqry("rptAuditPenangananDarah.jasper","report","::[ Data Audit Penanganan Darah ]::",
                    "select audit_penanganan_darah.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_penanganan_darah.tanggal,audit_penanganan_darah.menggunakan_apd_waktu_membuang_darah,"+
                    "audit_penanganan_darah.komponen_darah_tidak_ada_dilantai,audit_penanganan_darah.membuang_darah_pada_tempat_ditentukan,"+
                    "audit_penanganan_darah.pembersihan_areal_tumbahan_darah,audit_penanganan_darah.apd_dibuang_di_limbah_infeksius,"+
                    "audit_penanganan_darah.melakukan_kebersihan_tangan_setelah_prosedur from audit_penanganan_darah "+
                    "inner join ruang_audit_kepatuhan on audit_penanganan_darah.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_penanganan_darah.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
                    "and (audit_penanganan_darah.id_ruang like '%"+TCari.getText().trim()+"%' or ruang_audit_kepatuhan.nama_ruang like '%"+TCari.getText().trim()+"%') order by audit_penanganan_darah.tanggal",param);
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
            //Valid.pindah(evt, BtnCari, TPasien);
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            MenggunakanApdWaktuMembuangDarah.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_KdRuangKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        //Valid.pindah(evt,Detik,BB);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MenggunakanApdWaktuMembuangDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenggunakanApdWaktuMembuangDarahKeyPressed
        Valid.pindah(evt,btnPetugas,KomponenDarahTidakAdaDilantai);
    }//GEN-LAST:event_MenggunakanApdWaktuMembuangDarahKeyPressed

    private void KomponenDarahTidakAdaDilantaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomponenDarahTidakAdaDilantaiKeyPressed
        Valid.pindah(evt, MenggunakanApdWaktuMembuangDarah, MembuangDarahPadaTempatDitentukan);
    }//GEN-LAST:event_KomponenDarahTidakAdaDilantaiKeyPressed

    private void MembuangDarahPadaTempatDitentukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MembuangDarahPadaTempatDitentukanKeyPressed
        Valid.pindah(evt, KomponenDarahTidakAdaDilantai,PembersihanArealTumbahanDarah);
    }//GEN-LAST:event_MembuangDarahPadaTempatDitentukanKeyPressed

    private void PembersihanArealTumbahanDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembersihanArealTumbahanDarahKeyPressed
        Valid.pindah(evt, MembuangDarahPadaTempatDitentukan,ApdDibuangdiLimbahInfeksius);
    }//GEN-LAST:event_PembersihanArealTumbahanDarahKeyPressed

    private void ApdDibuangdiLimbahInfeksiusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApdDibuangdiLimbahInfeksiusKeyPressed
        Valid.pindah(evt, PembersihanArealTumbahanDarah,MelakukanKebersihanTanganSetelahProsedur);
    }//GEN-LAST:event_ApdDibuangdiLimbahInfeksiusKeyPressed

    private void MelakukanKebersihanTanganSetelahProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MelakukanKebersihanTanganSetelahProsedurKeyPressed
        Valid.pindah(evt, ApdDibuangdiLimbahInfeksius,BtnSimpan);
    }//GEN-LAST:event_MelakukanKebersihanTanganSetelahProsedurKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgAuditPenangananDarah dialog = new DlgAuditPenangananDarah(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ApdDibuangdiLimbahInfeksius;
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
    private widget.TextBox KdRuang;
    private widget.ComboBox KomponenDarahTidakAdaDilantai;
    private widget.Label LCount;
    private widget.ComboBox MelakukanKebersihanTanganSetelahProsedur;
    private widget.ComboBox MembuangDarahPadaTempatDitentukan;
    private widget.ComboBox MenggunakanApdWaktuMembuangDarah;
    private widget.ComboBox Menit;
    private widget.TextBox NmRuang;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PembersihanArealTumbahanDarah;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select audit_penanganan_darah.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_penanganan_darah.tanggal,audit_penanganan_darah.menggunakan_apd_waktu_membuang_darah,"+
                    "audit_penanganan_darah.komponen_darah_tidak_ada_dilantai,audit_penanganan_darah.membuang_darah_pada_tempat_ditentukan,"+
                    "audit_penanganan_darah.pembersihan_areal_tumbahan_darah,audit_penanganan_darah.apd_dibuang_di_limbah_infeksius,"+
                    "audit_penanganan_darah.melakukan_kebersihan_tangan_setelah_prosedur from audit_penanganan_darah "+
                    "inner join ruang_audit_kepatuhan on audit_penanganan_darah.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_penanganan_darah.tanggal between ? and ? order by audit_penanganan_darah.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select audit_penanganan_darah.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_penanganan_darah.tanggal,audit_penanganan_darah.menggunakan_apd_waktu_membuang_darah,"+
                    "audit_penanganan_darah.komponen_darah_tidak_ada_dilantai,audit_penanganan_darah.membuang_darah_pada_tempat_ditentukan,"+
                    "audit_penanganan_darah.pembersihan_areal_tumbahan_darah,audit_penanganan_darah.apd_dibuang_di_limbah_infeksius,"+
                    "audit_penanganan_darah.melakukan_kebersihan_tangan_setelah_prosedur from audit_penanganan_darah "+
                    "inner join ruang_audit_kepatuhan on audit_penanganan_darah.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_penanganan_darah.tanggal between ? and ? "+
                    "and (audit_penanganan_darah.id_ruang like ? or ruang_audit_kepatuhan.nama_ruang like ?) order by audit_penanganan_darah.tanggal");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                ttlmenggunakan_apd_waktu_membuang_darah=0;ttlkomponen_darah_tidak_ada_dilantai=0;ttlmembuang_darah_pada_tempat_ditentukan=0;ttlpembersihan_areal_tumbahan_darah=0;ttlapd_dibuang_di_limbah_infeksius=0;
                ttlmelakukan_kebersihan_tangan_setelah_prosedur=0;ttlpenilaian=0;
                i=1;
                while(rs.next()){
                    menggunakan_apd_waktu_membuang_darah=Double.parseDouble(rs.getString("menggunakan_apd_waktu_membuang_darah").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlmenggunakan_apd_waktu_membuang_darah=ttlmenggunakan_apd_waktu_membuang_darah+menggunakan_apd_waktu_membuang_darah;
                    komponen_darah_tidak_ada_dilantai=Double.parseDouble(rs.getString("komponen_darah_tidak_ada_dilantai").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlkomponen_darah_tidak_ada_dilantai=ttlkomponen_darah_tidak_ada_dilantai+komponen_darah_tidak_ada_dilantai;
                    membuang_darah_pada_tempat_ditentukan=Double.parseDouble(rs.getString("membuang_darah_pada_tempat_ditentukan").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlmembuang_darah_pada_tempat_ditentukan=ttlmembuang_darah_pada_tempat_ditentukan+membuang_darah_pada_tempat_ditentukan;
                    pembersihan_areal_tumbahan_darah=Double.parseDouble(rs.getString("pembersihan_areal_tumbahan_darah").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpembersihan_areal_tumbahan_darah=ttlpembersihan_areal_tumbahan_darah+pembersihan_areal_tumbahan_darah;
                    apd_dibuang_di_limbah_infeksius=Double.parseDouble(rs.getString("apd_dibuang_di_limbah_infeksius").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlapd_dibuang_di_limbah_infeksius=ttlapd_dibuang_di_limbah_infeksius+apd_dibuang_di_limbah_infeksius;
                    melakukan_kebersihan_tangan_setelah_prosedur=Double.parseDouble(rs.getString("melakukan_kebersihan_tangan_setelah_prosedur").replaceAll("Ya","1").replaceAll("Tidak","0"));;
                    ttlmelakukan_kebersihan_tangan_setelah_prosedur=ttlmelakukan_kebersihan_tangan_setelah_prosedur+melakukan_kebersihan_tangan_setelah_prosedur;
                    ttlpenilaian=ttlpenilaian+(((menggunakan_apd_waktu_membuang_darah+komponen_darah_tidak_ada_dilantai+membuang_darah_pada_tempat_ditentukan+
                            pembersihan_areal_tumbahan_darah+apd_dibuang_di_limbah_infeksius+melakukan_kebersihan_tangan_setelah_prosedur)/6)*100);
                    tabMode.addRow(new String[]{
                        rs.getString("tanggal"),rs.getString("id_ruang"),rs.getString("nama_ruang"),rs.getString("menggunakan_apd_waktu_membuang_darah"),rs.getString("komponen_darah_tidak_ada_dilantai"),
                        rs.getString("membuang_darah_pada_tempat_ditentukan"),rs.getString("pembersihan_areal_tumbahan_darah"),rs.getString("apd_dibuang_di_limbah_infeksius"),
                        rs.getString("melakukan_kebersihan_tangan_setelah_prosedur"),Math.round(((menggunakan_apd_waktu_membuang_darah+komponen_darah_tidak_ada_dilantai+
                        membuang_darah_pada_tempat_ditentukan+pembersihan_areal_tumbahan_darah+apd_dibuang_di_limbah_infeksius+melakukan_kebersihan_tangan_setelah_prosedur)/6)*100)+" %"
                    });
                    i++;
                }
                i=i-1;
                if(i>0){
                    tabMode.addRow(new String[]{
                        "","Ya",":",""+ttlmenggunakan_apd_waktu_membuang_darah,""+ttlkomponen_darah_tidak_ada_dilantai,""+ttlmembuang_darah_pada_tempat_ditentukan,
                        ""+ttlpembersihan_areal_tumbahan_darah,""+ttlapd_dibuang_di_limbah_infeksius,""+ttlmelakukan_kebersihan_tangan_setelah_prosedur,""+(ttlmenggunakan_apd_waktu_membuang_darah+
                        ttlkomponen_darah_tidak_ada_dilantai+ttlmembuang_darah_pada_tempat_ditentukan+ttlpembersihan_areal_tumbahan_darah+ttlapd_dibuang_di_limbah_infeksius+ttlmelakukan_kebersihan_tangan_setelah_prosedur)
                    });
                    tabMode.addRow(new String[]{
                        "","Tidak",":",""+(i-ttlmenggunakan_apd_waktu_membuang_darah),""+(i-ttlkomponen_darah_tidak_ada_dilantai),""+(i-ttlmembuang_darah_pada_tempat_ditentukan),
                        ""+(i-ttlpembersihan_areal_tumbahan_darah),""+(i-ttlapd_dibuang_di_limbah_infeksius),""+(i-ttlmelakukan_kebersihan_tangan_setelah_prosedur),""+((i-ttlmenggunakan_apd_waktu_membuang_darah)+
                        (i-ttlkomponen_darah_tidak_ada_dilantai)+(i-ttlmembuang_darah_pada_tempat_ditentukan)+(i-ttlpembersihan_areal_tumbahan_darah)+(i-ttlapd_dibuang_di_limbah_infeksius)+(i-ttlmelakukan_kebersihan_tangan_setelah_prosedur))
                    });
                    tabMode.addRow(new String[]{
                        "","Rata-rata",":",Math.round((ttlmenggunakan_apd_waktu_membuang_darah/i)*100)+" %",Math.round((ttlkomponen_darah_tidak_ada_dilantai/i)*100)+" %",Math.round((ttlmembuang_darah_pada_tempat_ditentukan/i)*100)+" %",
                        Math.round((ttlpembersihan_areal_tumbahan_darah/i)*100)+" %",Math.round((ttlapd_dibuang_di_limbah_infeksius/i)*100)+" %",Math.round((ttlmelakukan_kebersihan_tangan_setelah_prosedur/i)*100)+" %",Math.round(ttlpenilaian/i)+" %"
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
        LCount.setText(""+i);
    }
    
    public void emptTeks() {
        KdRuang.setText("");
        NmRuang.setText("");
        Tanggal.setDate(new Date());
        MenggunakanApdWaktuMembuangDarah.setSelectedIndex(0);
        KomponenDarahTidakAdaDilantai.setSelectedIndex(0);
        MembuangDarahPadaTempatDitentukan.setSelectedIndex(0);
        PembersihanArealTumbahanDarah.setSelectedIndex(0);
        ApdDibuangdiLimbahInfeksius.setSelectedIndex(0);
        MelakukanKebersihanTanganSetelahProsedur.setSelectedIndex(0);
        MenggunakanApdWaktuMembuangDarah.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            if(!tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().equals("")){
                KdRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                NmRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                MenggunakanApdWaktuMembuangDarah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
                KomponenDarahTidakAdaDilantai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
                MembuangDarahPadaTempatDitentukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                PembersihanArealTumbahanDarah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                ApdDibuangdiLimbahInfeksius.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
                MelakukanKebersihanTanganSetelahProsedur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            }
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,154));
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
        BtnSimpan.setEnabled(akses.getaudit_penanganan_darah());
        BtnHapus.setEnabled(akses.getaudit_penanganan_darah());
        BtnEdit.setEnabled(akses.getaudit_penanganan_darah());
        BtnPrint.setEnabled(akses.getaudit_penanganan_darah());         
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
}

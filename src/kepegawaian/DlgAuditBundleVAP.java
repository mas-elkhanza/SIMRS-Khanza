/*
 * Kontribusi dari Muanas RSAD Pelomonia Makasar
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
public final class DlgAuditBundleVAP extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
     private DlgCariRuangAuditKepatuhan ruang=new DlgCariRuangAuditKepatuhan(null,false);
    private double posisi_kepala=0,pengkajian_setiap_hari=0,hand_hygiene=0,oral_hygiene=0,suction_manajemen_sekresi=0,profilaksis_peptic_ulcer=0,dvt_profiklasisi=0,penggunaan_apd_sesuai=0,
                   ttlposisi_kepala=0,ttlpengkajian_setiap_hari=0,ttlhand_hygiene=0,ttloral_hygiene=0,ttlsuction_manajemen_sekresi=0,ttlprofilaksis_peptic_ulcer=0,ttldvt_profiklasisi=0,ttlpenggunaan_apd_sesuai=0,ttlpenilaian=0;
    
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgAuditBundleVAP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Tanggal Audit","ID Ruang","Ruang/Unit","1.Posisi kepala 30° s/d 45⁰",
            "2.Pengkajian setiap hari terhadap sedasi dan extubasi","3.Hand Hygiene","4.Oral Hygiene secara rutin",
            "5.Suction/ Manajemen Sekresi","6.Profilaksis Peptic Ulcer","7.DVT Profilaksisi jika indikasi","8.Penggunaan APD yang sesuai","Ttl.Nilai(%)"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
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
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        Tanggal = new widget.Tanggal();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel14 = new widget.Label();
        PosisiKepala = new widget.ComboBox();
        jLabel17 = new widget.Label();
        PengkajianSetiapHari = new widget.ComboBox();
        jLabel23 = new widget.Label();
        HandHygiene = new widget.ComboBox();
        jLabel28 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        Profilaksis = new widget.ComboBox();
        Suction = new widget.ComboBox();
        jLabel22 = new widget.Label();
        OralHygiene = new widget.ComboBox();
        jLabel24 = new widget.Label();
        PenggunaanApd = new widget.ComboBox();
        jLabel25 = new widget.Label();
        Dvt = new widget.ComboBox();
        jLabel18 = new widget.Label();
        KdRuang = new widget.TextBox();
        NmRuang = new widget.TextBox();
        btnPetugas = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Audit Bundle VAP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2022" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 155));
        FormInput.setLayout(null);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-07-2022" }));
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

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("1.  Posisi kepala 30° s/d 45⁰");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(64, 40, 180, 23);

        PosisiKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PosisiKepala.setName("PosisiKepala"); // NOI18N
        PosisiKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PosisiKepalaKeyPressed(evt);
            }
        });
        FormInput.add(PosisiKepala);
        PosisiKepala.setBounds(250, 40, 78, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2.  Pengkajian setiap hari terhadap sedasi dan extubasi");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(410, 40, 280, 23);

        PengkajianSetiapHari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PengkajianSetiapHari.setName("PengkajianSetiapHari"); // NOI18N
        PengkajianSetiapHari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengkajianSetiapHariKeyPressed(evt);
            }
        });
        FormInput.add(PengkajianSetiapHari);
        PengkajianSetiapHari.setBounds(709, 40, 78, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("3.  Hand Hygiene");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(64, 70, 180, 23);

        HandHygiene.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        HandHygiene.setName("HandHygiene"); // NOI18N
        HandHygiene.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HandHygieneKeyPressed(evt);
            }
        });
        FormInput.add(HandHygiene);
        HandHygiene.setBounds(250, 70, 78, 23);

        jLabel28.setText("Bundles :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 40, 60, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("8.  Penggunaan APD yang sesuai");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(410, 130, 280, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("5.  Suction / Manajemen Sekresi");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(64, 100, 180, 23);

        Profilaksis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Profilaksis.setName("Profilaksis"); // NOI18N
        Profilaksis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfilaksisKeyPressed(evt);
            }
        });
        FormInput.add(Profilaksis);
        Profilaksis.setBounds(709, 100, 78, 23);

        Suction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Suction.setName("Suction"); // NOI18N
        Suction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuctionKeyPressed(evt);
            }
        });
        FormInput.add(Suction);
        Suction.setBounds(250, 100, 78, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("4.  Oral Hygiene secara rutin");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(410, 70, 280, 23);

        OralHygiene.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        OralHygiene.setName("OralHygiene"); // NOI18N
        OralHygiene.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OralHygieneKeyPressed(evt);
            }
        });
        FormInput.add(OralHygiene);
        OralHygiene.setBounds(709, 70, 78, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("6.  Profilaksis Peptic Ulcer");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(410, 100, 280, 23);

        PenggunaanApd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PenggunaanApd.setName("PenggunaanApd"); // NOI18N
        PenggunaanApd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenggunaanApdKeyPressed(evt);
            }
        });
        FormInput.add(PenggunaanApd);
        PenggunaanApd.setBounds(709, 130, 78, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("7.  DVT Profilaksisi jika indikasi");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(64, 130, 180, 23);

        Dvt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Dvt.setName("Dvt"); // NOI18N
        Dvt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DvtKeyPressed(evt);
            }
        });
        FormInput.add(Dvt);
        Dvt.setBounds(250, 130, 78, 23);

        jLabel18.setText("Ruang/Unit Diaudit :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(380, 10, 110, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        FormInput.add(KdRuang);
        KdRuang.setBounds(494, 10, 80, 23);

        NmRuang.setEditable(false);
        NmRuang.setName("NmRuang"); // NOI18N
        FormInput.add(NmRuang);
        NmRuang.setBounds(577, 10, 180, 23);

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
        btnPetugas.setBounds(760, 10, 28, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
   if(KdRuang.getText().trim().equals("")||NmRuang.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Ruang/Unit");
        }else{
            if(Sequel.menyimpantf("audit_bundle_vap","?,?,?,?,?,?,?,?,?,?","Data",10,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),PosisiKepala.getSelectedItem().toString(),
                PengkajianSetiapHari.getSelectedItem().toString(),HandHygiene.getSelectedItem().toString(),OralHygiene.getSelectedItem().toString(),Suction.getSelectedItem().toString(),
                Profilaksis.getSelectedItem().toString(),Dvt.getSelectedItem().toString(),PenggunaanApd.getSelectedItem().toString()
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
            Valid.pindah(evt,Suction,BtnBatal);
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
            if(Sequel.queryu2tf("delete from audit_bundle_vap where id_ruang=? and tanggal=?",2,new String[]{
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
            Sequel.mengedit("audit_bundle_vap","id_ruang=? and tanggal=?","tanggal=?,id_ruang=?,posisi_kepala=?,pengkajian_setiap_hari=?,hand_hygiene=?,oral_hygiene=?,suction_manajemen_sekresi=?,profilaksis_peptic_ulcer=?,dvt_profiklasisi=?,penggunaan_apd_sesuai=?",12,new String[]{
                 Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),PosisiKepala.getSelectedItem().toString(),PengkajianSetiapHari.getSelectedItem().toString(),HandHygiene.getSelectedItem().toString(),
                OralHygiene.getSelectedItem().toString(),Suction.getSelectedItem().toString(),Profilaksis.getSelectedItem().toString(),Dvt.getSelectedItem().toString(),PenggunaanApd.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
                Valid.MyReportqry("rptAuditBundleVAP.jasper","report","::[ Data Audit Bundle VAP ]::",
                    "select audit_bundle_vap.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_vap.tanggal,audit_bundle_vap.posisi_kepala,"+
                    "audit_bundle_vap.pengkajian_setiap_hari,audit_bundle_vap.hand_hygiene,audit_bundle_vap.oral_hygiene,audit_bundle_vap.suction_manajemen_sekresi,"+
                    "audit_bundle_vap.profilaksis_peptic_ulcer,audit_bundle_vap.dvt_profiklasisi,audit_bundle_vap.penggunaan_apd_sesuai from audit_bundle_vap "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_vap.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_bundle_vap.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by audit_bundle_vap.tanggal",param);
            }else{
                Valid.MyReportqry("rptAuditBundleVAP.jasper","report","::[ Data Audit Bundle VAP ]::",
                    "select audit_bundle_vap.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_vap.tanggal,audit_bundle_vap.posisi_kepala,"+
                    "audit_bundle_vap.pengkajian_setiap_hari,audit_bundle_vap.hand_hygiene,audit_bundle_vap.oral_hygiene,audit_bundle_vap.suction_manajemen_sekresi,"+
                    "audit_bundle_vap.profilaksis_peptic_ulcer,audit_bundle_vap.dvt_profiklasisi,audit_bundle_vap.penggunaan_apd_sesuai from audit_bundle_vap "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_vap.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_bundle_vap.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
                    "and (audit_bundle_vap.id_ruang like '%"+TCari.getText().trim()+"%' or ruang_audit_kepatuhan.nama_ruang like '%"+TCari.getText().trim()+"%') order by audit_bundle_vap.tanggal",param);
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

    private void PosisiKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PosisiKepalaKeyPressed
        Valid.pindah(evt,btnPetugas,PengkajianSetiapHari);
    }//GEN-LAST:event_PosisiKepalaKeyPressed

    private void PengkajianSetiapHariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengkajianSetiapHariKeyPressed
        Valid.pindah(evt, PosisiKepala, HandHygiene);
    }//GEN-LAST:event_PengkajianSetiapHariKeyPressed

    private void HandHygieneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HandHygieneKeyPressed
        Valid.pindah(evt, PengkajianSetiapHari,Profilaksis);
    }//GEN-LAST:event_HandHygieneKeyPressed

    private void ProfilaksisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfilaksisKeyPressed
        Valid.pindah(evt, HandHygiene,Suction);
    }//GEN-LAST:event_ProfilaksisKeyPressed

    private void SuctionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuctionKeyPressed
        Valid.pindah(evt, Profilaksis,BtnSimpan);
    }//GEN-LAST:event_SuctionKeyPressed

    private void OralHygieneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OralHygieneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_OralHygieneKeyPressed

    private void PenggunaanApdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenggunaanApdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenggunaanApdKeyPressed

    private void DvtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DvtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DvtKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            PosisiKepala.requestFocus();
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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgAuditBundleVAP dialog = new DlgAuditBundleVAP(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Dvt;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HandHygiene;
    private widget.ComboBox Jam;
    private widget.TextBox KdRuang;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private widget.TextBox NmRuang;
    private widget.ComboBox OralHygiene;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PenggunaanApd;
    private widget.ComboBox PengkajianSetiapHari;
    private widget.ComboBox PosisiKepala;
    private widget.ComboBox Profilaksis;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Suction;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel28;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select audit_bundle_vap.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_vap.tanggal,audit_bundle_vap.posisi_kepala,"+
                    "audit_bundle_vap.pengkajian_setiap_hari,audit_bundle_vap.hand_hygiene,audit_bundle_vap.oral_hygiene,audit_bundle_vap.suction_manajemen_sekresi,"+
                    "audit_bundle_vap.profilaksis_peptic_ulcer,audit_bundle_vap.dvt_profiklasisi,audit_bundle_vap.penggunaan_apd_sesuai from audit_bundle_vap "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_vap.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_bundle_vap.tanggal between ? and ? order by audit_bundle_vap.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select audit_bundle_vap.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_vap.tanggal,audit_bundle_vap.posisi_kepala,"+
                    "audit_bundle_vap.pengkajian_setiap_hari,audit_bundle_vap.hand_hygiene,audit_bundle_vap.oral_hygiene,audit_bundle_vap.suction_manajemen_sekresi,"+
                    "audit_bundle_vap.profilaksis_peptic_ulcer,audit_bundle_vap.dvt_profiklasisi,audit_bundle_vap.penggunaan_apd_sesuai from audit_bundle_vap "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_vap.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_bundle_vap.tanggal between ? and ? "+
                    "and (audit_bundle_vap.id_ruang like ? or ruang_audit_kepatuhan.nama_ruang like ?) order by audit_bundle_vap.tanggal");
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
                ttlposisi_kepala=0;ttlpengkajian_setiap_hari=0;ttlhand_hygiene=0;ttloral_hygiene=0;ttlsuction_manajemen_sekresi=0;ttlprofilaksis_peptic_ulcer=0;ttldvt_profiklasisi=0;ttlpenggunaan_apd_sesuai=0;ttlpenilaian=0;
                i=1;
                while(rs.next()){
                    posisi_kepala=Double.parseDouble(rs.getString("posisi_kepala").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlposisi_kepala=ttlposisi_kepala+posisi_kepala;
                    pengkajian_setiap_hari=Double.parseDouble(rs.getString("pengkajian_setiap_hari").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpengkajian_setiap_hari=ttlpengkajian_setiap_hari+pengkajian_setiap_hari;
                    hand_hygiene=Double.parseDouble(rs.getString("hand_hygiene").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlhand_hygiene=ttlhand_hygiene+hand_hygiene;
                    oral_hygiene=Double.parseDouble(rs.getString("oral_hygiene").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttloral_hygiene=ttloral_hygiene+oral_hygiene;
                    suction_manajemen_sekresi=Double.parseDouble(rs.getString("suction_manajemen_sekresi").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlsuction_manajemen_sekresi=ttlsuction_manajemen_sekresi+suction_manajemen_sekresi;
                    profilaksis_peptic_ulcer=Double.parseDouble(rs.getString("profilaksis_peptic_ulcer").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlprofilaksis_peptic_ulcer=ttlprofilaksis_peptic_ulcer+profilaksis_peptic_ulcer;
                    dvt_profiklasisi=Double.parseDouble(rs.getString("dvt_profiklasisi").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttldvt_profiklasisi=ttldvt_profiklasisi+dvt_profiklasisi;
                    penggunaan_apd_sesuai=Double.parseDouble(rs.getString("penggunaan_apd_sesuai").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpenggunaan_apd_sesuai=ttlpenggunaan_apd_sesuai+penggunaan_apd_sesuai;
                    ttlpenilaian=ttlpenilaian+(((posisi_kepala+pengkajian_setiap_hari+hand_hygiene+oral_hygiene+suction_manajemen_sekresi+profilaksis_peptic_ulcer+dvt_profiklasisi+penggunaan_apd_sesuai)/8)*100);
                    tabMode.addRow(new String[]{
                        rs.getString("tanggal"),rs.getString("id_ruang"),rs.getString("nama_ruang"),rs.getString("posisi_kepala"),rs.getString("pengkajian_setiap_hari"),
                        rs.getString("hand_hygiene"),rs.getString("oral_hygiene"),rs.getString("suction_manajemen_sekresi"),rs.getString("profilaksis_peptic_ulcer"),rs.getString("dvt_profiklasisi"),rs.getString("penggunaan_apd_sesuai"),
                        Math.round(((posisi_kepala+pengkajian_setiap_hari+hand_hygiene+oral_hygiene+suction_manajemen_sekresi+profilaksis_peptic_ulcer+dvt_profiklasisi+penggunaan_apd_sesuai)/8)*100)+" %"
                    });
                    i++;
                }
                i=i-1;
                if(i>0){
                    tabMode.addRow(new String[]{
                        "","Ya",":",""+ttlposisi_kepala,""+ttlpengkajian_setiap_hari,""+ttlhand_hygiene,""+ttloral_hygiene,""+ttlsuction_manajemen_sekresi,""+ttlprofilaksis_peptic_ulcer,""+ttldvt_profiklasisi,""+ttlpenggunaan_apd_sesuai,""+(posisi_kepala+pengkajian_setiap_hari+hand_hygiene+oral_hygiene+suction_manajemen_sekresi+profilaksis_peptic_ulcer+dvt_profiklasisi+penggunaan_apd_sesuai)
                    });
                    tabMode.addRow(new String[]{
                        "","Tidak",":",""+(i-ttlposisi_kepala),""+(i-ttlpengkajian_setiap_hari),""+(i-ttlhand_hygiene),""+(i-ttloral_hygiene),""+(i-ttlsuction_manajemen_sekresi),""+(i-ttlprofilaksis_peptic_ulcer),""+(i-ttldvt_profiklasisi),""+(i-ttlpenggunaan_apd_sesuai),""+((i-ttlposisi_kepala)+
                        (i-ttlpengkajian_setiap_hari)+(i-ttlhand_hygiene)+(i-ttloral_hygiene)+(i-ttlsuction_manajemen_sekresi)+(i-ttlprofilaksis_peptic_ulcer)+(i-ttldvt_profiklasisi)+(i-ttlpenggunaan_apd_sesuai))
                    });
                    tabMode.addRow(new String[]{
                        "","Rata-rata",":",Math.round((ttlposisi_kepala/i)*100)+" %",Math.round((ttlpengkajian_setiap_hari/i)*100)+" %",Math.round((ttlhand_hygiene/i)*100)+" %",
                        Math.round((ttloral_hygiene/i)*100)+" %",Math.round((ttlsuction_manajemen_sekresi/i)*100)+" %",Math.round((ttlprofilaksis_peptic_ulcer/i)*100)+" %",Math.round((ttldvt_profiklasisi/i)*100)+" %",Math.round((ttlpenggunaan_apd_sesuai/i)*100)+" %",Math.round(ttlpenilaian/i)+" %"
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
        PosisiKepala.setSelectedIndex(0);
        PengkajianSetiapHari.setSelectedIndex(0);
        HandHygiene.setSelectedIndex(0);
        OralHygiene.setSelectedIndex(0);
        Suction.setSelectedIndex(0);
        Profilaksis.setSelectedIndex(0);
        Dvt.setSelectedIndex(0);
        PenggunaanApd.setSelectedIndex(0);
        PosisiKepala.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            if(!tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().equals("")){
                KdRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                NmRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                PosisiKepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
                PengkajianSetiapHari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
                HandHygiene.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                OralHygiene.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                Suction.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
                Profilaksis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                Dvt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
                PenggunaanApd.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
                Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            }
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            if(this.getHeight()>320){
                PanelInput.setPreferredSize(new Dimension(WIDTH,185));
            }else{
                PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
            }
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
        /*BtnSimpan.setEnabled(akses.getaudit_bundle_vap());
        BtnHapus.setEnabled(akses.getaudit_bundle_vap());
        BtnEdit.setEnabled(akses.getaudit_bundle_vap());
        BtnPrint.setEnabled(akses.getaudit_bundle_vap());   */      
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

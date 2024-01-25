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
public final class DlgAuditBundleISK extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariRuangAuditKepatuhan ruang=new DlgCariRuangAuditKepatuhan(null,false);
    private double pemasangan_sesuai_indikasi=0,hand_hygiene=0,menggunakan_apd_yang_tepat=0,pemasangan_menggunakan_alat_steril=0,segera_dilepas_setelah_tidak_diperlukan=0,pengisian_balon_sesuai_petunjuk=0,fiksasi_kateter_dengan_plester=0,urinebag_menggantung_tidak_menyentuh_lantai=0,
                ttlpemasangan_sesuai_indikasi=0,ttlhand_hygiene=0,ttlmenggunakan_apd_yang_tepat=0,ttlpemasangan_menggunakan_alat_steril=0,ttlsegera_dilepas_setelah_tidak_diperlukan=0,ttlpengisian_balon_sesuai_petunjuk=0,ttlfiksasi_kateter_dengan_plester=0,ttlurinebag_menggantung_tidak_menyentuh_lantai=0,ttlpenilaian=0;
    
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgAuditBundleISK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Tanggal Audit","ID Ruang","Ruang/Unit","1.Pemasangan sesuai indikasi",
            "2.Hand Hygiene","3.Menggunakan APD yang tepat","4.Pemasangan menggunakan alat steril","5.Segera dilepas setelah tdk diperlukan",
            "6.Pengisian balon sesuai petunjuk produk (20 ml)","7.Fiksasi kateter dengan plester","8.Urine bag menggantung tidak menyentuh lantai","Ttl.Nilai(%)"
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
        FormInput = new widget.PanelBiasa();
        Tanggal = new widget.Tanggal();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel14 = new widget.Label();
        PemasanganSesuaiIndikasi = new widget.ComboBox();
        jLabel17 = new widget.Label();
        HandHygiene = new widget.ComboBox();
        jLabel23 = new widget.Label();
        ApdYangTepat = new widget.ComboBox();
        jLabel28 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        PetunjukProduk = new widget.ComboBox();
        LepasSetelahTidakDiperlukan = new widget.ComboBox();
        jLabel22 = new widget.Label();
        AlatSteril = new widget.ComboBox();
        jLabel24 = new widget.Label();
        UrineBag = new widget.ComboBox();
        jLabel25 = new widget.Label();
        FiksasiKateter = new widget.ComboBox();
        jLabel18 = new widget.Label();
        KdRuang = new widget.TextBox();
        NmRuang = new widget.TextBox();
        btnPetugas = new widget.Button();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Audit Bundle ISK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-07-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-07-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(130, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 184));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-07-2022" }));
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
        jLabel14.setText("1.  Pemasangan sesuai indikasi");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(64, 40, 210, 23);

        PemasanganSesuaiIndikasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PemasanganSesuaiIndikasi.setName("PemasanganSesuaiIndikasi"); // NOI18N
        PemasanganSesuaiIndikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemasanganSesuaiIndikasiKeyPressed(evt);
            }
        });
        FormInput.add(PemasanganSesuaiIndikasi);
        PemasanganSesuaiIndikasi.setBounds(290, 40, 78, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2.  Hand Hygiene");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(430, 40, 270, 23);

        HandHygiene.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        HandHygiene.setName("HandHygiene"); // NOI18N
        HandHygiene.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HandHygieneKeyPressed(evt);
            }
        });
        FormInput.add(HandHygiene);
        HandHygiene.setBounds(709, 40, 78, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("3.  Menggunakan APD yang tepat");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(64, 70, 210, 23);

        ApdYangTepat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ApdYangTepat.setName("ApdYangTepat"); // NOI18N
        ApdYangTepat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApdYangTepatKeyPressed(evt);
            }
        });
        FormInput.add(ApdYangTepat);
        ApdYangTepat.setBounds(290, 70, 78, 23);

        jLabel28.setText("Bundles :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 40, 60, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("8.  Urine bag menggantung tidak menyentuh lantai");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(430, 130, 270, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("5.  Segera dilepas setelah tdk diperlukan");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(64, 100, 210, 23);

        PetunjukProduk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PetunjukProduk.setName("PetunjukProduk"); // NOI18N
        PetunjukProduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PetunjukProdukKeyPressed(evt);
            }
        });
        FormInput.add(PetunjukProduk);
        PetunjukProduk.setBounds(709, 70, 78, 23);

        LepasSetelahTidakDiperlukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LepasSetelahTidakDiperlukan.setName("LepasSetelahTidakDiperlukan"); // NOI18N
        LepasSetelahTidakDiperlukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LepasSetelahTidakDiperlukanKeyPressed(evt);
            }
        });
        FormInput.add(LepasSetelahTidakDiperlukan);
        LepasSetelahTidakDiperlukan.setBounds(290, 100, 78, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("4.  Pemasangan menggunakan alat steril");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(430, 70, 270, 23);

        AlatSteril.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        AlatSteril.setName("AlatSteril"); // NOI18N
        AlatSteril.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatSterilKeyPressed(evt);
            }
        });
        FormInput.add(AlatSteril);
        AlatSteril.setBounds(709, 100, 78, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("6.  Pengisian balon sesuai petunjuk produk (20 ml)");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(430, 100, 270, 23);

        UrineBag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        UrineBag.setName("UrineBag"); // NOI18N
        UrineBag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineBagKeyPressed(evt);
            }
        });
        FormInput.add(UrineBag);
        UrineBag.setBounds(709, 130, 78, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("7.  Fiksasi kateter dengan plester");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(64, 130, 210, 23);

        FiksasiKateter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        FiksasiKateter.setName("FiksasiKateter"); // NOI18N
        FiksasiKateter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FiksasiKateterKeyPressed(evt);
            }
        });
        FormInput.add(FiksasiKateter);
        FiksasiKateter.setBounds(290, 130, 78, 23);

        jLabel18.setText("Ruang/Unit Diaudit :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(390, 10, 110, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        FormInput.add(KdRuang);
        KdRuang.setBounds(504, 10, 80, 23);

        NmRuang.setEditable(false);
        NmRuang.setName("NmRuang"); // NOI18N
        FormInput.add(NmRuang);
        NmRuang.setBounds(587, 10, 200, 23);

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
        btnPetugas.setBounds(790, 10, 28, 23);

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
            if(Sequel.menyimpantf("audit_bundle_isk","?,?,?,?,?,?,?,?,?,?","Data",10,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),PemasanganSesuaiIndikasi.getSelectedItem().toString(),
                HandHygiene.getSelectedItem().toString(),ApdYangTepat.getSelectedItem().toString(),PetunjukProduk.getSelectedItem().toString(),LepasSetelahTidakDiperlukan.getSelectedItem().toString(),
                AlatSteril.getSelectedItem().toString(),FiksasiKateter.getSelectedItem().toString(),UrineBag.getSelectedItem().toString()
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
            Valid.pindah(evt,UrineBag,BtnBatal);
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
            if(Sequel.queryu2tf("delete from audit_bundle_isk where id_ruang=? and tanggal=?",2,new String[]{
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
            Sequel.mengedit("audit_bundle_isk","id_ruang=? and tanggal=?","tanggal=?,id_ruang=?,pemasangan_sesuai_indikasi=?,hand_hygiene=?,menggunakan_apd_yang_tepat=?,pemasangan_menggunakan_alat_steril=?,segera_dilepas_setelah_tidak_diperlukan=?,pengisian_balon_sesuai_petunjuk=?,fiksasi_kateter_dengan_plester=?,urinebag_menggantung_tidak_menyentuh_lantai=?",12,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),KdRuang.getText(),PemasanganSesuaiIndikasi.getSelectedItem().toString(),HandHygiene.getSelectedItem().toString(),ApdYangTepat.getSelectedItem().toString(),
                PetunjukProduk.getSelectedItem().toString(),LepasSetelahTidakDiperlukan.getSelectedItem().toString(),AlatSteril.getSelectedItem().toString(),FiksasiKateter.getSelectedItem().toString(),UrineBag.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
                Valid.MyReportqry("rptAuditBundleISK.jasper","report","::[ Data Audit Bundle ISK ]::",
                    "select audit_bundle_isk.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_isk.tanggal,audit_bundle_isk.pemasangan_sesuai_indikasi,"+
                    "audit_bundle_isk.hand_hygiene,audit_bundle_isk.menggunakan_apd_yang_tepat,audit_bundle_isk.pemasangan_menggunakan_alat_steril,audit_bundle_isk.segera_dilepas_setelah_tidak_diperlukan,"+
                    "audit_bundle_isk.pengisian_balon_sesuai_petunjuk,audit_bundle_isk.fiksasi_kateter_dengan_plester,audit_bundle_isk.urinebag_menggantung_tidak_menyentuh_lantai from audit_bundle_isk "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_isk.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_bundle_isk.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' order by audit_bundle_isk.tanggal",param);
            }else{
                Valid.MyReportqry("rptAuditBundleISK.jasper","report","::[ Data Audit Bundle ISK ]::",
                    "select audit_bundle_isk.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_isk.tanggal,audit_bundle_isk.pemasangan_sesuai_indikasi,"+
                    "audit_bundle_isk.hand_hygiene,audit_bundle_isk.menggunakan_apd_yang_tepat,audit_bundle_isk.pemasangan_menggunakan_alat_steril,audit_bundle_isk.segera_dilepas_setelah_tidak_diperlukan,"+
                    "audit_bundle_isk.pengisian_balon_sesuai_petunjuk,audit_bundle_isk.fiksasi_kateter_dengan_plester,audit_bundle_isk.urinebag_menggantung_tidak_menyentuh_lantai from audit_bundle_isk "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_isk.id_ruang=ruang_audit_kepatuhan.id_ruang where audit_bundle_isk.tanggal between "+
                    "'"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
                    "and (audit_bundle_isk.id_ruang like '%"+TCari.getText().trim()+"%' or ruang_audit_kepatuhan.nama_ruang like '%"+TCari.getText().trim()+"%') order by audit_bundle_isk.tanggal",param);
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

    private void PemasanganSesuaiIndikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemasanganSesuaiIndikasiKeyPressed
        Valid.pindah(evt,btnPetugas,HandHygiene);
    }//GEN-LAST:event_PemasanganSesuaiIndikasiKeyPressed

    private void HandHygieneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HandHygieneKeyPressed
        Valid.pindah(evt, PemasanganSesuaiIndikasi, ApdYangTepat);
    }//GEN-LAST:event_HandHygieneKeyPressed

    private void ApdYangTepatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApdYangTepatKeyPressed
        Valid.pindah(evt, HandHygiene,PetunjukProduk);
    }//GEN-LAST:event_ApdYangTepatKeyPressed

    private void PetunjukProdukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PetunjukProdukKeyPressed
        Valid.pindah(evt, ApdYangTepat,LepasSetelahTidakDiperlukan);
    }//GEN-LAST:event_PetunjukProdukKeyPressed

    private void LepasSetelahTidakDiperlukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LepasSetelahTidakDiperlukanKeyPressed
        Valid.pindah(evt, PetunjukProduk,BtnSimpan);
    }//GEN-LAST:event_LepasSetelahTidakDiperlukanKeyPressed

    private void AlatSterilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatSterilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatSterilKeyPressed

    private void UrineBagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrineBagKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrineBagKeyPressed

    private void FiksasiKateterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FiksasiKateterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FiksasiKateterKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            PemasanganSesuaiIndikasi.requestFocus();
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
            DlgAuditBundleISK dialog = new DlgAuditBundleISK(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AlatSteril;
    private widget.ComboBox ApdYangTepat;
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
    private widget.ComboBox FiksasiKateter;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HandHygiene;
    private widget.ComboBox Jam;
    private widget.TextBox KdRuang;
    private widget.Label LCount;
    private widget.ComboBox LepasSetelahTidakDiperlukan;
    private widget.ComboBox Menit;
    private widget.TextBox NmRuang;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PemasanganSesuaiIndikasi;
    private widget.ComboBox PetunjukProduk;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.ComboBox UrineBag;
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
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
      Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select audit_bundle_isk.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_isk.tanggal,audit_bundle_isk.pemasangan_sesuai_indikasi,"+
                    "audit_bundle_isk.hand_hygiene,audit_bundle_isk.menggunakan_apd_yang_tepat,audit_bundle_isk.pemasangan_menggunakan_alat_steril,audit_bundle_isk.segera_dilepas_setelah_tidak_diperlukan,"+
                    "audit_bundle_isk.pengisian_balon_sesuai_petunjuk,audit_bundle_isk.fiksasi_kateter_dengan_plester,audit_bundle_isk.urinebag_menggantung_tidak_menyentuh_lantai from audit_bundle_isk "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_isk.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_bundle_isk.tanggal between ? and ? order by audit_bundle_isk.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select audit_bundle_isk.id_ruang,ruang_audit_kepatuhan.nama_ruang,audit_bundle_isk.tanggal,audit_bundle_isk.pemasangan_sesuai_indikasi,"+
                    "audit_bundle_isk.hand_hygiene,audit_bundle_isk.menggunakan_apd_yang_tepat,audit_bundle_isk.pemasangan_menggunakan_alat_steril,audit_bundle_isk.segera_dilepas_setelah_tidak_diperlukan,"+
                    "audit_bundle_isk.pengisian_balon_sesuai_petunjuk,audit_bundle_isk.fiksasi_kateter_dengan_plester,audit_bundle_isk.urinebag_menggantung_tidak_menyentuh_lantai from audit_bundle_isk "+
                    "inner join ruang_audit_kepatuhan on audit_bundle_isk.id_ruang=ruang_audit_kepatuhan.id_ruang "+
                    "where audit_bundle_isk.tanggal between ? and ? "+
                    "and (audit_bundle_isk.id_ruang like ? or ruang_audit_kepatuhan.nama_ruang like ?) order by audit_bundle_isk.tanggal");
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
                ttlpemasangan_sesuai_indikasi=0;ttlhand_hygiene=0;ttlmenggunakan_apd_yang_tepat=0;ttlpemasangan_menggunakan_alat_steril=0;ttlsegera_dilepas_setelah_tidak_diperlukan=0;ttlpengisian_balon_sesuai_petunjuk=0;ttlfiksasi_kateter_dengan_plester=0;ttlurinebag_menggantung_tidak_menyentuh_lantai=0;ttlpenilaian=0;
                i=1;
                while(rs.next()){
                    pemasangan_sesuai_indikasi=Double.parseDouble(rs.getString("pemasangan_sesuai_indikasi").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpemasangan_sesuai_indikasi=ttlpemasangan_sesuai_indikasi+pemasangan_sesuai_indikasi;
                    hand_hygiene=Double.parseDouble(rs.getString("hand_hygiene").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlhand_hygiene=ttlhand_hygiene+hand_hygiene;
                    menggunakan_apd_yang_tepat=Double.parseDouble(rs.getString("menggunakan_apd_yang_tepat").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlmenggunakan_apd_yang_tepat=ttlmenggunakan_apd_yang_tepat+menggunakan_apd_yang_tepat;
                    pemasangan_menggunakan_alat_steril=Double.parseDouble(rs.getString("pemasangan_menggunakan_alat_steril").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpemasangan_menggunakan_alat_steril=ttlpemasangan_menggunakan_alat_steril+pemasangan_menggunakan_alat_steril;
                    segera_dilepas_setelah_tidak_diperlukan=Double.parseDouble(rs.getString("segera_dilepas_setelah_tidak_diperlukan").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlsegera_dilepas_setelah_tidak_diperlukan=ttlsegera_dilepas_setelah_tidak_diperlukan+segera_dilepas_setelah_tidak_diperlukan;
                    pengisian_balon_sesuai_petunjuk=Double.parseDouble(rs.getString("pengisian_balon_sesuai_petunjuk").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlpengisian_balon_sesuai_petunjuk=ttlpengisian_balon_sesuai_petunjuk+pengisian_balon_sesuai_petunjuk;
                    fiksasi_kateter_dengan_plester=Double.parseDouble(rs.getString("fiksasi_kateter_dengan_plester").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlfiksasi_kateter_dengan_plester=ttlfiksasi_kateter_dengan_plester+fiksasi_kateter_dengan_plester;
                    urinebag_menggantung_tidak_menyentuh_lantai=Double.parseDouble(rs.getString("urinebag_menggantung_tidak_menyentuh_lantai").replaceAll("Ya","1").replaceAll("Tidak","0"));
                    ttlurinebag_menggantung_tidak_menyentuh_lantai=ttlurinebag_menggantung_tidak_menyentuh_lantai+urinebag_menggantung_tidak_menyentuh_lantai;
                    ttlpenilaian=ttlpenilaian+(((pemasangan_sesuai_indikasi+hand_hygiene+menggunakan_apd_yang_tepat+pemasangan_menggunakan_alat_steril+segera_dilepas_setelah_tidak_diperlukan+pengisian_balon_sesuai_petunjuk+fiksasi_kateter_dengan_plester+urinebag_menggantung_tidak_menyentuh_lantai)/8)*100);
                    tabMode.addRow(new String[]{
                        rs.getString("tanggal"),rs.getString("id_ruang"),rs.getString("nama_ruang"),rs.getString("pemasangan_sesuai_indikasi"),rs.getString("hand_hygiene"),
                        rs.getString("menggunakan_apd_yang_tepat"),rs.getString("pemasangan_menggunakan_alat_steril"),rs.getString("segera_dilepas_setelah_tidak_diperlukan"),rs.getString("pengisian_balon_sesuai_petunjuk"),rs.getString("fiksasi_kateter_dengan_plester"),rs.getString("urinebag_menggantung_tidak_menyentuh_lantai"),
                        Math.round(((pemasangan_sesuai_indikasi+hand_hygiene+menggunakan_apd_yang_tepat+pemasangan_menggunakan_alat_steril+segera_dilepas_setelah_tidak_diperlukan+pengisian_balon_sesuai_petunjuk+fiksasi_kateter_dengan_plester+urinebag_menggantung_tidak_menyentuh_lantai)/8)*100)+" %"
                    });
                    i++;
                }
                i=i-1;
                if(i>0){
                    tabMode.addRow(new String[]{
                        "","Ya",":",""+ttlpemasangan_sesuai_indikasi,""+ttlhand_hygiene,""+ttlmenggunakan_apd_yang_tepat,""+ttlpemasangan_menggunakan_alat_steril,""+ttlsegera_dilepas_setelah_tidak_diperlukan,""+ttlpengisian_balon_sesuai_petunjuk,""+ttlfiksasi_kateter_dengan_plester,""+ttlurinebag_menggantung_tidak_menyentuh_lantai,""+(pemasangan_sesuai_indikasi+hand_hygiene+menggunakan_apd_yang_tepat+pemasangan_menggunakan_alat_steril+segera_dilepas_setelah_tidak_diperlukan+pengisian_balon_sesuai_petunjuk+fiksasi_kateter_dengan_plester+urinebag_menggantung_tidak_menyentuh_lantai)
                    });
                    tabMode.addRow(new String[]{
                        "","Tidak",":",""+(i-ttlpemasangan_sesuai_indikasi),""+(i-ttlhand_hygiene),""+(i-ttlmenggunakan_apd_yang_tepat),""+(i-ttlpemasangan_menggunakan_alat_steril),""+(i-ttlsegera_dilepas_setelah_tidak_diperlukan),""+(i-ttlpengisian_balon_sesuai_petunjuk),""+(i-ttlfiksasi_kateter_dengan_plester),""+(i-ttlurinebag_menggantung_tidak_menyentuh_lantai),""+((i-ttlpemasangan_sesuai_indikasi)+
                        (i-ttlhand_hygiene)+(i-ttlmenggunakan_apd_yang_tepat)+(i-ttlpemasangan_menggunakan_alat_steril)+(i-ttlsegera_dilepas_setelah_tidak_diperlukan)+(i-ttlpengisian_balon_sesuai_petunjuk)+(i-ttlfiksasi_kateter_dengan_plester)+(i-ttlurinebag_menggantung_tidak_menyentuh_lantai))
                    });
                    tabMode.addRow(new String[]{
                        "","Rata-rata",":",Math.round((ttlpemasangan_sesuai_indikasi/i)*100)+" %",Math.round((ttlhand_hygiene/i)*100)+" %",Math.round((ttlmenggunakan_apd_yang_tepat/i)*100)+" %",
                        Math.round((ttlpemasangan_menggunakan_alat_steril/i)*100)+" %",Math.round((ttlsegera_dilepas_setelah_tidak_diperlukan/i)*100)+" %",Math.round((ttlpengisian_balon_sesuai_petunjuk/i)*100)+" %",Math.round((ttlfiksasi_kateter_dengan_plester/i)*100)+" %",Math.round((ttlurinebag_menggantung_tidak_menyentuh_lantai/i)*100)+" %",Math.round(ttlpenilaian/i)+" %"
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
        PemasanganSesuaiIndikasi.setSelectedIndex(0);
        HandHygiene.setSelectedIndex(0);
        ApdYangTepat.setSelectedIndex(0);
        PetunjukProduk.setSelectedIndex(0);
        LepasSetelahTidakDiperlukan.setSelectedIndex(0);
        AlatSteril.setSelectedIndex(0);
        FiksasiKateter.setSelectedIndex(0);
        UrineBag.setSelectedIndex(0);
        PemasanganSesuaiIndikasi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            if(!tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().equals("")){
                KdRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                NmRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                PemasanganSesuaiIndikasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
                HandHygiene.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
                ApdYangTepat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                PetunjukProduk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
                LepasSetelahTidakDiperlukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
                AlatSteril.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                FiksasiKateter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
                UrineBag.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
                Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            }
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,184));
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
        BtnSimpan.setEnabled(akses.getaudit_bundle_isk());
        BtnHapus.setEnabled(akses.getaudit_bundle_isk());
        BtnEdit.setEnabled(akses.getaudit_bundle_isk());
        BtnPrint.setEnabled(akses.getaudit_bundle_isk());        
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

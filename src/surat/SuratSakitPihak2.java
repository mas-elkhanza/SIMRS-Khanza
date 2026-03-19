/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * kontribusi dari dokter Salim Mulyana
 */

package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 * 
 * @author salimmulyana
 */
public final class SuratSakitPihak2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String tgl,bln_angka="", bln_romawi="",finger="",kodedokter="",namadokter="";
    private Date lahir;
    private LocalDate today=LocalDate.now();
    private LocalDate birthday;
    private Period p;
    
    public SuratSakitPihak2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Nomor Surat","No.Rawat","No.R.M.","Nama Pasien","Dari Tanggal","Sampai",
            "Lama Sakit","Nama Pihak ke 2","Lahir","Umur","J.K.","Alamat",
            "Hubungan","Pekerjaan","Instansi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(155);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(170);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoSurat.setDocument(new batasInput((byte)17).getKata(NoSurat));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        LamaSakit.setDocument(new batasInput((byte)20).getKata(LamaSakit));         
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NamaPihak2.setDocument(new batasInput((byte)50).getKata(NamaPihak2));
        AlamatPj.setDocument(new batasInput((int)200).getKata(AlamatPj));  
        Instansi.setDocument(new batasInput((byte)50).getKata(Instansi));  
        TUmurTh.setDocument(new batasInput((byte)4).getKata(TUmurTh));    
        TUmurBl.setDocument(new batasInput((byte)4).getKata(TUmurBl));    
        TUmurHr.setDocument(new batasInput((byte)4).getKata(TUmurHr));  
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
        MnCetakSuratSakit2 = new javax.swing.JMenuItem();
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
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
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
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        NoSurat = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TanggalAkhir = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        LamaSakit = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        TglLahir = new widget.Tanggal();
        jLabel13 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        NamaPihak2 = new widget.TextBox();
        jLabel9 = new widget.Label();
        cmbJk = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        cmbHubungan = new widget.ComboBox();
        jLabel12 = new widget.Label();
        AlamatPj = new widget.TextBox();
        jLabel14 = new widget.Label();
        Instansi = new widget.TextBox();
        jLabel15 = new widget.Label();
        TanggalAwal = new widget.Tanggal();
        cmbPekerjaan = new widget.ComboBox();
        jLabel44 = new widget.Label();
        TUmurTh = new widget.TextBox();
        jLabel31 = new widget.Label();
        TUmurBl = new widget.TextBox();
        jLabel29 = new widget.Label();
        TUmurHr = new widget.TextBox();
        jLabel30 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratSakit2.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSakit2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit2.setText("Cetak Surat Keterangan Sakit Pihak Ke 2");
        MnCetakSuratSakit2.setName("MnCetakSuratSakit2"); // NOI18N
        MnCetakSuratSakit2.setPreferredSize(new java.awt.Dimension(245, 26));
        MnCetakSuratSakit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSakit2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Keterangan Sakit Pihak Ke 2 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 186));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(104, 40, 136, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 100, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(355, 10, 365, 23);

        TanggalAkhir.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2022" }));
        TanggalAkhir.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir.setName("TanggalAkhir"); // NOI18N
        TanggalAkhir.setOpaque(false);
        TanggalAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhirKeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir);
        TanggalAkhir.setBounds(457, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(242, 10, 111, 23);

        LamaSakit.setText("1 (Satu)");
        LamaSakit.setHighlighter(null);
        LamaSakit.setName("LamaSakit"); // NOI18N
        LamaSakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LamaSakitKeyPressed(evt);
            }
        });
        FormInput.add(LamaSakit);
        LamaSakit.setBounds(630, 40, 90, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("s.d.");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(427, 40, 27, 23);

        jLabel18.setText("Lama Sakit :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(556, 40, 70, 23);

        TglLahir.setForeground(new java.awt.Color(50, 70, 50));
        TglLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2022" }));
        TglLahir.setDisplayFormat("dd-MM-yyyy");
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.setOpaque(false);
        TglLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglLahirItemStateChanged(evt);
            }
        });
        TglLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLahirKeyPressed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(420, 70, 90, 23);

        jLabel13.setText("Tanggal Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(326, 70, 90, 23);

        jLabel5.setText("Nomor Surat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 100, 23);

        jLabel8.setText("Hubungan Dengan Pasien :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(451, 100, 155, 23);

        NamaPihak2.setName("NamaPihak2"); // NOI18N
        NamaPihak2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPihak2KeyPressed(evt);
            }
        });
        FormInput.add(NamaPihak2);
        NamaPihak2.setBounds(104, 70, 220, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 100, 100, 23);

        cmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        cmbJk.setName("cmbJk"); // NOI18N
        cmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJkKeyPressed(evt);
            }
        });
        FormInput.add(cmbJk);
        cmbJk.setBounds(104, 100, 110, 23);

        jLabel10.setText("Nama Pihak Ke 2 :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 70, 100, 23);

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 130, 100, 23);

        cmbHubungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Saudara", "Keponakan" }));
        cmbHubungan.setName("cmbHubungan"); // NOI18N
        cmbHubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbHubunganKeyPressed(evt);
            }
        });
        FormInput.add(cmbHubungan);
        cmbHubungan.setBounds(610, 100, 110, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(219, 100, 77, 23);

        AlamatPj.setName("AlamatPj"); // NOI18N
        AlamatPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPjKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPj);
        AlamatPj.setBounds(104, 130, 280, 23);

        jLabel14.setText("Instansi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(386, 130, 70, 23);

        Instansi.setName("Instansi"); // NOI18N
        Instansi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstansiKeyPressed(evt);
            }
        });
        FormInput.add(Instansi);
        Instansi.setBounds(460, 130, 260, 23);

        jLabel15.setText("Dari Tanggal :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(250, 40, 80, 23);

        TanggalAwal.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAwal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-01-2022" }));
        TanggalAwal.setDisplayFormat("dd-MM-yyyy");
        TanggalAwal.setName("TanggalAwal"); // NOI18N
        TanggalAwal.setOpaque(false);
        TanggalAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAwalKeyPressed(evt);
            }
        });
        FormInput.add(TanggalAwal);
        TanggalAwal.setBounds(334, 40, 90, 23);

        cmbPekerjaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Karyawan Swasta", "PNS", "Wiraswasta", "Pelajar", "Mahasiswa", "Buruh", "Lain-lain" }));
        cmbPekerjaan.setName("cmbPekerjaan"); // NOI18N
        cmbPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(cmbPekerjaan);
        cmbPekerjaan.setBounds(300, 100, 145, 23);

        jLabel44.setText("Umur :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(514, 70, 50, 23);

        TUmurTh.setText("0");
        TUmurTh.setName("TUmurTh"); // NOI18N
        TUmurTh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurThKeyPressed(evt);
            }
        });
        FormInput.add(TUmurTh);
        TUmurTh.setBounds(568, 70, 35, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Th");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(605, 70, 13, 23);

        TUmurBl.setText("0");
        TUmurBl.setName("TUmurBl"); // NOI18N
        TUmurBl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurBlKeyPressed(evt);
            }
        });
        FormInput.add(TUmurBl);
        TUmurBl.setBounds(620, 70, 35, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Bl");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(657, 70, 13, 23);

        TUmurHr.setText("0");
        TUmurHr.setName("TUmurHr"); // NOI18N
        TUmurHr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurHrKeyPressed(evt);
            }
        });
        FormInput.add(TUmurHr);
        TUmurHr.setBounds(670, 70, 35, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Hr");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(707, 70, 20, 23);

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
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
       Valid.pindah(evt,TCari,LamaSakit);
}//GEN-LAST:event_NoSuratKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,LamaSakit);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,NamaPihak2);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat Sakit");
        }else if(LamaSakit.getText().trim().equals("")){
            Valid.textKosong(LamaSakit,"lama sakit");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NamaPihak2.getText().trim().equals("")){
            Valid.textKosong(NamaPihak2,"Nama Pihak Ke 2");
        }else if(Instansi.getText().trim().equals("")){
            Valid.textKosong(Instansi,"Instansi Pihak Ke 2");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Pihak Ke 2");
        }else{
            if(Sequel.menyimpantf("suratsakitpihak2","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat Sakit",13,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalAwal.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir.getSelectedItem()+""),LamaSakit.getText(),
                    NamaPihak2.getText(),Valid.SetTgl(TglLahir.getSelectedItem()+""),TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",cmbJk.getSelectedItem()+"",AlamatPj.getText(),
                    cmbHubungan.getSelectedItem()+"",cmbPekerjaan.getSelectedItem()+"",Instansi.getText()
                })==true){
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(TanggalAwal.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir.getSelectedItem()+""),LamaSakit.getText(),
                    NamaPihak2.getText(),Valid.SetTgl(TglLahir.getSelectedItem()+""),TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",cmbJk.getSelectedItem().toString(),AlamatPj.getText(),
                    cmbHubungan.getToolTipText(),cmbPekerjaan.getSelectedItem().toString(),Instansi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Instansi,BtnBatal);
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
        if(Valid.hapusTabletf(tabMode,NoSurat,"suratsakitpihak2","no_surat")==true){
            if(tbObat.getSelectedRow()!= -1){
                tabMode.removeRow(tbObat.getSelectedRow());
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
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
        if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat Sakit");
        }else if(LamaSakit.getText().trim().equals("")){
            Valid.textKosong(LamaSakit,"lama sakit");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NamaPihak2.getText().trim().equals("")){
            Valid.textKosong(NamaPihak2,"Nama Pihak Ke 2");
        }else if(Instansi.getText().trim().equals("")){
            Valid.textKosong(Instansi,"Instansi Pihak Ke 2");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Pihak Ke 2");
        }else{  
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.mengedittf("suratsakitpihak2","no_surat=?","no_surat=?,no_rawat=?,tanggalawal=?,tanggalakhir=?,lamasakit=?,nama2=?,tgl_lahir=?,umur=?,jk=?,alamat=?,hubungan=?,pekerjaan=?,instansi=?",14,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalAwal.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir.getSelectedItem()+""),LamaSakit.getText(),
                    NamaPihak2.getText(),Valid.SetTgl(TglLahir.getSelectedItem()+""),TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",cmbJk.getSelectedItem()+"",AlamatPj.getText(),
                    cmbHubungan.getSelectedItem()+"",cmbPekerjaan.getSelectedItem()+"",Instansi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                    tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
                    tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
                    tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
                    tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
                    tbObat.setValueAt(Valid.SetTgl(TanggalAwal.getSelectedItem()+""),tbObat.getSelectedRow(),4);
                    tbObat.setValueAt(Valid.SetTgl(TanggalAkhir.getSelectedItem()+""),tbObat.getSelectedRow(),5);
                    tbObat.setValueAt(LamaSakit.getText(),tbObat.getSelectedRow(),6);
                    tbObat.setValueAt(NamaPihak2.getText(),tbObat.getSelectedRow(),7);
                    tbObat.setValueAt(Valid.SetTgl(TglLahir.getSelectedItem()+""),tbObat.getSelectedRow(),8);
                    tbObat.setValueAt(TUmurTh.getText()+" Th "+TUmurBl.getText()+" Bl "+TUmurHr.getText()+" Hr",tbObat.getSelectedRow(),9);
                    tbObat.setValueAt(cmbJk.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
                    tbObat.setValueAt(AlamatPj.getText(),tbObat.getSelectedRow(),11);
                    tbObat.setValueAt(cmbHubungan.getToolTipText(),tbObat.getSelectedRow(),12);
                    tbObat.setValueAt(cmbPekerjaan.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
                    tbObat.setValueAt(Instansi.getText(),tbObat.getSelectedRow(),14);
                    emptTeks();
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
            dispose();
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
            tgl=" suratsakitpihak2.tanggalawal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataSuratSakitPihak2.jasper","report","::[ Data Surat Keterangan Sakit Pihak Ke 2 ]::",
                     "select suratsakitpihak2.no_surat,suratsakitpihak2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "suratsakitpihak2.tanggalawal,suratsakitpihak2.tanggalakhir,suratsakitpihak2.lamasakit,suratsakitpihak2.nama2,suratsakitpihak2.tgl_lahir, "+                  
                     "suratsakitpihak2.umur,suratsakitpihak2.jk,suratsakitpihak2.alamat,suratsakitpihak2.hubungan,suratsakitpihak2.pekerjaan,suratsakitpihak2.instansi from suratsakitpihak2 inner join reg_periksa on suratsakitpihak2.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"order by suratsakitpihak2.no_surat",param);
            }else{
                Valid.MyReportqry("rptDataSuratSakitPihak2.jasper","report","::[ Data Surat Keterangan Sakit Pihak Ke 2 ]::",
                     "select suratsakitpihak2.no_surat,suratsakitpihak2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "suratsakitpihak2.tanggalawal,suratsakitpihak2.tanggalakhir,suratsakitpihak2.lamasakit,suratsakitpihak2.nama2,suratsakitpihak2.tgl_lahir, "+                  
                     "suratsakitpihak2.umur,suratsakitpihak2.jk,suratsakitpihak2.alamat,suratsakitpihak2.hubungan,suratsakitpihak2.pekerjaan,suratsakitpihak2.instansi from suratsakitpihak2 inner join reg_periksa on suratsakitpihak2.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+" and (no_surat like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     "reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     "suratsakitpihak2.instansi like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.alamat like '%"+TCari.getText().trim()+"%' or "+
                     "suratsakitpihak2.nama2 like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.hubungan like '%"+TCari.getText().trim()+"%') "+
                     "order by suratsakitpihak2.no_surat",param);
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
   
                                  
    private void TanggalAkhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhirKeyPressed
        Valid.pindah2(evt,TanggalAwal,LamaSakit);
}//GEN-LAST:event_TanggalAkhirKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
       
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void LamaSakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LamaSakitKeyPressed
        Valid.pindah(evt,TanggalAkhir,NamaPihak2);
    }//GEN-LAST:event_LamaSakitKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void TglLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLahirKeyPressed
        Valid.pindah2(evt,NamaPihak2,TUmurTh);    
    }//GEN-LAST:event_TglLahirKeyPressed

    private void MnCetakSuratSakit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit2ActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("hari",LamaSakit.getText());
                param.put("TanggalAwal",TglLahir.getSelectedItem().toString());
                param.put("TanggalAkhir",TanggalAkhir.getSelectedItem().toString());
                param.put("nosakit",NoSurat.getText());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("penyakit",Sequel.cariIsi("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join reg_periksa inner join penyakit "+
                    "on diagnosa_pasien.no_rawat=reg_periksa.no_rawat and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat=? and diagnosa_pasien.prioritas='1'",TNoRw.getText()));
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                kodedokter=Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
                namadokter=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kodedokter);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokter);
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
                Valid.MyReportqry("rptSuratSakitPihakKe2.jasper","report","::[ Surat Sakit Pihak Ke 2 ]::",
                              " select suratsakitpihak2.no_surat,DATE_FORMAT(suratsakitpihak2.tanggalawal,'%d-%m-%Y')as tanggalawal,DATE_FORMAT(suratsakitpihak2.tanggalakhir,'%d-%m-%Y')as tanggalakhir,suratsakitpihak2.lamasakit,suratsakitpihak2.nama2,"+
                              " DATE_FORMAT(suratsakitpihak2.tgl_lahir,'%d-%m-%Y')as tgl_lahirpj,(suratsakitpihak2.umur)as umurpj,(suratsakitpihak2.jk)as jkpj,"+
                              " (suratsakitpihak2.alamat)as alamatpj,suratsakitpihak2.hubungan,(suratsakitpihak2.pekerjaan)as pekerjaanpj,suratsakitpihak2.instansi,perusahaan_pasien.nama_perusahaan,dokter.nm_dokter,pasien.tgl_lahir," +
                              " DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y')as tgl_registrasi,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                              " from suratsakitpihak2 inner join perusahaan_pasien inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                              " on pasien.perusahaan_pasien=perusahaan_pasien.kode_perusahaan and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "+
                              " and suratsakitpihak2.no_rawat=reg_periksa.no_rawat and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());     
        }
    }//GEN-LAST:event_MnCetakSuratSakit2ActionPerformed

    private void TanggalAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAwalKeyPressed
        Valid.pindah2(evt,NoSurat,TanggalAkhir);
    }//GEN-LAST:event_TanggalAwalKeyPressed

    private void TglLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglLahirItemStateChanged
        lahir = TglLahir.getDate();    
        birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        p = Period.between(birthday,today);
        TUmurTh.setText(String.valueOf(p.getYears()));
        TUmurBl.setText(String.valueOf(p.getMonths()));
        TUmurHr.setText(String.valueOf(p.getDays()));
    }//GEN-LAST:event_TglLahirItemStateChanged

    private void TUmurThKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurThKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                Valid.SetTgl(TglLahir,Sequel.cariIsi("select DATE_SUB('"+Valid.SetTgl(TglLahir.getSelectedItem()+"")+"', interval "+TUmurTh.getText()+" year)"));
            } catch (Exception e) {
                System.out.println(e);
            }
            TUmurBl.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TglLahir.requestFocus();
        }
    }//GEN-LAST:event_TUmurThKeyPressed

    private void TUmurBlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurBlKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TUmurHr.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TUmurTh.requestFocus();
        }
    }//GEN-LAST:event_TUmurBlKeyPressed

    private void TUmurHrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurHrKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            cmbJk.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TUmurBl.requestFocus();
        }
    }//GEN-LAST:event_TUmurHrKeyPressed

    private void NamaPihak2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPihak2KeyPressed
        Valid.pindah(evt,LamaSakit,TglLahir);
    }//GEN-LAST:event_NamaPihak2KeyPressed

    private void cmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJkKeyPressed
        Valid.pindah(evt,TUmurHr,cmbPekerjaan);
    }//GEN-LAST:event_cmbJkKeyPressed

    private void cmbPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPekerjaanKeyPressed
        Valid.pindah(evt,cmbJk,cmbHubungan);
    }//GEN-LAST:event_cmbPekerjaanKeyPressed

    private void cmbHubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbHubunganKeyPressed
        Valid.pindah(evt,cmbPekerjaan,AlamatPj);
    }//GEN-LAST:event_cmbHubunganKeyPressed

    private void AlamatPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPjKeyPressed
        Valid.pindah(evt,cmbHubungan,Instansi);
    }//GEN-LAST:event_AlamatPjKeyPressed

    private void InstansiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstansiKeyPressed
        Valid.pindah(evt,AlamatPj,BtnSimpan);
    }//GEN-LAST:event_InstansiKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratSakitPihak2 dialog = new SuratSakitPihak2(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPj;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Instansi;
    private widget.Label LCount;
    private widget.TextBox LamaSakit;
    private javax.swing.JMenuItem MnCetakSuratSakit2;
    private widget.TextBox NamaPihak2;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TUmurBl;
    private widget.TextBox TUmurHr;
    private widget.TextBox TUmurTh;
    private widget.Tanggal TanggalAkhir;
    private widget.Tanggal TanggalAwal;
    private widget.Tanggal TglLahir;
    private widget.ComboBox cmbHubungan;
    private widget.ComboBox cmbJk;
    private widget.ComboBox cmbPekerjaan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel4;
    private widget.Label jLabel44;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" suratsakitpihak2.tanggalawal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                     "select suratsakitpihak2.no_surat,suratsakitpihak2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "suratsakitpihak2.tanggalawal,suratsakitpihak2.tanggalakhir,suratsakitpihak2.lamasakit,suratsakitpihak2.nama2,suratsakitpihak2.tgl_lahir, "+                  
                     "suratsakitpihak2.umur,suratsakitpihak2.jk,suratsakitpihak2.alamat,suratsakitpihak2.hubungan,suratsakitpihak2.pekerjaan,suratsakitpihak2.instansi from suratsakitpihak2 inner join reg_periksa on suratsakitpihak2.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"order by suratsakitpihak2.no_surat");
            }else{
                ps=koneksi.prepareStatement(
                     "select suratsakitpihak2.no_surat,suratsakitpihak2.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "suratsakitpihak2.tanggalawal,suratsakitpihak2.tanggalakhir,suratsakitpihak2.lamasakit,suratsakitpihak2.nama2,suratsakitpihak2.tgl_lahir, "+                  
                     "suratsakitpihak2.umur,suratsakitpihak2.jk,suratsakitpihak2.alamat,suratsakitpihak2.hubungan,suratsakitpihak2.pekerjaan,suratsakitpihak2.instansi from suratsakitpihak2 inner join reg_periksa on suratsakitpihak2.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+" and (no_surat like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     "reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     "suratsakitpihak2.instansi like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.alamat like '%"+TCari.getText().trim()+"%' or "+
                     "suratsakitpihak2.nama2 like '%"+TCari.getText().trim()+"%' or suratsakitpihak2.hubungan like '%"+TCari.getText().trim()+"%') "+
                     "order by suratsakitpihak2.no_surat");
            }
                
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(15)
                        
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
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        NoSurat.setText("");
        LamaSakit.setText("1 (Satu)");
        TglLahir.setDate(new Date());
        TanggalAwal.setDate(new Date());
        TanggalAkhir.setDate(new Date());
        NamaPihak2.setText("");
        TUmurTh.setText("0");
        TUmurBl.setText("0");
        TUmurHr.setText("0");
        AlamatPj.setText("");
        Instansi.setText("");
        bln_angka = "";
        bln_romawi = "";
        bln_angka = TanggalAwal.getSelectedItem().toString().substring(3,5);
        if (bln_angka.equals("01")) {
            bln_romawi = "I";
        } else if (bln_angka.equals("02")) {
            bln_romawi = "II";
        } else if (bln_angka.equals("03")) {
            bln_romawi = "III";
        } else if (bln_angka.equals("04")) {
            bln_romawi = "IV";
        } else if (bln_angka.equals("05")) {
            bln_romawi = "V";
        } else if (bln_angka.equals("06")) {
            bln_romawi = "VI";
        } else if (bln_angka.equals("07")) {
            bln_romawi = "VII";
        } else if (bln_angka.equals("08")) {
            bln_romawi = "VIII";
        } else if (bln_angka.equals("09")) {
            bln_romawi = "IX";
        } else if (bln_angka.equals("10")) {
            bln_romawi = "X";
        } else if (bln_angka.equals("11")) {
            bln_romawi = "XI";
        } else if (bln_angka.equals("12")) {
            bln_romawi = "XII";
        }
        Valid.autoNomer2("select ifnull(MAX(CONVERT(LEFT(suratsakitpihak2.no_surat,3),signed)),0) from suratsakitpihak2 where suratsakitpihak2.tanggalawal like '%" + Valid.SetTgl(TanggalAwal.getSelectedItem() + "").substring(0, 7) + "%' ",Valid.SetTgl(TanggalAwal.getSelectedItem() + "").substring(0, 4)+"/SKS-2/" + bln_romawi + "/", 3, NoSurat);
        NoSurat.requestFocus();
    }

 
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            LamaSakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NamaPihak2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            cmbJk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            AlamatPj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            cmbHubungan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            cmbPekerjaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            Instansi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Valid.SetTgl(TanggalAwal,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(TanggalAkhir,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Valid.SetTgl(TglLahir,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt,String alamat, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        AlamatPj.setText(alamat);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien(); 
        ChkInput.setSelected(true);
        isForm();
    }
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,186));
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
        BtnSimpan.setEnabled(akses.getsurat_sakit_pihak_2());
        BtnHapus.setEnabled(akses.getsurat_sakit_pihak_2());
        BtnEdit.setEnabled(akses.getsurat_sakit_pihak_2());
    }
    
}




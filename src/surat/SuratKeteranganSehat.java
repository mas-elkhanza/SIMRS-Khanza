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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.hssf.record.formula.functions.Setvalue;

//modif
import AESsecurity.EnkripsiAES;
import java.io.File;
import java.sql.SQLException;
import wa.GoWAService;
import wa.ServiceWAHA;


/**
 * 
 * @author salimmulyana
 */
public final class SuratKeteranganSehat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String tgl,finger="",kodedokter="",namadokter="";
    private String bln_angka;
    private String bln_romawi;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SuratKeteranganSehat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Surat","No.Rawat","No.R.M.","Nama Pasien","Tanggal","BB","TB",
            "Tensi","Suhu","SpO2","Nadi","BMI","Buta Warna","Keperluan","Kesimpulan"
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
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                column.setPreferredWidth(35);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(35);
            }else if(i==9){
                column.setPreferredWidth(35);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(35);    
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(80);
           }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoSurat.setDocument(new batasInput((byte)30).getKata(NoSurat));
        Bb.setDocument(new batasInput((byte)5).getKata(Bb));
        Tb.setDocument(new batasInput((byte)5).getKata(Tb));
        Tensi.setDocument(new batasInput((byte)8).getKata(Tensi));
        Suhu.setDocument(new batasInput((byte)4).getKata(Suhu));
        SpO2.setDocument(new batasInput((byte)4).getKata(SpO2));
        Nadi.setDocument(new batasInput((byte)4).getKata(Nadi));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));           
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
        MnCetakSuratSehat = new javax.swing.JMenuItem();
        MnCetakSuratSehat1 = new javax.swing.JMenuItem();
        MnCetakSuratLayakTerbang = new javax.swing.JMenuItem();
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
        CbPassword = new javax.swing.JComboBox<>();
        nohp = new widget.TextBox();
        BtnKirimGOWa = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnRiwayatSurat = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel13 = new widget.Label();
        Bb = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        Keperluan = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Tensi = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel5 = new widget.Label();
        Tb = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        CmbButaWarna = new widget.ComboBox();
        CmbKesimpulan = new widget.ComboBox();
        TanggalSurat = new widget.Tanggal();
        jLabel16 = new widget.Label();
        jLabel24 = new widget.Label();
        SpO2 = new widget.TextBox();
        jLabel26 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel29 = new widget.Label();
        btn_ambil = new widget.Button();
        jLabel30 = new widget.Label();
        BMI = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratSehat.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSehat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSehat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat.setText("SKK RSPK");
        MnCetakSuratSehat.setName("MnCetakSuratSehat"); // NOI18N
        MnCetakSuratSehat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCetakSuratSehat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSehat);

        MnCetakSuratSehat1.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSehat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSehat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat1.setText("Cetak Surat Sehat BMI");
        MnCetakSuratSehat1.setName("MnCetakSuratSehat1"); // NOI18N
        MnCetakSuratSehat1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCetakSuratSehat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehat1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSehat1);

        MnCetakSuratLayakTerbang.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratLayakTerbang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratLayakTerbang.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratLayakTerbang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratLayakTerbang.setText("Cetak Surat Sehat BMI");
        MnCetakSuratLayakTerbang.setEnabled(false);
        MnCetakSuratLayakTerbang.setName("MnCetakSuratLayakTerbang"); // NOI18N
        MnCetakSuratLayakTerbang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCetakSuratLayakTerbang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratLayakTerbangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratLayakTerbang);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Keterangan Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(40, 30));
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
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(40, 30));
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
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(40, 30));
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

        CbPassword.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        CbPassword.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Password", "No Password" }));
        CbPassword.setName("CbPassword"); // NOI18N
        CbPassword.setPreferredSize(new java.awt.Dimension(100, 20));
        panelGlass8.add(CbPassword);

        nohp.setMinimumSize(new java.awt.Dimension(80, 24));
        nohp.setName("nohp"); // NOI18N
        nohp.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(nohp);

        BtnKirimGOWa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/16whatsapp.png"))); // NOI18N
        BtnKirimGOWa.setMnemonic('K');
        BtnKirimGOWa.setText("Kirim PDF");
        BtnKirimGOWa.setToolTipText("Alt+K");
        BtnKirimGOWa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        BtnKirimGOWa.setName("BtnKirimGOWa"); // NOI18N
        BtnKirimGOWa.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirimGOWa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimGOWaActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKirimGOWa);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
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

        BtnRiwayatSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/preview-16x16.png"))); // NOI18N
        BtnRiwayatSurat.setMnemonic('3');
        BtnRiwayatSurat.setText("Riwayat");
        BtnRiwayatSurat.setToolTipText("Alt+3");
        BtnRiwayatSurat.setName("BtnRiwayatSurat"); // NOI18N
        BtnRiwayatSurat.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnRiwayatSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRiwayatSuratActionPerformed(evt);
            }
        });
        BtnRiwayatSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRiwayatSuratKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnRiwayatSurat);

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
        PanelInput.setPreferredSize(new java.awt.Dimension(915, 160));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(913, 144));
        FormInput.setLayout(null);

        jLabel3.setText("Keperluan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(290, 100, 70, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoSuratActionPerformed(evt);
            }
        });
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(79, 40, 150, 23);

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
        TNoRw.setBounds(79, 10, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(335, 10, 230, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(232, 10, 100, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(369, 40, 20, 23);

        Bb.setName("Bb"); // NOI18N
        Bb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BbKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BbKeyReleased(evt);
            }
        });
        FormInput.add(Bb);
        Bb.setBounds(317, 40, 50, 23);

        jLabel14.setText("SPO2");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(230, 70, 80, 23);

        jLabel15.setText("Tinggi Badan :");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(391, 40, 80, 23);

        Keperluan.setName("Keperluan"); // NOI18N
        Keperluan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeperluanKeyPressed(evt);
            }
        });
        FormInput.add(Keperluan);
        Keperluan.setBounds(360, 100, 150, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Cm");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(532, 40, 20, 23);

        jLabel18.setText("Tensi :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(564, 40, 40, 23);

        Tensi.setName("Tensi"); // NOI18N
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        FormInput.add(Tensi);
        Tensi.setBounds(608, 40, 64, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("mmHg");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(532, 70, 60, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("°C");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(230, 70, 40, 23);

        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(170, 70, 50, 23);

        jLabel23.setText("Suhu :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(110, 70, 50, 23);

        jLabel5.setText("No. Surat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 75, 23);

        Tb.setName("Tb"); // NOI18N
        Tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TbKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TbKeyReleased(evt);
            }
        });
        FormInput.add(Tb);
        Tb.setBounds(475, 40, 55, 23);

        jLabel27.setText("Buta Warna :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(120, 100, 83, 23);

        jLabel28.setText("Kesimpulan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(520, 100, 75, 23);

        CmbButaWarna.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CmbButaWarna.setName("CmbButaWarna"); // NOI18N
        CmbButaWarna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbButaWarnaKeyPressed(evt);
            }
        });
        FormInput.add(CmbButaWarna);
        CmbButaWarna.setBounds(210, 100, 80, 23);

        CmbKesimpulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sehat", "Tidak Sehat" }));
        CmbKesimpulan.setName("CmbKesimpulan"); // NOI18N
        CmbKesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbKesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(CmbKesimpulan);
        CmbKesimpulan.setBounds(600, 100, 114, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2026" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.setPreferredSize(new java.awt.Dimension(141, 18));
        TanggalSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalSuratActionPerformed(evt);
            }
        });
        TanggalSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSuratKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSurat);
        TanggalSurat.setBounds(630, 10, 90, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(566, 10, 60, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" %");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(369, 70, 30, 23);

        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(317, 70, 55, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText(" x/Menit");
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(680, 40, 60, 23);

        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(475, 70, 55, 23);

        jLabel29.setText("Nadi :");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(430, 70, 40, 23);

        btn_ambil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        btn_ambil.setText("Cari Data");
        btn_ambil.setMaximumSize(new java.awt.Dimension(120, 22));
        btn_ambil.setMinimumSize(new java.awt.Dimension(120, 22));
        btn_ambil.setName("btn_ambil"); // NOI18N
        btn_ambil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ambilActionPerformed(evt);
            }
        });
        FormInput.add(btn_ambil);
        btn_ambil.setBounds(20, 70, 100, 23);

        jLabel30.setText("BMI :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(564, 70, 40, 23);

        BMI.setName("BMI"); // NOI18N
        BMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BMIKeyReleased(evt);
            }
        });
        FormInput.add(BMI);
        BMI.setBounds(608, 70, 64, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Kg/m²");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(680, 70, 60, 23);

        jLabel32.setText("Berat Badan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(233, 40, 80, 23);

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
       Valid.pindah(evt,TanggalSurat,Bb);
}//GEN-LAST:event_NoSuratKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,NoSurat);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(Bb.getText().trim().equals("")){
            Valid.textKosong(Bb,"Berat Badan");
        }else if(Tb.getText().trim().equals("")){
            Valid.textKosong(Tb,"Tinggi Badan");
        }else if(Tensi.getText().trim().equals("")){
            Valid.textKosong(Tensi,"Tensi");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(SpO2.getText().trim().equals("")){
            Valid.textKosong(SpO2,"SpO2");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI");
        }else if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
       
        }else{
            if(Sequel.menyimpantf("surat_keterangan_sehat","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Surat",13,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Bb.getText(),Tb.getText(),Tensi.getText(),Suhu.getText(),SpO2.getText(),Nadi.getText(),BMI.getText(),CmbButaWarna.getSelectedItem()+"",
                    Keperluan.getText(),CmbKesimpulan.getSelectedItem()+""
                })==true){
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Bb.getText(),Tb.getText(),Tensi.getText(),Suhu.getText(),SpO2.getText(),Nadi.getText(),BMI.getText(),CmbButaWarna.getSelectedItem().toString(),Keperluan.getText(),CmbKesimpulan.getSelectedItem().toString()
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
            Valid.pindah(evt,CmbKesimpulan,BtnBatal);
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
        if(Valid.hapusTabletf(tabMode,NoSurat,"surat_keterangan_sehat","no_surat")==true){
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
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(Bb.getText().trim().equals("")){
            Valid.textKosong(Bb,"Berat Badan");
        }else if(Tb.getText().trim().equals("")){
            Valid.textKosong(Tb,"Tinggi Badan");
        }else if(Tensi.getText().trim().equals("")){
            Valid.textKosong(Tensi,"Tensi");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu");
        }else if(SpO2.getText().trim().equals("")){
            Valid.textKosong(SpO2,"SpO2");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI");
        }else if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
        }else{    
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.mengedittf("surat_keterangan_sehat","no_surat=?","no_surat=?,no_rawat=?,tanggalsurat=?,berat=?,tinggi=?,tensi=?,suhu=?,spo2=?,nadi=?,bmi=?,butawarna=?,keperluan=?,kesimpulan=?",14,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Bb.getText(),Tb.getText(),Tensi.getText(),Suhu.getText(),SpO2.getText(),Nadi.getText(),BMI.getText(),CmbButaWarna.getSelectedItem().toString(),
                    Keperluan.getText(),CmbKesimpulan.getSelectedItem().toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                    tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
                    tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
                    tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
                    tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
                    tbObat.setValueAt(Valid.SetTgl(TanggalSurat.getSelectedItem()+""),tbObat.getSelectedRow(),4);
                    tbObat.setValueAt(Bb.getText(),tbObat.getSelectedRow(),5);
                    tbObat.setValueAt(Tb.getText(),tbObat.getSelectedRow(),6);
                    tbObat.setValueAt(Tensi.getText(),tbObat.getSelectedRow(),7);
                    tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),8);
                    tbObat.setValueAt(SpO2.getText(),tbObat.getSelectedRow(),9);
                    tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),10);
                    tbObat.setValueAt(BMI.getText(),tbObat.getSelectedRow(),11);
                    tbObat.setValueAt(CmbButaWarna.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
                    tbObat.setValueAt(Keperluan.getText(),tbObat.getSelectedRow(),13);
                    tbObat.setValueAt(CmbKesimpulan.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
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
            tgl=" surat_keterangan_sehat.tanggalsurat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataSuratKeteranganSehat.jasper","report","::[ Data Surat Keterangan Sehat ]::",
                     "select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "surat_keterangan_sehat.tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu,surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.bmi, "+                  
                     "surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan from surat_keterangan_sehat inner join reg_periksa on surat_keterangan_sehat.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"order by surat_keterangan_sehat.no_surat",param);
            }else{
                Valid.MyReportqry("rptDataSuratKeteranganSehat.jasper","report","::[ Data Surat Keterangan Sehat ]::",
                     "select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "surat_keterangan_sehat.tanggalawal,surat_keterangan_sehat.tanggalakhir "+                  
                     "from surat_keterangan_sehat inner join reg_periksa on surat_keterangan_sehat.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"and no_surat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_keterangan_sehat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_keterangan_sehat.tanggalakhir like '%"+TCari.getText().trim()+"%' "+
                     "order by surat_keterangan_sehat.no_surat",param);
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
   
                                  
    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

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

    private void MnCetakSuratSehatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehatActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                kodedokter=Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
                namadokter=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kodedokter);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokter);
                param.put("finger","https://apps.rspelitakasih.id/verifyPDF/?id="+EnkripsiAES.encrypt("{'x':'skk','t':'"+NoSurat.getText()+"'}"));
                //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
                Valid.MyReportqrypdf("rptSuratKeteranganSehatRSPK.jasper","report","::[ Surat Keterangan Sehat ]::",
                              " select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.tanggalsurat as tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu,surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,pasien.tmp_lahir,pasien.tgl_lahir,surat_keterangan_sehat.butawarna, "+
                              " surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan,dokter.nm_dokter,pasien.jk,reg_periksa.kd_dokter,dokter.no_ijn_praktek," +
                              " pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                              " from surat_keterangan_sehat inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel and "+
                              " pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rawat=surat_keterangan_sehat.no_rawat "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
    }//GEN-LAST:event_MnCetakSuratSehatActionPerformed

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,TCari,Keperluan);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void MnCetakSuratSehat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehat1ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            kodedokter = Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText());
            namadokter = Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", kodedokter);
            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodedokter);
            //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
            param.put("finger", "https://apps.rspelitakasih.id/verifyPDF/?id=" + EnkripsiAES.encrypt("{'x':'skk','t':'" + NoSurat.getText() + "'}"));
            Valid.MyReportqrypdf("rptSuratKeteranganSehatBMI.jasper", "report", "::[ Surat Keterangan Sehat ]::",
                    " select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.tanggalsurat as tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu,surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,pasien.tmp_lahir,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.bmi, "
                    + " surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan,dokter.nm_dokter,pasien.jk,pasien.tgl_lahir as tgl_lahir,reg_periksa.kd_dokter,dokter.no_ijn_praktek, "
                    + " pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat"
                    + " from surat_keterangan_sehat inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel and "
                    + " pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rawat=surat_keterangan_sehat.no_rawat "
                    + " where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratSehat1ActionPerformed

    private void NoSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSuratActionPerformed

    private void BbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbKeyPressed
        Valid.pindah(evt,NoSurat,Bb);
    }//GEN-LAST:event_BbKeyPressed

    private void TbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TbKeyPressed
        Valid.pindah(evt,Bb,Tb);
    }//GEN-LAST:event_TbKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        Valid.pindah(evt,Tb,Tensi);
    }//GEN-LAST:event_TensiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,Tensi,SpO2);
    }//GEN-LAST:event_SuhuKeyPressed

    private void CmbButaWarnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbButaWarnaKeyPressed
        Valid.pindah(evt,Nadi,Keperluan);
    }//GEN-LAST:event_CmbButaWarnaKeyPressed

    private void KeperluanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeperluanKeyPressed
        Valid.pindah(evt,CmbButaWarna,CmbKesimpulan);
    }//GEN-LAST:event_KeperluanKeyPressed

    private void CmbKesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbKesimpulanKeyPressed
        Valid.pindah(evt,Keperluan,BtnSimpan);
    }//GEN-LAST:event_CmbKesimpulanKeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,Suhu,Nadi);
    }//GEN-LAST:event_SpO2KeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,SpO2,CmbButaWarna);
    }//GEN-LAST:event_NadiKeyPressed
    
    private void isBMI() {
        if ((!Tb.getText().equals("")) && (!Bb.getText().equals(""))) {
            try {
                BMI.setText(Valid.SetAngka8(
                        Valid.SetAngka(Bb.getText())
                        / ((Valid.SetAngka(Tb.getText()) / 100) * (Valid.SetAngka(Tb.getText()) / 100)), 1) + "");
            } catch (Exception e) {
                BMI.setText("");
            }
        }
    }

    
    private void btn_ambilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ambilActionPerformed
        if (Sequel.cariIsi("select pemeriksaan_ralan.suhu_tubuh from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'").trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Data SOAP Kosong !!!.");
        }else{
        // ambil data dari pemeriksaan_ralan SOAP DOKTER
        Suhu.setText(Sequel.cariIsi("select pemeriksaan_ralan.suhu_tubuh from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        Bb.setText(Sequel.cariIsi("select pemeriksaan_ralan.berat from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        Tensi.setText(Sequel.cariIsi("select pemeriksaan_ralan.tensi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        Nadi.setText(Sequel.cariIsi("select pemeriksaan_ralan.nadi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        Tb.setText(Sequel.cariIsi("select pemeriksaan_ralan.tinggi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        SpO2.setText(Sequel.cariIsi("select pemeriksaan_ralan.spo2 from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='" + TNoRw.getText() + "'"));
        isBMI();
        }
    }//GEN-LAST:event_btn_ambilActionPerformed

    private void BtnRiwayatSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatSuratActionPerformed
        if (Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rkm_medis='" + TNoRM.getText() + "'").trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan Pilih Dulu No.RM !!!.");
        } else {
            TCari.setText(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rkm_medis='" + TNoRM.getText() + "'"));
            //tampil();
            Valid.tabelKosong(tabMode);
            try {
                tgl = " surat_keterangan_sehat.tanggalsurat ";
                if (TCari.getText().trim().equals("")) {
                    ps = koneksi.prepareStatement(
                            "surat_keterangan_sehat.no_surat, "
                            + "surat_keterangan_sehat.no_rawat, "
                            + "reg_periksa.no_rkm_medis, "
                            + "pasien.nm_pasien, "
                            + "surat_keterangan_sehat.tanggalsurat, "
                            + "surat_keterangan_sehat.berat, "
                            + "surat_keterangan_sehat.tinggi, "
                            + "surat_keterangan_sehat.tensi, "
                            + "surat_keterangan_sehat.suhu, "
                            + "surat_keterangan_sehat.spo2, "
                            + "surat_keterangan_sehat.nadi, "
                            + "surat_keterangan_sehat.bmi, "
                            + "surat_keterangan_sehat.butawarna, "
                            + "surat_keterangan_sehat.keperluan, "
                            + "surat_keterangan_sehat.kesimpulan "
                            + "FROM surat_keterangan_sehat "
                            + "INNER JOIN reg_periksa ON surat_keterangan_sehat.no_rawat = reg_periksa.no_rawat "
                            + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                            + "WHERE reg_periksa.no_rkm_medis = ? "
                            + "ORDER BY surat_keterangan_sehat.no_surat");
                } else {
                    ps = koneksi.prepareStatement(
                            "select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "surat_keterangan_sehat.tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu, "
                            + "surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,surat_keterangan_sehat.bmi,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan from surat_keterangan_sehat inner join reg_periksa on surat_keterangan_sehat.no_rawat=reg_periksa.no_rawat "
                            + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "where " + tgl + "and no_surat like '%" + TCari.getText().trim() + "%' or "
                            + tgl + "and surat_keterangan_sehat.no_rawat like '%" + TCari.getText().trim() + "%' or "
                            + tgl + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                            + tgl + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                            + tgl + "and surat_keterangan_sehat.tanggalsurat like '%" + TCari.getText().trim() + "%' "
                            + "order by surat_keterangan_sehat.no_surat");
                }

                try {
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6),
                            rs.getString(7), rs.getString(8), rs.getString(9),
                            rs.getString(10), rs.getString(11), rs.getString(12),
                            rs.getString(13), rs.getString(14)

                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
            LCount.setText("" + tabMode.getRowCount());
        }
    
    }//GEN-LAST:event_BtnRiwayatSuratActionPerformed

    private void BtnRiwayatSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRiwayatSuratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRiwayatSuratKeyPressed

    private void BMIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BMIKeyReleased
      
    }//GEN-LAST:event_BMIKeyReleased

    private void BbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbKeyReleased
        Bb.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                isBMI();
            }
        });
    }//GEN-LAST:event_BbKeyReleased

    private void TbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TbKeyReleased
        Tb.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                isBMI();
            }
        });
    }//GEN-LAST:event_TbKeyReleased

    private void BtnKirimGOWaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimGOWaActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                kodedokter=Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText());
                namadokter=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kodedokter);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kodedokter);
                param.put("finger","https://apps.rspelitakasih.id/verifyPDF/?id="+EnkripsiAES.encrypt("{'x':'skk','t':'"+NoSurat.getText()+"'}"));
                //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
                Valid.MyReportqrypdf("rptSuratKeteranganSehatRSPK.jasper","report","::[ Surat Keterangan Sehat ]::",
                              " select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.tanggalsurat as tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu,surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,pasien.tmp_lahir,pasien.tgl_lahir,surat_keterangan_sehat.butawarna, "+
                              " surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan,dokter.nm_dokter,pasien.jk,reg_periksa.kd_dokter,dokter.no_ijn_praktek," +
                              " pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat" +
                              " from surat_keterangan_sehat inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten" +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel and "+
                              " pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rawat=surat_keterangan_sehat.no_rawat "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                //GENERATE WAHA KIRIM PDF//
                String pesan = "Halo *" + TPasien.getText() + "* 👋\n\n"
                   + "Berikut Surat Keterangan Sehat Anda.\n\n"
                   + "🔐 Password PDF: tanggal lahir (format: ddMMyyyy)\n\n"
                   + "⚠️ Pesan ini merupakan notifikasi otomatis dari sistem.\n"
                   + "Nomor ini tidak dapat menerima atau membalas pesan.\n\n"
                   + "Terima kasih.\n"
                   + akses.getnamars() + "\n";

                // ================= FILE ASLI DARI JASPER =================
            String modePassword = CbPassword.getSelectedItem().toString();
            boolean pakaiPassword = modePassword.equalsIgnoreCase("Password");

            boolean sukses = GoWAService.kirimDariNoRawat(
                    "rptSuratKeteranganSehatRSPK.pdf",
                    "SKK",
                    TNoRw.getText(),
                    nohp.getText(),
                    pakaiPassword,
                    pesan
            );
                this.setCursor(Cursor.getDefaultCursor());
            }
    }//GEN-LAST:event_BtnKirimGOWaActionPerformed

    private void MnCetakSuratLayakTerbangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratLayakTerbangActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            kodedokter = Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText());
            namadokter = Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", kodedokter);
            finger = Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kodedokter);
            //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+namadokter+"\nID "+(finger.equals("")?kodedokter:finger)+"\n"+Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));  
            param.put("finger", "https://apps.rspelitakasih.id/verifyPDF/?id=" + EnkripsiAES.encrypt("{'x':'skk','t':'" + NoSurat.getText() + "'}"));
            Valid.MyReportqrypdf("RSPKLayakTerbang.jasper", "report", "::[ Surat Keterangan Sehat ]::",
                    " select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.tanggalsurat as tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu,surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,pasien.tmp_lahir,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.bmi, "
                    + " surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan,dokter.nm_dokter,pasien.jk,pasien.tgl_lahir as tgl_lahir,reg_periksa.kd_dokter,dokter.no_ijn_praktek, "
                    + " pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat"
                    + " from surat_keterangan_sehat inner join reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel and "
                    + " pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rawat=surat_keterangan_sehat.no_rawat "
                    + " where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratLayakTerbangActionPerformed
                           

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratKeteranganSehat dialog = new SuratKeteranganSehat(new javax.swing.JFrame(), true);
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
    private widget.TextBox BMI;
    private widget.TextBox Bb;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirimGOWa;
    private widget.Button BtnPrint;
    private widget.Button BtnRiwayatSurat;
    private widget.Button BtnSimpan;
    private javax.swing.JComboBox<String> CbPassword;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbButaWarna;
    private widget.ComboBox CmbKesimpulan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Keperluan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakSuratLayakTerbang;
    private javax.swing.JMenuItem MnCetakSuratSehat;
    private javax.swing.JMenuItem MnCetakSuratSehat1;
    private widget.TextBox Nadi;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox SpO2;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox Tb;
    private widget.TextBox Tensi;
    private widget.Button btn_ambil;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox nohp;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" surat_keterangan_sehat.tanggalsurat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                     "select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "surat_keterangan_sehat.tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu, "+                  
                     "surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,surat_keterangan_sehat.bmi,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan from surat_keterangan_sehat inner join reg_periksa on surat_keterangan_sehat.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"order by surat_keterangan_sehat.no_surat");
            }else{
                ps=koneksi.prepareStatement(
                     "select surat_keterangan_sehat.no_surat,surat_keterangan_sehat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                     "surat_keterangan_sehat.tanggalsurat,surat_keterangan_sehat.berat,surat_keterangan_sehat.tinggi,surat_keterangan_sehat.tensi,surat_keterangan_sehat.suhu, "+                  
                     "surat_keterangan_sehat.spo2,surat_keterangan_sehat.nadi,surat_keterangan_sehat.bmi,surat_keterangan_sehat.butawarna,surat_keterangan_sehat.keperluan,surat_keterangan_sehat.kesimpulan from surat_keterangan_sehat inner join reg_periksa on surat_keterangan_sehat.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where "+tgl+"and no_surat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_keterangan_sehat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_keterangan_sehat.tanggalsurat like '%"+TCari.getText().trim()+"%' "+
                     "order by surat_keterangan_sehat.no_surat");
            }
                
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14)
                        
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
        Bb.setText("");
        Tb.setText("");
        Tensi.setText("");
        Suhu.setText("");
        SpO2.setText("");
        Nadi.setText("");
        //BMI.setText("");
        
        Keperluan.setText("");
        TanggalSurat.setDate(new Date());
        CmbButaWarna.setSelectedItem("Tidak");
        CmbKesimpulan.setSelectedItem("Sehat");
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_keterangan_sehat.no_surat,3),signed)),0) from surat_keterangan_sehat where surat_keterangan_sehat.tanggalsurat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
//                "SKK"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat);
//        NoSurat.requestFocus();
    //TAMBAH ANGKA ROMAWI DISURAT
        nomorSurat();
    }

    private void nomorSurat() {
        try {
            // Ambil bulan dari tanggal yang dipilih di TanggalSurat
            String tanggalStr = Valid.SetTgl(TanggalSurat.getSelectedItem().toString());
            String bulanStr = tanggalStr.substring(5, 7); // Format: yyyy-mm-dd, ambil mm
            String tahunStr = tanggalStr.substring(0, 4);  // Format: yyyy-mm-dd, ambil yyyy

            // Konversi bulan ke romawi
            String bln_romawi = "";
            int bulanInt = Integer.parseInt(bulanStr);

            switch (bulanInt) {
                case 1:
                    bln_romawi = "I";
                    break;
                case 2:
                    bln_romawi = "II";
                    break;
                case 3:
                    bln_romawi = "III";
                    break;
                case 4:
                    bln_romawi = "IV";
                    break;
                case 5:
                    bln_romawi = "V";
                    break;
                case 6:
                    bln_romawi = "VI";
                    break;
                case 7:
                    bln_romawi = "VII";
                    break;
                case 8:
                    bln_romawi = "VIII";
                    break;
                case 9:
                    bln_romawi = "IX";
                    break;
                case 10:
                    bln_romawi = "X";
                    break;
                case 11:
                    bln_romawi = "XI";
                    break;
                case 12:
                    bln_romawi = "XII";
                    break;
                default:
                    bln_romawi = "I";
                    break;
            }

            // Format yang akan dicari: %/RJ/RSPK/VIII/2025
            String formatPattern = "/SKK/RJ-RSPK/" + bln_romawi + "/" + tahunStr;

            // Query untuk mencari nomor terakhir
            String query = "SELECT IFNULL(MAX(CAST(SUBSTRING_INDEX(no_surat, '/', 1) AS UNSIGNED)), 0) "
                    + "FROM surat_keterangan_sehat "
                    + "WHERE no_surat LIKE '%" + formatPattern + "'";

            // Eksekusi query secara manual
            PreparedStatement ps = koneksi.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            int nomorTerakhir = 0;
            if (rs.next()) {
                nomorTerakhir = rs.getInt(1);
            }

            // Increment nomor
            int nomorBaru = nomorTerakhir + 1;

            // Format nomor dengan leading zero (3 digit)
            String nomorFormat = String.format("%03d", nomorBaru);

            // Set nomor surat lengkap
            String nomorSuratLengkap = nomorFormat + formatPattern;
            NoSurat.setText(nomorSuratLengkap);

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error generating nomor surat: " + e.getMessage());
            e.printStackTrace();
            // Fallback: set nomor manual
            NoSurat.setText("001/SKK/RJ-RSPK/VIII/2025");
        }
    }
    //TAMBAH ANGKA ROMAWI DISURAT
 
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Bb.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Tb.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Tensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            SpO2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            CmbButaWarna.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Keperluan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            CmbKesimpulan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,170));
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
        BtnSimpan.setEnabled(akses.getsurat_keterangan_sehat());
        BtnHapus.setEnabled(akses.getsurat_keterangan_sehat());
        BtnEdit.setEnabled(akses.getsurat_keterangan_sehat());
    }
}




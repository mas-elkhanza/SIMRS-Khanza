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
import kepegawaian.DlgCariDokter;


/**
 * 
 * @author salimmulyana
 */
public final class SuratBebasNarkoba extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private int i=0;
    private String tgl;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SuratBebasNarkoba(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Surat Sakit","No.Rawat","No.R.M.","Nama Pasien","Tanggal Surat","Kategori","KD Dokter","Dokter","Keperluan",
            "Opiat/Morphin","Ganja/Canabis","Amphetamin","Methampetamin","Benzodiazepin","Cocain"
            
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
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(75);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(75);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        Keperluan.setDocument(new batasInput((byte)50).getKata(Keperluan));         
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }   
                KdDok.requestFocus();
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
        MnCetakSuratSKBN = new javax.swing.JMenuItem();
        MnCetakSuratSKBN1 = new javax.swing.JMenuItem();
        MnCetakSuratSKBN2 = new javax.swing.JMenuItem();
        MnCetakSuratSKBN3 = new javax.swing.JMenuItem();
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
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel18 = new widget.Label();
        TanggalSurat = new widget.Tanggal();
        jLabel13 = new widget.Label();
        jLabel20 = new widget.Label();
        Keperluan = new widget.TextBox();
        hasil1 = new widget.ComboBox();
        KdDok = new widget.TextBox();
        TDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel5 = new widget.Label();
        jLabel22 = new widget.Label();
        Kategori = new widget.ComboBox();
        jLabel23 = new widget.Label();
        hasil2 = new widget.ComboBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        hasil4 = new widget.ComboBox();
        hasil3 = new widget.ComboBox();
        hasil5 = new widget.ComboBox();
        jLabel27 = new widget.Label();
        hasil6 = new widget.ComboBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratSKBN.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSKBN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSKBN.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSKBN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSKBN.setText("Cetak SKBN 1");
        MnCetakSuratSKBN.setName("MnCetakSuratSKBN"); // NOI18N
        MnCetakSuratSKBN.setPreferredSize(new java.awt.Dimension(140, 26));
        MnCetakSuratSKBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSKBNActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSKBN);

        MnCetakSuratSKBN1.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSKBN1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSKBN1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSKBN1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSKBN1.setText("Cetak SKBN 2");
        MnCetakSuratSKBN1.setName("MnCetakSuratSKBN1"); // NOI18N
        MnCetakSuratSKBN1.setPreferredSize(new java.awt.Dimension(140, 26));
        MnCetakSuratSKBN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSKBN1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSKBN1);

        MnCetakSuratSKBN2.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSKBN2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSKBN2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSKBN2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSKBN2.setText("Cetak SKBN 3");
        MnCetakSuratSKBN2.setName("MnCetakSuratSKBN2"); // NOI18N
        MnCetakSuratSKBN2.setPreferredSize(new java.awt.Dimension(140, 26));
        MnCetakSuratSKBN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSKBN2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSKBN2);

        MnCetakSuratSKBN3.setBackground(new java.awt.Color(250, 250, 250));
        MnCetakSuratSKBN3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSKBN3.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSKBN3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSKBN3.setText("Cetak SKBN 4");
        MnCetakSuratSKBN3.setName("MnCetakSuratSKBN3"); // NOI18N
        MnCetakSuratSKBN3.setPreferredSize(new java.awt.Dimension(140, 26));
        MnCetakSuratSKBN3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSKBN3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSKBN3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Keterangan Bebas Narkoba ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-07-2020" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-07-2020" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 185));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel3.setText("No. Surat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 40, 70, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(74, 40, 170, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

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
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(332, 10, 383, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 113, 23);

        jLabel18.setText("Kategori SKBN : ");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(511, 40, 100, 23);

        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-07-2020" }));
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
        TanggalSurat.setBounds(379, 40, 90, 23);

        jLabel13.setText("Tanggal Surat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(285, 40, 90, 23);

        jLabel20.setText("Ganja / Canabis :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 130, 135, 23);

        Keperluan.setHighlighter(null);
        Keperluan.setName("Keperluan"); // NOI18N
        Keperluan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeperluanKeyPressed(evt);
            }
        });
        FormInput.add(Keperluan);
        Keperluan.setBounds(490, 70, 225, 23);

        hasil1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil1.setName("hasil1"); // NOI18N
        hasil1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil1KeyPressed(evt);
            }
        });
        FormInput.add(hasil1);
        hasil1.setBounds(139, 100, 100, 23);

        KdDok.setEditable(false);
        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        FormInput.add(KdDok);
        KdDok.setBounds(74, 70, 99, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        TDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDokterKeyPressed(evt);
            }
        });
        FormInput.add(TDokter);
        TDokter.setBounds(175, 70, 204, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("Alt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(382, 70, 28, 23);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 70, 23);

        jLabel22.setText("Keperluan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(423, 70, 63, 23);

        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UMUM", "POLRI", "TNI" }));
        Kategori.setName("Kategori"); // NOI18N
        Kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriKeyPressed(evt);
            }
        });
        FormInput.add(Kategori);
        Kategori.setBounds(615, 40, 100, 23);

        jLabel23.setText("Opiat / Morphin :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 100, 135, 23);

        hasil2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil2.setName("hasil2"); // NOI18N
        hasil2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil2KeyPressed(evt);
            }
        });
        FormInput.add(hasil2);
        hasil2.setBounds(139, 130, 100, 23);

        jLabel24.setText("Amphetamin :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(290, 100, 95, 23);

        jLabel25.setText("Methamphetamin :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(290, 130, 95, 23);

        jLabel26.setText("Benzodiazepin :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(516, 100, 95, 23);

        hasil4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil4.setName("hasil4"); // NOI18N
        hasil4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil4KeyPressed(evt);
            }
        });
        FormInput.add(hasil4);
        hasil4.setBounds(389, 130, 100, 23);

        hasil3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil3.setName("hasil3"); // NOI18N
        hasil3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil3KeyPressed(evt);
            }
        });
        FormInput.add(hasil3);
        hasil3.setBounds(389, 100, 100, 23);

        hasil5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil5.setName("hasil5"); // NOI18N
        hasil5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil5KeyPressed(evt);
            }
        });
        FormInput.add(hasil5);
        hasil5.setBounds(615, 100, 100, 23);

        jLabel27.setText("Cocain :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(516, 130, 95, 23);

        hasil6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NEGATIF", "POSITIF" }));
        hasil6.setName("hasil6"); // NOI18N
        hasil6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hasil6KeyPressed(evt);
            }
        });
        FormInput.add(hasil6);
        hasil6.setBounds(615, 130, 100, 23);

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
       Valid.pindah(evt,TCari,Kategori);
}//GEN-LAST:event_NoSuratKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Keperluan);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Surat Keterangan");
        }else if(KdDok.getText().trim().equals("")){
            Valid.textKosong(KdDok,"Kode Dokter");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else{
            if(Sequel.menyimpantf("surat_skbn","?,?,?,?,?,?,?,?,?,?,?,?","No.Surat SKBN",12,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Kategori.getSelectedItem().toString(),KdDok.getText(),
                    Keperluan.getText(),hasil1.getSelectedItem().toString(),hasil2.getSelectedItem().toString(),hasil3.getSelectedItem().toString(),
                    hasil4.getSelectedItem().toString(),hasil5.getSelectedItem().toString(),hasil6.getSelectedItem().toString()
                })==true){
                tampil();
                emptTeks();
            }else{
                autoSKBN();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,hasil6,BtnBatal);
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
        Valid.hapusTable(tabMode,NoSurat,"surat_skbn","no_surat");
        tampil();
        emptTeks();
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
            Valid.textKosong(NoSurat,"No.Surat SKBN");      
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");    
        }else if(KdDok.getText().trim().equals("")){
            Valid.textKosong(KdDok,"Kode Dokter");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"Dokter yang memeriksa");
        }else if(Keperluan.getText().trim().equals("")){
            Valid.textKosong(Keperluan,"Keperluan");
        }else{    
            if(tbObat.getSelectedRow()!= -1){
                if(Sequel.mengedittf("surat_skbn","no_surat=?","no_surat=?,no_rawat=?,tanggalsurat=?,kategori=?,kd_dokter=?,keperluan=?,opiat=?,ganja=?,amphetamin=?,methamphetamin=?,benzodiazepin=?,cocain=?",13,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(TanggalSurat.getSelectedItem()+""),Kategori.getSelectedItem().toString(),KdDok.getText(),Keperluan.getText(),hasil1.getSelectedItem().toString(),
                    hasil2.getSelectedItem().toString(),hasil3.getSelectedItem().toString(),hasil4.getSelectedItem().toString(),hasil5.getSelectedItem().toString(),hasil6.getSelectedItem().toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                    tampil();
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
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            tgl=" surat_skbn.tanggalsurat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataSuratSKBN.jasper","report","::[ Data Surat Keterangan Bebas Narkoba ]::",
                     "select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_skbn.tanggalsurat,surat_skbn.kategori,surat_skbn.kd_dokter,dokter.nm_dokter,surat_skbn.keperluan, "+
                     "surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain "+                   
                     "from surat_skbn inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_skbn.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"order by surat_skbn.no_surat",param);
            }else{
                Valid.MyReportqry("rptDataSuratSKBN.jasper","report","::[ Data Surat Keterangan Pasien ]::",
                     "select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_skbn.tanggalsurat,surat_skbn.kategori,surat_skbn.kd_dokter,dokter.nm_dokter,surat_skbn.keperluan, "+
                     "surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain "+                   
                     "from surat_skbn inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_skbn.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"and no_surat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.tanggalsurat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.kategori like '%"+TCari.getText().trim()+"%' "+
                     "order by surat_skbn.no_surat",param);
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

    private void TanggalSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSuratActionPerformed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt,NoSurat,hasil1);
    }//GEN-LAST:event_TanggalSuratKeyPressed

    private void MnCetakSuratSKBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSKBNActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("keperluan",Keperluan.getText());
                param.put("tanggalsurat",TanggalSurat.getSelectedItem());
                param.put("kategori",Kategori.getSelectedItem().toString());
                param.put("nosurat",NoSurat.getText());
                param.put("dokter",TDokter.getText());
                param.put("opiat",hasil1.getSelectedItem().toString());
                param.put("ganja",hasil2.getSelectedItem().toString());
                param.put("amphetamin",hasil3.getSelectedItem().toString());
                param.put("methamphetamin",hasil4.getSelectedItem().toString());
                param.put("benzodiazepin",hasil5.getSelectedItem().toString());
                param.put("cocain",hasil6.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDok.getText())); 
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptBebasNarkoba1.jasper","report","::[ Surat SKBN 1 ]::",
                              " select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,perusahaan_pasien.nama_perusahaan,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur," +
                              " pasien.tmp_lahir,pasien.agama,pasien.nm_pasien,pasien.jk,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,suku_bangsa.nama_suku_bangsa " +
                              " from reg_periksa inner join pasien inner join kelurahan inner join perusahaan_pasien inner join kecamatan inner join kabupaten inner join suku_bangsa " +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel "+
                              " and pasien.perusahaan_pasien=perusahaan_pasien.kode_perusahaan and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and pasien.suku_bangsa=suku_bangsa.id "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }//GEN-LAST:event_MnCetakSuratSKBNActionPerformed

    private void MnCetakSuratSKBN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSKBN1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("keperluan",Keperluan.getText());
                param.put("tanggalsurat",TanggalSurat.getSelectedItem().toString());
                param.put("kategori",Kategori.getSelectedItem().toString());
                param.put("nosurat",NoSurat.getText());
                param.put("dokter",TDokter.getText());
                param.put("opiat",hasil1.getSelectedItem().toString());
                param.put("ganja",hasil2.getSelectedItem().toString());
                param.put("amphetamin",hasil3.getSelectedItem().toString());
                param.put("methamphetamin",hasil4.getSelectedItem().toString());
                param.put("benzodiazepin",hasil5.getSelectedItem().toString());
                param.put("cocain",hasil6.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDok.getText())); 
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptBebasNarkoba2.jasper","report","::[ Surat SKBN 2 ]::",
                              " select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,perusahaan_pasien.nama_perusahaan,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur," +
                              " pasien.tmp_lahir,pasien.agama,pasien.nm_pasien,pasien.jk,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,suku_bangsa.nama_suku_bangsa " +
                              " from reg_periksa inner join pasien inner join kelurahan inner join perusahaan_pasien inner join kecamatan inner join kabupaten inner join suku_bangsa " +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel "+
                              " and pasien.perusahaan_pasien=perusahaan_pasien.kode_perusahaan and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and pasien.suku_bangsa=suku_bangsa.id "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }//GEN-LAST:event_MnCetakSuratSKBN1ActionPerformed

    private void MnCetakSuratSKBN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSKBN2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("keperluan",Keperluan.getText());
                param.put("tanggalsurat",TanggalSurat.getSelectedItem().toString());
                param.put("kategori",Kategori.getSelectedItem().toString());
                param.put("nosurat",NoSurat.getText());
                param.put("dokter",TDokter.getText());
                param.put("opiat",hasil1.getSelectedItem().toString());
                param.put("ganja",hasil2.getSelectedItem().toString());
                param.put("amphetamin",hasil3.getSelectedItem().toString());
                param.put("methamphetamin",hasil4.getSelectedItem().toString());
                param.put("benzodiazepin",hasil5.getSelectedItem().toString());
                param.put("cocain",hasil6.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDok.getText())); 
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptBebasNarkoba3.jasper","report","::[ Surat SKBN 1 ]::",
                              " select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,perusahaan_pasien.nama_perusahaan,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur," +
                              " pasien.tmp_lahir,pasien.agama,pasien.nm_pasien,pasien.jk,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,suku_bangsa.nama_suku_bangsa,pasien.nip,"+
                              " pangkat_polri.nama_pangkat,satuan_polri.nama_satuan,jabatan_polri.nama_jabatan " +
                              " from reg_periksa inner join pasien inner join kelurahan inner join perusahaan_pasien inner join kecamatan inner join kabupaten inner join suku_bangsa "+
                              " inner join pangkat_polri inner join satuan_polri inner join jabatan_polri inner join pasien_polri "+
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel "+
                              " and pasien.perusahaan_pasien=perusahaan_pasien.kode_perusahaan and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and pasien.suku_bangsa=suku_bangsa.id "+
                              " and pasien.no_rkm_medis=pasien_polri.no_rkm_medis and pasien_polri.pangkat_polri=pangkat_polri.id and pasien_polri.satuan_polri=satuan_polri.id and pasien_polri.jabatan_polri=jabatan_polri.id "+
                              " where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }//GEN-LAST:event_MnCetakSuratSKBN2ActionPerformed

    private void KeperluanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeperluanKeyPressed
         Valid.pindah(evt,btnDokter,hasil1);
    }//GEN-LAST:event_KeperluanKeyPressed

    private void hasil1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil1KeyPressed
        Valid.pindah(evt,Keperluan,hasil2);
    }//GEN-LAST:event_hasil1KeyPressed

    private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
    }//GEN-LAST:event_TDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void KategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriKeyPressed
        Valid.pindah(evt,NoSurat,btnDokter);
    }//GEN-LAST:event_KategoriKeyPressed

    private void hasil2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil2KeyPressed
        Valid.pindah(evt,hasil1,hasil3);
    }//GEN-LAST:event_hasil2KeyPressed

    private void hasil3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil3KeyPressed
        Valid.pindah(evt,hasil2,hasil4);
    }//GEN-LAST:event_hasil3KeyPressed

    private void hasil4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil4KeyPressed
        Valid.pindah(evt,hasil3,hasil5);
    }//GEN-LAST:event_hasil4KeyPressed

    private void hasil5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil5KeyPressed
        Valid.pindah(evt,hasil4,hasil6);
    }//GEN-LAST:event_hasil5KeyPressed

    private void hasil6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hasil6KeyPressed
        Valid.pindah(evt,hasil5,BtnSimpan);
    }//GEN-LAST:event_hasil6KeyPressed

    private void MnCetakSuratSKBN3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSKBN3ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("keperluan",Keperluan.getText());
                param.put("tanggalsurat",TanggalSurat.getSelectedItem().toString());
                param.put("kategori",Kategori.getSelectedItem().toString());
                param.put("nosurat",NoSurat.getText());
                param.put("dokter",TDokter.getText());
                param.put("opiat",hasil1.getSelectedItem().toString());
                param.put("ganja",hasil2.getSelectedItem().toString());
                param.put("amphetamin",hasil3.getSelectedItem().toString());
                param.put("methamphetamin",hasil4.getSelectedItem().toString());
                param.put("benzodiazepin",hasil5.getSelectedItem().toString());
                param.put("cocain",hasil6.getSelectedItem().toString());
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());  
                param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDok.getText())); 
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptBebasNarkoba.jasper","report","::[ Surat SKBN 4 ]::",
                              " select reg_periksa.no_rawat,dokter.nm_dokter,pasien.tgl_lahir,pasien.nm_pasien,pasien.pekerjaan,pasien.alamat,pasien.jk " +
                              " from reg_periksa inner join pasien inner join dokter " +
                              " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
                this.setCursor(Cursor.getDefaultCursor());  
       }
    }//GEN-LAST:event_MnCetakSuratSKBN3ActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,Kategori,Keperluan);
    }//GEN-LAST:event_btnDokterKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratBebasNarkoba dialog = new SuratBebasNarkoba(new javax.swing.JFrame(), true);
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
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Kategori;
    private widget.TextBox KdDok;
    private widget.TextBox Keperluan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakSuratSKBN;
    private javax.swing.JMenuItem MnCetakSuratSKBN1;
    private javax.swing.JMenuItem MnCetakSuratSKBN2;
    private javax.swing.JMenuItem MnCetakSuratSKBN3;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalSurat;
    private widget.Button btnDokter;
    private widget.ComboBox hasil1;
    private widget.ComboBox hasil2;
    private widget.ComboBox hasil3;
    private widget.ComboBox hasil4;
    private widget.ComboBox hasil5;
    private widget.ComboBox hasil6;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" surat_skbn.tanggalsurat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                     "select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_skbn.tanggalsurat,surat_skbn.kategori,surat_skbn.kd_dokter,dokter.nm_dokter,surat_skbn.keperluan, "+
                     "surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain "+                   
                     "from surat_skbn inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_skbn.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"order by surat_skbn.no_surat");
            }else{
                ps=koneksi.prepareStatement(
                     "select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                     "surat_skbn.tanggalsurat,surat_skbn.kategori,surat_skbn.kd_dokter,dokter.nm_dokter,surat_skbn.keperluan, "+
                     "surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain "+                   
                     "from surat_skbn inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join dokter on surat_skbn.kd_dokter=dokter.kd_dokter "+
                     "where "+tgl+"and no_surat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.tanggalsurat like '%"+TCari.getText().trim()+"%' or "+
                     tgl+"and surat_skbn.kategori like '%"+TCari.getText().trim()+"%' "+
                     "order by surat_skbn.no_surat");
            }
                
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
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
        TanggalSurat.setDate(new Date());
        Kategori.setSelectedItem("-");
        KdDok.setText("");
        TDokter.setText("");
        Keperluan.setText("");
        hasil1.setSelectedItem("NEGATIF");
        hasil2.setSelectedItem("NEGATIF");
        hasil3.setSelectedItem("NEGATIF");
        hasil4.setSelectedItem("NEGATIF");
        hasil5.setSelectedItem("NEGATIF");
        hasil6.setSelectedItem("NEGATIF");
        autoSKBN();
        NoSurat.requestFocus();
    }

 
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(TanggalSurat,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Kategori.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KdDok.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            TDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Keperluan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            hasil1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            hasil2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            hasil3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            hasil4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            hasil5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            hasil6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
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
        autoSKBN();
    }
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,185));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
       
    private void autoSKBN() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0) from surat_skbn where tanggalsurat='"+Valid.SetTgl(TanggalSurat.getSelectedItem()+"")+"' ",
                "SKBN"+TanggalSurat.getSelectedItem().toString().substring(6,10)+TanggalSurat.getSelectedItem().toString().substring(3,5)+TanggalSurat.getSelectedItem().toString().substring(0,2),3,NoSurat); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getsurat_bebas_narkoba());
        BtnHapus.setEnabled(akses.getsurat_bebas_narkoba());
        BtnEdit.setEnabled(akses.getsurat_bebas_narkoba());
    }
}




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;


/**
 * 
 * @author windiartohugroho
 */
public final class SuratPersetujuanPemeriksaanHIV extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String finger="",lokasifile="";
    
    public SuratPersetujuanPemeriksaanHIV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Persetujuan","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Tanggal","NIP","Nama Petugas"
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
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));  
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
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
                    NIP.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                }  
                NIP.requestFocus();
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
    }
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JK = new widget.TextBox();
        Umur = new widget.TextBox();
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
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Persetujuan Pemeriksaan HIV ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-07-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-07-2025" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 95));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

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
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 255, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 111, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(584, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(648, 10, 85, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(170, 40, 55, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(229, 40, 100, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(331, 40, 152, 23);

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
        btnPetugas.setBounds(485, 40, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-07-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 90, 23);

        jLabel3.setText("No. Persetujuan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(510, 40, 100, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(614, 40, 119, 23);

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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Persetujuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NamaPetugas,"Petugas");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Persetujuan");
        }else{
            if(Sequel.menyimpantf("surat_persetujuan_pemeriksaan_hiv","?,?,?,?","Data",4,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),NIP.getText()
                })==true){
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),LahirPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),NIP.getText(),NamaPetugas.getText()
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
            Valid.pindah(evt,NoSurat,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh pegawai yang bersangkutan..!!");
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NamaPetugas,"Petugas");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Persetujuan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh pegawai yang bersangkutan..!!");
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
        pegawai.dispose();
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
                Valid.MyReportqry("rptDataFormulirPersetujuanPemeriksaanHIV.jasper","report","::[ Data Formulir Persetujuan Pemeriksaan HIV ]::",
                    "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama "+
                    "from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik where "+
                    "surat_persetujuan_pemeriksaan_hiv.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by surat_persetujuan_pemeriksaan_hiv.tanggal",param);
            }else{
                Valid.MyReportqry("rptDataFormulirPersetujuanPemeriksaanHIV.jasper","report","::[ Data Formulir Persetujuan Pemeriksaan HIV ]::",
                    "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama "+
                    "from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik where "+
                    "surat_persetujuan_pemeriksaan_hiv.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "surat_persetujuan_pemeriksaan_hiv.nik like '%"+TCari.getText().trim()+"%' or pegawai.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by surat_persetujuan_pemeriksaan_hiv.tanggal",param);
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
            try {
                isPhoto();
                panggilPhoto();
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

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Tanggal,NoSurat);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
    // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        //Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,btnPetugas,BtnSimpan);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Persetujuan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripersetujuanpemeriksaanhiv");
                Sequel.queryu("insert into antripersetujuanpemeriksaanhiv values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Persetujuan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Persetujuan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti persetujuan pasien umum terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuanpemeriksaanhiv/"+lokasifile);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),8).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()));
                Valid.MyReportqry("rptSuratPersetujuanPemeriksaanHIV.jasper","report","::[ Surat Persetujuan Pemeriksaan HIV ]::",
                    "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"+
                    "pasien.no_tlp,pasien.tmp_lahir,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where surat_persetujuan_pemeriksaan_hiv.no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratPersetujuanPemeriksaanHIV dialog = new SuratPersetujuanPemeriksaanHIV(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox JK;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoSurat;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.Button btnAmbil;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama "+
                    "from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik where "+
                    "surat_persetujuan_pemeriksaan_hiv.tanggal between ? and ? order by surat_persetujuan_pemeriksaan_hiv.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama "+
                    "from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik where "+
                    "surat_persetujuan_pemeriksaan_hiv.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or surat_persetujuan_pemeriksaan_hiv.nik like ? or pegawai.nama like ?) "+
                    "order by surat_persetujuan_pemeriksaan_hiv.tanggal");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getDate("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("nik"),rs.getString("nama") 
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
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_persetujuan_pemeriksaan_hiv.no_surat,3),signed)),0) from surat_persetujuan_pemeriksaan_hiv where surat_persetujuan_pemeriksaan_hiv.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PPU"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSurat);
        NoSurat.requestFocus();
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    LahirPasien.setText(rs.getString("tgl_lahir"));
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
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,95));
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
        BtnSimpan.setEnabled(akses.getsurat_persetujuan_pemeriksaan_hiv());
        BtnHapus.setEnabled(akses.getsurat_persetujuan_pemeriksaan_hiv());
        BtnEdit.setEnabled(akses.getsurat_persetujuan_pemeriksaan_hiv());
        BtnPrint.setEnabled(akses.getsurat_persetujuan_pemeriksaan_hiv()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(pegawai.tampil3(NIP.getText()));
        }            
    }
  
    private void ganti() {
        if(Sequel.mengedittf("surat_persetujuan_pemeriksaan_hiv","no_surat=?","no_surat=?,no_rawat=?,tanggal=?,nik=?",5,new String[]{
            NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),NIP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(LahirPasien.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+""),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),9);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_persetujuan_pemeriksaan_hiv where no_surat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan.photo from surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan where surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            lokasifile=rs.getString("photo");
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/persetujuanpemeriksaanhiv/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
                        }  
                    }else{
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    lokasifile="";
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
    }
}




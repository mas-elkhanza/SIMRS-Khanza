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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
 * @author perpustakaan
 */
public final class RMChecklistKriteriaKeluarPICU extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistKriteriaKeluarPICU(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Tidak Ada Tanda Gagal Napas Akut","Saturasi O₂ Stabil Tanpa Ventilator Mekanik Atau O₂ Nasal < 2 L/menit","Status Kesadaran Stabil (Sesuai Baseline, GCS ≥ 13/Tidak Ada Penurunan Akut)",
            "Tidak Ada Perdarahan Aktif/Syok","Tidak Membutuhkan Vasopressor / Inotropik","Tanda Vital Stabil (HR, RR, Nadi, TD, Suhu)","Tidak Lagi Membutuhkan Monitoring Invasif","Tidak Membutuhkan Terapi Intensif Berkelanjutan","Nyeri Terkontrol",
            "Kebutuhan Cairan & Nutrisi Dapat Dipenuhi Secara Oral / Enteral / IV Standar","Rencana Kontrol/Tindakan Lanjutan Tercatat","Orang Tua/Wali Mendapat Edukasi Kondisi & Rencana Perawatan","Konsultasi Dengan Tim Terkait Sudah Dilakukan",
            "Rencana Terapi Jelas Untuk Rawat Ruang Biasa","Keputusan","Keterangan/Catatan","NIP/Kode Dokter","DPJP/Dokter Jaga/PICU"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        Keterangan.setDocument(new batasInput((int)50).getKata(Keterangan));
        
        
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
                    KodePetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    btnPetugas.requestFocus();
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
        MnKriteriaKeluarPICU = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel61 = new widget.Label();
        KondisiKlinis2 = new widget.ComboBox();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        KondisiKlinis1 = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel59 = new widget.Label();
        jLabel92 = new widget.Label();
        Keputusan = new widget.ComboBox();
        jLabel91 = new widget.Label();
        jLabel9 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel66 = new widget.Label();
        KondisiKlinis3 = new widget.ComboBox();
        jLabel67 = new widget.Label();
        jLabel62 = new widget.Label();
        KondisiKlinis4 = new widget.ComboBox();
        jLabel68 = new widget.Label();
        KondisiKlinis5 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel63 = new widget.Label();
        KondisiKlinis6 = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel70 = new widget.Label();
        KebutuhanPerawatan2 = new widget.ComboBox();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        KebutuhanPerawatan1 = new widget.ComboBox();
        jLabel73 = new widget.Label();
        KebutuhanPerawatan4 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        KebutuhanPerawatan3 = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel76 = new widget.Label();
        TindakLanjut2 = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        TindakLanjut1 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        TindakLanjut4 = new widget.ComboBox();
        jLabel80 = new widget.Label();
        TindakLanjut3 = new widget.ComboBox();
        jLabel81 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKriteriaKeluarPICU.setBackground(new java.awt.Color(255, 255, 254));
        MnKriteriaKeluarPICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKriteriaKeluarPICU.setForeground(new java.awt.Color(50, 50, 50));
        MnKriteriaKeluarPICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKriteriaKeluarPICU.setText("Formulir Checklist Kriteria Keluar PICU");
        MnKriteriaKeluarPICU.setName("MnKriteriaKeluarPICU"); // NOI18N
        MnKriteriaKeluarPICU.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKriteriaKeluarPICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKriteriaKeluarPICUActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKriteriaKeluarPICU);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Kriteria Keluar PICU ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 393));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

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

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-08-2025 07:38:56" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        jLabel23.setText("DPJP / Dokter Jaga / NICU :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(221, 40, 160, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(385, 40, 127, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(514, 40, 245, 23);

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

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. KONDISI KLINIS");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel61.setText("Saturasi O₂ Stabil Tanpa Ventilator Mekanik Atau O₂ Nasal < 2 L/menit :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(345, 90, 360, 23);

        KondisiKlinis2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis2.setSelectedIndex(1);
        KondisiKlinis2.setName("KondisiKlinis2"); // NOI18N
        KondisiKlinis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis2KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis2);
        KondisiKlinis2.setBounds(709, 90, 80, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 217, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Tidak Ada Tanda Gagal Napas Akut");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 190, 23);

        KondisiKlinis1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis1.setSelectedIndex(1);
        KondisiKlinis1.setName("KondisiKlinis1"); // NOI18N
        KondisiKlinis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis1KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis1);
        KondisiKlinis1.setBounds(221, 90, 80, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 340, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 340, 810, 1);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("IV. KEPUTUSAN & KETERANGAN");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(10, 340, 210, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 360, 98, 23);

        Keputusan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Layak Keluar Dari PICU/Pindah Ke Ruang Rawat Biasa", "Tidak Layak Keluar/Tetap Dirawat Di PICU" }));
        Keputusan.setName("Keputusan"); // NOI18N
        Keputusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanKeyPressed(evt);
            }
        });
        FormInput.add(Keputusan);
        Keputusan.setBounds(102, 360, 313, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Keputusan");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(40, 360, 160, 23);

        jLabel9.setText("Keterangan/Catatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(415, 360, 130, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(549, 360, 240, 23);

        jLabel66.setText(":");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 120, 434, 23);

        KondisiKlinis3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis3.setSelectedIndex(1);
        KondisiKlinis3.setName("KondisiKlinis3"); // NOI18N
        KondisiKlinis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis3KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis3);
        KondisiKlinis3.setBounds(438, 120, 80, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Status Kesadaran Stabil (Sesuai Baseline, GCS ≥ 13/Tidak Ada Penurunan Akut)");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 120, 430, 23);

        jLabel62.setText("Tidak Ada Perdarahan Aktif/Syok :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(515, 120, 190, 23);

        KondisiKlinis4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis4.setSelectedIndex(1);
        KondisiKlinis4.setName("KondisiKlinis4"); // NOI18N
        KondisiKlinis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis4KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis4);
        KondisiKlinis4.setBounds(709, 120, 80, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 150, 262, 23);

        KondisiKlinis5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis5.setSelectedIndex(1);
        KondisiKlinis5.setName("KondisiKlinis5"); // NOI18N
        KondisiKlinis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis5KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis5);
        KondisiKlinis5.setBounds(266, 150, 80, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Tidak Membutuhkan Vasopressor / Inotropik");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(40, 150, 240, 23);

        jLabel63.setText("Tanda Vital Stabil (HR, RR, Nadi, TD, Suhu) :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(475, 150, 230, 23);

        KondisiKlinis6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KondisiKlinis6.setSelectedIndex(1);
        KondisiKlinis6.setName("KondisiKlinis6"); // NOI18N
        KondisiKlinis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKlinis6KeyPressed(evt);
            }
        });
        FormInput.add(KondisiKlinis6);
        KondisiKlinis6.setBounds(709, 150, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 180, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 180, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. KEBUTUHAN PERAWATAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 180, 180, 23);

        jLabel70.setText("Tidak Membutuhkan Terapi Intensif Berkelanjutan :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(415, 200, 290, 23);

        KebutuhanPerawatan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KebutuhanPerawatan2.setSelectedIndex(1);
        KebutuhanPerawatan2.setName("KebutuhanPerawatan2"); // NOI18N
        KebutuhanPerawatan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanPerawatan2KeyPressed(evt);
            }
        });
        FormInput.add(KebutuhanPerawatan2);
        KebutuhanPerawatan2.setBounds(709, 200, 80, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 200, 258, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Tidak Lagi Membutuhkan Monitoring Invasif ");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(40, 200, 230, 23);

        KebutuhanPerawatan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KebutuhanPerawatan1.setSelectedIndex(1);
        KebutuhanPerawatan1.setName("KebutuhanPerawatan1"); // NOI18N
        KebutuhanPerawatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanPerawatan1KeyPressed(evt);
            }
        });
        FormInput.add(KebutuhanPerawatan1);
        KebutuhanPerawatan1.setBounds(262, 200, 80, 23);

        jLabel73.setText("Kebutuhan Cairan & Nutrisi Dapat Dipenuhi Secara Oral / Enteral / IV Standar :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(255, 230, 450, 23);

        KebutuhanPerawatan4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KebutuhanPerawatan4.setSelectedIndex(1);
        KebutuhanPerawatan4.setName("KebutuhanPerawatan4"); // NOI18N
        KebutuhanPerawatan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanPerawatan4KeyPressed(evt);
            }
        });
        FormInput.add(KebutuhanPerawatan4);
        KebutuhanPerawatan4.setBounds(709, 230, 80, 23);

        jLabel74.setText(":");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(0, 230, 125, 23);

        KebutuhanPerawatan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KebutuhanPerawatan3.setSelectedIndex(1);
        KebutuhanPerawatan3.setName("KebutuhanPerawatan3"); // NOI18N
        KebutuhanPerawatan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KebutuhanPerawatan3KeyPressed(evt);
            }
        });
        FormInput.add(KebutuhanPerawatan3);
        KebutuhanPerawatan3.setBounds(129, 230, 80, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Nyeri Terkontrol");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(40, 230, 110, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 260, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 260, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. RENCANA TINDAK LANJUT");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 260, 180, 23);

        jLabel76.setText("Orang Tua/Wali Mendapat Edukasi Kondisi & Rencana Perawatan :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(355, 280, 350, 23);

        TindakLanjut2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TindakLanjut2.setSelectedIndex(1);
        TindakLanjut2.setName("TindakLanjut2"); // NOI18N
        TindakLanjut2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjut2KeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut2);
        TindakLanjut2.setBounds(709, 280, 80, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 280, 264, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Rencana Kontrol/Tindakan Lanjutan Tercatat");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(40, 280, 230, 23);

        TindakLanjut1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TindakLanjut1.setSelectedIndex(1);
        TindakLanjut1.setName("TindakLanjut1"); // NOI18N
        TindakLanjut1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjut1KeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut1);
        TindakLanjut1.setBounds(268, 280, 80, 23);

        jLabel79.setText("Rencana Terapi Jelas Untuk Rawat Ruang Biasa :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(415, 310, 290, 23);

        TindakLanjut4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TindakLanjut4.setSelectedIndex(1);
        TindakLanjut4.setName("TindakLanjut4"); // NOI18N
        TindakLanjut4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjut4KeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut4);
        TindakLanjut4.setBounds(709, 310, 80, 23);

        jLabel80.setText(":");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 310, 278, 23);

        TindakLanjut3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TindakLanjut3.setSelectedIndex(1);
        TindakLanjut3.setName("TindakLanjut3"); // NOI18N
        TindakLanjut3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjut3KeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut3);
        TindakLanjut3.setBounds(282, 310, 80, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Konsultasi Dengan Tim Terkait Sudah Dilakukan");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(40, 310, 250, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
            Tanggal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TCari.requestFocus();
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"DPJP/Dokter Jaga/IGD");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Keterangan,BtnBatal);
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"DPJP/Dokter Jaga/IGD");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
            try{
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Ada Tanda Gagal Napas Akut</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saturasi O₂ Stabil Tanpa Ventilator Mekanik Atau O₂ Nasal < 2 L/menit</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Status Kesadaran Stabil (Sesuai Baseline, GCS ≥ 13/Tidak Ada Penurunan Akut)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Ada Perdarahan Aktif/Syok</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Membutuhkan Vasopressor / Inotropik</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanda Vital Stabil (HR, RR, Nadi, TD, Suhu)</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Lagi Membutuhkan Monitoring Invasif</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Membutuhkan Terapi Intensif Berkelanjutan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Terkontrol</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kebutuhan Cairan & Nutrisi Dapat Dipenuhi Secara Oral / Enteral / IV Standar</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rencana Kontrol/Tindakan Lanjutan Tercatat</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Orang Tua/Wali Mendapat Edukasi Kondisi & Rencana Perawatan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Konsultasi Dengan Tim Terkait Sudah Dilakukan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rencana Terapi Jelas Untuk Rawat Ruang Biasa</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keputusan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan/Catatan</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP/Kode Dokter</b></td>").append(
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>DPJP/Dokter Jaga/PICU").append(
                    "</tr>"
                );
                
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>").append(
                           "<td valign='top'>").append(tbObat.getValueAt(i,0).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,1).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,2).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,3).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,4).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,5).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,6).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,7).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,8).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,9).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,10).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,11).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,12).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,13).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,14).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,15).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,16).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,17).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,18).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,19).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,20).toString()).append("</td>").append( 
                            "<td valign='top'>").append(tbObat.getValueAt(i,21).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,22).toString()).append("</td>").append(
                            "<td valign='top'>").append(tbObat.getValueAt(i,23).toString()).append("</td>").append(
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3300px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistKriteriaKeluarPICU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3300px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA KELUAR PICU<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
                htmlContent=null;
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

    private void MnKriteriaKeluarPICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKriteriaKeluarPICUActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            String finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),22).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistKriteriaKeluarPICU.jasper","report","::[ Formulir Check List Kriteria Keluar PICU ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_keluar_picu.tanggal,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis1,checklist_kriteria_keluar_picu.kondisiklinis2,checklist_kriteria_keluar_picu.kondisiklinis3,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis4,checklist_kriteria_keluar_picu.kondisiklinis5,checklist_kriteria_keluar_picu.kondisiklinis6,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan1,checklist_kriteria_keluar_picu.kebutuhanperawatan2,checklist_kriteria_keluar_picu.kebutuhanperawatan3,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan4,checklist_kriteria_keluar_picu.tindaklanjut1,checklist_kriteria_keluar_picu.tindaklanjut2,"+
                    "checklist_kriteria_keluar_picu.tindaklanjut3,checklist_kriteria_keluar_picu.tindaklanjut4,checklist_kriteria_keluar_picu.keputusan,"+
                    "checklist_kriteria_keluar_picu.keterangan,checklist_kriteria_keluar_picu.nik,pegawai.nama from checklist_kriteria_keluar_picu "+
                    "inner join reg_periksa on checklist_kriteria_keluar_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_keluar_picu.nik "+
                    "where checklist_kriteria_keluar_picu.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_kriteria_keluar_picu.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnKriteriaKeluarPICUActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,btnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
       Valid.pindah(evt,Tanggal,KondisiKlinis1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void KondisiKlinis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis2KeyPressed
        Valid.pindah(evt,KondisiKlinis1,KondisiKlinis3);
    }//GEN-LAST:event_KondisiKlinis2KeyPressed

    private void KondisiKlinis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis1KeyPressed
        Valid.pindah(evt,btnPetugas,KondisiKlinis2);
    }//GEN-LAST:event_KondisiKlinis1KeyPressed

    private void KeputusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanKeyPressed
        //Valid.pindah(evt,TindakLanjut3,Keterangan);
    }//GEN-LAST:event_KeputusanKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Keputusan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void KondisiKlinis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis3KeyPressed
        //Valid.pindah(evt,Respirasi2,KondisiKlinis4);
    }//GEN-LAST:event_KondisiKlinis3KeyPressed

    private void KondisiKlinis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiKlinis4KeyPressed

    private void KondisiKlinis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiKlinis5KeyPressed

    private void KondisiKlinis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKlinis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KondisiKlinis6KeyPressed

    private void KebutuhanPerawatan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanPerawatan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanPerawatan2KeyPressed

    private void KebutuhanPerawatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanPerawatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanPerawatan1KeyPressed

    private void KebutuhanPerawatan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanPerawatan4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanPerawatan4KeyPressed

    private void KebutuhanPerawatan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KebutuhanPerawatan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KebutuhanPerawatan3KeyPressed

    private void TindakLanjut2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjut2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjut2KeyPressed

    private void TindakLanjut1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjut1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjut1KeyPressed

    private void TindakLanjut4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjut4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjut4KeyPressed

    private void TindakLanjut3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjut3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakLanjut3KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistKriteriaKeluarPICU dialog = new RMChecklistKriteriaKeluarPICU(new javax.swing.JFrame(), true);
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
    private widget.TextBox JK;
    private widget.ComboBox KebutuhanPerawatan1;
    private widget.ComboBox KebutuhanPerawatan2;
    private widget.ComboBox KebutuhanPerawatan3;
    private widget.ComboBox KebutuhanPerawatan4;
    private widget.ComboBox Keputusan;
    private widget.TextBox Keterangan;
    private widget.TextBox KodePetugas;
    private widget.ComboBox KondisiKlinis1;
    private widget.ComboBox KondisiKlinis2;
    private widget.ComboBox KondisiKlinis3;
    private widget.ComboBox KondisiKlinis4;
    private widget.ComboBox KondisiKlinis5;
    private widget.ComboBox KondisiKlinis6;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnKriteriaKeluarPICU;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.ComboBox TindakLanjut1;
    private widget.ComboBox TindakLanjut2;
    private widget.ComboBox TindakLanjut3;
    private widget.ComboBox TindakLanjut4;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
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
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel9;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_keluar_picu.tanggal,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis1,checklist_kriteria_keluar_picu.kondisiklinis2,checklist_kriteria_keluar_picu.kondisiklinis3,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis4,checklist_kriteria_keluar_picu.kondisiklinis5,checklist_kriteria_keluar_picu.kondisiklinis6,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan1,checklist_kriteria_keluar_picu.kebutuhanperawatan2,checklist_kriteria_keluar_picu.kebutuhanperawatan3,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan4,checklist_kriteria_keluar_picu.tindaklanjut1,checklist_kriteria_keluar_picu.tindaklanjut2,"+
                    "checklist_kriteria_keluar_picu.tindaklanjut3,checklist_kriteria_keluar_picu.tindaklanjut4,checklist_kriteria_keluar_picu.keputusan,"+
                    "checklist_kriteria_keluar_picu.keterangan,checklist_kriteria_keluar_picu.nik,pegawai.nama from checklist_kriteria_keluar_picu "+
                    "inner join reg_periksa on checklist_kriteria_keluar_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_keluar_picu.nik "+
                    "where checklist_kriteria_keluar_picu.tanggal between ? and ? order by checklist_kriteria_keluar_picu.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_kriteria_keluar_picu.tanggal,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis1,checklist_kriteria_keluar_picu.kondisiklinis2,checklist_kriteria_keluar_picu.kondisiklinis3,"+
                    "checklist_kriteria_keluar_picu.kondisiklinis4,checklist_kriteria_keluar_picu.kondisiklinis5,checklist_kriteria_keluar_picu.kondisiklinis6,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan1,checklist_kriteria_keluar_picu.kebutuhanperawatan2,checklist_kriteria_keluar_picu.kebutuhanperawatan3,"+
                    "checklist_kriteria_keluar_picu.kebutuhanperawatan4,checklist_kriteria_keluar_picu.tindaklanjut1,checklist_kriteria_keluar_picu.tindaklanjut2,"+
                    "checklist_kriteria_keluar_picu.tindaklanjut3,checklist_kriteria_keluar_picu.tindaklanjut4,checklist_kriteria_keluar_picu.keputusan,"+
                    "checklist_kriteria_keluar_picu.keterangan,checklist_kriteria_keluar_picu.nik,pegawai.nama from checklist_kriteria_keluar_picu "+
                    "inner join reg_periksa on checklist_kriteria_keluar_picu.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pegawai on pegawai.nik=checklist_kriteria_keluar_picu.nik "+
                    "where checklist_kriteria_keluar_picu.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pegawai.nama like ? or checklist_kriteria_keluar_picu.nik like ?) order by checklist_kriteria_keluar_picu.tanggal ");
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
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("kondisiklinis1"),rs.getString("kondisiklinis2"),rs.getString("kondisiklinis3"),
                        rs.getString("kondisiklinis4"),rs.getString("kondisiklinis5"),rs.getString("kondisiklinis6"),rs.getString("kebutuhanperawatan1"),
                        rs.getString("kebutuhanperawatan2"),rs.getString("kebutuhanperawatan3"),rs.getString("kebutuhanperawatan4"),rs.getString("tindaklanjut1"),
                        rs.getString("tindaklanjut2"),rs.getString("tindaklanjut3"),rs.getString("tindaklanjut4"),rs.getString("keputusan"),
                        rs.getString("keterangan"),rs.getString("nik"),rs.getString("nama")
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
        KondisiKlinis1.setSelectedItem("Tidak");
        KondisiKlinis2.setSelectedItem("Tidak");
        KondisiKlinis3.setSelectedItem("Tidak");
        KondisiKlinis4.setSelectedItem("Tidak");
        KondisiKlinis5.setSelectedItem("Tidak");
        KondisiKlinis6.setSelectedItem("Tidak");
        KebutuhanPerawatan1.setSelectedItem("Tidak");
        KebutuhanPerawatan2.setSelectedItem("Tidak");
        KebutuhanPerawatan3.setSelectedItem("Tidak");
        KebutuhanPerawatan4.setSelectedItem("Tidak");
        TindakLanjut1.setSelectedItem("Tidak");
        TindakLanjut2.setSelectedItem("Tidak");
        TindakLanjut3.setSelectedItem("Tidak");
        TindakLanjut4.setSelectedItem("Tidak");
        Keputusan.setSelectedItem("Tidak");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        KondisiKlinis1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KondisiKlinis1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KondisiKlinis2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KondisiKlinis3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KondisiKlinis4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KondisiKlinis5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KondisiKlinis6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KebutuhanPerawatan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KebutuhanPerawatan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KebutuhanPerawatan3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KebutuhanPerawatan4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TindakLanjut1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TindakLanjut2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            TindakLanjut3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            TindakLanjut4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Keputusan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-306));
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
        BtnSimpan.setEnabled(akses.getkriteria_keluar_picu());
        BtnHapus.setEnabled(akses.getkriteria_keluar_picu());
        BtnEdit.setEnabled(akses.getkriteria_keluar_picu());
        BtnPrint.setEnabled(akses.getkriteria_keluar_picu()); 
        if(akses.getjml2()>=1){
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(pegawai.tampil3(akses.getkode()));
        }
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("checklist_kriteria_keluar_picu","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kondisiklinis1=?,kondisiklinis2=?,kondisiklinis3=?,kondisiklinis4=?,kondisiklinis5=?,"+
                "kondisiklinis6=?,kebutuhanperawatan1=?,kebutuhanperawatan2=?,kebutuhanperawatan3=?,kebutuhanperawatan4=?,tindaklanjut1=?,tindaklanjut2=?,tindaklanjut3=?,tindaklanjut4=?,keputusan=?,"+
                "keterangan=?,nik=?",21,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KondisiKlinis1.getSelectedItem().toString(),
                KondisiKlinis2.getSelectedItem().toString(),KondisiKlinis3.getSelectedItem().toString(),KondisiKlinis4.getSelectedItem().toString(),KondisiKlinis5.getSelectedItem().toString(),
                KondisiKlinis6.getSelectedItem().toString(),KebutuhanPerawatan1.getSelectedItem().toString(),KebutuhanPerawatan2.getSelectedItem().toString(),KebutuhanPerawatan3.getSelectedItem().toString(),
                KebutuhanPerawatan4.getSelectedItem().toString(),TindakLanjut1.getSelectedItem().toString(),TindakLanjut2.getSelectedItem().toString(),TindakLanjut3.getSelectedItem().toString(),
                TindakLanjut4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),Keterangan.getText(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(KondisiKlinis1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(KondisiKlinis2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KondisiKlinis3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(KondisiKlinis4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(KondisiKlinis5.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(KondisiKlinis6.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(KebutuhanPerawatan1.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(KebutuhanPerawatan2.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(KebutuhanPerawatan3.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(KebutuhanPerawatan4.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(TindakLanjut1.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(TindakLanjut2.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(TindakLanjut3.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(TindakLanjut3.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Keputusan.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),23);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_kriteria_keluar_picu where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("checklist_kriteria_keluar_picu","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",19,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KondisiKlinis1.getSelectedItem().toString(),
            KondisiKlinis2.getSelectedItem().toString(),KondisiKlinis3.getSelectedItem().toString(),KondisiKlinis4.getSelectedItem().toString(),KondisiKlinis5.getSelectedItem().toString(),
            KondisiKlinis6.getSelectedItem().toString(),KebutuhanPerawatan1.getSelectedItem().toString(),KebutuhanPerawatan2.getSelectedItem().toString(),KebutuhanPerawatan3.getSelectedItem().toString(),
            KebutuhanPerawatan4.getSelectedItem().toString(),TindakLanjut1.getSelectedItem().toString(),TindakLanjut2.getSelectedItem().toString(),TindakLanjut3.getSelectedItem().toString(),
            TindakLanjut4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),Keterangan.getText(),KodePetugas.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                KondisiKlinis1.getSelectedItem().toString(),KondisiKlinis2.getSelectedItem().toString(),KondisiKlinis3.getSelectedItem().toString(),KondisiKlinis4.getSelectedItem().toString(),KondisiKlinis5.getSelectedItem().toString(),
                KondisiKlinis6.getSelectedItem().toString(),KebutuhanPerawatan1.getSelectedItem().toString(),KebutuhanPerawatan2.getSelectedItem().toString(),KebutuhanPerawatan3.getSelectedItem().toString(),KebutuhanPerawatan4.getSelectedItem().toString(),
                TindakLanjut1.getSelectedItem().toString(),TindakLanjut2.getSelectedItem().toString(),TindakLanjut3.getSelectedItem().toString(),TindakLanjut4.getSelectedItem().toString(),Keputusan.getSelectedItem().toString(),Keterangan.getText(),
                KodePetugas.getText(),NamaPetugas.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
}
